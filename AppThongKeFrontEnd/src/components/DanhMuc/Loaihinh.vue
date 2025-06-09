<template>
  <div class="container-fluid">
    <div class="row">
      <main class="ms-sm-auto px-4 pt-4">
        <h2 class="text-center">Form Huyện</h2>

        <form @submit.prevent="submitForm">
          <input type="hidden" v-model="huyenForm.Id" />

          <div class="mb-3">
            <label for="ma_tinh_input" class="form-label">Mã Tỉnh:</label>
            <input
              type="text"
              id="ma_tinh_input"
              class="form-control"
              v-model="huyenForm.ma_tinh"
              required
            />
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
            <button type="submit" class="btn btn-primary">Lưu</button>
            <button type="button" class="btn btn-secondary ms-2" @click="resetForm">
              Làm mới Form
            </button>
          </div>
        </form>

        <h3 class="mt-4">Danh Sách Huyện</h3>
        <table class="table table-bordered mt-3">
          <thead>
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Mã Tỉnh</th>
              <th scope="col">Mã Huyện</th>
              <th scope="col">Tên Huyện</th>
              <th scope="col">Hành động</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="listHuyen.length === 0">
              <td colspan="5" class="text-center">Không có huyện nào trong cơ sở dữ liệu.</td>
            </tr>
            <tr v-else v-for="huyen in listHuyen" :key="huyen.Id">
              <td>{{ huyen.Id }}</td>
              <td>{{ huyen.ma_tinh }}</td>
              <td>{{ huyen.ma_huyen }}</td>
              <td>{{ huyen.ten_huyen }}</td>
              <td>
                <button
                  class="btn btn-warning btn-sm"
                  @click="editHuyen(huyen.Id)"
                >
                  Cập Nhật
                </button>
                <button
                  class="btn btn-danger btn-sm ms-2"
                  @click="deleteHuyen(huyen.Id)"
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
import { ref } from 'vue';

const huyenForm = ref({
  Id: null,
  ma_tinh: '',
  ma_huyen: '',
  ten_huyen: '',
});

const listHuyen = ref([

  { Id: 1, ma_tinh: 'DTH', ma_huyen: 'CL', ten_huyen: 'Cao Lãnh' },
  { Id: 2, ma_tinh: 'DTH', ma_huyen: 'TM', ten_huyen: 'Tháp Mười' },
  { Id: 3, ma_tinh: 'DTH', ma_huyen: 'HG', ten_huyen: 'Hồng Ngự' },
]);


const submitForm = () => {
  alert('Form đã được submit! (Đây là hàm placeholder)');
  console.log('Dữ liệu form:', huyenForm.value);

};

const resetForm = () => {
  huyenForm.value = {
    Id: null,
    ma_tinh: '',
    ma_huyen: '',
    ten_huyen: '',
  };
  alert('Form đã được làm mới!');
};

const editHuyen = (id) => {
  alert(`Chỉnh sửa Huyện với ID: ${id} (Đây là hàm placeholder)`);

};

const deleteHuyen = (id) => {
  if (confirm('Bạn có chắc chắn muốn xóa huyện này không?')) {
    alert(`Xóa Huyện với ID: ${id} (Đây là hàm placeholder)`);
    
  }
};
</script>

<style scoped>

h2 {
  text-align: center;
}

</style>