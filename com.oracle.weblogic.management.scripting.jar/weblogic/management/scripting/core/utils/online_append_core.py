# Caution: This file is part of the command scripting implementation. 
# Do not edit or move this file because this may cause commands and scripts to fail. 
# Do not try to reuse the logic in this file or keep copies of this file because this 
# could cause your scripts to fail when you upgrade to a different version.
# Copyright (c) 2004,2013, Oracle and/or its affiliates. All rights reserved.

import weblogic.management.scripting.core.utils.WLSTCoreUtil
WLS_CORE=WLSTCoreUtil.ensureWLCtx(theInterpreter)

def help(type='default',name='default1'):
  try:
    if type=='default' and name=='default1':
      WLS_CORE.help(name)
      print ''
    elif type!='default' and name=='default1':
      WLS_CORE.help(type)
      print ''
    elif type!='default' and name!='default1':
      WLS_CORE.helpMe(type,name)
      print ''
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=='true':
      raiseWLSTException(e)
    else:
      print e.getMessage()

def exit(defaultAnswer=None, exitcode=0):
  WLS_CORE.exit(defaultAnswer,exitcode)
  
def dumpStack():
  if WLS_CORE.isConnected() == 0 and WLS_CORE.getStackTrace() == None:
    return WLS.dumpStack()
  else:
    return WLS_CORE.dumpStack()
  
myps1="wls:/offline> "

def writeIniFile(filePath):
  WLS_CORE.writeIniFile(filePath)

def startRecording(filePath, recordAll='true'):
  global recording
  recording='true'
  try:
    WLS_CORE.startRecording(filePath, recordAll)
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      print e.getMessage()

def stopRecording():
  global recording
  recording='false'
  try:
    WLS_CORE.stopRecording()
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      print e.getMessage()

