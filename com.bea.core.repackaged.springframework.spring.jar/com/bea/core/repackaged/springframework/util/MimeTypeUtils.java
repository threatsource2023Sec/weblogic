package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class MimeTypeUtils {
   private static final byte[] BOUNDARY_CHARS = new byte[]{45, 95, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90};
   public static final Comparator SPECIFICITY_COMPARATOR = new MimeType.SpecificityComparator();
   public static final MimeType ALL = MimeType.valueOf("*/*");
   public static final String ALL_VALUE = "*/*";
   public static final MimeType APPLICATION_JSON = MimeType.valueOf("application/json");
   public static final String APPLICATION_JSON_VALUE = "application/json";
   public static final MimeType APPLICATION_OCTET_STREAM = MimeType.valueOf("application/octet-stream");
   public static final String APPLICATION_OCTET_STREAM_VALUE = "application/octet-stream";
   public static final MimeType APPLICATION_XML = MimeType.valueOf("application/xml");
   public static final String APPLICATION_XML_VALUE = "application/xml";
   public static final MimeType IMAGE_GIF = MimeType.valueOf("image/gif");
   public static final String IMAGE_GIF_VALUE = "image/gif";
   public static final MimeType IMAGE_JPEG = MimeType.valueOf("image/jpeg");
   public static final String IMAGE_JPEG_VALUE = "image/jpeg";
   public static final MimeType IMAGE_PNG = MimeType.valueOf("image/png");
   public static final String IMAGE_PNG_VALUE = "image/png";
   public static final MimeType TEXT_HTML = MimeType.valueOf("text/html");
   public static final String TEXT_HTML_VALUE = "text/html";
   public static final MimeType TEXT_PLAIN = MimeType.valueOf("text/plain");
   public static final String TEXT_PLAIN_VALUE = "text/plain";
   public static final MimeType TEXT_XML = MimeType.valueOf("text/xml");
   public static final String TEXT_XML_VALUE = "text/xml";
   @Nullable
   private static volatile Random random;

   public static MimeType parseMimeType(String mimeType) {
      if (!StringUtils.hasLength(mimeType)) {
         throw new InvalidMimeTypeException(mimeType, "'mimeType' must not be empty");
      } else {
         int index = mimeType.indexOf(59);
         String fullType = (index >= 0 ? mimeType.substring(0, index) : mimeType).trim();
         if (fullType.isEmpty()) {
            throw new InvalidMimeTypeException(mimeType, "'mimeType' must not be empty");
         } else {
            if ("*".equals(fullType)) {
               fullType = "*/*";
            }

            int subIndex = fullType.indexOf(47);
            if (subIndex == -1) {
               throw new InvalidMimeTypeException(mimeType, "does not contain '/'");
            } else if (subIndex == fullType.length() - 1) {
               throw new InvalidMimeTypeException(mimeType, "does not contain subtype after '/'");
            } else {
               String type = fullType.substring(0, subIndex);
               String subtype = fullType.substring(subIndex + 1, fullType.length());
               if ("*".equals(type) && !"*".equals(subtype)) {
                  throw new InvalidMimeTypeException(mimeType, "wildcard type is legal only in '*/*' (all mime types)");
               } else {
                  Map parameters = null;

                  int nextIndex;
                  do {
                     nextIndex = index + 1;

                     for(boolean quoted = false; nextIndex < mimeType.length(); ++nextIndex) {
                        char ch = mimeType.charAt(nextIndex);
                        if (ch == ';') {
                           if (!quoted) {
                              break;
                           }
                        } else if (ch == '"') {
                           quoted = !quoted;
                        }
                     }

                     String parameter = mimeType.substring(index + 1, nextIndex).trim();
                     if (parameter.length() > 0) {
                        if (parameters == null) {
                           parameters = new LinkedHashMap(4);
                        }

                        int eqIndex = parameter.indexOf(61);
                        if (eqIndex >= 0) {
                           String attribute = parameter.substring(0, eqIndex).trim();
                           String value = parameter.substring(eqIndex + 1, parameter.length()).trim();
                           parameters.put(attribute, value);
                        }
                     }

                     index = nextIndex;
                  } while(nextIndex < mimeType.length());

                  try {
                     return new MimeType(type, subtype, parameters);
                  } catch (UnsupportedCharsetException var13) {
                     throw new InvalidMimeTypeException(mimeType, "unsupported charset '" + var13.getCharsetName() + "'");
                  } catch (IllegalArgumentException var14) {
                     throw new InvalidMimeTypeException(mimeType, var14.getMessage());
                  }
               }
            }
         }
      }
   }

   public static List parseMimeTypes(String mimeTypes) {
      return !StringUtils.hasLength(mimeTypes) ? Collections.emptyList() : (List)tokenize(mimeTypes).stream().filter(StringUtils::hasText).map(MimeTypeUtils::parseMimeType).collect(Collectors.toList());
   }

   public static List tokenize(String mimeTypes) {
      if (!StringUtils.hasLength(mimeTypes)) {
         return Collections.emptyList();
      } else {
         List tokens = new ArrayList();
         boolean inQuotes = false;
         int startIndex = 0;

         for(int i = 0; i < mimeTypes.length(); ++i) {
            switch (mimeTypes.charAt(i)) {
               case '"':
                  inQuotes = !inQuotes;
                  break;
               case ',':
                  if (!inQuotes) {
                     tokens.add(mimeTypes.substring(startIndex, i));
                     startIndex = i + 1;
                  }
                  break;
               case '\\':
                  ++i;
            }
         }

         tokens.add(mimeTypes.substring(startIndex));
         return tokens;
      }
   }

   public static String toString(Collection mimeTypes) {
      StringBuilder builder = new StringBuilder();
      Iterator iterator = mimeTypes.iterator();

      while(iterator.hasNext()) {
         MimeType mimeType = (MimeType)iterator.next();
         mimeType.appendTo(builder);
         if (iterator.hasNext()) {
            builder.append(", ");
         }
      }

      return builder.toString();
   }

   public static void sortBySpecificity(List mimeTypes) {
      Assert.notNull(mimeTypes, (String)"'mimeTypes' must not be null");
      if (mimeTypes.size() > 1) {
         mimeTypes.sort(SPECIFICITY_COMPARATOR);
      }

   }

   private static Random initRandom() {
      Random randomToUse = random;
      if (randomToUse == null) {
         Class var1 = MimeTypeUtils.class;
         synchronized(MimeTypeUtils.class) {
            randomToUse = random;
            if (randomToUse == null) {
               randomToUse = new SecureRandom();
               random = (Random)randomToUse;
            }
         }
      }

      return (Random)randomToUse;
   }

   public static byte[] generateMultipartBoundary() {
      Random randomToUse = initRandom();
      byte[] boundary = new byte[randomToUse.nextInt(11) + 30];

      for(int i = 0; i < boundary.length; ++i) {
         boundary[i] = BOUNDARY_CHARS[randomToUse.nextInt(BOUNDARY_CHARS.length)];
      }

      return boundary;
   }

   public static String generateMultipartBoundaryString() {
      return new String(generateMultipartBoundary(), StandardCharsets.US_ASCII);
   }
}
