package com.olympicService.olympicAPI.Service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.stereotype.Service;

import com.olympicService.olympicAPI.Service.ClassListDownload;

@Service
public class ClassListDownloadImpl implements ClassListDownload {
	public void test() throws IOException, InvalidFormatException {
		List<Map<String, String>> listData = testData();

		XWPFDocument document = new XWPFDocument();

		setPageSize(document);

		FileOutputStream out = new FileOutputStream(new File("C:\\workingSpace\\createdocument.docx"));
		
		// 教室標示
		classTag(document, "2022年資訊奧林匹亞競賽", "第1試場", "C://workingSpace/logo.png", "A21010101", "A21010109");

		// 教室考生名單
		XWPFTable table = document.createTable();

		table.setWidth(10700);

		setClassListTitle(table, "臺灣師範大學 第 1 試場", "C://workingSpace/logo.png");

		setClassListHeader(table);

		setClassListData(table, document, listData);

		// 教室座位表
		setSeatMap(table, document, listData, "第 1 試場", "C://workingSpace/logo.png");

		// 教室點名單
		table = document.createTable();

		table.setWidth(10700);

		setClassCheckTitle(table, "臺灣師範大學 第 1 試場", "C://workingSpace/logo.png");

		setClassCheckListHeader(table);

		setClassCheckListData(table, document, listData);

		document.write(out);
		out.close();
	}

	public void setPageSize(XWPFDocument document) {
		CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();

		CTPageMar pageMar = sectPr.addNewPgMar();

		pageMar.setLeft(BigInteger.valueOf(720L));

		pageMar.setTop(BigInteger.valueOf(900L));

		pageMar.setRight(BigInteger.valueOf(720L));

		pageMar.setBottom(BigInteger.valueOf(900L));
	}

	public void classTag(XWPFDocument document, String title, String className, String img, String star, String end)
			throws InvalidFormatException, IOException {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = paragraph.createRun();

		run.setFontSize(46);
		run.setBold(true);
		run.setText(title);
		run.addBreak();

		String imgFile = img;
		FileInputStream is = new FileInputStream(imgFile);
		run.addBreak();
		run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(120), Units.toEMU(120));

		is.close();

		run.addBreak();
		run.addBreak();
		run = paragraph.createRun();
		run.setFontSize(72);
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

	public void setClassListTitle(XWPFTable table, String title, String img)
			throws InvalidFormatException, IOException {
		XWPFParagraph cellParagraph = null;

		XWPFRun cellParagraphRun = null;

		cellParagraph = getCellParagraph(table, 0, 0);
		cellParagraphRun = setTableWord(table, cellParagraph, 250, 250, 16, true);
		String imgFile = img;
		FileInputStream is = new FileInputStream(imgFile);
		cellParagraphRun.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(50), Units.toEMU(50));
		is.close();

		table.getRow(0).addNewTableCell();
		cellParagraph = getCellParagraph(table, 0, 1);
		cellParagraphRun = setTableWord(table, cellParagraph, 250, 600, 16, true);
		cellParagraphRun.setText(String.valueOf(title));
		table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);

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

	public void setSeatMap(XWPFTable table, XWPFDocument document, List<Map<String, String>> listData, String className,
			String imp) throws IOException, InvalidFormatException {
		XWPFParagraph cellParagraph = null;

		XWPFRun cellParagraphRun = null;

		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = paragraph.createRun();

		run.setFontSize(46);
		run.setBold(true);
		run.setText(className + " 座位表");
		run.addBreak();

		String imgFile = imp;
		FileInputStream is = new FileInputStream(imgFile);
		run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(80), Units.toEMU(80));
		run.addBreak();
		run.addBreak();
		is.close();

		table = document.createTable(7, 8);

		table.setWidth(10700);

		int count = 0;

		List<XWPFTableRow> rowList = table.getRows();
		for (int i = 0; i < rowList.size(); i++) {
			XWPFTableRow infoTableRow = rowList.get(i);
			rowList.get(i).setHeight(1200);
			List<XWPFTableCell> cellList = infoTableRow.getTableCells();
			for (int j = 0; j < cellList.size(); j++) {
				cellParagraph = cellList.get(j).getParagraphArray(0);
				cellParagraph.setAlignment(ParagraphAlignment.CENTER);
				cellParagraphRun = cellParagraph.createRun();
				cellParagraph.setSpacingBefore(230);
				cellParagraphRun.setFontSize(12);
				cellParagraphRun.setBold(true);
				if (count < listData.size()) {
					cellParagraphRun.setText(listData.get(count).get("id"));
					cellParagraphRun.addBreak();
					cellParagraphRun.setText(listData.get(count).get("name"));
				}

				count++;
			}

		}

		count = 0;
		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setPageBreak(true);
	}

	public void setClassCheckTitle(XWPFTable table, String title, String img)
			throws InvalidFormatException, IOException {
		XWPFParagraph cellParagraph = null;

		XWPFRun cellParagraphRun = null;

		cellParagraph = getCellParagraph(table, 0, 0);
		cellParagraphRun = setTableWord(table, cellParagraph, 250, 250, 16, true);
		String imgFile = img;
		FileInputStream is = new FileInputStream(imgFile);
		cellParagraphRun.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(50), Units.toEMU(50));
		is.close();

		table.getRow(0).addNewTableCell();
		cellParagraph = getCellParagraph(table, 0, 1);
		cellParagraphRun = setTableWord(table, cellParagraph, 250, 600, 16, true);
		cellParagraphRun.setText(String.valueOf(title));
		table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);

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

		data.put("id", "A22010101");
		data.put("i", "A2325");
		data.put("name", "林亭");
		listData.add(0, data);

		data = new HashMap<>();
		data.put("id", "A22010102");
		data.put("i", "T1675");
		data.put("name", "陳棋閔");
		listData.add(1, data);

		data = new HashMap<>();
		data.put("id", "A22010103");
		data.put("i", "F2395");
		data.put("name", "詹惟涵");
		listData.add(2, data);

		data = new HashMap<>();
		data.put("id", "A22010104");
		data.put("i", "F1678");
		data.put("name", "蔡季霖");
		listData.add(3, data);

		data = new HashMap<>();
		data.put("id", "A22010105");
		data.put("i", "A1198");
		data.put("name", "李岱昇");
		listData.add(4, data);

		data = new HashMap<>();
		data.put("id", "A22010106");
		data.put("i", "P1945");
		data.put("name", "李祐瑋");
		listData.add(5, data);

		data = new HashMap<>();
		data.put("id", "A22010107");
		data.put("i", "T1955");
		data.put("name", "洪冰儒");
		listData.add(6, data);

		data = new HashMap<>();
		data.put("id", "A22010108");
		data.put("i", "K2835");
		data.put("name", "張庭毓");
		listData.add(7, data);

		data = new HashMap<>();
		data.put("id", "A22010109");
		data.put("i", "Q1935");
		data.put("name", "陳致嘉");
		listData.add(8, data);

		data = new HashMap<>();
		data.put("id", "A22010110");
		data.put("i", "A2325");
		data.put("name", "劉思源");
		listData.add(9, data);

		data = new HashMap<>();
		data.put("id", "A22010111");
		data.put("i", "T1675");
		data.put("name", "蔡秉勳");
		listData.add(10, data);

		data = new HashMap<>();
		data.put("id", "A220101012");
		data.put("i", "F2395");
		data.put("name", "謝承達");
		listData.add(11, data);

		data = new HashMap<>();
		data.put("id", "A22010113");
		data.put("i", "F1678");
		data.put("name", "鄭雅文");
		listData.add(12, data);

		data = new HashMap<>();
		data.put("id", "A22010114");
		data.put("i", "A1198");
		data.put("name", "李碩");
		listData.add(13, data);

		data = new HashMap<>();
		data.put("id", "A22010115");
		data.put("i", "P1945");
		data.put("name", "黃駿");
		listData.add(14, data);

		data = new HashMap<>();
		data.put("id", "A22010116");
		data.put("i", "T1955");
		data.put("name", "鄭智元");
		listData.add(15, data);

		data = new HashMap<>();
		data.put("id", "A22010117");
		data.put("i", "K2835");
		data.put("name", "吳致樑");
		listData.add(16, data);

		data = new HashMap<>();
		data.put("id", "A22010118");
		data.put("i", "Q1935");
		data.put("name", "唐偉鴻");
		listData.add(17, data);

		data = new HashMap<>();
		data.put("id", "A22010119");
		data.put("i", "A2325");
		data.put("name", "吳少夫");
		listData.add(18, data);

		data = new HashMap<>();
		data.put("id", "A22010120");
		data.put("i", "T1675");
		data.put("name", "林俞廷");
		listData.add(19, data);

		data = new HashMap<>();
		data.put("id", "A220101021");
		data.put("i", "F2395");
		data.put("name", "曹正");
		listData.add(20, data);

		data = new HashMap<>();
		data.put("id", "A22010122");
		data.put("i", "F1678");
		data.put("name", "李崇嘉");
		listData.add(21, data);

		data = new HashMap<>();
		data.put("id", "A22010123");
		data.put("i", "A1198");
		data.put("name", "陳柏諺");
		listData.add(22, data);

		data = new HashMap<>();
		data.put("id", "A22010124");
		data.put("i", "P1945");
		data.put("name", "賴韋廷");
		listData.add(23, data);

		data = new HashMap<>();
		data.put("id", "A22010125");
		data.put("i", "T1955");
		data.put("name", "詹雅如");
		listData.add(24, data);

		data = new HashMap<>();
		data.put("id", "A22010126");
		data.put("i", "K2835");
		data.put("name", "林旻葦");
		listData.add(25, data);

		data = new HashMap<>();
		data.put("id", "A22010127");
		data.put("i", "Q1935");
		data.put("name", "陳蓮華");
		listData.add(26, data);
		
		data = new HashMap<>();
		data.put("id", "A22010128");
		data.put("i", "A2325");
		data.put("name", "陳毅鴻");
		listData.add(27, data);

		data = new HashMap<>();
		data.put("id", "A22010129");
		data.put("i", "T1675");
		data.put("name", "林妮臻");
		listData.add(28, data);

		data = new HashMap<>();
		data.put("id", "A220101030");
		data.put("i", "F2395");
		data.put("name", "葉又瑛");
		listData.add(29, data);

		data = new HashMap<>();
		data.put("id", "A22010131");
		data.put("i", "F1678");
		data.put("name", "林麗卿");
		listData.add(30, data);

		data = new HashMap<>();
		data.put("id", "A22010132");
		data.put("i", "A1198");
		data.put("name", "王若竹");
		listData.add(31, data);

		data = new HashMap<>();
		data.put("id", "A22010133");
		data.put("i", "P1945");
		data.put("name", "吳雨樺");
		listData.add(32, data);

		data = new HashMap<>();
		data.put("id", "A22010134");
		data.put("i", "T1955");
		data.put("name", "魏子翔");
		listData.add(33, data);

		data = new HashMap<>();
		data.put("id", "A22010135");
		data.put("i", "K2835");
		data.put("name", "歐陽辰");
		listData.add(34, data);

		data = new HashMap<>();
		data.put("id", "A22010136");
		data.put("i", "Q1935");
		data.put("name", "郭柏廷");
		listData.add(35, data);

		return listData;
	}
}
