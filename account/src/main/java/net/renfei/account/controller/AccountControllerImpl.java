package net.renfei.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.renfei.account.service.AccountService;
import net.renfei.api.account.entity.ReportPublicKeyVO;
import net.renfei.api.account.entity.SignInVO;
import net.renfei.api.account.entity.SignUpVO;
import net.renfei.api.account.service.AccountIdService;
import net.renfei.sdk.comm.StateCode;
import net.renfei.sdk.entity.APIResult;
import net.renfei.sdk.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 账户服务
 *
 * @author RenFei
 */
@RestController
@Api(tags = "账户服务接口")
public class AccountControllerImpl implements AccountIdService {
    @Autowired
    private AccountService accountService;

    /**
     * 申请非对称秘钥
     *
     * @return
     */
    @Override
    @ApiOperation("秘钥申请接口")
    public APIResult secretKey() {
        Map<Integer, String> map = accountService.secretKey();
        if (BeanUtils.isEmpty(map)) {
            return APIResult.builder()
                    .code(StateCode.Error)
                    .build();
        }
        return APIResult.builder()
                .code(StateCode.OK)
                .message(map.get(1))
                .data(map.get(0))
                .build();
    }

    @Override
    @ApiOperation("上报秘钥接口")
    public APIResult setSecretKey(ReportPublicKeyVO reportPublicKeyVO) {
        return accountService.setSecretKey(reportPublicKeyVO);
    }

    /**
     * 登陆
     *
     * @param signInVO 登录请求对象
     * @return
     */
    @Override
    @ApiOperation("账户登录接口")
    public APIResult<String> signIn(@RequestBody SignInVO signInVO) {
        return accountService.signIn(signInVO);
    }

    /**
     * 注册
     *
     * @param signUpVO 注册请求对象
     * @return
     */
    @Override
    @ApiOperation("账户注册接口")
    public APIResult<String> signUp(SignUpVO signUpVO) {
        return null;
    }

    @Override
    @ApiOperation("根据Token获取账户ID接口")
    public APIResult<String> getAccountIdByToken(String token) {
        String uuid = accountService.getAccountIdByToken(token);
        if (!BeanUtils.isEmpty(uuid)) {
            return APIResult.builder()
                    .code(StateCode.OK)
                    .data(uuid)
                    .build();
        }
        return APIResult.builder()
                .code(StateCode.Unauthorized)
                .build();
    }
}
