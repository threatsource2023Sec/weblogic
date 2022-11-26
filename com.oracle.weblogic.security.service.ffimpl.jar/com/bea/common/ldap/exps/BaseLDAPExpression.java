package com.bea.common.ldap.exps;

import com.bea.common.ldap.escaping.EscapingFactory;
import java.util.Collections;
import java.util.Map;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.StoreException;

public abstract class BaseLDAPExpression implements LDAPExpression {
   private static final Map scopingExpressions = Collections.emptyMap();

   public boolean containsScopingKey() {
      return false;
   }

   public Map getScopingExpressions() {
      return scopingExpressions;
   }

   protected EscapingFactory getEscapingFactory(FieldMetaData f) {
      String escaperClass = f.getStringExtension("com.bea.common.security", "ldap-escaping");
      return escaperClass != null ? this.getEscapingFactory(escaperClass) : null;
   }

   protected EscapingFactory getEscapingFactory(String escaperClass) {
      try {
         return (EscapingFactory)Class.forName(escaperClass).newInstance();
      } catch (Throwable var3) {
         throw new StoreException(var3);
      }
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter(this);
      visitor.exit(this);
   }

   protected static String ldapFilterEscape(String s) {
      int index = indexOfLdapFilterSpecial(s);
      if (index < 0) {
         return s;
      } else {
         StringBuilder sb = new StringBuilder(s.length() + 16);

         for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            switch (c) {
               case '(':
                  sb.append("\\28");
                  break;
               case ')':
                  sb.append("\\29");
                  break;
               case '*':
                  sb.append("\\2a");
                  break;
               case '\\':
                  sb.append("\\5c");
                  break;
               default:
                  sb.append(c);
            }
         }

         return sb.toString();
      }
   }

   private static final int indexOfLdapFilterSpecial(String s) {
      if (s != null) {
         for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '\\' || c == '*' || c == '(' || c == ')') {
               return i;
            }
         }
      }

      return -1;
   }
}
