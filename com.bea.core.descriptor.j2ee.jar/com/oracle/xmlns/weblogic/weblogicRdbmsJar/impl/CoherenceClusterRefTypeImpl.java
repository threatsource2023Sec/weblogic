package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CoherenceClusterRefType;
import javax.xml.namespace.QName;

public class CoherenceClusterRefTypeImpl extends XmlComplexContentImpl implements CoherenceClusterRefType {
   private static final long serialVersionUID = 1L;
   private static final QName COHERENCECLUSTERNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "coherence-cluster-name");

   public CoherenceClusterRefTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getCoherenceClusterName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COHERENCECLUSTERNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCoherenceClusterName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COHERENCECLUSTERNAME$0, 0);
         return target;
      }
   }

   public boolean isSetCoherenceClusterName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCECLUSTERNAME$0) != 0;
      }
   }

   public void setCoherenceClusterName(String coherenceClusterName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COHERENCECLUSTERNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COHERENCECLUSTERNAME$0);
         }

         target.setStringValue(coherenceClusterName);
      }
   }

   public void xsetCoherenceClusterName(XmlString coherenceClusterName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COHERENCECLUSTERNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COHERENCECLUSTERNAME$0);
         }

         target.set(coherenceClusterName);
      }
   }

   public void unsetCoherenceClusterName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCECLUSTERNAME$0, 0);
      }
   }
}
