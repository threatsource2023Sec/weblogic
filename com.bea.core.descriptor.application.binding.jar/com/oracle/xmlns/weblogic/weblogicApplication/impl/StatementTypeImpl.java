package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplication.StatementType;
import com.oracle.xmlns.weblogic.weblogicApplication.TrueFalseType;
import javax.xml.namespace.QName;

public class StatementTypeImpl extends XmlComplexContentImpl implements StatementType {
   private static final long serialVersionUID = 1L;
   private static final QName PROFILINGENABLED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "profiling-enabled");

   public StatementTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(PROFILINGENABLED$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROFILINGENABLED$0) != 0;
      }
   }

   public void setProfilingEnabled(TrueFalseType profilingEnabled) {
      this.generatedSetterHelperImpl(profilingEnabled, PROFILINGENABLED$0, 0, (short)1);
   }

   public TrueFalseType addNewProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(PROFILINGENABLED$0);
         return target;
      }
   }

   public void unsetProfilingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROFILINGENABLED$0, 0);
      }
   }
}
