package weblogic.xml.util.xed;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class XEDParser {
   private Reader reader;
   private char current;
   private boolean EOF = false;
   private static final boolean debug = true;
   private int line = 1;

   public XEDParser(Reader r) throws IOException, StreamEditorException {
      this.reader = r;
      this.accept();
   }

   public boolean reachedEOF() {
      return this.EOF;
   }

   public char current() {
      return this.current;
   }

   public boolean isSpace() {
      return this.current == ' ' || this.current == '\r' || this.current == '\t' || this.current == '\n';
   }

   public void skipSpace() throws IOException {
      while(this.isSpace() && !this.reachedEOF()) {
         this.accept();
      }

   }

   public String error() {
      return "\nParse error at line:" + this.line + " char:" + this.current();
   }

   public void accept() throws IOException {
      int c = this.reader.read();
      if (c == -1) {
         this.EOF = true;
      }

      this.current = (char)c;
      if (c == 10) {
         ++this.line;
      }

   }

   public void accept(char c) throws IOException, StreamEditorException {
      if (this.current() == c) {
         this.accept();
      } else {
         throw new StreamEditorException("Unable to match character[" + c + "]" + this.error());
      }
   }

   public void accept(String s) throws IOException, StreamEditorException {
      for(int i = 0; i < s.length(); ++i) {
         if (s.charAt(i) != this.current()) {
            throw new StreamEditorException("Unable to match string[" + s + "]" + this.error());
         }

         this.accept();
      }

   }

   public String readString() throws IOException, StreamEditorException {
      StringBuffer b = new StringBuffer();

      while(this.current() != ' ' && this.current() != '=' && this.current() != '+' && this.current() != ';' && !this.reachedEOF()) {
         b.append(this.current());
         this.accept();
      }

      return b.toString();
   }

   public String readString(char stopChar) throws IOException, StreamEditorException {
      StringBuffer b = new StringBuffer();

      while(this.current() != stopChar && !this.reachedEOF()) {
         b.append(this.current());
         this.accept();
      }

      return b.toString();
   }

   public String readXPathString(char stopChar) throws IOException, StreamEditorException {
      StringBuffer b = new StringBuffer();
      int parenCount = 0;

      while(this.current() != stopChar && !this.reachedEOF()) {
         if (this.current() == '(') {
            ++parenCount;
         }

         if (this.current() == ')') {
            --parenCount;
         }

         if (parenCount < 0) {
            break;
         }

         b.append(this.current());
         this.accept();
      }

      return b.toString();
   }

   public String readXMLString() throws IOException, StreamEditorException {
      int parenCount = 0;
      StringBuffer b = new StringBuffer();

      while(this.current() != ';' && !this.reachedEOF()) {
         if (this.current() == '(') {
            ++parenCount;
         }

         if (this.current() == ')') {
            --parenCount;
         }

         if (parenCount < 0) {
            break;
         }

         b.append(this.current());
         this.accept();
      }

      return b.toString();
   }

   public Operation parse() throws IOException, StreamEditorException {
      Operation o = this.parseOperation();
      if (this.reachedEOF()) {
         System.out.println("Input parsed successfully");
      }

      return o;
   }

   public Operation parseOperation() throws IOException, StreamEditorException {
      Operation operation = new Operation();
      this.skipSpace();

      while(!this.reachedEOF()) {
         Command c = null;
         switch (this.current()) {
            case 'd':
               c = this.parseDelete();
               break;
            case 'i':
               this.accept("insert-");
               if (this.current() == 'b') {
                  c = this.parseInsertBefore();
               } else if (this.current() == 'a') {
                  c = this.parseInsertAfter();
               } else {
                  if (this.current() != 'c') {
                     throw new StreamEditorException("Valid commands are insert-before, insert-child,  or insert-after" + this.error());
                  }

                  c = this.parseInsertChild();
               }
               break;
            case 'r':
               c = this.parseReplace();
               break;
            default:
               throw new StreamEditorException("Valid commands areinsert-before insert-child insert-after replace delete" + this.error());
         }

         this.skipSpace();
         operation.add(c);
      }

      return operation;
   }

   public String parseXML() throws IOException, StreamEditorException {
      this.skipSpace();
      String xml;
      if (this.current() == '<') {
         xml = this.readXMLString();
      } else {
         String file = this.readString(')');
         this.skipSpace();
         System.out.println("file ----->" + file);
         Reader r = new FileReader(new File(file));
         StringBuffer buf = new StringBuffer();
         boolean done = false;

         while(!done) {
            int i = r.read();
            if (i == -1) {
               done = true;
               break;
            }

            buf.append((char)i);
         }

         xml = buf.toString();
      }

      return xml;
   }

   public Command parseInsertBefore() throws IOException, StreamEditorException {
      InsertBefore ib = new InsertBefore();
      this.accept("before");
      this.skipSpace();
      this.accept('(');
      this.skipSpace();
      String xpath = this.readXPathString(',');
      this.accept(',');
      this.skipSpace();
      ib.setXPath(xpath);
      ib.setXML(this.parseXML());
      this.skipSpace();
      this.accept(')');
      this.skipSpace();
      this.accept(';');
      System.out.println("[INSERTBEFORE][" + xpath + "][" + ib.getXML() + "]");
      return ib;
   }

   public Command parseInsertChild() throws IOException, StreamEditorException {
      InsertChild ic = new InsertChild();
      this.accept("child");
      this.skipSpace();
      this.accept('(');
      this.skipSpace();
      String xpath = this.readXPathString(',');
      this.accept(',');
      this.skipSpace();
      ic.setXPath(xpath);
      ic.setXML(this.parseXML());
      this.skipSpace();
      this.accept(')');
      this.skipSpace();
      this.accept(';');
      System.out.println("[INSERTCHILD][" + xpath + "][" + ic.getXML() + "]");
      return ic;
   }

   public Command parseInsertAfter() throws IOException, StreamEditorException {
      InsertAfter ia = new InsertAfter();
      this.accept("after");
      this.skipSpace();
      this.accept('(');
      this.skipSpace();
      String xpath = this.readXPathString(',');
      this.accept(',');
      this.skipSpace();
      ia.setXPath(xpath);
      ia.setXML(this.parseXML());
      this.skipSpace();
      this.accept(')');
      this.skipSpace();
      this.accept(';');
      System.out.println("[INSERTAFTER][" + xpath + "][" + ia.getXML() + "]");
      return ia;
   }

   public Command parseDelete() throws IOException, StreamEditorException {
      Delete d = new Delete();
      this.accept("delete");
      this.skipSpace();
      this.accept('(');
      this.skipSpace();
      String xpath = this.readXPathString(';');
      this.skipSpace();
      d.setXPath(xpath);
      this.skipSpace();
      this.accept(')');
      this.skipSpace();
      this.accept(';');
      System.out.println("[DELETE][" + xpath + "]");
      return d;
   }

   public Command parseReplace() throws IOException, StreamEditorException {
      Replace r = new Replace();
      this.accept("replace");
      this.skipSpace();
      this.accept('(');
      this.skipSpace();
      String xpath = this.readXPathString(',');
      r.setXPath(xpath);
      this.skipSpace();
      System.out.print("[REPLACE][" + xpath + "]");
      switch (this.current()) {
         case ')':
            this.accept();
            this.skipSpace();
            this.parseAction(r);
            break;
         case ',':
            this.accept();
            this.skipSpace();
            String xml = this.readXMLString();
            this.skipSpace();
            r.setXML(xml);
            this.skipSpace();
            this.accept(')');
            this.skipSpace();
            this.accept(';');
            System.out.println("[" + xml + "]");
            break;
         default:
            throw new StreamEditorException("Replace command has the following structure:'replace' Xpath (XML ';' | { Assignments ... })" + this.error());
      }

      return r;
   }

   public void parseAction(Replace r) throws IOException, StreamEditorException {
      this.accept('{');
      System.out.print("\n\t");
      this.skipSpace();

      while(this.current == '$') {
         r.add(this.parseAssignment());
      }

      this.accept('}');
   }

   public Variable parseVariable() throws IOException, StreamEditorException {
      this.accept('$');
      Object v;
      if (this.current() == '@') {
         this.accept();
         v = new AttributeReference();
      } else {
         v = new Variable();
      }

      ((Variable)v).setName(this.readString());
      return (Variable)v;
   }

   public Assignment parseAssignment() throws IOException, StreamEditorException {
      Variable lhs = this.parseVariable();
      Object a;
      if (lhs.isAttributeRef()) {
         a = new AttributeAssignment();
      } else {
         a = new Assignment();
      }

      ((Assignment)a).setLHS(lhs);
      System.out.print("[" + lhs + "=");
      this.skipSpace();
      this.accept('=');
      this.skipSpace();
      Variable var;
      String str;
      switch (this.current()) {
         case '"':
         case '\'':
            str = this.parseConstant();
            System.out.print("'" + str + "'");
            ((Assignment)a).addRHS(new StringConstant(str));
            break;
         case '$':
            var = this.parseVariable();
            System.out.print(var);
            ((Assignment)a).addRHS(var);
            break;
         case '[':
            this.parseAttributeList((Assignment)a);
      }

      this.skipSpace();

      for(; this.current() == '+'; this.skipSpace()) {
         System.out.print("+");
         this.accept();
         switch (this.current()) {
            case '"':
            case '\'':
               str = this.parseConstant();
               ((Assignment)a).addRHS(new StringConstant(str));
               System.out.print("'" + str + "'");
               break;
            case '$':
               var = this.parseVariable();
               ((Assignment)a).addRHS(var);
               System.out.print(var);
               break;
            case '[':
               this.parseAttributeList((Assignment)a);
         }
      }

      System.out.println("]");
      this.accept(';');
      this.skipSpace();
      return (Assignment)a;
   }

   public String parseConstant() throws IOException, StreamEditorException {
      String value = "";
      if (this.current() == '"') {
         this.accept();
         value = this.readString('"');
      } else {
         if (this.current() != '\'') {
            throw new StreamEditorException("String consants must be enclosed in quotes" + this.error());
         }

         this.accept();
         value = this.readString('\'');
      }

      this.accept();
      return value;
   }

   public void parseAttributeList(Assignment a) throws IOException, StreamEditorException {
      this.accept('[');

      while(this.current != ']' && !this.reachedEOF()) {
         this.skipSpace();
         String name = this.readString();
         this.skipSpace();
         this.accept('=');
         String value = this.parseConstant();
         System.out.print("[" + name + "=" + value + "]");
         a.addRHS(new AttributeVariable(name, value));
      }

      this.accept(']');
   }

   public static void main(String[] args) throws Exception {
      XEDParser parser = new XEDParser(new FileReader(args[0]));
      System.out.println(parser.parse());
   }
}
