package com.sun.faces.el;

import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.RequestStateManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

public class VariableResolverChainWrapper extends ELResolver {
   private VariableResolver legacyVR = null;

   public VariableResolverChainWrapper(VariableResolver variableResolver) {
      this.legacyVR = variableResolver;
   }

   public void setWrapped(VariableResolver newVR) {
      this.legacyVR = newVR;
   }

   public Object getValue(ELContext context, Object base, Object property) throws ELException {
      if (this.legacyVR instanceof ChainAwareVariableResolver) {
         return null;
      } else if (base != null) {
         return null;
      } else if (base == null && property == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base and property");
         throw new PropertyNotFoundException(message);
      } else {
         context.setPropertyResolved(true);
         Object result = null;
         FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
         String propString = property.toString();
         Map stateMap = RequestStateManager.getStateMap(facesContext);
         boolean var15 = false;

         label173: {
            Object var9;
            try {
               var15 = true;
               Object varNames = (List)stateMap.get("com.sun.faces.LegacyVariableResolver");
               if (varNames == null || ((List)varNames).isEmpty() || !((List)varNames).contains(propString)) {
                  if (varNames == null) {
                     varNames = new ArrayList();
                     stateMap.put("com.sun.faces.LegacyVariableResolver", varNames);
                  }

                  ((List)varNames).add(propString);
                  result = this.legacyVR.resolveVariable(facesContext, propString);
                  var15 = false;
                  break label173;
               }

               context.setPropertyResolved(false);
               var9 = null;
               var15 = false;
            } catch (EvaluationException var16) {
               context.setPropertyResolved(false);
               throw new ELException(var16);
            } finally {
               if (var15) {
                  List varNames = (List)stateMap.get("com.sun.faces.LegacyVariableResolver");
                  if (varNames != null && !varNames.isEmpty()) {
                     varNames.remove(propString);
                  }

                  context.setPropertyResolved(result != null);
               }
            }

            List varNames = (List)stateMap.get("com.sun.faces.LegacyVariableResolver");
            if (varNames != null && !varNames.isEmpty()) {
               varNames.remove(propString);
            }

            context.setPropertyResolved(result != null);
            return var9;
         }

         List varNames = (List)stateMap.get("com.sun.faces.LegacyVariableResolver");
         if (varNames != null && !varNames.isEmpty()) {
            varNames.remove(propString);
         }

         context.setPropertyResolved(result != null);
         return result;
      }
   }

   public Class getType(ELContext context, Object base, Object property) throws ELException {
      if (this.legacyVR instanceof ChainAwareVariableResolver) {
         return null;
      } else {
         Object result = this.getValue(context, base, property);
         context.setPropertyResolved(result != null);
         return result != null ? result.getClass() : null;
      }
   }

   public void setValue(ELContext context, Object base, Object property, Object val) throws ELException {
      if (!(this.legacyVR instanceof ChainAwareVariableResolver)) {
         if (null == base && null == property) {
            throw new PropertyNotFoundException();
         }
      }
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) throws ELException {
      if (this.legacyVR instanceof ChainAwareVariableResolver) {
         return false;
      } else if (null == base && null == property) {
         throw new PropertyNotFoundException();
      } else {
         return false;
      }
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      return null;
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      if (this.legacyVR instanceof ChainAwareVariableResolver) {
         return null;
      } else {
         return base == null ? String.class : null;
      }
   }
}
