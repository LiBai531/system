package com.physics.dao.base.impl;

import java.util.List;

import com.physics.dao.base.AttachmentDao;
import com.physics.dao.common.impl.DaoImpl;
import com.physics.entity.Attachment;

public class AttachmentDaoImpl extends DaoImpl<Attachment, String> implements AttachmentDao{

	
	public List<Attachment> getAttachmentsByOccassion(int pageIndex,int pageSize,Boolean desc,
			String orderProperName, String occassion) {
		// TODO Auto-generated method stub
		String hql = "from Attachment a where a.occasion = ?";
		return super.hqlPage(hql, pageIndex, pageSize, desc, orderProperName,  new Object[]{occassion});
	}

	
	public int getCountByOccassion(String occassion) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from Attachment a where a.occasion = ?";
		return super.hqlCount(hql, new Object[]{occassion});
	}

}
