package com.excilys.mapper;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.domain.Company;
import com.excilys.domain.Computer;
import com.excilys.dto.ComputerDto;
import com.excilys.service.impl.CompanyServiceImpl;

@Component
public class ComputerMapper {
	
	@Autowired
	CompanyServiceImpl companyService;

	public Computer fromDto(ComputerDto compDto) {
		int id;
		String name;
		DateTime introduced;
		DateTime discontinued;
		Company company;
		
		id = compDto.getId();
		name = compDto.getName();
		if(compDto.getIntroduced()!="") introduced = new DateTime(compDto.getIntroduced());
		else introduced = null;
		if(compDto.getDiscontinued()!="") discontinued = new DateTime(compDto.getDiscontinued());
		else discontinued = null;
		if(companyService.getOneCompany(compDto.getCompanyId()) != null) company = companyService.getOneCompany(compDto.getCompanyId());
		else company = null;
		
		return new Computer(id, name, introduced, discontinued, company);
	}
	
	public ComputerDto toDto(Computer comp) {
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		
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
}
