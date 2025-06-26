<script setup>
import Login from './components/Login.vue';
import loaiVanban from './components/DanhMuc/loaivanBan.vue';
import SidebarMenu from './components/SidebarMenu.vue';
import Header from './components/header.vue';
import { RouterView } from 'vue-router';
</script>

<template>
  <Header></Header>
  <div>
    <RouterView></RouterView>

  </div>

</template>
<script setup></script>
