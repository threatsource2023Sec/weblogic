package weblogic.apache.org.apache.velocity.runtime.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.commons.collections.ExtendedProperties;

/** @deprecated */
public class Configuration extends Hashtable {
   private ExtendedProperties deprecationCrutch;
   private Configuration defaults;
   protected String file;
   protected String basePath;
   protected String fileSeparator;
   protected boolean isInitialized;
   protected static String include = "include";
   protected ArrayList keysAsListed;

   public Configuration() {
      this.deprecationCrutch = new ExtendedProperties();
      this.fileSeparator = System.getProperty("file.separator");
      this.isInitialized = false;
      this.keysAsListed = new ArrayList();
   }

   public Configuration(String file) throws IOException {
      this(file, (String)null);
   }

   public Configuration(String file, String defaultFile) throws IOException {
      this.deprecationCrutch = new ExtendedProperties();
      this.fileSeparator = System.getProperty("file.separator");
      this.isInitialized = false;
      this.keysAsListed = new ArrayList();
      this.file = file;
      this.basePath = (new File(file)).getAbsolutePath();
      this.basePath = this.basePath.substring(0, this.basePath.lastIndexOf(this.fileSeparator) + 1);
      this.load(new FileInputStream(file));
      if (defaultFile != null) {
         this.defaults = new Configuration(defaultFile);
      }

   }

   private void init(Configuration exp) throws IOException {
      this.isInitialized = true;
   }

   public boolean isInitialized() {
      return this.isInitialized;
   }

   public String getInclude() {
      return include;
   }

   public void setInclude(String inc) {
      include = inc;
   }

   public synchronized void load(InputStream input) throws IOException {
      PropertiesReader reader = new PropertiesReader(new InputStreamReader(input));

      try {
         while(true) {
            while(true) {
               String key;
               String value;
               do {
                  String line;
                  int equalSign;
                  do {
                     line = reader.readProperty();
                     equalSign = line.indexOf(61);
                  } while(equalSign <= 0);

                  key = line.substring(0, equalSign).trim();
                  value = line.substring(equalSign + 1).trim();
               } while("".equals(value));

               if (this.getInclude() != null && key.equalsIgnoreCase(this.getInclude())) {
                  File file = null;
                  if (value.startsWith(this.fileSeparator)) {
                     file = new File(value);
                  } else {
                     if (value.startsWith("." + this.fileSeparator)) {
                        value = value.substring(2);
                     }

                     file = new File(this.basePath + value);
                  }

                  if (file != null && file.exists() && file.canRead()) {
                     this.load(new FileInputStream(file));
                  }
               } else {
                  this.addProperty(key, value);
               }
            }
         }
      } catch (NullPointerException var8) {
      }
   }

   public Object getProperty(String key) {
      Object o = this.get(key);
      if (o == null && this.defaults != null) {
         o = this.defaults.get(key);
      }

      return o;
   }

   public void addProperty(String key, Object token) {
      this.deprecationCrutch.addProperty(key, token);
      Object o = this.get(key);
      if (o instanceof String) {
         Vector v = new Vector(2);
         v.addElement(o);
         v.addElement(token);
         this.put(key, v);
      } else if (o instanceof Vector) {
         ((Vector)o).addElement(token);
      } else if (token instanceof String && ((String)token).indexOf(",") > 0) {
         PropertiesTokenizer tokenizer = new PropertiesTokenizer((String)token);

         while(tokenizer.hasMoreTokens()) {
            String value = tokenizer.nextToken();
            this.addStringProperty(key, value);
         }
      } else {
         if (!this.containsKey(key)) {
            this.keysAsListed.add(key);
         }

         this.put(key, token);
      }

   }

   private void addStringProperty(String key, String token) {
      Object o = this.get(key);
      if (o instanceof String) {
         Vector v = new Vector(2);
         v.addElement(o);
         v.addElement(token);
         this.put(key, v);
      } else if (o instanceof Vector) {
         ((Vector)o).addElement(token);
      } else {
         if (!this.containsKey(key)) {
            this.keysAsListed.add(key);
         }

         this.put(key, token);
      }

   }

   public void setProperty(String key, Object value) {
      this.clearProperty(key);
      this.addProperty(key, value);
   }

   public synchronized void save(OutputStream output, String Header) throws IOException {
      if (output != null) {
         PrintWriter theWrtr = new PrintWriter(output);
         if (Header != null) {
            theWrtr.println(Header);
         }

         Enumeration theKeys = this.keys();

         while(theKeys.hasMoreElements()) {
            String key = (String)theKeys.nextElement();
            Object value = this.get(key);
            if (value != null) {
               if (value instanceof String) {
                  StringBuffer currentOutput = new StringBuffer();
                  currentOutput.append(key);
                  currentOutput.append("=");
                  currentOutput.append((String)value);
                  theWrtr.println(currentOutput.toString());
               } else if (value instanceof Vector) {
                  Vector values = (Vector)value;
                  Enumeration valuesEnum = values.elements();

                  while(valuesEnum.hasMoreElements()) {
                     String currentElement = (String)valuesEnum.nextElement();
                     StringBuffer currentOutput = new StringBuffer();
                     currentOutput.append(key);
                     currentOutput.append("=");
                     currentOutput.append(currentElement);
                     theWrtr.println(currentOutput.toString());
                  }
               }
            }

            theWrtr.println();
            theWrtr.flush();
         }
      }

   }

   public void combine(Configuration c) {
      Iterator i = c.getKeys();

      while(i.hasNext()) {
         String key = (String)i.next();
         this.setProperty(key, c.get(key));
      }

   }

   public void clearProperty(String key) {
      this.deprecationCrutch.clearProperty(key);
      if (this.containsKey(key)) {
         for(int i = 0; i < this.keysAsListed.size(); ++i) {
            if (((String)this.keysAsListed.get(i)).equals(key)) {
               this.keysAsListed.remove(i);
               break;
            }
         }

         this.remove(key);
      }

   }

   public Iterator getKeys() {
      return this.keysAsListed.iterator();
   }

   public Iterator getKeys(String prefix) {
      Iterator keys = this.getKeys();
      ArrayList matchingKeys = new ArrayList();

      while(keys.hasNext()) {
         Object key = keys.next();
         if (key instanceof String && ((String)key).startsWith(prefix)) {
            matchingKeys.add(key);
         }
      }

      return matchingKeys.iterator();
   }

   public Configuration subset(String prefix) {
      Configuration c = new Configuration();
      Iterator keys = this.getKeys();
      boolean validSubset = false;

      while(keys.hasNext()) {
         Object key = keys.next();
         if (key instanceof String && ((String)key).startsWith(prefix)) {
            if (!validSubset) {
               validSubset = true;
            }

            String newKey = null;
            if (((String)key).length() == prefix.length()) {
               newKey = prefix;
            } else {
               newKey = ((String)key).substring(prefix.length() + 1);
            }

            c.setProperty(newKey, this.get(key));
         }
      }

      if (validSubset) {
         return c;
      } else {
         return null;
      }
   }

   public void display() {
      Iterator i = this.getKeys();

      while(i.hasNext()) {
         String key = (String)i.next();
         Object value = this.get(key);
         System.out.println(key + " => " + value);
      }

   }

   public String getString(String key) {
      return this.getString(key, (String)null);
   }

   public String getString(String key, String defaultValue) {
      Object value = this.get(key);
      if (value instanceof String) {
         return (String)value;
      } else if (value == null) {
         return this.defaults != null ? this.defaults.getString(key, defaultValue) : defaultValue;
      } else if (value instanceof Vector) {
         return (String)((Vector)value).get(0);
      } else {
         throw new ClassCastException('\'' + key + "' doesn't map to a String object");
      }
   }

   public Properties getProperties(String key) {
      return this.getProperties(key, new Properties());
   }

   public Properties getProperties(String key, Properties defaults) {
      String[] tokens = this.getStringArray(key);
      Properties props = new Properties(defaults);

      for(int i = 0; i < tokens.length; ++i) {
         String token = tokens[i];
         int equalSign = token.indexOf(61);
         if (equalSign <= 0) {
            throw new IllegalArgumentException('\'' + token + "' does not contain " + "an equals sign");
         }

         String pkey = token.substring(0, equalSign).trim();
         String pvalue = token.substring(equalSign + 1).trim();
         props.put(pkey, pvalue);
      }

      return props;
   }

   public String[] getStringArray(String key) {
      Object value = this.get(key);
      Vector vector;
      if (value instanceof String) {
         vector = new Vector(1);
         vector.addElement(value);
      } else {
         if (!(value instanceof Vector)) {
            if (value == null) {
               if (this.defaults != null) {
                  return this.defaults.getStringArray(key);
               }

               return new String[0];
            }

            throw new ClassCastException('\'' + key + "' doesn't map to a String/Vector object");
         }

         vector = (Vector)value;
      }

      String[] tokens = new String[vector.size()];

      for(int i = 0; i < tokens.length; ++i) {
         tokens[i] = (String)vector.elementAt(i);
      }

      return tokens;
   }

   public Vector getVector(String key) {
      return this.getVector(key, (Vector)null);
   }

   public Vector getVector(String key, Vector defaultValue) {
      Object value = this.get(key);
      if (value instanceof Vector) {
         return (Vector)value;
      } else if (value instanceof String) {
         Vector v = new Vector(1);
         v.addElement((String)value);
         this.put(key, v);
         return v;
      } else if (value == null) {
         if (this.defaults != null) {
            return this.defaults.getVector(key, defaultValue);
         } else {
            return defaultValue == null ? new Vector() : defaultValue;
         }
      } else {
         throw new ClassCastException('\'' + key + "' doesn't map to a Vector object");
      }
   }

   public boolean getBoolean(String key) {
      Boolean b = this.getBoolean(key, (Boolean)null);
      if (b != null) {
         return b;
      } else {
         throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
      }
   }

   public boolean getBoolean(String key, boolean defaultValue) {
      return this.getBoolean(key, new Boolean(defaultValue));
   }

   public Boolean getBoolean(String key, Boolean defaultValue) {
      Object value = this.get(key);
      if (value instanceof Boolean) {
         return (Boolean)value;
      } else if (value instanceof String) {
         String s = this.testBoolean((String)value);
         Boolean b = new Boolean(s);
         this.put(key, b);
         return b;
      } else if (value == null) {
         return this.defaults != null ? this.defaults.getBoolean(key, defaultValue) : defaultValue;
      } else {
         throw new ClassCastException('\'' + key + "' doesn't map to a Boolean object");
      }
   }

   public String testBoolean(String value) {
      String s = value.toLowerCase();
      if (!s.equals("true") && !s.equals("on") && !s.equals("yes")) {
         return !s.equals("false") && !s.equals("off") && !s.equals("no") ? null : "false";
      } else {
         return "true";
      }
   }

   public byte getByte(String key) {
      Byte b = this.getByte(key, (Byte)null);
      if (b != null) {
         return b;
      } else {
         throw new NoSuchElementException('\'' + key + " doesn't map to an existing object");
      }
   }

   public byte getByte(String key, byte defaultValue) {
      return this.getByte(key, new Byte(defaultValue));
   }

   public Byte getByte(String key, Byte defaultValue) {
      Object value = this.get(key);
      if (value instanceof Byte) {
         return (Byte)value;
      } else if (value instanceof String) {
         Byte b = new Byte((String)value);
         this.put(key, b);
         return b;
      } else if (value == null) {
         return this.defaults != null ? this.defaults.getByte(key, defaultValue) : defaultValue;
      } else {
         throw new ClassCastException('\'' + key + "' doesn't map to a Byte object");
      }
   }

   public short getShort(String key) {
      Short s = this.getShort(key, (Short)null);
      if (s != null) {
         return s;
      } else {
         throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
      }
   }

   public short getShort(String key, short defaultValue) {
      return this.getShort(key, new Short(defaultValue));
   }

   public Short getShort(String key, Short defaultValue) {
      Object value = this.get(key);
      if (value instanceof Short) {
         return (Short)value;
      } else if (value instanceof String) {
         Short s = new Short((String)value);
         this.put(key, s);
         return s;
      } else if (value == null) {
         return this.defaults != null ? this.defaults.getShort(key, defaultValue) : defaultValue;
      } else {
         throw new ClassCastException('\'' + key + "' doesn't map to a Short object");
      }
   }

   public int getInt(String name) {
      return this.getInteger(name);
   }

   public int getInt(String name, int def) {
      return this.getInteger(name, def);
   }

   public int getInteger(String key) {
      Integer i = this.getInteger(key, (Integer)null);
      if (i != null) {
         return i;
      } else {
         throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
      }
   }

   public int getInteger(String key, int defaultValue) {
      Integer i = this.getInteger(key, (Integer)null);
      return i == null ? defaultValue : i;
   }

   public Integer getInteger(String key, Integer defaultValue) {
      Object value = this.get(key);
      if (value instanceof Integer) {
         return (Integer)value;
      } else if (value instanceof String) {
         Integer i = new Integer((String)value);
         this.put(key, i);
         return i;
      } else if (value == null) {
         return this.defaults != null ? this.defaults.getInteger(key, defaultValue) : defaultValue;
      } else {
         throw new ClassCastException('\'' + key + "' doesn't map to a Integer object");
      }
   }

   public long getLong(String key) {
      Long l = this.getLong(key, (Long)null);
      if (l != null) {
         return l;
      } else {
         throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
      }
   }

   public long getLong(String key, long defaultValue) {
      return this.getLong(key, new Long(defaultValue));
   }

   public Long getLong(String key, Long defaultValue) {
      Object value = this.get(key);
      if (value instanceof Long) {
         return (Long)value;
      } else if (value instanceof String) {
         Long l = new Long((String)value);
         this.put(key, l);
         return l;
      } else if (value == null) {
         return this.defaults != null ? this.defaults.getLong(key, defaultValue) : defaultValue;
      } else {
         throw new ClassCastException('\'' + key + "' doesn't map to a Long object");
      }
   }

   public float getFloat(String key) {
      Float f = this.getFloat(key, (Float)null);
      if (f != null) {
         return f;
      } else {
         throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
      }
   }

   public float getFloat(String key, float defaultValue) {
      return this.getFloat(key, new Float(defaultValue));
   }

   public Float getFloat(String key, Float defaultValue) {
      Object value = this.get(key);
      if (value instanceof Float) {
         return (Float)value;
      } else if (value instanceof String) {
         Float f = new Float((String)value);
         this.put(key, f);
         return f;
      } else if (value == null) {
         return this.defaults != null ? this.defaults.getFloat(key, defaultValue) : defaultValue;
      } else {
         throw new ClassCastException('\'' + key + "' doesn't map to a Float object");
      }
   }

   public double getDouble(String key) {
      Double d = this.getDouble(key, (Double)null);
      if (d != null) {
         return d;
      } else {
         throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
      }
   }

   public double getDouble(String key, double defaultValue) {
      return this.getDouble(key, new Double(defaultValue));
   }

   public Double getDouble(String key, Double defaultValue) {
      Object value = this.get(key);
      if (value instanceof Double) {
         return (Double)value;
      } else if (value instanceof String) {
         Double d = new Double((String)value);
         this.put(key, d);
         return d;
      } else if (value == null) {
         return this.defaults != null ? this.defaults.getDouble(key, defaultValue) : defaultValue;
      } else {
         throw new ClassCastException('\'' + key + "' doesn't map to a Double object");
      }
   }

   public static Configuration convertProperties(Properties p) {
      Configuration c = new Configuration();
      Enumeration e = p.keys();

      while(e.hasMoreElements()) {
         String s = (String)e.nextElement();
         c.setProperty(s, p.getProperty(s));
      }

      return c;
   }

   /** @deprecated */
   public ExtendedProperties getExtendedProperties() {
      return this.deprecationCrutch;
   }

   class PropertiesTokenizer extends StringTokenizer {
      static final String DELIMITER = ",";

      public PropertiesTokenizer(String string) {
         super(string, ",");
      }

      public boolean hasMoreTokens() {
         return super.hasMoreTokens();
      }

      public String nextToken() {
         StringBuffer buffer = new StringBuffer();

         while(this.hasMoreTokens()) {
            String token = super.nextToken();
            if (!token.endsWith("\\")) {
               buffer.append(token);
               break;
            }

            buffer.append(token.substring(0, token.length() - 1));
            buffer.append(",");
         }

         return buffer.toString().trim();
      }
   }

   class PropertiesReader extends LineNumberReader {
      public PropertiesReader(Reader reader) {
         super(reader);
      }

      public String readProperty() throws IOException {
         StringBuffer buffer = new StringBuffer();

         try {
            while(true) {
               String line;
               do {
                  do {
                     line = this.readLine().trim();
                  } while(line.length() == 0);
               } while(line.charAt(0) == '#');

               if (!line.endsWith("\\")) {
                  buffer.append(line);
                  return buffer.toString();
               }

               line = line.substring(0, line.length() - 1);
               buffer.append(line);
            }
         } catch (NullPointerException var3) {
            return null;
         }
      }
   }
}
