package com.bea.security.providers.xacml.entitlement.parser;

import com.bea.security.xacml.IOException;
import java.io.Writer;

public interface Expression {
   void writePersistantForm(Writer var1) throws IOException;
}
