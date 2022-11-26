package org.glassfish.admin.rest.model;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.utils.StringUtil;

public class Message {
   private Severity severity;
   private String field;
   private String message;

   public Message(Severity severity, String message) {
      this.severity = severity;
      this.message = message;
   }

   public Message(Severity severity, String field, String message) {
      this.severity = severity;
      this.field = field;
      this.message = message;
   }

   public Severity getSeverity() {
      return this.severity;
   }

   public void setSeverity(Severity val) {
      this.severity = val;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String val) {
      this.message = val;
   }

   public String getField() {
      return this.field;
   }

   public void setField(String val) {
      this.field = val;
   }

   public JSONObject toJson() throws JSONException {
      JSONObject object = new JSONObject();
      object.put("message", StringUtil.nonNull(this.getMessage()));
      object.put("severity", this.getSeverity());
      String f = this.getField();
      if (StringUtil.notEmpty(f)) {
         object.put("field", f);
      }

      return object;
   }

   public static enum Severity {
      SUCCESS,
      WARNING,
      FAILURE;
   }
}
