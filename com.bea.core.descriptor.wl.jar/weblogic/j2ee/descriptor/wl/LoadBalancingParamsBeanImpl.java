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
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.utils.collections.CombinedIterator;

public class LoadBalancingParamsBeanImpl extends SettableBeanImpl implements LoadBalancingParamsBean, Serializable {
   private boolean _LoadBalancingEnabled;
   private String _ProducerLoadBalancingPolicy;
   private boolean _ServerAffinityEnabled;
   private static SchemaHelper2 _schemaHelper;

   public LoadBalancingParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public LoadBalancingParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LoadBalancingParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isLoadBalancingEnabled() {
      return this._LoadBalancingEnabled;
   }

   public boolean isLoadBalancingEnabledInherited() {
      return false;
   }

   public boolean isLoadBalancingEnabledSet() {
      return this._isSet(0);
   }

   public void setLoadBalancingEnabled(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._LoadBalancingEnabled;
      this._LoadBalancingEnabled = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isServerAffinityEnabled() {
      return this._ServerAffinityEnabled;
   }

   public boolean isServerAffinityEnabledInherited() {
      return false;
   }

   public boolean isServerAffinityEnabledSet() {
      return this._isSet(1);
   }

   public void setServerAffinityEnabled(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._ServerAffinityEnabled;
      this._ServerAffinityEnabled = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getProducerLoadBalancingPolicy() {
      return this._ProducerLoadBalancingPolicy;
   }

   public boolean isProducerLoadBalancingPolicyInherited() {
      return false;
   }

   public boolean isProducerLoadBalancingPolicySet() {
      return this._isSet(2);
   }

   public void setProducerLoadBalancingPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{JMSConstants.PRODUCER_LB_POLICY_PER_MEMBER, JMSConstants.PRODUCER_LB_POLICY_PER_JVM};
      param0 = LegalChecks.checkInEnum("ProducerLoadBalancingPolicy", param0, _set);
      String _oldVal = this._ProducerLoadBalancingPolicy;
      this._ProducerLoadBalancingPolicy = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._ProducerLoadBalancingPolicy = JMSConstants.PRODUCER_LB_POLICY_PER_MEMBER;
               if (initOne) {
                  break;
               }
            case 0:
               this._LoadBalancingEnabled = true;
               if (initOne) {
                  break;
               }
            case 1:
               this._ServerAffinityEnabled = true;
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
            case 22:
               if (s.equals("load-balancing-enabled")) {
                  return 0;
               }
               break;
            case 23:
               if (s.equals("server-affinity-enabled")) {
                  return 1;
               }
               break;
            case 30:
               if (s.equals("producer-load-balancing-policy")) {
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
            case 0:
               return "load-balancing-enabled";
            case 1:
               return "server-affinity-enabled";
            case 2:
               return "producer-load-balancing-policy";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private LoadBalancingParamsBeanImpl bean;

      protected Helper(LoadBalancingParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "LoadBalancingEnabled";
            case 1:
               return "ServerAffinityEnabled";
            case 2:
               return "ProducerLoadBalancingPolicy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ProducerLoadBalancingPolicy")) {
            return 2;
         } else if (propName.equals("LoadBalancingEnabled")) {
            return 0;
         } else {
            return propName.equals("ServerAffinityEnabled") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isProducerLoadBalancingPolicySet()) {
               buf.append("ProducerLoadBalancingPolicy");
               buf.append(String.valueOf(this.bean.getProducerLoadBalancingPolicy()));
            }

            if (this.bean.isLoadBalancingEnabledSet()) {
               buf.append("LoadBalancingEnabled");
               buf.append(String.valueOf(this.bean.isLoadBalancingEnabled()));
            }

            if (this.bean.isServerAffinityEnabledSet()) {
               buf.append("ServerAffinityEnabled");
               buf.append(String.valueOf(this.bean.isServerAffinityEnabled()));
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
            LoadBalancingParamsBeanImpl otherTyped = (LoadBalancingParamsBeanImpl)other;
            this.computeDiff("ProducerLoadBalancingPolicy", this.bean.getProducerLoadBalancingPolicy(), otherTyped.getProducerLoadBalancingPolicy(), true);
            this.computeDiff("LoadBalancingEnabled", this.bean.isLoadBalancingEnabled(), otherTyped.isLoadBalancingEnabled(), true);
            this.computeDiff("ServerAffinityEnabled", this.bean.isServerAffinityEnabled(), otherTyped.isServerAffinityEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LoadBalancingParamsBeanImpl original = (LoadBalancingParamsBeanImpl)event.getSourceBean();
            LoadBalancingParamsBeanImpl proposed = (LoadBalancingParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ProducerLoadBalancingPolicy")) {
                  original.setProducerLoadBalancingPolicy(proposed.getProducerLoadBalancingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("LoadBalancingEnabled")) {
                  original.setLoadBalancingEnabled(proposed.isLoadBalancingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ServerAffinityEnabled")) {
                  original.setServerAffinityEnabled(proposed.isServerAffinityEnabled());
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
            LoadBalancingParamsBeanImpl copy = (LoadBalancingParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ProducerLoadBalancingPolicy")) && this.bean.isProducerLoadBalancingPolicySet()) {
               copy.setProducerLoadBalancingPolicy(this.bean.getProducerLoadBalancingPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("LoadBalancingEnabled")) && this.bean.isLoadBalancingEnabledSet()) {
               copy.setLoadBalancingEnabled(this.bean.isLoadBalancingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerAffinityEnabled")) && this.bean.isServerAffinityEnabledSet()) {
               copy.setServerAffinityEnabled(this.bean.isServerAffinityEnabled());
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
