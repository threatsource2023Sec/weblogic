package org.apache.oro.text.perl;

import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Substitution;

final class ParsedSubstitutionEntry {
   int _numSubstitutions;
   Pattern _pattern;
   Perl5Substitution _substitution;

   ParsedSubstitutionEntry(Pattern var1, Perl5Substitution var2, int var3) {
      this._numSubstitutions = var3;
      this._substitution = var2;
      this._pattern = var1;
   }
}
