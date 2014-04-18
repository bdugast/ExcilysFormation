package com.excilys.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.service.ComputerService;

@Component
public class IdValidator{
	
	static final Logger LOG = LoggerFactory.getLogger(IdValidator.class);

	@Autowired
	ComputerService computerService;

	public boolean validateId(String id) {
		String regex = "\\d+";
		if(id==null){
			return false;
		}else{
			if(id.matches(regex)){
				if(Integer.parseInt(id)>0){
					if(computerService.getOneComputer(Integer.parseInt(id)) != null){
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
