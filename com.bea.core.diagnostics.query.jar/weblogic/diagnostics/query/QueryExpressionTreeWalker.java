package weblogic.diagnostics.query;

import antlr.CommonToken;
import antlr.NoViableAltException;
import antlr.RecognitionException;
import antlr.TreeParser;
import antlr.collections.AST;
import java.util.Iterator;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.querytree.QueryNode;
import weblogic.diagnostics.querytree.QueryNodeType;
import weblogic.diagnostics.querytree.impl.QueryNodeImpl;
import weblogic.diagnostics.querytree.impl.QueryValueNodeImpl;
import weblogic.diagnostics.querytree.impl.QueryVariableNodeImpl;

public class QueryExpressionTreeWalker extends TreeParser implements QueryExpressionTreeWalkerTokenTypes {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticQuery");
   private QueryExecutionTrace queryExecutionTrace;
   private VariableResolver variableResolver;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "CONSTANT_BOOLEAN", "CONSTANT_NUMBER", "STRING_LITERAL", "VARIABLE_NAME", "SET_NODE", "NESTED_LOGICAL", "NESTED_ARITHMETIC", "\"AND\"", "\"OR\"", "\"NOT\"", "\"LIKE\"", "\"MATCHES\"", "\"IN\"", "END_OF_QUERY", "LT", "GT", "LE", "GE", "EQ", "NE", "LPAREN", "RPAREN", "BITWISE_OR", "BITWISE_AND", "COMMA", "WS", "SPACE", "PLUS", "MINUS", "DIGIT", "ASCII_VARNAME_START", "UNICODE_CHAR", "ALIAS_DELIMITER_START", "DOT", "NUMBER_SUFFIXES", "DOUBLE_SUFFIX", "FLOAT_SUFFIX", "LONG_SUFFIX", "EXPONENT"};

   public QueryExpressionTreeWalker(VariableResolver vr) {
      this();
      this.variableResolver = vr;
   }

   public QueryExecutionTrace getQueryExecutionTrace() {
      return this.queryExecutionTrace;
   }

   public QueryExpressionTreeWalker() {
      this.queryExecutionTrace = new QueryExecutionTrace();
      this.tokenNames = _tokenNames;
   }

   public final QueryNode buildTree(AST _t) throws RecognitionException {
      QueryNode node = null;
      AST buildTree_AST_in = _t == ASTNULL ? null : _t;
      if (_t == null) {
         _t = ASTNULL;
      }

      QueryNode n1;
      QueryNode n2;
      Object __t10;
      AST _t;
      switch (((AST)_t).getType()) {
         case 9:
            __t10 = _t;
            this.match((AST)_t, 9);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildTree(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            node = n1;
            break;
         case 10:
         case 17:
         default:
            throw new NoViableAltException((AST)_t);
         case 11:
            __t10 = _t;
            this.match((AST)_t, 11);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildTree(_t);
            _t = this._retTree;
            n2 = this.buildTree(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building AND node: n1=" + n1 + " n2=" + n2);
            }

            node = new QueryNodeImpl(QueryNodeType.AND, n1, n2);
            break;
         case 12:
            __t10 = _t;
            this.match((AST)_t, 12);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildTree(_t);
            _t = this._retTree;
            n2 = this.buildTree(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building OR node: n1=" + n1 + " n2=" + n2);
            }

            node = new QueryNodeImpl(QueryNodeType.OR, n1, n2);
            break;
         case 13:
            __t10 = _t;
            this.match((AST)_t, 13);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildTree(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building NOT node");
            }

            node = new QueryNodeImpl(QueryNodeType.NOT, n1);
            break;
         case 14:
            __t10 = _t;
            this.match((AST)_t, 14);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            n2 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building LIKE node: n1=" + n1 + " n2=" + n2);
            }

            node = new QueryNodeImpl(QueryNodeType.LIKE, n1, n2);
            break;
         case 15:
            __t10 = _t;
            this.match((AST)_t, 15);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            n2 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building MATCHES node: n1=" + n1 + " n2=" + n2);
            }

            node = new QueryNodeImpl(QueryNodeType.MATCHES, n1, n2);
            break;
         case 16:
            __t10 = _t;
            this.match((AST)_t, 16);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            n2 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building IN node: n1=" + n1 + " n2=" + n2);
            }

            node = new QueryNodeImpl(QueryNodeType.IN, n1, n2);
            break;
         case 18:
            __t10 = _t;
            this.match((AST)_t, 18);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            n2 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building LT node: " + n1 + " n2=" + n2);
            }

            node = new QueryNodeImpl(QueryNodeType.LT, n1, n2);
            break;
         case 19:
            __t10 = _t;
            this.match((AST)_t, 19);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            n2 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building GT node: n1=" + n1 + " n2=" + n2);
            }

            node = new QueryNodeImpl(QueryNodeType.GT, n1, n2);
            break;
         case 20:
            __t10 = _t;
            this.match((AST)_t, 20);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            n2 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building LE node: " + n1 + " n2=" + n2);
            }

            node = new QueryNodeImpl(QueryNodeType.LE, n1, n2);
            break;
         case 21:
            __t10 = _t;
            this.match((AST)_t, 21);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            n2 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building GE node: n1=" + n1 + " n2=" + n2);
            }

            node = new QueryNodeImpl(QueryNodeType.GE, n1, n2);
            break;
         case 22:
            __t10 = _t;
            this.match((AST)_t, 22);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            n2 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building EQ node: n1=" + n1 + " n2=" + n2);
            }

            node = new QueryNodeImpl(QueryNodeType.EQ, n1, n2);
            break;
         case 23:
            __t10 = _t;
            this.match((AST)_t, 23);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            n2 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building NE node: n1=" + n1 + " n2=" + n2);
            }

            node = new QueryNodeImpl(QueryNodeType.NE, n1, n2);
      }

      this._retTree = _t;
      return (QueryNode)node;
   }

   public final QueryNode buildBitwiseExpr(AST _t) throws RecognitionException {
      QueryNode node = null;
      AST buildBitwiseExpr_AST_in = _t == ASTNULL ? null : _t;
      if (_t == null) {
         _t = ASTNULL;
      }

      QueryNode n1;
      QueryNode n2;
      Object __t35;
      AST _t;
      switch (((AST)_t).getType()) {
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
            node = this.leafExpr((AST)_t);
            _t = this._retTree;
            break;
         case 9:
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
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         default:
            throw new NoViableAltException((AST)_t);
         case 10:
            __t35 = _t;
            this.match((AST)_t, 10);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t35).getNextSibling();
            node = n1;
            break;
         case 26:
            __t35 = _t;
            this.match((AST)_t, 26);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            n2 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t35).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building BITWISE_OR node");
            }

            node = new QueryNodeImpl(QueryNodeType.BITWISE_OR, n1, n2);
            break;
         case 27:
            __t35 = _t;
            this.match((AST)_t, 27);
            _t = ((AST)_t).getFirstChild();
            n1 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            n2 = this.buildBitwiseExpr(_t);
            _t = this._retTree;
            _t = ((AST)__t35).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building BITWISE_AND node");
            }

            node = new QueryNodeImpl(QueryNodeType.BITWISE_AND, n1, n2);
      }

      this._retTree = _t;
      return (QueryNode)node;
   }

   public final boolean evaluateQuery(AST _t) throws RecognitionException, QueryExecutionException {
      boolean r = false;
      AST evaluateQuery_AST_in = _t == ASTNULL ? null : _t;
      if (_t == null) {
         _t = ASTNULL;
      }

      boolean a;
      AtomNode at1;
      AtomNode at2;
      Object __t17;
      AST _t;
      switch (((AST)_t).getType()) {
         case 9:
            __t17 = _t;
            this.match((AST)_t, 9);
            _t = ((AST)_t).getFirstChild();
            a = this.evaluateQuery(_t);
            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            r = a;
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated NESTED_LOGICAL to " + a);
            }
            break;
         case 10:
         case 17:
         default:
            throw new NoViableAltException((AST)_t);
         case 11:
            __t17 = _t;
            this.match((AST)_t, 11);
            _t = ((AST)_t).getFirstChild();
            r = this.evaluateQuery(_t);
            _t = this._retTree;
            if (r) {
               r = this.evaluateQuery(_t);
            }

            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluating AND to " + r);
            }
            break;
         case 12:
            __t17 = _t;
            this.match((AST)_t, 12);
            _t = ((AST)_t).getFirstChild();
            r = this.evaluateQuery(_t);
            _t = this._retTree;
            if (!r) {
               r = this.evaluateQuery(_t);
            }

            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated OR to " + r);
            }
            break;
         case 13:
            __t17 = _t;
            this.match((AST)_t, 13);
            _t = ((AST)_t).getFirstChild();
            a = this.evaluateQuery(_t);
            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluating NOT " + a + " to " + !a);
            }

            r = !a;
            break;
         case 14:
            __t17 = _t;
            this.match((AST)_t, 14);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atomicExpression(_t);
            _t = this._retTree;
            at2 = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            r = ExpressionEvaluator.evaluateLike(at1, at2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated " + at1.getText() + " LIKE " + at2.getText() + " to " + r);
            }
            break;
         case 15:
            __t17 = _t;
            this.match((AST)_t, 15);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atomicExpression(_t);
            _t = this._retTree;
            at2 = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            r = ExpressionEvaluator.evaluateMatches(at1, at2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated " + at1.getText() + " MATCHES " + at2.getText() + " to " + r);
            }
            break;
         case 16:
            __t17 = _t;
            this.match((AST)_t, 16);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atomicExpression(_t);
            _t = this._retTree;
            at2 = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            r = ExpressionEvaluator.evaluateIn(at1, at2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated " + at1.getText() + " IN " + at2.getText() + " to " + r);
            }
            break;
         case 18:
            __t17 = _t;
            this.match((AST)_t, 18);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atomicExpression(_t);
            _t = this._retTree;
            at2 = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            r = ExpressionEvaluator.evaluateLessThan(at1, at2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated " + at1.getText() + " < " + at2.getText() + " to " + r);
            }
            break;
         case 19:
            __t17 = _t;
            this.match((AST)_t, 19);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atomicExpression(_t);
            _t = this._retTree;
            at2 = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            r = ExpressionEvaluator.evaluateGreaterThan(at1, at2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated " + at1.getText() + " > " + at2.getText() + " to " + r);
            }
            break;
         case 20:
            __t17 = _t;
            this.match((AST)_t, 20);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atomicExpression(_t);
            _t = this._retTree;
            at2 = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            r = ExpressionEvaluator.evaluateLessThanEquals(at1, at2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated " + at1.getText() + " <= " + at2.getText() + " to " + r);
            }
            break;
         case 21:
            __t17 = _t;
            this.match((AST)_t, 21);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atomicExpression(_t);
            _t = this._retTree;
            at2 = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            r = ExpressionEvaluator.evaluateGreaterThanEquals(at1, at2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated " + at1.getText() + " >= " + at2.getText() + " to " + r);
            }
            break;
         case 22:
            __t17 = _t;
            this.match((AST)_t, 22);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atomicExpression(_t);
            _t = this._retTree;
            at2 = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            r = ExpressionEvaluator.evaluateEquals(at1, at2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated " + at1.getText() + " = " + at2.getText() + " to " + r);
            }
            break;
         case 23:
            __t17 = _t;
            this.match((AST)_t, 23);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atomicExpression(_t);
            _t = this._retTree;
            at2 = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t17).getNextSibling();
            r = ExpressionEvaluator.evaluateNotEquals(at1, at2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated " + at1.getText() + " != " + at2.getText() + " to " + r);
            }
      }

      this._retTree = _t;
      return r;
   }

   public final AtomNode atomicExpression(AST _t) throws RecognitionException, QueryExecutionException {
      AST atomicExpression_AST_in = _t == ASTNULL ? null : _t;
      if (_t == null) {
         _t = ASTNULL;
      }

      Object an;
      AtomNode at1;
      AtomNode at2;
      Object __t30;
      long l;
      CommonToken t;
      AST _t;
      switch (((AST)_t).getType()) {
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
            an = this.atom((AST)_t);
            _t = this._retTree;
            break;
         case 9:
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
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         default:
            throw new NoViableAltException((AST)_t);
         case 10:
            __t30 = _t;
            this.match((AST)_t, 10);
            _t = ((AST)_t).getFirstChild();
            an = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t30).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated NESTED_ARITHMETIC to " + an);
            }
            break;
         case 26:
            __t30 = _t;
            this.match((AST)_t, 26);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atomicExpression(_t);
            _t = this._retTree;
            at2 = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t30).getNextSibling();
            l = ExpressionEvaluator.evaluateBitwiseOr(at1, at2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated " + at1.getText() + " | " + at2.getText() + " = " + l);
            }

            t = new CommonToken(27, l + "L");
            an = new ConstantNumberNode(t);
            break;
         case 27:
            __t30 = _t;
            this.match((AST)_t, 27);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atomicExpression(_t);
            _t = this._retTree;
            at2 = this.atomicExpression(_t);
            _t = this._retTree;
            _t = ((AST)__t30).getNextSibling();
            l = ExpressionEvaluator.evaluateBitwiseAnd(at1, at2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Evaluated " + at1.getText() + " & " + at2.getText() + " = " + l);
            }

            t = new CommonToken(27, l + "L");
            an = new ConstantNumberNode(t);
      }

      this._retTree = _t;
      return (AtomNode)an;
   }

   public final AtomNode atom(AST _t) throws RecognitionException {
      AtomNode an = (AtomNode)((AtomNode)_t);
      AST atom_AST_in = _t == ASTNULL ? null : _t;
      if (_t == null) {
         _t = ASTNULL;
      }

      AST _t;
      switch (((AST)_t).getType()) {
         case 4:
            this.match((AST)_t, 4);
            _t = ((AST)_t).getNextSibling();
            break;
         case 5:
            this.match((AST)_t, 5);
            _t = ((AST)_t).getNextSibling();
            break;
         case 6:
            this.match((AST)_t, 6);
            _t = ((AST)_t).getNextSibling();
            break;
         case 7:
            this.match((AST)_t, 7);
            _t = ((AST)_t).getNextSibling();
            VariableNode varNode = (VariableNode)an;
            varNode.setVariableResolver(this.variableResolver);
            varNode.setQueryExecutionTrace(this.queryExecutionTrace);
            break;
         case 8:
            this.match((AST)_t, 8);
            _t = ((AST)_t).getNextSibling();
            break;
         default:
            throw new NoViableAltException((AST)_t);
      }

      this._retTree = _t;
      return an;
   }

   public final QueryNode leafExpr(AST _t) throws RecognitionException {
      QueryNode node = null;
      AST leafExpr_AST_in = _t == ASTNULL ? null : _t;
      AtomNode an = (AtomNode)_t;
      if (_t == null) {
         _t = ASTNULL;
      }

      AST _t;
      String varName;
      label60:
      switch (((AST)_t).getType()) {
         case 4:
            this.match((AST)_t, 4);
            _t = ((AST)_t).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building VALUE node with: " + an);
            }

            varName = an.toString();
            node = new QueryValueNodeImpl(varName);
            break;
         case 5:
            this.match((AST)_t, 5);
            _t = ((AST)_t).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building VALUE node with: " + an);
            }

            Object val = an.toString();

            try {
               val = an.getValue();
            } catch (UnknownVariableException var9) {
            }

            node = new QueryValueNodeImpl(val);
            break;
         case 6:
            this.match((AST)_t, 6);
            _t = ((AST)_t).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building VALUE node with: " + an);
            }

            varName = an.toString();
            node = new QueryValueNodeImpl(varName);
            break;
         case 7:
            this.match((AST)_t, 7);
            _t = ((AST)_t).getNextSibling();
            varName = an.toString();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building VAR node: " + varName);
            }

            node = new QueryVariableNodeImpl(varName);
            break;
         case 8:
            this.match((AST)_t, 8);
            _t = ((AST)_t).getNextSibling();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Building SET node with: " + an);
            }

            try {
               Set valSet = (Set)an.getValue();
               node = new QueryNodeImpl(QueryNodeType.VALUEARR);
               Iterator var7 = valSet.iterator();

               while(true) {
                  if (!var7.hasNext()) {
                     break label60;
                  }

                  Object val = var7.next();
                  ((QueryNodeImpl)node).addChild(new QueryValueNodeImpl(val));
               }
            } catch (UnknownVariableException var10) {
               break;
            }
         default:
            throw new NoViableAltException((AST)_t);
      }

      this._retTree = _t;
      return (QueryNode)node;
   }
}
