package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;

public interface ProblemHeaderQName extends AttributedQName {
   String ELEMENT_LOCAL_NAME = "ProblemHeaderQName";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "ProblemHeaderQName", "wsa");
}
