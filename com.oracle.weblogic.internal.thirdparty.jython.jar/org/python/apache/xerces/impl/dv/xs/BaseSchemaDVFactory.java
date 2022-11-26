package org.python.apache.xerces.impl.dv.xs;

import org.python.apache.xerces.impl.dv.SchemaDVFactory;
import org.python.apache.xerces.impl.dv.XSFacets;
import org.python.apache.xerces.impl.dv.XSSimpleType;
import org.python.apache.xerces.impl.xs.XSDeclarationPool;
import org.python.apache.xerces.util.SymbolHash;
import org.python.apache.xerces.xs.XSObjectList;

public abstract class BaseSchemaDVFactory extends SchemaDVFactory {
   static final String URI_SCHEMAFORSCHEMA = "http://www.w3.org/2001/XMLSchema";
   protected XSDeclarationPool fDeclPool = null;

   protected static void createBuiltInTypes(SymbolHash var0, XSSimpleTypeDecl var1) {
      XSFacets var2 = new XSFacets();
      var0.put("anySimpleType", XSSimpleTypeDecl.fAnySimpleType);
      XSSimpleTypeDecl var3 = new XSSimpleTypeDecl(var1, "string", (short)1, (short)0, false, false, false, true, (short)2);
      var0.put("string", var3);
      var0.put("boolean", new XSSimpleTypeDecl(var1, "boolean", (short)2, (short)0, false, true, false, true, (short)3));
      XSSimpleTypeDecl var4 = new XSSimpleTypeDecl(var1, "decimal", (short)3, (short)2, false, false, true, true, (short)4);
      var0.put("decimal", var4);
      var0.put("anyURI", new XSSimpleTypeDecl(var1, "anyURI", (short)17, (short)0, false, false, false, true, (short)18));
      var0.put("base64Binary", new XSSimpleTypeDecl(var1, "base64Binary", (short)16, (short)0, false, false, false, true, (short)17));
      XSSimpleTypeDecl var5 = new XSSimpleTypeDecl(var1, "duration", (short)6, (short)1, false, false, false, true, (short)7);
      var0.put("duration", var5);
      var0.put("dateTime", new XSSimpleTypeDecl(var1, "dateTime", (short)7, (short)1, false, false, false, true, (short)8));
      var0.put("time", new XSSimpleTypeDecl(var1, "time", (short)8, (short)1, false, false, false, true, (short)9));
      var0.put("date", new XSSimpleTypeDecl(var1, "date", (short)9, (short)1, false, false, false, true, (short)10));
      var0.put("gYearMonth", new XSSimpleTypeDecl(var1, "gYearMonth", (short)10, (short)1, false, false, false, true, (short)11));
      var0.put("gYear", new XSSimpleTypeDecl(var1, "gYear", (short)11, (short)1, false, false, false, true, (short)12));
      var0.put("gMonthDay", new XSSimpleTypeDecl(var1, "gMonthDay", (short)12, (short)1, false, false, false, true, (short)13));
      var0.put("gDay", new XSSimpleTypeDecl(var1, "gDay", (short)13, (short)1, false, false, false, true, (short)14));
      var0.put("gMonth", new XSSimpleTypeDecl(var1, "gMonth", (short)14, (short)1, false, false, false, true, (short)15));
      XSSimpleTypeDecl var6 = new XSSimpleTypeDecl(var4, "integer", (short)24, (short)2, false, false, true, true, (short)30);
      var0.put("integer", var6);
      var2.maxInclusive = "0";
      XSSimpleTypeDecl var7 = new XSSimpleTypeDecl(var6, "nonPositiveInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)31);
      var7.applyFacets1(var2, (short)32, (short)0);
      var0.put("nonPositiveInteger", var7);
      var2.maxInclusive = "-1";
      XSSimpleTypeDecl var8 = new XSSimpleTypeDecl(var7, "negativeInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)32);
      var8.applyFacets1(var2, (short)32, (short)0);
      var0.put("negativeInteger", var8);
      var2.maxInclusive = "9223372036854775807";
      var2.minInclusive = "-9223372036854775808";
      XSSimpleTypeDecl var9 = new XSSimpleTypeDecl(var6, "long", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)33);
      var9.applyFacets1(var2, (short)288, (short)0);
      var0.put("long", var9);
      var2.maxInclusive = "2147483647";
      var2.minInclusive = "-2147483648";
      XSSimpleTypeDecl var10 = new XSSimpleTypeDecl(var9, "int", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)34);
      var10.applyFacets1(var2, (short)288, (short)0);
      var0.put("int", var10);
      var2.maxInclusive = "32767";
      var2.minInclusive = "-32768";
      XSSimpleTypeDecl var11 = new XSSimpleTypeDecl(var10, "short", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)35);
      var11.applyFacets1(var2, (short)288, (short)0);
      var0.put("short", var11);
      var2.maxInclusive = "127";
      var2.minInclusive = "-128";
      XSSimpleTypeDecl var12 = new XSSimpleTypeDecl(var11, "byte", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)36);
      var12.applyFacets1(var2, (short)288, (short)0);
      var0.put("byte", var12);
      var2.minInclusive = "0";
      XSSimpleTypeDecl var13 = new XSSimpleTypeDecl(var6, "nonNegativeInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)37);
      var13.applyFacets1(var2, (short)256, (short)0);
      var0.put("nonNegativeInteger", var13);
      var2.maxInclusive = "18446744073709551615";
      XSSimpleTypeDecl var14 = new XSSimpleTypeDecl(var13, "unsignedLong", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)38);
      var14.applyFacets1(var2, (short)32, (short)0);
      var0.put("unsignedLong", var14);
      var2.maxInclusive = "4294967295";
      XSSimpleTypeDecl var15 = new XSSimpleTypeDecl(var14, "unsignedInt", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)39);
      var15.applyFacets1(var2, (short)32, (short)0);
      var0.put("unsignedInt", var15);
      var2.maxInclusive = "65535";
      XSSimpleTypeDecl var16 = new XSSimpleTypeDecl(var15, "unsignedShort", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)40);
      var16.applyFacets1(var2, (short)32, (short)0);
      var0.put("unsignedShort", var16);
      var2.maxInclusive = "255";
      XSSimpleTypeDecl var17 = new XSSimpleTypeDecl(var16, "unsignedByte", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)41);
      var17.applyFacets1(var2, (short)32, (short)0);
      var0.put("unsignedByte", var17);
      var2.minInclusive = "1";
      XSSimpleTypeDecl var18 = new XSSimpleTypeDecl(var13, "positiveInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)42);
      var18.applyFacets1(var2, (short)256, (short)0);
      var0.put("positiveInteger", var18);
      var0.put("float", new XSSimpleTypeDecl(var1, "float", (short)4, (short)1, true, true, true, true, (short)5));
      var0.put("double", new XSSimpleTypeDecl(var1, "double", (short)5, (short)1, true, true, true, true, (short)6));
      var0.put("hexBinary", new XSSimpleTypeDecl(var1, "hexBinary", (short)15, (short)0, false, false, false, true, (short)16));
      var0.put("NOTATION", new XSSimpleTypeDecl(var1, "NOTATION", (short)20, (short)0, false, false, false, true, (short)20));
      var2.whiteSpace = 1;
      XSSimpleTypeDecl var19 = new XSSimpleTypeDecl(var3, "normalizedString", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)21);
      var19.applyFacets1(var2, (short)16, (short)0);
      var0.put("normalizedString", var19);
      var2.whiteSpace = 2;
      XSSimpleTypeDecl var20 = new XSSimpleTypeDecl(var19, "token", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)22);
      var20.applyFacets1(var2, (short)16, (short)0);
      var0.put("token", var20);
      var2.whiteSpace = 2;
      var2.pattern = "([a-zA-Z]{1,8})(-[a-zA-Z0-9]{1,8})*";
      XSSimpleTypeDecl var21 = new XSSimpleTypeDecl(var20, "language", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)23);
      var21.applyFacets1(var2, (short)24, (short)0);
      var0.put("language", var21);
      var2.whiteSpace = 2;
      XSSimpleTypeDecl var22 = new XSSimpleTypeDecl(var20, "Name", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)25);
      var22.applyFacets1(var2, (short)16, (short)0, (short)2);
      var0.put("Name", var22);
      var2.whiteSpace = 2;
      XSSimpleTypeDecl var23 = new XSSimpleTypeDecl(var22, "NCName", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)26);
      var23.applyFacets1(var2, (short)16, (short)0, (short)3);
      var0.put("NCName", var23);
      var0.put("QName", new XSSimpleTypeDecl(var1, "QName", (short)18, (short)0, false, false, false, true, (short)19));
      var0.put("ID", new XSSimpleTypeDecl(var23, "ID", (short)21, (short)0, false, false, false, true, (short)27));
      XSSimpleTypeDecl var24 = new XSSimpleTypeDecl(var23, "IDREF", (short)22, (short)0, false, false, false, true, (short)28);
      var0.put("IDREF", var24);
      var2.minLength = 1;
      XSSimpleTypeDecl var25 = new XSSimpleTypeDecl((String)null, "http://www.w3.org/2001/XMLSchema", (short)0, var24, true, (XSObjectList)null);
      XSSimpleTypeDecl var26 = new XSSimpleTypeDecl(var25, "IDREFS", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null);
      var26.applyFacets1(var2, (short)2, (short)0);
      var0.put("IDREFS", var26);
      XSSimpleTypeDecl var27 = new XSSimpleTypeDecl(var23, "ENTITY", (short)23, (short)0, false, false, false, true, (short)29);
      var0.put("ENTITY", var27);
      var2.minLength = 1;
      var25 = new XSSimpleTypeDecl((String)null, "http://www.w3.org/2001/XMLSchema", (short)0, var27, true, (XSObjectList)null);
      XSSimpleTypeDecl var28 = new XSSimpleTypeDecl(var25, "ENTITIES", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null);
      var28.applyFacets1(var2, (short)2, (short)0);
      var0.put("ENTITIES", var28);
      var2.whiteSpace = 2;
      XSSimpleTypeDecl var29 = new XSSimpleTypeDecl(var20, "NMTOKEN", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)24);
      var29.applyFacets1(var2, (short)16, (short)0, (short)1);
      var0.put("NMTOKEN", var29);
      var2.minLength = 1;
      var25 = new XSSimpleTypeDecl((String)null, "http://www.w3.org/2001/XMLSchema", (short)0, var29, true, (XSObjectList)null);
      XSSimpleTypeDecl var30 = new XSSimpleTypeDecl(var25, "NMTOKENS", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null);
      var30.applyFacets1(var2, (short)2, (short)0);
      var0.put("NMTOKENS", var30);
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
