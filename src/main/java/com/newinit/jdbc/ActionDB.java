/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newinit.jdbc;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ceasar
 */
public class ActionDB extends ConectorDB {

    public static String findSessionForToken(String token) {
        MongoClient mongo = new MongoClient();
        DB db = mongo.getDB("notificationPush");
        DBCollection table = db.getCollection("dispositivos");

        BasicDBObject query = new BasicDBObject();
        query.put("token", token);

        DBCursor cursor = table.find(query);
        String sesion = null;
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            System.out.println(obj);
            sesion = obj.getString("sesion");
        }
        return sesion;
    }

    public static List<BasicDBObject> findMessageNotSend() {
        MongoClient mongo = new MongoClient();
        DB db = mongo.getDB("notificationPush");
        DBCollection table = db.getCollection("mensajesNoEnviados");

        DBCursor cursor = table.find();
        String sesion = null;
        List<BasicDBObject> noEnviados = new ArrayList();
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            System.out.println(obj);
            //sesion = obj.getString("sesion");
            noEnviados.add(obj);
        }
        return noEnviados;
    }

    public static boolean validateForSession(String sesion) {
        MongoClient mongo = new MongoClient();
        DB db = mongo.getDB("notificationPush");
        DBCollection table = db.getCollection("dispositivos");

        BasicDBObject query = new BasicDBObject();
        query.put("sesion", sesion);

        DBCursor cursor = table.find(query);
        boolean activo = false;
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            System.out.println(obj);
            activo = obj.getBoolean("Activo");
        }
        mongo.close();
        return activo;
    }
    
    public static boolean validateForToken(String token) {
        MongoClient mongo = new MongoClient();
        DB db = mongo.getDB("notificationPush");
        DBCollection table = db.getCollection("dispositivos");

        BasicDBObject query = new BasicDBObject();
        query.put("token", token);

        DBCursor cursor = table.find(query);
        boolean activo = false;
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            System.out.println(obj);
            activo = obj.getBoolean("Activo");
        }
        mongo.close();
        return activo;
    }

    public static String insert(String sesionid, String imei, String os) {
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid = " + uuid);

        MongoClient mongo = new MongoClient();
        DB db = mongo.getDB("notificationPush");
        DBCollection table = db.getCollection("dispositivos");

        /**
         * ** Insert ***
         */
        // create a document to store key and value
        BasicDBObject document = new BasicDBObject();
        document.put("sesion", sesionid);
        document.put("token", uuid);
        document.put("fechaReg", new Date());
        document.put("imei", imei);
        document.put("os", os);
        document.put("Activo", false);
        table.insert(document);
        mongo.close();
        return uuid;
    }

    public static boolean insertMensaje(String idSend, String idrecpt, String mensaje) {
        MongoClient mongo = new MongoClient();
        DB db = mongo.getDB("notificationPush");
        DBCollection disp = db.getCollection("dispositivos");
        DBCollection table = db.getCollection("mensajes");

        /**
         * ** Insert ***
         */
        // create a document to store key and value
        BasicDBObject query = new BasicDBObject();
        query.put("sesion", idSend);
        String tokenSend = null;
        DBCursor cursor = disp.find(query);
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            tokenSend = obj.getString("token");
        }
        query = new BasicDBObject();
        query.put("sesion", idrecpt);
        String tokenRcpt = null;
        cursor = disp.find(query);
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            tokenRcpt = obj.getString("token");
        }
        BasicDBObject document = new BasicDBObject();
        document.put("tokenSend", tokenSend);
        document.put("token", tokenRcpt);
        document.put("fechaReg", new Date());
        document.put("mensaje", mensaje);
        table.insert(document);
        mongo.close();
        return true;
    }

    public static boolean insertMensajeNotSend(String idSend, String idrecpt, String mensaje) {
        MongoClient mongo = new MongoClient();
        DB db = mongo.getDB("notificationPush");
        DBCollection disp = db.getCollection("dispositivos");
        DBCollection table = db.getCollection("mensajesNoEnviados");

        /**
         * ** Insert ***
         */
        // create a document to store key and value
        BasicDBObject query = new BasicDBObject();
        query.put("sesion", idSend);
        String tokenSend = null;
        DBCursor cursor = disp.find(query);
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            tokenSend = obj.getString("token");
        }
        query = new BasicDBObject();
        query.put("sesion", idrecpt);
        String tokenRcpt = null;
        cursor = disp.find(query);
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            tokenRcpt = obj.getString("token");
        }
        BasicDBObject document = new BasicDBObject();
        document.put("tokenSend", tokenSend);
        document.put("token", tokenRcpt);
        document.put("fechaReg", new Date());
        document.put("mensaje", mensaje);
        table.insert(document);
        mongo.close();
        return true;
    }
    
    public static boolean deleteMensajeNotSend(String idSend, String idrecpt, String mensaje) {
        MongoClient mongo = new MongoClient();
        DB db = mongo.getDB("notificationPush");
        DBCollection disp = db.getCollection("dispositivos");
        DBCollection table = db.getCollection("mensajesNoEnviados");

        /**
         * ** Insert ***
         */
        // create a document to store key and value
        BasicDBObject query = new BasicDBObject();
        query.put("sesion", idSend);
        String tokenSend = null;
        DBCursor cursor = disp.find(query);
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            tokenSend = obj.getString("token");
        }
        query = new BasicDBObject();
        query.put("sesion", idrecpt);
        String tokenRcpt = null;
        cursor = disp.find(query);
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            tokenRcpt = obj.getString("token");
        }
        BasicDBObject document = new BasicDBObject();
        //document.put("tokenSend", tokenSend);
        document.put("token", tokenRcpt);
        document.put("mensaje", mensaje);
        table.remove(document);
        mongo.close();
        return true;
    }

    public static boolean updateSession(String token, boolean status) {
        MongoClient mongo = new MongoClient();
        DB db = mongo.getDB("notificationPush");
        DBCollection table = db.getCollection("dispositivos");

        BasicDBObject query = new BasicDBObject();
        query.put("sesion", token);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("Activo", status);

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        table.update(query, updateObj);
        mongo.close();
        return true;
    }

    public static boolean updateSession(String token, String licencia, String sesionid) {
        boolean licenceValid = false;
        MongoClient mongo = new MongoClient();
        DB db = mongo.getDB("notificationPush");
        DBCollection table = db.getCollection("dispositivos");
        DBCollection collectionlicencias = db.getCollection("licencias");

        BasicDBObject queryLic = new BasicDBObject();
        queryLic.put("licencia", licencia);

        DBCursor cursor = collectionlicencias.find(queryLic);
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            licenceValid = obj.getBoolean("valida");
        }

        BasicDBObject query = new BasicDBObject();
        query.put("token", token);

        cursor = table.find(query);
        if (cursor.count() > 0 && licenceValid) {
            while (cursor.hasNext()) {
                BasicDBObject obj = (BasicDBObject) cursor.next();
                System.out.println(obj);
                obj.put("sesion", sesionid);
                obj.put("token", token);
                obj.put("licencia", licencia);
                table.update(query, obj);
            }
        } else if (licenceValid) {
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("sesion", sesionid);
            newDocument.put("token", token);
            newDocument.put("licencia", licencia);
            table.insert(newDocument);
        }
        //BasicDBObject updateObj = new BasicDBObject();
        //updateObj.put("$set", newDocument);

        mongo.close();
        return true;
    }

}
