<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Hộ nuôi lợn, gà, vịt, ngan</h2>

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
                                <option v-for="tinh in provinces" :key="tinh.ma" :value="tinh.ma">{{ tinh.ma }} - {{ tinh.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-2 col-sm-6">
                            <label for="quanHuyen" class="form-label visually-hidden">Quận/Huyện</label>
                            <select v-model="filters.maHuyen" class="form-select" id="quanHuyen" @change="fetchCommunes" :disabled="!districts.length">
                                <option value="">--Chọn Huyện--</option>
                                <option v-for="huyen in districts" :key="huyen.ma" :value="huyen.ma">{{ huyen.ma }} - {{ huyen.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-3 col-sm-6">
                            <label for="xaPhuong" class="form-label visually-hidden">Xã/Phường</label>
                            <select v-model="filters.maXa" class="form-select" id="xaPhuong" :disabled="!communes.length">
                                <option value="">--Chọn Xã/Phường--</option>
                                <option v-for="xa in communes" :key="xa.ma" :value="xa.ma">{{ xa.ma }} - {{ xa.ten }}</option>
                            </select>
                        </div>
                         <div class="col-md-auto">
                            <button class="btn btn-primary" @click="applyFilters">
                                <i class="bi bi-search"></i> Xem
                            </button>
                        </div>
                    </div>
                </div>

                <div class="d-flex justify-content-end mb-3">
                    <button class="btn btn-success" @click="exportExcel">
                        <i class="bi bi-file-earmark-excel"></i> Xuất Excel
                    </button>
                </div>


                <div class="table-responsive">
                    <table class="table table-bordered table-hover caption-top">
                        <caption>Tổng số bản ghi: {{ filteredData.length }}</caption>
                        <thead class="table-primary">
                            <tr>
                                <th rowspan="2" scope="col">STT Hộ</th>
                                <th rowspan="2" scope="col">Mã Tỉnh</th>
                                <th rowspan="2" scope="col">Mã Huyện</th>
                                <th rowspan="2" scope="col">Mã Xã</th>
                                <th rowspan="2" scope="col">Mã Thôn</th>
                                <th rowspan="2" scope="col">Họ và tên chủ hộ</th>
                                <th rowspan="2" scope="col">Điện thoại</th>
                                <th colspan="3" class="text-center">Hộ nuôi lợn</th>
                                <th colspan="3" class="text-center">Hộ nuôi gà</th>
                                <th colspan="3" class="text-center">Hộ nuôi vịt</th>
                                <th colspan="3" class="text-center">Hộ nuôi ngan</th>
                                <th colspan="4" class="text-center">Mẫu được chọn điều tra</th>
                            </tr>
                            <tr>
                                <th scope="col" class="text-center">Từ 30 đến 99 con</th>
                                <th scope="col" class="text-center">Từ 100 đến 299 con</th>
                                <th scope="col" class="text-center">Từ 300 con trở lên</th>
                                <th scope="col" class="text-center">Từ 1000 đến 3999 con</th>
                                <th scope="col" class="text-center">Từ 4000 con trở lên</th>
                                <th scope="col" class="text-center">Từ 500 con trở lên</th>
                                <th scope="col" class="text-center">Từ 500 con trở lên</th>
                                <th scope="col" class="text-center">Từ 500 con trở lên</th>
                                <th scope="col" class="text-center">Từ 2000 con trở lên</th>
                                <th scope="col" class="text-center">Từ 500 con trở lên</th>
                                <th scope="col" class="text-center">Từ 500 con trở lên</th>
                                <th scope="col" class="text-center">Từ 2000 con trở lên</th>
                                <th scope="col" class="text-center">Lợn</th>
                                <th scope="col" class="text-center">Gà</th>
                                <th scope="col" class="text-center">Vịt</th>
                                <th scope="col" class="text-center">Ngan</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-if="filteredData.length === 0">
                                <td colspan="20" class="text-center">Chưa có dữ liệu</td>
                            </tr>
                            <tr v-for="(item, index) in filteredData" :key="index">
                                <td>{{ item.sttHo }}</td>
                                <td>{{ item.maTinh }}</td>
                                <td>{{ item.maHuyen }}</td>
                                <td>{{ item.maXa }}</td>
                                <td>{{ item.maThon }}</td>
                                <td>{{ item.tenChuHo }}</td>
                                <td>{{ item.dienThoai }}</td>
                                <td class="text-center">{{ item.lon_30_99 }}</td>
                                <td class="text-center">{{ item.lon_100_299 }}</td>
                                <td class="text-center">{{ item.lon_300_plus }}</td>
                                <td class="text-center">{{ item.ga_1000_3999 }}</td>
                                <td class="text-center">{{ item.ga_4000_plus }}</td>
                                <td class="text-center">{{ item.ga_500_plus_total }}</td>
                                <td class="text-center">{{ item.vit_500_plus_total }}</td>
                                <td class="text-center">{{ item.vit_2000_plus }}</td>
                                <td class="text-center">{{ item.ngan_500_plus_total }}</td>
                                <td class="text-center">{{ item.ngan_2000_plus }}</td>
                                <td class="text-center">{{ item.mauChon.lon ? 'X' : '' }}</td>
                                <td class="text-center">{{ item.mauChon.ga ? 'X' : '' }}</td>
                                <td class="text-center">{{ item.mauChon.vit ? 'X' : '' }}</td>
                                <td class="text-center">{{ item.mauChon.ngan ? 'X' : '' }}</td>
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
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';

// --- State Variables ---
const filters = ref({
    kyDieuTra: '4',
    nam: '2025',
    maTinh: '87', // Mặc định Đồng Tháp
    maHuyen: '',
    maXa: ''
});

const provinces = ref([
    { ma: '87', ten: 'Đồng Tháp' },
    { ma: '88', ten: 'Long An' },
    { ma: '89', ten: 'An Giang' },
    { ma: '92', ten: 'Cần Thơ' },
]);

const districts = ref([]);
const communes = ref([]);

// Dữ liệu mẫu đa dạng cho bảng
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
            '880': [ // TP Tân An
                { ma: '30100', ten: 'Phường 1' },
                { ma: '30101', ten: 'Phường 2' },
            ],
            // Thêm dữ liệu xã cho các huyện khác nếu cần
        };
        communes.value = await mockApiCall(mockCommunesData[filters.value.maHuyen] || []);
        if (communes.value.length > 0) {
            filters.value.maXa = communes.value[0].ma; // Tự động chọn xã đầu tiên
        }
    }
}

async function fetchHouseholdData() {
    console.log("Đang lấy dữ liệu hộ nuôi gia súc, gia cầm với bộ lọc:", filters.value);

    // Dữ liệu mẫu đa dạng
    const dummyData = [
        // Dữ liệu Đồng Tháp - Hồng Ngự - Xã Long Khánh A (maXa: 29980) - Kỳ 4, 2025
        { kyDieuTra: '4', nam: '2025', maTinh: '87', maHuyen: '870', maXa: '29980', maThon: '001', sttHo: '01', tenChuHo: 'Nguyễn Văn A', dienThoai: '0901234567',
          lon_30_99: 50, lon_100_299: 0, lon_300_plus: 0,
          ga_1000_3999: 2000, ga_4000_plus: 0, ga_500_plus_total: 0, // Dữ liệu giả định
          vit_500_plus_total: 0, vit_2000_plus: 0,
          ngan_500_plus_total: 0, ngan_2000_plus: 0,
          mauChon: { lon: true, ga: false, vit: false, ngan: false }
        },
        { kyDieuTra: '4', nam: '2025', maTinh: '87', maHuyen: '870', maXa: '29980', maThon: '002', sttHo: '02', tenChuHo: 'Trần Thị B', dienThoai: '0912345678',
          lon_30_99: 0, lon_100_299: 0, lon_300_plus: 0,
          ga_1000_3999: 0, ga_4000_plus: 5000, ga_500_plus_total: 0,
          vit_500_plus_total: 0, vit_2000_plus: 0,
          ngan_500_plus_total: 0, ngan_2000_plus: 0,
          mauChon: { lon: false, ga: true, vit: false, ngan: false }
        },
        { kyDieuTra: '4', nam: '2025', maTinh: '87', maHuyen: '870', maXa: '29980', maThon: '003', sttHo: '03', tenChuHo: 'Lê Văn C', dienThoai: '0987654321',
          lon_30_99: 0, lon_100_299: 0, lon_300_plus: 0,
          ga_1000_3999: 0, ga_4000_plus: 0, ga_500_plus_total: 0,
          vit_500_plus_total: 700, vit_2000_plus: 0,
          ngan_500_plus_total: 0, ngan_2000_plus: 0,
          mauChon: { lon: false, ga: false, vit: true, ngan: false }
        },
        // Dữ liệu Đồng Tháp - Hồng Ngự - Xã Thường Phước 1 (maXa: 29956) - Kỳ 4, 2025
        { kyDieuTra: '4', nam: '2025', maTinh: '87', maHuyen: '870', maXa: '29956', maThon: '001', sttHo: '04', tenChuHo: 'Phạm Thị D', dienThoai: '0976543210',
          lon_30_99: 0, lon_100_299: 0, lon_300_plus: 0,
          ga_1000_3999: 0, ga_4000_plus: 0, ga_500_plus_total: 0,
          vit_500_plus_total: 0, vit_2000_plus: 0,
          ngan_500_plus_total: 1000, ngan_2000_plus: 0,
          mauChon: { lon: false, ga: false, vit: false, ngan: true }
        },
        // Dữ liệu Long An - TP Tân An - Phường 1 (maXa: 30100) - Kỳ 3, 2024
        { kyDieuTra: '3', nam: '2024', maTinh: '88', maHuyen: '880', maXa: '30100', maThon: '005', sttHo: '05', tenChuHo: 'Võ Văn E', dienThoai: '0965432109',
          lon_30_99: 120, lon_100_299: 0, lon_300_plus: 0,
          ga_1000_3999: 0, ga_4000_plus: 0, ga_500_plus_total: 0,
          vit_500_plus_total: 0, vit_2000_plus: 0,
          ngan_500_plus_total: 0, ngan_2000_plus: 0,
          mauChon: { lon: true, ga: false, vit: false, ngan: false }
        },
        // Dữ liệu An Giang - TP Long Xuyên - Kỳ 1, 2023
        { kyDieuTra: '1', nam: '2023', maTinh: '89', maHuyen: '890', maXa: '30200', maThon: '001', sttHo: '06', tenChuHo: 'Đặng Thị F', dienThoai: '0943210987',
          lon_30_99: 0, lon_100_299: 0, lon_300_plus: 0,
          ga_1000_3999: 0, ga_4000_plus: 0, ga_500_plus_total: 0,
          vit_500_plus_total: 0, vit_2000_plus: 2500,
          ngan_500_plus_total: 0, ngan_2000_plus: 0,
          mauChon: { lon: false, ga: false, vit: true, ngan: false }
        },
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
    /*
    import * as XLSX from 'xlsx';
    const ws = XLSX.utils.json_to_sheet(filteredData.value);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "HoNuoiGiaSucGiaCam");
    XLSX.writeFile(wb, "HoNuoiGiaSucGiaCam.xlsx");
    */
}

// --- Lifecycle Hooks and Watchers ---
onMounted(() => {
    // Đảm bảo các dropdown được điền và dữ liệu hiển thị đúng với mặc định
    fetchDistricts().then(() => {
        if (filters.value.maHuyen) {
            fetchCommunes().then(() => {
                fetchHouseholdData();
            });
        } else {
            fetchHouseholdData();
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

// Watch tất cả các bộ lọc chính để tải lại dữ liệu bảng
watch([() => filters.value.kyDieuTra, () => filters.value.nam, () => filters.value.maTinh, () => filters.value.maHuyen, () => filters.value.maXa], () => {
    fetchHouseholdData();
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

.btn {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    padding: 0.5rem 1rem;
}

.btn i {
    margin-right: 0.5rem;
}

.btn-primary {
    background-color: #007bff;
    border-color: #007bff;
}

.btn-success {
    background-color: #28a745;
    border-color: #28a745;
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
    .col-md-2, .col-md-3, .col-lg-2, .col-lg-3, .col-sm-6, .col-md-auto {
        width: 100%;
        margin-bottom: 1rem;
    }
    .card .row.g-3 > div:last-child {
        margin-bottom: 0;
    }
}
</style>