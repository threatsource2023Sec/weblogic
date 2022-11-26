package com.oracle.wls.shaded.org.apache.xpath;

import com.oracle.wls.shaded.org.apache.xml.utils.PrefixResolver;
import javax.xml.transform.SourceLocator;

public interface XPathFactory {
   XPath create(String var1, SourceLocator var2, PrefixResolver var3, int var4);
}
