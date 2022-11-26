package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.cluster.migration.MTCustomValidator;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.MigratableTarget;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class MigratableTargetMBeanImpl extends SingletonServiceBaseMBeanImpl implements MigratableTargetMBean, Serializable {
   private ServerMBean[] _AllCandidateServers;
   private ClusterMBean _Cluster;
   private ServerMBean[] _ConstrainedCandidateServers;
   private boolean _Critical;
   private ServerMBean _DestinationServer;
   private boolean _DynamicallyCreated;
   private ServerMBean _HostingServer;
   private String _MigrationPolicy;
   private String _Name;
   private boolean _NonLocalPostAllowed;
   private int _NumberOfRestartAttempts;
   private String _PostScript;
   private boolean _PostScriptFailureFatal;
   private String _PreScript;
   private boolean _RestartOnFailure;
   private int _SecondsBetweenRestarts;
   private Set _ServerNames;
   private String[] _Tags;
   private ServerMBean _UserPreferredServer;
   private transient MigratableTarget _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private MigratableTargetMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(MigratableTargetMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(MigratableTargetMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public MigratableTargetMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(MigratableTargetMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      MigratableTargetMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._DestinationServer instanceof ServerMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getDestinationServer() != null) {
            this._getReferenceManager().unregisterBean((ServerMBeanImpl)oldDelegate.getDestinationServer());
         }

         if (delegate != null && delegate.getDestinationServer() != null) {
            this._getReferenceManager().registerBean((ServerMBeanImpl)delegate.getDestinationServer(), false);
         }

         ((ServerMBeanImpl)this._DestinationServer)._setDelegateBean((ServerMBeanImpl)((ServerMBeanImpl)(delegate == null ? null : delegate.getDestinationServer())));
      }

      if (this._HostingServer instanceof ServerMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getHostingServer() != null) {
            this._getReferenceManager().unregisterBean((ServerMBeanImpl)oldDelegate.getHostingServer());
         }

         if (delegate != null && delegate.getHostingServer() != null) {
            this._getReferenceManager().registerBean((ServerMBeanImpl)delegate.getHostingServer(), false);
         }

         ((ServerMBeanImpl)this._HostingServer)._setDelegateBean((ServerMBeanImpl)((ServerMBeanImpl)(delegate == null ? null : delegate.getHostingServer())));
      }

      if (this._UserPreferredServer instanceof ServerMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getUserPreferredServer() != null) {
            this._getReferenceManager().unregisterBean((ServerMBeanImpl)oldDelegate.getUserPreferredServer());
         }

         if (delegate != null && delegate.getUserPreferredServer() != null) {
            this._getReferenceManager().registerBean((ServerMBeanImpl)delegate.getUserPreferredServer(), false);
         }

         ((ServerMBeanImpl)this._UserPreferredServer)._setDelegateBean((ServerMBeanImpl)((ServerMBeanImpl)(delegate == null ? null : delegate.getUserPreferredServer())));
      }

   }

   public MigratableTargetMBeanImpl() {
      try {
         this._customizer = new MigratableTarget(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public MigratableTargetMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new MigratableTarget(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public MigratableTargetMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new MigratableTarget(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public ServerMBean[] getConstrainedCandidateServers() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getConstrainedCandidateServers() : this._ConstrainedCandidateServers;
   }

   public String getConstrainedCandidateServersAsString() {
      return this._getHelper()._serializeKeyList(this.getConstrainedCandidateServers());
   }

   public ServerMBean getHostingServer() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getHostingServer() : this._customizer.getHostingServer();
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

   public Set getServerNames() {
      return this._customizer.getServerNames();
   }

   public boolean isConstrainedCandidateServersInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isConstrainedCandidateServersSet() {
      return this._isSet(15);
   }

   public boolean isHostingServerInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isHostingServerSet() {
      return this._isSet(10);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isServerNamesInherited() {
      return false;
   }

   public boolean isServerNamesSet() {
      return this._isSet(14);
   }

   public void setConstrainedCandidateServersAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._ConstrainedCandidateServers);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, ServerMBean.class, new ReferenceManager.Resolver(this, 15, param0) {
                  public void resolveReference(Object value) {
                     try {
                        MigratableTargetMBeanImpl.this.addConstrainedCandidateServer((ServerMBean)value);
                        MigratableTargetMBeanImpl.this._getHelper().reorderArrayObjects((Object[])MigratableTargetMBeanImpl.this._ConstrainedCandidateServers, this.getHandbackObject());
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
               ServerMBean[] var6 = this._ConstrainedCandidateServers;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  ServerMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeConstrainedCandidateServer(member);
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
         ServerMBean[] _oldVal = this._ConstrainedCandidateServers;
         this._initializeProperty(15);
         this._postSet(15, _oldVal, this._ConstrainedCandidateServers);
      }
   }

   public void setServerNames(Set param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ServerNames = param0;
   }

   public void setConstrainedCandidateServers(ServerMBean[] param0) throws InvalidAttributeValueException {
      ServerMBean[] param0 = param0 == null ? new ServerMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      param0 = (ServerMBean[])((ServerMBean[])this._getHelper()._cleanAndValidateArray(param0, ServerMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 15, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return MigratableTargetMBeanImpl.this.getConstrainedCandidateServers();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      boolean wasSet = this._isSet(15);
      ServerMBean[] _oldVal = this._ConstrainedCandidateServers;
      this._ConstrainedCandidateServers = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public void setHostingServer(ServerMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._HostingServer = param0;
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
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addConstrainedCandidateServer(ServerMBean param0) throws InvalidAttributeValueException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         ServerMBean[] _new;
         if (this._isSet(15)) {
            _new = (ServerMBean[])((ServerMBean[])this._getHelper()._extendArray(this.getConstrainedCandidateServers(), ServerMBean.class, param0));
         } else {
            _new = new ServerMBean[]{param0};
         }

         try {
            this.setConstrainedCandidateServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public ServerMBean getUserPreferredServer() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().getUserPreferredServer() : this._customizer.getUserPreferredServer();
   }

   public String getUserPreferredServerAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getUserPreferredServer();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isUserPreferredServerInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isUserPreferredServerSet() {
      return this._isSet(11);
   }

   public void setUserPreferredServerAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ServerMBean.class, new ReferenceManager.Resolver(this, 11) {
            public void resolveReference(Object value) {
               try {
                  MigratableTargetMBeanImpl.this.setUserPreferredServer((ServerMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         ServerMBean _oldVal = this._UserPreferredServer;
         this._initializeProperty(11);
         this._postSet(11, _oldVal, this._UserPreferredServer);
      }

   }

   public boolean removeConstrainedCandidateServer(ServerMBean param0) throws InvalidAttributeValueException {
      ServerMBean[] _old = this.getConstrainedCandidateServers();
      ServerMBean[] _new = (ServerMBean[])((ServerMBean[])this._getHelper()._removeElement(_old, ServerMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setConstrainedCandidateServers(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setUserPreferredServer(ServerMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 11, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return MigratableTargetMBeanImpl.this.getUserPreferredServer();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(11);
      ServerMBean _oldVal = this.getUserPreferredServer();
      this._customizer.setUserPreferredServer(param0);
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public ClusterMBean getCluster() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getCluster() : this._Cluster;
   }

   public String getClusterAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getCluster();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isClusterInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isClusterSet() {
      return this._isSet(16);
   }

   public void setClusterAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ClusterMBean.class, new ReferenceManager.Resolver(this, 16) {
            public void resolveReference(Object value) {
               try {
                  MigratableTargetMBeanImpl.this.setCluster((ClusterMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         ClusterMBean _oldVal = this._Cluster;
         this._initializeProperty(16);
         this._postSet(16, _oldVal, this._Cluster);
      }

   }

   public void setCluster(ClusterMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 16, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return MigratableTargetMBeanImpl.this.getCluster();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(16);
      ClusterMBean _oldVal = this._Cluster;
      this._Cluster = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public void addAllCandidateServer(ServerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         ServerMBean[] _new = (ServerMBean[])((ServerMBean[])this._getHelper()._extendArray(this.getAllCandidateServers(), ServerMBean.class, param0));

         try {
            this.setAllCandidateServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServerMBean[] getAllCandidateServers() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getAllCandidateServers() : this._customizer.getAllCandidateServers();
   }

   public boolean isAllCandidateServersInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isAllCandidateServersSet() {
      return this._isSet(17);
   }

   public void removeAllCandidateServer(ServerMBean param0) {
      ServerMBean[] _old = this.getAllCandidateServers();
      ServerMBean[] _new = (ServerMBean[])((ServerMBean[])this._getHelper()._removeElement(_old, ServerMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setAllCandidateServers(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setAllCandidateServers(ServerMBean[] param0) {
      ServerMBean[] param0 = param0 == null ? new ServerMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._AllCandidateServers = (ServerMBean[])param0;
   }

   public boolean isManualActiveOn(ServerMBean param0) {
      return this._customizer.isManualActiveOn(param0);
   }

   public boolean isCandidate(ServerMBean param0) {
      return this._customizer.isCandidate(param0);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public ServerMBean getDestinationServer() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getDestinationServer() : this._DestinationServer;
   }

   public String getDestinationServerAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getDestinationServer();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isDestinationServerInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isDestinationServerSet() {
      return this._isSet(18);
   }

   public void setDestinationServerAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ServerMBean.class, new ReferenceManager.Resolver(this, 18) {
            public void resolveReference(Object value) {
               try {
                  MigratableTargetMBeanImpl.this.setDestinationServer((ServerMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         ServerMBean _oldVal = this._DestinationServer;
         this._initializeProperty(18);
         this._postSet(18, _oldVal, this._DestinationServer);
      }

   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setDestinationServer(ServerMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 18, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return MigratableTargetMBeanImpl.this.getDestinationServer();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(18);
      ServerMBean _oldVal = this._DestinationServer;
      this._DestinationServer = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMigrationPolicy() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._performMacroSubstitution(this._getDelegateBean().getMigrationPolicy(), this) : this._MigrationPolicy;
   }

   public boolean isMigrationPolicyInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isMigrationPolicySet() {
      return this._isSet(19);
   }

   public void setMigrationPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"manual", "exactly-once", "failure-recovery", "shutdown-recovery"};
      param0 = LegalChecks.checkInEnum("MigrationPolicy", param0, _set);
      MTCustomValidator.validateMigrationPolicy(this, param0);
      boolean wasSet = this._isSet(19);
      String _oldVal = this._MigrationPolicy;
      this._MigrationPolicy = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var5.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPreScript() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._performMacroSubstitution(this._getDelegateBean().getPreScript(), this) : this._PreScript;
   }

   public boolean isPreScriptInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isPreScriptSet() {
      return this._isSet(20);
   }

   public void setPreScript(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      MTCustomValidator.validateScriptPath(param0);
      boolean wasSet = this._isSet(20);
      String _oldVal = this._PreScript;
      this._PreScript = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPostScript() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._performMacroSubstitution(this._getDelegateBean().getPostScript(), this) : this._PostScript;
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

   public boolean isPostScriptInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isPostScriptSet() {
      return this._isSet(21);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setPostScript(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      MTCustomValidator.validateScriptPath(param0);
      boolean wasSet = this._isSet(21);
      String _oldVal = this._PostScript;
      this._PostScript = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isPostScriptFailureFatal() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._getDelegateBean().isPostScriptFailureFatal() : this._PostScriptFailureFatal;
   }

   public boolean isPostScriptFailureFatalInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isPostScriptFailureFatalSet() {
      return this._isSet(22);
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setPostScriptFailureFatal(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      boolean _oldVal = this._PostScriptFailureFatal;
      this._PostScriptFailureFatal = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
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
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
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

   public boolean isNonLocalPostAllowed() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().isNonLocalPostAllowed() : this._NonLocalPostAllowed;
   }

   public boolean isNonLocalPostAllowedInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isNonLocalPostAllowedSet() {
      return this._isSet(23);
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

   public void setNonLocalPostAllowed(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(23);
      boolean _oldVal = this._NonLocalPostAllowed;
      this._NonLocalPostAllowed = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getRestartOnFailure() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._getDelegateBean().getRestartOnFailure() : this._RestartOnFailure;
   }

   public boolean isRestartOnFailureInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isRestartOnFailureSet() {
      return this._isSet(24);
   }

   public void setRestartOnFailure(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(24);
      boolean _oldVal = this._RestartOnFailure;
      this._RestartOnFailure = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSecondsBetweenRestarts() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getSecondsBetweenRestarts() : this._SecondsBetweenRestarts;
   }

   public boolean isSecondsBetweenRestartsInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isSecondsBetweenRestartsSet() {
      return this._isSet(25);
   }

   public void setSecondsBetweenRestarts(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(25);
      int _oldVal = this._SecondsBetweenRestarts;
      this._SecondsBetweenRestarts = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public int getNumberOfRestartAttempts() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().getNumberOfRestartAttempts() : this._NumberOfRestartAttempts;
   }

   public boolean isNumberOfRestartAttemptsInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isNumberOfRestartAttemptsSet() {
      return this._isSet(26);
   }

   public void setNumberOfRestartAttempts(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(26);
      int _oldVal = this._NumberOfRestartAttempts;
      this._NumberOfRestartAttempts = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isCritical() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().isCritical() : this._Critical;
   }

   public boolean isCriticalInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isCriticalSet() {
      return this._isSet(27);
   }

   public void setCritical(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      MTCustomValidator.validateCritical(this, param0);
      boolean wasSet = this._isSet(27);
      boolean _oldVal = this._Critical;
      this._Critical = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MigratableTargetMBeanImpl source = (MigratableTargetMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      MTCustomValidator.validateMigratableTarget(this);
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
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._AllCandidateServers = new ServerMBean[0];
               if (initOne) {
                  break;
               }
            case 16:
               this._Cluster = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._ConstrainedCandidateServers = new ServerMBean[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._DestinationServer = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._HostingServer = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._MigrationPolicy = "manual";
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 26:
               this._NumberOfRestartAttempts = 6;
               if (initOne) {
                  break;
               }
            case 21:
               this._PostScript = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._PreScript = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._RestartOnFailure = false;
               if (initOne) {
                  break;
               }
            case 25:
               this._SecondsBetweenRestarts = 30;
               if (initOne) {
                  break;
               }
            case 14:
               this._ServerNames = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 11:
               this._customizer.setUserPreferredServer((ServerMBean)null);
               if (initOne) {
                  break;
               }
            case 27:
               this._Critical = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 23:
               this._NonLocalPostAllowed = false;
               if (initOne) {
                  break;
               }
            case 22:
               this._PostScriptFailureFatal = true;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 12:
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
      return "MigratableTarget";
   }

   public void putValue(String name, Object v) {
      ServerMBean[] oldVal;
      if (name.equals("AllCandidateServers")) {
         oldVal = this._AllCandidateServers;
         this._AllCandidateServers = (ServerMBean[])((ServerMBean[])v);
         this._postSet(17, oldVal, this._AllCandidateServers);
      } else if (name.equals("Cluster")) {
         ClusterMBean oldVal = this._Cluster;
         this._Cluster = (ClusterMBean)v;
         this._postSet(16, oldVal, this._Cluster);
      } else if (name.equals("ConstrainedCandidateServers")) {
         oldVal = this._ConstrainedCandidateServers;
         this._ConstrainedCandidateServers = (ServerMBean[])((ServerMBean[])v);
         this._postSet(15, oldVal, this._ConstrainedCandidateServers);
      } else {
         boolean oldVal;
         if (name.equals("Critical")) {
            oldVal = this._Critical;
            this._Critical = (Boolean)v;
            this._postSet(27, oldVal, this._Critical);
         } else {
            ServerMBean oldVal;
            if (name.equals("DestinationServer")) {
               oldVal = this._DestinationServer;
               this._DestinationServer = (ServerMBean)v;
               this._postSet(18, oldVal, this._DestinationServer);
            } else if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("HostingServer")) {
               oldVal = this._HostingServer;
               this._HostingServer = (ServerMBean)v;
               this._postSet(10, oldVal, this._HostingServer);
            } else {
               String oldVal;
               if (name.equals("MigrationPolicy")) {
                  oldVal = this._MigrationPolicy;
                  this._MigrationPolicy = (String)v;
                  this._postSet(19, oldVal, this._MigrationPolicy);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("NonLocalPostAllowed")) {
                  oldVal = this._NonLocalPostAllowed;
                  this._NonLocalPostAllowed = (Boolean)v;
                  this._postSet(23, oldVal, this._NonLocalPostAllowed);
               } else {
                  int oldVal;
                  if (name.equals("NumberOfRestartAttempts")) {
                     oldVal = this._NumberOfRestartAttempts;
                     this._NumberOfRestartAttempts = (Integer)v;
                     this._postSet(26, oldVal, this._NumberOfRestartAttempts);
                  } else if (name.equals("PostScript")) {
                     oldVal = this._PostScript;
                     this._PostScript = (String)v;
                     this._postSet(21, oldVal, this._PostScript);
                  } else if (name.equals("PostScriptFailureFatal")) {
                     oldVal = this._PostScriptFailureFatal;
                     this._PostScriptFailureFatal = (Boolean)v;
                     this._postSet(22, oldVal, this._PostScriptFailureFatal);
                  } else if (name.equals("PreScript")) {
                     oldVal = this._PreScript;
                     this._PreScript = (String)v;
                     this._postSet(20, oldVal, this._PreScript);
                  } else if (name.equals("RestartOnFailure")) {
                     oldVal = this._RestartOnFailure;
                     this._RestartOnFailure = (Boolean)v;
                     this._postSet(24, oldVal, this._RestartOnFailure);
                  } else if (name.equals("SecondsBetweenRestarts")) {
                     oldVal = this._SecondsBetweenRestarts;
                     this._SecondsBetweenRestarts = (Integer)v;
                     this._postSet(25, oldVal, this._SecondsBetweenRestarts);
                  } else if (name.equals("ServerNames")) {
                     Set oldVal = this._ServerNames;
                     this._ServerNames = (Set)v;
                     this._postSet(14, oldVal, this._ServerNames);
                  } else if (name.equals("Tags")) {
                     String[] oldVal = this._Tags;
                     this._Tags = (String[])((String[])v);
                     this._postSet(9, oldVal, this._Tags);
                  } else if (name.equals("UserPreferredServer")) {
                     oldVal = this._UserPreferredServer;
                     this._UserPreferredServer = (ServerMBean)v;
                     this._postSet(11, oldVal, this._UserPreferredServer);
                  } else if (name.equals("customizer")) {
                     MigratableTarget oldVal = this._customizer;
                     this._customizer = (MigratableTarget)v;
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AllCandidateServers")) {
         return this._AllCandidateServers;
      } else if (name.equals("Cluster")) {
         return this._Cluster;
      } else if (name.equals("ConstrainedCandidateServers")) {
         return this._ConstrainedCandidateServers;
      } else if (name.equals("Critical")) {
         return new Boolean(this._Critical);
      } else if (name.equals("DestinationServer")) {
         return this._DestinationServer;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("HostingServer")) {
         return this._HostingServer;
      } else if (name.equals("MigrationPolicy")) {
         return this._MigrationPolicy;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("NonLocalPostAllowed")) {
         return new Boolean(this._NonLocalPostAllowed);
      } else if (name.equals("NumberOfRestartAttempts")) {
         return new Integer(this._NumberOfRestartAttempts);
      } else if (name.equals("PostScript")) {
         return this._PostScript;
      } else if (name.equals("PostScriptFailureFatal")) {
         return new Boolean(this._PostScriptFailureFatal);
      } else if (name.equals("PreScript")) {
         return this._PreScript;
      } else if (name.equals("RestartOnFailure")) {
         return new Boolean(this._RestartOnFailure);
      } else if (name.equals("SecondsBetweenRestarts")) {
         return new Integer(this._SecondsBetweenRestarts);
      } else if (name.equals("ServerNames")) {
         return this._ServerNames;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("UserPreferredServer")) {
         return this._UserPreferredServer;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends SingletonServiceBaseMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 9:
            case 13:
            case 15:
            case 17:
            case 23:
            case 27:
            default:
               break;
            case 7:
               if (s.equals("cluster")) {
                  return 16;
               }
               break;
            case 8:
               if (s.equals("critical")) {
                  return 27;
               }
               break;
            case 10:
               if (s.equals("pre-script")) {
                  return 20;
               }
               break;
            case 11:
               if (s.equals("post-script")) {
                  return 21;
               }
               break;
            case 12:
               if (s.equals("server-names")) {
                  return 14;
               }
               break;
            case 14:
               if (s.equals("hosting-server")) {
                  return 10;
               }
               break;
            case 16:
               if (s.equals("migration-policy")) {
                  return 19;
               }
               break;
            case 18:
               if (s.equals("destination-server")) {
                  return 18;
               }

               if (s.equals("restart-on-failure")) {
                  return 24;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("all-candidate-server")) {
                  return 17;
               }
               break;
            case 21:
               if (s.equals("user-preferred-server")) {
                  return 11;
               }
               break;
            case 22:
               if (s.equals("non-local-post-allowed")) {
                  return 23;
               }
               break;
            case 24:
               if (s.equals("seconds-between-restarts")) {
                  return 25;
               }
               break;
            case 25:
               if (s.equals("post-script-failure-fatal")) {
                  return 22;
               }
               break;
            case 26:
               if (s.equals("number-of-restart-attempts")) {
                  return 26;
               }
               break;
            case 28:
               if (s.equals("constrained-candidate-server")) {
                  return 15;
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
            case 12:
            case 13:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "hosting-server";
            case 11:
               return "user-preferred-server";
            case 14:
               return "server-names";
            case 15:
               return "constrained-candidate-server";
            case 16:
               return "cluster";
            case 17:
               return "all-candidate-server";
            case 18:
               return "destination-server";
            case 19:
               return "migration-policy";
            case 20:
               return "pre-script";
            case 21:
               return "post-script";
            case 22:
               return "post-script-failure-fatal";
            case 23:
               return "non-local-post-allowed";
            case 24:
               return "restart-on-failure";
            case 25:
               return "seconds-between-restarts";
            case 26:
               return "number-of-restart-attempts";
            case 27:
               return "critical";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 15:
               return true;
            case 17:
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

   protected static class Helper extends SingletonServiceBaseMBeanImpl.Helper {
      private MigratableTargetMBeanImpl bean;

      protected Helper(MigratableTargetMBeanImpl bean) {
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
            case 12:
            case 13:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "HostingServer";
            case 11:
               return "UserPreferredServer";
            case 14:
               return "ServerNames";
            case 15:
               return "ConstrainedCandidateServers";
            case 16:
               return "Cluster";
            case 17:
               return "AllCandidateServers";
            case 18:
               return "DestinationServer";
            case 19:
               return "MigrationPolicy";
            case 20:
               return "PreScript";
            case 21:
               return "PostScript";
            case 22:
               return "PostScriptFailureFatal";
            case 23:
               return "NonLocalPostAllowed";
            case 24:
               return "RestartOnFailure";
            case 25:
               return "SecondsBetweenRestarts";
            case 26:
               return "NumberOfRestartAttempts";
            case 27:
               return "Critical";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AllCandidateServers")) {
            return 17;
         } else if (propName.equals("Cluster")) {
            return 16;
         } else if (propName.equals("ConstrainedCandidateServers")) {
            return 15;
         } else if (propName.equals("DestinationServer")) {
            return 18;
         } else if (propName.equals("HostingServer")) {
            return 10;
         } else if (propName.equals("MigrationPolicy")) {
            return 19;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("NumberOfRestartAttempts")) {
            return 26;
         } else if (propName.equals("PostScript")) {
            return 21;
         } else if (propName.equals("PreScript")) {
            return 20;
         } else if (propName.equals("RestartOnFailure")) {
            return 24;
         } else if (propName.equals("SecondsBetweenRestarts")) {
            return 25;
         } else if (propName.equals("ServerNames")) {
            return 14;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("UserPreferredServer")) {
            return 11;
         } else if (propName.equals("Critical")) {
            return 27;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("NonLocalPostAllowed")) {
            return 23;
         } else {
            return propName.equals("PostScriptFailureFatal") ? 22 : super.getPropertyIndex(propName);
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
            if (this.bean.isAllCandidateServersSet()) {
               buf.append("AllCandidateServers");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAllCandidateServers())));
            }

            if (this.bean.isClusterSet()) {
               buf.append("Cluster");
               buf.append(String.valueOf(this.bean.getCluster()));
            }

            if (this.bean.isConstrainedCandidateServersSet()) {
               buf.append("ConstrainedCandidateServers");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getConstrainedCandidateServers())));
            }

            if (this.bean.isDestinationServerSet()) {
               buf.append("DestinationServer");
               buf.append(String.valueOf(this.bean.getDestinationServer()));
            }

            if (this.bean.isHostingServerSet()) {
               buf.append("HostingServer");
               buf.append(String.valueOf(this.bean.getHostingServer()));
            }

            if (this.bean.isMigrationPolicySet()) {
               buf.append("MigrationPolicy");
               buf.append(String.valueOf(this.bean.getMigrationPolicy()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNumberOfRestartAttemptsSet()) {
               buf.append("NumberOfRestartAttempts");
               buf.append(String.valueOf(this.bean.getNumberOfRestartAttempts()));
            }

            if (this.bean.isPostScriptSet()) {
               buf.append("PostScript");
               buf.append(String.valueOf(this.bean.getPostScript()));
            }

            if (this.bean.isPreScriptSet()) {
               buf.append("PreScript");
               buf.append(String.valueOf(this.bean.getPreScript()));
            }

            if (this.bean.isRestartOnFailureSet()) {
               buf.append("RestartOnFailure");
               buf.append(String.valueOf(this.bean.getRestartOnFailure()));
            }

            if (this.bean.isSecondsBetweenRestartsSet()) {
               buf.append("SecondsBetweenRestarts");
               buf.append(String.valueOf(this.bean.getSecondsBetweenRestarts()));
            }

            if (this.bean.isServerNamesSet()) {
               buf.append("ServerNames");
               buf.append(String.valueOf(this.bean.getServerNames()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isUserPreferredServerSet()) {
               buf.append("UserPreferredServer");
               buf.append(String.valueOf(this.bean.getUserPreferredServer()));
            }

            if (this.bean.isCriticalSet()) {
               buf.append("Critical");
               buf.append(String.valueOf(this.bean.isCritical()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isNonLocalPostAllowedSet()) {
               buf.append("NonLocalPostAllowed");
               buf.append(String.valueOf(this.bean.isNonLocalPostAllowed()));
            }

            if (this.bean.isPostScriptFailureFatalSet()) {
               buf.append("PostScriptFailureFatal");
               buf.append(String.valueOf(this.bean.isPostScriptFailureFatal()));
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
            MigratableTargetMBeanImpl otherTyped = (MigratableTargetMBeanImpl)other;
            this.computeDiff("Cluster", this.bean.getCluster(), otherTyped.getCluster(), false);
            this.computeDiff("ConstrainedCandidateServers", this.bean.getConstrainedCandidateServers(), otherTyped.getConstrainedCandidateServers(), false);
            this.computeDiff("DestinationServer", this.bean.getDestinationServer(), otherTyped.getDestinationServer(), true);
            this.computeDiff("MigrationPolicy", this.bean.getMigrationPolicy(), otherTyped.getMigrationPolicy(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("NumberOfRestartAttempts", this.bean.getNumberOfRestartAttempts(), otherTyped.getNumberOfRestartAttempts(), true);
            this.computeDiff("PostScript", this.bean.getPostScript(), otherTyped.getPostScript(), false);
            this.computeDiff("PreScript", this.bean.getPreScript(), otherTyped.getPreScript(), false);
            this.computeDiff("RestartOnFailure", this.bean.getRestartOnFailure(), otherTyped.getRestartOnFailure(), true);
            this.computeDiff("SecondsBetweenRestarts", this.bean.getSecondsBetweenRestarts(), otherTyped.getSecondsBetweenRestarts(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("UserPreferredServer", this.bean.getUserPreferredServer(), otherTyped.getUserPreferredServer(), true);
            this.computeDiff("Critical", this.bean.isCritical(), otherTyped.isCritical(), false);
            this.computeDiff("NonLocalPostAllowed", this.bean.isNonLocalPostAllowed(), otherTyped.isNonLocalPostAllowed(), false);
            this.computeDiff("PostScriptFailureFatal", this.bean.isPostScriptFailureFatal(), otherTyped.isPostScriptFailureFatal(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MigratableTargetMBeanImpl original = (MigratableTargetMBeanImpl)event.getSourceBean();
            MigratableTargetMBeanImpl proposed = (MigratableTargetMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("AllCandidateServers")) {
                  if (prop.equals("Cluster")) {
                     original.setClusterAsString(proposed.getClusterAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("ConstrainedCandidateServers")) {
                     original.setConstrainedCandidateServersAsString(proposed.getConstrainedCandidateServersAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("DestinationServer")) {
                     original.setDestinationServerAsString(proposed.getDestinationServerAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  } else if (!prop.equals("HostingServer")) {
                     if (prop.equals("MigrationPolicy")) {
                        original.setMigrationPolicy(proposed.getMigrationPolicy());
                        original._conditionalUnset(update.isUnsetUpdate(), 19);
                     } else if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 2);
                     } else if (prop.equals("NumberOfRestartAttempts")) {
                        original.setNumberOfRestartAttempts(proposed.getNumberOfRestartAttempts());
                        original._conditionalUnset(update.isUnsetUpdate(), 26);
                     } else if (prop.equals("PostScript")) {
                        original.setPostScript(proposed.getPostScript());
                        original._conditionalUnset(update.isUnsetUpdate(), 21);
                     } else if (prop.equals("PreScript")) {
                        original.setPreScript(proposed.getPreScript());
                        original._conditionalUnset(update.isUnsetUpdate(), 20);
                     } else if (prop.equals("RestartOnFailure")) {
                        original.setRestartOnFailure(proposed.getRestartOnFailure());
                        original._conditionalUnset(update.isUnsetUpdate(), 24);
                     } else if (prop.equals("SecondsBetweenRestarts")) {
                        original.setSecondsBetweenRestarts(proposed.getSecondsBetweenRestarts());
                        original._conditionalUnset(update.isUnsetUpdate(), 25);
                     } else if (!prop.equals("ServerNames")) {
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
                        } else if (prop.equals("UserPreferredServer")) {
                           original.setUserPreferredServerAsString(proposed.getUserPreferredServerAsString());
                           original._conditionalUnset(update.isUnsetUpdate(), 11);
                        } else if (prop.equals("Critical")) {
                           original.setCritical(proposed.isCritical());
                           original._conditionalUnset(update.isUnsetUpdate(), 27);
                        } else if (!prop.equals("DynamicallyCreated")) {
                           if (prop.equals("NonLocalPostAllowed")) {
                              original.setNonLocalPostAllowed(proposed.isNonLocalPostAllowed());
                              original._conditionalUnset(update.isUnsetUpdate(), 23);
                           } else if (prop.equals("PostScriptFailureFatal")) {
                              original.setPostScriptFailureFatal(proposed.isPostScriptFailureFatal());
                              original._conditionalUnset(update.isUnsetUpdate(), 22);
                           } else {
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
            MigratableTargetMBeanImpl copy = (MigratableTargetMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Cluster")) && this.bean.isClusterSet()) {
               copy._unSet(copy, 16);
               copy.setClusterAsString(this.bean.getClusterAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ConstrainedCandidateServers")) && this.bean.isConstrainedCandidateServersSet()) {
               copy._unSet(copy, 15);
               copy.setConstrainedCandidateServersAsString(this.bean.getConstrainedCandidateServersAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationServer")) && this.bean.isDestinationServerSet()) {
               copy._unSet(copy, 18);
               copy.setDestinationServerAsString(this.bean.getDestinationServerAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("MigrationPolicy")) && this.bean.isMigrationPolicySet()) {
               copy.setMigrationPolicy(this.bean.getMigrationPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("NumberOfRestartAttempts")) && this.bean.isNumberOfRestartAttemptsSet()) {
               copy.setNumberOfRestartAttempts(this.bean.getNumberOfRestartAttempts());
            }

            if ((excludeProps == null || !excludeProps.contains("PostScript")) && this.bean.isPostScriptSet()) {
               copy.setPostScript(this.bean.getPostScript());
            }

            if ((excludeProps == null || !excludeProps.contains("PreScript")) && this.bean.isPreScriptSet()) {
               copy.setPreScript(this.bean.getPreScript());
            }

            if ((excludeProps == null || !excludeProps.contains("RestartOnFailure")) && this.bean.isRestartOnFailureSet()) {
               copy.setRestartOnFailure(this.bean.getRestartOnFailure());
            }

            if ((excludeProps == null || !excludeProps.contains("SecondsBetweenRestarts")) && this.bean.isSecondsBetweenRestartsSet()) {
               copy.setSecondsBetweenRestarts(this.bean.getSecondsBetweenRestarts());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UserPreferredServer")) && this.bean.isUserPreferredServerSet()) {
               copy._unSet(copy, 11);
               copy.setUserPreferredServerAsString(this.bean.getUserPreferredServerAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Critical")) && this.bean.isCriticalSet()) {
               copy.setCritical(this.bean.isCritical());
            }

            if ((excludeProps == null || !excludeProps.contains("NonLocalPostAllowed")) && this.bean.isNonLocalPostAllowedSet()) {
               copy.setNonLocalPostAllowed(this.bean.isNonLocalPostAllowed());
            }

            if ((excludeProps == null || !excludeProps.contains("PostScriptFailureFatal")) && this.bean.isPostScriptFailureFatalSet()) {
               copy.setPostScriptFailureFatal(this.bean.isPostScriptFailureFatal());
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
         this.inferSubTree(this.bean.getAllCandidateServers(), clazz, annotation);
         this.inferSubTree(this.bean.getCluster(), clazz, annotation);
         this.inferSubTree(this.bean.getConstrainedCandidateServers(), clazz, annotation);
         this.inferSubTree(this.bean.getDestinationServer(), clazz, annotation);
         this.inferSubTree(this.bean.getHostingServer(), clazz, annotation);
         this.inferSubTree(this.bean.getUserPreferredServer(), clazz, annotation);
      }
   }
}
