package weblogic.apache.xerces.impl.dv.xs;

import weblogic.apache.xerces.impl.dv.XSSimpleType;
import weblogic.apache.xerces.util.SymbolHash;

public class ExtendedSchemaDVFactoryImpl extends BaseSchemaDVFactory {
   static SymbolHash fBuiltInTypes = new SymbolHash();

   static void createBuiltInTypes() {
      BaseSchemaDVFactory.createBuiltInTypes(fBuiltInTypes, XSSimpleTypeDecl.fAnyAtomicType);
      fBuiltInTypes.put("anyAtomicType", XSSimpleTypeDecl.fAnyAtomicType);
      XSSimpleTypeDecl var4 = (XSSimpleTypeDecl)fBuiltInTypes.get("duration");
      fBuiltInTypes.put("yearMonthDuration", new XSSimpleTypeDecl(var4, "yearMonthDuration", (short)27, (short)1, false, false, false, true, (short)46));
      fBuiltInTypes.put("dayTimeDuration", new XSSimpleTypeDecl(var4, "dayTimeDuration", (short)28, (short)1, false, false, false, true, (short)47));
   }

   public XSSimpleType getBuiltInType(String var1) {
      return (XSSimpleType)fBuiltInTypes.get(var1);
   }

   public SymbolHash getBuiltInTypes() {
      return fBuiltInTypes.makeClone();
   }

   static {
      createBuiltInTypes();
   }
}
