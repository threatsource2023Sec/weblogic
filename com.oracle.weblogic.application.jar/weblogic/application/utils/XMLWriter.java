package weblogic.application.utils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class XMLWriter {
   private static final String sep = System.getProperty("line.separator");
   private static final int DEFAULT_INDENT = 2;
   private static final int DEFAULT_MAX_ATTRS_ON_SAME_LINE = 2;
   private boolean wroteFirstElement;
   private boolean hasElements;
   private boolean incompleteOpenTag;
   private boolean hasAttributes;
   private boolean closeOnNewLine;
   private final Stack elementStack;
   private final PrintWriter pw;
   private final int indent;
   private final int maxAttrsOnSameLine;
   private boolean textOnSameLineAsParentElement;

   public XMLWriter(PrintWriter pw) {
      this(pw, 2);
   }

   public XMLWriter(PrintWriter pw, int indent) {
      this(pw, indent, 2);
   }

   public XMLWriter(PrintWriter pw, int indent, int maxAttrsOnSameLine) {
      this.wroteFirstElement = false;
      this.hasElements = false;
      this.incompleteOpenTag = false;
      this.hasAttributes = false;
      this.closeOnNewLine = false;
      this.elementStack = new Stack();
      this.textOnSameLineAsParentElement = true;
      this.indent = indent;
      this.maxAttrsOnSameLine = maxAttrsOnSameLine;
      this.pw = pw;
   }

   public void flush() {
      this.pw.flush();
   }

   public void startDocumentDTD(String doctypeName, String publicID, String systemID) {
      if (this.hasElements) {
         throw new MalformedXMLRuntimeException("Cannot init document after elements have been added");
      } else {
         StringBuffer dtdString = new StringBuffer();
         dtdString.append("<!DOCTYPE ").append(doctypeName);
         if (publicID != null) {
            dtdString.append(" PUBLIC \"").append(publicID).append("\"");
         }

         if (systemID != null) {
            dtdString.append(" \"").append(systemID).append("\"");
         }

         dtdString.append(">").append(sep);
         this.writeGeneric(dtdString.toString());
      }
   }

   public void setTextOnSameLineAsParentElement(boolean b) {
      this.textOnSameLineAsParentElement = b;
   }

   public void addComment(String comment) {
      this.finishIncompleteTag();
      StringBuffer sb = new StringBuffer();
      sb.append(sep).append(this.getIndent()).append("<!--").append(comment).append("-->");
      this.writeGeneric(sb.toString());
   }

   public void addAttribute(String name, String value) {
      if (!this.incompleteOpenTag) {
         throw new MalformedXMLRuntimeException("Illegal call to addAttribute");
      } else {
         this.hasAttributes = true;
         if (this.closeOnNewLine) {
            this.pw.print(sep);
            this.pw.print(this.getIndent());
         } else {
            this.pw.print(" ");
         }

         this.pw.print(name);
         this.pw.print("=\"");
         this.pw.print(value);
         this.pw.print("\"");
      }
   }

   public void addAttribute(Map attributes) {
      String[] attributesArray = new String[attributes.size() * 2];
      int index = 0;

      for(Iterator iter = attributes.entrySet().iterator(); iter.hasNext(); index += 2) {
         Map.Entry entry = (Map.Entry)iter.next();
         attributesArray[index] = (String)entry.getKey();
         attributesArray[index + 1] = (String)entry.getValue();
      }

      this.addAttribute(attributesArray);
   }

   public void addAttribute(String[] attributes) {
      if (!this.hasAttributes && attributes.length / 2 > this.maxAttrsOnSameLine) {
         this.closeOnNewLine = true;
      }

      for(int i = 0; i < attributes.length; i += 2) {
         String key = attributes[i];
         String value = attributes[i + 1];
         this.addAttribute(key, value);
      }

   }

   public void addNestedElements(String[] elementNames) {
      for(int i = 0; i < elementNames.length; ++i) {
         this.addElement(elementNames[i]);
      }

   }

   public void addEmptyElements(String[] elementNames) {
      for(int i = 0; i < elementNames.length; ++i) {
         this.addEmptyElement(elementNames[i]);
      }

   }

   public void addEmptyElement(String elementName) {
      this.addElement(elementName);
      this.closeElement();
   }

   public void addElement(String elementName) {
      this.hasElements = true;
      this.finishIncompleteTag();
      if (this.wroteFirstElement) {
         this.pw.print(sep);
      } else {
         this.wroteFirstElement = true;
      }

      this.pw.print(this.getIndent());
      this.pw.print("<");
      this.pw.print(elementName);
      this.elementStack.push(elementName);
      this.incompleteOpenTag = true;
      this.hasAttributes = false;
      this.closeOnNewLine = false;
   }

   public void addElement(String elementName, String textChild) {
      this.addElement(elementName);
      this.addText(textChild, !this.textOnSameLineAsParentElement);
      this.closeElement(!this.textOnSameLineAsParentElement);
   }

   public void addElement(String elementName, String[] attributes) {
      this.addElement(elementName);
      this.addAttribute(attributes);
   }

   public void closeElement() {
      this.closeElement(true);
   }

   private void closeElement(boolean elementOnNewLine) {
      if (this.elementStack.isEmpty()) {
         throw new MalformedXMLRuntimeException("Illegal call to closeElement");
      } else {
         String element = this.elementStack.pop();
         if (this.incompleteOpenTag) {
            if (this.closeOnNewLine) {
               this.pw.print(sep);
               this.pw.print(this.getIndent());
            }

            this.pw.print("/>");
            this.incompleteOpenTag = false;
         } else {
            if (elementOnNewLine) {
               this.pw.print(sep);
               this.pw.print(this.getIndent());
            }

            this.pw.print("</");
            this.pw.print(element);
            this.pw.print(">");
         }

         this.hasAttributes = false;
      }
   }

   public void addText(String in) {
      this.addText(in, true);
   }

   private void addText(String in, boolean onNewLine) {
      if (in != null) {
         if (in.trim().length() != 0) {
            this.finishIncompleteTag();
            if (onNewLine) {
               this.pw.print(sep);
               this.pw.print(this.getIndent());
            }

            this.pw.print(in.trim());
         }
      }
   }

   public void addCDATA(String in) {
      if (in.trim().length() != 0) {
         this.finishIncompleteTag();
         StringBuffer sb = new StringBuffer();
         sb.append(sep).append(this.getIndent()).append("<![CDATA[");
         String data = in.trim();
         StringTokenizer st = new StringTokenizer(data, sep);

         while(st.hasMoreTokens()) {
            sb.append(sep).append(this.getIndent()).append(this.getIndentUnit()).append(st.nextToken().trim());
         }

         sb.append(sep).append(this.getIndent()).append("]]>");
         this.writeGeneric(sb.toString());
      }
   }

   public void finish() {
      this.closeAll();
      this.pw.flush();
   }

   private void closeAll() {
      while(!this.elementStack.isEmpty()) {
         this.closeElement();
      }

   }

   private void finishIncompleteTag() {
      if (this.incompleteOpenTag) {
         if (this.closeOnNewLine) {
            this.pw.print(sep);
            this.pw.print(this.getIndent(this.getStackSize() - 1));
         }

         this.pw.print(">");
         this.incompleteOpenTag = false;
      }

   }

   private void writeGeneric(String in) {
      this.pw.print(in);
   }

   private String getIndent() {
      return this.getIndent(this.getStackSize());
   }

   private String getIndent(int numUnits) {
      StringBuffer indentString = new StringBuffer();
      int max = numUnits * this.indent;

      for(int i = 0; i < max; ++i) {
         indentString.append(" ");
      }

      return indentString.toString();
   }

   private String getIndentUnit() {
      StringBuffer indentString = new StringBuffer();

      for(int i = 0; i < this.indent; ++i) {
         indentString.append(" ");
      }

      return indentString.toString();
   }

   private int getStackSize() {
      return this.elementStack.size();
   }

   public static void main(String[] args) {
      XMLWriter foo = new XMLWriter(new PrintWriter(System.out), 2, 3);
      foo.addElement("project");
      foo.addAttribute("name", "foo-project");
      foo.addAttribute("default", "default-target");
      foo.addAttribute("a3", "v3");
      foo.addAttribute("a4", "v4");
      foo.addElement("description", "this is a project of some sort");
      foo.addComment("A comment between description and target");
      foo.addElement("target", new String[]{"a1", "v1", "a2", "v2", "a3", "v3"});
      foo.addAttribute("name", "default-target");
      foo.addAttribute("a5", "v5");
      foo.addElement("test-suite");
      foo.addAttribute("testunit", "foo-testunit");
      foo.addElement("test");
      foo.addAttribute("name", "foo-test");
      foo.addElement("javatest");
      foo.addAttribute("testclass", "weblogic.qa.frame.test.foo");
      foo.addCDATA("Some CDATA nested inside the second \"javatest\" element");
      foo.closeElement();
      foo.addElement("javatest", new String[]{"a1", "v1", "a2", "v2", "a3", "v3", "a4", "v4"});
      foo.addText("Some text nested inside the second \"javatest\" element");
      foo.closeElement();
      foo.addElement("javatest", new String[]{"a1", "v1", "a2", "v2", "a3", "v3"});
      foo.closeElement();
      foo.addComment("Some nested elements");
      foo.addNestedElements(new String[]{"e1", "e2", "e3", "e4"});
      foo.finish();
      System.out.println(sep);
   }

   private class Stack extends ArrayList {
      private static final long serialVersionUID = -7341083262157803283L;

      private Stack() {
      }

      public void push(String s) {
         super.add(0, s);
      }

      public String pop() {
         return (String)super.remove(0);
      }

      // $FF: synthetic method
      Stack(Object x1) {
         this();
      }
   }

   private class MalformedXMLRuntimeException extends RuntimeException {
      private static final long serialVersionUID = 5122932004316476798L;

      private MalformedXMLRuntimeException(String message) {
         super(message);
      }

      // $FF: synthetic method
      MalformedXMLRuntimeException(String x1, Object x2) {
         this(x1);
      }
   }
}
