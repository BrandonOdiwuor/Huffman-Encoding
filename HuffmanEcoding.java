import java.util.HashMap;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Map;

public class HuffmanEcoding{
  /**
   * Returns a HashMap representation of the frequency table created from the file provided
   * @param fileName the name of the file whose frequency table is to be created
   * @return frequency table created from the file given
   */
  public HashMap<Character, Integer> getFrequencyTable(String fileName){
    HashMap<Character, Integer> frequencyTable = new HashMap<>();
    try{
      InputStream inputStream = new FileInputStream(fileName);
      int charAsci;
      while((charAsci = inputStream.read()) != -1){
        //System.out.print((char) charAsci + ", ");
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
  
  
  public static void main(String[] args){
    HuffmanEcoding h = new HuffmanEcoding();
    HashMap<Character, Integer> fTable = h.getFrequencyTable("document.txt");
    //System.out.println(fTable);
    h.buildEcodingTree(fTable);
  }
}