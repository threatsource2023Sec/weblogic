package oracle.jrockit.jfr.events;

import com.oracle.jrockit.jfr.DurationEvent;
import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import com.oracle.jrockit.jfr.InvalidValueException;
import com.oracle.jrockit.jfr.RequestableEvent;
import com.oracle.jrockit.jfr.TimedEvent;
import java.net.URI;
import java.util.HashSet;

public class JavaEventDescriptor extends DataStructureDescriptor implements EventDescriptor {
   private final Class eventClass;
   private final String name;
   private final String path;
   private final URI uri;
   private final String description;
   private final boolean timed;
   private final boolean requestable;
   private final boolean hasThread;
   private final boolean stacktrace;
   private final boolean hasStartTime;
   private final int id;

   public JavaEventDescriptor(Class eventClass, URI base, int id) throws InvalidEventDefinitionException, InvalidValueException {
      super(eventClass);
      EventDefinition e = (EventDefinition)eventClass.getAnnotation(EventDefinition.class);
      if (e == null) {
         throw new IllegalArgumentException("Missing " + EventDefinition.class.getName() + " annotation");
      } else {
         String name = e.name();
         if (name.length() == 0) {
            name = eventClass.getName();
            name = name.substring(name.lastIndexOf(46) + 1);
         }

         this.id = id;
         this.eventClass = eventClass;
         this.name = name;
         String path = e.path();
         if (path.length() == 0) {
            path = name;
         }

         this.path = path;
         this.description = e.description();
         this.requestable = RequestableEvent.class.isAssignableFrom(eventClass);
         this.hasThread = e.thread();
         this.stacktrace = e.stacktrace();
         this.timed = TimedEvent.class.isAssignableFrom(eventClass);
         this.hasStartTime = this.timed || DurationEvent.class.isAssignableFrom(eventClass);
         this.uri = base.resolve(this.path);
      }
   }

   public JavaEventDescriptor(int id, String name, String description, String path, URI uri, boolean hasStartTime, boolean hasThread, boolean stacktrace, boolean timed, boolean requestable, ValueDescriptor... values) throws InvalidEventDefinitionException {
      super(values);
      this.name = name;
      this.path = path == null ? "" : path;
      this.description = description;
      this.timed = timed;
      this.hasStartTime = hasStartTime;
      this.requestable = requestable;
      this.hasThread = hasThread;
      this.stacktrace = stacktrace;
      this.id = id;
      this.eventClass = null;
      this.uri = uri;
      this.checkRelations();
   }

   public JavaEventDescriptor(Class eventClass, URI base, int id, String name, String description, String path, boolean hasThread, boolean stacktrace, ValueDescriptor... values) throws InvalidEventDefinitionException {
      super(values);
      this.name = name;
      this.path = path == null ? "" : path;
      this.description = description;
      this.timed = TimedEvent.class.isAssignableFrom(eventClass);
      this.requestable = RequestableEvent.class.isAssignableFrom(eventClass);
      this.hasStartTime = this.timed || DurationEvent.class.isAssignableFrom(eventClass);
      this.hasThread = hasThread;
      this.stacktrace = stacktrace;
      this.id = id;
      this.eventClass = eventClass;
      this.uri = base.resolve(this.path);
      this.checkRelations();
   }

   private void checkRelations() throws InvalidEventDefinitionException {
      HashSet set = new HashSet();
      ValueDescriptor[] arr$ = this.getValues();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ValueDescriptor d = arr$[i$];
         String s = d.getRelationKey();
         if (s != null && set.contains(s)) {
            throw new InvalidEventDefinitionException("Duplicate relation key " + s + " in event");
         }

         set.add(s);
      }

   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public String getPath() {
      return this.path;
   }

   public URI getURI() {
      return this.uri;
   }

   public boolean isTimed() {
      return this.timed;
   }

   public boolean hasStartTime() {
      return this.hasStartTime;
   }

   public boolean isRequestable() {
      return this.requestable;
   }

   public boolean hasThread() {
      return this.hasThread;
   }

   public boolean hasStackTrace() {
      return this.stacktrace;
   }

   public int getId() {
      return this.id;
   }

   public Class getEventClass() {
      return this.eventClass;
   }

   void describe(StringBuilder buf) {
      if (this.timed) {
         buf.append("{ Timed event id=");
      } else if (this.requestable) {
         buf.append("{ Requestable event id=");
      } else {
         buf.append("{ Event id=");
      }

      buf.append(this.id).append(", name=").append(this.name);
      buf.append(", uri=").append(this.uri);
      if (this.eventClass != null) {
         buf.append(", class=").append(this.eventClass.getName());
      }

      if (this.hasThread) {
         buf.append(", thread");
      }

   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      this.describe(buf);
      if (this.stacktrace) {
         buf.append(", stacktrace");
      }

      buf.append(" }");
      return buf.toString();
   }
}
