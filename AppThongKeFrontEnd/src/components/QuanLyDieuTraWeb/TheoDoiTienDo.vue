<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Theo dõi tiến độ điều tra của doanh nghiệp, HTX</h2>

                <div class="card p-3 mb-4 shadow-sm">
                    <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
                        <div class="d-flex flex-wrap gap-2 align-items-center">
                            <div class="col-auto">
                                <label for="kyDieuTra" class="form-label visually-hidden">Kỳ điều tra</label>
                                <select v-model="filters.kyDieuTra" class="form-select" id="kyDieuTra">
                                    <option value="">--Chọn Kỳ--</option>
                                    <option>4</option>
                                    <option>3</option>
                                    <option>2</option>
                                </select>
                            </div>
                            <div class="col-auto">
                                <label for="nam" class="form-label visually-hidden">Năm</label>
                                <select v-model="filters.nam" class="form-select" id="nam">
                                    <option value="">--Chọn Năm--</option>
                                    <option>2025</option>
                                    <option>2024</option>
                                    <option>2023</option>
                                </select>
                            </div>
                            <div class="col-auto">
                                <label for="tinhTp" class="form-label visually-hidden">Tỉnh/TP</label>
                                <select v-model="filters.maTinh" class="form-select" id="tinhTp" @change="fetchDistrictsAndData">
                                    <option value="">--Chọn Tỉnh--</option>
                                    <option v-for="tinh in provinces" :key="tinh.ma" :value="tinh.ma">{{ tinh.ma }} - {{ tinh.ten }}</option>
                                </select>
                            </div>
                            <div class="col-auto">
                                <label for="quanHuyen" class="form-label visually-hidden">Quận/Huyện</label>
                                <select v-model="filters.maHuyen" class="form-select" id="quanHuyen" @change="fetchData" :disabled="!districts.length">
                                    <option value="">--Chọn Huyện--</option>
                                    <option v-for="huyen in districts" :key="huyen.ma" :value="huyen.ma">{{ huyen.ma }} - {{ huyen.ten }}</option>
                                </select>
                            </div>
                            <div class="col-auto">
                                <label for="searchTerm" class="form-label visually-hidden">Tìm kiếm</label>
                                <input type="text" v-model="filters.searchTerm" class="form-control" id="searchTerm" placeholder="Mã số thuế / Tên đơn vị">
                            </div>
                            <button class="btn btn-primary" @click="applyFilters">
                                <i class="bi bi-search"></i> Tìm
                            </button>
                            <button class="btn btn-secondary" @click="showStatistics">
                                <i class="bi bi-graph-up"></i> Thống kê
                            </button>
                             <button class="btn btn-success" @click="exportExcel">
                                <i class="bi bi-file-earmark-excel"></i> Xuất Excel
                            </button>
                        </div>
                        <button class="btn btn-warning text-dark mt-2 mt-md-0" @click="cancelCompletion">
                            <i class="bi bi-x-circle"></i> Hủy hoàn thành điều tra
                        </button>
                    </div>

                    <div class="row gx-3 gy-2">
                        <div class="col-md-3 col-sm-6">
                            <div class="p-2 border rounded bg-light">
                                <strong>Số Doanh nghiệp, HTX:</strong> {{ summary.soDoanhNghiepHTX }}
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-6">
                            <div class="p-2 border rounded bg-light">
                                <strong>Phiếu 1:</strong> {{ summary.phieu1 }}
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-6">
                            <div class="p-2 border rounded bg-light">
                                <strong>Phiếu 4:</strong> {{ summary.phieu4 }}
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-6">
                            <div class="p-2 border rounded bg-light">
                                <strong>Số hoàn thành điều tra:</strong> {{ summary.soHoanThanhDieuTra }}
                            </div>
                        </div>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-bordered table-hover caption-top">
                        <caption>Tổng số bản ghi: {{ filteredEnterprises.length }}</caption>
                        <thead class="table-primary">
                            <tr>
                                <th scope="col">STT</th>
                                <th scope="col">Mã Huyện</th>
                                <th scope="col">Mã Xã</th>
                                <th scope="col" style="min-width: 150px;">Tên xã</th>
                                <th scope="col" style="min-width: 150px;">Mã số thuế</th>
                                <th scope="col" style="min-width: 250px;">Tên đơn vị</th>
                                <th scope="col" style="min-width: 120px;">Loại hình</th>
                                <th scope="col" style="min-width: 150px;">Lĩnh vực chăn nuôi</th>
                                <th scope="col" style="min-width: 150px;">Hoàn thành điều tra</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-if="filteredEnterprises.length === 0">
                                <td colspan="9" class="text-center">Chưa có dữ liệu</td>
                            </tr>
                            <tr v-for="(enterprise, index) in filteredEnterprises" :key="enterprise.id">
                                <td>{{ index + 1 }}</td>
                                <td>{{ enterprise.maHuyen }}</td>
                                <td>{{ enterprise.maXa }}</td>
                                <td>{{ enterprise.tenXa }}</td>
                                <td>{{ enterprise.maSoThue }}</td>
                                <td>{{ enterprise.tenDonVi }}</td>
                                <td>{{ enterprise.loaiHinh }}</td>
                                <td>{{ enterprise.linhVucChanNuoi }}</td>
                                <td class="text-center">{{ enterprise.hoanThanhDieuTra ? 'X' : '' }}</td>
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
const allEnterprises = ref([]); // Stores all data
const filters = ref({
    kyDieuTra: '4', // Default from image
    nam: '2025',   // Default from image
    maTinh: '87',  // Default Đồng Tháp from image
    maHuyen: '',   // Will be set by fetchDistricts
    searchTerm: '' // For Mã số thuế / Tên đơn vị
});

const provinces = ref([
    { ma: '87', ten: 'Đồng Tháp' },
    { ma: '88', ten: 'Long An' },
    { ma: '89', ten: 'An Giang' },
    { ma: '92', ten: 'Cần Thơ' },
]);

const districts = ref([]);

const summary = ref({
    soDoanhNghiepHTX: 0,
    phieu1: 0,
    phieu4: 0,
    soHoanThanhDieuTra: 0
});

// --- Computed Properties ---
const filteredEnterprises = computed(() => {
    const term = filters.value.searchTerm.toLowerCase();
    return allEnterprises.value.filter(enterprise =>
        (filters.value.kyDieuTra === '' || enterprise.kyDieuTra == filters.value.kyDieuTra) &&
        (filters.value.nam === '' || enterprise.nam == filters.value.nam) &&
        (filters.value.maTinh === '' || enterprise.maTinh === filters.value.maTinh) &&
        (filters.value.maHuyen === '' || enterprise.maHuyen === filters.value.maHuyen) &&
        (term === '' || enterprise.maSoThue.toLowerCase().includes(term) || enterprise.tenDonVi.toLowerCase().includes(term))
    );
});

// --- Methods ---

// Helper to simulate API calls
const mockApiCall = (data, delay = 100) => {
    return new Promise(resolve => setTimeout(() => resolve(data), delay));
};

// Function to fetch districts and trigger data fetch
async function fetchDistrictsAndData() {
    filters.value.maHuyen = ''; // Reset huyện khi tỉnh thay đổi
    districts.value = [];
    if (filters.value.maTinh) {
        const mockDistrictsData = [
            { ma: '870', ten: 'Hồng Ngự', ma_tinh: '87' },
            { ma: '871', ten: 'Tam Nông', ma_tinh: '87' },
            { ma: '872', ten: 'Tháp Mười', ma_tinh: '87' },
            { ma: '880', ten: 'TP Tân An', ma_tinh: '88' },
        ];
        districts.value = await mockApiCall(mockDistrictsData.filter(d => d.ma_tinh === filters.value.maTinh));
        if (districts.value.length > 0) {
            filters.value.maHuyen = districts.value[0].ma; // Select first available district
        }
    }
    await fetchData(); // Call fetchData after districts are updated
}

// Function to fetch data based on all current filters
async function fetchData() {
    await generateEnterpriseData(); // Regenerate all data based on filters
    updateSummary(); // Update summary based on the new data
}

// --- Data Generation for Mock Data ---
async function generateEnterpriseData() {
    console.log("Đang tạo dữ liệu mẫu cho doanh nghiệp/HTX với bộ lọc hiện tại:", filters.value);
    const dummyData = [];
    const numRecords = 25; // Number of dummy enterprises

    const currentTinh = provinces.value.find(p => p.ma === filters.value.maTinh);
    const currentHuyen = districts.value.find(d => d.ma === filters.value.maHuyen);

    const baseMaTinh = currentTinh ? currentTinh.ma : '87';
    const baseMaHuyen = currentHuyen ? currentHuyen.ma : '870';
    const baseTenHuyen = currentHuyen ? currentHuyen.ten : 'Hồng Ngự';

    const sampleTenXa = ['Long Khánh A', 'Thường Phước 1', 'Thường Thới Hậu A', 'Phú Thuận B', 'Long Thuận'];
    const sampleLoaiHinh = ['Doanh nghiệp tư nhân', 'Hợp tác xã', 'Công ty TNHH'];
    const sampleLinhVuc = ['Chăn nuôi heo', 'Chăn nuôi gà', 'Chăn nuôi tổng hợp', 'Chăn nuôi thủy sản'];
    const sampleDonVi = ['Công ty A', 'HTX Xanh', 'Doanh nghiệp Phát triển', 'HTX Thủy Sản', 'Công ty Chăn Nuôi B'];

    for (let i = 0; i < numRecords; i++) {
        const maXa = `${baseMaHuyen}${String(Math.floor(Math.random() * 1000) + 29000).substring(1,4)}`; // Random-ish xã code
        const tenXa = sampleTenXa[i % sampleTenXa.length];
        const maSoThue = `MST${Math.floor(1000000000 + Math.random() * 9000000000)}`;
        const tenDonVi = `${sampleDonVi[i % sampleDonVi.length]} ${i + 1}`;
        const loaiHinh = sampleLoaiHinh[i % sampleLoaiHinh.length];
        const linhVucChanNuoi = sampleLinhVuc[i % sampleLinhVuc.length];
        const hoanThanhDieuTra = Math.random() > 0.3; // 70% completed

        dummyData.push({
            id: `ent-${crypto.randomUUID()}`,
            kyDieuTra: filters.value.kyDieuTra,
            nam: filters.value.nam,
            maTinh: baseMaTinh,
            maHuyen: baseMaHuyen,
            tenHuyen: baseTenHuyen,
            maXa: maXa,
            tenXa: tenXa,
            maSoThue: maSoThue,
            tenDonVi: tenDonVi,
            loaiHinh: loaiHinh,
            linhVucChanNuoi: linhVucChanNuoi,
            hoanThanhDieuTra: hoanThanhDieuTra
        });
    }
    // Simulate filtering at the "API" level based on current filters
    allEnterprises.value = await mockApiCall(dummyData.filter(e =>
        (filters.value.kyDieuTra === '' || e.kyDieuTra == filters.value.kyDieuTra) &&
        (filters.value.nam === '' || e.nam == filters.value.nam) &&
        (filters.value.maTinh === '' || e.maTinh === filters.value.maTinh) &&
        (filters.value.maHuyen === '' || e.maHuyen === filters.value.maHuyen)
    ));
}

function updateSummary() {
    summary.value.soDoanhNghiepHTX = filteredEnterprises.value.length;
    summary.value.phieu1 = filteredEnterprises.value.length; // Giả sử phiếu 1 luôn bằng tổng số DN/HTX
    summary.value.phieu4 = filteredEnterprises.value.filter(e => e.linhVucChanNuoi.includes('chăn nuôi')).length; // Giả sử phiếu 4 cho DN/HTX có chăn nuôi
    summary.value.soHoanThanhDieuTra = filteredEnterprises.value.filter(e => e.hoanThanhDieuTra).length;
}

// --- Main Actions ---
function applyFilters() {
    console.log("Nút 'Tìm' được nhấn. Đang áp dụng bộ lọc và tái tạo dữ liệu.");
    fetchData();
}

function showStatistics() {
    alert('Chức năng "Thống kê" (cần mở báo cáo/biểu đồ)');
}

function exportExcel() {
    alert('Chức năng "Xuất Excel" (cần implement logic)');
    // Bạn sẽ cần dùng thư viện như SheetJS để xuất `filteredEnterprises.value`
}

function cancelCompletion() {
    alert('Chức năng "Hủy hoàn thành điều tra" (cần xác nhận và xử lý)');
}

// --- Lifecycle Hooks and Watchers ---
onMounted(async () => {
    // Initial data load for filters and table
    await fetchDistrictsAndData(); // This will fetch districts and then the data
});

// Watchers for filter dropdowns that should trigger a new data fetch
watch(() => filters.value.kyDieuTra, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        fetchData();
    }
});

watch(() => filters.value.nam, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        fetchData();
    }
});

// Watch searchTerm for immediate filtering of already loaded data
watch(() => filters.value.searchTerm, () => {
    updateSummary(); // Update summary based on real-time filtered results
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
    padding: 0.75rem;
    font-weight: bold;
}

.table-primary thead th {
    background-color: #007bff !important;
    color: white;
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
    font-size: 0.95rem;
    white-space: nowrap;
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

.btn-warning {
    background-color: #ffc107;
    border-color: #ffc107;
    color: #343a40; /* Đảm bảo màu chữ dễ đọc trên nền vàng */
}

.btn-secondary {
    background-color: #6c757d;
    border-color: #6c757d;
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
    .d-flex.flex-wrap .col-auto,
    .d-flex.flex-wrap .btn {
        flex: 1 1 auto;
        margin-bottom: 0.5rem !important;
    }
    .col-md-3, .col-sm-6 {
        width: 100%;
    }
    .card .row.gy-2 > div {
        margin-bottom: 1rem;
    }
    .card .row.gy-2 > div:last-child {
        margin-bottom: 0;
    }
}
</style>