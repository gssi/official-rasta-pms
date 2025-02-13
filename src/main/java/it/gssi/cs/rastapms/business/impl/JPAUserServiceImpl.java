package it.gssi.cs.rastapms.business.impl;

import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.RequestGrid;
import it.gssi.cs.rastapms.business.ResponseGrid;
import it.gssi.cs.rastapms.business.UserService;
import it.gssi.cs.rastapms.business.repository.RoleRepository;
import it.gssi.cs.rastapms.business.repository.UserRepository;
import it.gssi.cs.rastapms.domain.Role;
import it.gssi.cs.rastapms.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class JPAUserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public JPAUserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findUserByUsername(String username) throws BusinessException {
        return userRepository.findByUsername(username);
    }

    @Override
    public void updateProfile(User user) throws BusinessException {


    }

    @Override
    public ResponseGrid<User> findAllUsersPaginated(RequestGrid requestGrid) throws BusinessException {

        Pageable pageable = PageRequest.of(requestGrid.getStartPage().intValue(), requestGrid.getLength().intValue(), Sort.by(Sort.Direction.fromString(requestGrid.getSortDir()), requestGrid.getSortCol()));
        Page<User> page = userRepository.findByNameLike(Utility.addPercent(requestGrid.getSearch().getValue()), pageable);
        ResponseGrid<User> response = new ResponseGrid<>(requestGrid.getDraw(), userRepository.count(), page.getTotalElements(), page.get().toList());
        return response;
    }

    @Override
    public void createUser(User user) throws BusinessException {
        userRepository.save(user);
    }

    @Override
    public Role findRoleByName(String name) throws BusinessException {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public User findUserByID(Long id) throws BusinessException {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteUser(User user) throws BusinessException {
        userRepository.delete(user);
    }


}
