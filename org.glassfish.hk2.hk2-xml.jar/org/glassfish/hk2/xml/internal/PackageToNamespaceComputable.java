package org.glassfish.hk2.xml.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;
import org.glassfish.hk2.utilities.cache.Computable;
import org.glassfish.hk2.utilities.cache.ComputationErrorException;

public class PackageToNamespaceComputable implements Computable {
   private static final Map EMPTY = Collections.emptyMap();

   public static Map calculateNamespaces(Class clazz) {
      Package p = clazz.getPackage();
      return (new PackageToNamespaceComputable()).compute(p);
   }

   public Map compute(Package key) throws ComputationErrorException {
      XmlSchema xmlSchema = (XmlSchema)key.getAnnotation(XmlSchema.class);
      if (xmlSchema == null) {
         return EMPTY;
      } else if (xmlSchema.xmlns() == null) {
         return EMPTY;
      } else {
         Map retVal = new HashMap();
         XmlNs[] var4 = xmlSchema.xmlns();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            XmlNs xmlNs = var4[var6];
            retVal.put(xmlNs.prefix(), xmlNs.namespaceURI());
         }

         return retVal;
      }
   }
}
