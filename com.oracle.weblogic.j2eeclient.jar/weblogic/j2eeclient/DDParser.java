package weblogic.j2eeclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.utils.NestedError;
import weblogic.xml.jaxp.WebLogicSAXParserFactory;

public final class DDParser extends HandlerBase {
   private static Map element2Field = new HashMap();
   private static int NAME_LENGTH;
   private SAXParser parser;
   private Map map = new HashMap();
   private String body;
   private Descriptor currentDescriptor;
   private boolean isJ2eeRi = false;
   private static final Map localMap;

   public DDParser() throws ParserConfigurationException, SAXException, IOException {
      SAXParserFactory factory = new WebLogicSAXParserFactory();
      factory.setFeature("http://apache.org/xml/features/allow-java-encodings", true);
      this.parser = factory.newSAXParser();
   }

   public Descriptor[] parse(InputStream is) throws IOException, SAXException {
      this.parser.parse(new FilterInputStream(is) {
         public void close() throws IOException {
         }
      }, this);
      return (Descriptor[])((Descriptor[])this.map.values().toArray(new Descriptor[this.map.size()]));
   }

   public Descriptor[] parse(File file) throws IOException, SAXException {
      return this.parse((InputStream)(new FileInputStream(file)));
   }

   public void startElement(String name, AttributeList ignored) {
      if ("j2ee-ri-specific-information".equals(name)) {
         this.isJ2eeRi = true;
      }

   }

   public void characters(char[] buf, int offset, int len) throws SAXException {
      String chars = (new String(buf, offset, len)).trim();
      if (this.body != null) {
         this.body = this.body + chars;
      } else {
         this.body = chars;
      }

   }

   public void endElement(String name) throws SAXException {
      Field field = (Field)element2Field.get(name);
      if (field == Descriptor.NAME) {
         this.currentDescriptor = (Descriptor)this.map.get(this.body);
         if (this.currentDescriptor == null) {
            this.currentDescriptor = new Descriptor();
            this.currentDescriptor.type = name.substring(0, name.length() - NAME_LENGTH);
            this.map.put(this.body, this.currentDescriptor);
         }
      }

      if (field != null) {
         if (this.currentDescriptor == null) {
            throw new SAXException("Tag " + name + " without preceding naming tag");
         }

         if (this.isJ2eeRi && "jndi-name".equals(name) && !"java.net.URL".equals(this.currentDescriptor.type)) {
            this.body = this.body.replace('.', '_');
         }

         if ("ejb-link".equals(name)) {
            this.body = "link:" + this.body;
         }

         try {
            field.set(this.currentDescriptor, this.body);
         } catch (Exception var4) {
            throw new NestedError(var4);
         }
      }

      this.body = null;
   }

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
      String localID = (String)localMap.get(publicId);
      return localID == null ? super.resolveEntity(publicId, systemId) : new InputSource(this.getClass().getResourceAsStream(localID));
   }

   public static void main(String[] argv) throws Exception {
      DDParser p = new DDParser();

      for(int i = 0; i < argv.length; ++i) {
         System.out.println(Arrays.asList((Object[])p.parse(new File(argv[i]))));
      }

   }

   static {
      element2Field.put("ejb-link", Descriptor.VALUE);
      element2Field.put("ejb-ref-name", Descriptor.NAME);
      element2Field.put("env-entry-name", Descriptor.NAME);
      element2Field.put("env-entry-type", Descriptor.TYPE);
      element2Field.put("env-entry-value", Descriptor.VALUE);
      element2Field.put("jndi-name", Descriptor.VALUE);
      element2Field.put("res-ref-name", Descriptor.NAME);
      element2Field.put("res-type", Descriptor.TYPE);
      element2Field.put("resource-env-ref-name", Descriptor.NAME);
      element2Field.put("resource-env-ref-type", Descriptor.TYPE);
      element2Field.put("resource-env-ref-value", Descriptor.VALUE);
      NAME_LENGTH = "-name".length();
      localMap = new HashMap();
      localMap.put("-//Sun Microsystems, Inc.//DTD J2EE Application Client 1.3//EN", "application-client_1_3.dtd");
      localMap.put("-//Sun Microsystems, Inc.//DTD J2EE Application Client 1.2//EN", "application-client_1_2.dtd");
      localMap.put("-//BEA Systems, Inc.//DTD WebLogic 7.0.0 J2EE Application Client//EN", "weblogic-appclient.dtd");
      localMap.put("-//BEA Systems, Inc.//DTD WebLogic 6.0.0 J2EE Application Client//EN", "weblogic-appclient12.dtd");
   }
}
