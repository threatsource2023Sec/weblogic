package org.glassfish.admin.rest.model;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.composite.RestModel;
import org.glassfish.admin.rest.utils.JsonUtil;

public class RestModelResponseBody extends ResponseBody {
   private RestModel entity;

   public RestModelResponseBody(HttpServletRequest request) {
      super(request);
   }

   public RestModel getEntity() {
      return this.entity;
   }

   public RestModelResponseBody setEntity(RestModel entity) {
      this.entity = entity;
      return this;
   }

   protected void populateJson(JSONObject object) throws JSONException {
      if (this.getEntity() != null) {
         this.populateItemJson(object);
      }

      super.populateJson(object);
   }

   protected void populateItemJson(JSONObject object) throws JSONException {
      JSONObject item = JsonUtil.getJsonForRestModel(this.getEntity(), true);
      Iterator i = item.keys();

      while(i.hasNext()) {
         String key = (String)i.next();
         if ("links".equals(key) || "messages".equals(key)) {
            throw new AssertionError("An entity contains a property with a reserved property name: " + key);
         }

         object.put(key, item.get(key));
      }

   }
}
