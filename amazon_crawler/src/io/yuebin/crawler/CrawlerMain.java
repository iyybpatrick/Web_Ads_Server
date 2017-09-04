
package io.bittiger.crawler;

/**
 * Created by YuebinYang on 2017/4/11.
 */
        import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.File;
        import java.io.FileWriter;
        import java.io.BufferedWriter;

        import java.io.IOException;
        import java.util.List;
        import com.fasterxml.jackson.core.JsonGenerationException;
        import com.fasterxml.jackson.databind.JsonMappingException;
        import com.fasterxml.jackson.databind.ObjectMapper;

        import io.bittiger.ad.Ad;

public class CrawlerMain {
    public static void main(String[] args) throws IOException {
        if(args.length < 2)
        {                                       // seeds file           //输出file     // log file 用于记录已经爬过的数据
            System.out.println("Usage: Crawler <rawQueryDataFilePath> <adsDataFilePath> <proxyFilePath> <logFilePath>");
            System.exit(0);
        }
        ObjectMapper mapper = new ObjectMapper();
        String rawQueryDataFilePath = args[0];
        String adsDataFilePath = args[1];  //ads data
        String proxyFilePath = args[2];
        String logFilePath = args[3];
        AmazonCrawler crawler = new AmazonCrawler(proxyFilePath, logFilePath);
        File file = new File(adsDataFilePath);   // ads data file
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();  // ads data
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());   //fw == ads data
        BufferedWriter bw = new BufferedWriter(fw);
        try (BufferedReader br = new BufferedReader(new FileReader(rawQueryDataFilePath))) {   // 一行行的读query file,拿去解析

            String line;
            while ((line = br.readLine()) != null) {  // 每次while循环读入新的一行数据
                if(line.isEmpty())   // 读到空行
                    continue;

                System.out.println(line);  //每一次处理，先读入raw data query， 表明接下来的内容输出的是谁的内容
                String[] fields = line.split(",");
                String query = fields[0].trim();    //去掉字符串首位的空格
                if (query == null) System.out.println("empty query");
                double bidPrice = Double.parseDouble(fields[1].trim());
                int campaignId = Integer.parseInt(fields[2].trim());
                int queryGroupId = Integer.parseInt(fields[3].trim());
                List<Ad> ads =  crawler.GetAdBasicInfoByQuery(query, bidPrice, campaignId, queryGroupId);
                for(Ad ad : ads) {
                    String jsonInString = mapper.writeValueAsString(ad);
                    //System.out.println(jsonInString);
                    bw.write(jsonInString);
                    bw.newLine();
                }
                Thread.sleep(5000);
            }
            bw.close();
        }catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        crawler.cleanup();
    }
}
