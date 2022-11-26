package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.apache.xerces.parsers.DOMParser;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.Decimal;
import weblogic.wtc.jatmi.TypedBuffer;
import weblogic.wtc.jatmi.TypedView;
import weblogic.wtc.jatmi.TypedView32;
import weblogic.wtc.jatmi.TypedXCType;
import weblogic.wtc.jatmi.TypedXCommon;

public final class XmlViewCnv {
   private static final String OPEN_BRACKET = "<";
   private static final String CLOSE_BRACKET = ">";
   private static final String OPEN_CLOSING_BRACKET = "</";
   private static final String OPEN_VIEW_TAG = "<VIEW>";
   private static final String CLOSE_VIEW_TAG = "</VIEW>";
   private static final String OPEN_VIEW32_TAG = "<VIEW32>";
   private static final String CLOSE_VIEW32_TAG = "</VIEW32>";
   private static final String OPEN_XCOMMON_TAG = "<X_COMMON>";
   private static final String CLOSE_XCOMMON_TAG = "</X_COMMON>";
   private static final String OPEN_XCTYPE_TAG = "<X_C_TYPE>";
   private static final String CLOSE_XCTYPE_TAG = "</X_C_TYPE>";

   public static String ViewToXML(TypedBuffer view, Class viewTable, boolean rootNeeded) {
      TuxClassView classView = new TuxClassView(viewTable);
      StringBuffer sb = new StringBuffer(4096);
      if (rootNeeded) {
         if (view instanceof TypedView32) {
            sb.append("<VIEW32>");
         } else if (view instanceof TypedView) {
            sb.append("<VIEW>");
         } else if (view instanceof TypedXCommon) {
            sb.append("<X_COMMON>");
         } else if (view instanceof TypedXCType) {
            sb.append("<X_C_TYPE>");
         }

         sb.append("<");
         sb.append(view.getSubtype());
         sb.append(">");
      }

      String name = null;
      Iterator iter = classView.getKeySet().iterator();

      label90:
      while(iter.hasNext()) {
         name = (String)iter.next();

         try {
            Class[] formalParams = null;
            Method m;
            if (!classView.getIsArray(name)) {
               m = ViewAccessors.getAccessor(view.getClass(), name, new Class[0]);
               if (m != null) {
                  if (classView.getType(name) == 6) {
                     byte[] b = (byte[])((byte[])m.invoke(view));
                     appendViewField(sb, name, b);
                  } else if (classView.getType(name) == 11) {
                     TypedView32 v32 = (TypedView32)m.invoke(view);
                     String xdoc = ViewToXML(v32, v32.getClass(), true);
                     sb.append("<");
                     sb.append(name);
                     sb.append(">");
                     sb.append(xdoc);
                     sb.append("</");
                     sb.append(name);
                     sb.append(">");
                  } else {
                     Object o = m.invoke(view);
                     appendViewField(sb, name, o);
                  }
               }
            } else {
               formalParams = new Class[]{Integer.TYPE};
               m = ViewAccessors.getAccessor(view.getClass(), name, formalParams);
               if (m != null) {
                  int i = 0;
                  Object[] actualParams = new Object[1];

                  while(true) {
                     while(true) {
                        try {
                           if (classView.getType(name) == 6) {
                              actualParams[0] = new Integer(i++);
                              byte[] b = (byte[])((byte[])m.invoke(view, actualParams));
                              appendViewField(sb, name, b);
                           } else if (classView.getType(name) == 11) {
                              actualParams[0] = new Integer(i++);
                              TypedView32 v32 = (TypedView32)m.invoke(view, actualParams);
                              String xdoc = ViewToXML(v32, v32.getClass(), true);
                              sb.append("<");
                              sb.append(name);
                              sb.append(">");
                              sb.append(xdoc);
                              sb.append("</");
                              sb.append(name);
                              sb.append(">");
                           } else {
                              actualParams[0] = new Integer(i++);
                              Object o = m.invoke(view, actualParams);
                              appendViewField(sb, name, o);
                           }
                        } catch (InvocationTargetException var13) {
                           Throwable t = var13.getTargetException();
                           if (t instanceof ArrayIndexOutOfBoundsException) {
                              continue label90;
                           }

                           WTCLogger.logViewToXMLException(view.getSubtype(), (Exception)t);
                        }
                     }
                  }
               }
            }
         } catch (Exception var14) {
            WTCLogger.logViewToXMLException(view.getSubtype(), var14);
         }
      }

      if (rootNeeded) {
         sb.append("</");
         sb.append(view.getSubtype());
         sb.append(">");
         if (view instanceof TypedView32) {
            sb.append("</VIEW32>");
         } else if (view instanceof TypedView) {
            sb.append("</VIEW>");
         } else if (view instanceof TypedXCommon) {
            sb.append("</X_COMMON>");
         } else if (view instanceof TypedXCType) {
            sb.append("</X_C_TYPE>");
         }
      }

      return sb.toString();
   }

   private static void appendViewField(StringBuffer sb, String name, byte[] value) {
      sb.append("<");
      sb.append(name);
      sb.append(">");
      sb.append((new BASE64Encoder()).encodeBuffer((byte[])value));
      sb.append("</");
      sb.append(name);
      sb.append(">");
   }

   private static void appendViewField(StringBuffer sb, String name, Object value) {
      sb.append("<");
      sb.append(name);
      sb.append(">");
      sb.append(XmlFmlCnv.normalize(value.toString().trim()));
      sb.append("</");
      sb.append(name);
      sb.append(">");
   }

   public static TypedBuffer XMLToView(String xmlDoc, Class viewTable, String subType) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      TuxClassView classView = new TuxClassView(viewTable);
      Element docRoot = getXmlRoot(xmlDoc, subType);
      if (docRoot == null) {
         if (traceEnabled) {
            ntrace.doTrace("/XmlViewCnv/XMLToView - could not find document root for " + subType);
         }

         return null;
      } else {
         return (TypedBuffer)traverseNodes(docRoot, classView);
      }
   }

   private static Element getXmlRoot(String xmlStr, String bufferId) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      Document doc = null;

      try {
         DOMParser parser = new DOMParser();
         parser.parse(new InputSource(new StringReader(xmlStr)));
         doc = parser.getDocument();
         parser = null;
      } catch (SAXException var6) {
         if (traceEnabled) {
            ntrace.doTrace("TuxedoUtil.getXmlRoot - Cannot parse XML document - " + var6.getMessage());
         }

         return null;
      } catch (IOException var7) {
         if (traceEnabled) {
            ntrace.doTrace("TuxedoUtil.getXmlRoot - Cannot convert XML string to InputSource - " + var7.getMessage());
         }

         return null;
      } catch (Exception var8) {
         if (traceEnabled) {
            ntrace.doTrace("TuxedoUtil.getXmlRoot - Unknown Exception - " + var8.getMessage());
         }

         return null;
      }

      if (doc == null) {
         if (traceEnabled) {
            ntrace.doTrace("TuxedoUtil.getXmlRoot - Null document from Parser: " + xmlStr);
         }

         return null;
      } else {
         Element rootElem = (Element)doc.getElementsByTagName(bufferId).item(0);
         if (rootElem == null) {
            if (traceEnabled) {
               ntrace.doTrace("TuxedoUtil.getXmlRoot - Document does not contain root element: " + xmlStr);
            }

            return null;
         } else {
            String rtag = rootElem.getTagName();
            if (rtag == null) {
               if (traceEnabled) {
                  ntrace.doTrace("TuxedoUtil.getXmlRoot - Root element is null: " + xmlStr);
               }

               return null;
            } else {
               return rootElem;
            }
         }
      }
   }

   public static Object traverseNodes(Element root, TuxClassView classView) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      Object viewInstance = null;

      try {
         viewInstance = classView.getInternalClass().newInstance();
      } catch (InstantiationException var20) {
         if (traceEnabled) {
            ntrace.doTrace("TuxedoUtil.traverseNodes - Error instantiating " + classView.getInternalClass().getName() + " " + var20.getMessage());
         }

         throw new RuntimeException(var20);
      } catch (IllegalAccessException var21) {
         if (traceEnabled) {
            ntrace.doTrace("TuxedoUtil.traverseNodes - Error Accessing " + classView.getInternalClass().getName() + " " + var21.getMessage());
         }

         throw new RuntimeException(var21);
      }

      HashMap indexMap = new HashMap(64);

      for(Node lg = root.getFirstChild(); lg != null; lg = lg.getNextSibling()) {
         if (lg.getNodeType() == 1 && lg instanceof Element) {
            String xtag = ((Element)lg).getTagName();
            if (xtag == null) {
               if (traceEnabled) {
                  ntrace.doTrace("TuxedoUtil.traverseNodes - Null XML ElemName " + lg.toString());
               }
            } else {
               if (xtag.indexOf(":") != -1) {
                  xtag = xtag.substring(xtag.indexOf(58) + 1);
               }

               try {
                  int typeCode = classView.getType(xtag);
                  Class[] formalParams = null;
                  Object[] actualParams = null;
                  Integer m;
                  if (classView.getIsArray(xtag)) {
                     m = (Integer)indexMap.get(xtag);
                     if (m == null) {
                        indexMap.put(xtag, new Integer(0));
                     }

                     formalParams = new Class[]{null, Integer.TYPE};
                     actualParams = new Object[2];
                     Integer index = (Integer)indexMap.get(xtag);
                     actualParams[1] = index;
                     indexMap.put(xtag, new Integer(index + 1));
                  } else {
                     formalParams = new Class[1];
                     actualParams = new Object[1];
                  }

                  double param;
                  long param;
                  short param;
                  Node n;
                  switch (typeCode) {
                     case 0:
                        param = Short.parseShort(lg.getFirstChild().getNodeValue());
                        formalParams[0] = Short.TYPE;
                        actualParams[0] = new Short(param);
                        break;
                     case 1:
                        param = Long.parseLong(lg.getFirstChild().getNodeValue());
                        formalParams[0] = Long.TYPE;
                        actualParams[0] = new Long(param);
                        break;
                     case 2:
                        char param = 0;
                        n = lg.getFirstChild();
                        if (n != null) {
                           param = n.getNodeValue().charAt(0);
                        }

                        formalParams[0] = Character.TYPE;
                        actualParams[0] = new Character(param);
                        break;
                     case 3:
                        float param = Float.parseFloat(lg.getFirstChild().getNodeValue());
                        formalParams[0] = Float.TYPE;
                        actualParams[0] = new Float(param);
                        break;
                     case 4:
                        param = Double.parseDouble(lg.getFirstChild().getNodeValue());
                        formalParams[0] = Double.TYPE;
                        actualParams[0] = new Double(param);
                        break;
                     case 5:
                        String param = lg.getFirstChild().getNodeValue();
                        formalParams[0] = param.getClass();
                        actualParams[0] = param;
                        break;
                     case 6:
                        byte[] param = new byte[0];
                        n = lg.getFirstChild();
                        if (n != null) {
                           param = (new BASE64Decoder()).decodeBuffer(n.getNodeValue());
                        }

                        formalParams[0] = param.getClass();
                        actualParams[0] = param;
                     case 7:
                     case 9:
                     case 10:
                     case 12:
                     case 13:
                     case 15:
                     case 17:
                     case 20:
                     case 21:
                     default:
                        break;
                     case 8:
                        Decimal param = new Decimal(lg.getFirstChild().getNodeValue());
                        formalParams[0] = param.getClass();
                        actualParams[0] = param;
                        break;
                     case 11:
                        Node n = lg.getFirstChild();
                        String subType = classView.getTypeName(xtag);
                        if (null != subType) {
                           String[] splitStr = subType.split("\\.");
                           String subTypeName = splitStr[splitStr.length - 1];
                           Document doc = n.getOwnerDocument();
                           if (doc == null) {
                              if (traceEnabled) {
                                 ntrace.doTrace("TuxedoUtil.traverseNodes - Null document from Parser: " + subTypeName);
                              }
                           } else {
                              int i = 0;
                              if (classView.getIsArray(xtag)) {
                                 i = (Integer)actualParams[1];
                              }

                              Element elem = (Element)doc.getElementsByTagName(subTypeName).item(i);
                              if (elem == null) {
                                 if (traceEnabled) {
                                    ntrace.doTrace("TuxedoUtil.traverseNodes - Null Element from Parser: " + subTypeName);
                                 }
                              } else {
                                 String rtag = elem.getTagName();
                                 if (rtag == null) {
                                    if (traceEnabled) {
                                       ntrace.doTrace("TuxedoUtil.traverseNodes - element is null: " + subTypeName);
                                    }
                                 } else {
                                    TypedView32 subView = (TypedView32)Class.forName(subType).newInstance();
                                    TuxClassView view = new TuxClassView(Class.forName(subType));
                                    subView = (TypedView32)traverseNodes(elem, view);
                                    formalParams[0] = subView.getClass();
                                    actualParams[0] = subView;
                                 }
                              }
                           }
                        }
                        break;
                     case 14:
                        boolean param = Boolean.parseBoolean(lg.getFirstChild().getNodeValue());
                        formalParams[0] = Boolean.TYPE;
                        actualParams[0] = new Boolean(param);
                        break;
                     case 16:
                        param = Short.parseShort(lg.getFirstChild().getNodeValue());
                        formalParams[0] = Short.TYPE;
                        actualParams[0] = new Short(param);
                        break;
                     case 18:
                        param = Long.parseLong(lg.getFirstChild().getNodeValue());
                        formalParams[0] = Long.TYPE;
                        actualParams[0] = new Long(param);
                        break;
                     case 19:
                        param = Long.parseLong(lg.getFirstChild().getNodeValue());
                        formalParams[0] = Long.TYPE;
                        actualParams[0] = new Long(param);
                        break;
                     case 22:
                        param = Double.parseDouble(lg.getFirstChild().getNodeValue());
                        formalParams[0] = Double.TYPE;
                        actualParams[0] = new Double(param);
                  }

                  m = null;

                  Method m;
                  try {
                     m = classView.getInternalClass().getMethod("set" + xtag, formalParams);
                  } catch (NoSuchMethodException var23) {
                     char[] chars = xtag.toCharArray();
                     chars[0] = Character.toUpperCase(chars[0]);
                     if (classView.getIsArray(xtag)) {
                     }

                     try {
                        m = classView.getInternalClass().getMethod("set" + new String(chars), formalParams);
                     } catch (NoSuchMethodException var22) {
                        if (traceEnabled) {
                           ntrace.doTrace("/TuxedoUtil/traverseNodes - Error: could not find setter for field " + xtag);
                        }

                        throw new RuntimeException(var22);
                     }
                  }

                  m.invoke(viewInstance, actualParams);
               } catch (Exception var24) {
                  if (traceEnabled) {
                     ntrace.doTrace("/XmlViewCnv/traverseNodes - Error in traverseNodes: " + var24);
                  }

                  throw new RuntimeException(var24);
               }
            }
         } else if (traceEnabled) {
            ntrace.doTrace("TuxedoUtil.traverseNodes - Skip non-element node " + lg.toString());
         }
      }

      return viewInstance;
   }
}
