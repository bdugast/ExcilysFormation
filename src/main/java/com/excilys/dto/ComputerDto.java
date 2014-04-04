package com.excilys.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


public class ComputerDto {
	private int id;
	
	@NotEmpty(message="must be filled") @Size(min=2, message="at least 2 characters") 
	private String name;
	
	@Pattern(regexp = "^|(\\d{4})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message="Correct date format 'yyyy-mm-dd' required")
	private String introduced;

	@Pattern(regexp = "^|(\\d{4})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message="Correct date format 'yyyy-mm-dd' required")
	private String discontinued;

	private int companyId;
	
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
}
