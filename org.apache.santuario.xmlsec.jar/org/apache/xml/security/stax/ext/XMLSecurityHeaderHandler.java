package org.apache.xml.security.stax.ext;

import java.util.Deque;
import org.apache.xml.security.exceptions.XMLSecurityException;

public interface XMLSecurityHeaderHandler {
   void handle(InputProcessorChain var1, XMLSecurityProperties var2, Deque var3, Integer var4) throws XMLSecurityException;
}
