import { createRouter, createWebHistory } from 'vue-router'

// Import modular route arrays
import adminRoutes from './Admin/AdminRouter.js'
import userRoutes from './User/UserRouter.js'
import authService from '../api/header-injectable-JWT.js'
import adminLayout from '../Layouts/AdminLayout.vue'
import UserLayout from '../Layouts/UserLayout.vue'
import Oauth2 from '../components/Module/Oauth2Success.vue'
import Unauthorized from '../components/Module/403View.vue'
import App from '../App.vue'
// Merge all routes
const routes = [
    {
        path: '/',
        component: UserLayout,
        children: [
            ...userRoutes,
        ]
    },
    {
        path: '/',
        component: App,
        children: [
            ...adminRoutes,
        ]
    }

]

const router = createRouter({
    history: createWebHistory(),
    routes
})


export default router
