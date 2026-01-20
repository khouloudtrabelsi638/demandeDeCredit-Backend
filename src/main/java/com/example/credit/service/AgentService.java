package com.example.credit.service;


import com.example.credit.model.Agent;
import com.example.credit.repository.AgentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {

    private final AgentRepository agentRepository; 
    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Optional<Agent> getAgentById(Long id) {
        return agentRepository.findById(id);
    }

    public Agent createAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    public Agent updateAgent(Long id, Agent updatedAgent) {
        return agentRepository.findById(id).map(agent -> {
            agent.setUsername(updatedAgent.getUsername());
            agent.setPassword(updatedAgent.getPassword());
            agent.setRole(updatedAgent.getRole());
            return agentRepository.save(agent);
        }).orElse(null);
    }

    public void deleteAgent(Long id) {
        agentRepository.deleteById(id);
    }

    public Optional<Agent> getAgentByUsername(String username) {
        return agentRepository.findByUsername(username);
    }
    
}
