package com.example.testards;

import org.springframework.data.repository.ListCrudRepository;

public interface CompanyRepository extends ListCrudRepository<Company, Long> {
}