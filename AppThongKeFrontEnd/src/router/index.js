import { createRouter, createWebHistory } from 'vue-router'

// Import modular route arrays
import adminRoutes from './Admin/AdminRouter.js'
import App from '../App.vue'
import Login from '../components/Login.vue'
import Huyen from '../components/DanhMuc/Huyen.vue'
import Tinh from '../components/DanhMuc/ThanhPho.vue'
import Xa from '../components/DanhMuc/Xa.vue'
import ThonAp from '../components/DanhMuc/ThonAp.vue'
import Loaihinh from '../components/DanhMuc/Loaihinh.vue'
import LoaiPhieu from '../components/DanhMuc/LoaiPhieu.vue';
import LoaivanBan from '../components/DanhMuc/loaivanBan.vue';
import QuyMo from '../components/DanhMuc/Quymo.vue'
import SanPham from '../components/DanhMuc/SanPham.vue'
import VatNuoi from '../components/DanhMuc/VatNuoi.vue'
// Merge all routes
const routes = [
    {
        path: '/',
        component: App,
        children: [
            ...adminRoutes,
        ]
    },
    {
        path: '/login',
        component: Login,
    },
    {
        path: '/',
        component: Login,
    },
    {
        path: '/Huyen',
        component: Huyen,
    },
    {
        path: '/LoaiVanban',
        component: LoaivanBan,
    },

]

const router = createRouter({
    history: createWebHistory(),
    routes
})


export default router
