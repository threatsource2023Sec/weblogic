package netscape.ldap;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERSet;

public class LDAPAttribute implements Serializable {
   static final long serialVersionUID = -4594745735452202600L;
   private String name = null;
   private byte[] nameBuf = null;
   private Object[] values = new Object[0];

   public LDAPAttribute(LDAPAttribute var1) {
      this.name = var1.name;
      this.nameBuf = var1.nameBuf;
      this.values = new Object[var1.values.length];

      for(int var2 = 0; var2 < var1.values.length; ++var2) {
         this.values[var2] = new byte[((byte[])((byte[])var1.values[var2])).length];
         System.arraycopy((byte[])((byte[])var1.values[var2]), 0, (byte[])((byte[])this.values[var2]), 0, ((byte[])((byte[])var1.values[var2])).length);
      }

   }

   public LDAPAttribute(String var1) {
      this.name = var1;
   }

   public LDAPAttribute(String var1, byte[] var2) {
      this.name = var1;
      this.addValue(var2);
   }

   public LDAPAttribute(String var1, String var2) {
      this.name = var1;
      this.addValue(var2);
   }

   public LDAPAttribute(String var1, String[] var2) {
      this.name = var1;
      if (var2 != null) {
         this.setValues(var2);
      }

   }

   public LDAPAttribute(BERElement var1) throws IOException {
      BERSequence var2 = (BERSequence)var1;
      BEROctetString var3 = (BEROctetString)var2.elementAt(0);
      this.nameBuf = var3.getValue();
      BERSet var4 = (BERSet)var2.elementAt(1);
      if (var4.size() > 0) {
         Object[] var5 = new Object[var4.size()];

         for(int var6 = 0; var6 < var4.size(); ++var6) {
            var5[var6] = ((BEROctetString)var4.elementAt(var6)).getValue();
            if (var5[var6] == null) {
               var5[var6] = new byte[0];
            }
         }

         this.setValues(var5);
      }

   }

   public int size() {
      return this.values.length;
   }

   public Enumeration getStringValues() {
      Vector var1 = new Vector();
      synchronized(this) {
         try {
            for(int var3 = 0; var3 < this.values.length; ++var3) {
               if (this.values[var3] != null) {
                  var1.addElement(new String((byte[])((byte[])this.values[var3]), "UTF8"));
               } else {
                  var1.addElement(new String(""));
               }
            }
         } catch (Exception var5) {
            return null;
         }
      }

      return var1.elements();
   }

   public String[] getStringValueArray() {
      String[] var1 = new String[this.values.length];
      synchronized(this) {
         try {
            for(int var3 = 0; var3 < this.values.length; ++var3) {
               if (this.values[var3] != null) {
                  var1[var3] = new String((byte[])((byte[])this.values[var3]), "UTF8");
               } else {
                  var1[var3] = new String("");
               }
            }
         } catch (Exception var5) {
            return null;
         }

         return var1;
      }
   }

   public Enumeration getByteValues() {
      Vector var1 = new Vector();
      synchronized(this) {
         for(int var3 = 0; var3 < this.values.length; ++var3) {
            if (this.values[var3] != null) {
               var1.addElement(this.values[var3]);
            } else {
               var1.addElement(new byte[0]);
            }
         }

         return var1.elements();
      }
   }

   public byte[][] getByteValueArray() {
      byte[][] var1 = new byte[this.values.length][];
      synchronized(this) {
         try {
            for(int var3 = 0; var3 < this.values.length; ++var3) {
               var1[var3] = new byte[((byte[])((byte[])this.values[var3])).length];
               System.arraycopy((byte[])((byte[])this.values[var3]), 0, (byte[])var1[var3], 0, ((byte[])((byte[])this.values[var3])).length);
            }
         } catch (Exception var5) {
            return (byte[][])null;
         }

         return var1;
      }
   }

   public String getName() {
      if (this.name == null && this.nameBuf != null) {
         try {
            this.name = new String(this.nameBuf, "UTF8");
         } catch (Throwable var2) {
         }
      }

      return this.name;
   }

   public static String[] getSubtypes(String var0) {
      StringTokenizer var1 = new StringTokenizer(var0, ";");
      if (!var1.hasMoreElements()) {
         return null;
      } else {
         var1.nextElement();
         String[] var2 = new String[var1.countTokens()];

         for(int var3 = 0; var1.hasMoreElements(); var2[var3++] = (String)var1.nextElement()) {
         }

         return var2;
      }
   }

   public String[] getSubtypes() {
      return getSubtypes(this.getName());
   }

   public String getLangSubtype() {
      String[] var1 = this.getSubtypes();
      if (var1 != null) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            if (var1[var2].length() >= 5 && var1[var2].substring(0, 5).equalsIgnoreCase("lang-")) {
               return var1[var2];
            }
         }
      }

      return null;
   }

   public static String getBaseName(String var0) {
      String var1 = var0;
      StringTokenizer var2 = new StringTokenizer(var0, ";");
      if (var2.hasMoreElements()) {
         var1 = (String)var2.nextElement();
      }

      return var1;
   }

   public String getBaseName() {
      return getBaseName(this.getName());
   }

   public boolean hasSubtype(String var1) {
      String[] var2 = this.getSubtypes();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         if (var1.equalsIgnoreCase(var2[var3])) {
            return true;
         }
      }

      return false;
   }

   public boolean hasSubtypes(String[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (!this.hasSubtype(var1[var2])) {
            return false;
         }
      }

      return true;
   }

   public synchronized void addValue(String var1) {
      if (var1 != null) {
         try {
            byte[] var2 = var1.getBytes("UTF8");
            this.addValue(var2);
         } catch (Throwable var3) {
         }
      }

   }

   protected void setValues(String[] var1) {
      Object[] var2;
      if (var1 != null) {
         var2 = new Object[var1.length];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            try {
               var2[var3] = var1[var3].getBytes("UTF8");
            } catch (Throwable var5) {
               var2[var3] = new byte[0];
            }
         }
      } else {
         var2 = new Object[0];
      }

      this.setValues(var2);
   }

   public synchronized void addValue(byte[] var1) {
      if (var1 != null) {
         Object[] var2 = new Object[this.values.length + 1];

         for(int var3 = 0; var3 < this.values.length; ++var3) {
            var2[var3] = this.values[var3];
         }

         var2[this.values.length] = var1;
         this.values = var2;
      }

   }

   protected synchronized void setValues(Object[] var1) {
      this.values = var1;
   }

   public synchronized void removeValue(String var1) {
      if (var1 != null) {
         try {
            byte[] var2 = var1.getBytes("UTF8");
            this.removeValue(var2);
         } catch (Throwable var3) {
         }
      }

   }

   public synchronized void removeValue(byte[] var1) {
      if (var1 != null && this.values != null && this.values.length >= 1) {
         int var2 = -1;

         for(int var3 = 0; var3 < this.values.length; ++var3) {
            if (equalValue(var1, (byte[])((byte[])this.values[var3]))) {
               var2 = var3;
               break;
            }
         }

         if (var2 >= 0) {
            Object[] var6 = new Object[this.values.length - 1];
            int var4 = 0;

            for(int var5 = 0; var5 < this.values.length; ++var5) {
               if (var5 != var2) {
                  var6[var4++] = this.values[var5];
               }
            }

            this.values = var6;
         }

      }
   }

   private static boolean equalValue(byte[] var0, byte[] var1) {
      if (var0.length != var1.length) {
         return false;
      } else {
         for(int var2 = 0; var2 < var0.length; ++var2) {
            if (var0[var2] != var1[var2]) {
               return false;
            }
         }

         return true;
      }
   }

   public BERElement getBERElement() {
      try {
         BERSequence var1 = new BERSequence();
         var1.addElement(new BEROctetString(this.getName()));
         BERSet var2 = new BERSet();

         for(int var3 = 0; var3 < this.values.length; ++var3) {
            var2.addElement(new BEROctetString((byte[])((byte[])this.values[var3])));
         }

         var1.addElement(var2);
         return var1;
      } catch (IOException var4) {
         return null;
      }
   }

   private String getParamString() {
      StringBuffer var1 = new StringBuffer();
      if (this.values.length > 0) {
         for(int var2 = 0; var2 < this.values.length; ++var2) {
            if (var2 != 0) {
               var1.append(",");
            }

            byte[] var3 = (byte[])((byte[])this.values[var2]);

            try {
               String var4 = new String(var3, "UTF8");
               if (var4.length() == 0 && var3.length > 0) {
                  var1.append("<binary value, length:");
                  var1.append(var3.length);
                  var1.append(">");
               } else {
                  var1.append(var4);
               }
            } catch (Exception var5) {
               if (var3 != null) {
                  var1.append("<binary value, length:");
                  var1.append(var3.length);
                  var1.append(">");
               } else {
                  var1.append("null value");
               }
            }
         }
      }

      return "{type='" + this.getName() + "', values='" + var1.toString() + "'}";
   }

   public String toString() {
      return "LDAPAttribute " + this.getParamString();
   }
}
