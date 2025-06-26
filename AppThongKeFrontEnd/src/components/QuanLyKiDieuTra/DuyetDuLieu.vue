<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Quản lý trạng thái và thông tin phê duyệt điều tra</h2>

                <div class="card p-3 mb-4 shadow-sm">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-2 col-sm-6">
                            <label for="kyDieuTra" class="form-label">Kỳ điều tra</label>
                            <select v-model="filters.kyDieuTra" class="form-select" id="kyDieuTra">
                                <option value="">--Tất cả--</option>
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                            </select>
                        </div>
                        <div class="col-md-2 col-sm-6">
                            <label for="nam" class="form-label">Năm</label>
                            <select v-model="filters.nam" class="form-select" id="nam">
                                <option value="">--Tất cả--</option>
                                <option>2025</option>
                                <option>2024</option>
                                <option>2023</option>
                                <option>2022</option>
                            </select>
                        </div>
                        <div class="col-md-4 col-lg-3 col-sm-6">
                            <label for="tinhTp" class="form-label">Tỉnh/TP</label>
                            <select v-model="filters.maTinh" class="form-select" id="tinhTp">
                                <option value="">--Tất cả Tỉnh--</option>
                                <option v-for="tinh in provinces" :key="tinh.ma" :value="tinh.ma">{{ tinh.ma }} - {{ tinh.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-auto">
                            <button class="btn btn-primary" @click="applyFilters">
                                <i class="bi bi-search"></i> Xem
                            </button>
                        </div>
                    </div>
                </div>

                <ul class="nav nav-tabs mb-3" id="myTab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" :class="{ active: activeTab === 'trangThaiDieuTra' }" @click="activeTab = 'trangThaiDieuTra'" type="button" role="tab" aria-controls="trangThaiDieuTra-tab-pane" aria-selected="true">Trạng thái điều tra</button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" :class="{ active: activeTab === 'thongTinPheDuyet' }" @click="activeTab = 'thongTinPheDuyet'" type="button" role="tab" aria-controls="thongTinPheDuyet-tab-pane" aria-selected="false">Thông tin phê duyệt</button>
                    </li>
                </ul>

                <div class="tab-content" id="myTabContent">
                    <div class="tab-pane fade" :class="{ 'show active': activeTab === 'trangThaiDieuTra' }" id="trangThaiDieuTra-tab-pane" role="tabpanel" aria-labelledby="trangThaiDieuTra-tab">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover caption-top">
                                <caption>Tổng số bản ghi: {{ filteredInvestigationStatusData.length }}</caption>
                                <thead class="table-primary">
                                    <tr>
                                        <th scope="col">Mã Tỉnh</th>
                                        <th scope="col">Ngày bắt đầu điều tra</th>
                                        <th scope="col">Ngày kết thúc điều tra</th>
                                        <th scope="col">Trạng thái bắt đầu</th>
                                        <th scope="col">Trạng thái kết thúc</th>
                                        <th scope="col">Trạng thái CAPI</th>
                                        <th scope="col">Trạng thái WEB</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(item, index) in filteredInvestigationStatusData" :key="index">
                                        <td>{{ item.maTinh }}</td>
                                        <td>{{ item.ngayBatDau }}</td>
                                        <td>{{ item.ngayKetThuc }}</td>
                                        <td>{{ item.trangThaiBatDau }}</td>
                                        <td>{{ item.trangThaiKetThuc }}</td>
                                        <td>{{ item.trangThaiCAPI }}</td>
                                        <td>{{ item.trangThaiWEB }}</td>
                                    </tr>
                                    <tr v-if="filteredInvestigationStatusData.length === 0">
                                        <td colspan="7" class="text-center">Không tìm thấy dữ liệu nào.</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="tab-pane fade" :class="{ 'show active': activeTab === 'thongTinPheDuyet' }" id="thongTinPheDuyet-tab-pane" role="tabpanel" aria-labelledby="thongTinPheDuyet-tab">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover caption-top">
                                <caption>Tổng số bản ghi: {{ filteredApprovalData.length }}</caption>
                                <thead class="table-primary">
                                    <tr>
                                        <th rowspan="2" scope="col">Mã Tỉnh</th>
                                        <th rowspan="2" scope="col">Tên Tỉnh</th>
                                        <th colspan="3" class="text-center">Thông tin tại địa phương</th>
                                        <th colspan="3" class="text-center">Thông tin tại TW</th>
                                    </tr>
                                    <tr>
                                        <th scope="col" class="text-center">Trạng thái</th>
                                        <th scope="col" class="text-center">Thời điểm thực hiện</th>
                                        <th scope="col" class="text-center">Người thực hiện</th>
                                        <th scope="col" class="text-center">Trạng thái</th>
                                        <th scope="col" class="text-center">Thời điểm thực hiện</th>
                                        <th scope="col" class="text-center">Người thực hiện</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(item, index) in filteredApprovalData" :key="index">
                                        <td>{{ item.maTinh }}</td>
                                        <td>{{ item.tenTinh }}</td>
                                        <td>{{ item.localStatus }}</td>
                                        <td>{{ item.localTimestamp }}</td>
                                        <td>{{ item.localUser }}</td>
                                        <td>{{ item.centralStatus }}</td>
                                        <td>{{ item.centralTimestamp }}</td>
                                        <td>{{ item.centralUser }}</td>
                                    </tr>
                                     <tr v-if="filteredApprovalData.length === 0">
                                        <td colspan="8" class="text-center">Không tìm thấy dữ liệu nào.</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';

// --- State Variables ---
const filters = ref({
    kyDieuTra: '4', // Mặc định kỳ 4
    nam: '2025',     // Mặc định năm 2025
    maTinh: '87'    // Mặc định Đồng Tháp
});

const activeTab = ref('trangThaiDieuTra'); // Mặc định tab "Trạng thái điều tra"

const provinces = ref([
    { ma: '87', ten: 'Đồng Tháp' },
    { ma: '88', ten: 'Long An' },
    { ma: '89', ten: 'An Giang' },
    { ma: '92', ten: 'Cần Thơ' },
    { ma: '90', ten: 'Tiền Giang' }
]);

// --- Dữ liệu mẫu đa dạng ---

const mockInvestigationStatusData = ref([
    // Dữ liệu từ ảnh (Đồng Tháp, Kỳ 4, 2025)
    { kyDieuTra: '4', nam: '2025', maTinh: '87', ngayBatDau: '01/04/2025', ngayKetThuc: '15/04/2025', trangThaiBatDau: 'Đã bắt đầu', trangThaiKetThuc: 'Đã kết thúc', trangThaiCAPI: 'Đã khóa', trangThaiWEB: 'Đã khóa' },

    // Dữ liệu Đồng Tháp, Kỳ 3, 2024
    { kyDieuTra: '3', nam: '2024', maTinh: '87', ngayBatDau: '01/07/2024', ngayKetThuc: '15/07/2024', trangThaiBatDau: 'Đã bắt đầu', trangThaiKetThuc: 'Đã kết thúc', trangThaiCAPI: 'Đã khóa', trangThaiWEB: 'Đã khóa' },
    // Dữ liệu Đồng Tháp, Kỳ 2, 2024
    { kyDieuTra: '2', nam: '2024', maTinh: '87', ngayBatDau: '01/04/2024', ngayKetThuc: '15/04/2024', trangThaiBatDau: 'Đã bắt đầu', trangThaiKetThuc: 'Đã kết thúc', trangThaiCAPI: 'Đã khóa', trangThaiWEB: 'Đã khóa' },

    // Dữ liệu Long An, Kỳ 4, 2025 (Chưa kết thúc)
    { kyDieuTra: '4', nam: '2025', maTinh: '88', ngayBatDau: '10/05/2025', ngayKetThuc: '30/05/2025', trangThaiBatDau: 'Đã bắt đầu', trangThaiKetThuc: 'Chưa kết thúc', trangThaiCAPI: 'Đang mở', trangThaiWEB: 'Đang mở' },
    // Dữ liệu Long An, Kỳ 1, 2023 (Đã kết thúc)
    { kyDieuTra: '1', nam: '2023', maTinh: '88', ngayBatDau: '01/01/2023', ngayKetThuc: '15/01/2023', trangThaiBatDau: 'Đã bắt đầu', trangThaiKetThuc: 'Đã kết thúc', trangThaiCAPI: 'Đã khóa', trangThaiWEB: 'Đã khóa' },

    // Dữ liệu An Giang, Kỳ 4, 2025 (Mới bắt đầu)
    { kyDieuTra: '4', nam: '2025', maTinh: '89', ngayBatDau: '20/05/2025', ngayKetThuc: '10/06/2025', trangThaiBatDau: 'Đã bắt đầu', trangThaiKetThuc: 'Chưa kết thúc', trangThaiCAPI: 'Đang mở', trangThaiWEB: 'Đang mở' },
    // Dữ liệu An Giang, Kỳ 2, 2023 (Đã kết thúc)
    { kyDieuTra: '2', nam: '2023', maTinh: '89', ngayBatDau: '01/04/2023', ngayKetThuc: '20/04/2023', trangThaiBatDau: 'Đã bắt đầu', trangThaiKetThuc: 'Đã kết thúc', trangThaiCAPI: 'Đã khóa', trangThaiWEB: 'Đã khóa' },

    // Dữ liệu Cần Thơ, Kỳ 4, 2025 (Chưa bắt đầu)
    { kyDieuTra: '4', nam: '2025', maTinh: '92', ngayBatDau: '01/06/2025', ngayKetThuc: '20/06/2025', trangThaiBatDau: 'Chưa bắt đầu', trangThaiKetThuc: 'Chưa kết thúc', trangThaiCAPI: 'Chưa mở', trangThaiWEB: 'Chưa mở' },
]);

const mockApprovalData = ref([
    // Dữ liệu từ ảnh (Đồng Tháp, Kỳ 4, 2025)
    { kyDieuTra: '4', nam: '2025', maTinh: '87', tenTinh: 'Đồng Tháp', localStatus: 'Đã duyệt', localTimestamp: '11:30:2025 - 16/5/2025', localUser: 'nguyentdh', centralStatus: 'Đã duyệt', centralTimestamp: '9:25:AM - 21/5/2025', centralUser: 'thuyena' },

    // Dữ liệu Long An, Kỳ 4, 2025 (Đang xử lý tại địa phương)
    { kyDieuTra: '4', nam: '2025', maTinh: '88', tenTinh: 'Long An', localStatus: 'Đang xử lý', localTimestamp: '10:00:2025 - 20/5/2025', localUser: 'nguyenlt', centralStatus: 'Chưa duyệt', centralTimestamp: '', centralUser: '' },

    // Dữ liệu An Giang, Kỳ 4, 2025 (Chưa duyệt)
    { kyDieuTra: '4', nam: '2025', maTinh: '89', tenTinh: 'An Giang', localStatus: 'Chưa duyệt', localTimestamp: '', localUser: '', centralStatus: 'Chưa duyệt', centralTimestamp: '', centralUser: '' },

    // Dữ liệu Đồng Tháp, Kỳ 3, 2024 (Đã duyệt cả hai)
    { kyDieuTra: '3', nam: '2024', maTinh: '87', tenTinh: 'Đồng Tháp', localStatus: 'Đã duyệt', localTimestamp: '10:00:2024 - 20/7/2024', localUser: 'nguyentdh', centralStatus: 'Đã duyệt', centralTimestamp: '14:30:2024 - 25/7/2024', centralUser: 'thuyena' },

    // Dữ liệu Long An, Kỳ 1, 2023 (Chỉ duyệt địa phương)
    { kyDieuTra: '1', nam: '2023', maTinh: '88', tenTinh: 'Long An', localStatus: 'Đã duyệt', localTimestamp: '09:00:2023 - 18/1/2023', localUser: 'phamth', centralStatus: 'Chưa duyệt', centralTimestamp: '', centralUser: '' },
]);


// --- Computed Properties ---
const filteredInvestigationStatusData = computed(() => {
    let data = mockInvestigationStatusData.value;
    return data.filter(item => {
        const matchesKy = filters.value.kyDieuTra === '' || item.kyDieuTra === filters.value.kyDieuTra;
        const matchesNam = filters.value.nam === '' || item.nam === filters.value.nam;
        const matchesTinh = filters.value.maTinh === '' || item.maTinh === filters.value.maTinh;
        return matchesKy && matchesNam && matchesTinh;
    });
});

const filteredApprovalData = computed(() => {
    let data = mockApprovalData.value;
    return data.filter(item => {
        const matchesKy = filters.value.kyDieuTra === '' || item.kyDieuTra === filters.value.kyDieuTra;
        const matchesNam = filters.value.nam === '' || item.nam === filters.value.nam;
        const matchesTinh = filters.value.maTinh === '' || item.maTinh === filters.value.maTinh;
        return matchesKy && matchesNam && matchesTinh;
    });
});

// --- Methods ---
function applyFilters() {
    // Khi bộ lọc thay đổi, các computed properties sẽ tự động cập nhật bảng.
    console.log("Áp dụng bộ lọc:", filters.value);
}

// --- Lifecycle Hooks ---
onMounted(() => {
    // Không cần fetch dữ liệu từ API giả nữa vì đã có dữ liệu mẫu tĩnh
    // Tuy nhiên, bạn có thể gọi applyFilters() để đảm bảo bảng hiển thị đúng với giá trị mặc định ban đầu
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
    text-align: center; /* Căn giữa cho tiêu đề cột */
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

/* Tab styling */
.nav-tabs .nav-link {
    color: #495057;
    border-top-left-radius: 0.25rem;
    border-top-right-radius: 0.25rem;
    border: 1px solid transparent;
    margin-bottom: -1px;
}

.nav-tabs .nav-link.active {
    color: #007bff;
    background-color: #fff;
    border-color: #dee2e6 #dee2e6 #fff;
}
.tab-pane {
    padding-top: 1rem;
    border: 1px solid #dee2e6;
    border-top: none;
    border-bottom-left-radius: 0.25rem;
    border-bottom-right-radius: 0.25rem;
    background-color: #fff;
    box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
}
.caption-top {
    caption-side: top;
    text-align: right;
    padding-right: 0.75rem;
    color: #6c757d;
}

/* Responsive adjustments */
@media (max-width: 767.98px) {
    .col-md-2, .col-md-4, .col-lg-3, .col-sm-6, .col-md-auto {
        width: 100%;
        margin-bottom: 1rem;
    }
    .card .row.g-3 > div:last-child {
        margin-bottom: 0;
    }
    .nav-tabs {
        flex-wrap: wrap;
        border-bottom: none; /* Remove bottom border for better wrap display */
    }
    .nav-item {
        flex-grow: 1;
        margin-bottom: 0.5rem;
    }
    .nav-link {
        text-align: center;
    }
}
</style>