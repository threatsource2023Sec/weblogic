package weblogic.diagnostics.query;

import antlr.NoViableAltException;
import antlr.RecognitionException;
import antlr.TreeParser;
import antlr.collections.AST;

public class QueryExpressionCompiler extends TreeParser implements QueryExpressionCompilerTokenTypes {
   private QueryCompiler qc;
   private VariableDeclarator varDecl;
   private VariableIndexResolver varIndexResolver;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "CONSTANT_BOOLEAN", "CONSTANT_NUMBER", "STRING_LITERAL", "VARIABLE_NAME", "SET_NODE", "NESTED_LOGICAL", "NESTED_ARITHMETIC", "\"AND\"", "\"OR\"", "\"NOT\"", "\"LIKE\"", "\"MATCHES\"", "\"IN\"", "END_OF_QUERY", "LT", "GT", "LE", "GE", "EQ", "NE", "LPAREN", "RPAREN", "BITWISE_OR", "BITWISE_AND", "COMMA", "WS", "SPACE", "PLUS", "MINUS", "DIGIT", "ASCII_VARNAME_START", "UNICODE_CHAR", "ALIAS_DELIMITER_START", "DOT", "NUMBER_SUFFIXES", "DOUBLE_SUFFIX", "FLOAT_SUFFIX", "LONG_SUFFIX", "EXPONENT"};

   public void initialize(VariableDeclarator vdl, VariableIndexResolver vir) {
      this.qc = new QueryCompiler(Query.class.getClassLoader(), vdl, vir);
      this.varDecl = vdl;
      this.varIndexResolver = vir;
      this.qc.beginExecuteQueryMethodCompilation();
   }

   public Query getCompiledQuery() throws QueryParsingException {
      this.qc.endExecuteQueryMethodCompilation();
      return this.qc.getCompiledQuery();
   }

   public QueryExpressionCompiler() {
      this.tokenNames = _tokenNames;
   }

   public final void compileQuery(AST _t) throws RecognitionException, QueryExecutionException {
      AST compileQuery_AST_in = _t == ASTNULL ? null : _t;
      if (_t == null) {
         _t = ASTNULL;
      }

      AST a;
      AST b;
      AtomNode at1;
      AtomNode at2;
      Object __t10;
      AST _t;
      switch (((AST)_t).getType()) {
         case 9:
            __t10 = _t;
            this.match((AST)_t, 9);
            _t = ((AST)_t).getFirstChild();
            this.compileQuery(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            break;
         case 10:
         case 17:
         default:
            throw new NoViableAltException((AST)_t);
         case 11:
            __t10 = _t;
            this.match((AST)_t, 11);
            _t = ((AST)_t).getFirstChild();
            a = this.binaryOperand(_t);
            _t = this._retTree;
            b = this.binaryOperand(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitAnd(this, a, b);
            break;
         case 12:
            __t10 = _t;
            this.match((AST)_t, 12);
            _t = ((AST)_t).getFirstChild();
            a = this.binaryOperand(_t);
            _t = this._retTree;
            b = this.binaryOperand(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitOr(this, a, b);
            break;
         case 13:
            __t10 = _t;
            this.match((AST)_t, 13);
            _t = ((AST)_t).getFirstChild();
            a = this.binaryOperand(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitNot(this, a);
            break;
         case 14:
            __t10 = _t;
            this.match((AST)_t, 14);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atom(_t);
            _t = this._retTree;
            at2 = this.atom(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitLike(at1, at2);
            break;
         case 15:
            __t10 = _t;
            this.match((AST)_t, 15);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atom(_t);
            _t = this._retTree;
            at2 = this.atom(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitMatches(at1, at2);
            break;
         case 16:
            __t10 = _t;
            this.match((AST)_t, 16);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atom(_t);
            _t = this._retTree;
            at2 = this.atom(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitIn(at1, at2);
            break;
         case 18:
            __t10 = _t;
            this.match((AST)_t, 18);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atom(_t);
            _t = this._retTree;
            at2 = this.atom(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitLessThan(at1, at2);
            break;
         case 19:
            __t10 = _t;
            this.match((AST)_t, 19);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atom(_t);
            _t = this._retTree;
            at2 = this.atom(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitGreaterThan(at1, at2);
            break;
         case 20:
            __t10 = _t;
            this.match((AST)_t, 20);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atom(_t);
            _t = this._retTree;
            at2 = this.atom(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitLessThanEquals(at1, at2);
            break;
         case 21:
            __t10 = _t;
            this.match((AST)_t, 21);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atom(_t);
            _t = this._retTree;
            at2 = this.atom(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitGreaterThanEquals(at1, at2);
            break;
         case 22:
            __t10 = _t;
            this.match((AST)_t, 22);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atom(_t);
            _t = this._retTree;
            at2 = this.atom(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitEquals(at1, at2);
            break;
         case 23:
            __t10 = _t;
            this.match((AST)_t, 23);
            _t = ((AST)_t).getFirstChild();
            at1 = this.atom(_t);
            _t = this._retTree;
            at2 = this.atom(_t);
            _t = this._retTree;
            _t = ((AST)__t10).getNextSibling();
            this.qc.visitNotEquals(at1, at2);
      }

      this._retTree = _t;
   }

   public final AST binaryOperand(AST _t) throws RecognitionException {
      AST r = _t;
      AST binaryOperand_AST_in = _t == ASTNULL ? null : _t;
      if (_t == null) {
         _t = ASTNULL;
      }

      AST _t;
      switch (((AST)_t).getType()) {
         case 9:
            this.match((AST)_t, 9);
            _t = ((AST)_t).getNextSibling();
            break;
         case 10:
         case 17:
         default:
            throw new NoViableAltException((AST)_t);
         case 11:
            this.match((AST)_t, 11);
            _t = ((AST)_t).getNextSibling();
            break;
         case 12:
            this.match((AST)_t, 12);
            _t = ((AST)_t).getNextSibling();
            break;
         case 13:
            this.match((AST)_t, 13);
            _t = ((AST)_t).getNextSibling();
            break;
         case 14:
            this.match((AST)_t, 14);
            _t = ((AST)_t).getNextSibling();
            break;
         case 15:
            this.match((AST)_t, 15);
            _t = ((AST)_t).getNextSibling();
            break;
         case 16:
            this.match((AST)_t, 16);
            _t = ((AST)_t).getNextSibling();
            break;
         case 18:
            this.match((AST)_t, 18);
            _t = ((AST)_t).getNextSibling();
            break;
         case 19:
            this.match((AST)_t, 19);
            _t = ((AST)_t).getNextSibling();
            break;
         case 20:
            this.match((AST)_t, 20);
            _t = ((AST)_t).getNextSibling();
            break;
         case 21:
            this.match((AST)_t, 21);
            _t = ((AST)_t).getNextSibling();
            break;
         case 22:
            this.match((AST)_t, 22);
            _t = ((AST)_t).getNextSibling();
            break;
         case 23:
            this.match((AST)_t, 23);
            _t = ((AST)_t).getNextSibling();
      }

      this._retTree = _t;
      return (AST)r;
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
}
