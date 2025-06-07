
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
        props: route => ({ TableName: 'Ap'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Ap/create',
        component:  () => import('../../components/Admin/Ap/Form.vue'),
        props: route => ({ TableName: 'Ap', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Ap/view/:id',
        component:  () => import('../../components/Admin/Ap/Form.vue'),
        props: route => ({ TableName: 'Ap', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Ap/update/:id',
        component:  () => import('../../components/Admin/Ap/Form.vue'),
        props: route => ({ TableName: 'Ap', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: 'Huyen',
        component: import('../../components/Admin/Huyen/index.vue'),
        props: route => ({ TableName: 'Huyen'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Huyen/create',
        component:  () => import('../../components/Admin/Huyen/Form.vue'),
        props: route => ({ TableName: 'Huyen', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Huyen/view/:id',
        component:  () => import('../../components/Admin/Huyen/Form.vue'),
        props: route => ({ TableName: 'Huyen', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Huyen/update/:id',
        component:  () => import('../../components/Admin/Huyen/Form.vue'),
        props: route => ({ TableName: 'Huyen', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: 'LoaiHinhDoanhNghiep',
        component: import('../../components/Admin/LoaiHinhDoanhNghiep/index.vue'),
        props: route => ({ TableName: 'LoaiHinhDoanhNghiep'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'LoaiHinhDoanhNghiep/create',
        component:  () => import('../../components/Admin/LoaiHinhDoanhNghiep/Form.vue'),
        props: route => ({ TableName: 'LoaiHinhDoanhNghiep', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'LoaiHinhDoanhNghiep/view/:id',
        component:  () => import('../../components/Admin/LoaiHinhDoanhNghiep/Form.vue'),
        props: route => ({ TableName: 'LoaiHinhDoanhNghiep', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'LoaiHinhDoanhNghiep/update/:id',
        component:  () => import('../../components/Admin/LoaiHinhDoanhNghiep/Form.vue'),
        props: route => ({ TableName: 'LoaiHinhDoanhNghiep', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: 'LoaiPhieu',
        component: import('../../components/Admin/LoaiPhieu/index.vue'),
        props: route => ({ TableName: 'LoaiPhieu'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'LoaiPhieu/create',
        component:  () => import('../../components/Admin/LoaiPhieu/Form.vue'),
        props: route => ({ TableName: 'LoaiPhieu', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'LoaiPhieu/view/:id',
        component:  () => import('../../components/Admin/LoaiPhieu/Form.vue'),
        props: route => ({ TableName: 'LoaiPhieu', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'LoaiPhieu/update/:id',
        component:  () => import('../../components/Admin/LoaiPhieu/Form.vue'),
        props: route => ({ TableName: 'LoaiPhieu', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: 'LoaiVanBan',
        component: import('../../components/Admin/LoaiVanBan/index.vue'),
        props: route => ({ TableName: 'LoaiVanBan'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'LoaiVanBan/create',
        component:  () => import('../../components/Admin/LoaiVanBan/Form.vue'),
        props: route => ({ TableName: 'LoaiVanBan', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'LoaiVanBan/view/:id',
        component:  () => import('../../components/Admin/LoaiVanBan/Form.vue'),
        props: route => ({ TableName: 'LoaiVanBan', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'LoaiVanBan/update/:id',
        component:  () => import('../../components/Admin/LoaiVanBan/Form.vue'),
        props: route => ({ TableName: 'LoaiVanBan', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: 'QuyMo',
        component: import('../../components/Admin/QuyMo/index.vue'),
        props: route => ({ TableName: 'QuyMo'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'QuyMo/create',
        component:  () => import('../../components/Admin/QuyMo/Form.vue'),
        props: route => ({ TableName: 'QuyMo', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'QuyMo/view/:id',
        component:  () => import('../../components/Admin/QuyMo/Form.vue'),
        props: route => ({ TableName: 'QuyMo', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'QuyMo/update/:id',
        component:  () => import('../../components/Admin/QuyMo/Form.vue'),
        props: route => ({ TableName: 'QuyMo', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: 'SpChanNuoi',
        component: import('../../components/Admin/SpChanNuoi/index.vue'),
        props: route => ({ TableName: 'SpChanNuoi'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'SpChanNuoi/create',
        component:  () => import('../../components/Admin/SpChanNuoi/Form.vue'),
        props: route => ({ TableName: 'SpChanNuoi', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'SpChanNuoi/view/:id',
        component:  () => import('../../components/Admin/SpChanNuoi/Form.vue'),
        props: route => ({ TableName: 'SpChanNuoi', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'SpChanNuoi/update/:id',
        component:  () => import('../../components/Admin/SpChanNuoi/Form.vue'),
        props: route => ({ TableName: 'SpChanNuoi', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: 'Sysdiagrams',
        component: import('../../components/Admin/Sysdiagrams/index.vue'),
        props: route => ({ TableName: 'Sysdiagrams'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Sysdiagrams/create',
        component:  () => import('../../components/Admin/Sysdiagrams/Form.vue'),
        props: route => ({ TableName: 'Sysdiagrams', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Sysdiagrams/view/:id',
        component:  () => import('../../components/Admin/Sysdiagrams/Form.vue'),
        props: route => ({ TableName: 'Sysdiagrams', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Sysdiagrams/update/:id',
        component:  () => import('../../components/Admin/Sysdiagrams/Form.vue'),
        props: route => ({ TableName: 'Sysdiagrams', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: 'Tinh',
        component: import('../../components/Admin/Tinh/index.vue'),
        props: route => ({ TableName: 'Tinh'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Tinh/create',
        component:  () => import('../../components/Admin/Tinh/Form.vue'),
        props: route => ({ TableName: 'Tinh', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Tinh/view/:id',
        component:  () => import('../../components/Admin/Tinh/Form.vue'),
        props: route => ({ TableName: 'Tinh', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Tinh/update/:id',
        component:  () => import('../../components/Admin/Tinh/Form.vue'),
        props: route => ({ TableName: 'Tinh', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: 'Users',
        component: import('../../components/Admin/Users/index.vue'),
        props: route => ({ TableName: 'Users'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Users/create',
        component:  () => import('../../components/Admin/Users/Form.vue'),
        props: route => ({ TableName: 'Users', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Users/view/:id',
        component:  () => import('../../components/Admin/Users/Form.vue'),
        props: route => ({ TableName: 'Users', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Users/update/:id',
        component:  () => import('../../components/Admin/Users/Form.vue'),
        props: route => ({ TableName: 'Users', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: 'VatNuoi',
        component: import('../../components/Admin/VatNuoi/index.vue'),
        props: route => ({ TableName: 'VatNuoi'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'VatNuoi/create',
        component:  () => import('../../components/Admin/VatNuoi/Form.vue'),
        props: route => ({ TableName: 'VatNuoi', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'VatNuoi/view/:id',
        component:  () => import('../../components/Admin/VatNuoi/Form.vue'),
        props: route => ({ TableName: 'VatNuoi', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'VatNuoi/update/:id',
        component:  () => import('../../components/Admin/VatNuoi/Form.vue'),
        props: route => ({ TableName: 'VatNuoi', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
  	{
        path: 'Xa',
        component: import('../../components/Admin/Xa/index.vue'),
        props: route => ({ TableName: 'Xa'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Xa/create',
        component:  () => import('../../components/Admin/Xa/Form.vue'),
        props: route => ({ TableName: 'Xa', action: 'create'}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Xa/view/:id',
        component:  () => import('../../components/Admin/Xa/Form.vue'),
        props: route => ({ TableName: 'Xa', action: 'view', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
    {
        path: 'Xa/update/:id',
        component:  () => import('../../components/Admin/Xa/Form.vue'),
        props: route => ({ TableName: 'Xa', action: 'update', id: route.params.id,}), meta: { requiresAuth: true , requiresRoles : ['ADMIN']}
    },
]
