<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center">Danh Mục Loại Phiếu</h2>

                <form @submit.prevent="saveLoaiPhieu" class="mb-4">
                    <div class="mb-3">
                        <label for="ten_phieu" class="form-label">Tên Phiếu</label>
                        <input type="text" v-model="newLoaiPhieu.ten_phieu" class="form-control" id="ten_phieu" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </form>

                <h3 class="mt-4">Danh Sách Loại Phiếu</h3>
                <table class="table table-bordered mt-3">
                    <thead>
                        <tr>
                            <th scope="col">ID Phiếu</th>
                            <th scope="col">Tên Phiếu</th>
                            <th scope="col">Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="loaiphieu in listLoaiPhieu" :key="loaiphieu.id_phieu">
                            <td>{{ loaiphieu.id_phieu }}</td>
                            <td>{{ loaiphieu.ten_phieu }}</td>
                            <td>
                                <button @click="updateLoaiPhieu(loaiphieu.id_phieu)" class="btn btn-warning btn-sm me-2">Cập Nhật</button>
                                <button @click="deleteLoaiPhieu(loaiphieu.id_phieu)" class="btn btn-danger btn-sm">Xóa</button>
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

// Reactive data for the form to add a new phiếu type
const newLoaiPhieu = ref({
    ten_phieu: ''
});

// Sample data for the list of phiếu types, parsed from your SQL insert statement
// We'll generate simple IDs since your SQL only has 'ten_phieu'
const listLoaiPhieu = ref([
    { id_phieu: 1, ten_phieu: 'phiếu số 01' },
    { id_phieu: 2, ten_phieu: 'phiếu số 02' },
    { id_phieu: 3, ten_phieu: 'phiếu số 03' }
]);

// A counter to generate unique IDs for new entries
let nextId = ref(listLoaiPhieu.value.length + 1);

// Method to handle saving a new phiếu type
function saveLoaiPhieu() {
    if (newLoaiPhieu.value.ten_phieu.trim() === '') {
        alert('Tên Phiếu không được để trống!');
        return;
    }

    // Check for duplicate ten_phieu (case-insensitive for better UX)
    if (listLoaiPhieu.value.some(item => item.ten_phieu.toLowerCase() === newLoaiPhieu.value.ten_phieu.toLowerCase())) {
        alert('Tên Phiếu đã tồn tại! Vui lòng nhập tên khác.');
        return;
    }

    const newEntry = {
        id_phieu: nextId.value, // Simple auto-incrementing ID
        ten_phieu: newLoaiPhieu.value.ten_phieu
    };

    listLoaiPhieu.value.push(newEntry); // Add the new entry to the reactive list
    nextId.value++; // Increment ID for next entry
    
    // Clear the form fields after saving
    newLoaiPhieu.value = { ten_phieu: '' };
    alert('Đã lưu loại phiếu mới!');
}

// Method to handle updating a phiếu type (placeholder for actual logic)
function updateLoaiPhieu(id_phieu) {
    alert(`Chức năng Cập Nhật cho ID Phiếu: ${id_phieu} sẽ được thực hiện tại đây.`);
    // In a real application, you'd typically:
    // 1. Find the item in listLoaiPhieu by id_phieu.
    // 2. Populate an editing form (e.g., in a modal or a separate page) with this item's data.
    // 3. Allow the user to modify the data.
    // 4. Send a PUT/PATCH request to the backend to save changes.
    // 5. Update the listLoaiPhieu reactive array after successful update.
}

// Method to handle deleting a phiếu type
function deleteLoaiPhieu(id_phieu) {
    if (confirm('Bạn có chắc chắn muốn xóa loại phiếu này?')) {
        // Filter out the item with the given id_phieu from the list
        listLoaiPhieu.value = listLoaiPhieu.value.filter(item => item.id_phieu !== id_phieu);
        alert('Đã xóa loại phiếu.');
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