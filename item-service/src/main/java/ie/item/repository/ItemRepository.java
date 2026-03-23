package ie.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.item.model.Item;

public interface ItemRepository extends JpaRepository<Item,Long>{


}
