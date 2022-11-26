package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.EJBComponent;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class EJBComponentMBeanImpl extends ComponentMBeanImpl implements EJBComponentMBean, Serializable {
   private TargetMBean[] _ActivatedTargets;
   private ApplicationMBean _Application;
   private boolean _DynamicallyCreated;
   private String _ExtraEjbcOptions;
   private String _ExtraRmicOptions;
   private boolean _ForceGeneration;
   private String _JavaCompiler;
   private String _JavaCompilerPostClassPath;
   private String _JavaCompilerPreClassPath;
   private boolean _KeepGenerated;
   private String _Name;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private String _TmpPath;
   private String _VerboseEJBDeploymentEnabled;
   private transient EJBComponent _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private EJBComponentMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(EJBComponentMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(EJBComponentMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public EJBComponentMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(EJBComponentMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      EJBComponentMBeanImpl oldDelegate = this._DelegateBean;
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

   public EJBComponentMBeanImpl() {
      try {
         this._customizer = new EJBComponent(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public EJBComponentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new EJBComponent(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public EJBComponentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new EJBComponent(this);
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

   public String getJavaCompiler() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._performMacroSubstitution(this._getDelegateBean().getJavaCompiler(), this) : this._JavaCompiler;
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

   public boolean isJavaCompilerInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isJavaCompilerSet() {
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
                        EJBComponentMBeanImpl.this.addTarget((TargetMBean)value);
                        EJBComponentMBeanImpl.this._getHelper().reorderArrayObjects((Object[])EJBComponentMBeanImpl.this._Targets, this.getHandbackObject());
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

   public void setJavaCompiler(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      String _oldVal = this._JavaCompiler;
      this._JavaCompiler = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

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
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
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
                  return EJBComponentMBeanImpl.this.getTargets();
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
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
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

   public String getJavaCompilerPreClassPath() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._performMacroSubstitution(this._getDelegateBean().getJavaCompilerPreClassPath(), this) : this._JavaCompilerPreClassPath;
   }

   public boolean isJavaCompilerPreClassPathInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isJavaCompilerPreClassPathSet() {
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

   public void setJavaCompilerPreClassPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      String _oldVal = this._JavaCompilerPreClassPath;
      this._JavaCompilerPreClassPath = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public TargetMBean[] getActivatedTargets() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getActivatedTargets() : this._customizer.getActivatedTargets();
   }

   public String getJavaCompilerPostClassPath() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._performMacroSubstitution(this._getDelegateBean().getJavaCompilerPostClassPath(), this) : this._JavaCompilerPostClassPath;
   }

   public boolean isActivatedTargetsInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isActivatedTargetsSet() {
      return this._isSet(14);
   }

   public boolean isJavaCompilerPostClassPathInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isJavaCompilerPostClassPathSet() {
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

   public void setJavaCompilerPostClassPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      String _oldVal = this._JavaCompilerPostClassPath;
      this._JavaCompilerPostClassPath = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public String getExtraRmicOptions() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._performMacroSubstitution(this._getDelegateBean().getExtraRmicOptions(), this) : this._ExtraRmicOptions;
   }

   public boolean isExtraRmicOptionsInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isExtraRmicOptionsSet() {
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

   public void setExtraRmicOptions(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      String _oldVal = this._ExtraRmicOptions;
      this._ExtraRmicOptions = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean activated(TargetMBean param0) {
      return this._customizer.activated(param0);
   }

   public boolean getKeepGenerated() {
      if (!this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19)) {
         return this._getDelegateBean().getKeepGenerated();
      } else if (!this._isSet(19)) {
         return !this._isSecureModeEnabled();
      } else {
         return this._KeepGenerated;
      }
   }

   public boolean isKeepGeneratedInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isKeepGeneratedSet() {
      return this._isSet(19);
   }

   public void refreshDDsIfNeeded(String[] param0) {
      this._customizer.refreshDDsIfNeeded(param0);
   }

   public void setKeepGenerated(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      boolean _oldVal = this._KeepGenerated;
      this._KeepGenerated = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getForceGeneration() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getForceGeneration() : this._ForceGeneration;
   }

   public boolean isForceGenerationInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isForceGenerationSet() {
      return this._isSet(20);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setForceGeneration(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      boolean _oldVal = this._ForceGeneration;
      this._ForceGeneration = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTmpPath() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._performMacroSubstitution(this._getDelegateBean().getTmpPath(), this) : this._TmpPath;
   }

   public boolean isTmpPathInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isTmpPathSet() {
      return this._isSet(21);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setTmpPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(21);
      String _oldVal = this._TmpPath;
      this._TmpPath = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public String getVerboseEJBDeploymentEnabled() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._performMacroSubstitution(this._getDelegateBean().getVerboseEJBDeploymentEnabled(), this) : this._VerboseEJBDeploymentEnabled;
   }

   public boolean isVerboseEJBDeploymentEnabledInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isVerboseEJBDeploymentEnabledSet() {
      return this._isSet(22);
   }

   public void setVerboseEJBDeploymentEnabled(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      String _oldVal = this._VerboseEJBDeploymentEnabled;
      this._VerboseEJBDeploymentEnabled = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public String getExtraEjbcOptions() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._performMacroSubstitution(this._getDelegateBean().getExtraEjbcOptions(), this) : this._ExtraEjbcOptions;
   }

   public boolean isExtraEjbcOptionsInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isExtraEjbcOptionsSet() {
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

   public void setExtraEjbcOptions(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(23);
      String _oldVal = this._ExtraEjbcOptions;
      this._ExtraEjbcOptions = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

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
         EJBComponentMBeanImpl source = (EJBComponentMBeanImpl)var4.next();
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
            case 23:
               this._ExtraEjbcOptions = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._ExtraRmicOptions = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._ForceGeneration = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._JavaCompiler = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._JavaCompilerPostClassPath = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._JavaCompilerPreClassPath = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._KeepGenerated = true;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
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
            case 21:
               this._TmpPath = "tmp_ejb";
               if (initOne) {
                  break;
               }
            case 22:
               this._VerboseEJBDeploymentEnabled = "false";
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
      return "EJBComponent";
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
         boolean oldVal;
         if (name.equals("DynamicallyCreated")) {
            oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else {
            String oldVal;
            if (name.equals("ExtraEjbcOptions")) {
               oldVal = this._ExtraEjbcOptions;
               this._ExtraEjbcOptions = (String)v;
               this._postSet(23, oldVal, this._ExtraEjbcOptions);
            } else if (name.equals("ExtraRmicOptions")) {
               oldVal = this._ExtraRmicOptions;
               this._ExtraRmicOptions = (String)v;
               this._postSet(18, oldVal, this._ExtraRmicOptions);
            } else if (name.equals("ForceGeneration")) {
               oldVal = this._ForceGeneration;
               this._ForceGeneration = (Boolean)v;
               this._postSet(20, oldVal, this._ForceGeneration);
            } else if (name.equals("JavaCompiler")) {
               oldVal = this._JavaCompiler;
               this._JavaCompiler = (String)v;
               this._postSet(15, oldVal, this._JavaCompiler);
            } else if (name.equals("JavaCompilerPostClassPath")) {
               oldVal = this._JavaCompilerPostClassPath;
               this._JavaCompilerPostClassPath = (String)v;
               this._postSet(17, oldVal, this._JavaCompilerPostClassPath);
            } else if (name.equals("JavaCompilerPreClassPath")) {
               oldVal = this._JavaCompilerPreClassPath;
               this._JavaCompilerPreClassPath = (String)v;
               this._postSet(16, oldVal, this._JavaCompilerPreClassPath);
            } else if (name.equals("KeepGenerated")) {
               oldVal = this._KeepGenerated;
               this._KeepGenerated = (Boolean)v;
               this._postSet(19, oldVal, this._KeepGenerated);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("Targets")) {
               oldVal = this._Targets;
               this._Targets = (TargetMBean[])((TargetMBean[])v);
               this._postSet(10, oldVal, this._Targets);
            } else if (name.equals("TmpPath")) {
               oldVal = this._TmpPath;
               this._TmpPath = (String)v;
               this._postSet(21, oldVal, this._TmpPath);
            } else if (name.equals("VerboseEJBDeploymentEnabled")) {
               oldVal = this._VerboseEJBDeploymentEnabled;
               this._VerboseEJBDeploymentEnabled = (String)v;
               this._postSet(22, oldVal, this._VerboseEJBDeploymentEnabled);
            } else if (name.equals("customizer")) {
               EJBComponent oldVal = this._customizer;
               this._customizer = (EJBComponent)v;
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ActivatedTargets")) {
         return this._ActivatedTargets;
      } else if (name.equals("Application")) {
         return this._Application;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("ExtraEjbcOptions")) {
         return this._ExtraEjbcOptions;
      } else if (name.equals("ExtraRmicOptions")) {
         return this._ExtraRmicOptions;
      } else if (name.equals("ForceGeneration")) {
         return new Boolean(this._ForceGeneration);
      } else if (name.equals("JavaCompiler")) {
         return this._JavaCompiler;
      } else if (name.equals("JavaCompilerPostClassPath")) {
         return this._JavaCompilerPostClassPath;
      } else if (name.equals("JavaCompilerPreClassPath")) {
         return this._JavaCompilerPreClassPath;
      } else if (name.equals("KeepGenerated")) {
         return new Boolean(this._KeepGenerated);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("TmpPath")) {
         return this._TmpPath;
      } else if (name.equals("VerboseEJBDeploymentEnabled")) {
         return this._VerboseEJBDeploymentEnabled;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
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
            case 7:
            case 9:
            case 10:
            case 12:
            case 15:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            default:
               break;
            case 6:
               if (s.equals("target")) {
                  return 10;
               }
               break;
            case 8:
               if (s.equals("tmp-path")) {
                  return 21;
               }
               break;
            case 11:
               if (s.equals("application")) {
                  return 12;
               }
               break;
            case 13:
               if (s.equals("java-compiler")) {
                  return 15;
               }
               break;
            case 14:
               if (s.equals("keep-generated")) {
                  return 19;
               }
               break;
            case 16:
               if (s.equals("activated-target")) {
                  return 14;
               }

               if (s.equals("force-generation")) {
                  return 20;
               }
               break;
            case 18:
               if (s.equals("extra-ejbc-options")) {
                  return 23;
               }

               if (s.equals("extra-rmic-options")) {
                  return 18;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 28:
               if (s.equals("java-compiler-pre-class-path")) {
                  return 16;
               }
               break;
            case 29:
               if (s.equals("java-compiler-post-class-path")) {
                  return 17;
               }

               if (s.equals("verboseejb-deployment-enabled")) {
                  return 22;
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
               return "java-compiler";
            case 16:
               return "java-compiler-pre-class-path";
            case 17:
               return "java-compiler-post-class-path";
            case 18:
               return "extra-rmic-options";
            case 19:
               return "keep-generated";
            case 20:
               return "force-generation";
            case 21:
               return "tmp-path";
            case 22:
               return "verboseejb-deployment-enabled";
            case 23:
               return "extra-ejbc-options";
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
      private EJBComponentMBeanImpl bean;

      protected Helper(EJBComponentMBeanImpl bean) {
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
               return "JavaCompiler";
            case 16:
               return "JavaCompilerPreClassPath";
            case 17:
               return "JavaCompilerPostClassPath";
            case 18:
               return "ExtraRmicOptions";
            case 19:
               return "KeepGenerated";
            case 20:
               return "ForceGeneration";
            case 21:
               return "TmpPath";
            case 22:
               return "VerboseEJBDeploymentEnabled";
            case 23:
               return "ExtraEjbcOptions";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActivatedTargets")) {
            return 14;
         } else if (propName.equals("Application")) {
            return 12;
         } else if (propName.equals("ExtraEjbcOptions")) {
            return 23;
         } else if (propName.equals("ExtraRmicOptions")) {
            return 18;
         } else if (propName.equals("ForceGeneration")) {
            return 20;
         } else if (propName.equals("JavaCompiler")) {
            return 15;
         } else if (propName.equals("JavaCompilerPostClassPath")) {
            return 17;
         } else if (propName.equals("JavaCompilerPreClassPath")) {
            return 16;
         } else if (propName.equals("KeepGenerated")) {
            return 19;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else if (propName.equals("TmpPath")) {
            return 21;
         } else if (propName.equals("VerboseEJBDeploymentEnabled")) {
            return 22;
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

            if (this.bean.isExtraEjbcOptionsSet()) {
               buf.append("ExtraEjbcOptions");
               buf.append(String.valueOf(this.bean.getExtraEjbcOptions()));
            }

            if (this.bean.isExtraRmicOptionsSet()) {
               buf.append("ExtraRmicOptions");
               buf.append(String.valueOf(this.bean.getExtraRmicOptions()));
            }

            if (this.bean.isForceGenerationSet()) {
               buf.append("ForceGeneration");
               buf.append(String.valueOf(this.bean.getForceGeneration()));
            }

            if (this.bean.isJavaCompilerSet()) {
               buf.append("JavaCompiler");
               buf.append(String.valueOf(this.bean.getJavaCompiler()));
            }

            if (this.bean.isJavaCompilerPostClassPathSet()) {
               buf.append("JavaCompilerPostClassPath");
               buf.append(String.valueOf(this.bean.getJavaCompilerPostClassPath()));
            }

            if (this.bean.isJavaCompilerPreClassPathSet()) {
               buf.append("JavaCompilerPreClassPath");
               buf.append(String.valueOf(this.bean.getJavaCompilerPreClassPath()));
            }

            if (this.bean.isKeepGeneratedSet()) {
               buf.append("KeepGenerated");
               buf.append(String.valueOf(this.bean.getKeepGenerated()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isTmpPathSet()) {
               buf.append("TmpPath");
               buf.append(String.valueOf(this.bean.getTmpPath()));
            }

            if (this.bean.isVerboseEJBDeploymentEnabledSet()) {
               buf.append("VerboseEJBDeploymentEnabled");
               buf.append(String.valueOf(this.bean.getVerboseEJBDeploymentEnabled()));
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
            EJBComponentMBeanImpl otherTyped = (EJBComponentMBeanImpl)other;
            this.computeDiff("ExtraEjbcOptions", this.bean.getExtraEjbcOptions(), otherTyped.getExtraEjbcOptions(), false);
            this.computeDiff("ExtraRmicOptions", this.bean.getExtraRmicOptions(), otherTyped.getExtraRmicOptions(), false);
            this.computeDiff("ForceGeneration", this.bean.getForceGeneration(), otherTyped.getForceGeneration(), false);
            this.computeDiff("JavaCompiler", this.bean.getJavaCompiler(), otherTyped.getJavaCompiler(), true);
            this.computeDiff("JavaCompilerPostClassPath", this.bean.getJavaCompilerPostClassPath(), otherTyped.getJavaCompilerPostClassPath(), false);
            this.computeDiff("JavaCompilerPreClassPath", this.bean.getJavaCompilerPreClassPath(), otherTyped.getJavaCompilerPreClassPath(), false);
            this.computeDiff("KeepGenerated", this.bean.getKeepGenerated(), otherTyped.getKeepGenerated(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            this.computeDiff("TmpPath", this.bean.getTmpPath(), otherTyped.getTmpPath(), false);
            this.computeDiff("VerboseEJBDeploymentEnabled", this.bean.getVerboseEJBDeploymentEnabled(), otherTyped.getVerboseEJBDeploymentEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EJBComponentMBeanImpl original = (EJBComponentMBeanImpl)event.getSourceBean();
            EJBComponentMBeanImpl proposed = (EJBComponentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("ActivatedTargets") && !prop.equals("Application")) {
                  if (prop.equals("ExtraEjbcOptions")) {
                     original.setExtraEjbcOptions(proposed.getExtraEjbcOptions());
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  } else if (prop.equals("ExtraRmicOptions")) {
                     original.setExtraRmicOptions(proposed.getExtraRmicOptions());
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  } else if (prop.equals("ForceGeneration")) {
                     original.setForceGeneration(proposed.getForceGeneration());
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (prop.equals("JavaCompiler")) {
                     original.setJavaCompiler(proposed.getJavaCompiler());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("JavaCompilerPostClassPath")) {
                     original.setJavaCompilerPostClassPath(proposed.getJavaCompilerPostClassPath());
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  } else if (prop.equals("JavaCompilerPreClassPath")) {
                     original.setJavaCompilerPreClassPath(proposed.getJavaCompilerPreClassPath());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("KeepGenerated")) {
                     original.setKeepGenerated(proposed.getKeepGenerated());
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
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
                  } else if (prop.equals("Targets")) {
                     original.setTargetsAsString(proposed.getTargetsAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("TmpPath")) {
                     original.setTmpPath(proposed.getTmpPath());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (prop.equals("VerboseEJBDeploymentEnabled")) {
                     original.setVerboseEJBDeploymentEnabled(proposed.getVerboseEJBDeploymentEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (!prop.equals("DynamicallyCreated")) {
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
            EJBComponentMBeanImpl copy = (EJBComponentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ExtraEjbcOptions")) && this.bean.isExtraEjbcOptionsSet()) {
               copy.setExtraEjbcOptions(this.bean.getExtraEjbcOptions());
            }

            if ((excludeProps == null || !excludeProps.contains("ExtraRmicOptions")) && this.bean.isExtraRmicOptionsSet()) {
               copy.setExtraRmicOptions(this.bean.getExtraRmicOptions());
            }

            if ((excludeProps == null || !excludeProps.contains("ForceGeneration")) && this.bean.isForceGenerationSet()) {
               copy.setForceGeneration(this.bean.getForceGeneration());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaCompiler")) && this.bean.isJavaCompilerSet()) {
               copy.setJavaCompiler(this.bean.getJavaCompiler());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaCompilerPostClassPath")) && this.bean.isJavaCompilerPostClassPathSet()) {
               copy.setJavaCompilerPostClassPath(this.bean.getJavaCompilerPostClassPath());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaCompilerPreClassPath")) && this.bean.isJavaCompilerPreClassPathSet()) {
               copy.setJavaCompilerPreClassPath(this.bean.getJavaCompilerPreClassPath());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepGenerated")) && this.bean.isKeepGeneratedSet()) {
               copy.setKeepGenerated(this.bean.getKeepGenerated());
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

            if ((excludeProps == null || !excludeProps.contains("TmpPath")) && this.bean.isTmpPathSet()) {
               copy.setTmpPath(this.bean.getTmpPath());
            }

            if ((excludeProps == null || !excludeProps.contains("VerboseEJBDeploymentEnabled")) && this.bean.isVerboseEJBDeploymentEnabledSet()) {
               copy.setVerboseEJBDeploymentEnabled(this.bean.getVerboseEJBDeploymentEnabled());
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
