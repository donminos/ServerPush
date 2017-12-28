package com.newinit.controller;

import com.newinit.controller.view.JsonResponseView;
import com.newinit.jdbc.ActionDB;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Carlos Cesar Rosas<face_less@hotmail.com>
 */
@RestController
public class SendMessage {

    @RequestMapping(value = "/registro.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponseView registro(HttpServletRequest request,@RequestBody Dispositivo dsp) {
        JsonResponseView json = new JsonResponseView();
        String token = ActionDB.insert(null, dsp.getImei(), dsp.getOs());
        json.getResponse().put("token", token);
        return json;
    }
}
