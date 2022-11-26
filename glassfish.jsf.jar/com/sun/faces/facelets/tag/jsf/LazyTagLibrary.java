package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.tag.TagLibraryImpl;

public abstract class LazyTagLibrary extends TagLibraryImpl {
   public LazyTagLibrary(String namespace) {
      super(namespace);
   }

   public abstract boolean tagLibraryForNSExists(String var1);
}
