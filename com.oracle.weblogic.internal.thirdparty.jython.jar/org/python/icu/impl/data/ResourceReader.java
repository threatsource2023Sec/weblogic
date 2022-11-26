package org.python.icu.impl.data;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.python.icu.impl.ICUData;
import org.python.icu.impl.PatternProps;

public class ResourceReader implements Closeable {
   private BufferedReader reader;
   private String resourceName;
   private String encoding;
   private Class root;
   private int lineNo;

   public ResourceReader(String resourceName, String encoding) throws UnsupportedEncodingException {
      this(ICUData.class, "data/" + resourceName, encoding);
   }

   public ResourceReader(String resourceName) {
      this(ICUData.class, "data/" + resourceName);
   }

   public ResourceReader(Class rootClass, String resourceName, String encoding) throws UnsupportedEncodingException {
      this.reader = null;
      this.root = rootClass;
      this.resourceName = resourceName;
      this.encoding = encoding;
      this.lineNo = -1;
      this._reset();
   }

   public ResourceReader(InputStream is, String resourceName, String encoding) {
      this.reader = null;
      this.root = null;
      this.resourceName = resourceName;
      this.encoding = encoding;
      this.lineNo = -1;

      try {
         InputStreamReader isr = encoding == null ? new InputStreamReader(is) : new InputStreamReader(is, encoding);
         this.reader = new BufferedReader(isr);
         this.lineNo = 0;
      } catch (UnsupportedEncodingException var5) {
      }

   }

   public ResourceReader(InputStream is, String resourceName) {
      this((InputStream)is, resourceName, (String)null);
   }

   public ResourceReader(Class rootClass, String resourceName) {
      this.reader = null;
      this.root = rootClass;
      this.resourceName = resourceName;
      this.encoding = null;
      this.lineNo = -1;

      try {
         this._reset();
      } catch (UnsupportedEncodingException var4) {
      }

   }

   public String readLine() throws IOException {
      if (this.lineNo != 0) {
         ++this.lineNo;
         return this.reader.readLine();
      } else {
         ++this.lineNo;
         String line = this.reader.readLine();
         if (line != null && (line.charAt(0) == '\uffef' || line.charAt(0) == '\ufeff')) {
            line = line.substring(1);
         }

         return line;
      }
   }

   public String readLineSkippingComments(boolean trim) throws IOException {
      String line;
      int pos;
      do {
         line = this.readLine();
         if (line == null) {
            return line;
         }

         pos = PatternProps.skipWhiteSpace(line, 0);
      } while(pos == line.length() || line.charAt(pos) == '#');

      if (trim) {
         line = line.substring(pos);
      }

      return line;
   }

   public String readLineSkippingComments() throws IOException {
      return this.readLineSkippingComments(false);
   }

   public int getLineNumber() {
      return this.lineNo;
   }

   public String describePosition() {
      return this.resourceName + ':' + this.lineNo;
   }

   public void reset() {
      try {
         this._reset();
      } catch (UnsupportedEncodingException var2) {
      }

   }

   private void _reset() throws UnsupportedEncodingException {
      try {
         this.close();
      } catch (IOException var3) {
      }

      if (this.lineNo != 0) {
         InputStream is = ICUData.getStream(this.root, this.resourceName);
         if (is == null) {
            throw new IllegalArgumentException("Can't open " + this.resourceName);
         } else {
            InputStreamReader isr = this.encoding == null ? new InputStreamReader(is) : new InputStreamReader(is, this.encoding);
            this.reader = new BufferedReader(isr);
            this.lineNo = 0;
         }
      }
   }

   public void close() throws IOException {
      if (this.reader != null) {
         this.reader.close();
         this.reader = null;
      }

   }
}
