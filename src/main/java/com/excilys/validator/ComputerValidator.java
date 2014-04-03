package com.excilys.validator;

import java.util.HashMap;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.dao.impl.CompanyDaoImpl;
import com.excilys.dao.impl.ComputerDaoImpl;
import com.excilys.dto.ComputerDto;

@Component
public class ComputerValidator implements Validator<ComputerDto>{
	
	static final Logger LOG = LoggerFactory.getLogger(ComputerValidator.class);

	@Autowired
	ComputerDaoImpl computerDao;
	@Autowired
	CompanyDaoImpl companyDao;

	public HashMap<String, String> validate(ComputerDto compDto) {
		HashMap<String, String> errorHash = new HashMap<>();

		String reg = "^(\\d{4})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
		
		String name = compDto.getName();
		String introduced = compDto.getIntroduced();
		String discontinued = compDto.getDiscontinued();
		int companyId = compDto.getCompanyId();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		if (name.equals(null)){
			if (name.length() < 2)
				errorHash.put("nameError", "Name lenght must be more than 2 characters");
		}
		LOG.debug("introduced " + introduced);
		if (!introduced.equals("")) {
			if(!introduced.matches(reg)){
				errorHash.put("introducedError","Format incorrect yyyy-mm-dd");
			}else{
				try {
					fmt.parseDateTime(introduced);
				} catch (Exception e) {
					errorHash.put("introducedError","Format incorrect yyyy-mm-dd");
				}
			}
		}
		LOG.debug("discontinued " + discontinued.toString());
		if (!discontinued.equals("")) {
			if(!discontinued.matches(reg)){
				errorHash.put("discontinuedError","Format incorrect yyyy-mm-dd");
			}else{
				try {
					fmt.parseDateTime(discontinued);
				} catch (Exception e) {
					errorHash.put("discontinuedError","Format incorrect yyyy-mm-dd");
				}
			}
		}
		LOG.debug("company " + companyId );
		if(companyId!=-1){
			if(companyDao.getOneCompany(companyId)==null)
				errorHash.put("companyError","your company doesn't exist");
		}
		return errorHash;
	}

	@Override
	public boolean validateId(String id) {
		String regex = "\\d+";
		if(id.matches(regex)){
			if(Integer.parseInt(id)>0){
				if(computerDao.getOneComputer(Integer.parseInt(id)) != null){
					LOG.debug("validateID : Good Computer");
					return true;
				}else{
					LOG.debug("validateID : erreur get");
					return false;			
				}
			}else{
				LOG.debug("validateID : erreur parseInt");
				return false;
			}
		}else{
			LOG.debug("validateID : erreur regex");
			return false;
		}
	}
}
