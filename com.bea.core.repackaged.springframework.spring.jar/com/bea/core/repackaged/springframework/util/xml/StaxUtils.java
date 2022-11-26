package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StreamUtils;
import java.util.List;
import java.util.function.Supplier;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;
import org.xml.sax.ContentHandler;
import org.xml.sax.XMLReader;

public abstract class StaxUtils {
   private static final XMLResolver NO_OP_XML_RESOLVER = (publicID, systemID, base, ns) -> {
      return StreamUtils.emptyInput();
   };

   public static XMLInputFactory createDefensiveInputFactory() {
      return createDefensiveInputFactory(XMLInputFactory::newInstance);
   }

   public static XMLInputFactory createDefensiveInputFactory(Supplier instanceSupplier) {
      XMLInputFactory inputFactory = (XMLInputFactory)instanceSupplier.get();
      inputFactory.setProperty("javax.xml.stream.supportDTD", false);
      inputFactory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
      inputFactory.setXMLResolver(NO_OP_XML_RESOLVER);
      return inputFactory;
   }

   public static Source createStaxSource(XMLStreamReader streamReader) {
      return new StAXSource(streamReader);
   }

   public static Source createStaxSource(XMLEventReader eventReader) throws XMLStreamException {
      return new StAXSource(eventReader);
   }

   public static Source createCustomStaxSource(XMLStreamReader streamReader) {
      return new StaxSource(streamReader);
   }

   public static Source createCustomStaxSource(XMLEventReader eventReader) {
      return new StaxSource(eventReader);
   }

   public static boolean isStaxSource(Source source) {
      return source instanceof StAXSource || source instanceof StaxSource;
   }

   @Nullable
   public static XMLStreamReader getXMLStreamReader(Source source) {
      if (source instanceof StAXSource) {
         return ((StAXSource)source).getXMLStreamReader();
      } else if (source instanceof StaxSource) {
         return ((StaxSource)source).getXMLStreamReader();
      } else {
         throw new IllegalArgumentException("Source '" + source + "' is neither StaxSource nor StAXSource");
      }
   }

   @Nullable
   public static XMLEventReader getXMLEventReader(Source source) {
      if (source instanceof StAXSource) {
         return ((StAXSource)source).getXMLEventReader();
      } else if (source instanceof StaxSource) {
         return ((StaxSource)source).getXMLEventReader();
      } else {
         throw new IllegalArgumentException("Source '" + source + "' is neither StaxSource nor StAXSource");
      }
   }

   public static Result createStaxResult(XMLStreamWriter streamWriter) {
      return new StAXResult(streamWriter);
   }

   public static Result createStaxResult(XMLEventWriter eventWriter) {
      return new StAXResult(eventWriter);
   }

   public static Result createCustomStaxResult(XMLStreamWriter streamWriter) {
      return new StaxResult(streamWriter);
   }

   public static Result createCustomStaxResult(XMLEventWriter eventWriter) {
      return new StaxResult(eventWriter);
   }

   public static boolean isStaxResult(Result result) {
      return result instanceof StAXResult || result instanceof StaxResult;
   }

   @Nullable
   public static XMLStreamWriter getXMLStreamWriter(Result result) {
      if (result instanceof StAXResult) {
         return ((StAXResult)result).getXMLStreamWriter();
      } else if (result instanceof StaxResult) {
         return ((StaxResult)result).getXMLStreamWriter();
      } else {
         throw new IllegalArgumentException("Result '" + result + "' is neither StaxResult nor StAXResult");
      }
   }

   @Nullable
   public static XMLEventWriter getXMLEventWriter(Result result) {
      if (result instanceof StAXResult) {
         return ((StAXResult)result).getXMLEventWriter();
      } else if (result instanceof StaxResult) {
         return ((StaxResult)result).getXMLEventWriter();
      } else {
         throw new IllegalArgumentException("Result '" + result + "' is neither StaxResult nor StAXResult");
      }
   }

   public static XMLEventReader createXMLEventReader(List events) {
      return new ListBasedXMLEventReader(events);
   }

   public static ContentHandler createContentHandler(XMLStreamWriter streamWriter) {
      return new StaxStreamHandler(streamWriter);
   }

   public static ContentHandler createContentHandler(XMLEventWriter eventWriter) {
      return new StaxEventHandler(eventWriter);
   }

   public static XMLReader createXMLReader(XMLStreamReader streamReader) {
      return new StaxStreamXMLReader(streamReader);
   }

   public static XMLReader createXMLReader(XMLEventReader eventReader) {
      return new StaxEventXMLReader(eventReader);
   }

   public static XMLStreamReader createEventStreamReader(XMLEventReader eventReader) throws XMLStreamException {
      return new XMLEventStreamReader(eventReader);
   }

   public static XMLStreamWriter createEventStreamWriter(XMLEventWriter eventWriter) {
      return new XMLEventStreamWriter(eventWriter, XMLEventFactory.newFactory());
   }

   public static XMLStreamWriter createEventStreamWriter(XMLEventWriter eventWriter, XMLEventFactory eventFactory) {
      return new XMLEventStreamWriter(eventWriter, eventFactory);
   }
}
