package net.shibboleth.utilities.java.support.net;

import com.google.common.annotations.Beta;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

@Beta
public final class URISupport {
   private URISupport() {
   }

   public static URI setFragment(URI prototype, String fragment) {
      try {
         return new URI(prototype.getScheme(), prototype.getUserInfo(), prototype.getHost(), prototype.getPort(), prototype.getPath(), prototype.getQuery(), trimOrNullFragment(fragment));
      } catch (URISyntaxException var3) {
         throw new IllegalArgumentException("Illegal fragment text", var3);
      }
   }

   public static URI setHost(URI prototype, String host) {
      try {
         return new URI(prototype.getScheme(), prototype.getUserInfo(), StringSupport.trimOrNull(host), prototype.getPort(), prototype.getPath(), prototype.getQuery(), prototype.getFragment());
      } catch (URISyntaxException var3) {
         throw new IllegalArgumentException("Illegal host", var3);
      }
   }

   public static URI setPath(URI prototype, String path) {
      try {
         return new URI(prototype.getScheme(), prototype.getUserInfo(), prototype.getHost(), prototype.getPort(), trimOrNullPath(path), prototype.getQuery(), prototype.getFragment());
      } catch (URISyntaxException var3) {
         throw new IllegalArgumentException("Illegal path", var3);
      }
   }

   public static URI setPort(URI prototype, int port) {
      try {
         return new URI(prototype.getScheme(), prototype.getUserInfo(), prototype.getHost(), port, prototype.getPath(), prototype.getQuery(), prototype.getFragment());
      } catch (URISyntaxException var3) {
         throw new IllegalArgumentException("Illegal port", var3);
      }
   }

   public static URI setQuery(URI prototype, String query) {
      try {
         return new URI(prototype.getScheme(), prototype.getUserInfo(), prototype.getHost(), prototype.getPort(), prototype.getPath(), trimOrNullQuery(query), prototype.getFragment());
      } catch (URISyntaxException var3) {
         throw new IllegalArgumentException("Illegal query", var3);
      }
   }

   public static URI setQuery(URI prototype, List parameters) {
      try {
         return new URI(prototype.getScheme(), prototype.getUserInfo(), prototype.getHost(), prototype.getPort(), prototype.getPath(), buildQuery(parameters), prototype.getFragment());
      } catch (URISyntaxException var3) {
         throw new IllegalArgumentException("Illegal query", var3);
      }
   }

   public static URI setScheme(URI prototype, String scheme) {
      try {
         return new URI(StringSupport.trimOrNull(scheme), prototype.getUserInfo(), prototype.getHost(), prototype.getPort(), prototype.getPath(), prototype.getQuery(), prototype.getFragment());
      } catch (URISyntaxException var3) {
         throw new IllegalArgumentException("Illegal scheme", var3);
      }
   }

   public static URI fileURIFromAbsolutePath(String path) throws URISyntaxException {
      StringBuilder uriPath = new StringBuilder(path.length() + 8);
      uriPath.append("file://");
      if (!path.startsWith("/")) {
         uriPath.append('/');
      }

      uriPath.append(path);
      return new URI(uriPath.toString());
   }

   public static String buildQuery(List parameters) {
      if (parameters != null && parameters.size() != 0) {
         StringBuilder builder = new StringBuilder();
         boolean firstParam = true;
         Iterator i$ = parameters.iterator();

         while(i$.hasNext()) {
            Pair parameter = (Pair)i$.next();
            if (firstParam) {
               firstParam = false;
            } else {
               builder.append("&");
            }

            builder.append(doURLEncode((String)parameter.getFirst()));
            builder.append("=");
            if (parameter.getSecond() != null) {
               builder.append(doURLEncode((String)parameter.getSecond()));
            }
         }

         return builder.toString();
      } else {
         return null;
      }
   }

   @Nonnull
   public static Map buildQueryMap(@Nullable List parameters) {
      if (parameters != null && parameters.size() != 0) {
         HashMap map = new HashMap();
         Iterator i$ = parameters.iterator();

         while(i$.hasNext()) {
            Pair param = (Pair)i$.next();
            if (param.getFirst() != null) {
               map.put(param.getFirst(), param.getSecond());
            }
         }

         return map;
      } else {
         return Collections.emptyMap();
      }
   }

   @Nullable
   public static String getRawQueryStringParameter(@Nullable String queryString, @Nullable String paramName) {
      String trimmedQuery = trimOrNullQuery(queryString);
      String trimmedName = StringSupport.trimOrNull(paramName);
      if (trimmedQuery != null && trimmedName != null) {
         String encodedName = doURLEncode(trimmedName);
         String[] candidates = trimmedQuery.split("&");
         String[] arr$ = candidates;
         int len$ = candidates.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String candidate = arr$[i$];
            if (candidate.startsWith(encodedName + "=") || candidate.equals(encodedName)) {
               return candidate;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static List parseQueryString(String queryString) {
      String trimmedQuery = trimOrNullQuery(queryString);
      if (trimmedQuery == null) {
         return new LazyList();
      } else {
         ArrayList queryParams = new ArrayList();
         String[] paramPairs = trimmedQuery.split("&");
         String[] arr$ = paramPairs;
         int len$ = paramPairs.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String paramPair = arr$[i$];
            String[] param = paramPair.split("=");
            if (param.length == 1) {
               queryParams.add(new Pair(doURLDecode(param[0]), (String)null));
            } else {
               queryParams.add(new Pair(doURLDecode(param[0]), doURLDecode(param[1])));
            }
         }

         return queryParams;
      }
   }

   public static String trimOrNullPath(String path) {
      String trimmedPath = StringSupport.trimOrNull(path);
      if (trimmedPath == null) {
         return null;
      } else {
         if (trimmedPath.startsWith("?")) {
            trimmedPath = trimmedPath.substring(1);
         }

         if (trimmedPath.endsWith("?") || trimmedPath.endsWith("#")) {
            trimmedPath = trimmedPath.substring(0, trimmedPath.length() - 1);
         }

         return trimmedPath;
      }
   }

   public static String trimOrNullQuery(String query) {
      String trimmedQuery = StringSupport.trimOrNull(query);
      if (trimmedQuery == null) {
         return null;
      } else {
         if (trimmedQuery.startsWith("?")) {
            trimmedQuery = trimmedQuery.substring(1);
         }

         if (trimmedQuery.endsWith("#")) {
            trimmedQuery = trimmedQuery.substring(0, trimmedQuery.length() - 1);
         }

         return trimmedQuery;
      }
   }

   public static String trimOrNullFragment(String fragment) {
      String trimmedFragment = StringSupport.trimOrNull(fragment);
      if (trimmedFragment == null) {
         return null;
      } else {
         if (trimmedFragment.startsWith("#")) {
            trimmedFragment = trimmedFragment.substring(1);
         }

         return trimmedFragment;
      }
   }

   public static String doURLDecode(String value) {
      if (value == null) {
         return null;
      } else {
         try {
            return URLDecoder.decode(value, "UTF-8");
         } catch (UnsupportedEncodingException var2) {
            return null;
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static String doURLEncode(String value) {
      if (value == null) {
         return null;
      } else {
         try {
            return URLEncoder.encode(value, "UTF-8");
         } catch (UnsupportedEncodingException var2) {
            return null;
         }
      }
   }
}
