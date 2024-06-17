package com.KarlMarket.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.KarlMarket.ecommerce.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {
}
