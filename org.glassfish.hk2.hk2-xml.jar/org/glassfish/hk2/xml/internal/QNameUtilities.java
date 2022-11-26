package org.glassfish.hk2.xml.internal;

import javax.xml.namespace.QName;
import org.glassfish.hk2.utilities.general.GeneralUtilities;

public class QNameUtilities {
   public static final String fixNamespace(String namespace) {
      return namespace != null && !namespace.isEmpty() && !namespace.trim().isEmpty() ? namespace : "##default";
   }

   public static QName createQName(String namespace, String localPart) {
      return createQName(namespace, localPart, (String)null);
   }

   public static QName createQName(String namespace, String localPart, String defaultNamespace) {
      if (localPart == null) {
         return null;
      } else {
         return namespace != null && !namespace.isEmpty() && !namespace.trim().isEmpty() && !"##default".equals(namespace) && (defaultNamespace == null || !GeneralUtilities.safeEquals(namespace, defaultNamespace)) ? new QName(namespace, localPart) : new QName(localPart);
      }
   }

   public static String getNamespace(QName qName) {
      return getNamespace(qName, (String)null);
   }

   public static String getNamespace(QName qName, String defaultNamespace) {
      if (qName == null) {
         return null;
      } else {
         String namespace = qName.getNamespaceURI();
         return namespace != null && !namespace.isEmpty() && !namespace.trim().isEmpty() && (defaultNamespace == null || !GeneralUtilities.safeEquals(defaultNamespace, namespace)) ? namespace : "##default";
      }
   }
}
