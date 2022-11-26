package org.apache.jcp.xml.dsig.internal.dom;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import org.w3c.dom.Node;

public abstract class DOMStructure implements XMLStructure {
   public final boolean isFeatureSupported(String feature) {
      if (feature == null) {
         throw new NullPointerException();
      } else {
         return false;
      }
   }

   public abstract void marshal(Node var1, String var2, DOMCryptoContext var3) throws MarshalException;
}
