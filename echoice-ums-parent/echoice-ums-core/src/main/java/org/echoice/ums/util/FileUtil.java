package org.echoice.ums.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class FileUtil {

	public static void saveFile(String filePath, InputStream in) throws IOException {
		if(StringUtils.isNoneBlank(filePath)){
			makeDirectory(filePath);
			OutputStream out = new FileOutputStream(filePath);
			IOUtils.copy(in, out);
			in.close();
			out.close();
		}
	}
	
	private static boolean makeDirectory(File file) {
		File parent = file.getParentFile();
		if (parent != null) {
			return parent.mkdirs();
		}
		return false;
	}
	
	public static boolean makeDirectory(String fileName) {
		File file = new File(fileName);
		return makeDirectory(file);
	}
}
