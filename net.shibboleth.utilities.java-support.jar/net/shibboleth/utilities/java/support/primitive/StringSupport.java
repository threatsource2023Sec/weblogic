package net.shibboleth.utilities.java.support.primitive;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NullableElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.logic.TrimOrNullStringFunction;

public final class StringSupport {
   private StringSupport() {
   }

   @Nonnull
   public static String inputStreamToString(@Nonnull InputStream input, @Nullable CharsetDecoder decoder) throws IOException {
      CharsetDecoder charsetDecoder = decoder;
      if (decoder == null) {
         charsetDecoder = Charset.defaultCharset().newDecoder();
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(input, charsetDecoder));
      Throwable var4 = null;

      try {
         StringBuilder stringBuffer = new StringBuilder();

         for(String line = reader.readLine(); line != null; line = reader.readLine()) {
            stringBuffer.append(line).append("\n");
         }

         String var7 = stringBuffer.toString();
         return var7;
      } catch (Throwable var16) {
         var4 = var16;
         throw var16;
      } finally {
         if (reader != null) {
            if (var4 != null) {
               try {
                  reader.close();
               } catch (Throwable var15) {
                  var4.addSuppressed(var15);
               }
            } else {
               reader.close();
            }
         }

      }
   }

   @Nonnull
   public static String listToStringValue(@Nonnull List values, @Nonnull String delimiter) {
      Constraint.isNotNull(values, "List of values can not be null");
      Constraint.isNotNull(delimiter, "String delimiter may not be null");
      StringBuilder stringValue = new StringBuilder();
      Iterator valueItr = values.iterator();

      while(valueItr.hasNext()) {
         stringValue.append(valueItr.next());
         if (valueItr.hasNext()) {
            stringValue.append(delimiter);
         }
      }

      return stringValue.toString();
   }

   @Nonnull
   public static List stringToList(@Nonnull String string, @Nonnull String delimiter) {
      Constraint.isNotNull(string, "String data can not be null");
      Constraint.isNotNull(delimiter, "String delimiter may not be null");
      ArrayList values = new ArrayList();
      String trimmedString = trimOrNull(string);
      if (trimmedString != null) {
         StringTokenizer tokens = new StringTokenizer(trimmedString, delimiter);

         while(tokens.hasMoreTokens()) {
            values.add(tokens.nextToken());
         }

         if (string.endsWith(delimiter)) {
            values.add("");
         }
      }

      return values;
   }

   @Nullable
   public static String trim(@Nullable String s) {
      return s == null ? null : s.trim();
   }

   @Nullable
   public static String trimOrNull(@Nullable String s) {
      String temp = trim(s);
      return temp != null && temp.length() != 0 ? temp : null;
   }

   @Nonnull
   @NonnullElements
   public static Collection normalizeStringCollection(@Nullable @NullableElements Collection values) {
      return (Collection)(values == null ? Collections.emptySet() : Collections2.filter(Collections2.transform(values, TrimOrNullStringFunction.INSTANCE), Predicates.notNull()));
   }
}
