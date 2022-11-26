package weblogic.ejb.container.deployer.mbimpl;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.utils.ToStringUtils;

public final class MethodInfoImpl implements MethodInfo {
   private final Method method;
   private final String methodIntf;
   private short methodDescriptorMethodType;
   private final Set roleNames = new HashSet();
   private final String[] methodParams;
   private short transAttribute = -1;
   private int isolationLevel = -1;
   private int selectForUpdate = 0;
   private boolean isExcluded = false;
   private boolean isUnchecked = false;
   private boolean idempotent = false;
   private boolean skipStateReplication = false;
   private int retryOnRollbackCount = 0;

   private MethodInfoImpl(Method method, String methodIntf) {
      this.method = method;
      Class[] paramTypes = method.getParameterTypes();
      this.methodParams = new String[paramTypes.length];

      for(int i = 0; i < paramTypes.length; ++i) {
         this.methodParams[i] = paramTypes[i].getName();
      }

      this.methodIntf = methodIntf;
   }

   public void setSelectForUpdate(int i) {
      this.selectForUpdate = i;
   }

   public int getSelectForUpdate() {
      return this.selectForUpdate;
   }

   public Method getMethod() {
      return this.method;
   }

   public String getSignature() {
      return DDUtils.getMethodSignature(this.method);
   }

   public String getMethodName() {
      return this.method.getName();
   }

   public String[] getMethodParams() {
      return (String[])this.methodParams.clone();
   }

   public void setMethodDescriptorMethodType(short s) {
      this.methodDescriptorMethodType = s;
   }

   public short getMethodDescriptorMethodType() {
      return this.methodDescriptorMethodType;
   }

   public String getMethodInterfaceType() {
      return this.methodIntf;
   }

   public void setRetryOnRollbackCount(int i) {
      this.retryOnRollbackCount = i;
   }

   public int getRetryOnRollbackCount() {
      return this.retryOnRollbackCount;
   }

   public void addSecurityRoleRestriction(String roleName) {
      this.roleNames.add(roleName);
   }

   public Set getSecurityRoleNames() {
      return this.roleNames;
   }

   public boolean hasRole(String roleName) {
      return this.roleNames.contains(roleName);
   }

   public boolean hasRoles() {
      return !this.roleNames.isEmpty();
   }

   public void setTransactionAttribute(short transAttr) {
      this.transAttribute = transAttr;
   }

   public short getTransactionAttribute() {
      return this.transAttribute;
   }

   public void setTxIsolationLevel(int l) {
      this.isolationLevel = l;
   }

   public int getTxIsolationLevel() {
      return this.isolationLevel;
   }

   public void setIsExcluded(boolean b) {
      this.isExcluded = b;
   }

   public boolean getIsExcluded() {
      return this.isExcluded;
   }

   public void setUnchecked(boolean b) {
      this.isUnchecked = b;
   }

   public boolean getUnchecked() {
      return this.isUnchecked;
   }

   public void setIdempotent(boolean val) {
      this.idempotent = val;
   }

   public boolean isIdempotent() {
      return this.idempotent;
   }

   public void setSkipStateReplication(boolean val) {
      this.skipStateReplication = val;
   }

   public boolean getSkipStateReplication() {
      return this.skipStateReplication;
   }

   public static MethodInfoImpl createMethodInfoImpl(Method method, String methodIntf) {
      return new MethodInfoImpl(method, methodIntf);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("MethodInfo: Method: ");
      sb.append(this.getSignature());
      sb.append(" TxAttribute: " + ToStringUtils.txAttributeToString(this.transAttribute));
      sb.append(" Isolation Level: " + ToStringUtils.isoToString(this.isolationLevel));
      return sb.toString();
   }
}
