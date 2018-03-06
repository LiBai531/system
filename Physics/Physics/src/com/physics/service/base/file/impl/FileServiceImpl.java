package com.physics.service.base.file.impl;

import java.io.IOException;

import com.physics.entity.Attachment;
import com.physics.entity.AttachmentHelper;
import com.physics.entity.enumeration.AttachmentType;
import com.physics.service.base.AttachmentService;
import com.physics.service.base.StoragePathService;
import com.physics.service.base.file.FileService;
import com.physics.util.pdf.BaseHTML;

import freemarker.template.TemplateException;

public class FileServiceImpl implements FileService{

	private StoragePathService storagePathService;
	private AttachmentService attachmentService;
	
	
	public Attachment generateHtml(String unitId, String discId,
			String itemChsName, String userId, String versionId,String content) {
		// TODO Auto-generated method stub
		String path = storagePathService.
				getPath(storagePathService.getActiveDomainPath(), unitId+"/", true);
		String name = unitId+"_"+discId+"_"+itemChsName+"_"+versionId;
		String relativePath = storagePathService.
				getOccasionRelativePath(AttachmentType.HTML, unitId);
		String storage = storagePathService.getActiveRootPath();
		BaseHTML baseHtml = new BaseHTML(path, name,storage,relativePath);
		try {
			baseHtml.generate(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AttachmentHelper attachmentHelper = attachmentService.getBriefHelper(baseHtml, userId, AttachmentType.HTML);
		//return attachmentService.addAttachment(attachmentHelper.getAttachment());
		return attachmentHelper.getAttachment();
	}
	
	public StoragePathService getStoragePathService() {
		return storagePathService;
	}
	public void setStoragePathService(StoragePathService storagePathService) {
		this.storagePathService = storagePathService;
	}
	public AttachmentService getAttachmentService() {
		return attachmentService;
	}
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	

}
