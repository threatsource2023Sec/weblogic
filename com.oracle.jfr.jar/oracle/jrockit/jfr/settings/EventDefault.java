package oracle.jrockit.jfr.settings;

import java.net.URI;
import java.util.regex.Matcher;
import oracle.jrockit.text.GlobPattern;

public final class EventDefault {
   private final Matcher matcher;
   private final URI pattern;
   private final EventSetting setting;
   private final String exact;

   public EventDefault(URI pattern, EventSetting setting) {
      String s = pattern.toString();
      boolean x = s.indexOf(42) == -1;
      this.matcher = x ? null : GlobPattern.compile(s).matcher("");
      this.pattern = pattern;
      this.setting = setting;
      this.exact = x ? pattern.toString() : null;
   }

   public boolean matches(URI eventURI) {
      return this.matches(eventURI.toString());
   }

   public boolean matches(String eventURI) {
      return this.exact != null ? this.exact.equals(eventURI) : this.matcher.reset(eventURI).matches();
   }

   public URI getPattern() {
      return this.pattern;
   }

   public EventSetting getSetting() {
      return this.setting;
   }

   public boolean isExact() {
      return this.exact != null;
   }

   public String toString() {
      return this.pattern + " : " + this.setting;
   }
}
