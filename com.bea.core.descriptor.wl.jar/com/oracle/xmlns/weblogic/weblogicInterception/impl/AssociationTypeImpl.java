package com.oracle.xmlns.weblogic.weblogicInterception.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicInterception.AssociationType;
import com.oracle.xmlns.weblogic.weblogicInterception.InterceptionPointType;
import com.oracle.xmlns.weblogic.weblogicInterception.ProcessorAssociationType;
import javax.xml.namespace.QName;

public class AssociationTypeImpl extends XmlComplexContentImpl implements AssociationType {
   private static final long serialVersionUID = 1L;
   private static final QName INTERCEPTIONPOINT$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "interception-point");
   private static final QName PROCESSOR$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "processor");

   public AssociationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public InterceptionPointType getInterceptionPoint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InterceptionPointType target = null;
         target = (InterceptionPointType)this.get_store().find_element_user(INTERCEPTIONPOINT$0, 0);
         return target == null ? null : target;
      }
   }

   public void setInterceptionPoint(InterceptionPointType interceptionPoint) {
      this.generatedSetterHelperImpl(interceptionPoint, INTERCEPTIONPOINT$0, 0, (short)1);
   }

   public InterceptionPointType addNewInterceptionPoint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InterceptionPointType target = null;
         target = (InterceptionPointType)this.get_store().add_element_user(INTERCEPTIONPOINT$0);
         return target;
      }
   }

   public ProcessorAssociationType getProcessor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProcessorAssociationType target = null;
         target = (ProcessorAssociationType)this.get_store().find_element_user(PROCESSOR$2, 0);
         return target == null ? null : target;
      }
   }

   public void setProcessor(ProcessorAssociationType processor) {
      this.generatedSetterHelperImpl(processor, PROCESSOR$2, 0, (short)1);
   }

   public ProcessorAssociationType addNewProcessor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProcessorAssociationType target = null;
         target = (ProcessorAssociationType)this.get_store().add_element_user(PROCESSOR$2);
         return target;
      }
   }
}
