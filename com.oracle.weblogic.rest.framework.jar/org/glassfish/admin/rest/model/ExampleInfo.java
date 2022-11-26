package org.glassfish.admin.rest.model;

import java.util.LinkedList;
import java.util.List;

public class ExampleInfo {
   private MethodInfo method;
   private String title;
   private List entries = new LinkedList();

   ExampleInfo(MethodInfo method, String title) {
      this.method = method;
      this.title = title;
   }

   public MethodInfo getMethod() {
      return this.method;
   }

   public String getTitle() {
      return this.title;
   }

   public List getEntries() {
      return this.entries;
   }

   public void addText(String text) {
      this.addEntry(new ExampleText(this, text));
   }

   public void addHeader(String text) {
      this.addEntry(new ExampleHeader(this, text));
   }

   public void addTextContent(String text) {
      this.addEntry(new ExampleTextContent(this, text));
   }

   private void addEntry(ExampleEntry entry) {
      this.entries.add(entry);
   }

   public static class ExampleHeader extends ExampleText {
      private ExampleHeader(ExampleInfo example, String text) {
         super(example, text, null);
      }

      // $FF: synthetic method
      ExampleHeader(ExampleInfo x0, String x1, Object x2) {
         this(x0, x1);
      }
   }

   public static class ExampleTextContent extends ExampleText {
      private ExampleTextContent(ExampleInfo example, String text) {
         super(example, text, null);
      }

      // $FF: synthetic method
      ExampleTextContent(ExampleInfo x0, String x1, Object x2) {
         this(x0, x1);
      }
   }

   public static class ExampleText extends ExampleEntry {
      private String text;

      private ExampleText(ExampleInfo example, String text) {
         super(example);
         this.text = text;
      }

      public String getText() {
         return this.text;
      }

      // $FF: synthetic method
      ExampleText(ExampleInfo x0, String x1, Object x2) {
         this(x0, x1);
      }
   }

   public abstract static class ExampleEntry {
      private ExampleInfo example;

      protected ExampleEntry(ExampleInfo example) {
         this.example = example;
      }

      public ExampleInfo getExample() {
         return this.example;
      }
   }
}
