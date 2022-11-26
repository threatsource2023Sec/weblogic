package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.InvalidNotificationException;
import com.bea.diagnostics.notifications.Notification;
import java.lang.annotation.Annotation;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFImageNotificationBean;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.image.ImageAlreadyCapturedException;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.InvalidDestinationDirectoryException;
import weblogic.diagnostics.image.InvalidLockoutTimeException;
import weblogic.diagnostics.type.DiagnosticRuntimeException;
import weblogic.server.GlobalServiceLocator;

final class ImageNotificationListener extends WatchNotificationListenerCommon implements WatchNotificationListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private String imageLocation;
   private int imageLockoutMinutes;
   private String partitionName = null;
   private String partitionId = null;

   ImageNotificationListener(WLDFImageNotificationBean configBean, String partitionName, String partitionId, WatchManagerStatisticsImpl stats) throws InvalidNotificationException, NotificationCreateException {
      super(configBean, stats);
      this.partitionName = partitionName;
      this.partitionId = partitionId;
      this.imageLocation = configBean.getImageDirectory();
      this.imageLockoutMinutes = configBean.getImageLockout();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created image notification for location: " + this.imageLocation + " lockout: " + this.imageLockoutMinutes);
      }

   }

   String getImageLocation() {
      return this.imageLocation;
   }

   int getImageLockoutMinutes() {
      return this.imageLockoutMinutes;
   }

   void setImageLocation(String imageDirectory) {
      this.imageLocation = imageDirectory;
   }

   void setImageLockoutMinutes(int lockoutMinutes) {
      this.imageLockoutMinutes = lockoutMinutes;
   }

   public void processWatchNotification(Notification wn) {
      try {
         try {
            ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Handle image notification for location: " + this.imageLocation + " lockout: " + this.imageLockoutMinutes);
               debugLogger.debug("Watch notification: " + wn);
            }

            if (this.imageLocation != null && this.imageLockoutMinutes >= 0) {
               imageManager.captureImage(this.partitionName, this.partitionId, this.imageLocation, this.imageLockoutMinutes);
            } else if (this.imageLocation != null) {
               imageManager.captureImage(this.partitionName, this.partitionId, this.imageLocation);
            } else if (this.imageLockoutMinutes >= 0) {
               imageManager.captureImage(this.partitionName, this.partitionId, this.imageLockoutMinutes);
            } else {
               imageManager.captureImage(this.partitionName, this.partitionId);
            }

            this.getStatistics().incrementTotalDIMGNotificationsPerformed();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Capture image started successfully.");
            }
         } catch (InvalidLockoutTimeException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Capture image failed due to invalid lockout time ", var3);
            }

            DiagnosticsLogger.logInvalidNotificationLockoutMinutes(Integer.toString(this.imageLockoutMinutes));
         } catch (InvalidDestinationDirectoryException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Capture image failed due to invalid destination ", var4);
            }

            DiagnosticsLogger.logInvalidNotificationImageLocation(this.imageLocation, "" + var4);
         } catch (ImageAlreadyCapturedException var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Capture image failed due to already captured exception ", var5);
            }

            DiagnosticsLogger.logNotificationImageAlreadyCaptured(var5.getMessage());
         } catch (Exception var6) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Capture image failed with exception ", var6);
            }

            DiagnosticsLogger.logErrorInNotification(var6);
         }

      } catch (Throwable var7) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Incrementing failed notifications ", var7);
         }

         this.getStatistics().incrementTotalFailedDIMGNotifications();
         throw new DiagnosticRuntimeException(var7);
      }
   }

   public String toString() {
      return "ImageNotificationListener - image directory: " + this.imageLocation + " image lockout minutes: " + this.imageLockoutMinutes + " isEnabled: " + this.isEnabled();
   }
}
