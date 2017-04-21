package helloworld3;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/4
 * Time: 10:10
 */
public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            String data = (String) msg;
            System.out.println("服务器收到消息: " + data);
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer("你好客户端$".getBytes("utf-8")));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
