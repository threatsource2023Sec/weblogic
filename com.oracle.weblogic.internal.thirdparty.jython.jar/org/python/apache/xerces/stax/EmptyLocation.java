package org.python.apache.xerces.stax;

import javax.xml.stream.Location;

public final class EmptyLocation implements Location {
   private static final EmptyLocation EMPTY_LOCATION_INSTANCE = new EmptyLocation();

   private EmptyLocation() {
   }

   public static EmptyLocation getInstance() {
      return EMPTY_LOCATION_INSTANCE;
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
