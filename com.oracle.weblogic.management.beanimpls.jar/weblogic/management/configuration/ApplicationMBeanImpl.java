package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.Application;
import weblogic.utils.ArrayUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ApplicationMBeanImpl extends ConfigurationMBeanImpl implements ApplicationMBean, Serializable {
   private String _AltDescriptorPath;
   private String _AltWLSDescriptorPath;
   private AppDeploymentMBean _AppDeployment;
   private ComponentMBean[] _Components;
   private ConnectorComponentMBean[] _ConnectorComponents;
   private boolean _DelegationEnabled;
   private boolean _Deployed;
   private int _DeploymentTimeout;
   private String _DeploymentType;
   private boolean _DynamicallyCreated;
   private EJBComponentMBean[] _EJBComponents;
   private boolean _Ear;
   private String _FullPath;
   private boolean _InternalApp;
   private int _InternalType;
   private JDBCPoolComponentMBean[] _JDBCPoolComponents;
   private int _LoadOrder;
   private String _Name;
   private String _Notes;
   private String _Path;
   private String[] _StagedTargets;
   private String _StagingMode;
   private String _StagingPath;
   private String[] _Tags;
   private boolean _TwoPhase;
   private WebAppComponentMBean[] _WebAppComponents;
   private WebServiceComponentMBean[] _WebServiceComponents;
   private transient Application _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ApplicationMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ApplicationMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ApplicationMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ApplicationMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ApplicationMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ApplicationMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._AppDeployment instanceof AppDeploymentMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getAppDeployment() != null) {
            this._getReferenceManager().unregisterBean((AppDeploymentMBeanImpl)oldDelegate.getAppDeployment());
         }

         if (delegate != null && delegate.getAppDeployment() != null) {
            this._getReferenceManager().registerBean((AppDeploymentMBeanImpl)delegate.getAppDeployment(), false);
         }

         ((AppDeploymentMBeanImpl)this._AppDeployment)._setDelegateBean((AppDeploymentMBeanImpl)((AppDeploymentMBeanImpl)(delegate == null ? null : delegate.getAppDeployment())));
      }

   }

   public ApplicationMBeanImpl() {
      try {
         this._customizer = new Application(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ApplicationMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new Application(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ApplicationMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new Application(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
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

   public String getPath() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getPath(), this) : this._customizer.getPath();
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isPathInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isPathSet() {
      return this._isSet(10);
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
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setPath(String param0) throws ManagementException, InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      String _oldVal = this.getPath();
      this._customizer.setPath(param0);
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public ComponentMBean[] getComponents() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().getComponents() : this._customizer.getComponents();
   }

   public String getNotes() {
      return !this._isSet(3) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(3) ? this._performMacroSubstitution(this._getDelegateBean().getNotes(), this) : this._customizer.getNotes();
   }

   public boolean isComponentsInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isComponentsSet() {
      return this._isSet(11);
   }

   public boolean isNotesInherited() {
      return !this._isSet(3) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(3);
   }

   public boolean isNotesSet() {
      return this._isSet(3);
   }

   public boolean addComponent(ComponentMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         ComponentMBean[] _new = (ComponentMBean[])((ComponentMBean[])this._getHelper()._extendArray(this.getComponents(), ComponentMBean.class, param0));

         try {
            this.setComponents(_new);
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

   public void setNotes(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(3);
      String _oldVal = this.getNotes();
      this._customizer.setNotes(param0);
      this._postSet(3, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
         if (source != null && !source._isSet(3)) {
            source._postSetFirePropertyChange(3, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean removeComponent(ComponentMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      ComponentMBean[] _old = this.getComponents();
      ComponentMBean[] _new = (ComponentMBean[])((ComponentMBean[])this._getHelper()._removeElement(_old, ComponentMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setComponents(_new);
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

   public void setComponents(ComponentMBean[] param0) {
      ComponentMBean[] param0 = param0 == null ? new ComponentMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._Components = (ComponentMBean[])param0;
   }

   public WebAppComponentMBean createWebAppComponent(String param0) {
      WebAppComponentMBeanImpl lookup = (WebAppComponentMBeanImpl)this.lookupWebAppComponent(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WebAppComponentMBeanImpl _val = new WebAppComponentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWebAppComponent(_val);
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

   public void destroyWebAppComponent(WebAppComponentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         WebAppComponentMBean[] _old = this.getWebAppComponents();
         WebAppComponentMBean[] _new = (WebAppComponentMBean[])((WebAppComponentMBean[])this._getHelper()._removeElement(_old, WebAppComponentMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  ApplicationMBeanImpl source = (ApplicationMBeanImpl)var6.next();
                  WebAppComponentMBeanImpl childImpl = (WebAppComponentMBeanImpl)_child;
                  WebAppComponentMBeanImpl lookup = (WebAppComponentMBeanImpl)source.lookupWebAppComponent(childImpl.getName());
                  if (lookup != null) {
                     source.destroyWebAppComponent(lookup);
                  }
               }

               this.setWebAppComponents(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public WebAppComponentMBean lookupWebAppComponent(String param0) {
      Object[] aary = (Object[])this.getWebAppComponents();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WebAppComponentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WebAppComponentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addWebAppComponent(WebAppComponentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         WebAppComponentMBean[] _new;
         if (this._isSet(12)) {
            _new = (WebAppComponentMBean[])((WebAppComponentMBean[])this._getHelper()._extendArray(this.getWebAppComponents(), WebAppComponentMBean.class, param0));
         } else {
            _new = new WebAppComponentMBean[]{param0};
         }

         try {
            this.setWebAppComponents(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WebAppComponentMBean[] getWebAppComponents() {
      WebAppComponentMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(12)) {
         delegateArray = this._getDelegateBean().getWebAppComponents();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._WebAppComponents.length; ++j) {
               if (delegateArray[i].getName().equals(this._WebAppComponents[j].getName())) {
                  ((WebAppComponentMBeanImpl)this._WebAppComponents[j])._setDelegateBean((WebAppComponentMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  WebAppComponentMBeanImpl mbean = new WebAppComponentMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 12);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((WebAppComponentMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(12)) {
                     this.setWebAppComponents((WebAppComponentMBean[])((WebAppComponentMBean[])this._getHelper()._extendArray(this._WebAppComponents, WebAppComponentMBean.class, mbean)));
                  } else {
                     this.setWebAppComponents(new WebAppComponentMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new WebAppComponentMBean[0];
      }

      if (this._WebAppComponents != null) {
         List removeList = new ArrayList();
         WebAppComponentMBean[] var18 = this._WebAppComponents;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            WebAppComponentMBean bn = var18[var5];
            WebAppComponentMBeanImpl bni = (WebAppComponentMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               WebAppComponentMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  WebAppComponentMBean delegateTo = var10[var12];
                  if (nameToSearch.equals(delegateTo.getName())) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  removeList.add(bn);
               }
            }
         }

         Iterator var19 = removeList.iterator();

         while(var19.hasNext()) {
            WebAppComponentMBean removeIt = (WebAppComponentMBean)var19.next();
            WebAppComponentMBeanImpl removeItImpl = (WebAppComponentMBeanImpl)removeIt;
            WebAppComponentMBean[] _new = (WebAppComponentMBean[])((WebAppComponentMBean[])this._getHelper()._removeElement(this._WebAppComponents, WebAppComponentMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setWebAppComponents(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._WebAppComponents;
   }

   public boolean isWebAppComponentsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(12)) {
         WebAppComponentMBean[] elements = this.getWebAppComponents();
         WebAppComponentMBean[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            if (o instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean adBean = (AbstractDescriptorBean)o;
               if (!adBean._isTransient() || !adBean._isSynthetic()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isWebAppComponentsSet() {
      return this._isSet(12);
   }

   public void removeWebAppComponent(WebAppComponentMBean param0) {
      this.destroyWebAppComponent(param0);
   }

   public void setWebAppComponents(WebAppComponentMBean[] param0) throws InvalidAttributeValueException {
      WebAppComponentMBean[] param0 = param0 == null ? new WebAppComponentMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._WebAppComponents, (Object[])param0, handler, new Comparator() {
            public int compare(WebAppComponentMBean o1, WebAppComponentMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            WebAppComponentMBean bean = (WebAppComponentMBean)var3.next();
            WebAppComponentMBeanImpl beanImpl = (WebAppComponentMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(12);
      WebAppComponentMBean[] _oldVal = this._WebAppComponents;
      this._WebAppComponents = (WebAppComponentMBean[])param0;
      this._postSet(12, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var11.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isEar() {
      return this._customizer.isEar();
   }

   public boolean isEarInherited() {
      return false;
   }

   public boolean isEarSet() {
      return this._isSet(13);
   }

   public void setEar(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      boolean _oldVal = this._Ear;
      this._Ear = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public String getFullPath() {
      return this._customizer.getFullPath();
   }

   public boolean isFullPathInherited() {
      return false;
   }

   public boolean isFullPathSet() {
      return this._isSet(14);
   }

   public void setFullPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._FullPath = param0;
   }

   public boolean isInternalApp() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().isInternalApp() : this._customizer.isInternalApp();
   }

   public boolean isInternalAppInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isInternalAppSet() {
      return this._isSet(15);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setInternalApp(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._customizer.setInternalApp(param0);
   }

   public String getStagingPath() {
      return this._StagingPath;
   }

   public boolean isStagingPathInherited() {
      return false;
   }

   public boolean isStagingPathSet() {
      return this._isSet(16);
   }

   public void setStagingPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._StagingPath = param0;
   }

   public String[] getStagedTargets() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getStagedTargets() : this._StagedTargets;
   }

   public boolean isStagedTargetsInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isStagedTargetsSet() {
      return this._isSet(17);
   }

   public void addStagedTarget(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(17)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getStagedTargets(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setStagedTargets(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
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

   public void removeStagedTarget(String param0) {
      String[] _old = this.getStagedTargets();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setStagedTargets(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setStagedTargets(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      String[] _oldVal = this._StagedTargets;
      this._StagedTargets = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
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

   public void unstageTargets(String[] param0) {
      this._customizer.unstageTargets(param0);
   }

   public String getStagingMode() {
      if (!this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18)) {
         return this._performMacroSubstitution(this._getDelegateBean().getStagingMode(), this);
      } else {
         if (!this._isSet(18)) {
            try {
               return DeployHelper.determineDefaultStagingMode(this.getParent().getName()) == "nostage" ? "nostage" : (DeployHelper.determineDefaultStagingMode(this.getParent().getName()) == ServerMBean.DEFAULT_STAGE ? ApplicationMBean.DEFAULT_STAGE : "stage");
            } catch (NullPointerException var2) {
            }
         }

         return this._customizer.getStagingMode();
      }
   }

   public boolean isStagingModeInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isStagingModeSet() {
      return this._isSet(18);
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
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
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

   public void setStagingMode(String param0) throws ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{ApplicationMBean.DEFAULT_STAGE, "nostage", "stage", "external_stage"};
      param0 = LegalChecks.checkInEnum("StagingMode", param0, _set);
      boolean wasSet = this._isSet(18);
      String _oldVal = this.getStagingMode();
      this._customizer.setStagingMode(param0);
      this._postSet(18, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var5.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
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

   public boolean stagingEnabled(String param0) {
      return this._customizer.stagingEnabled(param0);
   }

   public boolean staged(String param0) {
      return this._customizer.staged(param0);
   }

   public boolean useStagingDirectory(String param0) {
      return this._customizer.useStagingDirectory(param0);
   }

   public void sendAppLevelNotification(String param0, String param1, String param2) {
      this._customizer.sendAppLevelNotification(param0, param1, param2);
   }

   public void sendModuleNotification(String param0, String param1, String param2, String param3, String param4, String param5, long param6) {
      this._customizer.sendModuleNotification(param0, param1, param2, param3, param4, param5, param6);
   }

   public boolean isTwoPhase() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().isTwoPhase() : this._customizer.isTwoPhase();
   }

   public boolean isTwoPhaseInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isTwoPhaseSet() {
      return this._isSet(19);
   }

   public void setTwoPhase(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      boolean _oldVal = this._TwoPhase;
      this._TwoPhase = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLoadOrder() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getLoadOrder() : this._LoadOrder;
   }

   public boolean isLoadOrderInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isLoadOrderSet() {
      return this._isSet(20);
   }

   public void setLoadOrder(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      int _oldVal = this._LoadOrder;
      this._LoadOrder = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDeploymentType() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._performMacroSubstitution(this._getDelegateBean().getDeploymentType(), this) : this._customizer.getDeploymentType();
   }

   public boolean isDeploymentTypeInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isDeploymentTypeSet() {
      return this._isSet(21);
   }

   public void setDeploymentType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"EAR", "EXPLODED EAR", "COMPONENT", "EXPLODED COMPONENT", "UNKNOWN"};
      param0 = LegalChecks.checkInEnum("DeploymentType", param0, _set);
      this._customizer.setDeploymentType(param0);
   }

   public int getDeploymentTimeout() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._getDelegateBean().getDeploymentTimeout() : this._DeploymentTimeout;
   }

   public boolean isDeploymentTimeoutInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isDeploymentTimeoutSet() {
      return this._isSet(22);
   }

   public void setDeploymentTimeout(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      int _oldVal = this._DeploymentTimeout;
      this._DeploymentTimeout = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAltDescriptorPath() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._performMacroSubstitution(this._getDelegateBean().getAltDescriptorPath(), this) : this._AltDescriptorPath;
   }

   public boolean isAltDescriptorPathInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isAltDescriptorPathSet() {
      return this._isSet(23);
   }

   public void setAltDescriptorPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(23);
      String _oldVal = this._AltDescriptorPath;
      this._AltDescriptorPath = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAltWLSDescriptorPath() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._performMacroSubstitution(this._getDelegateBean().getAltWLSDescriptorPath(), this) : this._AltWLSDescriptorPath;
   }

   public boolean isAltWLSDescriptorPathInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isAltWLSDescriptorPathSet() {
      return this._isSet(24);
   }

   public void setAltWLSDescriptorPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(24);
      String _oldVal = this._AltWLSDescriptorPath;
      this._AltWLSDescriptorPath = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public void refreshDDsIfNeeded(String[] param0, String[] param1) {
      this._customizer.refreshDDsIfNeeded(param0, param1);
   }

   public boolean isDeployed() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().isDeployed() : this._customizer.isDeployed();
   }

   public boolean isDeployedInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isDeployedSet() {
      return this._isSet(25);
   }

   public void setDeployed(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(25);
      boolean _oldVal = this.isDeployed();
      this._customizer.setDeployed(param0);
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public EJBComponentMBean createEJBComponent(String param0) {
      EJBComponentMBeanImpl lookup = (EJBComponentMBeanImpl)this.lookupEJBComponent(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         EJBComponentMBeanImpl _val = new EJBComponentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addEJBComponent(_val);
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

   public void destroyEJBComponent(EJBComponentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 26);
         EJBComponentMBean[] _old = this.getEJBComponents();
         EJBComponentMBean[] _new = (EJBComponentMBean[])((EJBComponentMBean[])this._getHelper()._removeElement(_old, EJBComponentMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  ApplicationMBeanImpl source = (ApplicationMBeanImpl)var6.next();
                  EJBComponentMBeanImpl childImpl = (EJBComponentMBeanImpl)_child;
                  EJBComponentMBeanImpl lookup = (EJBComponentMBeanImpl)source.lookupEJBComponent(childImpl.getName());
                  if (lookup != null) {
                     source.destroyEJBComponent(lookup);
                  }
               }

               this.setEJBComponents(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public EJBComponentMBean lookupEJBComponent(String param0) {
      Object[] aary = (Object[])this.getEJBComponents();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      EJBComponentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (EJBComponentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addEJBComponent(EJBComponentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 26)) {
         EJBComponentMBean[] _new;
         if (this._isSet(26)) {
            _new = (EJBComponentMBean[])((EJBComponentMBean[])this._getHelper()._extendArray(this.getEJBComponents(), EJBComponentMBean.class, param0));
         } else {
            _new = new EJBComponentMBean[]{param0};
         }

         try {
            this.setEJBComponents(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EJBComponentMBean[] getEJBComponents() {
      EJBComponentMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(26)) {
         delegateArray = this._getDelegateBean().getEJBComponents();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._EJBComponents.length; ++j) {
               if (delegateArray[i].getName().equals(this._EJBComponents[j].getName())) {
                  ((EJBComponentMBeanImpl)this._EJBComponents[j])._setDelegateBean((EJBComponentMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  EJBComponentMBeanImpl mbean = new EJBComponentMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 26);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((EJBComponentMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(26)) {
                     this.setEJBComponents((EJBComponentMBean[])((EJBComponentMBean[])this._getHelper()._extendArray(this._EJBComponents, EJBComponentMBean.class, mbean)));
                  } else {
                     this.setEJBComponents(new EJBComponentMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new EJBComponentMBean[0];
      }

      if (this._EJBComponents != null) {
         List removeList = new ArrayList();
         EJBComponentMBean[] var18 = this._EJBComponents;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            EJBComponentMBean bn = var18[var5];
            EJBComponentMBeanImpl bni = (EJBComponentMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               EJBComponentMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  EJBComponentMBean delegateTo = var10[var12];
                  if (nameToSearch.equals(delegateTo.getName())) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  removeList.add(bn);
               }
            }
         }

         Iterator var19 = removeList.iterator();

         while(var19.hasNext()) {
            EJBComponentMBean removeIt = (EJBComponentMBean)var19.next();
            EJBComponentMBeanImpl removeItImpl = (EJBComponentMBeanImpl)removeIt;
            EJBComponentMBean[] _new = (EJBComponentMBean[])((EJBComponentMBean[])this._getHelper()._removeElement(this._EJBComponents, EJBComponentMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setEJBComponents(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._EJBComponents;
   }

   public boolean isEJBComponentsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(26)) {
         EJBComponentMBean[] elements = this.getEJBComponents();
         EJBComponentMBean[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            if (o instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean adBean = (AbstractDescriptorBean)o;
               if (!adBean._isTransient() || !adBean._isSynthetic()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isEJBComponentsSet() {
      return this._isSet(26);
   }

   public void removeEJBComponent(EJBComponentMBean param0) {
      this.destroyEJBComponent(param0);
   }

   public void setEJBComponents(EJBComponentMBean[] param0) throws InvalidAttributeValueException {
      EJBComponentMBean[] param0 = param0 == null ? new EJBComponentMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._EJBComponents, (Object[])param0, handler, new Comparator() {
            public int compare(EJBComponentMBean o1, EJBComponentMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            EJBComponentMBean bean = (EJBComponentMBean)var3.next();
            EJBComponentMBeanImpl beanImpl = (EJBComponentMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 26)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(26);
      EJBComponentMBean[] _oldVal = this._EJBComponents;
      this._EJBComponents = (EJBComponentMBean[])param0;
      this._postSet(26, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var11.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public ConnectorComponentMBean createConnectorComponent(String param0) {
      ConnectorComponentMBeanImpl lookup = (ConnectorComponentMBeanImpl)this.lookupConnectorComponent(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ConnectorComponentMBeanImpl _val = new ConnectorComponentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addConnectorComponent(_val);
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

   public void destroyConnectorComponent(ConnectorComponentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 27);
         ConnectorComponentMBean[] _old = this.getConnectorComponents();
         ConnectorComponentMBean[] _new = (ConnectorComponentMBean[])((ConnectorComponentMBean[])this._getHelper()._removeElement(_old, ConnectorComponentMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  ApplicationMBeanImpl source = (ApplicationMBeanImpl)var6.next();
                  ConnectorComponentMBeanImpl childImpl = (ConnectorComponentMBeanImpl)_child;
                  ConnectorComponentMBeanImpl lookup = (ConnectorComponentMBeanImpl)source.lookupConnectorComponent(childImpl.getName());
                  if (lookup != null) {
                     source.destroyConnectorComponent(lookup);
                  }
               }

               this.setConnectorComponents(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public ConnectorComponentMBean lookupConnectorComponent(String param0) {
      Object[] aary = (Object[])this.getConnectorComponents();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ConnectorComponentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ConnectorComponentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addConnectorComponent(ConnectorComponentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 27)) {
         ConnectorComponentMBean[] _new;
         if (this._isSet(27)) {
            _new = (ConnectorComponentMBean[])((ConnectorComponentMBean[])this._getHelper()._extendArray(this.getConnectorComponents(), ConnectorComponentMBean.class, param0));
         } else {
            _new = new ConnectorComponentMBean[]{param0};
         }

         try {
            this.setConnectorComponents(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConnectorComponentMBean[] getConnectorComponents() {
      ConnectorComponentMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(27)) {
         delegateArray = this._getDelegateBean().getConnectorComponents();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._ConnectorComponents.length; ++j) {
               if (delegateArray[i].getName().equals(this._ConnectorComponents[j].getName())) {
                  ((ConnectorComponentMBeanImpl)this._ConnectorComponents[j])._setDelegateBean((ConnectorComponentMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  ConnectorComponentMBeanImpl mbean = new ConnectorComponentMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 27);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((ConnectorComponentMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(27)) {
                     this.setConnectorComponents((ConnectorComponentMBean[])((ConnectorComponentMBean[])this._getHelper()._extendArray(this._ConnectorComponents, ConnectorComponentMBean.class, mbean)));
                  } else {
                     this.setConnectorComponents(new ConnectorComponentMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new ConnectorComponentMBean[0];
      }

      if (this._ConnectorComponents != null) {
         List removeList = new ArrayList();
         ConnectorComponentMBean[] var18 = this._ConnectorComponents;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            ConnectorComponentMBean bn = var18[var5];
            ConnectorComponentMBeanImpl bni = (ConnectorComponentMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               ConnectorComponentMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  ConnectorComponentMBean delegateTo = var10[var12];
                  if (nameToSearch.equals(delegateTo.getName())) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  removeList.add(bn);
               }
            }
         }

         Iterator var19 = removeList.iterator();

         while(var19.hasNext()) {
            ConnectorComponentMBean removeIt = (ConnectorComponentMBean)var19.next();
            ConnectorComponentMBeanImpl removeItImpl = (ConnectorComponentMBeanImpl)removeIt;
            ConnectorComponentMBean[] _new = (ConnectorComponentMBean[])((ConnectorComponentMBean[])this._getHelper()._removeElement(this._ConnectorComponents, ConnectorComponentMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setConnectorComponents(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._ConnectorComponents;
   }

   public boolean isConnectorComponentsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(27)) {
         ConnectorComponentMBean[] elements = this.getConnectorComponents();
         ConnectorComponentMBean[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            if (o instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean adBean = (AbstractDescriptorBean)o;
               if (!adBean._isTransient() || !adBean._isSynthetic()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isConnectorComponentsSet() {
      return this._isSet(27);
   }

   public void removeConnectorComponent(ConnectorComponentMBean param0) {
      this.destroyConnectorComponent(param0);
   }

   public void setConnectorComponents(ConnectorComponentMBean[] param0) throws InvalidAttributeValueException {
      ConnectorComponentMBean[] param0 = param0 == null ? new ConnectorComponentMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._ConnectorComponents, (Object[])param0, handler, new Comparator() {
            public int compare(ConnectorComponentMBean o1, ConnectorComponentMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            ConnectorComponentMBean bean = (ConnectorComponentMBean)var3.next();
            ConnectorComponentMBeanImpl beanImpl = (ConnectorComponentMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 27)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(27);
      ConnectorComponentMBean[] _oldVal = this._ConnectorComponents;
      this._ConnectorComponents = (ConnectorComponentMBean[])param0;
      this._postSet(27, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var11.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public WebServiceComponentMBean createWebServiceComponent(String param0) {
      WebServiceComponentMBeanImpl lookup = (WebServiceComponentMBeanImpl)this.lookupWebServiceComponent(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WebServiceComponentMBeanImpl _val = new WebServiceComponentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWebServiceComponent(_val);
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

   public void destroyWebServiceComponent(WebServiceComponentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 28);
         WebServiceComponentMBean[] _old = this.getWebServiceComponents();
         WebServiceComponentMBean[] _new = (WebServiceComponentMBean[])((WebServiceComponentMBean[])this._getHelper()._removeElement(_old, WebServiceComponentMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  ApplicationMBeanImpl source = (ApplicationMBeanImpl)var6.next();
                  WebServiceComponentMBeanImpl childImpl = (WebServiceComponentMBeanImpl)_child;
                  WebServiceComponentMBeanImpl lookup = (WebServiceComponentMBeanImpl)source.lookupWebServiceComponent(childImpl.getName());
                  if (lookup != null) {
                     source.destroyWebServiceComponent(lookup);
                  }
               }

               this.setWebServiceComponents(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public ComponentMBean createDummyComponent(String param0) {
      ComponentMBeanImpl _val = new ComponentMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addComponent(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public WebServiceComponentMBean lookupWebServiceComponent(String param0) {
      Object[] aary = (Object[])this.getWebServiceComponents();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WebServiceComponentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WebServiceComponentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addWebServiceComponent(WebServiceComponentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 28)) {
         WebServiceComponentMBean[] _new;
         if (this._isSet(28)) {
            _new = (WebServiceComponentMBean[])((WebServiceComponentMBean[])this._getHelper()._extendArray(this.getWebServiceComponents(), WebServiceComponentMBean.class, param0));
         } else {
            _new = new WebServiceComponentMBean[]{param0};
         }

         try {
            this.setWebServiceComponents(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WebServiceComponentMBean[] getWebServiceComponents() {
      WebServiceComponentMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(28)) {
         delegateArray = this._getDelegateBean().getWebServiceComponents();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._WebServiceComponents.length; ++j) {
               if (delegateArray[i].getName().equals(this._WebServiceComponents[j].getName())) {
                  ((WebServiceComponentMBeanImpl)this._WebServiceComponents[j])._setDelegateBean((WebServiceComponentMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  WebServiceComponentMBeanImpl mbean = new WebServiceComponentMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 28);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((WebServiceComponentMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(28)) {
                     this.setWebServiceComponents((WebServiceComponentMBean[])((WebServiceComponentMBean[])this._getHelper()._extendArray(this._WebServiceComponents, WebServiceComponentMBean.class, mbean)));
                  } else {
                     this.setWebServiceComponents(new WebServiceComponentMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new WebServiceComponentMBean[0];
      }

      if (this._WebServiceComponents != null) {
         List removeList = new ArrayList();
         WebServiceComponentMBean[] var18 = this._WebServiceComponents;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            WebServiceComponentMBean bn = var18[var5];
            WebServiceComponentMBeanImpl bni = (WebServiceComponentMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               WebServiceComponentMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  WebServiceComponentMBean delegateTo = var10[var12];
                  if (nameToSearch.equals(delegateTo.getName())) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  removeList.add(bn);
               }
            }
         }

         Iterator var19 = removeList.iterator();

         while(var19.hasNext()) {
            WebServiceComponentMBean removeIt = (WebServiceComponentMBean)var19.next();
            WebServiceComponentMBeanImpl removeItImpl = (WebServiceComponentMBeanImpl)removeIt;
            WebServiceComponentMBean[] _new = (WebServiceComponentMBean[])((WebServiceComponentMBean[])this._getHelper()._removeElement(this._WebServiceComponents, WebServiceComponentMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setWebServiceComponents(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._WebServiceComponents;
   }

   public boolean isWebServiceComponentsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(28)) {
         WebServiceComponentMBean[] elements = this.getWebServiceComponents();
         WebServiceComponentMBean[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            if (o instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean adBean = (AbstractDescriptorBean)o;
               if (!adBean._isTransient() || !adBean._isSynthetic()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isWebServiceComponentsSet() {
      return this._isSet(28);
   }

   public void removeWebServiceComponent(WebServiceComponentMBean param0) {
      this.destroyWebServiceComponent(param0);
   }

   public void setWebServiceComponents(WebServiceComponentMBean[] param0) throws InvalidAttributeValueException {
      WebServiceComponentMBean[] param0 = param0 == null ? new WebServiceComponentMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._WebServiceComponents, (Object[])param0, handler, new Comparator() {
            public int compare(WebServiceComponentMBean o1, WebServiceComponentMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            WebServiceComponentMBean bean = (WebServiceComponentMBean)var3.next();
            WebServiceComponentMBeanImpl beanImpl = (WebServiceComponentMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 28)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(28);
      WebServiceComponentMBean[] _oldVal = this._WebServiceComponents;
      this._WebServiceComponents = (WebServiceComponentMBean[])param0;
      this._postSet(28, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var11.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public JDBCPoolComponentMBean createJDBCPoolComponent(String param0) {
      JDBCPoolComponentMBeanImpl lookup = (JDBCPoolComponentMBeanImpl)this.lookupJDBCPoolComponent(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JDBCPoolComponentMBeanImpl _val = new JDBCPoolComponentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJDBCPoolComponent(_val);
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

   public void destroyJDBCPoolComponent(JDBCPoolComponentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 29);
         JDBCPoolComponentMBean[] _old = this.getJDBCPoolComponents();
         JDBCPoolComponentMBean[] _new = (JDBCPoolComponentMBean[])((JDBCPoolComponentMBean[])this._getHelper()._removeElement(_old, JDBCPoolComponentMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  ApplicationMBeanImpl source = (ApplicationMBeanImpl)var6.next();
                  JDBCPoolComponentMBeanImpl childImpl = (JDBCPoolComponentMBeanImpl)_child;
                  JDBCPoolComponentMBeanImpl lookup = (JDBCPoolComponentMBeanImpl)source.lookupJDBCPoolComponent(childImpl.getName());
                  if (lookup != null) {
                     source.destroyJDBCPoolComponent(lookup);
                  }
               }

               this.setJDBCPoolComponents(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public JDBCPoolComponentMBean lookupJDBCPoolComponent(String param0) {
      Object[] aary = (Object[])this.getJDBCPoolComponents();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JDBCPoolComponentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JDBCPoolComponentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJDBCPoolComponent(JDBCPoolComponentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 29)) {
         JDBCPoolComponentMBean[] _new;
         if (this._isSet(29)) {
            _new = (JDBCPoolComponentMBean[])((JDBCPoolComponentMBean[])this._getHelper()._extendArray(this.getJDBCPoolComponents(), JDBCPoolComponentMBean.class, param0));
         } else {
            _new = new JDBCPoolComponentMBean[]{param0};
         }

         try {
            this.setJDBCPoolComponents(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JDBCPoolComponentMBean[] getJDBCPoolComponents() {
      JDBCPoolComponentMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(29)) {
         delegateArray = this._getDelegateBean().getJDBCPoolComponents();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._JDBCPoolComponents.length; ++j) {
               if (delegateArray[i].getName().equals(this._JDBCPoolComponents[j].getName())) {
                  ((JDBCPoolComponentMBeanImpl)this._JDBCPoolComponents[j])._setDelegateBean((JDBCPoolComponentMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  JDBCPoolComponentMBeanImpl mbean = new JDBCPoolComponentMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 29);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((JDBCPoolComponentMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(29)) {
                     this.setJDBCPoolComponents((JDBCPoolComponentMBean[])((JDBCPoolComponentMBean[])this._getHelper()._extendArray(this._JDBCPoolComponents, JDBCPoolComponentMBean.class, mbean)));
                  } else {
                     this.setJDBCPoolComponents(new JDBCPoolComponentMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new JDBCPoolComponentMBean[0];
      }

      if (this._JDBCPoolComponents != null) {
         List removeList = new ArrayList();
         JDBCPoolComponentMBean[] var18 = this._JDBCPoolComponents;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            JDBCPoolComponentMBean bn = var18[var5];
            JDBCPoolComponentMBeanImpl bni = (JDBCPoolComponentMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               JDBCPoolComponentMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  JDBCPoolComponentMBean delegateTo = var10[var12];
                  if (nameToSearch.equals(delegateTo.getName())) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  removeList.add(bn);
               }
            }
         }

         Iterator var19 = removeList.iterator();

         while(var19.hasNext()) {
            JDBCPoolComponentMBean removeIt = (JDBCPoolComponentMBean)var19.next();
            JDBCPoolComponentMBeanImpl removeItImpl = (JDBCPoolComponentMBeanImpl)removeIt;
            JDBCPoolComponentMBean[] _new = (JDBCPoolComponentMBean[])((JDBCPoolComponentMBean[])this._getHelper()._removeElement(this._JDBCPoolComponents, JDBCPoolComponentMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setJDBCPoolComponents(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._JDBCPoolComponents;
   }

   public boolean isJDBCPoolComponentsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(29)) {
         JDBCPoolComponentMBean[] elements = this.getJDBCPoolComponents();
         JDBCPoolComponentMBean[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            if (o instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean adBean = (AbstractDescriptorBean)o;
               if (!adBean._isTransient() || !adBean._isSynthetic()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isJDBCPoolComponentsSet() {
      return this._isSet(29);
   }

   public void removeJDBCPoolComponent(JDBCPoolComponentMBean param0) {
      this.destroyJDBCPoolComponent(param0);
   }

   public void setJDBCPoolComponents(JDBCPoolComponentMBean[] param0) throws InvalidAttributeValueException {
      JDBCPoolComponentMBean[] param0 = param0 == null ? new JDBCPoolComponentMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._JDBCPoolComponents, (Object[])param0, handler, new Comparator() {
            public int compare(JDBCPoolComponentMBean o1, JDBCPoolComponentMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            JDBCPoolComponentMBean bean = (JDBCPoolComponentMBean)var3.next();
            JDBCPoolComponentMBeanImpl beanImpl = (JDBCPoolComponentMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 29)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(29);
      JDBCPoolComponentMBean[] _oldVal = this._JDBCPoolComponents;
      this._JDBCPoolComponents = (JDBCPoolComponentMBean[])param0;
      this._postSet(29, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ApplicationMBeanImpl source = (ApplicationMBeanImpl)var11.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public int getInternalType() {
      return this._customizer.getInternalType();
   }

   public boolean isInternalTypeInherited() {
      return false;
   }

   public boolean isInternalTypeSet() {
      return this._isSet(30);
   }

   public void setInternalType(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._InternalType = param0;
   }

   public void setAppDeployment(AppDeploymentMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._AppDeployment = param0;
   }

   public AppDeploymentMBean getAppDeployment() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().getAppDeployment() : this._customizer.getAppDeployment();
   }

   public boolean isAppDeploymentInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isAppDeploymentSet() {
      return this._isSet(31);
   }

   public AppDeploymentMBean returnDeployableUnit() {
      return this._customizer.returnDeployableUnit();
   }

   public void setDelegationEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._customizer.setDelegationEnabled(param0);
   }

   public boolean isDelegationEnabled() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().isDelegationEnabled() : this._customizer.isDelegationEnabled();
   }

   public boolean isDelegationEnabledInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isDelegationEnabledSet() {
      return this._isSet(32);
   }

   public void addHandler(Object param0) {
      this._customizer.addHandler(param0);
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
         idx = 23;
      }

      try {
         switch (idx) {
            case 23:
               this._AltDescriptorPath = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._AltWLSDescriptorPath = null;
               if (initOne) {
                  break;
               }
            case 31:
               this._AppDeployment = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._Components = new ComponentMBean[0];
               if (initOne) {
                  break;
               }
            case 27:
               this._ConnectorComponents = new ConnectorComponentMBean[0];
               if (initOne) {
                  break;
               }
            case 22:
               this._DeploymentTimeout = 3600000;
               if (initOne) {
                  break;
               }
            case 21:
               this._customizer.setDeploymentType("UNKNOWN");
               if (initOne) {
                  break;
               }
            case 26:
               this._EJBComponents = new EJBComponentMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._FullPath = null;
               if (initOne) {
                  break;
               }
            case 30:
               this._InternalType = 0;
               if (initOne) {
                  break;
               }
            case 29:
               this._JDBCPoolComponents = new JDBCPoolComponentMBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._LoadOrder = 100;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 3:
               this._customizer.setNotes((String)null);
               if (initOne) {
                  break;
               }
            case 10:
               this._customizer.setPath((String)null);
               if (initOne) {
                  break;
               }
            case 17:
               this._StagedTargets = new String[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._customizer.setStagingMode((String)null);
               if (initOne) {
                  break;
               }
            case 16:
               this._StagingPath = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 12:
               this._WebAppComponents = new WebAppComponentMBean[0];
               if (initOne) {
                  break;
               }
            case 28:
               this._WebServiceComponents = new WebServiceComponentMBean[0];
               if (initOne) {
                  break;
               }
            case 32:
               this._customizer.setDelegationEnabled(false);
               if (initOne) {
                  break;
               }
            case 25:
               this._customizer.setDeployed(false);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 13:
               this._Ear = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._customizer.setInternalApp(false);
               if (initOne) {
                  break;
               }
            case 19:
               this._TwoPhase = true;
               if (initOne) {
                  break;
               }
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
      return "Application";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AltDescriptorPath")) {
         oldVal = this._AltDescriptorPath;
         this._AltDescriptorPath = (String)v;
         this._postSet(23, oldVal, this._AltDescriptorPath);
      } else if (name.equals("AltWLSDescriptorPath")) {
         oldVal = this._AltWLSDescriptorPath;
         this._AltWLSDescriptorPath = (String)v;
         this._postSet(24, oldVal, this._AltWLSDescriptorPath);
      } else if (name.equals("AppDeployment")) {
         AppDeploymentMBean oldVal = this._AppDeployment;
         this._AppDeployment = (AppDeploymentMBean)v;
         this._postSet(31, oldVal, this._AppDeployment);
      } else if (name.equals("Components")) {
         ComponentMBean[] oldVal = this._Components;
         this._Components = (ComponentMBean[])((ComponentMBean[])v);
         this._postSet(11, oldVal, this._Components);
      } else if (name.equals("ConnectorComponents")) {
         ConnectorComponentMBean[] oldVal = this._ConnectorComponents;
         this._ConnectorComponents = (ConnectorComponentMBean[])((ConnectorComponentMBean[])v);
         this._postSet(27, oldVal, this._ConnectorComponents);
      } else {
         boolean oldVal;
         if (name.equals("DelegationEnabled")) {
            oldVal = this._DelegationEnabled;
            this._DelegationEnabled = (Boolean)v;
            this._postSet(32, oldVal, this._DelegationEnabled);
         } else if (name.equals("Deployed")) {
            oldVal = this._Deployed;
            this._Deployed = (Boolean)v;
            this._postSet(25, oldVal, this._Deployed);
         } else {
            int oldVal;
            if (name.equals("DeploymentTimeout")) {
               oldVal = this._DeploymentTimeout;
               this._DeploymentTimeout = (Integer)v;
               this._postSet(22, oldVal, this._DeploymentTimeout);
            } else if (name.equals("DeploymentType")) {
               oldVal = this._DeploymentType;
               this._DeploymentType = (String)v;
               this._postSet(21, oldVal, this._DeploymentType);
            } else if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("EJBComponents")) {
               EJBComponentMBean[] oldVal = this._EJBComponents;
               this._EJBComponents = (EJBComponentMBean[])((EJBComponentMBean[])v);
               this._postSet(26, oldVal, this._EJBComponents);
            } else if (name.equals("Ear")) {
               oldVal = this._Ear;
               this._Ear = (Boolean)v;
               this._postSet(13, oldVal, this._Ear);
            } else if (name.equals("FullPath")) {
               oldVal = this._FullPath;
               this._FullPath = (String)v;
               this._postSet(14, oldVal, this._FullPath);
            } else if (name.equals("InternalApp")) {
               oldVal = this._InternalApp;
               this._InternalApp = (Boolean)v;
               this._postSet(15, oldVal, this._InternalApp);
            } else if (name.equals("InternalType")) {
               oldVal = this._InternalType;
               this._InternalType = (Integer)v;
               this._postSet(30, oldVal, this._InternalType);
            } else if (name.equals("JDBCPoolComponents")) {
               JDBCPoolComponentMBean[] oldVal = this._JDBCPoolComponents;
               this._JDBCPoolComponents = (JDBCPoolComponentMBean[])((JDBCPoolComponentMBean[])v);
               this._postSet(29, oldVal, this._JDBCPoolComponents);
            } else if (name.equals("LoadOrder")) {
               oldVal = this._LoadOrder;
               this._LoadOrder = (Integer)v;
               this._postSet(20, oldVal, this._LoadOrder);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("Notes")) {
               oldVal = this._Notes;
               this._Notes = (String)v;
               this._postSet(3, oldVal, this._Notes);
            } else if (name.equals("Path")) {
               oldVal = this._Path;
               this._Path = (String)v;
               this._postSet(10, oldVal, this._Path);
            } else {
               String[] oldVal;
               if (name.equals("StagedTargets")) {
                  oldVal = this._StagedTargets;
                  this._StagedTargets = (String[])((String[])v);
                  this._postSet(17, oldVal, this._StagedTargets);
               } else if (name.equals("StagingMode")) {
                  oldVal = this._StagingMode;
                  this._StagingMode = (String)v;
                  this._postSet(18, oldVal, this._StagingMode);
               } else if (name.equals("StagingPath")) {
                  oldVal = this._StagingPath;
                  this._StagingPath = (String)v;
                  this._postSet(16, oldVal, this._StagingPath);
               } else if (name.equals("Tags")) {
                  oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("TwoPhase")) {
                  oldVal = this._TwoPhase;
                  this._TwoPhase = (Boolean)v;
                  this._postSet(19, oldVal, this._TwoPhase);
               } else if (name.equals("WebAppComponents")) {
                  WebAppComponentMBean[] oldVal = this._WebAppComponents;
                  this._WebAppComponents = (WebAppComponentMBean[])((WebAppComponentMBean[])v);
                  this._postSet(12, oldVal, this._WebAppComponents);
               } else if (name.equals("WebServiceComponents")) {
                  WebServiceComponentMBean[] oldVal = this._WebServiceComponents;
                  this._WebServiceComponents = (WebServiceComponentMBean[])((WebServiceComponentMBean[])v);
                  this._postSet(28, oldVal, this._WebServiceComponents);
               } else if (name.equals("customizer")) {
                  Application oldVal = this._customizer;
                  this._customizer = (Application)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AltDescriptorPath")) {
         return this._AltDescriptorPath;
      } else if (name.equals("AltWLSDescriptorPath")) {
         return this._AltWLSDescriptorPath;
      } else if (name.equals("AppDeployment")) {
         return this._AppDeployment;
      } else if (name.equals("Components")) {
         return this._Components;
      } else if (name.equals("ConnectorComponents")) {
         return this._ConnectorComponents;
      } else if (name.equals("DelegationEnabled")) {
         return new Boolean(this._DelegationEnabled);
      } else if (name.equals("Deployed")) {
         return new Boolean(this._Deployed);
      } else if (name.equals("DeploymentTimeout")) {
         return new Integer(this._DeploymentTimeout);
      } else if (name.equals("DeploymentType")) {
         return this._DeploymentType;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("EJBComponents")) {
         return this._EJBComponents;
      } else if (name.equals("Ear")) {
         return new Boolean(this._Ear);
      } else if (name.equals("FullPath")) {
         return this._FullPath;
      } else if (name.equals("InternalApp")) {
         return new Boolean(this._InternalApp);
      } else if (name.equals("InternalType")) {
         return new Integer(this._InternalType);
      } else if (name.equals("JDBCPoolComponents")) {
         return this._JDBCPoolComponents;
      } else if (name.equals("LoadOrder")) {
         return new Integer(this._LoadOrder);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Notes")) {
         return this._Notes;
      } else if (name.equals("Path")) {
         return this._Path;
      } else if (name.equals("StagedTargets")) {
         return this._StagedTargets;
      } else if (name.equals("StagingMode")) {
         return this._StagingMode;
      } else if (name.equals("StagingPath")) {
         return this._StagingPath;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("TwoPhase")) {
         return new Boolean(this._TwoPhase);
      } else if (name.equals("WebAppComponents")) {
         return this._WebAppComponents;
      } else if (name.equals("WebServiceComponents")) {
         return this._WebServiceComponents;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }

               if (s.equals("ear")) {
                  return 13;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }

               if (s.equals("path")) {
                  return 10;
               }
               break;
            case 5:
               if (s.equals("notes")) {
                  return 3;
               }
            case 6:
            case 7:
            case 11:
            case 16:
            case 20:
            default:
               break;
            case 8:
               if (s.equals("deployed")) {
                  return 25;
               }
               break;
            case 9:
               if (s.equals("component")) {
                  return 11;
               }

               if (s.equals("full-path")) {
                  return 14;
               }

               if (s.equals("two-phase")) {
                  return 19;
               }
               break;
            case 10:
               if (s.equals("load-order")) {
                  return 20;
               }
               break;
            case 12:
               if (s.equals("staging-mode")) {
                  return 18;
               }

               if (s.equals("staging-path")) {
                  return 16;
               }

               if (s.equals("internal-app")) {
                  return 15;
               }
               break;
            case 13:
               if (s.equals("ejb-component")) {
                  return 26;
               }

               if (s.equals("internal-type")) {
                  return 30;
               }

               if (s.equals("staged-target")) {
                  return 17;
               }
               break;
            case 14:
               if (s.equals("app-deployment")) {
                  return 31;
               }
               break;
            case 15:
               if (s.equals("deployment-type")) {
                  return 21;
               }
               break;
            case 17:
               if (s.equals("web-app-component")) {
                  return 12;
               }
               break;
            case 18:
               if (s.equals("deployment-timeout")) {
                  return 22;
               }

               if (s.equals("delegation-enabled")) {
                  return 32;
               }
               break;
            case 19:
               if (s.equals("alt-descriptor-path")) {
                  return 23;
               }

               if (s.equals("connector-component")) {
                  return 27;
               }

               if (s.equals("jdbc-pool-component")) {
                  return 29;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 21:
               if (s.equals("web-service-component")) {
                  return 28;
               }
               break;
            case 22:
               if (s.equals("altwls-descriptor-path")) {
                  return 24;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 12:
               return new WebAppComponentMBeanImpl.SchemaHelper2();
            case 26:
               return new EJBComponentMBeanImpl.SchemaHelper2();
            case 27:
               return new ConnectorComponentMBeanImpl.SchemaHelper2();
            case 28:
               return new WebServiceComponentMBeanImpl.SchemaHelper2();
            case 29:
               return new JDBCPoolComponentMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 3:
               return "notes";
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
               return "path";
            case 11:
               return "component";
            case 12:
               return "web-app-component";
            case 13:
               return "ear";
            case 14:
               return "full-path";
            case 15:
               return "internal-app";
            case 16:
               return "staging-path";
            case 17:
               return "staged-target";
            case 18:
               return "staging-mode";
            case 19:
               return "two-phase";
            case 20:
               return "load-order";
            case 21:
               return "deployment-type";
            case 22:
               return "deployment-timeout";
            case 23:
               return "alt-descriptor-path";
            case 24:
               return "altwls-descriptor-path";
            case 25:
               return "deployed";
            case 26:
               return "ejb-component";
            case 27:
               return "connector-component";
            case 28:
               return "web-service-component";
            case 29:
               return "jdbc-pool-component";
            case 30:
               return "internal-type";
            case 31:
               return "app-deployment";
            case 32:
               return "delegation-enabled";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            default:
               return super.isArray(propIndex);
            case 11:
               return true;
            case 12:
               return true;
            case 17:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 28:
               return true;
            case 29:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 12:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 28:
               return true;
            case 29:
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
      private ApplicationMBeanImpl bean;

      protected Helper(ApplicationMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
               return "Notes";
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
               return "Path";
            case 11:
               return "Components";
            case 12:
               return "WebAppComponents";
            case 13:
               return "Ear";
            case 14:
               return "FullPath";
            case 15:
               return "InternalApp";
            case 16:
               return "StagingPath";
            case 17:
               return "StagedTargets";
            case 18:
               return "StagingMode";
            case 19:
               return "TwoPhase";
            case 20:
               return "LoadOrder";
            case 21:
               return "DeploymentType";
            case 22:
               return "DeploymentTimeout";
            case 23:
               return "AltDescriptorPath";
            case 24:
               return "AltWLSDescriptorPath";
            case 25:
               return "Deployed";
            case 26:
               return "EJBComponents";
            case 27:
               return "ConnectorComponents";
            case 28:
               return "WebServiceComponents";
            case 29:
               return "JDBCPoolComponents";
            case 30:
               return "InternalType";
            case 31:
               return "AppDeployment";
            case 32:
               return "DelegationEnabled";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AltDescriptorPath")) {
            return 23;
         } else if (propName.equals("AltWLSDescriptorPath")) {
            return 24;
         } else if (propName.equals("AppDeployment")) {
            return 31;
         } else if (propName.equals("Components")) {
            return 11;
         } else if (propName.equals("ConnectorComponents")) {
            return 27;
         } else if (propName.equals("DeploymentTimeout")) {
            return 22;
         } else if (propName.equals("DeploymentType")) {
            return 21;
         } else if (propName.equals("EJBComponents")) {
            return 26;
         } else if (propName.equals("FullPath")) {
            return 14;
         } else if (propName.equals("InternalType")) {
            return 30;
         } else if (propName.equals("JDBCPoolComponents")) {
            return 29;
         } else if (propName.equals("LoadOrder")) {
            return 20;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Notes")) {
            return 3;
         } else if (propName.equals("Path")) {
            return 10;
         } else if (propName.equals("StagedTargets")) {
            return 17;
         } else if (propName.equals("StagingMode")) {
            return 18;
         } else if (propName.equals("StagingPath")) {
            return 16;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("WebAppComponents")) {
            return 12;
         } else if (propName.equals("WebServiceComponents")) {
            return 28;
         } else if (propName.equals("DelegationEnabled")) {
            return 32;
         } else if (propName.equals("Deployed")) {
            return 25;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("Ear")) {
            return 13;
         } else if (propName.equals("InternalApp")) {
            return 15;
         } else {
            return propName.equals("TwoPhase") ? 19 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConnectorComponents()));
         iterators.add(new ArrayIterator(this.bean.getEJBComponents()));
         iterators.add(new ArrayIterator(this.bean.getJDBCPoolComponents()));
         iterators.add(new ArrayIterator(this.bean.getWebAppComponents()));
         iterators.add(new ArrayIterator(this.bean.getWebServiceComponents()));
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
            if (this.bean.isAltDescriptorPathSet()) {
               buf.append("AltDescriptorPath");
               buf.append(String.valueOf(this.bean.getAltDescriptorPath()));
            }

            if (this.bean.isAltWLSDescriptorPathSet()) {
               buf.append("AltWLSDescriptorPath");
               buf.append(String.valueOf(this.bean.getAltWLSDescriptorPath()));
            }

            if (this.bean.isAppDeploymentSet()) {
               buf.append("AppDeployment");
               buf.append(String.valueOf(this.bean.getAppDeployment()));
            }

            if (this.bean.isComponentsSet()) {
               buf.append("Components");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getComponents())));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getConnectorComponents().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConnectorComponents()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDeploymentTimeoutSet()) {
               buf.append("DeploymentTimeout");
               buf.append(String.valueOf(this.bean.getDeploymentTimeout()));
            }

            if (this.bean.isDeploymentTypeSet()) {
               buf.append("DeploymentType");
               buf.append(String.valueOf(this.bean.getDeploymentType()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEJBComponents().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEJBComponents()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isFullPathSet()) {
               buf.append("FullPath");
               buf.append(String.valueOf(this.bean.getFullPath()));
            }

            if (this.bean.isInternalTypeSet()) {
               buf.append("InternalType");
               buf.append(String.valueOf(this.bean.getInternalType()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJDBCPoolComponents().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJDBCPoolComponents()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isLoadOrderSet()) {
               buf.append("LoadOrder");
               buf.append(String.valueOf(this.bean.getLoadOrder()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNotesSet()) {
               buf.append("Notes");
               buf.append(String.valueOf(this.bean.getNotes()));
            }

            if (this.bean.isPathSet()) {
               buf.append("Path");
               buf.append(String.valueOf(this.bean.getPath()));
            }

            if (this.bean.isStagedTargetsSet()) {
               buf.append("StagedTargets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getStagedTargets())));
            }

            if (this.bean.isStagingModeSet()) {
               buf.append("StagingMode");
               buf.append(String.valueOf(this.bean.getStagingMode()));
            }

            if (this.bean.isStagingPathSet()) {
               buf.append("StagingPath");
               buf.append(String.valueOf(this.bean.getStagingPath()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWebAppComponents().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWebAppComponents()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWebServiceComponents().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWebServiceComponents()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDelegationEnabledSet()) {
               buf.append("DelegationEnabled");
               buf.append(String.valueOf(this.bean.isDelegationEnabled()));
            }

            if (this.bean.isDeployedSet()) {
               buf.append("Deployed");
               buf.append(String.valueOf(this.bean.isDeployed()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isEarSet()) {
               buf.append("Ear");
               buf.append(String.valueOf(this.bean.isEar()));
            }

            if (this.bean.isInternalAppSet()) {
               buf.append("InternalApp");
               buf.append(String.valueOf(this.bean.isInternalApp()));
            }

            if (this.bean.isTwoPhaseSet()) {
               buf.append("TwoPhase");
               buf.append(String.valueOf(this.bean.isTwoPhase()));
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
            ApplicationMBeanImpl otherTyped = (ApplicationMBeanImpl)other;
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("AltDescriptorPath", this.bean.getAltDescriptorPath(), otherTyped.getAltDescriptorPath(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("AltWLSDescriptorPath", this.bean.getAltWLSDescriptorPath(), otherTyped.getAltWLSDescriptorPath(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("ConnectorComponents", this.bean.getConnectorComponents(), otherTyped.getConnectorComponents(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("DeploymentTimeout", this.bean.getDeploymentTimeout(), otherTyped.getDeploymentTimeout(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("EJBComponents", this.bean.getEJBComponents(), otherTyped.getEJBComponents(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JDBCPoolComponents", this.bean.getJDBCPoolComponents(), otherTyped.getJDBCPoolComponents(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LoadOrder", this.bean.getLoadOrder(), otherTyped.getLoadOrder(), false);
            }

            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Notes", this.bean.getNotes(), otherTyped.getNotes(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("Path", this.bean.getPath(), otherTyped.getPath(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StagedTargets", this.bean.getStagedTargets(), otherTyped.getStagedTargets(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StagingMode", this.bean.getStagingMode(), otherTyped.getStagingMode(), true);
            }

            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("WebAppComponents", this.bean.getWebAppComponents(), otherTyped.getWebAppComponents(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("WebServiceComponents", this.bean.getWebServiceComponents(), otherTyped.getWebServiceComponents(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("Deployed", this.bean.isDeployed(), otherTyped.isDeployed(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("Ear", this.bean.isEar(), otherTyped.isEar(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("TwoPhase", this.bean.isTwoPhase(), otherTyped.isTwoPhase(), true);
            }

         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ApplicationMBeanImpl original = (ApplicationMBeanImpl)event.getSourceBean();
            ApplicationMBeanImpl proposed = (ApplicationMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AltDescriptorPath")) {
                  original.setAltDescriptorPath(proposed.getAltDescriptorPath());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("AltWLSDescriptorPath")) {
                  original.setAltWLSDescriptorPath(proposed.getAltWLSDescriptorPath());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (!prop.equals("AppDeployment") && !prop.equals("Components")) {
                  if (prop.equals("ConnectorComponents")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addConnectorComponent((ConnectorComponentMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeConnectorComponent((ConnectorComponentMBean)update.getRemovedObject());
                     }

                     if (original.getConnectorComponents() == null || original.getConnectorComponents().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 27);
                     }
                  } else if (prop.equals("DeploymentTimeout")) {
                     original.setDeploymentTimeout(proposed.getDeploymentTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (!prop.equals("DeploymentType")) {
                     if (prop.equals("EJBComponents")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addEJBComponent((EJBComponentMBean)update.getAddedObject());
                           }
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original.removeEJBComponent((EJBComponentMBean)update.getRemovedObject());
                        }

                        if (original.getEJBComponents() == null || original.getEJBComponents().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 26);
                        }
                     } else if (!prop.equals("FullPath") && !prop.equals("InternalType")) {
                        if (prop.equals("JDBCPoolComponents")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addJDBCPoolComponent((JDBCPoolComponentMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeJDBCPoolComponent((JDBCPoolComponentMBean)update.getRemovedObject());
                           }

                           if (original.getJDBCPoolComponents() == null || original.getJDBCPoolComponents().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 29);
                           }
                        } else if (prop.equals("LoadOrder")) {
                           original.setLoadOrder(proposed.getLoadOrder());
                           original._conditionalUnset(update.isUnsetUpdate(), 20);
                        } else if (prop.equals("Name")) {
                           original.setName(proposed.getName());
                           original._conditionalUnset(update.isUnsetUpdate(), 2);
                        } else if (prop.equals("Notes")) {
                           original.setNotes(proposed.getNotes());
                           original._conditionalUnset(update.isUnsetUpdate(), 3);
                        } else if (prop.equals("Path")) {
                           original.setPath(proposed.getPath());
                           original._conditionalUnset(update.isUnsetUpdate(), 10);
                        } else if (prop.equals("StagedTargets")) {
                           if (type == 2) {
                              update.resetAddedObject(update.getAddedObject());
                              original.addStagedTarget((String)update.getAddedObject());
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeStagedTarget((String)update.getRemovedObject());
                           }

                           if (original.getStagedTargets() == null || original.getStagedTargets().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 17);
                           }
                        } else if (prop.equals("StagingMode")) {
                           original.setStagingMode(proposed.getStagingMode());
                           original._conditionalUnset(update.isUnsetUpdate(), 18);
                        } else if (!prop.equals("StagingPath")) {
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
                           } else if (prop.equals("WebAppComponents")) {
                              if (type == 2) {
                                 if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                    update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                    original.addWebAppComponent((WebAppComponentMBean)update.getAddedObject());
                                 }
                              } else {
                                 if (type != 3) {
                                    throw new AssertionError("Invalid type: " + type);
                                 }

                                 original.removeWebAppComponent((WebAppComponentMBean)update.getRemovedObject());
                              }

                              if (original.getWebAppComponents() == null || original.getWebAppComponents().length == 0) {
                                 original._conditionalUnset(update.isUnsetUpdate(), 12);
                              }
                           } else if (prop.equals("WebServiceComponents")) {
                              if (type == 2) {
                                 if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                    update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                    original.addWebServiceComponent((WebServiceComponentMBean)update.getAddedObject());
                                 }
                              } else {
                                 if (type != 3) {
                                    throw new AssertionError("Invalid type: " + type);
                                 }

                                 original.removeWebServiceComponent((WebServiceComponentMBean)update.getRemovedObject());
                              }

                              if (original.getWebServiceComponents() == null || original.getWebServiceComponents().length == 0) {
                                 original._conditionalUnset(update.isUnsetUpdate(), 28);
                              }
                           } else if (!prop.equals("DelegationEnabled")) {
                              if (prop.equals("Deployed")) {
                                 original.setDeployed(proposed.isDeployed());
                                 original._conditionalUnset(update.isUnsetUpdate(), 25);
                              } else if (!prop.equals("DynamicallyCreated")) {
                                 if (prop.equals("Ear")) {
                                    original._conditionalUnset(update.isUnsetUpdate(), 13);
                                 } else if (!prop.equals("InternalApp")) {
                                    if (prop.equals("TwoPhase")) {
                                       original.setTwoPhase(proposed.isTwoPhase());
                                       original._conditionalUnset(update.isUnsetUpdate(), 19);
                                    } else {
                                       super.applyPropertyUpdate(event, update);
                                    }
                                 }
                              }
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
            ApplicationMBeanImpl copy = (ApplicationMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("AltDescriptorPath")) && this.bean.isAltDescriptorPathSet()) {
               copy.setAltDescriptorPath(this.bean.getAltDescriptorPath());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("AltWLSDescriptorPath")) && this.bean.isAltWLSDescriptorPathSet()) {
               copy.setAltWLSDescriptorPath(this.bean.getAltWLSDescriptorPath());
            }

            int i;
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ConnectorComponents")) && this.bean.isConnectorComponentsSet() && !copy._isSet(27)) {
               ConnectorComponentMBean[] oldConnectorComponents = this.bean.getConnectorComponents();
               ConnectorComponentMBean[] newConnectorComponents = new ConnectorComponentMBean[oldConnectorComponents.length];

               for(i = 0; i < newConnectorComponents.length; ++i) {
                  newConnectorComponents[i] = (ConnectorComponentMBean)((ConnectorComponentMBean)this.createCopy((AbstractDescriptorBean)oldConnectorComponents[i], includeObsolete));
               }

               copy.setConnectorComponents(newConnectorComponents);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("DeploymentTimeout")) && this.bean.isDeploymentTimeoutSet()) {
               copy.setDeploymentTimeout(this.bean.getDeploymentTimeout());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("EJBComponents")) && this.bean.isEJBComponentsSet() && !copy._isSet(26)) {
               EJBComponentMBean[] oldEJBComponents = this.bean.getEJBComponents();
               EJBComponentMBean[] newEJBComponents = new EJBComponentMBean[oldEJBComponents.length];

               for(i = 0; i < newEJBComponents.length; ++i) {
                  newEJBComponents[i] = (EJBComponentMBean)((EJBComponentMBean)this.createCopy((AbstractDescriptorBean)oldEJBComponents[i], includeObsolete));
               }

               copy.setEJBComponents(newEJBComponents);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JDBCPoolComponents")) && this.bean.isJDBCPoolComponentsSet() && !copy._isSet(29)) {
               JDBCPoolComponentMBean[] oldJDBCPoolComponents = this.bean.getJDBCPoolComponents();
               JDBCPoolComponentMBean[] newJDBCPoolComponents = new JDBCPoolComponentMBean[oldJDBCPoolComponents.length];

               for(i = 0; i < newJDBCPoolComponents.length; ++i) {
                  newJDBCPoolComponents[i] = (JDBCPoolComponentMBean)((JDBCPoolComponentMBean)this.createCopy((AbstractDescriptorBean)oldJDBCPoolComponents[i], includeObsolete));
               }

               copy.setJDBCPoolComponents(newJDBCPoolComponents);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LoadOrder")) && this.bean.isLoadOrderSet()) {
               copy.setLoadOrder(this.bean.getLoadOrder());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Notes")) && this.bean.isNotesSet()) {
               copy.setNotes(this.bean.getNotes());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Path")) && this.bean.isPathSet()) {
               copy.setPath(this.bean.getPath());
            }

            String[] o;
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StagedTargets")) && this.bean.isStagedTargetsSet()) {
               o = this.bean.getStagedTargets();
               copy.setStagedTargets(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StagingMode")) && this.bean.isStagingModeSet()) {
               copy.setStagingMode(this.bean.getStagingMode());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("WebAppComponents")) && this.bean.isWebAppComponentsSet() && !copy._isSet(12)) {
               WebAppComponentMBean[] oldWebAppComponents = this.bean.getWebAppComponents();
               WebAppComponentMBean[] newWebAppComponents = new WebAppComponentMBean[oldWebAppComponents.length];

               for(i = 0; i < newWebAppComponents.length; ++i) {
                  newWebAppComponents[i] = (WebAppComponentMBean)((WebAppComponentMBean)this.createCopy((AbstractDescriptorBean)oldWebAppComponents[i], includeObsolete));
               }

               copy.setWebAppComponents(newWebAppComponents);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("WebServiceComponents")) && this.bean.isWebServiceComponentsSet() && !copy._isSet(28)) {
               WebServiceComponentMBean[] oldWebServiceComponents = this.bean.getWebServiceComponents();
               WebServiceComponentMBean[] newWebServiceComponents = new WebServiceComponentMBean[oldWebServiceComponents.length];

               for(i = 0; i < newWebServiceComponents.length; ++i) {
                  newWebServiceComponents[i] = (WebServiceComponentMBean)((WebServiceComponentMBean)this.createCopy((AbstractDescriptorBean)oldWebServiceComponents[i], includeObsolete));
               }

               copy.setWebServiceComponents(newWebServiceComponents);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Deployed")) && this.bean.isDeployedSet()) {
               copy.setDeployed(this.bean.isDeployed());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Ear")) && this.bean.isEarSet()) {
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("TwoPhase")) && this.bean.isTwoPhaseSet()) {
               copy.setTwoPhase(this.bean.isTwoPhase());
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
         this.inferSubTree(this.bean.getAppDeployment(), clazz, annotation);
         this.inferSubTree(this.bean.getComponents(), clazz, annotation);
         this.inferSubTree(this.bean.getConnectorComponents(), clazz, annotation);
         this.inferSubTree(this.bean.getEJBComponents(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCPoolComponents(), clazz, annotation);
         this.inferSubTree(this.bean.getWebAppComponents(), clazz, annotation);
         this.inferSubTree(this.bean.getWebServiceComponents(), clazz, annotation);
      }
   }
}
