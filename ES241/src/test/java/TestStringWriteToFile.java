import org.junit.Test;

import java.io.*;

/**
 * Created by seentech on 2017/1/19.
 */
public class TestStringWriteToFile {

    @Test
    public void testStrToFile() {
        /**
         * 文件重命名
         * 1. oldName文件必须存在
         * 2. newName文件之前不能存在
         */
        File oldName = new File("e:/test/oldName.txt");
        File newName = new File("e:/test/newName.txt");
        if(oldName.renameTo(newName)) {
            System.out.println("oldName.renameTo(newName) success");
        } else {
            System.out.println("Error");
        }

        String filePath1 = "e:/test/string1.dat";
        WriteStringToFile1(filePath1);
        String filePath2 = "e:/test/string2.dat";
        WriteStringToFile2(filePath2);
        String filePath3 = "e:/test/string3.dat";
        WriteStringToFile3(filePath3);
        String filePath4 = "e:/test/string4.dat";
        WriteStringToFile4(filePath4);
        String filePath5 = "e:/test/string5.dat";
        WriteStringToFile5(filePath5);


    }

    /**
     * 追加写
     * @param filePath
     */
    public void WriteStringToFile1(String filePath) {
        try {
            File file = new File(filePath);
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println("http://www.docin.com/p-315288370.html");// 往文件里写入字符串
            ps.append("http://www.docin.com/p-315288370.html");// 在已有的基础上添加字符串
            ps.append("http://www.docin.com/p-315288370.html");// 在已有的基础上添加字符串
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 追加写
     * @param filePath
     */
    public void WriteStringToFile2(String filePath) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("在已有的基础上添加字符串");
            bw.write("abc\r\n ");// 往已有的文件上添加字符串
            bw.append("在已有的基础上添加字符串");
            bw.write("def\r\n ");
            bw.write("在已有的基础上添加字符串");
            bw.write("hijk ");
            bw.close();
            fw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 追加写
     * @param filePath
     */
    public void WriteStringToFile3(String filePath) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filePath));
            pw.println("abc ");
            pw.println("def ");
            pw.println("hef ");
            pw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 追加写
     * @param filePath
     */
    public void WriteStringToFile4(String filePath) {
        try {
            RandomAccessFile rf = new RandomAccessFile(filePath, "rw");
            rf.writeBytes("op\r\n");
            rf.writeBytes("app\r\n");
            rf.writeBytes("hijklllll");
            rf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 追加写
     * @param filePath
     */
    public void WriteStringToFile5(String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            String s = "http://www.docin.com/p-315288370.html";
            fos.write(s.getBytes());
            fos.write(s.getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
