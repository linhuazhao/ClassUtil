package CopyUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 方法名：	CopyAndWrite
 * 编写者: 	林华钊
 * 编写日期: 	2017-3-14
 * 功能说明：     替换文件用的工具类
 *
 * ********************  修改日志  **********************************
 * 修改人：             修改日期：
 * 修改内容：
 **/
public class CopyAndWrite {

	private static String targetPath;

	private static List<String> resourcePath = new ArrayList<String>();

	private static Map<String, String> paths = new HashMap<String, String>();

	private static void init() {
		//替换文件夹
		targetPath = "C:/Users/HY112/Desktop/配置文件申请/文件替换/EMMS-替换文件申请单2017-4-12_计量_林华钊";
		//文件源目录
		resourcePath.add("E:/PROJECT_XJTFSRC/15Source/后台/JAVA/XJTF_SVR_ZH");
		resourcePath.add("E:/PROJECT_XJTFSRC/15Source/后台/JAVA/XJTF_SVR_JL");
		resourcePath.add("E:/PROJECT_XJTFSRC/15Source/前台/XJTF_WEB_ZH");
		resourcePath.add("E:/PROJECT_XJTFSRC/15Source/前台/XJTF_WEB_JL");
	}

	public static void main(String[] args) throws Exception {
		init();
		if (targetPath == null || resourcePath == null || resourcePath.size() == 0) {
			return;
		}
		File file;
		for (String rPath : resourcePath) {
			file = new File(rPath);
			find(file);
		}
		file = new File(targetPath);
		File fileWrite = new File(targetPath + "/文件路径.txt");
		if (!fileWrite.exists()) {
			fileWrite.createNewFile();
		}
		FileWriter out = new FileWriter(fileWrite);
		String[] list = file.list();
		for (String fileName : list) {
			fileName = fileName.replace(".java", ".class");
			String copyFilePath = paths.get(fileName);
			if (fileName.contains(".class")) {
				File copyFile = new File(copyFilePath);
				File targetFile = new File(targetPath + "/" + fileName);
				targetFile.createNewFile();
				InputStream in = new FileInputStream(copyFile);
				OutputStream output = new FileOutputStream(targetFile);
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = in.read(buffer)) != -1) {
					output.write(buffer, 0, len);
				}
				output.close();
				in.close();
			}
			if (copyFilePath != null) {
				copyFilePath = copyFilePath.substring(copyFilePath.lastIndexOf("/XJTF_"));
				out.write(copyFilePath + "\r\n");
				out.write("\r\n");
			}
		}
		out.close();
	}

	public static void find(File f) {
		File[] listFiles = f.listFiles();
		for (File file : listFiles) {
			if (file.isDirectory()) {
				find(file);
			} else {
				String path = file.getPath();
				paths.put(file.getName(), path.replaceAll("\\\\", "/"));
			}
		}
	}
}
