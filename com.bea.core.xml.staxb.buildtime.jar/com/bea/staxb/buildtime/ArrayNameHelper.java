package com.bea.staxb.buildtime;

import com.bea.util.jam.JClass;
import javax.xml.namespace.QName;

public class ArrayNameHelper {
   private static final String LITERAL_ARRAY_NAME_PREFIX = "ArrayOf";
   private static final String LITERAL_ARRAY_NAME_POSTFIX = "_literal";
   private static final String XSNS = "http://www.w3.org/2001/XMLSchema";

   public static String arrayComponentNameForJClass(JClass arrayComponentClass, String schemaComponentQNameLocalPart) {
      String arrayComponentName = schemaComponentQNameLocalPart;
      if (arrayComponentClass.getQualifiedName().startsWith("java.util.Date")) {
         arrayComponentName = "JavaUtilDate" + schemaComponentQNameLocalPart;
      } else if (arrayComponentClass.getQualifiedName().startsWith("java.util.Calendar")) {
         arrayComponentName = "JavaUtilCalendar" + schemaComponentQNameLocalPart;
      } else if (arrayComponentClass.getQualifiedName().startsWith("java.lang.Character")) {
         arrayComponentName = "JavaLangCharacter" + schemaComponentQNameLocalPart;
      } else if (arrayComponentClass.getQualifiedName().startsWith("char")) {
         arrayComponentName = "JavaPrimitiveChar" + schemaComponentQNameLocalPart;
      } else if (arrayComponentClass.getQualifiedName().startsWith("java.lang.")) {
         arrayComponentName = "JavaLang" + schemaComponentQNameLocalPart;
      } else if (arrayComponentClass.getQualifiedName().startsWith("java.math.")) {
         arrayComponentName = "JavaMath" + schemaComponentQNameLocalPart;
      } else if (arrayComponentClass.getQualifiedName().startsWith("java.util.")) {
         arrayComponentName = "JavaUtil" + schemaComponentQNameLocalPart;
      }

      return arrayComponentName;
   }

   public static QName getLiteralArrayTypeName(String nsOverride, JClass serviceClass, JClass arrayClass, String schemaComponentQNameLocalPart) {
      ArrayNamespaceInfo info = getArrayNamespaceInfo(nsOverride, serviceClass, arrayClass);
      return getLiteralArrayTypeName(info, schemaComponentQNameLocalPart);
   }

   public static QName getLiteralArrayTypeName(ArrayNamespaceInfo info, String schemaComponentQNameLocalPart) {
      JClass arrayClass = info.getArrayClass();
      int arrayPrefixCount = arrayClass.getArrayDimensions();
      JClass arrayComponentClass = arrayClass.getArrayComponentType();
      String namespace = info.getNamespace();
      String arrayComponentName = arrayComponentNameForJClass(arrayComponentClass, schemaComponentQNameLocalPart);
      if (arrayComponentName.equals("byte") || arrayComponentName.equals("base64Binary")) {
         --arrayPrefixCount;
         arrayComponentName = "base64Binary";
      }

      StringBuffer prefix = new StringBuffer();

      for(int i = 0; i < arrayPrefixCount; ++i) {
         prefix.append("ArrayOf");
      }

      String localName = prefix.toString() + arrayComponentName + "_literal";
      QName arrayTypeQName = new QName(namespace != null ? namespace.intern() : null, localName.intern());
      return arrayTypeQName;
   }

   public static ArrayNamespaceInfo getArrayNamespaceInfo(String nsOverride, JClass serviceClass, JClass arrayClass) {
      assert arrayClass.isArrayType() : " called Java2Schema.createArrayNamespaceInfo on a JClass '" + arrayClass + "' which is not an array type ";

      String ns = nsOverride;
      if (nsOverride == null) {
         boolean isUserDefined = true;
         JClass componentClass = arrayClass.getArrayComponentType();
         if (componentClass.isPrimitiveType()) {
            isUserDefined = false;
         } else if (componentClass.getQualifiedName().startsWith("java.lang.") || componentClass.getQualifiedName().startsWith("java.math.") || componentClass.getQualifiedName().startsWith("java.util.")) {
            isUserDefined = false;
         }

         if (isUserDefined) {
            ns = Java2Schema.getDefaultNsFor(componentClass);
         } else {
            ns = Java2Schema.getDefaultNsFor(serviceClass);
         }
      }

      return new ArrayNamespaceInfo(serviceClass, arrayClass, ns);
   }
}
