package com.octetstring.vde.syntax;

import java.util.Hashtable;

public class SyntaxMap {
   private static Hashtable s2c = new Hashtable();
   private static final String synbase = "1.3.6.1.4.1.1466.115.121.1.";
   private static SyntaxMap instance = null;

   private SyntaxMap() {
      s2c = new Hashtable();
      this.initialize();
   }

   public static SyntaxMap getInstance() {
      if (instance == null) {
         instance = new SyntaxMap();
      }

      return instance;
   }

   public static String get(String syntax) {
      return (String)s2c.get(syntax);
   }

   private void initialize() {
      s2c.put("1.3.6.1.4.1.1466.115.121.1.3", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.5", "com.octetstring.vde.syntax.BinarySyntax");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.6", "com.octetstring.vde.syntax.IA5String");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.7", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.8", "com.octetstring.vde.syntax.BinarySyntax");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.9", "com.octetstring.vde.syntax.BinarySyntax");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.10", "com.octetstring.vde.syntax.BinarySyntax");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.11", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.12", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.14", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.15", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.16", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.17", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.21", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.22", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.23", "com.octetstring.vde.syntax.BinarySyntax");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.24", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.25", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.26", "com.octetstring.vde.syntax.IA5String");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.27", "com.octetstring.vde.syntax.IntegerSyntax");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.28", "com.octetstring.vde.syntax.BinarySyntax");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.30", "com.octetstring.vde.syntax DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.31", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.33", "com.octetstring.vde.syntax.IA5String");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.34", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.35", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.36", "com.octetstring.vde.syntax.Integer");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.37", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.38", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.39", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.40", "com.octetstring.vde.syntax.BinarySyntax");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.41", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.43", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.44", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.50", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.51", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.53", "com.octetstring.vde.syntax.DirectoryString");
      s2c.put("1.3.6.1.4.1.1466.115.121.1.54", "com.octetstring.vde.syntax.DirectoryString");
   }
}
