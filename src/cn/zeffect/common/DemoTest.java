package cn.zeffect.common;

import cn.zeffect.common.encrypt.MD5Utils;
import cn.zeffect.common.file.FileUtils;

/**
 * 工具测试类
 * 
 * @author 62541
 *
 */
public class DemoTest {
	public static void main(String[] args) {
		System.out.println("FileUtils.maxPath():" + FileUtils.maxPath());
		System.out.println("FileUtils.fmtSpace(long space):" + FileUtils.fmtSpace(FileUtils.maxPath().getTotalSpace()));
		System.out.println("MD5Utils:123456 = " + MD5Utils.md5("123456"));
	}
}
