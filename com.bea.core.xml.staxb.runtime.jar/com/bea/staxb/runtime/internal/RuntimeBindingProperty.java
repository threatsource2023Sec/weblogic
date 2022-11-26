package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingProperty;
import com.bea.staxb.buildtime.internal.bts.JavaInstanceFactory;
import com.bea.staxb.buildtime.internal.bts.MethodName;
import com.bea.staxb.buildtime.internal.bts.ParentInstanceFactory;
import com.bea.staxb.runtime.internal.util.ReflectionUtils;
import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xml.XmlException;
import java.lang.reflect.Method;
import javax.xml.namespace.QName;

abstract class RuntimeBindingProperty {
   private Method parentFactoryMethod;
   private boolean parentFactoryMethodTakesClassArg;
   private final boolean mutable;
   private final int ctorArgIndex;
   private boolean parentFactoryMethodInitialized;
   private BindingProperty prop;
   private RuntimeBindingType containingType;

   protected RuntimeBindingProperty(RuntimeBindingType containingType) {
      this.parentFactoryMethod = null;
      this.parentFactoryMethodTakesClassArg = false;
      this.mutable = true;
      this.ctorArgIndex = -1;
   }

   protected RuntimeBindingProperty(BindingProperty prop, RuntimeBindingType containingType) throws XmlException {
      this.prop = prop;
      this.containingType = containingType;
      this.mutable = prop.isField() || prop.hasSetter() && prop.getCtorParamIndex() < 0;
      this.ctorArgIndex = prop.getCtorParamIndex();
   }

   private synchronized void initializeParentFactoryMethod() throws XmlException {
      if (!this.parentFactoryMethodInitialized && this.prop != null) {
         this.parentFactoryMethodInitialized = true;
         JavaInstanceFactory jif = this.prop.getJavaInstanceFactory();
         if (jif == null) {
            this.parentFactoryMethod = null;
            this.parentFactoryMethodTakesClassArg = false;
         } else {
            if (!(jif instanceof ParentInstanceFactory)) {
               throw new AssertionError("FACTORY UNIMP: " + jif);
            }

            ParentInstanceFactory pif = (ParentInstanceFactory)jif;
            MethodName create_method = pif.getCreateObjectMethod();
            Class container_class = this.containingType.getJavaType();
            this.parentFactoryMethod = ReflectionUtils.getMethodOnClass(create_method, container_class);
            Class[] param_types = this.parentFactoryMethod.getParameterTypes();
            String msg;
            if (param_types.length > 1) {
               msg = "too many args for parent factory method: " + this.parentFactoryMethod;
               throw new XmlException(msg);
            }

            if (param_types.length == 1 && !Class.class.equals(param_types[0])) {
               msg = "arg must be java.lang.Class for parent factory method: " + this.parentFactoryMethod;
               throw new XmlException(msg);
            }

            this.parentFactoryMethodTakesClassArg = param_types.length > 0;
         }

         this.prop = null;
         this.containingType = null;
      }
   }

   abstract RuntimeBindingType getRuntimeBindingType();

   RuntimeBindingType getActualRuntimeType(Object property_value, MarshalResult result) throws XmlException {
      return this.getRuntimeBindingType().determineActualRuntimeType(property_value, result);
   }

   protected Class getJavaType() throws XmlException {
      return this.getRuntimeBindingType().getJavaType();
   }

   protected BindingProperty getBindingProperty() {
      return this.prop;
   }

   abstract QName getName();

   abstract RuntimeBindingType getContainingType();

   protected final Object createIntermediary(Object parent_inter, RuntimeBindingType actual_rtt, UnmarshalResult context) throws XmlException {
      if (this.hasFactory()) {
         Object obj = this.createObjectViaFactory(parent_inter, actual_rtt);
         return actual_rtt.createIntermediary(context, obj);
      } else {
         return actual_rtt.createIntermediary(context);
      }
   }

   final CharSequence getLexical(Object value, MarshalResult result) throws XmlException {
      assert value != null : "null value for " + this.getName();

      assert result != null;

      RuntimeBindingType actualRuntimeType = this.getActualRuntimeType(value, result);

      assert actualRuntimeType.getMarshaller() != null : "null marshaller for prop=" + this.getName() + " propType=" + actualRuntimeType;

      return actualRuntimeType.getMarshaller().print(value, result);
   }

   abstract void setValue(Object var1, Object var2) throws XmlException;

   abstract Object getValue(Object var1) throws XmlException;

   abstract boolean isSet(Object var1) throws XmlException;

   abstract boolean isMultiple();

   abstract boolean isNillable();

   abstract boolean isOptional();

   abstract String getLexicalDefault();

   abstract boolean isTransient(Object var1);

   final void extractAndFillAttributeProp(UnmarshalResult context, Object inter) throws XmlException {
      RuntimeBindingType rtt = this.getRuntimeBindingType();
      TypeUnmarshaller um = rtt.getUnmarshaller();

      assert um != null;

      try {
         Object this_val;
         if (this.hasFactory()) {
            this_val = this.createObjectViaFactory(inter, rtt);
            um.unmarshalAttribute(this_val, context);
         } else {
            this_val = um.unmarshalAttribute(context);
         }

         this.fill(inter, this_val);
      } catch (InvalidLexicalValueException var7) {
         String msg = "invalid value for " + this.getName() + ": " + var7.getMessage();
         context.addError(msg, var7.getLocation());
      }

   }

   protected abstract void fill(Object var1, Object var2) throws XmlException;

   protected void fillPlaceholder(Object inter) {
      throw new UnsupportedOperationException("not used.  this=" + this);
   }

   protected int getSize(Object inter) {
      throw new UnsupportedOperationException("this=" + this);
   }

   protected void fill(Object final_obj, int index, Object prop_val) {
      throw new UnsupportedOperationException("this=" + this);
   }

   public int getCtorArgIndex() {
      return this.ctorArgIndex;
   }

   protected final boolean hasFactory() throws XmlException {
      this.initializeParentFactoryMethod();
      return this.parentFactoryMethod != null;
   }

   private Object createObjectViaFactory(Object inter, RuntimeBindingType actual_rtt) throws XmlException {
      this.initializeParentFactoryMethod();
      Object actual_obj = this.getContainingType().getObjectFromIntermediate(inter);

      assert actual_obj != null;

      assert this.parentFactoryMethod != null;

      Object[] params;
      if (this.parentFactoryMethodTakesClassArg) {
         params = new Object[]{actual_rtt.getJavaType()};
      } else {
         params = null;
      }

      Object obj = ReflectionUtils.invokeMethod(actual_obj, this.parentFactoryMethod, params);

      assert actual_rtt.checkInstance(obj);

      return obj;
   }

   public String toString() {
      return "RuntimeBindingProperty{name=" + this.getName() + " bindingType=" + this.getRuntimeBindingType().getBindingType() + "containingType=" + this.getContainingType() + "}";
   }

   public boolean isMutable() {
      return this.mutable;
   }
}
