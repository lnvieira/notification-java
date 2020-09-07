package org.medeiros.usecase.port;

import org.medeiros.domain.entity.Message;

import java.util.Optional;

public interface MessageRepository {

	Message create(Message message);

	void delete(String id);

	Optional<Message> find(String id);

}
