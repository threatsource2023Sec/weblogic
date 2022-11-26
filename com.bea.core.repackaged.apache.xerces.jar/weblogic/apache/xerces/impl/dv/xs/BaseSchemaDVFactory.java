package weblogic.apache.xerces.impl.dv.xs;

import weblogic.apache.xerces.impl.dv.SchemaDVFactory;
import weblogic.apache.xerces.impl.dv.XSFacets;
import weblogic.apache.xerces.impl.dv.XSSimpleType;
import weblogic.apache.xerces.impl.xs.XSDeclarationPool;
import weblogic.apache.xerces.util.SymbolHash;
import weblogic.apache.xerces.xs.XSObjectList;

public abstract class BaseSchemaDVFactory extends SchemaDVFactory {
   static final String URI_SCHEMAFORSCHEMA = "http://www.w3.org/2001/XMLSchema";
   protected XSDeclarationPool fDeclPool = null;

   protected static void createBuiltInTypes(SymbolHash var0, XSSimpleTypeDecl var1) {
      XSFacets var47 = new XSFacets();
      var0.put("anySimpleType", XSSimpleTypeDecl.fAnySimpleType);
      XSSimpleTypeDecl var48 = new XSSimpleTypeDecl(var1, "string", (short)1, (short)0, false, false, false, true, (short)2);
      var0.put("string", var48);
      var0.put("boolean", new XSSimpleTypeDecl(var1, "boolean", (short)2, (short)0, false, true, false, true, (short)3));
      XSSimpleTypeDecl var49 = new XSSimpleTypeDecl(var1, "decimal", (short)3, (short)2, false, false, true, true, (short)4);
      var0.put("decimal", var49);
      var0.put("anyURI", new XSSimpleTypeDecl(var1, "anyURI", (short)17, (short)0, false, false, false, true, (short)18));
      var0.put("base64Binary", new XSSimpleTypeDecl(var1, "base64Binary", (short)16, (short)0, false, false, false, true, (short)17));
      XSSimpleTypeDecl var50 = new XSSimpleTypeDecl(var1, "duration", (short)6, (short)1, false, false, false, true, (short)7);
      var0.put("duration", var50);
      var0.put("dateTime", new XSSimpleTypeDecl(var1, "dateTime", (short)7, (short)1, false, false, false, true, (short)8));
      var0.put("time", new XSSimpleTypeDecl(var1, "time", (short)8, (short)1, false, false, false, true, (short)9));
      var0.put("date", new XSSimpleTypeDecl(var1, "date", (short)9, (short)1, false, false, false, true, (short)10));
      var0.put("gYearMonth", new XSSimpleTypeDecl(var1, "gYearMonth", (short)10, (short)1, false, false, false, true, (short)11));
      var0.put("gYear", new XSSimpleTypeDecl(var1, "gYear", (short)11, (short)1, false, false, false, true, (short)12));
      var0.put("gMonthDay", new XSSimpleTypeDecl(var1, "gMonthDay", (short)12, (short)1, false, false, false, true, (short)13));
      var0.put("gDay", new XSSimpleTypeDecl(var1, "gDay", (short)13, (short)1, false, false, false, true, (short)14));
      var0.put("gMonth", new XSSimpleTypeDecl(var1, "gMonth", (short)14, (short)1, false, false, false, true, (short)15));
      XSSimpleTypeDecl var51 = new XSSimpleTypeDecl(var49, "integer", (short)24, (short)2, false, false, true, true, (short)30);
      var0.put("integer", var51);
      var47.maxInclusive = "0";
      XSSimpleTypeDecl var52 = new XSSimpleTypeDecl(var51, "nonPositiveInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)31);
      var52.applyFacets1(var47, (short)32, (short)0);
      var0.put("nonPositiveInteger", var52);
      var47.maxInclusive = "-1";
      XSSimpleTypeDecl var53 = new XSSimpleTypeDecl(var52, "negativeInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)32);
      var53.applyFacets1(var47, (short)32, (short)0);
      var0.put("negativeInteger", var53);
      var47.maxInclusive = "9223372036854775807";
      var47.minInclusive = "-9223372036854775808";
      XSSimpleTypeDecl var54 = new XSSimpleTypeDecl(var51, "long", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)33);
      var54.applyFacets1(var47, (short)288, (short)0);
      var0.put("long", var54);
      var47.maxInclusive = "2147483647";
      var47.minInclusive = "-2147483648";
      XSSimpleTypeDecl var55 = new XSSimpleTypeDecl(var54, "int", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)34);
      var55.applyFacets1(var47, (short)288, (short)0);
      var0.put("int", var55);
      var47.maxInclusive = "32767";
      var47.minInclusive = "-32768";
      XSSimpleTypeDecl var56 = new XSSimpleTypeDecl(var55, "short", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)35);
      var56.applyFacets1(var47, (short)288, (short)0);
      var0.put("short", var56);
      var47.maxInclusive = "127";
      var47.minInclusive = "-128";
      XSSimpleTypeDecl var57 = new XSSimpleTypeDecl(var56, "byte", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)36);
      var57.applyFacets1(var47, (short)288, (short)0);
      var0.put("byte", var57);
      var47.minInclusive = "0";
      XSSimpleTypeDecl var58 = new XSSimpleTypeDecl(var51, "nonNegativeInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)37);
      var58.applyFacets1(var47, (short)256, (short)0);
      var0.put("nonNegativeInteger", var58);
      var47.maxInclusive = "18446744073709551615";
      XSSimpleTypeDecl var59 = new XSSimpleTypeDecl(var58, "unsignedLong", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)38);
      var59.applyFacets1(var47, (short)32, (short)0);
      var0.put("unsignedLong", var59);
      var47.maxInclusive = "4294967295";
      XSSimpleTypeDecl var60 = new XSSimpleTypeDecl(var59, "unsignedInt", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)39);
      var60.applyFacets1(var47, (short)32, (short)0);
      var0.put("unsignedInt", var60);
      var47.maxInclusive = "65535";
      XSSimpleTypeDecl var61 = new XSSimpleTypeDecl(var60, "unsignedShort", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)40);
      var61.applyFacets1(var47, (short)32, (short)0);
      var0.put("unsignedShort", var61);
      var47.maxInclusive = "255";
      XSSimpleTypeDecl var62 = new XSSimpleTypeDecl(var61, "unsignedByte", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)41);
      var62.applyFacets1(var47, (short)32, (short)0);
      var0.put("unsignedByte", var62);
      var47.minInclusive = "1";
      XSSimpleTypeDecl var63 = new XSSimpleTypeDecl(var58, "positiveInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)42);
      var63.applyFacets1(var47, (short)256, (short)0);
      var0.put("positiveInteger", var63);
      var0.put("float", new XSSimpleTypeDecl(var1, "float", (short)4, (short)1, true, true, true, true, (short)5));
      var0.put("double", new XSSimpleTypeDecl(var1, "double", (short)5, (short)1, true, true, true, true, (short)6));
      var0.put("hexBinary", new XSSimpleTypeDecl(var1, "hexBinary", (short)15, (short)0, false, false, false, true, (short)16));
      var0.put("NOTATION", new XSSimpleTypeDecl(var1, "NOTATION", (short)20, (short)0, false, false, false, true, (short)20));
      var47.whiteSpace = 1;
      XSSimpleTypeDecl var64 = new XSSimpleTypeDecl(var48, "normalizedString", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)21);
      var64.applyFacets1(var47, (short)16, (short)0);
      var0.put("normalizedString", var64);
      var47.whiteSpace = 2;
      XSSimpleTypeDecl var65 = new XSSimpleTypeDecl(var64, "token", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)22);
      var65.applyFacets1(var47, (short)16, (short)0);
      var0.put("token", var65);
      var47.whiteSpace = 2;
      var47.pattern = "([a-zA-Z]{1,8})(-[a-zA-Z0-9]{1,8})*";
      XSSimpleTypeDecl var66 = new XSSimpleTypeDecl(var65, "language", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)23);
      var66.applyFacets1(var47, (short)24, (short)0);
      var0.put("language", var66);
      var47.whiteSpace = 2;
      XSSimpleTypeDecl var67 = new XSSimpleTypeDecl(var65, "Name", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)25);
      var67.applyFacets1(var47, (short)16, (short)0, (short)2);
      var0.put("Name", var67);
      var47.whiteSpace = 2;
      XSSimpleTypeDecl var68 = new XSSimpleTypeDecl(var67, "NCName", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)26);
      var68.applyFacets1(var47, (short)16, (short)0, (short)3);
      var0.put("NCName", var68);
      var0.put("QName", new XSSimpleTypeDecl(var1, "QName", (short)18, (short)0, false, false, false, true, (short)19));
      var0.put("ID", new XSSimpleTypeDecl(var68, "ID", (short)21, (short)0, false, false, false, true, (short)27));
      XSSimpleTypeDecl var69 = new XSSimpleTypeDecl(var68, "IDREF", (short)22, (short)0, false, false, false, true, (short)28);
      var0.put("IDREF", var69);
      var47.minLength = 1;
      XSSimpleTypeDecl var70 = new XSSimpleTypeDecl((String)null, "http://www.w3.org/2001/XMLSchema", (short)0, var69, true, (XSObjectList)null);
      XSSimpleTypeDecl var71 = new XSSimpleTypeDecl(var70, "IDREFS", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null);
      var71.applyFacets1(var47, (short)2, (short)0);
      var0.put("IDREFS", var71);
      XSSimpleTypeDecl var72 = new XSSimpleTypeDecl(var68, "ENTITY", (short)23, (short)0, false, false, false, true, (short)29);
      var0.put("ENTITY", var72);
      var47.minLength = 1;
      var70 = new XSSimpleTypeDecl((String)null, "http://www.w3.org/2001/XMLSchema", (short)0, var72, true, (XSObjectList)null);
      XSSimpleTypeDecl var73 = new XSSimpleTypeDecl(var70, "ENTITIES", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null);
      var73.applyFacets1(var47, (short)2, (short)0);
      var0.put("ENTITIES", var73);
      var47.whiteSpace = 2;
      XSSimpleTypeDecl var74 = new XSSimpleTypeDecl(var65, "NMTOKEN", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)24);
      var74.applyFacets1(var47, (short)16, (short)0, (short)1);
      var0.put("NMTOKEN", var74);
      var47.minLength = 1;
      var70 = new XSSimpleTypeDecl((String)null, "http://www.w3.org/2001/XMLSchema", (short)0, var74, true, (XSObjectList)null);
      XSSimpleTypeDecl var75 = new XSSimpleTypeDecl(var70, "NMTOKENS", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null);
      var75.applyFacets1(var47, (short)2, (short)0);
      var0.put("NMTOKENS", var75);
   }

   public XSSimpleType createTypeRestriction(String var1, String var2, short var3, XSSimpleType var4, XSObjectList var5) {
      if (this.fDeclPool != null) {
         XSSimpleTypeDecl var6 = this.fDeclPool.getSimpleTypeDecl();
         return var6.setRestrictionValues((XSSimpleTypeDecl)var4, var1, var2, var3, var5);
      } else {
         return new XSSimpleTypeDecl((XSSimpleTypeDecl)var4, var1, var2, var3, false, var5);
      }
   }

   public XSSimpleType createTypeList(String var1, String var2, short var3, XSSimpleType var4, XSObjectList var5) {
      if (this.fDeclPool != null) {
         XSSimpleTypeDecl var6 = this.fDeclPool.getSimpleTypeDecl();
         return var6.setListValues(var1, var2, var3, (XSSimpleTypeDecl)var4, var5);
      } else {
         return new XSSimpleTypeDecl(var1, var2, var3, (XSSimpleTypeDecl)var4, false, var5);
      }
   }

   public XSSimpleType createTypeUnion(String var1, String var2, short var3, XSSimpleType[] var4, XSObjectList var5) {
      int var6 = var4.length;
      XSSimpleTypeDecl[] var7 = new XSSimpleTypeDecl[var6];
      System.arraycopy(var4, 0, var7, 0, var6);
      if (this.fDeclPool != null) {
         XSSimpleTypeDecl var8 = this.fDeclPool.getSimpleTypeDecl();
         return var8.setUnionValues(var1, var2, var3, var7, var5);
      } else {
         return new XSSimpleTypeDecl(var1, var2, var3, var7, var5);
      }
   }

   public void setDeclPool(XSDeclarationPool var1) {
      this.fDeclPool = var1;
   }

   public XSSimpleTypeDecl newXSSimpleTypeDecl() {
      return new XSSimpleTypeDecl();
   }
}
