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

public class CacheAsyncListenersMBeanImpl extends ConfigurationMBeanImpl implements CacheAsyncListenersMBean, Serializable {
   private boolean _Enabled;
   private String _WorkManager;
   private static SchemaHelper2 _schemaHelper;

   public CacheAsyncListenersMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CacheAsyncListenersMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CacheAsyncListenersMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(10);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getWorkManager() {
      if (!this._isSet(11)) {
         try {
            return ((CacheMBean)this.getParent()).getWorkManager();
         } catch (NullPointerException var2) {
         }
      }

      return this._WorkManager;
   }

   public boolean isWorkManagerInherited() {
      return false;
   }

   public boolean isWorkManagerSet() {
      return this._isSet(11);
   }

   public void setWorkManager(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("WorkManager", param0);
      String _oldVal = this._WorkManager;
      this._WorkManager = param0;
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
               this._Enabled = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._WorkManager = null;
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
      return "CacheAsyncListeners";
   }

   public void putValue(String name, Object v) {
      if (name.equals("Enabled")) {
         boolean oldVal = this._Enabled;
         this._Enabled = (Boolean)v;
         this._postSet(10, oldVal, this._Enabled);
      } else if (name.equals("WorkManager")) {
         String oldVal = this._WorkManager;
         this._WorkManager = (String)v;
         this._postSet(11, oldVal, this._WorkManager);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Enabled")) {
         return new Boolean(this._Enabled);
      } else {
         return name.equals("WorkManager") ? this._WorkManager : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("enabled")) {
                  return 10;
               }
               break;
            case 12:
               if (s.equals("work-manager")) {
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
               return "enabled";
            case 11:
               return "work-manager";
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
      private CacheAsyncListenersMBeanImpl bean;

      protected Helper(CacheAsyncListenersMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Enabled";
            case 11:
               return "WorkManager";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Enabled")) {
            return 10;
         } else {
            return propName.equals("WorkManager") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.getEnabled()));
            }

            if (this.bean.isWorkManagerSet()) {
               buf.append("WorkManager");
               buf.append(String.valueOf(this.bean.getWorkManager()));
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
            CacheAsyncListenersMBeanImpl otherTyped = (CacheAsyncListenersMBeanImpl)other;
            this.computeDiff("Enabled", this.bean.getEnabled(), otherTyped.getEnabled(), true);
            this.computeDiff("WorkManager", this.bean.getWorkManager(), otherTyped.getWorkManager(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CacheAsyncListenersMBeanImpl original = (CacheAsyncListenersMBeanImpl)event.getSourceBean();
            CacheAsyncListenersMBeanImpl proposed = (CacheAsyncListenersMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.getEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("WorkManager")) {
                  original.setWorkManager(proposed.getWorkManager());
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
            CacheAsyncListenersMBeanImpl copy = (CacheAsyncListenersMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.getEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManager")) && this.bean.isWorkManagerSet()) {
               copy.setWorkManager(this.bean.getWorkManager());
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
