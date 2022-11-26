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

public class PreparedStatementBeanImpl extends AbstractDescriptorBean implements PreparedStatementBean, Serializable {
   private int _CacheProfilingThreshold;
   private int _CacheSize;
   private int _CacheType;
   private int _MaxParameterLength;
   private boolean _ParameterLoggingEnabled;
   private boolean _ProfilingEnabled;
   private static SchemaHelper2 _schemaHelper;

   public PreparedStatementBeanImpl() {
      this._initializeProperty(-1);
   }

   public PreparedStatementBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PreparedStatementBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isProfilingEnabled() {
      return this._ProfilingEnabled;
   }

   public boolean isProfilingEnabledInherited() {
      return false;
   }

   public boolean isProfilingEnabledSet() {
      return this._isSet(0);
   }

   public void setProfilingEnabled(boolean param0) {
      boolean _oldVal = this._ProfilingEnabled;
      this._ProfilingEnabled = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getCacheProfilingThreshold() {
      return this._CacheProfilingThreshold;
   }

   public boolean isCacheProfilingThresholdInherited() {
      return false;
   }

   public boolean isCacheProfilingThresholdSet() {
      return this._isSet(1);
   }

   public void setCacheProfilingThreshold(int param0) {
      int _oldVal = this._CacheProfilingThreshold;
      this._CacheProfilingThreshold = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getCacheSize() {
      return this._CacheSize;
   }

   public boolean isCacheSizeInherited() {
      return false;
   }

   public boolean isCacheSizeSet() {
      return this._isSet(2);
   }

   public void setCacheSize(int param0) {
      int _oldVal = this._CacheSize;
      this._CacheSize = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isParameterLoggingEnabled() {
      return this._ParameterLoggingEnabled;
   }

   public boolean isParameterLoggingEnabledInherited() {
      return false;
   }

   public boolean isParameterLoggingEnabledSet() {
      return this._isSet(3);
   }

   public void setParameterLoggingEnabled(boolean param0) {
      boolean _oldVal = this._ParameterLoggingEnabled;
      this._ParameterLoggingEnabled = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getMaxParameterLength() {
      return this._MaxParameterLength;
   }

   public boolean isMaxParameterLengthInherited() {
      return false;
   }

   public boolean isMaxParameterLengthSet() {
      return this._isSet(4);
   }

   public void setMaxParameterLength(int param0) {
      int _oldVal = this._MaxParameterLength;
      this._MaxParameterLength = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getCacheType() {
      return this._CacheType;
   }

   public boolean isCacheTypeInherited() {
      return false;
   }

   public boolean isCacheTypeSet() {
      return this._isSet(5);
   }

   public void setCacheType(int param0) {
      int _oldVal = this._CacheType;
      this._CacheType = param0;
      this._postSet(5, _oldVal, param0);
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
               this._CacheProfilingThreshold = 0;
               if (initOne) {
                  break;
               }
            case 2:
               this._CacheSize = 0;
               if (initOne) {
                  break;
               }
            case 5:
               this._CacheType = 0;
               if (initOne) {
                  break;
               }
            case 4:
               this._MaxParameterLength = 0;
               if (initOne) {
                  break;
               }
            case 3:
               this._ParameterLoggingEnabled = false;
               if (initOne) {
                  break;
               }
            case 0:
               this._ProfilingEnabled = false;
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
               if (s.equals("cache-size")) {
                  return 2;
               }

               if (s.equals("cache-type")) {
                  return 5;
               }
               break;
            case 17:
               if (s.equals("profiling-enabled")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("max-parameter-length")) {
                  return 4;
               }
               break;
            case 25:
               if (s.equals("cache-profiling-threshold")) {
                  return 1;
               }

               if (s.equals("parameter-logging-enabled")) {
                  return 3;
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
               return "profiling-enabled";
            case 1:
               return "cache-profiling-threshold";
            case 2:
               return "cache-size";
            case 3:
               return "parameter-logging-enabled";
            case 4:
               return "max-parameter-length";
            case 5:
               return "cache-type";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PreparedStatementBeanImpl bean;

      protected Helper(PreparedStatementBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ProfilingEnabled";
            case 1:
               return "CacheProfilingThreshold";
            case 2:
               return "CacheSize";
            case 3:
               return "ParameterLoggingEnabled";
            case 4:
               return "MaxParameterLength";
            case 5:
               return "CacheType";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CacheProfilingThreshold")) {
            return 1;
         } else if (propName.equals("CacheSize")) {
            return 2;
         } else if (propName.equals("CacheType")) {
            return 5;
         } else if (propName.equals("MaxParameterLength")) {
            return 4;
         } else if (propName.equals("ParameterLoggingEnabled")) {
            return 3;
         } else {
            return propName.equals("ProfilingEnabled") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isCacheProfilingThresholdSet()) {
               buf.append("CacheProfilingThreshold");
               buf.append(String.valueOf(this.bean.getCacheProfilingThreshold()));
            }

            if (this.bean.isCacheSizeSet()) {
               buf.append("CacheSize");
               buf.append(String.valueOf(this.bean.getCacheSize()));
            }

            if (this.bean.isCacheTypeSet()) {
               buf.append("CacheType");
               buf.append(String.valueOf(this.bean.getCacheType()));
            }

            if (this.bean.isMaxParameterLengthSet()) {
               buf.append("MaxParameterLength");
               buf.append(String.valueOf(this.bean.getMaxParameterLength()));
            }

            if (this.bean.isParameterLoggingEnabledSet()) {
               buf.append("ParameterLoggingEnabled");
               buf.append(String.valueOf(this.bean.isParameterLoggingEnabled()));
            }

            if (this.bean.isProfilingEnabledSet()) {
               buf.append("ProfilingEnabled");
               buf.append(String.valueOf(this.bean.isProfilingEnabled()));
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
            PreparedStatementBeanImpl otherTyped = (PreparedStatementBeanImpl)other;
            this.computeDiff("CacheProfilingThreshold", this.bean.getCacheProfilingThreshold(), otherTyped.getCacheProfilingThreshold(), false);
            this.computeDiff("CacheSize", this.bean.getCacheSize(), otherTyped.getCacheSize(), false);
            this.computeDiff("CacheType", this.bean.getCacheType(), otherTyped.getCacheType(), false);
            this.computeDiff("MaxParameterLength", this.bean.getMaxParameterLength(), otherTyped.getMaxParameterLength(), false);
            this.computeDiff("ParameterLoggingEnabled", this.bean.isParameterLoggingEnabled(), otherTyped.isParameterLoggingEnabled(), false);
            this.computeDiff("ProfilingEnabled", this.bean.isProfilingEnabled(), otherTyped.isProfilingEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PreparedStatementBeanImpl original = (PreparedStatementBeanImpl)event.getSourceBean();
            PreparedStatementBeanImpl proposed = (PreparedStatementBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CacheProfilingThreshold")) {
                  original.setCacheProfilingThreshold(proposed.getCacheProfilingThreshold());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("CacheSize")) {
                  original.setCacheSize(proposed.getCacheSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("CacheType")) {
                  original.setCacheType(proposed.getCacheType());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("MaxParameterLength")) {
                  original.setMaxParameterLength(proposed.getMaxParameterLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ParameterLoggingEnabled")) {
                  original.setParameterLoggingEnabled(proposed.isParameterLoggingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ProfilingEnabled")) {
                  original.setProfilingEnabled(proposed.isProfilingEnabled());
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
            PreparedStatementBeanImpl copy = (PreparedStatementBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CacheProfilingThreshold")) && this.bean.isCacheProfilingThresholdSet()) {
               copy.setCacheProfilingThreshold(this.bean.getCacheProfilingThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheSize")) && this.bean.isCacheSizeSet()) {
               copy.setCacheSize(this.bean.getCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheType")) && this.bean.isCacheTypeSet()) {
               copy.setCacheType(this.bean.getCacheType());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxParameterLength")) && this.bean.isMaxParameterLengthSet()) {
               copy.setMaxParameterLength(this.bean.getMaxParameterLength());
            }

            if ((excludeProps == null || !excludeProps.contains("ParameterLoggingEnabled")) && this.bean.isParameterLoggingEnabledSet()) {
               copy.setParameterLoggingEnabled(this.bean.isParameterLoggingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ProfilingEnabled")) && this.bean.isProfilingEnabledSet()) {
               copy.setProfilingEnabled(this.bean.isProfilingEnabled());
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
