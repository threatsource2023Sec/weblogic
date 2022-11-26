package org.glassfish.admin.rest.model;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ErrorResponseBody {
   private static final String ERROR_MESSAGE_SCHEMA_URL = "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1";
   private int status;
   private ResponseBody rb;

   public ErrorResponseBody(int status, ResponseBody rb) {
      this.status = status;
      this.rb = rb;
   }

   public JSONObject toJson() throws JSONException {
      JSONObject orig = this.rb.toJson();
      JSONArray links = orig.optJSONArray("links");
      JSONArray messages = orig.optJSONArray("messages");
      JSONObject rtn = new JSONObject();
      rtn.put("status", this.status);
      if (links != null) {
         rtn.put("links", links);
      }

      this.addRestErrorMessages(rtn, messages);
      return rtn;
   }

   private void addRestErrorMessages(JSONObject j, JSONArray messages) throws JSONException {
      if (messages != null) {
         if (messages.length() >= 1) {
            if (messages.length() == 1) {
               JSONObject message = messages.getJSONObject(0);
               String field = message.optString("field");
               if (field == null || field.length() < 1) {
                  this.addTopLevelRestErrorMessage(j, message);
                  return;
               }
            }

            this.addNestedRestErrorMessages(j, messages);
         }
      }
   }

   private void addNestedRestErrorMessages(JSONObject j, JSONArray messages) throws JSONException {
      JSONArray a = new JSONArray();

      for(int i = 0; i < messages.length(); ++i) {
         JSONObject m = new JSONObject();
         this.addTopLevelRestErrorMessage(m, messages.getJSONObject(i));
         a.put(m);
      }

      j.put("type", "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1");
      j.put("title", "ERRORS");
      j.put("wls:errorsDetails", a);
   }

   private void addTopLevelRestErrorMessage(JSONObject j, JSONObject message) throws JSONException {
      j.put("type", "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1");
      j.put("title", message.getString("severity"));
      j.put("detail", message.getString("message"));
      String field = message.optString("field");
      if (field != null && field.length() > 0) {
         j.put("o:errorPath", field);
      }

   }
}
