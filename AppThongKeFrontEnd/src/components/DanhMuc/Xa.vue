<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import axios from 'axios';

// --- Biến Reactive để lưu trữ dữ liệu cho Form ---
const xaForm = ref({
  id: null,
  ma_tinh: '',
  ma_huyen: '',
  ma_xa: '', // Mã xã
  ten_xa: '', // Tên xã
});

// --- Biến Reactive để lưu trữ danh sách dữ liệu từ API ---
const listTinh = ref([]);
const listHuyen = ref([]); // Danh sách Huyện thô từ API (sẽ được lọc)
const listXa = ref([]);    // Danh sách Xã thô từ API (sẽ được lọc và phân trang)

// --- Biến Reactive cho bộ lọc và tìm kiếm ---
const selectedTinhCode = ref(''); // Mã tỉnh được chọn trong bộ lọc
const selectedHuyenCode = ref(''); // Mã huyện được chọn trong bộ lọc
const searchTerm = ref('');       // Chuỗi tìm kiếm theo tên Xã

// --- Biến Reactive cho phân trang ---
const currentPage = ref(1);
const itemsPerPage = ref(30); // 30 phần tử mỗi trang
const maxPagesToShow = 10;    // Số lượng nút trang tối đa muốn hiển thị (ví dụ: 10 nút)

// --- Computed properties cho lọc, tìm kiếm và phân trang ---

// Computed: Danh sách Huyện được lọc dựa trên Tỉnh đã chọn
const filteredListHuyen = computed(() => {
  if (!selectedTinhCode.value) {
    return []; // Nếu không có tỉnh nào được chọn, không hiển thị huyện nào
  }
  const tinh = listTinh.value.find(t => t.code === selectedTinhCode.value);
  return tinh && tinh.districts ? tinh.districts : [];
});

// Computed: Danh sách Xã đã được lọc theo Tỉnh, Huyện, Tìm kiếm và Phân trang
const filteredAndSearchedAndPaginatedListXa = computed(() => {
  let filtered = listXa.value; // Bắt đầu từ toàn bộ danh sách xã

  // 1. Lọc theo Huyện được chọn
  if (selectedHuyenCode.value) {
    filtered = filtered.filter(xa => xa.district_code === selectedHuyenCode.value);
  } else if (selectedTinhCode.value) {
    // Nếu chỉ chọn Tỉnh mà không chọn Huyện, lọc tất cả xã thuộc Tỉnh đó
    const huyenCodesInSelectedTinh = filteredListHuyen.value.map(h => h.code);
    filtered = filtered.filter(xa => huyenCodesInSelectedTinh.includes(xa.district_code));
  }
  // Nếu không chọn cả Tỉnh và Huyện, filtered vẫn là toàn bộ listXa.value
  // (trừ khi listXa.value đã được lọc từ fetch data, nhưng ở đây ta fetch tất cả và lọc client-side)


  // 2. Tìm kiếm theo tên Xã
  if (searchTerm.value) {
    const searchLower = searchTerm.value.toLowerCase();
    filtered = filtered.filter(xa =>
      xa.ten_xa.toLowerCase().includes(searchLower)
    );
  }

  // 3. Phân trang
  const startIndex = (currentPage.value - 1) * itemsPerPage.value;
  const endIndex = startIndex + itemsPerPage.value;
  return filtered.slice(startIndex, endIndex);
});

// Computed: Tổng số trang sau khi lọc và tìm kiếm (của danh sách Xã)
const totalPages = computed(() => {
  let filtered = listXa.value;

  if (selectedHuyenCode.value) {
    filtered = filtered.filter(xa => xa.district_code === selectedHuyenCode.value);
  } else if (selectedTinhCode.value) {
    const huyenCodesInSelectedTinh = filteredListHuyen.value.map(h => h.code);
    filtered = filtered.filter(xa => huyenCodesInSelectedTinh.includes(xa.district_code));
  }

  if (searchTerm.value) {
    const searchLower = searchTerm.value.toLowerCase();
    filtered = filtered.filter(xa =>
      xa.ten_xa.toLowerCase().includes(searchLower)
    );
  }
  return Math.ceil(filtered.length / itemsPerPage.value);
});

// Computed: Các số trang sẽ hiển thị trên thanh phân trang (logic tương tự Huyen.vue)
const displayedPages = computed(() => {
  const pages = [];
  const total = totalPages.value;
  const current = currentPage.value;
  const max = maxPagesToShow;

  if (total <= max) {
    for (let i = 1; i <= total; i++) {
      pages.push(i);
    }
  } else {
    let startPage = Math.max(1, current - Math.floor(max / 2));
    let endPage = Math.min(total, startPage + max - 1);

    if (endPage - startPage + 1 < max) {
      startPage = Math.max(1, endPage - max + 1);
    }

    if (startPage > 1) {
      pages.push(1);
      if (startPage > 2) {
        pages.push('...');
      }
    }

    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }

    if (endPage < total) {
      if (endPage < total - 1) {
        pages.push('...');
      }
      pages.push(total);
    }
  }
  return pages;
});

// --- Hàm hỗ trợ lấy tên ---
const getTinhName = (tinhCode) => {
  const tinh = listTinh.value.find(t => t.code === tinhCode);
  return tinh ? tinh.name : 'Không xác định';
};

const getHuyenName = (huyenCode) => {
  const huyen = listHuyen.value.find(h => h.code === huyenCode); // Tìm trong listHuyen gốc
  return huyen ? huyen.name : 'Không xác định';
};

// --- Hàm Fetch API ---

// Fetch tất cả Tỉnh, Huyện, Xã ban đầu (vì số lượng Xã không quá lớn như Ấp)
const fetchInitialData = async () => {
  try {
    const response = await axios.get('/api-provinces/?depth=3'); // Lấy đến depth 3 để có xã/phường

    if (response.data && Array.isArray(response.data)) {
      listTinh.value = response.data;

      const allHuyen = [];
      const allXa = [];

      listTinh.value.forEach(tinh => {
        if (tinh.districts && Array.isArray(tinh.districts)) {
          tinh.districts.forEach(huyen => {
            allHuyen.push({
              code: huyen.code,
              name: huyen.name,
              province_code: tinh.code, // Giữ lại mã tỉnh để liên kết
              wards: huyen.wards // Giữ lại wards nếu muốn dùng để tạo listXa
            });

            if (huyen.wards && Array.isArray(huyen.wards)) {
              huyen.wards.forEach(xa => {
                allXa.push({
                  id: xa.code, // ID của xã
                  ma_tinh: tinh.code,
                  ma_huyen: huyen.code,
                  ma_xa: xa.code,
                  ten_xa: xa.name,
                  province_code: tinh.code,    // Để lọc nhanh
                  district_code: huyen.code   // Để lọc nhanh
                });
              });
            }
          });
        }
      });
      listHuyen.value = allHuyen;
      listXa.value = allXa;
      console.log(`Đã tải ${allXa.length} Xã/Phường.`);

    } else {
      console.error('Dữ liệu API trả về không đúng định dạng hoặc trống rỗng:', response.data);
    }
  } catch (error) {
    console.error('Lỗi khi tải dữ liệu tỉnh/huyện/xã từ API:', error);
  }
};


// --- Hàm xử lý Form và tương tác (placeholder) ---
const submitXaForm = async () => {
    alert("Chức năng lưu/cập nhật Xã chưa được triển khai ở backend.");
};

const resetXaForm = () => {
  xaForm.value = { id: null, ma_tinh: '', ma_huyen: '', ma_xa: '', ten_xa: '' };
};

const editXa = async (id) => {
    alert("Chức năng cập nhật Xã chưa được triển khai ở backend.");
    const xaToEdit = listXa.value.find(xa => xa.id === id);
    if (xaToEdit) {
        xaForm.value = {
            id: xaToEdit.id,
            ma_tinh: xaToEdit.ma_tinh,
            ma_huyen: xaToEdit.ma_huyen,
            ma_xa: xaToEdit.ma_xa,
            ten_xa: xaToEdit.ten_xa
        };
    }
};

const deleteXa = async (id) => {
    alert("Chức năng xóa Xã chưa được triển khai ở backend.");
};

// --- Hàm xử lý phân trang ---
const changePage = (page) => {
  if (page === '...') return; // Không làm gì nếu click vào dấu chấm lửng
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
  }
};

// --- Watchers để cập nhật dữ liệu khi lựa chọn thay đổi ---

// Khi Tỉnh thay đổi, reset Huyện được chọn, Xã được chọn và trang hiện tại
watch(selectedTinhCode, () => {
  selectedHuyenCode.value = ''; // Reset huyện
  currentPage.value = 1;      // Reset trang
  searchTerm.value = '';      // Reset tìm kiếm
});

// Khi Huyện thay đổi, reset Xã được chọn và trang hiện tại
watch(selectedHuyenCode, () => {
  currentPage.value = 1; // Reset trang
  searchTerm.value = ''; // Reset tìm kiếm
});

// Khi chuỗi tìm kiếm thay đổi, reset trang hiện tại
watch(searchTerm, () => {
  currentPage.value = 1; // Reset trang
});

// --- OnMounted: Tải dữ liệu ban đầu khi component được mount ---
onMounted(() => {
  fetchInitialData();
});
</script>

<template>
  <div class="container-fluid">
    <div class="row">
      <main class="ms-sm-auto px-4 pt-4">
        <h2>Quản lý Xã/Phường</h2>

        <h3>Form Xã/Phường</h3>
        <form @submit.prevent="submitXaForm">
          <input type="hidden" v-model="xaForm.id" />

          <div class="mb-3">
            <label for="form_ma_tinh_select" class="form-label">Tỉnh:</label>
            <select id="form_ma_tinh_select" class="form-select" v-model="xaForm.ma_tinh" required>
              <option value="" disabled selected>Chọn Tỉnh</option>
              <option v-for="tinh in listTinh" :key="tinh.code" :value="tinh.code">
                {{ tinh.name }}
              </option>
            </select>
          </div>

          <div class="mb-3">
            <label for="form_ma_huyen_select" class="form-label">Huyện:</label>
            <select id="form_ma_huyen_select" class="form-select" v-model="xaForm.ma_huyen" required :disabled="!xaForm.ma_tinh">
              <option value="" disabled selected>Chọn Huyện</option>
              <option v-for="huyen in filteredListHuyen" :key="huyen.code" :value="huyen.code">
                {{ huyen.name }}
              </option>
            </select>
          </div>

          <div class="mb-3">
            <label for="ma_xa_input" class="form-label">Mã Xã:</label>
            <input
              type="text"
              id="ma_xa_input"
              class="form-control"
              v-model="xaForm.ma_xa"
              required
            />
          </div>

          <div class="mb-3">
            <label for="ten_xa_input" class="form-label">Tên Xã:</label>
            <input
              type="text"
              id="ten_xa_input"
              class="form-control"
              v-model="xaForm.ten_xa"
              required
            />
          </div>

          <div class="mb-3">
            <button type="submit" class="btn btn-success">Lưu</button>
            <button type="button" class="btn btn-secondary ms-2" @click="resetXaForm">
              Làm mới Form
            </button>
          </div>
        </form>

        <hr/>

        <h2 class="mt-4">Danh sách Xã/Phường</h2>

        <div class="row mb-3">
          <div class="col-md-6">
            <label for="filter_tinh_select" class="form-label">Tỉnh:</label>
            <select id="filter_tinh_select" class="form-select" v-model="selectedTinhCode">
              <option value="">Tất cả các Tỉnh</option>
              <option v-for="tinh in listTinh" :key="tinh.code" :value="tinh.code">
                {{ tinh.name }}
              </option>
            </select>
          </div>
          <div class="col-md-6">
            <label for="filter_huyen_select" class="form-label">Huyện:</label>
            <select id="filter_huyen_select" class="form-select" v-model="selectedHuyenCode" :disabled="!selectedTinhCode">
              <option value="">Tất cả các Huyện</option>
              <option v-for="huyen in filteredListHuyen" :key="huyen.code" :value="huyen.code">
                {{ huyen.name }}
              </option>
            </select>
          </div>
        </div>

        <div class="mb-3">
          <label for="search_xa_input" class="form-label">Tìm kiếm theo tên Xã:</label>
          <input
            type="text"
            id="search_xa_input"
            class="form-control"
            v-model="searchTerm"
            placeholder="Nhập tên xã/phường để tìm kiếm..."
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
              <th scope="col">Mã Xã</th>
              <th scope="col">Tên Xã</th>
              <th scope="col">Hành động</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="filteredAndSearchedAndPaginatedListXa.length === 0">
              <td colspan="8" class="text-center">
                {{ searchTerm || selectedHuyenCode ? 'Không tìm thấy Xã/Phường nào theo tiêu chí tìm kiếm/lọc.' :
                   (selectedTinhCode ? 'Vui lòng chọn Huyện để xem danh sách Xã/Phường.' : 'Vui lòng chọn Tỉnh và Huyện để xem danh sách Xã/Phường.') }}
              </td>
            </tr>
            <tr v-else v-for="xa_item in filteredAndSearchedAndPaginatedListXa" :key="xa_item.id">
              <td>{{ xa_item.id }}</td>
              <td>{{ xa_item.ma_tinh }}</td>
              <td>{{ getTinhName(xa_item.ma_tinh) }}</td>
              <td>{{ xa_item.ma_huyen }}</td>
              <td>{{ getHuyenName(xa_item.ma_huyen) }}</td>
              <td>{{ xa_item.ma_xa }}</td>
              <td>{{ xa_item.ten_xa }}</td>
              <td>
                <button
                  class="btn btn-warning btn-sm"
                  @click="editXa(xa_item.id)"
                >
                  Cập Nhật
                </button>
                <button
                  class="btn btn-danger btn-sm ms-2"
                  @click="deleteXa(xa_item.id)"
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
              v-for="(page, index) in displayedPages"
              :key="index"
              :class="{ active: page === currentPage, disabled: page === '...' }"
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