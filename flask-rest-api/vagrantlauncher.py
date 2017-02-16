import sys
import subprocess
import shlex 
import commander

class VagrantManager:
    def __init__(self):
        c = Commander()
        self.GlobalStatus = c.executeCommand("vagrant global-status")
        self.ListOfBoxes = self.listBoxes()
        
    def listBoxes(self):
      #i = 0 is always the keys (id, status etc.)
      keys = self.GlobalStatus[0].split()
      temp = []
      #i = 1 is always a line of ----
      #i = 2 is the first line with box info
      for i in range(1,len(self.GlobalStatus)):
          
          values = self.GlobalStatus[i].split()

          if(len(values) == 5):
              temp.append(VagrantBox(values[0],values[1],values[2],values[3],values[4]))
      self.ListOfBoxes = temp
      
    def getListOfBoxes(self):
        self.listBoxes()
        temp = []
        boxes = {}
        [temp.append({'ID': box.ID, 'name':box.name, 'state': box.state, 'directory': box.directory, 'provider': box.provider}) for box in self.ListOfBoxes]
        boxes['boxes'] = temp
        return boxes
    
    def upBox(self, ID):
        c = Commander
        c.executeCommand("vagrant  up " + ID)  
        return "done"
  
class VagrantBox: 
    def __init__(self,id,name,provider,state,directory):
        self.provider = provider 
        self.ID = id
        self.state = state  
        self.name = name
        self.directory = directory

class Commander:
    def __init__(self): 
        self.os = sys.platform

    def executeCommand(self, command):
        arr = []
        if self.os == "darwin":
            process = subprocess.Popen(shlex.split(command), stdout=subprocess.PIPE)
            while True:
                output = process.stdout.readline().decode()
                if output == '' and process.poll() is not None:
                    break
                if output:
                    arr.append(output)
            rc = process.poll()
        if "win" in self.os:
            process = subprocess.Popen('cmd.exe /C ' + command, stdin = subprocess.PIPE, stdout = subprocess.PIPE)
            while True:
                output = process.stdout.readline().decode()
                if output == '' and process.poll() is not None:
                    break
                if output:
                    arr.append(output)
            rc = process.poll()
        return arr