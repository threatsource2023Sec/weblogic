package netscape.ldap;

import java.io.Serializable;

public class LDAPSortKey implements Serializable {
   static final long serialVersionUID = -7044232342344864405L;
   public static final int REVERSE = 129;
   private String m_key;
   private boolean m_reverse;
   private String m_matchRule;

   public LDAPSortKey(String var1) {
      if (var1 != null && var1.length() > 0) {
         if (var1.charAt(0) == '-') {
            this.m_reverse = true;
            this.m_key = var1.substring(1);
         } else {
            this.m_reverse = false;
            this.m_key = var1;
         }

         int var2 = this.m_key.indexOf(58);
         if (var2 == 0) {
            this.m_key = null;
         } else if (var2 > 0) {
            this.m_matchRule = this.m_key.substring(var2 + 1);
            this.m_key = this.m_key.substring(0, var2);
         }
      }

   }

   public LDAPSortKey(String var1, boolean var2) {
      this.m_key = var1;
      this.m_reverse = var2;
      this.m_matchRule = null;
   }

   public LDAPSortKey(String var1, boolean var2, String var3) {
      this.m_key = var1;
      this.m_reverse = var2;
      this.m_matchRule = var3;
   }

   public String getKey() {
      return this.m_key;
   }

   public boolean getReverse() {
      return this.m_reverse;
   }

   public String getMatchRule() {
      return this.m_matchRule;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("{SortKey:");
      var1.append(" key=");
      var1.append(this.m_key);
      var1.append(" reverse=");
      var1.append(this.m_reverse);
      if (this.m_matchRule != null) {
         var1.append(" matchRule=");
         var1.append(this.m_matchRule);
      }

      var1.append("}");
      return var1.toString();
   }
}
