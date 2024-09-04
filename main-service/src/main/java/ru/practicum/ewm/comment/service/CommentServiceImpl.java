package ru.practicum.ewm.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CommentDto addCommentPrivate(Long eventId, CommentDto commentDto) {
        validateIdUser(commentDto.getAuthor());
        validateIdEvent(eventId);
        Event event = eventRepository.getEventsById(eventId);
        User user = userRepository.getUserById(commentDto.getAuthor());
        Comment comment = CommentMapper.toComment(commentDto, user, event);
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public void deleteCommentByIdPrivate(Long commentId) {
        validateIdComment(commentId);
        commentRepository.deleteById(commentId);
    }

    @Transactional
    @Override
    public CommentDto updateCommentPrivate(Long commentId, CommentDto commentDto) {
        validateIdUser(commentDto.getAuthor());
        validateIdEvent(commentDto.getEvent());
        validateIdComment(commentId);
        validateIdEventAndIdUser(commentId, commentDto.getAuthor());

        Comment oldComment = commentRepository.getCommentById(commentId);
        Event event = eventRepository.getEventsById(oldComment.getEvent().getId());
        User user = userRepository.getUserById(oldComment.getAuthor().getId());

        Comment upComment = CommentMapper.toComment(commentDto, user, event);

        upComment.setId(oldComment.getId());
        upComment.setText((upComment.getText() == null || upComment.getText().isBlank()) ? oldComment.getText() : upComment.getText());
        upComment.setCreated(upComment.getCreated() == null ? oldComment.getCreated() : upComment.getCreated());

        return CommentMapper.toCommentDto(commentRepository.save(upComment));
    }

    @Override
    public List<CommentDto> getCommentsPrivate(Long eventId, Integer from, Integer size) {

        int pageNumber = from / size;
        Pageable pageable = PageRequest.of(pageNumber, size);

        List<CommentDto> commentsDto = commentRepository.getCommentByEventId(eventId, pageable).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
        if (commentsDto.isEmpty()) {
            throw new NotFoundException(String.format("У ивента с ID %s, нет комментариев", eventId));
        }
        return commentsDto;
    }

    @Override
    public CommentDto getCommentByIdPrivate(Long commentId) {
        validateIdComment(commentId);
        Comment comment = commentRepository.getCommentById(commentId);
        return CommentMapper.toCommentDto(comment);
    }

    @Transactional
    @Override
    public void deleteCommentByIdAdmin(Long commentId) {
        validateIdComment(commentId);
        commentRepository.deleteById(commentId);
    }

    @Transactional
    @Override
    public CommentDto updateCommentAdmin(Long commentId, CommentDto commentDto) {
        validateIdComment(commentId);
        Comment oldComment = commentRepository.getCommentById(commentId);
        Comment newComment = CommentMapper.toComment(commentDto, oldComment.getAuthor(), oldComment.getEvent());

        newComment.setId(oldComment.getId());
        newComment.setCreated(newComment.getCreated() == null ? oldComment.getCreated() : newComment.getCreated());

        return CommentMapper.toCommentDto(commentRepository.save(newComment));
    }

    private void validateIdUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.info("Пользователь не найден с id {}", userId);
            throw new NotFoundException(String.format("Пользователь не найден с id %s", userId));
        }
    }

    private void validateIdComment(Long commentId) {
        if (commentRepository.getCommentById(commentId) == null) {
            log.info("Комментарий не найден с id {}", commentId);
            throw new NotFoundException(String.format("Комментарий не найден с id %s", commentId));
        }
    }

    private void validateIdEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            log.info("Ивент не найден с id {}", eventId);
            throw new NotFoundException(String.format("Ивент не найден с id %s", eventId));
        }
    }

    private void validateIdEventAndIdUser(Long commentId, Long userId) {
        if (!commentRepository.getCommentById(commentId).getAuthor().getId().equals(userId)) {
            log.info("Юзер с id {} не оставлял комментарий с id {}", userId, commentId);
            throw new NotFoundException(String.format("Юзер с id %s не оставлял комментарий с id %s", userId, commentId));
        }
    }

}