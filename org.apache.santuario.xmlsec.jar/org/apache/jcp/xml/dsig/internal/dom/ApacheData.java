package org.apache.jcp.xml.dsig.internal.dom;

import javax.xml.crypto.Data;
import org.apache.xml.security.signature.XMLSignatureInput;

public interface ApacheData extends Data {
   XMLSignatureInput getXMLSignatureInput();
}
