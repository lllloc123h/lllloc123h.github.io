<template>
  <div class="row">
    <div id="Sidebar" class="sidebar col-sm-3">
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
    <RouterView class="col-lg-9 view"></RouterView>
  </div>
</template> 

<script setup>
import { ref } from 'vue'

const isVisible = ref(Array(12).fill(false))

function toggleList(index) {
  isVisible.value[index] = !isVisible.value[index]
}

const menuItems = [
  /*Danh mục*/{
    title: 'DANH MỤC',
    children: [
      {name:'Danh mục loại phiếu',
        path:'/LoaiPhieu'
      }, {
        name:'Danh mục loại hình',
        path:'/LoaiHinh'
      }, {
        name:'Danh mục loại văn bản',
        path:'/LoaiVanBan'
      },
      {
        name:'Quy Mô',
        path:'/QuyMo'
      }, 
      {
        name:'Sản Phẩm',
        path:'/SanPham'

      }, 
      {
        name:'Vật nuôi',
        path:'/VatNuoi'

      },
      {
        name:'Tỉnh',
        path:'/Tinh'
      }, 
      {
        name:'Huyện',
        path:'/Huyen'
      },
      {
        name:'Xã',
        path:'/Xa'
      } , 
      {
        name:'Ấp',
        path:'/Ap'
      }
    ]
  },
  /*Cập nhật địa bàn*/{
    title: 'CẬP NHẬT ĐỊA BÀN',
    children: [
      {
        name:'Danh sách địa bàn',
        path:'/DSDiaBan'
      },
      {
        name:'Chọn mẫu địa bàn',
        path:'/MauDiaban'
      },{
        name:'Duyệt mẫu địa bàn',
        path:'/DuyetDiaBan'
      },]
  },
  /*Câp nhật bảng kê*/{
    title: 'CẬP NHẬT BẢNG KÊ',
    children: [
      {
        name:'Bảng kê số 1',
        path:'/BangKe01'
      },
      {
        name:'Bảng kê số 2',
        path:'/BangKe02'
      }, 
      {
        name:'Bảng kê số 5',
        path:'/BangKe05'
      },
      {
        name:'Bảng kê số 6',
        path:'/BangKe06'
      },
      {
        name:'Bảng kê trâu bò nhỏ',
        path:'/BangKeTrauBoNho'
      },
      {
        name:'Bảng kê vật nuôi khác',
        path:'/BangKevatNuoiKhac'
      },
    ]
  },
  /*Chọn mẫu*/{
    title: 'CHỌN MẪU',
    children: [
      {
        name:'Bảng phân bố cỡ mẫu',
        path:'/PhanBoCoMau'
      },
      {
        name:'Chọn mẫu hộ',
        path:'/ChonMauHo'
      },
      {
        name:'Chọn mẫu hộ vật nuôi đặt thù',
        path:'/ChonMauHoDacThu'
      },
      {
        name:'Thống kê số lượng mẫu',
        path:'/ThongKeSL'
      },
      
    ]
  },
  /*Quản lý điều tra*/{
    title: 'QUẢN LÝ KỲ ĐIỀU TRA',
    children: [
      {name:'Quản lý kỳ điều tra',
        path:'/QuanLyKi'
      }, 
      {name:'Chuyển dữ liệu kỳ điều tra',
        path:'ChuyenDoiDuLieu'
      },
      {name:'Theo dõi tiến độ',
        path:'/TheoDoiTienDo'
      },
      {
        name:'Quản lý thông báo',
        path:'/QuanLyTB'
      },
     
    ]
  },
  /*CẬP NHẬT BỔ SUNG*/{
    title: 'CẬP NHẬT BỔ SUNG',
    children: [
     {
        name:'Thay thế hộ mất mẫu',
        path:'/ThayTheHoMatMau'
      }, 
      {
        name:'Cập nhật hộ quy mô nhỏ',
        path:'/CapNhatHoQuyMoNho'
      },
      {
        name:'Cập nhật hộ quy mô lớn',
        path:'/CapNhatHoQuyMoLon'
      },
      {
        name:'Cập nhật hộ điều tra',
        path:'/DanhSachHoDieuTra'
      },
    ]
  },
  /*QUẢN LÝ ĐIỀU TRA CAPI*/{
    title: 'QUẢN LÝ ĐIỀU TRA CAPI',
    children: [
      {
        name:'Quản lý điều tra viên',
        path:'/DanhSachHoDieuTra'
      },
       {
        name:'Phân quyền điều tra',
        path:'/PhanQuyenDieuTra'
      },
      {
        name:'Theo dõi phân quyền',
        path:'/TheoDoiPhanQuyen'
      }, 
      {
        name: 'Theo dõi tiến độ',
        path:'/TheoDoiTienDo'
      },
    ]
  },
  {
    title: 'QUẢN LÝ ĐIỀU TRA WEB',
    children: [
      {
        name: 'Quản lý tài khoản',
        path:'/QuanLyTaiKhoan'
      },
      {
        name: 'Theo dõi tiến độ',
        path:'/TheoDoiTienDoWeb'
      },]
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
