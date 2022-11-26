package com.sun.faces.renderkit.html_basic;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.io.FastStringWriter;
import com.sun.faces.util.HtmlUtils;
import com.sun.faces.util.MessageUtils;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.el.ValueExpression;
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
   private boolean withinScript;
   private boolean withinStyle;
   private boolean scriptOrStyleSrc;
   private boolean isPartial;
   private boolean isXhtml;
   private Writer origWriter;
   private FastStringWriter scriptBuffer;
   private FastStringWriter attributesBuffer;
   private Boolean isScriptHidingEnabled;
   private Boolean isScriptInAttributeValueEnabled;
   private char[] buffer;
   private static final int cdataBufferSize = 1024;
   private char[] cdataBuffer;
   private int cdataBufferLength;
   private static final int cdataTextBufferSize = 128;
   private char[] cdataTextBuffer;
   private Map passthroughAttributes;
   private char[] textBuffer;
   private char[] charHolder;
   private LinkedList elementNames;
   private static final String BREAKCDATA = "]]><![CDATA[";
   private static final char[] ESCAPEDSINGLEBRACKET = "]]]><![CDATA[".toCharArray();
   private static final char[] ESCAPEDLT = "&lt;]]><![CDATA[".toCharArray();
   private static final char[] ESCAPEDSTART = "&lt;]]><![CDATA[![".toCharArray();
   private static final char[] ESCAPEDEND = "]]]><![CDATA[]>".toCharArray();
   private static final int CLOSEBRACKET = 93;
   private static final int LT = 60;
   static final Pattern CDATA_START_SLASH_SLASH = Pattern.compile("^//\\s*\\Q<![CDATA[\\E");
   static final Pattern CDATA_END_SLASH_SLASH = Pattern.compile("//\\s*\\Q]]>\\E$");
   static final Pattern CDATA_START_SLASH_STAR = Pattern.compile("^/\\*\\s*\\Q<![CDATA[\\E\\s*\\*/");
   static final Pattern CDATA_END_SLASH_STAR = Pattern.compile("/\\*\\s*\\Q]]>\\E\\s*\\*/$");

   public HtmlResponseWriter(Writer writer, String contentType, String encoding) throws FacesException {
      this(writer, contentType, encoding, (Boolean)null, (Boolean)null, (WebConfiguration.DisableUnicodeEscaping)null, false);
   }

   public HtmlResponseWriter(Writer writer, String contentType, String encoding, Boolean isScriptHidingEnabled, Boolean isScriptInAttributeValueEnabled, WebConfiguration.DisableUnicodeEscaping disableUnicodeEscaping, boolean isPartial) throws FacesException {
      this.contentType = "text/html";
      this.encoding = null;
      this.writer = null;
      this.buffer = new char[1028];
      this.cdataBuffer = new char[1024];
      this.cdataBufferLength = 0;
      this.cdataTextBuffer = new char[128];
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

      this.isPartial = isPartial;
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
         HtmlResponseWriter responseWriter = new HtmlResponseWriter(writer, this.getContentType(), this.getCharacterEncoding(), this.isScriptHidingEnabled, this.isScriptInAttributeValueEnabled, this.disableUnicodeEscaping, this.isPartial);
         responseWriter.dontEscape = this.dontEscape;
         responseWriter.writingCdata = this.writingCdata;
         return responseWriter;
      } catch (FacesException var3) {
         throw new IllegalStateException();
      }
   }

   public void endDocument() throws IOException {
      if (this.writer instanceof FastStringWriter) {
         FastStringWriter fastStringWriter = (FastStringWriter)this.writer;
         String result = fastStringWriter.getBuffer().toString();
         fastStringWriter.reset();
         this.writer = this.origWriter;
         this.writer.write(result);
      }

      this.writer.flush();
   }

   public void endElement(String name) throws IOException {
      if (name == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "name"));
      } else {
         if ("script".equalsIgnoreCase(name)) {
            this.withinScript = false;
         }

         if ("style".equalsIgnoreCase(name)) {
            this.withinStyle = false;
         }

         if (!this.withinScript && !this.withinStyle) {
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

         if (this.withinScript && !this.isScript) {
            if (!this.withinStyle || this.isStyle) {
               this.isStyle = false;
            }
         } else {
            this.isScript = false;
         }

         if (!this.withinScript && !this.withinScript) {
            this.dontEscape = false;
         }

         if ("cdata".equalsIgnoreCase(name)) {
            this.endCDATA();
         } else {
            if (this.closeStart) {
               boolean isEmptyElement = HtmlUtils.isEmptyElement(name);
               if (isEmptyElement) {
                  this.flushAttributes();
                  this.writer.write(" />");
                  this.closeStart = false;
                  this.popElementName(name);
                  return;
               }

               this.flushAttributes();
               this.writer.write(62);
               this.closeStart = false;
            }

            this.writer.write("</");
            this.writer.write(this.popElementName(name));
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
         if ("script".equalsIgnoreCase(name)) {
            this.withinScript = true;
         }

         if ("style".equalsIgnoreCase(name)) {
            this.withinStyle = true;
         }

         this.closeStartIfNecessary();
         this.isScriptOrStyle(name);
         this.scriptOrStyleSrc = false;
         if ("cdata".equalsIgnoreCase(name)) {
            this.isCdata = true;
            this.startCDATA();
         } else {
            if (this.writingCdata) {
               this.isCdata = false;
               this.writingCdata = true;
            }

            if (null != componentForElement) {
               Map passThroughAttrs = componentForElement.getPassThroughAttributes(false);
               if (null != passThroughAttrs && !passThroughAttrs.isEmpty()) {
                  this.considerPassThroughAttributes(passThroughAttrs);
               }
            }

            this.writer.write(60);
            String elementName = this.pushElementName(name);
            this.writer.write(elementName);
            this.closeStart = true;
         }
      }
   }

   public void startCDATA() throws IOException {
      if (this.writingCdata) {
         throw new IllegalStateException("CDATA tags may not nest");
      } else {
         this.closeStartIfNecessary();
         this.writingCdata = true;
         this.writer.write("<![CDATA[");
         this.closeStart = false;
      }
   }

   public void endCDATA() throws IOException {
      this.closeStartIfNecessary();
      this.writer.write("]]>");
      this.writingCdata = false;
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
            if (!this.containsPassThroughAttribute(name)) {
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
   }

   public void writeComment(Object comment) throws IOException {
      if (comment == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR"));
      } else if (!this.writingCdata) {
         this.closeStartIfNecessary();
         this.writer.write("<!--");
         String str = comment.toString();
         this.ensureTextBufferCapacity(str);
         HtmlUtils.writeText(this.writer, true, true, this.buffer, str, this.textBuffer);
         this.writer.write("-->");
      }
   }

   public void writeText(char text) throws IOException {
      this.closeStartIfNecessary();
      if (this.dontEscape) {
         if (this.writingCdata) {
            this.charHolder[0] = text;
            this.writeUnescapedCData(this.charHolder, 0, 1);
         } else {
            this.writer.write(text);
         }
      } else if (!this.isPartial && this.writingCdata) {
         assert this.writingCdata;

         this.charHolder[0] = text;
         this.writeEscaped(this.charHolder, 0, 1);
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
            if (this.writingCdata) {
               this.writeUnescapedCData(text, 0, text.length);
            } else {
               this.writer.write(text);
            }
         } else if (!this.isPartial && this.writingCdata) {
            assert this.writingCdata;

            this.writeEscaped(text, 0, text.length);
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
         String textStr = text.toString();
         if (this.dontEscape) {
            if (this.writingCdata) {
               this.writeUnescapedCData(textStr.toCharArray(), 0, textStr.length());
            } else {
               this.writer.write(textStr);
            }
         } else if (!this.isPartial && this.writingCdata) {
            assert this.writingCdata;

            int textLen = textStr.length();
            if (textLen > 128) {
               this.writeEscaped(textStr.toCharArray(), 0, textLen);
            } else if (textLen >= 16) {
               textStr.getChars(0, textLen, this.cdataTextBuffer, 0);
               this.writeEscaped(this.cdataTextBuffer, 0, textLen);
            } else {
               for(int i = 0; i < textLen; ++i) {
                  this.cdataTextBuffer[i] = textStr.charAt(i);
               }

               this.writeEscaped(this.cdataTextBuffer, 0, textLen);
            }
         } else {
            this.ensureTextBufferCapacity(textStr);
            HtmlUtils.writeText(this.writer, this.escapeUnicode, this.escapeIso, this.buffer, textStr, this.textBuffer);
         }

      }
   }

   public void writeText(char[] text, int off, int len) throws IOException {
      if (text == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "text"));
      } else if (off >= 0 && off <= text.length && len >= 0 && len <= text.length) {
         this.closeStartIfNecessary();
         if (len != 0) {
            if (this.dontEscape) {
               if (this.writingCdata) {
                  this.writeUnescapedCData(text, off, len);
               } else {
                  this.writer.write(text, off, len);
               }
            } else if (!this.isPartial && this.writingCdata) {
               assert this.writingCdata;

               this.writeEscaped(text, off, len);
            } else {
               HtmlUtils.writeText(this.writer, this.escapeUnicode, this.escapeIso, this.buffer, text, off, len);
            }

         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void writeURIAttribute(String name, Object value, String componentPropertyName) throws IOException {
      if (null == name || !this.containsPassThroughAttribute(name)) {
         this.writeURIAttributeIgnoringPassThroughAttributes(name, value, componentPropertyName, false);
      }
   }

   private void writeURIAttributeIgnoringPassThroughAttributes(String name, Object value, String componentPropertyName, boolean isPassthrough) throws IOException {
      if (name == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "name"));
      } else if (value == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "value"));
      } else if (!this.isCdata) {
         if (!name.equals("elementName")) {
            if (name.equalsIgnoreCase("src") && this.isScriptOrStyle()) {
               this.scriptOrStyleSrc = true;
            }

            this.attributesBuffer.write(32);
            this.attributesBuffer.write(name);
            this.attributesBuffer.write("=\"");
            String stringValue = value.toString();
            this.ensureTextBufferCapacity(stringValue);
            if (!stringValue.startsWith("javascript:") && !isPassthrough) {
               HtmlUtils.writeURL(this.attributesBuffer, stringValue, this.textBuffer, this.encoding);
            } else {
               HtmlUtils.writeAttribute(this.attributesBuffer, this.escapeUnicode, this.escapeIso, this.buffer, stringValue, this.textBuffer, this.isScriptInAttributeValueEnabled);
            }

            this.attributesBuffer.write(34);
         }
      }
   }

   private void ensureTextBufferCapacity(String source) {
      int len = source.length();
      if (this.textBuffer.length < len) {
         this.textBuffer = new char[len * 2];
      }

      if (this.buffer.length < len) {
         this.buffer = new char[len * 2];
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

   private void considerPassThroughAttributes(Map toCopy) {
      assert null != toCopy && !toCopy.isEmpty();

      if (null != this.passthroughAttributes) {
         throw new IllegalStateException("Error, this method should only be called once per instance.");
      } else {
         this.passthroughAttributes = new ConcurrentHashMap(toCopy);
      }
   }

   private boolean containsPassThroughAttribute(String attrName) {
      boolean result = false;
      if (null != this.passthroughAttributes) {
         result = this.passthroughAttributes.containsKey(attrName);
      }

      return result;
   }

   private void flushAttributes() throws IOException {
      boolean hasPassthroughAttributes = null != this.passthroughAttributes && !this.passthroughAttributes.isEmpty();
      if (hasPassthroughAttributes) {
         FacesContext context = FacesContext.getCurrentInstance();
         Iterator var3 = this.passthroughAttributes.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            Object valObj = entry.getValue();
            String val = this.getAttributeValue(context, valObj);
            String key = (String)entry.getKey();
            if (val != null) {
               this.writeURIAttributeIgnoringPassThroughAttributes(key, val, key, true);
            }
         }
      }

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

      if (hasPassthroughAttributes) {
         this.passthroughAttributes.clear();
         this.passthroughAttributes = null;
      }

   }

   private String getAttributeValue(FacesContext context, Object valObj) {
      String val;
      if (valObj instanceof ValueExpression) {
         Object result = ((ValueExpression)valObj).getValue(context.getELContext());
         val = result != null ? result.toString() : null;
      } else {
         val = valObj.toString();
      }

      return val;
   }

   private String pushElementName(String original) {
      if (original.equals("option")) {
         if (this.elementNames == null) {
            this.elementNames = new LinkedList();
         }

         this.elementNames.push(original);
         return original;
      } else {
         String name = this.getElementName(original);
         if (this.passthroughAttributes != null) {
            this.passthroughAttributes.remove("elementName");
            if (this.passthroughAttributes.isEmpty()) {
               this.passthroughAttributes = null;
            }
         }

         if (!original.equals(name) || this.elementNames != null) {
            if (this.elementNames == null) {
               this.elementNames = new LinkedList();
            }

            this.elementNames.push(name);
         }

         return name;
      }
   }

   private String popElementName(String original) {
      return this.elementNames != null && !this.elementNames.isEmpty() ? (String)this.elementNames.pop() : original;
   }

   private String getElementName(String name) {
      if (this.containsPassThroughAttribute("elementName")) {
         FacesContext context = FacesContext.getCurrentInstance();
         String elementName = this.getAttributeValue(context, this.passthroughAttributes.get("elementName"));
         if (elementName != null && elementName.trim().length() > 0) {
            return elementName;
         }
      }

      return name;
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
         if (!this.withinScript && !this.withinStyle) {
            this.dontEscape = false;
         }
      }

      return this.isScript || this.isStyle;
   }

   private boolean isScriptOrStyle() {
      return this.isScript || this.isStyle;
   }

   private void writeEscaped(char[] cbuf, int offset, int length) throws IOException {
      if (cbuf != null && cbuf.length != 0 && length != 0) {
         if (offset >= 0 && length >= 0 && offset + length <= cbuf.length) {
            if (length == 1) {
               if (cbuf[offset] == '<') {
                  this.appendBuffer(ESCAPEDLT);
               } else if (cbuf[offset] == ']') {
                  this.appendBuffer(ESCAPEDSINGLEBRACKET);
               } else {
                  this.appendBuffer(cbuf[offset]);
               }

               this.flushBuffer();
            } else if (length == 2) {
               if (cbuf[offset] == '<' && cbuf[offset + 1] == '!') {
                  this.appendBuffer(ESCAPEDLT);
                  this.appendBuffer(cbuf[offset + 1]);
               } else if (cbuf[offset] == ']' && cbuf[offset + 1] == ']') {
                  this.appendBuffer(ESCAPEDSINGLEBRACKET);
                  this.appendBuffer(ESCAPEDSINGLEBRACKET);
               } else {
                  this.appendBuffer(cbuf[offset]);
                  this.appendBuffer(cbuf[offset + 1]);
               }

               this.flushBuffer();
            } else {
               boolean last = false;

               for(int i = offset; i < length - 2; ++i) {
                  if (cbuf[i] == '<' && cbuf[i + 1] == '!' && cbuf[i + 2] == '[') {
                     this.appendBuffer(ESCAPEDSTART);
                     i += 2;
                  } else if (cbuf[i] == ']' && cbuf[i + 1] == ']' && cbuf[i + 2] == '>') {
                     this.appendBuffer(ESCAPEDEND);
                     i += 2;
                  } else {
                     this.appendBuffer(cbuf[i]);
                  }

                  if (i == offset + length - 1) {
                     last = true;
                  }
               }

               if (!last) {
                  if (cbuf[offset + length - 2] == '<') {
                     this.appendBuffer(ESCAPEDLT);
                  } else if (cbuf[offset + length - 2] == ']') {
                     this.appendBuffer(ESCAPEDSINGLEBRACKET);
                  } else {
                     this.appendBuffer(cbuf[offset + length - 2]);
                  }

                  if (cbuf[offset + length - 1] == '<') {
                     this.appendBuffer(ESCAPEDLT);
                  } else if (cbuf[offset + length - 1] == ']') {
                     this.appendBuffer(ESCAPEDSINGLEBRACKET);
                  } else {
                     this.appendBuffer(cbuf[offset + length - 1]);
                  }
               }

               this.flushBuffer();
            }
         } else {
            throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > cbuf.length");
         }
      }
   }

   private void appendBuffer(char[] cbuf) throws IOException {
      if (cbuf.length + this.cdataBufferLength >= 1024) {
         this.flushBuffer();
      }

      if (cbuf.length >= 1024) {
         this.writer.write(cbuf);
      }

      System.arraycopy(cbuf, 0, this.cdataBuffer, this.cdataBufferLength, cbuf.length);
      this.cdataBufferLength += cbuf.length;
   }

   private void appendBuffer(char c) throws IOException {
      if (this.cdataBufferLength + 1 >= 1024) {
         this.flushBuffer();
      }

      this.cdataBuffer[this.cdataBufferLength] = c;
      ++this.cdataBufferLength;
   }

   private void flushBuffer() throws IOException {
      if (this.cdataBufferLength != 0) {
         this.writer.write(this.cdataBuffer, 0, this.cdataBufferLength);
         this.cdataBufferLength = 0;
      }
   }

   private void writeUnescapedCData(char[] cbuf, int offset, int length) throws IOException {
      if (length == 1) {
         if (cbuf[offset] == ']') {
            this.appendBuffer(ESCAPEDSINGLEBRACKET);
         } else {
            this.appendBuffer(cbuf[offset]);
         }

         this.flushBuffer();
      } else if (length == 2) {
         if (cbuf[offset] == ']' && cbuf[offset + 1] == ']') {
            this.appendBuffer(ESCAPEDSINGLEBRACKET);
            this.appendBuffer(ESCAPEDSINGLEBRACKET);
         } else {
            this.appendBuffer(cbuf[offset]);
            this.appendBuffer(cbuf[offset + 1]);
         }

         this.flushBuffer();
      } else {
         boolean last = false;

         for(int i = offset; i < length - 2; ++i) {
            if (cbuf[i] == ']' && cbuf[i + 1] == ']' && cbuf[i + 2] == '>') {
               this.appendBuffer(ESCAPEDEND);
               i += 2;
            } else {
               this.appendBuffer(cbuf[i]);
            }

            if (i == offset + length - 1) {
               last = true;
            }
         }

         if (!last) {
            if (cbuf[offset + length - 2] == ']') {
               this.appendBuffer(ESCAPEDSINGLEBRACKET);
            } else {
               this.appendBuffer(cbuf[offset + length - 2]);
            }

            if (cbuf[offset + length - 1] == ']') {
               this.appendBuffer(ESCAPEDSINGLEBRACKET);
            } else {
               this.appendBuffer(cbuf[offset + length - 1]);
            }
         }

         this.flushBuffer();
      }
   }
}
