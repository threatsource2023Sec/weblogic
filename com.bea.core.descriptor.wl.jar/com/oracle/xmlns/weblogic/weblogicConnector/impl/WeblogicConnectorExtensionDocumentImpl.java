package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.WeblogicConnectorExtensionDocument;
import com.oracle.xmlns.weblogic.weblogicConnector.WeblogicConnectorExtensionType;
import javax.xml.namespace.QName;

public class WeblogicConnectorExtensionDocumentImpl extends XmlComplexContentImpl implements WeblogicConnectorExtensionDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICCONNECTOREXTENSION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "weblogic-connector-extension");

   public WeblogicConnectorExtensionDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicConnectorExtensionType getWeblogicConnectorExtension() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicConnectorExtensionType target = null;
         target = (WeblogicConnectorExtensionType)this.get_store().find_element_user(WEBLOGICCONNECTOREXTENSION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicConnectorExtension(WeblogicConnectorExtensionType weblogicConnectorExtension) {
      this.generatedSetterHelperImpl(weblogicConnectorExtension, WEBLOGICCONNECTOREXTENSION$0, 0, (short)1);
   }

   public WeblogicConnectorExtensionType addNewWeblogicConnectorExtension() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicConnectorExtensionType target = null;
         target = (WeblogicConnectorExtensionType)this.get_store().add_element_user(WEBLOGICCONNECTOREXTENSION$0);
         return target;
      }
   }
}
