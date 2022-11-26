package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ProxyManagerImplType;
import javax.xml.namespace.QName;

public class ProxyManagerImplTypeImpl extends ProxyManagerTypeImpl implements ProxyManagerImplType {
   private static final long serialVersionUID = 1L;
   private static final QName ASSERTALLOWEDTYPE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "assert-allowed-type");
   private static final QName TRACKCHANGES$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "track-changes");

   public ProxyManagerImplTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getAssertAllowedType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ASSERTALLOWEDTYPE$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAssertAllowedType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ASSERTALLOWEDTYPE$0, 0);
         return target;
      }
   }

   public boolean isSetAssertAllowedType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ASSERTALLOWEDTYPE$0) != 0;
      }
   }

   public void setAssertAllowedType(boolean assertAllowedType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ASSERTALLOWEDTYPE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ASSERTALLOWEDTYPE$0);
         }

         target.setBooleanValue(assertAllowedType);
      }
   }

   public void xsetAssertAllowedType(XmlBoolean assertAllowedType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ASSERTALLOWEDTYPE$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ASSERTALLOWEDTYPE$0);
         }

         target.set(assertAllowedType);
      }
   }

   public void unsetAssertAllowedType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ASSERTALLOWEDTYPE$0, 0);
      }
   }

   public boolean getTrackChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRACKCHANGES$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetTrackChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(TRACKCHANGES$2, 0);
         return target;
      }
   }

   public boolean isSetTrackChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRACKCHANGES$2) != 0;
      }
   }

   public void setTrackChanges(boolean trackChanges) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRACKCHANGES$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRACKCHANGES$2);
         }

         target.setBooleanValue(trackChanges);
      }
   }

   public void xsetTrackChanges(XmlBoolean trackChanges) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(TRACKCHANGES$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(TRACKCHANGES$2);
         }

         target.set(trackChanges);
      }
   }

   public void unsetTrackChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRACKCHANGES$2, 0);
      }
   }
}
