package com.bea.core.repackaged.jdt.internal.compiler.apt.model;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Javadoc;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodVerifier;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class ElementsImpl implements Elements {
   private static final Pattern INITIAL_DELIMITER = Pattern.compile("^\\s*/\\*+");
   protected final BaseProcessingEnvImpl _env;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind;

   protected ElementsImpl(BaseProcessingEnvImpl env) {
      this._env = env;
   }

   public static ElementsImpl create(BaseProcessingEnvImpl env) {
      return (ElementsImpl)(SourceVersion.latest().compareTo(SourceVersion.RELEASE_8) <= 0 ? new ElementsImpl(env) : new ElementsImpl9(env));
   }

   public List getAllAnnotationMirrors(Element e) {
      if (e.getKind() == ElementKind.CLASS && e instanceof TypeElementImpl) {
         List annotations = new ArrayList();
         Set annotationTypes = new HashSet();
         ReferenceBinding binding = (ReferenceBinding)((TypeElementImpl)e)._binding;

         for(boolean checkIfInherited = false; binding != null; checkIfInherited = true) {
            if (binding instanceof ParameterizedTypeBinding) {
               binding = ((ParameterizedTypeBinding)binding).genericType();
            }

            AnnotationBinding[] var9;
            int var8 = (var9 = Factory.getPackedAnnotationBindings(binding.getAnnotations())).length;

            for(int var7 = 0; var7 < var8; ++var7) {
               AnnotationBinding annotation = var9[var7];
               if (annotation != null) {
                  ReferenceBinding annotationType = annotation.getAnnotationType();
                  if ((!checkIfInherited || (annotationType.getAnnotationTagBits() & 281474976710656L) != 0L) && !annotationTypes.contains(annotationType)) {
                     annotationTypes.add(annotationType);
                     annotations.add(annotation);
                  }
               }
            }

            binding = binding.superclass();
         }

         List list = new ArrayList(annotations.size());
         Iterator var13 = annotations.iterator();

         while(var13.hasNext()) {
            AnnotationBinding annotation = (AnnotationBinding)var13.next();
            list.add(this._env.getFactory().newAnnotationMirror(annotation));
         }

         return Collections.unmodifiableList(list);
      } else {
         return e.getAnnotationMirrors();
      }
   }

   public List getAllMembers(TypeElement type) {
      if (type != null && type instanceof TypeElementImpl) {
         ReferenceBinding binding = (ReferenceBinding)((TypeElementImpl)type)._binding;
         Map types = new HashMap();
         List fields = new ArrayList();
         Map methods = new HashMap();
         Set superinterfaces = new LinkedHashSet();

         ReferenceBinding nestedType;
         Iterator var10;
         for(boolean ignoreVisibility = true; binding != null; ignoreVisibility = false) {
            this.addMembers(binding, ignoreVisibility, types, fields, methods);
            Set newfound = new LinkedHashSet();
            this.collectSuperInterfaces(binding, superinterfaces, newfound);
            var10 = newfound.iterator();

            while(var10.hasNext()) {
               nestedType = (ReferenceBinding)var10.next();
               this.addMembers(nestedType, false, types, fields, methods);
            }

            superinterfaces.addAll(newfound);
            binding = binding.superclass();
         }

         List allMembers = new ArrayList();
         var10 = types.values().iterator();

         while(var10.hasNext()) {
            nestedType = (ReferenceBinding)var10.next();
            allMembers.add(this._env.getFactory().newElement(nestedType));
         }

         var10 = fields.iterator();

         while(var10.hasNext()) {
            FieldBinding field = (FieldBinding)var10.next();
            allMembers.add(this._env.getFactory().newElement(field));
         }

         var10 = methods.values().iterator();

         while(var10.hasNext()) {
            Set sameNamedMethods = (Set)var10.next();
            Iterator var12 = sameNamedMethods.iterator();

            while(var12.hasNext()) {
               MethodBinding method = (MethodBinding)var12.next();
               allMembers.add(this._env.getFactory().newElement(method));
            }
         }

         return allMembers;
      } else {
         return Collections.emptyList();
      }
   }

   private void collectSuperInterfaces(ReferenceBinding type, Set existing, Set newfound) {
      ReferenceBinding[] var7;
      int var6 = (var7 = type.superInterfaces()).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         ReferenceBinding superinterface = var7[var5];
         if (!existing.contains(superinterface) && !newfound.contains(superinterface)) {
            newfound.add(superinterface);
            this.collectSuperInterfaces(superinterface, existing, newfound);
         }
      }

   }

   private void addMembers(ReferenceBinding binding, boolean ignoreVisibility, Map types, List fields, Map methods) {
      ReferenceBinding[] var9;
      int var8 = (var9 = binding.memberTypes()).length;

      int var7;
      String methodName;
      for(var7 = 0; var7 < var8; ++var7) {
         ReferenceBinding subtype = var9[var7];
         if (ignoreVisibility || !subtype.isPrivate()) {
            methodName = new String(subtype.sourceName());
            if (types.get(methodName) == null) {
               types.put(methodName, subtype);
            }
         }
      }

      FieldBinding[] var18;
      var8 = (var18 = binding.fields()).length;

      for(var7 = 0; var7 < var8; ++var7) {
         FieldBinding field = var18[var7];
         if (ignoreVisibility || !field.isPrivate()) {
            fields.add(field);
         }
      }

      MethodBinding[] var19;
      var8 = (var19 = binding.methods()).length;

      for(var7 = 0; var7 < var8; ++var7) {
         MethodBinding method = var19[var7];
         if (!method.isSynthetic() && (ignoreVisibility || !method.isPrivate() && !method.isConstructor())) {
            methodName = new String(method.selector);
            Set sameNamedMethods = (Set)methods.get(methodName);
            if (sameNamedMethods == null) {
               Set sameNamedMethods = new HashSet(4);
               methods.put(methodName, sameNamedMethods);
               sameNamedMethods.add(method);
            } else {
               boolean unique = true;
               if (!ignoreVisibility) {
                  Iterator var14 = sameNamedMethods.iterator();

                  while(var14.hasNext()) {
                     MethodBinding existing = (MethodBinding)var14.next();
                     MethodVerifier verifier = this._env.getLookupEnvironment().methodVerifier();
                     if (verifier.doesMethodOverride(existing, method)) {
                        unique = false;
                        break;
                     }
                  }
               }

               if (unique) {
                  sameNamedMethods.add(method);
               }
            }
         }
      }

   }

   public Name getBinaryName(TypeElement type) {
      TypeElementImpl typeElementImpl = (TypeElementImpl)type;
      ReferenceBinding referenceBinding = (ReferenceBinding)typeElementImpl._binding;
      return new NameImpl(CharOperation.replaceOnCopy(referenceBinding.constantPoolName(), '/', '.'));
   }

   public String getConstantExpression(Object value) {
      if (!(value instanceof Integer) && !(value instanceof Byte) && !(value instanceof Float) && !(value instanceof Double) && !(value instanceof Long) && !(value instanceof Short) && !(value instanceof Character) && !(value instanceof String) && !(value instanceof Boolean)) {
         throw new IllegalArgumentException("Not a valid wrapper type : " + value.getClass());
      } else {
         StringBuilder builder;
         if (value instanceof Character) {
            builder = new StringBuilder();
            builder.append('\'').append(value).append('\'');
            return String.valueOf(builder);
         } else if (value instanceof String) {
            builder = new StringBuilder();
            builder.append('"').append(value).append('"');
            return String.valueOf(builder);
         } else if (value instanceof Float) {
            builder = new StringBuilder();
            builder.append(value).append('f');
            return String.valueOf(builder);
         } else if (value instanceof Long) {
            builder = new StringBuilder();
            builder.append(value).append('L');
            return String.valueOf(builder);
         } else if (value instanceof Short) {
            builder = new StringBuilder();
            builder.append("(short)").append(value);
            return String.valueOf(builder);
         } else if (value instanceof Byte) {
            builder = new StringBuilder();
            builder.append("(byte)0x");
            int intValue = (Byte)value;
            String hexString = Integer.toHexString(intValue & 255);
            if (hexString.length() < 2) {
               builder.append('0');
            }

            builder.append(hexString);
            return String.valueOf(builder);
         } else {
            return String.valueOf(value);
         }
      }
   }

   public String getDocComment(Element e) {
      char[] unparsed = this.getUnparsedDocComment(e);
      return formatJavadoc(unparsed);
   }

   private char[] getUnparsedDocComment(Element e) {
      Javadoc javadoc = null;
      ReferenceContext referenceContext = null;
      switch (e.getKind()) {
         case PACKAGE:
            PackageElementImpl packageElementImpl = (PackageElementImpl)e;
            PackageBinding packageBinding = (PackageBinding)packageElementImpl._binding;
            char[][] compoundName = CharOperation.arrayConcat(packageBinding.compoundName, TypeConstants.PACKAGE_INFO_NAME);
            ReferenceBinding type = this._env.getLookupEnvironment().getType(compoundName);
            if (type != null && type.isValidBinding() && type instanceof SourceTypeBinding) {
               SourceTypeBinding sourceTypeBinding = (SourceTypeBinding)type;
               referenceContext = sourceTypeBinding.scope.referenceContext;
               javadoc = ((TypeDeclaration)referenceContext).javadoc;
            }
            break;
         case ENUM:
         case CLASS:
         case ANNOTATION_TYPE:
         case INTERFACE:
            TypeElementImpl typeElementImpl = (TypeElementImpl)e;
            ReferenceBinding referenceBinding = (ReferenceBinding)typeElementImpl._binding;
            if (referenceBinding instanceof SourceTypeBinding) {
               SourceTypeBinding sourceTypeBinding = (SourceTypeBinding)referenceBinding;
               referenceContext = sourceTypeBinding.scope.referenceContext;
               javadoc = ((TypeDeclaration)referenceContext).javadoc;
            }
            break;
         case ENUM_CONSTANT:
         case FIELD:
            VariableElementImpl variableElementImpl = (VariableElementImpl)e;
            FieldBinding fieldBinding = (FieldBinding)variableElementImpl._binding;
            FieldDeclaration sourceField = fieldBinding.sourceField();
            if (sourceField != null) {
               javadoc = sourceField.javadoc;
               if (fieldBinding.declaringClass instanceof SourceTypeBinding) {
                  SourceTypeBinding sourceTypeBinding = (SourceTypeBinding)fieldBinding.declaringClass;
                  referenceContext = sourceTypeBinding.scope.referenceContext;
               }
            }
            break;
         case PARAMETER:
         case LOCAL_VARIABLE:
         case EXCEPTION_PARAMETER:
         default:
            return null;
         case METHOD:
         case CONSTRUCTOR:
            ExecutableElementImpl executableElementImpl = (ExecutableElementImpl)e;
            MethodBinding methodBinding = (MethodBinding)executableElementImpl._binding;
            AbstractMethodDeclaration sourceMethod = methodBinding.sourceMethod();
            if (sourceMethod != null) {
               javadoc = sourceMethod.javadoc;
               referenceContext = sourceMethod;
            }
      }

      if (javadoc != null && referenceContext != null) {
         char[] contents = ((ReferenceContext)referenceContext).compilationResult().getCompilationUnit().getContents();
         if (contents != null) {
            return CharOperation.subarray(contents, javadoc.sourceStart, javadoc.sourceEnd - 1);
         }
      }

      return null;
   }

   private static String formatJavadoc(char[] unparsed) {
      if (unparsed != null && unparsed.length >= 5) {
         String[] lines = (new String(unparsed)).split("\n");
         Matcher delimiterMatcher = INITIAL_DELIMITER.matcher(lines[0]);
         if (!delimiterMatcher.find()) {
            return null;
         } else {
            int iOpener = delimiterMatcher.end();
            lines[0] = lines[0].substring(iOpener);
            int line;
            if (lines.length == 1) {
               StringBuilder sb = new StringBuilder();
               char[] chars = lines[0].toCharArray();
               boolean startingWhitespaces = true;
               char[] var24 = chars;
               int var23 = chars.length;

               for(line = 0; line < var23; ++line) {
                  char c = var24[line];
                  if (Character.isWhitespace(c)) {
                     if (!startingWhitespaces) {
                        sb.append(c);
                     }
                  } else {
                     startingWhitespaces = false;
                     sb.append(c);
                  }
               }

               return sb.toString();
            } else {
               int firstLine = lines[0].trim().length() > 0 ? 0 : 1;
               int lastLine = lines[lines.length - 1].trim().length() > 0 ? lines.length - 1 : lines.length - 2;
               StringBuilder sb = new StringBuilder();
               if (lines[0].length() != 0 && firstLine == 1) {
                  sb.append('\n');
               }

               boolean preserveLineSeparator = lines[0].length() == 0;

               for(line = firstLine; line <= lastLine; ++line) {
                  char[] chars = lines[line].toCharArray();
                  int starsIndex = getStars(chars);
                  int leadingWhitespaces = 0;
                  boolean recordLeadingWhitespaces = true;
                  int i = 0;

                  for(int max = chars.length; i < max; ++i) {
                     char c = chars[i];
                     switch (c) {
                        case ' ':
                           if (starsIndex == -1) {
                              if (recordLeadingWhitespaces) {
                                 ++leadingWhitespaces;
                              } else {
                                 sb.append(c);
                              }
                           } else if (i >= starsIndex) {
                              sb.append(c);
                           }
                           break;
                        default:
                           recordLeadingWhitespaces = false;
                           if (leadingWhitespaces == 0) {
                              if (c == '\t') {
                                 if (i >= starsIndex) {
                                    sb.append(c);
                                 }
                              } else if (c != '*' || i > starsIndex) {
                                 sb.append(c);
                              }
                           } else {
                              int numberOfTabs = leadingWhitespaces / 8;
                              int j;
                              int max2;
                              if (numberOfTabs != 0) {
                                 j = 0;

                                 for(max2 = numberOfTabs; j < max2; ++j) {
                                    sb.append("        ");
                                 }

                                 if (leadingWhitespaces % 8 >= 1) {
                                    sb.append(' ');
                                 }
                              } else if (line != 0) {
                                 j = 0;

                                 for(max2 = leadingWhitespaces; j < max2; ++j) {
                                    sb.append(' ');
                                 }
                              }

                              leadingWhitespaces = 0;
                              sb.append(c);
                           }
                     }
                  }

                  i = lines.length - 1;
                  if (line < i) {
                     sb.append('\n');
                  } else if (preserveLineSeparator && line == i) {
                     sb.append('\n');
                  }
               }

               return sb.toString();
            }
         }
      } else {
         return null;
      }
   }

   private static int getStars(char[] line) {
      int i = 0;

      for(int max = line.length; i < max; ++i) {
         char c = line[i];
         if (!Character.isWhitespace(c)) {
            if (c == '*') {
               for(int j = i + 1; j < max; ++j) {
                  if (line[j] != '*') {
                     return j;
                  }
               }

               return max - 1;
            }
            break;
         }
      }

      return -1;
   }

   public Map getElementValuesWithDefaults(AnnotationMirror a) {
      return ((AnnotationMirrorImpl)a).getElementValuesWithDefaults();
   }

   public Name getName(CharSequence cs) {
      return new NameImpl(cs);
   }

   public PackageElement getPackageElement(CharSequence name) {
      LookupEnvironment le = this._env.getLookupEnvironment();
      if (name.length() == 0) {
         return (PackageElement)this._env.getFactory().newElement(le.defaultPackage);
      } else {
         char[] packageName = name.toString().toCharArray();
         PackageBinding packageBinding = le.createPackage(CharOperation.splitOn('.', packageName));
         return packageBinding == null ? null : (PackageElement)this._env.getFactory().newElement(packageBinding);
      }
   }

   public PackageElement getPackageOf(Element type) {
      VariableElementImpl variableElementImpl;
      switch (type.getKind()) {
         case PACKAGE:
            return (PackageElement)type;
         case ENUM:
         case CLASS:
         case ANNOTATION_TYPE:
         case INTERFACE:
            TypeElementImpl typeElementImpl = (TypeElementImpl)type;
            ReferenceBinding referenceBinding = (ReferenceBinding)typeElementImpl._binding;
            return (PackageElement)this._env.getFactory().newElement(referenceBinding.fPackage);
         case ENUM_CONSTANT:
         case FIELD:
            variableElementImpl = (VariableElementImpl)type;
            FieldBinding fieldBinding = (FieldBinding)variableElementImpl._binding;
            return (PackageElement)this._env.getFactory().newElement(fieldBinding.declaringClass.fPackage);
         case PARAMETER:
            variableElementImpl = (VariableElementImpl)type;
            LocalVariableBinding localVariableBinding = (LocalVariableBinding)variableElementImpl._binding;
            return (PackageElement)this._env.getFactory().newElement(localVariableBinding.declaringScope.classScope().referenceContext.binding.fPackage);
         case LOCAL_VARIABLE:
         case EXCEPTION_PARAMETER:
         case STATIC_INIT:
         case INSTANCE_INIT:
         case TYPE_PARAMETER:
         case OTHER:
            return null;
         case METHOD:
         case CONSTRUCTOR:
            ExecutableElementImpl executableElementImpl = (ExecutableElementImpl)type;
            MethodBinding methodBinding = (MethodBinding)executableElementImpl._binding;
            return (PackageElement)this._env.getFactory().newElement(methodBinding.declaringClass.fPackage);
         default:
            return null;
      }
   }

   public TypeElement getTypeElement(CharSequence name) {
      LookupEnvironment le = this._env.getLookupEnvironment();
      char[][] compoundName = CharOperation.splitOn('.', name.toString().toCharArray());
      ReferenceBinding binding = le.getType(compoundName);
      if (binding == null) {
         ReferenceBinding topLevelBinding = null;
         int topLevelSegments = compoundName.length;

         do {
            --topLevelSegments;
            if (topLevelSegments <= 0) {
               break;
            }

            char[][] topLevelName = new char[topLevelSegments][];

            for(int i = 0; i < topLevelSegments; ++i) {
               topLevelName[i] = compoundName[i];
            }

            topLevelBinding = le.getType(topLevelName);
         } while(topLevelBinding == null);

         if (topLevelBinding == null) {
            return null;
         }

         binding = topLevelBinding;

         for(int i = topLevelSegments; binding != null && i < compoundName.length; ++i) {
            binding = binding.getMemberType(compoundName[i]);
         }
      }

      if (binding == null) {
         return null;
      } else {
         return (binding.tagBits & 128L) != 0L ? null : new TypeElementImpl(this._env, binding, (ElementKind)null);
      }
   }

   public boolean hides(Element hider, Element hidden) {
      if (hidden == null) {
         throw new NullPointerException();
      } else {
         return ((ElementImpl)hider).hides(hidden);
      }
   }

   public boolean isDeprecated(Element e) {
      if (!(e instanceof ElementImpl)) {
         return false;
      } else {
         return (((ElementImpl)e)._binding.getAnnotationTagBits() & 70368744177664L) != 0L;
      }
   }

   public boolean overrides(ExecutableElement overrider, ExecutableElement overridden, TypeElement type) {
      if (overridden != null && type != null) {
         return ((ExecutableElementImpl)overrider).overrides(overridden, type);
      } else {
         throw new NullPointerException();
      }
   }

   public void printElements(Writer w, Element... elements) {
      String lineSeparator = System.getProperty("line.separator");
      Element[] var7 = elements;
      int var6 = elements.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         Element element = var7[var5];

         try {
            w.write(element.toString());
            w.write(lineSeparator);
         } catch (IOException var9) {
         }
      }

      try {
         w.flush();
      } catch (IOException var8) {
      }

   }

   public boolean isFunctionalInterface(TypeElement type) {
      if (type != null && type.getKind() == ElementKind.INTERFACE) {
         ReferenceBinding binding = (ReferenceBinding)((TypeElementImpl)type)._binding;
         if (binding instanceof SourceTypeBinding) {
            return binding.isFunctionalInterface(((SourceTypeBinding)binding).scope);
         }
      }

      return false;
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
}
