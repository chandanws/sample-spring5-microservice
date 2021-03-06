package de.rieckpil.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import de.rieckpil.domain.Country;
import de.rieckpil.dtos.CountryDTO;
import de.rieckpil.mapper.CountryMapper;
import de.rieckpil.repositories.CountryRepository;

@Service
public class CountryServiceImpl implements CountryService {

  private CountryRepository countryRepository;
  private  ApplicationEventPublisher publisher;
  private CountryMapper countryMapper;

  public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper, ApplicationEventPublisher publisher) {
    this.countryRepository = countryRepository;
    this.countryMapper = countryMapper;
    this.publisher = publisher;
  }

  @Override
  public List<CountryDTO> getAllCountries() {
    return countryRepository.findAll().stream().map(countryMapper::countryToCountryDTO)
        .collect(Collectors.toList());
  }

  @Override
  public int getAmountOfStoredCountries() {
    return getAllCountries().size();
  }

  @Override
  public CountryDTO getCountryByName(String countryName) {

    Optional<Country> optionalCountry = countryRepository.findValidByName(countryName);

    if (!optionalCountry.isPresent()) {
      throw new RuntimeException("Country not found");
    }

    return countryMapper.countryToCountryDTO(optionalCountry.get());
  }

  @Override
  public void createNewCountry(CountryDTO country) {

    try {
      Country countryToSave = countryMapper.countryDTOToCountry(country);
      countryRepository.save(countryToSave);
      publisher.publishEvent(countryToSave);
    } catch (DataIntegrityViolationException ex) {
      System.out.println("## catched: " + ex.getMessage());
      throw new RuntimeException("Constraint violations!");
    }
  }

  @Override
  public void deleteByCountryName(String countryName) {
    
    Optional<Country> country = countryRepository.findByName(countryName);
    
    if(!country.isPresent()) {
      throw new RuntimeException(String.format("Country with name: '%s' not found!", countryName));
    }
    
    countryRepository.delete(country.get());
  }
}
