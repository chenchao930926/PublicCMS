package com.publiccms.common.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.publiccms.common.tools.CommonUtils;

/**
 *
 * BaseService
 * 
 * @param <E>
 * 
 */
@Transactional
public abstract class BaseService<E> {
    protected final Log log = LogFactory.getLog(getClass());
    @Autowired
    protected BaseDao<E> basedao;

    /**
     * @param id
     * @return entity
     */
    public E getEntity(Serializable id) {
        return null != id ? basedao.getEntity(id) : null;
    }

    /**
     * @param id
     * @param pk
     * @return entity
     */
    public E getEntity(Serializable id, String pk) {
        return basedao.getEntity(id, pk);
    }

    /**
     * @param ids
     * @param pk
     * @return entitys list
     */
    public List<E> getEntitys(Serializable[] ids, String pk) {
        return basedao.getEntitys(ids, pk);
    }

    /**
     * @param ids
     * @param pk
     * @return entitys list
     */
    public List<E> getEntitys(Collection<Serializable> ids, String pk) {
        return basedao.getEntitys(ids, pk);
    }

    /**
     * @param ids
     * @return entitys list
     */
    public List<E> getEntitys(Serializable[] ids) {
        return basedao.getEntitys(ids);
    }

    /**
     * @param ids
     * @return entitys list
     */
    public List<E> getEntitys(Collection<Serializable> ids) {
        return basedao.getEntitys(ids);
    }

    /**
     * @param ids
     */
    public void delete(Collection<Serializable> ids) {
        for (Serializable id : ids) {
            delete(id);
        }
    }

    /**
     * @param ids
     */
    public void delete(Serializable[] ids) {
        for (Serializable id : ids) {
            delete(id);
        }
    }

    /**
     * @param id
     */
    public void delete(Serializable id) {
        basedao.delete(id);
    }

    /**
     * @param id
     * @param newEntity
     * @param ignoreProperties
     * @return entity
     */
    public E update(Serializable id, E newEntity, String[] ignoreProperties) {
        E entity = getEntity(id);
        if (null != entity) {
            BeanUtils.copyProperties(basedao.init(newEntity), entity, ignoreProperties);
        }
        return entity;
    }

    /**
     * @param id
     * @param newEntity
     * @return entity
     */
    public E update(Serializable id, E newEntity) {
        E entity = getEntity(id);
        if (null != entity) {
            BeanUtils.copyProperties(basedao.init(newEntity), entity);
        }
        return entity;
    }

    /**
     * @param entity
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void saveOrUpdate(E entity) {
        basedao.saveOrUpdate(entity);
    }

    /**
     * @param entity
     */
    public void save(E entity) {
        basedao.save(entity);
    }

    public long getId() {
        return basedao.getId();
    }

    /**
     * @param entityList
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void saveOrUpdate(List<E> entityList) {
        if (CommonUtils.notEmpty(entityList)) {
            for (E entity : entityList) {
                saveOrUpdate(entity);
            }
        }
    }

    /**
     * @param entityList
     */
    public void save(List<E> entityList) {
        if (CommonUtils.notEmpty(entityList)) {
            for (E entity : entityList) {
                save(entity);
            }
        }
    }
}
