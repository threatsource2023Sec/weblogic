package weblogic.coherence.app.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceApplicationBeanImpl extends SettableBeanImpl implements CoherenceApplicationBean, Serializable {
   private ApplicationLifecycleListenerBean _ApplicationLifecycleListener;
   private String _CacheConfigurationRef;
   private ConfigurableCacheFactoryConfigBean _ConfigurableCacheFactoryConfig;
   private String _PofConfigurationRef;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceApplicationBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public CoherenceApplicationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public CoherenceApplicationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public String getCacheConfigurationRef() {
      return this._CacheConfigurationRef;
   }

   public boolean isCacheConfigurationRefInherited() {
      return false;
   }

   public boolean isCacheConfigurationRefSet() {
      return this._isSet(0);
   }

   public void setCacheConfigurationRef(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CacheConfigurationRef;
      this._CacheConfigurationRef = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getPofConfigurationRef() {
      return this._PofConfigurationRef;
   }

   public boolean isPofConfigurationRefInherited() {
      return false;
   }

   public boolean isPofConfigurationRefSet() {
      return this._isSet(1);
   }

   public void setPofConfigurationRef(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PofConfigurationRef;
      this._PofConfigurationRef = param0;
      this._postSet(1, _oldVal, param0);
   }

   public ApplicationLifecycleListenerBean getApplicationLifecycleListener() {
      return this._ApplicationLifecycleListener;
   }

   public boolean isApplicationLifecycleListenerInherited() {
      return false;
   }

   public boolean isApplicationLifecycleListenerSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getApplicationLifecycleListener());
   }

   public void setApplicationLifecycleListener(ApplicationLifecycleListenerBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      ApplicationLifecycleListenerBean _oldVal = this._ApplicationLifecycleListener;
      this._ApplicationLifecycleListener = param0;
      this._postSet(2, _oldVal, param0);
   }

   public ConfigurableCacheFactoryConfigBean getConfigurableCacheFactoryConfig() {
      return this._ConfigurableCacheFactoryConfig;
   }

   public boolean isConfigurableCacheFactoryConfigInherited() {
      return false;
   }

   public boolean isConfigurableCacheFactoryConfigSet() {
      return this._isSet(3) || this._isAnythingSet((AbstractDescriptorBean)this.getConfigurableCacheFactoryConfig());
   }

   public void setConfigurableCacheFactoryConfig(ConfigurableCacheFactoryConfigBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 3)) {
         this._postCreate(_child);
      }

      ConfigurableCacheFactoryConfigBean _oldVal = this._ConfigurableCacheFactoryConfig;
      this._ConfigurableCacheFactoryConfig = param0;
      this._postSet(3, _oldVal, param0);
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
      return super._isAnythingSet() || this.isApplicationLifecycleListenerSet() || this.isConfigurableCacheFactoryConfigSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._ApplicationLifecycleListener = new ApplicationLifecycleListenerBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._ApplicationLifecycleListener);
               if (initOne) {
                  break;
               }
            case 0:
               this._CacheConfigurationRef = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ConfigurableCacheFactoryConfig = new ConfigurableCacheFactoryConfigBeanImpl(this, 3);
               this._postCreate((AbstractDescriptorBean)this._ConfigurableCacheFactoryConfig);
               if (initOne) {
                  break;
               }
            case 1:
               this._PofConfigurationRef = null;
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

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/coherence/coherence-application";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 21:
               if (s.equals("pof-configuration-ref")) {
                  return 1;
               }
               break;
            case 23:
               if (s.equals("cache-configuration-ref")) {
                  return 0;
               }
               break;
            case 30:
               if (s.equals("application-lifecycle-listener")) {
                  return 2;
               }
               break;
            case 33:
               if (s.equals("configurable-cache-factory-config")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new ApplicationLifecycleListenerBeanImpl.SchemaHelper2();
            case 3:
               return new ConfigurableCacheFactoryConfigBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "coherence-application";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "cache-configuration-ref";
            case 1:
               return "pof-configuration-ref";
            case 2:
               return "application-lifecycle-listener";
            case 3:
               return "configurable-cache-factory-config";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private CoherenceApplicationBeanImpl bean;

      protected Helper(CoherenceApplicationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "CacheConfigurationRef";
            case 1:
               return "PofConfigurationRef";
            case 2:
               return "ApplicationLifecycleListener";
            case 3:
               return "ConfigurableCacheFactoryConfig";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ApplicationLifecycleListener")) {
            return 2;
         } else if (propName.equals("CacheConfigurationRef")) {
            return 0;
         } else if (propName.equals("ConfigurableCacheFactoryConfig")) {
            return 3;
         } else {
            return propName.equals("PofConfigurationRef") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getApplicationLifecycleListener() != null) {
            iterators.add(new ArrayIterator(new ApplicationLifecycleListenerBean[]{this.bean.getApplicationLifecycleListener()}));
         }

         if (this.bean.getConfigurableCacheFactoryConfig() != null) {
            iterators.add(new ArrayIterator(new ConfigurableCacheFactoryConfigBean[]{this.bean.getConfigurableCacheFactoryConfig()}));
         }

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
            childValue = this.computeChildHashValue(this.bean.getApplicationLifecycleListener());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCacheConfigurationRefSet()) {
               buf.append("CacheConfigurationRef");
               buf.append(String.valueOf(this.bean.getCacheConfigurationRef()));
            }

            childValue = this.computeChildHashValue(this.bean.getConfigurableCacheFactoryConfig());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isPofConfigurationRefSet()) {
               buf.append("PofConfigurationRef");
               buf.append(String.valueOf(this.bean.getPofConfigurationRef()));
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
            CoherenceApplicationBeanImpl otherTyped = (CoherenceApplicationBeanImpl)other;
            this.computeSubDiff("ApplicationLifecycleListener", this.bean.getApplicationLifecycleListener(), otherTyped.getApplicationLifecycleListener());
            this.computeDiff("CacheConfigurationRef", this.bean.getCacheConfigurationRef(), otherTyped.getCacheConfigurationRef(), false);
            this.computeSubDiff("ConfigurableCacheFactoryConfig", this.bean.getConfigurableCacheFactoryConfig(), otherTyped.getConfigurableCacheFactoryConfig());
            this.computeDiff("PofConfigurationRef", this.bean.getPofConfigurationRef(), otherTyped.getPofConfigurationRef(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceApplicationBeanImpl original = (CoherenceApplicationBeanImpl)event.getSourceBean();
            CoherenceApplicationBeanImpl proposed = (CoherenceApplicationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ApplicationLifecycleListener")) {
                  if (type == 2) {
                     original.setApplicationLifecycleListener((ApplicationLifecycleListenerBean)this.createCopy((AbstractDescriptorBean)proposed.getApplicationLifecycleListener()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ApplicationLifecycleListener", (DescriptorBean)original.getApplicationLifecycleListener());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("CacheConfigurationRef")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ConfigurableCacheFactoryConfig")) {
                  if (type == 2) {
                     original.setConfigurableCacheFactoryConfig((ConfigurableCacheFactoryConfigBean)this.createCopy((AbstractDescriptorBean)proposed.getConfigurableCacheFactoryConfig()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ConfigurableCacheFactoryConfig", (DescriptorBean)original.getConfigurableCacheFactoryConfig());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("PofConfigurationRef")) {
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
            CoherenceApplicationBeanImpl copy = (CoherenceApplicationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ApplicationLifecycleListener")) && this.bean.isApplicationLifecycleListenerSet() && !copy._isSet(2)) {
               Object o = this.bean.getApplicationLifecycleListener();
               copy.setApplicationLifecycleListener((ApplicationLifecycleListenerBean)null);
               copy.setApplicationLifecycleListener(o == null ? null : (ApplicationLifecycleListenerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CacheConfigurationRef")) && this.bean.isCacheConfigurationRefSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigurableCacheFactoryConfig")) && this.bean.isConfigurableCacheFactoryConfigSet() && !copy._isSet(3)) {
               Object o = this.bean.getConfigurableCacheFactoryConfig();
               copy.setConfigurableCacheFactoryConfig((ConfigurableCacheFactoryConfigBean)null);
               copy.setConfigurableCacheFactoryConfig(o == null ? null : (ConfigurableCacheFactoryConfigBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PofConfigurationRef")) && this.bean.isPofConfigurationRefSet()) {
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
         this.inferSubTree(this.bean.getApplicationLifecycleListener(), clazz, annotation);
         this.inferSubTree(this.bean.getConfigurableCacheFactoryConfig(), clazz, annotation);
      }
   }
}
