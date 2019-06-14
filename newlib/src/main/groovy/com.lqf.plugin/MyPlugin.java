package com.lqf.plugin;


import com.android.build.gradle.AppExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;



public  class  MyPlugin  implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        System.out.println("lqfgarra123");
        AppExtension appExtension = (AppExtension) project.getProperties().get("android");
        appExtension.registerTransform(new MyTransform(project));
    }



}