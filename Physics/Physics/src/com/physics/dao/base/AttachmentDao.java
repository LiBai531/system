package com.physics.dao.base;

import java.util.List;

import com.physics.dao.common.Dao;
import com.physics.entity.Attachment;

public interface AttachmentDao extends Dao<Attachment, String>{
	
	public abstract List<Attachment>  getAttachmentsByOccassion(int pageIndex,int pageSize,Boolean desc,
			String orderProperName, String occassion) ;
	
	public int getCountByOccassion(String occassion);
	
}
