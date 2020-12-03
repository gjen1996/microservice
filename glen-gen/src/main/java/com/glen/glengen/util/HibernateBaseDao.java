package com.glen.glengen.util;/**
 * @author Glen
 * @create 2020- 09-2020/9/10-10:20
 * @Description
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Glen
 * @create 2020/9/10 10:20 
 * @Description
 */
public class HibernateBaseDao<E> {
    /** 泛型的类型 */
    private final Class<E> entityClass;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /** 获取泛型的类型  */
    @SuppressWarnings("rawtypes")
    private static Class getSuperClassGenricType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    /** HibernateBaseDao的构造方法 */
    @SuppressWarnings("unchecked")
    public HibernateBaseDao() {
        this.entityClass = getSuperClassGenricType(this.getClass(), 0);
    }

    /** 获取session */
    @Transactional
    protected Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();//这种方式需要手动关闭session
        // 这种方式会自动关闭session，但是要配置current_session_context_class，并且需要使用事务
        //return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
    }

    public E get(Serializable id) {
        Assert.notNull(id, "id is required");
        return (E) this.getSession().get(this.entityClass, id);
    }
}