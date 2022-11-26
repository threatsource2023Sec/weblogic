package weblogic.management.configuration;

import com.bea.logging.LoggingConfigValidator;
import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.diagnostics.debug.DebugHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class PartitionLogMBeanImpl extends ConfigurationMBeanImpl implements PartitionLogMBean, Serializable {
   private String[] _EnabledServerDebugAttributes;
   private Properties _PlatformLoggerLevels;
   private static SchemaHelper2 _schemaHelper;

   public PartitionLogMBeanImpl() {
      this._initializeProperty(-1);
   }

   public PartitionLogMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PartitionLogMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public Properties getPlatformLoggerLevels() {
      return this._PlatformLoggerLevels;
   }

   public String getPlatformLoggerLevelsAsString() {
      return StringHelper.objectToString(this.getPlatformLoggerLevels());
   }

   public boolean isPlatformLoggerLevelsInherited() {
      return false;
   }

   public boolean isPlatformLoggerLevelsSet() {
      return this._isSet(10);
   }

   public void setPlatformLoggerLevelsAsString(String param0) {
      try {
         this.setPlatformLoggerLevels(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setPlatformLoggerLevels(Properties param0) {
      LoggingConfigValidator.validatePlatformLoggerLevels(param0);
      Properties _oldVal = this._PlatformLoggerLevels;
      this._PlatformLoggerLevels = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String[] getEnabledServerDebugAttributes() {
      return this._EnabledServerDebugAttributes;
   }

   public boolean isEnabledServerDebugAttributesInherited() {
      return false;
   }

   public boolean isEnabledServerDebugAttributesSet() {
      return this._isSet(11);
   }

   public void setEnabledServerDebugAttributes(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      DebugHelper.validatePartitionDebugAttributes(param0);
      String[] _oldVal = this._EnabledServerDebugAttributes;
      this._EnabledServerDebugAttributes = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean addEnabledServerDebugAttribute(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(11)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getEnabledServerDebugAttributes(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setEnabledServerDebugAttributes(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removeEnabledServerDebugAttribute(String param0) {
      String[] _old = this.getEnabledServerDebugAttributes();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setEnabledServerDebugAttributes(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._EnabledServerDebugAttributes = new String[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._PlatformLoggerLevels = null;
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
      return "PartitionLog";
   }

   public void putValue(String name, Object v) {
      if (name.equals("EnabledServerDebugAttributes")) {
         String[] oldVal = this._EnabledServerDebugAttributes;
         this._EnabledServerDebugAttributes = (String[])((String[])v);
         this._postSet(11, oldVal, this._EnabledServerDebugAttributes);
      } else if (name.equals("PlatformLoggerLevels")) {
         Properties oldVal = this._PlatformLoggerLevels;
         this._PlatformLoggerLevels = (Properties)v;
         this._postSet(10, oldVal, this._PlatformLoggerLevels);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("EnabledServerDebugAttributes")) {
         return this._EnabledServerDebugAttributes;
      } else {
         return name.equals("PlatformLoggerLevels") ? this._PlatformLoggerLevels : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 22:
               if (s.equals("platform-logger-levels")) {
                  return 10;
               }
               break;
            case 30:
               if (s.equals("enabled-server-debug-attribute")) {
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
               return "platform-logger-levels";
            case 11:
               return "enabled-server-debug-attribute";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 11:
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
      private PartitionLogMBeanImpl bean;

      protected Helper(PartitionLogMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "PlatformLoggerLevels";
            case 11:
               return "EnabledServerDebugAttributes";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EnabledServerDebugAttributes")) {
            return 11;
         } else {
            return propName.equals("PlatformLoggerLevels") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isEnabledServerDebugAttributesSet()) {
               buf.append("EnabledServerDebugAttributes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getEnabledServerDebugAttributes())));
            }

            if (this.bean.isPlatformLoggerLevelsSet()) {
               buf.append("PlatformLoggerLevels");
               buf.append(String.valueOf(this.bean.getPlatformLoggerLevels()));
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
            PartitionLogMBeanImpl otherTyped = (PartitionLogMBeanImpl)other;
            this.computeDiff("EnabledServerDebugAttributes", this.bean.getEnabledServerDebugAttributes(), otherTyped.getEnabledServerDebugAttributes(), true);
            this.computeDiff("PlatformLoggerLevels", this.bean.getPlatformLoggerLevels(), otherTyped.getPlatformLoggerLevels(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PartitionLogMBeanImpl original = (PartitionLogMBeanImpl)event.getSourceBean();
            PartitionLogMBeanImpl proposed = (PartitionLogMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EnabledServerDebugAttributes")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addEnabledServerDebugAttribute((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEnabledServerDebugAttribute((String)update.getRemovedObject());
                  }

                  if (original.getEnabledServerDebugAttributes() == null || original.getEnabledServerDebugAttributes().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("PlatformLoggerLevels")) {
                  original.setPlatformLoggerLevels(proposed.getPlatformLoggerLevels() == null ? null : (Properties)proposed.getPlatformLoggerLevels().clone());
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
            PartitionLogMBeanImpl copy = (PartitionLogMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EnabledServerDebugAttributes")) && this.bean.isEnabledServerDebugAttributesSet()) {
               Object o = this.bean.getEnabledServerDebugAttributes();
               copy.setEnabledServerDebugAttributes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("PlatformLoggerLevels")) && this.bean.isPlatformLoggerLevelsSet()) {
               copy.setPlatformLoggerLevels(this.bean.getPlatformLoggerLevels());
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
