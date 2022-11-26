package com.bea.httppubsub.descriptor;

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

public class MessageFilterBeanImpl extends AbstractDescriptorBean implements MessageFilterBean, Serializable {
   private String _MessageFilterClass;
   private String _MessageFilterName;
   private static SchemaHelper2 _schemaHelper;

   public MessageFilterBeanImpl() {
      this._initializeProperty(-1);
   }

   public MessageFilterBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MessageFilterBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getMessageFilterName() {
      return this._MessageFilterName;
   }

   public boolean isMessageFilterNameInherited() {
      return false;
   }

   public boolean isMessageFilterNameSet() {
      return this._isSet(0);
   }

   public void setMessageFilterName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MessageFilterName;
      this._MessageFilterName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getMessageFilterClass() {
      return this._MessageFilterClass;
   }

   public boolean isMessageFilterClassInherited() {
      return false;
   }

   public boolean isMessageFilterClassSet() {
      return this._isSet(1);
   }

   public void setMessageFilterClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MessageFilterClass;
      this._MessageFilterClass = param0;
      this._postSet(1, _oldVal, param0);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._MessageFilterClass = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._MessageFilterName = null;
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
            case 19:
               if (s.equals("message-filter-name")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("message-filter-class")) {
                  return 1;
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
               return "message-filter-name";
            case 1:
               return "message-filter-class";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private MessageFilterBeanImpl bean;

      protected Helper(MessageFilterBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MessageFilterName";
            case 1:
               return "MessageFilterClass";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MessageFilterClass")) {
            return 1;
         } else {
            return propName.equals("MessageFilterName") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isMessageFilterClassSet()) {
               buf.append("MessageFilterClass");
               buf.append(String.valueOf(this.bean.getMessageFilterClass()));
            }

            if (this.bean.isMessageFilterNameSet()) {
               buf.append("MessageFilterName");
               buf.append(String.valueOf(this.bean.getMessageFilterName()));
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
            MessageFilterBeanImpl otherTyped = (MessageFilterBeanImpl)other;
            this.computeDiff("MessageFilterClass", this.bean.getMessageFilterClass(), otherTyped.getMessageFilterClass(), false);
            this.computeDiff("MessageFilterName", this.bean.getMessageFilterName(), otherTyped.getMessageFilterName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MessageFilterBeanImpl original = (MessageFilterBeanImpl)event.getSourceBean();
            MessageFilterBeanImpl proposed = (MessageFilterBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MessageFilterClass")) {
                  original.setMessageFilterClass(proposed.getMessageFilterClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MessageFilterName")) {
                  original.setMessageFilterName(proposed.getMessageFilterName());
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
            MessageFilterBeanImpl copy = (MessageFilterBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("MessageFilterClass")) && this.bean.isMessageFilterClassSet()) {
               copy.setMessageFilterClass(this.bean.getMessageFilterClass());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageFilterName")) && this.bean.isMessageFilterNameSet()) {
               copy.setMessageFilterName(this.bean.getMessageFilterName());
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
