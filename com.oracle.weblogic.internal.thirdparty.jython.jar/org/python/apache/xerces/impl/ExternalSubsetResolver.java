package org.python.apache.xerces.impl;

import java.io.IOException;
import org.python.apache.xerces.xni.XNIException;
import org.python.apache.xerces.xni.grammars.XMLDTDDescription;
import org.python.apache.xerces.xni.parser.XMLEntityResolver;
import org.python.apache.xerces.xni.parser.XMLInputSource;

public interface ExternalSubsetResolver extends XMLEntityResolver {
   XMLInputSource getExternalSubset(XMLDTDDescription var1) throws XNIException, IOException;
}
