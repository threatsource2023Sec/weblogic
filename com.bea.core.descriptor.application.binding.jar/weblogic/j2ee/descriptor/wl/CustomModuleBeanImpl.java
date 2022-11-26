package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class CustomModuleBeanImpl extends AbstractDescriptorBean implements CustomModuleBean, Serializable {
   private ConfigurationSupportBean _ConfigurationSupport;
   private String _ProviderName;
   private String _Uri;
   private static SchemaHelper2 _schemaHelper;

   public CustomModuleBeanImpl() {
      this._initializeProperty(-1);
   }

   public CustomModuleBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CustomModuleBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getUri() {
      return this._Uri;
   }

   public boolean isUriInherited() {
      return false;
   }

   public boolean isUriSet() {
      return this._isSet(0);
   }

   public void setUri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Uri;
      this._Uri = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getProviderName() {
      return this._ProviderName;
   }

   public boolean isProviderNameInherited() {
      return false;
   }

   public boolean isProviderNameSet() {
      return this._isSet(1);
   }

   public void setProviderName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ProviderName;
      this._ProviderName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public ConfigurationSupportBean getConfigurationSupport() {
      return this._ConfigurationSupport;
   }

   public boolean isConfigurationSupportInherited() {
      return false;
   }

   public boolean isConfigurationSupportSet() {
      return this._isSet(2);
   }

   public void setConfigurationSupport(ConfigurationSupportBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getConfigurationSupport() != null && param0 != this.getConfigurationSupport()) {
         throw new BeanAlreadyExistsException(this.getConfigurationSupport() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ConfigurationSupportBean _oldVal = this._ConfigurationSupport;
         this._ConfigurationSupport = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public ConfigurationSupportBean createConfigurationSupport() {
      ConfigurationSupportBeanImpl _val = new ConfigurationSupportBeanImpl(this, -1);

      try {
         this.setConfigurationSupport(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConfigurationSupport(ConfigurationSupportBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ConfigurationSupport;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setConfigurationSupport((ConfigurationSupportBean)null);
               this._unSet(2);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public Object _getKey() {
      return this.getUri();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 3:
            if (s.equals("uri")) {
               return info.compareXpaths(this._getPropertyXpath("uri"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._ConfigurationSupport = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ProviderName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Uri = null;
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
            case 3:
               if (s.equals("uri")) {
                  return 0;
               }
               break;
            case 13:
               if (s.equals("provider-name")) {
                  return 1;
               }
               break;
            case 21:
               if (s.equals("configuration-support")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new ConfigurationSupportBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "uri";
            case 1:
               return "provider-name";
            case 2:
               return "configuration-support";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("uri");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private CustomModuleBeanImpl bean;

      protected Helper(CustomModuleBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Uri";
            case 1:
               return "ProviderName";
            case 2:
               return "ConfigurationSupport";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConfigurationSupport")) {
            return 2;
         } else if (propName.equals("ProviderName")) {
            return 1;
         } else {
            return propName.equals("Uri") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getConfigurationSupport() != null) {
            iterators.add(new ArrayIterator(new ConfigurationSupportBean[]{this.bean.getConfigurationSupport()}));
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
            childValue = this.computeChildHashValue(this.bean.getConfigurationSupport());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isProviderNameSet()) {
               buf.append("ProviderName");
               buf.append(String.valueOf(this.bean.getProviderName()));
            }

            if (this.bean.isUriSet()) {
               buf.append("Uri");
               buf.append(String.valueOf(this.bean.getUri()));
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
            CustomModuleBeanImpl otherTyped = (CustomModuleBeanImpl)other;
            this.computeChildDiff("ConfigurationSupport", this.bean.getConfigurationSupport(), otherTyped.getConfigurationSupport(), false);
            this.computeDiff("ProviderName", this.bean.getProviderName(), otherTyped.getProviderName(), false);
            this.computeDiff("Uri", this.bean.getUri(), otherTyped.getUri(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CustomModuleBeanImpl original = (CustomModuleBeanImpl)event.getSourceBean();
            CustomModuleBeanImpl proposed = (CustomModuleBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConfigurationSupport")) {
                  if (type == 2) {
                     original.setConfigurationSupport((ConfigurationSupportBean)this.createCopy((AbstractDescriptorBean)proposed.getConfigurationSupport()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ConfigurationSupport", (DescriptorBean)original.getConfigurationSupport());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ProviderName")) {
                  original.setProviderName(proposed.getProviderName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Uri")) {
                  original.setUri(proposed.getUri());
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
            CustomModuleBeanImpl copy = (CustomModuleBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConfigurationSupport")) && this.bean.isConfigurationSupportSet() && !copy._isSet(2)) {
               Object o = this.bean.getConfigurationSupport();
               copy.setConfigurationSupport((ConfigurationSupportBean)null);
               copy.setConfigurationSupport(o == null ? null : (ConfigurationSupportBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ProviderName")) && this.bean.isProviderNameSet()) {
               copy.setProviderName(this.bean.getProviderName());
            }

            if ((excludeProps == null || !excludeProps.contains("Uri")) && this.bean.isUriSet()) {
               copy.setUri(this.bean.getUri());
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
         this.inferSubTree(this.bean.getConfigurationSupport(), clazz, annotation);
      }
   }
}
