package weblogic.apache.xerces.xni.parser;

import weblogic.apache.xerces.xni.XNIException;

public interface XMLErrorHandler {
   void warning(String var1, String var2, XMLParseException var3) throws XNIException;

   void error(String var1, String var2, XMLParseException var3) throws XNIException;

   void fatalError(String var1, String var2, XMLParseException var3) throws XNIException;
}
