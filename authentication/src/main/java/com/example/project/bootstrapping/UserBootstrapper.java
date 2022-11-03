/*
 * Copyright (c) 2022-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.example.project.bootstrapping;

import com.example.project.usermanagement.model.Role;
import com.example.project.usermanagement.model.User;
import com.example.project.usermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Spring will load and execute all components that implement the interface
 * CommandLinerunner on startup, so we will use that as a way to bootstrap some
 * data for testing purposes.
 * <p>
 * In order to enable this bootstraping make sure you activate the spring
 * profile "bootstrap" in application.properties
 *
 * @author pgsou
 *
 */
@Component
@Profile("bootstrap")
public class UserBootstrapper implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        // admin
        if (userRepo.findByUsername("pfcosta02@mail.com").isEmpty()) {
            User u1 = new User("pfcosta02@mail.com", encoder.encode("pass123"));
            u1.setFullName("Pedro Costa");
            u1.addAuthority(new Role(Role.MODERATOR));
            userRepo.save(u1);
        }

        // moderator
        if (userRepo.findByUsername("rfnm@mail.com").isEmpty()) {
            User u2 = new User("rfnm@mail.com", encoder.encode("12345"));
            u2.setFullName("Rafael Moreira");
            u2.addAuthority(new Role(Role.ADMIN));
            userRepo.save(u2);
        }

        // customer
        if (userRepo.findByUsername("bruna@mail.com").isEmpty()) {
            User u3 = new User("bruna@mail.com", encoder.encode("54321"));
            u3.setFullName("Bruno Rodrigues");
            u3.addAuthority(new Role(Role.CUSTOMER));
            userRepo.save(u3);
        }
    }

}
