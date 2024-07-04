package it.gssi.cs.rastapms.business;

import it.gssi.cs.rastapms.domain.Role;
import it.gssi.cs.rastapms.domain.User;

public interface UserService {
	
	User findUserByUsername(String username) throws BusinessException;

	void updateProfile(User user) throws BusinessException;


    ResponseGrid<User> findAllUsersPaginated(RequestGrid requestGrid) throws BusinessException;

	void createUser(User user) throws BusinessException;

	Role findRoleByName(String name) throws BusinessException;

    User findUserByID(Long id) throws BusinessException;
}
