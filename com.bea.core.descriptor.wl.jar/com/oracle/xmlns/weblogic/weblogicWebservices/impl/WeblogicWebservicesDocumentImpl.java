package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicWebservices.WeblogicWebservicesDocument;
import com.oracle.xmlns.weblogic.weblogicWebservices.WeblogicWebservicesType;
import javax.xml.namespace.QName;

public class WeblogicWebservicesDocumentImpl extends XmlComplexContentImpl implements WeblogicWebservicesDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICWEBSERVICES$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "weblogic-webservices");

   public WeblogicWebservicesDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicWebservicesType getWeblogicWebservices() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicWebservicesType target = null;
         target = (WeblogicWebservicesType)this.get_store().find_element_user(WEBLOGICWEBSERVICES$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicWebservices(WeblogicWebservicesType weblogicWebservices) {
      this.generatedSetterHelperImpl(weblogicWebservices, WEBLOGICWEBSERVICES$0, 0, (short)1);
   }

   public WeblogicWebservicesType addNewWeblogicWebservices() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicWebservicesType target = null;
         target = (WeblogicWebservicesType)this.get_store().add_element_user(WEBLOGICWEBSERVICES$0);
         return target;
      }
   }
}
