package com.example.algoshuffer.service.Mapping;

import com.example.algoshuffer.entity.Mapping.ProblemTagMapping;
import com.example.algoshuffer.repository.Mapping.ProblemTagMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemTagMappingService {
    private final ProblemTagMappingRepository problemTagMappingRepository;

    public void saveProblemTagMapping(ProblemTagMapping problemTagMapping){
        problemTagMappingRepository.save(problemTagMapping);
    }
}
