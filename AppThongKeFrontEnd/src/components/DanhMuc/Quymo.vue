<template>
    <div class="container-fluid">
        <div class="row">
            <main class="ms-sm-auto px-4 pt-4">
                <h2 class="text-center">Danh Mục Quy Mô</h2>

                <form @submit.prevent="saveQuyMo" class="mb-4">
                    <div class="mb-3">
                        <label for="id_quy_mo" class="form-label">ID Quy Mô</label>
                        <input type="text" v-model="newQuyMo.id" class="form-control" id="id_quy_mo" required>
                    </div>
                    <div class="mb-3">
                        <label for="quy_mo_name" class="form-label">Tên Quy Mô</label>
                        <input type="text" v-model="newQuyMo.quy_mo_name" class="form-control" id="quy_mo_name" required>
                    </div>
                    <div class="mb-3">
                        <label for="vat_nuoi_id" class="form-label">ID Vật Nuôi (liên kết)</label>
                        <input type="number" v-model.number="newQuyMo.vat_nuoi_id" class="form-control" id="vat_nuoi_id" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </form>

                <h3 class="mt-4">Danh Sách Quy Mô</h3>
                <table class="table table-bordered mt-3">
                    <thead>
                        <tr>
                            <th scope="col">ID Quy Mô</th>
                            <th scope="col">Tên Quy Mô</th>
                            <th scope="col">ID Vật Nuôi</th>
                            <th scope="col">Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="quymo in listQuyMo" :key="quymo.id">
                            <td>{{ quymo.id }}</td>
                            <td>{{ quymo.quy_mo_name }}</td>
                            <td>{{ quymo.vat_nuoi_id }}</td>
                            <td>
                                <button @click="updateQuyMo(quymo.id)" class="btn btn-warning btn-sm me-2">Cập Nhật</button>
                                <button @click="deleteQuyMo(quymo.id)" class="btn btn-danger btn-sm">Xóa</button>
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

// Reactive data for the form to add a new quy mo
const newQuyMo = ref({
    id: '',
    quy_mo_name: '',
    vat_nuoi_id: null // Use null for numbers that might be empty initially
});

// Sample data for the list of quy mo, parsed from your SQL insert statement
const listQuyMo = ref([
    { id: 'BS01', quy_mo_name: 'Nuôi bò sữa từ 1 đến 19 con', vat_nuoi_id: 2 },
    { id: 'BS02', quy_mo_name: 'Nuôi bò sữa từ 20 con trở lên', vat_nuoi_id: 2 },
    { id: 'BT01', quy_mo_name: 'Nuôi bò khác từ 1 đến 29 con', vat_nuoi_id: 2 },
    { id: 'BT02', quy_mo_name: 'Nuôi bò khác từ 30 con trở lên', vat_nuoi_id: 2 },
    { id: 'G01', quy_mo_name: 'Nuôi gà từ 1 đến 49 con', vat_nuoi_id: 15 },
    { id: 'G02', quy_mo_name: 'Nuôi gà từ 50 đến 199 con', vat_nuoi_id: 15 },
    { id: 'G03', quy_mo_name: 'Nuôi gà từ 200 đến 999 con', vat_nuoi_id: 15 },
    { id: 'G04_1', quy_mo_name: 'Nuôi gà thịt từ 1000 đến 3999 con', vat_nuoi_id: 15 },
    { id: 'G04_2', quy_mo_name: 'Nuôi gà đẻ trứng từ 1000 đến 3999 con', vat_nuoi_id: 15 },
    { id: 'G05', quy_mo_name: 'Nuôi gà từ 4000 con trở lên', vat_nuoi_id: 15 },
    { id: 'L01', quy_mo_name: 'Nuôi lợn từ 1 đến 9 con', vat_nuoi_id: 11 },
    { id: 'L02', quy_mo_name: 'Nuôi lợn từ 10 đến 29 con', vat_nuoi_id: 11 },
    { id: 'L03', quy_mo_name: 'Nuôi lợn từ 30 đến 99 con', vat_nuoi_id: 11 },
    { id: 'L04', quy_mo_name: 'Nuôi lợn từ 100 đến 199 con', vat_nuoi_id: 11 }
]);

// Method to handle saving a new quy mo
function saveQuyMo() {
    if (newQuyMo.value.id.trim() === '' || newQuyMo.value.quy_mo_name.trim() === '' || newQuyMo.value.vat_nuoi_id === null) {
        alert('Vui lòng điền đầy đủ tất cả các trường!');
        return;
    }

    // Check for duplicate ID
    if (listQuyMo.value.some(item => item.id === newQuyMo.value.id)) {
        alert('ID Quy Mô đã tồn tại! Vui lòng nhập ID khác.');
        return;
    }

    const newEntry = {
        id: newQuyMo.value.id,
        quy_mo_name: newQuyMo.value.quy_mo_name,
        vat_nuoi_id: newQuyMo.value.vat_nuoi_id
    };

    listQuyMo.value.push(newEntry); // Add the new entry to the reactive list
    
    // Clear the form fields after saving
    newQuyMo.value = { id: '', quy_mo_name: '', vat_nuoi_id: null };
    alert('Đã lưu quy mô mới!');
}

// Method to handle updating a quy mo (placeholder for actual logic)
function updateQuyMo(id) {
    alert(`Chức năng Cập Nhật cho Quy Mô ID: ${id} sẽ được thực hiện tại đây.`);
    // In a real application, you'd typically:
    // 1. Find the item in listQuyMo by ID.
    // 2. Populate an editing form (e.g., in a modal or a separate page) with this item's data.
    // 3. Allow the user to modify the data.
    // 4. Send a PUT/PATCH request to the backend to save changes.
    // 5. Update the listQuyMo reactive array after successful update.
}

// Method to handle deleting a quy mo
function deleteQuyMo(id) {
    if (confirm('Bạn có chắc chắn muốn xóa quy mô này?')) {
        // Filter out the item with the given ID from the list
        listQuyMo.value = listQuyMo.value.filter(item => item.id !== id);
        alert('Đã xóa quy mô.');
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