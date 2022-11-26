package weblogic.apache.xerces.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import weblogic.apache.xerces.dom3.as.ASModel;
import weblogic.apache.xerces.dom3.as.DOMASBuilder;
import weblogic.apache.xerces.dom3.as.DOMASWriter;
import weblogic.apache.xerces.dom3.as.DOMImplementationAS;
import weblogic.apache.xerces.parsers.DOMASBuilderImpl;

/** @deprecated */
public class ASDOMImplementationImpl extends DOMImplementationImpl implements DOMImplementationAS {
   static final ASDOMImplementationImpl singleton = new ASDOMImplementationImpl();

   public static DOMImplementation getDOMImplementation() {
      return singleton;
   }

   public ASModel createAS(boolean var1) {
      return new ASModelImpl(var1);
   }

   public DOMASBuilder createDOMASBuilder() {
      return new DOMASBuilderImpl();
   }

   public DOMASWriter createDOMASWriter() {
      String var1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", (Object[])null);
      throw new DOMException((short)9, var1);
   }
}
