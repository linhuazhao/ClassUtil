package ExcelUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 方法名：	wbjc
 * 编写者: 	林华钊
 * 编写日期: 	2017-2-14
 * 功能说明：     外部集成时常用输入输出语句
 *
 * ********************  修改日志  **********************************
 * 修改人：             修改日期：
 * 修改内容：
 **/
public class wbjc {

	//读取EXCEL文件的路径,注意excel文件后缀只能为.xls
	public static final String path = "d:/wbjc.xls";

	public static void main(String[] args) throws Exception {
		//		createXsd(true);
		createSet(false);
		//		createGet();
		//		createValt();
	}

	/**
	 * 生成参数校验语句
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws BiffException
	 */
	private static void createValt() throws FileNotFoundException, IOException, BiffException {
		Workbook readwb = null;
		InputStream in = new FileInputStream(path);
		readwb = Workbook.getWorkbook(in);
		Sheet sh = readwb.getSheet(0);
		int rows = sh.getRows();
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < rows; i++) {
			String bmsx = sh.getCell(0, i).getContents();
			String sxmc = sh.getCell(1, i).getContents();
			s.append("if (StringUtils.isEmpty(uploadVerificationInfoInType.get" + bmsx + "())) {        \r\n");
			s.append("	throw new CsgSoapFault(\"SERVER\", \"输入的" + sxmc + "为空!\", \"\", \"\");         \r\n");
			s.append("}                                                                          \r\n");
		}
		System.out.println(s.toString());
	}

	/**
	 * 输出打印参数语句
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws BiffException
	 */
	private static void createGet() throws FileNotFoundException, IOException, BiffException {
		Workbook readwb = null;
		InputStream in = new FileInputStream(path);
		readwb = Workbook.getWorkbook(in);
		Sheet sh = readwb.getSheet(0);
		int rows = sh.getRows();
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < rows; i++) {
			String bmsx = sh.getCell(0, i).getContents();
			String sxmc = sh.getCell(1, i).getContents();
			s.append("System.out.println(\"" + sxmc + ":\"+out.get" + bmsx + "());\r\n");
		}
		System.out.println(s.toString());
	}

	/**
	 * 创建数据set值语句
	 * @param flag 是否加双引号
	 */
	private static void createSet(boolean flag) throws FileNotFoundException, IOException, BiffException {
		Workbook readwb = null;
		InputStream in = new FileInputStream(path);
		readwb = Workbook.getWorkbook(in);
		Sheet sh = readwb.getSheet(0);
		int rows = sh.getRows();
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < rows; i++) {
			String bmsx = sh.getCell(0, i).getContents();
			String sxmc = sh.getCell(1, i).getContents();
			if (flag) {
				s.append("value.set" + bmsx + "(\"1\");//" + sxmc + "\r\n");
			} else {
				s.append("value.set" + bmsx + "();//" + sxmc + "\r\n");
			}
		}
		System.out.println(s.toString());
	}

	/**
	 * 创建XSD文件输入输出参数语句
	 * @param flag true则自动读取excel中第三列作为数据格式，false则默认为string格式
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws BiffException
	 */
	private static void createXsd(boolean flag) throws FileNotFoundException, IOException, BiffException {
		Workbook readwb = null;
		InputStream in = new FileInputStream(path);
		readwb = Workbook.getWorkbook(in);
		Sheet sh = readwb.getSheet(0);
		int rows = sh.getRows();
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < rows; i++) {
			String bmsx = sh.getCell(0, i).getContents();
			String sxmc = sh.getCell(1, i).getContents();
			String sxlx = "";
			if (flag) {
				sxlx = sh.getCell(2, i).getContents().toLowerCase();
			} else {
				sxlx = "string";
			}
			s.append("    		<xsd:element name=\"" + bmsx + "\" type=\"xsd:" + sxlx + "\">\r\n");
			s.append("    		    <xsd:annotation>\r\n");
			s.append("    		        <xsd:documentation>" + sxmc + "</xsd:documentation>\r\n");
			s.append("    		    </xsd:annotation>\r\n");
			s.append("    		</xsd:element>\r\n");
		}
		System.out.println(s.toString());
		System.out.println(rows);
	}
}
