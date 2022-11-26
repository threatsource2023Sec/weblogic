package com.sun.faces.facelets.tag.jsf;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.faces.view.facelets.FaceletContext;

public class IterationIdManager {
   private static final String _STACK_OF_TRACKED_IDS = "com.sun.faces.facelets.tag.js._TRACKED_IDS";

   public static boolean registerLiteralId(FaceletContext ctx, String id) {
      Set trackedIds = (Set)_getStackOfTrackedIds(ctx).peek();
      if (trackedIds == null) {
         return false;
      } else if (trackedIds.contains(id)) {
         return true;
      } else {
         trackedIds.add(id);
         return false;
      }
   }

   public static void startIteration(FaceletContext ctx) {
      Deque stack = _getStackOfTrackedIds(ctx);
      Set current = (Set)stack.peek();
      if (current == null) {
         current = new HashSet();
      }

      stack.push(current);
   }

   public static void stopIteration(FaceletContext ctx) {
      _getStackOfTrackedIds(ctx).pop();
   }

   public static void startNamingContainer(FaceletContext ctx) {
      _getStackOfTrackedIds(ctx).push((Object)null);
   }

   public static void stopNamingContainer(FaceletContext ctx) {
      _getStackOfTrackedIds(ctx).pop();
   }

   static boolean isIterating(FaceletContext context) {
      Deque iterationIds = (Deque)context.getAttribute("com.sun.faces.facelets.tag.js._TRACKED_IDS");
      return iterationIds != null && iterationIds.peek() != null;
   }

   private static Deque _getStackOfTrackedIds(FaceletContext ctx) {
      Deque stack = (Deque)ctx.getAttribute("com.sun.faces.facelets.tag.js._TRACKED_IDS");
      if (stack == null) {
         stack = new LinkedList();
         ctx.setAttribute("com.sun.faces.facelets.tag.js._TRACKED_IDS", stack);
      }

      return (Deque)stack;
   }
}
