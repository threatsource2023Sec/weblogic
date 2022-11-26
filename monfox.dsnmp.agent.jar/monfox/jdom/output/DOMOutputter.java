package monfox.jdom.output;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import monfox.jdom.Attribute;
import monfox.jdom.CDATA;
import monfox.jdom.Comment;
import monfox.jdom.DocType;
import monfox.jdom.Element;
import monfox.jdom.Entity;
import monfox.jdom.JDOMException;
import monfox.jdom.Namespace;
import monfox.jdom.ProcessingInstruction;
import monfox.jdom.adapters.DOMAdapter;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Text;

public class DOMOutputter {
   private static final String a = "I\u0014a\u0013n\\Ue\u0011nIUn\u0011`T\u000fj\u0007r\n#j\u0007bA\bK:Le\u001fn\u0005uA\t";
   private String b;

   public DOMOutputter() {
   }

   public DOMOutputter(String var1) {
      this.b = var1;
   }

   public Document output(monfox.jdom.Document var1) throws JDOMException {
      boolean var9 = XMLOutputter.q;
      a var2 = new a();
      Document var3 = null;

      try {
         var3 = this.a();
         DocType var4 = var1.getDocType();
         if (var4 != null) {
         }

         Iterator var5 = var1.getMixedContent().iterator();

         while(var5.hasNext()) {
            Object var6 = var5.next();
            if (var9) {
               break;
            }

            label50: {
               if (var6 instanceof Element) {
                  Element var7 = (Element)var6;
                  org.w3c.dom.Element var8 = this.output(var7, var3, var2);
                  var3.appendChild(var8);
                  if (!var9) {
                     break label50;
                  }
               }

               if (var6 instanceof Comment) {
                  Comment var11 = (Comment)var6;
                  org.w3c.dom.Comment var13 = var3.createComment(var11.getText());
                  var3.appendChild(var13);
                  if (!var9) {
                     break label50;
                  }
               }

               if (var6 instanceof ProcessingInstruction) {
                  ProcessingInstruction var12 = (ProcessingInstruction)var6;
                  org.w3c.dom.ProcessingInstruction var14 = var3.createProcessingInstruction(var12.getTarget(), var12.getData());
                  var3.appendChild(var14);
                  if (!var9) {
                     break label50;
                  }
               }

               throw new JDOMException(a("`\u0014l\u0000lA\u0015{UbK\u0015{\u0014hJ\u001ekUuK\u000b\"\u0019dR\u001ecUbK\u0015{\u0010oP[x\u001cuL[{\fqAA") + var6.getClass().getName());
            }

            if (var9) {
               break;
            }
         }

         return var3;
      } catch (Throwable var10) {
         throw new JDOMException(a("a\u0003l\u0010qP\u0012`\u001b!K\u000e{\u0005tP\u000ff\u001bf\u0004?`\u0016tI\u001ea\u0001"), var10);
      }
   }

   public org.w3c.dom.Element output(Element var1) throws JDOMException {
      try {
         Document var2 = this.a();
         return this.output(var1, var2, new a());
      } catch (Throwable var3) {
         throw new JDOMException(a("a\u0003l\u0010qP\u0012`\u001b!K\u000e{\u0005tP\u000ff\u001bf\u0004>c\u0010lA\u0015{U") + var1.getQualifiedName(), var3);
      }
   }

   private Document a() throws Throwable {
      DOMAdapter var13;
      label29: {
         if (this.b != null) {
            try {
               var13 = (DOMAdapter)Class.forName(this.b).newInstance();
               return var13.createDocument();
            } catch (ClassNotFoundException var12) {
               if (!XMLOutputter.q) {
                  break label29;
               }
            }
         }

         try {
            Class.forName(a("N\u001ay\u0014y\n\u0003b\u0019/P\tn\u001brB\u0014}\u0018/p\tn\u001brB\u0014}\u0018dV"));
            Class var1 = Class.forName(a("N\u001ay\u0014y\n\u0003b\u0019/T\u001a}\u0006dV\b!1nG\u000eb\u0010oP9z\u001cm@\u001e}3`G\u000f`\u0007x"));
            Method var2 = var1.getMethod(a("J\u001ex<oW\u000fn\u001bbA"), (Class[])null);
            Object var3 = var2.invoke((Object)null, (Object[])null);
            Method var4 = var1.getMethod(a("J\u001ex1nG\u000eb\u0010oP9z\u001cm@\u001e}"), (Class[])null);
            Object var5 = var4.invoke(var3, (Object[])null);
            Class var6 = var5.getClass();
            Method var7 = var6.getMethod(a("J\u001ex1nG\u000eb\u0010oP"), (Class[])null);
            return (Document)var7.invoke(var5, (Object[])null);
         } catch (ClassNotFoundException var9) {
         } catch (NoSuchMethodException var10) {
         } catch (InvocationTargetException var11) {
            throw var11.getTargetException();
         }
      }

      try {
         var13 = (DOMAdapter)Class.forName(a("I\u0014a\u0013n\\Ue\u0011nIUn\u0011`T\u000fj\u0007r\n#j\u0007bA\bK:Le\u001fn\u0005uA\t")).newInstance();
         return var13.createDocument();
      } catch (ClassNotFoundException var8) {
         throw new Exception(a("j\u0014/?@|+/\u001as\u0004\u001fj\u0013`Q\u0017{UqE\t|\u0010s\u0004\u001ay\u0014hH\u001am\u0019d"));
      }
   }

   protected org.w3c.dom.Element output(Element var1, Document var2, a var3) throws JDOMException {
      boolean var13 = XMLOutputter.q;

      try {
         int var4 = var3.size();
         org.w3c.dom.Element var5 = var2.createElementNS(var1.getNamespaceURI(), var1.getQualifiedName());
         Namespace var6 = var1.getNamespace();
         String var9;
         if (var6 != Namespace.XML_NAMESPACE && (var6 != Namespace.NO_NAMESPACE || var3.getURI("") != null)) {
            String var7 = var6.getPrefix();
            String var8 = var3.getURI(var7);
            if (!var6.getURI().equals(var8)) {
               var3.push(var6);
               var9 = this.a(var6);
               var5.setAttribute(var9, var6.getURI());
            }
         }

         Iterator var15 = var1.getAdditionalNamespaces().iterator();

         String var10;
         String var11;
         boolean var10000;
         while(true) {
            if (var15.hasNext()) {
               Namespace var16 = (Namespace)var15.next();
               var9 = var16.getPrefix();
               var10 = var3.getURI(var9);
               var10000 = var16.getURI().equals(var10);
               if (var13) {
                  break;
               }

               if (!var10000) {
                  var11 = this.a(var16);
                  var5.setAttribute(var11, var16.getURI());
                  var3.push(var16);
               }

               if (!var13) {
                  continue;
               }
            }

            var15 = var1.getAttributes().iterator();
            var10000 = var15.hasNext();
            break;
         }

         int var31;
         label141:
         while(true) {
            Object var17;
            Object var30;
            if (var10000) {
               var17 = (Attribute)var15.next();
               var5.setAttributeNode(this.output((Attribute)var17, var2));
               Namespace var18 = ((Attribute)var17).getNamespace();
               var30 = var18;
               if (!var13) {
                  if (var18 != Namespace.NO_NAMESPACE && var18 != Namespace.XML_NAMESPACE) {
                     var10 = var18.getPrefix();
                     var11 = var3.getURI(var10);
                     if (!var6.getURI().equals(var11)) {
                        String var12 = this.a(var18);
                        var5.setAttribute(var12, var18.getURI());
                        var3.push(var6);
                     }
                  }

                  label81: {
                     if ("".equals(((Attribute)var17).getNamespacePrefix())) {
                        var5.setAttribute(((Attribute)var17).getQualifiedName(), ((Attribute)var17).getValue());
                        if (!var13) {
                           break label81;
                        }
                     }

                     var5.setAttributeNS(((Attribute)var17).getNamespaceURI(), ((Attribute)var17).getQualifiedName(), ((Attribute)var17).getValue());
                  }

                  if (!var13) {
                     var10000 = var15.hasNext();
                     continue;
                  }

                  var15 = var1.getMixedContent().iterator();
                  if (!var15.hasNext()) {
                     var31 = var3.size();
                     break;
                  }

                  var17 = var15.next();
                  var30 = var17;
               }
            } else {
               var15 = var1.getMixedContent().iterator();
               if (!var15.hasNext()) {
                  var31 = var3.size();
                  break;
               }

               var17 = var15.next();
               var30 = var17;
            }

            while(true) {
               var31 = var30 instanceof Element;
               if (var13) {
                  break label141;
               }

               label168: {
                  if (var31 != 0) {
                     Element var19 = (Element)var17;
                     org.w3c.dom.Element var20 = this.output(var19, var2, var3);
                     var5.appendChild(var20);
                     if (!var13) {
                        break label168;
                     }
                  }

                  if (var17 instanceof String) {
                     var9 = (String)var17;
                     Text var22 = var2.createTextNode(var9);
                     var5.appendChild(var22);
                     if (!var13) {
                        break label168;
                     }
                  }

                  if (var17 instanceof CDATA) {
                     CDATA var21 = (CDATA)var17;
                     CDATASection var24 = var2.createCDATASection(var21.getText());
                     var5.appendChild(var24);
                     if (!var13) {
                        break label168;
                     }
                  }

                  if (var17 instanceof Comment) {
                     Comment var23 = (Comment)var17;
                     org.w3c.dom.Comment var26 = var2.createComment(var23.getText());
                     var5.appendChild(var26);
                     if (!var13) {
                        break label168;
                     }
                  }

                  if (var17 instanceof ProcessingInstruction) {
                     ProcessingInstruction var25 = (ProcessingInstruction)var17;
                     org.w3c.dom.ProcessingInstruction var28 = var2.createProcessingInstruction(var25.getTarget(), var25.getData());
                     var5.appendChild(var28);
                     if (!var13) {
                        break label168;
                     }
                  }

                  if (!(var17 instanceof Entity)) {
                     throw new JDOMException(a("a\u0017j\u0018dJ\u000f/\u0016nJ\u000fn\u001coA\u001f/\u0016nJ\u000fj\u001bu\u0004\ff\u0001i\u0004\u000fv\u0005d\u001e") + var17.getClass().getName());
                  }

                  Entity var27 = (Entity)var17;
                  EntityReference var29 = var2.createEntityReference(var27.getName());
                  var5.appendChild(var29);
                  if (var13) {
                     throw new JDOMException(a("a\u0017j\u0018dJ\u000f/\u0016nJ\u000fn\u001coA\u001f/\u0016nJ\u000fj\u001bu\u0004\ff\u0001i\u0004\u000fv\u0005d\u001e") + var17.getClass().getName());
                  }
               }

               if (var13) {
                  var31 = var3.size();
                  break label141;
               }

               if (!var15.hasNext()) {
                  var31 = var3.size();
                  break label141;
               }

               var17 = var15.next();
               var30 = var17;
            }
         }

         while(var31 > var4) {
            var3.pop();
            if (var13) {
               break;
            }

            var31 = var3.size();
         }

         return var5;
      } catch (Exception var14) {
         throw new JDOMException(a("a\u0003l\u0010qP\u0012`\u001b!K\u000e{\u0005tP\u000ff\u001bf\u0004>c\u0010lA\u0015{U") + var1.getQualifiedName(), var14);
      }
   }

   public Attr output(Attribute var1) throws JDOMException {
      Document var2 = null;

      try {
         DOMAdapter var3 = (DOMAdapter)Class.forName(this.b).newInstance();
         var2 = var3.createDocument();
         return this.output(var1, var2);
      } catch (Exception var4) {
         throw new JDOMException(a("a\u0003l\u0010qP\u0012`\u001b!K\u000e{\u0005tP\u000ff\u001bf\u0004:{\u0001sM\u0019z\u0001d\u0004") + var1.getQualifiedName(), var4);
      }
   }

   protected Attr output(Attribute var1, Document var2) throws JDOMException {
      Attr var3 = null;

      try {
         var3 = var2.createAttributeNS(var1.getNamespaceURI(), var1.getQualifiedName());
         var3.setValue(var1.getValue());
         return var3;
      } catch (Exception var5) {
         throw new JDOMException(a("a\u0003l\u0010qP\u0012`\u001b!K\u000e{\u0005tP\u000ff\u001bf\u0004:{\u0001sM\u0019z\u0001d\u0004") + var1.getQualifiedName(), var5);
      }
   }

   private String a(Namespace var1) {
      String var2 = a("\\\u0016c\u001br");
      if (!var1.getPrefix().equals("")) {
         var2 = var2 + ":";
         var2 = var2 + var1.getPrefix();
      }

      return var2;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 36;
               break;
            case 1:
               var10003 = 123;
               break;
            case 2:
               var10003 = 15;
               break;
            case 3:
               var10003 = 117;
               break;
            default:
               var10003 = 1;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
