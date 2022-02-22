package week1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader{
	public static void main(String args[]) throws Exception {
		String classUrl = System.getProperty("user.dir") + "/src/week1/Hello.xlass";
		Class<?> clazz = new HelloClassLoader().loadClass(classUrl);
		Object object = clazz.getDeclaredConstructor().newInstance();
		Method method = clazz.getMethod("hello");
		method.invoke(object);
		
	}
	
	//≤È’“class
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		File file = new File(name);
		byte[] dataByte = new byte[(int) file.length()];
		FileInputStream fio = null;
		
		try {
			fio = new FileInputStream(file);
			fio.read(dataByte);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fio.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Ω‚¬Î
		for (int i = 0; i < dataByte.length; i++) {
			dataByte[i] = (byte) (255 - dataByte[i]);
		}
		
		return defineClass(dataByte, 0 , dataByte.length);
	}
}
