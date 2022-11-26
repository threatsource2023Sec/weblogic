package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.apache.xerces.parsers.DOMParser;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.FML;
import weblogic.wtc.jatmi.FViewFld;
import weblogic.wtc.jatmi.Ferror;
import weblogic.wtc.jatmi.FldTbl;
import weblogic.wtc.jatmi.FmlKey;
import weblogic.wtc.jatmi.TypedBuffer;
import weblogic.wtc.jatmi.TypedFML;
import weblogic.wtc.jatmi.TypedFML32;
import weblogic.wtc.jatmi.TypedView32;
import weblogic.wtc.jatmi.ViewHelper;

public final class XmlFmlCnv {
   private TypedFML myFML;
   private TypedFML32 myFML32;
   private static final String OPEN_BRACKET = "<";
   private static final String CLOSE_BRACKET = ">";
   private static final String CLOSE_BRACKET2 = ">\n";
   private static final String OPEN_CLOSING_BRACKET = "</";
   private static final String OPEN_FML_TAG = "<FML>";
   private static final String CLOSE_FML_TAG = "</FML>";
   private static final String OPEN_FML32_TAG = "<FML32>";
   private static final String CLOSE_FML32_TAG = "</FML32>";
   private static final String VIEW_TAG = "VIEW_";
   private static boolean convertNonPrintable = getConvertNonPrintable();
   private static boolean convertNull = getConvertNull();
   private static boolean convertNullChar = getConvertNullChar();
   private static boolean normalizeEscaped = getNormalizeEscaped();
   private static boolean trimXmlValue = getTrimXmlValue();
   private static boolean convertNullToZero = getConvertNullToZero();

   public String FMLtoXML(TypedFML inData, boolean root, boolean ordering, boolean grouping, boolean beautify) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      String xmlOutput = null;
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/FMLtoXML/");
      }

      if (!ordering) {
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/FML32toXML/10/" + xmlOutput);
         }

         return this.printRawFml(inData, root);
      } else {
         HashMap hmap = this.preprocessFML(inData, grouping);
         if (hmap != null) {
            if (grouping) {
               xmlOutput = this.printGroupXML(inData, hmap, root, beautify);
            } else {
               xmlOutput = this.printSimpleXML(inData, hmap, root, beautify);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/FMLtoXML/20/" + xmlOutput);
         }

         return xmlOutput;
      }
   }

   public String FMLtoXML(TypedFML inData, boolean root) {
      return this.FMLtoXML(inData, root, false, false, false);
   }

   public String FMLtoXML(TypedFML inData, boolean root, boolean grouping) {
      return this.FMLtoXML(inData, root, true, grouping, false);
   }

   public String FMLtoXML(TypedFML inData, boolean root, boolean ordering, boolean grouping) {
      return this.FMLtoXML(inData, root, ordering, grouping, false);
   }

   public String FMLtoXML(TypedFML inData) {
      return this.FMLtoXML(inData, true, false, false, false);
   }

   private String printRawFml(TypedFML inData, boolean rootNeeded) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/FMLtoXML/");
      }

      StringBuffer sb = new StringBuffer(4096);

      try {
         if (rootNeeded) {
            sb.append("<FML>");
         }

         Iterator fiter = inData.Fiterator();
         HashMap fNames = new HashMap();
         Integer iFid = null;

         while(true) {
            if (!fiter.hasNext()) {
               if (rootNeeded) {
                  sb.append("</FML>");
               }
               break;
            }

            Map.Entry entry = (Map.Entry)fiter.next();
            FmlKey fmlkey = (FmlKey)entry.getKey();
            int fid = fmlkey.get_fldid();
            iFid = new Integer(fid);
            String tag;
            if ((tag = (String)fNames.get(iFid)) == null) {
               tag = inData.Fname(fid);
               fNames.put(iFid, tag);
            }

            Object val_obj = entry.getValue();
            int ftype = inData.Fldtype(fid);
            switch (ftype) {
               case 0:
               case 1:
               case 3:
               case 4:
               case 5:
                  sb.append("<");
                  sb.append(tag);
                  sb.append(">");
                  sb.append(normalize(val_obj.toString()));
                  sb.append("</");
                  sb.append(tag);
                  sb.append(">");
                  break;
               case 2:
                  boolean no_value = false;
                  char ch = 0;
                  if (val_obj != null && val_obj.toString() != null && !val_obj.toString().equals("")) {
                     ch = val_obj.toString().charAt(0);
                  } else {
                     no_value = true;
                  }

                  sb.append("<");
                  sb.append(tag);
                  if (convertNonPrintable && !no_value) {
                     sb.append(" decValue=\"");
                     sb.append(ch);
                     sb.append("\"");
                  }

                  if (convertNullChar && ch == 0) {
                     sb.append(" nil=\"true\"");
                  }

                  sb.append(">");
                  if (convertNonPrintable) {
                     if (ch > ' ') {
                        sb.append(normalize(val_obj.toString()));
                     }
                  } else if (ch != 0) {
                     sb.append(normalize(val_obj.toString()));
                  }

                  sb.append("</");
                  sb.append(tag);
                  sb.append(">");
                  break;
               case 6:
                  String base64Field = (new BASE64Encoder()).encodeBuffer((byte[])((byte[])val_obj));
                  sb.append("<");
                  sb.append(tag);
                  sb.append(">");
                  sb.append(base64Field);
                  sb.append("</");
                  sb.append(tag);
                  sb.append(">");
                  break;
               default:
                  WTCLogger.logErrorBadFmlFldType(ftype);
                  if (traceEnabled) {
                     ntrace.doTrace("]/XmlFmlCnv/FMLtoXML/35/null");
                  }

                  return null;
            }

            if (traceEnabled) {
               ntrace.doTrace("Tag=" + tag + " Val=" + val_obj + " Type=" + ftype);
            }
         }
      } catch (Ferror var16) {
         WTCLogger.logFEbadFMLinData(var16.toString());
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/FMLtoXML/40/null");
         }

         return null;
      } catch (Exception var17) {
         WTCLogger.logUEbadFMLinData(var17.getMessage());
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/FMLtoXML/45/null");
         }

         return null;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/XmlFmlCnv/FMLtoXML/50/" + sb.toString());
      }

      return sb.toString();
   }

   private String FML32toXML(TypedFML32 inData, boolean root, boolean ordering, boolean grouping, boolean beautify, int depth) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      String xmlOutput = null;
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/FML32toXML/");
      }

      if (!ordering) {
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/FML32toXML/10/" + xmlOutput);
         }

         return this.printRawFml32(inData, root);
      } else {
         HashMap hmap = this.preprocessFML(inData, grouping);
         if (hmap != null) {
            if (grouping) {
               xmlOutput = this.printGroupXML32(inData, hmap, root, beautify, depth);
            } else {
               xmlOutput = this.printSimpleXML32(inData, hmap, root, beautify, depth);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/FML32toXML/20/" + xmlOutput);
         }

         return xmlOutput;
      }
   }

   public String FML32toXML(TypedFML32 inData, boolean root, boolean ordering, boolean grouping, boolean beautify) {
      return this.FML32toXML(inData, root, ordering, grouping, beautify, 0);
   }

   public String FML32toXML(TypedFML32 inData, boolean root, boolean grouping) {
      return this.FML32toXML(inData, root, true, grouping, false, 0);
   }

   public String FML32toXML(TypedFML32 inData, boolean root, boolean ordering, boolean grouping) {
      return this.FML32toXML(inData, root, ordering, grouping, false, 0);
   }

   public String FML32toXML(TypedFML32 inData) {
      return this.FML32toXML(inData, true, false, false, false, 0);
   }

   public String FML32toXML(TypedFML32 inData, boolean rootneeded) {
      return this.FML32toXML(inData, rootneeded, false, false, false, 0);
   }

   private String printRawFml32(TypedFML32 inData, boolean rootneeded) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/FML32toXML/");
      }

      StringBuffer sb = new StringBuffer(4096);

      try {
         if (rootneeded) {
            sb.append("<FML32>");
         }

         FldTbl[] pflds = inData.getFieldTables();
         if (pflds == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/XmlFmlCnv/FML32toXML/15/Need FldTbls for Fname");
            }

            return null;
         }

         Iterator fiter = inData.Fiterator();
         HashMap fNames = new HashMap();
         Integer iFid32 = null;

         while(true) {
            if (!fiter.hasNext()) {
               if (rootneeded) {
                  sb.append("</FML32>");
               }
               break;
            }

            Map.Entry entry = (Map.Entry)fiter.next();
            FmlKey fmlkey = (FmlKey)entry.getKey();
            int fid32 = fmlkey.get_fldid();
            iFid32 = new Integer(fid32);
            String tag;
            if ((tag = (String)fNames.get(iFid32)) == null) {
               tag = inData.Fname(fid32);
               fNames.put(iFid32, tag);
            }

            Object val_obj = entry.getValue();
            int ftype32 = inData.Fldtype(fid32);
            String view32Field;
            String fml32Field;
            switch (ftype32) {
               case 0:
               case 1:
               case 3:
               case 4:
               case 5:
               case 7:
               case 8:
                  sb.append("<");
                  sb.append(tag);
                  sb.append(">");
                  sb.append(normalize(val_obj.toString()));
                  sb.append("</");
                  sb.append(tag);
                  sb.append(">");
                  break;
               case 2:
                  boolean no_value = false;
                  char ch = 0;
                  if (val_obj != null && val_obj.toString() != null && !val_obj.toString().equals("")) {
                     ch = val_obj.toString().charAt(0);
                  } else {
                     no_value = true;
                  }

                  sb.append("<");
                  sb.append(tag);
                  if (convertNonPrintable && !no_value) {
                     sb.append(" decValue=\"");
                     sb.append(ch);
                     sb.append("\"");
                  }

                  if (convertNullChar && ch == 0) {
                     sb.append(" nil=\"true\"");
                  }

                  sb.append(">");
                  if (convertNonPrintable) {
                     if (ch > ' ') {
                        sb.append(normalize(val_obj.toString()));
                     }
                  } else if (ch != 0) {
                     sb.append(normalize(val_obj.toString()));
                  }

                  sb.append("</");
                  sb.append(tag);
                  sb.append(">");
                  break;
               case 6:
                  view32Field = (new BASE64Encoder()).encodeBuffer((byte[])((byte[])val_obj));
                  sb.append("<");
                  sb.append(tag);
                  sb.append(">");
                  sb.append(view32Field);
                  sb.append("</");
                  sb.append(tag);
                  sb.append(">");
                  break;
               case 9:
               default:
                  WTCLogger.logErrorBadFml32FldType(ftype32);
                  if (traceEnabled) {
                     ntrace.doTrace("]/XmlFmlCnv/FML32toXML/35/null");
                  }

                  return null;
               case 10:
                  TypedFML32 fml32 = new TypedFML32((TypedFML32)val_obj);
                  if (fml32.getFieldTables() == null) {
                     if (traceEnabled) {
                        ntrace.doTrace("/XmlFmlCnv/FML32toXML/30/defaulting child FldTbls to parent FldTbls");
                     }

                     fml32.setFieldTables(pflds);
                  }

                  fml32Field = this.FML32toXML(fml32, false);
                  sb.append("<");
                  sb.append(tag);
                  sb.append(">");
                  sb.append(fml32Field);
                  sb.append("</");
                  sb.append(tag);
                  sb.append(">");
                  break;
               case 11:
                  sb.append("<");
                  sb.append(tag);
                  sb.append(">");
                  view32Field = XmlViewCnv.ViewToXML(((FViewFld)val_obj).getViewData(), ((FViewFld)val_obj).getViewData().getClass(), false);
                  fml32Field = ((FViewFld)val_obj).getViewData().getSubtype();
                  sb.append("<");
                  sb.append("VIEW_");
                  sb.append(fml32Field);
                  sb.append(">");
                  sb.append(view32Field);
                  sb.append("</");
                  sb.append("VIEW_");
                  sb.append(fml32Field);
                  sb.append(">");
                  sb.append("</");
                  sb.append(tag);
                  sb.append(">");
            }

            if (traceEnabled) {
               ntrace.doTrace("Tag=" + tag + " Val=" + val_obj + " Type=" + ftype32);
            }
         }
      } catch (Ferror var17) {
         WTCLogger.logFEbadFML32inData(var17.toString(), var17.getMessage());
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/FML32toXML/40/null");
         }

         return null;
      } catch (Exception var18) {
         WTCLogger.logUEbadFML32inData(var18.getMessage());
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/FML32toXML/45/null");
         }

         return null;
      }

      if (traceEnabled) {
         ntrace.doTrace("</XmlFmlCnv/FML32toXML/50/" + sb.toString());
      }

      return sb.toString();
   }

   private HashMap preprocessFML(FML buf, boolean grouping) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      HashMap imap = new HashMap();
      HashMap omap = null;
      if (grouping) {
         omap = new HashMap();
      }

      try {
         Iterator iter = buf.Fiterator();

         while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            FmlKey key = (FmlKey)entry.getKey();
            int id = key.get_fldid();
            int occ = key.get_occurrence();
            Integer fid = new Integer(id);
            ArrayList al;
            if ((al = (ArrayList)imap.get(fid)) == null) {
               int n = buf.Foccur(id);
               al = new ArrayList(n);
               imap.put(fid, al);

               for(int i = 0; i < n; ++i) {
                  al.add((Object)null);
               }

               if (grouping) {
                  Integer focc = new Integer(n);
                  ArrayList ol;
                  if ((ol = (ArrayList)omap.get(focc)) == null) {
                     ol = new ArrayList();
                     omap.put(focc, ol);
                  }

                  ol.add(al);
               }
            }

            try {
               al.remove(occ);
               al.add(occ, entry);
            } catch (IndexOutOfBoundsException var17) {
               var17.printStackTrace();
               if (traceEnabled) {
                  ntrace.doTrace("]/XmlFmlCnv/preprocessFML/10/Index Out Of Bound, return null");
               }

               return null;
            }
         }
      } catch (Ferror var18) {
         WTCLogger.logFEbadFMLinData(var18.toString());
         return null;
      }

      return !grouping ? imap : omap;
   }

   private String printSimpleXML(FML buf, HashMap hmap, boolean root, boolean beautify) {
      Set eSet = hmap.entrySet();
      Object[] o = eSet.toArray();
      StringBuffer sb = new StringBuffer(4096);
      String cb;
      if (beautify) {
         cb = ">\n";
      } else {
         cb = ">";
      }

      if (root) {
         sb.append("<FML>");
         if (beautify) {
            sb.append("\n");
         }
      }

      try {
         for(int i = 0; i < o.length; ++i) {
            Map.Entry ae = (Map.Entry)o[i];
            ArrayList a = (ArrayList)((ArrayList)ae.getValue());
            int n = a.size();
            String tag = null;

            for(int j = 0; j < n; ++j) {
               Map.Entry e = (Map.Entry)a.get(j);
               FmlKey k = (FmlKey)e.getKey();
               int id = k.get_fldid();
               int t = buf.Fldtype(id);
               Object value = e.getValue();
               if (tag == null) {
                  tag = buf.Fname(id);
               }

               switch (t) {
                  case 2:
                     boolean no_value = false;
                     char ch = 0;
                     if (value != null && value.toString() != null && !value.toString().equals("")) {
                        ch = value.toString().charAt(0);
                     } else {
                        no_value = true;
                     }

                     sb.append("<");
                     sb.append(tag);
                     if (convertNonPrintable && !no_value) {
                        sb.append(" decValue=\"");
                        sb.append(ch);
                        sb.append("\"");
                     }

                     if (convertNullChar && ch == 0) {
                        sb.append(" nil=\"true\"");
                     }

                     sb.append(">");
                     if (convertNonPrintable) {
                        if (ch > ' ') {
                           sb.append(normalize(value.toString()));
                        }
                     } else if (ch != 0) {
                        sb.append(normalize(value.toString()));
                     }

                     sb.append("</");
                     sb.append(tag);
                     sb.append(cb);
                     break;
                  case 5:
                     if (convertNull) {
                        sb.append("<");
                        sb.append(tag);
                        char ch;
                        if (value.toString() != null && !value.toString().equals("")) {
                           ch = value.toString().charAt(0);
                        } else {
                           ch = 0;
                        }

                        if (ch == 0) {
                           sb.append(" nil=\"true\"");
                        }

                        sb.append(">");
                        if (ch != 0) {
                           sb.append(normalize(value.toString()));
                        }

                        sb.append("</");
                        sb.append(tag);
                        sb.append(cb);
                        break;
                     }
                  case 0:
                  case 1:
                  case 3:
                  case 4:
                     sb.append("<");
                     sb.append(tag);
                     sb.append(">");
                     sb.append(normalize(value.toString()));
                     sb.append("</");
                     sb.append(tag);
                     sb.append(cb);
                     break;
                  case 6:
                     if (value == null) {
                        value = new byte[0];
                     }

                     String base64Field = (new BASE64Encoder()).encodeBuffer((byte[])((byte[])value));
                     sb.append("<");
                     sb.append(tag);
                     if (convertNull && base64Field.equals("")) {
                        sb.append(" nil=\"true\"");
                     }

                     sb.append(">");
                     sb.append(base64Field);
                     sb.append("</");
                     sb.append(tag);
                     sb.append(cb);
                     break;
                  default:
                     WTCLogger.logErrorBadFmlFldType(t);
                     return null;
               }
            }
         }
      } catch (Ferror var22) {
         WTCLogger.logFEbadFMLinData(var22.toString());
         return null;
      }

      if (root) {
         sb.append("</FML>");
         if (beautify) {
            sb.append("\n");
         }
      }

      return sb.toString();
   }

   private String printGroupXML(FML buf, HashMap hmap, boolean root, boolean beautify) {
      Set eSet = hmap.entrySet();
      Object[] o = eSet.toArray();
      StringBuffer sb = new StringBuffer(4096);
      String cb;
      if (beautify) {
         cb = ">\n";
         if (root) {
            sb.append("<FML>");
            sb.append("\n");
         }
      } else {
         cb = ">";
         if (root) {
            sb.append("<FML>");
         }
      }

      try {
         for(int i = 0; i < o.length; ++i) {
            Map.Entry ae = (Map.Entry)o[i];
            ArrayList a1 = (ArrayList)((ArrayList)ae.getValue());
            int n = a1.size();
            int c = ((ArrayList)a1.get(0)).size();
            String[] sa = new String[n];
            int[] ta = new int[n];

            int j;
            Map.Entry e;
            for(j = 0; j < n; ++j) {
               e = (Map.Entry)((ArrayList)a1.get(j)).get(0);
               FmlKey key = (FmlKey)e.getKey();
               int id = key.get_fldid();
               sa[j] = buf.Fname(id);
               ta[j] = buf.Fldtype(id);
            }

            for(j = 0; j < c; ++j) {
               for(int k = 0; k < n; ++k) {
                  e = (Map.Entry)((ArrayList)a1.get(k)).get(j);
                  Object value = e.getValue();
                  String tag = sa[k];
                  switch (ta[k]) {
                     case 2:
                        boolean no_value = false;
                        char ch = 0;
                        if (value != null && value.toString() != null && !value.toString().equals("")) {
                           ch = value.toString().charAt(0);
                        } else {
                           no_value = true;
                        }

                        sb.append("<");
                        sb.append(tag);
                        if (convertNonPrintable && !no_value) {
                           sb.append(" decValue=\"");
                           sb.append(ch);
                           sb.append("\"");
                        }

                        if (convertNullChar && ch == 0) {
                           sb.append(" nil=\"true\"");
                        }

                        sb.append(">");
                        if (convertNonPrintable) {
                           if (ch > ' ') {
                              sb.append(normalize(value.toString()));
                           }
                        } else if (ch != 0) {
                           sb.append(normalize(value.toString()));
                        }

                        sb.append("</");
                        sb.append(tag);
                        sb.append(cb);
                        break;
                     case 5:
                        if (convertNull) {
                           sb.append("<");
                           sb.append(tag);
                           char ch;
                           if (value.toString() != null && !value.toString().equals("")) {
                              ch = value.toString().charAt(0);
                           } else {
                              ch = 0;
                           }

                           if (ch == 0) {
                              sb.append(" nil=\"true\"");
                           }

                           sb.append(">");
                           if (ch != 0) {
                              sb.append(normalize(value.toString()));
                           }

                           sb.append("</");
                           sb.append(tag);
                           sb.append(cb);
                           break;
                        }
                     case 0:
                     case 1:
                     case 3:
                     case 4:
                        sb.append("<");
                        sb.append(tag);
                        sb.append(">");
                        sb.append(normalize(value.toString()));
                        sb.append("</");
                        sb.append(tag);
                        sb.append(cb);
                        break;
                     case 6:
                        if (value == null) {
                           value = new byte[0];
                        }

                        String base64Field = (new BASE64Encoder()).encodeBuffer((byte[])((byte[])value));
                        sb.append("<");
                        sb.append(tag);
                        if (convertNull && base64Field.equals("")) {
                           sb.append(" nil=\"true\"");
                        }

                        sb.append(">");
                        sb.append(base64Field);
                        sb.append("</");
                        sb.append(tag);
                        sb.append(cb);
                        break;
                     default:
                        WTCLogger.logErrorBadFmlFldType(ta[k]);
                        return null;
                  }
               }
            }
         }
      } catch (Ferror var26) {
         WTCLogger.logFEbadFMLinData(var26.toString());
         return null;
      }

      if (root) {
         sb.append("</FML>");
         if (beautify) {
            sb.append("\n");
         }
      }

      return sb.toString();
   }

   private String printSimpleXML32(FML buf, HashMap hmap, boolean root, boolean beautify, int depth) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      Set eSet = hmap.entrySet();
      Object[] o = eSet.toArray();
      int l = depth;
      StringBuffer sb = new StringBuffer(4096);
      int i;
      String cb;
      String tab;
      if (beautify) {
         if (root) {
            l = 1;
         }

         if (l <= 0) {
            tab = "";
         } else {
            StringBuffer sb2 = new StringBuffer(20);

            for(i = 0; i < l; ++i) {
               sb2.append("  ");
            }

            tab = sb2.toString();
         }

         cb = ">\n";
      } else {
         cb = ">";
         tab = "";
      }

      if (root) {
         sb.append("<FML32>");
         if (beautify) {
            sb.append("\n");
         }
      }

      try {
         FldTbl[] pflds = buf.getFieldTables();
         if (pflds == null) {
            ntrace.doTrace("]/XmlFmlCnv/printSimpleXML32/10/no field table");
            return null;
         }

         for(i = 0; i < o.length; ++i) {
            Map.Entry ae = (Map.Entry)o[i];
            ArrayList a = (ArrayList)((ArrayList)ae.getValue());
            int n = a.size();
            String tag = null;

            for(int j = 0; j < n; ++j) {
               Map.Entry e = (Map.Entry)a.get(j);
               FmlKey k = (FmlKey)e.getKey();
               int id = k.get_fldid();
               int t = buf.Fldtype(id);
               Object value = e.getValue();
               if (tag == null) {
                  tag = buf.Fname(id);
               }

               String fml32Field;
               switch (t) {
                  case 2:
                     boolean no_value = false;
                     char ch = 0;
                     if (value != null && value.toString() != null && !value.toString().equals("")) {
                        ch = value.toString().charAt(0);
                     } else {
                        no_value = true;
                     }

                     sb.append(tab);
                     sb.append("<");
                     sb.append(tag);
                     if (convertNonPrintable && !no_value) {
                        sb.append(" decValue=\"");
                        sb.append(ch);
                        sb.append("\"");
                     }

                     if (convertNullChar && ch == 0) {
                        sb.append(" nil=\"true\"");
                     }

                     sb.append(">");
                     if (convertNonPrintable) {
                        if (ch > ' ') {
                           sb.append(normalize(value.toString()));
                        }
                     } else if (ch != 0) {
                        sb.append(normalize(value.toString()));
                     }

                     sb.append("</");
                     sb.append(tag);
                     sb.append(cb);
                     break;
                  case 5:
                     if (convertNull) {
                        sb.append(tab);
                        sb.append("<");
                        sb.append(tag);
                        char ch;
                        if (value.toString() != null && !value.toString().equals("")) {
                           ch = value.toString().charAt(0);
                        } else {
                           ch = 0;
                        }

                        if (ch == 0) {
                           sb.append(" nil=\"true\"");
                        }

                        sb.append(">");
                        if (ch != 0) {
                           sb.append(normalize(value.toString()));
                        }

                        sb.append("</");
                        sb.append(tag);
                        sb.append(cb);
                        break;
                     }
                  case 0:
                  case 1:
                  case 3:
                  case 4:
                  case 7:
                  case 8:
                     sb.append(tab);
                     sb.append("<");
                     sb.append(tag);
                     sb.append(">");
                     sb.append(normalize(value.toString()));
                     sb.append("</");
                     sb.append(tag);
                     sb.append(cb);
                     break;
                  case 6:
                     if (value == null) {
                        value = new byte[0];
                     }

                     String base64Field = (new BASE64Encoder()).encodeBuffer((byte[])((byte[])value));
                     sb.append("<");
                     sb.append(tag);
                     if (convertNull && base64Field.equals("")) {
                        sb.append(" nil=\"true\"");
                     }

                     sb.append(">");
                     sb.append(base64Field);
                     sb.append("</");
                     sb.append(tag);
                     sb.append(cb);
                     break;
                  case 9:
                  default:
                     WTCLogger.logErrorBadFmlFldType(t);
                     return null;
                  case 10:
                     TypedFML32 fml32 = new TypedFML32((TypedFML32)value);
                     if (fml32.getFieldTables() == null) {
                        if (traceEnabled) {
                           ntrace.doTrace("/XmlFmlCnv/printSimpleXML32/child FldTbls to parent FldTbls");
                        }

                        fml32.setFieldTables(pflds);
                     }

                     fml32Field = this.FML32toXML(fml32, false, true, false, beautify, l + 1);
                     sb.append(tab);
                     sb.append("<");
                     sb.append(tag);
                     if (convertNull && fml32Field.equals("")) {
                        sb.append(" nil=\"true\"");
                     }

                     sb.append(cb);
                     sb.append(fml32Field);
                     sb.append(tab);
                     sb.append("</");
                     sb.append(tag);
                     sb.append(cb);
                     break;
                  case 11:
                     sb.append(tab);
                     sb.append("<");
                     sb.append(tag);
                     sb.append(cb);
                     String view32Field = XmlViewCnv.ViewToXML(((FViewFld)value).getViewData(), ((FViewFld)value).getViewData().getClass(), false);
                     fml32Field = ((FViewFld)value).getViewData().getSubtype();
                     sb.append(tab);
                     sb.append("<");
                     sb.append("VIEW_");
                     sb.append(fml32Field);
                     sb.append(cb);
                     sb.append(view32Field);
                     sb.append(tab);
                     sb.append("</");
                     sb.append("VIEW_");
                     sb.append(fml32Field);
                     sb.append(cb);
                     sb.append(tab);
                     sb.append("</");
                     sb.append(tag);
                     sb.append(cb);
               }
            }
         }
      } catch (Ferror var28) {
         WTCLogger.logFEbadFMLinData(var28.toString());
         return null;
      }

      if (root) {
         sb.append("</FML32>");
         if (beautify) {
            sb.append("\n");
         }
      }

      return sb.toString();
   }

   private String printGroupXML32(FML buf, HashMap hmap, boolean root, boolean beautify, int depth) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      Set eSet = hmap.entrySet();
      Object[] o = eSet.toArray();
      int l = depth;
      StringBuffer sb = new StringBuffer(4096);
      int i;
      String cb;
      String tab;
      if (beautify) {
         cb = ">\n";
         if (root) {
            l = 1;
         }

         if (l <= 0) {
            tab = "";
         } else {
            StringBuffer sb2 = new StringBuffer(2 * depth + 1);

            for(i = 0; i < l; ++i) {
               sb2.append("  ");
            }

            tab = sb2.toString();
         }

         if (root) {
            sb.append("<FML32>");
            sb.append("\n");
         }
      } else {
         cb = ">";
         tab = "";
         if (root) {
            sb.append("<FML32>");
         }
      }

      try {
         FldTbl[] pflds = buf.getFieldTables();
         if (pflds == null) {
            ntrace.doTrace("]/XmlFmlCnv/printGroupXML32/10/no field table");
            return null;
         }

         for(i = 0; i < o.length; ++i) {
            Map.Entry ae = (Map.Entry)o[i];
            ArrayList a1 = (ArrayList)((ArrayList)ae.getValue());
            int n = a1.size();
            int c = ((ArrayList)a1.get(0)).size();
            String[] sa = new String[n];
            int[] ta = new int[n];

            int j;
            Map.Entry e;
            for(j = 0; j < n; ++j) {
               e = (Map.Entry)((ArrayList)a1.get(j)).get(0);
               FmlKey key = (FmlKey)e.getKey();
               int id = key.get_fldid();
               sa[j] = buf.Fname(id);
               ta[j] = buf.Fldtype(id);
            }

            for(j = 0; j < c; ++j) {
               for(int k = 0; k < n; ++k) {
                  e = (Map.Entry)((ArrayList)a1.get(k)).get(j);
                  Object value = e.getValue();
                  String tag = sa[k];
                  String fml32Field;
                  switch (ta[k]) {
                     case 2:
                        boolean no_value = false;
                        char ch = 0;
                        if (value != null && value.toString() != null && !value.toString().equals("")) {
                           ch = value.toString().charAt(0);
                        } else {
                           no_value = true;
                        }

                        sb.append(tab);
                        sb.append("<");
                        sb.append(tag);
                        if (convertNonPrintable && !no_value) {
                           sb.append(" decValue=\"");
                           sb.append(ch);
                           sb.append("\"");
                        }

                        if (convertNullChar && ch == 0) {
                           sb.append(" nil=\"true\"");
                        }

                        sb.append(">");
                        if (convertNonPrintable) {
                           if (ch > ' ') {
                              sb.append(normalize(value.toString()));
                           }
                        } else if (ch != 0) {
                           sb.append(normalize(value.toString()));
                        }

                        sb.append("</");
                        sb.append(tag);
                        sb.append(cb);
                        break;
                     case 5:
                        if (convertNull) {
                           sb.append(tab + "<");
                           sb.append(tag);
                           char ch;
                           if (value.toString() != null && !value.toString().equals("")) {
                              ch = value.toString().charAt(0);
                           } else {
                              ch = 0;
                           }

                           if (ch == 0) {
                              sb.append(" nil=\"true\"");
                           }

                           sb.append(">");
                           if (ch != 0) {
                              sb.append(normalize(value.toString()));
                           }

                           sb.append("</");
                           sb.append(tag);
                           sb.append(cb);
                           break;
                        }
                     case 0:
                     case 1:
                     case 3:
                     case 4:
                     case 7:
                     case 8:
                        sb.append(tab);
                        sb.append("<");
                        sb.append(tag);
                        sb.append(">");
                        sb.append(normalize(value.toString()));
                        sb.append("</");
                        sb.append(tag);
                        sb.append(cb);
                        break;
                     case 6:
                        if (value == null) {
                           value = new byte[0];
                        }

                        String base64Field = (new BASE64Encoder()).encodeBuffer((byte[])((byte[])value));
                        sb.append("<");
                        sb.append(tag);
                        if (convertNull && base64Field.equals("")) {
                           sb.append(" nil=\"true\"");
                        }

                        sb.append(">");
                        sb.append(base64Field);
                        sb.append("</");
                        sb.append(tag);
                        sb.append(cb);
                        break;
                     case 9:
                     default:
                        WTCLogger.logErrorBadFmlFldType(ta[k]);
                        return null;
                     case 10:
                        TypedFML32 fml32 = new TypedFML32((TypedFML32)value);
                        if (fml32.getFieldTables() == null) {
                           if (traceEnabled) {
                              ntrace.doTrace("/XmlFmlCnv/printGroupXML32/child FldTbls to parent FldTbls");
                           }

                           fml32.setFieldTables(pflds);
                        }

                        fml32Field = this.FML32toXML(fml32, false, true, true, beautify, l + 1);
                        sb.append(tab);
                        sb.append("<");
                        sb.append(tag);
                        if (convertNull && fml32Field.equals("")) {
                           sb.append(" nil=\"true\"");
                        }

                        sb.append(cb);
                        sb.append(fml32Field);
                        sb.append(tab);
                        sb.append("</");
                        sb.append(tag);
                        sb.append(cb);
                        break;
                     case 11:
                        sb.append(tab);
                        sb.append("<");
                        sb.append(tag);
                        sb.append(cb);
                        String view32Field = XmlViewCnv.ViewToXML(((FViewFld)value).getViewData(), ((FViewFld)value).getViewData().getClass(), false);
                        fml32Field = ((FViewFld)value).getViewData().getSubtype();
                        sb.append(tab);
                        sb.append("<");
                        sb.append("VIEW_");
                        sb.append(fml32Field);
                        sb.append(cb);
                        sb.append(view32Field);
                        sb.append(tab);
                        sb.append("</");
                        sb.append("VIEW_");
                        sb.append(fml32Field);
                        sb.append(cb);
                        sb.append(tab);
                        sb.append("</");
                        sb.append(tag);
                        sb.append(cb);
                  }
               }
            }
         }
      } catch (Ferror var32) {
         WTCLogger.logFEbadFMLinData(var32.toString());
         return null;
      }

      if (root) {
         sb.append("</FML32>");
         if (beautify) {
            sb.append("\n");
         }
      }

      return sb.toString();
   }

   protected static String normalize(String s) {
      StringBuffer str = new StringBuffer();
      int len = s != null ? s.length() : 0;

      for(int i = 0; i < len; ++i) {
         char ch = s.charAt(i);
         if (ch < ' ') {
            str.append("&#");
            str.append(Integer.toString(ch));
            str.append(';');
         } else {
            switch (ch) {
               case '\n':
               case '\r':
                  str.append("&#");
                  str.append(Integer.toString(ch));
                  str.append(';');
                  break;
               case '"':
                  str.append("&quot;");
                  break;
               case '&':
                  if (normalizeEscaped || !"&lt;".regionMatches(0, s, i, 4) && !"&gt;".regionMatches(0, s, i, 4) && !"&amp;".regionMatches(0, s, i, 5) && !"&quot;".regionMatches(0, s, i, 6) && !"&apos;".regionMatches(0, s, i, 6) && !"&#".regionMatches(0, s, i, 2)) {
                     str.append("&amp;");
                  } else {
                     str.append(ch);
                  }
                  break;
               case '\'':
                  str.append("&apos;");
                  break;
               case '<':
                  str.append("&lt;");
                  break;
               case '>':
                  str.append("&gt;");
                  break;
               default:
                  str.append(ch);
            }
         }
      }

      return str.toString();
   }

   public static boolean getNormalizeEscaped() {
      boolean value = true;
      String flag = System.getProperty("weblogic.wtc.Fml2XmlNormalizeEscaped");
      if (flag != null && "false".equals(flag)) {
         value = false;
      }

      return value;
   }

   public TypedFML XMLtoFML(String xmlinfo, FldTbl[] fldtbls) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/XMLtoFML/string,FldTbl[]");
      }

      if (xmlinfo == null) {
         WTCLogger.logErrorNullXmlArg();
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/XMLtoFML/5/null");
         }

         return null;
      } else if (fldtbls != null && fldtbls.length != 0) {
         TypedFML retfml = this.XMLtoFML(xmlinfo, new TypedFML(fldtbls));
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/XMLtoFML/15/FML");
         }

         return retfml;
      } else {
         WTCLogger.logErrorBadFldTblsArg();
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/XMLtoFML/10/null");
         }

         return null;
      }
   }

   public TypedFML32 XMLtoFML32(String xmlinfo, FldTbl[] fldtbls) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/XMLtoFML32/string,FldTbl[]");
      }

      if (xmlinfo == null) {
         WTCLogger.logErrorNullXmlArg();
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/XMLtoFML32/5/null");
         }

         return null;
      } else if (fldtbls != null && fldtbls.length != 0) {
         TypedFML32 retfml32 = this.XMLtoFML32(xmlinfo, new TypedFML32(fldtbls));
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/XMLtoFML32/15/FML32");
         }

         return retfml32;
      } else {
         WTCLogger.logErrorBadFldTblsArg();
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/XMLtoFML32/10/null");
         }

         return null;
      }
   }

   public TypedFML XMLtoFML(String xmlinput, TypedFML fmldata) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/XMLtoFML/string,FML");
      }

      if (xmlinput == null) {
         WTCLogger.logErrorNullXmlArg();
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/XMLtoFML/5/null");
         }

         return null;
      } else if (fmldata == null) {
         WTCLogger.logErrorNullFMLarg();
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/XMLtoFML/10/null");
         }

         return null;
      } else {
         this.myFML = fmldata;
         Element docRoot = this.getXmlRoot(xmlinput);
         if (docRoot == null) {
            WTCLogger.logErrorNoXmlDocRoot(xmlinput);
            if (traceEnabled) {
               ntrace.doTrace("]/XmlFmlCnv/XMLtoFML/15/null");
            }

            return null;
         } else {
            for(Node lg = docRoot.getFirstChild(); lg != null; lg = lg.getNextSibling()) {
               if (lg.getNodeType() == 1 && lg instanceof Element) {
                  Element e = (Element)lg;
                  Node sn = lg.getFirstChild();
                  String ename = e.getTagName();
                  if (ename.indexOf(58) != -1) {
                     ename = ename.substring(ename.indexOf(58) + 1);
                  }

                  String evalue = sn == null ? null : sn.getNodeValue();
                  if (traceEnabled) {
                     ntrace.doTrace("EName=" + ename + " EVal=" + evalue);
                  }

                  boolean nil = false;
                  NamedNodeMap atts;
                  if (convertNull || convertNullChar) {
                     atts = lg.getAttributes();
                     if (atts != null && atts.getNamedItem("nil") != null && atts.getNamedItem("nil").getNodeValue().equals("true")) {
                        nil = true;
                     }
                  }

                  if (convertNonPrintable) {
                     atts = sn.getAttributes();
                     if (atts != null && atts.getNamedItem("decValue") != null) {
                        int i = Integer.parseInt(atts.getNamedItem("decValue").getNodeValue());
                        evalue = Character.toString((char)i);
                     }
                  }

                  if (!this.xmladd2fml(ename, evalue, nil)) {
                     return null;
                  }
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/XmlFmlCnv/XMLtoFML/35/FML");
            }

            return new TypedFML(this.myFML);
         }
      }
   }

   public TypedFML32 XMLtoFML32(String xmlinput, TypedFML32 fml32data) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/XMLtoFML32/string,FML32");
      }

      if (xmlinput == null) {
         WTCLogger.logErrorNullXmlArg();
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/XMLtoFML32/5/null");
         }

         return null;
      } else if (fml32data == null) {
         WTCLogger.logErrorNullFML32arg();
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/XMLtoFML32/10/null");
         }

         return null;
      } else {
         Element docRoot = this.getXmlRoot(xmlinput);
         if (docRoot == null) {
            WTCLogger.logErrorNoXmlDocRoot(xmlinput);
            if (traceEnabled) {
               ntrace.doTrace("]/XmlFmlCnv/XMLtoFML32/15/null");
            }

            return null;
         } else {
            this.myFML32 = this.traverseNodes(docRoot, fml32data);
            if (this.myFML32 == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/XmlFmlCnv/XMLtoFML32/20/null");
               }

               return null;
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("]/XmlFmlCnv/XMLtoFML32/35/FML32");
               }

               return new TypedFML32(this.myFML32);
            }
         }
      }
   }

   private TypedFML32 traverseNodes(Element root, TypedFML32 locfml32) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/traverseNodes/Element,FML32");
      }

      for(Node lg = root.getFirstChild(); lg != null; lg = lg.getNextSibling()) {
         if (lg.getNodeType() == 1 && lg instanceof Element) {
            boolean nil = false;
            if (convertNull || convertNullChar || convertNonPrintable) {
               NamedNodeMap atts = lg.getAttributes();
               if (atts != null && atts.getNamedItem("nil") != null && atts.getNamedItem("nil").getNodeValue().equals("true")) {
                  nil = true;
               }
            }

            locfml32 = this.xmladd2fml32((Element)lg, locfml32, nil);
            if (locfml32 == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/XmlFmlCnv/traverseNodes/25/null");
               }

               throw new RuntimeException("error converting " + lg + " to fml32 field");
            }
         } else if (traceEnabled) {
            ntrace.doTrace("/XmlFmlCnv/traverseNodes/15/skip non-element node");
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/XmlFmlCnv/traverseNodes/35/FML32");
      }

      return locfml32;
   }

   private Element getXmlRoot(String xmlstr) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/getXMLRoot/");
      }

      Document doc = null;

      try {
         DOMParser parser = new DOMParser();
         parser.parse(new InputSource(new StringReader(xmlstr)));
         doc = parser.getDocument();
         parser = null;
      } catch (SAXException var6) {
         WTCLogger.logSEbadXml2Parser(xmlstr, var6.getMessage());
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/getXMLRoot/15/null");
         }

         return null;
      } catch (IOException var7) {
         WTCLogger.logIOEbadXml2Parser(xmlstr, var7.getMessage());
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/getXMLRoot/20/null");
         }

         return null;
      } catch (Exception var8) {
         WTCLogger.logUEbadXml2Parser(xmlstr, var8.getMessage());
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/getXMLRoot/25/null");
         }

         return null;
      }

      if (doc == null) {
         WTCLogger.logErrorNullDocFromParser(xmlstr);
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/getXMLRoot/30/null");
         }

         return null;
      } else {
         Element rootElem = doc.getDocumentElement();
         if (rootElem == null) {
            WTCLogger.logErrorNoTopElemFromStr(xmlstr);
            if (traceEnabled) {
               ntrace.doTrace("]/XmlFmlCnv/getXMLRoot/40/null");
            }

            return null;
         } else {
            String rtag = rootElem.getTagName();
            if (rtag == null) {
               WTCLogger.logErrorNullTopElemName(xmlstr);
               if (traceEnabled) {
                  ntrace.doTrace("]/XmlFmlCnv/getXMLRoot/45/null");
               }

               return null;
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("]/XmlFmlCnv/getXMLRoot/50/ELEMENT,TAG=" + rtag);
               }

               return rootElem;
            }
         }
      }
   }

   private boolean xmladd2fml(String xmltag, String xmlvalue, boolean nil) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/xmladd2fml/");
      }

      if (this.myFML == null) {
         WTCLogger.logErrorNoFMLdata();
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/xmladd2fml/5/false");
         }

         return false;
      } else if (xmltag == null) {
         WTCLogger.logErrorNullXmlElemName();
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/xmladd2fml/10/false");
         }

         return false;
      } else if (xmlvalue == null && !nil) {
         WTCLogger.logErrorNullXmlElemValue(xmltag);
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/xmladd2fml/15/false");
         }

         return false;
      } else {
         try {
            Object val_obj = null;
            String xval = null;
            int fid = this.myFML.Fldid(xmltag);
            String fname = this.myFML.Fname(fid);
            FmlKey fmlkey = new FmlKey(fid, -1);
            int fldtype = this.myFML.Fldtype(fid);
            if (xmlvalue != null || (fldtype == 0 || fldtype == 1 || fldtype == 3 || fldtype == 4 || fldtype == 2 || fldtype == 5 || fldtype == 6) && nil) {
               if (traceEnabled) {
                  ntrace.doTrace("Fname=" + fname + " Ftype=" + fldtype);
               }

               if (nil) {
                  switch (fldtype) {
                     case 5:
                        val_obj = new String(new char[]{'\u0000'});
                        break;
                     case 6:
                        val_obj = new byte[0];
                        break;
                     default:
                        val_obj = null;
                  }
               } else {
                  if (trimXmlValue) {
                     xval = xmlvalue.trim();
                  } else {
                     xval = new String(xmlvalue);
                  }

                  switch (fldtype) {
                     case 0:
                        val_obj = new Short(Short.parseShort(xval, 10));
                        break;
                     case 1:
                        val_obj = new Long(Long.parseLong(xval, 10));
                        break;
                     case 2:
                        val_obj = new Character(xval.charAt(0));
                        break;
                     case 3:
                        val_obj = new Float(Float.parseFloat(xval));
                        break;
                     case 4:
                        val_obj = new Double(Double.parseDouble(xval));
                        break;
                     case 5:
                        val_obj = new String(xval);
                        break;
                     case 6:
                        val_obj = (new BASE64Decoder()).decodeBuffer(xval);
                        break;
                     default:
                        WTCLogger.logErrorBadFmlFldType(fldtype);
                        if (traceEnabled) {
                           ntrace.doTrace("]/XmlFmlCnv/xmladd2fml/50/false");
                        }

                        return false;
                  }
               }
            } else if (fldtype != 5) {
               WTCLogger.logErrorNullXmlElemValue(xmltag);
               if (traceEnabled) {
                  ntrace.doTrace("]/XmlFmlCnv/xmladd2fml/20/null");
               }

               return false;
            }

            this.myFML.Fchg(fmlkey, val_obj);
            if (val_obj == null && traceEnabled) {
               ntrace.doTrace("XmlFmlCnv/xmladd2fml/55/val_obj==null");
            }
         } catch (Ferror var11) {
            WTCLogger.logFEbadFMLinData(var11.toString());
            if (traceEnabled) {
               ntrace.doTrace("]/XmlFmlCnv/xmladd2fml/60/false");
            }

            return false;
         } catch (Exception var12) {
            WTCLogger.logUEbadFMLinData(var12.getMessage());
            if (traceEnabled) {
               ntrace.doTrace("]/XmlFmlCnv/xmladd2fml/65/false");
            }

            return false;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/xmladd2fml/70/true");
         }

         return true;
      }
   }

   private TypedFML32 xmladd2fml32(Element pe, TypedFML32 pfml32, boolean nil) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/XmlFmlCnv/xmladd2fml32/Element,FML32");
      }

      if (pfml32 == null) {
         WTCLogger.logErrorNoFML32data();
         if (traceEnabled) {
            ntrace.doTrace("]/XmlFmlCnv/xmladd2fml32/15/null");
         }

         return null;
      } else {
         String xtag = pe.getTagName();
         if (xtag == null) {
            WTCLogger.logErrorNullXmlElemName();
            if (traceEnabled) {
               ntrace.doTrace("]/XmlFmlCnv/xmladd2fml32/25/null");
            }

            return null;
         } else {
            if (xtag.indexOf(58) != -1) {
               xtag = xtag.substring(xtag.indexOf(58) + 1);
            }

            try {
               int fid32 = pfml32.Fldid(xtag);
               String fname32 = pfml32.Fname(fid32);
               FmlKey fmlkey = new FmlKey(fid32, -1);
               int fldtype32 = pfml32.Fldtype(fid32);
               if (traceEnabled) {
                  ntrace.doTrace("Fname=" + fname32 + " Ftype2=" + fldtype32);
               }

               Object val_obj = null;
               if (fldtype32 == 10) {
                  TypedFML32 cfml32 = new TypedFML32(pfml32.getFieldTables());
                  val_obj = this.traverseNodes(pe, cfml32);
               } else {
                  Node n;
                  if (fldtype32 == 11) {
                     for(n = pe.getFirstChild(); n != null && n.getNodeType() != 1 && !(n instanceof Element); n = n.getNextSibling()) {
                     }

                     Element sub = (Element)n;
                     String viewType = sub.getTagName();
                     if (viewType.indexOf(58) != -1) {
                        viewType = viewType.substring(viewType.indexOf(58) + 1);
                     }

                     viewType = viewType.substring(viewType.indexOf(95) + 1);
                     TypedBuffer b = (new ViewHelper()).newViewInstance(viewType);
                     if (b == null) {
                        throw new Exception("Invalid view type: " + viewType);
                     }

                     FViewFld fld = new FViewFld(viewType, (TypedView32)XmlViewCnv.traverseNodes(sub, new TuxClassView(b.getClass())));
                     val_obj = fld;
                  } else {
                     n = pe.getFirstChild();
                     String xval = null;
                     if ((n == null || (xval = n.getNodeValue()) == null) && (fldtype32 != 2 && fldtype32 != 0 && fldtype32 != 7 && fldtype32 != 8 && fldtype32 != 1 && fldtype32 != 3 && fldtype32 != 4 && fldtype32 != 5 && fldtype32 != 6 || !nil)) {
                        if (fldtype32 != 5) {
                           WTCLogger.logErrorNullXmlElemValue(xtag);
                           if (traceEnabled) {
                              ntrace.doTrace("]/XmlFmlCnv/xmladd2fml32/50/null");
                           }

                           if (!convertNullToZero) {
                              return pfml32;
                           }

                           switch (fldtype32) {
                              case 0:
                                 val_obj = new Short((short)0);
                                 break;
                              case 1:
                                 val_obj = new Long(0L);
                                 break;
                              case 2:
                                 val_obj = new Character('\u0000');
                                 break;
                              case 3:
                                 val_obj = new Float(0.0F);
                                 break;
                              case 4:
                                 val_obj = new Double(0.0);
                                 break;
                              case 5:
                              default:
                                 return pfml32;
                              case 6:
                                 val_obj = (new BASE64Decoder()).decodeBuffer(new String(new char[1]));
                                 break;
                              case 7:
                              case 8:
                                 val_obj = new Integer(0);
                           }
                        }
                     } else if (nil) {
                        switch (fldtype32) {
                           case 5:
                              val_obj = new String(new char[]{'\u0000'});
                              break;
                           case 6:
                              val_obj = new byte[0];
                              break;
                           default:
                              val_obj = null;
                        }
                     } else {
                        if (trimXmlValue) {
                           xval = xval.trim();
                        }

                        if (traceEnabled) {
                           ntrace.doTrace("XMLTAG=" + xtag + " XMLVAL=" + xval);
                        }

                        switch (fldtype32) {
                           case 0:
                              val_obj = new Short(Short.parseShort(xval, 10));
                              break;
                           case 1:
                              val_obj = new Long(Long.parseLong(xval, 10));
                              break;
                           case 2:
                              NamedNodeMap atts = pe.getAttributes();
                              if (atts != null) {
                                 if (atts.getNamedItem("decValue") != null) {
                                    int i = Integer.parseInt(atts.getNamedItem("decValue").getNodeValue());
                                    val_obj = new Character((char)i);
                                 } else {
                                    val_obj = new Character(xval.charAt(0));
                                 }
                              } else {
                                 val_obj = new Character(xval.charAt(0));
                              }
                              break;
                           case 3:
                              val_obj = new Float(Float.parseFloat(xval));
                              break;
                           case 4:
                              val_obj = new Double(Double.parseDouble(xval));
                              break;
                           case 5:
                              val_obj = new String(xval);
                              break;
                           case 6:
                              val_obj = (new BASE64Decoder()).decodeBuffer(xval);
                              break;
                           case 7:
                           case 8:
                              val_obj = new Integer(Integer.parseInt(xval, 10));
                              break;
                           default:
                              WTCLogger.logErrorBadFml32FldType(fldtype32);
                              if (traceEnabled) {
                                 ntrace.doTrace("]/XmlFmlCnv/xmladd2fml32/95/null");
                              }

                              return null;
                        }
                     }
                  }
               }

               pfml32.Fchg(fmlkey, val_obj);
               if (val_obj != null) {
                  if (traceEnabled) {
                     ntrace.doTrace("XmlFmlCnv/xmladd2fml32/100/" + fname32 + " added");
                  }
               } else if (traceEnabled) {
                  ntrace.doTrace("XmlFmlCnv/xmladd2fml32/105/val_obj==null");
               }
            } catch (Ferror var16) {
               WTCLogger.logFEbadFML32inData(var16.toString(), var16.getMessage());
               if (traceEnabled) {
                  ntrace.doTrace("]/XmlFmlCnv/xmladd2fml32/115/null");
               }

               return null;
            } catch (Exception var17) {
               var17.printStackTrace();
               WTCLogger.logUEbadFML32inData(var17.toString());
               if (traceEnabled) {
                  ntrace.doTrace("]/XmlFmlCnv/xmladd2fml32/125/null");
               }

               return null;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/XmlFmlCnv/xmladd2fml32/135/FML32");
            }

            return pfml32;
         }
      }
   }

   private static boolean getConvertNonPrintable() {
      String convertNull = System.getProperty("weblogic.wtc.convertNonPrintable");
      return convertNull != null && convertNull.equalsIgnoreCase("true");
   }

   private static boolean getConvertNull() {
      String convertNull = System.getProperty("weblogic.wtc.convertNull");
      return convertNull != null && convertNull.equalsIgnoreCase("true");
   }

   private static boolean getConvertNullChar() {
      String convertNullChar = System.getProperty("weblogic.wtc.convertNullChar");
      return convertNullChar != null && convertNullChar.equalsIgnoreCase("true");
   }

   private static boolean getTrimXmlValue() {
      String trimXmlValue = System.getProperty("weblogic.wtc.xml2fml.trim");
      return trimXmlValue == null || !trimXmlValue.equalsIgnoreCase("false");
   }

   private static boolean getConvertNullToZero() {
      String convertNullToZeroValue = System.getProperty("weblogic.wtc.convertNullToZero");
      return convertNullToZeroValue != null && convertNullToZeroValue.equalsIgnoreCase("true");
   }
}
