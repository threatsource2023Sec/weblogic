package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.oracle.xmlns.weblogic.weblogicWebservices.PortComponentType;
import com.oracle.xmlns.weblogic.weblogicWebservices.WebserviceDescriptionType;
import com.sun.java.xml.ns.j2Ee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WebserviceDescriptionTypeImpl extends XmlComplexContentImpl implements WebserviceDescriptionType {
   private static final long serialVersionUID = 1L;
   private static final QName WEBSERVICEDESCRIPTIONNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "webservice-description-name");
   private static final QName WEBSERVICETYPE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "webservice-type");
   private static final QName WSDLPUBLISHFILE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "wsdl-publish-file");
   private static final QName PORTCOMPONENT$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "port-component");

   public WebserviceDescriptionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getWebserviceDescriptionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(WEBSERVICEDESCRIPTIONNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWebserviceDescriptionName(String webserviceDescriptionName) {
      this.generatedSetterHelperImpl(webserviceDescriptionName, WEBSERVICEDESCRIPTIONNAME$0, 0, (short)1);
   }

   public String addNewWebserviceDescriptionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(WEBSERVICEDESCRIPTIONNAME$0);
         return target;
      }
   }

   public WebserviceDescriptionType.WebserviceType.Enum getWebserviceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WEBSERVICETYPE$2, 0);
         return target == null ? null : (WebserviceDescriptionType.WebserviceType.Enum)target.getEnumValue();
      }
   }

   public WebserviceDescriptionType.WebserviceType xgetWebserviceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebserviceDescriptionType.WebserviceType target = null;
         target = (WebserviceDescriptionType.WebserviceType)this.get_store().find_element_user(WEBSERVICETYPE$2, 0);
         return target;
      }
   }

   public boolean isSetWebserviceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBSERVICETYPE$2) != 0;
      }
   }

   public void setWebserviceType(WebserviceDescriptionType.WebserviceType.Enum webserviceType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WEBSERVICETYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(WEBSERVICETYPE$2);
         }

         target.setEnumValue(webserviceType);
      }
   }

   public void xsetWebserviceType(WebserviceDescriptionType.WebserviceType webserviceType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebserviceDescriptionType.WebserviceType target = null;
         target = (WebserviceDescriptionType.WebserviceType)this.get_store().find_element_user(WEBSERVICETYPE$2, 0);
         if (target == null) {
            target = (WebserviceDescriptionType.WebserviceType)this.get_store().add_element_user(WEBSERVICETYPE$2);
         }

         target.set(webserviceType);
      }
   }

   public void unsetWebserviceType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBSERVICETYPE$2, 0);
      }
   }

   public String getWsdlPublishFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(WSDLPUBLISHFILE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsdlPublishFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSDLPUBLISHFILE$4) != 0;
      }
   }

   public void setWsdlPublishFile(String wsdlPublishFile) {
      this.generatedSetterHelperImpl(wsdlPublishFile, WSDLPUBLISHFILE$4, 0, (short)1);
   }

   public String addNewWsdlPublishFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(WSDLPUBLISHFILE$4);
         return target;
      }
   }

   public void unsetWsdlPublishFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSDLPUBLISHFILE$4, 0);
      }
   }

   public PortComponentType[] getPortComponentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PORTCOMPONENT$6, targetList);
         PortComponentType[] result = new PortComponentType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PortComponentType getPortComponentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentType target = null;
         target = (PortComponentType)this.get_store().find_element_user(PORTCOMPONENT$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPortComponentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORTCOMPONENT$6);
      }
   }

   public void setPortComponentArray(PortComponentType[] portComponentArray) {
      this.check_orphaned();
      this.arraySetterHelper(portComponentArray, PORTCOMPONENT$6);
   }

   public void setPortComponentArray(int i, PortComponentType portComponent) {
      this.generatedSetterHelperImpl(portComponent, PORTCOMPONENT$6, i, (short)2);
   }

   public PortComponentType insertNewPortComponent(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentType target = null;
         target = (PortComponentType)this.get_store().insert_element_user(PORTCOMPONENT$6, i);
         return target;
      }
   }

   public PortComponentType addNewPortComponent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentType target = null;
         target = (PortComponentType)this.get_store().add_element_user(PORTCOMPONENT$6);
         return target;
      }
   }

   public void removePortComponent(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTCOMPONENT$6, i);
      }
   }

   public static class WebserviceTypeImpl extends JavaStringEnumerationHolderEx implements WebserviceDescriptionType.WebserviceType {
      private static final long serialVersionUID = 1L;

      public WebserviceTypeImpl(SchemaType sType) {
         super(sType, false);
      }

      protected WebserviceTypeImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
