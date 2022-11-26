package org.python.apache.xerces.impl.dv.xs;

import org.python.apache.xerces.impl.dv.SchemaDVFactory;
import org.python.apache.xerces.impl.dv.XSFacets;
import org.python.apache.xerces.impl.dv.XSSimpleType;
import org.python.apache.xerces.util.SymbolHash;
import org.python.apache.xerces.xs.XSObjectList;

public class BaseDVFactory extends SchemaDVFactory {
   static final String URI_SCHEMAFORSCHEMA = "http://www.w3.org/2001/XMLSchema";
   static SymbolHash fBaseTypes = new SymbolHash(53);

   public XSSimpleType getBuiltInType(String var1) {
      return (XSSimpleType)fBaseTypes.get(var1);
   }

   public SymbolHash getBuiltInTypes() {
      return fBaseTypes.makeClone();
   }

   public XSSimpleType createTypeRestriction(String var1, String var2, short var3, XSSimpleType var4, XSObjectList var5) {
      return new XSSimpleTypeDecl((XSSimpleTypeDecl)var4, var1, var2, var3, false, var5);
   }

   public XSSimpleType createTypeList(String var1, String var2, short var3, XSSimpleType var4, XSObjectList var5) {
      return new XSSimpleTypeDecl(var1, var2, var3, (XSSimpleTypeDecl)var4, false, var5);
   }

   public XSSimpleType createTypeUnion(String var1, String var2, short var3, XSSimpleType[] var4, XSObjectList var5) {
      int var6 = var4.length;
      XSSimpleTypeDecl[] var7 = new XSSimpleTypeDecl[var6];
      System.arraycopy(var4, 0, var7, 0, var6);
      return new XSSimpleTypeDecl(var1, var2, var3, var7, var5);
   }

   static void createBuiltInTypes(SymbolHash var0) {
      XSFacets var1 = new XSFacets();
      XSSimpleTypeDecl var2 = XSSimpleTypeDecl.fAnySimpleType;
      var0.put("anySimpleType", var2);
      XSSimpleTypeDecl var3 = new XSSimpleTypeDecl(var2, "string", (short)1, (short)0, false, false, false, true, (short)2);
      var0.put("string", var3);
      var0.put("boolean", new XSSimpleTypeDecl(var2, "boolean", (short)2, (short)0, false, true, false, true, (short)3));
      XSSimpleTypeDecl var4 = new XSSimpleTypeDecl(var2, "decimal", (short)3, (short)2, false, false, true, true, (short)4);
      var0.put("decimal", var4);
      var0.put("anyURI", new XSSimpleTypeDecl(var2, "anyURI", (short)17, (short)0, false, false, false, true, (short)18));
      var0.put("base64Binary", new XSSimpleTypeDecl(var2, "base64Binary", (short)16, (short)0, false, false, false, true, (short)17));
      var0.put("dateTime", new XSSimpleTypeDecl(var2, "dateTime", (short)7, (short)1, false, false, false, true, (short)8));
      var0.put("time", new XSSimpleTypeDecl(var2, "time", (short)8, (short)1, false, false, false, true, (short)9));
      var0.put("date", new XSSimpleTypeDecl(var2, "date", (short)9, (short)1, false, false, false, true, (short)10));
      var0.put("gYearMonth", new XSSimpleTypeDecl(var2, "gYearMonth", (short)10, (short)1, false, false, false, true, (short)11));
      var0.put("gYear", new XSSimpleTypeDecl(var2, "gYear", (short)11, (short)1, false, false, false, true, (short)12));
      var0.put("gMonthDay", new XSSimpleTypeDecl(var2, "gMonthDay", (short)12, (short)1, false, false, false, true, (short)13));
      var0.put("gDay", new XSSimpleTypeDecl(var2, "gDay", (short)13, (short)1, false, false, false, true, (short)14));
      var0.put("gMonth", new XSSimpleTypeDecl(var2, "gMonth", (short)14, (short)1, false, false, false, true, (short)15));
      XSSimpleTypeDecl var5 = new XSSimpleTypeDecl(var4, "integer", (short)24, (short)2, false, false, true, true, (short)30);
      var0.put("integer", var5);
      var1.maxInclusive = "0";
      XSSimpleTypeDecl var6 = new XSSimpleTypeDecl(var5, "nonPositiveInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)31);
      var6.applyFacets1(var1, (short)32, (short)0);
      var0.put("nonPositiveInteger", var6);
      var1.maxInclusive = "-1";
      XSSimpleTypeDecl var7 = new XSSimpleTypeDecl(var6, "negativeInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)32);
      var7.applyFacets1(var1, (short)32, (short)0);
      var0.put("negativeInteger", var7);
      var1.maxInclusive = "9223372036854775807";
      var1.minInclusive = "-9223372036854775808";
      XSSimpleTypeDecl var8 = new XSSimpleTypeDecl(var5, "long", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)33);
      var8.applyFacets1(var1, (short)288, (short)0);
      var0.put("long", var8);
      var1.maxInclusive = "2147483647";
      var1.minInclusive = "-2147483648";
      XSSimpleTypeDecl var9 = new XSSimpleTypeDecl(var8, "int", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)34);
      var9.applyFacets1(var1, (short)288, (short)0);
      var0.put("int", var9);
      var1.maxInclusive = "32767";
      var1.minInclusive = "-32768";
      XSSimpleTypeDecl var10 = new XSSimpleTypeDecl(var9, "short", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)35);
      var10.applyFacets1(var1, (short)288, (short)0);
      var0.put("short", var10);
      var1.maxInclusive = "127";
      var1.minInclusive = "-128";
      XSSimpleTypeDecl var11 = new XSSimpleTypeDecl(var10, "byte", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)36);
      var11.applyFacets1(var1, (short)288, (short)0);
      var0.put("byte", var11);
      var1.minInclusive = "0";
      XSSimpleTypeDecl var12 = new XSSimpleTypeDecl(var5, "nonNegativeInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)37);
      var12.applyFacets1(var1, (short)256, (short)0);
      var0.put("nonNegativeInteger", var12);
      var1.maxInclusive = "18446744073709551615";
      XSSimpleTypeDecl var13 = new XSSimpleTypeDecl(var12, "unsignedLong", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)38);
      var13.applyFacets1(var1, (short)32, (short)0);
      var0.put("unsignedLong", var13);
      var1.maxInclusive = "4294967295";
      XSSimpleTypeDecl var14 = new XSSimpleTypeDecl(var13, "unsignedInt", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)39);
      var14.applyFacets1(var1, (short)32, (short)0);
      var0.put("unsignedInt", var14);
      var1.maxInclusive = "65535";
      XSSimpleTypeDecl var15 = new XSSimpleTypeDecl(var14, "unsignedShort", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)40);
      var15.applyFacets1(var1, (short)32, (short)0);
      var0.put("unsignedShort", var15);
      var1.maxInclusive = "255";
      XSSimpleTypeDecl var16 = new XSSimpleTypeDecl(var15, "unsignedByte", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)41);
      var16.applyFacets1(var1, (short)32, (short)0);
      var0.put("unsignedByte", var16);
      var1.minInclusive = "1";
      XSSimpleTypeDecl var17 = new XSSimpleTypeDecl(var12, "positiveInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)42);
      var17.applyFacets1(var1, (short)256, (short)0);
      var0.put("positiveInteger", var17);
   }

   static {
      createBuiltInTypes(fBaseTypes);
   }
}
