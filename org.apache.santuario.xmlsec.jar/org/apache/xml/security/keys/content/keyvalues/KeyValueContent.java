package org.apache.xml.security.keys.content.keyvalues;

import java.security.PublicKey;
import org.apache.xml.security.exceptions.XMLSecurityException;

public interface KeyValueContent {
   PublicKey getPublicKey() throws XMLSecurityException;
}
