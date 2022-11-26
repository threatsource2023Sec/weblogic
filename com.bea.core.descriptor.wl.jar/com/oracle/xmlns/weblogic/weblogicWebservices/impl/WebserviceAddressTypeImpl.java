package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicWebservices.WebserviceAddressType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class WebserviceAddressTypeImpl extends XmlComplexContentImpl implements WebserviceAddressType {
   private static final long serialVersionUID = 1L;
   private static final QName WEBSERVICECONTEXTPATH$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "webservice-contextpath");
   private static final QName WEBSERVICESERVICEURI$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "webservice-serviceuri");

   public WebserviceAddressTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getWebserviceContextpath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(WEBSERVICECONTEXTPATH$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWebserviceContextpath(String webserviceContextpath) {
      this.generatedSetterHelperImpl(webserviceContextpath, WEBSERVICECONTEXTPATH$0, 0, (short)1);
   }

   public String addNewWebserviceContextpath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(WEBSERVICECONTEXTPATH$0);
         return target;
      }
   }

   public String getWebserviceServiceuri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(WEBSERVICESERVICEURI$2, 0);
         return target == null ? null : target;
      }
   }

   public void setWebserviceServiceuri(String webserviceServiceuri) {
      this.generatedSetterHelperImpl(webserviceServiceuri, WEBSERVICESERVICEURI$2, 0, (short)1);
   }

   public String addNewWebserviceServiceuri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(WEBSERVICESERVICEURI$2);
         return target;
      }
   }
}
