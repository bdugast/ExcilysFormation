package com.excilys.validator;

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

	@Override
	public boolean validateId(String id) {
		String regex = "\\d+";
		LOG.debug("error !!! " + id);
		if(id==null){
			return false;
		}else{
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
}
