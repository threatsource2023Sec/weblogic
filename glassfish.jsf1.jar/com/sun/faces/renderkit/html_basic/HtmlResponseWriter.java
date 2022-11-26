package com.sun.faces.renderkit.html_basic;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.io.FastStringWriter;
import com.sun.faces.util.HtmlUtils;
import com.sun.faces.util.MessageUtils;
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class HtmlResponseWriter extends ResponseWriter {
   private String contentType;
   private String encoding;
   private Writer writer;
   private boolean closeStart;
   private WebConfiguration.DisableUnicodeEscaping disableUnicodeEscaping;
   private boolean escapeUnicode;
   private boolean escapeIso;
   private boolean dontEscape;
   private boolean writingCdata;
   private boolean isCdata;
   private boolean isScript;
   private boolean isStyle;
   private boolean scriptOrStyleSrc;
   private boolean isXhtml;
   private Writer origWriter;
   private FastStringWriter scriptBuffer;
   private FastStringWriter attributesBuffer;
   private Boolean isScriptHidingEnabled;
   private Boolean isScriptInAttributeValueEnabled;
   private char[] buffer;
   private char[] textBuffer;
   private char[] charHolder;
   static final Pattern CDATA_START_SLASH_SLASH = Pattern.compile("^//\\s*\\Q<![CDATA[\\E");
   static final Pattern CDATA_END_SLASH_SLASH = Pattern.compile("//\\s*\\Q]]>\\E$");
   static final Pattern CDATA_START_SLASH_STAR = Pattern.compile("^/\\*\\s*\\Q<![CDATA[\\E\\s*\\*/");
   static final Pattern CDATA_END_SLASH_STAR = Pattern.compile("/\\*\\s*\\Q]]>\\E\\s*\\*/$");

   public HtmlResponseWriter(Writer writer, String contentType, String encoding) throws FacesException {
      this(writer, contentType, encoding, (Boolean)null, (Boolean)null, (WebConfiguration.DisableUnicodeEscaping)null);
   }

   public HtmlResponseWriter(Writer writer, String contentType, String encoding, Boolean isScriptHidingEnabled, Boolean isScriptInAttributeValueEnabled, WebConfiguration.DisableUnicodeEscaping disableUnicodeEscaping) throws FacesException {
      this.contentType = "text/html";
      this.encoding = null;
      this.writer = null;
      this.buffer = new char[1028];
      this.textBuffer = new char[128];
      this.charHolder = new char[1];
      this.writer = writer;
      if (null != contentType) {
         this.contentType = contentType;
      }

      this.encoding = encoding;
      WebConfiguration webConfig = null;
      if (isScriptHidingEnabled == null) {
         webConfig = this.getWebConfiguration(webConfig);
         isScriptHidingEnabled = null == webConfig ? WebConfiguration.BooleanWebContextInitParameter.EnableJSStyleHiding.getDefaultValue() : webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableJSStyleHiding);
      }

      if (isScriptInAttributeValueEnabled == null) {
         webConfig = this.getWebConfiguration(webConfig);
         isScriptInAttributeValueEnabled = null == webConfig ? WebConfiguration.BooleanWebContextInitParameter.EnableScriptInAttributeValue.getDefaultValue() : webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableScriptInAttributeValue);
      }

      if (disableUnicodeEscaping == null) {
         webConfig = this.getWebConfiguration(webConfig);
         disableUnicodeEscaping = WebConfiguration.DisableUnicodeEscaping.getByValue(null == webConfig ? WebConfiguration.WebContextInitParameter.DisableUnicodeEscaping.getDefaultValue() : webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.DisableUnicodeEscaping));
         if (disableUnicodeEscaping == null) {
            disableUnicodeEscaping = WebConfiguration.DisableUnicodeEscaping.False;
         }
      }

      this.isScriptHidingEnabled = isScriptHidingEnabled;
      this.isScriptInAttributeValueEnabled = isScriptInAttributeValueEnabled;
      this.disableUnicodeEscaping = disableUnicodeEscaping;
      this.attributesBuffer = new FastStringWriter(128);
      if (!HtmlUtils.validateEncoding(encoding)) {
         throw new IllegalArgumentException(MessageUtils.getExceptionMessageString("com.sun.faces.ENCODING_ERROR"));
      } else {
         String charsetName = encoding.toUpperCase();
         switch (disableUnicodeEscaping) {
            case True:
               this.escapeUnicode = false;
               this.escapeIso = false;
               break;
            case False:
               this.escapeUnicode = true;
               this.escapeIso = true;
               break;
            case Auto:
               this.escapeUnicode = !HtmlUtils.isUTFencoding(charsetName);
               this.escapeIso = !HtmlUtils.isISO8859_1encoding(charsetName) && !HtmlUtils.isUTFencoding(charsetName);
         }

      }
   }

   private WebConfiguration getWebConfiguration(WebConfiguration webConfig) {
      if (webConfig != null) {
         return webConfig;
      } else {
         FacesContext context = FacesContext.getCurrentInstance();
         if (null != context) {
            ExternalContext extContext = context.getExternalContext();
            if (null != extContext) {
               webConfig = WebConfiguration.getInstance(extContext);
            }
         }

         return webConfig;
      }
   }

   public void close() throws IOException {
      this.closeStartIfNecessary();
      this.writer.close();
   }

   public void flush() throws IOException {
      this.closeStartIfNecessary();
   }

   public String getContentType() {
      return this.contentType;
   }

   public ResponseWriter cloneWithWriter(Writer writer) {
      try {
         return new HtmlResponseWriter(writer, this.getContentType(), this.getCharacterEncoding(), this.isScriptHidingEnabled, this.isScriptInAttributeValueEnabled, this.disableUnicodeEscaping);
      } catch (FacesException var3) {
         throw new IllegalStateException();
      }
   }

   public void endDocument() throws IOException {
      this.writer.flush();
   }

   public void endElement(String name) throws IOException {
      if (name == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "name"));
      } else {
         if (!this.writingCdata) {
            this.dontEscape = false;
         }

         this.isXhtml = this.getContentType().equals("application/xhtml+xml");
         if (this.isScriptOrStyle(name) && !this.scriptOrStyleSrc && this.writer instanceof FastStringWriter) {
            String result = ((FastStringWriter)this.writer).getBuffer().toString();
            this.writer = this.origWriter;
            if (result != null) {
               String trim = result.trim();
               if (this.isXhtml) {
                  if (this.isScript) {
                     Matcher cdataStartSlashSlash = CDATA_START_SLASH_SLASH.matcher(trim);
                     Matcher cdataEndSlashSlash = CDATA_END_SLASH_SLASH.matcher(trim);
                     Matcher cdataStartSlashStar = CDATA_START_SLASH_STAR.matcher(trim);
                     Matcher cdataEndSlashStar = CDATA_END_SLASH_STAR.matcher(trim);
                     int trimLen = trim.length();
                     int start;
                     int end;
                     if (cdataStartSlashSlash.find() && cdataEndSlashSlash.find()) {
                        start = cdataStartSlashSlash.end() - cdataStartSlashSlash.start();
                        end = trimLen - (cdataEndSlashSlash.end() - cdataEndSlashSlash.start());
                        this.writer.write(trim.substring(start, end));
                     } else if (null != cdataStartSlashSlash.reset() && cdataStartSlashSlash.find() && cdataEndSlashStar.find()) {
                        start = cdataStartSlashSlash.end() - cdataStartSlashSlash.start();
                        end = trimLen - (cdataEndSlashStar.end() - cdataEndSlashStar.start());
                        this.writer.write(trim.substring(start, end));
                     } else if (cdataStartSlashStar.find() && null != cdataEndSlashStar.reset() && cdataEndSlashStar.find()) {
                        start = cdataStartSlashStar.end() - cdataStartSlashStar.start();
                        end = trimLen - (cdataEndSlashStar.end() - cdataEndSlashStar.start());
                        this.writer.write(trim.substring(start, end));
                     } else if (null != cdataStartSlashStar.reset() && cdataStartSlashStar.find() && null != cdataEndSlashStar.reset() && cdataEndSlashSlash.find()) {
                        start = cdataStartSlashStar.end() - cdataStartSlashStar.start();
                        end = trimLen - (cdataEndSlashSlash.end() - cdataEndSlashSlash.start());
                        this.writer.write(trim.substring(start, end));
                     } else {
                        this.writer.write(result);
                     }
                  } else if (trim.startsWith("<![CDATA[") && trim.endsWith("]]>")) {
                     this.writer.write(trim.substring(9, trim.length() - 3));
                  } else {
                     this.writer.write(result);
                  }
               } else if (trim.startsWith("<!--") && trim.endsWith("//-->")) {
                  this.writer.write(trim.substring(4, trim.length() - 5));
               } else {
                  this.writer.write(result);
               }
            }

            if (this.isXhtml) {
               if (!this.writingCdata) {
                  if (this.isScript) {
                     this.writer.write("\n//]]>\n");
                  } else {
                     this.writer.write("\n]]>\n");
                  }
               }
            } else if (this.isScriptHidingEnabled) {
               this.writer.write("\n//-->\n");
            }
         }

         this.isScript = false;
         this.isStyle = false;
         if ("cdata".equalsIgnoreCase(name)) {
            this.writer.write("]]>");
            this.writingCdata = false;
            this.isCdata = false;
            this.dontEscape = false;
         } else {
            if (this.closeStart) {
               boolean isEmptyElement = HtmlUtils.isEmptyElement(name);
               if (isEmptyElement) {
                  this.flushAttributes();
                  this.writer.write(" />");
                  this.closeStart = false;
                  return;
               }

               this.flushAttributes();
               this.writer.write(62);
               this.closeStart = false;
            }

            this.writer.write("</");
            this.writer.write(name);
            this.writer.write(62);
         }
      }
   }

   public String getCharacterEncoding() {
      return this.encoding;
   }

   public void startDocument() throws IOException {
   }

   public void startElement(String name, UIComponent componentForElement) throws IOException {
      if (name == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "name"));
      } else {
         this.closeStartIfNecessary();
         this.isScriptOrStyle(name);
         this.scriptOrStyleSrc = false;
         if ("cdata".equalsIgnoreCase(name)) {
            this.isCdata = true;
            this.writingCdata = true;
            this.dontEscape = true;
            this.writer.write("<![CDATA[");
            this.closeStart = false;
         } else {
            if (this.writingCdata) {
               this.isCdata = false;
               this.writingCdata = true;
               this.dontEscape = true;
            }

            this.writer.write(60);
            this.writer.write(name);
            this.closeStart = true;
         }
      }
   }

   public void write(char[] cbuf) throws IOException {
      this.closeStartIfNecessary();
      this.writer.write(cbuf);
   }

   public void write(int c) throws IOException {
      this.closeStartIfNecessary();
      this.writer.write(c);
   }

   public void write(String str) throws IOException {
      this.closeStartIfNecessary();
      this.writer.write(str);
   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      this.closeStartIfNecessary();
      this.writer.write(cbuf, off, len);
   }

   public void write(String str, int off, int len) throws IOException {
      this.closeStartIfNecessary();
      this.writer.write(str, off, len);
   }

   public void writeAttribute(String name, Object value, String componentPropertyName) throws IOException {
      if (name == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "name"));
      } else if (value != null) {
         if (!this.isCdata) {
            if (name.equalsIgnoreCase("src") && this.isScriptOrStyle()) {
               this.scriptOrStyleSrc = true;
            }

            Class valueClass = value.getClass();
            if (valueClass == Boolean.class) {
               if (Boolean.TRUE.equals(value)) {
                  this.attributesBuffer.write(32);
                  this.attributesBuffer.write(name);
                  this.attributesBuffer.write("=\"");
                  this.attributesBuffer.write(name);
                  this.attributesBuffer.write(34);
               }
            } else {
               this.attributesBuffer.write(32);
               this.attributesBuffer.write(name);
               this.attributesBuffer.write("=\"");
               String val = value.toString();
               this.ensureTextBufferCapacity(val);
               HtmlUtils.writeAttribute(this.attributesBuffer, this.escapeUnicode, this.escapeIso, this.buffer, val, this.textBuffer, this.isScriptInAttributeValueEnabled);
               this.attributesBuffer.write(34);
            }

         }
      }
   }

   public void writeComment(Object comment) throws IOException {
      if (comment == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
      } else if (!this.writingCdata) {
         this.closeStartIfNecessary();
         this.writer.write("<!--");
         this.writer.write(comment.toString());
         this.writer.write("-->");
      }
   }

   public void writeText(char text) throws IOException {
      this.closeStartIfNecessary();
      if (this.dontEscape) {
         this.writer.write(text);
      } else {
         this.charHolder[0] = text;
         HtmlUtils.writeText(this.writer, this.escapeUnicode, this.escapeIso, this.buffer, this.charHolder);
      }

   }

   public void writeText(char[] text) throws IOException {
      if (text == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "text"));
      } else {
         this.closeStartIfNecessary();
         if (this.dontEscape) {
            this.writer.write(text);
         } else {
            HtmlUtils.writeText(this.writer, this.escapeUnicode, this.escapeIso, this.buffer, text);
         }

      }
   }

   public void writeText(Object text, String componentPropertyName) throws IOException {
      if (text == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "text"));
      } else {
         this.closeStartIfNecessary();
         if (this.dontEscape) {
            this.writer.write(text.toString());
         } else {
            String val = text.toString();
            this.ensureTextBufferCapacity(val);
            HtmlUtils.writeText(this.writer, this.escapeUnicode, this.escapeIso, this.buffer, val, this.textBuffer);
         }

      }
   }

   public void writeText(char[] text, int off, int len) throws IOException {
      if (text == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "text"));
      } else if (off >= 0 && off <= text.length && len >= 0 && len <= text.length) {
         this.closeStartIfNecessary();
         if (this.dontEscape) {
            this.writer.write(text, off, len);
         } else {
            HtmlUtils.writeText(this.writer, this.escapeUnicode, this.escapeIso, this.buffer, text, off, len);
         }

      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void writeURIAttribute(String name, Object value, String componentPropertyName) throws IOException {
      if (name == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "name"));
      } else if (value == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "value"));
      } else if (!this.isCdata) {
         if (name.equalsIgnoreCase("src") && this.isScriptOrStyle()) {
            this.scriptOrStyleSrc = true;
         }

         this.attributesBuffer.write(32);
         this.attributesBuffer.write(name);
         this.attributesBuffer.write("=\"");
         String stringValue = value.toString();
         this.ensureTextBufferCapacity(stringValue);
         if (stringValue.startsWith("javascript:")) {
            HtmlUtils.writeAttribute(this.attributesBuffer, this.escapeUnicode, this.escapeIso, this.buffer, stringValue, this.textBuffer, this.isScriptInAttributeValueEnabled);
         } else {
            HtmlUtils.writeURL(this.attributesBuffer, stringValue, this.textBuffer, this.encoding);
         }

         this.attributesBuffer.write(34);
      }
   }

   private void ensureTextBufferCapacity(String source) {
      int len = source.length();
      if (this.textBuffer.length < len) {
         this.textBuffer = new char[len * 2];
      }

   }

   private void closeStartIfNecessary() throws IOException {
      if (this.closeStart) {
         this.flushAttributes();
         this.writer.write(62);
         this.closeStart = false;
         if (this.isScriptOrStyle() && !this.scriptOrStyleSrc) {
            this.isXhtml = this.getContentType().equals("application/xhtml+xml");
            if (this.isXhtml) {
               if (!this.writingCdata) {
                  if (this.isScript) {
                     this.writer.write("\n//<![CDATA[\n");
                  } else {
                     this.writer.write("\n<![CDATA[\n");
                  }
               }
            } else if (this.isScriptHidingEnabled) {
               this.writer.write("\n<!--\n");
            }

            this.origWriter = this.writer;
            if (this.scriptBuffer == null) {
               this.scriptBuffer = new FastStringWriter(1024);
            }

            this.scriptBuffer.reset();
            this.writer = this.scriptBuffer;
            this.isScript = false;
            this.isStyle = false;
         }
      }

   }

   private void flushAttributes() throws IOException {
      StringBuilder b = this.attributesBuffer.getBuffer();
      int totalLength = b.length();
      if (totalLength != 0) {
         int curIdx = 0;

         while(curIdx < totalLength) {
            int end;
            if (totalLength - curIdx > this.buffer.length) {
               end = curIdx + this.buffer.length;
               b.getChars(curIdx, end, this.buffer, 0);
               this.writer.write(this.buffer);
               curIdx += this.buffer.length;
            } else {
               end = totalLength - curIdx;
               b.getChars(curIdx, curIdx + end, this.buffer, 0);
               this.writer.write(this.buffer, 0, end);
               curIdx += end;
            }
         }

         this.attributesBuffer.reset();
      }

   }

   private boolean isScriptOrStyle(String name) {
      if ("script".equalsIgnoreCase(name)) {
         this.isScript = true;
         this.dontEscape = true;
      } else if ("style".equalsIgnoreCase(name)) {
         this.isStyle = true;
         this.dontEscape = true;
      } else {
         this.isScript = false;
         this.isStyle = false;
         this.dontEscape = false;
      }

      return this.isScript || this.isStyle;
   }

   private boolean isScriptOrStyle() {
      return this.isScript || this.isStyle;
   }
}
