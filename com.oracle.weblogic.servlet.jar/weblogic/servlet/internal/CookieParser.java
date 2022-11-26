package weblogic.servlet.internal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.http.Cookie;
import weblogic.utils.http.HttpParsing;

public final class CookieParser {
   private String path;
   private String comment;
   private String domain;
   private boolean secure;
   private int version = 0;
   private int maxAge = -1;
   private final char[] buf;
   private final int len;
   private final String bufAsString;
   private final List cookies = new ArrayList();
   private static boolean allowCommasInNetscapeCookie;
   private static boolean stripQuotedValues;
   private static final SimpleDateFormat[] expiresFormats;

   public static Iterator parseCookies(String headerLine) throws MalformedCookieHeaderException {
      CookieParser cookieParser = new CookieParser(headerLine);
      cookieParser.parse();
      return cookieParser.cookies.iterator();
   }

   private CookieParser(String headerLine) {
      String headerString = headerLine.trim();
      this.bufAsString = headerString.toLowerCase();
      this.buf = headerString.toCharArray();
      this.len = this.buf.length;
   }

   private void parse() throws MalformedCookieHeaderException {
      this.getVersion();
      if (this.version == 0) {
         this.parseNetscapeCookie();
      } else {
         this.parseRFC2109Cookie();
      }

   }

   private void parseNetscapeCookie() throws MalformedCookieHeaderException {
      this.path = this.getAttribute("path");
      this.domain = this.getAttribute("domain");
      if (this.indexAttributeName("secure") != -1) {
         this.secure = true;
      }

      this.getExpires();
      String malformedcookie = null;
      String malformedname = null;
      char malformedchar = 0;
      int malformedpos = 0;
      int namepos = 0;
      int valpos = false;

      while(true) {
         while((namepos = this.nextNonWS(namepos)) != -1) {
            int semipos = this.nextIndex(';', namepos);
            int endpos;
            String name;
            int valpos;
            if ((valpos = this.nextIndex('=', namepos)) == -1) {
               endpos = semipos > 0 ? semipos : this.len;
               name = new String(this.buf, namepos, endpos - namepos);
               namepos += name.length() + 1;
               name = name.trim();
               this.addCookie(name, "");
            } else if (semipos != -1 && valpos > semipos) {
               String name = new String(this.buf, namepos, semipos - namepos);
               namepos += name.length() + 1;
               name = name.trim();
               this.addCookie(name, "");
            } else {
               if (allowCommasInNetscapeCookie) {
                  endpos = this.nextIndex(';', ',', valpos);
               } else {
                  endpos = this.nextIndex(';', valpos);
               }

               if (endpos == -1) {
                  endpos = this.len;
               }

               name = new String(this.buf, namepos, valpos - namepos);

               for(int i = namepos; i < valpos; ++i) {
                  if (HttpParsing.isNetscapeSpecial(this.buf[i])) {
                     malformedcookie = new String(this.buf);
                     malformedname = name;
                     malformedchar = this.buf[i];
                     malformedpos = i;
                  }
               }

               ++valpos;
               boolean quotedstr = false;
               String val = new String(this.buf, valpos, endpos - valpos);
               if (valpos < this.len && this.buf[valpos] == '"' && endpos - 1 > valpos && this.buf[endpos - 1] == '"') {
                  quotedstr = true;
               }

               if (!name.equalsIgnoreCase("expires") && !quotedstr) {
                  for(int i = valpos; i < endpos; ++i) {
                     if (HttpParsing.isNetscapeSpecial(this.buf[i])) {
                        malformedcookie = new String(this.buf);
                        malformedname = name;
                        malformedchar = this.buf[i];
                        malformedpos = i;
                     }
                  }
               }

               if (name != malformedname) {
                  this.addCookie(name, this.unquote(val));
               }

               for(namepos = endpos + 1; namepos < this.len && HttpParsing.isSpace(this.buf[namepos]); ++namepos) {
               }
            }
         }

         if (malformedcookie != null) {
            throw new MalformedCookieHeaderException("In cookie '" + malformedcookie + "' character '" + malformedchar + "' at position " + malformedpos + " is illegal");
         }

         return;
      }
   }

   private void getExpires() throws MalformedCookieHeaderException {
      String expstr = this.getAttribute("Expires", true);
      if (expstr != null) {
         try {
            Date expGMTDate = null;
            int i = 0;

            label56:
            while(i < expiresFormats.length) {
               try {
                  int retry = 0;

                  while(true) {
                     if (retry >= 3) {
                        break label56;
                     }

                     try {
                        synchronized(expiresFormats[i]) {
                           expGMTDate = expiresFormats[i].parse(expstr);
                           break label56;
                        }
                     } catch (ParseException var8) {
                        throw var8;
                     } catch (Exception var9) {
                        ++retry;
                     }
                  }
               } catch (ParseException var10) {
                  if (i == expiresFormats.length - 1) {
                     throw var10;
                  }

                  ++i;
               }
            }

            if (expGMTDate == null) {
               throw new ParseException("Null date returned from parser", 0);
            } else {
               long expGMTTime = expGMTDate.getTime();
               long nowTime = (new Date()).getTime();
               if (nowTime < expGMTTime) {
                  this.maxAge = (int)((expGMTTime - nowTime) / 1000L);
               } else {
                  this.maxAge = -1;
               }

            }
         } catch (ParseException var11) {
            throw new MalformedCookieHeaderException("Expires header '" + expstr + "' in cookie '" + new String(this.buf) + "' is invalid, nested " + var11.toString());
         }
      }
   }

   private void parseRFC2109Cookie() throws MalformedCookieHeaderException {
      this.comment = this.getAttribute("$Comment");
      this.path = this.getAttribute("$Path");
      this.domain = this.getAttribute("$Domain");
      if (this.indexAttributeName("$Secure") != -1 || this.indexAttributeName("Secure") != -1) {
         this.secure = true;
      }

      this.getMaxAge();
      int namepos = 0;
      MalformedCookieHeaderException badcookie_e = null;
      boolean goodcookie = true;

      while((namepos = this.nextNonWS(namepos)) != -1) {
         String name = null;
         String val = null;
         int eqpos = this.nextIndex('=', namepos);
         int semipos = this.nextIndex(';', ',', namepos);
         if (eqpos >= 0 && (semipos == -1 || semipos >= eqpos)) {
            name = new String(this.buf, namepos, eqpos - namepos);

            try {
               ++eqpos;
               val = this.getValueString(name, eqpos, true);
            } catch (MalformedCookieHeaderException var9) {
               badcookie_e = var9;
               goodcookie = false;
            }

            namepos += name.length();
            namepos += 2;
            if (val != null) {
               namepos += val.length();
            }

            if (eqpos < this.len && this.buf[eqpos] == '"') {
               namepos += 2;
            }

            name = name.trim();
         } else {
            int endpos;
            if (semipos != -1 && semipos < eqpos) {
               endpos = semipos;
            } else if (eqpos >= 0) {
               endpos = eqpos;
            } else {
               endpos = this.len;
            }

            name = (new String(this.buf, namepos, endpos - namepos)).trim();
            namepos = endpos + 1;
         }

         if (!name.equalsIgnoreCase("$Version") && !name.equalsIgnoreCase("$Path") && !name.equalsIgnoreCase("$Domain")) {
            if (goodcookie) {
               this.addCookie(name, val);
            }

            goodcookie = true;
         }
      }

      if (badcookie_e != null) {
         throw badcookie_e;
      }
   }

   private void getMaxAge() throws MalformedCookieHeaderException {
      String s = this.getAttribute("Max-Age");
      if (s != null) {
         try {
            this.maxAge = Integer.parseInt(s);
         } catch (NumberFormatException var3) {
            throw new MalformedCookieHeaderException("Max-Age header '" + s + "' in cookie '" + new String(this.buf) + "' is invalid, nested " + var3);
         }
      }

   }

   private String getAttribute(String name) throws MalformedCookieHeaderException {
      return this.getAttribute(name, false);
   }

   private String getAttribute(String name, boolean wsOK) throws MalformedCookieHeaderException {
      int indname = this.indexAttributeName(name);
      if (indname == -1) {
         return null;
      } else {
         indname += name.length();
         int indeq = this.nextIndex('=', indname);
         int indsemi;
         if (this.version > 0) {
            indsemi = this.nextIndex(';', ',', indname);
         } else {
            indsemi = this.nextIndex(';', indname);
         }

         if (indeq >= 0 && (indsemi == -1 || indsemi >= indeq)) {
            ++indeq;
            return this.getValueString(name, indeq, wsOK);
         } else {
            return null;
         }
      }
   }

   private String getValueString(String name, int ind, boolean wsOK) throws MalformedCookieHeaderException {
      if (ind >= this.len) {
         return "";
      } else {
         int endindex;
         if (this.version > 0 && this.buf[ind] == '"') {
            ++ind;
            if ((endindex = this.nextIndex('"', ind)) == -1) {
               throw new MalformedCookieHeaderException("In cookie '" + new String(this.buf) + "' quote at position " + (ind - 1) + " is unbalanced");
            } else {
               return new String(this.buf, ind, endindex - ind);
            }
         } else {
            if (allowCommasInNetscapeCookie) {
               endindex = this.nextIndex(';', ',', ind);
            } else {
               endindex = this.nextIndex(';', ind);
            }

            if (endindex == -1) {
               endindex = this.len;
            }

            if (this.version == 0 && !wsOK) {
               for(int i = ind; i < endindex; ++i) {
                  if (!"expires".equalsIgnoreCase(name) && HttpParsing.isWS(this.buf[i])) {
                     throw new MalformedCookieHeaderException("In cookie '" + new String(this.buf) + "' the value of '" + name + "' may not contain white-space");
                  }
               }
            }

            return new String(this.buf, ind, endindex - ind);
         }
      }
   }

   private int nextNonWS(int ind) {
      while(ind < this.len) {
         if (!HttpParsing.isWS(this.buf[ind])) {
            return ind;
         }

         ++ind;
      }

      return -1;
   }

   private int nextIndex(char c, int start) {
      for(int i = start; i < this.len; ++i) {
         if (c == this.buf[i]) {
            return i;
         }
      }

      return -1;
   }

   private int nextIndex(char c1, char c2, int start) {
      for(int i = start; i < this.len; ++i) {
         if (c1 == this.buf[i] || c2 == this.buf[i]) {
            return i;
         }
      }

      return -1;
   }

   private int indexString(int off, String s) {
      return this.bufAsString.indexOf(s.toLowerCase(), off);
   }

   private int indexAttributeName(String s) {
      int ind = -1;

      while(true) {
         do {
            ++ind;
            if ((ind = this.indexString(ind, s)) == -1) {
               return -1;
            }
         } while(ind > 0 && !HttpParsing.isWS(this.buf[ind - 1]));

         if (ind + s.length() == this.len) {
            return ind;
         }

         for(int i = ind + s.length(); i < this.len; ++i) {
            if (this.version > 0) {
               if (this.buf[i] == '=' || this.buf[i] == ';' || this.buf[i] == ',') {
                  return ind;
               }
            } else if (this.buf[i] == '=' || this.buf[i] == ';') {
               return ind;
            }

            if (!HttpParsing.isWS(this.buf[i])) {
               break;
            }
         }
      }
   }

   private void addCookie(String name, String val) {
      try {
         Cookie c = new Cookie(name, val);
         c.setVersion(this.version);
         c.setSecure(this.secure);
         if (this.path != null) {
            c.setPath(this.path);
         }

         if (this.comment != null) {
            c.setComment(this.comment);
         }

         if (this.domain != null) {
            c.setDomain(this.domain);
         }

         c.setMaxAge(this.maxAge);
         this.cookies.add(c);
      } catch (IllegalArgumentException var4) {
      }

   }

   public static String formatCookie(Cookie cookie, boolean httpOnly) {
      StringBuilder sb = new StringBuilder(85);
      sb.append(cookie.getName());
      switch (cookie.getVersion()) {
         case 0:
            formatNetscapeCookie(sb, cookie);
            break;
         case 1:
         default:
            formatRFC2109Cookie(cookie, sb);
      }

      if (httpOnly) {
         sb.append("; HttpOnly");
      }

      return sb.toString();
   }

   private static void formatNetscapeCookie(StringBuilder sb, Cookie cookie) {
      sb.append('=');
      sb.append(cookie.getValue());
      String domain = cookie.getDomain();
      if (domain != null) {
         sb.append("; domain=").append(domain);
      }

      int maxAge = cookie.getMaxAge();
      if (maxAge >= 0) {
         Date d;
         if (maxAge == 0) {
            d = new Date(3600000L);
         } else {
            long longMaxAge = (long)maxAge * 1000L;
            d = new Date(System.currentTimeMillis() + longMaxAge);
         }

         int retry = 0;

         while(retry < 3) {
            try {
               synchronized(expiresFormats[0]) {
                  sb.append("; expires=").append(expiresFormats[0].format(d));
                  break;
               }
            } catch (Exception var9) {
               ++retry;
            }
         }
      }

      String path = cookie.getPath();
      if (path != null) {
         sb.append("; path=").append(path);
      }

      if (cookie.getSecure()) {
         sb.append("; secure");
      }

   }

   private static void formatRFC2109Cookie(Cookie cookie, StringBuilder sb) {
      String v = cookie.getValue();
      if (v != null) {
         sb.append('=');
         appendValue(sb, v);
      }

      String comment = cookie.getComment();
      if (comment != null) {
         appendValue(sb.append("; Comment="), comment);
      }

      sb.append("; Version=1");
      String domain = cookie.getDomain();
      if (domain != null) {
         appendValue(sb.append("; Domain="), domain);
      }

      String path = cookie.getPath();
      if (path != null) {
         if (stripQuotedValues) {
            sb.append("; Path=").append(path);
         } else {
            appendValue(sb.append("; Path="), path);
         }
      }

      int maxAge = cookie.getMaxAge();
      if (maxAge >= 0) {
         sb.append("; Max-Age=").append(maxAge);
      }

      if (cookie.getSecure()) {
         sb.append("; Secure");
      }

   }

   private void getVersion() {
      this.version = 0;
      int verdex = this.indexAttributeName("$Version");
      if (verdex != -1) {
         int i = this.nextIndex('=', verdex);
         if (i != -1 && i != this.len - 1) {
            ++i;

            try {
               while(HttpParsing.isWS(this.buf[i])) {
                  ++i;
                  if (i == this.len) {
                     return;
                  }
               }

               if (this.buf[i] == '"') {
                  ++i;
                  if (i == this.len) {
                     return;
                  }
               }
            } catch (ArrayIndexOutOfBoundsException var6) {
               return;
            }

            int start;
            for(start = i; i < this.len && HttpParsing.isDigit(this.buf[i]); ++i) {
            }

            if (i != start) {
               if (i - start == 1) {
                  this.version = this.buf[start] - 48;
               } else {
                  try {
                     this.version = Integer.parseInt(new String(this.buf, start, i - start));
                  } catch (Exception var5) {
                  }

               }
            }
         }
      }
   }

   private static void appendValue(StringBuilder sb, String value) {
      if (!HttpParsing.isTokenClean(value) && !HttpParsing.isQuoted(value)) {
         sb.append("\"").append(value).append("\"");
      } else {
         sb.append(value);
      }

   }

   private String unquote(String val) {
      return HttpParsing.isQuoted(val) && stripQuotedValues ? val.trim().substring(1, val.length() - 1) : val;
   }

   static {
      String allowComma = System.getProperty("weblogic.allowCommasInNetscapeCookie");
      if (allowComma != null && "true".equalsIgnoreCase(allowComma)) {
         allowCommasInNetscapeCookie = true;
      }

      stripQuotedValues = Boolean.getBoolean("weblogic.cookies.stripQuotedValues");
      expiresFormats = new SimpleDateFormat[]{new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss zz", Locale.US), new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zz", Locale.US), new SimpleDateFormat("EEEE, dd-MMM-yyyy HH:mm:ss zz", Locale.US), new SimpleDateFormat("EEEE, dd MMM yyyy HH:mm:ss zz", Locale.US)};

      for(int i = 0; i < expiresFormats.length; ++i) {
         expiresFormats[i].setTimeZone(TimeZone.getTimeZone("GMT"));
      }

   }
}
