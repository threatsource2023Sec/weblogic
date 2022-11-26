package org.apache.xmlbeans.impl.common;

import java.net.URI;
import java.util.AbstractCollection;
import java.util.Collections;
import java.util.Iterator;
import org.apache.xmlbeans.XmlError;

public class XmlErrorPrinter extends AbstractCollection {
   private boolean _noisy;
   private URI _baseURI;

   public XmlErrorPrinter(boolean noisy, URI baseURI) {
      this._noisy = noisy;
      this._baseURI = baseURI;
   }

   public boolean add(Object o) {
      if (o instanceof XmlError) {
         XmlError err = (XmlError)o;
         if (err.getSeverity() != 0 && err.getSeverity() != 1) {
            if (this._noisy) {
               System.out.println(err.toString(this._baseURI));
            }
         } else {
            System.err.println(err.toString(this._baseURI));
         }
      }

      return false;
   }

   public Iterator iterator() {
      return Collections.EMPTY_LIST.iterator();
   }

   public int size() {
      return 0;
   }
}
