package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.ClientSafType;
import com.oracle.xmlns.weblogic.weblogicJms.WeblogicClientJmsDocument;
import javax.xml.namespace.QName;

public class WeblogicClientJmsDocumentImpl extends XmlComplexContentImpl implements WeblogicClientJmsDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICCLIENTJMS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "weblogic-client-jms");

   public WeblogicClientJmsDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ClientSafType getWeblogicClientJms() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientSafType target = null;
         target = (ClientSafType)this.get_store().find_element_user(WEBLOGICCLIENTJMS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicClientJms(ClientSafType weblogicClientJms) {
      this.generatedSetterHelperImpl(weblogicClientJms, WEBLOGICCLIENTJMS$0, 0, (short)1);
   }

   public ClientSafType addNewWeblogicClientJms() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientSafType target = null;
         target = (ClientSafType)this.get_store().add_element_user(WEBLOGICCLIENTJMS$0);
         return target;
      }
   }
}
