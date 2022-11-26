package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;

public interface RetryAfter extends AttributedUnsignedLong {
   String ELEMENT_LOCAL_NAME = "RetryAfter";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "RetryAfter", "wsa");
}
