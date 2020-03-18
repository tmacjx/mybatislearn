package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public class RoleMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById(){
        SqlSession sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            Assert.assertNotNull(role);
            Assert.assertEquals("管理员", role.getRoleName());
        }finally {
            sqlSession.close();
        }
    }


}
