package org.apache.xml.security.stax.ext;

public interface ResourceResolverLookup {
   ResourceResolverLookup canResolve(String var1, String var2);

   ResourceResolver newInstance(String var1, String var2);
}
