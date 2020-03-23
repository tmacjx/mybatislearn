package tk.mybatis.simple.mapper;

import org.apache.ibatis.ognl.ASTSequence;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.awt.geom.RectangularShape;
import java.util.*;

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
            Assert.assertNotNull(user.getId());
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

    @Test
    public void testSelectByUser(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser query = new SysUser();
            query.setUserName("admin");
            List<SysUser> userList = userMapper.selectByUser(query);
            Assert.assertTrue(userList.size() > 0);

            query = new SysUser();
            query.setUserEmail("test@mybatis.tk");
            userList = userMapper.selectByUser(query);
            Assert.assertTrue(userList.size() > 0);

            query = new SysUser();
            query.setUserName("ad");
            query.setUserEmail("test@qq.com");
            userList = userMapper.selectByUser(query);
            Assert.assertEquals(0, userList.size());
        }finally {
            sqlSession.close();
        }


    }

    @Test
    public void testUpdateByldSelective(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setId(1L);
            user.setUserEmail("aa@qq.com");
            int result = userMapper.updateByldSelective(user);
            Assert.assertEquals(1, result);
            user = userMapper.selectById(1L);
            Assert.assertEquals("aa@qq.com",user.getUserEmail());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert2Selective(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("ttt");
            user.setUserPassword("123");
            user.setUserInfo("info");
            user.setCreateTime(new Date());

            int result = userMapper.insert2(user);
            Assert.assertEquals(1, result);
            user = userMapper.selectById(user.getId());
            Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
        }finally {
            sqlSession.close();
        }

    }

    @Test
    public void testSelectByIdOrUserName(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser query = new SysUser();
            query.setId(1L);
            query.setUserName("A");
            SysUser user = userMapper.selectByIdOrUserName(query);
            Assert.assertNotNull(user);

            query.setUserName("");
            user = userMapper.selectByIdOrUserName(query);
            Assert.assertNotNull(user);

            query.setId(null);
            user = userMapper.selectByIdOrUserName(query);
            Assert.assertNull(user);

        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByIdList(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<Long> idList= Arrays.asList(1L, 1001L);
            List<SysUser> userList = userMapper.selectByIdList(idList);
            Assert.assertTrue(userList.size() > 0);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByIdList2(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Long[] idArray = new Long[]{1L, 1001L};
            List<SysUser> userList = userMapper.selectByIdList2(idArray);
            Assert.assertTrue(userList.size() > 0);
        }finally {
            sqlSession.close();
        }
    }
    @Test
    public void testSelectByIdOrUserNameList(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Long[] idArray = new Long[]{1L, 1001L};
            String[] nameArray = new String[]{"admin"};
            List<SysUser> userList = userMapper.selectByIdOrUserNameList(idArray, nameArray);
            Assert.assertTrue(userList.size() > 0);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertList(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = new ArrayList<SysUser>();
            for(int i =0; i < 2; i ++){
                SysUser user = new SysUser();
                user.setUserName("t" + i);
                user.setUserPassword("123");
                user.setUserEmail("123@qq.com");
                userList.add(user);
            }
            int result = userMapper.insertList(userList);
            Assert.assertEquals(2, result);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByMap(){
        SqlSession sqlSession = getSqlSession();
        try{
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", 1L);
            map.put("user_email", "123@qq.com");
            userMapper.updateByMap(map);
            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("123@qq.com", user.getUserEmail());

        }finally {
            sqlSession.close();
        }


    }





}
