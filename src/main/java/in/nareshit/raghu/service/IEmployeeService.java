package in.nareshit.raghu.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.nareshit.raghu.model.Employee;

public interface IEmployeeService {

	Integer saveEmployee(Employee e);
	List<Employee> getAllEmployees();
	Employee getOneEmployee(Integer id);
	void deleteEmployee(Integer id);
	void updateEmployee(Employee e);
	boolean isEmployeeMailExist(String email);
	
	Page<Employee> getAllEmployees(Pageable pageable);
}
