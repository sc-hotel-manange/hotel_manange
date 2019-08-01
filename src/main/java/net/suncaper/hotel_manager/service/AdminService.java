package net.suncaper.hotel_manager.service;

import net.suncaper.hotel_manager.domain.H_Admin;
import net.suncaper.hotel_manager.domain.H_AdminExample;
import net.suncaper.hotel_manager.mapper.H_AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private H_AdminMapper h_adminMapper;

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

    public H_Admin getAdminInfo(int a_id){
        return h_adminMapper.selectByPrimaryKey(a_id);
    }

    public int updateInfo(H_Admin h_admin){
        h_adminMapper.updateByPrimaryKey(h_admin);
        return 1;
    }
}
