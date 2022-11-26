package org.apache.openjpa.lib.util;

import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class FormatPreservingProperties extends Properties {
   private static Localizer _loc = Localizer.forPackage(FormatPreservingProperties.class);
   private char defaultEntryDelimiter;
   private boolean addWhitespaceAfterDelimiter;
   private boolean allowDuplicates;
   private boolean insertTimestamp;
   private PropertySource source;
   private LinkedHashSet newKeys;
   private HashSet modifiedKeys;
   private transient boolean isNotDeserializing;
   private transient boolean isLoading;

   public FormatPreservingProperties() {
      this((Properties)null);
   }

   public FormatPreservingProperties(Properties defaults) {
      super(defaults);
      this.defaultEntryDelimiter = ':';
      this.addWhitespaceAfterDelimiter = true;
      this.allowDuplicates = false;
      this.insertTimestamp = false;
      this.newKeys = new LinkedHashSet();
      this.modifiedKeys = new HashSet();
      this.isNotDeserializing = true;
      this.isLoading = false;
   }

   public void setDefaultEntryDelimiter(char defaultEntryDelimiter) {
      this.defaultEntryDelimiter = defaultEntryDelimiter;
   }

   public char getDefaultEntryDelimiter() {
      return this.defaultEntryDelimiter;
   }

   public void setAddWhitespaceAfterDelimiter(boolean add) {
      this.addWhitespaceAfterDelimiter = add;
   }

   public boolean getAddWhitespaceAfterDelimiter() {
      return this.addWhitespaceAfterDelimiter;
   }

   public void setInsertTimestamp(boolean insertTimestamp) {
      this.insertTimestamp = insertTimestamp;
   }

   public boolean getInsertTimestamp() {
      return this.insertTimestamp;
   }

   public void setAllowDuplicates(boolean allowDuplicates) {
      this.allowDuplicates = allowDuplicates;
   }

   public boolean getAllowDuplicates() {
      return this.allowDuplicates;
   }

   public String getProperty(String key) {
      return super.getProperty(key);
   }

   public String getProperty(String key, String defaultValue) {
      return super.getProperty(key, defaultValue);
   }

   public Object setProperty(String key, String value) {
      return this.put(key, value);
   }

   public void putAll(Map m) {
      Iterator iter = m.entrySet().iterator();

      while(iter.hasNext()) {
         Map.Entry e = (Map.Entry)iter.next();
         this.put(e.getKey(), e.getValue());
      }

   }

   public Object remove(Object key) {
      this.newKeys.remove(key);
      return super.remove(key);
   }

   public void clear() {
      super.clear();
      if (this.source != null) {
         this.source.clear();
      }

      this.newKeys.clear();
      this.modifiedKeys.clear();
   }

   public Object clone() {
      FormatPreservingProperties c = (FormatPreservingProperties)super.clone();
      if (this.source != null) {
         c.source = (PropertySource)this.source.clone();
      }

      if (this.modifiedKeys != null) {
         c.modifiedKeys = (HashSet)this.modifiedKeys.clone();
      }

      if (this.newKeys != null) {
         c.newKeys = new LinkedHashSet();
         c.newKeys.addAll(this.newKeys);
      }

      return c;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.defaultWriteObject();
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      this.isNotDeserializing = true;
   }

   public Object put(Object key, Object val) {
      Object o = super.put(key, val);
      if (!this.isLoading && this.isNotDeserializing && !val.equals(o)) {
         if (o != null) {
            this.modifiedKeys.add(key);
         } else if (!this.newKeys.contains(key)) {
            this.newKeys.add(key);
         }
      }

      return o;
   }

   public void load(InputStream in) throws IOException {
      this.isLoading = true;

      try {
         this.loadProperties(in);
      } finally {
         this.isLoading = false;
      }

   }

   private void loadProperties(InputStream in) throws IOException {
      this.source = new PropertySource();
      PropertyLineReader reader = new PropertyLineReader(in, this.source);
      Set loadedKeys = new HashSet();

      PropertyLine l;
      while((l = reader.readPropertyLine()) != null && this.source.add(l)) {
         String line = l.line.toString();
         char c = 0;

         int pos;
         for(pos = 0; pos < line.length() && isSpace(c = line.charAt(pos)); ++pos) {
         }

         if (line.length() - pos != 0 && line.charAt(pos) != '#' && line.charAt(pos) != '!') {
            StringBuffer key = new StringBuffer();

            while(pos < line.length() && !isSpace(c = line.charAt(pos++)) && c != '=' && c != ':') {
               if (c == '\\') {
                  if (pos == line.length()) {
                     l.append(line = reader.readLine());

                     for(pos = 0; pos < line.length() && isSpace(c = line.charAt(pos)); ++pos) {
                     }
                  } else {
                     pos = readEscape(line, pos, key);
                  }
               } else {
                  key.append(c);
               }
            }

            boolean isDelim;
            for(isDelim = c == ':' || c == '='; pos < line.length() && isSpace(c = line.charAt(pos)); ++pos) {
            }

            if (!isDelim && (c == ':' || c == '=')) {
               ++pos;

               while(pos < line.length() && isSpace(line.charAt(pos))) {
                  ++pos;
               }
            }

            StringBuffer element = new StringBuffer(line.length() - pos);

            while(pos < line.length()) {
               c = line.charAt(pos++);
               if (c != '\\') {
                  element.append(c);
               } else if (pos != line.length()) {
                  pos = readEscape(line, pos, element);
               } else {
                  l.append(line = reader.readLine());
                  if (line == null) {
                     break;
                  }

                  for(pos = 0; pos < line.length() && isSpace(line.charAt(pos)); ++pos) {
                  }

                  element.ensureCapacity(line.length() - pos + element.length());
               }
            }

            if (!loadedKeys.add(key.toString()) && !this.allowDuplicates) {
               throw new DuplicateKeyException(key.toString(), this.getProperty(key.toString()), element.toString());
            }

            l.setPropertyKey(key.toString());
            l.setPropertyValue(element.toString());
            this.put(key.toString(), element.toString());
         }
      }

   }

   private static int readEscape(String source, int pos, StringBuffer value) {
      char c = source.charAt(pos++);
      switch (c) {
         case 'f':
            value.append('\f');
            break;
         case 'n':
            value.append('\n');
            break;
         case 'r':
            value.append('\r');
            break;
         case 't':
            value.append('\t');
            break;
         case 'u':
            if (pos + 4 <= source.length()) {
               char uni = (char)Integer.parseInt(source.substring(pos, pos + 4), 16);
               value.append(uni);
               pos += 4;
            }
            break;
         default:
            value.append(c);
      }

      return pos;
   }

   private static boolean isSpace(char ch) {
      return Character.isWhitespace(ch);
   }

   public void save(OutputStream out, String header) {
      try {
         this.store(out, header);
      } catch (IOException var4) {
      }

   }

   public void store(OutputStream out, String header) throws IOException {
      boolean endWithNewline = this.source != null && this.source.endsInNewline;
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "ISO-8859-1"), false);
      if (header != null) {
         writer.println("#" + header);
      }

      if (this.insertTimestamp) {
         writer.println("#" + Calendar.getInstance().getTime());
      }

      List lines = new LinkedList();
      if (this.source != null) {
         lines.addAll(this.source);
      }

      LinkedHashSet keys = new LinkedHashSet();
      keys.addAll(this.newKeys);
      keys.addAll(this.keySet());
      lines.addAll(keys);
      keys.remove((Object)null);
      boolean needsNewline = false;
      Iterator i = lines.iterator();

      while(true) {
         while(i.hasNext()) {
            Object next = i.next();
            if (next instanceof PropertyLine) {
               if (((PropertyLine)next).write(writer, keys, needsNewline)) {
                  needsNewline = i.hasNext();
               }
            } else if (next instanceof String) {
               String key = (String)next;
               if (keys.remove(key) && this.writeProperty(key, writer, needsNewline)) {
                  needsNewline = i.hasNext() && keys.size() > 0;
                  endWithNewline = true;
               }
            }
         }

         if (endWithNewline) {
            writer.println();
         }

         writer.flush();
         return;
      }
   }

   private boolean writeProperty(String key, PrintWriter writer, boolean needsNewline) {
      StringBuffer s = new StringBuffer();
      if (key == null) {
         return false;
      } else {
         String val = this.getProperty(key);
         if (val == null) {
            return false;
         } else {
            formatValue(key, s, true);
            s.append(this.defaultEntryDelimiter);
            if (this.addWhitespaceAfterDelimiter) {
               s.append(' ');
            }

            formatValue(val, s, false);
            if (needsNewline) {
               writer.println();
            }

            writer.print(s);
            return true;
         }
      }
   }

   private static void formatValue(String str, StringBuffer buf, boolean isKey) {
      if (isKey) {
         buf.setLength(0);
         buf.ensureCapacity(str.length());
      } else {
         buf.ensureCapacity(buf.length() + str.length());
      }

      boolean escapeSpace = true;
      int size = str.length();

      for(int i = 0; i < size; ++i) {
         char c = str.charAt(i);
         if (c == '\n') {
            buf.append("\\n");
         } else if (c == '\r') {
            buf.append("\\r");
         } else if (c == '\t') {
            buf.append("\\t");
         } else if (c == '\f') {
            buf.append("\\f");
         } else if (c == ' ') {
            buf.append(escapeSpace ? "\\ " : " ");
         } else if (c != '\\' && c != '!' && c != '#' && c != '=' && c != ':') {
            if (c >= ' ' && c <= '~') {
               buf.append(c);
            } else {
               buf.append("\\u0000".substring(0, 6 - Integer.toHexString(c).length())).append(Integer.toHexString(c));
            }
         } else {
            buf.append('\\').append(c);
         }

         if (c != ' ') {
            escapeSpace = isKey;
         }
      }

   }

   static class PropertySource extends LinkedList implements Cloneable, Serializable {
      private boolean endsInNewline = false;
   }

   private static class LineEndingStream extends FilterInputStream {
      private final PropertySource source;

      LineEndingStream(InputStream in, PropertySource source) {
         super(in);
         this.source = source;
      }

      public int read() throws IOException {
         int c = super.read();
         this.source.endsInNewline = c == 10 || c == 13;
         return c;
      }

      public int read(byte[] b, int off, int len) throws IOException {
         int n = super.read(b, off, len);
         if (n > 0) {
            this.source.endsInNewline = b[n + off - 1] == 10 || b[n + off - 1] == 13;
         }

         return n;
      }
   }

   private class PropertyLineReader extends BufferedReader {
      public PropertyLineReader(InputStream in, PropertySource source) throws IOException {
         super(new InputStreamReader(new LineEndingStream(in, source), "ISO-8859-1"));
      }

      public PropertyLine readPropertyLine() throws IOException {
         String l = this.readLine();
         if (l == null) {
            return null;
         } else {
            PropertyLine pl = FormatPreservingProperties.this.new PropertyLine(l);
            return pl;
         }
      }
   }

   private class PropertyLine implements Serializable {
      private final StringBuffer line = new StringBuffer();
      private String propertyKey;
      private String propertyValue;

      public PropertyLine(String line) {
         this.line.append(line);
      }

      public void append(String newline) {
         this.line.append(J2DoPrivHelper.getLineSeparator());
         this.line.append(newline);
      }

      public void setPropertyKey(String propertyKey) {
         this.propertyKey = propertyKey;
      }

      public String getPropertyKey() {
         return this.propertyKey;
      }

      public void setPropertyValue(String propertyValue) {
         this.propertyValue = propertyValue;
      }

      public String getPropertyValue() {
         return this.propertyValue;
      }

      public boolean write(PrintWriter pw, Collection keys, boolean needsNewline) {
         if (this.propertyKey == null) {
            if (needsNewline) {
               pw.println();
            }

            pw.print(this.line.toString());
            return true;
         } else if (this.propertyValue != null && FormatPreservingProperties.this.containsKey(this.propertyKey) && (this.propertyValue.equals(FormatPreservingProperties.this.getProperty(this.propertyKey)) || !FormatPreservingProperties.this.newKeys.contains(this.propertyKey) && !FormatPreservingProperties.this.modifiedKeys.contains(this.propertyKey))) {
            if (needsNewline) {
               pw.println();
            }

            pw.print(this.line.toString());
            keys.remove(this.propertyKey);
            return true;
         } else if (FormatPreservingProperties.this.containsKey(this.propertyKey) && (FormatPreservingProperties.this.modifiedKeys.contains(this.propertyKey) || FormatPreservingProperties.this.newKeys.contains(this.propertyKey))) {
            while(keys.remove(this.propertyKey)) {
            }

            return FormatPreservingProperties.this.writeProperty(this.propertyKey, pw, needsNewline);
         } else {
            return false;
         }
      }
   }

   public static class DuplicateKeyException extends RuntimeException {
      public DuplicateKeyException(String key, Object firstVal, String secondVal) {
         super(FormatPreservingProperties._loc.get("dup-key", key, firstVal, secondVal).getMessage());
      }
   }
}
