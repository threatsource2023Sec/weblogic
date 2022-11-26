package org.apache.xmlbeans;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.xml.stream.Location;

public class XmlError implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final ResourceBundle _bundle = PropertyResourceBundle.getBundle("org.apache.xmlbeans.message");
   private String _message;
   private String _code;
   private String _source;
   private int _severity;
   private int _line;
   private int _column;
   private int _offset;
   private transient XmlCursor _cursor;
   public static final int SEVERITY_ERROR = 0;
   public static final int SEVERITY_WARNING = 1;
   public static final int SEVERITY_INFO = 2;

   public XmlError(XmlError src) {
      this._severity = 0;
      this._line = -1;
      this._column = -1;
      this._offset = -1;
      this._message = src.getMessage();
      this._code = src.getErrorCode();
      this._severity = src.getSeverity();
      this._source = src.getSourceName();
      this._line = src.getLine();
      this._column = src.getColumn();
      this._offset = src.getOffset();
      this._cursor = src.getCursorLocation();
   }

   private XmlError(String message, String code, int severity, String source, int line, int column, int offset, XmlCursor cursor) {
      this._severity = 0;
      this._line = -1;
      this._column = -1;
      this._offset = -1;
      this._message = message;
      this._code = code;
      this._severity = severity;
      this._source = source;
      this._line = line;
      this._column = column;
      this._offset = offset;
      this._cursor = cursor;
   }

   private XmlError(String code, Object[] args, int severity, String source, int line, int column, int offset, XmlCursor cursor) {
      this(formattedMessage(code, args), code, severity, source, line, column, offset, cursor);
   }

   protected XmlError(String message, String code, int severity, XmlCursor cursor) {
      this._severity = 0;
      this._line = -1;
      this._column = -1;
      this._offset = -1;
      String source = null;
      int line = -1;
      int column = -1;
      int offset = -1;
      if (cursor != null) {
         source = cursor.documentProperties().getSourceName();
         XmlCursor c = cursor.newCursor();
         XmlLineNumber ln = (XmlLineNumber)c.getBookmark(XmlLineNumber.class);
         if (ln == null) {
            ln = (XmlLineNumber)c.toPrevBookmark(XmlLineNumber.class);
         }

         if (ln != null) {
            line = ln.getLine();
            column = ln.getColumn();
            offset = ln.getOffset();
         }

         c.dispose();
      }

      this._message = message;
      this._code = code;
      this._severity = severity;
      this._source = source;
      this._line = line;
      this._column = column;
      this._offset = offset;
      this._cursor = cursor;
   }

   protected XmlError(String code, Object[] args, int severity, XmlCursor cursor) {
      this(formattedMessage(code, args), code, severity, cursor);
   }

   protected XmlError(String message, String code, int severity, Location loc) {
      this._severity = 0;
      this._line = -1;
      this._column = -1;
      this._offset = -1;
      String source = null;
      int line = -1;
      int column = -1;
      if (loc != null) {
         line = loc.getLineNumber();
         column = loc.getColumnNumber();
         source = loc.getPublicId();
         if (source == null) {
            source = loc.getSystemId();
         }
      }

      this._message = message;
      this._code = code;
      this._severity = severity;
      this._source = source;
      this._line = line;
      this._column = column;
   }

   protected XmlError(String code, Object[] args, int severity, Location loc) {
      this(formattedMessage(code, args), code, severity, loc);
   }

   public static XmlError forMessage(String message) {
      return forMessage(message, 0);
   }

   public static XmlError forMessage(String message, int severity) {
      return forSource(message, severity, (String)null);
   }

   public static XmlError forMessage(String code, Object[] args) {
      return forSource(code, args, 0, (String)null);
   }

   public static XmlError forMessage(String code, Object[] args, int severity) {
      return forSource(code, args, severity, (String)null);
   }

   public static XmlError forSource(String message, String sourceName) {
      return forLocation(message, 0, sourceName, -1, -1, -1);
   }

   public static XmlError forSource(String message, int severity, String sourceName) {
      return forLocation(message, severity, sourceName, -1, -1, -1);
   }

   public static XmlError forSource(String code, Object[] args, int severity, String sourceName) {
      return forLocation(code, args, severity, sourceName, -1, -1, -1);
   }

   public static XmlError forLocation(String message, String sourceName, Location location) {
      return new XmlError(message, (String)null, 0, sourceName, location.getLineNumber(), location.getColumnNumber(), -1, (XmlCursor)null);
   }

   public static XmlError forLocation(String message, String sourceName, int line, int column, int offset) {
      return new XmlError(message, (String)null, 0, sourceName, line, column, offset, (XmlCursor)null);
   }

   public static XmlError forLocation(String code, Object[] args, int severity, String sourceName, int line, int column, int offset) {
      return new XmlError(code, args, severity, sourceName, line, column, offset, (XmlCursor)null);
   }

   public static XmlError forLocation(String message, int severity, String sourceName, int line, int column, int offset) {
      return new XmlError(message, (String)null, severity, sourceName, line, column, offset, (XmlCursor)null);
   }

   public static XmlError forLocationAndCursor(String message, int severity, String sourceName, int line, int column, int offset, XmlCursor cursor) {
      return new XmlError(message, (String)null, severity, sourceName, line, column, offset, cursor);
   }

   public static XmlError forObject(String message, XmlObject xobj) {
      return forObject(message, 0, xobj);
   }

   public static XmlError forObject(String code, Object[] args, XmlObject xobj) {
      return forObject(code, args, 0, xobj);
   }

   public static XmlError forObject(String message, int severity, XmlObject xobj) {
      if (xobj == null) {
         return forMessage(message, severity);
      } else {
         XmlCursor cur = xobj.newCursor();
         XmlError result = forCursor(message, severity, cur);
         return result;
      }
   }

   public static XmlError forObject(String code, Object[] args, int severity, XmlObject xobj) {
      if (xobj == null) {
         return forMessage(code, args, severity);
      } else {
         XmlCursor cur = xobj.newCursor();
         XmlError result = forCursor(code, args, severity, cur);
         return result;
      }
   }

   public static XmlError forCursor(String message, XmlCursor cursor) {
      return forCursor(message, 0, cursor);
   }

   public static XmlError forCursor(String code, Object[] args, XmlCursor cursor) {
      return forCursor(code, args, 0, cursor);
   }

   public static XmlError forCursor(String message, int severity, XmlCursor cursor) {
      return new XmlError(message, (String)null, severity, cursor);
   }

   public static XmlError forCursor(String code, Object[] args, int severity, XmlCursor cursor) {
      return new XmlError(code, args, severity, cursor);
   }

   protected static String formattedFileName(String rawString, URI base) {
      if (rawString == null) {
         return null;
      } else {
         URI uri = null;

         try {
            uri = new URI(rawString);
            if (!uri.isAbsolute()) {
               uri = null;
            }
         } catch (URISyntaxException var4) {
            uri = null;
         }

         if (uri == null) {
            uri = (new File(rawString)).toURI();
         }

         if (base != null) {
            uri = base.relativize(uri);
         }

         if (uri.isAbsolute()) {
            if (uri.getScheme().compareToIgnoreCase("file") != 0) {
               return uri.toString();
            }
         } else if (base == null || !base.isAbsolute() || base.getScheme().compareToIgnoreCase("file") != 0) {
            return uri.toString();
         }

         try {
            return (new File(uri)).toString();
         } catch (Exception var5) {
            return uri.toString();
         }
      }
   }

   public static String formattedMessage(String code, Object[] args) {
      if (code == null) {
         return null;
      } else {
         try {
            String message = MessageFormat.format(_bundle.getString(code), args);
            return message;
         } catch (MissingResourceException var4) {
            return MessageFormat.format(_bundle.getString("message.missing.resource"), var4.getMessage());
         } catch (IllegalArgumentException var5) {
            return MessageFormat.format(_bundle.getString("message.pattern.invalid"), var5.getMessage());
         }
      }
   }

   public int getSeverity() {
      return this._severity;
   }

   public String getMessage() {
      return this._message;
   }

   public String getErrorCode() {
      return this._code;
   }

   public String getSourceName() {
      return this._source;
   }

   public int getLine() {
      return this._line;
   }

   public int getColumn() {
      return this._column;
   }

   public int getOffset() {
      return this._offset;
   }

   public Object getLocation(Object type) {
      if (type == XmlCursor.class) {
         return this._cursor;
      } else {
         return type == XmlObject.class && this._cursor != null ? this._cursor.getObject() : null;
      }
   }

   public XmlCursor getCursorLocation() {
      return (XmlCursor)this.getLocation(XmlCursor.class);
   }

   public XmlObject getObjectLocation() {
      return (XmlObject)this.getLocation(XmlObject.class);
   }

   public String toString() {
      return this.toString((URI)null);
   }

   public String toString(URI base) {
      StringBuffer sb = new StringBuffer();
      String source = formattedFileName(this.getSourceName(), base);
      if (source != null) {
         sb.append(source);
         int line = this.getLine();
         if (line < 0) {
            line = 0;
         }

         sb.append(':');
         sb.append(line);
         sb.append(':');
         if (this.getColumn() > 0) {
            sb.append(this.getColumn());
            sb.append(':');
         }

         sb.append(" ");
      }

      switch (this.getSeverity()) {
         case 0:
            sb.append("error: ");
            break;
         case 1:
            sb.append("warning: ");
         case 2:
      }

      if (this.getErrorCode() != null) {
         sb.append(this.getErrorCode()).append(": ");
      }

      String msg = this.getMessage();
      sb.append(msg == null ? "<Unspecified message>" : msg);
      return sb.toString();
   }

   public static String severityAsString(int severity) {
      switch (severity) {
         case 0:
            return "error";
         case 1:
            return "warning";
         case 2:
            return "info";
         default:
            throw new IllegalArgumentException("unknown severity");
      }
   }
}
