package weblogic.j2ee.descriptor;

public interface JspPropertyGroupBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getDisplayNames();

   void addDisplayName(String var1);

   void removeDisplayName(String var1);

   void setDisplayNames(String[] var1);

   IconBean[] getIcons();

   IconBean createIcon();

   void destroyIcon(IconBean var1);

   String[] getUrlPatterns();

   void addUrlPattern(String var1);

   void removeUrlPattern(String var1);

   void setUrlPatterns(String[] var1);

   boolean isElIgnored();

   void setElIgnored(boolean var1);

   boolean isElIgnoredSet();

   String getPageEncoding();

   void setPageEncoding(String var1);

   boolean isScriptingInvalid();

   void setScriptingInvalid(boolean var1);

   boolean isScriptingInvalidSet();

   boolean isIsXml();

   void setIsXml(boolean var1);

   boolean isIsXmlSet();

   String[] getIncludePreludes();

   void addIncludePrelude(String var1);

   void removeIncludePrelude(String var1);

   void setIncludePreludes(String[] var1);

   String[] getIncludeCodas();

   void addIncludeCoda(String var1);

   void removeIncludeCoda(String var1);

   void setIncludeCodas(String[] var1);

   boolean isDeferredSyntaxAllowedAsLiteral();

   void setDeferredSyntaxAllowedAsLiteral(boolean var1);

   boolean isDeferredSyntaxAllowedAsLiteralSet();

   boolean isTrimDirectiveWhitespaces();

   void setTrimDirectiveWhitespaces(boolean var1);

   boolean isTrimDirectiveWhitespacesSet();

   String getDefaultContentType();

   void setDefaultContentType(String var1);

   String getBuffer();

   void setBuffer(String var1);

   boolean isErrorOnUndeclaredNamespace();

   void setErrorOnUndeclaredNamespace(boolean var1);

   boolean isErrorOnUndeclaredNamespaceSet();

   String getId();

   void setId(String var1);
}
