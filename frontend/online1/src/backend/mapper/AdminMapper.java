package com.niit.online_music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niit.online_music.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ProjectName: online_music
 * @Package: com.niit.online_music.mapper
 * @ClassName: AdminMapper
 * @Author: zjh
 * @description:
 * @date: 2024/1/17 10:39
 * @version: 1.0
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}
