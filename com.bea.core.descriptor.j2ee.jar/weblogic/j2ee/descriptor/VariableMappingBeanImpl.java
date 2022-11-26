package weblogic.j2ee.descriptor;

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

public class VariableMappingBeanImpl extends AbstractDescriptorBean implements VariableMappingBean, Serializable {
   private EmptyBean _DataMember;
   private String _Id;
   private String _JavaVariableName;
   private String _XmlAttributeName;
   private String _XmlElementName;
   private EmptyBean _XmlWildcard;
   private static SchemaHelper2 _schemaHelper;

   public VariableMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public VariableMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public VariableMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getJavaVariableName() {
      return this._JavaVariableName;
   }

   public boolean isJavaVariableNameInherited() {
      return false;
   }

   public boolean isJavaVariableNameSet() {
      return this._isSet(0);
   }

   public void setJavaVariableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JavaVariableName;
      this._JavaVariableName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public EmptyBean getDataMember() {
      return this._DataMember;
   }

   public boolean isDataMemberInherited() {
      return false;
   }

   public boolean isDataMemberSet() {
      return this._isSet(1);
   }

   public void setDataMember(EmptyBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDataMember() != null && param0 != this.getDataMember()) {
         throw new BeanAlreadyExistsException(this.getDataMember() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         EmptyBean _oldVal = this._DataMember;
         this._DataMember = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public EmptyBean createDataMember() {
      EmptyBeanImpl _val = new EmptyBeanImpl(this, -1);

      try {
         this.setDataMember(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDataMember(EmptyBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DataMember;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDataMember((EmptyBean)null);
               this._unSet(1);
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

   public String getXmlAttributeName() {
      return this._XmlAttributeName;
   }

   public boolean isXmlAttributeNameInherited() {
      return false;
   }

   public boolean isXmlAttributeNameSet() {
      return this._isSet(2);
   }

   public void setXmlAttributeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._XmlAttributeName;
      this._XmlAttributeName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getXmlElementName() {
      return this._XmlElementName;
   }

   public boolean isXmlElementNameInherited() {
      return false;
   }

   public boolean isXmlElementNameSet() {
      return this._isSet(3);
   }

   public void setXmlElementName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._XmlElementName;
      this._XmlElementName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public EmptyBean getXmlWildcard() {
      return this._XmlWildcard;
   }

   public boolean isXmlWildcardInherited() {
      return false;
   }

   public boolean isXmlWildcardSet() {
      return this._isSet(4);
   }

   public void setXmlWildcard(EmptyBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getXmlWildcard() != null && param0 != this.getXmlWildcard()) {
         throw new BeanAlreadyExistsException(this.getXmlWildcard() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         EmptyBean _oldVal = this._XmlWildcard;
         this._XmlWildcard = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public EmptyBean createXmlWildcard() {
      EmptyBeanImpl _val = new EmptyBeanImpl(this, -1);

      try {
         this.setXmlWildcard(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyXmlWildcard(EmptyBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._XmlWildcard;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setXmlWildcard((EmptyBean)null);
               this._unSet(4);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._DataMember = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._JavaVariableName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._XmlAttributeName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._XmlElementName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._XmlWildcard = null;
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
                  return 5;
               }
               break;
            case 11:
               if (s.equals("data-member")) {
                  return 1;
               }
               break;
            case 12:
               if (s.equals("xml-wildcard")) {
                  return 4;
               }
               break;
            case 16:
               if (s.equals("xml-element-name")) {
                  return 3;
               }
               break;
            case 18:
               if (s.equals("java-variable-name")) {
                  return 0;
               }

               if (s.equals("xml-attribute-name")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new EmptyBeanImpl.SchemaHelper2();
            case 4:
               return new EmptyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "java-variable-name";
            case 1:
               return "data-member";
            case 2:
               return "xml-attribute-name";
            case 3:
               return "xml-element-name";
            case 4:
               return "xml-wildcard";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 4:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private VariableMappingBeanImpl bean;

      protected Helper(VariableMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "JavaVariableName";
            case 1:
               return "DataMember";
            case 2:
               return "XmlAttributeName";
            case 3:
               return "XmlElementName";
            case 4:
               return "XmlWildcard";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DataMember")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 5;
         } else if (propName.equals("JavaVariableName")) {
            return 0;
         } else if (propName.equals("XmlAttributeName")) {
            return 2;
         } else if (propName.equals("XmlElementName")) {
            return 3;
         } else {
            return propName.equals("XmlWildcard") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getDataMember() != null) {
            iterators.add(new ArrayIterator(new EmptyBean[]{this.bean.getDataMember()}));
         }

         if (this.bean.getXmlWildcard() != null) {
            iterators.add(new ArrayIterator(new EmptyBean[]{this.bean.getXmlWildcard()}));
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
            childValue = this.computeChildHashValue(this.bean.getDataMember());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isJavaVariableNameSet()) {
               buf.append("JavaVariableName");
               buf.append(String.valueOf(this.bean.getJavaVariableName()));
            }

            if (this.bean.isXmlAttributeNameSet()) {
               buf.append("XmlAttributeName");
               buf.append(String.valueOf(this.bean.getXmlAttributeName()));
            }

            if (this.bean.isXmlElementNameSet()) {
               buf.append("XmlElementName");
               buf.append(String.valueOf(this.bean.getXmlElementName()));
            }

            childValue = this.computeChildHashValue(this.bean.getXmlWildcard());
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
            VariableMappingBeanImpl otherTyped = (VariableMappingBeanImpl)other;
            this.computeChildDiff("DataMember", this.bean.getDataMember(), otherTyped.getDataMember(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("JavaVariableName", this.bean.getJavaVariableName(), otherTyped.getJavaVariableName(), false);
            this.computeDiff("XmlAttributeName", this.bean.getXmlAttributeName(), otherTyped.getXmlAttributeName(), false);
            this.computeDiff("XmlElementName", this.bean.getXmlElementName(), otherTyped.getXmlElementName(), false);
            this.computeChildDiff("XmlWildcard", this.bean.getXmlWildcard(), otherTyped.getXmlWildcard(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            VariableMappingBeanImpl original = (VariableMappingBeanImpl)event.getSourceBean();
            VariableMappingBeanImpl proposed = (VariableMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DataMember")) {
                  if (type == 2) {
                     original.setDataMember((EmptyBean)this.createCopy((AbstractDescriptorBean)proposed.getDataMember()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DataMember", (DescriptorBean)original.getDataMember());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("JavaVariableName")) {
                  original.setJavaVariableName(proposed.getJavaVariableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("XmlAttributeName")) {
                  original.setXmlAttributeName(proposed.getXmlAttributeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("XmlElementName")) {
                  original.setXmlElementName(proposed.getXmlElementName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("XmlWildcard")) {
                  if (type == 2) {
                     original.setXmlWildcard((EmptyBean)this.createCopy((AbstractDescriptorBean)proposed.getXmlWildcard()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("XmlWildcard", (DescriptorBean)original.getXmlWildcard());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            VariableMappingBeanImpl copy = (VariableMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            EmptyBean o;
            if ((excludeProps == null || !excludeProps.contains("DataMember")) && this.bean.isDataMemberSet() && !copy._isSet(1)) {
               o = this.bean.getDataMember();
               copy.setDataMember((EmptyBean)null);
               copy.setDataMember(o == null ? null : (EmptyBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaVariableName")) && this.bean.isJavaVariableNameSet()) {
               copy.setJavaVariableName(this.bean.getJavaVariableName());
            }

            if ((excludeProps == null || !excludeProps.contains("XmlAttributeName")) && this.bean.isXmlAttributeNameSet()) {
               copy.setXmlAttributeName(this.bean.getXmlAttributeName());
            }

            if ((excludeProps == null || !excludeProps.contains("XmlElementName")) && this.bean.isXmlElementNameSet()) {
               copy.setXmlElementName(this.bean.getXmlElementName());
            }

            if ((excludeProps == null || !excludeProps.contains("XmlWildcard")) && this.bean.isXmlWildcardSet() && !copy._isSet(4)) {
               o = this.bean.getXmlWildcard();
               copy.setXmlWildcard((EmptyBean)null);
               copy.setXmlWildcard(o == null ? null : (EmptyBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getDataMember(), clazz, annotation);
         this.inferSubTree(this.bean.getXmlWildcard(), clazz, annotation);
      }
   }
}
