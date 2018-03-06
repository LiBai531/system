package com.physics.util.pdf;

import java.io.IOException;

import com.physics.util.FileOperate;

import freemarker.template.TemplateException;

public class BaseHTML extends BasePDF{

	private String storage;
	public BaseHTML(String path, String name,String storage, String relativePath) {
		super(path, name, relativePath);
		this.extensionName = ".html";
		this.fileType = "html";
		this.storage = storage;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void generate(Object dataModel) throws IOException,
			TemplateException, Exception {
		// TODO Auto-generated method stub
		String content = (String)dataModel;
		FileOperate.writeHtml(this.storage+this.getRelativePath(),this.getName()+this.extensionName, content);
	}

}
