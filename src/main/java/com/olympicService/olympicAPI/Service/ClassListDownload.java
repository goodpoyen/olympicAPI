package com.olympicService.olympicAPI.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

public interface ClassListDownload {
	public void setPageSize(XWPFDocument document);

	public void classTag(XWPFDocument document, String title, String className, String img, String star, String end)
			throws InvalidFormatException, IOException;

	public void setClassListTitle(XWPFTable table, String title, String img) throws InvalidFormatException, IOException;

	public void setClassListHeader(XWPFTable table);

	public void setClassListData(XWPFTable table, XWPFDocument document, List<Map<String, String>> listData);

	public void setSeatMap(XWPFTable table, XWPFDocument document, List<Map<String, String>> listData, String className,
			String imp) throws IOException, InvalidFormatException;

	public void setClassCheckTitle(XWPFTable table, String title, String img)
			throws InvalidFormatException, IOException;

	public void setClassCheckListHeader(XWPFTable table);

	public void setClassCheckListData(XWPFTable table, XWPFDocument documen, List<Map<String, String>> listData);

	public XWPFParagraph getCellParagraph(XWPFTable table, int row, int cell);

	public XWPFRun setTableWord(XWPFTable table, XWPFParagraph cellParagraph, int SpacingAfter, int SpacingBefore,
			int FontSize, Boolean Bold);
}
