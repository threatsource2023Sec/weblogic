package weblogic.servlet.internal;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.zip.CRC32;
import javax.servlet.http.HttpSessionActivationListener;
import weblogic.common.internal.PassivationUtils;
import weblogic.servlet.ReferencedAttribute;
import weblogic.utils.classloaders.ClassLoaderUtils;
import weblogic.utils.classloaders.GenericClassLoader;

public class AttributeWrapper implements Serializable {
   static final long serialVersionUID = 309266816418685703L;
   protected Object object;
   protected transient byte[] serializedObject;
   private final String intialContextPath;
   private transient boolean isLoadedByBootStrap;
   private transient boolean isWebLogicClassLoader;
   private transient int objectClassLoaderHash;
   private transient boolean forceToConvert;
   private transient int serializedObjectLength;
   private transient long currentChecksum;
   private transient long previousChecksum;
   private transient boolean isAttributeLoggedOnce;
   private boolean isEJBObjectWrapped;
   private boolean isHttpSessionActivationListenerWrapped;

   public AttributeWrapper(Object o) {
      this(o, (WebAppServletContext)null);
   }

   AttributeWrapper(Object o, WebAppServletContext ctx) {
      this.isLoadedByBootStrap = false;
      this.isWebLogicClassLoader = false;
      this.objectClassLoaderHash = 0;
      this.serializedObjectLength = -1;
      this.currentChecksum = -1L;
      this.previousChecksum = -1L;
      this.isAttributeLoggedOnce = false;
      this.isEJBObjectWrapped = false;
      this.isHttpSessionActivationListenerWrapped = false;
      this.object = o;
      this.intialContextPath = ctx == null ? null : ctx.getContextPath();
      this.saveObjectClassLoaderInfo(o);
      this.isHttpSessionActivationListenerWrapped = o instanceof HttpSessionActivationListener;
   }

   public final boolean isHttpSessionActivationListenerWrapped() {
      return this.isHttpSessionActivationListenerWrapped;
   }

   public final void setEJBObjectWrapped(boolean aValue) {
      this.isEJBObjectWrapped = aValue;
   }

   public final boolean isEJBObjectWrapped() {
      return this.isEJBObjectWrapped;
   }

   public final Object getObject() throws ClassNotFoundException, IOException {
      return this.getObject(true, (WebAppServletContext)null);
   }

   public Object getObject(boolean checkReload) throws ClassNotFoundException, IOException {
      return this.getObject(checkReload, (WebAppServletContext)null);
   }

   Object getObject(WebAppServletContext ctx) throws ClassNotFoundException, IOException {
      return this.getObject(true, ctx);
   }

   protected Object getObject(boolean checkReload, WebAppServletContext ctx) throws ClassNotFoundException, IOException {
      synchronized(this) {
         if (!checkReload) {
            return this.object;
         } else if (this.serializedObject != null) {
            return this.convertBytesToObject(this.serializedObject);
         } else if (needToWrap(this.object) && !this.isOptimisticSerialization(ctx)) {
            ClassLoader currentCL = Thread.currentThread().getContextClassLoader();
            if (this.needToConvert(currentCL, ctx)) {
               this.object = PassivationUtils.copy(this.object);
               this.saveObjectClassLoaderInfo(this.object);
               this.forceToConvert = false;
            }

            return this.object;
         } else {
            return this.object;
         }
      }
   }

   protected boolean isOptimisticSerialization(WebAppServletContext ctx) {
      return this.intialContextPath != null && ctx != null && ctx.getConfigManager().isOptimisticSerialization() && !this.intialContextPath.equals(ctx.getContextPath());
   }

   protected Object convertBytesToObject(byte[] objBytes) throws ClassNotFoundException, IOException {
      this.object = PassivationUtils.toObject(objBytes);
      this.saveObjectClassLoaderInfo(this.object);
      this.serializedObject = null;
      return this.object;
   }

   static boolean needToWrap(Object obj) {
      return !(obj instanceof String) && !(obj instanceof Number) && !(obj instanceof Boolean) && !(obj instanceof Character) && !(obj instanceof ReferencedAttribute);
   }

   public void setForceToConvert(boolean b) {
      this.forceToConvert = b;
   }

   public synchronized void convertToBytes() throws IOException {
      this.serializedObject = PassivationUtils.toByteArray(this.object);
   }

   protected boolean needToConvert(ClassLoader currentCL, WebAppServletContext ctx) {
      this.reInitClassLoaderInfo();
      if (this.isLoadedByBootStrap && (this.object instanceof Iterable || this.object instanceof Map) && this.intialContextPath != null && ctx != null && !this.intialContextPath.equals(ctx.getContextPath())) {
         return true;
      } else if (this.isLoadedByBootStrap) {
         return false;
      } else if (this.forceToConvert) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Attribute value is forced to be converted at lookup time with the appropriate ClassLoader");
         }

         return true;
      } else if (this.objectClassLoaderHash == currentCL.hashCode() && currentCL == this.object.getClass().getClassLoader()) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("The Classloader of attribute: " + this.object.getClass().getClassLoader() + "\nThe current Classloader:" + currentCL);
         }

         return false;
      } else if (!this.isWebLogicClassLoader) {
         if (HTTPDebugLogger.isEnabled() && !this.isAttributeLoggedOnce) {
            HTTPDebugLogger.debug("WLS ignores to convert attribute value.Because, attribute value is impossible to be converted at lookup time with the appropriate ClassLoader because initial ClassLoader isn't Weblogic classloaders. To convert attribute value, initial ClassLoader should be WebLogic ClassLoader. \nCurrent classloader: " + currentCL + "\nInitial context path: " + this.intialContextPath + ", current context path: " + (ctx == null ? "null" : ctx.getContextPath()) + "\nObject class: " + this.object.getClass());
            this.isAttributeLoggedOnce = true;
         }

         return false;
      } else if (!ClassLoaderUtils.isChild(this.objectClassLoaderHash, currentCL)) {
         if (HTTPDebugLogger.isEnabled() && !this.isAttributeLoggedOnce) {
            HTTPDebugLogger.debug("Attribute value is needed to be converted at lookup time with the appropriate ClassLoader because current ClassLoader is different with initial ClassLoader and is not child of initial ClassLoader.\nCurrent classloader: " + currentCL + "\nInitial context path: " + this.intialContextPath + ", current context path: " + (ctx == null ? "null" : ctx.getContextPath()) + "\nObject class: " + this.object.getClass());
            this.isAttributeLoggedOnce = true;
         }

         return true;
      } else {
         return false;
      }
   }

   protected void reInitClassLoaderInfo() {
      if (this.objectClassLoaderHash == 0) {
         this.saveObjectClassLoaderInfo(this.object);
      }

   }

   private void saveObjectClassLoaderInfo(Object o) {
      ClassLoader cl = o.getClass().getClassLoader();
      if (cl == null) {
         this.isLoadedByBootStrap = true;
         this.isWebLogicClassLoader = false;
      } else {
         this.objectClassLoaderHash = cl.hashCode();
         this.isWebLogicClassLoader = cl instanceof GenericClassLoader;
      }
   }

   public long getCheckSum() {
      return this.currentChecksum;
   }

   public long getPreviousChecksum() {
      return this.previousChecksum;
   }

   public int getSerializedObjectLength() {
      return this.serializedObjectLength;
   }

   public void testSerializability(AttributeWrapper oldValue) throws Exception {
      byte[] bytes = PassivationUtils.toByteArray(this.object);
      PassivationUtils.toObject(bytes);
      this.serializedObjectLength = bytes.length;
      CRC32 crc = new CRC32();
      crc.update(bytes);
      this.currentChecksum = crc.getValue();
      if (oldValue != null) {
         this.previousChecksum = oldValue.getCheckSum();
      }

   }
}
