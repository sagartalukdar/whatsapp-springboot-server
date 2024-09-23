package com.react.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.react.Model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

	@Query("select m from Message m join m.chat c where c.id=:chatId")
	public List<Message> findByChatId(Integer chatId);
}
