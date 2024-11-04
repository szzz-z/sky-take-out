package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;
    public static final String KEY = "SHOP_STATUS";

    /**
     * 设置店铺状态
     *
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable("status") Integer status) {
        log.info("设置店铺的营业状态为： {}", status == 1 ? "营业中" : "打样中");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    @GetMapping("/status")
    public Result<Integer> getStatus() {
        var status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("当前店铺状态为： {}", status == 1 ? "营业中" : "打样中");
        return Result.success(status);
    }

}
