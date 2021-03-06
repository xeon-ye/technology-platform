package com.pcitc.service.system.impl;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.pcitc.base.util.FileUtil;
import com.pcitc.service.system.FileService;

/**
 * @author:Administrator
 * @date:2019/4/28
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Override
    public String getGoogleUrl(boolean isCn, String longitude, String latitude) {
        String strReturn = "";
        try {
            strReturn = FileUtil.getGoogleUrl(isCn, longitude, latitude);
        } catch (Exception e) {
            e.printStackTrace();
            return strReturn;
        } finally {
            return strReturn;
        }
    }

    @Override
    public void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
            }
        }
    }

    @Override
    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
            return bytesum;
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean copyInputStreamToFile(InputStream inStream, File file) {
        boolean flag = false;
        try {
            int bytesum = 0;
            int byteread = 0;
            byte[] buffer = new byte[1024];
            FileOutputStream fs = new FileOutputStream(file);
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

   

    @Override
    public String getMD5ByInputStream(InputStream inStream) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            while ((len = inStream.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            inStream.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bi.toString(16);
        }
    }

    @Override
    public String getMD5ByFilePath(String path) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bi.toString(16);
        }
    }

    @Override
    public boolean mkdir(String dir) {
        boolean retVal = false;
        String dirTemp = dir;
        File dirPath = new File(dirTemp);
        if (!dirPath.exists()) {
            retVal = dirPath.mkdirs();
        }
        return retVal;

    }

    @Override
    public byte[] getFileBytes(String strFilePath) {
        // 定义一个输入流对象
        FileInputStream fis = null;
        byte[] buffer = null;
        // 定义一个存放输入流的缓冲对象
        BufferedInputStream bis = null;
        // 定义一个输出流，相当StringBuffer（），会根据读取数据的大小，调整byte的数组长度
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 把文件路径和文件名作为参数 告诉读取流
            fis = new FileInputStream(strFilePath);
            // 把文件读取流对象传递给缓存读取流对象
            bis = new BufferedInputStream(fis);
            // 获得缓存读取流开始的位置
            int len = bis.read();
            // 定义一个容量来盛放数据
            byte[] buf = new byte[1024];
            while ((len = bis.read(buf)) != -1) {
                // 如果有数据的话，就把数据添加到输出流
                //这里直接用字符串StringBuffer的append方法也可以接收
                baos.write(buf, 0, len);
//                System.out.println(new String(baos.toByteArray(), "utf-8"));
            }
            // 把文件输出流的数据，放到字节数组
            buffer = baos.toByteArray();
            // 打印输出
//            System.out.println(new String(buffer, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭所有的流
                baos.close();
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return buffer;
    }

	@Override
	public String[] getFileLatAndLong(String filePath, String[] strType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delFile(String filePathAndName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isImage(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String generateZipFileZdy(String basePath, String zipFileName, String... fileNames) {
		// TODO Auto-generated method stub
		return null;
	}
}
