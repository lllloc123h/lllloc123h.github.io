<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Quản lý thông báo</h2>

                <div class="card p-3 mb-4 shadow-sm">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-2 col-sm-6">
                            <label for="nam" class="form-label visually-hidden">Năm</label>
                            <select v-model="filters.nam" class="form-select" id="nam">
                                <option value="">2025</option>
                                <option>2024</option>
                                <option>2023</option>
                                <option>2022</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-2 col-sm-6">
                            <label for="tinhTp" class="form-label visually-hidden">Tỉnh/TP</label>
                            <select v-model="filters.maTinh" class="form-select" id="tinhTp">
                                <option value="">87 - Đồng Tháp</option>
                                <option v-for="tinh in provinces" :key="tinh.ma" :value="tinh.ma">{{ tinh.ma }} - {{ tinh.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-3 col-sm-6">
                            <label for="filterType" class="form-label visually-hidden">Theo nội dung thông báo</label>
                            <select v-model="filters.filterType" class="form-select" id="filterType">
                                <option value="noiDungThongBao">Theo nội dung thông báo</option>
                                <option value="ngayTao">Theo ngày tạo</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-3 col-sm-6">
                            <label for="noiDungThongBaoSearch" class="form-label visually-hidden">Nội dung thông báo</label>
                            <input type="text" v-model="filters.searchText" class="form-control" id="noiDungThongBaoSearch" placeholder="--Nội dung thông báo--">
                        </div>
                        <div class="col-md-auto">
                            <button class="btn btn-primary" @click="applyFilters">
                                <i class="bi bi-search"></i> Xem
                            </button>
                        </div>
                         <div class="col-md-auto">
                            <button class="btn btn-success" @click="exportExcel">
                                <i class="bi bi-file-earmark-excel"></i> Xuất Excel
                            </button>
                        </div>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-bordered table-hover caption-top">
                        <caption>Tổng số bản ghi: {{ filteredNotifications.length }}</caption>
                        <thead class="table-primary">
                            <tr>
                                <th scope="col" style="width: 70%;">Nội dung thông báo</th>
                                <th scope="col" style="width: 30%;">Ngày tạo</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(item, index) in filteredNotifications" :key="index">
                                <td>{{ item.noiDungThongBao }}</td>
                                <td>{{ item.ngayTao }}</td>
                            </tr>
                            <tr v-if="filteredNotifications.length === 0">
                                <td colspan="2" class="text-center">Không tìm thấy dữ liệu nào.</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css'; // Để sử dụng icon Bootstrap

// --- State Variables ---
const filters = ref({
    nam: '2025',             // Mặc định năm 2025
    maTinh: '87',            // Mặc định Đồng Tháp
    filterType: 'noiDungThongBao', // Mặc định lọc theo nội dung
    searchText: ''           // Nội dung tìm kiếm
});

const provinces = ref([
    { ma: '87', ten: 'Đồng Tháp' },
    { ma: '88', ten: 'Long An' },
    { ma: '89', ten: 'An Giang' },
    { ma: '92', ten: 'Cần Thơ' },
    { ma: '90', ten: 'Tiền Giang' }
]);

// Dữ liệu mẫu đa dạng cho thông báo
const allNotifications = ref([
    {
        nam: '2025',
        maTinh: '87',
        noiDungThongBao: 'Thông báo về kế hoạch điều tra dân số năm 2025 tại Đồng Tháp.',
        ngayTao: '2025-01-10'
    },
    {
        nam: '2025',
        maTinh: '87',
        noiDungThongBao: 'Cập nhật hướng dẫn thu thập dữ liệu hộ gia đình Qúy 2/2025.',
        ngayTao: '2025-04-05'
    },
    {
        nam: '2025',
        maTinh: '88',
        noiDungThongBao: 'Lịch tập huấn phần mềm điều tra CAPI cho Long An.',
        ngayTao: '2025-03-20'
    },
    {
        nam: '2024',
        maTinh: '87',
        noiDungThongBao: 'Tổng kết công tác điều tra nông nghiệp năm 2024 tỉnh Đồng Tháp.',
        ngayTao: '2024-12-15'
    },
    {
        nam: '2024',
        maTinh: '89',
        noiDungThongBao: 'Thông báo triển khai giai đoạn 2 điều tra doanh nghiệp An Giang.',
        ngayTao: '2024-08-01'
    },
    {
        nam: '2025',
        maTinh: '92',
        noiDungThongBao: 'Hướng dẫn sử dụng biểu mẫu mới cho Cần Thơ.',
        ngayTao: '2025-02-28'
    },
    {
        nam: '2023',
        maTinh: '87',
        noiDungThongBao: 'Quyết định về việc bổ sung nhân sự cho đội điều tra Đồng Tháp.',
        ngayTao: '2023-06-01'
    },
    {
        nam: '2025',
        maTinh: '87',
        noiDungThongBao: 'Nhắc nhở nộp báo cáo tiến độ điều tra định kỳ.',
        ngayTao: '2025-05-20'
    },
    {
        nam: '2025',
        maTinh: '87',
        noiDungThongBao: 'Khẩn: Yêu cầu kiểm tra lại dữ liệu điều tra tại huyện Hồng Ngự.',
        ngayTao: '2025-06-25'
    },
    {
        nam: '2025',
        maTinh: '88',
        noiDungThongBao: 'Thông báo về cuộc họp trực tuyến với các địa phương Long An.',
        ngayTao: '2025-06-18'
    }
]);

// --- Computed Properties ---
const filteredNotifications = computed(() => {
    let data = allNotifications.value;

    // Lọc theo Năm
    if (filters.value.nam !== '') {
        data = data.filter(item => item.nam === filters.value.nam);
    }

    // Lọc theo Tỉnh
    if (filters.value.maTinh !== '') {
        data = data.filter(item => item.maTinh === filters.value.maTinh);
    }

    // Lọc theo Nội dung thông báo hoặc Ngày tạo dựa trên searchText
    if (filters.value.searchText.trim() !== '') {
        const lowerCaseSearchText = filters.value.searchText.trim().toLowerCase();
        if (filters.value.filterType === 'noiDungThongBao') {
            data = data.filter(item =>
                item.noiDungThongBao.toLowerCase().includes(lowerCaseSearchText)
            );
        } else if (filters.value.filterType === 'ngayTao') {
            data = data.filter(item =>
                item.ngayTao.includes(filters.value.searchText.trim()) // Ngày tạo thường không cần toLowerCase()
            );
        }
    }

    return data;
});

// --- Methods ---
function applyFilters() {
    // Khi bộ lọc thay đổi, computed property `filteredNotifications` sẽ tự động cập nhật bảng.
    console.log("Áp dụng bộ lọc:", filters.value);
}

function exportExcel() {
    alert('Chức năng Xuất Excel (cần implement logic) cho dữ liệu thông báo.');
    // Ở đây, bạn sẽ sử dụng thư viện như SheetJS (xlsx) để xuất `filteredNotifications.value` ra file Excel.
    // Ví dụ:
    /*
    import * as XLSX from 'xlsx';
    const ws = XLSX.utils.json_to_sheet(filteredNotifications.value);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Thông báo");
    XLSX.writeFile(wb, "ThongBaoDieuTra.xlsx");
    */
}

// --- Lifecycle Hooks ---
onMounted(() => {
    // Để đảm bảo bảng hiển thị đúng với giá trị mặc định khi component được tải.
    applyFilters();
});
</script>

<style scoped>
@import url("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css");

.container-fluid {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f8f9fa;
    padding-bottom: 2rem;
}

h2 {
    color: #343a40;
    font-weight: 600;
}

.card {
    border: none;
    border-radius: 0.5rem;
    box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
}

.form-label {
    font-weight: 500;
    color: #495057;
}

.table-responsive {
    margin-top: 1.5rem;
    overflow-x: auto;
}

.table {
    white-space: nowrap; /* Ngăn không cho nội dung trong bảng bị xuống dòng */
}

.table thead th {
    vertical-align: middle;
    background-color: #e9ecef;
    border-bottom: 2px solid #dee2e6;
    text-align: left; /* Tiêu đề cột căn trái theo hình ảnh */
    padding: 0.75rem;
}

.table-bordered th, .table-bordered td {
    border: 1px solid #dee2e6;
    padding: 0.75rem;
}

.table-hover tbody tr:hover {
    background-color: #e2e6ea;
}

.btn-primary {
    background-color: #007bff;
    border-color: #007bff;
}

.btn-success {
    background-color: #28a745;
    border-color: #28a745;
}

.btn i {
    margin-right: 0.5rem;
}

/* Custom styles for visually hidden labels */
.visually-hidden {
    position: absolute !important;
    width: 1px !important;
    height: 1px !important;
    padding: 0 !important;
    margin: -1px !important;
    overflow: hidden !important;
    clip: rect(0, 0, 0, 0) !important;
    white-space: nowrap !important;
    border: 0 !important;
}

/* Responsive adjustments */
@media (max-width: 767.98px) {
    .col-md-2, .col-md-3, .col-lg-2, .col-lg-3, .col-sm-6, .col-md-auto {
        width: 100%;
        margin-bottom: 1rem;
    }
    .card .row.g-3 > div:last-child {
        margin-bottom: 0;
    }
}
</style>