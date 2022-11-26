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
import weblogic.management.mbeans.custom.CoherenceServer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceServerMBeanImpl extends ManagedExternalServerMBeanImpl implements CoherenceServerMBean, Serializable {
   private CoherenceClusterSystemResourceMBean _CoherenceClusterSystemResource;
   private CoherenceServerStartMBean _CoherenceServerStart;
   private boolean _DynamicallyCreated;
   private ManagedExternalServerStartMBean _ManagedExternalServerStart;
   private String _ManagedExternalType;
   private String _Name;
   private String[] _Tags;
   private String _UnicastListenAddress;
   private int _UnicastListenPort;
   private boolean _UnicastPortAutoAdjust;
   private int _UnicastPortAutoAdjustAttempts;
   private transient CoherenceServer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceServerMBeanImpl() {
      try {
         this._customizer = new CoherenceServer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public CoherenceServerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new CoherenceServer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public CoherenceServerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new CoherenceServer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      return this._customizer.getName();
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 18, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return CoherenceServerMBeanImpl.this.getCoherenceClusterSystemResource();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      CoherenceClusterSystemResourceMBean _oldVal = this._CoherenceClusterSystemResource;
      this._CoherenceClusterSystemResource = param0;
      this._postSet(18, _oldVal, param0);
   }

   public CoherenceClusterSystemResourceMBean getCoherenceClusterSystemResource() {
      return this._CoherenceClusterSystemResource;
   }

   public String getCoherenceClusterSystemResourceAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getCoherenceClusterSystemResource();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isCoherenceClusterSystemResourceInherited() {
      return false;
   }

   public boolean isCoherenceClusterSystemResourceSet() {
      return this._isSet(18);
   }

   public void setCoherenceClusterSystemResourceAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, CoherenceClusterSystemResourceMBean.class, new ReferenceManager.Resolver(this, 18) {
            public void resolveReference(Object value) {
               try {
                  CoherenceServerMBeanImpl.this.setCoherenceClusterSystemResource((CoherenceClusterSystemResourceMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         CoherenceClusterSystemResourceMBean _oldVal = this._CoherenceClusterSystemResource;
         this._initializeProperty(18);
         this._postSet(18, _oldVal, this._CoherenceClusterSystemResource);
      }

   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      ConfigurationValidator.validateNameUsedInDirectory(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public String getUnicastListenAddress() {
      return this._UnicastListenAddress;
   }

   public boolean isUnicastListenAddressInherited() {
      return false;
   }

   public boolean isUnicastListenAddressSet() {
      return this._isSet(19);
   }

   public void setUnicastListenAddress(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UnicastListenAddress;
      this._UnicastListenAddress = param0;
      this._postSet(19, _oldVal, param0);
   }

   public int getUnicastListenPort() {
      return this._UnicastListenPort;
   }

   public boolean isUnicastListenPortInherited() {
      return false;
   }

   public boolean isUnicastListenPortSet() {
      return this._isSet(20);
   }

   public void setUnicastListenPort(int param0) {
      LegalChecks.checkInRange("UnicastListenPort", (long)param0, 1L, 65535L);
      int _oldVal = this._UnicastListenPort;
      this._UnicastListenPort = param0;
      this._postSet(20, _oldVal, param0);
   }

   public boolean isUnicastPortAutoAdjust() {
      return this._UnicastPortAutoAdjust;
   }

   public boolean isUnicastPortAutoAdjustInherited() {
      return false;
   }

   public boolean isUnicastPortAutoAdjustSet() {
      return this._isSet(21);
   }

   public void setUnicastPortAutoAdjust(boolean param0) {
      boolean _oldVal = this._UnicastPortAutoAdjust;
      this._UnicastPortAutoAdjust = param0;
      this._postSet(21, _oldVal, param0);
   }

   public int getUnicastPortAutoAdjustAttempts() {
      return this._UnicastPortAutoAdjustAttempts;
   }

   public boolean isUnicastPortAutoAdjustAttemptsInherited() {
      return false;
   }

   public boolean isUnicastPortAutoAdjustAttemptsSet() {
      return this._isSet(22);
   }

   public void setUnicastPortAutoAdjustAttempts(int param0) {
      int _oldVal = this._UnicastPortAutoAdjustAttempts;
      this._UnicastPortAutoAdjustAttempts = param0;
      this._postSet(22, _oldVal, param0);
   }

   public CoherenceServerStartMBean getCoherenceServerStart() {
      return this._CoherenceServerStart;
   }

   public boolean isCoherenceServerStartInherited() {
      return false;
   }

   public boolean isCoherenceServerStartSet() {
      return this._isSet(23) || this._isAnythingSet((AbstractDescriptorBean)this.getCoherenceServerStart());
   }

   public void setCoherenceServerStart(CoherenceServerStartMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 23)) {
         this._postCreate(_child);
      }

      CoherenceServerStartMBean _oldVal = this._CoherenceServerStart;
      this._CoherenceServerStart = param0;
      this._postSet(23, _oldVal, param0);
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

   public ManagedExternalServerStartMBean getManagedExternalServerStart() {
      return this._customizer.getManagedExternalServerStart();
   }

   public boolean isManagedExternalServerStartInherited() {
      return false;
   }

   public boolean isManagedExternalServerStartSet() {
      return this._isSet(16);
   }

   public void setManagedExternalServerStart(ManagedExternalServerStartMBean param0) throws InvalidAttributeValueException {
      this._ManagedExternalServerStart = param0;
   }

   public String getManagedExternalType() {
      return this._customizer.getManagedExternalType();
   }

   public boolean isManagedExternalTypeInherited() {
      return false;
   }

   public boolean isManagedExternalTypeSet() {
      return this._isSet(17);
   }

   public void setManagedExternalType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this._ManagedExternalType = param0;
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
      return super._isAnythingSet() || this.isCoherenceServerStartSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 18;
      }

      try {
         switch (idx) {
            case 18:
               this._CoherenceClusterSystemResource = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._CoherenceServerStart = new CoherenceServerStartMBeanImpl(this, 23);
               this._postCreate((AbstractDescriptorBean)this._CoherenceServerStart);
               if (initOne) {
                  break;
               }
            case 16:
               this._ManagedExternalServerStart = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._ManagedExternalType = null;
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
            case 19:
               this._UnicastListenAddress = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._UnicastListenPort = 0;
               if (initOne) {
                  break;
               }
            case 22:
               this._UnicastPortAutoAdjustAttempts = 65535;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._UnicastPortAutoAdjust = true;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
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
      return "CoherenceServer";
   }

   public void putValue(String name, Object v) {
      if (name.equals("CoherenceClusterSystemResource")) {
         CoherenceClusterSystemResourceMBean oldVal = this._CoherenceClusterSystemResource;
         this._CoherenceClusterSystemResource = (CoherenceClusterSystemResourceMBean)v;
         this._postSet(18, oldVal, this._CoherenceClusterSystemResource);
      } else if (name.equals("CoherenceServerStart")) {
         CoherenceServerStartMBean oldVal = this._CoherenceServerStart;
         this._CoherenceServerStart = (CoherenceServerStartMBean)v;
         this._postSet(23, oldVal, this._CoherenceServerStart);
      } else {
         boolean oldVal;
         if (name.equals("DynamicallyCreated")) {
            oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else if (name.equals("ManagedExternalServerStart")) {
            ManagedExternalServerStartMBean oldVal = this._ManagedExternalServerStart;
            this._ManagedExternalServerStart = (ManagedExternalServerStartMBean)v;
            this._postSet(16, oldVal, this._ManagedExternalServerStart);
         } else {
            String oldVal;
            if (name.equals("ManagedExternalType")) {
               oldVal = this._ManagedExternalType;
               this._ManagedExternalType = (String)v;
               this._postSet(17, oldVal, this._ManagedExternalType);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("UnicastListenAddress")) {
               oldVal = this._UnicastListenAddress;
               this._UnicastListenAddress = (String)v;
               this._postSet(19, oldVal, this._UnicastListenAddress);
            } else {
               int oldVal;
               if (name.equals("UnicastListenPort")) {
                  oldVal = this._UnicastListenPort;
                  this._UnicastListenPort = (Integer)v;
                  this._postSet(20, oldVal, this._UnicastListenPort);
               } else if (name.equals("UnicastPortAutoAdjust")) {
                  oldVal = this._UnicastPortAutoAdjust;
                  this._UnicastPortAutoAdjust = (Boolean)v;
                  this._postSet(21, oldVal, this._UnicastPortAutoAdjust);
               } else if (name.equals("UnicastPortAutoAdjustAttempts")) {
                  oldVal = this._UnicastPortAutoAdjustAttempts;
                  this._UnicastPortAutoAdjustAttempts = (Integer)v;
                  this._postSet(22, oldVal, this._UnicastPortAutoAdjustAttempts);
               } else if (name.equals("customizer")) {
                  CoherenceServer oldVal = this._customizer;
                  this._customizer = (CoherenceServer)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("CoherenceClusterSystemResource")) {
         return this._CoherenceClusterSystemResource;
      } else if (name.equals("CoherenceServerStart")) {
         return this._CoherenceServerStart;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("ManagedExternalServerStart")) {
         return this._ManagedExternalServerStart;
      } else if (name.equals("ManagedExternalType")) {
         return this._ManagedExternalType;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("UnicastListenAddress")) {
         return this._UnicastListenAddress;
      } else if (name.equals("UnicastListenPort")) {
         return new Integer(this._UnicastListenPort);
      } else if (name.equals("UnicastPortAutoAdjust")) {
         return new Boolean(this._UnicastPortAutoAdjust);
      } else if (name.equals("UnicastPortAutoAdjustAttempts")) {
         return new Integer(this._UnicastPortAutoAdjustAttempts);
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ManagedExternalServerMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
               break;
            case 19:
               if (s.equals("unicast-listen-port")) {
                  return 20;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 21:
               if (s.equals("managed-external-type")) {
                  return 17;
               }
               break;
            case 22:
               if (s.equals("coherence-server-start")) {
                  return 23;
               }

               if (s.equals("unicast-listen-address")) {
                  return 19;
               }
               break;
            case 24:
               if (s.equals("unicast-port-auto-adjust")) {
                  return 21;
               }
               break;
            case 29:
               if (s.equals("managed-external-server-start")) {
                  return 16;
               }
               break;
            case 33:
               if (s.equals("coherence-cluster-system-resource")) {
                  return 18;
               }

               if (s.equals("unicast-port-auto-adjust-attempts")) {
                  return 22;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 23:
               return new CoherenceServerStartMBeanImpl.SchemaHelper2();
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
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 16:
               return "managed-external-server-start";
            case 17:
               return "managed-external-type";
            case 18:
               return "coherence-cluster-system-resource";
            case 19:
               return "unicast-listen-address";
            case 20:
               return "unicast-listen-port";
            case 21:
               return "unicast-port-auto-adjust";
            case 22:
               return "unicast-port-auto-adjust-attempts";
            case 23:
               return "coherence-server-start";
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 23:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
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

   protected static class Helper extends ManagedExternalServerMBeanImpl.Helper {
      private CoherenceServerMBeanImpl bean;

      protected Helper(CoherenceServerMBeanImpl bean) {
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
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 16:
               return "ManagedExternalServerStart";
            case 17:
               return "ManagedExternalType";
            case 18:
               return "CoherenceClusterSystemResource";
            case 19:
               return "UnicastListenAddress";
            case 20:
               return "UnicastListenPort";
            case 21:
               return "UnicastPortAutoAdjust";
            case 22:
               return "UnicastPortAutoAdjustAttempts";
            case 23:
               return "CoherenceServerStart";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CoherenceClusterSystemResource")) {
            return 18;
         } else if (propName.equals("CoherenceServerStart")) {
            return 23;
         } else if (propName.equals("ManagedExternalServerStart")) {
            return 16;
         } else if (propName.equals("ManagedExternalType")) {
            return 17;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("UnicastListenAddress")) {
            return 19;
         } else if (propName.equals("UnicastListenPort")) {
            return 20;
         } else if (propName.equals("UnicastPortAutoAdjustAttempts")) {
            return 22;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("UnicastPortAutoAdjust") ? 21 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCoherenceServerStart() != null) {
            iterators.add(new ArrayIterator(new CoherenceServerStartMBean[]{this.bean.getCoherenceServerStart()}));
         }

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
            if (this.bean.isCoherenceClusterSystemResourceSet()) {
               buf.append("CoherenceClusterSystemResource");
               buf.append(String.valueOf(this.bean.getCoherenceClusterSystemResource()));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherenceServerStart());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isManagedExternalServerStartSet()) {
               buf.append("ManagedExternalServerStart");
               buf.append(String.valueOf(this.bean.getManagedExternalServerStart()));
            }

            if (this.bean.isManagedExternalTypeSet()) {
               buf.append("ManagedExternalType");
               buf.append(String.valueOf(this.bean.getManagedExternalType()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
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

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
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
            CoherenceServerMBeanImpl otherTyped = (CoherenceServerMBeanImpl)other;
            this.computeDiff("CoherenceClusterSystemResource", this.bean.getCoherenceClusterSystemResource(), otherTyped.getCoherenceClusterSystemResource(), false);
            this.computeSubDiff("CoherenceServerStart", this.bean.getCoherenceServerStart(), otherTyped.getCoherenceServerStart());
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("UnicastListenAddress", this.bean.getUnicastListenAddress(), otherTyped.getUnicastListenAddress(), true);
            this.computeDiff("UnicastListenPort", this.bean.getUnicastListenPort(), otherTyped.getUnicastListenPort(), true);
            this.computeDiff("UnicastPortAutoAdjustAttempts", this.bean.getUnicastPortAutoAdjustAttempts(), otherTyped.getUnicastPortAutoAdjustAttempts(), true);
            this.computeDiff("UnicastPortAutoAdjust", this.bean.isUnicastPortAutoAdjust(), otherTyped.isUnicastPortAutoAdjust(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceServerMBeanImpl original = (CoherenceServerMBeanImpl)event.getSourceBean();
            CoherenceServerMBeanImpl proposed = (CoherenceServerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CoherenceClusterSystemResource")) {
                  original.setCoherenceClusterSystemResourceAsString(proposed.getCoherenceClusterSystemResourceAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("CoherenceServerStart")) {
                  if (type == 2) {
                     original.setCoherenceServerStart((CoherenceServerStartMBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceServerStart()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceServerStart", original.getCoherenceServerStart());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (!prop.equals("ManagedExternalServerStart") && !prop.equals("ManagedExternalType")) {
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
                  } else if (prop.equals("UnicastListenAddress")) {
                     original.setUnicastListenAddress(proposed.getUnicastListenAddress());
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  } else if (prop.equals("UnicastListenPort")) {
                     original.setUnicastListenPort(proposed.getUnicastListenPort());
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (prop.equals("UnicastPortAutoAdjustAttempts")) {
                     original.setUnicastPortAutoAdjustAttempts(proposed.getUnicastPortAutoAdjustAttempts());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (!prop.equals("DynamicallyCreated")) {
                     if (prop.equals("UnicastPortAutoAdjust")) {
                        original.setUnicastPortAutoAdjust(proposed.isUnicastPortAutoAdjust());
                        original._conditionalUnset(update.isUnsetUpdate(), 21);
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
            CoherenceServerMBeanImpl copy = (CoherenceServerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CoherenceClusterSystemResource")) && this.bean.isCoherenceClusterSystemResourceSet()) {
               copy._unSet(copy, 18);
               copy.setCoherenceClusterSystemResourceAsString(this.bean.getCoherenceClusterSystemResourceAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceServerStart")) && this.bean.isCoherenceServerStartSet() && !copy._isSet(23)) {
               Object o = this.bean.getCoherenceServerStart();
               copy.setCoherenceServerStart((CoherenceServerStartMBean)null);
               copy.setCoherenceServerStart(o == null ? null : (CoherenceServerStartMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
         this.inferSubTree(this.bean.getCoherenceClusterSystemResource(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceServerStart(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedExternalServerStart(), clazz, annotation);
      }
   }
}
