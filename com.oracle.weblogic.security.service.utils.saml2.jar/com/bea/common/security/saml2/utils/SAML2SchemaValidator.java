package com.bea.common.security.saml2.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.opensaml.core.Version;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.utils.XXEUtils;

public class SAML2SchemaValidator {
   private static final String[] XML_AND_SAML2_SCHEMAS = new String[]{"/schema/xml.xsd", "/schema/xmldsig-core-schema.xsd", "/schema/xenc-schema.xsd", "/schema/saml-schema-assertion-2.0.xsd", "/schema/saml-schema-protocol-2.0.xsd", "/schema/saml-schema-metadata-2.0.xsd", "/schema/saml-schema-authn-context-2.0.xsd", "/schema/saml-schema-x500-2.0.xsd", "/schema/saml-schema-xacml-2.0.xsd"};
   private static final Class OPENSAML_VERSION_CLASS = Version.class;
   private static Schema schema;

   public static void validateElement(Element elementToValidate) throws SAXException, IOException {
      Validator validator = getValidator();
      validator.validate(new DOMSource(elementToValidate));
   }

   private static Validator getValidator() throws SAXException {
      return XXEUtils.createValidator(getSchema());
   }

   private static synchronized Schema getSchema() throws SAXException {
      if (schema == null) {
         SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
         schema = sf.newSchema(getSchemaSources());
      }

      return schema;
   }

   private static Source[] getSchemaSources() {
      List schemaSources = new ArrayList();
      String[] var1 = XML_AND_SAML2_SCHEMAS;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String schema = var1[var3];
         InputStream is = OPENSAML_VERSION_CLASS.getResourceAsStream(schema);
         SAXSource saxSource = new SAXSource(new InputSource(is));
         saxSource.setSystemId(schema);
         schemaSources.add(saxSource);
      }

      return (Source[])((Source[])schemaSources.toArray(new Source[0]));
   }
}
