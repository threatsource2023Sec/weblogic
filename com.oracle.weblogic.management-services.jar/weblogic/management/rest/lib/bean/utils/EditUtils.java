package weblogic.management.rest.lib.bean.utils;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.admin.rest.utils.ExceptionUtil;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.rest.lib.utils.SecurityUtils;

public final class EditUtils {
   public static final String EDIT_SESSION_HEADER_NAME = "weblogic.edit.session";
   private static final String INTERNAL_EDIT_SESSION_PREFIX = "internal--";

   public static String getEditSessionName(HttpServletRequest request) throws Exception {
      return getEditSession(request).getEditSessionName();
   }

   public static EditAccess getEditAccess(HttpServletRequest request) throws Exception {
      SecurityUtils.checkPermission();
      return getEditSession(request).getEditAccess();
   }

   public static String getClientSpecifiedEditSessionName(HttpServletRequest request) throws Exception {
      EditSession es = getClientSpecifiedEditSession(request);
      return es != null ? es.getEditSessionName() : null;
   }

   public static String generateInternalEditSessionName(String baseEditSessionName) throws Exception {
      return baseEditSessionName == null ? null : "internal--" + baseEditSessionName;
   }

   private static EditSession getEditSession(HttpServletRequest request) throws Exception {
      EditSession es = getClientSpecifiedEditSession(request);
      if (es == null) {
         es = getDefaultEditSession(request, request.getRemoteUser());
      }

      return es;
   }

   private static EditSession getClientSpecifiedEditSession(HttpServletRequest request) throws Exception {
      return getNamedEditSession(request, request.getHeader("weblogic.edit.session"), request.getRemoteUser());
   }

   private static EditSession getNamedEditSession(HttpServletRequest request, String editSessionName, String currentUser) throws Exception {
      if (StringUtil.isEmpty(editSessionName)) {
         return null;
      } else if ("default".equals(editSessionName)) {
         return getDefaultEditSession();
      } else {
         EditAccess ea = ManagementServiceRestricted.getEditSession(editSessionName);
         if (ea == null) {
            ExceptionUtil.badRequest(MessageUtils.beanFormatter(request).msgNonExistentEditSession(editSessionName));
         }

         return new EditSession(editSessionName, ea);
      }
   }

   private static EditSession getDefaultEditSession(HttpServletRequest request, String currentUser) throws Exception {
      List sessions = getSessionsLockedByUser(currentUser);
      if (sessions.size() == 1) {
         return (EditSession)sessions.get(0);
      } else {
         if (sessions.size() > 1) {
            throwSpecifyEditSession(request, currentUser);
         }

         sessions = getSessionsCreatedByUser(currentUser);
         if (sessions.size() == 1) {
            return (EditSession)sessions.get(0);
         } else {
            if (sessions.size() > 1) {
               throwSpecifyEditSession(request, currentUser);
            }

            return getDefaultEditSession();
         }
      }
   }

   private static EditSession getDefaultEditSession() throws Exception {
      return new EditSession("default", ManagementServiceRestricted.getEditSession("default"));
   }

   private static List getSessionsLockedByUser(String currentUser) throws Exception {
      List sessions = new ArrayList();
      String[] var2 = ManagementServiceRestricted.getEditSessions();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String sessionName = var2[var4];
         EditAccess ea = ManagementServiceRestricted.getEditSession(sessionName);
         if (currentUser.equals(getCurrentLockOwner(ea)) && !isInternalEditSession(sessionName)) {
            sessions.add(new EditSession(sessionName, ea));
         }
      }

      return sessions;
   }

   private static List getSessionsCreatedByUser(String currentUser) throws Exception {
      List sessions = new ArrayList();
      String[] var2 = ManagementServiceRestricted.getEditSessions();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String sessionName = var2[var4];
         EditAccess ea = ManagementServiceRestricted.getEditSession(sessionName);
         if (currentUser.equals(ea.getCreator()) && !isInternalEditSession(sessionName)) {
            sessions.add(new EditSession(sessionName, ea));
         }
      }

      return sessions;
   }

   private static void throwSpecifyEditSession(HttpServletRequest request, String currentUser) throws Exception {
      ExceptionUtil.badRequest(MessageUtils.beanFormatter(request).msgCannotDetermineDefaultEditSession(currentUser, "weblogic.edit.session", getAvailableSessionNames(currentUser)));
   }

   private static String getAvailableSessionNames(String currentUser) throws Exception {
      List sessions = new ArrayList();
      String[] var2 = ManagementServiceRestricted.getEditSessions();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String sessionName = var2[var4];
         EditAccess ea = ManagementServiceRestricted.getEditSession(sessionName);
         String currentLockOwner = getCurrentLockOwner(ea);
         if (!StringUtil.notEmpty(currentLockOwner) || currentLockOwner.equals(currentUser)) {
            sessions.add(sessionName);
         }
      }

      return sessions.toString();
   }

   private static String getCurrentLockOwner(EditAccess ea) throws Exception {
      String lockOwner = ea.getEditor();
      if (lockOwner == null) {
         return null;
      } else {
         long exp = ea.getEditorExpirationTime();
         return exp > 0L && exp <= System.currentTimeMillis() ? null : lockOwner;
      }
   }

   private static boolean isInternalEditSession(String editSessionName) throws Exception {
      return editSessionName == null ? false : editSessionName.startsWith("internal--");
   }

   private static class EditSession {
      private String editSessionName;
      private EditAccess editAccess;

      private EditSession(String editSessionName, EditAccess editAccess) {
         this.editSessionName = editSessionName;
         this.editAccess = editAccess;
      }

      private String getEditSessionName() {
         return this.editSessionName;
      }

      private EditAccess getEditAccess() {
         return this.editAccess;
      }

      // $FF: synthetic method
      EditSession(String x0, EditAccess x1, Object x2) {
         this(x0, x1);
      }
   }
}
