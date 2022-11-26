package com.googlecode.cqengine.query.parser.sql.grammar;

import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface SQLGrammarListener extends ParseTreeListener {
   void enterStart(SQLGrammarParser.StartContext var1);

   void exitStart(SQLGrammarParser.StartContext var1);

   void enterIndexedCollection(SQLGrammarParser.IndexedCollectionContext var1);

   void exitIndexedCollection(SQLGrammarParser.IndexedCollectionContext var1);

   void enterWhereClause(SQLGrammarParser.WhereClauseContext var1);

   void exitWhereClause(SQLGrammarParser.WhereClauseContext var1);

   void enterOrderByClause(SQLGrammarParser.OrderByClauseContext var1);

   void exitOrderByClause(SQLGrammarParser.OrderByClauseContext var1);

   void enterQuery(SQLGrammarParser.QueryContext var1);

   void exitQuery(SQLGrammarParser.QueryContext var1);

   void enterLogicalQuery(SQLGrammarParser.LogicalQueryContext var1);

   void exitLogicalQuery(SQLGrammarParser.LogicalQueryContext var1);

   void enterAndQuery(SQLGrammarParser.AndQueryContext var1);

   void exitAndQuery(SQLGrammarParser.AndQueryContext var1);

   void enterOrQuery(SQLGrammarParser.OrQueryContext var1);

   void exitOrQuery(SQLGrammarParser.OrQueryContext var1);

   void enterNotQuery(SQLGrammarParser.NotQueryContext var1);

   void exitNotQuery(SQLGrammarParser.NotQueryContext var1);

   void enterSimpleQuery(SQLGrammarParser.SimpleQueryContext var1);

   void exitSimpleQuery(SQLGrammarParser.SimpleQueryContext var1);

   void enterEqualQuery(SQLGrammarParser.EqualQueryContext var1);

   void exitEqualQuery(SQLGrammarParser.EqualQueryContext var1);

   void enterNotEqualQuery(SQLGrammarParser.NotEqualQueryContext var1);

   void exitNotEqualQuery(SQLGrammarParser.NotEqualQueryContext var1);

   void enterLessThanOrEqualToQuery(SQLGrammarParser.LessThanOrEqualToQueryContext var1);

   void exitLessThanOrEqualToQuery(SQLGrammarParser.LessThanOrEqualToQueryContext var1);

   void enterLessThanQuery(SQLGrammarParser.LessThanQueryContext var1);

   void exitLessThanQuery(SQLGrammarParser.LessThanQueryContext var1);

   void enterGreaterThanOrEqualToQuery(SQLGrammarParser.GreaterThanOrEqualToQueryContext var1);

   void exitGreaterThanOrEqualToQuery(SQLGrammarParser.GreaterThanOrEqualToQueryContext var1);

   void enterGreaterThanQuery(SQLGrammarParser.GreaterThanQueryContext var1);

   void exitGreaterThanQuery(SQLGrammarParser.GreaterThanQueryContext var1);

   void enterBetweenQuery(SQLGrammarParser.BetweenQueryContext var1);

   void exitBetweenQuery(SQLGrammarParser.BetweenQueryContext var1);

   void enterNotBetweenQuery(SQLGrammarParser.NotBetweenQueryContext var1);

   void exitNotBetweenQuery(SQLGrammarParser.NotBetweenQueryContext var1);

   void enterInQuery(SQLGrammarParser.InQueryContext var1);

   void exitInQuery(SQLGrammarParser.InQueryContext var1);

   void enterNotInQuery(SQLGrammarParser.NotInQueryContext var1);

   void exitNotInQuery(SQLGrammarParser.NotInQueryContext var1);

   void enterStartsWithQuery(SQLGrammarParser.StartsWithQueryContext var1);

   void exitStartsWithQuery(SQLGrammarParser.StartsWithQueryContext var1);

   void enterEndsWithQuery(SQLGrammarParser.EndsWithQueryContext var1);

   void exitEndsWithQuery(SQLGrammarParser.EndsWithQueryContext var1);

   void enterContainsQuery(SQLGrammarParser.ContainsQueryContext var1);

   void exitContainsQuery(SQLGrammarParser.ContainsQueryContext var1);

   void enterHasQuery(SQLGrammarParser.HasQueryContext var1);

   void exitHasQuery(SQLGrammarParser.HasQueryContext var1);

   void enterNotHasQuery(SQLGrammarParser.NotHasQueryContext var1);

   void exitNotHasQuery(SQLGrammarParser.NotHasQueryContext var1);

   void enterAttributeName(SQLGrammarParser.AttributeNameContext var1);

   void exitAttributeName(SQLGrammarParser.AttributeNameContext var1);

   void enterQueryParameterTrailingPercent(SQLGrammarParser.QueryParameterTrailingPercentContext var1);

   void exitQueryParameterTrailingPercent(SQLGrammarParser.QueryParameterTrailingPercentContext var1);

   void enterQueryParameterLeadingPercent(SQLGrammarParser.QueryParameterLeadingPercentContext var1);

   void exitQueryParameterLeadingPercent(SQLGrammarParser.QueryParameterLeadingPercentContext var1);

   void enterQueryParameterLeadingAndTrailingPercent(SQLGrammarParser.QueryParameterLeadingAndTrailingPercentContext var1);

   void exitQueryParameterLeadingAndTrailingPercent(SQLGrammarParser.QueryParameterLeadingAndTrailingPercentContext var1);

   void enterQueryParameter(SQLGrammarParser.QueryParameterContext var1);

   void exitQueryParameter(SQLGrammarParser.QueryParameterContext var1);

   void enterAttributeOrder(SQLGrammarParser.AttributeOrderContext var1);

   void exitAttributeOrder(SQLGrammarParser.AttributeOrderContext var1);

   void enterDirection(SQLGrammarParser.DirectionContext var1);

   void exitDirection(SQLGrammarParser.DirectionContext var1);

   void enterParse(SQLGrammarParser.ParseContext var1);

   void exitParse(SQLGrammarParser.ParseContext var1);

   void enterError(SQLGrammarParser.ErrorContext var1);

   void exitError(SQLGrammarParser.ErrorContext var1);

   void enterSql_stmt_list(SQLGrammarParser.Sql_stmt_listContext var1);

   void exitSql_stmt_list(SQLGrammarParser.Sql_stmt_listContext var1);

   void enterSql_stmt(SQLGrammarParser.Sql_stmtContext var1);

   void exitSql_stmt(SQLGrammarParser.Sql_stmtContext var1);

   void enterAlter_table_stmt(SQLGrammarParser.Alter_table_stmtContext var1);

   void exitAlter_table_stmt(SQLGrammarParser.Alter_table_stmtContext var1);

   void enterAnalyze_stmt(SQLGrammarParser.Analyze_stmtContext var1);

   void exitAnalyze_stmt(SQLGrammarParser.Analyze_stmtContext var1);

   void enterAttach_stmt(SQLGrammarParser.Attach_stmtContext var1);

   void exitAttach_stmt(SQLGrammarParser.Attach_stmtContext var1);

   void enterBegin_stmt(SQLGrammarParser.Begin_stmtContext var1);

   void exitBegin_stmt(SQLGrammarParser.Begin_stmtContext var1);

   void enterCommit_stmt(SQLGrammarParser.Commit_stmtContext var1);

   void exitCommit_stmt(SQLGrammarParser.Commit_stmtContext var1);

   void enterCompound_select_stmt(SQLGrammarParser.Compound_select_stmtContext var1);

   void exitCompound_select_stmt(SQLGrammarParser.Compound_select_stmtContext var1);

   void enterCreate_index_stmt(SQLGrammarParser.Create_index_stmtContext var1);

   void exitCreate_index_stmt(SQLGrammarParser.Create_index_stmtContext var1);

   void enterCreate_table_stmt(SQLGrammarParser.Create_table_stmtContext var1);

   void exitCreate_table_stmt(SQLGrammarParser.Create_table_stmtContext var1);

   void enterCreate_trigger_stmt(SQLGrammarParser.Create_trigger_stmtContext var1);

   void exitCreate_trigger_stmt(SQLGrammarParser.Create_trigger_stmtContext var1);

   void enterCreate_view_stmt(SQLGrammarParser.Create_view_stmtContext var1);

   void exitCreate_view_stmt(SQLGrammarParser.Create_view_stmtContext var1);

   void enterCreate_virtual_table_stmt(SQLGrammarParser.Create_virtual_table_stmtContext var1);

   void exitCreate_virtual_table_stmt(SQLGrammarParser.Create_virtual_table_stmtContext var1);

   void enterDelete_stmt(SQLGrammarParser.Delete_stmtContext var1);

   void exitDelete_stmt(SQLGrammarParser.Delete_stmtContext var1);

   void enterDelete_stmt_limited(SQLGrammarParser.Delete_stmt_limitedContext var1);

   void exitDelete_stmt_limited(SQLGrammarParser.Delete_stmt_limitedContext var1);

   void enterDetach_stmt(SQLGrammarParser.Detach_stmtContext var1);

   void exitDetach_stmt(SQLGrammarParser.Detach_stmtContext var1);

   void enterDrop_index_stmt(SQLGrammarParser.Drop_index_stmtContext var1);

   void exitDrop_index_stmt(SQLGrammarParser.Drop_index_stmtContext var1);

   void enterDrop_table_stmt(SQLGrammarParser.Drop_table_stmtContext var1);

   void exitDrop_table_stmt(SQLGrammarParser.Drop_table_stmtContext var1);

   void enterDrop_trigger_stmt(SQLGrammarParser.Drop_trigger_stmtContext var1);

   void exitDrop_trigger_stmt(SQLGrammarParser.Drop_trigger_stmtContext var1);

   void enterDrop_view_stmt(SQLGrammarParser.Drop_view_stmtContext var1);

   void exitDrop_view_stmt(SQLGrammarParser.Drop_view_stmtContext var1);

   void enterFactored_select_stmt(SQLGrammarParser.Factored_select_stmtContext var1);

   void exitFactored_select_stmt(SQLGrammarParser.Factored_select_stmtContext var1);

   void enterInsert_stmt(SQLGrammarParser.Insert_stmtContext var1);

   void exitInsert_stmt(SQLGrammarParser.Insert_stmtContext var1);

   void enterPragma_stmt(SQLGrammarParser.Pragma_stmtContext var1);

   void exitPragma_stmt(SQLGrammarParser.Pragma_stmtContext var1);

   void enterReindex_stmt(SQLGrammarParser.Reindex_stmtContext var1);

   void exitReindex_stmt(SQLGrammarParser.Reindex_stmtContext var1);

   void enterRelease_stmt(SQLGrammarParser.Release_stmtContext var1);

   void exitRelease_stmt(SQLGrammarParser.Release_stmtContext var1);

   void enterRollback_stmt(SQLGrammarParser.Rollback_stmtContext var1);

   void exitRollback_stmt(SQLGrammarParser.Rollback_stmtContext var1);

   void enterSavepoint_stmt(SQLGrammarParser.Savepoint_stmtContext var1);

   void exitSavepoint_stmt(SQLGrammarParser.Savepoint_stmtContext var1);

   void enterSimple_select_stmt(SQLGrammarParser.Simple_select_stmtContext var1);

   void exitSimple_select_stmt(SQLGrammarParser.Simple_select_stmtContext var1);

   void enterSelect_stmt(SQLGrammarParser.Select_stmtContext var1);

   void exitSelect_stmt(SQLGrammarParser.Select_stmtContext var1);

   void enterSelect_or_values(SQLGrammarParser.Select_or_valuesContext var1);

   void exitSelect_or_values(SQLGrammarParser.Select_or_valuesContext var1);

   void enterUpdate_stmt(SQLGrammarParser.Update_stmtContext var1);

   void exitUpdate_stmt(SQLGrammarParser.Update_stmtContext var1);

   void enterUpdate_stmt_limited(SQLGrammarParser.Update_stmt_limitedContext var1);

   void exitUpdate_stmt_limited(SQLGrammarParser.Update_stmt_limitedContext var1);

   void enterVacuum_stmt(SQLGrammarParser.Vacuum_stmtContext var1);

   void exitVacuum_stmt(SQLGrammarParser.Vacuum_stmtContext var1);

   void enterColumn_def(SQLGrammarParser.Column_defContext var1);

   void exitColumn_def(SQLGrammarParser.Column_defContext var1);

   void enterType_name(SQLGrammarParser.Type_nameContext var1);

   void exitType_name(SQLGrammarParser.Type_nameContext var1);

   void enterColumn_constraint(SQLGrammarParser.Column_constraintContext var1);

   void exitColumn_constraint(SQLGrammarParser.Column_constraintContext var1);

   void enterConflict_clause(SQLGrammarParser.Conflict_clauseContext var1);

   void exitConflict_clause(SQLGrammarParser.Conflict_clauseContext var1);

   void enterExpr(SQLGrammarParser.ExprContext var1);

   void exitExpr(SQLGrammarParser.ExprContext var1);

   void enterForeign_key_clause(SQLGrammarParser.Foreign_key_clauseContext var1);

   void exitForeign_key_clause(SQLGrammarParser.Foreign_key_clauseContext var1);

   void enterRaise_function(SQLGrammarParser.Raise_functionContext var1);

   void exitRaise_function(SQLGrammarParser.Raise_functionContext var1);

   void enterIndexed_column(SQLGrammarParser.Indexed_columnContext var1);

   void exitIndexed_column(SQLGrammarParser.Indexed_columnContext var1);

   void enterTable_constraint(SQLGrammarParser.Table_constraintContext var1);

   void exitTable_constraint(SQLGrammarParser.Table_constraintContext var1);

   void enterWith_clause(SQLGrammarParser.With_clauseContext var1);

   void exitWith_clause(SQLGrammarParser.With_clauseContext var1);

   void enterQualified_table_name(SQLGrammarParser.Qualified_table_nameContext var1);

   void exitQualified_table_name(SQLGrammarParser.Qualified_table_nameContext var1);

   void enterOrdering_term(SQLGrammarParser.Ordering_termContext var1);

   void exitOrdering_term(SQLGrammarParser.Ordering_termContext var1);

   void enterPragma_value(SQLGrammarParser.Pragma_valueContext var1);

   void exitPragma_value(SQLGrammarParser.Pragma_valueContext var1);

   void enterCommon_table_expression(SQLGrammarParser.Common_table_expressionContext var1);

   void exitCommon_table_expression(SQLGrammarParser.Common_table_expressionContext var1);

   void enterResult_column(SQLGrammarParser.Result_columnContext var1);

   void exitResult_column(SQLGrammarParser.Result_columnContext var1);

   void enterTable_or_subquery(SQLGrammarParser.Table_or_subqueryContext var1);

   void exitTable_or_subquery(SQLGrammarParser.Table_or_subqueryContext var1);

   void enterJoin_clause(SQLGrammarParser.Join_clauseContext var1);

   void exitJoin_clause(SQLGrammarParser.Join_clauseContext var1);

   void enterJoin_operator(SQLGrammarParser.Join_operatorContext var1);

   void exitJoin_operator(SQLGrammarParser.Join_operatorContext var1);

   void enterJoin_constraint(SQLGrammarParser.Join_constraintContext var1);

   void exitJoin_constraint(SQLGrammarParser.Join_constraintContext var1);

   void enterSelect_core(SQLGrammarParser.Select_coreContext var1);

   void exitSelect_core(SQLGrammarParser.Select_coreContext var1);

   void enterCompound_operator(SQLGrammarParser.Compound_operatorContext var1);

   void exitCompound_operator(SQLGrammarParser.Compound_operatorContext var1);

   void enterCte_table_name(SQLGrammarParser.Cte_table_nameContext var1);

   void exitCte_table_name(SQLGrammarParser.Cte_table_nameContext var1);

   void enterSigned_number(SQLGrammarParser.Signed_numberContext var1);

   void exitSigned_number(SQLGrammarParser.Signed_numberContext var1);

   void enterLiteral_value(SQLGrammarParser.Literal_valueContext var1);

   void exitLiteral_value(SQLGrammarParser.Literal_valueContext var1);

   void enterUnary_operator(SQLGrammarParser.Unary_operatorContext var1);

   void exitUnary_operator(SQLGrammarParser.Unary_operatorContext var1);

   void enterError_message(SQLGrammarParser.Error_messageContext var1);

   void exitError_message(SQLGrammarParser.Error_messageContext var1);

   void enterModule_argument(SQLGrammarParser.Module_argumentContext var1);

   void exitModule_argument(SQLGrammarParser.Module_argumentContext var1);

   void enterColumn_alias(SQLGrammarParser.Column_aliasContext var1);

   void exitColumn_alias(SQLGrammarParser.Column_aliasContext var1);

   void enterKeyword(SQLGrammarParser.KeywordContext var1);

   void exitKeyword(SQLGrammarParser.KeywordContext var1);

   void enterName(SQLGrammarParser.NameContext var1);

   void exitName(SQLGrammarParser.NameContext var1);

   void enterFunction_name(SQLGrammarParser.Function_nameContext var1);

   void exitFunction_name(SQLGrammarParser.Function_nameContext var1);

   void enterDatabase_name(SQLGrammarParser.Database_nameContext var1);

   void exitDatabase_name(SQLGrammarParser.Database_nameContext var1);

   void enterTable_name(SQLGrammarParser.Table_nameContext var1);

   void exitTable_name(SQLGrammarParser.Table_nameContext var1);

   void enterTable_or_index_name(SQLGrammarParser.Table_or_index_nameContext var1);

   void exitTable_or_index_name(SQLGrammarParser.Table_or_index_nameContext var1);

   void enterNew_table_name(SQLGrammarParser.New_table_nameContext var1);

   void exitNew_table_name(SQLGrammarParser.New_table_nameContext var1);

   void enterColumn_name(SQLGrammarParser.Column_nameContext var1);

   void exitColumn_name(SQLGrammarParser.Column_nameContext var1);

   void enterCollation_name(SQLGrammarParser.Collation_nameContext var1);

   void exitCollation_name(SQLGrammarParser.Collation_nameContext var1);

   void enterForeign_table(SQLGrammarParser.Foreign_tableContext var1);

   void exitForeign_table(SQLGrammarParser.Foreign_tableContext var1);

   void enterIndex_name(SQLGrammarParser.Index_nameContext var1);

   void exitIndex_name(SQLGrammarParser.Index_nameContext var1);

   void enterTrigger_name(SQLGrammarParser.Trigger_nameContext var1);

   void exitTrigger_name(SQLGrammarParser.Trigger_nameContext var1);

   void enterView_name(SQLGrammarParser.View_nameContext var1);

   void exitView_name(SQLGrammarParser.View_nameContext var1);

   void enterModule_name(SQLGrammarParser.Module_nameContext var1);

   void exitModule_name(SQLGrammarParser.Module_nameContext var1);

   void enterPragma_name(SQLGrammarParser.Pragma_nameContext var1);

   void exitPragma_name(SQLGrammarParser.Pragma_nameContext var1);

   void enterSavepoint_name(SQLGrammarParser.Savepoint_nameContext var1);

   void exitSavepoint_name(SQLGrammarParser.Savepoint_nameContext var1);

   void enterTable_alias(SQLGrammarParser.Table_aliasContext var1);

   void exitTable_alias(SQLGrammarParser.Table_aliasContext var1);

   void enterTransaction_name(SQLGrammarParser.Transaction_nameContext var1);

   void exitTransaction_name(SQLGrammarParser.Transaction_nameContext var1);

   void enterAny_name(SQLGrammarParser.Any_nameContext var1);

   void exitAny_name(SQLGrammarParser.Any_nameContext var1);
}
