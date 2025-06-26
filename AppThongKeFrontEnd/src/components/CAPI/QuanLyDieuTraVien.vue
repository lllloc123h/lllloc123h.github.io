<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Danh sách điều tra viên</h2>

                <div class="card p-3 mb-4 shadow-sm">
                    <div class="d-flex flex-wrap align-items-center gap-2 mb-3">
                        <button class="btn btn-primary" @click="openAddModal">
                            <i class="bi bi-plus-circle"></i> Thêm mới
                        </button>
                        <button class="btn btn-warning text-dark" @click="openEditModal" :disabled="selectedDieuTraVien.length !== 1">
                            <i class="bi bi-pencil-square"></i> Sửa
                        </button>
                        <button class="btn btn-danger" @click="deleteDieuTraVien" :disabled="selectedDieuTraVien.length === 0">
                            <i class="bi bi-trash"></i> Xóa
                        </button>
                        <button class="btn btn-info text-dark" disabled><i class="bi bi-person-up"></i> Thay IMEI</button>
                        <button class="btn btn-secondary" disabled><i class="bi bi-lock"></i> Cho phép CAPI</button>
                        <button class="btn btn-secondary" disabled><i class="bi bi-unlock"></i> Không cho phép CAPI</button>
                        <button class="btn btn-primary" disabled><i class="bi bi-check-circle"></i> Kiểm tra IMEI</button>
                        <button class="btn btn-dark" disabled><i class="bi bi-x-circle"></i> Không kiểm tra IMEI</button>
                        <button class="btn btn-secondary" disabled><i class="bi bi-key"></i> Khôi phục mật khẩu</button>

                    </div>

                    <div class="row g-3 align-items-end">
                        <div class="col-md-2 col-sm-6">
                            <label for="filterNam" class="form-label visually-hidden">Năm</label>
                            <select v-model="filters.nam" class="form-select" id="filterNam">
                                <option value="">--Chọn Năm--</option>
                                <option>2025</option>
                                <option>2024</option>
                                <option>2023</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-2 col-sm-6">
                            <label for="filterTinhTp" class="form-label visually-hidden">Tỉnh/TP</label>
                            <select v-model="filters.maTinh" class="form-select" id="filterTinhTp" @change="fetchDistricts">
                                <option value="">--Chọn Tỉnh--</option>
                                <option v-for="tinh in provinces" :key="tinh.ma" :value="tinh.ma">{{ tinh.ma }} - {{ tinh.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-2 col-sm-6">
                            <label for="filterQuanHuyen" class="form-label visually-hidden">Quận/Huyện</label>
                            <select v-model="filters.maHuyen" class="form-select" id="filterQuanHuyen" :disabled="!districts.length">
                                <option value="">--Chọn Huyện--</option>
                                <option v-for="huyen in districts" :key="huyen.ma" :value="huyen.ma">{{ huyen.ma }} - {{ huyen.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-4 col-lg-3 col-sm-6">
                            <label for="filterHoTen" class="form-label visually-hidden">Họ tên/Điện thoại</label>
                            <input type="text" v-model="filters.searchText" class="form-control" id="filterHoTen" placeholder="Họ tên / SĐT...">
                        </div>
                        <div class="col-md-auto">
                            <button class="btn btn-primary" @click="applyFilters">
                                <i class="bi bi-search"></i> Tìm
                            </button>
                        </div>
                    </div>
                </div>

                <div class="d-flex justify-content-end gap-2 mb-3">
                    <button class="btn btn-success" @click="exportExcel">
                        <i class="bi bi-file-earmark-excel"></i> Xuất Excel
                    </button>
                    <button class="btn btn-info text-dark" disabled>
                        <i class="bi bi-upload"></i> Thêm từ file Excel
                    </button>
                </div>


                <div class="table-responsive">
                    <table class="table table-bordered table-hover caption-top">
                        <caption>Tổng số bản ghi: {{ filteredDieuTraVien.length }}</caption>
                        <thead class="table-primary">
                            <tr>
                                <th scope="col" class="text-center">
                                    <input type="checkbox" @change="toggleSelectAll" :checked="selectedDieuTraVien.length === filteredDieuTraVien.length && filteredDieuTraVien.length > 0">
                                </th>
                                <th scope="col">STT</th>
                                <th scope="col">Mã Huyện</th>
                                <th scope="col">Tên Huyện</th>
                                <th scope="col">Mã BTV</th>
                                <th scope="col" style="min-width: 180px;">Họ và tên</th>
                                <th scope="col">Số điện thoại</th>
                                <th scope="col" style="min-width: 250px;">Địa chỉ</th>
                                <th scope="col">Ghi chú</th>
                                <th scope="col">IMEI</th>
                                <th scope="col">Check IMEI</th>
                                <th scope="col">Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-if="filteredDieuTraVien.length === 0">
                                <td colspan="12" class="text-center">Không có dữ liệu</td>
                            </tr>
                            <tr v-for="(dtv, index) in filteredDieuTraVien" :key="dtv.id">
                                <td class="text-center">
                                    <input type="checkbox" :value="dtv.id" v-model="selectedDieuTraVien">
                                </td>
                                <td>{{ index + 1 }}</td>
                                <td>{{ dtv.maHuyen }}</td>
                                <td>{{ getHuyenTen(dtv.maHuyen) }}</td>
                                <td>{{ dtv.maBTV }}</td>
                                <td>{{ dtv.hoVaTen }}</td>
                                <td>{{ dtv.soDienThoai }}</td>
                                <td>{{ dtv.diaChi }}</td>
                                <td>{{ dtv.ghiChu }}</td>
                                <td>{{ dtv.imei }}</td>
                                <td class="text-center">{{ dtv.checkImei ? 'X' : '' }}</td>
                                <td>{{ dtv.trangThai }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>

        <div class="modal fade" id="dieutraVienModal" tabindex="-1" aria-labelledby="dieutraVienModalLabel" aria-hidden="true" ref="dieutraVienModalRef">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="dieutraVienModalLabel">{{ isEditMode ? 'Sửa Điều tra viên' : 'Thêm mới Điều tra viên' }}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form @submit.prevent="saveDieuTraVien">
                            <div class="mb-3">
                                <label for="modalMaTinh" class="form-label">Tỉnh/TP</label>
                                <select v-model="currentDieuTraVien.maTinh" class="form-select" id="modalMaTinh" @change="fetchModalDistricts" required>
                                    <option value="">--Chọn Tỉnh--</option>
                                    <option v-for="tinh in provinces" :key="tinh.ma" :value="tinh.ma">{{ tinh.ma }} - {{ tinh.ten }}</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="modalMaHuyen" class="form-label">Quận/Huyện</label>
                                <select v-model="currentDieuTraVien.maHuyen" class="form-select" id="modalMaHuyen" :disabled="!modalDistricts.length" required>
                                    <option value="">--Chọn Huyện--</option>
                                    <option v-for="huyen in modalDistricts" :key="huyen.ma" :value="huyen.ma">{{ huyen.ma }} - {{ huyen.ten }}</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="modalMaBTV" class="form-label">Mã BTV</label>
                                <input type="text" v-model="currentDieuTraVien.maBTV" class="form-control" id="modalMaBTV" required>
                            </div>
                            <div class="mb-3">
                                <label for="modalHoTen" class="form-label">Họ và tên</label>
                                <input type="text" v-model="currentDieuTraVien.hoVaTen" class="form-control" id="modalHoTen" required>
                            </div>
                            <div class="mb-3">
                                <label for="modalSoDienThoai" class="form-label">Số điện thoại</label>
                                <input type="tel" v-model="currentDieuTraVien.soDienThoai" class="form-control" id="modalSoDienThoai" required pattern="[0-9]{10,11}">
                            </div>
                            <div class="mb-3">
                                <label for="modalDiaChi" class="form-label">Địa chỉ</label>
                                <textarea v-model="currentDieuTraVien.diaChi" class="form-control" id="modalDiaChi" rows="2"></textarea>
                            </div>
                            <div class="mb-3">
                                <label for="modalGhiChu" class="form-label">Ghi chú</label>
                                <input type="text" v-model="currentDieuTraVien.ghiChu" class="form-control" id="modalGhiChu">
                            </div>
                            <div class="mb-3">
                                <label for="modalImei" class="form-label">IMEI</label>
                                <input type="text" v-model="currentDieuTraVien.imei" class="form-control" id="modalImei">
                            </div>
                            <div class="form-check mb-3">
                                <input class="form-check-input" type="checkbox" v-model="currentDieuTraVien.checkImei" id="modalCheckImei">
                                <label class="form-check-label" for="modalCheckImei">
                                    Check IMEI
                                </label>
                            </div>
                            <div class="mb-3">
                                <label for="modalTrangThai" class="form-label">Trạng thái</label>
                                <select v-model="currentDieuTraVien.trangThai" class="form-select" id="modalTrangThai" required>
                                    <option value="Đang hoạt động">Đang hoạt động</option>
                                    <option value="Ngưng hoạt động">Ngưng hoạt động</option>
                                    <option value="Đã khóa">Đã khóa</option>
                                </select>
                            </div>
                            <div class="modal-footer d-flex justify-content-end">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                <button type="submit" class="btn btn-primary">Lưu</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import { Modal } from 'bootstrap'; // Import Bootstrap's Modal JS

// --- State Variables ---
const allDieuTraVien = ref([]); // Store all data
const selectedDieuTraVien = ref([]); // Store IDs of selected rows for edit/delete
const currentDieuTraVien = ref({}); // Data for the add/edit modal
const isEditMode = ref(false); // Flag for add/edit modal mode

const filters = ref({
    nam: '2025', // Default year
    maTinh: '87', // Default province: Đồng Tháp
    maHuyen: '870', // Default district: Hồng Ngự
    searchText: '' // For Ho ten / SĐT
});

const provinces = ref([
    { ma: '87', ten: 'Đồng Tháp' },
    { ma: '88', ten: 'Long An' },
    { ma: '89', ten: 'An Giang' },
    { ma: '92', ten: 'Cần Thơ' },
]);

const districts = ref([]); // Districts for the main filter
const modalDistricts = ref([]); // Districts for the modal form

const dieutraVienModalRef = ref(null); // Reference to the modal DOM element
let dieutraVienBsModal = null; // Bootstrap Modal instance

// --- Computed Properties ---
const filteredDieuTraVien = computed(() => {
    let data = allDieuTraVien.value;

    return data.filter(dtv => {
        const matchesNam = filters.value.nam === '' || dtv.nam === filters.value.nam;
        const matchesTinh = filters.value.maTinh === '' || dtv.maTinh === filters.value.maTinh;
        const matchesHuyen = filters.value.maHuyen === '' || dtv.maHuyen === filters.value.maHuyen;

        const searchTextLower = filters.value.searchText.toLowerCase();
        const matchesSearch = filters.value.searchText === '' ||
                              dtv.hoVaTen.toLowerCase().includes(searchTextLower) ||
                              dtv.soDienThoai.includes(searchTextLower);

        return matchesNam && matchesTinh && matchesHuyen && matchesSearch;
    });
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
        // Tự động chọn huyện Hồng Ngự nếu là Đồng Tháp (theo hình ảnh)
        if (filters.value.maTinh === '87' && districts.value.some(d => d.ma === '870')) {
            filters.value.maHuyen = '870';
        } else if (districts.value.length > 0) {
            filters.value.maHuyen = districts.value[0].ma;
        }
    }
}

async function fetchModalDistricts() {
    modalDistricts.value = [];
    if (currentDieuTraVien.value.maTinh) {
        const mockDistrictsData = [
            { ma: '870', ten: 'Hồng Ngự', ma_tinh: '87' },
            { ma: '871', ten: 'Tam Nông', ma_tinh: '87' },
            { ma: '872', ten: 'Tháp Mười', ma_tinh: '87' },
            { ma: '880', ten: 'TP Tân An', ma_tinh: '88' },
            { ma: '881', ten: 'Đức Huệ', ma_tinh: '88' },
            { ma: '890', ten: 'TP Long Xuyên', ma_tinh: '89' },
            { ma: '891', ten: 'Châu Đốc', ma_tinh: '89' },
        ];
        modalDistricts.value = await mockApiCall(mockDistrictsData.filter(d => d.ma_tinh === currentDieuTraVien.value.maTinh));
    }
}

// Helper to get Huyen Ten for display
function getHuyenTen(maHuyen) {
    const huyen = districts.value.find(d => d.ma === maHuyen);
    return huyen ? huyen.ten : 'N/A';
}

// --- Main Data Management (CRUD Mock) ---
async function fetchAllDieuTraVien() {
    console.log("Đang tải dữ liệu điều tra viên...");
    const dummyData = [];
    const numRecords = 20;

    const trangThaiOptions = ['Đang hoạt động', 'Ngưng hoạt động', 'Đã khóa'];

    for (let i = 1; i <= numRecords; i++) {
        const maTinh = provinces.value[Math.floor(Math.random() * provinces.value.length)].ma;
        let maHuyen = '';
        let tenHuyen = '';

        if (maTinh === '87') {
            const huyenOptions = [{ma: '870', ten: 'Hồng Ngự'}, {ma: '871', ten: 'Tam Nông'}];
            const selectedHuyen = huyenOptions[Math.floor(Math.random() * huyenOptions.length)];
            maHuyen = selectedHuyen.ma;
            tenHuyen = selectedHuyen.ten;
        } else if (maTinh === '88') {
            const huyenOptions = [{ma: '880', ten: 'TP Tân An'}, {ma: '881', ten: 'Đức Huệ'}];
            const selectedHuyen = huyenOptions[Math.floor(Math.random() * huyenOptions.length)];
            maHuyen = selectedHuyen.ma;
            tenHuyen = selectedHuyen.ten;
        } else if (maTinh === '89') {
            const huyenOptions = [{ma: '890', ten: 'TP Long Xuyên'}, {ma: '891', ten: 'Châu Đốc'}];
            const selectedHuyen = huyenOptions[Math.floor(Math.random() * huyenOptions.length)];
            maHuyen = selectedHuyen.ma;
            tenHuyen = selectedHuyen.ten;
        } else { // Cần Thơ
            maHuyen = '920'; // Ví dụ: Quận Ninh Kiều
            tenHuyen = 'Ninh Kiều';
        }

        const hoVaTen = `DT V${i} ${String.fromCharCode(65 + Math.floor(Math.random() * 26))}`;
        const soDienThoai = `0${Math.floor(Math.random() * 900000000) + 100000000}`;
        const diaChi = `Ấp ${Math.floor(Math.random() * 5) + 1}, Xã ${tenHuyen.split(' ')[0]} ${String.fromCharCode(65 + Math.floor(Math.random() * 5))}, Huyện ${tenHuyen}, Tỉnh ${provinces.value.find(p => p.ma === maTinh)?.ten}`;
        const imei = `IMEI${Math.floor(Math.random() * 1000000).toString().padStart(6, '0')}`;

        dummyData.push({
            id: crypto.randomUUID(), // Unique ID for each record
            stt: i,
            nam: '2025', // Default năm cho dữ liệu mock, có thể random nếu muốn
            maTinh: maTinh,
            maHuyen: maHuyen,
            // tenHuyen: tenHuyen, // Could store this but computed property is better
            maBTV: `D${maHuyen}${String(i).padStart(3, '0')}`,
            hoVaTen: hoVaTen,
            soDienThoai: soDienThoai,
            diaChi: diaChi,
            ghiChu: Math.random() > 0.7 ? 'Ghi chú thử nghiệm' : '',
            imei: imei,
            checkImei: Math.random() > 0.5,
            trangThai: trangThaiOptions[Math.floor(Math.random() * trangThaiOptions.length)],
        });
    }
    allDieuTraVien.value = await mockApiCall(dummyData);
}

function applyFilters() {
    // Computed property `filteredDieuTraVien` sẽ tự động cập nhật bảng.
    console.log("Áp dụng bộ lọc:", filters.value);
    selectedDieuTraVien.value = []; // Clear selection on filter
}

function exportExcel() {
    alert('Chức năng Xuất Excel (cần implement logic)');
    // Sử dụng thư viện như SheetJS (xlsx) để xuất `filteredDieuTraVien.value` ra file Excel.
    /*
    import * as XLSX from 'xlsx';
    const ws = XLSX.utils.json_to_sheet(filteredDieuTraVien.value);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "DanhSachDieuTraVien");
    XLSX.writeFile(wb, "DanhSachDieuTraVien.xlsx");
    */
}

// --- CRUD Operations ---

function openAddModal() {
    isEditMode.value = false;
    currentDieuTraVien.value = {
        id: crypto.randomUUID(), // Generate a new ID
        nam: '2025', // Default for new entry
        maTinh: '',
        maHuyen: '',
        maBTV: '',
        hoVaTen: '',
        soDienThoai: '',
        diaChi: '',
        ghiChu: '',
        imei: '',
        checkImei: false,
        trangThai: 'Đang hoạt động',
    };
    modalDistricts.value = []; // Clear modal districts for fresh selection
    dieutraVienBsModal.show();
}

function openEditModal() {
    if (selectedDieuTraVien.value.length === 1) {
        isEditMode.value = true;
        const dtvToEdit = allDieuTraVien.value.find(dtv => dtv.id === selectedDieuTraVien.value[0]);
        if (dtvToEdit) {
            currentDieuTraVien.value = { ...dtvToEdit }; // Create a copy to avoid direct mutation
            fetchModalDistricts().then(() => { // Fetch districts for the selected province in modal
                 dieutraVienBsModal.show();
            });
        }
    } else {
        alert('Vui lòng chọn DUY NHẤT một điều tra viên để sửa.');
    }
}

function saveDieuTraVien() {
    if (isEditMode.value) {
        // Find index and update
        const index = allDieuTraVien.value.findIndex(dtv => dtv.id === currentDieuTraVien.value.id);
        if (index !== -1) {
            allDieuTraVien.value[index] = { ...currentDieuTraVien.value };
            alert('Cập nhật điều tra viên thành công!');
        }
    } else {
        // Add new
        allDieuTraVien.value.push(currentDieuTraVien.value);
        alert('Thêm mới điều tra viên thành công!');
    }
    selectedDieuTraVien.value = []; // Clear selection
    dieutraVienBsModal.hide(); // Hide modal
}

function deleteDieuTraVien() {
    if (selectedDieuTraVien.value.length === 0) {
        alert('Vui lòng chọn điều tra viên để xóa.');
        return;
    }

    if (confirm(`Bạn có chắc chắn muốn xóa ${selectedDieuTraVien.value.length} điều tra viên đã chọn không?`)) {
        allDieuTraVien.value = allDieuTraVien.value.filter(
            dtv => !selectedDieuTraVien.value.includes(dtv.id)
        );
        selectedDieuTraVien.value = []; // Clear selection
        alert('Xóa điều tra viên thành công!');
    }
}

function toggleSelectAll(event) {
    if (event.target.checked) {
        selectedDieuTraVien.value = filteredDieuTraVien.value.map(dtv => dtv.id);
    } else {
        selectedDieuTraVien.value = [];
    }
}

// --- Lifecycle Hooks and Watchers ---
onMounted(() => {
    // Initialize Bootstrap Modal instance
    if (dieutraVienModalRef.value) {
        dieutraVienBsModal = new Modal(dieutraVienModalRef.value);
        // Add event listener to clear currentDieuTraVien when modal is hidden
        dieutraVienModalRef.value.addEventListener('hidden.bs.modal', () => {
            currentDieuTraVien.value = {};
            modalDistricts.value = []; // Clear modal districts too
        });
    }

    // Initial data load
    fetchDistricts().then(() => {
        fetchAllDieuTraVien();
    });
});

// Watcher for province filter to update district filter dropdown
watch(() => filters.value.maTinh, (newVal) => {
    if (newVal) {
        fetchDistricts();
    } else {
        filters.value.maHuyen = '';
        districts.value = [];
    }
});

// Watcher for province in modal to update modal district dropdown
watch(() => currentDieuTraVien.value.maTinh, (newVal) => {
    if (newVal) {
        fetchModalDistricts();
    } else {
        currentDieuTraVien.value.maHuyen = '';
        modalDistricts.value = [];
    }
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
}

.btn-secondary {
    background-color: #6c757d;
    border-color: #6c757d;
}

.btn-dark {
    background-color: #343a40;
    border-color: #343a40;
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