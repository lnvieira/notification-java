package org.medeiros.adapter.repository;

import org.medeiros.adapter.repository.entity.MessageEntityRepository;
import org.medeiros.domain.Message;
import org.medeiros.usecase.port.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageGateway implements MessageRepository {

	private final MessageEntityRepository repository;
	private final MessageMapper messageMapper;

	public MessageGateway(MessageEntityRepository repository, MessageMapper messageMapper) {
		this.repository = repository;
		this.messageMapper = messageMapper;
	}

	@Override
	public Message create(Message message) {
		var entity = messageMapper.toEntity(message);
		entity.getChats().forEach(c -> {
			c.setMessage(entity);
		});
		repository.save(entity);
		return messageMapper.toDomain(entity);
	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<Message> find(String id) {
		var message = repository.findById(id);
		if (message.isPresent()) {
			return Optional.of(messageMapper.toDomain(message.get()));
		}
		return Optional.empty();
	}

	@Override
	public boolean exists(String id) {
		return repository.existsById(id);
	}
}
