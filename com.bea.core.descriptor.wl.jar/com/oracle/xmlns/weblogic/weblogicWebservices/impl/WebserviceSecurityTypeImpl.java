package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicWebservices.WebserviceSecurityType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class WebserviceSecurityTypeImpl extends XmlComplexContentImpl implements WebserviceSecurityType {
   private static final long serialVersionUID = 1L;
   private static final QName MBEANNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "mbean-name");

   public WebserviceSecurityTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getMbeanName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(MBEANNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMbeanName(String mbeanName) {
      this.generatedSetterHelperImpl(mbeanName, MBEANNAME$0, 0, (short)1);
   }

   public String addNewMbeanName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(MBEANNAME$0);
         return target;
      }
   }
}
