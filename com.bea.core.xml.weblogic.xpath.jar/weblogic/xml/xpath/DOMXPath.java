package weblogic.xml.xpath;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.dom.DOMContext;
import weblogic.xml.xpath.dom.DOMModelFactory;
import weblogic.xml.xpath.parser.XPathLexer;
import weblogic.xml.xpath.parser.XPathParser;

public final class DOMXPath {
   public static final int NODESET = 1;
   public static final int BOOLEAN = 2;
   public static final int NUMBER = 3;
   public static final int STRING = 4;
   public static final int OTHER = 0;
   public static final int NAMESPACE_NODE_TYPE = -4343;
   private static final int TRUNCATE = 55;
   private String mOriginalXPath;
   private Expression mRootExpr = null;
   private Map mVariableBindings = null;

   public DOMXPath(String xpath) throws XPathException {
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

   public Set evaluateAsNodeset(Document document) throws XPathException {
      return this.evaluateAsNodeset((Node)document);
   }

   public String evaluateAsString(Document document) throws XPathException {
      return this.evaluateAsString((Node)document);
   }

   public boolean evaluateAsBoolean(Document document) throws XPathException {
      return this.evaluateAsBoolean((Node)document);
   }

   public double evaluateAsNumber(Document document) throws XPathException {
      return this.evaluateAsNumber((Node)document);
   }

   public Set evaluateAsNodeset(Node contextNode) throws XPathException {
      if (contextNode == null) {
         throw new IllegalArgumentException("node cannot be null");
      } else {
         Collection list = this.getRootExpr().evaluateAsNodeset(new DOMContext(contextNode));
         if (list == null) {
            return null;
         } else {
            Set out = new LinkedHashSet();
            Iterator i = list.iterator();

            while(i.hasNext()) {
               out.add(i.next());
            }

            return out;
         }
      }
   }

   public String evaluateAsString(Node contextNode) throws XPathException {
      if (contextNode == null) {
         throw new IllegalArgumentException("node cannot be null");
      } else {
         return this.getRootExpr().evaluateAsString(new DOMContext(contextNode));
      }
   }

   public boolean evaluateAsBoolean(Node contextNode) throws XPathException {
      if (contextNode == null) {
         throw new IllegalArgumentException("node cannot be null");
      } else {
         return this.getRootExpr().evaluateAsBoolean(new DOMContext(contextNode));
      }
   }

   public double evaluateAsNumber(Node contextNode) throws XPathException {
      if (contextNode == null) {
         throw new IllegalArgumentException("node cannot be null");
      } else {
         return this.getRootExpr().evaluateAsNumber(new DOMContext(contextNode));
      }
   }

   public void setVariableBindings(Map bindings) {
      if (bindings == null) {
         throw new IllegalArgumentException("variableBindings argumentcannot be null.");
      } else {
         this.mVariableBindings = bindings;
      }
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
         parser.setModelFactory(new DOMModelFactory(this.mVariableBindings));

         try {
            this.mRootExpr = (Expression)parser.start();
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
            out.println("Usage:\n   java weblogic.xml.xpath.DOMXPath url xpath \n\n");
            out.flush();
            System.exit(-1);
         }

         out.println("--------------------------------------------");
         out.print("Evaluating xpath\n    ");
         out.println(args[1]);
         out.print("against document at\n    ");
         out.println(args[0]);
         out.println("using DOM");
         out.println("--------------------------------------------");

         DOMXPath xpath;
         try {
            xpath = new DOMXPath(args[1]);
         } catch (XPathException var12) {
            out.println(var12.getMessage());
            out.flush();
            System.exit(-1);
            xpath = null;
         }

         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setNamespaceAware(true);
         DocumentBuilder builder = factory.newDocumentBuilder();
         Document doc = builder.parse(args[0]);
         switch (xpath.getType()) {
            case 1:
               Collection result = xpath.evaluateAsNodeset(doc);
               Iterator j = result.iterator();

               while(j.hasNext()) {
                  printNode((Node)j.next(), out);
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
      } catch (Exception var13) {
         var13.printStackTrace(out);
      } finally {
         out.flush();
      }

   }

   private static final void printNode(Node node, PrintWriter out) {
      StringWriter sw;
      switch (node.getNodeType()) {
         case -4343:
            sw = new StringWriter();
            sw.write("[NAMESPACE] ");
            if (node.getNodeName() != null && node.getNodeName().trim().length() != 0) {
               sw.write(node.getNodeName());
            } else {
               sw.write("(default)");
            }

            sw.write("='");
            sw.write(node.getNodeValue());
            sw.write("'");
            out.println(truncate(sw.toString()));
            break;
         case 1:
         case 9:
            out.print("[ELEMENT]   ");
            sw = new StringWriter();
            sw.write("<");
            sw.write(node.getNodeName());
            NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
               for(int i = 0; i < attributes.getLength(); ++i) {
                  Node att = attributes.item(i);
                  sw.write(" ");
                  sw.write(att.getNodeName());
                  sw.write("='");
                  sw.write(att.getNodeValue());
                  sw.write("'");
               }
            }

            sw.write(">");
            out.println(truncate(sw.toString()));
            break;
         case 2:
            sw = new StringWriter();
            sw.write("[ATTRIBUTE] ");
            sw.write(node.getNodeName());
            sw.write("='");
            sw.write(node.getNodeValue());
            sw.write("'");
            out.println(truncate(sw.toString()));
            break;
         case 3:
            out.print("[TEXT]      ");
            out.println(truncate(node.getNodeValue()));
            break;
         case 4:
            out.print("[CDATA]     ");
            out.println(truncate(node.getNodeValue()));
            break;
         case 7:
            out.print("[PROC-INST] ");
            out.println(truncate(node.getNodeValue()));
            break;
         case 8:
            out.print("[COMMENT]   ");
            out.println(truncate(node.getNodeValue()));
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
