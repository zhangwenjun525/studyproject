package hello.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            //do something msg
            ByteBuf buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            System.out.println("server: " + new String(bytes, "utf-8"));

            //写给客户端
            //addListener(ChannelFutureListener.CLOSE)代表短连接,即一次通信就断开连接
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer("888".getBytes())).addListener(ChannelFutureListener.CLOSE);
        } finally {
            //释放buf
            ReferenceCountUtil.release(msg);
        }
    }
}
