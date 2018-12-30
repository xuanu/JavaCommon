package cn.zeffect.common.text;

public class TextUtils {
	public static boolean isEmpty(String msg) {
		return msg == null || msg.length() == 0;
	}

	public static boolean isBlank(String msg) {
		return msg == null || msg.trim().length() == 0;
	}
}
