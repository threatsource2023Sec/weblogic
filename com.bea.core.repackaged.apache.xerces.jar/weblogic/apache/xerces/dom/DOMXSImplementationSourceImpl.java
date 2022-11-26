package weblogic.apache.xerces.dom;

import java.util.ArrayList;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;
import weblogic.apache.xerces.impl.xs.XSImplementationImpl;

public class DOMXSImplementationSourceImpl extends DOMImplementationSourceImpl {
   public DOMImplementation getDOMImplementation(String var1) {
      DOMImplementation var2 = super.getDOMImplementation(var1);
      if (var2 != null) {
         return var2;
      } else {
         var2 = PSVIDOMImplementationImpl.getDOMImplementation();
         if (this.testImpl(var2, var1)) {
            return var2;
         } else {
            var2 = XSImplementationImpl.getDOMImplementation();
            return this.testImpl(var2, var1) ? var2 : null;
         }
      }
   }

   public DOMImplementationList getDOMImplementationList(String var1) {
      ArrayList var2 = new ArrayList();
      DOMImplementationList var3 = super.getDOMImplementationList(var1);

      for(int var4 = 0; var4 < var3.getLength(); ++var4) {
         var2.add(var3.item(var4));
      }

      DOMImplementation var5 = PSVIDOMImplementationImpl.getDOMImplementation();
      if (this.testImpl(var5, var1)) {
         var2.add(var5);
      }

      var5 = XSImplementationImpl.getDOMImplementation();
      if (this.testImpl(var5, var1)) {
         var2.add(var5);
      }

      return new DOMImplementationListImpl(var2);
   }
}
