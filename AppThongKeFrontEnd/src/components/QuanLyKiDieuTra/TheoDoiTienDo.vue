<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Thông tin kê khai</h2>

                <div class="card p-3 mb-4 shadow-sm">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-2 col-sm-6">
                            <label for="kyDieuTra" class="form-label">Kỳ điều tra</label>
                            <select v-model="filters.kyDieuTra" class="form-select" id="kyDieuTra">
                                <option value="4">4</option>
                                <option value="3">3</option>
                                <option value="2">2</option>
                                <option value="1">1</option>
                            </select>
                        </div>
                        <div class="col-md-2 col-sm-6">
                            <label for="nam" class="form-label">Năm</label>
                            <select v-model="filters.nam" class="form-select" id="nam">
                                <option>2025</option>
                                <option>2024</option>
                                <option>2023</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-2 col-sm-6">
                            <label for="tinhTp" class="form-label">Tỉnh/TP</label>
                            <select v-model="filters.maTinh" class="form-select" id="tinhTp" @change="fetchDistricts">
                                <option value="">--Chọn Tỉnh--</option>
                                <option v-for="tinh in provinces" :key="tinh.ma" :value="tinh.ma">{{ tinh.ma }} - {{ tinh.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-2 col-sm-6">
                            <label for="quanHuyen" class="form-label">Quận/Huyện</label>
                            <select v-model="filters.maHuyen" class="form-select" id="quanHuyen" @change="fetchCommunes" :disabled="!districts.length">
                                <option value="">--Chọn Huyện--</option>
                                <option v-for="huyen in districts" :key="huyen.ma" :value="huyen.ma">{{ huyen.ma }} - {{ huyen.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-2 col-sm-6">
                            <label for="thongKeTheo" class="form-label">Thống kê theo</label>
                            <select v-model="filters.thongKeTheo" class="form-select" id="thongKeTheo">
                                <option value="3-xa">3-Xã</option>
                                <option value="2-huyen">2-Huyện</option>
                                <option value="1-tinh">1-Tỉnh</option>
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
                        <caption>Tổng số bản ghi: {{ filteredInvestigationData.length }}</caption>
                        <thead class="table-primary">
                            <tr>
                                <th rowspan="2" scope="col">STT</th>
                                <th rowspan="2" scope="col">Mã Tỉnh</th>
                                <th rowspan="2" scope="col">Mã Huyện</th>
                                <th rowspan="2" scope="col">Mã Xã</th>
                                <th rowspan="2" scope="col" style="min-width: 180px;">Tên (Nhấn đúp vào dòng để hiển thị danh sách chưa thực hiện điều tra)</th>
                                <th colspan="3" class="text-center">Điều tra Doanh nghiệp, HTX</th>
                                <th colspan="3" class="text-center">Điều tra Hộ</th>
                            </tr>
                            <tr>
                                <th scope="col" class="text-center">Số lượng</th>
                                <th scope="col" class="text-center">Hoàn thành</th>
                                <th scope="col" class="text-center">Tỷ lệ</th>
                                <th scope="col" class="text-center">Số lượng</th>
                                <th scope="col" class="text-center">Hoàn thành</th>
                                <th scope="col" class="text-center">Tỷ lệ</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(item, index) in filteredInvestigationData" :key="getKey(item, index)" @dblclick="showUninvestigatedList(item)">
                                <td>{{ index + 1 }}</td>
                                <td>{{ item.ma_tinh }}</td>
                                <td>{{ item.ma_huyen }}</td>
                                <td>{{ item.ma_xa || '' }}</td>
                                <td>{{ item.ten }}</td>
                                <td class="text-center">{{ item.dn_htx_so_luong }}</td>
                                <td class="text-center">{{ item.dn_htx_hoan_thanh }}</td>
                                <td class="text-center">{{ item.dn_htx_ty_le }}%</td>
                                <td class="text-center">{{ item.ho_so_luong }}</td>
                                <td class="text-center">{{ item.ho_hoan_thanh }}</td>
                                <td class="text-center">{{ item.ho_ty_le }}%</td>
                            </tr>
                             <tr v-if="filteredInvestigationData.length === 0">
                                <td colspan="11" class="text-center">Không tìm thấy dữ liệu nào.</td>
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
import 'bootstrap-icons/font/bootstrap-icons.css'; // Để sử dụng icon Bootstrap

// --- State Variables (Biến trạng thái) ---
const filters = ref({
    kyDieuTra: '4',
    nam: '2025',
    maTinh: '',
    maHuyen: '',
    thongKeTheo: '3-xa' // Mặc định thống kê theo xã
});

// Dữ liệu tĩnh cho các dropdown
const provinces = ref([
    { ma: '87', ten: 'Đồng Tháp' },
    { ma: '88', ten: 'Long An' },
    { ma: '89', ten: 'An Giang' },
]);
const districts = ref([]);
const communes = ref([]); // Sẽ cần cho việc chọn Huyện -> Xã nếu muốn lọc chi tiết hơn

// Dữ liệu mẫu cho bảng
const allInvestigationData = ref([]);

// --- Computed Properties ---
const filteredInvestigationData = computed(() => {
    let data = allInvestigationData.value;

    // Lọc theo Kỳ điều tra và Năm
    data = data.filter(item =>
        item.ky_dieu_tra === filters.value.kyDieuTra &&
        item.nam === filters.value.nam
    );

    // Lọc theo Tỉnh
    if (filters.value.maTinh) {
        data = data.filter(item => item.ma_tinh === filters.value.maTinh);
    }

    // Lọc theo Huyện
    if (filters.value.maHuyen) {
        data = data.filter(item => item.ma_huyen === filters.value.maHuyen);
    }

    // Áp dụng "Thống kê theo"
    if (filters.value.thongKeTheo === '1-tinh') {
        // Gom nhóm theo Tỉnh
        const grouped = {};
        data.forEach(item => {
            if (!grouped[item.ma_tinh]) {
                grouped[item.ma_tinh] = {
                    stt: 0, // Sẽ được gán lại sau
                    ma_tinh: item.ma_tinh,
                    ma_huyen: '',
                    ma_xa: '',
                    ten: provinces.value.find(p => p.ma === item.ma_tinh)?.ten || `Tỉnh ${item.ma_tinh}`,
                    dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: 0,
                    ho_so_luong: 0, ho_hoan_thanh: 0, ho_ty_le: 0,
                    ky_dieu_tra: item.ky_dieu_tra,
                    nam: item.nam
                };
            }
            grouped[item.ma_tinh].dn_htx_so_luong += item.dn_htx_so_luong;
            grouped[item.ma_tinh].dn_htx_hoan_thanh += item.dn_htx_hoan_thanh;
            grouped[item.ma_tinh].ho_so_luong += item.ho_so_luong;
            grouped[item.ma_tinh].ho_hoan_thanh += item.ho_hoan_thanh;
        });
        const result = Object.values(grouped).map((item, idx) => {
            item.dn_htx_ty_le = item.dn_htx_so_luong > 0 ? ((item.dn_htx_hoan_thanh / item.dn_htx_so_luong) * 100).toFixed(2) : '0.00';
            item.ho_ty_le = item.ho_so_luong > 0 ? ((item.ho_hoan_thanh / item.ho_so_luong) * 100).toFixed(2) : '0.00';
            item.stt = idx + 1; // Cập nhật STT sau khi gom nhóm
            return item;
        });
        return result;

    } else if (filters.value.thongKeTheo === '2-huyen') {
        // Gom nhóm theo Huyện
        const grouped = {};
        data.forEach(item => {
            const key = `${item.ma_tinh}-${item.ma_huyen}`;
            if (!grouped[key]) {
                grouped[key] = {
                    stt: 0, // Sẽ được gán lại sau
                    ma_tinh: item.ma_tinh,
                    ma_huyen: item.ma_huyen,
                    ma_xa: '',
                    ten: districts.value.find(d => d.ma === item.ma_huyen && d.ma_tinh === item.ma_tinh)?.ten || `Huyện ${item.ma_huyen}`,
                    dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: 0,
                    ho_so_luong: 0, ho_hoan_thanh: 0, ho_ty_le: 0,
                    ky_dieu_tra: item.ky_dieu_tra,
                    nam: item.nam
                };
            }
            grouped[key].dn_htx_so_luong += item.dn_htx_so_luong;
            grouped[key].dn_htx_hoan_thanh += item.dn_htx_hoan_thanh;
            grouped[key].ho_so_luong += item.ho_so_luong;
            grouped[key].ho_hoan_thanh += item.ho_hoan_thanh;
        });
        const result = Object.values(grouped).map((item, idx) => {
            item.dn_htx_ty_le = item.dn_htx_so_luong > 0 ? ((item.dn_htx_hoan_thanh / item.dn_htx_so_luong) * 100).toFixed(2) : '0.00';
            item.ho_ty_le = item.ho_so_luong > 0 ? ((item.ho_hoan_thanh / item.ho_so_luong) * 100).toFixed(2) : '0.00';
            item.stt = idx + 1; // Cập nhật STT sau khi gom nhóm
            return item;
        });
        return result;
    }
    // Mặc định là '3-xa' hoặc khi không có bộ lọc thống kê
    // Lọc theo ma_xa nếu có giá trị, nếu không thì hiển thị tất cả các xã của huyện đã chọn
    if (filters.value.maHuyen && !filters.value.maTinh) { // Nếu chỉ chọn huyện mà không chọn tỉnh
        // Đây là một kịch bản ít xảy ra nếu tỉnh là cấp cao nhất và bắt buộc
        // Để đơn giản, giả sử luôn chọn tỉnh trước
    }

    // Nếu là '3-xa' (mặc định) hoặc không có thống kê đặc biệt, trả về dữ liệu xã đã lọc
    // Thêm STT cho dữ liệu cuối cùng
    return data.map((item, index) => ({ ...item, stt: index + 1 }));
});

// Hàm để tạo key duy nhất cho mỗi dòng trong bảng
const getKey = (item, index) => {
  return `${item.ma_tinh}-${item.ma_huyen}-${item.ma_xa || 'all'}-${item.ky_dieu_tra}-${item.nam}-${index}`;
};

// --- Methods (Hàm xử lý) ---

const mockApiCall = (data, delay = 100) => {
    return new Promise(resolve => setTimeout(() => resolve(data), delay));
};

async function fetchDistricts() {
    filters.value.maHuyen = ''; // Reset huyện khi tỉnh thay đổi
    districts.value = [];
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
        // Tự động chọn huyện đầu tiên nếu có
        if (districts.value.length > 0) {
            filters.value.maHuyen = districts.value[0].ma;
        }
    }
    applyFilters(); // Áp dụng bộ lọc và cập nhật bảng
}

async function fetchCommunes() {
    // Trong trang này, chúng ta không cần dropdown xã riêng biệt,
    // nhưng hàm này có thể được dùng để kích hoạt applyFilters
    // hoặc tải dữ liệu xã chi tiết nếu cần.
    applyFilters(); // Áp dụng bộ lọc và cập nhật bảng
}

async function fetchInvestigationData() {
    console.log("Đang lấy dữ liệu điều tra với các bộ lọc:", filters.value);

    // Dữ liệu mẫu mô phỏng từ ảnh, bao gồm cả các cấp và năm/kỳ khác nhau
    const dummyData = [
        // Dữ liệu Đồng Tháp - Hồng Ngự - các xã
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29956', ten: 'Xã Thường Phước 1', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 0, ho_hoan_thanh: 0, ho_ty_le: '0.00' },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29962', ten: 'Xã Thường Thới Hậu A', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 18, ho_hoan_thanh: 18, ho_ty_le: '100.00' },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29967', ten: 'Xã Thường Phước 2', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 43, ho_hoan_thanh: 43, ho_ty_le: '100.00' },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29971', ten: 'Thị trấn Thường Thới Tiền', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 34, ho_hoan_thanh: 34, ho_ty_le: '100.00' },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29974', ten: 'Xã Thường Lạc', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 14, ho_hoan_thanh: 14, ho_ty_le: '100.00' },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29977', ten: 'Xã Long Thuận', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 26, ho_hoan_thanh: 26, ho_ty_le: '100.00' },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ten: 'Xã Long Khánh A', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 2, ho_hoan_thanh: 2, ho_ty_le: '100.00' },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29983', ten: 'Xã Long Khánh B', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 4, ho_hoan_thanh: 4, ho_ty_le: '100.00' },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29992', ten: 'Xã Phú Thuận B', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 17, ho_hoan_thanh: 17, ho_ty_le: '100.00' },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29995', ten: 'Xã Phú Thuận A', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 4, ho_hoan_thanh: 4, ho_ty_le: '100.00' },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29998', ten: 'Xã Phú Thuận', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 22, ho_hoan_thanh: 22, ho_ty_le: '100.00' },
        // Dữ liệu Đồng Tháp - Tam Nông - các xã
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '871', ma_xa: '30000', ten: 'Thị trấn Tràm Chim', dn_htx_so_luong: 5, dn_htx_hoan_thanh: 3, dn_htx_ty_le: '60.00', ho_so_luong: 50, ho_hoan_thanh: 45, ho_ty_le: '90.00' },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '871', ma_xa: '30001', ten: 'Xã Phú Cường', dn_htx_so_luong: 2, dn_htx_hoan_thanh: 2, dn_htx_ty_le: '100.00', ho_so_luong: 30, ho_hoan_thanh: 30, ho_ty_le: '100.00' },
        // Dữ liệu Long An - TP Tân An - các xã/phường
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '88', ma_huyen: '880', ma_xa: '30100', ten: 'Phường 1', dn_htx_so_luong: 10, dn_htx_hoan_thanh: 8, dn_htx_ty_le: '80.00', ho_so_luong: 120, ho_hoan_thanh: 100, ho_ty_le: '83.33' },
        // Dữ liệu kỳ 3 năm 2024
        { ky_dieu_tra: '3', nam: '2024', ma_tinh: '87', ma_huyen: '870', ma_xa: '29962', ten: 'Xã Thường Thới Hậu A (Kỳ 3, 2024)', dn_htx_so_luong: 0, dn_htx_hoan_thanh: 0, dn_htx_ty_le: '0.00', ho_so_luong: 15, ho_hoan_thanh: 10, ho_ty_le: '66.67' },
        { ky_dieu_tra: '3', nam: '2024', ma_tinh: '88', ma_huyen: '880', ma_xa: '30100', ten: 'Phường 1 (Kỳ 3, 2024)', dn_htx_so_luong: 8, dn_htx_hoan_thanh: 5, dn_htx_ty_le: '62.50', ho_so_luong: 100, ho_hoan_thanh: 80, ho_ty_le: '80.00' },
    ];

    allInvestigationData.value = await mockApiCall(dummyData);
}

function applyFilters() {
    // Hàm này được gọi khi nút "Xem" được nhấn hoặc khi các bộ lọc cấp trên thay đổi.
    // Dữ liệu sẽ tự động được lọc thông qua computed property `filteredInvestigationData`.
    console.log("Áp dụng bộ lọc:", filters.value);
}

function exportExcel() {
    alert('Chức năng Xuất Excel (cần implement logic)');
    // Ở đây, bạn sẽ sử dụng thư viện như SheetJS (xlsx) để xuất `filteredInvestigationData` ra file Excel.
    // Ví dụ:
    /*
    import * as XLSX from 'xlsx';
    const ws = XLSX.utils.json_to_sheet(filteredInvestigationData.value);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Sheet1");
    XLSX.writeFile(wb, "ThongTinDieuTra.xlsx");
    */
}

function showUninvestigatedList(item) {
    alert(`Nhấn đúp vào dòng: Hiển thị danh sách hộ/doanh nghiệp chưa điều tra cho ${item.ten}. (Cần implement logic điều hướng/modal)`);
    // Bạn có thể mở một modal, hoặc điều hướng đến một trang chi tiết khác,
    // truyền các tham số như item.ma_tinh, item.ma_huyen, item.ma_xa để lấy dữ liệu.
}

// --- Lifecycle Hooks and Watchers ---
onMounted(() => {
    // Đặt giá trị mặc định cho tỉnh để kích hoạt tải huyện ban đầu
    filters.value.maTinh = provinces.value.length > 0 ? provinces.value[0].ma : '';
    // Fetch dữ liệu điều tra ban đầu
    fetchInvestigationData();
});

// Watchers
// Theo dõi thay đổi của tỉnh để cập nhật danh sách huyện và áp dụng bộ lọc
watch(() => filters.value.maTinh, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        fetchDistricts();
    }
}, { immediate: true }); // Chạy ngay khi component mount

// Theo dõi thay đổi của huyện để áp dụng bộ lọc (và nếu cần, có thể tải xã nếu có dropdown xã)
watch(() => filters.value.maHuyen, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        applyFilters();
    }
});

// Theo dõi thay đổi của "Thống kê theo" để cập nhật bảng
watch(() => filters.value.thongKeTheo, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        applyFilters();
    }
});

// Theo dõi thay đổi của Kỳ điều tra và Năm để cập nhật bảng
watch([() => filters.value.kyDieuTra, () => filters.value.nam], () => {
    fetchInvestigationData(); // Gọi lại API để lấy dữ liệu mới cho kỳ/năm
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
    cursor: pointer;
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

/* Custom styles for select elements */
.form-select:disabled {
    background-color: #e9ecef;
    opacity: 0.7;
}

/* Responsive adjustments */
@media (max-width: 767.98px) {
    .d-flex.flex-wrap .btn {
        flex: 1 1 auto;
        margin-right: 0 !important;
        margin-bottom: 0.5rem !important;
    }
    .d-flex.flex-wrap .btn:last-child {
        margin-bottom: 0 !important;
    }
    .col-md-2, .col-md-3, .col-lg-2, .col-sm-6, .col-md-auto {
        width: 100%;
        margin-bottom: 1rem; /* Thêm khoảng cách dưới cho mỗi bộ lọc */
    }
    .card .row.g-3 > div:last-child {
        margin-bottom: 0; /* Loại bỏ margin dưới cùng cho nút "Xem" */
    }
}
</style>