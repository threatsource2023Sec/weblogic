package weblogicx.jsp.tags;

import java.util.StringTokenizer;

public class KeyEnumerator {
   private StringTokenizer keyTokenizer;
   private KeyParser keyParser;

   public KeyEnumerator(String key) {
      this.keyTokenizer = new StringTokenizer(key, ",");
   }

   public boolean hasMoreKeys() {
      return this.keyTokenizer.hasMoreTokens();
   }

   public String getNextKey() {
      this.keyParser = new KeyParser(this.keyTokenizer.nextToken());
      return this.keyParser.getKey();
   }

   public String getKeyScope() {
      return this.keyParser.getKeyScope();
   }
}
