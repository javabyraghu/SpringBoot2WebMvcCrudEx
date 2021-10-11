package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.model.Employee;
import in.nareshit.raghu.repo.EmployeeRepository;
import in.nareshit.raghu.service.IEmployeeService;
import in.nareshit.raghu.util.EmployeeUtil;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private EmployeeRepository repo;

	@Autowired
	private EmployeeUtil employeeUtil;

	public Integer saveEmployee(Employee e) {
		//calculations
		employeeUtil.calculateData(e);
		return repo.save(e).getEmpId();
	}

	public List<Employee> getAllEmployees() {
		return repo.findAll();
	}

	public Employee getOneEmployee(Integer id) {
		Optional<Employee> opt = repo.findById(id);
		if(opt.isPresent())
			return opt.get();
		else 
			return null;
	}

	public void deleteEmployee(Integer id) {
		repo.deleteById(id);
	}

	public void updateEmployee(Employee e) {
		//calculations
		employeeUtil.calculateData(e);
		repo.save(e);
	}

	public boolean isEmployeeMailExist(String email) {
		Integer count = repo.getEmployeeMailCount(email);
		boolean exist = count>0?true:false;
		return exist;
		//return repo.getEmployeeMailCount(email)>0;
	}

	public Page<Employee> getAllEmployees(Pageable pageable) {
		return repo.findAll(pageable);
	}

}
