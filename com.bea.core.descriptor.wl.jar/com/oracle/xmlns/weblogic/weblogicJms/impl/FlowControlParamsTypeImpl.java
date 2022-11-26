package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.weblogicJms.FlowControlParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.OneWaySendModeType;
import javax.xml.namespace.QName;

public class FlowControlParamsTypeImpl extends XmlComplexContentImpl implements FlowControlParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName FLOWMINIMUM$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "flow-minimum");
   private static final QName FLOWMAXIMUM$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "flow-maximum");
   private static final QName FLOWINTERVAL$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "flow-interval");
   private static final QName FLOWSTEPS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "flow-steps");
   private static final QName FLOWCONTROLENABLED$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "flow-control-enabled");
   private static final QName ONEWAYSENDMODE$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "one-way-send-mode");
   private static final QName ONEWAYSENDWINDOWSIZE$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "one-way-send-window-size");

   public FlowControlParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getFlowMinimum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWMINIMUM$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetFlowMinimum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FLOWMINIMUM$0, 0);
         return target;
      }
   }

   public boolean isSetFlowMinimum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLOWMINIMUM$0) != 0;
      }
   }

   public void setFlowMinimum(int flowMinimum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWMINIMUM$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FLOWMINIMUM$0);
         }

         target.setIntValue(flowMinimum);
      }
   }

   public void xsetFlowMinimum(XmlInt flowMinimum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FLOWMINIMUM$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(FLOWMINIMUM$0);
         }

         target.set(flowMinimum);
      }
   }

   public void unsetFlowMinimum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOWMINIMUM$0, 0);
      }
   }

   public int getFlowMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWMAXIMUM$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetFlowMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FLOWMAXIMUM$2, 0);
         return target;
      }
   }

   public boolean isSetFlowMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLOWMAXIMUM$2) != 0;
      }
   }

   public void setFlowMaximum(int flowMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWMAXIMUM$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FLOWMAXIMUM$2);
         }

         target.setIntValue(flowMaximum);
      }
   }

   public void xsetFlowMaximum(XmlInt flowMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FLOWMAXIMUM$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(FLOWMAXIMUM$2);
         }

         target.set(flowMaximum);
      }
   }

   public void unsetFlowMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOWMAXIMUM$2, 0);
      }
   }

   public int getFlowInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWINTERVAL$4, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetFlowInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FLOWINTERVAL$4, 0);
         return target;
      }
   }

   public boolean isSetFlowInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLOWINTERVAL$4) != 0;
      }
   }

   public void setFlowInterval(int flowInterval) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWINTERVAL$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FLOWINTERVAL$4);
         }

         target.setIntValue(flowInterval);
      }
   }

   public void xsetFlowInterval(XmlInt flowInterval) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FLOWINTERVAL$4, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(FLOWINTERVAL$4);
         }

         target.set(flowInterval);
      }
   }

   public void unsetFlowInterval() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOWINTERVAL$4, 0);
      }
   }

   public int getFlowSteps() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWSTEPS$6, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetFlowSteps() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FLOWSTEPS$6, 0);
         return target;
      }
   }

   public boolean isSetFlowSteps() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLOWSTEPS$6) != 0;
      }
   }

   public void setFlowSteps(int flowSteps) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWSTEPS$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FLOWSTEPS$6);
         }

         target.setIntValue(flowSteps);
      }
   }

   public void xsetFlowSteps(XmlInt flowSteps) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FLOWSTEPS$6, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(FLOWSTEPS$6);
         }

         target.set(flowSteps);
      }
   }

   public void unsetFlowSteps() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOWSTEPS$6, 0);
      }
   }

   public boolean getFlowControlEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWCONTROLENABLED$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetFlowControlEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(FLOWCONTROLENABLED$8, 0);
         return target;
      }
   }

   public boolean isSetFlowControlEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLOWCONTROLENABLED$8) != 0;
      }
   }

   public void setFlowControlEnabled(boolean flowControlEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOWCONTROLENABLED$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FLOWCONTROLENABLED$8);
         }

         target.setBooleanValue(flowControlEnabled);
      }
   }

   public void xsetFlowControlEnabled(XmlBoolean flowControlEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(FLOWCONTROLENABLED$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(FLOWCONTROLENABLED$8);
         }

         target.set(flowControlEnabled);
      }
   }

   public void unsetFlowControlEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOWCONTROLENABLED$8, 0);
      }
   }

   public OneWaySendModeType.Enum getOneWaySendMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ONEWAYSENDMODE$10, 0);
         return target == null ? null : (OneWaySendModeType.Enum)target.getEnumValue();
      }
   }

   public OneWaySendModeType xgetOneWaySendMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OneWaySendModeType target = null;
         target = (OneWaySendModeType)this.get_store().find_element_user(ONEWAYSENDMODE$10, 0);
         return target;
      }
   }

   public boolean isSetOneWaySendMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ONEWAYSENDMODE$10) != 0;
      }
   }

   public void setOneWaySendMode(OneWaySendModeType.Enum oneWaySendMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ONEWAYSENDMODE$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ONEWAYSENDMODE$10);
         }

         target.setEnumValue(oneWaySendMode);
      }
   }

   public void xsetOneWaySendMode(OneWaySendModeType oneWaySendMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OneWaySendModeType target = null;
         target = (OneWaySendModeType)this.get_store().find_element_user(ONEWAYSENDMODE$10, 0);
         if (target == null) {
            target = (OneWaySendModeType)this.get_store().add_element_user(ONEWAYSENDMODE$10);
         }

         target.set(oneWaySendMode);
      }
   }

   public void unsetOneWaySendMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ONEWAYSENDMODE$10, 0);
      }
   }

   public int getOneWaySendWindowSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ONEWAYSENDWINDOWSIZE$12, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetOneWaySendWindowSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(ONEWAYSENDWINDOWSIZE$12, 0);
         return target;
      }
   }

   public boolean isSetOneWaySendWindowSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ONEWAYSENDWINDOWSIZE$12) != 0;
      }
   }

   public void setOneWaySendWindowSize(int oneWaySendWindowSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ONEWAYSENDWINDOWSIZE$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ONEWAYSENDWINDOWSIZE$12);
         }

         target.setIntValue(oneWaySendWindowSize);
      }
   }

   public void xsetOneWaySendWindowSize(XmlInt oneWaySendWindowSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(ONEWAYSENDWINDOWSIZE$12, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(ONEWAYSENDWINDOWSIZE$12);
         }

         target.set(oneWaySendWindowSize);
      }
   }

   public void unsetOneWaySendWindowSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ONEWAYSENDWINDOWSIZE$12, 0);
      }
   }
}
