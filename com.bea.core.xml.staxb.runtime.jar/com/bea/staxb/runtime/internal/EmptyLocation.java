package com.bea.staxb.runtime.internal;

import javax.xml.stream.Location;

final class EmptyLocation implements Location {
   private static final Location INSTANCE = new EmptyLocation();

   private EmptyLocation() {
   }

   public static Location getInstance() {
      return INSTANCE;
   }

   public int getLineNumber() {
      return -1;
   }

   public int getColumnNumber() {
      return -1;
   }

   public int getCharacterOffset() {
      return -1;
   }

   public String getPublicId() {
      return null;
   }

   public String getSystemId() {
      return null;
   }
}
