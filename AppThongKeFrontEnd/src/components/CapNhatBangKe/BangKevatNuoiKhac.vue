<template>
  <div class="bang-ke-quy-mo-nho-page">
    <div class="header-section">
      <h2 class="page-title">Bảng kê hộ quy mô nhỏ</h2>
      <div class="action-buttons">
        <button class="btn btn-add" @click="addNew">Thêm mới</button>
        <button class="btn btn-edit" @click="editItem">Sửa</button>
        <button class="btn btn-delete" @click="deleteItem">Xóa</button>
        <button class="btn btn-import-excel" @click="importExcel">Thêm từ File Excel</button>
        <button class="btn btn-delete-village-data" @click="deleteVillageData">Xóa dữ liệu xã</button>
        <button class="btn btn-export-village" @click="exportExcelVillage">Xuất Excel theo xã</button>
        <button class="btn btn-export-district" @click="exportExcelDistrict">Xuất Excel theo Huyện</button>
        <button class="btn btn-export-province" @click="exportExcelProvince">Xuất Excel theo Tỉnh</button>
      </div>
    </div>

    <div class="filter-section">
      <div class="filter-group">
        <label for="survey-period">Kỳ điều tra</label>
        <select id="survey-period" v-model="filters.kyDieuTra">
          <option value="">-- Chọn kỳ điều tra --</option>
          <option value="1">1</option>
          <option value="4">4</option>
        </select>
      </div>
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
        </select>
      </div>
      <div class="filter-group">
        <label for="district">Quận/Huyện</label>
        <select id="district" v-model="filters.quanHuyen">
          <option value="">-- Chọn Quận/Huyện --</option>
          <option value="870-HongNgu">870-Huyện Hồng Ngự</option>
        </select>
      </div>
      <div class="filter-group">
        <label for="commune">Xã/Phường</label>
        <select id="commune" v-model="filters.xaPhuong">
          <option value="">-- Chọn xã/phường --</option>
          <option v-for="commune in availableCommunes" :key="commune.maXa" :value="`${commune.maXa}-${commune.tenXa}`">
            {{ commune.maXa }}-{{ commune.tenXa }}
          </option>
        </select>
      </div>
      <div class="filter-group">
        <label for="hamlet">Thôn</label>
        <select id="hamlet" v-model="filters.thon">
          <option value="">-- Chọn thôn --</option>
          <option v-for="hamlet in availableHamlets" :key="hamlet" :value="hamlet">{{ hamlet }}</option>
        </select>
      </div>
    </div>

    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th rowspan="2">STT Hộ</th>
            <th rowspan="2">Mã Tỉnh</th>
            <th rowspan="2">Mã Huyện</th>
            <th rowspan="2">Mã Xã</th>
            <th rowspan="2">Mã Thôn</th>
            <th rowspan="2">Tên Thôn</th>
            <th rowspan="2">Họ và tên chủ hộ</th>
            <th rowspan="2">Điện thoại</th>
            <th colspan="3">Hộ nuôi lợn</th>
            <th colspan="3">Hộ nuôi gà</th>
            <th colspan="2">Hộ nuôi vịt</th>
            <th colspan="2">Hộ nuôi ngan</th>
            <th colspan="2">Hộ nuôi trâu</th>
            <th colspan="2">Hộ nuôi bò</th>
          </tr>
          <tr>
            <th>Từ 10 đến 99 con</th>
            <th>Từ 100 đến 299 con</th>
            <th>Từ 300 con trở lên</th>
            <th>Gà đẻ trứng từ 1000 đến 3999 con</th>
            <th>Gà thịt từ 500 đến 1999 con</th>
            <th>Gà từ 4000 con trở lên</th>
            <th>Vịt đẻ trứng từ 500 đến 1999 con</th>
            <th>Vịt từ 2000 con trở lên</th>
            <th>Ngan đẻ trứng từ 500 con trở lên</th>
            <th>Ngan từ 2000 con trở lên</th>
            <th>Từ 30 con trở lên</th>
            <th>Bò từ 30 con trở lên</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading" class="loading-row">
            <td :colspan="tableHeadersCount">Đang tải dữ liệu...</td>
          </tr>
          <tr v-else-if="filteredData.length === 0" class="no-data-row">
            <td :colspan="tableHeadersCount">Không có dữ liệu phù hợp.</td>
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
            <td class="center-text">{{ item.lon_10_99 ? 'x' : '' }}</td>
            <td class="center-text">{{ item.lon_100_299 ? 'x' : '' }}</td>
            <td class="center-text">{{ item.lon_300_Tren ? 'x' : '' }}</td>
            <td class="center-text">{{ item.ga_DeTrung_1000_3999 ? 'x' : '' }}</td>
            <td class="center-text">{{ item.ga_Thit_500_1999 ? 'x' : '' }}</td>
            <td class="center-text">{{ item.ga_4000_Tren ? 'x' : '' }}</td>
            <td class="center-text">{{ item.vit_DeTrung_500_1999 ? 'x' : '' }}</td>
            <td class="center-text">{{ item.vit_2000_Tren ? 'x' : '' }}</td>
            <td class="center-text">{{ item.ngan_DeTrung_500_1999 ? 'x' : '' }}</td>
            <td class="center-text">{{ item.ngan_2000_Tren ? 'x' : '' }}</td>
            <td class="center-text">{{ item.trau_30_Tren ? 'x' : '' }}</td>
            <td class="center-text">{{ item.bo_30_Tren ? 'x' : '' }}</td>
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
  xaPhuong: '29962-XaThuongThoi',
  thon: '',
});

const data = ref([]);
const loading = ref(true);

const tableHeadersCount = computed(() => {
  return 8 + 3 + 3 + 2 + 2 + 2;
});

const availableCommunes = computed(() => {
  const communes = new Set();
  data.value.forEach(item => {
    // Check if the item matches current filters for kyDieuTra, nam, tinhTP, quanHuyen
    const matchesKyDieuTra = filters.value.kyDieuTra === '' || String(filters.value.kyDieuTra) === String(item.kyDieuTra);
    const matchesNam = filters.value.nam === '' || String(filters.value.nam) === String(item.nam);
    const matchesTinhTP = filters.value.tinhTP === '' || filters.value.tinhTP.split('-')[0] === String(item.maTinh);
    const matchesQuanHuyen = filters.value.quanHuyen === '' || filters.value.quanHuyen.split('-')[0] === String(item.maHuyen);

    if (matchesKyDieuTra && matchesNam && matchesTinhTP && matchesQuanHuyen) {
      communes.add(JSON.stringify({ maXa: item.maXa, tenXa: item.tenXa }));
    }
  });
  const uniqueCommunes = Array.from(communes).map(str => JSON.parse(str));
  return uniqueCommunes.sort((a, b) => a.tenXa.localeCompare(b.tenXa));
});


const availableHamlets = computed(() => {
  if (!filters.value.xaPhuong) return [];
  const selectedXaCode = filters.value.xaPhuong.split('-')[0];
  const hamlets = new Set();
  data.value.forEach(item => {
    const matchesKyDieuTra = filters.value.kyDieuTra === '' || String(filters.value.kyDieuTra) === String(item.kyDieuTra);
    const matchesNam = filters.value.nam === '' || String(filters.value.nam) === String(item.nam);
    const matchesTinhTP = filters.value.tinhTP === '' || filters.value.tinhTP.split('-')[0] === String(item.maTinh);
    const matchesQuanHuyen = filters.value.quanHuyen === '' || filters.value.quanHuyen.split('-')[0] === String(item.maHuyen);

    if (matchesKyDieuTra && matchesNam && matchesTinhTP && matchesQuanHuyen && String(item.maXa) === selectedXaCode) {
      hamlets.add(item.tenThon);
    }
  });
  return Array.from(hamlets).sort();
});

const fetchTableData = async () => {
  loading.value = true;
  await new Promise(resolve => setTimeout(resolve, 500)); // Simulate API call delay

  const mockData = [
    // --- Dữ liệu cho Kỳ điều tra 4, Năm 2025 ---
    // Xã Thường Thới (maXa: 29962) - Huyện Hồng Ngự (maHuyen: 870)
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, tenXa: 'Xã Thường Thới', maThon: '01', sttHo: '0001', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Nguyễn Văn A', dienThoai: '0901112222', lon_10_99: true, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, tenXa: 'Xã Thường Thới', maThon: '01', sttHo: '0002', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Trần Thị B', dienThoai: '0902223333', lon_10_99: false, lon_100_299: true, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, tenXa: 'Xã Thường Thới', maThon: '02', sttHo: '0003', tenThon: 'Ấp Hòa Trung', hoVaTenChuHo: 'Lê Văn C', dienThoai: '0903334444', lon_10_99: false, lon_100_299: false, lon_300_Tren: true, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, tenXa: 'Xã Thường Thới', maThon: '02', sttHo: '0004', tenThon: 'Ấp Hòa Trung', hoVaTenChuHo: 'Phạm Thị D', dienThoai: '0904445555', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: true, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },

    // Xã Bình Thạnh (maXa: 29953) - Huyện Hồng Ngự (maHuyen: 870)
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29953, tenXa: 'Xã Bình Thạnh', maThon: '01', sttHo: '0001', tenThon: 'Ấp Bình A', hoVaTenChuHo: 'Nguyễn Văn E', dienThoai: '0915556666', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: true, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29953, tenXa: 'Xã Bình Thạnh', maThon: '02', sttHo: '0002', tenThon: 'Ấp Bình B', hoVaTenChuHo: 'Trần Thị F', dienThoai: '0916667777', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: true, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29953, tenXa: 'Xã Bình Thạnh', maThon: '03', sttHo: '0003', tenThon: 'Ấp Bình C', hoVaTenChuHo: 'Phạm Văn G', dienThoai: '0917778888', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: true, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },

    // Xã Long Khánh A (maXa: 29980) - Huyện Hồng Ngự (maHuyen: 870)
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29980, tenXa: 'Xã Long Khánh A', maThon: '01', sttHo: '0001', tenThon: 'Ấp Long Thuận', hoVaTenChuHo: 'Lý Văn H', dienThoai: '0928889999', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: true, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29980, tenXa: 'Xã Long Khánh A', maThon: '02', sttHo: '0002', tenThon: 'Ấp Long Thạnh', hoVaTenChuHo: 'Nguyễn Thị I', dienThoai: '0929990000', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: true, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },

    // --- Dữ liệu cho Kỳ điều tra 1, Năm 2024 ---
    // Xã Thường Thới (maXa: 29962) - Huyện Hồng Ngự (maHuyen: 870)
    { kyDieuTra: 1, nam: 2024, maTinh: 87, maHuyen: 870, maXa: 29962, tenXa: 'Xã Thường Thới', maThon: '01', sttHo: '0005', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Đinh Văn J', dienThoai: '0931112222', lon_10_99: true, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 1, nam: 2024, maTinh: 87, maHuyen: 870, maXa: 29962, tenXa: 'Xã Thường Thới', maThon: '02', sttHo: '0006', tenThon: 'Ấp Hòa Trung', hoVaTenChuHo: 'Võ Thị K', dienThoai: '0932223333', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: true, bo_30_Tren: false },

    // Xã Bình Thạnh (maXa: 29953) - Huyện Hồng Ngự (maHuyen: 870)
    { kyDieuTra: 1, nam: 2024, maTinh: 87, maHuyen: 870, maXa: 29953, tenXa: 'Xã Bình Thạnh', maThon: '01', sttHo: '0004', tenThon: 'Ấp Bình A', hoVaTenChuHo: 'Nguyễn Văn L', dienThoai: '0943334444', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: true },

    // Dữ liệu ban đầu từ các ảnh đã cung cấp (đã điều chỉnh cho phù hợp với cấu trúc mới)
    // Dữ liệu cho Xã Thường Thới Hậu A (29962-Xã Thường Thới), Kỳ điều tra 4, Năm 2025
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, tenXa: 'Xã Thường Thới', maThon: '02', sttHo: '0001', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Trần Văn Tiển', dienThoai: '', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: true, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, tenXa: 'Xã Thường Thới', maThon: '02', sttHo: '0002', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Lã Văn Chiến', dienThoai: '', lon_10_99: true, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, tenXa: 'Xã Thường Thới', maThon: '02', sttHo: '0003', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Huỳnh Văn Xuyên', dienThoai: '', lon_10_99: true, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    //... (Các dòng khác của Xã Thường Thới Hậu A từ các ảnh)
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, tenXa: 'Xã Thường Thới', maThon: '02', sttHo: '0006', tenThon: 'Ấp Bình Hòa Trung', hoVaTenChuHo: 'Lê Văn Hạnh', dienThoai: '', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: true, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },

    // Dữ liệu cho Ấp 1 (29956-Ấp 1), Kỳ điều tra 4, Năm 2025 - Giả định đây là Xã Thường Thới
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, tenXa: 'Xã Thường Thới Hậu A', maThon: '01', sttHo: '0014', tenThon: 'Ấp 1', hoVaTenChuHo: 'Nguyễn Thị Bé Ba', dienThoai: '', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: true, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, tenXa: 'Xã Thường Thới Hậu A', maThon: '02', sttHo: '0010', tenThon: 'Ấp 2', hoVaTenChuHo: 'Lê Văn Lợi', dienThoai: '', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: true, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },

    // Dữ liệu cho Xã Long Khánh A (29980-Xã Long Khánh A), Kỳ điều tra 4, Năm 2025
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29980, tenXa: 'Xã Long Khánh A', maThon: '02', sttHo: '0001', tenThon: '011-Long Hữu', hoVaTenChuHo: 'Nguyễn Thị Mến', dienThoai: '', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29980, tenXa: 'Xã Long Khánh A', maThon: '02', sttHo: '0002', tenThon: '011-Long Hữu', hoVaTenChuHo: 'Hồ Thị Khánh', dienThoai: '', lon_10_99: true, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },

    // Dữ liệu cho Xã Long Khánh (29983-Xã Long Khánh), Kỳ điều tra 1, Năm 2025
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29983, tenXa: 'Xã Long Khánh', maThon: '01', sttHo: '0001', tenThon: 'Ấp 1 Long Khánh', hoVaTenChuHo: 'Trần Văn Demo', dienThoai: '0123456789', lon_10_99: true, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: false, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
    { kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29983, tenXa: 'Xã Long Khánh', maThon: '02', sttHo: '0002', tenThon: 'Ấp 2 Long Khánh', hoVaTenChuHo: 'Nguyễn Thị Demo 2', dienThoai: '0987654321', lon_10_99: false, lon_100_299: false, lon_300_Tren: false, ga_DeTrung_1000_3999: true, ga_Thit_500_1999: false, ga_4000_Tren: false, vit_DeTrung_500_1999: false, vit_2000_Tren: false, ngan_DeTrung_500_1999: false, ngan_2000_Tren: false, trau_30_Tren: false, bo_30_Tren: false },
  ];

  data.value = mockData;
  loading.value = false;
  applyFilters();
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
    const matchesThon = filters.value.thon === '' || filters.value.thon === item.tenThon;

    return matchesKyDieuTra && matchesNam && matchesTinhTP && matchesQuanHuyen && matchesXaPhuong && matchesThon;
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

const exportExcelDistrict = () => {
  alert('Chức năng "Xuất Excel theo Huyện"');
};

const exportExcelProvince = () => {
  alert('Chức năng "Xuất Excel theo Tỉnh"');
};
</script>

<style scoped>
.bang-ke-quy-mo-nho-page {
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
.btn-export-district, /* New button style */
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
  flex-basis: calc(16% - 15px); /* Adjusted for more filters in a row */
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
  min-width: 1800px; /* Adjust as needed for content, especially with many columns */
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