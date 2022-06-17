package com.olympicService.olympicAPI.Service.Impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.springframework.stereotype.Service;

import com.olympicService.olympicAPI.Service.SeatTagDownload;

@Service
public class SeatTagDownloadImpl implements SeatTagDownload {
	public int count = 0;

	public void test() throws Exception {
		this.count = 0;
		
		List<Map<String, String>> listData = testData();

		List<XWPFDocument> xwpfDocuments = new ArrayList<XWPFDocument>();

		for (int i = 0; i < 2; i++) {
			xwpfDocuments = setPageData(xwpfDocuments, listData);
		}

		XWPFDocument outDocument = createNewFile(xwpfDocuments);
		
		FileOutputStream outStream = null;
		int r = (int)(Math.random()*100);
		outStream = new FileOutputStream("C:\\workingSpace\\seatTagTest" + r + ".docx");
		outDocument.write(outStream);
		outStream.close();
	}

	public List<XWPFDocument> setPageData(List<XWPFDocument> xwpfDocuments, List<Map<String, String>> listData)
			throws IOException {
		InputStream is = new FileInputStream("C:\\workingSpace\\seatTag3.docx");
		XWPFDocument document = new XWPFDocument(is);


		List<XWPFTable> tables = document.getTables();
		
		for (XWPFTable tbl : tables) {
			for (int i = 0; i < tbl.getRows().size(); i++) {
				XWPFTableRow row = tbl.getRow(i);
				for (int j = 0; j < row.getTableCells().size(); j++) {
					if (this.count < listData.size()) {
						XWPFParagraph cellParagraph = tbl.getRow(i).getCell(j).getParagraphArray(0);
						cellParagraph.setAlignment(ParagraphAlignment.CENTER);
						XWPFRun cellParagraphRun = cellParagraph.createRun();
						cellParagraphRun.setFontSize(12);
						cellParagraphRun.setText(String.valueOf("2022年資訊奧林匹亞競賽"));
						cellParagraphRun.addBreak();
						
						cellParagraph = tbl.getRow(i).getCell(j).getParagraphArray(0);
						cellParagraphRun = cellParagraph.createRun();
						cellParagraphRun.addBreak();
						cellParagraphRun.setBold(true);
						cellParagraphRun.setText(listData.get(this.count).get("id"));
						cellParagraphRun.addBreak();
						cellParagraphRun.setText(listData.get(this.count).get("name"));
					}
					
					this.count++;
				}
			}
		}

		xwpfDocuments.add(document);
//		is.close();

		return xwpfDocuments;
	}

	public XWPFDocument createNewFile(List<XWPFDocument> xwpfDocuments) throws Exception {

		XWPFDocument outDocument = new XWPFDocument();

		for (int i = 0; i < xwpfDocuments.size(); i++) {
			if (i == 0) {
				outDocument = xwpfDocuments.get(0);
				continue;
			} else {
				outDocument = mergeWord(outDocument, xwpfDocuments.get(i));
			}
		}

		return outDocument;
	}

	public XWPFDocument mergeWord(XWPFDocument document, XWPFDocument doucDocument2) throws Exception {
		XWPFDocument src1Document = document;

		CTBody src1Body = src1Document.getDocument().getBody();

		XWPFDocument src2Document = doucDocument2;
		CTBody src2Body = src2Document.getDocument().getBody();

		XmlOptions optionsOuter = new XmlOptions();
		optionsOuter.setSaveOuter();

		String appendString = src2Body.xmlText(optionsOuter);
		String srcString = src1Body.xmlText();
		String prefix = srcString.substring(0, srcString.indexOf(">"));
		String mainPart = srcString.substring(srcString.indexOf(">"), srcString.lastIndexOf("<"));
		String sufix = srcString.substring(srcString.lastIndexOf("<"));
		String addPart = appendString.substring(appendString.indexOf(">"), appendString.lastIndexOf("<"));
		CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart + sufix);

		src1Body.set(makeBody);
//		src2Document.close();
		return src1Document;
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
