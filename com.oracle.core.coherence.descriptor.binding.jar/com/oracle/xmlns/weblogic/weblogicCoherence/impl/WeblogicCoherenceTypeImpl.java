package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceAddressProvidersType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceClusterParamsType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceFederationParamsType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceLoggingParamsType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherencePersistenceParamsType;
import com.oracle.xmlns.weblogic.weblogicCoherence.WeblogicCoherenceType;
import javax.xml.namespace.QName;

public class WeblogicCoherenceTypeImpl extends XmlComplexContentImpl implements WeblogicCoherenceType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "name");
   private static final QName CUSTOMCLUSTERCONFIGURATIONFILENAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "custom-cluster-configuration-file-name");
   private static final QName CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "custom-cluster-configuration-file-last-updated-timestamp");
   private static final QName COHERENCECLUSTERPARAMS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-cluster-params");
   private static final QName COHERENCELOGGINGPARAMS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-logging-params");
   private static final QName COHERENCEADDRESSPROVIDERS$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-address-providers");
   private static final QName COHERENCEPERSISTENCEPARAMS$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-persistence-params");
   private static final QName COHERENCEFEDERATIONPARAMS$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-federation-params");
   private static final QName VERSION$16 = new QName("", "version");

   public WeblogicCoherenceTypeImpl(SchemaType sType) {
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

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NAME$0) != 0;
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

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NAME$0, 0);
      }
   }

   public String getCustomClusterConfigurationFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILENAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCustomClusterConfigurationFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILENAME$2, 0);
         return target;
      }
   }

   public boolean isNilCustomClusterConfigurationFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILENAME$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomClusterConfigurationFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMCLUSTERCONFIGURATIONFILENAME$2) != 0;
      }
   }

   public void setCustomClusterConfigurationFileName(String customClusterConfigurationFileName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILENAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CUSTOMCLUSTERCONFIGURATIONFILENAME$2);
         }

         target.setStringValue(customClusterConfigurationFileName);
      }
   }

   public void xsetCustomClusterConfigurationFileName(XmlString customClusterConfigurationFileName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILENAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CUSTOMCLUSTERCONFIGURATIONFILENAME$2);
         }

         target.set(customClusterConfigurationFileName);
      }
   }

   public void setNilCustomClusterConfigurationFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILENAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CUSTOMCLUSTERCONFIGURATIONFILENAME$2);
         }

         target.setNil();
      }
   }

   public void unsetCustomClusterConfigurationFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMCLUSTERCONFIGURATIONFILENAME$2, 0);
      }
   }

   public long getCustomClusterConfigurationFileLastUpdatedTimestamp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetCustomClusterConfigurationFileLastUpdatedTimestamp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4, 0);
         return target;
      }
   }

   public boolean isNilCustomClusterConfigurationFileLastUpdatedTimestamp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomClusterConfigurationFileLastUpdatedTimestamp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4) != 0;
      }
   }

   public void setCustomClusterConfigurationFileLastUpdatedTimestamp(long customClusterConfigurationFileLastUpdatedTimestamp) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4);
         }

         target.setLongValue(customClusterConfigurationFileLastUpdatedTimestamp);
      }
   }

   public void xsetCustomClusterConfigurationFileLastUpdatedTimestamp(XmlLong customClusterConfigurationFileLastUpdatedTimestamp) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4);
         }

         target.set(customClusterConfigurationFileLastUpdatedTimestamp);
      }
   }

   public void setNilCustomClusterConfigurationFileLastUpdatedTimestamp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4);
         }

         target.setNil();
      }
   }

   public void unsetCustomClusterConfigurationFileLastUpdatedTimestamp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMCLUSTERCONFIGURATIONFILELASTUPDATEDTIMESTAMP$4, 0);
      }
   }

   public CoherenceClusterParamsType getCoherenceClusterParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterParamsType target = null;
         target = (CoherenceClusterParamsType)this.get_store().find_element_user(COHERENCECLUSTERPARAMS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCoherenceClusterParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterParamsType target = null;
         target = (CoherenceClusterParamsType)this.get_store().find_element_user(COHERENCECLUSTERPARAMS$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCoherenceClusterParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCECLUSTERPARAMS$6) != 0;
      }
   }

   public void setCoherenceClusterParams(CoherenceClusterParamsType coherenceClusterParams) {
      this.generatedSetterHelperImpl(coherenceClusterParams, COHERENCECLUSTERPARAMS$6, 0, (short)1);
   }

   public CoherenceClusterParamsType addNewCoherenceClusterParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterParamsType target = null;
         target = (CoherenceClusterParamsType)this.get_store().add_element_user(COHERENCECLUSTERPARAMS$6);
         return target;
      }
   }

   public void setNilCoherenceClusterParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterParamsType target = null;
         target = (CoherenceClusterParamsType)this.get_store().find_element_user(COHERENCECLUSTERPARAMS$6, 0);
         if (target == null) {
            target = (CoherenceClusterParamsType)this.get_store().add_element_user(COHERENCECLUSTERPARAMS$6);
         }

         target.setNil();
      }
   }

   public void unsetCoherenceClusterParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCECLUSTERPARAMS$6, 0);
      }
   }

   public CoherenceLoggingParamsType getCoherenceLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceLoggingParamsType target = null;
         target = (CoherenceLoggingParamsType)this.get_store().find_element_user(COHERENCELOGGINGPARAMS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCoherenceLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceLoggingParamsType target = null;
         target = (CoherenceLoggingParamsType)this.get_store().find_element_user(COHERENCELOGGINGPARAMS$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCoherenceLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCELOGGINGPARAMS$8) != 0;
      }
   }

   public void setCoherenceLoggingParams(CoherenceLoggingParamsType coherenceLoggingParams) {
      this.generatedSetterHelperImpl(coherenceLoggingParams, COHERENCELOGGINGPARAMS$8, 0, (short)1);
   }

   public CoherenceLoggingParamsType addNewCoherenceLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceLoggingParamsType target = null;
         target = (CoherenceLoggingParamsType)this.get_store().add_element_user(COHERENCELOGGINGPARAMS$8);
         return target;
      }
   }

   public void setNilCoherenceLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceLoggingParamsType target = null;
         target = (CoherenceLoggingParamsType)this.get_store().find_element_user(COHERENCELOGGINGPARAMS$8, 0);
         if (target == null) {
            target = (CoherenceLoggingParamsType)this.get_store().add_element_user(COHERENCELOGGINGPARAMS$8);
         }

         target.setNil();
      }
   }

   public void unsetCoherenceLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCELOGGINGPARAMS$8, 0);
      }
   }

   public CoherenceAddressProvidersType getCoherenceAddressProviders() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceAddressProvidersType target = null;
         target = (CoherenceAddressProvidersType)this.get_store().find_element_user(COHERENCEADDRESSPROVIDERS$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCoherenceAddressProviders() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceAddressProvidersType target = null;
         target = (CoherenceAddressProvidersType)this.get_store().find_element_user(COHERENCEADDRESSPROVIDERS$10, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCoherenceAddressProviders() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCEADDRESSPROVIDERS$10) != 0;
      }
   }

   public void setCoherenceAddressProviders(CoherenceAddressProvidersType coherenceAddressProviders) {
      this.generatedSetterHelperImpl(coherenceAddressProviders, COHERENCEADDRESSPROVIDERS$10, 0, (short)1);
   }

   public CoherenceAddressProvidersType addNewCoherenceAddressProviders() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceAddressProvidersType target = null;
         target = (CoherenceAddressProvidersType)this.get_store().add_element_user(COHERENCEADDRESSPROVIDERS$10);
         return target;
      }
   }

   public void setNilCoherenceAddressProviders() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceAddressProvidersType target = null;
         target = (CoherenceAddressProvidersType)this.get_store().find_element_user(COHERENCEADDRESSPROVIDERS$10, 0);
         if (target == null) {
            target = (CoherenceAddressProvidersType)this.get_store().add_element_user(COHERENCEADDRESSPROVIDERS$10);
         }

         target.setNil();
      }
   }

   public void unsetCoherenceAddressProviders() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCEADDRESSPROVIDERS$10, 0);
      }
   }

   public CoherencePersistenceParamsType getCoherencePersistenceParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherencePersistenceParamsType target = null;
         target = (CoherencePersistenceParamsType)this.get_store().find_element_user(COHERENCEPERSISTENCEPARAMS$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCoherencePersistenceParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherencePersistenceParamsType target = null;
         target = (CoherencePersistenceParamsType)this.get_store().find_element_user(COHERENCEPERSISTENCEPARAMS$12, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCoherencePersistenceParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCEPERSISTENCEPARAMS$12) != 0;
      }
   }

   public void setCoherencePersistenceParams(CoherencePersistenceParamsType coherencePersistenceParams) {
      this.generatedSetterHelperImpl(coherencePersistenceParams, COHERENCEPERSISTENCEPARAMS$12, 0, (short)1);
   }

   public CoherencePersistenceParamsType addNewCoherencePersistenceParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherencePersistenceParamsType target = null;
         target = (CoherencePersistenceParamsType)this.get_store().add_element_user(COHERENCEPERSISTENCEPARAMS$12);
         return target;
      }
   }

   public void setNilCoherencePersistenceParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherencePersistenceParamsType target = null;
         target = (CoherencePersistenceParamsType)this.get_store().find_element_user(COHERENCEPERSISTENCEPARAMS$12, 0);
         if (target == null) {
            target = (CoherencePersistenceParamsType)this.get_store().add_element_user(COHERENCEPERSISTENCEPARAMS$12);
         }

         target.setNil();
      }
   }

   public void unsetCoherencePersistenceParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCEPERSISTENCEPARAMS$12, 0);
      }
   }

   public CoherenceFederationParamsType getCoherenceFederationParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceFederationParamsType target = null;
         target = (CoherenceFederationParamsType)this.get_store().find_element_user(COHERENCEFEDERATIONPARAMS$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCoherenceFederationParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceFederationParamsType target = null;
         target = (CoherenceFederationParamsType)this.get_store().find_element_user(COHERENCEFEDERATIONPARAMS$14, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCoherenceFederationParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCEFEDERATIONPARAMS$14) != 0;
      }
   }

   public void setCoherenceFederationParams(CoherenceFederationParamsType coherenceFederationParams) {
      this.generatedSetterHelperImpl(coherenceFederationParams, COHERENCEFEDERATIONPARAMS$14, 0, (short)1);
   }

   public CoherenceFederationParamsType addNewCoherenceFederationParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceFederationParamsType target = null;
         target = (CoherenceFederationParamsType)this.get_store().add_element_user(COHERENCEFEDERATIONPARAMS$14);
         return target;
      }
   }

   public void setNilCoherenceFederationParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceFederationParamsType target = null;
         target = (CoherenceFederationParamsType)this.get_store().find_element_user(COHERENCEFEDERATIONPARAMS$14, 0);
         if (target == null) {
            target = (CoherenceFederationParamsType)this.get_store().add_element_user(COHERENCEFEDERATIONPARAMS$14);
         }

         target.setNil();
      }
   }

   public void unsetCoherenceFederationParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCEFEDERATIONPARAMS$14, 0);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$16);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$16);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$16) != null;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$16);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$16);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$16);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$16);
      }
   }
}
