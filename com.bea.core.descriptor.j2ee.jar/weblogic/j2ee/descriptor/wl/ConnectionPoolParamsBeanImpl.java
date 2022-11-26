package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class ConnectionPoolParamsBeanImpl extends SettableBeanImpl implements ConnectionPoolParamsBean, Serializable {
   private int _CapacityIncrement;
   private int _ConnectionCreationRetryFrequencySeconds;
   private int _ConnectionReserveTimeoutSeconds;
   private int _HighestNumUnavailable;
   private int _HighestNumWaiters;
   private boolean _IgnoreInUseConnectionsEnabled;
   private int _InitialCapacity;
   private int _MaxCapacity;
   private int _ProfileHarvestFrequencySeconds;
   private int _ShrinkFrequencySeconds;
   private boolean _ShrinkingEnabled;
   private boolean _TestConnectionsOnCreate;
   private boolean _TestConnectionsOnRelease;
   private boolean _TestConnectionsOnReserve;
   private int _TestFrequencySeconds;
   private static SchemaHelper2 _schemaHelper;

   public ConnectionPoolParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConnectionPoolParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConnectionPoolParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getInitialCapacity() {
      return this._InitialCapacity;
   }

   public boolean isInitialCapacityInherited() {
      return false;
   }

   public boolean isInitialCapacitySet() {
      return this._isSet(0);
   }

   public void setInitialCapacity(int param0) {
      LegalChecks.checkInRange("InitialCapacity", (long)param0, 0L, 2147483647L);
      int _oldVal = this._InitialCapacity;
      this._InitialCapacity = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getMaxCapacity() {
      return this._MaxCapacity;
   }

   public boolean isMaxCapacityInherited() {
      return false;
   }

   public boolean isMaxCapacitySet() {
      return this._isSet(1);
   }

   public void setMaxCapacity(int param0) {
      int _oldVal = this._MaxCapacity;
      this._MaxCapacity = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getCapacityIncrement() {
      return this._CapacityIncrement;
   }

   public boolean isCapacityIncrementInherited() {
      return false;
   }

   public boolean isCapacityIncrementSet() {
      return this._isSet(2);
   }

   public void setCapacityIncrement(int param0) {
      int _oldVal = this._CapacityIncrement;
      this._CapacityIncrement = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isShrinkingEnabled() {
      return this._ShrinkingEnabled;
   }

   public boolean isShrinkingEnabledInherited() {
      return false;
   }

   public boolean isShrinkingEnabledSet() {
      return this._isSet(3);
   }

   public void setShrinkingEnabled(boolean param0) {
      boolean _oldVal = this._ShrinkingEnabled;
      this._ShrinkingEnabled = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getShrinkFrequencySeconds() {
      return this._ShrinkFrequencySeconds;
   }

   public boolean isShrinkFrequencySecondsInherited() {
      return false;
   }

   public boolean isShrinkFrequencySecondsSet() {
      return this._isSet(4);
   }

   public void setShrinkFrequencySeconds(int param0) {
      int _oldVal = this._ShrinkFrequencySeconds;
      this._ShrinkFrequencySeconds = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getHighestNumWaiters() {
      return this._HighestNumWaiters;
   }

   public boolean isHighestNumWaitersInherited() {
      return false;
   }

   public boolean isHighestNumWaitersSet() {
      return this._isSet(5);
   }

   public void setHighestNumWaiters(int param0) {
      int _oldVal = this._HighestNumWaiters;
      this._HighestNumWaiters = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getHighestNumUnavailable() {
      return this._HighestNumUnavailable;
   }

   public boolean isHighestNumUnavailableInherited() {
      return false;
   }

   public boolean isHighestNumUnavailableSet() {
      return this._isSet(6);
   }

   public void setHighestNumUnavailable(int param0) {
      int _oldVal = this._HighestNumUnavailable;
      this._HighestNumUnavailable = param0;
      this._postSet(6, _oldVal, param0);
   }

   public int getConnectionCreationRetryFrequencySeconds() {
      return this._ConnectionCreationRetryFrequencySeconds;
   }

   public boolean isConnectionCreationRetryFrequencySecondsInherited() {
      return false;
   }

   public boolean isConnectionCreationRetryFrequencySecondsSet() {
      return this._isSet(7);
   }

   public void setConnectionCreationRetryFrequencySeconds(int param0) {
      int _oldVal = this._ConnectionCreationRetryFrequencySeconds;
      this._ConnectionCreationRetryFrequencySeconds = param0;
      this._postSet(7, _oldVal, param0);
   }

   public int getConnectionReserveTimeoutSeconds() {
      return this._ConnectionReserveTimeoutSeconds;
   }

   public boolean isConnectionReserveTimeoutSecondsInherited() {
      return false;
   }

   public boolean isConnectionReserveTimeoutSecondsSet() {
      return this._isSet(8);
   }

   public void setConnectionReserveTimeoutSeconds(int param0) {
      LegalChecks.checkInRange("ConnectionReserveTimeoutSeconds", (long)param0, -1L, 2147483647L);
      int _oldVal = this._ConnectionReserveTimeoutSeconds;
      this._ConnectionReserveTimeoutSeconds = param0;
      this._postSet(8, _oldVal, param0);
   }

   public int getTestFrequencySeconds() {
      return this._TestFrequencySeconds;
   }

   public boolean isTestFrequencySecondsInherited() {
      return false;
   }

   public boolean isTestFrequencySecondsSet() {
      return this._isSet(9);
   }

   public void setTestFrequencySeconds(int param0) {
      int _oldVal = this._TestFrequencySeconds;
      this._TestFrequencySeconds = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean isTestConnectionsOnCreate() {
      return this._TestConnectionsOnCreate;
   }

   public boolean isTestConnectionsOnCreateInherited() {
      return false;
   }

   public boolean isTestConnectionsOnCreateSet() {
      return this._isSet(10);
   }

   public void setTestConnectionsOnCreate(boolean param0) {
      boolean _oldVal = this._TestConnectionsOnCreate;
      this._TestConnectionsOnCreate = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isTestConnectionsOnRelease() {
      return this._TestConnectionsOnRelease;
   }

   public boolean isTestConnectionsOnReleaseInherited() {
      return false;
   }

   public boolean isTestConnectionsOnReleaseSet() {
      return this._isSet(11);
   }

   public void setTestConnectionsOnRelease(boolean param0) {
      boolean _oldVal = this._TestConnectionsOnRelease;
      this._TestConnectionsOnRelease = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isTestConnectionsOnReserve() {
      return this._TestConnectionsOnReserve;
   }

   public boolean isTestConnectionsOnReserveInherited() {
      return false;
   }

   public boolean isTestConnectionsOnReserveSet() {
      return this._isSet(12);
   }

   public void setTestConnectionsOnReserve(boolean param0) {
      boolean _oldVal = this._TestConnectionsOnReserve;
      this._TestConnectionsOnReserve = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getProfileHarvestFrequencySeconds() {
      return this._ProfileHarvestFrequencySeconds;
   }

   public boolean isProfileHarvestFrequencySecondsInherited() {
      return false;
   }

   public boolean isProfileHarvestFrequencySecondsSet() {
      return this._isSet(13);
   }

   public void setProfileHarvestFrequencySeconds(int param0) {
      int _oldVal = this._ProfileHarvestFrequencySeconds;
      this._ProfileHarvestFrequencySeconds = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isIgnoreInUseConnectionsEnabled() {
      return this._IgnoreInUseConnectionsEnabled;
   }

   public boolean isIgnoreInUseConnectionsEnabledInherited() {
      return false;
   }

   public boolean isIgnoreInUseConnectionsEnabledSet() {
      return this._isSet(14);
   }

   public void setIgnoreInUseConnectionsEnabled(boolean param0) {
      boolean _oldVal = this._IgnoreInUseConnectionsEnabled;
      this._IgnoreInUseConnectionsEnabled = param0;
      this._postSet(14, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      ConnectionPoolParamsValidator.validateConnectionPoolParamsBean(this);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._CapacityIncrement = 1;
               if (initOne) {
                  break;
               }
            case 7:
               this._ConnectionCreationRetryFrequencySeconds = 0;
               if (initOne) {
                  break;
               }
            case 8:
               this._ConnectionReserveTimeoutSeconds = 10;
               if (initOne) {
                  break;
               }
            case 6:
               this._HighestNumUnavailable = 0;
               if (initOne) {
                  break;
               }
            case 5:
               this._HighestNumWaiters = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 0:
               this._InitialCapacity = 1;
               if (initOne) {
                  break;
               }
            case 1:
               this._MaxCapacity = 15;
               if (initOne) {
                  break;
               }
            case 13:
               this._ProfileHarvestFrequencySeconds = 300;
               if (initOne) {
                  break;
               }
            case 4:
               this._ShrinkFrequencySeconds = 900;
               if (initOne) {
                  break;
               }
            case 9:
               this._TestFrequencySeconds = 0;
               if (initOne) {
                  break;
               }
            case 14:
               this._IgnoreInUseConnectionsEnabled = true;
               if (initOne) {
                  break;
               }
            case 3:
               this._ShrinkingEnabled = true;
               if (initOne) {
                  break;
               }
            case 10:
               this._TestConnectionsOnCreate = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._TestConnectionsOnRelease = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._TestConnectionsOnReserve = false;
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("max-capacity")) {
                  return 1;
               }
            case 13:
            case 14:
            case 15:
            case 20:
            case 21:
            case 25:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            default:
               break;
            case 16:
               if (s.equals("initial-capacity")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("shrinking-enabled")) {
                  return 3;
               }
               break;
            case 18:
               if (s.equals("capacity-increment")) {
                  return 2;
               }
               break;
            case 19:
               if (s.equals("highest-num-waiters")) {
                  return 5;
               }
               break;
            case 22:
               if (s.equals("test-frequency-seconds")) {
                  return 9;
               }
               break;
            case 23:
               if (s.equals("highest-num-unavailable")) {
                  return 6;
               }
               break;
            case 24:
               if (s.equals("shrink-frequency-seconds")) {
                  return 4;
               }
               break;
            case 26:
               if (s.equals("test-connections-on-create")) {
                  return 10;
               }
               break;
            case 27:
               if (s.equals("test-connections-on-release")) {
                  return 11;
               }

               if (s.equals("test-connections-on-reserve")) {
                  return 12;
               }
               break;
            case 33:
               if (s.equals("profile-harvest-frequency-seconds")) {
                  return 13;
               }

               if (s.equals("ignore-in-use-connections-enabled")) {
                  return 14;
               }
               break;
            case 34:
               if (s.equals("connection-reserve-timeout-seconds")) {
                  return 8;
               }
               break;
            case 43:
               if (s.equals("connection-creation-retry-frequency-seconds")) {
                  return 7;
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
            case 0:
               return "initial-capacity";
            case 1:
               return "max-capacity";
            case 2:
               return "capacity-increment";
            case 3:
               return "shrinking-enabled";
            case 4:
               return "shrink-frequency-seconds";
            case 5:
               return "highest-num-waiters";
            case 6:
               return "highest-num-unavailable";
            case 7:
               return "connection-creation-retry-frequency-seconds";
            case 8:
               return "connection-reserve-timeout-seconds";
            case 9:
               return "test-frequency-seconds";
            case 10:
               return "test-connections-on-create";
            case 11:
               return "test-connections-on-release";
            case 12:
               return "test-connections-on-reserve";
            case 13:
               return "profile-harvest-frequency-seconds";
            case 14:
               return "ignore-in-use-connections-enabled";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private ConnectionPoolParamsBeanImpl bean;

      protected Helper(ConnectionPoolParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "InitialCapacity";
            case 1:
               return "MaxCapacity";
            case 2:
               return "CapacityIncrement";
            case 3:
               return "ShrinkingEnabled";
            case 4:
               return "ShrinkFrequencySeconds";
            case 5:
               return "HighestNumWaiters";
            case 6:
               return "HighestNumUnavailable";
            case 7:
               return "ConnectionCreationRetryFrequencySeconds";
            case 8:
               return "ConnectionReserveTimeoutSeconds";
            case 9:
               return "TestFrequencySeconds";
            case 10:
               return "TestConnectionsOnCreate";
            case 11:
               return "TestConnectionsOnRelease";
            case 12:
               return "TestConnectionsOnReserve";
            case 13:
               return "ProfileHarvestFrequencySeconds";
            case 14:
               return "IgnoreInUseConnectionsEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CapacityIncrement")) {
            return 2;
         } else if (propName.equals("ConnectionCreationRetryFrequencySeconds")) {
            return 7;
         } else if (propName.equals("ConnectionReserveTimeoutSeconds")) {
            return 8;
         } else if (propName.equals("HighestNumUnavailable")) {
            return 6;
         } else if (propName.equals("HighestNumWaiters")) {
            return 5;
         } else if (propName.equals("InitialCapacity")) {
            return 0;
         } else if (propName.equals("MaxCapacity")) {
            return 1;
         } else if (propName.equals("ProfileHarvestFrequencySeconds")) {
            return 13;
         } else if (propName.equals("ShrinkFrequencySeconds")) {
            return 4;
         } else if (propName.equals("TestFrequencySeconds")) {
            return 9;
         } else if (propName.equals("IgnoreInUseConnectionsEnabled")) {
            return 14;
         } else if (propName.equals("ShrinkingEnabled")) {
            return 3;
         } else if (propName.equals("TestConnectionsOnCreate")) {
            return 10;
         } else if (propName.equals("TestConnectionsOnRelease")) {
            return 11;
         } else {
            return propName.equals("TestConnectionsOnReserve") ? 12 : super.getPropertyIndex(propName);
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
            if (this.bean.isCapacityIncrementSet()) {
               buf.append("CapacityIncrement");
               buf.append(String.valueOf(this.bean.getCapacityIncrement()));
            }

            if (this.bean.isConnectionCreationRetryFrequencySecondsSet()) {
               buf.append("ConnectionCreationRetryFrequencySeconds");
               buf.append(String.valueOf(this.bean.getConnectionCreationRetryFrequencySeconds()));
            }

            if (this.bean.isConnectionReserveTimeoutSecondsSet()) {
               buf.append("ConnectionReserveTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getConnectionReserveTimeoutSeconds()));
            }

            if (this.bean.isHighestNumUnavailableSet()) {
               buf.append("HighestNumUnavailable");
               buf.append(String.valueOf(this.bean.getHighestNumUnavailable()));
            }

            if (this.bean.isHighestNumWaitersSet()) {
               buf.append("HighestNumWaiters");
               buf.append(String.valueOf(this.bean.getHighestNumWaiters()));
            }

            if (this.bean.isInitialCapacitySet()) {
               buf.append("InitialCapacity");
               buf.append(String.valueOf(this.bean.getInitialCapacity()));
            }

            if (this.bean.isMaxCapacitySet()) {
               buf.append("MaxCapacity");
               buf.append(String.valueOf(this.bean.getMaxCapacity()));
            }

            if (this.bean.isProfileHarvestFrequencySecondsSet()) {
               buf.append("ProfileHarvestFrequencySeconds");
               buf.append(String.valueOf(this.bean.getProfileHarvestFrequencySeconds()));
            }

            if (this.bean.isShrinkFrequencySecondsSet()) {
               buf.append("ShrinkFrequencySeconds");
               buf.append(String.valueOf(this.bean.getShrinkFrequencySeconds()));
            }

            if (this.bean.isTestFrequencySecondsSet()) {
               buf.append("TestFrequencySeconds");
               buf.append(String.valueOf(this.bean.getTestFrequencySeconds()));
            }

            if (this.bean.isIgnoreInUseConnectionsEnabledSet()) {
               buf.append("IgnoreInUseConnectionsEnabled");
               buf.append(String.valueOf(this.bean.isIgnoreInUseConnectionsEnabled()));
            }

            if (this.bean.isShrinkingEnabledSet()) {
               buf.append("ShrinkingEnabled");
               buf.append(String.valueOf(this.bean.isShrinkingEnabled()));
            }

            if (this.bean.isTestConnectionsOnCreateSet()) {
               buf.append("TestConnectionsOnCreate");
               buf.append(String.valueOf(this.bean.isTestConnectionsOnCreate()));
            }

            if (this.bean.isTestConnectionsOnReleaseSet()) {
               buf.append("TestConnectionsOnRelease");
               buf.append(String.valueOf(this.bean.isTestConnectionsOnRelease()));
            }

            if (this.bean.isTestConnectionsOnReserveSet()) {
               buf.append("TestConnectionsOnReserve");
               buf.append(String.valueOf(this.bean.isTestConnectionsOnReserve()));
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
            ConnectionPoolParamsBeanImpl otherTyped = (ConnectionPoolParamsBeanImpl)other;
            this.computeDiff("CapacityIncrement", this.bean.getCapacityIncrement(), otherTyped.getCapacityIncrement(), true);
            this.computeDiff("ConnectionCreationRetryFrequencySeconds", this.bean.getConnectionCreationRetryFrequencySeconds(), otherTyped.getConnectionCreationRetryFrequencySeconds(), true);
            this.computeDiff("ConnectionReserveTimeoutSeconds", this.bean.getConnectionReserveTimeoutSeconds(), otherTyped.getConnectionReserveTimeoutSeconds(), true);
            this.computeDiff("HighestNumUnavailable", this.bean.getHighestNumUnavailable(), otherTyped.getHighestNumUnavailable(), true);
            this.computeDiff("HighestNumWaiters", this.bean.getHighestNumWaiters(), otherTyped.getHighestNumWaiters(), true);
            this.computeDiff("InitialCapacity", this.bean.getInitialCapacity(), otherTyped.getInitialCapacity(), true);
            this.computeDiff("MaxCapacity", this.bean.getMaxCapacity(), otherTyped.getMaxCapacity(), true);
            this.computeDiff("ProfileHarvestFrequencySeconds", this.bean.getProfileHarvestFrequencySeconds(), otherTyped.getProfileHarvestFrequencySeconds(), true);
            this.computeDiff("ShrinkFrequencySeconds", this.bean.getShrinkFrequencySeconds(), otherTyped.getShrinkFrequencySeconds(), true);
            this.computeDiff("TestFrequencySeconds", this.bean.getTestFrequencySeconds(), otherTyped.getTestFrequencySeconds(), true);
            this.computeDiff("IgnoreInUseConnectionsEnabled", this.bean.isIgnoreInUseConnectionsEnabled(), otherTyped.isIgnoreInUseConnectionsEnabled(), true);
            this.computeDiff("ShrinkingEnabled", this.bean.isShrinkingEnabled(), otherTyped.isShrinkingEnabled(), true);
            this.computeDiff("TestConnectionsOnCreate", this.bean.isTestConnectionsOnCreate(), otherTyped.isTestConnectionsOnCreate(), true);
            this.computeDiff("TestConnectionsOnRelease", this.bean.isTestConnectionsOnRelease(), otherTyped.isTestConnectionsOnRelease(), true);
            this.computeDiff("TestConnectionsOnReserve", this.bean.isTestConnectionsOnReserve(), otherTyped.isTestConnectionsOnReserve(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConnectionPoolParamsBeanImpl original = (ConnectionPoolParamsBeanImpl)event.getSourceBean();
            ConnectionPoolParamsBeanImpl proposed = (ConnectionPoolParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CapacityIncrement")) {
                  original.setCapacityIncrement(proposed.getCapacityIncrement());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ConnectionCreationRetryFrequencySeconds")) {
                  original.setConnectionCreationRetryFrequencySeconds(proposed.getConnectionCreationRetryFrequencySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("ConnectionReserveTimeoutSeconds")) {
                  original.setConnectionReserveTimeoutSeconds(proposed.getConnectionReserveTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("HighestNumUnavailable")) {
                  original.setHighestNumUnavailable(proposed.getHighestNumUnavailable());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("HighestNumWaiters")) {
                  original.setHighestNumWaiters(proposed.getHighestNumWaiters());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("InitialCapacity")) {
                  original.setInitialCapacity(proposed.getInitialCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MaxCapacity")) {
                  original.setMaxCapacity(proposed.getMaxCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ProfileHarvestFrequencySeconds")) {
                  original.setProfileHarvestFrequencySeconds(proposed.getProfileHarvestFrequencySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ShrinkFrequencySeconds")) {
                  original.setShrinkFrequencySeconds(proposed.getShrinkFrequencySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("TestFrequencySeconds")) {
                  original.setTestFrequencySeconds(proposed.getTestFrequencySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("IgnoreInUseConnectionsEnabled")) {
                  original.setIgnoreInUseConnectionsEnabled(proposed.isIgnoreInUseConnectionsEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("ShrinkingEnabled")) {
                  original.setShrinkingEnabled(proposed.isShrinkingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("TestConnectionsOnCreate")) {
                  original.setTestConnectionsOnCreate(proposed.isTestConnectionsOnCreate());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("TestConnectionsOnRelease")) {
                  original.setTestConnectionsOnRelease(proposed.isTestConnectionsOnRelease());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("TestConnectionsOnReserve")) {
                  original.setTestConnectionsOnReserve(proposed.isTestConnectionsOnReserve());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            ConnectionPoolParamsBeanImpl copy = (ConnectionPoolParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CapacityIncrement")) && this.bean.isCapacityIncrementSet()) {
               copy.setCapacityIncrement(this.bean.getCapacityIncrement());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionCreationRetryFrequencySeconds")) && this.bean.isConnectionCreationRetryFrequencySecondsSet()) {
               copy.setConnectionCreationRetryFrequencySeconds(this.bean.getConnectionCreationRetryFrequencySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionReserveTimeoutSeconds")) && this.bean.isConnectionReserveTimeoutSecondsSet()) {
               copy.setConnectionReserveTimeoutSeconds(this.bean.getConnectionReserveTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("HighestNumUnavailable")) && this.bean.isHighestNumUnavailableSet()) {
               copy.setHighestNumUnavailable(this.bean.getHighestNumUnavailable());
            }

            if ((excludeProps == null || !excludeProps.contains("HighestNumWaiters")) && this.bean.isHighestNumWaitersSet()) {
               copy.setHighestNumWaiters(this.bean.getHighestNumWaiters());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialCapacity")) && this.bean.isInitialCapacitySet()) {
               copy.setInitialCapacity(this.bean.getInitialCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxCapacity")) && this.bean.isMaxCapacitySet()) {
               copy.setMaxCapacity(this.bean.getMaxCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("ProfileHarvestFrequencySeconds")) && this.bean.isProfileHarvestFrequencySecondsSet()) {
               copy.setProfileHarvestFrequencySeconds(this.bean.getProfileHarvestFrequencySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("ShrinkFrequencySeconds")) && this.bean.isShrinkFrequencySecondsSet()) {
               copy.setShrinkFrequencySeconds(this.bean.getShrinkFrequencySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("TestFrequencySeconds")) && this.bean.isTestFrequencySecondsSet()) {
               copy.setTestFrequencySeconds(this.bean.getTestFrequencySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreInUseConnectionsEnabled")) && this.bean.isIgnoreInUseConnectionsEnabledSet()) {
               copy.setIgnoreInUseConnectionsEnabled(this.bean.isIgnoreInUseConnectionsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ShrinkingEnabled")) && this.bean.isShrinkingEnabledSet()) {
               copy.setShrinkingEnabled(this.bean.isShrinkingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TestConnectionsOnCreate")) && this.bean.isTestConnectionsOnCreateSet()) {
               copy.setTestConnectionsOnCreate(this.bean.isTestConnectionsOnCreate());
            }

            if ((excludeProps == null || !excludeProps.contains("TestConnectionsOnRelease")) && this.bean.isTestConnectionsOnReleaseSet()) {
               copy.setTestConnectionsOnRelease(this.bean.isTestConnectionsOnRelease());
            }

            if ((excludeProps == null || !excludeProps.contains("TestConnectionsOnReserve")) && this.bean.isTestConnectionsOnReserveSet()) {
               copy.setTestConnectionsOnReserve(this.bean.isTestConnectionsOnReserve());
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
