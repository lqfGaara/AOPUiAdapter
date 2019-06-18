不管你是否还在为Android用户界面的适应而烦恼，不要再烦恼了。访问AOP编程插件将解决您的痛苦。为了在不同分辨率的手机上显示相同的效果。

## [README of English][https://github.com/lqfGaara/AOPUiAdapter/edit/master/README.md]

#### 使用方式
1.在项目的build.gradle中添加：

```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.lqf.plugin:newlib:1.1.7"
  }
}
  ```
2.在app的build.gradle中添加：
```
apply plugin: "com.lqf.gradle"

dependencies {
    implementation 'com.github.lqfGaara:Utils:v1.2'
}
  ```
#### 显示效果如下：
 Mobile phone resolution 540*960
>![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/af540960.jpg)
 
 Mobile phone resolution 720*1280
>![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/af7201280.jpg)

 Mobile phone resolution 900*1600
>![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/af9001600.jpg)

 Mobile phone resolution 1080*1920
 >![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/af10801920.jpg)
 #### 未使用之前的效果:
 Mobile phone resolution 540*960
 >![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/bf540960.jpg)
 
 Mobile phone resolution 720*1280
 >![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/bf540960.jpg)
 
 Mobile phone resolution 900*1600
>![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/bf540960.jpg)

 Mobile phone resolution 1080*1920
>![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/bf540960.jpg)
