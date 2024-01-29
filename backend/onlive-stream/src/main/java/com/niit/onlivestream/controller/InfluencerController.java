package com.niit.onlivestream.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.niit.onlivestream.common.BaseResponse;
import com.niit.onlivestream.common.ErrorCode;
import com.niit.onlivestream.util.ResultUtils;
import com.niit.onlivestream.domain.RoomInfo;
import com.niit.onlivestream.domain.RoomLog;
import com.niit.onlivestream.exception.BusinessException;
import com.niit.onlivestream.service.RoomInfoService;
import com.niit.onlivestream.service.RoomLogService;
import com.niit.onlivestream.vo.OnliveRequest.StreamRequest;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.niit.onlivestream.contant.RedisDataUse.LiveRedisTemplate;

@RestController
@RequestMapping("/influencer")
public class InfluencerController {

    @Resource
    private RoomInfoService roomInfoService;
    @Resource
    private RoomLogService roomLogService;


    @Resource(name = LiveRedisTemplate)
    private RedisTemplate<String, Object> liveTemplate;


    /**
     *
     * @param uuid 用户名
     * @return 直播序列号及相关信息
     */
    @GetMapping  ("/getRoomInfo")
    public BaseResponse<RoomInfo> getRoomInfoByUserId(@RequestParam String uuid){
        if(uuid==null)
            throw new BusinessException(ErrorCode.NULL_ERROR);
        RoomInfo roomInfo = roomInfoService.getRoomById(uuid);
        return ResultUtils.success(roomInfo);
    }


    /**
     * 开始直播
     * @param request  RoomInfo
     * @return  直播成功
     */
    @PostMapping("/startlive")
    public BaseResponse<String> startLive(@RequestBody StreamRequest request){
        if(request==null)
            throw new BusinessException(ErrorCode.NULL_ERROR);
        if (StringUtils.isAnyBlank(request.getUuid()))
            throw new BusinessException(ErrorCode.NULL_ERROR,"部分数据为空");
        int liveId = request.getLiveid();
        String userId = request.getUuid();
        //查一下用户ID和直播序列号 是不是 一一对应
        QueryWrapper<RoomInfo> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("liveid",liveId);
        queryWrapper.eq("uuid",userId);
        RoomInfo roomInfo = roomInfoService.getOne(queryWrapper);
        if(roomInfo==null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"序列号与用户ID不匹配");

        //先持久化部分 roomLog
        RoomLog roomLog =new RoomLog();
        roomLog.setCreatetime(new Date());
        roomLog.setRoomforeignid(roomInfo.getLiveid());
        roomLog.setName(roomInfo.getRoomname());
        roomLog.setProfile(roomInfo.getProfile());
        roomLog.setRoomAvatar(roomInfo.getRoomAvatar());
        roomLog.setPartitionid(roomInfo.getPartitionid());
        // 存redis 一部分roomlog
        // Redis 加入到开播队列
        ValueOperations<String,Object> liveDB = liveTemplate.opsForValue();
        String live = String.valueOf(liveId);
        liveDB.set(live,roomLog,3, TimeUnit.DAYS);
        return ResultUtils.success("开始直播成功");
    }

    @PostMapping("endlive")
    public BaseResponse<String> endLive(@RequestBody StreamRequest request){
        if(request==null)
            throw new BusinessException(ErrorCode.NULL_ERROR);
        if (StringUtils.isAnyBlank(request.getUuid()))
            throw new BusinessException(ErrorCode.NULL_ERROR,"部分数据为空");
        int liveId = request.getLiveid();
        String userId = request.getUuid();
        //查一下用户ID和直播序列号 是不是 一一对应
        QueryWrapper<RoomInfo> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("liveid",liveId);
        queryWrapper.eq("uuid",userId);
        RoomInfo roomInfo = roomInfoService.getOne(queryWrapper);
        if(roomInfo==null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"序列号与用户ID不匹配");

        //从RedIS里面拿取数据
        String liveID = String.valueOf(request.getLiveid());
        ValueOperations<String,Object> liveDB = liveTemplate.opsForValue();
        RoomLog roomLog = (RoomLog) liveDB.get(liveID);
        if(roomLog==null)
            throw new BusinessException(ErrorCode.TOKEN_OUTTIME,"直播间信息未及时关闭三天自动关闭");
        roomLog.setStoptime(new Date());
        boolean logResult = roomLogService.save(roomLog);
        if(!logResult)
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"保存失败");
        return ResultUtils.success("结束直播成功");
    }


    /**
     * 根据主键liveID查询是否直播
     * 是 返回房间信息
     * 否 返回null
     */

    @GetMapping("/findIslive")
    public BaseResponse<RoomInfo> getRoomByLiveId(@RequestParam Integer liveID){
        if(liveID==null)
            throw new BusinessException(ErrorCode.NULL_ERROR,"没有房间号");
        return null;
    }


    /**
     * get请求
     * @return  得到所有直播间
     */
//    @GetMapping("/roomAll")
//    public BaseResponse<ArrayList<RoomInfo>> getRoomInfoAll(){
//        Set<String> elements =stringRedisTemplate.opsForSet().members(liveQueue);
//        if(elements==null)
//            return ResultUtils.success(null,"无人开播");
//        ArrayList<RoomInfo> roomInfos = new ArrayList<>();
//        for (String json:elements) {
//            System.out.println("从Redis里面拿到的字符串是:"+json);
//            RoomInfo roomInfo = new RoomInfo();
//            try {
//                 roomInfo = JSON.parseObject(json,RoomInfo.class);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            roomInfos.add(roomInfo);
//        }
//        return ResultUtils.success(roomInfos);
//    }



}
