---create database ThongKe_ChanNuoi;
use ThongKe_ChanNuoi;

ALTER TABLE huyen
DROP CONSTRAINT IF EXISTS FK_huyen_tinh;

ALTER TABLE xa
DROP CONSTRAINT IF EXISTS FK_xa_huyen;

ALTER TABLE ap
DROP CONSTRAINT IF EXISTS FK_ap_xa;

ALTER TABLE quy_mo
DROP CONSTRAINT IF EXISTS FK_quy_mo_vat_nuoi;

-- Step 2: Drop tables in dependency order (child to parent)
IF OBJECT_ID ('loai_phieu', 'U') IS NOT NULL
DROP TABLE loai_phieu;

IF OBJECT_ID ('loai_van_ban', 'U') IS NOT NULL
DROP TABLE loai_van_ban;

IF OBJECT_ID ('loai_hinh_doanh_nghiep', 'U') IS NOT NULL
DROP TABLE loai_hinh_doanh_nghiep;

IF OBJECT_ID ('sp_chan_nuoi', 'U') IS NOT NULL
DROP TABLE sp_chan_nuoi;

IF OBJECT_ID ('quy_mo', 'U') IS NOT NULL
DROP TABLE quy_mo;

IF OBJECT_ID ('vat_nuoi', 'U') IS NOT NULL
DROP TABLE vat_nuoi;

IF OBJECT_ID ('ap', 'U') IS NOT NULL
DROP TABLE ap;

IF OBJECT_ID ('xa', 'U') IS NOT NULL
DROP TABLE xa;

IF OBJECT_ID ('huyen', 'U') IS NOT NULL
DROP TABLE huyen;

IF OBJECT_ID ('tinh', 'U') IS NOT NULL
DROP TABLE tinh;

IF OBJECT_ID ('users', 'U') IS NOT NULL
DROP TABLE users;

--drop database ThongKe_ChanNuoi
IF OBJECT_ID ('users', 'U') IS NOT NULL
DROP TABLE users;

IF OBJECT_ID ('tinh', 'U') IS NOT NULL
DROP TABLE tinh;

IF OBJECT_ID ('huyen', 'U') IS NOT NULL
DROP TABLE huyen;

IF OBJECT_ID ('xa', 'U') IS NOT NULL
DROP TABLE xa;

IF OBJECT_ID ('ap', 'U') IS NOT NULL
DROP TABLE ap;

IF OBJECT_ID ('quy_mo', 'U') IS NOT NULL
DROP TABLE quy_mo;

IF OBJECT_ID ('sp_chan_nuoi', 'U') IS NOT NULL
DROP TABLE sp_chan_nuoi;

IF OBJECT_ID ('vat_nuoi', 'U') IS NOT NULL
DROP TABLE vat_nuoi;

IF OBJECT_ID ('loai_hinh_doanh_nghiep', 'U') IS NOT NULL
DROP TABLE loai_hinh_doanh_nghiep;

IF OBJECT_ID ('loai_van_ban', 'U') IS NOT NULL
DROP TABLE loai_van_ban;

IF OBJECT_ID ('loai_phieu', 'U') IS NOT NULL
DROP TABLE loai_phieu;

create table
	users (
		Id int identity (1, 1) primary key,
		User_name varchar(255) unique not null,
		Password varchar(255) not null,
		is_admin bit
	)
create table
	tinh (ma_tinh int primary key, ten_tinh nvarchar (255))
create table
	huyen (
		ma_tinh int,
		ma_huyen int,
		primary key (ma_tinh, ma_huyen),
		ten_huyen nvarchar (255),
		CONSTRAINT FK_huyen_tinh FOREIGN KEY (ma_tinh) REFERENCES tinh (ma_tinh)
	)
create table
	xa (
		Ma_tinh int,
		Ma_huyen int,
		Ma_xa int,
		primary key (ma_tinh, ma_huyen, Ma_xa),
		Ten_xa nvarchar (255),
		is_Loai bit,
		CONSTRAINT FK_xa_huyen FOREIGN KEY (ma_tinh, ma_huyen) REFERENCES huyen (ma_tinh, ma_huyen)
	)
	--drop table ap
create table
	ap (
		Ma_tinh int,
		Ma_huyen int,
		Ma_xa int,
		Ma_ap int,
		primary key (ma_tinh, ma_huyen, Ma_xa, Ma_ap),
		Ten_ap nvarchar (255),
	)
create table
	vat_nuoi (
		Id int Identity (1, 1) primary key,
		ten_vat_nuoi nvarchar (255),
		ma_vat_nuoi nvarchar (255),
		is_nhom_khac bit
	)
	--drop table QuyMo
create table
	quy_mo (
		Id varchar(10) primary key,
		quy_mo_name nvarchar (255) not null,
		vat_nuoi_id int not null,
		CONSTRAINT FK_quy_mo_vat_nuoi FOREIGN KEY (vat_nuoi_id) REFERENCES vat_nuoi (Id)
	)
create table
	sp_chan_nuoi (
		ma_sp_chan_nuoi int identity (1, 1) primary key,
		ten_sp_chan_nuoi nvarchar (255),
		is_gia_cam bit
	)
create table
	loai_hinh_doanh_nghiep (
		ma_loai_hinh int identity (1, 1) primary key,
		ten_loai_hinh nvarchar (255)
	)
create table
	loai_van_ban (
		ma_loai_van_ban int identity (1, 1) primary key,
		ten_ma_loai_van_ban nvarchar (255) not null,
		ghi_chu nvarchar (255)
	)
create table
	loai_phieu (
		ma_phieu int identity (1, 1) primary key,
		ten_phieu nvarchar (255)
	)
insert into
	users (User_name, Password, is_admin)
values
	('HuuLoc', '123', 1),
	('NhatKha', '$2a$10$XJdL9eKNYA2RqUfE3FqnQuylsuKPjTIHEq/xygEZsqgUIjwDa7BXu', 0)-- Khannps39199@
insert into
	loai_phieu (ten_phieu)
values
	(N'phiếu số 01'),
	(N'phiếu số 02'),
	(N'phiếu số 03')
insert into
	loai_hinh_doanh_nghiep (ten_loai_hinh)
values
	(N'Doanh nghiệp ngoài nhà nước'),
	(N'Doanh nghiệp FDI'),
	(N'Họp tác xã'),
	(N'Tổ chức khác thuộc khu vực Nhà nước'),
	(N'Tổ chức khác thuộc khu vực Nhà nước(cá thể)')
insert into
	loai_van_ban (ten_ma_loai_van_ban, ghi_chu)
values
	(N'Phiếu điểu tra', ''),
	(N'Phương án điều tra', ''),
	(N'Các phụ luc hướng dẫn ', ''),
	(N'Thông báo nghiệp vụ', '')
insert into
	vat_nuoi (ten_vat_nuoi, ma_vat_nuoi, is_nhom_khac)
values
	(N'Trâu', N'0141210', 1),
	(N'Bò', N'0141220', 1),
	(N'Ngựa', N'0141210', 1),
	(N'Lừa', N'0142220', 1),
	(N'La', N'0142230', 1),
	(N'Dê', N'0144210', 1),
	(N'Cừu', N'0144220', 1),
	(N'Hươu', N'0144230', 1),
	(N'Nai', N'0144240', 1),
	(N'Lợn sữa', N'0145120', 0),
	(N'Lợn', N'0145200', 0),
	(N'Lợn thịt', N'0145201', 0),
	(N'Lợn nái', N'0145202', 0),
	(N'Lợn đực giống', N'0145203', 0),
	(N'Gà', N'0146210', 0),
	(N'Vịt', N'0146310', 0),
	(N'Ngan', N'0146320', 0),
	(N'Ngỗng', N'0146330', 0)
INSERT INTO
	sp_chan_nuoi (ten_sp_chan_nuoi, is_gia_cam)
VALUES
	(N'Sản phẩm chăn nuôi trâu, bò', 0),
	(N'Sản phẩm chăn nuôi ngựa, lừa, la', 1),
	(N'Sản phẩm chăn nuôi dê, cừu, hươu, nai', 1),
	(N'Sản phẩm chăn nuôi lợn', 0),
	(N'Sản phẩm chăn nuôi gia cầm', 1),
	(N'Sản phẩm chăn nuôi vịt, ngan, ngỗng', 1),
	(N'Sản phẩm chăn nuôi gia cầm khác', 1),
	(N'Sản phẩm chăn nuôi khác', 1);

--drop table Huyen
-- Thêm dữ liệu vào bảng Huyen dựa trên dữ liệu cung cấp (gộp nhiều VALUES)
--drop table Users
INSERT INTO
	quy_mo (Id, quy_mo_name, vat_nuoi_id)
VALUES
	('BS01', N'Nuôi bò sữa từ 1 đến 19 con', 2),
	('BS02', N'Nuôi bò sữa từ 20 con trở lên', 2),
	('BT01', N'Nuôi bò khác từ 1 đến 29 con', 2),
	('BT02', N'Nuôi bò khác từ 30 con trở lên', 2),
	('G01', N'Nuôi gà từ 1 đến 49 con', 15),
	('G02', N'Nuôi gà từ 50 đến 199 con', 15),
	('G03', N'Nuôi gà từ 200 đến 999 con', 15),
	('G04_1', N'Nuôi gà thịt từ 1000 đến 3999 con', 15),
	(
		'G04_2',
		N'Nuôi gà đẻ trứng từ 1000 đến 3999 con',
		15
	),
	('G05', N'Nuôi gà từ 4000 con trở lên', 15),
	('L01', N'Nuôi lợn từ 1 đến 9 con', 11),
	('L02', N'Nuôi lợn từ 10 đến 29 con', 11),
	('L03', N'Nuôi lợn từ 30 đến 99 con', 11),
	('L04', N'Nuôi lợn từ 100 đến 199 con', 11),
	('L05', N'Nuôi lợn từ 200 đến 299 con', 11),
	('L06', N'Nuôi lợn từ 300 con trở lên', 11),
	('N01', N'Nuôi ngan từ 1 đến 49 con', 17),
	('N02', N'Nuôi ngan từ 50 đến 199 con', 17),
	('N03', N'Nuôi ngan từ 200 đến 499 con', 17),
	('N04', N'Nuôi ngan từ 500 con trở lên', 17),
	('Tr01', N'Nuôi trâu từ 1 đến 29 con', 1),
	('Tr02', N'Nuôi trâu từ 30 con trở lên', 1),
	('V01', N'Nuôi vịt từ 1 đến 49 con', 16);

-- Chèn dữ liệu mẫu vào bảng xa
INSERT INTO
	tinh (ma_tinh, ten_tinh)
VALUES
	(87, N'Đồng Tháp');

INSERT INTO
	huyen (ma_tinh, ma_huyen, ten_huyen)
VALUES
	('87', '866', N'Thành phố Cao Lãnh'),
	('87', '867', N'Thành phố Sa Đéc'),
	('87', '868', N'Thành phố Hồng Ngự'),
	('87', '869', N'Huyện Tân Hồng'),
	('87', '870', N'Huyện Hồng Ngự'),
	('87', '871', N'Huyện Tam Nông'),
	('87', '872', N'Huyện Tháp Mười'),
	('87', '873', N'Huyện Cao Lãnh'),
	('87', '874', N'Huyện Thanh Bình'),
	('87', '875', N'Huyện Lấp Vò'),
	('87', '876', N'Huyện Lai Vung'),
	('87', '877', N'Huyện Châu Thành');

INSERT INTO
	xa (Ma_tinh, Ma_huyen, Ma_xa, Ten_xa, is_Loai)
VALUES
	('87', '866', '29863', N'Phường 11', 1), -- Thành thị
	('87', '866', '29866', N'Phường 1', 1), -- Thành thị
	('87', '866', '29869', N'Phường 2', 1), -- Thành thị
	('87', '866', '29872', N'Phường 4', 1), -- Thành thị
	('87', '866', '29875', N'Phường 3', 1), -- Thành thị
	('87', '866', '29878', N'Phường 6', 1), -- Thành thị
	('87', '866', '29881', N'Xã Mỹ Ngãi', 0), -- Nông thôn
	('87', '866', '29884', N'Xã Mỹ Tân', 0), -- Nông thôn
	('87', '866', '29887', N'Xã Mỹ Trà', 0), -- Nông thôn
	('87', '866', '29888', N'Phường Mỹ Phú', 1), -- Thành thị
	('87', '866', '29890', N'Xã Tân Thuận Tây', 0), -- Nông thôn
	('87', '866', '29892', N'Phường Hoà Thuận', 1), -- Thành thị
	('87', '866', '29893', N'Xã Hòa An', 0), -- Nông thôn
	('87', '866', '29896', N'Xã Tân Thuận Đông', 0), -- Nông thôn
	('87', '866', '29899', N'Xã Tịnh Thới', 0), -- Nông thôn
	('87', '867', '29902', N'Phường 3', 1), -- Thành thị
	('87', '867', '29905', N'Phường 1', 1), -- Thành thị
	('87', '867', '29908', N'Phường 4', 1), -- Thành thị
	('87', '867', '29911', N'Phường 2', 1), -- Thành thị
	('87', '876', '29914', N'Xã Tân Khánh Đông', 0), -- Nông thôn
	('87', '876', '29917', N'Phường Tân Quy Đông', 1), -- Thành thị
	('87', '876', '29919', N'Phường An Hoà', 1), -- Thành thị
	('87', '876', '29920', N'Xã Tân Quy Tây', 0);

-- Nông thôn
INSERT INTO
	ap (Ma_tinh, Ma_huyen, Ma_xa, Ma_ap, Ten_ap)
VALUES
	('87', '870', '29956', '01', N'Ap 1'),
	('87', '870', '29956', '02', N'Ap 2'),
	('87', '870', '29956', '03', N'Ap 3'),
	('87', '870', '29956', '04', N'Ấp Chôm Xoài'),
	('87', '870', '29956', '05', N'Ấp Giồng Bàng'),
	('87', '870', '29962', '01', N'Ấp Bình Hòa Thương'),
	('87', '870', '29962', '02', N'Ấp Bình Hòa Trung'),
	(
		'87',
		'870',
		'29971',
		'00',
		N'Thị trấn Thường Thới Tiền'
	),
	('87', '870', '29971', '01', N'Khóm Thương 1'),
	('87', '870', '29971', '02', N'Khóm Thương 2'),
	('87', '870', '29971', '03', N'Khóm Trung 1'),
	('87', '870', '29971', '04', N'Khóm Trung 2'),
	('87', '870', '29974', '01', N'Ấp 1'),
	('87', '870', '29974', '02', N'Ap 2'),
	('87', '870', '29974', '03', N'Ap 3'),
	('87', '870', '29977', '01', N'Ấp Thi'),
	('87', '870', '29977', '02', N'Ấp Trà Đư'),
	('87', '870', '29977', '03', N'ấp Bình Hòa Ha'),
	('87', '870', '29977', '04', N'Ap 1'),
	('87', '870', '29977', '05', N'ap 2'),
	('87', '870', '29977', '06', N'ap 6'),
	('87', '870', '29980', '01', N'Ấp Long Tả'),
	('87', '870', '29980', '02', N'Ấp Long Hữu'),
	('87', '870', '29980', '03', N'Ấp Long Thạnh B'),
	('87', '870', '29980', '04', N'Ấp Long Thạnh A'),
	('87', '870', '29980', '05', N'Ấp Long Phước'),
	('87', '870', '29980', '06', N'Ấp Long Hậu'),
	('87', '870', '29983', '01', N'Ấp Long Bình'),
	('87', '870', '29983', '02', N'Ấp Long Thái'),
	('87', '870', '29983', '03', N'Ấp Long Châu'),
	('87', '870', '29992', '01', N'Ấp Long Hưng'),
	('87', '870', '29992', '02', N'Ấp Long Thới A'),
	('87', '870', '29992', '03', N'Ấp Long Thới B'),
	('87', '870', '29992', '04', N'Ấp Long Hỏa'),
	('87', '870', '29992', '05', N'Ấp Long Thạnh'),
	('87', '870', '29995', '01', N'Ấp Phú Lợi A'),
	('87', '870', '29995', '02', N'Ấp Phú Lợi B'),
	('87', '870', '29995', '03', N'Ấp Phú Trung'),
	('87', '870', '29998', '01', N'Ấp Phú Hòa A'),
	('87', '870', '29998', '02', N'Ấp Phú Hòa B'),
	('87', '870', '29998', '03', N'Ấp Phú Thạnh B'),
	('87', '870', '29998', '04', N'Ấp Phú Thạnh A');