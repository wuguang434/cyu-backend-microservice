package com.cyu.cyuojbackendserviceclient.service;

import com.cyu.cyuojbackendcommon.common.ErrorCode;
import com.cyu.cyuojbackendcommon.exception.BusinessException;
import com.cyu.cyuojbackendmodel.model.entity.User;
import com.cyu.cyuojbackendmodel.model.enums.UserRoleEnum;
import com.cyu.cyuojbackendmodel.model.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.cyu.cyuojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务
 * <p>
 * # @author <a href="https://github.com/wuguang434">Coding boy:xlei</a>
 */
@FeignClient(name = "cyuoj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    /**
     * 根据id获取用户
     *
     * @param userId
     * @return
     * @RequestParam常用于Get请求,则该参数必须传递
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);

    /**
     * 根据id获取用户列表
     *
     * @param idList
     * @return
     */
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }


    /**
     * 是否为管理员
     * 只涉及基本的java语法判断,使用default,不走远程方法,更方便
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }


    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
