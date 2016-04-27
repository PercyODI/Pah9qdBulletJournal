/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pah9qdbulletjournal;

import org.json.simple.JSONObject;

/**
 *
 * @author pah9qd
 */
public interface JSONAble {
    public JSONObject convertToJson();
    public void parseJson(JSONObject jsonObject);
}
