package org.apache.openjpa.lib.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;

public class ParameterTemplate {
   private static final String SEP = J2DoPrivHelper.getLineSeparator();
   private final StringBuffer _buf = new StringBuffer();
   private final Map _params = new HashMap();

   public ParameterTemplate append(String value) {
      this._buf.append(value);
      return this;
   }

   public ParameterTemplate append(boolean value) {
      this._buf.append(value);
      return this;
   }

   public ParameterTemplate append(char value) {
      this._buf.append(value);
      return this;
   }

   public ParameterTemplate append(double value) {
      this._buf.append(value);
      return this;
   }

   public ParameterTemplate append(float value) {
      this._buf.append(value);
      return this;
   }

   public ParameterTemplate append(int value) {
      this._buf.append(value);
      return this;
   }

   public ParameterTemplate append(long value) {
      this._buf.append(value);
      return this;
   }

   public ParameterTemplate append(short value) {
      this._buf.append(value);
      return this;
   }

   public ParameterTemplate append(Object value) {
      this._buf.append(value);
      return this;
   }

   public ParameterTemplate append(InputStream in) throws IOException {
      return this.append((Reader)(new InputStreamReader(in)));
   }

   public ParameterTemplate append(Reader reader) throws IOException {
      BufferedReader buf = new BufferedReader(reader);

      String line;
      while((line = buf.readLine()) != null) {
         this._buf.append(line).append(SEP);
      }

      return this;
   }

   public ParameterTemplate append(File file) throws IOException {
      FileReader reader = new FileReader(file);

      ParameterTemplate var3;
      try {
         var3 = this.append((Reader)reader);
      } finally {
         try {
            reader.close();
         } catch (IOException var10) {
         }

      }

      return var3;
   }

   public boolean hasParameter(String name) {
      return this._params.containsKey(name);
   }

   public Object getParameter(String name) {
      return this._params.get(name);
   }

   public Object setParameter(String name, Object val) {
      return this._params.put(name, val);
   }

   public void setParameters(Map params) {
      this._params.putAll(params);
   }

   public void clearParameters() {
      this._params.clear();
   }

   public String toString() {
      if (this._buf.length() != 0 && !this._params.isEmpty()) {
         StringBuffer copy = new StringBuffer();
         StringBuffer param = null;
         char last = 0;

         for(int i = 0; i < this._buf.length(); ++i) {
            char ch = this._buf.charAt(i);
            if (last == '$' && ch == '{') {
               copy.deleteCharAt(copy.length() - 1);
               param = new StringBuffer();
            } else if (ch == '}' && param != null) {
               if (this._params.containsKey(param.toString())) {
                  copy.append(this._params.get(param.toString()));
               } else {
                  copy.append((String)AccessController.doPrivileged(J2DoPrivHelper.getPropertyAction(param.toString())));
               }

               param = null;
            } else if (param != null) {
               param.append(ch);
            } else {
               copy.append(ch);
            }

            last = ch;
         }

         return copy.toString();
      } else {
         return this._buf.toString();
      }
   }

   public void write(OutputStream out) throws IOException {
      this.write((Writer)(new OutputStreamWriter(out)));
   }

   public void write(Writer writer) throws IOException {
      writer.write(this.toString());
      writer.flush();
   }

   public void write(File file) throws IOException {
      FileWriter writer = new FileWriter(file);

      try {
         this.write((Writer)writer);
      } finally {
         try {
            writer.close();
         } catch (IOException var9) {
         }

      }

   }
}
