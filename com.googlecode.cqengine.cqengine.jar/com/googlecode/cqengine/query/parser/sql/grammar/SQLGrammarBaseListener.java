package com.googlecode.cqengine.query.parser.sql.grammar;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class SQLGrammarBaseListener implements SQLGrammarListener {
   public void enterStart(SQLGrammarParser.StartContext ctx) {
   }

   public void exitStart(SQLGrammarParser.StartContext ctx) {
   }

   public void enterIndexedCollection(SQLGrammarParser.IndexedCollectionContext ctx) {
   }

   public void exitIndexedCollection(SQLGrammarParser.IndexedCollectionContext ctx) {
   }

   public void enterWhereClause(SQLGrammarParser.WhereClauseContext ctx) {
   }

   public void exitWhereClause(SQLGrammarParser.WhereClauseContext ctx) {
   }

   public void enterOrderByClause(SQLGrammarParser.OrderByClauseContext ctx) {
   }

   public void exitOrderByClause(SQLGrammarParser.OrderByClauseContext ctx) {
   }

   public void enterQuery(SQLGrammarParser.QueryContext ctx) {
   }

   public void exitQuery(SQLGrammarParser.QueryContext ctx) {
   }

   public void enterLogicalQuery(SQLGrammarParser.LogicalQueryContext ctx) {
   }

   public void exitLogicalQuery(SQLGrammarParser.LogicalQueryContext ctx) {
   }

   public void enterAndQuery(SQLGrammarParser.AndQueryContext ctx) {
   }

   public void exitAndQuery(SQLGrammarParser.AndQueryContext ctx) {
   }

   public void enterOrQuery(SQLGrammarParser.OrQueryContext ctx) {
   }

   public void exitOrQuery(SQLGrammarParser.OrQueryContext ctx) {
   }

   public void enterNotQuery(SQLGrammarParser.NotQueryContext ctx) {
   }

   public void exitNotQuery(SQLGrammarParser.NotQueryContext ctx) {
   }

   public void enterSimpleQuery(SQLGrammarParser.SimpleQueryContext ctx) {
   }

   public void exitSimpleQuery(SQLGrammarParser.SimpleQueryContext ctx) {
   }

   public void enterEqualQuery(SQLGrammarParser.EqualQueryContext ctx) {
   }

   public void exitEqualQuery(SQLGrammarParser.EqualQueryContext ctx) {
   }

   public void enterNotEqualQuery(SQLGrammarParser.NotEqualQueryContext ctx) {
   }

   public void exitNotEqualQuery(SQLGrammarParser.NotEqualQueryContext ctx) {
   }

   public void enterLessThanOrEqualToQuery(SQLGrammarParser.LessThanOrEqualToQueryContext ctx) {
   }

   public void exitLessThanOrEqualToQuery(SQLGrammarParser.LessThanOrEqualToQueryContext ctx) {
   }

   public void enterLessThanQuery(SQLGrammarParser.LessThanQueryContext ctx) {
   }

   public void exitLessThanQuery(SQLGrammarParser.LessThanQueryContext ctx) {
   }

   public void enterGreaterThanOrEqualToQuery(SQLGrammarParser.GreaterThanOrEqualToQueryContext ctx) {
   }

   public void exitGreaterThanOrEqualToQuery(SQLGrammarParser.GreaterThanOrEqualToQueryContext ctx) {
   }

   public void enterGreaterThanQuery(SQLGrammarParser.GreaterThanQueryContext ctx) {
   }

   public void exitGreaterThanQuery(SQLGrammarParser.GreaterThanQueryContext ctx) {
   }

   public void enterBetweenQuery(SQLGrammarParser.BetweenQueryContext ctx) {
   }

   public void exitBetweenQuery(SQLGrammarParser.BetweenQueryContext ctx) {
   }

   public void enterNotBetweenQuery(SQLGrammarParser.NotBetweenQueryContext ctx) {
   }

   public void exitNotBetweenQuery(SQLGrammarParser.NotBetweenQueryContext ctx) {
   }

   public void enterInQuery(SQLGrammarParser.InQueryContext ctx) {
   }

   public void exitInQuery(SQLGrammarParser.InQueryContext ctx) {
   }

   public void enterNotInQuery(SQLGrammarParser.NotInQueryContext ctx) {
   }

   public void exitNotInQuery(SQLGrammarParser.NotInQueryContext ctx) {
   }

   public void enterStartsWithQuery(SQLGrammarParser.StartsWithQueryContext ctx) {
   }

   public void exitStartsWithQuery(SQLGrammarParser.StartsWithQueryContext ctx) {
   }

   public void enterEndsWithQuery(SQLGrammarParser.EndsWithQueryContext ctx) {
   }

   public void exitEndsWithQuery(SQLGrammarParser.EndsWithQueryContext ctx) {
   }

   public void enterContainsQuery(SQLGrammarParser.ContainsQueryContext ctx) {
   }

   public void exitContainsQuery(SQLGrammarParser.ContainsQueryContext ctx) {
   }

   public void enterHasQuery(SQLGrammarParser.HasQueryContext ctx) {
   }

   public void exitHasQuery(SQLGrammarParser.HasQueryContext ctx) {
   }

   public void enterNotHasQuery(SQLGrammarParser.NotHasQueryContext ctx) {
   }

   public void exitNotHasQuery(SQLGrammarParser.NotHasQueryContext ctx) {
   }

   public void enterAttributeName(SQLGrammarParser.AttributeNameContext ctx) {
   }

   public void exitAttributeName(SQLGrammarParser.AttributeNameContext ctx) {
   }

   public void enterQueryParameterTrailingPercent(SQLGrammarParser.QueryParameterTrailingPercentContext ctx) {
   }

   public void exitQueryParameterTrailingPercent(SQLGrammarParser.QueryParameterTrailingPercentContext ctx) {
   }

   public void enterQueryParameterLeadingPercent(SQLGrammarParser.QueryParameterLeadingPercentContext ctx) {
   }

   public void exitQueryParameterLeadingPercent(SQLGrammarParser.QueryParameterLeadingPercentContext ctx) {
   }

   public void enterQueryParameterLeadingAndTrailingPercent(SQLGrammarParser.QueryParameterLeadingAndTrailingPercentContext ctx) {
   }

   public void exitQueryParameterLeadingAndTrailingPercent(SQLGrammarParser.QueryParameterLeadingAndTrailingPercentContext ctx) {
   }

   public void enterQueryParameter(SQLGrammarParser.QueryParameterContext ctx) {
   }

   public void exitQueryParameter(SQLGrammarParser.QueryParameterContext ctx) {
   }

   public void enterAttributeOrder(SQLGrammarParser.AttributeOrderContext ctx) {
   }

   public void exitAttributeOrder(SQLGrammarParser.AttributeOrderContext ctx) {
   }

   public void enterDirection(SQLGrammarParser.DirectionContext ctx) {
   }

   public void exitDirection(SQLGrammarParser.DirectionContext ctx) {
   }

   public void enterParse(SQLGrammarParser.ParseContext ctx) {
   }

   public void exitParse(SQLGrammarParser.ParseContext ctx) {
   }

   public void enterError(SQLGrammarParser.ErrorContext ctx) {
   }

   public void exitError(SQLGrammarParser.ErrorContext ctx) {
   }

   public void enterSql_stmt_list(SQLGrammarParser.Sql_stmt_listContext ctx) {
   }

   public void exitSql_stmt_list(SQLGrammarParser.Sql_stmt_listContext ctx) {
   }

   public void enterSql_stmt(SQLGrammarParser.Sql_stmtContext ctx) {
   }

   public void exitSql_stmt(SQLGrammarParser.Sql_stmtContext ctx) {
   }

   public void enterAlter_table_stmt(SQLGrammarParser.Alter_table_stmtContext ctx) {
   }

   public void exitAlter_table_stmt(SQLGrammarParser.Alter_table_stmtContext ctx) {
   }

   public void enterAnalyze_stmt(SQLGrammarParser.Analyze_stmtContext ctx) {
   }

   public void exitAnalyze_stmt(SQLGrammarParser.Analyze_stmtContext ctx) {
   }

   public void enterAttach_stmt(SQLGrammarParser.Attach_stmtContext ctx) {
   }

   public void exitAttach_stmt(SQLGrammarParser.Attach_stmtContext ctx) {
   }

   public void enterBegin_stmt(SQLGrammarParser.Begin_stmtContext ctx) {
   }

   public void exitBegin_stmt(SQLGrammarParser.Begin_stmtContext ctx) {
   }

   public void enterCommit_stmt(SQLGrammarParser.Commit_stmtContext ctx) {
   }

   public void exitCommit_stmt(SQLGrammarParser.Commit_stmtContext ctx) {
   }

   public void enterCompound_select_stmt(SQLGrammarParser.Compound_select_stmtContext ctx) {
   }

   public void exitCompound_select_stmt(SQLGrammarParser.Compound_select_stmtContext ctx) {
   }

   public void enterCreate_index_stmt(SQLGrammarParser.Create_index_stmtContext ctx) {
   }

   public void exitCreate_index_stmt(SQLGrammarParser.Create_index_stmtContext ctx) {
   }

   public void enterCreate_table_stmt(SQLGrammarParser.Create_table_stmtContext ctx) {
   }

   public void exitCreate_table_stmt(SQLGrammarParser.Create_table_stmtContext ctx) {
   }

   public void enterCreate_trigger_stmt(SQLGrammarParser.Create_trigger_stmtContext ctx) {
   }

   public void exitCreate_trigger_stmt(SQLGrammarParser.Create_trigger_stmtContext ctx) {
   }

   public void enterCreate_view_stmt(SQLGrammarParser.Create_view_stmtContext ctx) {
   }

   public void exitCreate_view_stmt(SQLGrammarParser.Create_view_stmtContext ctx) {
   }

   public void enterCreate_virtual_table_stmt(SQLGrammarParser.Create_virtual_table_stmtContext ctx) {
   }

   public void exitCreate_virtual_table_stmt(SQLGrammarParser.Create_virtual_table_stmtContext ctx) {
   }

   public void enterDelete_stmt(SQLGrammarParser.Delete_stmtContext ctx) {
   }

   public void exitDelete_stmt(SQLGrammarParser.Delete_stmtContext ctx) {
   }

   public void enterDelete_stmt_limited(SQLGrammarParser.Delete_stmt_limitedContext ctx) {
   }

   public void exitDelete_stmt_limited(SQLGrammarParser.Delete_stmt_limitedContext ctx) {
   }

   public void enterDetach_stmt(SQLGrammarParser.Detach_stmtContext ctx) {
   }

   public void exitDetach_stmt(SQLGrammarParser.Detach_stmtContext ctx) {
   }

   public void enterDrop_index_stmt(SQLGrammarParser.Drop_index_stmtContext ctx) {
   }

   public void exitDrop_index_stmt(SQLGrammarParser.Drop_index_stmtContext ctx) {
   }

   public void enterDrop_table_stmt(SQLGrammarParser.Drop_table_stmtContext ctx) {
   }

   public void exitDrop_table_stmt(SQLGrammarParser.Drop_table_stmtContext ctx) {
   }

   public void enterDrop_trigger_stmt(SQLGrammarParser.Drop_trigger_stmtContext ctx) {
   }

   public void exitDrop_trigger_stmt(SQLGrammarParser.Drop_trigger_stmtContext ctx) {
   }

   public void enterDrop_view_stmt(SQLGrammarParser.Drop_view_stmtContext ctx) {
   }

   public void exitDrop_view_stmt(SQLGrammarParser.Drop_view_stmtContext ctx) {
   }

   public void enterFactored_select_stmt(SQLGrammarParser.Factored_select_stmtContext ctx) {
   }

   public void exitFactored_select_stmt(SQLGrammarParser.Factored_select_stmtContext ctx) {
   }

   public void enterInsert_stmt(SQLGrammarParser.Insert_stmtContext ctx) {
   }

   public void exitInsert_stmt(SQLGrammarParser.Insert_stmtContext ctx) {
   }

   public void enterPragma_stmt(SQLGrammarParser.Pragma_stmtContext ctx) {
   }

   public void exitPragma_stmt(SQLGrammarParser.Pragma_stmtContext ctx) {
   }

   public void enterReindex_stmt(SQLGrammarParser.Reindex_stmtContext ctx) {
   }

   public void exitReindex_stmt(SQLGrammarParser.Reindex_stmtContext ctx) {
   }

   public void enterRelease_stmt(SQLGrammarParser.Release_stmtContext ctx) {
   }

   public void exitRelease_stmt(SQLGrammarParser.Release_stmtContext ctx) {
   }

   public void enterRollback_stmt(SQLGrammarParser.Rollback_stmtContext ctx) {
   }

   public void exitRollback_stmt(SQLGrammarParser.Rollback_stmtContext ctx) {
   }

   public void enterSavepoint_stmt(SQLGrammarParser.Savepoint_stmtContext ctx) {
   }

   public void exitSavepoint_stmt(SQLGrammarParser.Savepoint_stmtContext ctx) {
   }

   public void enterSimple_select_stmt(SQLGrammarParser.Simple_select_stmtContext ctx) {
   }

   public void exitSimple_select_stmt(SQLGrammarParser.Simple_select_stmtContext ctx) {
   }

   public void enterSelect_stmt(SQLGrammarParser.Select_stmtContext ctx) {
   }

   public void exitSelect_stmt(SQLGrammarParser.Select_stmtContext ctx) {
   }

   public void enterSelect_or_values(SQLGrammarParser.Select_or_valuesContext ctx) {
   }

   public void exitSelect_or_values(SQLGrammarParser.Select_or_valuesContext ctx) {
   }

   public void enterUpdate_stmt(SQLGrammarParser.Update_stmtContext ctx) {
   }

   public void exitUpdate_stmt(SQLGrammarParser.Update_stmtContext ctx) {
   }

   public void enterUpdate_stmt_limited(SQLGrammarParser.Update_stmt_limitedContext ctx) {
   }

   public void exitUpdate_stmt_limited(SQLGrammarParser.Update_stmt_limitedContext ctx) {
   }

   public void enterVacuum_stmt(SQLGrammarParser.Vacuum_stmtContext ctx) {
   }

   public void exitVacuum_stmt(SQLGrammarParser.Vacuum_stmtContext ctx) {
   }

   public void enterColumn_def(SQLGrammarParser.Column_defContext ctx) {
   }

   public void exitColumn_def(SQLGrammarParser.Column_defContext ctx) {
   }

   public void enterType_name(SQLGrammarParser.Type_nameContext ctx) {
   }

   public void exitType_name(SQLGrammarParser.Type_nameContext ctx) {
   }

   public void enterColumn_constraint(SQLGrammarParser.Column_constraintContext ctx) {
   }

   public void exitColumn_constraint(SQLGrammarParser.Column_constraintContext ctx) {
   }

   public void enterConflict_clause(SQLGrammarParser.Conflict_clauseContext ctx) {
   }

   public void exitConflict_clause(SQLGrammarParser.Conflict_clauseContext ctx) {
   }

   public void enterExpr(SQLGrammarParser.ExprContext ctx) {
   }

   public void exitExpr(SQLGrammarParser.ExprContext ctx) {
   }

   public void enterForeign_key_clause(SQLGrammarParser.Foreign_key_clauseContext ctx) {
   }

   public void exitForeign_key_clause(SQLGrammarParser.Foreign_key_clauseContext ctx) {
   }

   public void enterRaise_function(SQLGrammarParser.Raise_functionContext ctx) {
   }

   public void exitRaise_function(SQLGrammarParser.Raise_functionContext ctx) {
   }

   public void enterIndexed_column(SQLGrammarParser.Indexed_columnContext ctx) {
   }

   public void exitIndexed_column(SQLGrammarParser.Indexed_columnContext ctx) {
   }

   public void enterTable_constraint(SQLGrammarParser.Table_constraintContext ctx) {
   }

   public void exitTable_constraint(SQLGrammarParser.Table_constraintContext ctx) {
   }

   public void enterWith_clause(SQLGrammarParser.With_clauseContext ctx) {
   }

   public void exitWith_clause(SQLGrammarParser.With_clauseContext ctx) {
   }

   public void enterQualified_table_name(SQLGrammarParser.Qualified_table_nameContext ctx) {
   }

   public void exitQualified_table_name(SQLGrammarParser.Qualified_table_nameContext ctx) {
   }

   public void enterOrdering_term(SQLGrammarParser.Ordering_termContext ctx) {
   }

   public void exitOrdering_term(SQLGrammarParser.Ordering_termContext ctx) {
   }

   public void enterPragma_value(SQLGrammarParser.Pragma_valueContext ctx) {
   }

   public void exitPragma_value(SQLGrammarParser.Pragma_valueContext ctx) {
   }

   public void enterCommon_table_expression(SQLGrammarParser.Common_table_expressionContext ctx) {
   }

   public void exitCommon_table_expression(SQLGrammarParser.Common_table_expressionContext ctx) {
   }

   public void enterResult_column(SQLGrammarParser.Result_columnContext ctx) {
   }

   public void exitResult_column(SQLGrammarParser.Result_columnContext ctx) {
   }

   public void enterTable_or_subquery(SQLGrammarParser.Table_or_subqueryContext ctx) {
   }

   public void exitTable_or_subquery(SQLGrammarParser.Table_or_subqueryContext ctx) {
   }

   public void enterJoin_clause(SQLGrammarParser.Join_clauseContext ctx) {
   }

   public void exitJoin_clause(SQLGrammarParser.Join_clauseContext ctx) {
   }

   public void enterJoin_operator(SQLGrammarParser.Join_operatorContext ctx) {
   }

   public void exitJoin_operator(SQLGrammarParser.Join_operatorContext ctx) {
   }

   public void enterJoin_constraint(SQLGrammarParser.Join_constraintContext ctx) {
   }

   public void exitJoin_constraint(SQLGrammarParser.Join_constraintContext ctx) {
   }

   public void enterSelect_core(SQLGrammarParser.Select_coreContext ctx) {
   }

   public void exitSelect_core(SQLGrammarParser.Select_coreContext ctx) {
   }

   public void enterCompound_operator(SQLGrammarParser.Compound_operatorContext ctx) {
   }

   public void exitCompound_operator(SQLGrammarParser.Compound_operatorContext ctx) {
   }

   public void enterCte_table_name(SQLGrammarParser.Cte_table_nameContext ctx) {
   }

   public void exitCte_table_name(SQLGrammarParser.Cte_table_nameContext ctx) {
   }

   public void enterSigned_number(SQLGrammarParser.Signed_numberContext ctx) {
   }

   public void exitSigned_number(SQLGrammarParser.Signed_numberContext ctx) {
   }

   public void enterLiteral_value(SQLGrammarParser.Literal_valueContext ctx) {
   }

   public void exitLiteral_value(SQLGrammarParser.Literal_valueContext ctx) {
   }

   public void enterUnary_operator(SQLGrammarParser.Unary_operatorContext ctx) {
   }

   public void exitUnary_operator(SQLGrammarParser.Unary_operatorContext ctx) {
   }

   public void enterError_message(SQLGrammarParser.Error_messageContext ctx) {
   }

   public void exitError_message(SQLGrammarParser.Error_messageContext ctx) {
   }

   public void enterModule_argument(SQLGrammarParser.Module_argumentContext ctx) {
   }

   public void exitModule_argument(SQLGrammarParser.Module_argumentContext ctx) {
   }

   public void enterColumn_alias(SQLGrammarParser.Column_aliasContext ctx) {
   }

   public void exitColumn_alias(SQLGrammarParser.Column_aliasContext ctx) {
   }

   public void enterKeyword(SQLGrammarParser.KeywordContext ctx) {
   }

   public void exitKeyword(SQLGrammarParser.KeywordContext ctx) {
   }

   public void enterName(SQLGrammarParser.NameContext ctx) {
   }

   public void exitName(SQLGrammarParser.NameContext ctx) {
   }

   public void enterFunction_name(SQLGrammarParser.Function_nameContext ctx) {
   }

   public void exitFunction_name(SQLGrammarParser.Function_nameContext ctx) {
   }

   public void enterDatabase_name(SQLGrammarParser.Database_nameContext ctx) {
   }

   public void exitDatabase_name(SQLGrammarParser.Database_nameContext ctx) {
   }

   public void enterTable_name(SQLGrammarParser.Table_nameContext ctx) {
   }

   public void exitTable_name(SQLGrammarParser.Table_nameContext ctx) {
   }

   public void enterTable_or_index_name(SQLGrammarParser.Table_or_index_nameContext ctx) {
   }

   public void exitTable_or_index_name(SQLGrammarParser.Table_or_index_nameContext ctx) {
   }

   public void enterNew_table_name(SQLGrammarParser.New_table_nameContext ctx) {
   }

   public void exitNew_table_name(SQLGrammarParser.New_table_nameContext ctx) {
   }

   public void enterColumn_name(SQLGrammarParser.Column_nameContext ctx) {
   }

   public void exitColumn_name(SQLGrammarParser.Column_nameContext ctx) {
   }

   public void enterCollation_name(SQLGrammarParser.Collation_nameContext ctx) {
   }

   public void exitCollation_name(SQLGrammarParser.Collation_nameContext ctx) {
   }

   public void enterForeign_table(SQLGrammarParser.Foreign_tableContext ctx) {
   }

   public void exitForeign_table(SQLGrammarParser.Foreign_tableContext ctx) {
   }

   public void enterIndex_name(SQLGrammarParser.Index_nameContext ctx) {
   }

   public void exitIndex_name(SQLGrammarParser.Index_nameContext ctx) {
   }

   public void enterTrigger_name(SQLGrammarParser.Trigger_nameContext ctx) {
   }

   public void exitTrigger_name(SQLGrammarParser.Trigger_nameContext ctx) {
   }

   public void enterView_name(SQLGrammarParser.View_nameContext ctx) {
   }

   public void exitView_name(SQLGrammarParser.View_nameContext ctx) {
   }

   public void enterModule_name(SQLGrammarParser.Module_nameContext ctx) {
   }

   public void exitModule_name(SQLGrammarParser.Module_nameContext ctx) {
   }

   public void enterPragma_name(SQLGrammarParser.Pragma_nameContext ctx) {
   }

   public void exitPragma_name(SQLGrammarParser.Pragma_nameContext ctx) {
   }

   public void enterSavepoint_name(SQLGrammarParser.Savepoint_nameContext ctx) {
   }

   public void exitSavepoint_name(SQLGrammarParser.Savepoint_nameContext ctx) {
   }

   public void enterTable_alias(SQLGrammarParser.Table_aliasContext ctx) {
   }

   public void exitTable_alias(SQLGrammarParser.Table_aliasContext ctx) {
   }

   public void enterTransaction_name(SQLGrammarParser.Transaction_nameContext ctx) {
   }

   public void exitTransaction_name(SQLGrammarParser.Transaction_nameContext ctx) {
   }

   public void enterAny_name(SQLGrammarParser.Any_nameContext ctx) {
   }

   public void exitAny_name(SQLGrammarParser.Any_nameContext ctx) {
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
