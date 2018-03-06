package com.physics.util.briefsheet;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import com.physics.common.exception.PathProvider;

import freemarker.template.Configuration;

public class FreemarkerConfigurer {
    private static Configuration config = null;

    /**
     * 获取配置信息，如果没有定义，则设置配置信息
     * @deprecated
     * @return
     */
    public static synchronized Configuration getConfiguation() {
        if (config == null) {
            setConfiguation();
        }
        return config;
    }

    /**
     * @deprecated
     * 私有函数，设置freemerker的配置信息，包括模板根目录，字符集信息
     */
    private static void setConfiguation() {
        config = new Configuration();
        String path = "";//PathProvider.getTemplateRootPath();
        try {
            config.setDirectoryForTemplateLoading(new File(path));
            config.setLocale(Locale.CHINA);
            config.setDefaultEncoding("utf-8");
            config.setEncoding(Locale.CHINA, "utf-8");
            System.out.println("in set config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Configuration getConfiguation(String templateRoot) {
        if (config == null) {
            config = new Configuration();
            String path = templateRoot;
            try {
                config.setDirectoryForTemplateLoading(new File(path));
                config.setLocale(Locale.CHINA);
                config.setDefaultEncoding("utf-8");
                config.setEncoding(Locale.CHINA, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return config;
    }
}
