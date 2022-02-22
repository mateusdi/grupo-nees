package br.com.nees.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class Usuario implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Membro membro;
	
	 public Usuario(Membro membro) {
	        this.membro = membro;
	    }
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> authorityListAdmin =  (ArrayList<GrantedAuthority>) AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		ArrayList<GrantedAuthority> authorityListUser = (ArrayList<GrantedAuthority>) AuthorityUtils.createAuthorityList("ROLE_USER");
		
		return this.membro.isAdmin() ? authorityListAdmin : authorityListUser;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.membro.getSenha();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.membro.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public Integer getIdMembro() {
		return this.membro.getId();
	}

}
