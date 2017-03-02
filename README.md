# android_fmj
Android 版本 伏魔记
![alt_text](https://github.com/artharyoung/android_fmj/blob/master/doc/image.jpg "运行截图") 

## 修改记录 
- 根据 GitHub 上一个 [java 版](https://github.com/artharyoung/fmj_pc)本稍加修改移植到Android平台
- 添加了游戏操作需要的按键，并处理四个方向键的长按事件
- 屏蔽投掷武器，武器与暗器实现类不同，不能转型。暂时未做修改，仅屏蔽处理来解决投掷武器崩溃的bug

## 已知bug
- 暗器投掷，给对面小怪回血？？
- 协同攻击没有伤害
- 钨龙剑群攻时，只有一只小怪时显示两个小怪的伤害提示

## 关于开发与维护
当时看到这个项目的java版本时，一时兴起。打算做个 Android 版本来玩玩，看完部分代码后，感觉用 SurfaceView 来实现不难，于是就移植过来了。
游戏打到钟山后觉得无聊了（可能跟一路手握钨龙剑普攻有关）。项目用来学习交流应该还是不错的。
