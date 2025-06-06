create database ThongKe_ChanNuoi;
use ThongKe_ChanNuoi;
--drop database ThongKe_ChanNuoi
	create table loai_phieu(
		ma_phieu int identity(1,1) primary key,
		ten_phieu nvarchar(255)
	)
	insert into loai_phieu(ten_phieu) values
	(N'phiếu số 01'),
	(N'phiếu số 02'),
	(N'phiếu số 03')
	create table loai_hinh_doanh_nghiep(
		ma_loai_hinh int identity(1,1) primary key,
		ten_loai_hinh nvarchar(255)
	)
	insert into loai_hinh_doanh_nghiep(ten_loai_hinh) values
	(N'Doanh nghiệp ngoài nhà nước'),
	(N'Doanh nghiệp FDI'),
	(N'Họp tác xã'),
	(N'Tổ chức khác thuộc khu vực Nhà nước'),
	(N'Tổ chức khác thuộc khu vực Nhà nước(cá thể)')

	create table loai_van_ban(
		ma_loai_van_ban int identity(1,1) primary key,
		ten_ma_loai_van_ban nvarchar(255) not null,
		ghi_chu nvarchar(255)
	)
	insert into loai_van_ban(ten_ma_loai_van_ban,ghi_chu) values
	(N'Phiếu điểu tra',''),
	(N'Phương án điều tra',''),
	(N'Các phụ luc hướng dẫn ',''),
	(N'Thông báo nghiệp vụ','')

	create table vat_nuoi(
		Id int Identity(1,1) primary key,
		ten_vat_nuoi nvarchar(255),
		ma_vat_nuoi nvarchar(255),
		nhom_khac bit
	)

	insert into vat_nuoi(ten_vat_nuoi,ma_vat_nuoi,nhom_khac) values
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

	create table sp_chan_nuoi(
		ma_sp_chan_nuoi int identity(1,1) primary key,
		ten_sp_chan_nuoi nvarchar(255),
		gia_cam bit
	)
	INSERT INTO sp_chan_nuoi (ten_sp_chan_nuoi, gia_cam) VALUES
	(N'Sản phẩm chăn nuôi trâu, bò', 0),
	(N'Sản phẩm chăn nuôi ngựa, lừa, la', 1),
	(N'Sản phẩm chăn nuôi dê, cừu, hươu, nai', 1),
	(N'Sản phẩm chăn nuôi lợn', 0),
	(N'Sản phẩm chăn nuôi gia cầm', 1),
	(N'Sản phẩm chăn nuôi vịt, ngan, ngỗng', 1),
	(N'Sản phẩm chăn nuôi gia cầm khác', 1),
	(N'Sản phẩm chăn nuôi khác', 1);
	--drop table Huyen
	create table huyen(
		Id int identity(1,1) primary key,
		ma_tinh varchar(5),
		ma_huyen varchar(10),
		ten_huyen nvarchar (255)
	)
	-- Thêm dữ liệu vào bảng Huyen dựa trên dữ liệu cung cấp (gộp nhiều VALUES)
	INSERT INTO huyen (ma_tinh, ma_huyen, ten_huyen) VALUES
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

	--drop table Users
	create table users(
	Id int identity(1,1) primary key,
	User_name varchar(255) unique not null,
	Password varchar(255) not null,
	User_admin bit
	)

	insert into users(User_name, Password, User_admin) values
	('HuuLoc','123', 1),
	('NhatKha','123',0)
	--drop table QuyMo
	create table quy_mo(
		Id varchar(10) primary key,
		quy_mo_name nvarchar(255) not null,
		vat_nuoi nvarchar(255) not null
	)
	INSERT INTO quy_mo (Id, quy_mo_name, vat_nuoi) VALUES
('BS01', N'Nuôi bò sữa từ 1 đến 19 con', N'Bò'),
('BS02', N'Nuôi bò sữa từ 20 con trở lên', N'Bò'),
('BT01', N'Nuôi bò khác từ 1 đến 29 con', N'Bò'),
('BT02', N'Nuôi bò khác từ 30 con trở lên', N'Bò'),
('G01', N'Nuôi gà từ 1 đến 49 con', N'Gà'),
('G02', N'Nuôi gà từ 50 đến 199 con', N'Gà'),
('G03', N'Nuôi gà từ 200 đến 999 con', N'Gà'),
('G04_1', N'Nuôi gà thịt từ 1000 đến 3999 con', N'Gà'),
('G04_2', N'Nuôi gà đẻ trứng từ 1000 đến 3999 con', N'Gà'),
('G05', N'Nuôi gà từ 4000 con trở lên', N'Gà'),
('L01', N'Nuôi lợn từ 1 đến 9 con', N'Lợn'),
('L02', N'Nuôi lợn từ 10 đến 29 con', N'Lợn'),
('L03', N'Nuôi lợn từ 30 đến 99 con', N'Lợn'),
('L04', N'Nuôi lợn từ 100 đến 199 con', N'Lợn'),
('L05', N'Nuôi lợn từ 200 đến 299 con', N'Lợn'),
('L06', N'Nuôi lợn từ 300 con trở lên', N'Lợn'),
('N01', N'Nuôi ngan từ 1 đến 49 con', N'Ngan'),
('N02', N'Nuôi ngan từ 50 đến 199 con', N'Ngan'),
('N03', N'Nuôi ngan từ 200 đến 499 con', N'Ngan'),
('N04', N'Nuôi ngan từ 500 con trở lên', N'Ngan'),
('Tr01', N'Nuôi trâu từ 1 đến 29 con', N'Trâu'),
('Tr02', N'Nuôi trâu từ 30 con trở lên', N'Trâu'),
('V01', N'Nuôi vịt từ 1 đến 49 con', N'Vịt');

create table xa(
	Id int identity(1,1) primary key,
	Ma_tinh varchar(2),
	Ma_huyen varchar(10),
	Ma_xa varchar(10),
	Ten_xa nvarchar(255),
	Loai bit
)

-- Chèn dữ liệu mẫu vào bảng xa
INSERT INTO xa (Ma_tinh, Ma_huyen, Ma_xa, Ten_xa, Loai) VALUES
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
('87', '876', '29920', N'Xã Tân Quy Tây', 0); -- Nông thôn
--drop table ap
create table ap(
	id int identity(1,1) primary key,
	Ma_tinh nvarchar(255),
	Ma_huyen nvarchar(255),
	Ma_xa nvarchar(255),
	Ma_ap nvarchar(255),
	Ten_ap nvarchar(255)
)
INSERT INTO ap (Ma_tinh, Ma_huyen, Ma_xa, Ma_ap, Ten_ap) VALUES
('87', '870', '29956', '01', N'Ap 1'),
('87', '870', '29956', '02', N'Ap 2'),
('87', '870', '29956', '03', N'Ap 3'),
('87', '870', '29956', '04', N'Ấp Chôm Xoài'),
('87', '870', '29956', '05', N'Ấp Giồng Bàng'),
('87', '870', '29962', '01', N'Ấp Bình Hòa Thương'),
('87', '870', '29962', '02', N'Ấp Bình Hòa Trung'),
('87', '870', '29971', '00', N'Thị trấn Thường Thới Tiền'),
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
