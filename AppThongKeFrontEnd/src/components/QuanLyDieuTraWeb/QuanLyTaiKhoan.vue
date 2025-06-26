<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Danh sách điều tra viên</h2>

                <div class="card p-3 mb-4 shadow-sm">
                    <div class="d-flex flex-wrap align-items-center gap-2 mb-3">
                        <button class="btn btn-primary" @click="addNew">
                            <i class="bi bi-plus-circle"></i> Thêm mới
                        </button>
                        <button class="btn btn-info text-dark" @click="editSelected" :disabled="selectedInvestigators.length !== 1">
                            <i class="bi bi-pencil-square"></i> Sửa
                        </button>
                        <button class="btn btn-danger" @click="deleteSelected" :disabled="selectedInvestigators.length === 0">
                            <i class="bi bi-trash"></i> Xóa
                        </button>
                        <button class="btn btn-secondary" @click="replaceImei" :disabled="selectedInvestigators.length !== 1">
                            <i class="bi bi-phone"></i> Thay thế IMEI
                        </button>
                        <button class="btn btn-success" @click="allowCAPI" :disabled="selectedInvestigators.length === 0">
                            <i class="bi bi-check-circle"></i> Cho phép CAPI
                        </button>
                        <button class="btn btn-warning text-dark" @click="denyCAPI" :disabled="selectedInvestigators.length === 0">
                            <i class="bi bi-x-circle"></i> Không cho phép CAPI
                        </button>
                        <button class="btn btn-primary" @click="checkImei" :disabled="selectedInvestigators.length === 0">
                            <i class="bi bi-phone-fill"></i> Kiểm tra IMEI
                        </button>
                        <button class="btn btn-secondary" @click="uncheckImei" :disabled="selectedInvestigators.length === 0">
                            <i class="bi bi-phone-slash-fill"></i> Không kiểm tra IMEI
                        </button>
                        <button class="btn btn-info" @click="resetPassword" :disabled="selectedInvestigators.length === 0">
                            <i class="bi bi-key"></i> Khôi phục mật khẩu
                        </button>

                        <button class="btn btn-success ms-auto" @click="exportExcel">
                            <i class="bi bi-file-earmark-excel"></i> Xuất Excel
                        </button>
                        <button class="btn btn-secondary" @click="addFromFile">
                            <i class="bi bi-upload"></i> Thêm từ file Excel
                        </button>
                    </div>

                    <div class="row g-3 align-items-end">
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
                            <select v-model="filters.maHuyen" class="form-select" id="quanHuyen" :disabled="!districts.length">
                                <option value="">--Chọn Huyện--</option>
                                <option v-for="huyen in districts" :key="huyen.ma" :value="huyen.ma">{{ huyen.ma }} - {{ huyen.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-3 col-sm-6">
                            <label for="hoTen" class="form-label visually-hidden">Họ tên</label>
                            <input type="text" v-model="filters.hoTen" class="form-control" id="hoTen" placeholder="Họ tên">
                        </div>
                         <div class="col-md-auto">
                            <button class="btn btn-primary" @click="applyFilters">
                                <i class="bi bi-search"></i> Tìm
                            </button>
                        </div>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-bordered table-hover caption-top">
                        <caption>Tổng số bản ghi: {{ filteredInvestigators.length }}</caption>
                        <thead class="table-primary">
                            <tr>
                                <th scope="col" class="text-center" style="width: 50px;">
                                    <input type="checkbox" @change="toggleSelectAll" :checked="selectedInvestigators.length === filteredInvestigators.length && filteredInvestigators.length > 0">
                                </th>
                                <th scope="col">STT</th>
                                <th scope="col">Mã Huyện</th>
                                <th scope="col">Tên Huyện</th>
                                <th scope="col">Mã ĐTV</th>
                                <th scope="col" style="min-width: 180px;">Họ tên</th>
                                <th scope="col" style="min-width: 120px;">Số điện thoại</th>
                                <th scope="col" style="min-width: 250px;">Địa chỉ</th>
                                <th scope="col" style="min-width: 150px;">Ghi chú</th>
                                <th scope="col" style="min-width: 150px;">IMEI</th>
                                <th scope="col">Check IMEI</th>
                                <th scope="col">Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-if="filteredInvestigators.length === 0">
                                <td colspan="12" class="text-center">Chưa có dữ liệu</td>
                            </tr>
                            <tr v-for="(investigator, index) in filteredInvestigators" :key="investigator.id">
                                <td class="text-center">
                                    <input type="checkbox" :value="investigator.id" v-model="selectedInvestigators">
                                </td>
                                <td>{{ index + 1 }}</td>
                                <td>{{ investigator.maHuyen }}</td>
                                <td>{{ investigator.tenHuyen }}</td>
                                <td>{{ investigator.maDTV }}</td>
                                <td>{{ investigator.hoTen }}</td>
                                <td>{{ investigator.soDienThoai }}</td>
                                <td>{{ investigator.diaChi }}</td>
                                <td>{{ investigator.ghiChu }}</td>
                                <td>{{ investigator.imei }}</td>
                                <td class="text-center">{{ investigator.checkImei ? 'X' : '' }}</td>
                                <td class="text-center">{{ investigator.trangThai ? 'X' : '' }}</td>
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
const allInvestigators = ref([]); // Store all data
const selectedInvestigators = ref([]); // IDs of selected investigators

const filters = ref({
    nam: '2025',
    maTinh: '87',
    maHuyen: '870', // Mặc định là Hồng Ngự để có dữ liệu ban đầu theo ảnh
    hoTen: '' // Search text for Ho ten
});

const provinces = ref([
    { ma: '87', ten: 'Đồng Tháp' },
    { ma: '88', ten: 'Long An' },
    { ma: '89', ten: 'An Giang' },
    { ma: '92', ten: 'Cần Thơ' },
]);

const districts = ref([]);

// --- Computed Properties ---
const filteredInvestigators = computed(() => {
    return allInvestigators.value.filter(investigator =>
        (filters.value.nam === '' || investigator.nam == filters.value.nam) &&
        (filters.value.maTinh === '' || investigator.maTinh === filters.value.maTinh) &&
        (filters.value.maHuyen === '' || investigator.maHuyen === filters.value.maHuyen) &&
        (filters.value.hoTen === '' || investigator.hoTen.toLowerCase().includes(filters.value.hoTen.toLowerCase()))
    );
});

// --- Methods ---

// Helper to simulate API calls
const mockApiCall = (data, delay = 100) => {
    return new Promise(resolve => setTimeout(() => resolve(data), delay));
};

// --- Data Fetching for Filters ---
async function fetchDistricts() {
    filters.value.maHuyen = ''; // Reset huyện khi tỉnh thay đổi
    districts.value = [];
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
        // Set default district based on mock data in the image if available
        if (filters.value.maTinh === '87' && districts.value.some(d => d.ma === '870')) {
             filters.value.maHuyen = '870'; // Default to Hồng Ngự based on image
        } else if (districts.value.length > 0) {
            filters.value.maHuyen = districts.value[0].ma;
        }
    }
}

// --- Data Generation for Mock Data ---
async function generateInvestigatorData() {
    console.log("Đang tạo dữ liệu mẫu cho danh sách điều tra viên với bộ lọc hiện tại:", filters.value);
    const dummyData = [];
    const numRecords = 20; // Number of dummy investigators

    const currentTinh = provinces.value.find(p => p.ma === filters.value.maTinh);
    const currentHuyen = districts.value.find(d => d.ma === filters.value.maHuyen);

    const baseMaTinh = currentTinh ? currentTinh.ma : '87';
    const baseMaHuyen = currentHuyen ? currentHuyen.ma : '870';
    const baseTenHuyen = currentHuyen ? currentHuyen.ten : 'Hồng Ngự';

    const sampleNames = ['PHẠM THANH ĐIỀN', 'VÕ THỊ BÉ', 'LÊ THỊ LAN', 'BÙI THỊ AN VUI', 'Trần Hữu Đức', 'Lê Văn Danh', 'Lê Huỳnh Hai', 'Nguyễn Thanh Tú', 'Nguyễn Văn Sua', 'Đỗ Ngọc An', 'Trần Thị Mỹ'];
    const sampleAddresses = [
        'Ấp Long Tía, Xã Long Khánh A, huyện Hồng Ngự, tỉnh Đồng Tháp',
        'Ấp 2, Xã Thường Phước 1, huyện Hồng Ngự, tỉnh Đồng Tháp',
        'Ấp Phú Lợi B, Xã Phú Thuận B, huyện Hồng Ngự, tỉnh Đồng Tháp',
        'Khu Trưng 2, TT Thường Thới Tiền, huyện Hồng Ngự, tỉnh Đồng Tháp',
        'Ấp Bình Hòa Hạ, Xã Thường Lạc',
        'Ấp Bình Hòa Trung, Xã Thường Thới Hậu A',
        'ấp Long Châu, Xã Long Khánh B',
        'Ấp Long Thới B, Xã Long Thuận',
        'Ấp Bình Hòa B, Xã Phú Thuận A',
        'Ấp Bình Hòa Thượng, Xã Thường Thới Hậu A',
    ];

    for (let i = 0; i < numRecords; i++) {
        const hoTen = sampleNames[i % sampleNames.length];
        const maDTV = `${baseMaHuyen}${String(i + 1).padStart(3, '0')}`;
        const soDienThoai = `0${Math.floor(Math.random() * 900000000) + 100000000}`;
        const diaChi = sampleAddresses[i % sampleAddresses.length];
        const ghiChu = Math.random() > 0.7 ? 'Đã được đào tạo' : '';
        const imei = `IMEI${Math.floor(100000000000000 + Math.random() * 900000000000000)}`;
        const checkImei = Math.random() > 0.3; // 70% chance to be 'X'
        const trangThai = Math.random() > 0.1; // 90% chance to be 'X'

        // Apply filters to data generation here for better mock data
        // Only add data that matches current filters
        const matchesNam = filters.value.nam === '' || filters.value.nam === String(filters.value.nam); // Dummy check for year
        const matchesTinh = filters.value.maTinh === '' || filters.value.maTinh === baseMaTinh;
        const matchesHuyen = filters.value.maHuyen === '' || filters.value.maHuyen === baseMaHuyen;
        const matchesHoTen = filters.value.hoTen === '' || hoTen.toLowerCase().includes(filters.value.hoTen.toLowerCase());


        // Only push if all basic filters match.
        // For 'hoTen', the computed property handles filtering on the full dataset.
        // We'll generate a full dataset for the selected geographic region and year.
        if (matchesNam && matchesTinh && matchesHuyen) {
            dummyData.push({
                id: `inv-${crypto.randomUUID()}`,
                nam: filters.value.nam,
                maTinh: baseMaTinh,
                maHuyen: baseMaHuyen,
                tenHuyen: baseTenHuyen,
                maDTV: maDTV,
                hoTen: hoTen,
                soDienThoai: soDienThoai,
                diaChi: diaChi,
                ghiChu: ghiChu,
                imei: imei,
                checkImei: checkImei,
                trangThai: trangThai
            });
        }
    }
    allInvestigators.value = await mockApiCall(dummyData);
}

// --- Main Actions ---
function applyFilters() {
    // When "Tìm" is clicked, we simply re-generate the data with current filters
    // and the computed property `filteredInvestigators` will take care of the rest.
    console.log("Nút 'Tìm' được nhấn. Đang áp dụng bộ lọc và tái tạo dữ liệu.");
    generateInvestigatorData();
    selectedInvestigators.value = []; // Clear selection
}

function addNew() {
    alert('Chức năng "Thêm mới" (cần mở form nhập liệu)');
}

function editSelected() {
    if (selectedInvestigators.value.length === 1) {
        const selectedId = selectedInvestigators.value[0];
        const investigator = allInvestigators.value.find(inv => inv.id === selectedId);
        alert(`Chức năng "Sửa": Mở form chỉnh sửa cho ĐTV: ${investigator?.hoTen} (Mã ĐTV: ${investigator?.maDTV})`);
    }
}

function deleteSelected() {
    if (selectedInvestigators.value.length === 0) {
        alert('Vui lòng chọn ít nhất một điều tra viên để xóa.');
        return;
    }
    const selectedNames = selectedInvestigators.value.map(id => allInvestigators.value.find(inv => inv.id === id)?.hoTen).filter(Boolean);
    if (confirm(`Bạn có chắc chắn muốn xóa các điều tra viên sau: ${selectedNames.join(', ')}?`)) {
        allInvestigators.value = allInvestigators.value.filter(inv => !selectedInvestigators.value.includes(inv.id));
        alert(`Đã xóa các điều tra viên: ${selectedNames.join(', ')}`);
        selectedInvestigators.value = [];
    }
}

function replaceImei() {
    if (selectedInvestigators.value.length !== 1) {
        alert('Vui lòng chọn DUY NHẤT một điều tra viên để thay thế IMEI.');
        return;
    }
    const selectedId = selectedInvestigators.value[0];
    const investigator = allInvestigators.value.find(inv => inv.id === selectedId);
    alert(`Chức năng "Thay thế IMEI" cho ĐTV: ${investigator?.hoTen} (Mã ĐTV: ${investigator?.maDTV})`);
}

function allowCAPI() {
    if (selectedInvestigators.value.length === 0) {
        alert('Vui lòng chọn ít nhất một điều tra viên.');
        return;
    }
    const selectedNames = selectedInvestigators.value.map(id => allInvestigators.value.find(inv => inv.id === id)?.hoTen).filter(Boolean);
    alert(`Đã "Cho phép CAPI" cho: ${selectedNames.join(', ')}`);
}

function denyCAPI() {
    if (selectedInvestigators.value.length === 0) {
        alert('Vui lòng chọn ít nhất một điều tra viên.');
        return;
    }
    const selectedNames = selectedInvestigators.value.map(id => allInvestigators.value.find(inv => inv.id === id)?.hoTen).filter(Boolean);
    alert(`Đã "Không cho phép CAPI" cho: ${selectedNames.join(', ')}`);
}

function checkImei() {
    if (selectedInvestigators.value.length === 0) {
        alert('Vui lòng chọn ít nhất một điều tra viên.');
        return;
    }
    const selectedNames = selectedInvestigators.value.map(id => allInvestigators.value.find(inv => inv.id === id)?.hoTen).filter(Boolean);
    alert(`Đã "Kiểm tra IMEI" cho: ${selectedNames.join(', ')}`);
}

function uncheckImei() {
    if (selectedInvestigators.value.length === 0) {
        alert('Vui lòng chọn ít nhất một điều tra viên.');
        return;
    }
    const selectedNames = selectedInvestigators.value.map(id => allInvestigators.value.find(inv => inv.id === id)?.hoTen).filter(Boolean);
    alert(`Đã "Không kiểm tra IMEI" cho: ${selectedNames.join(', ')}`);
}

function resetPassword() {
    if (selectedInvestigators.value.length === 0) {
        alert('Vui lòng chọn ít nhất một điều tra viên để khôi phục mật khẩu.');
        return;
    }
    const selectedNames = selectedInvestigators.value.map(id => allInvestigators.value.find(inv => inv.id === id)?.hoTen).filter(Boolean);
    if (confirm(`Bạn có chắc chắn muốn khôi phục mật khẩu cho các điều tra viên sau: ${selectedNames.join(', ')}?`)) {
        alert(`Đã gửi yêu cầu khôi phục mật khẩu cho: ${selectedNames.join(', ')}`);
        selectedInvestigators.value = [];
    }
}

function exportExcel() {
    alert('Chức năng Xuất Excel (cần implement logic)');
}

function addFromFile() {
    alert('Chức năng "Thêm từ file Excel" (cần mở dialog chọn file và xử lý nhập liệu)');
}

// --- Checkbox selection logic ---
function toggleSelectAll(event) {
    if (event.target.checked) {
        selectedInvestigators.value = filteredInvestigators.value.map(investigator => investigator.id);
    } else {
        selectedInvestigators.value = [];
    }
}

// --- Lifecycle Hooks and Watchers ---
onMounted(async () => {
    // Initial data load for filters
    await fetchDistricts();
    await generateInvestigatorData(); // Generate data after filters are ready
});

// Watcher for filter dropdowns to re-fetch related data
watch(() => filters.value.maTinh, async (newVal) => {
    if (newVal) {
        await fetchDistricts();
    } else {
        filters.value.maHuyen = '';
        districts.value = [];
    }
    // Re-generate data whenever maTinh changes
    await generateInvestigatorData();
});

watch(() => filters.value.maHuyen, async (newVal) => {
    // Re-generate data whenever maHuyen changes
    await generateInvestigatorData();
});

watch(() => filters.value.nam, async (newVal) => {
    // Re-generate data whenever nam changes
    await generateInvestigatorData();
});

// Watch 'hoTen' for immediate filtering, no need to regenerate all data
// The `filteredInvestigators` computed property handles this
watch(() => filters.value.hoTen, (newVal) => {
    console.log("Giá trị tìm kiếm 'Họ tên' thay đổi:", newVal);
    // No explicit call to generateInvestigatorData here because
    // filteredInvestigators computed property already reacts to changes in filters.hoTen.
    // However, if your actual API call depends on this, you might trigger applyFilters() or a data fetch here.
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
    white-space: nowrap; /* Đảm bảo chữ trên nút không bị xuống dòng */
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
}

.btn-danger {
    background-color: #dc3545;
    border-color: #dc3545;
}

.btn-info {
    background-color: #17a2b8;
    border-color: #17a2b8;
    color: white; /* Make text readable on info button */
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