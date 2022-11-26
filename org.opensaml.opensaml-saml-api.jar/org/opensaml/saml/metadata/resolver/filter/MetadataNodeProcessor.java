package org.opensaml.saml.metadata.resolver.filter;

import org.opensaml.core.xml.XMLObject;

public interface MetadataNodeProcessor {
   void process(XMLObject var1) throws FilterException;
}
