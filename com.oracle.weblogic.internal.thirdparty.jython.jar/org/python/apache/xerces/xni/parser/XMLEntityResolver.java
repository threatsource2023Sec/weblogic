package org.python.apache.xerces.xni.parser;

import java.io.IOException;
import org.python.apache.xerces.xni.XMLResourceIdentifier;
import org.python.apache.xerces.xni.XNIException;

public interface XMLEntityResolver {
   XMLInputSource resolveEntity(XMLResourceIdentifier var1) throws XNIException, IOException;
}
