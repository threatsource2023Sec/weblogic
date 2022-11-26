package weblogic.apache.xerces.impl.dv.dtd;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import weblogic.apache.xerces.impl.dv.DatatypeValidator;

public class XML11DTDDVFactoryImpl extends DTDDVFactoryImpl {
   static final Hashtable fXML11BuiltInTypes = new Hashtable();

   public DatatypeValidator getBuiltInDV(String var1) {
      return fXML11BuiltInTypes.get(var1) != null ? (DatatypeValidator)fXML11BuiltInTypes.get(var1) : (DatatypeValidator)DTDDVFactoryImpl.fBuiltInTypes.get(var1);
   }

   public Hashtable getBuiltInTypes() {
      Hashtable var1 = (Hashtable)DTDDVFactoryImpl.fBuiltInTypes.clone();
      Iterator var2 = fXML11BuiltInTypes.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry var3 = (Map.Entry)var2.next();
         Object var4 = var3.getKey();
         Object var5 = var3.getValue();
         var1.put(var4, var5);
      }

      return var1;
   }

   static {
      fXML11BuiltInTypes.put("XML11ID", new XML11IDDatatypeValidator());
      XML11IDREFDatatypeValidator var0 = new XML11IDREFDatatypeValidator();
      fXML11BuiltInTypes.put("XML11IDREF", var0);
      fXML11BuiltInTypes.put("XML11IDREFS", new ListDatatypeValidator(var0));
      XML11NMTOKENDatatypeValidator var1 = new XML11NMTOKENDatatypeValidator();
      fXML11BuiltInTypes.put("XML11NMTOKEN", var1);
      fXML11BuiltInTypes.put("XML11NMTOKENS", new ListDatatypeValidator(var1));
   }
}
