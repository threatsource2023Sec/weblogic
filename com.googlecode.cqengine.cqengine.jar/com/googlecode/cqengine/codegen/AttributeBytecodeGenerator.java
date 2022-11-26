package com.googlecode.cqengine.codegen;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.MultiValueAttribute;
import com.googlecode.cqengine.attribute.MultiValueNullableAttribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.codegen.support.GeneratedAttributeSupport;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.bytecode.SignatureAttribute;

public class AttributeBytecodeGenerator {
   static final Map PRIMITIVES_TO_WRAPPERS = new LinkedHashMap() {
      {
         this.put(Boolean.TYPE, Boolean.class);
         this.put(Byte.TYPE, Byte.class);
         this.put(Character.TYPE, Character.class);
         this.put(Double.TYPE, Double.class);
         this.put(Float.TYPE, Float.class);
         this.put(Integer.TYPE, Integer.class);
         this.put(Long.TYPE, Long.class);
         this.put(Short.TYPE, Short.class);
         this.put(Void.TYPE, Void.class);
      }
   };

   public static Map createAttributes(Class pojoClass) {
      return createAttributes(pojoClass, MemberFilters.FIELDS_ONLY);
   }

   public static Map createAttributes(Class pojoClass, MemberFilter memberFilter) {
      Map attributes = new TreeMap();
      Class currentClass = pojoClass;

      for(Set membersEncountered = new HashSet(); currentClass != null && currentClass != Object.class; currentClass = currentClass.getSuperclass()) {
         Iterator var5 = AttributeSourceGenerator.getMembers(currentClass).iterator();

         while(var5.hasNext()) {
            Member member = (Member)var5.next();

            try {
               if (memberFilter.accept(member) && !membersEncountered.contains(member.getName())) {
                  int modifiers = member.getModifiers();
                  String memberName = member.getName();
                  String attributeName = member.getName();
                  Class memberType = AttributeSourceGenerator.getType(member);
                  if (!Modifier.isStatic(modifiers) && !Modifier.isPrivate(modifiers) && (member.getDeclaringClass().getPackage().getName().equals(pojoClass.getPackage().getName()) || Modifier.isProtected(modifiers) || Modifier.isPublic(modifiers))) {
                     Class attributeClass;
                     Class componentType;
                     if (memberType.isPrimitive()) {
                        componentType = getWrapperForPrimitive(memberType);
                        attributeClass = member instanceof Method ? generateSimpleAttributeForGetter(pojoClass, componentType, memberName, attributeName) : generateSimpleAttributeForField(pojoClass, componentType, memberName, attributeName);
                     } else if (Iterable.class.isAssignableFrom(memberType)) {
                        ParameterizedType parameterizedType = AttributeSourceGenerator.getGenericType(member);
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        if (actualTypeArguments.length != 1) {
                           throw new UnsupportedOperationException();
                        }

                        Class genericType = (Class)actualTypeArguments[0];
                        attributeClass = member instanceof Method ? generateMultiValueNullableAttributeForGetter(pojoClass, genericType, memberName, true, attributeName) : generateMultiValueNullableAttributeForField(pojoClass, genericType, memberName, true, attributeName);
                     } else if (memberType.isArray()) {
                        componentType = memberType.getComponentType();
                        if (componentType.isPrimitive()) {
                           Class wrapperType = getWrapperForPrimitive(componentType);
                           attributeClass = member instanceof Method ? generateMultiValueNullableAttributeForGetter(pojoClass, wrapperType, memberName, false, attributeName) : generateMultiValueNullableAttributeForField(pojoClass, wrapperType, memberName, false, attributeName);
                        } else {
                           attributeClass = member instanceof Method ? generateMultiValueNullableAttributeForGetter(pojoClass, componentType, memberName, true, attributeName) : generateMultiValueNullableAttributeForField(pojoClass, componentType, memberName, true, attributeName);
                        }
                     } else {
                        attributeClass = member instanceof Method ? generateSimpleNullableAttributeForGetter(pojoClass, memberType, memberName, attributeName) : generateSimpleNullableAttributeForField(pojoClass, memberType, memberName, attributeName);
                     }

                     Attribute attribute = (Attribute)attributeClass.newInstance();
                     attributes.put(attribute.getAttributeName(), attribute);
                     membersEncountered.add(member.getName());
                  }
               }
            } catch (Throwable var15) {
               throw new IllegalStateException("Failed to create attribute for member: " + member.toString(), var15);
            }
         }
      }

      return attributes;
   }

   public static Class generateSimpleAttributeForField(Class pojoClass, Class attributeValueType, String fieldName, String attributeName) {
      ensureFieldExists(pojoClass, attributeValueType, fieldName, attributeName);
      return generateSimpleAttribute(SimpleAttribute.class, pojoClass, attributeValueType, attributeName, "object." + fieldName);
   }

   public static Class generateSimpleAttributeForGetter(Class pojoClass, Class attributeValueType, String getterMethodName, String attributeName) {
      ensureGetterExists(pojoClass, attributeValueType, getterMethodName, attributeName);
      return generateSimpleAttribute(SimpleAttribute.class, pojoClass, attributeValueType, attributeName, "object." + getterMethodName + "()");
   }

   public static Class generateSimpleAttributeForParameterizedGetter(Class pojoClass, Class attributeValueType, String getterMethodName, String getterParameter, String attributeName) {
      ensureParameterizedGetterExists(pojoClass, attributeValueType, getterMethodName, getterParameter, attributeName);
      return generateSimpleAttribute(SimpleAttribute.class, pojoClass, attributeValueType, attributeName, "object." + getterMethodName + "(\"" + getterParameter + "\")");
   }

   public static Class generateSimpleNullableAttributeForField(Class pojoClass, Class attributeValueType, String fieldName, String attributeName) {
      ensureFieldExists(pojoClass, attributeValueType, fieldName, attributeName);
      return generateSimpleAttribute(SimpleNullableAttribute.class, pojoClass, attributeValueType, attributeName, "object." + fieldName);
   }

   public static Class generateSimpleNullableAttributeForGetter(Class pojoClass, Class attributeValueType, String getterMethodName, String attributeName) {
      ensureGetterExists(pojoClass, attributeValueType, getterMethodName, attributeName);
      return generateSimpleAttribute(SimpleNullableAttribute.class, pojoClass, attributeValueType, attributeName, "object." + getterMethodName + "()");
   }

   public static Class generateSimpleNullableAttributeForParameterizedGetter(Class pojoClass, Class attributeValueType, String getterMethodName, String getterParameter, String attributeName) {
      ensureParameterizedGetterExists(pojoClass, attributeValueType, getterMethodName, getterParameter, attributeName);
      return generateSimpleAttribute(SimpleNullableAttribute.class, pojoClass, attributeValueType, attributeName, "object." + getterMethodName + "(\"" + getterParameter + "\")");
   }

   public static Class generateMultiValueAttributeForField(Class pojoClass, Class attributeValueType, String fieldName, String attributeName) {
      ensureFieldExists(pojoClass, attributeValueType, fieldName, attributeName);
      return generateMultiValueAttribute(MultiValueAttribute.class, pojoClass, attributeValueType, attributeName, "object." + fieldName);
   }

   public static Class generateMultiValueAttributeForGetter(Class pojoClass, Class attributeValueType, String getterMethodName, String attributeName) {
      ensureGetterExists(pojoClass, attributeValueType, getterMethodName, attributeName);
      return generateMultiValueAttribute(MultiValueAttribute.class, pojoClass, attributeValueType, attributeName, "object." + getterMethodName + "()");
   }

   public static Class generateMultiValueAttributeForParameterizedGetter(Class pojoClass, Class attributeValueType, String getterMethodName, String getterParameter, String attributeName) {
      ensureParameterizedGetterExists(pojoClass, attributeValueType, getterMethodName, getterParameter, attributeName);
      return generateMultiValueAttribute(MultiValueAttribute.class, pojoClass, attributeValueType, attributeName, "object." + getterMethodName + "(\"" + getterParameter + "\")");
   }

   public static Class generateMultiValueNullableAttributeForField(Class pojoClass, Class attributeValueType, String fieldName, boolean componentValuesNullable, String attributeName) {
      ensureFieldExists(pojoClass, attributeValueType, fieldName, attributeName);
      return generateMultiValueNullableAttribute(MultiValueNullableAttribute.class, pojoClass, attributeValueType, attributeName, componentValuesNullable, "object." + fieldName);
   }

   public static Class generateMultiValueNullableAttributeForGetter(Class pojoClass, Class attributeValueType, String getterMethodName, boolean componentValuesNullable, String attributeName) {
      ensureGetterExists(pojoClass, attributeValueType, getterMethodName, attributeName);
      return generateMultiValueNullableAttribute(MultiValueNullableAttribute.class, pojoClass, attributeValueType, attributeName, componentValuesNullable, "object." + getterMethodName + "()");
   }

   public static Class generateMultiValueNullableAttributeForParameterizedGetter(Class pojoClass, Class attributeValueType, String getterMethodName, String getterParameter, boolean componentValuesNullable, String attributeName) {
      ensureParameterizedGetterExists(pojoClass, attributeValueType, getterMethodName, getterParameter, attributeName);
      return generateMultiValueNullableAttribute(MultiValueNullableAttribute.class, pojoClass, attributeValueType, attributeName, componentValuesNullable, "object." + getterMethodName + "(\"" + getterParameter + "\")");
   }

   private static Class generateSimpleAttribute(Class attributeSuperClass, Class pojoClass, Class attributeValueType, String attributeName, String target) {
      try {
         ClassPool pool = new ClassPool(false);
         pool.appendClassPath(new ClassClassPath(pojoClass));
         CtClass attributeClass = pool.makeClass(pojoClass.getName() + "$$CQEngine_" + attributeSuperClass.getSimpleName() + "_" + attributeName);
         attributeClass.setSuperclass(pool.get(attributeSuperClass.getName()));
         SignatureAttribute.ClassType genericTypeOfAttribute = new SignatureAttribute.ClassType(attributeSuperClass.getName(), new SignatureAttribute.TypeArgument[]{new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(pojoClass.getName())), new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(attributeValueType.getName()))});
         attributeClass.setGenericSignature(genericTypeOfAttribute.encode());
         CtConstructor constructor = CtNewConstructor.make("public " + attributeClass.getSimpleName() + "() { super(\"" + attributeName + "\"); }", attributeClass);
         attributeClass.addConstructor(constructor);
         CtMethod getterMethod = CtMethod.make("public " + attributeValueType.getName() + " getValue(" + pojoClass.getName() + " object, " + QueryOptions.class.getName() + " queryOptions) { return (" + attributeValueType.getName() + ") " + GeneratedAttributeSupport.class.getName() + ".valueOf(" + target + "); }", attributeClass);
         attributeClass.addMethod(getterMethod);
         CtMethod getterBridgeMethod = CtMethod.make("public java.lang.Object getValue(java.lang.Object object, " + QueryOptions.class.getName() + " queryOptions) { return getValue((" + pojoClass.getName() + ")object, queryOptions); }", attributeClass);
         getterBridgeMethod.setModifiers(getterBridgeMethod.getModifiers() | 64);
         attributeClass.addMethod(getterBridgeMethod);
         Class result = attributeClass.toClass(pojoClass.getClassLoader(), pojoClass.getProtectionDomain());
         attributeClass.detach();
         return result;
      } catch (Exception var12) {
         throw new IllegalStateException(getExceptionMessage(pojoClass, attributeValueType, attributeName), var12);
      }
   }

   private static Class generateMultiValueAttribute(Class attributeSuperClass, Class pojoClass, Class attributeValueType, String attributeName, String target) {
      try {
         ClassPool pool = new ClassPool(false);
         pool.appendClassPath(new ClassClassPath(pojoClass));
         CtClass attributeClass = pool.makeClass(pojoClass.getName() + "$$CQEngine_" + attributeSuperClass.getSimpleName() + "_" + attributeName);
         attributeClass.setSuperclass(pool.get(attributeSuperClass.getName()));
         SignatureAttribute.ClassType genericTypeOfAttribute = new SignatureAttribute.ClassType(attributeSuperClass.getName(), new SignatureAttribute.TypeArgument[]{new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(pojoClass.getName())), new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(attributeValueType.getName()))});
         attributeClass.setGenericSignature(genericTypeOfAttribute.encode());
         CtConstructor constructor = CtNewConstructor.make("public " + attributeClass.getSimpleName() + "() { super(\"" + attributeName + "\"); }", attributeClass);
         attributeClass.addConstructor(constructor);
         CtMethod getterMethod = CtMethod.make("public java.lang.Iterable getValues(" + pojoClass.getName() + " object, " + QueryOptions.class.getName() + " queryOptions) { return " + GeneratedAttributeSupport.class.getName() + ".valueOf(" + target + "); }", attributeClass);
         getterMethod.setGenericSignature((new SignatureAttribute.MethodSignature(new SignatureAttribute.TypeParameter[0], new SignatureAttribute.Type[]{new SignatureAttribute.ClassType(pojoClass.getName())}, new SignatureAttribute.ClassType(Iterable.class.getName(), new SignatureAttribute.TypeArgument[]{new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(attributeValueType.getName()))}), new SignatureAttribute.ObjectType[0])).encode());
         attributeClass.addMethod(getterMethod);
         CtMethod getterBridgeMethod = CtMethod.make("public java.lang.Iterable getValues(java.lang.Object object, " + QueryOptions.class.getName() + " queryOptions) { return getValues((" + pojoClass.getName() + ")object, queryOptions); }", attributeClass);
         getterBridgeMethod.setModifiers(getterBridgeMethod.getModifiers() | 64);
         attributeClass.addMethod(getterBridgeMethod);
         Class result = attributeClass.toClass(pojoClass.getClassLoader(), pojoClass.getProtectionDomain());
         attributeClass.detach();
         return result;
      } catch (Exception var12) {
         throw new IllegalStateException(getExceptionMessage(pojoClass, attributeValueType, attributeName), var12);
      }
   }

   private static Class generateMultiValueNullableAttribute(Class attributeSuperClass, Class pojoClass, Class attributeValueType, String attributeName, boolean componentValuesNullable, String target) {
      try {
         ClassPool pool = new ClassPool(false);
         pool.appendClassPath(new ClassClassPath(pojoClass));
         CtClass attributeClass = pool.makeClass(pojoClass.getName() + "$$CQEngine_" + attributeSuperClass.getSimpleName() + "_" + attributeName);
         attributeClass.setSuperclass(pool.get(attributeSuperClass.getName()));
         SignatureAttribute.ClassType genericTypeOfAttribute = new SignatureAttribute.ClassType(attributeSuperClass.getName(), new SignatureAttribute.TypeArgument[]{new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(pojoClass.getName())), new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(attributeValueType.getName()))});
         attributeClass.setGenericSignature(genericTypeOfAttribute.encode());
         CtConstructor constructor = CtNewConstructor.make("public " + attributeClass.getSimpleName() + "() { super(\"" + attributeName + "\", " + componentValuesNullable + "); }", attributeClass);
         attributeClass.addConstructor(constructor);
         CtMethod getterMethod = CtMethod.make("public java.lang.Iterable getNullableValues(" + pojoClass.getName() + " object, " + QueryOptions.class.getName() + " queryOptions) { return " + GeneratedAttributeSupport.class.getName() + ".valueOf(" + target + "); }", attributeClass);
         getterMethod.setGenericSignature((new SignatureAttribute.MethodSignature(new SignatureAttribute.TypeParameter[0], new SignatureAttribute.Type[]{new SignatureAttribute.ClassType(pojoClass.getName())}, new SignatureAttribute.ClassType(Iterable.class.getName(), new SignatureAttribute.TypeArgument[]{new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(attributeValueType.getName()))}), new SignatureAttribute.ObjectType[0])).encode());
         attributeClass.addMethod(getterMethod);
         CtMethod getterBridgeMethod = CtMethod.make("public java.lang.Iterable getNullableValues(java.lang.Object object, " + QueryOptions.class.getName() + " queryOptions) { return getNullableValues((" + pojoClass.getName() + ")object, queryOptions); }", attributeClass);
         getterBridgeMethod.setModifiers(getterBridgeMethod.getModifiers() | 64);
         attributeClass.addMethod(getterBridgeMethod);
         Class result = attributeClass.toClass(pojoClass.getClassLoader(), pojoClass.getProtectionDomain());
         attributeClass.detach();
         return result;
      } catch (Exception var13) {
         throw new IllegalStateException(getExceptionMessage(pojoClass, attributeValueType, attributeName), var13);
      }
   }

   static String getClassName(Class cls) {
      return cls != null ? cls.getName() : null;
   }

   static void ensureFieldExists(Class pojoClass, Class attributeValueType, String fieldName, String attributeName) {
      try {
         while(pojoClass != null) {
            try {
               pojoClass.getDeclaredField(fieldName);
               return;
            } catch (NoSuchFieldException var5) {
               pojoClass = pojoClass.getSuperclass();
            }
         }

         throw new NoSuchFieldException(fieldName);
      } catch (Exception var6) {
         throw new IllegalStateException(getExceptionMessage(pojoClass, attributeValueType, attributeName), var6);
      }
   }

   static void ensureGetterExists(Class pojoClass, Class attributeValueType, String getterMethodName, String attributeName) {
      try {
         while(pojoClass != null) {
            try {
               pojoClass.getDeclaredMethod(getterMethodName);
               return;
            } catch (NoSuchMethodException var5) {
               pojoClass = pojoClass.getSuperclass();
            }
         }

         throw new NoSuchMethodException(getterMethodName);
      } catch (Exception var6) {
         throw new IllegalStateException(getExceptionMessage(pojoClass, attributeValueType, attributeName), var6);
      }
   }

   static void ensureParameterizedGetterExists(Class pojoClass, Class attributeValueType, String parameterizedGetterMethodName, String getterParameter, String attributeName) {
      try {
         if (!getterParameter.contains("\"") && !getterParameter.contains("\\")) {
            while(pojoClass != null) {
               try {
                  pojoClass.getDeclaredMethod(parameterizedGetterMethodName, String.class);
                  return;
               } catch (NoSuchMethodException var6) {
                  pojoClass = pojoClass.getSuperclass();
               }
            }

            throw new NoSuchMethodException(parameterizedGetterMethodName + "(String)");
         } else {
            throw new IllegalArgumentException("Getter parameter contains unsupported characters: " + getterParameter);
         }
      } catch (Exception var7) {
         throw new IllegalStateException(getExceptionMessage(pojoClass, attributeValueType, attributeName), var7);
      }
   }

   static String getExceptionMessage(Class pojoClass, Class attributeValueType, String attributeName) {
      return "Failed to generate attribute for class " + getClassName(pojoClass) + ", type " + getClassName(attributeValueType) + ", name '" + attributeName + "'";
   }

   static Class getWrapperForPrimitive(Class primitiveType) {
      Class wrapperType = (Class)PRIMITIVES_TO_WRAPPERS.get(primitiveType);
      if (wrapperType == null) {
         throw new IllegalStateException("No wrapper type for primitive type: " + primitiveType);
      } else {
         return wrapperType;
      }
   }
}
