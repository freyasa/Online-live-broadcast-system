package com.niit.onlivestream.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niit.onlivestream.domain.RoomInfo;
import generator.service.RoomInfoService;
import com.niit.onlivestream.mapper.RoomInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 29756
* @description 针对表【room_info】的数据库操作Service实现
* @createDate 2024-01-15 19:09:48
*/
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
    implements RoomInfoService{

}




