package weblogic.xml.babel.stream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.stream.BufferedXMLInputStream;
import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.util.XMLInputStreamFilterBase;
import weblogic.xml.stream.util.XMLPullReader;

public class XMLInputStreamFactoryImpl extends XMLInputStreamFactory {
   protected ElementFilter filter;
   protected boolean filterSet = false;

   public XMLInputStream wrap(XMLInputStream stream, ElementFilter filter) throws XMLStreamException {
      return new XMLInputStreamFilterBase(stream, filter);
   }

   public void setFilter(ElementFilter filter) throws XMLStreamException {
      this.filter = filter;
      this.filterSet = true;
   }

   public XMLInputStream newInputStream(File file) throws XMLStreamException {
      try {
         return this.newInputStream(SAXElementFactory.createInputSource(file));
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public XMLInputStream newInputStream(InputStream stream) throws XMLStreamException {
      return this.newInputStream(new InputSource(stream));
   }

   public XMLInputStream newInputStream(Reader reader) throws XMLStreamException {
      return this.newInputStream(new InputSource(reader));
   }

   protected XMLInputStream check(XMLInputStream input) throws XMLStreamException {
      return this.filterSet ? this.wrap(input, this.filter) : input;
   }

   protected XMLInputStream newInputStream(InputSource inputSource) throws XMLStreamException {
      XMLInputStreamBase input = new XMLInputStreamBase();
      input.open(inputSource);
      return this.check(input);
   }

   public XMLInputStream newInputStream(Reader reader, ElementFilter filter) throws XMLStreamException {
      return this.wrap(this.newInputStream(reader), filter);
   }

   public XMLInputStream newInputStream(InputStream stream, ElementFilter filter) throws XMLStreamException {
      return this.wrap(this.newInputStream(stream), filter);
   }

   public XMLInputStream newInputStream(File file, ElementFilter filter) throws XMLStreamException {
      return this.wrap(this.newInputStream(file), filter);
   }

   public XMLInputStream newInputStream(Document document) throws XMLStreamException {
      return this.newInputStream((Node)document);
   }

   public XMLInputStream newInputStream(Node node) throws XMLStreamException {
      DOMInputStream input = new DOMInputStream();
      input.open(node);
      return this.check(input);
   }

   public XMLInputStream newInputStream(Document document, ElementFilter filter) throws XMLStreamException {
      return this.newInputStream((Node)document, (ElementFilter)filter);
   }

   public XMLInputStream newInputStream(Node node, ElementFilter filter) throws XMLStreamException {
      return this.wrap(this.newInputStream(node), filter);
   }

   public XMLInputStream newInputStream(XMLPullReader reader, InputSource inputSource) throws XMLStreamException {
      SAXInputStream input = new SAXInputStream(reader);
      input.open(inputSource);
      return this.check(input);
   }

   public XMLInputStream newInputStream(XMLPullReader reader, InputSource inputSource, ElementFilter filter) throws XMLStreamException {
      return this.wrap(this.newInputStream(reader, inputSource), filter);
   }

   public BufferedXMLInputStream newBufferedInputStream(XMLInputStream inputStream) throws XMLStreamException {
      return new BufferedXMLInputStreamImpl(inputStream);
   }

   public XMLInputStream newInputStream(XMLInputStream inputStream, ElementFilter filter) throws XMLStreamException {
      return this.wrap(inputStream, filter);
   }

   public XMLInputStream newCanonicalInputStream(XMLInputStream inputStream) throws XMLStreamException {
      return new CanonicalInputStream(inputStream);
   }

   public XMLInputStream newDTDAwareInputStream(InputStream inputStream) throws XMLStreamException {
      XMLInputStreamBase base = new XMLInputStreamBase();
      base.openValidating(new InputSource(inputStream));
      return this.check(base);
   }

   public XMLInputStream newDTDAwareInputStream(Reader reader) throws XMLStreamException {
      XMLInputStreamBase base = new XMLInputStreamBase();
      base.openValidating(new InputSource(reader));
      return this.check(base);
   }

   public XMLInputStream newFragmentInputStream(InputStream inputStream, Map namespaces) throws XMLStreamException {
      XMLInputStreamBase base = new XMLInputStreamBase();
      base.openFragment(new InputSource(inputStream), namespaces);
      return this.check(base);
   }

   public XMLInputStream newFragmentInputStream(Reader reader, Map namespaces) throws XMLStreamException {
      XMLInputStreamBase base = new XMLInputStreamBase();
      base.openFragment(new InputSource(reader), namespaces);
      return this.check(base);
   }
}
