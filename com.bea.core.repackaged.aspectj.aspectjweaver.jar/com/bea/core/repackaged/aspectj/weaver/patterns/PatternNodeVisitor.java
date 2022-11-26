package com.bea.core.repackaged.aspectj.weaver.patterns;

public interface PatternNodeVisitor {
   Object visit(AndAnnotationTypePattern var1, Object var2);

   Object visit(AnyAnnotationTypePattern var1, Object var2);

   Object visit(EllipsisAnnotationTypePattern var1, Object var2);

   Object visit(ExactAnnotationTypePattern var1, Object var2);

   Object visit(BindingAnnotationTypePattern var1, Object var2);

   Object visit(NotAnnotationTypePattern var1, Object var2);

   Object visit(OrAnnotationTypePattern var1, Object var2);

   Object visit(WildAnnotationTypePattern var1, Object var2);

   Object visit(AnnotationPatternList var1, Object var2);

   Object visit(AndTypePattern var1, Object var2);

   Object visit(AnyTypePattern var1, Object var2);

   Object visit(AnyWithAnnotationTypePattern var1, Object var2);

   Object visit(EllipsisTypePattern var1, Object var2);

   Object visit(ExactTypePattern var1, Object var2);

   Object visit(BindingTypePattern var1, Object var2);

   Object visit(NotTypePattern var1, Object var2);

   Object visit(NoTypePattern var1, Object var2);

   Object visit(OrTypePattern var1, Object var2);

   Object visit(WildTypePattern var1, Object var2);

   Object visit(TypePatternList var1, Object var2);

   Object visit(HasMemberTypePattern var1, Object var2);

   Object visit(TypeCategoryTypePattern var1, Object var2);

   Object visit(AndPointcut var1, Object var2);

   Object visit(CflowPointcut var1, Object var2);

   Object visit(ConcreteCflowPointcut var1, Object var2);

   Object visit(HandlerPointcut var1, Object var2);

   Object visit(IfPointcut var1, Object var2);

   Object visit(KindedPointcut var1, Object var2);

   Object visit(Pointcut.MatchesNothingPointcut var1, Object var2);

   Object visit(AnnotationPointcut var1, Object var2);

   Object visit(ArgsAnnotationPointcut var1, Object var2);

   Object visit(ArgsPointcut var1, Object var2);

   Object visit(ThisOrTargetAnnotationPointcut var1, Object var2);

   Object visit(ThisOrTargetPointcut var1, Object var2);

   Object visit(WithinAnnotationPointcut var1, Object var2);

   Object visit(WithinCodeAnnotationPointcut var1, Object var2);

   Object visit(NotPointcut var1, Object var2);

   Object visit(OrPointcut var1, Object var2);

   Object visit(ReferencePointcut var1, Object var2);

   Object visit(WithinPointcut var1, Object var2);

   Object visit(WithincodePointcut var1, Object var2);

   Object visit(PerCflow var1, Object var2);

   Object visit(PerFromSuper var1, Object var2);

   Object visit(PerObject var1, Object var2);

   Object visit(PerSingleton var1, Object var2);

   Object visit(PerTypeWithin var1, Object var2);

   Object visit(DeclareAnnotation var1, Object var2);

   Object visit(DeclareErrorOrWarning var1, Object var2);

   Object visit(DeclareParents var1, Object var2);

   Object visit(DeclarePrecedence var1, Object var2);

   Object visit(DeclareSoft var1, Object var2);

   Object visit(ModifiersPattern var1, Object var2);

   Object visit(NamePattern var1, Object var2);

   Object visit(SignaturePattern var1, Object var2);

   Object visit(ThrowsPattern var1, Object var2);

   Object visit(TypeVariablePattern var1, Object var2);

   Object visit(TypeVariablePatternList var1, Object var2);

   Object visit(PatternNode var1, Object var2);
}
