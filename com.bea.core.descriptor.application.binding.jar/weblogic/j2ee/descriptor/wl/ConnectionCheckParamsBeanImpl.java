package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class ConnectionCheckParamsBeanImpl extends AbstractDescriptorBean implements ConnectionCheckParamsBean, Serializable {
   private boolean _CheckOnCreateEnabled;
   private boolean _CheckOnReleaseEnabled;
   private boolean _CheckOnReserveEnabled;
   private int _ConnectionCreationRetryFrequencySeconds;
   private int _ConnectionReserveTimeoutSeconds;
   private int _InactiveConnectionTimeoutSeconds;
   private String _InitSql;
   private int _RefreshMinutes;
   private String _TableName;
   private int _TestFrequencySeconds;
   private static SchemaHelper2 _schemaHelper;

   public ConnectionCheckParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConnectionCheckParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConnectionCheckParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getTableName() {
      return this._TableName;
   }

   public boolean isTableNameInherited() {
      return false;
   }

   public boolean isTableNameSet() {
      return this._isSet(0);
   }

   public void setTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TableName;
      this._TableName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isCheckOnReserveEnabled() {
      return this._CheckOnReserveEnabled;
   }

   public boolean isCheckOnReserveEnabledInherited() {
      return false;
   }

   public boolean isCheckOnReserveEnabledSet() {
      return this._isSet(1);
   }

   public void setCheckOnReserveEnabled(boolean param0) {
      boolean _oldVal = this._CheckOnReserveEnabled;
      this._CheckOnReserveEnabled = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean isCheckOnReleaseEnabled() {
      return this._CheckOnReleaseEnabled;
   }

   public boolean isCheckOnReleaseEnabledInherited() {
      return false;
   }

   public boolean isCheckOnReleaseEnabledSet() {
      return this._isSet(2);
   }

   public void setCheckOnReleaseEnabled(boolean param0) {
      boolean _oldVal = this._CheckOnReleaseEnabled;
      this._CheckOnReleaseEnabled = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getRefreshMinutes() {
      return this._RefreshMinutes;
   }

   public boolean isRefreshMinutesInherited() {
      return false;
   }

   public boolean isRefreshMinutesSet() {
      return this._isSet(3);
   }

   public void setRefreshMinutes(int param0) {
      int _oldVal = this._RefreshMinutes;
      this._RefreshMinutes = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isCheckOnCreateEnabled() {
      return this._CheckOnCreateEnabled;
   }

   public boolean isCheckOnCreateEnabledInherited() {
      return false;
   }

   public boolean isCheckOnCreateEnabledSet() {
      return this._isSet(4);
   }

   public void setCheckOnCreateEnabled(boolean param0) {
      boolean _oldVal = this._CheckOnCreateEnabled;
      this._CheckOnCreateEnabled = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getConnectionReserveTimeoutSeconds() {
      return this._ConnectionReserveTimeoutSeconds;
   }

   public boolean isConnectionReserveTimeoutSecondsInherited() {
      return false;
   }

   public boolean isConnectionReserveTimeoutSecondsSet() {
      return this._isSet(5);
   }

   public void setConnectionReserveTimeoutSeconds(int param0) {
      int _oldVal = this._ConnectionReserveTimeoutSeconds;
      this._ConnectionReserveTimeoutSeconds = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getConnectionCreationRetryFrequencySeconds() {
      return this._ConnectionCreationRetryFrequencySeconds;
   }

   public boolean isConnectionCreationRetryFrequencySecondsInherited() {
      return false;
   }

   public boolean isConnectionCreationRetryFrequencySecondsSet() {
      return this._isSet(6);
   }

   public void setConnectionCreationRetryFrequencySeconds(int param0) {
      int _oldVal = this._ConnectionCreationRetryFrequencySeconds;
      this._ConnectionCreationRetryFrequencySeconds = param0;
      this._postSet(6, _oldVal, param0);
   }

   public int getInactiveConnectionTimeoutSeconds() {
      return this._InactiveConnectionTimeoutSeconds;
   }

   public boolean isInactiveConnectionTimeoutSecondsInherited() {
      return false;
   }

   public boolean isInactiveConnectionTimeoutSecondsSet() {
      return this._isSet(7);
   }

   public void setInactiveConnectionTimeoutSeconds(int param0) {
      int _oldVal = this._InactiveConnectionTimeoutSeconds;
      this._InactiveConnectionTimeoutSeconds = param0;
      this._postSet(7, _oldVal, param0);
   }

   public int getTestFrequencySeconds() {
      return this._TestFrequencySeconds;
   }

   public boolean isTestFrequencySecondsInherited() {
      return false;
   }

   public boolean isTestFrequencySecondsSet() {
      return this._isSet(8);
   }

   public void setTestFrequencySeconds(int param0) {
      int _oldVal = this._TestFrequencySeconds;
      this._TestFrequencySeconds = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getInitSql() {
      return this._InitSql;
   }

   public boolean isInitSqlInherited() {
      return false;
   }

   public boolean isInitSqlSet() {
      return this._isSet(9);
   }

   public void setInitSql(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InitSql;
      this._InitSql = param0;
      this._postSet(9, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
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
         idx = 6;
      }

      try {
         switch (idx) {
            case 6:
               this._ConnectionCreationRetryFrequencySeconds = 0;
               if (initOne) {
                  break;
               }
            case 5:
               this._ConnectionReserveTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 7:
               this._InactiveConnectionTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 9:
               this._InitSql = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._RefreshMinutes = 0;
               if (initOne) {
                  break;
               }
            case 0:
               this._TableName = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._TestFrequencySeconds = 0;
               if (initOne) {
                  break;
               }
            case 4:
               this._CheckOnCreateEnabled = false;
               if (initOne) {
                  break;
               }
            case 2:
               this._CheckOnReleaseEnabled = false;
               if (initOne) {
                  break;
               }
            case 1:
               this._CheckOnReserveEnabled = false;
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

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("init-sql")) {
                  return 9;
               }
               break;
            case 10:
               if (s.equals("table-name")) {
                  return 0;
               }
               break;
            case 15:
               if (s.equals("refresh-minutes")) {
                  return 3;
               }
               break;
            case 22:
               if (s.equals("test-frequency-seconds")) {
                  return 8;
               }
               break;
            case 23:
               if (s.equals("check-on-create-enabled")) {
                  return 4;
               }
               break;
            case 24:
               if (s.equals("check-on-release-enabled")) {
                  return 2;
               }

               if (s.equals("check-on-reserve-enabled")) {
                  return 1;
               }
               break;
            case 34:
               if (s.equals("connection-reserve-timeout-seconds")) {
                  return 5;
               }
               break;
            case 35:
               if (s.equals("inactive-connection-timeout-seconds")) {
                  return 7;
               }
               break;
            case 43:
               if (s.equals("connection-creation-retry-frequency-seconds")) {
                  return 6;
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
               return "table-name";
            case 1:
               return "check-on-reserve-enabled";
            case 2:
               return "check-on-release-enabled";
            case 3:
               return "refresh-minutes";
            case 4:
               return "check-on-create-enabled";
            case 5:
               return "connection-reserve-timeout-seconds";
            case 6:
               return "connection-creation-retry-frequency-seconds";
            case 7:
               return "inactive-connection-timeout-seconds";
            case 8:
               return "test-frequency-seconds";
            case 9:
               return "init-sql";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ConnectionCheckParamsBeanImpl bean;

      protected Helper(ConnectionCheckParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TableName";
            case 1:
               return "CheckOnReserveEnabled";
            case 2:
               return "CheckOnReleaseEnabled";
            case 3:
               return "RefreshMinutes";
            case 4:
               return "CheckOnCreateEnabled";
            case 5:
               return "ConnectionReserveTimeoutSeconds";
            case 6:
               return "ConnectionCreationRetryFrequencySeconds";
            case 7:
               return "InactiveConnectionTimeoutSeconds";
            case 8:
               return "TestFrequencySeconds";
            case 9:
               return "InitSql";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionCreationRetryFrequencySeconds")) {
            return 6;
         } else if (propName.equals("ConnectionReserveTimeoutSeconds")) {
            return 5;
         } else if (propName.equals("InactiveConnectionTimeoutSeconds")) {
            return 7;
         } else if (propName.equals("InitSql")) {
            return 9;
         } else if (propName.equals("RefreshMinutes")) {
            return 3;
         } else if (propName.equals("TableName")) {
            return 0;
         } else if (propName.equals("TestFrequencySeconds")) {
            return 8;
         } else if (propName.equals("CheckOnCreateEnabled")) {
            return 4;
         } else if (propName.equals("CheckOnReleaseEnabled")) {
            return 2;
         } else {
            return propName.equals("CheckOnReserveEnabled") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionCreationRetryFrequencySecondsSet()) {
               buf.append("ConnectionCreationRetryFrequencySeconds");
               buf.append(String.valueOf(this.bean.getConnectionCreationRetryFrequencySeconds()));
            }

            if (this.bean.isConnectionReserveTimeoutSecondsSet()) {
               buf.append("ConnectionReserveTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getConnectionReserveTimeoutSeconds()));
            }

            if (this.bean.isInactiveConnectionTimeoutSecondsSet()) {
               buf.append("InactiveConnectionTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getInactiveConnectionTimeoutSeconds()));
            }

            if (this.bean.isInitSqlSet()) {
               buf.append("InitSql");
               buf.append(String.valueOf(this.bean.getInitSql()));
            }

            if (this.bean.isRefreshMinutesSet()) {
               buf.append("RefreshMinutes");
               buf.append(String.valueOf(this.bean.getRefreshMinutes()));
            }

            if (this.bean.isTableNameSet()) {
               buf.append("TableName");
               buf.append(String.valueOf(this.bean.getTableName()));
            }

            if (this.bean.isTestFrequencySecondsSet()) {
               buf.append("TestFrequencySeconds");
               buf.append(String.valueOf(this.bean.getTestFrequencySeconds()));
            }

            if (this.bean.isCheckOnCreateEnabledSet()) {
               buf.append("CheckOnCreateEnabled");
               buf.append(String.valueOf(this.bean.isCheckOnCreateEnabled()));
            }

            if (this.bean.isCheckOnReleaseEnabledSet()) {
               buf.append("CheckOnReleaseEnabled");
               buf.append(String.valueOf(this.bean.isCheckOnReleaseEnabled()));
            }

            if (this.bean.isCheckOnReserveEnabledSet()) {
               buf.append("CheckOnReserveEnabled");
               buf.append(String.valueOf(this.bean.isCheckOnReserveEnabled()));
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
            ConnectionCheckParamsBeanImpl otherTyped = (ConnectionCheckParamsBeanImpl)other;
            this.computeDiff("ConnectionCreationRetryFrequencySeconds", this.bean.getConnectionCreationRetryFrequencySeconds(), otherTyped.getConnectionCreationRetryFrequencySeconds(), false);
            this.computeDiff("ConnectionReserveTimeoutSeconds", this.bean.getConnectionReserveTimeoutSeconds(), otherTyped.getConnectionReserveTimeoutSeconds(), false);
            this.computeDiff("InactiveConnectionTimeoutSeconds", this.bean.getInactiveConnectionTimeoutSeconds(), otherTyped.getInactiveConnectionTimeoutSeconds(), false);
            this.computeDiff("InitSql", this.bean.getInitSql(), otherTyped.getInitSql(), false);
            this.computeDiff("RefreshMinutes", this.bean.getRefreshMinutes(), otherTyped.getRefreshMinutes(), false);
            this.computeDiff("TableName", this.bean.getTableName(), otherTyped.getTableName(), false);
            this.computeDiff("TestFrequencySeconds", this.bean.getTestFrequencySeconds(), otherTyped.getTestFrequencySeconds(), false);
            this.computeDiff("CheckOnCreateEnabled", this.bean.isCheckOnCreateEnabled(), otherTyped.isCheckOnCreateEnabled(), false);
            this.computeDiff("CheckOnReleaseEnabled", this.bean.isCheckOnReleaseEnabled(), otherTyped.isCheckOnReleaseEnabled(), false);
            this.computeDiff("CheckOnReserveEnabled", this.bean.isCheckOnReserveEnabled(), otherTyped.isCheckOnReserveEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConnectionCheckParamsBeanImpl original = (ConnectionCheckParamsBeanImpl)event.getSourceBean();
            ConnectionCheckParamsBeanImpl proposed = (ConnectionCheckParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionCreationRetryFrequencySeconds")) {
                  original.setConnectionCreationRetryFrequencySeconds(proposed.getConnectionCreationRetryFrequencySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("ConnectionReserveTimeoutSeconds")) {
                  original.setConnectionReserveTimeoutSeconds(proposed.getConnectionReserveTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("InactiveConnectionTimeoutSeconds")) {
                  original.setInactiveConnectionTimeoutSeconds(proposed.getInactiveConnectionTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("InitSql")) {
                  original.setInitSql(proposed.getInitSql());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("RefreshMinutes")) {
                  original.setRefreshMinutes(proposed.getRefreshMinutes());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("TableName")) {
                  original.setTableName(proposed.getTableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("TestFrequencySeconds")) {
                  original.setTestFrequencySeconds(proposed.getTestFrequencySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("CheckOnCreateEnabled")) {
                  original.setCheckOnCreateEnabled(proposed.isCheckOnCreateEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("CheckOnReleaseEnabled")) {
                  original.setCheckOnReleaseEnabled(proposed.isCheckOnReleaseEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("CheckOnReserveEnabled")) {
                  original.setCheckOnReserveEnabled(proposed.isCheckOnReserveEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            ConnectionCheckParamsBeanImpl copy = (ConnectionCheckParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionCreationRetryFrequencySeconds")) && this.bean.isConnectionCreationRetryFrequencySecondsSet()) {
               copy.setConnectionCreationRetryFrequencySeconds(this.bean.getConnectionCreationRetryFrequencySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionReserveTimeoutSeconds")) && this.bean.isConnectionReserveTimeoutSecondsSet()) {
               copy.setConnectionReserveTimeoutSeconds(this.bean.getConnectionReserveTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("InactiveConnectionTimeoutSeconds")) && this.bean.isInactiveConnectionTimeoutSecondsSet()) {
               copy.setInactiveConnectionTimeoutSeconds(this.bean.getInactiveConnectionTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("InitSql")) && this.bean.isInitSqlSet()) {
               copy.setInitSql(this.bean.getInitSql());
            }

            if ((excludeProps == null || !excludeProps.contains("RefreshMinutes")) && this.bean.isRefreshMinutesSet()) {
               copy.setRefreshMinutes(this.bean.getRefreshMinutes());
            }

            if ((excludeProps == null || !excludeProps.contains("TableName")) && this.bean.isTableNameSet()) {
               copy.setTableName(this.bean.getTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("TestFrequencySeconds")) && this.bean.isTestFrequencySecondsSet()) {
               copy.setTestFrequencySeconds(this.bean.getTestFrequencySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("CheckOnCreateEnabled")) && this.bean.isCheckOnCreateEnabledSet()) {
               copy.setCheckOnCreateEnabled(this.bean.isCheckOnCreateEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("CheckOnReleaseEnabled")) && this.bean.isCheckOnReleaseEnabledSet()) {
               copy.setCheckOnReleaseEnabled(this.bean.isCheckOnReleaseEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("CheckOnReserveEnabled")) && this.bean.isCheckOnReserveEnabledSet()) {
               copy.setCheckOnReserveEnabled(this.bean.isCheckOnReserveEnabled());
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
