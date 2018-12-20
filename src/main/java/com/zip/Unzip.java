package com.zip;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

//使用org.apache.tools.zip这个就不会中文乱码
/**
 * Created by liyongguan on 2017/12/21.
 */
public class Unzip {

    static String separator = File.separator;
    static String zipPath = "E:"+separator+"Test1"+separator+"Test.zip";//需要解压的压缩文件
    static String outPath = "E:"+separator+"Test2";//解压完成后保存路径,记得"\\"结尾哈
    static  int count = 0;
    public static  void main(String args[]) throws Exception{
        ZipFile zipFile = new ZipFile(zipPath,"GBK");//压缩文件的实列,并设置编码
        //获取压缩文中的所以项
        Enumeration<ZipEntry> enumeration = zipFile.getEntries();
        System.out.println(separator);
        for(; enumeration.hasMoreElements();)
        {
            ZipEntry zipEntry = enumeration.nextElement();//获取元素
            System.out.println();
            System.out.println(zipEntry.getName().replace("/",separator).endsWith(separator));
            //排除空文件夹
            if(zipEntry.getName().replace("/",separator).endsWith(separator)) {
                //如果为空文件夹，则创建该文件夹
                new File(outPath+zipEntry.getName()).mkdirs();
                System.out.println("create file");
            } else {
                System.out.println("正在解压文件:"+zipEntry.getName().replace("/",separator));//打印输出信息
                //创建解压目录
                String fileN = outPath+zipEntry.getName().replace("/",separator);
                File f = new File(fileN);

                OutputStream os = new FileOutputStream(fileN);//创建解压后的文件
                BufferedOutputStream bos = new BufferedOutputStream(os);//带缓的写出流
                InputStream is = zipFile.getInputStream(zipEntry);//读取元素
                BufferedInputStream bis = new BufferedInputStream(is);//读取流的缓存流
                CheckedInputStream cos = new CheckedInputStream(bis, new CRC32());//检查读取流，采用CRC32算法，保证文件的一致性
                byte [] b = new byte[1024];//字节数组，每次读取1024个字节
                //循环读取压缩文件的值
                while(cos.read(b)!=-1)
                {
                    bos.write(b);//写入到新文件
                }
                cos.close();
                bis.close();
                is.close();
                bos.close();
                os.close();
            }
        }
        System.out.println("解压完成");
        zipFile.close();

    }
}
