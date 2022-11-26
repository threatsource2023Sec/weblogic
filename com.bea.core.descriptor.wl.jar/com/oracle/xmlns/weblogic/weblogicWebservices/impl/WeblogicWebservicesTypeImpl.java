package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWebservices.WeblogicWebservicesType;
import com.oracle.xmlns.weblogic.weblogicWebservices.WebserviceDescriptionType;
import com.oracle.xmlns.weblogic.weblogicWebservices.WebserviceSecurityType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicWebservicesTypeImpl extends XmlComplexContentImpl implements WeblogicWebservicesType {
   private static final long serialVersionUID = 1L;
   private static final QName WEBSERVICEDESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "webservice-description");
   private static final QName WEBSERVICESECURITY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "webservice-security");
   private static final QName VERSION$4 = new QName("", "version");

   public WeblogicWebservicesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public WebserviceDescriptionType[] getWebserviceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WEBSERVICEDESCRIPTION$0, targetList);
         WebserviceDescriptionType[] result = new WebserviceDescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WebserviceDescriptionType getWebserviceDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebserviceDescriptionType target = null;
         target = (WebserviceDescriptionType)this.get_store().find_element_user(WEBSERVICEDESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWebserviceDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBSERVICEDESCRIPTION$0);
      }
   }

   public void setWebserviceDescriptionArray(WebserviceDescriptionType[] webserviceDescriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(webserviceDescriptionArray, WEBSERVICEDESCRIPTION$0);
   }

   public void setWebserviceDescriptionArray(int i, WebserviceDescriptionType webserviceDescription) {
      this.generatedSetterHelperImpl(webserviceDescription, WEBSERVICEDESCRIPTION$0, i, (short)2);
   }

   public WebserviceDescriptionType insertNewWebserviceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebserviceDescriptionType target = null;
         target = (WebserviceDescriptionType)this.get_store().insert_element_user(WEBSERVICEDESCRIPTION$0, i);
         return target;
      }
   }

   public WebserviceDescriptionType addNewWebserviceDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebserviceDescriptionType target = null;
         target = (WebserviceDescriptionType)this.get_store().add_element_user(WEBSERVICEDESCRIPTION$0);
         return target;
      }
   }

   public void removeWebserviceDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBSERVICEDESCRIPTION$0, i);
      }
   }

   public WebserviceSecurityType getWebserviceSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebserviceSecurityType target = null;
         target = (WebserviceSecurityType)this.get_store().find_element_user(WEBSERVICESECURITY$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWebserviceSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBSERVICESECURITY$2) != 0;
      }
   }

   public void setWebserviceSecurity(WebserviceSecurityType webserviceSecurity) {
      this.generatedSetterHelperImpl(webserviceSecurity, WEBSERVICESECURITY$2, 0, (short)1);
   }

   public WebserviceSecurityType addNewWebserviceSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebserviceSecurityType target = null;
         target = (WebserviceSecurityType)this.get_store().add_element_user(WEBSERVICESECURITY$2);
         return target;
      }
   }

   public void unsetWebserviceSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBSERVICESECURITY$2, 0);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$4);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$4) != null;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$4);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$4);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$4);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$4);
      }
   }
}
