package sdfs.datanode;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

/**
 * Created by HaoqiWu on 10/13/17.
 * 读取和写入文件的接口
 */
public class DataNode implements IDataNode{
    @Override
    public int read(int blockNumber, int offset, int size, byte[] b) throws IndexOutOfBoundsException, FileNotFoundException, IOException {
        //判断是否Index异常
        if ((offset < 0) || (offset+size > blockSize) || (b.length<size)){
            throw new IndexOutOfBoundsException();
        }
        System.out.println("Index正常");

        //判断文件是否存在
        String filePath = "src/data/" + blockNumber +".block";
        File file = new File(filePath);
        if (!file.exists()){
            throw new FileNotFoundException();
        }
        System.out.println("文件存在");

        //读取文件输入
        int read;
        FileInputStream fis = new FileInputStream(file);
        read = fis.read(b,offset,size);

        return read;
    }

    @Override
    public void write(int blockNumber, int offset, int size, byte[] b) throws IndexOutOfBoundsException, FileAlreadyExistsException, IOException {
        //判断是否Index异常
        if ((offset < 0) || (offset+size > blockSize) || (b.length<size)){
            throw new IndexOutOfBoundsException();
        }
        System.out.println("Index正常");

        //判断文件是否存在
        String filePath = "src/data/" + blockNumber +".block";
        File file = new File(filePath);
        if (file.exists()){
            throw new FileAlreadyExistsException(filePath);
        }
        System.out.println("文件不存在");

        //输出文件内容
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(b,offset,size);
    }
}
