package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.OracleDictionaryType;
import javax.xml.namespace.QName;

public class OracleDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements OracleDictionaryType {
   private static final long serialVersionUID = 1L;
   private static final QName USETRIGGERSFORAUTOASSIGN$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-triggers-for-auto-assign");
   private static final QName AUTOASSIGNSEQUENCENAME$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "auto-assign-sequence-name");
   private static final QName USESETFORMOFUSEFORUNICODE$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-set-form-of-use-for-unicode");

   public OracleDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getUseTriggersForAutoAssign() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USETRIGGERSFORAUTOASSIGN$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseTriggersForAutoAssign() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USETRIGGERSFORAUTOASSIGN$0, 0);
         return target;
      }
   }

   public boolean isSetUseTriggersForAutoAssign() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USETRIGGERSFORAUTOASSIGN$0) != 0;
      }
   }

   public void setUseTriggersForAutoAssign(boolean useTriggersForAutoAssign) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USETRIGGERSFORAUTOASSIGN$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USETRIGGERSFORAUTOASSIGN$0);
         }

         target.setBooleanValue(useTriggersForAutoAssign);
      }
   }

   public void xsetUseTriggersForAutoAssign(XmlBoolean useTriggersForAutoAssign) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USETRIGGERSFORAUTOASSIGN$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USETRIGGERSFORAUTOASSIGN$0);
         }

         target.set(useTriggersForAutoAssign);
      }
   }

   public void unsetUseTriggersForAutoAssign() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USETRIGGERSFORAUTOASSIGN$0, 0);
      }
   }

   public boolean getAutoAssignSequenceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTOASSIGNSEQUENCENAME$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAutoAssignSequenceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(AUTOASSIGNSEQUENCENAME$2, 0);
         return target;
      }
   }

   public boolean isSetAutoAssignSequenceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTOASSIGNSEQUENCENAME$2) != 0;
      }
   }

   public void setAutoAssignSequenceName(boolean autoAssignSequenceName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTOASSIGNSEQUENCENAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(AUTOASSIGNSEQUENCENAME$2);
         }

         target.setBooleanValue(autoAssignSequenceName);
      }
   }

   public void xsetAutoAssignSequenceName(XmlBoolean autoAssignSequenceName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(AUTOASSIGNSEQUENCENAME$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(AUTOASSIGNSEQUENCENAME$2);
         }

         target.set(autoAssignSequenceName);
      }
   }

   public void unsetAutoAssignSequenceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTOASSIGNSEQUENCENAME$2, 0);
      }
   }

   public boolean getUseSetFormOfUseForUnicode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESETFORMOFUSEFORUNICODE$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseSetFormOfUseForUnicode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESETFORMOFUSEFORUNICODE$4, 0);
         return target;
      }
   }

   public boolean isSetUseSetFormOfUseForUnicode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USESETFORMOFUSEFORUNICODE$4) != 0;
      }
   }

   public void setUseSetFormOfUseForUnicode(boolean useSetFormOfUseForUnicode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESETFORMOFUSEFORUNICODE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USESETFORMOFUSEFORUNICODE$4);
         }

         target.setBooleanValue(useSetFormOfUseForUnicode);
      }
   }

   public void xsetUseSetFormOfUseForUnicode(XmlBoolean useSetFormOfUseForUnicode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESETFORMOFUSEFORUNICODE$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USESETFORMOFUSEFORUNICODE$4);
         }

         target.set(useSetFormOfUseForUnicode);
      }
   }

   public void unsetUseSetFormOfUseForUnicode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USESETFORMOFUSEFORUNICODE$4, 0);
      }
   }
}
