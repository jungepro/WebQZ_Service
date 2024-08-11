package org.jeecg.modules.coderQ.service.impl;

import org.jeecg.modules.coderQ.entity.News;
import org.jeecg.modules.coderQ.mapper.NewsMapper;
import org.jeecg.modules.coderQ.service.INewsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: bk_news
 * @Author:
 * @Date:   2023-03-25
 * @Version: V1.0
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
