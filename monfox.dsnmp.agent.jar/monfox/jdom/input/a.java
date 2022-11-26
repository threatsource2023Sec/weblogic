package monfox.jdom.input;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

class a extends DefaultHandler implements LexicalHandler {
   private Document a;
   private Stack b;
   private boolean c;
   private boolean d;
   private boolean e;
   private boolean f;
   private LinkedList g;
   private LinkedList h;

   public a(Document var1) throws IOException {
      boolean var2 = BuilderErrorHandler.a;
      super();
      this.a = var1;
      this.c = true;
      this.b = new Stack();
      this.g = new LinkedList();
      this.h = new LinkedList();
      this.h.add(Namespace.XML_NAMESPACE);
      this.f = false;
      this.d = false;
      this.e = false;
      if (var2) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public void processingInstruction(String var1, String var2) throws SAXException {
      if (this.c) {
         this.a.addContent(new ProcessingInstruction(var1, var2));
         if (!BuilderErrorHandler.a) {
            return;
         }
      }

      ((Element)this.b.peek()).addContent(new ProcessingInstruction(var1, var2));
   }

   public void startPrefixMapping(String var1, String var2) throws SAXException {
      Namespace var3 = Namespace.getNamespace(var1, var2);
      this.g.add(var3);
   }

   public void endPrefixMapping(String var1) throws SAXException {
      Iterator var2 = this.h.iterator();

      while(var2.hasNext()) {
         Namespace var3 = (Namespace)var2.next();
         if (var1.equals(var3.getPrefix())) {
            var2.remove();
            return;
         }

         if (BuilderErrorHandler.a) {
            break;
         }
      }

   }

   public void startElement(String var1, String var2, String var3, Attributes var4) throws SAXException {
      boolean var12 = BuilderErrorHandler.a;
      Element var5 = null;
      int var7;
      if (var1 != null && !var1.equals("")) {
         String var6 = "";
         if (var2 != var3) {
            var7 = var3.indexOf(":");
            var6 = var3.substring(0, var7);
         }

         Namespace var14 = Namespace.getNamespace(var6, var1);
         var5 = new Element(var2, var14);
         this.g.remove(var14);
         this.h.addFirst(var14);
      } else {
         var5 = new Element(var2);
      }

      this.a(var5);
      int var13 = 0;
      var7 = var4.getLength();

      while(true) {
         if (var13 < var7) {
            Attribute var8 = null;
            String var9 = var4.getLocalName(var13);
            String var10 = var4.getQName(var13);
            if (var12) {
               break;
            }

            if (var9 != var10) {
               String var11 = var10.substring(0, var10.indexOf(":"));
               var8 = new Attribute(var9, var4.getValue(var13), this.a(var11));
            } else {
               var8 = new Attribute(var9, var4.getValue(var13));
            }

            var5.addAttribute(var8);
            ++var13;
            if (!var12) {
               continue;
            }
         }

         if (this.c) {
            this.a.setRootElement(var5);
            this.b.push(var5);
            this.c = false;
            if (!var12) {
               break;
            }
         }

         ((Element)this.b.peek()).addContent(var5);
         this.b.push(var5);
         break;
      }

      if (SnmpException.b) {
         BuilderErrorHandler.a = !var12;
      }

   }

   private void a(Element var1) {
      Iterator var2 = this.g.iterator();

      while(var2.hasNext()) {
         Namespace var3 = (Namespace)var2.next();
         var2.remove();
         this.h.addFirst(var3);
         var1.addNamespaceDeclaration(var3);
         if (BuilderErrorHandler.a) {
            break;
         }
      }

   }

   private Namespace a(String var1) {
      boolean var4 = BuilderErrorHandler.a;
      Iterator var2 = this.h.iterator();

      Namespace var10000;
      while(true) {
         if (var2.hasNext()) {
            var10000 = (Namespace)var2.next();
            if (var4) {
               break;
            }

            Namespace var3 = var10000;
            if (var1.equals(var3.getPrefix())) {
               return var3;
            }

            if (!var4) {
               continue;
            }
         }

         var10000 = Namespace.NO_NAMESPACE;
         break;
      }

      return var10000;
   }

   public void characters(char[] var1, int var2, int var3) throws SAXException {
      boolean var6 = BuilderErrorHandler.a;
      String var4 = new String(var1, var2, var3);
      if (this.e) {
         ((Element)this.b.peek()).addContent(new CDATA(var4));
         if (!var6) {
            return;
         }
      }

      if (this.f) {
         ((Entity)this.b.peek()).setContent(var4);
         if (!var6) {
            return;
         }
      }

      Element var5 = (Element)this.b.peek();
      var5.addContent(var4);
   }

   public void endElement(String var1, String var2, String var3) {
      Element var4 = (Element)this.b.pop();
      if (this.b.empty()) {
         this.c = true;
      }

      this.h.remove(var4.getAdditionalNamespaces());
   }

   public void startDTD(String var1, String var2, String var3) throws SAXException {
      this.a.setDocType(new DocType(var1, var2, var3));
      this.d = true;
   }

   public void endDTD() throws SAXException {
      this.d = false;
   }

   public void startEntity(String var1) throws SAXException {
      if (!this.d && !var1.equals(b("\re;")) && !var1.equals(b("\u0000|")) && !var1.equals(b("\u000b|")) && !var1.equals(b("\rx$h")) && !var1.equals(b("\u001d}$o"))) {
         Entity var2 = new Entity(var1);
         ((Element)this.b.peek()).addContent(var2);
         this.b.push(var2);
         this.f = true;
      }

   }

   public void endEntity(String var1) throws SAXException {
      if (this.f) {
         this.b.pop();
         this.f = false;
      }

   }

   public void startCDATA() throws SAXException {
      this.e = true;
   }

   public void endCDATA() throws SAXException {
      this.e = false;
   }

   public void comment(char[] var1, int var2, int var3) throws SAXException {
      String var4 = new String(var1, var2, var3);
      if (!this.d && !var4.equals("")) {
         if (this.b.empty()) {
            this.a.addContent(new Comment(var4));
            if (!BuilderErrorHandler.a) {
               return;
            }
         }

         ((Element)this.b.peek()).addContent(new Comment(var4));
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
               var10003 = 108;
               break;
            case 1:
               var10003 = 8;
               break;
            case 2:
               var10003 = 75;
               break;
            case 3:
               var10003 = 27;
               break;
            default:
               var10003 = 7;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
