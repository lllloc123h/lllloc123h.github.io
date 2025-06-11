<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Chọn Mẫu Địa Bàn</h2>

                <div class="card p-3 mb-4">
                    <div class="row g-3 align-items-end">
                        <div class="col-sm-6 col-md-4 col-lg-2">
                            <label for="filterYear" class="form-label">Năm</label>
                            <select v-model="filters.year" class="form-select" id="filterYear">
                                <option>2025</option>
                                <option>2024</option>
                                <option>2023</option>
                            </select>
                        </div>
                        <div class="col-sm-6 col-md-4 col-lg-2">
                            <label for="filterStatus" class="form-label">Trạng thái</label>
                            <select v-model="filters.status" class="form-select" id="filterStatus">
                                <option value="">---Tất cả---</option>
                                <option>Đang hoạt động</option>
                                <option>Ngừng hoạt động</option>
                            </select>
                        </div>
                        <div class="col-sm-6 col-md-4 col-lg-2">
                            <label for="filterProvince" class="form-label">Tỉnh/TP</label>
                            <select v-model="filters.province" class="form-select" id="filterProvince">
                                <option value="87">87 - Đồng Tháp</option>
                            </select>
                        </div>
                        <div class="col-sm-6 col-md-4 col-lg-2">
                            <label for="filterDistrict" class="form-label">Quận/Huyện</label>
                            <select v-model="filters.district" class="form-select" id="filterDistrict">
                                <option value="870">870 - Huyện Hồng Ngự</option>
                            </select>
                        </div>
                        <div class="col-sm-6 col-md-4 col-lg-2">
                            <label for="filterCommune" class="form-label">Xã/Phường</label>
                            <select v-model="filters.commune" class="form-select" id="filterCommune">
                                <option value="29980">29980 - Xã Long Khánh A</option>
                            </select>
                        </div>

                        <div class="col-12 col-lg-auto d-flex flex-wrap justify-content-lg-end justify-content-center mt-3 mt-lg-0">
                            <button class="btn btn-secondary me-2 mb-2" @click="viewMauDiaBan">
                                <i class="bi bi-eye"></i> Xem
                            </button>
                            <button class="btn btn-info me-2 mb-2" @click="exportExcel">
                                <i class="bi bi-file-earmark-excel"></i> Xuất excel
                            </button>
                            <button class="btn btn-success me-2 mb-2" @click="selectMauDiaBan">
                                <i class="bi bi-check-circle"></i> Chọn mẫu
                            </button>
                            <button class="btn btn-warning mb-2" @click="doiMauDiaBan">
                                <i class="bi bi-arrow-repeat"></i> Đổi mẫu
                            </button>
                        </div>
                    </div>
                </div>

                <table class="table table-bordered table-hover mt-3">
                    <thead>
                        <tr>
                            <th scope="col">Mã Tỉnh</th>
                            <th scope="col">Mã Huyện</th>
                            <th scope="col">Mã Xã</th>
                            <th scope="col">Thôn</th>
                            <th scope="col">Mã địa bàn</th>
                            <th scope="col">Địa bàn</th>
                            <th scope="col">Sổ Hộ</th>
                            <th scope="col">TTNT</th>
                            <th scope="col">Chọn mẫu</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="mauDiaBan in filteredMauDiaBans" :key="mauDiaBan.ma_dia_ban">
                            <td>{{ mauDiaBan.ma_tinh }}</td>
                            <td>{{ mauDiaBan.ma_huyen }}</td>
                            <td>{{ mauDiaBan.ma_xa }}</td>
                            <td>{{ mauDiaBan.thon }}</td>
                            <td>{{ mauDiaBan.ma_dia_ban }}</td>
                            <td>{{ mauDiaBan.dia_ban }}</td>
                            <td>{{ mauDiaBan.so_ho }}</td>
                            <td>{{ mauDiaBan.ttnt }}</td>
                            <td>
                                <div class="form-check d-flex justify-content-center">
                                    <input class="form-check-input" type="checkbox"
                                           :value="mauDiaBan.ma_dia_ban"
                                           v-model="selectedMauDiaBans">
                                </div>
                            </td>
                        </tr>
                        <tr v-if="filteredMauDiaBans.length === 0">
                            <td colspan="9" class="text-center">Chưa có dữ liệu</td>
                        </tr>
                    </tbody>
                </table>
            </main>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const filters = ref({
    year: '2025',
    status: '',
    province: '87',
    district: '870',
    commune: '29980'
});

const listMauDiaBan = ref([
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29980, thon: 'Thôn A', ma_dia_ban: 'DB001', dia_ban: 'Địa bàn ấp 1', so_ho: 150, ttnt: 1, year: '2025', status: 'Đang hoạt động' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29980, thon: 'Thôn A', ma_dia_ban: 'DB002', dia_ban: 'Địa bàn ấp 2', so_ho: 200, ttnt: 2, year: '2025', status: 'Đang hoạt động' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29980, thon: 'Thôn B', ma_dia_ban: 'DB003', dia_ban: 'Địa bàn ấp 3', so_ho: 120, ttnt: 1, year: '2024', status: 'Ngừng hoạt động' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29980, thon: 'Thôn B', ma_dia_ban: 'DB004', dia_ban: 'Địa bàn ấp 4', so_ho: 180, ttnt: 2, year: '2025', status: 'Đang hoạt động' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29980, thon: 'Thôn C', ma_dia_ban: 'DB005', dia_ban: 'Địa bàn ấp 5', so_ho: 90, ttnt: 1, year: '2023', status: 'Đang hoạt động' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29980, thon: 'Thôn D', ma_dia_ban: 'DB006', dia_ban: 'Địa bàn ấp 6', so_ho: 210, ttnt: 2, year: '2025', status: 'Ngừng hoạt động' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29980, thon: 'Thôn E', ma_dia_ban: 'DB007', dia_ban: 'Địa bàn ấp 7', so_ho: 130, ttnt: 1, year: '2024', status: 'Đang hoạt động' },
    { ma_tinh: 87, ma_huyen: 870, ma_xa: 29980, thon: 'Thôn F', ma_dia_ban: 'DB008', dia_ban: 'Địa bàn ấp 8', so_ho: 175, ttnt: 2, year: '2025', status: 'Đang hoạt động' },
]);

const selectedMauDiaBans = ref([]);

const filteredMauDiaBans = computed(() => {
    let filtered = listMauDiaBan.value;

    // Lọc theo năm
    if (filters.value.year) {
        filtered = filtered.filter(item => item.year === filters.value.year);
    }

    // Lọc theo trạng thái
    if (filters.value.status) {
        filtered = filtered.filter(item => item.status === filters.value.status);
    }

    // Lọc theo tỉnh
    if (filters.value.province) {
        filtered = filtered.filter(item => item.ma_tinh === parseInt(filters.value.province));
    }

    // Lọc theo huyện
    if (filters.value.district) {
        filtered = filtered.filter(item => item.ma_huyen === parseInt(filters.value.district));
    }

    // Lọc theo xã
    if (filters.value.commune) {
        filtered = filtered.filter(item => item.ma_xa === parseInt(filters.value.commune));
    }

    return filtered;
});

function viewMauDiaBan() {
    alert('Chức năng "Xem" mẫu địa bàn sẽ được thực hiện tại đây.');
}

function exportExcel() {
    alert('Chức năng "Xuất Excel" sẽ được thực hiện tại đây.');
}

function selectMauDiaBan() {
    if (selectedMauDiaBans.value.length > 0) {
        alert(`Bạn đã chọn các mẫu địa bàn có ID: ${selectedMauDiaBans.value.join(', ')}. Chức năng "Chọn mẫu" sẽ xử lý các mẫu này.`);
    } else {
        alert('Vui lòng chọn ít nhất một mẫu địa bàn.');
    }
}

function doiMauDiaBan() {
    alert('Chức năng "Đổi mẫu" sẽ được thực hiện tại đây.');
}

async function Logout() {
    console.log("Logging out...");
}
</script>

<style scoped>
/* Giữ nguyên các style hiện có */
.container-fluid {
    font-family: 'Segoe UI', sans-serif;
    background-color: #f8f9fa;
}

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

.table-hover tbody tr:hover {
    background-color: #e2e6ea;
    cursor: pointer;
}

/* Thêm một chút margin cho các nút trên màn hình nhỏ để chúng không bị dính vào nhau */
.mb-2 {
    margin-bottom: 0.5rem !important; /* Overrides default if needed */
}
</style>