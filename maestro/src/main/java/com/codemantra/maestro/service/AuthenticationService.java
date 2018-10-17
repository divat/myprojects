package com.codemantra.maestro.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codemantra.maestro.dao.UserDao;
import com.codemantra.maestro.model.Users;

@Service
public class AuthenticationService implements UserDetailsService {
	
	@Autowired
	private UserDao accountDao;

	/**
	 * Authenticate the user
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users account = accountDao.findByUserName(username);
		
	    if(account==null) {
	    	System.out.println("No such user: " + username);
	    	throw new UsernameNotFoundException("No such user: " + username);
	    } else if (account.getUserProfiles().isEmpty()) {
	    	System.out.println("User " + username + " has no authorities");
	        throw new UsernameNotFoundException("User " + username + " has no authorities");
	    }
	    
	
	    boolean accountIsEnabled = true;
	    boolean accountNonExpired = true;
	    boolean credentialsNonExpired = true;
	    boolean accountNonLocked = true;
	
	    return new User(account.getUsername(), account.getPassword(), accountIsEnabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getGrantedAuthorities(account));
	    //return new CustomUser(account.getUsername(), account.getPassword(), accountIsEnabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(account.getRoles()), permissionList, screenLists);
	}
	
	
	private List<GrantedAuthority> getGrantedAuthorities(Users user){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(com.codemantra.maestro.model.UserProfile userProfile : user.getUserProfiles()){
			System.out.println("UserProfile : "+userProfile);
			authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getType()));
		}
		System.out.print("authorities :"+authorities);
		return authorities;
	}
	
	
	/**
	 * Retrieve the screen list by role
	 * @param username
	 * @return
	 */
	/*@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Screen> getScreenList(String username){
		Users account = accountDao.findByStringField("loginName", username).get(0);
		
	    if(account==null) {
	    	System.out.println("No such user: " + username);
	    	throw new UsernameNotFoundException("No such user: " + username);
	    } else if (account.getRoles().isEmpty()) {
	    	System.out.println("User " + username + " has no authorities");
	        throw new UsernameNotFoundException("User " + username + " has no authorities");
	    }

	    List<Screen> screenLists = new ArrayList<Screen>();
	    int max1 = account.getRoles().size();
	    for(int i=0; i<max1; i++) {
	    	Set<Screen> screenList = account.getRoles().get(i).getScreens();
	    	for(Screen s : screenList){
	    		screenLists.add(s);
	    	}
	    }
	    return screenLists;
	}*/
	
	public Users getUserType(String loginName) {
		Users account = accountDao.findByStringField("loginName", loginName).get(0);
		return account;
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	/*public List<String> getRolesAsList(List<Permission> list) {
	    List <String> rolesAsList = new ArrayList<String>();
	    for(Permission permissions : list){
	        rolesAsList.add(permissions.getName());
	    }
	    return rolesAsList;
	}*/
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	/*public List<String> getRoleList(List<Role> list) {
	    List <String> rolesAsList = new ArrayList<String>();
	    for(Role role : list){
	        rolesAsList.add(role.getName());
	    }
	    return rolesAsList;
	}*/
	
	/**
	 * 
	 * @param roles
	 * @return
	 */
	/*public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
	    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	    for (String role : roles) {
	        authorities.add(new SimpleGrantedAuthority(role));
	    }
	    return authorities;
	}*/
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	/*public Collection<? extends GrantedAuthority> getRoleAuthorities(List<Role> list) {
	    List<GrantedAuthority> authList = getGrantedAuthorities(getRoleList(list));
	    return authList;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(List<Permission> list) {
	    List<GrantedAuthority> authList = getGrantedAuthorities(getRolesAsList(list));
	    return authList;
	}
	
	public List<Users> userList(String user){
		List<Users> users = accountDao.findByString("username", user);
		return users;
	}*/
}
