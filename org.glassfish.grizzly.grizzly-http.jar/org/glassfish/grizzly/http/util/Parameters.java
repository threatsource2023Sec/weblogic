package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.localization.LogMessages;

public final class Parameters {
   private static final Logger LOGGER = Grizzly.logger(Parameters.class);
   private final LinkedHashMap paramHashValues = new LinkedHashMap();
   private boolean didQueryParameters = false;
   private boolean didMerge = false;
   MimeHeaders headers;
   DataChunk queryDC;
   final DataChunk decodedQuery = DataChunk.newInstance();
   public static final int INITIAL_SIZE = 4;
   private Parameters child = null;
   private Parameters parent = null;
   private Parameters currentChild = null;
   Charset encoding = null;
   Charset queryStringEncoding = null;
   private int limit = -1;
   private int parameterCount = 0;
   final BufferChunk tmpName = new BufferChunk();
   final BufferChunk tmpValue = new BufferChunk();
   private final BufferChunk origName = new BufferChunk();
   private final BufferChunk origValue = new BufferChunk();
   final CharChunk tmpNameC = new CharChunk(1024);
   final CharChunk tmpValueC = new CharChunk(1024);
   public static final String DEFAULT_ENCODING;
   public static final Charset DEFAULT_CHARSET;

   public void setQuery(DataChunk queryBC) {
      this.queryDC = queryBC;
   }

   public void setHeaders(MimeHeaders headers) {
      this.headers = headers;
   }

   public void setLimit(int limit) {
      this.limit = limit;
   }

   public void setEncoding(Charset encoding) {
      this.encoding = encoding;
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Set encoding to {0}", encoding);
      }

   }

   public Charset getEncoding() {
      return this.encoding;
   }

   public void setQueryStringEncoding(Charset queryStringEncoding) {
      this.queryStringEncoding = queryStringEncoding;
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Set query string encoding to {0}", queryStringEncoding);
      }

   }

   public Charset getQueryStringEncoding() {
      return this.queryStringEncoding;
   }

   public void recycle() {
      this.paramHashValues.clear();
      this.didQueryParameters = false;
      this.currentChild = null;
      this.didMerge = false;
      this.encoding = null;
      this.queryStringEncoding = null;
      this.parameterCount = 0;
      this.decodedQuery.recycle();
   }

   public Parameters getCurrentSet() {
      return this.currentChild == null ? this : this.currentChild;
   }

   public void push() {
      if (this.currentChild == null) {
         this.currentChild = new Parameters();
         this.currentChild.parent = this;
      } else {
         if (this.currentChild.child == null) {
            this.currentChild.child = new Parameters();
            this.currentChild.child.parent = this.currentChild;
         }

         this.currentChild = this.currentChild.child;
         this.currentChild.setEncoding(this.encoding);
      }
   }

   public void pop() {
      if (this.currentChild == null) {
         throw new RuntimeException("Attempt to pop without a push");
      } else {
         this.currentChild.recycle();
         this.currentChild = this.currentChild.parent;
      }
   }

   public void addParameterValues(String key, String[] newValues) {
      if (key != null) {
         ArrayList values;
         if (this.paramHashValues.containsKey(key)) {
            values = (ArrayList)this.paramHashValues.get(key);
         } else {
            values = new ArrayList(1);
            this.paramHashValues.put(key, values);
         }

         values.ensureCapacity(values.size() + newValues.length);
         Collections.addAll(values, newValues);
      }
   }

   public String[] getParameterValues(String name) {
      this.handleQueryParameters();
      ArrayList values;
      if (this.currentChild != null) {
         this.currentChild.merge();
         values = (ArrayList)this.currentChild.paramHashValues.get(name);
      } else {
         values = (ArrayList)this.paramHashValues.get(name);
      }

      return values != null ? (String[])values.toArray(new String[values.size()]) : null;
   }

   public Set getParameterNames() {
      this.handleQueryParameters();
      if (this.currentChild != null) {
         this.currentChild.merge();
         this.currentChild.paramHashValues.keySet();
      }

      return this.paramHashValues.keySet();
   }

   private void merge() {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Before merging {0} {1} {2}", new Object[]{this, this.parent, this.didMerge});
         LOGGER.log(Level.FINEST, this.paramsAsString());
      }

      this.handleQueryParameters();
      if (!this.didMerge) {
         if (this.parent != null) {
            this.parent.merge();
            LinkedHashMap parentProps = this.parent.paramHashValues;
            merge2(this.paramHashValues, parentProps);
            this.didMerge = true;
            if (LOGGER.isLoggable(Level.FINEST)) {
               LOGGER.log(Level.FINEST, "After {0}", this.paramsAsString());
            }

         }
      }
   }

   public String getParameter(String name) {
      ArrayList values = (ArrayList)this.paramHashValues.get(name);
      if (values != null) {
         return values.isEmpty() ? "" : (String)values.get(0);
      } else {
         return null;
      }
   }

   public void handleQueryParameters() {
      if (!this.didQueryParameters) {
         this.didQueryParameters = true;
         if (this.queryDC != null && !this.queryDC.isNull()) {
            if (LOGGER.isLoggable(Level.FINEST)) {
               LOGGER.log(Level.FINEST, "Decoding query {0} {1}", new Object[]{this.queryDC, this.queryStringEncoding});
            }

            this.decodedQuery.duplicate(this.queryDC);
            this.processParameters(this.decodedQuery, this.queryStringEncoding);
         }
      }
   }

   private static void merge2(LinkedHashMap one, LinkedHashMap two) {
      Iterator var2 = two.keySet().iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         ArrayList oneValue = (ArrayList)one.get(name);
         ArrayList twoValue = (ArrayList)two.get(name);
         if (twoValue != null) {
            ArrayList combinedValue;
            if (oneValue == null) {
               combinedValue = new ArrayList(twoValue);
            } else {
               combinedValue = new ArrayList(oneValue.size() + twoValue.size());
               combinedValue.addAll(oneValue);
               combinedValue.addAll(twoValue);
            }

            one.put(name, combinedValue);
         }
      }

   }

   public void addParameter(String key, String value) throws IllegalStateException {
      if (key != null) {
         ++this.parameterCount;
         if (this.limit > -1 && this.parameterCount > this.limit) {
            throw new IllegalStateException();
         } else {
            ArrayList values = (ArrayList)this.paramHashValues.get(key);
            if (values == null) {
               values = new ArrayList(1);
               this.paramHashValues.put(key, values);
            }

            values.add(value);
         }
      }
   }

   public void processParameters(Buffer buffer, int start, int len) {
      this.processParameters(buffer, start, len, this.encoding);
   }

   public void processParameters(Buffer buffer, int start, int len, Charset enc) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Process parameters. Buffer: {0} start={1} len={2} content={3}", new Object[]{buffer, start, len, buffer.toStringContent(enc, start, start + len)});
      }

      int decodeFailCount = 0;
      int end = start + len;
      int pos = start;

      while(pos < end) {
         if (this.limit > -1 && this.parameterCount >= this.limit) {
            LOGGER.warning(LogMessages.WARNING_GRIZZLY_HTTP_SEVERE_GRIZZLY_HTTP_PARAMETERS_MAX_COUNT_FAIL(this.limit));
            break;
         }

         int nameStart = pos;
         int nameEnd = -1;
         int valueStart = -1;
         int valueEnd = -1;
         boolean parsingName = true;
         boolean decodeName = false;
         boolean decodeValue = false;
         boolean parameterComplete = false;

         do {
            switch (buffer.get(pos)) {
               case 37:
               case 43:
                  if (parsingName) {
                     decodeName = true;
                  } else {
                     decodeValue = true;
                  }

                  ++pos;
                  break;
               case 38:
                  if (parsingName) {
                     nameEnd = pos;
                  } else {
                     valueEnd = pos;
                  }

                  parameterComplete = true;
                  ++pos;
                  break;
               case 61:
                  if (parsingName) {
                     nameEnd = pos;
                     parsingName = false;
                     ++pos;
                     valueStart = pos;
                  } else {
                     ++pos;
                  }
                  break;
               default:
                  ++pos;
            }
         } while(!parameterComplete && pos < end);

         if (pos == end) {
            if (nameEnd == -1) {
               nameEnd = pos;
            } else if (valueStart > -1 && valueEnd == -1) {
               valueEnd = pos;
            }
         }

         if (LOGGER.isLoggable(Level.FINEST) && valueStart == -1) {
            LOGGER.log(Level.FINEST, LogMessages.FINE_GRIZZLY_HTTP_PARAMETERS_NOEQUAL(nameStart, nameEnd, buffer.toStringContent(DEFAULT_CHARSET, nameStart, nameEnd)));
         }

         if (nameEnd <= nameStart) {
            if (LOGGER.isLoggable(Level.INFO) && valueEnd < nameStart) {
               LOGGER.info(LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_INVALID_CHUNK(nameStart, nameEnd, (Object)null));
            }
         } else {
            this.tmpName.setBufferChunk(buffer, nameStart, nameEnd);
            this.tmpValue.setBufferChunk(buffer, valueStart, valueEnd);
            if (LOGGER.isLoggable(Level.FINEST)) {
               this.origName.setBufferChunk(buffer, nameStart, nameEnd);
               this.origValue.setBufferChunk(buffer, valueStart, valueEnd);
            }

            try {
               String value;
               try {
                  String name;
                  if (decodeName) {
                     name = this.urlDecode(this.tmpName, enc);
                  } else {
                     name = this.tmpName.toString(enc);
                  }

                  if (valueStart != -1) {
                     if (decodeValue) {
                        value = this.urlDecode(this.tmpValue, enc);
                     } else {
                        value = this.tmpValue.toString(enc);
                     }
                  } else {
                     value = "";
                  }

                  this.addParameter(name, value);
               } catch (Exception var22) {
                  ++decodeFailCount;
                  if (LOGGER.isLoggable(Level.FINEST)) {
                     LOGGER.log(Level.FINEST, LogMessages.FINE_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_DEBUG(this.origName.toString(), this.origValue.toString()));
                  } else if (LOGGER.isLoggable(Level.INFO) && decodeFailCount == 1) {
                     value = this.tmpName.getLength() > 0 ? this.tmpName.toString() : "unavailable";
                     String value = this.tmpValue.getLength() > 0 ? this.tmpValue.toString() : "unavailable";
                     LOGGER.log(Level.INFO, LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_INFO(var22.getMessage(), value, value));
                     LOGGER.log(Level.FINE, "Decoding stacktrace.", var22);
                  }
               }
            } finally {
               this.tmpName.recycle();
               this.tmpValue.recycle();
            }
         }
      }

      if (!LOGGER.isLoggable(Level.FINEST) && decodeFailCount > 1) {
         LOGGER.info(LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_MULTIPLE_DECODING_FAIL(decodeFailCount));
      }

   }

   private String urlDecode(BufferChunk bc, Charset enc) throws IOException {
      URLDecoder.decode(bc, true);
      String result;
      if (enc != null) {
         if (bc.getStart() == -1 && bc.getEnd() == -1) {
            return "";
         }

         result = bc.toString(enc);
      } else {
         CharChunk cc = this.tmpNameC;
         int length = bc.getLength();
         cc.allocate(length, -1);
         Buffer bbuf = bc.getBuffer();
         char[] cbuf = cc.getBuffer();
         int start = bc.getStart();

         for(int i = 0; i < length; ++i) {
            cbuf[i] = (char)(bbuf.get(i + start) & 255);
         }

         cc.setChars(cbuf, 0, length);
         result = cc.toString();
         cc.recycle();
      }

      return result;
   }

   public void processParameters(char[] chars, int start, int len) {
      int end = start + len;
      int pos = start;
      int decodeFailCount = 0;
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Process parameters. chars: {0} start={1} len={2} content={3}", new Object[]{chars, start, len, new String(chars, start, len)});
      }

      do {
         if (this.limit > -1 && this.parameterCount >= this.limit) {
            LOGGER.warning(LogMessages.WARNING_GRIZZLY_HTTP_SEVERE_GRIZZLY_HTTP_PARAMETERS_MAX_COUNT_FAIL(this.limit));
            break;
         }

         boolean noEq = false;
         int nameStart = pos;
         int valStart = -1;
         int valEnd = -1;
         int nameEnd = CharChunk.indexOf(chars, pos, end, '=');
         int nameEnd2 = CharChunk.indexOf(chars, pos, end, '&');
         if (nameEnd2 != -1 && (nameEnd == -1 || nameEnd > nameEnd2)) {
            nameEnd = nameEnd2;
            noEq = true;
            valStart = nameEnd2;
            valEnd = nameEnd2;
            if (LOGGER.isLoggable(Level.FINEST)) {
               LOGGER.log(Level.FINEST, "no equal {0} {1} {2}", new Object[]{pos, nameEnd2, new String(chars, pos, nameEnd2 - pos)});
            }
         }

         if (nameEnd == -1) {
            nameEnd = end;
         }

         if (!noEq) {
            valStart = nameEnd < end ? nameEnd + 1 : end;
            valEnd = CharChunk.indexOf(chars, valStart, end, '&');
            if (valEnd == -1) {
               valEnd = valStart < end ? end : valStart;
            }
         }

         pos = valEnd + 1;
         if (nameEnd > nameStart) {
            try {
               this.tmpNameC.append(chars, nameStart, nameEnd - nameStart);
               this.tmpValueC.append(chars, valStart, valEnd - valStart);
               if (LOGGER.isLoggable(Level.FINEST)) {
                  LOGGER.log(Level.FINEST, "{0}= {1}", new Object[]{this.tmpNameC, this.tmpValueC});
               }

               URLDecoder.decode(this.tmpNameC, this.tmpNameC, true, this.queryStringEncoding.name());
               URLDecoder.decode(this.tmpValueC, this.tmpValueC, true, this.queryStringEncoding.name());
               if (LOGGER.isLoggable(Level.FINEST)) {
                  LOGGER.log(Level.FINEST, "{0}= {1}", new Object[]{this.tmpNameC, this.tmpValueC});
               }

               this.addParameter(this.tmpNameC.toString(), this.tmpValueC.toString());
            } catch (Exception var19) {
               ++decodeFailCount;
               if (LOGGER.isLoggable(Level.FINEST)) {
                  LOGGER.log(Level.FINEST, LogMessages.FINE_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_DEBUG(this.origName.toString(), this.origValue.toString()));
               } else if (LOGGER.isLoggable(Level.INFO) && decodeFailCount == 1) {
                  String name = this.tmpNameC.getLength() > 0 ? this.tmpNameC.toString() : "unavailable";
                  String value = this.tmpValueC.getLength() > 0 ? this.tmpValueC.toString() : "unavailable";
                  LOGGER.log(Level.INFO, LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_INFO(var19.getMessage(), name, value));
                  LOGGER.log(Level.FINE, "Decoding stacktrace.", var19);
               }
            } finally {
               this.tmpNameC.recycle();
               this.tmpValueC.recycle();
            }
         }
      } while(pos < end);

      if (!LOGGER.isLoggable(Level.FINEST) && decodeFailCount > 1) {
         LOGGER.info(LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_MULTIPLE_DECODING_FAIL(decodeFailCount));
      }

   }

   public void processParameters(DataChunk data) {
      this.processParameters(data, this.encoding);
   }

   public void processParameters(DataChunk data, Charset encoding) {
      if (data != null && !data.isNull() && data.getLength() > 0) {
         try {
            if (data.getType() == DataChunk.Type.Buffer) {
               BufferChunk bc = data.getBufferChunk();
               this.processParameters(bc.getBuffer(), bc.getStart(), bc.getLength(), encoding);
            } else {
               if (data.getType() != DataChunk.Type.Chars) {
                  data.toChars(encoding);
               }

               CharChunk cc = data.getCharChunk();
               this.processParameters(cc.getChars(), cc.getStart(), cc.getLength());
            }

         } catch (CharConversionException var4) {
            throw new IllegalStateException(var4);
         }
      }
   }

   public String paramsAsString() {
      StringBuilder sb = new StringBuilder();
      Iterator var2 = this.paramHashValues.keySet().iterator();

      while(var2.hasNext()) {
         String s = (String)var2.next();
         sb.append(s).append('=');
         ArrayList v = (ArrayList)this.paramHashValues.get(s);
         int i = 0;

         for(int len = v.size(); i < len; ++i) {
            sb.append((String)v.get(i)).append(',');
         }

         sb.append('\n');
      }

      return sb.toString();
   }

   public void processParameters(String str) {
      int end = str.length();
      int pos = 0;
      int decodeFailCount = 0;
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Process parameters. String: {0}", str);
      }

      do {
         if (this.limit > -1 && this.parameterCount >= this.limit) {
            LOGGER.warning(LogMessages.WARNING_GRIZZLY_HTTP_SEVERE_GRIZZLY_HTTP_PARAMETERS_MAX_COUNT_FAIL(this.limit));
            break;
         }

         boolean noEq = false;
         int valStart = -1;
         int valEnd = -1;
         int nameStart = pos;
         int nameEnd = str.indexOf(61, pos);
         int nameEnd2 = str.indexOf(38, pos);
         if (nameEnd2 == -1) {
            nameEnd2 = end;
         }

         if (nameEnd2 != -1 && (nameEnd == -1 || nameEnd > nameEnd2)) {
            nameEnd = nameEnd2;
            noEq = true;
            valStart = nameEnd2;
            valEnd = nameEnd2;
            if (LOGGER.isLoggable(Level.FINEST)) {
               LOGGER.log(Level.FINEST, "no equal {0} {1} {2}", new Object[]{pos, nameEnd2, str.substring(pos, nameEnd2)});
            }
         }

         if (nameEnd == -1) {
            nameEnd = end;
         }

         if (!noEq) {
            valStart = nameEnd + 1;
            valEnd = str.indexOf(38, valStart);
            if (valEnd == -1) {
               valEnd = valStart < end ? end : valStart;
            }
         }

         pos = valEnd + 1;
         if (nameEnd > nameStart) {
            if (LOGGER.isLoggable(Level.FINEST)) {
               LOGGER.log(Level.FINEST, "XXX {0} {1} {2} {3}", new Object[]{nameStart, nameEnd, valStart, valEnd});
            }

            try {
               this.tmpNameC.append(str, nameStart, nameEnd - nameStart);
               this.tmpValueC.append(str, valStart, valEnd - valStart);
               if (LOGGER.isLoggable(Level.FINEST)) {
                  LOGGER.log(Level.FINEST, "{0}= {1}", new Object[]{this.tmpNameC, this.tmpValueC});
               }

               URLDecoder.decode(this.tmpNameC, true);
               URLDecoder.decode(this.tmpValueC, true);
               if (LOGGER.isLoggable(Level.FINEST)) {
                  LOGGER.log(Level.FINEST, "{0}= {1}", new Object[]{this.tmpNameC, this.tmpValueC});
               }

               this.addParameter(this.tmpNameC.toString(), this.tmpValueC.toString());
            } catch (Exception var17) {
               ++decodeFailCount;
               if (LOGGER.isLoggable(Level.FINEST)) {
                  LOGGER.log(Level.FINEST, LogMessages.FINE_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_DEBUG(this.origName.toString(), this.origValue.toString()));
               } else if (LOGGER.isLoggable(Level.INFO) && decodeFailCount == 1) {
                  String name = this.tmpNameC.getLength() > 0 ? this.tmpNameC.toString() : "unavailable";
                  String value = this.tmpValueC.getLength() > 0 ? this.tmpValueC.toString() : "unavailable";
                  LOGGER.log(Level.INFO, LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_INFO(var17.getMessage(), name, value));
                  LOGGER.log(Level.FINE, "Decoding stacktrace.", var17);
               }
            } finally {
               this.tmpNameC.recycle();
               this.tmpValueC.recycle();
            }
         }
      } while(pos < end);

      if (!LOGGER.isLoggable(Level.FINEST) && decodeFailCount > 1) {
         LOGGER.info(LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_MULTIPLE_DECODING_FAIL(decodeFailCount));
      }

   }

   static {
      DEFAULT_ENCODING = Constants.DEFAULT_HTTP_CHARACTER_ENCODING;
      DEFAULT_CHARSET = Constants.DEFAULT_HTTP_CHARSET;
   }
}
