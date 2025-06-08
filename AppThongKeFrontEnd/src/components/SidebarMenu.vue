<template>
  <div id="Sidebar" class="sidebar">
    <ul id="menu">
      <li class="ItemMenu" v-for="(item, index) in menuItems" :key="index">
        <button class="menu-btn" @click="toggleList(index)">
          <h5>{{ item.title }}</h5>
          <span class="arrow" :class="{ open: isVisible[index] }">▼</span>
        </button>
        <transition name="fade">
          <ul class="list" v-show="isVisible[index]">
            <li class="Item" v-for="(child, i) in item.children" :key="i">
              <router-link :to="child.path" class="link">{{ child.name }}</router-link>
            </li>
          </ul>
        </transition>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const isVisible = ref(Array(12).fill(false))

function toggleList(index) {
  isVisible.value[index] = !isVisible.value[index]
}

const menuItems = [
  {
    title: 'DANH MỤC',
    children: [
      {name:'Danh mục loại phiếu',
        path:'/Huyen'
      }, {
        name:'Danh mục loại hình',
        path:'/LoaiHinh'
      }, {
        name:'Danh mục loại văn bản',
        path:'/LoaiVanBan'
      },
      {
        name:'Quy Mô',
        path:'/LoaiVanBan'
      }, 
      {
        name:'Sản Phẩm',
        path:'/LoaiVanBan'

      }, 
      {
        name:'Vật nuôi',
        path:'/LoaiVanBan'

      },
      {
        name:'Tỉnh',
        path:'/LoaiVanBan'
      }, 
      {
        name:'Huyện',
        path:'/LoaiVanBan'
      },
      {
        name:'Xã',
        path:'/LoaiVanBan'
      } , 
      {
        name:'Ấp',
        path:'/LoaiVanBan'
      }
    ]
  },
  {
    title: 'CẬP NHẬT ĐỊA BÀN',
    children: ['Danh sách địa bàn', 'Chọn mẫu địa bàn', 'Duyệt mẫu địa bàn']
  },
  {
    title: 'CẬP NHẬT BẢNG KÊ',
    children: [
      'Bảng kê 01', 'Bảng kê 02', 'Bảng kê 03', 'Bảng kê 04', 'Bảng kê 05', 'Bảng kê 06',
      'Bảng kê hộ trâu bò nhỏ', 'Bảng kê hộ vật nuôi khác', 'Theo dõi số lượng hộ theo quy mô nhỏ', 'Tổng hợp số lượng bảng kê'
    ]
  },
  {
    title: 'CHỌN MẪU',
    children: [
      'Bảng phân bố cơ mẫu', 'Chọn mẫu hộ', 'Chọn mẫu hộ nuôi vật nuôi đặc thù',
      'Thống kê số lượng mẫu', 'Duyệt chuyển điều tra'
    ]
  },
  {
    title: 'QUẢN LÝ KỲ ĐIỀU TRA',
    children: [
      'Quản lý kỳ điều tra', 'Chuyển dữ liệu kỳ điều tra', 'Theo dõi tiến độ',
      'Quản lý thông báo', 'Duyệt dữ liệu'
    ]
  },
  {
    title: 'CẬP NHẬT BỔ SUNG',
    children: [
      'Thay thế hộ mất mẫu', 'Cập nhật hộ quy mô nhỏ', 'Cập nhật hộ quy mô lớn',
      'Cập nhật hộ điều tra', 'Cập nhật thông tin Doanh nghiệp, HTX'
    ]
  },
  {
    title: 'QUẢN LÝ ĐIỀU TRA CAPI',
    children: [
      'Quản lý điều tra viên', 'Phân quyền điều tra', 'Theo dõi phân quyền', 'Theo dõi tiến độ'
    ]
  },
  {
    title: 'QUẢN LÝ ĐIỀU TRA WEB',
    children: ['Quản lý tài khoản', 'Theo dõi tiến độ']
  },
  {
    title: 'KIỂM TRA LOGIC PHIẾU',
    children: [
      'Phiếu số 01', 'Phiếu số 02', 'Phiếu số 03', 'Phiếu số 04', 'Phiếu số 05', 'Phiếu số 06',
      'Theo dõi số lượng Logic'
    ]
  },
  {
    title: 'XUẤT DỮ LIỆU VI MÔ',
    children: [
      'Phiếu số 01', 'Phiếu số 02', 'Phiếu số 03', 'Phiếu số 04', 'Phiếu số 05', 'Phiếu số 06',
      'Chi nhánh phiếu số 01', 'Chi nhánh phiếu số 04', 'Danh mục câu hỏi', 'Nhật ký xuất dữ liệu'
    ]
  },
  {
    title: 'TỔNG HỢP SỐ LIỆU',
    children: [
      'Cập nhật số hộ huyện', 'Cập nhật số hộ địa bàn', 'Tổng hợp số liệu',
      'Xem biểu đồ tổng hợp theo kỳ', 'Xem biểu tổng hợp theo năm'
    ]
  },
  {
    title: 'TIỆN ÍCH',
    children: ['Quản lý văn bản', 'Tài liệu nghiệp vụ', 'Biểu mẫu bảng kê']
  }
]
</script>

<style scoped>
.sidebar {
  background-color: #1c2b4a;
  color: #ffffff;
  padding: 20px;
  width: 100%;
  max-width: 300px;
  border-radius: 10px;
}

.menu-btn {
  background-color: #2e3e5c;
  color: white;
  width: 100%;
  text-align: left;
  padding: 10px 15px;
  border: none;
  border-radius: 8px;
  margin-bottom: 5px;
  position: relative;
  cursor: pointer;
}

.menu-btn:hover {
  background-color: #3f5377;
}

.arrow {
  float: right;
  transition: transform 0.3s ease;
}

.arrow.open {
  transform: rotate(180deg);
}
.ItemMenu{
    list-style-type: none;
    margin-right: 5%;
}
.list {
  background-color: #34465d;
  padding: 10px;
  margin-top: 5px;
  border-radius: 6px;
  
}

.Item {
  margin: 6px 0;
  list-style-type: none;
}

.link {
  color: #ffffff;
  text-decoration: none;
  transition: color 0.2s;
  padding-left: 10px;
}

.link:hover {
  color: #50b7f5;
}
#menu{
    padding: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
