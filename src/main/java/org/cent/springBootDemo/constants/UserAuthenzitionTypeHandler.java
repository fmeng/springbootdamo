package org.cent.springBootDemo.constants;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by fmeng on 2017/10/29.
 */
public class UserAuthenzitionTypeHandler extends BaseTypeHandler<Set<AuthorityEnum>>{
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<AuthorityEnum> parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, AuthorityEnum.authsToMergeCode(parameter));
    }

    @Override
    public Set<AuthorityEnum> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        long mergeCode = rs.getLong(columnName);
        return AuthorityEnum.mergeCodeToAuths(mergeCode);
    }

    @Override
    public Set<AuthorityEnum> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        long mergeCode = rs.getLong(columnIndex);
        return AuthorityEnum.mergeCodeToAuths(mergeCode);
    }

    @Override
    public Set<AuthorityEnum> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        long mergerCode = cs.getLong(columnIndex);
        return AuthorityEnum.mergeCodeToAuths(mergerCode);
    }
}
