package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import weblogic.management.mbeans.custom.VirtualTarget;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class VirtualTargetMBeanImpl extends DeploymentMBeanImpl implements VirtualTargetMBean, Serializable {
   private boolean _DynamicallyCreated;
   private int _ExplicitPort;
   private String[] _HostNames;
   private boolean _MoreThanOneTargetAllowed;
   private String _Name;
   private String _PartitionChannel;
   private int _PortOffset;
   private Set _ServerNames;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private String _UriPrefix;
   private WebServerMBean _WebServer;
   private transient VirtualTarget _customizer;
   private static SchemaHelper2 _schemaHelper;

   public VirtualTargetMBeanImpl() {
      try {
         this._customizer = new VirtualTarget(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public VirtualTargetMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new VirtualTarget(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public VirtualTargetMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new VirtualTarget(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
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

   public Set getServerNames() {
      return this._customizer.getServerNames();
   }

   public TargetMBean[] getTargets() {
      return this._Targets;
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isServerNamesInherited() {
      return false;
   }

   public boolean isServerNamesSet() {
      return this._isSet(12);
   }

   public boolean isTargetsInherited() {
      return false;
   }

   public boolean isTargetsSet() {
      return this._isSet(10);
   }

   public void setServerNames(Set param0) throws InvalidAttributeValueException {
      this._ServerNames = param0;
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
                        VirtualTargetMBeanImpl.this.addTarget((TargetMBean)value);
                        VirtualTargetMBeanImpl.this._getHelper().reorderArrayObjects((Object[])VirtualTargetMBeanImpl.this._Targets, this.getHandbackObject());
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

   public String[] getHostNames() {
      return this._HostNames;
   }

   public boolean isHostNamesInherited() {
      return false;
   }

   public boolean isHostNamesSet() {
      return this._isSet(13);
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

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));
      VirtualTargetValidator.validateSetTargets(this, param0);

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return VirtualTargetMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      TargetMBean[] _oldVal = this._Targets;
      this._Targets = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         TargetMBean[] _new;
         if (this._isSet(10)) {
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

      return true;
   }

   public void setHostNames(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._HostNames;
      this._HostNames = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getUriPrefix() {
      return this._UriPrefix;
   }

   public boolean isUriPrefixInherited() {
      return false;
   }

   public boolean isUriPrefixSet() {
      return this._isSet(14);
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

   public void setUriPrefix(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      VirtualTargetValidator.validateUriPrefix(this, param0);
      String _oldVal = this._UriPrefix;
      this._UriPrefix = param0;
      this._postSet(14, _oldVal, param0);
   }

   public WebServerMBean getWebServer() {
      return this._WebServer;
   }

   public boolean isWebServerInherited() {
      return false;
   }

   public boolean isWebServerSet() {
      return this._isSet(15) || this._isAnythingSet((AbstractDescriptorBean)this.getWebServer());
   }

   public void setWebServer(WebServerMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 15)) {
         this._postCreate(_child);
      }

      WebServerMBean _oldVal = this._WebServer;
      this._WebServer = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getPortOffset() {
      return this._PortOffset;
   }

   public boolean isPortOffsetInherited() {
      return false;
   }

   public boolean isPortOffsetSet() {
      return this._isSet(16);
   }

   public void setPortOffset(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("PortOffset", (long)param0, 0L, 65535L);
      int _oldVal = this._PortOffset;
      this._PortOffset = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getExplicitPort() {
      return this._ExplicitPort;
   }

   public boolean isExplicitPortInherited() {
      return false;
   }

   public boolean isExplicitPortSet() {
      return this._isSet(17);
   }

   public void setExplicitPort(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("ExplicitPort", (long)param0, 0L, 65535L);
      int _oldVal = this._ExplicitPort;
      this._ExplicitPort = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getPartitionChannel() {
      return this._PartitionChannel;
   }

   public boolean isPartitionChannelInherited() {
      return false;
   }

   public boolean isPartitionChannelSet() {
      return this._isSet(18);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setPartitionChannel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PartitionChannel;
      this._PartitionChannel = param0;
      this._postSet(18, _oldVal, param0);
   }

   public boolean isMoreThanOneTargetAllowed() {
      return this._MoreThanOneTargetAllowed;
   }

   public boolean isMoreThanOneTargetAllowedInherited() {
      return false;
   }

   public boolean isMoreThanOneTargetAllowedSet() {
      return this._isSet(19);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setMoreThanOneTargetAllowed(boolean param0) {
      boolean _oldVal = this._MoreThanOneTargetAllowed;
      this._MoreThanOneTargetAllowed = param0;
      this._postSet(19, _oldVal, param0);
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
      VirtualTargetValidator.validateVirtualTarget(this);
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
      return super._isAnythingSet() || this.isWebServerSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._ExplicitPort = 0;
               if (initOne) {
                  break;
               }
            case 13:
               this._HostNames = new String[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 18:
               this._PartitionChannel = "PartitionChannel";
               if (initOne) {
                  break;
               }
            case 16:
               this._PortOffset = 0;
               if (initOne) {
                  break;
               }
            case 12:
               this._ServerNames = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 10:
               this._Targets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._UriPrefix = "/";
               if (initOne) {
                  break;
               }
            case 15:
               this._WebServer = new WebServerMBeanImpl(this, 15);
               this._postCreate((AbstractDescriptorBean)this._WebServer);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 19:
               this._MoreThanOneTargetAllowed = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
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
      return "VirtualTarget";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("DynamicallyCreated")) {
         oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else {
         int oldVal;
         if (name.equals("ExplicitPort")) {
            oldVal = this._ExplicitPort;
            this._ExplicitPort = (Integer)v;
            this._postSet(17, oldVal, this._ExplicitPort);
         } else {
            String[] oldVal;
            if (name.equals("HostNames")) {
               oldVal = this._HostNames;
               this._HostNames = (String[])((String[])v);
               this._postSet(13, oldVal, this._HostNames);
            } else if (name.equals("MoreThanOneTargetAllowed")) {
               oldVal = this._MoreThanOneTargetAllowed;
               this._MoreThanOneTargetAllowed = (Boolean)v;
               this._postSet(19, oldVal, this._MoreThanOneTargetAllowed);
            } else {
               String oldVal;
               if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("PartitionChannel")) {
                  oldVal = this._PartitionChannel;
                  this._PartitionChannel = (String)v;
                  this._postSet(18, oldVal, this._PartitionChannel);
               } else if (name.equals("PortOffset")) {
                  oldVal = this._PortOffset;
                  this._PortOffset = (Integer)v;
                  this._postSet(16, oldVal, this._PortOffset);
               } else if (name.equals("ServerNames")) {
                  Set oldVal = this._ServerNames;
                  this._ServerNames = (Set)v;
                  this._postSet(12, oldVal, this._ServerNames);
               } else if (name.equals("Tags")) {
                  oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("Targets")) {
                  TargetMBean[] oldVal = this._Targets;
                  this._Targets = (TargetMBean[])((TargetMBean[])v);
                  this._postSet(10, oldVal, this._Targets);
               } else if (name.equals("UriPrefix")) {
                  oldVal = this._UriPrefix;
                  this._UriPrefix = (String)v;
                  this._postSet(14, oldVal, this._UriPrefix);
               } else if (name.equals("WebServer")) {
                  WebServerMBean oldVal = this._WebServer;
                  this._WebServer = (WebServerMBean)v;
                  this._postSet(15, oldVal, this._WebServer);
               } else if (name.equals("customizer")) {
                  VirtualTarget oldVal = this._customizer;
                  this._customizer = (VirtualTarget)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("ExplicitPort")) {
         return new Integer(this._ExplicitPort);
      } else if (name.equals("HostNames")) {
         return this._HostNames;
      } else if (name.equals("MoreThanOneTargetAllowed")) {
         return new Boolean(this._MoreThanOneTargetAllowed);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PartitionChannel")) {
         return this._PartitionChannel;
      } else if (name.equals("PortOffset")) {
         return new Integer(this._PortOffset);
      } else if (name.equals("ServerNames")) {
         return this._ServerNames;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("UriPrefix")) {
         return this._UriPrefix;
      } else if (name.equals("WebServer")) {
         return this._WebServer;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 14:
            case 15:
            case 16:
            case 18:
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
            case 9:
               if (s.equals("host-name")) {
                  return 13;
               }
               break;
            case 10:
               if (s.equals("uri-prefix")) {
                  return 14;
               }

               if (s.equals("web-server")) {
                  return 15;
               }
               break;
            case 11:
               if (s.equals("port-offset")) {
                  return 16;
               }
               break;
            case 12:
               if (s.equals("server-names")) {
                  return 12;
               }
               break;
            case 13:
               if (s.equals("explicit-port")) {
                  return 17;
               }
               break;
            case 17:
               if (s.equals("partition-channel")) {
                  return 18;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 28:
               if (s.equals("more-than-one-target-allowed")) {
                  return 19;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 15:
               return new WebServerMBeanImpl.SchemaHelper2();
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
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "target";
            case 12:
               return "server-names";
            case 13:
               return "host-name";
            case 14:
               return "uri-prefix";
            case 15:
               return "web-server";
            case 16:
               return "port-offset";
            case 17:
               return "explicit-port";
            case 18:
               return "partition-channel";
            case 19:
               return "more-than-one-target-allowed";
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
            default:
               return super.isArray(propIndex);
            case 13:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 15:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 13:
               return true;
            case 14:
               return true;
            case 15:
            default:
               return super.isConfigurable(propIndex);
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
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

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private VirtualTargetMBeanImpl bean;

      protected Helper(VirtualTargetMBeanImpl bean) {
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
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "Targets";
            case 12:
               return "ServerNames";
            case 13:
               return "HostNames";
            case 14:
               return "UriPrefix";
            case 15:
               return "WebServer";
            case 16:
               return "PortOffset";
            case 17:
               return "ExplicitPort";
            case 18:
               return "PartitionChannel";
            case 19:
               return "MoreThanOneTargetAllowed";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ExplicitPort")) {
            return 17;
         } else if (propName.equals("HostNames")) {
            return 13;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PartitionChannel")) {
            return 18;
         } else if (propName.equals("PortOffset")) {
            return 16;
         } else if (propName.equals("ServerNames")) {
            return 12;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else if (propName.equals("UriPrefix")) {
            return 14;
         } else if (propName.equals("WebServer")) {
            return 15;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("MoreThanOneTargetAllowed") ? 19 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getWebServer() != null) {
            iterators.add(new ArrayIterator(new WebServerMBean[]{this.bean.getWebServer()}));
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
            if (this.bean.isExplicitPortSet()) {
               buf.append("ExplicitPort");
               buf.append(String.valueOf(this.bean.getExplicitPort()));
            }

            if (this.bean.isHostNamesSet()) {
               buf.append("HostNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getHostNames())));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPartitionChannelSet()) {
               buf.append("PartitionChannel");
               buf.append(String.valueOf(this.bean.getPartitionChannel()));
            }

            if (this.bean.isPortOffsetSet()) {
               buf.append("PortOffset");
               buf.append(String.valueOf(this.bean.getPortOffset()));
            }

            if (this.bean.isServerNamesSet()) {
               buf.append("ServerNames");
               buf.append(String.valueOf(this.bean.getServerNames()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isUriPrefixSet()) {
               buf.append("UriPrefix");
               buf.append(String.valueOf(this.bean.getUriPrefix()));
            }

            childValue = this.computeChildHashValue(this.bean.getWebServer());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isMoreThanOneTargetAllowedSet()) {
               buf.append("MoreThanOneTargetAllowed");
               buf.append(String.valueOf(this.bean.isMoreThanOneTargetAllowed()));
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
            VirtualTargetMBeanImpl otherTyped = (VirtualTargetMBeanImpl)other;
            this.addRestartElements("ExplicitPort", VirtualTargetValidator.getRestarts(this.bean));
            this.computeDiff("ExplicitPort", this.bean.getExplicitPort(), otherTyped.getExplicitPort(), true);
            this.addRestartElements("HostNames", VirtualTargetValidator.getRestarts(this.bean));
            this.computeDiff("HostNames", this.bean.getHostNames(), otherTyped.getHostNames(), true);
            this.addRestartElements("Name", VirtualTargetValidator.getRestarts(this.bean));
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.addRestartElements("PartitionChannel", VirtualTargetValidator.getRestarts(this.bean));
            this.computeDiff("PartitionChannel", this.bean.getPartitionChannel(), otherTyped.getPartitionChannel(), true);
            this.addRestartElements("PortOffset", VirtualTargetValidator.getRestarts(this.bean));
            this.computeDiff("PortOffset", this.bean.getPortOffset(), otherTyped.getPortOffset(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), false);
            this.addRestartElements("UriPrefix", VirtualTargetValidator.getRestarts(this.bean));
            this.computeDiff("UriPrefix", this.bean.getUriPrefix(), otherTyped.getUriPrefix(), true);
            this.addRestartElements("WebServer", VirtualTargetValidator.getRestarts(this.bean));
            this.computeSubDiff("WebServer", this.bean.getWebServer(), otherTyped.getWebServer());
            this.addRestartElements("MoreThanOneTargetAllowed", VirtualTargetValidator.getRestarts(this.bean));
            this.computeDiff("MoreThanOneTargetAllowed", this.bean.isMoreThanOneTargetAllowed(), otherTyped.isMoreThanOneTargetAllowed(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            VirtualTargetMBeanImpl original = (VirtualTargetMBeanImpl)event.getSourceBean();
            VirtualTargetMBeanImpl proposed = (VirtualTargetMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ExplicitPort")) {
                  original.setExplicitPort(proposed.getExplicitPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("HostNames")) {
                  original.setHostNames(proposed.getHostNames());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("PartitionChannel")) {
                  original.setPartitionChannel(proposed.getPartitionChannel());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("PortOffset")) {
                  original.setPortOffset(proposed.getPortOffset());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
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
                  } else if (prop.equals("Targets")) {
                     original.setTargetsAsString(proposed.getTargetsAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("UriPrefix")) {
                     original.setUriPrefix(proposed.getUriPrefix());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("WebServer")) {
                     if (type == 2) {
                        original.setWebServer((WebServerMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebServer()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("WebServer", original.getWebServer());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (!prop.equals("DynamicallyCreated")) {
                     if (prop.equals("MoreThanOneTargetAllowed")) {
                        original.setMoreThanOneTargetAllowed(proposed.isMoreThanOneTargetAllowed());
                        original._conditionalUnset(update.isUnsetUpdate(), 19);
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
            VirtualTargetMBeanImpl copy = (VirtualTargetMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ExplicitPort")) && this.bean.isExplicitPortSet()) {
               copy.setExplicitPort(this.bean.getExplicitPort());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("HostNames")) && this.bean.isHostNamesSet()) {
               o = this.bean.getHostNames();
               copy.setHostNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionChannel")) && this.bean.isPartitionChannelSet()) {
               copy.setPartitionChannel(this.bean.getPartitionChannel());
            }

            if ((excludeProps == null || !excludeProps.contains("PortOffset")) && this.bean.isPortOffsetSet()) {
               copy.setPortOffset(this.bean.getPortOffset());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("UriPrefix")) && this.bean.isUriPrefixSet()) {
               copy.setUriPrefix(this.bean.getUriPrefix());
            }

            if ((excludeProps == null || !excludeProps.contains("WebServer")) && this.bean.isWebServerSet() && !copy._isSet(15)) {
               Object o = this.bean.getWebServer();
               copy.setWebServer((WebServerMBean)null);
               copy.setWebServer(o == null ? null : (WebServerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MoreThanOneTargetAllowed")) && this.bean.isMoreThanOneTargetAllowedSet()) {
               copy.setMoreThanOneTargetAllowed(this.bean.isMoreThanOneTargetAllowed());
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
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getWebServer(), clazz, annotation);
      }
   }
}
