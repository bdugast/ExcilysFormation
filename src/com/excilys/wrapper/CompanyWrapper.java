package com.excilys.wrapper;

import java.util.List;

import com.excilys.domain.Company;

public class CompanyWrapper{

		List<Company> companies;

		public CompanyWrapper() {
			super();
		}
		public CompanyWrapper(List<Company> companies) {
			super();
			this.companies = companies;
		}

		public List<Company> getCompanies() {
			return companies;
		}
		public void setCompanies(List<Company> companies) {
			this.companies = companies;
		}

}
