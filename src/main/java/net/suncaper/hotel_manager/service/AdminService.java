package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_Admin;
import net.suncaper.hotel_manager.domain.H_AdminExample;
import net.suncaper.hotel_manager.domain.H_User;
import net.suncaper.hotel_manager.domain.H_UserExample;
import net.suncaper.hotel_manager.mapper.H_AdminMapper;
import net.suncaper.hotel_manager.mapper.H_UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理员权限说明：
 * 0 - 超级管理员
 * 1 - 普通管理员
 */
@Service
public class AdminService {
    @Autowired
    private H_AdminMapper h_adminMapper;
    @Autowired
    H_UserMapper h_userMapper;

    //返回所有用户
    public List<H_User> selectUser() {
        H_UserExample example = new H_UserExample();
        return h_userMapper.selectByExample(example);
    }

    //根据用户真实姓名搜索
    public List<H_User> searchUser(String content) {
        H_UserExample example = new H_UserExample();
        example.createCriteria().andUNameLike("%" + content + "%"); //模糊搜索

        return h_userMapper.selectByExample(example);
    }

    //返回某用户详细信息
    public H_User getUserInfo(int u_id) {
        return h_userMapper.selectByPrimaryKey(u_id);
    }

    public boolean updateInfo(H_User h_user) {
        h_userMapper.updateByPrimaryKey(h_user);
        return true;
    }

    public int getIdByAccountAndPwd(String a_account,String a_password){
        H_AdminExample example = new  H_AdminExample();
        example.createCriteria().andAAccountEqualTo(a_account).andAPasswordEqualTo(a_password);

        List<H_Admin> h_admins = h_adminMapper.selectByExample(example);
        for(H_Admin h_admin : h_admins) {
            return h_admin.getaId();
        }
        return -1;
    }

    public int getIdByAccount(String a_account){
        H_AdminExample example = new  H_AdminExample();
        example.createCriteria().andAAccountEqualTo(a_account);

        List<H_Admin> h_admins = h_adminMapper.selectByExample(example);
        for(H_Admin h_admin : h_admins) {
            return h_admin.getaId();
        }
        return -1;
    }

    public void insertAdmin(H_Admin h_admin) {
        h_adminMapper.insert(h_admin);
    }

    //管理员列表，若是超级管理员则显示全部，普通酒店管理员则只显示自己
    public List<H_Admin> getAdminList(int a_id) {
        H_Admin h_admin = getAdminInfo(a_id);
        H_AdminExample example = new H_AdminExample();

        //超级管理员
        if("1".equals(h_admin.getaPermission())){
            return h_adminMapper.selectByExample(example);
        }else {
            example.createCriteria().andAIdEqualTo(a_id);
            return h_adminMapper.selectByExample(example);
        }
    }

    //获取管理员信息
    public H_Admin getAdminInfo(int a_id){
        return h_adminMapper.selectByPrimaryKey(a_id);
    }

    //更新管理员信息
    public int updateInfo(H_Admin h_admin){
        h_adminMapper.updateByPrimaryKey(h_admin);
        return 1;
    }

    //根据管理员账号搜索管理员
    public List<H_Admin> searchAdmin(String content) {
        H_AdminExample example = new H_AdminExample();
        example.createCriteria().andAAccountLike("%" + content + "%"); //模糊搜索

        return h_adminMapper.selectByExample(example);
    }
}
