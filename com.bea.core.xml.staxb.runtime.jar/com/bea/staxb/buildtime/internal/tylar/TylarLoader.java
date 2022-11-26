package com.bea.staxb.buildtime.internal.tylar;

import com.bea.xml.XmlException;
import java.io.IOException;
import java.net.URI;

public interface TylarLoader {
   Tylar load(ClassLoader var1) throws IOException, XmlException;

   /** @deprecated */
   @Deprecated
   Tylar load(URI var1) throws IOException, XmlException;

   Tylar load(URI[] var1) throws IOException, XmlException;
}
