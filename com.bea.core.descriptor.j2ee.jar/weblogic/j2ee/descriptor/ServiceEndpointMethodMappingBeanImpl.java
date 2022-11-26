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

public class ServiceEndpointMethodMappingBeanImpl extends AbstractDescriptorBean implements ServiceEndpointMethodMappingBean, Serializable {
   private String _Id;
   private String _JavaMethodName;
   private MethodParamPartsMappingBean[] _MethodParamPartsMappings;
   private EmptyBean _WrappedElement;
   private String _WsdlOperation;
   private WsdlReturnValueMappingBean _WsdlReturnValueMapping;
   private static SchemaHelper2 _schemaHelper;

   public ServiceEndpointMethodMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServiceEndpointMethodMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServiceEndpointMethodMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getJavaMethodName() {
      return this._JavaMethodName;
   }

   public boolean isJavaMethodNameInherited() {
      return false;
   }

   public boolean isJavaMethodNameSet() {
      return this._isSet(0);
   }

   public void setJavaMethodName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JavaMethodName;
      this._JavaMethodName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getWsdlOperation() {
      return this._WsdlOperation;
   }

   public boolean isWsdlOperationInherited() {
      return false;
   }

   public boolean isWsdlOperationSet() {
      return this._isSet(1);
   }

   public void setWsdlOperation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WsdlOperation;
      this._WsdlOperation = param0;
      this._postSet(1, _oldVal, param0);
   }

   public EmptyBean getWrappedElement() {
      return this._WrappedElement;
   }

   public boolean isWrappedElementInherited() {
      return false;
   }

   public boolean isWrappedElementSet() {
      return this._isSet(2);
   }

   public void setWrappedElement(EmptyBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWrappedElement() != null && param0 != this.getWrappedElement()) {
         throw new BeanAlreadyExistsException(this.getWrappedElement() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         EmptyBean _oldVal = this._WrappedElement;
         this._WrappedElement = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public EmptyBean createWrappedElement() {
      EmptyBeanImpl _val = new EmptyBeanImpl(this, -1);

      try {
         this.setWrappedElement(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWrappedElement(EmptyBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WrappedElement;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWrappedElement((EmptyBean)null);
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

   public void addMethodParamPartsMapping(MethodParamPartsMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         MethodParamPartsMappingBean[] _new;
         if (this._isSet(3)) {
            _new = (MethodParamPartsMappingBean[])((MethodParamPartsMappingBean[])this._getHelper()._extendArray(this.getMethodParamPartsMappings(), MethodParamPartsMappingBean.class, param0));
         } else {
            _new = new MethodParamPartsMappingBean[]{param0};
         }

         try {
            this.setMethodParamPartsMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MethodParamPartsMappingBean[] getMethodParamPartsMappings() {
      return this._MethodParamPartsMappings;
   }

   public boolean isMethodParamPartsMappingsInherited() {
      return false;
   }

   public boolean isMethodParamPartsMappingsSet() {
      return this._isSet(3);
   }

   public void removeMethodParamPartsMapping(MethodParamPartsMappingBean param0) {
      this.destroyMethodParamPartsMapping(param0);
   }

   public void setMethodParamPartsMappings(MethodParamPartsMappingBean[] param0) throws InvalidAttributeValueException {
      MethodParamPartsMappingBean[] param0 = param0 == null ? new MethodParamPartsMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MethodParamPartsMappingBean[] _oldVal = this._MethodParamPartsMappings;
      this._MethodParamPartsMappings = (MethodParamPartsMappingBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public MethodParamPartsMappingBean createMethodParamPartsMapping() {
      MethodParamPartsMappingBeanImpl _val = new MethodParamPartsMappingBeanImpl(this, -1);

      try {
         this.addMethodParamPartsMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMethodParamPartsMapping(MethodParamPartsMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         MethodParamPartsMappingBean[] _old = this.getMethodParamPartsMappings();
         MethodParamPartsMappingBean[] _new = (MethodParamPartsMappingBean[])((MethodParamPartsMappingBean[])this._getHelper()._removeElement(_old, MethodParamPartsMappingBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMethodParamPartsMappings(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public WsdlReturnValueMappingBean getWsdlReturnValueMapping() {
      return this._WsdlReturnValueMapping;
   }

   public boolean isWsdlReturnValueMappingInherited() {
      return false;
   }

   public boolean isWsdlReturnValueMappingSet() {
      return this._isSet(4);
   }

   public void setWsdlReturnValueMapping(WsdlReturnValueMappingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWsdlReturnValueMapping() != null && param0 != this.getWsdlReturnValueMapping()) {
         throw new BeanAlreadyExistsException(this.getWsdlReturnValueMapping() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WsdlReturnValueMappingBean _oldVal = this._WsdlReturnValueMapping;
         this._WsdlReturnValueMapping = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public WsdlReturnValueMappingBean createWsdlReturnValueMapping() {
      WsdlReturnValueMappingBeanImpl _val = new WsdlReturnValueMappingBeanImpl(this, -1);

      try {
         this.setWsdlReturnValueMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWsdlReturnValueMapping(WsdlReturnValueMappingBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WsdlReturnValueMapping;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWsdlReturnValueMapping((WsdlReturnValueMappingBean)null);
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
         idx = 5;
      }

      try {
         switch (idx) {
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._JavaMethodName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._MethodParamPartsMappings = new MethodParamPartsMappingBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._WrappedElement = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._WsdlOperation = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._WsdlReturnValueMapping = null;
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
            case 14:
               if (s.equals("wsdl-operation")) {
                  return 1;
               }
               break;
            case 15:
               if (s.equals("wrapped-element")) {
                  return 2;
               }
               break;
            case 16:
               if (s.equals("java-method-name")) {
                  return 0;
               }
               break;
            case 25:
               if (s.equals("wsdl-return-value-mapping")) {
                  return 4;
               }
               break;
            case 26:
               if (s.equals("method-param-parts-mapping")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new EmptyBeanImpl.SchemaHelper2();
            case 3:
               return new MethodParamPartsMappingBeanImpl.SchemaHelper2();
            case 4:
               return new WsdlReturnValueMappingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "java-method-name";
            case 1:
               return "wsdl-operation";
            case 2:
               return "wrapped-element";
            case 3:
               return "method-param-parts-mapping";
            case 4:
               return "wsdl-return-value-mapping";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ServiceEndpointMethodMappingBeanImpl bean;

      protected Helper(ServiceEndpointMethodMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "JavaMethodName";
            case 1:
               return "WsdlOperation";
            case 2:
               return "WrappedElement";
            case 3:
               return "MethodParamPartsMappings";
            case 4:
               return "WsdlReturnValueMapping";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 5;
         } else if (propName.equals("JavaMethodName")) {
            return 0;
         } else if (propName.equals("MethodParamPartsMappings")) {
            return 3;
         } else if (propName.equals("WrappedElement")) {
            return 2;
         } else if (propName.equals("WsdlOperation")) {
            return 1;
         } else {
            return propName.equals("WsdlReturnValueMapping") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getMethodParamPartsMappings()));
         if (this.bean.getWrappedElement() != null) {
            iterators.add(new ArrayIterator(new EmptyBean[]{this.bean.getWrappedElement()}));
         }

         if (this.bean.getWsdlReturnValueMapping() != null) {
            iterators.add(new ArrayIterator(new WsdlReturnValueMappingBean[]{this.bean.getWsdlReturnValueMapping()}));
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isJavaMethodNameSet()) {
               buf.append("JavaMethodName");
               buf.append(String.valueOf(this.bean.getJavaMethodName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getMethodParamPartsMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMethodParamPartsMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWrappedElement());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isWsdlOperationSet()) {
               buf.append("WsdlOperation");
               buf.append(String.valueOf(this.bean.getWsdlOperation()));
            }

            childValue = this.computeChildHashValue(this.bean.getWsdlReturnValueMapping());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            ServiceEndpointMethodMappingBeanImpl otherTyped = (ServiceEndpointMethodMappingBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("JavaMethodName", this.bean.getJavaMethodName(), otherTyped.getJavaMethodName(), false);
            this.computeChildDiff("MethodParamPartsMappings", this.bean.getMethodParamPartsMappings(), otherTyped.getMethodParamPartsMappings(), false);
            this.computeChildDiff("WrappedElement", this.bean.getWrappedElement(), otherTyped.getWrappedElement(), false);
            this.computeDiff("WsdlOperation", this.bean.getWsdlOperation(), otherTyped.getWsdlOperation(), false);
            this.computeChildDiff("WsdlReturnValueMapping", this.bean.getWsdlReturnValueMapping(), otherTyped.getWsdlReturnValueMapping(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServiceEndpointMethodMappingBeanImpl original = (ServiceEndpointMethodMappingBeanImpl)event.getSourceBean();
            ServiceEndpointMethodMappingBeanImpl proposed = (ServiceEndpointMethodMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("JavaMethodName")) {
                  original.setJavaMethodName(proposed.getJavaMethodName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MethodParamPartsMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMethodParamPartsMapping((MethodParamPartsMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMethodParamPartsMapping((MethodParamPartsMappingBean)update.getRemovedObject());
                  }

                  if (original.getMethodParamPartsMappings() == null || original.getMethodParamPartsMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("WrappedElement")) {
                  if (type == 2) {
                     original.setWrappedElement((EmptyBean)this.createCopy((AbstractDescriptorBean)proposed.getWrappedElement()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WrappedElement", (DescriptorBean)original.getWrappedElement());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("WsdlOperation")) {
                  original.setWsdlOperation(proposed.getWsdlOperation());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("WsdlReturnValueMapping")) {
                  if (type == 2) {
                     original.setWsdlReturnValueMapping((WsdlReturnValueMappingBean)this.createCopy((AbstractDescriptorBean)proposed.getWsdlReturnValueMapping()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WsdlReturnValueMapping", (DescriptorBean)original.getWsdlReturnValueMapping());
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
            ServiceEndpointMethodMappingBeanImpl copy = (ServiceEndpointMethodMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaMethodName")) && this.bean.isJavaMethodNameSet()) {
               copy.setJavaMethodName(this.bean.getJavaMethodName());
            }

            if ((excludeProps == null || !excludeProps.contains("MethodParamPartsMappings")) && this.bean.isMethodParamPartsMappingsSet() && !copy._isSet(3)) {
               MethodParamPartsMappingBean[] oldMethodParamPartsMappings = this.bean.getMethodParamPartsMappings();
               MethodParamPartsMappingBean[] newMethodParamPartsMappings = new MethodParamPartsMappingBean[oldMethodParamPartsMappings.length];

               for(int i = 0; i < newMethodParamPartsMappings.length; ++i) {
                  newMethodParamPartsMappings[i] = (MethodParamPartsMappingBean)((MethodParamPartsMappingBean)this.createCopy((AbstractDescriptorBean)oldMethodParamPartsMappings[i], includeObsolete));
               }

               copy.setMethodParamPartsMappings(newMethodParamPartsMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("WrappedElement")) && this.bean.isWrappedElementSet() && !copy._isSet(2)) {
               Object o = this.bean.getWrappedElement();
               copy.setWrappedElement((EmptyBean)null);
               copy.setWrappedElement(o == null ? null : (EmptyBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlOperation")) && this.bean.isWsdlOperationSet()) {
               copy.setWsdlOperation(this.bean.getWsdlOperation());
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlReturnValueMapping")) && this.bean.isWsdlReturnValueMappingSet() && !copy._isSet(4)) {
               Object o = this.bean.getWsdlReturnValueMapping();
               copy.setWsdlReturnValueMapping((WsdlReturnValueMappingBean)null);
               copy.setWsdlReturnValueMapping(o == null ? null : (WsdlReturnValueMappingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getMethodParamPartsMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getWrappedElement(), clazz, annotation);
         this.inferSubTree(this.bean.getWsdlReturnValueMapping(), clazz, annotation);
      }
   }
}
