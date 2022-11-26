package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.ConnectorComponent;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ConnectorComponentMBeanImpl extends ComponentMBeanImpl implements ConnectorComponentMBean, Serializable {
   private TargetMBean[] _ActivatedTargets;
   private ApplicationMBean _Application;
   private HashSet _AuthenticationMechanisms;
   private ClassLoader _ClassLoader;
   private Hashtable _ConfigProperties;
   private String _ConnectionFactoryDescription;
   private String _ConnectionFactoryImpl;
   private String _ConnectionFactoryInterface;
   private String _ConnectionFactoryName;
   private String _ConnectionImpl;
   private String _ConnectionInterface;
   private boolean _ConnectionProfilingEnabled;
   private String _Description;
   private String _DisplayName;
   private boolean _DynamicallyCreated;
   private String _EisType;
   private String _LargeIcon;
   private String _LicenseDescription;
   private boolean _LicenseRequired;
   private String _ManagedConnectionFactoryClass;
   private int _MaxCapacity;
   private String _Name;
   private HashSet _SecurityPermissions;
   private String _SmallIcon;
   private String _SpecVersion;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private String _TransactionSupport;
   private String _VendorName;
   private String _Version;
   private transient ConnectorComponent _customizer;
   private boolean _reauthenticationSupport;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ConnectorComponentMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ConnectorComponentMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ConnectorComponentMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ConnectorComponentMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ConnectorComponentMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ConnectorComponentMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._Application instanceof ApplicationMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getApplication() != null) {
            this._getReferenceManager().unregisterBean((ApplicationMBeanImpl)oldDelegate.getApplication());
         }

         if (delegate != null && delegate.getApplication() != null) {
            this._getReferenceManager().registerBean((ApplicationMBeanImpl)delegate.getApplication(), false);
         }

         ((ApplicationMBeanImpl)this._Application)._setDelegateBean((ApplicationMBeanImpl)((ApplicationMBeanImpl)(delegate == null ? null : delegate.getApplication())));
      }

   }

   public ConnectorComponentMBeanImpl() {
      try {
         this._customizer = new ConnectorComponent(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ConnectorComponentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ConnectorComponent(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ConnectorComponentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ConnectorComponent(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public ApplicationMBean getApplication() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getApplication() : this._customizer.getApplication();
   }

   public String getDescription() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._performMacroSubstitution(this._getDelegateBean().getDescription(), this) : this._Description;
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._customizer.getName();
      }
   }

   public TargetMBean[] getTargets() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getTargets() : this._customizer.getTargets();
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isApplicationInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isApplicationSet() {
      return this._isSet(12);
   }

   public boolean isDescriptionInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isDescriptionSet() {
      return this._isSet(15);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isTargetsInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isTargetsSet() {
      return this._isSet(10);
   }

   public void setTargetsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Targets);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 10, param0) {
                  public void resolveReference(Object value) {
                     try {
                        ConnectorComponentMBeanImpl.this.addTarget((TargetMBean)value);
                        ConnectorComponentMBeanImpl.this._getHelper().reorderArrayObjects((Object[])ConnectorComponentMBeanImpl.this._Targets, this.getHandbackObject());
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
               TargetMBean[] var6 = this._Targets;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  TargetMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeTarget(member);
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
         TargetMBean[] _oldVal = this._Targets;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._Targets);
      }
   }

   public void setApplication(ApplicationMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._customizer.setApplication(param0);
   }

   public void setDescription(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._Description = param0;
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ConnectorComponentMBeanImpl source = (ConnectorComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return ConnectorComponentMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      boolean wasSet = this._isSet(10);
      TargetMBean[] _oldVal = this.getTargets();
      this._customizer.setTargets(param0);
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ConnectorComponentMBeanImpl source = (ConnectorComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));

         try {
            this.setTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public String getDisplayName() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._performMacroSubstitution(this._getDelegateBean().getDisplayName(), this) : this._DisplayName;
   }

   public boolean isDisplayNameInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isDisplayNameSet() {
      return this._isSet(16);
   }

   public boolean removeTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setDisplayName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DisplayName = param0;
   }

   public TargetMBean[] getActivatedTargets() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getActivatedTargets() : this._customizer.getActivatedTargets();
   }

   public String getEisType() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._performMacroSubstitution(this._getDelegateBean().getEisType(), this) : this._EisType;
   }

   public boolean isActivatedTargetsInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isActivatedTargetsSet() {
      return this._isSet(14);
   }

   public boolean isEisTypeInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isEisTypeSet() {
      return this._isSet(17);
   }

   public void addActivatedTarget(TargetMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getActivatedTargets(), TargetMBean.class, param0));

         try {
            this.setActivatedTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void setEisType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._EisType = param0;
   }

   public String getSmallIcon() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._performMacroSubstitution(this._getDelegateBean().getSmallIcon(), this) : this._SmallIcon;
   }

   public boolean isSmallIconInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isSmallIconSet() {
      return this._isSet(18);
   }

   public void removeActivatedTarget(TargetMBean param0) {
      TargetMBean[] _old = this.getActivatedTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setActivatedTargets(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setActivatedTargets(TargetMBean[] param0) {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ActivatedTargets = (TargetMBean[])param0;
   }

   public void setSmallIcon(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SmallIcon = param0;
   }

   public boolean activated(TargetMBean param0) {
      return this._customizer.activated(param0);
   }

   public String getLargeIcon() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._performMacroSubstitution(this._getDelegateBean().getLargeIcon(), this) : this._LargeIcon;
   }

   public boolean isLargeIconInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isLargeIconSet() {
      return this._isSet(19);
   }

   public void refreshDDsIfNeeded(String[] param0) {
      this._customizer.refreshDDsIfNeeded(param0);
   }

   public void setLargeIcon(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LargeIcon = param0;
   }

   public String getLicenseDescription() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._performMacroSubstitution(this._getDelegateBean().getLicenseDescription(), this) : this._LicenseDescription;
   }

   public boolean isLicenseDescriptionInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isLicenseDescriptionSet() {
      return this._isSet(20);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setLicenseDescription(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LicenseDescription = param0;
   }

   public boolean getLicenseRequired() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().getLicenseRequired() : this._LicenseRequired;
   }

   public boolean isLicenseRequiredInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isLicenseRequiredSet() {
      return this._isSet(21);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setLicenseRequired(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LicenseRequired = param0;
   }

   public String getSpecVersion() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._performMacroSubstitution(this._getDelegateBean().getSpecVersion(), this) : this._SpecVersion;
   }

   public boolean isSpecVersionInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isSpecVersionSet() {
      return this._isSet(22);
   }

   public void setSpecVersion(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SpecVersion = param0;
   }

   public String getVendorName() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._performMacroSubstitution(this._getDelegateBean().getVendorName(), this) : this._VendorName;
   }

   public boolean isVendorNameInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isVendorNameSet() {
      return this._isSet(23);
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
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setVendorName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._VendorName = param0;
   }

   public String getVersion() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._performMacroSubstitution(this._getDelegateBean().getVersion(), this) : this._Version;
   }

   public boolean isVersionInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isVersionSet() {
      return this._isSet(24);
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setVersion(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._Version = param0;
   }

   public String getConnectionFactoryImpl() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._performMacroSubstitution(this._getDelegateBean().getConnectionFactoryImpl(), this) : this._ConnectionFactoryImpl;
   }

   public boolean isConnectionFactoryImplInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isConnectionFactoryImplSet() {
      return this._isSet(25);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(9);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ConnectorComponentMBeanImpl source = (ConnectorComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(9)) {
            source._postSetFirePropertyChange(9, wasSet, _oldVal, param0);
         }
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

   public void setConnectionFactoryImpl(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ConnectionFactoryImpl = param0;
   }

   public String getConnectionFactoryInterface() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._performMacroSubstitution(this._getDelegateBean().getConnectionFactoryInterface(), this) : this._ConnectionFactoryInterface;
   }

   public boolean isConnectionFactoryInterfaceInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isConnectionFactoryInterfaceSet() {
      return this._isSet(26);
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

   public void setConnectionFactoryInterface(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ConnectionFactoryInterface = param0;
   }

   public String getConnectionImpl() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._performMacroSubstitution(this._getDelegateBean().getConnectionImpl(), this) : this._ConnectionImpl;
   }

   public boolean isConnectionImplInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isConnectionImplSet() {
      return this._isSet(27);
   }

   public void setConnectionImpl(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ConnectionImpl = param0;
   }

   public String getConnectionInterface() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._performMacroSubstitution(this._getDelegateBean().getConnectionInterface(), this) : this._ConnectionInterface;
   }

   public boolean isConnectionInterfaceInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isConnectionInterfaceSet() {
      return this._isSet(28);
   }

   public void setConnectionInterface(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ConnectionInterface = param0;
   }

   public boolean getConnectionProfilingEnabled() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().getConnectionProfilingEnabled() : this._ConnectionProfilingEnabled;
   }

   public boolean isConnectionProfilingEnabledInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isConnectionProfilingEnabledSet() {
      return this._isSet(29);
   }

   public void setConnectionProfilingEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      boolean _oldVal = this._ConnectionProfilingEnabled;
      this._ConnectionProfilingEnabled = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ConnectorComponentMBeanImpl source = (ConnectorComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public String getManagedConnectionFactoryClass() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._performMacroSubstitution(this._getDelegateBean().getManagedConnectionFactoryClass(), this) : this._ManagedConnectionFactoryClass;
   }

   public boolean isManagedConnectionFactoryClassInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isManagedConnectionFactoryClassSet() {
      return this._isSet(30);
   }

   public void setManagedConnectionFactoryClass(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ManagedConnectionFactoryClass = param0;
   }

   public boolean getreauthenticationSupport() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().getreauthenticationSupport() : this._reauthenticationSupport;
   }

   public boolean isreauthenticationSupportInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isreauthenticationSupportSet() {
      return this._isSet(31);
   }

   public void setreauthenticationSupport(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._reauthenticationSupport = param0;
   }

   public String getTransactionSupport() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._performMacroSubstitution(this._getDelegateBean().getTransactionSupport(), this) : this._TransactionSupport;
   }

   public boolean isTransactionSupportInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isTransactionSupportSet() {
      return this._isSet(32);
   }

   public void setTransactionSupport(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._TransactionSupport = param0;
   }

   public Hashtable getConfigProperties() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().getConfigProperties() : this._ConfigProperties;
   }

   public boolean isConfigPropertiesInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isConfigPropertiesSet() {
      return this._isSet(33);
   }

   public void setConfigProperties(Hashtable param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ConfigProperties = param0;
   }

   public HashSet getAuthenticationMechanisms() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._getDelegateBean().getAuthenticationMechanisms() : this._AuthenticationMechanisms;
   }

   public String getAuthenticationMechanismsAsString() {
      return StringHelper.objectToString(this.getAuthenticationMechanisms());
   }

   public boolean isAuthenticationMechanismsInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isAuthenticationMechanismsSet() {
      return this._isSet(34);
   }

   public void setAuthenticationMechanismsAsString(String param0) {
      try {
         this.setAuthenticationMechanisms(StringHelper.stringToHashSet(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setAuthenticationMechanisms(HashSet param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(34);
      HashSet _oldVal = this._AuthenticationMechanisms;
      this._AuthenticationMechanisms = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ConnectorComponentMBeanImpl source = (ConnectorComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public HashSet getSecurityPermissions() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._getDelegateBean().getSecurityPermissions() : this._SecurityPermissions;
   }

   public boolean isSecurityPermissionsInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isSecurityPermissionsSet() {
      return this._isSet(35);
   }

   public void setSecurityPermissions(HashSet param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SecurityPermissions = param0;
   }

   public ClassLoader getClassLoader() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._getDelegateBean().getClassLoader() : this._ClassLoader;
   }

   public boolean isClassLoaderInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isClassLoaderSet() {
      return this._isSet(36);
   }

   public void setClassLoader(ClassLoader param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ClassLoader = param0;
   }

   public String getConnectionFactoryName() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._performMacroSubstitution(this._getDelegateBean().getConnectionFactoryName(), this) : this._ConnectionFactoryName;
   }

   public boolean isConnectionFactoryNameInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isConnectionFactoryNameSet() {
      return this._isSet(37);
   }

   public void setConnectionFactoryName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ConnectionFactoryName = param0;
   }

   public String getConnectionFactoryDescription() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._performMacroSubstitution(this._getDelegateBean().getConnectionFactoryDescription(), this) : this._ConnectionFactoryDescription;
   }

   public boolean isConnectionFactoryDescriptionInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isConnectionFactoryDescriptionSet() {
      return this._isSet(38);
   }

   public void setConnectionFactoryDescription(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ConnectionFactoryDescription = param0;
   }

   public int getMaxCapacity() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._getDelegateBean().getMaxCapacity() : this._MaxCapacity;
   }

   public boolean isMaxCapacityInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isMaxCapacitySet() {
      return this._isSet(39);
   }

   public void setMaxCapacity(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("MaxCapacity", param0, 1);
      this._MaxCapacity = param0;
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._ActivatedTargets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._customizer.setApplication((ApplicationMBean)null);
               if (initOne) {
                  break;
               }
            case 34:
               this._AuthenticationMechanisms = null;
               if (initOne) {
                  break;
               }
            case 36:
               this._ClassLoader = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._ConfigProperties = null;
               if (initOne) {
                  break;
               }
            case 38:
               this._ConnectionFactoryDescription = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._ConnectionFactoryImpl = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._ConnectionFactoryInterface = null;
               if (initOne) {
                  break;
               }
            case 37:
               this._ConnectionFactoryName = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._ConnectionImpl = null;
               if (initOne) {
                  break;
               }
            case 28:
               this._ConnectionInterface = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._ConnectionProfilingEnabled = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._DisplayName = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._EisType = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._LargeIcon = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._LicenseDescription = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._LicenseRequired = false;
               if (initOne) {
                  break;
               }
            case 30:
               this._ManagedConnectionFactoryClass = null;
               if (initOne) {
                  break;
               }
            case 39:
               this._MaxCapacity = 10;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 35:
               this._SecurityPermissions = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._SmallIcon = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._SpecVersion = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 10:
               this._customizer.setTargets(new TargetMBean[0]);
               if (initOne) {
                  break;
               }
            case 32:
               this._TransactionSupport = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._VendorName = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._Version = null;
               if (initOne) {
                  break;
               }
            case 31:
               this._reauthenticationSupport = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            case 13:
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
      return "ConnectorComponent";
   }

   public void putValue(String name, Object v) {
      TargetMBean[] oldVal;
      if (name.equals("ActivatedTargets")) {
         oldVal = this._ActivatedTargets;
         this._ActivatedTargets = (TargetMBean[])((TargetMBean[])v);
         this._postSet(14, oldVal, this._ActivatedTargets);
      } else if (name.equals("Application")) {
         ApplicationMBean oldVal = this._Application;
         this._Application = (ApplicationMBean)v;
         this._postSet(12, oldVal, this._Application);
      } else {
         HashSet oldVal;
         if (name.equals("AuthenticationMechanisms")) {
            oldVal = this._AuthenticationMechanisms;
            this._AuthenticationMechanisms = (HashSet)v;
            this._postSet(34, oldVal, this._AuthenticationMechanisms);
         } else if (name.equals("ClassLoader")) {
            ClassLoader oldVal = this._ClassLoader;
            this._ClassLoader = (ClassLoader)v;
            this._postSet(36, oldVal, this._ClassLoader);
         } else if (name.equals("ConfigProperties")) {
            Hashtable oldVal = this._ConfigProperties;
            this._ConfigProperties = (Hashtable)v;
            this._postSet(33, oldVal, this._ConfigProperties);
         } else {
            String oldVal;
            if (name.equals("ConnectionFactoryDescription")) {
               oldVal = this._ConnectionFactoryDescription;
               this._ConnectionFactoryDescription = (String)v;
               this._postSet(38, oldVal, this._ConnectionFactoryDescription);
            } else if (name.equals("ConnectionFactoryImpl")) {
               oldVal = this._ConnectionFactoryImpl;
               this._ConnectionFactoryImpl = (String)v;
               this._postSet(25, oldVal, this._ConnectionFactoryImpl);
            } else if (name.equals("ConnectionFactoryInterface")) {
               oldVal = this._ConnectionFactoryInterface;
               this._ConnectionFactoryInterface = (String)v;
               this._postSet(26, oldVal, this._ConnectionFactoryInterface);
            } else if (name.equals("ConnectionFactoryName")) {
               oldVal = this._ConnectionFactoryName;
               this._ConnectionFactoryName = (String)v;
               this._postSet(37, oldVal, this._ConnectionFactoryName);
            } else if (name.equals("ConnectionImpl")) {
               oldVal = this._ConnectionImpl;
               this._ConnectionImpl = (String)v;
               this._postSet(27, oldVal, this._ConnectionImpl);
            } else if (name.equals("ConnectionInterface")) {
               oldVal = this._ConnectionInterface;
               this._ConnectionInterface = (String)v;
               this._postSet(28, oldVal, this._ConnectionInterface);
            } else {
               boolean oldVal;
               if (name.equals("ConnectionProfilingEnabled")) {
                  oldVal = this._ConnectionProfilingEnabled;
                  this._ConnectionProfilingEnabled = (Boolean)v;
                  this._postSet(29, oldVal, this._ConnectionProfilingEnabled);
               } else if (name.equals("Description")) {
                  oldVal = this._Description;
                  this._Description = (String)v;
                  this._postSet(15, oldVal, this._Description);
               } else if (name.equals("DisplayName")) {
                  oldVal = this._DisplayName;
                  this._DisplayName = (String)v;
                  this._postSet(16, oldVal, this._DisplayName);
               } else if (name.equals("DynamicallyCreated")) {
                  oldVal = this._DynamicallyCreated;
                  this._DynamicallyCreated = (Boolean)v;
                  this._postSet(7, oldVal, this._DynamicallyCreated);
               } else if (name.equals("EisType")) {
                  oldVal = this._EisType;
                  this._EisType = (String)v;
                  this._postSet(17, oldVal, this._EisType);
               } else if (name.equals("LargeIcon")) {
                  oldVal = this._LargeIcon;
                  this._LargeIcon = (String)v;
                  this._postSet(19, oldVal, this._LargeIcon);
               } else if (name.equals("LicenseDescription")) {
                  oldVal = this._LicenseDescription;
                  this._LicenseDescription = (String)v;
                  this._postSet(20, oldVal, this._LicenseDescription);
               } else if (name.equals("LicenseRequired")) {
                  oldVal = this._LicenseRequired;
                  this._LicenseRequired = (Boolean)v;
                  this._postSet(21, oldVal, this._LicenseRequired);
               } else if (name.equals("ManagedConnectionFactoryClass")) {
                  oldVal = this._ManagedConnectionFactoryClass;
                  this._ManagedConnectionFactoryClass = (String)v;
                  this._postSet(30, oldVal, this._ManagedConnectionFactoryClass);
               } else if (name.equals("MaxCapacity")) {
                  int oldVal = this._MaxCapacity;
                  this._MaxCapacity = (Integer)v;
                  this._postSet(39, oldVal, this._MaxCapacity);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("SecurityPermissions")) {
                  oldVal = this._SecurityPermissions;
                  this._SecurityPermissions = (HashSet)v;
                  this._postSet(35, oldVal, this._SecurityPermissions);
               } else if (name.equals("SmallIcon")) {
                  oldVal = this._SmallIcon;
                  this._SmallIcon = (String)v;
                  this._postSet(18, oldVal, this._SmallIcon);
               } else if (name.equals("SpecVersion")) {
                  oldVal = this._SpecVersion;
                  this._SpecVersion = (String)v;
                  this._postSet(22, oldVal, this._SpecVersion);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("Targets")) {
                  oldVal = this._Targets;
                  this._Targets = (TargetMBean[])((TargetMBean[])v);
                  this._postSet(10, oldVal, this._Targets);
               } else if (name.equals("TransactionSupport")) {
                  oldVal = this._TransactionSupport;
                  this._TransactionSupport = (String)v;
                  this._postSet(32, oldVal, this._TransactionSupport);
               } else if (name.equals("VendorName")) {
                  oldVal = this._VendorName;
                  this._VendorName = (String)v;
                  this._postSet(23, oldVal, this._VendorName);
               } else if (name.equals("Version")) {
                  oldVal = this._Version;
                  this._Version = (String)v;
                  this._postSet(24, oldVal, this._Version);
               } else if (name.equals("customizer")) {
                  ConnectorComponent oldVal = this._customizer;
                  this._customizer = (ConnectorComponent)v;
               } else if (name.equals("reauthenticationSupport")) {
                  oldVal = this._reauthenticationSupport;
                  this._reauthenticationSupport = (Boolean)v;
                  this._postSet(31, oldVal, this._reauthenticationSupport);
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ActivatedTargets")) {
         return this._ActivatedTargets;
      } else if (name.equals("Application")) {
         return this._Application;
      } else if (name.equals("AuthenticationMechanisms")) {
         return this._AuthenticationMechanisms;
      } else if (name.equals("ClassLoader")) {
         return this._ClassLoader;
      } else if (name.equals("ConfigProperties")) {
         return this._ConfigProperties;
      } else if (name.equals("ConnectionFactoryDescription")) {
         return this._ConnectionFactoryDescription;
      } else if (name.equals("ConnectionFactoryImpl")) {
         return this._ConnectionFactoryImpl;
      } else if (name.equals("ConnectionFactoryInterface")) {
         return this._ConnectionFactoryInterface;
      } else if (name.equals("ConnectionFactoryName")) {
         return this._ConnectionFactoryName;
      } else if (name.equals("ConnectionImpl")) {
         return this._ConnectionImpl;
      } else if (name.equals("ConnectionInterface")) {
         return this._ConnectionInterface;
      } else if (name.equals("ConnectionProfilingEnabled")) {
         return new Boolean(this._ConnectionProfilingEnabled);
      } else if (name.equals("Description")) {
         return this._Description;
      } else if (name.equals("DisplayName")) {
         return this._DisplayName;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("EisType")) {
         return this._EisType;
      } else if (name.equals("LargeIcon")) {
         return this._LargeIcon;
      } else if (name.equals("LicenseDescription")) {
         return this._LicenseDescription;
      } else if (name.equals("LicenseRequired")) {
         return new Boolean(this._LicenseRequired);
      } else if (name.equals("ManagedConnectionFactoryClass")) {
         return this._ManagedConnectionFactoryClass;
      } else if (name.equals("MaxCapacity")) {
         return new Integer(this._MaxCapacity);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("SecurityPermissions")) {
         return this._SecurityPermissions;
      } else if (name.equals("SmallIcon")) {
         return this._SmallIcon;
      } else if (name.equals("SpecVersion")) {
         return this._SpecVersion;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("TransactionSupport")) {
         return this._TransactionSupport;
      } else if (name.equals("VendorName")) {
         return this._VendorName;
      } else if (name.equals("Version")) {
         return this._Version;
      } else if (name.equals("customizer")) {
         return this._customizer;
      } else {
         return name.equals("reauthenticationSupport") ? new Boolean(this._reauthenticationSupport) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ComponentMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 9:
            case 13:
            case 14:
            case 18:
            case 21:
            case 22:
            case 26:
            case 27:
            case 29:
            case 31:
            default:
               break;
            case 6:
               if (s.equals("target")) {
                  return 10;
               }
               break;
            case 7:
               if (s.equals("version")) {
                  return 24;
               }
               break;
            case 8:
               if (s.equals("eis-type")) {
                  return 17;
               }
               break;
            case 10:
               if (s.equals("large-icon")) {
                  return 19;
               }

               if (s.equals("small-icon")) {
                  return 18;
               }
               break;
            case 11:
               if (s.equals("application")) {
                  return 12;
               }

               if (s.equals("description")) {
                  return 15;
               }

               if (s.equals("vendor-name")) {
                  return 23;
               }
               break;
            case 12:
               if (s.equals("class-loader")) {
                  return 36;
               }

               if (s.equals("display-name")) {
                  return 16;
               }

               if (s.equals("max-capacity")) {
                  return 39;
               }

               if (s.equals("spec-version")) {
                  return 22;
               }
               break;
            case 15:
               if (s.equals("connection-impl")) {
                  return 27;
               }
               break;
            case 16:
               if (s.equals("activated-target")) {
                  return 14;
               }

               if (s.equals("license-required")) {
                  return 21;
               }
               break;
            case 17:
               if (s.equals("config-properties")) {
                  return 33;
               }
               break;
            case 19:
               if (s.equals("license-description")) {
                  return 20;
               }

               if (s.equals("transaction-support")) {
                  return 32;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("connection-interface")) {
                  return 28;
               }

               if (s.equals("security-permissions")) {
                  return 35;
               }
               break;
            case 23:
               if (s.equals("connection-factory-impl")) {
                  return 25;
               }

               if (s.equals("connection-factory-name")) {
                  return 37;
               }
               break;
            case 24:
               if (s.equals("reauthentication-support")) {
                  return 31;
               }
               break;
            case 25:
               if (s.equals("authentication-mechanisms")) {
                  return 34;
               }
               break;
            case 28:
               if (s.equals("connection-factory-interface")) {
                  return 26;
               }

               if (s.equals("connection-profiling-enabled")) {
                  return 29;
               }
               break;
            case 30:
               if (s.equals("connection-factory-description")) {
                  return 38;
               }
               break;
            case 32:
               if (s.equals("managed-connection-factory-class")) {
                  return 30;
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
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            case 13:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "target";
            case 12:
               return "application";
            case 14:
               return "activated-target";
            case 15:
               return "description";
            case 16:
               return "display-name";
            case 17:
               return "eis-type";
            case 18:
               return "small-icon";
            case 19:
               return "large-icon";
            case 20:
               return "license-description";
            case 21:
               return "license-required";
            case 22:
               return "spec-version";
            case 23:
               return "vendor-name";
            case 24:
               return "version";
            case 25:
               return "connection-factory-impl";
            case 26:
               return "connection-factory-interface";
            case 27:
               return "connection-impl";
            case 28:
               return "connection-interface";
            case 29:
               return "connection-profiling-enabled";
            case 30:
               return "managed-connection-factory-class";
            case 31:
               return "reauthentication-support";
            case 32:
               return "transaction-support";
            case 33:
               return "config-properties";
            case 34:
               return "authentication-mechanisms";
            case 35:
               return "security-permissions";
            case 36:
               return "class-loader";
            case 37:
               return "connection-factory-name";
            case 38:
               return "connection-factory-description";
            case 39:
               return "max-capacity";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 14:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 29:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

   protected static class Helper extends ComponentMBeanImpl.Helper {
      private ConnectorComponentMBeanImpl bean;

      protected Helper(ConnectorComponentMBeanImpl bean) {
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
            case 11:
            case 13:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "Targets";
            case 12:
               return "Application";
            case 14:
               return "ActivatedTargets";
            case 15:
               return "Description";
            case 16:
               return "DisplayName";
            case 17:
               return "EisType";
            case 18:
               return "SmallIcon";
            case 19:
               return "LargeIcon";
            case 20:
               return "LicenseDescription";
            case 21:
               return "LicenseRequired";
            case 22:
               return "SpecVersion";
            case 23:
               return "VendorName";
            case 24:
               return "Version";
            case 25:
               return "ConnectionFactoryImpl";
            case 26:
               return "ConnectionFactoryInterface";
            case 27:
               return "ConnectionImpl";
            case 28:
               return "ConnectionInterface";
            case 29:
               return "ConnectionProfilingEnabled";
            case 30:
               return "ManagedConnectionFactoryClass";
            case 31:
               return "reauthenticationSupport";
            case 32:
               return "TransactionSupport";
            case 33:
               return "ConfigProperties";
            case 34:
               return "AuthenticationMechanisms";
            case 35:
               return "SecurityPermissions";
            case 36:
               return "ClassLoader";
            case 37:
               return "ConnectionFactoryName";
            case 38:
               return "ConnectionFactoryDescription";
            case 39:
               return "MaxCapacity";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActivatedTargets")) {
            return 14;
         } else if (propName.equals("Application")) {
            return 12;
         } else if (propName.equals("AuthenticationMechanisms")) {
            return 34;
         } else if (propName.equals("ClassLoader")) {
            return 36;
         } else if (propName.equals("ConfigProperties")) {
            return 33;
         } else if (propName.equals("ConnectionFactoryDescription")) {
            return 38;
         } else if (propName.equals("ConnectionFactoryImpl")) {
            return 25;
         } else if (propName.equals("ConnectionFactoryInterface")) {
            return 26;
         } else if (propName.equals("ConnectionFactoryName")) {
            return 37;
         } else if (propName.equals("ConnectionImpl")) {
            return 27;
         } else if (propName.equals("ConnectionInterface")) {
            return 28;
         } else if (propName.equals("ConnectionProfilingEnabled")) {
            return 29;
         } else if (propName.equals("Description")) {
            return 15;
         } else if (propName.equals("DisplayName")) {
            return 16;
         } else if (propName.equals("EisType")) {
            return 17;
         } else if (propName.equals("LargeIcon")) {
            return 19;
         } else if (propName.equals("LicenseDescription")) {
            return 20;
         } else if (propName.equals("LicenseRequired")) {
            return 21;
         } else if (propName.equals("ManagedConnectionFactoryClass")) {
            return 30;
         } else if (propName.equals("MaxCapacity")) {
            return 39;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("SecurityPermissions")) {
            return 35;
         } else if (propName.equals("SmallIcon")) {
            return 18;
         } else if (propName.equals("SpecVersion")) {
            return 22;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else if (propName.equals("TransactionSupport")) {
            return 32;
         } else if (propName.equals("VendorName")) {
            return 23;
         } else if (propName.equals("Version")) {
            return 24;
         } else if (propName.equals("reauthenticationSupport")) {
            return 31;
         } else {
            return propName.equals("DynamicallyCreated") ? 7 : super.getPropertyIndex(propName);
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
            if (this.bean.isActivatedTargetsSet()) {
               buf.append("ActivatedTargets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getActivatedTargets())));
            }

            if (this.bean.isApplicationSet()) {
               buf.append("Application");
               buf.append(String.valueOf(this.bean.getApplication()));
            }

            if (this.bean.isAuthenticationMechanismsSet()) {
               buf.append("AuthenticationMechanisms");
               buf.append(String.valueOf(this.bean.getAuthenticationMechanisms()));
            }

            if (this.bean.isClassLoaderSet()) {
               buf.append("ClassLoader");
               buf.append(String.valueOf(this.bean.getClassLoader()));
            }

            if (this.bean.isConfigPropertiesSet()) {
               buf.append("ConfigProperties");
               buf.append(String.valueOf(this.bean.getConfigProperties()));
            }

            if (this.bean.isConnectionFactoryDescriptionSet()) {
               buf.append("ConnectionFactoryDescription");
               buf.append(String.valueOf(this.bean.getConnectionFactoryDescription()));
            }

            if (this.bean.isConnectionFactoryImplSet()) {
               buf.append("ConnectionFactoryImpl");
               buf.append(String.valueOf(this.bean.getConnectionFactoryImpl()));
            }

            if (this.bean.isConnectionFactoryInterfaceSet()) {
               buf.append("ConnectionFactoryInterface");
               buf.append(String.valueOf(this.bean.getConnectionFactoryInterface()));
            }

            if (this.bean.isConnectionFactoryNameSet()) {
               buf.append("ConnectionFactoryName");
               buf.append(String.valueOf(this.bean.getConnectionFactoryName()));
            }

            if (this.bean.isConnectionImplSet()) {
               buf.append("ConnectionImpl");
               buf.append(String.valueOf(this.bean.getConnectionImpl()));
            }

            if (this.bean.isConnectionInterfaceSet()) {
               buf.append("ConnectionInterface");
               buf.append(String.valueOf(this.bean.getConnectionInterface()));
            }

            if (this.bean.isConnectionProfilingEnabledSet()) {
               buf.append("ConnectionProfilingEnabled");
               buf.append(String.valueOf(this.bean.getConnectionProfilingEnabled()));
            }

            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isDisplayNameSet()) {
               buf.append("DisplayName");
               buf.append(String.valueOf(this.bean.getDisplayName()));
            }

            if (this.bean.isEisTypeSet()) {
               buf.append("EisType");
               buf.append(String.valueOf(this.bean.getEisType()));
            }

            if (this.bean.isLargeIconSet()) {
               buf.append("LargeIcon");
               buf.append(String.valueOf(this.bean.getLargeIcon()));
            }

            if (this.bean.isLicenseDescriptionSet()) {
               buf.append("LicenseDescription");
               buf.append(String.valueOf(this.bean.getLicenseDescription()));
            }

            if (this.bean.isLicenseRequiredSet()) {
               buf.append("LicenseRequired");
               buf.append(String.valueOf(this.bean.getLicenseRequired()));
            }

            if (this.bean.isManagedConnectionFactoryClassSet()) {
               buf.append("ManagedConnectionFactoryClass");
               buf.append(String.valueOf(this.bean.getManagedConnectionFactoryClass()));
            }

            if (this.bean.isMaxCapacitySet()) {
               buf.append("MaxCapacity");
               buf.append(String.valueOf(this.bean.getMaxCapacity()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isSecurityPermissionsSet()) {
               buf.append("SecurityPermissions");
               buf.append(String.valueOf(this.bean.getSecurityPermissions()));
            }

            if (this.bean.isSmallIconSet()) {
               buf.append("SmallIcon");
               buf.append(String.valueOf(this.bean.getSmallIcon()));
            }

            if (this.bean.isSpecVersionSet()) {
               buf.append("SpecVersion");
               buf.append(String.valueOf(this.bean.getSpecVersion()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isTransactionSupportSet()) {
               buf.append("TransactionSupport");
               buf.append(String.valueOf(this.bean.getTransactionSupport()));
            }

            if (this.bean.isVendorNameSet()) {
               buf.append("VendorName");
               buf.append(String.valueOf(this.bean.getVendorName()));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            if (this.bean.isreauthenticationSupportSet()) {
               buf.append("reauthenticationSupport");
               buf.append(String.valueOf(this.bean.getreauthenticationSupport()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
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
            ConnectorComponentMBeanImpl otherTyped = (ConnectorComponentMBeanImpl)other;
            this.computeDiff("AuthenticationMechanisms", this.bean.getAuthenticationMechanisms(), otherTyped.getAuthenticationMechanisms(), false);
            this.computeDiff("ConnectionProfilingEnabled", this.bean.getConnectionProfilingEnabled(), otherTyped.getConnectionProfilingEnabled(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConnectorComponentMBeanImpl original = (ConnectorComponentMBeanImpl)event.getSourceBean();
            ConnectorComponentMBeanImpl proposed = (ConnectorComponentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("ActivatedTargets") && !prop.equals("Application")) {
                  if (prop.equals("AuthenticationMechanisms")) {
                     original.setAuthenticationMechanisms(proposed.getAuthenticationMechanisms() == null ? null : (HashSet)proposed.getAuthenticationMechanisms().clone());
                     original._conditionalUnset(update.isUnsetUpdate(), 34);
                  } else if (!prop.equals("ClassLoader") && !prop.equals("ConfigProperties") && !prop.equals("ConnectionFactoryDescription") && !prop.equals("ConnectionFactoryImpl") && !prop.equals("ConnectionFactoryInterface") && !prop.equals("ConnectionFactoryName") && !prop.equals("ConnectionImpl") && !prop.equals("ConnectionInterface")) {
                     if (prop.equals("ConnectionProfilingEnabled")) {
                        original.setConnectionProfilingEnabled(proposed.getConnectionProfilingEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 29);
                     } else if (!prop.equals("Description") && !prop.equals("DisplayName") && !prop.equals("EisType") && !prop.equals("LargeIcon") && !prop.equals("LicenseDescription") && !prop.equals("LicenseRequired") && !prop.equals("ManagedConnectionFactoryClass") && !prop.equals("MaxCapacity")) {
                        if (prop.equals("Name")) {
                           original.setName(proposed.getName());
                           original._conditionalUnset(update.isUnsetUpdate(), 2);
                        } else if (!prop.equals("SecurityPermissions") && !prop.equals("SmallIcon") && !prop.equals("SpecVersion")) {
                           if (prop.equals("Tags")) {
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
                           } else if (prop.equals("Targets")) {
                              original.setTargetsAsString(proposed.getTargetsAsString());
                              original._conditionalUnset(update.isUnsetUpdate(), 10);
                           } else if (!prop.equals("TransactionSupport") && !prop.equals("VendorName") && !prop.equals("Version") && !prop.equals("reauthenticationSupport") && !prop.equals("DynamicallyCreated")) {
                              super.applyPropertyUpdate(event, update);
                           }
                        }
                     }
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
            ConnectorComponentMBeanImpl copy = (ConnectorComponentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AuthenticationMechanisms")) && this.bean.isAuthenticationMechanismsSet()) {
               copy.setAuthenticationMechanisms(this.bean.getAuthenticationMechanisms());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionProfilingEnabled")) && this.bean.isConnectionProfilingEnabledSet()) {
               copy.setConnectionProfilingEnabled(this.bean.getConnectionProfilingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
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
         this.inferSubTree(this.bean.getActivatedTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getApplication(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}
