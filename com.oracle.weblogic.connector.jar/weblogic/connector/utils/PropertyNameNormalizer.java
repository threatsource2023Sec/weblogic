package weblogic.connector.utils;

public interface PropertyNameNormalizer {
   boolean isJCA16();

   String normalize(String var1);

   boolean match(String var1, String var2);

   int compare(String var1, String var2);
}
