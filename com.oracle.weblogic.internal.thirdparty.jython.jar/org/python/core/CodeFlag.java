package org.python.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public enum CodeFlag {
   CO_OPTIMIZED(1),
   CO_NEWLOCALS(2),
   CO_VARARGS(4),
   CO_VARKEYWORDS(8),
   CO_GENERATOR(32),
   CO_NESTED(16),
   CO_GENERATOR_ALLOWED(4096),
   CO_FUTURE_DIVISION(8192),
   CO_FUTURE_ABSOLUTE_IMPORT(16384),
   CO_FUTURE_WITH_STATEMENT(32768),
   CO_FUTURE_PRINT_FUNCTION(65536),
   CO_FUTURE_UNICODE_LITERALS(131072);

   public final int flag;
   private static Iterable allFlags = Collections.unmodifiableList(Arrays.asList(values()));

   private CodeFlag(int flag) {
      this.flag = flag;
   }

   public boolean isFlagBitSetIn(int flags) {
      return (flags & this.flag) != 0;
   }

   static Iterable parse(final int flags) {
      return new Iterable() {
         public Iterator iterator() {
            return new Iterator() {
               Iterator all;
               CodeFlag next;

               {
                  this.all = CodeFlag.allFlags.iterator();
                  this.next = null;
               }

               public boolean hasNext() {
                  if (this.next != null) {
                     return true;
                  } else {
                     CodeFlag flag;
                     do {
                        if (!this.all.hasNext()) {
                           return false;
                        }

                        flag = (CodeFlag)this.all.next();
                     } while(!flag.isFlagBitSetIn(flags));

                     this.next = flag;
                     return true;
                  }
               }

               public CodeFlag next() {
                  if (this.hasNext()) {
                     CodeFlag var1;
                     try {
                        var1 = this.next;
                     } finally {
                        this.next = null;
                     }

                     return var1;
                  } else {
                     throw new IllegalStateException();
                  }
               }

               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }
      };
   }
}
