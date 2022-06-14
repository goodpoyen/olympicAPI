package com.olympicService.olympicAPI.Service.Impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.stereotype.Service;

import com.olympicService.olympicAPI.Service.ClassListDownload;

@Service
public class ClassListDownloadImpl implements ClassListDownload {
	public void test() throws IOException {
		List<Map<String, String>> listData = testData();

		XWPFDocument document = new XWPFDocument();

		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File("C:\\workingSpace\\createdocument.docx"));

		classTag(document, "2022年資訊奧林匹亞競賽", "第1試場", "A21010101", "A21010109");

		// 教室考生名單
		XWPFTable table = document.createTable();

		table.setWidth(8300);

		setClassListTitle(table, "臺灣師範大學 第 1 試場");

		setClassListHeader(table);

		setClassListData(table, document, listData);

		// 教室點名單
		table = document.createTable();

		table.setWidth(8300);

		setClassCheckTitle(table, "臺灣師範大學 第 1 試場");

		setClassCheckListHeader(table);

		setClassCheckListData(table, document, listData);

		document.write(out);
		out.close();
	}

	public void classTag(XWPFDocument document, String title, String className, String star, String end) {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = paragraph.createRun();

		run.setFontSize(36);
		run.setBold(true);
		run.setText(title);
		run.addBreak();
		run.addBreak();
		run.addBreak();
		run.addBreak();
		run = paragraph.createRun();
		run.setFontSize(48);
		run.setBold(true);
		run.setText(className);
		run.addBreak();
		run.setText(star);
		run.addBreak();
		run.setText("~");
		run.addBreak();
		run.setText(end);

		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setPageBreak(true);
	}

	public void setClassListTitle(XWPFTable table, String title) {
		table.getRow(0).setHeight(900);

		XWPFParagraph cellParagraph = getCellParagraph(table, 0, 0);

		XWPFRun cellParagraphRun = setTableWord(table, cellParagraph, 300, 300, 16, true);

		cellParagraphRun.setText(String.valueOf(title));

		table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);

		table.getRow(0).addNewTableCell();
		table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);

		table.getRow(0).addNewTableCell();
		table.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);

		table.getRow(0).addNewTableCell();
		table.getRow(0).getCell(3).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
	}

	public void setClassListHeader(XWPFTable table) {
		table.createRow();
		table.getRow(1).setHeight(500);

		XWPFParagraph cellParagraph = getCellParagraph(table, 1, 0);
		XWPFRun cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
		cellParagraphRun.setText(String.valueOf("准考證號碼"));

		cellParagraph = getCellParagraph(table, 1, 1);
		cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
		cellParagraphRun.setText(String.valueOf("姓名"));

		cellParagraph = getCellParagraph(table, 1, 2);
		cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
		cellParagraphRun.setText(String.valueOf("准考證號碼"));

		cellParagraph = getCellParagraph(table, 1, 3);
		cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
		cellParagraphRun.setText(String.valueOf("姓名"));
	}

	public void setClassListData(XWPFTable table, XWPFDocument document, List<Map<String, String>> listData) {
		XWPFParagraph cellParagraph = null;

		XWPFRun cellParagraphRun = null;

		for (int i = 0; i < listData.size(); i++) {

			int row = 0;

			if ((i + 1) % 2 == 0) {
				row = ((i + 1) / 2) - 1;

				cellParagraph = getCellParagraph(table, row + 2, 2);
				cellParagraphRun = setTableWord(table, cellParagraph, 80, 80, 14, true);
				cellParagraphRun.setText(String.valueOf(listData.get(i).get("id")));

				cellParagraph = getCellParagraph(table, row + 2, 3);
				cellParagraphRun = setTableWord(table, cellParagraph, 80, 80, 12, false);
				cellParagraphRun.setText(String.valueOf(listData.get(i).get("name")));
			} else {
				table.createRow();
				row = ((i + 1) / 2);

				cellParagraph = getCellParagraph(table, row + 2, 0);
				cellParagraphRun = setTableWord(table, cellParagraph, 80, 80, 14, true);
				cellParagraphRun.setText(String.valueOf(listData.get(i).get("id")));

				cellParagraph = getCellParagraph(table, row + 2, 1);
				cellParagraphRun = setTableWord(table, cellParagraph, 80, 80, 12, false);
				cellParagraphRun.setText(String.valueOf(listData.get(i).get("name")));
			}
		}

		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setPageBreak(true);
	}

	public void setClassCheckTitle(XWPFTable table, String title) {
		table.getRow(0).setHeight(900);

		XWPFParagraph cellParagraph = getCellParagraph(table, 0, 0);

		XWPFRun cellParagraphRun = setTableWord(table, cellParagraph, 300, 300, 16, true);

		cellParagraphRun.setText(String.valueOf(title));

		table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);

		table.getRow(0).addNewTableCell();
		table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);

		table.getRow(0).addNewTableCell();
		table.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);

		table.getRow(0).addNewTableCell();
		table.getRow(0).getCell(3).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);

		table.getRow(0).addNewTableCell();
		table.getRow(0).getCell(4).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
	}

	public void setClassCheckListHeader(XWPFTable table) {
		table.createRow();
		table.getRow(1).setHeight(500);

		XWPFParagraph cellParagraph = getCellParagraph(table, 1, 0);
		XWPFRun cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
		cellParagraphRun.setText(String.valueOf("准考證號碼"));

		cellParagraph = getCellParagraph(table, 1, 1);
		cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
		cellParagraphRun.setText(String.valueOf("識別碼"));

		cellParagraph = getCellParagraph(table, 1, 2);
		cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
		cellParagraphRun.setText(String.valueOf("姓名"));

		cellParagraph = getCellParagraph(table, 1, 3);
		cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
		cellParagraphRun.setText(String.valueOf("簽到"));

		cellParagraph = getCellParagraph(table, 1, 4);
		cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
		cellParagraphRun.setText(String.valueOf("點名"));
	}

	public void setClassCheckListData(XWPFTable table, XWPFDocument document, List<Map<String, String>> listData) {
		XWPFParagraph cellParagraph = null;

		XWPFRun cellParagraphRun = null;

		for (int i = 0; i < listData.size(); i++) {
			table.createRow();

			cellParagraph = getCellParagraph(table, i + 2, 0);
			cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
			cellParagraphRun.setText(String.valueOf(listData.get(i).get("id")));

			cellParagraph = getCellParagraph(table, i + 2, 1);
			cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
			cellParagraphRun.setText(String.valueOf(listData.get(i).get("i")));

			cellParagraph = getCellParagraph(table, i + 2, 2);
			cellParagraphRun = setTableWord(table, cellParagraph, 100, 100, 16, true);
			cellParagraphRun.setText(String.valueOf(listData.get(i).get("name")));
		}

		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setFontSize(16);
		run.setBold(true);
		run.addBreak();
		run.addBreak();
		run.setText("監考人員簽名：______________");
	}

	public XWPFParagraph getCellParagraph(XWPFTable table, int row, int cell) {
		XWPFParagraph cellParagraph = table.getRow(row).getCell(cell).getParagraphArray(0);
		cellParagraph.setAlignment(ParagraphAlignment.CENTER);

		return cellParagraph;
	}

	public XWPFRun setTableWord(XWPFTable table, XWPFParagraph cellParagraph, int SpacingAfter, int SpacingBefore,
			int FontSize, Boolean Bold) {
		XWPFRun cellParagraphRun = cellParagraph.createRun();
		cellParagraph.setSpacingAfter(SpacingAfter);
		cellParagraph.setSpacingBefore(SpacingBefore);
		cellParagraphRun.setFontFamily("標楷體");
		cellParagraphRun.setFontSize(FontSize);
		cellParagraphRun.setBold(Bold);

		return cellParagraphRun;
	}

	public List<Map<String, String>> testData() {
		Map<String, String> data = new HashMap<>();

		List<Map<String, String>> listData = new ArrayList();

		data.put("id", "A21010101");
		data.put("i", "A2325");
		data.put("name", "林亭");
		listData.add(0, data);

		data = new HashMap<>();
		data.put("id", "A21010102");
		data.put("i", "T1675");
		data.put("name", "陳棋閔");
		listData.add(1, data);

		data = new HashMap<>();
		data.put("id", "A21010103");
		data.put("i", "F2395");
		data.put("name", "詹惟涵");
		listData.add(2, data);

		data = new HashMap<>();
		data.put("id", "A21010104");
		data.put("i", "F1678");
		data.put("name", "蔡季霖");
		listData.add(3, data);

		data = new HashMap<>();
		data.put("id", "A21010105");
		data.put("i", "A1198");
		data.put("name", "李岱昇");
		listData.add(4, data);

		data = new HashMap<>();
		data.put("id", "A21010106");
		data.put("i", "P1945");
		data.put("name", "李祐瑋");
		listData.add(5, data);

		data = new HashMap<>();
		data.put("id", "A21010107");
		data.put("i", "T1955");
		data.put("name", "洪冰儒");
		listData.add(6, data);

		data = new HashMap<>();
		data.put("id", "A21010108");
		data.put("i", "K2835");
		data.put("name", "張庭毓");
		listData.add(7, data);

		data = new HashMap<>();
		data.put("id", "A21010109");
		data.put("i", "Q1935");
		data.put("name", "陳致嘉");
		listData.add(8, data);

		return listData;
	}
}
