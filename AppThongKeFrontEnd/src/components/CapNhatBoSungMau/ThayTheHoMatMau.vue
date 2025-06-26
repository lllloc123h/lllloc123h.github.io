<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Thay hộ biểu mẫu bị mất trong quá trình điều tra</h2>

                <div class="d-flex flex-wrap gap-2 mb-3">
                    <button class="btn btn-danger">
                        <i class="bi bi-x-circle"></i> Xác nhận Hủy Mẫu
                    </button>
                    <button class="btn btn-danger">
                        <i class="bi bi-x-circle-fill"></i> Hủy Mẫu
                    </button>
                    <button class="btn btn-primary">
                        <i class="bi bi-pencil-square"></i> Thay mẫu điều tra
                    </button>
                    <button class="btn btn-success">
                        <i class="bi bi-check-circle"></i> Hủy Thay mẫu
                    </button>
                    <button class="btn btn-info text-white">
                        <i class="bi bi-file-earmark-excel"></i> Xuất Excel
                    </button>
                </div>

                <div class="card p-3 mb-4 shadow-sm">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-2 col-sm-6">
                            <label for="kyDieuTra" class="form-label visually-hidden">Kỳ điều tra</label>
                            <select v-model="filters.kyDieuTra" class="form-select" id="kyDieuTra">
                                <option value="4">4</option>
                                <option value="3">3</option>
                                <option value="2">2</option>
                                <option value="1">1</option>
                            </select>
                        </div>
                        <div class="col-md-2 col-sm-6">
                            <label for="nam" class="form-label visually-hidden">Năm</label>
                            <select v-model="filters.nam" class="form-select" id="nam">
                                <option>2025</option>
                                <option>2024</option>
                                <option>2023</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-2 col-sm-6">
                            <label for="tinhTp" class="form-label visually-hidden">Tỉnh/TP</label>
                            <select v-model="filters.maTinh" class="form-select" id="tinhTp" @change="fetchDistricts">
                                <option value="">--Chọn Tỉnh--</option>
                                <option v-for="tinh in provinces" :key="tinh.ma" :value="tinh.ma">{{ tinh.ma }} - {{
                                    tinh.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-2 col-sm-6">
                            <label for="quanHuyen" class="form-label visually-hidden">Quận/Huyện</label>
                            <select v-model="filters.maHuyen" class="form-select" id="quanHuyen" @change="fetchCommunes"
                                :disabled="!districts.length">
                                <option value="">--Chọn Huyện--</option>
                                <option v-for="huyen in districts" :key="huyen.ma" :value="huyen.ma">{{ huyen.ma }} - {{
                                    huyen.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-3 col-sm-6">
                            <label for="xaPhuong" class="form-label visually-hidden">Xã/Phường</label>
                            <select v-model="filters.maXa" class="form-select" id="xaPhuong"
                                :disabled="!communes.length">
                                <option value="">--Chọn Xã/Phường--</option>
                                <option v-for="xa in communes" :key="xa.ma" :value="xa.ma">{{ xa.ma }} - {{ xa.ten }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-auto">
                            <button class="btn btn-primary" @click="applyFilters">
                                <i class="bi bi-search"></i> Xem
                            </button>
                        </div>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-bordered table-hover caption-top">
                        <caption>Tổng số bản ghi: {{ filteredData.length }}</caption>
                        <thead class="table-primary">
                            <tr>
                                <th rowspan="2" scope="col">STT</th>
                                <th rowspan="2" scope="col">Mã Huyện</th>
                                <th rowspan="2" scope="col">Mã Xã</th>
                                <th rowspan="2" scope="col">Mã Thôn</th>
                                <th colspan="3" class="text-center">Hộ mất mẫu</th>
                                <th colspan="3" class="text-center">Hộ thay mẫu</th>
                            </tr>
                            <tr>
                                <th scope="col" class="text-center">Mã hộ</th>
                                <th scope="col" class="text-center">Họ và tên chủ hộ</th>
                                <th scope="col" class="text-center">Địa bàn</th>
                                <th scope="col" class="text-center">Mã hộ</th>
                                <th scope="col" class="text-center">Họ và tên chủ hộ</th>
                                <th scope="col" class="text-center">Địa bàn</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-if="filteredData.length === 0">
                                <td colspan="10" class="text-center">Chưa có dữ liệu</td>
                            </tr>
                            <tr v-for="(item, index) in filteredData" :key="index">
                                <td>{{ index + 1 }}</td>
                                <td>{{ item.maHuyen }}</td>
                                <td>{{ item.maXa }}</td>
                                <td>{{ item.maThon }}</td>
                                <td>{{ item.hoMatMau.maHo }}</td>
                                <td>{{ item.hoMatMau.tenChuHo }}</td>
                                <td>{{ item.hoMatMau.diaBan }}</td>
                                <td>{{ item.hoThayMau.maHo }}</td>
                                <td>{{ item.hoThayMau.tenChuHo }}</td>
                                <td>{{ item.hoThayMau.diaBan }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';

// --- State Variables ---
const filters = ref({
    kyDieuTra: '4',
    nam: '2025',
    maTinh: '87', // Mặc định Đồng Tháp
    maHuyen: '870', // Mặc định Hồng Ngự
    maXa: '29971'  // Mặc định Thị trấn Thường Thới Tiền
});

const provinces = ref([
    { ma: '87', ten: 'Đồng Tháp' },
    { ma: '88', ten: 'Long An' },
    { ma: '89', ten: 'An Giang' },
]);

const districts = ref([]);
const communes = ref([]);

// Dữ liệu mẫu
const allData = ref([]);

// --- Computed Properties ---
const filteredData = computed(() => {
    let data = allData.value;

    return data.filter(item =>
        (filters.value.kyDieuTra === '' || item.kyDieuTra === filters.value.kyDieuTra) &&
        (filters.value.nam === '' || item.nam === filters.value.nam) &&
        (filters.value.maTinh === '' || item.maTinh === filters.value.maTinh) &&
        (filters.value.maHuyen === '' || item.maHuyen === filters.value.maHuyen) &&
        (filters.value.maXa === '' || item.maXa === filters.value.maXa)
    );
});

// --- Methods ---
const mockApiCall = (data, delay = 100) => {
    return new Promise(resolve => setTimeout(() => resolve(data), delay));
};

async function fetchDistricts() {
    filters.value.maHuyen = ''; // Reset huyện khi tỉnh thay đổi
    filters.value.maXa = '';    // Reset xã khi tỉnh thay đổi
    districts.value = [];
    communes.value = [];
    if (filters.value.maTinh) {
        const mockDistrictsData = [
            { ma: '870', ten: 'Hồng Ngự', ma_tinh: '87' },
            { ma: '871', ten: 'Tam Nông', ma_tinh: '87' },
            { ma: '880', ten: 'TP Tân An', ma_tinh: '88' },
            { ma: '881', ten: 'Thủ Thừa', ma_tinh: '88' },
            { ma: '890', ten: 'TP Long Xuyên', ma_tinh: '89' },
            { ma: '891', ten: 'Châu Đốc', ma_tinh: '89' },
        ];
        districts.value = await mockApiCall(mockDistrictsData.filter(d => d.ma_tinh === filters.value.maTinh));
        if (districts.value.length > 0) {
            filters.value.maHuyen = districts.value[0].ma; // Tự động chọn huyện đầu tiên
        }
    }
}

async function fetchCommunes() {
    filters.value.maXa = ''; // Reset xã khi huyện thay đổi
    communes.value = [];
    if (filters.value.maHuyen) {
        const mockCommunesData = {
            '870': [ // Hồng Ngự
                { ma: '29956', ten: 'Xã Thường Phước 1' },
                { ma: '29962', ten: 'Xã Thường Thới Hậu A' },
                { ma: '29967', ten: 'Xã Thường Phước 2' },
                { ma: '29971', ten: 'Thị trấn Thường Thới Tiền' },
                { ma: '29974', ten: 'Xã Thường Lạc' },
                { ma: '29977', ten: 'Xã Long Thuận' },
                { ma: '29980', ten: 'Xã Long Khánh A' },
                { ma: '29983', ten: 'Xã Long Khánh B' },
                { ma: '29992', ten: 'Xã Phú Thuận B' },
                { ma: '29995', ten: 'Xã Phú Thuận A' },
                { ma: '29998', ten: 'Xã Phú Thuận' },
            ],
            '871': [ // Tam Nông
                { ma: '30000', ten: 'Thị trấn Tràm Chim' },
                { ma: '30001', ten: 'Xã Phú Cường' },
            ],
            // Thêm dữ liệu xã cho các huyện khác nếu cần
        };
        communes.value = await mockApiCall(mockCommunesData[filters.value.maHuyen] || []);
        if (communes.value.length > 0) {
            filters.value.maXa = communes.value[0].ma; // Tự động chọn xã đầu tiên
        }
    }
}

async function fetchReplacementData() {
    console.log("Đang lấy dữ liệu thay thế mẫu với bộ lọc:", filters.value);

    // Dữ liệu mẫu (chỉ hiển thị một số trường hợp để dễ hình dung)
    const dummyData = [
        {
            kyDieuTra: '4', nam: '2025', maTinh: '87', maHuyen: '870', maXa: '29971', maThon: '001',
            hoMatMau: { maHo: 'HN001', tenChuHo: 'Nguyễn Văn A', diaBan: 'Khu phố 1' },
            hoThayMau: { maHo: 'HN002', tenChuHo: 'Trần Thị B', diaBan: 'Khu phố 1' }
        },
        {
            kyDieuTra: '4', nam: '2025', maTinh: '87', maHuyen: '870', maXa: '29971', maThon: '002',
            hoMatMau: { maHo: 'HN003', tenChuHo: 'Lê Văn C', diaBan: 'Khu phố 2' },
            hoThayMau: { maHo: 'HN004', tenChuHo: 'Phạm Thị D', diaBan: 'Khu phố 2' }
        },
        {
            kyDieuTra: '4', nam: '2025', maTinh: '87', maHuyen: '870', maXa: '29962', maThon: '001',
            hoMatMau: { maHo: 'HA001', tenChuHo: 'Võ Văn E', diaBan: 'Ấp Thới An' },
            hoThayMau: { maHo: 'HA002', tenChuHo: 'Đặng Thị G', diaBan: 'Ấp Thới An' }
        },
        {
            kyDieuTra: '3', nam: '2024', maTinh: '88', maHuyen: '880', maXa: '30100', maThon: '001',
            hoMatMau: { maHo: 'TAN001', tenChuHo: 'Nguyễn Văn H', diaBan: 'Phường 1' },
            hoThayMau: { maHo: 'TAN002', tenChuHo: 'Trần Thị K', diaBan: 'Phường 1' }
        }
    ];

    allData.value = await mockApiCall(dummyData);
}

function applyFilters() {
    // Computed property `filteredData` sẽ tự động cập nhật bảng.
    console.log("Áp dụng bộ lọc:", filters.value);
}

function exportExcel() {
    alert('Chức năng Xuất Excel (cần implement logic)');
    // Sử dụng thư viện như SheetJS (xlsx) để xuất `filteredData.value` ra file Excel.
}

// --- Lifecycle Hooks and Watchers ---
onMounted(() => {
    // Đặt giá trị mặc định cho tỉnh, huyện, xã để kích hoạt tải dữ liệu ban đầu
    // và đảm bảo các dropdown được điền đầy đủ
    fetchDistricts().then(() => {
        if (filters.value.maHuyen) {
            fetchCommunes().then(() => {
                fetchReplacementData(); // Lấy dữ liệu bảng sau khi đã có các lựa chọn mặc định
            });
        } else {
            fetchReplacementData();
        }
    });
});

// Watchers
watch(() => filters.value.maTinh, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        fetchDistricts();
    }
});

watch(() => filters.value.maHuyen, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        fetchCommunes();
    }
});

watch([() => filters.value.kyDieuTra, () => filters.value.nam, () => filters.value.maTinh, () => filters.value.maHuyen, () => filters.value.maXa], () => {
    fetchReplacementData(); // Lấy lại dữ liệu khi bất kỳ bộ lọc nào thay đổi
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
    white-space: nowrap;
    /* Ngăn không cho nội dung trong bảng bị xuống dòng */
}

.table thead th {
    vertical-align: middle;
    background-color: #e9ecef;
    border-bottom: 2px solid #dee2e6;
    text-align: center;
    /* Căn giữa cho tiêu đề cột */
}

.table-bordered th,
.table-bordered td {
    border: 1px solid #dee2e6;
    padding: 0.75rem;
}

.table-hover tbody tr:hover {
    background-color: #e2e6ea;
}

.btn {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    padding: 0.5rem 1rem;
}

.btn i {
    margin-right: 0.5rem;
}

.btn-danger {
    background-color: #dc3545;
    border-color: #dc3545;
}

.btn-primary {
    background-color: #007bff;
    border-color: #007bff;
}

.btn-success {
    background-color: #28a745;
    border-color: #28a745;
}

.btn-info.text-white {
    background-color: #17a2b8;
    border-color: #17a2b8;
    color: white !important;
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
    .d-flex.flex-wrap .btn {
        flex: 1 1 auto;
        margin-bottom: 0.5rem !important;
    }

    .col-md-2,
    .col-md-3,
    .col-lg-2,
    .col-lg-3,
    .col-sm-6,
    .col-md-auto {
        width: 100%;
        margin-bottom: 1rem;
    }

    .card .row.g-3>div:last-child {
        margin-bottom: 0;
    }
}
</style>