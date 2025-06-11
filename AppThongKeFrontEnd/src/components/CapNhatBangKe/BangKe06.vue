<template>
  <div class="bang-ke-to-yen-page">
    <div class="header-section">
      <h2 class="page-title">Bảng kê hộ thu hoạch tổ yến</h2>
      <div class="action-buttons">
        <button class="btn btn-add" @click="addNew">Thêm mới</button>
        <button class="btn btn-edit" @click="editItem">Sửa</button>
        <button class="btn btn-delete" @click="deleteItem">Xóa</button>
        <button class="btn btn-import-excel" @click="importExcel">Thêm từ File Excel</button>
        <button class="btn btn-delete-village-data" @click="deleteVillageData">Xóa dữ liệu xã</button>
        <button class="btn btn-export-village" @click="exportExcelVillage">Xuất Excel theo xã</button>
        <button class="btn btn-export-province" @click="exportExcelProvince">Xuất Excel theo Tỉnh</button>
      </div>
    </div>

    <div class="filter-section">
      <div class="filter-group">
        <label for="survey-period">Kỳ điều tra</label>
        <select id="survey-period" v-model="filters.kyDieuTra">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
        </select>
      </div>
      <div class="filter-group">
        <label for="year">Năm</label>
        <select id="year" v-model="filters.nam">
          <option value="2025">2025</option>
          <option value="2024">2024</option>
          <option value="2023">2023</option>
        </select>
      </div>
      <div class="filter-group">
        <label for="province">Tỉnh/TP</label>
        <select id="province" v-model="filters.tinhTP">
          <option value="87-DongThap">87- Đồng Tháp</option>
          <option value="63-TPHCM">63- TP.HCM</option>
        </select>
      </div>
      <div class="filter-group">
        <label for="district">Quận/Huyện</label>
        <select id="district" v-model="filters.quanHuyen">
          <option value="870-HongNgu">870-Huyện Hồng Ngự</option>
          <option value="871-TamNong">871-Huyện Tam Nông</option>
        </select>
      </div>
      <div class="filter-group">
        <label for="commune">Xã/Phường</label>
        <select id="commune" v-model="filters.xaPhuong">
          <option value="29956-XaThuongPhuoc1">29956-Xã Thường Phước 1</option>
          <option value="29980-XaLongKhanhA">29980-Xã Long Khánh A</option>
          <option value="29992-XaLongThuan">29992-Xã Long Thuận</option>
          </select>
      </div>
    </div>

    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th>STT Hộ</th>
            <th>Mã Tỉnh</th>
            <th>Mã Huyện</th>
            <th>Mã Xã</th>
            <th>Mã Thôn</th>
            <th>Tên Thôn</th>
            <th>Tên chủ hộ</th>
            <th>Diện tích sàn xây dựng(m2)</th>
            <th>Diện tích sàn cho thu hoạch(m2)</th>
            <th>Số tháng thu hoạch trong năm (tháng)</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading" class="loading-row">
            <td :colspan="tableHeaders.length">Đang tải dữ liệu...</td>
          </tr>
          <tr v-else-if="filteredData.length === 0" class="no-data-row">
            <td :colspan="tableHeaders.length">Không có dữ liệu phù hợp.</td>
          </tr>
          <tr v-else v-for="(item, index) in filteredData" :key="index">
            <td>{{ item.sttHo }}</td>
            <td>{{ item.maTinh }}</td>
            <td>{{ item.maHuyen }}</td>
            <td>{{ item.maXa }}</td>
            <td>{{ item.maThon }}</td>
            <td>{{ item.tenThon }}</td>
            <td>{{ item.tenChuHo }}</td>
            <td class="center-text">{{ item.dienTichSanXayDung }}</td>
            <td class="center-text">{{ item.dienTichSanThuHoach }}</td>
            <td class="center-text">{{ item.soThangThuHoach }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';

const filters = ref({
  kyDieuTra: '1',
  nam: '2025',
  tinhTP: '87-DongThap',
  quanHuyen: '870-HongNgu',
  xaPhuong: '29956-XaThuongPhuoc1', // Default to a commune based on image_adbf34.png
});

const data = ref([]);
const loading = ref(true);

// Define table headers for colspan calculation
const tableHeaders = [
  'STT Hộ', 'Mã Tỉnh', 'Mã Huyện', 'Mã Xã', 'Mã Thôn', 'Tên Thôn',
  'Tên chủ hộ', 'Diện tích sàn xây dựng(m2)', 'Diện tích sàn cho thu hoạch(m2)', 'Số tháng thu hoạch trong năm (tháng)'
];

const fetchTableData = async () => {
  loading.value = true;
  await new Promise(resolve => setTimeout(resolve, 500)); // Simulate API call delay

  const allMockData = [
    // Data from image_adbf34.png (Xã Thường Phước 1 - 29956)
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '02', sttHo: '0001', tenThon: 'Ấp 2', tenChuHo: 'nguyễn văn tùng', dienTichSanXayDung: 250, dienTichSanThuHoach: 250, soThangThuHoach: 5 },
    // Data from image_adbc68.png (Xã Long Khánh A - 29980)
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29980, maThon: '01', sttHo: '0001', tenThon: 'Ấp Long Tả', tenChuHo: 'Nguyễn Văn Miền', dienTichSanXayDung: 120, dienTichSanThuHoach: 120, soThangThuHoach: 6 },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29980, maThon: '03', sttHo: '0002', tenThon: 'Ấp Long Thanh B', tenChuHo: 'Võ Thị Ánh Ngọc', dienTichSanXayDung: 100, dienTichSanThuHoach: 100, soThangThuHoach: 3 },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29980, maThon: '03', sttHo: '0003', tenThon: 'Ấp Long Thanh B', tenChuHo: 'Nguyễn Văn Minh', dienTichSanXayDung: 120, dienTichSanThuHoach: 120, soThangThuHoach: 3 },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29980, maThon: '05', sttHo: '0004', tenThon: 'Ấp Long Phước', tenChuHo: 'Vặng Thị Sữa', dienTichSanXayDung: 600, dienTichSanThuHoach: 600, soThangThuHoach: 12 },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29980, maThon: '06', sttHo: '0005', tenThon: 'Ấp Long Hậu', tenChuHo: 'Nguyễn Văn Phi', dienTichSanXayDung: 160, dienTichSanThuHoach: 160, soThangThuHoach: 6 },
    // Data from image_adbc11.png (Xã Long Thuận - 29992)
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '01', sttHo: '0001', tenThon: 'Ấp Long Hưng', tenChuHo: 'Thái Văn Thừa', dienTichSanXayDung: 160, dienTichSanThuHoach: 160, soThangThuHoach: 5 },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '01', sttHo: '0002', tenThon: 'Ấp Long Hưng', tenChuHo: 'Khương Bảo Muội', dienTichSanXayDung: 120, dienTichSanThuHoach: 120, soThangThuHoach: 5 },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '01', sttHo: '0003', tenThon: 'Ấp Long Hưng', tenChuHo: 'Khương Văn Tý', dienTichSanXayDung: 80, dienTichSanThuHoach: 80, soThangThuHoach: 5 },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '04', sttHo: '0004', tenThon: 'Ấp Long Hòa', tenChuHo: 'Võ Thị Mỹ Duyên', dienTichSanXayDung: 110, dienTichSanThuHoach: 110, soThangThuHoach: 5 },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '04', sttHo: '0005', tenThon: 'Ấp Long Hòa', tenChuHo: 'Nguyễn Thanh Nhôm', dienTichSanXayDung: 90, dienTichSanThuHoach: 90, soThangThuHoach: 5 },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '04', sttHo: '0006', tenThon: 'Ấp Long Hòa', tenChuHo: 'Trường Kim Hoàng', dienTichSanXayDung: 100, dienTichSanThuHoach: 100, soThangThuHoach: 6 },
  ];

  data.value = allMockData;
  loading.value = false;
  applyFilters(); // Apply filters immediately after loading data
};

onMounted(() => {
  fetchTableData();
});

const filteredData = ref([]);

const applyFilters = () => {
  let tempFilteredData = data.value.filter(item => {
    const matchesKyDieuTra = filters.value.kyDieuTra === '' || String(filters.value.kyDieuTra) === String(item.kyDieuTra);
    const matchesNam = filters.value.nam === '' || String(filters.value.nam) === String(item.nam);
    const matchesTinhTP = filters.value.tinhTP === '' || filters.value.tinhTP.split('-')[0] === String(item.maTinh);
    const matchesQuanHuyen = filters.value.quanHuyen === '' || filters.value.quanHuyen.split('-')[0] === String(item.maHuyen);
    const matchesXaPhuong = filters.value.xaPhuong === '' || filters.value.xaPhuong.split('-')[0] === String(item.maXa);

    return matchesKyDieuTra && matchesNam && matchesTinhTP && matchesQuanHuyen && matchesXaPhuong;
  });

  filteredData.value = tempFilteredData;
};

// Watch for filter changes and re-apply filters
watch(filters, () => {
  applyFilters();
}, { deep: true });

// Action button methods
const addNew = () => {
  alert('Chức năng "Thêm mới"');
};

const editItem = () => {
  alert('Chức năng "Sửa"');
};

const deleteItem = () => {
  alert('Chức năng "Xóa"');
};

const importExcel = () => {
  alert('Chức năng "Thêm từ File Excel"');
};

const deleteVillageData = () => {
  alert('Chức năng "Xóa dữ liệu xã"');
};

const exportExcelVillage = () => {
  alert('Chức năng "Xuất Excel theo xã"');
};

const exportExcelProvince = () => {
  alert('Chức năng "Xuất Excel theo Tỉnh"');
};
</script>

<style scoped>
.bang-ke-to-yen-page {
  font-family: Arial, sans-serif;
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
}

.btn-add { background-color: #28a745; } /* Green */
.btn-edit { background-color: #ffc107; color: #333; } /* Yellow */
.btn-delete { background-color: #dc3545; } /* Red */
.btn-import-excel,
.btn-delete-village-data,
.btn-export-village,
.btn-export-province {
  background-color: #17a2b8; /* Info blue */
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
  flex-basis: calc(18% - 15px); /* Adjusted for more filters in a row */
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
  min-width: 1000px; /* Adjust as needed for content */
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

.center-text {
  text-align: center;
}

.loading-row td, .no-data-row td {
  text-align: center;
  font-style: italic;
  color: #777;
  padding: 20px;
}
</style>