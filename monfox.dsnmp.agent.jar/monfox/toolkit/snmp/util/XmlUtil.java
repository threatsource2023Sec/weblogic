package monfox.toolkit.snmp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import monfox.log.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlUtil {
   public static String[] DEFAULT_URL_LIST = new String[]{a("\u0014\u001ah\u0007)\f\u0003yF<M\u000epD")};
   private static final String a = "_\u0015lE~\u0010\u0019fE!\u0010\u0005zL0C\u0015rE*\u0010WgZ(^Jw]0\u0013W0\u00063\u0014\u001a1^wM\u0002mNkRT&\u0010k;>S\u0006\u0010\u0011\fqZ\"\f\u001fr\u000ed\u0015\bmZ-\f\u0003\"\u000euM]8\u0017x\u001b\u001es\u0013+\u0016\u0019o\\0C\u0000z],\f\t\"\u000e<\u000e\u00018\t-\r\tzG0^JfL7DB!\u0015<\u0010\u0001%]!\u000e\u001dsH0\u0006MrH0\u0000\u0005\"\u000ekDS#Q7\u000fW|F4\u001a@pOd\u0010\bsL'\u0017P8\u0007cLS#\u0006<\u0010\u0001%]!\u000e\u001dsH0\u0006S#\u0006<\u0010\u0001%Z0\u001a\u0001zZ,\u0006\bk\u0017";
   private static final String b = "<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0'><xsl:output method='xml' indent='yes' standalone='no' doctype-system='${doctype}'/><xsl:template match='/'><xsl:copy-of select='.'/></xsl:template></xsl:stylesheet>";
   private static FileLocator c = new FileLocator();
   private static Logger d = Logger.getInstance(a("'>Qd\u0014"), a("69Ve"), a(";\u0000s|0\n\u0001"));

   public static Element addChild(Element var0, String var1) {
      Document var2 = var0.getOwnerDocument();
      Element var3 = var2.createElement(var1);
      var0.appendChild(var3);
      return var3;
   }

   public static Element addChildNS(Element var0, String var1, String var2) {
      Document var3 = var0.getOwnerDocument();
      Element var4 = null;
      if (var1 == null) {
         var4 = var3.createElement(var2);
      } else {
         var4 = var3.createElementNS(var1, var2);
      }

      var0.appendChild(var4);
      return var4;
   }

   public static void addTextChild(Element var0, String var1, String var2) {
      Document var3 = var0.getOwnerDocument();
      Element var4 = var3.createElement(var1);
      Text var5 = var3.createTextNode(var2);
      var4.appendChild(var5);
      var0.appendChild(var4);
   }

   public static Element addTextChildNS(Element var0, String var1, String var2, String var3) {
      Document var4 = var0.getOwnerDocument();
      Element var5 = null;
      if (var1 == null) {
         var5 = var4.createElement(var2);
      } else {
         var5 = var4.createElementNS(var1, var2);
      }

      Text var6 = var4.createTextNode(var3);
      var5.appendChild(var6);
      var0.appendChild(var5);
      return var5;
   }

   public static Element insertTextChildNS(Element var0, int var1, String var2, String var3, String var4) {
      Document var5 = var0.getOwnerDocument();
      Element var6 = null;
      if (var2 == null) {
         var6 = var5.createElement(var3);
      } else {
         var6 = var5.createElementNS(var2, var3);
      }

      Text var7 = var5.createTextNode(var4);
      var6.appendChild(var7);
      Element var8 = null;

      try {
         int var9 = -1;
         NodeList var10 = var0.getChildNodes();

         for(int var11 = 0; var11 < var10.getLength(); ++var11) {
            Node var12 = var10.item(var11);
            if (var12 instanceof Element) {
               ++var9;
               if (var9 == var1) {
                  var8 = (Element)var12;
                  break;
               }
            }
         }
      } catch (Exception var13) {
      }

      if (var8 != null) {
         var0.insertBefore(var6, var8);
         if (WorkItem.d == 0) {
            return var6;
         }
      }

      var0.appendChild(var6);
      return var6;
   }

   public static Element addBooleanChild(Element var0, String var1, boolean var2) {
      return addBooleanChildNS(var0, (String)null, var1, var2);
   }

   public static Element addBooleanChildNS(Element var0, String var1, String var2, boolean var3) {
      Document var4 = var0.getOwnerDocument();
      Element var5 = null;
      if (var1 == null) {
         var5 = var4.createElement(var2);
      } else {
         var5 = var4.createElementNS(var1, var2);
      }

      Text var6 = var4.createTextNode(var3 ? "1" : "0");
      var5.appendChild(var6);
      var0.appendChild(var5);
      return var5;
   }

   public static void addText(Element var0, String var1) {
      Document var2 = var0.getOwnerDocument();
      Text var3 = var2.createTextNode(var1);
      var0.appendChild(var3);
   }

   public static String getAttribute(Element var0, String var1, String var2) {
      if (var0 == null) {
         return var2;
      } else {
         String var3 = var0.getAttribute(var1);
         return var3 == null ? var2 : var3;
      }
   }

   public static int getIntAttribute(Element var0, String var1, int var2) throws NumberFormatException {
      String var3 = var0.getAttribute(var1);
      if (var3 == null) {
         return var2;
      } else {
         return var3.trim().length() == 0 ? var2 : Integer.parseInt(var3);
      }
   }

   public static boolean getBooleanAttribute(Element var0, String var1, boolean var2) throws NumberFormatException {
      String var3 = var0.getAttribute(var1);
      if (var3 == null) {
         return var2;
      } else if (var3.trim().equalsIgnoreCase(a("\u0017\u001fjL"))) {
         return true;
      } else {
         return var3.trim().equalsIgnoreCase(a("\u0005\fsZ!")) ? false : var2;
      }
   }

   public static String getText(Element var0, String var1) {
      int var5 = WorkItem.d;
      Node var2 = var0.getFirstChild();
      if (var2 != null && var2.getNodeValue() != null) {
         StringBuffer var3 = new StringBuffer();

         String var10000;
         while(true) {
            if (var2 != null) {
               String var4 = var2.getNodeValue();
               var10000 = var4;
               if (var5 != 0) {
                  break;
               }

               if (var4 != null) {
                  var3.append(var4);
               }

               var2 = var2.getNextSibling();
               if (var5 == 0) {
                  continue;
               }
            }

            var10000 = var3.toString();
            break;
         }

         return var10000;
      } else {
         return var1;
      }
   }

   public static String getChildAttribute(Element var0, String var1, String var2, String var3) {
      Element var4 = getChild(var0, var1);
      if (var4 == null) {
         return var3;
      } else {
         String var5 = var4.getAttribute(var2);
         return var5 == null ? var3 : var5;
      }
   }

   public static String getChildText(Element var0, String var1) {
      int var6 = WorkItem.d;
      Element var2 = getChild(var0, var1);
      if (var2 == null) {
         return null;
      } else {
         Node var3 = var2.getFirstChild();
         if (var3 == null) {
            return "";
         } else {
            StringBuffer var4 = new StringBuffer();

            String var10000;
            while(true) {
               if (var3 != null) {
                  String var5 = var3.getNodeValue();
                  var10000 = var5;
                  if (var6 != 0) {
                     break;
                  }

                  if (var5 != null) {
                     var4.append(var5);
                  }

                  var3 = var3.getNextSibling();
                  if (var6 == 0) {
                     continue;
                  }
               }

               var10000 = var4.toString();
               break;
            }

            return var10000;
         }
      }
   }

   public static String getChildText(Element var0, String var1, String var2) {
      int var7 = WorkItem.d;
      Element var3 = getChild(var0, var1);
      if (var3 == null) {
         return var2;
      } else {
         Node var4 = var3.getFirstChild();
         if (var4 == null) {
            return var2;
         } else {
            String var5 = var4.getNodeValue();
            if (var5 == null) {
               return var2;
            } else {
               StringBuffer var6 = new StringBuffer();

               String var10000;
               while(true) {
                  if (var4 != null) {
                     var5 = var4.getNodeValue();
                     var10000 = var5;
                     if (var7 != 0) {
                        break;
                     }

                     if (var5 != null) {
                        var6.append(var5);
                     }

                     var4 = var4.getNextSibling();
                     if (var7 == 0) {
                        continue;
                     }
                  }

                  var10000 = var6.toString();
                  break;
               }

               return var10000;
            }
         }
      }
   }

   public static Element removeChild(Element var0, String var1) {
      Element var2 = getChild(var0, var1);
      if (var2 != null) {
         var0.removeChild(var2);
      }

      return var2;
   }

   public static Element getChild(Element var0, String var1) {
      int var8 = WorkItem.d;
      if (var0 == null) {
         return null;
      } else if (var1 == null) {
         return null;
      } else {
         Element var2 = null;
         StringTokenizer var3 = new StringTokenizer(var1, a("LC"), false);

         Element var10000;
         while(true) {
            if (var3.hasMoreTokens()) {
               var10000 = var0;
               if (var8 != 0) {
                  break;
               }

               if (var0 != null) {
                  String var4 = var3.nextToken();
                  String var5 = ":" + var4;
                  var2 = null;
                  Node var9 = var0.getFirstChild();

                  label51:
                  while(true) {
                     Node var6 = var9;

                     while(true) {
                        if (var6 == null) {
                           break label51;
                        }

                        var9 = var6;
                        if (var8 != 0) {
                           break;
                        }

                        if (var6 instanceof Element) {
                           String var7 = var6.getNodeName();
                           if (var4 != null && var7 != null && (var4.equals(var7) || var4.equals(var6.getLocalName()) || var7.endsWith(var5))) {
                              var2 = (Element)var6;
                              break label51;
                           }
                        }

                        var6 = var6.getNextSibling();
                     }
                  }

                  var0 = var2;
                  if (var8 == 0) {
                     continue;
                  }
               }
            }

            var10000 = var2;
            break;
         }

         return var10000;
      }
   }

   public static List getChildren(Element var0) {
      return getChildren(var0, (String)null);
   }

   public static List getChildren(Element var0, String var1) {
      Vector var2 = new Vector();
      String var3 = ":" + var1;
      Node var4 = var0.getFirstChild();

      while(var4 != null) {
         if (var4 instanceof Element) {
            String var5 = var4.getNodeName();
            if (var1 == null || var5 != null && (var1.equals(var4.getLocalName()) || var1.equals(var5) || var3.endsWith(var5))) {
               var2.add(var4);
            }
         }

         var4 = var4.getNextSibling();
         if (WorkItem.d != 0) {
            break;
         }
      }

      return var2;
   }

   public static String getChildEnum(Element var0, String var1) throws XmlException {
      Element var2 = getChild(var0, var1);
      if (var2 == null) {
         return null;
      } else {
         Element var3 = null;

         for(Node var4 = var2.getFirstChild(); var4 != null; var4 = var4.getNextSibling()) {
            if (var4 instanceof Element) {
               var3 = (Element)var4;
               break;
            }
         }

         return var3 == null ? null : var3.getNodeName();
      }
   }

   public static String getChildEnum(Element var0, String var1, String var2) throws XmlException {
      String var3 = getChildEnum(var0, var1);
      return var3 == null ? var2 : var3;
   }

   public static Long getChildLong(Element var0, String var1) throws XmlException {
      Element var2 = getChild(var0, var1);
      if (var2 == null) {
         return null;
      } else {
         Node var3 = var2.getFirstChild();
         if (var3 == null) {
            return null;
         } else {
            String var4 = var3.getNodeValue();
            if (var4 == null) {
               return null;
            } else {
               var4 = var4.trim();
               if (var4.length() == 0) {
                  return null;
               } else {
                  try {
                     return new Long(Long.parseLong(var4));
                  } catch (NumberFormatException var6) {
                     throw new XmlException(a("\n\u0003iH(\n\t?@*\u0017MiH(\u0016\b?\u000e") + var4 + a("DMyF6C\u001d~[%\u000e\bkL6CJ") + var1 + "'");
                  }
               }
            }
         }
      }
   }

   public static Integer getChildInteger(Element var0, String var1) throws XmlException {
      Element var2 = getChild(var0, var1);
      if (var2 == null) {
         return null;
      } else {
         Node var3 = var2.getFirstChild();
         if (var3 == null) {
            return null;
         } else {
            String var4 = var3.getNodeValue();
            if (var4 == null) {
               return null;
            } else {
               var4 = var4.trim();
               if (var4.length() == 0) {
                  return null;
               } else {
                  try {
                     return new Integer(Integer.parseInt(var4));
                  } catch (NumberFormatException var6) {
                     throw new XmlException(a("\n\u0003iH(\n\t?@*\u0017MiH(\u0016\b?\u000e") + var4 + a("DMyF6C\u001d~[%\u000e\bkL6CJ") + var1 + "'");
                  }
               }
            }
         }
      }
   }

   public static boolean getChildBool(Element var0, String var1, boolean var2) throws XmlException {
      Boolean var3 = getChildBoolean(var0, var1);
      return var3 == null ? var2 : var3;
   }

   public static Boolean getChildBoolean(Element var0, String var1) throws XmlException {
      Element var2 = getChild(var0, var1);
      if (var2 == null) {
         return null;
      } else {
         Element var3 = getChild(var2, a("\u0017\u001fjL"));
         if (var3 != null) {
            return Boolean.TRUE;
         } else {
            Element var4 = getChild(var2, a("\u0017\u001fjL"));
            if (var4 != null) {
               return Boolean.FALSE;
            } else {
               Node var5 = var2.getFirstChild();
               if (var5 == null) {
                  return null;
               } else {
                  String var6 = var5.getNodeValue();
                  if (var6 == null) {
                     return null;
                  } else {
                     var6 = var6.trim();
                     if (var6.length() == 0) {
                        return null;
                     } else if (var6.equalsIgnoreCase(a("\u0017\u001fjL"))) {
                        return Boolean.TRUE;
                     } else if (var6.equalsIgnoreCase(a("\u0005\fsZ!"))) {
                        return Boolean.FALSE;
                     } else if (var6.equals("1")) {
                        return Boolean.TRUE;
                     } else if (var6.equals("0")) {
                        return Boolean.FALSE;
                     } else {
                        throw new XmlException(a("\n\u0003iH(\n\t?K+\f\u0001zH*C\u001b~E1\u0006MyF6YM") + var1);
                     }
                  }
               }
            }
         }
      }
   }

   public static int getChildInt(Element var0, String var1, int var2) throws XmlException {
      Integer var3 = getChildInteger(var0, var1);
      return var3 == null ? var2 : var3;
   }

   public static long getChildLong(Element var0, String var1, long var2) throws XmlException {
      Long var4 = getChildLong(var0, var1);
      return var4 == null ? var2 : var4;
   }

   public static Element loadXML(String var0) throws XmlException, IOException {
      return loadXML(var0, DEFAULT_URL_LIST);
   }

   public static Element loadXML(InputStream var0, boolean var1, boolean var2) throws XmlException, IOException {
      return loadXML(var0, a("_$qY1\u0017>k[!\u0002\u0000!"), DEFAULT_URL_LIST, var1, var2);
   }

   public static Element loadXML(String var0, boolean var1, boolean var2) throws XmlException, IOException {
      InputStream var3 = GetFileLocator().getInputStream(var0);
      return loadXML(var3, var0, DEFAULT_URL_LIST, var1, var2);
   }

   public static Element loadXML(String var0, String[] var1) throws XmlException, IOException {
      InputStream var2 = GetFileLocator().getInputStream(var0);
      return loadXML(var2, var0, var1);
   }

   public static Element loadXML(File var0) throws XmlException, IOException {
      return loadXML(var0, DEFAULT_URL_LIST);
   }

   public static Element loadXML(File var0, String[] var1) throws XmlException, IOException {
      FileInputStream var2 = new FileInputStream(var0);
      return loadXML(var2, var0.getAbsolutePath(), var1);
   }

   public static Element loadXML(InputStream var0, String var1) throws XmlException, IOException {
      return loadXML(var0, var1, DEFAULT_URL_LIST);
   }

   public static Element loadXML(InputStream var0, String var1, String[] var2) throws XmlException, IOException {
      return loadXML(var0, var1, var2, true, true);
   }

   public static Element loadXML(InputStream var0, String var1, String[] var2, boolean var3, boolean var4) throws XmlException, IOException {
      DocumentBuilderFactory var5 = DocumentBuilderFactory.newInstance();
      var5.setValidating(var3);
      if (var4) {
         var5.setNamespaceAware(true);
      }

      if (var3) {
         try {
            var5.setAttribute(a("\u000b\u0019kY~LB~Y%\u0000\u0005z\u0007+\u0011\n0Q)\u000fByL%\u0017\u0018mL7L\u001b~E-\u0007\fk@+\rBlJ,\u0006\u0000~"), Boolean.TRUE);
         } catch (Exception var14) {
            d.warn(a("; S\t7\u0000\u0005zD%C\u0003p]d\u0010\u0018oY+\u0011\u0019zM"), var14);
         }

         try {
            var5.setAttribute(a("\u000b\u0019kY~LBuH2\u0002Cl\\*M\u000epDk\u001b\u0000s\u0006.\u0002\u0015o\u00064\u0011\u0002oL6\u0017\u0004zZk\u0010\u000ewL)\u0002!~G#\u0016\fxL"), a("\u000b\u0019kY~LBh^3M\u001a,\u0007+\u0011\n0\u001btS\\0q\t/>|A!\u000e\f"));
         } catch (Exception var13) {
            d.warn(a("; S\t7\u0000\u0005zD%C\u0003p]d\u0010\u0018oY+\u0011\u0019zM"), var13);
         }
      }

      XmlErrorHandler var6 = new XmlErrorHandler(a(";\u0000s|0\n\u0001"), var1);

      try {
         DocumentBuilder var7 = var5.newDocumentBuilder();
         var7.setErrorHandler(var6);
         var7.setEntityResolver(new XmlEntityResolver(var2));
         Document var15 = var7.parse(new InputSource(var0));
         if (var6.getErrorCount() > 0) {
            throw new XmlException(var6.getMessages());
         } else {
            Element var9 = var15.getDocumentElement();
            var9.normalize();
            return var9;
         }
      } catch (SAXParseException var10) {
         String var8 = a("ig2\u0004d8\u0015rEd\u0013\fmZ!C\bgJ!\u0013\u0019vF*>M2\u0004NCM?O-\u000f\b?\tdYM") + var1 + a("iM?\t(\n\u0003z\tdCW?") + var10.getLineNumber() + a("iM?\t!\u0011\u001fp[dCW?") + var10.getMessage() + "\n";
         throw new XmlException(var8);
      } catch (SAXException var11) {
         d.error(a("\u0010\fg\t!\u001b\u000ezY0\n\u0002q"), var11);
         throw new XmlException(a("\u0010\fg\t!\u001b\u000ezY0\n\u0002q\u0013d") + var11.getMessage());
      } catch (ParserConfigurationException var12) {
         d.error(a("\u0013\fmZ!\u0011M|F*\u0005\u0004x\\6\u0002\u0019vF*C\bgJ!\u0013\u0019vF*"), var12);
         throw new XmlException(a("\u0013\fmZ!\u0011M|F*\u0005\u0004x\\6\u0002\u0019vF*C\bgJ!\u0013\u0019vF*YM") + var12.getMessage());
      }
   }

   public static void substituteText(Node var0, Map var1) {
      if (var0 != null) {
         if (var0.getNodeValue() != null) {
            String var6 = var0.getNodeValue();
            String var7 = (String)var1.get(var6);
            if (var7 != null) {
               var0.setNodeValue(var7);
            }

         } else {
            NodeList var3 = var0.getChildNodes();
            int var4 = 0;

            while(var4 < var3.getLength()) {
               Node var5 = var3.item(var4);
               substituteText(var5, var1);
               ++var4;
               if (WorkItem.d != 0) {
                  break;
               }
            }

         }
      }
   }

   public static void resolveVariables(Node var0, NameUtil var1) {
      resolveVariables(var0, var1, (List)null);
   }

   public static void resolveVariables(Node var0, NameUtil var1, List var2) {
      int var9 = WorkItem.d;
      if (var0 != null) {
         if (var0.getNodeName() == null || var2 == null || !var2.contains(var0.getNodeName())) {
            if (var0.getNodeValue() != null) {
               String var10 = var0.getNodeValue();
               String var12 = var1.resolveVars(var10);
               if (var12 != null) {
                  var0.setNodeValue(var12);
               }

            } else {
               Object var10000;
               label53: {
                  NamedNodeMap var4 = var0.getAttributes();
                  if (var4 != null) {
                     int var5 = 0;

                     while(var5 < var4.getLength()) {
                        Attr var6 = (Attr)var4.item(var5);
                        var10000 = var6;
                        if (var9 != 0) {
                           break label53;
                        }

                        String var7 = var6.getValue();
                        String var8 = var1.resolveVars(var7);
                        if (var8 != null) {
                           var6.setValue(var8);
                        }

                        ++var5;
                        if (var9 != 0) {
                           break;
                        }
                     }
                  }

                  var10000 = var0;
               }

               NodeList var11 = ((Node)var10000).getChildNodes();
               if (var11 != null) {
                  int var13 = 0;

                  while(var13 < var11.getLength()) {
                     Node var14 = var11.item(var13);
                     resolveVariables(var14, var1, var2);
                     ++var13;
                     if (var9 != 0) {
                        break;
                     }
                  }
               }

            }
         }
      }
   }

   public static void transform(Node var0, String[] var1, XmlTransformer var2) {
      int var8 = WorkItem.d;
      Node var3 = var0;
      if (var0 != null) {
         if (var2 != null) {
            if (var0.getNodeName() != null) {
               boolean var10000;
               label62: {
                  boolean var4;
                  label69: {
                     var4 = false;
                     if (var1 == null) {
                        var4 = true;
                        if (var8 == 0) {
                           break label69;
                        }
                     }

                     int var5 = 0;

                     while(var5 < var1.length) {
                        var10000 = var3.getNodeName().equals(var1[var5]);
                        if (var8 != 0) {
                           break label62;
                        }

                        if (var10000 || var3.getNodeName().endsWith(":" + var1[var5])) {
                           var4 = true;
                           if (var8 == 0) {
                              break;
                           }
                        }

                        ++var5;
                        if (var8 != 0) {
                           break;
                        }
                     }
                  }

                  var10000 = var4;
               }

               if (var10000) {
                  String var9 = null;
                  if (var3.getNodeValue() != null) {
                     var9 = var3.getNodeValue();
                  }

                  var2.transform(var3);
               }

               NodeList var10 = var0.getChildNodes();
               if (var10 != null) {
                  int var6 = 0;

                  while(var6 < var10.getLength()) {
                     Node var7 = var10.item(var6);
                     transform(var7, var1, var2);
                     ++var6;
                     if (var8 != 0) {
                        break;
                     }
                  }
               }

            }
         }
      }
   }

   public static void printXML(Document var0) throws IOException, XmlException {
      printXML(var0, (OutputStream)System.out);
   }

   public static void printXML(Document var0, String var1) throws IOException, XmlException {
      printXML(var0, (OutputStream)(new FileOutputStream(GetFileLocator().getFile(var1, false))));
   }

   public static void printXML(Document var0, OutputStream var1) throws IOException, XmlException {
      printXML(var0, var1, (String)null, false);
   }

   public static void printXML(Document var0, OutputStream var1, String var2, boolean var3) throws IOException, XmlException {
      String var4 = a("_\u0015lE~\u0010\u0019fE!\u0010\u0005zL0C\u0015rE*\u0010WgZ(^Jw]0\u0013W0\u00063\u0014\u001a1^wM\u0002mNkRT&\u0010k;>S\u0006\u0010\u0011\fqZ\"\f\u001fr\u000ed\u0015\bmZ-\f\u0003\"\u000euM]8\u0017x\u001b\u001es\u0013+\u0016\u0019o\\0C\u0000z],\f\t\"\u000e<\u000e\u00018\t-\r\tzG0^JfL7DB!\u0015<\u0010\u0001%]!\u000e\u001dsH0\u0006MrH0\u0000\u0005\"\u000ekDS#Q7\u000fW|F4\u001a@pOd\u0010\bsL'\u0017P8\u0007cLS#\u0006<\u0010\u0001%]!\u000e\u001dsH0\u0006S#\u0006<\u0010\u0001%Z0\u001a\u0001zZ,\u0006\bk\u0017");
      StreamSource var5 = new StreamSource(new StringReader(var4));
      DOMSource var6 = new DOMSource(var0);
      StreamResult var7 = new StreamResult(var1);
      TransformerFactory var8 = TransformerFactory.newInstance();

      try {
         Transformer var9;
         label21: {
            var9 = var8.newTransformer(var5);
            var9.setOutputProperty(a("\u000e\bkA+\u0007"), a("\u001b\u0000s"));
            var9.setOutputProperty(a("\n\u0003{L*\u0017"), a("\u001a\bl"));
            var9.setOutputProperty(a("\u0018\u0005k]4YB0Q)\u000fC~Y%\u0000\u0005z\u0007+\u0011\n0Q7\u000f\u0019b@*\u0007\bq]i\u0002\u0000p\\*\u0017"), "3");
            if (var3) {
               var9.setOutputProperty(a("\u0010\u0019~G \u0002\u0001pG!"), a("\u001a\bl"));
               if (WorkItem.d == 0) {
                  break label21;
               }
            }

            var9.setOutputProperty(a("\u0010\u0019~G \u0002\u0001pG!"), a("\r\u0002"));
         }

         var9.setOutputProperty(a("\u0015\bmZ-\f\u0003"), a("RC."));
         var9.setOutputProperty(a("\f\u0000v]i\u001b\u0000s\u0004 \u0006\u000esH6\u0002\u0019vF*"), a("\r\u0002"));
         if (var2 != null) {
            var9.setOutputProperty(a("\u0007\u0002|]=\u0013\b2Z=\u0010\u0019zD"), var2);
         }

         var9.transform(var6, var7);
      } catch (Exception var10) {
         d.error(a("\u0006\u0015|L4\u0017\u0004pGd\n\u0003?]6\u0002\u0003lO+\u0011\u0000vG#C)Pdd\u0017\u0002?Z0\u0011\u0004qN~C"), var10);
         throw new XmlException(a("\u0006\u0015|L4\u0017\u0004pGd\n\u0003?]6\u0002\u0003lO+\u0011\u0000vG#C)Pdd\u0017\u0002?Z0\u0011\u0004qN~C") + var10);
      }
   }

   public static void printXML(Document var0, Writer var1) throws IOException, XmlException {
      StreamSource var2 = new StreamSource(new StringReader(a("_\u0015lE~\u0010\u0019fE!\u0010\u0005zL0C\u0015rE*\u0010WgZ(^Jw]0\u0013W0\u00063\u0014\u001a1^wM\u0002mNkRT&\u0010k;>S\u0006\u0010\u0011\fqZ\"\f\u001fr\u000ed\u0015\bmZ-\f\u0003\"\u000euM]8\u0017x\u001b\u001es\u0013+\u0016\u0019o\\0C\u0000z],\f\t\"\u000e<\u000e\u00018\t-\r\tzG0^JfL7DB!\u0015<\u0010\u0001%]!\u000e\u001dsH0\u0006MrH0\u0000\u0005\"\u000ekDS#Q7\u000fW|F4\u001a@pOd\u0010\bsL'\u0017P8\u0007cLS#\u0006<\u0010\u0001%]!\u000e\u001dsH0\u0006S#\u0006<\u0010\u0001%Z0\u001a\u0001zZ,\u0006\bk\u0017")));
      DOMSource var3 = new DOMSource(var0);
      StreamResult var4 = new StreamResult(var1);
      TransformerFactory var5 = TransformerFactory.newInstance();

      try {
         Transformer var6 = var5.newTransformer(var2);
         var6.setOutputProperty(a("\u000e\bkA+\u0007"), a("\u001b\u0000s"));
         var6.setOutputProperty(a("\n\u0003{L*\u0017"), a("\u001a\bl"));
         var6.setOutputProperty(a("\u0018\u0005k]4YB0Q)\u000fC~Y%\u0000\u0005z\u0007+\u0011\n0Q7\u000f\u0019b@*\u0007\bq]i\u0002\u0000p\\*\u0017"), "3");
         var6.setOutputProperty(a("\u0015\bmZ-\f\u0003"), a("RC."));
         var6.setOutputProperty(a("\f\u0000v]i\u001b\u0000s\u0004 \u0006\u000esH6\u0002\u0019vF*"), a("\r\u0002"));
         var6.transform(var3, var4);
      } catch (Exception var7) {
         throw new XmlException(a("\u0006\u0015|L4\u0017\u0004pGd\n\u0003?]6\u0002\u0003lO+\u0011\u0000vG#C)Pdd\u0017\u0002?Z0\u0011\u0004qN~C") + var7);
      }
   }

   public static String xmlToString(Document var0) {
      try {
         StringWriter var1 = new StringWriter();
         printXML(var0, (Writer)var1);
         return var1.getBuffer().toString();
      } catch (Exception var2) {
         return null;
      }
   }

   public static FileLocator GetFileLocator() {
      return c;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 99;
               break;
            case 1:
               var10003 = 109;
               break;
            case 2:
               var10003 = 31;
               break;
            case 3:
               var10003 = 41;
               break;
            default:
               var10003 = 68;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public interface XmlTransformer {
      void transform(Node var1);
   }
}
