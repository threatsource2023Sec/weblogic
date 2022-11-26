package kodo.kernel.jdoql;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.QueryContext;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.kernel.exps.AbstractExpressionBuilder;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.Arguments;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionFactory;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.kernel.exps.Parameter;
import org.apache.openjpa.kernel.exps.Path;
import org.apache.openjpa.kernel.exps.QueryExpressions;
import org.apache.openjpa.kernel.exps.Resolver;
import org.apache.openjpa.kernel.exps.Subquery;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;
import serp.util.Strings;

class JDOQLExpressionBuilder extends AbstractExpressionBuilder {
   private static final int END = 0;
   private static final int NUMBER = 1;
   private static final int ID = 2;
   private static final int STRING = 3;
   private static final int TIMES = 4;
   private static final int DIVIDE_BY = 5;
   private static final int ADD = 6;
   private static final int SUBTRACT = 7;
   private static final int MOD = 8;
   private static final int GREATER = 9;
   private static final int LESS = 10;
   private static final int GREATER_EQUAL = 11;
   private static final int LESS_EQUAL = 12;
   private static final int EQUAL_EQUAL = 13;
   private static final int NOT_EQUAL = 14;
   private static final int LOGICAL_AND = 15;
   private static final int CONDITIONAL_AND = 16;
   private static final int LOGICAL_OR = 17;
   private static final int CONDITIONAL_OR = 18;
   private static final int BITWISE_COMPLEMENT = 19;
   private static final int NOT = 20;
   private static final int LEFT_PAREN = 21;
   private static final int RIGHT_PAREN = 22;
   private static final int NULL = 23;
   private static final int IS_EMPTY = 24;
   private static final int CONTAINS = 25;
   private static final int CAST = 26;
   private static final int DOT = 27;
   private static final int BOOLEAN = 28;
   private static final int THIS = 29;
   private static final int VARIABLE = 30;
   private static final int PARAMETER = 31;
   private static final int CLASS = 32;
   private static final int CONTAINS_KEY = 33;
   private static final int CONTAINS_VALUE = 34;
   private static final int CONTAINS_VAR = 35;
   private static final int CONTAINS_KEY_VAR = 36;
   private static final int CONTAINS_VALUE_VAR = 37;
   private static final int EXTENSION = 38;
   private static final int AGGREGATE = 39;
   private static final int COMMA = 40;
   private static final int STATIC_VALUE = 41;
   private static final int INSTANCEOF = 42;
   private static final int MATCHES = 43;
   private static final int STARTS_WITH = 44;
   private static final int ENDS_WITH = 45;
   private static final int TO_UPPER = 46;
   private static final int TO_LOWER = 47;
   private static final int SUBSTRING = 48;
   private static final int INDEX_OF = 49;
   private static final int COUNT = 50;
   private static final int MAX = 51;
   private static final int MIN = 52;
   private static final int SUM = 53;
   private static final int AVERAGE = 54;
   private static final int ABS = 55;
   private static final int SQRT = 56;
   private static final int GET_OID = 57;
   private static final int SUBQUERY = 58;
   private static final int SQ_STRING = 59;
   private static final int SIZE = 60;
   private static final int DISTINCT = 61;
   private static final int POSITIVE = 62;
   private static final int NEGATIVE = 63;
   private static final int MAP_GET = 64;
   private static final int MAP_GET_VAR = 65;
   private static final int NOARGS_OK = 100;
   private static final int BINARY_OK = 101;
   private static final Class TYPE_DATE = Date.class;
   private static final Class TYPE_CALENDAR = Calendar.class;
   private static final Set KEYWORDS = new HashSet(Arrays.asList("select", "SELECT", "from", "FROM", "as", "AS", "into", "INTO", "where", "WHERE", "variables", "VARIABLES", "parameters", "PARAMETERS", "import", "IMPORT", "having", "HAVING", "group", "GROUP", "by", "BY", "order", "ORDER", "range", "RANGE", "null", "true", "false", "this", "instanceof"));
   private static final String[] TYPE_LOOKUP = new String[]{"END", "NUMBER", "ID", "STRING", "TIMES", "DIVIDE_BY", "ADD", "SUBTRACT", "MOD", "GREATER", "LESS", "GREATER_EQUAL", "LESS_EQUAL", "EQUAL_EQUAL", "NOT_EQUAL", "LOGICAL_AND", "CONDITIONAL_AND", "LOGICAL_OR", "CONDITIONAL_OR", "BITWISE_COMPLEMENT", "NOT", "LEFT_PAREN", "RIGHT_PAREN", "NULL", "IS_EMPTY", "CONTAINS", "CAST", "DOT", "BOOLEAN", "THIS", "VARIABLE", "PARAMETER", "CLASS", "CONTAINS_KEY", "CONTAINS_VALUE", "CONTAINS_VAR", "CONTAINS_KEY_VAR", "CONTAINS_VALUE_VAR", "EXTENSION", "AGGREGATE", "COMMA", "STATIC_VALUE", "INSTANCEOF", "MATCHES", "STARTS_WITH", "ENDS_WITH", "TO_UPPER", "TO_LOWER", "SUBSTRING", "INDEX_OF", "COUNT", "MAX", "MIN", "SUM", "AVERAGE", "ABS", "SQRT", "GET_OID", "SUBQUERY", "SQ_STRING", "SIZE", "DISTINCT", "POSITIVE", "NEGATIVE", "MAP_GET", "MAP_GET_VAR"};
   private static final int VAR_OK = 0;
   private static final int VAR_PATH = 1;
   private static final int VAR_ERROR = 2;
   private static Localizer _loc = Localizer.forPackage(JDOQLExpressionBuilder.class);
   private final Class _type;
   private final ClassMetaData _meta;
   private final Stack _contexts = new Stack();
   private Map _seenParams = null;
   private LinkedMap _decParams = null;
   private Map _decVars = null;
   private String[] _imports = null;

   public JDOQLExpressionBuilder(ExpressionFactory factory, ClassMetaData meta, Class type, Resolver resolver) {
      super(factory, resolver);
      this._meta = meta;
      this._type = type;
      this.addAccessPath(meta);
      this._contexts.push(new Context());
   }

   protected ClassLoader getClassLoader() {
      return this._meta.getEnvClassLoader();
   }

   public QueryExpressions eval(SingleString sstr) {
      List decs;
      if (sstr.imports != null) {
         decs = Filters.parseDeclaration(sstr.imports, ';', "imports");
         this._imports = new String[decs.size() / 2];

         for(int i = 0; i < decs.size(); i += 2) {
            this._imports[i / 2] = (String)decs.get(i + 1);
         }
      }

      if (sstr.parameters != null) {
         decs = Filters.parseDeclaration(sstr.parameters, ',', "parameters");
         this._decParams = new LinkedMap((int)((double)(decs.size() / 2) * 1.33 + 1.0));
         this.parseDeclaration(decs, this._decParams);
      }

      if (sstr.variables != null) {
         decs = Filters.parseDeclaration(sstr.variables, ';', "variables");
         this._decVars = new HashMap((int)((double)(decs.size() / 2) * 1.33 + 1.0));
         this.parseDeclaration(decs, this._decVars);
      }

      Object name;
      Iterator itr;
      if (this._decParams != null) {
         itr = this._decParams.keySet().iterator();

         while(itr.hasNext()) {
            name = itr.next();
            if (KEYWORDS.contains(name)) {
               throw new UserException(_loc.get("keyword", "Parameter", name));
            }

            if (this._decVars != null && this._decVars.containsKey(name)) {
               throw new UserException(_loc.get("param-var", name));
            }
         }
      }

      if (this._decVars != null) {
         itr = this._decVars.keySet().iterator();

         while(itr.hasNext()) {
            name = itr.next();
            if (KEYWORDS.contains(name)) {
               throw new UserException(_loc.get("keyword", "Variable", name));
            }
         }
      }

      QueryExpressions exps = new QueryExpressions();
      exps.filter = this.evalExpression(sstr.filter, false);
      String result;
      int i;
      int idx;
      if (sstr.result != null) {
         result = sstr.result;
         if (!result.startsWith("distinct ") && !result.startsWith("DISTINCT ")) {
            if (result.startsWith("nondistinct ") || result.startsWith("NONDISTINCT ")) {
               exps.distinct = 8;
               result = result.substring(12).trim();
            }
         } else {
            exps.distinct |= 4;
            result = result.substring(9).trim();
         }

         if (result.startsWith("new ") || result.startsWith("NEW ")) {
            result = result.substring(4).trim();

            for(i = 0; i < result.length(); ++i) {
               idx = result.charAt(i);
               if (!Character.isJavaIdentifierPart((char)idx) && idx != 46) {
                  break;
               }
            }

            String resultClass = result.substring(0, i);
            exps.resultClass = this.resolver.classForName(resultClass, this._imports);
            result = result.substring(i + 1).trim();
            if (result.startsWith("(")) {
               result = result.substring(1);
            }

            if (result.endsWith(")")) {
               result = result.substring(0, result.length() - 1);
            }
         }

         List decs = Filters.splitExpressions(result, ',', 5);
         exps.projectionClauses = (String[])((String[])decs.toArray(new String[decs.size()]));
         exps.projectionAliases = new String[decs.size()];

         int i;
         for(i = 0; i < exps.projectionClauses.length; ++i) {
            idx = exps.projectionClauses[i].lastIndexOf(" as ");
            if (idx == -1) {
               idx = exps.projectionClauses[i].lastIndexOf(" AS ");
            }

            if (idx == -1) {
               exps.projectionClauses[i] = exps.projectionClauses[i].trim();
               exps.projectionAliases[i] = exps.projectionClauses[i];
            } else {
               exps.projectionAliases[i] = exps.projectionClauses[i].substring(idx + 4).trim();
               exps.projectionClauses[i] = exps.projectionClauses[i].substring(0, idx).trim();
            }
         }

         if (exps.projectionClauses.length == 1 && "this".equals(exps.projectionClauses[0])) {
            exps.projectionClauses = StoreQuery.EMPTY_STRINGS;
            exps.alias = exps.projectionAliases[0];
            if (!"this".equals(exps.alias) && KEYWORDS.contains(exps.alias)) {
               throw new UserException(_loc.get("keyword", "Alias", exps.alias));
            }

            exps.projectionAliases = StoreQuery.EMPTY_STRINGS;
         } else {
            for(i = 0; i < exps.projectionClauses.length; ++i) {
               if (!"this".equals(exps.projectionClauses[i]) && KEYWORDS.contains(exps.projectionClauses[i])) {
                  throw new UserException(_loc.get("keyword", "Projection", exps.projectionClauses[i]));
               }

               if ((!"this".equals(exps.projectionClauses[i]) || !"this".equals(exps.projectionAliases[i])) && KEYWORDS.contains(exps.projectionAliases[i])) {
                  throw new UserException(_loc.get("keyword", "Alias", exps.projectionAliases[i]));
               }
            }

            exps.projections = this.evalValues(exps.projectionClauses, true, true);
         }
      } else {
         exps.distinct |= 4;
         exps.alias = Strings.getClassName(this._meta.getDescribedType());
      }

      if (sstr.grouping != null) {
         if (exps.projections.length == 0) {
            throw new UserException(_loc.get("grouping-no-result", this._meta.getDescribedType(), sstr.filter));
         }

         result = sstr.grouping;
         String having = null;
         idx = result.lastIndexOf(" having ");
         if (idx == -1) {
            idx = result.lastIndexOf(" HAVING ");
         }

         if (idx != -1) {
            having = result.substring(idx + 7).trim();
            result = result.substring(0, idx);
         }

         List decs = Filters.splitExpressions(result, ',', 3);
         exps.groupingClauses = (String[])((String[])decs.toArray(new String[decs.size()]));

         for(int i = 0; i < exps.groupingClauses.length; ++i) {
            if (!"this".equals(exps.groupingClauses[i]) && KEYWORDS.contains(exps.groupingClauses[i])) {
               throw new UserException(_loc.get("keyword", "Grouping", exps.groupingClauses[i]));
            }
         }

         exps.grouping = this.evalValues(exps.groupingClauses, false, true);
         if (having != null) {
            exps.having = this.evalExpression(having, false);
         }
      }

      if (sstr.ordering != null) {
         List decs = Filters.splitExpressions(sstr.ordering, ',', 3);
         exps.orderingClauses = (String[])((String[])decs.toArray(new String[decs.size()]));
         exps.ascending = new boolean[exps.orderingClauses.length];

         for(i = 0; i < exps.orderingClauses.length; ++i) {
            if (!exps.orderingClauses[i].endsWith(" ascending") && !exps.orderingClauses[i].endsWith(" ASCENDING")) {
               if (!exps.orderingClauses[i].endsWith(" asc") && !exps.orderingClauses[i].endsWith(" ASC")) {
                  if (!exps.orderingClauses[i].endsWith(" descending") && !exps.orderingClauses[i].endsWith(" DESCENDING")) {
                     if (!exps.orderingClauses[i].endsWith(" desc") && !exps.orderingClauses[i].endsWith(" DESC")) {
                        throw new UserException(_loc.get("bad-ordering-dec", exps.orderingClauses[i]));
                     }

                     exps.orderingClauses[i] = exps.orderingClauses[i].substring(0, exps.orderingClauses[i].length() - 5).trim();
                     exps.ascending[i] = false;
                  } else {
                     exps.orderingClauses[i] = exps.orderingClauses[i].substring(0, exps.orderingClauses[i].length() - 11).trim();
                     exps.ascending[i] = false;
                  }
               } else {
                  exps.orderingClauses[i] = exps.orderingClauses[i].substring(0, exps.orderingClauses[i].length() - 4).trim();
                  exps.ascending[i] = true;
               }
            } else {
               exps.orderingClauses[i] = exps.orderingClauses[i].substring(0, exps.orderingClauses[i].length() - 10).trim();
               exps.ascending[i] = true;
            }

            if (!"this".equals(exps.orderingClauses[i]) && KEYWORDS.contains(exps.orderingClauses[i])) {
               throw new UserException(_loc.get("keyword", "Ordering", exps.orderingClauses[i]));
            }
         }

         exps.ordering = this.evalValues(exps.orderingClauses, false, true);
      }

      if (sstr.range != null) {
         i = sstr.range.toLowerCase().indexOf(" to ");
         String[] idxs;
         if (i != -1) {
            idxs = new String[]{sstr.range.substring(0, i).trim(), sstr.range.substring(i + 4).trim()};
         } else {
            List l = Filters.splitExpressions(sstr.range, ',', 2);
            if (l.size() != 2) {
               throw new UserException(_loc.get("bad-range", sstr.range));
            }

            idxs = (String[])((String[])l.toArray(new String[2]));
         }

         exps.range = this.evalValues(idxs, false, true);
      }

      exps.accessPath = this.getAccessPath();
      if (this._decParams != null) {
         exps.parameterTypes = this._decParams;
      } else {
         result = null;
         LinkedMap resultParams = this.currentContext().resultParams;
         LinkedMap implicitParams = this.currentContext().implicitParams;
         LinkedMap params;
         if (resultParams == null) {
            params = implicitParams;
         } else if (implicitParams != null && !implicitParams.isEmpty()) {
            params = new LinkedMap((int)((double)(resultParams.size() + implicitParams.size()) * 1.33 + 1.0));
            params.putAll(resultParams);
            params.putAll(implicitParams);
         } else {
            params = resultParams;
         }

         if (params != null) {
            int i = 0;

            for(Iterator itr = params.entrySet().iterator(); itr.hasNext(); ++i) {
               Map.Entry entry = (Map.Entry)itr.next();
               Parameter param = (Parameter)this._seenParams.get(entry.getKey());
               param.setIndex(i);
               entry.setValue(param.getType());
            }

            exps.parameterTypes = params;
         }
      }

      return exps;
   }

   private void parseDeclaration(List decs, Map map) {
      for(int i = 0; i < decs.size(); i += 2) {
         String str = (String)decs.get(i);
         Class cls = this.resolver.classForName(str, this._imports);
         if (cls == null) {
            throw new UserException(_loc.get("bad-type", str));
         }

         str = (String)decs.get(i + 1);
         if (str.startsWith(":")) {
            str = str.substring(1);
         }

         map.put(str, cls);
      }

   }

   public Value[] eval(String[] vals, LinkedMap paramTypes, boolean traverseNull) {
      this._decParams = paramTypes;
      return this.evalValues(vals, false, traverseNull);
   }

   protected String currentQuery() {
      return this.currentContext().jdoql;
   }

   protected Localizer getLocalizer() {
      return _loc;
   }

   private Expression evalExpression(String jdoql, boolean traverseNull) {
      Node compiled = null;
      if (jdoql != null && jdoql.length() > 0 && !"true".equals(jdoql)) {
         this.currentContext().jdoql = jdoql;
         compiled = this.parse();
      }

      if (compiled == null) {
         return this.factory.emptyExpression();
      } else {
         try {
            Expression exp = this.getExpression(compiled, traverseNull);
            this.assertUnboundVariablesValid();
            return exp;
         } catch (OpenJPAException var5) {
            throw var5;
         } catch (Exception var6) {
            throw this.parseException(0, "bad-jdoql", (Object[])null, var6);
         }
      }
   }

   private Value[] evalValues(String[] jdoql, boolean result, boolean traverseNull) {
      if (jdoql != null && jdoql.length != 0) {
         Value[] vals = new Value[jdoql.length];
         this.currentContext().result = result;

         try {
            for(int i = 0; i < jdoql.length; ++i) {
               this.currentContext().jdoql = jdoql[i];
               vals[i] = this.getValue(this.parse(), 1, traverseNull);
               this.assertUnboundVariablesValid();
            }

            Value[] var13 = vals;
            return var13;
         } catch (OpenJPAException var10) {
            throw var10;
         } catch (Exception var11) {
            throw this.parseException(0, "bad-jdoql", (Object[])null, var11);
         } finally {
            this.currentContext().result = false;
         }
      } else {
         return QueryExpressions.EMPTY_VALUES;
      }
   }

   private Context currentContext() {
      return (Context)this._contexts.peek();
   }

   private Context startContext(Subquery sub) {
      if (this.getSubquery(sub.getCandidateAlias()) != null) {
         throw this.parseException(0, "dup-subquery-alias", new Object[]{sub.getCandidateAlias()}, (Exception)null);
      } else {
         Context context = new Context();
         context.subquery = sub;
         this._contexts.push(context);
         return context;
      }
   }

   private void endContext() {
      Context context = (Context)this._contexts.pop();
      if (context.implicitParams != null || context.resultParams != null) {
         Context base = (Context)this._contexts.peek();
         if (base.result) {
            if (base.resultParams == null) {
               base.resultParams = context.resultParams;
            } else if (context.resultParams != null) {
               base.resultParams.putAll(context.resultParams);
            }

            if (base.resultParams == null) {
               base.resultParams = context.implicitParams;
            } else if (context.implicitParams != null) {
               base.resultParams.putAll(context.implicitParams);
            }
         } else {
            if (base.implicitParams == null) {
               base.implicitParams = context.resultParams;
            } else if (context.resultParams != null) {
               base.implicitParams.putAll(context.resultParams);
            }

            if (base.implicitParams == null) {
               base.implicitParams = context.implicitParams;
            } else if (context.implicitParams != null) {
               base.implicitParams.putAll(context.implicitParams);
            }
         }

      }
   }

   private Subquery getSubquery(String alias) {
      for(int i = this._contexts.size() - 1; i >= 1; --i) {
         Context context = (Context)this._contexts.get(i);
         if (context.subquery.getCandidateAlias().equals(alias)) {
            return context.subquery;
         }
      }

      return null;
   }

   protected boolean isDeclaredVariable(String name) {
      return this._decVars != null && this._decVars.containsKey(name);
   }

   private boolean isDeclaredParameter(String name) {
      return this._decParams != null && this._decParams.containsKey(name);
   }

   protected Class getDeclaredVariableType(String name) {
      return this._decVars == null ? null : (Class)this._decVars.get(name);
   }

   private Class getDeclaredParameterType(String name) {
      return this._decParams == null ? null : (Class)this._decParams.get(name);
   }

   private int getDeclaredParameterIndex(String name) {
      return this._decParams == null ? -1 : this._decParams.indexOf(name);
   }

   private Node parse() {
      FilterTokenizer ft = new FilterTokenizer(new StringReader(this.currentContext().jdoql));
      Stack termStack = new Stack();
      Stack nodeStack = new Stack();
      Node endNode = new Node();
      endNode.type = 0;
      termStack.push(endNode);

      try {
         Node a = endNode;
         Node b = this.scan(ft);

         while(true) {
            while(a.type != 0 || b.type != 0) {
               if (f(a.type) <= g(b.type)) {
                  termStack.push(b);
                  a = (Node)termStack.peek();
                  b = this.scan(ft);
               } else {
                  while(true) {
                     Node popped = (Node)termStack.pop();
                     Node n1;
                     Node n2;
                     switch (popped.type) {
                        case 1:
                        case 2:
                        case 3:
                        case 23:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 41:
                        case 58:
                        case 59:
                           nodeStack.push(popped);
                           break;
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 42:
                           popped.right = (Node)nodeStack.pop();
                           popped.left = (Node)nodeStack.pop();
                           nodeStack.push(popped);
                           break;
                        case 19:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                        case 61:
                        case 62:
                        case 63:
                           popped.right = (Node)nodeStack.pop();
                           nodeStack.push(popped);
                           break;
                        case 20:
                        case 26:
                           popped.left = (Node)nodeStack.pop();
                           nodeStack.push(popped);
                        case 21:
                        case 22:
                        case 35:
                        case 36:
                        case 37:
                        default:
                           break;
                        case 24:
                        case 46:
                        case 47:
                        case 60:
                           popped.left = (Node)nodeStack.pop();
                           nodeStack.push(popped);
                           termStack.pop();
                           break;
                        case 25:
                           popped.right = (Node)nodeStack.pop();
                           if (this.isVariable(popped.right)) {
                              popped.type = 35;
                           }

                           popped.left = (Node)nodeStack.pop();
                           nodeStack.push(popped);
                           termStack.pop();
                           break;
                        case 27:
                           n2 = (Node)nodeStack.pop();
                           n1 = (Node)nodeStack.pop();
                           if (n1.type == 29 && n2.type != 38 && n2.type != 39) {
                              n2.type = 2;
                              n2.query = n1.query;
                              nodeStack.push(n2);
                              break;
                           }

                           popped.left = n1;
                           popped.right = n2;
                           nodeStack.push(popped);
                           break;
                        case 33:
                           popped.right = (Node)nodeStack.pop();
                           if (this.isVariable(popped.right)) {
                              popped.type = 36;
                           }

                           popped.left = (Node)nodeStack.pop();
                           nodeStack.push(popped);
                           termStack.pop();
                           break;
                        case 34:
                           popped.right = (Node)nodeStack.pop();
                           if (this.isVariable(popped.right)) {
                              popped.type = 37;
                           }

                           popped.left = (Node)nodeStack.pop();
                           nodeStack.push(popped);
                           termStack.pop();
                           break;
                        case 38:
                           FilterListener listener = (FilterListener)popped.value;
                           if (listener.expectsArguments()) {
                              popped.right = (Node)nodeStack.pop();
                           }

                           if (listener.expectsTarget()) {
                              popped.left = (Node)nodeStack.pop();
                              termStack.pop();
                           }

                           nodeStack.push(popped);
                           break;
                        case 39:
                           AggregateListener agg = (AggregateListener)popped.value;
                           if (agg.expectsArguments()) {
                              popped.right = (Node)nodeStack.pop();
                           }

                           nodeStack.push(popped);
                           break;
                        case 40:
                           n2 = (Node)nodeStack.pop();
                           n1 = (Node)nodeStack.pop();
                           popped.left = n1;
                           popped.right = n2;
                           nodeStack.push(popped);
                           break;
                        case 43:
                        case 44:
                        case 45:
                        case 48:
                        case 49:
                           popped.right = (Node)nodeStack.pop();
                           popped.left = (Node)nodeStack.pop();
                           nodeStack.push(popped);
                           termStack.pop();
                           break;
                        case 64:
                           popped.right = (Node)nodeStack.pop();
                           if (this.isVariable(popped.right)) {
                              popped.type = 65;
                           }

                           popped.left = (Node)nodeStack.pop();
                           nodeStack.push(popped);
                           termStack.pop();
                     }

                     a = (Node)termStack.peek();
                     if (f(a.type) < g(popped.type)) {
                        break;
                     }
                  }
               }
            }

            return (Node)nodeStack.pop();
         }
      } catch (OpenJPAException var12) {
         throw var12;
      } catch (Exception var13) {
         throw this.parseException(0, "bad-jdoql", (Object[])null, var13);
      }
   }

   private boolean isVariable(Node node) {
      if (node.type == 30) {
         return true;
      } else if (node.type == 2 && node.query == null) {
         String field = (String)node.value;
         if (this._meta.isAccessibleField(field)) {
            return false;
         } else if (this._decVars != null && !this._decVars.isEmpty()) {
            throw this.parseException(0, "mixed-vars", new Object[]{node.value}, (Exception)null);
         } else {
            node.type = 30;
            return true;
         }
      } else {
         return false;
      }
   }

   private Node scan(FilterTokenizer ft) throws IOException {
      int token = ft.nextToken();
      Node result = new Node();
      String id;
      Class castTo;
      switch (token) {
         case -3:
            id = ft.sval;
            if (this.currentContext().hint == 42) {
               castTo = this.resolver.classForName(id, this._imports);
               if (castTo == null) {
                  throw this.parseException(0, "bad-instanceof-class", new Object[]{id}, (Exception)null);
               }

               result.value = castTo;
               result.type = 32;
            } else {
               int dotIdx = id.lastIndexOf(46);
               boolean method = ft.nextToken() == 40;
               ft.pushBack();
               if (!method || !this.isMethod(id, result, dotIdx != -1)) {
                  int start;
                  if (dotIdx != -1) {
                     String clsName = id.substring(0, dotIdx);
                     start = clsName.lastIndexOf(46);
                     if (Character.isUpperCase(clsName.charAt(start + 1))) {
                        Class cls = this.resolver.classForName(clsName, this._imports);
                        if (cls != null) {
                           Object value = this.traverseStaticField(cls, id.substring(dotIdx + 1));
                           if (value != null) {
                              result.value = value;
                              result.type = 41;
                              break;
                           }
                        }
                     }
                  }

                  if (dotIdx != -1) {
                     int firstDot = id.indexOf(46);

                     for(start = firstDot + 1; dotIdx != -1; start = dotIdx + 1) {
                        ft.lookaheadWord(".");
                        dotIdx = id.indexOf(46, start);
                        if (dotIdx == -1) {
                           ft.lookaheadWord(id.substring(start));
                        } else {
                           ft.lookaheadWord(id.substring(start, dotIdx));
                        }
                     }

                     id = id.substring(0, firstDot);
                  } else if (this.isReservedWord(id, result)) {
                     break;
                  }

                  if ("this".equals(id)) {
                     result.type = 29;
                  } else {
                     Subquery sub = this.getSubquery(id);
                     if (sub != null) {
                        result.type = 29;
                        result.query = sub;
                     } else {
                        boolean param = id.startsWith(":");
                        if (param) {
                           id = id.substring(1);
                        }

                        if (this.isDeclaredParameter(id)) {
                           result.type = 31;
                        } else if (param) {
                           if (this._decParams != null && !this._decParams.isEmpty()) {
                              throw this.parseException(0, "mixed-params", new Object[]{id}, (Exception)null);
                           }

                           result.type = 31;
                           this.recordImplicitParameter(id);
                        } else if (this.isDeclaredVariable(id)) {
                           result.type = 30;
                        } else {
                           if (id.endsWith("--")) {
                              throw this.parseException(0, "assign-ops", (Object[])null, (Exception)null);
                           }

                           if (id.equals("new")) {
                              throw this.parseException(0, "new-op", (Object[])null, (Exception)null);
                           }

                           result.type = 2;
                        }

                        result.value = id;
                     }
                  }
               }
            }
            break;
         case -2:
            result.type = 1;
            result.value = ft.nval;
            break;
         case -1:
            result.type = 0;
            break;
         case 33:
            if (ft.nextToken() == 61) {
               result.type = 14;
            } else {
               ft.pushBack();
               result.type = 20;
            }
            break;
         case 34:
            result.type = 3;
            result.value = ft.sval;
            break;
         case 37:
            result.type = 8;
            break;
         case 38:
            if (ft.nextToken() == 38) {
               result.type = 16;
            } else {
               ft.pushBack();
               result.type = 15;
            }
            break;
         case 39:
            result.type = 59;
            result.value = ft.sval;
            break;
         case 40:
            token = ft.nextToken();
            if (token == 41 && this.currentContext().hint != 100) {
               throw this.parseException(0, "methods", (Object[])null, (Exception)null);
            }

            if (token == -3 && "select".equalsIgnoreCase(ft.sval)) {
               StringBuffer subBuf = new StringBuffer();
               subBuf.append("select ");
               this.readSubqueryWords(ft, subBuf);
               SingleString sstr = new SingleString();
               sstr.fromString(subBuf.toString());
               if (sstr.imports == null && sstr.parameters == null && sstr.variables == null) {
                  if (sstr.candidateAlias == null) {
                     throw this.parseException(0, "no-subquery-alias", new Object[]{sstr}, (Exception)null);
                  }

                  Class candidate = this.resolver.classForName(sstr.candidateType, this._imports);
                  if (candidate == null) {
                     throw this.parseException(0, "bad-subquery-candidate", new Object[]{sstr}, (Exception)null);
                  }

                  ClassMetaData meta = this.getMetaData(candidate, true);
                  this.addAccessPath(meta);
                  result.type = 58;
                  result.query = this.factory.newSubquery(meta, sstr.subclasses, sstr.candidateAlias);
                  result.query.setMetaData(meta);
                  result.value = this.parseSubquery(result.query, sstr);
                  break;
               }

               throw this.parseException(0, "subquery-decs", new Object[]{sstr}, (Exception)null);
            } else {
               if (token == -3 && this.currentContext().hint != 42 && this.currentContext().hint != 25) {
                  id = ft.sval;
                  if (ft.nextToken() == 41 && !this.isDeclaredVariable(id) && !this.isDeclaredParameter(id)) {
                     castTo = this.resolver.classForName(id, this._imports);
                     if (castTo != null) {
                        result.type = 26;
                        result.value = castTo;
                     } else {
                        ft.pushBack();
                        result.type = 21;
                        ft.lookaheadWord(id);
                     }
                  } else {
                     ft.pushBack();
                     result.type = 21;
                     ft.lookaheadWord(id);
                  }
                  break;
               }

               ft.pushBack();
               result.type = 21;
               break;
            }
         case 41:
            result.type = 22;
            break;
         case 42:
            result.type = 4;
            break;
         case 43:
            if (this.currentContext().hint == 101) {
               result.type = 6;
            } else {
               result.type = 62;
            }

            if (ft.nextToken() == 43) {
               throw this.parseException(0, "assign-ops", (Object[])null, (Exception)null);
            }

            ft.pushBack();
            break;
         case 44:
            result.type = 40;
            break;
         case 45:
            if (this.currentContext().hint == 101) {
               result.type = 7;
            } else {
               result.type = 63;
            }

            if (ft.nextToken() == 45) {
               throw this.parseException(0, "assign-ops", (Object[])null, (Exception)null);
            }

            ft.pushBack();
            break;
         case 46:
            result.type = 27;
            break;
         case 47:
            result.type = 5;
            break;
         case 60:
            if (ft.nextToken() == 61) {
               result.type = 12;
            } else {
               ft.pushBack();
               result.type = 10;
            }
            break;
         case 61:
            if (ft.nextToken() != 61) {
               throw this.parseException(0, "assign-ops", (Object[])null, (Exception)null);
            }

            result.type = 13;
            break;
         case 62:
            if (ft.nextToken() == 61) {
               result.type = 11;
            } else {
               ft.pushBack();
               result.type = 9;
            }
            break;
         case 124:
            if (ft.nextToken() == 124) {
               result.type = 18;
            } else {
               ft.pushBack();
               result.type = 17;
            }
            break;
         case 126:
            result.type = 19;
            break;
         default:
            throw this.parseException(0, "token?", new Object[]{new Character((char)token)}, (Exception)null);
      }

      Context context = this.currentContext();
      switch (result.type) {
         case 1:
         case 2:
         case 3:
         case 22:
         case 23:
         case 30:
         case 31:
         case 41:
         case 59:
            context.hint = 101;
            break;
         case 21:
            if (context.hint == 42) {
               break;
            }
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 26:
         case 27:
         case 28:
         case 29:
         case 32:
         case 35:
         case 36:
         case 37:
         case 40:
         case 43:
         case 44:
         case 45:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         default:
            context.hint = 0;
            break;
         case 24:
         case 38:
         case 39:
         case 46:
         case 47:
         case 60:
            context.hint = 100;
            break;
         case 25:
         case 33:
         case 34:
            context.hint = 25;
            break;
         case 42:
            context.hint = 42;
      }

      return result;
   }

   private void readSubqueryWords(FilterTokenizer ft, StringBuffer buf) throws IOException {
      int parenDepth = 0;

      while(true) {
         int token = ft.nextToken();
         switch (token) {
            case -3:
               buf.append(" ").append(ft.sval).append(" ");
               break;
            case -2:
               buf.append(" ").append(ft.nval.toString()).append(" ");
               break;
            case -1:
               throw this.parseException(0, "unterminated-subquery", (Object[])null, (Exception)null);
            case 33:
               if (ft.nextToken() == 61) {
                  buf.append(" != ");
               } else {
                  ft.pushBack();
                  buf.append(" ! ");
               }
               break;
            case 34:
            case 39:
               buf.append(" ").append((char)token).append(ft.sval).append((char)token).append(" ");
               break;
            case 38:
               if (ft.nextToken() == 38) {
                  buf.append(" && ");
               } else {
                  ft.pushBack();
                  buf.append(" & ");
               }
               break;
            case 40:
               ++parenDepth;
               buf.append(" ( ");
               break;
            case 41:
               --parenDepth;
               if (parenDepth == -1) {
                  return;
               }

               buf.append(" ) ");
               break;
            case 43:
               if (ft.nextToken() == 43) {
                  throw this.parseException(0, "assign-ops", (Object[])null, (Exception)null);
               }

               ft.pushBack();
               buf.append(" + ");
               break;
            case 60:
               if (ft.nextToken() == 61) {
                  buf.append(" <= ");
               } else {
                  ft.pushBack();
                  buf.append(" < ");
               }
               break;
            case 61:
               if (ft.nextToken() != 61) {
                  throw this.parseException(0, "assign-ops", (Object[])null, (Exception)null);
               }

               buf.append(" == ");
               break;
            case 62:
               if (ft.nextToken() == 61) {
                  buf.append(" >= ");
               } else {
                  ft.pushBack();
                  buf.append(" > ");
               }
               break;
            case 124:
               if (ft.nextToken() == 124) {
                  buf.append(" || ");
               } else {
                  ft.pushBack();
                  buf.append(" | ");
               }
               break;
            default:
               buf.append(" ").append((char)token).append(" ");
         }
      }
   }

   private SubqueryNodes parseSubquery(Subquery q, SingleString sstr) {
      if (sstr.range != null) {
         throw this.parseException(0, "subquery-range", (Object[])null, (Exception)null);
      } else {
         Context context = this.startContext(q);
         SubqueryNodes snodes = new SubqueryNodes();
         snodes.sstr = sstr;
         if (sstr.filter != null) {
            context.jdoql = sstr.filter;
            snodes.filter = this.parse();
         }

         String grouping;
         int i;
         if (sstr.result != null) {
            grouping = sstr.result;
            if (!grouping.startsWith("distinct ") && !grouping.startsWith("DISTINCT ")) {
               if (grouping.startsWith("nondistinct ") || grouping.startsWith("NONDISTINCT ")) {
                  snodes.distinct |= 8;
                  grouping = grouping.substring(12).trim();
               }
            } else {
               snodes.distinct |= 4;
               grouping = grouping.substring(9).trim();
            }

            List results = Filters.splitExpressions(grouping, ',', 5);
            snodes.projectionClauses = (String[])((String[])results.toArray(new String[results.size()]));
            snodes.projections = new Node[results.size()];
            context.result = true;

            for(i = 0; i < snodes.projections.length; ++i) {
               context.jdoql = (String)results.get(i);
               snodes.projections[i] = this.parse();
            }

            context.result = false;
         }

         String order;
         if (sstr.grouping != null) {
            grouping = sstr.grouping;
            order = null;
            i = grouping.lastIndexOf(" having ");
            if (i == -1) {
               i = grouping.lastIndexOf(" HAVING ");
            }

            if (i != -1) {
               order = grouping.substring(i + 7).trim();
               grouping = grouping.substring(0, i);
            }

            List groups = Filters.splitExpressions(grouping, ',', 3);
            snodes.groupingClauses = (String[])((String[])groups.toArray(new String[groups.size()]));
            snodes.grouping = new Node[groups.size()];

            for(int i = 0; i < snodes.grouping.length; ++i) {
               context.jdoql = (String)groups.get(i);
               snodes.grouping[i] = this.parse();
            }

            if (order != null) {
               context.jdoql = order;
               snodes.having = this.parse();
            }
         }

         if (sstr.ordering != null) {
            List orders = Filters.splitExpressions(sstr.ordering, ',', 3);
            snodes.ordering = new Node[orders.size()];
            snodes.ascending = new boolean[orders.size()];

            for(i = 0; i < snodes.ordering.length; ++i) {
               order = (String)orders.get(i);
               if (!order.endsWith(" ascending") && !order.endsWith(" ASCENDING")) {
                  if (!order.endsWith(" asc") && !order.endsWith(" ASC")) {
                     if (!order.endsWith(" descending") && !order.endsWith(" DESCENDING")) {
                        if (!order.endsWith(" desc") && !order.endsWith(" DESC")) {
                           throw this.parseException(0, "bad-ordering-dec", new Object[]{order}, (Exception)null);
                        }

                        order = order.substring(0, order.length() - 5).trim();
                        snodes.ascending[i] = false;
                     } else {
                        order = order.substring(0, order.length() - 11).trim();
                        snodes.ascending[i] = false;
                     }
                  } else {
                     order = order.substring(0, order.length() - 4).trim();
                     snodes.ascending[i] = false;
                  }
               } else {
                  order = order.substring(0, order.length() - 10).trim();
                  snodes.ascending[i] = true;
               }

               context.jdoql = order;
               snodes.ordering[i] = this.parse();
            }
         }

         this.endContext();
         return snodes;
      }
   }

   private boolean isMethod(String id, Node result, boolean hasDot) {
      boolean custom = id.startsWith("ext:");
      if (custom) {
         id = id.substring(4);
      }

      if (!hasDot) {
         if (!custom && "contains".equals(id)) {
            result.type = 25;
            return true;
         }

         if (!custom && "isEmpty".equals(id)) {
            result.type = 24;
            return true;
         }

         if (!custom && "size".equals(id)) {
            result.type = 60;
            return true;
         }

         if ("containsKey".equals(id)) {
            result.type = 33;
            return true;
         }

         if ("containsValue".equals(id)) {
            result.type = 34;
            return true;
         }

         if ("get".equals(id)) {
            result.type = 64;
            return true;
         }
      }

      FilterListener filt = this.resolver.getFilterListener(id);
      if (filt != null) {
         result.type = 38;
         result.value = filt;
         return true;
      } else {
         AggregateListener agg = this.resolver.getAggregateListener(id);
         if (agg != null) {
            result.type = 39;
            result.value = agg;
            Context context = this.currentContext();
            return true;
         } else if (!hasDot) {
            if ("matches".equals(id)) {
               result.type = 43;
            } else if ("startsWith".equals(id)) {
               result.type = 44;
            } else if ("endsWith".equals(id)) {
               result.type = 45;
            } else if ("toUpperCase".equals(id)) {
               result.type = 46;
            } else if ("toLowerCase".equals(id)) {
               result.type = 47;
            } else if ("substring".equals(id)) {
               result.type = 48;
            } else if ("indexOf".equals(id)) {
               result.type = 49;
            } else {
               String lower = id.toLowerCase();
               if ("count".equals(lower)) {
                  result.type = 50;
               } else if ("max".equals(lower)) {
                  result.type = 51;
               } else if ("min".equals(lower)) {
                  result.type = 52;
               } else if ("sum".equals(lower)) {
                  result.type = 53;
               } else {
                  if (!"avg".equals(lower)) {
                     if (custom) {
                        throw this.parseException(0, "invalid-custom", new Object[]{id}, (Exception)null);
                     }

                     return false;
                  }

                  result.type = 54;
               }
            }

            return true;
         } else {
            if (!"Math.abs".equals(id) && !"java.lang.Math.abs".equals(id)) {
               if (!"Math.sqrt".equals(id) && !"java.lang.Math.sqrt".equals(id)) {
                  if (!"JDOHelper.getObjectId".equals(id) && !"javax.jdo.JDOHelper.getObjectId".equals(id)) {
                     return false;
                  }

                  result.type = 57;
               } else {
                  result.type = 56;
               }
            } else {
               result.type = 55;
            }

            return true;
         }
      }
   }

   private boolean isReservedWord(String id, Node result) {
      if ("null".equals(id)) {
         result.type = 23;
      } else if ("true".equals(id)) {
         result.type = 28;
         result.value = Boolean.TRUE;
      } else if ("false".equals(id)) {
         result.type = 28;
         result.value = Boolean.FALSE;
      } else if ("instanceof".equals(id)) {
         result.type = 42;
      } else {
         if (!"distinct".equals(id) && !"DISTINCT".equals(id)) {
            return false;
         }

         result.type = 61;
      }

      return true;
   }

   private void recordImplicitParameter(String name) {
      Context context = this.currentContext();
      if (context.result) {
         if (context.resultParams == null) {
            context.resultParams = new LinkedMap(4);
         }

         if (!context.resultParams.containsKey(name)) {
            context.resultParams.put(name, TYPE_OBJECT);
            if (context.implicitParams != null) {
               context.implicitParams.remove(name);
            }
         }
      } else {
         if (context.implicitParams == null) {
            context.implicitParams = new LinkedMap(6);
         }

         if (!context.implicitParams.containsKey(name)) {
            context.implicitParams.put(name, TYPE_OBJECT);
         }
      }

   }

   private Object eval(Node node, boolean traverseNull) {
      Value val1 = null;
      Value val2 = null;
      Expression exp1;
      Expression exp2;
      Path path;
      Class cls;
      switch (node.type) {
         case 1:
            return this.factory.newLiteral(node.value, 1);
         case 2:
            String field = (String)node.value;
            if (node.query == null) {
               String replace = this._meta.getInterfacePropertyAlias(this._type, field);
               if (replace != null) {
                  field = replace;
               }

               if (!this._meta.isAccessibleField(field)) {
                  return this.getVariable(field, false);
               }

               path = this.factory.newPath();
               path.setMetaData(this._meta);
            } else {
               path = this.factory.newPath(node.query);
               path.setMetaData(node.query.getMetaData());
            }

            return this.traversePath(path, field, false, traverseNull);
         case 3:
            return this.factory.newLiteral(node.value, 3);
         case 4:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            this.setImplicitTypes(val1, val2, TYPE_NUMBER);
            return this.factory.multiply(val1, val2);
         case 5:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            this.setImplicitTypes(val1, val2, TYPE_NUMBER);
            return this.factory.divide(val1, val2);
         case 6:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            if (val1.getType() != TYPE_STRING && val2.getType() != TYPE_STRING) {
               this.setImplicitTypes(val1, val2, TYPE_NUMBER);
               return this.factory.add(val1, val2);
            }

            this.setImplicitTypes(val1, val2, TYPE_STRING);
            return this.factory.concat(val1, val2);
         case 7:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            this.setImplicitTypes(val1, val2, TYPE_NUMBER);
            return this.factory.subtract(val1, val2);
         case 8:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            this.setImplicitTypes(val1, val2, TYPE_NUMBER);
            return this.factory.mod(val1, val2);
         case 9:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.greaterThan(val1, val2);
         case 10:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.lessThan(val1, val2);
         case 11:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.greaterThanEqual(val1, val2);
         case 12:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.lessThanEqual(val1, val2);
         case 13:
            try {
               val1 = this.getValue(node.left, 1, traverseNull);
            } catch (ClassCastException var20) {
               exp1 = this.getExpression(node.left, traverseNull);
               if (node.right.value == Boolean.TRUE) {
                  return exp1;
               }

               if (node.right.value == Boolean.FALSE) {
                  return this.factory.not(exp1);
               }

               throw var20;
            }

            try {
               val2 = this.getValue(node.right, 1, traverseNull);
            } catch (ClassCastException var19) {
               exp1 = this.getExpression(node.right, traverseNull);
               if (node.left.value == Boolean.TRUE) {
                  return exp1;
               }

               if (node.left.value == Boolean.FALSE) {
                  return this.factory.not(exp1);
               }

               throw var19;
            }

            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.equal(val1, val2);
         case 14:
            try {
               val1 = this.getValue(node.left, 1, traverseNull);
            } catch (ClassCastException var17) {
               exp1 = this.getExpression(node.left, traverseNull);
               if (node.right.value == Boolean.TRUE) {
                  return this.factory.not(exp1);
               }

               if (node.right.value == Boolean.FALSE) {
                  return exp1;
               }

               throw var17;
            }

            try {
               val2 = this.getValue(node.right, 1, traverseNull);
            } catch (ClassCastException var18) {
               exp1 = this.getExpression(node.right, traverseNull);
               if (node.left.value == Boolean.TRUE) {
                  return this.factory.not(exp1);
               }

               if (node.left.value == Boolean.FALSE) {
                  return exp1;
               }

               throw var18;
            }

            this.setImplicitTypes(val1, val2, (Class)null);
            return this.factory.notEqual(val1, val2);
         case 15:
         case 16:
            exp1 = this.getExpression(node.left, traverseNull);
            exp2 = this.getExpression(node.right, traverseNull);
            return this.factory.and(exp1, exp2);
         case 17:
         case 18:
            exp1 = this.getExpression(node.left, traverseNull);
            exp2 = this.getExpression(node.right, traverseNull);
            return this.factory.or(exp1, exp2);
         case 19:
            val1 = this.getValue(node.right, 1, traverseNull);
            val1 = this.factory.multiply(val1, this.factory.newLiteral(Numbers.valueOf(-1), 1));
            return this.factory.subtract(val1, this.factory.newLiteral(Numbers.valueOf(1), 1));
         case 20:
            return this.factory.not(this.getExpression(node.left, traverseNull));
         case 21:
         case 22:
         case 32:
         default:
            throw this.parseException(1, "bad-tree", (Object[])null, (Exception)null);
         case 23:
            return this.factory.getNull();
         case 24:
            return this.factory.isEmpty(this.getValue(node.left, 2, traverseNull));
         case 25:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            this.setImplicitContainsTypes(val1, val2, 1);
            return this.factory.contains(val1, val2);
         case 26:
            cls = (Class)node.value;
            ClassMetaData meta = this.getMetaData(cls, false);
            val1 = this.getValue(node.left, 0, traverseNull);
            setImplicitType(val1, cls);
            if (meta != null && val1.getMetaData() == null) {
               val1.setMetaData(meta);
            }

            val1 = this.factory.cast(val1, cls);
            if (meta != null) {
               this.addAccessPath(meta);
               val1.setMetaData(meta);
            }

            return val1;
         case 27:
            val1 = this.getValue(node.left, 0, traverseNull);
            if (val1 instanceof Path) {
               path = (Path)val1;
            } else {
               path = this.factory.newPath(val1);
               path.setMetaData(val1.getMetaData());
            }

            return this.traversePath(path, (String)node.right.value, false, traverseNull);
         case 28:
            return this.factory.newLiteral(node.value, 2);
         case 29:
            Object val1;
            if (node.query == null) {
               val1 = this.factory.getThis();
               ((Value)val1).setMetaData(this._meta);
            } else {
               val1 = this.factory.newPath(node.query);
               ((Value)val1).setMetaData(node.query.getMetaData());
            }

            return val1;
         case 30:
            return this.getVariable((String)node.value, false);
         case 31:
            return this.getParameter((String)node.value);
         case 33:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            this.setImplicitContainsTypes(val1, val2, 2);
            return this.factory.containsKey(val1, val2);
         case 34:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            this.setImplicitContainsTypes(val1, val2, 3);
            return this.factory.containsValue(val1, val2);
         case 35:
            val1 = this.getValue(node.left, 2, traverseNull);
            val2 = this.getVariable((String)node.right.value, true);
            if (this.isBound(val2)) {
               val2 = this.getValue(node.right, 1, traverseNull);
               this.setImplicitContainsTypes(val1, val2, 1);
               return this.factory.contains(val1, val2);
            }

            this.bind(val2);
            this.setImplicitContainsTypes(val1, val2, 1);
            return this.factory.bindVariable(val2, val1);
         case 36:
            val1 = this.getValue(node.left, 2, traverseNull);
            val2 = this.getVariable((String)node.right.value, true);
            if (this.isBound(val2)) {
               val2 = this.getValue(node.right, 1, traverseNull);
               this.setImplicitContainsTypes(val1, val2, 2);
               return this.factory.containsKey(val1, val2);
            }

            this.bind(val2);
            this.setImplicitContainsTypes(val1, val2, 2);
            return this.factory.bindKeyVariable(val2, val1);
         case 37:
            val1 = this.getValue(node.left, 2, traverseNull);
            val2 = this.getVariable((String)node.right.value, true);
            if (this.isBound(val2)) {
               val2 = this.getValue(node.right, 1, traverseNull);
               this.setImplicitContainsTypes(val1, val2, 3);
               return this.factory.containsValue(val1, val2);
            }

            this.bind(val2);
            this.setImplicitContainsTypes(val1, val2, 3);
            return this.factory.bindValueVariable(val2, val1);
         case 38:
            FilterListener listener = (FilterListener)node.value;
            if (listener.expectsTarget()) {
               val1 = this.getValue(node.left, 1, traverseNull);
            }

            if (listener.expectsArguments()) {
               val2 = this.getValue(node.right, 1, traverseNull);
            }

            return this.factory.newExtension(listener, val1, val2);
         case 39:
            AggregateListener agg = (AggregateListener)node.value;
            if (agg.expectsArguments()) {
               val1 = this.getValue(node.right, 1, traverseNull);
            }

            return this.factory.newAggregate(agg, val1);
         case 40:
            val1 = this.getValue(node.left, 0, traverseNull);
            val2 = this.getValue(node.right, 0, traverseNull);
            return this.factory.newArgumentList(val1, val2);
         case 41:
            return this.factory.newLiteral(node.value, 0);
         case 42:
            cls = (Class)node.right.value;

            try {
               val1 = this.getValue(node.left, 1, traverseNull);
            } catch (ClassCastException var16) {
               this.getExpression(node.left, traverseNull);
               return this.factory.isInstance(this.factory.newLiteral(Boolean.TRUE, 2), cls);
            }

            return this.factory.isInstance(val1, cls);
         case 43:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            val1.setImplicitType(TYPE_STRING);
            val2.setImplicitType(TYPE_STRING);
            return this.factory.matches(val1, val2, ".", ".*", (String)null);
         case 44:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            val1.setImplicitType(TYPE_STRING);
            val2.setImplicitType(TYPE_STRING);
            return this.factory.startsWith(val1, val2);
         case 45:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            val1.setImplicitType(TYPE_STRING);
            val2.setImplicitType(TYPE_STRING);
            return this.factory.endsWith(val1, val2);
         case 46:
            val1 = this.getValue(node.left, 1, traverseNull);
            val1.setImplicitType(TYPE_STRING);
            return this.factory.toUpperCase(val1);
         case 47:
            val1 = this.getValue(node.left, 1, traverseNull);
            val1.setImplicitType(TYPE_STRING);
            return this.factory.toLowerCase(val1);
         case 48:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            val1.setImplicitType(TYPE_STRING);
            setImplicitType(val2, Integer.TYPE);
            return this.factory.substring(val1, val2);
         case 49:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            val1.setImplicitType(TYPE_STRING);
            if (val2 instanceof Arguments) {
               ((Arguments)val2).getValues()[0].setImplicitType(TYPE_STRING);
            } else {
               val2.setImplicitType(TYPE_STRING);
            }

            return this.factory.indexOf(val1, val2);
         case 50:
            val1 = this.getValue(node.right, 1, traverseNull);
            return this.factory.count(val1);
         case 51:
            val1 = this.getValue(node.right, 1, traverseNull);
            this.assertOrderableValue(val1);
            return this.factory.max(val1);
         case 52:
            val1 = this.getValue(node.right, 1, traverseNull);
            this.assertOrderableValue(val1);
            return this.factory.min(val1);
         case 53:
            val1 = this.getValue(node.right, 1, traverseNull);
            this.assertNumericValue(val1);
            return this.factory.sum(val1);
         case 54:
            val1 = this.getValue(node.right, 1, traverseNull);
            this.assertNumericValue(val1);
            return this.factory.avg(val1);
         case 55:
            val1 = this.getValue(node.right, 1, traverseNull);
            this.assertNumericValue(val1);
            return this.factory.abs(val1);
         case 56:
            val1 = this.getValue(node.right, 1, traverseNull);
            this.assertNumericValue(val1);
            return this.factory.sqrt(val1);
         case 57:
            return this.factory.getObjectId(this.getValue(node.right, 1, traverseNull));
         case 58:
            SubqueryNodes snodes = (SubqueryNodes)node.value;
            QueryExpressions parsed = new QueryExpressions();
            if (snodes.filter != null) {
               parsed.filter = this.getExpression(snodes.filter, false);
            } else {
               parsed.filter = this.factory.emptyExpression();
            }

            parsed.distinct = snodes.distinct;
            int i;
            if (snodes.projections != null) {
               parsed.projectionClauses = snodes.projectionClauses;
               parsed.projections = new Value[snodes.projections.length];

               for(i = 0; i < snodes.projections.length; ++i) {
                  parsed.projections[i] = this.getValue(snodes.projections[i], 1, true);
               }
            }

            if (snodes.grouping != null) {
               parsed.groupingClauses = snodes.groupingClauses;
               parsed.grouping = new Value[snodes.grouping.length];

               for(i = 0; i < snodes.grouping.length; ++i) {
                  parsed.grouping[i] = this.getValue(snodes.grouping[i], 1, true);
               }
            }

            if (snodes.having != null) {
               parsed.having = this.getExpression(snodes.having, false);
            }

            if (snodes.ordering != null) {
               parsed.ordering = new Value[snodes.ordering.length];

               for(i = 0; i < snodes.ordering.length; ++i) {
                  parsed.ordering[i] = this.getValue(snodes.ordering[i], 1, true);
               }
            }

            parsed.ascending = snodes.ascending;
            node.query.setQueryExpressions(parsed);
            return node.query;
         case 59:
            if (node.value.toString().length() == 1) {
               return this.factory.newLiteral(new Character(node.value.toString().charAt(0)), 4);
            }

            return this.factory.newLiteral(node.value, 4);
         case 60:
            return this.factory.size(this.getValue(node.left, 2, traverseNull));
         case 61:
            val1 = this.getValue(node.right, 1, traverseNull);
            return this.factory.distinct(val1);
         case 62:
            return this.getValue(node.right, 1, traverseNull);
         case 63:
            val1 = this.getValue(node.right, 1, traverseNull);
            return this.factory.multiply(val1, this.factory.newLiteral(Numbers.valueOf(-1), 1));
         case 64:
            val1 = this.getValue(node.left, 1, traverseNull);
            val2 = this.getValue(node.right, 1, traverseNull);
            return this.factory.getMapValue(val1, val2);
         case 65:
            val1 = this.getValue(node.left, 2, traverseNull);
            val2 = this.getVariable((String)node.right.value, true);
            if (this.isBound(val2)) {
               val2 = this.getValue(node.right, 1, traverseNull);
               return this.factory.getMapValue(val1, val2);
            } else {
               this.bind(val2);
               return this.factory.bindValueVariable(val2, val1);
            }
      }
   }

   private void assertNumericValue(Value val) {
      Class type = val.getType();
      if (type != null && type != TYPE_OBJECT) {
         if (!TYPE_NUMBER.isAssignableFrom(Filters.wrap(type))) {
            throw new UserException(_loc.get("not-numeric", type));
         }
      } else {
         val.setImplicitType(TYPE_NUMBER);
      }

   }

   private void assertOrderableValue(Value val) {
      Class type = val.getType();
      if (type != null && type != TYPE_OBJECT) {
         Class wrappedType = Filters.wrap(type);
         if (!TYPE_NUMBER.isAssignableFrom(wrappedType) && !TYPE_STRING.isAssignableFrom(wrappedType) && !TYPE_DATE.isAssignableFrom(wrappedType) && !TYPE_CALENDAR.isAssignableFrom(wrappedType)) {
            throw new UserException(_loc.get("not-orderable", type));
         }
      } else {
         val.setImplicitType(TYPE_NUMBER);
      }

   }

   private Value getValue(Node node, int handleVar, boolean traverseNull) {
      Value val = (Value)this.eval(node, traverseNull);
      if (val.isVariable()) {
         if (handleVar == 1 && !(val instanceof Path)) {
            Path path = this.factory.newPath(val);
            path.setMetaData(val.getMetaData());
            return path;
         }

         if (handleVar == 2) {
            throw this.parseException(0, "unexpected-var", new Object[]{node.value}, (Exception)null);
         }
      }

      return val;
   }

   private Expression getExpression(Node node, boolean traverseNull) {
      Object exp = this.eval(node, traverseNull);
      if (!(exp instanceof Expression)) {
         Value val = (Value)exp;
         Class type = val.getType();
         if (type != Boolean.TYPE && type != Boolean.class && type != Object.class) {
            throw this.parseException(0, "not-boolean", (Object[])null, (Exception)null);
         } else {
            return this.factory.asExpression(val);
         }
      } else {
         return (Expression)exp;
      }
   }

   private Parameter getParameter(String id) {
      if (this._seenParams != null && this._seenParams.containsKey(id)) {
         return (Parameter)this._seenParams.get(id);
      } else {
         Class type = this.getDeclaredParameterType(id);
         ClassMetaData meta = null;
         int index = -1;
         if (type == null) {
            type = TYPE_OBJECT;
         } else {
            meta = this.getMetaData(type, false);
            index = this.getDeclaredParameterIndex(id);
         }

         if (meta != null) {
            this.addAccessPath(meta);
         }

         Parameter param = this.factory.newParameter(id, type);
         param.setMetaData(meta);
         param.setIndex(index);
         if (this._seenParams == null) {
            this._seenParams = new HashMap();
         }

         this._seenParams.put(id, param);
         return param;
      }
   }

   private static int f(int token) {
      switch (token) {
         case 0:
            return 0;
         case 1:
         case 2:
         case 3:
         case 23:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 41:
         case 58:
         case 59:
            return 18;
         case 4:
         case 5:
         case 8:
         case 20:
         case 24:
         case 25:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 43:
         case 44:
         case 45:
         case 60:
         case 64:
         case 65:
            return 13;
         case 6:
         case 7:
            return 11;
         case 9:
         case 10:
         case 11:
         case 12:
            return 9;
         case 13:
         case 14:
         case 42:
            return 7;
         case 15:
         case 16:
            return 4;
         case 17:
         case 18:
         case 40:
            return 3;
         case 19:
         case 26:
         case 62:
         case 63:
            return 15;
         case 21:
            return 0;
         case 22:
            return 18;
         case 27:
            return 16;
         case 38:
         case 39:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
            return 17;
         case 61:
            return 1;
         default:
            return -1;
      }
   }

   private static int g(int token) {
      switch (token) {
         case 0:
            return 0;
         case 1:
         case 2:
         case 3:
         case 20:
         case 23:
         case 24:
         case 25:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 41:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 59:
         case 60:
         case 64:
         case 65:
            return 16;
         case 4:
         case 5:
         case 8:
            return 12;
         case 6:
         case 7:
            return 10;
         case 9:
         case 10:
         case 11:
         case 12:
            return 8;
         case 13:
         case 14:
         case 42:
            return 6;
         case 15:
         case 16:
            return 5;
         case 17:
         case 18:
         case 40:
            return 2;
         case 19:
         case 26:
         case 62:
         case 63:
            return 14;
         case 21:
            return 18;
         case 22:
            return 0;
         case 27:
            return 15;
         case 61:
            return 1;
         default:
            return -1;
      }
   }

   public static void main(String[] args) throws Exception {
      OpenJPAConfiguration conf = new OpenJPAConfigurationImpl();
      final ClassMetaData meta = conf.newMetaDataRepositoryInstance().getMetaData(Class.forName(args[0]), (ClassLoader)null, true);
      JDOQLExpressionBuilder parser = new JDOQLExpressionBuilder((ExpressionFactory)null, meta, meta.getDescribedType(), new Resolver() {
         public Class classForName(String name, String[] imports) {
            try {
               return Strings.toClass(name, (ClassLoader)null);
            } catch (RuntimeException var4) {
               return null;
            }
         }

         public FilterListener getFilterListener(String tag) {
            return null;
         }

         public AggregateListener getAggregateListener(String tag) {
            return null;
         }

         public OpenJPAConfiguration getConfiguration() {
            return meta.getRepository().getConfiguration();
         }

         public QueryContext getQueryContext() {
            return null;
         }
      });
      parser.currentContext().jdoql = args[1];
      System.out.println(parser.parse());
   }

   private static class Node implements Serializable {
      public int type;
      public Object value;
      public Subquery query;
      public Node left;
      public Node right;

      private Node() {
         this.type = 0;
         this.value = null;
         this.query = null;
         this.left = null;
         this.right = null;
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         this.toString(buf, 0);
         return buf.toString();
      }

      private void toString(StringBuffer buf, int tabs) {
         this.appendTabs(buf, tabs);
         buf.append("<node type=\"").append(JDOQLExpressionBuilder.TYPE_LOOKUP[this.type]).append("\">\n");
         this.appendTabs(buf, tabs + 1);
         buf.append("<value>").append(this.value).append("</value>\n");
         buf.append("<query>").append(this.query).append("</query>\n");
         if (this.left != null) {
            this.appendTabs(buf, tabs + 1);
            buf.append("<left>\n");
            this.left.toString(buf, tabs + 2);
            this.appendTabs(buf, tabs + 1);
            buf.append("</left>\n");
         }

         if (this.right != null) {
            this.appendTabs(buf, tabs + 1);
            buf.append("<right>\n");
            this.right.toString(buf, tabs + 2);
            this.appendTabs(buf, tabs + 1);
            buf.append("</right>\n");
         }

         this.appendTabs(buf, tabs);
         buf.append("</node>\n");
      }

      private void appendTabs(StringBuffer buf, int tabs) {
         for(int i = 0; i < tabs; ++i) {
            buf.append("  ");
         }

      }

      // $FF: synthetic method
      Node(Object x0) {
         this();
      }
   }

   private static class SubqueryNodes {
      public SingleString sstr;
      public int distinct;
      public Node[] projections;
      public String[] projectionClauses;
      public Node filter;
      public Node[] grouping;
      public String[] groupingClauses;
      public Node having;
      public Node[] ordering;
      public boolean[] ascending;

      private SubqueryNodes() {
         this.sstr = null;
         this.distinct = 2;
         this.projections = null;
         this.projectionClauses = null;
         this.filter = null;
         this.grouping = null;
         this.groupingClauses = null;
         this.having = null;
         this.ordering = null;
         this.ascending = null;
      }

      // $FF: synthetic method
      SubqueryNodes(Object x0) {
         this();
      }
   }

   private static class Context {
      public Subquery subquery;
      public LinkedMap implicitParams;
      public LinkedMap resultParams;
      public String jdoql;
      public int hint;
      public boolean result;

      private Context() {
         this.subquery = null;
         this.implicitParams = null;
         this.resultParams = null;
         this.jdoql = null;
         this.hint = 0;
         this.result = false;
      }

      // $FF: synthetic method
      Context(Object x0) {
         this();
      }
   }
}
