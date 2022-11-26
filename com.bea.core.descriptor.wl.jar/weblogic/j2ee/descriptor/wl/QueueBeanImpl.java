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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class QueueBeanImpl extends DestinationBeanImpl implements QueueBean, Serializable {
   private int _ForwardDelay;
   private boolean _ResetDeliveryCountOnForward;
   private static SchemaHelper2 _schemaHelper;

   public QueueBeanImpl() {
      this._initializeProperty(-1);
   }

   public QueueBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public QueueBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getForwardDelay() {
      return this._ForwardDelay;
   }

   public boolean isForwardDelayInherited() {
      return false;
   }

   public boolean isForwardDelaySet() {
      return this._isSet(27);
   }

   public void setForwardDelay(int param0) throws IllegalArgumentException {
      int _oldVal = this._ForwardDelay;
      this._ForwardDelay = param0;
      this._postSet(27, _oldVal, param0);
   }

   public boolean getResetDeliveryCountOnForward() {
      return this._ResetDeliveryCountOnForward;
   }

   public boolean isResetDeliveryCountOnForwardInherited() {
      return false;
   }

   public boolean isResetDeliveryCountOnForwardSet() {
      return this._isSet(28);
   }

   public void setResetDeliveryCountOnForward(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._ResetDeliveryCountOnForward;
      this._ResetDeliveryCountOnForward = param0;
      this._postSet(28, _oldVal, param0);
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
         idx = 27;
      }

      try {
         switch (idx) {
            case 27:
               this._ForwardDelay = -1;
               if (initOne) {
                  break;
               }
            case 28:
               this._ResetDeliveryCountOnForward = true;
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

   public static class SchemaHelper2 extends DestinationBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("forward-delay")) {
                  return 27;
               }
               break;
            case 31:
               if (s.equals("reset-delivery-count-on-forward")) {
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
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 27:
               return "forward-delay";
            case 28:
               return "reset-delivery-count-on-forward";
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
            case 26:
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
            case 27:
               return true;
            case 28:
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

   protected static class Helper extends DestinationBeanImpl.Helper {
      private QueueBeanImpl bean;

      protected Helper(QueueBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 27:
               return "ForwardDelay";
            case 28:
               return "ResetDeliveryCountOnForward";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ForwardDelay")) {
            return 27;
         } else {
            return propName.equals("ResetDeliveryCountOnForward") ? 28 : super.getPropertyIndex(propName);
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

         if (this.bean.getThresholds() != null) {
            iterators.add(new ArrayIterator(new ThresholdParamsBean[]{this.bean.getThresholds()}));
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
            if (this.bean.isForwardDelaySet()) {
               buf.append("ForwardDelay");
               buf.append(String.valueOf(this.bean.getForwardDelay()));
            }

            if (this.bean.isResetDeliveryCountOnForwardSet()) {
               buf.append("ResetDeliveryCountOnForward");
               buf.append(String.valueOf(this.bean.getResetDeliveryCountOnForward()));
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
            QueueBeanImpl otherTyped = (QueueBeanImpl)other;
            this.computeDiff("ForwardDelay", this.bean.getForwardDelay(), otherTyped.getForwardDelay(), true);
            this.computeDiff("ResetDeliveryCountOnForward", this.bean.getResetDeliveryCountOnForward(), otherTyped.getResetDeliveryCountOnForward(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            QueueBeanImpl original = (QueueBeanImpl)event.getSourceBean();
            QueueBeanImpl proposed = (QueueBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ForwardDelay")) {
                  original.setForwardDelay(proposed.getForwardDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("ResetDeliveryCountOnForward")) {
                  original.setResetDeliveryCountOnForward(proposed.getResetDeliveryCountOnForward());
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
            QueueBeanImpl copy = (QueueBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ForwardDelay")) && this.bean.isForwardDelaySet()) {
               copy.setForwardDelay(this.bean.getForwardDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("ResetDeliveryCountOnForward")) && this.bean.isResetDeliveryCountOnForwardSet()) {
               copy.setResetDeliveryCountOnForward(this.bean.getResetDeliveryCountOnForward());
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
