package weblogic.apache.xerces.impl.dv;

import java.util.Locale;

public interface ValidationContext {
   boolean needFacetChecking();

   boolean needExtraChecking();

   boolean needToNormalize();

   boolean useNamespaces();

   boolean isEntityDeclared(String var1);

   boolean isEntityUnparsed(String var1);

   boolean isIdDeclared(String var1);

   void addId(String var1);

   void addIdRef(String var1);

   String getSymbol(String var1);

   String getURI(String var1);

   Locale getLocale();
}
