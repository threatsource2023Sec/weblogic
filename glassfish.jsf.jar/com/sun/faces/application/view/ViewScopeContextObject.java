package com.sun.faces.application.view;

import java.io.Serializable;

class ViewScopeContextObject implements Serializable {
   private static final long serialVersionUID = 370695657651519831L;
   private String passivationCapableId;
   private String name;

   public ViewScopeContextObject(String passivationCapableId, String name) {
      this.passivationCapableId = passivationCapableId;
      this.name = name;
   }

   public String getPassivationCapableId() {
      return this.passivationCapableId;
   }

   public String getName() {
      return this.name;
   }

   public void setPassivationCapableId(String passivationCapableId) {
      this.passivationCapableId = passivationCapableId;
   }

   public void setName(String name) {
      this.name = name;
   }
}
