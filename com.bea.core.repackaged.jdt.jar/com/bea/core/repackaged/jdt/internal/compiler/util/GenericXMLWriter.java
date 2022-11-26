package com.bea.core.repackaged.jdt.internal.compiler.util;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class GenericXMLWriter extends PrintWriter {
   private static final String XML_VERSION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
   private String lineSeparator;
   private int tab;

   private static void appendEscapedChar(StringBuffer buffer, char c) {
      String replacement = getReplacement(c);
      if (replacement != null) {
         buffer.append('&');
         buffer.append(replacement);
         buffer.append(';');
      } else {
         buffer.append(c);
      }

   }

   private static String getEscaped(String s) {
      StringBuffer result = new StringBuffer(s.length() + 10);

      for(int i = 0; i < s.length(); ++i) {
         appendEscapedChar(result, s.charAt(i));
      }

      return result.toString();
   }

   private static String getReplacement(char c) {
      switch (c) {
         case '"':
            return "quot";
         case '&':
            return "amp";
         case '\'':
            return "apos";
         case '<':
            return "lt";
         case '>':
            return "gt";
         default:
            return null;
      }
   }

   public GenericXMLWriter(OutputStream stream, String lineSeparator, boolean printXmlVersion) {
      this((Writer)(new PrintWriter(stream)), lineSeparator, printXmlVersion);
   }

   public GenericXMLWriter(Writer writer, String lineSeparator, boolean printXmlVersion) {
      super(writer);
      this.tab = 0;
      this.lineSeparator = lineSeparator;
      if (printXmlVersion) {
         this.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
         this.print(this.lineSeparator);
      }

   }

   public void endTag(String name, boolean insertTab, boolean insertNewLine) {
      --this.tab;
      this.printTag('/' + name, (HashMap)null, insertTab, insertNewLine, false);
   }

   public void printString(String string, boolean insertTab, boolean insertNewLine) {
      if (insertTab) {
         this.printTabulation();
      }

      this.print(string);
      if (insertNewLine) {
         this.print(this.lineSeparator);
      }

   }

   private void printTabulation() {
      for(int i = 0; i < this.tab; ++i) {
         this.print('\t');
      }

   }

   public void printTag(String name, HashMap parameters, boolean insertTab, boolean insertNewLine, boolean closeTag) {
      if (insertTab) {
         this.printTabulation();
      }

      this.print('<');
      this.print(name);
      if (parameters != null) {
         int length = parameters.size();
         Map.Entry[] entries = new Map.Entry[length];
         parameters.entrySet().toArray(entries);
         Arrays.sort(entries, new Comparator() {
            public int compare(Object o1, Object o2) {
               Map.Entry entry1 = (Map.Entry)o1;
               Map.Entry entry2 = (Map.Entry)o2;
               return ((String)entry1.getKey()).compareTo((String)entry2.getKey());
            }
         });

         for(int i = 0; i < length; ++i) {
            this.print(' ');
            this.print(entries[i].getKey());
            this.print("=\"");
            this.print(getEscaped(String.valueOf(entries[i].getValue())));
            this.print('"');
         }
      }

      if (closeTag) {
         this.print("/>");
      } else {
         this.print(">");
      }

      if (insertNewLine) {
         this.print(this.lineSeparator);
      }

      if (parameters != null && !closeTag) {
         ++this.tab;
      }

   }

   public void startTag(String name, boolean insertTab) {
      this.printTag(name, (HashMap)null, insertTab, true, false);
      ++this.tab;
   }
}
