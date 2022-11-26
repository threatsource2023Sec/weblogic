package com.sun.faces.flow;

public class FlowDiscoveryInfo {
   private Class definingClass;
   private String id;
   private String definingDocument;

   public String getDefiningDocument() {
      return this.definingDocument;
   }

   public void setDefiningDocument(String definingDocument) {
      this.definingDocument = definingDocument;
   }

   public FlowDiscoveryInfo(Class definingClass, String id, String definingDocument) {
      this.definingClass = definingClass;
      this.id = id;
      this.definingDocument = definingDocument;
   }

   public Class getDefiningClass() {
      return this.definingClass;
   }

   public void setDefiningClass(Class definingClass) {
      this.definingClass = definingClass;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }
}
