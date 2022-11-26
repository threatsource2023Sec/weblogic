package netscape.ldap.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import netscape.ldap.LDAPAttribute;

public class LDIFWriter extends LDAPWriter {
   private String m_sep;
   private boolean m_foldLines;
   private boolean m_attrsOnly;
   private boolean m_toFiles;
   private static final String DEFAULT_SEPARATOR = ":";
   private static final int MAX_LINE = 77;

   public LDIFWriter(PrintWriter var1) {
      this(var1, false, ":", true, false);
   }

   public LDIFWriter(PrintWriter var1, boolean var2, String var3, boolean var4, boolean var5) {
      super(var1);
      this.m_attrsOnly = var2;
      this.m_sep = var3;
      this.m_foldLines = var4;
      this.m_toFiles = var5;
   }

   protected void printAttribute(LDAPAttribute var1) {
      String var2 = var1.getName();
      if (this.m_attrsOnly) {
         this.printString(var2 + this.m_sep);
      } else {
         Enumeration var3 = var1.getByteValues();
         if (var3 == null) {
            this.printString(var2 + this.m_sep + ' ');
         } else {
            while(var3.hasMoreElements()) {
               if (this.m_toFiles) {
                  try {
                     FileOutputStream var4 = this.getTempFile(var2);
                     var4.write((byte[])((byte[])var3.nextElement()));
                  } catch (Exception var7) {
                     System.err.println("Error writing values of " + var2 + ", " + var7.toString());
                     System.exit(1);
                  }
               } else {
                  byte[] var9 = (byte[])((byte[])var3.nextElement());
                  String var5;
                  if (LDIF.isPrintable(var9)) {
                     try {
                        var5 = new String(var9, "UTF8");
                     } catch (UnsupportedEncodingException var8) {
                        var5 = "";
                     }

                     this.printString(var2 + this.m_sep + " " + var5);
                  } else {
                     var5 = this.getPrintableValue(var9);
                     if (var5.length() > 0) {
                        this.printString(var2 + ":: " + var5);
                     } else {
                        this.printString(var2 + this.m_sep + ' ');
                     }
                  }
               }
            }

         }
      }
   }

   protected void printEntryStart(String var1) {
      if (var1 == null) {
         this.printString("dn" + this.m_sep + " ");
      } else {
         Object var2 = null;

         byte[] var5;
         try {
            var5 = var1.getBytes("UTF8");
         } catch (UnsupportedEncodingException var4) {
            var5 = var1.getBytes();
         }

         if (!LDIF.isPrintable(var5)) {
            var1 = this.getPrintableValue(var5);
            this.printString("dn" + this.m_sep + this.m_sep + " " + var1);
         } else {
            this.printString("dn" + this.m_sep + " " + var1);
         }
      }

   }

   protected void printEntryEnd(String var1) {
      this.m_pw.println();
   }

   protected void printString(String var1) {
      if (this.m_foldLines) {
         LDIF.breakString(this.m_pw, var1, 77);
      } else {
         this.m_pw.print(var1);
         this.m_pw.print('\n');
      }

   }

   protected FileOutputStream getTempFile(String var1) throws IOException {
      int var2 = 0;

      File var3;
      String var4;
      do {
         var4 = var1 + '.' + var2;
         var3 = new File(var4);
         ++var2;
      } while(var3.exists());

      this.printString(var1 + this.m_sep + " " + var4);
      return new FileOutputStream(var3);
   }
}
