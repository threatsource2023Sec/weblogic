package weblogic.xml.babel.stream;

import java.util.HashSet;
import java.util.Set;
import weblogic.xml.babel.baseparser.SymbolTable;

public class ScopeManager {
   private SymbolTable namespaces = new SymbolTable();
   private Set needToWrite = new HashSet();

   public ScopeManager() {
      this.namespaces.openScope();
      this.namespaces.put("xmlns", "");
   }

   public void put(String key, String value) {
      this.namespaces.put(key, value);
   }

   public boolean inRootElement() {
      return this.namespaces.getDepth() > 1;
   }

   public void checkPrefixMap(String prefix, String uri) {
      if (!"xml".equals(prefix) || !"http://www.w3.org/XML/1998/namespace".equals(uri)) {
         prefix = this.getPrefix(prefix);
         String oldValue = this.namespaces.get(prefix);
         if (oldValue == null || !oldValue.equals(uri)) {
            this.needToWrite.add(prefix);
            this.namespaces.put(prefix, uri);
         }

      }
   }

   public boolean needToWriteNS(String prefix) {
      prefix = this.getPrefix(prefix);
      return this.needToWrite.contains(prefix);
   }

   private String getPrefix(String prefix) {
      return prefix.equals("") ? "xmlns" : prefix;
   }

   public boolean isPrefixInMap(String prefix) {
      prefix = this.getPrefix(prefix);
      return this.namespaces.get(prefix) != null;
   }

   public void wroteNS(String prefix) {
      this.needToWrite.remove(prefix);
   }

   public String getNamespaceURI(String prefix) {
      prefix = this.getPrefix(prefix);
      return this.namespaces.get(prefix);
   }

   public void openScope() {
      this.namespaces.openScope();
   }

   public void closeScope() {
      this.namespaces.closeScope(false);
   }
}
