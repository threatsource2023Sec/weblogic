package weblogic.apache.xerces.xni.parser;

import java.io.IOException;
import weblogic.apache.xerces.xni.XMLResourceIdentifier;
import weblogic.apache.xerces.xni.XNIException;

public interface XMLEntityResolver {
   XMLInputSource resolveEntity(XMLResourceIdentifier var1) throws XNIException, IOException;
}
