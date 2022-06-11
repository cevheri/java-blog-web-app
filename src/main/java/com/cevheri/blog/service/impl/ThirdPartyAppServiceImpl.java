package com.cevheri.blog.service.impl;

import com.cevheri.blog.domain.ThirdPartyApp;
import com.cevheri.blog.repository.ThirdPartyAppRepository;
import com.cevheri.blog.service.ThirdPartyAppService;
import com.cevheri.blog.service.dto.ThirdPartyAppDTO;
import com.cevheri.blog.service.mapper.ThirdPartyAppMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ThirdPartyApp}.
 */
@Service
@Transactional
public class ThirdPartyAppServiceImpl implements ThirdPartyAppService {

    private final Logger log = LoggerFactory.getLogger(ThirdPartyAppServiceImpl.class);

    private final ThirdPartyAppRepository thirdPartyAppRepository;

    private final ThirdPartyAppMapper thirdPartyAppMapper;

    public ThirdPartyAppServiceImpl(ThirdPartyAppRepository thirdPartyAppRepository, ThirdPartyAppMapper thirdPartyAppMapper) {
        this.thirdPartyAppRepository = thirdPartyAppRepository;
        this.thirdPartyAppMapper = thirdPartyAppMapper;
    }

    @Override
    public ThirdPartyAppDTO save(ThirdPartyAppDTO thirdPartyAppDTO) {
        log.debug("Request to save ThirdPartyApp : {}", thirdPartyAppDTO);
        ThirdPartyApp thirdPartyApp = thirdPartyAppMapper.toEntity(thirdPartyAppDTO);
        thirdPartyApp = thirdPartyAppRepository.save(thirdPartyApp);
        return thirdPartyAppMapper.toDto(thirdPartyApp);
    }

    @Override
    public ThirdPartyAppDTO update(ThirdPartyAppDTO thirdPartyAppDTO) {
        log.debug("Request to save ThirdPartyApp : {}", thirdPartyAppDTO);
        ThirdPartyApp thirdPartyApp = thirdPartyAppMapper.toEntity(thirdPartyAppDTO);
        thirdPartyApp = thirdPartyAppRepository.save(thirdPartyApp);
        return thirdPartyAppMapper.toDto(thirdPartyApp);
    }

    @Override
    public Optional<ThirdPartyAppDTO> partialUpdate(ThirdPartyAppDTO thirdPartyAppDTO) {
        log.debug("Request to partially update ThirdPartyApp : {}", thirdPartyAppDTO);

        return thirdPartyAppRepository
            .findById(thirdPartyAppDTO.getId())
            .map(existingThirdPartyApp -> {
                thirdPartyAppMapper.partialUpdate(existingThirdPartyApp, thirdPartyAppDTO);

                return existingThirdPartyApp;
            })
            .map(thirdPartyAppRepository::save)
            .map(thirdPartyAppMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThirdPartyAppDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThirdPartyApps");
        return thirdPartyAppRepository.findAll(pageable).map(thirdPartyAppMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThirdPartyAppDTO> findOne(Long id) {
        log.debug("Request to get ThirdPartyApp : {}", id);
        return thirdPartyAppRepository.findById(id).map(thirdPartyAppMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThirdPartyApp : {}", id);
        thirdPartyAppRepository.deleteById(id);
    }
}
