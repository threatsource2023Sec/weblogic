package org.apache.xml.security.encryption;

import java.util.Iterator;
import org.w3c.dom.Element;

public interface EncryptionProperty {
   String getTarget();

   void setTarget(String var1);

   String getId();

   void setId(String var1);

   String getAttribute(String var1);

   void setAttribute(String var1, String var2);

   Iterator getEncryptionInformation();

   void addEncryptionInformation(Element var1);

   void removeEncryptionInformation(Element var1);
}
