# Copyright (c) 2015, 2016, Oracle and/or its affiliates. All rights reserved.
#
# Migration sub-script: the base component export handler
#

from java.io import File

# Base Component Handler , all components should inheritant from this class
class BaseComponentHandler:
      compList = []
      def __init__(self, name):
          self.name = name
          if (name != None):
              self.compList.append(self)
          return
      def handle(self, configFile, theCompDir):
          print self.name + " is calling handler in BaseComponentHandler, and should not be!!"
          return
      def run(self, configFile, baseCompDir):
          for i in range(len(self.compList)):
              try:
                  comp = self.compList[i]
                  theCompDir = File(baseCompDir, comp.name)
                  theCompDir.mkdirs()
                  comp.handle(configFile, theCompDir)
              except Exception, ex:
                  print "Error: component handler failed."
                  print ex
          return

