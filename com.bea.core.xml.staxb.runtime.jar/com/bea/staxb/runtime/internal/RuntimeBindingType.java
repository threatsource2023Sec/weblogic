package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingProperty;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.MethodName;
import com.bea.staxb.buildtime.internal.bts.SimpleBindingType;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.runtime.internal.util.ReflectionUtils;
import com.bea.xbean.common.XmlStreamUtils;
import com.bea.xml.XmlException;
import com.bea.xml.XmlRuntimeException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

abstract class RuntimeBindingType {
   private final BindingType bindingType;
   private final Class javaClass;
   private final boolean javaPrimitive;
   private final boolean canHaveSubtype;
   private TypeMarshaller marshaller;
   private TypeUnmarshaller unmarshaller;
   private static final String XSD_NS = "http://www.w3.org/2001/XMLSchema";
   protected static final QName DEFAULT_MULTI_NAME = new QName("elt");

   RuntimeBindingType(BindingType binding_type) throws XmlException {
      this(binding_type, (TypeMarshaller)null, (TypeUnmarshaller)null);
   }

   RuntimeBindingType(BindingType binding_type, TypeMarshaller m, TypeUnmarshaller um) throws XmlException {
      this.bindingType = binding_type;

      try {
         this.javaClass = getJavaClass(binding_type, this.getClass().getClassLoader());
      } catch (ClassNotFoundException var7) {
         ClassLoader context_cl = Thread.currentThread().getContextClassLoader();
         String msg = "failed to load " + binding_type.getName().getJavaName() + " from " + context_cl;
         throw new XmlException(msg, var7);
      }

      this.javaPrimitive = this.javaClass.isPrimitive();
      this.canHaveSubtype = !this.javaPrimitive && !ReflectionUtils.isClassFinal(this.javaClass) && !isTypeAnonymous(this.bindingType);
      this.unmarshaller = um;
      this.marshaller = m;
   }

   abstract void accept(RuntimeTypeVisitor var1) throws XmlException;

   protected Object createIntermediary(UnmarshalResult context) throws XmlException {
      throw new UnsupportedOperationException("this=" + this + " at " + XmlStreamUtils.printEvent(context.baseReader));
   }

   protected Object createIntermediary(UnmarshalResult context, Object actual_object) throws XmlException {
      return actual_object;
   }

   Object getObjectFromIntermediate(Object inter) {
      return inter;
   }

   protected Object getFinalObjectFromIntermediary(Object inter, UnmarshalResult context) throws XmlException {
      return inter;
   }

   boolean isObjectFromIntermediateIdempotent() {
      return true;
   }

   final BindingType getBindingType() {
      return this.bindingType;
   }

   protected abstract void initialize(RuntimeBindingTypeTable var1, BindingLoader var2) throws XmlException;

   final void external_initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
      this.initialize(typeTable, bindingLoader);
      if (this.marshaller == null) {
         this.marshaller = typeTable.createMarshaller(this.bindingType, bindingLoader);
      }

      if (this.bindingType instanceof SimpleBindingType && this.marshaller == null) {
         throw new AssertionError("null marshaller for " + this.bindingType);
      } else {
         if (this.unmarshaller == null) {
            this.unmarshaller = typeTable.createUnmarshaller(this.bindingType, bindingLoader);
         }

         assert this.unmarshaller != null;

      }
   }

   final Class getJavaType() {
      return this.javaClass;
   }

   final boolean isJavaPrimitive() {
      return this.javaPrimitive;
   }

   boolean isJavaCollection() {
      return false;
   }

   boolean canHaveSubtype() {
      return this.canHaveSubtype;
   }

   protected boolean containsOwnContainingElement(Object instance) {
      return false;
   }

   void predefineNamespaces(Object instance, MarshalResult result) throws XmlException {
   }

   private static boolean isTypeAnonymous(BindingType btype) {
      XmlTypeName xml_type = btype.getName().getXmlName();

      assert xml_type.isSchemaType();

      return !xml_type.isGlobal();
   }

   protected final TypeUnmarshaller getUnmarshaller() {
      assert this.unmarshaller != null;

      return this.unmarshaller;
   }

   protected final TypeMarshaller getMarshaller() {
      return this.marshaller;
   }

   private static Class getJavaClass(BindingType btype, ClassLoader backup) throws ClassNotFoundException {
      JavaTypeName javaName = btype.getName().getJavaName();
      String jclass = javaName.toString();
      return ClassLoadingUtils.loadClass(jclass, backup);
   }

   protected final QName getSchemaTypeName() {
      return this.getBindingType().getName().getXmlName().getQName();
   }

   protected QName getXsiTypeName() {
      return this.getSchemaTypeName();
   }

   protected QName getMultiRefElementName() {
      QName schemaTypeName = this.getSchemaTypeName();
      return schemaTypeName != null ? schemaTypeName : DEFAULT_MULTI_NAME;
   }

   private boolean isSimpleContentExtendedFromXSD(RuntimeBindingType actual) throws XmlException {
      QName schemaTypeName = this.getSchemaTypeName();
      if (schemaTypeName == null) {
         return false;
      } else {
         if ("http://www.w3.org/2001/XMLSchema".equals(schemaTypeName.getNamespaceURI()) && actual instanceof SimpleContentRuntimeBindingType) {
            Class expected_type = this.getJavaType();
            SimpleContentRuntimeBindingType simpleActual = (SimpleContentRuntimeBindingType)actual;
            BeanRuntimeProperty sp = (BeanRuntimeProperty)simpleActual.getSimpleContentProperty();
            if (sp != null && sp.getPropertyClass() != null && expected_type.getName() == sp.getPropertyClass().getName()) {
               return true;
            }
         }

         return false;
      }
   }

   protected RuntimeBindingType determineActualRuntimeType(UnmarshalResult ctx) throws XmlException {
      QName xsi_type = ctx.getXsiType();
      if (xsi_type != null && !xsi_type.equals(this.getSchemaTypeName())) {
         BindingType btype = ctx.getPojoTypeFromXsiType(xsi_type);
         if (btype != null) {
            RuntimeBindingType actual_rtt = ctx.getRuntimeType(btype);
            if (this.isSimpleContentExtendedFromXSD(actual_rtt)) {
               return this;
            }

            if (this.isCompatibleTypeSubstitution(actual_rtt)) {
               return actual_rtt;
            }

            String e = "invalid type substitution: " + xsi_type + " for " + this.getSchemaTypeName() + " due to incompatible java types (" + actual_rtt.getJavaType().getName() + " for " + this.getJavaType().getName() + ") -- using declared type";
            ctx.addWarning(e);
         }
      }

      return this;
   }

   protected static Object extractDefaultObject(String value, BindingType bindingType, RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
      String xmldoc = "<a>" + value + "</a>";

      try {
         SchemaTypeLoaderProvider provider = UnusedSchemaTypeLoaderProvider.getInstance();
         UnmarshallerImpl um = new UnmarshallerImpl(loader, typeTable, provider);
         StringReader sr = new StringReader(xmldoc);
         XMLStreamReader reader = UnmarshallerImpl.getXmlInputFactory().createXMLStreamReader(sr);
         boolean ok = MarshalStreamUtils.advanceToNextStartElement(reader);

         assert ok;

         BindingTypeName btname = bindingType.getName();
         Object obj = um.unmarshalType(reader, btname.getXmlName().getQName(), btname.getJavaName().toString());
         reader.close();
         sr.close();
         return obj;
      } catch (XmlRuntimeException var12) {
         String msg = "invalid default value: " + value + " for type " + bindingType.getName();
         throw new XmlException(msg, var12);
      } catch (XMLStreamException var13) {
         throw new XmlException(var13);
      }
   }

   public String toString() {
      return this.getClass().getName() + "{bindingType=" + this.bindingType + "}";
   }

   protected boolean checkInstance(Object obj) throws XmlException {
      Class java_type = this.getJavaType();
      if (obj != null && !this.isJavaPrimitive() && !java_type.isInstance(obj)) {
         String m = "instance type: " + obj.getClass() + " not an instance of expected type: " + java_type;
         throw new XmlException(m);
      } else {
         return true;
      }
   }

   protected boolean canUseDefaultNamespace(Object obj) throws XmlException {
      return true;
   }

   abstract boolean hasElementChildren();

   protected RuntimeBindingType determineActualRuntimeType(Object property_value, MarshalResult result) throws XmlException {
      return result.determineRuntimeBindingType(this, property_value);
   }

   protected boolean needsXsiType(RuntimeBindingType expected, MarshalResult result) {
      return result.isForceXsiType() ? true : this.needsXsiType(expected);
   }

   protected boolean needsXsiType(RuntimeBindingType expected) {
      return this == expected ? false : expected.canHaveSubtype();
   }

   protected boolean isCompatibleTypeSubstitution(RuntimeBindingType actual) {
      if (this == actual) {
         return true;
      } else {
         Class expected_type = this.getJavaType();
         Class actual_type = actual.getJavaType();
         if (expected_type == actual_type) {
            return true;
         } else if (expected_type.equals(actual_type)) {
            return true;
         } else if (expected_type.isAssignableFrom(actual_type)) {
            return true;
         } else {
            return actual_type.isPrimitive();
         }
      }
   }

   protected abstract static class BeanRuntimeProperty extends RuntimeBindingProperty {
      private final Class beanClass;
      private final Field field;
      protected RuntimeBindingType runtimeBindingType;
      private final RuntimeBindingTypeTable runtimeBindingTypeTable;
      private final BindingType bindingType;
      private final BindingLoader bindingLoader;
      private Class propertyClass;
      private boolean propertyClassSet;
      private Class collectionElementClass;
      private boolean collectionElementClassSet;
      private Method getMethod;
      private Method setMethod;
      private Method issetMethod;
      private Method isTransientMethod;
      private boolean hasGetMethod;
      private boolean hasSetMethod;
      private boolean hasisSetMethod;
      private boolean hasisTransientMethod;

      BeanRuntimeProperty(Class beanClass, BindingProperty prop, RuntimeBindingType containingType, RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
         super(prop, containingType);
         this.beanClass = beanClass;
         BindingTypeName type_name = prop.getTypeName();
         BindingType binding_type = loader.getBindingType(type_name);
         if (binding_type == null) {
            throw new XmlException("unable to load " + type_name);
         } else {
            this.runtimeBindingTypeTable = typeTable;
            this.bindingType = binding_type;
            this.bindingLoader = loader;
            if (prop.isField()) {
               this.getMethod = null;
               this.setMethod = null;
               this.issetMethod = null;
               this.hasGetMethod = true;
               this.hasSetMethod = true;
               this.hasisSetMethod = true;
               this.field = ReflectionUtils.getField(prop, beanClass);
            } else {
               this.getMethod = null;
               this.setMethod = null;
               this.issetMethod = null;
               this.hasGetMethod = false;
               this.hasSetMethod = false;
               this.hasisSetMethod = false;
               this.field = null;
            }

         }
      }

      protected final Class getJavaType() throws XmlException {
         return this.getPropertyClass();
      }

      protected abstract BindingProperty getBindingProperty();

      public void fill(Object inter, Object prop_obj) throws XmlException {
         Object inst = this.getContainingType().getObjectFromIntermediate(inter);
         this.setValue(inst, prop_obj);
      }

      protected void setValue(Object target, Object prop_obj) throws XmlException {
         assert prop_obj == null || this.getPropertyClass().isPrimitive() || this.getPropertyClass().isInstance(prop_obj) : " wrong property type: " + prop_obj.getClass() + " expected " + this.getPropertyClass();

         assert this.isMutable() : "cannot set value on immutable property " + this;

         if (this.field == null) {
            if (!this.hasSetMethod) {
               this.setMethod = ReflectionUtils.getSetterMethod(this.getBindingProperty(), this.beanClass);
               this.hasSetMethod = true;
            }

            assert this.setMethod != null;

            ReflectionUtils.invokeMethod(target, this.setMethod, new Object[]{prop_obj});
         } else {
            ReflectionUtils.setFieldValue(target, this.field, prop_obj);
         }

      }

      final Object getValue(Object parentObject) throws XmlException {
         assert parentObject != null : "getValue on null parent.  this=" + this;

         assert this.beanClass.isInstance(parentObject) : parentObject.getClass() + " is not a " + this.beanClass;

         if (this.field == null) {
            if (!this.hasGetMethod) {
               this.getMethod = ReflectionUtils.getGetterMethod(this.getBindingProperty(), this.beanClass);
               if (this.getMethod == null) {
                  String e = "no getter found for " + this.getBindingProperty() + " on " + this.beanClass;
                  throw new XmlException(e);
               }

               this.hasGetMethod = true;
            }

            return ReflectionUtils.invokeMethod(parentObject, this.getMethod);
         } else {
            return ReflectionUtils.getFieldValue(parentObject, this.field);
         }
      }

      protected Class getPropertyClass() throws XmlException {
         if (this.propertyClassSet) {
            return this.propertyClass;
         } else {
            this.propertyClass = this.getPropertyClass(this.getBindingProperty(), this.bindingType);
            this.propertyClassSet = true;
            return this.propertyClass;
         }
      }

      protected Class getCollectionElementClass() throws XmlException {
         if (this.collectionElementClassSet) {
            return this.collectionElementClass;
         } else {
            this.collectionElementClass = this.getCollectionElementClass(this.getBindingProperty(), this.bindingType);
            this.collectionElementClassSet = true;
            return this.collectionElementClass;
         }
      }

      protected Class getPropertyClass(BindingProperty prop, BindingType btype) throws XmlException {
         assert btype != null;

         try {
            ClassLoader our_cl = this.getClass().getClassLoader();
            JavaTypeName collectionClass = prop.getCollectionClass();
            Class propertyClass;
            if (collectionClass == null) {
               propertyClass = RuntimeBindingType.getJavaClass(btype, our_cl);
            } else {
               String col = collectionClass.toString();
               propertyClass = ClassLoadingUtils.loadClass(col, our_cl);
            }

            return propertyClass;
         } catch (ClassNotFoundException var7) {
            throw new XmlException(var7);
         }
      }

      protected Class getCollectionElementClass(BindingProperty prop, BindingType btype) throws XmlException {
         assert btype != null;

         try {
            JavaTypeName collectionClass = prop.getCollectionClass();
            if (collectionClass == null) {
               return null;
            } else {
               ClassLoader our_cl = this.getClass().getClassLoader();
               return RuntimeBindingType.getJavaClass(btype, our_cl);
            }
         } catch (ClassNotFoundException var5) {
            throw new XmlException(var5);
         }
      }

      final RuntimeBindingType getRuntimeBindingType() {
         try {
            if (this.runtimeBindingType == null) {
               this.runtimeBindingType = this.runtimeBindingTypeTable.createRuntimeType(this.bindingType, this.bindingLoader);

               assert this.runtimeBindingType != null;
            }
         } catch (XmlException var2) {
            throw new XmlRuntimeException(var2);
         }

         return this.runtimeBindingType;
      }

      final boolean isSet(Object parentObject) throws XmlException {
         if (!this.hasisSetMethod) {
            this.issetMethod = ReflectionUtils.getIssetterMethod(this.getBindingProperty(), this.beanClass);
            this.hasisSetMethod = true;
         }

         if (this.issetMethod == null) {
            return this.isSetFallback(parentObject);
         } else {
            Boolean isset = (Boolean)ReflectionUtils.invokeMethod(parentObject, this.issetMethod);
            return isset;
         }
      }

      final boolean isTransient(Object currObject) {
         try {
            if (currObject != null && this.getBindingProperty() != null && this.getBindingProperty().hasIssetter()) {
               if (!this.hasisTransientMethod) {
                  this.isTransientMethod = ReflectionUtils.getMethodOnClass(MethodName.create("_isTransient"), currObject.getClass());
                  this.hasisTransientMethod = true;
               }

               if (this.isTransientMethod == null) {
                  return false;
               } else {
                  Boolean isTrans = (Boolean)ReflectionUtils.invokeMethod(currObject, this.isTransientMethod);
                  return isTrans;
               }
            } else {
               return false;
            }
         } catch (XmlException var3) {
            return false;
         }
      }

      private boolean isSetFallback(Object parentObject) throws XmlException {
         if (this.isNillable()) {
            return true;
         } else {
            Object val = this.getValue(parentObject);
            return val != null;
         }
      }
   }
}
