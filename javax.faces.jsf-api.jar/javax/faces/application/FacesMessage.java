package javax.faces.application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacesMessage implements Serializable {
   public static final String FACES_MESSAGES = "javax.faces.Messages";
   private static final String SEVERITY_INFO_NAME = "INFO";
   public static final Severity SEVERITY_INFO = new Severity("INFO");
   private static final String SEVERITY_WARN_NAME = "WARN";
   public static final Severity SEVERITY_WARN = new Severity("WARN");
   private static final String SEVERITY_ERROR_NAME = "ERROR";
   public static final Severity SEVERITY_ERROR = new Severity("ERROR");
   private static final String SEVERITY_FATAL_NAME = "FATAL";
   public static final Severity SEVERITY_FATAL = new Severity("FATAL");
   private static final Severity[] values;
   public static final List VALUES;
   private static Map _MODIFIABLE_MAP;
   public static final Map VALUES_MAP;
   private static final long serialVersionUID = -1180773928220076822L;
   private Severity severity;
   private String summary;
   private String detail;

   public FacesMessage() {
      this.severity = SEVERITY_INFO;
      this.summary = null;
      this.detail = null;
   }

   public FacesMessage(String summary) {
      this.severity = SEVERITY_INFO;
      this.summary = null;
      this.detail = null;
      this.setSummary(summary);
   }

   public FacesMessage(String summary, String detail) {
      this.severity = SEVERITY_INFO;
      this.summary = null;
      this.detail = null;
      this.setSummary(summary);
      this.setDetail(detail);
   }

   public FacesMessage(Severity severity, String summary, String detail) {
      this.severity = SEVERITY_INFO;
      this.summary = null;
      this.detail = null;
      this.setSeverity(severity);
      this.setSummary(summary);
      this.setDetail(detail);
   }

   public String getDetail() {
      return this.detail == null ? this.summary : this.detail;
   }

   public void setDetail(String detail) {
      this.detail = detail;
   }

   public Severity getSeverity() {
      return this.severity;
   }

   public void setSeverity(Severity severity) {
      if (severity.getOrdinal() >= SEVERITY_INFO.getOrdinal() && severity.getOrdinal() <= SEVERITY_FATAL.getOrdinal()) {
         this.severity = severity;
      } else {
         throw new IllegalArgumentException(String.valueOf(severity));
      }
   }

   public String getSummary() {
      return this.summary;
   }

   public void setSummary(String summary) {
      this.summary = summary;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeInt(this.getSeverity().getOrdinal());
      out.writeObject(this.getSummary());
      out.writeObject(this.getDetail());
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this.severity = SEVERITY_INFO;
      this.summary = null;
      this.detail = null;
      int ordinal = in.readInt();
      if (ordinal == SEVERITY_INFO.getOrdinal()) {
         this.setSeverity(SEVERITY_INFO);
      } else if (ordinal == SEVERITY_WARN.getOrdinal()) {
         this.setSeverity(SEVERITY_WARN);
      } else if (ordinal == SEVERITY_ERROR.getOrdinal()) {
         this.setSeverity(SEVERITY_ERROR);
      } else if (ordinal == SEVERITY_FATAL.getOrdinal()) {
         this.setSeverity(SEVERITY_FATAL);
      }

      this.setSummary((String)in.readObject());
      this.setDetail((String)in.readObject());
   }

   static {
      values = new Severity[]{SEVERITY_INFO, SEVERITY_WARN, SEVERITY_ERROR, SEVERITY_FATAL};
      VALUES = Collections.unmodifiableList(Arrays.asList(values));
      _MODIFIABLE_MAP = new HashMap(4, 1.0F);
      int i = 0;

      for(int len = values.length; i < len; ++i) {
         _MODIFIABLE_MAP.put(values[i].severityName, values[i]);
      }

      VALUES_MAP = Collections.unmodifiableMap(_MODIFIABLE_MAP);
   }

   public static class Severity implements Comparable {
      private final int ordinal;
      String severityName;
      private static int nextOrdinal = 0;

      private Severity(String newSeverityName) {
         this.ordinal = nextOrdinal++;
         this.severityName = null;
         this.severityName = newSeverityName;
      }

      public int compareTo(Object other) {
         return this.ordinal - ((Severity)other).ordinal;
      }

      public int getOrdinal() {
         return this.ordinal;
      }

      public String toString() {
         return null == this.severityName ? String.valueOf(this.ordinal) : this.severityName + ' ' + this.ordinal;
      }

      // $FF: synthetic method
      Severity(String x0, Object x1) {
         this(x0);
      }
   }
}
