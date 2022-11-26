package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class SNMPAttributeChangeMBeanImpl extends SNMPTrapSourceMBeanImpl implements SNMPAttributeChangeMBean, Serializable {
   private String _AttributeMBeanName;
   private String _AttributeMBeanType;
   private String _AttributeName;
   private static SchemaHelper2 _schemaHelper;

   public SNMPAttributeChangeMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SNMPAttributeChangeMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SNMPAttributeChangeMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getAttributeMBeanType() {
      return this._AttributeMBeanType;
   }

   public boolean isAttributeMBeanTypeInherited() {
      return false;
   }

   public boolean isAttributeMBeanTypeSet() {
      return this._isSet(11);
   }

   public void setAttributeMBeanType(String param0) throws InvalidAttributeValueException, ConfigurationException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("AttributeMBeanType", param0);
      String _oldVal = this._AttributeMBeanType;
      this._AttributeMBeanType = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getAttributeMBeanName() {
      return this._AttributeMBeanName;
   }

   public boolean isAttributeMBeanNameInherited() {
      return false;
   }

   public boolean isAttributeMBeanNameSet() {
      return this._isSet(12);
   }

   public void setAttributeMBeanName(String param0) throws InvalidAttributeValueException, ConfigurationException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AttributeMBeanName;
      this._AttributeMBeanName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getAttributeName() {
      return this._AttributeName;
   }

   public boolean isAttributeNameInherited() {
      return false;
   }

   public boolean isAttributeNameSet() {
      return this._isSet(13);
   }

   public void setAttributeName(String param0) throws InvalidAttributeValueException, ConfigurationException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("AttributeName", param0);
      String _oldVal = this._AttributeName;
      this._AttributeName = param0;
      this._postSet(13, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      SNMPValidator.validateAttributeChangeMBean(this);
      LegalChecks.checkIsSet("AttributeMBeanType", this.isAttributeMBeanTypeSet());
      LegalChecks.checkIsSet("AttributeName", this.isAttributeNameSet());
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._AttributeMBeanName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._AttributeMBeanType = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._AttributeName = null;
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
      return "SNMPAttributeChange";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AttributeMBeanName")) {
         oldVal = this._AttributeMBeanName;
         this._AttributeMBeanName = (String)v;
         this._postSet(12, oldVal, this._AttributeMBeanName);
      } else if (name.equals("AttributeMBeanType")) {
         oldVal = this._AttributeMBeanType;
         this._AttributeMBeanType = (String)v;
         this._postSet(11, oldVal, this._AttributeMBeanType);
      } else if (name.equals("AttributeName")) {
         oldVal = this._AttributeName;
         this._AttributeName = (String)v;
         this._postSet(13, oldVal, this._AttributeName);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("AttributeMBeanName")) {
         return this._AttributeMBeanName;
      } else if (name.equals("AttributeMBeanType")) {
         return this._AttributeMBeanType;
      } else {
         return name.equals("AttributeName") ? this._AttributeName : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends SNMPTrapSourceMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 14:
               if (s.equals("attribute-name")) {
                  return 13;
               }
               break;
            case 20:
               if (s.equals("attributem-bean-name")) {
                  return 12;
               }

               if (s.equals("attributem-bean-type")) {
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
            case 11:
               return "attributem-bean-type";
            case 12:
               return "attributem-bean-name";
            case 13:
               return "attribute-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
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

   protected static class Helper extends SNMPTrapSourceMBeanImpl.Helper {
      private SNMPAttributeChangeMBeanImpl bean;

      protected Helper(SNMPAttributeChangeMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 11:
               return "AttributeMBeanType";
            case 12:
               return "AttributeMBeanName";
            case 13:
               return "AttributeName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AttributeMBeanName")) {
            return 12;
         } else if (propName.equals("AttributeMBeanType")) {
            return 11;
         } else {
            return propName.equals("AttributeName") ? 13 : super.getPropertyIndex(propName);
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
            if (this.bean.isAttributeMBeanNameSet()) {
               buf.append("AttributeMBeanName");
               buf.append(String.valueOf(this.bean.getAttributeMBeanName()));
            }

            if (this.bean.isAttributeMBeanTypeSet()) {
               buf.append("AttributeMBeanType");
               buf.append(String.valueOf(this.bean.getAttributeMBeanType()));
            }

            if (this.bean.isAttributeNameSet()) {
               buf.append("AttributeName");
               buf.append(String.valueOf(this.bean.getAttributeName()));
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
            SNMPAttributeChangeMBeanImpl otherTyped = (SNMPAttributeChangeMBeanImpl)other;
            this.computeDiff("AttributeMBeanName", this.bean.getAttributeMBeanName(), otherTyped.getAttributeMBeanName(), true);
            this.computeDiff("AttributeMBeanType", this.bean.getAttributeMBeanType(), otherTyped.getAttributeMBeanType(), true);
            this.computeDiff("AttributeName", this.bean.getAttributeName(), otherTyped.getAttributeName(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SNMPAttributeChangeMBeanImpl original = (SNMPAttributeChangeMBeanImpl)event.getSourceBean();
            SNMPAttributeChangeMBeanImpl proposed = (SNMPAttributeChangeMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AttributeMBeanName")) {
                  original.setAttributeMBeanName(proposed.getAttributeMBeanName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("AttributeMBeanType")) {
                  original.setAttributeMBeanType(proposed.getAttributeMBeanType());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("AttributeName")) {
                  original.setAttributeName(proposed.getAttributeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            SNMPAttributeChangeMBeanImpl copy = (SNMPAttributeChangeMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AttributeMBeanName")) && this.bean.isAttributeMBeanNameSet()) {
               copy.setAttributeMBeanName(this.bean.getAttributeMBeanName());
            }

            if ((excludeProps == null || !excludeProps.contains("AttributeMBeanType")) && this.bean.isAttributeMBeanTypeSet()) {
               copy.setAttributeMBeanType(this.bean.getAttributeMBeanType());
            }

            if ((excludeProps == null || !excludeProps.contains("AttributeName")) && this.bean.isAttributeNameSet()) {
               copy.setAttributeName(this.bean.getAttributeName());
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
