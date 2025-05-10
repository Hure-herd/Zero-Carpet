# ZERO-Carpet-Addition

这是Capet mod的一个附属mod，仅仅支持1.21和1.21.1

# 规则

## 珍珠超传(PortalPearlWarp)
可以在某些特定的位置触发超传。  
下面是地狱门的位置，都是正正或者负负

| 地狱传送门位置（正中央)  |   主世界传送门位置（正中央)    |
|:-------------:|:------------------:|
|    915,915    | 29999600,29999600  |
|   7324,7324   |  3749942,3749942   |
|  58592,58592  |   468735,468735    |
| 468743,468743 |   58585,58585      |
* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet PortalPearlWarp true`
* 分类: `ZERO` , `Feature`

## 末影珍珠加载(Enderpearlloadchunk)
这个末影珍珠加载是从1.21.2移植下来的。十分好用
* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet enderpearlloadchunk true`
* 分类: `ZERO` , `FEATURE`

## 声音抑制移植(Soundsuppression)
将校准幽匿感测体的方块实体数据保留到陷阱箱！！！注意是陷阱箱
* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet soundsuppression true`
* 分类: `ZERO` , `Feature`

## 珍珠加载时间(Pearltime)
这个规则可以控制珍珠在大于20m/gt后多少gt被销毁
* 默认值: `40`
* 可选参数: `40`, `0`
* 开启方法: `/carpet pearltime true`
* 分类: `ZERO` , `FEATURE`

## 更好的TNT合并(MergeTNTPro)
合并大量TNT以减小实体及爆炸带来的卡顿，能显著降低mspt

* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet mergeTNTPro true`
* 分类: `ZERO`, `Feature`, `Survival`,`TNT`

## 禁止蝙蝠生成(DisableBatCanSpawn)
阻止蝙蝠自然生成
* 默认值: `false`
* 可选参数: `true`, `false`
* 开启方法: `/carpet disableBatCanSpawn true`
* 分类: `ZERO` , `Feature`

## 投掷物Raycast长度(ProjectileRaycastLength)
更改Raycast的距离。如果设置为0，将检查所有到达目的地的块。  
这减少了快速移动的延迟。在1.12中该值为200。
* 默认值: `0`
* 可选参数: `0`, `200`
* 开启方法: `/carpet ProjectileRaycastLength 200`
* 分类: `ZERO` , `Survival`