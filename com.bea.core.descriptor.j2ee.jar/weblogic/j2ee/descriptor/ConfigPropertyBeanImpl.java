package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ConfigPropertyBeanImpl extends AbstractDescriptorBean implements ConfigPropertyBean, Serializable {
   private boolean _ConfigPropertyConfidential;
   private boolean _ConfigPropertyIgnore;
   private String _ConfigPropertyName;
   private boolean _ConfigPropertySupportsDynamicUpdates;
   private String _ConfigPropertyType;
   private String _ConfigPropertyValue;
   private String[] _Descriptions;
   private String _Id;
   private static SchemaHelper2 _schemaHelper;

   public ConfigPropertyBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConfigPropertyBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConfigPropertyBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(0);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(0)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDescriptions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDescriptions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDescription(String param0) {
      String[] _old = this.getDescriptions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDescriptions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Descriptions;
      this._Descriptions = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getConfigPropertyName() {
      return this._ConfigPropertyName;
   }

   public boolean isConfigPropertyNameInherited() {
      return false;
   }

   public boolean isConfigPropertyNameSet() {
      return this._isSet(1);
   }

   public void setConfigPropertyName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConfigPropertyName;
      this._ConfigPropertyName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getConfigPropertyType() {
      return this._ConfigPropertyType;
   }

   public boolean isConfigPropertyTypeInherited() {
      return false;
   }

   public boolean isConfigPropertyTypeSet() {
      return this._isSet(2);
   }

   public void setConfigPropertyType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConfigPropertyType;
      this._ConfigPropertyType = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getConfigPropertyValue() {
      return this._ConfigPropertyValue;
   }

   public boolean isConfigPropertyValueInherited() {
      return false;
   }

   public boolean isConfigPropertyValueSet() {
      return this._isSet(3);
   }

   public void setConfigPropertyValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConfigPropertyValue;
      this._ConfigPropertyValue = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isConfigPropertyIgnore() {
      return this._ConfigPropertyIgnore;
   }

   public boolean isConfigPropertyIgnoreInherited() {
      return false;
   }

   public boolean isConfigPropertyIgnoreSet() {
      return this._isSet(4);
   }

   public void setConfigPropertyIgnore(boolean param0) {
      boolean _oldVal = this._ConfigPropertyIgnore;
      this._ConfigPropertyIgnore = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isConfigPropertySupportsDynamicUpdates() {
      return this._ConfigPropertySupportsDynamicUpdates;
   }

   public boolean isConfigPropertySupportsDynamicUpdatesInherited() {
      return false;
   }

   public boolean isConfigPropertySupportsDynamicUpdatesSet() {
      return this._isSet(5);
   }

   public void setConfigPropertySupportsDynamicUpdates(boolean param0) {
      boolean _oldVal = this._ConfigPropertySupportsDynamicUpdates;
      this._ConfigPropertySupportsDynamicUpdates = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isConfigPropertyConfidential() {
      return this._ConfigPropertyConfidential;
   }

   public boolean isConfigPropertyConfidentialInherited() {
      return false;
   }

   public boolean isConfigPropertyConfidentialSet() {
      return this._isSet(6);
   }

   public void setConfigPropertyConfidential(boolean param0) {
      boolean _oldVal = this._ConfigPropertyConfidential;
      this._ConfigPropertyConfidential = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getConfigPropertyName();
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
         case 20:
            if (s.equals("config-property-name")) {
               return info.compareXpaths(this._getPropertyXpath("config-property-name"));
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._ConfigPropertyName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ConfigPropertyType = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ConfigPropertyValue = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._ConfigPropertyConfidential = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._ConfigPropertyIgnore = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._ConfigPropertySupportsDynamicUpdates = false;
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
            case 2:
               if (s.equals("id")) {
                  return 7;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("config-property-name")) {
                  return 1;
               }

               if (s.equals("config-property-type")) {
                  return 2;
               }
               break;
            case 21:
               if (s.equals("config-property-value")) {
                  return 3;
               }
               break;
            case 22:
               if (s.equals("config-property-ignore")) {
                  return 4;
               }
               break;
            case 28:
               if (s.equals("config-property-confidential")) {
                  return 6;
               }
               break;
            case 40:
               if (s.equals("config-property-supports-dynamic-updates")) {
                  return 5;
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
               return "description";
            case 1:
               return "config-property-name";
            case 2:
               return "config-property-type";
            case 3:
               return "config-property-value";
            case 4:
               return "config-property-ignore";
            case 5:
               return "config-property-supports-dynamic-updates";
            case 6:
               return "config-property-confidential";
            case 7:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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
         indices.add("config-property-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ConfigPropertyBeanImpl bean;

      protected Helper(ConfigPropertyBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "ConfigPropertyName";
            case 2:
               return "ConfigPropertyType";
            case 3:
               return "ConfigPropertyValue";
            case 4:
               return "ConfigPropertyIgnore";
            case 5:
               return "ConfigPropertySupportsDynamicUpdates";
            case 6:
               return "ConfigPropertyConfidential";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConfigPropertyName")) {
            return 1;
         } else if (propName.equals("ConfigPropertyType")) {
            return 2;
         } else if (propName.equals("ConfigPropertyValue")) {
            return 3;
         } else if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("ConfigPropertyConfidential")) {
            return 6;
         } else if (propName.equals("ConfigPropertyIgnore")) {
            return 4;
         } else {
            return propName.equals("ConfigPropertySupportsDynamicUpdates") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isConfigPropertyNameSet()) {
               buf.append("ConfigPropertyName");
               buf.append(String.valueOf(this.bean.getConfigPropertyName()));
            }

            if (this.bean.isConfigPropertyTypeSet()) {
               buf.append("ConfigPropertyType");
               buf.append(String.valueOf(this.bean.getConfigPropertyType()));
            }

            if (this.bean.isConfigPropertyValueSet()) {
               buf.append("ConfigPropertyValue");
               buf.append(String.valueOf(this.bean.getConfigPropertyValue()));
            }

            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isConfigPropertyConfidentialSet()) {
               buf.append("ConfigPropertyConfidential");
               buf.append(String.valueOf(this.bean.isConfigPropertyConfidential()));
            }

            if (this.bean.isConfigPropertyIgnoreSet()) {
               buf.append("ConfigPropertyIgnore");
               buf.append(String.valueOf(this.bean.isConfigPropertyIgnore()));
            }

            if (this.bean.isConfigPropertySupportsDynamicUpdatesSet()) {
               buf.append("ConfigPropertySupportsDynamicUpdates");
               buf.append(String.valueOf(this.bean.isConfigPropertySupportsDynamicUpdates()));
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
            ConfigPropertyBeanImpl otherTyped = (ConfigPropertyBeanImpl)other;
            this.computeDiff("ConfigPropertyName", this.bean.getConfigPropertyName(), otherTyped.getConfigPropertyName(), false);
            this.computeDiff("ConfigPropertyType", this.bean.getConfigPropertyType(), otherTyped.getConfigPropertyType(), false);
            this.computeDiff("ConfigPropertyValue", this.bean.getConfigPropertyValue(), otherTyped.getConfigPropertyValue(), false);
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("ConfigPropertyConfidential", this.bean.isConfigPropertyConfidential(), otherTyped.isConfigPropertyConfidential(), false);
            this.computeDiff("ConfigPropertyIgnore", this.bean.isConfigPropertyIgnore(), otherTyped.isConfigPropertyIgnore(), false);
            this.computeDiff("ConfigPropertySupportsDynamicUpdates", this.bean.isConfigPropertySupportsDynamicUpdates(), otherTyped.isConfigPropertySupportsDynamicUpdates(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConfigPropertyBeanImpl original = (ConfigPropertyBeanImpl)event.getSourceBean();
            ConfigPropertyBeanImpl proposed = (ConfigPropertyBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConfigPropertyName")) {
                  original.setConfigPropertyName(proposed.getConfigPropertyName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ConfigPropertyType")) {
                  original.setConfigPropertyType(proposed.getConfigPropertyType());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ConfigPropertyValue")) {
                  original.setConfigPropertyValue(proposed.getConfigPropertyValue());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Descriptions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDescription((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDescription((String)update.getRemovedObject());
                  }

                  if (original.getDescriptions() == null || original.getDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("ConfigPropertyConfidential")) {
                  original.setConfigPropertyConfidential(proposed.isConfigPropertyConfidential());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("ConfigPropertyIgnore")) {
                  original.setConfigPropertyIgnore(proposed.isConfigPropertyIgnore());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ConfigPropertySupportsDynamicUpdates")) {
                  original.setConfigPropertySupportsDynamicUpdates(proposed.isConfigPropertySupportsDynamicUpdates());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            ConfigPropertyBeanImpl copy = (ConfigPropertyBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConfigPropertyName")) && this.bean.isConfigPropertyNameSet()) {
               copy.setConfigPropertyName(this.bean.getConfigPropertyName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigPropertyType")) && this.bean.isConfigPropertyTypeSet()) {
               copy.setConfigPropertyType(this.bean.getConfigPropertyType());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigPropertyValue")) && this.bean.isConfigPropertyValueSet()) {
               copy.setConfigPropertyValue(this.bean.getConfigPropertyValue());
            }

            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               Object o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigPropertyConfidential")) && this.bean.isConfigPropertyConfidentialSet()) {
               copy.setConfigPropertyConfidential(this.bean.isConfigPropertyConfidential());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigPropertyIgnore")) && this.bean.isConfigPropertyIgnoreSet()) {
               copy.setConfigPropertyIgnore(this.bean.isConfigPropertyIgnore());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigPropertySupportsDynamicUpdates")) && this.bean.isConfigPropertySupportsDynamicUpdatesSet()) {
               copy.setConfigPropertySupportsDynamicUpdates(this.bean.isConfigPropertySupportsDynamicUpdates());
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
