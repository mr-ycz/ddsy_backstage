package com.ycz.controller;

import com.ycz.pojo.Goods;
import com.ycz.pojo.User;
import com.ycz.pojo.UserVo;
import com.ycz.service.AdminService;
import com.ycz.service.GoodsService;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String loginLogic(UserVo userVo, Model model, HttpServletRequest request){
        UsernamePasswordToken token = new UsernamePasswordToken(userVo.getUsername(), userVo.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        User user = adminService.queryAdminByName(userVo.getUsername());
        model.addAttribute("admin", user);

        String iniPath = (String) request.getSession().getAttribute("iniPath");

        //如果当前登录角色是admin
        if (subject.hasRole("admin")){
            if ("/admincontroller/loadAddGoods".equals(iniPath) && iniPath!=null){
                request.getSession().setAttribute("iniPath", null);
                return "redirect:"+iniPath;
            }
            return "forward:/admin/admin.jsp";
        }else if (subject.hasRole("manager")){
            return "redirect:/admincontroller/userGoods";
        }
        return "forward:/admin/error";
    }

    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "forward:/admin/login.jsp";
    }

    @RequestMapping("/userGoods")
    public String userrGoods(HttpServletRequest request){
        showGoods(request);
        return "forward:/user/showGoods.jsp";
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
    public String updateGoods(String gid, String name, BigDecimal price, String intro, Integer typeid,
                              MultipartFile sources1, HttpServletRequest request) throws IOException {

        String fileName = UUID.randomUUID().toString();
        String originalFilename = sources1.getOriginalFilename();

        String extension = FilenameUtils.getExtension(originalFilename);
        // 拼接全局唯一文件名
        String uniqueFileName = fileName+"."+extension;

        String contentType = sources1.getContentType();
        if("image/jpeg".equals(contentType) || "image/png".equals(contentType) || "image/gif".equals(contentType)){
            String realPath = "D:\\ideaprograms\\ddsy_backstage\\src\\main\\webapp\\image";
            sources1.transferTo(new File(realPath+File.separator+uniqueFileName));
            String realFilePath=realPath+File.separator+uniqueFileName;

            Goods goods=new Goods(Integer.parseInt(gid), name, null, originalFilename, price, intro, typeid, null);
            goodsService.updateGoods(goods);
            return originalFilename;
        }
        return null;
    }

    @RequiresAuthentication
    @RequestMapping("/loadAddGoods")
    public String loadAddGoods(){
        return "forward:/admin/addGoods.jsp";
    }

    @RequestMapping("/addGoods")
    @ResponseBody
    public String addGoods(Goods goods, @RequestParam("sources1") MultipartFile sources1) throws IOException {

        String fileName = UUID.randomUUID().toString();
        String originalFilename = sources1.getOriginalFilename();

        String extension = FilenameUtils.getExtension(originalFilename);
        // 拼接全局唯一文件名
        String uniqueFileName = fileName+"."+extension;
        goods.setPicture(uniqueFileName);

        String contentType = sources1.getContentType();
        if("image/jpeg".equals(contentType) || "image/png".equals(contentType) || "image/gif".equals(contentType)){
            String realPath = "D:\\ideaprograms\\ddsy_backstage\\src\\main\\webapp\\image";
            sources1.transferTo(new File(realPath+File.separator+uniqueFileName));
            String realFilePath=realPath+File.separator+uniqueFileName;

            goodsService.addGoods(goods);
            return "1";
        }


        return null;
    }

}
