package com.smms.modules.sys.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.smms.common.entity.Result;
import com.smms.common.util.ShiroUtils;
import com.smms.modules.sys.entity.SysUser;
import com.smms.modules.sys.service.SysTokenService;
import com.smms.modules.sys.service.SysUserService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class SysLoginController {

    @Autowired
    private Producer producer;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysTokenService sysTokenService;


    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    @RequestMapping("/sys/login")
    public Result login(String username,String password,String captcha){
        String kaptcha=ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if(!captcha.equalsIgnoreCase(kaptcha)){
            return Result.error("验证码不正确");
        }
        //用户信息
        SysUser user=sysUserService.queryByUsername(username);
        //如果用户名为空或者密码不正确
        if(user == null || !user.getPassword().equals(new Sha256Hash(password,user.getSalt()).toHex())){
            return Result.error("用户名或密码不正确");
        }

        if(user.getStatus()==0){
            return Result.error("用户名已被锁定，请联系管理员");
        }
        //生成token并保存到数据库
        Result result=sysTokenService.createToken(user.getUserId());
        return result;
    }
}
