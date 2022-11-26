package org.python.indexer.demos;

import java.util.Iterator;
import java.util.List;
import org.python.indexer.Indexer;
import org.python.indexer.Outliner;

class HtmlOutline {
   private Indexer indexer;
   private StringBuilder buffer;

   public HtmlOutline(Indexer idx) {
      this.indexer = idx;
   }

   public String generate(String path) throws Exception {
      this.buffer = new StringBuilder(1024);
      List entries = this.indexer.generateOutline(path);
      this.addOutline(entries);
      String html = this.buffer.toString();
      this.buffer = null;
      return html;
   }

   private void addOutline(List entries) {
      this.add("<ul>\n");
      Iterator var2 = entries.iterator();

      while(var2.hasNext()) {
         Outliner.Entry e = (Outliner.Entry)var2.next();
         this.addEntry(e);
      }

      this.add("</ul>\n");
   }

   private void addEntry(Outliner.Entry e) {
      this.add("<li>");
      String style = null;
      switch (e.getKind()) {
         case FUNCTION:
         case METHOD:
         case CONSTRUCTOR:
            style = "function";
            break;
         case CLASS:
            style = "type-name";
            break;
         case PARAMETER:
            style = "parameter";
            break;
         case VARIABLE:
         case SCOPE:
            style = "identifier";
      }

      this.add("<a href='#");
      this.add(e.getQname());
      this.add("'>");
      if (style != null) {
         this.add("<span class='");
         this.add(style);
         this.add("'>");
      }

      this.add(e.getName());
      if (style != null) {
         this.add("</span>");
      }

      this.add("</a>");
      if (e.isBranch()) {
         this.addOutline(e.getChildren());
      }

      this.add("</li>");
   }

   private void add(String text) {
      this.buffer.append(text);
   }
}
