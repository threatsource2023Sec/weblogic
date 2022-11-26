package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.jdbc.common.internal.JDBCHelperImpl;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JDBCOracleParamsBeanImpl extends SettableBeanImpl implements JDBCOracleParamsBean, Serializable {
   private boolean _ActiveGridlink;
   private String _AffinityPolicy;
   private String _ConnectionInitializationCallback;
   private boolean _FanEnabled;
   private String _OnsNodeList;
   private String _OnsWalletFile;
   private String _OnsWalletPassword;
   private byte[] _OnsWalletPasswordEncrypted;
   private boolean _OracleEnableJavaNetFastPath;
   private boolean _OracleOptimizeUtf8Conversion;
   private boolean _OracleProxySession;
   private int _ReplayInitiationTimeout;
   private boolean _UseDatabaseCredentials;
   private static SchemaHelper2 _schemaHelper;

   public JDBCOracleParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public JDBCOracleParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JDBCOracleParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isFanEnabled() {
      return this._FanEnabled;
   }

   public boolean isFanEnabledInherited() {
      return false;
   }

   public boolean isFanEnabledSet() {
      return this._isSet(0);
   }

   public void setFanEnabled(boolean param0) {
      boolean _oldVal = this._FanEnabled;
      this._FanEnabled = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getOnsNodeList() {
      return this._OnsNodeList;
   }

   public boolean isOnsNodeListInherited() {
      return false;
   }

   public boolean isOnsNodeListSet() {
      return this._isSet(1);
   }

   public void setOnsNodeList(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OnsNodeList;
      this._OnsNodeList = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getOnsWalletFile() {
      return this._OnsWalletFile;
   }

   public boolean isOnsWalletFileInherited() {
      return false;
   }

   public boolean isOnsWalletFileSet() {
      return this._isSet(2);
   }

   public void setOnsWalletFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OnsWalletFile;
      this._OnsWalletFile = param0;
      this._postSet(2, _oldVal, param0);
   }

   public byte[] getOnsWalletPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._OnsWalletPasswordEncrypted);
   }

   public String getOnsWalletPasswordEncryptedAsString() {
      byte[] obj = this.getOnsWalletPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isOnsWalletPasswordEncryptedInherited() {
      return false;
   }

   public boolean isOnsWalletPasswordEncryptedSet() {
      return this._isSet(3);
   }

   public void setOnsWalletPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setOnsWalletPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getOnsWalletPassword() {
      byte[] bEncrypted = this.getOnsWalletPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("OnsWalletPassword", bEncrypted);
   }

   public boolean isOnsWalletPasswordInherited() {
      return false;
   }

   public boolean isOnsWalletPasswordSet() {
      return this.isOnsWalletPasswordEncryptedSet();
   }

   public void setOnsWalletPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this.setOnsWalletPasswordEncrypted(param0 == null ? null : this._encrypt("OnsWalletPassword", param0));
   }

   public boolean isOracleEnableJavaNetFastPath() {
      return this._OracleEnableJavaNetFastPath;
   }

   public boolean isOracleEnableJavaNetFastPathInherited() {
      return false;
   }

   public boolean isOracleEnableJavaNetFastPathSet() {
      return this._isSet(5);
   }

   public void setOracleEnableJavaNetFastPath(boolean param0) {
      boolean _oldVal = this._OracleEnableJavaNetFastPath;
      this._OracleEnableJavaNetFastPath = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isOracleOptimizeUtf8Conversion() {
      if (!this._isSet(6)) {
         try {
            return JDBCHelperImpl.isExalogicOptimizationsEnabled();
         } catch (NullPointerException var2) {
         }
      }

      return this._OracleOptimizeUtf8Conversion;
   }

   public boolean isOracleOptimizeUtf8ConversionInherited() {
      return false;
   }

   public boolean isOracleOptimizeUtf8ConversionSet() {
      return this._isSet(6);
   }

   public void setOracleOptimizeUtf8Conversion(boolean param0) {
      boolean _oldVal = this._OracleOptimizeUtf8Conversion;
      this._OracleOptimizeUtf8Conversion = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getConnectionInitializationCallback() {
      return this._ConnectionInitializationCallback;
   }

   public boolean isConnectionInitializationCallbackInherited() {
      return false;
   }

   public boolean isConnectionInitializationCallbackSet() {
      return this._isSet(7);
   }

   public void setConnectionInitializationCallback(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionInitializationCallback;
      this._ConnectionInitializationCallback = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getAffinityPolicy() {
      return this._AffinityPolicy;
   }

   public boolean isAffinityPolicyInherited() {
      return false;
   }

   public boolean isAffinityPolicySet() {
      return this._isSet(8);
   }

   public void setAffinityPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Transaction", "Session", "Data", "None"};
      param0 = LegalChecks.checkInEnum("AffinityPolicy", param0, _set);
      String _oldVal = this._AffinityPolicy;
      this._AffinityPolicy = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean isOracleProxySession() {
      return this._OracleProxySession;
   }

   public boolean isOracleProxySessionInherited() {
      return false;
   }

   public boolean isOracleProxySessionSet() {
      return this._isSet(9);
   }

   public void setOracleProxySession(boolean param0) {
      boolean _oldVal = this._OracleProxySession;
      this._OracleProxySession = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean isUseDatabaseCredentials() {
      return this._UseDatabaseCredentials;
   }

   public boolean isUseDatabaseCredentialsInherited() {
      return false;
   }

   public boolean isUseDatabaseCredentialsSet() {
      return this._isSet(10);
   }

   public void setUseDatabaseCredentials(boolean param0) {
      boolean _oldVal = this._UseDatabaseCredentials;
      this._UseDatabaseCredentials = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getReplayInitiationTimeout() {
      return this._ReplayInitiationTimeout;
   }

   public boolean isReplayInitiationTimeoutInherited() {
      return false;
   }

   public boolean isReplayInitiationTimeoutSet() {
      return this._isSet(11);
   }

   public void setReplayInitiationTimeout(int param0) {
      LegalChecks.checkInRange("ReplayInitiationTimeout", (long)param0, 0L, 2147483647L);
      int _oldVal = this._ReplayInitiationTimeout;
      this._ReplayInitiationTimeout = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isActiveGridlink() {
      return this._ActiveGridlink;
   }

   public boolean isActiveGridlinkInherited() {
      return false;
   }

   public boolean isActiveGridlinkSet() {
      return this._isSet(12);
   }

   public void setActiveGridlink(boolean param0) {
      boolean _oldVal = this._ActiveGridlink;
      this._ActiveGridlink = param0;
      this._postSet(12, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setOnsWalletPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._OnsWalletPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: OnsWalletPasswordEncrypted of JDBCOracleParamsBean");
      } else {
         this._getHelper()._clearArray(this._OnsWalletPasswordEncrypted);
         this._OnsWalletPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(3, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 4) {
            this._markSet(3, false);
         }
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
         idx = 8;
      }

      try {
         switch (idx) {
            case 8:
               this._AffinityPolicy = "Session";
               if (initOne) {
                  break;
               }
            case 7:
               this._ConnectionInitializationCallback = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._OnsNodeList = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._OnsWalletFile = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._OnsWalletPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._OnsWalletPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._ReplayInitiationTimeout = 3600;
               if (initOne) {
                  break;
               }
            case 12:
               this._ActiveGridlink = false;
               if (initOne) {
                  break;
               }
            case 0:
               this._FanEnabled = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._OracleEnableJavaNetFastPath = false;
               if (initOne) {
                  break;
               }
            case 6:
               this._OracleOptimizeUtf8Conversion = false;
               if (initOne) {
                  break;
               }
            case 9:
               this._OracleProxySession = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._UseDatabaseCredentials = false;
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
            case 11:
               if (s.equals("fan-enabled")) {
                  return 0;
               }
            case 12:
            case 14:
            case 16:
            case 17:
            case 18:
            case 21:
            case 22:
            case 23:
            case 26:
            case 27:
            case 28:
            case 30:
            case 33:
            default:
               break;
            case 13:
               if (s.equals("ons-node-list")) {
                  return 1;
               }
               break;
            case 15:
               if (s.equals("affinity-policy")) {
                  return 8;
               }

               if (s.equals("ons-wallet-file")) {
                  return 2;
               }

               if (s.equals("active-gridlink")) {
                  return 12;
               }
               break;
            case 19:
               if (s.equals("ons-wallet-password")) {
                  return 4;
               }
               break;
            case 20:
               if (s.equals("oracle-proxy-session")) {
                  return 9;
               }
               break;
            case 24:
               if (s.equals("use-database-credentials")) {
                  return 10;
               }
               break;
            case 25:
               if (s.equals("replay-initiation-timeout")) {
                  return 11;
               }
               break;
            case 29:
               if (s.equals("ons-wallet-password-encrypted")) {
                  return 3;
               }
               break;
            case 31:
               if (s.equals("oracle-optimize-utf8-conversion")) {
                  return 6;
               }
               break;
            case 32:
               if (s.equals("oracle-enable-java-net-fast-path")) {
                  return 5;
               }
               break;
            case 34:
               if (s.equals("connection-initialization-callback")) {
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
               return "fan-enabled";
            case 1:
               return "ons-node-list";
            case 2:
               return "ons-wallet-file";
            case 3:
               return "ons-wallet-password-encrypted";
            case 4:
               return "ons-wallet-password";
            case 5:
               return "oracle-enable-java-net-fast-path";
            case 6:
               return "oracle-optimize-utf8-conversion";
            case 7:
               return "connection-initialization-callback";
            case 8:
               return "affinity-policy";
            case 9:
               return "oracle-proxy-session";
            case 10:
               return "use-database-credentials";
            case 11:
               return "replay-initiation-timeout";
            case 12:
               return "active-gridlink";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            default:
               return super.isArray(propIndex);
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private JDBCOracleParamsBeanImpl bean;

      protected Helper(JDBCOracleParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "FanEnabled";
            case 1:
               return "OnsNodeList";
            case 2:
               return "OnsWalletFile";
            case 3:
               return "OnsWalletPasswordEncrypted";
            case 4:
               return "OnsWalletPassword";
            case 5:
               return "OracleEnableJavaNetFastPath";
            case 6:
               return "OracleOptimizeUtf8Conversion";
            case 7:
               return "ConnectionInitializationCallback";
            case 8:
               return "AffinityPolicy";
            case 9:
               return "OracleProxySession";
            case 10:
               return "UseDatabaseCredentials";
            case 11:
               return "ReplayInitiationTimeout";
            case 12:
               return "ActiveGridlink";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AffinityPolicy")) {
            return 8;
         } else if (propName.equals("ConnectionInitializationCallback")) {
            return 7;
         } else if (propName.equals("OnsNodeList")) {
            return 1;
         } else if (propName.equals("OnsWalletFile")) {
            return 2;
         } else if (propName.equals("OnsWalletPassword")) {
            return 4;
         } else if (propName.equals("OnsWalletPasswordEncrypted")) {
            return 3;
         } else if (propName.equals("ReplayInitiationTimeout")) {
            return 11;
         } else if (propName.equals("ActiveGridlink")) {
            return 12;
         } else if (propName.equals("FanEnabled")) {
            return 0;
         } else if (propName.equals("OracleEnableJavaNetFastPath")) {
            return 5;
         } else if (propName.equals("OracleOptimizeUtf8Conversion")) {
            return 6;
         } else if (propName.equals("OracleProxySession")) {
            return 9;
         } else {
            return propName.equals("UseDatabaseCredentials") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isAffinityPolicySet()) {
               buf.append("AffinityPolicy");
               buf.append(String.valueOf(this.bean.getAffinityPolicy()));
            }

            if (this.bean.isConnectionInitializationCallbackSet()) {
               buf.append("ConnectionInitializationCallback");
               buf.append(String.valueOf(this.bean.getConnectionInitializationCallback()));
            }

            if (this.bean.isOnsNodeListSet()) {
               buf.append("OnsNodeList");
               buf.append(String.valueOf(this.bean.getOnsNodeList()));
            }

            if (this.bean.isOnsWalletFileSet()) {
               buf.append("OnsWalletFile");
               buf.append(String.valueOf(this.bean.getOnsWalletFile()));
            }

            if (this.bean.isOnsWalletPasswordSet()) {
               buf.append("OnsWalletPassword");
               buf.append(String.valueOf(this.bean.getOnsWalletPassword()));
            }

            if (this.bean.isOnsWalletPasswordEncryptedSet()) {
               buf.append("OnsWalletPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getOnsWalletPasswordEncrypted())));
            }

            if (this.bean.isReplayInitiationTimeoutSet()) {
               buf.append("ReplayInitiationTimeout");
               buf.append(String.valueOf(this.bean.getReplayInitiationTimeout()));
            }

            if (this.bean.isActiveGridlinkSet()) {
               buf.append("ActiveGridlink");
               buf.append(String.valueOf(this.bean.isActiveGridlink()));
            }

            if (this.bean.isFanEnabledSet()) {
               buf.append("FanEnabled");
               buf.append(String.valueOf(this.bean.isFanEnabled()));
            }

            if (this.bean.isOracleEnableJavaNetFastPathSet()) {
               buf.append("OracleEnableJavaNetFastPath");
               buf.append(String.valueOf(this.bean.isOracleEnableJavaNetFastPath()));
            }

            if (this.bean.isOracleOptimizeUtf8ConversionSet()) {
               buf.append("OracleOptimizeUtf8Conversion");
               buf.append(String.valueOf(this.bean.isOracleOptimizeUtf8Conversion()));
            }

            if (this.bean.isOracleProxySessionSet()) {
               buf.append("OracleProxySession");
               buf.append(String.valueOf(this.bean.isOracleProxySession()));
            }

            if (this.bean.isUseDatabaseCredentialsSet()) {
               buf.append("UseDatabaseCredentials");
               buf.append(String.valueOf(this.bean.isUseDatabaseCredentials()));
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
            JDBCOracleParamsBeanImpl otherTyped = (JDBCOracleParamsBeanImpl)other;
            this.computeDiff("AffinityPolicy", this.bean.getAffinityPolicy(), otherTyped.getAffinityPolicy(), false);
            this.computeDiff("ConnectionInitializationCallback", this.bean.getConnectionInitializationCallback(), otherTyped.getConnectionInitializationCallback(), false);
            this.computeDiff("OnsNodeList", this.bean.getOnsNodeList(), otherTyped.getOnsNodeList(), false);
            this.computeDiff("OnsWalletFile", this.bean.getOnsWalletFile(), otherTyped.getOnsWalletFile(), false);
            this.computeDiff("OnsWalletPasswordEncrypted", this.bean.getOnsWalletPasswordEncrypted(), otherTyped.getOnsWalletPasswordEncrypted(), false);
            this.computeDiff("ReplayInitiationTimeout", this.bean.getReplayInitiationTimeout(), otherTyped.getReplayInitiationTimeout(), true);
            this.computeDiff("ActiveGridlink", this.bean.isActiveGridlink(), otherTyped.isActiveGridlink(), false);
            this.computeDiff("FanEnabled", this.bean.isFanEnabled(), otherTyped.isFanEnabled(), true);
            this.computeDiff("OracleEnableJavaNetFastPath", this.bean.isOracleEnableJavaNetFastPath(), otherTyped.isOracleEnableJavaNetFastPath(), true);
            this.computeDiff("OracleOptimizeUtf8Conversion", this.bean.isOracleOptimizeUtf8Conversion(), otherTyped.isOracleOptimizeUtf8Conversion(), true);
            this.computeDiff("OracleProxySession", this.bean.isOracleProxySession(), otherTyped.isOracleProxySession(), false);
            this.computeDiff("UseDatabaseCredentials", this.bean.isUseDatabaseCredentials(), otherTyped.isUseDatabaseCredentials(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JDBCOracleParamsBeanImpl original = (JDBCOracleParamsBeanImpl)event.getSourceBean();
            JDBCOracleParamsBeanImpl proposed = (JDBCOracleParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AffinityPolicy")) {
                  original.setAffinityPolicy(proposed.getAffinityPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("ConnectionInitializationCallback")) {
                  original.setConnectionInitializationCallback(proposed.getConnectionInitializationCallback());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("OnsNodeList")) {
                  original.setOnsNodeList(proposed.getOnsNodeList());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("OnsWalletFile")) {
                  original.setOnsWalletFile(proposed.getOnsWalletFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (!prop.equals("OnsWalletPassword")) {
                  if (prop.equals("OnsWalletPasswordEncrypted")) {
                     original.setOnsWalletPasswordEncrypted(proposed.getOnsWalletPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  } else if (prop.equals("ReplayInitiationTimeout")) {
                     original.setReplayInitiationTimeout(proposed.getReplayInitiationTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("ActiveGridlink")) {
                     original.setActiveGridlink(proposed.isActiveGridlink());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("FanEnabled")) {
                     original.setFanEnabled(proposed.isFanEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  } else if (prop.equals("OracleEnableJavaNetFastPath")) {
                     original.setOracleEnableJavaNetFastPath(proposed.isOracleEnableJavaNetFastPath());
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  } else if (prop.equals("OracleOptimizeUtf8Conversion")) {
                     original.setOracleOptimizeUtf8Conversion(proposed.isOracleOptimizeUtf8Conversion());
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  } else if (prop.equals("OracleProxySession")) {
                     original.setOracleProxySession(proposed.isOracleProxySession());
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  } else if (prop.equals("UseDatabaseCredentials")) {
                     original.setUseDatabaseCredentials(proposed.isUseDatabaseCredentials());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            JDBCOracleParamsBeanImpl copy = (JDBCOracleParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AffinityPolicy")) && this.bean.isAffinityPolicySet()) {
               copy.setAffinityPolicy(this.bean.getAffinityPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionInitializationCallback")) && this.bean.isConnectionInitializationCallbackSet()) {
               copy.setConnectionInitializationCallback(this.bean.getConnectionInitializationCallback());
            }

            if ((excludeProps == null || !excludeProps.contains("OnsNodeList")) && this.bean.isOnsNodeListSet()) {
               copy.setOnsNodeList(this.bean.getOnsNodeList());
            }

            if ((excludeProps == null || !excludeProps.contains("OnsWalletFile")) && this.bean.isOnsWalletFileSet()) {
               copy.setOnsWalletFile(this.bean.getOnsWalletFile());
            }

            if ((excludeProps == null || !excludeProps.contains("OnsWalletPasswordEncrypted")) && this.bean.isOnsWalletPasswordEncryptedSet()) {
               Object o = this.bean.getOnsWalletPasswordEncrypted();
               copy.setOnsWalletPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ReplayInitiationTimeout")) && this.bean.isReplayInitiationTimeoutSet()) {
               copy.setReplayInitiationTimeout(this.bean.getReplayInitiationTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("ActiveGridlink")) && this.bean.isActiveGridlinkSet()) {
               copy.setActiveGridlink(this.bean.isActiveGridlink());
            }

            if ((excludeProps == null || !excludeProps.contains("FanEnabled")) && this.bean.isFanEnabledSet()) {
               copy.setFanEnabled(this.bean.isFanEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("OracleEnableJavaNetFastPath")) && this.bean.isOracleEnableJavaNetFastPathSet()) {
               copy.setOracleEnableJavaNetFastPath(this.bean.isOracleEnableJavaNetFastPath());
            }

            if ((excludeProps == null || !excludeProps.contains("OracleOptimizeUtf8Conversion")) && this.bean.isOracleOptimizeUtf8ConversionSet()) {
               copy.setOracleOptimizeUtf8Conversion(this.bean.isOracleOptimizeUtf8Conversion());
            }

            if ((excludeProps == null || !excludeProps.contains("OracleProxySession")) && this.bean.isOracleProxySessionSet()) {
               copy.setOracleProxySession(this.bean.isOracleProxySession());
            }

            if ((excludeProps == null || !excludeProps.contains("UseDatabaseCredentials")) && this.bean.isUseDatabaseCredentialsSet()) {
               copy.setUseDatabaseCredentials(this.bean.isUseDatabaseCredentials());
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
