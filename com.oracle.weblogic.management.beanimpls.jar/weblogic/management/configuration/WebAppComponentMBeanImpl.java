package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import weblogic.management.mbeans.custom.WebAppComponent;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WebAppComponentMBeanImpl extends ComponentMBeanImpl implements WebAppComponentMBean, Serializable {
   private TargetMBean[] _ActivatedTargets;
   private ApplicationMBean _Application;
   private String _AuthFilter;
   private String _AuthRealmName;
   private boolean _CleanupSessionFilesEnabled;
   private String _ContextPath;
   private boolean _DebugEnabled;
   private String _DefaultServlet;
   private VirtualHostMBean[] _DeployedVirtualHosts;
   private String _DocumentRoot;
   private boolean _DynamicallyCreated;
   private boolean _IndexDirectoryEnabled;
   private String[] _IndexFiles;
   private Boolean _JaxRsMonitoringDefaultBehavior;
   private String _MimeTypeDefault;
   private Map _MimeTypes;
   private String _Name;
   private boolean _PreferWebInfClasses;
   private String _ServletClasspath;
   private boolean _ServletExtensionCaseSensitive;
   private int _ServletReloadCheckSecs;
   private String[] _Servlets;
   private int _SessionCacheSize;
   private String _SessionCookieComment;
   private String _SessionCookieDomain;
   private int _SessionCookieMaxAgeSecs;
   private String _SessionCookieName;
   private String _SessionCookiePath;
   private boolean _SessionCookiesEnabled;
   private boolean _SessionDebuggable;
   private int _SessionIDLength;
   private int _SessionInvalidationIntervalSecs;
   private int _SessionJDBCConnectionTimeoutSecs;
   private String _SessionMainAttribute;
   private boolean _SessionMonitoringEnabled;
   private String _SessionPersistentStoreCookieName;
   private String _SessionPersistentStoreDir;
   private String _SessionPersistentStorePool;
   private boolean _SessionPersistentStoreShared;
   private String _SessionPersistentStoreTable;
   private String _SessionPersistentStoreType;
   private int _SessionSwapIntervalSecs;
   private int _SessionTimeoutSecs;
   private boolean _SessionTrackingEnabled;
   private boolean _SessionURLRewritingEnabled;
   private int _SingleThreadedServletPoolSize;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private VirtualHostMBean[] _VirtualHosts;
   private WebServerMBean[] _WebServers;
   private transient WebAppComponent _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private WebAppComponentMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(WebAppComponentMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(WebAppComponentMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public WebAppComponentMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(WebAppComponentMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      WebAppComponentMBeanImpl oldDelegate = this._DelegateBean;
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

   public WebAppComponentMBeanImpl() {
      try {
         this._customizer = new WebAppComponent(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WebAppComponentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new WebAppComponent(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WebAppComponentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new WebAppComponent(this);
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

   public int getSessionCookieMaxAgeSecs() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getSessionCookieMaxAgeSecs() : this._SessionCookieMaxAgeSecs;
   }

   public TargetMBean[] getTargets() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getTargets() : this._customizer.getTargets();
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public WebServerMBean[] getWebServers() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getWebServers() : this._WebServers;
   }

   public boolean isApplicationInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isApplicationSet() {
      return this._isSet(12);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isSessionCookieMaxAgeSecsInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isSessionCookieMaxAgeSecsSet() {
      return this._isSet(18);
   }

   public boolean isTargetsInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isTargetsSet() {
      return this._isSet(10);
   }

   public boolean isWebServersInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isWebServersSet() {
      return this._isSet(15);
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
                        WebAppComponentMBeanImpl.this.addTarget((TargetMBean)value);
                        WebAppComponentMBeanImpl.this._getHelper().reorderArrayObjects((Object[])WebAppComponentMBeanImpl.this._Targets, this.getHandbackObject());
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
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setSessionCookieMaxAgeSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionCookieMaxAgeSecs = param0;
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
                  return WebAppComponentMBeanImpl.this.getTargets();
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
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public void setWebServers(WebServerMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      WebServerMBean[] param0 = param0 == null ? new WebServerMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._WebServers = (WebServerMBean[])param0;
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

   public boolean addWebServer(WebServerMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         WebServerMBean[] _new = (WebServerMBean[])((WebServerMBean[])this._getHelper()._extendArray(this.getWebServers(), WebServerMBean.class, param0));

         try {
            this.setWebServers(_new);
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

   public int getSessionInvalidationIntervalSecs() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().getSessionInvalidationIntervalSecs() : this._SessionInvalidationIntervalSecs;
   }

   public boolean isSessionInvalidationIntervalSecsInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isSessionInvalidationIntervalSecsSet() {
      return this._isSet(19);
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

   public boolean removeWebServer(WebServerMBean param0) throws DistributedManagementException {
      WebServerMBean[] _old = this.getWebServers();
      WebServerMBean[] _new = (WebServerMBean[])((WebServerMBean[])this._getHelper()._removeElement(_old, WebServerMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setWebServers(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
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

   public void setSessionInvalidationIntervalSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionInvalidationIntervalSecs = param0;
   }

   public TargetMBean[] getActivatedTargets() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getActivatedTargets() : this._customizer.getActivatedTargets();
   }

   public int getSessionJDBCConnectionTimeoutSecs() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getSessionJDBCConnectionTimeoutSecs() : this._SessionJDBCConnectionTimeoutSecs;
   }

   public VirtualHostMBean[] getVirtualHosts() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getVirtualHosts() : this._VirtualHosts;
   }

   public String getVirtualHostsAsString() {
      return this._getHelper()._serializeKeyList(this.getVirtualHosts());
   }

   public boolean isActivatedTargetsInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isActivatedTargetsSet() {
      return this._isSet(14);
   }

   public boolean isSessionJDBCConnectionTimeoutSecsInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isSessionJDBCConnectionTimeoutSecsSet() {
      return this._isSet(20);
   }

   public boolean isVirtualHostsInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isVirtualHostsSet() {
      return this._isSet(16);
   }

   public void setVirtualHostsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._VirtualHosts);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, VirtualHostMBean.class, new ReferenceManager.Resolver(this, 16, param0) {
                  public void resolveReference(Object value) {
                     try {
                        WebAppComponentMBeanImpl.this.addVirtualHost((VirtualHostMBean)value);
                        WebAppComponentMBeanImpl.this._getHelper().reorderArrayObjects((Object[])WebAppComponentMBeanImpl.this._VirtualHosts, this.getHandbackObject());
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
               VirtualHostMBean[] var6 = this._VirtualHosts;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  VirtualHostMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeVirtualHost(member);
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
         VirtualHostMBean[] _oldVal = this._VirtualHosts;
         this._initializeProperty(16);
         this._postSet(16, _oldVal, this._VirtualHosts);
      }
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

   public void setSessionJDBCConnectionTimeoutSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionJDBCConnectionTimeoutSecs = param0;
   }

   public void setVirtualHosts(VirtualHostMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      VirtualHostMBean[] param0 = param0 == null ? new VirtualHostMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      param0 = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._cleanAndValidateArray(param0, VirtualHostMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 16, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return WebAppComponentMBeanImpl.this.getVirtualHosts();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      boolean wasSet = this._isSet(16);
      VirtualHostMBean[] _oldVal = this._VirtualHosts;
      this._VirtualHosts = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addVirtualHost(VirtualHostMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         VirtualHostMBean[] _new;
         if (this._isSet(16)) {
            _new = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._extendArray(this.getVirtualHosts(), VirtualHostMBean.class, param0));
         } else {
            _new = new VirtualHostMBean[]{param0};
         }

         try {
            this.setVirtualHosts(_new);
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

   public int getSessionTimeoutSecs() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().getSessionTimeoutSecs() : this._SessionTimeoutSecs;
   }

   public boolean isSessionTimeoutSecsInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isSessionTimeoutSecsSet() {
      return this._isSet(21);
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

   public boolean removeVirtualHost(VirtualHostMBean param0) throws DistributedManagementException {
      VirtualHostMBean[] _old = this.getVirtualHosts();
      VirtualHostMBean[] _new = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._removeElement(_old, VirtualHostMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setVirtualHosts(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
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

   public void setActivatedTargets(TargetMBean[] param0) {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ActivatedTargets = (TargetMBean[])param0;
   }

   public void setSessionTimeoutSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionTimeoutSecs = param0;
   }

   public boolean activated(TargetMBean param0) {
      return this._customizer.activated(param0);
   }

   public void addDeployedVirtualHost(VirtualHostMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         VirtualHostMBean[] _new = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._extendArray(this.getDeployedVirtualHosts(), VirtualHostMBean.class, param0));

         try {
            this.setDeployedVirtualHosts(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public VirtualHostMBean[] getDeployedVirtualHosts() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getDeployedVirtualHosts() : this._DeployedVirtualHosts;
   }

   public String getMimeTypeDefault() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._performMacroSubstitution(this._getDelegateBean().getMimeTypeDefault(), this) : this._MimeTypeDefault;
   }

   public boolean isDeployedVirtualHostsInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isDeployedVirtualHostsSet() {
      return this._isSet(17);
   }

   public boolean isMimeTypeDefaultInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isMimeTypeDefaultSet() {
      return this._isSet(22);
   }

   public void removeDeployedVirtualHost(VirtualHostMBean param0) {
      VirtualHostMBean[] _old = this.getDeployedVirtualHosts();
      VirtualHostMBean[] _new = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._removeElement(_old, VirtualHostMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDeployedVirtualHosts(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void refreshDDsIfNeeded(String[] param0) {
      this._customizer.refreshDDsIfNeeded(param0);
   }

   public void setDeployedVirtualHosts(VirtualHostMBean[] param0) {
      VirtualHostMBean[] param0 = param0 == null ? new VirtualHostMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DeployedVirtualHosts = (VirtualHostMBean[])param0;
   }

   public void setMimeTypeDefault(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._MimeTypeDefault = param0;
   }

   public Map getMimeTypes() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().getMimeTypes() : this._MimeTypes;
   }

   public boolean isMimeTypesInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isMimeTypesSet() {
      return this._isSet(23);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setMimeTypes(Map param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._MimeTypes = param0;
   }

   public String getDocumentRoot() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._performMacroSubstitution(this._getDelegateBean().getDocumentRoot(), this) : this._DocumentRoot;
   }

   public boolean isDocumentRootInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isDocumentRootSet() {
      return this._isSet(24);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setDocumentRoot(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DocumentRoot = param0;
   }

   public String getDefaultServlet() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultServlet(), this) : this._DefaultServlet;
   }

   public boolean isDefaultServletInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isDefaultServletSet() {
      return this._isSet(25);
   }

   public void setDefaultServlet(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DefaultServlet = param0;
   }

   public boolean isIndexDirectoryEnabled() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().isIndexDirectoryEnabled() : this._IndexDirectoryEnabled;
   }

   public boolean isIndexDirectoryEnabledInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isIndexDirectoryEnabledSet() {
      return this._isSet(26);
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

   public void setIndexDirectoryEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(26);
      boolean _oldVal = this._IndexDirectoryEnabled;
      this._IndexDirectoryEnabled = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getIndexFiles() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().getIndexFiles() : this._IndexFiles;
   }

   public boolean isIndexFilesInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isIndexFilesSet() {
      return this._isSet(27);
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

   public void setIndexFiles(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._IndexFiles = param0;
   }

   public boolean addIndexFile(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getIndexFiles(), String.class, param0));

      try {
         this.setIndexFiles(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
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
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
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

   public boolean removeIndexFile(String param0) {
      String[] _old = this.getIndexFiles();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setIndexFiles(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public String getServletClasspath() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._performMacroSubstitution(this._getDelegateBean().getServletClasspath(), this) : this._ServletClasspath;
   }

   public boolean isServletClasspathInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isServletClasspathSet() {
      return this._isSet(28);
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

   public void setServletClasspath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ServletClasspath = param0;
   }

   public boolean isServletExtensionCaseSensitive() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().isServletExtensionCaseSensitive() : this._ServletExtensionCaseSensitive;
   }

   public boolean isServletExtensionCaseSensitiveInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isServletExtensionCaseSensitiveSet() {
      return this._isSet(29);
   }

   public void setServletExtensionCaseSensitive(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ServletExtensionCaseSensitive = param0;
   }

   public int getServletReloadCheckSecs() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().getServletReloadCheckSecs() : this._ServletReloadCheckSecs;
   }

   public boolean isServletReloadCheckSecsInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isServletReloadCheckSecsSet() {
      return this._isSet(30);
   }

   public void setServletReloadCheckSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      int _oldVal = this._ServletReloadCheckSecs;
      this._ServletReloadCheckSecs = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSingleThreadedServletPoolSize() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().getSingleThreadedServletPoolSize() : this._SingleThreadedServletPoolSize;
   }

   public boolean isSingleThreadedServletPoolSizeInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isSingleThreadedServletPoolSizeSet() {
      return this._isSet(31);
   }

   public void setSingleThreadedServletPoolSize(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      int _oldVal = this._SingleThreadedServletPoolSize;
      this._SingleThreadedServletPoolSize = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getServlets() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().getServlets() : this._Servlets;
   }

   public boolean isServletsInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isServletsSet() {
      return this._isSet(32);
   }

   public void setServlets(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._Servlets = param0;
   }

   public String getAuthRealmName() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._performMacroSubstitution(this._getDelegateBean().getAuthRealmName(), this) : this._AuthRealmName;
   }

   public boolean isAuthRealmNameInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isAuthRealmNameSet() {
      return this._isSet(33);
   }

   public void setAuthRealmName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(33);
      String _oldVal = this._AuthRealmName;
      this._AuthRealmName = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAuthFilter() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._performMacroSubstitution(this._getDelegateBean().getAuthFilter(), this) : this._AuthFilter;
   }

   public boolean isAuthFilterInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isAuthFilterSet() {
      return this._isSet(34);
   }

   public void setAuthFilter(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(34);
      String _oldVal = this._AuthFilter;
      this._AuthFilter = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isDebugEnabled() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._getDelegateBean().isDebugEnabled() : this._DebugEnabled;
   }

   public boolean isDebugEnabledInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isDebugEnabledSet() {
      return this._isSet(35);
   }

   public void setDebugEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DebugEnabled = param0;
   }

   public boolean isSessionURLRewritingEnabled() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._getDelegateBean().isSessionURLRewritingEnabled() : this._SessionURLRewritingEnabled;
   }

   public boolean isSessionURLRewritingEnabledInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isSessionURLRewritingEnabledSet() {
      return this._isSet(36);
   }

   public void setSessionURLRewritingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(36);
      boolean _oldVal = this._SessionURLRewritingEnabled;
      this._SessionURLRewritingEnabled = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSessionIDLength() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._getDelegateBean().getSessionIDLength() : this._SessionIDLength;
   }

   public boolean isSessionIDLengthInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isSessionIDLengthSet() {
      return this._isSet(37);
   }

   public void setSessionIDLength(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionIDLength = param0;
   }

   public int getSessionCacheSize() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._getDelegateBean().getSessionCacheSize() : this._SessionCacheSize;
   }

   public boolean isSessionCacheSizeInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isSessionCacheSizeSet() {
      return this._isSet(38);
   }

   public void setSessionCacheSize(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionCacheSize = param0;
   }

   public boolean isSessionCookiesEnabled() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._getDelegateBean().isSessionCookiesEnabled() : this._SessionCookiesEnabled;
   }

   public boolean isSessionCookiesEnabledInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isSessionCookiesEnabledSet() {
      return this._isSet(39);
   }

   public void setSessionCookiesEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionCookiesEnabled = param0;
   }

   public boolean isSessionTrackingEnabled() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40) ? this._getDelegateBean().isSessionTrackingEnabled() : this._SessionTrackingEnabled;
   }

   public boolean isSessionTrackingEnabledInherited() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40);
   }

   public boolean isSessionTrackingEnabledSet() {
      return this._isSet(40);
   }

   public void setSessionTrackingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionTrackingEnabled = param0;
   }

   public String getSessionCookieComment() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41) ? this._performMacroSubstitution(this._getDelegateBean().getSessionCookieComment(), this) : this._SessionCookieComment;
   }

   public boolean isSessionCookieCommentInherited() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41);
   }

   public boolean isSessionCookieCommentSet() {
      return this._isSet(41);
   }

   public void setSessionCookieComment(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionCookieComment = param0;
   }

   public String getSessionCookieDomain() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42) ? this._performMacroSubstitution(this._getDelegateBean().getSessionCookieDomain(), this) : this._SessionCookieDomain;
   }

   public boolean isSessionCookieDomainInherited() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42);
   }

   public boolean isSessionCookieDomainSet() {
      return this._isSet(42);
   }

   public void setSessionCookieDomain(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionCookieDomain = param0;
   }

   public String getSessionCookieName() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43) ? this._performMacroSubstitution(this._getDelegateBean().getSessionCookieName(), this) : this._SessionCookieName;
   }

   public boolean isSessionCookieNameInherited() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43);
   }

   public boolean isSessionCookieNameSet() {
      return this._isSet(43);
   }

   public void setSessionCookieName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionCookieName = param0;
   }

   public String getSessionCookiePath() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44) ? this._performMacroSubstitution(this._getDelegateBean().getSessionCookiePath(), this) : this._SessionCookiePath;
   }

   public boolean isSessionCookiePathInherited() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44);
   }

   public boolean isSessionCookiePathSet() {
      return this._isSet(44);
   }

   public void setSessionCookiePath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionCookiePath = param0;
   }

   public String getSessionPersistentStoreDir() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45) ? this._performMacroSubstitution(this._getDelegateBean().getSessionPersistentStoreDir(), this) : this._SessionPersistentStoreDir;
   }

   public boolean isSessionPersistentStoreDirInherited() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45);
   }

   public boolean isSessionPersistentStoreDirSet() {
      return this._isSet(45);
   }

   public void setSessionPersistentStoreDir(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionPersistentStoreDir = param0;
   }

   public String getSessionPersistentStorePool() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46) ? this._performMacroSubstitution(this._getDelegateBean().getSessionPersistentStorePool(), this) : this._SessionPersistentStorePool;
   }

   public boolean isSessionPersistentStorePoolInherited() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46);
   }

   public boolean isSessionPersistentStorePoolSet() {
      return this._isSet(46);
   }

   public void setSessionPersistentStorePool(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionPersistentStorePool = param0;
   }

   public String getSessionPersistentStoreTable() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47) ? this._performMacroSubstitution(this._getDelegateBean().getSessionPersistentStoreTable(), this) : this._SessionPersistentStoreTable;
   }

   public boolean isSessionPersistentStoreTableInherited() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47);
   }

   public boolean isSessionPersistentStoreTableSet() {
      return this._isSet(47);
   }

   public void setSessionPersistentStoreTable(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionPersistentStoreTable = param0;
   }

   public boolean isSessionPersistentStoreShared() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48) ? this._getDelegateBean().isSessionPersistentStoreShared() : this._SessionPersistentStoreShared;
   }

   public boolean isSessionPersistentStoreSharedInherited() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48);
   }

   public boolean isSessionPersistentStoreSharedSet() {
      return this._isSet(48);
   }

   public void setSessionPersistentStoreShared(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionPersistentStoreShared = param0;
   }

   public String getSessionPersistentStoreType() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49) ? this._performMacroSubstitution(this._getDelegateBean().getSessionPersistentStoreType(), this) : this._SessionPersistentStoreType;
   }

   public boolean isSessionPersistentStoreTypeInherited() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49);
   }

   public boolean isSessionPersistentStoreTypeSet() {
      return this._isSet(49);
   }

   public void setSessionPersistentStoreType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"memory", "file", "jdbc", "replicated", "cookie", "replicated_if_clustered"};
      param0 = LegalChecks.checkInEnum("SessionPersistentStoreType", param0, _set);
      this._SessionPersistentStoreType = param0;
   }

   public String getSessionPersistentStoreCookieName() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50) ? this._performMacroSubstitution(this._getDelegateBean().getSessionPersistentStoreCookieName(), this) : this._SessionPersistentStoreCookieName;
   }

   public boolean isSessionPersistentStoreCookieNameInherited() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50);
   }

   public boolean isSessionPersistentStoreCookieNameSet() {
      return this._isSet(50);
   }

   public void setSessionPersistentStoreCookieName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionPersistentStoreCookieName = param0;
   }

   public int getSessionSwapIntervalSecs() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51) ? this._getDelegateBean().getSessionSwapIntervalSecs() : this._SessionSwapIntervalSecs;
   }

   public boolean isSessionSwapIntervalSecsInherited() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51);
   }

   public boolean isSessionSwapIntervalSecsSet() {
      return this._isSet(51);
   }

   public void setSessionSwapIntervalSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionSwapIntervalSecs = param0;
   }

   public void setSessionDebuggable(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionDebuggable = param0;
   }

   public boolean isSessionDebuggable() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52) ? this._getDelegateBean().isSessionDebuggable() : this._SessionDebuggable;
   }

   public boolean isSessionDebuggableInherited() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52);
   }

   public boolean isSessionDebuggableSet() {
      return this._isSet(52);
   }

   public void setCleanupSessionFilesEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._CleanupSessionFilesEnabled = param0;
   }

   public boolean isCleanupSessionFilesEnabled() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53) ? this._getDelegateBean().isCleanupSessionFilesEnabled() : this._CleanupSessionFilesEnabled;
   }

   public boolean isCleanupSessionFilesEnabledInherited() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53);
   }

   public boolean isCleanupSessionFilesEnabledSet() {
      return this._isSet(53);
   }

   public String getContextPath() {
      return !this._isSet(54) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(54) ? this._performMacroSubstitution(this._getDelegateBean().getContextPath(), this) : this._ContextPath;
   }

   public boolean isContextPathInherited() {
      return !this._isSet(54) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(54);
   }

   public boolean isContextPathSet() {
      return this._isSet(54);
   }

   public void setContextPath(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ContextPath = param0;
   }

   public String getSessionMainAttribute() {
      return !this._isSet(55) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(55) ? this._performMacroSubstitution(this._getDelegateBean().getSessionMainAttribute(), this) : this._SessionMainAttribute;
   }

   public boolean isSessionMainAttributeInherited() {
      return !this._isSet(55) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(55);
   }

   public boolean isSessionMainAttributeSet() {
      return this._isSet(55);
   }

   public void setSessionMainAttribute(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._SessionMainAttribute = param0;
   }

   public boolean isSessionMonitoringEnabled() {
      return !this._isSet(56) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(56) ? this._getDelegateBean().isSessionMonitoringEnabled() : this._SessionMonitoringEnabled;
   }

   public boolean isSessionMonitoringEnabledInherited() {
      return !this._isSet(56) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(56);
   }

   public boolean isSessionMonitoringEnabledSet() {
      return this._isSet(56);
   }

   public void setSessionMonitoringEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(56);
      boolean _oldVal = this._SessionMonitoringEnabled;
      this._SessionMonitoringEnabled = param0;
      this._postSet(56, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(56)) {
            source._postSetFirePropertyChange(56, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isPreferWebInfClasses() {
      return !this._isSet(57) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(57) ? this._getDelegateBean().isPreferWebInfClasses() : this._PreferWebInfClasses;
   }

   public boolean isPreferWebInfClassesInherited() {
      return !this._isSet(57) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(57);
   }

   public boolean isPreferWebInfClassesSet() {
      return this._isSet(57);
   }

   public void setPreferWebInfClasses(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(57);
      boolean _oldVal = this._PreferWebInfClasses;
      this._PreferWebInfClasses = param0;
      this._postSet(57, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(57)) {
            source._postSetFirePropertyChange(57, wasSet, _oldVal, param0);
         }
      }

   }

   public void setJaxRsMonitoringDefaultBehavior(Boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(58);
      Boolean _oldVal = this._JaxRsMonitoringDefaultBehavior;
      this._JaxRsMonitoringDefaultBehavior = param0;
      this._postSet(58, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebAppComponentMBeanImpl source = (WebAppComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(58)) {
            source._postSetFirePropertyChange(58, wasSet, _oldVal, param0);
         }
      }

   }

   public Boolean isJaxRsMonitoringDefaultBehavior() {
      return !this._isSet(58) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(58) ? this._getDelegateBean().isJaxRsMonitoringDefaultBehavior() : this._JaxRsMonitoringDefaultBehavior;
   }

   public boolean isJaxRsMonitoringDefaultBehaviorInherited() {
      return !this._isSet(58) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(58);
   }

   public boolean isJaxRsMonitoringDefaultBehaviorSet() {
      return this._isSet(58);
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
               this._AuthFilter = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._AuthRealmName = "weblogic";
               if (initOne) {
                  break;
               }
            case 54:
               this._ContextPath = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._DefaultServlet = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._DeployedVirtualHosts = new VirtualHostMBean[0];
               if (initOne) {
                  break;
               }
            case 24:
               this._DocumentRoot = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._IndexFiles = StringHelper.split(INDEX_FILES);
               if (initOne) {
                  break;
               }
            case 22:
               this._MimeTypeDefault = "text/plain";
               if (initOne) {
                  break;
               }
            case 23:
               this._MimeTypes = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 28:
               this._ServletClasspath = null;
               if (initOne) {
                  break;
               }
            case 30:
               this._ServletReloadCheckSecs = 1;
               if (initOne) {
                  break;
               }
            case 32:
               this._Servlets = new String[0];
               if (initOne) {
                  break;
               }
            case 38:
               this._SessionCacheSize = 1024;
               if (initOne) {
                  break;
               }
            case 41:
               this._SessionCookieComment = "Weblogic Server Session Tracking Cookie";
               if (initOne) {
                  break;
               }
            case 42:
               this._SessionCookieDomain = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._SessionCookieMaxAgeSecs = -1;
               if (initOne) {
                  break;
               }
            case 43:
               this._SessionCookieName = "JSESSIONID";
               if (initOne) {
                  break;
               }
            case 44:
               this._SessionCookiePath = null;
               if (initOne) {
                  break;
               }
            case 37:
               this._SessionIDLength = 52;
               if (initOne) {
                  break;
               }
            case 19:
               this._SessionInvalidationIntervalSecs = 60;
               if (initOne) {
                  break;
               }
            case 20:
               this._SessionJDBCConnectionTimeoutSecs = 120;
               if (initOne) {
                  break;
               }
            case 55:
               this._SessionMainAttribute = "ConsoleAttribute";
               if (initOne) {
                  break;
               }
            case 50:
               this._SessionPersistentStoreCookieName = "WLCOOKIE";
               if (initOne) {
                  break;
               }
            case 45:
               this._SessionPersistentStoreDir = "session_db";
               if (initOne) {
                  break;
               }
            case 46:
               this._SessionPersistentStorePool = null;
               if (initOne) {
                  break;
               }
            case 47:
               this._SessionPersistentStoreTable = "wl_servlet_sessions";
               if (initOne) {
                  break;
               }
            case 49:
               this._SessionPersistentStoreType = "memory";
               if (initOne) {
                  break;
               }
            case 51:
               this._SessionSwapIntervalSecs = 10;
               if (initOne) {
                  break;
               }
            case 21:
               this._SessionTimeoutSecs = 3600;
               if (initOne) {
                  break;
               }
            case 31:
               this._SingleThreadedServletPoolSize = 5;
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
            case 16:
               this._VirtualHosts = new VirtualHostMBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._WebServers = new WebServerMBean[0];
               if (initOne) {
                  break;
               }
            case 53:
               this._CleanupSessionFilesEnabled = false;
               if (initOne) {
                  break;
               }
            case 35:
               this._DebugEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 26:
               this._IndexDirectoryEnabled = false;
               if (initOne) {
                  break;
               }
            case 58:
               this._JaxRsMonitoringDefaultBehavior = null;
               if (initOne) {
                  break;
               }
            case 57:
               this._PreferWebInfClasses = false;
               if (initOne) {
                  break;
               }
            case 29:
               this._ServletExtensionCaseSensitive = false;
               if (initOne) {
                  break;
               }
            case 39:
               this._SessionCookiesEnabled = true;
               if (initOne) {
                  break;
               }
            case 52:
               this._SessionDebuggable = false;
               if (initOne) {
                  break;
               }
            case 56:
               this._SessionMonitoringEnabled = false;
               if (initOne) {
                  break;
               }
            case 48:
               this._SessionPersistentStoreShared = false;
               if (initOne) {
                  break;
               }
            case 40:
               this._SessionTrackingEnabled = true;
               if (initOne) {
                  break;
               }
            case 36:
               this._SessionURLRewritingEnabled = true;
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
      return "WebAppComponent";
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
         String oldVal;
         if (name.equals("AuthFilter")) {
            oldVal = this._AuthFilter;
            this._AuthFilter = (String)v;
            this._postSet(34, oldVal, this._AuthFilter);
         } else if (name.equals("AuthRealmName")) {
            oldVal = this._AuthRealmName;
            this._AuthRealmName = (String)v;
            this._postSet(33, oldVal, this._AuthRealmName);
         } else {
            boolean oldVal;
            if (name.equals("CleanupSessionFilesEnabled")) {
               oldVal = this._CleanupSessionFilesEnabled;
               this._CleanupSessionFilesEnabled = (Boolean)v;
               this._postSet(53, oldVal, this._CleanupSessionFilesEnabled);
            } else if (name.equals("ContextPath")) {
               oldVal = this._ContextPath;
               this._ContextPath = (String)v;
               this._postSet(54, oldVal, this._ContextPath);
            } else if (name.equals("DebugEnabled")) {
               oldVal = this._DebugEnabled;
               this._DebugEnabled = (Boolean)v;
               this._postSet(35, oldVal, this._DebugEnabled);
            } else if (name.equals("DefaultServlet")) {
               oldVal = this._DefaultServlet;
               this._DefaultServlet = (String)v;
               this._postSet(25, oldVal, this._DefaultServlet);
            } else {
               VirtualHostMBean[] oldVal;
               if (name.equals("DeployedVirtualHosts")) {
                  oldVal = this._DeployedVirtualHosts;
                  this._DeployedVirtualHosts = (VirtualHostMBean[])((VirtualHostMBean[])v);
                  this._postSet(17, oldVal, this._DeployedVirtualHosts);
               } else if (name.equals("DocumentRoot")) {
                  oldVal = this._DocumentRoot;
                  this._DocumentRoot = (String)v;
                  this._postSet(24, oldVal, this._DocumentRoot);
               } else if (name.equals("DynamicallyCreated")) {
                  oldVal = this._DynamicallyCreated;
                  this._DynamicallyCreated = (Boolean)v;
                  this._postSet(7, oldVal, this._DynamicallyCreated);
               } else if (name.equals("IndexDirectoryEnabled")) {
                  oldVal = this._IndexDirectoryEnabled;
                  this._IndexDirectoryEnabled = (Boolean)v;
                  this._postSet(26, oldVal, this._IndexDirectoryEnabled);
               } else {
                  String[] oldVal;
                  if (name.equals("IndexFiles")) {
                     oldVal = this._IndexFiles;
                     this._IndexFiles = (String[])((String[])v);
                     this._postSet(27, oldVal, this._IndexFiles);
                  } else if (name.equals("JaxRsMonitoringDefaultBehavior")) {
                     Boolean oldVal = this._JaxRsMonitoringDefaultBehavior;
                     this._JaxRsMonitoringDefaultBehavior = (Boolean)v;
                     this._postSet(58, oldVal, this._JaxRsMonitoringDefaultBehavior);
                  } else if (name.equals("MimeTypeDefault")) {
                     oldVal = this._MimeTypeDefault;
                     this._MimeTypeDefault = (String)v;
                     this._postSet(22, oldVal, this._MimeTypeDefault);
                  } else if (name.equals("MimeTypes")) {
                     Map oldVal = this._MimeTypes;
                     this._MimeTypes = (Map)v;
                     this._postSet(23, oldVal, this._MimeTypes);
                  } else if (name.equals("Name")) {
                     oldVal = this._Name;
                     this._Name = (String)v;
                     this._postSet(2, oldVal, this._Name);
                  } else if (name.equals("PreferWebInfClasses")) {
                     oldVal = this._PreferWebInfClasses;
                     this._PreferWebInfClasses = (Boolean)v;
                     this._postSet(57, oldVal, this._PreferWebInfClasses);
                  } else if (name.equals("ServletClasspath")) {
                     oldVal = this._ServletClasspath;
                     this._ServletClasspath = (String)v;
                     this._postSet(28, oldVal, this._ServletClasspath);
                  } else if (name.equals("ServletExtensionCaseSensitive")) {
                     oldVal = this._ServletExtensionCaseSensitive;
                     this._ServletExtensionCaseSensitive = (Boolean)v;
                     this._postSet(29, oldVal, this._ServletExtensionCaseSensitive);
                  } else {
                     int oldVal;
                     if (name.equals("ServletReloadCheckSecs")) {
                        oldVal = this._ServletReloadCheckSecs;
                        this._ServletReloadCheckSecs = (Integer)v;
                        this._postSet(30, oldVal, this._ServletReloadCheckSecs);
                     } else if (name.equals("Servlets")) {
                        oldVal = this._Servlets;
                        this._Servlets = (String[])((String[])v);
                        this._postSet(32, oldVal, this._Servlets);
                     } else if (name.equals("SessionCacheSize")) {
                        oldVal = this._SessionCacheSize;
                        this._SessionCacheSize = (Integer)v;
                        this._postSet(38, oldVal, this._SessionCacheSize);
                     } else if (name.equals("SessionCookieComment")) {
                        oldVal = this._SessionCookieComment;
                        this._SessionCookieComment = (String)v;
                        this._postSet(41, oldVal, this._SessionCookieComment);
                     } else if (name.equals("SessionCookieDomain")) {
                        oldVal = this._SessionCookieDomain;
                        this._SessionCookieDomain = (String)v;
                        this._postSet(42, oldVal, this._SessionCookieDomain);
                     } else if (name.equals("SessionCookieMaxAgeSecs")) {
                        oldVal = this._SessionCookieMaxAgeSecs;
                        this._SessionCookieMaxAgeSecs = (Integer)v;
                        this._postSet(18, oldVal, this._SessionCookieMaxAgeSecs);
                     } else if (name.equals("SessionCookieName")) {
                        oldVal = this._SessionCookieName;
                        this._SessionCookieName = (String)v;
                        this._postSet(43, oldVal, this._SessionCookieName);
                     } else if (name.equals("SessionCookiePath")) {
                        oldVal = this._SessionCookiePath;
                        this._SessionCookiePath = (String)v;
                        this._postSet(44, oldVal, this._SessionCookiePath);
                     } else if (name.equals("SessionCookiesEnabled")) {
                        oldVal = this._SessionCookiesEnabled;
                        this._SessionCookiesEnabled = (Boolean)v;
                        this._postSet(39, oldVal, this._SessionCookiesEnabled);
                     } else if (name.equals("SessionDebuggable")) {
                        oldVal = this._SessionDebuggable;
                        this._SessionDebuggable = (Boolean)v;
                        this._postSet(52, oldVal, this._SessionDebuggable);
                     } else if (name.equals("SessionIDLength")) {
                        oldVal = this._SessionIDLength;
                        this._SessionIDLength = (Integer)v;
                        this._postSet(37, oldVal, this._SessionIDLength);
                     } else if (name.equals("SessionInvalidationIntervalSecs")) {
                        oldVal = this._SessionInvalidationIntervalSecs;
                        this._SessionInvalidationIntervalSecs = (Integer)v;
                        this._postSet(19, oldVal, this._SessionInvalidationIntervalSecs);
                     } else if (name.equals("SessionJDBCConnectionTimeoutSecs")) {
                        oldVal = this._SessionJDBCConnectionTimeoutSecs;
                        this._SessionJDBCConnectionTimeoutSecs = (Integer)v;
                        this._postSet(20, oldVal, this._SessionJDBCConnectionTimeoutSecs);
                     } else if (name.equals("SessionMainAttribute")) {
                        oldVal = this._SessionMainAttribute;
                        this._SessionMainAttribute = (String)v;
                        this._postSet(55, oldVal, this._SessionMainAttribute);
                     } else if (name.equals("SessionMonitoringEnabled")) {
                        oldVal = this._SessionMonitoringEnabled;
                        this._SessionMonitoringEnabled = (Boolean)v;
                        this._postSet(56, oldVal, this._SessionMonitoringEnabled);
                     } else if (name.equals("SessionPersistentStoreCookieName")) {
                        oldVal = this._SessionPersistentStoreCookieName;
                        this._SessionPersistentStoreCookieName = (String)v;
                        this._postSet(50, oldVal, this._SessionPersistentStoreCookieName);
                     } else if (name.equals("SessionPersistentStoreDir")) {
                        oldVal = this._SessionPersistentStoreDir;
                        this._SessionPersistentStoreDir = (String)v;
                        this._postSet(45, oldVal, this._SessionPersistentStoreDir);
                     } else if (name.equals("SessionPersistentStorePool")) {
                        oldVal = this._SessionPersistentStorePool;
                        this._SessionPersistentStorePool = (String)v;
                        this._postSet(46, oldVal, this._SessionPersistentStorePool);
                     } else if (name.equals("SessionPersistentStoreShared")) {
                        oldVal = this._SessionPersistentStoreShared;
                        this._SessionPersistentStoreShared = (Boolean)v;
                        this._postSet(48, oldVal, this._SessionPersistentStoreShared);
                     } else if (name.equals("SessionPersistentStoreTable")) {
                        oldVal = this._SessionPersistentStoreTable;
                        this._SessionPersistentStoreTable = (String)v;
                        this._postSet(47, oldVal, this._SessionPersistentStoreTable);
                     } else if (name.equals("SessionPersistentStoreType")) {
                        oldVal = this._SessionPersistentStoreType;
                        this._SessionPersistentStoreType = (String)v;
                        this._postSet(49, oldVal, this._SessionPersistentStoreType);
                     } else if (name.equals("SessionSwapIntervalSecs")) {
                        oldVal = this._SessionSwapIntervalSecs;
                        this._SessionSwapIntervalSecs = (Integer)v;
                        this._postSet(51, oldVal, this._SessionSwapIntervalSecs);
                     } else if (name.equals("SessionTimeoutSecs")) {
                        oldVal = this._SessionTimeoutSecs;
                        this._SessionTimeoutSecs = (Integer)v;
                        this._postSet(21, oldVal, this._SessionTimeoutSecs);
                     } else if (name.equals("SessionTrackingEnabled")) {
                        oldVal = this._SessionTrackingEnabled;
                        this._SessionTrackingEnabled = (Boolean)v;
                        this._postSet(40, oldVal, this._SessionTrackingEnabled);
                     } else if (name.equals("SessionURLRewritingEnabled")) {
                        oldVal = this._SessionURLRewritingEnabled;
                        this._SessionURLRewritingEnabled = (Boolean)v;
                        this._postSet(36, oldVal, this._SessionURLRewritingEnabled);
                     } else if (name.equals("SingleThreadedServletPoolSize")) {
                        oldVal = this._SingleThreadedServletPoolSize;
                        this._SingleThreadedServletPoolSize = (Integer)v;
                        this._postSet(31, oldVal, this._SingleThreadedServletPoolSize);
                     } else if (name.equals("Tags")) {
                        oldVal = this._Tags;
                        this._Tags = (String[])((String[])v);
                        this._postSet(9, oldVal, this._Tags);
                     } else if (name.equals("Targets")) {
                        oldVal = this._Targets;
                        this._Targets = (TargetMBean[])((TargetMBean[])v);
                        this._postSet(10, oldVal, this._Targets);
                     } else if (name.equals("VirtualHosts")) {
                        oldVal = this._VirtualHosts;
                        this._VirtualHosts = (VirtualHostMBean[])((VirtualHostMBean[])v);
                        this._postSet(16, oldVal, this._VirtualHosts);
                     } else if (name.equals("WebServers")) {
                        WebServerMBean[] oldVal = this._WebServers;
                        this._WebServers = (WebServerMBean[])((WebServerMBean[])v);
                        this._postSet(15, oldVal, this._WebServers);
                     } else if (name.equals("customizer")) {
                        WebAppComponent oldVal = this._customizer;
                        this._customizer = (WebAppComponent)v;
                     } else {
                        super.putValue(name, v);
                     }
                  }
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
      } else if (name.equals("AuthFilter")) {
         return this._AuthFilter;
      } else if (name.equals("AuthRealmName")) {
         return this._AuthRealmName;
      } else if (name.equals("CleanupSessionFilesEnabled")) {
         return new Boolean(this._CleanupSessionFilesEnabled);
      } else if (name.equals("ContextPath")) {
         return this._ContextPath;
      } else if (name.equals("DebugEnabled")) {
         return new Boolean(this._DebugEnabled);
      } else if (name.equals("DefaultServlet")) {
         return this._DefaultServlet;
      } else if (name.equals("DeployedVirtualHosts")) {
         return this._DeployedVirtualHosts;
      } else if (name.equals("DocumentRoot")) {
         return this._DocumentRoot;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("IndexDirectoryEnabled")) {
         return new Boolean(this._IndexDirectoryEnabled);
      } else if (name.equals("IndexFiles")) {
         return this._IndexFiles;
      } else if (name.equals("JaxRsMonitoringDefaultBehavior")) {
         return this._JaxRsMonitoringDefaultBehavior;
      } else if (name.equals("MimeTypeDefault")) {
         return this._MimeTypeDefault;
      } else if (name.equals("MimeTypes")) {
         return this._MimeTypes;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PreferWebInfClasses")) {
         return new Boolean(this._PreferWebInfClasses);
      } else if (name.equals("ServletClasspath")) {
         return this._ServletClasspath;
      } else if (name.equals("ServletExtensionCaseSensitive")) {
         return new Boolean(this._ServletExtensionCaseSensitive);
      } else if (name.equals("ServletReloadCheckSecs")) {
         return new Integer(this._ServletReloadCheckSecs);
      } else if (name.equals("Servlets")) {
         return this._Servlets;
      } else if (name.equals("SessionCacheSize")) {
         return new Integer(this._SessionCacheSize);
      } else if (name.equals("SessionCookieComment")) {
         return this._SessionCookieComment;
      } else if (name.equals("SessionCookieDomain")) {
         return this._SessionCookieDomain;
      } else if (name.equals("SessionCookieMaxAgeSecs")) {
         return new Integer(this._SessionCookieMaxAgeSecs);
      } else if (name.equals("SessionCookieName")) {
         return this._SessionCookieName;
      } else if (name.equals("SessionCookiePath")) {
         return this._SessionCookiePath;
      } else if (name.equals("SessionCookiesEnabled")) {
         return new Boolean(this._SessionCookiesEnabled);
      } else if (name.equals("SessionDebuggable")) {
         return new Boolean(this._SessionDebuggable);
      } else if (name.equals("SessionIDLength")) {
         return new Integer(this._SessionIDLength);
      } else if (name.equals("SessionInvalidationIntervalSecs")) {
         return new Integer(this._SessionInvalidationIntervalSecs);
      } else if (name.equals("SessionJDBCConnectionTimeoutSecs")) {
         return new Integer(this._SessionJDBCConnectionTimeoutSecs);
      } else if (name.equals("SessionMainAttribute")) {
         return this._SessionMainAttribute;
      } else if (name.equals("SessionMonitoringEnabled")) {
         return new Boolean(this._SessionMonitoringEnabled);
      } else if (name.equals("SessionPersistentStoreCookieName")) {
         return this._SessionPersistentStoreCookieName;
      } else if (name.equals("SessionPersistentStoreDir")) {
         return this._SessionPersistentStoreDir;
      } else if (name.equals("SessionPersistentStorePool")) {
         return this._SessionPersistentStorePool;
      } else if (name.equals("SessionPersistentStoreShared")) {
         return new Boolean(this._SessionPersistentStoreShared);
      } else if (name.equals("SessionPersistentStoreTable")) {
         return this._SessionPersistentStoreTable;
      } else if (name.equals("SessionPersistentStoreType")) {
         return this._SessionPersistentStoreType;
      } else if (name.equals("SessionSwapIntervalSecs")) {
         return new Integer(this._SessionSwapIntervalSecs);
      } else if (name.equals("SessionTimeoutSecs")) {
         return new Integer(this._SessionTimeoutSecs);
      } else if (name.equals("SessionTrackingEnabled")) {
         return new Boolean(this._SessionTrackingEnabled);
      } else if (name.equals("SessionURLRewritingEnabled")) {
         return new Boolean(this._SessionURLRewritingEnabled);
      } else if (name.equals("SingleThreadedServletPoolSize")) {
         return new Integer(this._SingleThreadedServletPoolSize);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("VirtualHosts")) {
         return this._VirtualHosts;
      } else if (name.equals("WebServers")) {
         return this._WebServers;
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
            case 8:
            case 9:
            case 14:
            case 35:
            default:
               break;
            case 6:
               if (s.equals("target")) {
                  return 10;
               }
               break;
            case 7:
               if (s.equals("servlet")) {
                  return 32;
               }
               break;
            case 10:
               if (s.equals("index-file")) {
                  return 27;
               }

               if (s.equals("mime-types")) {
                  return 23;
               }

               if (s.equals("web-server")) {
                  return 15;
               }
               break;
            case 11:
               if (s.equals("application")) {
                  return 12;
               }

               if (s.equals("auth-filter")) {
                  return 34;
               }
               break;
            case 12:
               if (s.equals("context-path")) {
                  return 54;
               }

               if (s.equals("virtual-host")) {
                  return 16;
               }
               break;
            case 13:
               if (s.equals("document-root")) {
                  return 24;
               }

               if (s.equals("debug-enabled")) {
                  return 35;
               }
               break;
            case 15:
               if (s.equals("auth-realm-name")) {
                  return 33;
               }

               if (s.equals("default-servlet")) {
                  return 25;
               }
               break;
            case 16:
               if (s.equals("activated-target")) {
                  return 14;
               }

               if (s.equals("sessionid-length")) {
                  return 37;
               }
               break;
            case 17:
               if (s.equals("mime-type-default")) {
                  return 22;
               }

               if (s.equals("servlet-classpath")) {
                  return 28;
               }
               break;
            case 18:
               if (s.equals("session-cache-size")) {
                  return 38;
               }

               if (s.equals("session-debuggable")) {
                  return 52;
               }
               break;
            case 19:
               if (s.equals("session-cookie-name")) {
                  return 43;
               }

               if (s.equals("session-cookie-path")) {
                  return 44;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("session-timeout-secs")) {
                  return 21;
               }
               break;
            case 21:
               if (s.equals("deployed-virtual-host")) {
                  return 17;
               }

               if (s.equals("session-cookie-domain")) {
                  return 42;
               }
               break;
            case 22:
               if (s.equals("session-cookie-comment")) {
                  return 41;
               }

               if (s.equals("session-main-attribute")) {
                  return 55;
               }

               if (s.equals("prefer-web-inf-classes")) {
                  return 57;
               }
               break;
            case 23:
               if (s.equals("index-directory-enabled")) {
                  return 26;
               }

               if (s.equals("session-cookies-enabled")) {
                  return 39;
               }
               break;
            case 24:
               if (s.equals("session-tracking-enabled")) {
                  return 40;
               }
               break;
            case 25:
               if (s.equals("servlet-reload-check-secs")) {
                  return 30;
               }
               break;
            case 26:
               if (s.equals("session-swap-interval-secs")) {
                  return 51;
               }

               if (s.equals("session-monitoring-enabled")) {
                  return 56;
               }
               break;
            case 27:
               if (s.equals("session-cookie-max-age-secs")) {
                  return 18;
               }
               break;
            case 28:
               if (s.equals("session-persistent-store-dir")) {
                  return 45;
               }
               break;
            case 29:
               if (s.equals("session-persistent-store-pool")) {
                  return 46;
               }

               if (s.equals("session-persistent-store-type")) {
                  return 49;
               }

               if (s.equals("cleanup-session-files-enabled")) {
                  return 53;
               }

               if (s.equals("session-url-rewriting-enabled")) {
                  return 36;
               }
               break;
            case 30:
               if (s.equals("session-persistent-store-table")) {
                  return 47;
               }
               break;
            case 31:
               if (s.equals("session-persistent-store-shared")) {
                  return 48;
               }
               break;
            case 32:
               if (s.equals("servlet-extension-case-sensitive")) {
                  return 29;
               }
               break;
            case 33:
               if (s.equals("single-threaded-servlet-pool-size")) {
                  return 31;
               }
               break;
            case 34:
               if (s.equals("session-invalidation-interval-secs")) {
                  return 19;
               }

               if (s.equals("jax-rs-monitoring-default-behavior")) {
                  return 58;
               }
               break;
            case 36:
               if (s.equals("session-jdbc-connection-timeout-secs")) {
                  return 20;
               }

               if (s.equals("session-persistent-store-cookie-name")) {
                  return 50;
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
               return "web-server";
            case 16:
               return "virtual-host";
            case 17:
               return "deployed-virtual-host";
            case 18:
               return "session-cookie-max-age-secs";
            case 19:
               return "session-invalidation-interval-secs";
            case 20:
               return "session-jdbc-connection-timeout-secs";
            case 21:
               return "session-timeout-secs";
            case 22:
               return "mime-type-default";
            case 23:
               return "mime-types";
            case 24:
               return "document-root";
            case 25:
               return "default-servlet";
            case 26:
               return "index-directory-enabled";
            case 27:
               return "index-file";
            case 28:
               return "servlet-classpath";
            case 29:
               return "servlet-extension-case-sensitive";
            case 30:
               return "servlet-reload-check-secs";
            case 31:
               return "single-threaded-servlet-pool-size";
            case 32:
               return "servlet";
            case 33:
               return "auth-realm-name";
            case 34:
               return "auth-filter";
            case 35:
               return "debug-enabled";
            case 36:
               return "session-url-rewriting-enabled";
            case 37:
               return "sessionid-length";
            case 38:
               return "session-cache-size";
            case 39:
               return "session-cookies-enabled";
            case 40:
               return "session-tracking-enabled";
            case 41:
               return "session-cookie-comment";
            case 42:
               return "session-cookie-domain";
            case 43:
               return "session-cookie-name";
            case 44:
               return "session-cookie-path";
            case 45:
               return "session-persistent-store-dir";
            case 46:
               return "session-persistent-store-pool";
            case 47:
               return "session-persistent-store-table";
            case 48:
               return "session-persistent-store-shared";
            case 49:
               return "session-persistent-store-type";
            case 50:
               return "session-persistent-store-cookie-name";
            case 51:
               return "session-swap-interval-secs";
            case 52:
               return "session-debuggable";
            case 53:
               return "cleanup-session-files-enabled";
            case 54:
               return "context-path";
            case 55:
               return "session-main-attribute";
            case 56:
               return "session-monitoring-enabled";
            case 57:
               return "prefer-web-inf-classes";
            case 58:
               return "jax-rs-monitoring-default-behavior";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
            case 12:
            case 13:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 30:
            case 31:
            default:
               return super.isArray(propIndex);
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 27:
               return true;
            case 32:
               return true;
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
            case 58:
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
      private WebAppComponentMBeanImpl bean;

      protected Helper(WebAppComponentMBeanImpl bean) {
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
               return "WebServers";
            case 16:
               return "VirtualHosts";
            case 17:
               return "DeployedVirtualHosts";
            case 18:
               return "SessionCookieMaxAgeSecs";
            case 19:
               return "SessionInvalidationIntervalSecs";
            case 20:
               return "SessionJDBCConnectionTimeoutSecs";
            case 21:
               return "SessionTimeoutSecs";
            case 22:
               return "MimeTypeDefault";
            case 23:
               return "MimeTypes";
            case 24:
               return "DocumentRoot";
            case 25:
               return "DefaultServlet";
            case 26:
               return "IndexDirectoryEnabled";
            case 27:
               return "IndexFiles";
            case 28:
               return "ServletClasspath";
            case 29:
               return "ServletExtensionCaseSensitive";
            case 30:
               return "ServletReloadCheckSecs";
            case 31:
               return "SingleThreadedServletPoolSize";
            case 32:
               return "Servlets";
            case 33:
               return "AuthRealmName";
            case 34:
               return "AuthFilter";
            case 35:
               return "DebugEnabled";
            case 36:
               return "SessionURLRewritingEnabled";
            case 37:
               return "SessionIDLength";
            case 38:
               return "SessionCacheSize";
            case 39:
               return "SessionCookiesEnabled";
            case 40:
               return "SessionTrackingEnabled";
            case 41:
               return "SessionCookieComment";
            case 42:
               return "SessionCookieDomain";
            case 43:
               return "SessionCookieName";
            case 44:
               return "SessionCookiePath";
            case 45:
               return "SessionPersistentStoreDir";
            case 46:
               return "SessionPersistentStorePool";
            case 47:
               return "SessionPersistentStoreTable";
            case 48:
               return "SessionPersistentStoreShared";
            case 49:
               return "SessionPersistentStoreType";
            case 50:
               return "SessionPersistentStoreCookieName";
            case 51:
               return "SessionSwapIntervalSecs";
            case 52:
               return "SessionDebuggable";
            case 53:
               return "CleanupSessionFilesEnabled";
            case 54:
               return "ContextPath";
            case 55:
               return "SessionMainAttribute";
            case 56:
               return "SessionMonitoringEnabled";
            case 57:
               return "PreferWebInfClasses";
            case 58:
               return "JaxRsMonitoringDefaultBehavior";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActivatedTargets")) {
            return 14;
         } else if (propName.equals("Application")) {
            return 12;
         } else if (propName.equals("AuthFilter")) {
            return 34;
         } else if (propName.equals("AuthRealmName")) {
            return 33;
         } else if (propName.equals("ContextPath")) {
            return 54;
         } else if (propName.equals("DefaultServlet")) {
            return 25;
         } else if (propName.equals("DeployedVirtualHosts")) {
            return 17;
         } else if (propName.equals("DocumentRoot")) {
            return 24;
         } else if (propName.equals("IndexFiles")) {
            return 27;
         } else if (propName.equals("MimeTypeDefault")) {
            return 22;
         } else if (propName.equals("MimeTypes")) {
            return 23;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("ServletClasspath")) {
            return 28;
         } else if (propName.equals("ServletReloadCheckSecs")) {
            return 30;
         } else if (propName.equals("Servlets")) {
            return 32;
         } else if (propName.equals("SessionCacheSize")) {
            return 38;
         } else if (propName.equals("SessionCookieComment")) {
            return 41;
         } else if (propName.equals("SessionCookieDomain")) {
            return 42;
         } else if (propName.equals("SessionCookieMaxAgeSecs")) {
            return 18;
         } else if (propName.equals("SessionCookieName")) {
            return 43;
         } else if (propName.equals("SessionCookiePath")) {
            return 44;
         } else if (propName.equals("SessionIDLength")) {
            return 37;
         } else if (propName.equals("SessionInvalidationIntervalSecs")) {
            return 19;
         } else if (propName.equals("SessionJDBCConnectionTimeoutSecs")) {
            return 20;
         } else if (propName.equals("SessionMainAttribute")) {
            return 55;
         } else if (propName.equals("SessionPersistentStoreCookieName")) {
            return 50;
         } else if (propName.equals("SessionPersistentStoreDir")) {
            return 45;
         } else if (propName.equals("SessionPersistentStorePool")) {
            return 46;
         } else if (propName.equals("SessionPersistentStoreTable")) {
            return 47;
         } else if (propName.equals("SessionPersistentStoreType")) {
            return 49;
         } else if (propName.equals("SessionSwapIntervalSecs")) {
            return 51;
         } else if (propName.equals("SessionTimeoutSecs")) {
            return 21;
         } else if (propName.equals("SingleThreadedServletPoolSize")) {
            return 31;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else if (propName.equals("VirtualHosts")) {
            return 16;
         } else if (propName.equals("WebServers")) {
            return 15;
         } else if (propName.equals("CleanupSessionFilesEnabled")) {
            return 53;
         } else if (propName.equals("DebugEnabled")) {
            return 35;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("IndexDirectoryEnabled")) {
            return 26;
         } else if (propName.equals("JaxRsMonitoringDefaultBehavior")) {
            return 58;
         } else if (propName.equals("PreferWebInfClasses")) {
            return 57;
         } else if (propName.equals("ServletExtensionCaseSensitive")) {
            return 29;
         } else if (propName.equals("SessionCookiesEnabled")) {
            return 39;
         } else if (propName.equals("SessionDebuggable")) {
            return 52;
         } else if (propName.equals("SessionMonitoringEnabled")) {
            return 56;
         } else if (propName.equals("SessionPersistentStoreShared")) {
            return 48;
         } else if (propName.equals("SessionTrackingEnabled")) {
            return 40;
         } else {
            return propName.equals("SessionURLRewritingEnabled") ? 36 : super.getPropertyIndex(propName);
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

            if (this.bean.isAuthFilterSet()) {
               buf.append("AuthFilter");
               buf.append(String.valueOf(this.bean.getAuthFilter()));
            }

            if (this.bean.isAuthRealmNameSet()) {
               buf.append("AuthRealmName");
               buf.append(String.valueOf(this.bean.getAuthRealmName()));
            }

            if (this.bean.isContextPathSet()) {
               buf.append("ContextPath");
               buf.append(String.valueOf(this.bean.getContextPath()));
            }

            if (this.bean.isDefaultServletSet()) {
               buf.append("DefaultServlet");
               buf.append(String.valueOf(this.bean.getDefaultServlet()));
            }

            if (this.bean.isDeployedVirtualHostsSet()) {
               buf.append("DeployedVirtualHosts");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDeployedVirtualHosts())));
            }

            if (this.bean.isDocumentRootSet()) {
               buf.append("DocumentRoot");
               buf.append(String.valueOf(this.bean.getDocumentRoot()));
            }

            if (this.bean.isIndexFilesSet()) {
               buf.append("IndexFiles");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getIndexFiles())));
            }

            if (this.bean.isMimeTypeDefaultSet()) {
               buf.append("MimeTypeDefault");
               buf.append(String.valueOf(this.bean.getMimeTypeDefault()));
            }

            if (this.bean.isMimeTypesSet()) {
               buf.append("MimeTypes");
               buf.append(String.valueOf(this.bean.getMimeTypes()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isServletClasspathSet()) {
               buf.append("ServletClasspath");
               buf.append(String.valueOf(this.bean.getServletClasspath()));
            }

            if (this.bean.isServletReloadCheckSecsSet()) {
               buf.append("ServletReloadCheckSecs");
               buf.append(String.valueOf(this.bean.getServletReloadCheckSecs()));
            }

            if (this.bean.isServletsSet()) {
               buf.append("Servlets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getServlets())));
            }

            if (this.bean.isSessionCacheSizeSet()) {
               buf.append("SessionCacheSize");
               buf.append(String.valueOf(this.bean.getSessionCacheSize()));
            }

            if (this.bean.isSessionCookieCommentSet()) {
               buf.append("SessionCookieComment");
               buf.append(String.valueOf(this.bean.getSessionCookieComment()));
            }

            if (this.bean.isSessionCookieDomainSet()) {
               buf.append("SessionCookieDomain");
               buf.append(String.valueOf(this.bean.getSessionCookieDomain()));
            }

            if (this.bean.isSessionCookieMaxAgeSecsSet()) {
               buf.append("SessionCookieMaxAgeSecs");
               buf.append(String.valueOf(this.bean.getSessionCookieMaxAgeSecs()));
            }

            if (this.bean.isSessionCookieNameSet()) {
               buf.append("SessionCookieName");
               buf.append(String.valueOf(this.bean.getSessionCookieName()));
            }

            if (this.bean.isSessionCookiePathSet()) {
               buf.append("SessionCookiePath");
               buf.append(String.valueOf(this.bean.getSessionCookiePath()));
            }

            if (this.bean.isSessionIDLengthSet()) {
               buf.append("SessionIDLength");
               buf.append(String.valueOf(this.bean.getSessionIDLength()));
            }

            if (this.bean.isSessionInvalidationIntervalSecsSet()) {
               buf.append("SessionInvalidationIntervalSecs");
               buf.append(String.valueOf(this.bean.getSessionInvalidationIntervalSecs()));
            }

            if (this.bean.isSessionJDBCConnectionTimeoutSecsSet()) {
               buf.append("SessionJDBCConnectionTimeoutSecs");
               buf.append(String.valueOf(this.bean.getSessionJDBCConnectionTimeoutSecs()));
            }

            if (this.bean.isSessionMainAttributeSet()) {
               buf.append("SessionMainAttribute");
               buf.append(String.valueOf(this.bean.getSessionMainAttribute()));
            }

            if (this.bean.isSessionPersistentStoreCookieNameSet()) {
               buf.append("SessionPersistentStoreCookieName");
               buf.append(String.valueOf(this.bean.getSessionPersistentStoreCookieName()));
            }

            if (this.bean.isSessionPersistentStoreDirSet()) {
               buf.append("SessionPersistentStoreDir");
               buf.append(String.valueOf(this.bean.getSessionPersistentStoreDir()));
            }

            if (this.bean.isSessionPersistentStorePoolSet()) {
               buf.append("SessionPersistentStorePool");
               buf.append(String.valueOf(this.bean.getSessionPersistentStorePool()));
            }

            if (this.bean.isSessionPersistentStoreTableSet()) {
               buf.append("SessionPersistentStoreTable");
               buf.append(String.valueOf(this.bean.getSessionPersistentStoreTable()));
            }

            if (this.bean.isSessionPersistentStoreTypeSet()) {
               buf.append("SessionPersistentStoreType");
               buf.append(String.valueOf(this.bean.getSessionPersistentStoreType()));
            }

            if (this.bean.isSessionSwapIntervalSecsSet()) {
               buf.append("SessionSwapIntervalSecs");
               buf.append(String.valueOf(this.bean.getSessionSwapIntervalSecs()));
            }

            if (this.bean.isSessionTimeoutSecsSet()) {
               buf.append("SessionTimeoutSecs");
               buf.append(String.valueOf(this.bean.getSessionTimeoutSecs()));
            }

            if (this.bean.isSingleThreadedServletPoolSizeSet()) {
               buf.append("SingleThreadedServletPoolSize");
               buf.append(String.valueOf(this.bean.getSingleThreadedServletPoolSize()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isVirtualHostsSet()) {
               buf.append("VirtualHosts");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getVirtualHosts())));
            }

            if (this.bean.isWebServersSet()) {
               buf.append("WebServers");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getWebServers())));
            }

            if (this.bean.isCleanupSessionFilesEnabledSet()) {
               buf.append("CleanupSessionFilesEnabled");
               buf.append(String.valueOf(this.bean.isCleanupSessionFilesEnabled()));
            }

            if (this.bean.isDebugEnabledSet()) {
               buf.append("DebugEnabled");
               buf.append(String.valueOf(this.bean.isDebugEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isIndexDirectoryEnabledSet()) {
               buf.append("IndexDirectoryEnabled");
               buf.append(String.valueOf(this.bean.isIndexDirectoryEnabled()));
            }

            if (this.bean.isJaxRsMonitoringDefaultBehaviorSet()) {
               buf.append("JaxRsMonitoringDefaultBehavior");
               buf.append(String.valueOf(this.bean.isJaxRsMonitoringDefaultBehavior()));
            }

            if (this.bean.isPreferWebInfClassesSet()) {
               buf.append("PreferWebInfClasses");
               buf.append(String.valueOf(this.bean.isPreferWebInfClasses()));
            }

            if (this.bean.isServletExtensionCaseSensitiveSet()) {
               buf.append("ServletExtensionCaseSensitive");
               buf.append(String.valueOf(this.bean.isServletExtensionCaseSensitive()));
            }

            if (this.bean.isSessionCookiesEnabledSet()) {
               buf.append("SessionCookiesEnabled");
               buf.append(String.valueOf(this.bean.isSessionCookiesEnabled()));
            }

            if (this.bean.isSessionDebuggableSet()) {
               buf.append("SessionDebuggable");
               buf.append(String.valueOf(this.bean.isSessionDebuggable()));
            }

            if (this.bean.isSessionMonitoringEnabledSet()) {
               buf.append("SessionMonitoringEnabled");
               buf.append(String.valueOf(this.bean.isSessionMonitoringEnabled()));
            }

            if (this.bean.isSessionPersistentStoreSharedSet()) {
               buf.append("SessionPersistentStoreShared");
               buf.append(String.valueOf(this.bean.isSessionPersistentStoreShared()));
            }

            if (this.bean.isSessionTrackingEnabledSet()) {
               buf.append("SessionTrackingEnabled");
               buf.append(String.valueOf(this.bean.isSessionTrackingEnabled()));
            }

            if (this.bean.isSessionURLRewritingEnabledSet()) {
               buf.append("SessionURLRewritingEnabled");
               buf.append(String.valueOf(this.bean.isSessionURLRewritingEnabled()));
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
            WebAppComponentMBeanImpl otherTyped = (WebAppComponentMBeanImpl)other;
            this.computeDiff("AuthFilter", this.bean.getAuthFilter(), otherTyped.getAuthFilter(), false);
            this.computeDiff("AuthRealmName", this.bean.getAuthRealmName(), otherTyped.getAuthRealmName(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("ServletReloadCheckSecs", this.bean.getServletReloadCheckSecs(), otherTyped.getServletReloadCheckSecs(), false);
            this.computeDiff("SingleThreadedServletPoolSize", this.bean.getSingleThreadedServletPoolSize(), otherTyped.getSingleThreadedServletPoolSize(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            this.computeDiff("VirtualHosts", this.bean.getVirtualHosts(), otherTyped.getVirtualHosts(), true);
            this.computeDiff("IndexDirectoryEnabled", this.bean.isIndexDirectoryEnabled(), otherTyped.isIndexDirectoryEnabled(), false);
            this.computeDiff("JaxRsMonitoringDefaultBehavior", this.bean.isJaxRsMonitoringDefaultBehavior(), otherTyped.isJaxRsMonitoringDefaultBehavior(), true);
            this.computeDiff("PreferWebInfClasses", this.bean.isPreferWebInfClasses(), otherTyped.isPreferWebInfClasses(), false);
            this.computeDiff("SessionMonitoringEnabled", this.bean.isSessionMonitoringEnabled(), otherTyped.isSessionMonitoringEnabled(), false);
            this.computeDiff("SessionURLRewritingEnabled", this.bean.isSessionURLRewritingEnabled(), otherTyped.isSessionURLRewritingEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebAppComponentMBeanImpl original = (WebAppComponentMBeanImpl)event.getSourceBean();
            WebAppComponentMBeanImpl proposed = (WebAppComponentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("ActivatedTargets") && !prop.equals("Application")) {
                  if (prop.equals("AuthFilter")) {
                     original.setAuthFilter(proposed.getAuthFilter());
                     original._conditionalUnset(update.isUnsetUpdate(), 34);
                  } else if (prop.equals("AuthRealmName")) {
                     original.setAuthRealmName(proposed.getAuthRealmName());
                     original._conditionalUnset(update.isUnsetUpdate(), 33);
                  } else if (!prop.equals("ContextPath") && !prop.equals("DefaultServlet") && !prop.equals("DeployedVirtualHosts") && !prop.equals("DocumentRoot") && !prop.equals("IndexFiles") && !prop.equals("MimeTypeDefault") && !prop.equals("MimeTypes")) {
                     if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 2);
                     } else if (!prop.equals("ServletClasspath")) {
                        if (prop.equals("ServletReloadCheckSecs")) {
                           original.setServletReloadCheckSecs(proposed.getServletReloadCheckSecs());
                           original._conditionalUnset(update.isUnsetUpdate(), 30);
                        } else if (!prop.equals("Servlets") && !prop.equals("SessionCacheSize") && !prop.equals("SessionCookieComment") && !prop.equals("SessionCookieDomain") && !prop.equals("SessionCookieMaxAgeSecs") && !prop.equals("SessionCookieName") && !prop.equals("SessionCookiePath") && !prop.equals("SessionIDLength") && !prop.equals("SessionInvalidationIntervalSecs") && !prop.equals("SessionJDBCConnectionTimeoutSecs") && !prop.equals("SessionMainAttribute") && !prop.equals("SessionPersistentStoreCookieName") && !prop.equals("SessionPersistentStoreDir") && !prop.equals("SessionPersistentStorePool") && !prop.equals("SessionPersistentStoreTable") && !prop.equals("SessionPersistentStoreType") && !prop.equals("SessionSwapIntervalSecs") && !prop.equals("SessionTimeoutSecs")) {
                           if (prop.equals("SingleThreadedServletPoolSize")) {
                              original.setSingleThreadedServletPoolSize(proposed.getSingleThreadedServletPoolSize());
                              original._conditionalUnset(update.isUnsetUpdate(), 31);
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
                           } else if (prop.equals("VirtualHosts")) {
                              original.setVirtualHostsAsString(proposed.getVirtualHostsAsString());
                              original._conditionalUnset(update.isUnsetUpdate(), 16);
                           } else if (!prop.equals("WebServers") && !prop.equals("CleanupSessionFilesEnabled") && !prop.equals("DebugEnabled") && !prop.equals("DynamicallyCreated")) {
                              if (prop.equals("IndexDirectoryEnabled")) {
                                 original.setIndexDirectoryEnabled(proposed.isIndexDirectoryEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 26);
                              } else if (prop.equals("JaxRsMonitoringDefaultBehavior")) {
                                 original.setJaxRsMonitoringDefaultBehavior(proposed.isJaxRsMonitoringDefaultBehavior());
                                 original._conditionalUnset(update.isUnsetUpdate(), 58);
                              } else if (prop.equals("PreferWebInfClasses")) {
                                 original.setPreferWebInfClasses(proposed.isPreferWebInfClasses());
                                 original._conditionalUnset(update.isUnsetUpdate(), 57);
                              } else if (!prop.equals("ServletExtensionCaseSensitive") && !prop.equals("SessionCookiesEnabled") && !prop.equals("SessionDebuggable")) {
                                 if (prop.equals("SessionMonitoringEnabled")) {
                                    original.setSessionMonitoringEnabled(proposed.isSessionMonitoringEnabled());
                                    original._conditionalUnset(update.isUnsetUpdate(), 56);
                                 } else if (!prop.equals("SessionPersistentStoreShared") && !prop.equals("SessionTrackingEnabled")) {
                                    if (prop.equals("SessionURLRewritingEnabled")) {
                                       original.setSessionURLRewritingEnabled(proposed.isSessionURLRewritingEnabled());
                                       original._conditionalUnset(update.isUnsetUpdate(), 36);
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
            WebAppComponentMBeanImpl copy = (WebAppComponentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AuthFilter")) && this.bean.isAuthFilterSet()) {
               copy.setAuthFilter(this.bean.getAuthFilter());
            }

            if ((excludeProps == null || !excludeProps.contains("AuthRealmName")) && this.bean.isAuthRealmNameSet()) {
               copy.setAuthRealmName(this.bean.getAuthRealmName());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServletReloadCheckSecs")) && this.bean.isServletReloadCheckSecsSet()) {
               copy.setServletReloadCheckSecs(this.bean.getServletReloadCheckSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("SingleThreadedServletPoolSize")) && this.bean.isSingleThreadedServletPoolSizeSet()) {
               copy.setSingleThreadedServletPoolSize(this.bean.getSingleThreadedServletPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("VirtualHosts")) && this.bean.isVirtualHostsSet()) {
               copy._unSet(copy, 16);
               copy.setVirtualHostsAsString(this.bean.getVirtualHostsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("IndexDirectoryEnabled")) && this.bean.isIndexDirectoryEnabledSet()) {
               copy.setIndexDirectoryEnabled(this.bean.isIndexDirectoryEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("JaxRsMonitoringDefaultBehavior")) && this.bean.isJaxRsMonitoringDefaultBehaviorSet()) {
               copy.setJaxRsMonitoringDefaultBehavior(this.bean.isJaxRsMonitoringDefaultBehavior());
            }

            if ((excludeProps == null || !excludeProps.contains("PreferWebInfClasses")) && this.bean.isPreferWebInfClassesSet()) {
               copy.setPreferWebInfClasses(this.bean.isPreferWebInfClasses());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionMonitoringEnabled")) && this.bean.isSessionMonitoringEnabledSet()) {
               copy.setSessionMonitoringEnabled(this.bean.isSessionMonitoringEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionURLRewritingEnabled")) && this.bean.isSessionURLRewritingEnabledSet()) {
               copy.setSessionURLRewritingEnabled(this.bean.isSessionURLRewritingEnabled());
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
         this.inferSubTree(this.bean.getDeployedVirtualHosts(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getVirtualHosts(), clazz, annotation);
         this.inferSubTree(this.bean.getWebServers(), clazz, annotation);
      }
   }
}
