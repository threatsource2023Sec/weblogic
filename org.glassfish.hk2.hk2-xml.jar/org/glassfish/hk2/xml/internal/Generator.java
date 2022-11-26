package org.glassfish.hk2.xml.internal;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.CtField.Initializer;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ByteMemberValue;
import javassist.bytecode.annotation.CharMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.DoubleMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.FloatMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.ShortMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.glassfish.hk2.xml.internal.alt.AdapterInformation;
import org.glassfish.hk2.xml.internal.alt.AltAnnotation;
import org.glassfish.hk2.xml.internal.alt.AltClass;
import org.glassfish.hk2.xml.internal.alt.AltEnum;
import org.glassfish.hk2.xml.internal.alt.AltMethod;
import org.glassfish.hk2.xml.internal.alt.MethodInformationI;
import org.glassfish.hk2.xml.internal.alt.clazz.AnnotationAltAnnotationImpl;
import org.glassfish.hk2.xml.internal.alt.clazz.ClassAltClassImpl;
import org.glassfish.hk2.xml.jaxb.internal.XmlElementImpl;
import org.glassfish.hk2.xml.jaxb.internal.XmlRootElementImpl;
import org.jvnet.hk2.annotations.Contract;

public class Generator {
   private static final boolean DEBUG_METHODS = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
      public Boolean run() {
         return Boolean.parseBoolean(System.getProperty("org.jvnet.hk2.xmlservice.compiler.methods", "false"));
      }
   });
   public static final String JAXB_DEFAULT_STRING = "##default";
   public static final String JAXB_DEFAULT_DEFAULT = "\u0000";
   public static final String NO_CHILD_PACKAGE = "java.";
   public static final String STATIC_GET_MODEL_METHOD_NAME = "__getModel";
   private static final String QUOTE = "\"";
   private static final Set NO_COPY_ANNOTATIONS = new HashSet(Arrays.asList(Contract.class.getName(), XmlTransient.class.getName(), Hk2XmlPreGenerate.class.getName(), "org.jvnet.hk2.config.Configured"));
   private static final Set NO_COPY_ANNOTATIONS_METHOD = new HashSet(Arrays.asList("javax.validation.Valid"));

   public static CtClass generate(AltClass convertMe, CtClass superClazz, ClassPool defaultClassPool) throws Throwable {
      String modelOriginalInterface = convertMe.getName();
      String modelTranslatedClass = Utilities.getProxyNameFromInterfaceName(modelOriginalInterface);
      if (DEBUG_METHODS) {
         Logger.getLogger().debug("Converting " + convertMe.getName() + " to " + modelTranslatedClass);
      }

      CtClass targetCtClass = defaultClassPool.makeClass(modelTranslatedClass);
      ClassFile targetClassFile = targetCtClass.getClassFile();
      targetClassFile.setVersionToJava5();
      ConstPool targetConstPool = targetClassFile.getConstPool();
      ModelImpl compiledModel = new ModelImpl(modelOriginalInterface, modelTranslatedClass);
      AnnotationsAttribute ctAnnotations = null;
      String[] propOrder = null;
      Iterator var11 = convertMe.getAnnotations().iterator();

      while(var11.hasNext()) {
         AltAnnotation convertMeAnnotation = (AltAnnotation)var11.next();
         if (!NO_COPY_ANNOTATIONS.contains(convertMeAnnotation.annotationType())) {
            if (ctAnnotations == null) {
               ctAnnotations = new AnnotationsAttribute(targetConstPool, "RuntimeVisibleAnnotations");
            }

            if (XmlRootElement.class.getName().equals(convertMeAnnotation.annotationType())) {
               QName modelRootName = GeneratorUtilities.convertXmlRootElementName(convertMeAnnotation, convertMe);
               compiledModel.setRootName(modelRootName);
               String modelRootNameNamespace = QNameUtilities.getNamespace(modelRootName);
               String modelRootNameKey = modelRootName.getLocalPart();
               XmlRootElement replacement = new XmlRootElementImpl(modelRootNameNamespace, modelRootNameKey);
               createAnnotationCopy(targetConstPool, (Annotation)replacement, ctAnnotations);
            } else {
               createAnnotationCopy(targetConstPool, convertMeAnnotation, ctAnnotations);
            }

            if (XmlType.class.getName().equals(convertMeAnnotation.annotationType())) {
               propOrder = convertMeAnnotation.getStringArrayValue("propOrder");
            }
         }
      }

      if (ctAnnotations != null) {
         targetClassFile.addAttribute(ctAnnotations);
      }

      CtClass originalCtClass = defaultClassPool.getOrNull(convertMe.getName());
      if (originalCtClass == null) {
         originalCtClass = defaultClassPool.makeInterface(convertMe.getName());
      }

      targetCtClass.setSuperclass(superClazz);
      targetCtClass.addInterface(originalCtClass);
      NameInformation xmlNameMap = GeneratorUtilities.getXmlNameMap(convertMe);
      Set alreadyAddedNaked = new LinkedHashSet();
      List allMethods = convertMe.getMethods();
      if (DEBUG_METHODS) {
         Logger.getLogger().debug("Analyzing " + allMethods.size() + " methods of " + convertMe.getName());
      }

      allMethods = Utilities.prioritizeMethods(allMethods, propOrder, xmlNameMap);
      Set setters = new LinkedHashSet();
      Map getters = new LinkedHashMap();
      Map elementsMethods = new LinkedHashMap();

      Iterator var18;
      String miRepPropNamespace;
      String miRepProp;
      String adapterClass;
      String name;
      String cast;
      String superMethodName;
      String function;
      CtMethod addMeCtMethod;
      label440:
      for(var18 = allMethods.iterator(); var18.hasNext(); targetCtClass.addMethod(addMeCtMethod)) {
         AltMethod wrapper = (AltMethod)var18.next();
         MethodInformationI mi = Utilities.getMethodInformation(wrapper, xmlNameMap);
         if (mi.isKey()) {
            compiledModel.setKeyProperty(mi.getRepresentedProperty());
         }

         miRepPropNamespace = QNameUtilities.getNamespace(mi.getRepresentedProperty());
         miRepProp = mi.getRepresentedProperty() == null ? null : mi.getRepresentedProperty().getLocalPart();
         if (!MethodType.CUSTOM.equals(mi.getMethodType())) {
            createInterfaceForAltClassIfNeeded(mi.getGetterSetterType(), defaultClassPool);
         } else {
            AltMethod original = mi.getOriginalMethod();
            AltClass originalReturn = original.getReturnType();
            if (!ClassAltClassImpl.VOID.equals(originalReturn)) {
               createInterfaceForAltClassIfNeeded(originalReturn, defaultClassPool);
            }

            Iterator var25 = original.getParameterTypes().iterator();

            while(var25.hasNext()) {
               AltClass parameter = (AltClass)var25.next();
               createInterfaceForAltClassIfNeeded(parameter, defaultClassPool);
            }
         }

         if (DEBUG_METHODS) {
            Logger.getLogger().debug("Analyzing method " + mi + " of " + convertMe.getSimpleName());
         }

         name = wrapper.getName();
         StringBuffer sb = new StringBuffer("public ");
         AltClass originalRetType = wrapper.getReturnType();
         boolean isVoid;
         if (originalRetType != null && !Void.TYPE.getName().equals(originalRetType.getName())) {
            sb.append(getCompilableClass(originalRetType) + " ");
            isVoid = false;
         } else {
            sb.append("void ");
            isVoid = true;
         }

         sb.append(name + "(");
         AltClass childType = null;
         boolean getterOrSetter = false;
         boolean isReference = false;
         boolean isSetter = false;
         String originalMethodName;
         String cast;
         String superMethodName;
         if (MethodType.SETTER.equals(mi.getMethodType())) {
            getterOrSetter = true;
            isSetter = true;
            setters.add(mi.getRepresentedProperty().getLocalPart());
            childType = mi.getBaseChildType();
            isReference = mi.isReference();
            sb.append(getCompilableClass(mi.getGetterSetterType()) + " arg0) { super._setProperty(\"" + miRepPropNamespace + "\",\"" + miRepProp + "\", arg0); }");
         } else if (MethodType.GETTER.equals(mi.getMethodType())) {
            getterOrSetter = true;
            getters.put(mi.getRepresentedProperty().getLocalPart(), mi);
            childType = mi.getBaseChildType();
            isReference = mi.isReference();
            cast = "";
            superMethodName = "_getProperty";
            if (Integer.TYPE.getName().equals(mi.getGetterSetterType().getName())) {
               superMethodName = superMethodName + "I";
            } else if (Long.TYPE.getName().equals(mi.getGetterSetterType().getName())) {
               superMethodName = superMethodName + "J";
            } else if (Boolean.TYPE.getName().equals(mi.getGetterSetterType().getName())) {
               superMethodName = superMethodName + "Z";
            } else if (Byte.TYPE.getName().equals(mi.getGetterSetterType().getName())) {
               superMethodName = superMethodName + "B";
            } else if (Character.TYPE.getName().equals(mi.getGetterSetterType().getName())) {
               superMethodName = superMethodName + "C";
            } else if (Short.TYPE.getName().equals(mi.getGetterSetterType().getName())) {
               superMethodName = superMethodName + "S";
            } else if (Float.TYPE.getName().equals(mi.getGetterSetterType().getName())) {
               superMethodName = superMethodName + "F";
            } else if (Double.TYPE.getName().equals(mi.getGetterSetterType().getName())) {
               superMethodName = superMethodName + "D";
            } else {
               cast = "(" + getCompilableClass(mi.getGetterSetterType()) + ") ";
            }

            sb.append(") { return " + cast + "super." + superMethodName + "(\"" + miRepPropNamespace + "\",\"" + miRepProp + "\"); }");
         } else if (MethodType.LOOKUP.equals(mi.getMethodType())) {
            sb.append("java.lang.String arg0) { return (" + getCompilableClass(originalRetType) + ") super._lookupChild(\"" + miRepPropNamespace + "\",\"" + miRepProp + "\", arg0); }");
         } else if (MethodType.ADD.equals(mi.getMethodType())) {
            cast = "";
            if (!isVoid) {
               createInterfaceForAltClassIfNeeded(originalRetType, defaultClassPool);
               cast = "return (" + getCompilableClass(originalRetType) + ") ";
            }

            List paramTypes = wrapper.getParameterTypes();
            if (paramTypes.size() == 0) {
               sb.append(") { " + cast + "super._doAdd(\"" + miRepPropNamespace + "\",\"" + miRepProp + "\", null, null, -1); }");
            } else if (paramTypes.size() == 1) {
               createInterfaceForAltClassIfNeeded((AltClass)paramTypes.get(0), defaultClassPool);
               sb.append(((AltClass)paramTypes.get(0)).getName() + " arg0) { " + cast + "super._doAdd(\"" + miRepPropNamespace + "\",\"" + miRepProp + "\",");
               if (((AltClass)paramTypes.get(0)).isInterface()) {
                  sb.append("arg0, null, -1); }");
               } else if (String.class.getName().equals(((AltClass)paramTypes.get(0)).getName())) {
                  sb.append("null, arg0, -1); }");
               } else {
                  sb.append("null, null, arg0); }");
               }
            } else {
               sb.append(((AltClass)paramTypes.get(0)).getName() + " arg0, int arg1) { " + cast + "super._doAdd(\"" + miRepPropNamespace + "\",\"" + miRepProp + "\",");
               if (((AltClass)paramTypes.get(0)).isInterface()) {
                  createInterfaceForAltClassIfNeeded((AltClass)paramTypes.get(0), defaultClassPool);
                  sb.append("arg0, null, arg1); }");
               } else {
                  sb.append("null, arg0, arg1); }");
               }
            }
         } else {
            List paramTypes;
            if (MethodType.REMOVE.equals(mi.getMethodType())) {
               paramTypes = wrapper.getParameterTypes();
               superMethodName = "";
               function = "super._doRemoveZ(\"";
               originalMethodName = "return";
               if (!Boolean.TYPE.getName().equals(originalRetType.getName())) {
                  if (Void.TYPE.getName().equals(originalRetType.getName())) {
                     originalMethodName = "";
                  } else {
                     superMethodName = "(" + getCompilableClass(originalRetType) + ") ";
                  }

                  function = "super._doRemove(\"";
               }

               if (paramTypes.size() == 0) {
                  sb.append(") { " + originalMethodName + " " + superMethodName + function + miRepPropNamespace + "\",\"" + miRepProp + "\", null, -1, null); }");
               } else if (String.class.getName().equals(((AltClass)paramTypes.get(0)).getName())) {
                  sb.append("java.lang.String arg0) { " + originalMethodName + " " + superMethodName + function + miRepPropNamespace + "\",\"" + miRepProp + "\", arg0, -1, null); }");
               } else if (Integer.TYPE.getName().equals(((AltClass)paramTypes.get(0)).getName())) {
                  sb.append("int arg0) { " + originalMethodName + " " + superMethodName + function + miRepPropNamespace + "\",\"" + miRepProp + "\", null, arg0, null); }");
               } else {
                  sb.append(((AltClass)paramTypes.get(0)).getName() + " arg0) { " + originalMethodName + " " + superMethodName + function + miRepPropNamespace + "\",\"" + miRepProp + "\", null, -1, arg0); }");
               }
            } else {
               if (!MethodType.CUSTOM.equals(mi.getMethodType())) {
                  throw new AssertionError("Unknown method type: " + mi.getMethodType());
               }

               paramTypes = wrapper.getParameterTypes();
               StringBuffer classSets = new StringBuffer();
               StringBuffer valSets = new StringBuffer();
               int lcv = 0;

               for(Iterator var35 = paramTypes.iterator(); var35.hasNext(); ++lcv) {
                  AltClass paramType = (AltClass)var35.next();
                  createInterfaceForAltClassIfNeeded(paramType, defaultClassPool);
                  if (lcv == 0) {
                     sb.append(getCompilableClass(paramType) + " arg" + lcv);
                  } else {
                     sb.append(", " + getCompilableClass(paramType) + " arg" + lcv);
                  }

                  classSets.append("mParams[" + lcv + "] = " + getCompilableClass(paramType) + ".class;\n");
                  valSets.append("mVars[" + lcv + "] = ");
                  if (Integer.TYPE.getName().equals(paramType.getName())) {
                     valSets.append("new java.lang.Integer(arg" + lcv + ");\n");
                  } else if (Long.TYPE.getName().equals(paramType.getName())) {
                     valSets.append("new java.lang.Long(arg" + lcv + ");\n");
                  } else if (Boolean.TYPE.getName().equals(paramType.getName())) {
                     valSets.append("new java.lang.Boolean(arg" + lcv + ");\n");
                  } else if (Byte.TYPE.getName().equals(paramType.getName())) {
                     valSets.append("new java.lang.Byte(arg" + lcv + ");\n");
                  } else if (Character.TYPE.getName().equals(paramType.getName())) {
                     valSets.append("new java.lang.Character(arg" + lcv + ");\n");
                  } else if (Short.TYPE.getName().equals(paramType.getName())) {
                     valSets.append("new java.lang.Short(arg" + lcv + ");\n");
                  } else if (Float.TYPE.getName().equals(paramType.getName())) {
                     valSets.append("new java.lang.Float(arg" + lcv + ");\n");
                  } else if (Double.TYPE.getName().equals(paramType.getName())) {
                     valSets.append("new java.lang.Double(arg" + lcv + ");\n");
                  } else {
                     valSets.append("arg" + lcv + ";\n");
                  }
               }

               sb.append(") { Class[] mParams = new Class[" + paramTypes.size() + "];\n");
               sb.append("Object[] mVars = new Object[" + paramTypes.size() + "];\n");
               sb.append(classSets.toString());
               sb.append(valSets.toString());
               cast = "";
               superMethodName = "_invokeCustomizedMethod";
               if (Integer.TYPE.getName().equals(originalRetType.getName())) {
                  superMethodName = superMethodName + "I";
               } else if (Long.TYPE.getName().equals(originalRetType.getName())) {
                  superMethodName = superMethodName + "J";
               } else if (Boolean.TYPE.getName().equals(originalRetType.getName())) {
                  superMethodName = superMethodName + "Z";
               } else if (Byte.TYPE.getName().equals(originalRetType.getName())) {
                  superMethodName = superMethodName + "B";
               } else if (Character.TYPE.getName().equals(originalRetType.getName())) {
                  superMethodName = superMethodName + "C";
               } else if (Short.TYPE.getName().equals(originalRetType.getName())) {
                  superMethodName = superMethodName + "S";
               } else if (Float.TYPE.getName().equals(originalRetType.getName())) {
                  superMethodName = superMethodName + "F";
               } else if (Double.TYPE.getName().equals(originalRetType.getName())) {
                  superMethodName = superMethodName + "D";
               } else if (!isVoid) {
                  cast = "(" + getCompilableClass(originalRetType) + ") ";
               }

               if (!isVoid) {
                  sb.append("return " + cast);
               }

               sb.append("super." + superMethodName + "(\"" + name + "\", mParams, mVars);}");
            }
         }

         if (DEBUG_METHODS) {
            Logger.getLogger().debug("Adding method for " + convertMe.getSimpleName() + " with implementation " + sb);
         }

         try {
            addMeCtMethod = CtNewMethod.make(sb.toString(), targetCtClass);
         } catch (CannotCompileException var44) {
            MultiException me = new MultiException(var44);
            me.addError(new AssertionError("Cannot compile method with source " + sb.toString()));
            throw me;
         }

         if (wrapper.isVarArgs()) {
            addMeCtMethod.setModifiers(addMeCtMethod.getModifiers() | 128);
         }

         if (mi.getListParameterizedType() != null) {
            addListGenericSignature(addMeCtMethod, mi.getListParameterizedType(), isSetter);
         }

         MethodInfo methodInfo = addMeCtMethod.getMethodInfo();
         ConstPool methodConstPool = methodInfo.getConstPool();
         ctAnnotations = null;
         Iterator var83 = wrapper.getAnnotations().iterator();

         while(true) {
            while(true) {
               AltAnnotation convertMeAnnotation;
               do {
                  if (!var83.hasNext()) {
                     if (getterOrSetter) {
                        originalMethodName = mi.getOriginalMethodName();
                        List aliases = xmlNameMap.getAliases(mi.getRepresentedProperty().getLocalPart());
                        boolean required = mi.isRequired();
                        String listPType;
                        if (childType == null && aliases == null) {
                           listPType = mi.getListParameterizedType() == null ? null : mi.getListParameterizedType().getName();
                           compiledModel.addNonChild(mi.getRepresentedProperty(), mi.getDefaultValue(), mi.getGetterSetterType().getName(), listPType, false, mi.getFormat(), AliasType.NORMAL, (String)null, required, originalMethodName);
                        } else if (!isReference) {
                           AliasType aType = aliases == null ? AliasType.NORMAL : AliasType.HAS_ALIASES;
                           AltClass useChildType = childType == null ? mi.getListParameterizedType() : childType;
                           AdapterInformation adapterInformation = mi.getAdapterInformation();
                           adapterClass = adapterInformation == null ? null : adapterInformation.getAdapter().getName();
                           if (useChildType.isInterface()) {
                              compiledModel.addChild(useChildType.getName(), QNameUtilities.getNamespace(mi.getRepresentedProperty()), mi.getRepresentedProperty().getLocalPart(), mi.getRepresentedProperty().getLocalPart(), getChildType(mi.isList(), mi.isArray()), mi.getDefaultValue(), aType, mi.getWrapperTag(), adapterClass, required, originalMethodName);
                           } else {
                              compiledModel.addNonChild(QNameUtilities.getNamespace(mi.getRepresentedProperty()), mi.getRepresentedProperty().getLocalPart(), mi.getDefaultValue(), mi.getGetterSetterType().getName(), mi.getListParameterizedType().getName(), false, Format.ELEMENT, aType, (String)null, required, originalMethodName);
                           }

                           if (aliases != null) {
                              Iterator var41 = aliases.iterator();

                              while(var41.hasNext()) {
                                 XmlElementData alias = (XmlElementData)var41.next();
                                 String aliasType = alias.getType();
                                 if (aliasType == null) {
                                    aliasType = useChildType.getName();
                                 }

                                 if (alias.isTypeInterface()) {
                                    compiledModel.addChild(aliasType, alias.getNamespace(), alias.getName(), alias.getAlias(), getChildType(mi.isList(), mi.isArray()), alias.getDefaultValue(), AliasType.IS_ALIAS, mi.getWrapperTag(), adapterClass, required, originalMethodName);
                                 } else {
                                    compiledModel.addNonChild(alias.getNamespace(), alias.getName(), alias.getDefaultValue(), mi.getGetterSetterType().getName(), aliasType, false, Format.ELEMENT, AliasType.IS_ALIAS, alias.getAlias(), required, originalMethodName);
                                 }
                              }
                           }
                        } else {
                           listPType = mi.getListParameterizedType() == null ? null : mi.getListParameterizedType().getName();
                           compiledModel.addNonChild(mi.getRepresentedProperty(), mi.getDefaultValue(), mi.getGetterSetterType().getName(), listPType, true, mi.getFormat(), AliasType.NORMAL, (String)null, required, originalMethodName);
                        }
                     }

                     if (getterOrSetter && childType != null && xmlNameMap.hasNoXmlElement(mi.getRepresentedProperty().getLocalPart()) && !alreadyAddedNaked.contains(mi.getRepresentedProperty())) {
                        alreadyAddedNaked.add(mi.getRepresentedProperty());
                        if (ctAnnotations == null) {
                           ctAnnotations = new AnnotationsAttribute(methodConstPool, "RuntimeVisibleAnnotations");
                        }

                        cast = Utilities.getProxyNameFromInterfaceName(childType.getName());
                        Annotation convertMeAnnotation = new XmlElementImpl("##default", false, false, "##default", "\u0000", cast);
                        if (DEBUG_METHODS) {
                           Logger.getLogger().debug("Adding ghost XmlElement for " + convertMe.getSimpleName() + " with data " + convertMeAnnotation);
                        }

                        createAnnotationCopy(methodConstPool, (Annotation)convertMeAnnotation, ctAnnotations);
                     }

                     if (ctAnnotations != null) {
                        methodInfo.addAttribute(ctAnnotations);
                     }
                     continue label440;
                  }

                  convertMeAnnotation = (AltAnnotation)var83.next();
               } while(NO_COPY_ANNOTATIONS_METHOD.contains(convertMeAnnotation.annotationType()));

               if (ctAnnotations == null) {
                  ctAnnotations = new AnnotationsAttribute(methodConstPool, "RuntimeVisibleAnnotations");
               }

               if (childType != null && XmlElement.class.getName().equals(convertMeAnnotation.annotationType())) {
                  superMethodName = Utilities.getProxyNameFromInterfaceName(childType.getName());
                  Annotation anno = new XmlElementImpl(convertMeAnnotation.getStringValue("name"), convertMeAnnotation.getBooleanValue("nillable"), convertMeAnnotation.getBooleanValue("required"), convertMeAnnotation.getStringValue("namespace"), convertMeAnnotation.getStringValue("defaultValue"), superMethodName);
                  createAnnotationCopy(methodConstPool, (Annotation)anno, ctAnnotations);
               } else if (getterOrSetter && XmlElements.class.getName().equals(convertMeAnnotation.annotationType())) {
                  QName representedProperty = mi.getRepresentedProperty();
                  AltAnnotation[] elements = convertMeAnnotation.getAnnotationArrayValue("value");
                  if (elements == null) {
                     elements = new AltAnnotation[0];
                  }

                  elementsMethods.put(representedProperty.getLocalPart(), new GhostXmlElementData(elements, mi.getGetterSetterType()));
               } else {
                  createAnnotationCopy(methodConstPool, convertMeAnnotation, ctAnnotations);
               }
            }
         }
      }

      var18 = getters.entrySet().iterator();

      String baseSetSetterName;
      String baseGetterName;
      while(var18.hasNext()) {
         Map.Entry getterEntry = (Map.Entry)var18.next();
         String getterProperty = (String)getterEntry.getKey();
         MethodInformationI mi = (MethodInformationI)getterEntry.getValue();
         miRepProp = QNameUtilities.getNamespace(mi.getRepresentedProperty());
         name = mi.getRepresentedProperty().getLocalPart();
         if (!setters.contains(getterProperty)) {
            baseSetSetterName = mi.getOriginalMethod().getName();
            baseGetterName = Utilities.convertToSetter(baseSetSetterName);
            StringBuffer sb = new StringBuffer("private void " + baseGetterName + "(");
            sb.append(getCompilableClass(mi.getGetterSetterType()) + " arg0) { super._setProperty(\"" + miRepProp + "\",\"" + name + "\", arg0); }");
            CtMethod addMeCtMethod = CtNewMethod.make(sb.toString(), targetCtClass);
            targetCtClass.addMethod(addMeCtMethod);
            if (mi.getListParameterizedType() != null) {
               addListGenericSignature(addMeCtMethod, mi.getListParameterizedType(), true);
            }

            if (DEBUG_METHODS) {
               Logger.getLogger().debug("Adding ghost setter method for " + convertMe.getSimpleName() + " with implementation " + sb);
            }
         }
      }

      int elementCount = 0;
      Iterator var53 = elementsMethods.entrySet().iterator();

      while(var53.hasNext()) {
         Map.Entry entry = (Map.Entry)var53.next();
         miRepPropNamespace = (String)entry.getKey();
         GhostXmlElementData gxed = (GhostXmlElementData)entry.getValue();
         AltAnnotation[] xmlElements = gxed.xmlElements;
         baseSetSetterName = "set_" + miRepPropNamespace;
         baseGetterName = "get_" + miRepPropNamespace;
         AltAnnotation[] var66 = xmlElements;
         int var68 = xmlElements.length;

         for(int var69 = 0; var69 < var68; ++var69) {
            AltAnnotation xmlElement = var66[var69];
            String elementName = xmlElement.getStringValue("name");
            cast = xmlElement.getStringValue("namespace");
            superMethodName = baseSetSetterName + "_" + elementCount;
            function = baseGetterName + "_" + elementCount;
            ++elementCount;
            AltClass ac = (AltClass)xmlElement.getAnnotationValues().get("type");
            StringBuffer ghostBufferGetter = new StringBuffer("private void " + superMethodName + "(");
            ghostBufferGetter.append(getCompilableClass(gxed.getterSetterType) + " arg0) { super._setProperty(\"" + cast + "\",\"" + elementName + "\", arg0); }");
            if (DEBUG_METHODS) {
               Logger.getLogger().debug("Adding ghost XmlElements setter method for " + convertMe.getSimpleName() + " with implementation " + ghostBufferGetter);
            }

            CtMethod elementsCtMethodGetter = CtNewMethod.make(ghostBufferGetter.toString(), targetCtClass);
            addListGenericSignature(elementsCtMethodGetter, ac, true);
            targetCtClass.addMethod(elementsCtMethodGetter);
            ghostBufferGetter = new StringBuffer("private " + getCompilableClass(gxed.getterSetterType) + " " + function + "() { return (" + getCompilableClass(gxed.getterSetterType) + ") super._getProperty(\"" + cast + "\",\"" + elementName + "\"); }");
            elementsCtMethodGetter = CtNewMethod.make(ghostBufferGetter.toString(), targetCtClass);
            addListGenericSignature(elementsCtMethodGetter, ac, false);
            MethodInfo elementsMethodInfo = elementsCtMethodGetter.getMethodInfo();
            ConstPool elementsMethodConstPool = elementsMethodInfo.getConstPool();
            AnnotationsAttribute aa = new AnnotationsAttribute(elementsMethodConstPool, "RuntimeVisibleAnnotations");
            if (ac.isInterface()) {
               adapterClass = Utilities.getProxyNameFromInterfaceName(ac.getName());
            } else {
               adapterClass = ac.getName();
            }

            XmlElement xElement = new XmlElementImpl(elementName, xmlElement.getBooleanValue("nillable"), xmlElement.getBooleanValue("required"), xmlElement.getStringValue("namespace"), xmlElement.getStringValue("defaultValue"), adapterClass);
            createAnnotationCopy(elementsMethodConstPool, (Annotation)xElement, aa);
            elementsMethodInfo.addAttribute(aa);
            if (DEBUG_METHODS) {
               Logger.getLogger().debug("Adding ghost XmlElements getter method for " + convertMe.getSimpleName() + " with implementation " + ghostBufferGetter);
               Logger.getLogger().debug("with XmlElement " + xElement);
            }

            targetCtClass.addMethod(elementsCtMethodGetter);
         }
      }

      generateStaticModelFieldAndAbstractMethodImpl(targetCtClass, compiledModel, defaultClassPool);
      return targetCtClass;
   }

   static ChildType getChildType(boolean isList, boolean isArray) {
      if (isList) {
         return ChildType.LIST;
      } else {
         return isArray ? ChildType.ARRAY : ChildType.DIRECT;
      }
   }

   private static void generateStaticModelFieldAndAbstractMethodImpl(CtClass targetCtClass, ModelImpl model, ClassPool defaultClassPool) throws CannotCompileException, NotFoundException {
      StringBuffer sb = new StringBuffer();
      sb.append("private static final org.glassfish.hk2.xml.internal.ModelImpl INIT_MODEL() {\n");
      sb.append("org.glassfish.hk2.xml.internal.ModelImpl retVal = new org.glassfish.hk2.xml.internal.ModelImpl(\"");
      sb.append(model.getOriginalInterface() + "\",\"" + model.getTranslatedClass() + "\");\n");
      String keyPropNamespace;
      String keyPropKey;
      if (model.getRootName() != null) {
         keyPropNamespace = QNameUtilities.getNamespace(model.getRootName());
         keyPropKey = model.getRootName().getLocalPart();
         sb.append("retVal.setRootName(\"" + keyPropNamespace + "\",\"" + keyPropKey + "\");\n");
      }

      if (model.getKeyProperty() != null) {
         keyPropNamespace = QNameUtilities.getNamespace(model.getKeyProperty());
         keyPropKey = model.getKeyProperty().getLocalPart();
         sb.append("retVal.setKeyProperty(\"" + keyPropNamespace + "\",\"" + keyPropKey + "\");\n");
      }

      Map allChildren = model.getAllChildrenDescriptors();
      Iterator var14 = allChildren.entrySet().iterator();

      while(var14.hasNext()) {
         Map.Entry entry = (Map.Entry)var14.next();
         QName xmlTagQName = (QName)entry.getKey();
         ChildDescriptor descriptor = (ChildDescriptor)entry.getValue();
         String xmlTagNamespace = QNameUtilities.getNamespace(xmlTagQName);
         String xmlTag = xmlTagQName.getLocalPart();
         ParentedModel parentedModel = descriptor.getParentedModel();
         if (parentedModel != null) {
            sb.append("retVal.addChild(" + asParameter(parentedModel.getChildInterface()) + "," + asParameter(parentedModel.getChildXmlNamespace()) + "," + asParameter(parentedModel.getChildXmlTag()) + "," + asParameter(parentedModel.getChildXmlAlias()) + "," + asParameter(parentedModel.getChildType()) + "," + asParameter(parentedModel.getGivenDefault()) + "," + asParameter(parentedModel.getAliasType()) + "," + asParameter(parentedModel.getXmlWrapperTag()) + "," + asParameter(parentedModel.getAdapter()) + "," + asBoolean(parentedModel.isRequired()) + "," + asParameter(parentedModel.getOriginalMethodName()) + ");\n");
         } else {
            ChildDataModel childDataModel = descriptor.getChildDataModel();
            sb.append("retVal.addNonChild(" + asParameter(xmlTagNamespace) + "," + asParameter(xmlTag) + "," + asParameter(childDataModel.getDefaultAsString()) + "," + asParameter(childDataModel.getChildType()) + "," + asParameter(childDataModel.getChildListType()) + "," + asBoolean(childDataModel.isReference()) + "," + asParameter(childDataModel.getFormat()) + "," + asParameter(childDataModel.getAliasType()) + "," + asParameter(childDataModel.getXmlAlias()) + "," + asBoolean(childDataModel.isRequired()) + "," + asParameter(childDataModel.getOriginalMethodName()) + ");\n");
         }
      }

      sb.append("return retVal; }");
      if (DEBUG_METHODS) {
         Logger.getLogger().debug("Adding static model generator for " + targetCtClass.getSimpleName() + " with implementation " + sb);
      }

      targetCtClass.addMethod(CtNewMethod.make(sb.toString(), targetCtClass));
      CtClass modelCt = defaultClassPool.get(ModelImpl.class.getName());
      CtField sField = new CtField(modelCt, "MODEL", targetCtClass);
      sField.setModifiers(26);
      targetCtClass.addField(sField, Initializer.byCall(targetCtClass, "INIT_MODEL"));
      CtMethod aMethod = CtNewMethod.make("public org.glassfish.hk2.xml.internal.ModelImpl _getModel() { return MODEL; }", targetCtClass);
      targetCtClass.addMethod(aMethod);
      CtMethod sMethod = CtNewMethod.make("public static final org.glassfish.hk2.xml.internal.ModelImpl __getModel() { return MODEL; }", targetCtClass);
      targetCtClass.addMethod(sMethod);
   }

   private static String asParameter(String me) {
      if (me == null) {
         return "null";
      } else {
         return "\u0000".equals(me) ? "null" : "\"" + me + "\"";
      }
   }

   private static String asBoolean(boolean bool) {
      return bool ? "true" : "false";
   }

   private static String asParameter(Format format) {
      String preCursor = Format.class.getName() + ".";
      switch (format) {
         case ATTRIBUTE:
            return preCursor + "ATTRIBUTE";
         case ELEMENT:
            return preCursor + "ELEMENT";
         case VALUE:
            return preCursor + "VALUE";
         default:
            throw new AssertionError("unknown Format " + format);
      }
   }

   private static String asParameter(ChildType ct) {
      switch (ct) {
         case DIRECT:
            return ChildType.class.getName() + ".DIRECT";
         case LIST:
            return ChildType.class.getName() + ".LIST";
         case ARRAY:
            return ChildType.class.getName() + ".ARRAY";
         default:
            throw new AssertionError("unknown ChildType " + ct);
      }
   }

   private static String asParameter(AliasType ct) {
      switch (ct) {
         case NORMAL:
            return AliasType.class.getName() + ".NORMAL";
         case HAS_ALIASES:
            return AliasType.class.getName() + ".HAS_ALIASES";
         case IS_ALIAS:
            return AliasType.class.getName() + ".IS_ALIAS";
         default:
            throw new AssertionError("unknown ChildType " + ct);
      }
   }

   private static void createAnnotationCopy(ConstPool parent, Annotation javaAnnotation, AnnotationsAttribute retVal) throws Throwable {
      createAnnotationCopy(parent, (AltAnnotation)(new AnnotationAltAnnotationImpl(javaAnnotation, (ClassReflectionHelper)null)), retVal);
   }

   private static void createAnnotationCopy(ConstPool parent, AltAnnotation javaAnnotation, AnnotationsAttribute retVal) throws Throwable {
      javassist.bytecode.annotation.Annotation addMe = createAnnotationCopyOnly(parent, javaAnnotation);
      retVal.addAnnotation(addMe);
   }

   private static javassist.bytecode.annotation.Annotation createAnnotationCopyOnly(ConstPool parent, AltAnnotation javaAnnotation) throws Throwable {
      javassist.bytecode.annotation.Annotation annotation = new javassist.bytecode.annotation.Annotation(javaAnnotation.annotationType(), parent);
      Map annotationValues = javaAnnotation.getAnnotationValues();
      Iterator var4 = annotationValues.entrySet().iterator();

      while(true) {
         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            String valueName = (String)entry.getKey();
            Object value = entry.getValue();
            Class javaAnnotationType = value.getClass();
            if (String.class.equals(javaAnnotationType)) {
               annotation.addMemberValue(valueName, new StringMemberValue((String)value, parent));
            } else if (Boolean.class.equals(javaAnnotationType)) {
               boolean bvalue = (Boolean)value;
               annotation.addMemberValue(valueName, new BooleanMemberValue(bvalue, parent));
            } else if (AltClass.class.isAssignableFrom(javaAnnotationType)) {
               AltClass altJavaAnnotationType = (AltClass)value;
               String sValue;
               if (javaAnnotation.annotationType().equals(XmlElement.class.getName()) && javaAnnotation.getStringValue("getTypeByName") != null) {
                  sValue = javaAnnotation.getStringValue("getTypeByName");
               } else {
                  sValue = altJavaAnnotationType.getName();
               }

               annotation.addMemberValue(valueName, new ClassMemberValue(sValue, parent));
            } else {
               int svalue;
               if (Integer.class.equals(javaAnnotationType)) {
                  svalue = (Integer)value;
                  annotation.addMemberValue(valueName, new IntegerMemberValue(parent, svalue));
               } else if (Long.class.equals(javaAnnotationType)) {
                  long lvalue = (Long)value;
                  annotation.addMemberValue(valueName, new LongMemberValue(lvalue, parent));
               } else if (Double.class.equals(javaAnnotationType)) {
                  double dvalue = (Double)value;
                  annotation.addMemberValue(valueName, new DoubleMemberValue(dvalue, parent));
               } else if (Byte.class.equals(javaAnnotationType)) {
                  byte bvalue = (Byte)value;
                  annotation.addMemberValue(valueName, new ByteMemberValue(bvalue, parent));
               } else if (Character.class.equals(javaAnnotationType)) {
                  char cvalue = (Character)value;
                  annotation.addMemberValue(valueName, new CharMemberValue(cvalue, parent));
               } else if (Short.class.equals(javaAnnotationType)) {
                  svalue = (Short)value;
                  annotation.addMemberValue(valueName, new ShortMemberValue((short)svalue, parent));
               } else if (Float.class.equals(javaAnnotationType)) {
                  float fvalue = (Float)value;
                  annotation.addMemberValue(valueName, new FloatMemberValue(fvalue, parent));
               } else if (AltEnum.class.isAssignableFrom(javaAnnotationType)) {
                  AltEnum evalue = (AltEnum)value;
                  EnumMemberValue jaEnum = new EnumMemberValue(parent);
                  jaEnum.setType(evalue.getDeclaringClass());
                  jaEnum.setValue(evalue.getName());
                  annotation.addMemberValue(valueName, jaEnum);
               } else {
                  if (!javaAnnotationType.isArray()) {
                     throw new AssertionError("Annotation type " + javaAnnotationType.getName() + " is not yet implemented for " + valueName);
                  }

                  Class typeOfArray = javaAnnotationType.getComponentType();
                  MemberValue[] arrayValue;
                  int lcv;
                  if (Integer.TYPE.equals(typeOfArray)) {
                     int[] iVals = (int[])((int[])value);
                     arrayValue = new MemberValue[iVals.length];

                     for(lcv = 0; lcv < iVals.length; ++lcv) {
                        arrayValue[lcv] = new IntegerMemberValue(parent, iVals[lcv]);
                     }
                  } else if (String.class.equals(typeOfArray)) {
                     String[] iVals = (String[])((String[])value);
                     arrayValue = new MemberValue[iVals.length];

                     for(lcv = 0; lcv < iVals.length; ++lcv) {
                        arrayValue[lcv] = new StringMemberValue(iVals[lcv], parent);
                     }
                  } else if (Long.TYPE.equals(typeOfArray)) {
                     long[] iVals = (long[])((long[])value);
                     arrayValue = new MemberValue[iVals.length];

                     for(lcv = 0; lcv < iVals.length; ++lcv) {
                        arrayValue[lcv] = new LongMemberValue(iVals[lcv], parent);
                     }
                  } else if (Boolean.TYPE.equals(typeOfArray)) {
                     boolean[] iVals = (boolean[])((boolean[])value);
                     arrayValue = new MemberValue[iVals.length];

                     for(lcv = 0; lcv < iVals.length; ++lcv) {
                        arrayValue[lcv] = new BooleanMemberValue(iVals[lcv], parent);
                     }
                  } else if (Float.TYPE.equals(typeOfArray)) {
                     float[] iVals = (float[])((float[])value);
                     arrayValue = new MemberValue[iVals.length];

                     for(lcv = 0; lcv < iVals.length; ++lcv) {
                        arrayValue[lcv] = new FloatMemberValue(iVals[lcv], parent);
                     }
                  } else if (Double.TYPE.equals(typeOfArray)) {
                     double[] iVals = (double[])((double[])value);
                     arrayValue = new MemberValue[iVals.length];

                     for(lcv = 0; lcv < iVals.length; ++lcv) {
                        arrayValue[lcv] = new DoubleMemberValue(iVals[lcv], parent);
                     }
                  } else if (Byte.TYPE.equals(typeOfArray)) {
                     byte[] iVals = (byte[])((byte[])value);
                     arrayValue = new MemberValue[iVals.length];

                     for(lcv = 0; lcv < iVals.length; ++lcv) {
                        arrayValue[lcv] = new ByteMemberValue(iVals[lcv], parent);
                     }
                  } else if (Character.TYPE.equals(typeOfArray)) {
                     char[] iVals = (char[])((char[])value);
                     arrayValue = new MemberValue[iVals.length];

                     for(lcv = 0; lcv < iVals.length; ++lcv) {
                        arrayValue[lcv] = new CharMemberValue(iVals[lcv], parent);
                     }
                  } else if (Short.TYPE.equals(typeOfArray)) {
                     short[] iVals = (short[])((short[])value);
                     arrayValue = new MemberValue[iVals.length];

                     for(lcv = 0; lcv < iVals.length; ++lcv) {
                        arrayValue[lcv] = new ShortMemberValue(iVals[lcv], parent);
                     }
                  } else if (AltEnum.class.isAssignableFrom(typeOfArray)) {
                     AltEnum[] iVals = (AltEnum[])((AltEnum[])value);
                     arrayValue = new MemberValue[iVals.length];

                     for(lcv = 0; lcv < iVals.length; ++lcv) {
                        EnumMemberValue jaEnum = new EnumMemberValue(parent);
                        jaEnum.setType(iVals[lcv].getDeclaringClass());
                        jaEnum.setValue(iVals[lcv].getName());
                        arrayValue[lcv] = jaEnum;
                     }
                  } else if (AltClass.class.isAssignableFrom(typeOfArray)) {
                     AltClass[] iVals = (AltClass[])((AltClass[])value);
                     arrayValue = new MemberValue[iVals.length];

                     for(lcv = 0; lcv < iVals.length; ++lcv) {
                        AltClass arrayElementClass = iVals[lcv];
                        arrayValue[lcv] = new ClassMemberValue(arrayElementClass.getName(), parent);
                     }
                  } else {
                     if (!AltAnnotation.class.isAssignableFrom(typeOfArray)) {
                        throw new AssertionError("Array type " + typeOfArray.getName() + " is not yet implemented for " + valueName);
                     }

                     AltAnnotation[] aVals = (AltAnnotation[])((AltAnnotation[])value);
                     arrayValue = new MemberValue[aVals.length];

                     for(lcv = 0; lcv < aVals.length; ++lcv) {
                        AltAnnotation arrayAnnotation = aVals[lcv];
                        arrayValue[lcv] = new AnnotationMemberValue(createAnnotationCopyOnly(parent, arrayAnnotation), parent);
                     }
                  }

                  ArrayMemberValue arrayMemberValue = new ArrayMemberValue(parent);
                  arrayMemberValue.setValue(arrayValue);
                  annotation.addMemberValue(valueName, arrayMemberValue);
               }
            }
         }

         return annotation;
      }
   }

   private static String getCompilableClass(AltClass clazz) {
      int depth;
      for(depth = 0; clazz.isArray(); clazz = clazz.getComponentType()) {
         ++depth;
      }

      StringBuffer sb = new StringBuffer(clazz.getName());

      for(int lcv = 0; lcv < depth; ++lcv) {
         sb.append("[]");
      }

      return sb.toString();
   }

   private static AltClass getUltimateNonArrayClass(AltClass clazz) {
      if (clazz == null) {
         return null;
      } else {
         while(clazz.isArray()) {
            clazz = clazz.getComponentType();
         }

         return clazz;
      }
   }

   private static void createInterfaceForAltClassIfNeeded(AltClass toFix, ClassPool defaultClassPool) {
      if (toFix != null) {
         toFix = getUltimateNonArrayClass(toFix);
         String fixerClass = toFix.getName();
         if (defaultClassPool.getOrNull(fixerClass) == null) {
            defaultClassPool.makeInterface(fixerClass);
         }

      }
   }

   private static void addListGenericSignature(CtMethod method, AltClass listPT, boolean isSetter) {
      SignatureAttribute.ClassType intSignature = new SignatureAttribute.ClassType(listPT.getName());
      SignatureAttribute.TypeArgument[] typeArguments = new SignatureAttribute.TypeArgument[]{new SignatureAttribute.TypeArgument(intSignature)};
      SignatureAttribute.ClassType listSignature = new SignatureAttribute.ClassType(List.class.getName(), typeArguments);
      SignatureAttribute.MethodSignature ms;
      if (isSetter) {
         SignatureAttribute.Type[] params = new SignatureAttribute.Type[]{listSignature};
         ms = new SignatureAttribute.MethodSignature((SignatureAttribute.TypeParameter[])null, params, (SignatureAttribute.Type)null, (SignatureAttribute.ObjectType[])null);
      } else {
         ms = new SignatureAttribute.MethodSignature((SignatureAttribute.TypeParameter[])null, (SignatureAttribute.Type[])null, listSignature, (SignatureAttribute.ObjectType[])null);
      }

      method.setGenericSignature(ms.encode());
   }

   private static class GhostXmlElementData {
      private final AltAnnotation[] xmlElements;
      private final AltClass getterSetterType;

      private GhostXmlElementData(AltAnnotation[] xmlElements, AltClass getterSetterType) {
         this.xmlElements = xmlElements;
         this.getterSetterType = getterSetterType;
      }

      // $FF: synthetic method
      GhostXmlElementData(AltAnnotation[] x0, AltClass x1, Object x2) {
         this(x0, x1);
      }
   }
}
