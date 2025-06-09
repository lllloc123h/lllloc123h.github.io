<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import axios from 'axios';

const huyenForm = ref({
  id: null,
  ma_tinh: '',
  ma_huyen: '',
  ten_huyen: '',
});

const listTinh = ref([]);
const listHuyen = ref([]);
const searchTerm = ref('');
const selectedFilterTinhCode = ref('');

// --- Các biến cho chức năng phân trang ---
const currentPage = ref(1);
const itemsPerPage = ref(30); // Đã thay đổi thành 30 phần tử mỗi trang
const maxPagesToShow = 10; // Số lượng nút trang tối đa muốn hiển thị (ví dụ: 10 nút)

// Computed property kết hợp lọc, tìm kiếm VÀ phân trang
const filteredAndSearchedAndPaginatedListHuyen = computed(() => {
  let filtered = listHuyen.value;

  // 1. Lọc theo Tỉnh (nếu có selectedFilterTinhCode)
  if (selectedFilterTinhCode.value) {
    filtered = filtered.filter(huyen => huyen.province_code === selectedFilterTinhCode.value);
  }

  // 2. Tìm kiếm theo tên Huyện (nếu có searchTerm)
  if (searchTerm.value) {
    const searchLower = searchTerm.value.toLowerCase();
    filtered = filtered.filter(huyen =>
      huyen.ten_huyen.toLowerCase().includes(searchLower)
    );
  }

  // 3. Phân trang
  const startIndex = (currentPage.value - 1) * itemsPerPage.value;
  const endIndex = startIndex + itemsPerPage.value;
  return filtered.slice(startIndex, endIndex);
});

// Computed property để tính tổng số trang sau khi lọc và tìm kiếm
const totalPages = computed(() => {
  let filtered = listHuyen.value;

  if (selectedFilterTinhCode.value) {
    filtered = filtered.filter(huyen => huyen.province_code === selectedFilterTinhCode.value);
  }

  if (searchTerm.value) {
    const searchLower = searchTerm.value.toLowerCase();
    filtered = filtered.filter(huyen =>
      huyen.ten_huyen.toLowerCase().includes(searchLower)
    );
  }
  return Math.ceil(filtered.length / itemsPerPage.value);
});

// --- Computed property MỚI để tính toán các số trang sẽ hiển thị ---
const displayedPages = computed(() => {
  const pages = [];
  const total = totalPages.value;
  const current = currentPage.value;
  const max = maxPagesToShow;

  if (total <= max) {
    // Nếu tổng số trang ít hơn hoặc bằng số lượng tối đa muốn hiển thị, hiển thị tất cả
    for (let i = 1; i <= total; i++) {
      pages.push(i);
    }
  } else {
    // Tính toán các trang xung quanh trang hiện tại
    let startPage = Math.max(1, current - Math.floor(max / 2));
    let endPage = Math.min(total, startPage + max - 1);

    // Điều chỉnh nếu vượt quá giới hạn
    if (endPage - startPage + 1 < max) {
      startPage = Math.max(1, endPage - max + 1);
    }

    // Thêm trang đầu tiên và dấu "..." nếu cần
    if (startPage > 1) {
      pages.push(1);
      if (startPage > 2) {
        pages.push('...'); // Dấu chấm lửng
      }
    }

    // Thêm các trang trong khoảng hiển thị
    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }

    // Thêm dấu "..." và trang cuối cùng nếu cần
    if (endPage < total) {
      if (endPage < total - 1) {
        pages.push('...'); // Dấu chấm lửng
      }
      pages.push(total);
    }
  }
  return pages;
});


const getTinhName = (tinhCode) => {
  const tinh = listTinh.value.find(t => t.code === tinhCode);
  return tinh ? tinh.name : 'Không xác định';
};

const fetchTinhAPI = async () => {
  try {
    const response = await axios.get('/api-provinces/?depth=2');

    if (response.data && Array.isArray(response.data)) {
      listTinh.value = response.data;

      const allHuyen = [];
      listTinh.value.forEach(tinh => {
        if (tinh.districts && Array.isArray(tinh.districts)) {
          tinh.districts.forEach(huyen => {
            allHuyen.push({
              id: huyen.code,
              ma_tinh: tinh.code,
              ma_huyen: huyen.code,
              ten_huyen: huyen.name,
              province_code: tinh.code
            });
          });
        }
      });
      listHuyen.value = allHuyen;

    } else {
      console.error('Dữ liệu API trả về không đúng định dạng hoặc trống rỗng:', response.data);
    }

  } catch (error) {
    console.error('Lỗi khi tải danh sách tỉnh/huyện từ API:', error);
  }
};

const submitHuyenForm = async () => {
    alert("Chức năng lưu/cập nhật chưa được triển khai ở backend.");
};

const resetHuyenForm = () => {
  huyenForm.value = { id: null, ma_tinh: '', ma_huyen: '', ten_huyen: '' };
};

const editHuyen = async (id) => {
    alert("Chức năng cập nhật huyện chưa được triển khai ở backend.");
    const huyenToEdit = listHuyen.value.find(h => h.id === id);
    if (huyenToEdit) {
        huyenForm.value = {
            id: huyenToEdit.id,
            ma_tinh: huyenToEdit.ma_tinh,
            ma_huyen: huyenToEdit.ma_huyen,
            ten_huyen: huyenToEdit.ten_huyen
        };
    }
};

const deleteHuyen = async (id) => {
    alert("Chức năng xóa huyện chưa được triển khai ở backend.");
};

// --- Hàm xử lý phân trang ---
const changePage = (page) => {
  if (page === '...') return; // Không làm gì nếu click vào dấu chấm lửng
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
  }
};

// Watcher: Reset về trang 1 khi filter hoặc search thay đổi
watch([searchTerm, selectedFilterTinhCode], () => {
  currentPage.value = 1;
});

onMounted(() => {
  fetchTinhAPI();
});
</script>

<template>
  <div class="container-fluid">
    <div class="row">
      <main class="ms-sm-auto px-4 pt-4">
        <h2>Form Huyện</h2>

        <form @submit.prevent="submitHuyenForm">
          <input type="hidden" v-model="huyenForm.id" />

          <div class="mb-3">
            <label for="ma_tinh_select" class="form-label">Tỉnh:</label>
            <select id="ma_tinh_select" class="form-select" v-model="huyenForm.ma_tinh" required>
              <option value="" disabled selected>Chọn Tỉnh</option>
              <option v-for="tinh in listTinh" :key="tinh.code" :value="tinh.code">
                {{ tinh.name }}
              </option>
            </select>
          </div>

          <div class="mb-3">
            <label for="ma_huyen_input" class="form-label">Mã Huyện:</label>
            <input
              type="text"
              id="ma_huyen_input"
              class="form-control"
              v-model="huyenForm.ma_huyen"
              required
            />
          </div>

          <div class="mb-3">
            <label for="ten_huyen_input" class="form-label">Tên Huyện:</label>
            <input
              type="text"
              id="ten_huyen_input"
              class="form-control"
              v-model="huyenForm.ten_huyen"
              required
            />
          </div>

          <div class="mb-3">
            <button type="submit" class="btn btn-success">Lưu</button>
            <button type="button" class="btn btn-secondary ms-2" @click="resetHuyenForm">
              Làm mới Form
            </button>
          </div>
        </form>

        <hr/>

        <h2 class="mt-4">Danh sách Huyện</h2>

        <div class="mb-3">
          <label for="filter_tinh_select" class="form-label">Lọc theo Tỉnh:</label>
          <select id="filter_tinh_select" class="form-select" v-model="selectedFilterTinhCode">
            <option value="">Tất cả các Tỉnh</option>
            <option v-for="tinh in listTinh" :key="tinh.code" :value="tinh.code">
              {{ tinh.name }}
            </option>
          </select>
        </div>

        <div class="mb-3">
          <label for="search_huyen_input" class="form-label">Tìm kiếm theo tên Huyện:</label>
          <input
            type="text"
            id="search_huyen_input"
            class="form-control"
            v-model="searchTerm"
            placeholder="Nhập tên huyện để tìm kiếm..."
          />
        </div>

        <table class="table table-bordered table-hover table-striped">
          <thead class="table-dark">
            <tr>
              <th scope="col">ID (API)</th>
              <th scope="col">Mã Tỉnh</th>
              <th scope="col">Tên Tỉnh</th>
              <th scope="col">Mã Huyện</th>
              <th scope="col">Tên Huyện</th>
              <th scope="col">Hành động</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="filteredAndSearchedAndPaginatedListHuyen.length === 0">
              <td colspan="6" class="text-center">
                {{ searchTerm || selectedFilterTinhCode ? 'Không tìm thấy huyện nào theo tiêu chí tìm kiếm/lọc.' : 'Không có huyện nào trong danh sách.' }}
              </td>
            </tr>
            <tr v-else v-for="huyen_item in filteredAndSearchedAndPaginatedListHuyen" :key="huyen_item.id">
              <td>{{ huyen_item.id }}</td>
              <td>{{ huyen_item.ma_tinh }}</td>
              <td>{{ getTinhName(huyen_item.ma_tinh) }}</td>
              <td>{{ huyen_item.ma_huyen }}</td>
              <td>{{ huyen_item.ten_huyen }}</td>
              <td>
                <button
                  class="btn btn-warning btn-sm"
                  @click="editHuyen(huyen_item.id)"
                >
                  Cập Nhật
                </button>
                <button
                  class="btn btn-danger btn-sm ms-2"
                  @click="deleteHuyen(huyen_item.id)"
                >
                  Xóa
                </button>
              </td>
            </tr>
          </tbody>
        </table>

        <nav aria-label="Page navigation" class="mt-4">
          <ul class="pagination justify-content-center">
            <li class="page-item" :class="{ disabled: currentPage === 1 }">
              <a class="page-link" href="#" @click.prevent="changePage(1)">Đầu</a>
            </li>
            <li class="page-item" :class="{ disabled: currentPage === 1 }">
              <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">Trước</a>
            </li>
            <li
              class="page-item"
              v-for="page in totalPages"
              :key="page"
              :class="{ active: page === currentPage }"
            >
              <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
            </li>
            <li class="page-item" :class="{ disabled: currentPage === totalPages }">
              <a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)">Sau</a>
            </li>
            <li class="page-item" :class="{ disabled: currentPage === totalPages }">
              <a class="page-link" href="#" @click.prevent="changePage(totalPages)">Cuối</a>
            </li>
          </ul>
        </nav>
        </main>
    </div>
  </div>
</template>

<style scoped>
h2, h3 {
  text-align: center;
  margin-bottom: 20px;
}
</style>