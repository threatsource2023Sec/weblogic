package org.glassfish.hk2.xml.internal;

import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;

public class UnresolvedReference {
   private final String type;
   private final String xmlID;
   private final String propertyNamespace;
   private final String propertyName;
   private final BaseHK2JAXBBean unfinished;

   UnresolvedReference(String type, String xmlID, String propertyNamespace, String propertyName, BaseHK2JAXBBean unfinished) {
      this.type = type;
      this.xmlID = xmlID;
      this.propertyNamespace = propertyNamespace;
      this.propertyName = propertyName;
      this.unfinished = unfinished;
   }

   public String getType() {
      return this.type;
   }

   public String getXmlID() {
      return this.xmlID;
   }

   public String getPropertyNamespace() {
      return this.propertyNamespace;
   }

   public String getPropertyName() {
      return this.propertyName;
   }

   public BaseHK2JAXBBean getUnfinished() {
      return this.unfinished;
   }

   public String toString() {
      return "UnresolvedReference(" + this.type + "," + this.xmlID + "," + this.propertyNamespace + "," + this.propertyName + "," + this.unfinished + "," + System.identityHashCode(this) + ")";
   }
}
