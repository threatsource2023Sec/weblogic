package net.shibboleth.utilities.java.support.net;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.net.MediaType;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public final class MediaTypeSupport {
   private static final Function STRIP_PARAMS = new StripMediaTypeParametersFunction();

   private MediaTypeSupport() {
   }

   public static boolean validateContentType(String contentType, Set validTypes, boolean noContentTypeIsValid, boolean isOneOfStrategy) {
      String contentTypeValue = StringSupport.trimOrNull(contentType);
      if (contentTypeValue != null) {
         MediaType mediaType;
         if (isOneOfStrategy) {
            mediaType = MediaType.parse(contentTypeValue);
            Iterator i$ = validTypes.iterator();

            MediaType validType;
            do {
               if (!i$.hasNext()) {
                  return false;
               }

               validType = (MediaType)i$.next();
            } while(!mediaType.is(validType));

            return true;
         } else {
            mediaType = MediaType.parse(contentTypeValue).withoutParameters();
            Set validTypesWithoutParameters = new HashSet();
            validTypesWithoutParameters.addAll(Collections2.filter(Collections2.transform(validTypes, STRIP_PARAMS), Predicates.notNull()));
            return validTypesWithoutParameters.contains(mediaType);
         }
      } else {
         return noContentTypeIsValid;
      }
   }
}
