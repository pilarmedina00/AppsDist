package aadd.mongo.dao;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public abstract class MongoDAO {
	protected static MongoClient mongoClient;
	protected static MongoDatabase db;
	protected static MongoCollection<Document> collection;

	public MongoDAO() {
		mongoClient = MongoClients.create("mongodb://localhost:27017");
		db = mongoClient.getDatabase("ecpaquita74");
		createCollection();
	}

	public abstract void createCollection();
}
