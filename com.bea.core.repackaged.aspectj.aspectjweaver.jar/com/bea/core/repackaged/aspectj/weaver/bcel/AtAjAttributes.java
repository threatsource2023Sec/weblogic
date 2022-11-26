package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Constant;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantUtf8;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Field;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LocalVariable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LocalVariableTable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Method;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Unknown;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.ArrayElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.ClassElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.ElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeVisAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.asm.AsmManager;
import com.bea.core.repackaged.aspectj.asm.IHierarchy;
import com.bea.core.repackaged.aspectj.asm.IProgramElement;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.weaver.AdviceKind;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.BindingScope;
import com.bea.core.repackaged.aspectj.weaver.ConstantPoolReader;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.MethodDelegateTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedPointcutDefinition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareErrorOrWarning;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareParents;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareParentsMixin;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclarePrecedence;
import com.bea.core.repackaged.aspectj.weaver.patterns.FormalBinding;
import com.bea.core.repackaged.aspectj.weaver.patterns.IScope;
import com.bea.core.repackaged.aspectj.weaver.patterns.ParserException;
import com.bea.core.repackaged.aspectj.weaver.patterns.PatternParser;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerCflow;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerFromSuper;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerObject;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerSingleton;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerTypeWithin;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class AtAjAttributes {
   private static final List NO_ATTRIBUTES = Collections.emptyList();
   private static final String[] EMPTY_STRINGS = new String[0];
   private static final String VALUE = "value";
   private static final String ARGNAMES = "argNames";
   private static final String POINTCUT = "pointcut";
   private static final String THROWING = "throwing";
   private static final String RETURNING = "returning";
   private static final String STRING_DESC = "Ljava/lang/String;";

   public static boolean acceptAttribute(Attribute attribute) {
      return attribute instanceof RuntimeVisAnnos;
   }

   public static List readAj5ClassAttributes(AsmManager model, JavaClass javaClass, ReferenceType type, ISourceContext context, IMessageHandler msgHandler, boolean isCodeStyleAspect) {
      boolean ignoreThisClass = javaClass.getClassName().charAt(0) == 'o' && javaClass.getClassName().startsWith("com.bea.core.repackaged.aspectj.lang.annotation");
      if (ignoreThisClass) {
         return NO_ATTRIBUTES;
      } else {
         boolean containsPointcut = false;
         boolean containsAnnotationClassReference = false;
         Constant[] cpool = javaClass.getConstantPool().getConstantPool();

         for(int i = 0; i < cpool.length; ++i) {
            Constant constant = cpool[i];
            if (constant != null && constant.getTag() == 1) {
               String constantValue = ((ConstantUtf8)constant).getValue();
               if (constantValue.length() > 28 && constantValue.charAt(1) == 'o' && constantValue.startsWith("Lorg/aspectj/lang/annotation")) {
                  containsAnnotationClassReference = true;
                  if ("Lorg/aspectj/lang/annotation/DeclareAnnotation;".equals(constantValue)) {
                     msgHandler.handleMessage(new Message("Found @DeclareAnnotation while current release does not support it (see '" + type.getName() + "')", IMessage.WARNING, (Throwable)null, type.getSourceLocation()));
                  }

                  if ("Lorg/aspectj/lang/annotation/Pointcut;".equals(constantValue)) {
                     containsPointcut = true;
                  }
               }
            }
         }

         if (!containsAnnotationClassReference) {
            return NO_ATTRIBUTES;
         } else {
            AjAttributeStruct struct = new AjAttributeStruct(type, context, msgHandler);
            Attribute[] attributes = javaClass.getAttributes();
            boolean hasAtAspectAnnotation = false;
            boolean hasAtPrecedenceAnnotation = false;
            AjAttribute.WeaverVersionInfo wvinfo = null;

            int i;
            Attribute attribute;
            for(i = 0; i < attributes.length; ++i) {
               attribute = attributes[i];
               if (acceptAttribute(attribute)) {
                  RuntimeAnnos rvs = (RuntimeAnnos)attribute;
                  if (!isCodeStyleAspect && !javaClass.isInterface()) {
                     hasAtAspectAnnotation = handleAspectAnnotation(rvs, struct);
                     hasAtPrecedenceAnnotation = handlePrecedenceAnnotation(rvs, struct);
                  }
                  break;
               }
            }

            for(i = attributes.length - 1; i >= 0; --i) {
               attribute = attributes[i];
               if (attribute.getName().equals("com.bea.core.repackaged.aspectj.weaver.WeaverVersion")) {
                  try {
                     VersionedDataInputStream s = new VersionedDataInputStream(new ByteArrayInputStream(((Unknown)attribute).getBytes()), (ConstantPoolReader)null);
                     wvinfo = AjAttribute.WeaverVersionInfo.read(s);
                     struct.ajAttributes.add(0, wvinfo);
                  } catch (IOException var23) {
                     var23.printStackTrace();
                  }
               }
            }

            if (wvinfo == null) {
               ReferenceTypeDelegate delegate = type.getDelegate();
               if (delegate instanceof BcelObjectType) {
                  wvinfo = ((BcelObjectType)delegate).getWeaverVersionAttribute();
                  if (wvinfo != null) {
                     if (wvinfo.getMajorVersion() != 0) {
                        struct.ajAttributes.add(0, wvinfo);
                     } else {
                        wvinfo = null;
                     }
                  }
               }

               if (wvinfo == null) {
                  struct.ajAttributes.add(0, new AjAttribute.WeaverVersionInfo());
               }
            }

            if (hasAtPrecedenceAnnotation && !hasAtAspectAnnotation) {
               msgHandler.handleMessage(new Message("Found @DeclarePrecedence on a non @Aspect type '" + type.getName() + "'", IMessage.WARNING, (Throwable)null, type.getSourceLocation()));
               return NO_ATTRIBUTES;
            } else if (!hasAtAspectAnnotation && !isCodeStyleAspect && !containsPointcut) {
               return NO_ATTRIBUTES;
            } else {
               Attribute[] fattributes;
               int j;
               Attribute fattribute;
               for(i = 0; i < javaClass.getMethods().length; ++i) {
                  Method method = javaClass.getMethods()[i];
                  if (!method.getName().startsWith("ajc$")) {
                     AjAttributeMethodStruct mstruct = null;
                     boolean processedPointcut = false;
                     fattributes = method.getAttributes();

                     for(j = 0; j < fattributes.length; ++j) {
                        fattribute = fattributes[j];
                        if (acceptAttribute(fattribute)) {
                           mstruct = new AjAttributeMethodStruct(method, (BcelMethod)null, type, context, msgHandler);
                           processedPointcut = handlePointcutAnnotation((RuntimeAnnos)fattribute, mstruct);
                           if (!processedPointcut) {
                              processedPointcut = handleDeclareMixinAnnotation((RuntimeAnnos)fattribute, mstruct);
                           }
                           break;
                        }
                     }

                     if (processedPointcut) {
                        struct.ajAttributes.addAll(mstruct.ajAttributes);
                     }
                  }
               }

               Field[] fs = javaClass.getFields();

               for(int i = 0; i < fs.length; ++i) {
                  Field field = fs[i];
                  if (!field.getName().startsWith("ajc$")) {
                     AjAttributeFieldStruct fstruct = new AjAttributeFieldStruct(field, (BcelField)null, type, context, msgHandler);
                     fattributes = field.getAttributes();

                     for(j = 0; j < fattributes.length; ++j) {
                        fattribute = fattributes[j];
                        if (acceptAttribute(fattribute)) {
                           RuntimeAnnos frvs = (RuntimeAnnos)fattribute;
                           if ((handleDeclareErrorOrWarningAnnotation(model, frvs, fstruct) || handleDeclareParentsAnnotation(frvs, fstruct)) && !type.isAnnotationStyleAspect() && !isCodeStyleAspect) {
                              msgHandler.handleMessage(new Message("Found @AspectJ annotations in a non @Aspect type '" + type.getName() + "'", IMessage.WARNING, (Throwable)null, type.getSourceLocation()));
                           }
                           break;
                        }
                     }

                     struct.ajAttributes.addAll(fstruct.ajAttributes);
                  }
               }

               return struct.ajAttributes;
            }
         }
      }
   }

   public static List readAj5MethodAttributes(Method method, BcelMethod bMethod, ResolvedType type, ResolvedPointcutDefinition preResolvedPointcut, ISourceContext context, IMessageHandler msgHandler) {
      if (method.getName().startsWith("ajc$")) {
         return Collections.emptyList();
      } else {
         AjAttributeMethodStruct struct = new AjAttributeMethodStruct(method, bMethod, type, context, msgHandler);
         Attribute[] attributes = method.getAttributes();
         boolean hasAtAspectJAnnotation = false;
         boolean hasAtAspectJAnnotationMustReturnVoid = false;

         for(int i = 0; i < attributes.length; ++i) {
            Attribute attribute = attributes[i];

            try {
               if (acceptAttribute(attribute)) {
                  RuntimeAnnos rvs = (RuntimeAnnos)attribute;
                  hasAtAspectJAnnotationMustReturnVoid = hasAtAspectJAnnotationMustReturnVoid || handleBeforeAnnotation(rvs, struct, preResolvedPointcut);
                  hasAtAspectJAnnotationMustReturnVoid = hasAtAspectJAnnotationMustReturnVoid || handleAfterAnnotation(rvs, struct, preResolvedPointcut);
                  hasAtAspectJAnnotationMustReturnVoid = hasAtAspectJAnnotationMustReturnVoid || handleAfterReturningAnnotation(rvs, struct, preResolvedPointcut, bMethod);
                  hasAtAspectJAnnotationMustReturnVoid = hasAtAspectJAnnotationMustReturnVoid || handleAfterThrowingAnnotation(rvs, struct, preResolvedPointcut, bMethod);
                  hasAtAspectJAnnotation = hasAtAspectJAnnotation || handleAroundAnnotation(rvs, struct, preResolvedPointcut);
                  break;
               }
            } catch (ReturningFormalNotDeclaredInAdviceSignatureException var13) {
               msgHandler.handleMessage(new Message(WeaverMessages.format("returningFormalNotDeclaredInAdvice", var13.getFormalName()), IMessage.ERROR, (Throwable)null, bMethod.getSourceLocation()));
            } catch (ThrownFormalNotDeclaredInAdviceSignatureException var14) {
               msgHandler.handleMessage(new Message(WeaverMessages.format("thrownFormalNotDeclaredInAdvice", var14.getFormalName()), IMessage.ERROR, (Throwable)null, bMethod.getSourceLocation()));
            }
         }

         hasAtAspectJAnnotation = hasAtAspectJAnnotation || hasAtAspectJAnnotationMustReturnVoid;
         if (hasAtAspectJAnnotation && !type.isAspect()) {
            msgHandler.handleMessage(new Message("Found @AspectJ annotations in a non @Aspect type '" + type.getName() + "'", IMessage.WARNING, (Throwable)null, type.getSourceLocation()));
         }

         if (hasAtAspectJAnnotation && !struct.method.isPublic()) {
            msgHandler.handleMessage(new Message("Found @AspectJ annotation on a non public advice '" + methodToString(struct.method) + "'", IMessage.ERROR, (Throwable)null, type.getSourceLocation()));
         }

         if (hasAtAspectJAnnotation && struct.method.isStatic()) {
            msgHandler.handleMessage(MessageUtil.error("Advice cannot be declared static '" + methodToString(struct.method) + "'", type.getSourceLocation()));
         }

         if (hasAtAspectJAnnotationMustReturnVoid && !Type.VOID.equals(struct.method.getReturnType())) {
            msgHandler.handleMessage(new Message("Found @AspectJ annotation on a non around advice not returning void '" + methodToString(struct.method) + "'", IMessage.ERROR, (Throwable)null, type.getSourceLocation()));
         }

         return struct.ajAttributes;
      }
   }

   public static List readAj5FieldAttributes(Field field, BcelField bField, ResolvedType type, ISourceContext context, IMessageHandler msgHandler) {
      return Collections.emptyList();
   }

   private static boolean handleAspectAnnotation(RuntimeAnnos runtimeAnnotations, AjAttributeStruct struct) {
      AnnotationGen aspect = getAnnotation(runtimeAnnotations, AjcMemberMaker.ASPECT_ANNOTATION);
      if (aspect != null) {
         boolean extendsAspect = false;
         if (!"java.lang.Object".equals(struct.enclosingType.getSuperclass().getName())) {
            if (!struct.enclosingType.getSuperclass().isAbstract() && struct.enclosingType.getSuperclass().isAspect()) {
               reportError("cannot extend a concrete aspect", struct);
               return false;
            }

            extendsAspect = struct.enclosingType.getSuperclass().isAspect();
         }

         NameValuePair aspectPerClause = getAnnotationElement(aspect, "value");
         Object perClause;
         if (aspectPerClause == null) {
            if (!extendsAspect) {
               perClause = new PerSingleton();
            } else {
               perClause = new PerFromSuper(struct.enclosingType.getSuperclass().getPerClause().getKind());
            }
         } else {
            String perX = aspectPerClause.getValue().stringifyValue();
            if (perX != null && perX.length() > 0) {
               perClause = parsePerClausePointcut(perX, struct);
            } else {
               perClause = new PerSingleton();
            }
         }

         if (perClause == null) {
            return false;
         } else {
            ((PerClause)perClause).setLocation(struct.context, -1, -1);
            AjAttribute.Aspect aspectAttribute = new AjAttribute.Aspect((PerClause)perClause);
            struct.ajAttributes.add(aspectAttribute);
            FormalBinding[] bindings = new FormalBinding[0];
            IScope binding = new BindingScope(struct.enclosingType, struct.context, bindings);
            aspectAttribute.setResolutionScope(binding);
            return true;
         }
      } else {
         return false;
      }
   }

   private static PerClause parsePerClausePointcut(String perClauseString, AjAttributeStruct struct) {
      Pointcut pointcut = null;
      TypePattern typePattern = null;
      String pointcutString;
      Object perClause;
      if (perClauseString.startsWith(PerClause.KindAnnotationPrefix.PERCFLOW.getName())) {
         pointcutString = PerClause.KindAnnotationPrefix.PERCFLOW.extractPointcut(perClauseString);
         pointcut = parsePointcut(pointcutString, struct, false);
         perClause = new PerCflow(pointcut, false);
      } else if (perClauseString.startsWith(PerClause.KindAnnotationPrefix.PERCFLOWBELOW.getName())) {
         pointcutString = PerClause.KindAnnotationPrefix.PERCFLOWBELOW.extractPointcut(perClauseString);
         pointcut = parsePointcut(pointcutString, struct, false);
         perClause = new PerCflow(pointcut, true);
      } else if (perClauseString.startsWith(PerClause.KindAnnotationPrefix.PERTARGET.getName())) {
         pointcutString = PerClause.KindAnnotationPrefix.PERTARGET.extractPointcut(perClauseString);
         pointcut = parsePointcut(pointcutString, struct, false);
         perClause = new PerObject(pointcut, false);
      } else if (perClauseString.startsWith(PerClause.KindAnnotationPrefix.PERTHIS.getName())) {
         pointcutString = PerClause.KindAnnotationPrefix.PERTHIS.extractPointcut(perClauseString);
         pointcut = parsePointcut(pointcutString, struct, false);
         perClause = new PerObject(pointcut, true);
      } else if (perClauseString.startsWith(PerClause.KindAnnotationPrefix.PERTYPEWITHIN.getName())) {
         pointcutString = PerClause.KindAnnotationPrefix.PERTYPEWITHIN.extractPointcut(perClauseString);
         typePattern = parseTypePattern(pointcutString, struct);
         perClause = new PerTypeWithin(typePattern);
      } else {
         if (!perClauseString.equalsIgnoreCase(PerClause.SINGLETON.getName() + "()")) {
            reportError("@Aspect per clause cannot be read '" + perClauseString + "'", struct);
            return null;
         }

         perClause = new PerSingleton();
      }

      if (!PerClause.SINGLETON.equals(((PerClause)perClause).getKind()) && !PerClause.PERTYPEWITHIN.equals(((PerClause)perClause).getKind()) && pointcut == null) {
         return null;
      } else {
         return (PerClause)(PerClause.PERTYPEWITHIN.equals(((PerClause)perClause).getKind()) && typePattern == null ? null : perClause);
      }
   }

   private static boolean handlePrecedenceAnnotation(RuntimeAnnos runtimeAnnotations, AjAttributeStruct struct) {
      AnnotationGen aspect = getAnnotation(runtimeAnnotations, AjcMemberMaker.DECLAREPRECEDENCE_ANNOTATION);
      if (aspect != null) {
         NameValuePair precedence = getAnnotationElement(aspect, "value");
         if (precedence != null) {
            String precedencePattern = precedence.getValue().stringifyValue();
            PatternParser parser = new PatternParser(precedencePattern);
            DeclarePrecedence ajPrecedence = parser.parseDominates();
            struct.ajAttributes.add(new AjAttribute.DeclareAttribute(ajPrecedence));
            return true;
         }
      }

      return false;
   }

   private static boolean handleDeclareParentsAnnotation(RuntimeAnnos runtimeAnnotations, AjAttributeFieldStruct struct) {
      AnnotationGen decp = getAnnotation(runtimeAnnotations, AjcMemberMaker.DECLAREPARENTS_ANNOTATION);
      if (decp != null) {
         NameValuePair decpPatternNVP = getAnnotationElement(decp, "value");
         String decpPattern = decpPatternNVP.getValue().stringifyValue();
         if (decpPattern != null) {
            TypePattern typePattern = parseTypePattern(decpPattern, struct);
            ResolvedType fieldType = UnresolvedType.forSignature(struct.field.getSignature()).resolve(struct.enclosingType.getWorld());
            if (((ResolvedType)fieldType).isParameterizedOrRawType()) {
               fieldType = ((ResolvedType)fieldType).getGenericType();
            }

            if (!((ResolvedType)fieldType).isInterface()) {
               reportError("@DeclareParents: can only be used on a field whose type is an interface", struct);
               return false;
            }

            TypePattern parent = parseTypePattern(((ResolvedType)fieldType).getName(), struct);
            FormalBinding[] bindings = new FormalBinding[0];
            IScope binding = new BindingScope(struct.enclosingType, struct.context, bindings);
            List parents = new ArrayList(1);
            parents.add(parent);
            DeclareParents dp = new DeclareParents(typePattern, parents, false);
            dp.resolve(binding);
            typePattern = dp.getChild();
            dp.setLocation(struct.context, -1, -1);
            struct.ajAttributes.add(new AjAttribute.DeclareAttribute(dp));
            String defaultImplClassName = null;
            NameValuePair defaultImplNVP = getAnnotationElement(decp, "defaultImpl");
            if (defaultImplNVP != null) {
               ClassElementValue defaultImpl = (ClassElementValue)defaultImplNVP.getValue();
               defaultImplClassName = UnresolvedType.forSignature(defaultImpl.getClassString()).getName();
               if (defaultImplClassName.equals("com.bea.core.repackaged.aspectj.lang.annotation.DeclareParents")) {
                  defaultImplClassName = null;
               } else {
                  ResolvedType impl = struct.enclosingType.getWorld().resolve(defaultImplClassName, false);
                  ResolvedMember[] mm = impl.getDeclaredMethods();
                  int implModifiers = impl.getModifiers();
                  boolean defaultVisibilityImpl = !Modifier.isPrivate(implModifiers) && !Modifier.isProtected(implModifiers) && !Modifier.isPublic(implModifiers);
                  boolean hasNoCtorOrANoArgOne = true;
                  ResolvedMember foundOneOfIncorrectVisibility = null;

                  for(int i = 0; i < mm.length; ++i) {
                     ResolvedMember resolvedMember = mm[i];
                     if (resolvedMember.getName().equals("<init>")) {
                        hasNoCtorOrANoArgOne = false;
                        if (resolvedMember.getParameterTypes().length == 0) {
                           if (defaultVisibilityImpl) {
                              if (!resolvedMember.isPublic() && !resolvedMember.isDefault()) {
                                 foundOneOfIncorrectVisibility = resolvedMember;
                              } else {
                                 hasNoCtorOrANoArgOne = true;
                              }
                           } else if (Modifier.isPublic(implModifiers)) {
                              if (resolvedMember.isPublic()) {
                                 hasNoCtorOrANoArgOne = true;
                              } else {
                                 foundOneOfIncorrectVisibility = resolvedMember;
                              }
                           }
                        }
                     }

                     if (hasNoCtorOrANoArgOne) {
                        break;
                     }
                  }

                  if (!hasNoCtorOrANoArgOne) {
                     if (foundOneOfIncorrectVisibility != null) {
                        reportError("@DeclareParents: defaultImpl=\"" + defaultImplClassName + "\" has a no argument constructor, but it is of incorrect visibility.  It must be at least as visible as the type.", struct);
                     } else {
                        reportError("@DeclareParents: defaultImpl=\"" + defaultImplClassName + "\" has no public no-arg constructor", struct);
                     }
                  }

                  if (!((ResolvedType)fieldType).isAssignableFrom(impl)) {
                     reportError("@DeclareParents: defaultImpl=\"" + defaultImplClassName + "\" does not implement the interface '" + ((ResolvedType)fieldType).toString() + "'", struct);
                  }
               }
            }

            boolean hasAtLeastOneMethod = false;
            Iterator methodIterator = ((ResolvedType)fieldType).getMethodsIncludingIntertypeDeclarations(false, true);

            ResolvedMember fieldHost;
            while(methodIterator.hasNext()) {
               fieldHost = (ResolvedMember)methodIterator.next();
               if (fieldHost.isAbstract()) {
                  hasAtLeastOneMethod = true;
                  MethodDelegateTypeMunger mdtm = new MethodDelegateTypeMunger(fieldHost, struct.enclosingType, defaultImplClassName, typePattern);
                  mdtm.setFieldType((UnresolvedType)fieldType);
                  mdtm.setSourceLocation(struct.enclosingType.getSourceLocation());
                  struct.ajAttributes.add(new AjAttribute.TypeMunger(mdtm));
               }
            }

            if (hasAtLeastOneMethod && defaultImplClassName != null) {
               fieldHost = AjcMemberMaker.itdAtDeclareParentsField((ResolvedType)null, (UnresolvedType)fieldType, struct.enclosingType);
               struct.ajAttributes.add(new AjAttribute.TypeMunger(new MethodDelegateTypeMunger.FieldHostTypeMunger(fieldHost, struct.enclosingType, typePattern)));
            }

            return true;
         }
      }

      return false;
   }

   public static String getMethodForMessage(AjAttributeMethodStruct methodstructure) {
      StringBuffer sb = new StringBuffer();
      sb.append("Method '");
      sb.append(methodstructure.method.getReturnType().toString());
      sb.append(" ").append(methodstructure.enclosingType).append(".").append(methodstructure.method.getName());
      sb.append("(");
      Type[] args = methodstructure.method.getArgumentTypes();
      if (args != null) {
         for(int t = 0; t < args.length; ++t) {
            if (t > 0) {
               sb.append(",");
            }

            sb.append(args[t].toString());
         }
      }

      sb.append(")'");
      return sb.toString();
   }

   private static boolean handleDeclareMixinAnnotation(RuntimeAnnos runtimeAnnotations, AjAttributeMethodStruct struct) {
      AnnotationGen declareMixinAnnotation = getAnnotation(runtimeAnnotations, AjcMemberMaker.DECLAREMIXIN_ANNOTATION);
      if (declareMixinAnnotation == null) {
         return false;
      } else {
         Method annotatedMethod = struct.method;
         World world = struct.enclosingType.getWorld();
         NameValuePair declareMixinPatternNameValuePair = getAnnotationElement(declareMixinAnnotation, "value");
         String declareMixinPattern = declareMixinPatternNameValuePair.getValue().stringifyValue();
         TypePattern targetTypePattern = parseTypePattern(declareMixinPattern, struct);
         ResolvedType methodReturnType = UnresolvedType.forSignature(annotatedMethod.getReturnType().getSignature()).resolve(world);
         if (((ResolvedType)methodReturnType).isParameterizedOrRawType()) {
            methodReturnType = ((ResolvedType)methodReturnType).getGenericType();
         }

         if (((ResolvedType)methodReturnType).isPrimitiveType()) {
            reportError(getMethodForMessage(struct) + ":  factory methods for a mixin cannot return void or a primitive type", struct);
            return false;
         } else if (annotatedMethod.getArgumentTypes().length > 1) {
            reportError(getMethodForMessage(struct) + ": factory methods for a mixin can take a maximum of one parameter", struct);
            return false;
         } else {
            NameValuePair interfaceListSpecified = getAnnotationElement(declareMixinAnnotation, "interfaces");
            List newParents = new ArrayList(1);
            List newInterfaceTypes = new ArrayList(1);
            ResolvedType typeForDelegation;
            if (interfaceListSpecified != null) {
               ArrayElementValue arrayOfInterfaceTypes = (ArrayElementValue)interfaceListSpecified.getValue();
               int numberOfTypes = arrayOfInterfaceTypes.getElementValuesArraySize();
               ElementValue[] theTypes = arrayOfInterfaceTypes.getElementValuesArray();

               for(int i = 0; i < numberOfTypes; ++i) {
                  ClassElementValue interfaceType = (ClassElementValue)theTypes[i];
                  typeForDelegation = UnresolvedType.forSignature(interfaceType.getClassString().replace("/", ".")).resolve(world);
                  if (typeForDelegation.isMissing() || !typeForDelegation.isInterface()) {
                     reportError("Types listed in the 'interfaces' DeclareMixin annotation value must be valid interfaces. This is invalid: " + typeForDelegation.getName(), struct);
                     return false;
                  }

                  if (!typeForDelegation.isAssignableFrom((ResolvedType)methodReturnType)) {
                     reportError(getMethodForMessage(struct) + ": factory method does not return something that implements '" + typeForDelegation.getName() + "'", struct);
                     return false;
                  }

                  newInterfaceTypes.add(typeForDelegation);
                  TypePattern newParent = parseTypePattern(typeForDelegation.getName(), struct);
                  newParents.add(newParent);
               }
            } else {
               if (((ResolvedType)methodReturnType).isClass()) {
                  reportError(getMethodForMessage(struct) + ": factory methods for a mixin must either return an interface type or specify interfaces in the annotation and return a class", struct);
                  return false;
               }

               TypePattern newParent = parseTypePattern(((ResolvedType)methodReturnType).getName(), struct);
               newInterfaceTypes.add(methodReturnType);
               newParents.add(newParent);
            }

            if (newParents.size() == 0) {
               return false;
            } else {
               FormalBinding[] bindings = new FormalBinding[0];
               IScope binding = new BindingScope(struct.enclosingType, struct.context, bindings);
               DeclareParents dp = new DeclareParentsMixin(targetTypePattern, newParents);
               dp.resolve(binding);
               targetTypePattern = dp.getChild();
               dp.setLocation(struct.context, -1, -1);
               struct.ajAttributes.add(new AjAttribute.DeclareAttribute(dp));
               boolean hasAtLeastOneMethod = false;
               Iterator iterator = newInterfaceTypes.iterator();

               while(iterator.hasNext()) {
                  typeForDelegation = (ResolvedType)iterator.next();
                  ResolvedMember[] methods = (ResolvedMember[])typeForDelegation.getMethodsWithoutIterator(true, false, false).toArray(new ResolvedMember[0]);

                  for(int i = 0; i < methods.length; ++i) {
                     ResolvedMember method = methods[i];
                     if (method.isAbstract()) {
                        hasAtLeastOneMethod = true;
                        if (method.hasBackingGenericMember()) {
                           method = method.getBackingGenericMember();
                        }

                        MethodDelegateTypeMunger mdtm = new MethodDelegateTypeMunger(method, struct.enclosingType, "", targetTypePattern, struct.method.getName(), struct.method.getSignature());
                        mdtm.setFieldType((UnresolvedType)methodReturnType);
                        mdtm.setSourceLocation(struct.enclosingType.getSourceLocation());
                        struct.ajAttributes.add(new AjAttribute.TypeMunger(mdtm));
                     }
                  }
               }

               if (hasAtLeastOneMethod) {
                  ResolvedMember fieldHost = AjcMemberMaker.itdAtDeclareParentsField((ResolvedType)null, (UnresolvedType)methodReturnType, struct.enclosingType);
                  struct.ajAttributes.add(new AjAttribute.TypeMunger(new MethodDelegateTypeMunger.FieldHostTypeMunger(fieldHost, struct.enclosingType, targetTypePattern)));
               }

               return true;
            }
         }
      }
   }

   private static boolean handleBeforeAnnotation(RuntimeAnnos runtimeAnnotations, AjAttributeMethodStruct struct, ResolvedPointcutDefinition preResolvedPointcut) {
      AnnotationGen before = getAnnotation(runtimeAnnotations, AjcMemberMaker.BEFORE_ANNOTATION);
      if (before != null) {
         NameValuePair beforeAdvice = getAnnotationElement(before, "value");
         if (beforeAdvice != null) {
            String argumentNames = getArgNamesValue(before);
            if (argumentNames != null) {
               struct.unparsedArgumentNames = argumentNames;
            }

            FormalBinding[] bindings = new FormalBinding[0];

            try {
               bindings = extractBindings(struct);
            } catch (UnreadableDebugInfoException var11) {
               return false;
            }

            IScope binding = new BindingScope(struct.enclosingType, struct.context, bindings);
            int extraArgument = extractExtraArgument(struct.method);
            Pointcut pc = null;
            if (preResolvedPointcut != null) {
               pc = preResolvedPointcut.getPointcut();
            } else {
               pc = parsePointcut(beforeAdvice.getValue().stringifyValue(), struct, false);
               if (pc == null) {
                  return false;
               }

               pc = pc.resolve(binding);
            }

            setIgnoreUnboundBindingNames(pc, bindings);
            ISourceLocation sl = struct.context.makeSourceLocation(struct.bMethod.getDeclarationLineNumber(), struct.bMethod.getDeclarationOffset());
            struct.ajAttributes.add(new AjAttribute.AdviceAttribute(AdviceKind.Before, pc, extraArgument, sl.getOffset(), sl.getOffset() + 1, struct.context));
            return true;
         }
      }

      return false;
   }

   private static boolean handleAfterAnnotation(RuntimeAnnos runtimeAnnotations, AjAttributeMethodStruct struct, ResolvedPointcutDefinition preResolvedPointcut) {
      AnnotationGen after = getAnnotation(runtimeAnnotations, AjcMemberMaker.AFTER_ANNOTATION);
      if (after != null) {
         NameValuePair afterAdvice = getAnnotationElement(after, "value");
         if (afterAdvice != null) {
            FormalBinding[] bindings = new FormalBinding[0];
            String argumentNames = getArgNamesValue(after);
            if (argumentNames != null) {
               struct.unparsedArgumentNames = argumentNames;
            }

            try {
               bindings = extractBindings(struct);
            } catch (UnreadableDebugInfoException var11) {
               return false;
            }

            IScope binding = new BindingScope(struct.enclosingType, struct.context, bindings);
            int extraArgument = extractExtraArgument(struct.method);
            Pointcut pc = null;
            if (preResolvedPointcut != null) {
               pc = preResolvedPointcut.getPointcut();
            } else {
               pc = parsePointcut(afterAdvice.getValue().stringifyValue(), struct, false);
               if (pc == null) {
                  return false;
               }

               pc.resolve(binding);
            }

            setIgnoreUnboundBindingNames(pc, bindings);
            ISourceLocation sl = struct.context.makeSourceLocation(struct.bMethod.getDeclarationLineNumber(), struct.bMethod.getDeclarationOffset());
            struct.ajAttributes.add(new AjAttribute.AdviceAttribute(AdviceKind.After, pc, extraArgument, sl.getOffset(), sl.getOffset() + 1, struct.context));
            return true;
         }
      }

      return false;
   }

   private static boolean handleAfterReturningAnnotation(RuntimeAnnos runtimeAnnotations, AjAttributeMethodStruct struct, ResolvedPointcutDefinition preResolvedPointcut, BcelMethod owningMethod) throws ReturningFormalNotDeclaredInAdviceSignatureException {
      AnnotationGen after = getAnnotation(runtimeAnnotations, AjcMemberMaker.AFTERRETURNING_ANNOTATION);
      if (after == null) {
         return false;
      } else {
         NameValuePair annValue = getAnnotationElement(after, "value");
         NameValuePair annPointcut = getAnnotationElement(after, "pointcut");
         NameValuePair annReturned = getAnnotationElement(after, "returning");
         String pointcut = null;
         String returned = null;
         if (annValue != null && annPointcut != null || annValue == null && annPointcut == null) {
            reportError("@AfterReturning: either 'value' or 'poincut' must be provided, not both", struct);
            return false;
         } else {
            if (annValue != null) {
               pointcut = annValue.getValue().stringifyValue();
            } else {
               pointcut = annPointcut.getValue().stringifyValue();
            }

            if (isNullOrEmpty(pointcut)) {
               reportError("@AfterReturning: either 'value' or 'poincut' must be provided, not both", struct);
               return false;
            } else {
               if (annReturned != null) {
                  returned = annReturned.getValue().stringifyValue();
                  if (isNullOrEmpty(returned)) {
                     returned = null;
                  } else {
                     String[] pNames = owningMethod.getParameterNames();
                     if (pNames == null || pNames.length == 0 || !Arrays.asList(pNames).contains(returned)) {
                        throw new ReturningFormalNotDeclaredInAdviceSignatureException(returned);
                     }
                  }
               }

               String argumentNames = getArgNamesValue(after);
               if (argumentNames != null) {
                  struct.unparsedArgumentNames = argumentNames;
               }

               FormalBinding[] bindings = new FormalBinding[0];

               try {
                  bindings = returned == null ? extractBindings(struct) : extractBindings(struct, returned);
               } catch (UnreadableDebugInfoException var16) {
                  return false;
               }

               IScope binding = new BindingScope(struct.enclosingType, struct.context, bindings);
               int extraArgument = extractExtraArgument(struct.method);
               if (returned != null) {
                  extraArgument |= 1;
               }

               Pointcut pc = null;
               if (preResolvedPointcut != null) {
                  pc = preResolvedPointcut.getPointcut();
               } else {
                  pc = parsePointcut(pointcut, struct, false);
                  if (pc == null) {
                     return false;
                  }

                  pc.resolve(binding);
               }

               setIgnoreUnboundBindingNames(pc, bindings);
               ISourceLocation sl = struct.context.makeSourceLocation(struct.bMethod.getDeclarationLineNumber(), struct.bMethod.getDeclarationOffset());
               struct.ajAttributes.add(new AjAttribute.AdviceAttribute(AdviceKind.AfterReturning, pc, extraArgument, sl.getOffset(), sl.getOffset() + 1, struct.context));
               return true;
            }
         }
      }
   }

   private static boolean handleAfterThrowingAnnotation(RuntimeAnnos runtimeAnnotations, AjAttributeMethodStruct struct, ResolvedPointcutDefinition preResolvedPointcut, BcelMethod owningMethod) throws ThrownFormalNotDeclaredInAdviceSignatureException {
      AnnotationGen after = getAnnotation(runtimeAnnotations, AjcMemberMaker.AFTERTHROWING_ANNOTATION);
      if (after == null) {
         return false;
      } else {
         NameValuePair annValue = getAnnotationElement(after, "value");
         NameValuePair annPointcut = getAnnotationElement(after, "pointcut");
         NameValuePair annThrown = getAnnotationElement(after, "throwing");
         String pointcut = null;
         String thrownFormal = null;
         if (annValue != null && annPointcut != null || annValue == null && annPointcut == null) {
            reportError("@AfterThrowing: either 'value' or 'poincut' must be provided, not both", struct);
            return false;
         } else {
            if (annValue != null) {
               pointcut = annValue.getValue().stringifyValue();
            } else {
               pointcut = annPointcut.getValue().stringifyValue();
            }

            if (isNullOrEmpty(pointcut)) {
               reportError("@AfterThrowing: either 'value' or 'poincut' must be provided, not both", struct);
               return false;
            } else {
               if (annThrown != null) {
                  thrownFormal = annThrown.getValue().stringifyValue();
                  if (isNullOrEmpty(thrownFormal)) {
                     thrownFormal = null;
                  } else {
                     String[] pNames = owningMethod.getParameterNames();
                     if (pNames == null || pNames.length == 0 || !Arrays.asList(pNames).contains(thrownFormal)) {
                        throw new ThrownFormalNotDeclaredInAdviceSignatureException(thrownFormal);
                     }
                  }
               }

               String argumentNames = getArgNamesValue(after);
               if (argumentNames != null) {
                  struct.unparsedArgumentNames = argumentNames;
               }

               FormalBinding[] bindings = new FormalBinding[0];

               try {
                  bindings = thrownFormal == null ? extractBindings(struct) : extractBindings(struct, thrownFormal);
               } catch (UnreadableDebugInfoException var16) {
                  return false;
               }

               IScope binding = new BindingScope(struct.enclosingType, struct.context, bindings);
               int extraArgument = extractExtraArgument(struct.method);
               if (thrownFormal != null) {
                  extraArgument |= 1;
               }

               Pointcut pc = null;
               if (preResolvedPointcut != null) {
                  pc = preResolvedPointcut.getPointcut();
               } else {
                  pc = parsePointcut(pointcut, struct, false);
                  if (pc == null) {
                     return false;
                  }

                  pc.resolve(binding);
               }

               setIgnoreUnboundBindingNames(pc, bindings);
               ISourceLocation sl = struct.context.makeSourceLocation(struct.bMethod.getDeclarationLineNumber(), struct.bMethod.getDeclarationOffset());
               struct.ajAttributes.add(new AjAttribute.AdviceAttribute(AdviceKind.AfterThrowing, pc, extraArgument, sl.getOffset(), sl.getOffset() + 1, struct.context));
               return true;
            }
         }
      }
   }

   private static boolean handleAroundAnnotation(RuntimeAnnos runtimeAnnotations, AjAttributeMethodStruct struct, ResolvedPointcutDefinition preResolvedPointcut) {
      AnnotationGen around = getAnnotation(runtimeAnnotations, AjcMemberMaker.AROUND_ANNOTATION);
      if (around != null) {
         NameValuePair aroundAdvice = getAnnotationElement(around, "value");
         if (aroundAdvice != null) {
            String argumentNames = getArgNamesValue(around);
            if (argumentNames != null) {
               struct.unparsedArgumentNames = argumentNames;
            }

            FormalBinding[] bindings = new FormalBinding[0];

            try {
               bindings = extractBindings(struct);
            } catch (UnreadableDebugInfoException var11) {
               return false;
            }

            IScope binding = new BindingScope(struct.enclosingType, struct.context, bindings);
            int extraArgument = extractExtraArgument(struct.method);
            Pointcut pc = null;
            if (preResolvedPointcut != null) {
               pc = preResolvedPointcut.getPointcut();
            } else {
               pc = parsePointcut(aroundAdvice.getValue().stringifyValue(), struct, false);
               if (pc == null) {
                  return false;
               }

               pc.resolve(binding);
            }

            setIgnoreUnboundBindingNames(pc, bindings);
            ISourceLocation sl = struct.context.makeSourceLocation(struct.bMethod.getDeclarationLineNumber(), struct.bMethod.getDeclarationOffset());
            struct.ajAttributes.add(new AjAttribute.AdviceAttribute(AdviceKind.Around, pc, extraArgument, sl.getOffset(), sl.getOffset() + 1, struct.context));
            return true;
         }
      }

      return false;
   }

   private static boolean handlePointcutAnnotation(RuntimeAnnos runtimeAnnotations, AjAttributeMethodStruct struct) {
      AnnotationGen pointcut = getAnnotation(runtimeAnnotations, AjcMemberMaker.POINTCUT_ANNOTATION);
      if (pointcut == null) {
         return false;
      } else {
         NameValuePair pointcutExpr = getAnnotationElement(pointcut, "value");
         if (!Type.VOID.equals(struct.method.getReturnType()) && (!Type.BOOLEAN.equals(struct.method.getReturnType()) || !struct.method.isStatic() || !struct.method.isPublic())) {
            reportWarning("Found @Pointcut on a method not returning 'void' or not 'public static boolean'", struct);
         }

         if (struct.method.getExceptionTable() != null) {
            reportWarning("Found @Pointcut on a method throwing exception", struct);
         }

         String argumentNames = getArgNamesValue(pointcut);
         if (argumentNames != null) {
            struct.unparsedArgumentNames = argumentNames;
         }

         BindingScope binding;
         try {
            if (struct.method.isAbstract()) {
               binding = null;
            } else {
               binding = new BindingScope(struct.enclosingType, struct.context, extractBindings(struct));
            }
         } catch (UnreadableDebugInfoException var8) {
            return false;
         }

         UnresolvedType[] argumentTypes = new UnresolvedType[struct.method.getArgumentTypes().length];

         for(int i = 0; i < argumentTypes.length; ++i) {
            argumentTypes[i] = UnresolvedType.forSignature(struct.method.getArgumentTypes()[i].getSignature());
         }

         Pointcut pc = null;
         if (struct.method.isAbstract()) {
            if ((pointcutExpr == null || !isNullOrEmpty(pointcutExpr.getValue().stringifyValue())) && pointcutExpr != null) {
               reportError("Found defined @Pointcut on an abstract method", struct);
               return false;
            }
         } else if (pointcutExpr != null && !isNullOrEmpty(pointcutExpr.getValue().stringifyValue())) {
            pc = parsePointcut(pointcutExpr.getValue().stringifyValue(), struct, true);
            if (pc == null) {
               return false;
            }

            pc.setLocation(struct.context, -1, -1);
         }

         struct.ajAttributes.add(new AjAttribute.PointcutDeclarationAttribute(new LazyResolvedPointcutDefinition(struct.enclosingType, struct.method.getModifiers(), struct.method.getName(), argumentTypes, UnresolvedType.forSignature(struct.method.getReturnType().getSignature()), pc, binding)));
         return true;
      }
   }

   private static boolean handleDeclareErrorOrWarningAnnotation(AsmManager model, RuntimeAnnos runtimeAnnotations, AjAttributeFieldStruct struct) {
      AnnotationGen error = getAnnotation(runtimeAnnotations, AjcMemberMaker.DECLAREERROR_ANNOTATION);
      boolean hasError = false;
      if (error != null) {
         NameValuePair declareError = getAnnotationElement(error, "value");
         if (declareError != null) {
            if (!"Ljava/lang/String;".equals(struct.field.getSignature()) || struct.field.getConstantValue() == null) {
               reportError("@DeclareError used on a non String constant field", struct);
               return false;
            }

            Pointcut pc = parsePointcut(declareError.getValue().stringifyValue(), struct, false);
            if (pc == null) {
               hasError = false;
            } else {
               DeclareErrorOrWarning deow = new DeclareErrorOrWarning(true, pc, struct.field.getConstantValue().toString());
               setDeclareErrorOrWarningLocation(model, deow, struct);
               struct.ajAttributes.add(new AjAttribute.DeclareAttribute(deow));
               hasError = true;
            }
         }
      }

      AnnotationGen warning = getAnnotation(runtimeAnnotations, AjcMemberMaker.DECLAREWARNING_ANNOTATION);
      boolean hasWarning = false;
      if (warning != null) {
         NameValuePair declareWarning = getAnnotationElement(warning, "value");
         if (declareWarning != null) {
            if (!"Ljava/lang/String;".equals(struct.field.getSignature()) || struct.field.getConstantValue() == null) {
               reportError("@DeclareWarning used on a non String constant field", struct);
               return false;
            }

            Pointcut pc = parsePointcut(declareWarning.getValue().stringifyValue(), struct, false);
            if (pc != null) {
               DeclareErrorOrWarning deow = new DeclareErrorOrWarning(false, pc, struct.field.getConstantValue().toString());
               setDeclareErrorOrWarningLocation(model, deow, struct);
               struct.ajAttributes.add(new AjAttribute.DeclareAttribute(deow));
               hasWarning = true;
               return true;
            }

            hasWarning = false;
         }
      }

      return hasError || hasWarning;
   }

   private static void setDeclareErrorOrWarningLocation(AsmManager model, DeclareErrorOrWarning deow, AjAttributeFieldStruct struct) {
      IHierarchy top = model == null ? null : model.getHierarchy();
      if (top != null && top.getRoot() != null) {
         IProgramElement ipe = top.findElementForLabel(top.getRoot(), IProgramElement.Kind.FIELD, struct.field.getName());
         if (ipe != null && ipe.getSourceLocation() != null) {
            ISourceLocation sourceLocation = ipe.getSourceLocation();
            int start = sourceLocation.getOffset();
            int end = start + struct.field.getName().length();
            deow.setLocation(struct.context, start, end);
            return;
         }
      }

      deow.setLocation(struct.context, -1, -1);
   }

   private static String methodToString(Method method) {
      StringBuffer sb = new StringBuffer();
      sb.append(method.getName());
      sb.append(method.getSignature());
      return sb.toString();
   }

   private static FormalBinding[] extractBindings(AjAttributeMethodStruct struct) throws UnreadableDebugInfoException {
      Method method = struct.method;
      String[] argumentNames = struct.getArgumentNames();
      if (argumentNames.length != method.getArgumentTypes().length) {
         reportError("Cannot read debug info for @Aspect to handle formal binding in pointcuts (please compile with 'javac -g' or '<javac debug='true'.../>' in Ant)", struct);
         throw new UnreadableDebugInfoException();
      } else {
         List bindings = new ArrayList();

         for(int i = 0; i < argumentNames.length; ++i) {
            String argumentName = argumentNames[i];
            UnresolvedType argumentType = UnresolvedType.forSignature(method.getArgumentTypes()[i].getSignature());
            if (!AjcMemberMaker.TYPEX_JOINPOINT.equals(argumentType) && !AjcMemberMaker.TYPEX_PROCEEDINGJOINPOINT.equals(argumentType) && !AjcMemberMaker.TYPEX_STATICJOINPOINT.equals(argumentType) && !AjcMemberMaker.TYPEX_ENCLOSINGSTATICJOINPOINT.equals(argumentType) && !AjcMemberMaker.AROUND_CLOSURE_TYPE.equals(argumentType)) {
               bindings.add(new FormalBinding(argumentType, argumentName, i));
            } else {
               bindings.add(new FormalBinding.ImplicitFormalBinding(argumentType, argumentName, i));
            }
         }

         return (FormalBinding[])bindings.toArray(new FormalBinding[0]);
      }
   }

   private static FormalBinding[] extractBindings(AjAttributeMethodStruct struct, String excludeFormal) throws UnreadableDebugInfoException {
      FormalBinding[] bindings = extractBindings(struct);

      for(int i = 0; i < bindings.length; ++i) {
         FormalBinding binding = bindings[i];
         if (binding.getName().equals(excludeFormal)) {
            bindings[i] = new FormalBinding.ImplicitFormalBinding(binding.getType(), binding.getName(), binding.getIndex());
            break;
         }
      }

      return bindings;
   }

   private static int extractExtraArgument(Method method) {
      Type[] methodArgs = method.getArgumentTypes();
      String[] sigs = new String[methodArgs.length];

      for(int i = 0; i < methodArgs.length; ++i) {
         sigs[i] = methodArgs[i].getSignature();
      }

      return extractExtraArgument(sigs);
   }

   public static int extractExtraArgument(String[] argumentSignatures) {
      int extraArgument = 0;

      for(int i = 0; i < argumentSignatures.length; ++i) {
         if (AjcMemberMaker.TYPEX_JOINPOINT.getSignature().equals(argumentSignatures[i])) {
            extraArgument |= 2;
         } else if (AjcMemberMaker.TYPEX_PROCEEDINGJOINPOINT.getSignature().equals(argumentSignatures[i])) {
            extraArgument |= 2;
         } else if (AjcMemberMaker.TYPEX_STATICJOINPOINT.getSignature().equals(argumentSignatures[i])) {
            extraArgument |= 4;
         } else if (AjcMemberMaker.TYPEX_ENCLOSINGSTATICJOINPOINT.getSignature().equals(argumentSignatures[i])) {
            extraArgument |= 8;
         }
      }

      return extraArgument;
   }

   private static AnnotationGen getAnnotation(RuntimeAnnos rvs, UnresolvedType annotationType) {
      String annotationTypeName = annotationType.getName();
      Iterator i$ = rvs.getAnnotations().iterator();

      AnnotationGen rv;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         rv = (AnnotationGen)i$.next();
      } while(!annotationTypeName.equals(rv.getTypeName()));

      return rv;
   }

   private static NameValuePair getAnnotationElement(AnnotationGen annotation, String elementName) {
      Iterator i$ = annotation.getValues().iterator();

      NameValuePair element;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         element = (NameValuePair)i$.next();
      } while(!elementName.equals(element.getNameString()));

      return element;
   }

   private static String getArgNamesValue(AnnotationGen anno) {
      List elements = anno.getValues();
      Iterator i$ = elements.iterator();

      NameValuePair element;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         element = (NameValuePair)i$.next();
      } while(!"argNames".equals(element.getNameString()));

      return element.getValue().stringifyValue();
   }

   private static String lastbit(String fqname) {
      int i = fqname.lastIndexOf(".");
      return i == -1 ? fqname : fqname.substring(i + 1);
   }

   private static String[] getMethodArgumentNames(Method method, String argNamesFromAnnotation, AjAttributeMethodStruct methodStruct) {
      if (method.getArgumentTypes().length == 0) {
         return EMPTY_STRINGS;
      } else {
         int startAtStackIndex = method.isStatic() ? 0 : 1;
         List arguments = new ArrayList();
         LocalVariableTable lt = method.getLocalVariableTable();
         int i;
         if (lt != null) {
            LocalVariable[] lvt = lt.getLocalVariableTable();

            for(i = 0; i < lvt.length; ++i) {
               LocalVariable localVariable = lvt[i];
               if (localVariable != null) {
                  if (localVariable.getStartPC() == 0 && localVariable.getIndex() >= startAtStackIndex) {
                     arguments.add(new MethodArgument(localVariable.getName(), localVariable.getIndex()));
                  }
               } else {
                  String typename = methodStruct.enclosingType != null ? methodStruct.enclosingType.getName() : "";
                  System.err.println("AspectJ: 348488 debug: unusual local variable table for method " + typename + "." + method.getName());
               }
            }

            if (arguments.size() == 0) {
               LocalVariable localVariable = lvt[0];
               if (localVariable != null && localVariable.getStartPC() != 0) {
                  for(int j = 0; j < lvt.length && arguments.size() < method.getArgumentTypes().length; ++j) {
                     localVariable = lvt[j];
                     if (localVariable.getIndex() >= startAtStackIndex) {
                        arguments.add(new MethodArgument(localVariable.getName(), localVariable.getIndex()));
                     }
                  }
               }
            }
         } else if (argNamesFromAnnotation != null) {
            StringTokenizer st = new StringTokenizer(argNamesFromAnnotation, " ,");
            List args = new ArrayList();

            while(st.hasMoreTokens()) {
               args.add(st.nextToken());
            }

            if (args.size() == method.getArgumentTypes().length) {
               return (String[])args.toArray(new String[0]);
            }

            StringBuffer shortString = (new StringBuffer()).append(lastbit(method.getReturnType().toString())).append(" ").append(method.getName());
            if (method.getArgumentTypes().length > 0) {
               shortString.append("(");

               for(int i = 0; i < method.getArgumentTypes().length; ++i) {
                  shortString.append(lastbit(method.getArgumentTypes()[i].toString()));
                  if (i + 1 < method.getArgumentTypes().length) {
                     shortString.append(",");
                  }
               }

               shortString.append(")");
            }

            reportError("argNames annotation value does not specify the right number of argument names for the method '" + shortString.toString() + "'", methodStruct);
            return EMPTY_STRINGS;
         }

         if (arguments.size() != method.getArgumentTypes().length) {
            return EMPTY_STRINGS;
         } else {
            Collections.sort(arguments, new Comparator() {
               public int compare(MethodArgument mo, MethodArgument mo1) {
                  if (mo.indexOnStack == mo1.indexOnStack) {
                     return 0;
                  } else {
                     return mo.indexOnStack > mo1.indexOnStack ? 1 : -1;
                  }
               }
            });
            String[] argumentNames = new String[arguments.size()];
            i = 0;

            MethodArgument methodArgument;
            for(Iterator i$ = arguments.iterator(); i$.hasNext(); argumentNames[i++] = methodArgument.name) {
               methodArgument = (MethodArgument)i$.next();
            }

            return argumentNames;
         }
      }
   }

   private static boolean isNullOrEmpty(String s) {
      return s == null || s.length() <= 0;
   }

   private static void setIgnoreUnboundBindingNames(Pointcut pointcut, FormalBinding[] bindings) {
      List ignores = new ArrayList();

      for(int i = 0; i < bindings.length; ++i) {
         FormalBinding formalBinding = bindings[i];
         if (formalBinding instanceof FormalBinding.ImplicitFormalBinding) {
            ignores.add(formalBinding.getName());
         }
      }

      pointcut.m_ignoreUnboundBindingForNames = (String[])ignores.toArray(new String[ignores.size()]);
   }

   private static void reportError(String message, AjAttributeStruct location) {
      if (!location.handler.isIgnoring(IMessage.ERROR)) {
         location.handler.handleMessage(new Message(message, location.enclosingType.getSourceLocation(), true));
      }

   }

   private static void reportWarning(String message, AjAttributeStruct location) {
      if (!location.handler.isIgnoring(IMessage.WARNING)) {
         location.handler.handleMessage(new Message(message, location.enclosingType.getSourceLocation(), false));
      }

   }

   private static Pointcut parsePointcut(String pointcutString, AjAttributeStruct struct, boolean allowIf) {
      try {
         PatternParser parser = new PatternParser(pointcutString, struct.context);
         Pointcut pointcut = parser.parsePointcut();
         parser.checkEof();
         pointcut.check((ISourceContext)null, struct.enclosingType.getWorld());
         if (!allowIf && pointcutString.indexOf("if()") >= 0 && hasIf(pointcut)) {
            reportError("if() pointcut is not allowed at this pointcut location '" + pointcutString + "'", struct);
            return null;
         } else {
            pointcut.setLocation(struct.context, -1, -1);
            return pointcut;
         }
      } catch (ParserException var5) {
         reportError("Invalid pointcut '" + pointcutString + "': " + var5.toString() + (var5.getLocation() == null ? "" : " at position " + var5.getLocation().getStart()), struct);
         return null;
      }
   }

   private static boolean hasIf(Pointcut pointcut) {
      IfFinder visitor = new IfFinder();
      pointcut.accept(visitor, (Object)null);
      return visitor.hasIf;
   }

   private static TypePattern parseTypePattern(String patternString, AjAttributeStruct location) {
      try {
         TypePattern typePattern = (new PatternParser(patternString)).parseTypePattern();
         typePattern.setLocation(location.context, -1, -1);
         return typePattern;
      } catch (ParserException var3) {
         reportError("Invalid type pattern'" + patternString + "' : " + var3.getLocation(), location);
         return null;
      }
   }

   static class ReturningFormalNotDeclaredInAdviceSignatureException extends Exception {
      private final String formalName;

      public ReturningFormalNotDeclaredInAdviceSignatureException(String formalName) {
         this.formalName = formalName;
      }

      public String getFormalName() {
         return this.formalName;
      }
   }

   static class ThrownFormalNotDeclaredInAdviceSignatureException extends Exception {
      private final String formalName;

      public ThrownFormalNotDeclaredInAdviceSignatureException(String formalName) {
         this.formalName = formalName;
      }

      public String getFormalName() {
         return this.formalName;
      }
   }

   private static class UnreadableDebugInfoException extends Exception {
      private UnreadableDebugInfoException() {
      }

      // $FF: synthetic method
      UnreadableDebugInfoException(Object x0) {
         this();
      }
   }

   public static class LazyResolvedPointcutDefinition extends ResolvedPointcutDefinition {
      private final Pointcut m_pointcutUnresolved;
      private final IScope m_binding;
      private Pointcut m_lazyPointcut = null;

      public LazyResolvedPointcutDefinition(UnresolvedType declaringType, int modifiers, String name, UnresolvedType[] parameterTypes, UnresolvedType returnType, Pointcut pointcut, IScope binding) {
         super(declaringType, modifiers, name, parameterTypes, returnType, Pointcut.makeMatchesNothing(Pointcut.RESOLVED));
         this.m_pointcutUnresolved = pointcut;
         this.m_binding = binding;
      }

      public Pointcut getPointcut() {
         if (this.m_lazyPointcut == null && this.m_pointcutUnresolved == null) {
            this.m_lazyPointcut = Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
         }

         if (this.m_lazyPointcut == null && this.m_pointcutUnresolved != null) {
            this.m_lazyPointcut = this.m_pointcutUnresolved.resolve(this.m_binding);
            this.m_lazyPointcut.copyLocationFrom(this.m_pointcutUnresolved);
         }

         return this.m_lazyPointcut;
      }
   }

   private static class MethodArgument {
      String name;
      int indexOnStack;

      public MethodArgument(String name, int indexOnStack) {
         this.name = name;
         this.indexOnStack = indexOnStack;
      }
   }

   private static class AjAttributeFieldStruct extends AjAttributeStruct {
      final Field field;

      public AjAttributeFieldStruct(Field field, BcelField bField, ResolvedType type, ISourceContext sourceContext, IMessageHandler messageHandler) {
         super(type, sourceContext, messageHandler);
         this.field = field;
      }
   }

   private static class AjAttributeMethodStruct extends AjAttributeStruct {
      private String[] m_argumentNamesLazy = null;
      public String unparsedArgumentNames = null;
      final Method method;
      final BcelMethod bMethod;

      public AjAttributeMethodStruct(Method method, BcelMethod bMethod, ResolvedType type, ISourceContext sourceContext, IMessageHandler messageHandler) {
         super(type, sourceContext, messageHandler);
         this.method = method;
         this.bMethod = bMethod;
      }

      public String[] getArgumentNames() {
         if (this.m_argumentNamesLazy == null) {
            this.m_argumentNamesLazy = AtAjAttributes.getMethodArgumentNames(this.method, this.unparsedArgumentNames, this);
         }

         return this.m_argumentNamesLazy;
      }
   }

   private static class AjAttributeStruct {
      List ajAttributes = new ArrayList();
      final ResolvedType enclosingType;
      final ISourceContext context;
      final IMessageHandler handler;

      public AjAttributeStruct(ResolvedType type, ISourceContext sourceContext, IMessageHandler messageHandler) {
         this.enclosingType = type;
         this.context = sourceContext;
         this.handler = messageHandler;
      }
   }
}
