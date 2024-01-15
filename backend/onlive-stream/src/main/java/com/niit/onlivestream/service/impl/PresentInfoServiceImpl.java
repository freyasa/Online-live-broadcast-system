package com.niit.onlivestream.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niit.onlivestream.domain.PresentInfo;
import generator.service.PresentInfoService;
import com.niit.onlivestream.mapper.PresentInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 29756
* @description 针对表【present_info】的数据库操作Service实现
* @createDate 2024-01-15 19:09:48
*/
@Service
public class PresentInfoServiceImpl extends ServiceImpl<PresentInfoMapper, PresentInfo>
    implements PresentInfoService{

}




