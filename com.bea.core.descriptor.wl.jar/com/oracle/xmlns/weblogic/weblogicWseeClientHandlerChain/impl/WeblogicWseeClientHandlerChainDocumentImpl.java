package com.oracle.xmlns.weblogic.weblogicWseeClientHandlerChain.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicWseeClientHandlerChain.WeblogicWseeClientHandlerChainDocument;
import com.oracle.xmlns.weblogic.weblogicWseeClientHandlerChain.WeblogicWseeClientHandlerChainType;
import javax.xml.namespace.QName;

public class WeblogicWseeClientHandlerChainDocumentImpl extends XmlComplexContentImpl implements WeblogicWseeClientHandlerChainDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICWSEECLIENTHANDLERCHAIN$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-wsee-clientHandlerChain", "weblogic-wsee-clientHandlerChain");

   public WeblogicWseeClientHandlerChainDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicWseeClientHandlerChainType getWeblogicWseeClientHandlerChain() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicWseeClientHandlerChainType target = null;
         target = (WeblogicWseeClientHandlerChainType)this.get_store().find_element_user(WEBLOGICWSEECLIENTHANDLERCHAIN$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWeblogicWseeClientHandlerChain(WeblogicWseeClientHandlerChainType weblogicWseeClientHandlerChain) {
      this.generatedSetterHelperImpl(weblogicWseeClientHandlerChain, WEBLOGICWSEECLIENTHANDLERCHAIN$0, 0, (short)1);
   }

   public WeblogicWseeClientHandlerChainType addNewWeblogicWseeClientHandlerChain() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicWseeClientHandlerChainType target = null;
         target = (WeblogicWseeClientHandlerChainType)this.get_store().add_element_user(WEBLOGICWSEECLIENTHANDLERCHAIN$0);
         return target;
      }
   }
}
