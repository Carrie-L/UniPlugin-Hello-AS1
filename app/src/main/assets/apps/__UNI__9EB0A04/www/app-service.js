if("undefined"==typeof Promise||Promise.prototype.finally||(Promise.prototype.finally=function(e){const t=this.constructor;return this.then((n=>t.resolve(e()).then((()=>n))),(n=>t.resolve(e()).then((()=>{throw n}))))}),"undefined"!=typeof uni&&uni&&uni.requireGlobal){const e=uni.requireGlobal();ArrayBuffer=e.ArrayBuffer,Int8Array=e.Int8Array,Uint8Array=e.Uint8Array,Uint8ClampedArray=e.Uint8ClampedArray,Int16Array=e.Int16Array,Uint16Array=e.Uint16Array,Int32Array=e.Int32Array,Uint32Array=e.Uint32Array,Float32Array=e.Float32Array,Float64Array=e.Float64Array,BigInt64Array=e.BigInt64Array,BigUint64Array=e.BigUint64Array}uni.restoreGlobal&&uni.restoreGlobal(Vue,weex,plus,setTimeout,clearTimeout,setInterval,clearInterval),function(e){"use strict";function t(e){return weex.requireModule(e)}function n(e,t,...n){uni.__log__?uni.__log__(e,t,...n):console[e].apply(console,[...n,t])}const a=(e,t)=>{const n=e.__vccOpts||e;for(const[a,i]of t)n[a]=i;return n},i=t("modal"),o=t("UHFModule"),s=t("PermissionModule"),r=t("UsbModule");__definePage("pages/index/index",a({data:()=>({loading:!1}),onLoad(){plus.globalEvent.addEventListener("TestEvent",(function(e){i.toast({message:"TestEvent收到："+e.msg,duration:1.5})}))},methods:{testBridge(){this.loading=!0,o.getInstance((e=>{0===e.code?n("log","at pages/index/index.vue:37","getInstance connect ：",e.data):n("log","at pages/index/index.vue:39","getInstance 没有：",e.data)})),o.asyncStartReading((e=>{0===e.code?n("log","at pages/index/index.vue:46","asyncStartReading 成功：",e.data):n("log","at pages/index/index.vue:48","asyncStartReading 失败：",e.msg)})),o.tagInventoryRealTime1((e=>{0===e.code?(n("log","at pages/index/index.vue:54","tagInventoryRealTime1 成功：",e.data),n("log","at pages/index/index.vue:55","tagInventoryRealTime2 成功：",JSON.stringify(e.data))):n("log","at pages/index/index.vue:57","tagInventoryRealTime1 失败：",e.msg)})),o.testReaderErrConversion((e=>{0===e.code?n("log","at pages/index/index.vue:63","testReaderErrConversion 成功：",e.data):n("log","at pages/index/index.vue:65","testReaderErrConversion 失败：",e.msg)})),o.testTempTagInfoConversion((e=>{0===e.code?n("log","at pages/index/index.vue:71","testTempTagInfoConversion 成功：",e.data):n("log","at pages/index/index.vue:73","testTempTagInfoConversion 失败：",e.msg)})),o.testRegionConfConversion((e=>{0===e.code?n("log","at pages/index/index.vue:79","testRegionConfConversion 成功：",e.data):n("log","at pages/index/index.vue:81","testRegionConfConversion 失败：",e.msg)})),n("log","at pages/index/index.vue:86","getHardware同步：",o.getHardware());const e=[3,5,2,4,1];o.Bytes2HexString(e,(e=>{0===e.code?n("log","at pages/index/index.vue:91","十六进制字符串 转换结果：",e.data):n("error","at pages/index/index.vue:93","十六进制字符串 转换失败：",e.msg)})),n("log","at pages/index/index.vue:98","十六进制字符串 转换结果test：",o.Bytes2HexString(e)),n("log","at pages/index/index.vue:101","十六进制字符串 转换结果 3test：",o.Bytes2HexString3(e)),o.uniteBytes(80,120,(e=>{0===e.code?n("log","at pages/index/index.vue:105","合并结果:",e.data):n("log","at pages/index/index.vue:107","合并失败:",e.msg)})),o.getMyTime((e=>{0===e.code?n("log","at pages/index/index.vue:113","getMyTime:",e.data):n("log","at pages/index/index.vue:115","getMyTime失败:",e.msg),this.loading=!1}))},testUSB(){n("log","at pages/index/index.vue:122","UsbModule: ",r),r.hasUsbPermission({vendorId:1234,productId:5678},(e=>{1020===e.code?(uni.showToast({title:"获取USB权限成功"}),n("log","at pages/index/index.vue:131","USB权限：",e)):(uni.showToast({title:"获取USB权限失败："+e.msg}),n("log","at pages/index/index.vue:136","USB权限失败：",e.msg))}))},testPermission(){n("log","at pages/index/index.vue:142","PermissionModule: ",s),s.requestPermission({permission:"android.permission.WRITE_EXTERNAL_STORAGE"},(e=>{1010===e.code?(uni.showToast({title:e.hasPermission?"已有权限":"无权限"}),n("log","at pages/index/index.vue:150","权限成功：",e.hasPermission)):(uni.showToast({title:"检查权限失败"}),n("log","at pages/index/index.vue:155","权限失败：",e.msg))}))},testClass(){let e=plus.android.importClass("com.handheld.uhfr.Reader");n("log","at pages/index/index.vue:162","Reader：",e);let t=e.TEMPTAGINFO,a=plus.android.importClass("com.handheld.uhfr.Reader.TEMPTAGINFO");n("log","at pages/index/index.vue:165","tempTagInfo1：",t),n("log","at pages/index/index.vue:166","tempTagInfo2：",a);const i=plus.android.importClass("cn.pda.serialport.Tools"),o=[3,5,2,4,1];n("log","at pages/index/index.vue:177","Bytes2HexString cls:",i.Bytes2HexString(o,o.length));n("log","at pages/index/index.vue:180","getmyTime cls:",i.getmyTime());n("log","at pages/index/index.vue:183","bytes1 cls:",i.uniteBytes(128,288))}}},[["render",function(t,n,a,i,o,s){return e.openBlock(),e.createElementBlock("div",null,[e.createElementVNode("button",{type:"primary",onClick:n[0]||(n[0]=(...e)=>s.testBridge&&s.testBridge(...e))},"-test UHF方法-"),e.createElementVNode("button",{type:"primary",onClick:n[1]||(n[1]=(...e)=>s.testPermission&&s.testPermission(...e))},"test 读取权限"),e.createElementVNode("button",{type:"primary",onClick:n[2]||(n[2]=(...e)=>s.testUSB&&s.testUSB(...e))},"test USB权限"),e.createElementVNode("button",{type:"primary",onClick:n[3]||(n[3]=(...e)=>s.testClass&&s.testClass(...e))},"test Class")])}]]));(function(t,n){t.mpTye="app",t.render=()=>{};const a=e.createVueApp(t,n);a.provide("__globalStyles",__uniConfig.styles);const i=a.mount;return a.mount=e=>i.call(a,e),a})({onLaunch:function(){n("log","at App.vue:4","App Launch")},onShow:function(){n("log","at App.vue:7","App Show")},onHide:function(){n("log","at App.vue:10","App Hide")}}).mount("#app")}(Vue);