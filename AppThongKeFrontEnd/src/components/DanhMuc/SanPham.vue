<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center">Danh Mục Sản Phẩm Chăn Nuôi</h2>

                <form @submit.prevent="saveSanPhamChanNuoi" class="mb-4">
                    <div class="mb-3">
                        <label for="ten_sp_chan_nuoi" class="form-label">Tên Sản Phẩm Chăn Nuôi</label>
                        <input type="text" v-model="newSanPham.ten_sp_chan_nuoi" class="form-control" id="ten_sp_chan_nuoi" required>
                    </div>
                    <div class="mb-3 form-check">
                        <input type="checkbox" v-model="newSanPham.is_gia_cam" class="form-check-input" id="is_gia_cam">
                        <label class="form-check-label" for="is_gia_cam">Là Gia Cầm</label>
                    </div>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </form>

                <h3 class="mt-4">Danh Sách Sản Phẩm Chăn Nuôi</h3>
                <table class="table table-bordered mt-3">
                    <thead>
                        <tr>
                            <th scope="col">ID</th> <th scope="col">Tên Sản Phẩm Chăn Nuôi</th>
                            <th scope="col">Là Gia Cầm</th>
                            <th scope="col">Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="sanpham in listSanPhamChanNuoi" :key="sanpham.id">
                            <td>{{ sanpham.id }}</td>
                            <td>{{ sanpham.ten_sp_chan_nuoi }}</td>
                            <td>{{ sanpham.is_gia_cam ? 'Có' : 'Không' }}</td>
                            <td>
                                <button @click="updateSanPhamChanNuoi(sanpham.id)" class="btn btn-warning btn-sm me-2">Cập Nhật</button>
                                <button @click="deleteSanPhamChanNuoi(sanpham.id)" class="btn btn-danger btn-sm">Xóa</button>
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

// Reactive data for the form to add a new product
const newSanPham = ref({
    ten_sp_chan_nuoi: '',
    is_gia_cam: false // Default to false
});

// Sample data for the list of products, parsed from your SQL insert statement
// We'll generate simple IDs since your SQL only has 'ten_sp_chan_nuoi' and 'is_gia_cam'
const listSanPhamChanNuoi = ref([
    { id: 1, ten_sp_chan_nuoi: 'Sản phẩm chăn nuôi trâu, bò', is_gia_cam: false },
    { id: 2, ten_sp_chan_nuoi: 'Sản phẩm chăn nuôi ngựa, lừa, la', is_gia_cam: true },
    { id: 3, ten_sp_chan_nuoi: 'Sản phẩm chăn nuôi dê, cừu, hươu, nai', is_gia_cam: true },
    { id: 4, ten_sp_chan_nuoi: 'Sản phẩm chăn nuôi lợn', is_gia_cam: false },
    { id: 5, ten_sp_chan_nuoi: 'Sản phẩm chăn nuôi gia cầm', is_gia_cam: true },
    { id: 6, ten_sp_chan_nuoi: 'Sản phẩm chăn nuôi vịt, ngan, ngỗng', is_gia_cam: true },
    { id: 7, ten_sp_chan_nuoi: 'Sản phẩm chăn nuôi gia cầm khác', is_gia_cam: true },
    { id: 8, ten_sp_chan_nuoi: 'Sản phẩm chăn nuôi khác', is_gia_cam: true }
]);

// A counter to generate unique IDs for new entries
let nextId = ref(listSanPhamChanNuoi.value.length + 1);

// Method to handle saving a new product
function saveSanPhamChanNuoi() {
    if (newSanPham.value.ten_sp_chan_nuoi.trim() === '') {
        alert('Tên Sản Phẩm Chăn Nuôi không được để trống!');
        return;
    }

    // Check for duplicate ten_sp_chan_nuoi (case-insensitive for better UX)
    if (listSanPhamChanNuoi.value.some(item => item.ten_sp_chan_nuoi.toLowerCase() === newSanPham.value.ten_sp_chan_nuoi.toLowerCase())) {
        alert('Tên Sản Phẩm Chăn Nuôi đã tồn tại! Vui lòng nhập tên khác.');
        return;
    }

    const newEntry = {
        id: nextId.value, // Simple auto-incrementing ID for frontend display
        ten_sp_chan_nuoi: newSanPham.value.ten_sp_chan_nuoi,
        is_gia_cam: newSanPham.value.is_gia_cam
    };

    listSanPhamChanNuoi.value.push(newEntry); // Add the new entry to the reactive list
    nextId.value++; // Increment ID for next entry
    
    // Clear the form fields after saving
    newSanPham.value = { ten_sp_chan_nuoi: '', is_gia_cam: false };
    alert('Đã lưu sản phẩm chăn nuôi mới!');
}

// Method to handle updating a product (placeholder for actual logic)
function updateSanPhamChanNuoi(id) {
    alert(`Chức năng Cập Nhật cho Sản Phẩm ID: ${id} sẽ được thực hiện tại đây.`);
    // In a real application, you'd typically:
    // 1. Find the item in listSanPhamChanNuoi by ID.
    // 2. Populate an editing form (e.g., in a modal or a separate page) with this item's data.
    // 3. Allow the user to modify the data.
    // 4. Send a PUT/PATCH request to the backend to save changes.
    // 5. Update the listSanPhamChanNuoi reactive array after successful update.
}

// Method to handle deleting a product
function deleteSanPhamChanNuoi(id) {
    if (confirm('Bạn có chắc chắn muốn xóa sản phẩm chăn nuôi này?')) {
        // Filter out the item with the given ID from the list
        listSanPhamChanNuoi.value = listSanPhamChanNuoi.value.filter(item => item.id !== id);
        alert('Đã xóa sản phẩm chăn nuôi.');
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