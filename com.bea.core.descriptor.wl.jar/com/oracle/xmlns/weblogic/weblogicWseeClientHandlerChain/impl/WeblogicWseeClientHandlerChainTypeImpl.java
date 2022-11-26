package com.oracle.xmlns.weblogic.weblogicWseeClientHandlerChain.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWseeClientHandlerChain.WeblogicWseeClientHandlerChainType;
import com.sun.java.xml.ns.j2Ee.ServiceRefHandlerType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicWseeClientHandlerChainTypeImpl extends XmlComplexContentImpl implements WeblogicWseeClientHandlerChainType {
   private static final long serialVersionUID = 1L;
   private static final QName HANDLER$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-wsee-clientHandlerChain", "handler");
   private static final QName VERSION$2 = new QName("", "version");

   public WeblogicWseeClientHandlerChainTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ServiceRefHandlerType[] getHandlerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HANDLER$0, targetList);
         ServiceRefHandlerType[] result = new ServiceRefHandlerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefHandlerType getHandlerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefHandlerType target = null;
         target = (ServiceRefHandlerType)this.get_store().find_element_user(HANDLER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfHandlerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HANDLER$0);
      }
   }

   public void setHandlerArray(ServiceRefHandlerType[] handlerArray) {
      this.check_orphaned();
      this.arraySetterHelper(handlerArray, HANDLER$0);
   }

   public void setHandlerArray(int i, ServiceRefHandlerType handler) {
      this.generatedSetterHelperImpl(handler, HANDLER$0, i, (short)2);
   }

   public ServiceRefHandlerType insertNewHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefHandlerType target = null;
         target = (ServiceRefHandlerType)this.get_store().insert_element_user(HANDLER$0, i);
         return target;
      }
   }

   public ServiceRefHandlerType addNewHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefHandlerType target = null;
         target = (ServiceRefHandlerType)this.get_store().add_element_user(HANDLER$0);
         return target;
      }
   }

   public void removeHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HANDLER$0, i);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$2);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$2) != null;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$2);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$2);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$2);
      }
   }
}
