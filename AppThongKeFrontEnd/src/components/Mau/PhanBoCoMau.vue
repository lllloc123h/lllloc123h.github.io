<template>
  <div class="phan-bo-co-cau-page">
    <div class="header-section">
      <h2 class="page-title">Phân bổ cỡ mẫu</h2>
      <div class="action-buttons">
        <button class="btn btn-export-excel" @click="exportExcel">Xuất Excel</button>
      </div>
    </div>

    <div class="filter-section">
      <div class="filter-group">
        <label for="year">Năm</label>
        <select id="year" v-model="filters.nam">
          <option value="">-- Chọn năm --</option>
          <option value="2025">2025</option>
          <option value="2024">2024</option>
          <option value="2023">2023</option>
        </select>
      </div>
      <div class="filter-group">
        <label for="province">Tỉnh/TP</label>
        <select id="province" v-model="filters.tinhTP">
          <option value="">-- Chọn Tỉnh/TP --</option>
          <option value="87-DongThap">87- Đồng Tháp</option>
          <option value="92-CanTho">92- Cần Thơ</option>
          <option value="89-AnGiang">89- An Giang</option>
          <option value="91-KienGiang">91- Kiên Giang</option>
        </select>
      </div>
      </div>

    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th>Mã Tỉnh</th>
            <th>Tên Tỉnh</th>
            <th>Thời điểm thực hiện</th>
            <th>Người thực hiện</th>
            <th>Trạng thái</th>
            <th>Tổng số địa bàn mẫu</th>
            <th>Tổng số hộ</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading" class="loading-row">
            <td :colspan="7">Đang tải dữ liệu...</td>
          </tr>
          <tr v-else-if="filteredData.length === 0" class="no-data-row">
            <td :colspan="7">Không có dữ liệu phù hợp.</td>
          </tr>
          <tr v-else v-for="(item, index) in filteredData" :key="index">
            <td>{{ item.maTinh }}</td>
            <td>{{ item.tenTinh }}</td>
            <td>{{ item.thoiDiemThucHien }}</td>
            <td>{{ item.nguoiThucHien }}</td>
            <td>{{ item.trangThai }}</td>
            <td>{{ item.tongSoDiaBanMau }}</td>
            <td>{{ item.tongSoHo }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';

const filters = ref({
  nam: '2025',
  tinhTP: '87-DongThap',
});

const data = ref([]);
const loading = ref(true);
const filteredData = ref([]);

const fetchTableData = async () => {
  loading.value = true;
  await new Promise(resolve => setTimeout(resolve, 500)); // Simulate API call delay

  const mockData = [
    {
      nam: 2025,
      maTinh: 87,
      tenTinh: 'Đồng Tháp',
      thoiDiemThucHien: '8:58:363 - 18/12/2024',
      nguoiThucHien: 'ngthuycth',
      trangThai: 'Đã duyệt',
      tongSoDiaBanMau: 150,
      tongSoHo: 3000
    },
    {
      nam: 2025,
      maTinh: 92,
      tenTinh: 'Cần Thơ',
      thoiDiemThucHien: '9:15:000 - 19/12/2024',
      nguoiThucHien: 'nvlongct',
      trangThai: 'Chờ duyệt',
      tongSoDiaBanMau: 120,
      tongSoHo: 2500
    },
    {
      nam: 2024,
      maTinh: 87,
      tenTinh: 'Đồng Tháp',
      thoiDiemThucHien: '10:00:000 - 15/01/2024',
      nguoiThucHien: 'ngthuycth',
      trangThai: 'Đã duyệt',
      tongSoDiaBanMau: 140,
      tongSoHo: 2800
    },
    {
      nam: 2024,
      maTinh: 92,
      tenTinh: 'Cần Thơ',
      thoiDiemThucHien: '10:00:000 - 15/01/2024',
      nguoiThucHien: 'nvlongct',
      trangThai: 'Đã duyệt',
      tongSoDiaBanMau: 110,
      tongSoHo: 2200
    },
    // Thêm 10 dòng dữ liệu mới
    {
      nam: 2025,
      maTinh: 89,
      tenTinh: 'An Giang',
      thoiDiemThucHien: '11:00:000 - 20/12/2024',
      nguoiThucHien: 'nthinhag',
      trangThai: 'Đã duyệt',
      tongSoDiaBanMau: 180,
      tongSoHo: 3500
    },
    {
      nam: 2025,
      maTinh: 91,
      tenTinh: 'Kiên Giang',
      thoiDiemThucHien: '12:30:000 - 21/12/2024',
      nguoiThucHien: 'ptuan kg',
      trangThai: 'Chờ duyệt',
      tongSoDiaBanMau: 200,
      tongSoHo: 4000
    },
    {
      nam: 2024,
      maTinh: 89,
      tenTinh: 'An Giang',
      thoiDiemThucHien: '14:00:000 - 16/01/2024',
      nguoiThucHien: 'nthinhag',
      trangThai: 'Đã duyệt',
      tongSoDiaBanMau: 170,
      tongSoHo: 3300
    },
    {
      nam: 2024,
      maTinh: 91,
      tenTinh: 'Kiên Giang',
      thoiDiemThucHien: '15:45:000 - 17/01/2024',
      nguoiThucHien: 'ptuan kg',
      trangThai: 'Đã duyệt',
      tongSoDiaBanMau: 190,
      tongSoHo: 3800
    },
    {
      nam: 2023,
      maTinh: 87,
      tenTinh: 'Đồng Tháp',
      thoiDiemThucHien: '09:00:000 - 10/02/2023',
      nguoiThucHien: 'ngthuycth',
      trangThai: 'Đã duyệt',
      tongSoDiaBanMau: 130,
      tongSoHo: 2600
    },
    {
      nam: 2023,
      maTinh: 92,
      tenTinh: 'Cần Thơ',
      thoiDiemThucHien: '10:30:000 - 11/02/2023',
      nguoiThucHien: 'nvlongct',
      trangThai: 'Đã duyệt',
      tongSoDiaBanMau: 100,
      tongSoHo: 2000
    },
    {
      nam: 2025,
      maTinh: 87,
      tenTinh: 'Đồng Tháp',
      thoiDiemThucHien: '16:00:000 - 22/12/2024',
      nguoiThucHien: 'ngthuycth',
      trangThai: 'Đã duyệt',
      tongSoDiaBanMau: 155,
      tongSoHo: 3100
    },
    {
      nam: 2025,
      maTinh: 92,
      tenTinh: 'Cần Thơ',
      thoiDiemThucHien: '17:30:000 - 23/12/2024',
      nguoiThucHien: 'nvlongct',
      trangThai: 'Từ chối',
      tongSoDiaBanMau: 115,
      tongSoHo: 2400
    },
    {
      nam: 2024,
      maTinh: 87,
      tenTinh: 'Đồng Tháp',
      thoiDiemThucHien: '11:15:000 - 18/01/2024',
      nguoiThucHien: 'ngthuycth',
      trangThai: 'Chờ duyệt',
      tongSoDiaBanMau: 145,
      tongSoHo: 2900
    },
    {
      nam: 2024,
      maTinh: 89,
      tenTinh: 'An Giang',
      thoiDiemThucHien: '13:00:000 - 19/01/2024',
      nguoiThucHien: 'nthinhag',
      trangThai: 'Đã duyệt',
      tongSoDiaBanMau: 175,
      tongSoHo: 3400
    },
];

  data.value = mockData;
  loading.value = false;
  applyFilters(); // Gọi applyFilters() ngay sau khi dữ liệu được tải
};

const applyFilters = () => {
  let tempFilteredData = data.value.filter(item => {
    const matchesNam = filters.value.nam === '' || String(filters.value.nam) === String(item.nam);
    const matchesTinhTP = filters.value.tinhTP === '' || filters.value.tinhTP.split('-')[0] === String(item.maTinh);
    return matchesNam && matchesTinhTP;
  });
  filteredData.value = tempFilteredData;
};

onMounted(() => {
  fetchTableData();
});

watch(filters, () => {
  // Khi filters thay đổi, chỉ cần áp dụng lại bộ lọc trên dữ liệu HIỆN CÓ
  // Không cần gọi fetchTableData() ở đây nữa nếu muốn lọc ngay lập tức trên mockData
  applyFilters();
}, { deep: true });


const exportExcel = () => {
  alert('Chức năng "Xuất Excel" cho Phân bổ cỡ mẫu');
};
</script>

<style scoped>
/* Các style tương tự như các bảng kê khác để giữ giao diện nhất quán */
.phan-bo-co-cau-page {
  padding: 20px;
  background-color: #f0f2f5;
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e0e0e0;
}

.page-title {
  color: #333;
  margin: 0;
  font-size: 24px;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.btn {
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  color: white;
  background-color: #007bff; /* Primary blue */
  transition: background-color 0.2s ease;
}

.btn:hover {
  opacity: 0.9;
}

.btn-export-excel {
  background-color: #17a2b8; /* Info blue */
}

/* Nút "Xem" đã được ẩn trong template, nhưng style vẫn còn nếu bạn muốn bật lại */
.btn-view {
  background-color: #6c757d; /* Grey */
  color: white;
}

/* Filter Section */
.filter-section {
  background-color: #ffffff;
  padding: 15px 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  margin-bottom: 20px;
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  align-items: flex-end;
}

.filter-group {
  display: flex;
  flex-direction: column;
  min-width: 120px;
}

.filter-group label {
  font-size: 13px;
  color: #555;
  margin-bottom: 5px;
}

.filter-group select {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  background-color: white;
  min-width: 100px;
}

/* Table Section */
.table-container {
  overflow-x: auto;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.data-table {
  width: 100%;
  border-collapse: collapse;
  min-width: 800px; /* Adjust as needed */
}

.data-table th, .data-table td {
  border: 1px solid #e0e0e0;
  padding: 10px;
  text-align: left;
  font-size: 13px;
  white-space: nowrap;
}

.data-table thead th {
  background-color: #e6f0ff;
  font-weight: bold;
  color: #333;
  text-align: center;
}

.data-table tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}

.data-table tbody tr:hover {
  background-color: #f0f8ff;
}

.loading-row td, .no-data-row td {
  text-align: center;
  font-style: italic;
  color: #777;
  padding: 20px;
}
</style>    