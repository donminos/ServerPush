package com.newinit.jdbc;

import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 *
 * @author ceasar
 */
public class ConectorDB {

    protected static MongoClient initDB() {
        /**
         * ** Connect to MongoDB ***
         */
        // Since 2.10.0, uses MongoClient
        MongoClient mongo = new MongoClient("localhost", 27017);

        /**
         * ** Get database ***
         */
        // if database doesn't exists, MongoDB will create it for you
        
        return mongo;
    }
}
