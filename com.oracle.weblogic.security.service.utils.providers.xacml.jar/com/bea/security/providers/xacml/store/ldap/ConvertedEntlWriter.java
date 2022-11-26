package com.bea.security.providers.xacml.store.ldap;

import java.io.OutputStream;
import java.util.List;

public interface ConvertedEntlWriter {
   void writeConvertedEntlPolicies(List var1, OutputStream var2, boolean var3) throws EntlConversionException;
}
