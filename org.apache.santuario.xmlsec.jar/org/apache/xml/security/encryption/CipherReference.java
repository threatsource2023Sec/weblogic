package org.apache.xml.security.encryption;

import org.w3c.dom.Attr;

public interface CipherReference {
   String getURI();

   Attr getURIAsAttr();

   Transforms getTransforms();

   void setTransforms(Transforms var1);
}
