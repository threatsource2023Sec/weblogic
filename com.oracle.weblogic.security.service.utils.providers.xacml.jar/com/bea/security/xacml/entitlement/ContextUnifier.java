package com.bea.security.xacml.entitlement;

import java.util.Map;
import weblogic.security.service.ContextHandler;

public interface ContextUnifier {
   Map unifyContext(ContextHandler var1, Map var2);
}
