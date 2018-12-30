package cn.zeffect.common.file;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;

import cn.zeffect.common.text.TextUtils;

public class FileUtils {
	public static File maxPath() {
		File[] root = File.listRoots();
		File maxRoot = null;
		long tempRootAvailable = 0;
		final long A_GB = 1073741824;
		for (int k = 0; k < root.length; k++) {
			File tempRoot = root[k];
			long tempRootFree = tempRoot.getFreeSpace();
			if (tempRootFree < A_GB) {
				continue;
			}
			if (tempRootFree > tempRootAvailable) {
				tempRootAvailable = tempRootFree;
				maxRoot = tempRoot;
			}
		}
		return maxRoot;
	}

	public static String read(String filePath) {
		if (TextUtils.isEmpty(filePath))
			return "";
		File tempFile = new File(filePath);
		if (!tempFile.exists() || tempFile.isDirectory())
			return "";
		FileInputStream fileInput;
		try {
			fileInput = new FileInputStream(tempFile);
			return inputStream2String(fileInput);
		} catch (FileNotFoundException e) {
			return "";
		}

	}

	public static boolean write(String filePath, String content) {
		return write(filePath, content, false);
	}

	public static boolean write(String filePath, String content, boolean append) {
		try {
			if (TextUtils.isEmpty(filePath))
				return false;
			File tempFile = new File(filePath);
			if (!tempFile.exists()) {
				tempFile.getParentFile().mkdirs();
				tempFile.createNewFile();
			}
			if (tempFile.isDirectory())
				return false;
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(filePath, append), "UTF-8"));
			writer.write(content);
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	private static String inputStream2String(InputStream inputs) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int i = inputs.read();
			while (i != -1) {
				baos.write(i);
				i = inputs.read();
			}
			return baos.toString();
		} catch (IOException e) {
			return "";
		} finally {
			if (inputs != null) {
				try {
					inputs.close();
				} catch (IOException e) {
				}
				inputs = null;
			}
		}
	}

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return getFileMD5(in);
	}

	public static String getFileMD5(InputStream in) {
		if (in == null) {
			return null;
		}
		MessageDigest digest = null;
		byte buffer[] = new byte[8192];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			while ((len = in.read(buffer)) != -1) {
				digest.update(buffer, 0, len);
			}
			return byteArrayToHexString(digest.digest());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static final long A_GB = 1073741824;
	public static final long A_MB = 1048576;
	public static final int A_KB = 1024;

	/**
	 * 格式化Byte数值
	 *
	 * @param space Byte数值
	 * @return 格式化后的字符串
	 */
	public static String fmtSpace(long space) {
		if (space <= 0) {
			return "0";
		}
		double gbValue = (double) space / A_GB;
		if (gbValue >= 1) {
			return String.format("%.2fGB", gbValue);
		} else {
			double mbValue = (double) space / A_MB;
			if (mbValue >= 1) {
				return String.format("%.2fMB", mbValue);
			} else {
				final double kbValue = space / A_KB;
				return String.format("%.2fKB", kbValue);
			}
		}
	}
}
