package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.List;

public interface UserMapper {
    // 接口参数是基本类型
    SysUser selectById(Long id);
    List<SysUser> selectAll();
    // 接口参数是JavaBean
    int insert(SysUser sysUser);
    int insert2(SysUser sysUser);
    int insert3(SysUser sysUser);
    int updateById(SysUser sysUser);
    int deleteById(Long id);
    // 接口多个参数, 使用Param注解
    List<SysRole> selectRoleByUserIdAndRoleEnabled(@Param("userId") Long userId,
                                                   @Param("enabled") Integer enabled);
}
