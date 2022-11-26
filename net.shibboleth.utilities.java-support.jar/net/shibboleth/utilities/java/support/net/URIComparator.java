package net.shibboleth.utilities.java.support.net;

import javax.annotation.Nullable;

public interface URIComparator {
   boolean compare(@Nullable String var1, @Nullable String var2) throws URIException;
}
