package com.framework.service;

import com.framework.bean.BaseEntity;
import com.framework.dao.Pager;
import com.framework.dao.Sort;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 一般数据库调用操作.
 */
public interface GenericCrudService {

    /**
     * 列表操作.
     * <p>
     * eg: select * from TABLE
     *
     * @param <E>   EntityBean类型泛参
     * @param <ID>  EntityBean ID类型泛参
     * @param clazz EntityBean 类型
     * @return select结果集
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <E extends BaseEntity<E, ID>, ID extends Serializable> List<E> list(Class<E> clazz);

    /**
     * 按ID取数据库条目.
     * <p>
     * eg: select * from TABLE where SimpleLongID = :id
     *
     * @param <E>   EntityBean类型泛参
     * @param <ID>  EntityBean ID类型泛参
     * @param clazz EntityBean 类型
     * @param id    数据库条目主键值
     * @return 数据库条目
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <E extends BaseEntity<E, ID>, ID extends Serializable> E get(Class<E> clazz, ID id);

    /**
     * 删除数据库条目.
     *
     * @param <E>    EntityBean类型泛参
     * @param <ID>   EntityBean ID类型泛参
     * @param entity 要删除的数据库条目,注意,如果仅知道条目id,请先做get操作,然后delete该结果.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void delete(E entity);

    /**
     * 保存数据库条目.
     * <p>
     * eg: insert into TABLE (col1,col2,...) values (val1,val2,...)
     *
     * @param <E>    EntityBean类型泛参
     * @param <ID>   EntityBean ID类型泛参
     * @param entity 数据库条目
     * @return 被保存的数据库条目的ID值, 在id为自动生成值时, 可以由此得到id值, 也可以entity.getId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public <E extends BaseEntity<E, ID>, ID extends Serializable> ID save(E entity);

    /**
     * 更新数据库条目.
     * <p>
     * eg: update TABLE set (col1=val1,col2=val2....) where SimpleLongID = :entity.id
     * 注意此更新操作为按条目主键更新.
     *
     * @param <E>    EntityBean类型泛参
     * @param <ID>   EntityBean ID类型泛参
     * @param entity 要更新的条目
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void update(E entity);

    /**
     * 保存或更新数据库条目.
     * <p>
     * 如果数据库存在此记录id的条目,则进行update操作,否则进行insert.
     *
     * @param <E>    EntityBean类型泛参
     * @param <ID>   EntityBean ID类型泛参
     * @param entity 要保存或更新的条目
     * @see #save(BaseEntity)
     * @see #update(BaseEntity)
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public <E extends BaseEntity<E, ID>, ID extends Serializable> void saveOrUpdate(E entity);

    /**
     * 统计数据库表记录数.
     * <p>
     * eg: select count(*) from TABLE
     *
     * @param <E>   EntityBean类型泛参
     * @param <ID>  EntityBean ID类型泛参
     * @param clazz EntityBean 类型
     * @return 条目数量.
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <E extends BaseEntity<E, ID>, ID extends Serializable> long count(Class<E> clazz);

    /**
     * 数据库HQL查询.
     * <p>
     * eg: hql: from ENTITY where property1 = ? and property2 = ?
     * args: property1 value,property2 value
     *
     * @param hql  HQL查询语句
     * @param args HQL查询参数
     * @return 查询结果集
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<?> hql(String hql, Object... args);

    /**
     * 数据库HQL查询.
     *
     * @param <T>        返回结果类型泛参
     * @param expectType 期望返回结果集类型
     * @see #hql(String, Object...)
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> hql(Class<T> expectType, String hql, Object... args);

    /**
     * 数据库原生SQL查询.
     * <p>
     * eg: SQL:select * from TABLE where col1 = ? and col2 = ?
     * args: col1 value, col2 value
     *
     * @param sql  SQL语句
     * @param args SQL查询参数
     * @return 查询结果集
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<?> sql(final String sql, final Object... args);

    /**
     * 数据库原生SQL查询.
     *
     * @param <T>        返回结果类型泛参
     * @param expectType 期望返回结果集类型
     * @see #sql(String, Object...)
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> sql(Class<T> expectType, final String sql, final Object... args);

    /**
     * 单一结果HQL查询.
     *
     * @return 结果
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException 返回结果数量不是1(expect 1)
     * @throws org.springframework.dao.EmptyResultDataAccessException         返回结果为0记录
     * @see #hql(String, Object...)
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Object uniqueResultHql(String hql, Object... args);

    /**
     * 单一结果HQL查询.
     *
     * @return 结果
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException 返回结果数量不是1(expect 1)
     * @throws org.springframework.dao.EmptyResultDataAccessException         返回结果为0记录
     * @see #hql(Class, String, Object...)
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> T uniqueResultHql(Class<T> expectType, String hql, Object... args);

    /**
     * 单一结果SQL查询.
     *
     * @return 结果
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException 返回结果数量不是1(expect 1)
     * @throws org.springframework.dao.EmptyResultDataAccessException         返回结果为0记录
     * @see #sql(String, Object...)
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Object uniqueResultSql(final String sql, final Object... args);

    /**
     * 单一结果SQL查询.
     *
     * @return 结果
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException 返回结果数量不是1(expect 1)
     * @throws org.springframework.dao.EmptyResultDataAccessException         返回结果为0记录
     * @see #sql(Class, String, Object...)
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> T uniqueResultSql(Class<T> expectType, final String sql, final Object... args);

    /**
     * DetachedCriteria查询.
     *
     * @param criteria
     * @return
     * @see DetachedCriteria
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<?> criteria(DetachedCriteria criteria);

    /**
     * Callback方式调用Hibernate,提供Hibernate session.
     *
     * @param <T>      返回结果类型
     * @param callback HibernateCallback实现
     * @return callback返回结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public <T> T callback(HibernateCallback<T> callback);

    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> T topResultHql(Class<T> clazz, final String hql, final Object... args);

    @Transactional(propagation = Propagation.SUPPORTS)
    public Object topResultHql(final String hql, final Object... args);

    @Transactional(propagation = Propagation.SUPPORTS)
    public Object topResultSql(final String sql, final Object... args);

    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> T topResultSql(Class<T> clazz, final String sql, final Object... args);

    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> topResultSql(Class<T> clazz, final int top, final String sql, final Object... args);

    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> topResultHql(Class<T> clazz, final int top, final String hql, final Object... args);

    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> Iterator<T> iterate(Class<T> clazz, final String hql, final Object... args);

    @Transactional(propagation = Propagation.SUPPORTS)
    public void closeIterator(Iterator<?> iterator);

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateHql(String hql, Object... args);

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateSql(final String sql, final Object... args);


//    /**
//     * TODO 详细补齐此操作的DOC文档
//     * TODO 修改此分页操作,hqlQueryBuilder,criteriaBuilder,detachCriteriaBuilder
//     *
//     * @param <T>
//     * @param pager
//     * @return
//     */
//    @Transactional(propagation = Propagation.SUPPORTS)
//    public <T extends Serializable> Pager<T> page(Pager<T> pager);

    /**
     * 分页查询
     *
     * @param pager  分页对象
     * @param clazz  类
     * @param params 条件
     * @param <T>
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T extends Serializable> void pager(Class clazz, Pager<T> pager, Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param pager  分页对象
     * @param clazz  类
     * @param params 条件
     * @param orders 排序字段
     * @param <T>
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T extends Serializable> void pager(Class clazz, Pager<T> pager, Map<String, Object> params, List<Sort> orders);

    /**
     * 分页查询
     *
     * @param pager  分页对象
     * @param clazz  类
     * @param orders 排序字段
     * @param <T>
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T extends Serializable> void pager(Class clazz, Pager<T> pager, List<Sort> orders);

    /**
     * 分页查询 hql
     *
     * @param pager  分页对象
     * @param hql    hql语句
     * @param params 参数
     * @param <T>
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T extends Serializable> void hqlPager(Pager<T> pager, String hql, Object[] params);

    /**
     * 分页查询  sql
     *
     * @param pager  分页对象
     * @param sql    sql语句
     * @param params 参数
     * @param <T>
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T extends Serializable> void sqlPager(Pager<T> pager, String sql, Object[] params);
}
