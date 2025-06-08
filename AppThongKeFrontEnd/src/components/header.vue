<template>
  <header class="header">
    <h1 class="title">CHI CỤC THỐNG KÊ TỈNH ĐỒNG THÁP</h1>
    <button class="logout-button" @click="logout">Đăng xuất</button>
  </header>
</template>

<script setup>
function logout() {
  // Gọi API logout hoặc xóa token, sau đó chuyển hướng về trang login
  console.log("Đăng xuất")
  // Ví dụ điều hướng:
  // window.location.href = "/login"
}
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #34465d;
  padding: 10px 20px;
  color: white;
  border-bottom: 2px solid #2c3e50;
}

.title {
  font-size: 1.5rem;
  font-weight: bold;
  text-align: center;
  flex: 1;
}

.logout-button {
  background-color: #ff4d4f;
  color: white;
  border: none;
  padding: 8px 16px;
  font-size: 0.95rem;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.logout-button:hover {
  background-color: #e60000;
}
</style>
