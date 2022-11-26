package weblogic.xml.xpath;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.xml.stream.XMLName;
import weblogic.xml.xmlnode.XMLNode;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.parser.XPathLexer;
import weblogic.xml.xpath.parser.XPathParser;
import weblogic.xml.xpath.xmlnode.XMLNodeContext;
import weblogic.xml.xpath.xmlnode.XMLNodeModelFactory;

public final class XMLNodeXPath {
   public static final int NODESET = 1;
   public static final int BOOLEAN = 2;
   public static final int NUMBER = 3;
   public static final int STRING = 4;
   public static final int OTHER = 0;
   private static final int TRUNCATE = 55;
   private String mOriginalXPath;
   private Expression mRootExpr = null;
   private Map mVariableBindings = null;

   public XMLNodeXPath(String xpath) throws XPathException {
      if (xpath == null) {
         throw new IllegalArgumentException("xpath argument cannot be null.");
      } else {
         this.mOriginalXPath = xpath;
         this.parse();
      }
   }

   public int getType() throws XPathException {
      return this.getRootExpr().getType();
   }

   public void setVariableBindings(Map bindings) {
      if (this.mRootExpr != null) {
         throw new IllegalStateException();
      } else if (bindings == null) {
         throw new IllegalArgumentException("variableBindings argumentcannot be null.");
      } else {
         this.mVariableBindings = bindings;
      }
   }

   public String evaluateAsString(XMLNode node) throws XPathException {
      if (node == null) {
         throw new IllegalArgumentException("node cannot be null");
      } else {
         return this.getRootExpr().evaluateAsString(XMLNodeContext.create(node));
      }
   }

   public boolean evaluateAsBoolean(XMLNode node) throws XPathException {
      if (node == null) {
         throw new IllegalArgumentException("node cannot be null");
      } else {
         return this.getRootExpr().evaluateAsBoolean(XMLNodeContext.create(node));
      }
   }

   public List evaluateAsNodeset(XMLNode node) throws XPathException {
      if (node == null) {
         throw new IllegalArgumentException("node cannot be null");
      } else {
         return this.getRootExpr().evaluateAsNodeset(XMLNodeContext.create(node));
      }
   }

   public double evaluateAsNumber(XMLNode node) throws XPathException {
      return this.getRootExpr().evaluateAsNumber(XMLNodeContext.create(node));
   }

   public String toString() {
      return this.mOriginalXPath;
   }

   public void parse() throws XPathException {
      this.mRootExpr = null;
      this.mRootExpr = this.getRootExpr();
   }

   private Expression getRootExpr() throws XPathException {
      if (this.mRootExpr == null) {
         XPathLexer lexer = new XPathLexer(new StringReader(this.mOriginalXPath));
         XPathParser parser = new XPathParser(lexer);
         parser.setModelFactory(new XMLNodeModelFactory(this.mVariableBindings));

         try {
            this.mRootExpr = (Expression)parser.start();
            if (this.mRootExpr == null) {
               throw new XPathException("internal error - parser returned null expression");
            }
         } catch (Exception var4) {
            if (parser.getErrors() == null) {
               var4.printStackTrace();
            }

            parser.reportError(var4);
         }

         if (parser.getErrors() != null) {
            throw XPathParsingException.create(parser.getErrors());
         }
      }

      return this.mRootExpr;
   }

   public static void main(String[] args) throws Exception {
      PrintWriter out = new PrintWriter(System.out);

      try {
         if (args.length != 2) {
            out.println("Usage:\n   java weblogic.xml.xpath.XMLNodeXPath url xpath \n\n");
            out.flush();
            System.exit(-1);
         }

         out.println("--------------------------------------------");
         out.print("Evaluating xpath\n    ");
         out.println(args[1]);
         out.print("against document at\n    ");
         out.println(args[0]);
         out.println("using XMLNode");
         out.println("--------------------------------------------");

         XMLNodeXPath xpath;
         try {
            xpath = new XMLNodeXPath(args[1]);
         } catch (XPathException var13) {
            out.println(var13.getMessage());
            out.flush();
            System.exit(-1);
            xpath = null;
         }

         URL url;
         try {
            url = new URL(args[0]);
         } catch (MalformedURLException var12) {
            url = (new File(args[0])).toURL();
         }

         XMLNode doc = new XMLNode();
         doc.read(url.openStream());
         switch (xpath.getType()) {
            case 1:
               List result = xpath.evaluateAsNodeset(doc);
               Iterator j = result.iterator();

               while(j.hasNext()) {
                  printNode((XMLNode)j.next(), out);
               }

               out.print("\n\n--------------------------------\n matched ");
               out.print(result.size());
               out.print(" node(s)\n");
               break;
            case 2:
               out.println(xpath.evaluateAsBoolean(doc));
               break;
            case 3:
               out.println(xpath.evaluateAsNumber(doc));
               break;
            case 4:
               out.println(xpath.evaluateAsString(doc));
         }

         out.println("--------------------------------\n");
         out.flush();
      } catch (Exception var14) {
         var14.printStackTrace(out);
      } finally {
         out.flush();
      }

   }

   private static final void printNode(XMLNode node, PrintWriter out) {
      if (node.isTextNode()) {
         out.print("[TEXT]      ");
         out.println(truncate(node.getText()));
      } else if (node.isEndNode()) {
         out.print("[end-node?] ");
         out.println(truncate(node.getText()));
      } else {
         out.print("[ELEMENT]   ");
         StringWriter sw = new StringWriter();
         if (node.getName() == null) {
            sw.write("<#document>");
         } else {
            sw.write("<");
            sw.write(node.getName() + "");
            Iterator attNames = node.getNodeAttributes();

            while(attNames.hasNext()) {
               XMLName attName = (XMLName)attNames.next();
               sw.write(" ");
               sw.write(attName.toString());
               sw.write("='");
               sw.write(node.getAttribute(attName));
               sw.write("'");
            }

            sw.write(">");
         }

         out.println(truncate(sw.toString()));
      }

   }

   private static final String truncate(String s) {
      s = s.replace('\n', ' ');
      if (s.length() > 70) {
         s = s.substring(0, 55) + "...";
      }

      return s;
   }
}
