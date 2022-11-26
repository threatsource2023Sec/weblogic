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

public class CacheTransactionMBeanImpl extends ConfigurationMBeanImpl implements CacheTransactionMBean, Serializable {
   private String _Concurrency;
   private String _IsolationLevel;
   private static SchemaHelper2 _schemaHelper;

   public CacheTransactionMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CacheTransactionMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CacheTransactionMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getConcurrency() {
      return this._Concurrency;
   }

   public boolean isConcurrencyInherited() {
      return false;
   }

   public boolean isConcurrencySet() {
      return this._isSet(10);
   }

   public void setConcurrency(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Pessimistic", "Optimistic", "None"};
      param0 = LegalChecks.checkInEnum("Concurrency", param0, _set);
      String _oldVal = this._Concurrency;
      this._Concurrency = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getIsolationLevel() {
      return this._IsolationLevel;
   }

   public boolean isIsolationLevelInherited() {
      return false;
   }

   public boolean isIsolationLevelSet() {
      return this._isSet(11);
   }

   public void setIsolationLevel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"ReadUncommitted", "ReadCommitted", "RepeatableRead"};
      param0 = LegalChecks.checkInEnum("IsolationLevel", param0, _set);
      String _oldVal = this._IsolationLevel;
      this._IsolationLevel = param0;
      this._postSet(11, _oldVal, param0);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._Concurrency = "None";
               if (initOne) {
                  break;
               }
            case 11:
               this._IsolationLevel = "RepeatableRead";
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
      return "CacheTransaction";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Concurrency")) {
         oldVal = this._Concurrency;
         this._Concurrency = (String)v;
         this._postSet(10, oldVal, this._Concurrency);
      } else if (name.equals("IsolationLevel")) {
         oldVal = this._IsolationLevel;
         this._IsolationLevel = (String)v;
         this._postSet(11, oldVal, this._IsolationLevel);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Concurrency")) {
         return this._Concurrency;
      } else {
         return name.equals("IsolationLevel") ? this._IsolationLevel : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("concurrency")) {
                  return 10;
               }
               break;
            case 15:
               if (s.equals("isolation-level")) {
                  return 11;
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
               return "concurrency";
            case 11:
               return "isolation-level";
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
      private CacheTransactionMBeanImpl bean;

      protected Helper(CacheTransactionMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Concurrency";
            case 11:
               return "IsolationLevel";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Concurrency")) {
            return 10;
         } else {
            return propName.equals("IsolationLevel") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isConcurrencySet()) {
               buf.append("Concurrency");
               buf.append(String.valueOf(this.bean.getConcurrency()));
            }

            if (this.bean.isIsolationLevelSet()) {
               buf.append("IsolationLevel");
               buf.append(String.valueOf(this.bean.getIsolationLevel()));
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
            CacheTransactionMBeanImpl otherTyped = (CacheTransactionMBeanImpl)other;
            this.computeDiff("Concurrency", this.bean.getConcurrency(), otherTyped.getConcurrency(), false);
            this.computeDiff("IsolationLevel", this.bean.getIsolationLevel(), otherTyped.getIsolationLevel(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CacheTransactionMBeanImpl original = (CacheTransactionMBeanImpl)event.getSourceBean();
            CacheTransactionMBeanImpl proposed = (CacheTransactionMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Concurrency")) {
                  original.setConcurrency(proposed.getConcurrency());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("IsolationLevel")) {
                  original.setIsolationLevel(proposed.getIsolationLevel());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            CacheTransactionMBeanImpl copy = (CacheTransactionMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Concurrency")) && this.bean.isConcurrencySet()) {
               copy.setConcurrency(this.bean.getConcurrency());
            }

            if ((excludeProps == null || !excludeProps.contains("IsolationLevel")) && this.bean.isIsolationLevelSet()) {
               copy.setIsolationLevel(this.bean.getIsolationLevel());
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
