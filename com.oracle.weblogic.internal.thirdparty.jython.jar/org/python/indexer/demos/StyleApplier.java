package org.python.indexer.demos;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.python.indexer.StyleRun;

class StyleApplier {
   private static final int SOURCE_BUF_MULTIPLIER = 6;
   private SortedSet tags = new TreeSet();
   private StringBuilder buffer;
   private String source;
   private String path;
   private int sourceOffset = 0;

   public StyleApplier(String path, String src, List runs) {
      this.path = path;
      this.source = src;
      Iterator var4 = runs.iterator();

      while(var4.hasNext()) {
         StyleRun run = (StyleRun)var4.next();
         this.tags.add(new StartTag(run));
         this.tags.add(new EndTag(run));
      }

   }

   public String apply() {
      this.buffer = new StringBuilder(this.source.length() * 6);
      int lastStart = true;
      Iterator var2 = this.tags.iterator();

      while(var2.hasNext()) {
         Tag tag = (Tag)var2.next();
         tag.insert();
      }

      if (this.sourceOffset < this.source.length()) {
         this.copySource(this.sourceOffset, this.source.length());
      }

      return this.buffer.toString();
   }

   private void copySource(int beg, int end) {
      try {
         String src = this.escape(end == -1 ? this.source.substring(beg) : this.source.substring(beg, end));
         this.buffer.append(src);
      } catch (RuntimeException var4) {
         System.err.println("Warning: " + var4);
      }

      this.sourceOffset = end;
   }

   private String escape(String s) {
      return s.replace("&", "&amp;").replace("'", "&#39;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;");
   }

   private String toCSS(StyleRun style) {
      return style.type.toString().toLowerCase().replace("_", "-");
   }

   class EndTag extends Tag {
      public EndTag(StyleRun style) {
         super();
         this.offset = style.end();
         this.style = style;
      }

      void insert() {
         super.insert();
         switch (this.style.type) {
            case ANCHOR:
            case LINK:
               StyleApplier.this.buffer.append("</a>");
               break;
            default:
               StyleApplier.this.buffer.append("</span>");
         }

      }
   }

   class StartTag extends Tag {
      public StartTag(StyleRun style) {
         super();
         this.offset = style.start();
         this.style = style;
      }

      void insert() {
         super.insert();
         switch (this.style.type) {
            case ANCHOR:
               StyleApplier.this.buffer.append("<a name='");
               StyleApplier.this.buffer.append(this.style.url);
               break;
            case LINK:
               StyleApplier.this.buffer.append("<a href='");
               StyleApplier.this.buffer.append(this.style.url);
               break;
            default:
               StyleApplier.this.buffer.append("<span class='");
               StyleApplier.this.buffer.append(StyleApplier.this.toCSS(this.style));
         }

         StyleApplier.this.buffer.append("'");
         if (this.style.message != null) {
            StyleApplier.this.buffer.append(" title='");
            StyleApplier.this.buffer.append(this.style.message);
            StyleApplier.this.buffer.append("'");
         }

         StyleApplier.this.buffer.append(">");
      }
   }

   abstract class Tag implements Comparable {
      int offset;
      StyleRun style;

      public int compareTo(Tag other) {
         if (this == other) {
            return 0;
         } else if (this.offset < other.offset) {
            return -1;
         } else {
            return other.offset < this.offset ? 1 : this.hashCode() - other.hashCode();
         }
      }

      void insert() {
         if (this.offset > StyleApplier.this.sourceOffset) {
            StyleApplier.this.copySource(StyleApplier.this.sourceOffset, this.offset);
         }

      }
   }
}
