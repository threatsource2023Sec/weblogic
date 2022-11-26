package kodo.jdo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import org.apache.openjpa.util.InternalException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class JDOEntityResolver implements EntityResolver {
   public static final String ID_SYSTEM_JDO = "file:/javax/jdo/jdo.dtd";
   public static final String ID_PUBLIC_JDO1 = "-//Sun Microsystems, Inc.//DTD Java Data Objects Metadata 1.0//EN";
   public static final String ID_PUBLIC_JDO2 = "-//Sun Microsystems, Inc.//DTD Java Data Objects Metadata 2.0//EN";
   public static final String ID_SYSTEM_QUERY = "file:/javax/jdo/jdoquery.dtd";
   public static final String ID_PUBLIC_QUERY = "-//Sun Microsystems, Inc.//DTD Java Data Objects Named Queries 2.0//EN";
   public static final String ID_SYSTEM_JDO_XSD = "http://java.sun.com/xml/ns/jdo/jdo_2_0.xsd";
   public static final String ID_SYSTEM_ORM_XSD = "http://java.sun.com/xml/ns/jdo/orm_2_0.xsd";
   public static final String ID_SYSTEM_JDOQUERY_XSD = "http://java.sun.com/xml/ns/jdo/jdoquery_2_0.xsd";

   protected static String readResource(Class cls, String rsrc, int size) {
      InputStream in = cls.getResourceAsStream(rsrc);

      try {
         BufferedReader rd = new BufferedReader(new InputStreamReader(in), size);
         StringBuffer buf = new StringBuffer(size);

         int c;
         while((c = rd.read()) != -1) {
            buf.append((char)c);
         }

         String var17 = buf.toString();
         return var17;
      } catch (IOException var15) {
         throw new InternalException(var15);
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var14) {
            }
         }

      }
   }

   protected static String readResourceSafe(Class cls, String rsrc, int size) {
      try {
         return readResource(cls, rsrc, size);
      } catch (Exception var4) {
         return null;
      }
   }

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
      if ("http://java.sun.com/xml/ns/jdo/jdo_2_0.xsd".equals(systemId) && JDOEntityResolver.JdoXSD.value != null) {
         return new InputSource(new StringReader(JDOEntityResolver.JdoXSD.value));
      } else if ("http://java.sun.com/xml/ns/jdo/orm_2_0.xsd".equals(systemId) && JDOEntityResolver.OrmXSD.value != null) {
         return new InputSource(new StringReader(JDOEntityResolver.OrmXSD.value));
      } else if ("http://java.sun.com/xml/ns/jdo/jdoquery_2_0.xsd".equals(systemId) && JDOEntityResolver.JdoqueryXSD.value != null) {
         return new InputSource(new StringReader(JDOEntityResolver.JdoqueryXSD.value));
      } else if (!"-//Sun Microsystems, Inc.//DTD Java Data Objects Metadata 1.0//EN".equals(publicId) && !"-//Sun Microsystems, Inc.//DTD Java Data Objects Metadata 2.0//EN".equals(publicId) && !"file:/javax/jdo/jdo.dtd".equals(systemId)) {
         return !"-//Sun Microsystems, Inc.//DTD Java Data Objects Named Queries 2.0//EN".equals(publicId) && !"file:/javax/jdo/jdoquery.dtd".equals(systemId) ? null : new InputSource(new StringReader(JDOEntityResolver.QueryDTD.value));
      } else {
         return new InputSource(new StringReader(JDOEntityResolver.JdoDTD.value));
      }
   }

   protected static final class JdoqueryXSD {
      protected static final String value = JDOEntityResolver.readResourceSafe(JDOEntityResolver.class, "/javax/jdo/jdoquery.xsd", 1000);
   }

   protected static final class OrmXSD {
      protected static final String value = JDOEntityResolver.readResourceSafe(JDOEntityResolver.class, "/javax/jdo/orm.xsd", 1000);
   }

   protected static final class JdoXSD {
      protected static final String value = JDOEntityResolver.readResourceSafe(JDOEntityResolver.class, "/javax/jdo/jdo.xsd", 1000);
   }

   private static final class QueryDTD {
      private static String value = JDOEntityResolver.readResource(JDOEntityResolver.class, "jdoquery-dtd.rsrc", 1000);
   }

   private static final class JdoDTD {
      private static String value = JDOEntityResolver.readResource(JDOEntityResolver.class, "jdo-dtd.rsrc", 9000);
   }
}
