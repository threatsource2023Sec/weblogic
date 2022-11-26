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

public class ChannelPersistenceBeanImpl extends AbstractDescriptorBean implements ChannelPersistenceBean, Serializable {
   private int _MaxPersistentMessageDurationSecs;
   private String _PersistentStore;
   private static SchemaHelper2 _schemaHelper;

   public ChannelPersistenceBeanImpl() {
      this._initializeProperty(-1);
   }

   public ChannelPersistenceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ChannelPersistenceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getMaxPersistentMessageDurationSecs() {
      return this._MaxPersistentMessageDurationSecs;
   }

   public boolean isMaxPersistentMessageDurationSecsInherited() {
      return false;
   }

   public boolean isMaxPersistentMessageDurationSecsSet() {
      return this._isSet(0);
   }

   public void setMaxPersistentMessageDurationSecs(int param0) {
      int _oldVal = this._MaxPersistentMessageDurationSecs;
      this._MaxPersistentMessageDurationSecs = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getPersistentStore() {
      return this._PersistentStore;
   }

   public boolean isPersistentStoreInherited() {
      return false;
   }

   public boolean isPersistentStoreSet() {
      return this._isSet(1);
   }

   public void setPersistentStore(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PersistentStore;
      this._PersistentStore = param0;
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._MaxPersistentMessageDurationSecs = 3600;
               if (initOne) {
                  break;
               }
            case 1:
               this._PersistentStore = null;
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
            case 16:
               if (s.equals("persistent-store")) {
                  return 1;
               }
               break;
            case 36:
               if (s.equals("max-persistent-message-duration-secs")) {
                  return 0;
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
               return "max-persistent-message-duration-secs";
            case 1:
               return "persistent-store";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ChannelPersistenceBeanImpl bean;

      protected Helper(ChannelPersistenceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MaxPersistentMessageDurationSecs";
            case 1:
               return "PersistentStore";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MaxPersistentMessageDurationSecs")) {
            return 0;
         } else {
            return propName.equals("PersistentStore") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isMaxPersistentMessageDurationSecsSet()) {
               buf.append("MaxPersistentMessageDurationSecs");
               buf.append(String.valueOf(this.bean.getMaxPersistentMessageDurationSecs()));
            }

            if (this.bean.isPersistentStoreSet()) {
               buf.append("PersistentStore");
               buf.append(String.valueOf(this.bean.getPersistentStore()));
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
            ChannelPersistenceBeanImpl otherTyped = (ChannelPersistenceBeanImpl)other;
            this.computeDiff("MaxPersistentMessageDurationSecs", this.bean.getMaxPersistentMessageDurationSecs(), otherTyped.getMaxPersistentMessageDurationSecs(), false);
            this.computeDiff("PersistentStore", this.bean.getPersistentStore(), otherTyped.getPersistentStore(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ChannelPersistenceBeanImpl original = (ChannelPersistenceBeanImpl)event.getSourceBean();
            ChannelPersistenceBeanImpl proposed = (ChannelPersistenceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MaxPersistentMessageDurationSecs")) {
                  original.setMaxPersistentMessageDurationSecs(proposed.getMaxPersistentMessageDurationSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("PersistentStore")) {
                  original.setPersistentStore(proposed.getPersistentStore());
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
            ChannelPersistenceBeanImpl copy = (ChannelPersistenceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("MaxPersistentMessageDurationSecs")) && this.bean.isMaxPersistentMessageDurationSecsSet()) {
               copy.setMaxPersistentMessageDurationSecs(this.bean.getMaxPersistentMessageDurationSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentStore")) && this.bean.isPersistentStoreSet()) {
               copy.setPersistentStore(this.bean.getPersistentStore());
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
