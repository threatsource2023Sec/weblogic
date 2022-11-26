package org.python.apache.xerces.impl.dv.xs;

import org.python.apache.xerces.impl.dv.XSSimpleType;
import org.python.apache.xerces.util.SymbolHash;

public class SchemaDVFactoryImpl extends BaseSchemaDVFactory {
   static final SymbolHash fBuiltInTypes = new SymbolHash();

   static void createBuiltInTypes() {
      BaseSchemaDVFactory.createBuiltInTypes(fBuiltInTypes, XSSimpleTypeDecl.fAnySimpleType);
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
