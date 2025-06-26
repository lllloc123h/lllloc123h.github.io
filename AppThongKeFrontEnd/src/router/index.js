import { createRouter, createWebHistory, createWebHashHistory } from "vue-router";
import Login from "../components/Login.vue";
import Huyen from "../components/DanhMuc/Huyen.vue";
import Tinh from "../components/DanhMuc/ThanhPho.vue";
import Xa from "../components/DanhMuc/Xa.vue";
import ThonAp from "../components/DanhMuc/ThonAp.vue";
import Loaihinh from "../components/DanhMuc/Loaihinh.vue";
import LoaiPhieu from "../components/DanhMuc/LoaiPhieu.vue";
import LoaivanBan from "../components/DanhMuc/loaivanBan.vue";
import QuyMo from "../components/DanhMuc/Quymo.vue";
import SanPham from "../components/DanhMuc/SanPham.vue";
import VatNuoi from "../components/DanhMuc/VatNuoi.vue";
import SidebarMenu from "../components/SidebarMenu.vue";

const routes = [
  {
    path: "/",
    component: SidebarMenu, // Chuyển hướng đến một trang mặc định khi vào '/'
    children: [
      // Các route con này sẽ được render vào <RouterView> bên trong SidebarMenu
      // (SidebarMenu được render bởi App.vue)
      {
        path: "login", // Đường dẫn tương đối: /login
        name: "Login", // Đặt tên cho route để dễ quản lý
        component: Login,
      },
      {
        path: "Huyen",
        name: "Huyen",
        component: Huyen,
      },
      {
        path: "LoaiVanBan",
        name: "LoaiVanBan",
        component: LoaivanBan,
      },
      {
        path: "LoaiHinh",
        name: "LoaiHinh",
        component: Loaihinh,
      },
      {
        path: "LoaiPhieu",
        name: "LoaiPhieu",
        component: LoaiPhieu,
      },
      {
        path: "Tinh",
        name: "Tinh",
        component: Tinh,
      },
      {
        path: "Xa",
        name: "Xa",
        component: Xa,
      },
      {
        path: "Ap", // Nên đổi tên là Ap để khớp với menuItems của bạn
        name: "Ap",
        component: ThonAp,
      },
      {
        path: "QuyMo",
        name: "QuyMo",
        component: QuyMo,
      },
      {
        path: "SanPham",
        name: "SanPham",
        component: SanPham,
      },
      {
        path: "VatNuoi",
        name: "VatNuoi",
        component: VatNuoi,
      },
      {
        path: "DSDiaBan",
        name: "DSDiaBan",
        component: () => import('../components/CapNhatDiaBan/DSDiaBan.vue')
      },
      {
        path: "MauDiaBan",
        name: "MauDiaBan",
        component: () => import('../components/CapNhatDiaBan/MauDiaBan.vue')
      },
      {
        path: "DuyetDiaBan",
        name: "DuyetDiaBan",
        component: () => import('../components/CapNhatDiaBan/DuyetDiaBan.vue')
      },
      {
        path: "BangKe01",
        name: "BangKe01",
        component: () => import('../components/CapNhatBangKe/BangKe01.vue')
      },
      {
        path: "BangKe02",
        name: "BangKe02",
        component: () => import('../components/CapNhatBangKe/BangKe02.vue')
      },
      {
        path: "BangKe05",
        name: "BangKe05",
        component: () => import('../components/CapNhatBangKe/BangKe05.vue')
      },
      {
        path: "BangKe06",
        name: "BangKe06",
        component: () => import('../components/CapNhatBangKe/BangKe06.vue')
      },
      {
        path: "BangKeTrauBoNho",
        name: "BangKeTrauBoNho",
        component: () => import('../components/CapNhatBangKe/BangKeTrauBoNho.vue')
      },
      {
        path: "BangKevatNuoiKhac",
        name: "BangKevatNuoiKhac",
        component: () => import('../components/CapNhatBangKe/BangKevatNuoiKhac.vue')
      },
      {
        path: "PhanBoCoMau",
        name: "PhanBoCoMau",
        component: () => import('../components/Mau/PhanBoCoMau.vue')
      },
      {
        path: "ChonMauHo",
        name: "ChonMauHo",
        component: () => import('../components/Mau/ChonMauHo.vue')
      },
      {
        path: "ChonMauHoDacThu",
        name: "ChonMauHoDacThu",
        component: () => import('../components/Mau/ChonMauHoDacThu.vue')
      },
      {
        path: "ThongKeSL",
        name: "ThongKeSL",
        component: () => import('../components/Mau/ThongKeSL.vue')
      },
      {
        path: "QuanLyKi",
        name: "QuanLyKi",
        component: () => import('../components/QuanLyKiDieuTra/QuanLyKi.vue')
      },
      {
        path: "DuyetDuLieu",
        name: "DuyetDuLieu",
        component: () => import('../components/QuanLyKiDieuTra/DuyetDuLieu.vue')
      },
      {
        path: "ChuyenDoiDuLieu",
        name: "ChuyenDoiDuLieu",
        component: () => import('../components/QuanLyKiDieuTra/ChuyenDoiDuLieu.vue')
      },
      {
        path: "QuanLyTB",
        name: "QuanLyTB",
        component: () => import('../components/QuanLyKiDieuTra/QuanLyThongBao.vue')
      },
      {
        path: "TheoDoiTienDo",
        name: "TheoDoiTienDo",
        component: () => import('../components/QuanLyKiDieuTra/TheoDoiTienDo.vue')
      },
      {
        path: "ThayTheHoMatMau",
        name: "ThayTheHoMatMau",
        component: () => import('../components/CapNhatBoSungMau/ThayTheHoMatMau.vue')
      },
      {
        path: "CapNhatHoQuyMoNho",
        name: "CapNhatHoQuyMoNho",
        component: () => import('../components/CapNhatBoSungMau/CapNhatHoQuyMoNho.vue')
      },
      {
        path: "CapNhatHoQuyMoLon",
        name: "CapNhatHoQuyMoLon",
        component: () => import('../components/CapNhatBoSungMau/CapNhatHoQuyMoLon.vue')
      },
      {
        path: "DanhSachHoDieuTra",
        name: "DanhSachHoDieuTra",
        component: () => import('../components/CapNhatBoSungMau/DanhSachHoDieuTra.vue')
      },
      {
        path: 'QuanLydieuTra',
        name: 'QuanLydieuTra',
        component: () => import('../components/CAPI/QuanLyDieuTraVien.vue')
      },
      {
        path: 'PhanQuyenDieuTra',
        name: 'PhanQuyenDieuTra',
        component: () => import('../components/CAPI/PhanQuyenDieuTra.vue')
      },
      {
        path: 'TheoDoiPhanQuyen',
        name: 'TheoDoiPhanQuyen',
        component: () => import('../components/CAPI/TheoDoiPhanQuyen.vue')
      },
      {
        path: 'QuanLyTaiKhoan',
        name: 'QuanLyTaiKhoan',
        component: () => import('../components/QuanLyDieuTraWeb/QuanLyTaiKhoan.vue')
      },
      {
        path: 'TheoDoiTienDoWeb',
        name: 'TheoDoiTienDoWeb',
        component: () => import('../components/QuanLyDieuTraWeb/TheoDoiTienDo.vue')
      },
      {
        path: "QuanLyKi",
        name: "QuanLyKi",
        component: () => import('../components/QuanLyKiDieuTra/QuanLyKi.vue')
      },
      {
        path: "DuyetDuLieu",
        name: "DuyetDuLieu",
        component: () => import('../components/QuanLyKiDieuTra/DuyetDuLieu.vue')
      },
      {
        path: "ChuyenDoiDuLieu",
        name: "ChuyenDoiDuLieu",
        component: () => import('../components/QuanLyKiDieuTra/ChuyenDoiDuLieu.vue')
      },
      {
        path: "QuanLyTB",
        name: "QuanLyTB",
        component: () => import('../components/QuanLyKiDieuTra/QuanLyThongBao.vue')
      },
      {
        path: "TheoDoiTienDo",
        name: "TheoDoiTienDo",
        component: () => import('../components/QuanLyKiDieuTra/TheoDoiTienDo.vue')
      },
      {
        path: "ThayTheHoMatMau",
        name: "ThayTheHoMatMau",
        component: () => import('../components/CapNhatBoSungMau/ThayTheHoMatMau.vue')
      },
      {
        path: "CapNhatHoQuyMoNho",
        name: "CapNhatHoQuyMoNho",
        component: () => import('../components/CapNhatBoSungMau/CapNhatHoQuyMoNho.vue')
      },
      {
        path: "CapNhatHoQuyMoLon",
        name: "CapNhatHoQuyMoLon",
        component: () => import('../components/CapNhatBoSungMau/CapNhatHoQuyMoLon.vue')
      },
      {
        path: "DanhSachHoDieuTra",
        name: "DanhSachHoDieuTra",
        component: () => import('../components/CapNhatBoSungMau/DanhSachHoDieuTra.vue')
      },
      {
        path: 'QuanLydieuTra',
        name: 'QuanLydieuTra',
        component: () => import('../components/CAPI/QuanLyDieuTraVien.vue')
      },
      {
        path: 'PhanQuyenDieuTra',
        name: 'PhanQuyenDieuTra',
        component: () => import('../components/CAPI/PhanQuyenDieuTra.vue')
      },
      {
        path: 'TheoDoiPhanQuyen',
        name: 'TheoDoiPhanQuyen',
        component: () => import('../components/CAPI/TheoDoiPhanQuyen.vue')
      },
      {
        path: 'QuanLyTaiKhoan',
        name: 'QuanLyTaiKhoan',
        component: () => import('../components/QuanLyDieuTraWeb/QuanLyTaiKhoan.vue')
      },
      {
        path: 'TheoDoiTienDoWeb',
        name: 'TheoDoiTienDoWeb',
        component: () => import('../components/QuanLyDieuTraWeb/TheoDoiTienDo.vue')
      },
    ],
  },
  // Nếu bạn có các route không thuộc layout sidebar (ví dụ: trang login riêng biệt, không có sidebar)
  // bạn có thể định nghĩa chúng ở ngoài mảng children này.
  // Ví dụ, nếu trang login không có sidebar:
  // {
  //   path: '/login', // Đường dẫn tuyệt đối
  //   name: 'LoginOutsideLayout',
  //   component: Login,
  // },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;