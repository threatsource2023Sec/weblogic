package org.glassfish.grizzly.http.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.Cookies;
import org.glassfish.grizzly.http.LazyCookieState;
import org.glassfish.grizzly.utils.Charsets;

public class CookieParserUtils {
   private static final Logger LOGGER = Grizzly.logger(CookieParserUtils.class);

   public static void parseClientCookies(Cookies cookies, Buffer buffer, int off, int len) {
      parseClientCookies(cookies, buffer, off, len, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED);
   }

   public static void parseClientCookies(Cookies cookies, Buffer buffer, int off, int len, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
      if (cookies == null) {
         throw new IllegalArgumentException("cookies cannot be null.");
      } else if (buffer == null) {
         throw new IllegalArgumentException("buffer cannot be null.");
      } else if (len > 0) {
         if (buffer.hasArray()) {
            parseClientCookies(cookies, buffer.array(), off + buffer.arrayOffset(), len, versionOneStrictCompliance, rfc6265Enabled);
         } else {
            int end = off + len;
            int pos = off;
            int version = 0;
            Cookie cookie = null;
            LazyCookieState lazyCookie = null;

            while(true) {
               while(pos < end) {
                  boolean isSpecial = false;

                  boolean isQuoted;
                  for(isQuoted = false; pos < end && (CookieUtils.isSeparator(buffer.get(pos)) || CookieUtils.isWhiteSpace(buffer.get(pos))); ++pos) {
                  }

                  if (pos >= end) {
                     return;
                  }

                  if (buffer.get(pos) == 36) {
                     isSpecial = true;
                     ++pos;
                  }

                  int nameStart = pos;

                  int nameEnd;
                  for(pos = nameEnd = CookieUtils.getTokenEndPosition(buffer, pos, end); pos < end && CookieUtils.isWhiteSpace(buffer.get(pos)); ++pos) {
                  }

                  int valueStart;
                  int valueEnd;
                  if (pos < end && buffer.get(pos) == 61) {
                     do {
                        ++pos;
                     } while(pos < end && CookieUtils.isWhiteSpace(buffer.get(pos)));

                     if (pos >= end) {
                        return;
                     }

                     switch (buffer.get(pos)) {
                        case 34:
                           isQuoted = true;
                           valueStart = pos + 1;
                           valueEnd = CookieUtils.getQuotedValueEndPosition(buffer, valueStart, end);
                           pos = valueEnd;
                           if (valueEnd >= end) {
                              return;
                           }
                           break;
                        case 44:
                        case 59:
                           valueEnd = -1;
                           valueStart = -1;
                           break;
                        default:
                           if (CookieUtils.isSeparator(buffer.get(pos), versionOneStrictCompliance)) {
                              LOGGER.fine("Invalid cookie. Value not a token or quoted value");

                              while(pos < end && buffer.get(pos) != 59 && buffer.get(pos) != 44) {
                                 ++pos;
                              }

                              ++pos;
                              cookie = null;
                              lazyCookie = null;
                              continue;
                           }

                           valueStart = pos;
                           valueEnd = CookieUtils.getTokenEndPosition(buffer, pos, end, versionOneStrictCompliance);
                           pos = valueEnd;
                     }
                  } else {
                     valueEnd = -1;
                     valueStart = -1;
                     pos = nameEnd;
                  }

                  while(pos < end && CookieUtils.isWhiteSpace(buffer.get(pos))) {
                     ++pos;
                  }

                  while(pos < end && buffer.get(pos) != 59 && buffer.get(pos) != 44) {
                     ++pos;
                  }

                  ++pos;
                  if (isSpecial) {
                     isSpecial = false;
                     if (CookieUtils.equals("Version", buffer, nameStart, nameEnd) && cookie == null) {
                        if (!rfc6265Enabled && buffer.get(valueStart) == 49 && valueEnd == valueStart + 1) {
                           version = 1;
                        }
                     } else if (cookie != null) {
                        if (CookieUtils.equals("Domain", buffer, nameStart, nameEnd)) {
                           lazyCookie.getDomain().setBuffer(buffer, valueStart, valueEnd);
                        } else if (CookieUtils.equals("Path", buffer, nameStart, nameEnd)) {
                           lazyCookie.getPath().setBuffer(buffer, valueStart, valueEnd);
                        } else {
                           LOGGER.fine("Unknown Special Cookie");
                        }
                     }
                  } else {
                     cookie = cookies.getNextUnusedCookie();
                     lazyCookie = cookie.getLazyCookieState();
                     if (!rfc6265Enabled && !cookie.isVersionSet()) {
                        cookie.setVersion(version);
                     }

                     lazyCookie.getName().setBuffer(buffer, nameStart, nameEnd);
                     if (valueStart != -1) {
                        lazyCookie.getValue().setBuffer(buffer, valueStart, valueEnd);
                        if (isQuoted) {
                           unescapeDoubleQuotes(lazyCookie.getValue());
                        }
                     } else {
                        lazyCookie.getValue().setString("");
                     }
                  }
               }

               return;
            }
         }
      }
   }

   public static void parseClientCookies(Cookies cookies, byte[] bytes, int off, int len) {
      parseClientCookies(cookies, bytes, off, len, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, false);
   }

   public static void parseClientCookies(Cookies cookies, byte[] bytes, int off, int len, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
      if (cookies == null) {
         throw new IllegalArgumentException("cookies cannot be null.");
      } else if (bytes == null) {
         throw new IllegalArgumentException("bytes cannot be null.");
      } else if (len > 0) {
         int end = off + len;
         int pos = off;
         int version = 0;
         Cookie cookie = null;
         LazyCookieState lazyCookie = null;

         while(true) {
            while(pos < end) {
               boolean isSpecial = false;

               boolean isQuoted;
               for(isQuoted = false; pos < end && (CookieUtils.isSeparator(bytes[pos]) || CookieUtils.isWhiteSpace(bytes[pos])); ++pos) {
               }

               if (pos >= end) {
                  return;
               }

               if (bytes[pos] == 36) {
                  isSpecial = true;
                  ++pos;
               }

               int nameStart = pos;

               int nameEnd;
               for(pos = nameEnd = CookieUtils.getTokenEndPosition(bytes, pos, end); pos < end && CookieUtils.isWhiteSpace(bytes[pos]); ++pos) {
               }

               int valueStart;
               int valueEnd;
               if (pos < end && bytes[pos] == 61) {
                  do {
                     ++pos;
                  } while(pos < end && CookieUtils.isWhiteSpace(bytes[pos]));

                  if (pos >= end) {
                     return;
                  }

                  switch (bytes[pos]) {
                     case 34:
                        isQuoted = true;
                        valueStart = pos + 1;
                        valueEnd = CookieUtils.getQuotedValueEndPosition(bytes, valueStart, end);
                        pos = valueEnd;
                        if (valueEnd >= end) {
                           return;
                        }
                        break;
                     case 44:
                     case 59:
                        valueEnd = -1;
                        valueStart = -1;
                        break;
                     default:
                        if (CookieUtils.isSeparator(bytes[pos], versionOneStrictCompliance)) {
                           LOGGER.fine("Invalid cookie. Value not a token or quoted value");

                           while(pos < end && bytes[pos] != 59 && bytes[pos] != 44) {
                              ++pos;
                           }

                           ++pos;
                           cookie = null;
                           lazyCookie = null;
                           continue;
                        }

                        valueStart = pos;
                        valueEnd = CookieUtils.getTokenEndPosition(bytes, pos, end, versionOneStrictCompliance);
                        pos = valueEnd;
                  }
               } else {
                  valueEnd = -1;
                  valueStart = -1;
                  pos = nameEnd;
               }

               while(pos < end && CookieUtils.isWhiteSpace(bytes[pos])) {
                  ++pos;
               }

               while(pos < end && bytes[pos] != 59 && bytes[pos] != 44) {
                  ++pos;
               }

               ++pos;
               if (isSpecial) {
                  isSpecial = false;
                  if (CookieUtils.equals("Version", bytes, nameStart, nameEnd) && cookie == null) {
                     if (!rfc6265Enabled && bytes[valueStart] == 49 && valueEnd == valueStart + 1) {
                        version = 1;
                     }
                  } else if (cookie != null) {
                     if (CookieUtils.equals("Domain", bytes, nameStart, nameEnd)) {
                        lazyCookie.getDomain().setBytes(bytes, valueStart, valueEnd);
                     } else if (CookieUtils.equals("Path", bytes, nameStart, nameEnd)) {
                        lazyCookie.getPath().setBytes(bytes, valueStart, valueEnd);
                     } else {
                        LOGGER.fine("Unknown Special Cookie");
                     }
                  }
               } else {
                  cookie = cookies.getNextUnusedCookie();
                  lazyCookie = cookie.getLazyCookieState();
                  if (!rfc6265Enabled && !cookie.isVersionSet()) {
                     cookie.setVersion(version);
                  }

                  lazyCookie.getName().setBytes(bytes, nameStart, nameEnd);
                  if (valueStart != -1) {
                     lazyCookie.getValue().setBytes(bytes, valueStart, valueEnd);
                     if (isQuoted) {
                        unescapeDoubleQuotes(lazyCookie.getValue());
                     }
                  } else {
                     lazyCookie.getValue().setString("");
                  }
               }
            }

            return;
         }
      }
   }

   public static void parseClientCookies(Cookies cookies, String cookiesStr, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
      if (cookies == null) {
         throw new IllegalArgumentException("cookies cannot be null.");
      } else if (cookiesStr == null) {
         throw new IllegalArgumentException("cookieStr cannot be null.");
      } else if (cookiesStr.length() != 0) {
         int end = cookiesStr.length();
         int pos = 0;
         int version = 0;
         Cookie cookie = null;

         while(true) {
            while(pos < end) {
               boolean isSpecial = false;

               boolean isQuoted;
               for(isQuoted = false; pos < end && (CookieUtils.isSeparator(cookiesStr.charAt(pos)) || CookieUtils.isWhiteSpace(cookiesStr.charAt(pos))); ++pos) {
               }

               if (pos >= end) {
                  return;
               }

               if (cookiesStr.charAt(pos) == '$') {
                  isSpecial = true;
                  ++pos;
               }

               int nameStart = pos;

               int nameEnd;
               for(pos = nameEnd = CookieUtils.getTokenEndPosition(cookiesStr, pos, end); pos < end && CookieUtils.isWhiteSpace(cookiesStr.charAt(pos)); ++pos) {
               }

               int valueStart;
               int valueEnd;
               if (pos < end && cookiesStr.charAt(pos) == '=') {
                  do {
                     ++pos;
                  } while(pos < end && CookieUtils.isWhiteSpace(cookiesStr.charAt(pos)));

                  if (pos >= end) {
                     return;
                  }

                  switch (cookiesStr.charAt(pos)) {
                     case '"':
                        isQuoted = true;
                        valueStart = pos + 1;
                        valueEnd = CookieUtils.getQuotedValueEndPosition(cookiesStr, valueStart, end);
                        pos = valueEnd;
                        if (valueEnd >= end) {
                           return;
                        }
                        break;
                     case ',':
                     case ';':
                        valueEnd = -1;
                        valueStart = -1;
                        break;
                     default:
                        if (CookieUtils.isSeparator(cookiesStr.charAt(pos), versionOneStrictCompliance)) {
                           LOGGER.fine("Invalid cookie. Value not a token or quoted value");

                           while(pos < end && cookiesStr.charAt(pos) != ';' && cookiesStr.charAt(pos) != ',') {
                              ++pos;
                           }

                           ++pos;
                           cookie = null;
                           continue;
                        }

                        valueStart = pos;
                        valueEnd = CookieUtils.getTokenEndPosition(cookiesStr, pos, end, versionOneStrictCompliance);
                        pos = valueEnd;
                  }
               } else {
                  valueEnd = -1;
                  valueStart = -1;
                  pos = nameEnd;
               }

               while(pos < end && CookieUtils.isWhiteSpace(cookiesStr.charAt(pos))) {
                  ++pos;
               }

               while(pos < end && cookiesStr.charAt(pos) != ';' && cookiesStr.charAt(pos) != ',') {
                  ++pos;
               }

               ++pos;
               if (isSpecial) {
                  isSpecial = false;
                  if (CookieUtils.equals("Version", cookiesStr, nameStart, nameEnd) && cookie == null) {
                     if (!rfc6265Enabled && cookiesStr.charAt(valueStart) == '1' && valueEnd == valueStart + 1) {
                        version = 1;
                     }
                  } else if (cookie != null) {
                     if (CookieUtils.equals("Domain", cookiesStr, nameStart, nameEnd)) {
                        cookie.setDomain(cookiesStr.substring(valueStart, valueEnd));
                     } else if (CookieUtils.equals("Path", cookiesStr, nameStart, nameEnd)) {
                        cookie.setPath(cookiesStr.substring(valueStart, valueEnd));
                     } else {
                        LOGGER.fine("Unknown Special Cookie");
                     }
                  }
               } else {
                  String name = cookiesStr.substring(nameStart, nameEnd);
                  String value;
                  if (valueStart != -1) {
                     if (isQuoted) {
                        value = unescapeDoubleQuotes(cookiesStr, valueStart, valueEnd - valueStart);
                     } else {
                        value = cookiesStr.substring(valueStart, valueEnd);
                     }
                  } else {
                     value = "";
                  }

                  cookie = cookies.getNextUnusedCookie();
                  cookie.setName(name);
                  cookie.setValue(value);
                  if (!rfc6265Enabled && !cookie.isVersionSet()) {
                     cookie.setVersion(version);
                  }
               }
            }

            return;
         }
      }
   }

   public static void parseServerCookies(Cookies cookies, byte[] bytes, int off, int len, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
      if (cookies == null) {
         throw new IllegalArgumentException("cookies cannot be null.");
      } else if (bytes == null) {
         throw new IllegalArgumentException("bytes cannot be null.");
      } else if (len > 0) {
         int end = off + len;
         int pos = off;
         Cookie cookie = null;
         LazyCookieState lazyCookie = null;

         while(true) {
            while(pos < end) {
               boolean isQuoted;
               for(isQuoted = false; pos < end && (CookieUtils.isSeparator(bytes[pos]) || CookieUtils.isWhiteSpace(bytes[pos])); ++pos) {
               }

               if (pos >= end) {
                  return;
               }

               int nameStart = pos;

               int nameEnd;
               for(pos = nameEnd = CookieUtils.getTokenEndPosition(bytes, pos, end); pos < end && CookieUtils.isWhiteSpace(bytes[pos]); ++pos) {
               }

               int valueStart;
               int valueEnd;
               if (pos < end && bytes[pos] == 61) {
                  do {
                     ++pos;
                  } while(pos < end && CookieUtils.isWhiteSpace(bytes[pos]));

                  if (pos >= end) {
                     return;
                  }

                  switch (bytes[pos]) {
                     case 34:
                        isQuoted = true;
                        valueStart = pos + 1;
                        valueEnd = CookieUtils.getQuotedValueEndPosition(bytes, valueStart, end);
                        pos = valueEnd;
                        if (valueEnd >= end) {
                           return;
                        }
                        break;
                     case 44:
                     case 59:
                        valueEnd = -1;
                        valueStart = -1;
                        break;
                     default:
                        if (CookieUtils.isSeparator(bytes[pos], versionOneStrictCompliance)) {
                           LOGGER.fine("Invalid cookie. Value not a token or quoted value");

                           while(pos < end && bytes[pos] != 59 && bytes[pos] != 44) {
                              ++pos;
                           }

                           ++pos;
                           cookie = null;
                           lazyCookie = null;
                           continue;
                        }

                        valueStart = pos;
                        valueEnd = CookieUtils.getTokenEndPosition(bytes, pos, end, versionOneStrictCompliance);
                        pos = valueEnd;
                  }
               } else {
                  valueEnd = -1;
                  valueStart = -1;
                  pos = nameEnd;
               }

               while(pos < end && CookieUtils.isWhiteSpace(bytes[pos])) {
                  ++pos;
               }

               while(pos < end && bytes[pos] != 59 && bytes[pos] != 44) {
                  ++pos;
               }

               ++pos;
               if (cookie != null) {
                  if (lazyCookie.getDomain().isNull() && CookieUtils.equalsIgnoreCase("Domain", bytes, nameStart, nameEnd)) {
                     lazyCookie.getDomain().setBytes(bytes, valueStart, valueEnd);
                     continue;
                  }

                  if (lazyCookie.getPath().isNull() && CookieUtils.equalsIgnoreCase("Path", bytes, nameStart, nameEnd)) {
                     lazyCookie.getPath().setBytes(bytes, valueStart, valueEnd);
                     continue;
                  }

                  if (CookieUtils.equals("Version", bytes, nameStart, nameEnd)) {
                     if (!rfc6265Enabled && bytes[valueStart] == 49 && valueEnd == valueStart + 1) {
                        cookie.setVersion(1);
                     }
                     continue;
                  }

                  if (lazyCookie.getComment().isNull() && CookieUtils.equals("Comment", bytes, nameStart, nameEnd)) {
                     lazyCookie.getComment().setBytes(bytes, valueStart, valueEnd);
                     continue;
                  }

                  if (cookie.getMaxAge() == -1 && CookieUtils.equals("Max-Age", bytes, nameStart, nameEnd)) {
                     cookie.setMaxAge(Ascii.parseInt(bytes, valueStart, valueEnd - valueStart));
                     continue;
                  }

                  if ((cookie.getVersion() == 0 || !cookie.isVersionSet()) && cookie.getMaxAge() == -1 && CookieUtils.equalsIgnoreCase("Expires", bytes, nameStart, nameEnd)) {
                     try {
                        valueEnd = CookieUtils.getTokenEndPosition(bytes, valueEnd + 1, end, false);
                        pos = valueEnd + 1;
                        String expiresDate = new String(bytes, valueStart, valueEnd - valueStart, Charsets.ASCII_CHARSET);
                        Date date = ((SimpleDateFormat)CookieUtils.OLD_COOKIE_FORMAT.get()).parse(expiresDate);
                        cookie.setMaxAge(getMaxAgeDelta(date.getTime(), System.currentTimeMillis()) / 1000);
                     } catch (ParseException var17) {
                     }
                     continue;
                  }

                  if (!cookie.isSecure() && CookieUtils.equalsIgnoreCase("Secure", bytes, nameStart, nameEnd)) {
                     lazyCookie.setSecure(true);
                     continue;
                  }

                  if (!cookie.isHttpOnly() && CookieUtils.equals("HttpOnly", bytes, nameStart, nameEnd)) {
                     cookie.setHttpOnly(true);
                     continue;
                  }

                  if (CookieUtils.equals("Discard", bytes, nameStart, nameEnd)) {
                     continue;
                  }
               }

               cookie = cookies.getNextUnusedCookie();
               if (!rfc6265Enabled && !cookie.isVersionSet()) {
                  cookie.setVersion(0);
               }

               lazyCookie = cookie.getLazyCookieState();
               lazyCookie.getName().setBytes(bytes, nameStart, nameEnd);
               if (valueStart != -1) {
                  lazyCookie.getValue().setBytes(bytes, valueStart, valueEnd);
                  if (isQuoted) {
                     unescapeDoubleQuotes(lazyCookie.getValue());
                  }
               } else {
                  lazyCookie.getValue().setString("");
               }
            }

            return;
         }
      }
   }

   public static void parseServerCookies(Cookies cookies, Buffer buffer, int off, int len, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
      if (cookies == null) {
         throw new IllegalArgumentException("cookies cannot be null.");
      } else if (buffer == null) {
         throw new IllegalArgumentException("buffer cannot be null.");
      } else if (len > 0) {
         if (buffer.hasArray()) {
            parseServerCookies(cookies, buffer.array(), off + buffer.arrayOffset(), len, versionOneStrictCompliance, rfc6265Enabled);
         } else {
            int end = off + len;
            int pos = off;
            Cookie cookie = null;
            LazyCookieState lazyCookie = null;

            while(true) {
               while(pos < end) {
                  boolean isQuoted;
                  for(isQuoted = false; pos < end && (CookieUtils.isSeparator(buffer.get(pos)) || CookieUtils.isWhiteSpace(buffer.get(pos))); ++pos) {
                  }

                  if (pos >= end) {
                     return;
                  }

                  int nameStart = pos;

                  int nameEnd;
                  for(pos = nameEnd = CookieUtils.getTokenEndPosition(buffer, pos, end); pos < end && CookieUtils.isWhiteSpace(buffer.get(pos)); ++pos) {
                  }

                  int valueStart;
                  int valueEnd;
                  if (pos < end && buffer.get(pos) == 61) {
                     do {
                        ++pos;
                     } while(pos < end && CookieUtils.isWhiteSpace(buffer.get(pos)));

                     if (pos >= end) {
                        return;
                     }

                     switch (buffer.get(pos)) {
                        case 34:
                           isQuoted = true;
                           valueStart = pos + 1;
                           valueEnd = CookieUtils.getQuotedValueEndPosition(buffer, valueStart, end);
                           pos = valueEnd;
                           if (valueEnd >= end) {
                              return;
                           }
                           break;
                        case 44:
                        case 59:
                           valueEnd = -1;
                           valueStart = -1;
                           break;
                        default:
                           if (CookieUtils.isSeparator(buffer.get(pos), versionOneStrictCompliance)) {
                              LOGGER.fine("Invalid cookie. Value not a token or quoted value");

                              while(pos < end && buffer.get(pos) != 59 && buffer.get(pos) != 44) {
                                 ++pos;
                              }

                              ++pos;
                              cookie = null;
                              lazyCookie = null;
                              continue;
                           }

                           valueStart = pos;
                           valueEnd = CookieUtils.getTokenEndPosition(buffer, pos, end, versionOneStrictCompliance);
                           pos = valueEnd;
                     }
                  } else {
                     valueEnd = -1;
                     valueStart = -1;
                     pos = nameEnd;
                  }

                  while(pos < end && CookieUtils.isWhiteSpace(buffer.get(pos))) {
                     ++pos;
                  }

                  while(pos < end && buffer.get(pos) != 59 && buffer.get(pos) != 44) {
                     ++pos;
                  }

                  ++pos;
                  if (cookie != null) {
                     if (lazyCookie.getDomain().isNull() && CookieUtils.equalsIgnoreCase("Domain", buffer, nameStart, nameEnd)) {
                        lazyCookie.getDomain().setBuffer(buffer, valueStart, valueEnd);
                        continue;
                     }

                     if (lazyCookie.getPath().isNull() && CookieUtils.equalsIgnoreCase("Path", buffer, nameStart, nameEnd)) {
                        lazyCookie.getPath().setBuffer(buffer, valueStart, valueEnd);
                        continue;
                     }

                     if (CookieUtils.equals("Version", buffer, nameStart, nameEnd)) {
                        if (!rfc6265Enabled && buffer.get(valueStart) == 49 && valueEnd == valueStart + 1) {
                           cookie.setVersion(1);
                        }
                        continue;
                     }

                     if (lazyCookie.getComment().isNull() && CookieUtils.equals("Comment", buffer, nameStart, nameEnd)) {
                        lazyCookie.getComment().setBuffer(buffer, valueStart, valueEnd);
                        continue;
                     }

                     if (cookie.getMaxAge() == -1 && CookieUtils.equals("Max-Age", buffer, nameStart, nameEnd)) {
                        cookie.setMaxAge(Ascii.parseInt(buffer, valueStart, valueEnd - valueStart));
                        continue;
                     }

                     if ((cookie.getVersion() == 0 || !cookie.isVersionSet()) && cookie.getMaxAge() == -1 && CookieUtils.equalsIgnoreCase("Expires", buffer, nameStart, nameEnd)) {
                        try {
                           valueEnd = CookieUtils.getTokenEndPosition(buffer, valueEnd + 1, end, false);
                           pos = valueEnd + 1;
                           String expiresDate = buffer.toStringContent(Charsets.ASCII_CHARSET, valueStart, valueEnd);
                           Date date = ((SimpleDateFormat)CookieUtils.OLD_COOKIE_FORMAT.get()).parse(expiresDate);
                           cookie.setMaxAge(getMaxAgeDelta(date.getTime(), System.currentTimeMillis()) / 1000);
                        } catch (ParseException var17) {
                        }
                        continue;
                     }

                     if (!cookie.isSecure() && CookieUtils.equalsIgnoreCase("Secure", buffer, nameStart, nameEnd)) {
                        lazyCookie.setSecure(true);
                        continue;
                     }

                     if (!cookie.isHttpOnly() && CookieUtils.equals("HttpOnly", buffer, nameStart, nameEnd)) {
                        cookie.setHttpOnly(true);
                        continue;
                     }

                     if (CookieUtils.equals("Discard", buffer, nameStart, nameEnd)) {
                        continue;
                     }
                  }

                  cookie = cookies.getNextUnusedCookie();
                  if (!rfc6265Enabled && !cookie.isVersionSet()) {
                     cookie.setVersion(0);
                  }

                  lazyCookie = cookie.getLazyCookieState();
                  lazyCookie.getName().setBuffer(buffer, nameStart, nameEnd);
                  if (valueStart != -1) {
                     lazyCookie.getValue().setBuffer(buffer, valueStart, valueEnd);
                     if (isQuoted) {
                        unescapeDoubleQuotes(lazyCookie.getValue());
                     }
                  } else {
                     lazyCookie.getValue().setString("");
                  }
               }

               return;
            }
         }
      }
   }

   public static void parseServerCookies(Cookies cookies, String cookiesStr, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
      if (cookies == null) {
         throw new IllegalArgumentException("cookies cannot be null.");
      } else if (cookiesStr == null) {
         throw new IllegalArgumentException();
      } else if (cookiesStr.length() != 0) {
         int end = cookiesStr.length();
         int pos = 0;
         Cookie cookie = null;

         while(true) {
            while(pos < end) {
               boolean isQuoted;
               for(isQuoted = false; pos < end && (CookieUtils.isSeparator(cookiesStr.charAt(pos)) || CookieUtils.isWhiteSpace(cookiesStr.charAt(pos))); ++pos) {
               }

               if (pos >= end) {
                  return;
               }

               int nameStart = pos;

               int nameEnd;
               for(pos = nameEnd = CookieUtils.getTokenEndPosition(cookiesStr, pos, end); pos < end && CookieUtils.isWhiteSpace(cookiesStr.charAt(pos)); ++pos) {
               }

               int valueStart;
               int valueEnd;
               if (pos < end && cookiesStr.charAt(pos) == '=') {
                  do {
                     ++pos;
                  } while(pos < end && CookieUtils.isWhiteSpace(cookiesStr.charAt(pos)));

                  if (pos >= end) {
                     return;
                  }

                  switch (cookiesStr.charAt(pos)) {
                     case '"':
                        isQuoted = true;
                        valueStart = pos + 1;
                        valueEnd = CookieUtils.getQuotedValueEndPosition(cookiesStr, valueStart, end);
                        pos = valueEnd;
                        if (valueEnd >= end) {
                           return;
                        }
                        break;
                     case ',':
                     case ';':
                        valueEnd = -1;
                        valueStart = -1;
                        break;
                     default:
                        if (CookieUtils.isSeparator(cookiesStr.charAt(pos), versionOneStrictCompliance)) {
                           LOGGER.fine("Invalid cookie. Value not a token or quoted value");

                           while(pos < end && cookiesStr.charAt(pos) != ';' && cookiesStr.charAt(pos) != ',') {
                              ++pos;
                           }

                           ++pos;
                           cookie = null;
                           continue;
                        }

                        valueStart = pos;
                        valueEnd = CookieUtils.getTokenEndPosition(cookiesStr, pos, end, versionOneStrictCompliance);
                        pos = valueEnd;
                  }
               } else {
                  valueEnd = -1;
                  valueStart = -1;
                  pos = nameEnd;
               }

               while(pos < end && CookieUtils.isWhiteSpace(cookiesStr.charAt(pos))) {
                  ++pos;
               }

               while(pos < end && cookiesStr.charAt(pos) != ';' && cookiesStr.charAt(pos) != ',') {
                  ++pos;
               }

               ++pos;
               String expiresDate;
               if (cookie != null) {
                  if (cookie.getDomain() == null && CookieUtils.equalsIgnoreCase("Domain", cookiesStr, nameStart, nameEnd)) {
                     cookie.setDomain(cookiesStr.substring(valueStart, valueEnd));
                     continue;
                  }

                  if (cookie.getPath() == null && CookieUtils.equalsIgnoreCase("Path", cookiesStr, nameStart, nameEnd)) {
                     cookie.setPath(cookiesStr.substring(valueStart, valueEnd));
                     continue;
                  }

                  if (CookieUtils.equals("Version", cookiesStr, nameStart, nameEnd)) {
                     if (rfc6265Enabled) {
                        continue;
                     }

                     if (cookiesStr.charAt(valueStart) == '1' && valueEnd == valueStart + 1) {
                        cookie.setVersion(1);
                        continue;
                     }

                     if (!rfc6265Enabled) {
                        cookie.setVersion(0);
                     }
                     continue;
                  }

                  if (cookie.getComment() == null && CookieUtils.equals("Comment", cookiesStr, nameStart, nameEnd)) {
                     cookie.setComment(cookiesStr.substring(valueStart, valueEnd));
                     continue;
                  }

                  if (cookie.getMaxAge() == -1 && CookieUtils.equals("Max-Age", cookiesStr, nameStart, nameEnd)) {
                     cookie.setMaxAge(Integer.parseInt(cookiesStr.substring(valueStart, valueEnd)));
                     continue;
                  }

                  if ((cookie.getVersion() == 0 || cookie.isVersionSet()) && cookie.getMaxAge() == -1 && CookieUtils.equalsIgnoreCase("Expires", cookiesStr, nameStart, nameEnd)) {
                     try {
                        valueEnd = CookieUtils.getTokenEndPosition(cookiesStr, valueEnd + 1, end, false);
                        pos = valueEnd + 1;
                        expiresDate = cookiesStr.substring(valueStart, valueEnd);
                        Date date = ((SimpleDateFormat)CookieUtils.OLD_COOKIE_FORMAT.get()).parse(expiresDate);
                        cookie.setMaxAge(getMaxAgeDelta(date.getTime(), System.currentTimeMillis()) / 1000);
                     } catch (ParseException var14) {
                     }
                     continue;
                  }

                  if (!cookie.isSecure() && CookieUtils.equalsIgnoreCase("Secure", cookiesStr, nameStart, nameEnd)) {
                     cookie.setSecure(true);
                     continue;
                  }

                  if (!cookie.isHttpOnly() && CookieUtils.equals("HttpOnly", cookiesStr, nameStart, nameEnd)) {
                     cookie.setHttpOnly(true);
                     continue;
                  }

                  if (CookieUtils.equals("Discard", cookiesStr, nameStart, nameEnd)) {
                     continue;
                  }
               }

               expiresDate = cookiesStr.substring(nameStart, nameEnd);
               String value;
               if (valueStart != -1) {
                  if (isQuoted) {
                     value = unescapeDoubleQuotes(cookiesStr, valueStart, valueEnd - valueStart);
                  } else {
                     value = cookiesStr.substring(valueStart, valueEnd);
                  }
               } else {
                  value = "";
               }

               cookie = cookies.getNextUnusedCookie();
               if (!rfc6265Enabled && !cookie.isVersionSet()) {
                  cookie.setVersion(0);
               }

               cookie.setName(expiresDate);
               cookie.setValue(value);
            }

            return;
         }
      }
   }

   public static void unescapeDoubleQuotes(DataChunk dc) {
      switch (dc.getType()) {
         case Bytes:
            unescapeDoubleQuotes(dc.getByteChunk());
            return;
         case Buffer:
            unescapeDoubleQuotes(dc.getBufferChunk());
            return;
         case String:
            String s = dc.toString();
            dc.setString(unescapeDoubleQuotes((String)s, 0, s.length()));
            return;
         case Chars:
         default:
            throw new NullPointerException();
      }
   }

   public static void unescapeDoubleQuotes(ByteChunk bc) {
      if (bc != null && bc.getLength() != 0) {
         int src = bc.getStart();
         int end = bc.getEnd();
         int dest = src;

         for(byte[] buffer = bc.getBuffer(); src < end; ++src) {
            if (buffer[src] == 92 && src < end && buffer[src + 1] == 34) {
               ++src;
            }

            buffer[dest] = buffer[src];
            ++dest;
         }

         bc.setEnd(dest);
      }
   }

   public static void unescapeDoubleQuotes(BufferChunk bc) {
      if (bc != null && bc.getLength() != 0) {
         int src = bc.getStart();
         int end = bc.getEnd();
         int dest = src;

         for(Buffer buffer = bc.getBuffer(); src < end; ++src) {
            if (buffer.get(src) == 92 && src < end && buffer.get(src + 1) == 34) {
               ++src;
            }

            buffer.put(dest, buffer.get(src));
            ++dest;
         }

         bc.setEnd(dest);
      }
   }

   public static void unescapeDoubleQuotes(CharChunk cc) {
      if (cc != null && cc.getLength() != 0) {
         int src = cc.getStart();
         int end = cc.getLimit();
         int dest = src;

         for(char[] buffer = cc.getBuffer(); src < end; ++src) {
            if (buffer[src] == '\\' && src < end && buffer[src + 1] == '"') {
               ++src;
            }

            buffer[dest] = buffer[src];
            ++dest;
         }

         cc.setLimit(dest);
      }
   }

   public static int unescapeDoubleQuotes(Buffer buffer, int start, int length) {
      if (buffer != null && length > 0) {
         int src = start;
         int end = start + length;

         int dest;
         for(dest = start; src < end; ++src) {
            if (buffer.get(src) == 92 && src < end && buffer.get(src + 1) == 34) {
               ++src;
            }

            buffer.put(dest, buffer.get(src));
            ++dest;
         }

         return dest - start;
      } else {
         return length;
      }
   }

   public static String unescapeDoubleQuotes(String s, int start, int length) {
      if (s != null && s.length() != 0) {
         StringBuilder sb = new StringBuilder(s.length());
         int src = start;

         for(int end = start + length; src < end; ++src) {
            if (s.charAt(src) == '\\' && src < end && s.charAt(src + 1) == '"') {
               ++src;
            }

            sb.append(s.charAt(src));
         }

         return sb.toString();
      } else {
         return s;
      }
   }

   private static int getMaxAgeDelta(long date1, long date2) {
      long result = date1 - date2;
      if (result > 2147483647L) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("Integer overflow when calculating max age delta.  Date: " + date1 + ", current date: " + date2 + ".  Using Integer.MAX_VALUE for further calculation.");
         }

         return Integer.MAX_VALUE;
      } else {
         return (int)result;
      }
   }
}
