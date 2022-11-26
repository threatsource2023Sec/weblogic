package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class ReplicatedStoreMBeanImpl extends PersistentStoreMBeanImpl implements ReplicatedStoreMBean, Serializable {
   private String _Address;
   private int _BlockSize;
   private long _BusyWaitMicroSeconds;
   private String _Directory;
   private int _IoBufferSize;
   private int _LocalIndex;
   private int _MaxReplicaCount;
   private int _MaximumMessageSizePercent;
   private int _MinReplicaCount;
   private int _Port;
   private int _RegionSize;
   private long _SleepWaitMilliSeconds;
   private int _SpaceLoggingDeltaPercent;
   private int _SpaceLoggingStartPercent;
   private int _SpaceOverloadRedPercent;
   private int _SpaceOverloadYellowPercent;
   private static SchemaHelper2 _schemaHelper;

   public ReplicatedStoreMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ReplicatedStoreMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ReplicatedStoreMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDirectory() {
      return this._Directory;
   }

   public boolean isDirectoryInherited() {
      return false;
   }

   public boolean isDirectorySet() {
      return this._isSet(23);
   }

   public void setDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Directory", param0);
      LegalChecks.checkNonNull("Directory", param0);
      String _oldVal = this._Directory;
      this._Directory = param0;
      this._postSet(23, _oldVal, param0);
   }

   public String getAddress() {
      return this._Address;
   }

   public boolean isAddressInherited() {
      return false;
   }

   public boolean isAddressSet() {
      return this._isSet(24);
   }

   public void setAddress(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Address;
      this._Address = param0;
      this._postSet(24, _oldVal, param0);
   }

   public int getPort() {
      return this._Port;
   }

   public boolean isPortInherited() {
      return false;
   }

   public boolean isPortSet() {
      return this._isSet(25);
   }

   public void setPort(int param0) {
      LegalChecks.checkInRange("Port", (long)param0, -1L, 65535L);
      int _oldVal = this._Port;
      this._Port = param0;
      this._postSet(25, _oldVal, param0);
   }

   public int getLocalIndex() {
      return this._LocalIndex;
   }

   public boolean isLocalIndexInherited() {
      return false;
   }

   public boolean isLocalIndexSet() {
      return this._isSet(26);
   }

   public void setLocalIndex(int param0) {
      LegalChecks.checkInRange("LocalIndex", (long)param0, -1L, 65535L);
      int _oldVal = this._LocalIndex;
      this._LocalIndex = param0;
      this._postSet(26, _oldVal, param0);
   }

   public int getRegionSize() {
      return this._RegionSize;
   }

   public boolean isRegionSizeInherited() {
      return false;
   }

   public boolean isRegionSizeSet() {
      return this._isSet(27);
   }

   public void setRegionSize(int param0) {
      LegalChecks.checkInRange("RegionSize", (long)param0, 33554432L, 1073741824L);
      int _oldVal = this._RegionSize;
      this._RegionSize = param0;
      this._postSet(27, _oldVal, param0);
   }

   public int getBlockSize() {
      return this._BlockSize;
   }

   public boolean isBlockSizeInherited() {
      return false;
   }

   public boolean isBlockSizeSet() {
      return this._isSet(28);
   }

   public void setBlockSize(int param0) {
      LegalChecks.checkInRange("BlockSize", (long)param0, -1L, 8192L);
      int _oldVal = this._BlockSize;
      this._BlockSize = param0;
      this._postSet(28, _oldVal, param0);
   }

   public int getIoBufferSize() {
      return this._IoBufferSize;
   }

   public boolean isIoBufferSizeInherited() {
      return false;
   }

   public boolean isIoBufferSizeSet() {
      return this._isSet(29);
   }

   public void setIoBufferSize(int param0) {
      LegalChecks.checkInRange("IoBufferSize", (long)param0, -1L, 67108864L);
      int _oldVal = this._IoBufferSize;
      this._IoBufferSize = param0;
      this._postSet(29, _oldVal, param0);
   }

   public long getBusyWaitMicroSeconds() {
      return this._BusyWaitMicroSeconds;
   }

   public boolean isBusyWaitMicroSecondsInherited() {
      return false;
   }

   public boolean isBusyWaitMicroSecondsSet() {
      return this._isSet(30);
   }

   public void setBusyWaitMicroSeconds(long param0) {
      LegalChecks.checkInRange("BusyWaitMicroSeconds", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._BusyWaitMicroSeconds;
      this._BusyWaitMicroSeconds = param0;
      this._postSet(30, _oldVal, param0);
   }

   public long getSleepWaitMilliSeconds() {
      return this._SleepWaitMilliSeconds;
   }

   public boolean isSleepWaitMilliSecondsInherited() {
      return false;
   }

   public boolean isSleepWaitMilliSecondsSet() {
      return this._isSet(31);
   }

   public void setSleepWaitMilliSeconds(long param0) {
      LegalChecks.checkInRange("SleepWaitMilliSeconds", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._SleepWaitMilliSeconds;
      this._SleepWaitMilliSeconds = param0;
      this._postSet(31, _oldVal, param0);
   }

   public int getMinReplicaCount() {
      return this._MinReplicaCount;
   }

   public boolean isMinReplicaCountInherited() {
      return false;
   }

   public boolean isMinReplicaCountSet() {
      return this._isSet(32);
   }

   public void setMinReplicaCount(int param0) {
      LegalChecks.checkInRange("MinReplicaCount", (long)param0, 0L, 32L);
      int _oldVal = this._MinReplicaCount;
      this._MinReplicaCount = param0;
      this._postSet(32, _oldVal, param0);
   }

   public int getMaxReplicaCount() {
      return this._MaxReplicaCount;
   }

   public boolean isMaxReplicaCountInherited() {
      return false;
   }

   public boolean isMaxReplicaCountSet() {
      return this._isSet(33);
   }

   public void setMaxReplicaCount(int param0) {
      LegalChecks.checkInRange("MaxReplicaCount", (long)param0, 0L, 32L);
      int _oldVal = this._MaxReplicaCount;
      this._MaxReplicaCount = param0;
      this._postSet(33, _oldVal, param0);
   }

   public int getMaximumMessageSizePercent() {
      return this._MaximumMessageSizePercent;
   }

   public boolean isMaximumMessageSizePercentInherited() {
      return false;
   }

   public boolean isMaximumMessageSizePercentSet() {
      return this._isSet(34);
   }

   public void setMaximumMessageSizePercent(int param0) {
      LegalChecks.checkInRange("MaximumMessageSizePercent", (long)param0, 1L, 100L);
      int _oldVal = this._MaximumMessageSizePercent;
      this._MaximumMessageSizePercent = param0;
      this._postSet(34, _oldVal, param0);
   }

   public int getSpaceLoggingStartPercent() {
      return this._SpaceLoggingStartPercent;
   }

   public boolean isSpaceLoggingStartPercentInherited() {
      return false;
   }

   public boolean isSpaceLoggingStartPercentSet() {
      return this._isSet(35);
   }

   public void setSpaceLoggingStartPercent(int param0) {
      LegalChecks.checkInRange("SpaceLoggingStartPercent", (long)param0, 1L, 100L);
      int _oldVal = this._SpaceLoggingStartPercent;
      this._SpaceLoggingStartPercent = param0;
      this._postSet(35, _oldVal, param0);
   }

   public int getSpaceLoggingDeltaPercent() {
      return this._SpaceLoggingDeltaPercent;
   }

   public boolean isSpaceLoggingDeltaPercentInherited() {
      return false;
   }

   public boolean isSpaceLoggingDeltaPercentSet() {
      return this._isSet(36);
   }

   public void setSpaceLoggingDeltaPercent(int param0) {
      LegalChecks.checkInRange("SpaceLoggingDeltaPercent", (long)param0, 1L, 100L);
      int _oldVal = this._SpaceLoggingDeltaPercent;
      this._SpaceLoggingDeltaPercent = param0;
      this._postSet(36, _oldVal, param0);
   }

   public int getSpaceOverloadYellowPercent() {
      return this._SpaceOverloadYellowPercent;
   }

   public boolean isSpaceOverloadYellowPercentInherited() {
      return false;
   }

   public boolean isSpaceOverloadYellowPercentSet() {
      return this._isSet(37);
   }

   public void setSpaceOverloadYellowPercent(int param0) {
      LegalChecks.checkInRange("SpaceOverloadYellowPercent", (long)param0, 1L, 100L);
      int _oldVal = this._SpaceOverloadYellowPercent;
      this._SpaceOverloadYellowPercent = param0;
      this._postSet(37, _oldVal, param0);
   }

   public int getSpaceOverloadRedPercent() {
      return this._SpaceOverloadRedPercent;
   }

   public boolean isSpaceOverloadRedPercentInherited() {
      return false;
   }

   public boolean isSpaceOverloadRedPercentSet() {
      return this._isSet(38);
   }

   public void setSpaceOverloadRedPercent(int param0) {
      LegalChecks.checkInRange("SpaceOverloadRedPercent", (long)param0, 1L, 100L);
      int _oldVal = this._SpaceOverloadRedPercent;
      this._SpaceOverloadRedPercent = param0;
      this._postSet(38, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Directory", this.isDirectorySet());
      JMSLegalHelper.validateSpaceUsageSettings(this);
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
         idx = 24;
      }

      try {
         switch (idx) {
            case 24:
               this._Address = null;
               if (initOne) {
                  break;
               }
            case 28:
               this._BlockSize = -1;
               if (initOne) {
                  break;
               }
            case 30:
               this._BusyWaitMicroSeconds = -1L;
               if (initOne) {
                  break;
               }
            case 23:
               this._Directory = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._IoBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 26:
               this._LocalIndex = 0;
               if (initOne) {
                  break;
               }
            case 33:
               this._MaxReplicaCount = 1;
               if (initOne) {
                  break;
               }
            case 34:
               this._MaximumMessageSizePercent = 1;
               if (initOne) {
                  break;
               }
            case 32:
               this._MinReplicaCount = 0;
               if (initOne) {
                  break;
               }
            case 25:
               this._Port = 0;
               if (initOne) {
                  break;
               }
            case 27:
               this._RegionSize = 134217728;
               if (initOne) {
                  break;
               }
            case 31:
               this._SleepWaitMilliSeconds = -1L;
               if (initOne) {
                  break;
               }
            case 36:
               this._SpaceLoggingDeltaPercent = 10;
               if (initOne) {
                  break;
               }
            case 35:
               this._SpaceLoggingStartPercent = 70;
               if (initOne) {
                  break;
               }
            case 38:
               this._SpaceOverloadRedPercent = 90;
               if (initOne) {
                  break;
               }
            case 37:
               this._SpaceOverloadYellowPercent = 80;
               if (initOne) {
                  break;
               }
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
      return "ReplicatedStore";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Address")) {
         oldVal = this._Address;
         this._Address = (String)v;
         this._postSet(24, oldVal, this._Address);
      } else {
         int oldVal;
         if (name.equals("BlockSize")) {
            oldVal = this._BlockSize;
            this._BlockSize = (Integer)v;
            this._postSet(28, oldVal, this._BlockSize);
         } else {
            long oldVal;
            if (name.equals("BusyWaitMicroSeconds")) {
               oldVal = this._BusyWaitMicroSeconds;
               this._BusyWaitMicroSeconds = (Long)v;
               this._postSet(30, oldVal, this._BusyWaitMicroSeconds);
            } else if (name.equals("Directory")) {
               oldVal = this._Directory;
               this._Directory = (String)v;
               this._postSet(23, oldVal, this._Directory);
            } else if (name.equals("IoBufferSize")) {
               oldVal = this._IoBufferSize;
               this._IoBufferSize = (Integer)v;
               this._postSet(29, oldVal, this._IoBufferSize);
            } else if (name.equals("LocalIndex")) {
               oldVal = this._LocalIndex;
               this._LocalIndex = (Integer)v;
               this._postSet(26, oldVal, this._LocalIndex);
            } else if (name.equals("MaxReplicaCount")) {
               oldVal = this._MaxReplicaCount;
               this._MaxReplicaCount = (Integer)v;
               this._postSet(33, oldVal, this._MaxReplicaCount);
            } else if (name.equals("MaximumMessageSizePercent")) {
               oldVal = this._MaximumMessageSizePercent;
               this._MaximumMessageSizePercent = (Integer)v;
               this._postSet(34, oldVal, this._MaximumMessageSizePercent);
            } else if (name.equals("MinReplicaCount")) {
               oldVal = this._MinReplicaCount;
               this._MinReplicaCount = (Integer)v;
               this._postSet(32, oldVal, this._MinReplicaCount);
            } else if (name.equals("Port")) {
               oldVal = this._Port;
               this._Port = (Integer)v;
               this._postSet(25, oldVal, this._Port);
            } else if (name.equals("RegionSize")) {
               oldVal = this._RegionSize;
               this._RegionSize = (Integer)v;
               this._postSet(27, oldVal, this._RegionSize);
            } else if (name.equals("SleepWaitMilliSeconds")) {
               oldVal = this._SleepWaitMilliSeconds;
               this._SleepWaitMilliSeconds = (Long)v;
               this._postSet(31, oldVal, this._SleepWaitMilliSeconds);
            } else if (name.equals("SpaceLoggingDeltaPercent")) {
               oldVal = this._SpaceLoggingDeltaPercent;
               this._SpaceLoggingDeltaPercent = (Integer)v;
               this._postSet(36, oldVal, this._SpaceLoggingDeltaPercent);
            } else if (name.equals("SpaceLoggingStartPercent")) {
               oldVal = this._SpaceLoggingStartPercent;
               this._SpaceLoggingStartPercent = (Integer)v;
               this._postSet(35, oldVal, this._SpaceLoggingStartPercent);
            } else if (name.equals("SpaceOverloadRedPercent")) {
               oldVal = this._SpaceOverloadRedPercent;
               this._SpaceOverloadRedPercent = (Integer)v;
               this._postSet(38, oldVal, this._SpaceOverloadRedPercent);
            } else if (name.equals("SpaceOverloadYellowPercent")) {
               oldVal = this._SpaceOverloadYellowPercent;
               this._SpaceOverloadYellowPercent = (Integer)v;
               this._postSet(37, oldVal, this._SpaceOverloadYellowPercent);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("Address")) {
         return this._Address;
      } else if (name.equals("BlockSize")) {
         return new Integer(this._BlockSize);
      } else if (name.equals("BusyWaitMicroSeconds")) {
         return new Long(this._BusyWaitMicroSeconds);
      } else if (name.equals("Directory")) {
         return this._Directory;
      } else if (name.equals("IoBufferSize")) {
         return new Integer(this._IoBufferSize);
      } else if (name.equals("LocalIndex")) {
         return new Integer(this._LocalIndex);
      } else if (name.equals("MaxReplicaCount")) {
         return new Integer(this._MaxReplicaCount);
      } else if (name.equals("MaximumMessageSizePercent")) {
         return new Integer(this._MaximumMessageSizePercent);
      } else if (name.equals("MinReplicaCount")) {
         return new Integer(this._MinReplicaCount);
      } else if (name.equals("Port")) {
         return new Integer(this._Port);
      } else if (name.equals("RegionSize")) {
         return new Integer(this._RegionSize);
      } else if (name.equals("SleepWaitMilliSeconds")) {
         return new Long(this._SleepWaitMilliSeconds);
      } else if (name.equals("SpaceLoggingDeltaPercent")) {
         return new Integer(this._SpaceLoggingDeltaPercent);
      } else if (name.equals("SpaceLoggingStartPercent")) {
         return new Integer(this._SpaceLoggingStartPercent);
      } else if (name.equals("SpaceOverloadRedPercent")) {
         return new Integer(this._SpaceOverloadRedPercent);
      } else {
         return name.equals("SpaceOverloadYellowPercent") ? new Integer(this._SpaceOverloadYellowPercent) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends PersistentStoreMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("port")) {
                  return 25;
               }
            case 5:
            case 6:
            case 8:
            case 12:
            case 13:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 25:
            default:
               break;
            case 7:
               if (s.equals("address")) {
                  return 24;
               }
               break;
            case 9:
               if (s.equals("directory")) {
                  return 23;
               }
               break;
            case 10:
               if (s.equals("block-size")) {
                  return 28;
               }
               break;
            case 11:
               if (s.equals("local-index")) {
                  return 26;
               }

               if (s.equals("region-size")) {
                  return 27;
               }
               break;
            case 14:
               if (s.equals("io-buffer-size")) {
                  return 29;
               }
               break;
            case 17:
               if (s.equals("max-replica-count")) {
                  return 33;
               }

               if (s.equals("min-replica-count")) {
                  return 32;
               }
               break;
            case 23:
               if (s.equals("busy-wait-micro-seconds")) {
                  return 30;
               }
               break;
            case 24:
               if (s.equals("sleep-wait-milli-seconds")) {
                  return 31;
               }
               break;
            case 26:
               if (s.equals("space-overload-red-percent")) {
                  return 38;
               }
               break;
            case 27:
               if (s.equals("space-logging-delta-percent")) {
                  return 36;
               }

               if (s.equals("space-logging-start-percent")) {
                  return 35;
               }
               break;
            case 28:
               if (s.equals("maximum-message-size-percent")) {
                  return 34;
               }
               break;
            case 29:
               if (s.equals("space-overload-yellow-percent")) {
                  return 37;
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
            case 23:
               return "directory";
            case 24:
               return "address";
            case 25:
               return "port";
            case 26:
               return "local-index";
            case 27:
               return "region-size";
            case 28:
               return "block-size";
            case 29:
               return "io-buffer-size";
            case 30:
               return "busy-wait-micro-seconds";
            case 31:
               return "sleep-wait-milli-seconds";
            case 32:
               return "min-replica-count";
            case 33:
               return "max-replica-count";
            case 34:
               return "maximum-message-size-percent";
            case 35:
               return "space-logging-start-percent";
            case 36:
               return "space-logging-delta-percent";
            case 37:
               return "space-overload-yellow-percent";
            case 38:
               return "space-overload-red-percent";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends PersistentStoreMBeanImpl.Helper {
      private ReplicatedStoreMBeanImpl bean;

      protected Helper(ReplicatedStoreMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 23:
               return "Directory";
            case 24:
               return "Address";
            case 25:
               return "Port";
            case 26:
               return "LocalIndex";
            case 27:
               return "RegionSize";
            case 28:
               return "BlockSize";
            case 29:
               return "IoBufferSize";
            case 30:
               return "BusyWaitMicroSeconds";
            case 31:
               return "SleepWaitMilliSeconds";
            case 32:
               return "MinReplicaCount";
            case 33:
               return "MaxReplicaCount";
            case 34:
               return "MaximumMessageSizePercent";
            case 35:
               return "SpaceLoggingStartPercent";
            case 36:
               return "SpaceLoggingDeltaPercent";
            case 37:
               return "SpaceOverloadYellowPercent";
            case 38:
               return "SpaceOverloadRedPercent";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Address")) {
            return 24;
         } else if (propName.equals("BlockSize")) {
            return 28;
         } else if (propName.equals("BusyWaitMicroSeconds")) {
            return 30;
         } else if (propName.equals("Directory")) {
            return 23;
         } else if (propName.equals("IoBufferSize")) {
            return 29;
         } else if (propName.equals("LocalIndex")) {
            return 26;
         } else if (propName.equals("MaxReplicaCount")) {
            return 33;
         } else if (propName.equals("MaximumMessageSizePercent")) {
            return 34;
         } else if (propName.equals("MinReplicaCount")) {
            return 32;
         } else if (propName.equals("Port")) {
            return 25;
         } else if (propName.equals("RegionSize")) {
            return 27;
         } else if (propName.equals("SleepWaitMilliSeconds")) {
            return 31;
         } else if (propName.equals("SpaceLoggingDeltaPercent")) {
            return 36;
         } else if (propName.equals("SpaceLoggingStartPercent")) {
            return 35;
         } else if (propName.equals("SpaceOverloadRedPercent")) {
            return 38;
         } else {
            return propName.equals("SpaceOverloadYellowPercent") ? 37 : super.getPropertyIndex(propName);
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
            if (this.bean.isAddressSet()) {
               buf.append("Address");
               buf.append(String.valueOf(this.bean.getAddress()));
            }

            if (this.bean.isBlockSizeSet()) {
               buf.append("BlockSize");
               buf.append(String.valueOf(this.bean.getBlockSize()));
            }

            if (this.bean.isBusyWaitMicroSecondsSet()) {
               buf.append("BusyWaitMicroSeconds");
               buf.append(String.valueOf(this.bean.getBusyWaitMicroSeconds()));
            }

            if (this.bean.isDirectorySet()) {
               buf.append("Directory");
               buf.append(String.valueOf(this.bean.getDirectory()));
            }

            if (this.bean.isIoBufferSizeSet()) {
               buf.append("IoBufferSize");
               buf.append(String.valueOf(this.bean.getIoBufferSize()));
            }

            if (this.bean.isLocalIndexSet()) {
               buf.append("LocalIndex");
               buf.append(String.valueOf(this.bean.getLocalIndex()));
            }

            if (this.bean.isMaxReplicaCountSet()) {
               buf.append("MaxReplicaCount");
               buf.append(String.valueOf(this.bean.getMaxReplicaCount()));
            }

            if (this.bean.isMaximumMessageSizePercentSet()) {
               buf.append("MaximumMessageSizePercent");
               buf.append(String.valueOf(this.bean.getMaximumMessageSizePercent()));
            }

            if (this.bean.isMinReplicaCountSet()) {
               buf.append("MinReplicaCount");
               buf.append(String.valueOf(this.bean.getMinReplicaCount()));
            }

            if (this.bean.isPortSet()) {
               buf.append("Port");
               buf.append(String.valueOf(this.bean.getPort()));
            }

            if (this.bean.isRegionSizeSet()) {
               buf.append("RegionSize");
               buf.append(String.valueOf(this.bean.getRegionSize()));
            }

            if (this.bean.isSleepWaitMilliSecondsSet()) {
               buf.append("SleepWaitMilliSeconds");
               buf.append(String.valueOf(this.bean.getSleepWaitMilliSeconds()));
            }

            if (this.bean.isSpaceLoggingDeltaPercentSet()) {
               buf.append("SpaceLoggingDeltaPercent");
               buf.append(String.valueOf(this.bean.getSpaceLoggingDeltaPercent()));
            }

            if (this.bean.isSpaceLoggingStartPercentSet()) {
               buf.append("SpaceLoggingStartPercent");
               buf.append(String.valueOf(this.bean.getSpaceLoggingStartPercent()));
            }

            if (this.bean.isSpaceOverloadRedPercentSet()) {
               buf.append("SpaceOverloadRedPercent");
               buf.append(String.valueOf(this.bean.getSpaceOverloadRedPercent()));
            }

            if (this.bean.isSpaceOverloadYellowPercentSet()) {
               buf.append("SpaceOverloadYellowPercent");
               buf.append(String.valueOf(this.bean.getSpaceOverloadYellowPercent()));
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
            ReplicatedStoreMBeanImpl otherTyped = (ReplicatedStoreMBeanImpl)other;
            this.computeDiff("Address", this.bean.getAddress(), otherTyped.getAddress(), false);
            this.computeDiff("BlockSize", this.bean.getBlockSize(), otherTyped.getBlockSize(), false);
            this.computeDiff("BusyWaitMicroSeconds", this.bean.getBusyWaitMicroSeconds(), otherTyped.getBusyWaitMicroSeconds(), false);
            this.computeDiff("Directory", this.bean.getDirectory(), otherTyped.getDirectory(), false);
            this.computeDiff("IoBufferSize", this.bean.getIoBufferSize(), otherTyped.getIoBufferSize(), false);
            this.computeDiff("LocalIndex", this.bean.getLocalIndex(), otherTyped.getLocalIndex(), false);
            this.computeDiff("MaxReplicaCount", this.bean.getMaxReplicaCount(), otherTyped.getMaxReplicaCount(), false);
            this.computeDiff("MaximumMessageSizePercent", this.bean.getMaximumMessageSizePercent(), otherTyped.getMaximumMessageSizePercent(), false);
            this.computeDiff("MinReplicaCount", this.bean.getMinReplicaCount(), otherTyped.getMinReplicaCount(), false);
            this.computeDiff("Port", this.bean.getPort(), otherTyped.getPort(), false);
            this.computeDiff("RegionSize", this.bean.getRegionSize(), otherTyped.getRegionSize(), false);
            this.computeDiff("SleepWaitMilliSeconds", this.bean.getSleepWaitMilliSeconds(), otherTyped.getSleepWaitMilliSeconds(), false);
            this.computeDiff("SpaceLoggingDeltaPercent", this.bean.getSpaceLoggingDeltaPercent(), otherTyped.getSpaceLoggingDeltaPercent(), false);
            this.computeDiff("SpaceLoggingStartPercent", this.bean.getSpaceLoggingStartPercent(), otherTyped.getSpaceLoggingStartPercent(), false);
            this.computeDiff("SpaceOverloadRedPercent", this.bean.getSpaceOverloadRedPercent(), otherTyped.getSpaceOverloadRedPercent(), false);
            this.computeDiff("SpaceOverloadYellowPercent", this.bean.getSpaceOverloadYellowPercent(), otherTyped.getSpaceOverloadYellowPercent(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ReplicatedStoreMBeanImpl original = (ReplicatedStoreMBeanImpl)event.getSourceBean();
            ReplicatedStoreMBeanImpl proposed = (ReplicatedStoreMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Address")) {
                  original.setAddress(proposed.getAddress());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("BlockSize")) {
                  original.setBlockSize(proposed.getBlockSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("BusyWaitMicroSeconds")) {
                  original.setBusyWaitMicroSeconds(proposed.getBusyWaitMicroSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("Directory")) {
                  original.setDirectory(proposed.getDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("IoBufferSize")) {
                  original.setIoBufferSize(proposed.getIoBufferSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("LocalIndex")) {
                  original.setLocalIndex(proposed.getLocalIndex());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("MaxReplicaCount")) {
                  original.setMaxReplicaCount(proposed.getMaxReplicaCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("MaximumMessageSizePercent")) {
                  original.setMaximumMessageSizePercent(proposed.getMaximumMessageSizePercent());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("MinReplicaCount")) {
                  original.setMinReplicaCount(proposed.getMinReplicaCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("Port")) {
                  original.setPort(proposed.getPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("RegionSize")) {
                  original.setRegionSize(proposed.getRegionSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("SleepWaitMilliSeconds")) {
                  original.setSleepWaitMilliSeconds(proposed.getSleepWaitMilliSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("SpaceLoggingDeltaPercent")) {
                  original.setSpaceLoggingDeltaPercent(proposed.getSpaceLoggingDeltaPercent());
                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("SpaceLoggingStartPercent")) {
                  original.setSpaceLoggingStartPercent(proposed.getSpaceLoggingStartPercent());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("SpaceOverloadRedPercent")) {
                  original.setSpaceOverloadRedPercent(proposed.getSpaceOverloadRedPercent());
                  original._conditionalUnset(update.isUnsetUpdate(), 38);
               } else if (prop.equals("SpaceOverloadYellowPercent")) {
                  original.setSpaceOverloadYellowPercent(proposed.getSpaceOverloadYellowPercent());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else {
                  super.applyPropertyUpdate(event, update);
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
            ReplicatedStoreMBeanImpl copy = (ReplicatedStoreMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Address")) && this.bean.isAddressSet()) {
               copy.setAddress(this.bean.getAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("BlockSize")) && this.bean.isBlockSizeSet()) {
               copy.setBlockSize(this.bean.getBlockSize());
            }

            if ((excludeProps == null || !excludeProps.contains("BusyWaitMicroSeconds")) && this.bean.isBusyWaitMicroSecondsSet()) {
               copy.setBusyWaitMicroSeconds(this.bean.getBusyWaitMicroSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("Directory")) && this.bean.isDirectorySet()) {
               copy.setDirectory(this.bean.getDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("IoBufferSize")) && this.bean.isIoBufferSizeSet()) {
               copy.setIoBufferSize(this.bean.getIoBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalIndex")) && this.bean.isLocalIndexSet()) {
               copy.setLocalIndex(this.bean.getLocalIndex());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxReplicaCount")) && this.bean.isMaxReplicaCountSet()) {
               copy.setMaxReplicaCount(this.bean.getMaxReplicaCount());
            }

            if ((excludeProps == null || !excludeProps.contains("MaximumMessageSizePercent")) && this.bean.isMaximumMessageSizePercentSet()) {
               copy.setMaximumMessageSizePercent(this.bean.getMaximumMessageSizePercent());
            }

            if ((excludeProps == null || !excludeProps.contains("MinReplicaCount")) && this.bean.isMinReplicaCountSet()) {
               copy.setMinReplicaCount(this.bean.getMinReplicaCount());
            }

            if ((excludeProps == null || !excludeProps.contains("Port")) && this.bean.isPortSet()) {
               copy.setPort(this.bean.getPort());
            }

            if ((excludeProps == null || !excludeProps.contains("RegionSize")) && this.bean.isRegionSizeSet()) {
               copy.setRegionSize(this.bean.getRegionSize());
            }

            if ((excludeProps == null || !excludeProps.contains("SleepWaitMilliSeconds")) && this.bean.isSleepWaitMilliSecondsSet()) {
               copy.setSleepWaitMilliSeconds(this.bean.getSleepWaitMilliSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("SpaceLoggingDeltaPercent")) && this.bean.isSpaceLoggingDeltaPercentSet()) {
               copy.setSpaceLoggingDeltaPercent(this.bean.getSpaceLoggingDeltaPercent());
            }

            if ((excludeProps == null || !excludeProps.contains("SpaceLoggingStartPercent")) && this.bean.isSpaceLoggingStartPercentSet()) {
               copy.setSpaceLoggingStartPercent(this.bean.getSpaceLoggingStartPercent());
            }

            if ((excludeProps == null || !excludeProps.contains("SpaceOverloadRedPercent")) && this.bean.isSpaceOverloadRedPercentSet()) {
               copy.setSpaceOverloadRedPercent(this.bean.getSpaceOverloadRedPercent());
            }

            if ((excludeProps == null || !excludeProps.contains("SpaceOverloadYellowPercent")) && this.bean.isSpaceOverloadYellowPercentSet()) {
               copy.setSpaceOverloadYellowPercent(this.bean.getSpaceOverloadYellowPercent());
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
      }
   }
}
