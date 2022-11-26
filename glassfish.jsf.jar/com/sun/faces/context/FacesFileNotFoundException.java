package com.sun.faces.context;

import java.io.FileNotFoundException;

public class FacesFileNotFoundException extends FileNotFoundException {
   private static final long serialVersionUID = 7593137790944497673L;

   public FacesFileNotFoundException(String s) {
      super(s);
   }

   public FacesFileNotFoundException() {
   }
}
