package oracle.jrockit.jfr.settings;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import oracle.jrockit.json.JSONElement;
import oracle.jrockit.json.JSONMember;
import oracle.jrockit.text.StringParse;

public class EventDefaultSet {
   private final List defaults;

   public EventDefaultSet(Reader r) throws URISyntaxException, IOException, ParseException {
      this.defaults = new ArrayList();
      JSONElement e = JSONElement.parse(r);
      LinkedList uriStack = new LinkedList();
      this.add(uriStack, e);
   }

   public EventDefaultSet(File f) throws IOException, URISyntaxException, ParseException {
      this((Reader)(new FileReader(f)));
   }

   public EventDefaultSet(String path) throws IOException, URISyntaxException, ParseException {
      this(new File(path));
   }

   public EventDefaultSet(Collection defaults) {
      this.defaults = new ArrayList();
      Iterator i$ = defaults.iterator();

      while(i$.hasNext()) {
         EventDefault d = (EventDefault)i$.next();
         this.add(d);
      }

   }

   public EventDefaultSet(EventDefault... defaults) {
      this((Collection)Arrays.asList(defaults));
   }

   public EventSetting get(URI uri) {
      return this.get(uri.toString());
   }

   public EventSetting get(String uri) {
      for(int i = this.defaults.size(); i > 0; --i) {
         EventDefault d = (EventDefault)this.defaults.get(i - 1);
         if (d.matches(uri)) {
            return d.getSetting();
         }
      }

      return null;
   }

   public List getAll() {
      return Collections.unmodifiableList(this.defaults);
   }

   private void add(LinkedList uriStack, JSONElement e) throws URISyntaxException, IOException {
      long threshold_ns = 0L;
      long period = 0L;
      boolean enabled = false;
      boolean stacktrace = false;
      int numValues = 0;
      if (e.isObject()) {
         Iterator i$ = e.iterator();

         while(i$.hasNext()) {
            JSONMember m = (JSONMember)i$.next();
            String s = m.getName();
            JSONElement v = m.getValue();
            if (v.isValue()) {
               if (uriStack.isEmpty()) {
                  throw new IllegalArgumentException("Syntax error: " + m);
               }

               s = s.toLowerCase();
               if (s.equals("enable")) {
                  enabled = v.booleanValue();
               } else if (s.equals("disable")) {
                  enabled = !v.booleanValue();
               } else if (s.equals("stacktrace")) {
                  stacktrace = v.booleanValue();
               } else if (s.equals("threshold")) {
                  threshold_ns = StringParse.nanos(v.stringValue());
               } else {
                  if (!s.equals("period")) {
                     throw new IllegalArgumentException("Bad value " + m);
                  }

                  period = StringParse.nanos(v.stringValue()) / 1000000L;
               }

               ++numValues;
            } else {
               URI uri = new URI(s);
               if (!uri.isAbsolute()) {
                  if (uriStack.isEmpty()) {
                     if (!uri.toString().startsWith("*")) {
                        throw new URISyntaxException(s, "Invalid URI : " + uri);
                     }
                  } else {
                     URI prev = (URI)uriStack.getLast();
                     if (!prev.toString().endsWith("/")) {
                        new URI(prev.toString() + "/");
                     }

                     uri = prev.resolve(s);
                  }
               }

               try {
                  uriStack.add(uri);
                  this.add(uriStack, v);
               } finally {
                  uriStack.removeLast();
               }
            }
         }

         if (numValues > 0) {
            assert !uriStack.isEmpty();

            EventSetting s = new EventSetting(0, enabled, stacktrace, threshold_ns, period);
            URI uri = (URI)uriStack.getLast();
            this.add(uri, s);
         }
      } else {
         if (e.isValue()) {
            throw new InternalError();
         }

         if (e.isArray()) {
            throw new IllegalArgumentException("Syntax error: " + e);
         }
      }

   }

   private void add(URI uri, EventSetting s) {
      this.add(new EventDefault(uri, s));
   }

   private void add(EventDefault d) {
      this.defaults.add(d);
   }

   public static EventDefaultSet find(String name) throws IOException, URISyntaxException, ParseException {
      String javahome = System.getProperty("java.home");
      File jfrLocation = new File(javahome, "lib/jfr");

      for(int i = 0; i < 2; ++i) {
         File file = new File(jfrLocation, name);
         if (file.exists()) {
            return new EventDefaultSet(file);
         }

         if (!name.endsWith(".jfs")) {
            file = new File(jfrLocation, name + ".jfs");
         }

         if (file.exists()) {
            return new EventDefaultSet(file);
         }

         String dev = System.getProperty("jrockit.launcher.dir");
         if (dev == null) {
            break;
         }

         jfrLocation = new File(dev);
      }

      return new EventDefaultSet(name);
   }
}
