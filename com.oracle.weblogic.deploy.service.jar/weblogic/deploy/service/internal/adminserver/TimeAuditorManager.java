package weblogic.deploy.service.internal.adminserver;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TimeAuditorManager {
   private static final boolean AUDITOR_ENABLED = Boolean.getBoolean("weblogic.deployment.TimeAuditorEnabled");
   public static final int PREPARE = 1;
   public static final int COMMIT = 2;
   public static final int CANCEL = 3;
   private Map auditors = Collections.synchronizedMap(new HashMap());

   public static TimeAuditorManager getInstance() {
      return TimeAuditorManager.Maker.SINGLETON;
   }

   public void startAuditor(long requestId) {
      if (AUDITOR_ENABLED) {
         this.auditors.put(requestId, new RequestAuditor(requestId));
      }

   }

   public void printAuditor(long requestId, OutputStream out) {
      this.printAuditor(requestId, new PrintWriter(out, true));
   }

   public void printAuditor(long requestId, PrintStream out) {
      RequestAuditor auditor = (RequestAuditor)this.auditors.get(requestId);
      if (auditor != null) {
         out.println("<" + new Date() + "> " + auditor);
      }

   }

   public void printAuditor(long requestId, PrintWriter out) {
      RequestAuditor auditor = (RequestAuditor)this.auditors.get(requestId);
      if (auditor != null) {
         out.println("<" + new Date() + "> " + auditor);
         out.flush();
      }

   }

   public String getAuditorAsString(long requestId) {
      RequestAuditor auditor = (RequestAuditor)this.auditors.get(requestId);
      return auditor != null ? auditor.toString() : null;
   }

   public Object endAuditor(long requestId) {
      return this.auditors.remove(requestId);
   }

   public void startTransition(long requestId, int transition) {
      RequestAuditor auditor = (RequestAuditor)this.auditors.get(requestId);
      if (auditor != null) {
         auditor.startTransition(transition);
      }

   }

   public void endTransition(long requestId, int transition) {
      RequestAuditor auditor = (RequestAuditor)this.auditors.get(requestId);
      if (auditor != null) {
         auditor.endTransition(transition);
      }

   }

   public void startTargetTransition(long requestId, String target, int transition) {
      RequestAuditor auditor = (RequestAuditor)this.auditors.get(requestId);
      if (auditor != null) {
         auditor.startTargetTransition(target, transition);
      }

   }

   public void endTargetTransition(long requestId, String target, int transition) {
      RequestAuditor auditor = (RequestAuditor)this.auditors.get(requestId);
      if (auditor != null) {
         auditor.endTargetTransition(target, transition);
      }

   }

   private static String transitionToString(int transition) {
      switch (transition) {
         case 1:
            return "PREPARE";
         case 2:
            return "COMMIT";
         case 3:
            return "CANCEL";
         default:
            throw new IllegalArgumentException("Transition type '" + transition + "' is invalid");
      }
   }

   private class RequestAuditor {
      private final List inspectors = Arrays.asList(TimeAuditorManager.this.new AdminTransitionInspector(1), TimeAuditorManager.this.new AdminTransitionInspector(2), TimeAuditorManager.this.new AdminTransitionInspector(3));
      private final long requestId;

      RequestAuditor(long givenId) {
         this.requestId = givenId;
      }

      void startTransition(int transition) {
         TransitionInspector inspector = this.getInspector(transition);
         inspector.setBeginTime(System.currentTimeMillis());
      }

      void endTransition(int transition) {
         TransitionInspector inspector = this.getInspector(transition);
         inspector.setEndTime(System.currentTimeMillis());
      }

      void startTargetTransition(String target, int transition) {
         AdminTransitionInspector inspector = this.getInspector(transition);
         inspector.startTargetTransition(target);
      }

      void endTargetTransition(String target, int transition) {
         AdminTransitionInspector inspector = this.getInspector(transition);
         inspector.endTargetTransition(target);
      }

      private AdminTransitionInspector getInspector(int transition) {
         Iterator iter = this.inspectors.iterator();

         AdminTransitionInspector each;
         do {
            if (!iter.hasNext()) {
               throw new IllegalArgumentException("Transition type '" + transition + "' is invalid");
            }

            each = (AdminTransitionInspector)iter.next();
         } while(each.getTransitionType() != transition);

         return each;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("Admin - Time calculations for requestId '").append(this.requestId);
         sb.append("' are [").append("\n");
         Iterator iter = this.inspectors.iterator();

         while(iter.hasNext()) {
            sb.append("\t").append(iter.next()).append("\n");
         }

         sb.append("]").append("\n");
         return sb.toString();
      }
   }

   private class AdminTransitionInspector extends TransitionInspector {
      private Map targetTransitions = new HashMap();

      AdminTransitionInspector(int given) {
         super(given);
      }

      void startTargetTransition(String target) {
         TransitionInspector inspector = (TransitionInspector)this.targetTransitions.get(target);
         if (inspector == null) {
            inspector = TimeAuditorManager.this.new TargetTransitionInspector(target, this.getTransitionType());
            this.targetTransitions.put(target, inspector);
         }

         ((TransitionInspector)inspector).setBeginTime(System.currentTimeMillis());
      }

      void endTargetTransition(String target) {
         TransitionInspector inspector = (TransitionInspector)this.targetTransitions.get(target);
         if (inspector != null) {
            inspector.setEndTime(System.currentTimeMillis());
         }

      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append(super.toString()).append(" - {").append("\n");
         Iterator iter = this.targetTransitions.values().iterator();

         while(iter.hasNext()) {
            sb.append(iter.next());
         }

         sb.append("\t").append("}");
         return sb.toString();
      }
   }

   private class TargetTransitionInspector extends TransitionInspector {
      private final String target;

      TargetTransitionInspector(String theTarget, int given) {
         super(given);
         this.target = theTarget;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("\t\t").append(this.target).append("=");
         sb.append(this.timeSpent()).append("\n");
         return sb.toString();
      }
   }

   private class TransitionInspector {
      private final int transition;
      private long beginTime = 0L;
      private long endTime = 0L;

      TransitionInspector(int given) {
         this.transition = given;
      }

      int getTransitionType() {
         return this.transition;
      }

      long getBeginTime() {
         return this.beginTime;
      }

      long getEndTime() {
         return this.endTime;
      }

      void setBeginTime(long given) {
         this.beginTime = given;
      }

      void setEndTime(long given) {
         this.endTime = given;
      }

      long timeSpent() {
         return this.endTime - this.beginTime;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("Transition '").append(TimeAuditorManager.transitionToString(this.transition));
         sb.append("'").append(" took : ").append(this.timeSpent());
         return sb.toString();
      }
   }

   private static class Maker {
      private static TimeAuditorManager SINGLETON = new TimeAuditorManager();
   }
}
