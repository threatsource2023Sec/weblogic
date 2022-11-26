package weblogic.apache.xerces.impl;

import java.io.IOException;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.grammars.XMLDTDDescription;
import weblogic.apache.xerces.xni.parser.XMLEntityResolver;
import weblogic.apache.xerces.xni.parser.XMLInputSource;

public interface ExternalSubsetResolver extends XMLEntityResolver {
   XMLInputSource getExternalSubset(XMLDTDDescription var1) throws XNIException, IOException;
}
