Whether you're still bothered about Android UI adaptation, don't bother anymore. Interview AOP programming plugins will solve your pain. In order to display the same effect on mobile phones with different resolutions.

## [README of Chinese](https://github.com/lqfGaara/AOPUiAdapter/blob/master/README-CN.md)

#### Usage
1.Under the build. gradle file of the project：

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
2.Add in the build. gradle file of app
```
apply plugin: "com.lqf.gradle"

dependencies {
    implementation 'com.github.lqfGaara:Utils:v1.2'
}
  ```
#### The results are as follows：
 Mobile phone resolution 540*960
>![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/af540960.jpg)
 
 Mobile phone resolution 720*1280
>![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/af7201280.jpg)

 Mobile phone resolution 900*1600
>![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/af9001600.jpg)

 Mobile phone resolution 1080*1920
 >![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/af10801920.jpg)
 #### If not used are as follows:
 Mobile phone resolution 540*960
 >![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/bf540960.jpg)
 
 Mobile phone resolution 720*1280
 >![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/bf540960.jpg)
 
 Mobile phone resolution 900*1600
>![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/bf540960.jpg)

 Mobile phone resolution 1080*1920
>![](https://github.com/lqfGaara/AOPUiAdapter/blob/master/bf540960.jpg)
