package org.jboss.weld.xml;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.logging.XmlLogger;
import org.jboss.weld.metadata.BeansXmlImpl;
import org.jboss.weld.metadata.ClassAvailableActivationImpl;
import org.jboss.weld.metadata.FilterImpl;
import org.jboss.weld.metadata.ScanningImpl;
import org.jboss.weld.metadata.SystemPropertyActivationImpl;
import org.jboss.weld.metadata.WeldFilterImpl;
import org.jboss.weld.util.collections.ImmutableSet;

public class BeansXmlStreamParser {
   public static final String JAVAEE_LEGACY_URI = "http://java.sun.com/xml/ns/javaee";
   public static final String JAVAEE_URI = "http://xmlns.jcp.org/xml/ns/javaee";
   public static final Set JAVAEE_URIS = ImmutableSet.of("http://java.sun.com/xml/ns/javaee", "http://xmlns.jcp.org/xml/ns/javaee");
   public static final String WELD_URI = "http://jboss.org/schema/weld/beans";
   public static final Set SCANNING_URIS = ImmutableSet.of("http://jboss.org/schema/weld/beans", "http://xmlns.jcp.org/xml/ns/javaee", "http://java.sun.com/xml/ns/javaee");
   private static final String VERSION_ATTRIBUTE_NAME = "version";
   private static final String BEAN_DISCOVERY_MODE_ATTRIBUTE_NAME = "bean-discovery-mode";
   private static final String NAME_ATTRIBUTE_NAME = "name";
   private static final String VALUE_ATTRIBUTE_NAME = "value";
   private static final String PATTERN_ATTRIBUTE_NAME = "pattern";
   private static final String IF_CLASS_AVAILABLE = "if-class-available";
   private static final String IF_CLASS_NOT_AVAILABLE = "if-class-not-available";
   private static final String IF_SYSTEM_PROPERTY = "if-system-property";
   private static final String CLASS = "class";
   private static final String STEREOTYPE = "stereotype";
   private static final String INCLUDE = "include";
   private static final String EXCLUDE = "exclude";
   private static final String TRIM = "trim";
   private static final String BEANS = "beans";
   private static final String ALTERNATIVES = "alternatives";
   private static final String INTERCEPTORS = "interceptors";
   private static final String DECORATORS = "decorators";
   private static final String SCAN = "scan";
   private List enabledInterceptors;
   private List enabledDecorators;
   private List selectedAlternatives;
   private List selectedAlternativeStereotypes;
   private List includes;
   private List excludes;
   private BeanDiscoveryMode discoveryMode;
   private String version;
   private boolean isTrimmed;
   private final URL beansXml;
   private final Function interpolator;

   public BeansXmlStreamParser(URL beansXml) {
      this(beansXml, Function.identity());
   }

   public BeansXmlStreamParser(URL beansXml, Function interpolator) {
      this.enabledInterceptors = null;
      this.enabledDecorators = null;
      this.selectedAlternatives = null;
      this.selectedAlternativeStereotypes = null;
      this.includes = null;
      this.excludes = null;
      this.discoveryMode = BeanDiscoveryMode.ALL;
      this.beansXml = beansXml;
      this.interpolator = interpolator;
   }

   @SuppressFBWarnings(
      value = {"RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"},
      justification = "False positive, see https://github.com/spotbugs/spotbugs/issues/259"
   )
   public BeansXml parse() {
      if (this.beansXml == null) {
         throw XmlLogger.LOG.loadError("unknown", (Throwable)null);
      } else {
         try {
            InputStream in = this.beansXml.openStream();
            Throwable var2 = null;

            BeansXml var3;
            try {
               if (in.available() != 0) {
                  XMLInputFactory factory = XMLInputFactory.newInstance();
                  XMLEventReader reader = factory.createXMLEventReader(in);
                  StartElement element = this.nextStartElement(reader, "beans", JAVAEE_URIS);
                  if (element != null) {
                     this.parseBeans(element);

                     while(reader.hasNext()) {
                        XMLEvent event = reader.nextEvent();
                        if (this.isEnd(event, "beans")) {
                           break;
                        }

                        if (this.isStartElement(event, "alternatives")) {
                           this.parseAlternatives(reader, event);
                        } else if (this.isStartElement(event, "interceptors")) {
                           this.parseInterceptors(reader, event);
                        } else if (this.isStartElement(event, "decorators")) {
                           this.parseDecorators(reader, event);
                        } else if (this.isStartElement(event, "scan", SCANNING_URIS)) {
                           this.parseScan(reader, event);
                        } else if (this.isStartElement(event, "trim")) {
                           this.isTrimmed = true;
                        }
                     }
                  }

                  reader.close();
                  return new BeansXmlImpl(this.orEmpty(this.selectedAlternatives), this.orEmpty(this.selectedAlternativeStereotypes), this.orEmpty(this.enabledDecorators), this.orEmpty(this.enabledInterceptors), new ScanningImpl(this.orEmpty(this.includes), this.orEmpty(this.excludes)), this.beansXml, this.discoveryMode, this.version, this.isTrimmed);
               }

               var3 = BeansXml.EMPTY_BEANS_XML;
            } catch (Throwable var17) {
               var2 = var17;
               throw var17;
            } finally {
               if (in != null) {
                  if (var2 != null) {
                     try {
                        in.close();
                     } catch (Throwable var16) {
                        var2.addSuppressed(var16);
                     }
                  } else {
                     in.close();
                  }
               }

            }

            return var3;
         } catch (IOException var19) {
            throw XmlLogger.LOG.loadError(this.beansXml, var19);
         } catch (XMLStreamException var20) {
            throw XmlLogger.LOG.parsingError(this.beansXml, var20);
         }
      }
   }

   private StartElement nextStartElement(XMLEventReader reader, String localName, Set namespaces) throws XMLStreamException {
      StartElement startElement = this.nextStartElement(reader);
      return startElement != null && localName.equals(startElement.getName().getLocalPart()) && this.isInNamespace(startElement.getName(), namespaces) ? startElement : null;
   }

   private StartElement nextStartElement(XMLEventReader reader) throws XMLStreamException {
      while(true) {
         if (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (!event.isStartElement()) {
               continue;
            }

            return event.asStartElement();
         }

         return null;
      }
   }

   private void parseBeans(StartElement element) {
      Iterator attributes = element.getAttributes();

      while(attributes.hasNext()) {
         Attribute attribute = (Attribute)attributes.next();
         if (this.isLocalName(attribute.getName(), "version")) {
            this.version = attribute.getValue();
         } else if (this.isLocalName(attribute.getName(), "bean-discovery-mode")) {
            this.discoveryMode = this.parseDiscoveryMode(this.interpolate(attribute.getValue()).trim().toUpperCase());
         }
      }

   }

   private boolean isLocalName(QName name, String value) {
      Objects.requireNonNull(name);
      Objects.requireNonNull(value);
      return value.equals(name.getLocalPart());
   }

   private void parseAlternatives(XMLEventReader reader, XMLEvent event) throws XMLStreamException {
      if (this.selectedAlternatives != null) {
         throw XmlLogger.LOG.multipleAlternatives(this.beansXml + "@" + event.asStartElement().getLocation().getLineNumber());
      } else {
         this.selectedAlternatives = new LinkedList();
         this.selectedAlternativeStereotypes = new LinkedList();

         while(reader.hasNext()) {
            event = reader.nextEvent();
            if (this.isEnd(event, "alternatives")) {
               return;
            }

            if (event.isStartElement()) {
               StartElement element = (StartElement)event;
               if (this.isStartElement(element, "class")) {
                  this.selectedAlternatives.add(new XmlMetadata(element.getName().toString(), this.getTrimmedElementText(reader), this.beansXml, element.getLocation().getLineNumber()));
               } else if (this.isStartElement(element, "stereotype")) {
                  this.selectedAlternativeStereotypes.add(new XmlMetadata(element.getName().toString(), this.getTrimmedElementText(reader), this.beansXml, element.getLocation().getLineNumber()));
               }
            }
         }

      }
   }

   private void parseInterceptors(XMLEventReader reader, XMLEvent event) throws XMLStreamException {
      if (this.enabledInterceptors != null) {
         throw XmlLogger.LOG.multipleInterceptors(this.beansXml + "@" + event.asStartElement().getLocation().getLineNumber());
      } else {
         this.enabledInterceptors = new LinkedList();

         while(reader.hasNext()) {
            event = reader.nextEvent();
            if (this.isEnd(event, "interceptors")) {
               return;
            }

            if (event.isStartElement()) {
               StartElement element = event.asStartElement();
               if (this.isStartElement(element, "class")) {
                  this.enabledInterceptors.add(new XmlMetadata(element.getName().toString(), this.getTrimmedElementText(reader), this.beansXml, element.getLocation().getLineNumber()));
               }
            }
         }

      }
   }

   private void parseDecorators(XMLEventReader reader, XMLEvent event) throws XMLStreamException {
      if (this.enabledDecorators != null) {
         throw XmlLogger.LOG.multipleDecorators(this.beansXml + "@" + event.asStartElement().getLocation().getLineNumber());
      } else {
         this.enabledDecorators = new LinkedList();

         while(reader.hasNext()) {
            event = reader.nextEvent();
            if (this.isEnd(event, "decorators")) {
               return;
            }

            if (event.isStartElement()) {
               StartElement element = event.asStartElement();
               if (this.isStartElement(element, "class")) {
                  this.enabledDecorators.add(new XmlMetadata(element.getName().toString(), this.getTrimmedElementText(reader), this.beansXml, element.getLocation().getLineNumber()));
               }
            }
         }

      }
   }

   private void parseScan(XMLEventReader reader, XMLEvent event) throws XMLStreamException {
      if (this.excludes != null) {
         throw XmlLogger.LOG.multipleScanning(this.beansXml + "@" + event.asStartElement().getLocation().getLineNumber());
      } else {
         this.excludes = new LinkedList();
         this.includes = new LinkedList();

         while(reader.hasNext()) {
            event = reader.nextEvent();
            if (this.isEnd(event, "scan")) {
               return;
            }

            if (event.isStartElement()) {
               StartElement element = (StartElement)event;
               List var10003;
               if (this.isStartElement(element, "exclude", SCANNING_URIS)) {
                  var10003 = this.excludes;
                  this.handleFilter(element, reader, var10003::add);
               } else if (this.isStartElement(element, "include", SCANNING_URIS)) {
                  var10003 = this.includes;
                  this.handleFilter(element, reader, var10003::add);
               }
            }
         }

      }
   }

   private void handleFilter(StartElement filterElement, XMLEventReader reader, Consumer consumer) throws XMLStreamException {
      String name = this.getAttribute(filterElement, "name");
      String pattern = name != null ? null : this.getAttribute(filterElement, "pattern");
      if (name != null || pattern != null) {
         List systemPropertyActivations = new LinkedList();
         List classAvailableActivations = new LinkedList();

         while(reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (this.isEnd(event, "exclude", SCANNING_URIS) || this.isEnd(event, "include", SCANNING_URIS)) {
               Object filter;
               if (filterElement.getName().getNamespaceURI().equals("http://jboss.org/schema/weld/beans")) {
                  filter = new WeldFilterImpl(name, systemPropertyActivations, classAvailableActivations, pattern);
               } else {
                  filter = new FilterImpl(name, systemPropertyActivations, classAvailableActivations);
               }

               consumer.accept(new XmlMetadata(filterElement.getName().toString(), filter, this.beansXml, filterElement.getLocation().getLineNumber()));
               return;
            }

            if (event.isStartElement()) {
               StartElement element = (StartElement)event;
               if (this.isStartElement(element, "if-class-available", SCANNING_URIS)) {
                  this.classAvailable(element, classAvailableActivations::add, false);
               } else if (this.isStartElement(element, "if-class-not-available", SCANNING_URIS)) {
                  this.classAvailable(element, classAvailableActivations::add, true);
               } else if (this.isStartElement(element, "if-system-property", SCANNING_URIS)) {
                  this.systemProperty(element, systemPropertyActivations::add);
               }
            }
         }
      }

   }

   private void classAvailable(StartElement element, Consumer consumer, boolean inverse) {
      String className = this.getAttribute(element, "name");
      Metadata classAvailableActivation = new XmlMetadata(element.getName().toString(), new ClassAvailableActivationImpl(className, inverse), this.beansXml, element.getLocation().getLineNumber());
      consumer.accept(classAvailableActivation);
   }

   private void systemProperty(StartElement element, Consumer consumer) {
      String name = this.getAttribute(element, "name");
      String value = this.getAttribute(element, "value");
      Metadata activation = new XmlMetadata(element.getName().toString(), new SystemPropertyActivationImpl(name, value), this.beansXml, element.getLocation().getLineNumber());
      consumer.accept(activation);
   }

   private String getAttribute(StartElement element, String name) {
      Iterator attributes = element.getAttributes();

      Attribute attribute;
      do {
         if (!attributes.hasNext()) {
            return null;
         }

         attribute = (Attribute)attributes.next();
      } while(!attribute.getName().getLocalPart().equals(name));

      return this.interpolate(attribute.getValue().trim());
   }

   private boolean isStartElement(XMLEvent event, String name, Set namespaces) {
      if (!event.isStartElement()) {
         return false;
      } else {
         StartElement element = event.asStartElement();
         return this.isLocalName(element.getName(), name) && this.isInNamespace(element.getName(), namespaces);
      }
   }

   private boolean isStartElement(XMLEvent event, String name) {
      return this.isStartElement(event, name, JAVAEE_URIS);
   }

   private boolean isEnd(XMLEvent event, String name) {
      return this.isEnd(event, name, JAVAEE_URIS);
   }

   private boolean isEnd(XMLEvent event, String name, Set namespaces) {
      if (!event.isEndElement()) {
         return false;
      } else {
         EndElement element = (EndElement)event;
         return this.isLocalName(element.getName(), name) && this.isInNamespace(element.getName(), namespaces);
      }
   }

   private BeanDiscoveryMode parseDiscoveryMode(String value) {
      BeanDiscoveryMode[] var2 = BeanDiscoveryMode.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         BeanDiscoveryMode mode = var2[var4];
         if (mode.toString().equals(value)) {
            return mode;
         }
      }

      throw new IllegalStateException("Unknown bean discovery mode: " + value);
   }

   private List orEmpty(List list) {
      return list == null ? Collections.emptyList() : list;
   }

   private boolean isInNamespace(QName name, Set uris) {
      String uri = name.getNamespaceURI();
      return uris != null && !uri.isEmpty() ? uris.contains(uri) : true;
   }

   private String getTrimmedElementText(XMLEventReader reader) throws XMLStreamException {
      return reader.getElementText().trim();
   }

   protected String interpolate(String value) {
      return (String)this.interpolator.apply(value);
   }
}
