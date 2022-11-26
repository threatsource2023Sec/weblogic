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
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.ResourceGroupTemplate;
import weblogic.utils.ArrayUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ResourceGroupTemplateMBeanImpl extends ConfigurationMBeanImpl implements ResourceGroupTemplateMBean, Serializable {
   private AppDeploymentMBean[] _AppDeployments;
   private CoherenceClusterSystemResourceMBean[] _CoherenceClusterSystemResources;
   private boolean _DynamicallyCreated;
   private FileStoreMBean[] _FileStores;
   private ForeignJNDIProviderMBean[] _ForeignJNDIProviders;
   private boolean _ImMutable;
   private JDBCStoreMBean[] _JDBCStores;
   private JDBCSystemResourceMBean[] _JDBCSystemResources;
   private JMSBridgeDestinationMBean[] _JMSBridgeDestinations;
   private JMSServerMBean[] _JMSServers;
   private JMSSystemResourceMBean[] _JMSSystemResources;
   private LibraryMBean[] _Libraries;
   private MailSessionMBean[] _MailSessions;
   private ManagedExecutorServiceMBean[] _ManagedExecutorServices;
   private ManagedScheduledExecutorServiceMBean[] _ManagedScheduledExecutorServices;
   private ManagedThreadFactoryMBean[] _ManagedThreadFactories;
   private MessagingBridgeMBean[] _MessagingBridges;
   private String _Name;
   private OsgiFrameworkMBean[] _OsgiFrameworks;
   private PathServiceMBean[] _PathServices;
   private String _ResourceGroupTemplateID;
   private SAFAgentMBean[] _SAFAgents;
   private String[] _Tags;
   private String _UploadDirectoryName;
   private WLDFSystemResourceMBean[] _WLDFSystemResources;
   private transient ResourceGroupTemplate _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ResourceGroupTemplateMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ResourceGroupTemplateMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ResourceGroupTemplateMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ResourceGroupTemplateMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ResourceGroupTemplateMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ResourceGroupTemplateMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public ResourceGroupTemplateMBeanImpl() {
      try {
         this._customizer = new ResourceGroupTemplate(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ResourceGroupTemplateMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ResourceGroupTemplate(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ResourceGroupTemplateMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ResourceGroupTemplate(this);
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

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void addAppDeployment(AppDeploymentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         AppDeploymentMBean[] _new;
         if (this._isSet(10)) {
            _new = (AppDeploymentMBean[])((AppDeploymentMBean[])this._getHelper()._extendArray(this.getAppDeployments(), AppDeploymentMBean.class, param0));
         } else {
            _new = new AppDeploymentMBean[]{param0};
         }

         try {
            this.setAppDeployments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AppDeploymentMBean[] getAppDeployments() {
      AppDeploymentMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(10)) {
         delegateArray = this._getDelegateBean().getAppDeployments();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._AppDeployments.length; ++j) {
               if (delegateArray[i].getName().equals(this._AppDeployments[j].getName())) {
                  ((AppDeploymentMBeanImpl)this._AppDeployments[j])._setDelegateBean((AppDeploymentMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  AppDeploymentMBeanImpl mbean = new AppDeploymentMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 10);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((AppDeploymentMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(10)) {
                     this.setAppDeployments((AppDeploymentMBean[])((AppDeploymentMBean[])this._getHelper()._extendArray(this._AppDeployments, AppDeploymentMBean.class, mbean)));
                  } else {
                     this.setAppDeployments(new AppDeploymentMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new AppDeploymentMBean[0];
      }

      if (this._AppDeployments != null) {
         List removeList = new ArrayList();
         AppDeploymentMBean[] var18 = this._AppDeployments;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            AppDeploymentMBean bn = var18[var5];
            AppDeploymentMBeanImpl bni = (AppDeploymentMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               AppDeploymentMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  AppDeploymentMBean delegateTo = var10[var12];
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
            AppDeploymentMBean removeIt = (AppDeploymentMBean)var19.next();
            AppDeploymentMBeanImpl removeItImpl = (AppDeploymentMBeanImpl)removeIt;
            AppDeploymentMBean[] _new = (AppDeploymentMBean[])((AppDeploymentMBean[])this._getHelper()._removeElement(this._AppDeployments, AppDeploymentMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setAppDeployments(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._AppDeployments;
   }

   public boolean isAppDeploymentsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(10)) {
         AppDeploymentMBean[] elements = this.getAppDeployments();
         AppDeploymentMBean[] var2 = elements;
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

   public boolean isAppDeploymentsSet() {
      return this._isSet(10);
   }

   public void removeAppDeployment(AppDeploymentMBean param0) {
      this.destroyAppDeployment(param0);
   }

   public void setAppDeployments(AppDeploymentMBean[] param0) throws InvalidAttributeValueException {
      AppDeploymentMBean[] param0 = param0 == null ? new AppDeploymentMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._AppDeployments, (Object[])param0, handler, new Comparator() {
            public int compare(AppDeploymentMBean o1, AppDeploymentMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            AppDeploymentMBean bean = (AppDeploymentMBean)var3.next();
            AppDeploymentMBeanImpl beanImpl = (AppDeploymentMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(10);
      AppDeploymentMBean[] _oldVal = this._AppDeployments;
      this._AppDeployments = (AppDeploymentMBean[])param0;
      this._postSet(10, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
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
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public AppDeploymentMBean lookupAppDeployment(String param0) {
      Object[] aary = (Object[])this.getAppDeployments();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      AppDeploymentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (AppDeploymentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public AppDeploymentMBean createAppDeployment(String param0, String param1) throws IllegalArgumentException {
      AppDeploymentMBeanImpl lookup = (AppDeploymentMBeanImpl)this.lookupAppDeployment(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         AppDeploymentMBeanImpl _val = new AppDeploymentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setSourcePath(param1);
            this.addAppDeployment(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else if (var6 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void destroyAppDeployment(AppDeploymentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         AppDeploymentMBean[] _old = this.getAppDeployments();
         AppDeploymentMBean[] _new = (AppDeploymentMBean[])((AppDeploymentMBean[])this._getHelper()._removeElement(_old, AppDeploymentMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  AppDeploymentMBeanImpl childImpl = (AppDeploymentMBeanImpl)_child;
                  AppDeploymentMBeanImpl lookup = (AppDeploymentMBeanImpl)source.lookupAppDeployment(childImpl.getName());
                  if (lookup != null) {
                     source.destroyAppDeployment(lookup);
                  }
               }

               this.setAppDeployments(_new);
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

   public void addLibrary(LibraryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         LibraryMBean[] _new;
         if (this._isSet(11)) {
            _new = (LibraryMBean[])((LibraryMBean[])this._getHelper()._extendArray(this.getLibraries(), LibraryMBean.class, param0));
         } else {
            _new = new LibraryMBean[]{param0};
         }

         try {
            this.setLibraries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LibraryMBean[] getLibraries() {
      LibraryMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         delegateArray = this._getDelegateBean().getLibraries();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._Libraries.length; ++j) {
               if (delegateArray[i].getName().equals(this._Libraries[j].getName())) {
                  ((LibraryMBeanImpl)this._Libraries[j])._setDelegateBean((LibraryMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  LibraryMBeanImpl mbean = new LibraryMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 11);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((LibraryMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(11)) {
                     this.setLibraries((LibraryMBean[])((LibraryMBean[])this._getHelper()._extendArray(this._Libraries, LibraryMBean.class, mbean)));
                  } else {
                     this.setLibraries(new LibraryMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new LibraryMBean[0];
      }

      if (this._Libraries != null) {
         List removeList = new ArrayList();
         LibraryMBean[] var18 = this._Libraries;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            LibraryMBean bn = var18[var5];
            LibraryMBeanImpl bni = (LibraryMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               LibraryMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  LibraryMBean delegateTo = var10[var12];
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
            LibraryMBean removeIt = (LibraryMBean)var19.next();
            LibraryMBeanImpl removeItImpl = (LibraryMBeanImpl)removeIt;
            LibraryMBean[] _new = (LibraryMBean[])((LibraryMBean[])this._getHelper()._removeElement(this._Libraries, LibraryMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setLibraries(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._Libraries;
   }

   public boolean isLibrariesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         LibraryMBean[] elements = this.getLibraries();
         LibraryMBean[] var2 = elements;
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

   public boolean isLibrariesSet() {
      return this._isSet(11);
   }

   public void removeLibrary(LibraryMBean param0) {
      this.destroyLibrary(param0);
   }

   public void setLibraries(LibraryMBean[] param0) throws InvalidAttributeValueException {
      LibraryMBean[] param0 = param0 == null ? new LibraryMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._Libraries, (Object[])param0, handler, new Comparator() {
            public int compare(LibraryMBean o1, LibraryMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            LibraryMBean bean = (LibraryMBean)var3.next();
            LibraryMBeanImpl beanImpl = (LibraryMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(11);
      LibraryMBean[] _oldVal = this._Libraries;
      this._Libraries = (LibraryMBean[])param0;
      this._postSet(11, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public LibraryMBean lookupLibrary(String param0) {
      Object[] aary = (Object[])this.getLibraries();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      LibraryMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (LibraryMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public LibraryMBean createLibrary(String param0, String param1) {
      LibraryMBeanImpl lookup = (LibraryMBeanImpl)this.lookupLibrary(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         LibraryMBeanImpl _val = new LibraryMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setSourcePath(param1);
            this.addLibrary(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void destroyLibrary(LibraryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         LibraryMBean[] _old = this.getLibraries();
         LibraryMBean[] _new = (LibraryMBean[])((LibraryMBean[])this._getHelper()._removeElement(_old, LibraryMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  LibraryMBeanImpl childImpl = (LibraryMBeanImpl)_child;
                  LibraryMBeanImpl lookup = (LibraryMBeanImpl)source.lookupLibrary(childImpl.getName());
                  if (lookup != null) {
                     source.destroyLibrary(lookup);
                  }
               }

               this.setLibraries(_new);
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

   public BasicDeploymentMBean[] getBasicDeployments() {
      return this._customizer.getBasicDeployments();
   }

   public DeploymentMBean[] getDeployments() {
      return this._customizer.getDeployments();
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public SystemResourceMBean[] getSystemResources() {
      return this._customizer.getSystemResources();
   }

   public void addJMSServer(JMSServerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         JMSServerMBean[] _new;
         if (this._isSet(12)) {
            _new = (JMSServerMBean[])((JMSServerMBean[])this._getHelper()._extendArray(this.getJMSServers(), JMSServerMBean.class, param0));
         } else {
            _new = new JMSServerMBean[]{param0};
         }

         try {
            this.setJMSServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSServerMBean[] getJMSServers() {
      JMSServerMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(12)) {
         delegateArray = this._getDelegateBean().getJMSServers();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._JMSServers.length; ++j) {
               if (delegateArray[i].getName().equals(this._JMSServers[j].getName())) {
                  ((JMSServerMBeanImpl)this._JMSServers[j])._setDelegateBean((JMSServerMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  JMSServerMBeanImpl mbean = new JMSServerMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 12);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((JMSServerMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(12)) {
                     this.setJMSServers((JMSServerMBean[])((JMSServerMBean[])this._getHelper()._extendArray(this._JMSServers, JMSServerMBean.class, mbean)));
                  } else {
                     this.setJMSServers(new JMSServerMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new JMSServerMBean[0];
      }

      if (this._JMSServers != null) {
         List removeList = new ArrayList();
         JMSServerMBean[] var18 = this._JMSServers;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            JMSServerMBean bn = var18[var5];
            JMSServerMBeanImpl bni = (JMSServerMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               JMSServerMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  JMSServerMBean delegateTo = var10[var12];
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
            JMSServerMBean removeIt = (JMSServerMBean)var19.next();
            JMSServerMBeanImpl removeItImpl = (JMSServerMBeanImpl)removeIt;
            JMSServerMBean[] _new = (JMSServerMBean[])((JMSServerMBean[])this._getHelper()._removeElement(this._JMSServers, JMSServerMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setJMSServers(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._JMSServers;
   }

   public boolean isJMSServersInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(12)) {
         JMSServerMBean[] elements = this.getJMSServers();
         JMSServerMBean[] var2 = elements;
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

   public boolean isJMSServersSet() {
      return this._isSet(12);
   }

   public void removeJMSServer(JMSServerMBean param0) {
      this.destroyJMSServer(param0);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setJMSServers(JMSServerMBean[] param0) throws InvalidAttributeValueException {
      JMSServerMBean[] param0 = param0 == null ? new JMSServerMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._JMSServers, (Object[])param0, handler, new Comparator() {
            public int compare(JMSServerMBean o1, JMSServerMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            JMSServerMBean bean = (JMSServerMBean)var3.next();
            JMSServerMBeanImpl beanImpl = (JMSServerMBeanImpl)bean;
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
      JMSServerMBean[] _oldVal = this._JMSServers;
      this._JMSServers = (JMSServerMBean[])param0;
      this._postSet(12, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public JMSServerMBean createJMSServer(String param0) {
      JMSServerMBeanImpl lookup = (JMSServerMBeanImpl)this.lookupJMSServer(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSServerMBeanImpl _val = new JMSServerMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSServer(_val);
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

   public void destroyJMSServer(JMSServerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         JMSServerMBean[] _old = this.getJMSServers();
         JMSServerMBean[] _new = (JMSServerMBean[])((JMSServerMBean[])this._getHelper()._removeElement(_old, JMSServerMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  JMSServerMBeanImpl childImpl = (JMSServerMBeanImpl)_child;
                  JMSServerMBeanImpl lookup = (JMSServerMBeanImpl)source.lookupJMSServer(childImpl.getName());
                  if (lookup != null) {
                     source.destroyJMSServer(lookup);
                  }
               }

               this.setJMSServers(_new);
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

   public JMSServerMBean lookupJMSServer(String param0) {
      Object[] aary = (Object[])this.getJMSServers();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSServerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSServerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addMessagingBridge(MessagingBridgeMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         MessagingBridgeMBean[] _new;
         if (this._isSet(13)) {
            _new = (MessagingBridgeMBean[])((MessagingBridgeMBean[])this._getHelper()._extendArray(this.getMessagingBridges(), MessagingBridgeMBean.class, param0));
         } else {
            _new = new MessagingBridgeMBean[]{param0};
         }

         try {
            this.setMessagingBridges(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MessagingBridgeMBean[] getMessagingBridges() {
      MessagingBridgeMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(13)) {
         delegateArray = this._getDelegateBean().getMessagingBridges();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._MessagingBridges.length; ++j) {
               if (delegateArray[i].getName().equals(this._MessagingBridges[j].getName())) {
                  ((MessagingBridgeMBeanImpl)this._MessagingBridges[j])._setDelegateBean((MessagingBridgeMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  MessagingBridgeMBeanImpl mbean = new MessagingBridgeMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 13);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((MessagingBridgeMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(13)) {
                     this.setMessagingBridges((MessagingBridgeMBean[])((MessagingBridgeMBean[])this._getHelper()._extendArray(this._MessagingBridges, MessagingBridgeMBean.class, mbean)));
                  } else {
                     this.setMessagingBridges(new MessagingBridgeMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new MessagingBridgeMBean[0];
      }

      if (this._MessagingBridges != null) {
         List removeList = new ArrayList();
         MessagingBridgeMBean[] var18 = this._MessagingBridges;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            MessagingBridgeMBean bn = var18[var5];
            MessagingBridgeMBeanImpl bni = (MessagingBridgeMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               MessagingBridgeMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  MessagingBridgeMBean delegateTo = var10[var12];
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
            MessagingBridgeMBean removeIt = (MessagingBridgeMBean)var19.next();
            MessagingBridgeMBeanImpl removeItImpl = (MessagingBridgeMBeanImpl)removeIt;
            MessagingBridgeMBean[] _new = (MessagingBridgeMBean[])((MessagingBridgeMBean[])this._getHelper()._removeElement(this._MessagingBridges, MessagingBridgeMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setMessagingBridges(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._MessagingBridges;
   }

   public boolean isMessagingBridgesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(13)) {
         MessagingBridgeMBean[] elements = this.getMessagingBridges();
         MessagingBridgeMBean[] var2 = elements;
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

   public boolean isMessagingBridgesSet() {
      return this._isSet(13);
   }

   public void removeMessagingBridge(MessagingBridgeMBean param0) {
      this.destroyMessagingBridge(param0);
   }

   public void setMessagingBridges(MessagingBridgeMBean[] param0) throws InvalidAttributeValueException {
      MessagingBridgeMBean[] param0 = param0 == null ? new MessagingBridgeMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._MessagingBridges, (Object[])param0, handler, new Comparator() {
            public int compare(MessagingBridgeMBean o1, MessagingBridgeMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            MessagingBridgeMBean bean = (MessagingBridgeMBean)var3.next();
            MessagingBridgeMBeanImpl beanImpl = (MessagingBridgeMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(13);
      MessagingBridgeMBean[] _oldVal = this._MessagingBridges;
      this._MessagingBridges = (MessagingBridgeMBean[])param0;
      this._postSet(13, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public MessagingBridgeMBean createMessagingBridge(String param0) {
      MessagingBridgeMBeanImpl lookup = (MessagingBridgeMBeanImpl)this.lookupMessagingBridge(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         MessagingBridgeMBeanImpl _val = new MessagingBridgeMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMessagingBridge(_val);
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

   public void destroyMessagingBridge(MessagingBridgeMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         MessagingBridgeMBean[] _old = this.getMessagingBridges();
         MessagingBridgeMBean[] _new = (MessagingBridgeMBean[])((MessagingBridgeMBean[])this._getHelper()._removeElement(_old, MessagingBridgeMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  MessagingBridgeMBeanImpl childImpl = (MessagingBridgeMBeanImpl)_child;
                  MessagingBridgeMBeanImpl lookup = (MessagingBridgeMBeanImpl)source.lookupMessagingBridge(childImpl.getName());
                  if (lookup != null) {
                     source.destroyMessagingBridge(lookup);
                  }
               }

               this.setMessagingBridges(_new);
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

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public MessagingBridgeMBean lookupMessagingBridge(String param0) {
      Object[] aary = (Object[])this.getMessagingBridges();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MessagingBridgeMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MessagingBridgeMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addPathService(PathServiceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         PathServiceMBean[] _new;
         if (this._isSet(14)) {
            _new = (PathServiceMBean[])((PathServiceMBean[])this._getHelper()._extendArray(this.getPathServices(), PathServiceMBean.class, param0));
         } else {
            _new = new PathServiceMBean[]{param0};
         }

         try {
            this.setPathServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PathServiceMBean[] getPathServices() {
      PathServiceMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
         delegateArray = this._getDelegateBean().getPathServices();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._PathServices.length; ++j) {
               if (delegateArray[i].getName().equals(this._PathServices[j].getName())) {
                  ((PathServiceMBeanImpl)this._PathServices[j])._setDelegateBean((PathServiceMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  PathServiceMBeanImpl mbean = new PathServiceMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 14);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((PathServiceMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(14)) {
                     this.setPathServices((PathServiceMBean[])((PathServiceMBean[])this._getHelper()._extendArray(this._PathServices, PathServiceMBean.class, mbean)));
                  } else {
                     this.setPathServices(new PathServiceMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new PathServiceMBean[0];
      }

      if (this._PathServices != null) {
         List removeList = new ArrayList();
         PathServiceMBean[] var18 = this._PathServices;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            PathServiceMBean bn = var18[var5];
            PathServiceMBeanImpl bni = (PathServiceMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               PathServiceMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  PathServiceMBean delegateTo = var10[var12];
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
            PathServiceMBean removeIt = (PathServiceMBean)var19.next();
            PathServiceMBeanImpl removeItImpl = (PathServiceMBeanImpl)removeIt;
            PathServiceMBean[] _new = (PathServiceMBean[])((PathServiceMBean[])this._getHelper()._removeElement(this._PathServices, PathServiceMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setPathServices(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._PathServices;
   }

   public boolean isPathServicesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
         PathServiceMBean[] elements = this.getPathServices();
         PathServiceMBean[] var2 = elements;
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

   public boolean isPathServicesSet() {
      return this._isSet(14);
   }

   public void removePathService(PathServiceMBean param0) {
      this.destroyPathService(param0);
   }

   public void setPathServices(PathServiceMBean[] param0) throws InvalidAttributeValueException {
      PathServiceMBean[] param0 = param0 == null ? new PathServiceMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._PathServices, (Object[])param0, handler, new Comparator() {
            public int compare(PathServiceMBean o1, PathServiceMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            PathServiceMBean bean = (PathServiceMBean)var3.next();
            PathServiceMBeanImpl beanImpl = (PathServiceMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(14);
      PathServiceMBean[] _oldVal = this._PathServices;
      this._PathServices = (PathServiceMBean[])param0;
      this._postSet(14, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

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
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var4.next();
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

   public PathServiceMBean createPathService(String param0) {
      PathServiceMBeanImpl lookup = (PathServiceMBeanImpl)this.lookupPathService(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         PathServiceMBeanImpl _val = new PathServiceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addPathService(_val);
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

   public void destroyPathService(PathServiceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         PathServiceMBean[] _old = this.getPathServices();
         PathServiceMBean[] _new = (PathServiceMBean[])((PathServiceMBean[])this._getHelper()._removeElement(_old, PathServiceMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  PathServiceMBeanImpl childImpl = (PathServiceMBeanImpl)_child;
                  PathServiceMBeanImpl lookup = (PathServiceMBeanImpl)source.lookupPathService(childImpl.getName());
                  if (lookup != null) {
                     source.destroyPathService(lookup);
                  }
               }

               this.setPathServices(_new);
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

   public PathServiceMBean lookupPathService(String param0) {
      Object[] aary = (Object[])this.getPathServices();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      PathServiceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (PathServiceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JMSBridgeDestinationMBean createJMSBridgeDestination(String param0) {
      JMSBridgeDestinationMBeanImpl lookup = (JMSBridgeDestinationMBeanImpl)this.lookupJMSBridgeDestination(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSBridgeDestinationMBeanImpl _val = new JMSBridgeDestinationMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSBridgeDestination(_val);
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

   public void destroyJMSBridgeDestination(JMSBridgeDestinationMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 15);
         JMSBridgeDestinationMBean[] _old = this.getJMSBridgeDestinations();
         JMSBridgeDestinationMBean[] _new = (JMSBridgeDestinationMBean[])((JMSBridgeDestinationMBean[])this._getHelper()._removeElement(_old, JMSBridgeDestinationMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  JMSBridgeDestinationMBeanImpl childImpl = (JMSBridgeDestinationMBeanImpl)_child;
                  JMSBridgeDestinationMBeanImpl lookup = (JMSBridgeDestinationMBeanImpl)source.lookupJMSBridgeDestination(childImpl.getName());
                  if (lookup != null) {
                     source.destroyJMSBridgeDestination(lookup);
                  }
               }

               this.setJMSBridgeDestinations(_new);
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

   public JMSBridgeDestinationMBean lookupJMSBridgeDestination(String param0) {
      Object[] aary = (Object[])this.getJMSBridgeDestinations();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSBridgeDestinationMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSBridgeDestinationMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSBridgeDestination(JMSBridgeDestinationMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         JMSBridgeDestinationMBean[] _new;
         if (this._isSet(15)) {
            _new = (JMSBridgeDestinationMBean[])((JMSBridgeDestinationMBean[])this._getHelper()._extendArray(this.getJMSBridgeDestinations(), JMSBridgeDestinationMBean.class, param0));
         } else {
            _new = new JMSBridgeDestinationMBean[]{param0};
         }

         try {
            this.setJMSBridgeDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSBridgeDestinationMBean[] getJMSBridgeDestinations() {
      JMSBridgeDestinationMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(15)) {
         delegateArray = this._getDelegateBean().getJMSBridgeDestinations();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._JMSBridgeDestinations.length; ++j) {
               if (delegateArray[i].getName().equals(this._JMSBridgeDestinations[j].getName())) {
                  ((JMSBridgeDestinationMBeanImpl)this._JMSBridgeDestinations[j])._setDelegateBean((JMSBridgeDestinationMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  JMSBridgeDestinationMBeanImpl mbean = new JMSBridgeDestinationMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 15);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((JMSBridgeDestinationMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(15)) {
                     this.setJMSBridgeDestinations((JMSBridgeDestinationMBean[])((JMSBridgeDestinationMBean[])this._getHelper()._extendArray(this._JMSBridgeDestinations, JMSBridgeDestinationMBean.class, mbean)));
                  } else {
                     this.setJMSBridgeDestinations(new JMSBridgeDestinationMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new JMSBridgeDestinationMBean[0];
      }

      if (this._JMSBridgeDestinations != null) {
         List removeList = new ArrayList();
         JMSBridgeDestinationMBean[] var18 = this._JMSBridgeDestinations;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            JMSBridgeDestinationMBean bn = var18[var5];
            JMSBridgeDestinationMBeanImpl bni = (JMSBridgeDestinationMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               JMSBridgeDestinationMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  JMSBridgeDestinationMBean delegateTo = var10[var12];
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
            JMSBridgeDestinationMBean removeIt = (JMSBridgeDestinationMBean)var19.next();
            JMSBridgeDestinationMBeanImpl removeItImpl = (JMSBridgeDestinationMBeanImpl)removeIt;
            JMSBridgeDestinationMBean[] _new = (JMSBridgeDestinationMBean[])((JMSBridgeDestinationMBean[])this._getHelper()._removeElement(this._JMSBridgeDestinations, JMSBridgeDestinationMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setJMSBridgeDestinations(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._JMSBridgeDestinations;
   }

   public boolean isJMSBridgeDestinationsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(15)) {
         JMSBridgeDestinationMBean[] elements = this.getJMSBridgeDestinations();
         JMSBridgeDestinationMBean[] var2 = elements;
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

   public boolean isJMSBridgeDestinationsSet() {
      return this._isSet(15);
   }

   public void removeJMSBridgeDestination(JMSBridgeDestinationMBean param0) {
      this.destroyJMSBridgeDestination(param0);
   }

   public void setJMSBridgeDestinations(JMSBridgeDestinationMBean[] param0) throws InvalidAttributeValueException {
      JMSBridgeDestinationMBean[] param0 = param0 == null ? new JMSBridgeDestinationMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._JMSBridgeDestinations, (Object[])param0, handler, new Comparator() {
            public int compare(JMSBridgeDestinationMBean o1, JMSBridgeDestinationMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            JMSBridgeDestinationMBean bean = (JMSBridgeDestinationMBean)var3.next();
            JMSBridgeDestinationMBeanImpl beanImpl = (JMSBridgeDestinationMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(15);
      JMSBridgeDestinationMBean[] _oldVal = this._JMSBridgeDestinations;
      this._JMSBridgeDestinations = (JMSBridgeDestinationMBean[])param0;
      this._postSet(15, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public void addMailSession(MailSessionMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         MailSessionMBean[] _new;
         if (this._isSet(16)) {
            _new = (MailSessionMBean[])((MailSessionMBean[])this._getHelper()._extendArray(this.getMailSessions(), MailSessionMBean.class, param0));
         } else {
            _new = new MailSessionMBean[]{param0};
         }

         try {
            this.setMailSessions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MailSessionMBean[] getMailSessions() {
      MailSessionMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(16)) {
         delegateArray = this._getDelegateBean().getMailSessions();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._MailSessions.length; ++j) {
               if (delegateArray[i].getName().equals(this._MailSessions[j].getName())) {
                  ((MailSessionMBeanImpl)this._MailSessions[j])._setDelegateBean((MailSessionMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  MailSessionMBeanImpl mbean = new MailSessionMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 16);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((MailSessionMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(16)) {
                     this.setMailSessions((MailSessionMBean[])((MailSessionMBean[])this._getHelper()._extendArray(this._MailSessions, MailSessionMBean.class, mbean)));
                  } else {
                     this.setMailSessions(new MailSessionMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new MailSessionMBean[0];
      }

      if (this._MailSessions != null) {
         List removeList = new ArrayList();
         MailSessionMBean[] var18 = this._MailSessions;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            MailSessionMBean bn = var18[var5];
            MailSessionMBeanImpl bni = (MailSessionMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               MailSessionMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  MailSessionMBean delegateTo = var10[var12];
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
            MailSessionMBean removeIt = (MailSessionMBean)var19.next();
            MailSessionMBeanImpl removeItImpl = (MailSessionMBeanImpl)removeIt;
            MailSessionMBean[] _new = (MailSessionMBean[])((MailSessionMBean[])this._getHelper()._removeElement(this._MailSessions, MailSessionMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setMailSessions(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._MailSessions;
   }

   public boolean isMailSessionsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(16)) {
         MailSessionMBean[] elements = this.getMailSessions();
         MailSessionMBean[] var2 = elements;
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

   public boolean isMailSessionsSet() {
      return this._isSet(16);
   }

   public void removeMailSession(MailSessionMBean param0) {
      this.destroyMailSession(param0);
   }

   public void setMailSessions(MailSessionMBean[] param0) throws InvalidAttributeValueException {
      MailSessionMBean[] param0 = param0 == null ? new MailSessionMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._MailSessions, (Object[])param0, handler, new Comparator() {
            public int compare(MailSessionMBean o1, MailSessionMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            MailSessionMBean bean = (MailSessionMBean)var3.next();
            MailSessionMBeanImpl beanImpl = (MailSessionMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 16)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(16);
      MailSessionMBean[] _oldVal = this._MailSessions;
      this._MailSessions = (MailSessionMBean[])param0;
      this._postSet(16, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public MailSessionMBean createMailSession(String param0) {
      MailSessionMBeanImpl lookup = (MailSessionMBeanImpl)this.lookupMailSession(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         MailSessionMBeanImpl _val = new MailSessionMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMailSession(_val);
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

   public void destroyMailSession(MailSessionMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 16);
         MailSessionMBean[] _old = this.getMailSessions();
         MailSessionMBean[] _new = (MailSessionMBean[])((MailSessionMBean[])this._getHelper()._removeElement(_old, MailSessionMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  MailSessionMBeanImpl childImpl = (MailSessionMBeanImpl)_child;
                  MailSessionMBeanImpl lookup = (MailSessionMBeanImpl)source.lookupMailSession(childImpl.getName());
                  if (lookup != null) {
                     source.destroyMailSession(lookup);
                  }
               }

               this.setMailSessions(_new);
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

   public MailSessionMBean lookupMailSession(String param0) {
      Object[] aary = (Object[])this.getMailSessions();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MailSessionMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MailSessionMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addFileStore(FileStoreMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         FileStoreMBean[] _new;
         if (this._isSet(17)) {
            _new = (FileStoreMBean[])((FileStoreMBean[])this._getHelper()._extendArray(this.getFileStores(), FileStoreMBean.class, param0));
         } else {
            _new = new FileStoreMBean[]{param0};
         }

         try {
            this.setFileStores(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public FileStoreMBean[] getFileStores() {
      FileStoreMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(17)) {
         delegateArray = this._getDelegateBean().getFileStores();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._FileStores.length; ++j) {
               if (delegateArray[i].getName().equals(this._FileStores[j].getName())) {
                  ((FileStoreMBeanImpl)this._FileStores[j])._setDelegateBean((FileStoreMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  FileStoreMBeanImpl mbean = new FileStoreMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 17);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((FileStoreMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(17)) {
                     this.setFileStores((FileStoreMBean[])((FileStoreMBean[])this._getHelper()._extendArray(this._FileStores, FileStoreMBean.class, mbean)));
                  } else {
                     this.setFileStores(new FileStoreMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new FileStoreMBean[0];
      }

      if (this._FileStores != null) {
         List removeList = new ArrayList();
         FileStoreMBean[] var18 = this._FileStores;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            FileStoreMBean bn = var18[var5];
            FileStoreMBeanImpl bni = (FileStoreMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               FileStoreMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  FileStoreMBean delegateTo = var10[var12];
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
            FileStoreMBean removeIt = (FileStoreMBean)var19.next();
            FileStoreMBeanImpl removeItImpl = (FileStoreMBeanImpl)removeIt;
            FileStoreMBean[] _new = (FileStoreMBean[])((FileStoreMBean[])this._getHelper()._removeElement(this._FileStores, FileStoreMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setFileStores(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._FileStores;
   }

   public boolean isFileStoresInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(17)) {
         FileStoreMBean[] elements = this.getFileStores();
         FileStoreMBean[] var2 = elements;
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

   public boolean isFileStoresSet() {
      return this._isSet(17);
   }

   public void removeFileStore(FileStoreMBean param0) {
      this.destroyFileStore(param0);
   }

   public void setFileStores(FileStoreMBean[] param0) throws InvalidAttributeValueException {
      FileStoreMBean[] param0 = param0 == null ? new FileStoreMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._FileStores, (Object[])param0, handler, new Comparator() {
            public int compare(FileStoreMBean o1, FileStoreMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            FileStoreMBean bean = (FileStoreMBean)var3.next();
            FileStoreMBeanImpl beanImpl = (FileStoreMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(17);
      FileStoreMBean[] _oldVal = this._FileStores;
      this._FileStores = (FileStoreMBean[])param0;
      this._postSet(17, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public FileStoreMBean createFileStore(String param0) {
      FileStoreMBeanImpl lookup = (FileStoreMBeanImpl)this.lookupFileStore(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         FileStoreMBeanImpl _val = new FileStoreMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addFileStore(_val);
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

   public void destroyFileStore(FileStoreMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 17);
         FileStoreMBean[] _old = this.getFileStores();
         FileStoreMBean[] _new = (FileStoreMBean[])((FileStoreMBean[])this._getHelper()._removeElement(_old, FileStoreMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  FileStoreMBeanImpl childImpl = (FileStoreMBeanImpl)_child;
                  FileStoreMBeanImpl lookup = (FileStoreMBeanImpl)source.lookupFileStore(childImpl.getName());
                  if (lookup != null) {
                     source.destroyFileStore(lookup);
                  }
               }

               this.setFileStores(_new);
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

   public FileStoreMBean lookupFileStore(String param0) {
      Object[] aary = (Object[])this.getFileStores();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      FileStoreMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (FileStoreMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJDBCStore(JDBCStoreMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         JDBCStoreMBean[] _new;
         if (this._isSet(18)) {
            _new = (JDBCStoreMBean[])((JDBCStoreMBean[])this._getHelper()._extendArray(this.getJDBCStores(), JDBCStoreMBean.class, param0));
         } else {
            _new = new JDBCStoreMBean[]{param0};
         }

         try {
            this.setJDBCStores(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JDBCStoreMBean[] getJDBCStores() {
      JDBCStoreMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(18)) {
         delegateArray = this._getDelegateBean().getJDBCStores();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._JDBCStores.length; ++j) {
               if (delegateArray[i].getName().equals(this._JDBCStores[j].getName())) {
                  ((JDBCStoreMBeanImpl)this._JDBCStores[j])._setDelegateBean((JDBCStoreMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  JDBCStoreMBeanImpl mbean = new JDBCStoreMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 18);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((JDBCStoreMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(18)) {
                     this.setJDBCStores((JDBCStoreMBean[])((JDBCStoreMBean[])this._getHelper()._extendArray(this._JDBCStores, JDBCStoreMBean.class, mbean)));
                  } else {
                     this.setJDBCStores(new JDBCStoreMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new JDBCStoreMBean[0];
      }

      if (this._JDBCStores != null) {
         List removeList = new ArrayList();
         JDBCStoreMBean[] var18 = this._JDBCStores;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            JDBCStoreMBean bn = var18[var5];
            JDBCStoreMBeanImpl bni = (JDBCStoreMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               JDBCStoreMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  JDBCStoreMBean delegateTo = var10[var12];
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
            JDBCStoreMBean removeIt = (JDBCStoreMBean)var19.next();
            JDBCStoreMBeanImpl removeItImpl = (JDBCStoreMBeanImpl)removeIt;
            JDBCStoreMBean[] _new = (JDBCStoreMBean[])((JDBCStoreMBean[])this._getHelper()._removeElement(this._JDBCStores, JDBCStoreMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setJDBCStores(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._JDBCStores;
   }

   public boolean isJDBCStoresInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(18)) {
         JDBCStoreMBean[] elements = this.getJDBCStores();
         JDBCStoreMBean[] var2 = elements;
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

   public boolean isJDBCStoresSet() {
      return this._isSet(18);
   }

   public void removeJDBCStore(JDBCStoreMBean param0) {
      this.destroyJDBCStore(param0);
   }

   public void setJDBCStores(JDBCStoreMBean[] param0) throws InvalidAttributeValueException {
      JDBCStoreMBean[] param0 = param0 == null ? new JDBCStoreMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._JDBCStores, (Object[])param0, handler, new Comparator() {
            public int compare(JDBCStoreMBean o1, JDBCStoreMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            JDBCStoreMBean bean = (JDBCStoreMBean)var3.next();
            JDBCStoreMBeanImpl beanImpl = (JDBCStoreMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 18)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(18);
      JDBCStoreMBean[] _oldVal = this._JDBCStores;
      this._JDBCStores = (JDBCStoreMBean[])param0;
      this._postSet(18, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public JDBCStoreMBean createJDBCStore(String param0) {
      JDBCStoreMBeanImpl lookup = (JDBCStoreMBeanImpl)this.lookupJDBCStore(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JDBCStoreMBeanImpl _val = new JDBCStoreMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJDBCStore(_val);
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

   public void destroyJDBCStore(JDBCStoreMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 18);
         JDBCStoreMBean[] _old = this.getJDBCStores();
         JDBCStoreMBean[] _new = (JDBCStoreMBean[])((JDBCStoreMBean[])this._getHelper()._removeElement(_old, JDBCStoreMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  JDBCStoreMBeanImpl childImpl = (JDBCStoreMBeanImpl)_child;
                  JDBCStoreMBeanImpl lookup = (JDBCStoreMBeanImpl)source.lookupJDBCStore(childImpl.getName());
                  if (lookup != null) {
                     source.destroyJDBCStore(lookup);
                  }
               }

               this.setJDBCStores(_new);
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

   public JDBCStoreMBean lookupJDBCStore(String param0) {
      Object[] aary = (Object[])this.getJDBCStores();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JDBCStoreMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JDBCStoreMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addJMSSystemResource(JMSSystemResourceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 19)) {
         JMSSystemResourceMBean[] _new;
         if (this._isSet(19)) {
            _new = (JMSSystemResourceMBean[])((JMSSystemResourceMBean[])this._getHelper()._extendArray(this.getJMSSystemResources(), JMSSystemResourceMBean.class, param0));
         } else {
            _new = new JMSSystemResourceMBean[]{param0};
         }

         try {
            this.setJMSSystemResources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSSystemResourceMBean[] getJMSSystemResources() {
      JMSSystemResourceMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(19)) {
         delegateArray = this._getDelegateBean().getJMSSystemResources();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._JMSSystemResources.length; ++j) {
               if (delegateArray[i].getName().equals(this._JMSSystemResources[j].getName())) {
                  ((JMSSystemResourceMBeanImpl)this._JMSSystemResources[j])._setDelegateBean((JMSSystemResourceMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  JMSSystemResourceMBeanImpl mbean = new JMSSystemResourceMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 19);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((JMSSystemResourceMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(19)) {
                     this.setJMSSystemResources((JMSSystemResourceMBean[])((JMSSystemResourceMBean[])this._getHelper()._extendArray(this._JMSSystemResources, JMSSystemResourceMBean.class, mbean)));
                  } else {
                     this.setJMSSystemResources(new JMSSystemResourceMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new JMSSystemResourceMBean[0];
      }

      if (this._JMSSystemResources != null) {
         List removeList = new ArrayList();
         JMSSystemResourceMBean[] var18 = this._JMSSystemResources;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            JMSSystemResourceMBean bn = var18[var5];
            JMSSystemResourceMBeanImpl bni = (JMSSystemResourceMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               JMSSystemResourceMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  JMSSystemResourceMBean delegateTo = var10[var12];
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
            JMSSystemResourceMBean removeIt = (JMSSystemResourceMBean)var19.next();
            JMSSystemResourceMBeanImpl removeItImpl = (JMSSystemResourceMBeanImpl)removeIt;
            JMSSystemResourceMBean[] _new = (JMSSystemResourceMBean[])((JMSSystemResourceMBean[])this._getHelper()._removeElement(this._JMSSystemResources, JMSSystemResourceMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setJMSSystemResources(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._JMSSystemResources;
   }

   public boolean isJMSSystemResourcesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(19)) {
         JMSSystemResourceMBean[] elements = this.getJMSSystemResources();
         JMSSystemResourceMBean[] var2 = elements;
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

   public boolean isJMSSystemResourcesSet() {
      return this._isSet(19);
   }

   public void removeJMSSystemResource(JMSSystemResourceMBean param0) {
      this.destroyJMSSystemResource(param0);
   }

   public void setJMSSystemResources(JMSSystemResourceMBean[] param0) throws InvalidAttributeValueException {
      JMSSystemResourceMBean[] param0 = param0 == null ? new JMSSystemResourceMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._JMSSystemResources, (Object[])param0, handler, new Comparator() {
            public int compare(JMSSystemResourceMBean o1, JMSSystemResourceMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            JMSSystemResourceMBean bean = (JMSSystemResourceMBean)var3.next();
            JMSSystemResourceMBeanImpl beanImpl = (JMSSystemResourceMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 19)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(19);
      JMSSystemResourceMBean[] _oldVal = this._JMSSystemResources;
      this._JMSSystemResources = (JMSSystemResourceMBean[])param0;
      this._postSet(19, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public JMSSystemResourceMBean createJMSSystemResource(String param0) {
      JMSSystemResourceMBeanImpl lookup = (JMSSystemResourceMBeanImpl)this.lookupJMSSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSSystemResourceMBeanImpl _val = new JMSSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSSystemResource(_val);
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

   public JMSSystemResourceMBean createJMSSystemResource(String param0, String param1) {
      JMSSystemResourceMBeanImpl lookup = (JMSSystemResourceMBeanImpl)this.lookupJMSSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSSystemResourceMBeanImpl _val = new JMSSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setDescriptorFileName(param1);
            this.addJMSSystemResource(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public void destroyJMSSystemResource(JMSSystemResourceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 19);
         JMSSystemResourceMBean[] _old = this.getJMSSystemResources();
         JMSSystemResourceMBean[] _new = (JMSSystemResourceMBean[])((JMSSystemResourceMBean[])this._getHelper()._removeElement(_old, JMSSystemResourceMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  JMSSystemResourceMBeanImpl childImpl = (JMSSystemResourceMBeanImpl)_child;
                  JMSSystemResourceMBeanImpl lookup = (JMSSystemResourceMBeanImpl)source.lookupJMSSystemResource(childImpl.getName());
                  if (lookup != null) {
                     source.destroyJMSSystemResource(lookup);
                  }
               }

               this.setJMSSystemResources(_new);
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

   public JMSSystemResourceMBean lookupJMSSystemResource(String param0) {
      Object[] aary = (Object[])this.getJMSSystemResources();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSSystemResourceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSSystemResourceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addForeignJNDIProvider(ForeignJNDIProviderMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         ForeignJNDIProviderMBean[] _new;
         if (this._isSet(20)) {
            _new = (ForeignJNDIProviderMBean[])((ForeignJNDIProviderMBean[])this._getHelper()._extendArray(this.getForeignJNDIProviders(), ForeignJNDIProviderMBean.class, param0));
         } else {
            _new = new ForeignJNDIProviderMBean[]{param0};
         }

         try {
            this.setForeignJNDIProviders(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ForeignJNDIProviderMBean[] getForeignJNDIProviders() {
      ForeignJNDIProviderMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(20)) {
         delegateArray = this._getDelegateBean().getForeignJNDIProviders();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._ForeignJNDIProviders.length; ++j) {
               if (delegateArray[i].getName().equals(this._ForeignJNDIProviders[j].getName())) {
                  ((ForeignJNDIProviderMBeanImpl)this._ForeignJNDIProviders[j])._setDelegateBean((ForeignJNDIProviderMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  ForeignJNDIProviderMBeanImpl mbean = new ForeignJNDIProviderMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 20);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((ForeignJNDIProviderMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(20)) {
                     this.setForeignJNDIProviders((ForeignJNDIProviderMBean[])((ForeignJNDIProviderMBean[])this._getHelper()._extendArray(this._ForeignJNDIProviders, ForeignJNDIProviderMBean.class, mbean)));
                  } else {
                     this.setForeignJNDIProviders(new ForeignJNDIProviderMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new ForeignJNDIProviderMBean[0];
      }

      if (this._ForeignJNDIProviders != null) {
         List removeList = new ArrayList();
         ForeignJNDIProviderMBean[] var18 = this._ForeignJNDIProviders;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            ForeignJNDIProviderMBean bn = var18[var5];
            ForeignJNDIProviderMBeanImpl bni = (ForeignJNDIProviderMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               ForeignJNDIProviderMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  ForeignJNDIProviderMBean delegateTo = var10[var12];
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
            ForeignJNDIProviderMBean removeIt = (ForeignJNDIProviderMBean)var19.next();
            ForeignJNDIProviderMBeanImpl removeItImpl = (ForeignJNDIProviderMBeanImpl)removeIt;
            ForeignJNDIProviderMBean[] _new = (ForeignJNDIProviderMBean[])((ForeignJNDIProviderMBean[])this._getHelper()._removeElement(this._ForeignJNDIProviders, ForeignJNDIProviderMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setForeignJNDIProviders(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._ForeignJNDIProviders;
   }

   public boolean isForeignJNDIProvidersInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(20)) {
         ForeignJNDIProviderMBean[] elements = this.getForeignJNDIProviders();
         ForeignJNDIProviderMBean[] var2 = elements;
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

   public boolean isForeignJNDIProvidersSet() {
      return this._isSet(20);
   }

   public void removeForeignJNDIProvider(ForeignJNDIProviderMBean param0) {
      this.destroyForeignJNDIProvider(param0);
   }

   public void setForeignJNDIProviders(ForeignJNDIProviderMBean[] param0) throws InvalidAttributeValueException {
      ForeignJNDIProviderMBean[] param0 = param0 == null ? new ForeignJNDIProviderMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._ForeignJNDIProviders, (Object[])param0, handler, new Comparator() {
            public int compare(ForeignJNDIProviderMBean o1, ForeignJNDIProviderMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            ForeignJNDIProviderMBean bean = (ForeignJNDIProviderMBean)var3.next();
            ForeignJNDIProviderMBeanImpl beanImpl = (ForeignJNDIProviderMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 20)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(20);
      ForeignJNDIProviderMBean[] _oldVal = this._ForeignJNDIProviders;
      this._ForeignJNDIProviders = (ForeignJNDIProviderMBean[])param0;
      this._postSet(20, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public ForeignJNDIProviderMBean lookupForeignJNDIProvider(String param0) {
      Object[] aary = (Object[])this.getForeignJNDIProviders();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignJNDIProviderMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignJNDIProviderMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ForeignJNDIProviderMBean createForeignJNDIProvider(String param0) {
      ForeignJNDIProviderMBeanImpl lookup = (ForeignJNDIProviderMBeanImpl)this.lookupForeignJNDIProvider(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignJNDIProviderMBeanImpl _val = new ForeignJNDIProviderMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignJNDIProvider(_val);
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

   public void destroyForeignJNDIProvider(ForeignJNDIProviderMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 20);
         ForeignJNDIProviderMBean[] _old = this.getForeignJNDIProviders();
         ForeignJNDIProviderMBean[] _new = (ForeignJNDIProviderMBean[])((ForeignJNDIProviderMBean[])this._getHelper()._removeElement(_old, ForeignJNDIProviderMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  ForeignJNDIProviderMBeanImpl childImpl = (ForeignJNDIProviderMBeanImpl)_child;
                  ForeignJNDIProviderMBeanImpl lookup = (ForeignJNDIProviderMBeanImpl)source.lookupForeignJNDIProvider(childImpl.getName());
                  if (lookup != null) {
                     source.destroyForeignJNDIProvider(lookup);
                  }
               }

               this.setForeignJNDIProviders(_new);
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

   public void addJDBCSystemResource(JDBCSystemResourceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 21)) {
         JDBCSystemResourceMBean[] _new;
         if (this._isSet(21)) {
            _new = (JDBCSystemResourceMBean[])((JDBCSystemResourceMBean[])this._getHelper()._extendArray(this.getJDBCSystemResources(), JDBCSystemResourceMBean.class, param0));
         } else {
            _new = new JDBCSystemResourceMBean[]{param0};
         }

         try {
            this.setJDBCSystemResources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JDBCSystemResourceMBean[] getJDBCSystemResources() {
      JDBCSystemResourceMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(21)) {
         delegateArray = this._getDelegateBean().getJDBCSystemResources();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._JDBCSystemResources.length; ++j) {
               if (delegateArray[i].getName().equals(this._JDBCSystemResources[j].getName())) {
                  ((JDBCSystemResourceMBeanImpl)this._JDBCSystemResources[j])._setDelegateBean((JDBCSystemResourceMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  JDBCSystemResourceMBeanImpl mbean = new JDBCSystemResourceMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 21);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((JDBCSystemResourceMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(21)) {
                     this.setJDBCSystemResources((JDBCSystemResourceMBean[])((JDBCSystemResourceMBean[])this._getHelper()._extendArray(this._JDBCSystemResources, JDBCSystemResourceMBean.class, mbean)));
                  } else {
                     this.setJDBCSystemResources(new JDBCSystemResourceMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new JDBCSystemResourceMBean[0];
      }

      if (this._JDBCSystemResources != null) {
         List removeList = new ArrayList();
         JDBCSystemResourceMBean[] var18 = this._JDBCSystemResources;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            JDBCSystemResourceMBean bn = var18[var5];
            JDBCSystemResourceMBeanImpl bni = (JDBCSystemResourceMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               JDBCSystemResourceMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  JDBCSystemResourceMBean delegateTo = var10[var12];
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
            JDBCSystemResourceMBean removeIt = (JDBCSystemResourceMBean)var19.next();
            JDBCSystemResourceMBeanImpl removeItImpl = (JDBCSystemResourceMBeanImpl)removeIt;
            JDBCSystemResourceMBean[] _new = (JDBCSystemResourceMBean[])((JDBCSystemResourceMBean[])this._getHelper()._removeElement(this._JDBCSystemResources, JDBCSystemResourceMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setJDBCSystemResources(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._JDBCSystemResources;
   }

   public boolean isJDBCSystemResourcesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(21)) {
         JDBCSystemResourceMBean[] elements = this.getJDBCSystemResources();
         JDBCSystemResourceMBean[] var2 = elements;
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

   public boolean isJDBCSystemResourcesSet() {
      return this._isSet(21);
   }

   public void removeJDBCSystemResource(JDBCSystemResourceMBean param0) {
      this.destroyJDBCSystemResource(param0);
   }

   public void setJDBCSystemResources(JDBCSystemResourceMBean[] param0) throws InvalidAttributeValueException {
      JDBCSystemResourceMBean[] param0 = param0 == null ? new JDBCSystemResourceMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._JDBCSystemResources, (Object[])param0, handler, new Comparator() {
            public int compare(JDBCSystemResourceMBean o1, JDBCSystemResourceMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            JDBCSystemResourceMBean bean = (JDBCSystemResourceMBean)var3.next();
            JDBCSystemResourceMBeanImpl beanImpl = (JDBCSystemResourceMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 21)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(21);
      JDBCSystemResourceMBean[] _oldVal = this._JDBCSystemResources;
      this._JDBCSystemResources = (JDBCSystemResourceMBean[])param0;
      this._postSet(21, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public JDBCSystemResourceMBean createJDBCSystemResource(String param0) {
      JDBCSystemResourceMBeanImpl lookup = (JDBCSystemResourceMBeanImpl)this.lookupJDBCSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JDBCSystemResourceMBeanImpl _val = new JDBCSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJDBCSystemResource(_val);
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

   public JDBCSystemResourceMBean createJDBCSystemResource(String param0, String param1) {
      JDBCSystemResourceMBeanImpl lookup = (JDBCSystemResourceMBeanImpl)this.lookupJDBCSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JDBCSystemResourceMBeanImpl _val = new JDBCSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setDescriptorFileName(param1);
            this.addJDBCSystemResource(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public JDBCSystemResourceMBean lookupJDBCSystemResource(String param0) {
      Object[] aary = (Object[])this.getJDBCSystemResources();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JDBCSystemResourceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JDBCSystemResourceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyJDBCSystemResource(JDBCSystemResourceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 21);
         JDBCSystemResourceMBean[] _old = this.getJDBCSystemResources();
         JDBCSystemResourceMBean[] _new = (JDBCSystemResourceMBean[])((JDBCSystemResourceMBean[])this._getHelper()._removeElement(_old, JDBCSystemResourceMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  JDBCSystemResourceMBeanImpl childImpl = (JDBCSystemResourceMBeanImpl)_child;
                  JDBCSystemResourceMBeanImpl lookup = (JDBCSystemResourceMBeanImpl)source.lookupJDBCSystemResource(childImpl.getName());
                  if (lookup != null) {
                     source.destroyJDBCSystemResource(lookup);
                  }
               }

               this.setJDBCSystemResources(_new);
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

   public void addWLDFSystemResource(WLDFSystemResourceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 22)) {
         WLDFSystemResourceMBean[] _new;
         if (this._isSet(22)) {
            _new = (WLDFSystemResourceMBean[])((WLDFSystemResourceMBean[])this._getHelper()._extendArray(this.getWLDFSystemResources(), WLDFSystemResourceMBean.class, param0));
         } else {
            _new = new WLDFSystemResourceMBean[]{param0};
         }

         try {
            this.setWLDFSystemResources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFSystemResourceMBean[] getWLDFSystemResources() {
      WLDFSystemResourceMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(22)) {
         delegateArray = this._getDelegateBean().getWLDFSystemResources();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._WLDFSystemResources.length; ++j) {
               if (delegateArray[i].getName().equals(this._WLDFSystemResources[j].getName())) {
                  ((WLDFSystemResourceMBeanImpl)this._WLDFSystemResources[j])._setDelegateBean((WLDFSystemResourceMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  WLDFSystemResourceMBeanImpl mbean = new WLDFSystemResourceMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 22);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((WLDFSystemResourceMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(22)) {
                     this.setWLDFSystemResources((WLDFSystemResourceMBean[])((WLDFSystemResourceMBean[])this._getHelper()._extendArray(this._WLDFSystemResources, WLDFSystemResourceMBean.class, mbean)));
                  } else {
                     this.setWLDFSystemResources(new WLDFSystemResourceMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new WLDFSystemResourceMBean[0];
      }

      if (this._WLDFSystemResources != null) {
         List removeList = new ArrayList();
         WLDFSystemResourceMBean[] var18 = this._WLDFSystemResources;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            WLDFSystemResourceMBean bn = var18[var5];
            WLDFSystemResourceMBeanImpl bni = (WLDFSystemResourceMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               WLDFSystemResourceMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  WLDFSystemResourceMBean delegateTo = var10[var12];
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
            WLDFSystemResourceMBean removeIt = (WLDFSystemResourceMBean)var19.next();
            WLDFSystemResourceMBeanImpl removeItImpl = (WLDFSystemResourceMBeanImpl)removeIt;
            WLDFSystemResourceMBean[] _new = (WLDFSystemResourceMBean[])((WLDFSystemResourceMBean[])this._getHelper()._removeElement(this._WLDFSystemResources, WLDFSystemResourceMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setWLDFSystemResources(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._WLDFSystemResources;
   }

   public boolean isWLDFSystemResourcesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(22)) {
         WLDFSystemResourceMBean[] elements = this.getWLDFSystemResources();
         WLDFSystemResourceMBean[] var2 = elements;
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

   public boolean isWLDFSystemResourcesSet() {
      return this._isSet(22);
   }

   public void removeWLDFSystemResource(WLDFSystemResourceMBean param0) {
      this.destroyWLDFSystemResource(param0);
   }

   public void setWLDFSystemResources(WLDFSystemResourceMBean[] param0) throws InvalidAttributeValueException {
      WLDFSystemResourceMBean[] param0 = param0 == null ? new WLDFSystemResourceMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._WLDFSystemResources, (Object[])param0, handler, new Comparator() {
            public int compare(WLDFSystemResourceMBean o1, WLDFSystemResourceMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            WLDFSystemResourceMBean bean = (WLDFSystemResourceMBean)var3.next();
            WLDFSystemResourceMBeanImpl beanImpl = (WLDFSystemResourceMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 22)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(22);
      WLDFSystemResourceMBean[] _oldVal = this._WLDFSystemResources;
      this._WLDFSystemResources = (WLDFSystemResourceMBean[])param0;
      this._postSet(22, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public WLDFSystemResourceMBean createWLDFSystemResource(String param0) {
      WLDFSystemResourceMBeanImpl lookup = (WLDFSystemResourceMBeanImpl)this.lookupWLDFSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFSystemResourceMBeanImpl _val = new WLDFSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWLDFSystemResource(_val);
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

   public WLDFSystemResourceMBean createWLDFSystemResource(String param0, String param1) {
      WLDFSystemResourceMBeanImpl lookup = (WLDFSystemResourceMBeanImpl)this.lookupWLDFSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFSystemResourceMBeanImpl _val = new WLDFSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            _val.setDescriptorFileName(param1);
            this.addWLDFSystemResource(_val);
            return _val;
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new UndeclaredThrowableException(var6);
            }
         }
      }
   }

   public WLDFSystemResourceMBean lookupWLDFSystemResource(String param0) {
      Object[] aary = (Object[])this.getWLDFSystemResources();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFSystemResourceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFSystemResourceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyWLDFSystemResource(WLDFSystemResourceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 22);
         WLDFSystemResourceMBean[] _old = this.getWLDFSystemResources();
         WLDFSystemResourceMBean[] _new = (WLDFSystemResourceMBean[])((WLDFSystemResourceMBean[])this._getHelper()._removeElement(_old, WLDFSystemResourceMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  WLDFSystemResourceMBeanImpl childImpl = (WLDFSystemResourceMBeanImpl)_child;
                  WLDFSystemResourceMBeanImpl lookup = (WLDFSystemResourceMBeanImpl)source.lookupWLDFSystemResource(childImpl.getName());
                  if (lookup != null) {
                     source.destroyWLDFSystemResource(lookup);
                  }
               }

               this.setWLDFSystemResources(_new);
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

   public void addSAFAgent(SAFAgentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 23)) {
         SAFAgentMBean[] _new;
         if (this._isSet(23)) {
            _new = (SAFAgentMBean[])((SAFAgentMBean[])this._getHelper()._extendArray(this.getSAFAgents(), SAFAgentMBean.class, param0));
         } else {
            _new = new SAFAgentMBean[]{param0};
         }

         try {
            this.setSAFAgents(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SAFAgentMBean[] getSAFAgents() {
      SAFAgentMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(23)) {
         delegateArray = this._getDelegateBean().getSAFAgents();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._SAFAgents.length; ++j) {
               if (delegateArray[i].getName().equals(this._SAFAgents[j].getName())) {
                  ((SAFAgentMBeanImpl)this._SAFAgents[j])._setDelegateBean((SAFAgentMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  SAFAgentMBeanImpl mbean = new SAFAgentMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 23);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((SAFAgentMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(23)) {
                     this.setSAFAgents((SAFAgentMBean[])((SAFAgentMBean[])this._getHelper()._extendArray(this._SAFAgents, SAFAgentMBean.class, mbean)));
                  } else {
                     this.setSAFAgents(new SAFAgentMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new SAFAgentMBean[0];
      }

      if (this._SAFAgents != null) {
         List removeList = new ArrayList();
         SAFAgentMBean[] var18 = this._SAFAgents;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            SAFAgentMBean bn = var18[var5];
            SAFAgentMBeanImpl bni = (SAFAgentMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               SAFAgentMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  SAFAgentMBean delegateTo = var10[var12];
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
            SAFAgentMBean removeIt = (SAFAgentMBean)var19.next();
            SAFAgentMBeanImpl removeItImpl = (SAFAgentMBeanImpl)removeIt;
            SAFAgentMBean[] _new = (SAFAgentMBean[])((SAFAgentMBean[])this._getHelper()._removeElement(this._SAFAgents, SAFAgentMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setSAFAgents(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._SAFAgents;
   }

   public boolean isSAFAgentsInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(23)) {
         SAFAgentMBean[] elements = this.getSAFAgents();
         SAFAgentMBean[] var2 = elements;
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

   public boolean isSAFAgentsSet() {
      return this._isSet(23);
   }

   public void removeSAFAgent(SAFAgentMBean param0) {
      this.destroySAFAgent(param0);
   }

   public void setSAFAgents(SAFAgentMBean[] param0) throws InvalidAttributeValueException {
      SAFAgentMBean[] param0 = param0 == null ? new SAFAgentMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._SAFAgents, (Object[])param0, handler, new Comparator() {
            public int compare(SAFAgentMBean o1, SAFAgentMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            SAFAgentMBean bean = (SAFAgentMBean)var3.next();
            SAFAgentMBeanImpl beanImpl = (SAFAgentMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 23)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(23);
      SAFAgentMBean[] _oldVal = this._SAFAgents;
      this._SAFAgents = (SAFAgentMBean[])param0;
      this._postSet(23, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public SAFAgentMBean createSAFAgent(String param0) {
      SAFAgentMBeanImpl lookup = (SAFAgentMBeanImpl)this.lookupSAFAgent(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SAFAgentMBeanImpl _val = new SAFAgentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSAFAgent(_val);
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

   public void destroySAFAgent(SAFAgentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 23);
         SAFAgentMBean[] _old = this.getSAFAgents();
         SAFAgentMBean[] _new = (SAFAgentMBean[])((SAFAgentMBean[])this._getHelper()._removeElement(_old, SAFAgentMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  SAFAgentMBeanImpl childImpl = (SAFAgentMBeanImpl)_child;
                  SAFAgentMBeanImpl lookup = (SAFAgentMBeanImpl)source.lookupSAFAgent(childImpl.getName());
                  if (lookup != null) {
                     source.destroySAFAgent(lookup);
                  }
               }

               this.setSAFAgents(_new);
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

   public SAFAgentMBean lookupSAFAgent(String param0) {
      Object[] aary = (Object[])this.getSAFAgents();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SAFAgentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SAFAgentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 24)) {
         CoherenceClusterSystemResourceMBean[] _new;
         if (this._isSet(24)) {
            _new = (CoherenceClusterSystemResourceMBean[])((CoherenceClusterSystemResourceMBean[])this._getHelper()._extendArray(this.getCoherenceClusterSystemResources(), CoherenceClusterSystemResourceMBean.class, param0));
         } else {
            _new = new CoherenceClusterSystemResourceMBean[]{param0};
         }

         try {
            this.setCoherenceClusterSystemResources(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherenceClusterSystemResourceMBean[] getCoherenceClusterSystemResources() {
      CoherenceClusterSystemResourceMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(24)) {
         delegateArray = this._getDelegateBean().getCoherenceClusterSystemResources();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._CoherenceClusterSystemResources.length; ++j) {
               if (delegateArray[i].getName().equals(this._CoherenceClusterSystemResources[j].getName())) {
                  ((CoherenceClusterSystemResourceMBeanImpl)this._CoherenceClusterSystemResources[j])._setDelegateBean((CoherenceClusterSystemResourceMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  CoherenceClusterSystemResourceMBeanImpl mbean = new CoherenceClusterSystemResourceMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 24);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((CoherenceClusterSystemResourceMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(24)) {
                     this.setCoherenceClusterSystemResources((CoherenceClusterSystemResourceMBean[])((CoherenceClusterSystemResourceMBean[])this._getHelper()._extendArray(this._CoherenceClusterSystemResources, CoherenceClusterSystemResourceMBean.class, mbean)));
                  } else {
                     this.setCoherenceClusterSystemResources(new CoherenceClusterSystemResourceMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new CoherenceClusterSystemResourceMBean[0];
      }

      if (this._CoherenceClusterSystemResources != null) {
         List removeList = new ArrayList();
         CoherenceClusterSystemResourceMBean[] var18 = this._CoherenceClusterSystemResources;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            CoherenceClusterSystemResourceMBean bn = var18[var5];
            CoherenceClusterSystemResourceMBeanImpl bni = (CoherenceClusterSystemResourceMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               CoherenceClusterSystemResourceMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  CoherenceClusterSystemResourceMBean delegateTo = var10[var12];
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
            CoherenceClusterSystemResourceMBean removeIt = (CoherenceClusterSystemResourceMBean)var19.next();
            CoherenceClusterSystemResourceMBeanImpl removeItImpl = (CoherenceClusterSystemResourceMBeanImpl)removeIt;
            CoherenceClusterSystemResourceMBean[] _new = (CoherenceClusterSystemResourceMBean[])((CoherenceClusterSystemResourceMBean[])this._getHelper()._removeElement(this._CoherenceClusterSystemResources, CoherenceClusterSystemResourceMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setCoherenceClusterSystemResources(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._CoherenceClusterSystemResources;
   }

   public boolean isCoherenceClusterSystemResourcesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(24)) {
         CoherenceClusterSystemResourceMBean[] elements = this.getCoherenceClusterSystemResources();
         CoherenceClusterSystemResourceMBean[] var2 = elements;
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

   public boolean isCoherenceClusterSystemResourcesSet() {
      return this._isSet(24);
   }

   public void removeCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean param0) {
      this.destroyCoherenceClusterSystemResource(param0);
   }

   public void setCoherenceClusterSystemResources(CoherenceClusterSystemResourceMBean[] param0) throws InvalidAttributeValueException {
      CoherenceClusterSystemResourceMBean[] param0 = param0 == null ? new CoherenceClusterSystemResourceMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._CoherenceClusterSystemResources, (Object[])param0, handler, new Comparator() {
            public int compare(CoherenceClusterSystemResourceMBean o1, CoherenceClusterSystemResourceMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            CoherenceClusterSystemResourceMBean bean = (CoherenceClusterSystemResourceMBean)var3.next();
            CoherenceClusterSystemResourceMBeanImpl beanImpl = (CoherenceClusterSystemResourceMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 24)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(24);
      CoherenceClusterSystemResourceMBean[] _oldVal = this._CoherenceClusterSystemResources;
      this._CoherenceClusterSystemResources = (CoherenceClusterSystemResourceMBean[])param0;
      this._postSet(24, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public CoherenceClusterSystemResourceMBean createCoherenceClusterSystemResource(String param0) {
      CoherenceClusterSystemResourceMBeanImpl lookup = (CoherenceClusterSystemResourceMBeanImpl)this.lookupCoherenceClusterSystemResource(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherenceClusterSystemResourceMBeanImpl _val = new CoherenceClusterSystemResourceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherenceClusterSystemResource(_val);
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

   public void destroyCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 24);
         CoherenceClusterSystemResourceMBean[] _old = this.getCoherenceClusterSystemResources();
         CoherenceClusterSystemResourceMBean[] _new = (CoherenceClusterSystemResourceMBean[])((CoherenceClusterSystemResourceMBean[])this._getHelper()._removeElement(_old, CoherenceClusterSystemResourceMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  CoherenceClusterSystemResourceMBeanImpl childImpl = (CoherenceClusterSystemResourceMBeanImpl)_child;
                  CoherenceClusterSystemResourceMBeanImpl lookup = (CoherenceClusterSystemResourceMBeanImpl)source.lookupCoherenceClusterSystemResource(childImpl.getName());
                  if (lookup != null) {
                     source.destroyCoherenceClusterSystemResource(lookup);
                  }
               }

               this.setCoherenceClusterSystemResources(_new);
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

   public CoherenceClusterSystemResourceMBean lookupCoherenceClusterSystemResource(String param0) {
      Object[] aary = (Object[])this.getCoherenceClusterSystemResources();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherenceClusterSystemResourceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherenceClusterSystemResourceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addOsgiFramework(OsgiFrameworkMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 25)) {
         OsgiFrameworkMBean[] _new;
         if (this._isSet(25)) {
            _new = (OsgiFrameworkMBean[])((OsgiFrameworkMBean[])this._getHelper()._extendArray(this.getOsgiFrameworks(), OsgiFrameworkMBean.class, param0));
         } else {
            _new = new OsgiFrameworkMBean[]{param0};
         }

         try {
            this.setOsgiFrameworks(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public OsgiFrameworkMBean[] getOsgiFrameworks() {
      OsgiFrameworkMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(25)) {
         delegateArray = this._getDelegateBean().getOsgiFrameworks();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._OsgiFrameworks.length; ++j) {
               if (delegateArray[i].getName().equals(this._OsgiFrameworks[j].getName())) {
                  ((OsgiFrameworkMBeanImpl)this._OsgiFrameworks[j])._setDelegateBean((OsgiFrameworkMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  OsgiFrameworkMBeanImpl mbean = new OsgiFrameworkMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 25);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((OsgiFrameworkMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(25)) {
                     this.setOsgiFrameworks((OsgiFrameworkMBean[])((OsgiFrameworkMBean[])this._getHelper()._extendArray(this._OsgiFrameworks, OsgiFrameworkMBean.class, mbean)));
                  } else {
                     this.setOsgiFrameworks(new OsgiFrameworkMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new OsgiFrameworkMBean[0];
      }

      if (this._OsgiFrameworks != null) {
         List removeList = new ArrayList();
         OsgiFrameworkMBean[] var18 = this._OsgiFrameworks;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            OsgiFrameworkMBean bn = var18[var5];
            OsgiFrameworkMBeanImpl bni = (OsgiFrameworkMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               OsgiFrameworkMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  OsgiFrameworkMBean delegateTo = var10[var12];
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
            OsgiFrameworkMBean removeIt = (OsgiFrameworkMBean)var19.next();
            OsgiFrameworkMBeanImpl removeItImpl = (OsgiFrameworkMBeanImpl)removeIt;
            OsgiFrameworkMBean[] _new = (OsgiFrameworkMBean[])((OsgiFrameworkMBean[])this._getHelper()._removeElement(this._OsgiFrameworks, OsgiFrameworkMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setOsgiFrameworks(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._OsgiFrameworks;
   }

   public boolean isOsgiFrameworksInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(25)) {
         OsgiFrameworkMBean[] elements = this.getOsgiFrameworks();
         OsgiFrameworkMBean[] var2 = elements;
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

   public boolean isOsgiFrameworksSet() {
      return this._isSet(25);
   }

   public void removeOsgiFramework(OsgiFrameworkMBean param0) {
      this.destroyOsgiFramework(param0);
   }

   public void setOsgiFrameworks(OsgiFrameworkMBean[] param0) throws InvalidAttributeValueException {
      OsgiFrameworkMBean[] param0 = param0 == null ? new OsgiFrameworkMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._OsgiFrameworks, (Object[])param0, handler, new Comparator() {
            public int compare(OsgiFrameworkMBean o1, OsgiFrameworkMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            OsgiFrameworkMBean bean = (OsgiFrameworkMBean)var3.next();
            OsgiFrameworkMBeanImpl beanImpl = (OsgiFrameworkMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 25)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(25);
      OsgiFrameworkMBean[] _oldVal = this._OsgiFrameworks;
      this._OsgiFrameworks = (OsgiFrameworkMBean[])param0;
      this._postSet(25, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public OsgiFrameworkMBean lookupOsgiFramework(String param0) {
      Object[] aary = (Object[])this.getOsgiFrameworks();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      OsgiFrameworkMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (OsgiFrameworkMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public OsgiFrameworkMBean createOsgiFramework(String param0) {
      OsgiFrameworkMBeanImpl lookup = (OsgiFrameworkMBeanImpl)this.lookupOsgiFramework(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         OsgiFrameworkMBeanImpl _val = new OsgiFrameworkMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addOsgiFramework(_val);
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

   public void destroyOsgiFramework(OsgiFrameworkMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 25);
         OsgiFrameworkMBean[] _old = this.getOsgiFrameworks();
         OsgiFrameworkMBean[] _new = (OsgiFrameworkMBean[])((OsgiFrameworkMBean[])this._getHelper()._removeElement(_old, OsgiFrameworkMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  OsgiFrameworkMBeanImpl childImpl = (OsgiFrameworkMBeanImpl)_child;
                  OsgiFrameworkMBeanImpl lookup = (OsgiFrameworkMBeanImpl)source.lookupOsgiFramework(childImpl.getName());
                  if (lookup != null) {
                     source.destroyOsgiFramework(lookup);
                  }
               }

               this.setOsgiFrameworks(_new);
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

   public void processResources(GeneralResourceProcessor param0) {
      try {
         this._customizer.processResources(param0);
      } catch (InvalidAttributeValueException var3) {
         throw new UndeclaredThrowableException(var3);
      } catch (ManagementException var4) {
         throw new UndeclaredThrowableException(var4);
      }
   }

   public void processResources(DiscreteResourceProcessor param0) {
      try {
         this._customizer.processResources(param0);
      } catch (ManagementException var3) {
         throw new UndeclaredThrowableException(var3);
      } catch (InvalidAttributeValueException var4) {
         throw new UndeclaredThrowableException(var4);
      }
   }

   public void addManagedExecutorService(ManagedExecutorServiceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 26)) {
         ManagedExecutorServiceMBean[] _new;
         if (this._isSet(26)) {
            _new = (ManagedExecutorServiceMBean[])((ManagedExecutorServiceMBean[])this._getHelper()._extendArray(this.getManagedExecutorServices(), ManagedExecutorServiceMBean.class, param0));
         } else {
            _new = new ManagedExecutorServiceMBean[]{param0};
         }

         try {
            this.setManagedExecutorServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedExecutorServiceMBean[] getManagedExecutorServices() {
      ManagedExecutorServiceMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(26)) {
         delegateArray = this._getDelegateBean().getManagedExecutorServices();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._ManagedExecutorServices.length; ++j) {
               if (delegateArray[i].getName().equals(this._ManagedExecutorServices[j].getName())) {
                  ((ManagedExecutorServiceMBeanImpl)this._ManagedExecutorServices[j])._setDelegateBean((ManagedExecutorServiceMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  ManagedExecutorServiceMBeanImpl mbean = new ManagedExecutorServiceMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 26);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((ManagedExecutorServiceMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(26)) {
                     this.setManagedExecutorServices((ManagedExecutorServiceMBean[])((ManagedExecutorServiceMBean[])this._getHelper()._extendArray(this._ManagedExecutorServices, ManagedExecutorServiceMBean.class, mbean)));
                  } else {
                     this.setManagedExecutorServices(new ManagedExecutorServiceMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new ManagedExecutorServiceMBean[0];
      }

      if (this._ManagedExecutorServices != null) {
         List removeList = new ArrayList();
         ManagedExecutorServiceMBean[] var18 = this._ManagedExecutorServices;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            ManagedExecutorServiceMBean bn = var18[var5];
            ManagedExecutorServiceMBeanImpl bni = (ManagedExecutorServiceMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               ManagedExecutorServiceMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  ManagedExecutorServiceMBean delegateTo = var10[var12];
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
            ManagedExecutorServiceMBean removeIt = (ManagedExecutorServiceMBean)var19.next();
            ManagedExecutorServiceMBeanImpl removeItImpl = (ManagedExecutorServiceMBeanImpl)removeIt;
            ManagedExecutorServiceMBean[] _new = (ManagedExecutorServiceMBean[])((ManagedExecutorServiceMBean[])this._getHelper()._removeElement(this._ManagedExecutorServices, ManagedExecutorServiceMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setManagedExecutorServices(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._ManagedExecutorServices;
   }

   public boolean isManagedExecutorServicesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(26)) {
         ManagedExecutorServiceMBean[] elements = this.getManagedExecutorServices();
         ManagedExecutorServiceMBean[] var2 = elements;
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

   public boolean isManagedExecutorServicesSet() {
      return this._isSet(26);
   }

   public void removeManagedExecutorService(ManagedExecutorServiceMBean param0) {
      this.destroyManagedExecutorService(param0);
   }

   public void setManagedExecutorServices(ManagedExecutorServiceMBean[] param0) throws InvalidAttributeValueException {
      ManagedExecutorServiceMBean[] param0 = param0 == null ? new ManagedExecutorServiceMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._ManagedExecutorServices, (Object[])param0, handler, new Comparator() {
            public int compare(ManagedExecutorServiceMBean o1, ManagedExecutorServiceMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            ManagedExecutorServiceMBean bean = (ManagedExecutorServiceMBean)var3.next();
            ManagedExecutorServiceMBeanImpl beanImpl = (ManagedExecutorServiceMBeanImpl)bean;
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
      ManagedExecutorServiceMBean[] _oldVal = this._ManagedExecutorServices;
      this._ManagedExecutorServices = (ManagedExecutorServiceMBean[])param0;
      this._postSet(26, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public ManagedExecutorServiceMBean createManagedExecutorService(String param0) {
      ManagedExecutorServiceMBeanImpl lookup = (ManagedExecutorServiceMBeanImpl)this.lookupManagedExecutorService(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ManagedExecutorServiceMBeanImpl _val = new ManagedExecutorServiceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addManagedExecutorService(_val);
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

   public void destroyManagedExecutorService(ManagedExecutorServiceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 26);
         ManagedExecutorServiceMBean[] _old = this.getManagedExecutorServices();
         ManagedExecutorServiceMBean[] _new = (ManagedExecutorServiceMBean[])((ManagedExecutorServiceMBean[])this._getHelper()._removeElement(_old, ManagedExecutorServiceMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  ManagedExecutorServiceMBeanImpl childImpl = (ManagedExecutorServiceMBeanImpl)_child;
                  ManagedExecutorServiceMBeanImpl lookup = (ManagedExecutorServiceMBeanImpl)source.lookupManagedExecutorService(childImpl.getName());
                  if (lookup != null) {
                     source.destroyManagedExecutorService(lookup);
                  }
               }

               this.setManagedExecutorServices(_new);
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

   public ManagedExecutorServiceMBean lookupManagedExecutorService(String param0) {
      Object[] aary = (Object[])this.getManagedExecutorServices();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ManagedExecutorServiceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ManagedExecutorServiceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addManagedScheduledExecutorService(ManagedScheduledExecutorServiceMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 27)) {
         ManagedScheduledExecutorServiceMBean[] _new;
         if (this._isSet(27)) {
            _new = (ManagedScheduledExecutorServiceMBean[])((ManagedScheduledExecutorServiceMBean[])this._getHelper()._extendArray(this.getManagedScheduledExecutorServices(), ManagedScheduledExecutorServiceMBean.class, param0));
         } else {
            _new = new ManagedScheduledExecutorServiceMBean[]{param0};
         }

         try {
            this.setManagedScheduledExecutorServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedScheduledExecutorServiceMBean[] getManagedScheduledExecutorServices() {
      ManagedScheduledExecutorServiceMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(27)) {
         delegateArray = this._getDelegateBean().getManagedScheduledExecutorServices();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._ManagedScheduledExecutorServices.length; ++j) {
               if (delegateArray[i].getName().equals(this._ManagedScheduledExecutorServices[j].getName())) {
                  ((ManagedScheduledExecutorServiceMBeanImpl)this._ManagedScheduledExecutorServices[j])._setDelegateBean((ManagedScheduledExecutorServiceMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  ManagedScheduledExecutorServiceMBeanImpl mbean = new ManagedScheduledExecutorServiceMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 27);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((ManagedScheduledExecutorServiceMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(27)) {
                     this.setManagedScheduledExecutorServices((ManagedScheduledExecutorServiceMBean[])((ManagedScheduledExecutorServiceMBean[])this._getHelper()._extendArray(this._ManagedScheduledExecutorServices, ManagedScheduledExecutorServiceMBean.class, mbean)));
                  } else {
                     this.setManagedScheduledExecutorServices(new ManagedScheduledExecutorServiceMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new ManagedScheduledExecutorServiceMBean[0];
      }

      if (this._ManagedScheduledExecutorServices != null) {
         List removeList = new ArrayList();
         ManagedScheduledExecutorServiceMBean[] var18 = this._ManagedScheduledExecutorServices;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            ManagedScheduledExecutorServiceMBean bn = var18[var5];
            ManagedScheduledExecutorServiceMBeanImpl bni = (ManagedScheduledExecutorServiceMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               ManagedScheduledExecutorServiceMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  ManagedScheduledExecutorServiceMBean delegateTo = var10[var12];
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
            ManagedScheduledExecutorServiceMBean removeIt = (ManagedScheduledExecutorServiceMBean)var19.next();
            ManagedScheduledExecutorServiceMBeanImpl removeItImpl = (ManagedScheduledExecutorServiceMBeanImpl)removeIt;
            ManagedScheduledExecutorServiceMBean[] _new = (ManagedScheduledExecutorServiceMBean[])((ManagedScheduledExecutorServiceMBean[])this._getHelper()._removeElement(this._ManagedScheduledExecutorServices, ManagedScheduledExecutorServiceMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setManagedScheduledExecutorServices(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._ManagedScheduledExecutorServices;
   }

   public boolean isManagedScheduledExecutorServicesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(27)) {
         ManagedScheduledExecutorServiceMBean[] elements = this.getManagedScheduledExecutorServices();
         ManagedScheduledExecutorServiceMBean[] var2 = elements;
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

   public boolean isManagedScheduledExecutorServicesSet() {
      return this._isSet(27);
   }

   public void removeManagedScheduledExecutorService(ManagedScheduledExecutorServiceMBean param0) {
      this.destroyManagedScheduledExecutorService(param0);
   }

   public void setManagedScheduledExecutorServices(ManagedScheduledExecutorServiceMBean[] param0) throws InvalidAttributeValueException {
      ManagedScheduledExecutorServiceMBean[] param0 = param0 == null ? new ManagedScheduledExecutorServiceMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._ManagedScheduledExecutorServices, (Object[])param0, handler, new Comparator() {
            public int compare(ManagedScheduledExecutorServiceMBean o1, ManagedScheduledExecutorServiceMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            ManagedScheduledExecutorServiceMBean bean = (ManagedScheduledExecutorServiceMBean)var3.next();
            ManagedScheduledExecutorServiceMBeanImpl beanImpl = (ManagedScheduledExecutorServiceMBeanImpl)bean;
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
      ManagedScheduledExecutorServiceMBean[] _oldVal = this._ManagedScheduledExecutorServices;
      this._ManagedScheduledExecutorServices = (ManagedScheduledExecutorServiceMBean[])param0;
      this._postSet(27, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public ManagedScheduledExecutorServiceMBean createManagedScheduledExecutorService(String param0) {
      ManagedScheduledExecutorServiceMBeanImpl lookup = (ManagedScheduledExecutorServiceMBeanImpl)this.lookupManagedScheduledExecutorService(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ManagedScheduledExecutorServiceMBeanImpl _val = new ManagedScheduledExecutorServiceMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addManagedScheduledExecutorService(_val);
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

   public void destroyManagedScheduledExecutorService(ManagedScheduledExecutorServiceMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 27);
         ManagedScheduledExecutorServiceMBean[] _old = this.getManagedScheduledExecutorServices();
         ManagedScheduledExecutorServiceMBean[] _new = (ManagedScheduledExecutorServiceMBean[])((ManagedScheduledExecutorServiceMBean[])this._getHelper()._removeElement(_old, ManagedScheduledExecutorServiceMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  ManagedScheduledExecutorServiceMBeanImpl childImpl = (ManagedScheduledExecutorServiceMBeanImpl)_child;
                  ManagedScheduledExecutorServiceMBeanImpl lookup = (ManagedScheduledExecutorServiceMBeanImpl)source.lookupManagedScheduledExecutorService(childImpl.getName());
                  if (lookup != null) {
                     source.destroyManagedScheduledExecutorService(lookup);
                  }
               }

               this.setManagedScheduledExecutorServices(_new);
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

   public ManagedScheduledExecutorServiceMBean lookupManagedScheduledExecutorService(String param0) {
      Object[] aary = (Object[])this.getManagedScheduledExecutorServices();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ManagedScheduledExecutorServiceMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ManagedScheduledExecutorServiceMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addManagedThreadFactory(ManagedThreadFactoryMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 28)) {
         ManagedThreadFactoryMBean[] _new;
         if (this._isSet(28)) {
            _new = (ManagedThreadFactoryMBean[])((ManagedThreadFactoryMBean[])this._getHelper()._extendArray(this.getManagedThreadFactories(), ManagedThreadFactoryMBean.class, param0));
         } else {
            _new = new ManagedThreadFactoryMBean[]{param0};
         }

         try {
            this.setManagedThreadFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedThreadFactoryMBean[] getManagedThreadFactories() {
      ManagedThreadFactoryMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(28)) {
         delegateArray = this._getDelegateBean().getManagedThreadFactories();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._ManagedThreadFactories.length; ++j) {
               if (delegateArray[i].getName().equals(this._ManagedThreadFactories[j].getName())) {
                  ((ManagedThreadFactoryMBeanImpl)this._ManagedThreadFactories[j])._setDelegateBean((ManagedThreadFactoryMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  ManagedThreadFactoryMBeanImpl mbean = new ManagedThreadFactoryMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 28);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((ManagedThreadFactoryMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(28)) {
                     this.setManagedThreadFactories((ManagedThreadFactoryMBean[])((ManagedThreadFactoryMBean[])this._getHelper()._extendArray(this._ManagedThreadFactories, ManagedThreadFactoryMBean.class, mbean)));
                  } else {
                     this.setManagedThreadFactories(new ManagedThreadFactoryMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new ManagedThreadFactoryMBean[0];
      }

      if (this._ManagedThreadFactories != null) {
         List removeList = new ArrayList();
         ManagedThreadFactoryMBean[] var18 = this._ManagedThreadFactories;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            ManagedThreadFactoryMBean bn = var18[var5];
            ManagedThreadFactoryMBeanImpl bni = (ManagedThreadFactoryMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               ManagedThreadFactoryMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  ManagedThreadFactoryMBean delegateTo = var10[var12];
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
            ManagedThreadFactoryMBean removeIt = (ManagedThreadFactoryMBean)var19.next();
            ManagedThreadFactoryMBeanImpl removeItImpl = (ManagedThreadFactoryMBeanImpl)removeIt;
            ManagedThreadFactoryMBean[] _new = (ManagedThreadFactoryMBean[])((ManagedThreadFactoryMBean[])this._getHelper()._removeElement(this._ManagedThreadFactories, ManagedThreadFactoryMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setManagedThreadFactories(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._ManagedThreadFactories;
   }

   public boolean isManagedThreadFactoriesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(28)) {
         ManagedThreadFactoryMBean[] elements = this.getManagedThreadFactories();
         ManagedThreadFactoryMBean[] var2 = elements;
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

   public boolean isManagedThreadFactoriesSet() {
      return this._isSet(28);
   }

   public void removeManagedThreadFactory(ManagedThreadFactoryMBean param0) {
      this.destroyManagedThreadFactory(param0);
   }

   public void setManagedThreadFactories(ManagedThreadFactoryMBean[] param0) throws InvalidAttributeValueException {
      ManagedThreadFactoryMBean[] param0 = param0 == null ? new ManagedThreadFactoryMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._ManagedThreadFactories, (Object[])param0, handler, new Comparator() {
            public int compare(ManagedThreadFactoryMBean o1, ManagedThreadFactoryMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            ManagedThreadFactoryMBean bean = (ManagedThreadFactoryMBean)var3.next();
            ManagedThreadFactoryMBeanImpl beanImpl = (ManagedThreadFactoryMBeanImpl)bean;
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
      ManagedThreadFactoryMBean[] _oldVal = this._ManagedThreadFactories;
      this._ManagedThreadFactories = (ManagedThreadFactoryMBean[])param0;
      this._postSet(28, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var11.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public ManagedThreadFactoryMBean createManagedThreadFactory(String param0) {
      ManagedThreadFactoryMBeanImpl lookup = (ManagedThreadFactoryMBeanImpl)this.lookupManagedThreadFactory(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ManagedThreadFactoryMBeanImpl _val = new ManagedThreadFactoryMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addManagedThreadFactory(_val);
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

   public void destroyManagedThreadFactory(ManagedThreadFactoryMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 28);
         ManagedThreadFactoryMBean[] _old = this.getManagedThreadFactories();
         ManagedThreadFactoryMBean[] _new = (ManagedThreadFactoryMBean[])((ManagedThreadFactoryMBean[])this._getHelper()._removeElement(_old, ManagedThreadFactoryMBean.class, param0));
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
                  ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var6.next();
                  ManagedThreadFactoryMBeanImpl childImpl = (ManagedThreadFactoryMBeanImpl)_child;
                  ManagedThreadFactoryMBeanImpl lookup = (ManagedThreadFactoryMBeanImpl)source.lookupManagedThreadFactory(childImpl.getName());
                  if (lookup != null) {
                     source.destroyManagedThreadFactory(lookup);
                  }
               }

               this.setManagedThreadFactories(_new);
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

   public ManagedThreadFactoryMBean lookupManagedThreadFactory(String param0) {
      Object[] aary = (Object[])this.getManagedThreadFactories();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ManagedThreadFactoryMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ManagedThreadFactoryMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public boolean isImMutable() {
      return this._ImMutable;
   }

   public boolean isImMutableInherited() {
      return false;
   }

   public boolean isImMutableSet() {
      return this._isSet(29);
   }

   public void setImMutable(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      boolean _oldVal = this._ImMutable;
      this._ImMutable = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public String getResourceGroupTemplateID() {
      return this._ResourceGroupTemplateID;
   }

   public boolean isResourceGroupTemplateIDInherited() {
      return false;
   }

   public boolean isResourceGroupTemplateIDSet() {
      return this._isSet(30);
   }

   public void setResourceGroupTemplateID(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      String _oldVal = this._ResourceGroupTemplateID;
      this._ResourceGroupTemplateID = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public String getUploadDirectoryName() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._performMacroSubstitution(this._getDelegateBean().getUploadDirectoryName(), this) : this._customizer.getUploadDirectoryName();
   }

   public boolean isUploadDirectoryNameInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isUploadDirectoryNameSet() {
      return this._isSet(31);
   }

   public void setUploadDirectoryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      String _oldVal = this.getUploadDirectoryName();
      this._customizer.setUploadDirectoryName(param0);
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ResourceGroupTemplateMBeanImpl source = (ResourceGroupTemplateMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _preDestroy() {
      this._customizer._preDestroy();
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._AppDeployments = new AppDeploymentMBean[0];
               if (initOne) {
                  break;
               }
            case 24:
               this._CoherenceClusterSystemResources = new CoherenceClusterSystemResourceMBean[0];
               if (initOne) {
                  break;
               }
            case 17:
               this._FileStores = new FileStoreMBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._ForeignJNDIProviders = new ForeignJNDIProviderMBean[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._JDBCStores = new JDBCStoreMBean[0];
               if (initOne) {
                  break;
               }
            case 21:
               this._JDBCSystemResources = new JDBCSystemResourceMBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._JMSBridgeDestinations = new JMSBridgeDestinationMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._JMSServers = new JMSServerMBean[0];
               if (initOne) {
                  break;
               }
            case 19:
               this._JMSSystemResources = new JMSSystemResourceMBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._Libraries = new LibraryMBean[0];
               if (initOne) {
                  break;
               }
            case 16:
               this._MailSessions = new MailSessionMBean[0];
               if (initOne) {
                  break;
               }
            case 26:
               this._ManagedExecutorServices = new ManagedExecutorServiceMBean[0];
               if (initOne) {
                  break;
               }
            case 27:
               this._ManagedScheduledExecutorServices = new ManagedScheduledExecutorServiceMBean[0];
               if (initOne) {
                  break;
               }
            case 28:
               this._ManagedThreadFactories = new ManagedThreadFactoryMBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._MessagingBridges = new MessagingBridgeMBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 25:
               this._OsgiFrameworks = new OsgiFrameworkMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._PathServices = new PathServiceMBean[0];
               if (initOne) {
                  break;
               }
            case 30:
               this._ResourceGroupTemplateID = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._SAFAgents = new SAFAgentMBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 31:
               this._customizer.setUploadDirectoryName((String)null);
               if (initOne) {
                  break;
               }
            case 22:
               this._WLDFSystemResources = new WLDFSystemResourceMBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 29:
               this._ImMutable = false;
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
      return "ResourceGroupTemplate";
   }

   public void putValue(String name, Object v) {
      if (name.equals("AppDeployments")) {
         AppDeploymentMBean[] oldVal = this._AppDeployments;
         this._AppDeployments = (AppDeploymentMBean[])((AppDeploymentMBean[])v);
         this._postSet(10, oldVal, this._AppDeployments);
      } else if (name.equals("CoherenceClusterSystemResources")) {
         CoherenceClusterSystemResourceMBean[] oldVal = this._CoherenceClusterSystemResources;
         this._CoherenceClusterSystemResources = (CoherenceClusterSystemResourceMBean[])((CoherenceClusterSystemResourceMBean[])v);
         this._postSet(24, oldVal, this._CoherenceClusterSystemResources);
      } else {
         boolean oldVal;
         if (name.equals("DynamicallyCreated")) {
            oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else if (name.equals("FileStores")) {
            FileStoreMBean[] oldVal = this._FileStores;
            this._FileStores = (FileStoreMBean[])((FileStoreMBean[])v);
            this._postSet(17, oldVal, this._FileStores);
         } else if (name.equals("ForeignJNDIProviders")) {
            ForeignJNDIProviderMBean[] oldVal = this._ForeignJNDIProviders;
            this._ForeignJNDIProviders = (ForeignJNDIProviderMBean[])((ForeignJNDIProviderMBean[])v);
            this._postSet(20, oldVal, this._ForeignJNDIProviders);
         } else if (name.equals("ImMutable")) {
            oldVal = this._ImMutable;
            this._ImMutable = (Boolean)v;
            this._postSet(29, oldVal, this._ImMutable);
         } else if (name.equals("JDBCStores")) {
            JDBCStoreMBean[] oldVal = this._JDBCStores;
            this._JDBCStores = (JDBCStoreMBean[])((JDBCStoreMBean[])v);
            this._postSet(18, oldVal, this._JDBCStores);
         } else if (name.equals("JDBCSystemResources")) {
            JDBCSystemResourceMBean[] oldVal = this._JDBCSystemResources;
            this._JDBCSystemResources = (JDBCSystemResourceMBean[])((JDBCSystemResourceMBean[])v);
            this._postSet(21, oldVal, this._JDBCSystemResources);
         } else if (name.equals("JMSBridgeDestinations")) {
            JMSBridgeDestinationMBean[] oldVal = this._JMSBridgeDestinations;
            this._JMSBridgeDestinations = (JMSBridgeDestinationMBean[])((JMSBridgeDestinationMBean[])v);
            this._postSet(15, oldVal, this._JMSBridgeDestinations);
         } else if (name.equals("JMSServers")) {
            JMSServerMBean[] oldVal = this._JMSServers;
            this._JMSServers = (JMSServerMBean[])((JMSServerMBean[])v);
            this._postSet(12, oldVal, this._JMSServers);
         } else if (name.equals("JMSSystemResources")) {
            JMSSystemResourceMBean[] oldVal = this._JMSSystemResources;
            this._JMSSystemResources = (JMSSystemResourceMBean[])((JMSSystemResourceMBean[])v);
            this._postSet(19, oldVal, this._JMSSystemResources);
         } else if (name.equals("Libraries")) {
            LibraryMBean[] oldVal = this._Libraries;
            this._Libraries = (LibraryMBean[])((LibraryMBean[])v);
            this._postSet(11, oldVal, this._Libraries);
         } else if (name.equals("MailSessions")) {
            MailSessionMBean[] oldVal = this._MailSessions;
            this._MailSessions = (MailSessionMBean[])((MailSessionMBean[])v);
            this._postSet(16, oldVal, this._MailSessions);
         } else if (name.equals("ManagedExecutorServices")) {
            ManagedExecutorServiceMBean[] oldVal = this._ManagedExecutorServices;
            this._ManagedExecutorServices = (ManagedExecutorServiceMBean[])((ManagedExecutorServiceMBean[])v);
            this._postSet(26, oldVal, this._ManagedExecutorServices);
         } else if (name.equals("ManagedScheduledExecutorServices")) {
            ManagedScheduledExecutorServiceMBean[] oldVal = this._ManagedScheduledExecutorServices;
            this._ManagedScheduledExecutorServices = (ManagedScheduledExecutorServiceMBean[])((ManagedScheduledExecutorServiceMBean[])v);
            this._postSet(27, oldVal, this._ManagedScheduledExecutorServices);
         } else if (name.equals("ManagedThreadFactories")) {
            ManagedThreadFactoryMBean[] oldVal = this._ManagedThreadFactories;
            this._ManagedThreadFactories = (ManagedThreadFactoryMBean[])((ManagedThreadFactoryMBean[])v);
            this._postSet(28, oldVal, this._ManagedThreadFactories);
         } else if (name.equals("MessagingBridges")) {
            MessagingBridgeMBean[] oldVal = this._MessagingBridges;
            this._MessagingBridges = (MessagingBridgeMBean[])((MessagingBridgeMBean[])v);
            this._postSet(13, oldVal, this._MessagingBridges);
         } else {
            String oldVal;
            if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("OsgiFrameworks")) {
               OsgiFrameworkMBean[] oldVal = this._OsgiFrameworks;
               this._OsgiFrameworks = (OsgiFrameworkMBean[])((OsgiFrameworkMBean[])v);
               this._postSet(25, oldVal, this._OsgiFrameworks);
            } else if (name.equals("PathServices")) {
               PathServiceMBean[] oldVal = this._PathServices;
               this._PathServices = (PathServiceMBean[])((PathServiceMBean[])v);
               this._postSet(14, oldVal, this._PathServices);
            } else if (name.equals("ResourceGroupTemplateID")) {
               oldVal = this._ResourceGroupTemplateID;
               this._ResourceGroupTemplateID = (String)v;
               this._postSet(30, oldVal, this._ResourceGroupTemplateID);
            } else if (name.equals("SAFAgents")) {
               SAFAgentMBean[] oldVal = this._SAFAgents;
               this._SAFAgents = (SAFAgentMBean[])((SAFAgentMBean[])v);
               this._postSet(23, oldVal, this._SAFAgents);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("UploadDirectoryName")) {
               oldVal = this._UploadDirectoryName;
               this._UploadDirectoryName = (String)v;
               this._postSet(31, oldVal, this._UploadDirectoryName);
            } else if (name.equals("WLDFSystemResources")) {
               WLDFSystemResourceMBean[] oldVal = this._WLDFSystemResources;
               this._WLDFSystemResources = (WLDFSystemResourceMBean[])((WLDFSystemResourceMBean[])v);
               this._postSet(22, oldVal, this._WLDFSystemResources);
            } else if (name.equals("customizer")) {
               ResourceGroupTemplate oldVal = this._customizer;
               this._customizer = (ResourceGroupTemplate)v;
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AppDeployments")) {
         return this._AppDeployments;
      } else if (name.equals("CoherenceClusterSystemResources")) {
         return this._CoherenceClusterSystemResources;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("FileStores")) {
         return this._FileStores;
      } else if (name.equals("ForeignJNDIProviders")) {
         return this._ForeignJNDIProviders;
      } else if (name.equals("ImMutable")) {
         return new Boolean(this._ImMutable);
      } else if (name.equals("JDBCStores")) {
         return this._JDBCStores;
      } else if (name.equals("JDBCSystemResources")) {
         return this._JDBCSystemResources;
      } else if (name.equals("JMSBridgeDestinations")) {
         return this._JMSBridgeDestinations;
      } else if (name.equals("JMSServers")) {
         return this._JMSServers;
      } else if (name.equals("JMSSystemResources")) {
         return this._JMSSystemResources;
      } else if (name.equals("Libraries")) {
         return this._Libraries;
      } else if (name.equals("MailSessions")) {
         return this._MailSessions;
      } else if (name.equals("ManagedExecutorServices")) {
         return this._ManagedExecutorServices;
      } else if (name.equals("ManagedScheduledExecutorServices")) {
         return this._ManagedScheduledExecutorServices;
      } else if (name.equals("ManagedThreadFactories")) {
         return this._ManagedThreadFactories;
      } else if (name.equals("MessagingBridges")) {
         return this._MessagingBridges;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("OsgiFrameworks")) {
         return this._OsgiFrameworks;
      } else if (name.equals("PathServices")) {
         return this._PathServices;
      } else if (name.equals("ResourceGroupTemplateID")) {
         return this._ResourceGroupTemplateID;
      } else if (name.equals("SAFAgents")) {
         return this._SAFAgents;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("UploadDirectoryName")) {
         return this._UploadDirectoryName;
      } else if (name.equals("WLDFSystemResources")) {
         return this._WLDFSystemResources;
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
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 6:
            case 8:
            case 11:
            case 13:
            case 15:
            case 17:
            case 18:
            case 23:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            default:
               break;
            case 7:
               if (s.equals("library")) {
                  return 11;
               }
               break;
            case 9:
               if (s.equals("saf-agent")) {
                  return 23;
               }

               if (s.equals("immutable")) {
                  return 29;
               }
               break;
            case 10:
               if (s.equals("file-store")) {
                  return 17;
               }

               if (s.equals("jdbc-store")) {
                  return 18;
               }

               if (s.equals("jms-server")) {
                  return 12;
               }
               break;
            case 12:
               if (s.equals("mail-session")) {
                  return 16;
               }

               if (s.equals("path-service")) {
                  return 14;
               }
               break;
            case 14:
               if (s.equals("app-deployment")) {
                  return 10;
               }

               if (s.equals("osgi-framework")) {
                  return 25;
               }
               break;
            case 16:
               if (s.equals("messaging-bridge")) {
                  return 13;
               }
               break;
            case 19:
               if (s.equals("jms-system-resource")) {
                  return 19;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("jdbc-system-resource")) {
                  return 21;
               }

               if (s.equals("wldf-system-resource")) {
                  return 22;
               }
               break;
            case 21:
               if (s.equals("foreign-jndi-provider")) {
                  return 20;
               }

               if (s.equals("upload-directory-name")) {
                  return 31;
               }
               break;
            case 22:
               if (s.equals("jms-bridge-destination")) {
                  return 15;
               }

               if (s.equals("managed-thread-factory")) {
                  return 28;
               }
               break;
            case 24:
               if (s.equals("managed-executor-service")) {
                  return 26;
               }
               break;
            case 25:
               if (s.equals("resource-group-templateid")) {
                  return 30;
               }
               break;
            case 33:
               if (s.equals("coherence-cluster-system-resource")) {
                  return 24;
               }
               break;
            case 34:
               if (s.equals("managed-scheduled-executor-service")) {
                  return 27;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new AppDeploymentMBeanImpl.SchemaHelper2();
            case 11:
               return new LibraryMBeanImpl.SchemaHelper2();
            case 12:
               return new JMSServerMBeanImpl.SchemaHelper2();
            case 13:
               return new MessagingBridgeMBeanImpl.SchemaHelper2();
            case 14:
               return new PathServiceMBeanImpl.SchemaHelper2();
            case 15:
               return new JMSBridgeDestinationMBeanImpl.SchemaHelper2();
            case 16:
               return new MailSessionMBeanImpl.SchemaHelper2();
            case 17:
               return new FileStoreMBeanImpl.SchemaHelper2();
            case 18:
               return new JDBCStoreMBeanImpl.SchemaHelper2();
            case 19:
               return new JMSSystemResourceMBeanImpl.SchemaHelper2();
            case 20:
               return new ForeignJNDIProviderMBeanImpl.SchemaHelper2();
            case 21:
               return new JDBCSystemResourceMBeanImpl.SchemaHelper2();
            case 22:
               return new WLDFSystemResourceMBeanImpl.SchemaHelper2();
            case 23:
               return new SAFAgentMBeanImpl.SchemaHelper2();
            case 24:
               return new CoherenceClusterSystemResourceMBeanImpl.SchemaHelper2();
            case 25:
               return new OsgiFrameworkMBeanImpl.SchemaHelper2();
            case 26:
               return new ManagedExecutorServiceMBeanImpl.SchemaHelper2();
            case 27:
               return new ManagedScheduledExecutorServiceMBeanImpl.SchemaHelper2();
            case 28:
               return new ManagedThreadFactoryMBeanImpl.SchemaHelper2();
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
               return "app-deployment";
            case 11:
               return "library";
            case 12:
               return "jms-server";
            case 13:
               return "messaging-bridge";
            case 14:
               return "path-service";
            case 15:
               return "jms-bridge-destination";
            case 16:
               return "mail-session";
            case 17:
               return "file-store";
            case 18:
               return "jdbc-store";
            case 19:
               return "jms-system-resource";
            case 20:
               return "foreign-jndi-provider";
            case 21:
               return "jdbc-system-resource";
            case 22:
               return "wldf-system-resource";
            case 23:
               return "saf-agent";
            case 24:
               return "coherence-cluster-system-resource";
            case 25:
               return "osgi-framework";
            case 26:
               return "managed-executor-service";
            case 27:
               return "managed-scheduled-executor-service";
            case 28:
               return "managed-thread-factory";
            case 29:
               return "immutable";
            case 30:
               return "resource-group-templateid";
            case 31:
               return "upload-directory-name";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
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
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
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
            case 28:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
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
            case 28:
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
      private ResourceGroupTemplateMBeanImpl bean;

      protected Helper(ResourceGroupTemplateMBeanImpl bean) {
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
               return "AppDeployments";
            case 11:
               return "Libraries";
            case 12:
               return "JMSServers";
            case 13:
               return "MessagingBridges";
            case 14:
               return "PathServices";
            case 15:
               return "JMSBridgeDestinations";
            case 16:
               return "MailSessions";
            case 17:
               return "FileStores";
            case 18:
               return "JDBCStores";
            case 19:
               return "JMSSystemResources";
            case 20:
               return "ForeignJNDIProviders";
            case 21:
               return "JDBCSystemResources";
            case 22:
               return "WLDFSystemResources";
            case 23:
               return "SAFAgents";
            case 24:
               return "CoherenceClusterSystemResources";
            case 25:
               return "OsgiFrameworks";
            case 26:
               return "ManagedExecutorServices";
            case 27:
               return "ManagedScheduledExecutorServices";
            case 28:
               return "ManagedThreadFactories";
            case 29:
               return "ImMutable";
            case 30:
               return "ResourceGroupTemplateID";
            case 31:
               return "UploadDirectoryName";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AppDeployments")) {
            return 10;
         } else if (propName.equals("CoherenceClusterSystemResources")) {
            return 24;
         } else if (propName.equals("FileStores")) {
            return 17;
         } else if (propName.equals("ForeignJNDIProviders")) {
            return 20;
         } else if (propName.equals("JDBCStores")) {
            return 18;
         } else if (propName.equals("JDBCSystemResources")) {
            return 21;
         } else if (propName.equals("JMSBridgeDestinations")) {
            return 15;
         } else if (propName.equals("JMSServers")) {
            return 12;
         } else if (propName.equals("JMSSystemResources")) {
            return 19;
         } else if (propName.equals("Libraries")) {
            return 11;
         } else if (propName.equals("MailSessions")) {
            return 16;
         } else if (propName.equals("ManagedExecutorServices")) {
            return 26;
         } else if (propName.equals("ManagedScheduledExecutorServices")) {
            return 27;
         } else if (propName.equals("ManagedThreadFactories")) {
            return 28;
         } else if (propName.equals("MessagingBridges")) {
            return 13;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("OsgiFrameworks")) {
            return 25;
         } else if (propName.equals("PathServices")) {
            return 14;
         } else if (propName.equals("ResourceGroupTemplateID")) {
            return 30;
         } else if (propName.equals("SAFAgents")) {
            return 23;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("UploadDirectoryName")) {
            return 31;
         } else if (propName.equals("WLDFSystemResources")) {
            return 22;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("ImMutable") ? 29 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAppDeployments()));
         iterators.add(new ArrayIterator(this.bean.getCoherenceClusterSystemResources()));
         iterators.add(new ArrayIterator(this.bean.getFileStores()));
         iterators.add(new ArrayIterator(this.bean.getForeignJNDIProviders()));
         iterators.add(new ArrayIterator(this.bean.getJDBCStores()));
         iterators.add(new ArrayIterator(this.bean.getJDBCSystemResources()));
         iterators.add(new ArrayIterator(this.bean.getJMSBridgeDestinations()));
         iterators.add(new ArrayIterator(this.bean.getJMSServers()));
         iterators.add(new ArrayIterator(this.bean.getJMSSystemResources()));
         iterators.add(new ArrayIterator(this.bean.getLibraries()));
         iterators.add(new ArrayIterator(this.bean.getMailSessions()));
         iterators.add(new ArrayIterator(this.bean.getManagedExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedScheduledExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedThreadFactories()));
         iterators.add(new ArrayIterator(this.bean.getMessagingBridges()));
         iterators.add(new ArrayIterator(this.bean.getOsgiFrameworks()));
         iterators.add(new ArrayIterator(this.bean.getPathServices()));
         iterators.add(new ArrayIterator(this.bean.getSAFAgents()));
         iterators.add(new ArrayIterator(this.bean.getWLDFSystemResources()));
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
            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getAppDeployments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAppDeployments()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCoherenceClusterSystemResources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherenceClusterSystemResources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getFileStores().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFileStores()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getForeignJNDIProviders().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignJNDIProviders()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJDBCStores().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJDBCStores()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJDBCSystemResources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJDBCSystemResources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSBridgeDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSBridgeDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSServers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSServers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJMSSystemResources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSSystemResources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLibraries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLibraries()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMailSessions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMailSessions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedExecutorServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedExecutorServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedScheduledExecutorServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedScheduledExecutorServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedThreadFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedThreadFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessagingBridges().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessagingBridges()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getOsgiFrameworks().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getOsgiFrameworks()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPathServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPathServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isResourceGroupTemplateIDSet()) {
               buf.append("ResourceGroupTemplateID");
               buf.append(String.valueOf(this.bean.getResourceGroupTemplateID()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSAFAgents().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSAFAgents()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isUploadDirectoryNameSet()) {
               buf.append("UploadDirectoryName");
               buf.append(String.valueOf(this.bean.getUploadDirectoryName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWLDFSystemResources().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWLDFSystemResources()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isImMutableSet()) {
               buf.append("ImMutable");
               buf.append(String.valueOf(this.bean.isImMutable()));
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
            ResourceGroupTemplateMBeanImpl otherTyped = (ResourceGroupTemplateMBeanImpl)other;
            this.computeChildDiff("AppDeployments", this.bean.getAppDeployments(), otherTyped.getAppDeployments(), true);
            this.computeChildDiff("CoherenceClusterSystemResources", this.bean.getCoherenceClusterSystemResources(), otherTyped.getCoherenceClusterSystemResources(), true);
            this.computeChildDiff("FileStores", this.bean.getFileStores(), otherTyped.getFileStores(), true);
            this.computeChildDiff("ForeignJNDIProviders", this.bean.getForeignJNDIProviders(), otherTyped.getForeignJNDIProviders(), true);
            this.computeChildDiff("JDBCStores", this.bean.getJDBCStores(), otherTyped.getJDBCStores(), true);
            this.computeChildDiff("JDBCSystemResources", this.bean.getJDBCSystemResources(), otherTyped.getJDBCSystemResources(), true);
            this.computeChildDiff("JMSBridgeDestinations", this.bean.getJMSBridgeDestinations(), otherTyped.getJMSBridgeDestinations(), true);
            this.computeChildDiff("JMSServers", this.bean.getJMSServers(), otherTyped.getJMSServers(), true);
            this.computeChildDiff("JMSSystemResources", this.bean.getJMSSystemResources(), otherTyped.getJMSSystemResources(), true);
            this.computeChildDiff("Libraries", this.bean.getLibraries(), otherTyped.getLibraries(), true);
            this.computeChildDiff("MailSessions", this.bean.getMailSessions(), otherTyped.getMailSessions(), true);
            this.computeChildDiff("ManagedExecutorServices", this.bean.getManagedExecutorServices(), otherTyped.getManagedExecutorServices(), true);
            this.computeChildDiff("ManagedScheduledExecutorServices", this.bean.getManagedScheduledExecutorServices(), otherTyped.getManagedScheduledExecutorServices(), true);
            this.computeChildDiff("ManagedThreadFactories", this.bean.getManagedThreadFactories(), otherTyped.getManagedThreadFactories(), true);
            this.computeChildDiff("MessagingBridges", this.bean.getMessagingBridges(), otherTyped.getMessagingBridges(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeChildDiff("OsgiFrameworks", this.bean.getOsgiFrameworks(), otherTyped.getOsgiFrameworks(), true);
            this.computeChildDiff("PathServices", this.bean.getPathServices(), otherTyped.getPathServices(), true);
            this.computeDiff("ResourceGroupTemplateID", this.bean.getResourceGroupTemplateID(), otherTyped.getResourceGroupTemplateID(), false);
            this.computeChildDiff("SAFAgents", this.bean.getSAFAgents(), otherTyped.getSAFAgents(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("UploadDirectoryName", this.bean.getUploadDirectoryName(), otherTyped.getUploadDirectoryName(), true);
            this.computeChildDiff("WLDFSystemResources", this.bean.getWLDFSystemResources(), otherTyped.getWLDFSystemResources(), true);
            this.computeDiff("ImMutable", this.bean.isImMutable(), otherTyped.isImMutable(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ResourceGroupTemplateMBeanImpl original = (ResourceGroupTemplateMBeanImpl)event.getSourceBean();
            ResourceGroupTemplateMBeanImpl proposed = (ResourceGroupTemplateMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AppDeployments")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAppDeployment((AppDeploymentMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAppDeployment((AppDeploymentMBean)update.getRemovedObject());
                  }

                  if (original.getAppDeployments() == null || original.getAppDeployments().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("CoherenceClusterSystemResources")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherenceClusterSystemResource((CoherenceClusterSystemResourceMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCoherenceClusterSystemResource((CoherenceClusterSystemResourceMBean)update.getRemovedObject());
                  }

                  if (original.getCoherenceClusterSystemResources() == null || original.getCoherenceClusterSystemResources().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  }
               } else if (prop.equals("FileStores")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addFileStore((FileStoreMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeFileStore((FileStoreMBean)update.getRemovedObject());
                  }

                  if (original.getFileStores() == null || original.getFileStores().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  }
               } else if (prop.equals("ForeignJNDIProviders")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addForeignJNDIProvider((ForeignJNDIProviderMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeForeignJNDIProvider((ForeignJNDIProviderMBean)update.getRemovedObject());
                  }

                  if (original.getForeignJNDIProviders() == null || original.getForeignJNDIProviders().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  }
               } else if (prop.equals("JDBCStores")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJDBCStore((JDBCStoreMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJDBCStore((JDBCStoreMBean)update.getRemovedObject());
                  }

                  if (original.getJDBCStores() == null || original.getJDBCStores().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  }
               } else if (prop.equals("JDBCSystemResources")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJDBCSystemResource((JDBCSystemResourceMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJDBCSystemResource((JDBCSystemResourceMBean)update.getRemovedObject());
                  }

                  if (original.getJDBCSystemResources() == null || original.getJDBCSystemResources().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  }
               } else if (prop.equals("JMSBridgeDestinations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJMSBridgeDestination((JMSBridgeDestinationMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJMSBridgeDestination((JMSBridgeDestinationMBean)update.getRemovedObject());
                  }

                  if (original.getJMSBridgeDestinations() == null || original.getJMSBridgeDestinations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  }
               } else if (prop.equals("JMSServers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJMSServer((JMSServerMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJMSServer((JMSServerMBean)update.getRemovedObject());
                  }

                  if (original.getJMSServers() == null || original.getJMSServers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("JMSSystemResources")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJMSSystemResource((JMSSystemResourceMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJMSSystemResource((JMSSystemResourceMBean)update.getRemovedObject());
                  }

                  if (original.getJMSSystemResources() == null || original.getJMSSystemResources().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  }
               } else if (prop.equals("Libraries")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addLibrary((LibraryMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeLibrary((LibraryMBean)update.getRemovedObject());
                  }

                  if (original.getLibraries() == null || original.getLibraries().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("MailSessions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMailSession((MailSessionMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMailSession((MailSessionMBean)update.getRemovedObject());
                  }

                  if (original.getMailSessions() == null || original.getMailSessions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  }
               } else if (prop.equals("ManagedExecutorServices")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addManagedExecutorService((ManagedExecutorServiceMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeManagedExecutorService((ManagedExecutorServiceMBean)update.getRemovedObject());
                  }

                  if (original.getManagedExecutorServices() == null || original.getManagedExecutorServices().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 26);
                  }
               } else if (prop.equals("ManagedScheduledExecutorServices")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addManagedScheduledExecutorService((ManagedScheduledExecutorServiceMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeManagedScheduledExecutorService((ManagedScheduledExecutorServiceMBean)update.getRemovedObject());
                  }

                  if (original.getManagedScheduledExecutorServices() == null || original.getManagedScheduledExecutorServices().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  }
               } else if (prop.equals("ManagedThreadFactories")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addManagedThreadFactory((ManagedThreadFactoryMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeManagedThreadFactory((ManagedThreadFactoryMBean)update.getRemovedObject());
                  }

                  if (original.getManagedThreadFactories() == null || original.getManagedThreadFactories().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 28);
                  }
               } else if (prop.equals("MessagingBridges")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMessagingBridge((MessagingBridgeMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMessagingBridge((MessagingBridgeMBean)update.getRemovedObject());
                  }

                  if (original.getMessagingBridges() == null || original.getMessagingBridges().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("OsgiFrameworks")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addOsgiFramework((OsgiFrameworkMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeOsgiFramework((OsgiFrameworkMBean)update.getRemovedObject());
                  }

                  if (original.getOsgiFrameworks() == null || original.getOsgiFrameworks().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
                  }
               } else if (prop.equals("PathServices")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPathService((PathServiceMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePathService((PathServiceMBean)update.getRemovedObject());
                  }

                  if (original.getPathServices() == null || original.getPathServices().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  }
               } else if (prop.equals("ResourceGroupTemplateID")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("SAFAgents")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSAFAgent((SAFAgentMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSAFAgent((SAFAgentMBean)update.getRemovedObject());
                  }

                  if (original.getSAFAgents() == null || original.getSAFAgents().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  }
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
               } else if (prop.equals("UploadDirectoryName")) {
                  original.setUploadDirectoryName(proposed.getUploadDirectoryName());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("WLDFSystemResources")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWLDFSystemResource((WLDFSystemResourceMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWLDFSystemResource((WLDFSystemResourceMBean)update.getRemovedObject());
                  }

                  if (original.getWLDFSystemResources() == null || original.getWLDFSystemResources().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  }
               } else if (!prop.equals("DynamicallyCreated")) {
                  if (prop.equals("ImMutable")) {
                     original._conditionalUnset(update.isUnsetUpdate(), 29);
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
            ResourceGroupTemplateMBeanImpl copy = (ResourceGroupTemplateMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AppDeployments")) && this.bean.isAppDeploymentsSet() && !copy._isSet(10)) {
               AppDeploymentMBean[] oldAppDeployments = this.bean.getAppDeployments();
               AppDeploymentMBean[] newAppDeployments = new AppDeploymentMBean[oldAppDeployments.length];

               for(i = 0; i < newAppDeployments.length; ++i) {
                  newAppDeployments[i] = (AppDeploymentMBean)((AppDeploymentMBean)this.createCopy((AbstractDescriptorBean)oldAppDeployments[i], includeObsolete));
               }

               copy.setAppDeployments(newAppDeployments);
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceClusterSystemResources")) && this.bean.isCoherenceClusterSystemResourcesSet() && !copy._isSet(24)) {
               CoherenceClusterSystemResourceMBean[] oldCoherenceClusterSystemResources = this.bean.getCoherenceClusterSystemResources();
               CoherenceClusterSystemResourceMBean[] newCoherenceClusterSystemResources = new CoherenceClusterSystemResourceMBean[oldCoherenceClusterSystemResources.length];

               for(i = 0; i < newCoherenceClusterSystemResources.length; ++i) {
                  newCoherenceClusterSystemResources[i] = (CoherenceClusterSystemResourceMBean)((CoherenceClusterSystemResourceMBean)this.createCopy((AbstractDescriptorBean)oldCoherenceClusterSystemResources[i], includeObsolete));
               }

               copy.setCoherenceClusterSystemResources(newCoherenceClusterSystemResources);
            }

            if ((excludeProps == null || !excludeProps.contains("FileStores")) && this.bean.isFileStoresSet() && !copy._isSet(17)) {
               FileStoreMBean[] oldFileStores = this.bean.getFileStores();
               FileStoreMBean[] newFileStores = new FileStoreMBean[oldFileStores.length];

               for(i = 0; i < newFileStores.length; ++i) {
                  newFileStores[i] = (FileStoreMBean)((FileStoreMBean)this.createCopy((AbstractDescriptorBean)oldFileStores[i], includeObsolete));
               }

               copy.setFileStores(newFileStores);
            }

            if ((excludeProps == null || !excludeProps.contains("ForeignJNDIProviders")) && this.bean.isForeignJNDIProvidersSet() && !copy._isSet(20)) {
               ForeignJNDIProviderMBean[] oldForeignJNDIProviders = this.bean.getForeignJNDIProviders();
               ForeignJNDIProviderMBean[] newForeignJNDIProviders = new ForeignJNDIProviderMBean[oldForeignJNDIProviders.length];

               for(i = 0; i < newForeignJNDIProviders.length; ++i) {
                  newForeignJNDIProviders[i] = (ForeignJNDIProviderMBean)((ForeignJNDIProviderMBean)this.createCopy((AbstractDescriptorBean)oldForeignJNDIProviders[i], includeObsolete));
               }

               copy.setForeignJNDIProviders(newForeignJNDIProviders);
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCStores")) && this.bean.isJDBCStoresSet() && !copy._isSet(18)) {
               JDBCStoreMBean[] oldJDBCStores = this.bean.getJDBCStores();
               JDBCStoreMBean[] newJDBCStores = new JDBCStoreMBean[oldJDBCStores.length];

               for(i = 0; i < newJDBCStores.length; ++i) {
                  newJDBCStores[i] = (JDBCStoreMBean)((JDBCStoreMBean)this.createCopy((AbstractDescriptorBean)oldJDBCStores[i], includeObsolete));
               }

               copy.setJDBCStores(newJDBCStores);
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCSystemResources")) && this.bean.isJDBCSystemResourcesSet() && !copy._isSet(21)) {
               JDBCSystemResourceMBean[] oldJDBCSystemResources = this.bean.getJDBCSystemResources();
               JDBCSystemResourceMBean[] newJDBCSystemResources = new JDBCSystemResourceMBean[oldJDBCSystemResources.length];

               for(i = 0; i < newJDBCSystemResources.length; ++i) {
                  newJDBCSystemResources[i] = (JDBCSystemResourceMBean)((JDBCSystemResourceMBean)this.createCopy((AbstractDescriptorBean)oldJDBCSystemResources[i], includeObsolete));
               }

               copy.setJDBCSystemResources(newJDBCSystemResources);
            }

            if ((excludeProps == null || !excludeProps.contains("JMSBridgeDestinations")) && this.bean.isJMSBridgeDestinationsSet() && !copy._isSet(15)) {
               JMSBridgeDestinationMBean[] oldJMSBridgeDestinations = this.bean.getJMSBridgeDestinations();
               JMSBridgeDestinationMBean[] newJMSBridgeDestinations = new JMSBridgeDestinationMBean[oldJMSBridgeDestinations.length];

               for(i = 0; i < newJMSBridgeDestinations.length; ++i) {
                  newJMSBridgeDestinations[i] = (JMSBridgeDestinationMBean)((JMSBridgeDestinationMBean)this.createCopy((AbstractDescriptorBean)oldJMSBridgeDestinations[i], includeObsolete));
               }

               copy.setJMSBridgeDestinations(newJMSBridgeDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("JMSServers")) && this.bean.isJMSServersSet() && !copy._isSet(12)) {
               JMSServerMBean[] oldJMSServers = this.bean.getJMSServers();
               JMSServerMBean[] newJMSServers = new JMSServerMBean[oldJMSServers.length];

               for(i = 0; i < newJMSServers.length; ++i) {
                  newJMSServers[i] = (JMSServerMBean)((JMSServerMBean)this.createCopy((AbstractDescriptorBean)oldJMSServers[i], includeObsolete));
               }

               copy.setJMSServers(newJMSServers);
            }

            if ((excludeProps == null || !excludeProps.contains("JMSSystemResources")) && this.bean.isJMSSystemResourcesSet() && !copy._isSet(19)) {
               JMSSystemResourceMBean[] oldJMSSystemResources = this.bean.getJMSSystemResources();
               JMSSystemResourceMBean[] newJMSSystemResources = new JMSSystemResourceMBean[oldJMSSystemResources.length];

               for(i = 0; i < newJMSSystemResources.length; ++i) {
                  newJMSSystemResources[i] = (JMSSystemResourceMBean)((JMSSystemResourceMBean)this.createCopy((AbstractDescriptorBean)oldJMSSystemResources[i], includeObsolete));
               }

               copy.setJMSSystemResources(newJMSSystemResources);
            }

            if ((excludeProps == null || !excludeProps.contains("Libraries")) && this.bean.isLibrariesSet() && !copy._isSet(11)) {
               LibraryMBean[] oldLibraries = this.bean.getLibraries();
               LibraryMBean[] newLibraries = new LibraryMBean[oldLibraries.length];

               for(i = 0; i < newLibraries.length; ++i) {
                  newLibraries[i] = (LibraryMBean)((LibraryMBean)this.createCopy((AbstractDescriptorBean)oldLibraries[i], includeObsolete));
               }

               copy.setLibraries(newLibraries);
            }

            if ((excludeProps == null || !excludeProps.contains("MailSessions")) && this.bean.isMailSessionsSet() && !copy._isSet(16)) {
               MailSessionMBean[] oldMailSessions = this.bean.getMailSessions();
               MailSessionMBean[] newMailSessions = new MailSessionMBean[oldMailSessions.length];

               for(i = 0; i < newMailSessions.length; ++i) {
                  newMailSessions[i] = (MailSessionMBean)((MailSessionMBean)this.createCopy((AbstractDescriptorBean)oldMailSessions[i], includeObsolete));
               }

               copy.setMailSessions(newMailSessions);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedExecutorServices")) && this.bean.isManagedExecutorServicesSet() && !copy._isSet(26)) {
               ManagedExecutorServiceMBean[] oldManagedExecutorServices = this.bean.getManagedExecutorServices();
               ManagedExecutorServiceMBean[] newManagedExecutorServices = new ManagedExecutorServiceMBean[oldManagedExecutorServices.length];

               for(i = 0; i < newManagedExecutorServices.length; ++i) {
                  newManagedExecutorServices[i] = (ManagedExecutorServiceMBean)((ManagedExecutorServiceMBean)this.createCopy((AbstractDescriptorBean)oldManagedExecutorServices[i], includeObsolete));
               }

               copy.setManagedExecutorServices(newManagedExecutorServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedScheduledExecutorServices")) && this.bean.isManagedScheduledExecutorServicesSet() && !copy._isSet(27)) {
               ManagedScheduledExecutorServiceMBean[] oldManagedScheduledExecutorServices = this.bean.getManagedScheduledExecutorServices();
               ManagedScheduledExecutorServiceMBean[] newManagedScheduledExecutorServices = new ManagedScheduledExecutorServiceMBean[oldManagedScheduledExecutorServices.length];

               for(i = 0; i < newManagedScheduledExecutorServices.length; ++i) {
                  newManagedScheduledExecutorServices[i] = (ManagedScheduledExecutorServiceMBean)((ManagedScheduledExecutorServiceMBean)this.createCopy((AbstractDescriptorBean)oldManagedScheduledExecutorServices[i], includeObsolete));
               }

               copy.setManagedScheduledExecutorServices(newManagedScheduledExecutorServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedThreadFactories")) && this.bean.isManagedThreadFactoriesSet() && !copy._isSet(28)) {
               ManagedThreadFactoryMBean[] oldManagedThreadFactories = this.bean.getManagedThreadFactories();
               ManagedThreadFactoryMBean[] newManagedThreadFactories = new ManagedThreadFactoryMBean[oldManagedThreadFactories.length];

               for(i = 0; i < newManagedThreadFactories.length; ++i) {
                  newManagedThreadFactories[i] = (ManagedThreadFactoryMBean)((ManagedThreadFactoryMBean)this.createCopy((AbstractDescriptorBean)oldManagedThreadFactories[i], includeObsolete));
               }

               copy.setManagedThreadFactories(newManagedThreadFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("MessagingBridges")) && this.bean.isMessagingBridgesSet() && !copy._isSet(13)) {
               MessagingBridgeMBean[] oldMessagingBridges = this.bean.getMessagingBridges();
               MessagingBridgeMBean[] newMessagingBridges = new MessagingBridgeMBean[oldMessagingBridges.length];

               for(i = 0; i < newMessagingBridges.length; ++i) {
                  newMessagingBridges[i] = (MessagingBridgeMBean)((MessagingBridgeMBean)this.createCopy((AbstractDescriptorBean)oldMessagingBridges[i], includeObsolete));
               }

               copy.setMessagingBridges(newMessagingBridges);
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("OsgiFrameworks")) && this.bean.isOsgiFrameworksSet() && !copy._isSet(25)) {
               OsgiFrameworkMBean[] oldOsgiFrameworks = this.bean.getOsgiFrameworks();
               OsgiFrameworkMBean[] newOsgiFrameworks = new OsgiFrameworkMBean[oldOsgiFrameworks.length];

               for(i = 0; i < newOsgiFrameworks.length; ++i) {
                  newOsgiFrameworks[i] = (OsgiFrameworkMBean)((OsgiFrameworkMBean)this.createCopy((AbstractDescriptorBean)oldOsgiFrameworks[i], includeObsolete));
               }

               copy.setOsgiFrameworks(newOsgiFrameworks);
            }

            if ((excludeProps == null || !excludeProps.contains("PathServices")) && this.bean.isPathServicesSet() && !copy._isSet(14)) {
               PathServiceMBean[] oldPathServices = this.bean.getPathServices();
               PathServiceMBean[] newPathServices = new PathServiceMBean[oldPathServices.length];

               for(i = 0; i < newPathServices.length; ++i) {
                  newPathServices[i] = (PathServiceMBean)((PathServiceMBean)this.createCopy((AbstractDescriptorBean)oldPathServices[i], includeObsolete));
               }

               copy.setPathServices(newPathServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceGroupTemplateID")) && this.bean.isResourceGroupTemplateIDSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("SAFAgents")) && this.bean.isSAFAgentsSet() && !copy._isSet(23)) {
               SAFAgentMBean[] oldSAFAgents = this.bean.getSAFAgents();
               SAFAgentMBean[] newSAFAgents = new SAFAgentMBean[oldSAFAgents.length];

               for(i = 0; i < newSAFAgents.length; ++i) {
                  newSAFAgents[i] = (SAFAgentMBean)((SAFAgentMBean)this.createCopy((AbstractDescriptorBean)oldSAFAgents[i], includeObsolete));
               }

               copy.setSAFAgents(newSAFAgents);
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UploadDirectoryName")) && this.bean.isUploadDirectoryNameSet()) {
               copy.setUploadDirectoryName(this.bean.getUploadDirectoryName());
            }

            if ((excludeProps == null || !excludeProps.contains("WLDFSystemResources")) && this.bean.isWLDFSystemResourcesSet() && !copy._isSet(22)) {
               WLDFSystemResourceMBean[] oldWLDFSystemResources = this.bean.getWLDFSystemResources();
               WLDFSystemResourceMBean[] newWLDFSystemResources = new WLDFSystemResourceMBean[oldWLDFSystemResources.length];

               for(i = 0; i < newWLDFSystemResources.length; ++i) {
                  newWLDFSystemResources[i] = (WLDFSystemResourceMBean)((WLDFSystemResourceMBean)this.createCopy((AbstractDescriptorBean)oldWLDFSystemResources[i], includeObsolete));
               }

               copy.setWLDFSystemResources(newWLDFSystemResources);
            }

            if ((excludeProps == null || !excludeProps.contains("ImMutable")) && this.bean.isImMutableSet()) {
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
         this.inferSubTree(this.bean.getAppDeployments(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceClusterSystemResources(), clazz, annotation);
         this.inferSubTree(this.bean.getFileStores(), clazz, annotation);
         this.inferSubTree(this.bean.getForeignJNDIProviders(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCStores(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCSystemResources(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSBridgeDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSServers(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSSystemResources(), clazz, annotation);
         this.inferSubTree(this.bean.getLibraries(), clazz, annotation);
         this.inferSubTree(this.bean.getMailSessions(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedExecutorServices(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedScheduledExecutorServices(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedThreadFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getMessagingBridges(), clazz, annotation);
         this.inferSubTree(this.bean.getOsgiFrameworks(), clazz, annotation);
         this.inferSubTree(this.bean.getPathServices(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFAgents(), clazz, annotation);
         this.inferSubTree(this.bean.getWLDFSystemResources(), clazz, annotation);
      }
   }
}
