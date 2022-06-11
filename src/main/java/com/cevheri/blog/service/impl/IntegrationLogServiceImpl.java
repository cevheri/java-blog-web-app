package com.cevheri.blog.service.impl;

import com.cevheri.blog.domain.IntegrationLog;
import com.cevheri.blog.repository.IntegrationLogRepository;
import com.cevheri.blog.service.IntegrationLogService;
import com.cevheri.blog.service.dto.IntegrationLogDTO;
import com.cevheri.blog.service.mapper.IntegrationLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IntegrationLog}.
 */
@Service
@Transactional
public class IntegrationLogServiceImpl implements IntegrationLogService {

    private final Logger log = LoggerFactory.getLogger(IntegrationLogServiceImpl.class);

    private final IntegrationLogRepository integrationLogRepository;

    private final IntegrationLogMapper integrationLogMapper;

    public IntegrationLogServiceImpl(IntegrationLogRepository integrationLogRepository, IntegrationLogMapper integrationLogMapper) {
        this.integrationLogRepository = integrationLogRepository;
        this.integrationLogMapper = integrationLogMapper;
    }

    @Override
    public IntegrationLogDTO save(IntegrationLogDTO integrationLogDTO) {
        log.debug("Request to save IntegrationLog : {}", integrationLogDTO);
        IntegrationLog integrationLog = integrationLogMapper.toEntity(integrationLogDTO);
        integrationLog = integrationLogRepository.save(integrationLog);
        return integrationLogMapper.toDto(integrationLog);
    }

    @Override
    public IntegrationLogDTO update(IntegrationLogDTO integrationLogDTO) {
        log.debug("Request to save IntegrationLog : {}", integrationLogDTO);
        IntegrationLog integrationLog = integrationLogMapper.toEntity(integrationLogDTO);
        integrationLog = integrationLogRepository.save(integrationLog);
        return integrationLogMapper.toDto(integrationLog);
    }

    @Override
    public Optional<IntegrationLogDTO> partialUpdate(IntegrationLogDTO integrationLogDTO) {
        log.debug("Request to partially update IntegrationLog : {}", integrationLogDTO);

        return integrationLogRepository
            .findById(integrationLogDTO.getId())
            .map(existingIntegrationLog -> {
                integrationLogMapper.partialUpdate(existingIntegrationLog, integrationLogDTO);

                return existingIntegrationLog;
            })
            .map(integrationLogRepository::save)
            .map(integrationLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IntegrationLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IntegrationLogs");
        return integrationLogRepository.findAll(pageable).map(integrationLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntegrationLogDTO> findOne(Long id) {
        log.debug("Request to get IntegrationLog : {}", id);
        return integrationLogRepository.findById(id).map(integrationLogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IntegrationLog : {}", id);
        integrationLogRepository.deleteById(id);
    }
}
