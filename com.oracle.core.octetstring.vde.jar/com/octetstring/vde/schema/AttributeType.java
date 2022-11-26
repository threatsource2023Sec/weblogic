package com.octetstring.vde.schema;

import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.syntax.SyntaxMap;
import java.util.StringTokenizer;

public class AttributeType {
   private String oid = null;
   private DirectoryString name = null;
   private String description = null;
   private boolean obsolete = false;
   private DirectoryString superior = null;
   private String equalityMatch = null;
   private String orderingMatch = null;
   private String substrMatch = null;
   private String syntax = null;
   private boolean singleValue = false;
   private boolean collective = false;
   private boolean noUserModification = false;
   private int bound = 0;
   private String usage = "userApplications";
   private static final int TOK_NONE = -1;
   private static final int TOK_OID = 0;
   private static final int TOK_NAME = 1;
   private static final int TOK_DESC = 2;
   private static final int TOK_SUP = 3;
   private static final int TOK_EQUALITY = 4;
   private static final int TOK_ORDERING = 5;
   private static final int TOK_SUBSTR = 6;
   private static final int TOK_SYNTAX = 7;
   private static final int TOK_USAGE = 8;

   public AttributeType() {
   }

   public AttributeType(String rfctype) throws Exception {
      StringTokenizer st = new StringTokenizer(rfctype);
      int where = 0;
      int paren = 0;

      while(true) {
         while(st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equals("(")) {
               ++paren;
            } else if (tok.equals(")")) {
               --paren;
            } else {
               if (tok.startsWith("'")) {
                  String nexttok;
                  if (!tok.endsWith("'")) {
                     for(boolean gobble = true; gobble && st.hasMoreTokens(); tok = tok.concat(" " + nexttok)) {
                        nexttok = st.nextToken();
                        if (nexttok.endsWith("'")) {
                           gobble = false;
                        }
                     }
                  }

                  tok = tok.substring(1, tok.length() - 1);
               }

               if (where == 0) {
                  this.setOid(tok);
                  where = -1;
               } else if (paren == 1 && tok.equals("NAME")) {
                  where = 1;
               } else if (paren == 1 && tok.equals("DESC")) {
                  where = 2;
               } else if (paren == 1 && tok.equals("OBSOLETE")) {
                  where = -1;
                  this.setObsolete(true);
               } else if (paren == 1 && tok.equals("SUP")) {
                  where = 3;
               } else if (paren == 1 && tok.equals("EQUALITY")) {
                  where = 4;
               } else if (paren == 1 && tok.equals("ORDERING")) {
                  where = 5;
               } else if (paren == 1 && tok.equals("SUBSTR")) {
                  where = 6;
               } else if (paren == 1 && tok.equals("SYNTAX")) {
                  where = 7;
               } else if (paren == 1 && tok.equals("USAGE")) {
                  where = 8;
               } else if (paren == 1 && tok.equals("SINGLE-VALUE")) {
                  this.setSingleValue(true);
               } else if (paren == 1 && tok.equals("COLLECTIVE")) {
                  this.setCollective(true);
               } else if (paren == 1 && tok.equals("NO-USER-MODIFICATION")) {
                  this.setNoUserModification(true);
               } else if (where == 1) {
                  this.setName(new DirectoryString(tok));
                  where = -1;
               } else if (where == 2) {
                  this.setDescription(tok);
                  where = -1;
               } else if (where == 3) {
                  this.setSuperior(new DirectoryString(tok));
                  where = -1;
               } else if (where == 4) {
                  this.setEqualityMatch(tok);
                  where = -1;
               } else if (where == 5) {
                  this.setOrderingMatch(tok);
                  where = -1;
               } else if (where == 6) {
                  this.setSubstrMatch(tok);
                  where = -1;
               } else if (where == 7) {
                  int i = tok.indexOf("{");
                  if (i < 0) {
                     this.setSyntax(tok);
                  } else {
                     this.setSyntax(tok.substring(0, i));
                     int j = tok.indexOf("}");
                     this.setBound(new Integer(tok.substring(i + 1, j)));
                  }
               } else if (where == 8) {
                  this.setUsage(tok);
                  where = -1;
               } else if (where == -1) {
                  throw new Exception("Invalid Format");
               }
            }
         }

         return;
      }
   }

   public int getBound() {
      return this.bound;
   }

   public void setBound(int newBound) {
      this.bound = newBound;
   }

   public boolean isCollective() {
      return this.collective;
   }

   public void setCollective(boolean collective) {
      this.collective = collective;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getEqualityMatch() {
      return this.equalityMatch;
   }

   public void setEqualityMatch(String equalityMatch) {
      this.equalityMatch = equalityMatch;
   }

   public DirectoryString getName() {
      return this.name;
   }

   public void setName(DirectoryString name) {
      this.name = name;
   }

   public boolean isNoUserModification() {
      return this.noUserModification;
   }

   public void setNoUserModification(boolean noUserModification) {
      this.noUserModification = noUserModification;
   }

   public boolean isObsolete() {
      return this.obsolete;
   }

   public void setObsolete(boolean obsolete) {
      this.obsolete = obsolete;
   }

   public String getOid() {
      return this.oid;
   }

   public void setOid(String oid) {
      this.oid = oid;
   }

   public String getOrderingMatch() {
      return this.orderingMatch;
   }

   public void setOrderingMatch(String orderingMatch) {
      this.orderingMatch = orderingMatch;
   }

   public boolean isSingleValue() {
      return this.singleValue;
   }

   public void setSingleValue(boolean singleValue) {
      this.singleValue = singleValue;
   }

   public String getSubstrMatch() {
      return this.substrMatch;
   }

   public void setSubstrMatch(String substrMatch) {
      this.substrMatch = substrMatch;
   }

   public DirectoryString getSuperior() {
      return this.superior;
   }

   public void setSuperior(DirectoryString superior) {
      this.superior = superior;
   }

   public String getSyntax() {
      return this.syntax;
   }

   public void setSyntax(String syntax) {
      this.syntax = syntax;
   }

   public String getUsage() {
      return this.usage;
   }

   public void setUsage(String usage) {
      this.usage = usage;
   }

   public Class getSyntaxClass() {
      try {
         if (this.getSyntax() != null) {
            SyntaxMap.getInstance();
            return Class.forName(SyntaxMap.get(this.getSyntax()));
         } else {
            return DirectoryString.class;
         }
      } catch (Exception var2) {
         return DirectoryString.class;
      }
   }

   public Syntax getSyntaxInstance() {
      try {
         if (this.getSyntax() != null) {
            SyntaxMap.getInstance();
            return (Syntax)Class.forName(SyntaxMap.get(this.getSyntax())).newInstance();
         } else {
            return new DirectoryString();
         }
      } catch (Exception var2) {
         return new DirectoryString();
      }
   }

   public String toString() {
      StringBuffer atstr = new StringBuffer();
      atstr.append("( ").append(this.oid).append(" ");
      atstr.append("NAME '").append(this.name).append("'");
      if (this.description != null) {
         atstr.append(" DESC '").append(this.description).append("'");
      }

      if (this.isObsolete()) {
         atstr.append(" OBSOLETE");
      }

      if (this.superior != null) {
         atstr.append(" SUP ").append(this.superior);
      }

      if (this.equalityMatch != null) {
         atstr.append(" EQUALITY ").append(this.equalityMatch);
      }

      if (this.orderingMatch != null) {
         atstr.append(" ORDERING ").append(this.orderingMatch);
      }

      if (this.substrMatch != null) {
         atstr.append(" SUBSTR ").append(this.substrMatch);
      }

      if (this.syntax != null) {
         atstr.append(" SYNTAX ").append(this.syntax);
         if (this.bound != 0) {
            atstr.append("{").append(this.bound).append("}");
         }
      }

      if (this.isSingleValue()) {
         atstr.append(" SINGLE-VALUE");
      }

      if (this.isCollective()) {
         atstr.append(" COLLECTIVE");
      }

      if (this.usage != null) {
         atstr.append(" USAGE ").append(this.usage);
      }

      atstr.append(" )");
      return atstr.toString();
   }
}
