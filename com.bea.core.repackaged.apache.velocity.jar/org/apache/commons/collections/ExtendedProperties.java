package org.apache.commons.collections;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public class ExtendedProperties extends Hashtable {
   private ExtendedProperties defaults;
   protected String file;
   protected String basePath;
   protected String fileSeparator;
   protected boolean isInitialized;
   protected static String include = "include";
   protected ArrayList keysAsListed;

   public ExtendedProperties() {
      this.fileSeparator = System.getProperty("file.separator");
      this.isInitialized = false;
      this.keysAsListed = new ArrayList();
   }

   public ExtendedProperties(String var1) throws IOException {
      this(var1, (String)null);
   }

   public ExtendedProperties(String var1, String var2) throws IOException {
      this.fileSeparator = System.getProperty("file.separator");
      this.isInitialized = false;
      this.keysAsListed = new ArrayList();
      this.file = var1;
      this.basePath = (new File(var1)).getAbsolutePath();
      this.basePath = this.basePath.substring(0, this.basePath.lastIndexOf(this.fileSeparator) + 1);
      this.load(new FileInputStream(var1));
      if (var2 != null) {
         this.defaults = new ExtendedProperties(var2);
      }

   }

   private void init(ExtendedProperties var1) throws IOException {
      this.isInitialized = true;
   }

   public boolean isInitialized() {
      return this.isInitialized;
   }

   public String getInclude() {
      return include;
   }

   public void setInclude(String var1) {
      include = var1;
   }

   public void load(InputStream var1) throws IOException {
      this.load(var1, (String)null);
   }

   public synchronized void load(InputStream var1, String var2) throws IOException {
      PropertiesReader var3 = null;
      if (var2 != null) {
         try {
            var3 = new PropertiesReader(new InputStreamReader(var1, var2));
         } catch (UnsupportedEncodingException var9) {
         }
      }

      if (var3 == null) {
         var3 = new PropertiesReader(new InputStreamReader(var1));
      }

      try {
         while(true) {
            while(true) {
               String var6;
               String var7;
               do {
                  String var4;
                  int var5;
                  do {
                     var4 = var3.readProperty();
                     var5 = var4.indexOf(61);
                  } while(var5 <= 0);

                  var6 = var4.substring(0, var5).trim();
                  var7 = var4.substring(var5 + 1).trim();
               } while("".equals(var7));

               if (this.getInclude() != null && var6.equalsIgnoreCase(this.getInclude())) {
                  File var8 = null;
                  if (var7.startsWith(this.fileSeparator)) {
                     var8 = new File(var7);
                  } else {
                     if (var7.startsWith("." + this.fileSeparator)) {
                        var7 = var7.substring(2);
                     }

                     var8 = new File(this.basePath + var7);
                  }

                  if (var8 != null && var8.exists() && var8.canRead()) {
                     this.load(new FileInputStream(var8));
                  }
               } else {
                  this.addProperty(var6, var7);
               }
            }
         }
      } catch (NullPointerException var10) {
      }
   }

   public Object getProperty(String var1) {
      Object var2 = this.get(var1);
      if (var2 == null && this.defaults != null) {
         var2 = this.defaults.get(var1);
      }

      return var2;
   }

   public void addProperty(String var1, Object var2) {
      Object var3 = this.get(var1);
      if (var3 instanceof String) {
         Vector var4 = new Vector(2);
         var4.addElement(var3);
         var4.addElement(var2);
         this.put(var1, var4);
      } else if (var3 instanceof Vector) {
         ((Vector)var3).addElement(var2);
      } else if (var2 instanceof String && ((String)var2).indexOf(",") > 0) {
         PropertiesTokenizer var6 = new PropertiesTokenizer((String)var2);

         while(var6.hasMoreTokens()) {
            String var5 = var6.nextToken();
            this.addStringProperty(var1, var5);
         }
      } else {
         this.addPropertyDirect(var1, var2);
      }

   }

   private void addPropertyDirect(String var1, Object var2) {
      if (!this.containsKey(var1)) {
         this.keysAsListed.add(var1);
      }

      this.put(var1, var2);
   }

   private void addStringProperty(String var1, String var2) {
      Object var3 = this.get(var1);
      if (var3 instanceof String) {
         Vector var4 = new Vector(2);
         var4.addElement(var3);
         var4.addElement(var2);
         this.put(var1, var4);
      } else if (var3 instanceof Vector) {
         ((Vector)var3).addElement(var2);
      } else {
         this.addPropertyDirect(var1, var2);
      }

   }

   public void setProperty(String var1, Object var2) {
      this.clearProperty(var1);
      this.addProperty(var1, var2);
   }

   public synchronized void save(OutputStream var1, String var2) throws IOException {
      if (var1 != null) {
         PrintWriter var3 = new PrintWriter(var1);
         if (var2 != null) {
            var3.println(var2);
         }

         Enumeration var4 = this.keys();

         while(var4.hasMoreElements()) {
            String var5 = (String)var4.nextElement();
            Object var6 = this.get(var5);
            if (var6 != null) {
               if (var6 instanceof String) {
                  StringBuffer var11 = new StringBuffer();
                  var11.append(var5);
                  var11.append("=");
                  var11.append((String)var6);
                  var3.println(var11.toString());
               } else if (var6 instanceof Vector) {
                  Vector var7 = (Vector)var6;
                  Enumeration var8 = var7.elements();

                  while(var8.hasMoreElements()) {
                     String var9 = (String)var8.nextElement();
                     StringBuffer var10 = new StringBuffer();
                     var10.append(var5);
                     var10.append("=");
                     var10.append(var9);
                     var3.println(var10.toString());
                  }
               }
            }

            var3.println();
            var3.flush();
         }
      }

   }

   public void combine(ExtendedProperties var1) {
      Iterator var2 = var1.getKeys();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         this.setProperty(var3, var1.get(var3));
      }

   }

   public void clearProperty(String var1) {
      if (this.containsKey(var1)) {
         for(int var2 = 0; var2 < this.keysAsListed.size(); ++var2) {
            if (((String)this.keysAsListed.get(var2)).equals(var1)) {
               this.keysAsListed.remove(var2);
               break;
            }
         }

         this.remove(var1);
      }

   }

   public Iterator getKeys() {
      return this.keysAsListed.iterator();
   }

   public Iterator getKeys(String var1) {
      Iterator var2 = this.getKeys();
      ArrayList var3 = new ArrayList();

      while(var2.hasNext()) {
         Object var4 = var2.next();
         if (var4 instanceof String && ((String)var4).startsWith(var1)) {
            var3.add(var4);
         }
      }

      return var3.iterator();
   }

   public ExtendedProperties subset(String var1) {
      ExtendedProperties var2 = new ExtendedProperties();
      Iterator var3 = this.getKeys();
      boolean var4 = false;

      while(var3.hasNext()) {
         Object var5 = var3.next();
         if (var5 instanceof String && ((String)var5).startsWith(var1)) {
            if (!var4) {
               var4 = true;
            }

            String var6 = null;
            if (((String)var5).length() == var1.length()) {
               var6 = var1;
            } else {
               var6 = ((String)var5).substring(var1.length() + 1);
            }

            var2.addPropertyDirect(var6, this.get(var5));
         }
      }

      if (var4) {
         return var2;
      } else {
         return null;
      }
   }

   public void display() {
      Iterator var1 = this.getKeys();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         Object var3 = this.get(var2);
         System.out.println(var2 + " => " + var3);
      }

   }

   public String getString(String var1) {
      return this.getString(var1, (String)null);
   }

   public String getString(String var1, String var2) {
      Object var3 = this.get(var1);
      if (var3 instanceof String) {
         return (String)var3;
      } else if (var3 == null) {
         return this.defaults != null ? this.defaults.getString(var1, var2) : var2;
      } else if (var3 instanceof Vector) {
         return (String)((Vector)var3).get(0);
      } else {
         throw new ClassCastException('\'' + var1 + "' doesn't map to a String object");
      }
   }

   public Properties getProperties(String var1) {
      return this.getProperties(var1, new Properties());
   }

   public Properties getProperties(String var1, Properties var2) {
      String[] var3 = this.getStringArray(var1);
      Properties var4 = new Properties(var2);

      for(int var5 = 0; var5 < var3.length; ++var5) {
         String var6 = var3[var5];
         int var7 = var6.indexOf(61);
         if (var7 <= 0) {
            throw new IllegalArgumentException('\'' + var6 + "' does not contain " + "an equals sign");
         }

         String var8 = var6.substring(0, var7).trim();
         String var9 = var6.substring(var7 + 1).trim();
         var4.put(var8, var9);
      }

      return var4;
   }

   public String[] getStringArray(String var1) {
      Object var2 = this.get(var1);
      Vector var3;
      if (var2 instanceof String) {
         var3 = new Vector(1);
         var3.addElement(var2);
      } else {
         if (!(var2 instanceof Vector)) {
            if (var2 == null) {
               if (this.defaults != null) {
                  return this.defaults.getStringArray(var1);
               }

               return new String[0];
            }

            throw new ClassCastException('\'' + var1 + "' doesn't map to a String/Vector object");
         }

         var3 = (Vector)var2;
      }

      String[] var4 = new String[var3.size()];

      for(int var5 = 0; var5 < var4.length; ++var5) {
         var4[var5] = (String)var3.elementAt(var5);
      }

      return var4;
   }

   public Vector getVector(String var1) {
      return this.getVector(var1, (Vector)null);
   }

   public Vector getVector(String var1, Vector var2) {
      Object var3 = this.get(var1);
      if (var3 instanceof Vector) {
         return (Vector)var3;
      } else if (var3 instanceof String) {
         Vector var4 = new Vector(1);
         var4.addElement((String)var3);
         this.put(var1, var4);
         return var4;
      } else if (var3 == null) {
         if (this.defaults != null) {
            return this.defaults.getVector(var1, var2);
         } else {
            return var2 == null ? new Vector() : var2;
         }
      } else {
         throw new ClassCastException('\'' + var1 + "' doesn't map to a Vector object");
      }
   }

   public boolean getBoolean(String var1) {
      Boolean var2 = this.getBoolean(var1, (Boolean)null);
      if (var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException('\'' + var1 + "' doesn't map to an existing object");
      }
   }

   public boolean getBoolean(String var1, boolean var2) {
      return this.getBoolean(var1, new Boolean(var2));
   }

   public Boolean getBoolean(String var1, Boolean var2) {
      Object var3 = this.get(var1);
      if (var3 instanceof Boolean) {
         return (Boolean)var3;
      } else if (var3 instanceof String) {
         String var4 = this.testBoolean((String)var3);
         Boolean var5 = new Boolean(var4);
         this.put(var1, var5);
         return var5;
      } else if (var3 == null) {
         return this.defaults != null ? this.defaults.getBoolean(var1, var2) : var2;
      } else {
         throw new ClassCastException('\'' + var1 + "' doesn't map to a Boolean object");
      }
   }

   public String testBoolean(String var1) {
      String var2 = var1.toLowerCase();
      if (!var2.equals("true") && !var2.equals("on") && !var2.equals("yes")) {
         return !var2.equals("false") && !var2.equals("off") && !var2.equals("no") ? null : "false";
      } else {
         return "true";
      }
   }

   public byte getByte(String var1) {
      Byte var2 = this.getByte(var1, (Byte)null);
      if (var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException('\'' + var1 + " doesn't map to an existing object");
      }
   }

   public byte getByte(String var1, byte var2) {
      return this.getByte(var1, new Byte(var2));
   }

   public Byte getByte(String var1, Byte var2) {
      Object var3 = this.get(var1);
      if (var3 instanceof Byte) {
         return (Byte)var3;
      } else if (var3 instanceof String) {
         Byte var4 = new Byte((String)var3);
         this.put(var1, var4);
         return var4;
      } else if (var3 == null) {
         return this.defaults != null ? this.defaults.getByte(var1, var2) : var2;
      } else {
         throw new ClassCastException('\'' + var1 + "' doesn't map to a Byte object");
      }
   }

   public short getShort(String var1) {
      Short var2 = this.getShort(var1, (Short)null);
      if (var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException('\'' + var1 + "' doesn't map to an existing object");
      }
   }

   public short getShort(String var1, short var2) {
      return this.getShort(var1, new Short(var2));
   }

   public Short getShort(String var1, Short var2) {
      Object var3 = this.get(var1);
      if (var3 instanceof Short) {
         return (Short)var3;
      } else if (var3 instanceof String) {
         Short var4 = new Short((String)var3);
         this.put(var1, var4);
         return var4;
      } else if (var3 == null) {
         return this.defaults != null ? this.defaults.getShort(var1, var2) : var2;
      } else {
         throw new ClassCastException('\'' + var1 + "' doesn't map to a Short object");
      }
   }

   public int getInt(String var1) {
      return this.getInteger(var1);
   }

   public int getInt(String var1, int var2) {
      return this.getInteger(var1, var2);
   }

   public int getInteger(String var1) {
      Integer var2 = this.getInteger(var1, (Integer)null);
      if (var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException('\'' + var1 + "' doesn't map to an existing object");
      }
   }

   public int getInteger(String var1, int var2) {
      Integer var3 = this.getInteger(var1, (Integer)null);
      return var3 == null ? var2 : var3;
   }

   public Integer getInteger(String var1, Integer var2) {
      Object var3 = this.get(var1);
      if (var3 instanceof Integer) {
         return (Integer)var3;
      } else if (var3 instanceof String) {
         Integer var4 = new Integer((String)var3);
         this.put(var1, var4);
         return var4;
      } else if (var3 == null) {
         return this.defaults != null ? this.defaults.getInteger(var1, var2) : var2;
      } else {
         throw new ClassCastException('\'' + var1 + "' doesn't map to a Integer object");
      }
   }

   public long getLong(String var1) {
      Long var2 = this.getLong(var1, (Long)null);
      if (var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException('\'' + var1 + "' doesn't map to an existing object");
      }
   }

   public long getLong(String var1, long var2) {
      return this.getLong(var1, new Long(var2));
   }

   public Long getLong(String var1, Long var2) {
      Object var3 = this.get(var1);
      if (var3 instanceof Long) {
         return (Long)var3;
      } else if (var3 instanceof String) {
         Long var4 = new Long((String)var3);
         this.put(var1, var4);
         return var4;
      } else if (var3 == null) {
         return this.defaults != null ? this.defaults.getLong(var1, var2) : var2;
      } else {
         throw new ClassCastException('\'' + var1 + "' doesn't map to a Long object");
      }
   }

   public float getFloat(String var1) {
      Float var2 = this.getFloat(var1, (Float)null);
      if (var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException('\'' + var1 + "' doesn't map to an existing object");
      }
   }

   public float getFloat(String var1, float var2) {
      return this.getFloat(var1, new Float(var2));
   }

   public Float getFloat(String var1, Float var2) {
      Object var3 = this.get(var1);
      if (var3 instanceof Float) {
         return (Float)var3;
      } else if (var3 instanceof String) {
         Float var4 = new Float((String)var3);
         this.put(var1, var4);
         return var4;
      } else if (var3 == null) {
         return this.defaults != null ? this.defaults.getFloat(var1, var2) : var2;
      } else {
         throw new ClassCastException('\'' + var1 + "' doesn't map to a Float object");
      }
   }

   public double getDouble(String var1) {
      Double var2 = this.getDouble(var1, (Double)null);
      if (var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException('\'' + var1 + "' doesn't map to an existing object");
      }
   }

   public double getDouble(String var1, double var2) {
      return this.getDouble(var1, new Double(var2));
   }

   public Double getDouble(String var1, Double var2) {
      Object var3 = this.get(var1);
      if (var3 instanceof Double) {
         return (Double)var3;
      } else if (var3 instanceof String) {
         Double var4 = new Double((String)var3);
         this.put(var1, var4);
         return var4;
      } else if (var3 == null) {
         return this.defaults != null ? this.defaults.getDouble(var1, var2) : var2;
      } else {
         throw new ClassCastException('\'' + var1 + "' doesn't map to a Double object");
      }
   }

   public static ExtendedProperties convertProperties(Properties var0) {
      ExtendedProperties var1 = new ExtendedProperties();
      Enumeration var2 = var0.keys();

      while(var2.hasMoreElements()) {
         String var3 = (String)var2.nextElement();
         var1.setProperty(var3, var0.getProperty(var3));
      }

      return var1;
   }

   class PropertiesTokenizer extends StringTokenizer {
      static final String DELIMITER = ",";

      public PropertiesTokenizer(String var2) {
         super(var2, ",");
      }

      public boolean hasMoreTokens() {
         return super.hasMoreTokens();
      }

      public String nextToken() {
         StringBuffer var1 = new StringBuffer();

         while(this.hasMoreTokens()) {
            String var2 = super.nextToken();
            if (!var2.endsWith("\\")) {
               var1.append(var2);
               break;
            }

            var1.append(var2.substring(0, var2.length() - 1));
            var1.append(",");
         }

         return var1.toString().trim();
      }
   }

   class PropertiesReader extends LineNumberReader {
      public PropertiesReader(Reader var2) {
         super(var2);
      }

      public String readProperty() throws IOException {
         StringBuffer var1 = new StringBuffer();

         try {
            while(true) {
               String var2;
               do {
                  do {
                     var2 = this.readLine().trim();
                  } while(var2.length() == 0);
               } while(var2.charAt(0) == '#');

               if (!var2.endsWith("\\")) {
                  var1.append(var2);
                  return var1.toString();
               }

               var2 = var2.substring(0, var2.length() - 1);
               var1.append(var2);
            }
         } catch (NullPointerException var3) {
            return null;
         }
      }
   }
}
