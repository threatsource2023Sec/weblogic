package weblogic.apache.xerces.stax;

import javax.xml.stream.Location;

public class ImmutableLocation implements Location {
   private final int fCharacterOffset;
   private final int fColumnNumber;
   private final int fLineNumber;
   private final String fPublicId;
   private final String fSystemId;

   public ImmutableLocation(Location var1) {
      this(var1.getCharacterOffset(), var1.getColumnNumber(), var1.getLineNumber(), var1.getPublicId(), var1.getSystemId());
   }

   public ImmutableLocation(int var1, int var2, int var3, String var4, String var5) {
      this.fCharacterOffset = var1;
      this.fColumnNumber = var2;
      this.fLineNumber = var3;
      this.fPublicId = var4;
      this.fSystemId = var5;
   }

   public int getCharacterOffset() {
      return this.fCharacterOffset;
   }

   public int getColumnNumber() {
      return this.fColumnNumber;
   }

   public int getLineNumber() {
      return this.fLineNumber;
   }

   public String getPublicId() {
      return this.fPublicId;
   }

   public String getSystemId() {
      return this.fSystemId;
   }
}
