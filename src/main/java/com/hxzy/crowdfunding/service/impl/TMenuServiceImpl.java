package com.hxzy.crowdfunding.service.impl;

import com.hxzy.crowdfunding.mapper.TMenuMapper;
import com.hxzy.crowdfunding.service.TMenuService;
import com.hxzy.crowdfunding.vo.tMenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TMenuServiceImpl implements TMenuService {

    @Autowired
    private TMenuMapper tMenuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void selectMenuAll(HttpSession session) {
        Object menu = redisTemplate.opsForValue().get("menu");

        if (menu == null){
            List<tMenuVo> tMenuVos = tMenuMapper.selectMenuAll();
            List<tMenuVo> parentsMenu = new ArrayList<>();

            Map<Integer,tMenuVo> map = new HashMap<>();

            for (tMenuVo tMenuVo : tMenuVos) {
                if (tMenuVo.getPid() == 0)
                    parentsMenu.add(tMenuVo);

                map.put(tMenuVo.getId(),tMenuVo);
            }

            for (tMenuVo menuVo : tMenuVos) {
                if (menuVo.getPid() != 0 ){
                    tMenuVo tMenuVo = map.get(menuVo.getPid());
                    tMenuVo.getChildren().add(menuVo);
                }
            }
            // 将菜单保存至 Redis
            redisTemplate.opsForValue().set("menu",parentsMenu);

            log.info("查询数据库,获取菜单---{}",parentsMenu);
        }else {
            log.info("通过redis获取菜单");
            session.setAttribute("menu",menu);

        }

    }
}
