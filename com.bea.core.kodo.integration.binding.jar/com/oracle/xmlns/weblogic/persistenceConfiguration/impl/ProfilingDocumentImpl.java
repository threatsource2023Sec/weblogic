package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ProfilingDocument;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ProfilingType;
import javax.xml.namespace.QName;

public class ProfilingDocumentImpl extends XmlComplexContentImpl implements ProfilingDocument {
   private static final long serialVersionUID = 1L;
   private static final QName PROFILING$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "profiling");

   public ProfilingDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ProfilingType getProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProfilingType target = null;
         target = (ProfilingType)this.get_store().find_element_user(PROFILING$0, 0);
         return target == null ? null : target;
      }
   }

   public void setProfiling(ProfilingType profiling) {
      this.generatedSetterHelperImpl(profiling, PROFILING$0, 0, (short)1);
   }

   public ProfilingType addNewProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProfilingType target = null;
         target = (ProfilingType)this.get_store().add_element_user(PROFILING$0);
         return target;
      }
   }
}
