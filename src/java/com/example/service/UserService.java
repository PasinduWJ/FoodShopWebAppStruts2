package com.example.service;

import com.example.dao.AdminDao;
import com.example.dao.UserDao;
import com.example.model.AdminModel;
import com.example.model.UserModel;
import com.example.util.PasswordEncrypt;

public class UserService {

    public String verifyLogin(UserModel uModel) throws Exception {
        String res;

        UserModel userModel = UserDao.getUserByUserName(uModel.getUserName());
        if (uModel.getUserName().equals(userModel.getUserName())) {
            if (uModel.getPassword().equals(PasswordEncrypt.pDecode(userModel.getPassword()))) {
                res = "success";
            } else {
                res = "NotPassword";
            }
        } else {
            res = "NotUserName";
        }
        return res;
    }

    public String verifyLoginAdmin(UserModel uModel) throws Exception {
        String res;

        AdminModel adminModel = AdminDao.getAdminByAdminName(uModel.getUserName());
        if (uModel.getUserName().equals(adminModel.getAdminName())) {
            if (uModel.getPassword().equals(PasswordEncrypt.pDecode(adminModel.getPassword()))) {
                res = "success";
            } else {
                res = "NotPassword";
            }
        } else {
            res = "NotUserName";
        }
        return res;
    }

    public boolean verifyRegister(UserModel uModel) throws Exception {

        UserModel userModel = UserDao.getUserByUserName(uModel.getUserName());
        if (uModel.getUserName().equals(userModel.getUserName())) {
            return true;
        }
        return false;
    }
}
