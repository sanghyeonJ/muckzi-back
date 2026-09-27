package com.muckzi.muckziback.service;

import com.muckzi.muckziback.dto.*;
import com.muckzi.muckziback.entity.*;
import com.muckzi.muckziback.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostImageRepository postImageRepository;
    private final PlaceRepository placeRepository;
    private final PostPlaceLinkRepository postPlaceLinkRepository;
    private final PlaceService placeService;

    private static final String UPLOAD_DIR = "uploads";

    @Transactional
    public Post createPost (String userId, PostRequest request) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        return postRepository.save(post);
    }


    @Transactional
    public void addImages (Long postId, String userId, List<MultipartFile> files) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (!post.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 게시글에만 이미지를 추가할 수 있습니다.");
        }

        // 기존 이미지 개수를 확인해서, 새로 추가되는 이미지의 순서를 이어서 매김
        int nextSortOrder = postImageRepository.findByPost_PostIdOrderBySortOrderAsc(postId).size();

        for (MultipartFile file : files) {
            String savedFileName = saveFile(file);

            PostImage postImage = PostImage.builder()
                    .post(post)
                    .imageUrl("/uploads/" + savedFileName)
                    .sortOrder(nextSortOrder++)
                    .build();
            postImageRepository.save(postImage);
        }
    }


    private String saveFile (MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String savedFileName = UUID.randomUUID() + extension;

            Path filePath = uploadPath.resolve(savedFileName);
            file.transferTo(filePath);

            return savedFileName;
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 저장에 실패했습니다.");
        }
    }


    @Transactional
    public void addPlaceLinks (Long postId, String userId, List<PostPlaceLinkRequest.PlaceInfo> places) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if(!post.getUser().getUserId().equals(userId)){
            throw new IllegalArgumentException("본인이 작성한 게시글에만 음식점을 연결할 수 있습니다.");
        }

        for (PostPlaceLinkRequest.PlaceInfo info : places) {
            Place place;

            if (info.getPlaceId() != null) {
                // 이미 등록된 음식점
                place = placeRepository.findById(info.getPlaceId())
                        .orElseThrow(() -> new IllegalArgumentException("음식점을 찾을 수 없습니다."));
            } else {
                if (info.getPlaceName() == null || info.getAddress() == null || info.getLatitude() == null || info.getLongitude() == null) {
                    throw new IllegalArgumentException("음식점 정보가 올바르지 않습니다.");
                }

                place = placeService.findOrCreatePlace(
                        info.getPlaceName(),
                        info.getCategory(),
                        info.getFilterCategory(),
                        info.getAddress(),
                        info.getLatitude(),
                        info.getLongitude()
                );
            }

            PostPlaceLink link = PostPlaceLink.builder()
                    .post(post)
                    .place(place)
                    .build();

            postPlaceLinkRepository.save(link);
        }
    }


    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (post.getStatus() != Post.Status.ACTIVE) {
            throw new IllegalArgumentException("삭제된 게시글입니다.");
        }

        List<PostImageResponse> images = postImageRepository
                .findByPost_PostIdOrderBySortOrderAsc(postId)
                .stream()
                .map(PostImageResponse::new)
                .toList();
        List<PostPlaceLinkResponse> places = postPlaceLinkRepository
                .findByPost_PostId(postId)
                .stream()
                .map(PostPlaceLinkResponse::new)
                .toList();

        return new PostDetailResponse(post, images, places);
    }

    @Transactional
    public void updatePost (Long postId, String userId, PostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (post.getStatus() != Post.Status.ACTIVE){
            throw new IllegalArgumentException("수정할 수 없는 게시글입니다.");
        }
        if (!post.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUpdatedAt(java.time.LocalDateTime.now());
    }

    @Transactional
    public void deleteImage (Long postId, Long imageId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (!post.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }

        PostImage image = postImageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));
        if (!image.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("해당 게시글의 이미지가 아닙니다.");
        }

        postImageRepository.delete(image);
    }

    @Transactional
    public void deletePlaceLink (Long postId, Long linkId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (!post.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 게시글의 음식점 링크만 삭제할 수 있습니다.");
        }

        PostPlaceLink link = postPlaceLinkRepository.findById(linkId)
                .orElseThrow(() -> new IllegalArgumentException("음식점 링크를 찾을 수 없습니다."));
        if (!link.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("해당 게시글의 음식점링크가 아닙니다.");
        }

        postPlaceLinkRepository.delete(link);
    }

    @Transactional
    public void deletePost (Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (post.getStatus() != Post.Status.ACTIVE) {
            throw new IllegalArgumentException("이미 삭제된 게시글입니다.");
        }
        if (!post.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 삭제할 수 있습니다.");
        }

        post.setStatus(Post.Status.DELETED);
    }

}
