package cc.mrbird.febs.job.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.job.dao.JobLogMapper;
import cc.mrbird.febs.job.domain.JobLog;
import cc.mrbird.febs.job.service.JobLogService;
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
@Service("JobLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements JobLogService {

    @Override
    public IPage findJobLogs(QueryRequest request, JobLog jobLog) {
        try {
            QueryWrapper<JobLog> queryWrapper = new QueryWrapper<>();

            if (StringUtils.isNotBlank(jobLog.getBeanName())) {
                queryWrapper.lambda().eq(JobLog::getBeanName, jobLog.getBeanName());
            }
            if (StringUtils.isNotBlank(jobLog.getMethodName())) {
                queryWrapper.lambda().eq(JobLog::getMethodName, jobLog.getMethodName());
            }
            if (StringUtils.isNotBlank(jobLog.getParams())) {
                queryWrapper.lambda().like(JobLog::getParams, jobLog.getParams());
            }
            if (StringUtils.isNotBlank(jobLog.getStatus())) {
                queryWrapper.lambda().eq(JobLog::getStatus, jobLog.getStatus());
            }

            if (StringUtils.isNotBlank(jobLog.getCreateTimeFrom()) && StringUtils.isNotBlank(jobLog.getCreateTimeTo())) {
                queryWrapper.lambda()
                        .ge(JobLog::getCreateTime, jobLog.getCreateTimeFrom())
                        .le(JobLog::getCreateTime, jobLog.getCreateTimeTo());
            }
            queryWrapper.lambda().orderByAsc(JobLog::getCreateTime);
            Page page = new Page(request.getPageNum(), request.getPageSize());
            return this.page(page, queryWrapper);

        } catch (Exception e) {
            log.error("获取调度日志信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void saveJobLog(JobLog log) {
        this.save(log);
    }

    @Override
    @Transactional
    public void deleteJobLogs(String[] jobLogIds) {
        List<String> list = Arrays.asList(jobLogIds);
        this.baseMapper.deleteBatchIds(list);
    }

}
