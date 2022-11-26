package org.glassfish.hk2.xml.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;
import org.glassfish.hk2.api.AnnotationLiteral;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.xml.api.annotations.PluralOf;
import org.glassfish.hk2.xml.internal.alt.AltAnnotation;
import org.glassfish.hk2.xml.internal.alt.AltClass;
import org.glassfish.hk2.xml.internal.alt.AltMethod;
import org.glassfish.hk2.xml.internal.alt.clazz.AnnotationAltAnnotationImpl;

public class GeneratorUtilities {
   private static final String XML_VALUE_LOCAL_PART = "##XmlValue";
   public static final String XML_ANY_ATTRIBUTE_LOCAL_PART = "##XmlAnyAttribute";

   public static QName convertXmlRootElementName(AltAnnotation root, AltClass clazz) {
      String namespace = root.getStringValue("namespace");
      String rootName = root.getStringValue("name");
      if (!"##default".equals(rootName)) {
         return QNameUtilities.createQName(namespace, rootName);
      } else {
         String simpleName = clazz.getSimpleName();
         char[] asChars = simpleName.toCharArray();
         StringBuffer sb = new StringBuffer();
         boolean firstChar = true;
         boolean lastCharWasCapital = false;
         char[] var9 = asChars;
         int var10 = asChars.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            char asChar = var9[var11];
            if (firstChar) {
               firstChar = false;
               if (Character.isUpperCase(asChar)) {
                  lastCharWasCapital = true;
                  sb.append(Character.toLowerCase(asChar));
               } else {
                  lastCharWasCapital = false;
                  sb.append(asChar);
               }
            } else if (Character.isUpperCase(asChar)) {
               if (!lastCharWasCapital) {
                  sb.append('-');
               }

               sb.append(Character.toLowerCase(asChar));
               lastCharWasCapital = true;
            } else {
               sb.append(asChar);
               lastCharWasCapital = false;
            }
         }

         String localPart = sb.toString();
         return QNameUtilities.createQName(namespace, localPart);
      }
   }

   private static boolean isSpecifiedReference(AltMethod method) {
      AltAnnotation customAnnotation = method.getAnnotation(XmlIDREF.class.getName());
      return customAnnotation != null;
   }

   private static void checkOnlyOne(AltClass convertMe, AltMethod method, AltAnnotation aAnnotation, AltAnnotation bAnnotation) {
      if (aAnnotation != null && bAnnotation != null) {
         throw new IllegalArgumentException("The method " + method + " of " + convertMe + " has both the annotation " + aAnnotation + " and the annotation " + bAnnotation + " which is illegal");
      }
   }

   public static NameInformation getXmlNameMap(AltClass convertMe) {
      Map xmlNameMap = new LinkedHashMap();
      Set unmappedNames = new LinkedHashSet();
      Map addMethodToVariableMap = new LinkedHashMap();
      Map removeMethodToVariableMap = new LinkedHashMap();
      Map lookupMethodToVariableMap = new LinkedHashMap();
      Set referenceSet = new LinkedHashSet();
      Map aliasMap = new LinkedHashMap();
      XmlElementData valueData = null;
      XmlElementData xmlAnyAttributeData = null;
      boolean hasAnElement = false;
      Iterator var11 = convertMe.getMethods().iterator();

      while(true) {
         AltMethod originalMethod;
         String originalMethodName;
         String setterVariable;
         do {
            if (!var11.hasNext()) {
               if (valueData != null && hasAnElement) {
                  throw new IllegalArgumentException("A bean cannot both have XmlElements and XmlValue methods in " + convertMe);
               }

               Set noXmlElementNames = new LinkedHashSet();
               Iterator var38 = unmappedNames.iterator();

               while(var38.hasNext()) {
                  originalMethodName = (String)var38.next();
                  if (!xmlNameMap.containsKey(originalMethodName)) {
                     noXmlElementNames.add(originalMethodName);
                  }
               }

               return new NameInformation(xmlNameMap, noXmlElementNames, addMethodToVariableMap, removeMethodToVariableMap, lookupMethodToVariableMap, referenceSet, aliasMap, valueData);
            }

            originalMethod = (AltMethod)var11.next();
            originalMethodName = originalMethod.getName();
            setterVariable = Utilities.isSetter(originalMethod);
            if (setterVariable != null) {
               break;
            }

            setterVariable = Utilities.isGetter(originalMethod);
         } while(setterVariable == null);

         if (isSpecifiedReference(originalMethod)) {
            referenceSet.add(setterVariable);
         }

         AltAnnotation pluralOf = null;
         AltAnnotation xmlElement = originalMethod.getAnnotation(XmlElement.class.getName());
         AltAnnotation xmlElements = originalMethod.getAnnotation(XmlElements.class.getName());
         AltAnnotation xmlElementWrapper = originalMethod.getAnnotation(XmlElementWrapper.class.getName());
         AltAnnotation xmlAttribute = originalMethod.getAnnotation(XmlAttribute.class.getName());
         AltAnnotation xmlValue = originalMethod.getAnnotation(XmlValue.class.getName());
         AltAnnotation xmlAnyAttribute = originalMethod.getAnnotation(XmlAnyAttribute.class.getName());
         String xmlElementWrapperName = xmlElementWrapper == null ? null : xmlElementWrapper.getStringValue("name");
         if (xmlElementWrapperName != null && xmlElementWrapperName.isEmpty()) {
            xmlElementWrapperName = setterVariable;
         }

         checkOnlyOne(convertMe, originalMethod, xmlElement, xmlElements);
         checkOnlyOne(convertMe, originalMethod, xmlElement, xmlAttribute);
         checkOnlyOne(convertMe, originalMethod, xmlElements, xmlAttribute);
         checkOnlyOne(convertMe, originalMethod, xmlElement, xmlValue);
         checkOnlyOne(convertMe, originalMethod, xmlElements, xmlValue);
         checkOnlyOne(convertMe, originalMethod, xmlAttribute, xmlValue);
         checkOnlyOne(convertMe, originalMethod, xmlElement, xmlAnyAttribute);
         checkOnlyOne(convertMe, originalMethod, xmlElements, xmlAnyAttribute);
         checkOnlyOne(convertMe, originalMethod, xmlAttribute, xmlAnyAttribute);
         checkOnlyOne(convertMe, originalMethod, xmlValue, xmlAnyAttribute);
         String namespace;
         String name;
         if (xmlElements != null) {
            hasAnElement = true;
            pluralOf = originalMethod.getAnnotation(PluralOf.class.getName());
            namespace = "\u0000";
            xmlNameMap.put(setterVariable, new XmlElementData("", setterVariable, setterVariable, namespace, Format.ELEMENT, (String)null, true, xmlElementWrapperName, false, originalMethodName));
            name = setterVariable;
            AltAnnotation[] allXmlElements = xmlElements.getAnnotationArrayValue("value");
            List aliases = new ArrayList(allXmlElements.length);
            aliasMap.put(setterVariable, aliases);
            AltAnnotation[] var27 = allXmlElements;
            int var28 = allXmlElements.length;

            for(int var29 = 0; var29 < var28; ++var29) {
               AltAnnotation allXmlElement = var27[var29];
               namespace = allXmlElement.getStringValue("defaultValue");
               String allXmlElementNamespace = allXmlElement.getStringValue("namespace");
               String allXmlElementName = allXmlElement.getStringValue("name");
               boolean allXmlElementRequired = allXmlElement.getBooleanValue("required");
               AltClass allXmlElementType = (AltClass)allXmlElement.getAnnotationValues().get("type");
               String allXmlElementTypeName = allXmlElementType == null ? null : allXmlElementType.getName();
               boolean allXmlElementTypeInterface = allXmlElementType == null ? true : allXmlElementType.isInterface();
               if ("##default".equals(allXmlElementName)) {
                  throw new IllegalArgumentException("The name field of an XmlElement inside an XmlElements must have a specified name");
               }

               aliases.add(new XmlElementData(allXmlElementNamespace, allXmlElementName, name, namespace, Format.ELEMENT, allXmlElementTypeName, allXmlElementTypeInterface, xmlElementWrapperName, allXmlElementRequired, originalMethodName));
            }
         } else if (xmlElement != null) {
            hasAnElement = true;
            pluralOf = originalMethod.getAnnotation(PluralOf.class.getName());
            namespace = xmlElement.getStringValue("defaultValue");
            name = xmlElement.getStringValue("namespace");
            String name = xmlElement.getStringValue("name");
            boolean required = xmlElement.getBooleanValue("required");
            if ("##default".equals(name)) {
               xmlNameMap.put(setterVariable, new XmlElementData(name, setterVariable, setterVariable, namespace, Format.ELEMENT, (String)null, true, xmlElementWrapperName, required, originalMethodName));
            } else {
               xmlNameMap.put(setterVariable, new XmlElementData(name, name, name, namespace, Format.ELEMENT, (String)null, true, xmlElementWrapperName, required, originalMethodName));
            }
         } else if (xmlAttribute != null) {
            namespace = xmlAttribute.getStringValue("namespace");
            name = xmlAttribute.getStringValue("name");
            boolean required = xmlAttribute.getBooleanValue("required");
            if ("##default".equals(name)) {
               xmlNameMap.put(setterVariable, new XmlElementData(namespace, setterVariable, setterVariable, "\u0000", Format.ATTRIBUTE, (String)null, true, xmlElementWrapperName, required, originalMethodName));
            } else {
               xmlNameMap.put(setterVariable, new XmlElementData(namespace, name, name, "\u0000", Format.ATTRIBUTE, (String)null, true, xmlElementWrapperName, required, originalMethodName));
            }
         } else if (xmlValue != null) {
            if (valueData != null) {
               throw new IllegalArgumentException("There may be only one XmlValue method on " + convertMe);
            }

            valueData = new XmlElementData("##default", "##XmlValue", "##XmlValue", (String)null, Format.VALUE, (String)null, false, xmlElementWrapperName, true, originalMethodName);
            xmlNameMap.put(setterVariable, valueData);
         } else if (xmlAnyAttribute != null) {
            if (xmlAnyAttributeData != null) {
               throw new IllegalArgumentException("There may be only one XmlAnyAttribute method on " + convertMe);
            }

            xmlAnyAttributeData = new XmlElementData("##default", "##XmlAnyAttribute", "##XmlAnyAttribute", (String)null, Format.ATTRIBUTE, (String)null, false, xmlElementWrapperName, false, originalMethodName);
            xmlNameMap.put(setterVariable, xmlAnyAttributeData);
         } else {
            unmappedNames.add(setterVariable);
         }

         if (pluralOf == null) {
            pluralOf = new AnnotationAltAnnotationImpl(new PluralOfDefault(), (ClassReflectionHelper)null);
         }

         namespace = originalMethod.getName().substring(3);
         addMethodToVariableMap.put(getMethodName(MethodType.ADD, namespace, (AltAnnotation)pluralOf), setterVariable);
         removeMethodToVariableMap.put(getMethodName(MethodType.REMOVE, namespace, (AltAnnotation)pluralOf), setterVariable);
         lookupMethodToVariableMap.put(getMethodName(MethodType.LOOKUP, namespace, (AltAnnotation)pluralOf), setterVariable);
      }
   }

   private static String getMethodName(MethodType methodType, String unDecapitalizedVariable, AltAnnotation instructions) {
      String retVal;
      switch (methodType) {
         case ADD:
            retVal = instructions.getStringValue("add");
            break;
         case REMOVE:
            retVal = instructions.getStringValue("remove");
            break;
         case LOOKUP:
            retVal = instructions.getStringValue("lookup");
            break;
         default:
            throw new AssertionError("Only ADD, REMOVE and LOOKUP supported");
      }

      if (!"*".equals(retVal)) {
         return retVal;
      } else {
         String pluralOf = instructions.getStringValue("value");
         if (!"*".equals(pluralOf)) {
            switch (methodType) {
               case ADD:
                  return "add" + pluralOf;
               case REMOVE:
                  return "remove" + pluralOf;
               case LOOKUP:
                  return "lookup" + pluralOf;
               default:
                  throw new AssertionError("Only add, remove and lookup supported");
            }
         } else {
            if (unDecapitalizedVariable.endsWith("s")) {
               unDecapitalizedVariable = unDecapitalizedVariable.substring(0, unDecapitalizedVariable.length() - 1);
            }

            switch (methodType) {
               case ADD:
                  return "add" + unDecapitalizedVariable;
               case REMOVE:
                  return "remove" + unDecapitalizedVariable;
               case LOOKUP:
                  return "lookup" + unDecapitalizedVariable;
               default:
                  throw new AssertionError("Only add, remove and lookup supported");
            }
         }
      }
   }

   private static final class PluralOfDefault extends AnnotationLiteral implements PluralOf {
      private static final long serialVersionUID = 4358923840720264176L;

      private PluralOfDefault() {
      }

      public String value() {
         return "*";
      }

      public String add() {
         return "*";
      }

      public String remove() {
         return "*";
      }

      public String lookup() {
         return "*";
      }

      // $FF: synthetic method
      PluralOfDefault(Object x0) {
         this();
      }
   }
}
