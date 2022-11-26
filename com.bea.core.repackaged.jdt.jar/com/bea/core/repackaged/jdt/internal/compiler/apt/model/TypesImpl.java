package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BaseTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;

public class TypesImpl implements Types {
   private final BaseProcessingEnvImpl _env;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$javax$lang$model$type$TypeKind;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind;

   public TypesImpl(BaseProcessingEnvImpl env) {
      this._env = env;
   }

   public Element asElement(TypeMirror t) {
      switch (t.getKind()) {
         case DECLARED:
         case TYPEVAR:
            return this._env.getFactory().newElement(((TypeMirrorImpl)t).binding());
         case ERROR:
         default:
            return null;
      }
   }

   public TypeMirror asMemberOf(DeclaredType containing, Element element) {
      ElementImpl elementImpl = (ElementImpl)element;
      DeclaredTypeImpl declaredTypeImpl = (DeclaredTypeImpl)containing;
      ReferenceBinding referenceBinding = (ReferenceBinding)declaredTypeImpl._binding;
      TypeMirror typeMirror;
      switch (element.getKind()) {
         case ENUM:
         case CLASS:
         case ANNOTATION_TYPE:
         case INTERFACE:
            typeMirror = this.findMemberInHierarchy(referenceBinding, elementImpl._binding, new MemberInTypeFinder() {
               public TypeMirror find(ReferenceBinding typeBinding, Binding memberBinding) {
                  ReferenceBinding elementBinding = (ReferenceBinding)memberBinding;
                  ReferenceBinding[] var7;
                  int var6 = (var7 = typeBinding.memberTypes()).length;

                  for(int var5 = 0; var5 < var6; ++var5) {
                     ReferenceBinding memberReferenceBinding = var7[var5];
                     if (CharOperation.equals(elementBinding.compoundName, memberReferenceBinding.compoundName)) {
                        return TypesImpl.this._env.getFactory().newTypeMirror(memberReferenceBinding);
                     }
                  }

                  return null;
               }
            });
            if (typeMirror != null) {
               return typeMirror;
            }
            break;
         case ENUM_CONSTANT:
         case FIELD:
            typeMirror = this.findMemberInHierarchy(referenceBinding, elementImpl._binding, new MemberInTypeFinder() {
               public TypeMirror find(ReferenceBinding typeBinding, Binding memberBinding) {
                  FieldBinding fieldBinding = (FieldBinding)memberBinding;
                  FieldBinding[] var7;
                  int var6 = (var7 = typeBinding.fields()).length;

                  for(int var5 = 0; var5 < var6; ++var5) {
                     FieldBinding field = var7[var5];
                     if (CharOperation.equals(field.name, fieldBinding.name)) {
                        return TypesImpl.this._env.getFactory().newTypeMirror(field);
                     }
                  }

                  return null;
               }
            });
            if (typeMirror != null) {
               return typeMirror;
            }
            break;
         case PARAMETER:
         case LOCAL_VARIABLE:
         case EXCEPTION_PARAMETER:
         case STATIC_INIT:
         case INSTANCE_INIT:
         default:
            throw new IllegalArgumentException("element " + element + " has unrecognized element kind " + element.getKind());
         case METHOD:
         case CONSTRUCTOR:
            typeMirror = this.findMemberInHierarchy(referenceBinding, elementImpl._binding, new MemberInTypeFinder() {
               public TypeMirror find(ReferenceBinding typeBinding, Binding memberBinding) {
                  MethodBinding methodBinding = (MethodBinding)memberBinding;
                  MethodBinding[] var7;
                  int var6 = (var7 = typeBinding.methods()).length;

                  for(int var5 = 0; var5 < var6; ++var5) {
                     MethodBinding method = var7[var5];
                     if (CharOperation.equals(method.selector, methodBinding.selector) && (method.original() == methodBinding || method.areParameterErasuresEqual(methodBinding))) {
                        return TypesImpl.this._env.getFactory().newTypeMirror(method);
                     }
                  }

                  return null;
               }
            });
            if (typeMirror != null) {
               return typeMirror;
            }
            break;
         case TYPE_PARAMETER:
            typeMirror = this.findMemberInHierarchy(referenceBinding, elementImpl._binding, new MemberInTypeFinder() {
               public TypeMirror find(ReferenceBinding typeBinding, Binding memberBinding) {
                  if (typeBinding instanceof ParameterizedTypeBinding) {
                     TypeVariableBinding variableBinding = (TypeVariableBinding)memberBinding;
                     ReferenceBinding binding = ((ParameterizedTypeBinding)typeBinding).genericType();
                     if (variableBinding.declaringElement == binding) {
                        TypeVariableBinding[] typeVariables = binding.typeVariables();
                        TypeBinding[] typeArguments = ((ParameterizedTypeBinding)typeBinding).typeArguments();
                        if (typeVariables.length == typeArguments.length) {
                           for(int i = 0; i < typeVariables.length; ++i) {
                              if (typeVariables[i] == memberBinding) {
                                 return TypesImpl.this._env.getFactory().newTypeMirror(typeArguments[i]);
                              }
                           }
                        }
                     }
                  }

                  return null;
               }
            });
            if (typeMirror != null) {
               return typeMirror;
            }
      }

      throw new IllegalArgumentException("element " + element + " is not a member of the containing type " + containing + " nor any of its superclasses");
   }

   private TypeMirror findMemberInHierarchy(ReferenceBinding typeBinding, Binding memberBinding, MemberInTypeFinder finder) {
      TypeMirror result = null;
      if (typeBinding == null) {
         return null;
      } else {
         result = finder.find(typeBinding, memberBinding);
         if (result != null) {
            return result;
         } else {
            result = this.findMemberInHierarchy(typeBinding.superclass(), memberBinding, finder);
            if (result != null) {
               return result;
            } else {
               ReferenceBinding[] var8;
               int var7 = (var8 = typeBinding.superInterfaces()).length;

               for(int var6 = 0; var6 < var7; ++var6) {
                  ReferenceBinding superInterface = var8[var6];
                  result = this.findMemberInHierarchy(superInterface, memberBinding, finder);
                  if (result != null) {
                     return result;
                  }
               }

               return null;
            }
         }
      }
   }

   private void validateRealType(TypeMirror t) {
      switch (t.getKind()) {
         case PACKAGE:
         case EXECUTABLE:
         case MODULE:
            throw new IllegalArgumentException("Executable, package and module are illegal argument for Types.contains(..)");
         case OTHER:
         case UNION:
         case INTERSECTION:
         default:
      }
   }

   private void validateRealTypes(TypeMirror t1, TypeMirror t2) {
      this.validateRealType(t1);
      this.validateRealType(t2);
   }

   public TypeElement boxedClass(PrimitiveType p) {
      PrimitiveTypeImpl primitiveTypeImpl = (PrimitiveTypeImpl)p;
      BaseTypeBinding baseTypeBinding = (BaseTypeBinding)primitiveTypeImpl._binding;
      TypeBinding boxed = this._env.getLookupEnvironment().computeBoxingType(baseTypeBinding);
      return (TypeElement)this._env.getFactory().newElement(boxed);
   }

   public TypeMirror capture(TypeMirror t) {
      this.validateRealType(t);
      TypeMirrorImpl typeMirrorImpl = (TypeMirrorImpl)t;
      if (typeMirrorImpl._binding instanceof ParameterizedTypeBinding) {
         throw new UnsupportedOperationException("NYI: TypesImpl.capture(...)");
      } else {
         return t;
      }
   }

   public boolean contains(TypeMirror t1, TypeMirror t2) {
      this.validateRealTypes(t1, t2);
      throw new UnsupportedOperationException("NYI: TypesImpl.contains(" + t1 + ", " + t2 + ")");
   }

   public List directSupertypes(TypeMirror t) {
      this.validateRealType(t);
      TypeMirrorImpl typeMirrorImpl = (TypeMirrorImpl)t;
      Binding binding = typeMirrorImpl._binding;
      if (!(binding instanceof ReferenceBinding)) {
         return Collections.emptyList();
      } else {
         ReferenceBinding referenceBinding = (ReferenceBinding)binding;
         ArrayList list = new ArrayList();
         ReferenceBinding superclass = referenceBinding.superclass();
         if (superclass != null) {
            list.add(this._env.getFactory().newTypeMirror(superclass));
         }

         ReferenceBinding[] var10;
         int var9 = (var10 = referenceBinding.superInterfaces()).length;

         for(int var8 = 0; var8 < var9; ++var8) {
            ReferenceBinding interfaceBinding = var10[var8];
            list.add(this._env.getFactory().newTypeMirror(interfaceBinding));
         }

         return Collections.unmodifiableList(list);
      }
   }

   public TypeMirror erasure(TypeMirror t) {
      this.validateRealType(t);
      TypeMirrorImpl typeMirrorImpl = (TypeMirrorImpl)t;
      Binding binding = typeMirrorImpl._binding;
      TypeBinding typeBinding;
      if (binding instanceof ReferenceBinding) {
         typeBinding = ((ReferenceBinding)binding).erasure();
         if (typeBinding.isGenericType()) {
            typeBinding = this._env.getLookupEnvironment().convertToRawType(typeBinding, false);
         }

         return this._env.getFactory().newTypeMirror(typeBinding);
      } else if (binding instanceof ArrayBinding) {
         typeBinding = (TypeBinding)binding;
         TypeBinding leafType = typeBinding.leafComponentType().erasure();
         if (leafType.isGenericType()) {
            leafType = this._env.getLookupEnvironment().convertToRawType(leafType, false);
         }

         return this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createArrayType(leafType, typeBinding.dimensions()));
      } else {
         return t;
      }
   }

   public ArrayType getArrayType(TypeMirror componentType) {
      TypeMirrorImpl typeMirrorImpl = (TypeMirrorImpl)componentType;
      TypeBinding typeBinding = (TypeBinding)typeMirrorImpl._binding;
      return (ArrayType)this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createArrayType(typeBinding.leafComponentType(), typeBinding.dimensions() + 1));
   }

   public DeclaredType getDeclaredType(TypeElement typeElem, TypeMirror... typeArgs) {
      int typeArgsLength = typeArgs.length;
      TypeElementImpl typeElementImpl = (TypeElementImpl)typeElem;
      ReferenceBinding elementBinding = (ReferenceBinding)typeElementImpl._binding;
      TypeVariableBinding[] typeVariables = elementBinding.typeVariables();
      int typeVariablesLength = typeVariables.length;
      if (typeArgsLength == 0) {
         return elementBinding.isGenericType() ? (DeclaredType)this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createRawType(elementBinding, (ReferenceBinding)null)) : (DeclaredType)typeElem.asType();
      } else if (typeArgsLength != typeVariablesLength) {
         throw new IllegalArgumentException("Number of typeArguments doesn't match the number of formal parameters of typeElem");
      } else {
         TypeBinding[] typeArguments = new TypeBinding[typeArgsLength];

         for(int i = 0; i < typeArgsLength; ++i) {
            TypeMirrorImpl typeMirrorImpl = (TypeMirrorImpl)typeArgs[i];
            Binding binding = typeMirrorImpl._binding;
            if (!(binding instanceof TypeBinding)) {
               throw new IllegalArgumentException("Invalid type argument: " + typeMirrorImpl);
            }

            typeArguments[i] = (TypeBinding)binding;
         }

         ReferenceBinding enclosing = elementBinding.enclosingType();
         if (enclosing != null) {
            enclosing = this._env.getLookupEnvironment().createRawType((ReferenceBinding)enclosing, (ReferenceBinding)null);
         }

         return (DeclaredType)this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createParameterizedType(elementBinding, typeArguments, (ReferenceBinding)enclosing));
      }
   }

   public DeclaredType getDeclaredType(DeclaredType containing, TypeElement typeElem, TypeMirror... typeArgs) {
      int typeArgsLength = typeArgs.length;
      TypeElementImpl typeElementImpl = (TypeElementImpl)typeElem;
      ReferenceBinding elementBinding = (ReferenceBinding)typeElementImpl._binding;
      TypeVariableBinding[] typeVariables = elementBinding.typeVariables();
      int typeVariablesLength = typeVariables.length;
      DeclaredTypeImpl declaredTypeImpl = (DeclaredTypeImpl)containing;
      ReferenceBinding enclosingType = (ReferenceBinding)declaredTypeImpl._binding;
      if (typeArgsLength == 0) {
         if (elementBinding.isGenericType()) {
            return (DeclaredType)this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createRawType(elementBinding, enclosingType));
         } else {
            ParameterizedTypeBinding ptb = this._env.getLookupEnvironment().createParameterizedType(elementBinding, (TypeBinding[])null, enclosingType);
            return (DeclaredType)this._env.getFactory().newTypeMirror(ptb);
         }
      } else if (typeArgsLength != typeVariablesLength) {
         throw new IllegalArgumentException("Number of typeArguments doesn't match the number of formal parameters of typeElem");
      } else {
         TypeBinding[] typeArguments = new TypeBinding[typeArgsLength];

         for(int i = 0; i < typeArgsLength; ++i) {
            TypeMirrorImpl typeMirrorImpl = (TypeMirrorImpl)typeArgs[i];
            Binding binding = typeMirrorImpl._binding;
            if (!(binding instanceof TypeBinding)) {
               throw new IllegalArgumentException("Invalid type for a type arguments : " + typeMirrorImpl);
            }

            typeArguments[i] = (TypeBinding)binding;
         }

         return (DeclaredType)this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createParameterizedType(elementBinding, typeArguments, enclosingType));
      }
   }

   public NoType getNoType(TypeKind kind) {
      return this._env.getFactory().getNoType(kind);
   }

   public NullType getNullType() {
      return this._env.getFactory().getNullType();
   }

   public PrimitiveType getPrimitiveType(TypeKind kind) {
      return this._env.getFactory().getPrimitiveType(kind);
   }

   public WildcardType getWildcardType(TypeMirror extendsBound, TypeMirror superBound) {
      if (extendsBound != null && superBound != null) {
         throw new IllegalArgumentException("Extends and super bounds cannot be set at the same time");
      } else {
         TypeMirrorImpl superBoundMirrorType;
         TypeBinding typeBinding;
         if (extendsBound != null) {
            superBoundMirrorType = (TypeMirrorImpl)extendsBound;
            typeBinding = (TypeBinding)superBoundMirrorType._binding;
            return (WildcardType)this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createWildcard((ReferenceBinding)null, 0, typeBinding, (TypeBinding[])null, 1));
         } else if (superBound != null) {
            superBoundMirrorType = (TypeMirrorImpl)superBound;
            typeBinding = (TypeBinding)superBoundMirrorType._binding;
            return new WildcardTypeImpl(this._env, this._env.getLookupEnvironment().createWildcard((ReferenceBinding)null, 0, typeBinding, (TypeBinding[])null, 2));
         } else {
            return new WildcardTypeImpl(this._env, this._env.getLookupEnvironment().createWildcard((ReferenceBinding)null, 0, (TypeBinding)null, (TypeBinding[])null, 0));
         }
      }
   }

   public boolean isAssignable(TypeMirror t1, TypeMirror t2) {
      this.validateRealTypes(t1, t2);
      if (t1 instanceof TypeMirrorImpl && t2 instanceof TypeMirrorImpl) {
         Binding b1 = ((TypeMirrorImpl)t1).binding();
         Binding b2 = ((TypeMirrorImpl)t2).binding();
         if (b1 instanceof TypeBinding && b2 instanceof TypeBinding) {
            if (((TypeBinding)b1).isCompatibleWith((TypeBinding)b2)) {
               return true;
            } else {
               TypeBinding convertedType = this._env.getLookupEnvironment().computeBoxingType((TypeBinding)b1);
               return convertedType != null && convertedType.isCompatibleWith((TypeBinding)b2);
            }
         } else {
            throw new IllegalArgumentException();
         }
      } else {
         return false;
      }
   }

   public boolean isSameType(TypeMirror t1, TypeMirror t2) {
      if (t1 instanceof NoTypeImpl) {
         if (t2 instanceof NoTypeImpl) {
            return ((NoTypeImpl)t1).getKind() == ((NoTypeImpl)t2).getKind();
         } else {
            return false;
         }
      } else if (t2 instanceof NoTypeImpl) {
         return false;
      } else if (t1.getKind() != TypeKind.WILDCARD && t2.getKind() != TypeKind.WILDCARD) {
         if (t1 == t2) {
            return true;
         } else if (t1 instanceof TypeMirrorImpl && t2 instanceof TypeMirrorImpl) {
            Binding b1 = ((TypeMirrorImpl)t1).binding();
            Binding b2 = ((TypeMirrorImpl)t2).binding();
            if (b1 == b2) {
               return true;
            } else if (b1 instanceof TypeBinding && b2 instanceof TypeBinding) {
               TypeBinding type1 = (TypeBinding)b1;
               TypeBinding type2 = (TypeBinding)b2;
               return TypeBinding.equalsEquals(type1, type2) ? true : CharOperation.equals(type1.computeUniqueKey(), type2.computeUniqueKey());
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean isSubsignature(ExecutableType m1, ExecutableType m2) {
      MethodBinding methodBinding1 = (MethodBinding)((ExecutableTypeImpl)m1)._binding;
      MethodBinding methodBinding2 = (MethodBinding)((ExecutableTypeImpl)m2)._binding;
      if (!CharOperation.equals(methodBinding1.selector, methodBinding2.selector)) {
         return false;
      } else {
         return methodBinding1.areParameterErasuresEqual(methodBinding2) && methodBinding1.areTypeVariableErasuresEqual(methodBinding2);
      }
   }

   public boolean isSubtype(TypeMirror t1, TypeMirror t2) {
      this.validateRealTypes(t1, t2);
      if (t1 instanceof NoTypeImpl) {
         if (t2 instanceof NoTypeImpl) {
            return ((NoTypeImpl)t1).getKind() == ((NoTypeImpl)t2).getKind();
         } else {
            return false;
         }
      } else if (t2 instanceof NoTypeImpl) {
         return false;
      } else if (t1 instanceof TypeMirrorImpl && t2 instanceof TypeMirrorImpl) {
         if (t1 == t2) {
            return true;
         } else {
            Binding b1 = ((TypeMirrorImpl)t1).binding();
            Binding b2 = ((TypeMirrorImpl)t2).binding();
            if (b1 == b2) {
               return true;
            } else if (b1 instanceof TypeBinding && b2 instanceof TypeBinding) {
               if (b1.kind() != 132 && b2.kind() != 132) {
                  return ((TypeBinding)b1).isCompatibleWith((TypeBinding)b2);
               } else {
                  return b1.kind() != b2.kind() ? false : ((TypeBinding)b1).isCompatibleWith((TypeBinding)b2);
               }
            } else {
               throw new IllegalArgumentException();
            }
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public PrimitiveType unboxedType(TypeMirror t) {
      if (!(((TypeMirrorImpl)t)._binding instanceof ReferenceBinding)) {
         throw new IllegalArgumentException("Given type mirror cannot be unboxed");
      } else {
         ReferenceBinding boxed = (ReferenceBinding)((TypeMirrorImpl)t)._binding;
         TypeBinding unboxed = this._env.getLookupEnvironment().computeBoxingType(boxed);
         if (unboxed.kind() != 132) {
            throw new IllegalArgumentException();
         } else {
            return (PrimitiveType)this._env.getFactory().newTypeMirror((BaseTypeBinding)unboxed);
         }
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

   private interface MemberInTypeFinder {
      TypeMirror find(ReferenceBinding var1, Binding var2);
   }
}
