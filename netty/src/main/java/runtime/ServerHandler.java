package runtime;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/4
 * Time: 15:09
 */
public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Request request = (Request) msg;
            System.out.println("Server : " + request.getId() + ", " + request.getName() + ", " + request.getRequestMessage());
            Response response = new Response();
            response.setId(request.getId());
            response.setName("response" + request.getId());
            response.setResponseMessage("响应内容" + request.getId());
            ctx.writeAndFlush(response);//.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
