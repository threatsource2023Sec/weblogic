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
import weblogic.management.mbeans.custom.SingletonService;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SingletonServiceAppScopedMBeanImpl extends SingletonServiceBaseMBeanImpl implements SingletonServiceAppScopedMBean, Serializable {
   private String _ClassName;
   private String _CompatibilityName;
   private boolean _DynamicallyCreated;
   private ServerMBean _HostingServer;
   private String _ModuleType;
   private String _Name;
   private SubDeploymentMBean[] _SubDeployments;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private boolean _Untargeted;
   private ServerMBean _UserPreferredServer;
   private transient SingletonService _customizer;
   private static SchemaHelper2 _schemaHelper;

   public SingletonServiceAppScopedMBeanImpl() {
      try {
         this._customizer = new SingletonService(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public SingletonServiceAppScopedMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new SingletonService(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public SingletonServiceAppScopedMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new SingletonService(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getClassName() {
      if (!this._isSet(19)) {
         try {
            return this.getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._ClassName;
   }

   public ServerMBean getHostingServer() {
      return this._customizer.getHostingServer();
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

   public boolean isClassNameInherited() {
      return false;
   }

   public boolean isClassNameSet() {
      return this._isSet(19);
   }

   public boolean isHostingServerInherited() {
      return false;
   }

   public boolean isHostingServerSet() {
      return this._isSet(10);
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void addSubDeployment(SubDeploymentMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         SubDeploymentMBean[] _new;
         if (this._isSet(17)) {
            _new = (SubDeploymentMBean[])((SubDeploymentMBean[])this._getHelper()._extendArray(this.getSubDeployments(), SubDeploymentMBean.class, param0));
         } else {
            _new = new SubDeploymentMBean[]{param0};
         }

         try {
            this.setSubDeployments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SubDeploymentMBean[] getSubDeployments() {
      return this._SubDeployments;
   }

   public boolean isSubDeploymentsInherited() {
      return false;
   }

   public boolean isSubDeploymentsSet() {
      return this._isSet(17);
   }

   public void removeSubDeployment(SubDeploymentMBean param0) {
      this.destroySubDeployment(param0);
   }

   public void setClassName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("ClassName", param0);
      String _oldVal = this._ClassName;
      this._ClassName = param0;
      this._postSet(19, _oldVal, param0);
   }

   public void setHostingServer(ServerMBean param0) {
      this._HostingServer = param0;
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void setSubDeployments(SubDeploymentMBean[] param0) throws InvalidAttributeValueException {
      SubDeploymentMBean[] param0 = param0 == null ? new SubDeploymentMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SubDeploymentMBean[] _oldVal = this._SubDeployments;
      this._SubDeployments = (SubDeploymentMBean[])param0;
      this._postSet(17, _oldVal, param0);
   }

   public SubDeploymentMBean createSubDeployment(String param0) {
      SubDeploymentMBeanImpl lookup = (SubDeploymentMBeanImpl)this.lookupSubDeployment(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         SubDeploymentMBeanImpl _val = new SubDeploymentMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addSubDeployment(_val);
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

   public TargetMBean[] getTargets() {
      if (!this._isSet(14)) {
         try {
            return DomainTargetHelper.getDefaultTargets(this, this.getValue("Targets"));
         } catch (NullPointerException var2) {
         }
      }

      return this._Targets;
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public ServerMBean getUserPreferredServer() {
      return this._customizer.getUserPreferredServer();
   }

   public String getUserPreferredServerAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getUserPreferredServer();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isTargetsInherited() {
      return false;
   }

   public boolean isTargetsSet() {
      return this._isSet(14);
   }

   public boolean isUserPreferredServerInherited() {
      return false;
   }

   public boolean isUserPreferredServerSet() {
      return this._isSet(11);
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
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 14, param0) {
                  public void resolveReference(Object value) {
                     try {
                        SingletonServiceAppScopedMBeanImpl.this.addTarget((TargetMBean)value);
                        SingletonServiceAppScopedMBeanImpl.this._getHelper().reorderArrayObjects((Object[])SingletonServiceAppScopedMBeanImpl.this._Targets, this.getHandbackObject());
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
         this._initializeProperty(14);
         this._postSet(14, _oldVal, this._Targets);
      }
   }

   public void setUserPreferredServerAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ServerMBean.class, new ReferenceManager.Resolver(this, 11) {
            public void resolveReference(Object value) {
               try {
                  SingletonServiceAppScopedMBeanImpl.this.setUserPreferredServer((ServerMBean)value);
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

   public SubDeploymentMBean lookupSubDeployment(String param0) {
      Object[] aary = (Object[])this._SubDeployments;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      SubDeploymentMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (SubDeploymentMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));
      DomainTargetHelper.validateTargets(this);

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 14, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return SingletonServiceAppScopedMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      TargetMBean[] _oldVal = this._Targets;
      this._Targets = param0;
      this._postSet(14, _oldVal, param0);
   }

   public void setUserPreferredServer(ServerMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 11, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return SingletonServiceAppScopedMBeanImpl.this.getUserPreferredServer();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      ServerMBean _oldVal = this.getUserPreferredServer();
      this._customizer.setUserPreferredServer(param0);
      this._postSet(11, _oldVal, param0);
   }

   public void addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         TargetMBean[] _new;
         if (this._isSet(14)) {
            _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));
         } else {
            _new = new TargetMBean[]{param0};
         }

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

   }

   public void destroySubDeployment(SubDeploymentMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 17);
         SubDeploymentMBean[] _old = this.getSubDeployments();
         SubDeploymentMBean[] _new = (SubDeploymentMBean[])((SubDeploymentMBean[])this._getHelper()._removeElement(_old, SubDeploymentMBean.class, param0));
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
               this.setSubDeployments(_new);
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

   public boolean isUntargeted() {
      return this._Untargeted;
   }

   public boolean isUntargetedInherited() {
      return false;
   }

   public boolean isUntargetedSet() {
      return this._isSet(18);
   }

   public void removeTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            }

            if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public String getModuleType() {
      return this._ModuleType;
   }

   public boolean isModuleTypeInherited() {
      return false;
   }

   public boolean isModuleTypeSet() {
      return this._isSet(15);
   }

   public void setUntargeted(boolean param0) {
      boolean _oldVal = this._Untargeted;
      this._Untargeted = param0;
      this._postSet(18, _oldVal, param0);
   }

   public void setModuleType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ModuleType;
      this._ModuleType = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getCompatibilityName() {
      return this._CompatibilityName;
   }

   public boolean isCompatibilityNameInherited() {
      return false;
   }

   public boolean isCompatibilityNameSet() {
      return this._isSet(16);
   }

   public void setCompatibilityName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CompatibilityName;
      this._CompatibilityName = param0;
      this._postSet(16, _oldVal, param0);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
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

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
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
         idx = 19;
      }

      try {
         switch (idx) {
            case 19:
               this._ClassName = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._CompatibilityName = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._HostingServer = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._ModuleType = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 17:
               this._SubDeployments = new SubDeploymentMBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 14:
               this._Targets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._customizer.setUserPreferredServer((ServerMBean)null);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 18:
               this._Untargeted = false;
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
      return "SingletonServiceAppScoped";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ClassName")) {
         oldVal = this._ClassName;
         this._ClassName = (String)v;
         this._postSet(19, oldVal, this._ClassName);
      } else if (name.equals("CompatibilityName")) {
         oldVal = this._CompatibilityName;
         this._CompatibilityName = (String)v;
         this._postSet(16, oldVal, this._CompatibilityName);
      } else {
         boolean oldVal;
         if (name.equals("DynamicallyCreated")) {
            oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else {
            ServerMBean oldVal;
            if (name.equals("HostingServer")) {
               oldVal = this._HostingServer;
               this._HostingServer = (ServerMBean)v;
               this._postSet(10, oldVal, this._HostingServer);
            } else if (name.equals("ModuleType")) {
               oldVal = this._ModuleType;
               this._ModuleType = (String)v;
               this._postSet(15, oldVal, this._ModuleType);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("SubDeployments")) {
               SubDeploymentMBean[] oldVal = this._SubDeployments;
               this._SubDeployments = (SubDeploymentMBean[])((SubDeploymentMBean[])v);
               this._postSet(17, oldVal, this._SubDeployments);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("Targets")) {
               TargetMBean[] oldVal = this._Targets;
               this._Targets = (TargetMBean[])((TargetMBean[])v);
               this._postSet(14, oldVal, this._Targets);
            } else if (name.equals("Untargeted")) {
               oldVal = this._Untargeted;
               this._Untargeted = (Boolean)v;
               this._postSet(18, oldVal, this._Untargeted);
            } else if (name.equals("UserPreferredServer")) {
               oldVal = this._UserPreferredServer;
               this._UserPreferredServer = (ServerMBean)v;
               this._postSet(11, oldVal, this._UserPreferredServer);
            } else if (name.equals("customizer")) {
               SingletonService oldVal = this._customizer;
               this._customizer = (SingletonService)v;
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ClassName")) {
         return this._ClassName;
      } else if (name.equals("CompatibilityName")) {
         return this._CompatibilityName;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("HostingServer")) {
         return this._HostingServer;
      } else if (name.equals("ModuleType")) {
         return this._ModuleType;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("SubDeployments")) {
         return this._SubDeployments;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("Untargeted")) {
         return new Boolean(this._Untargeted);
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
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 20:
            default:
               break;
            case 6:
               if (s.equals("target")) {
                  return 14;
               }
               break;
            case 10:
               if (s.equals("class-name")) {
                  return 19;
               }

               if (s.equals("untargeted")) {
                  return 18;
               }
               break;
            case 11:
               if (s.equals("module-type")) {
                  return 15;
               }
               break;
            case 14:
               if (s.equals("hosting-server")) {
                  return 10;
               }

               if (s.equals("sub-deployment")) {
                  return 17;
               }
               break;
            case 18:
               if (s.equals("compatibility-name")) {
                  return 16;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 21:
               if (s.equals("user-preferred-server")) {
                  return 11;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 17:
               return new SubDeploymentMBeanImpl.SchemaHelper2();
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
               return "target";
            case 15:
               return "module-type";
            case 16:
               return "compatibility-name";
            case 17:
               return "sub-deployment";
            case 18:
               return "untargeted";
            case 19:
               return "class-name";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 14:
               return true;
            case 17:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 17:
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

   protected static class Helper extends SingletonServiceBaseMBeanImpl.Helper {
      private SingletonServiceAppScopedMBeanImpl bean;

      protected Helper(SingletonServiceAppScopedMBeanImpl bean) {
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
               return "Targets";
            case 15:
               return "ModuleType";
            case 16:
               return "CompatibilityName";
            case 17:
               return "SubDeployments";
            case 18:
               return "Untargeted";
            case 19:
               return "ClassName";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClassName")) {
            return 19;
         } else if (propName.equals("CompatibilityName")) {
            return 16;
         } else if (propName.equals("HostingServer")) {
            return 10;
         } else if (propName.equals("ModuleType")) {
            return 15;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("SubDeployments")) {
            return 17;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 14;
         } else if (propName.equals("UserPreferredServer")) {
            return 11;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("Untargeted") ? 18 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getSubDeployments()));
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
            if (this.bean.isClassNameSet()) {
               buf.append("ClassName");
               buf.append(String.valueOf(this.bean.getClassName()));
            }

            if (this.bean.isCompatibilityNameSet()) {
               buf.append("CompatibilityName");
               buf.append(String.valueOf(this.bean.getCompatibilityName()));
            }

            if (this.bean.isHostingServerSet()) {
               buf.append("HostingServer");
               buf.append(String.valueOf(this.bean.getHostingServer()));
            }

            if (this.bean.isModuleTypeSet()) {
               buf.append("ModuleType");
               buf.append(String.valueOf(this.bean.getModuleType()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getSubDeployments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSubDeployments()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isUserPreferredServerSet()) {
               buf.append("UserPreferredServer");
               buf.append(String.valueOf(this.bean.getUserPreferredServer()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isUntargetedSet()) {
               buf.append("Untargeted");
               buf.append(String.valueOf(this.bean.isUntargeted()));
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
            SingletonServiceAppScopedMBeanImpl otherTyped = (SingletonServiceAppScopedMBeanImpl)other;
            this.computeDiff("ClassName", this.bean.getClassName(), otherTyped.getClassName(), false);
            this.computeDiff("CompatibilityName", this.bean.getCompatibilityName(), otherTyped.getCompatibilityName(), false);
            this.computeDiff("ModuleType", this.bean.getModuleType(), otherTyped.getModuleType(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeChildDiff("SubDeployments", this.bean.getSubDeployments(), otherTyped.getSubDeployments(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            this.computeDiff("UserPreferredServer", this.bean.getUserPreferredServer(), otherTyped.getUserPreferredServer(), true);
            this.computeDiff("Untargeted", this.bean.isUntargeted(), otherTyped.isUntargeted(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SingletonServiceAppScopedMBeanImpl original = (SingletonServiceAppScopedMBeanImpl)event.getSourceBean();
            SingletonServiceAppScopedMBeanImpl proposed = (SingletonServiceAppScopedMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClassName")) {
                  original.setClassName(proposed.getClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("CompatibilityName")) {
                  original.setCompatibilityName(proposed.getCompatibilityName());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (!prop.equals("HostingServer")) {
                  if (prop.equals("ModuleType")) {
                     original.setModuleType(proposed.getModuleType());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("SubDeployments")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addSubDeployment((SubDeploymentMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeSubDeployment((SubDeploymentMBean)update.getRemovedObject());
                     }

                     if (original.getSubDeployments() == null || original.getSubDeployments().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 17);
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
                  } else if (prop.equals("Targets")) {
                     original.setTargetsAsString(proposed.getTargetsAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("UserPreferredServer")) {
                     original.setUserPreferredServerAsString(proposed.getUserPreferredServerAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (!prop.equals("DynamicallyCreated")) {
                     if (prop.equals("Untargeted")) {
                        original.setUntargeted(proposed.isUntargeted());
                        original._conditionalUnset(update.isUnsetUpdate(), 18);
                     } else {
                        super.applyPropertyUpdate(event, update);
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
            SingletonServiceAppScopedMBeanImpl copy = (SingletonServiceAppScopedMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClassName")) && this.bean.isClassNameSet()) {
               copy.setClassName(this.bean.getClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("CompatibilityName")) && this.bean.isCompatibilityNameSet()) {
               copy.setCompatibilityName(this.bean.getCompatibilityName());
            }

            if ((excludeProps == null || !excludeProps.contains("ModuleType")) && this.bean.isModuleTypeSet()) {
               copy.setModuleType(this.bean.getModuleType());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("SubDeployments")) && this.bean.isSubDeploymentsSet() && !copy._isSet(17)) {
               SubDeploymentMBean[] oldSubDeployments = this.bean.getSubDeployments();
               SubDeploymentMBean[] newSubDeployments = new SubDeploymentMBean[oldSubDeployments.length];

               for(int i = 0; i < newSubDeployments.length; ++i) {
                  newSubDeployments[i] = (SubDeploymentMBean)((SubDeploymentMBean)this.createCopy((AbstractDescriptorBean)oldSubDeployments[i], includeObsolete));
               }

               copy.setSubDeployments(newSubDeployments);
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 14);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("UserPreferredServer")) && this.bean.isUserPreferredServerSet()) {
               copy._unSet(copy, 11);
               copy.setUserPreferredServerAsString(this.bean.getUserPreferredServerAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Untargeted")) && this.bean.isUntargetedSet()) {
               copy.setUntargeted(this.bean.isUntargeted());
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
         this.inferSubTree(this.bean.getHostingServer(), clazz, annotation);
         this.inferSubTree(this.bean.getSubDeployments(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getUserPreferredServer(), clazz, annotation);
      }
   }
}
