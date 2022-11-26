package org.apache.taglibs.standard.lang.jstl.parser;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import org.apache.taglibs.standard.lang.jstl.AndOperator;
import org.apache.taglibs.standard.lang.jstl.ArraySuffix;
import org.apache.taglibs.standard.lang.jstl.BinaryOperator;
import org.apache.taglibs.standard.lang.jstl.BinaryOperatorExpression;
import org.apache.taglibs.standard.lang.jstl.BooleanLiteral;
import org.apache.taglibs.standard.lang.jstl.ComplexValue;
import org.apache.taglibs.standard.lang.jstl.DivideOperator;
import org.apache.taglibs.standard.lang.jstl.EmptyOperator;
import org.apache.taglibs.standard.lang.jstl.EqualsOperator;
import org.apache.taglibs.standard.lang.jstl.Expression;
import org.apache.taglibs.standard.lang.jstl.ExpressionString;
import org.apache.taglibs.standard.lang.jstl.FloatingPointLiteral;
import org.apache.taglibs.standard.lang.jstl.FunctionInvocation;
import org.apache.taglibs.standard.lang.jstl.GreaterThanOperator;
import org.apache.taglibs.standard.lang.jstl.GreaterThanOrEqualsOperator;
import org.apache.taglibs.standard.lang.jstl.IntegerLiteral;
import org.apache.taglibs.standard.lang.jstl.LessThanOperator;
import org.apache.taglibs.standard.lang.jstl.LessThanOrEqualsOperator;
import org.apache.taglibs.standard.lang.jstl.Literal;
import org.apache.taglibs.standard.lang.jstl.MinusOperator;
import org.apache.taglibs.standard.lang.jstl.ModulusOperator;
import org.apache.taglibs.standard.lang.jstl.MultiplyOperator;
import org.apache.taglibs.standard.lang.jstl.NamedValue;
import org.apache.taglibs.standard.lang.jstl.NotEqualsOperator;
import org.apache.taglibs.standard.lang.jstl.NotOperator;
import org.apache.taglibs.standard.lang.jstl.NullLiteral;
import org.apache.taglibs.standard.lang.jstl.OrOperator;
import org.apache.taglibs.standard.lang.jstl.PlusOperator;
import org.apache.taglibs.standard.lang.jstl.PropertySuffix;
import org.apache.taglibs.standard.lang.jstl.StringLiteral;
import org.apache.taglibs.standard.lang.jstl.UnaryMinusOperator;
import org.apache.taglibs.standard.lang.jstl.UnaryOperator;
import org.apache.taglibs.standard.lang.jstl.UnaryOperatorExpression;
import org.apache.taglibs.standard.lang.jstl.ValueSuffix;

public class ELParser implements ELParserConstants {
   public ELParserTokenManager token_source;
   SimpleCharStream jj_input_stream;
   public Token token;
   public Token jj_nt;
   private int jj_ntk;
   private Token jj_scanpos;
   private Token jj_lastpos;
   private int jj_la;
   public boolean lookingAhead = false;
   private boolean jj_semLA;
   private int jj_gen;
   private final int[] jj_la1 = new int[34];
   private final int[] jj_la1_0 = new int[]{6, 6, 6, 0, 0, 0, 0, 408944640, 6291456, 402653184, 408944640, 127795200, 1572864, 393216, 100663296, 25165824, 127795200, 0, 0, 0, 0, 0, 0, 0, 0, 0, 65536, 536900992, 0, Integer.MIN_VALUE, 536900992, 65536, 30080, 12288};
   private final int[] jj_la1_1 = new int[]{0, 0, 0, 49152, 49152, 12288, 12288, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 24, 24, 992, 192, 768, 992, 68624, 3072, 68624, 2, 0, 131072, 0, 199696, 2, 0, 0};
   private final JJCalls[] jj_2_rtns = new JJCalls[2];
   private boolean jj_rescan = false;
   private int jj_gc = 0;
   private Vector jj_expentries = new Vector();
   private int[] jj_expentry;
   private int jj_kind = -1;
   private int[] jj_lasttokens = new int[100];
   private int jj_endpos;

   public static void main(String[] args) throws ParseException {
      ELParser parser = new ELParser(System.in);
      parser.ExpressionString();
   }

   public final Object ExpressionString() throws ParseException {
      Object ret = "";
      List elems = null;
      Object ret;
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 1:
            ret = this.AttrValueString();
            break;
         case 2:
            ret = this.AttrValueExpression();
            break;
         default:
            this.jj_la1[0] = this.jj_gen;
            this.jj_consume_token(-1);
            throw new ParseException();
      }

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 1:
            case 2:
               Object elem;
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 1:
                     elem = this.AttrValueString();
                     break;
                  case 2:
                     elem = this.AttrValueExpression();
                     break;
                  default:
                     this.jj_la1[2] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }

               if (elems == null) {
                  elems = new ArrayList();
                  elems.add(ret);
               }

               elems.add(elem);
               break;
            default:
               this.jj_la1[1] = this.jj_gen;
               if (elems != null) {
                  ret = new ExpressionString(elems.toArray());
               }

               return ret;
         }
      }
   }

   public final String AttrValueString() throws ParseException {
      Token t = this.jj_consume_token(1);
      return t.image;
   }

   public final Expression AttrValueExpression() throws ParseException {
      this.jj_consume_token(2);
      Expression exp = this.Expression();
      this.jj_consume_token(15);
      return exp;
   }

   public final Expression Expression() throws ParseException {
      Expression ret = this.OrExpression();
      return ret;
   }

   public final Expression OrExpression() throws ParseException {
      List operators = null;
      List expressions = null;
      Expression startExpression = this.AndExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 46:
            case 47:
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 46:
                     this.jj_consume_token(46);
                     break;
                  case 47:
                     this.jj_consume_token(47);
                     break;
                  default:
                     this.jj_la1[4] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }

               BinaryOperator operator = OrOperator.SINGLETON;
               Expression expression = this.AndExpression();
               if (operators == null) {
                  operators = new ArrayList();
                  expressions = new ArrayList();
               }

               operators.add(operator);
               expressions.add(expression);
               break;
            default:
               this.jj_la1[3] = this.jj_gen;
               if (operators != null) {
                  return new BinaryOperatorExpression(startExpression, operators, expressions);
               }

               return startExpression;
         }
      }
   }

   public final Expression AndExpression() throws ParseException {
      List operators = null;
      List expressions = null;
      Expression startExpression = this.EqualityExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 44:
            case 45:
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 44:
                     this.jj_consume_token(44);
                     break;
                  case 45:
                     this.jj_consume_token(45);
                     break;
                  default:
                     this.jj_la1[6] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }

               BinaryOperator operator = AndOperator.SINGLETON;
               Expression expression = this.EqualityExpression();
               if (operators == null) {
                  operators = new ArrayList();
                  expressions = new ArrayList();
               }

               operators.add(operator);
               expressions.add(expression);
               break;
            default:
               this.jj_la1[5] = this.jj_gen;
               if (operators != null) {
                  return new BinaryOperatorExpression(startExpression, operators, expressions);
               }

               return startExpression;
         }
      }
   }

   public final Expression EqualityExpression() throws ParseException {
      List operators = null;
      List expressions = null;
      Expression startExpression = this.RelationalExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 21:
            case 22:
            case 27:
            case 28:
               Object operator;
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 21:
                  case 22:
                     switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 21:
                           this.jj_consume_token(21);
                           break;
                        case 22:
                           this.jj_consume_token(22);
                           break;
                        default:
                           this.jj_la1[8] = this.jj_gen;
                           this.jj_consume_token(-1);
                           throw new ParseException();
                     }

                     operator = EqualsOperator.SINGLETON;
                     break;
                  case 23:
                  case 24:
                  case 25:
                  case 26:
                  default:
                     this.jj_la1[10] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
                  case 27:
                  case 28:
                     switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 27:
                           this.jj_consume_token(27);
                           break;
                        case 28:
                           this.jj_consume_token(28);
                           break;
                        default:
                           this.jj_la1[9] = this.jj_gen;
                           this.jj_consume_token(-1);
                           throw new ParseException();
                     }

                     operator = NotEqualsOperator.SINGLETON;
               }

               Expression expression = this.RelationalExpression();
               if (operators == null) {
                  operators = new ArrayList();
                  expressions = new ArrayList();
               }

               operators.add(operator);
               expressions.add(expression);
               break;
            case 23:
            case 24:
            case 25:
            case 26:
            default:
               this.jj_la1[7] = this.jj_gen;
               if (operators != null) {
                  return new BinaryOperatorExpression(startExpression, operators, expressions);
               }

               return startExpression;
         }
      }
   }

   public final Expression RelationalExpression() throws ParseException {
      List operators = null;
      List expressions = null;
      Expression startExpression = this.AddExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
               Object operator;
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 17:
                  case 18:
                     switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 17:
                           this.jj_consume_token(17);
                           break;
                        case 18:
                           this.jj_consume_token(18);
                           break;
                        default:
                           this.jj_la1[13] = this.jj_gen;
                           this.jj_consume_token(-1);
                           throw new ParseException();
                     }

                     operator = GreaterThanOperator.SINGLETON;
                     break;
                  case 19:
                  case 20:
                     switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 19:
                           this.jj_consume_token(19);
                           break;
                        case 20:
                           this.jj_consume_token(20);
                           break;
                        default:
                           this.jj_la1[12] = this.jj_gen;
                           this.jj_consume_token(-1);
                           throw new ParseException();
                     }

                     operator = LessThanOperator.SINGLETON;
                     break;
                  case 21:
                  case 22:
                  default:
                     this.jj_la1[16] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
                  case 23:
                  case 24:
                     switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 23:
                           this.jj_consume_token(23);
                           break;
                        case 24:
                           this.jj_consume_token(24);
                           break;
                        default:
                           this.jj_la1[15] = this.jj_gen;
                           this.jj_consume_token(-1);
                           throw new ParseException();
                     }

                     operator = LessThanOrEqualsOperator.SINGLETON;
                     break;
                  case 25:
                  case 26:
                     switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 25:
                           this.jj_consume_token(25);
                           break;
                        case 26:
                           this.jj_consume_token(26);
                           break;
                        default:
                           this.jj_la1[14] = this.jj_gen;
                           this.jj_consume_token(-1);
                           throw new ParseException();
                     }

                     operator = GreaterThanOrEqualsOperator.SINGLETON;
               }

               Expression expression = this.AddExpression();
               if (operators == null) {
                  operators = new ArrayList();
                  expressions = new ArrayList();
               }

               operators.add(operator);
               expressions.add(expression);
               break;
            case 21:
            case 22:
            default:
               this.jj_la1[11] = this.jj_gen;
               if (operators != null) {
                  return new BinaryOperatorExpression(startExpression, operators, expressions);
               }

               return startExpression;
         }
      }
   }

   public final Expression AddExpression() throws ParseException {
      List operators = null;
      List expressions = null;
      Expression startExpression = this.MultiplyExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 35:
            case 36:
               Object operator;
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 35:
                     this.jj_consume_token(35);
                     operator = PlusOperator.SINGLETON;
                     break;
                  case 36:
                     this.jj_consume_token(36);
                     operator = MinusOperator.SINGLETON;
                     break;
                  default:
                     this.jj_la1[18] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }

               Expression expression = this.MultiplyExpression();
               if (operators == null) {
                  operators = new ArrayList();
                  expressions = new ArrayList();
               }

               operators.add(operator);
               expressions.add(expression);
               break;
            default:
               this.jj_la1[17] = this.jj_gen;
               if (operators != null) {
                  return new BinaryOperatorExpression(startExpression, operators, expressions);
               }

               return startExpression;
         }
      }
   }

   public final Expression MultiplyExpression() throws ParseException {
      List operators = null;
      List expressions = null;
      Expression startExpression = this.UnaryExpression();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
               Object operator;
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 37:
                     this.jj_consume_token(37);
                     operator = MultiplyOperator.SINGLETON;
                     break;
                  case 38:
                  case 39:
                     switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 38:
                           this.jj_consume_token(38);
                           break;
                        case 39:
                           this.jj_consume_token(39);
                           break;
                        default:
                           this.jj_la1[20] = this.jj_gen;
                           this.jj_consume_token(-1);
                           throw new ParseException();
                     }

                     operator = DivideOperator.SINGLETON;
                     break;
                  case 40:
                  case 41:
                     switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 40:
                           this.jj_consume_token(40);
                           break;
                        case 41:
                           this.jj_consume_token(41);
                           break;
                        default:
                           this.jj_la1[21] = this.jj_gen;
                           this.jj_consume_token(-1);
                           throw new ParseException();
                     }

                     operator = ModulusOperator.SINGLETON;
                     break;
                  default:
                     this.jj_la1[22] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }

               Expression expression = this.UnaryExpression();
               if (operators == null) {
                  operators = new ArrayList();
                  expressions = new ArrayList();
               }

               operators.add(operator);
               expressions.add(expression);
               break;
            default:
               this.jj_la1[19] = this.jj_gen;
               if (operators != null) {
                  return new BinaryOperatorExpression(startExpression, operators, expressions);
               }

               return startExpression;
         }
      }
   }

   public final Expression UnaryExpression() throws ParseException {
      UnaryOperator singleOperator = null;
      List operators = null;

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 36:
            case 42:
            case 43:
            case 48:
               Object operator;
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 36:
                     this.jj_consume_token(36);
                     operator = UnaryMinusOperator.SINGLETON;
                     break;
                  case 42:
                  case 43:
                     switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                        case 42:
                           this.jj_consume_token(42);
                           break;
                        case 43:
                           this.jj_consume_token(43);
                           break;
                        default:
                           this.jj_la1[24] = this.jj_gen;
                           this.jj_consume_token(-1);
                           throw new ParseException();
                     }

                     operator = NotOperator.SINGLETON;
                     break;
                  case 48:
                     this.jj_consume_token(48);
                     operator = EmptyOperator.SINGLETON;
                     break;
                  default:
                     this.jj_la1[25] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }

               if (singleOperator == null) {
                  singleOperator = operator;
               } else if (operators == null) {
                  operators = new ArrayList();
                  operators.add(singleOperator);
                  operators.add(operator);
               } else {
                  operators.add(operator);
               }
               break;
            default:
               this.jj_la1[23] = this.jj_gen;
               Expression expression = this.Value();
               if (operators != null) {
                  return new UnaryOperatorExpression((UnaryOperator)null, operators, expression);
               }

               if (singleOperator != null) {
                  return new UnaryOperatorExpression((UnaryOperator)singleOperator, (List)null, expression);
               }

               return expression;
         }
      }
   }

   public final Expression Value() throws ParseException {
      List suffixes = null;
      Expression prefix = this.ValuePrefix();

      while(true) {
         switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
            case 16:
            case 33:
               ValueSuffix suffix = this.ValueSuffix();
               if (suffixes == null) {
                  suffixes = new ArrayList();
               }

               suffixes.add(suffix);
               break;
            default:
               this.jj_la1[26] = this.jj_gen;
               if (suffixes == null) {
                  return prefix;
               }

               return new ComplexValue(prefix, suffixes);
         }
      }
   }

   public final Expression ValuePrefix() throws ParseException {
      Object ret;
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 7:
         case 8:
         case 10:
         case 12:
         case 13:
         case 14:
            ret = this.Literal();
            break;
         case 9:
         case 11:
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
         case 26:
         case 27:
         case 28:
         default:
            this.jj_la1[27] = this.jj_gen;
            if (this.jj_2_1(Integer.MAX_VALUE)) {
               ret = this.FunctionInvocation();
               break;
            } else {
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 49:
                     ret = this.NamedValue();
                     return (Expression)ret;
                  default:
                     this.jj_la1[28] = this.jj_gen;
                     this.jj_consume_token(-1);
                     throw new ParseException();
               }
            }
         case 29:
            this.jj_consume_token(29);
            ret = this.Expression();
            this.jj_consume_token(30);
      }

      return (Expression)ret;
   }

   public final NamedValue NamedValue() throws ParseException {
      Token t = this.jj_consume_token(49);
      return new NamedValue(t.image);
   }

   public final FunctionInvocation FunctionInvocation() throws ParseException {
      ArrayList argumentList;
      String qualifiedName;
      argumentList = new ArrayList();
      qualifiedName = this.QualifiedName();
      this.jj_consume_token(29);
      label34:
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 7:
         case 8:
         case 10:
         case 12:
         case 13:
         case 14:
         case 29:
         case 36:
         case 42:
         case 43:
         case 48:
         case 49:
            Expression exp = this.Expression();
            argumentList.add(exp);

            while(true) {
               switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
                  case 31:
                     this.jj_consume_token(31);
                     exp = this.Expression();
                     argumentList.add(exp);
                     break;
                  default:
                     this.jj_la1[29] = this.jj_gen;
                     break label34;
               }
            }
         case 9:
         case 11:
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
         case 26:
         case 27:
         case 28:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 44:
         case 45:
         case 46:
         case 47:
         default:
            this.jj_la1[30] = this.jj_gen;
      }

      this.jj_consume_token(30);
      String allowed = System.getProperty("javax.servlet.jsp.functions.allowed");
      if (allowed != null && allowed.equalsIgnoreCase("true")) {
         return new FunctionInvocation(qualifiedName, argumentList);
      } else {
         throw new ParseException("EL functions are not supported.");
      }
   }

   public final ValueSuffix ValueSuffix() throws ParseException {
      Object suffix;
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 16:
            suffix = this.PropertySuffix();
            break;
         case 33:
            suffix = this.ArraySuffix();
            break;
         default:
            this.jj_la1[31] = this.jj_gen;
            this.jj_consume_token(-1);
            throw new ParseException();
      }

      return (ValueSuffix)suffix;
   }

   public final PropertySuffix PropertySuffix() throws ParseException {
      this.jj_consume_token(16);
      String property = this.Identifier();
      return new PropertySuffix(property);
   }

   public final ArraySuffix ArraySuffix() throws ParseException {
      this.jj_consume_token(33);
      Expression index = this.Expression();
      this.jj_consume_token(34);
      return new ArraySuffix(index);
   }

   public final Literal Literal() throws ParseException {
      Object ret;
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 7:
            ret = this.IntegerLiteral();
            break;
         case 8:
            ret = this.FloatingPointLiteral();
            break;
         case 9:
         case 11:
         default:
            this.jj_la1[32] = this.jj_gen;
            this.jj_consume_token(-1);
            throw new ParseException();
         case 10:
            ret = this.StringLiteral();
            break;
         case 12:
         case 13:
            ret = this.BooleanLiteral();
            break;
         case 14:
            ret = this.NullLiteral();
      }

      return (Literal)ret;
   }

   public final BooleanLiteral BooleanLiteral() throws ParseException {
      switch (this.jj_ntk == -1 ? this.jj_ntk() : this.jj_ntk) {
         case 12:
            this.jj_consume_token(12);
            return BooleanLiteral.TRUE;
         case 13:
            this.jj_consume_token(13);
            return BooleanLiteral.FALSE;
         default:
            this.jj_la1[33] = this.jj_gen;
            this.jj_consume_token(-1);
            throw new ParseException();
      }
   }

   public final StringLiteral StringLiteral() throws ParseException {
      Token t = this.jj_consume_token(10);
      return StringLiteral.fromToken(t.image);
   }

   public final IntegerLiteral IntegerLiteral() throws ParseException {
      Token t = this.jj_consume_token(7);
      return new IntegerLiteral(t.image);
   }

   public final FloatingPointLiteral FloatingPointLiteral() throws ParseException {
      Token t = this.jj_consume_token(8);
      return new FloatingPointLiteral(t.image);
   }

   public final NullLiteral NullLiteral() throws ParseException {
      this.jj_consume_token(14);
      return NullLiteral.SINGLETON;
   }

   public final String Identifier() throws ParseException {
      Token t = this.jj_consume_token(49);
      return t.image;
   }

   public final String QualifiedName() throws ParseException {
      String prefix = null;
      String localPart = null;
      if (this.jj_2_2(Integer.MAX_VALUE)) {
         prefix = this.Identifier();
         this.jj_consume_token(32);
      }

      localPart = this.Identifier();
      return prefix == null ? localPart : prefix + ":" + localPart;
   }

   private final boolean jj_2_1(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_1();
      this.jj_save(0, xla);
      return retval;
   }

   private final boolean jj_2_2(int xla) {
      this.jj_la = xla;
      this.jj_lastpos = this.jj_scanpos = this.token;
      boolean retval = !this.jj_3_2();
      this.jj_save(1, xla);
      return retval;
   }

   private final boolean jj_3R_13() {
      if (this.jj_3R_12()) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_scan_token(32)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_2() {
      if (this.jj_3R_12()) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_scan_token(32)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3_1() {
      if (this.jj_3R_11()) {
         return true;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      } else if (this.jj_scan_token(29)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_12() {
      if (this.jj_scan_token(49)) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   private final boolean jj_3R_11() {
      Token xsp = this.jj_scanpos;
      if (this.jj_3R_13()) {
         this.jj_scanpos = xsp;
      } else if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
         return false;
      }

      if (this.jj_3R_12()) {
         return true;
      } else {
         return this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos ? false : false;
      }
   }

   public ELParser(InputStream stream) {
      this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
      this.token_source = new ELParserTokenManager(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 34; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public void ReInit(InputStream stream) {
      this.jj_input_stream.ReInit((InputStream)stream, 1, 1);
      this.token_source.ReInit(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 34; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public ELParser(Reader stream) {
      this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
      this.token_source = new ELParserTokenManager(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 34; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public void ReInit(Reader stream) {
      this.jj_input_stream.ReInit((Reader)stream, 1, 1);
      this.token_source.ReInit(this.jj_input_stream);
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 34; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public ELParser(ELParserTokenManager tm) {
      this.token_source = tm;
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 34; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   public void ReInit(ELParserTokenManager tm) {
      this.token_source = tm;
      this.token = new Token();
      this.jj_ntk = -1;
      this.jj_gen = 0;

      int i;
      for(i = 0; i < 34; ++i) {
         this.jj_la1[i] = -1;
      }

      for(i = 0; i < this.jj_2_rtns.length; ++i) {
         this.jj_2_rtns[i] = new JJCalls();
      }

   }

   private final Token jj_consume_token(int kind) throws ParseException {
      Token oldToken;
      if ((oldToken = this.token).next != null) {
         this.token = this.token.next;
      } else {
         this.token = this.token.next = this.token_source.getNextToken();
      }

      this.jj_ntk = -1;
      if (this.token.kind != kind) {
         this.token = oldToken;
         this.jj_kind = kind;
         throw this.generateParseException();
      } else {
         ++this.jj_gen;
         if (++this.jj_gc > 100) {
            this.jj_gc = 0;

            for(int i = 0; i < this.jj_2_rtns.length; ++i) {
               for(JJCalls c = this.jj_2_rtns[i]; c != null; c = c.next) {
                  if (c.gen < this.jj_gen) {
                     c.first = null;
                  }
               }
            }
         }

         return this.token;
      }
   }

   private final boolean jj_scan_token(int kind) {
      if (this.jj_scanpos == this.jj_lastpos) {
         --this.jj_la;
         if (this.jj_scanpos.next == null) {
            this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next = this.token_source.getNextToken();
         } else {
            this.jj_lastpos = this.jj_scanpos = this.jj_scanpos.next;
         }
      } else {
         this.jj_scanpos = this.jj_scanpos.next;
      }

      if (this.jj_rescan) {
         int i = 0;

         Token tok;
         for(tok = this.token; tok != null && tok != this.jj_scanpos; tok = tok.next) {
            ++i;
         }

         if (tok != null) {
            this.jj_add_error_token(kind, i);
         }
      }

      return this.jj_scanpos.kind != kind;
   }

   public final Token getNextToken() {
      if (this.token.next != null) {
         this.token = this.token.next;
      } else {
         this.token = this.token.next = this.token_source.getNextToken();
      }

      this.jj_ntk = -1;
      ++this.jj_gen;
      return this.token;
   }

   public final Token getToken(int index) {
      Token t = this.lookingAhead ? this.jj_scanpos : this.token;

      for(int i = 0; i < index; ++i) {
         if (t.next != null) {
            t = t.next;
         } else {
            t = t.next = this.token_source.getNextToken();
         }
      }

      return t;
   }

   private final int jj_ntk() {
      return (this.jj_nt = this.token.next) == null ? (this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind) : (this.jj_ntk = this.jj_nt.kind);
   }

   private void jj_add_error_token(int kind, int pos) {
      if (pos < 100) {
         if (pos == this.jj_endpos + 1) {
            this.jj_lasttokens[this.jj_endpos++] = kind;
         } else if (this.jj_endpos != 0) {
            this.jj_expentry = new int[this.jj_endpos];

            for(int i = 0; i < this.jj_endpos; ++i) {
               this.jj_expentry[i] = this.jj_lasttokens[i];
            }

            boolean exists = false;
            Enumeration enum_ = this.jj_expentries.elements();

            label48:
            do {
               int[] oldentry;
               do {
                  if (!enum_.hasMoreElements()) {
                     break label48;
                  }

                  oldentry = (int[])((int[])enum_.nextElement());
               } while(oldentry.length != this.jj_expentry.length);

               exists = true;

               for(int i = 0; i < this.jj_expentry.length; ++i) {
                  if (oldentry[i] != this.jj_expentry[i]) {
                     exists = false;
                     break;
                  }
               }
            } while(!exists);

            if (!exists) {
               this.jj_expentries.addElement(this.jj_expentry);
            }

            if (pos != 0) {
               this.jj_lasttokens[(this.jj_endpos = pos) - 1] = kind;
            }
         }

      }
   }

   public final ParseException generateParseException() {
      this.jj_expentries.removeAllElements();
      boolean[] la1tokens = new boolean[54];

      int i;
      for(i = 0; i < 54; ++i) {
         la1tokens[i] = false;
      }

      if (this.jj_kind >= 0) {
         la1tokens[this.jj_kind] = true;
         this.jj_kind = -1;
      }

      int j;
      for(i = 0; i < 34; ++i) {
         if (this.jj_la1[i] == this.jj_gen) {
            for(j = 0; j < 32; ++j) {
               if ((this.jj_la1_0[i] & 1 << j) != 0) {
                  la1tokens[j] = true;
               }

               if ((this.jj_la1_1[i] & 1 << j) != 0) {
                  la1tokens[32 + j] = true;
               }
            }
         }
      }

      for(i = 0; i < 54; ++i) {
         if (la1tokens[i]) {
            this.jj_expentry = new int[1];
            this.jj_expentry[0] = i;
            this.jj_expentries.addElement(this.jj_expentry);
         }
      }

      this.jj_endpos = 0;
      this.jj_rescan_token();
      this.jj_add_error_token(0, 0);
      int[][] exptokseq = new int[this.jj_expentries.size()][];

      for(j = 0; j < this.jj_expentries.size(); ++j) {
         exptokseq[j] = (int[])((int[])this.jj_expentries.elementAt(j));
      }

      return new ParseException(this.token, exptokseq, tokenImage);
   }

   public final void enable_tracing() {
   }

   public final void disable_tracing() {
   }

   private final void jj_rescan_token() {
      this.jj_rescan = true;

      for(int i = 0; i < 2; ++i) {
         JJCalls p = this.jj_2_rtns[i];

         do {
            if (p.gen > this.jj_gen) {
               this.jj_la = p.arg;
               this.jj_lastpos = this.jj_scanpos = p.first;
               switch (i) {
                  case 0:
                     this.jj_3_1();
                     break;
                  case 1:
                     this.jj_3_2();
               }
            }

            p = p.next;
         } while(p != null);
      }

      this.jj_rescan = false;
   }

   private final void jj_save(int index, int xla) {
      JJCalls p;
      for(p = this.jj_2_rtns[index]; p.gen > this.jj_gen; p = p.next) {
         if (p.next == null) {
            p = p.next = new JJCalls();
            break;
         }
      }

      p.gen = this.jj_gen + xla - this.jj_la;
      p.first = this.token;
      p.arg = xla;
   }

   static final class JJCalls {
      int gen;
      Token first;
      int arg;
      JJCalls next;
   }
}
