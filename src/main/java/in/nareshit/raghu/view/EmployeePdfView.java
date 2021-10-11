package in.nareshit.raghu.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.nareshit.raghu.model.Employee;

public class EmployeePdfView extends AbstractPdfView {
	
	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		HeaderFooter header = new HeaderFooter(new Phrase("WELCOME TO EMPLOYEE"), false);
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);
		
		HeaderFooter footer = new HeaderFooter(new Phrase("Page No:#  "),true);
		footer.setAlignment(Element.ALIGN_RIGHT);
		document.setFooter(footer);
	}

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception 
	{
		//1. provide file name
		//response.addHeader("Content-Disposition", "attachment;filename=employees.pdf");

		//2. read data given by controller class
		@SuppressWarnings("unchecked")
		List<Employee> emps =  (List<Employee>) model.get("list");
		
		Font titleFont = new Font(Font.TIMES_ROMAN, 30, Font.BOLD , Color.RED);
		Paragraph title = new Paragraph("EMPLOYEES DATA",titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(10.0f);
		title.setSpacingAfter(35.0f);
		
		document.add(title);
		
		PdfPTable table = new PdfPTable(5);
		table.addCell("ID");
		table.addCell("NAME");
		table.addCell("SAL");
		table.addCell("GEN");
		table.addCell("DEPT");
		
		for(Employee e : emps ) {
			table.addCell(e.getEmpId().toString());
			table.addCell(e.getEmpName());
			table.addCell(e.getEmpSal().toString());
			table.addCell(e.getEmpGen());
			table.addCell(e.getEmpDept());
		}
		
		document.add(table);
		
	}
}
