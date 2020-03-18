package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.Date;
import java.util.List;

public class UserMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertNotNull(user);
            Assert.assertEquals("admin", user.getUserName());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAll();

            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size() > 0);
        }finally {
            sqlSession.close();
        }
    }


    @Test
    public void testInsert(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123");
            user.setUserEmail("123@qq.com");
            user.setUserInfo("TEST");
            user.setHeadImg(new byte[]{1, 2,3});
            user.setCreateTime(new Date());
            // 执行SQL影响的行数
            int result = userMapper.insert(user);
            Assert.assertEquals(1, result);
            // 没有给id赋值, 没有配置回写id
            Assert.assertNull(user.getId());

        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testInsert2(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123");
            user.setUserEmail("123@qq.com");
            user.setUserInfo("TEST");
            user.setHeadImg(new byte[]{1, 2,3});
            user.setCreateTime(new Date());
            // 执行SQL影响的行数
            int result = userMapper.insert2(user);
            Assert.assertEquals(1, result);
            // 没有给id赋值, 没有配置回写id
            Assert.assertNotNull(user.getId());
            // 需要手动commit
            sqlSession.commit();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testInsert3(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123");
            user.setUserEmail("123@qq.com");
            user.setUserInfo("TEST");
            user.setHeadImg(new byte[]{1, 2,3});
            user.setCreateTime(new Date());
            // 执行SQL影响的行数
            int result = userMapper.insert3(user);
            Assert.assertEquals(1, result);
            // 没有给id赋值, 没有配置回写id
            Assert.assertNotNull(user.getId());
            // 需要手动commit
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateById() {
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("admin", user.getUserName());
            user.setUserName("admin_test");
            user.setUserEmail("test@qq.com");
            int result = userMapper.updateById(user);
            Assert.assertEquals(1, result);
            user = userMapper.selectById(1L);
            Assert.assertEquals("admin_test", user.getUserName());

        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteById(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertNotNull(user);
            Assert.assertEquals(1, userMapper.deleteById(1L));
            Assert.assertNull(userMapper.selectById(1L));
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserIdAndRoleEnable(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysRole> userList = userMapper.selectRoleByUserIdAndRoleEnabled(1L, 1);
            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size() > 0);
        }finally {
            sqlSession.close();
        }

    }




}
