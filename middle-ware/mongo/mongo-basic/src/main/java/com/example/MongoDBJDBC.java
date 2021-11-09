package com.example;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yzh
 * @Date 2020/3/18 14:08
 * @version 1.0
 */
public class MongoDBJDBC {
    //设置连接客户端
    private static MongoClient mongoClient = null;
    //数据库名
    private static MongoDatabase mongoDatabase = null;
    //获取集合
    private static MongoCollection<Document> collection = null;
    //存放文档集合
    private static final List<Document> list = new ArrayList<>();

    public static void main(String[] args) {
        connectToMongo("localhost", 27017);
        //createCollection("test");
        getCollection("test");
        //Document采用 K-V存储
        //存放文档
        Document doc = new Document("title", "MongoDB");
        doc.append("description", "database");
        doc.append("likes", 100);
        list.add(doc);
        insertDocument(doc, null);
        insertDocument(null, list);

        showDoc();
        update();
        delete();
    }

    private static void delete(){
        System.out.println("==================执行删除操作=====================");
        //删除一个
        collection.deleteOne(Filters.eq("likes",200));
        System.out.println("============删除一个================");
        showDoc();
        //删除多个
        collection.deleteMany(Filters.eq("likes",200));
        System.out.println("==============删除多个=============");
        //检索查看结果
        showDoc();
    }


    /**
     * 更新文档
     */
    private static void update(){
        System.out.println("=================修改所有likes为100的文档=================");
        try {
            collection.updateMany(Filters.eq("likes",100),new Document("$set", new Document("likes",200)));
           showDoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历文档
     */
    private static void showDoc(){
        System.out.println("======================查看文档======================");
        try {
            FindIterable<Document> documents = collection.find();
            for (Document document : documents) {
                System.out.println(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 往集合中插入文档
     * @param doc 单个文档
     * @param list 多个文档
     */
    private static void insertDocument(Document doc, List<Document> list){
        System.out.println("===================插入文档==================");
        //如果是多个文档则用insetMany
        //如果是单个文档则用insertOne
        try {
            if(doc == null && list != null) {
                collection.insertMany(list);
            } else if(list!=null){
                collection.insertOne(doc);
            }
            System.out.println("doc插入成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取集合
     * @param collectionName 集合名称
     */
    private static void  getCollection(String collectionName) {
        System.out.println("==================获取集合==================");
        try {
            collection = mongoDatabase.getCollection(collectionName);
            System.out.println("集合 "+collectionName+" 选择成功, 地址为"+collection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  创建集合
     * @param collectionName 集合名称
     */
    private static void createCollection(String collectionName) {
        System.out.println("====================创建集合=================");
        try {
            mongoDatabase.createCollection(collectionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /**
     * 本地连接MongoDB数据库
     *
     * @param host 主机地址
     * @param port 端口号
     */
  private static void connectToMongo(String host, int port) {
        try {
            //连接到MongoDB服务
            mongoClient = new MongoClient(host, port);

            //连接到数据库
            mongoDatabase = mongoClient.getDatabase("col");

            System.out.println("Connect to database successfully");
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     *  第二种连接数据的方法
     * @param host  主机地址
     * @param port  端口号
     * @param username  登入用户名
     * @param password  密码
     * @param databaseName  连接数据的名称
     */
    private static void connectMongoDB2(String host, int port, String username, String password, String databaseName) {
        try {
            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
            //ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress(host, port);
            List<ServerAddress> adds = new ArrayList<>();
            adds.add(serverAddress);

            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            MongoCredential credential = MongoCredential.createScramSha1Credential(username, databaseName, password.toCharArray());
            ArrayList<MongoCredential> credentials = new ArrayList<>();
            credentials.add(credential);

            //通过连接认证获取MongoDB连接
            mongoClient = new MongoClient(adds, credentials);
            //连接到数据库
            mongoDatabase = mongoClient.getDatabase(databaseName);

            System.out.println("Connect to database successfully");
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

}


