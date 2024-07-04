package it.gssi.cs.rastapms.security;

import it.gssi.cs.rastapms.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.UserService;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserService service;

	public UserDetailsServiceImpl(UserService service) {
		this.service = service;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		try {
			user = service.findUserByUsername(username);
		} catch (BusinessException e) {
			throw new UsernameNotFoundException("user not found");
		}

		if (user == null) {
			throw new UsernameNotFoundException("utente non trovato");
		}
		return new UserDetailsImpl(user);

	}

}
