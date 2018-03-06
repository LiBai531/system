package com.physics.util.briefsheet;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.TrueTypeUtil;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

public class ITextRendererObjectFactory extends BasePoolableObjectFactory {
    private static GenericObjectPool itextRendererObjectPool = null;
    private static String            rootPath                = null;

    public ITextRendererObjectFactory() {}

    public ITextRendererObjectFactory(String templateRoot) {
        rootPath = templateRoot;
    }

    @Override
    public Object makeObject() throws Exception {
        ITextRenderer renderer = createTextRenderer();
        return renderer;
    }

    /**
     * 获取对象池,使用对象工厂 后提供性能,能够支持 500线程 迭代10
     * @Title: getObjectPool
     * @Description: 获取对象池
     * @return GenericObjectPool
     */
    public static GenericObjectPool getObjectPool() {
        synchronized (ITextRendererObjectFactory.class) {
            if (itextRendererObjectPool == null) {
                itextRendererObjectPool = new GenericObjectPool(new ITextRendererObjectFactory());
                GenericObjectPool.Config config = new GenericObjectPool.Config();
                config.lifo = false;
                config.maxActive = 15;
                config.maxIdle = 5;
                config.minIdle = 1;
                config.maxWait = 5 * 1000;
                itextRendererObjectPool.setConfig(config);
            }
        }

        return itextRendererObjectPool;
    }

    public static GenericObjectPool getObjectPool(String templateRoot) {
        synchronized (ITextRendererObjectFactory.class) {
            if (itextRendererObjectPool == null) {
                itextRendererObjectPool = new GenericObjectPool(new ITextRendererObjectFactory(
                        templateRoot));
                GenericObjectPool.Config config = new GenericObjectPool.Config();
                config.lifo = false;
                config.maxActive = 15;
                config.maxIdle = 5;
                config.minIdle = 1;
                config.maxWait = 5 * 1000;
                itextRendererObjectPool.setConfig(config);
            }
        }
        return itextRendererObjectPool;
    }

    /**
     * 初始化
     * 
     * @Title: initTextRenderer
     * @Description:
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static synchronized ITextRenderer createTextRenderer() throws DocumentException,
            IOException {
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        addFonts(fontResolver);
        return renderer;
    }

    /**
     * 添加字体
     * 
     * @Title: addFonts
     * @Description:
     * @param fontResolver
     * @throws DocumentException
     * @throws IOException
     */
    public static ITextFontResolver addFonts(ITextFontResolver fontResolver)
            throws DocumentException, IOException {
        File fontsDir = new File(rootPath, "fonts");
        if (fontsDir != null && fontsDir.isDirectory()) {
            File[] files = fontsDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f == null || f.isDirectory()) {
                    break;
                }
                String fontPath = f.getAbsolutePath();//.toLowerCase();
                String lower = fontPath.toLowerCase();
                BaseFont font;//为了显示字体信息
                if (lower.endsWith(".ttf") || lower.endsWith(".ttc") || lower.indexOf(".ttc,") != -1) {
                    fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    /*if (fontPath.substring(fontPath.lastIndexOf(".") + 1).equals("ttc")) {
                        font = BaseFont.createFont(fontPath + ",0", BaseFont.IDENTITY_H,
                                BaseFont.EMBEDDED);
                    } else {
                        font = BaseFont
                                .createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    }
                    String[] fontFamilyName = TrueTypeUtil.getFamilyNames(font);
                    System.out.printf("%s : %s\n", f.getPath(), Arrays.toString(fontFamilyName));*/
                }
            }
        }
        return fontResolver;
    }
}
