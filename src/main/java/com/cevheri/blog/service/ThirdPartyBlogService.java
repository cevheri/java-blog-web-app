package com.cevheri.blog.service;

import com.cevheri.blog.service.dto.PostDTO;

public interface ThirdPartyBlogService {
    String sendPost(PostDTO dto);
}
