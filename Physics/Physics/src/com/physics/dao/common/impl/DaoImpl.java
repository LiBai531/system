package com.physics.dao.common.impl;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.physics.common.customercontext.CustomerContextHolder;
import com.physics.dao.common.Dao;
import com.physics.util.StringProcess;

@SuppressWarnings("unchecked")
public class DaoImpl<T, PK extends Serializable> implements Dao<T, PK> {

    /* private HibernateTransactionManager transactionManager; */
    private String         noSessionException        = "No Session found for current thread";
    private String         sessionClosed             = "Session is closed";
    private static String  transactionCloseException = "This TransactionCoordinator has been closed";
    private SessionFactory sessionFactory;
    /* private static Session session = null; */
    private Session        session;
    private Class<T>       entityClass;
    private String isClearCache = "1"; //更新之后需要清理缓存
    

	public String getIsClearCache() {
		return isClearCache;
	}

	public void setIsClearCache(String isClearCache) {
		this.isClearCache = isClearCache;
	}

	public Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * 打印测试信息
     * 
     * @param message
     */
    private void TestSessionFactoryType(String message) {
        /* System.out.println("**********************************************");
         * if( session == null ) message += " session is null"; else if(
         * !session.isOpen()) message += " session is not open";
         * System.out.println(sessionFactory.getClass().getName() + " " +
         * message);
         * System.out.println("**********************************************"); */
    }
    protected Session getSession(Boolean...cache) {
    	// 如果已存在session，直接获取
        /* if (session != null && session.isOpen()) {
         * return session;
         * } */
        if(cache == null || cache.length == 0 || cache[0] == true){
        	CustomerContextHolder.setCustomerType(isClearCache);
        }else if(cache.length > 0 && cache[0] == false){
            CustomerContextHolder.setCustomerType("0");
        }
        try {
            // 先尝试事务中获取会话
            session = this.sessionFactory.getCurrentSession();
            return session;
        } catch (Exception e) {
            if (e.getMessage().equals(noSessionException)
                    || e.getMessage().equals(this.sessionClosed)) {
                // 如果没有开启事务则开启一个新的会话
            	
                session = this.sessionFactory.openSession();
                StringProcess.testOutput("open a new session");
                return session;
            } else {
                StringProcess.testOutput("whether open:" + session.isOpen());
                return null;
            }	
        }
    }

    public void setSessionFactory(SessionFactory factory) {
        this.sessionFactory = factory;
    }

    public DaoImpl() {
        this.entityClass = null;
        Class c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
    }

    public T get(PK id) {
        return (T) getSession().get(entityClass, id);
    }

    public List<T> getList(List<PK> ids) {
        List<T> list = new ArrayList<T>();
        for (PK key : ids) {
            list.add(this.get(key));
        }
        return list;
    }

    public List<T> getAll() {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setCacheable(true);
        return criteria.list();
    }

    public List<T> getAll(Boolean desc, String orderProperName) {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setCacheable(false);
        if (desc)
            criteria.addOrder(Order.desc(orderProperName));
        else
            criteria.addOrder(Order.asc(orderProperName));
        return criteria.list();
    }

    public List<T> page(int pageIndex, int pageSize) {
        int firstResultIndex = (pageIndex - 1) * pageSize;
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setCacheable(true);
        List<T> result = criteria.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
        return result;
    }

    public List<T> page(int pageIndex, int pageSize, Boolean desc, String orderProperName) {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setCacheable(true);
        if (desc)
            criteria.addOrder(Order.desc(orderProperName));
        else
            criteria.addOrder(Order.asc(orderProperName));

        int firstResultIndex = (pageIndex - 1) * pageSize;
        return criteria.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
    }

    public PK save(T entity) {
        return (PK) getSession().save(entity);
    }

    public void saveOrUpdate(T entity, Boolean... cache) {
        if(cache != null && cache.length > 0 && cache[0] == false){
            getSession(false).saveOrUpdate(entity);
        }else{
            getSession().saveOrUpdate(entity);
        }
    }
    

    //添加merge方法，对应异常：
    //illegally attempted to associate a proxy with two open Sessions
    //by qyx
    public void merge(T entity) {
        getSession().merge(entity);
    }

    public int Count() {
        String hql = "select count(*) from " + this.entityClass.getSimpleName();
        Query query = getSession().createQuery(hql);
        return ((Long) query.iterate().next()).intValue();
    }

    public void delete(T entity) {
        getSession().delete(entity);
        this.flush();
    }

    public void deleteAll(Collection<T> entities) {
        for (T entity : entities)
            this.delete(entity);
    }

    public void deleteByKey(PK id) {
        T entity = this.get(id);
        getSession().delete(entity);
    }

    // *******************************以下为hql&sql***************************
    /**
     * hql更新
     * 
     * @param hql
     * @return
     */
    public int hqlBulkUpdate(String hql) {
        Query q = getSession().createQuery(hql);
        return q.executeUpdate();
    }

    /**
     * @param 带参数hql更新
     * @param values
     * @return
     */
    public int hqlBulkUpdate(String hql, Object[] values) {
        Query q = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            q.setParameter(i, values[i]);
        }
        return q.executeUpdate();
    }

    /**
     * hql找实体们
     * 
     * @param hsql
     * @return List<实体>
     */
    public List<T> hqlFind(String hql) {
        Query query = getSession().createQuery(hql);
        query.setCacheable(true);
        return query.list();
    }

    /**
     * hql找实体们（带参）
     * 
     * @param hsql
     * @return List<实体>
     */
    public List<T> hqlFind(String hql, Object[] values) {
        Query query = getSession().createQuery(hql);
        query.setCacheable(true);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query.list();
    }
    /**
     * 包含数组类型的query查询 by qyx
     * http://blessht.iteye.com/blog/1051421
     * @param query
     * @param map
     * @return
     */
    public List<T> hqlFind(String hql, Map<String, Object> map) {  
        Query query = getSession().createQuery(hql);
        if (map != null || map.size() > 0) {
            Set<String> keySet = map.keySet();  
            for (String string : keySet) {  
                Object obj = map.get(string);  
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
                if(obj instanceof Collection<?>){  
                    query.setParameterList(string, (Collection<?>)obj);  
                }else if(obj instanceof Object[]){  
                    query.setParameterList(string, (Object[])obj);  
                }else{  
                    query.setParameter(string, obj);  
                }  
            }  
        }  
        return query.list();
    }  
    /**
     * hql分页找实体们
     * 
     * @param hql
     * @return List<实体>
     */
    protected List<T> hqlPage(String hql, int pageIndex, int pageSize) {
        int firstResultIndex = (pageIndex - 1) * pageSize;
        Query query = getSession().createQuery(hql);
        query.setCacheable(true);
        return query.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
    }

    /**
     * hql分页找实体们（带参）
     * 
     * @param hql
     * @return List<实体>
     */
    protected List<T> hqlPage(String hql, int pageIndex, int pageSize, Object[] values) {
        int firstResultIndex = (pageIndex - 1) * pageSize;
        Query query = getSession().createQuery(hql);
        query.setCacheable(true);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
    }

    /**
     * hql分页排序找实体们（带参）
     * 
     * @param hql
     *        hql语句，不带order by 子句
     * @param pageIndex
     * @param pageSize
     * @param desc
     *        bool变量，true为desc,false为asc
     * @param orderProperName
     *        排序的属性
     * @param values
     *        hql语句的参数，数组形式
     * @return
     */
    protected List<T> hqlPage(String hql, int pageIndex, int pageSize, Boolean desc,
            String orderProperName, Object[] values) {
        int firstResultIndex = (pageIndex - 1) * pageSize;
        Field[] fields = getEntityClass().getDeclaredFields();
        boolean isNumber = true;
        for (Field field : fields) {
            if (orderProperName != null && orderProperName.equals(field.getName())) {
                String type = field.getGenericType().toString();
                if (type.equals("class java.lang.Integer") || type.equals("int")) {
                    isNumber = true;
                }
            }
        }
       
        hql += " order by  " + orderProperName;
       
        hql += GetSortString(desc);
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
    }

    /**
     * hql根据主键集合获取特定的数据行并进行分页和排序
     * 
     * @param entityName
     *        实体名称
     * @param ids
     *        主键集合
     * @param pageIndex
     * @param pageSize
     * @param desc
     *        bool变量，true为desc,false为asc
     * @param orderProperName
     *        排序的属性
     * @return
     */
    protected List<T> hqlPage(String entityName, List<String> ids, int pageIndex, int pageSize,
            Boolean desc, String orderProperName) {
        int firstResultIndex = (pageIndex - 1) * pageSize;
        if (ids == null || ids.size() == 0)
            return null;
        String hqlList = GetListString(ids);
        String hql = "from " + entityName + " where id in (" + hqlList + ") order by "
                + orderProperName + "";
        hql += GetSortString(desc);
        Query query = getSession().createQuery(hql);
        query.setCacheable(false);
        return query.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
    }

    /**
     * 将主键集合转化为in子句的查询条件，形式为A,B,C
     * 
     * @param ids
     *        主键的集合
     * @return
     */
    private String GetListString(List<String> ids) {
        String list = "";
        for (int i = 0; i < ids.size(); i++) {
            list += ids.get(i) + ",";
        }
        list = list.substring(0, list.length() - 1);
        return list;
    }

    /**
     * 获取排序规则
     * 
     * @param desc
     * @return
     */
    private String GetSortString(boolean desc) {
        if (desc)
            return " desc";
        else
            return " asc";
    }

    /**
     * hql查询数量
     * 
     * @param hql
     * @return 数量
     */
    protected int hqlCount(String hql) {
        Query query = getSession().createQuery(hql);
        return ((Long) query.iterate().next()).intValue();
    }

    /**
     * hsql查询数量(带参数)
     * 
     * @param hql
     * @return 数量
     */
    protected int hqlCount(String hql, Object[] values) {
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return ((Long) query.iterate().next()).intValue();
    }

    /**
     * sql 查找
     * 
     * @param sql
     * @return
     */
    protected List<T> sqlFind(String sql) {
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setCacheable(true);
        return query.addEntity(entityClass).list();
    }

    protected List<Map<String, Object>> sqlQuery(String sql) {
        SQLQuery query = getSession().createSQLQuery(sql);
        return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    /**
     * 带参数sql 查找
     * 
     * @param sql
     * @return
     */
    protected List<T> sqlFind(String sql, Object[] values) {
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setCacheable(true);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query.addEntity(entityClass).list();
    }

    protected List<T> sqlFind(String sql, List<Object> values) {
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setCacheable(true);
        for (int i = 0; i < values.size(); i++) {
            query.setParameter(i, values.get(i));
        }
        return query.addEntity(entityClass).list();
    }

    /**
     * sql分页找实体们
     * 
     * @param sql
     * @return List<实体>
     */
    protected List<T> sqlPage(String sql, int pageIndex, int pageSize) {
        int firstResultIndex = (pageIndex - 1) * pageSize;
        Query query = getSession().createSQLQuery(sql).addEntity(entityClass);
        query.setCacheable(true);
        return query.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
    }

    /**
     * sql分页找实体们（带参）
     * @param hsql
     * @return List<实体>
     */
    protected List<T> sqlPage(String sql, int pageIndex, int pageSize, Object[] values) {
        int firstResultIndex = (pageIndex - 1) * pageSize;
        Query query = getSession().createSQLQuery(sql).addEntity(entityClass);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
    }

    protected List<T> sqlPage(String sql, int pageIndex, int pageSize, Boolean desc,
            String orderProperName, Object[] values) {
        int firstResultIndex = (pageIndex - 1) * pageSize;
        sql += " order by  " + orderProperName;
        sql += GetSortString(desc);
        Query query = getSession().createSQLQuery(sql).addEntity(entityClass);
        query.setCacheable(false);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
    }

    /**
     * sql更新
     * 
     * @param hql
     * @return
     */
    protected int sqlBulkUpdate(String sql) {
        SQLQuery q = getSession().createSQLQuery(sql);
        return q.executeUpdate();
    }

    /**
     * @param 带参数sql更新
     * @param values
     * @return
     */
    @SuppressWarnings("deprecation")
	protected int sqlBulkUpdate(String sql, Object[] values) {
    	//sessionFactory.evictQueries();
        SQLQuery q = getSession().createSQLQuery(sql);
        for (int i = 0; i < values.length; i++) {
            q.setParameter(i, values[i]);
        }
        q.setCacheMode(CacheMode.IGNORE);
        return q.executeUpdate();
    }

    /**
     * 获取单列投影查询结果
     * 
     * @param sql
     *        例："select 列名 from 表名",不能用*，必须指定单列名
     * @return
     */
    protected List<String> GetShadowResult(String hql) {
    	SQLQuery query = getSession().createSQLQuery(hql);
        List<String> theList = query.list();
        return theList;
    }

    /**
     * 获取统计函数如count()单列投影查询结果
     * 使用上面的方法会报BigDecimal cannot be cast to String的类型转换错误
     * 
     * @param sql
     * @return
     */
    protected List<Integer> GetShadowresult(String hql) {
    	SQLQuery q = getSession().createSQLQuery(hql);
        List<Object> theList = q.list();
        List<Integer> list = new ArrayList<Integer>();
        for (Object obj : theList) {
            list.add(Integer.parseInt(obj.toString()));
        }
        return list;
    }

    /**
     * 获取单列投影查询结果
     * 
     * @param sql
     *        sql语句 例："select 列名 from 表名",不能用*，必须指定单列名
     * @param param
     *        参数数组
     * @return 某一列结果的集合
     */
    protected List<String> GetShadowResult(String hql, Object[] param) {
    	SQLQuery q = getSession().createSQLQuery(hql);
        for (int i = 0; i < param.length; i++) {
            q.setString(i, param[i].toString());
        }
        return q.list();
    }

    /**
     * sql获得标量结果
     * 
     * @param sql
     * @param 查询结果的列名们
     * @return List<行数据>
     */
    protected List<Object[]> sqlScalarResults(String sql, String[] ColumnNames) {
        SQLQuery q = getSession().createSQLQuery(sql);
        for (int i = 0; i < ColumnNames.length; i++)
            q.addScalar(ColumnNames[i]);

        return q.list();
    }

    /**
     * sql获取 标量结果（不带列名，带参数）
     * @param sql
     * @param values
     * @return
     */
    protected List<Object[]> sqlScalarResults(String sql, Object[] values) {
        SQLQuery q = getSession().createSQLQuery(sql);
        for (int i = 0; i < values.length; i++) {
            q.setParameter(i, values[i]);
        }
        return q.list();

    }

    /**
     * sql获得标量结果(带参数)
     * 
     * @param sql
     * @param 查询结果的列名们
     * @return List<行数据>
     */
    protected List<Object[]> sqlScalarResults(String sql, String[] ColumnNames, Object[] values) {
        SQLQuery q = getSession().createSQLQuery(sql);
        for (int i = 0; i < values.length; i++)
            q.setParameter(i, values[i]);
        for (int i = 0; i < ColumnNames.length; i++)
            q.addScalar(ColumnNames[i]);
        List<Object[]> test = q.list();
        return test;
    }

    /**
     * 分页sql获得标量结果，结果不带列名(无参数)
     * 
     * @param sql
     * @param 查询结果的列名们
     * @return List<行数据>
     */
    protected List<Object[]> sqlScalarResultsByPage(String sql, String[] ColumnNames,
            int pageIndex, int pageSize) {
        SQLQuery q = getSession().createSQLQuery(sql);
        for (int i = 0; i < ColumnNames.length; i++)
            q.addScalar(ColumnNames[i]);
        int firstResultIndex = (pageIndex - 1) * pageSize;
        return q.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
    }

    /**
     * 分页sql获得结果,结果带列名(无参数)
     * 
     * @param sql
     * @param 查询结果的列名们
     * @return List<Map<列名，列值>>
     */
    protected List<Map<String, String>> sqlScalarResultsByPage2(String sql,
            List<String> dataBaseColumnNames, List<String> outPutColumnNames, int pageIndex,
            int pageSize) {
        SQLQuery q = getSession().createSQLQuery(sql);
        for (int i = 0; i < dataBaseColumnNames.size(); i++)
            q.addScalar(dataBaseColumnNames.get(i));
        int firstResultIndex = (pageIndex - 1) * pageSize;
        List<Object[]> data = q.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
        // 转换数据
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        for (Object[] r : data) {
            Map<String, String> row = new LinkedHashMap<String, String>();
            for (int i = 0; i < r.length; i++) {
                if (r[i] == null)
                    r[i] = "";
                row.put(outPutColumnNames.get(i), r[i].toString());
            }
            result.add(row);
        }
        return result;
    }

    /**
     * 分页sql获得标量结果，结果不带列名(带参数)
     * 
     * @param sql
     * @param 查询结果的列名们
     * @return List<行数据>
     */
    protected List<Object[]> sqlScalarResultsByPage(String sql, String[] ColumnNames,
            Object[] values, int pageIndex, int pageSize) {
        SQLQuery q = getSession().createSQLQuery(sql);
        for (int i = 0; i < values.length; i++)
            q.setParameter(i, values[i]);
        for (int i = 0; i < ColumnNames.length; i++)
            q.addScalar(ColumnNames[i]);
        int firstResultIndex = (pageIndex - 1) * pageSize;
        return q.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
    }

    /**
     * 分页sql获得标量结果，结果带列名(带参数)
     * 
     * @param sql
     * @param 查询结果的列名们
     * @return List<行数据>
     */
    protected List<Map<String, String>> sqlScalarResultsByPage2(String sql,
            List<String> dataBaseColumnNames, List<String> outPutColumnNames, Object[] values,
            int pageIndex, int pageSize) {
        SQLQuery q = getSession().createSQLQuery(sql);
        for (int i = 0; i < values.length; i++)
            q.setParameter(i, values[i]);
        for (int i = 0; i < dataBaseColumnNames.size(); i++)
            q.addScalar(dataBaseColumnNames.get(i));
        // 计算第一个结果index
        int firstResultIndex = (pageIndex - 1) * pageSize;
        List<Object[]> data = q.setFirstResult(firstResultIndex).setMaxResults(pageSize).list();
        // 转换数据
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        for (Object[] r : data) {
            Map<String, String> row = new LinkedHashMap<String, String>();
            for (int i = 0; i < r.length; i++) {
                if (r[i] == null)
                    r[i] = "";
                row.put(outPutColumnNames.get(i), r[i].toString());
            }
            result.add(row);
        }
        return result;
    }

    /**
     * @param sql获得标量结果
     * @param 查询结果的列名们
     * @param 列的类型
     *        （为org.hibernate.type.Type类型）
     * @return List<行数据>
     */
    protected List<Object[]> sqlScalarResults(String sql, String[] columnNames,
            org.hibernate.type.Type[] columntypes) {
        SQLQuery q = getSession().createSQLQuery(sql);
        for (int i = 0; i < columnNames.length; i++) {
            org.hibernate.type.Type type = columntypes[i];
            q.addScalar(columnNames[i], type);
        }
        return q.list();
    }

    /**
     * 根据sql查询唯一结果（无参数）
     * 
     * @param sql
     * @return
     */
    protected Object sqlUniqueResult(String sql) {
        Query query = getSession().createSQLQuery(sql);
        Object r = query.uniqueResult();
        return r;
    }

    /**
     * 根据sql查询唯一结果（有参数）
     * 
     * @param sql
     * @return
     */
    protected Object sqlUniqueResult(String sql, Object[] values) {
        Query query = getSession().createSQLQuery(sql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        Object r = query.uniqueResult();
        return r;
    }

    protected String sqlUniqueStrResult(String sql, Object[] values) {
        return String.valueOf(sqlUniqueResult(sql, values));
    }

    /**
     * @param sql获得标量结果
     * @param 查询结果的列名们
     * @param 列的类型
     *        （为org.hibernate.type.Type类型）
     * @return List<行数据>
     */
    protected List<Object[]> sqlScalarResults(String sql, String[] columnNames,
            org.hibernate.type.Type[] columntypes, Object[] values) {
        SQLQuery q = getSession().createSQLQuery(sql);
        for (int i = 0; i < values.length; i++)
            q.setParameter(i, values[i]);
        for (int i = 0; i < columnNames.length; i++) {
            org.hibernate.type.Type type = columntypes[i];
            q.addScalar(columnNames[i], type);
        }
        return q.list();
    }

    /**
     * sql查询数量
     * 
     * @param sql
     * @return 数量
     */
    protected int sqlCount(String sql) {
        Query query = getSession().createSQLQuery(sql);
        Object r = query.uniqueResult();
        return ((BigDecimal) r).intValue();
    }

    /**
     * sql查询数量(带参数)
     * 
     * @param sql
     * @return 数量
     */
    protected int sqlCount(String sql, Object[] values) {
        Query query = getSession().createSQLQuery(sql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        Object r = query.uniqueResult();
        return ((BigDecimal) r).intValue();
    }

    
    public void flush() {
        getSession().flush();
    }

    protected int Procedure(String procedureName, String[] args) {
        String procedure = "{call " + procedureName + "(";
        int i = 0;
        if (args != null)
            while (i++ < args.length)
                procedure += ((i == args.length) ? "?)" : "?,");
        SQLQuery query = getSession().createSQLQuery(procedure);
        if (args != null)
            for (int j = 0; j < args.length; j++)
                query.setString(j, args[j]);
        return query.executeUpdate();
    }

    protected void Procedure(String procedureName) {
        String procedure = "{call " + procedureName + "()}";
        SQLQuery query = getSession().createSQLQuery(procedure);
        query.executeUpdate();
    }

    
    public void lock(T entity, LockMode lock) {
        getSession().lock(entity, lock);
    }

    
    public List<T> getPageByCriteria(Map<String, Object> params, int indexPage, int pageSize,
            Boolean desc, String orderProperName, List<String> groupName) {
        // TODO Auto-generated method stub
        Criteria criteria = getSession().createCriteria(this.entityClass);
        criteria.setCacheable(true);
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                criteria.add(Restrictions.eq(key, params.get(key)));
            }
        }
        ProjectionList pList = Projections.projectionList();
        if (groupName != null && groupName.size() > 0) {
            for (String name : groupName)
                pList.add(Projections.groupProperty(name));
        }
        criteria.setProjection(pList);
        int firstResultIndex = (indexPage - 1) * pageSize;
        criteria.setFirstResult(firstResultIndex);
        criteria.setMaxResults(pageSize);
        if (desc) {
            criteria.addOrder(Order.desc(orderProperName));
        } else {
            criteria.addOrder(Order.asc(orderProperName));
        }
        return criteria.list();
    }

    
    public List<T> getCountByCriteria(Map<String, Object> params, List<String> groupName) {
        // TODO Auto-generated method stub
        Criteria criteria = getSession().createCriteria(this.entityClass);
        criteria.setCacheable(true);
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                criteria.add(Restrictions.eq(key, params.get(key)));
            }
        }
        ProjectionList pList = Projections.projectionList();
        if (groupName != null && groupName.size() > 0) {
            for (String name : groupName) {
                pList.add(Projections.groupProperty(name));
                pList.add(Projections.rowCount(), name);
            }
            criteria.setProjection(pList);
        }
        return criteria.list();
    }

    
    public List<T> getTByExample(T example) {
        // TODO Auto-generated method stub
        return getTByExample(example, null, null);
    }

    
    public List<T> getTByExample(T example, Boolean desc, String orderProperName) {
        // TODO Auto-generated method stub
        return getTByExample(example, 0, 0, desc, orderProperName);
    }

    
    public List<T> getTByExample(T example, int indexPage, int pageSize, Boolean desc,
            String orderProperName) {
        // TODO Auto-generated method stub
        Criteria criteria = getSession().createCriteria(this.entityClass);
        criteria.setCacheable(true);
        int firstResultIndex = (indexPage - 1) * pageSize;
        criteria.setFirstResult(firstResultIndex);
        criteria.setMaxResults(pageSize);
        if (desc != null) {
            if (desc) {
                criteria.addOrder(Order.desc(orderProperName));
            } else {
                criteria.addOrder(Order.asc(orderProperName));
            }
        }
        return criteria.add(Example.create(example)).list();
    }

    
    public Integer getCountCriteria(Map<String, Object> params, List<String> nullColumnNames,
            String countName) {
        // TODO Auto-generated method stub
        Criteria criteria = getSession().createCriteria(this.entityClass);
        criteria.setCacheable(false);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
            }
        }
        if (nullColumnNames != null) {
            for (String nullCol : nullColumnNames) {
                criteria.add(Restrictions.isNull(nullCol));
            }
        }
        criteria.setProjection(Projections.count(countName));
        return ((Number) criteria.uniqueResult()).intValue();
    }

    
    public List<T> getPageByCriteria(Map<String, Object> params, int indexPage, int pageSize,
            Boolean desc, String orderProperName) {
        // TODO Auto-generated method stub
        Criteria criteria = getSession().createCriteria(this.entityClass);
        criteria.setCacheable(true);
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                criteria.add(Restrictions.eq(key, params.get(key)));
            }
        }
        int firstResultIndex = (indexPage - 1) * pageSize;
        criteria.setFirstResult(firstResultIndex);
        criteria.setMaxResults(pageSize);
        if (desc) {
            criteria.addOrder(Order.desc(orderProperName));
        } else {
            criteria.addOrder(Order.asc(orderProperName));
        }
        return criteria.list();
    }
}
