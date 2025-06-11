<template>
  <div class="bang-ke-trau-bo-page">
    <div class="header-section">
      <h2 class="page-title">Bảng kê hộ trâu bò quy mô nhỏ</h2>
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
          <option value="29956-XaThuongPhuoc">29956-Xã Thường Phước</option>
          <option value="29992-XaLongThuan">29992-Xã Long Thuận</option>
          <option value="29962-XaThuongThoiHauA">29962-Xã Thường Thới Hậu A</option>
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
            <th>Họ và tên chủ hộ</th>
            <th>Điện thoại</th>
            <th>Hộ nuôi trâu dưới 30 con</th>
            <th>Hộ nuôi bò thịt dưới 20 con</th>
            <th>Hộ nuôi bò sữa dưới 20 con</th>
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
            <td>{{ item.hoVaTenChuHo }}</td>
            <td>{{ item.dienThoai }}</td>
            <td class="center-text">{{ item.hoNuoiTrauDuoi30Con ? 'x' : '' }}</td>
            <td class="center-text">{{ item.hoNuoiBoThitDuoi20Con ? 'x' : '' }}</td>
            <td class="center-text">{{ item.hoNuoiBoSuaDuoi20Con ? 'x' : '' }}</td>
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
  xaPhuong: '29956-XaThuongPhuoc', // Default based on image_adb82d.png
});

const data = ref([]);
const loading = ref(true);

// Define table headers for colspan calculation
const tableHeaders = [
  'STT Hộ', 'Mã Tỉnh', 'Mã Huyện', 'Mã Xã', 'Mã Thôn', 'Tên Thôn',
  'Họ và tên chủ hộ', 'Điện thoại',
  'Hộ nuôi trâu dưới 30 con', 'Hộ nuôi bò thịt dưới 20 con', 'Hộ nuôi bò sữa dưới 20 con'
];

const fetchTableData = async () => {
  loading.value = true;
  await new Promise(resolve => setTimeout(resolve, 500)); // Simulate API call delay

  const allMockData = [
    // Data from image_adb82d.png (Xã Thường Phước - 29956)
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0001', tenThon: 'Ấp 1', hoVaTenChuHo: 'nguyễn văn vui', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0002', tenThon: 'Ấp 1', hoVaTenChuHo: 'nguyễn văn bạch', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0003', tenThon: 'Ấp 1', hoVaTenChuHo: 'đông văn trí', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0004', tenThon: 'Ấp 1', hoVaTenChuHo: 'nguyễn văn đức', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0005', tenThon: 'Ấp 1', hoVaTenChuHo: 'trần văn đeo', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0006', tenThon: 'Ấp 1', hoVaTenChuHo: 'trần minh trung', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0007', tenThon: 'Ấp 1', hoVaTenChuHo: 'tạ thị phương', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0008', tenThon: 'Ấp 1', hoVaTenChuHo: 'nguyễn văn tháng', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0009', tenThon: 'Ấp 1', hoVaTenChuHo: 'nguyễn văn Quí', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0010', tenThon: 'Ấp 1', hoVaTenChuHo: 'Nguyễn Văn Khánh', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0011', tenThon: 'Ấp 1', hoVaTenChuHo: 'Văn Văn Tuấn', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0012', tenThon: 'Ấp 1', hoVaTenChuHo: 'Trần Văn Nùng', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0013', tenThon: 'Ấp 1', hoVaTenChuHo: 'Trần Văn Ưng', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0014', tenThon: 'Ấp 1', hoVaTenChuHo: 'Trần Văn Quới', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0015', tenThon: 'Ấp 1', hoVaTenChuHo: 'Nguyễn Văn Tép', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0016', tenThon: 'Ấp 1', hoVaTenChuHo: 'Dương Văn Giao', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0017', tenThon: 'Ấp 1', hoVaTenChuHo: 'Lê Văn Phương', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0018', tenThon: 'Ấp 1', hoVaTenChuHo: 'Trần Văn Nguyễn', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0019', tenThon: 'Ấp 1', hoVaTenChuHo: 'Nguyễn văn hên', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: true },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0020', tenThon: 'Ấp 1', hoVaTenChuHo: 'Nguyễn văn hữu', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: true },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0021', tenThon: 'Ấp 1', hoVaTenChuHo: 'Nguyễn văn Khang', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: true },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0022', tenThon: 'Ấp 1', hoVaTenChuHo: 'lê văn nam', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: true },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0023', tenThon: 'Ấp 1', hoVaTenChuHo: 'lê văn hiệp', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: true },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0024', tenThon: 'Ấp 1', hoVaTenChuHo: 'lê thị hạnh', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: false },

    // Data from image_adb7ea.png (Xã Long Thuận - 29992)
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0001', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Trần Văn Ưu', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0002', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Phạm Vũ Đoàn', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0003', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Võ Văn Tích', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0004', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Nguyễn Văn Sang', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0005', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Phan Văn Nhu', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0006', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Bùi Văn Vũ', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0007', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Trần Văn Tuấn', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0008', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Bùi Văn Chiến', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0009', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Bùi Văn Khanh', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0010', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Nguyễn Văn Khơm', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0011', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Huỳnh Văn Chững', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0012', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Bùi Văn Nho', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0013', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Bùi Văn Mai', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0014', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Huỳnh Văn Chuyến', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0015', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Huỳnh Văn Trọng', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0016', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Nguyễn Văn Thuấn', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0017', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Huỳnh Văn Tuấn', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0018', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Bùi Thế Lập', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0019', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Lê Văn Bảnh', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0020', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Nguyễn Văn Khoa', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0021', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Huỳnh Thanh Sang', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29992, maThon: '05', sttHo: '0022', tenThon: 'Ấp Long Thanh', hoVaTenChuHo: 'Huỳnh Thanh Hà', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },

    // Data from image_adb792.png (Xã Thường Thới Hậu A - 29962)
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0001', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'đinh quốc gia', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0002', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'bùi văn linh', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0003', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'đinh hữu trí', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0004', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'lê văn dụng', dienThoai: '', hoNuoiTrauDuoi30Con: true, hoNuoiBoThitDuoi20Con: false, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0005', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Hồng Văn Cuộc', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0006', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Đặng Văn Đức', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0007', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Hồng Thị Ngọc', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0008', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Phạm Văn Nung', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0009', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Nguyễn Văn Chiến', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0010', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Nguyễn Hoài Linh', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0011', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Trần Đắc Lập', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0012', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Bùi Văn Long', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0013', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Hồng Văn Sang', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0014', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Nguyễn Hoàng Em', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0015', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Lâm Văn Bình', dienThoai: '', hoNuoiTrauDuoi30Con: false, hoNuoiBoThitDuoi20Con: true, hoNuoiBoSuaDuoi20Con: false },

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
.bang-ke-trau-bo-page {
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