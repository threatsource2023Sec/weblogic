package com.bea.xbean.values;

import com.bea.xbean.common.PrefixResolver;

public interface NamespaceManager extends PrefixResolver {
   String find_prefix_for_nsuri(String var1, String var2);
}
