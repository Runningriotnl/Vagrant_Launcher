import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class VagrantManager{

   String [] GlobalStatus;
   List listOfBoxes = new ArrayList<HashMap>();

   public static void main(String argv[]){
       VagrantManager newVManager = new VagrantManager();

       System.out.println(newVManager.listOfBoxes);
   }
      
  public VagrantManager(){
      ArrayList<String> temp = new Commander().executeCommand("vagrant global-status"); 
      this.GlobalStatus = temp.toArray(new String[temp.size()]);
      
      //i = 0 is always the keys (id, status etc.)
      String [] keys = GlobalStatus[0].split("\\s+");

      //i = 1 is always a line of ----
      //i = 2 is the first line with box info
      for(int i = 1; i < GlobalStatus.length; i++){

          Map<String, String> box = new HashMap<String,String>();
          String [] values = GlobalStatus[i].split("\\s+");
          System.out.println(values.length);

          if(values.length == 5){
              for(int index = 0; index < keys.length; index++){
                  box.put(keys[index], values[index]);
              }
          listOfBoxes.add(box);
          }
      }
  }  

  public ArrayList getBoxes(){
      return (ArrayList)listOfBoxes; 
  }

  public String provisionBox(String id){
      return ""; 
  }

  public String reloadBox(String id){
      return "";
  }
  
  public String destroyBox(String id){
      return "";
  }

  public String upBox(String id){
      return "";
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