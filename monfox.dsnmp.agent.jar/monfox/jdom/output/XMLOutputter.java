package monfox.jdom.output;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import monfox.jdom.Attribute;
import monfox.jdom.CDATA;
import monfox.jdom.Comment;
import monfox.jdom.DocType;
import monfox.jdom.Document;
import monfox.jdom.Element;
import monfox.jdom.Entity;
import monfox.jdom.Namespace;
import monfox.jdom.ProcessingInstruction;
import monfox.toolkit.snmp.SnmpException;

public class XMLOutputter implements Cloneable {
   protected static final String STANDARD_INDENT = "/F";
   private boolean a;
   private String b;
   private boolean c;
   private String d;
   private int e;
   private boolean f;
   private boolean g;
   private String h;
   private boolean i;
   private boolean j;
   protected String padTextString;
   // $FF: synthetic field
   static Class k;
   // $FF: synthetic field
   static Class l;
   // $FF: synthetic field
   static Class m;
   // $FF: synthetic field
   static Class n;
   // $FF: synthetic field
   static Class o;
   // $FF: synthetic field
   static Class p;
   public static boolean q;

   public XMLOutputter() {
      this.a = false;
      this.b = b("Z2KB");
      this.c = false;
      this.d = null;
      this.e = 0;
      this.f = false;
      this.g = false;
      this.h = b("\u0002l");
      this.i = false;
      this.j = false;
      this.padTextString = " ";
   }

   public XMLOutputter(String var1) {
      this.a = false;
      this.b = b("Z2KB");
      this.c = false;
      this.d = null;
      this.e = 0;
      this.f = false;
      this.g = false;
      this.h = b("\u0002l");
      this.i = false;
      this.j = false;
      this.padTextString = " ";
      this.d = var1;
   }

   public XMLOutputter(String var1, boolean var2) {
      this.a = false;
      this.b = b("Z2KB");
      this.c = false;
      this.d = null;
      this.e = 0;
      this.f = false;
      this.g = false;
      this.h = b("\u0002l");
      this.i = false;
      this.j = false;
      this.padTextString = " ";
      this.d = var1;
      this.g = var2;
   }

   public XMLOutputter(String var1, boolean var2, String var3) {
      this.a = false;
      this.b = b("Z2KB");
      this.c = false;
      this.d = null;
      this.e = 0;
      this.f = false;
      this.g = false;
      this.h = b("\u0002l");
      this.i = false;
      this.j = false;
      this.padTextString = " ";
      this.d = var1;
      this.g = var2;
      this.b = var3;
   }

   public XMLOutputter(XMLOutputter var1) {
      boolean var2 = q;
      super();
      this.a = false;
      this.b = b("Z2KB");
      this.c = false;
      this.d = null;
      this.e = 0;
      this.f = false;
      this.g = false;
      this.h = b("\u0002l");
      this.i = false;
      this.j = false;
      this.padTextString = " ";
      this.a = var1.a;
      this.c = var1.c;
      this.d = var1.d;
      this.e = var1.e;
      this.f = var1.f;
      this.g = var1.g;
      this.b = var1.b;
      this.h = var1.h;
      this.i = var1.i;
      this.j = var1.j;
      if (var2) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public void setLineSeparator(String var1) {
      this.h = var1;
   }

   public void setNewlines(boolean var1) {
      this.g = var1;
   }

   public void setEncoding(String var1) {
      this.b = var1;
   }

   public void setOmitEncoding(boolean var1) {
      this.c = var1;
   }

   public void setSuppressDeclaration(boolean var1) {
      this.a = var1;
   }

   public void setExpandEmptyElements(boolean var1) {
      this.f = var1;
   }

   public void setTrimText(boolean var1) {
      this.i = var1;
   }

   public void setPadText(boolean var1) {
      this.j = var1;
   }

   public void setIndent(String var1) {
      if ("".equals(var1)) {
         var1 = null;
      }

      this.d = var1;
   }

   public void setIndent(boolean var1) {
      if (var1) {
         this.d = b("/F");
         if (!q) {
            return;
         }
      }

      this.d = null;
   }

   public void setIndentLevel(int var1) {
      this.e = var1;
   }

   public void setIndentSize(int var1) {
      boolean var4 = q;
      StringBuffer var2 = new StringBuffer();
      int var3 = 0;

      while(true) {
         if (var3 < var1) {
            var2.append(" ");
            ++var3;
            if (var4) {
               break;
            }

            if (!var4) {
               continue;
            }
         }

         this.d = var2.toString();
         break;
      }

   }

   protected void indent(Writer var1, int var2) throws IOException {
      if (this.d != null && !this.d.equals("")) {
         int var3 = 0;

         while(var3 < var2) {
            var1.write(this.d);
            ++var3;
            if (q) {
               break;
            }
         }
      }

   }

   protected void maybePrintln(Writer var1) throws IOException {
      if (this.g) {
         var1.write(this.h);
      }

   }

   protected Writer makeWriter(OutputStream var1) throws UnsupportedEncodingException {
      OutputStreamWriter var2 = new OutputStreamWriter(new BufferedOutputStream(var1), this.b);
      return var2;
   }

   protected Writer makeWriter(OutputStream var1, String var2) throws UnsupportedEncodingException {
      OutputStreamWriter var3 = new OutputStreamWriter(new BufferedOutputStream(var1), var2);
      return var3;
   }

   public void output(Document var1, OutputStream var2) throws IOException {
      Writer var3 = this.makeWriter(var2);
      this.output(var1, var3);
      var3.flush();
   }

   public void output(Document var1, Writer var2) throws IOException {
      boolean var5 = q;
      if (this.e > 0) {
         this.indent(var2, this.e);
      }

      this.printDeclaration(var1, var2, this.b);
      if (var1.getDocType() != null) {
         if (this.e > 0) {
            this.indent(var2, this.e);
         }

         this.printDocType(var1.getDocType(), var2);
      }

      Iterator var3 = var1.getMixedContent().iterator();

      while(true) {
         if (var3.hasNext()) {
            Object var4 = var3.next();
            if (var5) {
               break;
            }

            label53: {
               if (var4 instanceof Element) {
                  this.output(var1.getRootElement(), var2);
                  if (!var5) {
                     break label53;
                  }
               }

               if (var4 instanceof Comment) {
                  this.printComment((Comment)var4, var2, this.e);
                  if (!var5) {
                     break label53;
                  }
               }

               if (var4 instanceof ProcessingInstruction) {
                  this.printProcessingInstruction((ProcessingInstruction)var4, var2, this.e);
                  if (!var5) {
                     break label53;
                  }
               }

               if (var4 instanceof CDATA) {
                  this.printCDATASection((CDATA)var4, var2, this.e);
               }
            }

            if (!var5) {
               continue;
            }
         }

         var2.write(this.h);
         break;
      }

   }

   public void output(Element var1, Writer var2) throws IOException {
      this.printElement(var1, var2, this.e, new a());
   }

   public void output(Element var1, OutputStream var2) throws IOException {
      Writer var3 = this.makeWriter(var2);
      this.output(var1, var3);
      var3.flush();
   }

   public void outputElementContent(Element var1, Writer var2) throws IOException {
      List var3 = var1.getMixedContent();
      this.printElementContent(var1, var2, this.e, new a(), var3);
   }

   public void output(CDATA var1, Writer var2) throws IOException {
      this.printCDATASection(var1, var2, this.e);
   }

   public void output(CDATA var1, OutputStream var2) throws IOException {
      Writer var3 = this.makeWriter(var2);
      this.output(var1, var3);
      var3.flush();
   }

   public void output(Comment var1, Writer var2) throws IOException {
      this.printComment(var1, var2, this.e);
   }

   public void output(Comment var1, OutputStream var2) throws IOException {
      Writer var3 = this.makeWriter(var2);
      this.output(var1, var3);
      var3.flush();
   }

   public void output(String var1, Writer var2) throws IOException {
      this.printString(var1, var2);
   }

   public void output(String var1, OutputStream var2) throws IOException {
      Writer var3 = this.makeWriter(var2);
      this.printString(var1, var3);
      var3.flush();
   }

   public void output(Entity var1, Writer var2) throws IOException {
      this.printEntity(var1, var2);
   }

   public void output(Entity var1, OutputStream var2) throws IOException {
      Writer var3 = this.makeWriter(var2);
      this.printEntity(var1, var3);
      var3.flush();
   }

   public void output(ProcessingInstruction var1, Writer var2) throws IOException {
      this.printProcessingInstruction(var1, var2, this.e);
   }

   public void output(ProcessingInstruction var1, OutputStream var2) throws IOException {
      Writer var3 = this.makeWriter(var2);
      this.output(var1, var3);
      var3.flush();
   }

   public String outputString(Document var1) throws IOException {
      StringWriter var2 = new StringWriter();
      this.output((Document)var1, (Writer)var2);
      var2.flush();
      return var2.toString();
   }

   public String outputString(Element var1) throws IOException {
      StringWriter var2 = new StringWriter();
      this.output((Element)var1, (Writer)var2);
      var2.flush();
      return var2.toString();
   }

   protected void printDeclaration(Document var1, Writer var2, String var3) throws IOException {
      if (!this.a) {
         label25: {
            if (var3.equals(b("Z2KB"))) {
               var2.write(b("3Yu\u0017t/\u0010h\bkf\tcG:>H=X"));
               if (!this.c) {
                  var2.write(b("/\u0003c\u0019wk\u000fc\u001d%-3Y<57D"));
               }

               var2.write(b("0X"));
               if (!q) {
                  break label25;
               }
            }

            var2.write(b("3Yu\u0017t/\u0010h\bkf\tcG:>H=X"));
            if (!this.c) {
               var2.write(b("/\u0003c\u0019wk\u000fc\u001d%-") + var3 + "\"");
            }

            var2.write(b("0X"));
         }

         var2.write(this.h);
      }

   }

   protected void printDocType(DocType var1, Writer var2) throws IOException {
      if (var1 != null) {
         String var3 = var1.getPublicID();
         String var4 = var1.getSystemID();
         boolean var5 = false;
         var2.write(b("3GI5[[?]?8"));
         var2.write(var1.getElementName());
         if (var3 != null && !var3.equals("")) {
            var2.write(b("/6X8TF%-X"));
            var2.write(var3);
            var2.write("\"");
            var5 = true;
         }

         if (var4 != null && !var4.equals("")) {
            if (!var5) {
               var2.write(b("/5T)LJ+"));
            }

            var2.write(b("/D"));
            var2.write(var4);
            var2.write("\"");
         }

         var2.write(">");
         this.maybePrintln(var2);
      }
   }

   protected void printComment(Comment var1, Writer var2, int var3) throws IOException {
      this.indent(var2, var3);
      var2.write(var1.getSerializedForm());
      this.maybePrintln(var2);
   }

   protected void printProcessingInstruction(ProcessingInstruction var1, Writer var2, int var3) throws IOException {
      this.indent(var2, var3);
      var2.write(var1.getSerializedForm());
      this.maybePrintln(var2);
   }

   protected void printCDATASection(CDATA var1, Writer var2, int var3) throws IOException {
      this.indent(var2, var3);
      var2.write(var1.getSerializedForm());
      this.maybePrintln(var2);
   }

   protected void printElement(Element var1, Writer var2, int var3, a var4) throws IOException {
      boolean var15 = q;
      List var5 = var1.getMixedContent();
      boolean var6 = var5.size() == 0;
      boolean var7 = !var6 && var5.size() == 1 && var5.get(0) instanceof String;
      this.indent(var2, var3);
      var2.write("<");
      var2.write(var1.getQualifiedName());
      int var8 = var4.size();
      Namespace var9 = var1.getNamespace();
      String var11;
      if (var9 != Namespace.XML_NAMESPACE && (var9 != Namespace.NO_NAMESPACE || var4.getURI("") != null)) {
         String var10 = var9.getPrefix();
         var11 = var4.getURI(var10);
         if (!var9.getURI().equals(var11)) {
            var4.push(var9);
            this.printNamespace(var9, var2);
         }
      }

      boolean var10000;
      label96: {
         List var16 = var1.getAdditionalNamespaces();
         if (var16 != null) {
            int var17 = 0;

            while(var17 < var16.size()) {
               Namespace var12 = (Namespace)var16.get(var17);
               String var13 = var12.getPrefix();
               String var14 = var4.getURI(var13);
               var10000 = var12.getURI().equals(var14);
               if (var15) {
                  break label96;
               }

               if (!var10000) {
                  var4.push(var12);
                  this.printNamespace(var12, var2);
               }

               ++var17;
               if (var15) {
                  break;
               }
            }
         }

         this.printAttributes(var1.getAttributes(), var1, var2, var4);
         var10000 = var7;
      }

      if (var10000) {
         var11 = this.i ? var1.getTextTrim() : var1.getText();
         if (var11 == null || var11.equals("")) {
            var6 = true;
         }
      }

      label114: {
         if (var6) {
            label73: {
               if (!this.f) {
                  var2.write(b("/I3"));
                  if (!var15) {
                     break label73;
                  }
               }

               var2.write(b("1Z\""));
               var2.write(var1.getQualifiedName());
               var2.write(">");
            }

            this.maybePrintln(var2);
            if (!var15) {
               break label114;
            }
         }

         label66: {
            var2.write(">");
            if (var7) {
               this.printElementContent(var1, var2, var3, var4, var5);
               if (!var15) {
                  break label66;
               }
            }

            this.maybePrintln(var2);
            this.printElementContent(var1, var2, var3, var4, var5);
            this.indent(var2, var3);
         }

         var2.write(b("3I"));
         var2.write(var1.getQualifiedName());
         var2.write(">");
         this.maybePrintln(var2);
      }

      while(var4.size() > var8) {
         var4.pop();
         if (var15) {
            break;
         }
      }

      if (SnmpException.b) {
         q = !var15;
      }

   }

   protected void printElementContent(Element var1, Writer var2, int var3, a var4, List var5) throws IOException {
      boolean var12 = q;
      boolean var6 = var5.size() == 0;
      boolean var7 = !var6 && var5.size() == 1 && var5.get(0) instanceof String;
      String var8;
      if (var7) {
         var8 = this.i ? var1.getTextTrim() : var1.getText();
         var2.write(this.escapeElementEntities(var8));
         if (!var12) {
            return;
         }
      }

      var8 = null;
      Class var9 = null;
      int var10 = 0;
      int var11 = var5.size();

      while(var10 < var11) {
         label127: {
            Object var13 = var5.get(var10);
            if (var13 instanceof Comment) {
               this.printComment((Comment)var13, var2, var3 + 1);
               var9 = k == null ? (k = a(b("b\tc\u001cwwHg\u001ewbHN\u0015ub\u0003c\u000e"))) : k;
               if (!var12) {
                  break label127;
               }
            }

            if (var13 instanceof String) {
               if (this.j && var9 == (l == null ? (l = a(b("b\tc\u001cwwHg\u001ewbHH\u0016}b\u0003c\u000e"))) : l)) {
                  var2.write(this.padTextString);
               }

               this.printString((String)var13, var2);
               var9 = m == null ? (m = a(b("e\u0007{\u001b6c\u0007c\u001d6\\\u0012\u007f\u0013vh"))) : m;
               if (!var12) {
                  break label127;
               }
            }

            if (var13 instanceof Element) {
               if (this.j && var9 == (m == null ? (m = a(b("e\u0007{\u001b6c\u0007c\u001d6\\\u0012\u007f\u0013vh"))) : m)) {
                  var2.write(this.padTextString);
               }

               this.printElement((Element)var13, var2, var3 + 1, var4);
               var9 = l == null ? (l = a(b("b\tc\u001cwwHg\u001ewbHH\u0016}b\u0003c\u000e"))) : l;
               if (!var12) {
                  break label127;
               }
            }

            if (var13 instanceof Entity) {
               this.printEntity((Entity)var13, var2);
               var9 = n == null ? (n = a(b("b\tc\u001cwwHg\u001ewbHH\u0014lf\u0012t"))) : n;
               if (!var12) {
                  break label127;
               }
            }

            if (var13 instanceof ProcessingInstruction) {
               this.printProcessingInstruction((ProcessingInstruction)var13, var2, var3 + 1);
               var9 = o == null ? (o = a(b("b\tc\u001cwwHg\u001ewbH]\bwl\u0003~\tqa\u0001D\u0014k{\u0014x\u0019lf\tc"))) : o;
               if (!var12) {
                  break label127;
               }
            }

            if (var13 instanceof CDATA) {
               this.printCDATASection((CDATA)var13, var2, var3 + 1);
               var9 = p == null ? (p = a(b("b\tc\u001cwwHg\u001ewbHN>Y['"))) : p;
            }
         }

         ++var10;
         if (var12) {
            break;
         }
      }

   }

   protected void printString(String var1, Writer var2) throws IOException {
      boolean var5 = q;
      var1 = this.escapeElementEntities(var1);
      if (this.i) {
         StringTokenizer var3 = new StringTokenizer(var1);

         while(var3.hasMoreTokens()) {
            String var4 = var3.nextToken();
            var2.write(var4);
            if (var5) {
               return;
            }

            if (var3.hasMoreTokens()) {
               var2.write(" ");
            }

            if (var5) {
               break;
            }
         }

         if (!var5) {
            return;
         }
      }

      var2.write(var1);
   }

   protected void printEntity(Entity var1, Writer var2) throws IOException {
      var2.write(var1.getSerializedForm());
   }

   protected void printNamespace(Namespace var1, Writer var2) throws IOException {
      var2.write(b("/\u001e`\u0016v|"));
      String var3 = var1.getPrefix();
      if (!var3.equals("")) {
         var2.write(":");
         var2.write(var3);
      }

      var2.write(b("2D"));
      var2.write(var1.getURI());
      var2.write("\"");
   }

   protected void printAttributes(List var1, Element var2, Writer var3, a var4) throws IOException {
      new HashSet();
      int var6 = 0;
      int var7 = var1.size();

      while(var6 < var7) {
         Attribute var8 = (Attribute)var1.get(var6);
         Namespace var9 = var8.getNamespace();
         if (var9 != Namespace.NO_NAMESPACE && var9 != Namespace.XML_NAMESPACE) {
            String var10 = var9.getPrefix();
            String var11 = var4.getURI(var10);
            if (!var9.getURI().equals(var11)) {
               this.printNamespace(var9, var3);
               var4.push(var9);
            }
         }

         var3.write(" ");
         var3.write(var8.getQualifiedName());
         var3.write("=");
         var3.write("\"");
         var3.write(this.escapeAttributeEntities(var8.getValue()));
         var3.write("\"");
         ++var6;
         if (q) {
            break;
         }
      }

   }

   protected String escapeAttributeEntities(String var1) {
      boolean var7 = q;
      StringBuffer var2 = new StringBuffer();
      char[] var3 = var1.toCharArray();
      String var4 = null;
      int var5 = 0;
      int var6 = 0;

      int var10000;
      while(true) {
         if (var5 < var3.length) {
            var10000 = var3[var5];
            if (var7) {
               break;
            }

            switch (var10000) {
               case 60:
                  var4 = b(")\nyA");
                  if (!var7) {
                     break;
                  }
               case 62:
                  var4 = b(")\u0001yA");
                  if (!var7) {
                     break;
                  }
               case 39:
                  var4 = b(")\u0007}\u0015k4");
                  if (!var7) {
                     break;
                  }
               case 34:
                  var4 = b(")\u0017x\u0015l4");
                  if (!var7) {
                     break;
                  }
               case 38:
                  var4 = b(")\u0007`\n#");
                  if (var7) {
                  }
            }

            if (var4 != null) {
               var2.append(var3, var6, var5 - var6);
               var2.append(var4);
               var4 = null;
               var6 = var5 + 1;
            }

            ++var5;
            if (!var7) {
               continue;
            }
         }

         var10000 = var6;
         break;
      }

      if (var10000 < var3.length) {
         var2.append(var3, var6, var5 - var6);
      }

      return var2.toString();
   }

   protected String escapeElementEntities(String var1) {
      boolean var7 = q;
      StringBuffer var2 = new StringBuffer();
      char[] var3 = var1.toCharArray();
      String var4 = null;
      int var5 = 0;
      int var6 = 0;

      int var10000;
      while(true) {
         if (var5 < var3.length) {
            var10000 = var3[var5];
            if (var7) {
               break;
            }

            switch (var10000) {
               case 60:
                  var4 = b(")\nyA");
                  if (!var7) {
                     break;
                  }
               case 62:
                  var4 = b(")\u0001yA");
                  if (!var7) {
                     break;
                  }
               case 38:
                  var4 = b(")\u0007`\n#");
                  if (var7) {
                  }
            }

            if (var4 != null) {
               var2.append(var3, var6, var5 - var6);
               var2.append(var4);
               var4 = null;
               var6 = var5 + 1;
            }

            ++var5;
            if (!var7) {
               continue;
            }
         }

         var10000 = var6;
         break;
      }

      if (var10000 < var3.length) {
         var2.append(var3, var6, var5 - var6);
      }

      return var2.toString();
   }

   public int parseArgs(String[] var1, int var2) {
      boolean var3 = q;

      int var10000;
      while(true) {
         if (var2 < var1.length) {
            var10000 = var1[var2].equals(b("\"\u0015x\nh}\u0003~\t\\j\u0005a\u001bjn\u0012d\u0015v"));
            if (var3) {
               break;
            }

            label82: {
               if (var10000 != 0) {
                  this.setSuppressDeclaration(true);
                  if (!var3) {
                     break label82;
                  }
               }

               if (var1[var2].equals(b("\"\t`\u0013lJ\bn\u0015|f\bj"))) {
                  this.setOmitEncoding(true);
                  if (!var3) {
                     break label82;
                  }
               }

               if (var1[var2].equals(b("\"\u000fc\u001e}a\u0012"))) {
                  ++var2;
                  this.setIndent(var1[var2]);
                  if (!var3) {
                     break label82;
                  }
               }

               if (var1[var2].equals(b("\"\u000fc\u001e}a\u0012^\u0013bj"))) {
                  ++var2;
                  this.setIndentSize(Integer.parseInt(var1[var2]));
                  if (!var3) {
                     break label82;
                  }
               }

               if (var1[var2].equals(b("\"\u000fc\u001e}a\u0012A\u001fnj\n"))) {
                  ++var2;
                  this.setIndentLevel(Integer.parseInt(var1[var2]));
                  if (!var3) {
                     break label82;
                  }
               }

               if (var1[var2].startsWith(b("\"\u0003u\nya\u0002H\u0017h{\u001f"))) {
                  this.setExpandEmptyElements(true);
                  if (!var3) {
                     break label82;
                  }
               }

               if (var1[var2].equals(b("\"\u0003c\u0019wk\u000fc\u001d"))) {
                  ++var2;
                  this.setEncoding(var1[var2]);
                  if (!var3) {
                     break label82;
                  }
               }

               if (var1[var2].equals(b("\"\bh\rtf\bh\t"))) {
                  this.setNewlines(true);
                  if (!var3) {
                     break label82;
                  }
               }

               if (var1[var2].equals(b("\"\nd\u0014}\\\u0003}\u001bjn\u0012b\b"))) {
                  ++var2;
                  this.setLineSeparator(var1[var2]);
                  if (!var3) {
                     break label82;
                  }
               }

               if (var1[var2].equals(b("\"\u0012\u007f\u0013u[\u0003u\u000e"))) {
                  this.setTrimText(true);
                  if (!var3) {
                     break label82;
                  }
               }

               if (!var1[var2].equals(b("\"\u0016l\u001eLj\u001ey"))) {
                  return var2;
               }

               this.setPadText(true);
               if (var3) {
                  return var2;
               }
            }

            ++var2;
            if (!var3) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      return var10000;
   }

   // $FF: synthetic method
   static Class a(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 15;
               break;
            case 1:
               var10003 = 102;
               break;
            case 2:
               var10003 = 13;
               break;
            case 3:
               var10003 = 122;
               break;
            default:
               var10003 = 24;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
