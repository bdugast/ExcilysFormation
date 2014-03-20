package org.excilys.wrapper;

import java.util.List;

import org.excilys.domain.Company;

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
