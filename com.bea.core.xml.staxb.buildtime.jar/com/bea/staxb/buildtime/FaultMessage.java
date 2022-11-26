package com.bea.staxb.buildtime;

import javax.xml.namespace.QName;

public class FaultMessage {
   private QName name;
   private QName messageName;
   private String partName;

   public FaultMessage() {
   }

   public FaultMessage(QName name, QName namem, String namep) {
      this.name = name;
      this.messageName = namem;
      this.partName = namep;
   }

   public void setComponentName(QName name) {
      this.name = name;
   }

   public QName getComponentName() {
      return this.name;
   }

   public void setMessageName(QName name) {
      this.messageName = name;
   }

   public QName getMessageName() {
      return this.messageName;
   }

   public void setPartName(String name) {
      this.partName = name;
   }

   public String getPartName() {
      return this.partName;
   }
}
