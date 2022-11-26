package weblogic.ejb.container.ejbc.bytecodegen;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.ejb.EJBMetaData;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import javax.ejb.RemoveException;
import org.objectweb.asm.ClassWriter;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.internal.StatefulEJBHome;
import weblogic.ejb.container.internal.StatefulEJBHomeImpl;
import weblogic.ejb.container.internal.StatelessEJBHome;
import weblogic.ejb.container.internal.StatelessEJBHomeImpl;
import weblogic.ejb.container.utils.EJBMethodsUtil;

class HomeImplGenerator extends AbstractHomeImplGenerator {
   private static final MethInfo GET_EJB_METADATA_MI = MethInfo.of("getEJBMetaData", "md_getEJBMetaData").returns(EJBMetaData.class).exceps(RemoteException.class).create();
   private static final MethInfo GET_HOME_HANDLE_MI = MethInfo.of("getHomeHandle", "md_getHomeHandle").returns(HomeHandle.class).exceps(RemoteException.class).create();
   private static final MethInfo REMOVE_OBJ_MI = MethInfo.of("remove", "md_ejbRemove_O").args(Object.class).exceps(RemoteException.class, RemoveException.class).create();
   private static final MethInfo REMOVE_HANDLE_MI = MethInfo.of("remove", "md_ejbRemove_javax_ejb_Handle").args(Handle.class).exceps(RemoteException.class, RemoveException.class).create();

   HomeImplGenerator(NamingConvention nc, SessionBeanInfo sbi) {
      super(nc, sbi, BCUtil.binName(nc.getHomeClassName()), getSuperClsName(sbi));
   }

   String[] getInterfaces() {
      return new String[]{BCUtil.binName(this.sbi.getHomeInterfaceClass())};
   }

   String getComponentImplName() {
      return BCUtil.binName(this.nc.getEJBObjectClassName());
   }

   Class getComponentInterface() {
      return this.sbi.getRemoteInterfaceClass();
   }

   Class getDefExceptionForCreate() {
      return RemoteException.class;
   }

   String getSupersCreateReturnType() {
      return BCUtil.fieldDesc(EJBObject.class);
   }

   Method[] getCreateMethods() {
      return EJBMethodsUtil.getCreateMethods(this.sbi.getHomeInterfaceClass());
   }

   void addCustomMembers(ClassWriter cw) {
      BCUtil.addHomeMembers(cw, this.clsName, this.superClsName, GET_EJB_METADATA_MI, GET_HOME_HANDLE_MI, REMOVE_OBJ_MI, REMOVE_HANDLE_MI);
   }

   private static String getSuperClsName(SessionBeanInfo sbi) {
      if (sbi.isStateful()) {
         return sbi.hasBusinessRemotes() ? BCUtil.binName(StatefulEJBHomeImpl.class) : BCUtil.binName(StatefulEJBHome.class);
      } else {
         return sbi.hasBusinessRemotes() ? BCUtil.binName(StatelessEJBHomeImpl.class) : BCUtil.binName(StatelessEJBHome.class);
      }
   }
}
