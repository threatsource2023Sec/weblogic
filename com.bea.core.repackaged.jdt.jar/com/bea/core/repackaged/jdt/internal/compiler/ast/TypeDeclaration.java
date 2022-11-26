package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.InitializationFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import com.bea.core.repackaged.jdt.internal.compiler.impl.StringConstant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.CompilationUnitScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MemberTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.NestedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Parser;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilation;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortMethod;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortType;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemSeverities;
import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleSetOfCharArray;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;

public class TypeDeclaration extends Statement implements ProblemSeverities, ReferenceContext {
   public static final int CLASS_DECL = 1;
   public static final int INTERFACE_DECL = 2;
   public static final int ENUM_DECL = 3;
   public static final int ANNOTATION_TYPE_DECL = 4;
   public int modifiers = 0;
   public int modifiersSourceStart;
   public int functionalExpressionsCount = 0;
   public Annotation[] annotations;
   public char[] name;
   public TypeReference superclass;
   public TypeReference[] superInterfaces;
   public FieldDeclaration[] fields;
   public AbstractMethodDeclaration[] methods;
   public TypeDeclaration[] memberTypes;
   public SourceTypeBinding binding;
   public ClassScope scope;
   public MethodScope initializerScope;
   public MethodScope staticInitializerScope;
   public boolean ignoreFurtherInvestigation = false;
   public int maxFieldCount;
   public int declarationSourceStart;
   public int declarationSourceEnd;
   public int bodyStart;
   public int bodyEnd;
   public CompilationResult compilationResult;
   public MethodDeclaration[] missingAbstractMethods;
   public Javadoc javadoc;
   public QualifiedAllocationExpression allocation;
   public TypeDeclaration enclosingType;
   public FieldBinding enumValuesSyntheticfield;
   public int enumConstantsCounter;
   public TypeParameter[] typeParameters;

   public TypeDeclaration(CompilationResult compilationResult) {
      this.compilationResult = compilationResult;
   }

   public void abort(int abortLevel, CategorizedProblem problem) {
      switch (abortLevel) {
         case 2:
            throw new AbortCompilation(this.compilationResult, problem);
         case 4:
            throw new AbortCompilationUnit(this.compilationResult, problem);
         case 16:
            throw new AbortMethod(this.compilationResult, problem);
         default:
            throw new AbortType(this.compilationResult, problem);
      }
   }

   public final void addClinit() {
      if (this.needClassInitMethod()) {
         AbstractMethodDeclaration[] methodDeclarations;
         if ((methodDeclarations = this.methods) == null) {
            int length = false;
            methodDeclarations = new AbstractMethodDeclaration[1];
         } else {
            int length = methodDeclarations.length;
            System.arraycopy(methodDeclarations, 0, methodDeclarations = new AbstractMethodDeclaration[length + 1], 1, length);
         }

         Clinit clinit = new Clinit(this.compilationResult);
         methodDeclarations[0] = clinit;
         clinit.declarationSourceStart = clinit.sourceStart = this.sourceStart;
         clinit.declarationSourceEnd = clinit.sourceEnd = this.sourceEnd;
         clinit.bodyEnd = this.sourceEnd;
         this.methods = methodDeclarations;
      }

   }

   public MethodDeclaration addMissingAbstractMethodFor(MethodBinding methodBinding) {
      TypeBinding[] argumentTypes = methodBinding.parameters;
      int argumentsLength = argumentTypes.length;
      MethodDeclaration methodDeclaration = new MethodDeclaration(this.compilationResult);
      methodDeclaration.selector = methodBinding.selector;
      methodDeclaration.sourceStart = this.sourceStart;
      methodDeclaration.sourceEnd = this.sourceEnd;
      methodDeclaration.modifiers = methodBinding.getAccessFlags() & -1025;
      if (argumentsLength > 0) {
         String baseName = "arg";
         Argument[] arguments = methodDeclaration.arguments = new Argument[argumentsLength];
         int i = argumentsLength;

         while(true) {
            --i;
            if (i < 0) {
               break;
            }

            arguments[i] = new Argument((baseName + i).toCharArray(), 0L, (TypeReference)null, 0);
         }
      }

      if (this.missingAbstractMethods == null) {
         this.missingAbstractMethods = new MethodDeclaration[]{methodDeclaration};
      } else {
         MethodDeclaration[] newMethods;
         System.arraycopy(this.missingAbstractMethods, 0, newMethods = new MethodDeclaration[this.missingAbstractMethods.length + 1], 1, this.missingAbstractMethods.length);
         newMethods[0] = methodDeclaration;
         this.missingAbstractMethods = newMethods;
      }

      methodDeclaration.binding = new MethodBinding(methodDeclaration.modifiers | 4096, methodBinding.selector, methodBinding.returnType, argumentsLength == 0 ? Binding.NO_PARAMETERS : argumentTypes, methodBinding.thrownExceptions, this.binding);
      methodDeclaration.scope = new MethodScope(this.scope, methodDeclaration, true);
      methodDeclaration.bindArguments();
      return methodDeclaration;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      if (this.ignoreFurtherInvestigation) {
         return flowInfo;
      } else {
         try {
            if ((flowInfo.tagBits & 1) == 0) {
               this.bits |= Integer.MIN_VALUE;
               LocalTypeBinding localType = (LocalTypeBinding)this.binding;
               localType.setConstantPoolName(currentScope.compilationUnitScope().computeConstantPoolName(localType));
            }

            this.manageEnclosingInstanceAccessIfNecessary(currentScope, flowInfo);
            this.updateMaxFieldCount();
            this.internalAnalyseCode(flowContext, flowInfo);
         } catch (AbortType var5) {
            this.ignoreFurtherInvestigation = true;
         }

         return flowInfo;
      }
   }

   public void analyseCode(ClassScope enclosingClassScope) {
      if (!this.ignoreFurtherInvestigation) {
         try {
            this.updateMaxFieldCount();
            this.internalAnalyseCode((FlowContext)null, FlowInfo.initial(this.maxFieldCount));
         } catch (AbortType var2) {
            this.ignoreFurtherInvestigation = true;
         }

      }
   }

   public void analyseCode(ClassScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      if (!this.ignoreFurtherInvestigation) {
         try {
            if ((flowInfo.tagBits & 1) == 0) {
               this.bits |= Integer.MIN_VALUE;
               LocalTypeBinding localType = (LocalTypeBinding)this.binding;
               localType.setConstantPoolName(currentScope.compilationUnitScope().computeConstantPoolName(localType));
            }

            this.manageEnclosingInstanceAccessIfNecessary(currentScope, flowInfo);
            this.updateMaxFieldCount();
            this.internalAnalyseCode(flowContext, flowInfo);
         } catch (AbortType var5) {
            this.ignoreFurtherInvestigation = true;
         }

      }
   }

   public void analyseCode(CompilationUnitScope unitScope) {
      if (!this.ignoreFurtherInvestigation) {
         try {
            this.internalAnalyseCode((FlowContext)null, FlowInfo.initial(this.maxFieldCount));
         } catch (AbortType var2) {
            this.ignoreFurtherInvestigation = true;
         }

      }
   }

   public boolean checkConstructors(Parser var1) {
      // $FF: Couldn't be decompiled
   }

   public CompilationResult compilationResult() {
      return this.compilationResult;
   }

   public ConstructorDeclaration createDefaultConstructor(boolean needExplicitConstructorCall, boolean needToInsert) {
      ConstructorDeclaration constructor = new ConstructorDeclaration(this.compilationResult);
      constructor.bits |= 128;
      constructor.selector = this.name;
      constructor.modifiers = this.modifiers & 7;
      constructor.declarationSourceStart = constructor.sourceStart = this.sourceStart;
      constructor.declarationSourceEnd = constructor.sourceEnd = constructor.bodyEnd = this.sourceEnd;
      if (needExplicitConstructorCall) {
         constructor.constructorCall = SuperReference.implicitSuperConstructorCall();
         constructor.constructorCall.sourceStart = this.sourceStart;
         constructor.constructorCall.sourceEnd = this.sourceEnd;
      }

      if (needToInsert) {
         if (this.methods == null) {
            this.methods = new AbstractMethodDeclaration[]{constructor};
         } else {
            AbstractMethodDeclaration[] newMethods;
            System.arraycopy(this.methods, 0, newMethods = new AbstractMethodDeclaration[this.methods.length + 1], 1, this.methods.length);
            newMethods[0] = constructor;
            this.methods = newMethods;
         }
      }

      return constructor;
   }

   public MethodBinding createDefaultConstructorWithBinding(MethodBinding inheritedConstructorBinding, boolean eraseThrownExceptions) {
      String baseName = "$anonymous";
      TypeBinding[] argumentTypes = inheritedConstructorBinding.parameters;
      int argumentsLength = argumentTypes.length;
      ConstructorDeclaration constructor = new ConstructorDeclaration(this.compilationResult);
      constructor.selector = new char[]{'x'};
      constructor.sourceStart = this.sourceStart;
      constructor.sourceEnd = this.sourceEnd;
      int newModifiers = this.modifiers & 7;
      if (inheritedConstructorBinding.isVarargs()) {
         newModifiers |= 128;
      }

      constructor.modifiers = newModifiers;
      constructor.bits |= 128;
      int i;
      if (argumentsLength > 0) {
         Argument[] arguments = constructor.arguments = new Argument[argumentsLength];
         i = argumentsLength;

         while(true) {
            --i;
            if (i < 0) {
               break;
            }

            arguments[i] = new Argument((baseName + i).toCharArray(), 0L, (TypeReference)null, 0);
         }
      }

      constructor.constructorCall = SuperReference.implicitSuperConstructorCall();
      constructor.constructorCall.sourceStart = this.sourceStart;
      constructor.constructorCall.sourceEnd = this.sourceEnd;
      if (argumentsLength > 0) {
         Expression[] args = constructor.constructorCall.arguments = new Expression[argumentsLength];
         i = argumentsLength;

         while(true) {
            --i;
            if (i < 0) {
               break;
            }

            args[i] = new SingleNameReference((baseName + i).toCharArray(), 0L);
         }
      }

      if (this.methods == null) {
         this.methods = new AbstractMethodDeclaration[]{constructor};
      } else {
         AbstractMethodDeclaration[] newMethods;
         System.arraycopy(this.methods, 0, newMethods = new AbstractMethodDeclaration[this.methods.length + 1], 1, this.methods.length);
         newMethods[0] = constructor;
         this.methods = newMethods;
      }

      ReferenceBinding[] thrownExceptions = eraseThrownExceptions ? this.scope.environment().convertToRawTypes(inheritedConstructorBinding.thrownExceptions, true, true) : inheritedConstructorBinding.thrownExceptions;
      SourceTypeBinding sourceType = this.binding;
      constructor.binding = new MethodBinding(constructor.modifiers, argumentsLength == 0 ? Binding.NO_PARAMETERS : argumentTypes, thrownExceptions, sourceType);
      MethodBinding var10000 = constructor.binding;
      var10000.tagBits |= inheritedConstructorBinding.tagBits & 128L;
      var10000 = constructor.binding;
      var10000.modifiers |= 67108864;
      if (inheritedConstructorBinding.parameterNonNullness != null && argumentsLength > 0) {
         int len = inheritedConstructorBinding.parameterNonNullness.length;
         System.arraycopy(inheritedConstructorBinding.parameterNonNullness, 0, constructor.binding.parameterNonNullness = new Boolean[len], 0, len);
      }

      constructor.scope = new MethodScope(this.scope, constructor, true);
      constructor.bindArguments();
      constructor.constructorCall.resolve(constructor.scope);
      MethodBinding[] methodBindings = sourceType.methods();
      int length;
      System.arraycopy(methodBindings, 0, methodBindings = new MethodBinding[(length = methodBindings.length) + 1], 1, length);
      methodBindings[0] = constructor.binding;
      ++length;
      if (length > 1) {
         ReferenceBinding.sortMethods(methodBindings, 0, length);
      }

      sourceType.setMethods(methodBindings);
      return constructor.binding;
   }

   public FieldDeclaration declarationOf(FieldBinding fieldBinding) {
      if (fieldBinding != null && this.fields != null) {
         int i = 0;

         for(int max = this.fields.length; i < max; ++i) {
            FieldDeclaration fieldDecl;
            if ((fieldDecl = this.fields[i]).binding == fieldBinding) {
               return fieldDecl;
            }
         }
      }

      return null;
   }

   public TypeDeclaration declarationOf(MemberTypeBinding memberTypeBinding) {
      if (memberTypeBinding != null && this.memberTypes != null) {
         int i = 0;

         for(int max = this.memberTypes.length; i < max; ++i) {
            TypeDeclaration memberTypeDecl;
            if (TypeBinding.equalsEquals((memberTypeDecl = this.memberTypes[i]).binding, memberTypeBinding)) {
               return memberTypeDecl;
            }
         }
      }

      return null;
   }

   public AbstractMethodDeclaration declarationOf(MethodBinding methodBinding) {
      if (methodBinding != null && this.methods != null) {
         int i = 0;

         for(int max = this.methods.length; i < max; ++i) {
            AbstractMethodDeclaration methodDecl;
            if ((methodDecl = this.methods[i]).binding == methodBinding) {
               return methodDecl;
            }
         }
      }

      return null;
   }

   public TypeDeclaration declarationOfType(char[][] typeName) {
      int typeNameLength = typeName.length;
      if (typeNameLength >= 1 && CharOperation.equals(typeName[0], this.name)) {
         if (typeNameLength == 1) {
            return this;
         } else {
            char[][] subTypeName = new char[typeNameLength - 1][];
            System.arraycopy(typeName, 1, subTypeName, 0, typeNameLength - 1);

            for(int i = 0; i < this.memberTypes.length; ++i) {
               TypeDeclaration typeDecl = this.memberTypes[i].declarationOfType(subTypeName);
               if (typeDecl != null) {
                  return typeDecl;
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   public CompilationUnitDeclaration getCompilationUnitDeclaration() {
      return this.scope != null ? this.scope.compilationUnitScope().referenceContext : null;
   }

   public void generateCode(ClassFile enclosingClassFile) {
      if ((this.bits & 8192) == 0) {
         this.bits |= 8192;
         if (this.ignoreFurtherInvestigation) {
            if (this.binding != null) {
               ClassFile.createProblemType(this, this.scope.referenceCompilationUnit().compilationResult);
            }
         } else {
            try {
               ClassFile classFile = ClassFile.getNewInstance(this.binding);
               classFile.initialize(this.binding, enclosingClassFile, false);
               if (this.binding.isMemberType()) {
                  classFile.recordInnerClasses(this.binding);
               } else if (this.binding.isLocalType()) {
                  enclosingClassFile.recordInnerClasses(this.binding);
                  classFile.recordInnerClasses(this.binding);
               }

               TypeVariableBinding[] typeVariables = this.binding.typeVariables();
               int i = 0;

               int max;
               for(max = typeVariables.length; i < max; ++i) {
                  TypeVariableBinding typeVariableBinding = typeVariables[i];
                  if ((typeVariableBinding.tagBits & 2048L) != 0L) {
                     Util.recordNestedType(classFile, typeVariableBinding);
                  }
               }

               classFile.addFieldInfos();
               if (this.memberTypes != null) {
                  i = 0;

                  for(max = this.memberTypes.length; i < max; ++i) {
                     TypeDeclaration memberType = this.memberTypes[i];
                     classFile.recordInnerClasses(memberType.binding);
                     memberType.generateCode(this.scope, classFile);
                  }
               }

               classFile.setForMethodInfos();
               if (this.methods != null) {
                  i = 0;

                  for(max = this.methods.length; i < max; ++i) {
                     this.methods[i].generateCode(this.scope, classFile);
                  }
               }

               classFile.addSpecialMethods();
               if (this.ignoreFurtherInvestigation) {
                  throw new AbortType(this.scope.referenceCompilationUnit().compilationResult, (CategorizedProblem)null);
               }

               classFile.addAttributes();
               this.scope.referenceCompilationUnit().compilationResult.record(this.binding.constantPoolName(), classFile);
            } catch (AbortType var7) {
               if (this.binding == null) {
                  return;
               }

               ClassFile.createProblemType(this, this.scope.referenceCompilationUnit().compilationResult);
            }

         }
      }
   }

   public void generateCode(BlockScope blockScope, CodeStream codeStream) {
      if ((this.bits & Integer.MIN_VALUE) != 0) {
         if ((this.bits & 8192) == 0) {
            int pc = codeStream.position;
            if (this.binding != null) {
               SyntheticArgumentBinding[] enclosingInstances = ((NestedTypeBinding)this.binding).syntheticEnclosingInstances();
               int i = 0;
               int slotSize = 0;

               for(int count = enclosingInstances == null ? 0 : enclosingInstances.length; i < count; ++i) {
                  SyntheticArgumentBinding enclosingInstance = enclosingInstances[i];
                  ++slotSize;
                  enclosingInstance.resolvedPosition = slotSize;
                  if (slotSize > 255) {
                     blockScope.problemReporter().noMoreAvailableSpaceForArgument(enclosingInstance, blockScope.referenceType());
                  }
               }
            }

            this.generateCode(codeStream.classFile);
            codeStream.recordPositionsFrom(pc, this.sourceStart);
         }
      }
   }

   public void generateCode(ClassScope classScope, ClassFile enclosingClassFile) {
      if ((this.bits & 8192) == 0) {
         if (this.binding != null) {
            SyntheticArgumentBinding[] enclosingInstances = ((NestedTypeBinding)this.binding).syntheticEnclosingInstances();
            int i = 0;
            int slotSize = 0;

            for(int count = enclosingInstances == null ? 0 : enclosingInstances.length; i < count; ++i) {
               SyntheticArgumentBinding enclosingInstance = enclosingInstances[i];
               ++slotSize;
               enclosingInstance.resolvedPosition = slotSize;
               if (slotSize > 255) {
                  classScope.problemReporter().noMoreAvailableSpaceForArgument(enclosingInstance, classScope.referenceType());
               }
            }
         }

         this.generateCode(enclosingClassFile);
      }
   }

   public void generateCode(CompilationUnitScope unitScope) {
      this.generateCode((ClassFile)null);
   }

   public boolean hasErrors() {
      return this.ignoreFurtherInvestigation;
   }

   private void internalAnalyseCode(FlowContext flowContext, FlowInfo flowInfo) {
      if (!this.binding.isUsed() && this.binding.isOrEnclosedByPrivateType() && !this.scope.referenceCompilationUnit().compilationResult.hasSyntaxError) {
         this.scope.problemReporter().unusedPrivateType(this);
      }

      if (this.typeParameters != null && !this.scope.referenceCompilationUnit().compilationResult.hasSyntaxError) {
         int i = 0;

         for(int length = this.typeParameters.length; i < length; ++i) {
            TypeParameter typeParameter = this.typeParameters[i];
            if ((typeParameter.binding.modifiers & 134217728) == 0) {
               this.scope.problemReporter().unusedTypeParameter(typeParameter);
            }
         }
      }

      FlowContext parentContext = flowContext instanceof InitializationFlowContext ? null : flowContext;
      InitializationFlowContext initializerContext = new InitializationFlowContext(parentContext, this, flowInfo, flowContext, this.initializerScope);
      InitializationFlowContext staticInitializerContext = new InitializationFlowContext((FlowContext)null, this, flowInfo, flowContext, this.staticInitializerScope);
      FlowInfo nonStaticFieldInfo = flowInfo.unconditionalFieldLessCopy();
      FlowInfo staticFieldInfo = flowInfo.unconditionalFieldLessCopy();
      int i;
      int length;
      if (this.fields != null) {
         i = 0;

         for(length = this.fields.length; i < length; ++i) {
            FieldDeclaration field = this.fields[i];
            if (field.isStatic()) {
               if ((((FlowInfo)staticFieldInfo).tagBits & 1) != 0) {
                  field.bits &= Integer.MAX_VALUE;
               }

               staticInitializerContext.handledExceptions = Binding.ANY_EXCEPTION;
               staticFieldInfo = field.analyseCode(this.staticInitializerScope, staticInitializerContext, (FlowInfo)staticFieldInfo);
               if (staticFieldInfo == FlowInfo.DEAD_END) {
                  this.staticInitializerScope.problemReporter().initializerMustCompleteNormally(field);
                  staticFieldInfo = FlowInfo.initial(this.maxFieldCount).setReachMode(1);
               }
            } else {
               if ((((FlowInfo)nonStaticFieldInfo).tagBits & 1) != 0) {
                  field.bits &= Integer.MAX_VALUE;
               }

               initializerContext.handledExceptions = Binding.ANY_EXCEPTION;
               nonStaticFieldInfo = field.analyseCode(this.initializerScope, initializerContext, (FlowInfo)nonStaticFieldInfo);
               if (nonStaticFieldInfo == FlowInfo.DEAD_END) {
                  this.initializerScope.problemReporter().initializerMustCompleteNormally(field);
                  nonStaticFieldInfo = FlowInfo.initial(this.maxFieldCount).setReachMode(1);
               }
            }
         }
      }

      if (this.memberTypes != null) {
         i = 0;

         for(length = this.memberTypes.length; i < length; ++i) {
            if (flowContext != null) {
               this.memberTypes[i].analyseCode(this.scope, flowContext, ((FlowInfo)nonStaticFieldInfo).copy().setReachMode(flowInfo.reachMode()));
            } else {
               this.memberTypes[i].analyseCode(this.scope);
            }
         }
      }

      if (this.scope.compilerOptions().complianceLevel >= 3473408L && (this.methods == null || !this.methods[0].isClinit())) {
         Clinit clinit = new Clinit(this.compilationResult);
         clinit.declarationSourceStart = clinit.sourceStart = this.sourceStart;
         clinit.declarationSourceEnd = clinit.sourceEnd = this.sourceEnd;
         clinit.bodyEnd = this.sourceEnd;
         length = this.methods == null ? 0 : this.methods.length;
         AbstractMethodDeclaration[] methodDeclarations = new AbstractMethodDeclaration[length + 1];
         methodDeclarations[0] = clinit;
         if (this.methods != null) {
            System.arraycopy(this.methods, 0, methodDeclarations, 1, length);
         }
      }

      if (this.methods != null) {
         UnconditionalFlowInfo outerInfo = flowInfo.unconditionalFieldLessCopy();
         FlowInfo constructorInfo = ((FlowInfo)nonStaticFieldInfo).unconditionalInits().discardNonFieldInitializations().addInitializationsFrom(outerInfo);
         SimpleSetOfCharArray jUnitMethodSourceValues = this.getJUnitMethodSourceValues();
         int i = 0;

         for(int count = this.methods.length; i < count; ++i) {
            AbstractMethodDeclaration method = this.methods[i];
            if (!method.ignoreFurtherInvestigation) {
               if (method.isInitializationMethod()) {
                  if (method.isStatic()) {
                     ((Clinit)method).analyseCode(this.scope, staticInitializerContext, ((FlowInfo)staticFieldInfo).unconditionalInits().discardNonFieldInitializations().addInitializationsFrom(outerInfo));
                  } else {
                     ((ConstructorDeclaration)method).analyseCode(this.scope, initializerContext, constructorInfo.copy(), flowInfo.reachMode());
                  }
               } else {
                  if (method.arguments == null && jUnitMethodSourceValues.includes(method.selector) && method.binding != null) {
                     MethodBinding var10000 = method.binding;
                     var10000.modifiers |= 134217728;
                  }

                  ((MethodDeclaration)method).analyseCode(this.scope, parentContext, flowInfo.copy());
               }
            }
         }
      }

      if (this.binding.isEnum() && !this.binding.isAnonymousType()) {
         this.enumValuesSyntheticfield = this.binding.addSyntheticFieldForEnumValues();
      }

   }

   private SimpleSetOfCharArray getJUnitMethodSourceValues() {
      SimpleSetOfCharArray junitMethodSourceValues = new SimpleSetOfCharArray();
      AbstractMethodDeclaration[] var5;
      int var4 = (var5 = this.methods).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         AbstractMethodDeclaration methodDeclaration = var5[var3];
         if (methodDeclaration.annotations != null) {
            Annotation[] var9;
            int var8 = (var9 = methodDeclaration.annotations).length;

            for(int var7 = 0; var7 < var8; ++var7) {
               Annotation annotation = var9[var7];
               if (annotation.resolvedType != null && annotation.resolvedType.id == 93) {
                  this.addJUnitMethodSourceValues(junitMethodSourceValues, annotation, methodDeclaration.selector);
               }
            }
         }
      }

      return junitMethodSourceValues;
   }

   private void addJUnitMethodSourceValues(SimpleSetOfCharArray junitMethodSourceValues, Annotation annotation, char[] methodName) {
      MemberValuePair[] var7;
      int var6 = (var7 = annotation.memberValuePairs()).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         MemberValuePair memberValuePair = var7[var5];
         if (CharOperation.equals(memberValuePair.name, TypeConstants.VALUE)) {
            Expression value = memberValuePair.value;
            if (value instanceof ArrayInitializer) {
               ArrayInitializer arrayInitializer = (ArrayInitializer)value;
               Expression[] var13;
               int var12 = (var13 = arrayInitializer.expressions).length;

               for(int var11 = 0; var11 < var12; ++var11) {
                  Expression arrayValue = var13[var11];
                  junitMethodSourceValues.add(this.getValueAsChars(arrayValue));
               }
            } else {
               junitMethodSourceValues.add(this.getValueAsChars(value));
            }

            return;
         }
      }

      junitMethodSourceValues.add(methodName);
   }

   private char[] getValueAsChars(Expression value) {
      if (value instanceof StringLiteral) {
         return ((StringLiteral)value).source;
      } else {
         return value.constant instanceof StringConstant ? ((StringConstant)value.constant).stringValue().toCharArray() : CharOperation.NO_CHAR;
      }
   }

   public static final int kind(int flags) {
      switch (flags & 25088) {
         case 512:
            return 2;
         case 8704:
            return 4;
         case 16384:
            return 3;
         default:
            return 1;
      }
   }

   public void manageEnclosingInstanceAccessIfNecessary(BlockScope currentScope, FlowInfo flowInfo) {
      if ((flowInfo.tagBits & 1) == 0) {
         NestedTypeBinding nestedType = (NestedTypeBinding)this.binding;
         MethodScope methodScope = currentScope.methodScope();
         if (!methodScope.isStatic && !methodScope.isConstructorCall) {
            nestedType.addSyntheticArgumentAndField(nestedType.enclosingType());
         }

         if (nestedType.isAnonymousType()) {
            ReferenceBinding superclassBinding = (ReferenceBinding)nestedType.superclass.erasure();
            if (superclassBinding.enclosingType() != null && !superclassBinding.isStatic() && (!superclassBinding.isLocalType() || ((NestedTypeBinding)superclassBinding).getSyntheticField(superclassBinding.enclosingType(), true) != null || superclassBinding.isMemberType())) {
               nestedType.addSyntheticArgument(superclassBinding.enclosingType());
            }

            if (!methodScope.isStatic && methodScope.isConstructorCall && currentScope.compilerOptions().complianceLevel >= 3211264L) {
               ReferenceBinding enclosing = nestedType.enclosingType();
               if (enclosing.isNestedType()) {
                  NestedTypeBinding nestedEnclosing = (NestedTypeBinding)enclosing;
                  SyntheticArgumentBinding syntheticEnclosingInstanceArgument = nestedEnclosing.getSyntheticArgument(nestedEnclosing.enclosingType(), true, false);
                  if (syntheticEnclosingInstanceArgument != null) {
                     nestedType.addSyntheticArgumentAndField((LocalVariableBinding)syntheticEnclosingInstanceArgument);
                  }
               }
            }
         }

      }
   }

   public void manageEnclosingInstanceAccessIfNecessary(ClassScope currentScope, FlowInfo flowInfo) {
      if ((flowInfo.tagBits & 1) == 0) {
         NestedTypeBinding nestedType = (NestedTypeBinding)this.binding;
         nestedType.addSyntheticArgumentAndField(this.binding.enclosingType());
      }

   }

   public final boolean needClassInitMethod() {
      // $FF: Couldn't be decompiled
   }

   public void parseMethods(Parser parser, CompilationUnitDeclaration unit) {
      if (!unit.ignoreMethodBodies) {
         int length;
         int i;
         if (this.memberTypes != null) {
            length = this.memberTypes.length;

            for(i = 0; i < length; ++i) {
               TypeDeclaration typeDeclaration = this.memberTypes[i];
               typeDeclaration.parseMethods(parser, unit);
               this.bits |= typeDeclaration.bits & 524288;
            }
         }

         if (this.methods != null) {
            length = this.methods.length;

            for(i = 0; i < length; ++i) {
               AbstractMethodDeclaration abstractMethodDeclaration = this.methods[i];
               abstractMethodDeclaration.parseStatements(parser, unit);
               this.bits |= abstractMethodDeclaration.bits & 524288;
            }
         }

         if (this.fields != null) {
            length = this.fields.length;
            i = 0;

            while(i < length) {
               FieldDeclaration fieldDeclaration = this.fields[i];
               switch (fieldDeclaration.getKind()) {
                  case 2:
                     ((Initializer)fieldDeclaration).parseStatements(parser, this, unit);
                     this.bits |= fieldDeclaration.bits & 524288;
                  default:
                     ++i;
               }
            }
         }

      }
   }

   public StringBuffer print(int indent, StringBuffer output) {
      if (this.javadoc != null) {
         this.javadoc.print(indent, output);
      }

      if ((this.bits & 512) == 0) {
         printIndent(indent, output);
         this.printHeader(0, output);
      }

      return this.printBody(indent, output);
   }

   public StringBuffer printBody(int indent, StringBuffer output) {
      output.append(" {");
      int i;
      if (this.memberTypes != null) {
         for(i = 0; i < this.memberTypes.length; ++i) {
            if (this.memberTypes[i] != null) {
               output.append('\n');
               this.memberTypes[i].print(indent + 1, output);
            }
         }
      }

      if (this.fields != null) {
         for(i = 0; i < this.fields.length; ++i) {
            if (this.fields[i] != null) {
               output.append('\n');
               this.fields[i].print(indent + 1, output);
            }
         }
      }

      if (this.methods != null) {
         for(i = 0; i < this.methods.length; ++i) {
            if (this.methods[i] != null) {
               output.append('\n');
               this.methods[i].print(indent + 1, output);
            }
         }
      }

      output.append('\n');
      return printIndent(indent, output).append('}');
   }

   public StringBuffer printHeader(int var1, StringBuffer var2) {
      // $FF: Couldn't be decompiled
   }

   public StringBuffer printStatement(int tab, StringBuffer output) {
      return this.print(tab, output);
   }

   public int record(FunctionalExpression expression) {
      return this.functionalExpressionsCount++;
   }

   public void resolve() {
      // $FF: Couldn't be decompiled
   }

   public void resolve(BlockScope blockScope) {
      if ((this.bits & 512) == 0) {
         Binding existing = blockScope.getType(this.name);
         if (existing instanceof ReferenceBinding && existing != this.binding && existing.isValidBinding()) {
            ReferenceBinding existingType = (ReferenceBinding)existing;
            if (!(existingType instanceof TypeVariableBinding)) {
               if (existingType instanceof LocalTypeBinding && ((LocalTypeBinding)existingType).scope.methodScope() == blockScope.methodScope()) {
                  blockScope.problemReporter().duplicateNestedType(this);
               } else if (existingType instanceof LocalTypeBinding && blockScope.isLambdaSubscope() && blockScope.enclosingLambdaScope().enclosingMethodScope() == ((LocalTypeBinding)existingType).scope.methodScope()) {
                  blockScope.problemReporter().duplicateNestedType(this);
               } else if (blockScope.isDefinedInType(existingType)) {
                  blockScope.problemReporter().typeCollidesWithEnclosingType(this);
               } else if (blockScope.isDefinedInSameUnit(existingType)) {
                  blockScope.problemReporter().typeHiding((TypeDeclaration)this, (TypeBinding)existingType);
               }
            } else {
               blockScope.problemReporter().typeHiding(this, (TypeVariableBinding)existingType);

               for(Scope outerScope = blockScope.parent; outerScope != null; outerScope = outerScope.parent) {
                  Binding existing2 = outerScope.getType(this.name);
                  if (existing2 instanceof TypeVariableBinding && existing2.isValidBinding()) {
                     TypeVariableBinding tvb = (TypeVariableBinding)existingType;
                     Binding declaringElement = tvb.declaringElement;
                     if (declaringElement instanceof ReferenceBinding && CharOperation.equals(((ReferenceBinding)declaringElement).sourceName(), this.name)) {
                        blockScope.problemReporter().typeCollidesWithEnclosingType(this);
                        break;
                     }
                  } else {
                     if (existing2 instanceof ReferenceBinding && existing2.isValidBinding() && outerScope.isDefinedInType((ReferenceBinding)existing2)) {
                        blockScope.problemReporter().typeCollidesWithEnclosingType(this);
                        break;
                     }

                     if (existing2 == null) {
                        break;
                     }
                  }
               }
            }
         }

         blockScope.addLocalType(this);
      }

      if (this.binding != null) {
         blockScope.referenceCompilationUnit().record((LocalTypeBinding)this.binding);
         this.resolve();
         this.updateMaxFieldCount();
      }

   }

   public void resolve(ClassScope upperScope) {
      if (this.binding != null && this.binding instanceof LocalTypeBinding) {
         upperScope.referenceCompilationUnit().record((LocalTypeBinding)this.binding);
      }

      this.resolve();
      this.updateMaxFieldCount();
   }

   public void resolve(CompilationUnitScope upperScope) {
      this.resolve();
      this.updateMaxFieldCount();
   }

   public void tagAsHavingErrors() {
      this.ignoreFurtherInvestigation = true;
   }

   public void tagAsHavingIgnoredMandatoryErrors(int problemId) {
   }

   public void traverse(ASTVisitor visitor, CompilationUnitScope unitScope) {
      try {
         if (visitor.visit(this, unitScope)) {
            if (this.javadoc != null) {
               this.javadoc.traverse(visitor, this.scope);
            }

            int length;
            int i;
            if (this.annotations != null) {
               length = this.annotations.length;

               for(i = 0; i < length; ++i) {
                  this.annotations[i].traverse(visitor, (BlockScope)this.staticInitializerScope);
               }
            }

            if (this.superclass != null) {
               this.superclass.traverse(visitor, this.scope);
            }

            if (this.superInterfaces != null) {
               length = this.superInterfaces.length;

               for(i = 0; i < length; ++i) {
                  this.superInterfaces[i].traverse(visitor, this.scope);
               }
            }

            if (this.typeParameters != null) {
               length = this.typeParameters.length;

               for(i = 0; i < length; ++i) {
                  this.typeParameters[i].traverse(visitor, this.scope);
               }
            }

            if (this.memberTypes != null) {
               length = this.memberTypes.length;

               for(i = 0; i < length; ++i) {
                  this.memberTypes[i].traverse(visitor, this.scope);
               }
            }

            if (this.fields != null) {
               length = this.fields.length;

               for(i = 0; i < length; ++i) {
                  FieldDeclaration field;
                  if ((field = this.fields[i]).isStatic()) {
                     field.traverse(visitor, this.staticInitializerScope);
                  } else {
                     field.traverse(visitor, this.initializerScope);
                  }
               }
            }

            if (this.methods != null) {
               length = this.methods.length;

               for(i = 0; i < length; ++i) {
                  this.methods[i].traverse(visitor, this.scope);
               }
            }
         }

         visitor.endVisit(this, unitScope);
      } catch (AbortType var6) {
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      try {
         if (visitor.visit(this, blockScope)) {
            if (this.javadoc != null) {
               this.javadoc.traverse(visitor, this.scope);
            }

            int length;
            int i;
            if (this.annotations != null) {
               length = this.annotations.length;

               for(i = 0; i < length; ++i) {
                  this.annotations[i].traverse(visitor, (BlockScope)this.staticInitializerScope);
               }
            }

            if (this.superclass != null) {
               this.superclass.traverse(visitor, this.scope);
            }

            if (this.superInterfaces != null) {
               length = this.superInterfaces.length;

               for(i = 0; i < length; ++i) {
                  this.superInterfaces[i].traverse(visitor, this.scope);
               }
            }

            if (this.typeParameters != null) {
               length = this.typeParameters.length;

               for(i = 0; i < length; ++i) {
                  this.typeParameters[i].traverse(visitor, this.scope);
               }
            }

            if (this.memberTypes != null) {
               length = this.memberTypes.length;

               for(i = 0; i < length; ++i) {
                  this.memberTypes[i].traverse(visitor, this.scope);
               }
            }

            if (this.fields != null) {
               length = this.fields.length;

               for(i = 0; i < length; ++i) {
                  FieldDeclaration field = this.fields[i];
                  if (!field.isStatic() || field.isFinal()) {
                     field.traverse(visitor, this.initializerScope);
                  }
               }
            }

            if (this.methods != null) {
               length = this.methods.length;

               for(i = 0; i < length; ++i) {
                  this.methods[i].traverse(visitor, this.scope);
               }
            }
         }

         visitor.endVisit(this, blockScope);
      } catch (AbortType var6) {
      }

   }

   public void traverse(ASTVisitor visitor, ClassScope classScope) {
      try {
         if (visitor.visit(this, classScope)) {
            if (this.javadoc != null) {
               this.javadoc.traverse(visitor, this.scope);
            }

            int length;
            int i;
            if (this.annotations != null) {
               length = this.annotations.length;

               for(i = 0; i < length; ++i) {
                  this.annotations[i].traverse(visitor, (BlockScope)this.staticInitializerScope);
               }
            }

            if (this.superclass != null) {
               this.superclass.traverse(visitor, this.scope);
            }

            if (this.superInterfaces != null) {
               length = this.superInterfaces.length;

               for(i = 0; i < length; ++i) {
                  this.superInterfaces[i].traverse(visitor, this.scope);
               }
            }

            if (this.typeParameters != null) {
               length = this.typeParameters.length;

               for(i = 0; i < length; ++i) {
                  this.typeParameters[i].traverse(visitor, this.scope);
               }
            }

            if (this.memberTypes != null) {
               length = this.memberTypes.length;

               for(i = 0; i < length; ++i) {
                  this.memberTypes[i].traverse(visitor, this.scope);
               }
            }

            if (this.fields != null) {
               length = this.fields.length;

               for(i = 0; i < length; ++i) {
                  FieldDeclaration field;
                  if ((field = this.fields[i]).isStatic()) {
                     field.traverse(visitor, this.staticInitializerScope);
                  } else {
                     field.traverse(visitor, this.initializerScope);
                  }
               }
            }

            if (this.methods != null) {
               length = this.methods.length;

               for(i = 0; i < length; ++i) {
                  this.methods[i].traverse(visitor, this.scope);
               }
            }
         }

         visitor.endVisit(this, classScope);
      } catch (AbortType var6) {
      }

   }

   void updateMaxFieldCount() {
      if (this.binding != null) {
         TypeDeclaration outerMostType = this.scope.outerMostClassScope().referenceType();
         if (this.maxFieldCount > outerMostType.maxFieldCount) {
            outerMostType.maxFieldCount = this.maxFieldCount;
         } else {
            this.maxFieldCount = outerMostType.maxFieldCount;
         }

      }
   }

   private SourceTypeBinding findNestHost() {
      ClassScope classScope = this.scope.enclosingTopMostClassScope();
      return classScope != null ? classScope.referenceContext.binding : null;
   }

   void updateNestInfo() {
      if (this.binding != null) {
         SourceTypeBinding nestHost = this.findNestHost();
         if (nestHost != null && !this.binding.equals(nestHost)) {
            this.binding.setNestHost(nestHost);
            nestHost.addNestMember(this.binding);
         }

      }
   }

   public boolean isPackageInfo() {
      return CharOperation.equals(this.name, TypeConstants.PACKAGE_INFO_NAME);
   }

   public boolean isSecondary() {
      return (this.bits & 4096) != 0;
   }
}
