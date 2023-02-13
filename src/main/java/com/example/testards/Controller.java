package com.example.testards;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	private final CompanyRepository companyRepository;
	private final UserRepository userRepository;

	public Controller(CompanyRepository companyRepository, UserRepository userRepository) {
		this.companyRepository = companyRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/")
	public String test(){
		String value = "";
		MultitenantContext.setCurrentTenant(Schema.company.name()); // This is the lookup key for the AbstractRoutingDataSource
		value += companyRepository.count();

		MultitenantContext.setCurrentTenant(Schema.user.name());
		value += userRepository.count();
		return value;
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	private Long countUsers(){
		return userRepository.count();
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	private Long countCompanies(){
		return companyRepository.count();
	}
}