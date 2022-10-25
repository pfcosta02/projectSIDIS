package com.example.project.usermanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.project.exceptions.MyResourceNotFoundException;
import com.example.project.usermanagement.model.User;

/**
 * Based on https://github.com/Yoh0xFF/java-spring-security-example
 *
 */
@Repository
@CacheConfig(cacheNames = "users")
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    @CacheEvict(allEntries = true)
    <S extends User> List<S> saveAll(Iterable<S> entities);

    @Override
    @Caching(evict = { @CacheEvict(key = "#p0.id", condition = "#p0.id != null"),
            @CacheEvict(key = "#p0.username", condition = "#p0.username != null") })
    <S extends User> S save(S entity);

    @Override
    @Cacheable
    Optional<User> findById(Long objectId);

    @Cacheable
    default User getById(final Long id) {
        final Optional<User> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            throw new MyResourceNotFoundException(User.class, id);
        }
        if (!optionalUser.get().isEnabled()) {
            throw new MyResourceNotFoundException(User.class, id);
        }
        return optionalUser.get();
    }

    @Cacheable
    Optional<User> findByUsername(String username);
}
