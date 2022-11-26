package com.bea.security.providers.xacml.entitlement.parser;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.security.xacml.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Parser {
   private static final char UNION = '|';
   private static final char INTERSECT = '&';
   private static final char DIFFERENCE = '-';
   private static final char COMPLEMENT = '~';
   private static final char USERS_START = 'U';
   private static final char GROUPS_START = 'G';
   private static final char ROLES_START = 'R';
   private static final String USERS = "sr";
   private static final String GROUPS = "rp";
   private static final String ROLES = "ol";
   private static final char PREDICATE = '?';
   private static final char OPEN_ARGS = '(';
   private static final char CLOSE_ARGS = ')';
   private static final char LIST_SEPARATOR = ',';
   private static final String STRING_DELIM = ",";
   private static final char OPEN = '{';
   private static final char CLOSE = '}';
   private static final int UNION_ALLOWED = 1;
   private static final int INTERSECT_ALLOWED = 2;
   private static final int DIFFERENCE_ALLOWED = 4;
   private static final int COMPLEMENT_ALLOWED = 8;
   private static final int USERS_ALLOWED = 16;
   private static final int GROUPS_ALLOWED = 32;
   private static final int ROLES_ALLOWED = 64;
   private static final int PREDICATE_ALLOWED = 128;
   private static final int OPEN_ALLOWED = 256;
   private static final int INITIAL_ALLOWED = 504;
   private static final int AFTER_SINGLETON_EXPRESSION = 7;
   private static final char USR_IDENTIFIER = 'u';
   private static final char GRP_IDENTIFIER = 'g';
   private static final char ROL_IDENTIFIER = 'r';
   private static final char PRE_IDENTIFIER = 'p';
   private static final char EMP_IDENTIFIER = 'e';
   private static final char USER_UNION = 'U';
   private static final char GROUP_UNION = 'G';
   private static final char ROLE_UNION = 'R';
   private static final char SPACE = ' ';
   private static final char TAB = '\t';
   private static final char NEW_LINE = '\n';
   private static final Escaping escaper = new Escaping(new char[]{'#', '|', '&', '-', '~', '(', ')', ',', '{', '}', ' ', '\t', '?', '\n'});

   static String escape(String input) {
      return escaper.escapeString(input);
   }

   static String unescape(String input) {
      return escaper.unescapeString(input);
   }

   public static Expression generateExpression(String expression, boolean isRoleExpression) throws DocumentParseException {
      return generateExpression(expression, 504, isRoleExpression);
   }

   private static Expression generateExpression(String expression, int allowMask, boolean isRoleExpression) throws DocumentParseException {
      Expression left = null;

      for(int i = 0; i < expression.length(); ++i) {
         Object current;
         char next = expression.charAt(i);
         current = null;
         int idx;
         label204:
         switch (next) {
            case ' ':
               continue;
            case '&':
               if ((allowMask & 2) == 0) {
                  throw new DocumentParseException("Invalid placement of '&' operator");
               }

               left = new Intersect((Expression)left);
               allowMask = 504;
               break;
            case '-':
               if ((allowMask & 4) == 0) {
                  throw new DocumentParseException("Invalid placement of '-' operator");
               }

               left = new Difference((Expression)left);
               allowMask = 504;
               break;
            case '?':
               if ((allowMask & 128) == 0) {
                  throw new DocumentParseException("Invalid placement of '?' expression");
               }

               ++i;
               idx = expression.indexOf(40, i);
               if (idx < 0) {
                  throw new DocumentParseException("Opening '(' not found");
               }

               int open = idx;
               idx = expression.indexOf(41, idx);
               if (idx < 0) {
                  throw new DocumentParseException("Closing ')' not found");
               }

               current = new Predicate(expression.substring(i, open), parseStringList(expression.substring(open + 1, idx)));
               i = idx;
               allowMask = 7;
               break;
            case 'G':
               if (i + 2 >= expression.length() || expression.charAt(i + 1) != "rp".charAt(0) || expression.charAt(i + 2) != "rp".charAt(1)) {
                  throw new DocumentParseException("Invalid token starting with 'G' detected");
               }

               if ((allowMask & 32) == 0) {
                  throw new DocumentParseException("Invalid placement of 'Grp' token");
               }

               i += 3;
               if (i >= expression.length() || expression.charAt(i) != '(') {
                  throw new DocumentParseException("'Grp' must be followed by '('");
               }

               idx = expression.indexOf(41, i);
               if (idx < 0) {
                  throw new DocumentParseException("Closing ')' not found");
               }

               current = new Groups(parseStringList(expression.substring(i + 1, idx)));
               i = idx;
               allowMask = 7;
               break;
            case 'R':
               if (i + 2 >= expression.length() || expression.charAt(i + 1) != "ol".charAt(0) || expression.charAt(i + 2) != "ol".charAt(1)) {
                  throw new DocumentParseException("Invalid token starting with 'R' detected");
               }

               if ((allowMask & 64) == 0) {
                  throw new DocumentParseException("Invalid placement of 'Rol' token");
               }

               if (isRoleExpression) {
                  throw new DocumentParseException("Role tokens are not permitted in role expressions");
               }

               i += 3;
               if (i >= expression.length() || expression.charAt(i) != '(') {
                  throw new DocumentParseException("'Rol' must be followed by '('");
               }

               idx = expression.indexOf(41, i);
               if (idx < 0) {
                  throw new DocumentParseException("Closing ')' not found");
               }

               current = new Roles(parseStringList(expression.substring(i + 1, idx)));
               i = idx;
               allowMask = 7;
               break;
            case 'U':
               if (i + 2 < expression.length() && expression.charAt(i + 1) == "sr".charAt(0) && expression.charAt(i + 2) == "sr".charAt(1)) {
                  if ((allowMask & 16) == 0) {
                     throw new DocumentParseException("Invalid placement of 'Usr' token");
                  }

                  i += 3;
                  if (i >= expression.length() || expression.charAt(i) != '(') {
                     throw new DocumentParseException("'Usr' must be followed by '('");
                  }

                  idx = expression.indexOf(41, i);
                  if (idx < 0) {
                     throw new DocumentParseException("Closing ')' not found");
                  }

                  current = new Users(parseStringList(expression.substring(i + 1, idx)));
                  i = idx;
                  allowMask = 7;
                  break;
               }

               throw new DocumentParseException("Invalid token starting with 'U' detected");
            case '{':
               if ((allowMask & 256) == 0) {
                  throw new DocumentParseException("Invalid placement of '{'");
               }

               int count = 1;
               ++i;
               int idx = i;

               while(true) {
                  label223: {
                     if (idx < expression.length()) {
                        if (expression.charAt(idx) == '{') {
                           ++count;
                           break label223;
                        }

                        if (expression.charAt(idx) != '}') {
                           break label223;
                        }

                        --count;
                        if (count != 0) {
                           break label223;
                        }
                     }

                     if (idx >= expression.length()) {
                        throw new DocumentParseException("Matching closing '}' not found");
                     }

                     current = generateExpression(expression.substring(i, idx), 504, isRoleExpression);
                     i = idx;
                     allowMask = 7;
                     break label204;
                  }

                  ++idx;
               }
            case '|':
               if ((allowMask & 1) == 0) {
                  throw new DocumentParseException("Invalid placement of '|' operator");
               }

               left = new Union((Expression)left);
               allowMask = 504;
               break;
            case '~':
               if ((allowMask & 8) == 0) {
                  throw new DocumentParseException("Invalid placement of '~' operator");
               }

               current = new Complement();
               allowMask = 504;
               break;
            default:
               throw new DocumentParseException("Unknown token detected: " + next);
         }

         if (current != null) {
            if (left != null) {
               if (left instanceof BinaryOp) {
                  BinaryOp bo = (BinaryOp)left;
                  if (bo.hasRight()) {
                     Expression e = bo.getRight();
                     if (e instanceof UnaryOp) {
                        UnaryOp uo = (UnaryOp)e;
                        if (!uo.hasOp()) {
                           uo.setOp((Expression)current);
                           continue;
                        }

                        e = uo.getOp();
                     }

                     throw new DocumentParseException("Illegal expression or placement token following binary op");
                  } else {
                     bo.setRight((Expression)current);
                  }
               } else {
                  if (left instanceof UnaryOp) {
                     UnaryOp uo = (UnaryOp)left;
                     if (!uo.hasOp()) {
                        uo.setOp((Expression)current);
                        continue;
                     }

                     Expression e = uo.getOp();
                  }

                  throw new DocumentParseException("Illegal expression or placement token");
               }
            } else {
               left = current;
            }
         }
      }

      if (left != null) {
         return (Expression)left;
      } else {
         return null;
      }
   }

   private static StringList parseStringList(String expression) throws DocumentParseException {
      StringList sl = new StringList();
      StringTokenizer st = new StringTokenizer(expression, ",");

      while(st.hasMoreElements()) {
         sl.getData().add(unescape(st.nextToken()));
      }

      return sl;
   }

   public static String getPersistantForm(Expression e) throws IOException {
      StringWriter sw = new StringWriter();
      if (e != null) {
         e.writePersistantForm(sw);
      }

      return sw.toString();
   }

   public static Expression parsePersistantForm(String expression) throws DocumentParseException {
      return expression != null && expression.length() != 0 ? parsePersistantForm(expression, new ParseState(0)) : null;
   }

   private static Expression parsePersistantForm(String r, ParseState state) throws DocumentParseException {
      if (r.length() > state.getIdx()) {
         int uncount;
         int eos;
         int eos;
         switch ((char)(r.charAt(state.idxPP()) & 127)) {
            case '&':
               int incount = r.charAt(state.idxPP());
               if (incount < 1) {
                  throw new DocumentParseException("Intersect must specify at least one argument");
               }

               List inexprs = new ArrayList();

               for(int i = 0; i < incount; ++i) {
                  inexprs.add(parsePersistantForm(r, state));
               }

               while(inexprs.size() >= 2) {
                  Expression e2 = (Expression)inexprs.remove(inexprs.size() - 1);
                  Expression e1 = (Expression)inexprs.remove(inexprs.size() - 1);
                  inexprs.add(new Intersect(e1, e2));
               }

               return (Expression)inexprs.get(0);
            case '-':
               switch (r.charAt(state.idxPP())) {
                  case '\u0001':
                     return new Difference(parsePersistantForm(r, state));
                  case '\u0002':
                     return new Difference(parsePersistantForm(r, state), parsePersistantForm(r, state));
                  default:
                     throw new DocumentParseException("Difference must specify exactly 1 or two arguments");
               }
            case 'G':
               StringList glist = new StringList();
               int gcount = r.charAt(state.idxPP());

               for(int i = 0; i < gcount; ++i) {
                  if ((char)(r.charAt(state.idxPP()) & 127) != 'g') {
                     throw new DocumentParseException("Groups list may only specify group identifiers");
                  }

                  eos = r.indexOf(10, state.getIdx());
                  if (eos < 0) {
                     throw new DocumentParseException("String literal missing terminating newline character");
                  }

                  glist.getData().add(r.substring(state.getIdx(), eos));
                  state.setIdx(eos + 1);
               }

               return new Groups(glist);
            case 'R':
               StringList rlist = new StringList();
               int rcount = r.charAt(state.idxPP());

               for(int i = 0; i < rcount; ++i) {
                  if ((char)(r.charAt(state.idxPP()) & 127) != 'r') {
                     throw new DocumentParseException("Roles list may only specify role identifiers");
                  }

                  int eos = r.indexOf(10, state.getIdx());
                  if (eos < 0) {
                     throw new DocumentParseException("String literal missing terminating newline character");
                  }

                  rlist.getData().add(r.substring(state.getIdx(), eos));
                  state.setIdx(eos + 1);
               }

               return new Roles(rlist);
            case 'U':
               StringList ulist = new StringList();
               int ucount = r.charAt(state.idxPP());

               for(int i = 0; i < ucount; ++i) {
                  if ((char)(r.charAt(state.idxPP()) & 127) != 'u') {
                     throw new DocumentParseException("Users list may only specify user identifiers");
                  }

                  eos = r.indexOf(10, state.getIdx());
                  if (eos < 0) {
                     throw new DocumentParseException("String literal missing terminating newline character");
                  }

                  ulist.getData().add(r.substring(state.getIdx(), eos));
                  state.setIdx(eos + 1);
               }

               return new Users(ulist);
            case 'e':
               return null;
            case 'g':
               StringList glist1 = new StringList();
               eos = r.indexOf(10, state.getIdx());
               if (eos < 0) {
                  throw new DocumentParseException("String literal missing terminating newline character");
               }

               glist1.getData().add(r.substring(state.getIdx(), eos));
               state.setIdx(eos + 1);
               return new Groups(glist1);
            case 'p':
               int neos = r.indexOf(10, state.getIdx());
               if (neos < 0) {
                  throw new DocumentParseException("String literal missing terminating newline character");
               }

               String name = r.substring(state.getIdx(), neos);
               state.setIdx(neos + 1);
               StringList plist = new StringList();
               int pcount = r.charAt(state.idxPP());

               for(uncount = 0; uncount < pcount; ++uncount) {
                  int eos = r.indexOf(10, state.getIdx());
                  if (eos < 0) {
                     throw new DocumentParseException("String literal missing terminating newline character");
                  }

                  plist.getData().add(r.substring(state.getIdx(), eos));
                  state.setIdx(eos + 1);
               }

               return new Predicate(name, plist);
            case 'r':
               StringList rlist1 = new StringList();
               eos = r.indexOf(10, state.getIdx());
               if (eos < 0) {
                  throw new DocumentParseException("String literal missing terminating newline character");
               }

               rlist1.getData().add(r.substring(state.getIdx(), eos));
               state.setIdx(eos + 1);
               return new Roles(rlist1);
            case 'u':
               StringList ulist1 = new StringList();
               int eos1 = r.indexOf(10, state.getIdx());
               if (eos1 < 0) {
                  throw new DocumentParseException("String literal missing terminating newline character");
               }

               ulist1.getData().add(r.substring(state.getIdx(), eos1));
               state.setIdx(eos1 + 1);
               return new Users(ulist1);
            case '|':
               uncount = r.charAt(state.idxPP());
               if (uncount < 1) {
                  throw new DocumentParseException("Union must specify at least one argument");
               }

               List unexprs = new ArrayList();

               for(int i = 0; i < uncount; ++i) {
                  unexprs.add(parsePersistantForm(r, state));
               }

               while(unexprs.size() >= 2) {
                  Expression e2 = (Expression)unexprs.remove(unexprs.size() - 1);
                  Expression e1 = (Expression)unexprs.remove(unexprs.size() - 1);
                  unexprs.add(new Union(e1, e2));
               }

               return (Expression)unexprs.get(0);
            case '~':
               switch (r.charAt(state.idxPP())) {
                  case '\u0001':
                     return new Complement(parsePersistantForm(r, state));
                  default:
                     throw new DocumentParseException("Complement must specify exactly 1 argument");
               }
         }
      }

      return null;
   }

   private static class ParseState {
      private int idx;

      public ParseState(int idx) {
         this.idx = idx;
      }

      public int getIdx() {
         return this.idx;
      }

      public void setIdx(int idx) {
         this.idx = idx;
      }

      public int idxPP() {
         return this.idx++;
      }
   }
}
