package weblogic.jdbc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.util.TypeFilter;

public final class JDBCConnectionMetaDataParser {
   private XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();
   private static final String XML_FILE_NAME = "jdbcdrivers.xml";
   private List driverInfos = new ArrayList();
   private JDBCDriverInfoFactory theFactory = null;
   private static final XMLName BASE_ATTRIBUTE = ElementFactory.createXMLName("JDBC-Drivers");
   private static final XMLName NAME_ATTRIBUTE = ElementFactory.createXMLName("Name");
   private static final XMLName DRIVER_ATTRIBUTE = ElementFactory.createXMLName("Driver");
   private static final XMLName DRIVER_DBMS_ATTRIBUTE = ElementFactory.createXMLName("Database");
   private static final XMLName DRIVER_VENDOR_ATTRIBUTE = ElementFactory.createXMLName("Vendor");
   private static final XMLName DRIVER_TYPE_ATTRIBUTE = ElementFactory.createXMLName("Type");
   private static final XMLName DRIVER_DBMS_VERSION_ATTRIBUTE = ElementFactory.createXMLName("DatabaseVersion");
   private static final XMLName DRIVER_FORXA_ATTRIBUTE = ElementFactory.createXMLName("ForXA");
   private static final XMLName DRIVER_CERT_ATTRIBUTE = ElementFactory.createXMLName("Cert");
   private static final XMLName DRIVER_CLASSNAME_ATTRIBUTE = ElementFactory.createXMLName("ClassName");
   private static final XMLName DRIVER_URLCLASSNAME_ATTRIBUTE = ElementFactory.createXMLName("URLHelperClassname");
   private static final XMLName DRIVER_TEST_SQL_ATTRIBUTE = ElementFactory.createXMLName("TestSql");
   private static final XMLName DRIVER_INSTALL_URL_ATTRIBUTE = ElementFactory.createXMLName("InstallationUrl");
   private static final XMLName DRIVER_DESCRIPTION_ATTRIBUTE = ElementFactory.createXMLName("Description");
   private static final XMLName ATTRIBUTE_ATTRIBUTE = ElementFactory.createXMLName("Attribute");
   private static final XMLName ATTRIBUTE_DISPLAY_NAME = ElementFactory.createXMLName("DisplayName");
   private static final XMLName ATTRIBUTE_PROP_NAME = ElementFactory.createXMLName("PropertyName");
   private static final XMLName ATTRIBUTE_URL_ATTRIBUTE = ElementFactory.createXMLName("InURL");
   private static final XMLName ATTRIBUTE_REQUIRED_ATTRIBUTE = ElementFactory.createXMLName("Required");
   private static final XMLName ATTRIBUTE_DEFAULT_VALUE_ATTRIBUTE = ElementFactory.createXMLName("DefaultValue");
   private static final XMLName ATTRIBUTE_DESCRIPTION_ATTRIBUTE = ElementFactory.createXMLName("Description");

   public JDBCConnectionMetaDataParser() throws IOException, ParseException {
      this.loadSchema(this.getClass().getResourceAsStream("/jdbcdrivers.xml"));
   }

   public JDBCConnectionMetaDataParser(String fileName) throws IOException, ParseException {
      this.loadSchema(fileName);
   }

   public JDBCConnectionMetaDataParser(InputStream inputStream) throws IOException, ParseException {
      this.loadSchema(inputStream);
   }

   public JDBCDriverInfoFactory getJDBCDriverInfoFactory() {
      if (this.theFactory == null) {
         this.theFactory = new JDBCDriverInfoFactory(this.driverInfos);
      }

      return this.theFactory;
   }

   public static void main(String[] argv) throws Exception {
      if (argv.length == 0) {
         System.err.println("Usage weblogic.jdbc.utils.JDBCConnectionMetaDataParser <xml file>");
         System.exit(1);
      }

      new JDBCConnectionMetaDataParser(argv[0]);
   }

   private Attribute getRequiredAttribute(StartElement se, XMLName name) throws ParseException {
      Attribute at = se.getAttributeByName(name);
      if (at != null) {
         return at;
      } else {
         throw new ParseException("Expected required attribute: " + name.getLocalName() + " on element: " + se.getName().getLocalName());
      }
   }

   private String getNonRequiredAttribute(StartElement se, XMLName name) throws ParseException {
      Attribute at = se.getAttributeByName(name);
      return at != null ? at.getValue() : null;
   }

   private void checkLocalName(String expectedName, XMLEvent xe) throws ParseException {
      this.checkLocalName(expectedName, xe.getName());
   }

   private void checkLocalName(String expectedName, XMLName name) throws ParseException {
      if (!expectedName.equals(name.getLocalName())) {
         throw new ParseException("Expected name: '" + expectedName + "' but found '" + name.getLocalName() + "'");
      }
   }

   private void loadSchema(String fileName) throws IOException, ParseException {
      FileInputStream fis = null;

      try {
         fis = new FileInputStream(fileName);
         this.loadSchema((InputStream)fis);
      } finally {
         if (fis != null) {
            fis.close();
         }

      }

   }

   private void loadSchema(InputStream in) throws IOException, ParseException {
      XMLInputStream xis = null;

      try {
         xis = this.factory.newInputStream(in, new TypeFilter(2));
         this.parseSchema(xis);
      } finally {
         if (xis != null) {
            xis.close();
         }

      }

   }

   private void parseSchema(XMLInputStream xis) throws IOException, ParseException {
      StartElement se = (StartElement)xis.next();
      this.checkLocalName(BASE_ATTRIBUTE.getLocalName(), (XMLEvent)se);
      this.parseDrivers(xis);
   }

   private void parseDrivers(XMLInputStream xis) throws IOException, ParseException {
      while(xis.hasNext()) {
         this.parseDriver(xis);
      }

   }

   private void parseDriver(XMLInputStream xis) throws IOException, ParseException {
      StartElement se = (StartElement)xis.next();
      this.checkLocalName(DRIVER_ATTRIBUTE.getLocalName(), se.getName());
      MetaJDBCDriverInfo metaInfo = new MetaJDBCDriverInfo();
      metaInfo.setDbmsVendor(this.getRequiredAttribute(se, DRIVER_DBMS_ATTRIBUTE).getValue());
      metaInfo.setDriverVendor(this.getRequiredAttribute(se, DRIVER_VENDOR_ATTRIBUTE).getValue());
      metaInfo.setDriverClassName(this.getRequiredAttribute(se, DRIVER_CLASSNAME_ATTRIBUTE).getValue());
      metaInfo.setURLHelperClassName(this.getRequiredAttribute(se, DRIVER_URLCLASSNAME_ATTRIBUTE).getValue());
      metaInfo.setType(this.getRequiredAttribute(se, DRIVER_TYPE_ATTRIBUTE).getValue());
      metaInfo.setDbmsVersion(this.getRequiredAttribute(se, DRIVER_DBMS_VERSION_ATTRIBUTE).getValue());
      metaInfo.setForXA(this.getNonRequiredAttribute(se, DRIVER_FORXA_ATTRIBUTE));
      metaInfo.setCert(this.getNonRequiredAttribute(se, DRIVER_CERT_ATTRIBUTE));
      metaInfo.setTestSQL(this.getNonRequiredAttribute(se, DRIVER_TEST_SQL_ATTRIBUTE));
      metaInfo.setInstallURL(this.getNonRequiredAttribute(se, DRIVER_INSTALL_URL_ATTRIBUTE));
      metaInfo.setDescription(this.getNonRequiredAttribute(se, DRIVER_DESCRIPTION_ATTRIBUTE));
      this.parseAttributes(xis, metaInfo);
      this.driverInfos.add(metaInfo);
   }

   private void parseAttributes(XMLInputStream xis, MetaJDBCDriverInfo metaInfo) throws IOException, ParseException {
      while(true) {
         if (xis.hasNext()) {
            XMLName n = xis.peek().getName();
            if (ATTRIBUTE_ATTRIBUTE.equals(n)) {
               this.parseAtribute(xis, metaInfo);
               continue;
            }

            return;
         }

         return;
      }
   }

   private void parseAtribute(XMLInputStream xis, MetaJDBCDriverInfo metaInfo) throws IOException, ParseException {
      StartElement se = (StartElement)xis.next();
      this.checkLocalName(ATTRIBUTE_ATTRIBUTE.getLocalName(), (XMLEvent)se);
      JDBCDriverAttribute attribute = new JDBCDriverAttribute(metaInfo);
      attribute.setName(this.getRequiredAttribute(se, NAME_ATTRIBUTE).getValue());
      attribute.setDisplayName(this.getNonRequiredAttribute(se, ATTRIBUTE_DISPLAY_NAME));
      attribute.setPropertyName(this.getNonRequiredAttribute(se, ATTRIBUTE_PROP_NAME));
      attribute.setDefaultValue(this.getNonRequiredAttribute(se, ATTRIBUTE_DEFAULT_VALUE_ATTRIBUTE));
      attribute.setInURL(this.getNonRequiredAttribute(se, ATTRIBUTE_URL_ATTRIBUTE));
      attribute.setIsRequired(this.getNonRequiredAttribute(se, ATTRIBUTE_REQUIRED_ATTRIBUTE));
      attribute.setDesription(this.getNonRequiredAttribute(se, ATTRIBUTE_DESCRIPTION_ATTRIBUTE));
      metaInfo.setDriverAttribute(attribute.getName(), attribute);
   }
}
