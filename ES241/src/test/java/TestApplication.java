import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;


/**
 * Created by seentech on 2017/1/19.
 */
public class TestApplication {
    // data chunk be written per time
    private static final int DATA_CHUNK = 128 * 1024 * 1024;

    // total data size is 2G
    private static final long LEN = 2L * 1024 * 1024 * 1024L;


    @Test
    public void testWriteFile() throws IOException {

        long startTime = 0;
        long endTime = 0;
        System.out.println("writeWithFileChannel Start Time :" + System.currentTimeMillis());
        writeWithFileChannel();

        System.out.println("writeWithFileChannel End Time :" + System.currentTimeMillis());


        System.out.println("writeWithTransferTo Start Time :" + System.currentTimeMillis());
        writeWithTransferTo();
        System.out.println("writeWithTransferTo End Time :" + System.currentTimeMillis());

    }

    private void writeWithTransferTo() throws IOException {
        File file = new File("e:/test/transfer.dat");
        if (file.exists()) {
            file.delete();
        }

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel toFileChannel = raf.getChannel();

        long len = LEN;
        byte[] data = null;
        ByteArrayInputStream bais = null;
        ReadableByteChannel fromByteChannel = null;
        long position = 0;
        int dataChunk = DATA_CHUNK / (1024 * 1024);
        while (len >= DATA_CHUNK) {
            System.out.println("write a data chunk: " + dataChunk + "MB");

            data = new byte[DATA_CHUNK];
            bais = new ByteArrayInputStream(data);
            fromByteChannel = Channels.newChannel(bais);

            long count = DATA_CHUNK;
            toFileChannel.transferFrom(fromByteChannel, position, count);

            data = null;
            position += DATA_CHUNK;
            len -= DATA_CHUNK;
        }

        if (len > 0) {
            System.out.println("write rest data chunk: " + len + "B");

            data = new byte[(int) len];
            bais = new ByteArrayInputStream(data);
            fromByteChannel = Channels.newChannel(bais);

            long count = len;
            toFileChannel.transferFrom(fromByteChannel, position, count);
        }

        data = null;
        toFileChannel.close();
        fromByteChannel.close();
    }

    private void writeWithFileChannel() throws IOException {
        File file = new File("e:/test/fc.dat");
        if (file.exists()) {
            file.delete();
        }

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = raf.getChannel();

        byte[] data = null;
        long len = LEN;
        ByteBuffer buf = ByteBuffer.allocate(DATA_CHUNK);
        int dataChunk = DATA_CHUNK / (1024 * 1024);

        while(len >= DATA_CHUNK){
            System.out.println("write a data chunk: " + dataChunk + "MB");

            buf.clear();
            data = new byte[DATA_CHUNK];

            for(int i = 0; i < DATA_CHUNK; i++){
                buf.put(data[i]);
            }

            data = null;

            buf.flip();
            fileChannel.write(buf);
            fileChannel.force(true);

            len -= DATA_CHUNK;
        }

        if(len > 0){
            System.out.println("write rest data chunk: " + len + "B");

            buf = ByteBuffer.allocateDirect((int) len);

            data = new byte[(int) len];

            for( int i = 0; i < len; i++){
                buf.put(data[i]);
            }

            buf.flip();
            fileChannel.write(buf);
            fileChannel.force(true);
            data = null;
        }

        fileChannel.close();
        raf.close();

    }



}
