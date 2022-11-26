package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.HandlerChainType;
import org.jcp.xmlns.xml.ns.javaee.HandlerType;
import org.jcp.xmlns.xml.ns.javaee.ProtocolBindingListType;
import org.jcp.xmlns.xml.ns.javaee.QnamePattern;

public class HandlerChainTypeImpl extends XmlComplexContentImpl implements HandlerChainType {
   private static final long serialVersionUID = 1L;
   private static final QName SERVICENAMEPATTERN$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-name-pattern");
   private static final QName PORTNAMEPATTERN$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "port-name-pattern");
   private static final QName PROTOCOLBINDINGS$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "protocol-bindings");
   private static final QName HANDLER$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "handler");
   private static final QName ID$8 = new QName("", "id");

   public HandlerChainTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getServiceNamePattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SERVICENAMEPATTERN$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public QnamePattern xgetServiceNamePattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnamePattern target = null;
         target = (QnamePattern)this.get_store().find_element_user(SERVICENAMEPATTERN$0, 0);
         return target;
      }
   }

   public boolean isSetServiceNamePattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICENAMEPATTERN$0) != 0;
      }
   }

   public void setServiceNamePattern(String serviceNamePattern) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SERVICENAMEPATTERN$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SERVICENAMEPATTERN$0);
         }

         target.setStringValue(serviceNamePattern);
      }
   }

   public void xsetServiceNamePattern(QnamePattern serviceNamePattern) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnamePattern target = null;
         target = (QnamePattern)this.get_store().find_element_user(SERVICENAMEPATTERN$0, 0);
         if (target == null) {
            target = (QnamePattern)this.get_store().add_element_user(SERVICENAMEPATTERN$0);
         }

         target.set(serviceNamePattern);
      }
   }

   public void unsetServiceNamePattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICENAMEPATTERN$0, 0);
      }
   }

   public String getPortNamePattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PORTNAMEPATTERN$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public QnamePattern xgetPortNamePattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnamePattern target = null;
         target = (QnamePattern)this.get_store().find_element_user(PORTNAMEPATTERN$2, 0);
         return target;
      }
   }

   public boolean isSetPortNamePattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORTNAMEPATTERN$2) != 0;
      }
   }

   public void setPortNamePattern(String portNamePattern) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PORTNAMEPATTERN$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PORTNAMEPATTERN$2);
         }

         target.setStringValue(portNamePattern);
      }
   }

   public void xsetPortNamePattern(QnamePattern portNamePattern) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnamePattern target = null;
         target = (QnamePattern)this.get_store().find_element_user(PORTNAMEPATTERN$2, 0);
         if (target == null) {
            target = (QnamePattern)this.get_store().add_element_user(PORTNAMEPATTERN$2);
         }

         target.set(portNamePattern);
      }
   }

   public void unsetPortNamePattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTNAMEPATTERN$2, 0);
      }
   }

   public List getProtocolBindings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROTOCOLBINDINGS$4, 0);
         return target == null ? null : target.getListValue();
      }
   }

   public ProtocolBindingListType xgetProtocolBindings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProtocolBindingListType target = null;
         target = (ProtocolBindingListType)this.get_store().find_element_user(PROTOCOLBINDINGS$4, 0);
         return target;
      }
   }

   public boolean isSetProtocolBindings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROTOCOLBINDINGS$4) != 0;
      }
   }

   public void setProtocolBindings(List protocolBindings) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROTOCOLBINDINGS$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PROTOCOLBINDINGS$4);
         }

         target.setListValue(protocolBindings);
      }
   }

   public void xsetProtocolBindings(ProtocolBindingListType protocolBindings) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProtocolBindingListType target = null;
         target = (ProtocolBindingListType)this.get_store().find_element_user(PROTOCOLBINDINGS$4, 0);
         if (target == null) {
            target = (ProtocolBindingListType)this.get_store().add_element_user(PROTOCOLBINDINGS$4);
         }

         target.set(protocolBindings);
      }
   }

   public void unsetProtocolBindings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROTOCOLBINDINGS$4, 0);
      }
   }

   public HandlerType[] getHandlerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HANDLER$6, targetList);
         HandlerType[] result = new HandlerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public HandlerType getHandlerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerType target = null;
         target = (HandlerType)this.get_store().find_element_user(HANDLER$6, i);
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
         return this.get_store().count_elements(HANDLER$6);
      }
   }

   public void setHandlerArray(HandlerType[] handlerArray) {
      this.check_orphaned();
      this.arraySetterHelper(handlerArray, HANDLER$6);
   }

   public void setHandlerArray(int i, HandlerType handler) {
      this.generatedSetterHelperImpl(handler, HANDLER$6, i, (short)2);
   }

   public HandlerType insertNewHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerType target = null;
         target = (HandlerType)this.get_store().insert_element_user(HANDLER$6, i);
         return target;
      }
   }

   public HandlerType addNewHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerType target = null;
         target = (HandlerType)this.get_store().add_element_user(HANDLER$6);
         return target;
      }
   }

   public void removeHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HANDLER$6, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$8) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$8);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$8);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$8);
      }
   }
}
