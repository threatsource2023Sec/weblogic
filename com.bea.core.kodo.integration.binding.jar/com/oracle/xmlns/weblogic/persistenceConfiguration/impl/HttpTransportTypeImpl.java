package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.HttpTransportType;
import javax.xml.namespace.QName;

public class HttpTransportTypeImpl extends PersistenceServerTypeImpl implements HttpTransportType {
   private static final long serialVersionUID = 1L;
   private static final QName URL$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "url");

   public HttpTransportTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URL$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URL$0, 0);
         return target;
      }
   }

   public boolean isNilUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URL$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(URL$0) != 0;
      }
   }

   public void setUrl(String url) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URL$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(URL$0);
         }

         target.setStringValue(url);
      }
   }

   public void xsetUrl(XmlString url) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URL$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(URL$0);
         }

         target.set(url);
      }
   }

   public void setNilUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URL$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(URL$0);
         }

         target.setNil();
      }
   }

   public void unsetUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(URL$0, 0);
      }
   }
}
