package serial;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/4
 * Time: 12:33
 */
public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Req req = (Req) msg;
            System.out.println("Server : " + req.getId() + ", " + req.getName() + ", " + req.getRequestMessage());

            Resp resp = new Resp();
            resp.setId(req.getId());
            resp.setName("resp" + req.getId());
            resp.setResponseMessage("响应内容" + req.getId());
            ctx.writeAndFlush(resp);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
