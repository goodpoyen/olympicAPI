package com.olympicService.olympicAPI.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface SeatTagDownload {
	public List<XWPFDocument> setPageData(List<XWPFDocument> xwpfDocuments, List<Map<String, String>> listData)
			throws IOException;

	public XWPFDocument createNewFile(List<XWPFDocument> xwpfDocuments) throws Exception;
	
	public XWPFDocument mergeWord(XWPFDocument document, XWPFDocument doucDocument2) throws Exception;

}
