package com.shoes.scarecrow.web.controller;

import com.shoes.scarecrow.common.enums.UserType;
import com.shoes.scarecrow.common.utils.MD5;
import com.shoes.scarecrow.persistence.domain.User;
import com.shoes.scarecrow.persistence.domain.UserCondition;
import com.shoes.scarecrow.persistence.service.UserService;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author li
 * @description
 * @time 2018/3/8 14:22
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    private static Logger log = Logger.getLogger(IndexController.class);
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    @ResponseBody
    public Map login(String userName,String passWord ,HttpSession session){
        log.info("username="+userName+",password="+passWord);
        User user = userService.getUser(userName, MD5.excute(passWord), UserType.BUSINESS.getKey());
        Map<String,Object> map = new HashMap<String,Object>();
        if(user==null){
            map.put("flag",false);
            map.put("message","用户名或密码错误");
        }else {
            map.put("flag",true);
            map.put("message","登录成功");
            session.setAttribute("user","merchant");
            session.setAttribute("userId",user.getId());
            session.setAttribute("userName",user.getUserName());
        }
        return map;
    }
    @RequestMapping("/changePassword")
    @ResponseBody
    public Map changePassword(Long id,String password,String newPassword,HttpSession session){
        log.info(session.getAttribute("userName")+"进入修改用户登录密码方法,id:"+id+",password:"+password+",new password:"+newPassword);
        Map<String,Object> map = new HashMap<>();
        User user1 = userService.queryById(Integer.valueOf(String.valueOf(id)));
        String s_password = user1.getPassword();
        if(s_password!=null&&!s_password.equals(MD5.excute(password))){
            map.put("flag",false);
            map.put("message","输入的原密码与原来的不一致！");
            return map;
        }
        User user = new User();
        user.setId(id);
        user.setPassword(MD5.excute(newPassword));
        int n = userService.updateUser(user);
        if(n>0){
            map.put("flag",true);
            map.put("message","密码修改成功！");
        }else{
            map.put("flag",false);
            map.put("message","密码修改失败，请联系管理员！");
        }
        return map;
    }
    @RequestMapping("/logout")
    public void logout(HttpSession session,HttpServletRequest request, HttpServletResponse response) throws IOException {
        session.removeAttribute("userName");
        log.info(request.getRequestURI());
        String basePath = request.getRequestURL().substring(0,request.getRequestURL().indexOf(request.getServletPath()));
        log.info(request.getRequestURL());
        log.info(request.getServletPath());
        response.sendRedirect(basePath);
    }
    @RequestMapping("/main")
    public ModelAndView main(HttpSession session){
        String retCode = null;
        if(session.getAttribute("user").equals("manager")){
            retCode = "managerMain";
        }else{
            retCode = "main";
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(retCode);
        modelAndView.addObject("userName",session.getAttribute("userName"));
        return modelAndView;
    }
    @RequestMapping("/register")
    public String register(){
        return "redirect:/register/forward";
    }
    @RequestMapping(value = "/register/images", method = RequestMethod.POST)
    public void images(MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws Exception {
        log.info("开始上传微信图片");
        String name = file.getOriginalFilename();
        log.info(name);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Set factory constraints
        factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
        String tempPathFile = System.getProperty("java.io.tmpdir");
        factory.setRepository(new File(tempPathFile));// 设置缓冲区目录
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        System.out.println(tempPathFile);
        //如果文件不为空，写入上传路径

        //上传文件路径
        String path = request.getServletContext().getRealPath("/images/weixin/");//"/home/youwenqian/image/";//
        //上传文件名
        upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB
        File tempFile = new File(path,name);
        if(!tempFile.getParentFile().exists()){
            tempFile.getParentFile().mkdirs();
        }
        if(!tempFile.exists()){
            tempFile.createNewFile();
        }
        file.transferTo(tempFile);
        System.out.print("上传成功！");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("flag",true);
        map.put("message","上传成功");
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            String retStr = objectMapper.writeValueAsString(map);
            response.setCharacterEncoding("utf-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.print(retStr);
        }catch (IOException e){
            log.error("请求index/register/images.do 写返回数据时发生错误。"+e.getMessage());
        }
    }

    @RequestMapping("/confirmPassword")
    @ResponseBody
    public Map confirmUserPassword(HttpSession session,String confirmPassword){
        Map<String,Object> map = new HashMap<>();
        log.info(session.getAttribute("userName")+"进入到确认密码方法,confirmPassword="+confirmPassword);
        UserCondition userCondition = new UserCondition();
        String userName = String.valueOf(session.getAttribute("userName"));
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        userCondition.setUserName(userName);
        userCondition.setConfirmPassword(MD5.excute(confirmPassword));
        List<User> list = userService.queryByCondition(userCondition);
        if(list.size()>0){
            map.put("success",true);
        }else{
            map.put("success",false);
        }
        log.info(session.getAttribute("userName")+"离开到确认密码方法,confirmPassword="+confirmPassword);
        return map;
    }
}
