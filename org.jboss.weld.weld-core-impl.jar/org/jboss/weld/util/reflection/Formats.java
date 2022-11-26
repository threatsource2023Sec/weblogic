package org.jboss.weld.util.reflection;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionPoint;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LineNumberTable;
import org.jboss.classfilewriter.util.DescriptorUtils;
import org.jboss.weld.ejb.spi.BusinessInterfaceDescriptor;
import org.jboss.weld.resources.ClassLoaderResourceLoader;
import org.jboss.weld.resources.WeldClassLoaderResourceLoader;

public class Formats {
   private static final String BCEL_CLASS = "org.apache.bcel.classfile.ClassParser";
   private static final String SNAPSHOT = "SNAPSHOT";
   private static final String NULL = "null";
   private static final String SQUARE_BRACKETS = "[]";
   private static final String INIT_METHOD_NAME = "<init>";
   private static final String BUILD_PROPERTIES_FILE = "weld-build.properties";
   private static final String BUILD_PROPERTIES_VERSION = "version";
   private static final String BUILD_PROPERTIES_TIMESTAMP = "timestamp";
   private static final String UPPER_BOUND = " extends ";
   private static final String WILDCARD = "?";
   private static final String WILDCARD_UPPER_BOUND = "? extends ";
   private static final String WILDCARD_LOWER_BOUND = "? super ";
   private static final String GT = ">";
   private static final String LT = "<";
   private static final Function SPACE_DELIMITER_FUNCTION = new Function() {
      public String apply(Object from, int position) {
         if (position > 0) {
            return " " + (from == null ? "null" : from.toString());
         } else {
            return from == null ? "null" : from.toString();
         }
      }
   };
   private static final Function COMMA_DELIMITER_FUNCTION = new Function() {
      public String apply(Object from, int position) {
         if (position > 0) {
            return ", " + (from == null ? "null" : from.toString());
         } else {
            return from == null ? "null" : from.toString();
         }
      }
   };
   private static final Function ANNOTATION_LIST_FUNCTION = new Function() {
      public String apply(Annotation from, int position) {
         return Formats.spaceDelimiterFunction().apply("@" + from.annotationType().getSimpleName(), position);
      }
   };

   private Formats() {
   }

   public static String formatAsStackTraceElement(InjectionPoint ij) {
      Object member;
      if (ij.getAnnotated() instanceof AnnotatedField) {
         AnnotatedField annotatedField = (AnnotatedField)ij.getAnnotated();
         member = annotatedField.getJavaMember();
      } else {
         if (!(ij.getAnnotated() instanceof AnnotatedParameter)) {
            return "-";
         }

         AnnotatedParameter annotatedParameter = (AnnotatedParameter)ij.getAnnotated();
         member = annotatedParameter.getDeclaringCallable().getJavaMember();
      }

      return formatAsStackTraceElement((Member)member);
   }

   public static String formatAsStackTraceElement(Member member) {
      return member.getDeclaringClass().getName() + "." + (member instanceof Constructor ? "<init>" : member.getName()) + "(" + getFileName(member.getDeclaringClass()) + ":" + getLineNumber(member) + ")";
   }

   public static int getLineNumber(Member member) {
      if (!(member instanceof Method) && !(member instanceof Constructor)) {
         return 0;
      } else if (!Reflections.isClassLoadable("org.apache.bcel.classfile.ClassParser", WeldClassLoaderResourceLoader.INSTANCE)) {
         return 0;
      } else {
         String classFile = member.getDeclaringClass().getName().replace('.', '/');
         ClassLoaderResourceLoader classFileResourceLoader = new ClassLoaderResourceLoader(member.getDeclaringClass().getClassLoader());
         InputStream in = null;

         byte var5;
         try {
            URL classFileUrl = classFileResourceLoader.getResource(classFile + ".class");
            if (classFileUrl != null) {
               in = classFileUrl.openStream();
               ClassParser cp = new ClassParser(in, classFile);
               JavaClass javaClass = cp.parse();
               org.apache.bcel.classfile.Method[] methods = javaClass.getMethods();
               org.apache.bcel.classfile.Method match = null;
               String signature;
               String name;
               byte var30;
               if (member instanceof Method) {
                  signature = DescriptorUtils.methodDescriptor((Method)member);
                  name = member.getName();
               } else {
                  if (!(member instanceof Constructor)) {
                     var30 = 0;
                     return var30;
                  }

                  signature = DescriptorUtils.makeDescriptor((Constructor)member);
                  name = "<init>";
               }

               org.apache.bcel.classfile.Method[] var11 = methods;
               int line = methods.length;

               int var13;
               for(var13 = 0; var13 < line; ++var13) {
                  org.apache.bcel.classfile.Method method = var11[var13];
                  if (method.getName().equals(name) && member.getModifiers() == method.getModifiers() && method.getSignature().equals(signature)) {
                     match = method;
                  }
               }

               if (match != null) {
                  LineNumberTable lineNumberTable = match.getLineNumberTable();
                  if (lineNumberTable != null) {
                     line = lineNumberTable.getSourceLine(0);
                     var13 = line == -1 ? 0 : line;
                     return var13;
                  }
               }

               var30 = 0;
               return var30;
            }

            var5 = 0;
            return var5;
         } catch (Throwable var26) {
            var5 = 0;
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (Exception var25) {
                  return 0;
               }
            }

         }

         return var5;
      }
   }

   private static String getFileName(Class clazz) {
      return clazz.getSimpleName() + ".java";
   }

   private static Function spaceDelimiterFunction() {
      return SPACE_DELIMITER_FUNCTION;
   }

   private static Function commaDelimiterFunction() {
      return COMMA_DELIMITER_FUNCTION;
   }

   public static String formatInjectionPointType(Type type) {
      return type instanceof Class ? ((Class)type).getSimpleName() : formatType(type);
   }

   public static String formatType(Type baseType) {
      return formatType(baseType, true);
   }

   public static String formatType(Type baseType, boolean simpleNames) {
      if (baseType == null) {
         return "null";
      } else if (baseType instanceof Class) {
         Class clazz = (Class)baseType;
         return clazz.isArray() ? formatType(clazz.getComponentType(), simpleNames) + "[]" : getClassName(clazz, simpleNames);
      } else if (baseType instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType)baseType;
         return getClassName((Class)parameterizedType.getRawType(), simpleNames) + formatActualTypeArguments(parameterizedType.getActualTypeArguments());
      } else if (baseType instanceof WildcardType) {
         WildcardType wildcardType = (WildcardType)baseType;
         Type[] upperBound = wildcardType.getUpperBounds();
         Type[] lowerBound = wildcardType.getLowerBounds();
         if (lowerBound.length == 0 && Reflections.isEmptyBoundArray(upperBound)) {
            return "?";
         } else {
            return lowerBound.length == 0 ? "? extends " + formatType(upperBound[0], simpleNames) : "? super " + formatType(lowerBound[0], simpleNames);
         }
      } else if (baseType instanceof GenericArrayType) {
         GenericArrayType gat = (GenericArrayType)baseType;
         return formatType(gat.getGenericComponentType(), simpleNames) + "[]";
      } else {
         return baseType instanceof TypeVariable ? formatTypeVariable((TypeVariable)baseType, simpleNames) : baseType.toString();
      }
   }

   private static String getClassName(Class clazz, boolean simpleNames) {
      return simpleNames ? clazz.getSimpleName() : clazz.getName();
   }

   public static String formatTypes(Iterable baseTypes, final boolean simpleNames) {
      return formatIterable(baseTypes, new Function() {
         public String apply(Type from, int position) {
            return Formats.commaDelimiterFunction().apply(Formats.formatType(from, simpleNames), position);
         }
      });
   }

   public static String formatTypes(Iterable baseTypes) {
      return formatTypes(baseTypes, true);
   }

   public static String formatBusinessInterfaceDescriptors(Iterable businessInterfaceDescriptors) {
      return formatIterable(businessInterfaceDescriptors, new Function() {
         public String apply(BusinessInterfaceDescriptor from, int position) {
            return Formats.commaDelimiterFunction().apply(Formats.formatType(from.getInterface()), position);
         }
      });
   }

   public static String addSpaceIfNeeded(String string) {
      return string.length() > 0 ? string + " " : string;
   }

   public static String formatAsFormalParameterList(Iterable parameters) {
      return "(" + formatIterable(parameters, new Function() {
         public String apply(AnnotatedParameter from, int position) {
            return Formats.commaDelimiterFunction().apply(Formats.formatParameter(from), position);
         }
      }) + ")";
   }

   public static String formatParameter(AnnotatedParameter parameter) {
      return addSpaceIfNeeded(formatAnnotations((Iterable)parameter.getAnnotations())) + formatType(parameter.getBaseType());
   }

   public static String formatModifiers(int modifiers) {
      return formatIterable((Iterable)parseModifiers(modifiers), spaceDelimiterFunction());
   }

   private static String formatIterable(Iterable items, Function function) {
      if (items == null) {
         return "";
      } else {
         StringBuilder stringBuilder = new StringBuilder();
         int i = 0;

         for(Iterator var4 = items.iterator(); var4.hasNext(); ++i) {
            Object item = var4.next();
            stringBuilder.append(function.apply(item, i));
         }

         return stringBuilder.toString();
      }
   }

   private static String formatIterable(Object[] items, Function function) {
      StringBuilder stringBuilder = new StringBuilder();
      int i = 0;
      Object[] var4 = items;
      int var5 = items.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object item = var4[var6];
         stringBuilder.append(function.apply(item, i));
         ++i;
      }

      return stringBuilder.toString();
   }

   private static List parseModifiers(int modifiers) {
      List result = new ArrayList();
      if (Modifier.isPrivate(modifiers)) {
         result.add("private");
      }

      if (Modifier.isProtected(modifiers)) {
         result.add("protected");
      }

      if (Modifier.isPublic(modifiers)) {
         result.add("public");
      }

      if (Modifier.isAbstract(modifiers)) {
         result.add("abstract");
      }

      if (Modifier.isFinal(modifiers)) {
         result.add("final");
      }

      if (Modifier.isNative(modifiers)) {
         result.add("native");
      }

      if (Modifier.isStatic(modifiers)) {
         result.add("static");
      }

      if (Modifier.isStrict(modifiers)) {
         result.add("strict");
      }

      if (Modifier.isSynchronized(modifiers)) {
         result.add("synchronized");
      }

      if (Modifier.isTransient(modifiers)) {
         result.add("transient");
      }

      if (Modifier.isVolatile(modifiers)) {
         result.add("volatile");
      }

      if (Modifier.isInterface(modifiers)) {
         result.add("interface");
      }

      return result;
   }

   public static String formatActualTypeArguments(Type type) {
      return type instanceof ParameterizedType ? formatActualTypeArguments(((ParameterizedType)ParameterizedType.class.cast(type)).getActualTypeArguments()) : "";
   }

   public static String formatActualTypeArguments(Type[] actualTypeArguments) {
      return formatActualTypeArguments(actualTypeArguments, true);
   }

   public static String formatActualTypeArguments(Type[] actualTypeArguments, final boolean simpleNames) {
      return wrapIfNecessary(formatIterable((Object[])actualTypeArguments, new Function() {
         public String apply(Type from, int position) {
            return Formats.commaDelimiterFunction().apply(Formats.formatType(from, simpleNames), position);
         }
      }), "<", ">");
   }

   public static String wrapIfNecessary(String string, String prepend, String append) {
      return string != null && string.length() > 0 ? prepend + string + append : string;
   }

   public static String formatAnnotations(Iterable annotations) {
      return formatIterable(annotations, ANNOTATION_LIST_FUNCTION);
   }

   public static String formatAnnotations(Annotation[] annotations) {
      return formatIterable((Object[])annotations, ANNOTATION_LIST_FUNCTION);
   }

   public static String version(@Deprecated Package pkg) {
      String version = null;
      String timestamp = null;
      Properties buildProperties = getBuildProperties();
      if (buildProperties != null) {
         version = buildProperties.getProperty("version");
         timestamp = buildProperties.getProperty("timestamp");
      }

      if (version == null) {
         version = getManifestImplementationVersion();
      }

      return version(version, timestamp);
   }

   public static String getSimpleVersion() {
      Properties buildProperties = getBuildProperties();
      return buildProperties != null ? buildProperties.getProperty("version") : getManifestImplementationVersion();
   }

   @SuppressFBWarnings(
      value = {"NP_NULL_ON_SOME_PATH_MIGHT_BE_INFEASIBLE"},
      justification = "False positive."
   )
   public static String version(String version, String timestamp) {
      if (version == null && timestamp != null) {
         return timestamp;
      } else if (version == null && timestamp == null) {
         return "SNAPSHOT";
      } else {
         String major = null;
         String minor = null;
         String micro = null;
         String qualifier = null;
         List split = new ArrayList(Arrays.asList(version.split("\\.")));
         String[] split2 = ((String)split.get(split.size() - 1)).split("\\-");
         if (split2.length > 1) {
            split.remove(split.size() - 1);
            split.add(split.size(), split2[0]);
            qualifier = split2[1];
         } else if (split2.length > 0) {
            split.remove(split.size() - 1);
            qualifier = split2[0];
         }

         if (split.size() > 0) {
            major = (String)split.get(0);
         }

         if (split.size() > 1) {
            minor = (String)split.get(1);
         }

         if (split.size() > 2) {
            micro = (String)split.get(2);
         }

         if (major == null && timestamp != null) {
            return timestamp;
         } else if (major == null && timestamp == null) {
            return "SNAPSHOT";
         } else {
            StringBuilder builder = new StringBuilder();
            builder.append(major);
            if (minor != null) {
               builder.append(".").append(minor);
            }

            if (minor != null && micro != null) {
               builder.append(".").append(micro);
            }

            if (qualifier != null) {
               builder.append(" (");
               if (qualifier.equals("SNAPSHOT") && timestamp != null) {
                  builder.append(timestamp);
               } else {
                  builder.append(qualifier);
               }

               builder.append(")");
            }

            return builder.toString();
         }
      }
   }

   public static String formatSimpleClassName(Object object) {
      return formatSimpleClassName(object.getClass());
   }

   public static String formatSimpleClassName(Class javaClass) {
      String simpleName = javaClass.getSimpleName();
      StringBuilder builder = new StringBuilder(simpleName.length() + 2);
      builder.append("[");
      builder.append(simpleName);
      builder.append("]");
      return builder.toString();
   }

   public static String formatAnnotatedType(AnnotatedType type) {
      return formatSimpleClassName((Object)type) + " " + addSpaceIfNeeded(formatModifiers(type.getJavaClass().getModifiers())) + formatAnnotations((Iterable)type.getAnnotations()) + " class " + type.getJavaClass().getName() + formatActualTypeArguments(type.getBaseType());
   }

   public static String formatAnnotatedConstructor(AnnotatedConstructor constructor) {
      return formatSimpleClassName((Object)constructor) + " " + addSpaceIfNeeded(formatAnnotations((Iterable)constructor.getAnnotations())) + addSpaceIfNeeded(formatModifiers(constructor.getJavaMember().getModifiers())) + constructor.getDeclaringType().getJavaClass().getName() + formatAsFormalParameterList(constructor.getParameters());
   }

   public static String formatAnnotatedField(AnnotatedField field) {
      return formatSimpleClassName((Object)field) + " " + addSpaceIfNeeded(formatAnnotations((Iterable)field.getAnnotations())) + addSpaceIfNeeded(formatModifiers(field.getJavaMember().getModifiers())) + field.getDeclaringType().getJavaClass().getName() + "." + field.getJavaMember().getName();
   }

   public static String formatAnnotatedMethod(AnnotatedMethod method) {
      return formatSimpleClassName((Object)method) + " " + addSpaceIfNeeded(formatAnnotations((Iterable)method.getAnnotations())) + addSpaceIfNeeded(formatModifiers(method.getJavaMember().getModifiers())) + method.getDeclaringType().getJavaClass().getName() + "." + method.getJavaMember().getName() + formatAsFormalParameterList(method.getParameters());
   }

   public static String formatAnnotatedParameter(AnnotatedParameter parameter) {
      return formatSimpleClassName((Object)parameter) + " Parameter " + (parameter.getPosition() + 1) + " of " + parameter.getDeclaringCallable().toString();
   }

   public static String getNameOfMissingClassLoaderDependency(Throwable e) {
      if (e instanceof NoClassDefFoundError) {
         if (e.getCause() instanceof ClassNotFoundException) {
            return getNameOfMissingClassLoaderDependency(e.getCause());
         }

         if (e.getMessage() != null) {
            return e.getMessage().replace('/', '.');
         }
      }

      if (e instanceof ClassNotFoundException && e.getMessage() != null) {
         return e.getMessage();
      } else {
         return e.getCause() != null ? getNameOfMissingClassLoaderDependency(e.getCause()) : "[unknown]";
      }
   }

   public static String formatTypeParameters(TypeVariable[] typeParams) {
      return wrapIfNecessary(formatIterable((Object[])typeParams, new Function() {
         public String apply(TypeVariable from, int position) {
            return Formats.spaceDelimiterFunction().apply(Formats.formatTypeVariable(from, true), position);
         }
      }), "<", ">");
   }

   private static String formatTypeVariable(TypeVariable typeVariable, boolean simpleNames) {
      Type[] bounds = typeVariable.getBounds();
      return Reflections.isEmptyBoundArray(bounds) ? typeVariable.getName() : typeVariable.getName() + " extends " + formatType(bounds[0], simpleNames);
   }

   @SuppressFBWarnings(
      value = {"RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE"},
      justification = "False positive, getBuildPropertiesResource() can return null in various situations"
   )
   private static Properties getBuildProperties() {
      Properties buildProperties = null;

      try {
         InputStream in = getBuildPropertiesResource();
         Throwable var2 = null;

         try {
            if (in != null) {
               buildProperties = new Properties();
               buildProperties.load(in);
            }
         } catch (Throwable var12) {
            var2 = var12;
            throw var12;
         } finally {
            if (in != null) {
               if (var2 != null) {
                  try {
                     in.close();
                  } catch (Throwable var11) {
                     var2.addSuppressed(var11);
                  }
               } else {
                  in.close();
               }
            }

         }
      } catch (IOException var14) {
      }

      return buildProperties;
   }

   private static String getManifestImplementationVersion() {
      Package pack = WeldClassLoaderResourceLoader.class.getPackage();
      if (pack == null) {
         throw new IllegalArgumentException("Package can not be null");
      } else {
         return pack.getImplementationVersion();
      }
   }

   private static InputStream getBuildPropertiesResource() {
      URL url = WeldClassLoaderResourceLoader.INSTANCE.getResource("weld-build.properties");

      try {
         return url != null ? url.openStream() : null;
      } catch (IOException var2) {
         return null;
      }
   }

   private interface Function {
      String apply(Object var1, int var2);
   }
}
