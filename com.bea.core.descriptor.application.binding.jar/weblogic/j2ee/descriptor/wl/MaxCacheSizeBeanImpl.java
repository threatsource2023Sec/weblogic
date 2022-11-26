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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class MaxCacheSizeBeanImpl extends AbstractDescriptorBean implements MaxCacheSizeBean, Serializable {
   private int _Bytes;
   private int _Megabytes;
   private static SchemaHelper2 _schemaHelper;

   public MaxCacheSizeBeanImpl() {
      this._initializeProperty(-1);
   }

   public MaxCacheSizeBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MaxCacheSizeBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getBytes() {
      return this._Bytes;
   }

   public boolean isBytesInherited() {
      return false;
   }

   public boolean isBytesSet() {
      return this._isSet(0);
   }

   public void setBytes(int param0) {
      int _oldVal = this._Bytes;
      this._Bytes = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getMegabytes() {
      return this._Megabytes;
   }

   public boolean isMegabytesInherited() {
      return false;
   }

   public boolean isMegabytesSet() {
      return this._isSet(1);
   }

   public void setMegabytes(int param0) {
      int _oldVal = this._Megabytes;
      this._Megabytes = param0;
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
               this._Bytes = -1;
               if (initOne) {
                  break;
               }
            case 1:
               this._Megabytes = -1;
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
            case 5:
               if (s.equals("bytes")) {
                  return 0;
               }
               break;
            case 9:
               if (s.equals("megabytes")) {
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
               return "bytes";
            case 1:
               return "megabytes";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private MaxCacheSizeBeanImpl bean;

      protected Helper(MaxCacheSizeBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Bytes";
            case 1:
               return "Megabytes";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Bytes")) {
            return 0;
         } else {
            return propName.equals("Megabytes") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isBytesSet()) {
               buf.append("Bytes");
               buf.append(String.valueOf(this.bean.getBytes()));
            }

            if (this.bean.isMegabytesSet()) {
               buf.append("Megabytes");
               buf.append(String.valueOf(this.bean.getMegabytes()));
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
            MaxCacheSizeBeanImpl otherTyped = (MaxCacheSizeBeanImpl)other;
            this.computeDiff("Bytes", this.bean.getBytes(), otherTyped.getBytes(), true);
            this.computeDiff("Megabytes", this.bean.getMegabytes(), otherTyped.getMegabytes(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MaxCacheSizeBeanImpl original = (MaxCacheSizeBeanImpl)event.getSourceBean();
            MaxCacheSizeBeanImpl proposed = (MaxCacheSizeBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Bytes")) {
                  original.setBytes(proposed.getBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Megabytes")) {
                  original.setMegabytes(proposed.getMegabytes());
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
            MaxCacheSizeBeanImpl copy = (MaxCacheSizeBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Bytes")) && this.bean.isBytesSet()) {
               copy.setBytes(this.bean.getBytes());
            }

            if ((excludeProps == null || !excludeProps.contains("Megabytes")) && this.bean.isMegabytesSet()) {
               copy.setMegabytes(this.bean.getMegabytes());
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
