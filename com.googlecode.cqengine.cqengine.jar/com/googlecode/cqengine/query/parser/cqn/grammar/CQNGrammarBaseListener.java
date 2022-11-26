package com.googlecode.cqengine.query.parser.cqn.grammar;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class CQNGrammarBaseListener implements CQNGrammarListener {
   public void enterStart(CQNGrammarParser.StartContext ctx) {
   }

   public void exitStart(CQNGrammarParser.StartContext ctx) {
   }

   public void enterQuery(CQNGrammarParser.QueryContext ctx) {
   }

   public void exitQuery(CQNGrammarParser.QueryContext ctx) {
   }

   public void enterLogicalQuery(CQNGrammarParser.LogicalQueryContext ctx) {
   }

   public void exitLogicalQuery(CQNGrammarParser.LogicalQueryContext ctx) {
   }

   public void enterAndQuery(CQNGrammarParser.AndQueryContext ctx) {
   }

   public void exitAndQuery(CQNGrammarParser.AndQueryContext ctx) {
   }

   public void enterOrQuery(CQNGrammarParser.OrQueryContext ctx) {
   }

   public void exitOrQuery(CQNGrammarParser.OrQueryContext ctx) {
   }

   public void enterNotQuery(CQNGrammarParser.NotQueryContext ctx) {
   }

   public void exitNotQuery(CQNGrammarParser.NotQueryContext ctx) {
   }

   public void enterSimpleQuery(CQNGrammarParser.SimpleQueryContext ctx) {
   }

   public void exitSimpleQuery(CQNGrammarParser.SimpleQueryContext ctx) {
   }

   public void enterEqualQuery(CQNGrammarParser.EqualQueryContext ctx) {
   }

   public void exitEqualQuery(CQNGrammarParser.EqualQueryContext ctx) {
   }

   public void enterLessThanOrEqualToQuery(CQNGrammarParser.LessThanOrEqualToQueryContext ctx) {
   }

   public void exitLessThanOrEqualToQuery(CQNGrammarParser.LessThanOrEqualToQueryContext ctx) {
   }

   public void enterLessThanQuery(CQNGrammarParser.LessThanQueryContext ctx) {
   }

   public void exitLessThanQuery(CQNGrammarParser.LessThanQueryContext ctx) {
   }

   public void enterGreaterThanOrEqualToQuery(CQNGrammarParser.GreaterThanOrEqualToQueryContext ctx) {
   }

   public void exitGreaterThanOrEqualToQuery(CQNGrammarParser.GreaterThanOrEqualToQueryContext ctx) {
   }

   public void enterGreaterThanQuery(CQNGrammarParser.GreaterThanQueryContext ctx) {
   }

   public void exitGreaterThanQuery(CQNGrammarParser.GreaterThanQueryContext ctx) {
   }

   public void enterVerboseBetweenQuery(CQNGrammarParser.VerboseBetweenQueryContext ctx) {
   }

   public void exitVerboseBetweenQuery(CQNGrammarParser.VerboseBetweenQueryContext ctx) {
   }

   public void enterBetweenQuery(CQNGrammarParser.BetweenQueryContext ctx) {
   }

   public void exitBetweenQuery(CQNGrammarParser.BetweenQueryContext ctx) {
   }

   public void enterInQuery(CQNGrammarParser.InQueryContext ctx) {
   }

   public void exitInQuery(CQNGrammarParser.InQueryContext ctx) {
   }

   public void enterStartsWithQuery(CQNGrammarParser.StartsWithQueryContext ctx) {
   }

   public void exitStartsWithQuery(CQNGrammarParser.StartsWithQueryContext ctx) {
   }

   public void enterEndsWithQuery(CQNGrammarParser.EndsWithQueryContext ctx) {
   }

   public void exitEndsWithQuery(CQNGrammarParser.EndsWithQueryContext ctx) {
   }

   public void enterContainsQuery(CQNGrammarParser.ContainsQueryContext ctx) {
   }

   public void exitContainsQuery(CQNGrammarParser.ContainsQueryContext ctx) {
   }

   public void enterIsContainedInQuery(CQNGrammarParser.IsContainedInQueryContext ctx) {
   }

   public void exitIsContainedInQuery(CQNGrammarParser.IsContainedInQueryContext ctx) {
   }

   public void enterMatchesRegexQuery(CQNGrammarParser.MatchesRegexQueryContext ctx) {
   }

   public void exitMatchesRegexQuery(CQNGrammarParser.MatchesRegexQueryContext ctx) {
   }

   public void enterHasQuery(CQNGrammarParser.HasQueryContext ctx) {
   }

   public void exitHasQuery(CQNGrammarParser.HasQueryContext ctx) {
   }

   public void enterAllQuery(CQNGrammarParser.AllQueryContext ctx) {
   }

   public void exitAllQuery(CQNGrammarParser.AllQueryContext ctx) {
   }

   public void enterNoneQuery(CQNGrammarParser.NoneQueryContext ctx) {
   }

   public void exitNoneQuery(CQNGrammarParser.NoneQueryContext ctx) {
   }

   public void enterObjectType(CQNGrammarParser.ObjectTypeContext ctx) {
   }

   public void exitObjectType(CQNGrammarParser.ObjectTypeContext ctx) {
   }

   public void enterAttributeName(CQNGrammarParser.AttributeNameContext ctx) {
   }

   public void exitAttributeName(CQNGrammarParser.AttributeNameContext ctx) {
   }

   public void enterQueryParameter(CQNGrammarParser.QueryParameterContext ctx) {
   }

   public void exitQueryParameter(CQNGrammarParser.QueryParameterContext ctx) {
   }

   public void enterStringQueryParameter(CQNGrammarParser.StringQueryParameterContext ctx) {
   }

   public void exitStringQueryParameter(CQNGrammarParser.StringQueryParameterContext ctx) {
   }

   public void enterQueryOptions(CQNGrammarParser.QueryOptionsContext ctx) {
   }

   public void exitQueryOptions(CQNGrammarParser.QueryOptionsContext ctx) {
   }

   public void enterQueryOption(CQNGrammarParser.QueryOptionContext ctx) {
   }

   public void exitQueryOption(CQNGrammarParser.QueryOptionContext ctx) {
   }

   public void enterOrderByOption(CQNGrammarParser.OrderByOptionContext ctx) {
   }

   public void exitOrderByOption(CQNGrammarParser.OrderByOptionContext ctx) {
   }

   public void enterAttributeOrder(CQNGrammarParser.AttributeOrderContext ctx) {
   }

   public void exitAttributeOrder(CQNGrammarParser.AttributeOrderContext ctx) {
   }

   public void enterDirection(CQNGrammarParser.DirectionContext ctx) {
   }

   public void exitDirection(CQNGrammarParser.DirectionContext ctx) {
   }

   public void enterLiteral(CQNGrammarParser.LiteralContext ctx) {
   }

   public void exitLiteral(CQNGrammarParser.LiteralContext ctx) {
   }

   public void enterCompilationUnit(CQNGrammarParser.CompilationUnitContext ctx) {
   }

   public void exitCompilationUnit(CQNGrammarParser.CompilationUnitContext ctx) {
   }

   public void enterPackageDeclaration(CQNGrammarParser.PackageDeclarationContext ctx) {
   }

   public void exitPackageDeclaration(CQNGrammarParser.PackageDeclarationContext ctx) {
   }

   public void enterImportDeclaration(CQNGrammarParser.ImportDeclarationContext ctx) {
   }

   public void exitImportDeclaration(CQNGrammarParser.ImportDeclarationContext ctx) {
   }

   public void enterTypeDeclaration(CQNGrammarParser.TypeDeclarationContext ctx) {
   }

   public void exitTypeDeclaration(CQNGrammarParser.TypeDeclarationContext ctx) {
   }

   public void enterModifier(CQNGrammarParser.ModifierContext ctx) {
   }

   public void exitModifier(CQNGrammarParser.ModifierContext ctx) {
   }

   public void enterClassOrInterfaceModifier(CQNGrammarParser.ClassOrInterfaceModifierContext ctx) {
   }

   public void exitClassOrInterfaceModifier(CQNGrammarParser.ClassOrInterfaceModifierContext ctx) {
   }

   public void enterVariableModifier(CQNGrammarParser.VariableModifierContext ctx) {
   }

   public void exitVariableModifier(CQNGrammarParser.VariableModifierContext ctx) {
   }

   public void enterClassDeclaration(CQNGrammarParser.ClassDeclarationContext ctx) {
   }

   public void exitClassDeclaration(CQNGrammarParser.ClassDeclarationContext ctx) {
   }

   public void enterTypeParameters(CQNGrammarParser.TypeParametersContext ctx) {
   }

   public void exitTypeParameters(CQNGrammarParser.TypeParametersContext ctx) {
   }

   public void enterTypeParameter(CQNGrammarParser.TypeParameterContext ctx) {
   }

   public void exitTypeParameter(CQNGrammarParser.TypeParameterContext ctx) {
   }

   public void enterTypeBound(CQNGrammarParser.TypeBoundContext ctx) {
   }

   public void exitTypeBound(CQNGrammarParser.TypeBoundContext ctx) {
   }

   public void enterEnumDeclaration(CQNGrammarParser.EnumDeclarationContext ctx) {
   }

   public void exitEnumDeclaration(CQNGrammarParser.EnumDeclarationContext ctx) {
   }

   public void enterEnumConstants(CQNGrammarParser.EnumConstantsContext ctx) {
   }

   public void exitEnumConstants(CQNGrammarParser.EnumConstantsContext ctx) {
   }

   public void enterEnumConstant(CQNGrammarParser.EnumConstantContext ctx) {
   }

   public void exitEnumConstant(CQNGrammarParser.EnumConstantContext ctx) {
   }

   public void enterEnumBodyDeclarations(CQNGrammarParser.EnumBodyDeclarationsContext ctx) {
   }

   public void exitEnumBodyDeclarations(CQNGrammarParser.EnumBodyDeclarationsContext ctx) {
   }

   public void enterInterfaceDeclaration(CQNGrammarParser.InterfaceDeclarationContext ctx) {
   }

   public void exitInterfaceDeclaration(CQNGrammarParser.InterfaceDeclarationContext ctx) {
   }

   public void enterTypeList(CQNGrammarParser.TypeListContext ctx) {
   }

   public void exitTypeList(CQNGrammarParser.TypeListContext ctx) {
   }

   public void enterClassBody(CQNGrammarParser.ClassBodyContext ctx) {
   }

   public void exitClassBody(CQNGrammarParser.ClassBodyContext ctx) {
   }

   public void enterInterfaceBody(CQNGrammarParser.InterfaceBodyContext ctx) {
   }

   public void exitInterfaceBody(CQNGrammarParser.InterfaceBodyContext ctx) {
   }

   public void enterClassBodyDeclaration(CQNGrammarParser.ClassBodyDeclarationContext ctx) {
   }

   public void exitClassBodyDeclaration(CQNGrammarParser.ClassBodyDeclarationContext ctx) {
   }

   public void enterMemberDeclaration(CQNGrammarParser.MemberDeclarationContext ctx) {
   }

   public void exitMemberDeclaration(CQNGrammarParser.MemberDeclarationContext ctx) {
   }

   public void enterMethodDeclaration(CQNGrammarParser.MethodDeclarationContext ctx) {
   }

   public void exitMethodDeclaration(CQNGrammarParser.MethodDeclarationContext ctx) {
   }

   public void enterGenericMethodDeclaration(CQNGrammarParser.GenericMethodDeclarationContext ctx) {
   }

   public void exitGenericMethodDeclaration(CQNGrammarParser.GenericMethodDeclarationContext ctx) {
   }

   public void enterConstructorDeclaration(CQNGrammarParser.ConstructorDeclarationContext ctx) {
   }

   public void exitConstructorDeclaration(CQNGrammarParser.ConstructorDeclarationContext ctx) {
   }

   public void enterGenericConstructorDeclaration(CQNGrammarParser.GenericConstructorDeclarationContext ctx) {
   }

   public void exitGenericConstructorDeclaration(CQNGrammarParser.GenericConstructorDeclarationContext ctx) {
   }

   public void enterFieldDeclaration(CQNGrammarParser.FieldDeclarationContext ctx) {
   }

   public void exitFieldDeclaration(CQNGrammarParser.FieldDeclarationContext ctx) {
   }

   public void enterInterfaceBodyDeclaration(CQNGrammarParser.InterfaceBodyDeclarationContext ctx) {
   }

   public void exitInterfaceBodyDeclaration(CQNGrammarParser.InterfaceBodyDeclarationContext ctx) {
   }

   public void enterInterfaceMemberDeclaration(CQNGrammarParser.InterfaceMemberDeclarationContext ctx) {
   }

   public void exitInterfaceMemberDeclaration(CQNGrammarParser.InterfaceMemberDeclarationContext ctx) {
   }

   public void enterConstDeclaration(CQNGrammarParser.ConstDeclarationContext ctx) {
   }

   public void exitConstDeclaration(CQNGrammarParser.ConstDeclarationContext ctx) {
   }

   public void enterConstantDeclarator(CQNGrammarParser.ConstantDeclaratorContext ctx) {
   }

   public void exitConstantDeclarator(CQNGrammarParser.ConstantDeclaratorContext ctx) {
   }

   public void enterInterfaceMethodDeclaration(CQNGrammarParser.InterfaceMethodDeclarationContext ctx) {
   }

   public void exitInterfaceMethodDeclaration(CQNGrammarParser.InterfaceMethodDeclarationContext ctx) {
   }

   public void enterGenericInterfaceMethodDeclaration(CQNGrammarParser.GenericInterfaceMethodDeclarationContext ctx) {
   }

   public void exitGenericInterfaceMethodDeclaration(CQNGrammarParser.GenericInterfaceMethodDeclarationContext ctx) {
   }

   public void enterVariableDeclarators(CQNGrammarParser.VariableDeclaratorsContext ctx) {
   }

   public void exitVariableDeclarators(CQNGrammarParser.VariableDeclaratorsContext ctx) {
   }

   public void enterVariableDeclarator(CQNGrammarParser.VariableDeclaratorContext ctx) {
   }

   public void exitVariableDeclarator(CQNGrammarParser.VariableDeclaratorContext ctx) {
   }

   public void enterVariableDeclaratorId(CQNGrammarParser.VariableDeclaratorIdContext ctx) {
   }

   public void exitVariableDeclaratorId(CQNGrammarParser.VariableDeclaratorIdContext ctx) {
   }

   public void enterVariableInitializer(CQNGrammarParser.VariableInitializerContext ctx) {
   }

   public void exitVariableInitializer(CQNGrammarParser.VariableInitializerContext ctx) {
   }

   public void enterArrayInitializer(CQNGrammarParser.ArrayInitializerContext ctx) {
   }

   public void exitArrayInitializer(CQNGrammarParser.ArrayInitializerContext ctx) {
   }

   public void enterEnumConstantName(CQNGrammarParser.EnumConstantNameContext ctx) {
   }

   public void exitEnumConstantName(CQNGrammarParser.EnumConstantNameContext ctx) {
   }

   public void enterType(CQNGrammarParser.TypeContext ctx) {
   }

   public void exitType(CQNGrammarParser.TypeContext ctx) {
   }

   public void enterClassOrInterfaceType(CQNGrammarParser.ClassOrInterfaceTypeContext ctx) {
   }

   public void exitClassOrInterfaceType(CQNGrammarParser.ClassOrInterfaceTypeContext ctx) {
   }

   public void enterPrimitiveType(CQNGrammarParser.PrimitiveTypeContext ctx) {
   }

   public void exitPrimitiveType(CQNGrammarParser.PrimitiveTypeContext ctx) {
   }

   public void enterTypeArguments(CQNGrammarParser.TypeArgumentsContext ctx) {
   }

   public void exitTypeArguments(CQNGrammarParser.TypeArgumentsContext ctx) {
   }

   public void enterTypeArgument(CQNGrammarParser.TypeArgumentContext ctx) {
   }

   public void exitTypeArgument(CQNGrammarParser.TypeArgumentContext ctx) {
   }

   public void enterQualifiedNameList(CQNGrammarParser.QualifiedNameListContext ctx) {
   }

   public void exitQualifiedNameList(CQNGrammarParser.QualifiedNameListContext ctx) {
   }

   public void enterFormalParameters(CQNGrammarParser.FormalParametersContext ctx) {
   }

   public void exitFormalParameters(CQNGrammarParser.FormalParametersContext ctx) {
   }

   public void enterFormalParameterList(CQNGrammarParser.FormalParameterListContext ctx) {
   }

   public void exitFormalParameterList(CQNGrammarParser.FormalParameterListContext ctx) {
   }

   public void enterFormalParameter(CQNGrammarParser.FormalParameterContext ctx) {
   }

   public void exitFormalParameter(CQNGrammarParser.FormalParameterContext ctx) {
   }

   public void enterLastFormalParameter(CQNGrammarParser.LastFormalParameterContext ctx) {
   }

   public void exitLastFormalParameter(CQNGrammarParser.LastFormalParameterContext ctx) {
   }

   public void enterMethodBody(CQNGrammarParser.MethodBodyContext ctx) {
   }

   public void exitMethodBody(CQNGrammarParser.MethodBodyContext ctx) {
   }

   public void enterConstructorBody(CQNGrammarParser.ConstructorBodyContext ctx) {
   }

   public void exitConstructorBody(CQNGrammarParser.ConstructorBodyContext ctx) {
   }

   public void enterQualifiedName(CQNGrammarParser.QualifiedNameContext ctx) {
   }

   public void exitQualifiedName(CQNGrammarParser.QualifiedNameContext ctx) {
   }

   public void enterAnnotation(CQNGrammarParser.AnnotationContext ctx) {
   }

   public void exitAnnotation(CQNGrammarParser.AnnotationContext ctx) {
   }

   public void enterAnnotationName(CQNGrammarParser.AnnotationNameContext ctx) {
   }

   public void exitAnnotationName(CQNGrammarParser.AnnotationNameContext ctx) {
   }

   public void enterElementValuePairs(CQNGrammarParser.ElementValuePairsContext ctx) {
   }

   public void exitElementValuePairs(CQNGrammarParser.ElementValuePairsContext ctx) {
   }

   public void enterElementValuePair(CQNGrammarParser.ElementValuePairContext ctx) {
   }

   public void exitElementValuePair(CQNGrammarParser.ElementValuePairContext ctx) {
   }

   public void enterElementValue(CQNGrammarParser.ElementValueContext ctx) {
   }

   public void exitElementValue(CQNGrammarParser.ElementValueContext ctx) {
   }

   public void enterElementValueArrayInitializer(CQNGrammarParser.ElementValueArrayInitializerContext ctx) {
   }

   public void exitElementValueArrayInitializer(CQNGrammarParser.ElementValueArrayInitializerContext ctx) {
   }

   public void enterAnnotationTypeDeclaration(CQNGrammarParser.AnnotationTypeDeclarationContext ctx) {
   }

   public void exitAnnotationTypeDeclaration(CQNGrammarParser.AnnotationTypeDeclarationContext ctx) {
   }

   public void enterAnnotationTypeBody(CQNGrammarParser.AnnotationTypeBodyContext ctx) {
   }

   public void exitAnnotationTypeBody(CQNGrammarParser.AnnotationTypeBodyContext ctx) {
   }

   public void enterAnnotationTypeElementDeclaration(CQNGrammarParser.AnnotationTypeElementDeclarationContext ctx) {
   }

   public void exitAnnotationTypeElementDeclaration(CQNGrammarParser.AnnotationTypeElementDeclarationContext ctx) {
   }

   public void enterAnnotationTypeElementRest(CQNGrammarParser.AnnotationTypeElementRestContext ctx) {
   }

   public void exitAnnotationTypeElementRest(CQNGrammarParser.AnnotationTypeElementRestContext ctx) {
   }

   public void enterAnnotationMethodOrConstantRest(CQNGrammarParser.AnnotationMethodOrConstantRestContext ctx) {
   }

   public void exitAnnotationMethodOrConstantRest(CQNGrammarParser.AnnotationMethodOrConstantRestContext ctx) {
   }

   public void enterAnnotationMethodRest(CQNGrammarParser.AnnotationMethodRestContext ctx) {
   }

   public void exitAnnotationMethodRest(CQNGrammarParser.AnnotationMethodRestContext ctx) {
   }

   public void enterAnnotationConstantRest(CQNGrammarParser.AnnotationConstantRestContext ctx) {
   }

   public void exitAnnotationConstantRest(CQNGrammarParser.AnnotationConstantRestContext ctx) {
   }

   public void enterDefaultValue(CQNGrammarParser.DefaultValueContext ctx) {
   }

   public void exitDefaultValue(CQNGrammarParser.DefaultValueContext ctx) {
   }

   public void enterBlock(CQNGrammarParser.BlockContext ctx) {
   }

   public void exitBlock(CQNGrammarParser.BlockContext ctx) {
   }

   public void enterBlockStatement(CQNGrammarParser.BlockStatementContext ctx) {
   }

   public void exitBlockStatement(CQNGrammarParser.BlockStatementContext ctx) {
   }

   public void enterLocalVariableDeclarationStatement(CQNGrammarParser.LocalVariableDeclarationStatementContext ctx) {
   }

   public void exitLocalVariableDeclarationStatement(CQNGrammarParser.LocalVariableDeclarationStatementContext ctx) {
   }

   public void enterLocalVariableDeclaration(CQNGrammarParser.LocalVariableDeclarationContext ctx) {
   }

   public void exitLocalVariableDeclaration(CQNGrammarParser.LocalVariableDeclarationContext ctx) {
   }

   public void enterStatement(CQNGrammarParser.StatementContext ctx) {
   }

   public void exitStatement(CQNGrammarParser.StatementContext ctx) {
   }

   public void enterCatchClause(CQNGrammarParser.CatchClauseContext ctx) {
   }

   public void exitCatchClause(CQNGrammarParser.CatchClauseContext ctx) {
   }

   public void enterCatchType(CQNGrammarParser.CatchTypeContext ctx) {
   }

   public void exitCatchType(CQNGrammarParser.CatchTypeContext ctx) {
   }

   public void enterFinallyBlock(CQNGrammarParser.FinallyBlockContext ctx) {
   }

   public void exitFinallyBlock(CQNGrammarParser.FinallyBlockContext ctx) {
   }

   public void enterResourceSpecification(CQNGrammarParser.ResourceSpecificationContext ctx) {
   }

   public void exitResourceSpecification(CQNGrammarParser.ResourceSpecificationContext ctx) {
   }

   public void enterResources(CQNGrammarParser.ResourcesContext ctx) {
   }

   public void exitResources(CQNGrammarParser.ResourcesContext ctx) {
   }

   public void enterResource(CQNGrammarParser.ResourceContext ctx) {
   }

   public void exitResource(CQNGrammarParser.ResourceContext ctx) {
   }

   public void enterSwitchBlockStatementGroup(CQNGrammarParser.SwitchBlockStatementGroupContext ctx) {
   }

   public void exitSwitchBlockStatementGroup(CQNGrammarParser.SwitchBlockStatementGroupContext ctx) {
   }

   public void enterSwitchLabel(CQNGrammarParser.SwitchLabelContext ctx) {
   }

   public void exitSwitchLabel(CQNGrammarParser.SwitchLabelContext ctx) {
   }

   public void enterForControl(CQNGrammarParser.ForControlContext ctx) {
   }

   public void exitForControl(CQNGrammarParser.ForControlContext ctx) {
   }

   public void enterForInit(CQNGrammarParser.ForInitContext ctx) {
   }

   public void exitForInit(CQNGrammarParser.ForInitContext ctx) {
   }

   public void enterEnhancedForControl(CQNGrammarParser.EnhancedForControlContext ctx) {
   }

   public void exitEnhancedForControl(CQNGrammarParser.EnhancedForControlContext ctx) {
   }

   public void enterForUpdate(CQNGrammarParser.ForUpdateContext ctx) {
   }

   public void exitForUpdate(CQNGrammarParser.ForUpdateContext ctx) {
   }

   public void enterParExpression(CQNGrammarParser.ParExpressionContext ctx) {
   }

   public void exitParExpression(CQNGrammarParser.ParExpressionContext ctx) {
   }

   public void enterExpressionList(CQNGrammarParser.ExpressionListContext ctx) {
   }

   public void exitExpressionList(CQNGrammarParser.ExpressionListContext ctx) {
   }

   public void enterStatementExpression(CQNGrammarParser.StatementExpressionContext ctx) {
   }

   public void exitStatementExpression(CQNGrammarParser.StatementExpressionContext ctx) {
   }

   public void enterConstantExpression(CQNGrammarParser.ConstantExpressionContext ctx) {
   }

   public void exitConstantExpression(CQNGrammarParser.ConstantExpressionContext ctx) {
   }

   public void enterExpression(CQNGrammarParser.ExpressionContext ctx) {
   }

   public void exitExpression(CQNGrammarParser.ExpressionContext ctx) {
   }

   public void enterPrimary(CQNGrammarParser.PrimaryContext ctx) {
   }

   public void exitPrimary(CQNGrammarParser.PrimaryContext ctx) {
   }

   public void enterCreator(CQNGrammarParser.CreatorContext ctx) {
   }

   public void exitCreator(CQNGrammarParser.CreatorContext ctx) {
   }

   public void enterCreatedName(CQNGrammarParser.CreatedNameContext ctx) {
   }

   public void exitCreatedName(CQNGrammarParser.CreatedNameContext ctx) {
   }

   public void enterInnerCreator(CQNGrammarParser.InnerCreatorContext ctx) {
   }

   public void exitInnerCreator(CQNGrammarParser.InnerCreatorContext ctx) {
   }

   public void enterArrayCreatorRest(CQNGrammarParser.ArrayCreatorRestContext ctx) {
   }

   public void exitArrayCreatorRest(CQNGrammarParser.ArrayCreatorRestContext ctx) {
   }

   public void enterClassCreatorRest(CQNGrammarParser.ClassCreatorRestContext ctx) {
   }

   public void exitClassCreatorRest(CQNGrammarParser.ClassCreatorRestContext ctx) {
   }

   public void enterExplicitGenericInvocation(CQNGrammarParser.ExplicitGenericInvocationContext ctx) {
   }

   public void exitExplicitGenericInvocation(CQNGrammarParser.ExplicitGenericInvocationContext ctx) {
   }

   public void enterNonWildcardTypeArguments(CQNGrammarParser.NonWildcardTypeArgumentsContext ctx) {
   }

   public void exitNonWildcardTypeArguments(CQNGrammarParser.NonWildcardTypeArgumentsContext ctx) {
   }

   public void enterTypeArgumentsOrDiamond(CQNGrammarParser.TypeArgumentsOrDiamondContext ctx) {
   }

   public void exitTypeArgumentsOrDiamond(CQNGrammarParser.TypeArgumentsOrDiamondContext ctx) {
   }

   public void enterNonWildcardTypeArgumentsOrDiamond(CQNGrammarParser.NonWildcardTypeArgumentsOrDiamondContext ctx) {
   }

   public void exitNonWildcardTypeArgumentsOrDiamond(CQNGrammarParser.NonWildcardTypeArgumentsOrDiamondContext ctx) {
   }

   public void enterSuperSuffix(CQNGrammarParser.SuperSuffixContext ctx) {
   }

   public void exitSuperSuffix(CQNGrammarParser.SuperSuffixContext ctx) {
   }

   public void enterExplicitGenericInvocationSuffix(CQNGrammarParser.ExplicitGenericInvocationSuffixContext ctx) {
   }

   public void exitExplicitGenericInvocationSuffix(CQNGrammarParser.ExplicitGenericInvocationSuffixContext ctx) {
   }

   public void enterArguments(CQNGrammarParser.ArgumentsContext ctx) {
   }

   public void exitArguments(CQNGrammarParser.ArgumentsContext ctx) {
   }

   public void enterEveryRule(ParserRuleContext ctx) {
   }

   public void exitEveryRule(ParserRuleContext ctx) {
   }

   public void visitTerminal(TerminalNode node) {
   }

   public void visitErrorNode(ErrorNode node) {
   }
}
