package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.CapacityType;
import com.bea.connector.monitoring1Dot0.ContextRequestClassType;
import com.bea.connector.monitoring1Dot0.FairShareRequestClassType;
import com.bea.connector.monitoring1Dot0.MaxThreadsConstraintType;
import com.bea.connector.monitoring1Dot0.MinThreadsConstraintType;
import com.bea.connector.monitoring1Dot0.ResponseTimeRequestClassType;
import com.bea.connector.monitoring1Dot0.WorkManagerShutdownTriggerType;
import com.bea.connector.monitoring1Dot0.WorkManagerType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class WorkManagerTypeImpl extends XmlComplexContentImpl implements WorkManagerType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "name");
   private static final QName RESPONSETIMEREQUESTCLASS$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "response-time-request-class");
   private static final QName FAIRSHAREREQUESTCLASS$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "fair-share-request-class");
   private static final QName CONTEXTREQUESTCLASS$6 = new QName("http://www.bea.com/connector/monitoring1dot0", "context-request-class");
   private static final QName REQUESTCLASSNAME$8 = new QName("http://www.bea.com/connector/monitoring1dot0", "request-class-name");
   private static final QName MINTHREADSCONSTRAINT$10 = new QName("http://www.bea.com/connector/monitoring1dot0", "min-threads-constraint");
   private static final QName MINTHREADSCONSTRAINTNAME$12 = new QName("http://www.bea.com/connector/monitoring1dot0", "min-threads-constraint-name");
   private static final QName MAXTHREADSCONSTRAINT$14 = new QName("http://www.bea.com/connector/monitoring1dot0", "max-threads-constraint");
   private static final QName MAXTHREADSCONSTRAINTNAME$16 = new QName("http://www.bea.com/connector/monitoring1dot0", "max-threads-constraint-name");
   private static final QName CAPACITY$18 = new QName("http://www.bea.com/connector/monitoring1dot0", "capacity");
   private static final QName CAPACITYNAME$20 = new QName("http://www.bea.com/connector/monitoring1dot0", "capacity-name");
   private static final QName WORKMANAGERSHUTDOWNTRIGGER$22 = new QName("http://www.bea.com/connector/monitoring1dot0", "work-manager-shutdown-trigger");
   private static final QName IGNORESTUCKTHREADS$24 = new QName("http://www.bea.com/connector/monitoring1dot0", "ignore-stuck-threads");

   public WorkManagerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$0);
         }

         target.set(name);
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

   public String getRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUESTCLASSNAME$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REQUESTCLASSNAME$8, 0);
         return target;
      }
   }

   public boolean isSetRequestClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUESTCLASSNAME$8) != 0;
      }
   }

   public void setRequestClassName(String requestClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REQUESTCLASSNAME$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REQUESTCLASSNAME$8);
         }

         target.setStringValue(requestClassName);
      }
   }

   public void xsetRequestClassName(XmlString requestClassName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REQUESTCLASSNAME$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REQUESTCLASSNAME$8);
         }

         target.set(requestClassName);
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

   public String getMinThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MINTHREADSCONSTRAINTNAME$12, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMinThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MINTHREADSCONSTRAINTNAME$12, 0);
         return target;
      }
   }

   public boolean isSetMinThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINTHREADSCONSTRAINTNAME$12) != 0;
      }
   }

   public void setMinThreadsConstraintName(String minThreadsConstraintName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MINTHREADSCONSTRAINTNAME$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MINTHREADSCONSTRAINTNAME$12);
         }

         target.setStringValue(minThreadsConstraintName);
      }
   }

   public void xsetMinThreadsConstraintName(XmlString minThreadsConstraintName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MINTHREADSCONSTRAINTNAME$12, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MINTHREADSCONSTRAINTNAME$12);
         }

         target.set(minThreadsConstraintName);
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

   public String getMaxThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXTHREADSCONSTRAINTNAME$16, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMaxThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAXTHREADSCONSTRAINTNAME$16, 0);
         return target;
      }
   }

   public boolean isSetMaxThreadsConstraintName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXTHREADSCONSTRAINTNAME$16) != 0;
      }
   }

   public void setMaxThreadsConstraintName(String maxThreadsConstraintName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXTHREADSCONSTRAINTNAME$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXTHREADSCONSTRAINTNAME$16);
         }

         target.setStringValue(maxThreadsConstraintName);
      }
   }

   public void xsetMaxThreadsConstraintName(XmlString maxThreadsConstraintName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAXTHREADSCONSTRAINTNAME$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MAXTHREADSCONSTRAINTNAME$16);
         }

         target.set(maxThreadsConstraintName);
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

   public String getCapacityName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CAPACITYNAME$20, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCapacityName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CAPACITYNAME$20, 0);
         return target;
      }
   }

   public boolean isSetCapacityName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CAPACITYNAME$20) != 0;
      }
   }

   public void setCapacityName(String capacityName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CAPACITYNAME$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CAPACITYNAME$20);
         }

         target.setStringValue(capacityName);
      }
   }

   public void xsetCapacityName(XmlString capacityName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CAPACITYNAME$20, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CAPACITYNAME$20);
         }

         target.set(capacityName);
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

   public boolean getIgnoreStuckThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNORESTUCKTHREADS$24, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIgnoreStuckThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNORESTUCKTHREADS$24, 0);
         return target;
      }
   }

   public boolean isSetIgnoreStuckThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IGNORESTUCKTHREADS$24) != 0;
      }
   }

   public void setIgnoreStuckThreads(boolean ignoreStuckThreads) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNORESTUCKTHREADS$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(IGNORESTUCKTHREADS$24);
         }

         target.setBooleanValue(ignoreStuckThreads);
      }
   }

   public void xsetIgnoreStuckThreads(XmlBoolean ignoreStuckThreads) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNORESTUCKTHREADS$24, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(IGNORESTUCKTHREADS$24);
         }

         target.set(ignoreStuckThreads);
      }
   }

   public void unsetIgnoreStuckThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IGNORESTUCKTHREADS$24, 0);
      }
   }
}
