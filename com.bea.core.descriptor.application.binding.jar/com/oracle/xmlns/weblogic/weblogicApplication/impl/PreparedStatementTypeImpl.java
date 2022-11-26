package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.weblogicApplication.PreparedStatementType;
import com.oracle.xmlns.weblogic.weblogicApplication.TrueFalseType;
import javax.xml.namespace.QName;

public class PreparedStatementTypeImpl extends XmlComplexContentImpl implements PreparedStatementType {
   private static final long serialVersionUID = 1L;
   private static final QName PROFILINGENABLED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "profiling-enabled");
   private static final QName CACHEPROFILINGTHRESHOLD$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "cache-profiling-threshold");
   private static final QName CACHESIZE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "cache-size");
   private static final QName PARAMETERLOGGINGENABLED$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "parameter-logging-enabled");
   private static final QName MAXPARAMETERLENGTH$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "max-parameter-length");
   private static final QName CACHETYPE$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "cache-type");

   public PreparedStatementTypeImpl(SchemaType sType) {
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

   public int getCacheProfilingThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHEPROFILINGTHRESHOLD$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetCacheProfilingThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CACHEPROFILINGTHRESHOLD$2, 0);
         return target;
      }
   }

   public boolean isSetCacheProfilingThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHEPROFILINGTHRESHOLD$2) != 0;
      }
   }

   public void setCacheProfilingThreshold(int cacheProfilingThreshold) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHEPROFILINGTHRESHOLD$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CACHEPROFILINGTHRESHOLD$2);
         }

         target.setIntValue(cacheProfilingThreshold);
      }
   }

   public void xsetCacheProfilingThreshold(XmlInt cacheProfilingThreshold) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CACHEPROFILINGTHRESHOLD$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(CACHEPROFILINGTHRESHOLD$2);
         }

         target.set(cacheProfilingThreshold);
      }
   }

   public void unsetCacheProfilingThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHEPROFILINGTHRESHOLD$2, 0);
      }
   }

   public int getCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHESIZE$4, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CACHESIZE$4, 0);
         return target;
      }
   }

   public boolean isSetCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHESIZE$4) != 0;
      }
   }

   public void setCacheSize(int cacheSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHESIZE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CACHESIZE$4);
         }

         target.setIntValue(cacheSize);
      }
   }

   public void xsetCacheSize(XmlInt cacheSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CACHESIZE$4, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(CACHESIZE$4);
         }

         target.set(cacheSize);
      }
   }

   public void unsetCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHESIZE$4, 0);
      }
   }

   public TrueFalseType getParameterLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(PARAMETERLOGGINGENABLED$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetParameterLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PARAMETERLOGGINGENABLED$6) != 0;
      }
   }

   public void setParameterLoggingEnabled(TrueFalseType parameterLoggingEnabled) {
      this.generatedSetterHelperImpl(parameterLoggingEnabled, PARAMETERLOGGINGENABLED$6, 0, (short)1);
   }

   public TrueFalseType addNewParameterLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(PARAMETERLOGGINGENABLED$6);
         return target;
      }
   }

   public void unsetParameterLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PARAMETERLOGGINGENABLED$6, 0);
      }
   }

   public int getMaxParameterLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXPARAMETERLENGTH$8, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxParameterLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXPARAMETERLENGTH$8, 0);
         return target;
      }
   }

   public boolean isSetMaxParameterLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXPARAMETERLENGTH$8) != 0;
      }
   }

   public void setMaxParameterLength(int maxParameterLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXPARAMETERLENGTH$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXPARAMETERLENGTH$8);
         }

         target.setIntValue(maxParameterLength);
      }
   }

   public void xsetMaxParameterLength(XmlInt maxParameterLength) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXPARAMETERLENGTH$8, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXPARAMETERLENGTH$8);
         }

         target.set(maxParameterLength);
      }
   }

   public void unsetMaxParameterLength() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXPARAMETERLENGTH$8, 0);
      }
   }

   public int getCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHETYPE$10, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CACHETYPE$10, 0);
         return target;
      }
   }

   public boolean isSetCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHETYPE$10) != 0;
      }
   }

   public void setCacheType(int cacheType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHETYPE$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CACHETYPE$10);
         }

         target.setIntValue(cacheType);
      }
   }

   public void xsetCacheType(XmlInt cacheType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CACHETYPE$10, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(CACHETYPE$10);
         }

         target.set(cacheType);
      }
   }

   public void unsetCacheType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHETYPE$10, 0);
      }
   }
}
