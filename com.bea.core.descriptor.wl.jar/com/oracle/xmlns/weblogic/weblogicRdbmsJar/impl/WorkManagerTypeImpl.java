package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CapacityType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.ContextRequestClassType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.DispatchPolicyType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.FairShareRequestClassType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.MaxThreadsConstraintType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.MinThreadsConstraintType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.ResponseTimeRequestClassType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WorkManagerShutdownTriggerType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WorkManagerType;
import com.sun.java.xml.ns.javaee.XsdBooleanType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import javax.xml.namespace.QName;

public class WorkManagerTypeImpl extends XmlComplexContentImpl implements WorkManagerType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "name");
   private static final QName RESPONSETIMEREQUESTCLASS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "response-time-request-class");
   private static final QName FAIRSHAREREQUESTCLASS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "fair-share-request-class");
   private static final QName CONTEXTREQUESTCLASS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "context-request-class");
   private static final QName REQUESTCLASSNAME$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "request-class-name");
   private static final QName MINTHREADSCONSTRAINT$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "min-threads-constraint");
   private static final QName MINTHREADSCONSTRAINTNAME$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "min-threads-constraint-name");
   private static final QName MAXTHREADSCONSTRAINT$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "max-threads-constraint");
   private static final QName MAXTHREADSCONSTRAINTNAME$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "max-threads-constraint-name");
   private static final QName CAPACITY$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "capacity");
   private static final QName CAPACITYNAME$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "capacity-name");
   private static final QName WORKMANAGERSHUTDOWNTRIGGER$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "work-manager-shutdown-trigger");
   private static final QName IGNORESTUCKTHREADS$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "ignore-stuck-threads");
   private static final QName ID$26 = new QName("", "id");

   public WorkManagerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DispatchPolicyType getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatchPolicyType target = null;
         target = (DispatchPolicyType)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setName(DispatchPolicyType name) {
      this.generatedSetterHelperImpl(name, NAME$0, 0, (short)1);
   }

   public DispatchPolicyType addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatchPolicyType target = null;
         target = (DispatchPolicyType)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public ResponseTimeRequestClassType getResponseTimeRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResponseTimeRequestClassType target = null;
         target = (ResponseTimeRequestClassType)this.get_store().find_element_user(RESPONSETIMEREQUESTCLASS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResponseTimeRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESPONSETIMEREQUESTCLASS$2) != 0;
      }
   }

   public void setResponseTimeRequestClass(ResponseTimeRequestClassType responseTimeRequestClass) {
      this.generatedSetterHelperImpl(responseTimeRequestClass, RESPONSETIMEREQUESTCLASS$2, 0, (short)1);
   }

   public ResponseTimeRequestClassType addNewResponseTimeRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResponseTimeRequestClassType target = null;
         target = (ResponseTimeRequestClassType)this.get_store().add_element_user(RESPONSETIMEREQUESTCLASS$2);
         return target;
      }
   }

   public void unsetResponseTimeRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESPONSETIMEREQUESTCLASS$2, 0);
      }
   }

   public FairShareRequestClassType getFairShareRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FairShareRequestClassType target = null;
         target = (FairShareRequestClassType)this.get_store().find_element_user(FAIRSHAREREQUESTCLASS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFairShareRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FAIRSHAREREQUESTCLASS$4) != 0;
      }
   }

   public void setFairShareRequestClass(FairShareRequestClassType fairShareRequestClass) {
      this.generatedSetterHelperImpl(fairShareRequestClass, FAIRSHAREREQUESTCLASS$4, 0, (short)1);
   }

   public FairShareRequestClassType addNewFairShareRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FairShareRequestClassType target = null;
         target = (FairShareRequestClassType)this.get_store().add_element_user(FAIRSHAREREQUESTCLASS$4);
         return target;
      }
   }

   public void unsetFairShareRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FAIRSHAREREQUESTCLASS$4, 0);
      }
   }

   public ContextRequestClassType getContextRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContextRequestClassType target = null;
         target = (ContextRequestClassType)this.get_store().find_element_user(CONTEXTREQUESTCLASS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetContextRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONTEXTREQUESTCLASS$6) != 0;
      }
   }

   public void setContextRequestClass(ContextRequestClassType contextRequestClass) {
      this.generatedSetterHelperImpl(contextRequestClass, CONTEXTREQUESTCLASS$6, 0, (short)1);
   }

   public ContextRequestClassType addNewContextRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContextRequestClassType target = null;
         target = (ContextRequestClassType)this.get_store().add_element_user(CONTEXTREQUESTCLASS$6);
         return target;
      }
   }

   public void unsetContextRequestClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONTEXTREQUESTCLASS$6, 0);
      }
   }

   public XsdStringType getRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(REQUESTCLASSNAME$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUESTCLASSNAME$8) != 0;
      }
   }

   public void setRequestClassName(XsdStringType requestClassName) {
      this.generatedSetterHelperImpl(requestClassName, REQUESTCLASSNAME$8, 0, (short)1);
   }

   public XsdStringType addNewRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(REQUESTCLASSNAME$8);
         return target;
      }
   }

   public void unsetRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUESTCLASSNAME$8, 0);
      }
   }

   public MinThreadsConstraintType getMinThreadsConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MinThreadsConstraintType target = null;
         target = (MinThreadsConstraintType)this.get_store().find_element_user(MINTHREADSCONSTRAINT$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMinThreadsConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINTHREADSCONSTRAINT$10) != 0;
      }
   }

   public void setMinThreadsConstraint(MinThreadsConstraintType minThreadsConstraint) {
      this.generatedSetterHelperImpl(minThreadsConstraint, MINTHREADSCONSTRAINT$10, 0, (short)1);
   }

   public MinThreadsConstraintType addNewMinThreadsConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MinThreadsConstraintType target = null;
         target = (MinThreadsConstraintType)this.get_store().add_element_user(MINTHREADSCONSTRAINT$10);
         return target;
      }
   }

   public void unsetMinThreadsConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINTHREADSCONSTRAINT$10, 0);
      }
   }

   public XsdStringType getMinThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(MINTHREADSCONSTRAINTNAME$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMinThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINTHREADSCONSTRAINTNAME$12) != 0;
      }
   }

   public void setMinThreadsConstraintName(XsdStringType minThreadsConstraintName) {
      this.generatedSetterHelperImpl(minThreadsConstraintName, MINTHREADSCONSTRAINTNAME$12, 0, (short)1);
   }

   public XsdStringType addNewMinThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(MINTHREADSCONSTRAINTNAME$12);
         return target;
      }
   }

   public void unsetMinThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINTHREADSCONSTRAINTNAME$12, 0);
      }
   }

   public MaxThreadsConstraintType getMaxThreadsConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MaxThreadsConstraintType target = null;
         target = (MaxThreadsConstraintType)this.get_store().find_element_user(MAXTHREADSCONSTRAINT$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxThreadsConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXTHREADSCONSTRAINT$14) != 0;
      }
   }

   public void setMaxThreadsConstraint(MaxThreadsConstraintType maxThreadsConstraint) {
      this.generatedSetterHelperImpl(maxThreadsConstraint, MAXTHREADSCONSTRAINT$14, 0, (short)1);
   }

   public MaxThreadsConstraintType addNewMaxThreadsConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MaxThreadsConstraintType target = null;
         target = (MaxThreadsConstraintType)this.get_store().add_element_user(MAXTHREADSCONSTRAINT$14);
         return target;
      }
   }

   public void unsetMaxThreadsConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXTHREADSCONSTRAINT$14, 0);
      }
   }

   public XsdStringType getMaxThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(MAXTHREADSCONSTRAINTNAME$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXTHREADSCONSTRAINTNAME$16) != 0;
      }
   }

   public void setMaxThreadsConstraintName(XsdStringType maxThreadsConstraintName) {
      this.generatedSetterHelperImpl(maxThreadsConstraintName, MAXTHREADSCONSTRAINTNAME$16, 0, (short)1);
   }

   public XsdStringType addNewMaxThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(MAXTHREADSCONSTRAINTNAME$16);
         return target;
      }
   }

   public void unsetMaxThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXTHREADSCONSTRAINTNAME$16, 0);
      }
   }

   public CapacityType getCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CapacityType target = null;
         target = (CapacityType)this.get_store().find_element_user(CAPACITY$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CAPACITY$18) != 0;
      }
   }

   public void setCapacity(CapacityType capacity) {
      this.generatedSetterHelperImpl(capacity, CAPACITY$18, 0, (short)1);
   }

   public CapacityType addNewCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CapacityType target = null;
         target = (CapacityType)this.get_store().add_element_user(CAPACITY$18);
         return target;
      }
   }

   public void unsetCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CAPACITY$18, 0);
      }
   }

   public XsdStringType getCapacityName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(CAPACITYNAME$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCapacityName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CAPACITYNAME$20) != 0;
      }
   }

   public void setCapacityName(XsdStringType capacityName) {
      this.generatedSetterHelperImpl(capacityName, CAPACITYNAME$20, 0, (short)1);
   }

   public XsdStringType addNewCapacityName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(CAPACITYNAME$20);
         return target;
      }
   }

   public void unsetCapacityName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CAPACITYNAME$20, 0);
      }
   }

   public WorkManagerShutdownTriggerType getWorkManagerShutdownTrigger() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerShutdownTriggerType target = null;
         target = (WorkManagerShutdownTriggerType)this.get_store().find_element_user(WORKMANAGERSHUTDOWNTRIGGER$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWorkManagerShutdownTrigger() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WORKMANAGERSHUTDOWNTRIGGER$22) != 0;
      }
   }

   public void setWorkManagerShutdownTrigger(WorkManagerShutdownTriggerType workManagerShutdownTrigger) {
      this.generatedSetterHelperImpl(workManagerShutdownTrigger, WORKMANAGERSHUTDOWNTRIGGER$22, 0, (short)1);
   }

   public WorkManagerShutdownTriggerType addNewWorkManagerShutdownTrigger() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerShutdownTriggerType target = null;
         target = (WorkManagerShutdownTriggerType)this.get_store().add_element_user(WORKMANAGERSHUTDOWNTRIGGER$22);
         return target;
      }
   }

   public void unsetWorkManagerShutdownTrigger() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WORKMANAGERSHUTDOWNTRIGGER$22, 0);
      }
   }

   public XsdBooleanType getIgnoreStuckThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdBooleanType target = null;
         target = (XsdBooleanType)this.get_store().find_element_user(IGNORESTUCKTHREADS$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIgnoreStuckThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IGNORESTUCKTHREADS$24) != 0;
      }
   }

   public void setIgnoreStuckThreads(XsdBooleanType ignoreStuckThreads) {
      this.generatedSetterHelperImpl(ignoreStuckThreads, IGNORESTUCKTHREADS$24, 0, (short)1);
   }

   public XsdBooleanType addNewIgnoreStuckThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdBooleanType target = null;
         target = (XsdBooleanType)this.get_store().add_element_user(IGNORESTUCKTHREADS$24);
         return target;
      }
   }

   public void unsetIgnoreStuckThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IGNORESTUCKTHREADS$24, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$26);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$26);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$26) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$26);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$26);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$26);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$26);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$26);
      }
   }
}
