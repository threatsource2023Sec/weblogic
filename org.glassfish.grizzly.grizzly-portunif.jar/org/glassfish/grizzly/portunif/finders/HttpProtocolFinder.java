package org.glassfish.grizzly.portunif.finders;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.portunif.PUContext;
import org.glassfish.grizzly.portunif.ProtocolFinder;

public class HttpProtocolFinder implements ProtocolFinder {
   private static final char[] METHOD_FIRST_LETTERS = new char[]{'G', 'P', 'O', 'H', 'D', 'T', 'C'};
   private final Attribute parsingStateAttribute;
   private final int maxRequestLineSize;

   public HttpProtocolFinder() {
      this(2048);
   }

   public HttpProtocolFinder(int maxRequestLineSize) {
      this.parsingStateAttribute = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(HttpProtocolFinder.class + "-" + this.hashCode() + ".parsingStateAttribute");
      this.maxRequestLineSize = maxRequestLineSize;
   }

   public ProtocolFinder.Result find(PUContext puContext, FilterChainContext ctx) {
      Connection connection = ctx.getConnection();
      Buffer buffer = (Buffer)ctx.getMessage();
      ParsingState parsingState = (ParsingState)this.parsingStateAttribute.get(connection);
      int limit = buffer.limit();
      int position;
      int state;
      if (parsingState == null) {
         position = buffer.position();
         state = 0;
      } else {
         position = parsingState.position;
         state = parsingState.state;
      }

      byte c = 0;

      label61:
      while(position < limit) {
         byte c2 = c;
         c = buffer.get(position++);
         switch (state) {
            case 0:
               for(int i = 0; i < METHOD_FIRST_LETTERS.length; ++i) {
                  if (c == METHOD_FIRST_LETTERS[i]) {
                     state = 1;
                     continue label61;
                  }
               }

               return ProtocolFinder.Result.NOT_FOUND;
            case 1:
               if (c == 32) {
                  state = 2;
               }
               break;
            case 2:
               if (c == 32) {
                  state = 3;
               }
               break;
            case 3:
               if (c != 72) {
                  return ProtocolFinder.Result.NOT_FOUND;
               }

               state = 4;
               break;
            case 4:
               if (c == 47 && c2 == 80) {
                  if (parsingState != null) {
                     this.parsingStateAttribute.remove(connection);
                  }

                  return ProtocolFinder.Result.FOUND;
               }
               break;
            default:
               return ProtocolFinder.Result.NOT_FOUND;
         }
      }

      if (position >= this.maxRequestLineSize) {
         return ProtocolFinder.Result.NOT_FOUND;
      } else {
         if (parsingState == null) {
            this.parsingStateAttribute.set(connection, new ParsingState(position, state));
         } else {
            parsingState.position = position;
            parsingState.state = state;
         }

         return ProtocolFinder.Result.NEED_MORE_DATA;
      }
   }

   private static final class ParsingState {
      int position;
      int state;

      public ParsingState(int position, int state) {
         this.position = position;
         this.state = state;
      }
   }
}
