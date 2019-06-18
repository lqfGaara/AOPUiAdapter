Whether you're still bothered about Android UI adaptation, don't bother anymore. Interview AOP programming plugins will solve your pain. In order to display the same effect on mobile phones with different resolutions.

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


