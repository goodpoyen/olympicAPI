package com.olympicService.olympicAPI.Service;

import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

public interface ClassListDownload {
	public void classTag(XWPFDocument document, String title, String className, String star, String end);

	public void setClassListTitle(XWPFTable table, String title);

	public void setClassListHeader(XWPFTable table);

	public void setClassListData(XWPFTable table, XWPFDocument document, List<Map<String, String>> listData);

	public void setClassCheckTitle(XWPFTable table, String title);

	public void setClassCheckListHeader(XWPFTable table);

	public void setClassCheckListData(XWPFTable table, XWPFDocument documen, List<Map<String, String>> listData);

	public XWPFParagraph getCellParagraph(XWPFTable table, int row, int cell);

	public XWPFRun setTableWord(XWPFTable table, XWPFParagraph cellParagraph, int SpacingAfter, int SpacingBefore,
			int FontSize, Boolean Bold);
}
