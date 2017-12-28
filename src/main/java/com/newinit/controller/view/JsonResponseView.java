/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newinit.controller.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos Cesar Rosas<face_less@hotmail.com>
 */
public class JsonResponseView implements Serializable{
    
    public JsonResponseView(){
        response=new HashMap();
        response.put("success", true);
    }
    private Map<Object, Object> response;

    public Map<Object, Object> getResponse() {
        return response;
    }

    public void setResponse(Map<Object, Object> response) {
        this.response = response;
    }
}
