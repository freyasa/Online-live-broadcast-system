package com.niit.onlivestream.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niit.onlivestream.domain.PartitionInfo;
import generator.service.PartitionInfoService;
import com.niit.onlivestream.mapper.PartitionInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 29756
* @description 针对表【partition_info】的数据库操作Service实现
* @createDate 2024-01-15 19:09:48
*/
@Service
public class PartitionInfoServiceImpl extends ServiceImpl<PartitionInfoMapper, PartitionInfo>
    implements PartitionInfoService{

}




