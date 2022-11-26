package netscape.ldap;

import java.io.Serializable;
import java.text.Collator;
import java.util.Enumeration;
import java.util.Locale;
import java.util.NoSuchElementException;

public class LDAPCompareAttrNames implements LDAPEntryComparator, Serializable {
   static final long serialVersionUID = -2567450425231175944L;
   private String[] m_attrs;
   private boolean[] m_ascending;
   private Locale m_locale = null;
   private Collator m_collator = null;
   private boolean m_sensitive = true;

   public LDAPCompareAttrNames(String var1) {
      this.m_attrs = new String[1];
      this.m_attrs[0] = var1;
      this.m_ascending = new boolean[1];
      this.m_ascending[0] = true;
   }

   public LDAPCompareAttrNames(String var1, boolean var2) {
      this.m_attrs = new String[1];
      this.m_attrs[0] = var1;
      this.m_ascending = new boolean[1];
      this.m_ascending[0] = var2;
   }

   public LDAPCompareAttrNames(String[] var1) {
      this.m_attrs = var1;
      this.m_ascending = new boolean[var1.length];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.m_ascending[var2] = true;
      }

   }

   public LDAPCompareAttrNames(String[] var1, boolean[] var2) {
      this.m_attrs = var1;
      this.m_ascending = var2;
      if (this.m_ascending == null) {
         this.m_ascending = new boolean[var1.length];

         for(int var3 = 0; var3 < var1.length; ++var3) {
            this.m_ascending[var3] = true;
         }
      }

   }

   public Locale getLocale() {
      return this.m_locale;
   }

   public void setLocale(Locale var1) {
      if (this.m_sensitive) {
         this.setLocale(var1, 3);
      } else {
         this.setLocale(var1, 0);
      }

   }

   public void setLocale(Locale var1, int var2) {
      this.m_locale = var1;
      if (this.m_locale == null) {
         this.m_collator = null;
      } else {
         this.m_collator = Collator.getInstance(this.m_locale);
         this.m_collator.setStrength(var2);
      }

   }

   public boolean getCaseSensitive() {
      return this.m_sensitive;
   }

   public void setCaseSensitive(boolean var1) {
      this.m_sensitive = var1;
   }

   public boolean isGreater(LDAPEntry var1, LDAPEntry var2) {
      return var1.equals(var2) ? false : this.attrGreater(var1, var2, 0);
   }

   boolean attrGreater(LDAPEntry var1, LDAPEntry var2, int var3) {
      Enumeration var4 = var1.getAttributeSet().getAttributes();
      Enumeration var5 = var2.getAttributeSet().getAttributes();
      String var6 = null;
      String var7 = null;
      String var8 = this.m_attrs[var3];
      boolean var9 = this.m_ascending[var3];

      try {
         LDAPAttribute var10;
         while(var5.hasMoreElements()) {
            var10 = (LDAPAttribute)((LDAPAttribute)var5.nextElement());
            if (var8.equalsIgnoreCase(var10.getName())) {
               var7 = (String)((String)var10.getStringValues().nextElement());
               break;
            }
         }

         while(var4.hasMoreElements()) {
            var10 = (LDAPAttribute)((LDAPAttribute)var4.nextElement());
            if (var8.equalsIgnoreCase(var10.getName())) {
               var6 = (String)((String)var10.getStringValues().nextElement());
               break;
            }
         }
      } catch (ClassCastException var11) {
         return false;
      } catch (NoSuchElementException var12) {
         return false;
      }

      if (var7 == null ^ var6 == null) {
         return var6 != null;
      } else if (var7 != null && (this.m_collator == null || this.m_collator.compare(var6, var7) != 0) && (this.m_collator != null || !this.m_sensitive || !var7.equals(var6)) && (this.m_collator != null || this.m_sensitive || !var7.equalsIgnoreCase(var6))) {
         if (var9) {
            if (this.m_collator != null) {
               return this.m_collator.compare(var6, var7) > 0;
            } else if (this.m_sensitive) {
               return var6.compareTo(var7) > 0;
            } else {
               return var6.toLowerCase().compareTo(var7.toLowerCase()) > 0;
            }
         } else if (this.m_collator != null) {
            return this.m_collator.compare(var6, var7) < 0;
         } else if (this.m_sensitive) {
            return var6.compareTo(var7) < 0;
         } else {
            return var6.toLowerCase().compareTo(var7.toLowerCase()) < 0;
         }
      } else {
         return var3 == this.m_attrs.length - 1 ? false : this.attrGreater(var1, var2, var3 + 1);
      }
   }
}
