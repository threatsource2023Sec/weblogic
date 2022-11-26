package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.diagnostics.harvester.Validators;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WLDFHarvestedTypeBeanImpl extends WLDFBeanImpl implements WLDFHarvestedTypeBean, Serializable {
   private boolean _Enabled;
   private String[] _HarvestedAttributes;
   private String[] _HarvestedInstances;
   private boolean _KnownType;
   private String _Name;
   private String _Namespace;
   private static SchemaHelper2 _schemaHelper;

   public WLDFHarvestedTypeBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFHarvestedTypeBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFHarvestedTypeBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("Name", param0);
      Validators.validateConfiguredType(param0);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(2);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isKnownType() {
      return this._KnownType;
   }

   public boolean isKnownTypeInherited() {
      return false;
   }

   public boolean isKnownTypeSet() {
      return this._isSet(3);
   }

   public void setKnownType(boolean param0) {
      boolean _oldVal = this._KnownType;
      this._KnownType = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String[] getHarvestedAttributes() {
      return this._HarvestedAttributes;
   }

   public boolean isHarvestedAttributesInherited() {
      return false;
   }

   public boolean isHarvestedAttributesSet() {
      return this._isSet(4);
   }

   public void setHarvestedAttributes(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._HarvestedAttributes;
      this._HarvestedAttributes = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String[] getHarvestedInstances() {
      return this._HarvestedInstances;
   }

   public boolean isHarvestedInstancesInherited() {
      return false;
   }

   public boolean isHarvestedInstancesSet() {
      return this._isSet(5);
   }

   public void setHarvestedInstances(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      Validators.validateConfiguredInstances(param0);
      String[] _oldVal = this._HarvestedInstances;
      this._HarvestedInstances = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getNamespace() {
      return this._Namespace;
   }

   public boolean isNamespaceInherited() {
      return false;
   }

   public boolean isNamespaceSet() {
      return this._isSet(6);
   }

   public void setNamespace(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"ServerRuntime", "DomainRuntime"};
      param0 = LegalChecks.checkInEnum("Namespace", param0, _set);
      LegalChecks.checkNonEmptyString("Namespace", param0);
      LegalChecks.checkNonNull("Namespace", param0);
      String _oldVal = this._Namespace;
      this._Namespace = param0;
      this._postSet(6, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      Validators.validateHarvestedTypeBean(this);
      Validators.validateConfiguredAttributes(this);
      LegalChecks.checkIsSet("Name", this.isNameSet());
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._HarvestedAttributes = new String[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._HarvestedInstances = new String[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._Namespace = "ServerRuntime";
               if (initOne) {
                  break;
               }
            case 2:
               this._Enabled = true;
               if (initOne) {
                  break;
               }
            case 3:
               this._KnownType = false;
               if (initOne) {
                  break;
               }
            case 1:
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/2.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static void validateGeneration() {
      try {
         String[] _set = new String[]{"ServerRuntime", "DomainRuntime"};
         LegalChecks.checkInEnum("Namespace", "ServerRuntime", _set);
      } catch (IllegalArgumentException var3) {
         throw new DescriptorValidateException("Default value for a property  should be one of the legal values. Refer annotation legalValues on property Namespace in WLDFHarvestedTypeBean" + var3.getMessage());
      }

      try {
         LegalChecks.checkNonNull("Namespace", "ServerRuntime");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property Namespace in WLDFHarvestedTypeBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonEmptyString("Namespace", "ServerRuntime");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is zero-length. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-zero-length value on @default annotation. Refer annotation legalZeroLength on property Namespace in WLDFHarvestedTypeBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends WLDFBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
            case 5:
            case 6:
            case 8:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            default:
               break;
            case 7:
               if (s.equals("enabled")) {
                  return 2;
               }
               break;
            case 9:
               if (s.equals("namespace")) {
                  return 6;
               }
               break;
            case 10:
               if (s.equals("known-type")) {
                  return 3;
               }
               break;
            case 18:
               if (s.equals("harvested-instance")) {
                  return 5;
               }
               break;
            case 19:
               if (s.equals("harvested-attribute")) {
                  return 4;
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
               return "name";
            case 1:
            default:
               return super.getElementName(propIndex);
            case 2:
               return "enabled";
            case 3:
               return "known-type";
            case 4:
               return "harvested-attribute";
            case 5:
               return "harvested-instance";
            case 6:
               return "namespace";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends WLDFBeanImpl.Helper {
      private WLDFHarvestedTypeBeanImpl bean;

      protected Helper(WLDFHarvestedTypeBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
            default:
               return super.getPropertyName(propIndex);
            case 2:
               return "Enabled";
            case 3:
               return "KnownType";
            case 4:
               return "HarvestedAttributes";
            case 5:
               return "HarvestedInstances";
            case 6:
               return "Namespace";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("HarvestedAttributes")) {
            return 4;
         } else if (propName.equals("HarvestedInstances")) {
            return 5;
         } else if (propName.equals("Name")) {
            return 0;
         } else if (propName.equals("Namespace")) {
            return 6;
         } else if (propName.equals("Enabled")) {
            return 2;
         } else {
            return propName.equals("KnownType") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isHarvestedAttributesSet()) {
               buf.append("HarvestedAttributes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getHarvestedAttributes())));
            }

            if (this.bean.isHarvestedInstancesSet()) {
               buf.append("HarvestedInstances");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getHarvestedInstances())));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNamespaceSet()) {
               buf.append("Namespace");
               buf.append(String.valueOf(this.bean.getNamespace()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
            }

            if (this.bean.isKnownTypeSet()) {
               buf.append("KnownType");
               buf.append(String.valueOf(this.bean.isKnownType()));
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
            WLDFHarvestedTypeBeanImpl otherTyped = (WLDFHarvestedTypeBeanImpl)other;
            this.computeDiff("HarvestedAttributes", this.bean.getHarvestedAttributes(), otherTyped.getHarvestedAttributes(), true);
            this.computeDiff("HarvestedInstances", this.bean.getHarvestedInstances(), otherTyped.getHarvestedInstances(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Namespace", this.bean.getNamespace(), otherTyped.getNamespace(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
            this.computeDiff("KnownType", this.bean.isKnownType(), otherTyped.isKnownType(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFHarvestedTypeBeanImpl original = (WLDFHarvestedTypeBeanImpl)event.getSourceBean();
            WLDFHarvestedTypeBeanImpl proposed = (WLDFHarvestedTypeBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("HarvestedAttributes")) {
                  original.setHarvestedAttributes(proposed.getHarvestedAttributes());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("HarvestedInstances")) {
                  original.setHarvestedInstances(proposed.getHarvestedInstances());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Namespace")) {
                  original.setNamespace(proposed.getNamespace());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("KnownType")) {
                  original.setKnownType(proposed.isKnownType());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            WLDFHarvestedTypeBeanImpl copy = (WLDFHarvestedTypeBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("HarvestedAttributes")) && this.bean.isHarvestedAttributesSet()) {
               o = this.bean.getHarvestedAttributes();
               copy.setHarvestedAttributes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("HarvestedInstances")) && this.bean.isHarvestedInstancesSet()) {
               o = this.bean.getHarvestedInstances();
               copy.setHarvestedInstances(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Namespace")) && this.bean.isNamespaceSet()) {
               copy.setNamespace(this.bean.getNamespace());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("KnownType")) && this.bean.isKnownTypeSet()) {
               copy.setKnownType(this.bean.isKnownType());
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
