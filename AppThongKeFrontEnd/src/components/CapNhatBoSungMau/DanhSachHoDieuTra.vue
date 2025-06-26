<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Danh sách hộ được chọn theo mẫu tổng quy mô</h2>

                <div class="card p-3 mb-4 shadow-sm">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-2 col-sm-6">
                            <label for="kyDieuTra" class="form-label visually-hidden">Kỳ điều tra</label>
                            <select v-model="filters.kyDieuTra" class="form-select" id="kyDieuTra">
                                <option value="">--Chọn Kỳ--</option>
                                <option value="4">4</option>
                                <option value="3">3</option>
                                <option value="2">2</option>
                                <option value="1">1</option>
                            </select>
                        </div>
                        <div class="col-md-2 col-sm-6">
                            <label for="nam" class="form-label visually-hidden">Năm</label>
                            <select v-model="filters.nam" class="form-select" id="nam">
                                <option value="">--Chọn Năm--</option>
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
                                <th rowspan="2" scope="col">STT</th>
                                <th rowspan="2" scope="col">Mã Tỉnh</th>
                                <th rowspan="2" scope="col">Mã Huyện</th>
                                <th rowspan="2" scope="col">Mã Xã</th>
                                <th rowspan="2" scope="col">Thôn</th>
                                <th rowspan="2" scope="col">Địa bàn</th>
                                <th rowspan="2" scope="col">Hộ số</th>
                                <th rowspan="2" scope="col" style="min-width: 180px;">Tên chủ hộ</th>
                                <th rowspan="2" scope="col">Điện thoại</th>
                                <th colspan="4" class="text-center">Quy mô</th>
                                <th rowspan="2" scope="col">Hộ nuôi yến</th>
                                <th rowspan="2" scope="col">BTV</th>
                            </tr>
                            <tr>
                                <th scope="col" class="text-center">Lợn</th>
                                <th scope="col" class="text-center">Gà</th>
                                <th scope="col" class="text-center">Vịt</th>
                                <th scope="col" class="text-center">Ngan</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-if="filteredData.length === 0">
                                <td colspan="15" class="text-center">Chưa có dữ liệu</td>
                            </tr>
                            <tr v-for="(item, index) in filteredData" :key="index">
                                <td>{{ item.stt }}</td>
                                <td>{{ item.maTinh }}</td>
                                <td>{{ item.maHuyen }}</td>
                                <td>{{ item.maXa }}</td>
                                <td>{{ item.thon }}</td>
                                <td>{{ item.diaBan }}</td>
                                <td>{{ item.hoSo }}</td>
                                <td>{{ item.tenChuHo }}</td>
                                <td>{{ item.dienThoai }}</td>
                                <td class="text-center">{{ item.quyMo.lon }}</td>
                                <td class="text-center">{{ item.quyMo.ga }}</td>
                                <td class="text-center">{{ item.quyMo.vit }}</td>
                                <td class="text-center">{{ item.quyMo.ngan }}</td>
                                <td class="text-center">{{ item.hoNuoiYen ? 'Có' : 'Không' }}</td>
                                <td>{{ item.btv }}</td>
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
    kyDieuTra: '', // Mặc định không chọn để hiển thị tất cả ban đầu
    nam: '',      // Mặc định không chọn
    maTinh: '',   // Mặc định không chọn
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
            { ma: '872', ten: 'Tháp Mười', ma_tinh: '87' },
            { ma: '880', ten: 'TP Tân An', ma_tinh: '88' },
            { ma: '881', ten: 'Đức Huệ', ma_tinh: '88' },
            { ma: '890', ten: 'TP Long Xuyên', ma_tinh: '89' },
            { ma: '891', ten: 'Châu Đốc', ma_tinh: '89' },
        ];
        districts.value = await mockApiCall(mockDistrictsData.filter(d => d.ma_tinh === filters.value.maTinh));
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
                { ma: '29980', ten: 'Xã Long Khánh A' },
                { ma: '29983', ten: 'Xã Long Khánh B' },
            ],
            '871': [ // Tam Nông
                { ma: '30000', ten: 'Thị trấn Tràm Chim' },
                { ma: '30001', ten: 'Xã Phú Cường' },
                { ma: '30002', ten: 'Xã Tân Công Sính' },
            ],
            '872': [ // Tháp Mười
                { ma: '30050', ten: 'Thị trấn Mỹ An' },
                { ma: '30051', ten: 'Xã Đốc Binh Kiều' },
            ],
            '880': [ // TP Tân An
                { ma: '30100', ten: 'Phường 1' },
                { ma: '30101', ten: 'Phường 2' },
                { ma: '30102', ten: 'Xã Lợi Bình Nhơn' },
            ],
            '881': [ // Đức Huệ
                { ma: '30150', ten: 'Thị trấn Đông Thành' },
                { ma: '30151', ten: 'Xã Mỹ Quý Đông' },
            ],
            '890': [ // TP Long Xuyên
                { ma: '30200', ten: 'Phường Mỹ Bình' },
                { ma: '30201', ten: 'Phường Mỹ Long' },
            ],
            '891': [ // Châu Đốc
                { ma: '30250', ten: 'Phường Châu Phú A' },
                { ma: '30251', ten: 'Phường Vĩnh Mỹ' },
            ]
        };
        communes.value = await mockApiCall(mockCommunesData[filters.value.maHuyen] || []);
    }
}

async function fetchHouseholdData() {
    console.log("Đang lấy dữ liệu danh sách hộ theo quy mô với bộ lọc:", filters.value);

    // Dữ liệu mẫu đa dạng hơn, bao gồm nhiều trường hợp khác nhau về kỳ, năm, tỉnh, huyện, xã, thôn
    const dummyData = [];

    const numRecords = 100; // Số lượng bản ghi mẫu để tạo sự đa dạng
    const quyMoOptions = ['', 'V01', 'V02', 'V03', 'V04', 'V05']; // Các tùy chọn quy mô

    for (let i = 1; i <= numRecords; i++) {
        const kyDieuTra = ['4', '3', '2'][Math.floor(Math.random() * 3)];
        const nam = ['2025', '2024', '2023'][Math.floor(Math.random() * 3)];

        const tinh = provinces.value[Math.floor(Math.random() * provinces.value.length)];
        let maTinh = tinh.ma;

        let huyen = {};
        let xa = {};

        // Tìm huyện và xã tương ứng với maTinh đã chọn
        const allDistricts = [
            { ma: '870', ten: 'Hồng Ngự', ma_tinh: '87' },
            { ma: '871', ten: 'Tam Nông', ma_tinh: '87' },
            { ma: '872', ten: 'Tháp Mười', ma_tinh: '87' },
            { ma: '880', ten: 'TP Tân An', ma_tinh: '88' },
            { ma: '881', ten: 'Đức Huệ', ma_tinh: '88' },
            { ma: '890', ten: 'TP Long Xuyên', ma_tinh: '89' },
            { ma: '891', ten: 'Châu Đốc', ma_tinh: '89' },
        ];
        const districtsForTinh = allDistricts.filter(d => d.ma_tinh === maTinh);
        if (districtsForTinh.length > 0) {
            huyen = districtsForTinh[Math.floor(Math.random() * districtsForTinh.length)];
        }

        let maHuyen = huyen.ma || '';

        const allCommunes = {
            '870': [
                { ma: '29956', ten: 'Xã Thường Phước 1' }, { ma: '29962', ten: 'Xã Thường Thới Hậu A' },
                { ma: '29967', ten: 'Xã Thường Phước 2' }, { ma: '29971', ten: 'Thị trấn Thường Thới Tiền' },
                { ma: '29980', ten: 'Xã Long Khánh A' }, { ma: '29983', ten: 'Xã Long Khánh B' },
            ],
            '871': [
                { ma: '30000', ten: 'Thị trấn Tràm Chim' }, { ma: '30001', ten: 'Xã Phú Cường' }, { ma: '30002', ten: 'Xã Tân Công Sính' },
            ],
            '872': [
                { ma: '30050', ten: 'Thị trấn Mỹ An' }, { ma: '30051', ten: 'Xã Đốc Binh Kiều' },
            ],
            '880': [
                { ma: '30100', ten: 'Phường 1' }, { ma: '30101', ten: 'Phường 2' }, { ma: '30102', ten: 'Xã Lợi Bình Nhơn' },
            ],
            '881': [
                { ma: '30150', ten: 'Thị trấn Đông Thành' }, { ma: '30151', ten: 'Xã Mỹ Quý Đông' },
            ],
            '890': [
                { ma: '30200', ten: 'Phường Mỹ Bình' }, { ma: '30201', ten: 'Phường Mỹ Long' },
            ],
            '891': [
                { ma: '30250', ten: 'Phường Châu Phú A' }, { ma: '30251', ten: 'Phường Vĩnh Mỹ' },
            ]
        };
        const communesForHuyen = allCommunes[maHuyen] || [];
        if (communesForHuyen.length > 0) {
            xa = communesForHuyen[Math.floor(Math.random() * communesForHuyen.length)];
        }

        let maXa = xa.ma || '';
        let tenXa = xa.ten || '';

        const thon = `Ấp/Khu ${Math.floor(Math.random() * 5) + 1}`;
        const diaBan = `Địa bàn ${String.fromCharCode(65 + Math.floor(Math.random() * 3))}`; // A, B, C
        const hoSo = String(Math.floor(Math.random() * 1000) + 1).padStart(4, '0');
        const tenChuHo = `Nguyễn Văn ${String.fromCharCode(65 + i % 26)} ${i}`;
        const dienThoai = `0${Math.floor(Math.random() * 900000000) + 100000000}`; // Random 10 digit number

        dummyData.push({
            stt: i,
            kyDieuTra: kyDieuTra,
            nam: nam,
            maTinh: maTinh,
            maHuyen: maHuyen,
            maXa: maXa,
            thon: thon,
            diaBan: diaBan,
            hoSo: hoSo,
            tenChuHo: tenChuHo,
            dienThoai: dienThoai,
            quyMo: {
                lon: quyMoOptions[Math.floor(Math.random() * quyMoOptions.length)],
                ga: quyMoOptions[Math.floor(Math.random() * quyMoOptions.length)],
                vit: quyMoOptions[Math.floor(Math.random() * quyMoOptions.length)],
                ngan: quyMoOptions[Math.floor(Math.random() * quyMoOptions.length)],
            },
            hoNuoiYen: Math.random() > 0.8, // Khoảng 20% hộ nuôi yến
            btv: `D${maTinh}${String(Math.floor(Math.random() * 100) + 1).padStart(3, '0')}`,
        });
    }

    allData.value = await mockApiCall(dummyData);
}

function applyFilters() {
    // Computed property `filteredData` sẽ tự động cập nhật bảng.
    console.log("Áp dụng bộ lọc:", filters.value);
}

function exportExcel() {
    alert('Chức năng Xuất Excel (cần implement logic)');
    // Để xuất Excel, bạn sẽ cần một thư viện như SheetJS (xlsx):
    // import * as XLSX from 'xlsx';
    // const ws = XLSX.utils.json_to_sheet(filteredData.value);
    // const wb = XLSX.utils.book_new();
    // XLSX.utils.book_append_sheet(wb, ws, "DanhSachHoTheoQuyMo");
    // XLSX.writeFile(wb, "DanhSachHoTheoQuyMo.xlsx");
}

// --- Lifecycle Hooks and Watchers ---
onMounted(() => {
    // Không tự động chọn mặc định tỉnh/huyện/xã để cho phép lọc từ đầu
    // Chỉ cần lấy dữ liệu mẫu đầy đủ để người dùng bắt đầu lọc
    fetchDistricts(); // Khởi tạo danh sách huyện dựa trên tỉnh mặc định (nếu có)
    fetchHouseholdData(); // Tải toàn bộ dữ liệu mẫu ban đầu
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

// Không cần watcher cho các filters khác vì filteredData là computed property
// và fetchHouseholdData() không được gọi lại mỗi khi filter thay đổi
// trừ khi người dùng nhấn nút "Xem"
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