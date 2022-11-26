package org.apache.xml.security.encryption;

import java.io.IOException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface Serializer {
   void setCanonicalizer(Canonicalizer var1);

   byte[] serializeToByteArray(Element var1) throws Exception;

   byte[] serializeToByteArray(NodeList var1) throws Exception;

   byte[] canonSerializeToByteArray(Node var1) throws Exception;

   Node deserialize(byte[] var1, Node var2) throws XMLEncryptionException, IOException;
}
