<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center">Danh Mục Địa Bàn</h2>

                <div class="d-flex justify-content-between mb-3">
                    <div>
                        <button class="btn btn-success me-2" @click="addNewDiaBan">
                            <i class="bi bi-plus-circle"></i> Thêm mới
                        </button>
                        <button class="btn btn-info me-2" @click="editSelectedDiaBan">
                            <i class="bi bi-pencil-square"></i> Sửa
                        </button>
                        <button class="btn btn-danger me-2" @click="deleteSelectedDiaBan">
                            <i class="bi bi-trash"></i> Xóa
                        </button>
                        <button class="btn btn-secondary me-2" @click="exportExcel">
                            <i class="bi bi-file-earmark-excel"></i> Xuất Excel
                        </button>
                        <button class="btn btn-primary me-2" @click="addFromFile">
                            <i class="bi bi-file-earmark-arrow-up"></i> Thêm từ file Excel
                        </button>
                        <button class="btn btn-warning" @click="clearHuyenData">
                            <i class="bi bi-x-circle"></i> Xóa dữ liệu huyện
                        </button>
                    </div>
                </div>

                <div class="card p-3 mb-4">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-2">
                            <label for="filterYear" class="form-label">Năm</label>
                            <select v-model="filters.year" class="form-select" id="filterYear">
                                <option value="">Tất cả</option>
                                <option>2025</option>
                                <option>2024</option>
                                <option>2023</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label for="filterProvince" class="form-label">Tỉnh/TP</label>
                            <select v-model="filters.province" class="form-select" id="filterProvince">
                                <option value="">Chọn...</option>
                                <option value="87">87 - Đồng Tháp</option>
                                </select>
                        </div>
                        <div class="col-md-3">
                            <label for="filterDistrict" class="form-label">Quận/Huyện</label>
                            <select v-model="filters.district" class="form-select" id="filterDistrict">
                                <option value="">Chọn...</option>
                                <option value="870">870 - [Tên Huyện]</option>
                                </select>
                        </div>
                        <div class="col-md-2">
                            <label for="filterCommune" class="form-label">Xã/Phường</label>
                            <select v-model="filters.commune" class="form-select" id="filterCommune">
                                <option value="">Chọn...</option>
                                <option value="29956">29956</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label for="searchDiaBan" class="form-label">Tên địa bàn</label>
                            <input type="text" v-model="filters.search" class="form-control" id="searchDiaBan" placeholder="Nhập tên địa bàn...">
                        </div>
                        <div class="col-auto">
                            <button class="btn btn-primary" @click="applyFilters">
                                <i class="bi bi-search"></i> Tìm kiếm
                            </button>
                        </div>
                    </div>
                </div>

                <table class="table table-bordered table-hover mt-3">
                    <thead>
                        <tr>
                            <th scope="col">STT</th>
                            <th scope="col">Mã Tỉnh</th>
                            <th scope="col">Mã Huyện</th>
                            <th scope="col">Mã Xã</th>
                            <th scope="col">Tên Thôn</th>
                            <th scope="col">Mã Địa Bàn</th>
                            <th scope="col">Tên Địa Bàn</th>
                            <th scope="col">TT/NT</th>
                            <th scope="col">Ghi chú</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(diaBan, index) in filteredDiaBans" :key="diaBan.ma_dia_ban"
                            :class="{ 'table-active': selectedDiaBan && selectedDiaBan.ma_dia_ban === diaBan.ma_dia_ban }"
                            @click="selectDiaBan(diaBan)">
                            <td>{{ index + 1 }}</td>
                            <td>{{ diaBan.ma_tinh }}</td>
                            <td>{{ diaBan.ma_huyen }}</td>
                            <td>{{ diaBan.ma_xa }}</td>
                            <td>{{ diaBan.ten_thon }}</td>
                            <td>{{ diaBan.ma_dia_ban }}</td>
                            <td>{{ diaBan.ten_dia_ban }}</td>
                            <td>{{ diaBan.tt_nt }}</td>
                            <td>{{ diaBan.ghi_chu }}</td>
                        </tr>
                        <tr v-if="filteredDiaBans.length === 0">
                            <td colspan="9" class="text-center">Không có dữ liệu địa bàn nào phù hợp.</td>
                        </tr>
                    </tbody>
                </table>
            </main>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';

// Reactive data for filters
const filters = ref({
    year: '',
    province: '87', // Default to '87 - Đồng Tháp' as in the image
    district: '870', // Default to '870' as in the image
    commune: '',
    search: ''
});

// Sample data for the list of Dia Ban, based on your image
const listDiaBan = ref([
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '01. Ấp 1', ma_dia_ban: '001', ten_dia_ban: 'ấp 1+1', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '01. Ấp 1', ma_dia_ban: '002', ten_dia_ban: 'ấp 1+7', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '01. Ấp 1', ma_dia_ban: '003', ten_dia_ban: 'ấp 1+9', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '01. Ấp 1', ma_dia_ban: '004', ten_dia_ban: 'ấp 1+4', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '01. Ấp 1', ma_dia_ban: '005', ten_dia_ban: 'ấp 1+5', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '01. Ấp 1', ma_dia_ban: '006', ten_dia_ban: 'ấp 1+2', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '01. Ấp 1', ma_dia_ban: '007', ten_dia_ban: 'ấp 1+3', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '01. Ấp 1', ma_dia_ban: '008', ten_dia_ban: 'ấp 1+8', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '01. Ấp 1', ma_dia_ban: '009', ten_dia_ban: 'ấp 1+6', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '01. Ấp 1', ma_dia_ban: '010', ten_dia_ban: 'ấp 1', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '02. Ấp 2', ma_dia_ban: '011', ten_dia_ban: 'ấp 2+9', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '02. Ấp 2', ma_dia_ban: '012', ten_dia_ban: 'ấp 2+6', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '02. Ấp 2', ma_dia_ban: '013', ten_dia_ban: 'ấp 2+3', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '02. Ấp 2', ma_dia_ban: '014', ten_dia_ban: 'ấp 2+2', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '02. Ấp 2', ma_dia_ban: '015', ten_dia_ban: 'ấp 2+10', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '02. Ấp 2', ma_dia_ban: '016', ten_dia_ban: 'ấp 2+4', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '02. Ấp 2', ma_dia_ban: '017', ten_dia_ban: 'ấp 2+11', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '02. Ấp 2', ma_dia_ban: '018', ten_dia_ban: 'ấp 2+1', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '02. Ấp 2', ma_dia_ban: '019', ten_dia_ban: 'ấp 2+12', tt_nt: 2, ghi_chu: '' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29956, ten_thon: '02. Ấp 2', ma_dia_ban: '020', ten_dia_ban: 'ấp 2+8', tt_nt: 2, ghi_chu: '' }
]);

// Reactive variable to store the currently selected row
const selectedDiaBan = ref(null);

// Computed property to filter the list based on filter criteria
const filteredDiaBans = computed(() => {
    let filtered = listDiaBan.value;

    // Filter by province
    if (filters.value.province) {
        filtered = filtered.filter(item => item.ma_tinh === parseInt(filters.value.province));
    }

    // Filter by district
    if (filters.value.district) {
        filtered = filtered.filter(item => item.ma_huyen === parseInt(filters.value.district));
    }

    // Filter by commune (Mã Xã)
    if (filters.value.commune) {
        filtered = filtered.filter(item => item.ma_xa === parseInt(filters.value.commune));
    }

    // Filter by search text (Tên Địa Bàn)
    if (filters.value.search) {
        const searchTerm = filters.value.search.toLowerCase();
        filtered = filtered.filter(item => 
            item.ten_dia_ban.toLowerCase().includes(searchTerm) ||
            item.ten_thon.toLowerCase().includes(searchTerm) ||
            item.ma_dia_ban.toLowerCase().includes(searchTerm)
        );
    }
    
    // Note: Year filter is present in UI but no corresponding data in sample
    // If you add a 'year' field to your listDiaBan, you can enable this.
    // if (filters.value.year) {
    //     filtered = filtered.filter(item => item.year === parseInt(filters.value.year));
    // }

    return filtered;
});

// Method to apply filters (re-computes filteredDiaBans)
function applyFilters() {
    console.log('Applying filters:', filters.value);
    // The computed property `filteredDiaBans` will automatically react to changes in `filters.value`
    // No explicit action needed here beyond logging or triggering an API call in a real app.
}

// Method to select a row
function selectDiaBan(diaBan) {
    selectedDiaBan.value = diaBan;
    console.log('Selected Dia Ban:', diaBan);
}

// Action methods for buttons (placeholders for actual logic)
function addNewDiaBan() {
    alert('Chức năng "Thêm mới" sẽ được thực hiện tại đây.');
    // You would typically open a modal or navigate to a new form.
}

function editSelectedDiaBan() {
    if (selectedDiaBan.value) {
        alert(`Chức năng "Sửa" địa bàn ID: ${selectedDiaBan.value.ma_dia_ban} sẽ được thực hiện.`);
        // Open a modal or navigate to an edit form pre-filled with selectedDiaBan.value data.
    } else {
        alert('Vui lòng chọn một địa bàn để sửa.');
    }
}

function deleteSelectedDiaBan() {
    if (selectedDiaBan.value) {
        if (confirm(`Bạn có chắc chắn muốn xóa địa bàn ID: ${selectedDiaBan.value.ma_dia_ban} không?`)) {
            listDiaBan.value = listDiaBan.value.filter(item => item.ma_dia_ban !== selectedDiaBan.value.ma_dia_ban);
            selectedDiaBan.value = null; // Clear selection after deletion
            alert('Đã xóa địa bàn.');
            // In a real app, send DELETE request to backend.
        }
    } else {
        alert('Vui lòng chọn một địa bàn để xóa.');
    }
}

function exportExcel() {
    alert('Chức năng "Xuất Excel" sẽ được thực hiện tại đây.');
    // Implement logic to generate and download an Excel file (client-side or via backend API).
}

function addFromFile() {
    alert('Chức năng "Thêm từ file Excel" sẽ được thực hiện tại đây.');
    // Implement logic to handle file upload and parsing.
}

function clearHuyenData() {
    if (confirm('Bạn có chắc chắn muốn xóa dữ liệu của huyện hiện tại không? (Chức năng này cần backend xử lý)')) {
        alert('Chức năng "Xóa dữ liệu huyện" đã được kích hoạt. Cần backend để xử lý.');
        // This usually involves sending a request to the backend to delete all data for the selected district.
    }
}

// Placeholder for a logout function, if needed in the future
async function Logout() {
    console.log("Logging out...");
}
</script>

<style scoped>
/* Scoped styles ensure these rules only apply to this component */

/* Base font and background for the main container */
.container-fluid {
    font-family: 'Segoe UI', sans-serif;
    background-color: #f8f9fa;
}

/* Optional: Styles for a potential sidebar/header if this component is part of a larger layout */
.sidebar {
    min-height: 100vh;
    background-color: #343a40;
    color: white;
}
.sidebar a { color: white; text-decoration: none; }
.sidebar a:hover { color: #ffc107; }
.sidebar .nav-link.active { background-color: #495057; }

.header {
    background-color: #007bff;
    color: white;
    padding: 10px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.header h1 { margin: 0; }
.logout-btn {
    color: white;
    background-color: #dc3545;
    border: none;
    padding: 5px 15px;
    cursor: pointer;
    border-radius: 5px;
}
.logout-btn:hover { background-color: #c82333; }

/* Table specific styling for row selection */
.table-hover tbody tr:hover {
    background-color: #e2e6ea; /* Light gray on hover */
    cursor: pointer;
}

.table-active {
    background-color: #cce5ff !important; /* Lighter blue for selected row */
}
</style>