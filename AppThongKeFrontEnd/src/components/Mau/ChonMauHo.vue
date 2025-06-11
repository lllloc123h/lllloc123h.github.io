<template>
  <div class="bang-ke-ho-tong-hop-page">
    <div class="header-section">
      <h2 class="page-title">Chọn mẫu Hộ cho tất cả quy mô và loại vật nuôi</h2>
      <div class="action-buttons">
        <button class="btn btn-primary" @click="chonLaiMau">Chọn lại mẫu</button>
      </div>
    </div>

    <div class="filter-section">
      <div class="filter-row">
        <div class="filter-group">
          <label for="kyDieuTra">Kỳ điều tra</label>
          <select id="kyDieuTra" v-model="filters.kyDieuTra">
            <option value="">-- Chọn kỳ --</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
          </select>
        </div>
        <div class="filter-group">
          <label for="nam">Năm</label>
          <select id="nam" v-model="filters.nam">
            <option value="">-- Chọn năm --</option>
            <option value="2025">2025</option>
            <option value="2024">2024</option>
            <option value="2023">2023</option>
          </select>
        </div>
        <div class="filter-group">
          <label for="tinhTP">Tỉnh/TP</label>
          <select id="tinhTP" v-model="filters.tinhTP">
            <option value="">-- Chọn Tỉnh/TP --</option>
            <option value="87-DongThap">87- Đồng Tháp</option>
            <option value="92-CanTho">92- Cần Thơ</option>
            <option value="89-AnGiang">89- An Giang</option>
            <option value="91-KienGiang">91- Kiên Giang</option>
          </select>
        </div>
        <div class="filter-group">
          <label for="quanHuyen">Quận/Huyện</label>
          <select id="quanHuyen" v-model="filters.quanHuyen">
            <option value="">-- Chọn Quận/Huyện --</option>
            <option value="870-HongNgu">870-Hồng Ngự</option>
            <option value="871-CaoLanh">871-Cao Lãnh</option>
          </select>
        </div>
        <div class="filter-group">
          <label for="xaPhuong">Xã/Phường</label>
          <select id="xaPhuong" v-model="filters.xaPhuong">
            <option value="">-- Chọn Xã/Phường --</option>
            <option value="29962-LongKhanhA">29962-Xã Long Khánh A</option>
            <option value="29963-LongKhanhB">29963-Xã Long Khánh B</option>
            <option value="29956-ThoiThuan">29956-Xã Thới Thuận</option>
          </select>
        </div>
        <div class="filter-group">
          <label for="thon">Thôn</label>
          <select id="thon" v-model="filters.thon">
            <option value="">-- Chọn Thôn --</option>
            <option value="01-Ap1">01-Ấp 1</option>
            <option value="02-Ap2">02-Ấp 2</option>
            <option value="03-Ap3">03-Ấp 3</option>
            <option value="05-Ap5">05-Ấp 5</option>
          </select>
        </div>
        <div class="filter-group">
          <label for="trangThai">Trạng thái</label>
          <select id="trangThai" v-model="filters.trangThai">
            <option value="">Không chọn</option>
            <option value="daduyet">Đã duyệt</option>
            <option value="choduyet">Chờ duyệt</option>
            <option value="tuchoi">Từ chối</option>
          </select>
        </div>
      </div>
      <div class="filter-buttons">
        <button class="btn btn-secondary" @click="fetchTableData">Xem</button>
        <button class="btn btn-info" @click="exportExcel">Xuất Excel</button>
      </div>
    </div>

    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th rowspan="2">STT Hộ</th>
            <th rowspan="2">Mã Tỉnh</th>
            <th rowspan="2">Mã Huyện</th>
            <th rowspan="2">Mã Xã</th>
            <th rowspan="2">Mã Thôn</th>
            <th rowspan="2">Tên Thôn</th>
            <th rowspan="2">Họ và tên chủ hộ</th>
            <th rowspan="2">Điện thoại</th>
            <th colspan="3">Hộ nuôi lợn</th>
            <th colspan="5">Hộ nuôi gà</th>
            <th colspan="3">Hộ nuôi vịt</th>
            <th colspan="3">Hộ nuôi ngan/ngỗng</th>
            <th rowspan="2">Hộ nuôi trâu/bò từ 30 con trở lên</th>
            <th rowspan="2">Hộ nuôi dê/cừu từ 30 con trở lên</th>
            <th rowspan="2">Hộ nuôi vật nuôi khác</th>
          </tr>
          <tr>
            <th>Từ 10 đến 99 con</th>
            <th>Từ 100 đến 299 con</th>
            <th>Từ 300 con trở lên</th>
            <th>Từ 50 đến 199 con</th>
            <th>Từ 200 đến 499 con</th>
            <th>Từ 500 đến 999 con</th>
            <th>Từ 1000 đến 3999 con</th>
            <th>Từ 4000 con trở lên</th>
            <th>Từ 50 đến 199 con</th>
            <th>Từ 200 đến 499 con</th>
            <th>Từ 500 con trở lên</th>
            <th>Từ 50 đến 199 con</th>
            <th>Từ 200 đến 499 con</th>
            <th>Từ 500 con trở lên</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading" class="loading-row">
            <td :colspan="25">Đang tải dữ liệu...</td>
          </tr>
          <tr v-else-if="filteredData.length === 0" class="no-data-row">
            <td :colspan="25">Không có dữ liệu phù hợp.</td>
          </tr>
          <tr v-else v-for="(item, index) in filteredData" :key="index">
            <td>{{ item.sttHo }}</td>
            <td>{{ item.maTinh }}</td>
            <td>{{ item.maHuyen }}</td>
            <td>{{ item.maXa }}</td>
            <td>{{ item.maThon }}</td>
            <td>{{ item.tenThon }}</td>
            <td>{{ item.hoVaTenChuHo }}</td>
            <td>{{ item.dienThoai }}</td>
            <td class="center-x">{{ item.lon_10_99 ? 'x' : '' }}</td>
            <td class="center-x">{{ item.lon_100_299 ? 'x' : '' }}</td>
            <td class="center-x">{{ item.lon_300_Tren ? 'x' : '' }}</td>
            <td class="center-x">{{ item.ga_50_199 ? 'x' : '' }}</td>
            <td class="center-x">{{ item.ga_200_499 ? 'x' : '' }}</td>
            <td class="center-x">{{ item.ga_500_999 ? 'x' : '' }}</td>
            <td class="center-x">{{ item.ga_1000_3999 ? 'x' : '' }}</td>
            <td class="center-x">{{ item.ga_4000_Tren ? 'x' : '' }}</td>
            <td class="center-x">{{ item.vit_50_199 ? 'x' : '' }}</td>
            <td class="center-x">{{ item.vit_200_499 ? 'x' : '' }}</td>
            <td class="center-x">{{ item.vit_500_Tren ? 'x' : '' }}</td>
            <td class="center-x">{{ item.ngan_50_199 ? 'x' : '' }}</td>
            <td class="center-x">{{ item.ngan_200_499 ? 'x' : '' }}</td>
            <td class="center-x">{{ item.ngan_500_Tren ? 'x' : '' }}</td>
            <td class="center-x">{{ item.trauBo_30_Tren ? 'x' : '' }}</td>
            <td class="center-x">{{ item.deCuu_30_Tren ? 'x' : '' }}</td>
            <td class="center-x">{{ item.vatNuoiKhac ? 'x' : '' }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';

const filters = ref({
  kyDieuTra: '1',
  nam: '2025',
  tinhTP: '87-DongThap',
  quanHuyen: '870-HongNgu',
  xaPhuong: '29962-LongKhanhA',
  thon: '',
  trangThai: '',
});

const data = ref([]);
const loading = ref(true);
const filteredData = ref([]);

const fetchTableData = async () => {
  loading.value = true;
  await new Promise(resolve => setTimeout(resolve, 500)); // Simulate API call delay

  // Dữ liệu giả định dựa trên hình ảnh image_ac6a0f.png và các cấu trúc trước đó
  const mockData = [
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '01', sttHo: '0028', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Nguyễn Thị Nhanh', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0029', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Đặng Hoàng Phượng', dienThoai: '',
      lon_10_99: true, lon_100_299: false, lon_300_Tren: false, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0030', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Phạm Trung Ưng', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0031', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Đoàn Văn Buổi', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0032', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Nguyễn Thị Nhung', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '03', sttHo: '0001', tenThon: 'Ấp Hòa Trung', hoVaTenChuHo: 'Phạm Thị Dũ', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: true, ga_4000_Tren: false, // Gà (lớn)
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '03', sttHo: '0003', tenThon: 'Ấp Hòa Trung', hoVaTenChuHo: 'Lê Thành Lộc', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0001', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Vũ Văn Đức', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: true, ga_4000_Tren: false, // Gà (lớn)
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0002', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Nguyễn Văn Tùng', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0003', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Trần Hoài Quân', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0004', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Nguyễn Bá Tuân', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0005', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Lê Thanh Thơ', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0006', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Nguyễn Thị Bích Phượng', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0007', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Nguyễn Minh Phượng', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0008', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Phạm Thị Kim Phà', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0009', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Lê Phước Bình', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0010', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Nguyễn Văn Lực', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0011', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Nguyễn Ngọc Thụy', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '05', sttHo: '0012', tenThon: 'Ấp Thới Bình', hoVaTenChuHo: 'Phạm Văn Thuận', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: true, // Lợn
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false, // Gà
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '01', sttHo: '0001', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Trần Văn Tiển', dienThoai: '',
      lon_10_99: true, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '01', sttHo: '0002', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Lý Văn Chiến', dienThoai: '',
      lon_10_99: true, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '01', sttHo: '0003', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Huỳnh Văn Xuyên', dienThoai: '',
      lon_10_99: true, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '01', sttHo: '0004', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Vũ Văn Hai', dienThoai: '',
      lon_10_99: true, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '01', sttHo: '0005', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Hồ Văn Hiển', dienThoai: '',
      lon_10_99: true, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0006', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Bùi Thị Doi', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0007', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Phạm Văn Bồng', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0008', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Nguyễn Văn Triệu', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0009', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Nguyễn Văn Lắm', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29962, maThon: '02', sttHo: '0010', tenThon: 'Ấp Long Khánh A', hoVaTenChuHo: 'Lê Văn Phương', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    // Dữ liệu cho xã Thới Thuận (29956)
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0001', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Trần Văn Tiển', dienThoai: '',
      lon_10_99: true, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0002', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Lý Văn Chiến', dienThoai: '',
      lon_100_299: true, lon_10_99: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0003', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Huỳnh Văn Xuyên', dienThoai: '',
      lon_10_99: true, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0004', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Vũ Văn Hải', dienThoai: '',
      lon_10_99: true, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0005', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Lê Văn Hạnh', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0006', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Lê Thị Nga', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0007', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Huỳnh Hùng Cường', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0008', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Lý Văn Sú', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0009', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Lâm Văn Phụng', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0010', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Lê Thị Trang', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0011', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Hồ Văn Phục', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0012', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Nguyễn Thị Phượng', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0013', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Phạm Văn Thầu', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0014', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Huỳnh Tiết Xuân', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0015', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Lý Thị Huỳnh Anh', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0016', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Nguyễn Thị Mỹ', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0017', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Phạm Thị Thúy', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0018', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Trần Văn Phượng', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0019', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Tô Thị Định', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0020', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Phạm Thị Vàng', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0021', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Nguyễn Văn Huyển', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0022', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Lê Thị Trang', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0023', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Huỳnh Văn Minh', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 4, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29956, maThon: '01', sttHo: '0024', tenThon: 'Ấp Bình Hòa Thượng', hoVaTenChuHo: 'Nguyễn Văn Minh', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: true, trangThai: 'daduyet'
    },
    // Thêm các dòng khác nếu cần, đảm bảo khớp với các bộ lọc
    // Ví dụ cho Xã Long Khánh B (29963-LongKhanhB) và Thôn 01-Ap1, 02-Ap2, 03-Ap3
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '01', sttHo: '0001', tenThon: 'Ấp 1', hoVaTenChuHo: 'Nguyễn Thị Ba Ba', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: false,
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: true, // Ngan/Ngỗng
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '02', sttHo: '0010', tenThon: 'Ấp 2', hoVaTenChuHo: 'Lê Văn Lợi', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: true, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '03', sttHo: '0008', tenThon: 'Ấp 3', hoVaTenChuHo: 'Lê Văn Sang', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: true, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
    {
      kyDieuTra: 1, nam: 2025, maTinh: 87, maHuyen: 870, maXa: 29963, maThon: '03', sttHo: '0009', tenThon: 'Ấp 3', hoVaTenChuHo: 'Ngô Văn Khiêm', dienThoai: '',
      lon_10_99: false, lon_100_299: false, lon_300_Tren: false,
      ga_50_199: false, ga_200_499: false, ga_500_999: false, ga_1000_3999: false, ga_4000_Tren: false,
      vit_50_199: false, vit_200_499: false, vit_500_Tren: true, // Vịt
      ngan_50_199: false, ngan_200_499: false, ngan_500_Tren: false,
      trauBo_30_Tren: false, deCuu_30_Tren: false, vatNuoiKhac: false, trangThai: 'daduyet'
    },
  ];

  data.value = mockData;
  loading.value = false;
  applyFilters(); // Áp dụng bộ lọc ngay sau khi tải dữ liệu ban đầu
};

const applyFilters = () => {
  let tempFilteredData = data.value.filter(item => {
    const matchesKyDieuTra = filters.value.kyDieuTra === '' || String(filters.value.kyDieuTra) === String(item.kyDieuTra);
    const matchesNam = filters.value.nam === '' || String(filters.value.nam) === String(item.nam);
    const matchesTinhTP = filters.value.tinhTP === '' || filters.value.tinhTP.split('-')[0] === String(item.maTinh);
    const matchesQuanHuyen = filters.value.quanHuyen === '' || filters.value.quanHuyen.split('-')[0] === String(item.maHuyen);
    const matchesXaPhuong = filters.value.xaPhuong === '' || filters.value.xaPhuong.split('-')[0] === String(item.maXa);
    const matchesThon = filters.value.thon === '' || filters.value.thon.split('-')[0] === String(item.maThon);
    const matchesTrangThai = filters.value.trangThai === '' || filters.value.trangThai === item.trangThai;

    // Logic lọc theo loại vật nuôi (nếu có chọn)
    let matchesLoaiVatNuoi = true;
    // Không có bộ lọc "Loại vật nuôi" trong hình ảnh, tôi đã bỏ nó khỏi filters và logic lọc.
    // Nếu bạn muốn thêm vào, vui lòng cho biết các giá trị cụ thể của bộ lọc đó.

    return matchesKyDieuTra && matchesNam && matchesTinhTP && matchesQuanHuyen && matchesXaPhuong && matchesThon && matchesTrangThai;
  });
  filteredData.value = tempFilteredData;
};

onMounted(() => {
  fetchTableData();
});

// Watch filters to apply filtering automatically
watch(filters, () => {
  applyFilters();
}, { deep: true });

const chonLaiMau = () => {
  alert('Chức năng "Chọn lại mẫu"');
  // Thực hiện logic chọn lại mẫu ở đây
  // Ví dụ: reset bộ lọc và tải lại dữ liệu ban đầu
  // filters.value = { ...defaultFilters };
  // fetchTableData();
};

const exportExcel = () => {
  alert('Chức năng "Xuất Excel"');
  // Thực hiện logic xuất Excel ở đây
};
</script>

<style scoped>
.bang-ke-ho-tong-hop-page {
  padding: 20px;
  background-color: #f0f2f5;
  font-family: Arial, sans-serif;
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e0e0e0;
}

.page-title {
  color: #000080; /* Blue color from image */
  margin: 0;
  font-size: 18px;
  font-weight: bold;
  display: flex;
  align-items: center;
}

.page-title::before {
  content: '🌍'; /* Globe icon */
  margin-right: 10px;
  font-size: 20px;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.btn {
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  color: white;
  transition: background-color 0.2s ease;
}

.btn-primary {
  background-color: #007bff; /* Blue */
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-secondary {
  background-color: #6c757d; /* Grey */
}

.btn-secondary:hover {
  background-color: #5a6268;
}

.btn-info {
  background-color: #17a2b8; /* Cyan */
}

.btn-info:hover {
  background-color: #138496;
}

/* Filter Section */
.filter-section {
  background-color: #ffffff;
  padding: 15px 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  margin-bottom: 20px;
}

.filter-row {
  display: flex;
  flex-wrap: wrap;
  gap: 15px 25px; /* Khoảng cách giữa các nhóm lọc */
  align-items: flex-end;
  margin-bottom: 15px;
}

.filter-group {
  display: flex;
  flex-direction: column;
  min-width: 120px;
}

.filter-group label {
  font-size: 13px;
  color: #555;
  margin-bottom: 5px;
  font-weight: bold;
}

.filter-group select {
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 13px;
  background-color: white;
  min-width: 150px; /* Adjust width as needed */
  height: 30px; /* Match height of other inputs/buttons */
}

.filter-buttons {
  display: flex;
  gap: 10px;
  justify-content: flex-start; /* Align buttons to the left */
}

/* Table Section */
.table-container {
  overflow-x: auto; /* Cho phép cuộn ngang nếu bảng quá rộng */
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.data-table {
  width: 100%;
  border-collapse: collapse;
  min-width: 1500px; /* Đặt chiều rộng tối thiểu để chứa tất cả các cột */
}

.data-table th, .data-table td {
  border: 1px solid #c0c0c0; /* Màu border giống hình ảnh */
  padding: 8px 10px;
  text-align: left;
  font-size: 12px;
  white-space: nowrap; /* Ngăn không cho chữ xuống dòng */
}

.data-table thead th {
  background-color: #4682B4; /* SteelBlue - màu xanh đậm từ ảnh */
  color: white;
  font-weight: bold;
  text-align: center;
}

.data-table thead th[rowspan="2"] {
  vertical-align: middle; /* Căn giữa theo chiều dọc cho tiêu đề rowspan */
}

.data-table tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}

.data-table tbody tr:hover {
  background-color: #e6f0ff;
}

.center-x {
  text-align: center;
  font-weight: bold;
}

.loading-row td, .no-data-row td {
  text-align: center;
  font-style: italic;
  color: #777;
  padding: 20px;
}
</style>