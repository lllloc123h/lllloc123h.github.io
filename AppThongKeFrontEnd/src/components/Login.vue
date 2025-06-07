<template>
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <div class="card p-4">
                <h3 class="text-center mb-4 title">Đăng nhập hệ thống</h3>


                <form @submit.prevent="handleLogin">
                    <div class="mb-3">
                        <label for="username" class="form-label">Tên đăng nhập</label>
                        <input type="text" class="form-control" id="username" name="username" v-model="user.username"
                            required autofocus>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control" id="password" name="password"
                            v-model="user.password" required>
                    </div>

                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary btn-login">Đăng nhập</button>
                    </div>
                </form>

            </div>
        </div>
    </div>

</template>
<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'
import axios from 'axios';

const user = reactive({
    userName: 'NhutKha',
    password: 'Khannps39199@',
})
async function handleLogin() {
    console.log(user)
    await axios.post(`http://localhost:8080/login`, user);
} 
</script>
<style scoped>
body {
    background: linear-gradient(to right, #74ebd5, #ACB6E5);
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: background 0.5s ease-in-out;
}

.card {
    border-radius: 1.5rem;
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
    animation: fadeInUp 1s ease;
    transition: all 0.4s ease-in-out;
}

.card:hover {
    transform: scale(1.02);
    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.2);
}

.form-control {
    border-radius: 0.75rem;
    transition: box-shadow 0.3s ease;
}

.form-control:focus {
    box-shadow: 0 0 10px rgba(0, 123, 255, 0.4);
}

.btn-login {
    border-radius: 0.75rem;
    padding: 10px;
    font-weight: 600;
    transition: background-color 0.3s ease;
}

.btn-login:hover {
    background-color: #0d6efd;
    color: white;
}

@keyframes fadeInUp {
    from {
        transform: translateY(30px);
        opacity: 0;
    }

    to {
        transform: translateY(0);
        opacity: 1;
    }
}

.title {
    font-weight: bold;
    background: linear-gradient(to right, #4facfe, #00f2fe);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    animation: fadeInDown 1s ease;
}

@keyframes fadeInDown {
    from {
        transform: translateY(-30px);
        opacity: 0;
    }

    to {
        transform: translateY(0);
        opacity: 1;
    }
}
</style>
