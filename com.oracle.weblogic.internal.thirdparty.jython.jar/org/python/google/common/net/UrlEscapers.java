package org.python.google.common.net;

import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.escape.Escaper;

@GwtCompatible
public final class UrlEscapers {
   static final String URL_FORM_PARAMETER_OTHER_SAFE_CHARS = "-_.*";
   static final String URL_PATH_OTHER_SAFE_CHARS_LACKING_PLUS = "-._~!$'()*,;&=@:";
   private static final Escaper URL_FORM_PARAMETER_ESCAPER = new PercentEscaper("-_.*", true);
   private static final Escaper URL_PATH_SEGMENT_ESCAPER = new PercentEscaper("-._~!$'()*,;&=@:+", false);
   private static final Escaper URL_FRAGMENT_ESCAPER = new PercentEscaper("-._~!$'()*,;&=@:+/?", false);

   private UrlEscapers() {
   }

   public static Escaper urlFormParameterEscaper() {
      return URL_FORM_PARAMETER_ESCAPER;
   }

   public static Escaper urlPathSegmentEscaper() {
      return URL_PATH_SEGMENT_ESCAPER;
   }

   public static Escaper urlFragmentEscaper() {
      return URL_FRAGMENT_ESCAPER;
   }
}
