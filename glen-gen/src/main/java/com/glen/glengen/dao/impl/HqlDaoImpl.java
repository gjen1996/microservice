package com.glen.glengen.dao.impl;

import com.glen.glengen.dao.HqlDao;
import com.glen.glengen.entity.TEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Copyright (c), 2012-2019, NetWork.Co.,Ltd
 * @package
 * @FileName:
 * @Author:wangtai
 * @Date:
 * @Description:
 * @Vesion:1.0
 */
@Slf4j
public class HqlDaoImpl implements HqlDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * 开启一个session
     * 需要手动开关session
     * @return
     */
    public Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    /**
     * 托管给spring 需要引入一个配置
     * @return
     */
    public Session getCurrentSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
    }
    @Override
    public <T> T findById(Class entityClass, String id){
        log.info("jjinru这里");
        T t = null;
        log.info("getSession():"+getSession());
        try {
            t = (T) getCurrentSession().get(entityClass, id);
            log.info("t:"+t);
        } catch (org.hibernate.ObjectNotFoundException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
    @Override
    public Serializable save(TEntity entity){
        Serializable s = null;
        Session session = getCurrentSession();
        try {
            s = session.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public void delete(TEntity entity){
        try {
            getCurrentSession().delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(TEntity[] entitys) {
        try {
            Session session = getCurrentSession();
            for (TEntity entity : entitys) {
                session.delete(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Class clazz, String id){
        try {
            Session session = getCurrentSession();
            TEntity o = this.findById(clazz, id);
            //为了支持spingboot不使用newInstance实例对象
            //TEntity o = (TEntity) clazz.newInstance();
            //o.setId(id);
            session.delete(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Class clazz, String[] ids) {
        try {
            Session session = getCurrentSession();
            for (String id : ids) {
                TEntity o = this.findById(clazz, id);
                //为了支持spingboot不使用newInstance实例对象
                //TEntity o = (TEntity) clazz.newInstance();
                //o.setId(id);
                session.delete(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public <T> T findById(Class entityClass, String id){
//        log.info("jjinru这里");
//        T t = null;
//        log.info("getSession():"+getSession());
//        try {
//            t = (T) getCurrentSession().get(entityClass, id);
//            log.info("t:"+t);
//        } catch (org.hibernate.ObjectNotFoundException e) {
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return t;
//    }

    @Override
    public void update(TEntity entity) {
        try {
            getCurrentSession().update(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> List<T> search(String hql){
        List<T> list = null;
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(hql);
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public <T> List<T> search(String hql, Map<String, Object> param)  {
        List<T> list = null;
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(hql);
            this.setQueryParams(query, param);
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public <T> T load(String hql)  {
        T t = null;
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(hql);
            List<T> list = query.list();
            if (null != list && list.size() != 0) {
                t = list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public <T> T load(String hql, Map<String, Object> param) {
        T t = null;
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(hql);
            this.setQueryParams(query, param);
            List<T> list = query.list();
            if (null != list && list.size() != 0) {
                t = list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 计算总条数
     * @param hql
     * @param page
     * @throws DBException
     */

    /**
     * 构造CountHql
     * @param hql
     * @return
     */

    /**
     * 添加参数
     * @param query
     * @param param
     */
    private void setQueryParams(Query query, Map<String, Object> param)  {
        for (String s : param.keySet()) {
            query.setParameter(s, param.get(s));
        }
    }

}
