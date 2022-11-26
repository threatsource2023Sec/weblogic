package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;

public interface ProblemIRI extends AttributedURI {
   String ELEMENT_LOCAL_NAME = "ProblemIRI";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "ProblemIRI", "wsa");
}
