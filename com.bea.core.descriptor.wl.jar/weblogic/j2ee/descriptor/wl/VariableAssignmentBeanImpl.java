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

public class VariableAssignmentBeanImpl extends AbstractDescriptorBean implements VariableAssignmentBean, Serializable {
   private String _Description;
   private String _Name;
   private String _Operation;
   private String _Origin;
   private String _Xpath;
   private static SchemaHelper2 _schemaHelper;

   public VariableAssignmentBeanImpl() {
      this._initializeProperty(-1);
   }

   public VariableAssignmentBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public VariableAssignmentBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(0);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(1);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getXpath() {
      return this._Xpath;
   }

   public boolean isXpathInherited() {
      return false;
   }

   public boolean isXpathSet() {
      return this._isSet(2);
   }

   public void setXpath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Xpath;
      this._Xpath = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getOrigin() {
      return this._Origin;
   }

   public boolean isOriginInherited() {
      return false;
   }

   public boolean isOriginSet() {
      return this._isSet(3);
   }

   public void setOrigin(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Origin;
      this._Origin = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getOperation() {
      return this._Operation;
   }

   public boolean isOperationInherited() {
      return false;
   }

   public boolean isOperationSet() {
      return this._isSet(4);
   }

   public void setOperation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Operation;
      this._Operation = param0;
      this._postSet(4, _oldVal, param0);
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
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._Operation = "add";
               if (initOne) {
                  break;
               }
            case 3:
               this._Origin = "external";
               if (initOne) {
                  break;
               }
            case 2:
               this._Xpath = null;
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
            case 4:
               if (s.equals("name")) {
                  return 1;
               }
               break;
            case 5:
               if (s.equals("xpath")) {
                  return 2;
               }
               break;
            case 6:
               if (s.equals("origin")) {
                  return 3;
               }
            case 7:
            case 8:
            case 10:
            default:
               break;
            case 9:
               if (s.equals("operation")) {
                  return 4;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
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
               return "name";
            case 2:
               return "xpath";
            case 3:
               return "origin";
            case 4:
               return "operation";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private VariableAssignmentBeanImpl bean;

      protected Helper(VariableAssignmentBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "Name";
            case 2:
               return "Xpath";
            case 3:
               return "Origin";
            case 4:
               return "Operation";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("Name")) {
            return 1;
         } else if (propName.equals("Operation")) {
            return 4;
         } else if (propName.equals("Origin")) {
            return 3;
         } else {
            return propName.equals("Xpath") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isOperationSet()) {
               buf.append("Operation");
               buf.append(String.valueOf(this.bean.getOperation()));
            }

            if (this.bean.isOriginSet()) {
               buf.append("Origin");
               buf.append(String.valueOf(this.bean.getOrigin()));
            }

            if (this.bean.isXpathSet()) {
               buf.append("Xpath");
               buf.append(String.valueOf(this.bean.getXpath()));
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
            VariableAssignmentBeanImpl otherTyped = (VariableAssignmentBeanImpl)other;
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Operation", this.bean.getOperation(), otherTyped.getOperation(), false);
            this.computeDiff("Origin", this.bean.getOrigin(), otherTyped.getOrigin(), false);
            this.computeDiff("Xpath", this.bean.getXpath(), otherTyped.getXpath(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            VariableAssignmentBeanImpl original = (VariableAssignmentBeanImpl)event.getSourceBean();
            VariableAssignmentBeanImpl proposed = (VariableAssignmentBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Operation")) {
                  original.setOperation(proposed.getOperation());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Origin")) {
                  original.setOrigin(proposed.getOrigin());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Xpath")) {
                  original.setXpath(proposed.getXpath());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            VariableAssignmentBeanImpl copy = (VariableAssignmentBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Operation")) && this.bean.isOperationSet()) {
               copy.setOperation(this.bean.getOperation());
            }

            if ((excludeProps == null || !excludeProps.contains("Origin")) && this.bean.isOriginSet()) {
               copy.setOrigin(this.bean.getOrigin());
            }

            if ((excludeProps == null || !excludeProps.contains("Xpath")) && this.bean.isXpathSet()) {
               copy.setXpath(this.bean.getXpath());
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
