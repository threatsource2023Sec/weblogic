package monfox.jdom.input;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import monfox.jdom.Document;
import monfox.jdom.Element;
import monfox.jdom.JDOMException;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SAXBuilder {
   private static final String a = "\u0019{I>X\u0006hMx\\XqKbZ\u0013z\u0000`X\u0004zKbJXZoHi\u0017{]uK";
   private boolean b;
   private String c;
   private ErrorHandler d;
   private EntityResolver e;
   private DTDHandler f;
   private XMLFilter g;

   public SAXBuilder() {
      this(false);
   }

   public SAXBuilder(boolean var1) {
      this.d = null;
      this.e = null;
      this.f = null;
      this.g = null;
      this.b = var1;
   }

   public SAXBuilder(String var1) {
      this(var1, false);
   }

   public SAXBuilder(String var1, boolean var2) {
      this.d = null;
      this.e = null;
      this.f = null;
      this.g = null;
      this.c = var1;
      this.b = var2;
   }

   public void setValidation(boolean var1) {
      this.b = var1;
   }

   public void setErrorHandler(ErrorHandler var1) {
      this.d = var1;
   }

   public void setEntityResolver(EntityResolver var1) {
      this.e = var1;
   }

   public void setDTDHandler(DTDHandler var1) {
      this.f = var1;
   }

   public void setXMLFilter(XMLFilter var1) {
      this.g = var1;
   }

   protected Document build(InputSource var1) throws JDOMException {
      boolean var12 = BuilderErrorHandler.a;
      Document var2 = new Document((Element)null);

      try {
         Object var3 = null;
         if (this.c != null) {
            var3 = XMLReaderFactory.createXMLReader(this.c);
         } else {
            try {
               Class var23 = Class.forName(a("\u001chXqAXqC|\u0017\u0006h\\c\\\u0004z\u0000Cx.YObJ\u0013{hqZ\u0002f\\i"));
               Method var25 = var23.getMethod(a("\u0018lYYW\u0005}O~Z\u0013"), (Class[])null);
               Object var6 = var25.invoke((Object)null, (Object[])null);
               Method var7 = var23.getMethod(a("\u0005lZFX\u001a`JqM\u001fgI"), Boolean.TYPE);
               var7.invoke(var6, new Boolean(this.b));
               Method var8 = var23.getMethod(a("\u0018lYCx.YObJ\u0013{"), (Class[])null);
               Object var9 = var8.invoke(var6, (Object[])null);
               Class var10 = var9.getClass();
               Method var11 = var10.getMethod(a("\u0011lZHt:[Kq]\u0013{"), (Class[])null);
               var3 = (XMLReader)var11.invoke(var9, (Object[])null);
            } catch (ClassNotFoundException var17) {
            } catch (InvocationTargetException var18) {
            } catch (NoSuchMethodException var19) {
            }
         }

         if (var3 == null) {
            var3 = XMLReaderFactory.createXMLReader(a("\u0019{I>X\u0006hMx\\XqKbZ\u0013z\u0000`X\u0004zKbJXZoHi\u0017{]uK"));
         }

         if (this.g != null) {
            XMLFilter var24 = this.g;

            label100: {
               while(var24.getParent() instanceof XMLFilter) {
                  var24 = (XMLFilter)var24.getParent();
                  if (var12) {
                     break label100;
                  }

                  if (var12) {
                     break;
                  }
               }

               var24.setParent((XMLReader)var3);
            }

            var3 = this.g;
         }

         a var26 = new a(var2);
         ((XMLReader)var3).setContentHandler(var26);
         if (this.e != null) {
            ((XMLReader)var3).setEntityResolver(this.e);
         }

         if (this.f != null) {
            ((XMLReader)var3).setDTDHandler(this.f);
         }

         boolean var27 = false;

         try {
            ((XMLReader)var3).setProperty(a("\u001e}Z`\u0003Y&V}UXf\\w\u0016\u0005hV?Q\u0017gJ|\\\u0004z\u0001\\\\\u000e`MqU>h@tU\u0013{"), var26);
            var27 = true;
         } catch (SAXNotSupportedException var15) {
         } catch (SAXNotRecognizedException var16) {
         }

         if (!var27) {
            try {
               ((XMLReader)var3).setProperty(a("\u001e}Z`\u0003Y&V}UXf\\w\u0016\u0005hV?I\u0004f^uK\u0002`Kc\u0016\u001alVyZ\u0017e\u0003xX\u0018mBuK"), var26);
               var27 = true;
            } catch (SAXNotSupportedException var13) {
            } catch (SAXNotRecognizedException var14) {
            }
         }

         try {
            label80: {
               ((XMLReader)var3).setFeature(a("\u001e}Z`\u0003Y&V}UXf\\w\u0016\u0005hV?_\u0013hZeK\u0013z\u0001fX\u001a`JqM\u001ff@"), this.b);
               ((XMLReader)var3).setFeature(a("\u001e}Z`\u0003Y&V}UXf\\w\u0016\u0005hV?_\u0013hZeK\u0013z\u0001~X\u001bl]`X\u0015l]"), true);
               ((XMLReader)var3).setFeature(a("\u001e}Z`\u0003Y&V}UXf\\w\u0016\u0005hV?_\u0013hZeK\u0013z\u0001~X\u001bl]`X\u0015l\u0003`K\u0013oGh\\\u0005"), false);
               if (this.d != null) {
                  ((XMLReader)var3).setErrorHandler(this.d);
                  if (!var12) {
                     break label80;
                  }
               }

               ((XMLReader)var3).setErrorHandler(new BuilderErrorHandler());
            }
         } catch (SAXNotSupportedException var20) {
            if (this.b) {
               throw new JDOMException(a(" hBy]\u0017}G\u007fWVgAd\u0019\u0005|^`V\u0004}Kt\u0019\u0010f\\0") + this.c + a("VZoH\u00192{Gf\\\u0004"));
            }
         } catch (SAXNotRecognizedException var21) {
            if (this.b) {
               throw new JDOMException(a(" hBy]\u0017}G\u007fWVoKqM\u0003{K0W\u0019}\u000eb\\\u0015fI~P\flJ0_\u0019{\u000e") + this.c + a("VZoH\u00192{Gf\\\u0004"));
            }
         }

         ((XMLReader)var3).parse(var1);
         return var2;
      } catch (Exception var22) {
         if (var22 instanceof SAXParseException) {
            SAXParseException var4 = (SAXParseException)var22;
            String var5 = var4.getSystemId();
            if (var5 != null) {
               throw new JDOMException(a("3{\\\u007fKVf@0U\u001fgK0") + var4.getLineNumber() + a("VfH0]\u0019j[}\\\u0018}\u000e") + var5, var22);
            } else {
               throw new JDOMException(a("3{\\\u007fKVf@0U\u001fgK0") + var4.getLineNumber(), var22);
            }
         } else {
            throw new JDOMException(a("3{\\\u007fKV`@0[\u0003`BtP\u0018n"), var22);
         }
      }
   }

   public Document build(InputStream var1) throws JDOMException {
      return this.build(new InputSource(var1));
   }

   public Document build(File var1) throws JDOMException {
      try {
         URL var2 = this.fileToURL(var1);
         return this.build(var2);
      } catch (MalformedURLException var3) {
         throw new JDOMException(a("3{\\\u007fKV`@0[\u0003`BtP\u0018n"), var3);
      }
   }

   public Document build(URL var1) throws JDOMException {
      String var2 = var1.toExternalForm();
      return this.build(new InputSource(var2));
   }

   public Document build(InputStream var1, String var2) throws JDOMException {
      InputSource var3 = new InputSource(var1);
      var3.setSystemId(var2);
      return this.build(var3);
   }

   public Document build(Reader var1) throws JDOMException {
      return this.build(new InputSource(var1));
   }

   public Document build(Reader var1, String var2) throws JDOMException {
      InputSource var3 = new InputSource(var1);
      var3.setSystemId(var2);
      return this.build(var3);
   }

   public Document build(String var1) throws JDOMException {
      return this.build(new InputSource(var1));
   }

   protected URL fileToURL(File var1) throws MalformedURLException {
      String var2 = var1.getAbsolutePath();
      if (File.separatorChar != '/') {
         var2 = var2.replace(File.separatorChar, '/');
      }

      if (!var2.startsWith("/")) {
         var2 = "/" + var2;
      }

      if (!var2.endsWith("/") && var1.isDirectory()) {
         var2 = var2 + "/";
      }

      return new URL(a("\u0010`Bu"), "", var2);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 118;
               break;
            case 1:
               var10003 = 9;
               break;
            case 2:
               var10003 = 46;
               break;
            case 3:
               var10003 = 16;
               break;
            default:
               var10003 = 57;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
