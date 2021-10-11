package in.nareshit.raghu.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.Employee;

public interface EmployeeRepository
	extends JpaRepository<Employee, Integer>
{

	@Query("SELECT count(e.empMail) FROM Employee e  WHERE e.empMail=:email")
	public Integer getEmployeeMailCount(String email);
	
}
