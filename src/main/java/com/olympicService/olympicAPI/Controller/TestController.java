package com.olympicService.olympicAPI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olympicService.olympicAPI.DAO.Repository.SchoolUsersRepository;
import com.olympicService.olympicAPI.Service.Impl.AES256ServiceImpl;
import com.olympicService.olympicAPI.Service.Impl.ClassListDownloadImpl;
import com.olympicService.olympicAPI.Service.Impl.ExamTicketDownloadImpl;
import com.olympicService.olympicAPI.Service.Impl.JWTServiceImpl;
import com.olympicService.olympicAPI.Service.Impl.SchoolUsersServiceImpl;

@RestController
public class TestController {
	@Autowired
	private AES256ServiceImpl AES256ServiceImpl;

	@Autowired
	private JWTServiceImpl JWTService;

	private String secret = "-555018516626007488A";

	@Autowired
	private SchoolUsersRepository SchoolUsersRepository;

	@Autowired
	private SchoolUsersServiceImpl SchoolUsersServiceImpl;

	@Autowired
	private ExamTicketDownloadImpl ExamTicketDownloadImpl;

	@Autowired
	private ClassListDownloadImpl ClassListDownloadImpl;

	@GetMapping("/test")
	public String test() throws Exception {
//		AES256ServiceImpl.setKey("uBdUx82vPHkDKb284d7NkjFoNcKWBuka", "c558Gq0YQK2QUlMc");
//		String a = AES256ServiceImpl.encode("(02)22987456");
//		System.out.println(a);
//		long timeout = 1000 * 60 * 30;
//		
//		String a = JWTService.generateToken(timeout, "olympic", secret);
//		
//		System.out.println(a);
//		System.out.println(JWTService.validateToken(a, "olympic", secret));

//		JSONArray signupColumns = SchoolUsersServiceImpl.getSchoolUsers();
//		System.out.println(signupColumns);

//		Map<String, Object> data = new HashMap<>();//要插入的数据
//        data.put("admitCart", "TOI2022AB01003");
//        data.put("id", "A1356");
//        data.put("name", "王曉明");
//        data.put("area", "台師大");
//        data.put("classroom", "B101");
//        data.put("address", "台北市大安區");
//        data.put("date", "2022/04/30 09:30");
//
//        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//
//        PdfReader pdfReader = new PdfReader("C:\\workingSpace\\tempalte.pdf");
//        
//        File file = new File("C:\\workingSpace\\test.pdf");
//
//        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(file));
//        AcroFields form = pdfStamper.getAcroFields();
//        form.addSubstitutionFont(baseFont);
// 
//
//        for (String key : data.keySet()) {
//            String value = data.get(key).toString();
//
//            form.setField(key, value);
//        }
//        
//        pdfStamper.setFormFlattening(true);
//        pdfStamper.close();

		ExamTicketDownloadImpl.test();

		ClassListDownloadImpl.test();

		return "OK";
	}
}
