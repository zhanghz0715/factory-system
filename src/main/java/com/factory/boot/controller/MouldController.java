package com.factory.boot.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.Image;
import com.factory.boot.model.Mould;
import com.factory.boot.model.Type;
import com.factory.boot.service.ImageService;
import com.factory.boot.service.MouldService;
import com.factory.boot.service.ProductService;
import com.factory.boot.service.TypeService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2020-05-14
 */
@RestController
@RequestMapping("/mould")
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MouldController extends BaseController {

    @Autowired
    private ProductService productService;


    @Autowired
    private MouldService mouldService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private ImageService imageService;



    @PostMapping("/page")
    public AjaxJson getPage(Page<Mould> page, String factoryId, String name) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(factoryId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            EntityWrapper entityWrapper = new EntityWrapper<Type>();
            if (!ObjectUtils.isEmpty(factoryId)) {
                entityWrapper.eq("factory_id", factoryId);
            }
            if (!ObjectUtils.isEmpty(name)) {
                entityWrapper.like("name", name);
            }
            page = mouldService.selectPage(page, entityWrapper.orderBy("status", true).orderBy("createTime", false));
            if (!ObjectUtils.isEmpty(page) && !ObjectUtils.isEmpty(page.getRecords())) {
                List<Map> mapList = productService.getAverageWeight();
                Map<String, Object> typeMap = new HashMap<>();
                if (!ObjectUtils.isEmpty(mapList) && mapList.size() > 0) {
                    for (Map map : mapList) {
                        String typeId = (String) map.get("typeId");
                        typeMap.put(typeId, map.get("averageWeight"));
                    }
                }
                for (Mould mould : page.getRecords()) {
                    Type type = typeService.selectById(mould.getTypeId());
                    mould.setTypeName(type.getName());
                    mould.setTypeLength(type.getLength());
                    mould.setTheoryWeight(type.getWeight());
                    if (!ObjectUtils.isEmpty(typeMap.get(type.getId()))) {
                        Double averageWeight = (Double) typeMap.get(type.getId());
                        mould.setAverageWeight(Double.parseDouble(String.format("%.2f", type.getLength() * averageWeight)));
                    }
                    if (mould.getStatus() == 1) {
                        mould.setStatusName("正常");
                    } else {
                        mould.setStatusName("报废");
                    }

                }
                ajaxJson.setData(page);
            }
            ajaxJson.setData(page);

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    @PostMapping("/myMouldPage")
    public AjaxJson myMouldPage(Page<Map> page, String factoryId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(factoryId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("factoryId",factoryId);
            page = mouldService.findPage(page,params);
            if (!ObjectUtils.isEmpty(page) && !ObjectUtils.isEmpty(page.getRecords())) {
                for (Map map : page.getRecords()) {
                   Integer status = (Integer) map.get("status");
                    if (status == 1) {
                        map.put("statusNmae","正常");
                    } else {
                        map.put("statusNmae","报废");
                    }
                    if(!ObjectUtils.isEmpty(map.get("image"))){
                        String base64String = Base64.encodeBase64String((byte[]) map.get("image"));
                        map.put("image","data:image/png;base64,"+base64String);
                    }
                }
            }
            ajaxJson.setData(page);

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    /**
     * 根据产品类型列表
     *
     * @return
     */
    @PostMapping("/list")
    public AjaxJson wxLogin() {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            List<Mould> mouldList = mouldService.selectList(new EntityWrapper<Mould>().orderBy("createTime", false));
            ajaxJson.setData(mouldList);
            return ajaxJson;

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
    }


    @PostMapping("/save")
    public AjaxJson save(Mould mould) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(mould) || ObjectUtils.isEmpty(mould.getFactoryId())) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            List<Mould> typeList = mouldService.selectList(new EntityWrapper<Mould>().eq("factory_id", mould.getFactoryId()).eq("supplier", mould.getSupplier()).eq("name", mould.getName()));
            if (typeList.size() > 0) {
                return new AjaxJson("该产品已经存在");
            }
            mould.setStatus(Mould.STATUS_ONE);
            mouldService.insert(mould);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }

    @PostMapping("/update")
    public AjaxJson update(Mould mould) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(mould) || ObjectUtils.isEmpty(mould.getId())) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            Mould mould1 = mouldService.selectById(mould.getId());
            List<Mould> typeList = mouldService.selectList(new EntityWrapper<Mould>().eq("factory_id", mould1.getFactoryId()).eq("supplier", mould.getSupplier()).eq("name", mould.getName()));
            if (typeList.size() > 0 && !mould.getSupplier().equals(mould1.getSupplier()) && !mould.getName().equals(mould1.getName())) {
                return new AjaxJson("该产品已经存在");
            }
            mouldService.updateById(mould);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    @PostMapping("/delete")
    public AjaxJson delete(String mouldId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(mouldId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            mouldService.deleteById(mouldId);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }

    @PostMapping("/getDetail")
    public AjaxJson getDetail(String mouldId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(mouldId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            Mould mould = mouldService.selectById(mouldId);
            if (ObjectUtils.isEmpty(mould)) {
                return new AjaxJson("参数错误，请检查参数是否正确！");
            }
            Type type = typeService.selectById(mould.getTypeId());
            Image image = imageService.selectById(mould.getImageId());
            if (!ObjectUtils.isEmpty(type)) {
                mould.setTypeName(type.getName());
            }
            if (!ObjectUtils.isEmpty(image)) {
                String base64String = Base64.encodeBase64String(image.getImage());
                mould.setImage(base64String);
            }
            ajaxJson.setData(mould);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


}

