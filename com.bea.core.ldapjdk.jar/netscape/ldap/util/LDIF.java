package netscape.ldap.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPModification;

public class LDIF implements Serializable {
   private static final char COMMENT = '#';
   static final long serialVersionUID = -2710382547996750924L;
   private int m_version = 1;
   private boolean m_done = false;
   private LineReader m_reader = null;
   private String m_source = null;
   private MimeBase64Decoder m_decoder = null;
   private boolean m_currEntryDone = false;
   private int m_currLineNum;
   private int m_continuationLength;

   public LDIF() throws IOException {
      DataInputStream var1 = new DataInputStream(System.in);
      BufferedReader var2 = new BufferedReader(new InputStreamReader(var1, "UTF8"));
      this.m_reader = new LineReader(var2);
      this.m_source = "System.in";
      this.m_decoder = new MimeBase64Decoder();
   }

   public LDIF(String var1) throws IOException {
      FileInputStream var2 = new FileInputStream(var1);
      DataInputStream var3 = new DataInputStream(var2);
      BufferedReader var4 = new BufferedReader(new InputStreamReader(var3, "UTF8"));
      this.m_reader = new LineReader(var4);
      this.m_source = var1;
      this.m_decoder = new MimeBase64Decoder();
   }

   public LDIF(DataInputStream var1) throws IOException {
      BufferedReader var2 = new BufferedReader(new InputStreamReader(var1, "UTF8"));
      this.m_reader = new LineReader(var2);
      this.m_source = var1.toString();
      this.m_decoder = new MimeBase64Decoder();
   }

   public LDIFRecord nextRecord() throws IOException {
      return this.m_done ? null : this.parse_ldif_record(this.m_reader);
   }

   private LDIFRecord parse_ldif_record(LineReader var1) throws IOException {
      String var2 = null;
      String var3 = null;
      new Vector();
      LDIFRecord var5 = null;

      while((var2 = var1.readLine()) != null && var2.length() < 1) {
      }

      if (var2 == null) {
         return null;
      } else {
         if (var2.startsWith("version:")) {
            this.m_version = Integer.parseInt(var2.substring("version:".length()).trim());
            if (this.m_version != 1) {
               this.throwLDIFException("Unexpected " + var2);
            }

            var2 = var1.readLine();
            if (var2 != null && var2.length() == 0) {
               var2 = var1.readLine();
            }

            if (var2 == null) {
               return null;
            }
         }

         if (!var2.startsWith("dn:")) {
            this.throwLDIFException("expecting dn:");
         }

         var3 = var2.substring(3).trim();
         if (var3.startsWith(":") && var3.length() > 1) {
            String var6 = var3.substring(1).trim();
            var3 = new String(this.getDecodedBytes(var6), "UTF8");
         }

         LDIFContent var7 = this.parse_ldif_content(var1);
         var5 = new LDIFRecord(var3, var7);
         return var5;
      }
   }

   private LDIFContent parse_ldif_content(LineReader var1) throws IOException {
      String var2 = var1.readLine();
      if (var2 != null && var2.length() >= 1 && !var2.equals("-")) {
         String var4;
         if (var2.startsWith("changetype:")) {
            Object var13 = null;
            var4 = var2.substring(11).trim();
            if (var4.equals("modify")) {
               var13 = this.parse_mod_spec(var1);
            } else if (var4.equals("add")) {
               var13 = this.parse_add_spec(var1);
            } else if (var4.equals("delete")) {
               var13 = this.parse_delete_spec(var1);
            } else if (!var4.equals("moddn") && !var4.equals("modrdn")) {
               this.throwLDIFException("change type not supported");
            } else {
               var13 = this.parse_moddn_spec(var1);
            }

            return (LDIFContent)var13;
         } else {
            Hashtable var3 = new Hashtable();
            var4 = null;
            Object var5 = null;
            LDAPAttribute var6 = null;
            Vector var7 = null;

            while(true) {
               if (var2.startsWith("control:")) {
                  if (var7 == null) {
                     var7 = new Vector();
                  }

                  var7.addElement(this.parse_control_spec(var2));
               } else {
                  int var8 = var2.length();
                  if (var8 < 1) {
                     break;
                  }

                  int var9 = var2.indexOf(58);
                  if (var9 == -1) {
                     this.throwLDIFException("no ':' found");
                  }

                  var4 = var2.substring(0, var9).toLowerCase();
                  var5 = "";
                  ++var9;
                  if (var8 > var9) {
                     if (var2.charAt(var9) == ':') {
                        ++var9;
                        String var10 = var2.substring(var9).trim();
                        var5 = this.getDecodedBytes(var10);
                     } else if (var2.charAt(var9) == '<') {
                        try {
                           URL var16 = new URL(var2.substring(var9 + 1).trim());
                           String var11 = var16.getFile();
                           var5 = this.getFileContent(var11);
                        } catch (MalformedURLException var12) {
                           this.throwLDIFException(var12 + ": cannot construct url " + var2.substring(var9 + 1).trim());
                        }
                     } else {
                        var5 = var2.substring(var9).trim();
                     }
                  }

                  var6 = (LDAPAttribute)var3.get(var4);
                  if (var6 == null) {
                     var6 = new LDAPAttribute(var4);
                  }

                  if (var5 instanceof String) {
                     var6.addValue((String)var5);
                  } else {
                     var6.addValue((byte[])((byte[])var5));
                  }

                  var3.put(var4, var6);
               }

               var2 = var1.readLine();
               if (var2 == null || var2.length() < 1 || var2.equals("-")) {
                  if (var2 != null && var2.length() < 1) {
                     this.m_currEntryDone = true;
                  }
                  break;
               }
            }

            LDIFAttributeContent var14 = new LDIFAttributeContent();
            Enumeration var15 = var3.elements();

            while(var15.hasMoreElements()) {
               var14.addElement((LDAPAttribute)var15.nextElement());
            }

            var3.clear();
            if (var7 != null) {
               LDAPControl[] var17 = new LDAPControl[var7.size()];
               var7.copyInto(var17);
               var14.setControls(var17);
               var7.removeAllElements();
            }

            return var14;
         }
      } else {
         if (var2 != null && var2.length() < 1) {
            this.m_currEntryDone = true;
         }

         return null;
      }
   }

   private byte[] getDecodedBytes(String var1) {
      ByteBuf var2 = new ByteBuf(var1);
      ByteBuf var3 = new ByteBuf();
      this.m_decoder.translate(var2, var3);
      return var3.toBytes();
   }

   private byte[] getFileContent(String var1) throws IOException {
      StringTokenizer var2 = new StringTokenizer(var1, "|");
      String var3 = var1;
      int var4 = var2.countTokens();
      if (var4 == 2) {
         String var5 = (String)var2.nextElement();
         int var6 = var5.lastIndexOf("/");
         String var7 = var5.substring(var6 + 1);
         var5 = (String)var2.nextElement();
         var5 = var5.replace('/', '\\');
         var3 = var7 + ":" + var5;
      }

      File var8 = new File(var3);
      byte[] var9 = new byte[(int)var8.length()];
      FileInputStream var10 = new FileInputStream(var3);
      var10.read(var9);
      return var9;
   }

   private LDIFAddContent parse_add_spec(LineReader var1) throws IOException {
      LDIFAttributeContent var2 = (LDIFAttributeContent)this.parse_ldif_content(var1);
      if (this.m_currEntryDone) {
         this.m_currEntryDone = false;
      }

      LDAPAttribute[] var3 = var2.getAttributes();
      LDIFAddContent var4 = new LDIFAddContent(var3);
      LDAPControl[] var5 = var2.getControls();
      if (var5 != null) {
         var4.setControls(var5);
      }

      return var4;
   }

   private LDIFDeleteContent parse_delete_spec(LineReader var1) throws IOException {
      Vector var2 = null;
      LDIFDeleteContent var3 = new LDIFDeleteContent();

      for(String var4 = var1.readLine(); var4 != null && !var4.equals(""); var4 = var1.readLine()) {
         if (var4.startsWith("control:")) {
            if (var2 == null) {
               var2 = new Vector();
            }

            var2.addElement(this.parse_control_spec(var4));
         } else {
            this.throwLDIFException("invalid SEP");
         }
      }

      if (var2 != null) {
         LDAPControl[] var5 = new LDAPControl[var2.size()];
         var2.copyInto(var5);
         var3.setControls(var5);
         var2.removeAllElements();
      }

      return var3;
   }

   private LDIFModifyContent parse_mod_spec(LineReader var1) throws IOException {
      Vector var2 = null;
      String var3 = null;
      var3 = var1.readLine();
      LDIFModifyContent var4 = new LDIFModifyContent();

      do {
         byte var5 = -1;
         if (var3.startsWith("add:")) {
            var5 = 0;
         } else if (var3.startsWith("delete:")) {
            var5 = 1;
         } else if (var3.startsWith("replace:")) {
            var5 = 2;
         } else {
            this.throwLDIFException("unknown modify type");
         }

         LDIFAttributeContent var6 = (LDIFAttributeContent)this.parse_ldif_content(var1);
         if (var6 == null) {
            int var12 = var3.indexOf(":");
            if (var12 == -1) {
               this.throwLDIFException("colon missing in " + var3);
            }

            String var14 = var3.substring(var12 + 1).trim();
            if (var5 == 0) {
               this.throwLDIFException("add operation needs the value for attribute " + var14);
            }

            LDAPAttribute var16 = new LDAPAttribute(var14);
            LDAPModification var10 = new LDAPModification(var5, var16);
            var4.addElement(var10);
         } else {
            LDAPAttribute[] var7 = var6.getAttributes();

            for(int var8 = 0; var8 < var7.length; ++var8) {
               LDAPModification var9 = new LDAPModification(var5, var7[var8]);
               var4.addElement(var9);
            }

            LDAPControl[] var13 = var6.getControls();
            if (var13 != null) {
               if (var2 == null) {
                  var2 = new Vector();
               }

               for(int var15 = 0; var15 < var13.length; ++var15) {
                  var2.addElement(var13[var15]);
               }
            }
         }

         if (this.m_currEntryDone) {
            this.m_currEntryDone = false;
            break;
         }

         var3 = var1.readLine();
      } while(var3 != null && !var3.equals(""));

      if (var2 != null) {
         LDAPControl[] var11 = new LDAPControl[var2.size()];
         var2.copyInto(var11);
         var4.setControls(var11);
         var2.removeAllElements();
      }

      return var4;
   }

   private LDIFModDNContent parse_moddn_spec(LineReader var1) throws IOException {
      Vector var2 = null;
      String var3 = null;
      var3 = var1.readLine();
      LDIFModDNContent var4 = new LDIFModDNContent();
      Object var5 = null;

      do {
         if (var3.startsWith("newrdn:") && var3.length() > "newrdn:".length() + 1) {
            var4.setRDN(var3.substring("newrdn:".length()).trim());
         } else if (var3.startsWith("deleteoldrdn:") && var3.length() > "deleteoldrdn:".length() + 1) {
            String var6 = var3.substring("deleteoldrdn:".length()).trim();
            if (var6.equals("0")) {
               var4.setDeleteOldRDN(false);
            } else if (var6.equals("1")) {
               var4.setDeleteOldRDN(true);
            } else {
               this.throwLDIFException("Incorrect input for deleteOldRdn ");
            }
         } else if (var3.startsWith("newsuperior:") && var3.length() > "newsuperior:".length() + 1) {
            var4.setNewParent(var3.substring("newsuperior:".length()).trim());
         } else if (var3.startsWith("newparent:") && var3.length() > "newparent:".length() + 1) {
            var4.setNewParent(var3.substring("newparent:".length()).trim());
         } else if (var3.startsWith("control:")) {
            if (var2 == null) {
               var2 = new Vector();
            }

            var2.addElement(this.parse_control_spec(var3));
         }

         var3 = var1.readLine();
      } while(var3 != null && !var3.equals(""));

      if (var2 != null) {
         LDAPControl[] var7 = new LDAPControl[var2.size()];
         var2.copyInto(var7);
         var4.setControls(var7);
         var2.removeAllElements();
      }

      return var4;
   }

   protected LDAPControl parse_control_spec(String var1) throws IOException {
      boolean var2 = true;
      byte[] var4 = null;
      int var5 = var1.length();
      int var6 = var1.indexOf(58) + 2;
      if (var6 >= var5) {
         this.throwLDIFException("OID required for control");
      }

      var1 = var1.substring(var6).trim();
      var6 = var1.indexOf(32);
      String var3;
      if (var6 < 0) {
         var3 = var1;
      } else {
         var3 = var1.substring(0, var6);
         var1 = var1.substring(var6 + 1);
         var6 = var1.indexOf(58);
         String var7;
         if (var6 > 0) {
            var7 = var1.substring(0, var6);
         } else {
            var7 = var1;
         }

         if (var7.compareTo("true") == 0) {
            var2 = true;
         } else if (var7.compareTo("false") == 0) {
            var2 = false;
         } else {
            this.throwLDIFException("Criticality for control must be true or false, not " + var7);
         }

         if (var6 > 0) {
            ++var6;
            if (var1.length() > var6) {
               if (var1.charAt(var6) == ':') {
                  ++var6;
                  var1 = var1.substring(var6).trim();
                  var4 = this.getDecodedBytes(var1);
               } else if (var1.charAt(var6) == '<') {
                  String var8 = var1.substring(var6 + 1).trim();

                  try {
                     URL var9 = new URL(var8);
                     String var10 = var9.getFile();
                     var4 = this.getFileContent(var10);
                  } catch (MalformedURLException var12) {
                     this.throwLDIFException(var12 + ": cannot construct url " + var8);
                  }
               } else {
                  try {
                     var4 = var1.substring(var6).trim().getBytes("UTF8");
                  } catch (Exception var11) {
                  }
               }
            }
         }
      }

      return new LDAPControl(var3, var2, var4);
   }

   public static boolean isPrintable(byte[] var0) {
      for(int var1 = var0.length - 1; var1 >= 0; --var1) {
         if ((var0[var1] < 32 || var0[var1] > 127) && var0[var1] != 9) {
            return false;
         }
      }

      return true;
   }

   public static void breakString(PrintWriter var0, String var1, int var2) {
      int var3 = var1.length();
      int var4 = 0;
      int var5 = var2;

      while(var3 > 0) {
         int var6 = Math.min(var5, var3);
         String var7 = var1.substring(var4, var4 + var6);
         if (var4 != 0) {
            var0.print(" " + var7);
         } else {
            var0.print(var7);
            --var5;
         }

         var4 += var6;
         var3 -= var6;
         var0.print('\n');
      }

   }

   public int getVersion() {
      return this.m_version;
   }

   public String toString() {
      return "LDIF {" + this.m_source + "}";
   }

   protected void throwLDIFException(String var1) throws IOException {
      throw new IOException("line " + (this.m_currLineNum - this.m_continuationLength) + ": " + var1);
   }

   public static String toPrintableString(byte[] var0) {
      String var1 = "";
      if (isPrintable(var0)) {
         try {
            var1 = new String(var0, "UTF8");
         } catch (UnsupportedEncodingException var6) {
         }
      } else {
         ByteBuf var2 = new ByteBuf(var0, 0, var0.length);
         ByteBuf var3 = new ByteBuf();
         MimeBase64Encoder var4 = new MimeBase64Encoder();
         var4.translate(var2, var3);
         int var5 = var3.length();
         if (var5 > 0) {
            var1 = new String(var3.toBytes(), 0, var5);
         }
      }

      return var1;
   }

   public static void main(String[] var0) {
      if (var0.length != 1) {
         System.out.println("Usage: java LDIF <FILENAME>");
         System.exit(1);
      }

      LDIF var1 = null;

      try {
         var1 = new LDIF(var0[0]);
      } catch (Exception var3) {
         System.err.println("Failed to read LDIF file " + var0[0] + ", " + var3.toString());
         System.exit(1);
      }

      try {
         for(LDIFRecord var2 = var1.nextRecord(); var2 != null; var2 = var1.nextRecord()) {
            System.out.println(var2.toString() + '\n');
         }
      } catch (IOException var4) {
         System.out.println(var4);
         System.exit(1);
      }

      System.exit(0);
   }

   class LineReader {
      private BufferedReader _d;
      String _next = null;

      LineReader(BufferedReader var2) {
         this._d = var2;
      }

      String readLine() throws IOException {
         String var1 = null;
         String var2 = null;
         int var3 = 0;
         int var4 = 0;

         while(true) {
            if (this._next != null) {
               var1 = this._next;
               this._next = null;
            } else {
               var1 = this._d.readLine();
            }

            if (var1 == null) {
               break;
            }

            ++var3;
            if (var1.length() < 1) {
               if (var2 != null) {
                  this._next = var1;
                  break;
               }

               var2 = var1;
            } else if (var1.charAt(0) != '#') {
               if (var1.charAt(0) != ' ') {
                  if (var2 != null) {
                     this._next = var1;
                     break;
                  }

                  var2 = var1;
               } else {
                  if (var2 == null) {
                     LDIF.this.m_currLineNum = var3;
                     LDIF.this.throwLDIFException("continuation out of nowhere");
                  }

                  var2 = var2 + var1.substring(1);
                  ++var4;
               }
            }
         }

         LDIF.this.m_done = var1 == null;
         LDIF.this.m_currLineNum = var3;
         if (this._next != null) {
            LDIF.this.m_currLineNum--;
         }

         LDIF.this.m_continuationLength = var4;
         return var2;
      }
   }
}
