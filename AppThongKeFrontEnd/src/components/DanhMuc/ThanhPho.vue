<template>
  <div class="container-fluid">
    <div class="row">
      <main class="ms-sm-auto px-4 pt-4">
        <h2 class="text-center">Form Tỉnh</h2>

        <form @submit.prevent="submitTinhForm">
          <input type="hidden" v-model="tinhForm.id" />

          <div class="mb-3">
            <label for="ma_tinh_input" class="form-label">Mã Tỉnh:</label>
            <input
              type="text"
              id="ma_tinh_input"
              class="form-control"
              v-model="tinhForm.ma_tinh"
              required
            />
          </div>

          <div class="mb-3">
            <label for="ten_tinh_input" class="form-label">Tên Tỉnh:</label>
            <input
              type="text"
              id="ten_tinh_input"
              class="form-control"
              v-model="tinhForm.ten_tinh"
              required
            />
          </div>

          <div class="mb-3">
            <button type="submit" class="btn btn-success">Lưu</button>
            <button type="button" class="btn btn-secondary ms-2" @click="resetTinhForm">
              Làm mới Form
            </button>
          </div>
        </form>

        <hr/>

        <h3 class="mt-4">Danh sách Tỉnh</h3>

        <div class="mb-3">
          <label for="search_tinh_input" class="form-label">Tìm kiếm Tỉnh:</label>
          <input
            type="text"
            id="search_tinh_input"
            class="form-control"
            v-model="searchTerm"
            placeholder="Nhập tên tỉnh để tìm kiếm..."
          />
        </div>

        <table class="table table-bordered table-hover table-striped">
          <thead class="table-dark">
            <tr>
              <th scope="col">Mã Tỉnh</th>
              <th scope="col">Tên Tỉnh</th>
              <th scope="col">Hành động</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="filteredListTinh.length === 0">
              <td colspan="3" class="text-center">Không tìm thấy tỉnh nào.</td>
            </tr>
            <tr v-else v-for="tinh_item in filteredListTinh" :key="tinh_item.code">
              <td>{{ tinh_item.code }}</td>
              <td>{{ tinh_item.name }}</td>
              <td>
                <button
                  class="btn btn-warning btn-sm"
                  @click="editTinh(tinh_item.code)"
                >
                  Cập Nhật
                </button>
                <button
                  class="btn btn-danger btn-sm ms-2"
                  @click="deleteTinh(tinh_item.code)"
                >
                  Xóa
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';

const tinhForm = ref({
  id: null,
  ma_tinh: '',
  ten_tinh: '',
});

const listTinh = ref([]);
const searchTerm = ref('');

const filteredListTinh = computed(() => {
  if (!searchTerm.value) {
    return listTinh.value;
  }
  const searchLower = searchTerm.value.toLowerCase();
  return listTinh.value.filter(tinh =>
    tinh.name.toLowerCase().includes(searchLower)
  );
});

const fetchTinh = async () => {
  try {
    const response = await axios.get('https://provinces.open-api.vn/api/?depth=1');
    listTinh.value = response.data;
    console.log('Đã tải thành công 63 tỉnh thành:', listTinh.value);
  } catch (error) {
    console.error('Lỗi khi tải danh sách tỉnh:', error);
    alert('Không thể tải danh sách tỉnh. Vui lòng kiểm tra kết nối mạng hoặc API.');
  }
};

const submitTinhForm = () => {
  console.log('Form Tỉnh đã được submit:', tinhForm.value);
  alert('Chức năng Lưu Tỉnh chưa được triển khai API (cần backend của bạn).');
};

const resetTinhForm = () => {
  tinhForm.value = {
    id: null,
    ma_tinh: '',
    ten_tinh: '',
  };
  alert('Form Tỉnh đã được làm mới.');
};

const editTinh = (code) => {
  console.log('Yêu cầu chỉnh sửa Tỉnh với Mã:', code);
  alert(`Chức năng Cập nhật Tỉnh có mã ${code} chưa được triển khai API (cần backend của bạn).`);
  const tinhToEdit = listTinh.value.find(tinh => tinh.code === code);
  if (tinhToEdit) {
    tinhForm.value.id = tinhToEdit.code;
    tinhForm.value.ma_tinh = tinhToEdit.code;
    tinhForm.value.ten_tinh = tinhToEdit.name;
  }
};

const deleteTinh = (code) => {
  if (confirm('Bạn có chắc chắn muốn xóa tỉnh này không?')) {
    console.log('Yêu cầu xóa Tỉnh với Mã:', code);
    alert(`Chức năng Xóa Tỉnh có mã ${code} chưa được triển khai API (cần backend của bạn).`);
  }
};

onMounted(fetchTinh);
</script>

<style scoped>
h2, h3 {
  text-align: center;
  margin-bottom: 20px;
}
</style>