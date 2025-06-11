<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center mb-4">Duyệt dữ liệu địa bàn mẫu</h2>

                <div class="card p-3 mb-4">
                    <div class="row g-3 align-items-end">
                        <div class="col-sm-6 col-md-4 col-lg-2">
                            <label for="filterYearDuyet" class="form-label">Năm</label>
                            <select v-model="filtersDuyet.year" class="form-select" id="filterYearDuyet">
                                <option value="">---Tất cả---</option>
                                <option>2025</option>
                                <option>2024</option>
                                <option>2023</option>
                            </select>
                        </div>
                        <div class="col-sm-6 col-md-4 col-lg-3">
                            <label for="filterProvinceDuyet" class="form-label">Tỉnh/TP</label>
                            <select v-model="filtersDuyet.province" class="form-select" id="filterProvinceDuyet">
                                <option value="">---Tất cả---</option>
                                <option value="87">87 - Đồng Tháp</option>
                                <option value="89">89 - An Giang</option>
                            </select>
                        </div>
                        <div class="col-12 col-sm-6 col-md-4 col-lg-auto d-flex align-items-end">
                            <button class="btn btn-primary" @click="viewDuyetData">
                                <i class="bi bi-eye"></i> Xem
                            </button>
                        </div>
                    </div>
                </div>

                <table class="table table-bordered table-hover mt-3">
                    <thead>
                        <tr>
                            <th scope="col">Mã Tỉnh</th>
                            <th scope="col">Tên Tỉnh</th>
                            <th scope="col">Thời điểm thực hiện</th>
                            <th scope="col">Người thực hiện</th>
                            <th scope="col">Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="item in filteredDuyetData" :key="item.ma_tinh + '-' + item.nam + '-' + item.thoi_diem_thuc_hien">
                            <td>{{ item.ma_tinh }}</td>
                            <td>{{ item.ten_tinh }}</td>
                            <td>{{ item.thoi_diem_thuc_hien }}</td>
                            <td>{{ item.nguoi_thuc_hien }}</td>
                            <td>{{ item.trang_thai }}</td>
                        </tr>
                        <tr v-if="filteredDuyetData.length === 0">
                            <td colspan="5" class="text-center">Chưa có dữ liệu duyệt</td>
                        </tr>
                    </tbody>
                </table>
            </main>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';

// State cho bộ lọc của component này
const filtersDuyet = ref({
    year: '2025', // Mặc định năm 2025 như trong ảnh
    province: '87' // Mặc định Đồng Tháp như trong ảnh
});

// Dữ liệu mẫu cho bảng duyệt
const listDuyetDiaBanMau = ref([
    {
        nam: '2025',
        ma_tinh: 87,
        ten_tinh: 'Đồng Tháp',
        thoi_diem_thuc_hien: '8:58:363 - 18/12/2024',
        nguoi_thuc_hien: 'ngthuydth',
        trang_thai: 'Đã duyệt'
    },
    {
        nam: '2025',
        ma_tinh: 89,
        ten_tinh: 'An Giang',
        thoi_diem_thuc_hien: '9:15:000 - 18/12/2024',
        nguoi_thuc_hien: 'user_an_giang',
        trang_thai: 'Đang chờ duyệt'
    },
    {
        nam: '2024',
        ma_tinh: 87,
        ten_tinh: 'Đồng Tháp',
        thoi_diem_thuc_hien: '10:00:000 - 01/01/2024',
        nguoi_thuc_hien: 'admin_dt',
        trang_thai: 'Đã duyệt'
    }
]);

// Computed property để lọc dữ liệu hiển thị
const filteredDuyetData = computed(() => {
    let filtered = listDuyetDiaBanMau.value;

    if (filtersDuyet.value.year) {
        filtered = filtered.filter(item => item.nam === filtersDuyet.value.year);
    }

    if (filtersDuyet.value.province) {
        filtered = filtered.filter(item => item.ma_tinh === parseInt(filtersDuyet.value.province));
    }

    return filtered;
});

// Hàm cho nút "Xem"
function viewDuyetData() {
    alert('Thực hiện chức năng xem dữ liệu duyệt với bộ lọc: Năm = ' + filtersDuyet.value.year + ', Tỉnh = ' + filtersDuyet.value.province);
    // Trong thực tế, bạn sẽ gọi API để tải dữ liệu dựa trên các bộ lọc
}

</script>

<style scoped>
/* Giữ lại các style chung nếu bạn muốn component này có giao diện tương tự */
.container-fluid {
    font-family: 'Segoe UI', sans-serif;
    background-color: #f8f9fa;
}

/* Các style liên quan đến sidebar và header có thể bị xóa nếu component này độc lập */
/* .sidebar, .header, .logout-btn có thể không cần thiết ở đây */

.table-hover tbody tr:hover {
    background-color: #e2e6ea;
    cursor: pointer;
}
</style>