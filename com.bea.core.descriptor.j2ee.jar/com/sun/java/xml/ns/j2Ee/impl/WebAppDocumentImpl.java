package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.j2Ee.WebAppDocument;
import com.sun.java.xml.ns.j2Ee.WebAppType;
import javax.xml.namespace.QName;

public class WebAppDocumentImpl extends XmlComplexContentImpl implements WebAppDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBAPP$0 = new QName("http://java.sun.com/xml/ns/j2ee", "web-app");

   public WebAppDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WebAppType getWebApp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebAppType target = null;
         target = (WebAppType)this.get_store().find_element_user(WEBAPP$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWebApp(WebAppType webApp) {
      this.generatedSetterHelperImpl(webApp, WEBAPP$0, 0, (short)1);
   }

   public WebAppType addNewWebApp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebAppType target = null;
         target = (WebAppType)this.get_store().add_element_user(WEBAPP$0);
         return target;
      }
   }
}
