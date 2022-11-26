package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.SingletonService;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class SingletonServiceMBeanImpl extends SingletonServiceBaseMBeanImpl implements SingletonServiceMBean, Serializable {
   private ServerMBean[] _AllCandidateServers;
   private String _ClassName;
   private ClusterMBean _Cluster;
   private ServerMBean[] _ConstrainedCandidateServers;
   private boolean _DynamicallyCreated;
   private ServerMBean _HostingServer;
   private String _Name;
   private String[] _Tags;
   private ServerMBean _UserPreferredServer;
   private transient SingletonService _customizer;
   private static SchemaHelper2 _schemaHelper;

   public SingletonServiceMBeanImpl() {
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

   public SingletonServiceMBeanImpl(DescriptorBean param0, int param1) {
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

   public SingletonServiceMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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
      if (!this._isSet(14)) {
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
      return this._isSet(14);
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

   public void setClassName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("ClassName", param0);
      String _oldVal = this._ClassName;
      this._ClassName = param0;
      this._postSet(14, _oldVal, param0);
   }

   public void setHostingServer(ServerMBean param0) {
      this._HostingServer = param0;
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

   public ServerMBean[] getConstrainedCandidateServers() {
      return this._ConstrainedCandidateServers;
   }

   public String getConstrainedCandidateServersAsString() {
      return this._getHelper()._serializeKeyList(this.getConstrainedCandidateServers());
   }

   public ServerMBean getUserPreferredServer() {
      return this._customizer.getUserPreferredServer();
   }

   public String getUserPreferredServerAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getUserPreferredServer();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isConstrainedCandidateServersInherited() {
      return false;
   }

   public boolean isConstrainedCandidateServersSet() {
      return this._isSet(15);
   }

   public boolean isUserPreferredServerInherited() {
      return false;
   }

   public boolean isUserPreferredServerSet() {
      return this._isSet(11);
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
                        SingletonServiceMBeanImpl.this.addConstrainedCandidateServer((ServerMBean)value);
                        SingletonServiceMBeanImpl.this._getHelper().reorderArrayObjects((Object[])SingletonServiceMBeanImpl.this._ConstrainedCandidateServers, this.getHandbackObject());
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

   public void setUserPreferredServerAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ServerMBean.class, new ReferenceManager.Resolver(this, 11) {
            public void resolveReference(Object value) {
               try {
                  SingletonServiceMBeanImpl.this.setUserPreferredServer((ServerMBean)value);
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

   public void setConstrainedCandidateServers(ServerMBean[] param0) throws InvalidAttributeValueException {
      ServerMBean[] param0 = param0 == null ? new ServerMBeanImpl[0] : param0;
      param0 = (ServerMBean[])((ServerMBean[])this._getHelper()._cleanAndValidateArray(param0, ServerMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 15, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return SingletonServiceMBeanImpl.this.getConstrainedCandidateServers();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      ServerMBean[] _oldVal = this._ConstrainedCandidateServers;
      this._ConstrainedCandidateServers = param0;
      this._postSet(15, _oldVal, param0);
   }

   public void setUserPreferredServer(ServerMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 11, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return SingletonServiceMBeanImpl.this.getUserPreferredServer();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      ServerMBean _oldVal = this.getUserPreferredServer();
      this._customizer.setUserPreferredServer(param0);
      this._postSet(11, _oldVal, param0);
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

   public ClusterMBean getCluster() {
      return this._Cluster;
   }

   public String getClusterAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getCluster();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isClusterInherited() {
      return false;
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
                  SingletonServiceMBeanImpl.this.setCluster((ClusterMBean)value);
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
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 16, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return SingletonServiceMBeanImpl.this.getCluster();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      ClusterMBean _oldVal = this._Cluster;
      this._Cluster = param0;
      this._postSet(16, _oldVal, param0);
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
      return this._customizer.getAllCandidateServers();
   }

   public boolean isAllCandidateServersInherited() {
      return false;
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
      this._AllCandidateServers = (ServerMBean[])param0;
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
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._AllCandidateServers = new ServerMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._ClassName = null;
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
            case 10:
               this._HostingServer = null;
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
      return "SingletonService";
   }

   public void putValue(String name, Object v) {
      ServerMBean[] oldVal;
      if (name.equals("AllCandidateServers")) {
         oldVal = this._AllCandidateServers;
         this._AllCandidateServers = (ServerMBean[])((ServerMBean[])v);
         this._postSet(17, oldVal, this._AllCandidateServers);
      } else {
         String oldVal;
         if (name.equals("ClassName")) {
            oldVal = this._ClassName;
            this._ClassName = (String)v;
            this._postSet(14, oldVal, this._ClassName);
         } else if (name.equals("Cluster")) {
            ClusterMBean oldVal = this._Cluster;
            this._Cluster = (ClusterMBean)v;
            this._postSet(16, oldVal, this._Cluster);
         } else if (name.equals("ConstrainedCandidateServers")) {
            oldVal = this._ConstrainedCandidateServers;
            this._ConstrainedCandidateServers = (ServerMBean[])((ServerMBean[])v);
            this._postSet(15, oldVal, this._ConstrainedCandidateServers);
         } else if (name.equals("DynamicallyCreated")) {
            boolean oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else {
            ServerMBean oldVal;
            if (name.equals("HostingServer")) {
               oldVal = this._HostingServer;
               this._HostingServer = (ServerMBean)v;
               this._postSet(10, oldVal, this._HostingServer);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
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
      if (name.equals("AllCandidateServers")) {
         return this._AllCandidateServers;
      } else if (name.equals("ClassName")) {
         return this._ClassName;
      } else if (name.equals("Cluster")) {
         return this._Cluster;
      } else if (name.equals("ConstrainedCandidateServers")) {
         return this._ConstrainedCandidateServers;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("HostingServer")) {
         return this._HostingServer;
      } else if (name.equals("Name")) {
         return this._Name;
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
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            default:
               break;
            case 7:
               if (s.equals("cluster")) {
                  return 16;
               }
               break;
            case 10:
               if (s.equals("class-name")) {
                  return 14;
               }
               break;
            case 14:
               if (s.equals("hosting-server")) {
                  return 10;
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
               return "class-name";
            case 15:
               return "constrained-candidate-server";
            case 16:
               return "cluster";
            case 17:
               return "all-candidate-server";
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
      private SingletonServiceMBeanImpl bean;

      protected Helper(SingletonServiceMBeanImpl bean) {
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
               return "ClassName";
            case 15:
               return "ConstrainedCandidateServers";
            case 16:
               return "Cluster";
            case 17:
               return "AllCandidateServers";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AllCandidateServers")) {
            return 17;
         } else if (propName.equals("ClassName")) {
            return 14;
         } else if (propName.equals("Cluster")) {
            return 16;
         } else if (propName.equals("ConstrainedCandidateServers")) {
            return 15;
         } else if (propName.equals("HostingServer")) {
            return 10;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("UserPreferredServer")) {
            return 11;
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
            if (this.bean.isAllCandidateServersSet()) {
               buf.append("AllCandidateServers");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAllCandidateServers())));
            }

            if (this.bean.isClassNameSet()) {
               buf.append("ClassName");
               buf.append(String.valueOf(this.bean.getClassName()));
            }

            if (this.bean.isClusterSet()) {
               buf.append("Cluster");
               buf.append(String.valueOf(this.bean.getCluster()));
            }

            if (this.bean.isConstrainedCandidateServersSet()) {
               buf.append("ConstrainedCandidateServers");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getConstrainedCandidateServers())));
            }

            if (this.bean.isHostingServerSet()) {
               buf.append("HostingServer");
               buf.append(String.valueOf(this.bean.getHostingServer()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isUserPreferredServerSet()) {
               buf.append("UserPreferredServer");
               buf.append(String.valueOf(this.bean.getUserPreferredServer()));
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
            SingletonServiceMBeanImpl otherTyped = (SingletonServiceMBeanImpl)other;
            this.computeDiff("ClassName", this.bean.getClassName(), otherTyped.getClassName(), false);
            this.computeDiff("Cluster", this.bean.getCluster(), otherTyped.getCluster(), false);
            this.computeDiff("ConstrainedCandidateServers", this.bean.getConstrainedCandidateServers(), otherTyped.getConstrainedCandidateServers(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("UserPreferredServer", this.bean.getUserPreferredServer(), otherTyped.getUserPreferredServer(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SingletonServiceMBeanImpl original = (SingletonServiceMBeanImpl)event.getSourceBean();
            SingletonServiceMBeanImpl proposed = (SingletonServiceMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("AllCandidateServers")) {
                  if (prop.equals("ClassName")) {
                     original.setClassName(proposed.getClassName());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("Cluster")) {
                     original.setClusterAsString(proposed.getClusterAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("ConstrainedCandidateServers")) {
                     original.setConstrainedCandidateServersAsString(proposed.getConstrainedCandidateServersAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (!prop.equals("HostingServer")) {
                     if (prop.equals("Name")) {
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
                     } else if (prop.equals("UserPreferredServer")) {
                        original.setUserPreferredServerAsString(proposed.getUserPreferredServerAsString());
                        original._conditionalUnset(update.isUnsetUpdate(), 11);
                     } else if (!prop.equals("DynamicallyCreated")) {
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
            SingletonServiceMBeanImpl copy = (SingletonServiceMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClassName")) && this.bean.isClassNameSet()) {
               copy.setClassName(this.bean.getClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("Cluster")) && this.bean.isClusterSet()) {
               copy._unSet(copy, 16);
               copy.setClusterAsString(this.bean.getClusterAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ConstrainedCandidateServers")) && this.bean.isConstrainedCandidateServersSet()) {
               copy._unSet(copy, 15);
               copy.setConstrainedCandidateServersAsString(this.bean.getConstrainedCandidateServersAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UserPreferredServer")) && this.bean.isUserPreferredServerSet()) {
               copy._unSet(copy, 11);
               copy.setUserPreferredServerAsString(this.bean.getUserPreferredServerAsString());
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
         this.inferSubTree(this.bean.getHostingServer(), clazz, annotation);
         this.inferSubTree(this.bean.getUserPreferredServer(), clazz, annotation);
      }
   }
}
