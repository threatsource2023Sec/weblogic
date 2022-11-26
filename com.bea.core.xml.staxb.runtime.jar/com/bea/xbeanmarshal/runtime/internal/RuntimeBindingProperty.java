package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbeanmarshal.runtime.internal.util.ReflectionUtils;
import com.bea.xml.XmlException;
import java.lang.reflect.Method;
import javax.xml.namespace.QName;

abstract class RuntimeBindingProperty {
   private final Method parentFactoryMethod = null;
   private final boolean parentFactoryMethodTakesClassArg = false;
   private final boolean mutable = true;
   private final int ctorArgIndex = -1;

   protected RuntimeBindingProperty(RuntimeBindingType containingType) {
   }

   abstract RuntimeBindingType getRuntimeBindingType();

   RuntimeBindingType getActualRuntimeType(Object property_value, MarshalResult result) throws XmlException {
      return this.getRuntimeBindingType().determineActualRuntimeType(property_value, result);
   }

   protected Class getJavaType() {
      return this.getRuntimeBindingType().getJavaType();
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

   abstract void setValue(Object var1, Object var2) throws XmlException;

   abstract Object getValue(Object var1) throws XmlException;

   abstract boolean isSet(Object var1) throws XmlException;

   abstract boolean isMultiple();

   abstract boolean isNillable();

   abstract String getLexicalDefault();

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
         throw new XmlException(msg + "  " + var7.getLocation());
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

   protected final boolean hasFactory() {
      return this.parentFactoryMethod != null;
   }

   private Object createObjectViaFactory(Object inter, RuntimeBindingType actual_rtt) throws XmlException {
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
      try {
         return "RuntimeBindingProperty{name=" + this.getName() + " bindingType=" + this.getRuntimeBindingType().getBindingType() + "containingType=" + this.getContainingType() + "}";
      } catch (AssertionError var2) {
         return "RuntimeBindingProperty{name=" + this.getName() + " bindingType=" + this.getRuntimeBindingType().getBindingType() + "containingType=" + var2.getMessage() + "}";
      }
   }

   public boolean isMutable() {
      return this.mutable;
   }
}
