package weblogic.xml.xpath;

import antlr.ANTLRException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Map;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.babel.stream.XMLInputStreamBase;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.xpath.common.expressions.BooleanExpression;
import weblogic.xml.xpath.parser.ExpressionModel;
import weblogic.xml.xpath.parser.XPathLexer;
import weblogic.xml.xpath.parser.XPathParser;
import weblogic.xml.xpath.stream.LocationPath;
import weblogic.xml.xpath.stream.StreamModelFactory;
import weblogic.xml.xpath.stream.nodetests.NodeNodeTest;

public final class StreamXPath {
   private Map mVariables = null;
   private String mOriginalXPath;
   private LocationPath mLocationPath;

   public StreamXPath(String xpath) throws XPathException {
      if (xpath == null) {
         throw new IllegalArgumentException("xpath argument cannot be null.");
      } else {
         this.mOriginalXPath = xpath;
         this.parse();
      }
   }

   public void setVariableBindings(Map variableBindings) {
      this.mVariables = variableBindings;
   }

   public String toString() {
      return this.mOriginalXPath;
   }

   public boolean equals(Object o) {
      return o instanceof StreamXPath && this.mOriginalXPath.equals(((StreamXPath)o).mOriginalXPath);
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.mOriginalXPath == null ? 0 : this.mOriginalXPath.hashCode());
      return result;
   }

   public LocationPath getLocationPath() {
      return this.mLocationPath;
   }

   public void parse() throws XPathException {
      XPathLexer lexer = new XPathLexer(new StringReader(this.mOriginalXPath));
      XPathParser parser = new XPathParser(lexer);
      StreamModelFactory factory = new StreamModelFactory();
      parser.setModelFactory(factory);

      try {
         ExpressionModel expr = parser.start();
         if (expr == null) {
            throw new IllegalStateException();
         }

         if (expr instanceof BooleanExpression) {
            ArrayList predicates = new ArrayList();
            predicates.add(expr);
            ArrayList steps = new ArrayList();
            steps.add(factory.createStep(13, NodeNodeTest.INSTANCE, predicates));
            expr = factory.createLocationPath(steps);
         }

         if (!(expr instanceof LocationPath)) {
            throw new XPathUnsupportedException("Only location paths can be evaluated against a stream. ");
         }

         this.mLocationPath = (LocationPath)expr;
         this.mLocationPath.validateAsRootExpr();
         this.mLocationPath.setRequirements(factory.getRequirements());
      } catch (XPathException var7) {
         throw var7;
      } catch (ANTLRException var8) {
         throw new XPathParsingException(var8);
      }

      if (parser.getErrors() != null) {
         throw XPathParsingException.create(parser.getErrors());
      }
   }

   public Map getVariableBindings() {
      return this.mVariables;
   }

   public static void main(String[] args) {
      PrintWriter out = new PrintWriter(System.out);

      try {
         if (args.length < 2) {
            out.println("Usage: java weblogic.xml.xpath.StreamXPath url xpath [xpath...]");
            out.flush();
            System.exit(-1);
         }

         out.print("--------------------------------------------\n");
         out.println("Matching the following xpath(s)");

         for(int i = 1; i < args.length; ++i) {
            out.print("    [");
            out.print(i);
            out.print("] ");
            out.println(args[i]);
         }

         out.print("\nagainst the document at\n    ");
         out.println(args[0]);
         out.print("using the streaming XML parser\n");
         out.print("--------------------------------------------\n\n");
         XPathStreamFactory f = new XPathStreamFactory();
         MainObserver observer = new MainObserver(out);

         for(int i = 1; i < args.length; ++i) {
            try {
               StreamXPath xpath = new StreamXPath(args[i]);
               f.install(xpath, observer);
            } catch (XPathException var7) {
               out.print("[");
               out.print(i + "");
               out.print("] Error: ");
               out.println(var7.getMessage());
            }
         }

         XMLInputStreamBase source = new XMLInputStreamBase();
         source.open(SAXElementFactory.createInputSource(args[0]));

         XMLEvent var6;
         for(XMLInputStream input = f.createStream((XMLInputStream)source); input.hasNext(); var6 = input.next()) {
         }

         out.print("\n\n--------------------------------\n observed ");
         out.print(observer.matchCount);
         out.println(" matches");
         out.println("--------------------------------\n");
      } catch (Throwable var8) {
         var8.printStackTrace(out);
      }

      out.flush();
   }
}
