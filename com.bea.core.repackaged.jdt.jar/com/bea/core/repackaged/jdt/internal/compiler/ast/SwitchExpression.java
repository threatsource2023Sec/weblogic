package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PolyTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SwitchExpression extends SwitchStatement implements IPolyExpression {
   TypeBinding expectedType;
   private ExpressionContext expressionContext;
   private boolean isPolyExpression;
   private TypeBinding[] originalValueResultExpressionTypes;
   private TypeBinding[] finalValueResultExpressionTypes;
   private int nullStatus;
   public List resultExpressions;
   public boolean resolveAll;
   List resultExpressionNullStatus;
   private static Map type_map = new HashMap();

   static {
      type_map.put(TypeBinding.CHAR, new TypeBinding[]{TypeBinding.CHAR, TypeBinding.BYTE, TypeBinding.INT});
      type_map.put(TypeBinding.SHORT, new TypeBinding[]{TypeBinding.SHORT, TypeBinding.BYTE, TypeBinding.INT});
      type_map.put(TypeBinding.BYTE, new TypeBinding[]{TypeBinding.BYTE, TypeBinding.INT});
   }

   public SwitchExpression() {
      this.expressionContext = ExpressionContext.VANILLA_CONTEXT;
      this.isPolyExpression = false;
      this.nullStatus = 1;
   }

   public void setExpressionContext(ExpressionContext context) {
      this.expressionContext = context;
   }

   public void setExpectedType(TypeBinding expectedType) {
      this.expectedType = expectedType;
   }

   public ExpressionContext getExpressionContext() {
      return this.expressionContext;
   }

   protected boolean ignoreMissingDefaultCase(CompilerOptions compilerOptions, boolean isEnumSwitch) {
      return isEnumSwitch;
   }

   protected void reportMissingEnumConstantCase(BlockScope upperScope, FieldBinding enumConstant) {
      upperScope.problemReporter().missingEnumConstantCase(this, enumConstant);
   }

   protected int getFallThroughState(Statement stmt, BlockScope blockScope) {
      if ((!(stmt instanceof Expression) || !((Expression)stmt).isTrulyExpression()) && !(stmt instanceof ThrowStatement)) {
         if (this.switchLabeledRules && stmt instanceof Block) {
            Block block = (Block)stmt;
            if (block.doesNotCompleteNormally()) {
               return 3;
            }

            blockScope.problemReporter().switchExpressionSwitchLabeledBlockCompletesNormally(block);
         }

         return 1;
      } else {
         return 3;
      }
   }

   public boolean checkNPE(BlockScope skope, FlowContext flowContext, FlowInfo flowInfo, int ttlForFieldCheck) {
      if ((this.nullStatus & 2) != 0) {
         skope.problemReporter().expressionNullReference(this);
      } else if ((this.nullStatus & 16) != 0) {
         skope.problemReporter().expressionPotentialNullReference(this);
      }

      return true;
   }

   private void computeNullStatus(FlowInfo flowInfo, FlowContext flowContext) {
      boolean precomputed = this.resultExpressionNullStatus.size() > 0;
      if (!precomputed) {
         this.resultExpressionNullStatus.add(((Expression)this.resultExpressions.get(0)).nullStatus(flowInfo, flowContext));
      }

      int status = ((Expression)this.resultExpressions.get(0)).nullStatus(flowInfo, flowContext);
      int combinedStatus = status;
      boolean identicalStatus = true;
      int i = 1;

      for(int l = this.resultExpressions.size(); i < l; ++i) {
         if (!precomputed) {
            this.resultExpressionNullStatus.add(((Expression)this.resultExpressions.get(i)).nullStatus(flowInfo, flowContext));
         }

         int tmp = ((Expression)this.resultExpressions.get(i)).nullStatus(flowInfo, flowContext);
         identicalStatus &= status == tmp;
         combinedStatus |= tmp;
      }

      if (identicalStatus) {
         this.nullStatus = status;
      } else {
         status = Expression.computeNullStatus(0, combinedStatus);
         if (status > 0) {
            this.nullStatus = status;
         }

      }
   }

   protected void completeNormallyCheck(BlockScope blockScope) {
      if (!this.switchLabeledRules) {
         int sz = this.statements != null ? this.statements.length : 0;
         if (sz != 0) {
            Statement lastNonCaseStmt = null;
            Statement firstTrailingCaseStmt = null;

            for(int i = sz - 1; i >= 0; --i) {
               Statement stmt = this.statements[sz - 1];
               if (!(stmt instanceof CaseStatement)) {
                  lastNonCaseStmt = stmt;
                  break;
               }

               firstTrailingCaseStmt = stmt;
            }

            if (lastNonCaseStmt != null) {
               if (!lastNonCaseStmt.doesNotCompleteNormally()) {
                  blockScope.problemReporter().switchExpressionLastStatementCompletesNormally(lastNonCaseStmt);
               } else if (lastNonCaseStmt instanceof ContinueStatement || lastNonCaseStmt instanceof ReturnStatement) {
                  blockScope.problemReporter().switchExpressionIllegalLastStatement(lastNonCaseStmt);
               }
            }

            if (firstTrailingCaseStmt != null) {
               blockScope.problemReporter().switchExpressionTrailingSwitchLabels(firstTrailingCaseStmt);
            }

         }
      }
   }

   protected boolean needToCheckFlowInAbsenceOfDefaultBranch() {
      return !this.switchLabeledRules;
   }

   public Expression[] getPolyExpressions() {
      List polys = new ArrayList();
      Iterator var3 = this.resultExpressions.iterator();

      while(var3.hasNext()) {
         Expression e = (Expression)var3.next();
         Expression[] ea = e.getPolyExpressions();
         if (ea != null && ea.length != 0) {
            polys.addAll(Arrays.asList(ea));
         }
      }

      return (Expression[])polys.toArray(new Expression[0]);
   }

   public boolean isPertinentToApplicability(TypeBinding targetType, MethodBinding method) {
      Iterator var4 = this.resultExpressions.iterator();

      while(var4.hasNext()) {
         Expression e = (Expression)var4.next();
         if (!e.isPertinentToApplicability(targetType, method)) {
            return false;
         }
      }

      return true;
   }

   public boolean isPotentiallyCompatibleWith(TypeBinding targetType, Scope scope1) {
      Iterator var4 = this.resultExpressions.iterator();

      while(var4.hasNext()) {
         Expression e = (Expression)var4.next();
         if (!e.isPotentiallyCompatibleWith(targetType, scope1)) {
            return false;
         }
      }

      return true;
   }

   public boolean isFunctionalType() {
      Iterator var2 = this.resultExpressions.iterator();

      while(var2.hasNext()) {
         Expression e = (Expression)var2.next();
         if (e.isFunctionalType()) {
            return true;
         }
      }

      return false;
   }

   public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
      return (this.implicitConversion & 512) != 0 ? 4 : this.nullStatus;
   }

   protected void statementGenerateCode(BlockScope currentScope, CodeStream codeStream, Statement statement) {
      if (statement instanceof Expression && ((Expression)statement).isTrulyExpression() && !(statement instanceof Assignment) && !(statement instanceof MessageSend) && (!(statement instanceof SwitchStatement) || statement instanceof SwitchExpression)) {
         Expression expression1 = (Expression)statement;
         expression1.generateCode(currentScope, codeStream, true);
      } else {
         super.statementGenerateCode(currentScope, codeStream, statement);
      }
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      super.generateCode(currentScope, codeStream);
      if (!valueRequired) {
         switch (this.postConversionType(currentScope).id) {
            case 6:
               break;
            case 7:
            case 8:
               codeStream.pop2();
               break;
            default:
               codeStream.pop();
         }
      }

   }

   protected boolean computeConversions(BlockScope blockScope, TypeBinding targetType) {
      boolean ok = true;
      int i = 0;

      for(int l = this.resultExpressions.size(); i < l; ++i) {
         ok &= this.computeConversionsResultExpressions(blockScope, targetType, this.originalValueResultExpressionTypes[i], (Expression)this.resultExpressions.get(i));
      }

      return ok;
   }

   private boolean computeConversionsResultExpressions(BlockScope blockScope, TypeBinding targetType, TypeBinding resultExpressionType, Expression resultExpression) {
      if (resultExpressionType != null && resultExpressionType.isValidBinding()) {
         if (!resultExpression.isConstantValueOfTypeAssignableToType(resultExpressionType, targetType) && !resultExpressionType.isCompatibleWith(targetType)) {
            if (!this.isBoxingCompatible(resultExpressionType, targetType, resultExpression, blockScope)) {
               blockScope.problemReporter().typeMismatchError(resultExpressionType, (TypeBinding)targetType, (ASTNode)resultExpression, (ASTNode)null);
               return false;
            }

            resultExpression.computeConversion(blockScope, targetType, resultExpressionType);
            if (resultExpression instanceof CastExpression && (resultExpression.bits & 16416) == 0) {
               CastExpression.checkNeedForAssignedCast(blockScope, targetType, (CastExpression)resultExpression);
            }
         } else {
            resultExpression.computeConversion(blockScope, targetType, resultExpressionType);
            if (resultExpressionType.needsUncheckedConversion(targetType)) {
               blockScope.problemReporter().unsafeTypeConversion(resultExpression, resultExpressionType, targetType);
            }

            if (resultExpression instanceof CastExpression && (resultExpression.bits & 16416) == 0) {
               CastExpression.checkNeedForAssignedCast(blockScope, targetType, (CastExpression)resultExpression);
            }
         }
      }

      return true;
   }

   public TypeBinding resolveType(BlockScope upperScope) {
      try {
         int resultExpressionsCount;
         int i;
         TypeBinding var33;
         if (this.constant != Constant.NotAConstant) {
            this.constant = Constant.NotAConstant;
            if (this.expressionContext == ExpressionContext.ASSIGNMENT_CONTEXT || this.expressionContext == ExpressionContext.INVOCATION_CONTEXT) {
               Iterator var4 = this.resultExpressions.iterator();

               while(var4.hasNext()) {
                  Expression e = (Expression)var4.next();
                  e.setExpressionContext(this.expressionContext);
                  e.setExpectedType(this.expectedType);
               }
            }

            this.resolve(upperScope);
            if (this.statements == null || this.statements.length == 0) {
               upperScope.problemReporter().switchExpressionEmptySwitchBlock(this);
               return null;
            }

            resultExpressionsCount = this.resultExpressions != null ? this.resultExpressions.size() : 0;
            if (resultExpressionsCount == 0) {
               upperScope.problemReporter().switchExpressionNoResultExpressions(this);
               return null;
            }

            if (this.originalValueResultExpressionTypes == null) {
               this.originalValueResultExpressionTypes = new TypeBinding[resultExpressionsCount];
               this.finalValueResultExpressionTypes = new TypeBinding[resultExpressionsCount];

               for(i = 0; i < resultExpressionsCount; ++i) {
                  this.finalValueResultExpressionTypes[i] = this.originalValueResultExpressionTypes[i] = ((Expression)this.resultExpressions.get(i)).resolvedType;
               }
            }

            if (this.isPolyExpression()) {
               if (this.expectedType != null && this.expectedType.isProperType(true)) {
                  var33 = this.resolvedType = this.computeConversions(this.scope, this.expectedType) ? this.expectedType : null;
                  return var33;
               }

               PolyTypeBinding var16 = new PolyTypeBinding(this);
               return var16;
            }
         } else {
            resultExpressionsCount = this.resultExpressions != null ? this.resultExpressions.size() : 0;
            i = 0;

            while(true) {
               if (i >= resultExpressionsCount) {
                  this.resolvedType = this.computeConversions(this.scope, this.expectedType) ? this.expectedType : null;
                  break;
               }

               Expression resultExpr = (Expression)this.resultExpressions.get(i);
               if (resultExpr.resolvedType == null || resultExpr.resolvedType.kind() == 65540) {
                  this.finalValueResultExpressionTypes[i] = this.originalValueResultExpressionTypes[i] = resultExpr.resolveTypeExpecting(upperScope, this.expectedType);
               }

               if (!this.resolveAll && (resultExpr.resolvedType == null || !resultExpr.resolvedType.isValidBinding())) {
                  var33 = this.resolvedType = null;
                  return var33;
               }

               ++i;
            }
         }

         if (resultExpressionsCount == 1) {
            var33 = this.resolvedType = this.originalValueResultExpressionTypes[0];
            return var33;
         } else {
            boolean typeUniformAcrossAllArms = true;
            TypeBinding tmp = this.originalValueResultExpressionTypes[0];
            int i = 1;

            for(int l = this.originalValueResultExpressionTypes.length; i < l; ++i) {
               TypeBinding originalType = this.originalValueResultExpressionTypes[i];
               if (originalType != null && TypeBinding.notEquals(tmp, originalType)) {
                  typeUniformAcrossAllArms = false;
                  break;
               }
            }

            if (typeUniformAcrossAllArms) {
               tmp = this.originalValueResultExpressionTypes[0];

               for(i = 1; i < resultExpressionsCount; ++i) {
                  if (this.originalValueResultExpressionTypes[i] != null) {
                     tmp = NullAnnotationMatching.moreDangerousType(tmp, this.originalValueResultExpressionTypes[i]);
                  }
               }

               var33 = this.resolvedType = tmp;
               return var33;
            } else {
               boolean typeBbolean = true;
               TypeBinding[] typeSet;
               int var8 = (typeSet = this.originalValueResultExpressionTypes).length;

               int i;
               for(i = 0; i < var8; ++i) {
                  TypeBinding t = typeSet[i];
                  if (t != null) {
                     typeBbolean &= t.id == 5 || t.id == 33;
                  }
               }

               LookupEnvironment env = this.scope.environment();
               if (typeBbolean) {
                  for(i = 0; i < resultExpressionsCount; ++i) {
                     if (this.originalValueResultExpressionTypes[i].id != 5) {
                        this.finalValueResultExpressionTypes[i] = env.computeBoxingType(this.originalValueResultExpressionTypes[i]);
                        ((Expression)this.resultExpressions.get(i)).computeConversion(this.scope, this.finalValueResultExpressionTypes[i], this.originalValueResultExpressionTypes[i]);
                     }
                  }

                  var33 = this.resolvedType = TypeBinding.BOOLEAN;
                  return var33;
               } else {
                  boolean typeNumeric = true;
                  TypeBinding resultNumeric = null;
                  HashSet typeSet = new HashSet();

                  int i;
                  TypeBinding binding;
                  for(i = 0; i < resultExpressionsCount; ++i) {
                     binding = this.originalValueResultExpressionTypes[i];
                     if (binding != null) {
                        tmp = binding.isNumericType() ? binding : env.computeBoxingType(binding);
                        if (!tmp.isNumericType()) {
                           typeNumeric = false;
                           break;
                        }

                        typeSet.add(TypeBinding.wellKnownType(this.scope, tmp.id));
                     }
                  }

                  int l;
                  int i;
                  if (typeNumeric) {
                     TypeBinding[] dfl = new TypeBinding[]{TypeBinding.DOUBLE, TypeBinding.FLOAT, TypeBinding.LONG};
                     TypeBinding[] var14 = dfl;
                     int var13 = dfl.length;
                     l = 0;

                     while(true) {
                        if (l < var13) {
                           binding = var14[l];
                           if (!typeSet.contains(binding)) {
                              ++l;
                              continue;
                           }

                           resultNumeric = binding;
                        }

                        resultNumeric = resultNumeric != null ? resultNumeric : this.check_nonconstant_int();
                        resultNumeric = resultNumeric != null ? resultNumeric : this.getResultNumeric(typeSet, this.originalValueResultExpressionTypes);
                        typeSet = null;

                        for(i = 0; i < resultExpressionsCount; ++i) {
                           ((Expression)this.resultExpressions.get(i)).computeConversion(this.scope, resultNumeric, this.originalValueResultExpressionTypes[i]);
                           this.finalValueResultExpressionTypes[i] = resultNumeric;
                        }

                        var33 = this.resolvedType = resultNumeric;
                        return var33;
                     }
                  } else {
                     for(i = 0; i < resultExpressionsCount; ++i) {
                        binding = this.finalValueResultExpressionTypes[i];
                        if (binding != null && binding.isBaseType()) {
                           this.finalValueResultExpressionTypes[i] = env.computeBoxingType(binding);
                        }
                     }

                     TypeBinding commonType = this.scope.lowerUpperBound(this.finalValueResultExpressionTypes);
                     if (commonType != null) {
                        i = 0;

                        for(l = this.resultExpressions.size(); i < l; ++i) {
                           if (this.originalValueResultExpressionTypes[i] != null) {
                              ((Expression)this.resultExpressions.get(i)).computeConversion(this.scope, commonType, this.originalValueResultExpressionTypes[i]);
                              this.finalValueResultExpressionTypes[i] = commonType;
                           }
                        }

                        var33 = this.resolvedType = commonType.capture(this.scope, this.sourceStart, this.sourceEnd);
                        return var33;
                     } else {
                        this.scope.problemReporter().switchExpressionIncompatibleResultExpressions(this);
                        return null;
                     }
                  }
               }
            }
         }
      } finally {
         if (this.scope != null) {
            this.scope.enclosingCase = null;
         }

      }
   }

   private TypeBinding check_nonconstant_int() {
      int i = 0;

      for(int l = this.resultExpressions.size(); i < l; ++i) {
         Expression e = (Expression)this.resultExpressions.get(i);
         TypeBinding type = this.originalValueResultExpressionTypes[i];
         if (type != null && type.id == 10 && e.constant == Constant.NotAConstant) {
            return TypeBinding.INT;
         }
      }

      return null;
   }

   private boolean areAllIntegerResultExpressionsConvertibleToTargetType(TypeBinding targetType) {
      int i = 0;

      for(int l = this.resultExpressions.size(); i < l; ++i) {
         Expression e = (Expression)this.resultExpressions.get(i);
         TypeBinding t = this.originalValueResultExpressionTypes[i];
         if (TypeBinding.equalsEquals(t, TypeBinding.INT) && !e.isConstantValueOfTypeAssignableToType(t, targetType)) {
            return false;
         }
      }

      return true;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      flowInfo = super.analyseCode(currentScope, flowContext, flowInfo);
      this.resultExpressionNullStatus = new ArrayList(0);
      CompilerOptions compilerOptions = currentScope.compilerOptions();
      if (compilerOptions.enableSyntacticNullAnalysisForFields) {
         Iterator var6 = this.resultExpressions.iterator();

         while(var6.hasNext()) {
            Expression re = (Expression)var6.next();
            this.resultExpressionNullStatus.add(re.nullStatus(flowInfo, flowContext));
            flowContext.expireNullCheckedFieldInfo();
         }
      }

      this.computeNullStatus(flowInfo, flowContext);
      return flowInfo;
   }

   private TypeBinding check_csb(Set typeSet, TypeBinding candidate) {
      if (!typeSet.contains(candidate)) {
         return null;
      } else {
         TypeBinding[] allowedTypes = (TypeBinding[])type_map.get(candidate);
         Set allowedSet = (Set)Arrays.stream(allowedTypes).collect(Collectors.toSet());
         if (!allowedSet.containsAll(typeSet)) {
            return null;
         } else {
            return this.areAllIntegerResultExpressionsConvertibleToTargetType(candidate) ? candidate : null;
         }
      }
   }

   private TypeBinding getResultNumeric(Set typeSet, TypeBinding[] armTypes) {
      TypeBinding[] csb = new TypeBinding[]{TypeBinding.CHAR, TypeBinding.SHORT, TypeBinding.BYTE};
      TypeBinding[] var7 = csb;
      int var6 = csb.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         TypeBinding c = var7[var5];
         TypeBinding result = this.check_csb(typeSet, c);
         if (result != null) {
            return result;
         }
      }

      return TypeBinding.INT;
   }

   public boolean isPolyExpression() {
      return this.isPolyExpression ? true : (this.isPolyExpression = this.expressionContext == ExpressionContext.ASSIGNMENT_CONTEXT || this.expressionContext == ExpressionContext.INVOCATION_CONTEXT);
   }

   public boolean isTrulyExpression() {
      return true;
   }

   public boolean isCompatibleWith(TypeBinding left, Scope skope) {
      if (!this.isPolyExpression()) {
         return super.isCompatibleWith(left, skope);
      } else {
         Iterator var4 = this.resultExpressions.iterator();

         while(var4.hasNext()) {
            Expression e = (Expression)var4.next();
            if (!e.isCompatibleWith(left, skope)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isBoxingCompatibleWith(TypeBinding targetType, Scope skope) {
      if (!this.isPolyExpression()) {
         return super.isBoxingCompatibleWith(targetType, skope);
      } else {
         Iterator var4 = this.resultExpressions.iterator();

         Expression e;
         do {
            if (!var4.hasNext()) {
               return true;
            }

            e = (Expression)var4.next();
         } while(e.isCompatibleWith(targetType, skope) || e.isBoxingCompatibleWith(targetType, skope));

         return false;
      }
   }

   public boolean sIsMoreSpecific(TypeBinding s, TypeBinding t, Scope skope) {
      if (super.sIsMoreSpecific(s, t, skope)) {
         return true;
      } else if (!this.isPolyExpression()) {
         return false;
      } else {
         Iterator var5 = this.resultExpressions.iterator();

         while(var5.hasNext()) {
            Expression e = (Expression)var5.next();
            if (!e.sIsMoreSpecific(s, t, skope)) {
               return false;
            }
         }

         return true;
      }
   }

   public TypeBinding expectedType() {
      return this.expectedType;
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         this.expression.traverse(visitor, blockScope);
         if (this.statements != null) {
            int statementsLength = this.statements.length;

            for(int i = 0; i < statementsLength; ++i) {
               this.statements[i].traverse(visitor, this.scope);
            }
         }
      }

      visitor.endVisit(this, blockScope);
   }
}
