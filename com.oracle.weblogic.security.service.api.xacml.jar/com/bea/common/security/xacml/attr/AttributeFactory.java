package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.util.Iterator;
import org.w3c.dom.Node;

public interface AttributeFactory {
   AttributeValue createAttribute(Node var1, Iterator var2) throws URISyntaxException, InvalidAttributeException;

   AttributeValue createAttribute(URI var1, Node var2, Iterator var3) throws URISyntaxException, InvalidAttributeException;
}
