package org.apache.xml.security.stax.ext.stax;

import javax.xml.stream.events.Attribute;

public interface XMLSecAttribute extends XMLSecEvent, Attribute, Comparable {
   XMLSecNamespace getAttributeNamespace();
}
