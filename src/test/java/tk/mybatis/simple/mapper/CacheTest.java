package tk.mybatis.simple.mapper;

import org.apache.ibatis.ognl.ASTSequence;
import org.apache.ibatis.session.SqlSession;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import javax.management.relation.Role;

public class CacheTest extends BaseMapperTest {

    /* 一级缓存(本地缓存) 默认会开启
       存在sqlSession生命周期中
     * 根据方法名和参数生成缓存的键值
     * 键值和结果存入Map对象中
     *
     * */
    @Test
    public void testL1Cache(){
        SqlSession sqlSession = getSqlSession();
        SysUser user1 = null;
        try{
            // 共用一个session, 未提交，但是本session内的改动可见
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            user1 = userMapper.selectById(1L);
            user1.setUserName("New Name");
            // 缓存了参数和方法, 所以未实际执行SQL
            SysUser user2 = userMapper.selectById(1L);
            // 可以设置xml flushCache为true, 同一session中也走数据库
            Assert.assertEquals("New Name", user2.getUserName());
            // user1和user2是同一个对象
            Assert.assertEquals(user1, user2);
        }finally {
            sqlSession.close();
        }
        System.out.println("开启新session");
        sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user2 = userMapper.selectById(1L);
            // 之前的提交未提交, 所以不同的session不可见
            Assert.assertNotEquals("New Name", user2.getUserName());
            Assert.assertNotEquals(user1, user2);
            // 执行insert/update/delete后, 缓存失效
            userMapper.deleteById(2L);
            SysUser user3 = userMapper.selectById(1L);
            //user2和user3不是一个对象
            Assert.assertNotEquals(user2, user3);
        }finally {
            sqlSession.close();
        }
    }


    /*
        二级缓存，存在在sqlFactory声明周期中
     */
    @Test
    public void testL2Cache(){
        SqlSession sqlSession = getSqlSession();
        SysRole role1 = null;
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            role1 = roleMapper.selectById(1L);
            role1.setRoleName("New Name");

            SysRole role2 = roleMapper.selectById(1L);
            Assert.assertEquals("New Name", role2.getRoleName());
            Assert.assertEquals(role1, role2);
        }finally {

            sqlSession.close();
        }
        System.out.println("开启新session");
        sqlSession = getSqlSession();
        try{
            //
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole rol2 = roleMapper.selectById(1L);
            // 之前sqlSession close后保存查询结果到二级缓存
            Assert.assertEquals("New Name", rol2.getRoleName());
            Assert.assertNotEquals(role1, rol2);

            SysRole role3 = roleMapper.selectById(1L);
            Assert.assertNotEquals(rol2, role3);

        }finally {
            sqlSession.close();
        }
    }


    @Test
    public void testDirtyData(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1L);
            Assert.assertEquals("管理员", user.getRole().getRoleName());
        }finally {
            sqlSession.close();
        }
        System.out.println("开启新session");
        sqlSession = getSqlSession();

        try{
            RoleMapper  roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            role.setRoleName("脏数据");
            roleMapper.updateById(role);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
        System.out.println("开启新session");
        sqlSession = getSqlSession();

        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1L);
            RoleMapper  roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);

            Assert.assertEquals("管理员", user.getRole().getRoleName());
            Assert.assertEquals("脏数据", role.getRoleName());

            role.setRoleName("管理员");
            roleMapper.updateById(role);
            sqlSession.commit();

        }finally {
            sqlSession.close();
        }
    }

}
