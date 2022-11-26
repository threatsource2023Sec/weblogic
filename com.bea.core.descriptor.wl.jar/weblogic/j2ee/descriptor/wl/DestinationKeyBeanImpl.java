package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.JMSModuleValidator;
import weblogic.utils.collections.CombinedIterator;

public class DestinationKeyBeanImpl extends NamedEntityBeanImpl implements DestinationKeyBean, Serializable {
   private String _KeyType;
   private String _Property;
   private String _SortOrder;
   private static SchemaHelper2 _schemaHelper;

   public DestinationKeyBeanImpl() {
      this._initializeProperty(-1);
   }

   public DestinationKeyBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DestinationKeyBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getProperty() {
      return this._Property;
   }

   public boolean isPropertyInherited() {
      return false;
   }

   public boolean isPropertySet() {
      return this._isSet(3);
   }

   public void setProperty(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Property;
      this._Property = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getKeyType() {
      return this._KeyType;
   }

   public boolean isKeyTypeInherited() {
      return false;
   }

   public boolean isKeyTypeSet() {
      return this._isSet(4);
   }

   public void setKeyType(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Boolean", "Byte", "Short", "Int", "Long", "Float", "Double", "String"};
      param0 = LegalChecks.checkInEnum("KeyType", param0, _set);
      String _oldVal = this._KeyType;
      this._KeyType = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getSortOrder() {
      return this._SortOrder;
   }

   public boolean isSortOrderInherited() {
      return false;
   }

   public boolean isSortOrderSet() {
      return this._isSet(5);
   }

   public void setSortOrder(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Ascending", "Descending"};
      param0 = LegalChecks.checkInEnum("SortOrder", param0, _set);
      String _oldVal = this._SortOrder;
      this._SortOrder = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      JMSModuleValidator.validateDestinationKeyProperty(this);
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
               this._KeyType = "String";
               if (initOne) {
                  break;
               }
            case 3:
               this._Property = "JMSMessageID";
               if (initOne) {
                  break;
               }
            case 5:
               this._SortOrder = "Ascending";
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

   public static class SchemaHelper2 extends NamedEntityBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("key-type")) {
                  return 4;
               }

               if (s.equals("property")) {
                  return 3;
               }
               break;
            case 10:
               if (s.equals("sort-order")) {
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
            case 3:
               return "property";
            case 4:
               return "key-type";
            case 5:
               return "sort-order";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends NamedEntityBeanImpl.Helper {
      private DestinationKeyBeanImpl bean;

      protected Helper(DestinationKeyBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "Property";
            case 4:
               return "KeyType";
            case 5:
               return "SortOrder";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("KeyType")) {
            return 4;
         } else if (propName.equals("Property")) {
            return 3;
         } else {
            return propName.equals("SortOrder") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isKeyTypeSet()) {
               buf.append("KeyType");
               buf.append(String.valueOf(this.bean.getKeyType()));
            }

            if (this.bean.isPropertySet()) {
               buf.append("Property");
               buf.append(String.valueOf(this.bean.getProperty()));
            }

            if (this.bean.isSortOrderSet()) {
               buf.append("SortOrder");
               buf.append(String.valueOf(this.bean.getSortOrder()));
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
            DestinationKeyBeanImpl otherTyped = (DestinationKeyBeanImpl)other;
            this.computeDiff("KeyType", this.bean.getKeyType(), otherTyped.getKeyType(), false);
            this.computeDiff("Property", this.bean.getProperty(), otherTyped.getProperty(), false);
            this.computeDiff("SortOrder", this.bean.getSortOrder(), otherTyped.getSortOrder(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DestinationKeyBeanImpl original = (DestinationKeyBeanImpl)event.getSourceBean();
            DestinationKeyBeanImpl proposed = (DestinationKeyBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("KeyType")) {
                  original.setKeyType(proposed.getKeyType());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Property")) {
                  original.setProperty(proposed.getProperty());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("SortOrder")) {
                  original.setSortOrder(proposed.getSortOrder());
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
            DestinationKeyBeanImpl copy = (DestinationKeyBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("KeyType")) && this.bean.isKeyTypeSet()) {
               copy.setKeyType(this.bean.getKeyType());
            }

            if ((excludeProps == null || !excludeProps.contains("Property")) && this.bean.isPropertySet()) {
               copy.setProperty(this.bean.getProperty());
            }

            if ((excludeProps == null || !excludeProps.contains("SortOrder")) && this.bean.isSortOrderSet()) {
               copy.setSortOrder(this.bean.getSortOrder());
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
