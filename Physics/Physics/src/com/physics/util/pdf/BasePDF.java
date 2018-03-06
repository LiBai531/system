package com.physics.util.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import freemarker.template.TemplateException;

public abstract class BasePDF implements Cloneable {
    protected String name;
    protected String path;
    protected String relativePath;
    protected String extensionName;
    protected String fileType;

    /**
     * 构造函数
     * @param name
     *        文件名(不包含扩展名，即filePost)
     * @param path
     *        文件完整绝对路径
     * @param relativePath
     *        文件相对路径(形式：Domain/occasion/subPath/)
     * @param extensionName
     *        文件后缀名(例如：.pdf)
     * @param fileType
     *        文件类型(例如：pdf)
     */
    public BasePDF(String name, String path, String relativePath, String filePost, String fileType) {
        super();
        this.extensionName = filePost;
        this.fileType = fileType;
        this.path = path;
        this.name = name;
        this.relativePath = relativePath;
    }

    /**
     * PDF基类的构造函数
     * @param path
     *        文件完整绝对路径
     * @param name
     *        文件名(不包含扩展名)
     * @param relativePath
     *        文件相对路径(形式：Domain/occasion/subPath/)
     */
    public BasePDF(String path, String name, String relativePath) {
        super();
        this.path = path;
        this.name = name;
        this.relativePath = relativePath;
    }

    public abstract void generate(Object dataModel) throws IOException, TemplateException,
            Exception;

    protected File loadFile() throws FileNotFoundException {
        File outputDir = new File(this.path);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        File outputFile = new File(this.path, this.name + this.extensionName);
        return outputFile;
    }

    protected boolean delete() {
        File file = new File(this.path, this.name + this.extensionName);
        return file.delete();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * new File,然后调用getAbsolutePath()
     * @return
     */
    public String getFullPath() {
        return new File(this.path, this.name + this.extensionName).getAbsolutePath();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getFilePost() {
        return extensionName;
    }

    public void setFilePost(String filePost) {
        this.extensionName = filePost;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
