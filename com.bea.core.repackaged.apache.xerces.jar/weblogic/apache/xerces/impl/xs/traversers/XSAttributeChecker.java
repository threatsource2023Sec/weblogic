package weblogic.apache.xerces.impl.xs.traversers;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import weblogic.apache.xerces.impl.dv.InvalidDatatypeValueException;
import weblogic.apache.xerces.impl.dv.ValidatedInfo;
import weblogic.apache.xerces.impl.dv.ValidationContext;
import weblogic.apache.xerces.impl.dv.XSSimpleType;
import weblogic.apache.xerces.impl.xs.SchemaGrammar;
import weblogic.apache.xerces.impl.xs.SchemaNamespaceSupport;
import weblogic.apache.xerces.impl.xs.SchemaSymbols;
import weblogic.apache.xerces.impl.xs.XSAttributeDecl;
import weblogic.apache.xerces.impl.xs.XSGrammarBucket;
import weblogic.apache.xerces.impl.xs.util.XInt;
import weblogic.apache.xerces.impl.xs.util.XIntPool;
import weblogic.apache.xerces.util.DOMUtil;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.util.XMLChar;
import weblogic.apache.xerces.util.XMLSymbols;
import weblogic.apache.xerces.xni.QName;

public class XSAttributeChecker {
   private static final String ELEMENT_N = "element_n";
   private static final String ELEMENT_R = "element_r";
   private static final String ATTRIBUTE_N = "attribute_n";
   private static final String ATTRIBUTE_R = "attribute_r";
   private static int ATTIDX_COUNT = 0;
   public static final int ATTIDX_ABSTRACT;
   public static final int ATTIDX_AFORMDEFAULT;
   public static final int ATTIDX_BASE;
   public static final int ATTIDX_BLOCK;
   public static final int ATTIDX_BLOCKDEFAULT;
   public static final int ATTIDX_DEFAULT;
   public static final int ATTIDX_EFORMDEFAULT;
   public static final int ATTIDX_FINAL;
   public static final int ATTIDX_FINALDEFAULT;
   public static final int ATTIDX_FIXED;
   public static final int ATTIDX_FORM;
   public static final int ATTIDX_ID;
   public static final int ATTIDX_ITEMTYPE;
   public static final int ATTIDX_MAXOCCURS;
   public static final int ATTIDX_MEMBERTYPES;
   public static final int ATTIDX_MINOCCURS;
   public static final int ATTIDX_MIXED;
   public static final int ATTIDX_NAME;
   public static final int ATTIDX_NAMESPACE;
   public static final int ATTIDX_NAMESPACE_LIST;
   public static final int ATTIDX_NILLABLE;
   public static final int ATTIDX_NONSCHEMA;
   public static final int ATTIDX_PROCESSCONTENTS;
   public static final int ATTIDX_PUBLIC;
   public static final int ATTIDX_REF;
   public static final int ATTIDX_REFER;
   public static final int ATTIDX_SCHEMALOCATION;
   public static final int ATTIDX_SOURCE;
   public static final int ATTIDX_SUBSGROUP;
   public static final int ATTIDX_SYSTEM;
   public static final int ATTIDX_TARGETNAMESPACE;
   public static final int ATTIDX_TYPE;
   public static final int ATTIDX_USE;
   public static final int ATTIDX_VALUE;
   public static final int ATTIDX_ENUMNSDECLS;
   public static final int ATTIDX_VERSION;
   public static final int ATTIDX_XML_LANG;
   public static final int ATTIDX_XPATH;
   public static final int ATTIDX_FROMDEFAULT;
   public static final int ATTIDX_ISRETURNED;
   private static final XIntPool fXIntPool;
   private static final XInt INT_QUALIFIED;
   private static final XInt INT_UNQUALIFIED;
   private static final XInt INT_EMPTY_SET;
   private static final XInt INT_ANY_STRICT;
   private static final XInt INT_ANY_LAX;
   private static final XInt INT_ANY_SKIP;
   private static final XInt INT_ANY_ANY;
   private static final XInt INT_ANY_LIST;
   private static final XInt INT_ANY_NOT;
   private static final XInt INT_USE_OPTIONAL;
   private static final XInt INT_USE_REQUIRED;
   private static final XInt INT_USE_PROHIBITED;
   private static final XInt INT_WS_PRESERVE;
   private static final XInt INT_WS_REPLACE;
   private static final XInt INT_WS_COLLAPSE;
   private static final XInt INT_UNBOUNDED;
   private static final Hashtable fEleAttrsMapG;
   private static final Hashtable fEleAttrsMapL;
   protected static final int DT_ANYURI = 0;
   protected static final int DT_ID = 1;
   protected static final int DT_QNAME = 2;
   protected static final int DT_STRING = 3;
   protected static final int DT_TOKEN = 4;
   protected static final int DT_NCNAME = 5;
   protected static final int DT_XPATH = 6;
   protected static final int DT_XPATH1 = 7;
   protected static final int DT_LANGUAGE = 8;
   protected static final int DT_COUNT = 9;
   private static final XSSimpleType[] fExtraDVs;
   protected static final int DT_BLOCK = -1;
   protected static final int DT_BLOCK1 = -2;
   protected static final int DT_FINAL = -3;
   protected static final int DT_FINAL1 = -4;
   protected static final int DT_FINAL2 = -5;
   protected static final int DT_FORM = -6;
   protected static final int DT_MAXOCCURS = -7;
   protected static final int DT_MAXOCCURS1 = -8;
   protected static final int DT_MEMBERTYPES = -9;
   protected static final int DT_MINOCCURS1 = -10;
   protected static final int DT_NAMESPACE = -11;
   protected static final int DT_PROCESSCONTENTS = -12;
   protected static final int DT_USE = -13;
   protected static final int DT_WHITESPACE = -14;
   protected static final int DT_BOOLEAN = -15;
   protected static final int DT_NONNEGINT = -16;
   protected static final int DT_POSINT = -17;
   protected XSDHandler fSchemaHandler = null;
   protected SymbolTable fSymbolTable = null;
   protected Hashtable fNonSchemaAttrs = new Hashtable();
   protected Vector fNamespaceList = new Vector();
   protected boolean[] fSeen;
   private static boolean[] fSeenTemp;
   static final int INIT_POOL_SIZE = 10;
   static final int INC_POOL_SIZE = 10;
   Object[][] fArrayPool;
   private static Object[] fTempArray;
   int fPoolPos;

   public XSAttributeChecker(XSDHandler var1) {
      this.fSeen = new boolean[ATTIDX_COUNT];
      this.fArrayPool = new Object[10][ATTIDX_COUNT];
      this.fPoolPos = 0;
      this.fSchemaHandler = var1;
   }

   public void reset(SymbolTable var1) {
      this.fSymbolTable = var1;
      this.fNonSchemaAttrs.clear();
   }

   public Object[] checkAttributes(Element var1, boolean var2, XSDocumentInfo var3) {
      return this.checkAttributes(var1, var2, var3, false);
   }

   public Object[] checkAttributes(Element var1, boolean var2, XSDocumentInfo var3, boolean var4) {
      if (var1 == null) {
         return null;
      } else {
         Attr[] var5 = DOMUtil.getAttrs(var1);
         this.resolveNamespace(var1, var5, var3.fNamespaceSupport);
         String var6 = DOMUtil.getNamespaceURI(var1);
         String var7 = DOMUtil.getLocalName(var1);
         if (!SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(var6)) {
            this.reportSchemaError("s4s-elt-schema-ns", new Object[]{var7}, var1);
         }

         Hashtable var8 = fEleAttrsMapG;
         String var9 = var7;
         if (!var2) {
            var8 = fEleAttrsMapL;
            if (var7.equals(SchemaSymbols.ELT_ELEMENT)) {
               if (DOMUtil.getAttr(var1, SchemaSymbols.ATT_REF) != null) {
                  var9 = "element_r";
               } else {
                  var9 = "element_n";
               }
            } else if (var7.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
               if (DOMUtil.getAttr(var1, SchemaSymbols.ATT_REF) != null) {
                  var9 = "attribute_r";
               } else {
                  var9 = "attribute_n";
               }
            }
         }

         Container var10 = (Container)var8.get(var9);
         if (var10 == null) {
            this.reportSchemaError("s4s-elt-invalid", new Object[]{var7}, var1);
            return null;
         } else {
            Object[] var11 = this.getAvailableArray();
            long var12 = 0L;
            System.arraycopy(fSeenTemp, 0, this.fSeen, 0, ATTIDX_COUNT);
            int var14 = var5.length;
            Attr var15 = null;

            for(int var16 = 0; var16 < var14; ++var16) {
               var15 = var5[var16];
               String var17 = var15.getName();
               String var18 = DOMUtil.getNamespaceURI(var15);
               String var19 = DOMUtil.getValue(var15);
               if (var17.startsWith("xml")) {
                  String var20 = DOMUtil.getPrefix(var15);
                  if ("xmlns".equals(var20) || "xmlns".equals(var17)) {
                     continue;
                  }

                  if (SchemaSymbols.ATT_XML_LANG.equals(var17) && (SchemaSymbols.ELT_SCHEMA.equals(var7) || SchemaSymbols.ELT_DOCUMENTATION.equals(var7))) {
                     var18 = null;
                  }
               }

               if (var18 != null && var18.length() != 0) {
                  if (var18.equals(SchemaSymbols.URI_SCHEMAFORSCHEMA)) {
                     this.reportSchemaError("s4s-att-not-allowed", new Object[]{var7, var17}, var1);
                  } else {
                     if (var11[ATTIDX_NONSCHEMA] == null) {
                        var11[ATTIDX_NONSCHEMA] = new Vector(4, 2);
                     }

                     ((Vector)var11[ATTIDX_NONSCHEMA]).addElement(var17);
                     ((Vector)var11[ATTIDX_NONSCHEMA]).addElement(var19);
                  }
               } else {
                  OneAttr var29 = var10.get(var17);
                  if (var29 == null) {
                     this.reportSchemaError("s4s-att-not-allowed", new Object[]{var7, var17}, var1);
                  } else {
                     this.fSeen[var29.valueIndex] = true;

                     try {
                        if (var29.dvIndex >= 0) {
                           if (var29.dvIndex != 3 && var29.dvIndex != 6 && var29.dvIndex != 7) {
                              XSSimpleType var21 = fExtraDVs[var29.dvIndex];
                              Object var22 = var21.validate((String)var19, var3.fValidationContext, (ValidatedInfo)null);
                              if (var29.dvIndex == 2) {
                                 QName var23 = (QName)var22;
                                 if (var23.prefix == XMLSymbols.EMPTY_STRING && var23.uri == null && var3.fIsChameleonSchema) {
                                    var23.uri = var3.fTargetNamespace;
                                 }
                              }

                              var11[var29.valueIndex] = var22;
                           } else {
                              var11[var29.valueIndex] = var19;
                           }
                        } else {
                           var11[var29.valueIndex] = this.validate(var11, var17, var19, var29.dvIndex, var3);
                        }
                     } catch (InvalidDatatypeValueException var24) {
                        this.reportSchemaError("s4s-att-invalid-value", new Object[]{var7, var17, var24.getMessage()}, var1);
                        if (var29.dfltValue != null) {
                           var11[var29.valueIndex] = var29.dfltValue;
                        }
                     }

                     if (var7.equals(SchemaSymbols.ELT_ENUMERATION) && var4) {
                        var11[ATTIDX_ENUMNSDECLS] = new SchemaNamespaceSupport(var3.fNamespaceSupport);
                     }
                  }
               }
            }

            OneAttr[] var25 = var10.values;

            for(int var26 = 0; var26 < var25.length; ++var26) {
               OneAttr var27 = var25[var26];
               if (var27.dfltValue != null && !this.fSeen[var27.valueIndex]) {
                  var11[var27.valueIndex] = var27.dfltValue;
                  var12 |= (long)(1 << var27.valueIndex);
               }
            }

            var11[ATTIDX_FROMDEFAULT] = new Long(var12);
            if (var11[ATTIDX_MAXOCCURS] != null) {
               int var28 = ((XInt)var11[ATTIDX_MINOCCURS]).intValue();
               int var30 = ((XInt)var11[ATTIDX_MAXOCCURS]).intValue();
               if (var30 != -1 && var28 > var30) {
                  this.reportSchemaError("p-props-correct.2.1", new Object[]{var7, var11[ATTIDX_MINOCCURS], var11[ATTIDX_MAXOCCURS]}, var1);
                  var11[ATTIDX_MINOCCURS] = var11[ATTIDX_MAXOCCURS];
               }
            }

            return var11;
         }
      }
   }

   private Object validate(Object[] var1, String var2, String var3, int var4, XSDocumentInfo var5) throws InvalidDatatypeValueException {
      if (var3 == null) {
         return null;
      } else {
         String var6 = XMLChar.trim(var3);
         Object var7 = null;
         int var9;
         StringTokenizer var10;
         String var11;
         switch (var4) {
            case -17:
               try {
                  if (var6.length() > 0 && var6.charAt(0) == '+') {
                     var6 = var6.substring(1);
                  }

                  var7 = fXIntPool.getXInt(Integer.parseInt(var6));
               } catch (NumberFormatException var17) {
                  throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{var6, "positiveInteger"});
               }

               if (((XInt)var7).intValue() <= 0) {
                  throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{var6, "positiveInteger"});
               }
               break;
            case -16:
               try {
                  if (var6.length() > 0 && var6.charAt(0) == '+') {
                     var6 = var6.substring(1);
                  }

                  var7 = fXIntPool.getXInt(Integer.parseInt(var6));
               } catch (NumberFormatException var16) {
                  throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{var6, "nonNegativeInteger"});
               }

               if (((XInt)var7).intValue() < 0) {
                  throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{var6, "nonNegativeInteger"});
               }
               break;
            case -15:
               if (!var6.equals("false") && !var6.equals("0")) {
                  if (!var6.equals("true") && !var6.equals("1")) {
                     throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{var6, "boolean"});
                  }

                  var7 = Boolean.TRUE;
               } else {
                  var7 = Boolean.FALSE;
               }
               break;
            case -14:
               if (var6.equals("preserve")) {
                  var7 = INT_WS_PRESERVE;
               } else if (var6.equals("replace")) {
                  var7 = INT_WS_REPLACE;
               } else {
                  if (!var6.equals("collapse")) {
                     throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{var6, "(preserve | replace | collapse)"});
                  }

                  var7 = INT_WS_COLLAPSE;
               }
               break;
            case -13:
               if (var6.equals("optional")) {
                  var7 = INT_USE_OPTIONAL;
               } else if (var6.equals("required")) {
                  var7 = INT_USE_REQUIRED;
               } else {
                  if (!var6.equals("prohibited")) {
                     throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{var6, "(optional | prohibited | required)"});
                  }

                  var7 = INT_USE_PROHIBITED;
               }
               break;
            case -12:
               if (var6.equals("strict")) {
                  var7 = INT_ANY_STRICT;
               } else if (var6.equals("lax")) {
                  var7 = INT_ANY_LAX;
               } else {
                  if (!var6.equals("skip")) {
                     throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{var6, "(lax | skip | strict)"});
                  }

                  var7 = INT_ANY_SKIP;
               }
               break;
            case -11:
               if (var6.equals("##any")) {
                  var7 = INT_ANY_ANY;
               } else if (var6.equals("##other")) {
                  var7 = INT_ANY_NOT;
                  String[] var20 = new String[]{var5.fTargetNamespace, null};
                  var1[ATTIDX_NAMESPACE_LIST] = var20;
               } else {
                  var7 = INT_ANY_LIST;
                  this.fNamespaceList.removeAllElements();
                  var10 = new StringTokenizer(var6, " \n\t\r");

                  try {
                     while(var10.hasMoreTokens()) {
                        var11 = var10.nextToken();
                        String var21;
                        if (var11.equals("##local")) {
                           var21 = null;
                        } else if (var11.equals("##targetNamespace")) {
                           var21 = var5.fTargetNamespace;
                        } else {
                           fExtraDVs[0].validate((String)var11, var5.fValidationContext, (ValidatedInfo)null);
                           var21 = this.fSymbolTable.addSymbol(var11);
                        }

                        if (!this.fNamespaceList.contains(var21)) {
                           this.fNamespaceList.addElement(var21);
                        }
                     }
                  } catch (InvalidDatatypeValueException var18) {
                     throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{var6, "((##any | ##other) | List of (anyURI | (##targetNamespace | ##local)) )"});
                  }

                  int var13 = this.fNamespaceList.size();
                  String[] var14 = new String[var13];
                  this.fNamespaceList.copyInto(var14);
                  var1[ATTIDX_NAMESPACE_LIST] = var14;
               }
               break;
            case -10:
               if (var6.equals("0")) {
                  var7 = fXIntPool.getXInt(0);
               } else {
                  if (!var6.equals("1")) {
                     throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{var6, "(0 | 1)"});
                  }

                  var7 = fXIntPool.getXInt(1);
               }
               break;
            case -9:
               Vector var8 = new Vector();

               try {
                  QName var12;
                  for(var10 = new StringTokenizer(var6, " \n\t\r"); var10.hasMoreTokens(); var8.addElement(var12)) {
                     var11 = var10.nextToken();
                     var12 = (QName)fExtraDVs[2].validate((String)var11, var5.fValidationContext, (ValidatedInfo)null);
                     if (var12.prefix == XMLSymbols.EMPTY_STRING && var12.uri == null && var5.fIsChameleonSchema) {
                        var12.uri = var5.fTargetNamespace;
                     }
                  }

                  var7 = var8;
                  break;
               } catch (InvalidDatatypeValueException var19) {
                  throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.2", new Object[]{var6, "(List of QName)"});
               }
            case -8:
               if (!var6.equals("1")) {
                  throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{var6, "(1)"});
               }

               var7 = fXIntPool.getXInt(1);
               break;
            case -7:
               if (var6.equals("unbounded")) {
                  var7 = INT_UNBOUNDED;
               } else {
                  try {
                     var7 = this.validate(var1, var2, var6, -16, var5);
                  } catch (NumberFormatException var15) {
                     throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{var6, "(nonNegativeInteger | unbounded)"});
                  }
               }
               break;
            case -6:
               if (var6.equals("qualified")) {
                  var7 = INT_QUALIFIED;
               } else {
                  if (!var6.equals("unqualified")) {
                     throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[]{var6, "(qualified | unqualified)"});
                  }

                  var7 = INT_UNQUALIFIED;
               }
               break;
            case -5:
               var9 = 0;
               if (var6.equals("#all")) {
                  var9 = 31;
               } else {
                  var10 = new StringTokenizer(var6, " \n\t\r");

                  while(var10.hasMoreTokens()) {
                     var11 = var10.nextToken();
                     if (var11.equals("extension")) {
                        var9 |= 1;
                     } else if (var11.equals("restriction")) {
                        var9 |= 2;
                     } else if (var11.equals("list")) {
                        var9 |= 16;
                     } else {
                        if (!var11.equals("union")) {
                           throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{var6, "(#all | List of (extension | restriction | list | union))"});
                        }

                        var9 |= 8;
                     }
                  }
               }

               var7 = fXIntPool.getXInt(var9);
               break;
            case -4:
               var9 = 0;
               if (var6.equals("#all")) {
                  var9 = 31;
               } else {
                  var10 = new StringTokenizer(var6, " \n\t\r");

                  while(var10.hasMoreTokens()) {
                     var11 = var10.nextToken();
                     if (var11.equals("list")) {
                        var9 |= 16;
                     } else if (var11.equals("union")) {
                        var9 |= 8;
                     } else {
                        if (!var11.equals("restriction")) {
                           throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{var6, "(#all | List of (list | union | restriction))"});
                        }

                        var9 |= 2;
                     }
                  }
               }

               var7 = fXIntPool.getXInt(var9);
               break;
            case -3:
            case -2:
               var9 = 0;
               if (var6.equals("#all")) {
                  var9 = 31;
               } else {
                  var10 = new StringTokenizer(var6, " \n\t\r");

                  while(var10.hasMoreTokens()) {
                     var11 = var10.nextToken();
                     if (var11.equals("extension")) {
                        var9 |= 1;
                     } else {
                        if (!var11.equals("restriction")) {
                           throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{var6, "(#all | List of (extension | restriction))"});
                        }

                        var9 |= 2;
                     }
                  }
               }

               var7 = fXIntPool.getXInt(var9);
               break;
            case -1:
               var9 = 0;
               if (var6.equals("#all")) {
                  var9 = 31;
               } else {
                  var10 = new StringTokenizer(var6, " \n\t\r");

                  while(var10.hasMoreTokens()) {
                     var11 = var10.nextToken();
                     if (var11.equals("extension")) {
                        var9 |= 1;
                     } else if (var11.equals("restriction")) {
                        var9 |= 2;
                     } else {
                        if (!var11.equals("substitution")) {
                           throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[]{var6, "(#all | List of (extension | restriction | substitution))"});
                        }

                        var9 |= 4;
                     }
                  }
               }

               var7 = fXIntPool.getXInt(var9);
         }

         return var7;
      }
   }

   void reportSchemaError(String var1, Object[] var2, Element var3) {
      this.fSchemaHandler.reportSchemaError(var1, var2, var3);
   }

   public void checkNonSchemaAttributes(XSGrammarBucket var1) {
      Iterator var2 = this.fNonSchemaAttrs.entrySet().iterator();

      while(true) {
         Map.Entry var4;
         XSSimpleType var9;
         do {
            XSAttributeDecl var3;
            do {
               String var7;
               SchemaGrammar var8;
               do {
                  if (!var2.hasNext()) {
                     return;
                  }

                  var4 = (Map.Entry)var2.next();
                  String var5 = (String)var4.getKey();
                  String var6 = var5.substring(0, var5.indexOf(44));
                  var7 = var5.substring(var5.indexOf(44) + 1);
                  var8 = var1.getGrammar(var6);
               } while(var8 == null);

               var3 = var8.getGlobalAttributeDecl(var7);
            } while(var3 == null);

            var9 = (XSSimpleType)var3.getTypeDefinition();
         } while(var9 == null);

         Vector var10 = (Vector)var4.getValue();
         String var12 = (String)var10.elementAt(0);
         int var13 = var10.size();

         for(int var14 = 1; var14 < var13; var14 += 2) {
            String var11 = (String)var10.elementAt(var14);

            try {
               var9.validate((String)((String)var10.elementAt(var14 + 1)), (ValidationContext)null, (ValidatedInfo)null);
            } catch (InvalidDatatypeValueException var16) {
               this.reportSchemaError("s4s-att-invalid-value", new Object[]{var11, var12, var16.getMessage()}, (Element)null);
            }
         }
      }
   }

   public static String normalize(String var0, short var1) {
      int var2 = var0 == null ? 0 : var0.length();
      if (var2 != 0 && var1 != 0) {
         StringBuffer var3 = new StringBuffer();
         char var4;
         int var5;
         if (var1 == 1) {
            for(var5 = 0; var5 < var2; ++var5) {
               var4 = var0.charAt(var5);
               if (var4 != '\t' && var4 != '\n' && var4 != '\r') {
                  var3.append(var4);
               } else {
                  var3.append(' ');
               }
            }
         } else {
            boolean var6 = true;

            for(var5 = 0; var5 < var2; ++var5) {
               var4 = var0.charAt(var5);
               if (var4 != '\t' && var4 != '\n' && var4 != '\r' && var4 != ' ') {
                  var3.append(var4);
                  var6 = false;
               } else {
                  while(var5 < var2 - 1) {
                     var4 = var0.charAt(var5 + 1);
                     if (var4 != '\t' && var4 != '\n' && var4 != '\r' && var4 != ' ') {
                        break;
                     }

                     ++var5;
                  }

                  if (var5 < var2 - 1 && !var6) {
                     var3.append(' ');
                  }
               }
            }
         }

         return var3.toString();
      } else {
         return var0;
      }
   }

   protected Object[] getAvailableArray() {
      if (this.fArrayPool.length == this.fPoolPos) {
         this.fArrayPool = new Object[this.fPoolPos + 10][];

         for(int var1 = this.fPoolPos; var1 < this.fArrayPool.length; ++var1) {
            this.fArrayPool[var1] = new Object[ATTIDX_COUNT];
         }
      }

      Object[] var2 = this.fArrayPool[this.fPoolPos];
      this.fArrayPool[this.fPoolPos++] = null;
      System.arraycopy(fTempArray, 0, var2, 0, ATTIDX_COUNT - 1);
      var2[ATTIDX_ISRETURNED] = Boolean.FALSE;
      return var2;
   }

   public void returnAttrArray(Object[] var1, XSDocumentInfo var2) {
      if (var2 != null) {
         var2.fNamespaceSupport.popContext();
      }

      if (this.fPoolPos != 0 && var1 != null && var1.length == ATTIDX_COUNT && !(Boolean)var1[ATTIDX_ISRETURNED]) {
         var1[ATTIDX_ISRETURNED] = Boolean.TRUE;
         if (var1[ATTIDX_NONSCHEMA] != null) {
            ((Vector)var1[ATTIDX_NONSCHEMA]).clear();
         }

         this.fArrayPool[--this.fPoolPos] = var1;
      }
   }

   public void resolveNamespace(Element var1, Attr[] var2, SchemaNamespaceSupport var3) {
      var3.pushContext();
      int var4 = var2.length;
      Attr var5 = null;

      for(int var9 = 0; var9 < var4; ++var9) {
         var5 = var2[var9];
         String var6 = DOMUtil.getName(var5);
         String var7 = null;
         if (var6.equals(XMLSymbols.PREFIX_XMLNS)) {
            var7 = XMLSymbols.EMPTY_STRING;
         } else if (var6.startsWith("xmlns:")) {
            var7 = this.fSymbolTable.addSymbol(DOMUtil.getLocalName(var5));
         }

         if (var7 != null) {
            String var8 = this.fSymbolTable.addSymbol(DOMUtil.getValue(var5));
            var3.declarePrefix(var7, var8.length() != 0 ? var8 : null);
         }
      }

   }

   static {
      ATTIDX_ABSTRACT = ATTIDX_COUNT++;
      ATTIDX_AFORMDEFAULT = ATTIDX_COUNT++;
      ATTIDX_BASE = ATTIDX_COUNT++;
      ATTIDX_BLOCK = ATTIDX_COUNT++;
      ATTIDX_BLOCKDEFAULT = ATTIDX_COUNT++;
      ATTIDX_DEFAULT = ATTIDX_COUNT++;
      ATTIDX_EFORMDEFAULT = ATTIDX_COUNT++;
      ATTIDX_FINAL = ATTIDX_COUNT++;
      ATTIDX_FINALDEFAULT = ATTIDX_COUNT++;
      ATTIDX_FIXED = ATTIDX_COUNT++;
      ATTIDX_FORM = ATTIDX_COUNT++;
      ATTIDX_ID = ATTIDX_COUNT++;
      ATTIDX_ITEMTYPE = ATTIDX_COUNT++;
      ATTIDX_MAXOCCURS = ATTIDX_COUNT++;
      ATTIDX_MEMBERTYPES = ATTIDX_COUNT++;
      ATTIDX_MINOCCURS = ATTIDX_COUNT++;
      ATTIDX_MIXED = ATTIDX_COUNT++;
      ATTIDX_NAME = ATTIDX_COUNT++;
      ATTIDX_NAMESPACE = ATTIDX_COUNT++;
      ATTIDX_NAMESPACE_LIST = ATTIDX_COUNT++;
      ATTIDX_NILLABLE = ATTIDX_COUNT++;
      ATTIDX_NONSCHEMA = ATTIDX_COUNT++;
      ATTIDX_PROCESSCONTENTS = ATTIDX_COUNT++;
      ATTIDX_PUBLIC = ATTIDX_COUNT++;
      ATTIDX_REF = ATTIDX_COUNT++;
      ATTIDX_REFER = ATTIDX_COUNT++;
      ATTIDX_SCHEMALOCATION = ATTIDX_COUNT++;
      ATTIDX_SOURCE = ATTIDX_COUNT++;
      ATTIDX_SUBSGROUP = ATTIDX_COUNT++;
      ATTIDX_SYSTEM = ATTIDX_COUNT++;
      ATTIDX_TARGETNAMESPACE = ATTIDX_COUNT++;
      ATTIDX_TYPE = ATTIDX_COUNT++;
      ATTIDX_USE = ATTIDX_COUNT++;
      ATTIDX_VALUE = ATTIDX_COUNT++;
      ATTIDX_ENUMNSDECLS = ATTIDX_COUNT++;
      ATTIDX_VERSION = ATTIDX_COUNT++;
      ATTIDX_XML_LANG = ATTIDX_COUNT++;
      ATTIDX_XPATH = ATTIDX_COUNT++;
      ATTIDX_FROMDEFAULT = ATTIDX_COUNT++;
      ATTIDX_ISRETURNED = ATTIDX_COUNT++;
      fXIntPool = new XIntPool();
      INT_QUALIFIED = fXIntPool.getXInt(1);
      INT_UNQUALIFIED = fXIntPool.getXInt(0);
      INT_EMPTY_SET = fXIntPool.getXInt(0);
      INT_ANY_STRICT = fXIntPool.getXInt(1);
      INT_ANY_LAX = fXIntPool.getXInt(3);
      INT_ANY_SKIP = fXIntPool.getXInt(2);
      INT_ANY_ANY = fXIntPool.getXInt(1);
      INT_ANY_LIST = fXIntPool.getXInt(3);
      INT_ANY_NOT = fXIntPool.getXInt(2);
      INT_USE_OPTIONAL = fXIntPool.getXInt(0);
      INT_USE_REQUIRED = fXIntPool.getXInt(1);
      INT_USE_PROHIBITED = fXIntPool.getXInt(2);
      INT_WS_PRESERVE = fXIntPool.getXInt(0);
      INT_WS_REPLACE = fXIntPool.getXInt(1);
      INT_WS_COLLAPSE = fXIntPool.getXInt(2);
      INT_UNBOUNDED = fXIntPool.getXInt(-1);
      fEleAttrsMapG = new Hashtable(29);
      fEleAttrsMapL = new Hashtable(79);
      fExtraDVs = new XSSimpleType[9];
      SchemaGrammar.BuiltinSchemaGrammar var0 = SchemaGrammar.SG_SchemaNS;
      fExtraDVs[0] = (XSSimpleType)var0.getGlobalTypeDecl("anyURI");
      fExtraDVs[1] = (XSSimpleType)var0.getGlobalTypeDecl("ID");
      fExtraDVs[2] = (XSSimpleType)var0.getGlobalTypeDecl("QName");
      fExtraDVs[3] = (XSSimpleType)var0.getGlobalTypeDecl("string");
      fExtraDVs[4] = (XSSimpleType)var0.getGlobalTypeDecl("token");
      fExtraDVs[5] = (XSSimpleType)var0.getGlobalTypeDecl("NCName");
      fExtraDVs[6] = fExtraDVs[3];
      fExtraDVs[6] = fExtraDVs[3];
      fExtraDVs[8] = (XSSimpleType)var0.getGlobalTypeDecl("language");
      int var51 = 0;
      int var1 = var51++;
      int var2 = var51++;
      int var3 = var51++;
      int var4 = var51++;
      int var5 = var51++;
      int var6 = var51++;
      int var7 = var51++;
      int var8 = var51++;
      int var9 = var51++;
      int var10 = var51++;
      int var11 = var51++;
      int var12 = var51++;
      int var13 = var51++;
      int var14 = var51++;
      int var15 = var51++;
      int var16 = var51++;
      int var17 = var51++;
      int var18 = var51++;
      int var19 = var51++;
      int var20 = var51++;
      int var21 = var51++;
      int var22 = var51++;
      int var23 = var51++;
      int var24 = var51++;
      int var25 = var51++;
      int var26 = var51++;
      int var27 = var51++;
      int var28 = var51++;
      int var29 = var51++;
      int var30 = var51++;
      int var31 = var51++;
      int var32 = var51++;
      int var33 = var51++;
      int var34 = var51++;
      int var35 = var51++;
      int var36 = var51++;
      int var37 = var51++;
      int var38 = var51++;
      int var39 = var51++;
      int var40 = var51++;
      int var41 = var51++;
      int var42 = var51++;
      int var43 = var51++;
      int var44 = var51++;
      int var45 = var51++;
      int var46 = var51++;
      int var47 = var51++;
      int var48 = var51++;
      OneAttr[] var49 = new OneAttr[var51];
      var49[var1] = new OneAttr(SchemaSymbols.ATT_ABSTRACT, -15, ATTIDX_ABSTRACT, Boolean.FALSE);
      var49[var2] = new OneAttr(SchemaSymbols.ATT_ATTRIBUTEFORMDEFAULT, -6, ATTIDX_AFORMDEFAULT, INT_UNQUALIFIED);
      var49[var3] = new OneAttr(SchemaSymbols.ATT_BASE, 2, ATTIDX_BASE, (Object)null);
      var49[var4] = new OneAttr(SchemaSymbols.ATT_BASE, 2, ATTIDX_BASE, (Object)null);
      var49[var5] = new OneAttr(SchemaSymbols.ATT_BLOCK, -1, ATTIDX_BLOCK, (Object)null);
      var49[var6] = new OneAttr(SchemaSymbols.ATT_BLOCK, -2, ATTIDX_BLOCK, (Object)null);
      var49[var7] = new OneAttr(SchemaSymbols.ATT_BLOCKDEFAULT, -1, ATTIDX_BLOCKDEFAULT, INT_EMPTY_SET);
      var49[var8] = new OneAttr(SchemaSymbols.ATT_DEFAULT, 3, ATTIDX_DEFAULT, (Object)null);
      var49[var9] = new OneAttr(SchemaSymbols.ATT_ELEMENTFORMDEFAULT, -6, ATTIDX_EFORMDEFAULT, INT_UNQUALIFIED);
      var49[var10] = new OneAttr(SchemaSymbols.ATT_FINAL, -3, ATTIDX_FINAL, (Object)null);
      var49[var11] = new OneAttr(SchemaSymbols.ATT_FINAL, -4, ATTIDX_FINAL, (Object)null);
      var49[var12] = new OneAttr(SchemaSymbols.ATT_FINALDEFAULT, -5, ATTIDX_FINALDEFAULT, INT_EMPTY_SET);
      var49[var13] = new OneAttr(SchemaSymbols.ATT_FIXED, 3, ATTIDX_FIXED, (Object)null);
      var49[var14] = new OneAttr(SchemaSymbols.ATT_FIXED, -15, ATTIDX_FIXED, Boolean.FALSE);
      var49[var15] = new OneAttr(SchemaSymbols.ATT_FORM, -6, ATTIDX_FORM, (Object)null);
      var49[var16] = new OneAttr(SchemaSymbols.ATT_ID, 1, ATTIDX_ID, (Object)null);
      var49[var17] = new OneAttr(SchemaSymbols.ATT_ITEMTYPE, 2, ATTIDX_ITEMTYPE, (Object)null);
      var49[var18] = new OneAttr(SchemaSymbols.ATT_MAXOCCURS, -7, ATTIDX_MAXOCCURS, fXIntPool.getXInt(1));
      var49[var19] = new OneAttr(SchemaSymbols.ATT_MAXOCCURS, -8, ATTIDX_MAXOCCURS, fXIntPool.getXInt(1));
      var49[var20] = new OneAttr(SchemaSymbols.ATT_MEMBERTYPES, -9, ATTIDX_MEMBERTYPES, (Object)null);
      var49[var21] = new OneAttr(SchemaSymbols.ATT_MINOCCURS, -16, ATTIDX_MINOCCURS, fXIntPool.getXInt(1));
      var49[var22] = new OneAttr(SchemaSymbols.ATT_MINOCCURS, -10, ATTIDX_MINOCCURS, fXIntPool.getXInt(1));
      var49[var23] = new OneAttr(SchemaSymbols.ATT_MIXED, -15, ATTIDX_MIXED, Boolean.FALSE);
      var49[var24] = new OneAttr(SchemaSymbols.ATT_MIXED, -15, ATTIDX_MIXED, (Object)null);
      var49[var25] = new OneAttr(SchemaSymbols.ATT_NAME, 5, ATTIDX_NAME, (Object)null);
      var49[var26] = new OneAttr(SchemaSymbols.ATT_NAMESPACE, -11, ATTIDX_NAMESPACE, INT_ANY_ANY);
      var49[var27] = new OneAttr(SchemaSymbols.ATT_NAMESPACE, 0, ATTIDX_NAMESPACE, (Object)null);
      var49[var28] = new OneAttr(SchemaSymbols.ATT_NILLABLE, -15, ATTIDX_NILLABLE, Boolean.FALSE);
      var49[var29] = new OneAttr(SchemaSymbols.ATT_PROCESSCONTENTS, -12, ATTIDX_PROCESSCONTENTS, INT_ANY_STRICT);
      var49[var30] = new OneAttr(SchemaSymbols.ATT_PUBLIC, 4, ATTIDX_PUBLIC, (Object)null);
      var49[var31] = new OneAttr(SchemaSymbols.ATT_REF, 2, ATTIDX_REF, (Object)null);
      var49[var32] = new OneAttr(SchemaSymbols.ATT_REFER, 2, ATTIDX_REFER, (Object)null);
      var49[var33] = new OneAttr(SchemaSymbols.ATT_SCHEMALOCATION, 0, ATTIDX_SCHEMALOCATION, (Object)null);
      var49[var34] = new OneAttr(SchemaSymbols.ATT_SCHEMALOCATION, 0, ATTIDX_SCHEMALOCATION, (Object)null);
      var49[var35] = new OneAttr(SchemaSymbols.ATT_SOURCE, 0, ATTIDX_SOURCE, (Object)null);
      var49[var36] = new OneAttr(SchemaSymbols.ATT_SUBSTITUTIONGROUP, 2, ATTIDX_SUBSGROUP, (Object)null);
      var49[var37] = new OneAttr(SchemaSymbols.ATT_SYSTEM, 0, ATTIDX_SYSTEM, (Object)null);
      var49[var38] = new OneAttr(SchemaSymbols.ATT_TARGETNAMESPACE, 0, ATTIDX_TARGETNAMESPACE, (Object)null);
      var49[var39] = new OneAttr(SchemaSymbols.ATT_TYPE, 2, ATTIDX_TYPE, (Object)null);
      var49[var40] = new OneAttr(SchemaSymbols.ATT_USE, -13, ATTIDX_USE, INT_USE_OPTIONAL);
      var49[var41] = new OneAttr(SchemaSymbols.ATT_VALUE, -16, ATTIDX_VALUE, (Object)null);
      var49[var42] = new OneAttr(SchemaSymbols.ATT_VALUE, -17, ATTIDX_VALUE, (Object)null);
      var49[var43] = new OneAttr(SchemaSymbols.ATT_VALUE, 3, ATTIDX_VALUE, (Object)null);
      var49[var44] = new OneAttr(SchemaSymbols.ATT_VALUE, -14, ATTIDX_VALUE, (Object)null);
      var49[var45] = new OneAttr(SchemaSymbols.ATT_VERSION, 4, ATTIDX_VERSION, (Object)null);
      var49[var46] = new OneAttr(SchemaSymbols.ATT_XML_LANG, 8, ATTIDX_XML_LANG, (Object)null);
      var49[var47] = new OneAttr(SchemaSymbols.ATT_XPATH, 6, ATTIDX_XPATH, (Object)null);
      var49[var48] = new OneAttr(SchemaSymbols.ATT_XPATH, 7, ATTIDX_XPATH, (Object)null);
      Container var50 = Container.getContainer(5);
      var50.put(SchemaSymbols.ATT_DEFAULT, var49[var8]);
      var50.put(SchemaSymbols.ATT_FIXED, var49[var13]);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_NAME, var49[var25]);
      var50.put(SchemaSymbols.ATT_TYPE, var49[var39]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_ATTRIBUTE, var50);
      var50 = Container.getContainer(7);
      var50.put(SchemaSymbols.ATT_DEFAULT, var49[var8]);
      var50.put(SchemaSymbols.ATT_FIXED, var49[var13]);
      var50.put(SchemaSymbols.ATT_FORM, var49[var15]);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_NAME, var49[var25]);
      var50.put(SchemaSymbols.ATT_TYPE, var49[var39]);
      var50.put(SchemaSymbols.ATT_USE, var49[var40]);
      fEleAttrsMapL.put("attribute_n", var50);
      var50 = Container.getContainer(5);
      var50.put(SchemaSymbols.ATT_DEFAULT, var49[var8]);
      var50.put(SchemaSymbols.ATT_FIXED, var49[var13]);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_REF, var49[var31]);
      var50.put(SchemaSymbols.ATT_USE, var49[var40]);
      fEleAttrsMapL.put("attribute_r", var50);
      var50 = Container.getContainer(10);
      var50.put(SchemaSymbols.ATT_ABSTRACT, var49[var1]);
      var50.put(SchemaSymbols.ATT_BLOCK, var49[var5]);
      var50.put(SchemaSymbols.ATT_DEFAULT, var49[var8]);
      var50.put(SchemaSymbols.ATT_FINAL, var49[var10]);
      var50.put(SchemaSymbols.ATT_FIXED, var49[var13]);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_NAME, var49[var25]);
      var50.put(SchemaSymbols.ATT_NILLABLE, var49[var28]);
      var50.put(SchemaSymbols.ATT_SUBSTITUTIONGROUP, var49[var36]);
      var50.put(SchemaSymbols.ATT_TYPE, var49[var39]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_ELEMENT, var50);
      var50 = Container.getContainer(10);
      var50.put(SchemaSymbols.ATT_BLOCK, var49[var5]);
      var50.put(SchemaSymbols.ATT_DEFAULT, var49[var8]);
      var50.put(SchemaSymbols.ATT_FIXED, var49[var13]);
      var50.put(SchemaSymbols.ATT_FORM, var49[var15]);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_MAXOCCURS, var49[var18]);
      var50.put(SchemaSymbols.ATT_MINOCCURS, var49[var21]);
      var50.put(SchemaSymbols.ATT_NAME, var49[var25]);
      var50.put(SchemaSymbols.ATT_NILLABLE, var49[var28]);
      var50.put(SchemaSymbols.ATT_TYPE, var49[var39]);
      fEleAttrsMapL.put("element_n", var50);
      var50 = Container.getContainer(4);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_MAXOCCURS, var49[var18]);
      var50.put(SchemaSymbols.ATT_MINOCCURS, var49[var21]);
      var50.put(SchemaSymbols.ATT_REF, var49[var31]);
      fEleAttrsMapL.put("element_r", var50);
      var50 = Container.getContainer(6);
      var50.put(SchemaSymbols.ATT_ABSTRACT, var49[var1]);
      var50.put(SchemaSymbols.ATT_BLOCK, var49[var6]);
      var50.put(SchemaSymbols.ATT_FINAL, var49[var10]);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_MIXED, var49[var23]);
      var50.put(SchemaSymbols.ATT_NAME, var49[var25]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_COMPLEXTYPE, var50);
      var50 = Container.getContainer(4);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_NAME, var49[var25]);
      var50.put(SchemaSymbols.ATT_PUBLIC, var49[var30]);
      var50.put(SchemaSymbols.ATT_SYSTEM, var49[var37]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_NOTATION, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_MIXED, var49[var23]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_COMPLEXTYPE, var50);
      var50 = Container.getContainer(1);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_SIMPLECONTENT, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_BASE, var49[var4]);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_RESTRICTION, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_BASE, var49[var3]);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_EXTENSION, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_REF, var49[var31]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_ATTRIBUTEGROUP, var50);
      var50 = Container.getContainer(3);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_NAMESPACE, var49[var26]);
      var50.put(SchemaSymbols.ATT_PROCESSCONTENTS, var49[var29]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_ANYATTRIBUTE, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_MIXED, var49[var24]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_COMPLEXCONTENT, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_NAME, var49[var25]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_ATTRIBUTEGROUP, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_NAME, var49[var25]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_GROUP, var50);
      var50 = Container.getContainer(4);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_MAXOCCURS, var49[var18]);
      var50.put(SchemaSymbols.ATT_MINOCCURS, var49[var21]);
      var50.put(SchemaSymbols.ATT_REF, var49[var31]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_GROUP, var50);
      var50 = Container.getContainer(3);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_MAXOCCURS, var49[var19]);
      var50.put(SchemaSymbols.ATT_MINOCCURS, var49[var22]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_ALL, var50);
      var50 = Container.getContainer(3);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_MAXOCCURS, var49[var18]);
      var50.put(SchemaSymbols.ATT_MINOCCURS, var49[var21]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_CHOICE, var50);
      fEleAttrsMapL.put(SchemaSymbols.ELT_SEQUENCE, var50);
      var50 = Container.getContainer(5);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_MAXOCCURS, var49[var18]);
      var50.put(SchemaSymbols.ATT_MINOCCURS, var49[var21]);
      var50.put(SchemaSymbols.ATT_NAMESPACE, var49[var26]);
      var50.put(SchemaSymbols.ATT_PROCESSCONTENTS, var49[var29]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_ANY, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_NAME, var49[var25]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_UNIQUE, var50);
      fEleAttrsMapL.put(SchemaSymbols.ELT_KEY, var50);
      var50 = Container.getContainer(3);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_NAME, var49[var25]);
      var50.put(SchemaSymbols.ATT_REFER, var49[var32]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_KEYREF, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_XPATH, var49[var47]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_SELECTOR, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_XPATH, var49[var48]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_FIELD, var50);
      var50 = Container.getContainer(1);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_ANNOTATION, var50);
      fEleAttrsMapL.put(SchemaSymbols.ELT_ANNOTATION, var50);
      var50 = Container.getContainer(1);
      var50.put(SchemaSymbols.ATT_SOURCE, var49[var35]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_APPINFO, var50);
      fEleAttrsMapL.put(SchemaSymbols.ELT_APPINFO, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_SOURCE, var49[var35]);
      var50.put(SchemaSymbols.ATT_XML_LANG, var49[var46]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_DOCUMENTATION, var50);
      fEleAttrsMapL.put(SchemaSymbols.ELT_DOCUMENTATION, var50);
      var50 = Container.getContainer(3);
      var50.put(SchemaSymbols.ATT_FINAL, var49[var11]);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_NAME, var49[var25]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_SIMPLETYPE, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_FINAL, var49[var11]);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_SIMPLETYPE, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_ITEMTYPE, var49[var17]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_LIST, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_MEMBERTYPES, var49[var20]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_UNION, var50);
      var50 = Container.getContainer(8);
      var50.put(SchemaSymbols.ATT_ATTRIBUTEFORMDEFAULT, var49[var2]);
      var50.put(SchemaSymbols.ATT_BLOCKDEFAULT, var49[var7]);
      var50.put(SchemaSymbols.ATT_ELEMENTFORMDEFAULT, var49[var9]);
      var50.put(SchemaSymbols.ATT_FINALDEFAULT, var49[var12]);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_TARGETNAMESPACE, var49[var38]);
      var50.put(SchemaSymbols.ATT_VERSION, var49[var45]);
      var50.put(SchemaSymbols.ATT_XML_LANG, var49[var46]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_SCHEMA, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_SCHEMALOCATION, var49[var33]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_INCLUDE, var50);
      fEleAttrsMapG.put(SchemaSymbols.ELT_REDEFINE, var50);
      var50 = Container.getContainer(3);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_NAMESPACE, var49[var27]);
      var50.put(SchemaSymbols.ATT_SCHEMALOCATION, var49[var34]);
      fEleAttrsMapG.put(SchemaSymbols.ELT_IMPORT, var50);
      var50 = Container.getContainer(3);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_VALUE, var49[var41]);
      var50.put(SchemaSymbols.ATT_FIXED, var49[var14]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_LENGTH, var50);
      fEleAttrsMapL.put(SchemaSymbols.ELT_MINLENGTH, var50);
      fEleAttrsMapL.put(SchemaSymbols.ELT_MAXLENGTH, var50);
      fEleAttrsMapL.put(SchemaSymbols.ELT_FRACTIONDIGITS, var50);
      var50 = Container.getContainer(3);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_VALUE, var49[var42]);
      var50.put(SchemaSymbols.ATT_FIXED, var49[var14]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_TOTALDIGITS, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_VALUE, var49[var43]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_PATTERN, var50);
      var50 = Container.getContainer(2);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_VALUE, var49[var43]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_ENUMERATION, var50);
      var50 = Container.getContainer(3);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_VALUE, var49[var44]);
      var50.put(SchemaSymbols.ATT_FIXED, var49[var14]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_WHITESPACE, var50);
      var50 = Container.getContainer(3);
      var50.put(SchemaSymbols.ATT_ID, var49[var16]);
      var50.put(SchemaSymbols.ATT_VALUE, var49[var43]);
      var50.put(SchemaSymbols.ATT_FIXED, var49[var14]);
      fEleAttrsMapL.put(SchemaSymbols.ELT_MAXINCLUSIVE, var50);
      fEleAttrsMapL.put(SchemaSymbols.ELT_MAXEXCLUSIVE, var50);
      fEleAttrsMapL.put(SchemaSymbols.ELT_MININCLUSIVE, var50);
      fEleAttrsMapL.put(SchemaSymbols.ELT_MINEXCLUSIVE, var50);
      fSeenTemp = new boolean[ATTIDX_COUNT];
      fTempArray = new Object[ATTIDX_COUNT];
   }
}
