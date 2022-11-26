package org.python.apache.xerces.impl.dv.xs;

import org.python.apache.xerces.impl.dv.XSFacets;
import org.python.apache.xerces.impl.dv.XSSimpleType;
import org.python.apache.xerces.util.SymbolHash;
import org.python.apache.xerces.xs.XSObjectList;

public class FullDVFactory extends BaseDVFactory {
   static final String URI_SCHEMAFORSCHEMA = "http://www.w3.org/2001/XMLSchema";
   static SymbolHash fFullTypes = new SymbolHash(89);

   public XSSimpleType getBuiltInType(String var1) {
      return (XSSimpleType)fFullTypes.get(var1);
   }

   public SymbolHash getBuiltInTypes() {
      return fFullTypes.makeClone();
   }

   static void createBuiltInTypes(SymbolHash var0) {
      BaseDVFactory.createBuiltInTypes(var0);
      XSFacets var1 = new XSFacets();
      XSSimpleTypeDecl var2 = XSSimpleTypeDecl.fAnySimpleType;
      XSSimpleTypeDecl var3 = (XSSimpleTypeDecl)var0.get("string");
      var0.put("float", new XSSimpleTypeDecl(var2, "float", (short)4, (short)1, true, true, true, true, (short)5));
      var0.put("double", new XSSimpleTypeDecl(var2, "double", (short)5, (short)1, true, true, true, true, (short)6));
      var0.put("duration", new XSSimpleTypeDecl(var2, "duration", (short)6, (short)1, false, false, false, true, (short)7));
      var0.put("hexBinary", new XSSimpleTypeDecl(var2, "hexBinary", (short)15, (short)0, false, false, false, true, (short)16));
      var0.put("QName", new XSSimpleTypeDecl(var2, "QName", (short)18, (short)0, false, false, false, true, (short)19));
      var0.put("NOTATION", new XSSimpleTypeDecl(var2, "NOTATION", (short)20, (short)0, false, false, false, true, (short)20));
      var1.whiteSpace = 1;
      XSSimpleTypeDecl var4 = new XSSimpleTypeDecl(var3, "normalizedString", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)21);
      var4.applyFacets1(var1, (short)16, (short)0);
      var0.put("normalizedString", var4);
      var1.whiteSpace = 2;
      XSSimpleTypeDecl var5 = new XSSimpleTypeDecl(var4, "token", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)22);
      var5.applyFacets1(var1, (short)16, (short)0);
      var0.put("token", var5);
      var1.whiteSpace = 2;
      var1.pattern = "([a-zA-Z]{1,8})(-[a-zA-Z0-9]{1,8})*";
      XSSimpleTypeDecl var6 = new XSSimpleTypeDecl(var5, "language", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)23);
      var6.applyFacets1(var1, (short)24, (short)0);
      var0.put("language", var6);
      var1.whiteSpace = 2;
      XSSimpleTypeDecl var7 = new XSSimpleTypeDecl(var5, "Name", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)25);
      var7.applyFacets1(var1, (short)16, (short)0, (short)2);
      var0.put("Name", var7);
      var1.whiteSpace = 2;
      XSSimpleTypeDecl var8 = new XSSimpleTypeDecl(var7, "NCName", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)26);
      var8.applyFacets1(var1, (short)16, (short)0, (short)3);
      var0.put("NCName", var8);
      var0.put("ID", new XSSimpleTypeDecl(var8, "ID", (short)21, (short)0, false, false, false, true, (short)27));
      XSSimpleTypeDecl var9 = new XSSimpleTypeDecl(var8, "IDREF", (short)22, (short)0, false, false, false, true, (short)28);
      var0.put("IDREF", var9);
      var1.minLength = 1;
      XSSimpleTypeDecl var10 = new XSSimpleTypeDecl((String)null, "http://www.w3.org/2001/XMLSchema", (short)0, var9, true, (XSObjectList)null);
      XSSimpleTypeDecl var11 = new XSSimpleTypeDecl(var10, "IDREFS", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null);
      var11.applyFacets1(var1, (short)2, (short)0);
      var0.put("IDREFS", var11);
      XSSimpleTypeDecl var12 = new XSSimpleTypeDecl(var8, "ENTITY", (short)23, (short)0, false, false, false, true, (short)29);
      var0.put("ENTITY", var12);
      var1.minLength = 1;
      var10 = new XSSimpleTypeDecl((String)null, "http://www.w3.org/2001/XMLSchema", (short)0, var12, true, (XSObjectList)null);
      XSSimpleTypeDecl var13 = new XSSimpleTypeDecl(var10, "ENTITIES", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null);
      var13.applyFacets1(var1, (short)2, (short)0);
      var0.put("ENTITIES", var13);
      var1.whiteSpace = 2;
      XSSimpleTypeDecl var14 = new XSSimpleTypeDecl(var5, "NMTOKEN", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null, (short)24);
      var14.applyFacets1(var1, (short)16, (short)0, (short)1);
      var0.put("NMTOKEN", var14);
      var1.minLength = 1;
      var10 = new XSSimpleTypeDecl((String)null, "http://www.w3.org/2001/XMLSchema", (short)0, var14, true, (XSObjectList)null);
      XSSimpleTypeDecl var15 = new XSSimpleTypeDecl(var10, "NMTOKENS", "http://www.w3.org/2001/XMLSchema", (short)0, false, (XSObjectList)null);
      var15.applyFacets1(var1, (short)2, (short)0);
      var0.put("NMTOKENS", var15);
   }

   static {
      createBuiltInTypes(fFullTypes);
   }
}
