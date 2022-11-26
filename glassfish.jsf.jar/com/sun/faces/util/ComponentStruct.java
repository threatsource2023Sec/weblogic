package com.sun.faces.util;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

public class ComponentStruct implements StateHolder {
   public static final String ADD = "ADD";
   public static final String REMOVE = "REMOVE";
   private String action;
   private String facetName;
   private String parentClientId;
   private String clientId;
   private String id;

   public ComponentStruct() {
   }

   public ComponentStruct(String action, String clientId, String id) {
      this.action = action;
      this.clientId = clientId;
      this.id = id;
   }

   public ComponentStruct(String action, String facetName, String parentClientId, String clientId, String id) {
      this.action = action;
      this.facetName = facetName;
      this.parentClientId = parentClientId;
      this.clientId = clientId;
      this.id = id;
   }

   public boolean isTransient() {
      return false;
   }

   public void restoreState(FacesContext ctx, Object state) {
      if (ctx == null) {
         throw new NullPointerException();
      } else if (state != null) {
         Object[] s = (Object[])((Object[])state);
         this.action = (String)s[0];
         this.parentClientId = (String)s[1];
         this.clientId = (String)s[2];
         this.id = (String)s[3];
         this.facetName = (String)s[4];
      }
   }

   public Object saveState(FacesContext ctx) {
      if (ctx == null) {
         throw new NullPointerException();
      } else {
         Object[] state = new Object[]{this.action, this.parentClientId, this.clientId, this.id, this.facetName};
         return state;
      }
   }

   public void setTransient(boolean trans) {
   }

   public boolean equals(Object obj) {
      boolean result = false;
      if (obj instanceof ComponentStruct) {
         ComponentStruct struct = (ComponentStruct)obj;
         result = struct.clientId.equals(this.clientId);
      }

      return result;
   }

   public int hashCode() {
      int hash = 5;
      hash = 89 * hash + (this.clientId != null ? this.clientId.hashCode() : 0);
      return hash;
   }

   public String getAction() {
      return this.action;
   }

   public String getFacetName() {
      return this.facetName;
   }

   public String getParentClientId() {
      return this.parentClientId;
   }

   public String getClientId() {
      return this.clientId;
   }

   public String getId() {
      return this.id;
   }
}
