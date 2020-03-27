package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    // 接口参数是基本类型
    SysUser selectById(Long id);
    List<SysUser> selectAll();
    List<SysUser> selectByIdList(List<Long> idList);
    List<SysUser> selectByIdList2(Long[] idArray);
    List<SysUser> selectByIdOrUserNameList(@Param("idArray") Long[] idArray,
                                           @Param("nameArray") String[] nameArray);

    SysUser selectByIdOrUserName(SysUser sysUser);
    // 接口参数是JavaBean
    int insert(SysUser sysUser);
    int insert2(SysUser sysUser);
    int insert3(SysUser sysUser);
    int insertList(List<SysUser> userList);
    int updateById(SysUser sysUser);
    int updateByMap(Map<String, Object> map);
    int deleteById(Long id);
    // 接口多个参数, 使用Param注解
    List<SysRole> selectRoleByUserIdAndRoleEnabled(@Param("userId") Long userId,
                                                   @Param("enabled") Integer enabled);

    SysUser selectUserAndRoleById(Long id);

    List<SysUser> selectAllUserAndRoles();

    SysUser selectUserAndRoleById2(Long id);
    SysUser selectUserRoleMapSelect(Long id);

    List<SysUser> selectByUser(SysUser sysUser);

    int updateByldSelective(SysUser sysUser);

    SysUser selectAllUserAndRolesSelect(Long id );

}
