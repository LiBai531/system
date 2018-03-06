package com.physics.service.base.impl;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.physics.dao.base.AttachmentDao;
import com.physics.dao.base.StorageDao;
import com.physics.entity.AttachmentHelper;
import com.physics.entity.Attachment;
import com.physics.entity.Storage;
import com.physics.entity.enumeration.PhysicsEnum;
import com.physics.service.base.AttachmentService;
import com.physics.util.FileOperate;
import com.physics.util.GUID;
import com.physics.util.pdf.BasePDF;
import com.physics.vm.PageVM;

public class AttachmentServiceImpl implements AttachmentService{
	
	private StorageDao storageDao;
	private AttachmentDao attachmentDao;

	public StorageDao getStorageDao() {
		return storageDao;
	}

	public void setStorageDao(StorageDao storageDao) {
		this.storageDao = storageDao;
	} 
	
	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	
	
	public AttachmentHelper getAttachmentHelper(MultipartFile file, String uploader,
			PhysicsEnum occasion) {
		
		AttachmentHelper ah = new AttachmentHelper();
		
		String id = GUID.get();
		//生成新的文件名
		String originalName = file.getOriginalFilename();
		String storageName = getProcessedFileName(originalName, id);
		if("TEMPLATE".equals(occasion.getShowing())){
			storageName = originalName;
		}
		
		//生成文件路径（相对路径）
		
		String relativePath=null;
		if("TEMPLATE".equals(occasion.getShowing())){
			relativePath =  occasion.getShowing() + "/";
		}else{
			relativePath =  occasion.getShowing()  + "/";
		}
		//读取根目录路径
		Storage storage = storageDao.getActiveStorage();
		String rootPath = getStoragePath(storage);//根目录信息
		
		//获取存储位置信息
		String path = rootPath + relativePath;
		
		Attachment attachment = new Attachment();
		attachment.setId(id);
		attachment.setName(originalName);
		attachment.setOccasion(occasion.getStatus());
		attachment.setPath(relativePath+storageName);
		attachment.setType(file.getContentType());
		attachment.setUploader(uploader);
		attachment.setDate((new Date()).toString());
		attachment.setStorage(storage);
		
		ah.setAttachment(attachment);
		ah.setPath(path);
		ah.setStorageName(storageName);

		return ah;
	}
	
	
	
	public AttachmentHelper getAttachmentHelper(MultipartFile file, String uploader,
			PhysicsEnum occasion,String keyValue) {
		
		AttachmentHelper ah = new AttachmentHelper();
		
		String id = GUID.get();
		//生成新的文件名
		String originalName = file.getOriginalFilename();
		String storageName = null;
		storageName = getProcessedFileName(originalName, id);
		if("TEMPLATE".equals(occasion.getShowing())){
			storageName = originalName;
		}
		String flag = "homework_";
		

		//完整文件名称
		storageName = flag + storageName;
		
		
		//生成文件路径（相对路径）
		
		String relativePath = null;
		
		if("TEMPLATE".equals(occasion.getShowing())){
			relativePath =occasion.getShowing() + "/";
		}else{
			relativePath =  occasion.getShowing() + "/";
		}
		//读取根目录路径
		
		Storage storage = new Storage();
		if (!occasion.getStatus().equals("1")) {
			storage =	storageDao.getStorageById(occasion.getStatus());
		} else {
			storage =	storageDao.getActiveStorage();
		}
		
		String rootPath = getStoragePath(storage);//根目录信息
		
		//获取存储位置信息
		String path = rootPath + relativePath;
		
		Attachment attachment = new Attachment();
		attachment.setId(id);
		attachment.setName(originalName);
		attachment.setOccasion(occasion.getStatus());
		attachment.setPath(relativePath+storageName);
		attachment.setType(file.getContentType());
		attachment.setUploader(uploader);
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
	    String s = format1.format(new Date());
		attachment.setDate(s);
		attachment.setStorage(storage);
		
		ah.setAttachment(attachment);
		ah.setPath(path);
		ah.setStorageName(storageName);

		return ah;
	}
	
	
	public AttachmentHelper getQuestionnairHelper(String questionnairId, String uploader, PhysicsEnum occasion) {
		String id = GUID.get();
		//生成新的文件名
		String originalName = questionnairId;
		String storageName = questionnairId;
		//生成文件路径（相对路径）
		String unitId = "center";
		String relativePath=null;
		if("TEMPLATE".equals(occasion.getShowing())){
			relativePath = occasion.getShowing() + "/";
		}else{
			relativePath = occasion.getShowing() + "/" + unitId + "/";
		}
		//读取根目录路径
		Storage storage = storageDao.getActiveStorage();
		String rootPath = getStoragePath(storage);//根目录信息	
		//获取存储位置信息
		String path = rootPath + relativePath;
		
		Attachment attachment = new Attachment();
		attachment.setId(id);
		attachment.setName(originalName);
		attachment.setOccasion(occasion.getStatus());
		attachment.setPath(relativePath+storageName);
		attachment.setType("jsp");//???
		attachment.setUploader(uploader);
		attachment.setDate((new Date()).toString());
		attachment.setStorage(storage);
		
		AttachmentHelper ah = new AttachmentHelper();
		ah.setAttachment(attachment);
		ah.setPath(path);
		ah.setStorageName(storageName);
		return ah;
	}
	
	//如果协议为local，则返回本地路径，否则返回web路径
	private String getStoragePath(Storage storage){
		if(storage.getProtocol().equals("local"))
			return storage.getPath();
		else
			return storage.getWebUrl();
	}
	
	
	public String addAttachment(Attachment attachment) {
		String pk = attachmentDao.save(attachment);
		return pk;
	}
	
	
	public String getAttachmentPath(String id) {
		Attachment attachment = attachmentDao.get(id);
		if(attachment != null){
			String CName = attachment.getName();
			String relativePath = attachment.getPath();
			String rootPath = getStoragePath(attachment.getStorage());
			String path = rootPath + relativePath;
			return path;
		}else{
			return null;
		}
	}
	
	public String getAttachmentPathName(String id){
		Attachment attachment = attachmentDao.get(id);
		if(attachment != null){
			String CName = attachment.getName();
			return CName;
		}else{
			return null;
		}
	}
	
	
	public String getAttachmentPath(String id, String storageId) {
		Attachment attachment = attachmentDao.get(id);
		String relativePath = attachment.getPath();
		String rootPath = getStoragePath(storageDao.getStorageById(storageId));
		String path = rootPath + relativePath;
		return path;
	}
	
	
	public Attachment getAttachment(String id) {
		return attachmentDao.get(id);
	}
	
	
	public void deleteAttachment(String id) {
		if(id != null){
			attachmentDao.deleteByKey(id);
		}
	}
	
	/**
	 * 获取处理后的文件名
	 * @param formerFileName 原文件名，包含扩展名
	 * @param userString 需要在原文件名后添加的后缀字符串
	 * @return
	 */
	private static String getProcessedFileName(String formerFileName,String userString){
		return FileOperate.getProcessedFileName(formerFileName, userString);
	}
	
	public PageVM<Attachment> getAttachPageVMByOccassion(String occassion,
			String orderName,int pageIndex,
			int pageSize,Boolean asc){
		List<Attachment> attachments = attachmentDao.getAttachmentsByOccassion(pageIndex, pageSize,
				!asc, orderName, occassion);
		int totalCount = attachmentDao.getCountByOccassion(occassion);
		PageVM<Attachment> pageVM = new PageVM<Attachment>(pageIndex, 
				totalCount, pageSize,attachments);
		return pageVM;
	}

	
	public AttachmentHelper getBriefHelper(BasePDF basePDF, String uploader,
			PhysicsEnum occasion) {
		String id = GUID.get();
		Storage storage = storageDao.getActiveStorage();
		String relativePath = basePDF.getRelativePath();
		String path = basePDF.getPath();
		String name = basePDF.getName()+ basePDF.getFilePost();
		
		Attachment attachment = new Attachment();
		attachment.setId(id);
		attachment.setName(name);
		attachment.setOccasion(occasion.getStatus());
		attachment.setPath(relativePath + name);
		attachment.setType(basePDF.getFileType());
		attachment.setUploader(uploader);
		attachment.setDate((new Date()).toString());
		attachment.setStorage(storage);
		
		AttachmentHelper ah = new AttachmentHelper();
		ah.setAttachment(attachment);
		ah.setPath(path);
		ah.setStorageName(name);
		return ah;
	}

	
	public String getZipAttachmentPath(List<String> attachIds) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public List<Attachment> getVideoAttachments(int pageIndex,int pageSize,Boolean desc,
			String orderProperName, String occassion){
		return attachmentDao.getAttachmentsByOccassion(pageIndex, pageSize, desc, orderProperName, occassion);
	}
}
