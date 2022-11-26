package com.bea.core.repackaged.springframework.expression.spel.standard;

import com.bea.core.repackaged.springframework.expression.ParseException;
import com.bea.core.repackaged.springframework.expression.ParserContext;
import com.bea.core.repackaged.springframework.expression.common.TemplateAwareExpressionParser;
import com.bea.core.repackaged.springframework.expression.spel.InternalParseException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.expression.spel.SpelParseException;
import com.bea.core.repackaged.springframework.expression.spel.SpelParserConfiguration;
import com.bea.core.repackaged.springframework.expression.spel.ast.Assign;
import com.bea.core.repackaged.springframework.expression.spel.ast.BeanReference;
import com.bea.core.repackaged.springframework.expression.spel.ast.BooleanLiteral;
import com.bea.core.repackaged.springframework.expression.spel.ast.CompoundExpression;
import com.bea.core.repackaged.springframework.expression.spel.ast.ConstructorReference;
import com.bea.core.repackaged.springframework.expression.spel.ast.Elvis;
import com.bea.core.repackaged.springframework.expression.spel.ast.FunctionReference;
import com.bea.core.repackaged.springframework.expression.spel.ast.Identifier;
import com.bea.core.repackaged.springframework.expression.spel.ast.Indexer;
import com.bea.core.repackaged.springframework.expression.spel.ast.InlineList;
import com.bea.core.repackaged.springframework.expression.spel.ast.InlineMap;
import com.bea.core.repackaged.springframework.expression.spel.ast.Literal;
import com.bea.core.repackaged.springframework.expression.spel.ast.MethodReference;
import com.bea.core.repackaged.springframework.expression.spel.ast.NullLiteral;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpAnd;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpDec;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpDivide;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpEQ;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpGE;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpGT;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpInc;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpLE;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpLT;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpMinus;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpModulus;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpMultiply;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpNE;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpOr;
import com.bea.core.repackaged.springframework.expression.spel.ast.OpPlus;
import com.bea.core.repackaged.springframework.expression.spel.ast.OperatorBetween;
import com.bea.core.repackaged.springframework.expression.spel.ast.OperatorInstanceof;
import com.bea.core.repackaged.springframework.expression.spel.ast.OperatorMatches;
import com.bea.core.repackaged.springframework.expression.spel.ast.OperatorNot;
import com.bea.core.repackaged.springframework.expression.spel.ast.OperatorPower;
import com.bea.core.repackaged.springframework.expression.spel.ast.Projection;
import com.bea.core.repackaged.springframework.expression.spel.ast.PropertyOrFieldReference;
import com.bea.core.repackaged.springframework.expression.spel.ast.QualifiedIdentifier;
import com.bea.core.repackaged.springframework.expression.spel.ast.Selection;
import com.bea.core.repackaged.springframework.expression.spel.ast.SpelNodeImpl;
import com.bea.core.repackaged.springframework.expression.spel.ast.StringLiteral;
import com.bea.core.repackaged.springframework.expression.spel.ast.Ternary;
import com.bea.core.repackaged.springframework.expression.spel.ast.TypeReference;
import com.bea.core.repackaged.springframework.expression.spel.ast.VariableReference;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;

class InternalSpelExpressionParser extends TemplateAwareExpressionParser {
   private static final Pattern VALID_QUALIFIED_ID_PATTERN = Pattern.compile("[\\p{L}\\p{N}_$]+");
   private final SpelParserConfiguration configuration;
   private final Deque constructedNodes = new ArrayDeque();
   private String expressionString = "";
   private List tokenStream = Collections.emptyList();
   private int tokenStreamLength;
   private int tokenStreamPointer;

   public InternalSpelExpressionParser(SpelParserConfiguration configuration) {
      this.configuration = configuration;
   }

   protected SpelExpression doParseExpression(String expressionString, @Nullable ParserContext context) throws ParseException {
      try {
         this.expressionString = expressionString;
         Tokenizer tokenizer = new Tokenizer(expressionString);
         this.tokenStream = tokenizer.process();
         this.tokenStreamLength = this.tokenStream.size();
         this.tokenStreamPointer = 0;
         this.constructedNodes.clear();
         SpelNodeImpl ast = this.eatExpression();
         Assert.state(ast != null, "No node");
         Token t = this.peekToken();
         if (t != null) {
            throw new SpelParseException(t.startPos, SpelMessage.MORE_INPUT, new Object[]{this.toString(this.nextToken())});
         } else {
            Assert.isTrue(this.constructedNodes.isEmpty(), "At least one node expected");
            return new SpelExpression(expressionString, ast, this.configuration);
         }
      } catch (InternalParseException var6) {
         throw var6.getCause();
      }
   }

   @Nullable
   private SpelNodeImpl eatExpression() {
      SpelNodeImpl expr = this.eatLogicalOrExpression();
      Token t = this.peekToken();
      if (t != null) {
         SpelNodeImpl ifTrueExprValue;
         if (t.kind == TokenKind.ASSIGN) {
            if (expr == null) {
               expr = new NullLiteral(this.toPos(t.startPos - 1, t.endPos - 1));
            }

            this.nextToken();
            ifTrueExprValue = this.eatLogicalOrExpression();
            return new Assign(this.toPos(t), new SpelNodeImpl[]{(SpelNodeImpl)expr, ifTrueExprValue});
         }

         if (t.kind == TokenKind.ELVIS) {
            if (expr == null) {
               expr = new NullLiteral(this.toPos(t.startPos - 1, t.endPos - 2));
            }

            this.nextToken();
            SpelNodeImpl valueIfNull = this.eatExpression();
            if (valueIfNull == null) {
               valueIfNull = new NullLiteral(this.toPos(t.startPos + 1, t.endPos + 1));
            }

            return new Elvis(this.toPos(t), new SpelNodeImpl[]{(SpelNodeImpl)expr, (SpelNodeImpl)valueIfNull});
         }

         if (t.kind == TokenKind.QMARK) {
            if (expr == null) {
               expr = new NullLiteral(this.toPos(t.startPos - 1, t.endPos - 1));
            }

            this.nextToken();
            ifTrueExprValue = this.eatExpression();
            this.eatToken(TokenKind.COLON);
            SpelNodeImpl ifFalseExprValue = this.eatExpression();
            return new Ternary(this.toPos(t), new SpelNodeImpl[]{(SpelNodeImpl)expr, ifTrueExprValue, ifFalseExprValue});
         }
      }

      return (SpelNodeImpl)expr;
   }

   @Nullable
   private SpelNodeImpl eatLogicalOrExpression() {
      Object expr;
      Token t;
      SpelNodeImpl rhExpr;
      for(expr = this.eatLogicalAndExpression(); this.peekIdentifierToken("or") || this.peekToken(TokenKind.SYMBOLIC_OR); expr = new OpOr(this.toPos(t), new SpelNodeImpl[]{(SpelNodeImpl)expr, rhExpr})) {
         t = this.takeToken();
         rhExpr = this.eatLogicalAndExpression();
         this.checkOperands(t, (SpelNodeImpl)expr, rhExpr);
      }

      return (SpelNodeImpl)expr;
   }

   @Nullable
   private SpelNodeImpl eatLogicalAndExpression() {
      Object expr;
      Token t;
      SpelNodeImpl rhExpr;
      for(expr = this.eatRelationalExpression(); this.peekIdentifierToken("and") || this.peekToken(TokenKind.SYMBOLIC_AND); expr = new OpAnd(this.toPos(t), new SpelNodeImpl[]{(SpelNodeImpl)expr, rhExpr})) {
         t = this.takeToken();
         rhExpr = this.eatRelationalExpression();
         this.checkOperands(t, (SpelNodeImpl)expr, rhExpr);
      }

      return (SpelNodeImpl)expr;
   }

   @Nullable
   private SpelNodeImpl eatRelationalExpression() {
      SpelNodeImpl expr = this.eatSumExpression();
      Token relationalOperatorToken = this.maybeEatRelationalOperator();
      if (relationalOperatorToken != null) {
         Token t = this.takeToken();
         SpelNodeImpl rhExpr = this.eatSumExpression();
         this.checkOperands(t, expr, rhExpr);
         TokenKind tk = relationalOperatorToken.kind;
         if (relationalOperatorToken.isNumericRelationalOperator()) {
            int pos = this.toPos(t);
            if (tk == TokenKind.GT) {
               return new OpGT(pos, new SpelNodeImpl[]{expr, rhExpr});
            } else if (tk == TokenKind.LT) {
               return new OpLT(pos, new SpelNodeImpl[]{expr, rhExpr});
            } else if (tk == TokenKind.LE) {
               return new OpLE(pos, new SpelNodeImpl[]{expr, rhExpr});
            } else if (tk == TokenKind.GE) {
               return new OpGE(pos, new SpelNodeImpl[]{expr, rhExpr});
            } else if (tk == TokenKind.EQ) {
               return new OpEQ(pos, new SpelNodeImpl[]{expr, rhExpr});
            } else {
               Assert.isTrue(tk == TokenKind.NE, "Not-equals token expected");
               return new OpNE(pos, new SpelNodeImpl[]{expr, rhExpr});
            }
         } else if (tk == TokenKind.INSTANCEOF) {
            return new OperatorInstanceof(this.toPos(t), new SpelNodeImpl[]{expr, rhExpr});
         } else if (tk == TokenKind.MATCHES) {
            return new OperatorMatches(this.toPos(t), new SpelNodeImpl[]{expr, rhExpr});
         } else {
            Assert.isTrue(tk == TokenKind.BETWEEN, "Between token expected");
            return new OperatorBetween(this.toPos(t), new SpelNodeImpl[]{expr, rhExpr});
         }
      } else {
         return expr;
      }
   }

   @Nullable
   private SpelNodeImpl eatSumExpression() {
      SpelNodeImpl expr = this.eatProductExpression();

      while(this.peekToken(TokenKind.PLUS, TokenKind.MINUS, TokenKind.INC)) {
         Token t = this.takeToken();
         SpelNodeImpl rhExpr = this.eatProductExpression();
         this.checkRightOperand(t, rhExpr);
         if (t.kind == TokenKind.PLUS) {
            expr = new OpPlus(this.toPos(t), new SpelNodeImpl[]{(SpelNodeImpl)expr, rhExpr});
         } else if (t.kind == TokenKind.MINUS) {
            expr = new OpMinus(this.toPos(t), new SpelNodeImpl[]{(SpelNodeImpl)expr, rhExpr});
         }
      }

      return (SpelNodeImpl)expr;
   }

   @Nullable
   private SpelNodeImpl eatProductExpression() {
      SpelNodeImpl expr = this.eatPowerIncDecExpression();

      while(this.peekToken(TokenKind.STAR, TokenKind.DIV, TokenKind.MOD)) {
         Token t = this.takeToken();
         SpelNodeImpl rhExpr = this.eatPowerIncDecExpression();
         this.checkOperands(t, (SpelNodeImpl)expr, rhExpr);
         if (t.kind == TokenKind.STAR) {
            expr = new OpMultiply(this.toPos(t), new SpelNodeImpl[]{(SpelNodeImpl)expr, rhExpr});
         } else if (t.kind == TokenKind.DIV) {
            expr = new OpDivide(this.toPos(t), new SpelNodeImpl[]{(SpelNodeImpl)expr, rhExpr});
         } else {
            Assert.isTrue(t.kind == TokenKind.MOD, "Mod token expected");
            expr = new OpModulus(this.toPos(t), new SpelNodeImpl[]{(SpelNodeImpl)expr, rhExpr});
         }
      }

      return (SpelNodeImpl)expr;
   }

   @Nullable
   private SpelNodeImpl eatPowerIncDecExpression() {
      SpelNodeImpl expr = this.eatUnaryExpression();
      Token t;
      if (this.peekToken(TokenKind.POWER)) {
         t = this.takeToken();
         SpelNodeImpl rhExpr = this.eatUnaryExpression();
         this.checkRightOperand(t, rhExpr);
         return new OperatorPower(this.toPos(t), new SpelNodeImpl[]{expr, rhExpr});
      } else if (expr != null && this.peekToken(TokenKind.INC, TokenKind.DEC)) {
         t = this.takeToken();
         return (SpelNodeImpl)(t.getKind() == TokenKind.INC ? new OpInc(this.toPos(t), true, new SpelNodeImpl[]{expr}) : new OpDec(this.toPos(t), true, new SpelNodeImpl[]{expr}));
      } else {
         return expr;
      }
   }

   @Nullable
   private SpelNodeImpl eatUnaryExpression() {
      Token t;
      SpelNodeImpl expr;
      if (this.peekToken(TokenKind.PLUS, TokenKind.MINUS, TokenKind.NOT)) {
         t = this.takeToken();
         expr = this.eatUnaryExpression();
         Assert.state(expr != null, "No node");
         if (t.kind == TokenKind.NOT) {
            return new OperatorNot(this.toPos(t), expr);
         } else if (t.kind == TokenKind.PLUS) {
            return new OpPlus(this.toPos(t), new SpelNodeImpl[]{expr});
         } else {
            Assert.isTrue(t.kind == TokenKind.MINUS, "Minus token expected");
            return new OpMinus(this.toPos(t), new SpelNodeImpl[]{expr});
         }
      } else if (this.peekToken(TokenKind.INC, TokenKind.DEC)) {
         t = this.takeToken();
         expr = this.eatUnaryExpression();
         return (SpelNodeImpl)(t.getKind() == TokenKind.INC ? new OpInc(this.toPos(t), false, new SpelNodeImpl[]{expr}) : new OpDec(this.toPos(t), false, new SpelNodeImpl[]{expr}));
      } else {
         return this.eatPrimaryExpression();
      }
   }

   @Nullable
   private SpelNodeImpl eatPrimaryExpression() {
      SpelNodeImpl start = this.eatStartNode();
      List nodes = null;

      for(SpelNodeImpl node = this.eatNode(); node != null; node = this.eatNode()) {
         if (nodes == null) {
            nodes = new ArrayList(4);
            nodes.add(start);
         }

         nodes.add(node);
      }

      if (start != null && nodes != null) {
         return new CompoundExpression(this.toPos(start.getStartPosition(), ((SpelNodeImpl)nodes.get(nodes.size() - 1)).getEndPosition()), (SpelNodeImpl[])nodes.toArray(new SpelNodeImpl[0]));
      } else {
         return start;
      }
   }

   @Nullable
   private SpelNodeImpl eatNode() {
      return this.peekToken(TokenKind.DOT, TokenKind.SAFE_NAVI) ? this.eatDottedNode() : this.eatNonDottedNode();
   }

   @Nullable
   private SpelNodeImpl eatNonDottedNode() {
      return this.peekToken(TokenKind.LSQUARE) && this.maybeEatIndexer() ? this.pop() : null;
   }

   private SpelNodeImpl eatDottedNode() {
      Token t = this.takeToken();
      boolean nullSafeNavigation = t.kind == TokenKind.SAFE_NAVI;
      if (!this.maybeEatMethodOrProperty(nullSafeNavigation) && !this.maybeEatFunctionOrVar() && !this.maybeEatProjection(nullSafeNavigation) && !this.maybeEatSelection(nullSafeNavigation)) {
         if (this.peekToken() == null) {
            throw this.internalException(t.startPos, SpelMessage.OOD);
         } else {
            throw this.internalException(t.startPos, SpelMessage.UNEXPECTED_DATA_AFTER_DOT, this.toString(this.peekToken()));
         }
      } else {
         return this.pop();
      }
   }

   private boolean maybeEatFunctionOrVar() {
      if (!this.peekToken(TokenKind.HASH)) {
         return false;
      } else {
         Token t = this.takeToken();
         Token functionOrVariableName = this.eatToken(TokenKind.IDENTIFIER);
         SpelNodeImpl[] args = this.maybeEatMethodArgs();
         if (args == null) {
            this.push(new VariableReference(functionOrVariableName.stringValue(), this.toPos(t.startPos, functionOrVariableName.endPos)));
            return true;
         } else {
            this.push(new FunctionReference(functionOrVariableName.stringValue(), this.toPos(t.startPos, functionOrVariableName.endPos), args));
            return true;
         }
      }
   }

   @Nullable
   private SpelNodeImpl[] maybeEatMethodArgs() {
      if (!this.peekToken(TokenKind.LPAREN)) {
         return null;
      } else {
         List args = new ArrayList();
         this.consumeArguments(args);
         this.eatToken(TokenKind.RPAREN);
         return (SpelNodeImpl[])args.toArray(new SpelNodeImpl[0]);
      }
   }

   private void eatConstructorArgs(List accumulatedArguments) {
      if (!this.peekToken(TokenKind.LPAREN)) {
         throw new InternalParseException(new SpelParseException(this.expressionString, this.positionOf(this.peekToken()), SpelMessage.MISSING_CONSTRUCTOR_ARGS, new Object[0]));
      } else {
         this.consumeArguments(accumulatedArguments);
         this.eatToken(TokenKind.RPAREN);
      }
   }

   private void consumeArguments(List accumulatedArguments) {
      Token t = this.peekToken();
      Assert.state(t != null, "Expected token");
      int pos = t.startPos;

      Token next;
      do {
         this.nextToken();
         t = this.peekToken();
         if (t == null) {
            throw this.internalException(pos, SpelMessage.RUN_OUT_OF_ARGUMENTS);
         }

         if (t.kind != TokenKind.RPAREN) {
            accumulatedArguments.add(this.eatExpression());
         }

         next = this.peekToken();
      } while(next != null && next.kind == TokenKind.COMMA);

      if (next == null) {
         throw this.internalException(pos, SpelMessage.RUN_OUT_OF_ARGUMENTS);
      }
   }

   private int positionOf(@Nullable Token t) {
      return t == null ? this.expressionString.length() : t.startPos;
   }

   @Nullable
   private SpelNodeImpl eatStartNode() {
      if (this.maybeEatLiteral()) {
         return this.pop();
      } else if (this.maybeEatParenExpression()) {
         return this.pop();
      } else if (!this.maybeEatTypeReference() && !this.maybeEatNullReference() && !this.maybeEatConstructorReference() && !this.maybeEatMethodOrProperty(false) && !this.maybeEatFunctionOrVar()) {
         if (this.maybeEatBeanReference()) {
            return this.pop();
         } else if (!this.maybeEatProjection(false) && !this.maybeEatSelection(false) && !this.maybeEatIndexer()) {
            return this.maybeEatInlineListOrMap() ? this.pop() : null;
         } else {
            return this.pop();
         }
      } else {
         return this.pop();
      }
   }

   private boolean maybeEatBeanReference() {
      if (!this.peekToken(TokenKind.BEAN_REF) && !this.peekToken(TokenKind.FACTORY_BEAN_REF)) {
         return false;
      } else {
         Token beanRefToken = this.takeToken();
         Token beanNameToken = null;
         String beanName = null;
         if (this.peekToken(TokenKind.IDENTIFIER)) {
            beanNameToken = this.eatToken(TokenKind.IDENTIFIER);
            beanName = beanNameToken.stringValue();
         } else {
            if (!this.peekToken(TokenKind.LITERAL_STRING)) {
               throw this.internalException(beanRefToken.startPos, SpelMessage.INVALID_BEAN_REFERENCE);
            }

            beanNameToken = this.eatToken(TokenKind.LITERAL_STRING);
            beanName = beanNameToken.stringValue();
            beanName = beanName.substring(1, beanName.length() - 1);
         }

         BeanReference beanReference;
         if (beanRefToken.getKind() == TokenKind.FACTORY_BEAN_REF) {
            String beanNameString = TokenKind.FACTORY_BEAN_REF.tokenChars + beanName;
            beanReference = new BeanReference(this.toPos(beanRefToken.startPos, beanNameToken.endPos), beanNameString);
         } else {
            beanReference = new BeanReference(this.toPos(beanNameToken), beanName);
         }

         this.constructedNodes.push(beanReference);
         return true;
      }
   }

   private boolean maybeEatTypeReference() {
      if (!this.peekToken(TokenKind.IDENTIFIER)) {
         return false;
      } else {
         Token typeName = this.peekToken();
         Assert.state(typeName != null, "Expected token");
         if (!"T".equals(typeName.stringValue())) {
            return false;
         } else {
            Token t = this.takeToken();
            if (this.peekToken(TokenKind.RSQUARE)) {
               this.push(new PropertyOrFieldReference(false, t.stringValue(), this.toPos(t)));
               return true;
            } else {
               this.eatToken(TokenKind.LPAREN);
               SpelNodeImpl node = this.eatPossiblyQualifiedId();

               int dims;
               for(dims = 0; this.peekToken(TokenKind.LSQUARE, true); ++dims) {
                  this.eatToken(TokenKind.RSQUARE);
               }

               this.eatToken(TokenKind.RPAREN);
               this.constructedNodes.push(new TypeReference(this.toPos(typeName), node, dims));
               return true;
            }
         }
      }
   }

   private boolean maybeEatNullReference() {
      if (this.peekToken(TokenKind.IDENTIFIER)) {
         Token nullToken = this.peekToken();
         Assert.state(nullToken != null, "Expected token");
         if (!"null".equalsIgnoreCase(nullToken.stringValue())) {
            return false;
         } else {
            this.nextToken();
            this.constructedNodes.push(new NullLiteral(this.toPos(nullToken)));
            return true;
         }
      } else {
         return false;
      }
   }

   private boolean maybeEatProjection(boolean nullSafeNavigation) {
      Token t = this.peekToken();
      if (!this.peekToken(TokenKind.PROJECT, true)) {
         return false;
      } else {
         Assert.state(t != null, "No token");
         SpelNodeImpl expr = this.eatExpression();
         Assert.state(expr != null, "No node");
         this.eatToken(TokenKind.RSQUARE);
         this.constructedNodes.push(new Projection(nullSafeNavigation, this.toPos(t), expr));
         return true;
      }
   }

   private boolean maybeEatInlineListOrMap() {
      Token t = this.peekToken();
      if (!this.peekToken(TokenKind.LCURLY, true)) {
         return false;
      } else {
         Assert.state(t != null, "No token");
         SpelNodeImpl expr = null;
         Token closingCurly = this.peekToken();
         if (this.peekToken(TokenKind.RCURLY, true)) {
            Assert.state(closingCurly != null, "No token");
            expr = new InlineList(this.toPos(t.startPos, closingCurly.endPos), new SpelNodeImpl[0]);
         } else if (this.peekToken(TokenKind.COLON, true)) {
            closingCurly = this.eatToken(TokenKind.RCURLY);
            expr = new InlineMap(this.toPos(t.startPos, closingCurly.endPos), new SpelNodeImpl[0]);
         } else {
            SpelNodeImpl firstExpression = this.eatExpression();
            ArrayList elements;
            if (this.peekToken(TokenKind.RCURLY)) {
               elements = new ArrayList();
               elements.add(firstExpression);
               closingCurly = this.eatToken(TokenKind.RCURLY);
               expr = new InlineList(this.toPos(t.startPos, closingCurly.endPos), (SpelNodeImpl[])elements.toArray(new SpelNodeImpl[0]));
            } else if (this.peekToken(TokenKind.COMMA, true)) {
               elements = new ArrayList();
               elements.add(firstExpression);

               do {
                  elements.add(this.eatExpression());
               } while(this.peekToken(TokenKind.COMMA, true));

               closingCurly = this.eatToken(TokenKind.RCURLY);
               expr = new InlineList(this.toPos(t.startPos, closingCurly.endPos), (SpelNodeImpl[])elements.toArray(new SpelNodeImpl[0]));
            } else {
               if (!this.peekToken(TokenKind.COLON, true)) {
                  throw this.internalException(t.startPos, SpelMessage.OOD);
               }

               elements = new ArrayList();
               elements.add(firstExpression);
               elements.add(this.eatExpression());

               while(this.peekToken(TokenKind.COMMA, true)) {
                  elements.add(this.eatExpression());
                  this.eatToken(TokenKind.COLON);
                  elements.add(this.eatExpression());
               }

               closingCurly = this.eatToken(TokenKind.RCURLY);
               expr = new InlineMap(this.toPos(t.startPos, closingCurly.endPos), (SpelNodeImpl[])elements.toArray(new SpelNodeImpl[0]));
            }
         }

         this.constructedNodes.push(expr);
         return true;
      }
   }

   private boolean maybeEatIndexer() {
      Token t = this.peekToken();
      if (!this.peekToken(TokenKind.LSQUARE, true)) {
         return false;
      } else {
         Assert.state(t != null, "No token");
         SpelNodeImpl expr = this.eatExpression();
         Assert.state(expr != null, "No node");
         this.eatToken(TokenKind.RSQUARE);
         this.constructedNodes.push(new Indexer(this.toPos(t), expr));
         return true;
      }
   }

   private boolean maybeEatSelection(boolean nullSafeNavigation) {
      Token t = this.peekToken();
      if (!this.peekSelectToken()) {
         return false;
      } else {
         Assert.state(t != null, "No token");
         this.nextToken();
         SpelNodeImpl expr = this.eatExpression();
         if (expr == null) {
            throw this.internalException(this.toPos(t), SpelMessage.MISSING_SELECTION_EXPRESSION);
         } else {
            this.eatToken(TokenKind.RSQUARE);
            if (t.kind == TokenKind.SELECT_FIRST) {
               this.constructedNodes.push(new Selection(nullSafeNavigation, 1, this.toPos(t), expr));
            } else if (t.kind == TokenKind.SELECT_LAST) {
               this.constructedNodes.push(new Selection(nullSafeNavigation, 2, this.toPos(t), expr));
            } else {
               this.constructedNodes.push(new Selection(nullSafeNavigation, 0, this.toPos(t), expr));
            }

            return true;
         }
      }
   }

   private SpelNodeImpl eatPossiblyQualifiedId() {
      Deque qualifiedIdPieces = new ArrayDeque();

      Token node;
      for(node = this.peekToken(); this.isValidQualifiedId(node); node = this.peekToken()) {
         this.nextToken();
         if (node.kind != TokenKind.DOT) {
            qualifiedIdPieces.add(new Identifier(node.stringValue(), this.toPos(node)));
         }
      }

      if (qualifiedIdPieces.isEmpty()) {
         if (node == null) {
            throw this.internalException(this.expressionString.length(), SpelMessage.OOD);
         } else {
            throw this.internalException(node.startPos, SpelMessage.NOT_EXPECTED_TOKEN, "qualified ID", node.getKind().toString().toLowerCase());
         }
      } else {
         int pos = this.toPos(((SpelNodeImpl)qualifiedIdPieces.getFirst()).getStartPosition(), ((SpelNodeImpl)qualifiedIdPieces.getLast()).getEndPosition());
         return new QualifiedIdentifier(pos, (SpelNodeImpl[])qualifiedIdPieces.toArray(new SpelNodeImpl[0]));
      }
   }

   private boolean isValidQualifiedId(@Nullable Token node) {
      if (node != null && node.kind != TokenKind.LITERAL_STRING) {
         if (node.kind != TokenKind.DOT && node.kind != TokenKind.IDENTIFIER) {
            String value = node.stringValue();
            return StringUtils.hasLength(value) && VALID_QUALIFIED_ID_PATTERN.matcher(value).matches();
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private boolean maybeEatMethodOrProperty(boolean nullSafeNavigation) {
      if (this.peekToken(TokenKind.IDENTIFIER)) {
         Token methodOrPropertyName = this.takeToken();
         SpelNodeImpl[] args = this.maybeEatMethodArgs();
         if (args == null) {
            this.push(new PropertyOrFieldReference(nullSafeNavigation, methodOrPropertyName.stringValue(), this.toPos(methodOrPropertyName)));
            return true;
         } else {
            this.push(new MethodReference(nullSafeNavigation, methodOrPropertyName.stringValue(), this.toPos(methodOrPropertyName), args));
            return true;
         }
      } else {
         return false;
      }
   }

   private boolean maybeEatConstructorReference() {
      if (this.peekIdentifierToken("new")) {
         Token newToken = this.takeToken();
         if (this.peekToken(TokenKind.RSQUARE)) {
            this.push(new PropertyOrFieldReference(false, newToken.stringValue(), this.toPos(newToken)));
            return true;
         } else {
            SpelNodeImpl possiblyQualifiedConstructorName = this.eatPossiblyQualifiedId();
            List nodes = new ArrayList();
            nodes.add(possiblyQualifiedConstructorName);
            if (this.peekToken(TokenKind.LSQUARE)) {
               ArrayList dimensions;
               for(dimensions = new ArrayList(); this.peekToken(TokenKind.LSQUARE, true); this.eatToken(TokenKind.RSQUARE)) {
                  if (!this.peekToken(TokenKind.RSQUARE)) {
                     dimensions.add(this.eatExpression());
                  } else {
                     dimensions.add((Object)null);
                  }
               }

               if (this.maybeEatInlineListOrMap()) {
                  nodes.add(this.pop());
               }

               this.push(new ConstructorReference(this.toPos(newToken), (SpelNodeImpl[])dimensions.toArray(new SpelNodeImpl[0]), (SpelNodeImpl[])nodes.toArray(new SpelNodeImpl[0])));
            } else {
               this.eatConstructorArgs(nodes);
               this.push(new ConstructorReference(this.toPos(newToken), (SpelNodeImpl[])nodes.toArray(new SpelNodeImpl[0])));
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void push(SpelNodeImpl newNode) {
      this.constructedNodes.push(newNode);
   }

   private SpelNodeImpl pop() {
      return (SpelNodeImpl)this.constructedNodes.pop();
   }

   private boolean maybeEatLiteral() {
      Token t = this.peekToken();
      if (t == null) {
         return false;
      } else {
         if (t.kind == TokenKind.LITERAL_INT) {
            this.push(Literal.getIntLiteral(t.stringValue(), this.toPos(t), 10));
         } else if (t.kind == TokenKind.LITERAL_LONG) {
            this.push(Literal.getLongLiteral(t.stringValue(), this.toPos(t), 10));
         } else if (t.kind == TokenKind.LITERAL_HEXINT) {
            this.push(Literal.getIntLiteral(t.stringValue(), this.toPos(t), 16));
         } else if (t.kind == TokenKind.LITERAL_HEXLONG) {
            this.push(Literal.getLongLiteral(t.stringValue(), this.toPos(t), 16));
         } else if (t.kind == TokenKind.LITERAL_REAL) {
            this.push(Literal.getRealLiteral(t.stringValue(), this.toPos(t), false));
         } else if (t.kind == TokenKind.LITERAL_REAL_FLOAT) {
            this.push(Literal.getRealLiteral(t.stringValue(), this.toPos(t), true));
         } else if (this.peekIdentifierToken("true")) {
            this.push(new BooleanLiteral(t.stringValue(), this.toPos(t), true));
         } else if (this.peekIdentifierToken("false")) {
            this.push(new BooleanLiteral(t.stringValue(), this.toPos(t), false));
         } else {
            if (t.kind != TokenKind.LITERAL_STRING) {
               return false;
            }

            this.push(new StringLiteral(t.stringValue(), this.toPos(t), t.stringValue()));
         }

         this.nextToken();
         return true;
      }
   }

   private boolean maybeEatParenExpression() {
      if (this.peekToken(TokenKind.LPAREN)) {
         this.nextToken();
         SpelNodeImpl expr = this.eatExpression();
         Assert.state(expr != null, "No node");
         this.eatToken(TokenKind.RPAREN);
         this.push(expr);
         return true;
      } else {
         return false;
      }
   }

   @Nullable
   private Token maybeEatRelationalOperator() {
      Token t = this.peekToken();
      if (t == null) {
         return null;
      } else if (t.isNumericRelationalOperator()) {
         return t;
      } else {
         if (t.isIdentifier()) {
            String idString = t.stringValue();
            if (idString.equalsIgnoreCase("instanceof")) {
               return t.asInstanceOfToken();
            }

            if (idString.equalsIgnoreCase("matches")) {
               return t.asMatchesToken();
            }

            if (idString.equalsIgnoreCase("between")) {
               return t.asBetweenToken();
            }
         }

         return null;
      }
   }

   private Token eatToken(TokenKind expectedKind) {
      Token t = this.nextToken();
      if (t == null) {
         int pos = this.expressionString.length();
         throw this.internalException(pos, SpelMessage.OOD);
      } else if (t.kind != expectedKind) {
         throw this.internalException(t.startPos, SpelMessage.NOT_EXPECTED_TOKEN, expectedKind.toString().toLowerCase(), t.getKind().toString().toLowerCase());
      } else {
         return t;
      }
   }

   private boolean peekToken(TokenKind desiredTokenKind) {
      return this.peekToken(desiredTokenKind, false);
   }

   private boolean peekToken(TokenKind desiredTokenKind, boolean consumeIfMatched) {
      Token t = this.peekToken();
      if (t == null) {
         return false;
      } else if (t.kind == desiredTokenKind) {
         if (consumeIfMatched) {
            ++this.tokenStreamPointer;
         }

         return true;
      } else {
         return desiredTokenKind == TokenKind.IDENTIFIER && t.kind.ordinal() >= TokenKind.DIV.ordinal() && t.kind.ordinal() <= TokenKind.NOT.ordinal() && t.data != null;
      }
   }

   private boolean peekToken(TokenKind possible1, TokenKind possible2) {
      Token t = this.peekToken();
      if (t == null) {
         return false;
      } else {
         return t.kind == possible1 || t.kind == possible2;
      }
   }

   private boolean peekToken(TokenKind possible1, TokenKind possible2, TokenKind possible3) {
      Token t = this.peekToken();
      if (t == null) {
         return false;
      } else {
         return t.kind == possible1 || t.kind == possible2 || t.kind == possible3;
      }
   }

   private boolean peekIdentifierToken(String identifierString) {
      Token t = this.peekToken();
      if (t == null) {
         return false;
      } else {
         return t.kind == TokenKind.IDENTIFIER && identifierString.equalsIgnoreCase(t.stringValue());
      }
   }

   private boolean peekSelectToken() {
      Token t = this.peekToken();
      if (t == null) {
         return false;
      } else {
         return t.kind == TokenKind.SELECT || t.kind == TokenKind.SELECT_FIRST || t.kind == TokenKind.SELECT_LAST;
      }
   }

   private Token takeToken() {
      if (this.tokenStreamPointer >= this.tokenStreamLength) {
         throw new IllegalStateException("No token");
      } else {
         return (Token)this.tokenStream.get(this.tokenStreamPointer++);
      }
   }

   @Nullable
   private Token nextToken() {
      return this.tokenStreamPointer >= this.tokenStreamLength ? null : (Token)this.tokenStream.get(this.tokenStreamPointer++);
   }

   @Nullable
   private Token peekToken() {
      return this.tokenStreamPointer >= this.tokenStreamLength ? null : (Token)this.tokenStream.get(this.tokenStreamPointer);
   }

   public String toString(@Nullable Token t) {
      if (t == null) {
         return "";
      } else {
         return t.getKind().hasPayload() ? t.stringValue() : t.kind.toString().toLowerCase();
      }
   }

   private void checkOperands(Token token, @Nullable SpelNodeImpl left, @Nullable SpelNodeImpl right) {
      this.checkLeftOperand(token, left);
      this.checkRightOperand(token, right);
   }

   private void checkLeftOperand(Token token, @Nullable SpelNodeImpl operandExpression) {
      if (operandExpression == null) {
         throw this.internalException(token.startPos, SpelMessage.LEFT_OPERAND_PROBLEM);
      }
   }

   private void checkRightOperand(Token token, @Nullable SpelNodeImpl operandExpression) {
      if (operandExpression == null) {
         throw this.internalException(token.startPos, SpelMessage.RIGHT_OPERAND_PROBLEM);
      }
   }

   private InternalParseException internalException(int pos, SpelMessage message, Object... inserts) {
      return new InternalParseException(new SpelParseException(this.expressionString, pos, message, inserts));
   }

   private int toPos(Token t) {
      return (t.startPos << 16) + t.endPos;
   }

   private int toPos(int start, int end) {
      return (start << 16) + end;
   }
}
