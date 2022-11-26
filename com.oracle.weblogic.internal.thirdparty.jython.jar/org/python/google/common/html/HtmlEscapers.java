package org.python.google.common.html;

import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.escape.Escaper;
import org.python.google.common.escape.Escapers;

@Beta
@GwtCompatible
public final class HtmlEscapers {
   private static final Escaper HTML_ESCAPER = Escapers.builder().addEscape('"', "&quot;").addEscape('\'', "&#39;").addEscape('&', "&amp;").addEscape('<', "&lt;").addEscape('>', "&gt;").build();

   public static Escaper htmlEscaper() {
      return HTML_ESCAPER;
   }

   private HtmlEscapers() {
   }
}
