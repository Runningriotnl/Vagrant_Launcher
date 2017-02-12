import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class VagrantManager{

   String [] globalStatus;
   ArrayList<VagrantBox> listOfBoxes = new ArrayList<VagrantBox>();

   public static void main(String argv[]){
       VagrantManager newVManager = new VagrantManager();
       //set an id here to find the box and do commands on it 
       //currently you need to getBoxByID("the id") 
       //in order to update the state of the box after 
       //executing the command
       VagrantBox testbox = newVManager.getBoxByID("db8260a");
       testbox.upBox();
       testbox = newVManager.getBoxByID("db8260a");
       testbox.provisionBox();
       testbox = newVManager.getBoxByID("db8260a");
       testbox.haltBox();

       if (testbox == null){
           System.out.println("This should return a message to client saying there was no box to be found with that ID");
       }
   }
      
  public VagrantManager(){
      updateglobalStatus();
      getBoxes();
  }  

  private void updateglobalStatus(){
      ArrayList<String> temp = new Commander().executeCommand("vagrant global-status"); 
      this.globalStatus = temp.toArray(new String[temp.size()]);
  }

  public ArrayList<VagrantBox> getListOfBoxes(){
      getBoxes();
      return this.listOfBoxes; 
  } 
  
  private VagrantBox getBoxByID(String id){
      updateglobalStatus();
      getBoxes();
      for(VagrantBox box : this.listOfBoxes){
          
          if(box.ID.equals(id)){
              System.out.println("I found your box :-)");
              return box; 
          }
          System.out.println("Could not find a :-( box");
      }
      return null; 
  }
  private ArrayList getBoxes(){
      //i = 0 is always the keys (id, status etc.)
      String [] keys = this.globalStatus[0].split("\\s+");
      ArrayList<VagrantBox> templistOfBoxes = new ArrayList<VagrantBox>();
      //i = 1 is always a line of ----
      //i = 2 is the first line with box info
      for(int i = 1; i < globalStatus.length; i++){

          Map<String, String> box = new HashMap<String,String>();
          String [] values = globalStatus[i].split("\\s+");

          if(values.length == 5){
              
              for(int index = 0; index < keys.length; index++){
                  box.put(keys[index], values[index]);
              }
          templistOfBoxes.add(new VagrantBox(values[0],values[1],values[2],values[3],values[4]));
          }
          this.listOfBoxes = templistOfBoxes;
      }
      return (ArrayList)templistOfBoxes; 
  }
}

class VagrantBox{

    String provider; 
    final String ID;
    String state;  
    String name; 
    String directory; 

    public String [] toStringArray(){
        String [] temp = new String[5];
        temp[0] = this.ID;
        temp[1] = this.name;
        temp[2] = this.provider;
        temp[3] = this.state;
        temp[4] = this.directory;

        return temp;
    }

    public VagrantBox(String id, String name, String provider, String state, String directory){
        this.provider = provider; 
        this.ID = id; 
        this.state = state;
        this.name = name;
        this.directory = directory; 
    }
  
  public String provisionBox(){
      List<String> temp = new ArrayList(); 
      temp = new Commander().executeCommand("vagrant provision " + this.ID); 
      System.out.println("I have been provisioned :-)");
      return ""; 
  }

  public String reloadBox(){
      List<String> temp = new ArrayList(); 
      temp = new Commander().executeCommand("vagrant reload " + this.ID); 
      System.out.println("I have been reloaded :-|");
      return "";
  }
  
  public String destroyBox(){
      List<String> temp = new ArrayList(); 
      temp = new Commander().executeCommand("vagrant destroy " + this.ID);
      System.out.println("I have been destroyed :-(");
      return "";
  }

  public String upBox(){
      List<String> temp = new ArrayList(); 
      temp = new Commander().executeCommand("vagrant up " + this.ID);
      System.out.println("I have been turned on :-)");
      return "I am starting up but don't have code to check'";
  }
  
  public String haltBox(){
      List<String> temp = new ArrayList(); 
      if(this.state.equals("running")){
        temp = new Commander().executeCommand("vagrant halt " + this.ID);
        System.out.println("I have been turned off :-)");
      }
      else{
        System.out.println("can't halt, the state of box is : " + this.state);
      }
      return "I am starting up but don't have code to check'";
  }

}
class Commander{

      public ArrayList<String> executeCommand(String command) {

        ArrayList <String> output = new ArrayList<String>();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";
			while ((line = reader.readLine())!= null) {
				output.add(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
}