package com.olympicService.olympicAPI.Service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.olympicService.olympicAPI.Service.ExamTicketDownload;

@Service
public class ExamTicketDownloadImpl implements ExamTicketDownload {
//	private static final float IAMGE_HEIGHT = 150f;
//	private static final float IAMGE_WIDTH = 150f;
	private static final float IAMGE_HEIGHT = 80f;
	private static final float IAMGE_WIDTH = 80f;

	public void test() {
		File file = new File("C:\\workingSpace\\tempalte.pdf");
		if (file != null && file.exists()) {
			FileInputStream is = null;
			try {
				is = new FileInputStream(file);
				long length = file.length();
				byte[] fileBytes = new byte[(int) length];
				is.read(fileBytes);

				File file1 = updatePDF(fileBytes);
				if (file1 != null && file1.exists()) {
					System.out.println("修改pdf完成！");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public File updatePDF(byte[] tpeHtcxyw) {
		PdfStamper stamper = null;
		try {
			PdfReader reader = new PdfReader(tpeHtcxyw);

			String filePath = "C://workingSpace/";

			File directory = new File(filePath);

			if (!directory.exists()) {
				directory.mkdirs();
			}
			String filename = "_after.pdf";

			File file = new File(filePath + filename);

			BaseFont baseFont = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.EMBEDDED);

			Font font = new Font(baseFont);
			stamper = new PdfStamper(reader, new FileOutputStream(file));

			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				PdfContentByte over = stamper.getOverContent(i);
				ColumnText columnText = new ColumnText(over);
				if (i == 1) {
					writeData(tpeHtcxyw, columnText, font, "2022年中華民國資訊奧林匹亞競賽", "考試項目");
					writeData(tpeHtcxyw, columnText, font, "桃竹區–桃園市立武陵高級中等學校", "考區");
					writeData(tpeHtcxyw, columnText, font, "330桃園市桃園區中山路889號", "校址");
					writeData(tpeHtcxyw, columnText, font, "2021年11月6日(六) 上午09:30~11:30", "時間");
					writeData(tpeHtcxyw, columnText, font, "A21020135", "准考證號碼");
					writeData(tpeHtcxyw, columnText, font, "第01試場 / 第35座位", "試場及座位");
					writeData(tpeHtcxyw, columnText, font, "李亞明", "考生姓名");
					writeData(tpeHtcxyw, columnText, font, "H1234", "辨識碼");

				}
			}

			waterMarkByImg(reader, stamper, "C://workingSpace/logo.png");
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (stamper != null) {
				try {
					stamper.close();
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void writeData(byte[] tpeHtcxyw, ColumnText columnText, Font font, String data, String key)
			throws FileNotFoundException, DocumentException, IOException {
		float[] po = getCoordinate(key, tpeHtcxyw);

		if (po[0] == 1.00f) {
			columnText.setSimpleColumn((float) 170.332, po[2] - 9f, 500, 0);

			Paragraph elements = new Paragraph(0, new Chunk(new Chunk(data)));

			elements.setFont(font);
			columnText.addElement(elements);
			columnText.go();
		}
	}

	public void waterMarkByImg(PdfReader reader, PdfStamper stamper, String logoPath) throws Exception {
		int pageSize = reader.getNumberOfPages();

		try {
			for (int i = 1; i <= pageSize; i++) {
				PdfContentByte under = stamper.getUnderContent(i);
				PdfGState gs = new PdfGState();
				Image img = Image.getInstance(logoPath);
				gs.setFillOpacity(0.5f);
				under.setGState(gs);
				img.scaleAbsolute(IAMGE_WIDTH, IAMGE_HEIGHT);
//				img.setAbsolutePosition(400, 430);
				img.setAbsolutePosition(50, 725);
				under.addImage(img);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			stamper.close();
			reader.close();
		}
	}

	public void waterMarkByWord(PdfStamper stamper, BaseFont baseFont, int page, String key) {
		PdfContentByte under;
		Rectangle pageRect = null;
		pageRect = stamper.getReader().getPageSizeWithRotation(page);
		float x = pageRect.getWidth() / 3 - 50;
		float y = pageRect.getHeight() / 2 - 20;

		under = stamper.getOverContent(page);
		under.saveState();
		PdfGState gs = new PdfGState();
		gs.setFillOpacity(0.3f);
		under.setGState(gs);
		under.beginText();
		under.setFontAndSize(baseFont, 45);
		under.setColorFill(BaseColor.RED);
		under.showTextAligned(Element.ALIGN_LEFT, key, x, y, 45);
		under.endText();
		under.setLineWidth(1f);
		under.stroke();
	}

	public float[] getCoordinate(String signKey, byte[] pdf) {
		PdfReader reader = null;
		final float[] po = new float[3];
		try {
			reader = new PdfReader(pdf);
			int pageNum = reader.getNumberOfPages();
			final String signKeyWord = signKey;
			for (int page = 1; page <= pageNum; page++) {
				PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(reader);
				pdfReaderContentParser.processContent(page, new RenderListener() {
					StringBuilder sb = new StringBuilder("");
					int maxLength = signKeyWord.length();

					@Override
					public void renderText(TextRenderInfo textRenderInfo) {

						boolean isKeywordChunk = textRenderInfo.getText().length() == maxLength;
						if (isKeywordChunk) {
							sb.delete(0, sb.length());
							sb.append(textRenderInfo.getText());
						} else {

							sb.append(textRenderInfo.getText());

							if (sb.length() > maxLength) {
								sb.delete(0, sb.length() - maxLength);
							}
						}

						if (signKeyWord.equals(sb.toString())) {
							Rectangle2D.Float baseFloat = textRenderInfo.getBaseline().getBoundingRectange();
							Rectangle2D.Float ascentFloat = textRenderInfo.getAscentLine().getBoundingRectange();
							float centreX;
							float centreY;
							if (isKeywordChunk) {
								centreX = baseFloat.x + 5 * ascentFloat.width / 6;
								centreY = baseFloat.y + (5 * (ascentFloat.y - baseFloat.y) / 6);
							} else {
								centreX = baseFloat.x + ascentFloat.width - (5 * maxLength * ascentFloat.width / 6);
								centreY = baseFloat.y + (5 * (ascentFloat.y - baseFloat.y) / 6);
							}
							po[0] = 1.00f;
							po[1] = centreX + 3;
							po[2] = centreY;
							sb.delete(0, sb.length());
						}
					}

					@Override
					public void renderImage(ImageRenderInfo arg0) {
					}

					@Override
					public void endTextBlock() {
					}

					@Override
					public void beginTextBlock() {
					}
				});
			}
			if (po[0] == 1.00f) {
				return po;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return null;
	}
}
