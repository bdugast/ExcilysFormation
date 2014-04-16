package com.excilys.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.dto.ComputerDto;

@Component
public class ComputerMapper {
	
	@Autowired
	private ResourceBundleMessageSource messageSource;

	public Computer fromDto(ComputerDto compDto, Company company) {
		Locale locale = LocaleContextHolder.getLocale();
		
		int id;
		String name;
		DateTime introduced;
		DateTime discontinued;
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern(messageSource.getMessage("date.format.joda", null, locale));
		
		id = compDto.getId();
		name = compDto.getName();
		if(compDto.getIntroduced()!="") introduced = new DateTime(dtf.parseDateTime(compDto.getIntroduced()));
		else introduced = null;
		if(compDto.getDiscontinued()!="") discontinued = new DateTime(dtf.parseDateTime(compDto.getDiscontinued()));
		else discontinued = null;
		
		return new Computer(id, name, introduced, discontinued, company);
	}
	
	public ComputerDto toDto(Computer comp) {
		
		Locale locale = LocaleContextHolder.getLocale();
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern(messageSource.getMessage("date.format.joda", null, locale));
		
		String introduced = null;
		String discontinued = null;
		if(comp.getIntroduced()!=null) introduced = dtf.print(comp.getIntroduced());
		if(comp.getDiscontinued()!=null) discontinued = dtf.print(comp.getDiscontinued());
		
		ComputerDto compDto = ComputerDto.builder()
				.id(comp.getId())
                .name(comp.getName())
                .introduced(introduced)
                .discontinued(discontinued)
                .companyId(comp.getCompany().getId()).build();
		
		return compDto;
	}

	public List<ComputerDto> toListCompDto(List<Computer> compList) {
		List<ComputerDto> compDtoList = new ArrayList<ComputerDto>();
		for (Computer computer : compList) {
			compDtoList.add(toDto(computer));
		}
		return compDtoList;
	}
}
