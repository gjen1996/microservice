package com.glen.glengen.dao;

import com.glen.glengen.entity.TEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Copyright (c), 2012-2099 NetWork.Co.,Ltd
 * @package
 * @FileName:
 * @Author:wangtai
 * @Date:
 * @Description:
 * @Vesion:1.0
 */
public interface HqlDao {

    /**
	 * 通过一个实体保存一条记录
	 * 返回保存记录的实体Id
	 * @param entity
	 * @return Serializable
	 */
    Serializable save(TEntity entity);

	/**
	 * 删除一个实体
	 * @param entity
	 * @throws
	 */
	void delete(TEntity entity);

	/**
	 * 删除实体集合
	 * @param entitys
	 */
	void delete(TEntity[] entitys);

	/**
	 * 根据id删除实体类
	 * @param clazz
	 * @param id
	 */
	void delete(Class clazz, String id);

	/**
	 *
	 * @param clazz
	 * @param ids
	 */
	void delete(Class clazz, String[] ids);

	/**
	 * 根据id查找实体
	 * @param entityClass
	 * @param id
	 * @param <T>
	 * @return
	 */
	<T> T findById(Class entityClass, String id);

	/**
	 * 更新实体
	 * @param entity
	 */
	void update(TEntity entity) ;

	/**
	 * 根据hql查询集合
	 * @param hql
	 * @param <T>
	 * @return
	 */
    <T> List<T> search(String hql) ;

    <T> List<T> search(String hql, Map<String, Object> param) ;

    <T> T load(String hql) ;

	<T> T load(String hql, Map<String, Object> param);
}
