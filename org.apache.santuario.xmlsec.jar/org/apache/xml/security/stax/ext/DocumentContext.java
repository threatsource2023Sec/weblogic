package org.apache.xml.security.stax.ext;

import java.util.List;
import java.util.Map;

public interface DocumentContext {
   String getEncoding();

   String getBaseURI();

   void setIsInEncryptedContent(int var1, Object var2);

   void unsetIsInEncryptedContent(Object var1);

   boolean isInEncryptedContent();

   void setIsInSignedContent(int var1, Object var2);

   void unsetIsInSignedContent(Object var1);

   boolean isInSignedContent();

   List getProtectionOrder();

   Map getContentTypeMap();
}
