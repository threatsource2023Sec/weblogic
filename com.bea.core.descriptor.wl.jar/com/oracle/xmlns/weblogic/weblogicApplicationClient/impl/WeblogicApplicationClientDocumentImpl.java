package com.oracle.xmlns.weblogic.weblogicApplicationClient.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.WeblogicApplicationClientDocument;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.WeblogicApplicationClientType;
import javax.xml.namespace.QName;

public class WeblogicApplicationClientDocumentImpl extends XmlComplexContentImpl implements WeblogicApplicationClientDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICAPPLICATIONCLIENT$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "weblogic-application-client");

   public WeblogicApplicationClientDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicApplicationClientType getWeblogicApplicationClient() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicApplicationClientType target = null;
         target = (WeblogicApplicationClientType)this.get_store().find_element_user(WEBLOGICAPPLICATIONCLIENT$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicApplicationClient(WeblogicApplicationClientType weblogicApplicationClient) {
      this.generatedSetterHelperImpl(weblogicApplicationClient, WEBLOGICAPPLICATIONCLIENT$0, 0, (short)1);
   }

   public WeblogicApplicationClientType addNewWeblogicApplicationClient() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicApplicationClientType target = null;
         target = (WeblogicApplicationClientType)this.get_store().add_element_user(WEBLOGICAPPLICATIONCLIENT$0);
         return target;
      }
   }
}
