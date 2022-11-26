package org.python.jline.console.completer;

import org.python.jline.internal.Preconditions;

public class EnumCompleter extends StringsCompleter {
   public EnumCompleter(Class source) {
      this(source, true);
   }

   public EnumCompleter(Class source, boolean toLowerCase) {
      Preconditions.checkNotNull(source);
      Enum[] var3 = (Enum[])source.getEnumConstants();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Enum n = var3[var5];
         this.getStrings().add(toLowerCase ? n.name().toLowerCase() : n.name());
      }

   }
}
