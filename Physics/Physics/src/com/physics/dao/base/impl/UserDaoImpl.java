package com.physics.dao.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.physics.dao.common.impl.DaoImpl;
import com.physics.dao.base.UserDao;
import com.physics.entity.User;

public class UserDaoImpl extends DaoImpl<User,String> implements UserDao {
	
	public boolean validatorUser(String id,String password) {		
		String hql = "from User u where u.loginId=? and u.password=?";
	    List<User> list=super.hqlFind(hql, new Object[]{id,password});
		return (list.size()>0);
	}
	
	public boolean validatorUserInit(String id,String password) {		
		String hql = "from User u where u.loginId=? and u.init_password=?";
	    List<User> list=super.hqlFind(hql, new Object[]{id,password});
		return (list.size()>0);
	}

	
	public User getByLoginId(String loginId) {
		// TODO Auto-generated method stub
		String hql="from User u where u.loginId=?";
		List<User>list =super.hqlFind(hql, new Object[]{loginId});
		if(list.size()>0)
			return list.get(0);
		else {
			return null;
		}
	}

	/**
	 * 根据用户类型返回用户列表，返回的用户限定在pageIndex和pageSize范围内
	 */
	
	public List<User> getPageUsersByUserType(int pageIndex, int pageSize,
			Boolean desc, String orderProperName, String userType) {
		// TODO Auto-generated method stub
		String hql="from User u where u.userType=?";
		List<User> list =super.hqlPage(hql, pageIndex, pageSize, desc, orderProperName, new Object[]{userType});
		return list;
	}

	/**
	 * 
	 */
	
	public List<User> getUsersByUserType(String userType) {
		// TODO Auto-generated method stub
		String hql="from User u where u.userType=?";
		List<User> list = super.hqlFind(hql,new Object[]{userType});
		return list;
	}
	
	/**
	 * 根据学校ID返回本学校学科用户(分页)
	 */
	
	public List<User> getDisciplineUsersByUnitId(int pageIndex, int pageSize,
			Boolean desc, String orderProperName, String unitId) {
		// TODO Auto-generated method stub
		String hql="from User u where u.userType=? and u.unitId = ?";
		List<User> list =super.hqlPage(hql, pageIndex, pageSize, desc, orderProperName, new Object[]{"3", unitId});
		return list;
	}

	/**
	 * 获得本学校的所有学科用户	
	 */
	
	public List<User> getDisciplineUsersByUnitId(String unitId){
		String hql="from User u where u.userType=? and u.unitId = ?";
		List<User> list = super.hqlFind(hql, new Object[]{"3",unitId});
		return list;
	}

	
	
	

	
	public int deleteUserBySource(int source) {
		// TODO Auto-generated method stub
		String sql = "delete from dsep_rbac_user where source = ?";
		Object[] valueParameter = new Object[1];
		valueParameter[0] = source;
		int result = super.sqlBulkUpdate(sql, valueParameter);
		return result;
	}

	
	public List<String> getUserIdsByUnitIdAndDiscId(String unitId, String discId) {
		// TODO Auto-generated method stub
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select id from dsep_rbac_user u");
		List<Object> values=new ArrayList<Object>(0);
		boolean bFirst= true;
		if(StringUtils.isNotBlank(unitId)){
			if(bFirst){
				bFirst=false;
				sqlBuffer.append(" where ");
			}else{
				sqlBuffer.append(" and ");
			}
			sqlBuffer.append(" u.unit_id = ? ");
			values.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			if(bFirst){
				bFirst=false;
				sqlBuffer.append(" where ");
			}else{
				sqlBuffer.append(" and ");
			}
			sqlBuffer.append(" disc_id = ? ");
			values.add(discId);
		}
		return super.GetShadowResult(sqlBuffer.toString(), values.toArray());
	}

	
	public List<User> getUserByDisc12(String loginId,String name,String unitId, String discId1,
			String discId2,int pageIndex,int pageSize,String orderProperName,Boolean desc) {
		// TODO Auto-generated method stub
		boolean bFirst = true;
		boolean bSecond = true;
		List<Object> values = new ArrayList<Object>(0);
		StringBuilder hql = new StringBuilder(" from User u  ");
		if(StringUtils.isNotBlank(loginId)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.loginId = ? ");
			values.add(loginId);
		}
		if(StringUtils.isNotBlank(name)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.name = ? ");
			values.add(name);
		}
		if(StringUtils.isNotBlank(unitId)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.unitId = ? ");
			values.add(unitId);
		}
		if(StringUtils.isNotBlank(discId1)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			if(bSecond){
				bSecond= false;
				hql.append(" ( ");
			}else{
				hql.append(" or ");
			}
			hql.append(" u.discId = ? ");
			values.add(discId1);
		}
		if(StringUtils.isNotBlank(discId2)){
			
			if(bSecond){
				bSecond= false;
				if(bFirst){
					bFirst=false;
					hql.append(" where ");
				}else{
					hql.append(" and ");
				}
				hql.append(" ( ");
			}else{
				hql.append(" or ");
			}
			hql.append(" u.discId2 = ? )  ");
			values.add(discId2);
		}else{
			hql.append(" )  ");
		}
		List<User> users = super.hqlPage(hql.toString(), pageIndex, pageSize, desc,
				orderProperName, values.toArray());
		return users;
	}

	
	public int getCount12(String loginId,String name,String unitId, String discId1, String discId2) {
		// TODO Auto-generated method stub
		boolean bFirst = true;
		boolean bSecond = true;
		List<Object> values = new ArrayList<Object>(0);
		StringBuilder hql = new StringBuilder(" select count(*) from User u  ");
		if(StringUtils.isNotBlank(loginId)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.loginId = ? ");
			values.add(loginId);
		}
		if(StringUtils.isNotBlank(name)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.name = ? ");
			values.add(name);
		}
		if(StringUtils.isNotBlank(unitId)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			hql.append(" u.unitId = ? ");
			values.add(unitId);
		}
		if(StringUtils.isNotBlank(discId1)){
			if(bFirst){
				bFirst=false;
				hql.append(" where ");
			}else{
				hql.append(" and ");
			}
			if(bSecond){
				bSecond= false;
				hql.append(" ( ");
			}else{
				hql.append(" or ");
			}
			hql.append(" u.discId = ? ");
			values.add(discId1);
		}
		if(StringUtils.isNotBlank(discId2)){
			
			if(bSecond){
				bSecond= false;
				if(bFirst){
					bFirst=false;
					hql.append(" where ");
				}else{
					hql.append(" and ");
				}
				hql.append(" ( ");
			}else{
				hql.append(" or ");
			}
			hql.append(" u.discId2 = ? )  ");
			values.add(discId2);
		}else{
			hql.append(" )  ");
		}
		return super.hqlCount(hql.toString(),values.toArray());
	}

	
	public List<User> getSearchUsers(String loginId, String unitId, String discId, String name,String userType,
			String isValid, int page, int pageSize) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.id is not null";
		List<Object> params = new ArrayList<Object>(0);
		
		if(StringUtils.isNotBlank(loginId)){
			hql += " and u.loginId like ?";
			loginId = "%" + loginId + "%";
			params.add(loginId);
		}
		
		if(StringUtils.isNotBlank(unitId)){
			hql += " and u.unitId=?";
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			hql += " and u.discId=?";
			params.add(discId);
		}
		if(!userType.equals("-")){
			hql += " and u.userType=?";
			params.add(userType);
		}
		
		if(!isValid.equals("-")){
			hql += " and u.isValid=?";
			char c = isValid.charAt(0);
			params.add(c);
		}
		if(StringUtils.isNotBlank(name)){
			hql += " and u.name like ?";
			name = "%" + name + "%";
			params.add(name);
		}
		
		
		List<User> list = super.hqlPage(hql, page , pageSize, params.toArray());
		
		return list;
	}

	
	public int getSearchCount(String loginId, String unitId, String discId,
			String name, String userType) {
		// TODO Auto-generated method stub
		String hql = "select count(id) from User u where u.id is not null";
		List<Object> params = new ArrayList<Object>(0);
		
		if(StringUtils.isNotBlank(loginId)){
			hql += " and u.loginId like ?";
			loginId = "%" + loginId + "%";
			params.add(loginId);
		}
		
		if(StringUtils.isNotBlank(unitId)){
			hql += " and u.unitId=?";
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			hql += " and u.discId=?";
			params.add(discId);
		}
		if(!userType.equals("-")){
			hql += " and u.userType=?";
			params.add(userType);
		}
		if(StringUtils.isNotBlank(name)){
			hql += " and u.name like ?";
			name = "%" + name + "%";
			params.add(name);
		}
		
		return super.hqlCount(hql, params.toArray());
	}
	
	
	public int getSearchCountNew(String loginId, String unitId, String discId,
			String name, String userType,String isValid) {
		// TODO Auto-generated method stub
		String hql = "select count(id) from User u where u.id is not null";
		List<Object> params = new ArrayList<Object>(0);
		
		if(StringUtils.isNotBlank(loginId)){
			hql += " and u.loginId like ?";
			loginId = "%" + loginId + "%";
			params.add(loginId);
		}
		
		if(StringUtils.isNotBlank(unitId)){
			hql += " and u.unitId=?";
			params.add(unitId);
		}
		if(StringUtils.isNotBlank(discId)){
			hql += " and u.discId=?";
			params.add(discId);
		}
		if(!userType.equals("-")){
			hql += " and u.userType=?";
			params.add(userType);
		}
		if(StringUtils.isNotBlank(name)){
			hql += " and u.name like ?";
			name = "%" + name + "%";
			params.add(name);
		}
		if(!isValid.equals("-")){
			hql += " and u.isValid=?";
			char c = isValid.charAt(0);
			params.add(c);
		}
		
		return super.hqlCount(hql, params.toArray());
	}

	
	public String getUserIdByEmail(String email) {
		String hql="from User u where u.loginId=?";
		List<User>list =super.hqlFind(hql, new Object[]{email});
		if(list.size()>0){
			User user = list.get(0);
			return user.getId();
		}
		else {
			return null;
		}
	}

	
	public boolean ifEmailExist(String email) {
		String hql = "from User u where u.loginId=?";
	    List<User> list=super.hqlFind(hql, new Object[]{email});
	    return (list.size()>0);
	}

	
	public List<User> getUserByUser(User user) {
		// TODO Auto-generated method stub
		return super.getTByExample(user);
	}

	
	public List<User> getUsersByUnitIdAndDiscId(Object unitId, Object discId) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.unitId = ? AND u.discId = ? AND u.userType = 4";
		List<User> list = super.hqlFind(hql,new Object[]{unitId,discId});
		return list;
	}

	
	public void updateLoginInfo(String loginIp, Date loginTime, String id) {
		// TODO Auto-generated method stub
		
	}

	
	public void updateUserPassword(String password, String id) {
		// TODO Auto-generated method stub
		
	}

	
	public void resetPasswordByAdmin(String new_password, String userId) {
		// TODO Auto-generated method stub
		
	}

	
	public List<User> getUsersByHql(String hql, Object[] objects) {
		return super.hqlFind(hql,objects);
	}

}
