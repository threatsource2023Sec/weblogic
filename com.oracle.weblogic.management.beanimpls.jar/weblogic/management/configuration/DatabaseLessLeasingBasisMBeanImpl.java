package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class DatabaseLessLeasingBasisMBeanImpl extends ConfigurationMBeanImpl implements DatabaseLessLeasingBasisMBean, Serializable {
   private int _FenceTimeout;
   private int _LeaderHeartbeatPeriod;
   private int _MemberDiscoveryTimeout;
   private int _MessageDeliveryTimeout;
   private int _NodeManagerTimeoutMillis;
   private boolean _PeriodicSRMCheckEnabled;
   private static SchemaHelper2 _schemaHelper;

   public DatabaseLessLeasingBasisMBeanImpl() {
      this._initializeProperty(-1);
   }

   public DatabaseLessLeasingBasisMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DatabaseLessLeasingBasisMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setMemberDiscoveryTimeout(int param0) {
      LegalChecks.checkMin("MemberDiscoveryTimeout", param0, 10);
      int _oldVal = this._MemberDiscoveryTimeout;
      this._MemberDiscoveryTimeout = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getMemberDiscoveryTimeout() {
      return this._MemberDiscoveryTimeout;
   }

   public boolean isMemberDiscoveryTimeoutInherited() {
      return false;
   }

   public boolean isMemberDiscoveryTimeoutSet() {
      return this._isSet(10);
   }

   public void setLeaderHeartbeatPeriod(int param0) {
      LegalChecks.checkMin("LeaderHeartbeatPeriod", param0, 1);
      int _oldVal = this._LeaderHeartbeatPeriod;
      this._LeaderHeartbeatPeriod = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getLeaderHeartbeatPeriod() {
      return this._LeaderHeartbeatPeriod;
   }

   public boolean isLeaderHeartbeatPeriodInherited() {
      return false;
   }

   public boolean isLeaderHeartbeatPeriodSet() {
      return this._isSet(11);
   }

   public void setMessageDeliveryTimeout(int param0) {
      int _oldVal = this._MessageDeliveryTimeout;
      this._MessageDeliveryTimeout = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getMessageDeliveryTimeout() {
      return this._MessageDeliveryTimeout;
   }

   public boolean isMessageDeliveryTimeoutInherited() {
      return false;
   }

   public boolean isMessageDeliveryTimeoutSet() {
      return this._isSet(12);
   }

   public void setFenceTimeout(int param0) {
      int _oldVal = this._FenceTimeout;
      this._FenceTimeout = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getFenceTimeout() {
      return this._FenceTimeout;
   }

   public boolean isFenceTimeoutInherited() {
      return false;
   }

   public boolean isFenceTimeoutSet() {
      return this._isSet(13);
   }

   public boolean isPeriodicSRMCheckEnabled() {
      return this._PeriodicSRMCheckEnabled;
   }

   public boolean isPeriodicSRMCheckEnabledInherited() {
      return false;
   }

   public boolean isPeriodicSRMCheckEnabledSet() {
      return this._isSet(14);
   }

   public void setPeriodicSRMCheckEnabled(boolean param0) {
      boolean _oldVal = this._PeriodicSRMCheckEnabled;
      this._PeriodicSRMCheckEnabled = param0;
      this._postSet(14, _oldVal, param0);
   }

   public void setNodeManagerTimeoutMillis(int param0) {
      LegalChecks.checkMin("NodeManagerTimeoutMillis", param0, 0);
      int _oldVal = this._NodeManagerTimeoutMillis;
      this._NodeManagerTimeoutMillis = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getNodeManagerTimeoutMillis() {
      return this._NodeManagerTimeoutMillis;
   }

   public boolean isNodeManagerTimeoutMillisInherited() {
      return false;
   }

   public boolean isNodeManagerTimeoutMillisSet() {
      return this._isSet(15);
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
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._FenceTimeout = 5;
               if (initOne) {
                  break;
               }
            case 11:
               this._LeaderHeartbeatPeriod = 10;
               if (initOne) {
                  break;
               }
            case 10:
               this._MemberDiscoveryTimeout = 30;
               if (initOne) {
                  break;
               }
            case 12:
               this._MessageDeliveryTimeout = 5000;
               if (initOne) {
                  break;
               }
            case 15:
               this._NodeManagerTimeoutMillis = 180000;
               if (initOne) {
                  break;
               }
            case 14:
               this._PeriodicSRMCheckEnabled = true;
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
      return "DatabaseLessLeasingBasis";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("FenceTimeout")) {
         oldVal = this._FenceTimeout;
         this._FenceTimeout = (Integer)v;
         this._postSet(13, oldVal, this._FenceTimeout);
      } else if (name.equals("LeaderHeartbeatPeriod")) {
         oldVal = this._LeaderHeartbeatPeriod;
         this._LeaderHeartbeatPeriod = (Integer)v;
         this._postSet(11, oldVal, this._LeaderHeartbeatPeriod);
      } else if (name.equals("MemberDiscoveryTimeout")) {
         oldVal = this._MemberDiscoveryTimeout;
         this._MemberDiscoveryTimeout = (Integer)v;
         this._postSet(10, oldVal, this._MemberDiscoveryTimeout);
      } else if (name.equals("MessageDeliveryTimeout")) {
         oldVal = this._MessageDeliveryTimeout;
         this._MessageDeliveryTimeout = (Integer)v;
         this._postSet(12, oldVal, this._MessageDeliveryTimeout);
      } else if (name.equals("NodeManagerTimeoutMillis")) {
         oldVal = this._NodeManagerTimeoutMillis;
         this._NodeManagerTimeoutMillis = (Integer)v;
         this._postSet(15, oldVal, this._NodeManagerTimeoutMillis);
      } else if (name.equals("PeriodicSRMCheckEnabled")) {
         boolean oldVal = this._PeriodicSRMCheckEnabled;
         this._PeriodicSRMCheckEnabled = (Boolean)v;
         this._postSet(14, oldVal, this._PeriodicSRMCheckEnabled);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("FenceTimeout")) {
         return new Integer(this._FenceTimeout);
      } else if (name.equals("LeaderHeartbeatPeriod")) {
         return new Integer(this._LeaderHeartbeatPeriod);
      } else if (name.equals("MemberDiscoveryTimeout")) {
         return new Integer(this._MemberDiscoveryTimeout);
      } else if (name.equals("MessageDeliveryTimeout")) {
         return new Integer(this._MessageDeliveryTimeout);
      } else if (name.equals("NodeManagerTimeoutMillis")) {
         return new Integer(this._NodeManagerTimeoutMillis);
      } else {
         return name.equals("PeriodicSRMCheckEnabled") ? new Boolean(this._PeriodicSRMCheckEnabled) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("fence-timeout")) {
                  return 13;
               }
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 26:
            default:
               break;
            case 23:
               if (s.equals("leader-heartbeat-period")) {
                  return 11;
               }
               break;
            case 24:
               if (s.equals("member-discovery-timeout")) {
                  return 10;
               }

               if (s.equals("message-delivery-timeout")) {
                  return 12;
               }
               break;
            case 25:
               if (s.equals("periodicsrm-check-enabled")) {
                  return 14;
               }
               break;
            case 27:
               if (s.equals("node-manager-timeout-millis")) {
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
            case 10:
               return "member-discovery-timeout";
            case 11:
               return "leader-heartbeat-period";
            case 12:
               return "message-delivery-timeout";
            case 13:
               return "fence-timeout";
            case 14:
               return "periodicsrm-check-enabled";
            case 15:
               return "node-manager-timeout-millis";
            default:
               return super.getElementName(propIndex);
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
            case 15:
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private DatabaseLessLeasingBasisMBeanImpl bean;

      protected Helper(DatabaseLessLeasingBasisMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "MemberDiscoveryTimeout";
            case 11:
               return "LeaderHeartbeatPeriod";
            case 12:
               return "MessageDeliveryTimeout";
            case 13:
               return "FenceTimeout";
            case 14:
               return "PeriodicSRMCheckEnabled";
            case 15:
               return "NodeManagerTimeoutMillis";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FenceTimeout")) {
            return 13;
         } else if (propName.equals("LeaderHeartbeatPeriod")) {
            return 11;
         } else if (propName.equals("MemberDiscoveryTimeout")) {
            return 10;
         } else if (propName.equals("MessageDeliveryTimeout")) {
            return 12;
         } else if (propName.equals("NodeManagerTimeoutMillis")) {
            return 15;
         } else {
            return propName.equals("PeriodicSRMCheckEnabled") ? 14 : super.getPropertyIndex(propName);
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
            if (this.bean.isFenceTimeoutSet()) {
               buf.append("FenceTimeout");
               buf.append(String.valueOf(this.bean.getFenceTimeout()));
            }

            if (this.bean.isLeaderHeartbeatPeriodSet()) {
               buf.append("LeaderHeartbeatPeriod");
               buf.append(String.valueOf(this.bean.getLeaderHeartbeatPeriod()));
            }

            if (this.bean.isMemberDiscoveryTimeoutSet()) {
               buf.append("MemberDiscoveryTimeout");
               buf.append(String.valueOf(this.bean.getMemberDiscoveryTimeout()));
            }

            if (this.bean.isMessageDeliveryTimeoutSet()) {
               buf.append("MessageDeliveryTimeout");
               buf.append(String.valueOf(this.bean.getMessageDeliveryTimeout()));
            }

            if (this.bean.isNodeManagerTimeoutMillisSet()) {
               buf.append("NodeManagerTimeoutMillis");
               buf.append(String.valueOf(this.bean.getNodeManagerTimeoutMillis()));
            }

            if (this.bean.isPeriodicSRMCheckEnabledSet()) {
               buf.append("PeriodicSRMCheckEnabled");
               buf.append(String.valueOf(this.bean.isPeriodicSRMCheckEnabled()));
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
            DatabaseLessLeasingBasisMBeanImpl otherTyped = (DatabaseLessLeasingBasisMBeanImpl)other;
            this.computeDiff("FenceTimeout", this.bean.getFenceTimeout(), otherTyped.getFenceTimeout(), true);
            this.computeDiff("LeaderHeartbeatPeriod", this.bean.getLeaderHeartbeatPeriod(), otherTyped.getLeaderHeartbeatPeriod(), false);
            this.computeDiff("MemberDiscoveryTimeout", this.bean.getMemberDiscoveryTimeout(), otherTyped.getMemberDiscoveryTimeout(), false);
            this.computeDiff("MessageDeliveryTimeout", this.bean.getMessageDeliveryTimeout(), otherTyped.getMessageDeliveryTimeout(), true);
            this.computeDiff("NodeManagerTimeoutMillis", this.bean.getNodeManagerTimeoutMillis(), otherTyped.getNodeManagerTimeoutMillis(), true);
            this.computeDiff("PeriodicSRMCheckEnabled", this.bean.isPeriodicSRMCheckEnabled(), otherTyped.isPeriodicSRMCheckEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DatabaseLessLeasingBasisMBeanImpl original = (DatabaseLessLeasingBasisMBeanImpl)event.getSourceBean();
            DatabaseLessLeasingBasisMBeanImpl proposed = (DatabaseLessLeasingBasisMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FenceTimeout")) {
                  original.setFenceTimeout(proposed.getFenceTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("LeaderHeartbeatPeriod")) {
                  original.setLeaderHeartbeatPeriod(proposed.getLeaderHeartbeatPeriod());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("MemberDiscoveryTimeout")) {
                  original.setMemberDiscoveryTimeout(proposed.getMemberDiscoveryTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MessageDeliveryTimeout")) {
                  original.setMessageDeliveryTimeout(proposed.getMessageDeliveryTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("NodeManagerTimeoutMillis")) {
                  original.setNodeManagerTimeoutMillis(proposed.getNodeManagerTimeoutMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("PeriodicSRMCheckEnabled")) {
                  original.setPeriodicSRMCheckEnabled(proposed.isPeriodicSRMCheckEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
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
            DatabaseLessLeasingBasisMBeanImpl copy = (DatabaseLessLeasingBasisMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FenceTimeout")) && this.bean.isFenceTimeoutSet()) {
               copy.setFenceTimeout(this.bean.getFenceTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("LeaderHeartbeatPeriod")) && this.bean.isLeaderHeartbeatPeriodSet()) {
               copy.setLeaderHeartbeatPeriod(this.bean.getLeaderHeartbeatPeriod());
            }

            if ((excludeProps == null || !excludeProps.contains("MemberDiscoveryTimeout")) && this.bean.isMemberDiscoveryTimeoutSet()) {
               copy.setMemberDiscoveryTimeout(this.bean.getMemberDiscoveryTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDeliveryTimeout")) && this.bean.isMessageDeliveryTimeoutSet()) {
               copy.setMessageDeliveryTimeout(this.bean.getMessageDeliveryTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("NodeManagerTimeoutMillis")) && this.bean.isNodeManagerTimeoutMillisSet()) {
               copy.setNodeManagerTimeoutMillis(this.bean.getNodeManagerTimeoutMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("PeriodicSRMCheckEnabled")) && this.bean.isPeriodicSRMCheckEnabledSet()) {
               copy.setPeriodicSRMCheckEnabled(this.bean.isPeriodicSRMCheckEnabled());
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
