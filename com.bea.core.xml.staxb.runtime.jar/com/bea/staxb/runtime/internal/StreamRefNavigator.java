package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.StreamReaderFromNode;
import com.bea.xml.XmlException;
import javax.xml.stream.XMLStreamReader;

public interface StreamRefNavigator {
   XMLStreamReader lookupRef(String var1, StreamReaderFromNode var2) throws XmlException;
}
