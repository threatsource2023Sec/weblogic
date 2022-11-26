package weblogic.rmi;

import java.io.FileDescriptor;
import java.net.InetAddress;

/** @deprecated */
@Deprecated
public final class RMISecurityManager extends SecurityManager {
   public boolean assessTrust(Object[] o) {
      return true;
   }

   public void checkCreateClassLoader() {
   }

   public void checkAccess(Thread t) {
   }

   public void checkAccess(ThreadGroup t) {
   }

   public void checkExit(int i) {
   }

   public void checkExec(String s) {
   }

   public void checkLink(String s) {
   }

   public void checkRead(FileDescriptor f) {
   }

   public void checkRead(String s) {
   }

   public void checkRead(String s, Object o) {
   }

   public void checkWrite(FileDescriptor f) {
   }

   public void checkWrite(String s) {
   }

   public void checkDelete(String s) {
   }

   public void checkConnect(String s, int i) {
   }

   public void checkConnect(String s, int i, Object o) {
   }

   public void checkListen(int i) {
   }

   public void checkAccept(String s, int i) {
   }

   public void checkMulticast(InetAddress i) {
   }

   public void checkMulticast(InetAddress i, byte b) {
   }

   public void checkPropertiesAccess() {
   }

   public void checkPropertyAccess(String s) {
   }

   public void checkPropertyAccess(String s, String t) {
   }

   public boolean checkTopLevelWindow(Object o) {
      return true;
   }

   public void checkPrintJobAccess() {
   }

   public void checkSystemClipboardAccess() {
   }

   public void checkAwtEventQueueAccess() {
   }

   public void checkPackageAccess(String s) {
   }

   public void checkPackageDefinition(String s) {
   }

   public void checkSetFactory() {
   }

   public void checkMemberAccess(Class c, int i) {
   }

   public void checkSecurityAccess(String s) {
   }
}
