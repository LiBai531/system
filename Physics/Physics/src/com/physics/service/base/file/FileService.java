package com.physics.service.base.file;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.physics.entity.Attachment;

public interface FileService {
	
	/**
	 * 生成HTML文件，并写入数据库文件Id,最后返回Id,
	 * 如果文件已经存在则返回原文件Id,如果文件不存在则新生成文件并
	 * @param unitId
	 * @param discId
	 * @param itemChsName
	 * @param userId
	 * @param versionId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public abstract Attachment generateHtml(String unitId,
			String discId,
			String itemChsName,
			String userId,
			String versionId,String content);
}
