<template>
  <div class="bang-ke-ho-vat-nuoi-khac-page">
    <div class="top-actions">
      <button class="btn btn-primary" @click="themMoi">
        <i class="icon-plus"></i> Thêm mới
      </button>
      <button class="btn btn-secondary" @click="suaHo">
        <i class="icon-edit"></i> Sửa
      </button>
      <button class="btn btn-danger" @click="xoaHo">
        <i class="icon-delete"></i> Xóa
      </button>
      <button class="btn btn-info" @click="themTuFileExcel">
        <i class="icon-excel"></i> Thêm từ file Excel
      </button>
      <button class="btn btn-success" @click="xuatDuLieuXa">
        <i class="icon-download"></i> Xuất dữ liệu xã
      </button>
      <button class="btn btn-export" @click="xuatExcelTheoXa">
        <i class="icon-export-excel"></i> Xuất Excel theo xã
      </button>
      <button class="btn btn-export" @click="xuatExcelTheoTinh">
        <i class="icon-export-excel"></i> Xuất Excel theo Tỉnh
      </button>
    </div>

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
            <th>STT Hộ</th>
            <th>Mã Tỉnh</th>
            <th>Mã Huyện</th>
            <th>Mã Xã</th>
            <th>Mã Thôn</th>
            <th>Tên Thôn</th>
            <th>Họ và tên chủ hộ</th>
            <th>Loại vật nuôi</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading" class="loading-row">
            <td :colspan="8">Đang tải dữ liệu...</td>
          </tr>
          <tr v-else-if="filteredData.length === 0" class="no-data-row">
            <td :colspan="8">Chưa có dữ liệu</td>
          </tr>
          <tr v-else v-for="(item, index) in filteredData" :key="index">
            <td>{{ item.sttHo }}</td>
            <td>{{ item.maTinh }}</td>
            <td>{{ item.maHuyen }}</td>
            <td>{{ item.maXa }}</td>
            <td>{{ item.maThon }}</td>
            <td>{{ item.tenThon }}</td>
            <td>{{ item.hoVaTenChuHo }}</td>
            <td>{{ item.loaiVatNuoi }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';

const filters = ref({
  kyDieuTra: '1',
  nam: '2025',
  tinhTP: '87-DongThap',
  quanHuyen: '870-HongNgu',
  xaPhuong: '29963-LongKhanhB', // Giá trị mặc định theo image_ad6137.png
});

const data = ref([]);
const loading = ref(true);
const filteredData = ref([]);

const fetchTableData = async () => {
  loading.value = true;
  await new Promise(resolve => setTimeout(resolve, 500)); // Simulate API call delay

  // Dữ liệu giả định cho "Bảng kê hộ vật nuôi khác"
  const mockData = [
    // Dữ liệu từ image_ae2c2f.png (Có vẻ là dữ liệu chung, không rõ từ Xã nào, tôi sẽ giả định từ Long Khánh B)
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '01', sttHo: '0001', tenThon: 'Ấp 1', hoVaTenChuHo: 'Nguyễn Thị Ba Ba', loaiVatNuoi: 'Trăn', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '02', sttHo: '0010', tenThon: 'Ấp 2', hoVaTenChuHo: 'Lê Văn Lợi', loaiVatNuoi: 'Vịt xiêm', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '03', sttHo: '0008', tenThon: 'Ấp 3', hoVaTenChuHo: 'Lê Văn Sang', loaiVatNuoi: 'Ếch', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '03', sttHo: '0009', tenThon: 'Ấp 3', hoVaTenChuHo: 'Ngô Văn Khiêm', loaiVatNuoi: 'Cá sấu', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '03', sttHo: '0011', tenThon: 'Ấp 3', hoVaTenChuHo: 'Lâm Thị Hiền', loaiVatNuoi: 'Chó', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '03', sttHo: '0012', tenThon: 'Ấp 3', hoVaTenChuHo: 'Lê Văn Úa', loaiVatNuoi: 'Mèo', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '03', sttHo: '0013', tenThon: 'Ấp 3', hoVaTenChuHo: 'Nguyễn Văn Sáu', loaiVatNuoi: 'Thỏ', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '01', sttHo: '0001', tenThon: 'Ấp Chòm Xoài', hoVaTenChuHo: 'Nguyễn Văn Nữ', loaiVatNuoi: 'Cá', trangThai: 'daduyet' // Giả định là Xã Thới Thuận hoặc Long Khánh B
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '01', sttHo: '0002', tenThon: 'Ấp Chòm Xoài', hoVaTenChuHo: 'Mai Kim Hạnh', loaiVatNuoi: 'Ba ba', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '02', sttHo: '0003', tenThon: 'Ấp Gióng Bàng', hoVaTenChuHo: 'Ngô Thị Bọt', loaiVatNuoi: 'Cá tra', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '02', sttHo: '0004', tenThon: 'Ấp Gióng Bàng', hoVaTenChuHo: 'Lương Văn Ồ', loaiVatNuoi: 'Cá lóc', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '02', sttHo: '0005', tenThon: 'Ấp Gióng Bàng', hoVaTenChuHo: 'Phạm Văn Ngày', loaiVatNuoi: 'Tôm', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '02', sttHo: '0006', tenThon: 'Ấp Gióng Bàng', hoVaTenChuHo: 'Huỳnh Văn Chiều', loaiVatNuoi: 'Cua', trangThai: 'daduyet'
    },
    // Thêm các dữ liệu khác nếu cần để test các bộ lọc
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '01', sttHo: '0020', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Nguyễn Văn A', loaiVatNuoi: 'Cá tra', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '01', sttHo: '0021', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Trần Thị B', loaiVatNuoi: 'Thủy sản', trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0022', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Nguyễn Văn C', loaiVatNuoi: 'Chim', trangThai: 'daduyet'
    },
  ];

  data.value = mockData;
  loading.value = false;
  applyFilters(); // Áp dụng bộ lọc ngay sau khi tải dữ liệu ban đầu
};

const applyFilters = () => {
  let tempFilteredData = data.value.filter(item => {
    const matchesKyDieuTra = filters.value.kyDieuTra === '' || String(filters.value.kyDieuTra) === String(item.kyDieuTra);
    const matchesNam = filters.value.nam === '' || String(filters.value.nam) === String(item.nam);
    const matchesTinhTP = filters.value.tinhTP === '' || filters.value.tinhTP.split('-')[0] === String(item.maTinh);
    const matchesQuanHuyen = filters.value.quanHuyen === '' || filters.value.quanHuyen.split('-')[0] === String(item.maHuyen);
    const matchesXaPhuong = filters.value.xaPhuong === '' || filters.value.xaPhuong.split('-')[0] === String(item.maXa);
    // filter 'thon' không có trong hình ảnh này
    // filter 'trangThai' không có trong hình ảnh này

    return matchesKyDieuTra && matchesNam && matchesTinhTP && matchesQuanHuyen && matchesXaPhuong;
  });
  filteredData.value = tempFilteredData;
};

onMounted(() => {
  fetchTableData();
});

// Watch filters to apply filtering automatically
watch(filters, () => {
  applyFilters();
}, { deep: true });

// Placeholder functions for buttons
const themMoi = () => {
  alert('Chức năng "Thêm mới"');
};

const suaHo = () => {
  alert('Chức năng "Sửa"');
};

const xoaHo = () => {
  alert('Chức năng "Xóa"');
};

const themTuFileExcel = () => {
  alert('Chức năng "Thêm từ file Excel"');
};

const xuatDuLieuXa = () => {
  alert('Chức năng "Xuất dữ liệu xã"');
};

const xuatExcelTheoXa = () => {
  alert('Chức năng "Xuất Excel theo xã"');
};

const xuatExcelTheoTinh = () => {
  alert('Chức năng "Xuất Excel theo Tỉnh"');
};

const exportExcel = () => {
  alert('Chức năng "Xuất Excel"'); // Nút này có vẻ trùng với "Xuất Excel theo xã" hoặc "Xuất Excel theo Tỉnh" trong ngữ cảnh chung của hình ảnh đầu tiên.
};
</script>

<style scoped>
.bang-ke-ho-vat-nuoi-khac-page {
  padding: 20px;
  background-color: #f0f2f5;
  font-family: Arial, sans-serif;
}

.top-actions {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  padding: 10px 0;
  border-bottom: 1px solid #e0e0e0;
}

.top-actions .btn {
  display: flex;
  align-items: center;
  padding: 6px 12px;
  font-size: 13px;
}

.top-actions .btn .icon-plus::before { content: '➕'; margin-right: 5px; }
.top-actions .btn .icon-edit::before { content: '✏️'; margin-right: 5px; }
.top-actions .btn .icon-delete::before { content: '🗑️'; margin-right: 5px; }
.top-actions .btn .icon-excel::before { content: '📄'; margin-right: 5px; }
.top-actions .btn .icon-download::before { content: '📥'; margin-right: 5px; }
.top-actions .btn .icon-export-excel::before { content: '📤'; margin-right: 5px; }


.btn {
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  color: white;
  transition: background-color 0.2s ease;
}

.btn-primary { background-color: #007bff; }
.btn-primary:hover { background-color: #0056b3; }

.btn-secondary { background-color: #6c757d; }
.btn-secondary:hover { background-color: #5a6268; }

.btn-danger { background-color: #dc3545; }
.btn-danger:hover { background-color: #c82333; }

.btn-info { background-color: #17a2b8; }
.btn-info:hover { background-color: #138496; }

.btn-success { background-color: #28a745; }
.btn-success:hover { background-color: #218838; }

.btn-export { background-color: #ffc107; color: #333; } /* Vàng cam cho nút xuất */
.btn-export:hover { background-color: #e0a800; }


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
  height: 30px; /* Đảm bảo chiều cao phù hợp với select */
  align-self: flex-end; /* Căn chỉnh nút với đáy của các select */
  padding: 0 15px; /* Điều chỉnh padding cho phù hợp */
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
  min-width: 900px; /* Chiều rộng tối thiểu cho các cột hiện có */
}

.data-table th, .data-table td {
  border: 1px solid #c0c0c0;
  padding: 8px 10px;
  text-align: left;
  font-size: 12px;
  white-space: nowrap;
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