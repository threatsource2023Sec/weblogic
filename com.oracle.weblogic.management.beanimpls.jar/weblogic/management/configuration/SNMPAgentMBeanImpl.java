package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.SNMPAgent;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SNMPAgentMBeanImpl extends ConfigurationMBeanImpl implements SNMPAgentMBean, Serializable {
   private String _AuthenticationProtocol;
   private boolean _CommunityBasedAccessEnabled;
   private String _CommunityPrefix;
   private int _DebugLevel;
   private boolean _DynamicallyCreated;
   private boolean _Enabled;
   private boolean _InformEnabled;
   private int _InformRetryInterval;
   private String _ListenAddress;
   private long _LocalizedKeyCacheInvalidationInterval;
   private int _MasterAgentXPort;
   private int _MaxInformRetryCount;
   private int _MibDataRefreshInterval;
   private String _Name;
   private String _PrivacyProtocol;
   private boolean _SNMPAccessForUserMBeansEnabled;
   private SNMPAttributeChangeMBean[] _SNMPAttributeChanges;
   private SNMPCounterMonitorMBean[] _SNMPCounterMonitors;
   private String _SNMPEngineId;
   private SNMPGaugeMonitorMBean[] _SNMPGaugeMonitors;
   private SNMPLogFilterMBean[] _SNMPLogFilters;
   private int _SNMPPort;
   private SNMPProxyMBean[] _SNMPProxies;
   private SNMPStringMonitorMBean[] _SNMPStringMonitors;
   private SNMPTrapDestinationMBean[] _SNMPTrapDestinations;
   private int _SNMPTrapVersion;
   private boolean _SendAutomaticTrapsEnabled;
   private int _ServerStatusCheckIntervalFactor;
   private String[] _Tags;
   private SNMPTrapDestinationMBean[] _TargetedTrapDestinations;
   private String _UserDefinedMib;
   private transient SNMPAgent _customizer;
   private static SchemaHelper2 _schemaHelper;

   public SNMPAgentMBeanImpl() {
      try {
         this._customizer = new SNMPAgent(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public SNMPAgentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new SNMPAgent(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public SNMPAgentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new SNMPAgent(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(10);
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public boolean isSendAutomaticTrapsEnabled() {
      return this._SendAutomaticTrapsEnabled;
   }

   public boolean isSendAutomaticTrapsEnabledInherited() {
      return false;
   }

   public boolean isSendAutomaticTrapsEnabledSet() {
      return this._isSet(11);
   }

   public void setSendAutomaticTrapsEnabled(boolean param0) {
      boolean _oldVal = this._SendAutomaticTrapsEnabled;
      this._SendAutomaticTrapsEnabled = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getListenAddress() {
      return this._ListenAddress;
   }

   public boolean isListenAddressInherited() {
      return false;
   }

   public boolean isListenAddressSet() {
      return this._isSet(12);
   }

   public void setListenAddress(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ListenAddress;
      this._ListenAddress = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getSNMPPort() {
      return this._SNMPPort;
   }

   public boolean isSNMPPortInherited() {
      return false;
   }

   public boolean isSNMPPortSet() {
      return this._isSet(13);
   }

   public void setSNMPPort(int param0) throws InvalidAttributeValueException, ConfigurationException {
      LegalChecks.checkInRange("SNMPPort", (long)param0, 1L, 65535L);
      int _oldVal = this._SNMPPort;
      this._SNMPPort = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getSNMPTrapVersion() {
      return this._SNMPTrapVersion;
   }

   public boolean isSNMPTrapVersionInherited() {
      return false;
   }

   public boolean isSNMPTrapVersionSet() {
      return this._isSet(14);
   }

   public void setSNMPTrapVersion(int param0) {
      int[] _set = new int[]{1, 2, 3};
      param0 = LegalChecks.checkInEnum("SNMPTrapVersion", param0, _set);
      int _oldVal = this._SNMPTrapVersion;
      this._SNMPTrapVersion = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getMibDataRefreshInterval() {
      return this._MibDataRefreshInterval;
   }

   public boolean isMibDataRefreshIntervalInherited() {
      return false;
   }

   public boolean isMibDataRefreshIntervalSet() {
      return this._isSet(15);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setMibDataRefreshInterval(int param0) throws InvalidAttributeValueException, ConfigurationException {
      LegalChecks.checkInRange("MibDataRefreshInterval", (long)param0, 30L, 65535L);
      int _oldVal = this._MibDataRefreshInterval;
      this._MibDataRefreshInterval = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getServerStatusCheckIntervalFactor() {
      return this._ServerStatusCheckIntervalFactor;
   }

   public boolean isServerStatusCheckIntervalFactorInherited() {
      return false;
   }

   public boolean isServerStatusCheckIntervalFactorSet() {
      return this._isSet(16);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setServerStatusCheckIntervalFactor(int param0) throws InvalidAttributeValueException, ConfigurationException {
      LegalChecks.checkInRange("ServerStatusCheckIntervalFactor", (long)param0, 1L, 65535L);
      int _oldVal = this._ServerStatusCheckIntervalFactor;
      this._ServerStatusCheckIntervalFactor = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getCommunityPrefix() {
      return this._CommunityPrefix;
   }

   public boolean isCommunityPrefixInherited() {
      return false;
   }

   public boolean isCommunityPrefixSet() {
      return this._isSet(17);
   }

   public void setCommunityPrefix(String param0) throws InvalidAttributeValueException, ConfigurationException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CommunityPrefix;
      this._CommunityPrefix = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getUserDefinedMib() {
      return this._UserDefinedMib;
   }

   public boolean isUserDefinedMibInherited() {
      return false;
   }

   public boolean isUserDefinedMibSet() {
      return this._isSet(18);
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public void setUserDefinedMib(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("UserDefinedMib", param0);
      String _oldVal = this._UserDefinedMib;
      this._UserDefinedMib = param0;
      this._postSet(18, _oldVal, param0);
   }

   public int getDebugLevel() {
      return this._DebugLevel;
   }

   public boolean isDebugLevelInherited() {
      return false;
   }

   public boolean isDebugLevelSet() {
      return this._isSet(19);
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setDebugLevel(int param0) throws InvalidAttributeValueException, ConfigurationException {
      int[] _set = new int[]{0, 1, 2, 3};
      param0 = LegalChecks.checkInEnum("DebugLevel", param0, _set);
      int _oldVal = this._DebugLevel;
      this._DebugLevel = param0;
      this._postSet(19, _oldVal, param0);
   }

   public SNMPTrapDestinationMBean[] getTargetedTrapDestinations() {
      return this._TargetedTrapDestinations;
   }

   public String getTargetedTrapDestinationsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargetedTrapDestinations());
   }

   public boolean isTargetedTrapDestinationsInherited() {
      return false;
   }

   public boolean isTargetedTrapDestinationsSet() {
      return this._isSet(20);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
   }

   public void setTargetedTrapDestinationsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._TargetedTrapDestinations);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, SNMPTrapDestinationMBean.class, new ReferenceManager.Resolver(this, 20, param0) {
                  public void resolveReference(Object value) {
                     try {
                        SNMPAgentMBeanImpl.this.addTargetedTrapDestination((SNMPTrapDestinationMBean)value);
                        SNMPAgentMBeanImpl.this._getHelper().reorderArrayObjects((Object[])SNMPAgentMBeanImpl.this._TargetedTrapDestinations, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               SNMPTrapDestinationMBean[] var6 = this._TargetedTrapDestinations;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  SNMPTrapDestinationMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeTargetedTrapDestination(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         SNMPTrapDestinationMBean[] _oldVal = this._TargetedTrapDestinations;
         this._initializeProperty(20);
         this._postSet(20, _oldVal, this._TargetedTrapDestinations);
      }
   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void setTargetedTrapDestinations(SNMPTrapDestinationMBean[] param0) throws InvalidAttributeValueException, ConfigurationException {
      SNMPTrapDestinationMBean[] param0 = param0 == null ? new SNMPTrapDestinationMBeanImpl[0] : param0;
      SNMPTrapDestinationMBean[] _oldVal = this._TargetedTrapDestinations;
      this._TargetedTrapDestinations = (SNMPTrapDestinationMBean[])param0;
      this._postSet(20, _oldVal, param0);
   }

   public boolean addTargetedTrapDestination(SNMPTrapDestinationMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         SNMPTrapDestinationMBean[] _new;
         if (this._isSet(20)) {
            _new = (SNMPTrapDestinationMBean[])((SNMPTrapDestinationMBean[])this._getHelper()._extendArray(this.getTargetedTrapDestinations(), SNMPTrapDestinationMBean.class, param0));
         } else {
            _new = new SNMPTrapDestinationMBean[]{param0};
         }

         try {
            this.setTargetedTrapDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof ConfigurationException) {
               throw (ConfigurationException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public boolean removeTargetedTrapDestination(SNMPTrapDestinationMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      SNMPTrapDestinationMBean[] _old = this.getTargetedTrapDestinations();
      SNMPTrapDestinationMBean[] _new = (SNMPTrapDestinationMBean[])((SNMPTrapDestinationMBean[])this._getHelper()._removeElement(_old, SNMPTrapDestinationMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargetedTrapDestinations(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof ConfigurationException) {
               throw (ConfigurationException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public SNMPTrapDestinationMBean createSNMPTrapDestination(String param0) {
      SNMPTrapDestinationMBeanImpl lookup = (SNMPTrapDestinationMBeanImpl)this.lookupSNMPTrapDestination(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SNMPTrapDestinationMBeanImpl _val = new SNMPTrapDestinationMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSNMPTrapDestination(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public SNMPTrapDestinationMBean createSNMPTrapDestination(String param0, SNMPTrapDestinationMBean param1) {
      return this._customizer.createSNMPTrapDestination(param0, param1);
   }

   public void destroySNMPTrapDestination(SNMPTrapDestinationMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 21);
         SNMPTrapDestinationMBean[] _old = this.getSNMPTrapDestinations();
         SNMPTrapDestinationMBean[] _new = (SNMPTrapDestinationMBean[])((SNMPTrapDestinationMBean[])this._getHelper()._removeElement(_old, SNMPTrapDestinationMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPTrapDestinations(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SNMPTrapDestinationMBean lookupSNMPTrapDestination(String param0) {
      Object[] aary = (Object[])this._SNMPTrapDestinations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SNMPTrapDestinationMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SNMPTrapDestinationMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public SNMPTrapDestinationMBean[] getSNMPTrapDestinations() {
      return this._SNMPTrapDestinations;
   }

   public boolean isSNMPTrapDestinationsInherited() {
      return false;
   }

   public boolean isSNMPTrapDestinationsSet() {
      return this._isSet(21);
   }

   public void removeSNMPTrapDestination(SNMPTrapDestinationMBean param0) {
      this.destroySNMPTrapDestination(param0);
   }

   public void setSNMPTrapDestinations(SNMPTrapDestinationMBean[] param0) throws InvalidAttributeValueException {
      SNMPTrapDestinationMBean[] param0 = param0 == null ? new SNMPTrapDestinationMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 21)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      SNMPTrapDestinationMBean[] _oldVal = this._SNMPTrapDestinations;
      this._SNMPTrapDestinations = (SNMPTrapDestinationMBean[])param0;
      this._postSet(21, _oldVal, param0);
   }

   public boolean addSNMPTrapDestination(SNMPTrapDestinationMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 21)) {
         SNMPTrapDestinationMBean[] _new;
         if (this._isSet(21)) {
            _new = (SNMPTrapDestinationMBean[])((SNMPTrapDestinationMBean[])this._getHelper()._extendArray(this.getSNMPTrapDestinations(), SNMPTrapDestinationMBean.class, param0));
         } else {
            _new = new SNMPTrapDestinationMBean[]{param0};
         }

         try {
            this.setSNMPTrapDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof ConfigurationException) {
               throw (ConfigurationException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public SNMPProxyMBean[] getSNMPProxies() {
      return this._SNMPProxies;
   }

   public boolean isSNMPProxiesInherited() {
      return false;
   }

   public boolean isSNMPProxiesSet() {
      return this._isSet(22);
   }

   public void setSNMPProxies(SNMPProxyMBean[] param0) throws InvalidAttributeValueException, ConfigurationException {
      SNMPProxyMBean[] param0 = param0 == null ? new SNMPProxyMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 22)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPProxyMBean[] _oldVal = this._SNMPProxies;
      this._SNMPProxies = (SNMPProxyMBean[])param0;
      this._postSet(22, _oldVal, param0);
   }

   public boolean addSNMPProxy(SNMPProxyMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 22)) {
         SNMPProxyMBean[] _new;
         if (this._isSet(22)) {
            _new = (SNMPProxyMBean[])((SNMPProxyMBean[])this._getHelper()._extendArray(this.getSNMPProxies(), SNMPProxyMBean.class, param0));
         } else {
            _new = new SNMPProxyMBean[]{param0};
         }

         try {
            this.setSNMPProxies(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof ConfigurationException) {
               throw (ConfigurationException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeSNMPProxy(SNMPProxyMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this.destroySNMPProxy(param0);
      return true;
   }

   public SNMPProxyMBean createSNMPProxy(String param0) {
      SNMPProxyMBeanImpl lookup = (SNMPProxyMBeanImpl)this.lookupSNMPProxy(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SNMPProxyMBeanImpl _val = new SNMPProxyMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSNMPProxy(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public SNMPProxyMBean createSNMPProxy(String param0, SNMPProxyMBean param1) {
      return this._customizer.createSNMPProxy(param0, param1);
   }

   public void destroySNMPProxy(SNMPProxyMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 22);
         SNMPProxyMBean[] _old = this.getSNMPProxies();
         SNMPProxyMBean[] _new = (SNMPProxyMBean[])((SNMPProxyMBean[])this._getHelper()._removeElement(_old, SNMPProxyMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPProxies(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SNMPProxyMBean lookupSNMPProxy(String param0) {
      Object[] aary = (Object[])this._SNMPProxies;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SNMPProxyMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SNMPProxyMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public SNMPGaugeMonitorMBean[] getSNMPGaugeMonitors() {
      return this._SNMPGaugeMonitors;
   }

   public boolean isSNMPGaugeMonitorsInherited() {
      return false;
   }

   public boolean isSNMPGaugeMonitorsSet() {
      return this._isSet(23);
   }

   public void setSNMPGaugeMonitors(SNMPGaugeMonitorMBean[] param0) throws InvalidAttributeValueException, ConfigurationException {
      SNMPGaugeMonitorMBean[] param0 = param0 == null ? new SNMPGaugeMonitorMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 23)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPGaugeMonitorMBean[] _oldVal = this._SNMPGaugeMonitors;
      this._SNMPGaugeMonitors = (SNMPGaugeMonitorMBean[])param0;
      this._postSet(23, _oldVal, param0);
   }

   public boolean addSNMPGaugeMonitor(SNMPGaugeMonitorMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 23)) {
         SNMPGaugeMonitorMBean[] _new;
         if (this._isSet(23)) {
            _new = (SNMPGaugeMonitorMBean[])((SNMPGaugeMonitorMBean[])this._getHelper()._extendArray(this.getSNMPGaugeMonitors(), SNMPGaugeMonitorMBean.class, param0));
         } else {
            _new = new SNMPGaugeMonitorMBean[]{param0};
         }

         try {
            this.setSNMPGaugeMonitors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof ConfigurationException) {
               throw (ConfigurationException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeSNMPGaugeMonitor(SNMPGaugeMonitorMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this.destroySNMPGaugeMonitor(param0);
      return true;
   }

   public SNMPGaugeMonitorMBean createSNMPGaugeMonitor(String param0) {
      SNMPGaugeMonitorMBeanImpl lookup = (SNMPGaugeMonitorMBeanImpl)this.lookupSNMPGaugeMonitor(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SNMPGaugeMonitorMBeanImpl _val = new SNMPGaugeMonitorMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSNMPGaugeMonitor(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public SNMPGaugeMonitorMBean createSNMPGaugeMonitor(String param0, SNMPGaugeMonitorMBean param1) {
      return this._customizer.createSNMPGaugeMonitor(param0, param1);
   }

   public void destroySNMPGaugeMonitor(SNMPGaugeMonitorMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 23);
         SNMPGaugeMonitorMBean[] _old = this.getSNMPGaugeMonitors();
         SNMPGaugeMonitorMBean[] _new = (SNMPGaugeMonitorMBean[])((SNMPGaugeMonitorMBean[])this._getHelper()._removeElement(_old, SNMPGaugeMonitorMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPGaugeMonitors(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SNMPGaugeMonitorMBean lookupSNMPGaugeMonitor(String param0) {
      Object[] aary = (Object[])this._SNMPGaugeMonitors;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SNMPGaugeMonitorMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SNMPGaugeMonitorMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public SNMPStringMonitorMBean[] getSNMPStringMonitors() {
      return this._SNMPStringMonitors;
   }

   public boolean isSNMPStringMonitorsInherited() {
      return false;
   }

   public boolean isSNMPStringMonitorsSet() {
      return this._isSet(24);
   }

   public void setSNMPStringMonitors(SNMPStringMonitorMBean[] param0) throws InvalidAttributeValueException, ConfigurationException {
      SNMPStringMonitorMBean[] param0 = param0 == null ? new SNMPStringMonitorMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 24)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPStringMonitorMBean[] _oldVal = this._SNMPStringMonitors;
      this._SNMPStringMonitors = (SNMPStringMonitorMBean[])param0;
      this._postSet(24, _oldVal, param0);
   }

   public boolean addSNMPStringMonitor(SNMPStringMonitorMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 24)) {
         SNMPStringMonitorMBean[] _new;
         if (this._isSet(24)) {
            _new = (SNMPStringMonitorMBean[])((SNMPStringMonitorMBean[])this._getHelper()._extendArray(this.getSNMPStringMonitors(), SNMPStringMonitorMBean.class, param0));
         } else {
            _new = new SNMPStringMonitorMBean[]{param0};
         }

         try {
            this.setSNMPStringMonitors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof ConfigurationException) {
               throw (ConfigurationException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeSNMPStringMonitor(SNMPStringMonitorMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this.destroySNMPStringMonitor(param0);
      return true;
   }

   public SNMPStringMonitorMBean createSNMPStringMonitor(String param0) {
      SNMPStringMonitorMBeanImpl lookup = (SNMPStringMonitorMBeanImpl)this.lookupSNMPStringMonitor(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SNMPStringMonitorMBeanImpl _val = new SNMPStringMonitorMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSNMPStringMonitor(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public SNMPStringMonitorMBean createSNMPStringMonitor(String param0, SNMPStringMonitorMBean param1) {
      return this._customizer.createSNMPStringMonitor(param0, param1);
   }

   public void destroySNMPStringMonitor(SNMPStringMonitorMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 24);
         SNMPStringMonitorMBean[] _old = this.getSNMPStringMonitors();
         SNMPStringMonitorMBean[] _new = (SNMPStringMonitorMBean[])((SNMPStringMonitorMBean[])this._getHelper()._removeElement(_old, SNMPStringMonitorMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPStringMonitors(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SNMPStringMonitorMBean lookupSNMPStringMonitor(String param0) {
      Object[] aary = (Object[])this._SNMPStringMonitors;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SNMPStringMonitorMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SNMPStringMonitorMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public SNMPCounterMonitorMBean[] getSNMPCounterMonitors() {
      return this._SNMPCounterMonitors;
   }

   public boolean isSNMPCounterMonitorsInherited() {
      return false;
   }

   public boolean isSNMPCounterMonitorsSet() {
      return this._isSet(25);
   }

   public void setSNMPCounterMonitors(SNMPCounterMonitorMBean[] param0) throws InvalidAttributeValueException, ConfigurationException {
      SNMPCounterMonitorMBean[] param0 = param0 == null ? new SNMPCounterMonitorMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 25)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPCounterMonitorMBean[] _oldVal = this._SNMPCounterMonitors;
      this._SNMPCounterMonitors = (SNMPCounterMonitorMBean[])param0;
      this._postSet(25, _oldVal, param0);
   }

   public boolean addSNMPCounterMonitor(SNMPCounterMonitorMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 25)) {
         SNMPCounterMonitorMBean[] _new;
         if (this._isSet(25)) {
            _new = (SNMPCounterMonitorMBean[])((SNMPCounterMonitorMBean[])this._getHelper()._extendArray(this.getSNMPCounterMonitors(), SNMPCounterMonitorMBean.class, param0));
         } else {
            _new = new SNMPCounterMonitorMBean[]{param0};
         }

         try {
            this.setSNMPCounterMonitors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof ConfigurationException) {
               throw (ConfigurationException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeSNMPCounterMonitor(SNMPCounterMonitorMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this.destroySNMPCounterMonitor(param0);
      return true;
   }

   public SNMPCounterMonitorMBean createSNMPCounterMonitor(String param0) {
      SNMPCounterMonitorMBeanImpl lookup = (SNMPCounterMonitorMBeanImpl)this.lookupSNMPCounterMonitor(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SNMPCounterMonitorMBeanImpl _val = new SNMPCounterMonitorMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSNMPCounterMonitor(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public SNMPCounterMonitorMBean createSNMPCounterMonitor(String param0, SNMPCounterMonitorMBean param1) {
      return this._customizer.createSNMPCounterMonitor(param0, param1);
   }

   public void destroySNMPCounterMonitor(SNMPCounterMonitorMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 25);
         SNMPCounterMonitorMBean[] _old = this.getSNMPCounterMonitors();
         SNMPCounterMonitorMBean[] _new = (SNMPCounterMonitorMBean[])((SNMPCounterMonitorMBean[])this._getHelper()._removeElement(_old, SNMPCounterMonitorMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPCounterMonitors(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SNMPCounterMonitorMBean lookupSNMPCounterMonitor(String param0) {
      Object[] aary = (Object[])this._SNMPCounterMonitors;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SNMPCounterMonitorMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SNMPCounterMonitorMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public SNMPLogFilterMBean[] getSNMPLogFilters() {
      return this._SNMPLogFilters;
   }

   public boolean isSNMPLogFiltersInherited() {
      return false;
   }

   public boolean isSNMPLogFiltersSet() {
      return this._isSet(26);
   }

   public void setSNMPLogFilters(SNMPLogFilterMBean[] param0) throws InvalidAttributeValueException, ConfigurationException {
      SNMPLogFilterMBean[] param0 = param0 == null ? new SNMPLogFilterMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 26)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPLogFilterMBean[] _oldVal = this._SNMPLogFilters;
      this._SNMPLogFilters = (SNMPLogFilterMBean[])param0;
      this._postSet(26, _oldVal, param0);
   }

   public boolean addSNMPLogFilter(SNMPLogFilterMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 26)) {
         SNMPLogFilterMBean[] _new;
         if (this._isSet(26)) {
            _new = (SNMPLogFilterMBean[])((SNMPLogFilterMBean[])this._getHelper()._extendArray(this.getSNMPLogFilters(), SNMPLogFilterMBean.class, param0));
         } else {
            _new = new SNMPLogFilterMBean[]{param0};
         }

         try {
            this.setSNMPLogFilters(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof ConfigurationException) {
               throw (ConfigurationException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeSNMPLogFilter(SNMPLogFilterMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this.destroySNMPLogFilter(param0);
      return true;
   }

   public SNMPLogFilterMBean createSNMPLogFilter(String param0) {
      SNMPLogFilterMBeanImpl lookup = (SNMPLogFilterMBeanImpl)this.lookupSNMPLogFilter(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SNMPLogFilterMBeanImpl _val = new SNMPLogFilterMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSNMPLogFilter(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public SNMPLogFilterMBean createSNMPLogFilter(String param0, SNMPLogFilterMBean param1) {
      return this._customizer.createSNMPLogFilter(param0, param1);
   }

   public void destroySNMPLogFilter(SNMPLogFilterMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 26);
         SNMPLogFilterMBean[] _old = this.getSNMPLogFilters();
         SNMPLogFilterMBean[] _new = (SNMPLogFilterMBean[])((SNMPLogFilterMBean[])this._getHelper()._removeElement(_old, SNMPLogFilterMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPLogFilters(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SNMPLogFilterMBean lookupSNMPLogFilter(String param0) {
      Object[] aary = (Object[])this._SNMPLogFilters;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SNMPLogFilterMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SNMPLogFilterMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public SNMPAttributeChangeMBean[] getSNMPAttributeChanges() {
      return this._SNMPAttributeChanges;
   }

   public boolean isSNMPAttributeChangesInherited() {
      return false;
   }

   public boolean isSNMPAttributeChangesSet() {
      return this._isSet(27);
   }

   public void setSNMPAttributeChanges(SNMPAttributeChangeMBean[] param0) throws InvalidAttributeValueException, ConfigurationException {
      SNMPAttributeChangeMBean[] param0 = param0 == null ? new SNMPAttributeChangeMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 27)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SNMPAttributeChangeMBean[] _oldVal = this._SNMPAttributeChanges;
      this._SNMPAttributeChanges = (SNMPAttributeChangeMBean[])param0;
      this._postSet(27, _oldVal, param0);
   }

   public boolean addSNMPAttributeChange(SNMPAttributeChangeMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 27)) {
         SNMPAttributeChangeMBean[] _new;
         if (this._isSet(27)) {
            _new = (SNMPAttributeChangeMBean[])((SNMPAttributeChangeMBean[])this._getHelper()._extendArray(this.getSNMPAttributeChanges(), SNMPAttributeChangeMBean.class, param0));
         } else {
            _new = new SNMPAttributeChangeMBean[]{param0};
         }

         try {
            this.setSNMPAttributeChanges(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof ConfigurationException) {
               throw (ConfigurationException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeSNMPAttributeChange(SNMPAttributeChangeMBean param0) throws InvalidAttributeValueException, ConfigurationException {
      this.destroySNMPAttributeChange(param0);
      return true;
   }

   public SNMPAttributeChangeMBean createSNMPAttributeChange(String param0) {
      SNMPAttributeChangeMBeanImpl lookup = (SNMPAttributeChangeMBeanImpl)this.lookupSNMPAttributeChange(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SNMPAttributeChangeMBeanImpl _val = new SNMPAttributeChangeMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSNMPAttributeChange(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public SNMPAttributeChangeMBean createSNMPAttributeChange(String param0, SNMPAttributeChangeMBean param1) {
      return this._customizer.createSNMPAttributeChange(param0, param1);
   }

   public void destroySNMPAttributeChange(SNMPAttributeChangeMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 27);
         SNMPAttributeChangeMBean[] _old = this.getSNMPAttributeChanges();
         SNMPAttributeChangeMBean[] _new = (SNMPAttributeChangeMBean[])((SNMPAttributeChangeMBean[])this._getHelper()._removeElement(_old, SNMPAttributeChangeMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSNMPAttributeChanges(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SNMPAttributeChangeMBean lookupSNMPAttributeChange(String param0) {
      Object[] aary = (Object[])this._SNMPAttributeChanges;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SNMPAttributeChangeMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SNMPAttributeChangeMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public boolean isCommunityBasedAccessEnabled() {
      return this._CommunityBasedAccessEnabled;
   }

   public boolean isCommunityBasedAccessEnabledInherited() {
      return false;
   }

   public boolean isCommunityBasedAccessEnabledSet() {
      return this._isSet(28);
   }

   public void setCommunityBasedAccessEnabled(boolean param0) {
      boolean _oldVal = this._CommunityBasedAccessEnabled;
      this._CommunityBasedAccessEnabled = param0;
      this._postSet(28, _oldVal, param0);
   }

   public String getSNMPEngineId() {
      if (!this._isSet(29)) {
         try {
            return this.getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._SNMPEngineId;
   }

   public boolean isSNMPEngineIdInherited() {
      return false;
   }

   public boolean isSNMPEngineIdSet() {
      return this._isSet(29);
   }

   public void setSNMPEngineId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("SNMPEngineId", param0);
      String _oldVal = this._SNMPEngineId;
      this._SNMPEngineId = param0;
      this._postSet(29, _oldVal, param0);
   }

   public String getAuthenticationProtocol() {
      return this._AuthenticationProtocol;
   }

   public boolean isAuthenticationProtocolInherited() {
      return false;
   }

   public boolean isAuthenticationProtocolSet() {
      return this._isSet(30);
   }

   public void setAuthenticationProtocol(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"noAuth", "MD5", "SHA"};
      param0 = LegalChecks.checkInEnum("AuthenticationProtocol", param0, _set);
      LegalChecks.checkNonNull("AuthenticationProtocol", param0);
      String _oldVal = this._AuthenticationProtocol;
      this._AuthenticationProtocol = param0;
      this._postSet(30, _oldVal, param0);
   }

   public String getPrivacyProtocol() {
      return this._PrivacyProtocol;
   }

   public boolean isPrivacyProtocolInherited() {
      return false;
   }

   public boolean isPrivacyProtocolSet() {
      return this._isSet(31);
   }

   public void setPrivacyProtocol(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"noPriv", "DES", "AES_128"};
      param0 = LegalChecks.checkInEnum("PrivacyProtocol", param0, _set);
      LegalChecks.checkNonNull("PrivacyProtocol", param0);
      String _oldVal = this._PrivacyProtocol;
      this._PrivacyProtocol = param0;
      this._postSet(31, _oldVal, param0);
   }

   public int getInformRetryInterval() {
      return this._InformRetryInterval;
   }

   public boolean isInformRetryIntervalInherited() {
      return false;
   }

   public boolean isInformRetryIntervalSet() {
      return this._isSet(32);
   }

   public void setInformRetryInterval(int param0) {
      LegalChecks.checkInRange("InformRetryInterval", (long)param0, 3000L, 30000L);
      int _oldVal = this._InformRetryInterval;
      this._InformRetryInterval = param0;
      this._postSet(32, _oldVal, param0);
   }

   public int getMaxInformRetryCount() {
      return this._MaxInformRetryCount;
   }

   public boolean isMaxInformRetryCountInherited() {
      return false;
   }

   public boolean isMaxInformRetryCountSet() {
      return this._isSet(33);
   }

   public void setMaxInformRetryCount(int param0) {
      LegalChecks.checkInRange("MaxInformRetryCount", (long)param0, 1L, 3L);
      int _oldVal = this._MaxInformRetryCount;
      this._MaxInformRetryCount = param0;
      this._postSet(33, _oldVal, param0);
   }

   public long getLocalizedKeyCacheInvalidationInterval() {
      return this._LocalizedKeyCacheInvalidationInterval;
   }

   public boolean isLocalizedKeyCacheInvalidationIntervalInherited() {
      return false;
   }

   public boolean isLocalizedKeyCacheInvalidationIntervalSet() {
      return this._isSet(34);
   }

   public void setLocalizedKeyCacheInvalidationInterval(long param0) {
      LegalChecks.checkMax("LocalizedKeyCacheInvalidationInterval", param0, 86400000L);
      long _oldVal = this._LocalizedKeyCacheInvalidationInterval;
      this._LocalizedKeyCacheInvalidationInterval = param0;
      this._postSet(34, _oldVal, param0);
   }

   public boolean isSNMPAccessForUserMBeansEnabled() {
      return this._SNMPAccessForUserMBeansEnabled;
   }

   public boolean isSNMPAccessForUserMBeansEnabledInherited() {
      return false;
   }

   public boolean isSNMPAccessForUserMBeansEnabledSet() {
      return this._isSet(35);
   }

   public void setSNMPAccessForUserMBeansEnabled(boolean param0) {
      boolean _oldVal = this._SNMPAccessForUserMBeansEnabled;
      this._SNMPAccessForUserMBeansEnabled = param0;
      this._postSet(35, _oldVal, param0);
   }

   public boolean isInformEnabled() {
      return this._InformEnabled;
   }

   public boolean isInformEnabledInherited() {
      return false;
   }

   public boolean isInformEnabledSet() {
      return this._isSet(36);
   }

   public void setInformEnabled(boolean param0) {
      boolean _oldVal = this._InformEnabled;
      this._InformEnabled = param0;
      this._postSet(36, _oldVal, param0);
   }

   public int getMasterAgentXPort() {
      return this._MasterAgentXPort;
   }

   public boolean isMasterAgentXPortInherited() {
      return false;
   }

   public boolean isMasterAgentXPortSet() {
      return this._isSet(37);
   }

   public void setMasterAgentXPort(int param0) {
      LegalChecks.checkInRange("MasterAgentXPort", (long)param0, 1L, 65535L);
      int _oldVal = this._MasterAgentXPort;
      this._MasterAgentXPort = param0;
      this._postSet(37, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      SNMPValidator.validateSNMPAgent(this);
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 30;
      }

      try {
         switch (idx) {
            case 30:
               this._AuthenticationProtocol = "MD5";
               if (initOne) {
                  break;
               }
            case 17:
               this._CommunityPrefix = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._DebugLevel = 0;
               if (initOne) {
                  break;
               }
            case 32:
               this._InformRetryInterval = 10000;
               if (initOne) {
                  break;
               }
            case 12:
               this._ListenAddress = null;
               if (initOne) {
                  break;
               }
            case 34:
               this._LocalizedKeyCacheInvalidationInterval = 3600000L;
               if (initOne) {
                  break;
               }
            case 37:
               this._MasterAgentXPort = 705;
               if (initOne) {
                  break;
               }
            case 33:
               this._MaxInformRetryCount = 1;
               if (initOne) {
                  break;
               }
            case 15:
               this._MibDataRefreshInterval = 120;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 31:
               this._PrivacyProtocol = "AES_128";
               if (initOne) {
                  break;
               }
            case 27:
               this._SNMPAttributeChanges = new SNMPAttributeChangeMBean[0];
               if (initOne) {
                  break;
               }
            case 25:
               this._SNMPCounterMonitors = new SNMPCounterMonitorMBean[0];
               if (initOne) {
                  break;
               }
            case 29:
               this._SNMPEngineId = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._SNMPGaugeMonitors = new SNMPGaugeMonitorMBean[0];
               if (initOne) {
                  break;
               }
            case 26:
               this._SNMPLogFilters = new SNMPLogFilterMBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._SNMPPort = 161;
               if (initOne) {
                  break;
               }
            case 22:
               this._SNMPProxies = new SNMPProxyMBean[0];
               if (initOne) {
                  break;
               }
            case 24:
               this._SNMPStringMonitors = new SNMPStringMonitorMBean[0];
               if (initOne) {
                  break;
               }
            case 21:
               this._SNMPTrapDestinations = new SNMPTrapDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._SNMPTrapVersion = 3;
               if (initOne) {
                  break;
               }
            case 16:
               this._ServerStatusCheckIntervalFactor = 1;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 20:
               this._TargetedTrapDestinations = new SNMPTrapDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._UserDefinedMib = "na";
               if (initOne) {
                  break;
               }
            case 28:
               this._CommunityBasedAccessEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._Enabled = false;
               if (initOne) {
                  break;
               }
            case 36:
               this._InformEnabled = false;
               if (initOne) {
                  break;
               }
            case 35:
               this._SNMPAccessForUserMBeansEnabled = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._SendAutomaticTrapsEnabled = true;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "SNMPAgent";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AuthenticationProtocol")) {
         oldVal = this._AuthenticationProtocol;
         this._AuthenticationProtocol = (String)v;
         this._postSet(30, oldVal, this._AuthenticationProtocol);
      } else {
         boolean oldVal;
         if (name.equals("CommunityBasedAccessEnabled")) {
            oldVal = this._CommunityBasedAccessEnabled;
            this._CommunityBasedAccessEnabled = (Boolean)v;
            this._postSet(28, oldVal, this._CommunityBasedAccessEnabled);
         } else if (name.equals("CommunityPrefix")) {
            oldVal = this._CommunityPrefix;
            this._CommunityPrefix = (String)v;
            this._postSet(17, oldVal, this._CommunityPrefix);
         } else {
            int oldVal;
            if (name.equals("DebugLevel")) {
               oldVal = this._DebugLevel;
               this._DebugLevel = (Integer)v;
               this._postSet(19, oldVal, this._DebugLevel);
            } else if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("Enabled")) {
               oldVal = this._Enabled;
               this._Enabled = (Boolean)v;
               this._postSet(10, oldVal, this._Enabled);
            } else if (name.equals("InformEnabled")) {
               oldVal = this._InformEnabled;
               this._InformEnabled = (Boolean)v;
               this._postSet(36, oldVal, this._InformEnabled);
            } else if (name.equals("InformRetryInterval")) {
               oldVal = this._InformRetryInterval;
               this._InformRetryInterval = (Integer)v;
               this._postSet(32, oldVal, this._InformRetryInterval);
            } else if (name.equals("ListenAddress")) {
               oldVal = this._ListenAddress;
               this._ListenAddress = (String)v;
               this._postSet(12, oldVal, this._ListenAddress);
            } else if (name.equals("LocalizedKeyCacheInvalidationInterval")) {
               long oldVal = this._LocalizedKeyCacheInvalidationInterval;
               this._LocalizedKeyCacheInvalidationInterval = (Long)v;
               this._postSet(34, oldVal, this._LocalizedKeyCacheInvalidationInterval);
            } else if (name.equals("MasterAgentXPort")) {
               oldVal = this._MasterAgentXPort;
               this._MasterAgentXPort = (Integer)v;
               this._postSet(37, oldVal, this._MasterAgentXPort);
            } else if (name.equals("MaxInformRetryCount")) {
               oldVal = this._MaxInformRetryCount;
               this._MaxInformRetryCount = (Integer)v;
               this._postSet(33, oldVal, this._MaxInformRetryCount);
            } else if (name.equals("MibDataRefreshInterval")) {
               oldVal = this._MibDataRefreshInterval;
               this._MibDataRefreshInterval = (Integer)v;
               this._postSet(15, oldVal, this._MibDataRefreshInterval);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("PrivacyProtocol")) {
               oldVal = this._PrivacyProtocol;
               this._PrivacyProtocol = (String)v;
               this._postSet(31, oldVal, this._PrivacyProtocol);
            } else if (name.equals("SNMPAccessForUserMBeansEnabled")) {
               oldVal = this._SNMPAccessForUserMBeansEnabled;
               this._SNMPAccessForUserMBeansEnabled = (Boolean)v;
               this._postSet(35, oldVal, this._SNMPAccessForUserMBeansEnabled);
            } else if (name.equals("SNMPAttributeChanges")) {
               SNMPAttributeChangeMBean[] oldVal = this._SNMPAttributeChanges;
               this._SNMPAttributeChanges = (SNMPAttributeChangeMBean[])((SNMPAttributeChangeMBean[])v);
               this._postSet(27, oldVal, this._SNMPAttributeChanges);
            } else if (name.equals("SNMPCounterMonitors")) {
               SNMPCounterMonitorMBean[] oldVal = this._SNMPCounterMonitors;
               this._SNMPCounterMonitors = (SNMPCounterMonitorMBean[])((SNMPCounterMonitorMBean[])v);
               this._postSet(25, oldVal, this._SNMPCounterMonitors);
            } else if (name.equals("SNMPEngineId")) {
               oldVal = this._SNMPEngineId;
               this._SNMPEngineId = (String)v;
               this._postSet(29, oldVal, this._SNMPEngineId);
            } else if (name.equals("SNMPGaugeMonitors")) {
               SNMPGaugeMonitorMBean[] oldVal = this._SNMPGaugeMonitors;
               this._SNMPGaugeMonitors = (SNMPGaugeMonitorMBean[])((SNMPGaugeMonitorMBean[])v);
               this._postSet(23, oldVal, this._SNMPGaugeMonitors);
            } else if (name.equals("SNMPLogFilters")) {
               SNMPLogFilterMBean[] oldVal = this._SNMPLogFilters;
               this._SNMPLogFilters = (SNMPLogFilterMBean[])((SNMPLogFilterMBean[])v);
               this._postSet(26, oldVal, this._SNMPLogFilters);
            } else if (name.equals("SNMPPort")) {
               oldVal = this._SNMPPort;
               this._SNMPPort = (Integer)v;
               this._postSet(13, oldVal, this._SNMPPort);
            } else if (name.equals("SNMPProxies")) {
               SNMPProxyMBean[] oldVal = this._SNMPProxies;
               this._SNMPProxies = (SNMPProxyMBean[])((SNMPProxyMBean[])v);
               this._postSet(22, oldVal, this._SNMPProxies);
            } else if (name.equals("SNMPStringMonitors")) {
               SNMPStringMonitorMBean[] oldVal = this._SNMPStringMonitors;
               this._SNMPStringMonitors = (SNMPStringMonitorMBean[])((SNMPStringMonitorMBean[])v);
               this._postSet(24, oldVal, this._SNMPStringMonitors);
            } else {
               SNMPTrapDestinationMBean[] oldVal;
               if (name.equals("SNMPTrapDestinations")) {
                  oldVal = this._SNMPTrapDestinations;
                  this._SNMPTrapDestinations = (SNMPTrapDestinationMBean[])((SNMPTrapDestinationMBean[])v);
                  this._postSet(21, oldVal, this._SNMPTrapDestinations);
               } else if (name.equals("SNMPTrapVersion")) {
                  oldVal = this._SNMPTrapVersion;
                  this._SNMPTrapVersion = (Integer)v;
                  this._postSet(14, oldVal, this._SNMPTrapVersion);
               } else if (name.equals("SendAutomaticTrapsEnabled")) {
                  oldVal = this._SendAutomaticTrapsEnabled;
                  this._SendAutomaticTrapsEnabled = (Boolean)v;
                  this._postSet(11, oldVal, this._SendAutomaticTrapsEnabled);
               } else if (name.equals("ServerStatusCheckIntervalFactor")) {
                  oldVal = this._ServerStatusCheckIntervalFactor;
                  this._ServerStatusCheckIntervalFactor = (Integer)v;
                  this._postSet(16, oldVal, this._ServerStatusCheckIntervalFactor);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("TargetedTrapDestinations")) {
                  oldVal = this._TargetedTrapDestinations;
                  this._TargetedTrapDestinations = (SNMPTrapDestinationMBean[])((SNMPTrapDestinationMBean[])v);
                  this._postSet(20, oldVal, this._TargetedTrapDestinations);
               } else if (name.equals("UserDefinedMib")) {
                  oldVal = this._UserDefinedMib;
                  this._UserDefinedMib = (String)v;
                  this._postSet(18, oldVal, this._UserDefinedMib);
               } else if (name.equals("customizer")) {
                  SNMPAgent oldVal = this._customizer;
                  this._customizer = (SNMPAgent)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AuthenticationProtocol")) {
         return this._AuthenticationProtocol;
      } else if (name.equals("CommunityBasedAccessEnabled")) {
         return new Boolean(this._CommunityBasedAccessEnabled);
      } else if (name.equals("CommunityPrefix")) {
         return this._CommunityPrefix;
      } else if (name.equals("DebugLevel")) {
         return new Integer(this._DebugLevel);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Enabled")) {
         return new Boolean(this._Enabled);
      } else if (name.equals("InformEnabled")) {
         return new Boolean(this._InformEnabled);
      } else if (name.equals("InformRetryInterval")) {
         return new Integer(this._InformRetryInterval);
      } else if (name.equals("ListenAddress")) {
         return this._ListenAddress;
      } else if (name.equals("LocalizedKeyCacheInvalidationInterval")) {
         return new Long(this._LocalizedKeyCacheInvalidationInterval);
      } else if (name.equals("MasterAgentXPort")) {
         return new Integer(this._MasterAgentXPort);
      } else if (name.equals("MaxInformRetryCount")) {
         return new Integer(this._MaxInformRetryCount);
      } else if (name.equals("MibDataRefreshInterval")) {
         return new Integer(this._MibDataRefreshInterval);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PrivacyProtocol")) {
         return this._PrivacyProtocol;
      } else if (name.equals("SNMPAccessForUserMBeansEnabled")) {
         return new Boolean(this._SNMPAccessForUserMBeansEnabled);
      } else if (name.equals("SNMPAttributeChanges")) {
         return this._SNMPAttributeChanges;
      } else if (name.equals("SNMPCounterMonitors")) {
         return this._SNMPCounterMonitors;
      } else if (name.equals("SNMPEngineId")) {
         return this._SNMPEngineId;
      } else if (name.equals("SNMPGaugeMonitors")) {
         return this._SNMPGaugeMonitors;
      } else if (name.equals("SNMPLogFilters")) {
         return this._SNMPLogFilters;
      } else if (name.equals("SNMPPort")) {
         return new Integer(this._SNMPPort);
      } else if (name.equals("SNMPProxies")) {
         return this._SNMPProxies;
      } else if (name.equals("SNMPStringMonitors")) {
         return this._SNMPStringMonitors;
      } else if (name.equals("SNMPTrapDestinations")) {
         return this._SNMPTrapDestinations;
      } else if (name.equals("SNMPTrapVersion")) {
         return new Integer(this._SNMPTrapVersion);
      } else if (name.equals("SendAutomaticTrapsEnabled")) {
         return new Boolean(this._SendAutomaticTrapsEnabled);
      } else if (name.equals("ServerStatusCheckIntervalFactor")) {
         return new Integer(this._ServerStatusCheckIntervalFactor);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("TargetedTrapDestinations")) {
         return this._TargetedTrapDestinations;
      } else if (name.equals("UserDefinedMib")) {
         return this._UserDefinedMib;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("AuthenticationProtocol", "MD5");
      } catch (IllegalArgumentException var3) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property AuthenticationProtocol in SNMPAgentMBean" + var3.getMessage());
      }

      try {
         LegalChecks.checkNonNull("PrivacyProtocol", "AES_128");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property PrivacyProtocol in SNMPAgentMBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonEmptyString("UserDefinedMib", "na");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is zero-length. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-zero-length value on @default annotation. Refer annotation legalZeroLength on property UserDefinedMib in SNMPAgentMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 6:
            case 8:
            case 12:
            case 13:
            case 24:
            case 26:
            case 27:
            case 29:
            case 31:
            case 32:
            case 33:
            case 34:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            default:
               break;
            case 7:
               if (s.equals("enabled")) {
                  return 10;
               }
               break;
            case 9:
               if (s.equals("snmp-port")) {
                  return 13;
               }
               break;
            case 10:
               if (s.equals("snmp-proxy")) {
                  return 22;
               }
               break;
            case 11:
               if (s.equals("debug-level")) {
                  return 19;
               }
               break;
            case 14:
               if (s.equals("listen-address")) {
                  return 12;
               }

               if (s.equals("snmp-engine-id")) {
                  return 29;
               }

               if (s.equals("inform-enabled")) {
                  return 36;
               }
               break;
            case 15:
               if (s.equals("snmp-log-filter")) {
                  return 26;
               }
               break;
            case 16:
               if (s.equals("community-prefix")) {
                  return 17;
               }

               if (s.equals("privacy-protocol")) {
                  return 31;
               }

               if (s.equals("user-defined-mib")) {
                  return 18;
               }
               break;
            case 17:
               if (s.equals("snmp-trap-version")) {
                  return 14;
               }
               break;
            case 18:
               if (s.equals("master-agentx-port")) {
                  return 37;
               }

               if (s.equals("snmp-gauge-monitor")) {
                  return 23;
               }
               break;
            case 19:
               if (s.equals("snmp-string-monitor")) {
                  return 24;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("snmp-counter-monitor")) {
                  return 25;
               }
               break;
            case 21:
               if (s.equals("inform-retry-interval")) {
                  return 32;
               }

               if (s.equals("snmp-attribute-change")) {
                  return 27;
               }

               if (s.equals("snmp-trap-destination")) {
                  return 21;
               }
               break;
            case 22:
               if (s.equals("max-inform-retry-count")) {
                  return 33;
               }
               break;
            case 23:
               if (s.equals("authentication-protocol")) {
                  return 30;
               }
               break;
            case 25:
               if (s.equals("mib-data-refresh-interval")) {
                  return 15;
               }

               if (s.equals("targeted-trap-destination")) {
                  return 20;
               }
               break;
            case 28:
               if (s.equals("send-automatic-traps-enabled")) {
                  return 11;
               }
               break;
            case 30:
               if (s.equals("community-based-access-enabled")) {
                  return 28;
               }
               break;
            case 35:
               if (s.equals("server-status-check-interval-factor")) {
                  return 16;
               }

               if (s.equals("snmp-access-for-userm-beans-enabled")) {
                  return 35;
               }
               break;
            case 41:
               if (s.equals("localized-key-cache-invalidation-interval")) {
                  return 34;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 21:
               return new SNMPTrapDestinationMBeanImpl.SchemaHelper2();
            case 22:
               return new SNMPProxyMBeanImpl.SchemaHelper2();
            case 23:
               return new SNMPGaugeMonitorMBeanImpl.SchemaHelper2();
            case 24:
               return new SNMPStringMonitorMBeanImpl.SchemaHelper2();
            case 25:
               return new SNMPCounterMonitorMBeanImpl.SchemaHelper2();
            case 26:
               return new SNMPLogFilterMBeanImpl.SchemaHelper2();
            case 27:
               return new SNMPAttributeChangeMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "enabled";
            case 11:
               return "send-automatic-traps-enabled";
            case 12:
               return "listen-address";
            case 13:
               return "snmp-port";
            case 14:
               return "snmp-trap-version";
            case 15:
               return "mib-data-refresh-interval";
            case 16:
               return "server-status-check-interval-factor";
            case 17:
               return "community-prefix";
            case 18:
               return "user-defined-mib";
            case 19:
               return "debug-level";
            case 20:
               return "targeted-trap-destination";
            case 21:
               return "snmp-trap-destination";
            case 22:
               return "snmp-proxy";
            case 23:
               return "snmp-gauge-monitor";
            case 24:
               return "snmp-string-monitor";
            case 25:
               return "snmp-counter-monitor";
            case 26:
               return "snmp-log-filter";
            case 27:
               return "snmp-attribute-change";
            case 28:
               return "community-based-access-enabled";
            case 29:
               return "snmp-engine-id";
            case 30:
               return "authentication-protocol";
            case 31:
               return "privacy-protocol";
            case 32:
               return "inform-retry-interval";
            case 33:
               return "max-inform-retry-count";
            case 34:
               return "localized-key-cache-invalidation-interval";
            case 35:
               return "snmp-access-for-userm-beans-enabled";
            case 36:
               return "inform-enabled";
            case 37:
               return "master-agentx-port";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            default:
               return super.isArray(propIndex);
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private SNMPAgentMBeanImpl bean;

      protected Helper(SNMPAgentMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "Enabled";
            case 11:
               return "SendAutomaticTrapsEnabled";
            case 12:
               return "ListenAddress";
            case 13:
               return "SNMPPort";
            case 14:
               return "SNMPTrapVersion";
            case 15:
               return "MibDataRefreshInterval";
            case 16:
               return "ServerStatusCheckIntervalFactor";
            case 17:
               return "CommunityPrefix";
            case 18:
               return "UserDefinedMib";
            case 19:
               return "DebugLevel";
            case 20:
               return "TargetedTrapDestinations";
            case 21:
               return "SNMPTrapDestinations";
            case 22:
               return "SNMPProxies";
            case 23:
               return "SNMPGaugeMonitors";
            case 24:
               return "SNMPStringMonitors";
            case 25:
               return "SNMPCounterMonitors";
            case 26:
               return "SNMPLogFilters";
            case 27:
               return "SNMPAttributeChanges";
            case 28:
               return "CommunityBasedAccessEnabled";
            case 29:
               return "SNMPEngineId";
            case 30:
               return "AuthenticationProtocol";
            case 31:
               return "PrivacyProtocol";
            case 32:
               return "InformRetryInterval";
            case 33:
               return "MaxInformRetryCount";
            case 34:
               return "LocalizedKeyCacheInvalidationInterval";
            case 35:
               return "SNMPAccessForUserMBeansEnabled";
            case 36:
               return "InformEnabled";
            case 37:
               return "MasterAgentXPort";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AuthenticationProtocol")) {
            return 30;
         } else if (propName.equals("CommunityPrefix")) {
            return 17;
         } else if (propName.equals("DebugLevel")) {
            return 19;
         } else if (propName.equals("InformRetryInterval")) {
            return 32;
         } else if (propName.equals("ListenAddress")) {
            return 12;
         } else if (propName.equals("LocalizedKeyCacheInvalidationInterval")) {
            return 34;
         } else if (propName.equals("MasterAgentXPort")) {
            return 37;
         } else if (propName.equals("MaxInformRetryCount")) {
            return 33;
         } else if (propName.equals("MibDataRefreshInterval")) {
            return 15;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PrivacyProtocol")) {
            return 31;
         } else if (propName.equals("SNMPAttributeChanges")) {
            return 27;
         } else if (propName.equals("SNMPCounterMonitors")) {
            return 25;
         } else if (propName.equals("SNMPEngineId")) {
            return 29;
         } else if (propName.equals("SNMPGaugeMonitors")) {
            return 23;
         } else if (propName.equals("SNMPLogFilters")) {
            return 26;
         } else if (propName.equals("SNMPPort")) {
            return 13;
         } else if (propName.equals("SNMPProxies")) {
            return 22;
         } else if (propName.equals("SNMPStringMonitors")) {
            return 24;
         } else if (propName.equals("SNMPTrapDestinations")) {
            return 21;
         } else if (propName.equals("SNMPTrapVersion")) {
            return 14;
         } else if (propName.equals("ServerStatusCheckIntervalFactor")) {
            return 16;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("TargetedTrapDestinations")) {
            return 20;
         } else if (propName.equals("UserDefinedMib")) {
            return 18;
         } else if (propName.equals("CommunityBasedAccessEnabled")) {
            return 28;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("Enabled")) {
            return 10;
         } else if (propName.equals("InformEnabled")) {
            return 36;
         } else if (propName.equals("SNMPAccessForUserMBeansEnabled")) {
            return 35;
         } else {
            return propName.equals("SendAutomaticTrapsEnabled") ? 11 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getSNMPAttributeChanges()));
         iterators.add(new ArrayIterator(this.bean.getSNMPCounterMonitors()));
         iterators.add(new ArrayIterator(this.bean.getSNMPGaugeMonitors()));
         iterators.add(new ArrayIterator(this.bean.getSNMPLogFilters()));
         iterators.add(new ArrayIterator(this.bean.getSNMPProxies()));
         iterators.add(new ArrayIterator(this.bean.getSNMPStringMonitors()));
         iterators.add(new ArrayIterator(this.bean.getSNMPTrapDestinations()));
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isAuthenticationProtocolSet()) {
               buf.append("AuthenticationProtocol");
               buf.append(String.valueOf(this.bean.getAuthenticationProtocol()));
            }

            if (this.bean.isCommunityPrefixSet()) {
               buf.append("CommunityPrefix");
               buf.append(String.valueOf(this.bean.getCommunityPrefix()));
            }

            if (this.bean.isDebugLevelSet()) {
               buf.append("DebugLevel");
               buf.append(String.valueOf(this.bean.getDebugLevel()));
            }

            if (this.bean.isInformRetryIntervalSet()) {
               buf.append("InformRetryInterval");
               buf.append(String.valueOf(this.bean.getInformRetryInterval()));
            }

            if (this.bean.isListenAddressSet()) {
               buf.append("ListenAddress");
               buf.append(String.valueOf(this.bean.getListenAddress()));
            }

            if (this.bean.isLocalizedKeyCacheInvalidationIntervalSet()) {
               buf.append("LocalizedKeyCacheInvalidationInterval");
               buf.append(String.valueOf(this.bean.getLocalizedKeyCacheInvalidationInterval()));
            }

            if (this.bean.isMasterAgentXPortSet()) {
               buf.append("MasterAgentXPort");
               buf.append(String.valueOf(this.bean.getMasterAgentXPort()));
            }

            if (this.bean.isMaxInformRetryCountSet()) {
               buf.append("MaxInformRetryCount");
               buf.append(String.valueOf(this.bean.getMaxInformRetryCount()));
            }

            if (this.bean.isMibDataRefreshIntervalSet()) {
               buf.append("MibDataRefreshInterval");
               buf.append(String.valueOf(this.bean.getMibDataRefreshInterval()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPrivacyProtocolSet()) {
               buf.append("PrivacyProtocol");
               buf.append(String.valueOf(this.bean.getPrivacyProtocol()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getSNMPAttributeChanges().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPAttributeChanges()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPCounterMonitors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPCounterMonitors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSNMPEngineIdSet()) {
               buf.append("SNMPEngineId");
               buf.append(String.valueOf(this.bean.getSNMPEngineId()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPGaugeMonitors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPGaugeMonitors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPLogFilters().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPLogFilters()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSNMPPortSet()) {
               buf.append("SNMPPort");
               buf.append(String.valueOf(this.bean.getSNMPPort()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPProxies().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPProxies()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPStringMonitors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPStringMonitors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSNMPTrapDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSNMPTrapDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSNMPTrapVersionSet()) {
               buf.append("SNMPTrapVersion");
               buf.append(String.valueOf(this.bean.getSNMPTrapVersion()));
            }

            if (this.bean.isServerStatusCheckIntervalFactorSet()) {
               buf.append("ServerStatusCheckIntervalFactor");
               buf.append(String.valueOf(this.bean.getServerStatusCheckIntervalFactor()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetedTrapDestinationsSet()) {
               buf.append("TargetedTrapDestinations");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargetedTrapDestinations())));
            }

            if (this.bean.isUserDefinedMibSet()) {
               buf.append("UserDefinedMib");
               buf.append(String.valueOf(this.bean.getUserDefinedMib()));
            }

            if (this.bean.isCommunityBasedAccessEnabledSet()) {
               buf.append("CommunityBasedAccessEnabled");
               buf.append(String.valueOf(this.bean.isCommunityBasedAccessEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
            }

            if (this.bean.isInformEnabledSet()) {
               buf.append("InformEnabled");
               buf.append(String.valueOf(this.bean.isInformEnabled()));
            }

            if (this.bean.isSNMPAccessForUserMBeansEnabledSet()) {
               buf.append("SNMPAccessForUserMBeansEnabled");
               buf.append(String.valueOf(this.bean.isSNMPAccessForUserMBeansEnabled()));
            }

            if (this.bean.isSendAutomaticTrapsEnabledSet()) {
               buf.append("SendAutomaticTrapsEnabled");
               buf.append(String.valueOf(this.bean.isSendAutomaticTrapsEnabled()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            SNMPAgentMBeanImpl otherTyped = (SNMPAgentMBeanImpl)other;
            this.computeDiff("AuthenticationProtocol", this.bean.getAuthenticationProtocol(), otherTyped.getAuthenticationProtocol(), true);
            this.computeDiff("CommunityPrefix", this.bean.getCommunityPrefix(), otherTyped.getCommunityPrefix(), true);
            this.computeDiff("DebugLevel", this.bean.getDebugLevel(), otherTyped.getDebugLevel(), true);
            this.computeDiff("InformRetryInterval", this.bean.getInformRetryInterval(), otherTyped.getInformRetryInterval(), true);
            this.computeDiff("ListenAddress", this.bean.getListenAddress(), otherTyped.getListenAddress(), true);
            this.computeDiff("LocalizedKeyCacheInvalidationInterval", this.bean.getLocalizedKeyCacheInvalidationInterval(), otherTyped.getLocalizedKeyCacheInvalidationInterval(), true);
            this.computeDiff("MasterAgentXPort", this.bean.getMasterAgentXPort(), otherTyped.getMasterAgentXPort(), true);
            this.computeDiff("MaxInformRetryCount", this.bean.getMaxInformRetryCount(), otherTyped.getMaxInformRetryCount(), true);
            this.computeDiff("MibDataRefreshInterval", this.bean.getMibDataRefreshInterval(), otherTyped.getMibDataRefreshInterval(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PrivacyProtocol", this.bean.getPrivacyProtocol(), otherTyped.getPrivacyProtocol(), true);
            this.computeChildDiff("SNMPAttributeChanges", this.bean.getSNMPAttributeChanges(), otherTyped.getSNMPAttributeChanges(), true);
            this.computeChildDiff("SNMPCounterMonitors", this.bean.getSNMPCounterMonitors(), otherTyped.getSNMPCounterMonitors(), true);
            this.computeDiff("SNMPEngineId", this.bean.getSNMPEngineId(), otherTyped.getSNMPEngineId(), true);
            this.computeChildDiff("SNMPGaugeMonitors", this.bean.getSNMPGaugeMonitors(), otherTyped.getSNMPGaugeMonitors(), true);
            this.computeChildDiff("SNMPLogFilters", this.bean.getSNMPLogFilters(), otherTyped.getSNMPLogFilters(), true);
            this.computeDiff("SNMPPort", this.bean.getSNMPPort(), otherTyped.getSNMPPort(), true);
            this.computeChildDiff("SNMPProxies", this.bean.getSNMPProxies(), otherTyped.getSNMPProxies(), true);
            this.computeChildDiff("SNMPStringMonitors", this.bean.getSNMPStringMonitors(), otherTyped.getSNMPStringMonitors(), true);
            this.computeChildDiff("SNMPTrapDestinations", this.bean.getSNMPTrapDestinations(), otherTyped.getSNMPTrapDestinations(), true);
            this.computeDiff("SNMPTrapVersion", this.bean.getSNMPTrapVersion(), otherTyped.getSNMPTrapVersion(), true);
            this.computeDiff("ServerStatusCheckIntervalFactor", this.bean.getServerStatusCheckIntervalFactor(), otherTyped.getServerStatusCheckIntervalFactor(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("TargetedTrapDestinations", this.bean.getTargetedTrapDestinations(), otherTyped.getTargetedTrapDestinations(), true);
            }

            this.computeDiff("UserDefinedMib", this.bean.getUserDefinedMib(), otherTyped.getUserDefinedMib(), true);
            this.computeDiff("CommunityBasedAccessEnabled", this.bean.isCommunityBasedAccessEnabled(), otherTyped.isCommunityBasedAccessEnabled(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
            this.computeDiff("InformEnabled", this.bean.isInformEnabled(), otherTyped.isInformEnabled(), true);
            this.computeDiff("SNMPAccessForUserMBeansEnabled", this.bean.isSNMPAccessForUserMBeansEnabled(), otherTyped.isSNMPAccessForUserMBeansEnabled(), true);
            this.computeDiff("SendAutomaticTrapsEnabled", this.bean.isSendAutomaticTrapsEnabled(), otherTyped.isSendAutomaticTrapsEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SNMPAgentMBeanImpl original = (SNMPAgentMBeanImpl)event.getSourceBean();
            SNMPAgentMBeanImpl proposed = (SNMPAgentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AuthenticationProtocol")) {
                  original.setAuthenticationProtocol(proposed.getAuthenticationProtocol());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("CommunityPrefix")) {
                  original.setCommunityPrefix(proposed.getCommunityPrefix());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("DebugLevel")) {
                  original.setDebugLevel(proposed.getDebugLevel());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("InformRetryInterval")) {
                  original.setInformRetryInterval(proposed.getInformRetryInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("ListenAddress")) {
                  original.setListenAddress(proposed.getListenAddress());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("LocalizedKeyCacheInvalidationInterval")) {
                  original.setLocalizedKeyCacheInvalidationInterval(proposed.getLocalizedKeyCacheInvalidationInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("MasterAgentXPort")) {
                  original.setMasterAgentXPort(proposed.getMasterAgentXPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("MaxInformRetryCount")) {
                  original.setMaxInformRetryCount(proposed.getMaxInformRetryCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("MibDataRefreshInterval")) {
                  original.setMibDataRefreshInterval(proposed.getMibDataRefreshInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("PrivacyProtocol")) {
                  original.setPrivacyProtocol(proposed.getPrivacyProtocol());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("SNMPAttributeChanges")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSNMPAttributeChange((SNMPAttributeChangeMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSNMPAttributeChange((SNMPAttributeChangeMBean)update.getRemovedObject());
                  }

                  if (original.getSNMPAttributeChanges() == null || original.getSNMPAttributeChanges().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  }
               } else if (prop.equals("SNMPCounterMonitors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSNMPCounterMonitor((SNMPCounterMonitorMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSNMPCounterMonitor((SNMPCounterMonitorMBean)update.getRemovedObject());
                  }

                  if (original.getSNMPCounterMonitors() == null || original.getSNMPCounterMonitors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
                  }
               } else if (prop.equals("SNMPEngineId")) {
                  original.setSNMPEngineId(proposed.getSNMPEngineId());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("SNMPGaugeMonitors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSNMPGaugeMonitor((SNMPGaugeMonitorMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSNMPGaugeMonitor((SNMPGaugeMonitorMBean)update.getRemovedObject());
                  }

                  if (original.getSNMPGaugeMonitors() == null || original.getSNMPGaugeMonitors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  }
               } else if (prop.equals("SNMPLogFilters")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSNMPLogFilter((SNMPLogFilterMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSNMPLogFilter((SNMPLogFilterMBean)update.getRemovedObject());
                  }

                  if (original.getSNMPLogFilters() == null || original.getSNMPLogFilters().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 26);
                  }
               } else if (prop.equals("SNMPPort")) {
                  original.setSNMPPort(proposed.getSNMPPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("SNMPProxies")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSNMPProxy((SNMPProxyMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSNMPProxy((SNMPProxyMBean)update.getRemovedObject());
                  }

                  if (original.getSNMPProxies() == null || original.getSNMPProxies().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  }
               } else if (prop.equals("SNMPStringMonitors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSNMPStringMonitor((SNMPStringMonitorMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSNMPStringMonitor((SNMPStringMonitorMBean)update.getRemovedObject());
                  }

                  if (original.getSNMPStringMonitors() == null || original.getSNMPStringMonitors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  }
               } else if (prop.equals("SNMPTrapDestinations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSNMPTrapDestination((SNMPTrapDestinationMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSNMPTrapDestination((SNMPTrapDestinationMBean)update.getRemovedObject());
                  }

                  if (original.getSNMPTrapDestinations() == null || original.getSNMPTrapDestinations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  }
               } else if (prop.equals("SNMPTrapVersion")) {
                  original.setSNMPTrapVersion(proposed.getSNMPTrapVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("ServerStatusCheckIntervalFactor")) {
                  original.setServerStatusCheckIntervalFactor(proposed.getServerStatusCheckIntervalFactor());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("Tags")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addTag((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTag((String)update.getRemovedObject());
                  }

                  if (original.getTags() == null || original.getTags().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("TargetedTrapDestinations")) {
                  original.setTargetedTrapDestinationsAsString(proposed.getTargetedTrapDestinationsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("UserDefinedMib")) {
                  original.setUserDefinedMib(proposed.getUserDefinedMib());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("CommunityBasedAccessEnabled")) {
                  original.setCommunityBasedAccessEnabled(proposed.isCommunityBasedAccessEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (!prop.equals("DynamicallyCreated")) {
                  if (prop.equals("Enabled")) {
                     original.setEnabled(proposed.isEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("InformEnabled")) {
                     original.setInformEnabled(proposed.isInformEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 36);
                  } else if (prop.equals("SNMPAccessForUserMBeansEnabled")) {
                     original.setSNMPAccessForUserMBeansEnabled(proposed.isSNMPAccessForUserMBeansEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 35);
                  } else if (prop.equals("SendAutomaticTrapsEnabled")) {
                     original.setSendAutomaticTrapsEnabled(proposed.isSendAutomaticTrapsEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else {
                     super.applyPropertyUpdate(event, update);
                  }
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            SNMPAgentMBeanImpl copy = (SNMPAgentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AuthenticationProtocol")) && this.bean.isAuthenticationProtocolSet()) {
               copy.setAuthenticationProtocol(this.bean.getAuthenticationProtocol());
            }

            if ((excludeProps == null || !excludeProps.contains("CommunityPrefix")) && this.bean.isCommunityPrefixSet()) {
               copy.setCommunityPrefix(this.bean.getCommunityPrefix());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugLevel")) && this.bean.isDebugLevelSet()) {
               copy.setDebugLevel(this.bean.getDebugLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("InformRetryInterval")) && this.bean.isInformRetryIntervalSet()) {
               copy.setInformRetryInterval(this.bean.getInformRetryInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenAddress")) && this.bean.isListenAddressSet()) {
               copy.setListenAddress(this.bean.getListenAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalizedKeyCacheInvalidationInterval")) && this.bean.isLocalizedKeyCacheInvalidationIntervalSet()) {
               copy.setLocalizedKeyCacheInvalidationInterval(this.bean.getLocalizedKeyCacheInvalidationInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("MasterAgentXPort")) && this.bean.isMasterAgentXPortSet()) {
               copy.setMasterAgentXPort(this.bean.getMasterAgentXPort());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxInformRetryCount")) && this.bean.isMaxInformRetryCountSet()) {
               copy.setMaxInformRetryCount(this.bean.getMaxInformRetryCount());
            }

            if ((excludeProps == null || !excludeProps.contains("MibDataRefreshInterval")) && this.bean.isMibDataRefreshIntervalSet()) {
               copy.setMibDataRefreshInterval(this.bean.getMibDataRefreshInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PrivacyProtocol")) && this.bean.isPrivacyProtocolSet()) {
               copy.setPrivacyProtocol(this.bean.getPrivacyProtocol());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("SNMPAttributeChanges")) && this.bean.isSNMPAttributeChangesSet() && !copy._isSet(27)) {
               SNMPAttributeChangeMBean[] oldSNMPAttributeChanges = this.bean.getSNMPAttributeChanges();
               SNMPAttributeChangeMBean[] newSNMPAttributeChanges = new SNMPAttributeChangeMBean[oldSNMPAttributeChanges.length];

               for(i = 0; i < newSNMPAttributeChanges.length; ++i) {
                  newSNMPAttributeChanges[i] = (SNMPAttributeChangeMBean)((SNMPAttributeChangeMBean)this.createCopy((AbstractDescriptorBean)oldSNMPAttributeChanges[i], includeObsolete));
               }

               copy.setSNMPAttributeChanges(newSNMPAttributeChanges);
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPCounterMonitors")) && this.bean.isSNMPCounterMonitorsSet() && !copy._isSet(25)) {
               SNMPCounterMonitorMBean[] oldSNMPCounterMonitors = this.bean.getSNMPCounterMonitors();
               SNMPCounterMonitorMBean[] newSNMPCounterMonitors = new SNMPCounterMonitorMBean[oldSNMPCounterMonitors.length];

               for(i = 0; i < newSNMPCounterMonitors.length; ++i) {
                  newSNMPCounterMonitors[i] = (SNMPCounterMonitorMBean)((SNMPCounterMonitorMBean)this.createCopy((AbstractDescriptorBean)oldSNMPCounterMonitors[i], includeObsolete));
               }

               copy.setSNMPCounterMonitors(newSNMPCounterMonitors);
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPEngineId")) && this.bean.isSNMPEngineIdSet()) {
               copy.setSNMPEngineId(this.bean.getSNMPEngineId());
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPGaugeMonitors")) && this.bean.isSNMPGaugeMonitorsSet() && !copy._isSet(23)) {
               SNMPGaugeMonitorMBean[] oldSNMPGaugeMonitors = this.bean.getSNMPGaugeMonitors();
               SNMPGaugeMonitorMBean[] newSNMPGaugeMonitors = new SNMPGaugeMonitorMBean[oldSNMPGaugeMonitors.length];

               for(i = 0; i < newSNMPGaugeMonitors.length; ++i) {
                  newSNMPGaugeMonitors[i] = (SNMPGaugeMonitorMBean)((SNMPGaugeMonitorMBean)this.createCopy((AbstractDescriptorBean)oldSNMPGaugeMonitors[i], includeObsolete));
               }

               copy.setSNMPGaugeMonitors(newSNMPGaugeMonitors);
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPLogFilters")) && this.bean.isSNMPLogFiltersSet() && !copy._isSet(26)) {
               SNMPLogFilterMBean[] oldSNMPLogFilters = this.bean.getSNMPLogFilters();
               SNMPLogFilterMBean[] newSNMPLogFilters = new SNMPLogFilterMBean[oldSNMPLogFilters.length];

               for(i = 0; i < newSNMPLogFilters.length; ++i) {
                  newSNMPLogFilters[i] = (SNMPLogFilterMBean)((SNMPLogFilterMBean)this.createCopy((AbstractDescriptorBean)oldSNMPLogFilters[i], includeObsolete));
               }

               copy.setSNMPLogFilters(newSNMPLogFilters);
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPPort")) && this.bean.isSNMPPortSet()) {
               copy.setSNMPPort(this.bean.getSNMPPort());
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPProxies")) && this.bean.isSNMPProxiesSet() && !copy._isSet(22)) {
               SNMPProxyMBean[] oldSNMPProxies = this.bean.getSNMPProxies();
               SNMPProxyMBean[] newSNMPProxies = new SNMPProxyMBean[oldSNMPProxies.length];

               for(i = 0; i < newSNMPProxies.length; ++i) {
                  newSNMPProxies[i] = (SNMPProxyMBean)((SNMPProxyMBean)this.createCopy((AbstractDescriptorBean)oldSNMPProxies[i], includeObsolete));
               }

               copy.setSNMPProxies(newSNMPProxies);
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPStringMonitors")) && this.bean.isSNMPStringMonitorsSet() && !copy._isSet(24)) {
               SNMPStringMonitorMBean[] oldSNMPStringMonitors = this.bean.getSNMPStringMonitors();
               SNMPStringMonitorMBean[] newSNMPStringMonitors = new SNMPStringMonitorMBean[oldSNMPStringMonitors.length];

               for(i = 0; i < newSNMPStringMonitors.length; ++i) {
                  newSNMPStringMonitors[i] = (SNMPStringMonitorMBean)((SNMPStringMonitorMBean)this.createCopy((AbstractDescriptorBean)oldSNMPStringMonitors[i], includeObsolete));
               }

               copy.setSNMPStringMonitors(newSNMPStringMonitors);
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPTrapDestinations")) && this.bean.isSNMPTrapDestinationsSet() && !copy._isSet(21)) {
               SNMPTrapDestinationMBean[] oldSNMPTrapDestinations = this.bean.getSNMPTrapDestinations();
               SNMPTrapDestinationMBean[] newSNMPTrapDestinations = new SNMPTrapDestinationMBean[oldSNMPTrapDestinations.length];

               for(i = 0; i < newSNMPTrapDestinations.length; ++i) {
                  newSNMPTrapDestinations[i] = (SNMPTrapDestinationMBean)((SNMPTrapDestinationMBean)this.createCopy((AbstractDescriptorBean)oldSNMPTrapDestinations[i], includeObsolete));
               }

               copy.setSNMPTrapDestinations(newSNMPTrapDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPTrapVersion")) && this.bean.isSNMPTrapVersionSet()) {
               copy.setSNMPTrapVersion(this.bean.getSNMPTrapVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerStatusCheckIntervalFactor")) && this.bean.isServerStatusCheckIntervalFactorSet()) {
               copy.setServerStatusCheckIntervalFactor(this.bean.getServerStatusCheckIntervalFactor());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("TargetedTrapDestinations")) && this.bean.isTargetedTrapDestinationsSet()) {
               copy._unSet(copy, 20);
               copy.setTargetedTrapDestinationsAsString(this.bean.getTargetedTrapDestinationsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("UserDefinedMib")) && this.bean.isUserDefinedMibSet()) {
               copy.setUserDefinedMib(this.bean.getUserDefinedMib());
            }

            if ((excludeProps == null || !excludeProps.contains("CommunityBasedAccessEnabled")) && this.bean.isCommunityBasedAccessEnabledSet()) {
               copy.setCommunityBasedAccessEnabled(this.bean.isCommunityBasedAccessEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("InformEnabled")) && this.bean.isInformEnabledSet()) {
               copy.setInformEnabled(this.bean.isInformEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SNMPAccessForUserMBeansEnabled")) && this.bean.isSNMPAccessForUserMBeansEnabledSet()) {
               copy.setSNMPAccessForUserMBeansEnabled(this.bean.isSNMPAccessForUserMBeansEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SendAutomaticTrapsEnabled")) && this.bean.isSendAutomaticTrapsEnabledSet()) {
               copy.setSendAutomaticTrapsEnabled(this.bean.isSendAutomaticTrapsEnabled());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getSNMPAttributeChanges(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPCounterMonitors(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPGaugeMonitors(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPLogFilters(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPProxies(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPStringMonitors(), clazz, annotation);
         this.inferSubTree(this.bean.getSNMPTrapDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getTargetedTrapDestinations(), clazz, annotation);
      }
   }
}
