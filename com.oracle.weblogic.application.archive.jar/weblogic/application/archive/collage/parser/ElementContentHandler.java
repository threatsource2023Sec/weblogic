package weblogic.application.archive.collage.parser;

import java.nio.CharBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class ElementContentHandler extends DefaultHandler {
   CharBuffer chars = CharBuffer.allocate(1000);
   Collage collage;
   LinkedList stack = new LinkedList();
   public static final Map bindersByQName;

   public Collage getCollage() {
      return this.collage;
   }

   public void startDocument() throws SAXException {
      super.startDocument();
   }

   protected static void validateEmptyContents(String contents) {
      if (!contents.isEmpty()) {
         throw new RuntimeException("Collage should not contain text: <" + contents + ">");
      }
   }

   public void endDocument() throws SAXException {
   }

   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      super.startElement(uri, localName, qName, attributes);
      Object obj = ((Binder)bindersByQName.get(qName)).newInstance(this.currentContainer(), attributes);
      if (this.collage == null) {
         this.collage = (Collage)obj;
      }

      this.stack.push(obj);
   }

   public void endElement(String uri, String localName, String qName) throws SAXException {
      int limit = this.chars.position();
      this.chars.rewind().limit(limit);
      String contents = this.chars.toString().trim();
      this.chars.limit(this.chars.capacity());
      Object obj = this.stack.pop();
      Container parent = this.currentContainer();
      Binder binder = (Binder)bindersByQName.get(qName);
      binder.bind(obj, parent, contents);
      if (parent != null) {
         parent.add(obj);
      }

   }

   private Container currentContainer() {
      return (Container)this.stack.peek();
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      this.chars.put(ch, start, length);
   }

   public void ignorableWhitespace(char[] ch, int start, int length) {
      this.chars.position(this.chars.position() - length);
   }

   public void warning(SAXParseException e) throws SAXException {
      super.warning(e);
   }

   public void error(SAXParseException e) throws SAXException {
      super.error(e);
   }

   public void fatalError(SAXParseException e) throws SAXException {
      super.fatalError(e);
   }

   static {
      Map aMap = new HashMap();
      aMap.put("collage", new Binder() {
         protected Object newInstance(Container parent, Attributes attributes) {
            return new Collage(attributes);
         }

         protected void bind(Object instance, Container parent, String contents) {
            ElementContentHandler.validateEmptyContents(contents);
         }
      });
      aMap.put("patternset", new Binder() {
         protected Object newInstance(Container parent, Attributes attributes) {
            return new PatternSet((Collage)parent, attributes);
         }

         protected void bind(Object instance, Container parent, String contents) {
            ElementContentHandler.validateEmptyContents(contents);
         }
      });
      aMap.put("exclude", new Binder() {
         protected Object newInstance(Container parent, Attributes attributes) {
            return Pattern.newExcludePattern();
         }

         protected void bind(Object instance, Container parent, String contents) {
            ((Pattern)instance).init(contents);
         }
      });
      aMap.put("include", new Binder() {
         protected Object newInstance(Container parent, Attributes attributes) {
            return Pattern.newIncludePattern();
         }

         protected void bind(Object instance, Container parent, String contents) {
            ((Pattern)instance).init(contents);
         }
      });
      aMap.put("pattern", new Binder() {
         protected Object newInstance(Container parent, Attributes attributes) {
            return attributes.getValue("refid");
         }

         protected void bind(Object instance, Container parent, String contents) {
            ElementContentHandler.validateEmptyContents(contents);
            ((PatternSet)parent).addPatternWithId((String)instance);
         }
      });
      aMap.put("mapping", new Binder() {
         protected Object newInstance(Container parent, Attributes attributes) {
            return new Mapping((Collage)parent, attributes);
         }

         protected void bind(Object instance, Container parent, String contents) {
            ElementContentHandler.validateEmptyContents(contents);
         }
      });
      aMap.put("uri", new Binder() {
         protected Object newInstance(Container parent, Attributes attributes) {
            return null;
         }

         protected void bind(Object object, Container parent, String contents) {
            ((Mapping)parent).setUri(contents);
         }
      });
      aMap.put("path", new Binder() {
         protected Object newInstance(Container parent, Attributes attributes) {
            return null;
         }

         protected void bind(Object object, Container parent, String contents) {
            ((Mapping)parent).setPath(contents);
         }
      });
      bindersByQName = Collections.unmodifiableMap(aMap);
   }

   private abstract static class Binder {
      private Binder() {
      }

      protected abstract Object newInstance(Container var1, Attributes var2);

      protected abstract void bind(Object var1, Container var2, String var3);

      // $FF: synthetic method
      Binder(Object x0) {
         this();
      }
   }
}
