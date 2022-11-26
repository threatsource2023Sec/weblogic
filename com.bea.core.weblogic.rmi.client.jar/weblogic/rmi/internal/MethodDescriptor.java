package weblogic.rmi.internal;

import java.io.EOFException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.List;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.utils.Debug;
import weblogic.utils.reflect.MethodSignatureBuilder;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class MethodDescriptor implements RuntimeMethodDescriptor, InteropWriteReplaceable, Externalizable {
   static final long serialVersionUID = 1317402407729624699L;
   private String signature;
   private int index;
   private boolean transactional;
   private boolean requiresTransaction;
   private boolean onewayTransactionalRequest;
   private boolean onewayTransactionalResponse;
   private boolean oneway;
   private boolean idempotent;
   private WorkManager workManager;
   private String dispatchPolicyName;
   private int timeOut;
   private boolean asyncResponse;
   private boolean asyncParameter;
   private String mangledName;
   private int asyncParameterIndex;
   private Method dispatchMethod;
   private short returnTypeCode;
   private short[] paramTypeCodes;
   private List parameters;
   private WeakReference returnType;
   private final WeakReference declaringClass;
   private final WeakReference implClass;
   private WeakReference dispatchType;
   private int objectID;
   private String remoteExceptionWrapperName;
   private static final boolean DEBUG = false;
   private MethodDescriptor genericMethodDescriptor;
   private MethodDescriptor original;
   private boolean failedToGenerateOldSignature;
   private static boolean generifiedMethodSignatureForBackwardCompatibilityOnly;

   public static boolean isGenericMethodSignatureModeEnabled() {
      return generifiedMethodSignatureForBackwardCompatibilityOnly;
   }

   MethodDescriptor(Method method, Class implClass, String applicationName, int i) {
      this(method, implClass, applicationName, (String)null, i);
   }

   MethodDescriptor(Method method, Class implClass, String applicationName, String moduleName, int i) {
      this.index = -1;
      this.transactional = true;
      this.requiresTransaction = false;
      this.timeOut = 0;
      this.asyncParameterIndex = -1;
      this.remoteExceptionWrapperName = null;
      this.signature = MethodSignatureBuilder.compute(method);
      this.dispatchPolicyName = "weblogic.kernel.Default";
      if (KernelStatus.isServer()) {
         if (applicationName == null && moduleName == null) {
            this.workManager = WorkManagerFactory.getInstance().getDefault();
         } else {
            this.workManager = WorkManagerFactory.getInstance().find(this.dispatchPolicyName, applicationName, moduleName, false);
         }
      }

      this.initializeParameterTypes(method);
      this.declaringClass = new WeakReference(method.getDeclaringClass());
      this.implClass = new WeakReference(implClass);
      this.index = i;
      this.objectID = -1;
      this.createGenericMethodDescriptor(method, implClass);
   }

   private MethodDescriptor(Method method, Class implClazz) {
      this.index = -1;
      this.transactional = true;
      this.requiresTransaction = false;
      this.timeOut = 0;
      this.asyncParameterIndex = -1;
      this.remoteExceptionWrapperName = null;
      this.signature = GenericMethodDescriptor.computeGenericMethodSignature(method);
      this.declaringClass = new WeakReference(method.getDeclaringClass());
      this.implClass = new WeakReference(implClazz);
   }

   private void createGenericMethodDescriptor(Method method, Class implClass) {
      if (isGenericMethodSignatureModeEnabled() && GenericMethodDescriptor.isGenericMethod(method)) {
         try {
            this.genericMethodDescriptor = new MethodDescriptor(method, implClass);
            this.genericMethodDescriptor.oneway = this.oneway;
            this.genericMethodDescriptor.transactional = this.transactional;
            this.genericMethodDescriptor.onewayTransactionalRequest = this.onewayTransactionalRequest;
            this.genericMethodDescriptor.idempotent = this.idempotent;
            this.genericMethodDescriptor.asyncResponse = this.asyncResponse;
            this.genericMethodDescriptor.asyncParameter = this.asyncParameter;
            this.genericMethodDescriptor.asyncParameterIndex = this.asyncParameterIndex;
            this.genericMethodDescriptor.timeOut = this.timeOut;
            this.genericMethodDescriptor.dispatchPolicyName = this.dispatchPolicyName;
            this.genericMethodDescriptor.workManager = this.workManager;
            this.genericMethodDescriptor.returnType = this.returnType;
            this.genericMethodDescriptor.returnTypeCode = this.returnTypeCode;
            this.genericMethodDescriptor.parameters = this.parameters;
            this.genericMethodDescriptor.paramTypeCodes = this.paramTypeCodes;
            this.genericMethodDescriptor.index = this.index;
            this.genericMethodDescriptor.objectID = this.objectID;
            this.genericMethodDescriptor.original = this;
         } catch (UnresolvedTypeException var4) {
            this.genericMethodDescriptor = null;
            this.failedToGenerateOldSignature = true;
         }
      }

   }

   public MethodDescriptor(Method method, Class implClass, boolean oneway, boolean transactional, boolean onewayTransactionalRequest, boolean idempotent, int mSecs) {
      this(method, implClass, oneway, transactional, onewayTransactionalRequest, idempotent, mSecs, -1, false, (String)null);
   }

   public MethodDescriptor(Method method, Class implClass, boolean oneway, boolean transactional, boolean onewayTransactionalRequest, boolean idempotent, int mSecs, boolean asynchronousReturn) {
      this(method, implClass, oneway, transactional, onewayTransactionalRequest, idempotent, mSecs, -1, asynchronousReturn, (String)null);
   }

   public MethodDescriptor(Method method, Class implClass, boolean oneway, boolean transactional, boolean onewayTransactionalRequest, boolean idempotent, int mSecs, int objectID, boolean asynchronousReturn, String remoteExceptionWrapperName) {
      this.index = -1;
      this.transactional = true;
      this.requiresTransaction = false;
      this.timeOut = 0;
      this.asyncParameterIndex = -1;
      this.remoteExceptionWrapperName = null;
      this.signature = MethodSignatureBuilder.compute(method);
      this.oneway = oneway;
      this.transactional = transactional;
      this.onewayTransactionalRequest = onewayTransactionalRequest;
      this.idempotent = idempotent;
      this.asyncResponse = AsyncResult.class.isAssignableFrom(method.getReturnType()) || asynchronousReturn;
      Class[] parameters = method.getParameterTypes();

      for(int i = 0; i < parameters.length; ++i) {
         if (parameters.length > 0 && AsyncResult.class.isAssignableFrom(parameters[i])) {
            this.asyncParameter = true;
            this.asyncParameterIndex = i;
         }
      }

      this.timeOut = mSecs;
      this.initializeParameterTypes(method);
      this.declaringClass = new WeakReference(method.getDeclaringClass());
      this.implClass = new WeakReference(implClass);
      this.objectID = objectID;
      this.remoteExceptionWrapperName = remoteExceptionWrapperName;
      this.createGenericMethodDescriptor(method, implClass);
   }

   ClientMethodDescriptor getClientDescriptor() {
      return new ClientMethodDescriptor(this.signature, this.transactional, this.oneway, this.onewayTransactionalRequest, this.idempotent, this.timeOut, this.asyncResponse, (short[])null, this.remoteExceptionWrapperName);
   }

   public Class getDeclaringClass() {
      return this.declaringClass != null ? (Class)this.declaringClass.get() : null;
   }

   public Class[] getParameterTypes() {
      if (this.parameters != null && this.parameters.size() != 0) {
         Class[] returnVal = new Class[this.parameters.size()];

         for(int i = 0; i < this.parameters.size(); ++i) {
            returnVal[i] = (Class)((WeakReference)this.parameters.get(i)).get();
            if (returnVal[i] == null) {
               throw new IllegalStateException("return type is already GCed due to application undeploy : " + this);
            }
         }

         return returnVal;
      } else {
         return new Class[0];
      }
   }

   public Class getReturnType() {
      return this.returnType != null ? (Class)this.returnType.get() : null;
   }

   Method getDispatchMethod() {
      return this.dispatchMethod;
   }

   Class getDispatchType() {
      return this.dispatchType != null ? (Class)this.dispatchType.get() : null;
   }

   public String getSignature() {
      return this.signature;
   }

   public int getIndex() {
      return this.index;
   }

   public String getMangledName() {
      if (this.mangledName == null) {
         this.mangledName = RMIEnvironment.getEnvironment().getIIOPMangledName(this.getMethod(), (Class)this.implClass.get());
      }

      return this.mangledName;
   }

   public Method getMethod() {
      try {
         Class dClass = (Class)this.declaringClass.get();
         if (dClass == null) {
            throw new AssertionError("declaringClass is already GCed");
         } else {
            return dClass.getMethod(this.signature.substring(0, this.signature.indexOf(40)), this.getParameterTypes());
         }
      } catch (NoSuchMethodException var2) {
         throw new AssertionError(var2);
      }
   }

   public int getAsyncParameterIndex() {
      return this.asyncParameterIndex;
   }

   public RuntimeMethodDescriptor getCanonical(RuntimeDescriptor rd) throws UnmarshalException {
      RuntimeMethodDescriptor rmd = rd.getControlDescriptor(this);
      if (rmd == null) {
         throw new UnmarshalException("Method not found: '" + this.signature + "'");
      } else {
         return rmd;
      }
   }

   public boolean isOnewayTransactionalRequest() {
      return this.onewayTransactionalRequest;
   }

   void setOnewayTransactionalRequest(boolean b) {
      this.onewayTransactionalRequest = b;
      if (this.original == null && this.genericMethodDescriptor != null) {
         this.genericMethodDescriptor.onewayTransactionalRequest = this.onewayTransactionalRequest;
      }

   }

   public boolean isTransactionalOnewayResponse() {
      return this.onewayTransactionalResponse;
   }

   void setOnewayTransactionalResponse(boolean b) {
      this.onewayTransactionalResponse = b;
      if (this.original == null && this.genericMethodDescriptor != null) {
         this.genericMethodDescriptor.onewayTransactionalResponse = this.onewayTransactionalResponse;
      }

   }

   void setTransactional(boolean b) {
      this.transactional = b;
      if (this.original == null && this.genericMethodDescriptor != null) {
         this.genericMethodDescriptor.transactional = this.transactional;
      }

   }

   void setOneway(boolean b) {
      this.oneway = b;
      if (this.original == null && this.genericMethodDescriptor != null) {
         this.genericMethodDescriptor.oneway = this.oneway;
      }

   }

   void setIdempotent(boolean b) {
      this.idempotent = b;
      if (this.original == null && this.genericMethodDescriptor != null) {
         this.genericMethodDescriptor.idempotent = this.idempotent;
      }

   }

   void setAsynchronous(boolean b) {
      this.asyncResponse = b;
      if (this.original == null && this.genericMethodDescriptor != null) {
         this.genericMethodDescriptor.asyncResponse = this.asyncResponse;
      }

   }

   public boolean isIdempotent() {
      return this.idempotent;
   }

   public short[] getParameterTypeAbbrevs() {
      return this.paramTypeCodes;
   }

   public short getReturnTypeAbbrev() {
      return this.returnTypeCode;
   }

   void setRequiresTransaction(boolean b) {
      this.requiresTransaction = b;
      if (this.original == null && this.genericMethodDescriptor != null) {
         this.genericMethodDescriptor.requiresTransaction = this.requiresTransaction;
      }

   }

   public boolean requiresTransaction() {
      return this.requiresTransaction;
   }

   public WorkManager getWorkManager() {
      if (this.workManager == null) {
         Debug.assertion(!KernelStatus.isServer());
         this.workManager = WorkManagerFactory.getInstance().find(this.dispatchPolicyName);
      }

      return this.workManager;
   }

   void setTimeOut(int mSecs) {
      this.timeOut = mSecs;
      if (this.original == null && this.genericMethodDescriptor != null) {
         this.genericMethodDescriptor.timeOut = this.timeOut;
      }

   }

   void setDispatchPolicy(String name, WorkManager wm) {
      this.dispatchPolicyName = name;
      this.workManager = wm;
      if (this.original == null && this.genericMethodDescriptor != null) {
         this.genericMethodDescriptor.dispatchPolicyName = this.dispatchPolicyName;
         this.genericMethodDescriptor.workManager = this.workManager;
      }

   }

   void setDispatchMethod(Method method, Class implClass, Class contextType) {
      this.dispatchMethod = null;
      this.dispatchType = null;
      Class[] mParams = method.getParameterTypes();
      Class[] tmp = new Class[mParams.length + 1];
      System.arraycopy(mParams, 0, tmp, 0, mParams.length);
      tmp[tmp.length - 1] = contextType;

      try {
         this.dispatchMethod = implClass.getMethod(method.getName(), tmp);
         this.dispatchType = new WeakReference(contextType);
         if (this.original == null && this.genericMethodDescriptor != null) {
            this.genericMethodDescriptor.dispatchMethod = this.dispatchMethod;
            this.genericMethodDescriptor.dispatchType = this.dispatchType;
         }

      } catch (NoSuchMethodException var7) {
         throw new AssertionError(method.getName() + " has " + contextType + " context type incorrectly set in class " + implClass.getName(), var7);
      }
   }

   public boolean getImplRespondsToClient() {
      return (this.dispatchType != null ? (Class)this.dispatchType.get() : null) == FutureResponse.class;
   }

   public boolean isOneway() {
      return this.oneway;
   }

   public boolean isTransactional() {
      return this.transactional;
   }

   public boolean hasAsyncResponse() {
      return this.asyncResponse;
   }

   public boolean hasAsyncParameter() {
      return this.asyncParameter;
   }

   public int getTimeOut() {
      return this.timeOut;
   }

   public void setRemoteExceptionWrapperClassName(String wrapperClass) {
      this.remoteExceptionWrapperName = wrapperClass;
   }

   public String getRemoteExceptionWrapperClassName() {
      return this.remoteExceptionWrapperName;
   }

   public boolean workManagerAvailable() {
      return this.workManager != null;
   }

   public String toString() {
      return this.signature;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof MethodDescriptor)) {
         return false;
      } else {
         MethodDescriptor mdOther = (MethodDescriptor)other;
         return this.objectID == mdOther.objectID && this.signature.hashCode() == mdOther.signature.hashCode() && this.signature.equals(mdOther.signature);
      }
   }

   public int hashCode() {
      return this.objectID ^ this.signature.hashCode();
   }

   private short getTypeCode(Class type) {
      if (!type.isPrimitive()) {
         return 0;
      } else if (type == Integer.TYPE) {
         return 1;
      } else if (type == Boolean.TYPE) {
         return 2;
      } else if (type == Byte.TYPE) {
         return 3;
      } else if (type == Character.TYPE) {
         return 4;
      } else if (type == Short.TYPE) {
         return 5;
      } else if (type == Long.TYPE) {
         return 6;
      } else if (type == Float.TYPE) {
         return 7;
      } else if (type == Double.TYPE) {
         return 8;
      } else {
         return (short)(type == Void.TYPE ? 9 : -1);
      }
   }

   private void initializeParameterTypes(Method method) {
      Class rawReturnType = method.getReturnType();
      this.returnType = new WeakReference(rawReturnType);
      this.returnTypeCode = this.getTypeCode(rawReturnType);
      Class[] rawParameters = method.getParameterTypes();
      if (rawParameters != null && rawParameters.length > 0) {
         this.parameters = new ArrayList(rawParameters.length);
         this.paramTypeCodes = new short[rawParameters.length];

         for(int i = 0; i < rawParameters.length; ++i) {
            this.parameters.add(new WeakReference(rawParameters[i]));
            this.paramTypeCodes[i] = this.getTypeCode(rawParameters[i]);
         }
      }

   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      if (peerInfo.compareTo(PeerInfo.VERSION_1033) < 0) {
         if (this.genericMethodDescriptor != null) {
            return this.genericMethodDescriptor;
         }

         if (this.failedToGenerateOldSignature) {
            throw new IOException("Can't talk to old Peer");
         }
      } else if (this.original != null) {
         return this.original;
      }

      return this;
   }

   public MethodDescriptor() {
      this.index = -1;
      this.transactional = true;
      this.requiresTransaction = false;
      this.timeOut = 0;
      this.asyncParameterIndex = -1;
      this.remoteExceptionWrapperName = null;
      this.asyncParameterIndex = -1;
      this.declaringClass = null;
      this.objectID = -1;
      this.implClass = null;
      this.signature = "";
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(this.signature);
      out.writeInt(this.objectID);
   }

   public void readExternal(ObjectInput in) throws IOException {
      this.signature = in.readUTF();

      try {
         this.objectID = in.readInt();
      } catch (StreamCorruptedException var3) {
      } catch (EOFException var4) {
      }

   }

   static {
      if (KernelStatus.isApplet()) {
         generifiedMethodSignatureForBackwardCompatibilityOnly = true;
      } else {
         String s = System.getProperty("weblogic.rmi.generateGenericMethodSignature");
         generifiedMethodSignatureForBackwardCompatibilityOnly = s == null || !s.equalsIgnoreCase("false");
      }

   }
}
