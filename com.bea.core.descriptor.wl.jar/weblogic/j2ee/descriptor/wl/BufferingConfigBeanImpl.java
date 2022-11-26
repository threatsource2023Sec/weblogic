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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.WseeConfigBeanValidator;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class BufferingConfigBeanImpl extends AbstractDescriptorBean implements BufferingConfigBean, Serializable {
   private boolean _Customized;
   private BufferingQueueBean _RequestQueue;
   private BufferingQueueBean _ResponseQueue;
   private int _RetryCount;
   private String _RetryDelay;
   private static SchemaHelper2 _schemaHelper;

   public BufferingConfigBeanImpl() {
      this._initializeProperty(-1);
   }

   public BufferingConfigBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public BufferingConfigBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isCustomized() {
      return this._Customized;
   }

   public boolean isCustomizedInherited() {
      return false;
   }

   public boolean isCustomizedSet() {
      return this._isSet(0);
   }

   public void setCustomized(boolean param0) {
      boolean _oldVal = this._Customized;
      this._Customized = param0;
      this._postSet(0, _oldVal, param0);
   }

   public BufferingQueueBean getRequestQueue() {
      return this._RequestQueue;
   }

   public boolean isRequestQueueInherited() {
      return false;
   }

   public boolean isRequestQueueSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getRequestQueue());
   }

   public void setRequestQueue(BufferingQueueBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      BufferingQueueBean _oldVal = this._RequestQueue;
      this._RequestQueue = param0;
      this._postSet(1, _oldVal, param0);
   }

   public BufferingQueueBean getResponseQueue() {
      return this._ResponseQueue;
   }

   public boolean isResponseQueueInherited() {
      return false;
   }

   public boolean isResponseQueueSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getResponseQueue());
   }

   public void setResponseQueue(BufferingQueueBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      BufferingQueueBean _oldVal = this._ResponseQueue;
      this._ResponseQueue = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getRetryCount() {
      return this._RetryCount;
   }

   public boolean isRetryCountInherited() {
      return false;
   }

   public boolean isRetryCountSet() {
      return this._isSet(3);
   }

   public void setRetryCount(int param0) {
      LegalChecks.checkMin("RetryCount", param0, 0);
      int _oldVal = this._RetryCount;
      this._RetryCount = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getRetryDelay() {
      return this._RetryDelay;
   }

   public boolean isRetryDelayInherited() {
      return false;
   }

   public boolean isRetryDelaySet() {
      return this._isSet(4);
   }

   public void setRetryDelay(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WseeConfigBeanValidator.validateRetryDelay(param0);
      String _oldVal = this._RetryDelay;
      this._RetryDelay = param0;
      this._postSet(4, _oldVal, param0);
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
      return super._isAnythingSet() || this.isRequestQueueSet() || this.isResponseQueueSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._RequestQueue = new BufferingQueueBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._RequestQueue);
               if (initOne) {
                  break;
               }
            case 2:
               this._ResponseQueue = new BufferingQueueBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._ResponseQueue);
               if (initOne) {
                  break;
               }
            case 3:
               this._RetryCount = 3;
               if (initOne) {
                  break;
               }
            case 4:
               this._RetryDelay = "P0DT30S";
               if (initOne) {
                  break;
               }
            case 0:
               this._Customized = true;
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
            case 10:
               if (s.equals("customized")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("retry-count")) {
                  return 3;
               }

               if (s.equals("retry-delay")) {
                  return 4;
               }
            case 12:
            default:
               break;
            case 13:
               if (s.equals("request-queue")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("response-queue")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new BufferingQueueBeanImpl.SchemaHelper2();
            case 2:
               return new BufferingQueueBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "customized";
            case 1:
               return "request-queue";
            case 2:
               return "response-queue";
            case 3:
               return "retry-count";
            case 4:
               return "retry-delay";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private BufferingConfigBeanImpl bean;

      protected Helper(BufferingConfigBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Customized";
            case 1:
               return "RequestQueue";
            case 2:
               return "ResponseQueue";
            case 3:
               return "RetryCount";
            case 4:
               return "RetryDelay";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("RequestQueue")) {
            return 1;
         } else if (propName.equals("ResponseQueue")) {
            return 2;
         } else if (propName.equals("RetryCount")) {
            return 3;
         } else if (propName.equals("RetryDelay")) {
            return 4;
         } else {
            return propName.equals("Customized") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getRequestQueue() != null) {
            iterators.add(new ArrayIterator(new BufferingQueueBean[]{this.bean.getRequestQueue()}));
         }

         if (this.bean.getResponseQueue() != null) {
            iterators.add(new ArrayIterator(new BufferingQueueBean[]{this.bean.getResponseQueue()}));
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
            childValue = this.computeChildHashValue(this.bean.getRequestQueue());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getResponseQueue());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isRetryCountSet()) {
               buf.append("RetryCount");
               buf.append(String.valueOf(this.bean.getRetryCount()));
            }

            if (this.bean.isRetryDelaySet()) {
               buf.append("RetryDelay");
               buf.append(String.valueOf(this.bean.getRetryDelay()));
            }

            if (this.bean.isCustomizedSet()) {
               buf.append("Customized");
               buf.append(String.valueOf(this.bean.isCustomized()));
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
            BufferingConfigBeanImpl otherTyped = (BufferingConfigBeanImpl)other;
            this.computeSubDiff("RequestQueue", this.bean.getRequestQueue(), otherTyped.getRequestQueue());
            this.computeSubDiff("ResponseQueue", this.bean.getResponseQueue(), otherTyped.getResponseQueue());
            this.computeDiff("RetryCount", this.bean.getRetryCount(), otherTyped.getRetryCount(), true);
            this.computeDiff("RetryDelay", this.bean.getRetryDelay(), otherTyped.getRetryDelay(), true);
            this.computeDiff("Customized", this.bean.isCustomized(), otherTyped.isCustomized(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            BufferingConfigBeanImpl original = (BufferingConfigBeanImpl)event.getSourceBean();
            BufferingConfigBeanImpl proposed = (BufferingConfigBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("RequestQueue")) {
                  if (type == 2) {
                     original.setRequestQueue((BufferingQueueBean)this.createCopy((AbstractDescriptorBean)proposed.getRequestQueue()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("RequestQueue", (DescriptorBean)original.getRequestQueue());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ResponseQueue")) {
                  if (type == 2) {
                     original.setResponseQueue((BufferingQueueBean)this.createCopy((AbstractDescriptorBean)proposed.getResponseQueue()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ResponseQueue", (DescriptorBean)original.getResponseQueue());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RetryCount")) {
                  original.setRetryCount(proposed.getRetryCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RetryDelay")) {
                  original.setRetryDelay(proposed.getRetryDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Customized")) {
                  original.setCustomized(proposed.isCustomized());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            BufferingConfigBeanImpl copy = (BufferingConfigBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            BufferingQueueBean o;
            if ((excludeProps == null || !excludeProps.contains("RequestQueue")) && this.bean.isRequestQueueSet() && !copy._isSet(1)) {
               o = this.bean.getRequestQueue();
               copy.setRequestQueue((BufferingQueueBean)null);
               copy.setRequestQueue(o == null ? null : (BufferingQueueBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ResponseQueue")) && this.bean.isResponseQueueSet() && !copy._isSet(2)) {
               o = this.bean.getResponseQueue();
               copy.setResponseQueue((BufferingQueueBean)null);
               copy.setResponseQueue(o == null ? null : (BufferingQueueBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("RetryCount")) && this.bean.isRetryCountSet()) {
               copy.setRetryCount(this.bean.getRetryCount());
            }

            if ((excludeProps == null || !excludeProps.contains("RetryDelay")) && this.bean.isRetryDelaySet()) {
               copy.setRetryDelay(this.bean.getRetryDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("Customized")) && this.bean.isCustomizedSet()) {
               copy.setCustomized(this.bean.isCustomized());
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
         this.inferSubTree(this.bean.getRequestQueue(), clazz, annotation);
         this.inferSubTree(this.bean.getResponseQueue(), clazz, annotation);
      }
   }
}
