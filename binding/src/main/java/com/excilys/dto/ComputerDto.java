package com.excilys.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.excilys.validator.DateValidator;

/**
 * @author bdugast
 * @param id
 * 		id of the computer
 * @param name
 * 		name of the computer (not empty, and length of 2 characters min)
 * @param introduced
 * 		string of the introduced date (must check the custom annotation)
 * @param discontinued
 * 		string of the discontinued date (must check the custom annotation)
 * @param companyId
 * 		id of the company
 */
public class ComputerDto {
	private int id;
	
	@NotEmpty(message="{NotEmpty.computerDto.name}")
	@Size(min=2, message="{Size.computerDto.name}") 
	private String name;
	
	@DateValidator
	private String introduced;

	@DateValidator
	private String discontinued;

	private int companyId;
	private String companyName;
	
	public static class Builder {

        ComputerDto computer;

        private Builder() {
            computer = new ComputerDto();
        }

        public Builder id(Integer id) {
            if(id != null)
                this.computer.id = id;
            return this;
        }

        public Builder name(String name) {
            this.computer.name = name;
            return this;
        }

        public Builder introduced(String introduced) {
           this.computer.introduced = introduced;
            return this;
        }

        public Builder discontinued(String discontinued) {
            this.computer.discontinued = discontinued;
            return this;
        }

        public Builder companyId(Integer companyId) {
            this.computer.companyId = companyId;
            return this;
        }
        public Builder companyName(String companyName) {
            this.computer.companyName = companyName;
            return this;
        }

        public ComputerDto build() {
            return this.computer;
        }

    }

    public static Builder builder() {
        return new Builder();
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduced() {
		return introduced;
	}
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
