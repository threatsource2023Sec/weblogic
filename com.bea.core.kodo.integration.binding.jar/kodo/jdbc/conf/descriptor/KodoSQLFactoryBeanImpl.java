package kodo.jdbc.conf.descriptor;

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

public class KodoSQLFactoryBeanImpl extends SQLFactoryBeanImpl implements KodoSQLFactoryBean, Serializable {
   private String _AdvancedSQL;
   private static SchemaHelper2 _schemaHelper;

   public KodoSQLFactoryBeanImpl() {
      this._initializeProperty(-1);
   }

   public KodoSQLFactoryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public KodoSQLFactoryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getAdvancedSQL() {
      return this._AdvancedSQL;
   }

   public boolean isAdvancedSQLInherited() {
      return false;
   }

   public boolean isAdvancedSQLSet() {
      return this._isSet(0);
   }

   public void setAdvancedSQL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AdvancedSQL;
      this._AdvancedSQL = param0;
      this._postSet(0, _oldVal, param0);
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
               this._AdvancedSQL = null;
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

   public static class SchemaHelper2 extends SQLFactoryBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("advanced-sql")) {
                  return 0;
               }
            default:
               return super.getPropertyIndex(s);
         }
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
               return "advanced-sql";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends SQLFactoryBeanImpl.Helper {
      private KodoSQLFactoryBeanImpl bean;

      protected Helper(KodoSQLFactoryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AdvancedSQL";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("AdvancedSQL") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isAdvancedSQLSet()) {
               buf.append("AdvancedSQL");
               buf.append(String.valueOf(this.bean.getAdvancedSQL()));
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
            KodoSQLFactoryBeanImpl otherTyped = (KodoSQLFactoryBeanImpl)other;
            this.computeDiff("AdvancedSQL", this.bean.getAdvancedSQL(), otherTyped.getAdvancedSQL(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            KodoSQLFactoryBeanImpl original = (KodoSQLFactoryBeanImpl)event.getSourceBean();
            KodoSQLFactoryBeanImpl proposed = (KodoSQLFactoryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdvancedSQL")) {
                  original.setAdvancedSQL(proposed.getAdvancedSQL());
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
            KodoSQLFactoryBeanImpl copy = (KodoSQLFactoryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AdvancedSQL")) && this.bean.isAdvancedSQLSet()) {
               copy.setAdvancedSQL(this.bean.getAdvancedSQL());
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
