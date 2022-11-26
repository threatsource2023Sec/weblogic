package com.bea.core.repackaged.jdt.internal.compiler;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AnnotationMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Argument;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ArrayInitializer;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ClassLiteralAccess;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ExportsStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FunctionalExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LambdaExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LocalDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MemberValuePair;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ModuleDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.NormalAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.OpensStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.QualifiedNameReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Receiver;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ReferenceExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.RequiresStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleMemberAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleNameReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SwitchStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeParameter;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.AnnotationContext;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.AttributeNamesConstants;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ConstantPool;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ExceptionLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.StackMapFrame;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.StackMapFrameCodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.TypeAnnotationCodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.VerificationTypeInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.StringConstant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PolymorphicMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SyntheticMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeIds;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortMethod;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortType;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ShouldNotImplement;
import com.bea.core.repackaged.jdt.internal.compiler.util.Messages;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassFile implements TypeConstants, TypeIds {
   private byte[] bytes;
   public CodeStream codeStream;
   public ConstantPool constantPool;
   public int constantPoolOffset;
   public byte[] contents;
   public int contentsOffset;
   protected boolean creatingProblemType;
   public ClassFile enclosingClassFile;
   public byte[] header;
   public int headerOffset;
   public Map innerClassesBindings;
   public List bootstrapMethods = null;
   public int methodCount;
   public int methodCountOffset;
   boolean isShared = false;
   public int produceAttributes;
   public SourceTypeBinding referenceBinding;
   public boolean isNestedType;
   public long targetJDK;
   public List missingTypes = null;
   public Set visitedTypes;
   public static final int INITIAL_CONTENTS_SIZE = 400;
   public static final int INITIAL_HEADER_SIZE = 1500;
   public static final int INNER_CLASSES_SIZE = 5;
   public static final int NESTED_MEMBER_SIZE = 5;

   public static void createProblemType(TypeDeclaration typeDeclaration, CompilationResult unitResult) {
      createProblemType(typeDeclaration, (ClassFile)null, unitResult);
   }

   private static void createProblemType(TypeDeclaration typeDeclaration, ClassFile parentClassFile, CompilationResult unitResult) {
      SourceTypeBinding typeBinding = typeDeclaration.binding;
      ClassFile classFile = getNewInstance(typeBinding);
      classFile.initialize(typeBinding, parentClassFile, true);
      int i;
      int problemsLength;
      if (typeBinding.hasMemberTypes()) {
         ReferenceBinding[] members = typeBinding.memberTypes;
         i = 0;

         for(problemsLength = members.length; i < problemsLength; ++i) {
            classFile.recordInnerClasses(members[i]);
         }
      }

      if (typeBinding.isNestedType()) {
         classFile.recordInnerClasses(typeBinding);
      }

      TypeVariableBinding[] typeVariables = typeBinding.typeVariables();
      i = 0;

      for(problemsLength = typeVariables.length; i < problemsLength; ++i) {
         TypeVariableBinding typeVariableBinding = typeVariables[i];
         if ((typeVariableBinding.tagBits & 2048L) != 0L) {
            Util.recordNestedType(classFile, typeVariableBinding);
         }
      }

      FieldBinding[] fields = typeBinding.fields();
      if (fields != null && fields != Binding.NO_FIELDS) {
         classFile.addFieldInfos();
      } else {
         classFile.contents[classFile.contentsOffset++] = 0;
         classFile.contents[classFile.contentsOffset++] = 0;
      }

      classFile.setForMethodInfos();
      CategorizedProblem[] problems = unitResult.getErrors();
      if (problems == null) {
         problems = new CategorizedProblem[0];
      }

      CategorizedProblem[] problemsCopy = new CategorizedProblem[problemsLength = problems.length];
      System.arraycopy(problems, 0, problemsCopy, 0, problemsLength);
      AbstractMethodDeclaration[] methodDecls = typeDeclaration.methods;
      boolean abstractMethodsOnly = false;
      int i;
      int max;
      if (methodDecls != null) {
         if (typeBinding.isInterface()) {
            if (typeBinding.scope.compilerOptions().sourceLevel < 3407872L) {
               abstractMethodsOnly = true;
            }

            classFile.addProblemClinit(problemsCopy);
         }

         i = 0;

         for(max = methodDecls.length; i < max; ++i) {
            AbstractMethodDeclaration methodDecl = methodDecls[i];
            MethodBinding method = methodDecl.binding;
            if (method != null) {
               if (abstractMethodsOnly) {
                  method.modifiers = 1025;
               }

               if (method.isConstructor()) {
                  if (!typeBinding.isInterface()) {
                     classFile.addProblemConstructor(methodDecl, method, problemsCopy);
                  }
               } else if (method.isAbstract()) {
                  classFile.addAbstractMethod(methodDecl, method);
               } else {
                  classFile.addProblemMethod(methodDecl, method, problemsCopy);
               }
            }
         }

         classFile.addDefaultAbstractMethods();
      }

      if (typeDeclaration.memberTypes != null) {
         i = 0;

         for(max = typeDeclaration.memberTypes.length; i < max; ++i) {
            TypeDeclaration memberType = typeDeclaration.memberTypes[i];
            if (memberType.binding != null) {
               createProblemType(memberType, classFile, unitResult);
            }
         }
      }

      classFile.addAttributes();
      unitResult.record(typeBinding.constantPoolName(), classFile);
   }

   public static ClassFile getNewInstance(SourceTypeBinding typeBinding) {
      LookupEnvironment env = typeBinding.scope.environment();
      return env.classFilePool.acquire(typeBinding);
   }

   protected ClassFile() {
   }

   public ClassFile(SourceTypeBinding typeBinding) {
      this.constantPool = new ConstantPool(this);
      CompilerOptions options = typeBinding.scope.compilerOptions();
      this.targetJDK = options.targetJDK;
      this.produceAttributes = options.produceDebugAttributes;
      this.referenceBinding = typeBinding;
      this.isNestedType = typeBinding.isNestedType();
      if (this.targetJDK >= 3276800L) {
         this.produceAttributes |= 8;
         if (this.targetJDK >= 3407872L) {
            this.produceAttributes |= 32;
            this.codeStream = new TypeAnnotationCodeStream(this);
            if (options.produceMethodParameters) {
               this.produceAttributes |= 64;
            }
         } else {
            this.codeStream = new StackMapFrameCodeStream(this);
         }
      } else if (this.targetJDK == 2949124L) {
         this.targetJDK = 2949123L;
         this.produceAttributes |= 16;
         this.codeStream = new StackMapFrameCodeStream(this);
      } else {
         this.codeStream = new CodeStream(this);
      }

      this.initByteArrays(this.referenceBinding.methods().length + this.referenceBinding.fields().length);
   }

   public ClassFile(ModuleBinding moduleBinding, CompilerOptions options) {
      this.constantPool = new ConstantPool(this);
      this.targetJDK = options.targetJDK;
      this.produceAttributes = 1;
      this.isNestedType = false;
      this.codeStream = new StackMapFrameCodeStream(this);
      this.initByteArrays(0);
   }

   public void addAbstractMethod(AbstractMethodDeclaration method, MethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      this.completeMethodInfo(methodBinding, methodAttributeOffset, attributeNumber);
   }

   public void addAttributes() {
      this.contents[this.methodCountOffset++] = (byte)(this.methodCount >> 8);
      this.contents[this.methodCountOffset] = (byte)this.methodCount;
      int attributesNumber = 0;
      int attributeOffset = this.contentsOffset;
      this.contentsOffset += 2;
      int numberOfInnerClasses;
      if ((this.produceAttributes & 1) != 0) {
         String fullFileName = new String(this.referenceBinding.scope.referenceCompilationUnit().getFileName());
         fullFileName = fullFileName.replace('\\', '/');
         numberOfInnerClasses = fullFileName.lastIndexOf(47);
         if (numberOfInnerClasses != -1) {
            fullFileName = fullFileName.substring(numberOfInnerClasses + 1, fullFileName.length());
         }

         attributesNumber += this.generateSourceAttribute(fullFileName);
      }

      if (this.referenceBinding.isDeprecated()) {
         attributesNumber += this.generateDeprecatedAttribute();
      }

      char[] genericSignature = this.referenceBinding.genericSignature();
      if (genericSignature != null) {
         attributesNumber += this.generateSignatureAttribute(genericSignature);
      }

      if (this.targetJDK >= 3211264L && this.referenceBinding.isNestedType() && !this.referenceBinding.isMemberType()) {
         attributesNumber += this.generateEnclosingMethodAttribute();
      }

      if (this.targetJDK >= 3145728L) {
         TypeDeclaration typeDeclaration = this.referenceBinding.scope.referenceContext;
         if (typeDeclaration != null) {
            Annotation[] annotations = typeDeclaration.annotations;
            if (annotations != null) {
               long targetMask;
               if (typeDeclaration.isPackageInfo()) {
                  targetMask = 8796093022208L;
               } else if (this.referenceBinding.isAnnotationType()) {
                  targetMask = 4466765987840L;
               } else {
                  targetMask = 9007267974217728L;
               }

               attributesNumber += this.generateRuntimeAnnotations(annotations, targetMask);
            }
         }
      }

      ReferenceBinding[] superInterfaces;
      if (this.referenceBinding.isHierarchyInconsistent()) {
         ReferenceBinding superclass = this.referenceBinding.superclass;
         if (superclass != null) {
            this.missingTypes = superclass.collectMissingTypes(this.missingTypes);
         }

         superInterfaces = this.referenceBinding.superInterfaces();
         int i = 0;

         for(int max = superInterfaces.length; i < max; ++i) {
            this.missingTypes = superInterfaces[i].collectMissingTypes(this.missingTypes);
         }

         attributesNumber += this.generateHierarchyInconsistentAttribute();
      }

      if (this.bootstrapMethods != null && !this.bootstrapMethods.isEmpty()) {
         attributesNumber += this.generateBootstrapMethods(this.bootstrapMethods);
      }

      numberOfInnerClasses = this.innerClassesBindings == null ? 0 : this.innerClassesBindings.size();
      if (numberOfInnerClasses != 0) {
         superInterfaces = new ReferenceBinding[numberOfInnerClasses];
         this.innerClassesBindings.keySet().toArray(superInterfaces);
         Arrays.sort(superInterfaces, new Comparator() {
            public int compare(Object o1, Object o2) {
               TypeBinding binding1 = (TypeBinding)o1;
               TypeBinding binding2 = (TypeBinding)o2;
               Boolean onBottom1 = (Boolean)ClassFile.this.innerClassesBindings.get(o1);
               Boolean onBottom2 = (Boolean)ClassFile.this.innerClassesBindings.get(o2);
               if (onBottom1) {
                  if (!onBottom2) {
                     return 1;
                  }
               } else if (onBottom2) {
                  return -1;
               }

               return CharOperation.compareTo(binding1.constantPoolName(), binding2.constantPoolName());
            }
         });
         attributesNumber += this.generateInnerClassAttribute(numberOfInnerClasses, superInterfaces);
      }

      if (this.missingTypes != null) {
         this.generateMissingTypesAttribute();
         ++attributesNumber;
      }

      attributesNumber += this.generateTypeAnnotationAttributeForTypeDeclaration();
      if (this.targetJDK >= 3604480L) {
         attributesNumber += this.generateNestAttributes();
      }

      if (attributeOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contents[attributeOffset++] = (byte)(attributesNumber >> 8);
      this.contents[attributeOffset] = (byte)attributesNumber;
      this.header = this.constantPool.poolContent;
      this.headerOffset = this.constantPool.currentOffset;
      int constantPoolCount = this.constantPool.currentIndex;
      this.header[this.constantPoolOffset++] = (byte)(constantPoolCount >> 8);
      this.header[this.constantPoolOffset] = (byte)constantPoolCount;
   }

   public void addModuleAttributes(ModuleBinding module, Annotation[] annotations, CompilationUnitDeclaration cud) {
      int attributesNumber = 0;
      int attributeOffset = this.contentsOffset;
      this.contentsOffset += 2;
      if ((this.produceAttributes & 1) != 0) {
         String fullFileName = new String(cud.getFileName());
         fullFileName = fullFileName.replace('\\', '/');
         int lastIndex = fullFileName.lastIndexOf(47);
         if (lastIndex != -1) {
            fullFileName = fullFileName.substring(lastIndex + 1, fullFileName.length());
         }

         attributesNumber += this.generateSourceAttribute(fullFileName);
      }

      attributesNumber += this.generateModuleAttribute(cud.moduleDeclaration);
      if (annotations != null) {
         long targetMask = 2305843009213693952L;
         attributesNumber += this.generateRuntimeAnnotations(annotations, targetMask);
      }

      char[] mainClass = cud.moduleDeclaration.binding.mainClassName;
      if (mainClass != null) {
         attributesNumber += this.generateModuleMainClassAttribute(CharOperation.replaceOnCopy(mainClass, '.', '/'));
      }

      char[][] packageNames = cud.moduleDeclaration.binding.getPackageNamesForClassFile();
      if (packageNames != null) {
         attributesNumber += this.generateModulePackagesAttribute(packageNames);
      }

      if (attributeOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contents[attributeOffset++] = (byte)(attributesNumber >> 8);
      this.contents[attributeOffset] = (byte)attributesNumber;
      this.header = this.constantPool.poolContent;
      this.headerOffset = this.constantPool.currentOffset;
      int constantPoolCount = this.constantPool.currentIndex;
      this.header[this.constantPoolOffset++] = (byte)(constantPoolCount >> 8);
      this.header[this.constantPoolOffset] = (byte)constantPoolCount;
   }

   public void addDefaultAbstractMethods() {
      MethodBinding[] defaultAbstractMethods = this.referenceBinding.getDefaultAbstractMethods();
      int i = 0;

      for(int max = defaultAbstractMethods.length; i < max; ++i) {
         MethodBinding methodBinding = defaultAbstractMethods[i];
         this.generateMethodInfoHeader(methodBinding);
         int methodAttributeOffset = this.contentsOffset;
         int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
         this.completeMethodInfo(methodBinding, methodAttributeOffset, attributeNumber);
      }

   }

   private int addFieldAttributes(FieldBinding fieldBinding, int fieldAttributeOffset) {
      int attributesNumber = 0;
      Constant fieldConstant = fieldBinding.constant();
      if (fieldConstant != Constant.NotAConstant) {
         attributesNumber += this.generateConstantValueAttribute(fieldConstant, fieldBinding, fieldAttributeOffset);
      }

      if (this.targetJDK < 3211264L && fieldBinding.isSynthetic()) {
         attributesNumber += this.generateSyntheticAttribute();
      }

      if (fieldBinding.isDeprecated()) {
         attributesNumber += this.generateDeprecatedAttribute();
      }

      char[] genericSignature = fieldBinding.genericSignature();
      if (genericSignature != null) {
         attributesNumber += this.generateSignatureAttribute(genericSignature);
      }

      if (this.targetJDK >= 3145728L) {
         FieldDeclaration fieldDeclaration = fieldBinding.sourceField();
         if (fieldDeclaration != null) {
            Annotation[] annotations = fieldDeclaration.annotations;
            if (annotations != null) {
               attributesNumber += this.generateRuntimeAnnotations(annotations, 137438953472L);
            }

            if ((this.produceAttributes & 32) != 0) {
               List allTypeAnnotationContexts = new ArrayList();
               if (annotations != null && (fieldDeclaration.bits & 1048576) != 0) {
                  fieldDeclaration.getAllAnnotationContexts(19, allTypeAnnotationContexts);
               }

               int invisibleTypeAnnotationsCounter = 0;
               int visibleTypeAnnotationsCounter = 0;
               TypeReference fieldType = fieldDeclaration.type;
               if (fieldType != null && (fieldType.bits & 1048576) != 0) {
                  fieldType.getAllAnnotationContexts(19, allTypeAnnotationContexts);
               }

               int size = allTypeAnnotationContexts.size();
               if (size != 0) {
                  AnnotationContext[] allTypeAnnotationContextsArray = new AnnotationContext[size];
                  allTypeAnnotationContexts.toArray(allTypeAnnotationContextsArray);
                  int i = 0;

                  for(int max = allTypeAnnotationContextsArray.length; i < max; ++i) {
                     AnnotationContext annotationContext = allTypeAnnotationContextsArray[i];
                     if ((annotationContext.visibility & 2) != 0) {
                        ++invisibleTypeAnnotationsCounter;
                        allTypeAnnotationContexts.add(annotationContext);
                     } else {
                        ++visibleTypeAnnotationsCounter;
                        allTypeAnnotationContexts.add(annotationContext);
                     }
                  }

                  attributesNumber += this.generateRuntimeTypeAnnotations(allTypeAnnotationContextsArray, visibleTypeAnnotationsCounter, invisibleTypeAnnotationsCounter);
               }
            }
         }
      }

      if ((fieldBinding.tagBits & 128L) != 0L) {
         this.missingTypes = fieldBinding.type.collectMissingTypes(this.missingTypes);
      }

      return attributesNumber;
   }

   private void addFieldInfo(FieldBinding fieldBinding) {
      if (this.contentsOffset + 8 >= this.contents.length) {
         this.resizeContents(8);
      }

      int accessFlags = fieldBinding.getAccessFlags();
      if (this.targetJDK < 3211264L) {
         accessFlags &= -4097;
      }

      this.contents[this.contentsOffset++] = (byte)(accessFlags >> 8);
      this.contents[this.contentsOffset++] = (byte)accessFlags;
      int nameIndex = this.constantPool.literalIndex(fieldBinding.name);
      this.contents[this.contentsOffset++] = (byte)(nameIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)nameIndex;
      int descriptorIndex = this.constantPool.literalIndex(fieldBinding.type);
      this.contents[this.contentsOffset++] = (byte)(descriptorIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)descriptorIndex;
      int fieldAttributeOffset = this.contentsOffset;
      int attributeNumber = 0;
      this.contentsOffset += 2;
      attributeNumber += this.addFieldAttributes(fieldBinding, fieldAttributeOffset);
      if (this.contentsOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contents[fieldAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[fieldAttributeOffset] = (byte)attributeNumber;
   }

   public void addFieldInfos() {
      SourceTypeBinding currentBinding = this.referenceBinding;
      FieldBinding[] syntheticFields = currentBinding.syntheticFields();
      int fieldCount = currentBinding.fieldCount() + (syntheticFields == null ? 0 : syntheticFields.length);
      if (fieldCount > 65535) {
         this.referenceBinding.scope.problemReporter().tooManyFields(this.referenceBinding.scope.referenceType());
      }

      this.contents[this.contentsOffset++] = (byte)(fieldCount >> 8);
      this.contents[this.contentsOffset++] = (byte)fieldCount;
      FieldDeclaration[] fieldDecls = currentBinding.scope.referenceContext.fields;
      int i = 0;

      int max;
      for(max = fieldDecls == null ? 0 : fieldDecls.length; i < max; ++i) {
         FieldDeclaration fieldDecl = fieldDecls[i];
         if (fieldDecl.binding != null) {
            this.addFieldInfo(fieldDecl.binding);
         }
      }

      if (syntheticFields != null) {
         i = 0;

         for(max = syntheticFields.length; i < max; ++i) {
            this.addFieldInfo(syntheticFields[i]);
         }
      }

   }

   private void addMissingAbstractProblemMethod(MethodDeclaration methodDeclaration, MethodBinding methodBinding, CategorizedProblem problem, CompilationResult compilationResult) {
      this.generateMethodInfoHeader(methodBinding, methodBinding.modifiers & -3329);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      ++attributeNumber;
      int codeAttributeOffset = this.contentsOffset;
      this.generateCodeAttributeHeader();
      StringBuffer buffer = new StringBuffer(25);
      buffer.append("\t" + problem.getMessage() + "\n");
      buffer.insert(0, Messages.compilation_unresolvedProblem);
      String problemString = buffer.toString();
      this.codeStream.init(this);
      this.codeStream.preserveUnusedLocals = true;
      this.codeStream.initializeMaxLocals(methodBinding);
      this.codeStream.generateCodeAttributeForProblemMethod(problemString);
      this.completeCodeAttributeForMissingAbstractProblemMethod(methodBinding, codeAttributeOffset, compilationResult.getLineSeparatorPositions(), problem.getSourceLineNumber());
      this.completeMethodInfo(methodBinding, methodAttributeOffset, attributeNumber);
   }

   public void addProblemClinit(CategorizedProblem[] problems) {
      this.generateMethodInfoHeaderForClinit();
      this.contentsOffset -= 2;
      int attributeOffset = this.contentsOffset;
      this.contentsOffset += 2;
      int attributeNumber = 0;
      int codeAttributeOffset = this.contentsOffset;
      this.generateCodeAttributeHeader();
      this.codeStream.resetForProblemClinit(this);
      String problemString = "";
      int problemLine = 0;
      if (problems != null) {
         int max = problems.length;
         StringBuffer buffer = new StringBuffer(25);
         int count = 0;

         for(int i = 0; i < max; ++i) {
            CategorizedProblem problem = problems[i];
            if (problem != null && problem.isError()) {
               buffer.append("\t" + problem.getMessage() + "\n");
               ++count;
               if (problemLine == 0) {
                  problemLine = problem.getSourceLineNumber();
               }

               problems[i] = null;
            }
         }

         if (count > 1) {
            buffer.insert(0, Messages.compilation_unresolvedProblems);
         } else {
            buffer.insert(0, Messages.compilation_unresolvedProblem);
         }

         problemString = buffer.toString();
      }

      this.codeStream.generateCodeAttributeForProblemMethod(problemString);
      ++attributeNumber;
      this.completeCodeAttributeForClinit(codeAttributeOffset, problemLine);
      if (this.contentsOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contents[attributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[attributeOffset] = (byte)attributeNumber;
   }

   public void addProblemConstructor(AbstractMethodDeclaration method, MethodBinding methodBinding, CategorizedProblem[] problems) {
      if (methodBinding.declaringClass.isInterface()) {
         method.abort(8, (CategorizedProblem)null);
      }

      this.generateMethodInfoHeader(methodBinding, methodBinding.modifiers & -3329);
      int methodAttributeOffset = this.contentsOffset;
      int attributesNumber = this.generateMethodInfoAttributes(methodBinding);
      ++attributesNumber;
      int codeAttributeOffset = this.contentsOffset;
      this.generateCodeAttributeHeader();
      this.codeStream.reset(method, this);
      String problemString = "";
      int problemLine = 0;
      if (problems != null) {
         int max = problems.length;
         StringBuffer buffer = new StringBuffer(25);
         int count = 0;

         for(int i = 0; i < max; ++i) {
            CategorizedProblem problem = problems[i];
            if (problem != null && problem.isError()) {
               buffer.append("\t" + problem.getMessage() + "\n");
               ++count;
               if (problemLine == 0) {
                  problemLine = problem.getSourceLineNumber();
               }
            }
         }

         if (count > 1) {
            buffer.insert(0, Messages.compilation_unresolvedProblems);
         } else {
            buffer.insert(0, Messages.compilation_unresolvedProblem);
         }

         problemString = buffer.toString();
      }

      this.codeStream.generateCodeAttributeForProblemMethod(problemString);
      this.completeCodeAttributeForProblemMethod(method, methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions(), problemLine);
      this.completeMethodInfo(methodBinding, methodAttributeOffset, attributesNumber);
   }

   public void addProblemConstructor(AbstractMethodDeclaration method, MethodBinding methodBinding, CategorizedProblem[] problems, int savedOffset) {
      this.contentsOffset = savedOffset;
      --this.methodCount;
      this.addProblemConstructor(method, methodBinding, problems);
   }

   public void addProblemMethod(AbstractMethodDeclaration method, MethodBinding methodBinding, CategorizedProblem[] problems) {
      if (methodBinding.isAbstract() && methodBinding.declaringClass.isInterface()) {
         method.abort(8, (CategorizedProblem)null);
      }

      this.generateMethodInfoHeader(methodBinding, methodBinding.modifiers & -3329);
      int methodAttributeOffset = this.contentsOffset;
      int attributesNumber = this.generateMethodInfoAttributes(methodBinding);
      ++attributesNumber;
      int codeAttributeOffset = this.contentsOffset;
      this.generateCodeAttributeHeader();
      this.codeStream.reset(method, this);
      String problemString = "";
      int problemLine = 0;
      if (problems != null) {
         int max = problems.length;
         StringBuffer buffer = new StringBuffer(25);
         int count = 0;

         for(int i = 0; i < max; ++i) {
            CategorizedProblem problem = problems[i];
            if (problem != null && problem.isError() && problem.getSourceStart() >= method.declarationSourceStart && problem.getSourceEnd() <= method.declarationSourceEnd) {
               buffer.append("\t" + problem.getMessage() + "\n");
               ++count;
               if (problemLine == 0) {
                  problemLine = problem.getSourceLineNumber();
               }

               problems[i] = null;
            }
         }

         if (count > 1) {
            buffer.insert(0, Messages.compilation_unresolvedProblems);
         } else {
            buffer.insert(0, Messages.compilation_unresolvedProblem);
         }

         problemString = buffer.toString();
      }

      this.codeStream.generateCodeAttributeForProblemMethod(problemString);
      this.completeCodeAttributeForProblemMethod(method, methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions(), problemLine);
      this.completeMethodInfo(methodBinding, methodAttributeOffset, attributesNumber);
   }

   public void addProblemMethod(AbstractMethodDeclaration method, MethodBinding methodBinding, CategorizedProblem[] problems, int savedOffset) {
      this.contentsOffset = savedOffset;
      --this.methodCount;
      this.addProblemMethod(method, methodBinding, problems);
   }

   public void addSpecialMethods() {
      this.generateMissingAbstractMethods(this.referenceBinding.scope.referenceType().missingAbstractMethods, this.referenceBinding.scope.referenceCompilationUnit().compilationResult);
      MethodBinding[] defaultAbstractMethods = this.referenceBinding.getDefaultAbstractMethods();
      int emittedSyntheticsCount = 0;

      int problemResetPC;
      int currentSyntheticsCount;
      for(int max = defaultAbstractMethods.length; emittedSyntheticsCount < max; ++emittedSyntheticsCount) {
         MethodBinding methodBinding = defaultAbstractMethods[emittedSyntheticsCount];
         this.generateMethodInfoHeader(methodBinding);
         problemResetPC = this.contentsOffset;
         currentSyntheticsCount = this.generateMethodInfoAttributes(methodBinding);
         this.completeMethodInfo(methodBinding, problemResetPC, currentSyntheticsCount);
      }

      emittedSyntheticsCount = 0;
      SyntheticMethodBinding deserializeLambdaMethod = null;
      boolean continueScanningSynthetics = true;

      while(continueScanningSynthetics) {
         continueScanningSynthetics = false;
         SyntheticMethodBinding[] syntheticMethods = this.referenceBinding.syntheticMethods();
         currentSyntheticsCount = syntheticMethods == null ? 0 : syntheticMethods.length;
         if (emittedSyntheticsCount != currentSyntheticsCount) {
            int i = emittedSyntheticsCount;

            for(int max = currentSyntheticsCount; i < max; ++i) {
               SyntheticMethodBinding syntheticMethod = syntheticMethods[i];
               switch (syntheticMethod.purpose) {
                  case 1:
                  case 3:
                     this.addSyntheticFieldReadAccessMethod(syntheticMethod);
                     break;
                  case 2:
                  case 4:
                     this.addSyntheticFieldWriteAccessMethod(syntheticMethod);
                     break;
                  case 5:
                  case 7:
                  case 8:
                     this.addSyntheticMethodAccessMethod(syntheticMethod);
                     break;
                  case 6:
                     this.addSyntheticConstructorAccessMethod(syntheticMethod);
                     break;
                  case 9:
                     this.addSyntheticEnumValuesMethod(syntheticMethod);
                     break;
                  case 10:
                     this.addSyntheticEnumValueOfMethod(syntheticMethod);
                     break;
                  case 11:
                     this.addSyntheticSwitchTable(syntheticMethod);
                     break;
                  case 12:
                     this.addSyntheticEnumInitializationMethod(syntheticMethod);
                     break;
                  case 13:
                     syntheticMethod.lambda.generateCode(this.referenceBinding.scope, this);
                     continueScanningSynthetics = true;
                     break;
                  case 14:
                     this.addSyntheticArrayConstructor(syntheticMethod);
                     break;
                  case 15:
                     this.addSyntheticArrayClone(syntheticMethod);
                     break;
                  case 16:
                     this.addSyntheticFactoryMethod(syntheticMethod);
                     break;
                  case 17:
                     deserializeLambdaMethod = syntheticMethod;
                  case 18:
               }
            }

            emittedSyntheticsCount = currentSyntheticsCount;
         }
      }

      if (deserializeLambdaMethod != null) {
         problemResetPC = 0;
         this.codeStream.wideMode = false;
         boolean restart = false;

         do {
            try {
               problemResetPC = this.contentsOffset;
               this.addSyntheticDeserializeLambda(deserializeLambdaMethod, this.referenceBinding.syntheticMethods());
               restart = false;
            } catch (AbortMethod var10) {
               if (var10.compilationResult != CodeStream.RESTART_IN_WIDE_MODE) {
                  throw new AbortType(this.referenceBinding.scope.referenceContext.compilationResult, var10.problem);
               }

               this.contentsOffset = problemResetPC;
               --this.methodCount;
               this.codeStream.resetInWideMode();
               restart = true;
            }
         } while(restart);
      }

   }

   public void addSyntheticArrayConstructor(SyntheticMethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForArrayConstructor(methodBinding);
      this.completeCodeAttributeForSyntheticMethod(methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void addSyntheticArrayClone(SyntheticMethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForArrayClone(methodBinding);
      this.completeCodeAttributeForSyntheticMethod(methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void addSyntheticFactoryMethod(SyntheticMethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForFactoryMethod(methodBinding);
      this.completeCodeAttributeForSyntheticMethod(methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void addSyntheticConstructorAccessMethod(SyntheticMethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForConstructorAccess(methodBinding);
      this.completeCodeAttributeForSyntheticMethod(methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void addSyntheticEnumValueOfMethod(SyntheticMethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForEnumValueOf(methodBinding);
      this.completeCodeAttributeForSyntheticMethod(methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      if ((this.produceAttributes & 64) != 0) {
         attributeNumber += this.generateMethodParameters(methodBinding);
      }

      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void addSyntheticEnumValuesMethod(SyntheticMethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForEnumValues(methodBinding);
      this.completeCodeAttributeForSyntheticMethod(methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void addSyntheticEnumInitializationMethod(SyntheticMethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForEnumInitializationMethod(methodBinding);
      this.completeCodeAttributeForSyntheticMethod(methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void addSyntheticFieldReadAccessMethod(SyntheticMethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForFieldReadAccess(methodBinding);
      this.completeCodeAttributeForSyntheticMethod(methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void addSyntheticFieldWriteAccessMethod(SyntheticMethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForFieldWriteAccess(methodBinding);
      this.completeCodeAttributeForSyntheticMethod(methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void addSyntheticMethodAccessMethod(SyntheticMethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForMethodAccess(methodBinding);
      this.completeCodeAttributeForSyntheticMethod(methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void addSyntheticSwitchTable(SyntheticMethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForSwitchTable(methodBinding);
      int code_length = this.codeStream.position;
      if (code_length > 65535) {
         SwitchStatement switchStatement = methodBinding.switchStatement;
         if (switchStatement != null) {
            switchStatement.scope.problemReporter().bytecodeExceeds64KLimit(switchStatement);
         }
      }

      this.completeCodeAttributeForSyntheticMethod(true, methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void completeCodeAttribute(int codeAttributeOffset) {
      this.contents = this.codeStream.bCodeStream;
      int localContentsOffset = this.codeStream.classFileOffset;
      int code_length = this.codeStream.position;
      if (code_length > 65535) {
         if (this.codeStream.methodDeclaration != null) {
            this.codeStream.methodDeclaration.scope.problemReporter().bytecodeExceeds64KLimit(this.codeStream.methodDeclaration);
         } else {
            this.codeStream.lambdaExpression.scope.problemReporter().bytecodeExceeds64KLimit(this.codeStream.lambdaExpression);
         }
      }

      if (localContentsOffset + 20 >= this.contents.length) {
         this.resizeContents(20);
      }

      int max_stack = this.codeStream.stackMax;
      this.contents[codeAttributeOffset + 6] = (byte)(max_stack >> 8);
      this.contents[codeAttributeOffset + 7] = (byte)max_stack;
      int max_locals = this.codeStream.maxLocals;
      this.contents[codeAttributeOffset + 8] = (byte)(max_locals >> 8);
      this.contents[codeAttributeOffset + 9] = (byte)max_locals;
      this.contents[codeAttributeOffset + 10] = (byte)(code_length >> 24);
      this.contents[codeAttributeOffset + 11] = (byte)(code_length >> 16);
      this.contents[codeAttributeOffset + 12] = (byte)(code_length >> 8);
      this.contents[codeAttributeOffset + 13] = (byte)code_length;
      boolean addStackMaps = (this.produceAttributes & 8) != 0;
      ExceptionLabel[] exceptionLabels = this.codeStream.exceptionLabels;
      int exceptionHandlersCount = 0;
      int exSize = 0;

      int codeAttributeAttributeOffset;
      for(codeAttributeAttributeOffset = this.codeStream.exceptionLabelsCounter; exSize < codeAttributeAttributeOffset; ++exSize) {
         exceptionHandlersCount += this.codeStream.exceptionLabels[exSize].getCount() / 2;
      }

      exSize = exceptionHandlersCount * 8 + 2;
      if (exSize + localContentsOffset >= this.contents.length) {
         this.resizeContents(exSize);
      }

      this.contents[localContentsOffset++] = (byte)(exceptionHandlersCount >> 8);
      this.contents[localContentsOffset++] = (byte)exceptionHandlersCount;
      codeAttributeAttributeOffset = 0;

      int attributesNumber;
      for(attributesNumber = this.codeStream.exceptionLabelsCounter; codeAttributeAttributeOffset < attributesNumber; ++codeAttributeAttributeOffset) {
         ExceptionLabel exceptionLabel = exceptionLabels[codeAttributeAttributeOffset];
         if (exceptionLabel != null) {
            int iRange = 0;
            int maxRange = exceptionLabel.getCount();
            if ((maxRange & 1) != 0) {
               if (this.codeStream.methodDeclaration != null) {
                  this.codeStream.methodDeclaration.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidExceptionAttribute, (Object)(new String(this.codeStream.methodDeclaration.selector))), this.codeStream.methodDeclaration);
               } else {
                  this.codeStream.lambdaExpression.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidExceptionAttribute, (Object)(new String(this.codeStream.lambdaExpression.binding.selector))), this.codeStream.lambdaExpression);
               }
            }

            while(iRange < maxRange) {
               int start = exceptionLabel.ranges[iRange++];
               this.contents[localContentsOffset++] = (byte)(start >> 8);
               this.contents[localContentsOffset++] = (byte)start;
               int end = exceptionLabel.ranges[iRange++];
               this.contents[localContentsOffset++] = (byte)(end >> 8);
               this.contents[localContentsOffset++] = (byte)end;
               int handlerPC = exceptionLabel.position;
               if (addStackMaps) {
                  StackMapFrameCodeStream stackMapFrameCodeStream = (StackMapFrameCodeStream)this.codeStream;
                  stackMapFrameCodeStream.addFramePosition(handlerPC);
               }

               this.contents[localContentsOffset++] = (byte)(handlerPC >> 8);
               this.contents[localContentsOffset++] = (byte)handlerPC;
               if (exceptionLabel.exceptionType == null) {
                  this.contents[localContentsOffset++] = 0;
                  this.contents[localContentsOffset++] = 0;
               } else {
                  int nameIndex;
                  if (exceptionLabel.exceptionType == TypeBinding.NULL) {
                     nameIndex = this.constantPool.literalIndexForType(ConstantPool.JavaLangClassNotFoundExceptionConstantPoolName);
                  } else {
                     nameIndex = this.constantPool.literalIndexForType(exceptionLabel.exceptionType);
                  }

                  this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
                  this.contents[localContentsOffset++] = (byte)nameIndex;
               }
            }
         }
      }

      codeAttributeAttributeOffset = localContentsOffset;
      attributesNumber = 0;
      localContentsOffset += 2;
      if (localContentsOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contentsOffset = localContentsOffset;
      if ((this.produceAttributes & 2) != 0) {
         attributesNumber += this.generateLineNumberAttribute();
      }

      if ((this.produceAttributes & 4) != 0) {
         boolean methodDeclarationIsStatic = this.codeStream.methodDeclaration != null ? this.codeStream.methodDeclaration.isStatic() : this.codeStream.lambdaExpression.binding.isStatic();
         attributesNumber += this.generateLocalVariableTableAttribute(code_length, methodDeclarationIsStatic, false);
      }

      if (addStackMaps) {
         attributesNumber += this.generateStackMapTableAttribute(this.codeStream.methodDeclaration != null ? this.codeStream.methodDeclaration.binding : this.codeStream.lambdaExpression.binding, code_length, codeAttributeOffset, max_locals, false);
      }

      if ((this.produceAttributes & 16) != 0) {
         attributesNumber += this.generateStackMapAttribute(this.codeStream.methodDeclaration != null ? this.codeStream.methodDeclaration.binding : this.codeStream.lambdaExpression.binding, code_length, codeAttributeOffset, max_locals, false);
      }

      if ((this.produceAttributes & 32) != 0) {
         attributesNumber += this.generateTypeAnnotationsOnCodeAttribute();
      }

      this.contents[codeAttributeAttributeOffset++] = (byte)(attributesNumber >> 8);
      this.contents[codeAttributeAttributeOffset] = (byte)attributesNumber;
      int codeAttributeLength = this.contentsOffset - (codeAttributeOffset + 6);
      this.contents[codeAttributeOffset + 2] = (byte)(codeAttributeLength >> 24);
      this.contents[codeAttributeOffset + 3] = (byte)(codeAttributeLength >> 16);
      this.contents[codeAttributeOffset + 4] = (byte)(codeAttributeLength >> 8);
      this.contents[codeAttributeOffset + 5] = (byte)codeAttributeLength;
   }

   public int generateTypeAnnotationsOnCodeAttribute() {
      int attributesNumber = 0;
      List allTypeAnnotationContexts = ((TypeAnnotationCodeStream)this.codeStream).allTypeAnnotationContexts;
      int invisibleTypeAnnotationsCounter = 0;
      int visibleTypeAnnotationsCounter = 0;
      int i = 0;

      int size;
      int max2;
      for(size = this.codeStream.allLocalsCounter; i < size; ++i) {
         LocalVariableBinding localVariable = this.codeStream.locals[i];
         if (!localVariable.isCatchParameter()) {
            LocalDeclaration declaration = localVariable.declaration;
            if (declaration != null && (!declaration.isArgument() || (declaration.bits & 536870912) != 0) && localVariable.initializationCount != 0 && (declaration.bits & 1048576) != 0) {
               max2 = (localVariable.tagBits & 8192L) == 0L ? 64 : 65;
               declaration.getAllAnnotationContexts(max2, localVariable, allTypeAnnotationContexts);
            }
         }
      }

      ExceptionLabel[] exceptionLabels = this.codeStream.exceptionLabels;
      size = 0;

      for(int max = this.codeStream.exceptionLabelsCounter; size < max; ++size) {
         ExceptionLabel exceptionLabel = exceptionLabels[size];
         if (exceptionLabel.exceptionTypeReference != null && (exceptionLabel.exceptionTypeReference.bits & 1048576) != 0) {
            exceptionLabel.exceptionTypeReference.getAllAnnotationContexts(66, size, allTypeAnnotationContexts, exceptionLabel.se7Annotations);
         }
      }

      size = allTypeAnnotationContexts.size();
      if (size != 0) {
         AnnotationContext[] allTypeAnnotationContextsArray = new AnnotationContext[size];
         allTypeAnnotationContexts.toArray(allTypeAnnotationContextsArray);
         int j = 0;

         for(max2 = allTypeAnnotationContextsArray.length; j < max2; ++j) {
            AnnotationContext annotationContext = allTypeAnnotationContextsArray[j];
            if ((annotationContext.visibility & 2) != 0) {
               ++invisibleTypeAnnotationsCounter;
            } else {
               ++visibleTypeAnnotationsCounter;
            }
         }

         attributesNumber += this.generateRuntimeTypeAnnotations(allTypeAnnotationContextsArray, visibleTypeAnnotationsCounter, invisibleTypeAnnotationsCounter);
      }

      return attributesNumber;
   }

   public void completeCodeAttributeForClinit(int codeAttributeOffset) {
      this.contents = this.codeStream.bCodeStream;
      int localContentsOffset = this.codeStream.classFileOffset;
      int code_length = this.codeStream.position;
      if (code_length > 65535) {
         this.codeStream.methodDeclaration.scope.problemReporter().bytecodeExceeds64KLimit(this.codeStream.methodDeclaration.scope.referenceType());
      }

      if (localContentsOffset + 20 >= this.contents.length) {
         this.resizeContents(20);
      }

      int max_stack = this.codeStream.stackMax;
      this.contents[codeAttributeOffset + 6] = (byte)(max_stack >> 8);
      this.contents[codeAttributeOffset + 7] = (byte)max_stack;
      int max_locals = this.codeStream.maxLocals;
      this.contents[codeAttributeOffset + 8] = (byte)(max_locals >> 8);
      this.contents[codeAttributeOffset + 9] = (byte)max_locals;
      this.contents[codeAttributeOffset + 10] = (byte)(code_length >> 24);
      this.contents[codeAttributeOffset + 11] = (byte)(code_length >> 16);
      this.contents[codeAttributeOffset + 12] = (byte)(code_length >> 8);
      this.contents[codeAttributeOffset + 13] = (byte)code_length;
      boolean addStackMaps = (this.produceAttributes & 8) != 0;
      ExceptionLabel[] exceptionLabels = this.codeStream.exceptionLabels;
      int exceptionHandlersCount = 0;
      int exSize = 0;

      int codeAttributeAttributeOffset;
      for(codeAttributeAttributeOffset = this.codeStream.exceptionLabelsCounter; exSize < codeAttributeAttributeOffset; ++exSize) {
         exceptionHandlersCount += this.codeStream.exceptionLabels[exSize].getCount() / 2;
      }

      exSize = exceptionHandlersCount * 8 + 2;
      if (exSize + localContentsOffset >= this.contents.length) {
         this.resizeContents(exSize);
      }

      this.contents[localContentsOffset++] = (byte)(exceptionHandlersCount >> 8);
      this.contents[localContentsOffset++] = (byte)exceptionHandlersCount;
      codeAttributeAttributeOffset = 0;

      int attributesNumber;
      for(attributesNumber = this.codeStream.exceptionLabelsCounter; codeAttributeAttributeOffset < attributesNumber; ++codeAttributeAttributeOffset) {
         ExceptionLabel exceptionLabel = exceptionLabels[codeAttributeAttributeOffset];
         if (exceptionLabel != null) {
            int iRange = 0;
            int maxRange = exceptionLabel.getCount();
            if ((maxRange & 1) != 0) {
               this.codeStream.methodDeclaration.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidExceptionAttribute, (Object)(new String(this.codeStream.methodDeclaration.selector))), this.codeStream.methodDeclaration);
            }

            while(iRange < maxRange) {
               int start = exceptionLabel.ranges[iRange++];
               this.contents[localContentsOffset++] = (byte)(start >> 8);
               this.contents[localContentsOffset++] = (byte)start;
               int end = exceptionLabel.ranges[iRange++];
               this.contents[localContentsOffset++] = (byte)(end >> 8);
               this.contents[localContentsOffset++] = (byte)end;
               int handlerPC = exceptionLabel.position;
               this.contents[localContentsOffset++] = (byte)(handlerPC >> 8);
               this.contents[localContentsOffset++] = (byte)handlerPC;
               if (addStackMaps) {
                  StackMapFrameCodeStream stackMapFrameCodeStream = (StackMapFrameCodeStream)this.codeStream;
                  stackMapFrameCodeStream.addFramePosition(handlerPC);
               }

               if (exceptionLabel.exceptionType == null) {
                  this.contents[localContentsOffset++] = 0;
                  this.contents[localContentsOffset++] = 0;
               } else {
                  int nameIndex;
                  if (exceptionLabel.exceptionType == TypeBinding.NULL) {
                     nameIndex = this.constantPool.literalIndexForType(ConstantPool.JavaLangClassNotFoundExceptionConstantPoolName);
                  } else {
                     nameIndex = this.constantPool.literalIndexForType(exceptionLabel.exceptionType);
                  }

                  this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
                  this.contents[localContentsOffset++] = (byte)nameIndex;
               }
            }
         }
      }

      codeAttributeAttributeOffset = localContentsOffset;
      attributesNumber = 0;
      localContentsOffset += 2;
      if (localContentsOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contentsOffset = localContentsOffset;
      if ((this.produceAttributes & 2) != 0) {
         attributesNumber += this.generateLineNumberAttribute();
      }

      if ((this.produceAttributes & 4) != 0) {
         attributesNumber += this.generateLocalVariableTableAttribute(code_length, true, false);
      }

      if ((this.produceAttributes & 8) != 0) {
         attributesNumber += this.generateStackMapTableAttribute((MethodBinding)null, code_length, codeAttributeOffset, max_locals, true);
      }

      if ((this.produceAttributes & 16) != 0) {
         attributesNumber += this.generateStackMapAttribute((MethodBinding)null, code_length, codeAttributeOffset, max_locals, true);
      }

      if ((this.produceAttributes & 32) != 0) {
         attributesNumber += this.generateTypeAnnotationsOnCodeAttribute();
      }

      if (codeAttributeAttributeOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contents[codeAttributeAttributeOffset++] = (byte)(attributesNumber >> 8);
      this.contents[codeAttributeAttributeOffset] = (byte)attributesNumber;
      int codeAttributeLength = this.contentsOffset - (codeAttributeOffset + 6);
      this.contents[codeAttributeOffset + 2] = (byte)(codeAttributeLength >> 24);
      this.contents[codeAttributeOffset + 3] = (byte)(codeAttributeLength >> 16);
      this.contents[codeAttributeOffset + 4] = (byte)(codeAttributeLength >> 8);
      this.contents[codeAttributeOffset + 5] = (byte)codeAttributeLength;
   }

   public void completeCodeAttributeForClinit(int codeAttributeOffset, int problemLine) {
      this.contents = this.codeStream.bCodeStream;
      int localContentsOffset = this.codeStream.classFileOffset;
      int code_length = this.codeStream.position;
      if (code_length > 65535) {
         this.codeStream.methodDeclaration.scope.problemReporter().bytecodeExceeds64KLimit(this.codeStream.methodDeclaration.scope.referenceType());
      }

      if (localContentsOffset + 20 >= this.contents.length) {
         this.resizeContents(20);
      }

      int max_stack = this.codeStream.stackMax;
      this.contents[codeAttributeOffset + 6] = (byte)(max_stack >> 8);
      this.contents[codeAttributeOffset + 7] = (byte)max_stack;
      int max_locals = this.codeStream.maxLocals;
      this.contents[codeAttributeOffset + 8] = (byte)(max_locals >> 8);
      this.contents[codeAttributeOffset + 9] = (byte)max_locals;
      this.contents[codeAttributeOffset + 10] = (byte)(code_length >> 24);
      this.contents[codeAttributeOffset + 11] = (byte)(code_length >> 16);
      this.contents[codeAttributeOffset + 12] = (byte)(code_length >> 8);
      this.contents[codeAttributeOffset + 13] = (byte)code_length;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      int codeAttributeAttributeOffset = localContentsOffset;
      int attributesNumber = 0;
      localContentsOffset += 2;
      if (localContentsOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contentsOffset = localContentsOffset;
      if ((this.produceAttributes & 2) != 0) {
         attributesNumber += this.generateLineNumberAttribute(problemLine);
      }

      localContentsOffset = this.contentsOffset;
      int codeAttributeLength;
      if ((this.produceAttributes & 4) != 0) {
         codeAttributeLength = this.constantPool.literalIndex(AttributeNamesConstants.LocalVariableTableName);
         if (localContentsOffset + 8 >= this.contents.length) {
            this.resizeContents(8);
         }

         this.contents[localContentsOffset++] = (byte)(codeAttributeLength >> 8);
         this.contents[localContentsOffset++] = (byte)codeAttributeLength;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 2;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 0;
         ++attributesNumber;
      }

      this.contentsOffset = localContentsOffset;
      if ((this.produceAttributes & 8) != 0) {
         attributesNumber += this.generateStackMapTableAttribute((MethodBinding)null, code_length, codeAttributeOffset, max_locals, true);
      }

      if ((this.produceAttributes & 16) != 0) {
         attributesNumber += this.generateStackMapAttribute((MethodBinding)null, code_length, codeAttributeOffset, max_locals, true);
      }

      if ((this.produceAttributes & 32) != 0) {
         attributesNumber += this.generateTypeAnnotationsOnCodeAttribute();
      }

      if (codeAttributeAttributeOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contents[codeAttributeAttributeOffset++] = (byte)(attributesNumber >> 8);
      this.contents[codeAttributeAttributeOffset] = (byte)attributesNumber;
      codeAttributeLength = this.contentsOffset - (codeAttributeOffset + 6);
      this.contents[codeAttributeOffset + 2] = (byte)(codeAttributeLength >> 24);
      this.contents[codeAttributeOffset + 3] = (byte)(codeAttributeLength >> 16);
      this.contents[codeAttributeOffset + 4] = (byte)(codeAttributeLength >> 8);
      this.contents[codeAttributeOffset + 5] = (byte)codeAttributeLength;
   }

   public void completeCodeAttributeForMissingAbstractProblemMethod(MethodBinding binding, int codeAttributeOffset, int[] startLineIndexes, int problemLine) {
      this.contents = this.codeStream.bCodeStream;
      int localContentsOffset = this.codeStream.classFileOffset;
      int max_stack = this.codeStream.stackMax;
      this.contents[codeAttributeOffset + 6] = (byte)(max_stack >> 8);
      this.contents[codeAttributeOffset + 7] = (byte)max_stack;
      int max_locals = this.codeStream.maxLocals;
      this.contents[codeAttributeOffset + 8] = (byte)(max_locals >> 8);
      this.contents[codeAttributeOffset + 9] = (byte)max_locals;
      int code_length = this.codeStream.position;
      this.contents[codeAttributeOffset + 10] = (byte)(code_length >> 24);
      this.contents[codeAttributeOffset + 11] = (byte)(code_length >> 16);
      this.contents[codeAttributeOffset + 12] = (byte)(code_length >> 8);
      this.contents[codeAttributeOffset + 13] = (byte)code_length;
      if (localContentsOffset + 50 >= this.contents.length) {
         this.resizeContents(50);
      }

      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      int codeAttributeAttributeOffset = localContentsOffset;
      int attributesNumber = 0;
      localContentsOffset += 2;
      if (localContentsOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contentsOffset = localContentsOffset;
      if ((this.produceAttributes & 2) != 0) {
         if (problemLine == 0) {
            problemLine = Util.getLineNumber(binding.sourceStart(), startLineIndexes, 0, startLineIndexes.length - 1);
         }

         attributesNumber += this.generateLineNumberAttribute(problemLine);
      }

      if ((this.produceAttributes & 8) != 0) {
         attributesNumber += this.generateStackMapTableAttribute(binding, code_length, codeAttributeOffset, max_locals, false);
      }

      if ((this.produceAttributes & 16) != 0) {
         attributesNumber += this.generateStackMapAttribute(binding, code_length, codeAttributeOffset, max_locals, false);
      }

      if (codeAttributeAttributeOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contents[codeAttributeAttributeOffset++] = (byte)(attributesNumber >> 8);
      this.contents[codeAttributeAttributeOffset] = (byte)attributesNumber;
      int codeAttributeLength = this.contentsOffset - (codeAttributeOffset + 6);
      this.contents[codeAttributeOffset + 2] = (byte)(codeAttributeLength >> 24);
      this.contents[codeAttributeOffset + 3] = (byte)(codeAttributeLength >> 16);
      this.contents[codeAttributeOffset + 4] = (byte)(codeAttributeLength >> 8);
      this.contents[codeAttributeOffset + 5] = (byte)codeAttributeLength;
   }

   public void completeCodeAttributeForProblemMethod(AbstractMethodDeclaration method, MethodBinding binding, int codeAttributeOffset, int[] startLineIndexes, int problemLine) {
      this.contents = this.codeStream.bCodeStream;
      int localContentsOffset = this.codeStream.classFileOffset;
      int max_stack = this.codeStream.stackMax;
      this.contents[codeAttributeOffset + 6] = (byte)(max_stack >> 8);
      this.contents[codeAttributeOffset + 7] = (byte)max_stack;
      int max_locals = this.codeStream.maxLocals;
      this.contents[codeAttributeOffset + 8] = (byte)(max_locals >> 8);
      this.contents[codeAttributeOffset + 9] = (byte)max_locals;
      int code_length = this.codeStream.position;
      this.contents[codeAttributeOffset + 10] = (byte)(code_length >> 24);
      this.contents[codeAttributeOffset + 11] = (byte)(code_length >> 16);
      this.contents[codeAttributeOffset + 12] = (byte)(code_length >> 8);
      this.contents[codeAttributeOffset + 13] = (byte)code_length;
      if (localContentsOffset + 50 >= this.contents.length) {
         this.resizeContents(50);
      }

      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      int codeAttributeAttributeOffset = localContentsOffset;
      int attributesNumber = 0;
      localContentsOffset += 2;
      if (localContentsOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contentsOffset = localContentsOffset;
      if ((this.produceAttributes & 2) != 0) {
         if (problemLine == 0) {
            problemLine = Util.getLineNumber(binding.sourceStart(), startLineIndexes, 0, startLineIndexes.length - 1);
         }

         attributesNumber += this.generateLineNumberAttribute(problemLine);
      }

      if ((this.produceAttributes & 4) != 0) {
         boolean methodDeclarationIsStatic = this.codeStream.methodDeclaration.isStatic();
         attributesNumber += this.generateLocalVariableTableAttribute(code_length, methodDeclarationIsStatic, false);
      }

      if ((this.produceAttributes & 8) != 0) {
         attributesNumber += this.generateStackMapTableAttribute(binding, code_length, codeAttributeOffset, max_locals, false);
      }

      if ((this.produceAttributes & 16) != 0) {
         attributesNumber += this.generateStackMapAttribute(binding, code_length, codeAttributeOffset, max_locals, false);
      }

      if (codeAttributeAttributeOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contents[codeAttributeAttributeOffset++] = (byte)(attributesNumber >> 8);
      this.contents[codeAttributeAttributeOffset] = (byte)attributesNumber;
      int codeAttributeLength = this.contentsOffset - (codeAttributeOffset + 6);
      this.contents[codeAttributeOffset + 2] = (byte)(codeAttributeLength >> 24);
      this.contents[codeAttributeOffset + 3] = (byte)(codeAttributeLength >> 16);
      this.contents[codeAttributeOffset + 4] = (byte)(codeAttributeLength >> 8);
      this.contents[codeAttributeOffset + 5] = (byte)codeAttributeLength;
   }

   public void completeCodeAttributeForSyntheticMethod(boolean hasExceptionHandlers, SyntheticMethodBinding binding, int codeAttributeOffset, int[] startLineIndexes) {
      this.contents = this.codeStream.bCodeStream;
      int localContentsOffset = this.codeStream.classFileOffset;
      int max_stack = this.codeStream.stackMax;
      this.contents[codeAttributeOffset + 6] = (byte)(max_stack >> 8);
      this.contents[codeAttributeOffset + 7] = (byte)max_stack;
      int max_locals = this.codeStream.maxLocals;
      this.contents[codeAttributeOffset + 8] = (byte)(max_locals >> 8);
      this.contents[codeAttributeOffset + 9] = (byte)max_locals;
      int code_length = this.codeStream.position;
      this.contents[codeAttributeOffset + 10] = (byte)(code_length >> 24);
      this.contents[codeAttributeOffset + 11] = (byte)(code_length >> 16);
      this.contents[codeAttributeOffset + 12] = (byte)(code_length >> 8);
      this.contents[codeAttributeOffset + 13] = (byte)code_length;
      if (localContentsOffset + 40 >= this.contents.length) {
         this.resizeContents(40);
      }

      boolean addStackMaps = (this.produceAttributes & 8) != 0;
      int attributesNumber;
      int codeAttributeLength;
      if (hasExceptionHandlers) {
         ExceptionLabel[] exceptionLabels = this.codeStream.exceptionLabels;
         attributesNumber = 0;
         codeAttributeLength = 0;

         int i;
         for(i = this.codeStream.exceptionLabelsCounter; codeAttributeLength < i; ++codeAttributeLength) {
            attributesNumber += this.codeStream.exceptionLabels[codeAttributeLength].getCount() / 2;
         }

         codeAttributeLength = attributesNumber * 8 + 2;
         if (codeAttributeLength + localContentsOffset >= this.contents.length) {
            this.resizeContents(codeAttributeLength);
         }

         this.contents[localContentsOffset++] = (byte)(attributesNumber >> 8);
         this.contents[localContentsOffset++] = (byte)attributesNumber;
         i = 0;

         for(int max = this.codeStream.exceptionLabelsCounter; i < max; ++i) {
            ExceptionLabel exceptionLabel = exceptionLabels[i];
            if (exceptionLabel != null) {
               int iRange = 0;
               int maxRange = exceptionLabel.getCount();
               if ((maxRange & 1) != 0) {
                  this.referenceBinding.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidExceptionAttribute, new String(binding.selector), this.referenceBinding.scope.problemReporter().referenceContext));
               }

               while(iRange < maxRange) {
                  int start = exceptionLabel.ranges[iRange++];
                  this.contents[localContentsOffset++] = (byte)(start >> 8);
                  this.contents[localContentsOffset++] = (byte)start;
                  int end = exceptionLabel.ranges[iRange++];
                  this.contents[localContentsOffset++] = (byte)(end >> 8);
                  this.contents[localContentsOffset++] = (byte)end;
                  int handlerPC = exceptionLabel.position;
                  if (addStackMaps) {
                     StackMapFrameCodeStream stackMapFrameCodeStream = (StackMapFrameCodeStream)this.codeStream;
                     stackMapFrameCodeStream.addFramePosition(handlerPC);
                  }

                  this.contents[localContentsOffset++] = (byte)(handlerPC >> 8);
                  this.contents[localContentsOffset++] = (byte)handlerPC;
                  if (exceptionLabel.exceptionType == null) {
                     this.contents[localContentsOffset++] = 0;
                     this.contents[localContentsOffset++] = 0;
                  } else {
                     int nameIndex;
                     switch (exceptionLabel.exceptionType.id) {
                        case 7:
                           nameIndex = this.constantPool.literalIndexForType(ConstantPool.JavaLangNoSuchFieldErrorConstantPoolName);
                           break;
                        case 12:
                           nameIndex = this.constantPool.literalIndexForType(ConstantPool.JavaLangClassNotFoundExceptionConstantPoolName);
                           break;
                        default:
                           nameIndex = this.constantPool.literalIndexForType(exceptionLabel.exceptionType);
                     }

                     this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
                     this.contents[localContentsOffset++] = (byte)nameIndex;
                  }
               }
            }
         }
      } else {
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 0;
      }

      int codeAttributeAttributeOffset = localContentsOffset;
      attributesNumber = 0;
      localContentsOffset += 2;
      if (localContentsOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contentsOffset = localContentsOffset;
      if ((this.produceAttributes & 2) != 0) {
         codeAttributeLength = Util.getLineNumber(binding.sourceStart, startLineIndexes, 0, startLineIndexes.length - 1);
         attributesNumber += this.generateLineNumberAttribute(codeAttributeLength);
      }

      if ((this.produceAttributes & 4) != 0) {
         boolean methodDeclarationIsStatic = binding.isStatic();
         attributesNumber += this.generateLocalVariableTableAttribute(code_length, methodDeclarationIsStatic, true);
      }

      if (addStackMaps) {
         attributesNumber += this.generateStackMapTableAttribute(binding, code_length, codeAttributeOffset, max_locals, false);
      }

      if ((this.produceAttributes & 16) != 0) {
         attributesNumber += this.generateStackMapAttribute(binding, code_length, codeAttributeOffset, max_locals, false);
      }

      if (codeAttributeAttributeOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      this.contents[codeAttributeAttributeOffset++] = (byte)(attributesNumber >> 8);
      this.contents[codeAttributeAttributeOffset] = (byte)attributesNumber;
      codeAttributeLength = this.contentsOffset - (codeAttributeOffset + 6);
      this.contents[codeAttributeOffset + 2] = (byte)(codeAttributeLength >> 24);
      this.contents[codeAttributeOffset + 3] = (byte)(codeAttributeLength >> 16);
      this.contents[codeAttributeOffset + 4] = (byte)(codeAttributeLength >> 8);
      this.contents[codeAttributeOffset + 5] = (byte)codeAttributeLength;
   }

   public void completeCodeAttributeForSyntheticMethod(SyntheticMethodBinding binding, int codeAttributeOffset, int[] startLineIndexes) {
      this.completeCodeAttributeForSyntheticMethod(false, binding, codeAttributeOffset, startLineIndexes);
   }

   private void completeArgumentAnnotationInfo(Argument[] arguments, List allAnnotationContexts) {
      int i = 0;

      for(int max = arguments.length; i < max; ++i) {
         Argument argument = arguments[i];
         if ((argument.bits & 1048576) != 0) {
            argument.getAllAnnotationContexts(22, i, allAnnotationContexts);
         }
      }

   }

   public void completeMethodInfo(MethodBinding binding, int methodAttributeOffset, int attributesNumber) {
      if ((this.produceAttributes & 32) != 0) {
         List allTypeAnnotationContexts = new ArrayList();
         int invisibleTypeAnnotationsCounter = 0;
         int visibleTypeAnnotationsCounter = 0;
         AbstractMethodDeclaration methodDeclaration = binding.sourceMethod();
         int i;
         int i;
         if (methodDeclaration != null) {
            if ((methodDeclaration.bits & 1048576) != 0) {
               Argument[] arguments = methodDeclaration.arguments;
               if (arguments != null) {
                  this.completeArgumentAnnotationInfo(arguments, allTypeAnnotationContexts);
               }

               Receiver receiver = methodDeclaration.receiver;
               if (receiver != null && (receiver.type.bits & 1048576) != 0) {
                  receiver.type.getAllAnnotationContexts(21, allTypeAnnotationContexts);
               }
            }

            Annotation[] annotations = methodDeclaration.annotations;
            if (annotations != null && !methodDeclaration.isClinit() && (methodDeclaration.isConstructor() || binding.returnType.id != 6)) {
               methodDeclaration.getAllAnnotationContexts(20, allTypeAnnotationContexts);
            }

            if (!methodDeclaration.isConstructor() && !methodDeclaration.isClinit() && binding.returnType.id != 6) {
               MethodDeclaration declaration = (MethodDeclaration)methodDeclaration;
               TypeReference typeReference = declaration.returnType;
               if ((typeReference.bits & 1048576) != 0) {
                  typeReference.getAllAnnotationContexts(20, allTypeAnnotationContexts);
               }
            }

            TypeReference[] thrownExceptions = methodDeclaration.thrownExceptions;
            if (thrownExceptions != null) {
               i = 0;

               for(i = thrownExceptions.length; i < i; ++i) {
                  TypeReference thrownException = thrownExceptions[i];
                  thrownException.getAllAnnotationContexts(23, i, allTypeAnnotationContexts);
               }
            }

            TypeParameter[] typeParameters = methodDeclaration.typeParameters();
            if (typeParameters != null) {
               i = 0;

               for(int max = typeParameters.length; i < max; ++i) {
                  TypeParameter typeParameter = typeParameters[i];
                  if ((typeParameter.bits & 1048576) != 0) {
                     typeParameter.getAllAnnotationContexts(1, i, allTypeAnnotationContexts);
                  }
               }
            }
         } else if (binding.sourceLambda() != null) {
            LambdaExpression lambda = binding.sourceLambda();
            if ((lambda.bits & 1048576) != 0 && lambda.arguments != null) {
               this.completeArgumentAnnotationInfo(lambda.arguments, allTypeAnnotationContexts);
            }
         }

         int size = allTypeAnnotationContexts.size();
         if (size != 0) {
            AnnotationContext[] allTypeAnnotationContextsArray = new AnnotationContext[size];
            allTypeAnnotationContexts.toArray(allTypeAnnotationContextsArray);
            i = 0;

            for(i = allTypeAnnotationContextsArray.length; i < i; ++i) {
               AnnotationContext annotationContext = allTypeAnnotationContextsArray[i];
               if ((annotationContext.visibility & 2) != 0) {
                  ++invisibleTypeAnnotationsCounter;
               } else {
                  ++visibleTypeAnnotationsCounter;
               }
            }

            attributesNumber += this.generateRuntimeTypeAnnotations(allTypeAnnotationContextsArray, visibleTypeAnnotationsCounter, invisibleTypeAnnotationsCounter);
         }
      }

      if ((this.produceAttributes & 64) != 0) {
         attributesNumber += this.generateMethodParameters(binding);
      }

      this.contents[methodAttributeOffset++] = (byte)(attributesNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributesNumber;
   }

   private void dumpLocations(int[] locations) {
      if (locations == null) {
         if (this.contentsOffset + 1 >= this.contents.length) {
            this.resizeContents(1);
         }

         this.contents[this.contentsOffset++] = 0;
      } else {
         int length = locations.length;
         if (this.contentsOffset + length >= this.contents.length) {
            this.resizeContents(length + 1);
         }

         this.contents[this.contentsOffset++] = (byte)(locations.length / 2);

         for(int i = 0; i < length; ++i) {
            this.contents[this.contentsOffset++] = (byte)locations[i];
         }
      }

   }

   private void dumpTargetTypeContents(int targetType, AnnotationContext annotationContext) {
      switch (targetType) {
         case 0:
         case 1:
            this.contents[this.contentsOffset++] = (byte)annotationContext.info;
            break;
         case 16:
         case 23:
            this.contents[this.contentsOffset++] = (byte)(annotationContext.info >> 8);
            this.contents[this.contentsOffset++] = (byte)annotationContext.info;
            break;
         case 17:
            this.contents[this.contentsOffset++] = (byte)annotationContext.info;
            this.contents[this.contentsOffset++] = (byte)annotationContext.info2;
            break;
         case 18:
            this.contents[this.contentsOffset++] = (byte)annotationContext.info;
            this.contents[this.contentsOffset++] = (byte)annotationContext.info2;
         case 19:
         case 20:
         case 21:
         default:
            break;
         case 22:
            this.contents[this.contentsOffset++] = (byte)annotationContext.info;
            break;
         case 64:
         case 65:
            int localVariableTableOffset = this.contentsOffset;
            LocalVariableBinding localVariable = annotationContext.variableBinding;
            int actualSize = 0;
            int initializationCount = localVariable.initializationCount;
            actualSize += 2 + 6 * initializationCount;
            if (this.contentsOffset + actualSize >= this.contents.length) {
               this.resizeContents(actualSize);
            }

            this.contentsOffset += 2;
            int numberOfEntries = 0;

            for(int j = 0; j < initializationCount; ++j) {
               int startPC = localVariable.initializationPCs[j << 1];
               int endPC = localVariable.initializationPCs[(j << 1) + 1];
               if (startPC != endPC) {
                  ++numberOfEntries;
                  this.contents[this.contentsOffset++] = (byte)(startPC >> 8);
                  this.contents[this.contentsOffset++] = (byte)startPC;
                  int length = endPC - startPC;
                  this.contents[this.contentsOffset++] = (byte)(length >> 8);
                  this.contents[this.contentsOffset++] = (byte)length;
                  int resolvedPosition = localVariable.resolvedPosition;
                  this.contents[this.contentsOffset++] = (byte)(resolvedPosition >> 8);
                  this.contents[this.contentsOffset++] = (byte)resolvedPosition;
               }
            }

            this.contents[localVariableTableOffset++] = (byte)(numberOfEntries >> 8);
            this.contents[localVariableTableOffset] = (byte)numberOfEntries;
            break;
         case 66:
         case 67:
         case 68:
         case 69:
         case 70:
            this.contents[this.contentsOffset++] = (byte)(annotationContext.info >> 8);
            this.contents[this.contentsOffset++] = (byte)annotationContext.info;
            break;
         case 71:
            this.contents[this.contentsOffset++] = (byte)(annotationContext.info >> 8);
            this.contents[this.contentsOffset++] = (byte)annotationContext.info;
            this.contents[this.contentsOffset++] = (byte)annotationContext.info2;
            break;
         case 72:
         case 73:
         case 74:
         case 75:
            this.contents[this.contentsOffset++] = (byte)(annotationContext.info >> 8);
            this.contents[this.contentsOffset++] = (byte)annotationContext.info;
            this.contents[this.contentsOffset++] = (byte)annotationContext.info2;
      }

   }

   public char[] fileName() {
      return this.constantPool.UTF8Cache.returnKeyFor(2);
   }

   private void generateAnnotation(Annotation annotation, int currentOffset) {
      int startingContentsOffset = currentOffset;
      if (this.contentsOffset + 4 >= this.contents.length) {
         this.resizeContents(4);
      }

      TypeBinding annotationTypeBinding = annotation.resolvedType;
      if (annotationTypeBinding == null) {
         this.contentsOffset = currentOffset;
      } else {
         if (annotationTypeBinding.isMemberType()) {
            this.recordInnerClasses(annotationTypeBinding);
         }

         int typeIndex = this.constantPool.literalIndex(annotationTypeBinding.signature());
         this.contents[this.contentsOffset++] = (byte)(typeIndex >> 8);
         this.contents[this.contentsOffset++] = (byte)typeIndex;
         int memberValuePairOffset;
         if (annotation instanceof NormalAnnotation) {
            NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;
            MemberValuePair[] memberValuePairs = normalAnnotation.memberValuePairs;
            int memberValuePairOffset = this.contentsOffset;
            if (memberValuePairs != null) {
               memberValuePairOffset = 0;
               int memberValuePairsLengthPosition = this.contentsOffset;
               this.contentsOffset += 2;
               int resetPosition = this.contentsOffset;
               int memberValuePairsLength = memberValuePairs.length;

               for(int i = 0; i < memberValuePairsLength; ++i) {
                  MemberValuePair memberValuePair = memberValuePairs[i];
                  if (this.contentsOffset + 2 >= this.contents.length) {
                     this.resizeContents(2);
                  }

                  int elementNameIndex = this.constantPool.literalIndex(memberValuePair.name);
                  this.contents[this.contentsOffset++] = (byte)(elementNameIndex >> 8);
                  this.contents[this.contentsOffset++] = (byte)elementNameIndex;
                  MethodBinding methodBinding = memberValuePair.binding;
                  if (methodBinding == null) {
                     this.contentsOffset = resetPosition;
                  } else {
                     try {
                        this.generateElementValue(memberValuePair.value, methodBinding.returnType, memberValuePairOffset);
                        if (this.contentsOffset == memberValuePairOffset) {
                           this.contents[this.contentsOffset++] = 0;
                           this.contents[this.contentsOffset++] = 0;
                           break;
                        }

                        ++memberValuePairOffset;
                        resetPosition = this.contentsOffset;
                     } catch (ClassCastException var19) {
                        this.contentsOffset = resetPosition;
                     } catch (ShouldNotImplement var20) {
                        this.contentsOffset = resetPosition;
                     }
                  }
               }

               this.contents[memberValuePairsLengthPosition++] = (byte)(memberValuePairOffset >> 8);
               this.contents[memberValuePairsLengthPosition++] = (byte)memberValuePairOffset;
            } else {
               this.contents[this.contentsOffset++] = 0;
               this.contents[this.contentsOffset++] = 0;
            }
         } else if (annotation instanceof SingleMemberAnnotation) {
            SingleMemberAnnotation singleMemberAnnotation = (SingleMemberAnnotation)annotation;
            this.contents[this.contentsOffset++] = 0;
            this.contents[this.contentsOffset++] = 1;
            if (this.contentsOffset + 2 >= this.contents.length) {
               this.resizeContents(2);
            }

            int elementNameIndex = this.constantPool.literalIndex(VALUE);
            this.contents[this.contentsOffset++] = (byte)(elementNameIndex >> 8);
            this.contents[this.contentsOffset++] = (byte)elementNameIndex;
            MethodBinding methodBinding = singleMemberAnnotation.memberValuePairs()[0].binding;
            if (methodBinding == null) {
               this.contentsOffset = currentOffset;
            } else {
               memberValuePairOffset = this.contentsOffset;

               try {
                  this.generateElementValue(singleMemberAnnotation.memberValue, methodBinding.returnType, memberValuePairOffset);
                  if (this.contentsOffset == memberValuePairOffset) {
                     this.contentsOffset = startingContentsOffset;
                  }
               } catch (ClassCastException var17) {
                  this.contentsOffset = currentOffset;
               } catch (ShouldNotImplement var18) {
                  this.contentsOffset = currentOffset;
               }
            }
         } else {
            this.contents[this.contentsOffset++] = 0;
            this.contents[this.contentsOffset++] = 0;
         }

      }
   }

   private int generateAnnotationDefaultAttribute(AnnotationMethodDeclaration declaration, int attributeOffset) {
      int attributesNumber = 0;
      int annotationDefaultNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.AnnotationDefaultName);
      if (this.contentsOffset + 6 >= this.contents.length) {
         this.resizeContents(6);
      }

      this.contents[this.contentsOffset++] = (byte)(annotationDefaultNameIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)annotationDefaultNameIndex;
      int attributeLengthOffset = this.contentsOffset;
      this.contentsOffset += 4;
      this.generateElementValue(declaration.defaultValue, declaration.binding.returnType, attributeOffset);
      if (this.contentsOffset != attributeOffset) {
         int attributeLength = this.contentsOffset - attributeLengthOffset - 4;
         this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 24);
         this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 16);
         this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 8);
         this.contents[attributeLengthOffset++] = (byte)attributeLength;
         ++attributesNumber;
      }

      return attributesNumber;
   }

   public void generateCodeAttributeHeader() {
      if (this.contentsOffset + 20 >= this.contents.length) {
         this.resizeContents(20);
      }

      int constantValueNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.CodeName);
      this.contents[this.contentsOffset++] = (byte)(constantValueNameIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)constantValueNameIndex;
      this.contentsOffset += 12;
   }

   private int generateConstantValueAttribute(Constant fieldConstant, FieldBinding fieldBinding, int fieldAttributeOffset) {
      int localContentsOffset = this.contentsOffset;
      int attributesNumber = 1;
      if (localContentsOffset + 8 >= this.contents.length) {
         this.resizeContents(8);
      }

      int constantValueNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.ConstantValueName);
      this.contents[localContentsOffset++] = (byte)(constantValueNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)constantValueNameIndex;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 2;
      switch (fieldConstant.typeID()) {
         case 2:
         case 3:
         case 4:
         case 10:
            int integerValueIndex = this.constantPool.literalIndex(fieldConstant.intValue());
            this.contents[localContentsOffset++] = (byte)(integerValueIndex >> 8);
            this.contents[localContentsOffset++] = (byte)integerValueIndex;
            break;
         case 5:
            int booleanValueIndex = this.constantPool.literalIndex(fieldConstant.booleanValue() ? 1 : 0);
            this.contents[localContentsOffset++] = (byte)(booleanValueIndex >> 8);
            this.contents[localContentsOffset++] = (byte)booleanValueIndex;
         case 6:
         default:
            break;
         case 7:
            int longValueIndex = this.constantPool.literalIndex(fieldConstant.longValue());
            this.contents[localContentsOffset++] = (byte)(longValueIndex >> 8);
            this.contents[localContentsOffset++] = (byte)longValueIndex;
            break;
         case 8:
            int doubleValueIndex = this.constantPool.literalIndex(fieldConstant.doubleValue());
            this.contents[localContentsOffset++] = (byte)(doubleValueIndex >> 8);
            this.contents[localContentsOffset++] = (byte)doubleValueIndex;
            break;
         case 9:
            int floatValueIndex = this.constantPool.literalIndex(fieldConstant.floatValue());
            this.contents[localContentsOffset++] = (byte)(floatValueIndex >> 8);
            this.contents[localContentsOffset++] = (byte)floatValueIndex;
            break;
         case 11:
            int stringValueIndex = this.constantPool.literalIndex(((StringConstant)fieldConstant).stringValue());
            if (stringValueIndex == -1) {
               if (!this.creatingProblemType) {
                  TypeDeclaration typeDeclaration = this.referenceBinding.scope.referenceContext;
                  FieldDeclaration[] fieldDecls = typeDeclaration.fields;
                  int max = fieldDecls == null ? 0 : fieldDecls.length;

                  for(int i = 0; i < max; ++i) {
                     if (fieldDecls[i].binding == fieldBinding) {
                        typeDeclaration.scope.problemReporter().stringConstantIsExceedingUtf8Limit(fieldDecls[i]);
                     }
                  }
               } else {
                  this.contentsOffset = fieldAttributeOffset;
                  attributesNumber = 0;
               }
            } else {
               this.contents[localContentsOffset++] = (byte)(stringValueIndex >> 8);
               this.contents[localContentsOffset++] = (byte)stringValueIndex;
            }
      }

      this.contentsOffset = localContentsOffset;
      return attributesNumber;
   }

   private int generateDeprecatedAttribute() {
      int localContentsOffset = this.contentsOffset;
      if (localContentsOffset + 6 >= this.contents.length) {
         this.resizeContents(6);
      }

      int deprecatedAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.DeprecatedName);
      this.contents[localContentsOffset++] = (byte)(deprecatedAttributeNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)deprecatedAttributeNameIndex;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private int generateNestHostAttribute() {
      SourceTypeBinding nestHost = this.referenceBinding.getNestHost();
      if (nestHost == null) {
         return 0;
      } else {
         int localContentsOffset = this.contentsOffset;
         if (localContentsOffset + 10 >= this.contents.length) {
            this.resizeContents(10);
         }

         int nestHostAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.NestHost);
         this.contents[localContentsOffset++] = (byte)(nestHostAttributeNameIndex >> 8);
         this.contents[localContentsOffset++] = (byte)nestHostAttributeNameIndex;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 2;
         int nestHostIndex = this.constantPool.literalIndexForType(nestHost.constantPoolName());
         this.contents[localContentsOffset++] = (byte)(nestHostIndex >> 8);
         this.contents[localContentsOffset++] = (byte)nestHostIndex;
         this.contentsOffset = localContentsOffset;
         return 1;
      }
   }

   private int generateNestMembersAttribute() {
      int localContentsOffset = this.contentsOffset;
      List nestedMembers = this.referenceBinding.getNestMembers();
      int numberOfNestedMembers = nestedMembers != null ? nestedMembers.size() : 0;
      if (numberOfNestedMembers == 0) {
         return 0;
      } else {
         int exSize = 8 + 2 * numberOfNestedMembers;
         if (exSize + localContentsOffset >= this.contents.length) {
            this.resizeContents(exSize);
         }

         int attributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.NestMembers);
         this.contents[localContentsOffset++] = (byte)(attributeNameIndex >> 8);
         this.contents[localContentsOffset++] = (byte)attributeNameIndex;
         int value = (numberOfNestedMembers << 1) + 2;
         this.contents[localContentsOffset++] = (byte)(value >> 24);
         this.contents[localContentsOffset++] = (byte)(value >> 16);
         this.contents[localContentsOffset++] = (byte)(value >> 8);
         this.contents[localContentsOffset++] = (byte)value;
         this.contents[localContentsOffset++] = (byte)(numberOfNestedMembers >> 8);
         this.contents[localContentsOffset++] = (byte)numberOfNestedMembers;

         for(int i = 0; i < numberOfNestedMembers; ++i) {
            char[] nestMemberName = ((String)nestedMembers.get(i)).toCharArray();
            int nestedMemberIndex = this.constantPool.literalIndexForType(nestMemberName);
            this.contents[localContentsOffset++] = (byte)(nestedMemberIndex >> 8);
            this.contents[localContentsOffset++] = (byte)nestedMemberIndex;
         }

         this.contentsOffset = localContentsOffset;
         return 1;
      }
   }

   private int generateNestAttributes() {
      int nAttrs = this.generateNestMembersAttribute();
      nAttrs += this.generateNestHostAttribute();
      return nAttrs;
   }

   private int generateModuleAttribute(ModuleDeclaration module) {
      ModuleBinding binding = module.binding;
      int localContentsOffset = this.contentsOffset;
      if (localContentsOffset + 10 >= this.contents.length) {
         this.resizeContents(10);
      }

      int moduleAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.ModuleName);
      this.contents[localContentsOffset++] = (byte)(moduleAttributeNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)moduleAttributeNameIndex;
      int attrLengthOffset = localContentsOffset;
      localContentsOffset += 4;
      int moduleNameIndex = this.constantPool.literalIndexForModule(binding.moduleName);
      this.contents[localContentsOffset++] = (byte)(moduleNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)moduleNameIndex;
      int flags = module.modifiers & -32769;
      this.contents[localContentsOffset++] = (byte)(flags >> 8);
      this.contents[localContentsOffset++] = (byte)flags;
      int module_version = 0;
      this.contents[localContentsOffset++] = (byte)(module_version >> 8);
      this.contents[localContentsOffset++] = (byte)module_version;
      int attrLength = 6;
      int requiresCountOffset = localContentsOffset;
      int requiresCount = module.requiresCount;
      int requiresSize = 2 + requiresCount * 6;
      if (localContentsOffset + requiresSize >= this.contents.length) {
         this.resizeContents(requiresSize);
      }

      localContentsOffset += 2;
      ModuleBinding javaBaseBinding = null;

      int exportsSize;
      int servicesSize;
      int i;
      for(exportsSize = 0; exportsSize < module.requiresCount; ++exportsSize) {
         RequiresStatement req = module.requires[exportsSize];
         ModuleBinding reqBinding = req.resolvedBinding;
         if (CharOperation.equals(reqBinding.moduleName, TypeConstants.JAVA_BASE)) {
            javaBaseBinding = reqBinding;
         }

         servicesSize = this.constantPool.literalIndexForModule(reqBinding.moduleName);
         this.contents[localContentsOffset++] = (byte)(servicesSize >> 8);
         this.contents[localContentsOffset++] = (byte)servicesSize;
         flags = req.modifiers;
         this.contents[localContentsOffset++] = (byte)(flags >> 8);
         this.contents[localContentsOffset++] = (byte)flags;
         i = 0;
         this.contents[localContentsOffset++] = (byte)(i >> 8);
         this.contents[localContentsOffset++] = (byte)i;
      }

      int opensSize;
      if (!CharOperation.equals(binding.moduleName, TypeConstants.JAVA_BASE) && javaBaseBinding == null) {
         if (localContentsOffset + 6 >= this.contents.length) {
            this.resizeContents(6);
         }

         javaBaseBinding = binding.environment.javaBaseModule();
         exportsSize = this.constantPool.literalIndexForModule(javaBaseBinding.moduleName);
         this.contents[localContentsOffset++] = (byte)(exportsSize >> 8);
         this.contents[localContentsOffset++] = (byte)exportsSize;
         int flags = '耀';
         this.contents[localContentsOffset++] = (byte)(flags >> 8);
         this.contents[localContentsOffset++] = (byte)flags;
         opensSize = 0;
         this.contents[localContentsOffset++] = (byte)(opensSize >> 8);
         this.contents[localContentsOffset++] = (byte)opensSize;
         ++requiresCount;
      }

      this.contents[requiresCountOffset++] = (byte)(requiresCount >> 8);
      this.contents[requiresCountOffset++] = (byte)requiresCount;
      attrLength += 2 + 6 * requiresCount;
      exportsSize = 2 + module.exportsCount * 6;
      if (localContentsOffset + exportsSize >= this.contents.length) {
         this.resizeContents(exportsSize);
      }

      this.contents[localContentsOffset++] = (byte)(module.exportsCount >> 8);
      this.contents[localContentsOffset++] = (byte)module.exportsCount;

      int nameIndex;
      int targetSize;
      for(opensSize = 0; opensSize < module.exportsCount; ++opensSize) {
         ExportsStatement ref = module.exports[opensSize];
         if (localContentsOffset + 6 >= this.contents.length) {
            this.resizeContents((module.exportsCount - opensSize) * 6);
         }

         servicesSize = this.constantPool.literalIndexForPackage(CharOperation.replaceOnCopy(ref.pkgName, '.', '/'));
         this.contents[localContentsOffset++] = (byte)(servicesSize >> 8);
         this.contents[localContentsOffset++] = (byte)servicesSize;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 0;
         i = ref.isQualified() ? ref.targets.length : 0;
         this.contents[localContentsOffset++] = (byte)(i >> 8);
         this.contents[localContentsOffset++] = (byte)i;
         if (i > 0) {
            nameIndex = 2 * i;
            if (localContentsOffset + nameIndex >= this.contents.length) {
               this.resizeContents(nameIndex);
            }

            for(targetSize = 0; targetSize < i; ++targetSize) {
               servicesSize = this.constantPool.literalIndexForModule(ref.targets[targetSize].moduleName);
               this.contents[localContentsOffset++] = (byte)(servicesSize >> 8);
               this.contents[localContentsOffset++] = (byte)servicesSize;
            }

            attrLength += nameIndex;
         }
      }

      attrLength += exportsSize;
      opensSize = 2 + module.opensCount * 6;
      if (localContentsOffset + opensSize >= this.contents.length) {
         this.resizeContents(opensSize);
      }

      this.contents[localContentsOffset++] = (byte)(module.opensCount >> 8);
      this.contents[localContentsOffset++] = (byte)module.opensCount;

      int implLength;
      int usesSize;
      for(usesSize = 0; usesSize < module.opensCount; ++usesSize) {
         OpensStatement ref = module.opens[usesSize];
         if (localContentsOffset + 6 >= this.contents.length) {
            this.resizeContents((module.opensCount - usesSize) * 6);
         }

         i = this.constantPool.literalIndexForPackage(CharOperation.replaceOnCopy(ref.pkgName, '.', '/'));
         this.contents[localContentsOffset++] = (byte)(i >> 8);
         this.contents[localContentsOffset++] = (byte)i;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 0;
         nameIndex = ref.isQualified() ? ref.targets.length : 0;
         this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
         this.contents[localContentsOffset++] = (byte)nameIndex;
         if (nameIndex > 0) {
            targetSize = 2 * nameIndex;
            if (localContentsOffset + targetSize >= this.contents.length) {
               this.resizeContents(targetSize);
            }

            for(implLength = 0; implLength < nameIndex; ++implLength) {
               i = this.constantPool.literalIndexForModule(ref.targets[implLength].moduleName);
               this.contents[localContentsOffset++] = (byte)(i >> 8);
               this.contents[localContentsOffset++] = (byte)i;
            }

            attrLength += targetSize;
         }
      }

      attrLength += opensSize;
      usesSize = 2 + 2 * module.usesCount;
      if (localContentsOffset + usesSize >= this.contents.length) {
         this.resizeContents(usesSize);
      }

      this.contents[localContentsOffset++] = (byte)(module.usesCount >> 8);
      this.contents[localContentsOffset++] = (byte)module.usesCount;

      for(servicesSize = 0; servicesSize < module.usesCount; ++servicesSize) {
         i = this.constantPool.literalIndexForType(module.uses[servicesSize].serviceInterface.resolvedType.constantPoolName());
         this.contents[localContentsOffset++] = (byte)(i >> 8);
         this.contents[localContentsOffset++] = (byte)i;
      }

      attrLength += usesSize;
      servicesSize = 2 + 4 * module.servicesCount;
      if (localContentsOffset + servicesSize >= this.contents.length) {
         this.resizeContents(servicesSize);
      }

      this.contents[localContentsOffset++] = (byte)(module.servicesCount >> 8);
      this.contents[localContentsOffset++] = (byte)module.servicesCount;

      for(i = 0; i < module.servicesCount; ++i) {
         if (localContentsOffset + 4 >= this.contents.length) {
            this.resizeContents((module.servicesCount - i) * 4);
         }

         nameIndex = this.constantPool.literalIndexForType(module.services[i].serviceInterface.resolvedType.constantPoolName());
         this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
         this.contents[localContentsOffset++] = (byte)nameIndex;
         TypeReference[] impls = module.services[i].implementations;
         implLength = impls.length;
         this.contents[localContentsOffset++] = (byte)(implLength >> 8);
         this.contents[localContentsOffset++] = (byte)implLength;
         int targetSize = implLength * 2;
         if (localContentsOffset + targetSize >= this.contents.length) {
            this.resizeContents(targetSize);
         }

         for(int j = 0; j < implLength; ++j) {
            nameIndex = this.constantPool.literalIndexForType(impls[j].resolvedType.constantPoolName());
            this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
            this.contents[localContentsOffset++] = (byte)nameIndex;
         }

         attrLength += targetSize;
      }

      attrLength += servicesSize;
      this.contents[attrLengthOffset++] = (byte)(attrLength >> 24);
      this.contents[attrLengthOffset++] = (byte)(attrLength >> 16);
      this.contents[attrLengthOffset++] = (byte)(attrLength >> 8);
      this.contents[attrLengthOffset++] = (byte)attrLength;
      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private int generateModuleMainClassAttribute(char[] moduleMainClass) {
      int localContentsOffset = this.contentsOffset;
      if (localContentsOffset + 8 >= this.contents.length) {
         this.resizeContents(8);
      }

      int moduleAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.ModuleMainClass);
      this.contents[localContentsOffset++] = (byte)(moduleAttributeNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)moduleAttributeNameIndex;
      int attrLength = 2;
      this.contents[localContentsOffset++] = (byte)(attrLength >> 24);
      this.contents[localContentsOffset++] = (byte)(attrLength >> 16);
      this.contents[localContentsOffset++] = (byte)(attrLength >> 8);
      this.contents[localContentsOffset++] = (byte)attrLength;
      int moduleNameIndex = this.constantPool.literalIndexForType(moduleMainClass);
      this.contents[localContentsOffset++] = (byte)(moduleNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)moduleNameIndex;
      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private int generateModulePackagesAttribute(char[][] packageNames) {
      int localContentsOffset = this.contentsOffset;
      int maxSize = 6 + 2 * packageNames.length;
      if (localContentsOffset + maxSize >= this.contents.length) {
         this.resizeContents(maxSize);
      }

      int moduleAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.ModulePackages);
      this.contents[localContentsOffset++] = (byte)(moduleAttributeNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)moduleAttributeNameIndex;
      int attrLengthOffset = localContentsOffset;
      localContentsOffset += 4;
      int packageCountOffset = localContentsOffset;
      localContentsOffset += 2;
      int packagesCount = 0;
      char[][] var11 = packageNames;
      int var10 = packageNames.length;

      for(int var9 = 0; var9 < var10; ++var9) {
         char[] packageName = var11[var9];
         if (packageName != null && packageName.length != 0) {
            int packageNameIndex = this.constantPool.literalIndexForPackage(packageName);
            this.contents[localContentsOffset++] = (byte)(packageNameIndex >> 8);
            this.contents[localContentsOffset++] = (byte)packageNameIndex;
            ++packagesCount;
         }
      }

      this.contents[packageCountOffset++] = (byte)(packagesCount >> 8);
      this.contents[packageCountOffset++] = (byte)packagesCount;
      int attrLength = 2 + 2 * packagesCount;
      this.contents[attrLengthOffset++] = (byte)(attrLength >> 24);
      this.contents[attrLengthOffset++] = (byte)(attrLength >> 16);
      this.contents[attrLengthOffset++] = (byte)(attrLength >> 8);
      this.contents[attrLengthOffset++] = (byte)attrLength;
      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private void generateElementValue(Expression defaultValue, TypeBinding memberValuePairReturnType, int attributeOffset) {
      Constant constant = defaultValue.constant;
      TypeBinding defaultValueBinding = defaultValue.resolvedType;
      if (defaultValueBinding == null) {
         this.contentsOffset = attributeOffset;
      } else {
         if (defaultValueBinding.isMemberType()) {
            this.recordInnerClasses(defaultValueBinding);
         }

         if (memberValuePairReturnType.isMemberType()) {
            this.recordInnerClasses(memberValuePairReturnType);
         }

         if (memberValuePairReturnType.isArrayType() && !defaultValueBinding.isArrayType()) {
            if (this.contentsOffset + 3 >= this.contents.length) {
               this.resizeContents(3);
            }

            this.contents[this.contentsOffset++] = 91;
            this.contents[this.contentsOffset++] = 0;
            this.contents[this.contentsOffset++] = 1;
         }

         if (constant != null && constant != Constant.NotAConstant) {
            this.generateElementValue(attributeOffset, defaultValue, constant, memberValuePairReturnType.leafComponentType());
         } else {
            this.generateElementValueForNonConstantExpression(defaultValue, attributeOffset, defaultValueBinding);
         }
      }

   }

   private void generateElementValue(int attributeOffset, Expression defaultValue, Constant constant, TypeBinding binding) {
      if (this.contentsOffset + 3 >= this.contents.length) {
         this.resizeContents(3);
      }

      int integerValueIndex;
      switch (binding.id) {
         case 2:
            this.contents[this.contentsOffset++] = 67;
            integerValueIndex = this.constantPool.literalIndex(constant.intValue());
            this.contents[this.contentsOffset++] = (byte)(integerValueIndex >> 8);
            this.contents[this.contentsOffset++] = (byte)integerValueIndex;
            break;
         case 3:
            this.contents[this.contentsOffset++] = 66;
            integerValueIndex = this.constantPool.literalIndex(constant.intValue());
            this.contents[this.contentsOffset++] = (byte)(integerValueIndex >> 8);
            this.contents[this.contentsOffset++] = (byte)integerValueIndex;
            break;
         case 4:
            this.contents[this.contentsOffset++] = 83;
            integerValueIndex = this.constantPool.literalIndex(constant.intValue());
            this.contents[this.contentsOffset++] = (byte)(integerValueIndex >> 8);
            this.contents[this.contentsOffset++] = (byte)integerValueIndex;
            break;
         case 5:
            this.contents[this.contentsOffset++] = 90;
            int booleanValueIndex = this.constantPool.literalIndex(constant.booleanValue() ? 1 : 0);
            this.contents[this.contentsOffset++] = (byte)(booleanValueIndex >> 8);
            this.contents[this.contentsOffset++] = (byte)booleanValueIndex;
         case 6:
         default:
            break;
         case 7:
            this.contents[this.contentsOffset++] = 74;
            int longValueIndex = this.constantPool.literalIndex(constant.longValue());
            this.contents[this.contentsOffset++] = (byte)(longValueIndex >> 8);
            this.contents[this.contentsOffset++] = (byte)longValueIndex;
            break;
         case 8:
            this.contents[this.contentsOffset++] = 68;
            int doubleValueIndex = this.constantPool.literalIndex(constant.doubleValue());
            this.contents[this.contentsOffset++] = (byte)(doubleValueIndex >> 8);
            this.contents[this.contentsOffset++] = (byte)doubleValueIndex;
            break;
         case 9:
            this.contents[this.contentsOffset++] = 70;
            int floatValueIndex = this.constantPool.literalIndex(constant.floatValue());
            this.contents[this.contentsOffset++] = (byte)(floatValueIndex >> 8);
            this.contents[this.contentsOffset++] = (byte)floatValueIndex;
            break;
         case 10:
            this.contents[this.contentsOffset++] = 73;
            integerValueIndex = this.constantPool.literalIndex(constant.intValue());
            this.contents[this.contentsOffset++] = (byte)(integerValueIndex >> 8);
            this.contents[this.contentsOffset++] = (byte)integerValueIndex;
            break;
         case 11:
            this.contents[this.contentsOffset++] = 115;
            int stringValueIndex = this.constantPool.literalIndex(((StringConstant)constant).stringValue().toCharArray());
            if (stringValueIndex == -1) {
               if (!this.creatingProblemType) {
                  TypeDeclaration typeDeclaration = this.referenceBinding.scope.referenceContext;
                  typeDeclaration.scope.problemReporter().stringConstantIsExceedingUtf8Limit(defaultValue);
               } else {
                  this.contentsOffset = attributeOffset;
               }
            } else {
               this.contents[this.contentsOffset++] = (byte)(stringValueIndex >> 8);
               this.contents[this.contentsOffset++] = (byte)stringValueIndex;
            }
      }

   }

   private void generateElementValueForNonConstantExpression(Expression defaultValue, int attributeOffset, TypeBinding defaultValueBinding) {
      if (defaultValueBinding != null) {
         int i;
         int arrayLength;
         if (defaultValueBinding.isEnum()) {
            if (this.contentsOffset + 5 >= this.contents.length) {
               this.resizeContents(5);
            }

            this.contents[this.contentsOffset++] = 101;
            FieldBinding fieldBinding = null;
            if (defaultValue instanceof QualifiedNameReference) {
               QualifiedNameReference nameReference = (QualifiedNameReference)defaultValue;
               fieldBinding = (FieldBinding)nameReference.binding;
            } else if (defaultValue instanceof SingleNameReference) {
               SingleNameReference nameReference = (SingleNameReference)defaultValue;
               fieldBinding = (FieldBinding)nameReference.binding;
            } else {
               this.contentsOffset = attributeOffset;
            }

            if (fieldBinding != null) {
               arrayLength = this.constantPool.literalIndex(fieldBinding.type.signature());
               i = this.constantPool.literalIndex(fieldBinding.name);
               this.contents[this.contentsOffset++] = (byte)(arrayLength >> 8);
               this.contents[this.contentsOffset++] = (byte)arrayLength;
               this.contents[this.contentsOffset++] = (byte)(i >> 8);
               this.contents[this.contentsOffset++] = (byte)i;
            }
         } else if (defaultValueBinding.isAnnotationType()) {
            if (this.contentsOffset + 1 >= this.contents.length) {
               this.resizeContents(1);
            }

            this.contents[this.contentsOffset++] = 64;
            this.generateAnnotation((Annotation)defaultValue, attributeOffset);
         } else if (defaultValueBinding.isArrayType()) {
            if (this.contentsOffset + 3 >= this.contents.length) {
               this.resizeContents(3);
            }

            this.contents[this.contentsOffset++] = 91;
            if (defaultValue instanceof ArrayInitializer) {
               ArrayInitializer arrayInitializer = (ArrayInitializer)defaultValue;
               arrayLength = arrayInitializer.expressions != null ? arrayInitializer.expressions.length : 0;
               this.contents[this.contentsOffset++] = (byte)(arrayLength >> 8);
               this.contents[this.contentsOffset++] = (byte)arrayLength;

               for(i = 0; i < arrayLength; ++i) {
                  this.generateElementValue(arrayInitializer.expressions[i], defaultValueBinding.leafComponentType(), attributeOffset);
               }
            } else {
               this.contentsOffset = attributeOffset;
            }
         } else {
            if (this.contentsOffset + 3 >= this.contents.length) {
               this.resizeContents(3);
            }

            this.contents[this.contentsOffset++] = 99;
            if (defaultValue instanceof ClassLiteralAccess) {
               ClassLiteralAccess classLiteralAccess = (ClassLiteralAccess)defaultValue;
               arrayLength = this.constantPool.literalIndex(classLiteralAccess.targetType.signature());
               this.contents[this.contentsOffset++] = (byte)(arrayLength >> 8);
               this.contents[this.contentsOffset++] = (byte)arrayLength;
            } else {
               this.contentsOffset = attributeOffset;
            }
         }
      } else {
         this.contentsOffset = attributeOffset;
      }

   }

   private int generateEnclosingMethodAttribute() {
      int localContentsOffset = this.contentsOffset;
      if (localContentsOffset + 10 >= this.contents.length) {
         this.resizeContents(10);
      }

      int enclosingMethodAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.EnclosingMethodName);
      this.contents[localContentsOffset++] = (byte)(enclosingMethodAttributeNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)enclosingMethodAttributeNameIndex;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 4;
      int enclosingTypeIndex = this.constantPool.literalIndexForType(this.referenceBinding.enclosingType().constantPoolName());
      this.contents[localContentsOffset++] = (byte)(enclosingTypeIndex >> 8);
      this.contents[localContentsOffset++] = (byte)enclosingTypeIndex;
      byte methodIndexByte1 = 0;
      byte methodIndexByte2 = 0;
      if (this.referenceBinding instanceof LocalTypeBinding) {
         MethodBinding methodBinding = ((LocalTypeBinding)this.referenceBinding).enclosingMethod;
         if (methodBinding != null) {
            int enclosingMethodIndex = this.constantPool.literalIndexForNameAndType(methodBinding.selector, methodBinding.signature(this));
            methodIndexByte1 = (byte)(enclosingMethodIndex >> 8);
            methodIndexByte2 = (byte)enclosingMethodIndex;
         }
      }

      this.contents[localContentsOffset++] = methodIndexByte1;
      this.contents[localContentsOffset++] = methodIndexByte2;
      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private int generateExceptionsAttribute(ReferenceBinding[] thrownsExceptions) {
      int localContentsOffset = this.contentsOffset;
      int length = thrownsExceptions.length;
      int exSize = 8 + length * 2;
      if (exSize + this.contentsOffset >= this.contents.length) {
         this.resizeContents(exSize);
      }

      int exceptionNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.ExceptionsName);
      this.contents[localContentsOffset++] = (byte)(exceptionNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)exceptionNameIndex;
      int attributeLength = length * 2 + 2;
      this.contents[localContentsOffset++] = (byte)(attributeLength >> 24);
      this.contents[localContentsOffset++] = (byte)(attributeLength >> 16);
      this.contents[localContentsOffset++] = (byte)(attributeLength >> 8);
      this.contents[localContentsOffset++] = (byte)attributeLength;
      this.contents[localContentsOffset++] = (byte)(length >> 8);
      this.contents[localContentsOffset++] = (byte)length;

      for(int i = 0; i < length; ++i) {
         int exceptionIndex = this.constantPool.literalIndexForType((TypeBinding)thrownsExceptions[i]);
         this.contents[localContentsOffset++] = (byte)(exceptionIndex >> 8);
         this.contents[localContentsOffset++] = (byte)exceptionIndex;
      }

      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private int generateHierarchyInconsistentAttribute() {
      int localContentsOffset = this.contentsOffset;
      if (localContentsOffset + 6 >= this.contents.length) {
         this.resizeContents(6);
      }

      int inconsistentHierarchyNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.InconsistentHierarchy);
      this.contents[localContentsOffset++] = (byte)(inconsistentHierarchyNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)inconsistentHierarchyNameIndex;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private int generateInnerClassAttribute(int numberOfInnerClasses, ReferenceBinding[] innerClasses) {
      int localContentsOffset = this.contentsOffset;
      int exSize = 8 * numberOfInnerClasses + 8;
      if (exSize + localContentsOffset >= this.contents.length) {
         this.resizeContents(exSize);
      }

      int attributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.InnerClassName);
      this.contents[localContentsOffset++] = (byte)(attributeNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)attributeNameIndex;
      int value = (numberOfInnerClasses << 3) + 2;
      this.contents[localContentsOffset++] = (byte)(value >> 24);
      this.contents[localContentsOffset++] = (byte)(value >> 16);
      this.contents[localContentsOffset++] = (byte)(value >> 8);
      this.contents[localContentsOffset++] = (byte)value;
      this.contents[localContentsOffset++] = (byte)(numberOfInnerClasses >> 8);
      this.contents[localContentsOffset++] = (byte)numberOfInnerClasses;

      for(int i = 0; i < numberOfInnerClasses; ++i) {
         ReferenceBinding innerClass = innerClasses[i];
         int accessFlags = innerClass.getAccessFlags();
         int innerClassIndex = this.constantPool.literalIndexForType(innerClass.constantPoolName());
         this.contents[localContentsOffset++] = (byte)(innerClassIndex >> 8);
         this.contents[localContentsOffset++] = (byte)innerClassIndex;
         int nameIndex;
         if (innerClass.isMemberType()) {
            nameIndex = this.constantPool.literalIndexForType(innerClass.enclosingType().constantPoolName());
            this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
            this.contents[localContentsOffset++] = (byte)nameIndex;
         } else {
            this.contents[localContentsOffset++] = 0;
            this.contents[localContentsOffset++] = 0;
         }

         if (!innerClass.isAnonymousType()) {
            nameIndex = this.constantPool.literalIndex(innerClass.sourceName());
            this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
            this.contents[localContentsOffset++] = (byte)nameIndex;
         } else {
            this.contents[localContentsOffset++] = 0;
            this.contents[localContentsOffset++] = 0;
         }

         if (innerClass.isAnonymousType()) {
            accessFlags &= -17;
         } else if (innerClass.isMemberType() && innerClass.isInterface()) {
            accessFlags |= 8;
         }

         this.contents[localContentsOffset++] = (byte)(accessFlags >> 8);
         this.contents[localContentsOffset++] = (byte)accessFlags;
      }

      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private int generateBootstrapMethods(List functionalExpressionList) {
      ReferenceBinding methodHandlesLookup = this.referenceBinding.scope.getJavaLangInvokeMethodHandlesLookup();
      if (methodHandlesLookup == null) {
         return 0;
      } else {
         this.recordInnerClasses(methodHandlesLookup);
         ReferenceBinding javaLangInvokeLambdaMetafactory = this.referenceBinding.scope.getJavaLangInvokeLambdaMetafactory();
         int indexForMetaFactory = 0;
         int indexForAltMetaFactory = 0;
         int numberOfBootstraps = functionalExpressionList.size();
         int localContentsOffset = this.contentsOffset;
         int exSize = 10 * numberOfBootstraps + 8;
         if (exSize + localContentsOffset >= this.contents.length) {
            this.resizeContents(exSize);
         }

         int attributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.BootstrapMethodsName);
         this.contents[localContentsOffset++] = (byte)(attributeNameIndex >> 8);
         this.contents[localContentsOffset++] = (byte)attributeNameIndex;
         int attributeLengthPosition = localContentsOffset;
         localContentsOffset += 4;
         this.contents[localContentsOffset++] = (byte)(numberOfBootstraps >> 8);
         this.contents[localContentsOffset++] = (byte)numberOfBootstraps;

         int attributeLength;
         for(attributeLength = 0; attributeLength < numberOfBootstraps; ++attributeLength) {
            FunctionalExpression functional = (FunctionalExpression)functionalExpressionList.get(attributeLength);
            MethodBinding[] bridges = functional.getRequiredBridges();
            TypeBinding[] markerInterfaces = null;
            int extraSpace;
            int functionalDescriptorIndex;
            if ((!(functional instanceof LambdaExpression) || (markerInterfaces = ((LambdaExpression)functional).getMarkerInterfaces()) == null) && bridges == null && !functional.isSerializable) {
               if (10 + localContentsOffset >= this.contents.length) {
                  this.resizeContents(10);
               }

               if (indexForMetaFactory == 0) {
                  indexForMetaFactory = this.constantPool.literalIndexForMethodHandle(6, javaLangInvokeLambdaMetafactory, ConstantPool.METAFACTORY, ConstantPool.JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_METAFACTORY_SIGNATURE, false);
               }

               this.contents[localContentsOffset++] = (byte)(indexForMetaFactory >> 8);
               this.contents[localContentsOffset++] = (byte)indexForMetaFactory;
               this.contents[localContentsOffset++] = 0;
               this.contents[localContentsOffset++] = 3;
               extraSpace = this.constantPool.literalIndexForMethodType(functional.descriptor.original().signature());
               this.contents[localContentsOffset++] = (byte)(extraSpace >> 8);
               this.contents[localContentsOffset++] = (byte)extraSpace;
               functionalDescriptorIndex = this.constantPool.literalIndexForMethodHandle(functional.binding instanceof PolymorphicMethodBinding ? functional.binding : functional.binding.original());
               this.contents[localContentsOffset++] = (byte)(functionalDescriptorIndex >> 8);
               this.contents[localContentsOffset++] = (byte)functionalDescriptorIndex;
               char[] instantiatedSignature = functional.descriptor.signature();
               int methodTypeIndex = this.constantPool.literalIndexForMethodType(instantiatedSignature);
               this.contents[localContentsOffset++] = (byte)(methodTypeIndex >> 8);
               this.contents[localContentsOffset++] = (byte)methodTypeIndex;
            } else {
               extraSpace = 2;
               if (markerInterfaces != null) {
                  extraSpace += 2 + 2 * markerInterfaces.length;
               }

               if (bridges != null) {
                  extraSpace += 2 + 2 * bridges.length;
               }

               if (extraSpace + 10 + localContentsOffset >= this.contents.length) {
                  this.resizeContents(extraSpace + 10);
               }

               if (indexForAltMetaFactory == 0) {
                  indexForAltMetaFactory = this.constantPool.literalIndexForMethodHandle(6, javaLangInvokeLambdaMetafactory, ConstantPool.ALTMETAFACTORY, ConstantPool.JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_ALTMETAFACTORY_SIGNATURE, false);
               }

               this.contents[localContentsOffset++] = (byte)(indexForAltMetaFactory >> 8);
               this.contents[localContentsOffset++] = (byte)indexForAltMetaFactory;
               this.contents[localContentsOffset++] = 0;
               this.contents[localContentsOffset++] = (byte)(4 + (markerInterfaces == null ? 0 : 1 + markerInterfaces.length) + (bridges == null ? 0 : 1 + bridges.length));
               functionalDescriptorIndex = this.constantPool.literalIndexForMethodType(functional.descriptor.original().signature());
               this.contents[localContentsOffset++] = (byte)(functionalDescriptorIndex >> 8);
               this.contents[localContentsOffset++] = (byte)functionalDescriptorIndex;
               int methodHandleIndex = this.constantPool.literalIndexForMethodHandle(functional.binding.original());
               this.contents[localContentsOffset++] = (byte)(methodHandleIndex >> 8);
               this.contents[localContentsOffset++] = (byte)methodHandleIndex;
               char[] instantiatedSignature = functional.descriptor.signature();
               int methodTypeIndex = this.constantPool.literalIndexForMethodType(instantiatedSignature);
               this.contents[localContentsOffset++] = (byte)(methodTypeIndex >> 8);
               this.contents[localContentsOffset++] = (byte)methodTypeIndex;
               int bitflags = 0;
               if (functional.isSerializable) {
                  bitflags |= 1;
               }

               if (markerInterfaces != null) {
                  bitflags |= 2;
               }

               if (bridges != null) {
                  bitflags |= 4;
               }

               int indexForBitflags = this.constantPool.literalIndex(bitflags);
               this.contents[localContentsOffset++] = (byte)(indexForBitflags >> 8);
               this.contents[localContentsOffset++] = (byte)indexForBitflags;
               int bridgeCountIndex;
               int m;
               int maxm;
               if (markerInterfaces != null) {
                  bridgeCountIndex = this.constantPool.literalIndex(markerInterfaces.length);
                  this.contents[localContentsOffset++] = (byte)(bridgeCountIndex >> 8);
                  this.contents[localContentsOffset++] = (byte)bridgeCountIndex;
                  m = 0;

                  for(maxm = markerInterfaces.length; m < maxm; ++m) {
                     int classTypeIndex = this.constantPool.literalIndexForType(markerInterfaces[m]);
                     this.contents[localContentsOffset++] = (byte)(classTypeIndex >> 8);
                     this.contents[localContentsOffset++] = (byte)classTypeIndex;
                  }
               }

               if (bridges != null) {
                  bridgeCountIndex = this.constantPool.literalIndex(bridges.length);
                  this.contents[localContentsOffset++] = (byte)(bridgeCountIndex >> 8);
                  this.contents[localContentsOffset++] = (byte)bridgeCountIndex;
                  m = 0;

                  for(maxm = bridges.length; m < maxm; ++m) {
                     char[] bridgeSignature = bridges[m].signature();
                     int bridgeMethodTypeIndex = this.constantPool.literalIndexForMethodType(bridgeSignature);
                     this.contents[localContentsOffset++] = (byte)(bridgeMethodTypeIndex >> 8);
                     this.contents[localContentsOffset++] = (byte)bridgeMethodTypeIndex;
                  }
               }
            }
         }

         attributeLength = localContentsOffset - attributeLengthPosition - 4;
         this.contents[attributeLengthPosition++] = (byte)(attributeLength >> 24);
         this.contents[attributeLengthPosition++] = (byte)(attributeLength >> 16);
         this.contents[attributeLengthPosition++] = (byte)(attributeLength >> 8);
         this.contents[attributeLengthPosition++] = (byte)attributeLength;
         this.contentsOffset = localContentsOffset;
         return 1;
      }
   }

   private int generateLineNumberAttribute() {
      int localContentsOffset = this.contentsOffset;
      int attributesNumber = 0;
      int[] pcToSourceMapTable;
      if ((pcToSourceMapTable = this.codeStream.pcToSourceMap) != null && this.codeStream.pcToSourceMapSize != 0) {
         int lineNumberNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.LineNumberTableName);
         if (localContentsOffset + 8 >= this.contents.length) {
            this.resizeContents(8);
         }

         this.contents[localContentsOffset++] = (byte)(lineNumberNameIndex >> 8);
         this.contents[localContentsOffset++] = (byte)lineNumberNameIndex;
         int lineNumberTableOffset = localContentsOffset;
         localContentsOffset += 6;
         int numberOfEntries = 0;
         int length = this.codeStream.pcToSourceMapSize;

         int lineNumberAttr_length;
         for(lineNumberAttr_length = 0; lineNumberAttr_length < length; ++numberOfEntries) {
            if (localContentsOffset + 4 >= this.contents.length) {
               this.resizeContents(4);
            }

            int pc = pcToSourceMapTable[lineNumberAttr_length++];
            this.contents[localContentsOffset++] = (byte)(pc >> 8);
            this.contents[localContentsOffset++] = (byte)pc;
            int lineNumber = pcToSourceMapTable[lineNumberAttr_length++];
            this.contents[localContentsOffset++] = (byte)(lineNumber >> 8);
            this.contents[localContentsOffset++] = (byte)lineNumber;
         }

         lineNumberAttr_length = numberOfEntries * 4 + 2;
         this.contents[lineNumberTableOffset++] = (byte)(lineNumberAttr_length >> 24);
         this.contents[lineNumberTableOffset++] = (byte)(lineNumberAttr_length >> 16);
         this.contents[lineNumberTableOffset++] = (byte)(lineNumberAttr_length >> 8);
         this.contents[lineNumberTableOffset++] = (byte)lineNumberAttr_length;
         this.contents[lineNumberTableOffset++] = (byte)(numberOfEntries >> 8);
         this.contents[lineNumberTableOffset++] = (byte)numberOfEntries;
         attributesNumber = 1;
      }

      this.contentsOffset = localContentsOffset;
      return attributesNumber;
   }

   private int generateLineNumberAttribute(int problemLine) {
      int localContentsOffset = this.contentsOffset;
      if (localContentsOffset + 12 >= this.contents.length) {
         this.resizeContents(12);
      }

      int lineNumberNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.LineNumberTableName);
      this.contents[localContentsOffset++] = (byte)(lineNumberNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)lineNumberNameIndex;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 6;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 1;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = (byte)(problemLine >> 8);
      this.contents[localContentsOffset++] = (byte)problemLine;
      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private int generateLocalVariableTableAttribute(int code_length, boolean methodDeclarationIsStatic, boolean isSynthetic) {
      int attributesNumber = 0;
      int localContentsOffset = this.contentsOffset;
      int numberOfEntries = 0;
      int localVariableNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.LocalVariableTableName);
      int maxOfEntries = 8 + 10 * (methodDeclarationIsStatic ? 0 : 1);

      int localVariableTableOffset;
      for(localVariableTableOffset = 0; localVariableTableOffset < this.codeStream.allLocalsCounter; ++localVariableTableOffset) {
         LocalVariableBinding localVariableBinding = this.codeStream.locals[localVariableTableOffset];
         maxOfEntries += 10 * localVariableBinding.initializationCount;
      }

      if (localContentsOffset + maxOfEntries >= this.contents.length) {
         this.resizeContents(maxOfEntries);
      }

      this.contents[localContentsOffset++] = (byte)(localVariableNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)localVariableNameIndex;
      localVariableTableOffset = localContentsOffset;
      localContentsOffset += 6;
      SourceTypeBinding declaringClassBinding = null;
      int descriptorIndex;
      int nameIndex;
      if (!methodDeclarationIsStatic && !isSynthetic) {
         ++numberOfEntries;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = (byte)(code_length >> 8);
         this.contents[localContentsOffset++] = (byte)code_length;
         nameIndex = this.constantPool.literalIndex(ConstantPool.This);
         this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
         this.contents[localContentsOffset++] = (byte)nameIndex;
         declaringClassBinding = (SourceTypeBinding)(this.codeStream.methodDeclaration != null ? this.codeStream.methodDeclaration.binding.declaringClass : this.codeStream.lambdaExpression.binding.declaringClass);
         descriptorIndex = this.constantPool.literalIndex(declaringClassBinding.signature());
         this.contents[localContentsOffset++] = (byte)(descriptorIndex >> 8);
         this.contents[localContentsOffset++] = (byte)descriptorIndex;
         this.contents[localContentsOffset++] = 0;
         this.contents[localContentsOffset++] = 0;
      }

      int genericLocalVariablesCounter = 0;
      LocalVariableBinding[] genericLocalVariables = null;
      int numberOfGenericEntries = 0;
      int value = 0;

      int i;
      int startPC;
      int endPC;
      int length;
      int resolvedPosition;
      for(int max = this.codeStream.allLocalsCounter; value < max; ++value) {
         LocalVariableBinding localVariable = this.codeStream.locals[value];
         i = localVariable.initializationCount;
         if (i != 0 && localVariable.declaration != null) {
            TypeBinding localVariableTypeBinding = localVariable.type;
            boolean isParameterizedType = localVariableTypeBinding.isParameterizedType() || localVariableTypeBinding.isTypeVariable();
            if (isParameterizedType) {
               if (genericLocalVariables == null) {
                  genericLocalVariables = new LocalVariableBinding[max];
               }

               genericLocalVariables[genericLocalVariablesCounter++] = localVariable;
            }

            for(startPC = 0; startPC < i; ++startPC) {
               endPC = localVariable.initializationPCs[startPC << 1];
               length = localVariable.initializationPCs[(startPC << 1) + 1];
               if (endPC != length) {
                  if (length == -1) {
                     localVariable.declaringScope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidAttribute, (Object)(new String(localVariable.name))), (ASTNode)localVariable.declaringScope.methodScope().referenceContext);
                  }

                  if (isParameterizedType) {
                     ++numberOfGenericEntries;
                  }

                  ++numberOfEntries;
                  this.contents[localContentsOffset++] = (byte)(endPC >> 8);
                  this.contents[localContentsOffset++] = (byte)endPC;
                  resolvedPosition = length - endPC;
                  this.contents[localContentsOffset++] = (byte)(resolvedPosition >> 8);
                  this.contents[localContentsOffset++] = (byte)resolvedPosition;
                  nameIndex = this.constantPool.literalIndex(localVariable.name);
                  this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
                  this.contents[localContentsOffset++] = (byte)nameIndex;
                  descriptorIndex = this.constantPool.literalIndex(localVariableTypeBinding.signature());
                  this.contents[localContentsOffset++] = (byte)(descriptorIndex >> 8);
                  this.contents[localContentsOffset++] = (byte)descriptorIndex;
                  int resolvedPosition = localVariable.resolvedPosition;
                  this.contents[localContentsOffset++] = (byte)(resolvedPosition >> 8);
                  this.contents[localContentsOffset++] = (byte)resolvedPosition;
               }
            }
         }
      }

      value = numberOfEntries * 10 + 2;
      this.contents[localVariableTableOffset++] = (byte)(value >> 24);
      this.contents[localVariableTableOffset++] = (byte)(value >> 16);
      this.contents[localVariableTableOffset++] = (byte)(value >> 8);
      this.contents[localVariableTableOffset++] = (byte)value;
      this.contents[localVariableTableOffset++] = (byte)(numberOfEntries >> 8);
      this.contents[localVariableTableOffset] = (byte)numberOfEntries;
      ++attributesNumber;
      boolean currentInstanceIsGeneric = !methodDeclarationIsStatic && declaringClassBinding != null && declaringClassBinding.typeVariables != Binding.NO_TYPE_VARIABLES;
      if (genericLocalVariablesCounter != 0 || currentInstanceIsGeneric) {
         numberOfGenericEntries += currentInstanceIsGeneric ? 1 : 0;
         maxOfEntries = 8 + numberOfGenericEntries * 10;
         if (localContentsOffset + maxOfEntries >= this.contents.length) {
            this.resizeContents(maxOfEntries);
         }

         int localVariableTypeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.LocalVariableTypeTableName);
         this.contents[localContentsOffset++] = (byte)(localVariableTypeNameIndex >> 8);
         this.contents[localContentsOffset++] = (byte)localVariableTypeNameIndex;
         value = numberOfGenericEntries * 10 + 2;
         this.contents[localContentsOffset++] = (byte)(value >> 24);
         this.contents[localContentsOffset++] = (byte)(value >> 16);
         this.contents[localContentsOffset++] = (byte)(value >> 8);
         this.contents[localContentsOffset++] = (byte)value;
         this.contents[localContentsOffset++] = (byte)(numberOfGenericEntries >> 8);
         this.contents[localContentsOffset++] = (byte)numberOfGenericEntries;
         if (currentInstanceIsGeneric) {
            this.contents[localContentsOffset++] = 0;
            this.contents[localContentsOffset++] = 0;
            this.contents[localContentsOffset++] = (byte)(code_length >> 8);
            this.contents[localContentsOffset++] = (byte)code_length;
            nameIndex = this.constantPool.literalIndex(ConstantPool.This);
            this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
            this.contents[localContentsOffset++] = (byte)nameIndex;
            descriptorIndex = this.constantPool.literalIndex(declaringClassBinding.genericTypeSignature());
            this.contents[localContentsOffset++] = (byte)(descriptorIndex >> 8);
            this.contents[localContentsOffset++] = (byte)descriptorIndex;
            this.contents[localContentsOffset++] = 0;
            this.contents[localContentsOffset++] = 0;
         }

         for(i = 0; i < genericLocalVariablesCounter; ++i) {
            LocalVariableBinding localVariable = genericLocalVariables[i];

            for(int j = 0; j < localVariable.initializationCount; ++j) {
               startPC = localVariable.initializationPCs[j << 1];
               endPC = localVariable.initializationPCs[(j << 1) + 1];
               if (startPC != endPC) {
                  this.contents[localContentsOffset++] = (byte)(startPC >> 8);
                  this.contents[localContentsOffset++] = (byte)startPC;
                  length = endPC - startPC;
                  this.contents[localContentsOffset++] = (byte)(length >> 8);
                  this.contents[localContentsOffset++] = (byte)length;
                  nameIndex = this.constantPool.literalIndex(localVariable.name);
                  this.contents[localContentsOffset++] = (byte)(nameIndex >> 8);
                  this.contents[localContentsOffset++] = (byte)nameIndex;
                  descriptorIndex = this.constantPool.literalIndex(localVariable.type.genericTypeSignature());
                  this.contents[localContentsOffset++] = (byte)(descriptorIndex >> 8);
                  this.contents[localContentsOffset++] = (byte)descriptorIndex;
                  resolvedPosition = localVariable.resolvedPosition;
                  this.contents[localContentsOffset++] = (byte)(resolvedPosition >> 8);
                  this.contents[localContentsOffset++] = (byte)resolvedPosition;
               }
            }
         }

         ++attributesNumber;
      }

      this.contentsOffset = localContentsOffset;
      return attributesNumber;
   }

   public int generateMethodInfoAttributes(MethodBinding methodBinding) {
      this.contentsOffset += 2;
      if (this.contentsOffset + 2 >= this.contents.length) {
         this.resizeContents(2);
      }

      int attributesNumber = 0;
      ReferenceBinding[] thrownsExceptions;
      if ((thrownsExceptions = methodBinding.thrownExceptions) != Binding.NO_EXCEPTIONS) {
         attributesNumber += this.generateExceptionsAttribute(thrownsExceptions);
      }

      if (methodBinding.isDeprecated()) {
         attributesNumber += this.generateDeprecatedAttribute();
      }

      if (this.targetJDK < 3211264L) {
         if (methodBinding.isSynthetic()) {
            attributesNumber += this.generateSyntheticAttribute();
         }

         if (methodBinding.isVarargs()) {
            attributesNumber += this.generateVarargsAttribute();
         }
      }

      char[] genericSignature = methodBinding.genericSignature();
      if (genericSignature != null) {
         attributesNumber += this.generateSignatureAttribute(genericSignature);
      }

      if (this.targetJDK >= 3145728L) {
         AbstractMethodDeclaration methodDeclaration = methodBinding.sourceMethod();
         if (methodBinding instanceof SyntheticMethodBinding) {
            SyntheticMethodBinding syntheticMethod = (SyntheticMethodBinding)methodBinding;
            if (syntheticMethod.purpose == 7 && CharOperation.equals(syntheticMethod.selector, syntheticMethod.targetMethod.selector)) {
               methodDeclaration = ((SyntheticMethodBinding)methodBinding).targetMethod.sourceMethod();
            }
         }

         Argument[] arguments;
         if (methodDeclaration != null) {
            Annotation[] annotations = methodDeclaration.annotations;
            if (annotations != null) {
               attributesNumber += this.generateRuntimeAnnotations(annotations, methodBinding.isConstructor() ? 1099511627776L : 274877906944L);
            }

            if ((methodBinding.tagBits & 1024L) != 0L) {
               arguments = methodDeclaration.arguments;
               if (arguments != null) {
                  attributesNumber += this.generateRuntimeAnnotationsForParameters(arguments);
               }
            }
         } else {
            LambdaExpression lambda = methodBinding.sourceLambda();
            if (lambda != null && (methodBinding.tagBits & 1024L) != 0L) {
               arguments = lambda.arguments();
               if (arguments != null) {
                  int parameterCount = methodBinding.parameters.length;
                  int argumentCount = arguments.length;
                  if (parameterCount > argumentCount) {
                     int redShift = parameterCount - argumentCount;
                     System.arraycopy(arguments, 0, arguments = new Argument[parameterCount], redShift, argumentCount);

                     for(int i = 0; i < redShift; ++i) {
                        arguments[i] = new Argument(CharOperation.NO_CHAR, 0L, (TypeReference)null, 0);
                     }
                  }

                  attributesNumber += this.generateRuntimeAnnotationsForParameters(arguments);
               }
            }
         }
      }

      if ((methodBinding.tagBits & 128L) != 0L) {
         this.missingTypes = methodBinding.collectMissingTypes(this.missingTypes);
      }

      return attributesNumber;
   }

   public int generateMethodInfoAttributes(MethodBinding methodBinding, AnnotationMethodDeclaration declaration) {
      int attributesNumber = this.generateMethodInfoAttributes(methodBinding);
      int attributeOffset = this.contentsOffset;
      if ((declaration.modifiers & 131072) != 0) {
         attributesNumber += this.generateAnnotationDefaultAttribute(declaration, attributeOffset);
      }

      return attributesNumber;
   }

   public void generateMethodInfoHeader(MethodBinding methodBinding) {
      this.generateMethodInfoHeader(methodBinding, methodBinding.modifiers);
   }

   public void generateMethodInfoHeader(MethodBinding methodBinding, int accessFlags) {
      ++this.methodCount;
      if (this.contentsOffset + 10 >= this.contents.length) {
         this.resizeContents(10);
      }

      if (this.targetJDK < 3211264L) {
         accessFlags &= -4225;
      }

      if ((methodBinding.tagBits & 512L) != 0L) {
         accessFlags &= -3;
      }

      this.contents[this.contentsOffset++] = (byte)(accessFlags >> 8);
      this.contents[this.contentsOffset++] = (byte)accessFlags;
      int nameIndex = this.constantPool.literalIndex(methodBinding.selector);
      this.contents[this.contentsOffset++] = (byte)(nameIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)nameIndex;
      int descriptorIndex = this.constantPool.literalIndex(methodBinding.signature(this));
      this.contents[this.contentsOffset++] = (byte)(descriptorIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)descriptorIndex;
   }

   public void addSyntheticDeserializeLambda(SyntheticMethodBinding methodBinding, SyntheticMethodBinding[] syntheticMethodBindings) {
      this.generateMethodInfoHeader(methodBinding);
      int methodAttributeOffset = this.contentsOffset;
      int attributeNumber = this.generateMethodInfoAttributes(methodBinding);
      int codeAttributeOffset = this.contentsOffset;
      ++attributeNumber;
      this.generateCodeAttributeHeader();
      this.codeStream.init(this);
      this.codeStream.generateSyntheticBodyForDeserializeLambda(methodBinding, syntheticMethodBindings);
      int code_length = this.codeStream.position;
      if (code_length > 65535) {
         this.referenceBinding.scope.problemReporter().bytecodeExceeds64KLimit(methodBinding, this.referenceBinding.sourceStart(), this.referenceBinding.sourceEnd());
      }

      this.completeCodeAttributeForSyntheticMethod(methodBinding, codeAttributeOffset, ((SourceTypeBinding)methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
      this.contents[methodAttributeOffset++] = (byte)(attributeNumber >> 8);
      this.contents[methodAttributeOffset] = (byte)attributeNumber;
   }

   public void generateMethodInfoHeaderForClinit() {
      ++this.methodCount;
      if (this.contentsOffset + 10 >= this.contents.length) {
         this.resizeContents(10);
      }

      this.contents[this.contentsOffset++] = 0;
      this.contents[this.contentsOffset++] = 8;
      int nameIndex = this.constantPool.literalIndex(ConstantPool.Clinit);
      this.contents[this.contentsOffset++] = (byte)(nameIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)nameIndex;
      int descriptorIndex = this.constantPool.literalIndex(ConstantPool.ClinitSignature);
      this.contents[this.contentsOffset++] = (byte)(descriptorIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)descriptorIndex;
      this.contents[this.contentsOffset++] = 0;
      this.contents[this.contentsOffset++] = 1;
   }

   public void generateMissingAbstractMethods(MethodDeclaration[] methodDeclarations, CompilationResult compilationResult) {
      if (methodDeclarations != null) {
         TypeDeclaration currentDeclaration = this.referenceBinding.scope.referenceContext;
         int typeDeclarationSourceStart = currentDeclaration.sourceStart();
         int typeDeclarationSourceEnd = currentDeclaration.sourceEnd();
         int i = 0;

         for(int max = methodDeclarations.length; i < max; ++i) {
            MethodDeclaration methodDeclaration = methodDeclarations[i];
            MethodBinding methodBinding = methodDeclaration.binding;
            String readableName = new String(methodBinding.readableName());
            CategorizedProblem[] problems = compilationResult.problems;
            int problemsCount = compilationResult.problemCount;

            for(int j = 0; j < problemsCount; ++j) {
               CategorizedProblem problem = problems[j];
               if (problem != null && problem.getID() == 67109264 && problem.getMessage().indexOf(readableName) != -1 && problem.getSourceStart() >= typeDeclarationSourceStart && problem.getSourceEnd() <= typeDeclarationSourceEnd) {
                  this.addMissingAbstractProblemMethod(methodDeclaration, methodBinding, problem, compilationResult);
               }
            }
         }
      }

   }

   private void generateMissingTypesAttribute() {
      int initialSize = this.missingTypes.size();
      int[] missingTypesIndexes = new int[initialSize];
      int numberOfMissingTypes = 0;
      if (initialSize > 1) {
         Collections.sort(this.missingTypes, new Comparator() {
            public int compare(Object o1, Object o2) {
               TypeBinding typeBinding1 = (TypeBinding)o1;
               TypeBinding typeBinding2 = (TypeBinding)o2;
               return CharOperation.compareTo(typeBinding1.constantPoolName(), typeBinding2.constantPoolName());
            }
         });
      }

      int previousIndex = 0;

      int attributeLength;
      int missingTypesNameIndex;
      for(attributeLength = 0; attributeLength < initialSize; ++attributeLength) {
         missingTypesNameIndex = this.constantPool.literalIndexForType((TypeBinding)this.missingTypes.get(attributeLength));
         if (previousIndex != missingTypesNameIndex) {
            previousIndex = missingTypesNameIndex;
            missingTypesIndexes[numberOfMissingTypes++] = missingTypesNameIndex;
         }
      }

      attributeLength = numberOfMissingTypes * 2 + 2;
      if (this.contentsOffset + attributeLength + 6 >= this.contents.length) {
         this.resizeContents(attributeLength + 6);
      }

      missingTypesNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.MissingTypesName);
      this.contents[this.contentsOffset++] = (byte)(missingTypesNameIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)missingTypesNameIndex;
      this.contents[this.contentsOffset++] = (byte)(attributeLength >> 24);
      this.contents[this.contentsOffset++] = (byte)(attributeLength >> 16);
      this.contents[this.contentsOffset++] = (byte)(attributeLength >> 8);
      this.contents[this.contentsOffset++] = (byte)attributeLength;
      this.contents[this.contentsOffset++] = (byte)(numberOfMissingTypes >> 8);
      this.contents[this.contentsOffset++] = (byte)numberOfMissingTypes;

      for(int i = 0; i < numberOfMissingTypes; ++i) {
         int missingTypeIndex = missingTypesIndexes[i];
         this.contents[this.contentsOffset++] = (byte)(missingTypeIndex >> 8);
         this.contents[this.contentsOffset++] = (byte)missingTypeIndex;
      }

   }

   private boolean jdk16packageInfoAnnotation(long annotationMask, long targetMask) {
      return this.targetJDK <= 3276800L && targetMask == 8796093022208L && annotationMask != 0L && (annotationMask & 8796093022208L) == 0L;
   }

   private int generateRuntimeAnnotations(Annotation[] annotations, long targetMask) {
      int attributesNumber = 0;
      int length = annotations.length;
      int visibleAnnotationsCounter = 0;
      int invisibleAnnotationsCounter = 0;

      int annotationAttributeOffset;
      for(annotationAttributeOffset = 0; annotationAttributeOffset < length; ++annotationAttributeOffset) {
         Annotation annotation;
         if ((annotation = annotations[annotationAttributeOffset].getPersistibleAnnotation()) != null) {
            long annotationMask = annotation.resolvedType != null ? annotation.resolvedType.getAnnotationTagBits() & 2332882164804222976L : 0L;
            if (annotationMask == 0L || (annotationMask & targetMask) != 0L || this.jdk16packageInfoAnnotation(annotationMask, targetMask)) {
               if (!annotation.isRuntimeInvisible() && !annotation.isRuntimeTypeInvisible()) {
                  if (annotation.isRuntimeVisible() || annotation.isRuntimeTypeVisible()) {
                     ++visibleAnnotationsCounter;
                  }
               } else {
                  ++invisibleAnnotationsCounter;
               }
            }
         }
      }

      annotationAttributeOffset = this.contentsOffset;
      int annotationsLengthOffset;
      int counter;
      int attributeLength;
      Annotation annotation;
      long annotationMask;
      int currentAnnotationOffset;
      int runtimeVisibleAnnotationsAttributeNameIndex;
      int attributeLengthOffset;
      if (invisibleAnnotationsCounter != 0) {
         if (this.contentsOffset + 10 >= this.contents.length) {
            this.resizeContents(10);
         }

         runtimeVisibleAnnotationsAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeInvisibleAnnotationsName);
         this.contents[this.contentsOffset++] = (byte)(runtimeVisibleAnnotationsAttributeNameIndex >> 8);
         this.contents[this.contentsOffset++] = (byte)runtimeVisibleAnnotationsAttributeNameIndex;
         attributeLengthOffset = this.contentsOffset;
         this.contentsOffset += 4;
         annotationsLengthOffset = this.contentsOffset;
         this.contentsOffset += 2;
         counter = 0;

         for(attributeLength = 0; attributeLength < length && invisibleAnnotationsCounter != 0; ++attributeLength) {
            if ((annotation = annotations[attributeLength].getPersistibleAnnotation()) != null) {
               annotationMask = annotation.resolvedType != null ? annotation.resolvedType.getAnnotationTagBits() & 2332882164804222976L : 0L;
               if ((annotationMask == 0L || (annotationMask & targetMask) != 0L || this.jdk16packageInfoAnnotation(annotationMask, targetMask)) && (annotation.isRuntimeInvisible() || annotation.isRuntimeTypeInvisible())) {
                  currentAnnotationOffset = this.contentsOffset;
                  this.generateAnnotation(annotation, currentAnnotationOffset);
                  --invisibleAnnotationsCounter;
                  if (this.contentsOffset != currentAnnotationOffset) {
                     ++counter;
                  }
               }
            }
         }

         if (counter != 0) {
            this.contents[annotationsLengthOffset++] = (byte)(counter >> 8);
            this.contents[annotationsLengthOffset++] = (byte)counter;
            attributeLength = this.contentsOffset - attributeLengthOffset - 4;
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 24);
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 16);
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 8);
            this.contents[attributeLengthOffset++] = (byte)attributeLength;
            ++attributesNumber;
         } else {
            this.contentsOffset = annotationAttributeOffset;
         }
      }

      annotationAttributeOffset = this.contentsOffset;
      if (visibleAnnotationsCounter != 0) {
         if (this.contentsOffset + 10 >= this.contents.length) {
            this.resizeContents(10);
         }

         runtimeVisibleAnnotationsAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeVisibleAnnotationsName);
         this.contents[this.contentsOffset++] = (byte)(runtimeVisibleAnnotationsAttributeNameIndex >> 8);
         this.contents[this.contentsOffset++] = (byte)runtimeVisibleAnnotationsAttributeNameIndex;
         attributeLengthOffset = this.contentsOffset;
         this.contentsOffset += 4;
         annotationsLengthOffset = this.contentsOffset;
         this.contentsOffset += 2;
         counter = 0;

         for(attributeLength = 0; attributeLength < length && visibleAnnotationsCounter != 0; ++attributeLength) {
            if ((annotation = annotations[attributeLength].getPersistibleAnnotation()) != null) {
               annotationMask = annotation.resolvedType != null ? annotation.resolvedType.getAnnotationTagBits() & 2332882164804222976L : 0L;
               if ((annotationMask == 0L || (annotationMask & targetMask) != 0L || this.jdk16packageInfoAnnotation(annotationMask, targetMask)) && (annotation.isRuntimeVisible() || annotation.isRuntimeTypeVisible())) {
                  --visibleAnnotationsCounter;
                  currentAnnotationOffset = this.contentsOffset;
                  this.generateAnnotation(annotation, currentAnnotationOffset);
                  if (this.contentsOffset != currentAnnotationOffset) {
                     ++counter;
                  }
               }
            }
         }

         if (counter != 0) {
            this.contents[annotationsLengthOffset++] = (byte)(counter >> 8);
            this.contents[annotationsLengthOffset++] = (byte)counter;
            attributeLength = this.contentsOffset - attributeLengthOffset - 4;
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 24);
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 16);
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 8);
            this.contents[attributeLengthOffset++] = (byte)attributeLength;
            ++attributesNumber;
         } else {
            this.contentsOffset = annotationAttributeOffset;
         }
      }

      return attributesNumber;
   }

   private int generateRuntimeAnnotationsForParameters(Argument[] arguments) {
      int argumentsLength = arguments.length;
      int invisibleParametersAnnotationsCounter = 0;
      int visibleParametersAnnotationsCounter = 0;
      int[][] annotationsCounters = new int[argumentsLength][2];

      int i;
      int attributeNameIndex;
      int attributeLengthOffset;
      for(i = 0; i < argumentsLength; ++i) {
         Argument argument = arguments[i];
         Annotation[] annotations = argument.annotations;
         if (annotations != null) {
            attributeNameIndex = 0;

            for(attributeLengthOffset = annotations.length; attributeNameIndex < attributeLengthOffset; ++attributeNameIndex) {
               Annotation annotation;
               if ((annotation = annotations[attributeNameIndex].getPersistibleAnnotation()) != null) {
                  long annotationMask = annotation.resolvedType != null ? annotation.resolvedType.getAnnotationTagBits() & 2332882164804222976L : 0L;
                  if (annotationMask == 0L || (annotationMask & 549755813888L) != 0L) {
                     int var10002;
                     if (annotation.isRuntimeInvisible()) {
                        var10002 = annotationsCounters[i][1]++;
                        ++invisibleParametersAnnotationsCounter;
                     } else if (annotation.isRuntimeVisible()) {
                        var10002 = annotationsCounters[i][0]++;
                        ++visibleParametersAnnotationsCounter;
                     }
                  }
               }
            }
         }
      }

      i = 0;
      int annotationAttributeOffset = this.contentsOffset;
      int visibleAnnotationsOffset;
      int counter;
      Argument argument;
      Annotation[] annotations;
      int j;
      int max;
      Annotation annotation;
      long annotationMask;
      int currentAnnotationOffset;
      int globalCounter;
      int i;
      int numberOfVisibleAnnotations;
      if (invisibleParametersAnnotationsCounter != 0) {
         globalCounter = 0;
         if (this.contentsOffset + 7 >= this.contents.length) {
            this.resizeContents(7);
         }

         attributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeInvisibleParameterAnnotationsName);
         this.contents[this.contentsOffset++] = (byte)(attributeNameIndex >> 8);
         this.contents[this.contentsOffset++] = (byte)attributeNameIndex;
         attributeLengthOffset = this.contentsOffset;
         this.contentsOffset += 4;
         this.contents[this.contentsOffset++] = (byte)argumentsLength;

         for(i = 0; i < argumentsLength; ++i) {
            if (this.contentsOffset + 2 >= this.contents.length) {
               this.resizeContents(2);
            }

            if (invisibleParametersAnnotationsCounter == 0) {
               this.contents[this.contentsOffset++] = 0;
               this.contents[this.contentsOffset++] = 0;
            } else {
               numberOfVisibleAnnotations = annotationsCounters[i][1];
               visibleAnnotationsOffset = this.contentsOffset;
               this.contentsOffset += 2;
               counter = 0;
               if (numberOfVisibleAnnotations != 0) {
                  argument = arguments[i];
                  annotations = argument.annotations;
                  j = 0;

                  for(max = annotations.length; j < max; ++j) {
                     if ((annotation = annotations[j].getPersistibleAnnotation()) != null) {
                        annotationMask = annotation.resolvedType != null ? annotation.resolvedType.getAnnotationTagBits() & 2332882164804222976L : 0L;
                        if ((annotationMask == 0L || (annotationMask & 549755813888L) != 0L) && annotation.isRuntimeInvisible()) {
                           currentAnnotationOffset = this.contentsOffset;
                           this.generateAnnotation(annotation, currentAnnotationOffset);
                           if (this.contentsOffset != currentAnnotationOffset) {
                              ++counter;
                              ++globalCounter;
                           }

                           --invisibleParametersAnnotationsCounter;
                        }
                     }
                  }
               }

               this.contents[visibleAnnotationsOffset++] = (byte)(counter >> 8);
               this.contents[visibleAnnotationsOffset] = (byte)counter;
            }
         }

         if (globalCounter != 0) {
            i = this.contentsOffset - attributeLengthOffset - 4;
            this.contents[attributeLengthOffset++] = (byte)(i >> 24);
            this.contents[attributeLengthOffset++] = (byte)(i >> 16);
            this.contents[attributeLengthOffset++] = (byte)(i >> 8);
            this.contents[attributeLengthOffset++] = (byte)i;
            ++i;
         } else {
            this.contentsOffset = annotationAttributeOffset;
         }
      }

      if (visibleParametersAnnotationsCounter != 0) {
         globalCounter = 0;
         if (this.contentsOffset + 7 >= this.contents.length) {
            this.resizeContents(7);
         }

         attributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeVisibleParameterAnnotationsName);
         this.contents[this.contentsOffset++] = (byte)(attributeNameIndex >> 8);
         this.contents[this.contentsOffset++] = (byte)attributeNameIndex;
         attributeLengthOffset = this.contentsOffset;
         this.contentsOffset += 4;
         this.contents[this.contentsOffset++] = (byte)argumentsLength;

         for(i = 0; i < argumentsLength; ++i) {
            if (this.contentsOffset + 2 >= this.contents.length) {
               this.resizeContents(2);
            }

            if (visibleParametersAnnotationsCounter == 0) {
               this.contents[this.contentsOffset++] = 0;
               this.contents[this.contentsOffset++] = 0;
            } else {
               numberOfVisibleAnnotations = annotationsCounters[i][0];
               visibleAnnotationsOffset = this.contentsOffset;
               this.contentsOffset += 2;
               counter = 0;
               if (numberOfVisibleAnnotations != 0) {
                  argument = arguments[i];
                  annotations = argument.annotations;
                  j = 0;

                  for(max = annotations.length; j < max; ++j) {
                     if ((annotation = annotations[j].getPersistibleAnnotation()) != null) {
                        annotationMask = annotation.resolvedType != null ? annotation.resolvedType.getAnnotationTagBits() & 2332882164804222976L : 0L;
                        if ((annotationMask == 0L || (annotationMask & 549755813888L) != 0L) && annotation.isRuntimeVisible()) {
                           currentAnnotationOffset = this.contentsOffset;
                           this.generateAnnotation(annotation, currentAnnotationOffset);
                           if (this.contentsOffset != currentAnnotationOffset) {
                              ++counter;
                              ++globalCounter;
                           }

                           --visibleParametersAnnotationsCounter;
                        }
                     }
                  }
               }

               this.contents[visibleAnnotationsOffset++] = (byte)(counter >> 8);
               this.contents[visibleAnnotationsOffset] = (byte)counter;
            }
         }

         if (globalCounter != 0) {
            i = this.contentsOffset - attributeLengthOffset - 4;
            this.contents[attributeLengthOffset++] = (byte)(i >> 24);
            this.contents[attributeLengthOffset++] = (byte)(i >> 16);
            this.contents[attributeLengthOffset++] = (byte)(i >> 8);
            this.contents[attributeLengthOffset++] = (byte)i;
            ++i;
         } else {
            this.contentsOffset = annotationAttributeOffset;
         }
      }

      return i;
   }

   private int generateRuntimeTypeAnnotations(AnnotationContext[] annotationContexts, int visibleTypeAnnotationsNumber, int invisibleTypeAnnotationsNumber) {
      int attributesNumber = 0;
      int length = annotationContexts.length;
      int visibleTypeAnnotationsCounter = visibleTypeAnnotationsNumber;
      int invisibleTypeAnnotationsCounter = invisibleTypeAnnotationsNumber;
      int annotationAttributeOffset = this.contentsOffset;
      int runtimeVisibleAnnotationsAttributeNameIndex;
      int attributeLengthOffset;
      int annotationsLengthOffset;
      int counter;
      int attributeLength;
      AnnotationContext annotationContext;
      int currentAnnotationOffset;
      if (invisibleTypeAnnotationsNumber != 0) {
         if (this.contentsOffset + 10 >= this.contents.length) {
            this.resizeContents(10);
         }

         runtimeVisibleAnnotationsAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeInvisibleTypeAnnotationsName);
         this.contents[this.contentsOffset++] = (byte)(runtimeVisibleAnnotationsAttributeNameIndex >> 8);
         this.contents[this.contentsOffset++] = (byte)runtimeVisibleAnnotationsAttributeNameIndex;
         attributeLengthOffset = this.contentsOffset;
         this.contentsOffset += 4;
         annotationsLengthOffset = this.contentsOffset;
         this.contentsOffset += 2;
         counter = 0;

         for(attributeLength = 0; attributeLength < length && invisibleTypeAnnotationsCounter != 0; ++attributeLength) {
            annotationContext = annotationContexts[attributeLength];
            if ((annotationContext.visibility & 2) != 0) {
               currentAnnotationOffset = this.contentsOffset;
               this.generateTypeAnnotation(annotationContext, currentAnnotationOffset);
               --invisibleTypeAnnotationsCounter;
               if (this.contentsOffset != currentAnnotationOffset) {
                  ++counter;
               }
            }
         }

         if (counter != 0) {
            this.contents[annotationsLengthOffset++] = (byte)(counter >> 8);
            this.contents[annotationsLengthOffset++] = (byte)counter;
            attributeLength = this.contentsOffset - attributeLengthOffset - 4;
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 24);
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 16);
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 8);
            this.contents[attributeLengthOffset++] = (byte)attributeLength;
            ++attributesNumber;
         } else {
            this.contentsOffset = annotationAttributeOffset;
         }
      }

      annotationAttributeOffset = this.contentsOffset;
      if (visibleTypeAnnotationsNumber != 0) {
         if (this.contentsOffset + 10 >= this.contents.length) {
            this.resizeContents(10);
         }

         runtimeVisibleAnnotationsAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeVisibleTypeAnnotationsName);
         this.contents[this.contentsOffset++] = (byte)(runtimeVisibleAnnotationsAttributeNameIndex >> 8);
         this.contents[this.contentsOffset++] = (byte)runtimeVisibleAnnotationsAttributeNameIndex;
         attributeLengthOffset = this.contentsOffset;
         this.contentsOffset += 4;
         annotationsLengthOffset = this.contentsOffset;
         this.contentsOffset += 2;
         counter = 0;

         for(attributeLength = 0; attributeLength < length && visibleTypeAnnotationsCounter != 0; ++attributeLength) {
            annotationContext = annotationContexts[attributeLength];
            if ((annotationContext.visibility & 1) != 0) {
               --visibleTypeAnnotationsCounter;
               currentAnnotationOffset = this.contentsOffset;
               this.generateTypeAnnotation(annotationContext, currentAnnotationOffset);
               if (this.contentsOffset != currentAnnotationOffset) {
                  ++counter;
               }
            }
         }

         if (counter != 0) {
            this.contents[annotationsLengthOffset++] = (byte)(counter >> 8);
            this.contents[annotationsLengthOffset++] = (byte)counter;
            attributeLength = this.contentsOffset - attributeLengthOffset - 4;
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 24);
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 16);
            this.contents[attributeLengthOffset++] = (byte)(attributeLength >> 8);
            this.contents[attributeLengthOffset++] = (byte)attributeLength;
            ++attributesNumber;
         } else {
            this.contentsOffset = annotationAttributeOffset;
         }
      }

      return attributesNumber;
   }

   private int generateMethodParameters(MethodBinding binding) {
      if (binding.sourceLambda() != null) {
         return 0;
      } else {
         int initialContentsOffset = this.contentsOffset;
         int length = 0;
         AbstractMethodDeclaration methodDeclaration = binding.sourceMethod();
         boolean isConstructor = binding.isConstructor();
         TypeBinding[] targetParameters = binding.parameters;
         ReferenceBinding declaringClass = binding.declaringClass;
         if (declaringClass.isEnum()) {
            if (isConstructor) {
               length = this.writeArgumentName(ConstantPool.EnumName, 4096, length);
               length = this.writeArgumentName(ConstantPool.EnumOrdinal, 4096, length);
            } else if (binding instanceof SyntheticMethodBinding && CharOperation.equals(ConstantPool.ValueOf, binding.selector)) {
               length = this.writeArgumentName(ConstantPool.Name, 32768, length);
               targetParameters = Binding.NO_PARAMETERS;
            }
         }

         boolean needSynthetics = isConstructor && declaringClass.isNestedType();
         if (needSynthetics) {
            boolean anonymousWithLocalSuper = declaringClass.isAnonymousType() && declaringClass.superclass().isLocalType();
            boolean anonymousWithNestedSuper = declaringClass.isAnonymousType() && declaringClass.superclass().isNestedType();
            boolean isImplicitlyDeclared = (!declaringClass.isPrivate() || declaringClass.isAnonymousType()) && !anonymousWithLocalSuper;
            ReferenceBinding[] syntheticArgumentTypes = declaringClass.syntheticEnclosingInstanceTypes();
            if (syntheticArgumentTypes != null) {
               int i = 0;

               for(int count = syntheticArgumentTypes.length; i < count; ++i) {
                  boolean couldForwardToMandated = anonymousWithNestedSuper ? declaringClass.superclass().enclosingType().equals(syntheticArgumentTypes[i]) : true;
                  int modifier = couldForwardToMandated && isImplicitlyDeclared ? '耀' : 4096;
                  char[] name = CharOperation.concat(TypeConstants.SYNTHETIC_ENCLOSING_INSTANCE_PREFIX, String.valueOf(i).toCharArray());
                  length = this.writeArgumentName(name, modifier | 16, length);
               }
            }

            if (binding instanceof SyntheticMethodBinding) {
               targetParameters = ((SyntheticMethodBinding)binding).targetMethod.parameters;
               methodDeclaration = ((SyntheticMethodBinding)binding).targetMethod.sourceMethod();
            }
         }

         int i;
         int i;
         int extraLength;
         if (targetParameters != Binding.NO_PARAMETERS) {
            Argument[] arguments = null;
            if (methodDeclaration != null && methodDeclaration.arguments != null) {
               arguments = methodDeclaration.arguments;
            }

            i = 0;
            i = targetParameters.length;

            for(extraLength = arguments != null ? arguments.length : 0; i < i; ++i) {
               if (extraLength > i && arguments[i] != null) {
                  Argument argument = arguments[i];
                  length = this.writeArgumentName(argument.name, argument.binding.modifiers, length);
               } else {
                  length = this.writeArgumentName((char[])null, 4096, length);
               }
            }
         }

         if (needSynthetics) {
            SyntheticArgumentBinding[] syntheticOuterArguments = declaringClass.syntheticOuterLocalVariables();
            i = syntheticOuterArguments == null ? 0 : syntheticOuterArguments.length;

            for(i = 0; i < i; ++i) {
               length = this.writeArgumentName(syntheticOuterArguments[i].name, syntheticOuterArguments[i].modifiers | 4096, length);
            }

            i = targetParameters.length;

            for(extraLength = binding.parameters.length; i < extraLength; ++i) {
               TypeBinding parameter = binding.parameters[i];
               length = this.writeArgumentName(parameter.constantPoolName(), 4096, length);
            }
         }

         if (length > 0) {
            int attributeLength = 1 + 4 * length;
            if (this.contentsOffset + 6 + attributeLength >= this.contents.length) {
               this.resizeContents(6 + attributeLength);
            }

            i = this.constantPool.literalIndex(AttributeNamesConstants.MethodParametersName);
            this.contents[initialContentsOffset++] = (byte)(i >> 8);
            this.contents[initialContentsOffset++] = (byte)i;
            this.contents[initialContentsOffset++] = (byte)(attributeLength >> 24);
            this.contents[initialContentsOffset++] = (byte)(attributeLength >> 16);
            this.contents[initialContentsOffset++] = (byte)(attributeLength >> 8);
            this.contents[initialContentsOffset++] = (byte)attributeLength;
            this.contents[initialContentsOffset++] = (byte)length;
            return 1;
         } else {
            return 0;
         }
      }
   }

   private int writeArgumentName(char[] name, int modifiers, int oldLength) {
      int ensureRoomForBytes = 4;
      if (oldLength == 0) {
         ensureRoomForBytes += 7;
         this.contentsOffset += 7;
      }

      if (this.contentsOffset + ensureRoomForBytes > this.contents.length) {
         this.resizeContents(ensureRoomForBytes);
      }

      int parameterNameIndex = name == null ? 0 : this.constantPool.literalIndex(name);
      this.contents[this.contentsOffset++] = (byte)(parameterNameIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)parameterNameIndex;
      int flags = modifiers & '逐';
      this.contents[this.contentsOffset++] = (byte)(flags >> 8);
      this.contents[this.contentsOffset++] = (byte)flags;
      return oldLength + 1;
   }

   private int generateSignatureAttribute(char[] genericSignature) {
      int localContentsOffset = this.contentsOffset;
      if (localContentsOffset + 8 >= this.contents.length) {
         this.resizeContents(8);
      }

      int signatureAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.SignatureName);
      this.contents[localContentsOffset++] = (byte)(signatureAttributeNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)signatureAttributeNameIndex;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 2;
      int signatureIndex = this.constantPool.literalIndex(genericSignature);
      this.contents[localContentsOffset++] = (byte)(signatureIndex >> 8);
      this.contents[localContentsOffset++] = (byte)signatureIndex;
      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private int generateSourceAttribute(String fullFileName) {
      int localContentsOffset = this.contentsOffset;
      if (localContentsOffset + 8 >= this.contents.length) {
         this.resizeContents(8);
      }

      int sourceAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.SourceName);
      this.contents[localContentsOffset++] = (byte)(sourceAttributeNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)sourceAttributeNameIndex;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 2;
      int fileNameIndex = this.constantPool.literalIndex(fullFileName.toCharArray());
      this.contents[localContentsOffset++] = (byte)(fileNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)fileNameIndex;
      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private int generateStackMapAttribute(MethodBinding methodBinding, int code_length, int codeAttributeOffset, int max_locals, boolean isClinit) {
      int attributesNumber = 0;
      int localContentsOffset = this.contentsOffset;
      StackMapFrameCodeStream stackMapFrameCodeStream = (StackMapFrameCodeStream)this.codeStream;
      stackMapFrameCodeStream.removeFramePosition(code_length);
      if (stackMapFrameCodeStream.hasFramePositions()) {
         Map frames = new HashMap();
         List realFrames = this.traverse(isClinit ? null : methodBinding, max_locals, this.contents, codeAttributeOffset + 14, code_length, frames, isClinit);
         int numberOfFrames = realFrames.size();
         if (numberOfFrames > 1) {
            int stackMapTableAttributeOffset = localContentsOffset;
            if (localContentsOffset + 8 >= this.contents.length) {
               this.resizeContents(8);
            }

            int stackMapAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.StackMapName);
            this.contents[localContentsOffset++] = (byte)(stackMapAttributeNameIndex >> 8);
            this.contents[localContentsOffset++] = (byte)stackMapAttributeNameIndex;
            int stackMapAttributeLengthOffset = localContentsOffset;
            localContentsOffset += 4;
            if (localContentsOffset + 4 >= this.contents.length) {
               this.resizeContents(4);
            }

            int numberOfFramesOffset = localContentsOffset;
            localContentsOffset += 2;
            if (localContentsOffset + 2 >= this.contents.length) {
               this.resizeContents(2);
            }

            StackMapFrame currentFrame = (StackMapFrame)realFrames.get(0);

            int attributeLength;
            for(attributeLength = 1; attributeLength < numberOfFrames; ++attributeLength) {
               currentFrame = (StackMapFrame)realFrames.get(attributeLength);
               int frameOffset = currentFrame.pc;
               if (localContentsOffset + 5 >= this.contents.length) {
                  this.resizeContents(5);
               }

               this.contents[localContentsOffset++] = (byte)(frameOffset >> 8);
               this.contents[localContentsOffset++] = (byte)frameOffset;
               int numberOfLocalOffset = localContentsOffset;
               localContentsOffset += 2;
               int numberOfLocalEntries = 0;
               int numberOfLocals = currentFrame.getNumberOfLocals();
               int numberOfEntries = 0;
               int localsLength = currentFrame.locals == null ? 0 : currentFrame.locals.length;

               int numberOfStackItems;
               int offset;
               for(numberOfStackItems = 0; numberOfStackItems < localsLength && numberOfLocalEntries < numberOfLocals; ++numberOfStackItems) {
                  if (localContentsOffset + 3 >= this.contents.length) {
                     this.resizeContents(3);
                  }

                  VerificationTypeInfo info = currentFrame.locals[numberOfStackItems];
                  if (info == null) {
                     this.contents[localContentsOffset++] = 0;
                  } else {
                     label77:
                     switch (info.id()) {
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 10:
                           this.contents[localContentsOffset++] = 1;
                           break;
                        case 6:
                        case 11:
                        default:
                           this.contents[localContentsOffset++] = (byte)info.tag;
                           switch (info.tag) {
                              case 7:
                                 offset = this.constantPool.literalIndexForType(info.constantPoolName());
                                 this.contents[localContentsOffset++] = (byte)(offset >> 8);
                                 this.contents[localContentsOffset++] = (byte)offset;
                                 break label77;
                              case 8:
                                 int offset = info.offset;
                                 this.contents[localContentsOffset++] = (byte)(offset >> 8);
                                 this.contents[localContentsOffset++] = (byte)offset;
                              default:
                                 break label77;
                           }
                        case 7:
                           this.contents[localContentsOffset++] = 4;
                           ++numberOfStackItems;
                           break;
                        case 8:
                           this.contents[localContentsOffset++] = 3;
                           ++numberOfStackItems;
                           break;
                        case 9:
                           this.contents[localContentsOffset++] = 2;
                           break;
                        case 12:
                           this.contents[localContentsOffset++] = 5;
                     }

                     ++numberOfLocalEntries;
                  }

                  ++numberOfEntries;
               }

               if (localContentsOffset + 4 >= this.contents.length) {
                  this.resizeContents(4);
               }

               this.contents[numberOfLocalOffset++] = (byte)(numberOfEntries >> 8);
               this.contents[numberOfLocalOffset] = (byte)numberOfEntries;
               numberOfStackItems = currentFrame.numberOfStackItems;
               this.contents[localContentsOffset++] = (byte)(numberOfStackItems >> 8);
               this.contents[localContentsOffset++] = (byte)numberOfStackItems;

               for(int i = 0; i < numberOfStackItems; ++i) {
                  if (localContentsOffset + 3 >= this.contents.length) {
                     this.resizeContents(3);
                  }

                  VerificationTypeInfo info = currentFrame.stackItems[i];
                  if (info == null) {
                     this.contents[localContentsOffset++] = 0;
                  } else {
                     switch (info.id()) {
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 10:
                           this.contents[localContentsOffset++] = 1;
                           break;
                        case 6:
                        case 11:
                        default:
                           this.contents[localContentsOffset++] = (byte)info.tag;
                           switch (info.tag) {
                              case 7:
                                 int indexForType = this.constantPool.literalIndexForType(info.constantPoolName());
                                 this.contents[localContentsOffset++] = (byte)(indexForType >> 8);
                                 this.contents[localContentsOffset++] = (byte)indexForType;
                                 continue;
                              case 8:
                                 offset = info.offset;
                                 this.contents[localContentsOffset++] = (byte)(offset >> 8);
                                 this.contents[localContentsOffset++] = (byte)offset;
                              default:
                                 continue;
                           }
                        case 7:
                           this.contents[localContentsOffset++] = 4;
                           break;
                        case 8:
                           this.contents[localContentsOffset++] = 3;
                           break;
                        case 9:
                           this.contents[localContentsOffset++] = 2;
                           break;
                        case 12:
                           this.contents[localContentsOffset++] = 5;
                     }
                  }
               }
            }

            --numberOfFrames;
            if (numberOfFrames != 0) {
               this.contents[numberOfFramesOffset++] = (byte)(numberOfFrames >> 8);
               this.contents[numberOfFramesOffset] = (byte)numberOfFrames;
               attributeLength = localContentsOffset - stackMapAttributeLengthOffset - 4;
               this.contents[stackMapAttributeLengthOffset++] = (byte)(attributeLength >> 24);
               this.contents[stackMapAttributeLengthOffset++] = (byte)(attributeLength >> 16);
               this.contents[stackMapAttributeLengthOffset++] = (byte)(attributeLength >> 8);
               this.contents[stackMapAttributeLengthOffset] = (byte)attributeLength;
               ++attributesNumber;
            } else {
               localContentsOffset = stackMapTableAttributeOffset;
            }
         }
      }

      this.contentsOffset = localContentsOffset;
      return attributesNumber;
   }

   private int generateStackMapTableAttribute(MethodBinding methodBinding, int code_length, int codeAttributeOffset, int max_locals, boolean isClinit) {
      int attributesNumber = 0;
      int localContentsOffset = this.contentsOffset;
      StackMapFrameCodeStream stackMapFrameCodeStream = (StackMapFrameCodeStream)this.codeStream;
      stackMapFrameCodeStream.removeFramePosition(code_length);
      if (stackMapFrameCodeStream.hasFramePositions()) {
         Map frames = new HashMap();
         List realFrames = this.traverse(isClinit ? null : methodBinding, max_locals, this.contents, codeAttributeOffset + 14, code_length, frames, isClinit);
         int numberOfFrames = realFrames.size();
         if (numberOfFrames > 1) {
            int stackMapTableAttributeOffset = localContentsOffset;
            if (localContentsOffset + 8 >= this.contents.length) {
               this.resizeContents(8);
            }

            int stackMapTableAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.StackMapTableName);
            this.contents[localContentsOffset++] = (byte)(stackMapTableAttributeNameIndex >> 8);
            this.contents[localContentsOffset++] = (byte)stackMapTableAttributeNameIndex;
            int stackMapTableAttributeLengthOffset = localContentsOffset;
            localContentsOffset += 4;
            if (localContentsOffset + 4 >= this.contents.length) {
               this.resizeContents(4);
            }

            int numberOfFramesOffset = localContentsOffset;
            localContentsOffset += 2;
            if (localContentsOffset + 2 >= this.contents.length) {
               this.resizeContents(2);
            }

            StackMapFrame currentFrame = (StackMapFrame)realFrames.get(0);
            StackMapFrame prevFrame = null;

            int attributeLength;
            label208:
            for(attributeLength = 1; attributeLength < numberOfFrames; ++attributeLength) {
               prevFrame = currentFrame;
               currentFrame = (StackMapFrame)realFrames.get(attributeLength);
               int offsetDelta = currentFrame.getOffsetDelta(prevFrame);
               int numberOfDifferentLocals;
               int numberOfLocals;
               VerificationTypeInfo info;
               int tag;
               int offset;
               int indexForType;
               int i;
               switch (currentFrame.getFrameType(prevFrame)) {
                  case 0:
                     if (localContentsOffset + 1 >= this.contents.length) {
                        this.resizeContents(1);
                     }

                     this.contents[localContentsOffset++] = (byte)offsetDelta;
                     break;
                  case 1:
                     if (localContentsOffset + 3 >= this.contents.length) {
                        this.resizeContents(3);
                     }

                     numberOfDifferentLocals = -currentFrame.numberOfDifferentLocals(prevFrame);
                     this.contents[localContentsOffset++] = (byte)(251 - numberOfDifferentLocals);
                     this.contents[localContentsOffset++] = (byte)(offsetDelta >> 8);
                     this.contents[localContentsOffset++] = (byte)offsetDelta;
                     break;
                  case 2:
                     if (localContentsOffset + 3 >= this.contents.length) {
                        this.resizeContents(3);
                     }

                     numberOfDifferentLocals = currentFrame.numberOfDifferentLocals(prevFrame);
                     this.contents[localContentsOffset++] = (byte)(251 + numberOfDifferentLocals);
                     this.contents[localContentsOffset++] = (byte)(offsetDelta >> 8);
                     this.contents[localContentsOffset++] = (byte)offsetDelta;
                     int index = currentFrame.getIndexOfDifferentLocals(numberOfDifferentLocals);
                     numberOfLocals = currentFrame.getNumberOfLocals();
                     i = index;

                     while(true) {
                        if (i >= currentFrame.locals.length || numberOfDifferentLocals <= 0) {
                           continue label208;
                        }

                        if (localContentsOffset + 6 >= this.contents.length) {
                           this.resizeContents(6);
                        }

                        VerificationTypeInfo info = currentFrame.locals[i];
                        if (info == null) {
                           this.contents[localContentsOffset++] = 0;
                        } else {
                           label169:
                           switch (info.id()) {
                              case 2:
                              case 3:
                              case 4:
                              case 5:
                              case 10:
                                 this.contents[localContentsOffset++] = 1;
                                 break;
                              case 6:
                              case 11:
                              default:
                                 this.contents[localContentsOffset++] = (byte)info.tag;
                                 switch (info.tag) {
                                    case 7:
                                       indexForType = this.constantPool.literalIndexForType(info.constantPoolName());
                                       this.contents[localContentsOffset++] = (byte)(indexForType >> 8);
                                       this.contents[localContentsOffset++] = (byte)indexForType;
                                       break label169;
                                    case 8:
                                       offset = info.offset;
                                       this.contents[localContentsOffset++] = (byte)(offset >> 8);
                                       this.contents[localContentsOffset++] = (byte)offset;
                                    default:
                                       break label169;
                                 }
                              case 7:
                                 this.contents[localContentsOffset++] = 4;
                                 ++i;
                                 break;
                              case 8:
                                 this.contents[localContentsOffset++] = 3;
                                 ++i;
                                 break;
                              case 9:
                                 this.contents[localContentsOffset++] = 2;
                                 break;
                              case 12:
                                 this.contents[localContentsOffset++] = 5;
                           }

                           --numberOfDifferentLocals;
                        }

                        ++i;
                     }
                  case 3:
                     if (localContentsOffset + 3 >= this.contents.length) {
                        this.resizeContents(3);
                     }

                     this.contents[localContentsOffset++] = -5;
                     this.contents[localContentsOffset++] = (byte)(offsetDelta >> 8);
                     this.contents[localContentsOffset++] = (byte)offsetDelta;
                     break;
                  case 4:
                  default:
                     if (localContentsOffset + 5 >= this.contents.length) {
                        this.resizeContents(5);
                     }

                     this.contents[localContentsOffset++] = -1;
                     this.contents[localContentsOffset++] = (byte)(offsetDelta >> 8);
                     this.contents[localContentsOffset++] = (byte)offsetDelta;
                     i = localContentsOffset;
                     localContentsOffset += 2;
                     tag = 0;
                     numberOfLocals = currentFrame.getNumberOfLocals();
                     offset = 0;
                     indexForType = currentFrame.locals == null ? 0 : currentFrame.locals.length;

                     int numberOfStackItems;
                     int offset;
                     for(numberOfStackItems = 0; numberOfStackItems < indexForType && tag < numberOfLocals; ++numberOfStackItems) {
                        if (localContentsOffset + 3 >= this.contents.length) {
                           this.resizeContents(3);
                        }

                        VerificationTypeInfo info = currentFrame.locals[numberOfStackItems];
                        if (info == null) {
                           this.contents[localContentsOffset++] = 0;
                        } else {
                           label136:
                           switch (info.id()) {
                              case 2:
                              case 3:
                              case 4:
                              case 5:
                              case 10:
                                 this.contents[localContentsOffset++] = 1;
                                 break;
                              case 6:
                              case 11:
                              default:
                                 this.contents[localContentsOffset++] = (byte)info.tag;
                                 switch (info.tag) {
                                    case 7:
                                       offset = this.constantPool.literalIndexForType(info.constantPoolName());
                                       this.contents[localContentsOffset++] = (byte)(offset >> 8);
                                       this.contents[localContentsOffset++] = (byte)offset;
                                       break label136;
                                    case 8:
                                       int offset = info.offset;
                                       this.contents[localContentsOffset++] = (byte)(offset >> 8);
                                       this.contents[localContentsOffset++] = (byte)offset;
                                    default:
                                       break label136;
                                 }
                              case 7:
                                 this.contents[localContentsOffset++] = 4;
                                 ++numberOfStackItems;
                                 break;
                              case 8:
                                 this.contents[localContentsOffset++] = 3;
                                 ++numberOfStackItems;
                                 break;
                              case 9:
                                 this.contents[localContentsOffset++] = 2;
                                 break;
                              case 12:
                                 this.contents[localContentsOffset++] = 5;
                           }

                           ++tag;
                        }

                        ++offset;
                     }

                     if (localContentsOffset + 4 >= this.contents.length) {
                        this.resizeContents(4);
                     }

                     this.contents[i++] = (byte)(offset >> 8);
                     this.contents[i] = (byte)offset;
                     numberOfStackItems = currentFrame.numberOfStackItems;
                     this.contents[localContentsOffset++] = (byte)(numberOfStackItems >> 8);
                     this.contents[localContentsOffset++] = (byte)numberOfStackItems;

                     for(int i = 0; i < numberOfStackItems; ++i) {
                        if (localContentsOffset + 3 >= this.contents.length) {
                           this.resizeContents(3);
                        }

                        VerificationTypeInfo info = currentFrame.stackItems[i];
                        if (info == null) {
                           this.contents[localContentsOffset++] = 0;
                        } else {
                           switch (info.id()) {
                              case 2:
                              case 3:
                              case 4:
                              case 5:
                              case 10:
                                 this.contents[localContentsOffset++] = 1;
                                 break;
                              case 6:
                              case 11:
                              default:
                                 this.contents[localContentsOffset++] = (byte)info.tag;
                                 switch (info.tag) {
                                    case 7:
                                       int indexForType = this.constantPool.literalIndexForType(info.constantPoolName());
                                       this.contents[localContentsOffset++] = (byte)(indexForType >> 8);
                                       this.contents[localContentsOffset++] = (byte)indexForType;
                                       continue;
                                    case 8:
                                       offset = info.offset;
                                       this.contents[localContentsOffset++] = (byte)(offset >> 8);
                                       this.contents[localContentsOffset++] = (byte)offset;
                                    default:
                                       continue;
                                 }
                              case 7:
                                 this.contents[localContentsOffset++] = 4;
                                 break;
                              case 8:
                                 this.contents[localContentsOffset++] = 3;
                                 break;
                              case 9:
                                 this.contents[localContentsOffset++] = 2;
                                 break;
                              case 12:
                                 this.contents[localContentsOffset++] = 5;
                           }
                        }
                     }
                     break;
                  case 5:
                     if (localContentsOffset + 4 >= this.contents.length) {
                        this.resizeContents(4);
                     }

                     this.contents[localContentsOffset++] = (byte)(offsetDelta + 64);
                     if (currentFrame.stackItems[0] == null) {
                        this.contents[localContentsOffset++] = 0;
                     } else {
                        switch (currentFrame.stackItems[0].id()) {
                           case 2:
                           case 3:
                           case 4:
                           case 5:
                           case 10:
                              this.contents[localContentsOffset++] = 1;
                              continue;
                           case 6:
                           case 11:
                           default:
                              info = currentFrame.stackItems[0];
                              tag = (byte)info.tag;
                              this.contents[localContentsOffset++] = (byte)tag;
                              switch (tag) {
                                 case 7:
                                    indexForType = this.constantPool.literalIndexForType(info.constantPoolName());
                                    this.contents[localContentsOffset++] = (byte)(indexForType >> 8);
                                    this.contents[localContentsOffset++] = (byte)indexForType;
                                    continue;
                                 case 8:
                                    offset = info.offset;
                                    this.contents[localContentsOffset++] = (byte)(offset >> 8);
                                    this.contents[localContentsOffset++] = (byte)offset;
                                 default:
                                    continue;
                              }
                           case 7:
                              this.contents[localContentsOffset++] = 4;
                              continue;
                           case 8:
                              this.contents[localContentsOffset++] = 3;
                              continue;
                           case 9:
                              this.contents[localContentsOffset++] = 2;
                              continue;
                           case 12:
                              this.contents[localContentsOffset++] = 5;
                        }
                     }
                     break;
                  case 6:
                     if (localContentsOffset + 6 >= this.contents.length) {
                        this.resizeContents(6);
                     }

                     this.contents[localContentsOffset++] = -9;
                     this.contents[localContentsOffset++] = (byte)(offsetDelta >> 8);
                     this.contents[localContentsOffset++] = (byte)offsetDelta;
                     if (currentFrame.stackItems[0] == null) {
                        this.contents[localContentsOffset++] = 0;
                     } else {
                        switch (currentFrame.stackItems[0].id()) {
                           case 2:
                           case 3:
                           case 4:
                           case 5:
                           case 10:
                              this.contents[localContentsOffset++] = 1;
                              break;
                           case 6:
                           case 11:
                           default:
                              info = currentFrame.stackItems[0];
                              tag = (byte)info.tag;
                              this.contents[localContentsOffset++] = (byte)tag;
                              switch (tag) {
                                 case 7:
                                    indexForType = this.constantPool.literalIndexForType(info.constantPoolName());
                                    this.contents[localContentsOffset++] = (byte)(indexForType >> 8);
                                    this.contents[localContentsOffset++] = (byte)indexForType;
                                    continue;
                                 case 8:
                                    offset = info.offset;
                                    this.contents[localContentsOffset++] = (byte)(offset >> 8);
                                    this.contents[localContentsOffset++] = (byte)offset;
                                 default:
                                    continue;
                              }
                           case 7:
                              this.contents[localContentsOffset++] = 4;
                              break;
                           case 8:
                              this.contents[localContentsOffset++] = 3;
                              break;
                           case 9:
                              this.contents[localContentsOffset++] = 2;
                              break;
                           case 12:
                              this.contents[localContentsOffset++] = 5;
                        }
                     }
               }
            }

            --numberOfFrames;
            if (numberOfFrames != 0) {
               this.contents[numberOfFramesOffset++] = (byte)(numberOfFrames >> 8);
               this.contents[numberOfFramesOffset] = (byte)numberOfFrames;
               attributeLength = localContentsOffset - stackMapTableAttributeLengthOffset - 4;
               this.contents[stackMapTableAttributeLengthOffset++] = (byte)(attributeLength >> 24);
               this.contents[stackMapTableAttributeLengthOffset++] = (byte)(attributeLength >> 16);
               this.contents[stackMapTableAttributeLengthOffset++] = (byte)(attributeLength >> 8);
               this.contents[stackMapTableAttributeLengthOffset] = (byte)attributeLength;
               ++attributesNumber;
            } else {
               localContentsOffset = stackMapTableAttributeOffset;
            }
         }
      }

      this.contentsOffset = localContentsOffset;
      return attributesNumber;
   }

   private int generateSyntheticAttribute() {
      int localContentsOffset = this.contentsOffset;
      if (localContentsOffset + 6 >= this.contents.length) {
         this.resizeContents(6);
      }

      int syntheticAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.SyntheticName);
      this.contents[localContentsOffset++] = (byte)(syntheticAttributeNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)syntheticAttributeNameIndex;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contentsOffset = localContentsOffset;
      return 1;
   }

   private void generateTypeAnnotation(AnnotationContext annotationContext, int currentOffset) {
      Annotation annotation = annotationContext.annotation.getPersistibleAnnotation();
      if (annotation != null && annotation.resolvedType != null) {
         int targetType = annotationContext.targetType;
         int[] locations = Annotation.getLocations(annotationContext.typeReference, annotationContext.annotation);
         if (this.contentsOffset + 5 >= this.contents.length) {
            this.resizeContents(5);
         }

         this.contents[this.contentsOffset++] = (byte)targetType;
         this.dumpTargetTypeContents(targetType, annotationContext);
         this.dumpLocations(locations);
         this.generateAnnotation(annotation, currentOffset);
      }
   }

   private int generateTypeAnnotationAttributeForTypeDeclaration() {
      TypeDeclaration typeDeclaration = this.referenceBinding.scope.referenceContext;
      if ((typeDeclaration.bits & 1048576) == 0) {
         return 0;
      } else {
         int attributesNumber = 0;
         int visibleTypeAnnotationsCounter = 0;
         int invisibleTypeAnnotationsCounter = 0;
         TypeReference superclass = typeDeclaration.superclass;
         List allTypeAnnotationContexts = new ArrayList();
         if (superclass != null && (superclass.bits & 1048576) != 0) {
            superclass.getAllAnnotationContexts(16, -1, allTypeAnnotationContexts);
         }

         TypeReference[] superInterfaces = typeDeclaration.superInterfaces;
         if (superInterfaces != null) {
            for(int i = 0; i < superInterfaces.length; ++i) {
               TypeReference superInterface = superInterfaces[i];
               if ((superInterface.bits & 1048576) != 0) {
                  superInterface.getAllAnnotationContexts(16, i, allTypeAnnotationContexts);
               }
            }
         }

         TypeParameter[] typeParameters = typeDeclaration.typeParameters;
         int i;
         if (typeParameters != null) {
            i = 0;

            for(int max = typeParameters.length; i < max; ++i) {
               TypeParameter typeParameter = typeParameters[i];
               if ((typeParameter.bits & 1048576) != 0) {
                  typeParameter.getAllAnnotationContexts(0, i, allTypeAnnotationContexts);
               }
            }
         }

         i = allTypeAnnotationContexts.size();
         if (i != 0) {
            AnnotationContext[] allTypeAnnotationContextsArray = new AnnotationContext[i];
            allTypeAnnotationContexts.toArray(allTypeAnnotationContextsArray);
            int j = 0;

            for(int max = allTypeAnnotationContextsArray.length; j < max; ++j) {
               AnnotationContext annotationContext = allTypeAnnotationContextsArray[j];
               if ((annotationContext.visibility & 2) != 0) {
                  ++invisibleTypeAnnotationsCounter;
                  allTypeAnnotationContexts.add(annotationContext);
               } else {
                  ++visibleTypeAnnotationsCounter;
                  allTypeAnnotationContexts.add(annotationContext);
               }
            }

            attributesNumber += this.generateRuntimeTypeAnnotations(allTypeAnnotationContextsArray, visibleTypeAnnotationsCounter, invisibleTypeAnnotationsCounter);
         }

         return attributesNumber;
      }
   }

   private int generateVarargsAttribute() {
      int localContentsOffset = this.contentsOffset;
      if (localContentsOffset + 6 >= this.contents.length) {
         this.resizeContents(6);
      }

      int varargsAttributeNameIndex = this.constantPool.literalIndex(AttributeNamesConstants.VarargsName);
      this.contents[localContentsOffset++] = (byte)(varargsAttributeNameIndex >> 8);
      this.contents[localContentsOffset++] = (byte)varargsAttributeNameIndex;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contents[localContentsOffset++] = 0;
      this.contentsOffset = localContentsOffset;
      return 1;
   }

   public byte[] getBytes() {
      if (this.bytes == null) {
         this.bytes = new byte[this.headerOffset + this.contentsOffset];
         System.arraycopy(this.header, 0, this.bytes, 0, this.headerOffset);
         System.arraycopy(this.contents, 0, this.bytes, this.headerOffset, this.contentsOffset);
      }

      return this.bytes;
   }

   public char[][] getCompoundName() {
      return CharOperation.splitOn('/', this.fileName());
   }

   private int getParametersCount(char[] methodSignature) {
      int i = CharOperation.indexOf('(', methodSignature);
      ++i;
      char currentCharacter = methodSignature[i];
      if (currentCharacter == ')') {
         return 0;
      } else {
         int result = 0;

         while(true) {
            currentCharacter = methodSignature[i];
            if (currentCharacter == ')') {
               return result;
            }

            int scanType;
            switch (currentCharacter) {
               case 'B':
               case 'C':
               case 'D':
               case 'F':
               case 'I':
               case 'J':
               case 'S':
               case 'Z':
                  ++result;
                  ++i;
                  break;
               case 'L':
                  scanType = CharOperation.indexOf(';', methodSignature, i + 1);
                  ++result;
                  i = scanType + 1;
                  break;
               case '[':
                  scanType = this.scanType(methodSignature, i + 1);
                  ++result;
                  i = scanType + 1;
                  break;
               default:
                  throw new IllegalArgumentException("Invalid starting type character : " + currentCharacter);
            }
         }
      }
   }

   private char[] getReturnType(char[] methodSignature) {
      int paren = CharOperation.lastIndexOf(')', methodSignature);
      return CharOperation.subarray(methodSignature, paren + 1, methodSignature.length);
   }

   private final int i4At(byte[] reference, int relativeOffset, int structOffset) {
      int position = relativeOffset + structOffset;
      return ((reference[position++] & 255) << 24) + ((reference[position++] & 255) << 16) + ((reference[position++] & 255) << 8) + (reference[position] & 255);
   }

   protected void initByteArrays(int members) {
      this.header = new byte[1500];
      this.contents = new byte[members < 15 ? 400 : 1500];
   }

   private void initializeHeader(ClassFile parentClassFile, int accessFlags) {
      this.header[this.headerOffset++] = -54;
      this.header[this.headerOffset++] = -2;
      this.header[this.headerOffset++] = -70;
      this.header[this.headerOffset++] = -66;
      long targetVersion = this.targetJDK;
      this.header[this.headerOffset++] = (byte)((int)(targetVersion >> 8));
      this.header[this.headerOffset++] = (byte)((int)(targetVersion >> 0));
      this.header[this.headerOffset++] = (byte)((int)(targetVersion >> 24));
      this.header[this.headerOffset++] = (byte)((int)(targetVersion >> 16));
      this.constantPoolOffset = this.headerOffset;
      this.headerOffset += 2;
      this.constantPool.initialize(this);
      this.enclosingClassFile = parentClassFile;
      this.contents[this.contentsOffset++] = (byte)(accessFlags >> 8);
      this.contents[this.contentsOffset++] = (byte)accessFlags;
   }

   public void initialize(SourceTypeBinding aType, ClassFile parentClassFile, boolean createProblemType) {
      int accessFlags = aType.getAccessFlags();
      if (aType.isPrivate()) {
         accessFlags &= -2;
      }

      if (aType.isProtected()) {
         accessFlags |= 1;
      }

      accessFlags &= -2351;
      if (!aType.isInterface()) {
         accessFlags |= 32;
      }

      if (aType.isAnonymousType()) {
         accessFlags &= -17;
      }

      int finalAbstract = 1040;
      if ((accessFlags & finalAbstract) == finalAbstract) {
         accessFlags &= ~finalAbstract;
      }

      this.initializeHeader(parentClassFile, accessFlags);
      int classNameIndex = this.constantPool.literalIndexForType((TypeBinding)aType);
      this.contents[this.contentsOffset++] = (byte)(classNameIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)classNameIndex;
      int superclassNameIndex;
      if (aType.isInterface()) {
         superclassNameIndex = this.constantPool.literalIndexForType(ConstantPool.JavaLangObjectConstantPoolName);
      } else if (aType.superclass != null) {
         if ((aType.superclass.tagBits & 128L) != 0L) {
            superclassNameIndex = this.constantPool.literalIndexForType(ConstantPool.JavaLangObjectConstantPoolName);
         } else {
            superclassNameIndex = this.constantPool.literalIndexForType((TypeBinding)aType.superclass);
         }
      } else {
         superclassNameIndex = 0;
      }

      this.contents[this.contentsOffset++] = (byte)(superclassNameIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)superclassNameIndex;
      ReferenceBinding[] superInterfacesBinding = aType.superInterfaces();
      int interfacesCount = superInterfacesBinding.length;
      int interfacesCountPosition = this.contentsOffset;
      this.contentsOffset += 2;
      int interfaceCounter = 0;

      for(int i = 0; i < interfacesCount; ++i) {
         ReferenceBinding binding = superInterfacesBinding[i];
         if ((binding.tagBits & 128L) == 0L) {
            ++interfaceCounter;
            int interfaceIndex = this.constantPool.literalIndexForType((TypeBinding)binding);
            this.contents[this.contentsOffset++] = (byte)(interfaceIndex >> 8);
            this.contents[this.contentsOffset++] = (byte)interfaceIndex;
         }
      }

      this.contents[interfacesCountPosition++] = (byte)(interfaceCounter >> 8);
      this.contents[interfacesCountPosition] = (byte)interfaceCounter;
      this.creatingProblemType = createProblemType;
      this.codeStream.maxFieldCount = aType.scope.outerMostClassScope().referenceType().maxFieldCount;
   }

   public void initializeForModule(ModuleBinding module) {
      this.initializeHeader((ClassFile)null, 32768);
      int classNameIndex = this.constantPool.literalIndexForType(TypeConstants.MODULE_INFO_NAME);
      this.contents[this.contentsOffset++] = (byte)(classNameIndex >> 8);
      this.contents[this.contentsOffset++] = (byte)classNameIndex;
      this.codeStream.maxFieldCount = 0;
      this.contents[this.contentsOffset++] = 0;
      this.contents[this.contentsOffset++] = 0;
      this.contents[this.contentsOffset++] = 0;
      this.contents[this.contentsOffset++] = 0;
      this.contents[this.contentsOffset++] = 0;
      this.contents[this.contentsOffset++] = 0;
      this.contents[this.contentsOffset++] = 0;
      this.contents[this.contentsOffset++] = 0;
   }

   private void initializeDefaultLocals(StackMapFrame frame, MethodBinding methodBinding, int maxLocals, int codeLength) {
      if (maxLocals != 0) {
         int resolvedPosition = 0;
         boolean isConstructor = methodBinding.isConstructor();
         LocalVariableBinding localVariableBinding;
         if (isConstructor || !methodBinding.isStatic()) {
            localVariableBinding = new LocalVariableBinding(ConstantPool.This, methodBinding.declaringClass, 0, false);
            localVariableBinding.resolvedPosition = 0;
            this.codeStream.record(localVariableBinding);
            localVariableBinding.recordInitializationStartPC(0);
            localVariableBinding.recordInitializationEndPC(codeLength);
            frame.putLocal(resolvedPosition, new VerificationTypeInfo(isConstructor ? 6 : 7, methodBinding.declaringClass));
            ++resolvedPosition;
         }

         int i;
         int i;
         TypeBinding[] arguments;
         TypeBinding typeBinding;
         if (isConstructor) {
            if (methodBinding.declaringClass.isEnum()) {
               localVariableBinding = new LocalVariableBinding(" name".toCharArray(), this.referenceBinding.scope.getJavaLangString(), 0, false);
               localVariableBinding.resolvedPosition = resolvedPosition;
               this.codeStream.record(localVariableBinding);
               localVariableBinding.recordInitializationStartPC(0);
               localVariableBinding.recordInitializationEndPC(codeLength);
               frame.putLocal(resolvedPosition, new VerificationTypeInfo(11, ConstantPool.JavaLangStringConstantPoolName));
               ++resolvedPosition;
               localVariableBinding = new LocalVariableBinding(" ordinal".toCharArray(), TypeBinding.INT, 0, false);
               localVariableBinding.resolvedPosition = resolvedPosition;
               this.codeStream.record(localVariableBinding);
               localVariableBinding.recordInitializationStartPC(0);
               localVariableBinding.recordInitializationEndPC(codeLength);
               frame.putLocal(resolvedPosition, new VerificationTypeInfo(TypeBinding.INT));
               ++resolvedPosition;
            }

            if (methodBinding.declaringClass.isNestedType()) {
               ReferenceBinding[] enclosingInstanceTypes;
               if ((enclosingInstanceTypes = methodBinding.declaringClass.syntheticEnclosingInstanceTypes()) != null) {
                  i = 0;

                  for(i = enclosingInstanceTypes.length; i < i; ++i) {
                     LocalVariableBinding localVariableBinding = new LocalVariableBinding((" enclosingType" + i).toCharArray(), enclosingInstanceTypes[i], 0, false);
                     localVariableBinding.resolvedPosition = resolvedPosition;
                     this.codeStream.record(localVariableBinding);
                     localVariableBinding.recordInitializationStartPC(0);
                     localVariableBinding.recordInitializationEndPC(codeLength);
                     frame.putLocal(resolvedPosition, new VerificationTypeInfo(enclosingInstanceTypes[i]));
                     ++resolvedPosition;
                  }
               }

               TypeBinding[] arguments;
               int i;
               if ((arguments = methodBinding.parameters) != null) {
                  i = 0;

                  for(i = arguments.length; i < i; ++i) {
                     TypeBinding typeBinding = arguments[i];
                     frame.putLocal(resolvedPosition, new VerificationTypeInfo(typeBinding));
                     switch (typeBinding.id) {
                        case 7:
                        case 8:
                           resolvedPosition += 2;
                           break;
                        default:
                           ++resolvedPosition;
                     }
                  }
               }

               SyntheticArgumentBinding[] syntheticArguments;
               if ((syntheticArguments = methodBinding.declaringClass.syntheticOuterLocalVariables()) != null) {
                  i = 0;

                  for(int max = syntheticArguments.length; i < max; ++i) {
                     TypeBinding typeBinding = syntheticArguments[i].type;
                     LocalVariableBinding localVariableBinding = new LocalVariableBinding((" synthetic" + i).toCharArray(), typeBinding, 0, false);
                     localVariableBinding.resolvedPosition = resolvedPosition;
                     this.codeStream.record(localVariableBinding);
                     localVariableBinding.recordInitializationStartPC(0);
                     localVariableBinding.recordInitializationEndPC(codeLength);
                     frame.putLocal(resolvedPosition, new VerificationTypeInfo(typeBinding));
                     switch (typeBinding.id) {
                        case 7:
                        case 8:
                           resolvedPosition += 2;
                           break;
                        default:
                           ++resolvedPosition;
                     }
                  }
               }
            } else if ((arguments = methodBinding.parameters) != null) {
               i = 0;

               for(i = arguments.length; i < i; ++i) {
                  typeBinding = arguments[i];
                  frame.putLocal(resolvedPosition, new VerificationTypeInfo(typeBinding));
                  switch (typeBinding.id) {
                     case 7:
                     case 8:
                        resolvedPosition += 2;
                        break;
                     default:
                        ++resolvedPosition;
                  }
               }
            }
         } else if ((arguments = methodBinding.parameters) != null) {
            i = 0;

            for(i = arguments.length; i < i; ++i) {
               typeBinding = arguments[i];
               LocalVariableBinding localVariableBinding = new LocalVariableBinding((" synthetic" + i).toCharArray(), typeBinding, 0, true);
               localVariableBinding.resolvedPosition = i;
               this.codeStream.record(localVariableBinding);
               localVariableBinding.recordInitializationStartPC(0);
               localVariableBinding.recordInitializationEndPC(codeLength);
               frame.putLocal(resolvedPosition, new VerificationTypeInfo(typeBinding));
               switch (typeBinding.id) {
                  case 7:
                  case 8:
                     resolvedPosition += 2;
                     break;
                  default:
                     ++resolvedPosition;
               }
            }
         }
      }

   }

   private void initializeLocals(boolean isStatic, int currentPC, StackMapFrame currentFrame) {
      VerificationTypeInfo[] locals = currentFrame.locals;
      int localsLength = locals.length;
      int i = 0;
      if (!isStatic) {
         i = 1;
      }

      while(i < localsLength) {
         locals[i] = null;
         ++i;
      }

      i = 0;

      for(int max = this.codeStream.allLocalsCounter; i < max; ++i) {
         LocalVariableBinding localVariable = this.codeStream.locals[i];
         if (localVariable != null) {
            int resolvedPosition = localVariable.resolvedPosition;
            TypeBinding localVariableTypeBinding = localVariable.type;

            for(int j = 0; j < localVariable.initializationCount; ++j) {
               int startPC = localVariable.initializationPCs[j << 1];
               int endPC = localVariable.initializationPCs[(j << 1) + 1];
               if (currentPC >= startPC && currentPC < endPC) {
                  if (currentFrame.locals[resolvedPosition] == null) {
                     currentFrame.locals[resolvedPosition] = new VerificationTypeInfo(localVariableTypeBinding);
                  }
                  break;
               }
            }
         }
      }

   }

   public ClassFile outerMostEnclosingClassFile() {
      ClassFile current;
      for(current = this; current.enclosingClassFile != null; current = current.enclosingClassFile) {
      }

      return current;
   }

   public void recordInnerClasses(TypeBinding binding) {
      this.recordInnerClasses(binding, false);
   }

   public void recordInnerClasses(TypeBinding binding, boolean onBottomForBug445231) {
      if (this.innerClassesBindings == null) {
         this.innerClassesBindings = new HashMap(5);
      }

      ReferenceBinding innerClass = (ReferenceBinding)binding;
      this.innerClassesBindings.put(innerClass.erasure().unannotated(), onBottomForBug445231);

      for(ReferenceBinding enclosingType = innerClass.enclosingType(); enclosingType != null && enclosingType.isNestedType(); enclosingType = enclosingType.enclosingType()) {
         this.innerClassesBindings.put(enclosingType.erasure().unannotated(), onBottomForBug445231);
      }

   }

   public int recordBootstrapMethod(FunctionalExpression expression) {
      if (this.bootstrapMethods == null) {
         this.bootstrapMethods = new ArrayList();
      }

      if (expression instanceof ReferenceExpression) {
         for(int i = 0; i < this.bootstrapMethods.size(); ++i) {
            FunctionalExpression fexp = (FunctionalExpression)this.bootstrapMethods.get(i);
            if (fexp.binding == expression.binding && TypeBinding.equalsEquals(fexp.expectedType(), expression.expectedType())) {
               return expression.bootstrapMethodNumber = i;
            }
         }
      }

      this.bootstrapMethods.add(expression);
      return expression.bootstrapMethodNumber = this.bootstrapMethods.size() - 1;
   }

   public void reset(SourceTypeBinding typeBinding, CompilerOptions options) {
      if (typeBinding != null) {
         this.referenceBinding = typeBinding;
         this.isNestedType = typeBinding.isNestedType();
      } else {
         this.referenceBinding = null;
         this.isNestedType = false;
      }

      this.targetJDK = options.targetJDK;
      this.produceAttributes = options.produceDebugAttributes;
      if (this.targetJDK >= 3276800L) {
         this.produceAttributes |= 8;
         if (this.targetJDK >= 3407872L) {
            this.produceAttributes |= 32;
            if (!(this.codeStream instanceof TypeAnnotationCodeStream) && this.referenceBinding != null) {
               this.codeStream = new TypeAnnotationCodeStream(this);
            }

            if (options.produceMethodParameters) {
               this.produceAttributes |= 64;
            }
         }
      } else if (this.targetJDK == 2949124L) {
         this.targetJDK = 2949123L;
         this.produceAttributes |= 16;
      }

      this.bytes = null;
      this.constantPool.reset();
      this.codeStream.reset(this);
      this.constantPoolOffset = 0;
      this.contentsOffset = 0;
      this.creatingProblemType = false;
      this.enclosingClassFile = null;
      this.headerOffset = 0;
      this.methodCount = 0;
      this.methodCountOffset = 0;
      if (this.innerClassesBindings != null) {
         this.innerClassesBindings.clear();
      }

      if (this.bootstrapMethods != null) {
         this.bootstrapMethods.clear();
      }

      this.missingTypes = null;
      this.visitedTypes = null;
   }

   private final void resizeContents(int minimalSize) {
      int length = this.contents.length;
      int toAdd = length;
      if (length < minimalSize) {
         toAdd = minimalSize;
      }

      System.arraycopy(this.contents, 0, this.contents = new byte[length + toAdd], 0, length);
   }

   private VerificationTypeInfo retrieveLocal(int currentPC, int resolvedPosition) {
      int i = 0;

      for(int max = this.codeStream.allLocalsCounter; i < max; ++i) {
         LocalVariableBinding localVariable = this.codeStream.locals[i];
         if (localVariable != null && resolvedPosition == localVariable.resolvedPosition) {
            for(int j = 0; j < localVariable.initializationCount; ++j) {
               int startPC = localVariable.initializationPCs[j << 1];
               int endPC = localVariable.initializationPCs[(j << 1) + 1];
               if (currentPC >= startPC && currentPC < endPC) {
                  return new VerificationTypeInfo(localVariable.type);
               }
            }
         }
      }

      return null;
   }

   private int scanType(char[] methodSignature, int index) {
      switch (methodSignature[index]) {
         case 'B':
         case 'C':
         case 'D':
         case 'F':
         case 'I':
         case 'J':
         case 'S':
         case 'Z':
            return index;
         case 'L':
            return CharOperation.indexOf(';', methodSignature, index + 1);
         case '[':
            return this.scanType(methodSignature, index + 1);
         default:
            throw new IllegalArgumentException();
      }
   }

   public void setForMethodInfos() {
      this.methodCountOffset = this.contentsOffset;
      this.contentsOffset += 2;
   }

   private List filterFakeFrames(Set realJumpTargets, Map frames, int codeLength) {
      realJumpTargets.remove(codeLength);
      List result = new ArrayList();
      Iterator iterator = realJumpTargets.iterator();

      while(iterator.hasNext()) {
         Integer jumpTarget = (Integer)iterator.next();
         StackMapFrame frame = (StackMapFrame)frames.get(jumpTarget);
         if (frame != null) {
            result.add(frame);
         }
      }

      Collections.sort(result, new Comparator() {
         public int compare(Object o1, Object o2) {
            StackMapFrame frame = (StackMapFrame)o1;
            StackMapFrame frame2 = (StackMapFrame)o2;
            return frame.pc - frame2.pc;
         }
      });
      return result;
   }

   public List traverse(MethodBinding methodBinding, int maxLocals, byte[] bytecodes, int codeOffset, int codeLength, Map frames, boolean isClinit) {
      Set realJumpTarget = new HashSet();
      StackMapFrameCodeStream stackMapFrameCodeStream = (StackMapFrameCodeStream)this.codeStream;
      int[] framePositions = stackMapFrameCodeStream.getFramePositions();
      int pc = codeOffset;
      int[] constantPoolOffsets = this.constantPool.offsets;
      byte[] poolContents = this.constantPool.poolContent;
      int indexInFramePositions = 0;
      int framePositionsLength = framePositions.length;
      int currentFramePosition = framePositions[0];
      int indexInStackDepthMarkers = 0;
      StackMapFrameCodeStream.StackDepthMarker[] stackDepthMarkers = stackMapFrameCodeStream.getStackDepthMarkers();
      int stackDepthMarkersLength = stackDepthMarkers == null ? 0 : stackDepthMarkers.length;
      boolean hasStackDepthMarkers = stackDepthMarkersLength != 0;
      StackMapFrameCodeStream.StackDepthMarker stackDepthMarker = null;
      if (hasStackDepthMarkers) {
         stackDepthMarker = stackDepthMarkers[0];
      }

      int indexInStackMarkers = 0;
      StackMapFrameCodeStream.StackMarker[] stackMarkers = stackMapFrameCodeStream.getStackMarkers();
      int stackMarkersLength = stackMarkers == null ? 0 : stackMarkers.length;
      boolean hasStackMarkers = stackMarkersLength != 0;
      StackMapFrameCodeStream.StackMarker stackMarker = null;
      if (hasStackMarkers) {
         stackMarker = stackMarkers[0];
      }

      int indexInExceptionMarkers = 0;
      StackMapFrameCodeStream.ExceptionMarker[] exceptionMarkers = stackMapFrameCodeStream.getExceptionMarkers();
      int exceptionsMarkersLength = exceptionMarkers == null ? 0 : exceptionMarkers.length;
      boolean hasExceptionMarkers = exceptionsMarkersLength != 0;
      StackMapFrameCodeStream.ExceptionMarker exceptionMarker = null;
      if (hasExceptionMarkers) {
         exceptionMarker = exceptionMarkers[0];
      }

      StackMapFrame frame = new StackMapFrame(maxLocals);
      if (!isClinit) {
         this.initializeDefaultLocals(frame, methodBinding, maxLocals, codeLength);
      }

      frame.pc = -1;
      this.add(frames, frame.duplicate());
      this.addRealJumpTarget(realJumpTarget, -1);
      int currentPC = 0;

      for(int max = this.codeStream.exceptionLabelsCounter; currentPC < max; ++currentPC) {
         ExceptionLabel exceptionLabel = this.codeStream.exceptionLabels[currentPC];
         if (exceptionLabel != null) {
            this.addRealJumpTarget(realJumpTarget, exceptionLabel.position);
         }
      }

      do {
         currentPC = pc - codeOffset;
         VerificationTypeInfo[] infos;
         if (hasStackMarkers && stackMarker.pc == currentPC) {
            infos = frame.stackItems;
            VerificationTypeInfo[] tempInfos = new VerificationTypeInfo[frame.numberOfStackItems];
            System.arraycopy(infos, 0, tempInfos, 0, frame.numberOfStackItems);
            stackMarker.setInfos(tempInfos);
         } else if (hasStackMarkers && stackMarker.destinationPC == currentPC) {
            infos = stackMarker.infos;
            frame.stackItems = infos;
            frame.numberOfStackItems = infos.length;
            ++indexInStackMarkers;
            if (indexInStackMarkers < stackMarkersLength) {
               stackMarker = stackMarkers[indexInStackMarkers];
            } else {
               hasStackMarkers = false;
            }
         }

         if (hasStackDepthMarkers && stackDepthMarker.pc == currentPC) {
            TypeBinding typeBinding = stackDepthMarker.typeBinding;
            if (typeBinding != null) {
               if (stackDepthMarker.delta > 0) {
                  frame.addStackItem(new VerificationTypeInfo(typeBinding));
               } else {
                  frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(typeBinding);
               }
            } else {
               --frame.numberOfStackItems;
            }

            ++indexInStackDepthMarkers;
            if (indexInStackDepthMarkers < stackDepthMarkersLength) {
               stackDepthMarker = stackDepthMarkers[indexInStackDepthMarkers];
            } else {
               hasStackDepthMarkers = false;
            }
         }

         if (hasExceptionMarkers && exceptionMarker.pc == currentPC) {
            frame.numberOfStackItems = 0;
            frame.addStackItem(new VerificationTypeInfo(0, 7, exceptionMarker.constantPoolName));
            ++indexInExceptionMarkers;
            if (indexInExceptionMarkers < exceptionsMarkersLength) {
               exceptionMarker = exceptionMarkers[indexInExceptionMarkers];
            } else {
               hasExceptionMarkers = false;
            }
         }

         if (currentFramePosition < currentPC) {
            do {
               ++indexInFramePositions;
               if (indexInFramePositions < framePositionsLength) {
                  currentFramePosition = framePositions[indexInFramePositions];
               } else {
                  currentFramePosition = Integer.MAX_VALUE;
               }
            } while(currentFramePosition < currentPC);
         }

         if (currentFramePosition == currentPC) {
            StackMapFrame currentFrame = frame.duplicate();
            currentFrame.pc = currentPC;
            this.initializeLocals(isClinit ? true : methodBinding.isStatic(), currentPC, currentFrame);
            this.add(frames, currentFrame);
            ++indexInFramePositions;
            if (indexInFramePositions < framePositionsLength) {
               currentFramePosition = framePositions[indexInFramePositions];
            } else {
               currentFramePosition = Integer.MAX_VALUE;
            }
         }

         byte opcode = (byte)this.u1At(bytecodes, 0, pc);
         int index;
         int numberOfStackItems;
         VerificationTypeInfo info;
         VerificationTypeInfo info2;
         VerificationTypeInfo info3;
         int npairs;
         int i;
         int utf8index;
         char[] descriptor;
         char[] returnType;
         char[] className;
         char[] constantPoolName;
         int classNameLength;
         VerificationTypeInfo localsN;
         switch (opcode) {
            case -128:
            case -127:
            case -126:
            case -125:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
               --frame.numberOfStackItems;
               ++pc;
               break;
            case -124:
               pc += 3;
               break;
            case -123:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.LONG);
               ++pc;
               break;
            case -122:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.FLOAT);
               ++pc;
               break;
            case -121:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.DOUBLE);
               ++pc;
               break;
            case -120:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.INT);
               ++pc;
               break;
            case -119:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.FLOAT);
               ++pc;
               break;
            case -118:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.DOUBLE);
               ++pc;
               break;
            case -117:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.INT);
               ++pc;
               break;
            case -116:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.LONG);
               ++pc;
               break;
            case -115:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.DOUBLE);
               ++pc;
               break;
            case -114:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.INT);
               ++pc;
               break;
            case -113:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.LONG);
               ++pc;
               break;
            case -112:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.FLOAT);
               ++pc;
               break;
            case -111:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.BYTE);
               ++pc;
               break;
            case -110:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.CHAR);
               ++pc;
               break;
            case -109:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.SHORT);
               ++pc;
               break;
            case -108:
            case -107:
            case -106:
            case -105:
            case -104:
               frame.numberOfStackItems -= 2;
               frame.addStackItem((TypeBinding)TypeBinding.INT);
               ++pc;
               break;
            case -103:
            case -102:
            case -101:
            case -100:
            case -99:
            case -98:
               --frame.numberOfStackItems;
               this.addRealJumpTarget(realJumpTarget, currentPC + this.i2At(bytecodes, 1, pc));
               pc += 3;
               break;
            case -97:
            case -96:
            case -95:
            case -94:
            case -93:
            case -92:
            case -91:
            case -90:
               frame.numberOfStackItems -= 2;
               this.addRealJumpTarget(realJumpTarget, currentPC + this.i2At(bytecodes, 1, pc));
               pc += 3;
               break;
            case -89:
               this.addRealJumpTarget(realJumpTarget, currentPC + this.i2At(bytecodes, 1, pc));
               pc += 3;
               this.addRealJumpTarget(realJumpTarget, pc - codeOffset);
               break;
            case -88:
            case -87:
            case -55:
            case -54:
            case -53:
            case -52:
            case -51:
            case -50:
            case -49:
            case -48:
            case -47:
            case -46:
            case -45:
            case -44:
            case -43:
            case -42:
            case -41:
            case -40:
            case -39:
            case -38:
            case -37:
            case -36:
            case -35:
            case -34:
            case -33:
            case -32:
            case -31:
            case -30:
            case -29:
            case -28:
            case -27:
            case -26:
            case -25:
            case -24:
            case -23:
            case -22:
            case -21:
            case -20:
            case -19:
            case -18:
            case -17:
            case -16:
            case -15:
            case -14:
            case -13:
            case -12:
            case -11:
            case -10:
            case -9:
            case -8:
            case -7:
            case -6:
            case -5:
            case -4:
            case -3:
            case -2:
            case -1:
            default:
               if (this.codeStream.methodDeclaration != null) {
                  this.codeStream.methodDeclaration.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidOpcode, new Object[]{opcode, pc, new String(methodBinding.shortReadableName())}), this.codeStream.methodDeclaration);
               } else {
                  this.codeStream.lambdaExpression.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidOpcode, new Object[]{opcode, pc, new String(methodBinding.shortReadableName())}), this.codeStream.lambdaExpression);
               }
               break;
            case -86:
               ++pc;

               while((pc - codeOffset & 3) != 0) {
                  ++pc;
               }

               this.addRealJumpTarget(realJumpTarget, currentPC + this.i4At(bytecodes, 0, pc));
               pc += 4;
               int low = this.i4At(bytecodes, 0, pc);
               pc += 4;
               int high = this.i4At(bytecodes, 0, pc);
               pc += 4;
               int length = high - low + 1;

               for(npairs = 0; npairs < length; ++npairs) {
                  this.addRealJumpTarget(realJumpTarget, currentPC + this.i4At(bytecodes, 0, pc));
                  pc += 4;
               }

               --frame.numberOfStackItems;
               break;
            case -85:
               ++pc;

               while((pc - codeOffset & 3) != 0) {
                  ++pc;
               }

               this.addRealJumpTarget(realJumpTarget, currentPC + this.i4At(bytecodes, 0, pc));
               pc += 4;
               npairs = (int)this.u4At(bytecodes, 0, pc);
               pc += 4;

               for(i = 0; i < npairs; ++i) {
                  pc += 4;
                  this.addRealJumpTarget(realJumpTarget, currentPC + this.i4At(bytecodes, 0, pc));
                  pc += 4;
               }

               --frame.numberOfStackItems;
               break;
            case -84:
            case -83:
            case -82:
            case -81:
            case -80:
               --frame.numberOfStackItems;
               ++pc;
               this.addRealJumpTarget(realJumpTarget, pc - codeOffset);
               break;
            case -79:
               ++pc;
               this.addRealJumpTarget(realJumpTarget, pc - codeOffset);
               break;
            case -78:
               index = this.u2At(bytecodes, 1, pc);
               i = this.u2At(poolContents, 3, constantPoolOffsets[index]);
               utf8index = this.u2At(poolContents, 3, constantPoolOffsets[i]);
               descriptor = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               if (descriptor.length == 1) {
                  switch (descriptor[0]) {
                     case 'B':
                        frame.addStackItem((TypeBinding)TypeBinding.BYTE);
                        break;
                     case 'C':
                        frame.addStackItem((TypeBinding)TypeBinding.CHAR);
                        break;
                     case 'D':
                        frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
                        break;
                     case 'F':
                        frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
                        break;
                     case 'I':
                        frame.addStackItem((TypeBinding)TypeBinding.INT);
                        break;
                     case 'J':
                        frame.addStackItem((TypeBinding)TypeBinding.LONG);
                        break;
                     case 'S':
                        frame.addStackItem((TypeBinding)TypeBinding.SHORT);
                        break;
                     case 'Z':
                        frame.addStackItem((TypeBinding)TypeBinding.BOOLEAN);
                  }
               } else if (descriptor[0] == '[') {
                  frame.addStackItem(new VerificationTypeInfo(0, descriptor));
               } else {
                  frame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray((char[])descriptor, 1, descriptor.length - 1)));
               }

               pc += 3;
               break;
            case -77:
               --frame.numberOfStackItems;
               pc += 3;
               break;
            case -76:
               index = this.u2At(bytecodes, 1, pc);
               i = this.u2At(poolContents, 3, constantPoolOffsets[index]);
               utf8index = this.u2At(poolContents, 3, constantPoolOffsets[i]);
               descriptor = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               --frame.numberOfStackItems;
               if (descriptor.length == 1) {
                  switch (descriptor[0]) {
                     case 'B':
                        frame.addStackItem((TypeBinding)TypeBinding.BYTE);
                        break;
                     case 'C':
                        frame.addStackItem((TypeBinding)TypeBinding.CHAR);
                        break;
                     case 'D':
                        frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
                        break;
                     case 'F':
                        frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
                        break;
                     case 'I':
                        frame.addStackItem((TypeBinding)TypeBinding.INT);
                        break;
                     case 'J':
                        frame.addStackItem((TypeBinding)TypeBinding.LONG);
                        break;
                     case 'S':
                        frame.addStackItem((TypeBinding)TypeBinding.SHORT);
                        break;
                     case 'Z':
                        frame.addStackItem((TypeBinding)TypeBinding.BOOLEAN);
                  }
               } else if (descriptor[0] == '[') {
                  frame.addStackItem(new VerificationTypeInfo(0, descriptor));
               } else {
                  frame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray((char[])descriptor, 1, descriptor.length - 1)));
               }

               pc += 3;
               break;
            case -75:
               frame.numberOfStackItems -= 2;
               pc += 3;
               break;
            case -74:
               index = this.u2At(bytecodes, 1, pc);
               i = this.u2At(poolContents, 3, constantPoolOffsets[index]);
               utf8index = this.u2At(poolContents, 3, constantPoolOffsets[i]);
               descriptor = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               utf8index = this.u2At(poolContents, 1, constantPoolOffsets[i]);
               this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               frame.numberOfStackItems -= this.getParametersCount(descriptor) + 1;
               returnType = this.getReturnType(descriptor);
               if (returnType.length == 1) {
                  switch (returnType[0]) {
                     case 'B':
                        frame.addStackItem((TypeBinding)TypeBinding.BYTE);
                        break;
                     case 'C':
                        frame.addStackItem((TypeBinding)TypeBinding.CHAR);
                        break;
                     case 'D':
                        frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
                        break;
                     case 'F':
                        frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
                        break;
                     case 'I':
                        frame.addStackItem((TypeBinding)TypeBinding.INT);
                        break;
                     case 'J':
                        frame.addStackItem((TypeBinding)TypeBinding.LONG);
                        break;
                     case 'S':
                        frame.addStackItem((TypeBinding)TypeBinding.SHORT);
                        break;
                     case 'Z':
                        frame.addStackItem((TypeBinding)TypeBinding.BOOLEAN);
                  }
               } else if (returnType[0] == '[') {
                  frame.addStackItem(new VerificationTypeInfo(0, returnType));
               } else {
                  frame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray((char[])returnType, 1, returnType.length - 1)));
               }

               pc += 3;
               break;
            case -73:
               index = this.u2At(bytecodes, 1, pc);
               i = this.u2At(poolContents, 3, constantPoolOffsets[index]);
               utf8index = this.u2At(poolContents, 3, constantPoolOffsets[i]);
               descriptor = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               utf8index = this.u2At(poolContents, 1, constantPoolOffsets[i]);
               char[] name = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               frame.numberOfStackItems -= this.getParametersCount(descriptor);
               if (CharOperation.equals(ConstantPool.Init, name)) {
                  frame.stackItems[frame.numberOfStackItems - 1].tag = 7;
               }

               --frame.numberOfStackItems;
               returnType = this.getReturnType(descriptor);
               if (returnType.length == 1) {
                  switch (returnType[0]) {
                     case 'B':
                        frame.addStackItem((TypeBinding)TypeBinding.BYTE);
                        break;
                     case 'C':
                        frame.addStackItem((TypeBinding)TypeBinding.CHAR);
                        break;
                     case 'D':
                        frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
                        break;
                     case 'F':
                        frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
                        break;
                     case 'I':
                        frame.addStackItem((TypeBinding)TypeBinding.INT);
                        break;
                     case 'J':
                        frame.addStackItem((TypeBinding)TypeBinding.LONG);
                        break;
                     case 'S':
                        frame.addStackItem((TypeBinding)TypeBinding.SHORT);
                        break;
                     case 'Z':
                        frame.addStackItem((TypeBinding)TypeBinding.BOOLEAN);
                  }
               } else if (returnType[0] == '[') {
                  frame.addStackItem(new VerificationTypeInfo(0, returnType));
               } else {
                  frame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray((char[])returnType, 1, returnType.length - 1)));
               }

               pc += 3;
               break;
            case -72:
               index = this.u2At(bytecodes, 1, pc);
               i = this.u2At(poolContents, 3, constantPoolOffsets[index]);
               utf8index = this.u2At(poolContents, 3, constantPoolOffsets[i]);
               descriptor = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               utf8index = this.u2At(poolContents, 1, constantPoolOffsets[i]);
               this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               frame.numberOfStackItems -= this.getParametersCount(descriptor);
               returnType = this.getReturnType(descriptor);
               if (returnType.length == 1) {
                  switch (returnType[0]) {
                     case 'B':
                        frame.addStackItem((TypeBinding)TypeBinding.BYTE);
                        break;
                     case 'C':
                        frame.addStackItem((TypeBinding)TypeBinding.CHAR);
                        break;
                     case 'D':
                        frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
                        break;
                     case 'F':
                        frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
                        break;
                     case 'I':
                        frame.addStackItem((TypeBinding)TypeBinding.INT);
                        break;
                     case 'J':
                        frame.addStackItem((TypeBinding)TypeBinding.LONG);
                        break;
                     case 'S':
                        frame.addStackItem((TypeBinding)TypeBinding.SHORT);
                        break;
                     case 'Z':
                        frame.addStackItem((TypeBinding)TypeBinding.BOOLEAN);
                  }
               } else if (returnType[0] == '[') {
                  frame.addStackItem(new VerificationTypeInfo(0, returnType));
               } else {
                  frame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray((char[])returnType, 1, returnType.length - 1)));
               }

               pc += 3;
               break;
            case -71:
               index = this.u2At(bytecodes, 1, pc);
               i = this.u2At(poolContents, 3, constantPoolOffsets[index]);
               utf8index = this.u2At(poolContents, 3, constantPoolOffsets[i]);
               descriptor = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               utf8index = this.u2At(poolContents, 1, constantPoolOffsets[i]);
               this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               frame.numberOfStackItems -= this.getParametersCount(descriptor) + 1;
               returnType = this.getReturnType(descriptor);
               if (returnType.length == 1) {
                  switch (returnType[0]) {
                     case 'B':
                        frame.addStackItem((TypeBinding)TypeBinding.BYTE);
                        break;
                     case 'C':
                        frame.addStackItem((TypeBinding)TypeBinding.CHAR);
                        break;
                     case 'D':
                        frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
                        break;
                     case 'F':
                        frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
                        break;
                     case 'I':
                        frame.addStackItem((TypeBinding)TypeBinding.INT);
                        break;
                     case 'J':
                        frame.addStackItem((TypeBinding)TypeBinding.LONG);
                        break;
                     case 'S':
                        frame.addStackItem((TypeBinding)TypeBinding.SHORT);
                        break;
                     case 'Z':
                        frame.addStackItem((TypeBinding)TypeBinding.BOOLEAN);
                  }
               } else if (returnType[0] == '[') {
                  frame.addStackItem(new VerificationTypeInfo(0, returnType));
               } else {
                  frame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray((char[])returnType, 1, returnType.length - 1)));
               }

               pc += 5;
               break;
            case -70:
               index = this.u2At(bytecodes, 1, pc);
               i = this.u2At(poolContents, 3, constantPoolOffsets[index]);
               utf8index = this.u2At(poolContents, 3, constantPoolOffsets[i]);
               descriptor = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               frame.numberOfStackItems -= this.getParametersCount(descriptor);
               returnType = this.getReturnType(descriptor);
               if (returnType.length == 1) {
                  switch (returnType[0]) {
                     case 'B':
                        frame.addStackItem((TypeBinding)TypeBinding.BYTE);
                        break;
                     case 'C':
                        frame.addStackItem((TypeBinding)TypeBinding.CHAR);
                        break;
                     case 'D':
                        frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
                        break;
                     case 'F':
                        frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
                        break;
                     case 'I':
                        frame.addStackItem((TypeBinding)TypeBinding.INT);
                        break;
                     case 'J':
                        frame.addStackItem((TypeBinding)TypeBinding.LONG);
                        break;
                     case 'S':
                        frame.addStackItem((TypeBinding)TypeBinding.SHORT);
                        break;
                     case 'Z':
                        frame.addStackItem((TypeBinding)TypeBinding.BOOLEAN);
                  }
               } else if (returnType[0] == '[') {
                  frame.addStackItem(new VerificationTypeInfo(0, returnType));
               } else {
                  frame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray((char[])returnType, 1, returnType.length - 1)));
               }

               pc += 5;
               break;
            case -69:
               index = this.u2At(bytecodes, 1, pc);
               utf8index = this.u2At(poolContents, 1, constantPoolOffsets[index]);
               className = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               VerificationTypeInfo verificationTypeInfo = new VerificationTypeInfo(0, 8, className);
               verificationTypeInfo.offset = currentPC;
               frame.addStackItem(verificationTypeInfo);
               pc += 3;
               break;
            case -68:
               constantPoolName = null;
               switch (this.u1At(bytecodes, 1, pc)) {
                  case 4:
                     constantPoolName = new char[]{'[', 'Z'};
                     break;
                  case 5:
                     constantPoolName = new char[]{'[', 'C'};
                     break;
                  case 6:
                     constantPoolName = new char[]{'[', 'F'};
                     break;
                  case 7:
                     constantPoolName = new char[]{'[', 'D'};
                     break;
                  case 8:
                     constantPoolName = new char[]{'[', 'B'};
                     break;
                  case 9:
                     constantPoolName = new char[]{'[', 'S'};
                     break;
                  case 10:
                     constantPoolName = new char[]{'[', 'I'};
                     break;
                  case 11:
                     constantPoolName = new char[]{'[', 'J'};
               }

               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(1, constantPoolName);
               pc += 2;
               break;
            case -67:
               index = this.u2At(bytecodes, 1, pc);
               utf8index = this.u2At(poolContents, 1, constantPoolOffsets[index]);
               className = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               classNameLength = className.length;
               if (className[0] != '[') {
                  System.arraycopy(className, 0, constantPoolName = new char[classNameLength + 3], 2, classNameLength);
                  constantPoolName[0] = '[';
                  constantPoolName[1] = 'L';
                  constantPoolName[classNameLength + 2] = ';';
               } else {
                  System.arraycopy(className, 0, constantPoolName = new char[classNameLength + 1], 1, classNameLength);
                  constantPoolName[0] = '[';
               }

               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(0, constantPoolName);
               pc += 3;
               break;
            case -66:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.INT);
               ++pc;
               break;
            case -65:
               --frame.numberOfStackItems;
               ++pc;
               this.addRealJumpTarget(realJumpTarget, pc - codeOffset);
               break;
            case -64:
               index = this.u2At(bytecodes, 1, pc);
               utf8index = this.u2At(poolContents, 1, constantPoolOffsets[index]);
               className = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(0, className);
               pc += 3;
               break;
            case -63:
               frame.stackItems[frame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.INT);
               pc += 3;
               break;
            case -62:
            case -61:
               --frame.numberOfStackItems;
               ++pc;
               break;
            case -60:
               opcode = (byte)this.u1At(bytecodes, 1, pc);
               if (opcode == -124) {
                  pc += 6;
               } else {
                  index = this.u2At(bytecodes, 2, pc);
                  switch (opcode) {
                     case 21:
                        frame.addStackItem((TypeBinding)TypeBinding.INT);
                        break;
                     case 22:
                        frame.addStackItem((TypeBinding)TypeBinding.LONG);
                        break;
                     case 23:
                        frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
                        break;
                     case 24:
                        frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
                        break;
                     case 25:
                        localsN = frame.locals[index];
                        if (localsN == null) {
                           localsN = this.retrieveLocal(currentPC, index);
                        }

                        frame.addStackItem(localsN);
                        break;
                     case 54:
                        --frame.numberOfStackItems;
                        break;
                     case 55:
                        --frame.numberOfStackItems;
                        break;
                     case 56:
                        --frame.numberOfStackItems;
                        break;
                     case 57:
                        --frame.numberOfStackItems;
                        break;
                     case 58:
                        frame.locals[index] = frame.stackItems[frame.numberOfStackItems - 1];
                        --frame.numberOfStackItems;
                  }

                  pc += 4;
               }
               break;
            case -59:
               index = this.u2At(bytecodes, 1, pc);
               utf8index = this.u2At(poolContents, 1, constantPoolOffsets[index]);
               className = this.utf8At(poolContents, constantPoolOffsets[utf8index] + 3, this.u2At(poolContents, 1, constantPoolOffsets[utf8index]));
               int dimensions = this.u1At(bytecodes, 3, pc);
               frame.numberOfStackItems -= dimensions;
               classNameLength = className.length;
               constantPoolName = new char[classNameLength];
               System.arraycopy(className, 0, constantPoolName, 0, classNameLength);
               frame.addStackItem(new VerificationTypeInfo(0, constantPoolName));
               pc += 4;
               break;
            case -58:
            case -57:
               --frame.numberOfStackItems;
               this.addRealJumpTarget(realJumpTarget, currentPC + this.i2At(bytecodes, 1, pc));
               pc += 3;
               break;
            case -56:
               this.addRealJumpTarget(realJumpTarget, currentPC + this.i4At(bytecodes, 1, pc));
               pc += 5;
               this.addRealJumpTarget(realJumpTarget, pc - codeOffset);
               break;
            case 0:
               ++pc;
               break;
            case 1:
               frame.addStackItem((TypeBinding)TypeBinding.NULL);
               ++pc;
               break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
               frame.addStackItem((TypeBinding)TypeBinding.INT);
               ++pc;
               break;
            case 9:
            case 10:
               frame.addStackItem((TypeBinding)TypeBinding.LONG);
               ++pc;
               break;
            case 11:
            case 12:
            case 13:
               frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
               ++pc;
               break;
            case 14:
            case 15:
               frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
               ++pc;
               break;
            case 16:
               frame.addStackItem((TypeBinding)TypeBinding.BYTE);
               pc += 2;
               break;
            case 17:
               frame.addStackItem((TypeBinding)TypeBinding.SHORT);
               pc += 3;
               break;
            case 18:
               index = this.u1At(bytecodes, 1, pc);
               switch (this.u1At(poolContents, 0, constantPoolOffsets[index])) {
                  case 3:
                     frame.addStackItem((TypeBinding)TypeBinding.INT);
                     break;
                  case 4:
                     frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
                  case 5:
                  case 6:
                  default:
                     break;
                  case 7:
                     frame.addStackItem(new VerificationTypeInfo(16, ConstantPool.JavaLangClassConstantPoolName));
                     break;
                  case 8:
                     frame.addStackItem(new VerificationTypeInfo(11, ConstantPool.JavaLangStringConstantPoolName));
               }

               pc += 2;
               break;
            case 19:
               index = this.u2At(bytecodes, 1, pc);
               switch (this.u1At(poolContents, 0, constantPoolOffsets[index])) {
                  case 3:
                     frame.addStackItem((TypeBinding)TypeBinding.INT);
                     break;
                  case 4:
                     frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
                  case 5:
                  case 6:
                  default:
                     break;
                  case 7:
                     frame.addStackItem(new VerificationTypeInfo(16, ConstantPool.JavaLangClassConstantPoolName));
                     break;
                  case 8:
                     frame.addStackItem(new VerificationTypeInfo(11, ConstantPool.JavaLangStringConstantPoolName));
               }

               pc += 3;
               break;
            case 20:
               index = this.u2At(bytecodes, 1, pc);
               switch (this.u1At(poolContents, 0, constantPoolOffsets[index])) {
                  case 5:
                     frame.addStackItem((TypeBinding)TypeBinding.LONG);
                     break;
                  case 6:
                     frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
               }

               pc += 3;
               break;
            case 21:
               frame.addStackItem((TypeBinding)TypeBinding.INT);
               pc += 2;
               break;
            case 22:
               frame.addStackItem((TypeBinding)TypeBinding.LONG);
               pc += 2;
               break;
            case 23:
               frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
               pc += 2;
               break;
            case 24:
               frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
               pc += 2;
               break;
            case 25:
               index = this.u1At(bytecodes, 1, pc);
               localsN = this.retrieveLocal(currentPC, index);
               frame.addStackItem(localsN);
               pc += 2;
               break;
            case 26:
            case 27:
            case 28:
            case 29:
               frame.addStackItem((TypeBinding)TypeBinding.INT);
               ++pc;
               break;
            case 30:
            case 31:
            case 32:
            case 33:
               frame.addStackItem((TypeBinding)TypeBinding.LONG);
               ++pc;
               break;
            case 34:
            case 35:
            case 36:
            case 37:
               frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
               ++pc;
               break;
            case 38:
            case 39:
            case 40:
            case 41:
               frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
               ++pc;
               break;
            case 42:
               VerificationTypeInfo locals0 = frame.locals[0];
               if (locals0 == null || locals0.tag != 6) {
                  locals0 = this.retrieveLocal(currentPC, 0);
               }

               frame.addStackItem(locals0);
               ++pc;
               break;
            case 43:
               VerificationTypeInfo locals1 = this.retrieveLocal(currentPC, 1);
               frame.addStackItem(locals1);
               ++pc;
               break;
            case 44:
               VerificationTypeInfo locals2 = this.retrieveLocal(currentPC, 2);
               frame.addStackItem(locals2);
               ++pc;
               break;
            case 45:
               VerificationTypeInfo locals3 = this.retrieveLocal(currentPC, 3);
               frame.addStackItem(locals3);
               ++pc;
               break;
            case 46:
               frame.numberOfStackItems -= 2;
               frame.addStackItem((TypeBinding)TypeBinding.INT);
               ++pc;
               break;
            case 47:
               frame.numberOfStackItems -= 2;
               frame.addStackItem((TypeBinding)TypeBinding.LONG);
               ++pc;
               break;
            case 48:
               frame.numberOfStackItems -= 2;
               frame.addStackItem((TypeBinding)TypeBinding.FLOAT);
               ++pc;
               break;
            case 49:
               frame.numberOfStackItems -= 2;
               frame.addStackItem((TypeBinding)TypeBinding.DOUBLE);
               ++pc;
               break;
            case 50:
               --frame.numberOfStackItems;
               frame.replaceWithElementType();
               ++pc;
               break;
            case 51:
               frame.numberOfStackItems -= 2;
               frame.addStackItem((TypeBinding)TypeBinding.BYTE);
               ++pc;
               break;
            case 52:
               frame.numberOfStackItems -= 2;
               frame.addStackItem((TypeBinding)TypeBinding.CHAR);
               ++pc;
               break;
            case 53:
               frame.numberOfStackItems -= 2;
               frame.addStackItem((TypeBinding)TypeBinding.SHORT);
               ++pc;
               break;
            case 54:
            case 55:
            case 56:
            case 57:
               --frame.numberOfStackItems;
               pc += 2;
               break;
            case 58:
               this.u1At(bytecodes, 1, pc);
               --frame.numberOfStackItems;
               pc += 2;
               break;
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 76:
            case 77:
            case 78:
               --frame.numberOfStackItems;
               ++pc;
               break;
            case 75:
               frame.locals[0] = frame.stackItems[frame.numberOfStackItems - 1];
               --frame.numberOfStackItems;
               ++pc;
               break;
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
               frame.numberOfStackItems -= 3;
               ++pc;
               break;
            case 87:
               --frame.numberOfStackItems;
               ++pc;
               break;
            case 88:
               numberOfStackItems = frame.numberOfStackItems;
               switch (frame.stackItems[numberOfStackItems - 1].id()) {
                  case 7:
                  case 8:
                     --frame.numberOfStackItems;
                     break;
                  default:
                     frame.numberOfStackItems -= 2;
               }

               ++pc;
               break;
            case 89:
               frame.addStackItem(frame.stackItems[frame.numberOfStackItems - 1]);
               ++pc;
               break;
            case 90:
               info = frame.stackItems[frame.numberOfStackItems - 1];
               --frame.numberOfStackItems;
               info2 = frame.stackItems[frame.numberOfStackItems - 1];
               --frame.numberOfStackItems;
               frame.addStackItem(info);
               frame.addStackItem(info2);
               frame.addStackItem(info);
               ++pc;
               break;
            case 91:
               info = frame.stackItems[frame.numberOfStackItems - 1];
               --frame.numberOfStackItems;
               info2 = frame.stackItems[frame.numberOfStackItems - 1];
               --frame.numberOfStackItems;
               switch (info2.id()) {
                  case 7:
                  case 8:
                     frame.addStackItem(info);
                     frame.addStackItem(info2);
                     frame.addStackItem(info);
                     break;
                  default:
                     numberOfStackItems = frame.numberOfStackItems;
                     info3 = frame.stackItems[numberOfStackItems - 1];
                     --frame.numberOfStackItems;
                     frame.addStackItem(info);
                     frame.addStackItem(info3);
                     frame.addStackItem(info2);
                     frame.addStackItem(info);
               }

               ++pc;
               break;
            case 92:
               info = frame.stackItems[frame.numberOfStackItems - 1];
               --frame.numberOfStackItems;
               switch (info.id()) {
                  case 7:
                  case 8:
                     frame.addStackItem(info);
                     frame.addStackItem(info);
                     break;
                  default:
                     info2 = frame.stackItems[frame.numberOfStackItems - 1];
                     --frame.numberOfStackItems;
                     frame.addStackItem(info2);
                     frame.addStackItem(info);
                     frame.addStackItem(info2);
                     frame.addStackItem(info);
               }

               ++pc;
               break;
            case 93:
               info = frame.stackItems[frame.numberOfStackItems - 1];
               --frame.numberOfStackItems;
               info2 = frame.stackItems[frame.numberOfStackItems - 1];
               --frame.numberOfStackItems;
               switch (info.id()) {
                  case 7:
                  case 8:
                     frame.addStackItem(info);
                     frame.addStackItem(info2);
                     frame.addStackItem(info);
                     break;
                  default:
                     info3 = frame.stackItems[frame.numberOfStackItems - 1];
                     --frame.numberOfStackItems;
                     frame.addStackItem(info2);
                     frame.addStackItem(info);
                     frame.addStackItem(info3);
                     frame.addStackItem(info2);
                     frame.addStackItem(info);
               }

               ++pc;
               break;
            case 94:
               numberOfStackItems = frame.numberOfStackItems;
               info = frame.stackItems[numberOfStackItems - 1];
               --frame.numberOfStackItems;
               info2 = frame.stackItems[frame.numberOfStackItems - 1];
               --frame.numberOfStackItems;
               label404:
               switch (info.id()) {
                  case 7:
                  case 8:
                     switch (info2.id()) {
                        case 7:
                        case 8:
                           frame.addStackItem(info);
                           frame.addStackItem(info2);
                           frame.addStackItem(info);
                           break label404;
                        default:
                           numberOfStackItems = frame.numberOfStackItems;
                           info3 = frame.stackItems[numberOfStackItems - 1];
                           --frame.numberOfStackItems;
                           frame.addStackItem(info);
                           frame.addStackItem(info3);
                           frame.addStackItem(info2);
                           frame.addStackItem(info);
                           break label404;
                     }
                  default:
                     numberOfStackItems = frame.numberOfStackItems;
                     info3 = frame.stackItems[numberOfStackItems - 1];
                     --frame.numberOfStackItems;
                     switch (info3.id()) {
                        case 7:
                        case 8:
                           frame.addStackItem(info2);
                           frame.addStackItem(info);
                           frame.addStackItem(info3);
                           frame.addStackItem(info2);
                           frame.addStackItem(info);
                           break;
                        default:
                           numberOfStackItems = frame.numberOfStackItems;
                           VerificationTypeInfo info4 = frame.stackItems[numberOfStackItems - 1];
                           --frame.numberOfStackItems;
                           frame.addStackItem(info2);
                           frame.addStackItem(info);
                           frame.addStackItem(info4);
                           frame.addStackItem(info3);
                           frame.addStackItem(info2);
                           frame.addStackItem(info);
                     }
               }

               ++pc;
               break;
            case 95:
               numberOfStackItems = frame.numberOfStackItems;
               info = frame.stackItems[numberOfStackItems - 1];
               info2 = frame.stackItems[numberOfStackItems - 2];
               frame.stackItems[numberOfStackItems - 1] = info2;
               frame.stackItems[numberOfStackItems - 2] = info;
               ++pc;
               break;
            case 116:
            case 117:
            case 118:
            case 119:
               ++pc;
         }
      } while(pc < codeLength + codeOffset);

      return this.filterFakeFrames(realJumpTarget, frames, codeLength);
   }

   private void addRealJumpTarget(Set realJumpTarget, int pc) {
      realJumpTarget.add(pc);
   }

   private void add(Map frames, StackMapFrame frame) {
      frames.put(frame.pc, frame);
   }

   private final int u1At(byte[] reference, int relativeOffset, int structOffset) {
      return reference[relativeOffset + structOffset] & 255;
   }

   private final int u2At(byte[] reference, int relativeOffset, int structOffset) {
      int position = relativeOffset + structOffset;
      return ((reference[position++] & 255) << 8) + (reference[position] & 255);
   }

   private final long u4At(byte[] reference, int relativeOffset, int structOffset) {
      int position = relativeOffset + structOffset;
      return (((long)reference[position++] & 255L) << 24) + (long)((reference[position++] & 255) << 16) + (long)((reference[position++] & 255) << 8) + (long)(reference[position] & 255);
   }

   private final int i2At(byte[] reference, int relativeOffset, int structOffset) {
      int position = relativeOffset + structOffset;
      return (reference[position++] << 8) + (reference[position] & 255);
   }

   public char[] utf8At(byte[] reference, int absoluteOffset, int bytesAvailable) {
      int length = bytesAvailable;
      char[] outputBuf = new char[bytesAvailable];
      int outputPos = 0;

      int x;
      for(int readOffset = absoluteOffset; length != 0; outputBuf[outputPos++] = (char)x) {
         x = reference[readOffset++] & 255;
         --length;
         if ((128 & x) != 0) {
            if ((x & 32) != 0) {
               length -= 2;
               x = (x & 15) << 12 | (reference[readOffset++] & 63) << 6 | reference[readOffset++] & 63;
            } else {
               --length;
               x = (x & 31) << 6 | reference[readOffset++] & 63;
            }
         }
      }

      if (outputPos != bytesAvailable) {
         System.arraycopy(outputBuf, 0, outputBuf = new char[outputPos], 0, outputPos);
      }

      return outputBuf;
   }
}
