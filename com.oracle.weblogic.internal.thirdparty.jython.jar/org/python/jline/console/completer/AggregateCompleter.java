package org.python.jline.console.completer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.python.jline.internal.Preconditions;

public class AggregateCompleter implements Completer {
   private final List completers;

   public AggregateCompleter() {
      this.completers = new ArrayList();
   }

   public AggregateCompleter(Collection completers) {
      this.completers = new ArrayList();
      Preconditions.checkNotNull(completers);
      this.completers.addAll(completers);
   }

   public AggregateCompleter(Completer... completers) {
      this((Collection)Arrays.asList(completers));
   }

   public Collection getCompleters() {
      return this.completers;
   }

   public int complete(String buffer, int cursor, List candidates) {
      Preconditions.checkNotNull(candidates);
      List completions = new ArrayList(this.completers.size());
      int max = -1;
      Iterator var6 = this.completers.iterator();

      while(var6.hasNext()) {
         Completer completer = (Completer)var6.next();
         Completion completion = new Completion(candidates);
         completion.complete(completer, buffer, cursor);
         max = Math.max(max, completion.cursor);
         completions.add(completion);
      }

      var6 = completions.iterator();

      while(var6.hasNext()) {
         Completion completion = (Completion)var6.next();
         if (completion.cursor == max) {
            candidates.addAll(completion.candidates);
         }
      }

      return max;
   }

   public String toString() {
      return this.getClass().getSimpleName() + "{completers=" + this.completers + '}';
   }

   private class Completion {
      public final List candidates;
      public int cursor;

      public Completion(List candidates) {
         Preconditions.checkNotNull(candidates);
         this.candidates = new LinkedList(candidates);
      }

      public void complete(Completer completer, String buffer, int cursor) {
         Preconditions.checkNotNull(completer);
         this.cursor = completer.complete(buffer, cursor, this.candidates);
      }
   }
}
