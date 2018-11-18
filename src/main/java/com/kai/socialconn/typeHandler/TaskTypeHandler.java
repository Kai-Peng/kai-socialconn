package com.kai.socialconn.typeHandler;

import com.kai.socialconn.bean.TypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.awt.*;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(value = TypeEnum.class)
public class TaskTypeHandler extends BaseTypeHandler {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if(parameter.getClass().getSimpleName().equals("TypeEnum")){
            ps.setInt(i, ((TypeEnum) parameter).getId());
        }else{
            ps.setObject(i, parameter);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int type = rs.getInt(columnName);

        return TypeEnum.getEnumById(type);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int type = rs.getInt(columnIndex);

        return TypeEnum.getEnumById(type);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int type = cs.getInt(columnIndex);

        return TypeEnum.getEnumById(type);
    }
}
