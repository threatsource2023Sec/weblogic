package org.python.apache.xerces.impl.xs;

import org.python.apache.xerces.dom.DOMMessageFormatter;
import org.python.apache.xerces.dom.PSVIDOMImplementationImpl;
import org.python.apache.xerces.impl.xs.util.LSInputListImpl;
import org.python.apache.xerces.impl.xs.util.StringListImpl;
import org.python.apache.xerces.xs.LSInputList;
import org.python.apache.xerces.xs.StringList;
import org.python.apache.xerces.xs.XSException;
import org.python.apache.xerces.xs.XSImplementation;
import org.python.apache.xerces.xs.XSLoader;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.ls.LSInput;

public class XSImplementationImpl extends PSVIDOMImplementationImpl implements XSImplementation {
   static final XSImplementationImpl singleton = new XSImplementationImpl();

   public static DOMImplementation getDOMImplementation() {
      return singleton;
   }

   public boolean hasFeature(String var1, String var2) {
      return var1.equalsIgnoreCase("XS-Loader") && (var2 == null || var2.equals("1.0")) || super.hasFeature(var1, var2);
   }

   public XSLoader createXSLoader(StringList var1) throws XSException {
      XSLoaderImpl var2 = new XSLoaderImpl();
      if (var1 == null) {
         return var2;
      } else {
         for(int var3 = 0; var3 < var1.getLength(); ++var3) {
            if (!var1.item(var3).equals("1.0")) {
               String var4 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[]{var1.item(var3)});
               throw new XSException((short)1, var4);
            }
         }

         return var2;
      }
   }

   public StringList createStringList(String[] var1) {
      int var2 = var1 != null ? var1.length : 0;
      return var2 != 0 ? new StringListImpl((String[])var1.clone(), var2) : StringListImpl.EMPTY_LIST;
   }

   public LSInputList createLSInputList(LSInput[] var1) {
      int var2 = var1 != null ? var1.length : 0;
      return var2 != 0 ? new LSInputListImpl((LSInput[])var1.clone(), var2) : LSInputListImpl.EMPTY_LIST;
   }

   public StringList getRecognizedVersions() {
      StringListImpl var1 = new StringListImpl(new String[]{"1.0"}, 1);
      return var1;
   }
}
