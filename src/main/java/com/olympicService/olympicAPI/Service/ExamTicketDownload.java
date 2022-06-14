package com.olympicService.olympicAPI.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public interface ExamTicketDownload {
	File updatePDF(byte[] tpeHtcxyw);

	void writeData(byte[] tpeHtcxyw, ColumnText columnText, Font font, String data, String key)
			throws FileNotFoundException, DocumentException, IOException;

	void waterMarkByImg(PdfReader reader, PdfStamper stamper, String logoPath) throws Exception;
	
	void waterMarkByWord (PdfStamper stamper, BaseFont baseFont, int page, String key);
	
	float[] getCoordinate(String signKey, byte[] pdf);
}
