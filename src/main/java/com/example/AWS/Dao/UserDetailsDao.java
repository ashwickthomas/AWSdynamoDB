package com.example.AWS.Dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.AWS.Model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

/**
 * @author tapas
 *
 */
@Repository
public class UserDetailsDao {

    private static Logger LOGGER = LoggerFactory.getLogger(UserDetailsDao.class);

    AmazonDynamoDB client = AmazonDynamoDBClient.builder().withRegion("ap-northeast-1" +
            "").build();

   // AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
    DynamoDB dynamoDb = new DynamoDB(client);

    public UserModel getUserDetails(String key) {
        Table table = dynamoDb.getTable("users");
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("user_id", key);
        try {
            System.out.println("Attempting to read item");
            Item outcome = table.getItem(spec);
            if (Objects.nonNull(outcome)) {
                UserModel user = new UserModel();
                user.setUserId(outcome.get("user_id").toString());
                user.setFirstName(outcome.get("first_name").toString());
                user.setLastName(outcome.get("last_name").toString());
                return user;
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred during getUserDetails : ", e);
        }
        return null;
    }

    public String addUserDetails(UserModel user) {
        Table table = dynamoDb.getTable("users");
        try {
            final Map<String, String> addressMap = new HashMap<String, String>();
            addressMap.put("city", "Hyderabad");
            addressMap.put("pin", "500019");
            PutItemOutcome outcome = table.putItem(
                    new Item().withPrimaryKey("user_id", user.getUserId()).with("first_name", user.getFirstName())
                            .with("last_name", user.getLastName()).withMap("address", addressMap));
            if (Objects.nonNull(outcome))
                return "SUCCESS";
            else
                return "FAILURE";
        } catch (Exception e) {
            LOGGER.error("Exception occurred while adding record to the db : ", e);
            return null;
        }
    }

}