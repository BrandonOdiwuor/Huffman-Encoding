import java.util.HashMap;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Map;
import java.io.File;
import java.util.Scanner;

public class HuffmanEcoding{
  /**
   * Returns a HashMap representation of the frequency table created from the file provided
   * @param fileName the name of the file whose frequency table is to be created
   * @return frequency table created from the file given
   */
  public HashMap<Character, Integer> getFrequencyTable(String fileName){
    HashMap<Character, Integer> frequencyTable = new HashMap<>();
    String string = readFile(fileName);
    for(char character : string.toCharArray()){
      if(frequencyTable.containsKey(character)){
        Integer frequency = frequencyTable.get(character);
        frequencyTable.put(character, new Integer(++frequency));
      }
      else
        frequencyTable.put(character, new Integer(1));
    }
    return frequencyTable;
  }
  
  /**
   * With the help of buildAndPorpulatePriorityQueue() function it builds an encoding tree 
   * from the frequncy table provided and returns a binary tree representation of the tree
   * @param frequencies the frequency table to be used in building the encoding tree
   * @return a binary tree representation of the encoding tree created
   */
  public BinaryTree buildEcodingTree(HashMap<Character, Integer> frequencies){
    PriorityQueue priorityQueue = buildAndPorpulatePriorityQueue(frequencies);
    while(priorityQueue.size() != 1){
      BinaryTree tree1 = priorityQueue.dequeueMin();
      BinaryTree tree2= priorityQueue.dequeueMin();
      BinaryTree tree3 = new BinaryTree(tree1, tree2);
      priorityQueue.enqueue(tree3);
    }
    return priorityQueue.dequeueMin();
  }
  
  /**
   * Builds and returns a priority queue of binary trees from the frequency table given
   * @param frequencies the frequency table used in creating priority queue of binary trees
   * @return a priority queue of binary trees created from the frequency table
   */
  public PriorityQueue buildAndPorpulatePriorityQueue(HashMap<Character, Integer> frequencies){
    PriorityQueue pQueue = new PriorityQueue();
    for(Map.Entry<Character, Integer> entry : frequencies.entrySet() ){
      pQueue.enqueue(new BinaryTree(entry.getKey(), entry.getValue()));
    }
    return pQueue;
  }
  
  /**
   * 
   */
  public void encodeFile(String inputFileName, BinaryTree encodingTree, String outputFileName){
    String encoding = "";
    Map<Character,String> encodingTable = encodingTree.getEncodingTable();
    String inputFileContents = readFile(inputFileName);
    for(char character : inputFileContents.toCharArray())
      encoding += encodingTable.get(new Character(character));
    writeToFile(outputFileName, encoding);
    System.out.println(encodingTree.decodeString(encoding));
  }
  
  /**
   * 
   */
  public void decodeFile(String inputFileName, BinaryTree encodingTree, String outputFileName){
    String inputFileContent = readFile(inputFileName);
    writeToFile(outputFileName,  encodingTree.decodeString(inputFileContent));
  }
  
  /**
   * 
   */
  public void compress(String inputFileName, String outputFileName){}
  
  /**
   * 
   */
  public void decompress(String inputFileName, String outputFileName){}
  
  /**
   * 
   */
  public String readFile(String fileName){
    StringBuilder string = new StringBuilder();
    try{
      File file = new File(fileName); 
      Scanner input = new Scanner(System.in); 
      input = new Scanner(file); 
      while (input.hasNextLine()) 
        string.append(input.nextLine()); 
      input.close();
    }catch(IOException ex){
      ex.printStackTrace();
    }
    return string.toString();
  }
  
  /**
   * 
   */
  public void writeToFile(String fileName, String string){
    try{
      FileWriter writer = new FileWriter(fileName);
      writer.write(string);
      writer.flush();
      writer.close();
    }catch(IOException ex){
      ex.printStackTrace();
    }
  }
  
  
  public static void main(String[] args){
    HuffmanEcoding h = new HuffmanEcoding();
    HashMap<Character, Integer> fTable = h.getFrequencyTable("document.txt");
    System.out.println(fTable);
    h.buildEcodingTree(fTable);
  }
}