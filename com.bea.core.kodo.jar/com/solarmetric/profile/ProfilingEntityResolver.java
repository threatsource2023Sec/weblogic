package com.solarmetric.profile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ProfilingEntityResolver implements EntityResolver {
   public static final String ID_SYSTEM = "file:/com/solarmetric/profile/profiling.dtd";
   public static final String ID_PUBLIC = "-//BEA Systems, Inc.//DTD BEA Profiling Metadata 1.0//EN";
   private static String _dtd = null;

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
      return !"-//BEA Systems, Inc.//DTD BEA Profiling Metadata 1.0//EN".equals(publicId) && !"file:/com/solarmetric/profile/profiling.dtd".equals(systemId) ? null : new InputSource(new StringReader(_dtd));
   }

   static {
      InputStream in = ProfilingEntityResolver.class.getResourceAsStream("profiling-dtd.rsrc");

      try {
         BufferedReader rd = new BufferedReader(new InputStreamReader(in));
         StringBuffer buf = new StringBuffer(1500);

         while(true) {
            String ln;
            if ((ln = rd.readLine()) == null) {
               _dtd = buf.toString();
               break;
            }

            buf.append(ln);
         }
      } catch (IOException var11) {
         throw new NestableRuntimeException(var11);
      } finally {
         try {
            in.close();
         } catch (IOException var10) {
         }

      }

   }
}
