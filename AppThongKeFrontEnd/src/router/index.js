import { createRouter, createWebHistory } from "vue-router";

// Import modular route arrays
// import adminRoutes from './Admin/AdminRouter.js'
// Không cần import App ở đây
import Login from "../components/Login.vue";
import Huyen from "../components/DanhMuc/Huyen.vue";
// Đổi tên biến để tránh trùng lặp, dùng Tinh cho component ThanhPho.vue
import Tinh from "../components/DanhMuc/ThanhPho.vue"; // Sử dụng tên Tinh cho ThanhPho.vue
import Xa from "../components/DanhMuc/Xa.vue";
import ThonAp from "../components/DanhMuc/ThonAp.vue";
import Loaihinh from "../components/DanhMuc/Loaihinh.vue";
import LoaiPhieu from "../components/DanhMuc/LoaiPhieu.vue";
import LoaivanBan from "../components/DanhMuc/loaivanBan.vue";
import QuyMo from "../components/DanhMuc/Quymo.vue";
import SanPham from "../components/DanhMuc/SanPham.vue";
import VatNuoi from "../components/DanhMuc/VatNuoi.vue";
// Loại bỏ import trùng lặp ThanhPho nếu đã import Tinh
// import ThanhPho from "../components/DanhMuc/ThanhPho.vue"; // Đã được import là Tinh
import SidebarMenu from "../components/SidebarMenu.vue";

// Merge all routes
const routes = [
  {
    path: "/",
    // LOẠI BỎ DÒNG NÀY: component: SidebarMenu,
    // Thay vào đó, chúng ta sẽ chuyển hướng đến một route con mặc định
    redirect: "/LoaiPhieu", // Chuyển hướng đến một trang mặc định khi vào '/'
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
  history: createWebHistory(),
  routes,
});

export default router;