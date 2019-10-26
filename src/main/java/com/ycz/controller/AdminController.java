package com.ycz.controller;

import com.ycz.pojo.Goods;
import com.ycz.pojo.User;
import com.ycz.pojo.UserVo;
import com.ycz.service.AdminService;
import com.ycz.service.GoodsService;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admincontroller")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/login")
    public String loginPage(){
        return "forward:/admin/login.jsp";
    }

    @PostMapping("login")
    public String loginLogic(UserVo userVo, Model model){
        UsernamePasswordToken token = new UsernamePasswordToken(userVo.getUsername(), userVo.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        User user = adminService.queryAdminByName(userVo.getUsername());
        model.addAttribute("admin", user);

        //如果当前登录角色是admin
        if (subject.hasRole("admin")){
            return "forward:/admin/admin.jsp";
        }else if (subject.hasRole("manager")){
            return "index";
        }

        return "forward:/admin/error";
    }

    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "forward:/admin/login.jsp";
    }

    @RequestMapping("/showGoods")
    @ResponseBody
    public String showGoods(HttpServletRequest request){
        List<Goods> goods=goodsService.queryAllGoods();

        request.getSession().setAttribute("goods", goods);

        return "1";
    }

    @RequestMapping("/goods")
    public String goods(HttpServletRequest request){
        showGoods(request);
        return "forward:/admin/showGoods.jsp";
    }

    @RequestMapping("delGoods")
    @ResponseBody
    public String delGoods(Integer gid){
        goodsService.deleteGoodsById(gid);
        return "1";
    }

    @RequestMapping("/loadGoods")
    public String loadGoods(Integer gid, HttpServletRequest request){
        Goods goods = goodsService.queryById(gid);
        request.setAttribute("goods", goods);
        request.setAttribute("typeid", goods.getTypeid());
        return "forward:/admin/updateGoods.jsp";
    }

    @RequestMapping("/updateGoods")
    @ResponseBody
    public String updateGoods(String gid, String name, BigDecimal price, String intro, Integer typeid,  MultipartFile sources1, HttpServletRequest request) throws IOException {
//        System.out.println("name:"+name);
//        System.out.println("price:"+price);
//        System.out.println("intro:"+intro);
//        System.out.println("typeid:"+typeid);
//        System.out.println(sources1.getOriginalFilename());

        String fileName = UUID.randomUUID().toString();
        String originalFilename = sources1.getOriginalFilename();

        String extension = FilenameUtils.getExtension(originalFilename);
        // 拼接全局唯一文件名
        String uniqueFileName = fileName+"."+extension;

        System.out.println(request.getSession().getServletContext().getRealPath("/image"));

        String contentType = sources1.getContentType();
        if("image/jpeg".equals(contentType) || "image/png".equals(contentType) || "image/gif".equals(contentType)){
            String realPath = request.getSession().getServletContext().getRealPath("/image");
//            System.out.println(realPath + File.separator + uniqueFileName);
            sources1.transferTo(new File(realPath+File.separator+uniqueFileName));
            String realFilePath=realPath+File.separator+uniqueFileName;

            Goods goods=new Goods(Integer.parseInt(gid), name, null, realFilePath, price, intro, typeid, null);
            goodsService.updateGoods(goods);
            goods(request);
            return null;
        }
        return null;
    }

}
