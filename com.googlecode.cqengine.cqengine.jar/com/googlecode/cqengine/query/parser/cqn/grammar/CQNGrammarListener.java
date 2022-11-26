package com.googlecode.cqengine.query.parser.cqn.grammar;

import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface CQNGrammarListener extends ParseTreeListener {
   void enterStart(CQNGrammarParser.StartContext var1);

   void exitStart(CQNGrammarParser.StartContext var1);

   void enterQuery(CQNGrammarParser.QueryContext var1);

   void exitQuery(CQNGrammarParser.QueryContext var1);

   void enterLogicalQuery(CQNGrammarParser.LogicalQueryContext var1);

   void exitLogicalQuery(CQNGrammarParser.LogicalQueryContext var1);

   void enterAndQuery(CQNGrammarParser.AndQueryContext var1);

   void exitAndQuery(CQNGrammarParser.AndQueryContext var1);

   void enterOrQuery(CQNGrammarParser.OrQueryContext var1);

   void exitOrQuery(CQNGrammarParser.OrQueryContext var1);

   void enterNotQuery(CQNGrammarParser.NotQueryContext var1);

   void exitNotQuery(CQNGrammarParser.NotQueryContext var1);

   void enterSimpleQuery(CQNGrammarParser.SimpleQueryContext var1);

   void exitSimpleQuery(CQNGrammarParser.SimpleQueryContext var1);

   void enterEqualQuery(CQNGrammarParser.EqualQueryContext var1);

   void exitEqualQuery(CQNGrammarParser.EqualQueryContext var1);

   void enterLessThanOrEqualToQuery(CQNGrammarParser.LessThanOrEqualToQueryContext var1);

   void exitLessThanOrEqualToQuery(CQNGrammarParser.LessThanOrEqualToQueryContext var1);

   void enterLessThanQuery(CQNGrammarParser.LessThanQueryContext var1);

   void exitLessThanQuery(CQNGrammarParser.LessThanQueryContext var1);

   void enterGreaterThanOrEqualToQuery(CQNGrammarParser.GreaterThanOrEqualToQueryContext var1);

   void exitGreaterThanOrEqualToQuery(CQNGrammarParser.GreaterThanOrEqualToQueryContext var1);

   void enterGreaterThanQuery(CQNGrammarParser.GreaterThanQueryContext var1);

   void exitGreaterThanQuery(CQNGrammarParser.GreaterThanQueryContext var1);

   void enterVerboseBetweenQuery(CQNGrammarParser.VerboseBetweenQueryContext var1);

   void exitVerboseBetweenQuery(CQNGrammarParser.VerboseBetweenQueryContext var1);

   void enterBetweenQuery(CQNGrammarParser.BetweenQueryContext var1);

   void exitBetweenQuery(CQNGrammarParser.BetweenQueryContext var1);

   void enterInQuery(CQNGrammarParser.InQueryContext var1);

   void exitInQuery(CQNGrammarParser.InQueryContext var1);

   void enterStartsWithQuery(CQNGrammarParser.StartsWithQueryContext var1);

   void exitStartsWithQuery(CQNGrammarParser.StartsWithQueryContext var1);

   void enterEndsWithQuery(CQNGrammarParser.EndsWithQueryContext var1);

   void exitEndsWithQuery(CQNGrammarParser.EndsWithQueryContext var1);

   void enterContainsQuery(CQNGrammarParser.ContainsQueryContext var1);

   void exitContainsQuery(CQNGrammarParser.ContainsQueryContext var1);

   void enterIsContainedInQuery(CQNGrammarParser.IsContainedInQueryContext var1);

   void exitIsContainedInQuery(CQNGrammarParser.IsContainedInQueryContext var1);

   void enterMatchesRegexQuery(CQNGrammarParser.MatchesRegexQueryContext var1);

   void exitMatchesRegexQuery(CQNGrammarParser.MatchesRegexQueryContext var1);

   void enterHasQuery(CQNGrammarParser.HasQueryContext var1);

   void exitHasQuery(CQNGrammarParser.HasQueryContext var1);

   void enterAllQuery(CQNGrammarParser.AllQueryContext var1);

   void exitAllQuery(CQNGrammarParser.AllQueryContext var1);

   void enterNoneQuery(CQNGrammarParser.NoneQueryContext var1);

   void exitNoneQuery(CQNGrammarParser.NoneQueryContext var1);

   void enterObjectType(CQNGrammarParser.ObjectTypeContext var1);

   void exitObjectType(CQNGrammarParser.ObjectTypeContext var1);

   void enterAttributeName(CQNGrammarParser.AttributeNameContext var1);

   void exitAttributeName(CQNGrammarParser.AttributeNameContext var1);

   void enterQueryParameter(CQNGrammarParser.QueryParameterContext var1);

   void exitQueryParameter(CQNGrammarParser.QueryParameterContext var1);

   void enterStringQueryParameter(CQNGrammarParser.StringQueryParameterContext var1);

   void exitStringQueryParameter(CQNGrammarParser.StringQueryParameterContext var1);

   void enterQueryOptions(CQNGrammarParser.QueryOptionsContext var1);

   void exitQueryOptions(CQNGrammarParser.QueryOptionsContext var1);

   void enterQueryOption(CQNGrammarParser.QueryOptionContext var1);

   void exitQueryOption(CQNGrammarParser.QueryOptionContext var1);

   void enterOrderByOption(CQNGrammarParser.OrderByOptionContext var1);

   void exitOrderByOption(CQNGrammarParser.OrderByOptionContext var1);

   void enterAttributeOrder(CQNGrammarParser.AttributeOrderContext var1);

   void exitAttributeOrder(CQNGrammarParser.AttributeOrderContext var1);

   void enterDirection(CQNGrammarParser.DirectionContext var1);

   void exitDirection(CQNGrammarParser.DirectionContext var1);

   void enterLiteral(CQNGrammarParser.LiteralContext var1);

   void exitLiteral(CQNGrammarParser.LiteralContext var1);

   void enterCompilationUnit(CQNGrammarParser.CompilationUnitContext var1);

   void exitCompilationUnit(CQNGrammarParser.CompilationUnitContext var1);

   void enterPackageDeclaration(CQNGrammarParser.PackageDeclarationContext var1);

   void exitPackageDeclaration(CQNGrammarParser.PackageDeclarationContext var1);

   void enterImportDeclaration(CQNGrammarParser.ImportDeclarationContext var1);

   void exitImportDeclaration(CQNGrammarParser.ImportDeclarationContext var1);

   void enterTypeDeclaration(CQNGrammarParser.TypeDeclarationContext var1);

   void exitTypeDeclaration(CQNGrammarParser.TypeDeclarationContext var1);

   void enterModifier(CQNGrammarParser.ModifierContext var1);

   void exitModifier(CQNGrammarParser.ModifierContext var1);

   void enterClassOrInterfaceModifier(CQNGrammarParser.ClassOrInterfaceModifierContext var1);

   void exitClassOrInterfaceModifier(CQNGrammarParser.ClassOrInterfaceModifierContext var1);

   void enterVariableModifier(CQNGrammarParser.VariableModifierContext var1);

   void exitVariableModifier(CQNGrammarParser.VariableModifierContext var1);

   void enterClassDeclaration(CQNGrammarParser.ClassDeclarationContext var1);

   void exitClassDeclaration(CQNGrammarParser.ClassDeclarationContext var1);

   void enterTypeParameters(CQNGrammarParser.TypeParametersContext var1);

   void exitTypeParameters(CQNGrammarParser.TypeParametersContext var1);

   void enterTypeParameter(CQNGrammarParser.TypeParameterContext var1);

   void exitTypeParameter(CQNGrammarParser.TypeParameterContext var1);

   void enterTypeBound(CQNGrammarParser.TypeBoundContext var1);

   void exitTypeBound(CQNGrammarParser.TypeBoundContext var1);

   void enterEnumDeclaration(CQNGrammarParser.EnumDeclarationContext var1);

   void exitEnumDeclaration(CQNGrammarParser.EnumDeclarationContext var1);

   void enterEnumConstants(CQNGrammarParser.EnumConstantsContext var1);

   void exitEnumConstants(CQNGrammarParser.EnumConstantsContext var1);

   void enterEnumConstant(CQNGrammarParser.EnumConstantContext var1);

   void exitEnumConstant(CQNGrammarParser.EnumConstantContext var1);

   void enterEnumBodyDeclarations(CQNGrammarParser.EnumBodyDeclarationsContext var1);

   void exitEnumBodyDeclarations(CQNGrammarParser.EnumBodyDeclarationsContext var1);

   void enterInterfaceDeclaration(CQNGrammarParser.InterfaceDeclarationContext var1);

   void exitInterfaceDeclaration(CQNGrammarParser.InterfaceDeclarationContext var1);

   void enterTypeList(CQNGrammarParser.TypeListContext var1);

   void exitTypeList(CQNGrammarParser.TypeListContext var1);

   void enterClassBody(CQNGrammarParser.ClassBodyContext var1);

   void exitClassBody(CQNGrammarParser.ClassBodyContext var1);

   void enterInterfaceBody(CQNGrammarParser.InterfaceBodyContext var1);

   void exitInterfaceBody(CQNGrammarParser.InterfaceBodyContext var1);

   void enterClassBodyDeclaration(CQNGrammarParser.ClassBodyDeclarationContext var1);

   void exitClassBodyDeclaration(CQNGrammarParser.ClassBodyDeclarationContext var1);

   void enterMemberDeclaration(CQNGrammarParser.MemberDeclarationContext var1);

   void exitMemberDeclaration(CQNGrammarParser.MemberDeclarationContext var1);

   void enterMethodDeclaration(CQNGrammarParser.MethodDeclarationContext var1);

   void exitMethodDeclaration(CQNGrammarParser.MethodDeclarationContext var1);

   void enterGenericMethodDeclaration(CQNGrammarParser.GenericMethodDeclarationContext var1);

   void exitGenericMethodDeclaration(CQNGrammarParser.GenericMethodDeclarationContext var1);

   void enterConstructorDeclaration(CQNGrammarParser.ConstructorDeclarationContext var1);

   void exitConstructorDeclaration(CQNGrammarParser.ConstructorDeclarationContext var1);

   void enterGenericConstructorDeclaration(CQNGrammarParser.GenericConstructorDeclarationContext var1);

   void exitGenericConstructorDeclaration(CQNGrammarParser.GenericConstructorDeclarationContext var1);

   void enterFieldDeclaration(CQNGrammarParser.FieldDeclarationContext var1);

   void exitFieldDeclaration(CQNGrammarParser.FieldDeclarationContext var1);

   void enterInterfaceBodyDeclaration(CQNGrammarParser.InterfaceBodyDeclarationContext var1);

   void exitInterfaceBodyDeclaration(CQNGrammarParser.InterfaceBodyDeclarationContext var1);

   void enterInterfaceMemberDeclaration(CQNGrammarParser.InterfaceMemberDeclarationContext var1);

   void exitInterfaceMemberDeclaration(CQNGrammarParser.InterfaceMemberDeclarationContext var1);

   void enterConstDeclaration(CQNGrammarParser.ConstDeclarationContext var1);

   void exitConstDeclaration(CQNGrammarParser.ConstDeclarationContext var1);

   void enterConstantDeclarator(CQNGrammarParser.ConstantDeclaratorContext var1);

   void exitConstantDeclarator(CQNGrammarParser.ConstantDeclaratorContext var1);

   void enterInterfaceMethodDeclaration(CQNGrammarParser.InterfaceMethodDeclarationContext var1);

   void exitInterfaceMethodDeclaration(CQNGrammarParser.InterfaceMethodDeclarationContext var1);

   void enterGenericInterfaceMethodDeclaration(CQNGrammarParser.GenericInterfaceMethodDeclarationContext var1);

   void exitGenericInterfaceMethodDeclaration(CQNGrammarParser.GenericInterfaceMethodDeclarationContext var1);

   void enterVariableDeclarators(CQNGrammarParser.VariableDeclaratorsContext var1);

   void exitVariableDeclarators(CQNGrammarParser.VariableDeclaratorsContext var1);

   void enterVariableDeclarator(CQNGrammarParser.VariableDeclaratorContext var1);

   void exitVariableDeclarator(CQNGrammarParser.VariableDeclaratorContext var1);

   void enterVariableDeclaratorId(CQNGrammarParser.VariableDeclaratorIdContext var1);

   void exitVariableDeclaratorId(CQNGrammarParser.VariableDeclaratorIdContext var1);

   void enterVariableInitializer(CQNGrammarParser.VariableInitializerContext var1);

   void exitVariableInitializer(CQNGrammarParser.VariableInitializerContext var1);

   void enterArrayInitializer(CQNGrammarParser.ArrayInitializerContext var1);

   void exitArrayInitializer(CQNGrammarParser.ArrayInitializerContext var1);

   void enterEnumConstantName(CQNGrammarParser.EnumConstantNameContext var1);

   void exitEnumConstantName(CQNGrammarParser.EnumConstantNameContext var1);

   void enterType(CQNGrammarParser.TypeContext var1);

   void exitType(CQNGrammarParser.TypeContext var1);

   void enterClassOrInterfaceType(CQNGrammarParser.ClassOrInterfaceTypeContext var1);

   void exitClassOrInterfaceType(CQNGrammarParser.ClassOrInterfaceTypeContext var1);

   void enterPrimitiveType(CQNGrammarParser.PrimitiveTypeContext var1);

   void exitPrimitiveType(CQNGrammarParser.PrimitiveTypeContext var1);

   void enterTypeArguments(CQNGrammarParser.TypeArgumentsContext var1);

   void exitTypeArguments(CQNGrammarParser.TypeArgumentsContext var1);

   void enterTypeArgument(CQNGrammarParser.TypeArgumentContext var1);

   void exitTypeArgument(CQNGrammarParser.TypeArgumentContext var1);

   void enterQualifiedNameList(CQNGrammarParser.QualifiedNameListContext var1);

   void exitQualifiedNameList(CQNGrammarParser.QualifiedNameListContext var1);

   void enterFormalParameters(CQNGrammarParser.FormalParametersContext var1);

   void exitFormalParameters(CQNGrammarParser.FormalParametersContext var1);

   void enterFormalParameterList(CQNGrammarParser.FormalParameterListContext var1);

   void exitFormalParameterList(CQNGrammarParser.FormalParameterListContext var1);

   void enterFormalParameter(CQNGrammarParser.FormalParameterContext var1);

   void exitFormalParameter(CQNGrammarParser.FormalParameterContext var1);

   void enterLastFormalParameter(CQNGrammarParser.LastFormalParameterContext var1);

   void exitLastFormalParameter(CQNGrammarParser.LastFormalParameterContext var1);

   void enterMethodBody(CQNGrammarParser.MethodBodyContext var1);

   void exitMethodBody(CQNGrammarParser.MethodBodyContext var1);

   void enterConstructorBody(CQNGrammarParser.ConstructorBodyContext var1);

   void exitConstructorBody(CQNGrammarParser.ConstructorBodyContext var1);

   void enterQualifiedName(CQNGrammarParser.QualifiedNameContext var1);

   void exitQualifiedName(CQNGrammarParser.QualifiedNameContext var1);

   void enterAnnotation(CQNGrammarParser.AnnotationContext var1);

   void exitAnnotation(CQNGrammarParser.AnnotationContext var1);

   void enterAnnotationName(CQNGrammarParser.AnnotationNameContext var1);

   void exitAnnotationName(CQNGrammarParser.AnnotationNameContext var1);

   void enterElementValuePairs(CQNGrammarParser.ElementValuePairsContext var1);

   void exitElementValuePairs(CQNGrammarParser.ElementValuePairsContext var1);

   void enterElementValuePair(CQNGrammarParser.ElementValuePairContext var1);

   void exitElementValuePair(CQNGrammarParser.ElementValuePairContext var1);

   void enterElementValue(CQNGrammarParser.ElementValueContext var1);

   void exitElementValue(CQNGrammarParser.ElementValueContext var1);

   void enterElementValueArrayInitializer(CQNGrammarParser.ElementValueArrayInitializerContext var1);

   void exitElementValueArrayInitializer(CQNGrammarParser.ElementValueArrayInitializerContext var1);

   void enterAnnotationTypeDeclaration(CQNGrammarParser.AnnotationTypeDeclarationContext var1);

   void exitAnnotationTypeDeclaration(CQNGrammarParser.AnnotationTypeDeclarationContext var1);

   void enterAnnotationTypeBody(CQNGrammarParser.AnnotationTypeBodyContext var1);

   void exitAnnotationTypeBody(CQNGrammarParser.AnnotationTypeBodyContext var1);

   void enterAnnotationTypeElementDeclaration(CQNGrammarParser.AnnotationTypeElementDeclarationContext var1);

   void exitAnnotationTypeElementDeclaration(CQNGrammarParser.AnnotationTypeElementDeclarationContext var1);

   void enterAnnotationTypeElementRest(CQNGrammarParser.AnnotationTypeElementRestContext var1);

   void exitAnnotationTypeElementRest(CQNGrammarParser.AnnotationTypeElementRestContext var1);

   void enterAnnotationMethodOrConstantRest(CQNGrammarParser.AnnotationMethodOrConstantRestContext var1);

   void exitAnnotationMethodOrConstantRest(CQNGrammarParser.AnnotationMethodOrConstantRestContext var1);

   void enterAnnotationMethodRest(CQNGrammarParser.AnnotationMethodRestContext var1);

   void exitAnnotationMethodRest(CQNGrammarParser.AnnotationMethodRestContext var1);

   void enterAnnotationConstantRest(CQNGrammarParser.AnnotationConstantRestContext var1);

   void exitAnnotationConstantRest(CQNGrammarParser.AnnotationConstantRestContext var1);

   void enterDefaultValue(CQNGrammarParser.DefaultValueContext var1);

   void exitDefaultValue(CQNGrammarParser.DefaultValueContext var1);

   void enterBlock(CQNGrammarParser.BlockContext var1);

   void exitBlock(CQNGrammarParser.BlockContext var1);

   void enterBlockStatement(CQNGrammarParser.BlockStatementContext var1);

   void exitBlockStatement(CQNGrammarParser.BlockStatementContext var1);

   void enterLocalVariableDeclarationStatement(CQNGrammarParser.LocalVariableDeclarationStatementContext var1);

   void exitLocalVariableDeclarationStatement(CQNGrammarParser.LocalVariableDeclarationStatementContext var1);

   void enterLocalVariableDeclaration(CQNGrammarParser.LocalVariableDeclarationContext var1);

   void exitLocalVariableDeclaration(CQNGrammarParser.LocalVariableDeclarationContext var1);

   void enterStatement(CQNGrammarParser.StatementContext var1);

   void exitStatement(CQNGrammarParser.StatementContext var1);

   void enterCatchClause(CQNGrammarParser.CatchClauseContext var1);

   void exitCatchClause(CQNGrammarParser.CatchClauseContext var1);

   void enterCatchType(CQNGrammarParser.CatchTypeContext var1);

   void exitCatchType(CQNGrammarParser.CatchTypeContext var1);

   void enterFinallyBlock(CQNGrammarParser.FinallyBlockContext var1);

   void exitFinallyBlock(CQNGrammarParser.FinallyBlockContext var1);

   void enterResourceSpecification(CQNGrammarParser.ResourceSpecificationContext var1);

   void exitResourceSpecification(CQNGrammarParser.ResourceSpecificationContext var1);

   void enterResources(CQNGrammarParser.ResourcesContext var1);

   void exitResources(CQNGrammarParser.ResourcesContext var1);

   void enterResource(CQNGrammarParser.ResourceContext var1);

   void exitResource(CQNGrammarParser.ResourceContext var1);

   void enterSwitchBlockStatementGroup(CQNGrammarParser.SwitchBlockStatementGroupContext var1);

   void exitSwitchBlockStatementGroup(CQNGrammarParser.SwitchBlockStatementGroupContext var1);

   void enterSwitchLabel(CQNGrammarParser.SwitchLabelContext var1);

   void exitSwitchLabel(CQNGrammarParser.SwitchLabelContext var1);

   void enterForControl(CQNGrammarParser.ForControlContext var1);

   void exitForControl(CQNGrammarParser.ForControlContext var1);

   void enterForInit(CQNGrammarParser.ForInitContext var1);

   void exitForInit(CQNGrammarParser.ForInitContext var1);

   void enterEnhancedForControl(CQNGrammarParser.EnhancedForControlContext var1);

   void exitEnhancedForControl(CQNGrammarParser.EnhancedForControlContext var1);

   void enterForUpdate(CQNGrammarParser.ForUpdateContext var1);

   void exitForUpdate(CQNGrammarParser.ForUpdateContext var1);

   void enterParExpression(CQNGrammarParser.ParExpressionContext var1);

   void exitParExpression(CQNGrammarParser.ParExpressionContext var1);

   void enterExpressionList(CQNGrammarParser.ExpressionListContext var1);

   void exitExpressionList(CQNGrammarParser.ExpressionListContext var1);

   void enterStatementExpression(CQNGrammarParser.StatementExpressionContext var1);

   void exitStatementExpression(CQNGrammarParser.StatementExpressionContext var1);

   void enterConstantExpression(CQNGrammarParser.ConstantExpressionContext var1);

   void exitConstantExpression(CQNGrammarParser.ConstantExpressionContext var1);

   void enterExpression(CQNGrammarParser.ExpressionContext var1);

   void exitExpression(CQNGrammarParser.ExpressionContext var1);

   void enterPrimary(CQNGrammarParser.PrimaryContext var1);

   void exitPrimary(CQNGrammarParser.PrimaryContext var1);

   void enterCreator(CQNGrammarParser.CreatorContext var1);

   void exitCreator(CQNGrammarParser.CreatorContext var1);

   void enterCreatedName(CQNGrammarParser.CreatedNameContext var1);

   void exitCreatedName(CQNGrammarParser.CreatedNameContext var1);

   void enterInnerCreator(CQNGrammarParser.InnerCreatorContext var1);

   void exitInnerCreator(CQNGrammarParser.InnerCreatorContext var1);

   void enterArrayCreatorRest(CQNGrammarParser.ArrayCreatorRestContext var1);

   void exitArrayCreatorRest(CQNGrammarParser.ArrayCreatorRestContext var1);

   void enterClassCreatorRest(CQNGrammarParser.ClassCreatorRestContext var1);

   void exitClassCreatorRest(CQNGrammarParser.ClassCreatorRestContext var1);

   void enterExplicitGenericInvocation(CQNGrammarParser.ExplicitGenericInvocationContext var1);

   void exitExplicitGenericInvocation(CQNGrammarParser.ExplicitGenericInvocationContext var1);

   void enterNonWildcardTypeArguments(CQNGrammarParser.NonWildcardTypeArgumentsContext var1);

   void exitNonWildcardTypeArguments(CQNGrammarParser.NonWildcardTypeArgumentsContext var1);

   void enterTypeArgumentsOrDiamond(CQNGrammarParser.TypeArgumentsOrDiamondContext var1);

   void exitTypeArgumentsOrDiamond(CQNGrammarParser.TypeArgumentsOrDiamondContext var1);

   void enterNonWildcardTypeArgumentsOrDiamond(CQNGrammarParser.NonWildcardTypeArgumentsOrDiamondContext var1);

   void exitNonWildcardTypeArgumentsOrDiamond(CQNGrammarParser.NonWildcardTypeArgumentsOrDiamondContext var1);

   void enterSuperSuffix(CQNGrammarParser.SuperSuffixContext var1);

   void exitSuperSuffix(CQNGrammarParser.SuperSuffixContext var1);

   void enterExplicitGenericInvocationSuffix(CQNGrammarParser.ExplicitGenericInvocationSuffixContext var1);

   void exitExplicitGenericInvocationSuffix(CQNGrammarParser.ExplicitGenericInvocationSuffixContext var1);

   void enterArguments(CQNGrammarParser.ArgumentsContext var1);

   void exitArguments(CQNGrammarParser.ArgumentsContext var1);
}
