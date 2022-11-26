package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplication.WeblogicApplicationDocument;
import com.oracle.xmlns.weblogic.weblogicApplication.WeblogicApplicationType;
import javax.xml.namespace.QName;

public class WeblogicApplicationDocumentImpl extends XmlComplexContentImpl implements WeblogicApplicationDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICAPPLICATION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "weblogic-application");

   public WeblogicApplicationDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicApplicationType getWeblogicApplication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicApplicationType target = null;
         target = (WeblogicApplicationType)this.get_store().find_element_user(WEBLOGICAPPLICATION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicApplication(WeblogicApplicationType weblogicApplication) {
      this.generatedSetterHelperImpl(weblogicApplication, WEBLOGICAPPLICATION$0, 0, (short)1);
   }

   public WeblogicApplicationType addNewWeblogicApplication() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicApplicationType target = null;
         target = (WeblogicApplicationType)this.get_store().add_element_user(WEBLOGICAPPLICATION$0);
         return target;
      }
   }
}
