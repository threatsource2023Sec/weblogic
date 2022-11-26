package weblogic.diagnostics.image.descriptor;

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

public class MetricBeanImpl extends AbstractDescriptorBean implements MetricBean, Serializable {
   private String _AttributeName;
   private String _AttributeType;
   private String _AttributeValue;
   private static SchemaHelper2 _schemaHelper;

   public MetricBeanImpl() {
      this._initializeProperty(-1);
   }

   public MetricBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MetricBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getAttributeName() {
      return this._AttributeName;
   }

   public boolean isAttributeNameInherited() {
      return false;
   }

   public boolean isAttributeNameSet() {
      return this._isSet(0);
   }

   public void setAttributeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AttributeName;
      this._AttributeName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getAttributeValue() {
      return this._AttributeValue;
   }

   public boolean isAttributeValueInherited() {
      return false;
   }

   public boolean isAttributeValueSet() {
      return this._isSet(1);
   }

   public void setAttributeValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AttributeValue;
      this._AttributeValue = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getAttributeType() {
      return this._AttributeType;
   }

   public boolean isAttributeTypeInherited() {
      return false;
   }

   public boolean isAttributeTypeSet() {
      return this._isSet(2);
   }

   public void setAttributeType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AttributeType;
      this._AttributeType = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._AttributeName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._AttributeType = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._AttributeValue = null;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image/1.0/weblogic-diagnostics-image.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image";
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
            case 14:
               if (s.equals("attribute-name")) {
                  return 0;
               }

               if (s.equals("attribute-type")) {
                  return 2;
               }
               break;
            case 15:
               if (s.equals("attribute-value")) {
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
               return "attribute-name";
            case 1:
               return "attribute-value";
            case 2:
               return "attribute-type";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private MetricBeanImpl bean;

      protected Helper(MetricBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AttributeName";
            case 1:
               return "AttributeValue";
            case 2:
               return "AttributeType";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AttributeName")) {
            return 0;
         } else if (propName.equals("AttributeType")) {
            return 2;
         } else {
            return propName.equals("AttributeValue") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isAttributeNameSet()) {
               buf.append("AttributeName");
               buf.append(String.valueOf(this.bean.getAttributeName()));
            }

            if (this.bean.isAttributeTypeSet()) {
               buf.append("AttributeType");
               buf.append(String.valueOf(this.bean.getAttributeType()));
            }

            if (this.bean.isAttributeValueSet()) {
               buf.append("AttributeValue");
               buf.append(String.valueOf(this.bean.getAttributeValue()));
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
            MetricBeanImpl otherTyped = (MetricBeanImpl)other;
            this.computeDiff("AttributeName", this.bean.getAttributeName(), otherTyped.getAttributeName(), false);
            this.computeDiff("AttributeType", this.bean.getAttributeType(), otherTyped.getAttributeType(), false);
            this.computeDiff("AttributeValue", this.bean.getAttributeValue(), otherTyped.getAttributeValue(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MetricBeanImpl original = (MetricBeanImpl)event.getSourceBean();
            MetricBeanImpl proposed = (MetricBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AttributeName")) {
                  original.setAttributeName(proposed.getAttributeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("AttributeType")) {
                  original.setAttributeType(proposed.getAttributeType());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("AttributeValue")) {
                  original.setAttributeValue(proposed.getAttributeValue());
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
            MetricBeanImpl copy = (MetricBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AttributeName")) && this.bean.isAttributeNameSet()) {
               copy.setAttributeName(this.bean.getAttributeName());
            }

            if ((excludeProps == null || !excludeProps.contains("AttributeType")) && this.bean.isAttributeTypeSet()) {
               copy.setAttributeType(this.bean.getAttributeType());
            }

            if ((excludeProps == null || !excludeProps.contains("AttributeValue")) && this.bean.isAttributeValueSet()) {
               copy.setAttributeValue(this.bean.getAttributeValue());
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
