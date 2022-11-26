package net.shibboleth.utilities.java.support.primitive;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;

public class LangBearingString extends Pair {
   public LangBearingString(@Nullable String value) {
      super(value, (Object)null);
   }

   public LangBearingString(@Nullable String value, @Nullable @NotEmpty String lang) {
      super(value, StringSupport.trimOrNull(lang));
   }

   @Nullable
   public String getValue() {
      return (String)this.getFirst();
   }

   @Nullable
   @NotEmpty
   public String getLang() {
      return (String)this.getSecond();
   }

   public void setSecond(@Nullable String newSecond) {
      super.setSecond(StringSupport.trimOrNull(newSecond));
   }

   public String toString() {
      return this.getValue();
   }
}
