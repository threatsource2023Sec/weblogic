package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;

public final class ParamNode {
   private static final DebugLogger debugLogger;
   private RDBMSBean rbean;
   private String paramName;
   private int variableNumber;
   private Class classType;
   private String id;
   private String remoteHomeName;
   private boolean isBeanParam;
   private boolean isSelectInEntity;
   private Class primaryKeyClass;
   private boolean hasCompoundKey;
   private List paramSubList;
   private boolean isOracleNLSDataType;

   public ParamNode(RDBMSBean rb, String pName, int v, Class cl, String id, String h, boolean isBP, boolean isSE, Class pkClass, boolean hasCK, boolean isNLS) {
      this.rbean = rb;
      this.paramName = pName;
      this.variableNumber = v;
      this.classType = cl;
      this.id = id;
      this.remoteHomeName = h;
      this.isBeanParam = isBP;
      this.isSelectInEntity = isSE;
      this.primaryKeyClass = pkClass;
      this.hasCompoundKey = hasCK;
      this.isOracleNLSDataType = isNLS;
      if (debugLogger.isDebugEnabled()) {
         debug(" Created new Param Node for: " + id + ", variableNumber: " + v);
         debug(this.toString());
      }

   }

   public boolean isBeanParam() {
      return this.isBeanParam;
   }

   public boolean hasCompoundKey() {
      return this.hasCompoundKey;
   }

   public String getRemoteHomeName() {
      return this.remoteHomeName;
   }

   public String getId() {
      return this.id;
   }

   public Class getParamClass() {
      return this.classType;
   }

   public String getParamName() {
      return this.paramName;
   }

   public void addParamSubList(ParamNode n) {
      if (this.paramSubList == null) {
         this.paramSubList = new ArrayList();
      }

      this.paramSubList.add(n);
   }

   public List getParamSubList() {
      if (this.paramSubList == null) {
         this.paramSubList = new ArrayList();
      }

      return this.paramSubList;
   }

   public Class getPrimaryKeyClass() {
      return this.primaryKeyClass;
   }

   public void setPrimaryKeyClass(Class c) {
      this.primaryKeyClass = c;
   }

   public RDBMSBean getRDBMSBean() {
      return this.rbean;
   }

   public boolean isSelectInEntity() {
      return this.isSelectInEntity;
   }

   public int getVariableNumber() {
      return this.variableNumber;
   }

   public boolean isOracleNLSDataType() {
      return this.isOracleNLSDataType;
   }

   public void setOracleNLSDataType(boolean isNLS) {
      this.isOracleNLSDataType = isNLS;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("\n\n------------------------");
      sb.append("\n  ParamNode     name: " + this.paramName);
      sb.append("\n               class: " + this.classType.getName());
      sb.append("\n      variableNumber: " + this.variableNumber);
      sb.append("\n                  id: " + this.id);
      sb.append("\n      remoteHomeName: " + this.remoteHomeName);
      sb.append("\n         isBeanParam: " + (this.isBeanParam ? "true" : "false"));
      sb.append("\n    isSelectInEntity: " + (this.isSelectInEntity ? "true" : "false"));
      sb.append("\n     primaryKeyClass: " + this.primaryKeyClass);
      sb.append("\n      hasCompoundKey: " + (this.hasCompoundKey ? "true" : "false"));
      sb.append("\n");
      if (this.paramSubList != null) {
         Iterator it = this.paramSubList.iterator();
         if (it.hasNext()) {
            sb.append("  ----------- subList ------");

            while(it.hasNext()) {
               ParamNode n = (ParamNode)it.next();
               sb.append(n.toString());
            }
         }
      }

      return sb.toString();
   }

   public static void addInVariableOrder(List l, ParamNode newNode) {
      int listSize = l.size();
      boolean moved = false;

      for(int i = 0; i < listSize; ++i) {
         ParamNode listNode = (ParamNode)l.get(i);
         if (newNode.getVariableNumber() < listNode.getVariableNumber()) {
            l.add(i, newNode);
            moved = true;
         }

         if (moved) {
            break;
         }
      }

      if (!moved) {
         l.add(newNode);
      }

   }

   public static List listInVariableOrder(List l) {
      List newList = new ArrayList();
      Iterator it = l.iterator();

      while(it.hasNext()) {
         ParamNode n = (ParamNode)it.next();
         int newSize = newList.size();
         boolean moved = false;

         for(int i = 0; i < newSize; ++i) {
            ParamNode newNode = (ParamNode)newList.get(i);
            if (n.getVariableNumber() < newNode.getVariableNumber()) {
               newList.add(i, n);
               moved = true;
            }

            if (moved) {
               break;
            }
         }

         if (!moved) {
            newList.add(n);
         }
      }

      return newList;
   }

   private static void debug(String s) {
      debugLogger.debug("[ParamNode] " + s);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
