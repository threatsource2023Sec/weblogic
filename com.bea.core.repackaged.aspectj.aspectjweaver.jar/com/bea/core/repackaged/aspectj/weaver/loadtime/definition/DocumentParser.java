package com.bea.core.repackaged.aspectj.weaver.loadtime.definition;

import com.bea.core.repackaged.aspectj.util.LangUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class DocumentParser extends DefaultHandler {
   private static final String DTD_PUBLIC_ID = "-//AspectJ//DTD 1.5.0//EN";
   private static final String DTD_PUBLIC_ID_ALIAS = "-//AspectJ//DTD//EN";
   private static final String ASPECTJ_ELEMENT = "aspectj";
   private static final String WEAVER_ELEMENT = "weaver";
   private static final String DUMP_ELEMENT = "dump";
   private static final String DUMP_BEFOREANDAFTER_ATTRIBUTE = "beforeandafter";
   private static final String DUMP_PERCLASSLOADERDIR_ATTRIBUTE = "perclassloaderdumpdir";
   private static final String INCLUDE_ELEMENT = "include";
   private static final String EXCLUDE_ELEMENT = "exclude";
   private static final String OPTIONS_ATTRIBUTE = "options";
   private static final String ASPECTS_ELEMENT = "aspects";
   private static final String ASPECT_ELEMENT = "aspect";
   private static final String CONCRETE_ASPECT_ELEMENT = "concrete-aspect";
   private static final String NAME_ATTRIBUTE = "name";
   private static final String SCOPE_ATTRIBUTE = "scope";
   private static final String REQUIRES_ATTRIBUTE = "requires";
   private static final String EXTEND_ATTRIBUTE = "extends";
   private static final String PRECEDENCE_ATTRIBUTE = "precedence";
   private static final String PERCLAUSE_ATTRIBUTE = "perclause";
   private static final String POINTCUT_ELEMENT = "pointcut";
   private static final String BEFORE_ELEMENT = "before";
   private static final String AFTER_ELEMENT = "after";
   private static final String AFTER_RETURNING_ELEMENT = "after-returning";
   private static final String AFTER_THROWING_ELEMENT = "after-throwing";
   private static final String AROUND_ELEMENT = "around";
   private static final String WITHIN_ATTRIBUTE = "within";
   private static final String EXPRESSION_ATTRIBUTE = "expression";
   private static final String DECLARE_ANNOTATION_ELEMENT = "declare-annotation";
   private final Definition definition = new Definition();
   private boolean inAspectJ;
   private boolean inWeaver;
   private boolean inAspects;
   private Definition.ConcreteAspect activeConcreteAspectDefinition;
   private static Hashtable parsedFiles = new Hashtable();
   private static boolean CACHE;
   private static final boolean LIGHTPARSER;

   private DocumentParser() {
   }

   public static Definition parse(URL url) throws Exception {
      if (CACHE && parsedFiles.containsKey(url.toString())) {
         return (Definition)parsedFiles.get(url.toString());
      } else {
         Definition def = null;
         if (LIGHTPARSER) {
            def = SimpleAOPParser.parse(url);
         } else {
            def = saxParsing(url);
         }

         if (CACHE && def.getAspectClassNames().size() > 0) {
            parsedFiles.put(url.toString(), def);
         }

         return def;
      }
   }

   private static Definition saxParsing(URL url) throws SAXException, ParserConfigurationException, IOException {
      DocumentParser parser = new DocumentParser();
      XMLReader xmlReader = getXMLReader();
      xmlReader.setContentHandler(parser);
      xmlReader.setErrorHandler(parser);

      try {
         xmlReader.setFeature("http://xml.org/sax/features/validation", false);
      } catch (SAXException var6) {
      }

      try {
         xmlReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
      } catch (SAXException var5) {
      }

      try {
         xmlReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      } catch (SAXException var4) {
      }

      xmlReader.setEntityResolver(parser);
      InputStream in = url.openStream();
      xmlReader.parse(new InputSource(in));
      return parser.definition;
   }

   private static XMLReader getXMLReader() throws SAXException, ParserConfigurationException {
      XMLReader xmlReader = null;

      try {
         xmlReader = XMLReaderFactory.createXMLReader();
      } catch (SAXException var2) {
         xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      }

      return xmlReader;
   }

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
      if (!publicId.equals("-//AspectJ//DTD 1.5.0//EN") && !publicId.equals("-//AspectJ//DTD//EN")) {
         System.err.println("AspectJ - WARN - unknown DTD " + publicId + " - consider using " + "-//AspectJ//DTD 1.5.0//EN");
         return null;
      } else {
         InputStream in = DocumentParser.class.getResourceAsStream("/aspectj_1_5_0.dtd");
         if (in == null) {
            System.err.println("AspectJ - WARN - could not read DTD " + publicId);
            return null;
         } else {
            return new InputSource(in);
         }
      }
   }

   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      String typePattern;
      String beforeAndAfter;
      String perWeaverDumpDir;
      if ("aspect".equals(qName)) {
         typePattern = attributes.getValue("name");
         beforeAndAfter = replaceXmlAnd(attributes.getValue("scope"));
         perWeaverDumpDir = attributes.getValue("requires");
         if (!this.isNull(typePattern)) {
            this.definition.getAspectClassNames().add(typePattern);
            if (beforeAndAfter != null) {
               this.definition.addScopedAspect(typePattern, beforeAndAfter);
            }

            if (perWeaverDumpDir != null) {
               this.definition.setAspectRequires(typePattern, perWeaverDumpDir);
            }
         }
      } else if ("weaver".equals(qName)) {
         typePattern = attributes.getValue("options");
         if (!this.isNull(typePattern)) {
            this.definition.appendWeaverOptions(typePattern);
         }

         this.inWeaver = true;
      } else {
         String anno;
         if ("concrete-aspect".equals(qName)) {
            typePattern = attributes.getValue("name");
            beforeAndAfter = attributes.getValue("extends");
            perWeaverDumpDir = attributes.getValue("precedence");
            anno = attributes.getValue("perclause");
            if (!this.isNull(typePattern)) {
               this.activeConcreteAspectDefinition = new Definition.ConcreteAspect(typePattern, beforeAndAfter, perWeaverDumpDir, anno);
               this.definition.getConcreteAspects().add(this.activeConcreteAspectDefinition);
            }
         } else if ("pointcut".equals(qName) && this.activeConcreteAspectDefinition != null) {
            typePattern = attributes.getValue("name");
            beforeAndAfter = attributes.getValue("expression");
            if (!this.isNull(typePattern) && !this.isNull(beforeAndAfter)) {
               this.activeConcreteAspectDefinition.pointcuts.add(new Definition.Pointcut(typePattern, replaceXmlAnd(beforeAndAfter)));
            }
         } else if ("declare-annotation".equals(qName) && this.activeConcreteAspectDefinition != null) {
            typePattern = attributes.getValue("method");
            beforeAndAfter = attributes.getValue("field");
            perWeaverDumpDir = attributes.getValue("type");
            anno = attributes.getValue("annotation");
            if (this.isNull(anno)) {
               throw new SAXException("Badly formed <declare-annotation> element, 'annotation' value is missing");
            }

            if (this.isNull(typePattern) && this.isNull(beforeAndAfter) && this.isNull(perWeaverDumpDir)) {
               throw new SAXException("Badly formed <declare-annotation> element, need one of 'method'/'field'/'type' specified");
            }

            if (!this.isNull(typePattern)) {
               this.activeConcreteAspectDefinition.declareAnnotations.add(new Definition.DeclareAnnotation(Definition.DeclareAnnotationKind.Method, typePattern, anno));
            } else if (!this.isNull(beforeAndAfter)) {
               this.activeConcreteAspectDefinition.declareAnnotations.add(new Definition.DeclareAnnotation(Definition.DeclareAnnotationKind.Field, beforeAndAfter, anno));
            } else if (!this.isNull(perWeaverDumpDir)) {
               this.activeConcreteAspectDefinition.declareAnnotations.add(new Definition.DeclareAnnotation(Definition.DeclareAnnotationKind.Type, perWeaverDumpDir, anno));
            }
         } else if ("before".equals(qName) && this.activeConcreteAspectDefinition != null) {
            typePattern = attributes.getValue("pointcut");
            beforeAndAfter = attributes.getValue("invokeClass");
            perWeaverDumpDir = attributes.getValue("invokeMethod");
            if (this.isNull(typePattern) || this.isNull(beforeAndAfter) || this.isNull(perWeaverDumpDir)) {
               throw new SAXException("Badly formed <before> element");
            }

            this.activeConcreteAspectDefinition.pointcutsAndAdvice.add(new Definition.PointcutAndAdvice(Definition.AdviceKind.Before, replaceXmlAnd(typePattern), beforeAndAfter, perWeaverDumpDir));
         } else if ("after".equals(qName) && this.activeConcreteAspectDefinition != null) {
            typePattern = attributes.getValue("pointcut");
            beforeAndAfter = attributes.getValue("invokeClass");
            perWeaverDumpDir = attributes.getValue("invokeMethod");
            if (this.isNull(typePattern) || this.isNull(beforeAndAfter) || this.isNull(perWeaverDumpDir)) {
               throw new SAXException("Badly formed <after> element");
            }

            this.activeConcreteAspectDefinition.pointcutsAndAdvice.add(new Definition.PointcutAndAdvice(Definition.AdviceKind.After, replaceXmlAnd(typePattern), beforeAndAfter, perWeaverDumpDir));
         } else if ("around".equals(qName) && this.activeConcreteAspectDefinition != null) {
            typePattern = attributes.getValue("pointcut");
            beforeAndAfter = attributes.getValue("invokeClass");
            perWeaverDumpDir = attributes.getValue("invokeMethod");
            if (this.isNull(typePattern) || this.isNull(beforeAndAfter) || this.isNull(perWeaverDumpDir)) {
               throw new SAXException("Badly formed <before> element");
            }

            this.activeConcreteAspectDefinition.pointcutsAndAdvice.add(new Definition.PointcutAndAdvice(Definition.AdviceKind.Around, replaceXmlAnd(typePattern), beforeAndAfter, perWeaverDumpDir));
         } else if ("aspectj".equals(qName)) {
            if (this.inAspectJ) {
               throw new SAXException("Found nested <aspectj> element");
            }

            this.inAspectJ = true;
         } else if ("aspects".equals(qName)) {
            this.inAspects = true;
         } else if ("include".equals(qName) && this.inWeaver) {
            typePattern = this.getWithinAttribute(attributes);
            if (!this.isNull(typePattern)) {
               this.definition.getIncludePatterns().add(typePattern);
            }
         } else if ("exclude".equals(qName) && this.inWeaver) {
            typePattern = this.getWithinAttribute(attributes);
            if (!this.isNull(typePattern)) {
               this.definition.getExcludePatterns().add(typePattern);
            }
         } else if ("dump".equals(qName) && this.inWeaver) {
            typePattern = this.getWithinAttribute(attributes);
            if (!this.isNull(typePattern)) {
               this.definition.getDumpPatterns().add(typePattern);
            }

            beforeAndAfter = attributes.getValue("beforeandafter");
            if (this.isTrue(beforeAndAfter)) {
               this.definition.setDumpBefore(true);
            }

            perWeaverDumpDir = attributes.getValue("perclassloaderdumpdir");
            if (this.isTrue(perWeaverDumpDir)) {
               this.definition.setCreateDumpDirPerClassloader(true);
            }
         } else if ("exclude".equals(qName) && this.inAspects) {
            typePattern = this.getWithinAttribute(attributes);
            if (!this.isNull(typePattern)) {
               this.definition.getAspectExcludePatterns().add(typePattern);
            }
         } else {
            if (!"include".equals(qName) || !this.inAspects) {
               throw new SAXException("Unknown element while parsing <aspectj> element: " + qName);
            }

            typePattern = this.getWithinAttribute(attributes);
            if (!this.isNull(typePattern)) {
               this.definition.getAspectIncludePatterns().add(typePattern);
            }
         }
      }

      super.startElement(uri, localName, qName, attributes);
   }

   private String getWithinAttribute(Attributes attributes) {
      return replaceXmlAnd(attributes.getValue("within"));
   }

   public void endElement(String uri, String localName, String qName) throws SAXException {
      if ("concrete-aspect".equals(qName)) {
         this.activeConcreteAspectDefinition = null;
      } else if ("aspectj".equals(qName)) {
         this.inAspectJ = false;
      } else if ("weaver".equals(qName)) {
         this.inWeaver = false;
      } else if ("aspects".equals(qName)) {
         this.inAspects = false;
      }

      super.endElement(uri, localName, qName);
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

   private static String replaceXmlAnd(String expression) {
      return LangUtil.replace(expression, " AND ", " && ");
   }

   private boolean isNull(String s) {
      return s == null || s.length() <= 0;
   }

   private boolean isTrue(String s) {
      return s != null && s.equals("true");
   }

   public static void deactivateCaching() {
      CACHE = false;
   }

   static {
      boolean value = false;

      try {
         value = System.getProperty("com.bea.core.repackaged.aspectj.weaver.loadtime.configuration.cache", "true").equalsIgnoreCase("true");
      } catch (Throwable var3) {
         var3.printStackTrace();
      }

      CACHE = value;
      value = false;

      try {
         value = System.getProperty("com.bea.core.repackaged.aspectj.weaver.loadtime.configuration.lightxmlparser", "false").equalsIgnoreCase("true");
      } catch (Throwable var2) {
         var2.printStackTrace();
      }

      LIGHTPARSER = value;
   }
}
