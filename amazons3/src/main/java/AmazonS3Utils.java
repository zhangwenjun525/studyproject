import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/11
 * Time: 10:38
 */
public class AmazonS3Utils {

    private String accessKey;

    private String accessSecret;

    private String bucketName;

    private AmazonS3Client s3Client;

    public void init() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        s3Client = new AmazonS3Client(credentials);
    }

    private AmazonS3Utils() {
    }


    public AmazonS3Utils(String accessKey, String accessSecret) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        init();
    }

    public AmazonS3Utils(String accessKey, String accessSecret, String bucketName) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.bucketName = bucketName;
        init();
    }

    /**
     * 上传指定用户文件
     *
     * @param userId        用户Id  生成的文件名为用户Id + UUID
     * @param multipartFile
     * @return
     */
    public String uploadAssignUserFile(String userId, MultipartFile multipartFile) {
        return uploadAssignUserFile(this.bucketName, userId, multipartFile);
    }

    /**
     * 上传指定用户文件
     *
     * @param bucketName    文件桶
     * @param userId        用户Id  生成的文件名为用户Id + UUID
     * @param multipartFile
     * @return
     */
    public String uploadAssignUserFile(String bucketName, String userId, MultipartFile multipartFile) {
        return uploadAssignUserFile(bucketName, null, userId, multipartFile);
    }


    /**
     * 上传指定用户文件
     *
     * @param bucketName    文件桶
     * @param folder        文件桶下的文件夹
     * @param userId        用户Id  生成的文件名为用户Id + UUID
     * @param multipartFile
     * @return
     */
    public String uploadAssignUserFile(String bucketName, String folder, String userId, MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            Random random = new Random();
            String name_ = userId + random.nextInt(10000) + substring;
            fileUrl = uploadFile(bucketName, folder, name_, multipartFile.getInputStream());
        } catch (IOException e) {

        }
        return fileUrl;
    }

    /**
     * 文件上传
     *
     * @param fileName    文件名
     * @param inputStream 文件流
     * @return
     */
    public String uploadFile(String fileName, InputStream inputStream) {
        return uploadFile(this.bucketName, null, fileName, inputStream);
    }

    /**
     * 文件上传
     *
     * @param bucketName    文件桶
     * @param folder        文件桶下的文件夹
     * @param multipartFile
     * @return
     */
    public String uploadFile(String bucketName, String folder, MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            fileUrl = uploadFile(bucketName, folder, multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        } catch (IOException e) {

        }
        return fileUrl;
    }

    /**
     * @param bucketName  文件桶
     * @param folder      文件桶下的文件夹
     * @param fileName    文件名
     * @param inputStream 文件流
     * @return
     */
    public String uploadFile(String bucketName, String folder, String fileName, InputStream inputStream) {
        String fileUrl = "";
        try {
            //1.设置元素据
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength((long) inputStream.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentType(getcontentType(getFileExtensions(fileName)));
            metadata.setContentDisposition("inline;filename=" + fileName);
            //2.上传文件
            String filePath = assemblyFolderAndFileName(folder, fileName);
            PutObjectRequest request = new PutObjectRequest(bucketName, filePath, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult putObjectResult = s3Client.putObject(request);

            //3.获取上传后的url
            GeneratePresignedUrlRequest r = new GeneratePresignedUrlRequest(bucketName, filePath);
            URL url = s3Client.generatePresignedUrl(r);

            fileUrl = url.toString();
        } catch (IOException e) {

        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return fileUrl;
    }


    private String getFileExtensions(String fileName) {
        if (fileName == null || fileName.trim().length() == 0)
            return "";
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String assemblyFolderAndFileName(String folder, String fileName) {
        if (null != folder) {
            folder = folder.trim();
            if (folder.startsWith("/")) {
                folder = folder.substring(1);
            }

            if (!folder.endsWith("/")) {
                folder = folder.concat("/");
            }
        } else {
            folder = "";
        }
        return folder.concat(fileName);
    }

    private String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase("jpeg") ||
                FilenameExtension.equalsIgnoreCase("jpg") ||
                FilenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase("pptx") ||
                FilenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase("docx") ||
                FilenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }


}
