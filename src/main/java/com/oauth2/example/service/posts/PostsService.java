package com.oauth2.example.service.posts;

import com.oauth2.example.domain.posts.Posts;
import com.oauth2.example.domain.posts.PostsRepository;
import com.oauth2.example.web.dto.PostsListResponseDto;
import com.oauth2.example.web.dto.PostsResponseDto;
import com.oauth2.example.web.dto.PostsSaveRequestDto;
import com.oauth2.example.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto) {

        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {

        return new PostsResponseDto(postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id)));
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {

        return postsRepository.findAllDesc()
                .stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {

        postsRepository.delete(postsRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" +id)));
    }
}
