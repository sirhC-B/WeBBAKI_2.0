package de.thb.webbaki.service;

import de.thb.webbaki.entity.MasterScenario;
import de.thb.webbaki.repository.MasterScenarioRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
@AllArgsConstructor
public class MasterScenarioService {
    private final MasterScenarioRepository masterScenarioRepository;

    public List<MasterScenario> getAllMasterScenarios(){return (List<MasterScenario>) masterScenarioRepository.findAll();}


}
