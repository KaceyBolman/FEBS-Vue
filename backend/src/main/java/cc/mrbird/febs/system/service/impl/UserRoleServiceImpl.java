package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.system.dao.UserRoleMapper;
import cc.mrbird.febs.system.domain.UserRole;
import cc.mrbird.febs.system.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("userRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

	@Override
	@Transactional
	public void deleteUserRolesByRoleId(String[] roleIds) {
		List<String> list = Arrays.asList(roleIds);
		for(String id : list){
			baseMapper.deleteByRoleId(Long.valueOf(id));
		}
	}

	@Override
	@Transactional
	public void deleteUserRolesByUserId(String[] userIds) {
		List<String> list = Arrays.asList(userIds);
		for(String id : list){
			baseMapper.deleteByUserId(Long.valueOf(id));
		}
	}

	@Override
	public List<String> findUserIdsByRoleId(String[] roleIds) {

		List<UserRole> list = baseMapper.selectList(new QueryWrapper<UserRole>().lambda().in(UserRole::getRoleId, roleIds));

		return list.stream().map(userRole -> String.valueOf(userRole.getUserId())).collect(Collectors.toList());
	}

}
