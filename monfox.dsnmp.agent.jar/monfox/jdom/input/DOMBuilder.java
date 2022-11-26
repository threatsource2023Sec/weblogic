package monfox.jdom.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import monfox.jdom.Attribute;
import monfox.jdom.CDATA;
import monfox.jdom.Comment;
import monfox.jdom.DocType;
import monfox.jdom.Document;
import monfox.jdom.Element;
import monfox.jdom.Entity;
import monfox.jdom.JDOMException;
import monfox.jdom.Namespace;
import monfox.jdom.ProcessingInstruction;
import monfox.jdom.adapters.DOMAdapter;
import org.w3c.dom.Attr;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

public class DOMBuilder {
   private static final String a = "w\u0002\u000b\u0005{bC\u000f\u0007{wC\u0004\u0007uj\u0019\u0000\u0011g45\u0000\u0011w\u007f\u001e!,Y[\t\u0004\u0013`\u007f\u001f";
   private boolean b;
   private String c;
   // $FF: synthetic field
   static Class d;
   // $FF: synthetic field
   static Class e;

   public DOMBuilder() {
      this(false);
   }

   public DOMBuilder(boolean var1) {
      this.setValidation(var1);
   }

   public DOMBuilder(String var1) {
      this(var1, false);
   }

   public DOMBuilder(String var1, boolean var2) {
      this.c = var1;
      this.setValidation(var2);
   }

   public void setValidation(boolean var1) {
      this.b = var1;
   }

   public Document build(InputStream var1) throws JDOMException {
      Document var2 = new Document((Element)null);
      org.w3c.dom.Document var3 = null;

      try {
         DOMAdapter var4;
         if (this.c != null) {
            try {
               var4 = (DOMAdapter)Class.forName(this.c).newInstance();
               var3 = var4.getDocument(var1, this.b);
            } catch (ClassNotFoundException var18) {
            }
         } else {
            try {
               Class.forName(b("p\f\u0013\u0002l4\u0015\b\u000f:n\u001f\u0004\rg|\u0002\u0017\u000e:N\u001f\u0004\rg|\u0002\u0017\u000eqh"));
               Class var20 = Class.forName(b("p\f\u0013\u0002l4\u0015\b\u000f:j\f\u0017\u0010qh\u001eK'{y\u0018\b\u0006zn/\u0010\nx~\b\u0017%uy\u0019\n\u0011m"));
               Method var21 = var20.getMethod(b("t\b\u0012*zi\u0019\u0004\rw\u007f"), (Class[])null);
               Object var22 = var21.invoke((Object)null, (Object[])null);
               Method var7 = var20.getMethod(b("i\b\u00115uv\u0004\u0001\u0002`s\u0003\u0002"), Boolean.TYPE);
               var7.invoke(var22, new Boolean(this.b));
               Method var8 = var20.getMethod(b("i\b\u0011-uw\b\u0016\u0013uy\b$\u0014uh\b"), Boolean.TYPE);
               var8.invoke(var22, Boolean.TRUE);
               Method var9 = var20.getMethod(b("t\b\u0012'{y\u0018\b\u0006zn/\u0010\nx~\b\u0017"), (Class[])null);
               Object var10 = var9.invoke(var22, (Object[])null);
               Class var11 = var10.getClass();
               Method var12 = var11.getMethod(b("i\b\u0011&fh\u0002\u0017+ut\t\t\u0006f"), d == null ? (d = a(b("u\u001f\u0002Mlw\u0001K\u0010ubC \u0011fu\u001f-\u0002z~\u0001\u0000\u0011"))) : d);
               var12.invoke(var10, new BuilderErrorHandler());
               Method var13 = var11.getMethod(b("j\f\u0017\u0010q"), e == null ? (e = a(b("p\f\u0013\u0002:s\u0002K*zj\u0018\u00110`h\b\u0004\u000e"))) : e);
               var3 = (org.w3c.dom.Document)var13.invoke(var10, var1);
            } catch (ClassNotFoundException var15) {
            } catch (NoSuchMethodException var16) {
            } catch (InvocationTargetException var17) {
               throw var17.getTargetException();
            }
         }

         if (var3 == null && this.c == null) {
            try {
               var4 = (DOMAdapter)Class.forName(b("w\u0002\u000b\u0005{bC\u000f\u0007{wC\u0004\u0007uj\u0019\u0000\u0011g45\u0000\u0011w\u007f\u001e!,Y[\t\u0004\u0013`\u007f\u001f")).newInstance();
               var3 = var4.getDocument(var1, this.b);
            } catch (ClassNotFoundException var14) {
            }
         }

         this.a(var3, var2, (Element)null, true);
         return var2;
      } catch (Throwable var19) {
         if (var19 instanceof SAXParseException) {
            SAXParseException var5 = (SAXParseException)var19;
            String var6 = var5.getSystemId();
            if (var6 != null) {
               throw new JDOMException(b("_\u001f\u0017\ff:\u0002\u000bCxs\u0003\u0000C") + var5.getLineNumber() + b(":\u0002\u0003Cpu\u000e\u0010\u000eqt\u0019E") + var6, var19);
            } else {
               throw new JDOMException(b("_\u001f\u0017\ff:\u0002\u000bCxs\u0003\u0000C") + var5.getLineNumber(), var19);
            }
         } else {
            throw new JDOMException(b("_\u001f\u0017\ff:\u0004\u000bCvo\u0004\t\u0007}t\nE\u0005fu\u0000E\u0010`h\b\u0004\u000e"), var19);
         }
      }
   }

   public Document build(File var1) throws JDOMException {
      try {
         FileInputStream var2 = new FileInputStream(var1);
         return this.build((InputStream)var2);
      } catch (FileNotFoundException var3) {
         throw new JDOMException(b("_\u001f\u0017\ff:\u0004\u000bCvo\u0004\t\u0007}t\nE\u0005fu\u0000E") + var1, var3);
      }
   }

   public Document build(URL var1) throws JDOMException {
      try {
         return this.build(var1.openStream());
      } catch (IOException var3) {
         throw new JDOMException(b("_\u001f\u0017\ff:\u0004\u000bCvo\u0004\t\u0007}t\nE\u0005fu\u0000E") + var1, var3);
      }
   }

   public Document build(org.w3c.dom.Document var1) {
      Document var2 = new Document((Element)null);
      this.a(var1, var2, (Element)null, true);
      return var2;
   }

   public Element build(org.w3c.dom.Element var1) {
      Document var2 = new Document((Element)null);
      this.a(var1, var2, (Element)null, true);
      return var2.getRootElement();
   }

   private void a(Node var1, Document var2, Element var3, boolean var4) {
      String var29;
      label152: {
         boolean var20;
         label151: {
            label156: {
               label157: {
                  label148: {
                     label147: {
                        var20 = BuilderErrorHandler.a;
                        switch (var1.getNodeType()) {
                           case 2:
                           default:
                              return;
                           case 3:
                              break;
                           case 4:
                              break label147;
                           case 5:
                              break label156;
                           case 6:
                              break label151;
                           case 7:
                              break label148;
                           case 8:
                              break label157;
                           case 9:
                              NodeList var5 = var1.getChildNodes();
                              int var6 = 0;
                              int var7 = var5.getLength();

                              while(var6 < var7) {
                                 this.a(var5.item(var6), var2, var3, true);
                                 ++var6;
                                 if (var20 && var20) {
                                    return;
                                 }
                              }

                              if (!var20) {
                                 return;
                              }
                           case 1:
                              String var21 = var1.getLocalName();
                              String var22 = var1.getPrefix();
                              String var8 = var1.getNamespaceURI();
                              Element var9 = null;
                              Namespace var10 = null;
                              if (var8 == null) {
                                 var9 = new Element(var21);
                              } else {
                                 var10 = Namespace.getNamespace(var22, var8);
                                 var9 = new Element(var21, var10);
                              }

                              NamedNodeMap var11 = var1.getAttributes();
                              int var12 = 0;
                              int var13 = var11.getLength();

                              boolean var10000;
                              label120: {
                                 while(var12 < var13) {
                                    Attr var14 = (Attr)var11.item(var12);
                                    String var15 = var14.getName();
                                    String var16 = var14.getValue();
                                    var10000 = var15.equals(b("b\u0000\t\rg"));
                                    if (var20) {
                                       break label120;
                                    }

                                    label161: {
                                       if (var10000) {
                                          Namespace var17 = Namespace.getNamespace("", var16);
                                          if (!var17.equals(var10)) {
                                             var9.addNamespaceDeclaration(var17);
                                          }

                                          if (!var20) {
                                             break label161;
                                          }
                                       }

                                       Namespace var18;
                                       if (var15.startsWith(b("b\u0000\t\rg "))) {
                                          var29 = var15.substring(6);
                                          var18 = Namespace.getNamespace(var29, var16);
                                          if (!var18.equals(var10)) {
                                             var9.addNamespaceDeclaration(var18);
                                          }

                                          if (!var20) {
                                             break label161;
                                          }
                                       }

                                       var22 = var14.getPrefix();
                                       var8 = var14.getNamespaceURI();
                                       var29 = var14.getLocalName();
                                       var18 = Namespace.getNamespace(var22, var8);
                                       Attribute var19 = new Attribute(var29, var16, var18);
                                       var9.addAttribute(var19);
                                    }

                                    ++var12;
                                    if (var20) {
                                       break;
                                    }
                                 }

                                 var10000 = var4;
                              }

                              label99: {
                                 if (var10000) {
                                    var2.setRootElement(var9);
                                    if (!var20) {
                                       break label99;
                                    }
                                 }

                                 var3.addContent(var9);
                              }

                              NodeList var23 = var1.getChildNodes();
                              var13 = 0;
                              int var25 = var23.getLength();

                              while(var13 < var25) {
                                 this.a(var23.item(var13), var2, var9, false);
                                 ++var13;
                                 if (var20 && var20) {
                                    return;
                                 }
                              }

                              if (!var20) {
                                 return;
                              }
                              break;
                           case 10:
                              break label152;
                        }

                        String var24 = var1.getNodeValue();
                        var3.addContent(var24);
                        if (!var20) {
                           return;
                        }
                     }

                     String var26 = var1.getNodeValue();
                     var3.addContent(new CDATA(var26));
                     if (!var20) {
                        return;
                     }
                  }

                  if (var4) {
                     var2.addContent(new ProcessingInstruction(var1.getNodeName(), var1.getNodeValue()));
                     if (!var20) {
                        return;
                     }
                  }

                  var3.addContent(new ProcessingInstruction(var1.getNodeName(), var1.getNodeValue()));
                  if (!var20) {
                     return;
                  }
               }

               if (var4) {
                  var2.addContent(new Comment(var1.getNodeValue()));
                  if (!var20) {
                     return;
                  }
               }

               var3.addContent(new Comment(var1.getNodeValue()));
               if (!var20) {
                  return;
               }
            }

            Entity var27 = new Entity(var1.getNodeName());
            var27.setContent(var1.getFirstChild().getNodeValue());
            var3.addContent(var27);
            if (!var20) {
               return;
            }
         }

         if (!var20) {
            return;
         }
      }

      DocumentType var28 = (DocumentType)var1;
      var29 = var28.getPublicId();
      String var30 = var28.getSystemId();
      DocType var31 = new DocType(var28.getName());
      if (var29 != null && !var29.equals("")) {
         var31.setPublicID(var29);
      }

      if (var30 != null && !var30.equals("")) {
         var31.setSystemID(var30);
      }

      var2.setDocType(var31);
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
               var10003 = 26;
               break;
            case 1:
               var10003 = 109;
               break;
            case 2:
               var10003 = 101;
               break;
            case 3:
               var10003 = 99;
               break;
            default:
               var10003 = 20;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
