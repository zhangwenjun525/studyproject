import cn.fraudmetrix.riskservice.RuleDetailClient;
import cn.fraudmetrix.riskservice.RuleDetailResult;
import cn.fraudmetrix.riskservice.object.Environment;
import cn.fraudmetrix.riskservice.ruledetail.BlackListDetail;
import cn.fraudmetrix.riskservice.ruledetail.BlackListHit;

import java.util.List;
import java.util.Observable;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/26
 * Time: 11:36
 */
public class RuleDetailDemo extends Observable {

    public static void main(String[] args) {
        // 填写参数
        String partnerCode = "qbdc";
        String partnerKey = "123456789";
        String sequenceId = "1462326266162000X065A0EDB3559014";
        Environment env = Environment.SANDBOX; // Environment.PRODUCT表示调用生产环境, 测试环境请修改为Environment.SANDBOX

        // 调用接口
        RuleDetailClient client = RuleDetailClient.getInstance(partnerCode, env);
        RuleDetailResult result = client.execute(partnerKey, sequenceId);
        if (result == null) return;

        // 样例：获取风险名单命中的数据
        List<BlackListDetail> find = result.find(BlackListDetail.class);
        for (BlackListDetail e : find) {
            List<BlackListHit> hits = e.getHits();
            for (BlackListHit hit : hits) {
                // hit中包含了命中风险名单的具体信息
            }
        }



    }

}
