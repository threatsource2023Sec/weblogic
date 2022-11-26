package weblogic.application.archive.collage.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.utils.XXEUtils;

public class Collage extends Container {
   File pathToWriteableRoot;
   LinkedList mappings = new LinkedList();
   Map patternSetsById = new HashMap();
   boolean isAntStyle;
   private static String newLine = System.getProperty("line.separator");

   public static Collage readCollageDescriptor(InputStream xmlDocument) throws IOException {
      try {
         SAXParserFactory saxParserFactory = XXEUtils.createSAXParserFactoryInstance();
         saxParserFactory.setNamespaceAware(false);
         saxParserFactory.setValidating(false);
         saxParserFactory.setXIncludeAware(false);
         SAXParser parser = saxParserFactory.newSAXParser();
         ElementContentHandler handler = new ElementContentHandler();
         parser.parse(new InputSource(xmlDocument), handler);
         return handler.getCollage();
      } catch (ParserConfigurationException var4) {
         throw new IOException(var4);
      } catch (SAXException var5) {
         throw new IOException(var5);
      }
   }

   public Collage(Attributes attributes) {
      String pathString = attributes.getValue("path-to-writable-root");
      if (pathString != null) {
         this.pathToWriteableRoot = new File(pathString);
      }

      this.isAntStyle = getStyle(attributes.getValue("mapping-style"), true);
   }

   public File getWriteableRootDir() {
      return this.pathToWriteableRoot;
   }

   protected static boolean getStyle(String mappingStyle, boolean isAnt) {
      if (mappingStyle != null && !mappingStyle.isEmpty()) {
         if ("ant".equals(mappingStyle)) {
            return true;
         } else if (!"first-match".equals(mappingStyle)) {
            throw new RuntimeException("Invalid mapping style: " + mappingStyle);
         } else {
            return false;
         }
      } else {
         return isAnt;
      }
   }

   protected void add(Object object) {
      if (object instanceof Mapping) {
         this.mappings.add((Mapping)object);
      } else if (object instanceof PatternSet) {
         PatternSet ps = (PatternSet)object;
         this.patternSetsById.put(ps.id, ps);
      }

   }

   public List getMappings() {
      return this.mappings;
   }

   protected Object getPatternSetWithId(String id) {
      return this.patternSetsById.get(id);
   }

   public String toString() {
      return this.toString("", new StringBuffer());
   }

   private String toString(String indent, StringBuffer sb) {
      sb.append(indent).append("Collage path-to-writable-root = ").append(this.pathToWriteableRoot.getPath());
      sb.append(" mapping-style = ").append(this.isAntStyle ? "ant" : "first-match").append(newLine);
      Iterator var3 = this.patternSetsById.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry ps = (Map.Entry)var3.next();
         sb.append("  PatternSets id = ").append((String)ps.getKey()).append(newLine);
         ((PatternSet)ps.getValue()).toString(indent + "    ", sb);
      }

      return sb.toString();
   }
}
