package com.physics.util.pdf;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.physics.util.briefsheet.FreemarkerConfigurer;
import com.physics.util.briefsheet.ITextRendererObjectFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BriefPDF extends BasePDF implements Cloneable {
    private String imagePath;
    private String templateName;
    private String templateRoot;

    /**
     * 简况表pdf构造函数
     * @param path
     *        文件路径(末级文件夹)
     * @param name
     *        文件名（不包含文件类型后缀）
     * @param imagePath
     *        图片路径（没有的话，传null）
     * @param templateName
     *        模板名，与xml配置对应
     * @param templateRoot
     *        相关模板文件根目录
     */
    public BriefPDF(String path, String name, String relativePath, String imagePath,
            String templateName, String templateRoot) {
        super(name, path, relativePath, ".pdf", "pdf");
        this.imagePath = imagePath;
        this.templateName = templateName;
        this.templateRoot = templateRoot;
    }

    @Override
    public void generate(Object dataModel) throws IOException, TemplateException, Exception {
        String htmlString = getHTMLString(dataModel);
        String pdfString = htmlToPdfString(htmlString);
        /*PrintWriter out = new PrintWriter(this.path + File.separator + "filename.html");
        out.println(pdfString);
        out.flush();
        out.close();*/
        this.generate(pdfString);
    }

    private String htmlToPdfString(String htmlString) {
        String str = htmlString/* .replace("楷体_GB2312", "KaiTi")
                                * .replace("font-family:楷体", "font-family:KaiTi")
                                * .replace("font-family:宋体", "font-family:SimSun")
                                * .replace("font-family:黑体", "font-family:SimHei")
                                * .replace("font-family:仿宋", "font-family:FangSong")
                                * .replace("font-family: Simsun", "font-family:SimSun") */;
        return str;
    }

    private String getHTMLString(Object dataModel) throws IOException, TemplateException {
        String htmlContent = null;
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        Configuration config = FreemarkerConfigurer.getConfiguation(this.templateRoot);
        Template tp = config.getTemplate(this.templateName, Locale.CHINA, "UTF-8");
        tp.setEncoding("UTF-8");
        tp.process(dataModel, writer);
        writer.flush();
        htmlContent = stringWriter.toString();
        return htmlContent;
    }

    /**
     * 根据html字符串生成对应的pdf
     * @see http://www.iteye.com/topic/509417?page=6#1279762
     * @param htmlString
     * @throws Exception
     */
    public void generate(String htmlString) throws Exception {
        OutputStream out = null;
        ITextRenderer iTextRenderer = null;
        GenericObjectPool itextRendererObjectPool = null;
        File pdf = this.loadFile();
        try {

            out = new FileOutputStream(pdf);
            itextRendererObjectPool = ITextRendererObjectFactory.getObjectPool(this.templateRoot);
            iTextRenderer = (ITextRenderer) itextRendererObjectPool.borrowObject();//获取对象池中对象,注释中用的是new
            try {
                //iTextRenderer.setDocumentFromString(htmlString);
                iTextRenderer.setDocumentFromString(htmlString, "file:///" + this.imagePath);
                iTextRenderer.layout();
                iTextRenderer.createPDF(out);
            } catch (Exception e) {
                itextRendererObjectPool.invalidateObject(iTextRenderer);
                iTextRenderer = null;
                throw e;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null)
                out.close();
            if (iTextRenderer != null) {
                itextRendererObjectPool.returnObject(iTextRenderer);
            }
        }
    }

    /**
     * 为学科简况表添加版本水印
     * 顺便把页脚的页码加了
     * http://developers.itextpdf.com/examples/stamping-content-existing-pdfs/watermark-examples
     */
    public void markDraft(String version) throws DocumentException, IOException {
        File pdf = this.loadFile();
        //watermark
        PdfReader reader = new PdfReader(new BufferedInputStream(new FileInputStream(pdf)));
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pdf));
        Font f = new Font(FontFamily.HELVETICA, 60);
        Phrase p = new Phrase(version, f), pageNum;
        // transparency
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.1f);
        //遍历
        // properties
        PdfContentByte over;
        Rectangle pagesize;
        float x, y, bottom;
        for (int i = 1, n = reader.getNumberOfPages(); i <= n; i++) {
            pagesize = reader.getPageSize(i);
            x = (pagesize.getLeft() + pagesize.getRight()) / 2;
            y = (pagesize.getTop() + pagesize.getBottom()) / 2;
            bottom = pagesize.getBottom() + 10;
            over = stamper.getOverContent(i);
            over.saveState();
            over.setGState(gs1);
            if (x < y) {
                ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 60);
            } else {
                ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 30);
            }
            over.restoreState();
            over.saveState();
            pageNum = new Phrase(String.valueOf(i));
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, pageNum, x, bottom, 0);
            over.restoreState();
        }
        stamper.close();
        reader.close();
    }

    /**
     * 向生成的简况表后追加pdf
     * @param appends
     * @throws DocumentException
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public BriefPDF append(String[] appends) throws DocumentException, IOException,
            CloneNotSupportedException {
        //要生成一个新的pdf文件，把原pdf合并
        BriefPDF newPdf = (BriefPDF) this.clone();
        newPdf.setName(this.name + "-简况表");
        List<PdfReader> readers = new LinkedList<PdfReader>();
        Document doc = new Document();
        try {
            PdfCopy copy = new PdfCopy(doc, new FileOutputStream(newPdf.loadFile()));
            copy.setMergeFields();
            doc.open();
            //再把其他的追加
            for (String append : appends) {
                PdfReader reader = new PdfReader(append);
                readers.add(reader);
                copy.addDocument(reader);
            }
        } finally {
            doc.close();
            for (PdfReader reader : readers) {
                reader.close();
            }
            readers.clear();
            this.delete();
        }
        return newPdf;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getTemplateRoot() {
        return templateRoot;
    }
}
