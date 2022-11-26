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

public class ConfigurableCacheFactoryConfigBeanImpl extends SettableBeanImpl implements ConfigurableCacheFactoryConfigBean, Serializable {
   private String _ClassName;
   private InitParamsBean _InitParams;
   private static SchemaHelper2 _schemaHelper;

   public ConfigurableCacheFactoryConfigBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConfigurableCacheFactoryConfigBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConfigurableCacheFactoryConfigBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getClassName() {
      return this._ClassName;
   }

   public boolean isClassNameInherited() {
      return false;
   }

   public boolean isClassNameSet() {
      return this._isSet(0);
   }

   public void setClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClassName;
      this._ClassName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public InitParamsBean getInitParams() {
      return this._InitParams;
   }

   public boolean isInitParamsInherited() {
      return false;
   }

   public boolean isInitParamsSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getInitParams());
   }

   public void setInitParams(InitParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      InitParamsBean _oldVal = this._InitParams;
      this._InitParams = param0;
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
      return super._isAnythingSet() || this.isInitParamsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._ClassName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._InitParams = new InitParamsBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._InitParams);
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("class-name")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("init-params")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new InitParamsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "class-name";
            case 1:
               return "init-params";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private ConfigurableCacheFactoryConfigBeanImpl bean;

      protected Helper(ConfigurableCacheFactoryConfigBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ClassName";
            case 1:
               return "InitParams";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClassName")) {
            return 0;
         } else {
            return propName.equals("InitParams") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getInitParams() != null) {
            iterators.add(new ArrayIterator(new InitParamsBean[]{this.bean.getInitParams()}));
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
            if (this.bean.isClassNameSet()) {
               buf.append("ClassName");
               buf.append(String.valueOf(this.bean.getClassName()));
            }

            childValue = this.computeChildHashValue(this.bean.getInitParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            ConfigurableCacheFactoryConfigBeanImpl otherTyped = (ConfigurableCacheFactoryConfigBeanImpl)other;
            this.computeDiff("ClassName", this.bean.getClassName(), otherTyped.getClassName(), false);
            this.computeSubDiff("InitParams", this.bean.getInitParams(), otherTyped.getInitParams());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConfigurableCacheFactoryConfigBeanImpl original = (ConfigurableCacheFactoryConfigBeanImpl)event.getSourceBean();
            ConfigurableCacheFactoryConfigBeanImpl proposed = (ConfigurableCacheFactoryConfigBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClassName")) {
                  original.setClassName(proposed.getClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("InitParams")) {
                  if (type == 2) {
                     original.setInitParams((InitParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getInitParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("InitParams", (DescriptorBean)original.getInitParams());
                  }

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
            ConfigurableCacheFactoryConfigBeanImpl copy = (ConfigurableCacheFactoryConfigBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClassName")) && this.bean.isClassNameSet()) {
               copy.setClassName(this.bean.getClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("InitParams")) && this.bean.isInitParamsSet() && !copy._isSet(1)) {
               Object o = this.bean.getInitParams();
               copy.setInitParams((InitParamsBean)null);
               copy.setInitParams(o == null ? null : (InitParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getInitParams(), clazz, annotation);
      }
   }
}
