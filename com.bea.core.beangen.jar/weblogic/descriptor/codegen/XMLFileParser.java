package weblogic.descriptor.codegen;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import weblogic.utils.XXEUtils;
import weblogic.utils.codegen.AttributeBinder;
import weblogic.utils.codegen.AttributeBinderBase;
import weblogic.utils.codegen.AttributeBinderFactory;
import weblogic.utils.codegen.AttributeBinderFactoryHelper;

public class XMLFileParser {
   AttributeBinderFactoryHelper helper;
   AttributeBinder root;
   String rootTag;
   AttributeBinder top;
   Stack stack = new Stack();

   private XMLFileParser() {
   }

   public XMLFileParser(String file, String helperClassName, String out) {
      try {
         this.run(file, out, helperClassName);
      } catch (Exception var5) {
         throw new AssertionError(var5);
      }
   }

   private void run(String in, String helperClassName, String out) throws Exception {
      System.out.println("Loading: " + helperClassName);
      Class helperClass = this.getClass().getClassLoader().loadClass(helperClassName);
      this.helper = (AttributeBinderFactoryHelper)helperClass.newInstance();
      XMLReader r = XXEUtils.createXMLReader();
      r.setContentHandler(new DefaultHandler() {
         public void startElement(String namespaceURI, String elementName, String qName, Attributes atts) {
            try {
               AttributeBinderFactory factory = XMLFileParser.this.helper.getAttributeBinderFactory(elementName);
               AttributeBinder b = factory.getAttributeBinder();
               XMLFileParser.this.stack.push(XMLFileParser.this.top);
               if (XMLFileParser.this.top == null) {
                  XMLFileParser.this.root = b;
                  XMLFileParser.this.rootTag = elementName;
               }

               XMLFileParser.this.top = b;

               for(int i = atts.getLength() - 1; i >= 0; --i) {
                  b.bindAttribute(atts.getQName(i), atts.getValue(i));
               }

            } catch (ClassNotFoundException var8) {
               throw new AssertionError(var8);
            }
         }

         public void endElement(String namespaceURI, String elementName, String qName) {
            AttributeBinder parent = (AttributeBinder)XMLFileParser.this.stack.pop();
            if (parent != null) {
               parent.bindAttribute(elementName, XMLFileParser.this.top);
            }

            XMLFileParser.this.top = parent;
         }
      });
      r.parse(new InputSource(in));
      if (out != null) {
         AttributeBinderBase rtemp = (AttributeBinderBase)this.root;
         if (out.equals("System.out")) {
            rtemp.toXML(new PrintStream(System.out), this.rootTag);
         } else if (out.equals("System.err")) {
            rtemp.toXML(new PrintStream(System.err), this.rootTag);
         } else {
            rtemp.toXML(new PrintStream(new FileOutputStream(out)), this.rootTag);
         }
      }

   }

   public static void main(String[] argv) throws Exception {
      try {
         (new XMLFileParser()).run(argv[0], argv[1], argv.length > 2 ? argv[2] : null);
      } catch (Exception var2) {
         System.out.println("Sample usage:\n");
         System.out.println("Usage: java -Dorg.xml.sax.driver=org.apache.crimson.parser.XMLReaderImpl weblogic.descriptor.codegen.XMLFileParser samples/medrec/src/physicianEar/META-INF/application.xml weblogic.utils.descriptorgen.DeploymentDescriptorFactoryHelper System.out\n");
         if (var2 instanceof SAXException) {
            ((SAXException)var2).getException().printStackTrace();
         } else {
            var2.printStackTrace();
         }
      }

   }
}
