package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceMemberConfigMBeanImpl extends ConfigurationMBeanImpl implements CoherenceMemberConfigMBean, Serializable {
   private boolean _CoherenceWebFederatedStorageEnabled;
   private boolean _CoherenceWebLocalStorageEnabled;
   private boolean _LocalStorageEnabled;
   private boolean _ManagementProxy;
   private String _RackName;
   private String _RoleName;
   private String _SiteName;
   private String _UnicastListenAddress;
   private int _UnicastListenPort;
   private boolean _UnicastPortAutoAdjust;
   private int _UnicastPortAutoAdjustAttempts;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private CoherenceMemberConfigMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(CoherenceMemberConfigMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(CoherenceMemberConfigMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public CoherenceMemberConfigMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(CoherenceMemberConfigMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      CoherenceMemberConfigMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public CoherenceMemberConfigMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherenceMemberConfigMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherenceMemberConfigMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getUnicastListenAddress() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getUnicastListenAddress(), this) : this._UnicastListenAddress;
   }

   public boolean isUnicastListenAddressInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isUnicastListenAddressSet() {
      return this._isSet(10);
   }

   public void setUnicastListenAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      String _oldVal = this._UnicastListenAddress;
      this._UnicastListenAddress = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceMemberConfigMBeanImpl source = (CoherenceMemberConfigMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public int getUnicastListenPort() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().getUnicastListenPort() : this._UnicastListenPort;
   }

   public boolean isUnicastListenPortInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isUnicastListenPortSet() {
      return this._isSet(11);
   }

   public void setUnicastListenPort(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("UnicastListenPort", (long)param0, 0L, 65535L);
      boolean wasSet = this._isSet(11);
      int _oldVal = this._UnicastListenPort;
      this._UnicastListenPort = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceMemberConfigMBeanImpl source = (CoherenceMemberConfigMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isUnicastPortAutoAdjust() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().isUnicastPortAutoAdjust() : this._UnicastPortAutoAdjust;
   }

   public boolean isUnicastPortAutoAdjustInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isUnicastPortAutoAdjustSet() {
      return this._isSet(12);
   }

   public void setUnicastPortAutoAdjust(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      boolean _oldVal = this._UnicastPortAutoAdjust;
      this._UnicastPortAutoAdjust = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceMemberConfigMBeanImpl source = (CoherenceMemberConfigMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public int getUnicastPortAutoAdjustAttempts() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getUnicastPortAutoAdjustAttempts() : this._UnicastPortAutoAdjustAttempts;
   }

   public boolean isUnicastPortAutoAdjustAttemptsInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isUnicastPortAutoAdjustAttemptsSet() {
      return this._isSet(13);
   }

   public void setUnicastPortAutoAdjustAttempts(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("UnicastPortAutoAdjustAttempts", (long)param0, 0L, 65535L);
      boolean wasSet = this._isSet(13);
      int _oldVal = this._UnicastPortAutoAdjustAttempts;
      this._UnicastPortAutoAdjustAttempts = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceMemberConfigMBeanImpl source = (CoherenceMemberConfigMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isLocalStorageEnabled() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().isLocalStorageEnabled() : this._LocalStorageEnabled;
   }

   public boolean isLocalStorageEnabledInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isLocalStorageEnabledSet() {
      return this._isSet(14);
   }

   public void setLocalStorageEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      boolean _oldVal = this._LocalStorageEnabled;
      this._LocalStorageEnabled = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceMemberConfigMBeanImpl source = (CoherenceMemberConfigMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isCoherenceWebLocalStorageEnabled() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().isCoherenceWebLocalStorageEnabled() : this._CoherenceWebLocalStorageEnabled;
   }

   public boolean isCoherenceWebLocalStorageEnabledInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isCoherenceWebLocalStorageEnabledSet() {
      return this._isSet(15);
   }

   public void setCoherenceWebLocalStorageEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      boolean _oldVal = this._CoherenceWebLocalStorageEnabled;
      this._CoherenceWebLocalStorageEnabled = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceMemberConfigMBeanImpl source = (CoherenceMemberConfigMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isCoherenceWebFederatedStorageEnabled() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().isCoherenceWebFederatedStorageEnabled() : this._CoherenceWebFederatedStorageEnabled;
   }

   public boolean isCoherenceWebFederatedStorageEnabledInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isCoherenceWebFederatedStorageEnabledSet() {
      return this._isSet(16);
   }

   public void setCoherenceWebFederatedStorageEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      boolean _oldVal = this._CoherenceWebFederatedStorageEnabled;
      this._CoherenceWebFederatedStorageEnabled = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceMemberConfigMBeanImpl source = (CoherenceMemberConfigMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSiteName() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._performMacroSubstitution(this._getDelegateBean().getSiteName(), this) : this._SiteName;
   }

   public boolean isSiteNameInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isSiteNameSet() {
      return this._isSet(17);
   }

   public void setSiteName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      String _oldVal = this._SiteName;
      this._SiteName = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceMemberConfigMBeanImpl source = (CoherenceMemberConfigMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public String getRackName() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._performMacroSubstitution(this._getDelegateBean().getRackName(), this) : this._RackName;
   }

   public boolean isRackNameInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isRackNameSet() {
      return this._isSet(18);
   }

   public void setRackName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      String _oldVal = this._RackName;
      this._RackName = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceMemberConfigMBeanImpl source = (CoherenceMemberConfigMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public String getRoleName() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._performMacroSubstitution(this._getDelegateBean().getRoleName(), this) : this._RoleName;
   }

   public boolean isRoleNameInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isRoleNameSet() {
      return this._isSet(19);
   }

   public void setRoleName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      String _oldVal = this._RoleName;
      this._RoleName = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceMemberConfigMBeanImpl source = (CoherenceMemberConfigMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isManagementProxy() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().isManagementProxy() : this._ManagementProxy;
   }

   public boolean isManagementProxyInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isManagementProxySet() {
      return this._isSet(20);
   }

   public void setManagementProxy(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      boolean _oldVal = this._ManagementProxy;
      this._ManagementProxy = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CoherenceMemberConfigMBeanImpl source = (CoherenceMemberConfigMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
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
         idx = 18;
      }

      try {
         switch (idx) {
            case 18:
               this._RackName = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._RoleName = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._SiteName = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._UnicastListenAddress = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._UnicastListenPort = 0;
               if (initOne) {
                  break;
               }
            case 13:
               this._UnicastPortAutoAdjustAttempts = 0;
               if (initOne) {
                  break;
               }
            case 16:
               this._CoherenceWebFederatedStorageEnabled = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._CoherenceWebLocalStorageEnabled = false;
               if (initOne) {
                  break;
               }
            case 14:
               this._LocalStorageEnabled = true;
               if (initOne) {
                  break;
               }
            case 20:
               this._ManagementProxy = true;
               if (initOne) {
                  break;
               }
            case 12:
               this._UnicastPortAutoAdjust = false;
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
      return "CoherenceMemberConfig";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("CoherenceWebFederatedStorageEnabled")) {
         oldVal = this._CoherenceWebFederatedStorageEnabled;
         this._CoherenceWebFederatedStorageEnabled = (Boolean)v;
         this._postSet(16, oldVal, this._CoherenceWebFederatedStorageEnabled);
      } else if (name.equals("CoherenceWebLocalStorageEnabled")) {
         oldVal = this._CoherenceWebLocalStorageEnabled;
         this._CoherenceWebLocalStorageEnabled = (Boolean)v;
         this._postSet(15, oldVal, this._CoherenceWebLocalStorageEnabled);
      } else if (name.equals("LocalStorageEnabled")) {
         oldVal = this._LocalStorageEnabled;
         this._LocalStorageEnabled = (Boolean)v;
         this._postSet(14, oldVal, this._LocalStorageEnabled);
      } else if (name.equals("ManagementProxy")) {
         oldVal = this._ManagementProxy;
         this._ManagementProxy = (Boolean)v;
         this._postSet(20, oldVal, this._ManagementProxy);
      } else {
         String oldVal;
         if (name.equals("RackName")) {
            oldVal = this._RackName;
            this._RackName = (String)v;
            this._postSet(18, oldVal, this._RackName);
         } else if (name.equals("RoleName")) {
            oldVal = this._RoleName;
            this._RoleName = (String)v;
            this._postSet(19, oldVal, this._RoleName);
         } else if (name.equals("SiteName")) {
            oldVal = this._SiteName;
            this._SiteName = (String)v;
            this._postSet(17, oldVal, this._SiteName);
         } else if (name.equals("UnicastListenAddress")) {
            oldVal = this._UnicastListenAddress;
            this._UnicastListenAddress = (String)v;
            this._postSet(10, oldVal, this._UnicastListenAddress);
         } else {
            int oldVal;
            if (name.equals("UnicastListenPort")) {
               oldVal = this._UnicastListenPort;
               this._UnicastListenPort = (Integer)v;
               this._postSet(11, oldVal, this._UnicastListenPort);
            } else if (name.equals("UnicastPortAutoAdjust")) {
               oldVal = this._UnicastPortAutoAdjust;
               this._UnicastPortAutoAdjust = (Boolean)v;
               this._postSet(12, oldVal, this._UnicastPortAutoAdjust);
            } else if (name.equals("UnicastPortAutoAdjustAttempts")) {
               oldVal = this._UnicastPortAutoAdjustAttempts;
               this._UnicastPortAutoAdjustAttempts = (Integer)v;
               this._postSet(13, oldVal, this._UnicastPortAutoAdjustAttempts);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("CoherenceWebFederatedStorageEnabled")) {
         return new Boolean(this._CoherenceWebFederatedStorageEnabled);
      } else if (name.equals("CoherenceWebLocalStorageEnabled")) {
         return new Boolean(this._CoherenceWebLocalStorageEnabled);
      } else if (name.equals("LocalStorageEnabled")) {
         return new Boolean(this._LocalStorageEnabled);
      } else if (name.equals("ManagementProxy")) {
         return new Boolean(this._ManagementProxy);
      } else if (name.equals("RackName")) {
         return this._RackName;
      } else if (name.equals("RoleName")) {
         return this._RoleName;
      } else if (name.equals("SiteName")) {
         return this._SiteName;
      } else if (name.equals("UnicastListenAddress")) {
         return this._UnicastListenAddress;
      } else if (name.equals("UnicastListenPort")) {
         return new Integer(this._UnicastListenPort);
      } else if (name.equals("UnicastPortAutoAdjust")) {
         return new Boolean(this._UnicastPortAutoAdjust);
      } else {
         return name.equals("UnicastPortAutoAdjustAttempts") ? new Integer(this._UnicastPortAutoAdjustAttempts) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("rack-name")) {
                  return 18;
               }

               if (s.equals("role-name")) {
                  return 19;
               }

               if (s.equals("site-name")) {
                  return 17;
               }
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 20:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            case 36:
            case 37:
            case 38:
            default:
               break;
            case 16:
               if (s.equals("management-proxy")) {
                  return 20;
               }
               break;
            case 19:
               if (s.equals("unicast-listen-port")) {
                  return 11;
               }
               break;
            case 21:
               if (s.equals("local-storage-enabled")) {
                  return 14;
               }
               break;
            case 22:
               if (s.equals("unicast-listen-address")) {
                  return 10;
               }
               break;
            case 24:
               if (s.equals("unicast-port-auto-adjust")) {
                  return 12;
               }
               break;
            case 33:
               if (s.equals("unicast-port-auto-adjust-attempts")) {
                  return 13;
               }
               break;
            case 35:
               if (s.equals("coherence-web-local-storage-enabled")) {
                  return 15;
               }
               break;
            case 39:
               if (s.equals("coherence-web-federated-storage-enabled")) {
                  return 16;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "unicast-listen-address";
            case 11:
               return "unicast-listen-port";
            case 12:
               return "unicast-port-auto-adjust";
            case 13:
               return "unicast-port-auto-adjust-attempts";
            case 14:
               return "local-storage-enabled";
            case 15:
               return "coherence-web-local-storage-enabled";
            case 16:
               return "coherence-web-federated-storage-enabled";
            case 17:
               return "site-name";
            case 18:
               return "rack-name";
            case 19:
               return "role-name";
            case 20:
               return "management-proxy";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private CoherenceMemberConfigMBeanImpl bean;

      protected Helper(CoherenceMemberConfigMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "UnicastListenAddress";
            case 11:
               return "UnicastListenPort";
            case 12:
               return "UnicastPortAutoAdjust";
            case 13:
               return "UnicastPortAutoAdjustAttempts";
            case 14:
               return "LocalStorageEnabled";
            case 15:
               return "CoherenceWebLocalStorageEnabled";
            case 16:
               return "CoherenceWebFederatedStorageEnabled";
            case 17:
               return "SiteName";
            case 18:
               return "RackName";
            case 19:
               return "RoleName";
            case 20:
               return "ManagementProxy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("RackName")) {
            return 18;
         } else if (propName.equals("RoleName")) {
            return 19;
         } else if (propName.equals("SiteName")) {
            return 17;
         } else if (propName.equals("UnicastListenAddress")) {
            return 10;
         } else if (propName.equals("UnicastListenPort")) {
            return 11;
         } else if (propName.equals("UnicastPortAutoAdjustAttempts")) {
            return 13;
         } else if (propName.equals("CoherenceWebFederatedStorageEnabled")) {
            return 16;
         } else if (propName.equals("CoherenceWebLocalStorageEnabled")) {
            return 15;
         } else if (propName.equals("LocalStorageEnabled")) {
            return 14;
         } else if (propName.equals("ManagementProxy")) {
            return 20;
         } else {
            return propName.equals("UnicastPortAutoAdjust") ? 12 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isRackNameSet()) {
               buf.append("RackName");
               buf.append(String.valueOf(this.bean.getRackName()));
            }

            if (this.bean.isRoleNameSet()) {
               buf.append("RoleName");
               buf.append(String.valueOf(this.bean.getRoleName()));
            }

            if (this.bean.isSiteNameSet()) {
               buf.append("SiteName");
               buf.append(String.valueOf(this.bean.getSiteName()));
            }

            if (this.bean.isUnicastListenAddressSet()) {
               buf.append("UnicastListenAddress");
               buf.append(String.valueOf(this.bean.getUnicastListenAddress()));
            }

            if (this.bean.isUnicastListenPortSet()) {
               buf.append("UnicastListenPort");
               buf.append(String.valueOf(this.bean.getUnicastListenPort()));
            }

            if (this.bean.isUnicastPortAutoAdjustAttemptsSet()) {
               buf.append("UnicastPortAutoAdjustAttempts");
               buf.append(String.valueOf(this.bean.getUnicastPortAutoAdjustAttempts()));
            }

            if (this.bean.isCoherenceWebFederatedStorageEnabledSet()) {
               buf.append("CoherenceWebFederatedStorageEnabled");
               buf.append(String.valueOf(this.bean.isCoherenceWebFederatedStorageEnabled()));
            }

            if (this.bean.isCoherenceWebLocalStorageEnabledSet()) {
               buf.append("CoherenceWebLocalStorageEnabled");
               buf.append(String.valueOf(this.bean.isCoherenceWebLocalStorageEnabled()));
            }

            if (this.bean.isLocalStorageEnabledSet()) {
               buf.append("LocalStorageEnabled");
               buf.append(String.valueOf(this.bean.isLocalStorageEnabled()));
            }

            if (this.bean.isManagementProxySet()) {
               buf.append("ManagementProxy");
               buf.append(String.valueOf(this.bean.isManagementProxy()));
            }

            if (this.bean.isUnicastPortAutoAdjustSet()) {
               buf.append("UnicastPortAutoAdjust");
               buf.append(String.valueOf(this.bean.isUnicastPortAutoAdjust()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            CoherenceMemberConfigMBeanImpl otherTyped = (CoherenceMemberConfigMBeanImpl)other;
            this.computeDiff("RackName", this.bean.getRackName(), otherTyped.getRackName(), false);
            this.computeDiff("RoleName", this.bean.getRoleName(), otherTyped.getRoleName(), false);
            this.computeDiff("SiteName", this.bean.getSiteName(), otherTyped.getSiteName(), false);
            this.computeDiff("UnicastListenAddress", this.bean.getUnicastListenAddress(), otherTyped.getUnicastListenAddress(), false);
            this.computeDiff("UnicastListenPort", this.bean.getUnicastListenPort(), otherTyped.getUnicastListenPort(), false);
            this.computeDiff("UnicastPortAutoAdjustAttempts", this.bean.getUnicastPortAutoAdjustAttempts(), otherTyped.getUnicastPortAutoAdjustAttempts(), false);
            this.computeDiff("CoherenceWebFederatedStorageEnabled", this.bean.isCoherenceWebFederatedStorageEnabled(), otherTyped.isCoherenceWebFederatedStorageEnabled(), false);
            this.computeDiff("CoherenceWebLocalStorageEnabled", this.bean.isCoherenceWebLocalStorageEnabled(), otherTyped.isCoherenceWebLocalStorageEnabled(), false);
            this.computeDiff("LocalStorageEnabled", this.bean.isLocalStorageEnabled(), otherTyped.isLocalStorageEnabled(), false);
            this.computeDiff("ManagementProxy", this.bean.isManagementProxy(), otherTyped.isManagementProxy(), false);
            this.computeDiff("UnicastPortAutoAdjust", this.bean.isUnicastPortAutoAdjust(), otherTyped.isUnicastPortAutoAdjust(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceMemberConfigMBeanImpl original = (CoherenceMemberConfigMBeanImpl)event.getSourceBean();
            CoherenceMemberConfigMBeanImpl proposed = (CoherenceMemberConfigMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("RackName")) {
                  original.setRackName(proposed.getRackName());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("RoleName")) {
                  original.setRoleName(proposed.getRoleName());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("SiteName")) {
                  original.setSiteName(proposed.getSiteName());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("UnicastListenAddress")) {
                  original.setUnicastListenAddress(proposed.getUnicastListenAddress());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("UnicastListenPort")) {
                  original.setUnicastListenPort(proposed.getUnicastListenPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("UnicastPortAutoAdjustAttempts")) {
                  original.setUnicastPortAutoAdjustAttempts(proposed.getUnicastPortAutoAdjustAttempts());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("CoherenceWebFederatedStorageEnabled")) {
                  original.setCoherenceWebFederatedStorageEnabled(proposed.isCoherenceWebFederatedStorageEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("CoherenceWebLocalStorageEnabled")) {
                  original.setCoherenceWebLocalStorageEnabled(proposed.isCoherenceWebLocalStorageEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("LocalStorageEnabled")) {
                  original.setLocalStorageEnabled(proposed.isLocalStorageEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("ManagementProxy")) {
                  original.setManagementProxy(proposed.isManagementProxy());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("UnicastPortAutoAdjust")) {
                  original.setUnicastPortAutoAdjust(proposed.isUnicastPortAutoAdjust());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            CoherenceMemberConfigMBeanImpl copy = (CoherenceMemberConfigMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("RackName")) && this.bean.isRackNameSet()) {
               copy.setRackName(this.bean.getRackName());
            }

            if ((excludeProps == null || !excludeProps.contains("RoleName")) && this.bean.isRoleNameSet()) {
               copy.setRoleName(this.bean.getRoleName());
            }

            if ((excludeProps == null || !excludeProps.contains("SiteName")) && this.bean.isSiteNameSet()) {
               copy.setSiteName(this.bean.getSiteName());
            }

            if ((excludeProps == null || !excludeProps.contains("UnicastListenAddress")) && this.bean.isUnicastListenAddressSet()) {
               copy.setUnicastListenAddress(this.bean.getUnicastListenAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("UnicastListenPort")) && this.bean.isUnicastListenPortSet()) {
               copy.setUnicastListenPort(this.bean.getUnicastListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("UnicastPortAutoAdjustAttempts")) && this.bean.isUnicastPortAutoAdjustAttemptsSet()) {
               copy.setUnicastPortAutoAdjustAttempts(this.bean.getUnicastPortAutoAdjustAttempts());
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceWebFederatedStorageEnabled")) && this.bean.isCoherenceWebFederatedStorageEnabledSet()) {
               copy.setCoherenceWebFederatedStorageEnabled(this.bean.isCoherenceWebFederatedStorageEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceWebLocalStorageEnabled")) && this.bean.isCoherenceWebLocalStorageEnabledSet()) {
               copy.setCoherenceWebLocalStorageEnabled(this.bean.isCoherenceWebLocalStorageEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalStorageEnabled")) && this.bean.isLocalStorageEnabledSet()) {
               copy.setLocalStorageEnabled(this.bean.isLocalStorageEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ManagementProxy")) && this.bean.isManagementProxySet()) {
               copy.setManagementProxy(this.bean.isManagementProxy());
            }

            if ((excludeProps == null || !excludeProps.contains("UnicastPortAutoAdjust")) && this.bean.isUnicastPortAutoAdjustSet()) {
               copy.setUnicastPortAutoAdjust(this.bean.isUnicastPortAutoAdjust());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}
