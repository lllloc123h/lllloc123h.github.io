<template>
  <div class="bang-ke-ho-tong-hop-page">
    <div class="filter-section">
      <div class="filter-row">
        <div class="filter-group">
          <label for="kyDieuTra">Kỳ điều tra</label>
          <select id="kyDieuTra" v-model="filters.kyDieuTra">
            <option value="">-- Chọn kỳ --</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
          </select>
        </div>
        <div class="filter-group">
          <label for="nam">Năm</label>
          <select id="nam" v-model="filters.nam">
            <option value="">-- Chọn năm --</option>
            <option value="2025">2025</option>
            <option value="2024">2024</option>
            <option value="2023">2023</option>
          </select>
        </div>
        <div class="filter-group">
          <label for="tinhTP">Tỉnh/TP</label>
          <select id="tinhTP" v-model="filters.tinhTP">
            <option value="">-- Chọn Tỉnh/TP --</option>
            <option value="87-DongThap">87- Đồng Tháp</option>
            <option value="92-CanTho">92- Cần Thơ</option>
          </select>
        </div>
        <div class="filter-group">
          <label for="quanHuyen">Quận/Huyện</label>
          <select id="quanHuyen" v-model="filters.quanHuyen">
            <option value="">-- Chọn Quận/Huyện --</option>
            <option value="870-HongNgu">870-Hồng Ngự</option>
            <option value="871-CaoLanh">871-Cao Lãnh</option>
          </select>
        </div>
        <div class="filter-group">
          <label for="xaPhuong">Xã/Phường</label>
          <select id="xaPhuong" v-model="filters.xaPhuong">
            <option value="">-- Chọn Xã/Phường --</option>
            <option value="29963-LongKhanhB">29963-Xã Long Khánh B</option>
            <option value="29962-LongKhanhA">29962-Xã Long Khánh A</option>
            <option value="29956-ThoiThuan">29956-Xã Thới Thuận</option>
            <option value="29970-ThoiThuanHauA">29970-Xã Thới Thuận Hậu A</option>
            <option value="29971-ThoiThuanTrungTien">29971-Thới Thuận Trung Tiền</option>
            <option value="29974-ThoiThuanPhuoc2">29974-Thới Thuận Phước 2</option>
            <option value="29977-ThoiThuanLoc">29977-Thới Thuận Lộc</option>
            <option value="29980-XaLongKhanhA">29980-Xã Long Khánh A</option>
            <option value="29983-XaLongKhanhB">29983-Xã Long Khánh B</option>
            <option value="29992-XaLongThuan">29992-Xã Long Thuận</option>
            <option value="29995-XaPhuThuanB">29995-Xã Phú Thuận B</option>
            <option value="29998-XaPhuThuanA">29998-Xã Phú Thuận A</option>
          </select>
        </div>
        <button class="btn btn-secondary filter-button" @click="fetchTableData">Xem</button>
        <button class="btn btn-info filter-button" @click="exportExcel">Xuất Excel</button>
      </div>
    </div>

    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th rowspan="2">STT</th>
            <th rowspan="2">Mã Tỉnh</th>
            <th rowspan="2">Mã Xã</th>
            <th rowspan="2">Tên Xã</th>
            <th colspan="4">Hộ nuôi lợn</th>
            <th colspan="4">Hộ nuôi gà</th>
            <th colspan="4">Hộ nuôi vịt</th>
            <th colspan="4">Hộ nuôi ngan/quy</th>
            <th colspan="2">Hộ nuôi trâu</th>
            <th colspan="2">Hộ nuôi bò thịt</th>
            <th colspan="2">Hộ nuôi bò sữa</th>
          </tr>
          <tr>
            <th>Từ 1 đến 9 con</th>
            <th>Từ 10 đến 29 con</th>
            <th>Từ 30 đến 99 con</th>
            <th>Từ 100 con trở lên</th>
            <th>Gà thịt từ 1 đến 999 con</th>
            <th>Gà đẻ trứng từ 1000 đến 3999 con</th>
            <th>Từ 4000 con trở lên</th>
            <th>Từ 1 đến 49 con</th>
            <th>Vịt thịt từ 50 đến 199 con</th>
            <th>Vịt đẻ trứng từ 200 con trở lên</th>
            <th>Từ 2000 con trở lên</th>
            <th>Từ 1 đến 50 con</th>
            <th>Từ 50 đến 199 con</th>
            <th>Từ 200 con trở lên</th>
            <th>Dưới 30 con</th>
            <th>30 con trở lên</th>
            <th>Dưới 20 con</th>
            <th>20 con trở lên</th>
            <th>Dưới 20 con</th>
            <th>20 con trở lên</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading" class="loading-row">
            <td :colspan="26">Đang tải dữ liệu...</td>
          </tr>
          <tr v-else-if="filteredData.length === 0" class="no-data-row">
            <td :colspan="26">Chưa có dữ liệu</td>
          </tr>
          <tr v-else v-for="(item, index) in filteredData" :key="index">
            <td>{{ index + 1 }}</td>
            <td>{{ item.maTinh }}</td>
            <td>{{ item.maXa }}</td>
            <td>{{ item.tenXa }}</td>
            <td>{{ item.lon_1_9 }}</td>
            <td>{{ item.lon_10_29 }}</td>
            <td>{{ item.lon_30_99 }}</td>
            <td>{{ item.lon_100_plus }}</td>
            <td>{{ item.ga_thit_1_999 }}</td>
            <td>{{ item.ga_de_1000_3999 }}</td>
            <td>{{ item.ga_4000_plus }}</td>
            <td>{{ item.vit_1_49 }}</td>
            <td>{{ item.vit_50_199 }}</td>
            <td>{{ item.vit_de_200_plus }}</td>
            <td>{{ item.vit_2000_plus }}</td>
            <td>{{ item.ngan_quy_1_50 }}</td>
            <td>{{ item.ngan_quy_50_199 }}</td>
            <td>{{ item.ngan_quy_200_plus }}</td>
            <td>{{ item.trau_duoi_30 }}</td>
            <td>{{ item.trau_30_plus }}</td>
            <td>{{ item.bo_thit_duoi_20 }}</td>
            <td>{{ item.bo_thit_20_plus }}</td>
            <td>{{ item.bo_sua_duoi_20 }}</td>
            <td>{{ item.bo_sua_20_plus }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';

const filters = ref({
  kyDieuTra: '4', // Từ image_b93361.png
  nam: '2025',   // Từ image_b93361.png
  tinhTP: '87-DongThap', // Từ image_b93361.png
  quanHuyen: '870-HongNgu', // Từ image_b93361.png
  xaPhuong: '', // Để trống để hiển thị tất cả ban đầu
});

const data = ref([]);
const loading = ref(true);
const filteredData = ref([]);

const fetchTableData = async () => {
  loading.value = true;
  await new Promise(resolve => setTimeout(resolve, 500)); // Simulate API call delay

  // Dữ liệu giả định dựa trên image_abf91c.png và các hình ảnh tổng hợp khác
  const mockData = [
    {
      maTinh: 87, maXa: 29956, tenXa: 'Xã Thường Thới Tiền',
      lon_1_9: '14 / 20', lon_10_29: '0 / 0', lon_30_99: '0 / 0', lon_100_plus: '0 / 0',
      ga_thit_1_999: '17 / 858', ga_de_1000_3999: '0 / 11', ga_4000_plus: '0 / 0',
      vit_1_49: '0 / 0', vit_50_199: '3 / 8', vit_de_200_plus: '2 / 6', vit_2000_plus: '2 / 5',
      ngan_quy_1_50: '137 / 137', ngan_quy_50_199: '0 / 45', ngan_quy_200_plus: '0 / 0',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
    {
      maTinh: 87, maXa: 29956, tenXa: 'Xã Thường Phước 1',
      lon_1_9: '0 / 1', lon_10_29: '0 / 2', lon_30_99: '0 / 0', lon_100_plus: '0 / 0',
      ga_thit_1_999: '4 / 77', ga_de_1000_3999: '0 / 3', ga_4000_plus: '0 / 0',
      vit_1_49: '0 / 7', vit_50_199: '0 / 0', vit_de_200_plus: '0 / 0', vit_2000_plus: '0 / 0',
      ngan_quy_1_50: '14 / 14', ngan_quy_50_199: '0 / 10', ngan_quy_200_plus: '0 / 0',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
    {
      maTinh: 87, maXa: 29962, tenXa: 'Xã Thường Thới Hậu A',
      lon_1_9: '0 / 0', lon_10_29: '0 / 0', lon_30_99: '0 / 5', lon_100_plus: '0 / 0',
      ga_thit_1_999: '0 / 8', ga_de_1000_3999: '0 / 0', ga_4000_plus: '0 / 0',
      vit_1_49: '0 / 4', vit_50_199: '0 / 1', vit_de_200_plus: '0 / 0', vit_2000_plus: '0 / 0',
      ngan_quy_1_50: '43 / 43', ngan_quy_50_199: '0 / 1', ngan_quy_200_plus: '0 / 0',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
    {
      maTinh: 87, maXa: 29971, tenXa: 'Thới Thuận Trung Tiền',
      lon_1_9: '14 / 20', lon_10_29: '0 / 5', lon_30_99: '0 / 0', lon_100_plus: '0 / 0',
      ga_thit_1_999: '7 / 421', ga_de_1000_3999: '0 / 5', ga_4000_plus: '2 / 2',
      vit_1_49: '0 / 49', vit_50_199: '1 / 3', vit_de_200_plus: '0 / 0', vit_2000_plus: '0 / 0',
      ngan_quy_1_50: '5 / 10', ngan_quy_50_199: '5 / 5', ngan_quy_200_plus: '0 / 24',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
    {
      maTinh: 87, maXa: 29974, tenXa: 'Xã Thường Thới Phước 2',
      lon_1_9: '0 / 0', lon_10_29: '0 / 0', lon_30_99: '2 / 5', lon_100_plus: '0 / 0',
      ga_thit_1_999: '0 / 7', ga_de_1000_3999: '0 / 0', ga_4000_plus: '0 / 0',
      vit_1_49: '0 / 0', vit_50_199: '0 / 0', vit_de_200_plus: '0 / 0', vit_2000_plus: '0 / 0',
      ngan_quy_1_50: '12 / 12', ngan_quy_50_199: '0 / 1', ngan_quy_200_plus: '0 / 0',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
    {
      maTinh: 87, maXa: 29977, tenXa: 'Xã Thường Thới Lộc',
      lon_1_9: '0 / 5', lon_10_29: '0 / 1', lon_30_99: '0 / 0', lon_100_plus: '0 / 0',
      ga_thit_1_999: '6 / 81', ga_de_1000_3999: '0 / 2', ga_4000_plus: '0 / 0',
      vit_1_49: '0 / 3', vit_50_199: '3 / 13', vit_de_200_plus: '0 / 0', vit_2000_plus: '0 / 0',
      ngan_quy_1_50: '17 / 17', ngan_quy_50_199: '0 / 5', ngan_quy_200_plus: '0 / 0',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
    {
      maTinh: 87, maXa: 29980, tenXa: 'Xã Long Khánh A',
      lon_1_9: '0 / 3', lon_10_29: '0 / 1', lon_30_99: '0 / 4', lon_100_plus: '0 / 0',
      ga_thit_1_999: '0 / 89', ga_de_1000_3999: '1 / 1', ga_4000_plus: '0 / 0',
      vit_1_49: '0 / 2', vit_50_199: '0 / 0', vit_de_200_plus: '0 / 0', vit_2000_plus: '0 / 0',
      ngan_quy_1_50: '2 / 2', ngan_quy_50_199: '0 / 1', ngan_quy_200_plus: '0 / 0',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
    {
      maTinh: 87, maXa: 29983, tenXa: 'Xã Long Khánh B',
      lon_1_9: '0 / 0', lon_10_29: '0 / 0', lon_30_99: '0 / 0', lon_100_plus: '0 / 0',
      ga_thit_1_999: '0 / 56', ga_de_1000_3999: '0 / 0', ga_4000_plus: '0 / 0',
      vit_1_49: '0 / 4', vit_50_199: '0 / 0', vit_de_200_plus: '2 / 5', vit_2000_plus: '0 / 0',
      ngan_quy_1_50: '0 / 0', ngan_quy_50_199: '0 / 0', ngan_quy_200_plus: '0 / 0',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
    {
      maTinh: 87, maXa: 29992, tenXa: 'Xã Long Thuận',
      lon_1_9: '0 / 9', lon_10_29: '0 / 4', lon_30_99: '0 / 1', lon_100_plus: '0 / 0',
      ga_thit_1_999: '0 / 56', ga_de_1000_3999: '0 / 0', ga_4000_plus: '0 / 0',
      vit_1_49: '0 / 9', vit_50_199: '0 / 0', vit_de_200_plus: '0 / 0', vit_2000_plus: '0 / 0',
      ngan_quy_1_50: '16 / 16', ngan_quy_50_199: '0 / 0', ngan_quy_200_plus: '0 / 0',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
    {
      maTinh: 87, maXa: 29995, tenXa: 'Xã Phú Thuận B',
      lon_1_9: '0 / 0', lon_10_29: '0 / 0', lon_30_99: '0 / 0', lon_100_plus: '0 / 0',
      ga_thit_1_999: '0 / 49', ga_de_1000_3999: '0 / 0', ga_4000_plus: '0 / 0',
      vit_1_49: '0 / 2', vit_50_199: '0 / 0', vit_de_200_plus: '0 / 0', vit_2000_plus: '0 / 1',
      ngan_quy_1_50: '4 / 4', ngan_quy_50_199: '0 / 3', ngan_quy_200_plus: '0 / 0',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
    {
      maTinh: 87, maXa: 29998, tenXa: 'Xã Phú Thuận A',
      lon_1_9: '0 / 1', lon_10_29: '0 / 2', lon_30_99: '0 / 0', lon_100_plus: '0 / 0',
      ga_thit_1_999: '0 / 15', ga_de_1000_3999: '0 / 0', ga_4000_plus: '0 / 0',
      vit_1_49: '0 / 6', vit_50_199: '0 / 0', vit_de_200_plus: '0 / 0', vit_2000_plus: '0 / 0',
      ngan_quy_1_50: '22 / 22', ngan_quy_50_199: '0 / 0', ngan_quy_200_plus: '0 / 0',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
    // Dữ liệu giả định khác từ image_ae3eb7.png / image_ae46d3.png cho các xã khác nếu cần
    {
      maTinh: 87, maXa: 29962, tenXa: 'Xã Thường Thới Hậu', // Ví dụ xã này nếu có dữ liệu khác
      lon_1_9: '0 / 1', lon_10_29: '0 / 0', lon_30_99: '0 / 0', lon_100_plus: '0 / 0',
      ga_thit_1_999: '0 / 10', ga_de_1000_3999: '0 / 0', ga_4000_plus: '0 / 0',
      vit_1_49: '0 / 5', vit_50_199: '0 / 0', vit_de_200_plus: '0 / 0', vit_2000_plus: '0 / 0',
      ngan_quy_1_50: '5 / 5', ngan_quy_50_199: '0 / 0', ngan_quy_200_plus: '0 / 0',
      trau_duoi_30: '0 / 0', trau_30_plus: '0 / 0', bo_thit_duoi_20: '0 / 0', bo_thit_20_plus: '0 / 0',
      bo_sua_duoi_20: '0 / 0', bo_sua_20_plus: '0 / 0'
    },
  ];

  data.value = mockData;
  loading.value = false;
  applyFilters();
};

const applyFilters = () => {
  let tempFilteredData = data.value.filter(item => {
    const matchesKyDieuTra = filters.value.kyDieuTra === '' || String(filters.value.kyDieuTra) === String(1); // Mặc định là 1 nếu không có trong data
    const matchesNam = filters.value.nam === '' || String(filters.value.nam) === String(2025); // Mặc định là 2025
    const matchesTinhTP = filters.value.tinhTP === '' || filters.value.tinhTP.split('-')[0] === String(item.maTinh);
    // Quận/Huyện trong bảng này không hiển thị cột riêng mà chỉ là bộ lọc, nên không cần so sánh với item.maHuyen
    const matchesXaPhuong = filters.value.xaPhuong === '' || filters.value.xaPhuong.split('-')[0] === String(item.maXa);

    return matchesKyDieuTra && matchesNam && matchesTinhTP && matchesXaPhuong;
  });
  filteredData.value = tempFilteredData;
};

onMounted(() => {
  fetchTableData();
});

watch(filters, () => {
  applyFilters();
}, { deep: true });

const exportExcel = () => {
  alert('Chức năng "Xuất Excel"');
};
</script>

<style scoped>
.bang-ke-ho-tong-hop-page {
  padding: 20px;
  background-color: #f0f2f5;
  font-family: Arial, sans-serif;
}

/* Filter Section */
.filter-section {
  background-color: #ffffff;
  padding: 15px 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  margin-bottom: 20px;
}

.filter-row {
  display: flex;
  flex-wrap: wrap;
  gap: 15px 25px;
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
  font-weight: bold;
}

.filter-group select {
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 13px;
  background-color: white;
  min-width: 150px;
  height: 30px;
}

.filter-button {
  height: 30px;
  align-self: flex-end;
  padding: 0 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  color: white;
  transition: background-color 0.2s ease;
}

.btn-secondary { background-color: #6c757d; }
.btn-secondary:hover { background-color: #5a6268; }

.btn-info { background-color: #17a2b8; }
.btn-info:hover { background-color: #138496; }


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
  min-width: 1800px; /* Adjust this value based on the total width of all columns */
}

.data-table th, .data-table td {
  border: 1px solid #c0c0c0;
  padding: 8px 10px;
  text-align: left;
  font-size: 12px;
  white-space: nowrap; /* Prevent text wrapping in headers */
}

.data-table thead th {
  background-color: #4682B4; /* SteelBlue */
  color: white;
  font-weight: bold;
  text-align: center;
}

.data-table tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}

.data-table tbody tr:hover {
  background-color: #e6f0ff;
}

.loading-row td, .no-data-row td {
  text-align: center;
  font-style: italic;
  color: #777;
  padding: 20px;
}
</style>