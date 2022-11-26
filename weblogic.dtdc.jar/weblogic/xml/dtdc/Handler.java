package weblogic.xml.dtdc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Stack;
import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.ParserFactory;

public class Handler implements DocumentHandler {
   private Stack stack;
   private Object topLevel;
   private boolean verbose;
   private String packageName;
   private Map map;
   private ClassLoader loader;

   public static void main(String[] args) throws Exception {
      try {
         Handler handler = new Handler();
         Parser parser = ParserFactory.makeParser("com.ibm.xml.parser.SAXDriver");
         parser.setDocumentHandler(handler);
         parser.parse(args[0]);
      } catch (SAXParseException var3) {
         System.out.println("** Parsing error, line " + var3.getLineNumber() + ", column " + var3.getColumnNumber() + ", uri " + var3.getSystemId());
         System.out.println("   " + var3.getMessage());
      } catch (SAXException var4) {
         Exception x = var4;
         if (var4.getException() != null) {
            x = var4.getException();
         }

         ((Exception)x).printStackTrace();
      } catch (Throwable var5) {
         var5.printStackTrace();
      }

   }

   public Handler() {
      this.stack = new Stack();
      this.packageName = "weblogic.xml.objects";
      this.loader = this.getClass().getClassLoader();
   }

   public Handler(boolean verbose) {
      this.stack = new Stack();
      this.packageName = "weblogic.xml.objects";
      this.loader = this.getClass().getClassLoader();
      this.verbose = verbose;
   }

   public Handler(String packageName, boolean verbose) {
      this(verbose);
      this.packageName = packageName;
   }

   public Handler(Map nameHandlers, String packageName, boolean verbose) {
      this(packageName, verbose);
      this.map = nameHandlers;
   }

   public void setPackageName(String packageName) {
      this.packageName = packageName;
   }

   public void setClassLoader(ClassLoader loader) {
      this.loader = loader;
   }

   public Object getTopLevel() {
      return this.topLevel;
   }

   public void setDocumentLocator(Locator locator) {
      if (this.verbose) {
         System.out.println("setDocumentLocator: " + locator);
      }

   }

   public void startDocument() throws SAXException {
      if (this.verbose) {
         System.out.println("startDocument");
      }

   }

   public void endDocument() throws SAXException {
      if (this.verbose) {
         System.out.println("endDocument");
      }

   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      if (this.verbose) {
         System.out.println("ignorableWhitespace: " + length);
      }

   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if (this.verbose) {
         System.out.println("characters: [");
         char[] chars = new char[length];
         System.arraycopy(ch, start, chars, 0, length);
         System.out.print(chars);
         System.out.println("]");
      }

      NameValuePair parent = (NameValuePair)this.stack.peek();
      char[] chars = new char[length];
      System.arraycopy(ch, start, chars, 0, length);

      try {
         Method addDataElement = parent.value.getClass().getMethod("addDataElement", String.class);
         addDataElement.invoke(parent.value, new String(chars));
      } catch (Exception var7) {
         throw new SAXException("Could not add data to element: " + parent.value + " because " + ((InvocationTargetException)var7).getTargetException());
      }
   }

   public void processingInstruction(String target, String data) throws SAXException {
      if (this.verbose) {
         System.out.println("processingInstruction: " + target + " <- " + data);
      }

   }

   public void startElement(String name, AttributeList atts) throws SAXException {
      try {
         String packageName = this.packageName + NameMangler.getpackage(name);
         String className = this.map != null && this.map.get(name) != null ? (String)this.map.get(name) : packageName + "." + NameMangler.upcase(NameMangler.depackage(name));
         GeneratedElement element;
         if (this.loader == null) {
            element = (GeneratedElement)Class.forName(className).newInstance();
         } else {
            element = (GeneratedElement)this.loader.loadClass(className).newInstance();
         }

         element.initialize(name, atts);
         this.stack.push(new NameValuePair(name, element));
         if (this.verbose) {
            System.out.println("startElement: " + name + " -> " + atts);
         }

      } catch (Exception var6) {
         if (this.verbose) {
            var6.printStackTrace();
         }

         throw new SAXException(var6);
      }
   }

   public void endElement(String name) throws SAXException {
      if (this.verbose) {
         System.out.println("endElement: " + name);
      }

      NameValuePair element = (NameValuePair)this.stack.pop();
      if (this.stack.empty()) {
         this.topLevel = element.value;
      } else {
         String methodName = "add" + NameMangler.upcase(NameMangler.depackage(name)) + "Element";
         NameValuePair parent = (NameValuePair)this.stack.peek();

         try {
            Method addSubElement;
            if (this.loader == null) {
               addSubElement = parent.value.getClass().getMethod(methodName, Class.forName(this.packageName + "." + NameMangler.upcase(NameMangler.depackage(element.name))));
            } else {
               addSubElement = parent.value.getClass().getMethod(methodName, this.loader.loadClass(this.packageName + "." + NameMangler.upcase(NameMangler.depackage(element.name))));
            }

            addSubElement.invoke(parent.value, element.value);
         } catch (InvocationTargetException var7) {
            Throwable th = var7.getTargetException();
            if (this.verbose) {
               th.printStackTrace();
            }

            if (th instanceof Exception) {
               throw new SAXException((Exception)th);
            } else {
               throw (Error)th;
            }
         } catch (NoSuchMethodException var8) {
            throw new SAXException("Could not find " + methodName + "(" + element.value.getClass() + ") in " + parent.name);
         } catch (Exception var9) {
            if (this.verbose) {
               var9.printStackTrace();
            }

            throw new SAXException(var9);
         }
      }
   }

   private static class NameValuePair {
      String name;
      Object value;

      NameValuePair(String name, Object value) {
         this.name = name;
         this.value = value;
      }
   }
}
