package netscape.ldap;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class LDAPAttributeSet implements Cloneable, Serializable {
   static final long serialVersionUID = 5018474561697778100L;
   Hashtable attrHash = null;
   LDAPAttribute[] attrs = new LDAPAttribute[0];
   static final int ATTR_COUNT_REQUIRES_HASH = 5;

   public LDAPAttributeSet() {
   }

   public LDAPAttributeSet(LDAPAttribute[] var1) {
      this.attrs = var1;
   }

   public synchronized Object clone() {
      try {
         LDAPAttributeSet var1 = new LDAPAttributeSet();
         var1.attrs = new LDAPAttribute[this.attrs.length];

         for(int var2 = 0; var2 < this.attrs.length; ++var2) {
            var1.attrs[var2] = new LDAPAttribute(this.attrs[var2]);
         }

         return var1;
      } catch (Exception var3) {
         return null;
      }
   }

   public Enumeration getAttributes() {
      Vector var1 = new Vector();
      synchronized(this) {
         for(int var3 = 0; var3 < this.attrs.length; ++var3) {
            var1.addElement(this.attrs[var3]);
         }

         return var1.elements();
      }
   }

   public LDAPAttributeSet getSubset(String var1) {
      LDAPAttributeSet var2 = new LDAPAttributeSet();
      if (var1 == null) {
         return var2;
      } else {
         StringTokenizer var3 = new StringTokenizer(var1, ";");
         if (var3.countTokens() < 1) {
            return var2;
         } else {
            String[] var4 = new String[var3.countTokens()];

            for(int var5 = 0; var3.hasMoreElements(); ++var5) {
               var4[var5] = (String)var3.nextElement();
            }

            Enumeration var6 = this.getAttributes();

            while(var6.hasMoreElements()) {
               LDAPAttribute var7 = (LDAPAttribute)var6.nextElement();
               if (var7.hasSubtypes(var4)) {
                  var2.add(new LDAPAttribute(var7));
               }
            }

            return var2;
         }
      }
   }

   public LDAPAttribute getAttribute(String var1) {
      this.prepareHashtable();
      if (this.attrHash != null) {
         return (LDAPAttribute)this.attrHash.get(var1.toLowerCase());
      } else {
         for(int var2 = 0; var2 < this.attrs.length; ++var2) {
            if (var1.equalsIgnoreCase(this.attrs[var2].getName())) {
               return this.attrs[var2];
            }
         }

         return null;
      }
   }

   private void prepareHashtable() {
      if (this.attrHash == null && this.attrs.length >= 5) {
         this.attrHash = new Hashtable();

         for(int var1 = 0; var1 < this.attrs.length; ++var1) {
            this.attrHash.put(this.attrs[var1].getName().toLowerCase(), this.attrs[var1]);
         }
      }

   }

   public LDAPAttribute getAttribute(String var1, String var2) {
      if (var2 != null && var2.length() >= 1) {
         String var3 = var2.toLowerCase();
         if (var3.length() >= 5 && var3.substring(0, 5).equals("lang-")) {
            StringTokenizer var4 = new StringTokenizer(var3, "-");
            var4.nextToken();
            String[] var5 = new String[var4.countTokens()];

            int var6;
            for(var6 = 0; var4.hasMoreTokens(); ++var6) {
               var5[var6] = var4.nextToken();
            }

            String var7 = LDAPAttribute.getBaseName(var1);
            String[] var8 = LDAPAttribute.getSubtypes(var1);
            LDAPAttribute var9 = null;
            int var10 = 0;

            for(var6 = 0; var6 < this.attrs.length; ++var6) {
               boolean var11 = false;
               LDAPAttribute var12 = this.attrs[var6];
               if (var12.getBaseName().equalsIgnoreCase(var7)) {
                  if (var8 != null && var8.length >= 1) {
                     if (var12.hasSubtypes(var8)) {
                        var11 = true;
                     }
                  } else {
                     var11 = true;
                  }
               }

               String var13 = null;
               if (var11) {
                  var13 = var12.getLangSubtype();
                  if (var13 == null) {
                     var9 = var12;
                  } else {
                     var4 = new StringTokenizer(var13.toLowerCase(), "-");
                     var4.nextToken();
                     if (var4.countTokens() <= var5.length) {
                        int var14;
                        for(var14 = 0; var4.hasMoreTokens(); ++var14) {
                           if (!var5[var14].equals(var4.nextToken())) {
                              var14 = 0;
                              break;
                           }
                        }

                        if (var14 > var10) {
                           var9 = var12;
                           var10 = var14;
                        }
                     }
                  }
               }
            }

            return var9;
         } else {
            return null;
         }
      } else {
         return this.getAttribute(var1);
      }
   }

   public LDAPAttribute elementAt(int var1) {
      return this.attrs[var1];
   }

   public void removeElementAt(int var1) {
      if (var1 >= 0 && var1 < this.attrs.length) {
         synchronized(this) {
            LDAPAttribute[] var3 = new LDAPAttribute[this.attrs.length - 1];
            int var4 = 0;

            for(int var5 = 0; var5 < this.attrs.length; ++var5) {
               if (var5 != var1) {
                  var3[var4++] = this.attrs[var5];
               }
            }

            if (this.attrHash != null) {
               this.attrHash.remove(this.attrs[var1].getName().toLowerCase());
            }

            this.attrs = var3;
         }
      }

   }

   public int size() {
      return this.attrs.length;
   }

   public synchronized void add(LDAPAttribute var1) {
      if (var1 != null) {
         LDAPAttribute[] var2 = new LDAPAttribute[this.attrs.length + 1];

         for(int var3 = 0; var3 < this.attrs.length; ++var3) {
            var2[var3] = this.attrs[var3];
         }

         var2[this.attrs.length] = var1;
         this.attrs = var2;
         if (this.attrHash != null) {
            this.attrHash.put(var1.getName().toLowerCase(), var1);
         }
      }

   }

   public synchronized void remove(String var1) {
      for(int var2 = 0; var2 < this.attrs.length; ++var2) {
         if (var1.equalsIgnoreCase(this.attrs[var2].getName())) {
            this.removeElementAt(var2);
            break;
         }
      }

   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("LDAPAttributeSet: ");

      for(int var2 = 0; var2 < this.attrs.length; ++var2) {
         if (var2 != 0) {
            var1.append(" ");
         }

         var1.append(this.attrs[var2].toString());
      }

      return var1.toString();
   }
}
