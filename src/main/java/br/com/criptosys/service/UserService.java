package br.com.criptosys.service;

import br.com.criptosys.domain.entity.UserDE;
import br.com.criptosys.domain.repository.UserRepository;
import br.com.criptosys.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public UserDE save(UserDE userDE) {
        userDE.setPassword(this.bCryptPasswordEncoder.encode(userDE.getPassword()));
        return this.userRepository.save(userDE);
    }

    public List<UserDE> findAll() {
        return this.userRepository.findAll();
    }

    public Optional<UserDE> findById(BigInteger id) {
        return this.userRepository.findById(id);
    }

    public UserDE findByEmailAndUsername(String email, String username){
        return this.userRepository.findByEmailAndUsername(email, username);
    }

    public UserDE findByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

    public void deleteById(BigInteger id) {
        this.userRepository.deleteById(id);
    }

    public UserDE mapDE(UserDTO userDTO){
        return UserDE.builder()
                .userId(userDTO.getUserId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .active(userDTO.getActive())
                .build();
    }
}
