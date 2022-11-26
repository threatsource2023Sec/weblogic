package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.WebAppDocument;
import org.jcp.xmlns.xml.ns.javaee.WebAppType;

public class WebAppDocumentImpl extends XmlComplexContentImpl implements WebAppDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBAPP$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "web-app");

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
