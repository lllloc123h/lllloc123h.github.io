
const listDashBoard = [
    "Ap",
    "Huyen",
    "LoaiHinhDoanhNghiep",
    "LoaiPhieu",
    "LoaiVanBan",
    "QuyMo",
    "SpChanNuoi",
    "Sysdiagrams",
    "Tinh",
    "Users",
    "VatNuoi",
    "Xa",
]
export default [
  	{
        path: 'Ap',
        component: import('../../components/Admin/Ap/index.vue'),
        props: route => ({ TableName: 'Ap'})
    },
    {
        path: 'Ap/create',
        component:  () => import('../../components/Admin/Ap/Form.vue'),
        props: route => ({ TableName: 'Ap', action: 'create'})
    },
    {
        path: 'Ap/view/:id',
        component:  () => import('../../components/Admin/Ap/Form.vue'),
        props: route => ({ TableName: 'Ap', action: 'view', id: route.params.id,})
    },
    {
        path: 'Ap/update/:id',
        component:  () => import('../../components/Admin/Ap/Form.vue'),
        props: route => ({ TableName: 'Ap', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Huyen',
        component: import('../../components/Admin/Huyen/index.vue'),
        props: route => ({ TableName: 'Huyen'})
    },
    {
        path: 'Huyen/create',
        component:  () => import('../../components/Admin/Huyen/Form.vue'),
        props: route => ({ TableName: 'Huyen', action: 'create'})
    },
    {
        path: 'Huyen/view/:id',
        component:  () => import('../../components/Admin/Huyen/Form.vue'),
        props: route => ({ TableName: 'Huyen', action: 'view', id: route.params.id,})
    },
    {
        path: 'Huyen/update/:id',
        component:  () => import('../../components/Admin/Huyen/Form.vue'),
        props: route => ({ TableName: 'Huyen', action: 'update', id: route.params.id,})
    },
  	{
        path: 'LoaiHinhDoanhNghiep',
        component: import('../../components/Admin/LoaiHinhDoanhNghiep/index.vue'),
        props: route => ({ TableName: 'LoaiHinhDoanhNghiep'})
    },
    {
        path: 'LoaiHinhDoanhNghiep/create',
        component:  () => import('../../components/Admin/LoaiHinhDoanhNghiep/Form.vue'),
        props: route => ({ TableName: 'LoaiHinhDoanhNghiep', action: 'create'})
    },
    {
        path: 'LoaiHinhDoanhNghiep/view/:id',
        component:  () => import('../../components/Admin/LoaiHinhDoanhNghiep/Form.vue'),
        props: route => ({ TableName: 'LoaiHinhDoanhNghiep', action: 'view', id: route.params.id,})
    },
    {
        path: 'LoaiHinhDoanhNghiep/update/:id',
        component:  () => import('../../components/Admin/LoaiHinhDoanhNghiep/Form.vue'),
        props: route => ({ TableName: 'LoaiHinhDoanhNghiep', action: 'update', id: route.params.id,})
    },
  	{
        path: 'LoaiPhieu',
        component: import('../../components/Admin/LoaiPhieu/index.vue'),
        props: route => ({ TableName: 'LoaiPhieu'})
    },
    {
        path: 'LoaiPhieu/create',
        component:  () => import('../../components/Admin/LoaiPhieu/Form.vue'),
        props: route => ({ TableName: 'LoaiPhieu', action: 'create'})
    },
    {
        path: 'LoaiPhieu/view/:id',
        component:  () => import('../../components/Admin/LoaiPhieu/Form.vue'),
        props: route => ({ TableName: 'LoaiPhieu', action: 'view', id: route.params.id,})
    },
    {
        path: 'LoaiPhieu/update/:id',
        component:  () => import('../../components/Admin/LoaiPhieu/Form.vue'),
        props: route => ({ TableName: 'LoaiPhieu', action: 'update', id: route.params.id,})
    },
  	{
        path: 'LoaiVanBan',
        component: import('../../components/Admin/LoaiVanBan/index.vue'),
        props: route => ({ TableName: 'LoaiVanBan'})
    },
    {
        path: 'LoaiVanBan/create',
        component:  () => import('../../components/Admin/LoaiVanBan/Form.vue'),
        props: route => ({ TableName: 'LoaiVanBan', action: 'create'})
    },
    {
        path: 'LoaiVanBan/view/:id',
        component:  () => import('../../components/Admin/LoaiVanBan/Form.vue'),
        props: route => ({ TableName: 'LoaiVanBan', action: 'view', id: route.params.id,})
    },
    {
        path: 'LoaiVanBan/update/:id',
        component:  () => import('../../components/Admin/LoaiVanBan/Form.vue'),
        props: route => ({ TableName: 'LoaiVanBan', action: 'update', id: route.params.id,})
    },
  	{
        path: 'QuyMo',
        component: import('../../components/Admin/QuyMo/index.vue'),
        props: route => ({ TableName: 'QuyMo'})
    },
    {
        path: 'QuyMo/create',
        component:  () => import('../../components/Admin/QuyMo/Form.vue'),
        props: route => ({ TableName: 'QuyMo', action: 'create'})
    },
    {
        path: 'QuyMo/view/:id',
        component:  () => import('../../components/Admin/QuyMo/Form.vue'),
        props: route => ({ TableName: 'QuyMo', action: 'view', id: route.params.id,})
    },
    {
        path: 'QuyMo/update/:id',
        component:  () => import('../../components/Admin/QuyMo/Form.vue'),
        props: route => ({ TableName: 'QuyMo', action: 'update', id: route.params.id,})
    },
  	{
        path: 'SpChanNuoi',
        component: import('../../components/Admin/SpChanNuoi/index.vue'),
        props: route => ({ TableName: 'SpChanNuoi'})
    },
    {
        path: 'SpChanNuoi/create',
        component:  () => import('../../components/Admin/SpChanNuoi/Form.vue'),
        props: route => ({ TableName: 'SpChanNuoi', action: 'create'})
    },
    {
        path: 'SpChanNuoi/view/:id',
        component:  () => import('../../components/Admin/SpChanNuoi/Form.vue'),
        props: route => ({ TableName: 'SpChanNuoi', action: 'view', id: route.params.id,})
    },
    {
        path: 'SpChanNuoi/update/:id',
        component:  () => import('../../components/Admin/SpChanNuoi/Form.vue'),
        props: route => ({ TableName: 'SpChanNuoi', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Sysdiagrams',
        component: import('../../components/Admin/Sysdiagrams/index.vue'),
        props: route => ({ TableName: 'Sysdiagrams'})
    },
    {
        path: 'Sysdiagrams/create',
        component:  () => import('../../components/Admin/Sysdiagrams/Form.vue'),
        props: route => ({ TableName: 'Sysdiagrams', action: 'create'})
    },
    {
        path: 'Sysdiagrams/view/:id',
        component:  () => import('../../components/Admin/Sysdiagrams/Form.vue'),
        props: route => ({ TableName: 'Sysdiagrams', action: 'view', id: route.params.id,})
    },
    {
        path: 'Sysdiagrams/update/:id',
        component:  () => import('../../components/Admin/Sysdiagrams/Form.vue'),
        props: route => ({ TableName: 'Sysdiagrams', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Tinh',
        component: import('../../components/Admin/Tinh/index.vue'),
        props: route => ({ TableName: 'Tinh'})
    },
    {
        path: 'Tinh/create',
        component:  () => import('../../components/Admin/Tinh/Form.vue'),
        props: route => ({ TableName: 'Tinh', action: 'create'})
    },
    {
        path: 'Tinh/view/:id',
        component:  () => import('../../components/Admin/Tinh/Form.vue'),
        props: route => ({ TableName: 'Tinh', action: 'view', id: route.params.id,})
    },
    {
        path: 'Tinh/update/:id',
        component:  () => import('../../components/Admin/Tinh/Form.vue'),
        props: route => ({ TableName: 'Tinh', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Users',
        component: import('../../components/Admin/Users/index.vue'),
        props: route => ({ TableName: 'Users'})
    },
    {
        path: 'Users/create',
        component:  () => import('../../components/Admin/Users/Form.vue'),
        props: route => ({ TableName: 'Users', action: 'create'})
    },
    {
        path: 'Users/view/:id',
        component:  () => import('../../components/Admin/Users/Form.vue'),
        props: route => ({ TableName: 'Users', action: 'view', id: route.params.id,})
    },
    {
        path: 'Users/update/:id',
        component:  () => import('../../components/Admin/Users/Form.vue'),
        props: route => ({ TableName: 'Users', action: 'update', id: route.params.id,})
    },
  	{
        path: 'VatNuoi',
        component: import('../../components/Admin/VatNuoi/index.vue'),
        props: route => ({ TableName: 'VatNuoi'})
    },
    {
        path: 'VatNuoi/create',
        component:  () => import('../../components/Admin/VatNuoi/Form.vue'),
        props: route => ({ TableName: 'VatNuoi', action: 'create'})
    },
    {
        path: 'VatNuoi/view/:id',
        component:  () => import('../../components/Admin/VatNuoi/Form.vue'),
        props: route => ({ TableName: 'VatNuoi', action: 'view', id: route.params.id,})
    },
    {
        path: 'VatNuoi/update/:id',
        component:  () => import('../../components/Admin/VatNuoi/Form.vue'),
        props: route => ({ TableName: 'VatNuoi', action: 'update', id: route.params.id,})
    },
  	{
        path: 'Xa',
        component: import('../../components/Admin/Xa/index.vue'),
        props: route => ({ TableName: 'Xa'})
    },
    {
        path: 'Xa/create',
        component:  () => import('../../components/Admin/Xa/Form.vue'),
        props: route => ({ TableName: 'Xa', action: 'create'})
    },
    {
        path: 'Xa/view/:id',
        component:  () => import('../../components/Admin/Xa/Form.vue'),
        props: route => ({ TableName: 'Xa', action: 'view', id: route.params.id,})
    },
    {
        path: 'Xa/update/:id',
        component:  () => import('../../components/Admin/Xa/Form.vue'),
        props: route => ({ TableName: 'Xa', action: 'update', id: route.params.id,})
    },
]
