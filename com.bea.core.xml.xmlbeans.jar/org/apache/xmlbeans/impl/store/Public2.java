package org.apache.xmlbeans.impl.store;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.SchemaField;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeLoader;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.values.NamespaceManager;
import org.apache.xmlbeans.impl.values.TypeStore;
import org.apache.xmlbeans.impl.values.TypeStoreUser;
import org.apache.xmlbeans.impl.values.TypeStoreVisitor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public final class Public2 {
   private static Locale newLocale(Saaj saaj) {
      XmlOptions options = null;
      if (saaj != null) {
         options = new XmlOptions();
         options.put("SAAJ_IMPL", saaj);
      }

      return Locale.getLocale((SchemaTypeLoader)null, options);
   }

   private static Locale newLocale() {
      return Locale.getLocale((SchemaTypeLoader)null, (XmlOptions)null);
   }

   public static void setSync(Document doc, boolean sync) {
      assert doc instanceof DomImpl.Dom;

      Locale l = ((DomImpl.Dom)doc).locale();
      l._noSync = !sync;
   }

   public static String compilePath(String path, XmlOptions options) {
      return Path.compilePath(path, options);
   }

   public static DOMImplementation getDomImplementation() {
      return newLocale();
   }

   public static DOMImplementation getDomImplementation(Saaj saaj) {
      return newLocale(saaj);
   }

   public static Document parse(String s) throws XmlException {
      Locale l = newLocale();
      DomImpl.Dom d;
      if (l.noSync()) {
         l.enter();

         try {
            d = l.load(s);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               d = l.load(s);
            } finally {
               l.exit();
            }
         }
      }

      return (Document)d;
   }

   public static Document parse(String s, XmlOptions options) throws XmlException {
      Locale l = newLocale();
      DomImpl.Dom d;
      if (l.noSync()) {
         l.enter();

         try {
            d = l.load(s, options);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               d = l.load(s, options);
            } finally {
               l.exit();
            }
         }
      }

      return (Document)d;
   }

   public static Document parse(String s, Saaj saaj) throws XmlException {
      Locale l = newLocale(saaj);
      DomImpl.Dom d;
      if (l.noSync()) {
         l.enter();

         try {
            d = l.load(s);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               d = l.load(s);
            } finally {
               l.exit();
            }
         }
      }

      return (Document)d;
   }

   public static Document parse(InputStream is, XmlOptions options) throws XmlException, IOException {
      Locale l = newLocale();
      DomImpl.Dom d;
      if (l.noSync()) {
         l.enter();

         try {
            d = l.load(is, options);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               d = l.load(is, options);
            } finally {
               l.exit();
            }
         }
      }

      return (Document)d;
   }

   public static Document parse(InputStream is, Saaj saaj) throws XmlException, IOException {
      Locale l = newLocale(saaj);
      DomImpl.Dom d;
      if (l.noSync()) {
         l.enter();

         try {
            d = l.load(is);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               d = l.load(is);
            } finally {
               l.exit();
            }
         }
      }

      return (Document)d;
   }

   public static Node getNode(XMLStreamReader s) {
      return Jsr173.nodeFromStream(s);
   }

   public static XMLStreamReader getStream(Node n) {
      assert n instanceof DomImpl.Dom;

      DomImpl.Dom d = (DomImpl.Dom)n;
      Locale l = d.locale();
      if (l.noSync()) {
         l.enter();

         XMLStreamReader var3;
         try {
            var3 = DomImpl.getXmlStreamReader(d);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            XMLStreamReader var4;
            try {
               var4 = DomImpl.getXmlStreamReader(d);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static String save(Node n) {
      return save((Node)n, (XmlOptions)null);
   }

   public static void save(Node n, OutputStream os, XmlOptions options) throws IOException {
      XmlCursor c = getCursor(n);
      c.save(os, options);
      c.dispose();
   }

   public static String save(Node n, XmlOptions options) {
      assert n instanceof DomImpl.Dom;

      DomImpl.Dom d = (DomImpl.Dom)n;
      Locale l = d.locale();
      if (l.noSync()) {
         l.enter();

         String var4;
         try {
            var4 = saveImpl(d, options);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            String var5;
            try {
               var5 = saveImpl(d, options);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   private static String saveImpl(DomImpl.Dom d, XmlOptions options) {
      Cur c = d.tempCur();
      String s = (new Saver.TextSaver(c, options, (String)null)).saveToString();
      c.release();
      return s;
   }

   public static String save(XmlCursor c) {
      return save((XmlCursor)c, (XmlOptions)null);
   }

   public static String save(XmlCursor xc, XmlOptions options) {
      Cursor cursor = (Cursor)xc;
      Locale l = cursor.locale();
      if (l.noSync()) {
         l.enter();

         String var4;
         try {
            var4 = saveImpl(cursor, options);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            String var5;
            try {
               var5 = saveImpl(cursor, options);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   private static String saveImpl(Cursor cursor, XmlOptions options) {
      Cur c = cursor.tempCur();
      String s = (new Saver.TextSaver(c, options, (String)null)).saveToString();
      c.release();
      return s;
   }

   public static XmlCursor newStore() {
      return newStore((Saaj)null);
   }

   public static XmlCursor newStore(Saaj saaj) {
      Locale l = newLocale(saaj);
      if (l.noSync()) {
         l.enter();

         XmlCursor var2;
         try {
            var2 = _newStore(l);
         } finally {
            l.exit();
         }

         return var2;
      } else {
         synchronized(l) {
            l.enter();

            XmlCursor var3;
            try {
               var3 = _newStore(l);
            } finally {
               l.exit();
            }

            return var3;
         }
      }
   }

   public static XmlCursor _newStore(Locale l) {
      Cur c = l.tempCur();
      c.createRoot();
      Cursor cursor = new Cursor(c);
      c.release();
      return cursor;
   }

   public static XmlCursor getCursor(Node n) {
      assert n instanceof DomImpl.Dom;

      DomImpl.Dom d = (DomImpl.Dom)n;
      Locale l = d.locale();
      if (l.noSync()) {
         l.enter();

         XmlCursor var3;
         try {
            var3 = DomImpl.getXmlCursor(d);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            XmlCursor var4;
            try {
               var4 = DomImpl.getXmlCursor(d);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static void dump(PrintStream o, DomImpl.Dom d) {
      d.dump(o);
   }

   public static void dump(PrintStream o, Node n) {
      dump(o, (DomImpl.Dom)n);
   }

   public static void dump(PrintStream o, XmlCursor c) {
      ((Cursor)c).dump(o);
   }

   public static void dump(PrintStream o, XmlObject x) {
      XmlCursor xc = x.newCursor();
      Node n = xc.getDomNode();
      DomImpl.Dom d = (DomImpl.Dom)n;
      xc.dispose();
      dump(o, d);
   }

   public static void dump(DomImpl.Dom d) {
      dump(System.out, d);
   }

   public static void dump(Node n) {
      dump(System.out, n);
   }

   public static void dump(XmlCursor c) {
      dump(System.out, c);
   }

   public static void dump(XmlObject x) {
      dump(System.out, x);
   }

   public static void test() throws Exception {
      Xobj x = (Xobj)parse("<a>XY</a>");
      Locale l = x._locale;
      l.enter();

      try {
         Cur c = x.tempCur();
         c.next();
         Cur c2 = c.tempCur();
         c2.next();
         Cur c3 = c2.tempCur();
         c3.nextChars(1);
         Cur c4 = c3.tempCur();
         c4.nextChars(1);
         c.dump();
         c.moveNodeContents(c, true);
         c.dump();
      } catch (Throwable var9) {
         var9.printStackTrace();
      } finally {
         l.exit();
      }

   }

   private static class TestTypeStoreUser implements TypeStoreUser {
      private String _value;

      TestTypeStoreUser(String value) {
         this._value = value;
      }

      public void attach_store(TypeStore store) {
      }

      public SchemaType get_schema_type() {
         throw new RuntimeException("Not impl");
      }

      public TypeStore get_store() {
         throw new RuntimeException("Not impl");
      }

      public void invalidate_value() {
      }

      public boolean uses_invalidate_value() {
         throw new RuntimeException("Not impl");
      }

      public String build_text(NamespaceManager nsm) {
         return this._value;
      }

      public boolean build_nil() {
         throw new RuntimeException("Not impl");
      }

      public void invalidate_nilvalue() {
         throw new RuntimeException("Not impl");
      }

      public void invalidate_element_order() {
         throw new RuntimeException("Not impl");
      }

      public void validate_now() {
         throw new RuntimeException("Not impl");
      }

      public void disconnect_store() {
         throw new RuntimeException("Not impl");
      }

      public TypeStoreUser create_element_user(QName eltName, QName xsiType) {
         return new TestTypeStoreUser("ELEM");
      }

      public TypeStoreUser create_attribute_user(QName attrName) {
         throw new RuntimeException("Not impl");
      }

      public String get_default_element_text(QName eltName) {
         throw new RuntimeException("Not impl");
      }

      public String get_default_attribute_text(QName attrName) {
         throw new RuntimeException("Not impl");
      }

      public SchemaType get_element_type(QName eltName, QName xsiType) {
         throw new RuntimeException("Not impl");
      }

      public SchemaType get_attribute_type(QName attrName) {
         throw new RuntimeException("Not impl");
      }

      public int get_elementflags(QName eltName) {
         throw new RuntimeException("Not impl");
      }

      public int get_attributeflags(QName attrName) {
         throw new RuntimeException("Not impl");
      }

      public SchemaField get_attribute_field(QName attrName) {
         throw new RuntimeException("Not impl");
      }

      public boolean is_child_element_order_sensitive() {
         throw new RuntimeException("Not impl");
      }

      public QNameSet get_element_ending_delimiters(QName eltname) {
         throw new RuntimeException("Not impl");
      }

      public TypeStoreVisitor new_visitor() {
         throw new RuntimeException("Not impl");
      }
   }
}
