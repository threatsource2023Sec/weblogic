package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import javax.xml.namespace.QName;
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

public class ExceptionMappingBeanImpl extends AbstractDescriptorBean implements ExceptionMappingBean, Serializable {
   private ConstructorParameterOrderBean _ConstructorParameterOrder;
   private String _ExceptionType;
   private String _Id;
   private QName _WsdlMessage;
   private String _WsdlMessagePartName;
   private static SchemaHelper2 _schemaHelper;

   public ExceptionMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public ExceptionMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ExceptionMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getExceptionType() {
      return this._ExceptionType;
   }

   public boolean isExceptionTypeInherited() {
      return false;
   }

   public boolean isExceptionTypeSet() {
      return this._isSet(0);
   }

   public void setExceptionType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ExceptionType;
      this._ExceptionType = param0;
      this._postSet(0, _oldVal, param0);
   }

   public QName getWsdlMessage() {
      return this._WsdlMessage;
   }

   public boolean isWsdlMessageInherited() {
      return false;
   }

   public boolean isWsdlMessageSet() {
      return this._isSet(1);
   }

   public void setWsdlMessage(QName param0) {
      QName _oldVal = this._WsdlMessage;
      this._WsdlMessage = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getWsdlMessagePartName() {
      return this._WsdlMessagePartName;
   }

   public boolean isWsdlMessagePartNameInherited() {
      return false;
   }

   public boolean isWsdlMessagePartNameSet() {
      return this._isSet(2);
   }

   public void setWsdlMessagePartName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WsdlMessagePartName;
      this._WsdlMessagePartName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public ConstructorParameterOrderBean getConstructorParameterOrder() {
      return this._ConstructorParameterOrder;
   }

   public boolean isConstructorParameterOrderInherited() {
      return false;
   }

   public boolean isConstructorParameterOrderSet() {
      return this._isSet(3);
   }

   public void setConstructorParameterOrder(ConstructorParameterOrderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getConstructorParameterOrder() != null && param0 != this.getConstructorParameterOrder()) {
         throw new BeanAlreadyExistsException(this.getConstructorParameterOrder() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ConstructorParameterOrderBean _oldVal = this._ConstructorParameterOrder;
         this._ConstructorParameterOrder = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public ConstructorParameterOrderBean createConstructorParameterOrder() {
      ConstructorParameterOrderBeanImpl _val = new ConstructorParameterOrderBeanImpl(this, -1);

      try {
         this.setConstructorParameterOrder(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConstructorParameterOrder(ConstructorParameterOrderBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ConstructorParameterOrder;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setConstructorParameterOrder((ConstructorParameterOrderBean)null);
               this._unSet(3);
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
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._ConstructorParameterOrder = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ExceptionType = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._WsdlMessage = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._WsdlMessagePartName = null;
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
                  return 4;
               }
               break;
            case 12:
               if (s.equals("wsdl-message")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("exception-type")) {
                  return 0;
               }
               break;
            case 22:
               if (s.equals("wsdl-message-part-name")) {
                  return 2;
               }
               break;
            case 27:
               if (s.equals("constructor-parameter-order")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new ConstructorParameterOrderBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "exception-type";
            case 1:
               return "wsdl-message";
            case 2:
               return "wsdl-message-part-name";
            case 3:
               return "constructor-parameter-order";
            case 4:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ExceptionMappingBeanImpl bean;

      protected Helper(ExceptionMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ExceptionType";
            case 1:
               return "WsdlMessage";
            case 2:
               return "WsdlMessagePartName";
            case 3:
               return "ConstructorParameterOrder";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConstructorParameterOrder")) {
            return 3;
         } else if (propName.equals("ExceptionType")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("WsdlMessage")) {
            return 1;
         } else {
            return propName.equals("WsdlMessagePartName") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getConstructorParameterOrder() != null) {
            iterators.add(new ArrayIterator(new ConstructorParameterOrderBean[]{this.bean.getConstructorParameterOrder()}));
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
            childValue = this.computeChildHashValue(this.bean.getConstructorParameterOrder());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isExceptionTypeSet()) {
               buf.append("ExceptionType");
               buf.append(String.valueOf(this.bean.getExceptionType()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isWsdlMessageSet()) {
               buf.append("WsdlMessage");
               buf.append(String.valueOf(this.bean.getWsdlMessage()));
            }

            if (this.bean.isWsdlMessagePartNameSet()) {
               buf.append("WsdlMessagePartName");
               buf.append(String.valueOf(this.bean.getWsdlMessagePartName()));
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
            ExceptionMappingBeanImpl otherTyped = (ExceptionMappingBeanImpl)other;
            this.computeChildDiff("ConstructorParameterOrder", this.bean.getConstructorParameterOrder(), otherTyped.getConstructorParameterOrder(), false);
            this.computeDiff("ExceptionType", this.bean.getExceptionType(), otherTyped.getExceptionType(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("WsdlMessage", this.bean.getWsdlMessage(), otherTyped.getWsdlMessage(), false);
            this.computeDiff("WsdlMessagePartName", this.bean.getWsdlMessagePartName(), otherTyped.getWsdlMessagePartName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ExceptionMappingBeanImpl original = (ExceptionMappingBeanImpl)event.getSourceBean();
            ExceptionMappingBeanImpl proposed = (ExceptionMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConstructorParameterOrder")) {
                  if (type == 2) {
                     original.setConstructorParameterOrder((ConstructorParameterOrderBean)this.createCopy((AbstractDescriptorBean)proposed.getConstructorParameterOrder()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ConstructorParameterOrder", (DescriptorBean)original.getConstructorParameterOrder());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ExceptionType")) {
                  original.setExceptionType(proposed.getExceptionType());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("WsdlMessage")) {
                  original.setWsdlMessage(proposed.getWsdlMessage());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("WsdlMessagePartName")) {
                  original.setWsdlMessagePartName(proposed.getWsdlMessagePartName());
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
            ExceptionMappingBeanImpl copy = (ExceptionMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConstructorParameterOrder")) && this.bean.isConstructorParameterOrderSet() && !copy._isSet(3)) {
               Object o = this.bean.getConstructorParameterOrder();
               copy.setConstructorParameterOrder((ConstructorParameterOrderBean)null);
               copy.setConstructorParameterOrder(o == null ? null : (ConstructorParameterOrderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ExceptionType")) && this.bean.isExceptionTypeSet()) {
               copy.setExceptionType(this.bean.getExceptionType());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlMessage")) && this.bean.isWsdlMessageSet()) {
               copy.setWsdlMessage(this.bean.getWsdlMessage());
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlMessagePartName")) && this.bean.isWsdlMessagePartNameSet()) {
               copy.setWsdlMessagePartName(this.bean.getWsdlMessagePartName());
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
         this.inferSubTree(this.bean.getConstructorParameterOrder(), clazz, annotation);
      }
   }
}
