package weblogic.entitlement.parser;

import weblogic.entitlement.expression.Difference;
import weblogic.entitlement.expression.EExprRep;
import weblogic.entitlement.expression.EExpression;
import weblogic.entitlement.expression.Empty;
import weblogic.entitlement.expression.GroupIdentifier;
import weblogic.entitlement.expression.GroupList;
import weblogic.entitlement.expression.Intersection;
import weblogic.entitlement.expression.InvalidPredicateClassException;
import weblogic.entitlement.expression.Inverse;
import weblogic.entitlement.expression.PredicateOp;
import weblogic.entitlement.expression.RoleIdentifier;
import weblogic.entitlement.expression.RoleList;
import weblogic.entitlement.expression.Union;
import weblogic.entitlement.expression.UserIdentifier;
import weblogic.entitlement.expression.UserList;
import weblogic.entitlement.util.Escaping;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;

public class Parser {
   public static final char UNION = '|';
   public static final char INTERSECT = '&';
   public static final char DIFFERENCE = '-';
   public static final char COMPLEMENT = '~';
   public static final char PREDICATE = '?';
   public static final String USR_LIST = "Usr";
   public static final String GROUP_LIST = "Grp";
   public static final String ROLE_LIST = "Rol";
   public static final char LIST_START = '(';
   public static final char LIST_END = ')';
   public static final char LIST_SEPARATOR = ',';
   public static final char SUB_EEXPR_OPEN = '{';
   public static final char SUB_EEXPR_CLOSE = '}';
   public static final char SPACE = ' ';
   public static final char TAB = '\t';
   public static final char NEW_LINE = '\n';
   public static final char USR_IDENTIFIER = 'u';
   public static final char GRP_IDENTIFIER = 'g';
   public static final char ROL_IDENTIFIER = 'r';
   public static final char PRE_IDENTIFIER = 'p';
   public static final char EMP_IDENTIFIER = 'e';
   public static final char USER_UNION = 'U';
   public static final char GROUP_UNION = 'G';
   public static final char ROLE_UNION = 'R';
   public static final String UNCHECKED_EXPR = "{ ?weblogic.entitlement.rules.UncheckedPolicy() }";
   public static final String EXCLUDED_EXPR = "{ ?weblogic.entitlement.rules.ExcludedPolicy() }";
   private static final Escaping escaper = new Escaping(new char[]{'#', '|', '&', '-', '~', '(', ')', ',', '{', '}', ' ', '\t', '?', '\n'});

   public static EExpression parseResourceExpression(String expr) throws ParseException, IllegalPredicateArgumentException, InvalidPredicateClassException {
      char[] e = expr.toCharArray();
      return parse(e, new int[]{0}, new int[]{e.length}, 0, true);
   }

   public static EExpression parseRoleExpression(String expr) throws ParseException, IllegalPredicateArgumentException, InvalidPredicateClassException {
      char[] e = expr.toCharArray();
      return parse(e, new int[]{0}, new int[]{e.length}, 0, false);
   }

   public static String uncheckedExpr() {
      return "{ ?weblogic.entitlement.rules.UncheckedPolicy() }";
   }

   public static String excludedExpr() {
      return "{ ?weblogic.entitlement.rules.ExcludedPolicy() }";
   }

   public static String roles2Expr(String[] roleNames) {
      return toExpr(roleNames, "Rol");
   }

   public static String users2Expr(String[] userNames) {
      return toExpr(userNames, "Usr");
   }

   public static String groups2Expr(String[] groupNames) {
      return toExpr(groupNames, "Grp");
   }

   public static String escape(String in) {
      return escaper.escapeString(in);
   }

   public static String unescape(String in) {
      return escaper.unescapeString(in);
   }

   private static String toExpr(String[] names, String what) {
      if (names.length == 0) {
         return "";
      } else {
         int len = 0;

         for(int i = 0; i < names.length; ++i) {
            len += names[i].length();
         }

         StringBuffer buf = new StringBuffer(len * 2);
         buf.append('{');
         buf.append(what);
         buf.append('(');
         buf.append(escaper.escapeString(names[0]));

         for(int i = 1; i < names.length; ++i) {
            buf.append(',');
            buf.append(escaper.escapeString(names[i]));
         }

         buf.append(')');
         buf.append('}');
         return buf.toString();
      }
   }

   private static EExprRep parse(char[] e, int[] start, int[] end, int nest, boolean allowRoles) throws ParseException, IllegalPredicateArgumentException, InvalidPredicateClassException {
      int charCount = 0;
      int i = start[0];

      for(int level = 0; i < end[0] && level >= 0; ++i) {
         if (e[i] == '{') {
            ++level;
         } else if (e[i] == '}') {
            --level;
         }

         if (level == 0) {
            ++charCount;
         }
      }

      i = 2 + charCount / 2;
      String[] ele = new String[i];
      EExprRep[] operands = new EExprRep[i];
      int operandCount = 0;
      EExprRep expr = null;
      char[] opStack = new char[2];
      int opStackIdx = -1;
      char lastElement = false;
      skipSpace(e, start, end, (String)null);

      EExprRep[] args;
      for(; start[0] < end[0]; skipSpace(e, start, end, (String)null)) {
         int count;
         int i;
         int var10002;
         switch (e[start[0]]) {
            case '&':
            case '-':
            case '|':
            case '~':
               if (expr != null) {
                  operands[operandCount++] = (EExprRep)expr;
                  expr = null;
               }

               if (opStackIdx >= 0 && opStack[opStackIdx] == e[start[0]]) {
                  if ('~' == e[start[0]]) {
                     --opStackIdx;
                  }
               } else {
                  if (opStackIdx >= 0 && '~' != e[start[0]]) {
                     args = new EExprRep[operandCount];
                     System.arraycopy(operands, 0, args, 0, operandCount);
                     switch (opStack[opStackIdx--]) {
                        case '&':
                           expr = new Intersection(args);
                           break;
                        case '-':
                           expr = new Difference(args);
                           break;
                        case '|':
                           expr = new Union(args);
                     }

                     int operandCount = 0;
                     operandCount = operandCount + 1;
                     operands[operandCount] = (EExprRep)expr;
                     expr = null;
                  }

                  ++opStackIdx;
                  opStack[opStackIdx] = e[start[0]];
               }
               break;
            case '?':
               if (expr != null) {
                  throw new ParseException(syntaxMsg("Operator missing", e, start[0]));
               }

               var10002 = start[0]++;
               skipSpace(e, start, end, "Predicate class name is expected");

               for(count = start[0]; start[0] < end[0] && e[start[0]] != '(' && !isWhitespace(e[start[0]]); var10002 = start[0]++) {
                  validateChar(e[start[0]], start[0]);
               }

               String name = new String(e, count, start[0] - count);
               skipSpace(e, start, end, "Missing '('.");
               count = parseToCloseParam(e, start, end, ele, true);

               String[] param;
               for(param = new String[count]; count-- > 0; param[count] = escaper.unescapeString(ele[count])) {
               }

               expr = new PredicateOp(name, param);
               break;
            case 'G':
            case 'g':
               if (e[start[0] + 1] != 'r' || e[start[0] + 2] != 'p') {
                  throw new ParseException(syntaxMsg("Unknown word", e, start[0]));
               }

               if (expr != null) {
                  throw new ParseException(syntaxMsg("Operator missing", e, start[0]));
               }

               start[0] += 3;
               skipSpace(e, start, end, "Incomplete 'Grp()' construct.");
               count = parseToCloseParam(e, start, end, ele, false);
               if (count == 0) {
                  throw new ParseException("Missing arguments for 'Grp()' construct.");
               }

               if (count == 1) {
                  expr = new GroupIdentifier(escaper.unescapeString(ele[0]));
               } else {
                  GroupIdentifier[] u = new GroupIdentifier[count];

                  for(i = 0; i < count; ++i) {
                     u[i] = new GroupIdentifier(escaper.unescapeString(ele[i]));
                  }

                  expr = new GroupList(u);
               }
               break;
            case 'R':
            case 'r':
               if (e[start[0] + 1] != 'o' || e[start[0] + 2] != 'l') {
                  throw new ParseException(syntaxMsg("Unknown word", e, start[0]));
               }

               if (expr != null) {
                  throw new ParseException(syntaxMsg("Operator missing", e, start[0]));
               }

               if (!allowRoles) {
                  throw new ParseException(syntaxMsg("Role within role is not allowed", e, start[0]));
               }

               start[0] += 3;
               skipSpace(e, start, end, "Incomplete 'Rol()' construct.");
               count = parseToCloseParam(e, start, end, ele, false);
               if (count == 0) {
                  throw new ParseException("Missing arguments for 'Rol()' construct.");
               }

               if (count == 1) {
                  expr = new RoleIdentifier(escaper.unescapeString(ele[0]));
               } else {
                  RoleIdentifier[] u = new RoleIdentifier[count];

                  for(i = 0; i < count; ++i) {
                     u[i] = new RoleIdentifier(escaper.unescapeString(ele[i]));
                  }

                  expr = new RoleList(u);
               }
               break;
            case 'U':
            case 'u':
               if (e[start[0] + 1] == 's' && e[start[0] + 2] == 'r') {
                  if (expr != null) {
                     throw new ParseException(syntaxMsg("Operator missing", e, start[0]));
                  }

                  start[0] += 3;
                  skipSpace(e, start, end, "Incomplete 'Usr()' construct.");
                  count = parseToCloseParam(e, start, end, ele, false);
                  if (count == 0) {
                     throw new ParseException("Missing arguments for 'Usr()' construct.");
                  }

                  if (count == 1) {
                     expr = new UserIdentifier(escaper.unescapeString(ele[0]));
                  } else {
                     UserIdentifier[] u = new UserIdentifier[count];

                     for(i = 0; i < count; ++i) {
                        u[i] = new UserIdentifier(escaper.unescapeString(ele[i]));
                     }

                     expr = new UserList(u);
                  }
                  break;
               }

               throw new ParseException(syntaxMsg("Unknown word", e, start[0]));
            case '{':
               if (expr != null) {
                  throw new ParseException(syntaxMsg("Operator missing", e, start[0]));
               }

               var10002 = start[0]++;
               expr = parse(e, start, end, nest + 1, allowRoles);
               break;
            case '}':
               if (nest == 0) {
                  throw new ParseException(syntaxMsg("Extra '}'.", e, start[0]));
               }

               if (expr != null) {
                  operands[operandCount++] = (EExprRep)expr;
                  expr = null;
               }

               if (operandCount > 0) {
                  if (opStackIdx >= 0) {
                     args = new EExprRep[operandCount];
                     System.arraycopy(operands, 0, args, 0, operandCount);
                     switch (opStack[opStackIdx--]) {
                        case '&':
                           expr = new Intersection(args);
                           break;
                        case '-':
                           expr = new Difference(args);
                           break;
                        case '|':
                           expr = new Union(args);
                     }

                     int operandCount = false;
                  } else {
                     expr = operands[0];
                  }
               }

               if (opStackIdx >= 0) {
                  throw new ParseException(syntaxMsg("Missing operand for operator '" + opStack[opStackIdx] + "'.", e, start[0]));
               }

               var10002 = start[0]++;
               if (expr != null && expr != Empty.EMPTY) {
                  ((EExprRep)expr).SetEnclosed();
               }

               return (EExprRep)(expr == null ? Empty.EMPTY : expr);
            default:
               throw new ParseException(syntaxMsg("Unknown word", e, start[0]));
         }

         if (expr == null) {
            var10002 = start[0]++;
         } else if (opStackIdx >= 0 && opStack[opStackIdx] == '~') {
            --opStackIdx;
            expr = new Inverse((EExprRep)expr);
         }
      }

      if (opStackIdx >= 0) {
         if (expr != null) {
            operands[operandCount++] = (EExprRep)expr;
            expr = null;
         }

         args = new EExprRep[operandCount];
         System.arraycopy(operands, 0, args, 0, operandCount);
         switch (opStack[opStackIdx--]) {
            case '&':
               expr = new Intersection(args);
               break;
            case '-':
               expr = new Difference(args);
               break;
            case '|':
               expr = new Union(args);
         }
      }

      if (nest != 0) {
         throw new ParseException(syntaxMsg("Unbalanced '{'.", e, start[0]));
      } else {
         return (EExprRep)expr;
      }
   }

   private static void skipSpace(char[] e, int[] start, int[] end, String msg) throws ParseException {
      while(start[0] < end[0]) {
         if (!isWhitespace(e[start[0]])) {
            return;
         }

         int var10002 = start[0]++;
      }

      if (msg != null) {
         throw new ParseException(syntaxMsg(msg, e, start[0]));
      }
   }

   private static boolean isWhitespace(char c) {
      return c == ' ' || c == '\t' || c == '\n';
   }

   private static int parseToCloseParam(char[] e, int[] start, int[] end, String[] ele, boolean allowQuote) throws ParseException {
      int var10004 = start[0];
      int var10001 = start[0];
      start[0] = var10004 + 1;
      if (e[var10001] != '(') {
         throw new ParseException(syntaxMsg("Missing '('.", e, start[0]));
      } else {
         skipSpace(e, start, end, "Missing ')'.");
         int count = 0;
         int saveEnd = -1;
         int inQuote = false;
         int saveStart = start[0];
         int var10002;
         if (allowQuote && e[start[0]] == '"') {
            inQuote = true;
            var10002 = start[0]++;
         }

         while(true) {
            while(true) {
               if (inQuote) {
                  if (start[0] == end[0]) {
                     throw new ParseException(syntaxMsg("Unbalanced quote '\"'.", e, start[0]));
                  }

                  if (e[start[0]] != '"') {
                     break;
                  }

                  saveEnd = ++start[0];
                  inQuote ^= true;
               } else {
                  if (start[0] == end[0]) {
                     throw new ParseException(syntaxMsg("Missing ')'.", e, start[0]));
                  }

                  if (e[start[0]] == '(') {
                     throw new ParseException(syntaxMsg("Unexpected nested '('.", e, start[0]));
                  }

                  if (e[start[0]] == '{') {
                     throw new ParseException(syntaxMsg("Unexpected '{' inside '(...)'.", e, start[0]));
                  }

                  if (isWhitespace(e[start[0]])) {
                     if (saveEnd == -1) {
                        saveEnd = start[0];
                     }

                     skipSpace(e, start, end, "Missing ')'.");
                  }

                  if (e[start[0]] != ',' && e[start[0]] != ')') {
                     if (saveEnd != -1) {
                        throw new ParseException(syntaxMsg("Missing ',' delimiter.", e, start[0]));
                     }
                     break;
                  }

                  if (saveEnd == -1) {
                     saveEnd = start[0];
                  }

                  if (saveEnd == saveStart) {
                     if (allowQuote) {
                        var10004 = start[0];
                        var10001 = start[0];
                        start[0] = var10004 + 1;
                        if (e[var10001] == ')') {
                           skipSpace(e, start, end, (String)null);
                           return count;
                        }
                     }

                     throw new ParseException(syntaxMsg("Missing name.", e, start[0]));
                  }

                  ele[count++] = new String(e, saveStart, saveEnd - saveStart);
                  var10004 = start[0];
                  var10001 = start[0];
                  start[0] = var10004 + 1;
                  if (e[var10001] == ')') {
                     skipSpace(e, start, end, (String)null);
                     return count;
                  }

                  skipSpace(e, start, end, "Missing ')'.");
                  saveStart = start[0];
                  saveEnd = -1;
               }
            }

            validateChar(e[start[0]], start[0]);
            var10002 = start[0]++;
         }
      }
   }

   private static void validateChar(char c, int i) throws ParseException {
      switch (c) {
         case '&':
         case ')':
         case ',':
         case '-':
         case '?':
         case '{':
         case '|':
         case '~':
            throw new ParseException("Character '" + c + "'not allowed.", i);
         default:
      }
   }

   private static String syntaxMsg(String m, char[] e, int index) {
      return m + " for '" + new String(e) + "' at position:" + index;
   }

   public static void main(String[] args) throws Exception {
      EExpression expr = parseResourceExpression(args[0]);
      System.out.println("Expression: " + expr.externalize());
   }
}
