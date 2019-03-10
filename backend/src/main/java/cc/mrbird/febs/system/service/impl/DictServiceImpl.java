package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.dao.DictMapper;
import cc.mrbird.febs.system.domain.Dict;
import cc.mrbird.febs.system.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service("dictService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public IPage findDicts(QueryRequest request, Dict dict) {
        try {
            QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();

            if (StringUtils.isNotBlank(dict.getKeyy())) {
                queryWrapper.lambda().eq(Dict::getKeyy, dict.getKeyy());
            }
            if (StringUtils.isNotBlank(dict.getValuee())) {
                queryWrapper.lambda().eq(Dict::getValuee, dict.getValuee());
            }
            if (StringUtils.isNotBlank(dict.getTableName())) {
                queryWrapper.lambda().eq(Dict::getTableName, dict.getTableName());
            }
            if (StringUtils.isNotBlank(dict.getFieldName())) {
                queryWrapper.lambda().eq(Dict::getFieldName, dict.getFieldName());
            }

            Page page = new Page();
            FebsUtil.handleSort(request, page, null);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDict(Dict dict) {
        this.save(dict);
    }

    @Override
    @Transactional
    public void updateDict(Dict dict) {
        this.baseMapper.updateById(dict);
    }

    @Override
    @Transactional
    public void deleteDicts(String[] dictIds) {
        List<String> list = Arrays.asList(dictIds);
        this.baseMapper.deleteBatchIds(list);
    }
}
