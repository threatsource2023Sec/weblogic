package org.glassfish.admin.rest.model;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.utils.JsonFilter;

public class RestJsonResponseBody extends ResponseBody {
   private JSONObject entity;
   private JSONArray entities;

   public RestJsonResponseBody(HttpServletRequest request) {
      this(request, (JsonFilter)null);
   }

   public RestJsonResponseBody(HttpServletRequest request, JsonFilter filter) {
      super(request, filter);
   }

   public JSONObject getEntity() {
      return this.entity;
   }

   public RestJsonResponseBody setEntity(JSONObject entity) {
      this.entity = entity;
      return this;
   }

   public JSONArray getEntities() {
      return this.entities;
   }

   public RestJsonResponseBody setEntities(JSONArray entities) {
      this.entities = entities;
      return this;
   }

   protected void populateJson(JSONObject object) throws JSONException {
      super.populateJson(object);
      if (this.getEntity() != null) {
         JSONObject item = this.getEntity();
         Iterator i = item.keys();

         while(i.hasNext()) {
            String key = (String)i.next();
            if ("links".equals(key) || "messages".equals(key)) {
               throw new AssertionError("An entity contains a property with a reserved property name: " + key);
            }

            object.put(key, item.get(key));
         }
      }

      if (this.getEntities() != null) {
         object.put("items", this.getEntities());
      }

   }
}
