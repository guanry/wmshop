package net.watermelon.resource.dao;

import net.watermelon.resource.vo.Resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ResourceDao<T> extends JpaRepository<Resource, Integer>{

		 
}
