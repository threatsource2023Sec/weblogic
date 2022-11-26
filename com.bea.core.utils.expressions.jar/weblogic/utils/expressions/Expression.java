package weblogic.utils.expressions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import org.w3c.dom.Document;

public final class Expression implements ExpressionEvaluator, ExpressionTypes, Serializable {
   private static final long serialVersionUID = 782182102375685724L;
   private final int type;
   private Expression term1;
   private Expression term2;
   private Expression term3;
   private ArrayList terms;
   private long ival;
   private double fval;
   private String style;
   private String sval;
   private Variable variable;
   private final int UNKNOWN = -1;

   public Expression(int type) {
      this.type = type;
   }

   protected Expression(int type, Expression expr) {
      this.type = type;
      this.term1 = expr;
   }

   protected Expression(int type, Expression expr1, Expression expr2) {
      this.type = type;
      this.term1 = expr1;
      this.term2 = expr2;
   }

   protected Expression(int type, Expression expr1, Expression expr2, Expression expr3) {
      this.type = type;
      this.term1 = expr1;
      this.term2 = expr2;
      this.term3 = expr3;
   }

   protected Expression(int type, Expression expr1, ArrayList terms) {
      this.type = type;
      this.term1 = expr1;
      this.terms = terms;
   }

   protected Expression(int type, VariableBinder variableBinder, String lit) {
      this.sval = lit;
      this.type = type;
      if (type != 17) {
         throw new AssertionError("Unexpected type: " + type);
      } else {
         this.variable = variableBinder.getVariable(lit);
      }
   }

   protected Expression(int type, String lit) {
      this.type = type;
      if (type == 18) {
         this.sval = lit.substring(1, lit.length() - 1);
      } else {
         this.sval = lit;
      }

      if (type == 19) {
         char l = lit.charAt(lit.length() - 1);
         if (l == 'l' || l == 'L') {
            lit = lit.substring(0, lit.length() - 1);
         }

         this.ival = Long.decode(lit);
      } else if (type == 20) {
         this.fval = Double.parseDouble(lit);
      }

   }

   protected Expression(int type, String style, String select) {
      this.type = type;
      this.style = style;
      this.sval = select;
   }

   int type() {
      return this.type;
   }

   String getSval() {
      return this.sval;
   }

   public void setSval(String s) {
      this.sval = s;
   }

   private long getIval() {
      return this.ival;
   }

   private double getFval() {
      return this.fval;
   }

   public String getSelectorID() {
      return this.type == 4 && this.term1.type == 17 && this.variable == null ? this.term1.getSval() : null;
   }

   public String getfilteredForwardedSelectorID() {
      return this.term2.type == 4 && this.term2.term1.type == 17 && this.term2.variable == null ? this.term2.term1.getSval() : null;
   }

   public String getStandardForwarderID() {
      return this.type == 2 && this.term1.type == 17 && this.variable == null ? this.term1.getSval() : null;
   }

   public String getComplexForwarderID() {
      return this.type == 0 && this.term1 != null && this.term2 != null && this.term3 == null && this.term1.type == 2 && this.term1.term1.type == 17 && this.term1.variable == null ? this.term1.term1.getSval() : null;
   }

   public boolean evaluate(Object context) throws ExpressionEvaluationException {
      return this.evaluateBoolean(context) == 14;
   }

   private int evaluateBoolean(Object context) throws ExpressionEvaluationException {
      Object o;
      String s1;
      int t1;
      int t2;
      switch (this.type) {
         case 0:
            t1 = this.term1.evaluateBoolean(context);
            if (t1 == 15) {
               return 15;
            } else {
               t2 = this.term2.evaluateBoolean(context);
               if (t2 == 15) {
                  return 15;
               } else {
                  if (t1 != -1 && t2 != -1) {
                     return 14;
                  }

                  return -1;
               }
            }
         case 1:
            t1 = this.term1.evaluateBoolean(context);
            if (t1 == 14) {
               return 14;
            } else {
               t2 = this.term2.evaluateBoolean(context);
               if (t2 == 14) {
                  return 14;
               } else {
                  if (t1 != -1 && t2 != -1) {
                     return 15;
                  }

                  return -1;
               }
            }
         case 2:
            t1 = this.term1.evaluateBoolean(context);
            return t1 == 14 ? 15 : (t1 == 15 ? 14 : -1);
         case 3:
            return this.term1.evaluateExpr(context) == null ? 14 : 15;
         case 4:
            return this.term1.evaluateExpr(context) == null ? 15 : 14;
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
            Boolean b = (Boolean)this.evaluateExpr(context);
            return b == null ? -1 : (b ? 14 : 15);
         case 11:
            char esc = this.term3 == null ? 0 : ((String)this.term3.evaluateExpr(context)).charAt(0);

            try {
               s1 = (String)this.term1.evaluateExpr(context);
               String s2 = (String)this.term2.evaluateExpr(context);
               if (s1 != null && s2 != null) {
                  return matchStrings(s2, s1, esc) ? 14 : 15;
               }

               return -1;
            } catch (ClassCastException var8) {
               return -1;
            }
         case 12:
         case 16:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         default:
            throw new ExpressionEvaluationException("Unexpected type: " + TYPE_NAMES[this.type], new RuntimeException("Unexpected type: " + TYPE_NAMES[this.type]));
         case 13:
            o = this.term1.evaluateExpr(context);
            if (o == null) {
               return -1;
            } else if (!(o instanceof String)) {
               return -1;
            } else {
               Iterator i = this.terms.iterator();

               do {
                  if (!i.hasNext()) {
                     return 15;
                  }
               } while(!o.equals(((Expression)i.next()).evaluateExpr(context)));

               return 14;
            }
         case 14:
            return 14;
         case 15:
            return 15;
         case 17:
            o = this.evaluateExpr(context);
            if (o == null) {
               return -1;
            } else if (o.equals(Boolean.TRUE)) {
               return 14;
            } else {
               if (o.equals(Boolean.FALSE)) {
                  return 15;
               }

               return -1;
            }
         case 25:
            s1 = this.style.replace('\'', ' ').trim();
            if (!s1.equals(TYPE_NAMES[this.type])) {
               throw new ExpressionEvaluationException("Unknown expression syntax specified in JMS_BEA_SELECT(): " + this.style, new RuntimeException("Unknown expression syntax specified in JMS_BEA_SELECT(): " + this.style));
            } else {
               o = getXMLSelect(this.sval, context);
               return o == null ? 15 : 14;
            }
      }
   }

   private Object evaluateExpr(Object context) throws ExpressionEvaluationException {
      switch (this.type) {
         case 14:
            return Boolean.TRUE;
         case 15:
            return Boolean.FALSE;
         case 16:
            return null;
         case 17:
            if (this.variable == null) {
               throw new ExpressionEvaluationException("Unbound variable: " + this.getSval());
            }

            return this.variable.get(context);
         case 18:
            return this.getSval();
         case 19:
            return new Long(this.getIval());
         case 20:
            return new Double(this.getFval());
         case 21:
         case 22:
         case 23:
         case 24:
         default:
            Object v1 = this.term1.evaluateExpr(context);
            Object v2 = this.term2.evaluateExpr(context);
            if (v1 != null && v2 != null) {
               try {
                  if ((v1 instanceof String || v2 instanceof String) && (!(v1 instanceof String) || !(v2 instanceof String))) {
                     throw new NumberFormatException((String)null);
                  } else {
                     if (!(v1 instanceof Double) && !(v2 instanceof Double)) {
                        if (!(v1 instanceof Long) && !(v2 instanceof Long)) {
                           if (v1 instanceof Float || v2 instanceof Float) {
                              float l1 = ((Number)v1).floatValue();
                              float l2 = ((Number)v2).floatValue();
                              return evaluateExpr(this.type, l1, l2);
                           }

                           if (!(v1 instanceof Integer) && !(v2 instanceof Integer)) {
                              if (!(v1 instanceof Short) && !(v2 instanceof Short)) {
                                 if (!(v1 instanceof Byte) && !(v2 instanceof Byte)) {
                                    if (v1 instanceof Boolean && v2 instanceof Boolean) {
                                       if (this.type == 5) {
                                          return v1.equals(v2);
                                       }

                                       if (this.type == 10) {
                                          return !v1.equals(v2);
                                       }
                                    }

                                    if (v1 instanceof String || v2 instanceof String) {
                                       if (this.type == 5) {
                                          return v1.equals(v2);
                                       }

                                       if (this.type == 10) {
                                          return !v1.equals(v2);
                                       }
                                    }

                                    throw new ExpressionEvaluationException("Not numeric expression: " + TYPE_NAMES[this.type], new RuntimeException("Not numeric expression: " + TYPE_NAMES[this.type]));
                                 }

                                 byte l1 = ((Number)v1).byteValue();
                                 byte l2 = ((Number)v2).byteValue();
                                 return evaluateExpr(this.type, l1, l2);
                              }

                              short l1 = ((Number)v1).shortValue();
                              short l2 = ((Number)v2).shortValue();
                              return evaluateExpr(this.type, l1, l2);
                           }

                           int l1 = ((Number)v1).intValue();
                           int l2 = ((Number)v2).intValue();
                           return evaluateExpr(this.type, l1, l2);
                        }

                        long l1 = ((Number)v1).longValue();
                        long l2 = ((Number)v2).longValue();
                        return evaluateExpr(this.type, l1, l2);
                     }

                     double l1 = ((Number)v1).doubleValue();
                     double l2 = ((Number)v2).doubleValue();
                     return evaluateExpr(this.type, l1, l2);
                  }
               } catch (NumberFormatException var10) {
                  throw new ExpressionEvaluationException("Unsupported JMS_BEA_SELECT() return value conversion: incompatible data types ", new RuntimeException("Unsupported JMS_BEA_SELECT() return value conversion: incompatible data types "));
               }
            } else {
               return null;
            }
         case 25:
            String temp = this.style.replace('\'', ' ').trim();
            if (!temp.equals(TYPE_NAMES[this.type])) {
               throw new ExpressionEvaluationException("Unknown expression syntax specified in JMS_BEA_SELECT(): " + this.style, new RuntimeException("Unknown expression syntax specified in JMS_BEA_SELECT(): " + this.style));
            } else {
               Object o = getXMLSelect(this.sval, context);
               if (o == null) {
                  return null;
               } else {
                  return (String)o;
               }
            }
      }
   }

   private static Object getXMLSelect(String expr, Object context) {
      Document doc = null;

      try {
         doc = (Document)((ExpressionMap)context).parse();
      } catch (Exception var5) {
         return null;
      }

      try {
         XMLSelection xmlSelectionObject = new XMLSelection();
         return xmlSelectionObject.filter(doc, expr);
      } catch (Exception var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private static Object evaluateExpr(int op, double v1, double v2) {
      switch (op) {
         case 5:
            return v1 == v2;
         case 6:
            return v1 < v2;
         case 7:
            return v1 > v2;
         case 8:
            return v1 <= v2;
         case 9:
            return v1 >= v2;
         case 10:
            return v1 != v2;
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
         default:
            throw new IllegalArgumentException("Not numeric operation: " + TYPE_NAMES[op]);
         case 21:
            return new Double(v1 - v2);
         case 22:
            return new Double(v1 + v2);
         case 23:
            return new Double(v1 * v2);
         case 24:
            return new Double(v1 / v2);
      }
   }

   private static Object evaluateExpr(int op, long v1, long v2) {
      switch (op) {
         case 5:
            return v1 == v2;
         case 6:
            return v1 < v2;
         case 7:
            return v1 > v2;
         case 8:
            return v1 <= v2;
         case 9:
            return v1 >= v2;
         case 10:
            return v1 != v2;
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
         default:
            throw new IllegalArgumentException("Not numeric operation: " + TYPE_NAMES[op]);
         case 21:
            return new Long(v1 - v2);
         case 22:
            return new Long(v1 + v2);
         case 23:
            return new Long(v1 * v2);
         case 24:
            return new Long(v1 / v2);
      }
   }

   private static Object evaluateExpr(int op, float v1, float v2) {
      switch (op) {
         case 6:
            return v1 < v2;
         case 7:
            return v1 > v2;
         case 8:
            return v1 <= v2;
         case 9:
            return v1 >= v2;
         case 10:
            return v1 != v2;
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
         default:
            throw new IllegalArgumentException("Not numeric operation: " + TYPE_NAMES[op]);
         case 21:
            return new Float(v1 - v2);
         case 22:
            return new Float(v1 + v2);
         case 23:
            return new Float(v1 * v2);
         case 24:
            return new Float(v1 / v2);
      }
   }

   private static Object evaluateExpr(int op, int v1, int v2) {
      switch (op) {
         case 6:
            return v1 < v2;
         case 7:
            return v1 > v2;
         case 8:
            return v1 <= v2;
         case 9:
            return v1 >= v2;
         case 10:
            return v1 != v2;
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
         default:
            throw new IllegalArgumentException("Not numeric operation: " + TYPE_NAMES[op]);
         case 21:
            return new Integer(v1 - v2);
         case 22:
            return new Integer(v1 + v2);
         case 23:
            return new Integer(v1 * v2);
         case 24:
            return new Integer(v1 / v2);
      }
   }

   private static Object evaluateExpr(int op, short v1, short v2) {
      switch (op) {
         case 6:
            return v1 < v2;
         case 7:
            return v1 > v2;
         case 8:
            return v1 <= v2;
         case 9:
            return v1 >= v2;
         case 10:
            return v1 != v2;
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
         default:
            throw new IllegalArgumentException("Not numeric operation: " + TYPE_NAMES[op]);
         case 21:
            return new Short((short)(v1 - v2));
         case 22:
            return new Short((short)(v1 + v2));
         case 23:
            return new Short((short)(v1 * v2));
         case 24:
            return new Short((short)(v1 / v2));
      }
   }

   private static Object evaluateExpr(int op, byte v1, byte v2) {
      switch (op) {
         case 6:
            return v1 < v2;
         case 7:
            return v1 > v2;
         case 8:
            return v1 <= v2;
         case 9:
            return v1 >= v2;
         case 10:
            return v1 != v2;
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
         default:
            throw new IllegalArgumentException("Not numeric operation: " + TYPE_NAMES[op]);
         case 21:
            return new Byte((byte)(v1 - v2));
         case 22:
            return new Byte((byte)(v1 + v2));
         case 23:
            return new Byte((byte)(v1 * v2));
         case 24:
            return new Byte((byte)(v1 / v2));
      }
   }

   private static boolean matchStrings(String pattern, String str, char esc) {
      return matchStrings(pattern, str, 0, 0, pattern.length(), str.length(), esc);
   }

   private static boolean matchStrings(String pattern, String string, int patternIndex, int stringIndex, int patternLen, int stringLen, char esc) {
      while(true) {
         if (patternIndex < patternLen && stringIndex < stringLen) {
            char pc = pattern.charAt(patternIndex);
            char sc = string.charAt(stringIndex);
            if (pc == esc) {
               ++patternIndex;
               pc = pattern.charAt(patternIndex);
               if (sc != pc) {
                  return false;
               }
            }

            if (pc == sc || pc == '_') {
               ++patternIndex;
               ++stringIndex;
               continue;
            }
         }

         if (patternIndex == patternLen && stringIndex == stringLen) {
            return true;
         }

         if (patternIndex == patternLen && stringIndex != stringLen) {
            return false;
         }

         if (patternIndex == patternLen - 1 && pattern.charAt(patternIndex) == '%') {
            return true;
         }

         if (pattern.charAt(patternIndex) == '%') {
            while(stringIndex < stringLen) {
               if (matchStrings(pattern, string, patternIndex + 1, stringIndex, patternLen, stringLen, esc)) {
                  return true;
               }

               ++stringIndex;
            }
         }

         return false;
      }
   }

   public String toString() {
      String val = this.toVal();
      return TYPE_NAMES[this.type] + ": " + (val == null ? this.term1 + "," + this.term2 : val);
   }

   private String toVal() {
      switch (this.type()) {
         case 17:
         case 18:
            return this.getSval();
         case 19:
            return this.getIval() + "";
         case 20:
            return this.getFval() + "";
         default:
            return null;
      }
   }

   public Type getType() {
      switch (this.type) {
         case 0:
         case 1:
         case 2:
         case 3:
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
            return Expression.Type.BOOLEAN;
         case 16:
         default:
            throw new AssertionError("Unknown expression type: " + this.type);
         case 17:
            return this.variable.getType();
         case 18:
            return Expression.Type.STRING;
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 26:
         case 27:
            return Expression.Type.NUMERIC;
         case 25:
            return Expression.Type.ANY;
      }
   }

   public boolean isNumeric() {
      switch (this.type) {
         case 17:
            return this.getType() == Expression.Type.NUMERIC || this.getType() == Expression.Type.ANY;
         case 18:
         default:
            return false;
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
            return true;
      }
   }

   public boolean isBoolean() {
      switch (this.type) {
         case 0:
         case 1:
         case 2:
         case 3:
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
         case 25:
            return true;
         case 16:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         default:
            return false;
         case 17:
            return this.getType() == Expression.Type.BOOLEAN || this.getType() == Expression.Type.ANY;
      }
   }

   public boolean isString() {
      switch (this.type) {
         case 17:
            return this.getType() == Expression.Type.STRING || this.getType() == Expression.Type.ANY;
         case 18:
         case 25:
            return true;
         default:
            return false;
      }
   }

   public boolean isIdentifier() {
      return this.type == 17;
   }

   public static void main(String[] args) {
      System.out.println(matchStrings("abc", "abc", '\u0000'));
      System.out.println(matchStrings("abc%", "abc", '\u0000'));
      System.out.println(matchStrings("a_c", "abc", '\u0000'));
      System.out.println(matchStrings("ab%c", "abc", '\u0000'));
      System.out.println(matchStrings("%c", "abc", '\u0000'));
      System.out.println(matchStrings("%", "abc", '\u0000'));
      System.out.println(matchStrings("\\_%", "_foo", '\\'));
      System.out.println(matchStrings("ab%cd%ef_", "abxxxcdyyefg", '\\'));
      System.out.println(!matchStrings("abcde", "abc", '\u0000'));
      System.out.println(!matchStrings("\\_%x", "_foo", '\\'));
   }

   public static class Type {
      public static final Type ANY = new Type();
      public static final Type NUMERIC = new Type();
      public static final Type BOOLEAN = new Type();
      public static final Type STRING = new Type();

      private Type() {
      }
   }
}
