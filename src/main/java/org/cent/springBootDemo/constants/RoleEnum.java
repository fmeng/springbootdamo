package org.cent.springBootDemo.constants;

import com.google.common.collect.*;
import org.cent.springBootDemo.util.ArrayUtil;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.cent.springBootDemo.constants.AuthorityEnum.*;

/**
 * Created by fmeng on 2017/10/30.
 */
public enum  RoleEnum {
    ADMIN(1, "管理员", Sets.newHashSet(READ,WRITE))

    ;

    private static final Map<Integer, RoleEnum> enumMap = Arrays.stream(RoleEnum.values()).parallel()
            .collect(ImmutableMap.toImmutableMap(RoleEnum::getCode, mapRoleEnum -> mapRoleEnum));

    private int code;
    private String text;
    private Set<AuthorityEnum> auths;

    RoleEnum(int code, String text, Set<AuthorityEnum> auths){
        this.code = code;
        this.text = text;
        this.auths = auths;
    }

    public static RoleEnum codeOf(int code){
        return enumMap.get(code);
    }

    public static Set<RoleEnum> toRoles(Set<AuthorityEnum> auths){
        if (CollectionUtils.isEmpty(auths)){
            return null;
        }
        Set<RoleEnum> res = Arrays.stream(RoleEnum.values()).parallel()
                .filter(mapRoleEnum -> auths.containsAll(mapRoleEnum.getAuths()))
                .collect(Collectors.toSet());
        return res;
    }

    public static Set<RoleEnum> toRoles(Set<AuthorityEnum> auths, RoleEnum ... roleEnums){
        Set<AuthorityEnum> allAuths = toAuths(auths, roleEnums);
        Set<RoleEnum> res = toRoles(allAuths);
        return res;
    }

    public static Set<AuthorityEnum> toAuths(RoleEnum ... roleEnums){
        if (ArrayUtil.isEmpty(roleEnums)){
            return null;
        }
        Set<AuthorityEnum> res = Arrays.stream(roleEnums).parallel()
                .map(RoleEnum::getAuths)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        return res;
    }

    public static Set<AuthorityEnum> toAuths(Set<AuthorityEnum> auths, RoleEnum ... roleEnums){
        if (CollectionUtils.isEmpty(auths) && ArrayUtil.isEmpty(roleEnums)){
            return null;
        }
        Set<AuthorityEnum> res = auths;
        if (CollectionUtils.isEmpty(res)){
            res = Sets.newHashSet();
        }
        if (ArrayUtil.isNotEmpty(roleEnums)){
            Set<AuthorityEnum> roleAuths = toAuths(roleEnums);
            res.addAll(roleAuths);
        }
        return CollectionUtils.isEmpty(res)?null:res;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public Set<AuthorityEnum> getAuths() {
        return auths;
    }
}
