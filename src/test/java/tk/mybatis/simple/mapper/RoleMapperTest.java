package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;
import tk.mybatis.simple.plugin.PageRowBounds;
import tk.mybatis.simple.type.Enabled;

import java.util.Date;
import java.util.List;

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

    @Test
    public void testSelectById2(){
        SqlSession sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById2(1L);
            Assert.assertNotNull(role);
            Assert.assertEquals("管理员", role.getRoleName());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectAll();
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size() > 0);
        }finally {
            sqlSession.close();
        }
    }


    @Test
    public void testSelectAllByRowBounds(){
        SqlSession sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            // 查询第一个, 不会返回总数
            RowBounds rowBounds = new RowBounds(0, 1);
            List<SysRole> list = roleMapper.selectAll(rowBounds);
            for(SysRole role: list){
                System.out.println("角色名 " + role.getRoleName());
            }
            // 查询第一个
            PageRowBounds pageRowBounds = new PageRowBounds(0, 1);
            list = roleMapper.selectAll(pageRowBounds);
            System.out.println("查询总数 " + pageRowBounds.getTotal());
            for(SysRole role: list){
                System.out.println("角色名 " + role.getRoleName());
            }

            // 查询第二个
            pageRowBounds = new PageRowBounds(1, 1);
            list = roleMapper.selectAll(pageRowBounds);
            System.out.println("查询总数 " + pageRowBounds.getTotal());
            for(SysRole role: list){
                System.out.println("角色名 " + role.getRoleName());
            }

        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert(){
        SqlSession sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = new SysRole();
            role.setRoleName("test");
            role.setEnabled(Enabled.enabled);
            role.setCreateBy("test");
            role.setCreateTime(new Date());
            int result = roleMapper.insert(role);
            Assert.assertEquals(1, result);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert2(){
        SqlSession sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = new SysRole();
            role.setRoleName("test");
            role.setEnabled(Enabled.enabled);
            role.setCreateBy("test");
            role.setCreateTime(new Date());
            int result = roleMapper.insert2(role);
            Assert.assertEquals(1, result);
            Assert.assertNotNull(role.getRoleName());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert3(){
        SqlSession sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = new SysRole();
            role.setRoleName("test");
            role.setEnabled(Enabled.enabled);
            role.setCreateBy("test");
            role.setCreateTime(new Date());
            int result = roleMapper.insert3(role);
            Assert.assertEquals(1, result);
            Assert.assertNotNull(role.getRoleName());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateById(){
        SqlSession sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            role.setRoleName("test");
            int result = roleMapper.updateById(role);
            Assert.assertEquals(1, result);
            role = roleMapper.selectById(1L);
            Assert.assertEquals("test", role.getRoleName());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteById(){
        SqlSession sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            int result = roleMapper.deleteById(1L);
            Assert.assertEquals(1, result);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllRoleAndPrivileges(){
        SqlSession sqlSession = getSqlSession();
        try{
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roles = roleMapper.selectAllRoleAndPrivileges();
            for(SysRole role: roles){
                System.out.println("角色名 " + role.getRoleName());
                for(SysPrivilege privilege: role.getPrivilegeList()){
                    System.out.println("权限名 " + privilege.getPrivilegeName());
                }
            }
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserId() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roles = roleMapper.selectRoleByUserId(1L);
            Assert.assertNotNull(roles);
            Assert.assertTrue(roles.size() > 0);

        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserIdChoose() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(2L);
            role.setEnabled(Enabled.disabled);
            roleMapper.updateById(role);
            List<SysRole> roleList = roleMapper.selectRoleByUserIdChoose(1L);
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size() > 0);
            for(SysRole r: roleList){
                System.out.println("角色名 " + r.getRoleName());
                if(r.getId() == 1){
                    Assert.assertNotNull(r.getPrivilegeList());
                }else if (r.getId() == 2){
                    Assert.assertNull(r.getPrivilegeList());
                    continue;
                }
                for(SysPrivilege privilege: r.getPrivilegeList()){
                    System.out.println("权限名 " + privilege.getPrivilegeName());
                }
            }
        } finally {
            sqlSession.close();
        }
    }





}
