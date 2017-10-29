package org.cent.springBootDemo.constants;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 枚举的code 必须为2的幂
 * Created by fmeng on 2017/10/29.
 */
public enum AuthorityEnum {
    READ(2 << 0, "读"),
    WRITE(2 << 1, "写");

    private static final Map<Integer, AuthorityEnum> enumMap = null;
    private static final BiMap<Long, Set<AuthorityEnum>> mergeCodeAuthsMap = HashBiMap.create(Maps.<Long, Set<AuthorityEnum>>newConcurrentMap());

    private int code;
    private String text;

    AuthorityEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static AuthorityEnum codeOf(int code){
        return enumMap.get(code);
    }

    /**
     *
     * @param mergeCode
     * @return
     */
    public static Set<AuthorityEnum> mergeCodeToAuths(long mergeCode){
        if (0L == mergeCode){
            return null;
        }
        Set<AuthorityEnum> res = mergeCodeAuthsMap.get(mergeCode);
        if (CollectionUtils.isEmpty(res)){
            int index = 0;
            res = Sets.newHashSet();
            do {
                int indexCode = 2 << index;
                if ((mergeCode & indexCode) == indexCode){
                    res.add(codeOf(indexCode));
                }
                index ++;
            }while (index < AuthorityEnum.values().length);
            mergeCodeAuthsMap.putIfAbsent(mergeCode, res);
        }
        return res;
    }

    /**
     *
     * @param auths
     * @return
     */
    public static long authsToMergeCode(Set<AuthorityEnum> auths){
        if (CollectionUtils.isEmpty(auths)){
            return 0L;
        }
        Long res = mergeCodeAuthsMap.inverse().get(auths);
        if (Objects.isNull(res)){
            res = auths.stream()
                    .map(AuthorityEnum::getCode)
                    .reduce(0, (a, b) -> a + b)
                    .longValue();
            mergeCodeAuthsMap.putIfAbsent(res, auths);
        }
        return res;
    }

}
