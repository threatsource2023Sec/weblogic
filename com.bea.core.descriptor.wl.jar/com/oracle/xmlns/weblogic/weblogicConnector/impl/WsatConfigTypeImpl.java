package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.weblogicConnector.TransactionFlowType;
import com.oracle.xmlns.weblogic.weblogicConnector.TransactionVersion;
import com.oracle.xmlns.weblogic.weblogicConnector.WsatConfigType;
import javax.xml.namespace.QName;

public class WsatConfigTypeImpl extends XmlComplexContentImpl implements WsatConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName ENABLED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "enabled");
   private static final QName FLOWTYPE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "flow-type");
   private static final QName VERSION$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "version");

   public WsatConfigTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLED$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLED$0, 0);
         return target;
      }
   }

   public boolean isSetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLED$0) != 0;
      }
   }

   public void setEnabled(boolean enabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLED$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENABLED$0);
         }

         target.setBooleanValue(enabled);
      }
   }

   public void xsetEnabled(XmlBoolean enabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLED$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ENABLED$0);
         }

         target.set(enabled);
      }
   }

   public void unsetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLED$0, 0);
      }
   }

   public TransactionFlowType.Enum getFlowType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWTYPE$2, 0);
         return target == null ? null : (TransactionFlowType.Enum)target.getEnumValue();
      }
   }

   public TransactionFlowType xgetFlowType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionFlowType target = null;
         target = (TransactionFlowType)this.get_store().find_element_user(FLOWTYPE$2, 0);
         return target;
      }
   }

   public boolean isSetFlowType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLOWTYPE$2) != 0;
      }
   }

   public void setFlowType(TransactionFlowType.Enum flowType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWTYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FLOWTYPE$2);
         }

         target.setEnumValue(flowType);
      }
   }

   public void xsetFlowType(TransactionFlowType flowType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionFlowType target = null;
         target = (TransactionFlowType)this.get_store().find_element_user(FLOWTYPE$2, 0);
         if (target == null) {
            target = (TransactionFlowType)this.get_store().add_element_user(FLOWTYPE$2);
         }

         target.set(flowType);
      }
   }

   public void unsetFlowType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOWTYPE$2, 0);
      }
   }

   public TransactionVersion.Enum getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSION$4, 0);
         return target == null ? null : (TransactionVersion.Enum)target.getEnumValue();
      }
   }

   public TransactionVersion xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionVersion target = null;
         target = (TransactionVersion)this.get_store().find_element_user(VERSION$4, 0);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERSION$4) != 0;
      }
   }

   public void setVersion(TransactionVersion.Enum version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSION$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VERSION$4);
         }

         target.setEnumValue(version);
      }
   }

   public void xsetVersion(TransactionVersion version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionVersion target = null;
         target = (TransactionVersion)this.get_store().find_element_user(VERSION$4, 0);
         if (target == null) {
            target = (TransactionVersion)this.get_store().add_element_user(VERSION$4);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERSION$4, 0);
      }
   }
}
