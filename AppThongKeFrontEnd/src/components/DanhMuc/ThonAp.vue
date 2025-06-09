<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import axios from 'axios';

// --- Biến Reactive để lưu trữ dữ liệu ---
const huyenForm = ref({ // Có thể không cần form này nếu chỉ hiển thị
  id: null,
  ma_tinh: '',
  ma_huyen: '',
  ma_xa: '', // Thêm mã xã để liên kết đến Ấp
  ten_ap: '',
});

const listTinh = ref([]);
const listHuyen = ref([]);
const listXa = ref([]); // Danh sách Xã/Phường
const listAp = ref([]); // Danh sách Ấp/Thôn/Khu phố

// --- Biến Reactive cho bộ lọc và tìm kiếm ---
const selectedTinhCode = ref('');
const selectedHuyenCode = ref('');
const selectedXaCode = ref(''); // Mới: Lọc theo Xã
const searchTerm = ref(''); // Tìm kiếm Ấp theo tên

// --- Biến Reactive cho phân trang ---
const currentPage = ref(1);
const itemsPerPage = ref(30); // 30 phần tử mỗi trang
const maxPagesToShow = 10; // Số lượng nút trang tối đa muốn hiển thị

// --- Computed properties cho lọc, tìm kiếm và phân trang ---

// Lọc danh sách Huyện theo Tỉnh được chọn
const filteredListHuyen = computed(() => {
  if (!selectedTinhCode.value) {
    return []; // Không có tỉnh nào được chọn, không hiển thị huyện nào
  }
  const tinh = listTinh.value.find(t => t.code === selectedTinhCode.value);
  return tinh && tinh.districts ? tinh.districts : [];
});

// Lọc danh sách Xã/Phường theo Huyện được chọn
const filteredListXa = computed(() => {
  if (!selectedHuyenCode.value) {
    return [];
  }
  const huyen = filteredListHuyen.value.find(h => h.code === selectedHuyenCode.value);
  // Giả định rằng dữ liệu Huyện có chứa mảng 'wards'
  return huyen && huyen.wards ? huyen.wards : [];
});

// Danh sách Ấp đã lọc, tìm kiếm và phân trang
const filteredAndSearchedAndPaginatedListAp = computed(() => {
  let filtered = listAp.value;

  // Lọc theo Xã/Phường được chọn (nếu có)
  if (selectedXaCode.value) {
    filtered = filtered.filter(ap => ap.ward_code === selectedXaCode.value);
  } else if (selectedHuyenCode.value) {
    // Nếu không chọn Xã/Phường cụ thể nhưng đã chọn Huyện,
    // ta cần lọc Ấp dựa trên các Xã/Phường thuộc Huyện đó.
    // Điều này đòi hỏi dữ liệu `listAp` đã có `ward_code` hoặc `district_code`
    // Hoặc ta cần đảm bảo fetch_Ap_By_Huyen_Or_Xa chỉ lấy Ấp của huyện/xã đó.
    // Hiện tại, logic fetch_Ap_By_Huyen_Or_Xa sẽ chỉ lấy Ấp của huyện/xã được chọn,
    // nên phần lọc này có thể không cần thiết nếu logic fetch là chính xác.
  }

  // Tìm kiếm theo tên Ấp
  if (searchTerm.value) {
    const searchLower = searchTerm.value.toLowerCase();
    filtered = filtered.filter(ap =>
      ap.ten_ap.toLowerCase().includes(searchLower)
    );
  }

  // Phân trang
  const startIndex = (currentPage.value - 1) * itemsPerPage.value;
  const endIndex = startIndex + itemsPerPage.value;
  return filtered.slice(startIndex, endIndex);
});

// Tổng số trang sau khi lọc và tìm kiếm (của danh sách Ấp)
const totalPages = computed(() => {
  let filtered = listAp.value;

  if (selectedXaCode.value) {
    filtered = filtered.filter(ap => ap.ward_code === selectedXaCode.value);
  }

  if (searchTerm.value) {
    const searchLower = searchTerm.value.toLowerCase();
    filtered = filtered.filter(ap =>
      ap.ten_ap.toLowerCase().includes(searchLower)
    );
  }
  return Math.ceil(filtered.length / itemsPerPage.value);
});

// Các số trang sẽ hiển thị trên thanh phân trang (logic tương tự Huyen.vue)
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
  const huyen = filteredListHuyen.value.find(h => h.code === huyenCode);
  return huyen ? huyen.name : 'Không xác định';
};

const getXaName = (xaCode) => {
  const xa = filteredListXa.value.find(x => x.code === xaCode);
  return xa ? xa.name : 'Không xác định';
};

// --- Hàm Fetch API ---

// Fetch tất cả Tỉnh và Huyện (và Xã) ban đầu
const fetchInitialData = async () => {
  try {
    // Gọi API với depth đủ lớn để lấy wards (xã/phường)
    // Nếu API của bạn có depth cho ấp/thôn, hãy thay đổi thành depth cao hơn (vd: 4 hoặc 5)
    // Hiện tại, tôi giả định wards có trong depth=3
    const response = await axios.get('/api-provinces/?depth=3');

    if (response.data && Array.isArray(response.data)) {
      listTinh.value = response.data;
      
      // Khởi tạo danh sách Huyện và Xã để sử dụng cho select boxes
      // Chúng ta sẽ lọc chúng qua computed properties
    } else {
      console.error('Dữ liệu API trả về không đúng định dạng hoặc trống rỗng:', response.data);
    }
  } catch (error) {
    console.error('Lỗi khi tải dữ liệu tỉnh/huyện/xã từ API:', error);
  }
};

// Hàm fetch Ấp dựa trên Huyện hoặc Xã được chọn
// Đây là phần quan trọng nhất và giả định về cấu trúc API của bạn
const fetchApData = async () => {
  listAp.value = []; // Reset danh sách Ấp
  currentPage.value = 1; // Reset trang về 1 khi fetch mới

  if (!selectedXaCode.value && !selectedHuyenCode.value) {
      // Không có Xã hoặc Huyện được chọn, không fetch Ấp
      return;
  }

  try {
      // Giả định 1: Có thể lấy wards từ Huyện, và wards có 'villages' hoặc 'residential_areas'
      // Đây là cách client-side lọc nếu API đã trả về tất cả xã/phường có chứa ấp
      let apData = [];
      if (selectedXaCode.value) {
          const selectedXa = filteredListXa.value.find(x => x.code === selectedXaCode.value);
          if (selectedXa && selectedXa.villages) { // Hoặc selectedXa.residential_areas
              apData = selectedXa.villages.map(ap => ({
                  id: ap.code,
                  ma_tinh: selectedTinhCode.value,
                  ma_huyen: selectedHuyenCode.value,
                  ma_xa: selectedXaCode.value,
                  ten_ap: ap.name,
                  ward_code: selectedXaCode.value // Để lọc theo Xã
              }));
          } else {
              console.warn(`Không tìm thấy Ấp cho xã/phường có mã: ${selectedXaCode.value} hoặc cấu trúc dữ liệu không đúng.`);
          }
      } else if (selectedHuyenCode.value) {
          // Nếu chỉ chọn Huyện mà không chọn Xã, lấy tất cả Ấp của các Xã thuộc Huyện đó
          const huyen = filteredListHuyen.value.find(h => h.code === selectedHuyenCode.value);
          if (huyen && huyen.wards) {
              huyen.wards.forEach(xa => {
                  if (xa.villages) { // Hoặc xa.residential_areas
                      xa.villages.forEach(ap => {
                          apData.push({
                              id: ap.code,
                              ma_tinh: selectedTinhCode.value,
                              ma_huyen: selectedHuyenCode.value,
                              ma_xa: xa.code,
                              ten_ap: ap.name,
                              ward_code: xa.code
                          });
                      });
                  }
              });
          }
      }
      listAp.value = apData;
      console.log(`Đã tải ${apData.length} Ấp.`);

      // Giả định 2 (Tốt hơn cho số lượng Ấp lớn): Fetch trực tiếp từ API theo mã Xã/Huyện
      // Bạn cần một endpoint API hỗ trợ điều này. Ví dụ:
      // const response = await axios.get(`/api-wards/${selectedXaCode.value}/villages`);
      // listAp.value = response.data.map(ap => ({
      //     id: ap.code,
      //     ma_tinh: selectedTinhCode.value,
      //     ma_huyen: selectedHuyenCode.value,
      //     ma_xa: selectedXaCode.value,
      //     ten_ap: ap.name,
      //     ward_code: selectedXaCode.value
      // }));
      // Hoặc nếu API có endpoint cho huyện:
      // const response = await axios.get(`/api-districts/${selectedHuyenCode.value}/villages`);

  } catch (error) {
    console.error('Lỗi khi tải danh sách Ấp:', error);
  }
};


// --- Hàm xử lý Form và tương tác ---
const submitApForm = async () => {
    alert("Chức năng lưu/cập nhật Ấp chưa được triển khai ở backend.");
};

const resetApForm = () => {
  huyenForm.value = { id: null, ma_tinh: '', ma_huyen: '', ma_xa: '', ten_ap: '' };
};

const editAp = async (id) => {
    alert("Chức năng cập nhật Ấp chưa được triển khai ở backend.");
    const apToEdit = listAp.value.find(ap => ap.id === id);
    if (apToEdit) {
        huyenForm.value = { // Sử dụng huyenForm tạm thời để điền dữ liệu
            id: apToEdit.id,
            ma_tinh: apToEdit.ma_tinh,
            ma_huyen: apToEdit.ma_huyen,
            ma_xa: apToEdit.ma_xa,
            ten_ap: apToEdit.ten_ap
        };
    }
};

const deleteAp = async (id) => {
    alert("Chức năng xóa Ấp chưa được triển khai ở backend.");
};

// --- Hàm xử lý phân trang ---
const changePage = (page) => {
  if (page === '...') return;
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
  }
};

// --- Watchers để cập nhật dữ liệu khi lựa chọn thay đổi ---

// Khi Tỉnh thay đổi, reset Huyện, Xã và Ấp
watch(selectedTinhCode, (newTinhCode) => {
  selectedHuyenCode.value = ''; // Reset huyện
  selectedXaCode.value = ''; // Reset xã
  listHuyen.value = []; // Xóa danh sách huyện cũ
  listXa.value = []; // Xóa danh sách xã cũ
  listAp.value = []; // Xóa danh sách ấp cũ
  currentPage.value = 1; // Reset trang

  // Không cần fetch Huyện riêng vì filteredListHuyen đã xử lý
  // Nhưng cần đảm bảo data tỉnh đã có
  if (newTinhCode) {
      // Logic đã được xử lý bởi computed property filteredListHuyen
  }
});

// Khi Huyện thay đổi, reset Xã và Ấp, sau đó fetch Ấp mới
watch(selectedHuyenCode, (newHuyenCode) => {
  selectedXaCode.value = ''; // Reset xã
  listXa.value = []; // Xóa danh sách xã cũ
  listAp.value = []; // Xóa danh sách ấp cũ
  currentPage.value = 1; // Reset trang

  if (newHuyenCode) {
      // Cập nhật danh sách Xã (đã xử lý bởi computed property filteredListXa)
      // Sau đó fetch Ấp
      fetchApData();
  }
});

// Khi Xã thay đổi, fetch Ấp mới
watch(selectedXaCode, (newXaCode) => {
  listAp.value = []; // Xóa danh sách ấp cũ
  currentPage.value = 1; // Reset trang
  if (newXaCode) {
      fetchApData();
  } else if (selectedHuyenCode.value) {
      // Nếu bỏ chọn Xã nhưng Huyện vẫn được chọn, fetch lại tất cả Ấp của Huyện đó
      fetchApData();
  }
});

// Watcher: Reset về trang 1 khi searchTerm thay đổi
watch(searchTerm, () => {
  currentPage.value = 1;
});

// --- OnMounted: Tải dữ liệu ban đầu ---
onMounted(() => {
  fetchInitialData();
});
</script>

<template>
  <div class="container-fluid">
    <div class="row">
      <main class="ms-sm-auto px-4 pt-4">
        <h2>Quản lý Ấp/Thôn/Khu phố</h2>

        <h3>Form Ấp</h3>
        <form @submit.prevent="submitApForm">
          <input type="hidden" v-model="huyenForm.id" />

          <div class="mb-3">
            <label for="form_ma_tinh_select" class="form-label">Tỉnh:</label>
            <select id="form_ma_tinh_select" class="form-select" v-model="huyenForm.ma_tinh" required>
              <option value="" disabled selected>Chọn Tỉnh</option>
              <option v-for="tinh in listTinh" :key="tinh.code" :value="tinh.code">
                {{ tinh.name }}
              </option>
            </select>
          </div>

          <div class="mb-3">
            <label for="form_ma_huyen_select" class="form-label">Huyện:</label>
            <select id="form_ma_huyen_select" class="form-select" v-model="huyenForm.ma_huyen" required>
              <option value="" disabled selected>Chọn Huyện</option>
              <option v-for="huyen in filteredListHuyen" :key="huyen.code" :value="huyen.code">
                {{ huyen.name }}
              </option>
            </select>
          </div>

          <div class="mb-3">
            <label for="form_ma_xa_select" class="form-label">Xã/Phường:</label>
            <select id="form_ma_xa_select" class="form-select" v-model="huyenForm.ma_xa" required>
              <option value="" disabled selected>Chọn Xã/Phường</option>
              <option v-for="xa in filteredListXa" :key="xa.code" :value="xa.code">
                {{ xa.name }}
              </option>
            </select>
          </div>

          <div class="mb-3">
            <label for="ten_ap_input" class="form-label">Tên Ấp:</label>
            <input
              type="text"
              id="ten_ap_input"
              class="form-control"
              v-model="huyenForm.ten_ap"
              required
            />
          </div>

          <div class="mb-3">
            <button type="submit" class="btn btn-success">Lưu</button>
            <button type="button" class="btn btn-secondary ms-2" @click="resetApForm">
              Làm mới Form
            </button>
          </div>
        </form>

        <hr/>

        <h2 class="mt-4">Danh sách Ấp</h2>

        <div class="row mb-3">
          <div class="col-md-4">
            <label for="filter_tinh_select" class="form-label">Tỉnh:</label>
            <select id="filter_tinh_select" class="form-select" v-model="selectedTinhCode">
              <option value="">Tất cả các Tỉnh</option>
              <option v-for="tinh in listTinh" :key="tinh.code" :value="tinh.code">
                {{ tinh.name }}
              </option>
            </select>
          </div>
          <div class="col-md-4">
            <label for="filter_huyen_select" class="form-label">Huyện:</label>
            <select id="filter_huyen_select" class="form-select" v-model="selectedHuyenCode" :disabled="!selectedTinhCode">
              <option value="">Tất cả các Huyện</option>
              <option v-for="huyen in filteredListHuyen" :key="huyen.code" :value="huyen.code">
                {{ huyen.name }}
              </option>
            </select>
          </div>
          <div class="col-md-4">
            <label for="filter_xa_select" class="form-label">Xã/Phường:</label>
            <select id="filter_xa_select" class="form-select" v-model="selectedXaCode" :disabled="!selectedHuyenCode">
              <option value="">Tất cả các Xã/Phường</option>
              <option v-for="xa in filteredListXa" :key="xa.code" :value="xa.code">
                {{ xa.name }}
              </option>
            </select>
          </div>
        </div>

        <div class="mb-3">
          <label for="search_ap_input" class="form-label">Tìm kiếm theo tên Ấp:</label>
          <input
            type="text"
            id="search_ap_input"
            class="form-control"
            v-model="searchTerm"
            placeholder="Nhập tên ấp để tìm kiếm..."
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
              <th scope="col">Mã Ấp</th>
              <th scope="col">Tên Ấp</th>
              <th scope="col">Hành động</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="filteredAndSearchedAndPaginatedListAp.length === 0">
              <td colspan="10" class="text-center">
                {{ searchTerm || selectedXaCode ? 'Không tìm thấy Ấp nào theo tiêu chí tìm kiếm/lọc.' :
                   (selectedHuyenCode ? 'Không có Ấp nào cho Huyện được chọn.' :
                   (selectedTinhCode ? 'Vui lòng chọn Huyện để xem danh sách Ấp.' : 'Vui lòng chọn Tỉnh và Huyện để xem danh sách Ấp.')) }}
              </td>
            </tr>
            <tr v-else v-for="ap_item in filteredAndSearchedAndPaginatedListAp" :key="ap_item.id">
              <td>{{ ap_item.id }}</td>
              <td>{{ ap_item.ma_tinh }}</td>
              <td>{{ getTinhName(ap_item.ma_tinh) }}</td>
              <td>{{ ap_item.ma_huyen }}</td>
              <td>{{ getHuyenName(ap_item.ma_huyen) }}</td>
              <td>{{ ap_item.ma_xa }}</td>
              <td>{{ getXaName(ap_item.ma_xa) }}</td>
              <td>{{ ap_item.ma_ap }}</td> <td>{{ ap_item.ten_ap }}</td>
              <td>
                <button
                  class="btn btn-warning btn-sm"
                  @click="editAp(ap_item.id)"
                >
                  Cập Nhật
                </button>
                <button
                  class="btn btn-danger btn-sm ms-2"
                  @click="deleteAp(ap_item.id)"
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