package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JmxDocument;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JmxType;
import javax.xml.namespace.QName;

public class JmxDocumentImpl extends XmlComplexContentImpl implements JmxDocument {
   private static final long serialVersionUID = 1L;
   private static final QName JMX$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "jmx");

   public JmxDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public JmxType getJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmxType target = null;
         target = (JmxType)this.get_store().find_element_user(JMX$0, 0);
         return target == null ? null : target;
      }
   }

   public void setJmx(JmxType jmx) {
      this.generatedSetterHelperImpl(jmx, JMX$0, 0, (short)1);
   }

   public JmxType addNewJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmxType target = null;
         target = (JmxType)this.get_store().add_element_user(JMX$0);
         return target;
      }
   }
}
