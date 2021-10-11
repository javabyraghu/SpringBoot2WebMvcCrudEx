package in.nareshit.raghu.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import in.nareshit.raghu.model.Employee;

public class EmployeeExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, 
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//1. provide file name
		response.addHeader("Content-Disposition", "attachment;filename=employees.xls");

		//2. read data given by controller class
		@SuppressWarnings("unchecked")
		List<Employee> emps =  (List<Employee>) model.get("list");

		//3. create one new sheet
		Sheet sheet = workbook.createSheet("EMPLOYEES");

		//4. create Header
		setHead(sheet);
		//5. create Body
		setBody(sheet,emps);

	}

	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("NAME");
		row.createCell(2).setCellValue("SALARY");
		row.createCell(3).setCellValue("GENDER");
		row.createCell(4).setCellValue("MAIL");
		row.createCell(5).setCellValue("DEPT");
	}

	private void setBody(Sheet sheet, List<Employee> emps) {
		int count = 1;
		for(Employee emp :emps) {
			Row row = sheet.createRow(count++);
			row.createCell(0).setCellValue(emp.getEmpId());
			row.createCell(1).setCellValue(emp.getEmpName());
			row.createCell(2).setCellValue(emp.getEmpSal());
			row.createCell(3).setCellValue(emp.getEmpGen());
			row.createCell(4).setCellValue(emp.getEmpMail());
			row.createCell(5).setCellValue(emp.getEmpDept());
		}
	}
}
