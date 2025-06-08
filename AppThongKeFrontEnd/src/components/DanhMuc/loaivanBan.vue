<template>
    <div class="header">
	    <h1>Chi cục thống kê tỉnh Đồng Tháp</h1>
	    <form @submit.prevent="Logout" method="post">
	        <button type="submit" class="logout-btn">Đăng xuất</button>
	    </form>
	</div>
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
		<nav class="col-md-2 sidebar py-4">
		            <h5 class="text-center mb-4">Danh mục</h5>
		            <ul class="nav flex-column">
		                <li class="nav-item dropdown">
		                    <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">Quản lý</a>
		                    <ul class="dropdown-menu dropdown-menu-dark">
		                        <li><a class="dropdown-item" th:href="@{/Ap}">Ấp</a></li> <li><a class="dropdown-item" th:href="@{/Huyen}">Huyện</a></li>
		                        <li><a class="dropdown-item" th:href="@{/LoaiHinh}">Loại Hình Doanh Nghiệp</a></li>
		                        <li><a class="dropdown-item" th:href="@{/LoaiPhieu}">Loại Phiếu</a></li>
		                        <li><a class="dropdown-item" th:href="@{/LoaiVanBan}">Loại Văn Bản</a></li>
		                        <li><a class="dropdown-item" th:href="@{/SanPham}">Sản Phẩm Chăn Nuôi</a></li>
		                        <li><a class="dropdown-item" th:href="@{/VatNuoi}">Vật Nuôi</a></li>
		                        <li><a class="dropdown-item" th:href="@{/QuyMo}">Quy Mô</a></li>
		                        <li><a class="dropdown-item" th:href="@{/Xa}">Xã</a></li>
		                    </ul>
		                </li>
		            </ul>
		        </nav>

        <!-- Main Content -->
        <main class="col-md-10 ms-sm-auto px-4 pt-4">
            <h2 class="text-center">Danh Mục Loại Văn Bản</h2>

            <!-- Form thêm mới loại văn bản -->
            <form th:action="@{/LoaiVanBan}" th:object="${loaivanban}" method="post">
                <div class="mb-3">
                    <label for="ten_ma_loai_van_ban" class="form-label">Tên Loại Văn Bản</label>
                    <input type="text" th:field="*{ten_ma_loai_van_ban}" class="form-control" id="ten_ma_loai_van_ban" required>
                </div>
                <div class="mb-3">
                    <label for="ghi_chu" class="form-label">Ghi Chú</label>
                    <input type="text" th:field="*{ghi_chu}" class="form-control" id="ghi_chu">
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
                    <tr th:each="loaivanban : ${list}">
                        <td th:text="${loaivanban.ma_loai_van_ban}"></td>
                        <td th:text="${loaivanban.ten_ma_loai_van_ban}"></td>
                        <td th:text="${loaivanban.ghi_chu}"></td>
                        <td>
                            <a th:href="@{/LoaiVanBan/update/{id}(id=${loaivanban.ma_loai_van_ban})}" class="btn btn-warning btn-sm">Cập Nhật</a>
                            <a th:href="@{/LoaiVanBan/delete/{id}(id=${loaivanban.ma_loai_van_ban})}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn muốn xóa?')">Xóa</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </main>
    </div>
</div>
</template>
<script setup>
    async function Logout() {
        
    }
</script>

<style scoped>
template {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f8f9fa;
        }

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
