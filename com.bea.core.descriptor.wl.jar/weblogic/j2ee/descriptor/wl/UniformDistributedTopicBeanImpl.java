package weblogic.j2ee.descriptor.wl;

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
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class UniformDistributedTopicBeanImpl extends UniformDistributedDestinationBeanImpl implements UniformDistributedTopicBean, Serializable {
   private String _ForwardingPolicy;
   private MulticastParamsBean _Multicast;
   private TopicSubscriptionParamsBean _TopicSubscriptionParams;
   private static SchemaHelper2 _schemaHelper;

   public UniformDistributedTopicBeanImpl() {
      this._initializeProperty(-1);
   }

   public UniformDistributedTopicBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public UniformDistributedTopicBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public MulticastParamsBean getMulticast() {
      return this._Multicast;
   }

   public boolean isMulticastInherited() {
      return false;
   }

   public boolean isMulticastSet() {
      return this._isSet(27) || this._isAnythingSet((AbstractDescriptorBean)this.getMulticast());
   }

   public void setMulticast(MulticastParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 27)) {
         this._postCreate(_child);
      }

      MulticastParamsBean _oldVal = this._Multicast;
      this._Multicast = param0;
      this._postSet(27, _oldVal, param0);
   }

   public TopicSubscriptionParamsBean getTopicSubscriptionParams() {
      return this._TopicSubscriptionParams;
   }

   public boolean isTopicSubscriptionParamsInherited() {
      return false;
   }

   public boolean isTopicSubscriptionParamsSet() {
      return this._isSet(28) || this._isAnythingSet((AbstractDescriptorBean)this.getTopicSubscriptionParams());
   }

   public void setTopicSubscriptionParams(TopicSubscriptionParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 28)) {
         this._postCreate(_child);
      }

      TopicSubscriptionParamsBean _oldVal = this._TopicSubscriptionParams;
      this._TopicSubscriptionParams = param0;
      this._postSet(28, _oldVal, param0);
   }

   public String getForwardingPolicy() {
      return this._ForwardingPolicy;
   }

   public boolean isForwardingPolicyInherited() {
      return false;
   }

   public boolean isForwardingPolicySet() {
      return this._isSet(29);
   }

   public void setForwardingPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{JMSConstants.FORWARDING_POLICY_PARTITIONED, JMSConstants.FORWARDING_POLICY_REPLICATED};
      param0 = LegalChecks.checkInEnum("ForwardingPolicy", param0, _set);
      String _oldVal = this._ForwardingPolicy;
      this._ForwardingPolicy = param0;
      this._postSet(29, _oldVal, param0);
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
      return super._isAnythingSet() || this.isMulticastSet() || this.isTopicSubscriptionParamsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 29;
      }

      try {
         switch (idx) {
            case 29:
               this._ForwardingPolicy = JMSConstants.FORWARDING_POLICY_REPLICATED;
               if (initOne) {
                  break;
               }
            case 27:
               this._Multicast = new MulticastParamsBeanImpl(this, 27);
               this._postCreate((AbstractDescriptorBean)this._Multicast);
               if (initOne) {
                  break;
               }
            case 28:
               this._TopicSubscriptionParams = new TopicSubscriptionParamsBeanImpl(this, 28);
               this._postCreate((AbstractDescriptorBean)this._TopicSubscriptionParams);
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

   public static class SchemaHelper2 extends UniformDistributedDestinationBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("multicast")) {
                  return 27;
               }
               break;
            case 17:
               if (s.equals("forwarding-policy")) {
                  return 29;
               }
               break;
            case 25:
               if (s.equals("topic-subscription-params")) {
                  return 28;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 7:
               return new ThresholdParamsBeanImpl.SchemaHelper2();
            case 8:
               return new DeliveryParamsOverridesBeanImpl.SchemaHelper2();
            case 9:
               return new DeliveryFailureParamsBeanImpl.SchemaHelper2();
            case 10:
               return new MessageLoggingParamsBeanImpl.SchemaHelper2();
            case 27:
               return new MulticastParamsBeanImpl.SchemaHelper2();
            case 28:
               return new TopicSubscriptionParamsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 27:
               return "multicast";
            case 28:
               return "topic-subscription-params";
            case 29:
               return "forwarding-policy";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 6:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 27:
               return true;
            case 28:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 17:
            case 18:
            case 19:
            case 21:
            default:
               return super.isConfigurable(propIndex);
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
            case 16:
               return true;
            case 20:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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

   protected static class Helper extends UniformDistributedDestinationBeanImpl.Helper {
      private UniformDistributedTopicBeanImpl bean;

      protected Helper(UniformDistributedTopicBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 27:
               return "Multicast";
            case 28:
               return "TopicSubscriptionParams";
            case 29:
               return "ForwardingPolicy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ForwardingPolicy")) {
            return 29;
         } else if (propName.equals("Multicast")) {
            return 27;
         } else {
            return propName.equals("TopicSubscriptionParams") ? 28 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getDeliveryFailureParams() != null) {
            iterators.add(new ArrayIterator(new DeliveryFailureParamsBean[]{this.bean.getDeliveryFailureParams()}));
         }

         if (this.bean.getDeliveryParamsOverrides() != null) {
            iterators.add(new ArrayIterator(new DeliveryParamsOverridesBean[]{this.bean.getDeliveryParamsOverrides()}));
         }

         if (this.bean.getMessageLoggingParams() != null) {
            iterators.add(new ArrayIterator(new MessageLoggingParamsBean[]{this.bean.getMessageLoggingParams()}));
         }

         if (this.bean.getMulticast() != null) {
            iterators.add(new ArrayIterator(new MulticastParamsBean[]{this.bean.getMulticast()}));
         }

         if (this.bean.getThresholds() != null) {
            iterators.add(new ArrayIterator(new ThresholdParamsBean[]{this.bean.getThresholds()}));
         }

         if (this.bean.getTopicSubscriptionParams() != null) {
            iterators.add(new ArrayIterator(new TopicSubscriptionParamsBean[]{this.bean.getTopicSubscriptionParams()}));
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
            if (this.bean.isForwardingPolicySet()) {
               buf.append("ForwardingPolicy");
               buf.append(String.valueOf(this.bean.getForwardingPolicy()));
            }

            childValue = this.computeChildHashValue(this.bean.getMulticast());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTopicSubscriptionParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            UniformDistributedTopicBeanImpl otherTyped = (UniformDistributedTopicBeanImpl)other;
            this.computeDiff("ForwardingPolicy", this.bean.getForwardingPolicy(), otherTyped.getForwardingPolicy(), false);
            this.computeSubDiff("Multicast", this.bean.getMulticast(), otherTyped.getMulticast());
            this.computeSubDiff("TopicSubscriptionParams", this.bean.getTopicSubscriptionParams(), otherTyped.getTopicSubscriptionParams());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            UniformDistributedTopicBeanImpl original = (UniformDistributedTopicBeanImpl)event.getSourceBean();
            UniformDistributedTopicBeanImpl proposed = (UniformDistributedTopicBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ForwardingPolicy")) {
                  original.setForwardingPolicy(proposed.getForwardingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("Multicast")) {
                  if (type == 2) {
                     original.setMulticast((MulticastParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getMulticast()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Multicast", (DescriptorBean)original.getMulticast());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("TopicSubscriptionParams")) {
                  if (type == 2) {
                     original.setTopicSubscriptionParams((TopicSubscriptionParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getTopicSubscriptionParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TopicSubscriptionParams", (DescriptorBean)original.getTopicSubscriptionParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 28);
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
            UniformDistributedTopicBeanImpl copy = (UniformDistributedTopicBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ForwardingPolicy")) && this.bean.isForwardingPolicySet()) {
               copy.setForwardingPolicy(this.bean.getForwardingPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("Multicast")) && this.bean.isMulticastSet() && !copy._isSet(27)) {
               Object o = this.bean.getMulticast();
               copy.setMulticast((MulticastParamsBean)null);
               copy.setMulticast(o == null ? null : (MulticastParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TopicSubscriptionParams")) && this.bean.isTopicSubscriptionParamsSet() && !copy._isSet(28)) {
               Object o = this.bean.getTopicSubscriptionParams();
               copy.setTopicSubscriptionParams((TopicSubscriptionParamsBean)null);
               copy.setTopicSubscriptionParams(o == null ? null : (TopicSubscriptionParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getMulticast(), clazz, annotation);
         this.inferSubTree(this.bean.getTopicSubscriptionParams(), clazz, annotation);
      }
   }
}
