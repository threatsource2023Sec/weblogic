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
import weblogic.jdbc.common.internal.JDBCConstants;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JDBCDataSourceParamsBeanImpl extends SettableBeanImpl implements JDBCDataSourceParamsBean, Serializable {
   private String _AlgorithmType;
   private String _ConnectionPoolFailoverCallbackHandler;
   private String _DataSourceList;
   private boolean _FailoverRequestIfBusy;
   private String _GlobalTransactionsProtocol;
   private String[] _JNDINames;
   private boolean _KeepConnAfterGlobalTx;
   private boolean _KeepConnAfterLocalTx;
   private String _ProxySwitchingCallback;
   private String _ProxySwitchingProperties;
   private boolean _RowPrefetch;
   private int _RowPrefetchSize;
   private String _Scope;
   private int _StreamChunkSize;
   private static SchemaHelper2 _schemaHelper;

   public JDBCDataSourceParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public JDBCDataSourceParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JDBCDataSourceParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getJNDINames() {
      if (!this._isSet(0)) {
         try {
            return JDBCConstants.getDefaultJNDINames((JDBCDataSourceBean)this.getParentBean());
         } catch (NullPointerException var2) {
         }
      }

      return this._JNDINames;
   }

   public boolean isJNDINamesInherited() {
      return false;
   }

   public boolean isJNDINamesSet() {
      return this._isSet(0);
   }

   public void setJNDINames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._JNDINames;
      this._JNDINames = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addJNDIName(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(0)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getJNDINames(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setJNDINames(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeJNDIName(String param0) {
      String[] _old = this.getJNDINames();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setJNDINames(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public String getScope() {
      return this._Scope;
   }

   public boolean isScopeInherited() {
      return false;
   }

   public boolean isScopeSet() {
      return this._isSet(1);
   }

   public void setScope(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Global", "Application"};
      param0 = LegalChecks.checkInEnum("Scope", param0, _set);
      String _oldVal = this._Scope;
      this._Scope = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean isRowPrefetch() {
      return this._RowPrefetch;
   }

   public boolean isRowPrefetchInherited() {
      return false;
   }

   public boolean isRowPrefetchSet() {
      return this._isSet(2);
   }

   public void setRowPrefetch(boolean param0) {
      boolean _oldVal = this._RowPrefetch;
      this._RowPrefetch = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getRowPrefetchSize() {
      return this._RowPrefetchSize;
   }

   public boolean isRowPrefetchSizeInherited() {
      return false;
   }

   public boolean isRowPrefetchSizeSet() {
      return this._isSet(3);
   }

   public void setRowPrefetchSize(int param0) {
      LegalChecks.checkInRange("RowPrefetchSize", (long)param0, 2L, 65536L);
      int _oldVal = this._RowPrefetchSize;
      this._RowPrefetchSize = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getStreamChunkSize() {
      return this._StreamChunkSize;
   }

   public boolean isStreamChunkSizeInherited() {
      return false;
   }

   public boolean isStreamChunkSizeSet() {
      return this._isSet(4);
   }

   public void setStreamChunkSize(int param0) {
      LegalChecks.checkInRange("StreamChunkSize", (long)param0, 1L, 65536L);
      int _oldVal = this._StreamChunkSize;
      this._StreamChunkSize = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getAlgorithmType() {
      return this._AlgorithmType;
   }

   public boolean isAlgorithmTypeInherited() {
      return false;
   }

   public boolean isAlgorithmTypeSet() {
      return this._isSet(5);
   }

   public void setAlgorithmType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Load-Balancing", "Failover"};
      param0 = LegalChecks.checkInEnum("AlgorithmType", param0, _set);
      String _oldVal = this._AlgorithmType;
      this._AlgorithmType = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getDataSourceList() {
      return this._DataSourceList;
   }

   public boolean isDataSourceListInherited() {
      return false;
   }

   public boolean isDataSourceListSet() {
      return this._isSet(6);
   }

   public void setDataSourceList(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DataSourceList;
      this._DataSourceList = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getConnectionPoolFailoverCallbackHandler() {
      return this._ConnectionPoolFailoverCallbackHandler;
   }

   public boolean isConnectionPoolFailoverCallbackHandlerInherited() {
      return false;
   }

   public boolean isConnectionPoolFailoverCallbackHandlerSet() {
      return this._isSet(7);
   }

   public void setConnectionPoolFailoverCallbackHandler(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionPoolFailoverCallbackHandler;
      this._ConnectionPoolFailoverCallbackHandler = param0;
      this._postSet(7, _oldVal, param0);
   }

   public boolean isFailoverRequestIfBusy() {
      return this._FailoverRequestIfBusy;
   }

   public boolean isFailoverRequestIfBusyInherited() {
      return false;
   }

   public boolean isFailoverRequestIfBusySet() {
      return this._isSet(8);
   }

   public void setFailoverRequestIfBusy(boolean param0) {
      boolean _oldVal = this._FailoverRequestIfBusy;
      this._FailoverRequestIfBusy = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getGlobalTransactionsProtocol() {
      return this._GlobalTransactionsProtocol;
   }

   public boolean isGlobalTransactionsProtocolInherited() {
      return false;
   }

   public boolean isGlobalTransactionsProtocolSet() {
      return this._isSet(9);
   }

   public void setGlobalTransactionsProtocol(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"TwoPhaseCommit", "LoggingLastResource", "EmulateTwoPhaseCommit", "OnePhaseCommit", "None"};
      param0 = LegalChecks.checkInEnum("GlobalTransactionsProtocol", param0, _set);
      String _oldVal = this._GlobalTransactionsProtocol;
      this._GlobalTransactionsProtocol = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean isKeepConnAfterLocalTx() {
      return this._KeepConnAfterLocalTx;
   }

   public boolean isKeepConnAfterLocalTxInherited() {
      return false;
   }

   public boolean isKeepConnAfterLocalTxSet() {
      return this._isSet(10);
   }

   public void setKeepConnAfterLocalTx(boolean param0) {
      boolean _oldVal = this._KeepConnAfterLocalTx;
      this._KeepConnAfterLocalTx = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isKeepConnAfterGlobalTx() {
      return this._KeepConnAfterGlobalTx;
   }

   public boolean isKeepConnAfterGlobalTxInherited() {
      return false;
   }

   public boolean isKeepConnAfterGlobalTxSet() {
      return this._isSet(11);
   }

   public void setKeepConnAfterGlobalTx(boolean param0) {
      boolean _oldVal = this._KeepConnAfterGlobalTx;
      this._KeepConnAfterGlobalTx = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getProxySwitchingCallback() {
      return this._ProxySwitchingCallback;
   }

   public boolean isProxySwitchingCallbackInherited() {
      return false;
   }

   public boolean isProxySwitchingCallbackSet() {
      return this._isSet(12);
   }

   public void setProxySwitchingCallback(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ProxySwitchingCallback;
      this._ProxySwitchingCallback = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getProxySwitchingProperties() {
      return this._ProxySwitchingProperties;
   }

   public boolean isProxySwitchingPropertiesInherited() {
      return false;
   }

   public boolean isProxySwitchingPropertiesSet() {
      return this._isSet(13);
   }

   public void setProxySwitchingProperties(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ProxySwitchingProperties;
      this._ProxySwitchingProperties = param0;
      this._postSet(13, _oldVal, param0);
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
         idx = 5;
      }

      try {
         switch (idx) {
            case 5:
               this._AlgorithmType = "Failover";
               if (initOne) {
                  break;
               }
            case 7:
               this._ConnectionPoolFailoverCallbackHandler = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._DataSourceList = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._GlobalTransactionsProtocol = "OnePhaseCommit";
               if (initOne) {
                  break;
               }
            case 0:
               this._JNDINames = new String[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._ProxySwitchingCallback = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._ProxySwitchingProperties = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._RowPrefetchSize = 48;
               if (initOne) {
                  break;
               }
            case 1:
               this._Scope = "Global";
               if (initOne) {
                  break;
               }
            case 4:
               this._StreamChunkSize = 256;
               if (initOne) {
                  break;
               }
            case 8:
               this._FailoverRequestIfBusy = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._KeepConnAfterGlobalTx = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._KeepConnAfterLocalTx = true;
               if (initOne) {
                  break;
               }
            case 2:
               this._RowPrefetch = false;
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
            case 5:
               if (s.equals("scope")) {
                  return 1;
               }
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 27:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            default:
               break;
            case 9:
               if (s.equals("jndi-name")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("row-prefetch")) {
                  return 2;
               }
               break;
            case 14:
               if (s.equals("algorithm-type")) {
                  return 5;
               }
               break;
            case 16:
               if (s.equals("data-source-list")) {
                  return 6;
               }
               break;
            case 17:
               if (s.equals("row-prefetch-size")) {
                  return 3;
               }

               if (s.equals("stream-chunk-size")) {
                  return 4;
               }
               break;
            case 24:
               if (s.equals("proxy-switching-callback")) {
                  return 12;
               }

               if (s.equals("failover-request-if-busy")) {
                  return 8;
               }

               if (s.equals("keep-conn-after-local-tx")) {
                  return 10;
               }
               break;
            case 25:
               if (s.equals("keep-conn-after-global-tx")) {
                  return 11;
               }
               break;
            case 26:
               if (s.equals("proxy-switching-properties")) {
                  return 13;
               }
               break;
            case 28:
               if (s.equals("global-transactions-protocol")) {
                  return 9;
               }
               break;
            case 41:
               if (s.equals("connection-pool-failover-callback-handler")) {
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
               return "jndi-name";
            case 1:
               return "scope";
            case 2:
               return "row-prefetch";
            case 3:
               return "row-prefetch-size";
            case 4:
               return "stream-chunk-size";
            case 5:
               return "algorithm-type";
            case 6:
               return "data-source-list";
            case 7:
               return "connection-pool-failover-callback-handler";
            case 8:
               return "failover-request-if-busy";
            case 9:
               return "global-transactions-protocol";
            case 10:
               return "keep-conn-after-local-tx";
            case 11:
               return "keep-conn-after-global-tx";
            case 12:
               return "proxy-switching-callback";
            case 13:
               return "proxy-switching-properties";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
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
            case 13:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private JDBCDataSourceParamsBeanImpl bean;

      protected Helper(JDBCDataSourceParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "JNDINames";
            case 1:
               return "Scope";
            case 2:
               return "RowPrefetch";
            case 3:
               return "RowPrefetchSize";
            case 4:
               return "StreamChunkSize";
            case 5:
               return "AlgorithmType";
            case 6:
               return "DataSourceList";
            case 7:
               return "ConnectionPoolFailoverCallbackHandler";
            case 8:
               return "FailoverRequestIfBusy";
            case 9:
               return "GlobalTransactionsProtocol";
            case 10:
               return "KeepConnAfterLocalTx";
            case 11:
               return "KeepConnAfterGlobalTx";
            case 12:
               return "ProxySwitchingCallback";
            case 13:
               return "ProxySwitchingProperties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AlgorithmType")) {
            return 5;
         } else if (propName.equals("ConnectionPoolFailoverCallbackHandler")) {
            return 7;
         } else if (propName.equals("DataSourceList")) {
            return 6;
         } else if (propName.equals("GlobalTransactionsProtocol")) {
            return 9;
         } else if (propName.equals("JNDINames")) {
            return 0;
         } else if (propName.equals("ProxySwitchingCallback")) {
            return 12;
         } else if (propName.equals("ProxySwitchingProperties")) {
            return 13;
         } else if (propName.equals("RowPrefetchSize")) {
            return 3;
         } else if (propName.equals("Scope")) {
            return 1;
         } else if (propName.equals("StreamChunkSize")) {
            return 4;
         } else if (propName.equals("FailoverRequestIfBusy")) {
            return 8;
         } else if (propName.equals("KeepConnAfterGlobalTx")) {
            return 11;
         } else if (propName.equals("KeepConnAfterLocalTx")) {
            return 10;
         } else {
            return propName.equals("RowPrefetch") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isAlgorithmTypeSet()) {
               buf.append("AlgorithmType");
               buf.append(String.valueOf(this.bean.getAlgorithmType()));
            }

            if (this.bean.isConnectionPoolFailoverCallbackHandlerSet()) {
               buf.append("ConnectionPoolFailoverCallbackHandler");
               buf.append(String.valueOf(this.bean.getConnectionPoolFailoverCallbackHandler()));
            }

            if (this.bean.isDataSourceListSet()) {
               buf.append("DataSourceList");
               buf.append(String.valueOf(this.bean.getDataSourceList()));
            }

            if (this.bean.isGlobalTransactionsProtocolSet()) {
               buf.append("GlobalTransactionsProtocol");
               buf.append(String.valueOf(this.bean.getGlobalTransactionsProtocol()));
            }

            if (this.bean.isJNDINamesSet()) {
               buf.append("JNDINames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getJNDINames())));
            }

            if (this.bean.isProxySwitchingCallbackSet()) {
               buf.append("ProxySwitchingCallback");
               buf.append(String.valueOf(this.bean.getProxySwitchingCallback()));
            }

            if (this.bean.isProxySwitchingPropertiesSet()) {
               buf.append("ProxySwitchingProperties");
               buf.append(String.valueOf(this.bean.getProxySwitchingProperties()));
            }

            if (this.bean.isRowPrefetchSizeSet()) {
               buf.append("RowPrefetchSize");
               buf.append(String.valueOf(this.bean.getRowPrefetchSize()));
            }

            if (this.bean.isScopeSet()) {
               buf.append("Scope");
               buf.append(String.valueOf(this.bean.getScope()));
            }

            if (this.bean.isStreamChunkSizeSet()) {
               buf.append("StreamChunkSize");
               buf.append(String.valueOf(this.bean.getStreamChunkSize()));
            }

            if (this.bean.isFailoverRequestIfBusySet()) {
               buf.append("FailoverRequestIfBusy");
               buf.append(String.valueOf(this.bean.isFailoverRequestIfBusy()));
            }

            if (this.bean.isKeepConnAfterGlobalTxSet()) {
               buf.append("KeepConnAfterGlobalTx");
               buf.append(String.valueOf(this.bean.isKeepConnAfterGlobalTx()));
            }

            if (this.bean.isKeepConnAfterLocalTxSet()) {
               buf.append("KeepConnAfterLocalTx");
               buf.append(String.valueOf(this.bean.isKeepConnAfterLocalTx()));
            }

            if (this.bean.isRowPrefetchSet()) {
               buf.append("RowPrefetch");
               buf.append(String.valueOf(this.bean.isRowPrefetch()));
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
            JDBCDataSourceParamsBeanImpl otherTyped = (JDBCDataSourceParamsBeanImpl)other;
            this.computeDiff("AlgorithmType", this.bean.getAlgorithmType(), otherTyped.getAlgorithmType(), false);
            this.computeDiff("ConnectionPoolFailoverCallbackHandler", this.bean.getConnectionPoolFailoverCallbackHandler(), otherTyped.getConnectionPoolFailoverCallbackHandler(), false);
            this.computeDiff("DataSourceList", this.bean.getDataSourceList(), otherTyped.getDataSourceList(), true);
            this.computeDiff("GlobalTransactionsProtocol", this.bean.getGlobalTransactionsProtocol(), otherTyped.getGlobalTransactionsProtocol(), false);
            this.computeDiff("JNDINames", this.bean.getJNDINames(), otherTyped.getJNDINames(), false);
            this.computeDiff("ProxySwitchingCallback", this.bean.getProxySwitchingCallback(), otherTyped.getProxySwitchingCallback(), false);
            this.computeDiff("ProxySwitchingProperties", this.bean.getProxySwitchingProperties(), otherTyped.getProxySwitchingProperties(), true);
            this.computeDiff("RowPrefetchSize", this.bean.getRowPrefetchSize(), otherTyped.getRowPrefetchSize(), false);
            this.computeDiff("Scope", this.bean.getScope(), otherTyped.getScope(), false);
            this.computeDiff("StreamChunkSize", this.bean.getStreamChunkSize(), otherTyped.getStreamChunkSize(), false);
            this.computeDiff("FailoverRequestIfBusy", this.bean.isFailoverRequestIfBusy(), otherTyped.isFailoverRequestIfBusy(), false);
            this.computeDiff("KeepConnAfterGlobalTx", this.bean.isKeepConnAfterGlobalTx(), otherTyped.isKeepConnAfterGlobalTx(), false);
            this.computeDiff("KeepConnAfterLocalTx", this.bean.isKeepConnAfterLocalTx(), otherTyped.isKeepConnAfterLocalTx(), false);
            this.computeDiff("RowPrefetch", this.bean.isRowPrefetch(), otherTyped.isRowPrefetch(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JDBCDataSourceParamsBeanImpl original = (JDBCDataSourceParamsBeanImpl)event.getSourceBean();
            JDBCDataSourceParamsBeanImpl proposed = (JDBCDataSourceParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AlgorithmType")) {
                  original.setAlgorithmType(proposed.getAlgorithmType());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("ConnectionPoolFailoverCallbackHandler")) {
                  original.setConnectionPoolFailoverCallbackHandler(proposed.getConnectionPoolFailoverCallbackHandler());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("DataSourceList")) {
                  original.setDataSourceList(proposed.getDataSourceList());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("GlobalTransactionsProtocol")) {
                  original.setGlobalTransactionsProtocol(proposed.getGlobalTransactionsProtocol());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("JNDINames")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addJNDIName((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJNDIName((String)update.getRemovedObject());
                  }

                  if (original.getJNDINames() == null || original.getJNDINames().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("ProxySwitchingCallback")) {
                  original.setProxySwitchingCallback(proposed.getProxySwitchingCallback());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ProxySwitchingProperties")) {
                  original.setProxySwitchingProperties(proposed.getProxySwitchingProperties());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("RowPrefetchSize")) {
                  original.setRowPrefetchSize(proposed.getRowPrefetchSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Scope")) {
                  original.setScope(proposed.getScope());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("StreamChunkSize")) {
                  original.setStreamChunkSize(proposed.getStreamChunkSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("FailoverRequestIfBusy")) {
                  original.setFailoverRequestIfBusy(proposed.isFailoverRequestIfBusy());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("KeepConnAfterGlobalTx")) {
                  original.setKeepConnAfterGlobalTx(proposed.isKeepConnAfterGlobalTx());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("KeepConnAfterLocalTx")) {
                  original.setKeepConnAfterLocalTx(proposed.isKeepConnAfterLocalTx());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("RowPrefetch")) {
                  original.setRowPrefetch(proposed.isRowPrefetch());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            JDBCDataSourceParamsBeanImpl copy = (JDBCDataSourceParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AlgorithmType")) && this.bean.isAlgorithmTypeSet()) {
               copy.setAlgorithmType(this.bean.getAlgorithmType());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionPoolFailoverCallbackHandler")) && this.bean.isConnectionPoolFailoverCallbackHandlerSet()) {
               copy.setConnectionPoolFailoverCallbackHandler(this.bean.getConnectionPoolFailoverCallbackHandler());
            }

            if ((excludeProps == null || !excludeProps.contains("DataSourceList")) && this.bean.isDataSourceListSet()) {
               copy.setDataSourceList(this.bean.getDataSourceList());
            }

            if ((excludeProps == null || !excludeProps.contains("GlobalTransactionsProtocol")) && this.bean.isGlobalTransactionsProtocolSet()) {
               copy.setGlobalTransactionsProtocol(this.bean.getGlobalTransactionsProtocol());
            }

            if ((excludeProps == null || !excludeProps.contains("JNDINames")) && this.bean.isJNDINamesSet()) {
               Object o = this.bean.getJNDINames();
               copy.setJNDINames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ProxySwitchingCallback")) && this.bean.isProxySwitchingCallbackSet()) {
               copy.setProxySwitchingCallback(this.bean.getProxySwitchingCallback());
            }

            if ((excludeProps == null || !excludeProps.contains("ProxySwitchingProperties")) && this.bean.isProxySwitchingPropertiesSet()) {
               copy.setProxySwitchingProperties(this.bean.getProxySwitchingProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("RowPrefetchSize")) && this.bean.isRowPrefetchSizeSet()) {
               copy.setRowPrefetchSize(this.bean.getRowPrefetchSize());
            }

            if ((excludeProps == null || !excludeProps.contains("Scope")) && this.bean.isScopeSet()) {
               copy.setScope(this.bean.getScope());
            }

            if ((excludeProps == null || !excludeProps.contains("StreamChunkSize")) && this.bean.isStreamChunkSizeSet()) {
               copy.setStreamChunkSize(this.bean.getStreamChunkSize());
            }

            if ((excludeProps == null || !excludeProps.contains("FailoverRequestIfBusy")) && this.bean.isFailoverRequestIfBusySet()) {
               copy.setFailoverRequestIfBusy(this.bean.isFailoverRequestIfBusy());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepConnAfterGlobalTx")) && this.bean.isKeepConnAfterGlobalTxSet()) {
               copy.setKeepConnAfterGlobalTx(this.bean.isKeepConnAfterGlobalTx());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepConnAfterLocalTx")) && this.bean.isKeepConnAfterLocalTxSet()) {
               copy.setKeepConnAfterLocalTx(this.bean.isKeepConnAfterLocalTx());
            }

            if ((excludeProps == null || !excludeProps.contains("RowPrefetch")) && this.bean.isRowPrefetchSet()) {
               copy.setRowPrefetch(this.bean.isRowPrefetch());
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
