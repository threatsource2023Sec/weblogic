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
import weblogic.management.mbeans.custom.DynamicServers;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class DynamicServersMBeanImpl extends ConfigurationMBeanImpl implements DynamicServersMBean, Serializable {
   private boolean _CalculatedListenPorts;
   private boolean _CalculatedMachineNames;
   private int _DynamicClusterCooloffPeriodSeconds;
   private int _DynamicClusterShutdownTimeoutSeconds;
   private int _DynamicClusterSize;
   private String[] _DynamicServerNames;
   private boolean _DynamicallyCreated;
   private boolean _IgnoreSessionsDuringShutdown;
   private String _MachineMatchExpression;
   private String _MachineMatchType;
   private String _MachineNameMatchExpression;
   private int _MaxDynamicClusterSize;
   private int _MaximumDynamicServerCount;
   private int _MinDynamicClusterSize;
   private String _Name;
   private String _ServerNamePrefix;
   private int _ServerNameStartingIndex;
   private ServerTemplateMBean _ServerTemplate;
   private String[] _Tags;
   private boolean _WaitForAllSessionsDuringShutdown;
   private transient DynamicServers _customizer;
   private static SchemaHelper2 _schemaHelper;

   public DynamicServersMBeanImpl() {
      try {
         this._customizer = new DynamicServers(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public DynamicServersMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new DynamicServers(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public DynamicServersMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new DynamicServers(this);
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

   public ServerTemplateMBean getServerTemplate() {
      return this._ServerTemplate;
   }

   public String getServerTemplateAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getServerTemplate();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isServerTemplateInherited() {
      return false;
   }

   public boolean isServerTemplateSet() {
      return this._isSet(10);
   }

   public void setServerTemplateAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ServerTemplateMBean.class, new ReferenceManager.Resolver(this, 10) {
            public void resolveReference(Object value) {
               try {
                  DynamicServersMBeanImpl.this.setServerTemplate((ServerTemplateMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         ServerTemplateMBean _oldVal = this._ServerTemplate;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._ServerTemplate);
      }

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

   public void setServerTemplate(ServerTemplateMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return DynamicServersMBeanImpl.this.getServerTemplate();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      ServerTemplateMBean _oldVal = this._ServerTemplate;
      this._ServerTemplate = param0;
      this._postSet(10, _oldVal, param0);
   }

   public void setMaximumDynamicServerCount(int param0) {
      LegalChecks.checkInRange("MaximumDynamicServerCount", (long)param0, 0L, 800L);
      int _oldVal = this._MaximumDynamicServerCount;
      this._MaximumDynamicServerCount = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getMaximumDynamicServerCount() {
      return this._MaximumDynamicServerCount;
   }

   public boolean isMaximumDynamicServerCountInherited() {
      return false;
   }

   public boolean isMaximumDynamicServerCountSet() {
      return this._isSet(11);
   }

   public void setCalculatedListenPorts(boolean param0) {
      boolean _oldVal = this._CalculatedListenPorts;
      this._CalculatedListenPorts = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean isCalculatedListenPorts() {
      return this._CalculatedListenPorts;
   }

   public boolean isCalculatedListenPortsInherited() {
      return false;
   }

   public boolean isCalculatedListenPortsSet() {
      return this._isSet(12);
   }

   public void setCalculatedMachineNames(boolean param0) {
      boolean _oldVal = this._CalculatedMachineNames;
      this._CalculatedMachineNames = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isCalculatedMachineNames() {
      return this._CalculatedMachineNames;
   }

   public boolean isCalculatedMachineNamesInherited() {
      return false;
   }

   public boolean isCalculatedMachineNamesSet() {
      return this._isSet(13);
   }

   public void setMachineNameMatchExpression(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MachineNameMatchExpression;
      this._MachineNameMatchExpression = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getMachineNameMatchExpression() {
      return this._MachineNameMatchExpression;
   }

   public boolean isMachineNameMatchExpressionInherited() {
      return false;
   }

   public boolean isMachineNameMatchExpressionSet() {
      return this._isSet(14);
   }

   public void setMachineMatchExpression(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MachineMatchExpression;
      this._MachineMatchExpression = param0;
      this._postSet(15, _oldVal, param0);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public String getMachineMatchExpression() {
      return this._MachineMatchExpression;
   }

   public boolean isMachineMatchExpressionInherited() {
      return false;
   }

   public boolean isMachineMatchExpressionSet() {
      return this._isSet(15);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setMachineMatchType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"name", "tag"};
      param0 = LegalChecks.checkInEnum("MachineMatchType", param0, _set);
      String _oldVal = this._MachineMatchType;
      this._MachineMatchType = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getMachineMatchType() {
      return this._MachineMatchType;
   }

   public boolean isMachineMatchTypeInherited() {
      return false;
   }

   public boolean isMachineMatchTypeSet() {
      return this._isSet(16);
   }

   public void setServerNamePrefix(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getServerNamePrefix();
      this._customizer.setServerNamePrefix(param0);
      this._postSet(17, _oldVal, param0);
   }

   public String getServerNamePrefix() {
      if (!this._isSet(17)) {
         try {
            return this.getName() + "-";
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getServerNamePrefix();
   }

   public boolean isServerNamePrefixInherited() {
      return false;
   }

   public boolean isServerNamePrefixSet() {
      return this._isSet(17);
   }

   public int getDynamicClusterSize() {
      return this._customizer.getDynamicClusterSize();
   }

   public boolean isDynamicClusterSizeInherited() {
      return false;
   }

   public boolean isDynamicClusterSizeSet() {
      return this._isSet(18);
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

   public void setDynamicClusterSize(int param0) {
      LegalChecks.checkInRange("DynamicClusterSize", (long)param0, 0L, 800L);
      int _oldVal = this.getDynamicClusterSize();
      this._customizer.setDynamicClusterSize(param0);
      this._postSet(18, _oldVal, param0);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public int getMinDynamicClusterSize() {
      return this._MinDynamicClusterSize;
   }

   public boolean isMinDynamicClusterSizeInherited() {
      return false;
   }

   public boolean isMinDynamicClusterSizeSet() {
      return this._isSet(19);
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

   public void setMinDynamicClusterSize(int param0) {
      LegalChecks.checkInRange("MinDynamicClusterSize", (long)param0, 0L, 800L);
      int _oldVal = this._MinDynamicClusterSize;
      this._MinDynamicClusterSize = param0;
      this._postSet(19, _oldVal, param0);
   }

   public int getMaxDynamicClusterSize() {
      return this._MaxDynamicClusterSize;
   }

   public boolean isMaxDynamicClusterSizeInherited() {
      return false;
   }

   public boolean isMaxDynamicClusterSizeSet() {
      return this._isSet(20);
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

   public void setMaxDynamicClusterSize(int param0) {
      LegalChecks.checkInRange("MaxDynamicClusterSize", (long)param0, 0L, 800L);
      int _oldVal = this._MaxDynamicClusterSize;
      this._MaxDynamicClusterSize = param0;
      this._postSet(20, _oldVal, param0);
   }

   public int getDynamicClusterCooloffPeriodSeconds() {
      return this._DynamicClusterCooloffPeriodSeconds;
   }

   public boolean isDynamicClusterCooloffPeriodSecondsInherited() {
      return false;
   }

   public boolean isDynamicClusterCooloffPeriodSecondsSet() {
      return this._isSet(21);
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

   public void setDynamicClusterCooloffPeriodSeconds(int param0) {
      LegalChecks.checkMin("DynamicClusterCooloffPeriodSeconds", param0, 0);
      int _oldVal = this._DynamicClusterCooloffPeriodSeconds;
      this._DynamicClusterCooloffPeriodSeconds = param0;
      this._postSet(21, _oldVal, param0);
   }

   public int getDynamicClusterShutdownTimeoutSeconds() {
      return this._DynamicClusterShutdownTimeoutSeconds;
   }

   public boolean isDynamicClusterShutdownTimeoutSecondsInherited() {
      return false;
   }

   public boolean isDynamicClusterShutdownTimeoutSecondsSet() {
      return this._isSet(22);
   }

   public void setDynamicClusterShutdownTimeoutSeconds(int param0) {
      LegalChecks.checkMin("DynamicClusterShutdownTimeoutSeconds", param0, 0);
      int _oldVal = this._DynamicClusterShutdownTimeoutSeconds;
      this._DynamicClusterShutdownTimeoutSeconds = param0;
      this._postSet(22, _oldVal, param0);
   }

   public boolean isIgnoreSessionsDuringShutdown() {
      return this._IgnoreSessionsDuringShutdown;
   }

   public boolean isIgnoreSessionsDuringShutdownInherited() {
      return false;
   }

   public boolean isIgnoreSessionsDuringShutdownSet() {
      return this._isSet(23);
   }

   public void setIgnoreSessionsDuringShutdown(boolean param0) {
      boolean _oldVal = this._IgnoreSessionsDuringShutdown;
      this._IgnoreSessionsDuringShutdown = param0;
      this._postSet(23, _oldVal, param0);
   }

   public boolean isWaitForAllSessionsDuringShutdown() {
      return this._WaitForAllSessionsDuringShutdown;
   }

   public boolean isWaitForAllSessionsDuringShutdownInherited() {
      return false;
   }

   public boolean isWaitForAllSessionsDuringShutdownSet() {
      return this._isSet(24);
   }

   public void setWaitForAllSessionsDuringShutdown(boolean param0) {
      boolean _oldVal = this._WaitForAllSessionsDuringShutdown;
      this._WaitForAllSessionsDuringShutdown = param0;
      this._postSet(24, _oldVal, param0);
   }

   public String[] getDynamicServerNames() {
      return this._customizer.getDynamicServerNames();
   }

   public boolean isDynamicServerNamesInherited() {
      return false;
   }

   public boolean isDynamicServerNamesSet() {
      return this._isSet(25);
   }

   public void setDynamicServerNames(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._DynamicServerNames;
      this._DynamicServerNames = param0;
      this._postSet(25, _oldVal, param0);
   }

   public int getServerNameStartingIndex() {
      return this._ServerNameStartingIndex;
   }

   public boolean isServerNameStartingIndexInherited() {
      return false;
   }

   public boolean isServerNameStartingIndexSet() {
      return this._isSet(26);
   }

   public void setServerNameStartingIndex(int param0) {
      LegalChecks.checkInRange("ServerNameStartingIndex", (long)param0, 0L, 2147480000L);
      int _oldVal = this._ServerNameStartingIndex;
      this._ServerNameStartingIndex = param0;
      this._postSet(26, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      DynamicServersValidator.validateDynamicServers(this);
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
         idx = 21;
      }

      try {
         switch (idx) {
            case 21:
               this._DynamicClusterCooloffPeriodSeconds = 900;
               if (initOne) {
                  break;
               }
            case 22:
               this._DynamicClusterShutdownTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 18:
               this._customizer.setDynamicClusterSize(0);
               if (initOne) {
                  break;
               }
            case 25:
               this._DynamicServerNames = new String[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._MachineMatchExpression = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._MachineMatchType = "name";
               if (initOne) {
                  break;
               }
            case 14:
               this._MachineNameMatchExpression = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._MaxDynamicClusterSize = 8;
               if (initOne) {
                  break;
               }
            case 11:
               this._MaximumDynamicServerCount = 0;
               if (initOne) {
                  break;
               }
            case 19:
               this._MinDynamicClusterSize = 1;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 17:
               this._customizer.setServerNamePrefix((String)null);
               if (initOne) {
                  break;
               }
            case 26:
               this._ServerNameStartingIndex = 1;
               if (initOne) {
                  break;
               }
            case 10:
               this._ServerTemplate = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 12:
               this._CalculatedListenPorts = true;
               if (initOne) {
                  break;
               }
            case 13:
               this._CalculatedMachineNames = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 23:
               this._IgnoreSessionsDuringShutdown = false;
               if (initOne) {
                  break;
               }
            case 24:
               this._WaitForAllSessionsDuringShutdown = false;
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
      return "DynamicServers";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("CalculatedListenPorts")) {
         oldVal = this._CalculatedListenPorts;
         this._CalculatedListenPorts = (Boolean)v;
         this._postSet(12, oldVal, this._CalculatedListenPorts);
      } else if (name.equals("CalculatedMachineNames")) {
         oldVal = this._CalculatedMachineNames;
         this._CalculatedMachineNames = (Boolean)v;
         this._postSet(13, oldVal, this._CalculatedMachineNames);
      } else {
         int oldVal;
         if (name.equals("DynamicClusterCooloffPeriodSeconds")) {
            oldVal = this._DynamicClusterCooloffPeriodSeconds;
            this._DynamicClusterCooloffPeriodSeconds = (Integer)v;
            this._postSet(21, oldVal, this._DynamicClusterCooloffPeriodSeconds);
         } else if (name.equals("DynamicClusterShutdownTimeoutSeconds")) {
            oldVal = this._DynamicClusterShutdownTimeoutSeconds;
            this._DynamicClusterShutdownTimeoutSeconds = (Integer)v;
            this._postSet(22, oldVal, this._DynamicClusterShutdownTimeoutSeconds);
         } else if (name.equals("DynamicClusterSize")) {
            oldVal = this._DynamicClusterSize;
            this._DynamicClusterSize = (Integer)v;
            this._postSet(18, oldVal, this._DynamicClusterSize);
         } else {
            String[] oldVal;
            if (name.equals("DynamicServerNames")) {
               oldVal = this._DynamicServerNames;
               this._DynamicServerNames = (String[])((String[])v);
               this._postSet(25, oldVal, this._DynamicServerNames);
            } else if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("IgnoreSessionsDuringShutdown")) {
               oldVal = this._IgnoreSessionsDuringShutdown;
               this._IgnoreSessionsDuringShutdown = (Boolean)v;
               this._postSet(23, oldVal, this._IgnoreSessionsDuringShutdown);
            } else {
               String oldVal;
               if (name.equals("MachineMatchExpression")) {
                  oldVal = this._MachineMatchExpression;
                  this._MachineMatchExpression = (String)v;
                  this._postSet(15, oldVal, this._MachineMatchExpression);
               } else if (name.equals("MachineMatchType")) {
                  oldVal = this._MachineMatchType;
                  this._MachineMatchType = (String)v;
                  this._postSet(16, oldVal, this._MachineMatchType);
               } else if (name.equals("MachineNameMatchExpression")) {
                  oldVal = this._MachineNameMatchExpression;
                  this._MachineNameMatchExpression = (String)v;
                  this._postSet(14, oldVal, this._MachineNameMatchExpression);
               } else if (name.equals("MaxDynamicClusterSize")) {
                  oldVal = this._MaxDynamicClusterSize;
                  this._MaxDynamicClusterSize = (Integer)v;
                  this._postSet(20, oldVal, this._MaxDynamicClusterSize);
               } else if (name.equals("MaximumDynamicServerCount")) {
                  oldVal = this._MaximumDynamicServerCount;
                  this._MaximumDynamicServerCount = (Integer)v;
                  this._postSet(11, oldVal, this._MaximumDynamicServerCount);
               } else if (name.equals("MinDynamicClusterSize")) {
                  oldVal = this._MinDynamicClusterSize;
                  this._MinDynamicClusterSize = (Integer)v;
                  this._postSet(19, oldVal, this._MinDynamicClusterSize);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("ServerNamePrefix")) {
                  oldVal = this._ServerNamePrefix;
                  this._ServerNamePrefix = (String)v;
                  this._postSet(17, oldVal, this._ServerNamePrefix);
               } else if (name.equals("ServerNameStartingIndex")) {
                  oldVal = this._ServerNameStartingIndex;
                  this._ServerNameStartingIndex = (Integer)v;
                  this._postSet(26, oldVal, this._ServerNameStartingIndex);
               } else if (name.equals("ServerTemplate")) {
                  ServerTemplateMBean oldVal = this._ServerTemplate;
                  this._ServerTemplate = (ServerTemplateMBean)v;
                  this._postSet(10, oldVal, this._ServerTemplate);
               } else if (name.equals("Tags")) {
                  oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("WaitForAllSessionsDuringShutdown")) {
                  oldVal = this._WaitForAllSessionsDuringShutdown;
                  this._WaitForAllSessionsDuringShutdown = (Boolean)v;
                  this._postSet(24, oldVal, this._WaitForAllSessionsDuringShutdown);
               } else if (name.equals("customizer")) {
                  DynamicServers oldVal = this._customizer;
                  this._customizer = (DynamicServers)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("CalculatedListenPorts")) {
         return new Boolean(this._CalculatedListenPorts);
      } else if (name.equals("CalculatedMachineNames")) {
         return new Boolean(this._CalculatedMachineNames);
      } else if (name.equals("DynamicClusterCooloffPeriodSeconds")) {
         return new Integer(this._DynamicClusterCooloffPeriodSeconds);
      } else if (name.equals("DynamicClusterShutdownTimeoutSeconds")) {
         return new Integer(this._DynamicClusterShutdownTimeoutSeconds);
      } else if (name.equals("DynamicClusterSize")) {
         return new Integer(this._DynamicClusterSize);
      } else if (name.equals("DynamicServerNames")) {
         return this._DynamicServerNames;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("IgnoreSessionsDuringShutdown")) {
         return new Boolean(this._IgnoreSessionsDuringShutdown);
      } else if (name.equals("MachineMatchExpression")) {
         return this._MachineMatchExpression;
      } else if (name.equals("MachineMatchType")) {
         return this._MachineMatchType;
      } else if (name.equals("MachineNameMatchExpression")) {
         return this._MachineNameMatchExpression;
      } else if (name.equals("MaxDynamicClusterSize")) {
         return new Integer(this._MaxDynamicClusterSize);
      } else if (name.equals("MaximumDynamicServerCount")) {
         return new Integer(this._MaximumDynamicServerCount);
      } else if (name.equals("MinDynamicClusterSize")) {
         return new Integer(this._MinDynamicClusterSize);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("ServerNamePrefix")) {
         return this._ServerNamePrefix;
      } else if (name.equals("ServerNameStartingIndex")) {
         return new Integer(this._ServerNameStartingIndex);
      } else if (name.equals("ServerTemplate")) {
         return this._ServerTemplate;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("WaitForAllSessionsDuringShutdown")) {
         return new Boolean(this._WaitForAllSessionsDuringShutdown);
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
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 21:
            case 22:
            case 25:
            case 27:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 39:
            default:
               break;
            case 15:
               if (s.equals("server-template")) {
                  return 10;
               }
               break;
            case 18:
               if (s.equals("machine-match-type")) {
                  return 16;
               }

               if (s.equals("server-name-prefix")) {
                  return 17;
               }
               break;
            case 19:
               if (s.equals("dynamic-server-name")) {
                  return 25;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("dynamic-cluster-size")) {
                  return 18;
               }
               break;
            case 23:
               if (s.equals("calculated-listen-ports")) {
                  return 12;
               }
               break;
            case 24:
               if (s.equals("machine-match-expression")) {
                  return 15;
               }

               if (s.equals("max-dynamic-cluster-size")) {
                  return 20;
               }

               if (s.equals("min-dynamic-cluster-size")) {
                  return 19;
               }

               if (s.equals("calculated-machine-names")) {
                  return 13;
               }
               break;
            case 26:
               if (s.equals("server-name-starting-index")) {
                  return 26;
               }
               break;
            case 28:
               if (s.equals("maximum-dynamic-server-count")) {
                  return 11;
               }
               break;
            case 29:
               if (s.equals("machine-name-match-expression")) {
                  return 14;
               }
               break;
            case 31:
               if (s.equals("ignore-sessions-during-shutdown")) {
                  return 23;
               }
               break;
            case 37:
               if (s.equals("wait-for-all-sessions-during-shutdown")) {
                  return 24;
               }
               break;
            case 38:
               if (s.equals("dynamic-cluster-cooloff-period-seconds")) {
                  return 21;
               }
               break;
            case 40:
               if (s.equals("dynamic-cluster-shutdown-timeout-seconds")) {
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
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "server-template";
            case 11:
               return "maximum-dynamic-server-count";
            case 12:
               return "calculated-listen-ports";
            case 13:
               return "calculated-machine-names";
            case 14:
               return "machine-name-match-expression";
            case 15:
               return "machine-match-expression";
            case 16:
               return "machine-match-type";
            case 17:
               return "server-name-prefix";
            case 18:
               return "dynamic-cluster-size";
            case 19:
               return "min-dynamic-cluster-size";
            case 20:
               return "max-dynamic-cluster-size";
            case 21:
               return "dynamic-cluster-cooloff-period-seconds";
            case 22:
               return "dynamic-cluster-shutdown-timeout-seconds";
            case 23:
               return "ignore-sessions-during-shutdown";
            case 24:
               return "wait-for-all-sessions-during-shutdown";
            case 25:
               return "dynamic-server-name";
            case 26:
               return "server-name-starting-index";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 25:
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private DynamicServersMBeanImpl bean;

      protected Helper(DynamicServersMBeanImpl bean) {
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
               return "ServerTemplate";
            case 11:
               return "MaximumDynamicServerCount";
            case 12:
               return "CalculatedListenPorts";
            case 13:
               return "CalculatedMachineNames";
            case 14:
               return "MachineNameMatchExpression";
            case 15:
               return "MachineMatchExpression";
            case 16:
               return "MachineMatchType";
            case 17:
               return "ServerNamePrefix";
            case 18:
               return "DynamicClusterSize";
            case 19:
               return "MinDynamicClusterSize";
            case 20:
               return "MaxDynamicClusterSize";
            case 21:
               return "DynamicClusterCooloffPeriodSeconds";
            case 22:
               return "DynamicClusterShutdownTimeoutSeconds";
            case 23:
               return "IgnoreSessionsDuringShutdown";
            case 24:
               return "WaitForAllSessionsDuringShutdown";
            case 25:
               return "DynamicServerNames";
            case 26:
               return "ServerNameStartingIndex";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DynamicClusterCooloffPeriodSeconds")) {
            return 21;
         } else if (propName.equals("DynamicClusterShutdownTimeoutSeconds")) {
            return 22;
         } else if (propName.equals("DynamicClusterSize")) {
            return 18;
         } else if (propName.equals("DynamicServerNames")) {
            return 25;
         } else if (propName.equals("MachineMatchExpression")) {
            return 15;
         } else if (propName.equals("MachineMatchType")) {
            return 16;
         } else if (propName.equals("MachineNameMatchExpression")) {
            return 14;
         } else if (propName.equals("MaxDynamicClusterSize")) {
            return 20;
         } else if (propName.equals("MaximumDynamicServerCount")) {
            return 11;
         } else if (propName.equals("MinDynamicClusterSize")) {
            return 19;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("ServerNamePrefix")) {
            return 17;
         } else if (propName.equals("ServerNameStartingIndex")) {
            return 26;
         } else if (propName.equals("ServerTemplate")) {
            return 10;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("CalculatedListenPorts")) {
            return 12;
         } else if (propName.equals("CalculatedMachineNames")) {
            return 13;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("IgnoreSessionsDuringShutdown")) {
            return 23;
         } else {
            return propName.equals("WaitForAllSessionsDuringShutdown") ? 24 : super.getPropertyIndex(propName);
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
            if (this.bean.isDynamicClusterCooloffPeriodSecondsSet()) {
               buf.append("DynamicClusterCooloffPeriodSeconds");
               buf.append(String.valueOf(this.bean.getDynamicClusterCooloffPeriodSeconds()));
            }

            if (this.bean.isDynamicClusterShutdownTimeoutSecondsSet()) {
               buf.append("DynamicClusterShutdownTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getDynamicClusterShutdownTimeoutSeconds()));
            }

            if (this.bean.isDynamicClusterSizeSet()) {
               buf.append("DynamicClusterSize");
               buf.append(String.valueOf(this.bean.getDynamicClusterSize()));
            }

            if (this.bean.isDynamicServerNamesSet()) {
               buf.append("DynamicServerNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDynamicServerNames())));
            }

            if (this.bean.isMachineMatchExpressionSet()) {
               buf.append("MachineMatchExpression");
               buf.append(String.valueOf(this.bean.getMachineMatchExpression()));
            }

            if (this.bean.isMachineMatchTypeSet()) {
               buf.append("MachineMatchType");
               buf.append(String.valueOf(this.bean.getMachineMatchType()));
            }

            if (this.bean.isMachineNameMatchExpressionSet()) {
               buf.append("MachineNameMatchExpression");
               buf.append(String.valueOf(this.bean.getMachineNameMatchExpression()));
            }

            if (this.bean.isMaxDynamicClusterSizeSet()) {
               buf.append("MaxDynamicClusterSize");
               buf.append(String.valueOf(this.bean.getMaxDynamicClusterSize()));
            }

            if (this.bean.isMaximumDynamicServerCountSet()) {
               buf.append("MaximumDynamicServerCount");
               buf.append(String.valueOf(this.bean.getMaximumDynamicServerCount()));
            }

            if (this.bean.isMinDynamicClusterSizeSet()) {
               buf.append("MinDynamicClusterSize");
               buf.append(String.valueOf(this.bean.getMinDynamicClusterSize()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isServerNamePrefixSet()) {
               buf.append("ServerNamePrefix");
               buf.append(String.valueOf(this.bean.getServerNamePrefix()));
            }

            if (this.bean.isServerNameStartingIndexSet()) {
               buf.append("ServerNameStartingIndex");
               buf.append(String.valueOf(this.bean.getServerNameStartingIndex()));
            }

            if (this.bean.isServerTemplateSet()) {
               buf.append("ServerTemplate");
               buf.append(String.valueOf(this.bean.getServerTemplate()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isCalculatedListenPortsSet()) {
               buf.append("CalculatedListenPorts");
               buf.append(String.valueOf(this.bean.isCalculatedListenPorts()));
            }

            if (this.bean.isCalculatedMachineNamesSet()) {
               buf.append("CalculatedMachineNames");
               buf.append(String.valueOf(this.bean.isCalculatedMachineNames()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isIgnoreSessionsDuringShutdownSet()) {
               buf.append("IgnoreSessionsDuringShutdown");
               buf.append(String.valueOf(this.bean.isIgnoreSessionsDuringShutdown()));
            }

            if (this.bean.isWaitForAllSessionsDuringShutdownSet()) {
               buf.append("WaitForAllSessionsDuringShutdown");
               buf.append(String.valueOf(this.bean.isWaitForAllSessionsDuringShutdown()));
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
            DynamicServersMBeanImpl otherTyped = (DynamicServersMBeanImpl)other;
            this.computeDiff("DynamicClusterCooloffPeriodSeconds", this.bean.getDynamicClusterCooloffPeriodSeconds(), otherTyped.getDynamicClusterCooloffPeriodSeconds(), true);
            this.computeDiff("DynamicClusterShutdownTimeoutSeconds", this.bean.getDynamicClusterShutdownTimeoutSeconds(), otherTyped.getDynamicClusterShutdownTimeoutSeconds(), true);
            this.computeDiff("DynamicClusterSize", this.bean.getDynamicClusterSize(), otherTyped.getDynamicClusterSize(), true);
            this.computeDiff("DynamicServerNames", this.bean.getDynamicServerNames(), otherTyped.getDynamicServerNames(), true);
            this.computeDiff("MachineMatchExpression", this.bean.getMachineMatchExpression(), otherTyped.getMachineMatchExpression(), false);
            this.computeDiff("MachineMatchType", this.bean.getMachineMatchType(), otherTyped.getMachineMatchType(), false);
            this.computeDiff("MachineNameMatchExpression", this.bean.getMachineNameMatchExpression(), otherTyped.getMachineNameMatchExpression(), false);
            this.computeDiff("MaxDynamicClusterSize", this.bean.getMaxDynamicClusterSize(), otherTyped.getMaxDynamicClusterSize(), true);
            this.computeDiff("MaximumDynamicServerCount", this.bean.getMaximumDynamicServerCount(), otherTyped.getMaximumDynamicServerCount(), true);
            this.computeDiff("MinDynamicClusterSize", this.bean.getMinDynamicClusterSize(), otherTyped.getMinDynamicClusterSize(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("ServerNamePrefix", this.bean.getServerNamePrefix(), otherTyped.getServerNamePrefix(), false);
            this.computeDiff("ServerNameStartingIndex", this.bean.getServerNameStartingIndex(), otherTyped.getServerNameStartingIndex(), false);
            this.computeDiff("ServerTemplate", this.bean.getServerTemplate(), otherTyped.getServerTemplate(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("CalculatedListenPorts", this.bean.isCalculatedListenPorts(), otherTyped.isCalculatedListenPorts(), false);
            this.computeDiff("CalculatedMachineNames", this.bean.isCalculatedMachineNames(), otherTyped.isCalculatedMachineNames(), false);
            this.computeDiff("IgnoreSessionsDuringShutdown", this.bean.isIgnoreSessionsDuringShutdown(), otherTyped.isIgnoreSessionsDuringShutdown(), true);
            this.computeDiff("WaitForAllSessionsDuringShutdown", this.bean.isWaitForAllSessionsDuringShutdown(), otherTyped.isWaitForAllSessionsDuringShutdown(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DynamicServersMBeanImpl original = (DynamicServersMBeanImpl)event.getSourceBean();
            DynamicServersMBeanImpl proposed = (DynamicServersMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DynamicClusterCooloffPeriodSeconds")) {
                  original.setDynamicClusterCooloffPeriodSeconds(proposed.getDynamicClusterCooloffPeriodSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("DynamicClusterShutdownTimeoutSeconds")) {
                  original.setDynamicClusterShutdownTimeoutSeconds(proposed.getDynamicClusterShutdownTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("DynamicClusterSize")) {
                  original.setDynamicClusterSize(proposed.getDynamicClusterSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("DynamicServerNames")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("MachineMatchExpression")) {
                  original.setMachineMatchExpression(proposed.getMachineMatchExpression());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("MachineMatchType")) {
                  original.setMachineMatchType(proposed.getMachineMatchType());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("MachineNameMatchExpression")) {
                  original.setMachineNameMatchExpression(proposed.getMachineNameMatchExpression());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("MaxDynamicClusterSize")) {
                  original.setMaxDynamicClusterSize(proposed.getMaxDynamicClusterSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("MaximumDynamicServerCount")) {
                  original.setMaximumDynamicServerCount(proposed.getMaximumDynamicServerCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("MinDynamicClusterSize")) {
                  original.setMinDynamicClusterSize(proposed.getMinDynamicClusterSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ServerNamePrefix")) {
                  original.setServerNamePrefix(proposed.getServerNamePrefix());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("ServerNameStartingIndex")) {
                  original.setServerNameStartingIndex(proposed.getServerNameStartingIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("ServerTemplate")) {
                  original.setServerTemplateAsString(proposed.getServerTemplateAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
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
               } else if (prop.equals("CalculatedListenPorts")) {
                  original.setCalculatedListenPorts(proposed.isCalculatedListenPorts());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("CalculatedMachineNames")) {
                  original.setCalculatedMachineNames(proposed.isCalculatedMachineNames());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (!prop.equals("DynamicallyCreated")) {
                  if (prop.equals("IgnoreSessionsDuringShutdown")) {
                     original.setIgnoreSessionsDuringShutdown(proposed.isIgnoreSessionsDuringShutdown());
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  } else if (prop.equals("WaitForAllSessionsDuringShutdown")) {
                     original.setWaitForAllSessionsDuringShutdown(proposed.isWaitForAllSessionsDuringShutdown());
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
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
            DynamicServersMBeanImpl copy = (DynamicServersMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DynamicClusterCooloffPeriodSeconds")) && this.bean.isDynamicClusterCooloffPeriodSecondsSet()) {
               copy.setDynamicClusterCooloffPeriodSeconds(this.bean.getDynamicClusterCooloffPeriodSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("DynamicClusterShutdownTimeoutSeconds")) && this.bean.isDynamicClusterShutdownTimeoutSecondsSet()) {
               copy.setDynamicClusterShutdownTimeoutSeconds(this.bean.getDynamicClusterShutdownTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("DynamicClusterSize")) && this.bean.isDynamicClusterSizeSet()) {
               copy.setDynamicClusterSize(this.bean.getDynamicClusterSize());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("DynamicServerNames")) && this.bean.isDynamicServerNamesSet()) {
               o = this.bean.getDynamicServerNames();
               copy.setDynamicServerNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("MachineMatchExpression")) && this.bean.isMachineMatchExpressionSet()) {
               copy.setMachineMatchExpression(this.bean.getMachineMatchExpression());
            }

            if ((excludeProps == null || !excludeProps.contains("MachineMatchType")) && this.bean.isMachineMatchTypeSet()) {
               copy.setMachineMatchType(this.bean.getMachineMatchType());
            }

            if ((excludeProps == null || !excludeProps.contains("MachineNameMatchExpression")) && this.bean.isMachineNameMatchExpressionSet()) {
               copy.setMachineNameMatchExpression(this.bean.getMachineNameMatchExpression());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxDynamicClusterSize")) && this.bean.isMaxDynamicClusterSizeSet()) {
               copy.setMaxDynamicClusterSize(this.bean.getMaxDynamicClusterSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaximumDynamicServerCount")) && this.bean.isMaximumDynamicServerCountSet()) {
               copy.setMaximumDynamicServerCount(this.bean.getMaximumDynamicServerCount());
            }

            if ((excludeProps == null || !excludeProps.contains("MinDynamicClusterSize")) && this.bean.isMinDynamicClusterSizeSet()) {
               copy.setMinDynamicClusterSize(this.bean.getMinDynamicClusterSize());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerNamePrefix")) && this.bean.isServerNamePrefixSet()) {
               copy.setServerNamePrefix(this.bean.getServerNamePrefix());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerNameStartingIndex")) && this.bean.isServerNameStartingIndexSet()) {
               copy.setServerNameStartingIndex(this.bean.getServerNameStartingIndex());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerTemplate")) && this.bean.isServerTemplateSet()) {
               copy._unSet(copy, 10);
               copy.setServerTemplateAsString(this.bean.getServerTemplateAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("CalculatedListenPorts")) && this.bean.isCalculatedListenPortsSet()) {
               copy.setCalculatedListenPorts(this.bean.isCalculatedListenPorts());
            }

            if ((excludeProps == null || !excludeProps.contains("CalculatedMachineNames")) && this.bean.isCalculatedMachineNamesSet()) {
               copy.setCalculatedMachineNames(this.bean.isCalculatedMachineNames());
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreSessionsDuringShutdown")) && this.bean.isIgnoreSessionsDuringShutdownSet()) {
               copy.setIgnoreSessionsDuringShutdown(this.bean.isIgnoreSessionsDuringShutdown());
            }

            if ((excludeProps == null || !excludeProps.contains("WaitForAllSessionsDuringShutdown")) && this.bean.isWaitForAllSessionsDuringShutdownSet()) {
               copy.setWaitForAllSessionsDuringShutdown(this.bean.isWaitForAllSessionsDuringShutdown());
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
         this.inferSubTree(this.bean.getServerTemplate(), clazz, annotation);
      }
   }
}
