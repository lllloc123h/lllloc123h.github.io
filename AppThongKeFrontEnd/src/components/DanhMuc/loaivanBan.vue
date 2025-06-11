<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center">Danh Mục Loại Văn Bản</h2>

                <form @submit.prevent="saveLoaiVanBan" class="mb-4">
                    <div class="mb-3">
                        <label for="ten_ma_loai_van_ban" class="form-label">Tên Loại Văn Bản</label>
                        <input type="text" v-model="newLoaiVanBan.ten_ma_loai_van_ban" class="form-control" id="ten_ma_loai_van_ban" required>
                    </div>
                    <div class="mb-3">
                        <label for="ghi_chu" class="form-label">Ghi Chú</label>
                        <input type="text" v-model="newLoaiVanBan.ghi_chu" class="form-control" id="ghi_chu">
                    </div>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </form>

                <h3 class="mt-4">Danh Sách Loại Văn Bản</h3>
                <table class="table table-bordered mt-3">
                    <thead>
                        <tr>
                            <th scope="col">Mã Loại Văn Bản</th>
                            <th scope="col">Tên Loại Văn Bản</th>
                            <th scope="col">Ghi Chú</th>
                            <th scope="col">Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="loaivanban in listLoaiVanBan" :key="loaivanban.ma_loai_van_ban">
                            <td>{{ loaivanban.ma_loai_van_ban }}</td>
                            <td>{{ loaivanban.ten_ma_loai_van_ban }}</td>
                            <td>{{ loaivanban.ghi_chu }}</td>
                            <td>
                                <button @click="updateLoaiVanBan(loaivanban.ma_loai_van_ban)" class="btn btn-warning btn-sm me-2">Cập Nhật</button>
                                <button @click="deleteLoaiVanBan(loaivanban.ma_loai_van_ban)" class="btn btn-danger btn-sm">Xóa</button>
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

// Reactive data for the form to add a new document type
const newLoaiVanBan = ref({
    ten_ma_loai_van_ban: '',
    ghi_chu: ''
});

// Sample data for the list of document types
// Includes initial data and data from your SQL insert statements
const listLoaiVanBan = ref([
    { ma_loai_van_ban: 'LVB001', ten_ma_loai_van_ban: 'Công Văn', ghi_chu: 'Văn bản hành chính' },
    { ma_loai_van_ban: 'LVB002', ten_ma_loai_van_ban: 'Thông Báo', ghi_chu: 'Văn bản thông báo' },
    { ma_loai_van_ban: 'LVB003', ten_ma_loai_van_ban: 'Quyết Định', ghi_chu: 'Văn bản pháp quy' },
    { ma_loai_van_ban: 'LVB004', ten_ma_loai_van_ban: 'Báo Cáo', ghi_chu: 'Báo cáo định kỳ' },
    { ma_loai_van_ban: 'LVB005', ten_ma_loai_van_ban: 'Phiếu điều tra', ghi_chu: '' },
    { ma_loai_van_ban: 'LVB006', ten_ma_loai_van_ban: 'Phương án điều tra', ghi_chu: '' },
    { ma_loai_van_ban: 'LVB007', ten_ma_loai_van_ban: 'Các phụ lục hướng dẫn', ghi_chu: '' },
    { ma_loai_van_ban: 'LVB008', ten_ma_loai_van_ban: 'Thông báo nghiệp vụ', ghi_chu: '' }
]);

// A counter to generate unique IDs for new entries
// Starts from the current number of items + 1 to ensure unique IDs for new additions
let nextId = ref(listLoaiVanBan.value.length + 1);

// Method to handle saving a new document type
function saveLoaiVanBan() {
    if (newLoaiVanBan.value.ten_ma_loai_van_ban.trim() === '') {
        alert('Tên Loại Văn Bản không được để trống!');
        return;
    }

    const newEntry = {
        ma_loai_van_ban: `LVB${String(nextId.value).padStart(3, '0')}`, // Generate a simple ID
        ten_ma_loai_van_ban: newLoaiVanBan.value.ten_ma_loai_van_ban,
        ghi_chu: newLoaiVanBan.value.ghi_chu
    };

    listLoaiVanBan.value.push(newEntry); // Add the new entry to the reactive list
    nextId.value++; // Increment ID for next entry
    
    // Clear the form fields after saving
    newLoaiVanBan.value = { ten_ma_loai_van_ban: '', ghi_chu: '' };
    alert('Đã lưu loại văn bản mới!');
}

// Method to handle updating a document type (placeholder for actual logic)
function updateLoaiVanBan(id) {
    alert(`Chức năng Cập Nhật cho Mã Loại Văn Bản: ${id} sẽ được thực hiện tại đây.`);
    // In a real application, you'd typically:
    // 1. Fetch the data for the specific ID from the backend.
    // 2. Populate an editing form (e.g., in a modal or a separate page).
    // 3. Allow the user to modify the data.
    // 4. Send a PUT/PATCH request to the backend to save changes.
    // 5. Update the listLoaiVanBan reactive array after successful update.
}

// Method to handle deleting a document type
function deleteLoaiVanBan(id) {
    if (confirm('Bạn có chắc chắn muốn xóa loại văn bản này?')) {
        // Filter out the item with the given ID from the list
        listLoaiVanBan.value = listLoaiVanBan.value.filter(item => item.ma_loai_van_ban !== id);
        alert('Đã xóa loại văn bản.');
        // In a real application, you'd also send a DELETE request to your backend here.
    }
}

// The original Logout function from your provided script block
async function Logout() {
    // Implement your actual logout logic here, e.g.,
    // - Clear user session/token from localStorage
    // - Redirect to login page
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