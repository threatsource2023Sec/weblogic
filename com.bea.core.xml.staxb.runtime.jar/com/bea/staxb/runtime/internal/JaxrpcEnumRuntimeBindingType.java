package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.JaxrpcEnumType;
import com.bea.staxb.runtime.internal.util.ReflectionUtils;
import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xml.XmlException;
import java.lang.reflect.Method;

final class JaxrpcEnumRuntimeBindingType extends RuntimeBindingType {
   private final JaxrpcEnumType jaxrpcEnumType;
   private ItemInfo itemInfo;

   JaxrpcEnumRuntimeBindingType(JaxrpcEnumType type) throws XmlException {
      super(type);
      this.jaxrpcEnumType = type;
   }

   void accept(RuntimeTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
      this.itemInfo = new ItemInfo(this.jaxrpcEnumType, this.getJavaType(), typeTable, bindingLoader);
   }

   boolean hasElementChildren() {
      return false;
   }

   CharSequence print(Object value, MarshalResult result) throws XmlException {
      if (this.itemInfo.hasToXmlMethod()) {
         return (String)ReflectionUtils.invokeMethod(value, this.itemInfo.getToXmlMethod());
      } else {
         Object simple_content = this.extractValue(value);
         return this.itemInfo.getItemMarshaller().print(simple_content, result);
      }
   }

   private Object extractValue(Object value) throws XmlException {
      return ReflectionUtils.invokeMethod(value, this.itemInfo.getGetValueMethod());
   }

   TypeUnmarshaller getItemUnmarshaller() {
      return this.itemInfo.getItemUnmarshaller();
   }

   Object fromValue(Object itemValue, UnmarshalResult context) throws XmlException {
      assert itemValue != null;

      assert context != null;

      try {
         return ReflectionUtils.invokeMethod((Object)null, this.itemInfo.getFromValueMethod(), new Object[]{itemValue});
      } catch (XmlException var4) {
         throw new InvalidLexicalValueException(var4, context.getLocation());
      }
   }

   private static final class ItemInfo {
      private final RuntimeBindingType itemType;
      private final Method getValueMethod;
      private final Method fromValueMethod;
      private final Method toXmlMethod;

      ItemInfo(JaxrpcEnumType jaxrpcEnumType, Class enum_java_class, RuntimeBindingTypeTable typeTable, BindingLoader loader) throws XmlException {
         BindingTypeName base_name = jaxrpcEnumType.getBaseTypeName();
         if (base_name == null) {
            throw new XmlException("null base type for " + jaxrpcEnumType);
         } else {
            BindingType item_type = loader.getBindingType(base_name);
            String e;
            if (item_type == null) {
               e = "unable to load type " + base_name + " for " + jaxrpcEnumType;
               throw new XmlException(e);
            } else {
               this.itemType = typeTable.createRuntimeType(item_type, loader);
               this.fromValueMethod = ReflectionUtils.getMethodOnClass(jaxrpcEnumType.getFromValueMethod(), enum_java_class);
               if (!ReflectionUtils.isMethodStatic(this.fromValueMethod)) {
                  e = "fromValue method must be static.  invalid method: " + this.fromValueMethod + " in type " + jaxrpcEnumType;
                  throw new XmlException(e);
               } else {
                  this.getValueMethod = ReflectionUtils.getMethodOnClass(jaxrpcEnumType.getGetValueMethod(), enum_java_class);
                  this.toXmlMethod = ReflectionUtils.getMethodOnClass(jaxrpcEnumType.getToXMLMethod(), enum_java_class);
                  TypeMarshaller itemMarshaller = this.itemType.getMarshaller();
                  if (itemMarshaller == null) {
                     String m = "unable to locate marshaller for " + item_type;
                     throw new XmlException(m);
                  } else {
                     Class[] parms = this.fromValueMethod.getParameterTypes();
                     if (parms.length != 1) {
                        throw new XmlException("invalid fromValue method, must have one parameter: " + this.fromValueMethod + " for type " + jaxrpcEnumType);
                     } else if (!parms[0].isAssignableFrom(this.itemType.getJavaType())) {
                        String m = "invalid fromValue method:" + this.fromValueMethod + " --  type mismatch between: " + parms[0] + " and " + this.itemType.getJavaType() + " for type " + jaxrpcEnumType;
                        throw new XmlException(m);
                     }
                  }
               }
            }
         }
      }

      TypeMarshaller getItemMarshaller() {
         return this.itemType.getMarshaller();
      }

      TypeUnmarshaller getItemUnmarshaller() {
         return this.itemType.getUnmarshaller();
      }

      Method getGetValueMethod() {
         return this.getValueMethod;
      }

      Method getFromValueMethod() {
         return this.fromValueMethod;
      }

      Method getToXmlMethod() {
         return this.toXmlMethod;
      }

      boolean hasToXmlMethod() {
         return this.toXmlMethod != null;
      }
   }
}
