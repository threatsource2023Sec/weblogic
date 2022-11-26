package org.apache.openjpa.kernel.jpql;

import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.kernel.ExpressionStoreQuery;
import org.apache.openjpa.kernel.QueryContext;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.kernel.exps.AbstractExpressionBuilder;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionFactory;
import org.apache.openjpa.kernel.exps.Literal;
import org.apache.openjpa.kernel.exps.Parameter;
import org.apache.openjpa.kernel.exps.Path;
import org.apache.openjpa.kernel.exps.QueryExpressions;
import org.apache.openjpa.kernel.exps.Subquery;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;

public class JPQLExpressionBuilder extends AbstractExpressionBuilder implements JPQLTreeConstants {
   private static final int VAR_PATH = 1;
   private static final int VAR_ERROR = 2;
   private static final Localizer _loc = Localizer.forPackage(JPQLExpressionBuilder.class);
   private final Stack contexts = new Stack();
   private LinkedMap parameterTypes;
   private int aliasCount = 0;

   public JPQLExpressionBuilder(ExpressionFactory factory, ExpressionStoreQuery query, Object parsedQuery) {
      super(factory, query.getResolver());
      this.contexts.push(new Context(parsedQuery instanceof ParsedJPQL ? (ParsedJPQL)parsedQuery : (parsedQuery instanceof String ? this.getParsedQuery((String)parsedQuery) : null), (Subquery)null));
      if (this.ctx().parsed == null) {
         throw new InternalException(parsedQuery + "");
      }
   }

   protected Localizer getLocalizer() {
      return _loc;
   }

   protected ClassLoader getClassLoader() {
      return this.getClass().getClassLoader();
   }

   protected ParsedJPQL getParsedQuery() {
      return this.ctx().parsed;
   }

   protected ParsedJPQL getParsedQuery(String jpql) {
      return new ParsedJPQL(jpql);
   }

   private void setCandidate(ClassMetaData cmd, String schemaAlias) {
      this.addAccessPath(cmd);
      if (cmd != null) {
         this.ctx().meta = cmd;
      }

      if (schemaAlias != null) {
         this.ctx().schemaAlias = schemaAlias;
      }

   }

   private String nextAlias() {
      return "jpqlalias" + ++this.aliasCount;
   }

   protected ClassMetaData resolveClassMetaData(JPQLNode node) {
      String schemaName = this.assertSchemaName(node);
      ClassMetaData cmd = this.getClassMetaData(schemaName, false);
      if (cmd != null) {
         return cmd;
      } else if (this.isPath(node)) {
         Path path = this.getPath(node);
         return getFieldType(path.last());
      } else {
         return this.getClassMetaData(schemaName, true);
      }
   }

   private ClassMetaData getClassMetaData(String alias, boolean assertValid) {
      ClassLoader loader = this.getClassLoader();
      MetaDataRepository repos = this.resolver.getConfiguration().getMetaDataRepositoryInstance();
      ClassMetaData cmd = repos.getMetaData(alias, loader, false);
      if (cmd != null) {
         return cmd;
      } else {
         Class c = this.resolver.classForName(alias, (String[])null);
         if (c != null) {
            cmd = repos.getMetaData(c, loader, assertValid);
         } else if (assertValid) {
            cmd = repos.getMetaData(alias, loader, false);
         }

         if (cmd == null && assertValid) {
            String close = repos.getClosestAliasName(alias);
            if (close != null) {
               throw this.parseException(0, "not-schema-name-hint", new Object[]{alias, close, repos.getAliasNames()}, (Exception)null);
            } else {
               throw this.parseException(0, "not-schema-name", new Object[]{alias, repos.getAliasNames()}, (Exception)null);
            }
         } else {
            return cmd;
         }
      }
   }

   private Class getCandidateType() {
      return this.getCandidateMetaData().getDescribedType();
   }

   private ClassMetaData getCandidateMetaData() {
      if (this.ctx().meta != null) {
         return this.ctx().meta;
      } else {
         ClassMetaData cls = this.getCandidateMetaData(this.root());
         if (cls == null) {
            throw this.parseException(0, "not-schema-name", new Object[]{this.root()}, (Exception)null);
         } else {
            this.setCandidate(cls, (String)null);
            return cls;
         }
      }
   }

   protected ClassMetaData getCandidateMetaData(JPQLNode node) {
      JPQLNode from = node.findChildByID(5, true);
      if (from == null) {
         if (node.id != 33) {
            throw this.parseException(0, "no-from-clause", (Object[])null, (Exception)null);
         }

         from = node.findChildByID(4, true);
      }

      for(int i = 0; i < from.children.length; ++i) {
         JPQLNode n = from.children[i];
         if (n.id == 79) {
            ClassMetaData cmd = this.resolveClassMetaData(n);
            if (cmd != null) {
               return cmd;
            }

            String cls = this.assertSchemaName(n);
            if (cls == null) {
               throw this.parseException(0, "not-schema-name", new Object[]{this.root()}, (Exception)null);
            }

            return this.getClassMetaData(cls, true);
         }

         if (node.id == 33) {
            if (n.id == 6) {
               n = n.getChild(0);
            }

            if (n.id == 10) {
               Path path = this.getPath(n);
               ClassMetaData cmd = getFieldType(path.last());
               if (cmd != null) {
                  return cmd;
               }

               throw this.parseException(0, "no-alias", new Object[]{n}, (Exception)null);
            }
         }
      }

      return null;
   }

   protected String currentQuery() {
      return this.ctx().parsed != null && this.root().parser != null ? this.root().parser.jpql : null;
   }

   QueryExpressions getQueryExpressions() {
      QueryExpressions exps = new QueryExpressions();
      this.evalQueryOperation(exps);
      Expression filter = null;
      filter = this.and(this.evalFromClause(this.root().id == 1), filter);
      filter = this.and(this.evalWhereClause(), filter);
      filter = this.and(this.evalSelectClause(exps), filter);
      exps.filter = filter == null ? this.factory.emptyExpression() : filter;
      this.evalGroupingClause(exps);
      this.evalHavingClause(exps);
      this.evalFetchJoins(exps);
      this.evalSetClause(exps);
      this.evalOrderingClauses(exps);
      if (this.parameterTypes != null) {
         exps.parameterTypes = this.parameterTypes;
      }

      exps.accessPath = this.getAccessPath();
      return exps;
   }

   private Expression and(Expression e1, Expression e2) {
      return e1 == null ? e2 : (e2 == null ? e1 : this.factory.and(e1, e2));
   }

   private static String assemble(JPQLNode node) {
      return assemble(node, ".", 0);
   }

   private static String assemble(JPQLNode node, String delimiter, int last) {
      StringBuffer result = new StringBuffer();
      JPQLNode[] parts = node.children;

      for(int i = 0; parts != null && i < parts.length - last; ++i) {
         result.append(result.length() > 0 ? delimiter : "").append(parts[i].text);
      }

      return result.toString();
   }

   private Expression assignProjections(JPQLNode parametersNode, QueryExpressions exps) {
      int count = parametersNode.getChildCount();
      exps.projections = new Value[count];
      exps.projectionClauses = new String[count];
      exps.projectionAliases = new String[count];
      Expression exp = null;

      for(int i = 0; i < count; ++i) {
         JPQLNode parent = parametersNode.getChild(i);
         JPQLNode node = this.onlyChild(parent);
         Value proj = this.getValue(node);
         exps.projections[i] = proj;
         exps.projectionClauses[i] = assemble(node);
         exps.projectionAliases[i] = this.nextAlias();
      }

      return (Expression)exp;
   }

   private void evalQueryOperation(QueryExpressions exps) {
      if (this.root().id != 1 && this.root().id != 33) {
         if (this.root().id == 3) {
            exps.operation = 2;
         } else {
            if (this.root().id != 2) {
               throw this.parseException(2, "unrecognized-operation", new Object[]{this.root()}, (Exception)null);
            }

            exps.operation = 3;
         }
      } else {
         exps.operation = 1;
      }

   }

   private void evalGroupingClause(QueryExpressions exps) {
      JPQLNode groupByNode = this.root().findChildByID(30, true);
      if (groupByNode != null) {
         int groupByCount = groupByNode.getChildCount();
         exps.grouping = new Value[groupByCount];

         for(int i = 0; i < groupByCount; ++i) {
            JPQLNode node = groupByNode.getChild(i);
            exps.grouping[i] = this.getValue(node);
         }

      }
   }

   private void evalHavingClause(QueryExpressions exps) {
      JPQLNode havingNode = this.root().findChildByID(32, true);
      if (havingNode != null) {
         exps.having = this.getExpression(this.onlyChild(havingNode));
      }
   }

   private void evalOrderingClauses(QueryExpressions exps) {
      JPQLNode orderby = this.root().findChildByID(74, false);
      if (orderby != null) {
         int ordercount = orderby.getChildCount();
         exps.ordering = new Value[ordercount];
         exps.orderingClauses = new String[ordercount];
         exps.ascending = new boolean[ordercount];

         for(int i = 0; i < ordercount; ++i) {
            JPQLNode node = orderby.getChild(i);
            exps.ordering[i] = this.getValue(this.firstChild(node));
            exps.orderingClauses[i] = assemble(this.firstChild(node));
            exps.ascending[i] = node.getChildCount() <= 1 || lastChild(node).id == 76;
         }
      }

   }

   private Expression evalSelectClause(QueryExpressions exps) {
      if (exps.operation != 1) {
         return null;
      } else {
         JPQLNode selectNode = this.root();
         JPQLNode selectClause = selectNode.findChildByID(13, false);
         if (selectClause != null && selectClause.hasChildID(22)) {
            exps.distinct = 4 | 2;
         } else {
            exps.distinct = 8;
         }

         JPQLNode constructor = selectNode.findChildByID(17, true);
         if (constructor != null) {
            String resultClassName = assemble(this.left(constructor));
            exps.resultClass = this.resolver.classForName(resultClassName, (String[])null);
            return this.assignProjections(this.right(constructor), exps);
         } else {
            JPQLNode expNode = selectNode.findChildByID(14, true);
            if (expNode == null) {
               return null;
            } else {
               int selectCount = expNode.getChildCount();
               JPQLNode selectChild = this.firstChild(expNode);
               if (selectCount == 1 && selectChild != null && selectChild.getChildCount() == 1 && this.onlyChild(selectChild) != null && this.assertSchemaAlias().equalsIgnoreCase(this.onlyChild(selectChild).text)) {
                  return null;
               } else {
                  exps.distinct &= ~2;
                  return this.assignProjections(expNode, exps);
               }
            }
         }
      }
   }

   private String assertSchemaAlias() {
      String alias = this.ctx().schemaAlias;
      if (alias == null) {
         throw this.parseException(0, "alias-required", new Object[]{this.ctx().meta}, (Exception)null);
      } else {
         return alias;
      }
   }

   protected Expression evalFetchJoins(QueryExpressions exps) {
      Expression filter = null;
      Set joins = null;
      Set innerJoins = null;
      JPQLNode[] outers = this.root().findChildrenByID(8);

      for(int i = 0; outers != null && i < outers.length; ++i) {
         (joins == null ? (joins = new TreeSet()) : joins).add(this.getPath(this.onlyChild(outers[i])).last().getFullName(false));
      }

      JPQLNode[] inners = this.root().findChildrenByID(9);

      for(int i = 0; inners != null && i < inners.length; ++i) {
         String path = this.getPath(this.onlyChild(inners[i])).last().getFullName(false);
         (joins == null ? (joins = new TreeSet()) : joins).add(path);
         (innerJoins == null ? (innerJoins = new TreeSet()) : innerJoins).add(path);
      }

      if (joins != null) {
         exps.fetchPaths = (String[])((String[])joins.toArray(new String[joins.size()]));
      }

      if (innerJoins != null) {
         exps.fetchInnerPaths = (String[])((String[])innerJoins.toArray(new String[innerJoins.size()]));
      }

      return (Expression)filter;
   }

   protected void evalSetClause(QueryExpressions exps) {
      JPQLNode[] nodes = this.root().findChildrenByID(11);

      for(int i = 0; nodes != null && i < nodes.length; ++i) {
         Path path = this.getPath(this.firstChild(nodes[i]));
         Value val = this.getValue(this.onlyChild(lastChild(nodes[i])));
         exps.putUpdate(path, val);
      }

   }

   private Expression evalWhereClause() {
      JPQLNode whereNode = this.root().findChildByID(29, false);
      return whereNode == null ? null : (Expression)this.eval(whereNode);
   }

   private Expression evalFromClause(boolean needsAlias) {
      Expression exp = null;
      JPQLNode from = this.root().findChildByID(4, false);
      if (from == null) {
         throw this.parseException(0, "no-from-clause", (Object[])null, (Exception)null);
      } else {
         for(int i = 0; i < from.children.length; ++i) {
            JPQLNode node = from.children[i];
            if (node.id == 5) {
               exp = this.evalFromItem(exp, node, needsAlias);
            } else if (node.id == 7) {
               exp = this.addJoin(node, false, exp);
            } else if (node.id == 6) {
               exp = this.addJoin(node, true, exp);
            } else if (node.id != 9 && node.id != 8) {
               throw this.parseException(0, "not-schema-name", new Object[]{node}, (Exception)null);
            }
         }

         return exp;
      }
   }

   private Expression addJoin(JPQLNode node, boolean inner, Expression exp) {
      Path path = this.getPath(this.firstChild(node), false, inner);
      JPQLNode alias = node.getChildCount() >= 2 ? this.right(node) : null;
      if (inner && this.ctx().subquery != null && this.ctx().schemaAlias == null) {
         this.setCandidate(getFieldType(path.last()), alias.text);
         Path subpath = this.factory.newPath(this.ctx().subquery);
         subpath.setMetaData(this.ctx().subquery.getMetaData());
         exp = this.and(exp, this.factory.equal(path, subpath));
      }

      return this.addJoin(path, alias, exp);
   }

   private Expression addJoin(Path path, JPQLNode aliasNode, Expression exp) {
      FieldMetaData fmd = path.last();
      if (fmd == null) {
         throw this.parseException(0, "path-no-meta", new Object[]{path, null}, (Exception)null);
      } else {
         String alias = aliasNode != null ? aliasNode.text : this.nextAlias();
         Value var = this.getVariable(alias, true);
         var.setMetaData(getFieldType(fmd));
         Expression join = null;
         boolean bound = this.isBound(var);
         if (bound) {
            var = this.getValue(aliasNode, 1);
         } else {
            this.bind(var);
            join = this.and(join, this.factory.bindVariable(var, path));
         }

         if (!fmd.isTypePC()) {
            if (bound) {
               join = this.and(join, this.factory.contains(path, var));
            }

            this.setImplicitContainsTypes(path, var, 1);
         }

         return this.and(exp, join);
      }
   }

   private Expression evalFromItem(Expression exp, JPQLNode node, boolean needsAlias) {
      ClassMetaData cmd = this.resolveClassMetaData(this.firstChild(node));
      String alias = null;
      if (node.getChildCount() < 2) {
         if (needsAlias) {
            throw this.parseException(0, "alias-required", new Object[]{cmd}, (Exception)null);
         }
      } else {
         alias = this.right(node).text;
         JPQLNode left = this.left(node);
         if (this.isPath(left)) {
            Path path = this.getPath(left);
            this.setCandidate(getFieldType(path.last()), alias);
            Path subpath = this.factory.newPath(this.ctx().subquery);
            subpath.setMetaData(this.ctx().subquery.getMetaData());
            return this.and(exp, this.factory.equal(path, subpath));
         }

         Value var = this.getVariable(alias, true);
         var.setMetaData(cmd);
         this.bind(var);
      }

      if (this.ctx().schemaAlias == null) {
         this.setCandidate(cmd, alias);
      }

      return exp;
   }

   protected boolean isDeclaredVariable(String name) {
      return false;
   }

   boolean isPath(JPQLNode node) {
      if (node.getChildCount() < 2) {
         return false;
      } else {
         String name = this.firstChild(node).text;
         if (name == null) {
            return false;
         } else if (this.getMetaDataForAlias(name) != null) {
            return true;
         } else if (!this.isSeenVariable(name)) {
            return false;
         } else {
            Value var = this.getVariable(name, false);
            return var != null ? this.isBound(var) : false;
         }
      }
   }

   private static ClassMetaData getFieldType(FieldMetaData fmd) {
      if (fmd == null) {
         return null;
      } else {
         ClassMetaData cmd = null;
         ValueMetaData vmd;
         if ((vmd = fmd.getElement()) != null) {
            cmd = vmd.getDeclaredTypeMetaData();
         } else if ((vmd = fmd.getKey()) != null) {
            cmd = vmd.getDeclaredTypeMetaData();
         } else if ((vmd = fmd.getValue()) != null) {
            cmd = vmd.getDeclaredTypeMetaData();
         }

         if (cmd == null || cmd.getDescribedType() == Object.class) {
            cmd = fmd.getDeclaredTypeMetaData();
         }

         return cmd;
      }
   }

   protected Value getVariable(String id, boolean bind) {
      return id == null ? null : super.getVariable(id.toLowerCase(), bind);
   }

   protected boolean isSeendVariable(String id) {
      return id != null && super.isSeenVariable(id.toLowerCase());
   }

   private String assertSchemaName(JPQLNode node) {
      if (node.id != 79) {
         throw this.parseException(0, "not-identifer", new Object[]{node}, (Exception)null);
      } else {
         return assemble(node);
      }
   }

   private Object eval(JPQLNode node) {
      Value val1 = null;
      Value val2 = null;
      Value val3 = null;
      boolean not = node.not;
      switch (node.id) {
         case 10:
            return this.getPathOrConstant(node);
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 17:
         case 18:
         case 19:
         case 20:
         case 22:
         case 30:
         case 32:
         case 56:
         case 62:
         case 63:
         case 64:
         case 74:
         case 75:
         case 76:
         case 77:
         case 79:
         case 80:
         default:
            throw this.parseException(1, "bad-tree", new Object[]{node}, (Exception)null);
         case 16:
            this.assertQueryExtensions("SELECT");
            return this.eval(this.onlyChild(node));
         case 21:
            return this.eval(this.onlyChild(node));
         case 23:
            return this.factory.distinct(this.getValue(this.onlyChild(node)));
         case 24:
            return this.factory.count(this.getValue(lastChild(node)));
         case 25:
            return this.factory.avg(this.getNumberValue(this.onlyChild(node)));
         case 26:
            return this.factory.max(this.getNumberValue(this.onlyChild(node)));
         case 27:
            return this.factory.min(this.getNumberValue(this.onlyChild(node)));
         case 28:
            return this.factory.sum(this.getNumberValue(this.onlyChild(node)));
         case 29:
            return this.getExpression(this.onlyChild(node));
         case 31:
            this.assertQueryExtensions("GROUP BY");
            return this.eval(this.onlyChild(node));
         case 33:
            return this.getSubquery(node);
         case 34:
            return this.factory.or(this.getExpression(this.left(node)), this.getExpression(this.right(node)));
         case 35:
            return this.and(this.getExpression(this.left(node)), this.getExpression(this.right(node)));
         case 36:
            return this.factory.not(this.getExpression(this.onlyChild(node)));
         case 37:
            val1 = this.getValue(this.child(node, 0, 3));
            val2 = this.getValue(this.child(node, 1, 3));
            val3 = this.getValue(this.child(node, 2, 3));
            this.setImplicitTypes(val1, val2, (Class)null);
            this.setImplicitTypes(val1, val3, (Class)null);
            return this.evalNot(not, this.and(this.factory.greaterThanEqual(val1, val2), this.factory.lessThanEqual(val1, val3)));
         case 38:
            Expression inExp = null;
            Iterator inIterator = node.iterator();
            val1 = this.getValue((JPQLNode)inIterator.next());

            while(inIterator.hasNext()) {
               val2 = this.getValue((JPQLNode)inIterator.next());
               if (!(val2 instanceof Literal) && node.getChildCount() == 2) {
                  return this.evalNot(not, this.factory.contains(val2, val1));
               }

               this.setImplicitTypes(val1, val2, (Class)null);
               if (inExp == null) {
                  inExp = this.factory.equal(val1, val2);
               } else {
                  inExp = this.factory.or(inExp, this.factory.equal(val1, val2));
               }
            }

            return this.and(this.evalNot(not, inExp), this.factory.notEqual(val1, this.factory.getNull()));
         case 39:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            setImplicitType(val1, TYPE_STRING);
            setImplicitType(val2, TYPE_STRING);
            String escape = null;
            JPQLNode escapeNode = this.right(node).findChildByID(90, true);
            if (escapeNode != null) {
               escape = this.trimQuotes(this.onlyChild(escapeNode).text);
            }

            if (not) {
               return this.factory.notMatches(val1, val2, "_", "%", escape);
            }

            return this.factory.matches(val1, val2, "_", "%", escape);
         case 40:
            if (not) {
               return this.factory.notEqual(this.getValue(this.onlyChild(node)), this.factory.getNull());
            }

            return this.factory.equal(this.getValue(this.onlyChild(node)), this.factory.getNull());
         case 41:
            return this.evalNot(not, this.factory.isEmpty(this.getValue(this.onlyChild(node))));
         case 42:
            val1 = this.getValue(this.left(node), 1);
            val2 = this.getValue(this.right(node), 1);
            this.setImplicitContainsTypes(val2, val1, 1);
            return this.evalNot(not, this.factory.contains(val2, val1));
         case 43:
            return this.factory.isNotEmpty((Value)this.eval(this.onlyChild(node)));
         case 44:
            return this.factory.any((Value)this.eval(this.onlyChild(node)));
         case 45:
            return this.factory.all((Value)this.eval(this.onlyChild(node)));
         case 46:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.equal(val1, val2);
         case 47:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.notEqual(val1, val2);
         case 48:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.greaterThan(val1, val2);
         case 49:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.greaterThanEqual(val1, val2);
         case 50:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.lessThan(val1, val2);
         case 51:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.lessThanEqual(val1, val2);
         case 52:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            this.setImplicitTypes(val1, val2, TYPE_NUMBER);
            return this.factory.add(val1, val2);
         case 53:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            this.setImplicitTypes(val1, val2, TYPE_NUMBER);
            return this.factory.subtract(val1, val2);
         case 54:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            this.setImplicitTypes(val1, val2, TYPE_NUMBER);
            return this.factory.multiply(val1, val2);
         case 55:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            this.setImplicitTypes(val1, val2, TYPE_NUMBER);
            return this.factory.divide(val1, val2);
         case 57:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            setImplicitType(val1, TYPE_STRING);
            setImplicitType(val2, TYPE_STRING);
            return this.factory.concat(val1, val2);
         case 58:
            val1 = this.getValue(this.child(node, 0, 3));
            val2 = this.getValue(this.child(node, 1, 3));
            val3 = this.getValue(this.child(node, 2, 3));
            setImplicitType(val1, TYPE_STRING);
            setImplicitType(val2, Integer.TYPE);
            setImplicitType(val3, Integer.TYPE);
            Object start;
            Object end;
            if (val2 instanceof Literal && val3 instanceof Literal) {
               long jpqlStart = ((Number)((Literal)val2).getValue()).longValue();
               long length = ((Number)((Literal)val3).getValue()).longValue();
               start = this.factory.newLiteral(new Long(jpqlStart - 1L), 1);
               long endIndex = length + (jpqlStart - 1L);
               end = this.factory.newLiteral(new Long(endIndex), 1);
            } else {
               start = this.factory.subtract(val2, this.factory.newLiteral(Numbers.valueOf(1), 1));
               end = this.factory.add(val3, this.factory.subtract(val2, this.factory.newLiteral(Numbers.valueOf(1), 1)));
            }

            return this.factory.substring(val1, this.factory.newArgumentList((Value)start, (Value)end));
         case 59:
            val1 = this.getValue(lastChild(node));
            setImplicitType(val1, TYPE_STRING);
            Boolean trimWhere = null;
            JPQLNode firstTrimChild = this.firstChild(node);
            if (node.getChildCount() > 1) {
               trimWhere = firstTrimChild.id == 62 ? Boolean.TRUE : (firstTrimChild.id == 63 ? Boolean.FALSE : null);
            }

            Object trimChar;
            if (node.getChildCount() == 3) {
               trimChar = this.getValue(secondChild(node));
            } else if (node.getChildCount() == 2 && firstTrimChild.id != 62 && firstTrimChild.id != 63 && firstTrimChild.id != 64) {
               trimChar = this.getValue(this.firstChild(node));
            } else {
               trimChar = this.factory.newLiteral(" ", 3);
            }

            return this.factory.trim(val1, (Value)trimChar, trimWhere);
         case 60:
            return this.factory.toLowerCase(this.getStringValue(this.onlyChild(node)));
         case 61:
            val1 = this.getValue(this.onlyChild(node));
            setImplicitType(val1, TYPE_STRING);
            return this.factory.toUpperCase(val1);
         case 65:
            return this.factory.stringLength(this.getStringValue(this.onlyChild(node)));
         case 66:
            Value locatePath = this.getValue(this.firstChild(node));
            Value locateSearch = this.getValue(secondChild(node));
            Value locateFromIndex = null;
            if (node.getChildCount() > 2) {
               locateFromIndex = this.getValue(thirdChild(node));
            }

            setImplicitType(locatePath, TYPE_STRING);
            setImplicitType(locateSearch, TYPE_STRING);
            if (locateFromIndex != null) {
               setImplicitType(locateFromIndex, TYPE_STRING);
            }

            return this.factory.add(this.factory.indexOf(locateSearch, (Value)(locateFromIndex == null ? locatePath : this.factory.newArgumentList(locatePath, this.factory.subtract(locateFromIndex, this.factory.newLiteral(Numbers.valueOf(1), 1))))), this.factory.newLiteral(Numbers.valueOf(1), 1));
         case 67:
            return this.factory.abs(this.getNumberValue(this.onlyChild(node)));
         case 68:
            return this.factory.sqrt(this.getNumberValue(this.onlyChild(node)));
         case 69:
            val1 = this.getValue(this.left(node));
            val2 = this.getValue(this.right(node));
            this.setImplicitTypes(val1, val2, TYPE_NUMBER);
            return this.factory.mod(val1, val2);
         case 70:
            return this.factory.size(this.getValue(this.onlyChild(node)));
         case 71:
            return this.factory.getCurrentDate();
         case 72:
            return this.factory.getCurrentTime();
         case 73:
            return this.factory.getCurrentTimestamp();
         case 78:
            this.assertQueryExtensions("ORDER BY");
            return this.eval(this.onlyChild(node));
         case 81:
         case 82:
            return this.getIdentifier(node);
         case 83:
            BigDecimal intlit = (new BigDecimal(!node.text.endsWith("l") && !node.text.endsWith("L") ? node.text : node.text.substring(0, node.text.length() - 1))).multiply(new BigDecimal(this.negative(node)));
            return this.factory.newLiteral(new Long(intlit.longValue()), 1);
         case 84:
            BigDecimal declit = (new BigDecimal(!node.text.endsWith("d") && !node.text.endsWith("D") && !node.text.endsWith("f") && !node.text.endsWith("F") ? node.text : node.text.substring(0, node.text.length() - 1))).multiply(new BigDecimal(this.negative(node)));
            return this.factory.newLiteral(declit, 1);
         case 85:
            return this.factory.newLiteral("true".equalsIgnoreCase(node.text) ? Boolean.TRUE : Boolean.FALSE, 2);
         case 86:
         case 90:
         case 91:
            return this.factory.newLiteral(this.trimQuotes(node.text), 4);
         case 87:
            return this.getParameter(node.text, false);
         case 88:
            return this.getParameter(node.text, true);
         case 89:
            return this.eval(this.firstChild(node));
      }
   }

   private void assertQueryExtensions(String clause) {
      OpenJPAConfiguration conf = this.resolver.getConfiguration();
      switch (conf.getCompatibilityInstance().getJPQL()) {
         case 0:
            throw new ParseException(_loc.get("query-extensions-error", clause, this.currentQuery()).getMessage());
         case 1:
            StoreContext ctx = this.resolver.getQueryContext().getStoreContext();
            String query = this.currentQuery();
            if (ctx.getBroker() != null && query != null) {
               String key = this.getClass().getName() + ":" + query;
               BrokerFactory factory = ctx.getBroker().getBrokerFactory();
               Object hasWarned = factory.getUserObject(key);
               if (hasWarned != null) {
                  return;
               }

               factory.putUserObject(key, Boolean.TRUE);
            }

            Log log = conf.getLog("openjpa.Query");
            if (log.isWarnEnabled()) {
               log.warn(_loc.get("query-extensions-warning", clause, this.currentQuery()));
            }
         case 2:
            return;
         default:
            throw new IllegalStateException("Compatibility.getJPQL() == " + conf.getCompatibilityInstance().getJPQL());
      }
   }

   protected void setImplicitTypes(Value val1, Value val2, Class expected) {
      super.setImplicitTypes(val1, val2, expected);
      Parameter param = val1 instanceof Parameter ? (Parameter)val1 : (val2 instanceof Parameter ? (Parameter)val2 : null);
      Path path = val1 instanceof Path ? (Path)val1 : (val2 instanceof Path ? (Path)val2 : null);
      if (param != null && path != null && this.parameterTypes != null) {
         FieldMetaData fmd = path.last();
         if (fmd != null) {
            Class type = path.isXPath() ? path.getType() : fmd.getType();
            if (type != null) {
               String paramName = param.getParameterName();
               if (paramName != null) {
                  if (this.parameterTypes.containsKey(paramName)) {
                     this.parameterTypes.put(paramName, type);
                  }

               }
            }
         }
      }
   }

   private Value getStringValue(JPQLNode node) {
      return this.getTypeValue(node, TYPE_STRING);
   }

   private Value getNumberValue(JPQLNode node) {
      return this.getTypeValue(node, TYPE_NUMBER);
   }

   private Value getTypeValue(JPQLNode node, Class implicitType) {
      Value val = this.getValue(node);
      setImplicitType(val, implicitType);
      return val;
   }

   private Value getSubquery(JPQLNode node) {
      boolean subclasses = true;
      String alias = this.nextAlias();
      ParsedJPQL parsed = new ParsedJPQL(node.parser.jpql, node);
      ClassMetaData candidate = this.getCandidateMetaData(node);
      Subquery subq = this.factory.newSubquery(candidate, true, alias);
      subq.setMetaData(candidate);
      this.contexts.push(new Context(parsed, subq));

      Subquery var8;
      try {
         QueryExpressions subexp = this.getQueryExpressions();
         subq.setQueryExpressions(subexp);
         var8 = subq;
      } finally {
         this.contexts.pop();
      }

      return var8;
   }

   private Parameter getParameter(String id, boolean positional) {
      if (this.parameterTypes == null) {
         this.parameterTypes = new LinkedMap(6);
      }

      if (!this.parameterTypes.containsKey(id)) {
         this.parameterTypes.put(id, TYPE_OBJECT);
      }

      Class type = Object.class;
      ClassMetaData meta = null;
      int index;
      if (positional) {
         try {
            index = Integer.parseInt(id) - 1;
         } catch (NumberFormatException var7) {
            throw this.parseException(0, "bad-positional-parameter", new Object[]{id}, var7);
         }

         if (index < 0) {
            throw this.parseException(0, "bad-positional-parameter", new Object[]{id}, (Exception)null);
         }
      } else {
         index = this.parameterTypes.indexOf(id);
      }

      Parameter param = this.factory.newParameter(id, type);
      param.setMetaData((ClassMetaData)meta);
      param.setIndex(index);
      return param;
   }

   private Expression evalNot(boolean not, Expression exp) {
      return not ? this.factory.not(exp) : exp;
   }

   private String trimQuotes(String str) {
      if (str != null && str.length() > 1) {
         if (str.startsWith("'") && str.endsWith("'")) {
            str = str.substring(1, str.length() - 1);
         }

         for(int index = -1; (index = str.indexOf("''", index + 1)) != -1; str = str.substring(0, index + 1) + str.substring(index + 2)) {
         }

         return str;
      } else {
         return str;
      }
   }

   private short negative(JPQLNode node) {
      return (short)(node.children != null && node.children.length == 1 && this.firstChild(node).id == 56 ? -1 : 1);
   }

   private Value getIdentifier(JPQLNode node) {
      String name = node.text;
      Value val = this.getVariable(name, false);
      ClassMetaData cmd = this.getMetaDataForAlias(name);
      if (cmd != null) {
         Value thiz = this.factory.getThis();
         thiz.setMetaData(cmd);
         return thiz;
      } else if (val instanceof Path) {
         return (Path)val;
      } else if (val instanceof Value) {
         return val;
      } else {
         throw this.parseException(0, "unknown-identifier", new Object[]{name}, (Exception)null);
      }
   }

   private Value getPathOrConstant(JPQLNode node) {
      String className = assemble(node, ".", 1);
      Class c = this.resolver.classForName(className, (String[])null);
      if (c != null) {
         String fieldName = lastChild(node).text;

         try {
            Field field = c.getField(fieldName);
            Object value = field.get((Object)null);
            return this.factory.newLiteral(value, 0);
         } catch (NoSuchFieldException var7) {
            if (node.inEnumPath) {
               throw this.parseException(0, "no-field", new Object[]{c.getName(), fieldName}, var7);
            } else {
               return this.getPath(node, false, true);
            }
         } catch (Exception var8) {
            throw this.parseException(0, "unaccessible-field", new Object[]{className, fieldName}, var8);
         }
      } else {
         return this.getPath(node, false, true);
      }
   }

   private Path getPath(JPQLNode node) {
      return this.getPath(node, false, true);
   }

   private Path getPath(JPQLNode node, boolean pcOnly, boolean inner) {
      String name = this.firstChild(node).text;
      Value val = this.getVariable(name, false);
      Path path;
      if (name.equalsIgnoreCase(this.ctx().schemaAlias)) {
         if (this.ctx().subquery != null) {
            path = this.factory.newPath(this.ctx().subquery);
            path.setMetaData(this.ctx().subquery.getMetaData());
         } else {
            path = this.factory.newPath();
            path.setMetaData(this.ctx().meta);
         }
      } else if (this.getMetaDataForAlias(name) != null) {
         path = this.newPath((Value)null, this.getMetaDataForAlias(name));
      } else if (val instanceof Path) {
         path = (Path)val;
      } else {
         if (val.getMetaData() == null) {
            throw this.parseException(0, "path-invalid", new Object[]{assemble(node), name}, (Exception)null);
         }

         path = this.newPath(val, val.getMetaData());
      }

      boolean allowNull = !inner;

      for(int i = 1; i < node.children.length; ++i) {
         if (path.isXPath()) {
            for(int j = i; j < node.children.length; ++j) {
               path = (Path)this.traverseXPath(path, node.children[j].text);
            }

            return path;
         }

         path = (Path)this.traversePath(path, node.children[i].text, pcOnly, allowNull);
         allowNull = false;
      }

      return path;
   }

   protected Class getDeclaredVariableType(String name) {
      ClassMetaData cmd = this.getMetaDataForAlias(name);
      if (cmd != null) {
         return cmd.getDescribedType();
      } else {
         return name != null && name.equals(this.ctx().schemaAlias) ? this.getCandidateType() : null;
      }
   }

   private Expression getExpression(JPQLNode node) {
      Object exp = this.eval(node);
      return !(exp instanceof Expression) ? this.factory.asExpression((Value)exp) : (Expression)exp;
   }

   private Value getValue(JPQLNode node) {
      return this.getValue(node, 1);
   }

   private Path newPath(Value val, ClassMetaData meta) {
      Path path = val == null ? this.factory.newPath() : this.factory.newPath(val);
      if (meta != null) {
         path.setMetaData(meta);
      }

      return path;
   }

   private Value getValue(JPQLNode node, int handleVar) {
      Value val = (Value)this.eval(node);
      if (!val.isVariable()) {
         return val;
      } else if (handleVar == 1 && !(val instanceof Path)) {
         return this.newPath(val, val.getMetaData());
      } else if (handleVar == 2) {
         throw this.parseException(0, "unexpected-var", new Object[]{node.text}, (Exception)null);
      } else {
         return val;
      }
   }

   private Context ctx() {
      return (Context)this.contexts.peek();
   }

   private JPQLNode root() {
      return this.ctx().parsed.root;
   }

   private ClassMetaData getMetaDataForAlias(String alias) {
      for(int i = this.contexts.size() - 1; i >= 0; --i) {
         Context context = (Context)this.contexts.get(i);
         if (alias.equalsIgnoreCase(context.schemaAlias)) {
            return context.meta;
         }
      }

      return null;
   }

   private JPQLNode onlyChild(JPQLNode node) throws UserException {
      JPQLNode child = this.firstChild(node);
      if (node.children.length > 1) {
         throw this.parseException(0, "multi-children", new Object[]{node, Arrays.asList(node.children)}, (Exception)null);
      } else {
         return child;
      }
   }

   private JPQLNode left(JPQLNode node) {
      return this.child(node, 0, 2);
   }

   private JPQLNode right(JPQLNode node) {
      return this.child(node, 1, 2);
   }

   private JPQLNode child(JPQLNode node, int childNum, int assertCount) {
      if (node.children.length != assertCount) {
         throw this.parseException(0, "wrong-child-count", new Object[]{new Integer(assertCount), node, Arrays.asList(node.children)}, (Exception)null);
      } else {
         return node.children[childNum];
      }
   }

   private JPQLNode firstChild(JPQLNode node) {
      if (node.children != null && node.children.length != 0) {
         return node.children[0];
      } else {
         throw this.parseException(0, "no-children", new Object[]{node}, (Exception)null);
      }
   }

   private static JPQLNode secondChild(JPQLNode node) {
      return node.children[1];
   }

   private static JPQLNode thirdChild(JPQLNode node) {
      return node.children[2];
   }

   private static JPQLNode lastChild(JPQLNode node) {
      return lastChild(node, 0);
   }

   private static JPQLNode lastChild(JPQLNode node, int fromLast) {
      return node.children[node.children.length - (1 + fromLast)];
   }

   public static class ParsedJPQL implements Serializable {
      private final transient JPQLNode root;
      private final String query;
      private Class _candidateType;

      ParsedJPQL(String jpql) {
         this(jpql, parse(jpql));
      }

      ParsedJPQL(String query, JPQLNode root) {
         this.root = root;
         this.query = query;
      }

      private static JPQLNode parse(String jpql) {
         if (jpql == null) {
            jpql = "";
         }

         try {
            return (JPQLNode)(new JPQL(jpql)).parseQuery();
         } catch (Error var2) {
            throw new UserException(JPQLExpressionBuilder._loc.get("parse-error", new Object[]{var2.toString(), jpql}));
         }
      }

      void populate(ExpressionStoreQuery query) {
         QueryContext ctx = query.getContext();
         if (ctx.getCandidateType() == null) {
            if (this._candidateType == null) {
               this._candidateType = (new JPQLExpressionBuilder((ExpressionFactory)null, query, this)).getCandidateType();
            }

            ctx.setCandidateType(this._candidateType, true);
         }

      }

      public Class getCandidateType() {
         return this._candidateType;
      }

      public String toString() {
         return this.query;
      }
   }

   protected abstract static class JPQLNode implements Node, Serializable {
      final int id;
      final JPQL parser;
      JPQLNode parent;
      JPQLNode[] children;
      String text;
      boolean not = false;
      boolean inEnumPath = false;

      public JPQLNode(JPQL parser, int id) {
         this.id = id;
         this.parser = parser;
         this.inEnumPath = parser.inEnumPath;
      }

      public void jjtOpen() {
      }

      public void jjtClose() {
      }

      JPQLNode[] findChildrenByID(int id) {
         Collection set = new HashSet();
         this.findChildrenByID(id, set);
         return (JPQLNode[])((JPQLNode[])set.toArray(new JPQLNode[set.size()]));
      }

      private void findChildrenByID(int id, Collection set) {
         for(int i = 0; this.children != null && i < this.children.length; ++i) {
            if (this.children[i].id == id) {
               set.add(this.children[i]);
            }

            this.children[i].findChildrenByID(id, set);
         }

      }

      boolean hasChildID(int id) {
         return this.findChildByID(id, false) != null;
      }

      JPQLNode findChildByID(int id, boolean recurse) {
         for(int i = 0; this.children != null && i < this.children.length; ++i) {
            JPQLNode child = this.children[i];
            if (child.id == id) {
               return this.children[i];
            }

            if (recurse) {
               JPQLNode found = child.findChildByID(id, recurse);
               if (found != null) {
                  return found;
               }
            }
         }

         return null;
      }

      public void jjtSetParent(Node parent) {
         this.parent = (JPQLNode)parent;
      }

      public Node jjtGetParent() {
         return this.parent;
      }

      public void jjtAddChild(Node n, int i) {
         if (this.children == null) {
            this.children = new JPQLNode[i + 1];
         } else if (i >= this.children.length) {
            JPQLNode[] c = new JPQLNode[i + 1];
            System.arraycopy(this.children, 0, c, 0, this.children.length);
            this.children = c;
         }

         this.children[i] = (JPQLNode)n;
      }

      public Node jjtGetChild(int i) {
         return this.children[i];
      }

      public int getChildCount() {
         return this.jjtGetNumChildren();
      }

      public JPQLNode getChild(int index) {
         return (JPQLNode)this.jjtGetChild(index);
      }

      public Iterator iterator() {
         return Arrays.asList(this.children).iterator();
      }

      public int jjtGetNumChildren() {
         return this.children == null ? 0 : this.children.length;
      }

      void setText(String text) {
         this.text = text;
      }

      void setToken(Token t) {
         this.setText(t.image);
      }

      public String toString() {
         return JPQLTreeConstants.jjtNodeName[this.id];
      }

      public String toString(String prefix) {
         return prefix + this.toString();
      }

      public void dump(String prefix) {
         this.dump(System.out, prefix);
      }

      public void dump() {
         this.dump(" ");
      }

      public void dump(PrintStream out, String prefix) {
         this.dump(out, prefix, false);
      }

      public void dump(PrintStream out, String prefix, boolean text) {
         out.println(this.toString(prefix) + (text && this.text != null ? " [" + this.text + "]" : ""));
         if (this.children != null) {
            for(int i = 0; i < this.children.length; ++i) {
               JPQLNode n = this.children[i];
               if (n != null) {
                  n.dump(out, prefix + " ", text);
               }
            }
         }

      }
   }

   private class Context {
      private final ParsedJPQL parsed;
      private ClassMetaData meta;
      private String schemaAlias;
      private Subquery subquery;

      Context(ParsedJPQL parsed, Subquery subquery) {
         this.parsed = parsed;
         this.subquery = subquery;
      }
   }
}
