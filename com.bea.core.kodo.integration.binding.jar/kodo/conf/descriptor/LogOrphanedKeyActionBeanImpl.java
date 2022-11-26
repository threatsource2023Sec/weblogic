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

public class LogOrphanedKeyActionBeanImpl extends OrphanedKeyActionBeanImpl implements LogOrphanedKeyActionBean, Serializable {
   private String _Channel;
   private String _Level;
   private static SchemaHelper2 _schemaHelper;

   public LogOrphanedKeyActionBeanImpl() {
      this._initializeProperty(-1);
   }

   public LogOrphanedKeyActionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LogOrphanedKeyActionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getChannel() {
      return this._Channel;
   }

   public boolean isChannelInherited() {
      return false;
   }

   public boolean isChannelSet() {
      return this._isSet(0);
   }

   public void setChannel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Channel;
      this._Channel = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getLevel() {
      return this._Level;
   }

   public boolean isLevelInherited() {
      return false;
   }

   public boolean isLevelSet() {
      return this._isSet(1);
   }

   public void setLevel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Level;
      this._Level = param0;
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
               this._Channel = "openjpa.Runtime";
               if (initOne) {
                  break;
               }
            case 1:
               this._Level = "4";
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

   public static class SchemaHelper2 extends OrphanedKeyActionBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("level")) {
                  return 1;
               }
               break;
            case 7:
               if (s.equals("channel")) {
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
               return "channel";
            case 1:
               return "level";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends OrphanedKeyActionBeanImpl.Helper {
      private LogOrphanedKeyActionBeanImpl bean;

      protected Helper(LogOrphanedKeyActionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Channel";
            case 1:
               return "Level";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Channel")) {
            return 0;
         } else {
            return propName.equals("Level") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isChannelSet()) {
               buf.append("Channel");
               buf.append(String.valueOf(this.bean.getChannel()));
            }

            if (this.bean.isLevelSet()) {
               buf.append("Level");
               buf.append(String.valueOf(this.bean.getLevel()));
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
            LogOrphanedKeyActionBeanImpl otherTyped = (LogOrphanedKeyActionBeanImpl)other;
            this.computeDiff("Channel", this.bean.getChannel(), otherTyped.getChannel(), false);
            this.computeDiff("Level", this.bean.getLevel(), otherTyped.getLevel(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LogOrphanedKeyActionBeanImpl original = (LogOrphanedKeyActionBeanImpl)event.getSourceBean();
            LogOrphanedKeyActionBeanImpl proposed = (LogOrphanedKeyActionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Channel")) {
                  original.setChannel(proposed.getChannel());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Level")) {
                  original.setLevel(proposed.getLevel());
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
            LogOrphanedKeyActionBeanImpl copy = (LogOrphanedKeyActionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Channel")) && this.bean.isChannelSet()) {
               copy.setChannel(this.bean.getChannel());
            }

            if ((excludeProps == null || !excludeProps.contains("Level")) && this.bean.isLevelSet()) {
               copy.setLevel(this.bean.getLevel());
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
