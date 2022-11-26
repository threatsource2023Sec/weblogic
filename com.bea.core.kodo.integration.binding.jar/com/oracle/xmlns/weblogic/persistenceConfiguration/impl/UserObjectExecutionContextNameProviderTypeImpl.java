package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.UserObjectExecutionContextNameProviderType;
import javax.xml.namespace.QName;

public class UserObjectExecutionContextNameProviderTypeImpl extends XmlComplexContentImpl implements UserObjectExecutionContextNameProviderType {
   private static final long serialVersionUID = 1L;
   private static final QName KEY$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "key");

   public UserObjectExecutionContextNameProviderTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(KEY$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(KEY$0, 0);
         return target;
      }
   }

   public void setKey(String key) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(KEY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(KEY$0);
         }

         target.setStringValue(key);
      }
   }

   public void xsetKey(XmlString key) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(KEY$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(KEY$0);
         }

         target.set(key);
      }
   }
}
