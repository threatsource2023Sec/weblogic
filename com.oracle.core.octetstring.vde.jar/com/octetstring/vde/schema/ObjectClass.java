package com.octetstring.vde.schema;

import com.octetstring.vde.syntax.DirectoryString;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

public class ObjectClass {
   public static final int OC_ABSTRACT = 0;
   public static final int OC_STRUCTURAL = 1;
   public static final int OC_AUXILIARY = 2;
   private String oid = null;
   private DirectoryString name = null;
   private String description = null;
   private boolean obsolete = false;
   private DirectoryString superior = null;
   private int type = 1;
   private Vector must = null;
   private Vector may = null;
   private static final int TOK_NONE = -1;
   private static final int TOK_OID = 0;
   private static final int TOK_NAME = 1;
   private static final int TOK_DESC = 2;
   private static final int TOK_SUP = 3;
   private static final int TOK_MUST = 4;
   private static final int TOK_MAY = 5;

   public ObjectClass() {
      this.setMay(new Vector());
      this.setMust(new Vector());
   }

   public ObjectClass(String rfcclass) throws Exception {
      this.setMay(new Vector());
      this.setMust(new Vector());
      StringTokenizer st = new StringTokenizer(rfcclass);
      int where = 0;
      int paren = 0;

      while(true) {
         while(st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equals("(")) {
               ++paren;
            } else if (tok.equals(")")) {
               --paren;
            } else if (!tok.equals("$")) {
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
               } else if (paren == 1 && tok.equals("ABSTRACT")) {
                  this.setType(0);
               } else if (paren == 1 && tok.equals("STRUCTURAL")) {
                  this.setType(1);
               } else if (paren == 1 && tok.equals("AUXILIARY")) {
                  this.setType(2);
               } else if (paren == 1 && tok.equals("MUST")) {
                  where = 4;
               } else if (paren == 1 && tok.equals("MAY")) {
                  where = 5;
               } else if (where == 1) {
                  this.setName(new DirectoryString(tok));
               } else if (where == 2) {
                  this.setDescription(tok);
               } else if (where == 3) {
                  this.setSuperior(new DirectoryString(tok));
               } else if (where == 4) {
                  this.addMust(new DirectoryString(tok));
               } else if (where == 5) {
                  this.addMay(new DirectoryString(tok));
               } else if (where == -1) {
                  throw new Exception("Invalid Format");
               }
            }
         }

         return;
      }
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Vector getMay() {
      return this.may;
   }

   public void setMay(Vector may) {
      this.may = may;
   }

   public Vector getMust() {
      return this.must;
   }

   public void setMust(Vector must) {
      this.must = must;
   }

   public DirectoryString getName() {
      return this.name;
   }

   public void setName(DirectoryString name) {
      this.name = name;
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

   public DirectoryString getSuperior() {
      return this.superior;
   }

   public void setSuperior(DirectoryString superior) {
      this.superior = superior;
   }

   public int getType() {
      return this.type;
   }

   public void setType(int type) {
      this.type = type;
   }

   public void addMay(DirectoryString aMay) {
      Vector may = this.getMay();
      if (may == null) {
         may = new Vector();
      }

      may.addElement(aMay);
      this.may = may;
   }

   public void addMust(DirectoryString aMust) {
      Vector must = this.getMust();
      if (must == null) {
         must = new Vector();
      }

      must.addElement(aMust);
      this.must = must;
   }

   public String toString() {
      StringBuffer objClass = new StringBuffer();
      objClass = objClass.append("( ").append(this.getOid()).append(" NAME '").append(this.getName().toString()).append("'");
      if (this.description != null && !this.description.equals("")) {
         objClass = objClass.append(" DESC '").append(this.description).append("'");
      }

      if (this.isObsolete()) {
         objClass = objClass.append(" OBSOLETE");
      }

      if (this.getSuperior() != null) {
         objClass = objClass.append(" SUP ").append(this.getSuperior().toString());
      }

      if (this.getType() == 0) {
         objClass = objClass.append(" ABSTRACT");
      } else if (this.getType() == 2) {
         objClass = objClass.append(" AUXILIARY");
      }

      Enumeration enm;
      if (this.getMust() != null && !this.getMust().isEmpty()) {
         if (this.getMust().size() > 1) {
            objClass = objClass.append(" MUST ( ");
            enm = this.getMust().elements();

            while(enm.hasMoreElements()) {
               objClass = objClass.append(((DirectoryString)enm.nextElement()).toString());
               if (enm.hasMoreElements()) {
                  objClass = objClass.append(" $ ");
               }
            }

            objClass = objClass.append(" )");
         } else {
            objClass = objClass.append(" MUST ").append(((DirectoryString)this.getMust().elementAt(0)).toString());
         }
      }

      if (this.getMay() != null && !this.getMay().isEmpty()) {
         if (this.getMay().size() > 1) {
            objClass = objClass.append(" MAY ( ");
            enm = this.getMay().elements();

            while(enm.hasMoreElements()) {
               objClass = objClass.append(((DirectoryString)enm.nextElement()).toString());
               if (enm.hasMoreElements()) {
                  objClass = objClass.append(" $ ");
               }
            }

            objClass = objClass.append(" )");
         } else {
            objClass = objClass.append(" MAY ").append((DirectoryString)this.getMay().elementAt(0));
         }
      }

      objClass = objClass.append(" )");
      return objClass.toString();
   }
}
