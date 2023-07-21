package com.publiccms.logic.service.cms;

import static org.springframework.util.StringUtils.collectionToDelimitedString;
import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import com.publiccms.common.tools.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publiccms.common.api.Config;
import com.publiccms.common.base.BaseService;
import com.publiccms.common.constants.CommonConstants;
import com.publiccms.common.handler.FacetPageHandler;
import com.publiccms.common.handler.PageHandler;
import com.publiccms.entities.cms.CmsCategory;
import com.publiccms.entities.cms.CmsContent;
import com.publiccms.entities.cms.CmsContentAttribute;
import com.publiccms.entities.cms.CmsContentFile;
import com.publiccms.entities.cms.CmsContentProduct;
import com.publiccms.entities.cms.CmsEditorHistory;
import com.publiccms.entities.sys.SysExtendField;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.entities.sys.SysUser;
import com.publiccms.logic.dao.cms.CmsContentDao;
import com.publiccms.logic.service.sys.SysExtendFieldService;
import com.publiccms.logic.service.sys.SysExtendService;
import com.publiccms.views.pojo.entities.ClickStatistics;
import com.publiccms.views.pojo.entities.CmsModel;
import com.publiccms.views.pojo.model.CmsContentParameters;
import com.publiccms.views.pojo.query.CmsContentQuery;
import com.publiccms.views.pojo.query.CmsContentSearchQuery;

/**
 *
 * CmsContentService
 * 
 */
@Service
@Transactional
public class CmsContentService extends BaseService<CmsContent> {
    protected final Log log = LogFactory.getLog(getClass());
    private static String[] DICTIONARY_INPUT_TYPES = { Config.INPUTTYPE_NUMBER, Config.INPUTTYPE_BOOLEAN, Config.INPUTTYPE_USER,
            Config.INPUTTYPE_DEPT, Config.INPUTTYPE_CONTENT, Config.INPUTTYPE_CATEGORY, Config.INPUTTYPE_DICTIONARY,
            Config.INPUTTYPE_CATEGORYTYPE, Config.INPUTTYPE_TAGTYPE };

    public static final String[] ignoreProperties = new String[] { "id", "siteId" };
    /**
     * 
     */
    public static final int STATUS_DRAFT = 0;
    /**
     * 
     */
    public static final int STATUS_NORMAL = 1;
    /**
     * 
     */
    public static final int STATUS_PEND = 2;
    /**
     * 
     */
    public static final int STATUS_REJECT = 3;

    /**
     * 
     */
    public static final Integer[] STATUS_NORMAL_ARRAY = new Integer[] { STATUS_NORMAL };

    @Resource
    private CmsCategoryService categoryService;
    @Resource
    private SysExtendService extendService;
    @Resource
    private SysExtendFieldService extendFieldService;
    @Resource
    private CmsTagService tagService;
    @Resource
    private CmsContentFileService contentFileService;
    @Resource
    private CmsEditorHistoryService editorHistoryService;
    @Resource
    private CmsContentProductService contentProductService;
    @Resource
    private CmsContentAttributeService attributeService;
    @Resource
    private CmsContentRelatedService cmsContentRelatedService;

    /**
     * @param queryEntity
     * @param containChild
     * @param orderField
     * @param pageIndex
     * @param pageSize
     * @param maxResults
     * @return results page
     */
    @Transactional(readOnly = true)
    public PageHandler query(CmsContentSearchQuery queryEntity, Boolean containChild, String orderField, Integer pageIndex,
            Integer pageSize, Integer maxResults) {
        queryEntity.setCategoryIds(getCategoryIds(containChild, queryEntity.getCategoryId(), queryEntity.getCategoryIds()));
        log.info("CmsContentSearchQuery=" + JsonUtils.getString(queryEntity));
        return dao.query(queryEntity, orderField, pageIndex, pageSize, maxResults);
    }

    /**
     * @param queryEntity
     * @param containChild
     * @param orderField
     * @param pageIndex
     * @param pageSize
     * @param maxResults
     * @return results page
     */
    @Transactional(readOnly = true)
    public FacetPageHandler facetQuery(CmsContentSearchQuery queryEntity, Boolean containChild, String orderField,
            Integer pageIndex, Integer pageSize, Integer maxResults) {
        queryEntity.setCategoryIds(getCategoryIds(containChild, queryEntity.getCategoryId(), queryEntity.getCategoryIds()));
        return dao.facetQuery(queryEntity, orderField, pageIndex, pageSize, maxResults);
    }

    /**
     * @param siteId
     * @param ids
     */
    public void index(short siteId, Serializable[] ids) {
        dao.index(siteId, ids);
    }

    /**
     * @param queryEntity
     * @param containChild
     * @param orderField
     * @param orderType
     * @param firstResult
     * @param pageIndex
     * @param pageSize
     * @param maxResults
     * @return results page
     */
    @Transactional(readOnly = true)
    public PageHandler getPage(CmsContentQuery queryEntity, Boolean containChild, String orderField, String orderType,
            Integer firstResult, Integer pageIndex, Integer pageSize, Integer maxResults) {
        queryEntity.setCategoryIds(getCategoryIds(containChild, queryEntity.getCategoryId(), queryEntity.getCategoryIds()));
        return dao.getPage(queryEntity, orderField, orderType, firstResult, pageIndex, pageSize, maxResults);
    }

    /**
     * @param siteId
     * @param quoteId
     * @return results list
     */
    @Transactional(readOnly = true)
    public List<CmsContent> getListByQuoteId(short siteId, Long quoteId) {
        return dao.getListByQuoteId(siteId, quoteId);
    }

    public List<CmsContent> getListByTopId(short siteId, Long topId) {
        return dao.getListByTopId(siteId, topId);
    }

    public CmsContent saveTagAndAttribute(SysSite site, Long userId, Long id, CmsContentParameters contentParameters,
            CmsModel cmsModel, Integer extendId, CmsContentAttribute attribute) {
        CmsContent entity = getEntity(id);
        if (null != entity) {
            Set<Serializable> tagIds = tagService.update(site.getId(), contentParameters.getTags());
            entity.setTagIds(collectionToDelimitedString(tagIds, CommonConstants.BLANK_SPACE));
            if (entity.isHasImages() || entity.isHasFiles()) {
                contentFileService.update(entity.getId(), userId, entity.isHasFiles() ? contentParameters.getFiles() : null,
                        entity.isHasImages() ? contentParameters.getImages() : null);// 更新保存图集，附件
            }
            if (entity.isHasProducts()) {
                contentProductService.update(site.getId(), entity.getId(), userId, contentParameters.getProducts());
            }

            List<SysExtendField> modelExtendList = cmsModel.getExtendList();
            List<SysExtendField> categoryExtendList = null;
            Map<String, String> map = ExtendUtils.getExtentDataMap(contentParameters.getModelExtendDataList(), modelExtendList);
            if (null != extendId && null != extendService.getEntity(extendId)) {
                categoryExtendList = extendFieldService.getList(extendId, null, null);
                Map<String, String> categoryMap = ExtendUtils.getSysExtentDataMap(contentParameters.getCategoryExtendDataList(),
                        categoryExtendList);
                if (CommonUtils.notEmpty(map)) {
                    map.putAll(categoryMap);
                } else {
                    map = categoryMap;
                }
            }

            dealAttribute(entity,site, modelExtendList, categoryExtendList, map, cmsModel,
                    entity.isHasFiles() ? contentParameters.getFiles() : null,
                    entity.isHasImages() ? contentParameters.getImages() : null,
                    entity.isHasProducts() ? contentParameters.getProducts() : null, attribute);

            saveEditorHistory(attributeService.getEntity(entity.getId()), attribute, site.getId(), entity.getId(), userId,
                    modelExtendList, categoryExtendList, map);// 保存编辑器字段历史记录

            attributeService.updateAttribute(entity.getId(), attribute);// 更新保存扩展字段，文本字段
            cmsContentRelatedService.update(entity.getId(), userId, contentParameters.getContentRelateds());// 更新保存推荐内容
        }
        return entity;
    }

    private void saveEditorHistory(CmsContentAttribute oldAttribute, CmsContentAttribute attribute, short siteId, long contentId,
            long userId, List<SysExtendField> modelExtendList, List<SysExtendField> categoryExtendList, Map<String, String> map) {
        if (null != oldAttribute) {
            if (CommonUtils.notEmpty(oldAttribute.getText()) && !oldAttribute.getText().equals(attribute.getText())) {
                CmsEditorHistory history = new CmsEditorHistory(siteId, CmsEditorHistoryService.ITEM_TYPE_CONTENT,
                        String.valueOf(contentId), "text", CommonUtils.getDate(), userId, oldAttribute.getText());
                editorHistoryService.save(history);
            }
            if (CommonUtils.notEmpty(oldAttribute.getData())) {
                Map<String, String> oldMap = ExtendUtils.getExtendMap(oldAttribute.getData());
                if (CommonUtils.notEmpty(modelExtendList)) {
                    for (SysExtendField extendField : modelExtendList) {
                        if (ArrayUtils.contains(Config.INPUT_TYPE_EDITORS, extendField.getInputType())) {
                            if (CommonUtils.notEmpty(oldMap) && CommonUtils.notEmpty(oldMap.get(extendField.getId().getCode()))
                                    && (CommonUtils.notEmpty(map) || !oldMap.get(extendField.getId().getCode())
                                            .equals(map.get(extendField.getId().getCode())))) {
                                CmsEditorHistory history = new CmsEditorHistory(siteId,
                                        CmsEditorHistoryService.ITEM_TYPE_CONTENT_EXTEND, String.valueOf(contentId),
                                        extendField.getId().getCode(), CommonUtils.getDate(), userId,
                                        map.get(extendField.getId().getCode()));
                                editorHistoryService.save(history);
                            }
                        }
                    }
                }
                if (CommonUtils.notEmpty(categoryExtendList)) {
                    for (SysExtendField extendField : categoryExtendList) {
                        if (ArrayUtils.contains(Config.INPUT_TYPE_EDITORS, extendField.getInputType())) {
                            if (CommonUtils.notEmpty(oldMap) && CommonUtils.notEmpty(oldMap.get(extendField.getId().getCode()))
                                    && (CommonUtils.notEmpty(map) || !oldMap.get(extendField.getId().getCode())
                                            .equals(map.get(extendField.getId().getCode())))) {
                                CmsEditorHistory history = new CmsEditorHistory(siteId,
                                        CmsEditorHistoryService.ITEM_TYPE_CONTENT_EXTEND, String.valueOf(contentId),
                                        extendField.getId().getCode(), CommonUtils.getDate(), userId,
                                        map.get(extendField.getId().getCode()));
                                editorHistoryService.save(history);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param siteId
     * @param categoryId
     * @param modelId
     * @param worker
     * @param batchSize
     */
    public void batchWorkId(short siteId, Integer categoryId, String modelId, BiConsumer<List<Serializable>, Integer> worker,
            int batchSize) {
        dao.batchWorkId(siteId, categoryId, modelId, worker, batchSize);
    }

    /**
     * @param siteId
     * @param categoryId
     * @param modelId
     * @param worker
     * @param batchSize
     */
    public void batchWorkContent(short siteId, Integer categoryId, String modelId, BiConsumer<List<CmsContent>, Integer> worker,
            int batchSize) {
        dao.batchWorkContent(siteId, categoryId, modelId, worker, batchSize);
    }

    public void rebuildSearchText(SysSite site, CmsModel cmsModel, List<SysExtendField> categoryExtendList,
            List<CmsContent> list) {
        for (CmsContent entity : list) {
            CmsContentAttribute attribute = attributeService.getEntity(entity.getId());
            if (null == attribute) {
                attribute = new CmsContentAttribute(entity.getId(), 0);
            }
            List<SysExtendField> modelExtendList = cmsModel.getExtendList();
            List<CmsContentFile> files = null;
            List<CmsContentFile> images = null;
            List<CmsContentProduct> products = null;
            if (entity.isHasFiles()) {
                files = contentFileService.getList(site.getId(), CmsFileUtils.OTHER_FILETYPES);
            }
            if (entity.isHasImages()) {
                images = contentFileService.getList(site.getId(), CmsFileUtils.IMAGE_FILETYPES);
            }
            if (entity.isHasProducts()) {
                products = contentProductService.getList(site.getId(), entity.getId());
            }
            dealAttribute(entity, site, modelExtendList, categoryExtendList, ExtendUtils.getExtendMap(attribute.getData()),
                    cmsModel, files, images, products, attribute);
            attributeService.updateAttribute(entity.getId(), attribute);
        }
    }

    private void dealAttribute(CmsContent entity, SysSite site, List<SysExtendField> modelExtendList,
            List<SysExtendField> categoryExtendList, Map<String, String> map, CmsModel cmsModel, List<CmsContentFile> files,
            List<CmsContentFile> images, List<CmsContentProduct> products, CmsContentAttribute attribute) {
        StringBuilder searchTextBuilder = new StringBuilder();
        String text = HtmlUtils.removeHtmlTag(attribute.getText());
        if (null != text) {
            attribute.setWordCount(text.length());
            if (cmsModel.isSearchable()) {
                searchTextBuilder.append(text).append(CommonConstants.BLANK_SPACE);
            }
            if (CommonUtils.empty(entity.getDescription())) {
                entity.setDescription(CommonUtils.keep(text, 300));
            }
        } else {
            attribute.setWordCount(0);
        }

        if (CommonUtils.notEmpty(map)) {
            Set<String> dictionaryValueList = new HashSet<>();
            Set<String> extendsFieldList = new HashSet<>();
            StringBuilder extendsTextBuilder = new StringBuilder();
            dealExtend(modelExtendList, dictionaryValueList, extendsFieldList, map, extendsTextBuilder, site);
            dealExtend(categoryExtendList, dictionaryValueList, extendsFieldList, map, extendsTextBuilder, site);
            if (CommonUtils.notEmpty(dictionaryValueList)) {
                attribute.setDictionaryValues(collectionToDelimitedString(dictionaryValueList, CommonConstants.BLANK_SPACE));
            } else {
                attribute.setDictionaryValues(null);
            }
            if (CommonUtils.notEmpty(extendsFieldList)) {
                attribute.setExtendsFields(collectionToCommaDelimitedString(extendsFieldList));
                attribute.setExtendsText(extendsTextBuilder.toString());
                searchTextBuilder.append(attribute.getExtendsText());
            } else {
                attribute.setExtendsFields(null);
                attribute.setExtendsText(null);
            }
            attribute.setData(ExtendUtils.getExtendString(map));
        } else {
            attribute.setData(null);
            attribute.setDictionaryValues(null);
            attribute.setExtendsFields(null);
            attribute.setExtendsText(null);
        }
        dealFiles(files, images, products, attribute);

        if (searchTextBuilder.length() > 0) {
            attribute.setSearchText(searchTextBuilder.toString());
        } else {
            attribute.setSearchText(null);
        }
    }

    private static void dealFiles(List<CmsContentFile> files, List<CmsContentFile> images, List<CmsContentProduct> products,
            CmsContentAttribute attribute) {
        StringBuilder filesTextBuilder = new StringBuilder();
        if (CommonUtils.notEmpty(files)) {
            for (CmsContentFile file : files) {
                filesTextBuilder.append(file.getDescription()).append(CommonConstants.BLANK_SPACE);
            }
        }
        if (CommonUtils.notEmpty(images)) {
            for (CmsContentFile file : images) {
                filesTextBuilder.append(file.getDescription()).append(CommonConstants.BLANK_SPACE);
            }
        }
        attribute.setFilesText(filesTextBuilder.toString());
        if (CommonUtils.notEmpty(products)) {
            BigDecimal minPrice = BigDecimal.ZERO;
            BigDecimal maxPrice = BigDecimal.ZERO;
            for (CmsContentProduct product : products) {
                if (0 == BigDecimal.ZERO.compareTo(maxPrice) || 0 < minPrice.compareTo(product.getPrice())) {
                    minPrice = product.getPrice();
                }
                if (0 > maxPrice.compareTo(product.getPrice())) {
                    maxPrice = product.getPrice();
                }
            }
            attribute.setMinPrice(minPrice);
            attribute.setMaxPrice(maxPrice);
        } else {
            attribute.setMinPrice(null);
            attribute.setMaxPrice(null);
        }
    }

    private static void dealExtend(List<SysExtendField> extendList, Set<String> dictionaryValueList, Set<String> extendsFieldList,
            Map<String, String> map, StringBuilder searchTextBuilder, SysSite site) {
        if (CommonUtils.notEmpty(extendList)) {
            for (SysExtendField extendField : extendList) {
                if (extendField.isSearchable()) {
                    if (ArrayUtils.contains(DICTIONARY_INPUT_TYPES, extendField.getInputType())) {
                        if (Config.INPUTTYPE_DICTIONARY.equals(extendField.getInputType())) {
                            String[] values = StringUtils.split(map.get(extendField.getId().getCode()), CommonConstants.COMMA);
                            if (CommonUtils.notEmpty(values)) {
                                for (String value : values) {
                                    dictionaryValueList.add(extendField.getId().getCode() + "_" + value);
                                }
                            }
                        } else {
                            String value = map.get(extendField.getId().getCode());
                            if (null != value) {
                                dictionaryValueList.add(extendField.getId().getCode() + "_" + value);
                            }
                        }
                    } else {
                        String value = map.get(extendField.getId().getCode());
                        if (null != value) {
                            if (ArrayUtils.contains(Config.INPUT_TYPE_EDITORS, extendField.getInputType())) {
                                map.put(extendField.getId().getCode(), HtmlUtils.cleanUnsafeHtml(value, site.getSitePath()));
                                value = HtmlUtils.removeHtmlTag(value);
                            }
                            if (CommonUtils.notEmpty(value)) {
                                extendsFieldList.add(extendField.getId().getCode());
                                searchTextBuilder.append(value).append(CommonConstants.BLANK_SPACE);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param siteId
     * @param user
     * @param ids
     * @return results list
     */
    public List<CmsContent> refresh(short siteId, SysUser user, Serializable[] ids) {
        List<CmsContent> entityList = new ArrayList<>();
        List<CmsContent> list = getEntitys(ids);
        Collections.reverse(list);
        for (CmsContent entity : list) {
            if (null != entity && STATUS_NORMAL == entity.getStatus() && siteId == entity.getSiteId()
                    && ControllerUtils.hasContentPermissions(user, entity)) {
                Date now = CommonUtils.getDate();
                if (now.after(entity.getPublishDate())) {
                    entity.setPublishDate(now);
                    entityList.add(entity);
                }
            }
        }
        return entityList;
    }

    /**
     * @param siteId
     * @param user
     * @param id
     * @return result
     */
    public CmsContent check(short siteId, SysUser user, Serializable id) {
        CmsContent entity = getEntity(id);
        if (null != entity && siteId == entity.getSiteId() && STATUS_DRAFT != entity.getStatus()
                && STATUS_NORMAL != entity.getStatus() && ControllerUtils.hasContentPermissions(user, entity)) {
            entity.setStatus(STATUS_NORMAL);
            entity.setCheckUserId(user.getId());
            entity.setCheckDate(CommonUtils.getDate());
        }
        return entity;
    }

    /**
     * @param siteId
     * @param user
     * @param ids
     * @return results list
     */
    public List<CmsContent> check(short siteId, SysUser user, Serializable[] ids) {
        List<CmsContent> entityList = new ArrayList<>();
        for (CmsContent entity : getEntitys(ids)) {
            if (null != entity && siteId == entity.getSiteId() && STATUS_DRAFT != entity.getStatus()
                    && STATUS_NORMAL != entity.getStatus() && ControllerUtils.hasContentPermissions(user, entity)) {
                entity.setStatus(STATUS_NORMAL);
                entity.setCheckUserId(user.getId());
                entity.setCheckDate(CommonUtils.getDate());
                entityList.add(entity);
            }
        }
        return entityList;
    }

    /**
     * @param siteId
     * @param user
     * @param ids
     * @return results list
     */
    public List<CmsContent> reject(short siteId, SysUser user, Serializable[] ids) {
        List<CmsContent> entityList = new ArrayList<>();
        for (CmsContent entity : getEntitys(ids)) {
            if (null != entity && siteId == entity.getSiteId() && STATUS_PEND == entity.getStatus()
                    && ControllerUtils.hasContentPermissions(user, entity)) {
                entity.setStatus(STATUS_REJECT);
                entity.setCheckUserId(user.getId());
                entity.setCheckDate(CommonUtils.getDate());
                entityList.add(entity);
            }
        }
        return entityList;
    }

    /**
     * @param siteId
     * @param user
     * @param ids
     * @return results list
     */
    public List<CmsContent> uncheck(short siteId, SysUser user, Serializable[] ids) {
        List<CmsContent> entityList = new ArrayList<>();
        for (CmsContent entity : getEntitys(ids)) {
            if (siteId == entity.getSiteId() && STATUS_NORMAL == entity.getStatus()
                    && ControllerUtils.hasContentPermissions(user, entity)) {
                entity.setStatus(STATUS_PEND);
                entityList.add(entity);
            }
        }
        return entityList;
    }

    /**
     * @param id
     * @param categoryList
     * @param category
     */
    public void saveQuote(Serializable id, List<CmsCategory> categoryList, CmsCategory category) {
        CmsContent entity = getEntity(id);
        if (CommonUtils.notEmpty(categoryList) && null != entity) {
            for (CmsCategory c : categoryList) {
                if (null != c && !category.getId().equals(c.getId())) {
                    CmsContent quote = new CmsContent(entity.getSiteId(), entity.getTitle(), entity.getUserId(), c.getId(),
                            entity.getModelId(), entity.isCopied(), entity.isContribute(), true, entity.isHasImages(),
                            entity.isHasFiles(), entity.isHasProducts(), entity.isHasStatic(), 0, 0, 0, BigDecimal.ZERO, 0, 0,
                            entity.getPublishDate(), entity.getCreateDate(), 0, entity.getStatus(), false);
                    quote.setUrl(entity.getUrl());
                    quote.setDescription(entity.getDescription());
                    quote.setAuthor(entity.getAuthor());
                    quote.setCover(entity.getCover());
                    quote.setEditor(entity.getEditor());
                    quote.setExpiryDate(entity.getExpiryDate());
                    quote.setQuoteContentId(entity.getId());
                    quote.setCheckUserId(entity.getCheckUserId());
                    quote.setCheckDate(entity.getCheckDate());
                    quote.setPublishDate(entity.getPublishDate());
                    save(quote);
                }
            }
        }
    }

    /**
     * @param id
     * @param contentParameters
     * @return categoryIds set
     */
    public Set<Serializable> updateQuote(Serializable id, CmsContentParameters contentParameters) {
        CmsContent entity = getEntity(id);
        Set<Serializable> categoryIds = new HashSet<>();
        if (null != entity) {
            for (CmsContent quote : getListByQuoteId(entity.getSiteId(), entity.getId())) {
                if (null != contentParameters.getContentIds() && contentParameters.getContentIds().contains(quote.getId())) {
                    quote.setUrl(entity.getUrl());
                    quote.setTitle(entity.getTitle());
                    quote.setDescription(entity.getDescription());
                    quote.setAuthor(entity.getAuthor());
                    quote.setCover(entity.getCover());
                    quote.setEditor(entity.getEditor());
                    quote.setExpiryDate(entity.getExpiryDate());
                    quote.setStatus(entity.getStatus());
                    quote.setCheckUserId(entity.getCheckUserId());
                    quote.setCheckDate(entity.getCheckDate());
                    quote.setPublishDate(entity.getPublishDate());
                    quote.setHasStatic(entity.isHasStatic());
                    quote.setHasFiles(entity.isHasFiles());
                    quote.setHasImages(entity.isHasImages());
                } else {
                    delete(quote.getId());
                    categoryIds.add(quote.getCategoryId());
                }
            }
        }
        return categoryIds;
    }

    /**
     * @param siteId
     * @param user
     * @param entity
     */
    public void save(short siteId, SysUser user, CmsContent entity) {
        entity.setSiteId(siteId);
        entity.setUserId(user.getId());
        entity.setDeptId(user.getDeptId());
        log.info("save cmsContent=" + JsonUtils.getString(entity));
        save(entity);
    }

    /**
     * @param entitys
     */
    public void updateStatistics(Collection<ClickStatistics> entitys) {
        for (ClickStatistics entityStatistics : entitys) {
            CmsContent entity = getEntity(entityStatistics.getId());
            if (null != entity) {
                entity.setClicks(entity.getClicks() + entityStatistics.getClicks());
            }
        }
    }

    /**
     * @param siteId
     * @param id
     * @param comments
     * @return
     */
    @Transactional
    public CmsContent updateComments(short siteId, Serializable id, int comments) {
        CmsContent entity = getEntity(id);
        if (null != entity && siteId == entity.getSiteId()) {
            entity.setComments(entity.getComments() + comments);
        }
        return entity;
    }

    /**
     * @param siteId
     * @param id
     * @param scoreUsers
     * @param scores
     * @return
     */
    @Transactional
    public CmsContent updateScores(short siteId, Serializable id, int scoreUsers, int scores) {
        CmsContent entity = getEntity(id);
        if (null != entity && siteId == entity.getSiteId()) {
            entity.setScores(entity.getScores() + scores);
            entity.setScoreUsers(entity.getScoreUsers() + scoreUsers);
            if (0 < entity.getScoreUsers()) {
                entity.setScore(new BigDecimal(entity.getScores()).divide(new BigDecimal(entity.getScoreUsers())));
            } else {
                entity.setScore(BigDecimal.ZERO);
            }
        }
        return entity;
    }

    /**
     * @param siteId
     * @param id
     * @param categoryId
     * @return result
     */
    public CmsContent updateCategoryId(short siteId, Serializable id, int categoryId) {
        CmsContent entity = getEntity(id);
        if (null != entity && siteId == entity.getSiteId()) {
            entity.setCategoryId(categoryId);
            dao.moveByTopId(siteId, entity.getId(), categoryId);
        }
        return entity;
    }

    /**
     * @param id
     * @param num
     * @return result
     */
    public CmsContent updateChilds(Serializable id, int num) {
        CmsContent entity = getEntity(id);
        if (null != entity) {
            entity.setChilds(entity.getChilds() + num);
        }
        return entity;
    }

    /**
     * @param id
     * @param modelId
     */
    public void changeModel(Serializable id, String modelId) {
        CmsContent entity = getEntity(id);
        if (null != entity) {
            entity.setModelId(modelId);
        }
    }

    /**
     * @param siteId
     * @param id
     * @param sort
     * @return result
     */
    public CmsContent sort(short siteId, Long id, int sort) {
        CmsContent entity = getEntity(id);
        if (null != entity && siteId == entity.getSiteId()) {
            entity.setSort(sort);
        }
        return entity;
    }

    /**
     * @param id
     * @param url
     * @param hasStatic
     * @return result
     */
    public CmsContent updateUrl(Serializable id, String url, boolean hasStatic) {
        CmsContent entity = getEntity(id);
        if (null != entity) {
            entity.setUrl(url);
            entity.setHasStatic(hasStatic);
        }
        return entity;
    }

    /**
     * @param siteId
     * @param categoryIds
     * @return number of data deleted
     */
    public int deleteByCategoryIds(short siteId, Integer[] categoryIds) {
        return dao.deleteByCategoryIds(siteId, categoryIds);
    }

    /**
     * @param siteId
     * @param user
     * @param ids
     * @return list of data deleted
     */
    public List<CmsContent> delete(short siteId, SysUser user, Serializable[] ids) {
        List<CmsContent> entityList = new ArrayList<>();
        for (CmsContent entity : getEntitys(ids)) {
            if (siteId == entity.getSiteId() && !entity.isDisabled() && ControllerUtils.hasContentPermissions(user, entity)) {
                if (null == entity.getParentId()) {
                    for (CmsContent quote : getListByQuoteId(siteId, entity.getId())) {
                        quote.setDisabled(true);
                    }
                } else {
                    updateChilds(entity.getParentId(), -1);
                }
                entity.setDisabled(true);
                entityList.add(entity);
            }
        }
        return entityList;
    }

    private Integer[] getCategoryIds(Boolean containChild, Integer categoryId, Integer[] categoryIds) {
        if (CommonUtils.empty(categoryId)) {
            return categoryIds;
        } else if (null != containChild && containChild) {
            CmsCategory category = categoryService.getEntity(categoryId);
            if (null != category) {
                String[] categoryStringIds = ArrayUtils.add(
                        StringUtils.splitByWholeSeparator(category.getChildIds(), CommonConstants.COMMA_DELIMITED),
                        String.valueOf(categoryId));
                categoryIds = new Integer[categoryStringIds.length];
                for (int i = 0; i < categoryStringIds.length; i++) {
                    categoryIds[i] = Integer.parseInt(categoryStringIds[i]);
                }
            } else {
                categoryIds = new Integer[] { categoryId };
            }
        } else {
            categoryIds = new Integer[] { categoryId };
        }
        return categoryIds;
    }

    public CmsContent copy(SysSite site, CmsContent content, CmsCategory category, int status, Long userId) {
        if (null != content && null != category) {
            Date now = CommonUtils.getDate();
            CmsContent entity = new CmsContent();
            BeanUtils.copyProperties(content, entity, ignoreProperties);
            entity.setSiteId(category.getSiteId());
            entity.setStatus(status);
            entity.setPublishDate(now);
            entity.setCreateDate(now);
            entity.setClicks(0);
            entity.setScores(0);
            entity.setComments(0);
            entity.setTagIds(null);
            entity.setCopied(true);
            if (status == STATUS_NORMAL) {
                entity.setCheckUserId(userId);
                entity.setCheckDate(now);
            } else {
                entity.setCheckUserId(null);
                entity.setCheckDate(null);
            }
            entity.setUserId(userId);
            entity.setCategoryId(category.getId());
            save(entity);
            CmsContentAttribute attribute = attributeService.getEntity(content.getId());
            if (null != attribute) {
                CmsContentAttribute attributeEntity = new CmsContentAttribute();
                BeanUtils.copyProperties(attribute, attributeEntity, CmsContentAttributeService.ignoreProperties);
                attributeEntity.setContentId(entity.getId());
                attributeEntity.setSource(site.getName());
                attributeEntity.setSourceUrl(content.getUrl());
                attributeService.save(attributeEntity);
            }
            @SuppressWarnings("unchecked")
            List<CmsContentFile> fileList = (List<CmsContentFile>) contentFileService
                    .getPage(content.getId(), null, null, null, null, null, null).getList();
            if (CommonUtils.notEmpty(fileList)) {
                List<CmsContentFile> resultList = new ArrayList<>();
                for (CmsContentFile file : fileList) {
                    CmsContentFile contentFile = new CmsContentFile();
                    BeanUtils.copyProperties(file, contentFile, CmsContentFileService.ignoreProperties);
                    contentFile.setContentId(entity.getId());
                    contentFile.setUserId(userId);
                    resultList.add(contentFile);
                }
                contentFileService.save(resultList);
            }
            @SuppressWarnings("unchecked")
            List<CmsContentProduct> productList = (List<CmsContentProduct>) contentProductService
                    .getPage(entity.getSiteId(), entity.getId(), null, null, null, null, null, null, null, null).getList();
            if (CommonUtils.notEmpty(productList)) {
                List<CmsContentProduct> resultList = new ArrayList<>();
                for (CmsContentProduct product : productList) {
                    CmsContentProduct contentProduct = new CmsContentProduct();
                    BeanUtils.copyProperties(product, contentProduct, CmsContentProductService.ignoreProperties);
                    contentProduct.setSiteId(category.getSiteId());
                    contentProduct.setContentId(entity.getId());
                    contentProduct.setUserId(userId);
                    resultList.add(contentProduct);
                }
                contentProductService.save(resultList);
            }
            return entity;
        }
        return null;
    }

    /**
     * @param siteId
     * @param ids
     * @return
     */
    public List<CmsContent> recycle(short siteId, Serializable[] ids) {
        List<CmsContent> entityList = new ArrayList<>();
        for (CmsContent entity : getEntitys(ids)) {
            if (siteId == entity.getSiteId() && entity.isDisabled()) {
                entity.setDisabled(false);
                entityList.add(entity);
                if (null != entity.getParentId()) {
                    updateChilds(entity.getParentId(), 1);
                } else {
                    dao.deleteByTopId(siteId, entity.getId());
                }
            }
        }
        return entityList;
    }

    /**
     * @param siteId
     * @param ids
     */
    public void realDelete(Short siteId, Long[] ids) {
        for (CmsContent entity : getEntitys(ids)) {
            if (siteId == entity.getSiteId() && entity.isDisabled()) {
                delete(entity.getId());
                attributeService.delete(entity.getId());
            }
        }
    }

    /**
     * @param entityList
     * @return
     */
    public List<CmsContent> batchUpdate(List<CmsContent> entityList) {
        List<CmsContent> resultList = new ArrayList<>();
        if (CommonUtils.notEmpty(entityList)) {
            for (CmsContent entity : entityList) {
                if (null == update(entity.getId(), entity, ignoreProperties)) {
                    resultList.add(entity);
                }
            }
        }
        return resultList;
    }

    @Resource
    private CmsContentDao dao;
}