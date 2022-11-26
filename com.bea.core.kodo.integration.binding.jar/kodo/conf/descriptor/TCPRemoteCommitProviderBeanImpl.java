package kodo.conf.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class TCPRemoteCommitProviderBeanImpl extends RemoteCommitProviderBeanImpl implements TCPRemoteCommitProviderBean, Serializable {
   private String _Addresses;
   private int _MaxActive;
   private int _MaxIdle;
   private int _NumBroadcastThreads;
   private int _Port;
   private int _RecoveryTimeMillis;
   private static SchemaHelper2 _schemaHelper;

   public TCPRemoteCommitProviderBeanImpl() {
      this._initializeProperty(-1);
   }

   public TCPRemoteCommitProviderBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TCPRemoteCommitProviderBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getMaxIdle() {
      return this._MaxIdle;
   }

   public boolean isMaxIdleInherited() {
      return false;
   }

   public boolean isMaxIdleSet() {
      return this._isSet(1);
   }

   public void setMaxIdle(int param0) {
      int _oldVal = this._MaxIdle;
      this._MaxIdle = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getNumBroadcastThreads() {
      return this._NumBroadcastThreads;
   }

   public boolean isNumBroadcastThreadsInherited() {
      return false;
   }

   public boolean isNumBroadcastThreadsSet() {
      return this._isSet(2);
   }

   public void setNumBroadcastThreads(int param0) {
      int _oldVal = this._NumBroadcastThreads;
      this._NumBroadcastThreads = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getRecoveryTimeMillis() {
      return this._RecoveryTimeMillis;
   }

   public boolean isRecoveryTimeMillisInherited() {
      return false;
   }

   public boolean isRecoveryTimeMillisSet() {
      return this._isSet(3);
   }

   public void setRecoveryTimeMillis(int param0) {
      int _oldVal = this._RecoveryTimeMillis;
      this._RecoveryTimeMillis = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getMaxActive() {
      return this._MaxActive;
   }

   public boolean isMaxActiveInherited() {
      return false;
   }

   public boolean isMaxActiveSet() {
      return this._isSet(4);
   }

   public void setMaxActive(int param0) {
      int _oldVal = this._MaxActive;
      this._MaxActive = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getPort() {
      return this._Port;
   }

   public boolean isPortInherited() {
      return false;
   }

   public boolean isPortSet() {
      return this._isSet(5);
   }

   public void setPort(int param0) {
      int _oldVal = this._Port;
      this._Port = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getAddresses() {
      return this._Addresses;
   }

   public boolean isAddressesInherited() {
      return false;
   }

   public boolean isAddressesSet() {
      return this._isSet(6);
   }

   public void setAddresses(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Addresses;
      this._Addresses = param0;
      this._postSet(6, _oldVal, param0);
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
               this._Addresses = "[]";
               if (initOne) {
                  break;
               }
            case 4:
               this._MaxActive = 2;
               if (initOne) {
                  break;
               }
            case 1:
               this._MaxIdle = 2;
               if (initOne) {
                  break;
               }
            case 2:
               this._NumBroadcastThreads = 2;
               if (initOne) {
                  break;
               }
            case 5:
               this._Port = 5636;
               if (initOne) {
                  break;
               }
            case 3:
               this._RecoveryTimeMillis = 15000;
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

   public static class SchemaHelper2 extends RemoteCommitProviderBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("port")) {
                  return 5;
               }
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            default:
               break;
            case 8:
               if (s.equals("max-idle")) {
                  return 1;
               }
               break;
            case 9:
               if (s.equals("addresses")) {
                  return 6;
               }
               break;
            case 10:
               if (s.equals("max-active")) {
                  return 4;
               }
               break;
            case 20:
               if (s.equals("recovery-time-millis")) {
                  return 3;
               }
               break;
            case 21:
               if (s.equals("num-broadcast-threads")) {
                  return 2;
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
            case 1:
               return "max-idle";
            case 2:
               return "num-broadcast-threads";
            case 3:
               return "recovery-time-millis";
            case 4:
               return "max-active";
            case 5:
               return "port";
            case 6:
               return "addresses";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends RemoteCommitProviderBeanImpl.Helper {
      private TCPRemoteCommitProviderBeanImpl bean;

      protected Helper(TCPRemoteCommitProviderBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "MaxIdle";
            case 2:
               return "NumBroadcastThreads";
            case 3:
               return "RecoveryTimeMillis";
            case 4:
               return "MaxActive";
            case 5:
               return "Port";
            case 6:
               return "Addresses";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Addresses")) {
            return 6;
         } else if (propName.equals("MaxActive")) {
            return 4;
         } else if (propName.equals("MaxIdle")) {
            return 1;
         } else if (propName.equals("NumBroadcastThreads")) {
            return 2;
         } else if (propName.equals("Port")) {
            return 5;
         } else {
            return propName.equals("RecoveryTimeMillis") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isAddressesSet()) {
               buf.append("Addresses");
               buf.append(String.valueOf(this.bean.getAddresses()));
            }

            if (this.bean.isMaxActiveSet()) {
               buf.append("MaxActive");
               buf.append(String.valueOf(this.bean.getMaxActive()));
            }

            if (this.bean.isMaxIdleSet()) {
               buf.append("MaxIdle");
               buf.append(String.valueOf(this.bean.getMaxIdle()));
            }

            if (this.bean.isNumBroadcastThreadsSet()) {
               buf.append("NumBroadcastThreads");
               buf.append(String.valueOf(this.bean.getNumBroadcastThreads()));
            }

            if (this.bean.isPortSet()) {
               buf.append("Port");
               buf.append(String.valueOf(this.bean.getPort()));
            }

            if (this.bean.isRecoveryTimeMillisSet()) {
               buf.append("RecoveryTimeMillis");
               buf.append(String.valueOf(this.bean.getRecoveryTimeMillis()));
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
            TCPRemoteCommitProviderBeanImpl otherTyped = (TCPRemoteCommitProviderBeanImpl)other;
            this.computeDiff("Addresses", this.bean.getAddresses(), otherTyped.getAddresses(), false);
            this.computeDiff("MaxActive", this.bean.getMaxActive(), otherTyped.getMaxActive(), false);
            this.computeDiff("MaxIdle", this.bean.getMaxIdle(), otherTyped.getMaxIdle(), false);
            this.computeDiff("NumBroadcastThreads", this.bean.getNumBroadcastThreads(), otherTyped.getNumBroadcastThreads(), false);
            this.computeDiff("Port", this.bean.getPort(), otherTyped.getPort(), false);
            this.computeDiff("RecoveryTimeMillis", this.bean.getRecoveryTimeMillis(), otherTyped.getRecoveryTimeMillis(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TCPRemoteCommitProviderBeanImpl original = (TCPRemoteCommitProviderBeanImpl)event.getSourceBean();
            TCPRemoteCommitProviderBeanImpl proposed = (TCPRemoteCommitProviderBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Addresses")) {
                  original.setAddresses(proposed.getAddresses());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("MaxActive")) {
                  original.setMaxActive(proposed.getMaxActive());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("MaxIdle")) {
                  original.setMaxIdle(proposed.getMaxIdle());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("NumBroadcastThreads")) {
                  original.setNumBroadcastThreads(proposed.getNumBroadcastThreads());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Port")) {
                  original.setPort(proposed.getPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("RecoveryTimeMillis")) {
                  original.setRecoveryTimeMillis(proposed.getRecoveryTimeMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            TCPRemoteCommitProviderBeanImpl copy = (TCPRemoteCommitProviderBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Addresses")) && this.bean.isAddressesSet()) {
               copy.setAddresses(this.bean.getAddresses());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxActive")) && this.bean.isMaxActiveSet()) {
               copy.setMaxActive(this.bean.getMaxActive());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxIdle")) && this.bean.isMaxIdleSet()) {
               copy.setMaxIdle(this.bean.getMaxIdle());
            }

            if ((excludeProps == null || !excludeProps.contains("NumBroadcastThreads")) && this.bean.isNumBroadcastThreadsSet()) {
               copy.setNumBroadcastThreads(this.bean.getNumBroadcastThreads());
            }

            if ((excludeProps == null || !excludeProps.contains("Port")) && this.bean.isPortSet()) {
               copy.setPort(this.bean.getPort());
            }

            if ((excludeProps == null || !excludeProps.contains("RecoveryTimeMillis")) && this.bean.isRecoveryTimeMillisSet()) {
               copy.setRecoveryTimeMillis(this.bean.getRecoveryTimeMillis());
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
