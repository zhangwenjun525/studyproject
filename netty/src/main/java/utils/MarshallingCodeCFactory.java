package utils;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/4
 * Time: 11:45
 */
public class MarshallingCodeCFactory {

    /**
     * 创建JBoss Marshalling解码器
     *
     * @return 解码器
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
        /*首先通过Marshalling工具类的getProvidedMarshallerFactory静态方法获取MarshallerFactory实例,
         *参数serial表示创建的是java序列化工厂
         */
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");

        /**
         * 创建MarshallingConfiguration对象，配置版本号为5
         */
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        /**
         * 根据marshallerFactory和configuration创建provider
         */
        UnmarshallerProvider unmarshallerProvider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);

        /**
         * 构建Netty的MarshallingDecoder对象,
         * 参数二代表单个对象序列化后最大的尺寸
         */
        MarshallingDecoder decoder = new MarshallingDecoder(unmarshallerProvider, 1024 << 2);

        return decoder;
    }

    /**
     * 创建JBoss Marshalling编码器
     *
     * @return 编码器
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
        /*首先通过Marshalling工具类的getProvidedMarshallerFactory静态方法获取MarshallerFactory实例,
         *参数serial表示创建的是java序列化工厂
         */
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");

        /**
         * 创建MarshallingConfiguration对象，配置版本号为5
         */
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        /**
         * 根据marshallerFactory和configuration创建provider
         */
        MarshallerProvider marshallerProvider = new DefaultMarshallerProvider(marshallerFactory, configuration);

        MarshallingEncoder encoder = new MarshallingEncoder(marshallerProvider);

        return encoder;
    }

}
