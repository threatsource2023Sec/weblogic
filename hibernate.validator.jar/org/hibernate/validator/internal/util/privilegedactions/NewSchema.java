package org.hibernate.validator.internal.util.privilegedactions;

import java.net.URL;
import java.security.PrivilegedExceptionAction;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

public final class NewSchema implements PrivilegedExceptionAction {
   private final SchemaFactory schemaFactory;
   private final URL url;

   public static NewSchema action(SchemaFactory schemaFactory, URL url) {
      return new NewSchema(schemaFactory, url);
   }

   public NewSchema(SchemaFactory schemaFactory, URL url) {
      this.schemaFactory = schemaFactory;
      this.url = url;
   }

   public Schema run() throws SAXException {
      return this.schemaFactory.newSchema(this.url);
   }
}
