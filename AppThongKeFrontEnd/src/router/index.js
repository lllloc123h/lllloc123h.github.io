import { createRouter, createWebHistory } from 'vue-router'

// Import modular route arrays
import adminRoutes from './Admin/AdminRouter.js'
import App from '../App.vue'
import Login from '../components/Login.vue'
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
    }

]

const router = createRouter({
    history: createWebHistory(),
    routes
})


export default router
