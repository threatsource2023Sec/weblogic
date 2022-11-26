package weblogic.management.configuration;

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

public class DatasourceInterceptorMBeanImpl extends InterceptorMBeanImpl implements DatasourceInterceptorMBean, Serializable {
   private int _ConnectionQuota;
   private String _ConnectionUrlsPattern;
   private String _InterceptedTargetKey;
   private String _InterceptorTypeName;
   private static SchemaHelper2 _schemaHelper;

   public DatasourceInterceptorMBeanImpl() {
      this._initializeProperty(-1);
   }

   public DatasourceInterceptorMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DatasourceInterceptorMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getInterceptorTypeName() {
      return this._InterceptorTypeName;
   }

   public boolean isInterceptorTypeNameInherited() {
      return false;
   }

   public boolean isInterceptorTypeNameSet() {
      return this._isSet(10);
   }

   public void setInterceptorTypeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InterceptorTypeName;
      this._InterceptorTypeName = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getConnectionUrlsPattern() {
      return this._ConnectionUrlsPattern;
   }

   public String getInterceptedTargetKey() {
      return this._InterceptedTargetKey;
   }

   public boolean isConnectionUrlsPatternInherited() {
      return false;
   }

   public boolean isConnectionUrlsPatternSet() {
      return this._isSet(16);
   }

   public boolean isInterceptedTargetKeyInherited() {
      return false;
   }

   public boolean isInterceptedTargetKeySet() {
      return this._isSet(11);
   }

   public void setConnectionUrlsPattern(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionUrlsPattern;
      this._ConnectionUrlsPattern = param0;
      this._postSet(16, _oldVal, param0);
   }

   public void setInterceptedTargetKey(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InterceptedTargetKey;
      this._InterceptedTargetKey = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getConnectionQuota() {
      return this._ConnectionQuota;
   }

   public boolean isConnectionQuotaInherited() {
      return false;
   }

   public boolean isConnectionQuotaSet() {
      return this._isSet(17);
   }

   public void setConnectionQuota(int param0) {
      int _oldVal = this._ConnectionQuota;
      this._ConnectionQuota = param0;
      this._postSet(17, _oldVal, param0);
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
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._ConnectionQuota = 0;
               if (initOne) {
                  break;
               }
            case 16:
               this._ConnectionUrlsPattern = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._InterceptedTargetKey = "ElasticServiceManager";
               if (initOne) {
                  break;
               }
            case 10:
               this._InterceptorTypeName = "DatasourceInterceptor";
               if (initOne) {
                  break;
               }
            case 12:
            case 13:
            case 14:
            case 15:
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
      return "DatasourceInterceptor";
   }

   public void putValue(String name, Object v) {
      if (name.equals("ConnectionQuota")) {
         int oldVal = this._ConnectionQuota;
         this._ConnectionQuota = (Integer)v;
         this._postSet(17, oldVal, this._ConnectionQuota);
      } else {
         String oldVal;
         if (name.equals("ConnectionUrlsPattern")) {
            oldVal = this._ConnectionUrlsPattern;
            this._ConnectionUrlsPattern = (String)v;
            this._postSet(16, oldVal, this._ConnectionUrlsPattern);
         } else if (name.equals("InterceptedTargetKey")) {
            oldVal = this._InterceptedTargetKey;
            this._InterceptedTargetKey = (String)v;
            this._postSet(11, oldVal, this._InterceptedTargetKey);
         } else if (name.equals("InterceptorTypeName")) {
            oldVal = this._InterceptorTypeName;
            this._InterceptorTypeName = (String)v;
            this._postSet(10, oldVal, this._InterceptorTypeName);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ConnectionQuota")) {
         return new Integer(this._ConnectionQuota);
      } else if (name.equals("ConnectionUrlsPattern")) {
         return this._ConnectionUrlsPattern;
      } else if (name.equals("InterceptedTargetKey")) {
         return this._InterceptedTargetKey;
      } else {
         return name.equals("InterceptorTypeName") ? this._InterceptorTypeName : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends InterceptorMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 16:
               if (s.equals("connection-quota")) {
                  return 17;
               }
            case 17:
            case 18:
            case 19:
            case 20:
            default:
               break;
            case 21:
               if (s.equals("interceptor-type-name")) {
                  return 10;
               }
               break;
            case 22:
               if (s.equals("intercepted-target-key")) {
                  return 11;
               }
               break;
            case 23:
               if (s.equals("connection-urls-pattern")) {
                  return 16;
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
               return "interceptor-type-name";
            case 11:
               return "intercepted-target-key";
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.getElementName(propIndex);
            case 16:
               return "connection-urls-pattern";
            case 17:
               return "connection-quota";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 12:
               return true;
            case 14:
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

   protected static class Helper extends InterceptorMBeanImpl.Helper {
      private DatasourceInterceptorMBeanImpl bean;

      protected Helper(DatasourceInterceptorMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "InterceptorTypeName";
            case 11:
               return "InterceptedTargetKey";
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.getPropertyName(propIndex);
            case 16:
               return "ConnectionUrlsPattern";
            case 17:
               return "ConnectionQuota";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionQuota")) {
            return 17;
         } else if (propName.equals("ConnectionUrlsPattern")) {
            return 16;
         } else if (propName.equals("InterceptedTargetKey")) {
            return 11;
         } else {
            return propName.equals("InterceptorTypeName") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionQuotaSet()) {
               buf.append("ConnectionQuota");
               buf.append(String.valueOf(this.bean.getConnectionQuota()));
            }

            if (this.bean.isConnectionUrlsPatternSet()) {
               buf.append("ConnectionUrlsPattern");
               buf.append(String.valueOf(this.bean.getConnectionUrlsPattern()));
            }

            if (this.bean.isInterceptedTargetKeySet()) {
               buf.append("InterceptedTargetKey");
               buf.append(String.valueOf(this.bean.getInterceptedTargetKey()));
            }

            if (this.bean.isInterceptorTypeNameSet()) {
               buf.append("InterceptorTypeName");
               buf.append(String.valueOf(this.bean.getInterceptorTypeName()));
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
            DatasourceInterceptorMBeanImpl otherTyped = (DatasourceInterceptorMBeanImpl)other;
            this.computeDiff("ConnectionQuota", this.bean.getConnectionQuota(), otherTyped.getConnectionQuota(), true);
            this.computeDiff("ConnectionUrlsPattern", this.bean.getConnectionUrlsPattern(), otherTyped.getConnectionUrlsPattern(), true);
            this.computeDiff("InterceptedTargetKey", this.bean.getInterceptedTargetKey(), otherTyped.getInterceptedTargetKey(), false);
            this.computeDiff("InterceptorTypeName", this.bean.getInterceptorTypeName(), otherTyped.getInterceptorTypeName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DatasourceInterceptorMBeanImpl original = (DatasourceInterceptorMBeanImpl)event.getSourceBean();
            DatasourceInterceptorMBeanImpl proposed = (DatasourceInterceptorMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionQuota")) {
                  original.setConnectionQuota(proposed.getConnectionQuota());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("ConnectionUrlsPattern")) {
                  original.setConnectionUrlsPattern(proposed.getConnectionUrlsPattern());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("InterceptedTargetKey")) {
                  original.setInterceptedTargetKey(proposed.getInterceptedTargetKey());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("InterceptorTypeName")) {
                  original.setInterceptorTypeName(proposed.getInterceptorTypeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            DatasourceInterceptorMBeanImpl copy = (DatasourceInterceptorMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionQuota")) && this.bean.isConnectionQuotaSet()) {
               copy.setConnectionQuota(this.bean.getConnectionQuota());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionUrlsPattern")) && this.bean.isConnectionUrlsPatternSet()) {
               copy.setConnectionUrlsPattern(this.bean.getConnectionUrlsPattern());
            }

            if ((excludeProps == null || !excludeProps.contains("InterceptedTargetKey")) && this.bean.isInterceptedTargetKeySet()) {
               copy.setInterceptedTargetKey(this.bean.getInterceptedTargetKey());
            }

            if ((excludeProps == null || !excludeProps.contains("InterceptorTypeName")) && this.bean.isInterceptorTypeNameSet()) {
               copy.setInterceptorTypeName(this.bean.getInterceptorTypeName());
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
