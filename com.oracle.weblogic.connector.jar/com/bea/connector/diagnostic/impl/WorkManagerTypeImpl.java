package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.WorkManagerType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class WorkManagerTypeImpl extends XmlComplexContentImpl implements WorkManagerType {
   private static final long serialVersionUID = 1L;
   private static final QName WORKMANAGERNAME$0 = new QName("http://www.bea.com/connector/diagnostic", "workManagerName");

   public WorkManagerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getWorkManagerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WORKMANAGERNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetWorkManagerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(WORKMANAGERNAME$0, 0);
         return target;
      }
   }

   public void setWorkManagerName(String workManagerName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WORKMANAGERNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(WORKMANAGERNAME$0);
         }

         target.setStringValue(workManagerName);
      }
   }

   public void xsetWorkManagerName(XmlString workManagerName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(WORKMANAGERNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(WORKMANAGERNAME$0);
         }

         target.set(workManagerName);
      }
   }
}
