package org.python.apache.xerces.impl.dv;

import org.python.apache.xerces.util.SymbolHash;
import org.python.apache.xerces.xs.XSObjectList;

public abstract class SchemaDVFactory {
   private static final String DEFAULT_FACTORY_CLASS = "org.python.apache.xerces.impl.dv.xs.SchemaDVFactoryImpl";

   public static final SchemaDVFactory getInstance() throws DVFactoryException {
      return getInstance("org.python.apache.xerces.impl.dv.xs.SchemaDVFactoryImpl");
   }

   public static final SchemaDVFactory getInstance(String var0) throws DVFactoryException {
      try {
         return (SchemaDVFactory)ObjectFactory.newInstance(var0, ObjectFactory.findClassLoader(), true);
      } catch (ClassCastException var2) {
         throw new DVFactoryException("Schema factory class " + var0 + " does not extend from SchemaDVFactory.");
      }
   }

   protected SchemaDVFactory() {
   }

   public abstract XSSimpleType getBuiltInType(String var1);

   public abstract SymbolHash getBuiltInTypes();

   public abstract XSSimpleType createTypeRestriction(String var1, String var2, short var3, XSSimpleType var4, XSObjectList var5);

   public abstract XSSimpleType createTypeList(String var1, String var2, short var3, XSSimpleType var4, XSObjectList var5);

   public abstract XSSimpleType createTypeUnion(String var1, String var2, short var3, XSSimpleType[] var4, XSObjectList var5);
}
