package cn.zeffect.common.http;

import com.zhy.http.okhttp.OkHttpUtils;

import cn.zeffect.common.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/3.
 */

public class HttpUtils {

	/**
	 * 同步下载文件
	 **/
	public static boolean downFileSync(String url, File saveFile) {
		System.out.println("---------------------正在下载文件：" + url);
		if (TextUtils.isEmpty(url))
			return false;
		if (!url.startsWith("http"))
			return false;
		try {
			if (saveFile.isDirectory()) {
				saveFile.delete();
			}
			if (saveFile.exists()) {
				return true;
			}
			saveFile.getParentFile().mkdirs();
			Response response = OkHttpUtils.get().url(url).build().connTimeOut(30 * 1000).readTimeOut(2 * 60 * 1000)
					.writeTimeOut(2 * 60 * 1000).execute();
			boolean downSuccess = saveFile(response, saveFile);
			if (!downSuccess) {
				JSONObject dataJson = new JSONObject();
				dataJson.put("file", saveFile.getAbsolutePath());
				dataJson.put("url", url);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 保存下载流
	 *
	 * @param response
	 * @param saveFile
	 * @return
	 * @throws IOException
	 */
	private static boolean saveFile(Response response, File saveFile) throws IOException {
		if (response == null || !response.isSuccessful()) {
			response.body().close();
			return false;
		}
		if (saveFile.isDirectory())
			saveFile.delete();
		if (!saveFile.exists()) {
			saveFile.getParentFile().mkdirs();
			saveFile.createNewFile();
		}
		InputStream is = null;
		byte[] buf = new byte[2048];
		int len = 0;
		FileOutputStream fos = null;
		try {
			is = response.body().byteStream();
//			final long total = response.body().contentLength();
			// System.out.println("文件大小：" + fmtSpace(total) + ",保存路径：" +
			// saveFile.getAbsolutePath());
			long sum = 0;
			fos = new FileOutputStream(saveFile);
			while ((len = is.read(buf)) != -1) {
				sum += len;
				fos.write(buf, 0, len);
			}
			fos.flush();
			response.body().close();
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				response.body().close();
				if (is != null)
					is.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
			}
		}
	}



}
