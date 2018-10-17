package com.cm.style.profile.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.style.profile.dao.PublisherDao;
import com.cm.style.profile.model.StyleDetails;

@Service
public class ReportService {

	@Autowired
	PublisherDao publisherDao;

	public List<StyleDetails> styleProfileData(String createdBy) {		
		return publisherDao.getStyleDetails(createdBy);
	}
	
	/*public void writeXlsxReport(JasperPrint jp, HttpServletResponse response, final String reportName) throws IOException, JRException{
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "inline; filename=" + (reportName == null ? jp.getName() : reportName).replace('"', '_') + ".xlsx");

		
		JRXlsxExporter xlsxExporter = new JRXlsxExporter();
		
		
		ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
		
		xlsxExporter.setExporterInput(new SimpleExporterInput(jp));
		xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
		xlsxExporter.exportReport();

		final byte[] rawBytes = xlsReport.toByteArray();
		response.setContentLength(rawBytes.length);

		final ByteArrayInputStream bais = new ByteArrayInputStream(rawBytes);
		
		final OutputStream outStream = response.getOutputStream();
		IOUtils.copy(bais, outStream);

		outStream.flush();

		IOUtils.closeQuietly(xlsReport);
		IOUtils.closeQuietly(bais);
		IOUtils.closeQuietly(outStream);
	}*/
}
