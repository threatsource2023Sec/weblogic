package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.HandlerChainType;
import org.jcp.xmlns.xml.ns.javaee.HandlerChainsType;

public class HandlerChainsTypeImpl extends XmlComplexContentImpl implements HandlerChainsType {
   private static final long serialVersionUID = 1L;
   private static final QName HANDLERCHAIN$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "handler-chain");
   private static final QName ID$2 = new QName("", "id");

   public HandlerChainsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public HandlerChainType[] getHandlerChainArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HANDLERCHAIN$0, targetList);
         HandlerChainType[] result = new HandlerChainType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public HandlerChainType getHandlerChainArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerChainType target = null;
         target = (HandlerChainType)this.get_store().find_element_user(HANDLERCHAIN$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfHandlerChainArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HANDLERCHAIN$0);
      }
   }

   public void setHandlerChainArray(HandlerChainType[] handlerChainArray) {
      this.check_orphaned();
      this.arraySetterHelper(handlerChainArray, HANDLERCHAIN$0);
   }

   public void setHandlerChainArray(int i, HandlerChainType handlerChain) {
      this.generatedSetterHelperImpl(handlerChain, HANDLERCHAIN$0, i, (short)2);
   }

   public HandlerChainType insertNewHandlerChain(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerChainType target = null;
         target = (HandlerChainType)this.get_store().insert_element_user(HANDLERCHAIN$0, i);
         return target;
      }
   }

   public HandlerChainType addNewHandlerChain() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerChainType target = null;
         target = (HandlerChainType)this.get_store().add_element_user(HANDLERCHAIN$0);
         return target;
      }
   }

   public void removeHandlerChain(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HANDLERCHAIN$0, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$2) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$2);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$2);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$2);
      }
   }
}
