import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/6
 * Time: 18:22
 */
public class AmazonS3Service {

    protected static AmazonS3 getS3Conn() {
        AWSCredentials credentials = null;
        try {
            credentials = new PropertiesCredentials(new File("e:/amazons3.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new AmazonS3Client(credentials);
    }


    public static void main(String[] args) {
/*        AmazonS3 s3Conn = getS3Conn();
        List<Bucket> buckets = s3Conn.listBuckets();
        String folder = "test/img/";
        buckets.stream().forEach(System.out::println);*/

        String fullUrl = "https://s3-ap-northeast-1.amazonaws.com/qibei-app/test/img/2.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20170411T045027Z&X-Amz-SignedHeaders=host&X-Amz-Expires=899&X-Amz-Credential=AKIAJONFPE254LY2HABA%2F20170411%2Fap-northeast-1%2Fs3%2Faws4_request&X-Amz-Signature=48c399909820bbbd6a6cb9719c08c3815d72cba51d441d5764f3054b0ac3cfed";
        System.out.println(fullUrl.substring(0, fullUrl.indexOf("?")));


/*        PutObjectRequest request = new PutObjectRequest("qibei-app", folder + "3.jpg", new File("E:" + File.separator + "img8.jpg"))
                .withCannedAcl(CannedAccessControlList.PublicRead);

        PutObjectResult putObjectResult = s3Conn.putObject(request);
        System.out.println(putObjectResult);


        GeneratePresignedUrlRequest r = new GeneratePresignedUrlRequest("qibei-app",
                folder + "3.jpg");
        URL url = s3Conn.generatePresignedUrl(r);
        System.out.println(url.toString());*/
    }

}
