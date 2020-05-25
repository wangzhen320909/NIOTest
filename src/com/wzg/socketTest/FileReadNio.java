package com.wzg.socketTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用nio 实现按行读取文件(大文件)
 * @author Liuyang
 *
 */
public class FileReadNio {

	// 开启log4j日志
//	private static Logger log = Logger.getLogger(FileOperate.class);

	public static void main(String[] args) {
		List<String> list= FileReadNio.readFileNioByLine("E:\\testDocument\\ZYD001_20200520_1234567896.txt", ByteBuffer.allocate(3),60);
		
		for (String string : list) {
			System.out.println(string);
		}
	}

	/**
	 * 使用nio 实现按行读取文件(大文件)
	 * 
	 * @author Liuyang
	 * 
	 * @param filepath
	 *            文件路径
	 * @param rbuffer
	 *            每次缓冲字符区的大小
	 * @param maxnumline
	 *            最长行的字符个数
	 * @return 每行数据的集合 发生异常返回null
	 */
	public static List<String> readFileNioByLine(String filepath, ByteBuffer rbuffer, int maxnumline) {
		// 文件不存在或不是文件 返回null
		if (!new File(filepath).exists() || !new File(filepath).isFile()) {
			return null;
		}

		// 返回数据
		List<String> retStrList = new ArrayList<String>();
		try (FileInputStream fis = new FileInputStream(filepath);) {
			// 获取文件管道
			FileChannel fc = fis.getChannel();
			// 表示按行分割的标识 window:\r\n 13 10 linux/unix:\r 13 mac:\n 10
			byte changeLine = 10;
//			if (OS.isLinux()) {
//				changeLine = 13;
//			}
			// 换行符前面字符组合，可解决乱码
			ByteBuffer elseBuffer = ByteBuffer.allocate(maxnumline * 3);
			int lineNo  = 0;
			// 将缓冲区中数据读到字符数组中
			byte[] bs = null;
			// 从管道中循环读取数据
			while (fc.read(rbuffer) != -1) {
				// 根据读取到的字节数确定即将获取的字节数组大小
				bs = new byte[rbuffer.position()];
				rbuffer.rewind();
				// 相对读，从position位置读取一个byte[]
				rbuffer.get(bs);
				// 清楚缓冲区，供下次使用
				rbuffer.clear();
				for (byte b : bs) {
					// 遭遇换行符
					if (b == changeLine) {
						// 字符缓冲区位置，用于确定字节数组大小
						int byteSize = elseBuffer.position();
						// 确定出字节数组大小
						byte[] byteLine = new byte[byteSize];
						// position=0 准备读取数据
						elseBuffer.rewind();
						// 从position开始读取byteLine.length个字节
						elseBuffer.get(byteLine);
						// 如果是 剔除 /r 前的 /n 后转换为字符串
						String line = null;
						if(lineNo == 0) {
							line = new String(byteLine, 0, byteLine.length - 1);
						} else {
							line = new String(byteLine, 0, byteLine.length - 1);
						}
						// 添加到返回集合中
						retStrList.add(line);
						elseBuffer.clear();
						continue;
					}
					try {
						// 缓存住未匹配上的数据
						elseBuffer.put(b);
						// 由于提前确定的字节缓冲区太小导致错误
					} catch (BufferOverflowException e) {
//						log.error("the maxNumLine is too small:" + e.getMessage());
						return null;
					}

				}
			}
			// 循环完毕，处理剩余数据,注释同上
			if (elseBuffer.position() > 0) {
				int byteSize = elseBuffer.position();
				byte[] byteLine = new byte[byteSize];
				elseBuffer.rewind();
				elseBuffer.get(byteLine);
				String line = new String(byteLine, 0, byteLine.length);
				retStrList.add(line);
				elseBuffer.clear();
			}
		} catch (FileNotFoundException e) {
//			log.error("file not find:" + e.getMessage());
			return null;
		} catch (IOException e) {
//			log.error("read file by line error:" + e.getMessage());
			return null;
		}
		return retStrList;
	}
}