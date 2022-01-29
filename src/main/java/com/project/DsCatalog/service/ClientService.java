package com.project.DsCatalog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.DsCatalog.DTO.ClienteDTO;
import com.project.DsCatalog.entity.Client;
import com.project.DsCatalog.exception.DatabaseException;
import com.project.DsCatalog.exception.ResourcesNotFoundException;
import com.project.DsCatalog.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = clientRepository.findAll(pageRequest);
		return list.map(x -> new ClienteDTO(x));
	}

	@Transactional(readOnly = true)
	public ClienteDTO findById(Long id) {
		Optional<Client> cliente = clientRepository.findById(id);
		Client clie = cliente.orElseThrow(() -> new ResourcesNotFoundException("Não foi encontrado cliente com esse" + id));
		return new ClienteDTO(clie);
	}

	@Transactional
	public ClienteDTO insert(ClienteDTO dto) {
		Client clie = new Client();
		clie.setName(dto.getName());
		clie.setCpf(dto.getCpf());
		clie.setIncome(dto.getIncome());
		clie.setBirthDate(dto.getBirthDate());
		clie.setChildren(dto.getChildren());
		clie = clientRepository.save(clie);
		return new ClienteDTO(clie);
	}

	public ClienteDTO update(Long id, ClienteDTO dto) {
		try {
			Client clie = clientRepository.getOne(id);
			clie.setName(dto.getName());
			clie.setCpf(dto.getCpf());
			clie.setIncome(dto.getIncome());
			clie.setBirthDate(dto.getBirthDate());
			clie.setChildren(dto.getChildren());
			clie = clientRepository.save(clie);
			return new ClienteDTO(clie);
		} catch (Exception e) {
			throw new ResourcesNotFoundException("Id não encontrado" + id);
		}

	
	}

	public void delete(Long id) {
		try {
			clientRepository.deleteById(id);
			
		}catch (EmptyResultDataAccessException e) {
			throw new ResourcesNotFoundException("Id não encontrado");
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

}
