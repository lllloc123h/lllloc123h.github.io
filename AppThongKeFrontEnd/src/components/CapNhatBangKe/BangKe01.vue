<template>
    <div class="container">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Bảng kê hộ quy mô nhỏ</h2>

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
                            <label for="xaPhuong" class="form-label">Xã/Phường</label>
                            <select v-model="filters.maXa" class="form-select" id="xaPhuong" @change="fetchThons" :disabled="!communes.length">
                                <option value="">--Chọn Xã/Phường--</option>
                                <option v-for="xa in communes" :key="xa.ma" :value="xa.ma">{{ xa.ma }} - {{ xa.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-2 col-sm-6">
                            <label for="thon" class="form-label">Thôn</label>
                            <select v-model="filters.maThon" class="form-select" id="thon" @change="fetchDiaBans" :disabled="!thons.length">
                                <option value="">--Chọn Thôn--</option>
                                <option v-for="thon in thons" :key="thon.ma" :value="thon.ma">{{ thon.ma }} - {{ thon.ten }}</option>
                            </select>
                        </div>
                        <div class="col-md-3 col-lg-2 col-sm-6">
                            <label for="diaBan" class="form-label">Địa bàn</label>
                            <select v-model="filters.maDiaBan" class="form-select" id="diaBan" :disabled="!diaBans.length">
                                <option value="">--Chọn Địa bàn--</option>
                                <option v-for="db in diaBans" :key="db.ma" :value="db.ma">{{ db.ma }} - {{ db.ten }}</option>
                            </select>
                        </div>

                        <div class="col-md-6 col-lg-4 d-flex align-items-end justify-content-end">
                            <div class="input-group flex-grow-1 me-2">
                                <span class="input-group-text">Tổng số hộ thôn/địa bàn:</span>
                                <input type="text" class="form-control" :value="totalHoInCurrentDiaBan" readonly>
                            </div>
                            <button class="btn btn-secondary flex-shrink-0" @click="updateTotalHo">
                                <i class="bi bi-arrow-clockwise"></i> Cập nhật
                            </button>
                        </div>
                    </div>
                </div>

                <div class="d-flex justify-content-end mb-4 flex-wrap">
                    <button class="btn btn-success me-2 mb-2" @click="exportExcelDiaBan">
                        <i class="bi bi-file-earmark-excel"></i> Xuất Excel theo địa bàn
                    </button>
                    <button class="btn btn-success me-2 mb-2" @click="exportExcelHuyen">
                        <i class="bi bi-file-earmark-excel"></i> Xuất Excel theo Huyện
                    </button>
                    <button class="btn btn-success mb-2" @click="exportExcelTinh">
                        <i class="bi bi-file-earmark-excel"></i> Xuất Excel theo Tỉnh
                    </button>
                </div>

                <div class="table-responsive">
                    <table class="table table-bordered table-hover caption-top">
                        <caption>Tổng số hộ đang hiển thị: {{ filteredHouseholds.length }}</caption>
                        <thead class="table-primary">
                            <tr>
                                <th scope="col">Mã Tỉnh</th>
                                <th scope="col">Mã Xã</th>
                                <th scope="col">Mã Thôn</th>
                                <th scope="col">Địa bàn</th>
                                <th scope="col">STT Hộ</th>
                                <th scope="col" style="min-width: 180px;">Họ và tên chủ hộ</th>
                                <th scope="col" class="text-center">Lợn</th>
                                <th scope="col" class="text-center">Gà</th>
                                <th scope="col" class="text-center">Vịt</th>
                                <th scope="col" class="text-center">Ngang</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(household, index) in filteredHouseholds" :key="household.stt_ho">
                                <td>{{ household.ma_tinh }}</td>
                                <td>{{ household.ma_xa }}</td>
                                <td>{{ household.ma_thon }}</td>
                                <td>{{ household.dia_ban }}</td>
                                <td>{{ household.stt_ho }}</td>
                                <td>{{ household.ho_ten_chu_ho }}</td>
                                <td class="text-center">{{ getAnimalScale(household.loi_thong_tin, 'lon') }}</td>
                                <td class="text-center">{{ getAnimalScale(household.loi_thong_tin, 'ga') }}</td>
                                <td class="text-center">{{ getAnimalScale(household.loi_thong_tin, 'vit') }}</td>
                                <td class="text-center">{{ getAnimalScale(household.loi_thong_tin, 'ngang') }}</td>
                            </tr>
                            <tr v-if="filteredHouseholds.length === 0">
                                <td colspan="10" class="text-center">Không tìm thấy dữ liệu hộ quy mô nhỏ nào.</td>
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

// --- State Variables (Biến trạng thái) ---
const filters = ref({
    kyDieuTra: '4',
    nam: '2025',
    maTinh: '87',
    maHuyen: '870',
    maXa: '29980',
    maThon: '05', // Thôn Long Phước
    maDiaBan: '011-long hau 4' // Địa bàn 011-long hau 4
});

// Dữ liệu tĩnh cho các dropdown (trong thực tế sẽ lấy từ API)
const provinces = ref([
    { ma: '87', ten: 'Đồng Tháp' },
    { ma: '88', ten: 'Long An' },
    { ma: '89', ten: 'An Giang' },
]);
const districts = ref([]); // Sẽ được fetch động
const communes = ref([]); // Sẽ được fetch động
const thons = ref([]);    // Sẽ được fetch động
const diaBans = ref([]);  // Sẽ được fetch động

// Dữ liệu mẫu cho các hộ gia đình
const allHouseholds = ref([]);

// --- Computed Properties ---
const totalHoInCurrentDiaBan = computed(() => {
    // Tính tổng số hộ trong địa bàn/thôn hiện tại dựa trên bộ lọc
    // (Trong thực tế, bạn có thể cần một API endpoint để lấy tổng số hộ này nhanh hơn)
    return allHouseholds.value.filter(h =>
        h.ma_tinh === filters.value.maTinh &&
        h.ma_huyen === filters.value.maHuyen &&
        h.ma_xa === filters.value.maXa &&
        h.ma_thon === filters.value.maThon &&
        h.dia_ban_ma === filters.value.maDiaBan // Sử dụng mã địa bàn để lọc
    ).length;
});

const filteredHouseholds = computed(() => {
    let filtered = allHouseholds.value;

    // Áp dụng các bộ lọc
    if (filters.value.kyDieuTra) {
        filtered = filtered.filter(h => h.ky_dieu_tra === filters.value.kyDieuTra);
    }
    if (filters.value.nam) {
        filtered = filtered.filter(h => h.nam === filters.value.nam);
    }
    if (filters.value.maTinh) {
        filtered = filtered.filter(h => h.ma_tinh === filters.value.maTinh);
    }
    if (filters.value.maHuyen) {
        filtered = filtered.filter(h => h.ma_huyen === filters.value.maHuyen);
    }
    if (filters.value.maXa) {
        filtered = filtered.filter(h => h.ma_xa === filters.value.maXa);
    }
    if (filters.value.maThon) {
        filtered = filtered.filter(h => h.ma_thon === filters.value.maThon);
    }
    if (filters.value.maDiaBan) {
        filtered = filtered.filter(h => h.dia_ban_ma === filters.value.maDiaBan);
    }

    return filtered;
});

// --- Methods (Hàm xử lý) ---

// Helper function to get the correct animal scale string
function getAnimalScale(loiThongTin, animalType) {
    const scales = [
        { key: `${animalType}_1_9`, label: '1-9' },
        { key: `${animalType}_10_29`, label: '10-29' },
        { key: `${animalType}_50_199`, label: '50-199' },
        { key: `${animalType}_200_499`, label: '200-499' },
        { key: `${animalType}_500_plus`, label: '>=500' },
    ];

    for (const scale of scales) {
        if (loiThongTin[scale.key]) {
            return scale.label;
        }
    }
    return ''; // Return empty string if no scale applies
}


// Giả lập việc lấy dữ liệu từ API
const mockApiCall = (data, delay = 300) => {
    return new Promise(resolve => setTimeout(() => resolve(data), delay));
};

async function fetchDistricts() {
    filters.value.maHuyen = ''; // Reset huyện khi tỉnh thay đổi
    filters.value.maXa = '';
    filters.value.maThon = '';
    filters.value.maDiaBan = '';
    communes.value = [];
    thons.value = [];
    diaBans.value = [];
    if (filters.value.maTinh) {
        // Trong thực tế: const response = await axios.get(`/api/districts?province=${filters.value.maTinh}`);
        const data = await mockApiCall([
            { ma: '870', ten: 'Huyện Hồng Ngự', ma_tinh: '87' },
            { ma: '871', ten: 'Huyện Tam Nông', ma_tinh: '87' },
            { ma: '880', ten: 'TP Tân An', ma_tinh: '88' },
        ].filter(d => d.ma_tinh === filters.value.maTinh));
        districts.value = data;
        // Tự động chọn huyện đầu tiên nếu có
        if (districts.value.length > 0) {
            filters.value.maHuyen = districts.value[0].ma;
            fetchCommunes();
        }
    } else {
        districts.value = [];
    }
}

async function fetchCommunes() {
    filters.value.maXa = ''; // Reset xã khi huyện thay đổi
    filters.value.maThon = '';
    filters.value.maDiaBan = '';
    thons.value = [];
    diaBans.value = [];
    if (filters.value.maHuyen) {
        const data = await mockApiCall([
            { ma: '29980', ten: 'Xã Long Khánh A', ma_huyen: '870' },
            { ma: '29981', ten: 'Xã Long Khánh B', ma_huyen: '870' },
            { ma: '30000', ten: 'Thị trấn Tràm Chim', ma_huyen: '871' },
            { ma: '30001', ten: 'Xã Phú Cường', ma_huyen: '871' },
            { ma: '30100', ten: 'Phường 1', ma_huyen: '880' },
        ].filter(c => c.ma_huyen === filters.value.maHuyen));
        communes.value = data;
        if (communes.value.length > 0) {
            filters.value.maXa = communes.value[0].ma;
            fetchThons();
        }
    } else {
        communes.value = [];
    }
}

async function fetchThons() {
    filters.value.maThon = ''; // Reset thôn khi xã thay đổi
    filters.value.maDiaBan = '';
    diaBans.value = [];
    if (filters.value.maXa) {
        const data = await mockApiCall([
            { ma: '05', ten: 'Long Phước', ma_xa: '29980' },
            { ma: '06', ten: 'Long Thạnh', ma_xa: '29980' },
            { ma: '01', ten: 'Ấp 1', ma_xa: '29981' },
            { ma: '02', ten: 'Ấp 2', ma_xa: '29981' },
        ].filter(t => t.ma_xa === filters.value.maXa));
        thons.value = data;
        if (thons.value.length > 0) {
            filters.value.maThon = thons.value[0].ma;
            fetchDiaBans();
        }
    } else {
        thons.value = [];
    }
}

async function fetchDiaBans() {
    filters.value.maDiaBan = ''; // Reset địa bàn khi thôn thay đổi
    if (filters.value.maThon) {
        const data = await mockApiCall([
            { ma: '011-long hau 4', ten: '011-long hau 4', ma_thon: '05' },
            { ma: '012-long hau 5', ten: '012-long hau 5', ma_thon: '05' },
            { ma: '013-long thanh', ten: '013-long thanh', ma_thon: '06' },
        ].filter(db => db.ma_thon === filters.value.maThon));
        diaBans.value = data;
        if (diaBans.value.length > 0) {
            filters.value.maDiaBan = diaBans.value[0].ma;
        }
    } else {
        diaBans.value = [];
    }
}

async function fetchHouseholds() {
    // Trong thực tế, gọi API với tất cả các filters để lấy dữ liệu hộ
    console.log("Đang lấy dữ liệu hộ với các bộ lọc:", filters.value);

    // Dữ liệu mẫu đa dạng hơn
    const dummyHouseholds = [
        // Hộ ở Đồng Tháp, Hồng Ngự, Long Khánh A, Thôn Long Phước, Địa bàn 011-long hau 4
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: '011-long hau 4', dia_ban_ma: '011-long hau 4', stt_ho: '0001', ho_ten_chu_ho: 'Nguyễn Thị Mèn',
            loi_thong_tin: { lon_1_9: true, lon_10_29: false, lon_50_199: false, lon_200_499: false, lon_500_plus: false,
                             ga_1_9: false, ga_10_29: true, ga_50_199: false, ga_200_499: false, ga_500_plus: false,
                             vit_1_9: false, vit_10_29: false, vit_50_199: false, vit_200_499: false, vit_500_plus: false,
                             ngang_1_9: false, ngang_10_29: false, ngang_50_199: false, ngang_200_499: false, ngang_500_plus: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: '011-long hau 4', dia_ban_ma: '011-long hau 4', stt_ho: '0002', ho_ten_chu_ho: 'Hồ Thị Nhảnh',
            loi_thong_tin: { lon_1_9: false, lon_10_29: true, lon_50_199: false, lon_200_499: false, lon_500_plus: false,
                             ga_1_9: true, ga_10_29: false, ga_50_199: false, ga_200_499: false, ga_500_plus: false,
                             vit_1_9: false, vit_10_29: false, vit_50_199: false, vit_200_499: false, vit_500_plus: false,
                             ngang_1_9: false, ngang_10_29: false, ngang_50_199: false, ngang_200_499: false, ngang_500_plus: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: '011-long hau 4', dia_ban_ma: '011-long hau 4', stt_ho: '0003', ho_ten_chu_ho: 'Nguyễn Văn Tuấn',
            loi_thong_tin: { lon_1_9: true, lon_10_29: false, lon_50_199: false, lon_200_499: false, lon_500_plus: false,
                             ga_1_9: false, ga_10_29: false, ga_50_199: false, ga_200_499: false, ga_500_plus: false,
                             vit_1_9: false, vit_10_29: true, vit_50_199: false, vit_200_499: false, vit_500_plus: false,
                             ngang_1_9: false, ngang_10_29: false, ngang_50_199: false, ngang_200_499: false, ngang_500_plus: false }
        },
        // Thêm các hộ khác với dữ liệu đa dạng hơn
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: '011-long hau 4', dia_ban_ma: '011-long hau 4', stt_ho: '0004', ho_ten_chu_ho: 'Nguyễn Văn Đông',
            loi_thong_tin: { lon_1_9: true, lon_10_29: false, lon_50_199: false, lon_200_499: false, lon_500_plus: false,
                             ga_1_9: false, ga_10_29: true, ga_50_199: false, ga_200_499: false, ga_500_plus: false,
                             vit_1_9: false, vit_10_29: false, vit_50_199: false, vit_200_499: false, vit_500_plus: false,
                             ngang_1_9: false, ngang_10_29: false, ngang_50_199: false, ngang_200_499: false, ngang_500_plus: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: '011-long hau 4', dia_ban_ma: '011-long hau 4', stt_ho: '0005', ho_ten_chu_ho: 'Hồ Văn Hiển',
            loi_thong_tin: { lon_1_9: false, lon_10_29: false, lon_50_199: true, lon_200_499: false, lon_500_plus: false,
                             ga_1_9: false, ga_10_29: false, ga_50_199: false, ga_200_499: false, ga_500_plus: false,
                             vit_1_9: false, vit_10_29: false, vit_50_199: false, vit_200_499: false, vit_500_plus: false,
                             ngang_1_9: false, ngang_10_29: false, ngang_50_199: false, ngang_200_499: false, ngang_500_plus: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: '011-long hau 4', dia_ban_ma: '011-long hau 4', stt_ho: '0006', ho_ten_chu_ho: 'Bùi Thị Dõi',
            loi_thong_tin: { lon_1_9: false, lon_10_29: false, lon_50_199: false, lon_200_499: false, lon_500_plus: false,
                             ga_1_9: false, ga_10_29: false, ga_50_199: true, ga_200_499: false, ga_500_plus: false,
                             vit_1_9: false, vit_10_29: false, vit_50_199: false, vit_200_499: false, vit_500_plus: false,
                             ngang_1_9: false, ngang_10_29: false, ngang_50_199: false, ngang_200_499: false, ngang_500_plus: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: '011-long hau 4', dia_ban_ma: '011-long hau 4', stt_ho: '0007', ho_ten_chu_ho: 'Phạm Văn Bồng',
            loi_thong_tin: { lon_1_9: false, lon_10_29: false, lon_50_199: false, lon_200_499: false, lon_500_plus: false,
                             ga_1_9: false, ga_10_29: false, ga_50_199: false, ga_200_499: true, ga_500_plus: false,
                             vit_1_9: false, vit_10_29: false, vit_50_199: false, vit_200_499: false, vit_500_plus: false,
                             ngang_1_9: false, ngang_10_29: false, ngang_50_199: false, ngang_200_499: false, ngang_500_plus: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: '011-long hau 4', dia_ban_ma: '011-long hau 4', stt_ho: '0008', ho_ten_chu_ho: 'Nguyễn Văn Triệu',
            loi_thong_tin: { lon_1_9: false, lon_10_29: false, lon_50_199: false, lon_200_499: false, lon_500_plus: true,
                             ga_1_9: false, ga_10_29: false, ga_50_199: false, ga_200_499: false, ga_500_plus: false,
                             vit_1_9: false, vit_10_29: false, vit_50_199: false, vit_200_499: false, vit_500_plus: false,
                             ngang_1_9: false, ngang_10_29: false, ngang_50_199: false, ngang_200_499: false, ngang_500_plus: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: '011-long hau 4', dia_ban_ma: '011-long hau 4', stt_ho: '0009', ho_ten_chu_ho: 'Nguyễn Văn Lâm',
            loi_thong_tin: { lon_1_9: false, lon_10_29: false, lon_50_199: false, lon_200_499: false, lon_500_plus: false,
                             ga_1_9: true, ga_10_29: false, ga_50_199: false, ga_200_499: false, ga_500_plus: false,
                             vit_1_9: false, vit_10_29: false, vit_50_199: false, vit_200_499: false, vit_500_plus: false,
                             ngang_1_9: false, ngang_10_29: false, ngang_50_199: false, ngang_200_499: false, ngang_500_plus: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: '011-long hau 4', dia_ban_ma: '011-long hau 4', stt_ho: '0010', ho_ten_chu_ho: 'Lê Văn Phương',
            loi_thong_tin: { lon_1_9: false, lon_10_29: false, lon_50_199: false, lon_200_499: false, lon_500_plus: false,
                             ga_1_9: false, ga_10_29: true, ga_50_199: false, ga_200_499: false, ga_500_plus: false,
                             vit_1_9: false, vit_10_29: false, vit_50_199: false, vit_200_499: false, vit_500_plus: false,
                             ngang_1_9: false, ngang_10_29: false, ngang_50_199: false, ngang_200_499: false, ngang_500_plus: false }
        },
        // Thêm 50 hộ khác với dữ liệu ngẫu nhiên để tăng sự đa dạng
        ...Array.from({ length: 50 }, (_, i) => {
            const stt = String(i + 11).padStart(4, '0');
            const hasLon = Math.random() < 0.5;
            const hasGa = Math.random() < 0.5;
            const hasVit = Math.random() < 0.5;
            const hasNgang = Math.random() < 0.5;

            const getLonScale = () => {
                if (!hasLon) return { lon_1_9: false, lon_10_29: false, lon_50_199: false, lon_200_499: false, lon_500_plus: false };
                const rand = Math.random();
                if (rand < 0.2) return { lon_1_9: true };
                if (rand < 0.4) return { lon_10_29: true };
                if (rand < 0.6) return { lon_50_199: true };
                if (rand < 0.8) return { lon_200_499: true };
                return { lon_500_plus: true };
            };
            const getGaScale = () => {
                 if (!hasGa) return { ga_1_9: false, ga_10_29: false, ga_50_199: false, ga_200_499: false, ga_500_plus: false };
                const rand = Math.random();
                if (rand < 0.2) return { ga_1_9: true };
                if (rand < 0.4) return { ga_10_29: true };
                if (rand < 0.6) return { ga_50_199: true };
                if (rand < 0.8) return { ga_200_499: true };
                return { ga_500_plus: true };
            };
            const getVitScale = () => {
                 if (!hasVit) return { vit_1_9: false, vit_10_29: false, vit_50_199: false, vit_200_499: false, vit_500_plus: false };
                const rand = Math.random();
                if (rand < 0.2) return { vit_1_9: true };
                if (rand < 0.4) return { vit_10_29: true };
                if (rand < 0.6) return { vit_50_199: true };
                if (rand < 0.8) return { vit_200_499: true };
                return { vit_500_plus: true };
            };
             const getNgangScale = () => {
                 if (!hasNgang) return { ngang_1_9: false, ngang_10_29: false, ngang_50_199: false, ngang_200_499: false, ngang_500_plus: false };
                const rand = Math.random();
                if (rand < 0.2) return { ngang_1_9: true };
                if (rand < 0.4) return { ngang_10_29: true };
                if (rand < 0.6) return { ngang_50_199: true };
                if (rand < 0.8) return { ngang_200_499: true };
                return { ngang_500_plus: true };
            };

            return {
                ky_dieu_tra: '4',
                nam: '2025',
                ma_tinh: '87',
                ma_huyen: '870',
                ma_xa: '29980',
                ma_thon: '05',
                dia_ban: '011-long hau 4',
                dia_ban_ma: '011-long hau 4',
                stt_ho: stt,
                ho_ten_chu_ho: `Chủ Hộ Test ${stt}`,
                loi_thong_tin: {
                    ...getLonScale(),
                    ...getGaScale(),
                    ...getVitScale(),
                    ...getNgangScale()
                }
            };
        })
    ];

    allHouseholds.value = dummyHouseholds;
}

function updateTotalHo() {
    alert('Đang cập nhật tổng số hộ địa bàn. (Thực tế sẽ gọi API)');
    // Trong thực tế, có thể làm mới dữ liệu hoặc chỉ cập nhật lại số liệu.
}

function exportExcelDiaBan() {
    alert('Xuất Excel theo Địa bàn đã chọn.');
    // Logic xuất Excel
}

function exportExcelHuyen() {
    alert('Xuất Excel theo Huyện đã chọn.');
    // Logic xuất Excel
}

function exportExcelTinh() {
    alert('Xuất Excel theo Tỉnh đã chọn.');
    // Logic xuất Excel
}

// --- Lifecycle Hooks and Watchers ---
onMounted(() => {
    // Khi component được mount, fetch dữ liệu ban đầu
    fetchDistricts(); // Lấy danh sách huyện khi tỉnh được chọn
    fetchHouseholds(); // Lấy danh sách hộ
});

// Watchers để fetch dữ liệu hộ khi bộ lọc thay đổi
watch(() => filters.value.maDiaBan, () => {
    fetchHouseholds();
});
watch(() => filters.value.maThon, () => {
    // Có thể fetch hộ hoặc chỉ lọc lại nếu dữ liệu đã có sẵn
    if (!filters.value.maDiaBan) { // Nếu địa bàn chưa được chọn, có thể fetch hoặc lọc
         fetchHouseholds();
    }
});
// Thêm các watcher khác nếu muốn dữ liệu được fetch lại khi các bộ lọc khác thay đổi ngay lập tức
// watch(() => filters.value.nam, () => fetchHouseholds());
// watch(() => filters.value.maTinh, () => fetchHouseholds());
// ...

</script>

<style scoped>
.container-fluid {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f8f9fa;
    padding-bottom: 2rem;
}

.table-responsive {
    overflow-x: auto; /* Cho phép cuộn ngang nếu bảng quá rộng */
}

.table thead th {
    vertical-align: middle; /* Căn giữa theo chiều dọc cho tiêu đề bảng */
    white-space: nowrap; /* Ngăn tiêu đề bị xuống dòng */
}

.table-bordered th, .table-bordered td {
    border: 1px solid #dee2e6;
}

.table-hover tbody tr:hover {
    background-color: #e2e6ea;
    cursor: pointer;
}

/* Tùy chỉnh nhỏ để các nút xuất excel dễ nhìn hơn trên mobile */
@media (max-width: 767.98px) {
    .d-flex.flex-wrap .btn {
        flex: 1 1 auto; /* Các nút sẽ co giãn để lấp đầy không gian */
        margin-right: 0 !important; /* Xóa margin giữa các nút */
        margin-bottom: 0.5rem !important; /* Thêm margin dưới cho mỗi nút */
    }
    .d-flex.flex-wrap .btn:last-child {
        margin-bottom: 0 !important;
    }
}
</style>