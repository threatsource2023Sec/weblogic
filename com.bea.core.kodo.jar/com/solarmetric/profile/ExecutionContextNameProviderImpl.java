package com.solarmetric.profile;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class ExecutionContextNameProviderImpl implements ExecutionContextNameProvider, Configurable {
   private static final Localizer _loc = Localizer.forPackage(ExecutionContextNameProviderImpl.class);
   public static final int STACK_SINGLELINE = 0;
   public static final int STACK_PARTIAL = 1;
   public static final int STACK_FULL = 2;
   public static final String STACK_SINGLELINE_STR = "line";
   public static final String STACK_PARTIAL_STR = "partial";
   public static final String STACK_FULL_STR = "full";
   protected int _stackStyle = 0;
   protected String[] _removalCandidates = new String[]{"com.solarmetric."};
   protected Configuration _conf;

   public void setConfiguration(Configuration conf) {
      this._conf = conf;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public void setStackStyle(String stackStyleStr) {
      if (stackStyleStr.equals("line")) {
         this._stackStyle = 0;
      } else if (stackStyleStr.equals("partial")) {
         this._stackStyle = 1;
      } else if (stackStyleStr.equals("full")) {
         this._stackStyle = 2;
      } else {
         Log log = ProfilingLog.get(this._conf);
         log.warn(_loc.get("invalid-stack-style", stackStyleStr));
      }

   }

   public void addRemovalCandidate(String removalCand) {
      String[] newCandidateArray = new String[this._removalCandidates.length + 1];

      for(int i = 0; i < this._removalCandidates.length; ++i) {
         newCandidateArray[i] = this._removalCandidates[i];
      }

      this._removalCandidates = newCandidateArray;
      this._removalCandidates[this._removalCandidates.length - 1] = removalCand;
   }

   public String getCreationPoint(Object creationPointType, Broker broker) {
      Throwable t = new Throwable();
      StringWriter sw = new StringWriter();
      t.printStackTrace(new PrintWriter(sw));
      String swStr = sw.toString();
      swStr = swStr.substring(swStr.indexOf(10) + 1);
      if (this._stackStyle == 2) {
         return swStr;
      } else {
         while(true) {
            String candidate = swStr.substring(0, swStr.indexOf(10));
            candidate = candidate.substring(candidate.indexOf(32) + 1);
            boolean discard = this.discardLine(candidate);
            if (!discard) {
               int openParen = candidate.indexOf(40);
               int colon = candidate.lastIndexOf(58);
               int closeParen = candidate.lastIndexOf(41);
               candidate = candidate.substring(0, openParen) + " line: " + candidate.substring(colon + 1, closeParen);
               if (this._stackStyle == 0) {
                  return candidate;
               } else {
                  StringBuffer buf = new StringBuffer();
                  buf.append(candidate);

                  int nextEol;
                  for(swStr = swStr.substring(swStr.indexOf(10) + 1); (nextEol = swStr.indexOf(10)) != -1; swStr = swStr.substring(nextEol + 1)) {
                     buf.append("\n");
                     String line = swStr.substring(0, nextEol);
                     line = this.transformLine(line);
                     buf.append(line);
                  }

                  return buf.toString();
               }
            }

            swStr = swStr.substring(swStr.indexOf(10) + 1);
         }
      }
   }

   protected boolean discardLine(String candidate) {
      for(int i = 0; i < this._removalCandidates.length; ++i) {
         if (candidate.startsWith(this._removalCandidates[i])) {
            return true;
         }
      }

      return false;
   }

   protected String transformLine(String inline) {
      inline = inline.substring(inline.indexOf(32) + 1);
      int openParen = inline.indexOf(40);
      int colon = inline.lastIndexOf(58);
      int closeParen = inline.lastIndexOf(41);
      inline = inline.substring(0, openParen) + " line: " + inline.substring(colon + 1, closeParen);
      return inline;
   }
}
