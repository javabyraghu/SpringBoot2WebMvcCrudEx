package in.nareshit.raghu.util;

import org.springframework.stereotype.Component;

import in.nareshit.raghu.model.Employee;

@Component
public class EmployeeUtil {

	public void calculateData(Employee e) {
		double sal = e.getEmpSal();
		e.setEmpHra(sal*12/100.0);
		e.setEmpTa(sal*3/100.0);		
	}

}
