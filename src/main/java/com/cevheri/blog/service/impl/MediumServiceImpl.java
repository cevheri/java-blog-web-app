package com.cevheri.blog.service.impl;

import com.cevheri.blog.domain.enumeration.ExitCodeType;
import com.cevheri.blog.domain.enumeration.ThirdPartyAppName;
import com.cevheri.blog.security.SecurityUtils;
import com.cevheri.blog.service.IntegrationLogService;
import com.cevheri.blog.service.ThirdPartyBlogService;
import com.cevheri.blog.service.dto.IntegrationLogDTO;
import com.cevheri.blog.service.dto.PostDTO;
import com.cevheri.blog.service.mapper.PostMapper;
import com.cevheri.blog.service.medium.PostApi;
import com.cevheri.blog.service.medium.PostResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;

@Service
@Transactional
public class MediumServiceImpl
    implements ThirdPartyBlogService {

    private final PostMapper postMapper;
    private final IntegrationLogService integrationLogService;

    public MediumServiceImpl(PostMapper postMapper,
                         IntegrationLogService integrationLogService) {
        this.postMapper = postMapper;
        this.integrationLogService = integrationLogService;
    }


    @Override
    public String sendPost(PostDTO postDTO) {
        IntegrationLogDTO integrationLogDTO = new IntegrationLogDTO();
        PostResponse result = new PostResponse();
        try {

//            if(postDTO.getIntegrationId()!=null){
//                //TODO call medium updatePost api.
//            }
            result = PostApi.createPost(postMapper.toPostModel(postDTO));
            if(!result.getErrors().isEmpty()){
                //TODO PostCreateError. Medium response Error. Write a log!!!!
            }
            integrationLogDTO.setExitCode(ExitCodeType.SUCCESS);
            integrationLogDTO.setResponseData(result.toString());
        }catch (IOException ie){
            integrationLogDTO.setExitCode(ExitCodeType.ERROR);
            integrationLogDTO.setResponseData(null);
            integrationLogDTO.setErrorMessage(ExceptionUtils.getRootCauseMessage(ie));
        }finally {
            integrationLogDTO.setApiUrl(result.getApiUrl());
            integrationLogDTO.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            integrationLogDTO.setCreatedDate(Instant.now());
            integrationLogDTO.setIntegrationName(ThirdPartyAppName.MEDIUM);
            integrationLogDTO.setRequestData(postDTO.toString());
            integrationLogService.save(integrationLogDTO);
        }
        String integrationId = result.getData().getId();
        return integrationId;
    }

}
