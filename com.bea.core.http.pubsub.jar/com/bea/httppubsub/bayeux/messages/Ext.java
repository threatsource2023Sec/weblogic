package com.bea.httppubsub.bayeux.messages;

import java.io.Serializable;

public class Ext implements Serializable {
   private static final long serialVersionUID = 4270506840022212672L;
   private String jsonString = null;
   private boolean jsonCommentFiltered = false;

   public void setJsonCommentFiltered(boolean jsonCommentFiltered) {
      this.jsonCommentFiltered = jsonCommentFiltered;
      this.fieldUpdated();
   }

   public boolean isJsonCommentFiltered() {
      return this.jsonCommentFiltered;
   }

   public String toJSONString() {
      if (this.jsonString != null) {
         return this.jsonString;
      } else {
         StringBuilder sbuf = new StringBuilder();
         sbuf.append("\"ext\": {");
         sbuf.append("\"").append("json-comment-filtered").append("\": ");
         sbuf.append(this.jsonCommentFiltered);
         sbuf.append("}");
         this.jsonString = sbuf.toString();
         return this.jsonString;
      }
   }

   private void fieldUpdated() {
      this.jsonString = null;
   }
}
