package weblogic.coherence.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceClusterParamsBeanImpl extends SettableBeanImpl implements CoherenceClusterParamsBean, Serializable {
   private int _ClusterListenPort;
   private String _ClusteringMode;
   private CoherenceCacheBean[] _CoherenceCaches;
   private CoherenceClusterWellKnownAddressesBean _CoherenceClusterWellKnownAddresses;
   private CoherenceIdentityAsserterBean _CoherenceIdentityAsserter;
   private CoherenceKeystoreParamsBean _CoherenceKeystoreParams;
   private CoherenceServiceBean[] _CoherenceServices;
   private String _MulticastListenAddress;
   private int _MulticastListenPort;
   private boolean _SecurityFrameworkEnabled;
   private int _TimeToLive;
   private String _Transport;
   private int _UnicastListenPort;
   private boolean _UnicastPortAutoAdjust;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceClusterParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherenceClusterParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherenceClusterParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getClusterListenPort() {
      return this._ClusterListenPort;
   }

   public boolean isClusterListenPortInherited() {
      return false;
   }

   public boolean isClusterListenPortSet() {
      return this._isSet(0);
   }

   public void setClusterListenPort(int param0) {
      LegalChecks.checkInRange("ClusterListenPort", (long)param0, 0L, 65535L);
      int _oldVal = this._ClusterListenPort;
      this._ClusterListenPort = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getUnicastListenPort() {
      return this._UnicastListenPort;
   }

   public boolean isUnicastListenPortInherited() {
      return false;
   }

   public boolean isUnicastListenPortSet() {
      return this._isSet(1);
   }

   public void setUnicastListenPort(int param0) {
      LegalChecks.checkInRange("UnicastListenPort", (long)param0, 0L, 65535L);
      int _oldVal = this._UnicastListenPort;
      this._UnicastListenPort = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean isUnicastPortAutoAdjust() {
      return this._UnicastPortAutoAdjust;
   }

   public boolean isUnicastPortAutoAdjustInherited() {
      return false;
   }

   public boolean isUnicastPortAutoAdjustSet() {
      return this._isSet(2);
   }

   public void setUnicastPortAutoAdjust(boolean param0) {
      boolean _oldVal = this._UnicastPortAutoAdjust;
      this._UnicastPortAutoAdjust = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getMulticastListenAddress() {
      return this._MulticastListenAddress;
   }

   public boolean isMulticastListenAddressInherited() {
      return false;
   }

   public boolean isMulticastListenAddressSet() {
      return this._isSet(3);
   }

   public void setMulticastListenAddress(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MulticastListenAddress;
      this._MulticastListenAddress = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getMulticastListenPort() {
      return this._MulticastListenPort;
   }

   public boolean isMulticastListenPortInherited() {
      return false;
   }

   public boolean isMulticastListenPortSet() {
      return this._isSet(4);
   }

   public void setMulticastListenPort(int param0) {
      LegalChecks.checkInRange("MulticastListenPort", (long)param0, 1L, 65535L);
      int _oldVal = this._MulticastListenPort;
      this._MulticastListenPort = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getTimeToLive() {
      return this._TimeToLive;
   }

   public boolean isTimeToLiveInherited() {
      return false;
   }

   public boolean isTimeToLiveSet() {
      return this._isSet(5);
   }

   public void setTimeToLive(int param0) {
      LegalChecks.checkInRange("TimeToLive", (long)param0, 0L, 255L);
      int _oldVal = this._TimeToLive;
      this._TimeToLive = param0;
      this._postSet(5, _oldVal, param0);
   }

   public CoherenceClusterWellKnownAddressesBean getCoherenceClusterWellKnownAddresses() {
      return this._CoherenceClusterWellKnownAddresses;
   }

   public boolean isCoherenceClusterWellKnownAddressesInherited() {
      return false;
   }

   public boolean isCoherenceClusterWellKnownAddressesSet() {
      return this._isSet(6) || this._isAnythingSet((AbstractDescriptorBean)this.getCoherenceClusterWellKnownAddresses());
   }

   public void setCoherenceClusterWellKnownAddresses(CoherenceClusterWellKnownAddressesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 6)) {
         this._postCreate(_child);
      }

      CoherenceClusterWellKnownAddressesBean _oldVal = this._CoherenceClusterWellKnownAddresses;
      this._CoherenceClusterWellKnownAddresses = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getClusteringMode() {
      if (!this._isSet(7)) {
         try {
            return CoherenceClusterDefaults.getDefaultClusteringMode(this);
         } catch (NullPointerException var2) {
         }
      }

      return this._ClusteringMode;
   }

   public boolean isClusteringModeInherited() {
      return false;
   }

   public boolean isClusteringModeSet() {
      return this._isSet(7);
   }

   public void setClusteringMode(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"multicast", "unicast"};
      param0 = LegalChecks.checkInEnum("ClusteringMode", param0, _set);
      String _oldVal = this._ClusteringMode;
      this._ClusteringMode = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getTransport() {
      return this._Transport;
   }

   public boolean isTransportInherited() {
      return false;
   }

   public boolean isTransportSet() {
      return this._isSet(8);
   }

   public void setTransport(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"udp", "tcp", "ssl", "tmb", "sdmb", "imb"};
      param0 = LegalChecks.checkInEnum("Transport", param0, _set);
      String _oldVal = this._Transport;
      this._Transport = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean isSecurityFrameworkEnabled() {
      return this._SecurityFrameworkEnabled;
   }

   public boolean isSecurityFrameworkEnabledInherited() {
      return false;
   }

   public boolean isSecurityFrameworkEnabledSet() {
      return this._isSet(9);
   }

   public void setSecurityFrameworkEnabled(boolean param0) {
      boolean _oldVal = this._SecurityFrameworkEnabled;
      this._SecurityFrameworkEnabled = param0;
      this._postSet(9, _oldVal, param0);
   }

   public CoherenceIdentityAsserterBean getCoherenceIdentityAsserter() {
      return this._CoherenceIdentityAsserter;
   }

   public boolean isCoherenceIdentityAsserterInherited() {
      return false;
   }

   public boolean isCoherenceIdentityAsserterSet() {
      return this._isSet(10) || this._isAnythingSet((AbstractDescriptorBean)this.getCoherenceIdentityAsserter());
   }

   public void setCoherenceIdentityAsserter(CoherenceIdentityAsserterBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 10)) {
         this._postCreate(_child);
      }

      CoherenceIdentityAsserterBean _oldVal = this._CoherenceIdentityAsserter;
      this._CoherenceIdentityAsserter = param0;
      this._postSet(10, _oldVal, param0);
   }

   public CoherenceKeystoreParamsBean getCoherenceKeystoreParams() {
      return this._CoherenceKeystoreParams;
   }

   public boolean isCoherenceKeystoreParamsInherited() {
      return false;
   }

   public boolean isCoherenceKeystoreParamsSet() {
      return this._isSet(11) || this._isAnythingSet((AbstractDescriptorBean)this.getCoherenceKeystoreParams());
   }

   public void setCoherenceKeystoreParams(CoherenceKeystoreParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 11)) {
         this._postCreate(_child);
      }

      CoherenceKeystoreParamsBean _oldVal = this._CoherenceKeystoreParams;
      this._CoherenceKeystoreParams = param0;
      this._postSet(11, _oldVal, param0);
   }

   public void addCoherenceCache(CoherenceCacheBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         CoherenceCacheBean[] _new;
         if (this._isSet(12)) {
            _new = (CoherenceCacheBean[])((CoherenceCacheBean[])this._getHelper()._extendArray(this.getCoherenceCaches(), CoherenceCacheBean.class, param0));
         } else {
            _new = new CoherenceCacheBean[]{param0};
         }

         try {
            this.setCoherenceCaches(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherenceCacheBean[] getCoherenceCaches() {
      return this._CoherenceCaches;
   }

   public boolean isCoherenceCachesInherited() {
      return false;
   }

   public boolean isCoherenceCachesSet() {
      return this._isSet(12);
   }

   public void removeCoherenceCache(CoherenceCacheBean param0) {
      this.destroyCoherenceCache(param0);
   }

   public void setCoherenceCaches(CoherenceCacheBean[] param0) throws InvalidAttributeValueException {
      CoherenceCacheBean[] param0 = param0 == null ? new CoherenceCacheBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CoherenceCacheBean[] _oldVal = this._CoherenceCaches;
      this._CoherenceCaches = (CoherenceCacheBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public CoherenceCacheBean createCoherenceCache(String param0) {
      CoherenceCacheBeanImpl lookup = (CoherenceCacheBeanImpl)this.lookupCoherenceCache(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherenceCacheBeanImpl _val = new CoherenceCacheBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherenceCache(_val);
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

   public CoherenceCacheBean lookupCoherenceCache(String param0) {
      Object[] aary = (Object[])this._CoherenceCaches;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherenceCacheBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherenceCacheBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyCoherenceCache(CoherenceCacheBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         CoherenceCacheBean[] _old = this.getCoherenceCaches();
         CoherenceCacheBean[] _new = (CoherenceCacheBean[])((CoherenceCacheBean[])this._getHelper()._removeElement(_old, CoherenceCacheBean.class, param0));
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
               this.setCoherenceCaches(_new);
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

   public void addCoherenceService(CoherenceServiceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         CoherenceServiceBean[] _new;
         if (this._isSet(13)) {
            _new = (CoherenceServiceBean[])((CoherenceServiceBean[])this._getHelper()._extendArray(this.getCoherenceServices(), CoherenceServiceBean.class, param0));
         } else {
            _new = new CoherenceServiceBean[]{param0};
         }

         try {
            this.setCoherenceServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherenceServiceBean[] getCoherenceServices() {
      return this._CoherenceServices;
   }

   public boolean isCoherenceServicesInherited() {
      return false;
   }

   public boolean isCoherenceServicesSet() {
      return this._isSet(13);
   }

   public void removeCoherenceService(CoherenceServiceBean param0) {
      this.destroyCoherenceService(param0);
   }

   public void setCoherenceServices(CoherenceServiceBean[] param0) throws InvalidAttributeValueException {
      CoherenceServiceBean[] param0 = param0 == null ? new CoherenceServiceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CoherenceServiceBean[] _oldVal = this._CoherenceServices;
      this._CoherenceServices = (CoherenceServiceBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public CoherenceServiceBean createCoherenceService(String param0) {
      CoherenceServiceBeanImpl lookup = (CoherenceServiceBeanImpl)this.lookupCoherenceService(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherenceServiceBeanImpl _val = new CoherenceServiceBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherenceService(_val);
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

   public CoherenceServiceBean lookupCoherenceService(String param0) {
      Object[] aary = (Object[])this._CoherenceServices;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherenceServiceBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherenceServiceBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyCoherenceService(CoherenceServiceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         CoherenceServiceBean[] _old = this.getCoherenceServices();
         CoherenceServiceBean[] _new = (CoherenceServiceBean[])((CoherenceServiceBean[])this._getHelper()._removeElement(_old, CoherenceServiceBean.class, param0));
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
               this.setCoherenceServices(_new);
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

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      CoherenceClusterValidator.validateCoherenceClusterParams(this);
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
      return super._isAnythingSet() || this.isCoherenceClusterWellKnownAddressesSet() || this.isCoherenceIdentityAsserterSet() || this.isCoherenceKeystoreParamsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._ClusterListenPort = 0;
               if (initOne) {
                  break;
               }
            case 7:
               this._ClusteringMode = "unicast";
               if (initOne) {
                  break;
               }
            case 12:
               this._CoherenceCaches = new CoherenceCacheBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._CoherenceClusterWellKnownAddresses = new CoherenceClusterWellKnownAddressesBeanImpl(this, 6);
               this._postCreate((AbstractDescriptorBean)this._CoherenceClusterWellKnownAddresses);
               if (initOne) {
                  break;
               }
            case 10:
               this._CoherenceIdentityAsserter = new CoherenceIdentityAsserterBeanImpl(this, 10);
               this._postCreate((AbstractDescriptorBean)this._CoherenceIdentityAsserter);
               if (initOne) {
                  break;
               }
            case 11:
               this._CoherenceKeystoreParams = new CoherenceKeystoreParamsBeanImpl(this, 11);
               this._postCreate((AbstractDescriptorBean)this._CoherenceKeystoreParams);
               if (initOne) {
                  break;
               }
            case 13:
               this._CoherenceServices = new CoherenceServiceBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._MulticastListenAddress = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._MulticastListenPort = 33387;
               if (initOne) {
                  break;
               }
            case 5:
               this._TimeToLive = 4;
               if (initOne) {
                  break;
               }
            case 8:
               this._Transport = "udp";
               if (initOne) {
                  break;
               }
            case 1:
               this._UnicastListenPort = 0;
               if (initOne) {
                  break;
               }
            case 9:
               this._SecurityFrameworkEnabled = false;
               if (initOne) {
                  break;
               }
            case 2:
               this._UnicastPortAutoAdjust = true;
               if (initOne) {
                  break;
               }
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("transport")) {
                  return 8;
               }
            case 10:
            case 11:
            case 13:
            case 14:
            case 16:
            case 18:
            case 20:
            case 22:
            case 23:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            default:
               break;
            case 12:
               if (s.equals("time-to-live")) {
                  return 5;
               }
               break;
            case 15:
               if (s.equals("clustering-mode")) {
                  return 7;
               }

               if (s.equals("coherence-cache")) {
                  return 12;
               }
               break;
            case 17:
               if (s.equals("coherence-service")) {
                  return 13;
               }
               break;
            case 19:
               if (s.equals("cluster-listen-port")) {
                  return 0;
               }

               if (s.equals("unicast-listen-port")) {
                  return 1;
               }
               break;
            case 21:
               if (s.equals("multicast-listen-port")) {
                  return 4;
               }
               break;
            case 24:
               if (s.equals("multicast-listen-address")) {
                  return 3;
               }

               if (s.equals("unicast-port-auto-adjust")) {
                  return 2;
               }
               break;
            case 25:
               if (s.equals("coherence-keystore-params")) {
                  return 11;
               }
               break;
            case 26:
               if (s.equals("security-framework-enabled")) {
                  return 9;
               }
               break;
            case 27:
               if (s.equals("coherence-identity-asserter")) {
                  return 10;
               }
               break;
            case 38:
               if (s.equals("coherence-cluster-well-known-addresses")) {
                  return 6;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 6:
               return new CoherenceClusterWellKnownAddressesBeanImpl.SchemaHelper2();
            case 7:
            case 8:
            case 9:
            default:
               return super.getSchemaHelper(propIndex);
            case 10:
               return new CoherenceIdentityAsserterBeanImpl.SchemaHelper2();
            case 11:
               return new CoherenceKeystoreParamsBeanImpl.SchemaHelper2();
            case 12:
               return new CoherenceCacheBeanImpl.SchemaHelper2();
            case 13:
               return new CoherenceServiceBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "cluster-listen-port";
            case 1:
               return "unicast-listen-port";
            case 2:
               return "unicast-port-auto-adjust";
            case 3:
               return "multicast-listen-address";
            case 4:
               return "multicast-listen-port";
            case 5:
               return "time-to-live";
            case 6:
               return "coherence-cluster-well-known-addresses";
            case 7:
               return "clustering-mode";
            case 8:
               return "transport";
            case 9:
               return "security-framework-enabled";
            case 10:
               return "coherence-identity-asserter";
            case 11:
               return "coherence-keystore-params";
            case 12:
               return "coherence-cache";
            case 13:
               return "coherence-service";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 12:
               return true;
            case 13:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 6:
               return true;
            case 7:
            case 8:
            case 9:
            default:
               return super.isBean(propIndex);
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private CoherenceClusterParamsBeanImpl bean;

      protected Helper(CoherenceClusterParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ClusterListenPort";
            case 1:
               return "UnicastListenPort";
            case 2:
               return "UnicastPortAutoAdjust";
            case 3:
               return "MulticastListenAddress";
            case 4:
               return "MulticastListenPort";
            case 5:
               return "TimeToLive";
            case 6:
               return "CoherenceClusterWellKnownAddresses";
            case 7:
               return "ClusteringMode";
            case 8:
               return "Transport";
            case 9:
               return "SecurityFrameworkEnabled";
            case 10:
               return "CoherenceIdentityAsserter";
            case 11:
               return "CoherenceKeystoreParams";
            case 12:
               return "CoherenceCaches";
            case 13:
               return "CoherenceServices";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClusterListenPort")) {
            return 0;
         } else if (propName.equals("ClusteringMode")) {
            return 7;
         } else if (propName.equals("CoherenceCaches")) {
            return 12;
         } else if (propName.equals("CoherenceClusterWellKnownAddresses")) {
            return 6;
         } else if (propName.equals("CoherenceIdentityAsserter")) {
            return 10;
         } else if (propName.equals("CoherenceKeystoreParams")) {
            return 11;
         } else if (propName.equals("CoherenceServices")) {
            return 13;
         } else if (propName.equals("MulticastListenAddress")) {
            return 3;
         } else if (propName.equals("MulticastListenPort")) {
            return 4;
         } else if (propName.equals("TimeToLive")) {
            return 5;
         } else if (propName.equals("Transport")) {
            return 8;
         } else if (propName.equals("UnicastListenPort")) {
            return 1;
         } else if (propName.equals("SecurityFrameworkEnabled")) {
            return 9;
         } else {
            return propName.equals("UnicastPortAutoAdjust") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCoherenceCaches()));
         if (this.bean.getCoherenceClusterWellKnownAddresses() != null) {
            iterators.add(new ArrayIterator(new CoherenceClusterWellKnownAddressesBean[]{this.bean.getCoherenceClusterWellKnownAddresses()}));
         }

         if (this.bean.getCoherenceIdentityAsserter() != null) {
            iterators.add(new ArrayIterator(new CoherenceIdentityAsserterBean[]{this.bean.getCoherenceIdentityAsserter()}));
         }

         if (this.bean.getCoherenceKeystoreParams() != null) {
            iterators.add(new ArrayIterator(new CoherenceKeystoreParamsBean[]{this.bean.getCoherenceKeystoreParams()}));
         }

         iterators.add(new ArrayIterator(this.bean.getCoherenceServices()));
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
            if (this.bean.isClusterListenPortSet()) {
               buf.append("ClusterListenPort");
               buf.append(String.valueOf(this.bean.getClusterListenPort()));
            }

            if (this.bean.isClusteringModeSet()) {
               buf.append("ClusteringMode");
               buf.append(String.valueOf(this.bean.getClusteringMode()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getCoherenceCaches().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherenceCaches()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherenceClusterWellKnownAddresses());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherenceIdentityAsserter());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherenceKeystoreParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCoherenceServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherenceServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMulticastListenAddressSet()) {
               buf.append("MulticastListenAddress");
               buf.append(String.valueOf(this.bean.getMulticastListenAddress()));
            }

            if (this.bean.isMulticastListenPortSet()) {
               buf.append("MulticastListenPort");
               buf.append(String.valueOf(this.bean.getMulticastListenPort()));
            }

            if (this.bean.isTimeToLiveSet()) {
               buf.append("TimeToLive");
               buf.append(String.valueOf(this.bean.getTimeToLive()));
            }

            if (this.bean.isTransportSet()) {
               buf.append("Transport");
               buf.append(String.valueOf(this.bean.getTransport()));
            }

            if (this.bean.isUnicastListenPortSet()) {
               buf.append("UnicastListenPort");
               buf.append(String.valueOf(this.bean.getUnicastListenPort()));
            }

            if (this.bean.isSecurityFrameworkEnabledSet()) {
               buf.append("SecurityFrameworkEnabled");
               buf.append(String.valueOf(this.bean.isSecurityFrameworkEnabled()));
            }

            if (this.bean.isUnicastPortAutoAdjustSet()) {
               buf.append("UnicastPortAutoAdjust");
               buf.append(String.valueOf(this.bean.isUnicastPortAutoAdjust()));
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
            CoherenceClusterParamsBeanImpl otherTyped = (CoherenceClusterParamsBeanImpl)other;
            this.computeDiff("ClusterListenPort", this.bean.getClusterListenPort(), otherTyped.getClusterListenPort(), false);
            this.computeDiff("ClusteringMode", this.bean.getClusteringMode(), otherTyped.getClusteringMode(), false);
            this.computeChildDiff("CoherenceCaches", this.bean.getCoherenceCaches(), otherTyped.getCoherenceCaches(), false);
            this.computeSubDiff("CoherenceClusterWellKnownAddresses", this.bean.getCoherenceClusterWellKnownAddresses(), otherTyped.getCoherenceClusterWellKnownAddresses());
            this.computeSubDiff("CoherenceIdentityAsserter", this.bean.getCoherenceIdentityAsserter(), otherTyped.getCoherenceIdentityAsserter());
            this.computeSubDiff("CoherenceKeystoreParams", this.bean.getCoherenceKeystoreParams(), otherTyped.getCoherenceKeystoreParams());
            this.computeChildDiff("CoherenceServices", this.bean.getCoherenceServices(), otherTyped.getCoherenceServices(), false);
            this.computeDiff("MulticastListenAddress", this.bean.getMulticastListenAddress(), otherTyped.getMulticastListenAddress(), false);
            this.computeDiff("MulticastListenPort", this.bean.getMulticastListenPort(), otherTyped.getMulticastListenPort(), false);
            this.computeDiff("TimeToLive", this.bean.getTimeToLive(), otherTyped.getTimeToLive(), false);
            this.computeDiff("Transport", this.bean.getTransport(), otherTyped.getTransport(), false);
            this.computeDiff("UnicastListenPort", this.bean.getUnicastListenPort(), otherTyped.getUnicastListenPort(), false);
            this.computeDiff("SecurityFrameworkEnabled", this.bean.isSecurityFrameworkEnabled(), otherTyped.isSecurityFrameworkEnabled(), false);
            this.computeDiff("UnicastPortAutoAdjust", this.bean.isUnicastPortAutoAdjust(), otherTyped.isUnicastPortAutoAdjust(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceClusterParamsBeanImpl original = (CoherenceClusterParamsBeanImpl)event.getSourceBean();
            CoherenceClusterParamsBeanImpl proposed = (CoherenceClusterParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClusterListenPort")) {
                  original.setClusterListenPort(proposed.getClusterListenPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ClusteringMode")) {
                  original.setClusteringMode(proposed.getClusteringMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("CoherenceCaches")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherenceCache((CoherenceCacheBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCoherenceCache((CoherenceCacheBean)update.getRemovedObject());
                  }

                  if (original.getCoherenceCaches() == null || original.getCoherenceCaches().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("CoherenceClusterWellKnownAddresses")) {
                  if (type == 2) {
                     original.setCoherenceClusterWellKnownAddresses((CoherenceClusterWellKnownAddressesBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceClusterWellKnownAddresses()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceClusterWellKnownAddresses", (DescriptorBean)original.getCoherenceClusterWellKnownAddresses());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("CoherenceIdentityAsserter")) {
                  if (type == 2) {
                     original.setCoherenceIdentityAsserter((CoherenceIdentityAsserterBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceIdentityAsserter()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceIdentityAsserter", (DescriptorBean)original.getCoherenceIdentityAsserter());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("CoherenceKeystoreParams")) {
                  if (type == 2) {
                     original.setCoherenceKeystoreParams((CoherenceKeystoreParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceKeystoreParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceKeystoreParams", (DescriptorBean)original.getCoherenceKeystoreParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("CoherenceServices")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherenceService((CoherenceServiceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCoherenceService((CoherenceServiceBean)update.getRemovedObject());
                  }

                  if (original.getCoherenceServices() == null || original.getCoherenceServices().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("MulticastListenAddress")) {
                  original.setMulticastListenAddress(proposed.getMulticastListenAddress());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("MulticastListenPort")) {
                  original.setMulticastListenPort(proposed.getMulticastListenPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("TimeToLive")) {
                  original.setTimeToLive(proposed.getTimeToLive());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Transport")) {
                  original.setTransport(proposed.getTransport());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("UnicastListenPort")) {
                  original.setUnicastListenPort(proposed.getUnicastListenPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("SecurityFrameworkEnabled")) {
                  original.setSecurityFrameworkEnabled(proposed.isSecurityFrameworkEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("UnicastPortAutoAdjust")) {
                  original.setUnicastPortAutoAdjust(proposed.isUnicastPortAutoAdjust());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else {
                  super.applyPropertyUpdate(event, update);
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
            CoherenceClusterParamsBeanImpl copy = (CoherenceClusterParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClusterListenPort")) && this.bean.isClusterListenPortSet()) {
               copy.setClusterListenPort(this.bean.getClusterListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("ClusteringMode")) && this.bean.isClusteringModeSet()) {
               copy.setClusteringMode(this.bean.getClusteringMode());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("CoherenceCaches")) && this.bean.isCoherenceCachesSet() && !copy._isSet(12)) {
               CoherenceCacheBean[] oldCoherenceCaches = this.bean.getCoherenceCaches();
               CoherenceCacheBean[] newCoherenceCaches = new CoherenceCacheBean[oldCoherenceCaches.length];

               for(i = 0; i < newCoherenceCaches.length; ++i) {
                  newCoherenceCaches[i] = (CoherenceCacheBean)((CoherenceCacheBean)this.createCopy((AbstractDescriptorBean)oldCoherenceCaches[i], includeObsolete));
               }

               copy.setCoherenceCaches(newCoherenceCaches);
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceClusterWellKnownAddresses")) && this.bean.isCoherenceClusterWellKnownAddressesSet() && !copy._isSet(6)) {
               Object o = this.bean.getCoherenceClusterWellKnownAddresses();
               copy.setCoherenceClusterWellKnownAddresses((CoherenceClusterWellKnownAddressesBean)null);
               copy.setCoherenceClusterWellKnownAddresses(o == null ? null : (CoherenceClusterWellKnownAddressesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceIdentityAsserter")) && this.bean.isCoherenceIdentityAsserterSet() && !copy._isSet(10)) {
               Object o = this.bean.getCoherenceIdentityAsserter();
               copy.setCoherenceIdentityAsserter((CoherenceIdentityAsserterBean)null);
               copy.setCoherenceIdentityAsserter(o == null ? null : (CoherenceIdentityAsserterBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceKeystoreParams")) && this.bean.isCoherenceKeystoreParamsSet() && !copy._isSet(11)) {
               Object o = this.bean.getCoherenceKeystoreParams();
               copy.setCoherenceKeystoreParams((CoherenceKeystoreParamsBean)null);
               copy.setCoherenceKeystoreParams(o == null ? null : (CoherenceKeystoreParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceServices")) && this.bean.isCoherenceServicesSet() && !copy._isSet(13)) {
               CoherenceServiceBean[] oldCoherenceServices = this.bean.getCoherenceServices();
               CoherenceServiceBean[] newCoherenceServices = new CoherenceServiceBean[oldCoherenceServices.length];

               for(i = 0; i < newCoherenceServices.length; ++i) {
                  newCoherenceServices[i] = (CoherenceServiceBean)((CoherenceServiceBean)this.createCopy((AbstractDescriptorBean)oldCoherenceServices[i], includeObsolete));
               }

               copy.setCoherenceServices(newCoherenceServices);
            }

            if ((excludeProps == null || !excludeProps.contains("MulticastListenAddress")) && this.bean.isMulticastListenAddressSet()) {
               copy.setMulticastListenAddress(this.bean.getMulticastListenAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("MulticastListenPort")) && this.bean.isMulticastListenPortSet()) {
               copy.setMulticastListenPort(this.bean.getMulticastListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeToLive")) && this.bean.isTimeToLiveSet()) {
               copy.setTimeToLive(this.bean.getTimeToLive());
            }

            if ((excludeProps == null || !excludeProps.contains("Transport")) && this.bean.isTransportSet()) {
               copy.setTransport(this.bean.getTransport());
            }

            if ((excludeProps == null || !excludeProps.contains("UnicastListenPort")) && this.bean.isUnicastListenPortSet()) {
               copy.setUnicastListenPort(this.bean.getUnicastListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityFrameworkEnabled")) && this.bean.isSecurityFrameworkEnabledSet()) {
               copy.setSecurityFrameworkEnabled(this.bean.isSecurityFrameworkEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("UnicastPortAutoAdjust")) && this.bean.isUnicastPortAutoAdjustSet()) {
               copy.setUnicastPortAutoAdjust(this.bean.isUnicastPortAutoAdjust());
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
         this.inferSubTree(this.bean.getCoherenceCaches(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceClusterWellKnownAddresses(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceIdentityAsserter(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceKeystoreParams(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceServices(), clazz, annotation);
      }
   }
}
