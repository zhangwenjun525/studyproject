package mayikt.channel;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author zhangwj
 * @date 2018/9/2 22:51
 */
public class ChannelTest {

    //非直接缓存区读写
    @Test
    public void fileChannelTest() throws IOException {
        FileInputStream fis = new FileInputStream("e:\\042-技术部-章文君.jpg");
        FileOutputStream fos = new FileOutputStream("e:\\042-技术部-章文君-cpy.jpg");

        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (fisChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            fosChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        fisChannel.close();
        fosChannel.close();
        fos.close();
        fis.close();

    }

    //直接缓冲区读写
    @Test
    public void fileChannelTest2() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("e:\\042-技术部-章文君.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("e:\\042-技术部-章文君_cpy.jpg"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        MappedByteBuffer inBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        byte[] bytes = new byte[inBuffer.limit()];
        inBuffer.get(bytes);
        outBuffer.put(bytes);

        inChannel.close();
        outChannel.close();
    }


    //分散读取 聚集写入
    @Test
    public void fileChannelTest3() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("e:\\私钥.txt", "rw");

        FileChannel channel = randomAccessFile.getChannel();

        ByteBuffer buffer1 = ByteBuffer.allocate(500);
        ByteBuffer buffer2 = ByteBuffer.allocate(2048);

        ByteBuffer[] buffers = {buffer1, buffer2};

        channel.read(buffers);

        for (ByteBuffer byteBuffer : buffers) {
            byteBuffer.flip();
        }

        System.out.println(new String(buffers[0].array(), 0, buffers[0].limit()));

        RandomAccessFile randomAccessFile2 = new RandomAccessFile("e:\\私钥2.txt", "rw");

        FileChannel channel2 = randomAccessFile2.getChannel();
        channel2.write(buffers);

        randomAccessFile2.close();
        randomAccessFile.close();
    }

    @Test
    public void charsetTest() throws CharacterCodingException {
        Charset charset = Charset.forName("GBK");
        CharsetEncoder ce = charset.newEncoder();
        CharsetDecoder cd = charset.newDecoder();

        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("蚂蚁课堂牛逼...");
        charBuffer.flip();

        ByteBuffer byteBuffer = ce.encode(charBuffer);
        for (int i = 0; i < byteBuffer.limit(); ++i) {
            System.out.println(byteBuffer.get());
        }

        byteBuffer.flip();
        CharBuffer decode = cd.decode(byteBuffer);
        System.out.println(decode.toString());


    }


}
