package com.googlecode.cqengine.query.parser.sql.support;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.logical.And;
import com.googlecode.cqengine.query.logical.Or;
import com.googlecode.cqengine.query.option.AttributeOrder;
import com.googlecode.cqengine.query.option.OrderByOption;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.parser.common.ParserUtils;
import com.googlecode.cqengine.query.parser.common.QueryParser;
import com.googlecode.cqengine.query.parser.sql.grammar.SQLGrammarBaseListener;
import com.googlecode.cqengine.query.parser.sql.grammar.SQLGrammarParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class SQLAntlrListener extends SQLGrammarBaseListener {
   protected final QueryParser queryParser;
   protected final Map childQueries = new HashMap();
   protected OrderByOption orderByOption = null;
   protected int numQueriesEncountered = 0;
   protected int numQueriesParsed = 0;

   public SQLAntlrListener(QueryParser queryParser) {
      this.queryParser = queryParser;
   }

   public void exitAndQuery(SQLGrammarParser.AndQueryContext ctx) {
      this.addParsedQuery(ctx, new And((Collection)this.childQueries.get(ctx)));
   }

   public void exitOrQuery(SQLGrammarParser.OrQueryContext ctx) {
      this.addParsedQuery(ctx, new Or((Collection)this.childQueries.get(ctx)));
   }

   public void exitNotQuery(SQLGrammarParser.NotQueryContext ctx) {
      this.addParsedQuery(ctx, QueryFactory.not((Query)((Collection)this.childQueries.get(ctx)).iterator().next()));
   }

   public void exitEqualQuery(SQLGrammarParser.EqualQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Object.class);
      Object value = this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameter());
      this.addParsedQuery(ctx, QueryFactory.equal(attribute, value));
   }

   public void exitNotEqualQuery(SQLGrammarParser.NotEqualQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Object.class);
      Object value = this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameter());
      this.addParsedQuery(ctx, QueryFactory.not(QueryFactory.equal(attribute, value)));
   }

   public void exitLessThanOrEqualToQuery(SQLGrammarParser.LessThanOrEqualToQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      Comparable value = (Comparable)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameter());
      this.addParsedQuery(ctx, QueryFactory.lessThanOrEqualTo(attribute, value));
   }

   public void exitLessThanQuery(SQLGrammarParser.LessThanQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      Comparable value = (Comparable)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameter());
      this.addParsedQuery(ctx, QueryFactory.lessThan(attribute, value));
   }

   public void exitGreaterThanOrEqualToQuery(SQLGrammarParser.GreaterThanOrEqualToQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      Comparable value = (Comparable)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameter());
      this.addParsedQuery(ctx, QueryFactory.greaterThanOrEqualTo(attribute, value));
   }

   public void exitGreaterThanQuery(SQLGrammarParser.GreaterThanQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      Comparable value = (Comparable)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameter());
      this.addParsedQuery(ctx, QueryFactory.greaterThan(attribute, value));
   }

   public void exitBetweenQuery(SQLGrammarParser.BetweenQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      List queryParameters = ctx.queryParameter();
      Comparable lowerValue = (Comparable)this.queryParser.parseValue(attribute, (ParseTree)queryParameters.get(0));
      Comparable upperValue = (Comparable)this.queryParser.parseValue(attribute, (ParseTree)queryParameters.get(1));
      this.addParsedQuery(ctx, QueryFactory.between(attribute, lowerValue, upperValue));
   }

   public void exitNotBetweenQuery(SQLGrammarParser.NotBetweenQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      List queryParameters = ctx.queryParameter();
      Comparable lowerValue = (Comparable)this.queryParser.parseValue(attribute, (ParseTree)queryParameters.get(0));
      Comparable upperValue = (Comparable)this.queryParser.parseValue(attribute, (ParseTree)queryParameters.get(1));
      this.addParsedQuery(ctx, QueryFactory.not(QueryFactory.between(attribute, lowerValue, upperValue)));
   }

   public void exitInQuery(SQLGrammarParser.InQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Object.class);
      List queryParameters = ctx.queryParameter();
      Collection values = new ArrayList(queryParameters.size());
      Iterator var5 = queryParameters.iterator();

      while(var5.hasNext()) {
         ParseTree queryParameter = (ParseTree)var5.next();
         Object value = this.queryParser.parseValue(attribute, queryParameter);
         values.add(value);
      }

      this.addParsedQuery(ctx, QueryFactory.in(attribute, (Collection)values));
   }

   public void exitNotInQuery(SQLGrammarParser.NotInQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Object.class);
      List queryParameters = ctx.queryParameter();
      Collection values = new ArrayList(queryParameters.size());
      Iterator var5 = queryParameters.iterator();

      while(var5.hasNext()) {
         ParseTree queryParameter = (ParseTree)var5.next();
         Object value = this.queryParser.parseValue(attribute, queryParameter);
         values.add(value);
      }

      this.addParsedQuery(ctx, QueryFactory.not(QueryFactory.in(attribute, (Collection)values)));
   }

   public void exitStartsWithQuery(SQLGrammarParser.StartsWithQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), String.class);
      String value = (String)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameterTrailingPercent());
      value = value.substring(0, value.length() - 1);
      this.addParsedQuery(ctx, QueryFactory.startsWith(attribute, value));
   }

   public void exitEndsWithQuery(SQLGrammarParser.EndsWithQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), String.class);
      String value = (String)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameterLeadingPercent());
      value = value.substring(1, value.length());
      this.addParsedQuery(ctx, QueryFactory.endsWith(attribute, value));
   }

   public void exitContainsQuery(SQLGrammarParser.ContainsQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), String.class);
      String value = (String)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameterLeadingAndTrailingPercent());
      value = value.substring(1, value.length() - 1);
      this.addParsedQuery(ctx, QueryFactory.contains(attribute, value));
   }

   public void exitHasQuery(SQLGrammarParser.HasQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Object.class);
      this.addParsedQuery(ctx, QueryFactory.has(attribute));
   }

   public void exitNotHasQuery(SQLGrammarParser.NotHasQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Object.class);
      this.addParsedQuery(ctx, QueryFactory.not(QueryFactory.has(attribute)));
   }

   public void exitQuery(SQLGrammarParser.QueryContext ctx) {
      ++this.numQueriesEncountered;
      ParserUtils.validateAllQueriesParsed(this.numQueriesEncountered, this.numQueriesParsed);
   }

   public void exitOrderByClause(SQLGrammarParser.OrderByClauseContext ctx) {
      List attributeOrders = new ArrayList();
      Iterator var3 = ctx.attributeOrder().iterator();

      while(var3.hasNext()) {
         SQLGrammarParser.AttributeOrderContext orderContext = (SQLGrammarParser.AttributeOrderContext)var3.next();
         Attribute attribute = this.queryParser.getAttribute(orderContext.attributeName(), Comparable.class);
         boolean descending = orderContext.direction() != null && orderContext.direction().K_DESC() != null;
         attributeOrders.add(new AttributeOrder(attribute, descending));
      }

      this.orderByOption = QueryFactory.orderBy((List)attributeOrders);
   }

   void addParsedQuery(ParserRuleContext currentContext, Query parsedQuery) {
      ParserRuleContext parentContext = ParserUtils.getParentContextOfType(currentContext, this.getAndOrNotContextClasses());
      Collection childrenOfParent = (Collection)this.childQueries.get(parentContext);
      if (childrenOfParent == null) {
         childrenOfParent = new ArrayList();
         this.childQueries.put(parentContext, childrenOfParent);
      }

      ((Collection)childrenOfParent).add(parsedQuery);
      ++this.numQueriesParsed;
   }

   public Query getParsedQuery() {
      Collection rootQuery = (Collection)this.childQueries.get((Object)null);
      if (rootQuery == null) {
         return QueryFactory.all(this.queryParser.getObjectType());
      } else {
         ParserUtils.validateExpectedNumberOfChildQueries(1, rootQuery.size());
         return (Query)rootQuery.iterator().next();
      }
   }

   public QueryOptions getQueryOptions() {
      OrderByOption orderByOption = this.orderByOption;
      return orderByOption != null ? QueryFactory.queryOptions((Object)orderByOption) : QueryFactory.noQueryOptions();
   }

   protected Class[] getAndOrNotContextClasses() {
      return new Class[]{SQLGrammarParser.AndQueryContext.class, SQLGrammarParser.OrQueryContext.class, SQLGrammarParser.NotQueryContext.class};
   }
}
