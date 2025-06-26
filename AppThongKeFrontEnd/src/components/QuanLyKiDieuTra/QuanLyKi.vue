<template>
    <div class="container-fluid">
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
                            <tr v-for="(household, index) in filteredHouseholds" :key="household.stt_ho + household.ma_dia_ban + household.ma_thon">
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
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css'; // Để sử dụng icon Bootstrap

// --- State Variables (Biến trạng thái) ---
const filters = ref({
    kyDieuTra: '4',
    nam: '2025',
    maTinh: '', // Khởi tạo rỗng để người dùng chọn
    maHuyen: '',
    maXa: '',
    maThon: '',
    maDiaBan: ''
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

// Dữ liệu mẫu cho các hộ gia đình (sẽ được tải lên hoặc fetch)
const allHouseholds = ref([]);

// --- Computed Properties ---
const totalHoInCurrentDiaBan = computed(() => {
    // Tính tổng số hộ trong địa bàn/thôn hiện tại dựa trên bộ lọc
    return allHouseholds.value.filter(h =>
        (!filters.value.maTinh || h.ma_tinh === filters.value.maTinh) &&
        (!filters.value.maHuyen || h.ma_huyen === filters.value.maHuyen) &&
        (!filters.value.maXa || h.ma_xa === filters.value.maXa) &&
        (!filters.value.maThon || h.ma_thon === filters.value.maThon) &&
        (!filters.value.maDiaBan || h.dia_ban_ma === filters.value.maDiaBan)
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
    if (!loiThongTin) return ''; // Đảm bảo loiThongTin không null/undefined

    const scales = [
        { key: `${animalType}_1_9`, label: '1-9' },
        { key: `${animalType}_10_29`, label: '10-29' },
        { key: `${animalType}_30_49`, label: '30-49' }, // Bổ sung theo quy mô nhỏ thường gặp
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
const mockApiCall = (data, delay = 100) => {
    return new Promise(resolve => setTimeout(() => resolve(data), delay));
};

// Hàm fetch danh sách huyện
async function fetchDistricts() {
    filters.value.maHuyen = ''; // Reset huyện khi tỉnh thay đổi
    filters.value.maXa = '';
    filters.value.maThon = '';
    filters.value.maDiaBan = '';
    districts.value = [];
    communes.value = [];
    thons.value = [];
    diaBans.value = [];

    if (filters.value.maTinh) {
        // Mock data cho huyện dựa trên maTinh
        const mockDistrictsData = [
            { ma: '870', ten: 'Hồng Ngự', ma_tinh: '87' },
            { ma: '871', ten: 'Tam Nông', ma_tinh: '87' },
            { ma: '880', ten: 'TP Tân An', ma_tinh: '88' },
            { ma: '881', ten: 'Thủ Thừa', ma_tinh: '88' },
            { ma: '890', ten: 'TP Long Xuyên', ma_tinh: '89' },
            { ma: '891', ten: 'Châu Đốc', ma_tinh: '89' },
        ];
        const data = await mockApiCall(mockDistrictsData.filter(d => d.ma_tinh === filters.value.maTinh));
        districts.value = data;
        // Tự động chọn huyện đầu tiên nếu có và chưa có giá trị được chọn
        if (districts.value.length > 0 && !filters.value.maHuyen) {
            filters.value.maHuyen = districts.value[0].ma;
            fetchCommunes();
        }
    }
}

// Hàm fetch danh sách xã/phường
async function fetchCommunes() {
    filters.value.maXa = ''; // Reset xã khi huyện thay đổi
    filters.value.maThon = '';
    filters.value.maDiaBan = '';
    communes.value = [];
    thons.value = [];
    diaBans.value = [];

    if (filters.value.maHuyen) {
        // Mock data cho xã dựa trên maHuyen
        const mockCommunesData = [
            { ma: '29980', ten: 'Long Khánh A', ma_huyen: '870' }, // Hồng Ngự
            { ma: '29981', ten: 'Long Khánh B', ma_huyen: '870' }, // Hồng Ngự
            { ma: '30000', ten: 'Thị trấn Tràm Chim', ma_huyen: '871' }, // Tam Nông
            { ma: '30100', ten: 'Phường 1', ma_huyen: '880' }, // TP Tân An
        ];
        const data = await mockApiCall(mockCommunesData.filter(c => c.ma_huyen === filters.value.maHuyen));
        communes.value = data;
        if (communes.value.length > 0 && !filters.value.maXa) {
            filters.value.maXa = communes.value[0].ma;
            fetchThons();
        }
    }
}

// Hàm fetch danh sách thôn
async function fetchThons() {
    filters.value.maThon = ''; // Reset thôn khi xã thay đổi
    filters.value.maDiaBan = '';
    thons.value = [];
    diaBans.value = [];

    if (filters.value.maXa) {
        // Mock data cho thôn dựa trên maXa
        const mockThonsData = [
            { ma: '05', ten: 'Long Phước', ma_xa: '29980' },
            { ma: '06', ten: 'Long Thạnh', ma_xa: '29980' },
            { ma: '01', ten: 'Ấp An Hòa', ma_xa: '29981' },
            { ma: '02', ten: 'Ấp An Long', ma_xa: '29981' },
            { ma: '03', ten: 'Thôn Chợ Mới', ma_xa: '30000' },
        ];
        const data = await mockApiCall(mockThonsData.filter(t => t.ma_xa === filters.value.maXa));
        thons.value = data;
        if (thons.value.length > 0 && !filters.value.maThon) {
            filters.value.maThon = thons.value[0].ma;
            fetchDiaBans();
        }
    }
}

// Hàm fetch danh sách địa bàn
async function fetchDiaBans() {
    filters.value.maDiaBan = ''; // Reset địa bàn khi thôn thay đổi
    diaBans.value = [];

    if (filters.value.maThon) {
        // Mock data cho địa bàn dựa trên maThon
        const mockDiaBansData = [
            { ma: '011-long hau 4', ten: 'Địa bàn 011', ma_thon: '05' },
            { ma: '012-long hau 5', ten: 'Địa bàn 012', ma_thon: '05' },
            { ma: '013-long thanh', ten: 'Địa bàn 013', ma_thon: '06' },
            { ma: '001-an hoa', ten: 'Địa bàn 001', ma_thon: '01' },
        ];
        const data = await mockApiCall(mockDiaBansData.filter(db => db.ma_thon === filters.value.maThon));
        diaBans.value = data;
        if (diaBans.value.length > 0 && !filters.value.maDiaBan) {
            filters.value.maDiaBan = diaBans.value[0].ma;
        }
    }
    // Sau khi các bộ lọc cấp dưới đã được cập nhật, gọi fetchHouseholds để cập nhật bảng
    fetchHouseholds();
}

async function fetchHouseholds() {
    console.log("Đang lấy dữ liệu hộ với các bộ lọc:", filters.value);

    // Dữ liệu mẫu đa dạng hơn
    const dummyHouseholds = [
        // Hộ ở Đồng Tháp, Hồng Ngự, Long Khánh A, Thôn Long Phước, Địa bàn 011-long hau 4 (ma_tinh: 87, ma_huyen: 870, ma_xa: 29980, ma_thon: 05, dia_ban_ma: 011-long hau 4)
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: 'Địa bàn 011', dia_ban_ma: '011-long hau 4', stt_ho: '0001', ho_ten_chu_ho: 'Nguyễn Thị Mèn',
            loi_thong_tin: { lon_1_9: true, ga_10_29: true, vit_1_9: false, ngang_1_9: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: 'Địa bàn 011', dia_ban_ma: '011-long hau 4', stt_ho: '0002', ho_ten_chu_ho: 'Hồ Thị Nhảnh',
            loi_thong_tin: { lon_10_29: true, ga_1_9: true, vit_1_9: false, ngang_1_9: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: 'Địa bàn 011', dia_ban_ma: '011-long hau 4', stt_ho: '0003', ho_ten_chu_ho: 'Trần Văn Thi',
            loi_thong_tin: { lon_1_9: true, ga_1_9: false, vit_10_29: true, ngang_1_9: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: 'Địa bàn 011', dia_ban_ma: '011-long hau 4', stt_ho: '0004', ho_ten_chu_ho: 'Lê Văn An',
            loi_thong_tin: { lon_50_199: true, ga_50_199: false, vit_50_199: false, ngang_50_199: false }
        },
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: 'Địa bàn 011', dia_ban_ma: '011-long hau 4', stt_ho: '0005', ho_ten_chu_ho: 'Phạm Thị Lan',
            loi_thong_tin: { lon_200_499: true, ga_200_499: false, vit_200_499: false, ngang_200_499: false }
        },
        // Hộ ở Đồng Tháp, Hồng Ngự, Long Khánh A, Thôn Long Phước, Địa bàn 012-long hau 5
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: 'Địa bàn 012', dia_ban_ma: '012-long hau 5', stt_ho: '0001', ho_ten_chu_ho: 'Võ Văn Long',
            loi_thong_tin: { lon_1_9: true, ga_1_9: true, vit_1_9: true, ngang_1_9: true }
        },
        // Hộ ở Đồng Tháp, Hồng Ngự, Long Khánh B, Thôn Ấp An Hòa, Địa bàn 001-an hoa
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '87', ma_huyen: '870', ma_xa: '29981', ma_thon: '01', dia_ban: 'Địa bàn 001', dia_ban_ma: '001-an hoa', stt_ho: '0001', ho_ten_chu_ho: 'Nguyễn Văn Cảnh',
            loi_thong_tin: { lon_1_9: true, ga_10_29: true, vit_1_9: true, ngang_1_9: true }
        },
        // Hộ ở Long An, TP Tân An, Phường 1 (ma_tinh: 88, ma_huyen: 880, ma_xa: 30100)
        { ky_dieu_tra: '4', nam: '2025', ma_tinh: '88', ma_huyen: '880', ma_xa: '30100', ma_thon: '01', dia_ban: 'Địa bàn 001', dia_ban_ma: '001-p1', stt_ho: '0001', ho_ten_chu_ho: 'Phan Văn Minh',
            loi_thong_tin: { lon_1_9: true, ga_1_9: true, vit_1_9: true, ngang_1_9: true }
        },
        // Thêm dữ liệu cho các năm và kỳ khác
        { ky_dieu_tra: '3', nam: '2024', ma_tinh: '87', ma_huyen: '870', ma_xa: '29980', ma_thon: '05', dia_ban: 'Địa bàn 011', dia_ban_ma: '011-long hau 4', stt_ho: '0001', ho_ten_chu_ho: 'Trần Văn Kỷ',
            loi_thong_tin: { lon_1_9: true, ga_10_29: true, vit_1_9: false, ngang_1_9: false }
        },
        { ky_dieu_tra: '4', nam: '2024', ma_tinh: '89', ma_huyen: '890', ma_xa: '30100', ma_thon: '01', dia_ban: 'Địa bàn 001', dia_ban_ma: '001-lx', stt_ho: '0001', ho_ten_chu_ho: 'Mai Thị Đào',
            loi_thong_tin: { lon_1_9: true, ga_1_9: true, vit_1_9: true, ngang_1_9: true }
        },
         // Thêm 30 hộ khác với dữ liệu ngẫu nhiên để tăng sự đa dạng trong địa bàn 011-long hau 4
         ...Array.from({ length: 30 }, (_, i) => {
            const stt = String(i + 10).padStart(4, '0');
            const hasLon = Math.random() < 0.7;
            const hasGa = Math.random() < 0.7;
            const hasVit = Math.random() < 0.7;
            const hasNgang = Math.random() < 0.7;

            const getRandomScale = (animalType, hasAnimal) => {
                const scalesList = ['1_9', '10_29', '30_49', '50_199', '200_499', '500_plus'];
                const selectedScale = hasAnimal ? scalesList[Math.floor(Math.random() * scalesList.length)] : null;
                const obj = {};
                scalesList.forEach(s => obj[`${animalType}_${s}`] = false); // Set all to false
                if (selectedScale) {
                    obj[`${animalType}_${selectedScale}`] = true; // Set selected to true
                }
                return obj;
            };

            return {
                ky_dieu_tra: '4',
                nam: '2025',
                ma_tinh: '87',
                ma_huyen: '870',
                ma_xa: '29980',
                ma_thon: '05',
                dia_ban: 'Địa bàn 011',
                dia_ban_ma: '011-long hau 4',
                stt_ho: stt,
                ho_ten_chu_ho: `Chủ Hộ Random ${stt}`,
                loi_thong_tin: {
                    ...getRandomScale('lon', hasLon),
                    ...getRandomScale('ga', hasGa),
                    ...getRandomScale('vit', hasVit),
                    ...getRandomScale('ngang', hasNgang)
                }
            };
        })
    ];

    allHouseholds.value = await mockApiCall(dummyHouseholds); // Giả lập lấy dữ liệu từ API
}

function updateTotalHo() {
    alert(`Tổng số hộ trong địa bàn/thôn hiện tại: ${totalHoInCurrentDiaBan.value}. (Tính toán dựa trên dữ liệu hiển thị)`);
    // Trong thực tế, nếu totalHoInCurrentDiaBan được tính từ một API riêng, bạn sẽ gọi API đó ở đây.
}

function exportExcelDiaBan() {
    alert('Xuất Excel theo Địa bàn đã chọn. (Logic xuất Excel cần được implement)');
    // Ví dụ: sử dụng thư viện như SheetJS (xlsx) để xuất dữ liệu filteredHouseholds ra Excel
}

function exportExcelHuyen() {
    alert('Xuất Excel theo Huyện đã chọn. (Logic xuất Excel cần được implement)');
    // Lọc dữ liệu theo Huyện và xuất
}

function exportExcelTinh() {
    alert('Xuất Excel theo Tỉnh đã chọn. (Logic xuất Excel cần được implement)');
    // Lọc dữ liệu theo Tỉnh và xuất
}

// --- Lifecycle Hooks and Watchers ---
onMounted(() => {
    // Khởi tạo giá trị mặc định cho các bộ lọc và fetch dữ liệu
    filters.value.maTinh = provinces.value.length > 0 ? provinces.value[0].ma : '';
    // Các hàm fetch cấp dưới sẽ được gọi qua watchers hoặc khi fetchDistricts được gọi
});

// Watchers để fetch dữ liệu các cấp hành chính khi bộ lọc cấp trên thay đổi
watch(() => filters.value.maTinh, (newVal, oldVal) => {
    if (newVal !== oldVal) { // Chỉ chạy khi giá trị thực sự thay đổi
        fetchDistricts();
    }
}, { immediate: true }); // Chạy ngay lập tức khi component mount

watch(() => filters.value.maHuyen, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        fetchCommunes();
    }
});

watch(() => filters.value.maXa, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        fetchThons();
    }
});

watch(() => filters.value.maThon, (newVal, oldVal) => {
    if (newVal !== oldVal) {
        fetchDiaBans();
    }
});

// Watcher cho maDiaBan để trigger fetchHouseholds khi địa bàn cuối cùng được chọn/thay đổi
// Hoặc bạn có thể gọi fetchHouseholds() sau mỗi lần thay đổi bộ lọc chính.
watch(() => filters.value.maDiaBan, () => {
    fetchHouseholds();
});

// Bạn có thể thêm một watcher tổng quát nếu muốn bảng cập nhật ngay lập tức khi bất kỳ bộ lọc nào thay đổi
// watch(filters, fetchHouseholds, { deep: true });
</script>

<style scoped>
/* Import Bootstrap Icons (nếu bạn sử dụng CDN) */
@import url("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css");

.container-fluid {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f8f9fa;
    padding-bottom: 2rem;
}

.table-responsive {
    margin-top: 1.5rem;
}

.table thead th {
    vertical-align: middle;
    white-space: nowrap;
    background-color: #e9ecef; /* Màu nền cho header bảng */
    border-bottom: 2px solid #dee2e6;
}

.table-bordered th, .table-bordered td {
    border: 1px solid #dee2e6;
}

.table-hover tbody tr:hover {
    background-color: #e2e6ea;
    cursor: pointer;
}

.form-label {
    font-weight: 500;
    color: #495057;
}

.card {
    border: none;
    border-radius: 0.5rem;
}

/* Custom styles for input-group text */
.input-group-text {
    background-color: #e9ecef;
    font-weight: 500;
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

    .col-md-2, .col-md-3, .col-lg-2, .col-lg-4, .col-sm-6 {
        width: 100%; /* Đảm bảo các cột chiếm toàn bộ chiều rộng trên màn hình nhỏ */
    }
}
</style>