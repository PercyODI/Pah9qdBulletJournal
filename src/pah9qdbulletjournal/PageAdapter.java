/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pah9qdbulletjournal;

import com.app.taskpage.TaskPage;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author pah9qd
 */

//https://www.javacodegeeks.com/2012/04/json-with-gson-and-abstract-classes.html
public class PageAdapter implements JsonSerializer<Page>, JsonDeserializer<Page> {

    @Override
    public JsonElement serialize(Page p, Type type, JsonSerializationContext jsc) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(p.getClass().getName()));
        result.add("properties", jsc.serialize(p, p.getClass()));

        return result;
    }

    @Override
    public TaskPage deserialize(JsonElement je, Type typeOfT, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = je.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return jdc.deserialize(element, Class.forName(type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }  
    }
    
}
