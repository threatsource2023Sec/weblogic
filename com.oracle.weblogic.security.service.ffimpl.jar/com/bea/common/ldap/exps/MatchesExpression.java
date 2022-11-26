package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPExpressionFactory;
import com.bea.common.ldap.escaping.Escaping;
import com.bea.common.ldap.escaping.EscapingFactory;

public class MatchesExpression extends BaseLDAPExpression {
   private ExpPath left;
   private Const right;
   private String single;
   private String multi;
   private String escape = "\\";
   private Escaping escaper;

   public MatchesExpression(Val left, Val right, String single, String multi, String escape) {
      if (left instanceof ExpPath) {
         this.left = (ExpPath)left;
         if (this.left.containsScopingKey()) {
            throw new IllegalArgumentException("May not test scoping primary key with matches expression");
         } else if (right != null && !(right instanceof Const)) {
            throw new IllegalArgumentException("Operands to matches expression must be field reference and constant value");
         } else {
            this.right = (Const)right;
            this.single = single;
            this.multi = multi;
            if (escape != null) {
               this.escape = escape;
            }

            EscapingFactory fac = this.getEscapingFactory(this.left.last());
            this.escaper = fac == null ? null : fac.getEscaping();
         }
      } else {
         throw new IllegalArgumentException("Operands to matches expression must be field reference and constant value");
      }
   }

   public String getFilter(LDAPExpressionFactory fact, Object[] params) {
      String l = this.left.getAttribute();
      Object o = this.right.getValue(params);
      if (o == null) {
         throw new IllegalArgumentException("Test constant must not be null");
      } else {
         String regexp = o.toString();
         StringBuilder filt = new StringBuilder();
         boolean escaped = false;

         for(int i = 0; i < regexp.length(); ++i) {
            if (escaped) {
               escaped = false;
               if (this.escape != null && regexp.startsWith(this.escape, i)) {
                  i += this.escape.length() - 1;
                  filt.append(this.escape(this.escape));
               } else if (this.multi != null && regexp.startsWith(this.multi, i)) {
                  i += this.multi.length() - 1;
                  filt.append(this.escape(this.multi));
               } else {
                  if (this.single == null || !regexp.startsWith(this.single, i)) {
                     throw new IllegalArgumentException("Invalid escaped character not supported in matches expression: " + regexp);
                  }

                  i += this.single.length() - 1;
                  filt.append(this.escape(this.single));
               }
            } else if (this.escape != null && regexp.startsWith(this.escape, i)) {
               i += this.escape.length() - 1;
               escaped = true;
            } else if (this.multi != null && regexp.startsWith(this.multi, i)) {
               i += this.multi.length() - 1;
               filt.append('*');
            } else {
               if (this.single != null && regexp.startsWith(this.single, i)) {
                  throw new IllegalArgumentException("Single character wildcard not supported in matches expression: " + regexp);
               }

               filt.append(this.escape(regexp.substring(i, i + 1)));
            }
         }

         return "(" + l + "=" + filt + ")";
      }
   }

   private String escape(String str) {
      if (this.escaper != null) {
         str = this.escaper.escapeString(str);
      }

      return ldapFilterEscape(str);
   }
}
