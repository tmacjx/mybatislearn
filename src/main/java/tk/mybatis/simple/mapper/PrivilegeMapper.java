package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.provider.PrivilegeProvider;

import java.util.List;

public interface PrivilegeMapper {

    // SQL较长时, 推荐使用这种方式
    @SelectProvider(type = PrivilegeProvider.class, method = "selectById")
    SysPrivilege selectById(long id);

    List<SysPrivilege> selectPrivilegeByRoleId(Long roleId);

}
