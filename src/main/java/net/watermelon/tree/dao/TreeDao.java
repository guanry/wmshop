package net.watermelon.tree.dao;



import net.watermelon.tree.Tree;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface TreeDao<T> extends JpaRepository<Tree, Integer>{

		 
}
