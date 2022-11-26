package com.bea.common.security.service;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;

public interface JAXPFactoryService {
   DocumentBuilderFactory newDocumentBuilderFactory();

   TransformerFactory newTransformerFactory();
}
