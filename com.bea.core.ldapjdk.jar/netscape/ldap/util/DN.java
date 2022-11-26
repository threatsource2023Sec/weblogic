package netscape.ldap.util;

import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.Vector;

public final class DN implements Serializable {
   private Vector m_rdns = new Vector();
   public static int RFC = 0;
   public static int OSF = 1;
   private int m_dnType;
   static final long serialVersionUID = -8867457218975952548L;
   public static final char[] ESCAPED_CHAR = new char[]{',', '+', '"', '\\', '<', '>', ';'};

   public DN() {
      this.m_dnType = RFC;
   }

   public DN(String var1) {
      this.m_dnType = RFC;
      String var2 = this.neutralizeEscapes(var1);
      if (var2 != null) {
         if (var2.indexOf(44) == -1 && var2.indexOf(59) == -1) {
            if (var1.indexOf(47) != -1) {
               this.m_dnType = OSF;
               StringTokenizer var3 = new StringTokenizer(var1, "/");
               Vector var4 = new Vector();

               while(var3.hasMoreTokens()) {
                  String var5 = var3.nextToken();
                  if (!RDN.isRDN(var5)) {
                     return;
                  }

                  var4.addElement(new RDN(var5));
               }

               for(int var6 = var4.size() - 1; var6 >= 0; --var6) {
                  this.m_rdns.addElement(var4.elementAt(var6));
               }
            } else if (RDN.isRDN(var1)) {
               this.m_rdns.addElement(new RDN(var1));
            }
         } else {
            this.parseRDNs(var2, var1, ",;");
         }

      }
   }

   private String neutralizeEscapes(String var1) {
      String var2 = RDN.neutralizeEscapes(var1);
      if (var2 == null) {
         return null;
      } else {
         String var3 = var2.trim();
         if (var3.length() == 0) {
            return var2;
         } else if (var3.charAt(0) != ';' && var3.charAt(0) != ',') {
            int var4 = var3.length() - 1;
            return var3.charAt(var4) != ';' && var3.charAt(var4) != ',' ? var2 : null;
         } else {
            return null;
         }
      }
   }

   private void parseRDNs(String var1, String var2, String var3) {
      int var4 = 0;
      boolean var5 = false;
      RDN var6 = null;

      int var9;
      for(StringTokenizer var7 = new StringTokenizer(var1, var3); var7.hasMoreElements(); var4 = var9 + 1) {
         String var8 = var7.nextToken();
         var9 = var4 + var8.length();
         var6 = new RDN(var2.substring(var4, var9));
         if (var6.getTypes() == null) {
            this.m_rdns.removeAllElements();
            return;
         }

         this.m_rdns.addElement(var6);
      }

   }

   public void addRDNToFront(RDN var1) {
      this.m_rdns.insertElementAt(var1, 0);
   }

   public void addRDNToBack(RDN var1) {
      this.m_rdns.addElement(var1);
   }

   public void addRDN(RDN var1) {
      if (this.m_dnType == RFC) {
         this.addRDNToFront(var1);
      } else {
         this.addRDNToBack(var1);
      }

   }

   public void setDNType(int var1) {
      this.m_dnType = var1;
   }

   public int getDNType() {
      return this.m_dnType;
   }

   public int countRDNs() {
      return this.m_rdns.size();
   }

   public Vector getRDNs() {
      return this.m_rdns;
   }

   public String[] explodeDN(boolean var1) {
      if (this.m_rdns.size() == 0) {
         return null;
      } else {
         String[] var2 = new String[this.m_rdns.size()];

         for(int var3 = 0; var3 < this.m_rdns.size(); ++var3) {
            if (var1) {
               var2[var3] = ((RDN)this.m_rdns.elementAt(var3)).getValue();
            } else {
               var2[var3] = ((RDN)this.m_rdns.elementAt(var3)).toString();
            }
         }

         return var2;
      }
   }

   public boolean isRFC() {
      return this.m_dnType == RFC;
   }

   public String toRFCString() {
      String var1 = "";

      for(int var2 = 0; var2 < this.m_rdns.size(); ++var2) {
         if (var2 != 0) {
            var1 = var1 + ",";
         }

         var1 = var1 + ((RDN)this.m_rdns.elementAt(var2)).toString();
      }

      return var1;
   }

   public String toOSFString() {
      String var1 = "";

      for(int var2 = 0; var2 < this.m_rdns.size(); ++var2) {
         if (var2 != 0) {
            var1 = "/" + var1;
         }

         RDN var3 = (RDN)this.m_rdns.elementAt(var2);
         var1 = var3.toString() + var1;
      }

      return var1;
   }

   public String toString() {
      return this.m_dnType == RFC ? this.toRFCString() : this.toOSFString();
   }

   public static boolean isDN(String var0) {
      if (var0.equals("")) {
         return true;
      } else {
         DN var1 = new DN(var0);
         return var1.countRDNs() > 0;
      }
   }

   public boolean equals(DN var1) {
      return var1.toRFCString().toUpperCase().equals(this.toRFCString().toUpperCase());
   }

   public DN getParent() {
      DN var1 = new DN();

      for(int var2 = this.m_rdns.size() - 1; var2 > 0; --var2) {
         var1.addRDN((RDN)this.m_rdns.elementAt(var2));
      }

      return var1;
   }

   /** @deprecated */
   public boolean contains(DN var1) {
      return this.isDescendantOf(var1);
   }

   public boolean isDescendantOf(DN var1) {
      Vector var2 = var1.m_rdns;
      Vector var3 = this.m_rdns;
      int var4 = var2.size() - 1;
      int var5 = var3.size() - 1;
      if (var5 >= var4 && !this.equals(var1)) {
         while(var4 >= 0 && var5 >= 0) {
            RDN var6 = (RDN)var2.elementAt(var4);
            RDN var7 = (RDN)var3.elementAt(var5);
            if (!var7.equals(var6)) {
               return false;
            }

            --var4;
            --var5;
         }

         return true;
      } else {
         return false;
      }
   }
}
