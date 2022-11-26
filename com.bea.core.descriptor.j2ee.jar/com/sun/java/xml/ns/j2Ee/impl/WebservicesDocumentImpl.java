package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.j2Ee.WebservicesDocument;
import com.sun.java.xml.ns.j2Ee.WebservicesType;
import javax.xml.namespace.QName;

public class WebservicesDocumentImpl extends XmlComplexContentImpl implements WebservicesDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBSERVICES$0 = new QName("http://java.sun.com/xml/ns/j2ee", "webservices");

   public WebservicesDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WebservicesType getWebservices() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebservicesType target = null;
         target = (WebservicesType)this.get_store().find_element_user(WEBSERVICES$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWebservices(WebservicesType webservices) {
      this.generatedSetterHelperImpl(webservices, WEBSERVICES$0, 0, (short)1);
   }

   public WebservicesType addNewWebservices() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebservicesType target = null;
         target = (WebservicesType)this.get_store().add_element_user(WEBSERVICES$0);
         return target;
      }
   }
}
