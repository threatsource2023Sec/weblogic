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

public class ConfigurationSupportBeanImpl extends AbstractDescriptorBean implements ConfigurationSupportBean, Serializable {
   private String _BaseNamespace;
   private String _BasePackageName;
   private String _BaseRootElement;
   private String _BaseUri;
   private String _ConfigNamespace;
   private String _ConfigPackageName;
   private String _ConfigRootElement;
   private String _ConfigUri;
   private static SchemaHelper2 _schemaHelper;

   public ConfigurationSupportBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConfigurationSupportBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConfigurationSupportBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getBaseRootElement() {
      return this._BaseRootElement;
   }

   public boolean isBaseRootElementInherited() {
      return false;
   }

   public boolean isBaseRootElementSet() {
      return this._isSet(0);
   }

   public void setBaseRootElement(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BaseRootElement;
      this._BaseRootElement = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getConfigRootElement() {
      return this._ConfigRootElement;
   }

   public boolean isConfigRootElementInherited() {
      return false;
   }

   public boolean isConfigRootElementSet() {
      return this._isSet(1);
   }

   public void setConfigRootElement(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConfigRootElement;
      this._ConfigRootElement = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getBaseNamespace() {
      return this._BaseNamespace;
   }

   public boolean isBaseNamespaceInherited() {
      return false;
   }

   public boolean isBaseNamespaceSet() {
      return this._isSet(2);
   }

   public void setBaseNamespace(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BaseNamespace;
      this._BaseNamespace = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getConfigNamespace() {
      return this._ConfigNamespace;
   }

   public boolean isConfigNamespaceInherited() {
      return false;
   }

   public boolean isConfigNamespaceSet() {
      return this._isSet(3);
   }

   public void setConfigNamespace(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConfigNamespace;
      this._ConfigNamespace = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getBaseUri() {
      return this._BaseUri;
   }

   public boolean isBaseUriInherited() {
      return false;
   }

   public boolean isBaseUriSet() {
      return this._isSet(4);
   }

   public void setBaseUri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BaseUri;
      this._BaseUri = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getConfigUri() {
      return this._ConfigUri;
   }

   public boolean isConfigUriInherited() {
      return false;
   }

   public boolean isConfigUriSet() {
      return this._isSet(5);
   }

   public void setConfigUri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConfigUri;
      this._ConfigUri = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getBasePackageName() {
      return this._BasePackageName;
   }

   public boolean isBasePackageNameInherited() {
      return false;
   }

   public boolean isBasePackageNameSet() {
      return this._isSet(6);
   }

   public void setBasePackageName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BasePackageName;
      this._BasePackageName = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getConfigPackageName() {
      return this._ConfigPackageName;
   }

   public boolean isConfigPackageNameInherited() {
      return false;
   }

   public boolean isConfigPackageNameSet() {
      return this._isSet(7);
   }

   public void setConfigPackageName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConfigPackageName;
      this._ConfigPackageName = param0;
      this._postSet(7, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._BaseNamespace = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._BasePackageName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._BaseRootElement = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._BaseUri = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ConfigNamespace = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._ConfigPackageName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ConfigRootElement = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._ConfigUri = null;
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
            case 8:
               if (s.equals("base-uri")) {
                  return 4;
               }
            case 9:
            case 11:
            case 12:
            case 13:
            case 15:
            case 18:
            default:
               break;
            case 10:
               if (s.equals("config-uri")) {
                  return 5;
               }
               break;
            case 14:
               if (s.equals("base-namespace")) {
                  return 2;
               }
               break;
            case 16:
               if (s.equals("config-namespace")) {
                  return 3;
               }
               break;
            case 17:
               if (s.equals("base-package-name")) {
                  return 6;
               }

               if (s.equals("base-root-element")) {
                  return 0;
               }
               break;
            case 19:
               if (s.equals("config-package-name")) {
                  return 7;
               }

               if (s.equals("config-root-element")) {
                  return 1;
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
               return "base-root-element";
            case 1:
               return "config-root-element";
            case 2:
               return "base-namespace";
            case 3:
               return "config-namespace";
            case 4:
               return "base-uri";
            case 5:
               return "config-uri";
            case 6:
               return "base-package-name";
            case 7:
               return "config-package-name";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ConfigurationSupportBeanImpl bean;

      protected Helper(ConfigurationSupportBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "BaseRootElement";
            case 1:
               return "ConfigRootElement";
            case 2:
               return "BaseNamespace";
            case 3:
               return "ConfigNamespace";
            case 4:
               return "BaseUri";
            case 5:
               return "ConfigUri";
            case 6:
               return "BasePackageName";
            case 7:
               return "ConfigPackageName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BaseNamespace")) {
            return 2;
         } else if (propName.equals("BasePackageName")) {
            return 6;
         } else if (propName.equals("BaseRootElement")) {
            return 0;
         } else if (propName.equals("BaseUri")) {
            return 4;
         } else if (propName.equals("ConfigNamespace")) {
            return 3;
         } else if (propName.equals("ConfigPackageName")) {
            return 7;
         } else if (propName.equals("ConfigRootElement")) {
            return 1;
         } else {
            return propName.equals("ConfigUri") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isBaseNamespaceSet()) {
               buf.append("BaseNamespace");
               buf.append(String.valueOf(this.bean.getBaseNamespace()));
            }

            if (this.bean.isBasePackageNameSet()) {
               buf.append("BasePackageName");
               buf.append(String.valueOf(this.bean.getBasePackageName()));
            }

            if (this.bean.isBaseRootElementSet()) {
               buf.append("BaseRootElement");
               buf.append(String.valueOf(this.bean.getBaseRootElement()));
            }

            if (this.bean.isBaseUriSet()) {
               buf.append("BaseUri");
               buf.append(String.valueOf(this.bean.getBaseUri()));
            }

            if (this.bean.isConfigNamespaceSet()) {
               buf.append("ConfigNamespace");
               buf.append(String.valueOf(this.bean.getConfigNamespace()));
            }

            if (this.bean.isConfigPackageNameSet()) {
               buf.append("ConfigPackageName");
               buf.append(String.valueOf(this.bean.getConfigPackageName()));
            }

            if (this.bean.isConfigRootElementSet()) {
               buf.append("ConfigRootElement");
               buf.append(String.valueOf(this.bean.getConfigRootElement()));
            }

            if (this.bean.isConfigUriSet()) {
               buf.append("ConfigUri");
               buf.append(String.valueOf(this.bean.getConfigUri()));
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
            ConfigurationSupportBeanImpl otherTyped = (ConfigurationSupportBeanImpl)other;
            this.computeDiff("BaseNamespace", this.bean.getBaseNamespace(), otherTyped.getBaseNamespace(), false);
            this.computeDiff("BasePackageName", this.bean.getBasePackageName(), otherTyped.getBasePackageName(), false);
            this.computeDiff("BaseRootElement", this.bean.getBaseRootElement(), otherTyped.getBaseRootElement(), false);
            this.computeDiff("BaseUri", this.bean.getBaseUri(), otherTyped.getBaseUri(), false);
            this.computeDiff("ConfigNamespace", this.bean.getConfigNamespace(), otherTyped.getConfigNamespace(), false);
            this.computeDiff("ConfigPackageName", this.bean.getConfigPackageName(), otherTyped.getConfigPackageName(), false);
            this.computeDiff("ConfigRootElement", this.bean.getConfigRootElement(), otherTyped.getConfigRootElement(), false);
            this.computeDiff("ConfigUri", this.bean.getConfigUri(), otherTyped.getConfigUri(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConfigurationSupportBeanImpl original = (ConfigurationSupportBeanImpl)event.getSourceBean();
            ConfigurationSupportBeanImpl proposed = (ConfigurationSupportBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BaseNamespace")) {
                  original.setBaseNamespace(proposed.getBaseNamespace());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("BasePackageName")) {
                  original.setBasePackageName(proposed.getBasePackageName());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("BaseRootElement")) {
                  original.setBaseRootElement(proposed.getBaseRootElement());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("BaseUri")) {
                  original.setBaseUri(proposed.getBaseUri());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ConfigNamespace")) {
                  original.setConfigNamespace(proposed.getConfigNamespace());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ConfigPackageName")) {
                  original.setConfigPackageName(proposed.getConfigPackageName());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("ConfigRootElement")) {
                  original.setConfigRootElement(proposed.getConfigRootElement());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ConfigUri")) {
                  original.setConfigUri(proposed.getConfigUri());
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
            ConfigurationSupportBeanImpl copy = (ConfigurationSupportBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BaseNamespace")) && this.bean.isBaseNamespaceSet()) {
               copy.setBaseNamespace(this.bean.getBaseNamespace());
            }

            if ((excludeProps == null || !excludeProps.contains("BasePackageName")) && this.bean.isBasePackageNameSet()) {
               copy.setBasePackageName(this.bean.getBasePackageName());
            }

            if ((excludeProps == null || !excludeProps.contains("BaseRootElement")) && this.bean.isBaseRootElementSet()) {
               copy.setBaseRootElement(this.bean.getBaseRootElement());
            }

            if ((excludeProps == null || !excludeProps.contains("BaseUri")) && this.bean.isBaseUriSet()) {
               copy.setBaseUri(this.bean.getBaseUri());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigNamespace")) && this.bean.isConfigNamespaceSet()) {
               copy.setConfigNamespace(this.bean.getConfigNamespace());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigPackageName")) && this.bean.isConfigPackageNameSet()) {
               copy.setConfigPackageName(this.bean.getConfigPackageName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigRootElement")) && this.bean.isConfigRootElementSet()) {
               copy.setConfigRootElement(this.bean.getConfigRootElement());
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigUri")) && this.bean.isConfigUriSet()) {
               copy.setConfigUri(this.bean.getConfigUri());
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
