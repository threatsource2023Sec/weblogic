package com.bea.xbean.schema;

import com.bea.xbean.common.NameUtil;
import com.bea.xml.BindingConfig;
import com.bea.xml.InterfaceExtension;
import com.bea.xml.PrePostExtension;
import com.bea.xml.SchemaField;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaStringEnumEntry;
import com.bea.xml.SchemaType;
import com.bea.xml.UserType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlObject;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;

public class StscJavaizer {
   private static final int MAX_ENUM_COUNT = 3668;
   private static final String[] PREFIXES = new String[]{"get", "xget", "isNil", "isSet", "sizeOf", "set", "xset", "addNew", "setNil", "unset", "insert", "add", "insertNew", "addNew", "remove"};
   static String[] PROTECTED_PROPERTIES = new String[]{"StringValue", "BooleanValue", "ByteValue", "ShortValue", "IntValue", "LongValue", "BigIntegerValue", "BigDecimalValue", "FloatValue", "DoubleValue", "ByteArrayValue", "EnumValue", "CalendarValue", "DateValue", "GDateValue", "GDurationValue", "QNameValue", "ListValue", "ObjectValue", "Class"};
   static Set PROTECTED_PROPERTIES_SET;

   public static void javaizeAllTypes(boolean javaize) {
      StscState state = StscState.get();
      List allSeenTypes = new ArrayList();
      allSeenTypes.addAll(Arrays.asList(state.documentTypes()));
      allSeenTypes.addAll(Arrays.asList(state.attributeTypes()));
      allSeenTypes.addAll(Arrays.asList(state.globalTypes()));
      if (javaize) {
         assignGlobalJavaNames(allSeenTypes);
      }

      for(int i = 0; i < allSeenTypes.size(); ++i) {
         SchemaType gType = (SchemaType)allSeenTypes.get(i);
         if (javaize) {
            javaizeType((SchemaTypeImpl)gType);
            String className = gType.getFullJavaName();
            if (className != null) {
               state.addClassname(className.replace('$', '.'), gType);
            }
         } else {
            skipJavaizingType((SchemaTypeImpl)gType);
         }

         allSeenTypes.addAll(Arrays.asList(gType.getAnonymousTypes()));
         addAnonymousTypesFromRedefinition(gType, allSeenTypes);
      }

   }

   static void assignGlobalJavaNames(Collection schemaTypes) {
      HashSet usedNames = new HashSet();
      StscState state = StscState.get();
      Iterator i = schemaTypes.iterator();

      while(i.hasNext()) {
         SchemaTypeImpl sImpl = (SchemaTypeImpl)i.next();
         QName topName = findTopName(sImpl);
         String pickedName = state.getJavaname(topName, sImpl.isDocumentType() ? 2 : 1);
         if (sImpl.isUnjavaized()) {
            sImpl.setFullJavaName(pickFullJavaClassName(usedNames, findTopName(sImpl), pickedName, sImpl.isDocumentType(), sImpl.isAttributeType()));
            sImpl.setFullJavaImplName(pickFullJavaImplName(usedNames, sImpl.getFullJavaName()));
            setUserTypes(sImpl, state);
            setExtensions(sImpl, state);
         }
      }

      verifyInterfaceNameCollisions(usedNames, state);
   }

   private static void verifyInterfaceNameCollisions(Set usedNames, StscState state) {
      BindingConfig config = state.getBindingConfig();
      if (config != null) {
         InterfaceExtension[] exts = config.getInterfaceExtensions();

         for(int i = 0; i < exts.length; ++i) {
            if (usedNames.contains(exts[i].getInterface().toLowerCase())) {
               state.error("InterfaceExtension interface '" + exts[i].getInterface() + "' creates a name collision with one of the generated interfaces or classes.", 0, (XmlObject)null);
            }

            String handler = exts[i].getStaticHandler();
            if (handler != null && usedNames.contains(handler.toLowerCase())) {
               state.error("InterfaceExtension handler class '" + handler + "' creates a name collision with one of the generated interfaces or classes.", 0, (XmlObject)null);
            }
         }

         PrePostExtension[] prepost = config.getPrePostExtensions();

         for(int i = 0; i < prepost.length; ++i) {
            String handler = prepost[i].getStaticHandler();
            if (handler != null && usedNames.contains(handler.toLowerCase())) {
               state.error("PrePostExtension handler class '" + handler + "' creates a name collision with one of the generated interfaces or classes.", 0, (XmlObject)null);
            }
         }

      }
   }

   private static void setUserTypes(SchemaTypeImpl sImpl, StscState state) {
      BindingConfig config = state.getBindingConfig();
      if (config != null) {
         UserType utype = config.lookupUserTypeForQName(sImpl.getName());
         if (utype != null) {
            sImpl.setUserTypeName(utype.getJavaName());
            sImpl.setUserTypeHandlerName(utype.getStaticHandler());
         }
      }

   }

   private static void setExtensions(SchemaTypeImpl sImpl, StscState state) {
      String javaName = sImpl.getFullJavaName();
      BindingConfig config = state.getBindingConfig();
      if (javaName != null && config != null) {
         sImpl.setInterfaceExtensions(config.getInterfaceExtensions(javaName));
         sImpl.setPrePostExtension(config.getPrePostExtension(javaName));
      }

   }

   private static boolean isStringType(SchemaType type) {
      if (type != null && type.getSimpleVariety() == 1) {
         return type.getPrimitiveType().getBuiltinTypeCode() == 12;
      } else {
         return false;
      }
   }

   static String pickConstantName(Set usedNames, String words) {
      String base = NameUtil.upperCaseUnderbar(words);
      if (base.length() == 0) {
         base = "X";
      }

      if (base.startsWith("INT_")) {
         base = "X_" + base;
      }

      int index = 1;

      String uniqName;
      for(uniqName = base; usedNames.contains(uniqName); uniqName = base + "_" + index) {
         ++index;
      }

      usedNames.add(uniqName);
      return uniqName;
   }

   static void skipJavaizingType(SchemaTypeImpl sImpl) {
      if (!sImpl.isJavaized()) {
         SchemaTypeImpl baseType = (SchemaTypeImpl)sImpl.getBaseType();
         if (baseType != null) {
            skipJavaizingType(baseType);
         }

         sImpl.startJavaizing();
         secondPassProcessType(sImpl);
         sImpl.finishJavaizing();
      }
   }

   static void secondPassProcessType(SchemaTypeImpl sImpl) {
      if (isStringType(sImpl)) {
         XmlAnySimpleType[] enumVals = sImpl.getEnumerationValues();
         if (enumVals != null) {
            if (enumVals.length > 3668) {
               StscState.get().warning("SchemaType Enumeration found with too many enumeration values to create a Java enumeration. The base SchemaType \"" + sImpl.getBaseEnumType() + "\" will be used instead", 1, (XmlObject)null);
               sImpl = (SchemaTypeImpl)sImpl.getBaseEnumType();
            } else {
               SchemaStringEnumEntry[] entryArray = new SchemaStringEnumEntry[enumVals.length];
               SchemaType basedOn = sImpl.getBaseEnumType();
               if (basedOn == sImpl) {
                  Set usedNames = new HashSet();

                  for(int i = 0; i < enumVals.length; ++i) {
                     String val = enumVals[i].getStringValue();
                     entryArray[i] = new SchemaStringEnumEntryImpl(val, i + 1, pickConstantName(usedNames, val));
                  }
               } else {
                  for(int i = 0; i < enumVals.length; ++i) {
                     String val = enumVals[i].getStringValue();
                     entryArray[i] = basedOn.enumEntryForString(val);
                  }
               }

               sImpl.setStringEnumEntries(entryArray);
            }
         }
      }

   }

   static void javaizeType(SchemaTypeImpl sImpl) {
      if (!sImpl.isJavaized()) {
         SchemaTypeImpl baseType = (SchemaTypeImpl)sImpl.getBaseType();
         if (baseType != null) {
            javaizeType(baseType);
         }

         if (sImpl.getContentBasedOnType() != null && sImpl.getContentBasedOnType() != baseType) {
            javaizeType((SchemaTypeImpl)sImpl.getContentBasedOnType());
         }

         sImpl.startJavaizing();
         sImpl.setCompiled(true);
         secondPassProcessType(sImpl);
         if (!sImpl.isSimpleType()) {
            SchemaProperty[] eltProps = sImpl.getElementProperties();
            SchemaProperty[] attrProps = sImpl.getAttributeProperties();
            Set usedPropNames = new HashSet();
            SchemaProperty[] baseProps = baseType.getProperties();

            for(int i = 0; i < baseProps.length; ++i) {
               String name = baseProps[i].getJavaPropertyName();

               assert !usedPropNames.contains(name);

               usedPropNames.add(name);
            }

            avoidExtensionMethods(usedPropNames, sImpl);
            boolean doInherited = true;

            while(true) {
               if (eltProps.length > 0) {
                  assignJavaPropertyNames(usedPropNames, eltProps, baseType, doInherited);
               }

               assignJavaPropertyNames(usedPropNames, attrProps, baseType, doInherited);
               if (!doInherited) {
                  SchemaProperty[] allprops = sImpl.getProperties();
                  boolean insensitive = isPropertyModelOrderInsensitive(allprops);
                  assignJavaTypeCodes(allprops);
                  sImpl.setOrderSensitive(!insensitive);
                  break;
               }

               doInherited = false;
            }
         }

         if (sImpl.getFullJavaName() != null || sImpl.getOuterType() != null) {
            assignJavaAnonymousTypeNames(sImpl);
         }

         sImpl.finishJavaizing();
      }
   }

   private static void avoidExtensionMethods(Set usedPropNames, SchemaTypeImpl sImpl) {
      InterfaceExtension[] exts = sImpl.getInterfaceExtensions();
      if (exts != null) {
         for(int i = 0; i < exts.length; ++i) {
            InterfaceExtension ext = exts[i];
            InterfaceExtension.MethodSignature[] methods = ext.getMethods();

            for(int j = 0; j < methods.length; ++j) {
               String methodName = methods[j].getName();

               for(int k = 0; k < PREFIXES.length; ++k) {
                  String prefix = PREFIXES[k];
                  if (methodName.startsWith(prefix)) {
                     usedPropNames.add(methodName.substring(prefix.length()));
                  }
               }
            }
         }
      }

   }

   static void assignJavaAnonymousTypeNames(SchemaTypeImpl outerType) {
      Set usedTypeNames = new HashSet();
      SchemaType[] anonymousTypes = outerType.getAnonymousTypes();
      StscState state = StscState.get();
      int nrOfAnonTypes = anonymousTypes.length;
      if (outerType.isRedefinition()) {
         ArrayList list = new ArrayList();
         addAnonymousTypesFromRedefinition(outerType, list);
         if (list.size() > 0) {
            SchemaType[] temp = new SchemaType[nrOfAnonTypes + list.size()];
            list.toArray(temp);
            System.arraycopy(anonymousTypes, 0, temp, list.size(), nrOfAnonTypes);
            anonymousTypes = temp;
         }
      }

      Object scanOuterType;
      for(scanOuterType = outerType; scanOuterType != null; scanOuterType = ((SchemaType)scanOuterType).getOuterType()) {
         usedTypeNames.add(((SchemaType)scanOuterType).getShortJavaName());
      }

      for(scanOuterType = outerType; scanOuterType != null; scanOuterType = ((SchemaType)scanOuterType).getOuterType()) {
         usedTypeNames.add(((SchemaType)scanOuterType).getShortJavaImplName());
      }

      usedTypeNames.add(getOutermostPackage(outerType.getFullJavaName()));

      for(int i = 0; i < anonymousTypes.length; ++i) {
         SchemaTypeImpl sImpl = (SchemaTypeImpl)anonymousTypes[i];
         if (sImpl != null && !sImpl.isSkippedAnonymousType()) {
            String localname = null;
            String javaname = null;
            SchemaField containerField = sImpl.getContainerField();
            if (containerField != null) {
               QName qname = sImpl.getContainerField().getName();
               localname = qname.getLocalPart();
               javaname = state.getJavaname(sImpl.getContainerField().getName(), 1);
            } else {
               switch (sImpl.getOuterType().getSimpleVariety()) {
                  case 1:
                  default:
                     assert false : "Weird type " + sImpl.toString();

                     javaname = "Base";
                     break;
                  case 2:
                     javaname = "Member";
                     break;
                  case 3:
                     javaname = "Item";
               }
            }

            if (i < nrOfAnonTypes) {
               sImpl.setShortJavaName(pickInnerJavaClassName(usedTypeNames, localname, javaname));
               sImpl.setShortJavaImplName(pickInnerJavaImplName(usedTypeNames, localname, javaname == null ? null : javaname + "Impl"));
            } else {
               sImpl.setFullJavaName(outerType.getFullJavaName() + "$" + pickInnerJavaClassName(usedTypeNames, localname, javaname));
               sImpl.setFullJavaImplName(outerType.getFullJavaImplName() + "$" + pickInnerJavaImplName(usedTypeNames, localname, javaname == null ? null : javaname + "Impl"));
            }

            setExtensions(sImpl, state);
         }
      }

   }

   static void assignJavaPropertyNames(Set usedNames, SchemaProperty[] props, SchemaType baseType, boolean doInherited) {
      StscState state = StscState.get();

      for(int i = 0; i < props.length; ++i) {
         SchemaPropertyImpl sImpl = (SchemaPropertyImpl)props[i];
         SchemaProperty baseProp = sImpl.isAttribute() ? baseType.getAttributeProperty(sImpl.getName()) : baseType.getElementProperty(sImpl.getName());
         if (baseProp != null == doInherited) {
            QName propQName = sImpl.getName();
            String theName;
            if (baseProp == null) {
               theName = pickJavaPropertyName(usedNames, propQName.getLocalPart(), state.getJavaname(propQName, sImpl.isAttribute() ? 4 : 3));
            } else {
               theName = baseProp.getJavaPropertyName();
            }

            sImpl.setJavaPropertyName(theName);
            boolean isArray = sImpl.getMaxOccurs() == null || sImpl.getMaxOccurs().compareTo(BigInteger.ONE) > 0;
            boolean isSingleton = !isArray && sImpl.getMaxOccurs().signum() > 0;
            boolean isOption = isSingleton && sImpl.getMinOccurs().signum() == 0;
            SchemaType javaBasedOnType = sImpl.getType();
            if (baseProp != null) {
               if (baseProp.extendsJavaArray()) {
                  isSingleton = false;
                  isOption = false;
                  isArray = true;
               }

               if (baseProp.extendsJavaSingleton()) {
                  isSingleton = true;
               }

               if (baseProp.extendsJavaOption()) {
                  isOption = true;
               }

               javaBasedOnType = baseProp.javaBasedOnType();
            }

            sImpl.setExtendsJava(javaBasedOnType.getRef(), isSingleton, isOption, isArray);
         }
      }

   }

   static void assignJavaTypeCodes(SchemaProperty[] properties) {
      for(int i = 0; i < properties.length; ++i) {
         SchemaPropertyImpl sImpl = (SchemaPropertyImpl)properties[i];
         SchemaType sType = sImpl.javaBasedOnType();
         sImpl.setJavaTypeCode(javaTypeCodeForType(sType));
      }

   }

   static int javaTypeCodeInCommon(SchemaType[] types) {
      if (types != null && types.length != 0) {
         int code = javaTypeCodeForType(types[0]);
         if (code == 19) {
            return code;
         } else {
            for(int i = 1; i < types.length; ++i) {
               if (code != javaTypeCodeForType(types[i])) {
                  return 19;
               }
            }

            return code;
         }
      } else {
         return 0;
      }
   }

   static int javaTypeCodeForType(SchemaType sType) {
      if (!sType.isSimpleType()) {
         return 0;
      } else if (((SchemaTypeImpl)sType).getUserTypeHandlerName() != null) {
         return 20;
      } else {
         if (sType.getSimpleVariety() == 2) {
            SchemaType baseType = sType.getUnionCommonBaseType();
            if (baseType == null || baseType.isURType()) {
               return javaTypeCodeInCommon(sType.getUnionConstituentTypes());
            }

            sType = baseType;
         }

         if (sType.getSimpleVariety() == 3) {
            return 16;
         } else if (sType.isURType()) {
            return 0;
         } else {
            switch (sType.getPrimitiveType().getBuiltinTypeCode()) {
               case 2:
                  return 10;
               case 3:
                  return 1;
               case 4:
                  return 11;
               case 5:
                  return 11;
               case 6:
                  return 10;
               case 7:
                  return 15;
               case 8:
                  return 0;
               case 9:
                  return 2;
               case 10:
                  return 3;
               case 11:
                  switch (sType.getDecimalSize()) {
                     case 8:
                        return 4;
                     case 16:
                        return 5;
                     case 32:
                        return 6;
                     case 64:
                        return 7;
                     case 1000000:
                        return 9;
                     case 1000001:
                     default:
                        return 8;
                  }
               case 12:
                  if (isStringType(sType.getBaseEnumType())) {
                     if (sType.getEnumerationValues() != null && sType.getEnumerationValues().length > 3668) {
                        return 10;
                     }

                     return 18;
                  }

                  return 10;
               case 13:
                  return 13;
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
                  return 17;
               default:
                  assert false : "unrecognized code " + sType.getPrimitiveType().getBuiltinTypeCode();

                  throw new IllegalStateException("unrecognized code " + sType.getPrimitiveType().getBuiltinTypeCode() + " of " + sType.getPrimitiveType().getName());
            }
         }
      }
   }

   static boolean isPropertyModelOrderInsensitive(SchemaProperty[] properties) {
      for(int i = 0; i < properties.length; ++i) {
         SchemaProperty prop = properties[i];
         if (prop.hasNillable() == 1) {
            return false;
         }

         if (prop.hasDefault() == 1) {
            return false;
         }

         if (prop.hasFixed() == 1) {
            return false;
         }

         if (prop.hasDefault() != 0 && prop.getDefaultText() == null) {
            return false;
         }
      }

      return true;
   }

   static boolean protectReservedGlobalClassNames(String name) {
      int i = name.lastIndexOf(46);
      String lastSegment = name.substring(i + 1);
      return lastSegment.endsWith("Document") && !lastSegment.equals("Document");
   }

   static boolean protectReservedInnerClassNames(String name) {
      return name.equals("Enum") || name.equals("Factory");
   }

   static boolean protectReservedPropertyNames(String name) {
      return PROTECTED_PROPERTIES_SET.contains(name) || name.endsWith("Array") && !name.equals("Array");
   }

   static String pickFullJavaClassName(Set usedNames, QName qName, String configname, boolean isDocument, boolean isAttrType) {
      String base;
      boolean protect;
      String uniqName;
      if (configname != null && configname.indexOf(46) >= 0) {
         base = configname;
         protect = protectReservedGlobalClassNames(configname);
      } else {
         StscState state = StscState.get();
         String uri = qName.getNamespaceURI();
         base = NameUtil.getClassNameFromQName(qName);
         uniqName = state.getPackageOverride(uri);
         if (uniqName != null) {
            base = uniqName + "." + base.substring(base.lastIndexOf(46) + 1);
         }

         String javaPrefix = state.getJavaPrefix(uri);
         if (javaPrefix != null) {
            base = base.substring(0, base.lastIndexOf(46) + 1) + javaPrefix + base.substring(base.lastIndexOf(46) + 1);
         }

         if (configname != null) {
            base = base.substring(0, base.lastIndexOf(46) + 1) + configname;
         }

         protect = protectReservedGlobalClassNames(base);
         if (configname == null) {
            if (isDocument) {
               base = base + "Document";
            } else if (isAttrType) {
               base = base + "Attribute";
            }

            String javaSuffix = state.getJavaSuffix(uri);
            if (javaSuffix != null) {
               base = base + javaSuffix;
            }
         }
      }

      String outermostPkg = getOutermostPackage(base);
      int index = 1;
      if (protect) {
         uniqName = base + index;
      } else {
         uniqName = base;
      }

      while(usedNames.contains(uniqName.toLowerCase()) || uniqName.equals(outermostPkg)) {
         ++index;
         uniqName = base + index;
      }

      usedNames.add(uniqName.toLowerCase());
      return uniqName;
   }

   static String getOutermostPackage(String fqcn) {
      if (fqcn == null) {
         return "";
      } else {
         int lastdot = fqcn.indexOf(46);
         return lastdot < 0 ? "" : fqcn.substring(0, lastdot);
      }
   }

   static String pickFullJavaImplName(Set usedNames, String intfName) {
      String className = intfName;
      String pkgName = null;
      int index = intfName.lastIndexOf(46);
      if (index >= 0) {
         className = intfName.substring(index + 1);
         pkgName = intfName.substring(0, index);
      }

      String base = pkgName + ".impl." + className + "Impl";
      index = 1;

      String uniqName;
      for(uniqName = base; usedNames.contains(uniqName.toLowerCase()); uniqName = base + index) {
         ++index;
      }

      usedNames.add(uniqName.toLowerCase());
      return uniqName;
   }

   static String pickJavaPropertyName(Set usedNames, String localName, String javaName) {
      if (javaName == null) {
         javaName = NameUtil.upperCamelCase(localName);
      }

      boolean protect = protectReservedPropertyNames(javaName);
      int index = 1;
      String uniqName;
      if (protect) {
         uniqName = javaName + index;
      } else {
         uniqName = javaName;
      }

      while(usedNames.contains(uniqName)) {
         ++index;
         uniqName = javaName + index;
      }

      usedNames.add(uniqName);
      return uniqName;
   }

   static String pickInnerJavaClassName(Set usedNames, String localName, String javaName) {
      if (javaName == null) {
         javaName = NameUtil.upperCamelCase(localName);
      }

      boolean protect = protectReservedInnerClassNames(javaName);
      int index = 1;
      String uniqName;
      if (protect) {
         uniqName = javaName + index;
      } else {
         uniqName = javaName;
      }

      while(usedNames.contains(uniqName)) {
         ++index;
         uniqName = javaName + index;
      }

      usedNames.add(uniqName);
      return uniqName;
   }

   static String pickInnerJavaImplName(Set usedNames, String localName, String javaName) {
      if (javaName == null) {
         javaName = NameUtil.upperCamelCase(localName) + "Impl";
      }

      String uniqName = javaName;

      for(int index = 1; usedNames.contains(uniqName); uniqName = javaName + index) {
         ++index;
      }

      usedNames.add(uniqName);
      return uniqName;
   }

   static QName findTopName(SchemaType sType) {
      if (sType.getName() != null) {
         return sType.getName();
      } else if (sType.isDocumentType()) {
         if (sType.getContentModel() != null && sType.getContentModel().getParticleType() == 4) {
            return sType.getDocumentElementName();
         } else {
            throw new IllegalStateException();
         }
      } else if (sType.isAttributeType()) {
         if (sType.getAttributeModel() != null && sType.getAttributeModel().getAttributes().length == 1) {
            return sType.getAttributeTypeAttributeName();
         } else {
            throw new IllegalStateException();
         }
      } else {
         SchemaField sElt = sType.getContainerField();

         assert sElt != null;

         assert sType.getOuterType() == null;

         return sElt.getName();
      }
   }

   static void addAnonymousTypesFromRedefinition(SchemaType sType, List result) {
      while(((SchemaTypeImpl)sType).isRedefinition() && (sType.getDerivationType() == 2 || sType.isSimpleType())) {
         sType = sType.getBaseType();
         SchemaType[] newAnonTypes = sType.getAnonymousTypes();
         if (newAnonTypes.length > 0) {
            result.addAll(Arrays.asList(newAnonTypes));
         }
      }

   }

   static {
      PROTECTED_PROPERTIES_SET = new HashSet(Arrays.asList(PROTECTED_PROPERTIES));
   }
}
