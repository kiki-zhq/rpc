# rpc

学习如何写个微型rpc框架

## 包的分类

| 包名 | 作用
| :-----:| :----:
| annotation | 注解存放位置
| application | 主要处理程序
| mvc |扫描路径以及路径适配
| netty|对服务端的初始化以及与路径适配器结合
| reflection| 反射工具包

###目前会出现问题
######<font color="red">1、在反射下的获取不到方法参数的名称 需要额外设置</font>
######<font color="red">2、类路径与方法路径不能重复 不然会无法适配器</font>

## 目前已将基础功能完善可以正常使用http处理

### 接下来需要处理的问题

#### 1、bean的循环依赖问题(已经解决) 🐶✔️

#### 2、需要实现rpc的过程(已完成) 🐶✔️

#### 3、接入统一返回处理模型
