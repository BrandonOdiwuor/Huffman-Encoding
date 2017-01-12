import java.util.HashMap;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

public class HuffmanEcoding{
  public HashMap<Character, Integer> getFrequencyTable(String fileName){
    HashMap<Character, Integer> frequencyTable = new HashMap<>();
    try{
      InputStream inputStream = new FileInputStream(fileName);
      int charAsci;
      while((charAsci = inputStream.read()) != -1){
        if(frequencyTable.containsKey(new Character((char) charAsci))){
          Integer frequency = frequencyTable.get(new Character((char) charAsci));
          frequencyTable.put(new Character((char) charAsci), new Integer(++frequency));
        }
        else
          frequencyTable.put(new Character((char) charAsci), new Integer(1));
      }
      inputStream.close();
    }catch(IOException ex){
      ex.printStackTrace();
    }
    return frequencyTable;
  }
  
  public void buildEcodingTree(HashMap<Character, Integer> frequencies){}
  
  //public PriorityQueue porpulatePriorityQueue(HashMap<Character, Integer> frequencies){}
  
  
  public static void main(String[] args){
    HuffmanEcoding h = new HuffmanEcoding();
    HashMap<Character, Integer> fTable = h.getFrequencyTable("document.txt");
    System.out.println(fTable);
  }
}