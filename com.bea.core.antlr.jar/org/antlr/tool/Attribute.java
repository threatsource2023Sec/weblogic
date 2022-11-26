package org.antlr.tool;

public class Attribute {
   public String decl;
   public String type;
   public String name;
   public String initValue;

   public Attribute(String decl) {
      this.extractAttribute(decl);
   }

   public Attribute(String name, String decl) {
      this.name = name;
      this.decl = decl;
   }

   protected void extractAttribute(String decl) {
      if (decl != null) {
         boolean inID = false;
         int start = -1;
         int rightEdgeOfDeclarator = decl.length() - 1;
         int equalsIndex = decl.indexOf(61);
         if (equalsIndex > 0) {
            this.initValue = decl.substring(equalsIndex + 1, decl.length());
            rightEdgeOfDeclarator = equalsIndex - 1;
         }

         int stop;
         for(stop = rightEdgeOfDeclarator; stop >= 0; --stop) {
            if (!inID && Character.isLetterOrDigit(decl.charAt(stop))) {
               inID = true;
            } else if (inID && !Character.isLetterOrDigit(decl.charAt(stop)) && decl.charAt(stop) != '_') {
               start = stop + 1;
               break;
            }
         }

         if (start < 0 && inID) {
            start = 0;
         }

         if (start < 0) {
            ErrorManager.error(104, (Object)decl);
         }

         stop = -1;

         for(int i = start; i <= rightEdgeOfDeclarator; ++i) {
            if (!Character.isLetterOrDigit(decl.charAt(i)) && decl.charAt(i) != '_') {
               stop = i;
               break;
            }

            if (i == rightEdgeOfDeclarator) {
               stop = i + 1;
            }
         }

         this.name = decl.substring(start, stop);
         this.type = decl.substring(0, start);
         if (stop <= rightEdgeOfDeclarator) {
            this.type = this.type + decl.substring(stop, rightEdgeOfDeclarator + 1);
         }

         this.type = this.type.trim();
         if (this.type.length() == 0) {
            this.type = null;
         }

         this.decl = decl;
      }
   }

   public String toString() {
      return this.initValue != null ? this.type + " " + this.name + "=" + this.initValue : this.type + " " + this.name;
   }
}
