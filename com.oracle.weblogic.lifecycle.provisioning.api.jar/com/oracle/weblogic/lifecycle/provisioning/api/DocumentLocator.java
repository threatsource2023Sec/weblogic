package com.oracle.weblogic.lifecycle.provisioning.api;

import java.net.URI;
import org.jvnet.hk2.annotations.Contract;
import org.w3c.dom.Document;

@Contract
public interface DocumentLocator extends ResourceLocator {
   Document getDocumentRelativeTo(Document var1, URI var2) throws DocumentLocatorException;

   Document getDocumentRelativeTo(Document var1, URI var2, ResourceLocator.URIResolver var3) throws DocumentLocatorException;
}
