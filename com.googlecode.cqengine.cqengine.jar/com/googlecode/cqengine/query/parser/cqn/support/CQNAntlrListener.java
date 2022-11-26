package com.googlecode.cqengine.query.parser.cqn.support;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.logical.And;
import com.googlecode.cqengine.query.logical.Or;
import com.googlecode.cqengine.query.option.AttributeOrder;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.parser.common.ParserUtils;
import com.googlecode.cqengine.query.parser.common.QueryParser;
import com.googlecode.cqengine.query.parser.cqn.grammar.CQNGrammarBaseListener;
import com.googlecode.cqengine.query.parser.cqn.grammar.CQNGrammarParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class CQNAntlrListener extends CQNGrammarBaseListener {
   protected final QueryParser queryParser;
   protected final Map childQueries = new HashMap();
   protected int numQueriesEncountered = 0;
   protected int numQueriesParsed = 0;
   protected final List attributeOrders = new LinkedList();

   public CQNAntlrListener(QueryParser queryParser) {
      this.queryParser = queryParser;
   }

   public void exitAndQuery(CQNGrammarParser.AndQueryContext ctx) {
      this.addParsedQuery(ctx, new And((Collection)this.childQueries.get(ctx)));
   }

   public void exitOrQuery(CQNGrammarParser.OrQueryContext ctx) {
      this.addParsedQuery(ctx, new Or((Collection)this.childQueries.get(ctx)));
   }

   public void exitNotQuery(CQNGrammarParser.NotQueryContext ctx) {
      this.addParsedQuery(ctx, QueryFactory.not((Query)((Collection)this.childQueries.get(ctx)).iterator().next()));
   }

   public void exitEqualQuery(CQNGrammarParser.EqualQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Object.class);
      Object value = this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameter());
      this.addParsedQuery(ctx, QueryFactory.equal(attribute, value));
   }

   public void exitLessThanOrEqualToQuery(CQNGrammarParser.LessThanOrEqualToQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      Comparable value = (Comparable)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameter());
      this.addParsedQuery(ctx, QueryFactory.lessThanOrEqualTo(attribute, value));
   }

   public void exitLessThanQuery(CQNGrammarParser.LessThanQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      Comparable value = (Comparable)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameter());
      this.addParsedQuery(ctx, QueryFactory.lessThan(attribute, value));
   }

   public void exitGreaterThanOrEqualToQuery(CQNGrammarParser.GreaterThanOrEqualToQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      Comparable value = (Comparable)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameter());
      this.addParsedQuery(ctx, QueryFactory.greaterThanOrEqualTo(attribute, value));
   }

   public void exitGreaterThanQuery(CQNGrammarParser.GreaterThanQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      Comparable value = (Comparable)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.queryParameter());
      this.addParsedQuery(ctx, QueryFactory.greaterThan(attribute, value));
   }

   public void exitVerboseBetweenQuery(CQNGrammarParser.VerboseBetweenQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      List queryParameters = ctx.queryParameter();
      List booleanParameters = ctx.BooleanLiteral();
      Comparable lowerValue = (Comparable)this.queryParser.parseValue(attribute, (ParseTree)queryParameters.get(0));
      boolean lowerInclusive = (Boolean)this.queryParser.parseValue(Boolean.class, (ParseTree)booleanParameters.get(0));
      Comparable upperValue = (Comparable)this.queryParser.parseValue(attribute, (ParseTree)queryParameters.get(1));
      boolean upperInclusive = (Boolean)this.queryParser.parseValue(Boolean.class, (ParseTree)booleanParameters.get(1));
      this.addParsedQuery(ctx, QueryFactory.between(attribute, lowerValue, lowerInclusive, upperValue, upperInclusive));
   }

   public void exitBetweenQuery(CQNGrammarParser.BetweenQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Comparable.class);
      List queryParameters = ctx.queryParameter();
      Comparable lowerValue = (Comparable)this.queryParser.parseValue(attribute, (ParseTree)queryParameters.get(0));
      Comparable upperValue = (Comparable)this.queryParser.parseValue(attribute, (ParseTree)queryParameters.get(1));
      this.addParsedQuery(ctx, QueryFactory.between(attribute, lowerValue, upperValue));
   }

   public void exitInQuery(CQNGrammarParser.InQueryContext ctx) {
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

   public void exitStartsWithQuery(CQNGrammarParser.StartsWithQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), String.class);
      String value = (String)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.stringQueryParameter());
      this.addParsedQuery(ctx, QueryFactory.startsWith(attribute, value));
   }

   public void exitEndsWithQuery(CQNGrammarParser.EndsWithQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), String.class);
      String value = (String)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.stringQueryParameter());
      this.addParsedQuery(ctx, QueryFactory.endsWith(attribute, value));
   }

   public void exitContainsQuery(CQNGrammarParser.ContainsQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), String.class);
      String value = (String)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.stringQueryParameter());
      this.addParsedQuery(ctx, QueryFactory.contains(attribute, value));
   }

   public void exitIsContainedInQuery(CQNGrammarParser.IsContainedInQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), String.class);
      String value = (String)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.stringQueryParameter());
      this.addParsedQuery(ctx, QueryFactory.isContainedIn(attribute, value));
   }

   public void exitMatchesRegexQuery(CQNGrammarParser.MatchesRegexQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), String.class);
      String value = (String)this.queryParser.parseValue((Attribute)attribute, (ParseTree)ctx.stringQueryParameter());
      this.addParsedQuery(ctx, QueryFactory.matchesRegex(attribute, value));
   }

   public void exitHasQuery(CQNGrammarParser.HasQueryContext ctx) {
      Attribute attribute = this.queryParser.getAttribute(ctx.attributeName(), Object.class);
      this.addParsedQuery(ctx, QueryFactory.has(attribute));
   }

   public void exitAllQuery(CQNGrammarParser.AllQueryContext ctx) {
      ParserUtils.validateObjectTypeParameter(this.queryParser.getObjectType(), ctx.objectType().getText());
      this.addParsedQuery(ctx, QueryFactory.all(this.queryParser.getObjectType()));
   }

   public void exitNoneQuery(CQNGrammarParser.NoneQueryContext ctx) {
      ParserUtils.validateObjectTypeParameter(this.queryParser.getObjectType(), ctx.objectType().getText());
      this.addParsedQuery(ctx, QueryFactory.none(this.queryParser.getObjectType()));
   }

   public void exitQuery(CQNGrammarParser.QueryContext ctx) {
      ++this.numQueriesEncountered;
      ParserUtils.validateAllQueriesParsed(this.numQueriesEncountered, this.numQueriesParsed);
   }

   public void exitOrderByOption(CQNGrammarParser.OrderByOptionContext ctx) {
      Iterator var2 = ctx.attributeOrder().iterator();

      while(var2.hasNext()) {
         CQNGrammarParser.AttributeOrderContext attributeOrderContext = (CQNGrammarParser.AttributeOrderContext)var2.next();
         Attribute attribute = this.queryParser.getAttribute(attributeOrderContext.attributeName(), Comparable.class);
         boolean descending = "descending".equals(attributeOrderContext.direction().getText());
         this.attributeOrders.add(new AttributeOrder(attribute, descending));
      }

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
      ParserUtils.validateExpectedNumberOfChildQueries(1, rootQuery.size());
      return (Query)rootQuery.iterator().next();
   }

   public QueryOptions getQueryOptions() {
      return this.attributeOrders.isEmpty() ? QueryFactory.noQueryOptions() : QueryFactory.queryOptions((Object)QueryFactory.orderBy(this.attributeOrders));
   }

   protected Class[] getAndOrNotContextClasses() {
      return new Class[]{CQNGrammarParser.AndQueryContext.class, CQNGrammarParser.OrQueryContext.class, CQNGrammarParser.NotQueryContext.class};
   }
}
