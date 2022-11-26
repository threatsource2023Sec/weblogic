package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicWebApp.WeblogicWebAppDocument;
import com.oracle.xmlns.weblogic.weblogicWebApp.WeblogicWebAppType;
import javax.xml.namespace.QName;

public class WeblogicWebAppDocumentImpl extends XmlComplexContentImpl implements WeblogicWebAppDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICWEBAPP$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "weblogic-web-app");

   public WeblogicWebAppDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicWebAppType getWeblogicWebApp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicWebAppType target = null;
         target = (WeblogicWebAppType)this.get_store().find_element_user(WEBLOGICWEBAPP$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicWebApp(WeblogicWebAppType weblogicWebApp) {
      this.generatedSetterHelperImpl(weblogicWebApp, WEBLOGICWEBAPP$0, 0, (short)1);
   }

   public WeblogicWebAppType addNewWeblogicWebApp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicWebAppType target = null;
         target = (WeblogicWebAppType)this.get_store().add_element_user(WEBLOGICWEBAPP$0);
         return target;
      }
   }
}
