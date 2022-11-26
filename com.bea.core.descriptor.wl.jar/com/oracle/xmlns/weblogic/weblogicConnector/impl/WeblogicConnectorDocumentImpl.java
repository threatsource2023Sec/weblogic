package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.WeblogicConnectorDocument;
import com.oracle.xmlns.weblogic.weblogicConnector.WeblogicConnectorType;
import javax.xml.namespace.QName;

public class WeblogicConnectorDocumentImpl extends XmlComplexContentImpl implements WeblogicConnectorDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICCONNECTOR$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "weblogic-connector");

   public WeblogicConnectorDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicConnectorType getWeblogicConnector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicConnectorType target = null;
         target = (WeblogicConnectorType)this.get_store().find_element_user(WEBLOGICCONNECTOR$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicConnector(WeblogicConnectorType weblogicConnector) {
      this.generatedSetterHelperImpl(weblogicConnector, WEBLOGICCONNECTOR$0, 0, (short)1);
   }

   public WeblogicConnectorType addNewWeblogicConnector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicConnectorType target = null;
         target = (WeblogicConnectorType)this.get_store().add_element_user(WEBLOGICCONNECTOR$0);
         return target;
      }
   }
}
