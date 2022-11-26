package com.oracle.xmlns.weblogic.weblogicExtension.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicExtension.WeblogicExtensionDocument;
import com.oracle.xmlns.weblogic.weblogicExtension.WeblogicExtensionType;
import javax.xml.namespace.QName;

public class WeblogicExtensionDocumentImpl extends XmlComplexContentImpl implements WeblogicExtensionDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICEXTENSION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "weblogic-extension");

   public WeblogicExtensionDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicExtensionType getWeblogicExtension() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicExtensionType target = null;
         target = (WeblogicExtensionType)this.get_store().find_element_user(WEBLOGICEXTENSION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicExtension(WeblogicExtensionType weblogicExtension) {
      this.generatedSetterHelperImpl(weblogicExtension, WEBLOGICEXTENSION$0, 0, (short)1);
   }

   public WeblogicExtensionType addNewWeblogicExtension() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicExtensionType target = null;
         target = (WeblogicExtensionType)this.get_store().add_element_user(WEBLOGICEXTENSION$0);
         return target;
      }
   }
}
