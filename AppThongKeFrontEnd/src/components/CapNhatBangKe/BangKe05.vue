<template>
  <div class="bang-ke-quy-mo-nho-page">
    <div class="header-section">
      <h2 class="page-title">Bảng kê hộ quy mô nhỏ</h2>
      <div class="export-buttons">
        <button class="btn btn-export-area" @click="exportExcelArea">Xuất Excel theo địa bàn</button>
        <button class="btn btn-export-district" @click="exportExcelDistrict">Xuất Excel theo huyện</button>
        <button class="btn btn-export-province" @click="exportExcelProvince">Xuất Excel theo tỉnh</button>
      </div>
    </div>

    <div class="filter-section">
      <div class="filter-group">
        <label for="survey-period">Kỳ điều tra</label>
        <select id="survey-period" v-model="filters.kyDieuTra">
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
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
          <option value="29980-LongKhanhA">29980-Xã Long Khánh A</option>
          <option value="29962-ThuongThoiA">29962-Xã Thường Thới A</option>
          <option value="29956-VinhThanh">29956-Xã Vĩnh Thạnh</option>
          <option value="29959-PhuThuanB">29959-Xã Phú Thuận B</option>
        </select>
      </div>
      <div class="filter-group">
        <label for="village">Thôn</label>
        <select id="village" v-model="filters.thon">
          <option value="">-- Chọn Thôn --</option>
          <option value="05-ApLongPhuoc">05-Ấp Long Phước</option>
          <option value="011-longhuu4">011-long hữu 4</option>
          <option value="Ap 1">Ấp 1</option>
          <option value="Ap 2">Ấp 2</option>
          <option value="Ap 3">Ấp 3</option>
          <option value="Ap Chom Xoai">Ấp Chòm Xoài</option>
          <option value="Ap Giong Bang">Ấp Giồng Bàng</option>
          <option value="Ap Binh Hoa Thuong">Ấp Bình Hòa Thượng</option>
          <option value="Ap Tan Thuan">Ấp Tân Thuận</option>
        </select>
      </div>
      <div class="filter-group">
        <label for="area">Địa bàn</label>
        <select id="area" v-model="filters.diaBan">
          <option value="">-- Chọn Địa bàn --</option>
          <option value="011-longhuu4">011-long hữu 4</option>
          <option value="012-longhuu5">012-long hữu 5</option>
        </select>
      </div>
      <div class="filter-group right-align-group">
        <span class="total-households">Tổng số hộ nông thôn: {{ totalHouseholds }}</span>
        <button class="btn btn-update-area-data" @click="updateAreaData">Cập nhật số hộ địa bàn</button>
      </div>
    </div>

    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th rowspan="2">Mã Tỉnh</th>
            <th rowspan="2">Mã Huyện</th>
            <th rowspan="2">Mã Xã</th>
            <th rowspan="2">Mã Địa bàn</th>
            <th rowspan="2">STT Hộ</th>
            <th rowspan="2">Họ và tên chủ hộ</th>
            <th rowspan="2">Điện thoại</th>
            <th colspan="4">Hộ nuôi lợn</th>
            <th colspan="2">Hộ nuôi gà</th>
            <th colspan="2">Hộ nuôi vịt</th>
            <th colspan="2">Hộ nuôi ngan</th>
            <th rowspan="2">Hộ nuôi trâu/bò từ 30 con trở lên</th>
            <th rowspan="2">Hộ nuôi dê/cừu từ 30 con trở lên</th>
          </tr>
          <tr>
            <th>Từ 1 đến 9 con</th>
            <th>Từ 10 đến 29 con</th>
            <th>Từ 30 đến 99 con</th>
            <th>Từ 100 đến 299 con</th>
            <th>Từ 1 đến 49 con</th>
            <th>Từ 50 đến 199 con</th>
            <th>Từ 1 đến 49 con</th>
            <th>Từ 50 đến 199 con</th>
            <th>Từ 50 đến 199 con</th>
            <th>Từ 200 đến 499 con</th>
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
            <td>{{ item.maTinh }}</td>
            <td>{{ item.maHuyen }}</td>
            <td>{{ item.maXa }}</td>
            <td>{{ item.maDiaBan }}</td>
            <td>{{ item.sttHo }}</td>
            <td>{{ item.hoVaTenChuHo }}</td>
            <td class="center-text">{{ item.dienThoai === true ? 'x' : item.dienThoai }}</td>
            <td class="center-text">{{ item.lon_1_9 === true ? 'x' : '' }}</td>
            <td class="center-text">{{ item.lon_10_29 === true ? 'x' : '' }}</td>
            <td class="center-text">{{ item.lon_30_99 === true ? 'x' : '' }}</td>
            <td class="center-text">{{ item.lon_100_299 === true ? 'x' : '' }}</td>
            <td class="center-text">{{ item.ga_1_49 === true ? 'x' : '' }}</td>
            <td class="center-text">{{ item.ga_50_199 === true ? 'x' : '' }}</td>
            <td class="center-text">{{ item.vit_1_49 === true ? 'x' : '' }}</td>
            <td class="center-text">{{ item.vit_50_199 === true ? 'x' : '' }}</td>
            <td class="center-text">{{ item.ngan_50_199 === true ? 'x' : '' }}</td>
            <td class="center-text">{{ item.ngan_200_499 === true ? 'x' : '' }}</td>
            <td class="center-text">{{ item.trauBo_30 === true ? 'x' : '' }}</td>
            <td class="center-text">{{ item.deCuu_30 === true ? 'x' : '' }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';

const filters = ref({
  kyDieuTra: '4',
  nam: '2025',
  tinhTP: '87-DongThap',
  quanHuyen: '870-HongNgu',
  xaPhuong: '29980-LongKhanhA', // Default to Long Khanh A
  thon: '', // Default empty
  diaBan: '' // Default empty
});

const data = ref([]);
const loading = ref(true);
const totalHouseholds = ref(0);

// Define table headers for colspan calculation - Make sure this matches your <thead> structure
const tableHeaders = [
  'Mã Tỉnh', 'Mã Huyện', 'Mã Xã', 'Mã Địa bàn', 'STT Hộ', 'Họ và tên chủ hộ', 'Điện thoại',
  'Lợn 1-9', 'Lợn 10-29', 'Lợn 30-99', 'Lợn 100-299',
  'Gà 1-49', 'Gà 50-199',
  'Vịt 1-49', 'Vịt 50-199',
  'Ngan 50-199', 'Ngan 200-499',
  'Trâu/Bò >= 30', 'Dê/Cừu >= 30'
];

const fetchTableData = async () => {
  loading.value = true;
  await new Promise(resolve => setTimeout(resolve, 500)); // Simulate API call delay

  const allMockData = [
    // Data from image_b93361.png (Xã Long Khánh A, Địa bàn 011-longhuu4)
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0001', hoVaTenChuHo: 'Nguyễn Thị Mến', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0002', hoVaTenChuHo: 'Hồ Thị Nhảnh', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0003', hoVaTenChuHo: 'Nguyễn Văn Tuấn', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0004', hoVaTenChuHo: 'Nguyễn Văn Đồng', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0005', hoVaTenChuHo: 'Hồ Văn Hiển', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0006', hoVaTenChuHo: 'Bùi Thị Dõi', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0007', hoVaTenChuHo: 'Phan Văn Đồng', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0008', hoVaTenChuHo: 'Nguyễn Văn Triệu', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0009', hoVaTenChuHo: 'Nguyễn Văn Lâm', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0010', hoVaTenChuHo: 'Lê Văn Phương', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0011', hoVaTenChuHo: 'Lê Văn Cương', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0012', hoVaTenChuHo: 'Trường Công Thành', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0013', hoVaTenChuHo: 'Phạm Văn Tạo', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0014', hoVaTenChuHo: 'Nguyễn Văn Sơn', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0015', hoVaTenChuHo: 'Nguyễn Văn Chương', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0016', hoVaTenChuHo: 'Ngô Văn Nộp', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0017', hoVaTenChuHo: 'Phạm Văn Gia', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29980, thon: '05-ApLongPhuoc', maDiaBan: 11, sttHo: '0018', hoVaTenChuHo: 'Đỗ Văn Sơn', dienThoai: '', lon_1_9: false, lon_10_29: true, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },

    // Data from image_ae46d3.png (Xã Thường Thới A - 29962)
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0001', hoVaTenChuHo: 'Trần Văn Tiến', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0002', hoVaTenChuHo: 'Lê Văn Chiến', dienThoai: 'x', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0003', hoVaTenChuHo: 'Huỳnh Văn Xuyên', dienThoai: 'x', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0004', hoVaTenChuHo: 'Võ Văn Hải', dienThoai: 'x', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0005', hoVaTenChuHo: 'Huỳnh Văn Âu', dienThoai: 'x', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0006', hoVaTenChuHo: 'Lê Văn Hạnh', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0007', hoVaTenChuHo: 'Lê Thị Nga', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0008', hoVaTenChuHo: 'Huỳnh Hùng Cường', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0009', hoVaTenChuHo: 'Lê Văn Sứ', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0010', hoVaTenChuHo: 'Lâm Văn Phụng', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0011', hoVaTenChuHo: 'Lê Thị Trang', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0012', hoVaTenChuHo: 'Hồ Văn Phúc', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0013', hoVaTenChuHo: 'Nguyễn Thị Phượng', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0014', hoVaTenChuHo: 'Phan Văn Thâu', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0015', hoVaTenChuHo: 'Phan Văn Phụng', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0016', hoVaTenChuHo: 'Huỳnh Tiết Xuân', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0017', hoVaTenChuHo: 'Lý Thị Huỳnh Anh', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0018', hoVaTenChuHo: 'Nguyễn Thị Mị', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0019', hoVaTenChuHo: 'Phạm Thị Thúy', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0020', hoVaTenChuHo: 'Phạm Văn Thâu', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0021', hoVaTenChuHo: 'Tô Thị Định', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0022', hoVaTenChuHo: 'Phan Thị Vàng', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0023', hoVaTenChuHo: 'Nguyễn Văn Huyên', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0024', hoVaTenChuHo: 'Lê Thị Trang', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0025', hoVaTenChuHo: 'Huỳnh Văn Minh', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29962, thon: 'Ấp Bình Hòa Thượng', maDiaBan: null, sttHo: '0026', hoVaTenChuHo: 'Nguyễn Văn Minh', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },

    // Data from image_b8bb1c.png (Xã Vĩnh Thạnh - 29956, các ấp)
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp 1', maDiaBan: null, sttHo: '0014', hoVaTenChuHo: 'Nguyễn Thị Bé Ba', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp 2', maDiaBan: null, sttHo: '0010', hoVaTenChuHo: 'Lê Văn Lợi', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp 3', maDiaBan: null, sttHo: '0008', hoVaTenChuHo: 'Lê Văn Sang', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp 3', maDiaBan: null, sttHo: '0009', hoVaTenChuHo: 'Ngô Văn Khiện', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp 3', maDiaBan: null, sttHo: '0011', hoVaTenChuHo: 'Lâm Thị Hiền', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp 3', maDiaBan: null, sttHo: '0012', hoVaTenChuHo: 'Lê Văn Úa', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp 3', maDiaBan: null, sttHo: '0013', hoVaTenChuHo: 'Nguyễn Văn Sáu', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp Chòm Xoài', maDiaBan: null, sttHo: '0001', hoVaTenChuHo: 'Nguyễn Văn Chùm Xoài', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp Chòm Xoài', maDiaBan: null, sttHo: '0002', hoVaTenChuHo: 'Mai Kim Hạnh', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp Chòm Xoài', maDiaBan: null, sttHo: '0003', hoVaTenChuHo: 'Nguyễn Văn Nữ', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp Chòm Xoài', maDiaBan: null, sttHo: '0004', hoVaTenChuHo: 'Ngô Thị Bợt', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp Giồng Bàng', maDiaBan: null, sttHo: '0001', hoVaTenChuHo: 'Lương Văn Ồ', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp Giồng Bàng', maDiaBan: null, sttHo: '0002', hoVaTenChuHo: 'Phạm Văn Ngay', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29956, thon: 'Ấp Giồng Bàng', maDiaBan: null, sttHo: '0003', hoVaTenChuHo: 'Huỳnh Văn Chiều', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: true, trauBo_30: false, deCuu_30: false },

    // Data from image_ae2c2f.png (Xã Phú Thuận B - 29959, Thôn Tân Thuận)
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0001', hoVaTenChuHo: 'Nguyễn Văn Đang', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: true, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0002', hoVaTenChuHo: 'Võ Thị Sáu', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: true, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0003', hoVaTenChuHo: 'Nguyễn Văn Năm', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: true, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0004', hoVaTenChuHo: 'Lê Văn Tám', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: true, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0005', hoVaTenChuHo: 'Huỳnh Thị Sáu', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: true, lon_100_299: false, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0006', hoVaTenChuHo: 'Trần Văn Tiên', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0007', hoVaTenChuHo: 'Lê Văn Chính', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0008', hoVaTenChuHo: 'Huỳnh Văn Ngon', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0009', hoVaTenChuHo: 'Phan Văn Chơn', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0010', hoVaTenChuHo: 'Nguyễn Văn Đời', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0011', hoVaTenChuHo: 'Nguyễn Văn Thạch', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0012', hoVaTenChuHo: 'Trần Văn Tiến', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0013', hoVaTenChuHo: 'Lâm Văn Phương', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0014', hoVaTenChuHo: 'Lê Thanh Ngạn', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0015', hoVaTenChuHo: 'Phạm Thanh', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0016', hoVaTenChuHo: 'Trần Thanh Ngân', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0017', hoVaTenChuHo: 'Nguyễn Văn Đang', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0018', hoVaTenChuHo: 'Huỳnh Văn Sang', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0019', hoVaTenChuHo: 'Nguyễn Văn Định', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0020', hoVaTenChuHo: 'Phạm Thị Giang', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0021', hoVaTenChuHo: 'Nguyễn Văn Đang', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0022', hoVaTenChuHo: 'Huỳnh Văn Ngân', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0023', hoVaTenChuHo: 'Lê Thị Trang', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0024', hoVaTenChuHo: 'Huỳnh Văn Đang', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0025', hoVaTenChuHo: 'Huỳnh Văn Ngon', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
    { maTinh: 87, maHuyen: 870, maXa: 29959, thon: 'Ấp Tân Thuận', maDiaBan: null, sttHo: '0026', hoVaTenChuHo: 'Huỳnh Văn Thạch', dienThoai: '', lon_1_9: false, lon_10_29: false, lon_30_99: false, lon_100_299: true, ga_1_49: false, ga_50_199: false, vit_1_49: false, vit_50_199: false, ngan_50_199: false, ngan_200_499: false, trauBo_30: false, deCuu_30: false },
  ];
  
  data.value = allMockData; // Load all mock data initially
  loading.value = false;
  applyFilters(); // Apply filters immediately after loading data
};

onMounted(() => {
  fetchTableData();
});

// Watch for filter changes and re-apply filters
watch(filters, () => {
  applyFilters();
}, { deep: true });

const filteredData = ref([]);

const applyFilters = () => {
  let tempFilteredData = data.value.filter(item => {
    const matchesKyDieuTra = filters.value.kyDieuTra === '' || String(filters.value.kyDieuTra) === String(item.kyDieuTra || '4'); // Default to 4 if not in mock data
    const matchesNam = filters.value.nam === '' || String(filters.value.nam) === String(2025); // Assume 2025 for all mock data if not specified
    const matchesTinhTP = filters.value.tinhTP === '' || filters.value.tinhTP.split('-')[0] === String(item.maTinh);
    const matchesQuanHuyen = filters.value.quanHuyen === '' || filters.value.quanHuyen.split('-')[0] === String(item.maHuyen);
    const matchesXaPhuong = filters.value.xaPhuong === '' || filters.value.xaPhuong.split('-')[0] === String(item.maXa);
    const matchesThon = filters.value.thon === '' || filters.value.thon === item.thon;
    const matchesDiaBan = filters.value.diaBan === '' || (item.maDiaBan && filters.value.diaBan.split('-')[0] === String(item.maDiaBan));

    return matchesKyDieuTra && matchesNam && matchesTinhTP && matchesQuanHuyen && matchesXaPhuong && matchesThon && matchesDiaBan;
  });

  filteredData.value = tempFilteredData;
  totalHouseholds.value = tempFilteredData.length;
};


const updateAreaData = () => {
  console.log("Cập nhật số hộ địa bàn với các bộ lọc:", filters.value);
  // In a real application, you might re-fetch data from an API here
  // based on the current filters, or perform a client-side update.
  applyFilters(); // Re-apply filters to update the table
};

const exportExcelArea = () => {
  alert('Xuất Excel theo địa bàn');
};

const exportExcelDistrict = () => {
  alert('Xuất Excel theo huyện');
};

const exportExcelProvince = () => {
  alert('Xuất Excel theo tỉnh');
};
</script>

<style scoped>
/* General Styles */
.bang-ke-quy-mo-nho-page {
  font-family: Arial, sans-serif;
  padding: 20px;
  background-color: #f0f2f5;
}

.btn {
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.btn-export-area, .btn-export-district, .btn-export-province {
  background-color: #28a745; /* Green for export buttons */
  color: white;
  margin-left: 10px;
}

.btn-update-area-data {
  background-color: #007bff; /* Blue for update button */
  color: white;
}

/* Header Section */
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
  flex-basis: calc(14% - 15px); /* Adjusted for more filters in a row */
  min-width: 130px;
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

.right-align-group {
  margin-left: auto;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 10px;
}

.total-households {
  font-weight: bold;
  color: #0056b3;
  font-size: 15px;
  margin-bottom: 5px;
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
  min-width: 1800px; /* Increased min-width significantly for many columns */
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

.data-table thead th[rowspan="2"] {
  vertical-align: middle;
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