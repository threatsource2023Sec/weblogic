package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingType;
import com.bea.xbeanmarshal.buildtime.internal.bts.JavaTypeName;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlTypeName;
import com.bea.xbeanmarshal.buildtime.internal.util.XmlBeanUtil;
import com.bea.xbeanmarshal.runtime.internal.util.ReflectionUtils;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

abstract class RuntimeBindingType {
   private final BindingType bindingType;
   private final Class javaClass;
   private final boolean javaPrimitive;
   private boolean canHaveSubtype;
   private TypeMarshaller marshaller;
   private TypeUnmarshaller unmarshaller;
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
      if (XmlBeanUtil.isXmlBean(this.javaClass)) {
         this.canHaveSubtype = false;
      }

      this.unmarshaller = um;
      this.marshaller = m;
   }

   abstract void accept(RuntimeTypeVisitor var1) throws XmlException;

   protected Object createIntermediary(UnmarshalResult context) throws XmlException {
      throw new UnsupportedOperationException("createIntermediary call on RuntimeBindingType base class");
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

      if (this.unmarshaller == null) {
         this.unmarshaller = typeTable.createUnmarshaller(this.bindingType, bindingLoader);
      }

      assert this.unmarshaller != null;

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

   protected RuntimeBindingType determineActualRuntimeType(UnmarshalResult ctx) throws XmlException {
      return this;
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
}
