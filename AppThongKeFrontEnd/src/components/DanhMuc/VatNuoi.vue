<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center">Danh Mục Vật Nuôi</h2>

                <form @submit.prevent="saveVatNuoi" class="mb-4">
                    <div class="mb-3">
                        <label for="ten_vat_nuoi" class="form-label">Tên Vật Nuôi</label>
                        <input type="text" v-model="newVatNuoi.ten_vat_nuoi" class="form-control" id="ten_vat_nuoi" required>
                    </div>
                    <div class="mb-3">
                        <label for="ma_vat_nuoi" class="form-label">Mã Vật Nuôi</label>
                        <input type="text" v-model="newVatNuoi.ma_vat_nuoi" class="form-control" id="ma_vat_nuoi" required>
                    </div>
                    <div class="mb-3 form-check">
                        <input type="checkbox" v-model="newVatNuoi.is_nhom_khac" class="form-check-input" id="is_nhom_khac">
                        <label class="form-check-label" for="is_nhom_khac">Là nhóm khác</label>
                    </div>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </form>

                <h3 class="mt-4">Danh Sách Vật Nuôi</h3>
                <table class="table table-bordered mt-3">
                    <thead>
                        <tr>
                            <th scope="col">Tên Vật Nuôi</th>
                            <th scope="col">Mã Vật Nuôi</th>
                            <th scope="col">Là Nhóm Khác</th>
                            <th scope="col">Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="vatnuoi in listVatNuoi" :key="vatnuoi.ma_vat_nuoi">
                            <td>{{ vatnuoi.ten_vat_nuoi }}</td>
                            <td>{{ vatnuoi.ma_vat_nuoi }}</td>
                            <td>{{ vatnuoi.is_nhom_khac ? 'Có' : 'Không' }}</td>
                            <td>
                                <button @click="updateVatNuoi(vatnuoi.ma_vat_nuoi)" class="btn btn-warning btn-sm me-2">Cập Nhật</button>
                                <button @click="deleteVatNuoi(vatnuoi.ma_vat_nuoi)" class="btn btn-danger btn-sm">Xóa</button>
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

// Reactive data for the form to add a new animal type
const newVatNuoi = ref({
    ten_vat_nuoi: '',
    ma_vat_nuoi: '',
    is_nhom_khac: false // Default to false
});

// Sample data for the list of animal types, parsed from your SQL insert statement
const listVatNuoi = ref([
    { ten_vat_nuoi: 'Trâu', ma_vat_nuoi: '0141210', is_nhom_khac: true },
    { ten_vat_nuoi: 'Bò', ma_vat_nuoi: '0141220', is_nhom_khac: true },
    { ten_vat_nuoi: 'Ngựa', ma_vat_nuoi: '0141220', is_nhom_khac: true }, // Duplicate ma_vat_nuoi with Bo, consider if this is intended
    { ten_vat_nuoi: 'Lừa', ma_vat_nuoi: '0142220', is_nhom_khac: true },
    { ten_vat_nuoi: 'La', ma_vat_nuoi: '0142230', is_nhom_khac: true },
    { ten_vat_nuoi: 'Dê', ma_vat_nuoi: '0144210', is_nhom_khac: true },
    { ten_vat_nuoi: 'Cừu', ma_vat_nuoi: '0144220', is_nhom_khac: true },
    { ten_vat_nuoi: 'Hươu', ma_vat_nuoi: '0144230', is_nhom_khac: true },
    { ten_vat_nuoi: 'Nai', ma_vat_nuoi: '0144240', is_nhom_khac: true },
    { ten_vat_nuoi: 'Lợn sữa', ma_vat_nuoi: '0145120', is_nhom_khac: false },
    { ten_vat_nuoi: 'Lợn', ma_vat_nuoi: '0145200', is_nhom_khac: false },
    { ten_vat_nuoi: 'Lợn thịt', ma_vat_nuoi: '0145201', is_nhom_khac: false },
    { ten_vat_nuoi: 'Lợn nái', ma_vat_nuoi: '0145202', is_nhom_khac: false },
    { ten_vat_nuoi: 'Lợn đực giống', ma_vat_nuoi: '0145203', is_nhom_khac: false },
    { ten_vat_nuoi: 'Gà', ma_vat_nuoi: '0146210', is_nhom_khac: false },
    { ten_vat_nuoi: 'Vịt', ma_vat_nuoi: '0146310', is_nhom_khac: false },
    { ten_vat_nuoi: 'Ngan', ma_vat_nuoi: '0146320', is_nhom_khac: false },
    { ten_vat_nuoi: 'Ngỗng', ma_vat_nuoi: '0146330', is_nhom_khac: false }
]);

// Method to handle saving a new animal type
function saveVatNuoi() {
    if (newVatNuoi.value.ten_vat_nuoi.trim() === '' || newVatNuoi.value.ma_vat_nuoi.trim() === '') {
        alert('Tên Vật Nuôi và Mã Vật Nuôi không được để trống!');
        return;
    }

    // Check for duplicate ma_vat_nuoi
    if (listVatNuoi.value.some(item => item.ma_vat_nuoi === newVatNuoi.value.ma_vat_nuoi)) {
        alert('Mã Vật Nuôi đã tồn tại! Vui lòng nhập mã khác.');
        return;
    }

    const newEntry = {
        ten_vat_nuoi: newVatNuoi.value.ten_vat_nuoi,
        ma_vat_nuoi: newVatNuoi.value.ma_vat_nuoi,
        is_nhom_khac: newVatNuoi.value.is_nhom_khac
    };

    listVatNuoi.value.push(newEntry); // Add the new entry to the reactive list
    
    // Clear the form fields after saving
    newVatNuoi.value = { ten_vat_nuoi: '', ma_vat_nuoi: '', is_nhom_khac: false };
    alert('Đã lưu vật nuôi mới!');
}

// Method to handle updating an animal type (placeholder for actual logic)
function updateVatNuoi(ma_vat_nuoi) {
    alert(`Chức năng Cập Nhật cho Mã Vật Nuôi: ${ma_vat_nuoi} sẽ được thực hiện tại đây.`);
    // In a real application, you'd typically:
    // 1. Find the item in listVatNuoi by ma_vat_nuoi.
    // 2. Populate an editing form (e.g., in a modal or a separate page) with this item's data.
    // 3. Allow the user to modify the data.
    // 4. Send a PUT/PATCH request to the backend to save changes.
    // 5. Update the listVatNuoi reactive array after successful update.
}

// Method to handle deleting an animal type
function deleteVatNuoi(ma_vat_nuoi) {
    if (confirm('Bạn có chắc chắn muốn xóa vật nuôi này?')) {
        // Filter out the item with the given ma_vat_nuoi from the list
        listVatNuoi.value = listVatNuoi.value.filter(item => item.ma_vat_nuoi !== ma_vat_nuoi);
        alert('Đã xóa vật nuôi.');
        // In a real application, you'd also send a DELETE request to your backend here.
    }
}

// Placeholder for a logout function, if needed in the future
async function Logout() {
    console.log("Logging out...");
}
</script>

<style scoped>
/* Scoped styles ensure these rules only apply to this component */

/* Base font and background for the main container */
.container-fluid {
    font-family: 'Segoe UI', sans-serif;
    background-color: #f8f9fa;
}

/* Styles for a potential sidebar (not used in this specific template, but provided in your original CSS) */
.sidebar {
    min-height: 100vh;
    background-color: #343a40;
    color: white;
}

.sidebar a {
    color: white;
    text-decoration: none;
}

.sidebar a:hover {
    color: #ffc107;
}

.sidebar .nav-link.active {
    background-color: #495057;
}

/* Styles for a potential header (not used in this specific template, but provided in your original CSS) */
.header {
    background-color: #007bff;
    color: white;
    padding: 10px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.header h1 {
    margin: 0;
}

.logout-btn {
    color: white;
    background-color: #dc3545;
    border: none;
    padding: 5px 15px;
    cursor: pointer;
    border-radius: 5px;
}

.logout-btn:hover {
    background-color: #c82333;
}
</style>