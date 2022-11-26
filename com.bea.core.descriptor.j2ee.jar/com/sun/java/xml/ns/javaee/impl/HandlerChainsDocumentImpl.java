package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.javaee.HandlerChainsDocument;
import com.sun.java.xml.ns.javaee.HandlerChainsType;
import javax.xml.namespace.QName;

public class HandlerChainsDocumentImpl extends XmlComplexContentImpl implements HandlerChainsDocument {
   private static final long serialVersionUID = 1L;
   private static final QName HANDLERCHAINS$0 = new QName("http://java.sun.com/xml/ns/javaee", "handler-chains");

   public HandlerChainsDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public HandlerChainsType getHandlerChains() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerChainsType target = null;
         target = (HandlerChainsType)this.get_store().find_element_user(HANDLERCHAINS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setHandlerChains(HandlerChainsType handlerChains) {
      this.generatedSetterHelperImpl(handlerChains, HANDLERCHAINS$0, 0, (short)1);
   }

   public HandlerChainsType addNewHandlerChains() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerChainsType target = null;
         target = (HandlerChainsType)this.get_store().add_element_user(HANDLERCHAINS$0);
         return target;
      }
   }
}
