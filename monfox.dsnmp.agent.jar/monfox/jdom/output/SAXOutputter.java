package monfox.jdom.output;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import monfox.jdom.Attribute;
import monfox.jdom.CDATA;
import monfox.jdom.DocType;
import monfox.jdom.Document;
import monfox.jdom.Element;
import monfox.jdom.JDOMException;
import monfox.jdom.Namespace;
import monfox.jdom.ProcessingInstruction;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.LocatorImpl;

public class SAXOutputter {
   private ContentHandler a;
   private ErrorHandler b;
   private DTDHandler c;
   private EntityResolver d;

   public SAXOutputter(ContentHandler var1) {
      this.a = var1;
   }

   public SAXOutputter(ContentHandler var1, ErrorHandler var2, DTDHandler var3, EntityResolver var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public void setContentHandler(ContentHandler var1) {
      this.a = var1;
   }

   public void setErrorHandler(ErrorHandler var1) {
      this.b = var1;
   }

   public void setDTDHandler(DTDHandler var1) {
      this.c = var1;
   }

   public void setEntityResolver(EntityResolver var1) {
      this.d = var1;
   }

   public void output(Document var1) throws JDOMException {
      boolean var4 = XMLOutputter.q;
      if (var1 != null) {
         this.b(var1);
         this.a();
         this.a(var1);
         Iterator var2 = var1.getMixedContent().iterator();

         while(true) {
            if (var2.hasNext()) {
               Object var3 = var2.next();
               if (var4) {
                  break;
               }

               label40: {
                  if (var3 instanceof Element) {
                     this.a(var1.getRootElement(), new a());
                     if (!var4) {
                        break label40;
                     }
                  }

                  if (var3 instanceof ProcessingInstruction) {
                     this.a((ProcessingInstruction)var3);
                     if (!var4) {
                        break label40;
                     }
                  }

                  if (var3 instanceof CDATA) {
                     this.a(((CDATA)var3).getText());
                  }
               }

               if (!var4) {
                  continue;
               }
            }

            this.b();
            break;
         }

      }
   }

   private void a(Document var1) throws JDOMException {
      if (this.d != null) {
         DocType var2 = var1.getDocType();
         String var3 = null;
         String var4 = null;
         if (var2 != null) {
            var3 = var2.getPublicID();
            var4 = var2.getSystemID();
         }

         if (var3 != null || var4 != null) {
            try {
               this.d.resolveEntity(var3, var4);
            } catch (SAXException var6) {
               throw new JDOMException(b("=BkD\u0016\rfCu\u0007\u0001m"), var6);
            } catch (IOException var7) {
               throw new JDOMException(b("'Lvy\r\u000bsGh\u0001\u0000"), var7);
            }
         }
      }

   }

   private void b(Document var1) {
      LocatorImpl var2 = new LocatorImpl();
      String var3 = null;
      String var4 = null;
      DocType var5 = var1.getDocType();
      if (var5 != null) {
         var3 = var5.getPublicID();
         var4 = var5.getSystemID();
      }

      var2.setPublicId(var3);
      var2.setSystemId(var4);
      var2.setLineNumber(-1);
      var2.setColumnNumber(-1);
      this.a.setDocumentLocator(var2);
   }

   private void a() throws JDOMException {
      try {
         this.a.startDocument();
      } catch (SAXException var2) {
         throw new JDOMException(b("=BkD\u0016\rfCu\u0007\u0001m"), var2);
      }
   }

   private void b() throws JDOMException {
      try {
         this.a.endDocument();
      } catch (SAXException var2) {
         throw new JDOMException(b("=BkD\u0016\rfCu\u0007\u0001m"), var2);
      }
   }

   private void a(ProcessingInstruction var1) throws JDOMException {
      if (var1 != null) {
         String var2 = var1.getTarget();
         String var3 = var1.getData();

         try {
            this.a.processingInstruction(var2, var3);
         } catch (SAXException var5) {
            throw new JDOMException(b("=BkD\u0016\rfCu\u0007\u0001m"), var5);
         }
      }

   }

   private void a(Element var1, a var2) throws JDOMException {
      int var3 = var2.size();
      this.b(var1, var2);
      this.a(var1);
      this.c(var1, var2);
      this.b(var1);
      this.a(var2, var3);
   }

   private void b(Element var1, a var2) throws JDOMException {
      Namespace var3 = var1.getNamespace();
      if (var3 != Namespace.NO_NAMESPACE && var3 != Namespace.XML_NAMESPACE) {
         String var4 = var3.getPrefix();
         String var5 = var2.getURI(var4);
         if (!var3.getURI().equals(var5)) {
            var2.push(var3);

            try {
               this.a.startPrefixMapping(var4, var3.getURI());
            } catch (SAXException var7) {
               throw new JDOMException(b("=BkD\u0016\rfCu\u0007\u0001m"), var7);
            }
         }
      }

   }

   private void a(a var1, int var2) throws JDOMException {
      while(true) {
         if (var1.size() > var2) {
            String var3 = var1.pop();

            try {
               this.a.endPrefixMapping(var3);
            } catch (SAXException var5) {
               throw new JDOMException(b("=BkD\u0016\rfCu\u0007\u0001m"), var5);
            }

            if (!XMLOutputter.q) {
               continue;
            }
         }

         return;
      }
   }

   private void a(Element var1) throws JDOMException {
      boolean var9 = XMLOutputter.q;
      String var2 = var1.getNamespaceURI();
      String var3 = var1.getName();
      String var4 = var1.getQualifiedName();
      AttributesImpl var5 = new AttributesImpl();
      List var6 = var1.getAttributes();
      Iterator var7 = var6.iterator();

      while(true) {
         if (var7.hasNext()) {
            Attribute var8 = (Attribute)var7.next();
            var5.addAttribute(var8.getNamespaceURI(), var8.getName(), var8.getQualifiedName(), b("-GrU/"), var8.getValue());
            if (var9) {
               break;
            }

            if (!var9) {
               continue;
            }
         }

         try {
            this.a.startElement(var2, var3, var4, var5);
            break;
         } catch (SAXException var10) {
            throw new JDOMException(b("=BkD\u0016\rfCu\u0007\u0001m"), var10);
         }
      }

   }

   private void b(Element var1) throws JDOMException {
      String var2 = var1.getNamespaceURI();
      String var3 = var1.getName();
      String var4 = var1.getQualifiedName();

      try {
         this.a.endElement(var2, var3, var4);
      } catch (SAXException var6) {
         throw new JDOMException(b("=BkD\u0016\rfCu\u0007\u0001m"), var6);
      }
   }

   private void c(Element var1, a var2) throws JDOMException {
      boolean var9 = XMLOutputter.q;
      List var3 = var1.getMixedContent();
      boolean var4 = var3.size() == 0;
      boolean var5 = !var4 && var3.size() == 1 && var3.get(0) instanceof String;
      if (var5) {
         this.a(var1.getText());
         if (!var9) {
            return;
         }
      }

      Object var6 = null;
      int var7 = 0;
      int var8 = var3.size();

      while(var7 < var8) {
         label60: {
            var6 = var3.get(var7);
            if (var6 instanceof Element) {
               this.a((Element)var6, var2);
               if (!var9) {
                  break label60;
               }
            }

            if (var6 instanceof String) {
               this.a((String)var6);
               if (!var9) {
                  break label60;
               }
            }

            if (var6 instanceof CDATA) {
               this.a(((CDATA)var6).getText());
               if (!var9) {
                  break label60;
               }
            }

            if (var6 instanceof ProcessingInstruction) {
               this.a((ProcessingInstruction)var6);
            }
         }

         ++var7;
         if (var9) {
            break;
         }
      }

   }

   private void a(String var1) throws JDOMException {
      char[] var2 = var1.toCharArray();

      try {
         this.a.characters(var2, 0, var2.length);
      } catch (SAXException var4) {
         throw new JDOMException(b("=BkD\u0016\rfCu\u0007\u0001m"), var4);
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
               var10003 = 110;
               break;
            case 1:
               var10003 = 3;
               break;
            case 2:
               var10003 = 51;
               break;
            case 3:
               var10003 = 1;
               break;
            default:
               var10003 = 110;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
