package com.physics.dao.common;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.LockMode;
import org.hibernate.Query;

/**
 * @author BOLIN
 * 
 * @param <T>
 */
public interface Dao<T, PK> {

    /**
     * 按照主键取实体
     * 
     * @param 主键
     * @return 实体
     */
    public abstract T get(PK id);

    /**
     * 根据PK列表返回实体类列表
     * 
     * @param List
     *        <主键>
     * @return List<实体>
     */
    public abstract List<T> getList(List<PK> ids);

    /**
     * 获得所有的实体类
     * 
     * @return List<实体>
     */
    public abstract List<T> getAll();

    /**
     * 按照排序字段获得所有的实体
     * @param desc
     *        升序还是降序true-降序，false-升序
     * @param orderProperName
     *        属性字段名
     * @return
     */
    public abstract List<T> getAll(Boolean desc, String orderProperName);

    /**
     * 分页取实体
     * 
     * @param pageIndex
     *        页序号，从1开始
     * @param pageSize
     *        页大小
     * @return List<实体>
     */
    public abstract List<T> page(int pageIndex, int pageSize);

    /**
     * 分页取实体（按照排序字段分页）
     * 
     * @param pageIndex
     *        页序号，从1开始
     * @param pageSize
     *        也大小
     * @param desc
     *        排序类型，true降序,false升序
     * @param orderProperName
     *        排序字段名（实体类的属性名）
     * @return
     */
    public abstract List<T> page(int pageIndex, int pageSize, Boolean desc, String orderProperName);

    /**
     * 保存实体
     * 
     * @param 实体
     * @return 主键
     */
    public abstract PK save(T entity);

    /**
     * 持久化或更新实体
     * 
     * @param 实体
     */
    public abstract void saveOrUpdate(T entity, Boolean...cache);

    public abstract void merge(T entity);

    /**
     * 取得该实体的数量
     * 
     * @return
     */
    public abstract int Count();

    /**
     * 删除实体
     * 
     * @param 实体
     */
    public abstract void delete(T entity);

    /**
     * 删除实体们
     * 
     * @param List
     *        <实体>
     */
    public abstract void deleteAll(Collection<T> entities);

    /**
     * 根据主键删除实体
     * 
     * @param 主键
     */
    public abstract void deleteByKey(PK id);

    /**
     * 强制SessionFlush（）
     * 
     */
    public abstract void flush();

    /**
     * 给实体加锁
     * 
     * @param entity
     * @param lock
     */
    public abstract void lock(T entity, LockMode lock);

    /**
     * 通过Criteria获取数据,只取出group by的列
     * @return
     */
    public abstract List<T> getPageByCriteria(Map<String, Object> params, final int indexPage,
            final int pageSize, Boolean desc, String orderProperName, List<String> groupName);

    /**
     * 取出所有列
     * @param params
     * @param indexPage
     * @param pageSize
     * @param desc
     * @param orderProperName
     * @return
     */
    public abstract List<T> getPageByCriteria(Map<String, Object> params, final int indexPage,
            final int pageSize, Boolean desc, String orderProperName);

    public abstract Integer getCountCriteria(Map<String, Object> params,
            List<String> nullColumnNames, String countName);

    /**
     * 通过Criteria获取列和该列的count
     * @param params
     * @param groupNames
     * @return
     */
    public abstract List<T> getCountByCriteria(Map<String, Object> params, List<String> groupNames);

    /**
     * 通过example获取对象
     * @param example
     * @return
     */
    public abstract List<T> getTByExample(T example);

    /**
     * 
     * @param example
     * @param desc
     * @param orderProperName
     * @return
     */
    public abstract List<T> getTByExample(T example, Boolean desc, String orderProperName);

    /**
     * 
     * @param example
     * @param indexPage
     * @param pageSize
     * @param desc
     * @param orderProperName
     * @return
     */
    public abstract List<T> getTByExample(T example, final int indexPage, final int pageSize,
            Boolean desc, String orderProperName);
    
    
    public abstract int hqlBulkUpdate(String hql);

    /**
     * @param 带参数hql更新
     * @param values
     * @return
     */
    public abstract int hqlBulkUpdate(String hql, Object[] values) ;

    /**
     * hql找实体们
     * 
     * @param hsql
     * @return List<实体>
     */
    public abstract List<T> hqlFind(String hql) ;

    /**
     * hql找实体们（带参）
     * 
     * @param hsql
     * @return List<实体>
     */
    public abstract List<T> hqlFind(String hql, Object[] values) ;
    /**
     * 包含数组类型的query查询 
     * http://blessht.iteye.com/blog/1051421
     * @param query
     * @param map
     * @return
     */
    public abstract List<T> hqlFind(String hql, Map<String, Object> map);
}
