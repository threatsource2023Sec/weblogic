package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Predicate;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public class RegexPredicate implements Predicate {
   @Nullable
   private Pattern pattern;

   public RegexPredicate(@Nonnull Pattern p) {
      this.pattern = p;
   }

   public RegexPredicate(@Nonnull @NotEmpty String s) {
      this.pattern = Pattern.compile(s);
   }

   public boolean apply(CharSequence input) {
      return input == null ? false : this.pattern.matcher(input).matches();
   }
}
