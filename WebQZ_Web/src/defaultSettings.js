/**
 * 项目默认配置项
 * primaryColor - 默认主题色
 * navTheme - sidebar theme ['dark', 'light'] 两种主题
 * colorWeak - 色盲模式
 * layout - 整体布局方式 ['sidemenu', 'topmenu'] 两种布局
 * fixedHeader - 固定 Header : boolean
 * fixSiderbar - 固定左侧菜单栏 ： boolean
 * autoHideHeader - 向下滚动时，隐藏 Header : boolean
 * contentWidth - 内容区布局： 流式 |  固定
 *
 * storageOptions: {} - Vue-ls 插件配置项 (localStorage/sessionStorage)
 *
 */

  // key: '薄暮', color: '#F5222D',
  // key: '火山', color: '#FA541C',
  // key: '日暮', color: '#FAAD14',
  // key: '明青', color: '#13C2C2',
  // key: '极光绿', color: '#52C41A',
  // key: '拂晓蓝（默认）', color: '#1890FF',
  // key: '极客蓝', color: '#2F54EB',
  // key: '酱紫', color: '#722ED1',
export default {
  primaryColor: '#FA541C',  // primary color of ant design
  navTheme: 'dark', // theme for nav menu
  layout: 'topmenu', // nav menu position: sidemenu or topmenu
  contentWidth: 'Fixed', // layout of content: Fluid or Fixed, only works when layout is topmenu
  fixedHeader: false, // sticky header
  fixSiderbar: false, // sticky siderbar
  autoHideHeader: false, //  auto hide header
  colorWeak: false,
  multipage: true, //默认多页签模式
  // vue-ls options
  storageOptions: {
    namespace: 'pro__', // key prefix
    name: 'ls', // name variable Vue.[ls] or this.[$ls],
    storage: 'local', // storage name session, local, memory
  }
}