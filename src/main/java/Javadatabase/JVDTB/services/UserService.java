/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Javadatabase.JVDTB.services;

import Javadatabase.JVDTB.Role;
import Javadatabase.JVDTB.entity.User;
import Javadatabase.JVDTB.repository.IRoleRepository;
import Javadatabase.JVDTB.repository.IUserRepository;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Admin
 */
@Service 
@Slf4j 
@Transactional 
public class UserService implements UserDetailsService { 
 
    @Autowired 
    private IUserRepository userRepository; 
    @Autowired 
    private IRoleRepository roleRepository; 
 
    // Lưu người dùng mới vào cơ sở dữ liệu sau khi mã hóa mật khẩu. 
    public void save(@NotNull User user) { 
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); 
        userRepository.save(user); 
    } 
 
    // Gán vai trò mặc định cho người dùng dựa trên tên người dùng. 
    public void setDefaultRole(String username) { 
        userRepository.findByUsername(username).ifPresentOrElse( 
                user -> { 
                    user.getRoles().add(roleRepository.findRoleById(Role.USER.value)); 
                    userRepository.save(user); 
                }, 
                () -> { throw new UsernameNotFoundException("User not found"); } 
        );
        } 
 
    // Tải thông tin chi tiết người dùng để xác thực. 
    @Override 
    public UserDetails loadUserByUsername(String username) throws 
UsernameNotFoundException { 
        var user = userRepository.findByUsername(username) 
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); 
        return org.springframework.security.core.userdetails.User 
                .withUsername(user.getUsername()) 
                .password(user.getPassword()) 
                .authorities(user.getAuthorities()) 
                .accountExpired(!user.isAccountNonExpired()) 
                .accountLocked(!user.isAccountNonLocked()) 
                .credentialsExpired(!user.isCredentialsNonExpired()) 
                .disabled(!user.isEnabled()) 
                .build(); 
    } 
 
    // Tìm kiếm người dùng dựa trên tên đăng nhập. 
    public Optional<User> findByUsername(String username) throws 
UsernameNotFoundException { 
        return userRepository.findByUsername(username); 
    } 
}