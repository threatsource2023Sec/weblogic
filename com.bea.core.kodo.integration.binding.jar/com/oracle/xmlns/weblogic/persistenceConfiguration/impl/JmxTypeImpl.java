package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.GuiJmxType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Jmx2JmxType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JmxType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LocalJmxType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Mx4J1JmxType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneJmxType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Wls81JmxType;
import javax.xml.namespace.QName;

public class JmxTypeImpl extends XmlComplexContentImpl implements JmxType {
   private static final long serialVersionUID = 1L;
   private static final QName NONEJMX$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "none-jmx");
   private static final QName LOCALJMX$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "local-jmx");
   private static final QName GUIJMX$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "gui-jmx");
   private static final QName JMX2JMX$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "jmx2-jmx");
   private static final QName MX4J1JMX$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "mx4j1-jmx");
   private static final QName WLS81JMX$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "wls81-jmx");

   public JmxTypeImpl(SchemaType sType) {
      super(sType);
   }

   public NoneJmxType getNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneJmxType target = null;
         target = (NoneJmxType)this.get_store().find_element_user(NONEJMX$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneJmxType target = null;
         target = (NoneJmxType)this.get_store().find_element_user(NONEJMX$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONEJMX$0) != 0;
      }
   }

   public void setNoneJmx(NoneJmxType noneJmx) {
      this.generatedSetterHelperImpl(noneJmx, NONEJMX$0, 0, (short)1);
   }

   public NoneJmxType addNewNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneJmxType target = null;
         target = (NoneJmxType)this.get_store().add_element_user(NONEJMX$0);
         return target;
      }
   }

   public void setNilNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneJmxType target = null;
         target = (NoneJmxType)this.get_store().find_element_user(NONEJMX$0, 0);
         if (target == null) {
            target = (NoneJmxType)this.get_store().add_element_user(NONEJMX$0);
         }

         target.setNil();
      }
   }

   public void unsetNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONEJMX$0, 0);
      }
   }

   public LocalJmxType getLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalJmxType target = null;
         target = (LocalJmxType)this.get_store().find_element_user(LOCALJMX$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalJmxType target = null;
         target = (LocalJmxType)this.get_store().find_element_user(LOCALJMX$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALJMX$2) != 0;
      }
   }

   public void setLocalJmx(LocalJmxType localJmx) {
      this.generatedSetterHelperImpl(localJmx, LOCALJMX$2, 0, (short)1);
   }

   public LocalJmxType addNewLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalJmxType target = null;
         target = (LocalJmxType)this.get_store().add_element_user(LOCALJMX$2);
         return target;
      }
   }

   public void setNilLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalJmxType target = null;
         target = (LocalJmxType)this.get_store().find_element_user(LOCALJMX$2, 0);
         if (target == null) {
            target = (LocalJmxType)this.get_store().add_element_user(LOCALJMX$2);
         }

         target.setNil();
      }
   }

   public void unsetLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALJMX$2, 0);
      }
   }

   public GuiJmxType getGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiJmxType target = null;
         target = (GuiJmxType)this.get_store().find_element_user(GUIJMX$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiJmxType target = null;
         target = (GuiJmxType)this.get_store().find_element_user(GUIJMX$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GUIJMX$4) != 0;
      }
   }

   public void setGuiJmx(GuiJmxType guiJmx) {
      this.generatedSetterHelperImpl(guiJmx, GUIJMX$4, 0, (short)1);
   }

   public GuiJmxType addNewGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiJmxType target = null;
         target = (GuiJmxType)this.get_store().add_element_user(GUIJMX$4);
         return target;
      }
   }

   public void setNilGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiJmxType target = null;
         target = (GuiJmxType)this.get_store().find_element_user(GUIJMX$4, 0);
         if (target == null) {
            target = (GuiJmxType)this.get_store().add_element_user(GUIJMX$4);
         }

         target.setNil();
      }
   }

   public void unsetGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GUIJMX$4, 0);
      }
   }

   public Jmx2JmxType getJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jmx2JmxType target = null;
         target = (Jmx2JmxType)this.get_store().find_element_user(JMX2JMX$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jmx2JmxType target = null;
         target = (Jmx2JmxType)this.get_store().find_element_user(JMX2JMX$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMX2JMX$6) != 0;
      }
   }

   public void setJmx2Jmx(Jmx2JmxType jmx2Jmx) {
      this.generatedSetterHelperImpl(jmx2Jmx, JMX2JMX$6, 0, (short)1);
   }

   public Jmx2JmxType addNewJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jmx2JmxType target = null;
         target = (Jmx2JmxType)this.get_store().add_element_user(JMX2JMX$6);
         return target;
      }
   }

   public void setNilJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jmx2JmxType target = null;
         target = (Jmx2JmxType)this.get_store().find_element_user(JMX2JMX$6, 0);
         if (target == null) {
            target = (Jmx2JmxType)this.get_store().add_element_user(JMX2JMX$6);
         }

         target.setNil();
      }
   }

   public void unsetJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMX2JMX$6, 0);
      }
   }

   public Mx4J1JmxType getMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mx4J1JmxType target = null;
         target = (Mx4J1JmxType)this.get_store().find_element_user(MX4J1JMX$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mx4J1JmxType target = null;
         target = (Mx4J1JmxType)this.get_store().find_element_user(MX4J1JMX$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MX4J1JMX$8) != 0;
      }
   }

   public void setMx4J1Jmx(Mx4J1JmxType mx4J1Jmx) {
      this.generatedSetterHelperImpl(mx4J1Jmx, MX4J1JMX$8, 0, (short)1);
   }

   public Mx4J1JmxType addNewMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mx4J1JmxType target = null;
         target = (Mx4J1JmxType)this.get_store().add_element_user(MX4J1JMX$8);
         return target;
      }
   }

   public void setNilMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mx4J1JmxType target = null;
         target = (Mx4J1JmxType)this.get_store().find_element_user(MX4J1JMX$8, 0);
         if (target == null) {
            target = (Mx4J1JmxType)this.get_store().add_element_user(MX4J1JMX$8);
         }

         target.setNil();
      }
   }

   public void unsetMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MX4J1JMX$8, 0);
      }
   }

   public Wls81JmxType getWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wls81JmxType target = null;
         target = (Wls81JmxType)this.get_store().find_element_user(WLS81JMX$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wls81JmxType target = null;
         target = (Wls81JmxType)this.get_store().find_element_user(WLS81JMX$10, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WLS81JMX$10) != 0;
      }
   }

   public void setWls81Jmx(Wls81JmxType wls81Jmx) {
      this.generatedSetterHelperImpl(wls81Jmx, WLS81JMX$10, 0, (short)1);
   }

   public Wls81JmxType addNewWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wls81JmxType target = null;
         target = (Wls81JmxType)this.get_store().add_element_user(WLS81JMX$10);
         return target;
      }
   }

   public void setNilWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wls81JmxType target = null;
         target = (Wls81JmxType)this.get_store().find_element_user(WLS81JMX$10, 0);
         if (target == null) {
            target = (Wls81JmxType)this.get_store().add_element_user(WLS81JMX$10);
         }

         target.setNil();
      }
   }

   public void unsetWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WLS81JMX$10, 0);
      }
   }
}
