package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BaseTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ElementValuePair;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SplitPackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.VariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.WildcardBinding;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class Factory {
   public static final Byte DUMMY_BYTE = 0;
   public static final Character DUMMY_CHAR = '0';
   public static final Double DUMMY_DOUBLE = 0.0;
   public static final Float DUMMY_FLOAT = 0.0F;
   public static final Integer DUMMY_INTEGER = 0;
   public static final Long DUMMY_LONG = 0L;
   public static final Short DUMMY_SHORT = Short.valueOf((short)0);
   private final BaseProcessingEnvImpl _env;
   public static List EMPTY_ANNOTATION_MIRRORS = Collections.emptyList();
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$javax$lang$model$type$TypeKind;

   public Factory(BaseProcessingEnvImpl env) {
      this._env = env;
   }

   public List getAnnotationMirrors(AnnotationBinding[] annotations) {
      if (annotations != null && annotations.length != 0) {
         List list = new ArrayList(annotations.length);
         AnnotationBinding[] var6 = annotations;
         int var5 = annotations.length;

         for(int var4 = 0; var4 < var5; ++var4) {
            AnnotationBinding annotation = var6[var4];
            if (annotation != null) {
               list.add(this.newAnnotationMirror(annotation));
            }
         }

         return Collections.unmodifiableList(list);
      } else {
         return Collections.emptyList();
      }
   }

   public Annotation[] getAnnotationsByType(AnnotationBinding[] annoInstances, Class annotationClass) {
      Annotation[] result = this.getAnnotations(annoInstances, annotationClass, false);
      return result == null ? (Annotation[])Array.newInstance(annotationClass, 0) : result;
   }

   public Annotation getAnnotation(AnnotationBinding[] annoInstances, Class annotationClass) {
      Annotation[] result = this.getAnnotations(annoInstances, annotationClass, true);
      return result == null ? null : result[0];
   }

   private Annotation[] getAnnotations(AnnotationBinding[] annoInstances, Class annotationClass, boolean justTheFirst) {
      if (annoInstances != null && annoInstances.length != 0 && annotationClass != null) {
         String annoTypeName = annotationClass.getName();
         if (annoTypeName == null) {
            return null;
         } else {
            List list = new ArrayList(annoInstances.length);
            AnnotationBinding[] var9 = annoInstances;
            int var8 = annoInstances.length;

            for(int var7 = 0; var7 < var8; ++var7) {
               AnnotationBinding annoInstance = var9[var7];
               if (annoInstance != null) {
                  AnnotationMirrorImpl annoMirror = this.createAnnotationMirror(annoTypeName, annoInstance);
                  if (annoMirror != null) {
                     list.add((Annotation)Proxy.newProxyInstance(annotationClass.getClassLoader(), new Class[]{annotationClass}, annoMirror));
                     if (justTheFirst) {
                        break;
                     }
                  }
               }
            }

            Annotation[] result = (Annotation[])Array.newInstance(annotationClass, list.size());
            return list.size() > 0 ? (Annotation[])list.toArray(result) : null;
         }
      } else {
         return null;
      }
   }

   private AnnotationMirrorImpl createAnnotationMirror(String annoTypeName, AnnotationBinding annoInstance) {
      ReferenceBinding binding = annoInstance.getAnnotationType();
      if (binding != null && binding.isAnnotationType()) {
         char[] qName;
         if (binding.isMemberType()) {
            annoTypeName = annoTypeName.replace('$', '.');
            qName = CharOperation.concatWith(binding.enclosingType().compoundName, binding.sourceName, '.');
            CharOperation.replace(qName, '$', '.');
         } else {
            qName = CharOperation.concatWith(binding.compoundName, '.');
         }

         if (annoTypeName.equals(new String(qName))) {
            return (AnnotationMirrorImpl)this._env.getFactory().newAnnotationMirror(annoInstance);
         }
      }

      return null;
   }

   private static void appendModifier(Set result, int modifiers, int modifierConstant, Modifier modifier) {
      if ((modifiers & modifierConstant) != 0) {
         result.add(modifier);
      }

   }

   private static void decodeModifiers(Set result, int modifiers, int[] checkBits) {
      if (checkBits != null) {
         int i = 0;

         for(int max = checkBits.length; i < max; ++i) {
            switch (checkBits[i]) {
               case 1:
                  appendModifier(result, modifiers, checkBits[i], Modifier.PUBLIC);
                  break;
               case 2:
                  appendModifier(result, modifiers, checkBits[i], Modifier.PRIVATE);
                  break;
               case 4:
                  appendModifier(result, modifiers, checkBits[i], Modifier.PROTECTED);
                  break;
               case 8:
                  appendModifier(result, modifiers, checkBits[i], Modifier.STATIC);
                  break;
               case 16:
                  appendModifier(result, modifiers, checkBits[i], Modifier.FINAL);
                  break;
               case 32:
                  appendModifier(result, modifiers, checkBits[i], Modifier.SYNCHRONIZED);
                  break;
               case 64:
                  appendModifier(result, modifiers, checkBits[i], Modifier.VOLATILE);
                  break;
               case 128:
                  appendModifier(result, modifiers, checkBits[i], Modifier.TRANSIENT);
                  break;
               case 256:
                  appendModifier(result, modifiers, checkBits[i], Modifier.NATIVE);
                  break;
               case 1024:
                  appendModifier(result, modifiers, checkBits[i], Modifier.ABSTRACT);
                  break;
               case 2048:
                  appendModifier(result, modifiers, checkBits[i], Modifier.STRICTFP);
                  break;
               case 65536:
                  try {
                     appendModifier(result, modifiers, checkBits[i], Modifier.valueOf("DEFAULT"));
                  } catch (IllegalArgumentException var5) {
                  }
            }
         }

      }
   }

   public static Object getMatchingDummyValue(Class expectedType) {
      if (expectedType.isPrimitive()) {
         if (expectedType == Boolean.TYPE) {
            return Boolean.FALSE;
         } else if (expectedType == Byte.TYPE) {
            return DUMMY_BYTE;
         } else if (expectedType == Character.TYPE) {
            return DUMMY_CHAR;
         } else if (expectedType == Double.TYPE) {
            return DUMMY_DOUBLE;
         } else if (expectedType == Float.TYPE) {
            return DUMMY_FLOAT;
         } else if (expectedType == Integer.TYPE) {
            return DUMMY_INTEGER;
         } else if (expectedType == Long.TYPE) {
            return DUMMY_LONG;
         } else {
            return expectedType == Short.TYPE ? DUMMY_SHORT : DUMMY_INTEGER;
         }
      } else {
         return null;
      }
   }

   public TypeMirror getReceiverType(MethodBinding binding) {
      if (binding != null) {
         if (binding.receiver != null) {
            return this._env.getFactory().newTypeMirror(binding.receiver);
         }

         if (binding.declaringClass != null && !binding.isStatic() && (!binding.isConstructor() || binding.declaringClass.isMemberType())) {
            return this._env.getFactory().newTypeMirror(binding.declaringClass);
         }
      }

      return NoTypeImpl.NO_TYPE_NONE;
   }

   public static Set getModifiers(int modifiers, ElementKind kind) {
      return getModifiers(modifiers, kind, false);
   }

   public static Set getModifiers(int modifiers, ElementKind kind, boolean isFromBinary) {
      EnumSet result = EnumSet.noneOf(Modifier.class);
      switch (kind) {
         case ENUM:
            if (isFromBinary) {
               decodeModifiers(result, modifiers, new int[]{1, 4, 16, 2, 1024, 8, 2048});
            } else {
               decodeModifiers(result, modifiers, new int[]{1, 4, 16, 2, 8, 2048});
            }
            break;
         case CLASS:
         case ANNOTATION_TYPE:
         case INTERFACE:
            decodeModifiers(result, modifiers, new int[]{1, 4, 1024, 16, 2, 8, 2048});
            break;
         case ENUM_CONSTANT:
         case FIELD:
            decodeModifiers(result, modifiers, new int[]{1, 4, 2, 8, 16, 128, 64});
         case PARAMETER:
         case LOCAL_VARIABLE:
         case EXCEPTION_PARAMETER:
         case STATIC_INIT:
         case INSTANCE_INIT:
         case TYPE_PARAMETER:
         case OTHER:
         case RESOURCE_VARIABLE:
         default:
            break;
         case METHOD:
         case CONSTRUCTOR:
            decodeModifiers(result, modifiers, new int[]{1, 4, 2, 1024, 8, 16, 32, 256, 2048, 65536});
            break;
         case MODULE:
            decodeModifiers(result, modifiers, new int[]{32, 32});
      }

      return Collections.unmodifiableSet(result);
   }

   public AnnotationMirror newAnnotationMirror(AnnotationBinding binding) {
      return new AnnotationMirrorImpl(this._env, binding);
   }

   public Element newElement(Binding binding, ElementKind kindHint) {
      if (binding == null) {
         return null;
      } else {
         switch (binding.kind()) {
            case 1:
            case 2:
            case 3:
               return new VariableElementImpl(this._env, (VariableBinding)binding);
            case 4:
            case 2052:
               ReferenceBinding referenceBinding = (ReferenceBinding)binding;
               if ((referenceBinding.tagBits & 128L) != 0L) {
                  return new ErrorTypeElement(this._env, referenceBinding);
               } else {
                  if (CharOperation.equals(referenceBinding.sourceName, TypeConstants.PACKAGE_INFO_NAME)) {
                     return this.newPackageElement(referenceBinding.fPackage);
                  }

                  return new TypeElementImpl(this._env, referenceBinding, kindHint);
               }
            case 8:
               return new ExecutableElementImpl(this._env, (MethodBinding)binding);
            case 16:
               return this.newPackageElement((PackageBinding)binding);
            case 32:
            case 68:
            case 132:
            case 516:
            case 8196:
               throw new UnsupportedOperationException("NYI: binding type " + binding.kind());
            case 64:
               return new ModuleElementImpl(this._env, (ModuleBinding)binding);
            case 260:
            case 1028:
               return new TypeElementImpl(this._env, ((ParameterizedTypeBinding)binding).genericType(), kindHint);
            case 4100:
               return new TypeParameterElementImpl(this._env, (TypeVariableBinding)binding);
            default:
               return null;
         }
      }
   }

   public Element newElement(Binding binding) {
      return this.newElement(binding, (ElementKind)null);
   }

   public PackageElement newPackageElement(PackageBinding binding) {
      if (binding instanceof SplitPackageBinding && binding.enclosingModule != null) {
         binding = ((SplitPackageBinding)binding).getIncarnation(binding.enclosingModule);
      }

      return binding == null ? null : new PackageElementImpl(this._env, binding);
   }

   public NullType getNullType() {
      return NoTypeImpl.NULL_TYPE;
   }

   public NoType getNoType(TypeKind kind) {
      switch (kind) {
         case VOID:
            return NoTypeImpl.NO_TYPE_VOID;
         case NONE:
            return NoTypeImpl.NO_TYPE_NONE;
         case PACKAGE:
            return NoTypeImpl.NO_TYPE_PACKAGE;
         case MODULE:
            return new NoTypeImpl(kind);
         default:
            throw new IllegalArgumentException();
      }
   }

   public PrimitiveTypeImpl getPrimitiveType(TypeKind kind) {
      switch (kind) {
         case BOOLEAN:
            return PrimitiveTypeImpl.BOOLEAN;
         case BYTE:
            return PrimitiveTypeImpl.BYTE;
         case SHORT:
            return PrimitiveTypeImpl.SHORT;
         case INT:
            return PrimitiveTypeImpl.INT;
         case LONG:
            return PrimitiveTypeImpl.LONG;
         case CHAR:
            return PrimitiveTypeImpl.CHAR;
         case FLOAT:
            return PrimitiveTypeImpl.FLOAT;
         case DOUBLE:
            return PrimitiveTypeImpl.DOUBLE;
         default:
            throw new IllegalArgumentException();
      }
   }

   public PrimitiveTypeImpl getPrimitiveType(BaseTypeBinding binding) {
      AnnotationBinding[] annotations = binding.getTypeAnnotations();
      return annotations != null && annotations.length != 0 ? new PrimitiveTypeImpl(this._env, binding) : this.getPrimitiveType(PrimitiveTypeImpl.getKind(binding));
   }

   public TypeMirror newTypeMirror(Binding binding) {
      switch (binding.kind()) {
         case 1:
         case 2:
         case 3:
            return this.newTypeMirror(((VariableBinding)binding).type);
         case 4:
         case 260:
         case 1028:
         case 2052:
            ReferenceBinding referenceBinding = (ReferenceBinding)binding;
            if ((referenceBinding.tagBits & 128L) != 0L) {
               return this.getErrorType(referenceBinding);
            }

            return new DeclaredTypeImpl(this._env, (ReferenceBinding)binding);
         case 8:
            return new ExecutableTypeImpl(this._env, (MethodBinding)binding);
         case 16:
            return this.getNoType(TypeKind.PACKAGE);
         case 32:
            throw new UnsupportedOperationException("NYI: import type " + binding.kind());
         case 64:
            return this.getNoType(TypeKind.MODULE);
         case 68:
            return new ArrayTypeImpl(this._env, (ArrayBinding)binding);
         case 132:
            BaseTypeBinding btb = (BaseTypeBinding)binding;
            switch (btb.id) {
               case 6:
                  return this.getNoType(TypeKind.VOID);
               case 12:
                  return this.getNullType();
               default:
                  return this.getPrimitiveType(btb);
            }
         case 516:
         case 8196:
            return new WildcardTypeImpl(this._env, (WildcardBinding)binding);
         case 4100:
            return new TypeVariableImpl(this._env, (TypeVariableBinding)binding);
         default:
            return null;
      }
   }

   public TypeParameterElement newTypeParameterElement(TypeVariableBinding variable, Element declaringElement) {
      return new TypeParameterElementImpl(this._env, variable, declaringElement);
   }

   public ErrorType getErrorType(ReferenceBinding binding) {
      return new ErrorTypeImpl(this._env, binding);
   }

   public static Object performNecessaryPrimitiveTypeConversion(Class expectedType, Object value, boolean avoidReflectException) {
      assert expectedType.isPrimitive() : "expectedType is not a primitive type: " + expectedType.getName();

      if (value == null) {
         return avoidReflectException ? getMatchingDummyValue(expectedType) : null;
      } else {
         String typeName = expectedType.getName();
         char expectedTypeChar = typeName.charAt(0);
         int nameLen = typeName.length();
         if (value instanceof Byte) {
            byte b = (Byte)value;
            switch (expectedTypeChar) {
               case 'b':
                  if (nameLen == 4) {
                     return value;
                  }

                  return avoidReflectException ? Boolean.FALSE : value;
               case 'c':
                  return (char)b;
               case 'd':
                  return (double)b;
               case 'f':
                  return (float)b;
               case 'i':
                  return Integer.valueOf(b);
               case 'l':
                  return (long)b;
               case 's':
                  return Short.valueOf(b);
               default:
                  throw new IllegalStateException("unknown type " + expectedTypeChar);
            }
         } else {
            short c;
            if (value instanceof Short) {
               c = (Short)value;
               switch (expectedTypeChar) {
                  case 'b':
                     if (nameLen == 4) {
                        return (byte)c;
                     }

                     return avoidReflectException ? Boolean.FALSE : value;
                  case 'c':
                     return (char)c;
                  case 'd':
                     return (double)c;
                  case 'f':
                     return (float)c;
                  case 'i':
                     return Integer.valueOf(c);
                  case 'l':
                     return (long)c;
                  case 's':
                     return value;
                  default:
                     throw new IllegalStateException("unknown type " + expectedTypeChar);
               }
            } else if (value instanceof Character) {
               c = (short)(Character)value;
               switch (expectedTypeChar) {
                  case 'b':
                     if (nameLen == 4) {
                        return (byte)c;
                     }

                     return avoidReflectException ? Boolean.FALSE : value;
                  case 'c':
                     return value;
                  case 'd':
                     return (double)c;
                  case 'f':
                     return (float)c;
                  case 'i':
                     return Integer.valueOf(c);
                  case 'l':
                     return (long)c;
                  case 's':
                     return (short)c;
                  default:
                     throw new IllegalStateException("unknown type " + expectedTypeChar);
               }
            } else if (value instanceof Integer) {
               int i = (Integer)value;
               switch (expectedTypeChar) {
                  case 'b':
                     if (nameLen == 4) {
                        return (byte)i;
                     }

                     return avoidReflectException ? Boolean.FALSE : value;
                  case 'c':
                     return (char)i;
                  case 'd':
                     return (double)i;
                  case 'f':
                     return (float)i;
                  case 'i':
                     return value;
                  case 'l':
                     return (long)i;
                  case 's':
                     return (short)i;
                  default:
                     throw new IllegalStateException("unknown type " + expectedTypeChar);
               }
            } else if (value instanceof Long) {
               long l = (Long)value;
               switch (expectedTypeChar) {
                  case 'b':
                  case 'c':
                  case 'i':
                  case 's':
                     return avoidReflectException ? getMatchingDummyValue(expectedType) : value;
                  case 'd':
                     return (double)l;
                  case 'f':
                     return (float)l;
                  case 'l':
                     return value;
                  default:
                     throw new IllegalStateException("unknown type " + expectedTypeChar);
               }
            } else if (value instanceof Float) {
               float f = (Float)value;
               switch (expectedTypeChar) {
                  case 'b':
                  case 'c':
                  case 'i':
                  case 'l':
                  case 's':
                     return avoidReflectException ? getMatchingDummyValue(expectedType) : value;
                  case 'd':
                     return (double)f;
                  case 'f':
                     return value;
                  default:
                     throw new IllegalStateException("unknown type " + expectedTypeChar);
               }
            } else if (value instanceof Double) {
               if (expectedTypeChar == 'd') {
                  return value;
               } else {
                  return avoidReflectException ? getMatchingDummyValue(expectedType) : value;
               }
            } else if (value instanceof Boolean) {
               if (expectedTypeChar == 'b' && nameLen == 7) {
                  return value;
               } else {
                  return avoidReflectException ? getMatchingDummyValue(expectedType) : value;
               }
            } else {
               return avoidReflectException ? getMatchingDummyValue(expectedType) : value;
            }
         }
      }
   }

   public static void setArrayMatchingDummyValue(Object array, int i, Class expectedLeafType) {
      if (Boolean.TYPE.equals(expectedLeafType)) {
         Array.setBoolean(array, i, false);
      } else if (Byte.TYPE.equals(expectedLeafType)) {
         Array.setByte(array, i, DUMMY_BYTE);
      } else if (Character.TYPE.equals(expectedLeafType)) {
         Array.setChar(array, i, DUMMY_CHAR);
      } else if (Double.TYPE.equals(expectedLeafType)) {
         Array.setDouble(array, i, DUMMY_DOUBLE);
      } else if (Float.TYPE.equals(expectedLeafType)) {
         Array.setFloat(array, i, DUMMY_FLOAT);
      } else if (Integer.TYPE.equals(expectedLeafType)) {
         Array.setInt(array, i, DUMMY_INTEGER);
      } else if (Long.TYPE.equals(expectedLeafType)) {
         Array.setLong(array, i, DUMMY_LONG);
      } else if (Short.TYPE.equals(expectedLeafType)) {
         Array.setShort(array, i, DUMMY_SHORT);
      } else {
         Array.set(array, i, (Object)null);
      }

   }

   public static AnnotationBinding[] getPackedAnnotationBindings(AnnotationBinding[] annotations) {
      int length = annotations == null ? 0 : annotations.length;
      if (length == 0) {
         return annotations;
      } else {
         AnnotationBinding[] repackagedBindings = annotations;

         int i;
         for(i = 0; i < length; ++i) {
            AnnotationBinding annotation = repackagedBindings[i];
            if (annotation != null) {
               ReferenceBinding annotationType = annotation.getAnnotationType();
               if (annotationType.isRepeatableAnnotationType()) {
                  ReferenceBinding containerType = annotationType.containerAnnotationType();
                  if (containerType != null) {
                     MethodBinding[] values = containerType.getMethods(TypeConstants.VALUE);
                     if (values != null && values.length == 1) {
                        MethodBinding value = values[0];
                        if (value.returnType != null && value.returnType.dimensions() == 1 && !TypeBinding.notEquals(value.returnType.leafComponentType(), annotationType)) {
                           List containees = null;

                           for(int j = i + 1; j < length; ++j) {
                              AnnotationBinding otherAnnotation = repackagedBindings[j];
                              if (otherAnnotation != null && otherAnnotation.getAnnotationType() == annotationType) {
                                 if (repackagedBindings == annotations) {
                                    System.arraycopy(repackagedBindings, 0, repackagedBindings = new AnnotationBinding[length], 0, length);
                                 }

                                 repackagedBindings[j] = null;
                                 if (containees == null) {
                                    containees = new ArrayList();
                                    containees.add(annotation);
                                 }

                                 containees.add(otherAnnotation);
                              }
                           }

                           if (containees != null) {
                              ElementValuePair[] elementValuePairs = new ElementValuePair[]{new ElementValuePair(TypeConstants.VALUE, containees.toArray(), value)};
                              repackagedBindings[i] = new AnnotationBinding(containerType, elementValuePairs);
                           }
                        }
                     }
                  }
               }
            }
         }

         i = 0;

         int i;
         for(i = 0; i < length; ++i) {
            if (repackagedBindings[i] != null) {
               ++i;
            }
         }

         if (repackagedBindings == annotations && i == length) {
            return annotations;
         } else {
            annotations = new AnnotationBinding[i];
            i = 0;

            for(int j = 0; i < length; ++i) {
               if (repackagedBindings[i] != null) {
                  annotations[j++] = repackagedBindings[i];
               }
            }

            return annotations;
         }
      }
   }

   public static AnnotationBinding[] getUnpackedAnnotationBindings(AnnotationBinding[] annotations) {
      int length = annotations == null ? 0 : annotations.length;
      if (length == 0) {
         return annotations;
      } else {
         List unpackedAnnotations = new ArrayList();

         label68:
         for(int i = 0; i < length; ++i) {
            AnnotationBinding annotation = annotations[i];
            if (annotation != null) {
               unpackedAnnotations.add(annotation);
               ReferenceBinding annotationType = annotation.getAnnotationType();
               MethodBinding[] values = annotationType.getMethods(TypeConstants.VALUE);
               if (values != null && values.length == 1) {
                  MethodBinding value = values[0];
                  if (value.returnType.dimensions() == 1) {
                     TypeBinding containeeType = value.returnType.leafComponentType();
                     if (containeeType != null && containeeType.isAnnotationType() && containeeType.isRepeatableAnnotationType() && containeeType.containerAnnotationType() == annotationType) {
                        ElementValuePair[] elementValuePairs = annotation.getElementValuePairs();
                        ElementValuePair[] var13 = elementValuePairs;
                        int var12 = elementValuePairs.length;

                        for(int var11 = 0; var11 < var12; ++var11) {
                           ElementValuePair elementValuePair = var13[var11];
                           if (CharOperation.equals(elementValuePair.getName(), TypeConstants.VALUE)) {
                              Object[] containees = (Object[])elementValuePair.getValue();
                              Object[] var18 = containees;
                              int var17 = containees.length;
                              int var16 = 0;

                              while(true) {
                                 if (var16 >= var17) {
                                    continue label68;
                                 }

                                 Object object = var18[var16];
                                 unpackedAnnotations.add((AnnotationBinding)object);
                                 ++var16;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return (AnnotationBinding[])unpackedAnnotations.toArray(new AnnotationBinding[unpackedAnnotations.size()]);
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind() {
      int[] var10000 = $SWITCH_TABLE$javax$lang$model$element$ElementKind;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[ElementKind.values().length];

         try {
            var0[ElementKind.ANNOTATION_TYPE.ordinal()] = 4;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[ElementKind.CLASS.ordinal()] = 3;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[ElementKind.CONSTRUCTOR.ordinal()] = 12;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[ElementKind.ENUM.ordinal()] = 2;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[ElementKind.ENUM_CONSTANT.ordinal()] = 6;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[ElementKind.EXCEPTION_PARAMETER.ordinal()] = 10;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[ElementKind.FIELD.ordinal()] = 7;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[ElementKind.INSTANCE_INIT.ordinal()] = 14;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[ElementKind.INTERFACE.ordinal()] = 5;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[ElementKind.LOCAL_VARIABLE.ordinal()] = 9;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[ElementKind.METHOD.ordinal()] = 11;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[ElementKind.MODULE.ordinal()] = 18;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[ElementKind.OTHER.ordinal()] = 16;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[ElementKind.PACKAGE.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[ElementKind.PARAMETER.ordinal()] = 8;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[ElementKind.RESOURCE_VARIABLE.ordinal()] = 17;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[ElementKind.STATIC_INIT.ordinal()] = 13;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[ElementKind.TYPE_PARAMETER.ordinal()] = 15;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$javax$lang$model$element$ElementKind = var0;
         return var0;
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$javax$lang$model$type$TypeKind() {
      int[] var10000 = $SWITCH_TABLE$javax$lang$model$type$TypeKind;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[TypeKind.values().length];

         try {
            var0[TypeKind.ARRAY.ordinal()] = 12;
         } catch (NoSuchFieldError var22) {
         }

         try {
            var0[TypeKind.BOOLEAN.ordinal()] = 1;
         } catch (NoSuchFieldError var21) {
         }

         try {
            var0[TypeKind.BYTE.ordinal()] = 2;
         } catch (NoSuchFieldError var20) {
         }

         try {
            var0[TypeKind.CHAR.ordinal()] = 6;
         } catch (NoSuchFieldError var19) {
         }

         try {
            var0[TypeKind.DECLARED.ordinal()] = 13;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[TypeKind.DOUBLE.ordinal()] = 8;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[TypeKind.ERROR.ordinal()] = 14;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[TypeKind.EXECUTABLE.ordinal()] = 18;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[TypeKind.FLOAT.ordinal()] = 7;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[TypeKind.INT.ordinal()] = 4;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[TypeKind.INTERSECTION.ordinal()] = 21;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[TypeKind.LONG.ordinal()] = 5;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[TypeKind.MODULE.ordinal()] = 22;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[TypeKind.NONE.ordinal()] = 10;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[TypeKind.NULL.ordinal()] = 11;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[TypeKind.OTHER.ordinal()] = 19;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[TypeKind.PACKAGE.ordinal()] = 17;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[TypeKind.SHORT.ordinal()] = 3;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[TypeKind.TYPEVAR.ordinal()] = 15;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[TypeKind.UNION.ordinal()] = 20;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[TypeKind.VOID.ordinal()] = 9;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[TypeKind.WILDCARD.ordinal()] = 16;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$javax$lang$model$type$TypeKind = var0;
         return var0;
      }
   }
}
