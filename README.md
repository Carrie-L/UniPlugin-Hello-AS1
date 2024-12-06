# 提供给Uniapp调用的Android原生插件

```js
let UHFModule = uni.requireNativePlugin('UHFModule');

UHFModule.getInstance((res) => {
	if (res.code === 0) { // 连接成功
		console.log('getInstance 成功：', res.data); //返回  'connect'
	} else { // 连接失败
		console.log('getInstance 失败：', res.msg); // 返回 'null'
	}
});
```
