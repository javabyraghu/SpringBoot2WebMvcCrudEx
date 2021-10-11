package in.nareshit.raghu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.model.Employee;
import in.nareshit.raghu.service.IEmployeeService;
import in.nareshit.raghu.view.EmployeeExcelView;
import in.nareshit.raghu.view.EmployeePdfView;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private IEmployeeService service;

	//1. show Register page
	@GetMapping("/register")
	public String showReg() {
		return "EmployeeRegister";
	}

	//2. save operation
	@PostMapping("/save")
	public String saveData(
			@ModelAttribute Employee employee, //reading form data
			Model model
			) 
	{
		Integer id = service.saveEmployee(employee); //store in db
		String message = "Employee '"+id+"' Created!"; //create message
		model.addAttribute("message", message); //send message to ui
		return "EmployeeRegister";
	}

	//3. display data
	/*@GetMapping("/all")
	public String getAll(
			@RequestParam(required = false) String message,
			Model model
			) 
	{
		List<Employee> list = service.getAllEmployees();
		model.addAttribute("list", list);
		model.addAttribute("message", message);
		return "EmployeeData";
	}*/

	//3. Display data using pagination
	//.../all?page=3&size=10
	@GetMapping("/all")
	public String getAll(
			@PageableDefault(page = 0, size = 3) Pageable pageable,
			@RequestParam(required = false) String message,
			Model model
			) 
	{
		Page<Employee> page = service.getAllEmployees(pageable);
		model.addAttribute("list", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("message", message);
		return "EmployeeData";
	}


	//4. remove operation
	@GetMapping("/remove")
	public String removeData(
			@RequestParam Integer id,
			RedirectAttributes attributes
			) 
	{
		service.deleteEmployee(id);
		attributes.addAttribute("message", "Employee '"+id+"' deleted");
		return "redirect:all";
	}

	//5. show edit
	@GetMapping("/edit")
	public String showEdit(
			@RequestParam Integer id,
			Model model) 
	{
		Employee e = service.getOneEmployee(id);
		model.addAttribute("employee", e);
		return "EmployeeEdit";
	}


	//6. do update
	@PostMapping("/update")
	public String updateData(
			@ModelAttribute Employee employee,
			RedirectAttributes attributes
			) 
	{
		service.updateEmployee(employee);
		attributes.addAttribute("message", "Employee '"+employee.getEmpId()+"' Updated!");
		return "redirect:all";
	}

	//7. emailid exist check
	@GetMapping("/validate")
	@ResponseBody
	public String validateEmail(
			@RequestParam String email
			) 
	{
		String message="";

		if(service.isEmployeeMailExist(email)) {
			message = email+", already exist!";
		}
		return message;
	}

	//8. Excel Export
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		ModelAndView m = new ModelAndView();
		m.setView(new EmployeeExcelView());
		m.addObject("list", service.getAllEmployees());
		return m;
	}

	//9. Excel Export
	@GetMapping("/pdf")
	public ModelAndView exportToPdf() {
		ModelAndView m = new ModelAndView();
		m.setView(new EmployeePdfView());
		m.addObject("list", service.getAllEmployees());
		return m;
	}

}
