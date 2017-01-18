/**
 * Implementation of Huffman Encoding algorithm
 * @author Brandon Wayne Odiwuor
 */

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
   * from the frequncy table provided and returns a Huffman Tree
   * @param frequencies the frequency table to be used in building the encoding tree
   * @return the resulting Huffman Tree created
   */
  public HuffmanTree buildEcodingTree(HashMap<Character, Integer> frequencies){
    PriorityQueue priorityQueue = buildAndPorpulatePriorityQueue(frequencies);
    while(priorityQueue.size() != 1){
      HuffmanTree tree1 = priorityQueue.dequeueMin();
      HuffmanTree tree2= priorityQueue.dequeueMin();
      priorityQueue.enqueue(new HuffmanTree(tree1, tree2));
    }
    return priorityQueue.dequeueMin();
  }
  
  /**
   * Builds and returns a priority queue of Huffman Trees from the frequency table given
   * @param frequencies the frequency table used in creating priority queue of huffman trees
   * @return a priority queue of Huffman Trees created from the frequency table
   */
  public PriorityQueue buildAndPorpulatePriorityQueue(HashMap<Character, Integer> frequencies){
    PriorityQueue pQueue = new PriorityQueue();
    for(Map.Entry<Character, Integer> entry : frequencies.entrySet() ){
      pQueue.enqueue(new HuffmanTree(entry.getKey(), entry.getValue()));
    }
    return pQueue;
  }
  
  /**
   * Reads a file (input file) and uses the encodingTree to encode the file and writes its 
   * content in the output file
   * @param inputFileName the name of the file to be encoded
   * @param encodedTree the huffman tree to be used in encoding the inputFile
   * @param outputFileName the name of the file that the encoded string is to be written
   */
  public void encodeFile(String inputFileName, HuffmanTree encodingTree, String outputFileName){
    String encoding = "";
    Map<Character,String> encodingTable = encodingTree.getEncodingTable();
    String inputFileContents = readFile(inputFileName);
    for(char character : inputFileContents.toCharArray())
      encoding += encodingTable.get(new Character(character));
    writeToFile(outputFileName, encoding);
  }
  
  /**
   * Reads an ecoded file (inputFile) and uses the encodingTree to decode the fileContents 
   * before writting it into the outputFile
   * @param inputFileName the name of the file to be decoded
   * @param encodedTree the huffman tree to be used in decoding the inputFile
   * @param outputFileName the name of the file that the decoded string is to be written
   */
  public void decodeFile(String inputFileName, HuffmanTree encodingTree, String outputFileName){
    String inputFileContent = readFile(inputFileName);
    writeToFile(outputFileName,  encodingTree.decodeString(inputFileContent));
  }
  
  /**
   * With the help of encodeFile() function, compresses an input file and writes 
   * a string representation of the resultant bits into output file
   * @param inputFileName the file to be compressed
   * @param outputFileName the name of the file containing a string representation 
   *                    bit output from the compression
   */
  public void compress(String inputFileName, String outputFileName){
    String inputFileContent = readFile(inputFileName);
     HashMap<Character, Integer> frequencyTable = getFrequencyTable(inputFileName);
     HuffmanTree encodingTree = buildEcodingTree(frequencyTable);
     encodeFile(inputFileName, encodingTree, outputFileName);
     writeToFile("encoding_tree.txt", encodingTree.toString());
  }
  
  /**
   * With the hel of decodeFile() function, reads bits from the input file and reconstructs the original document
   * @param inputFileName
   * @param outputFileName
   */
  public void decompress(String inputFileName, String outputFileName){
    HashMap<Character, Integer> frequencyTable = getFrequencyTableFromFile("encoding_tree.txt");
    HuffmanTree encodingTree = buildEcodingTree(frequencyTable);
    decodeFile(inputFileName, encodingTree, outputFileName);
  }
  
  /**
   * Reads the input file and reconstructs the frequency table
   * @param fileName the name of the file to be used in reconstructing the frequency table
   */
  public HashMap<Character, Integer> getFrequencyTableFromFile(String fileName){
    HashMap<Character, Integer> frequencyTable = new HashMap<>();
    try{
      File file = new File(fileName); 
      Scanner input = new Scanner(System.in); 
      input = new Scanner(file); 
      while (input.hasNextLine()) {
        String[] token = input.nextLine().split("\t");
        frequencyTable.put(token[0].charAt(0), Integer.parseInt(token[1]));
      }
      input.close();
    }catch(IOException ex){
      ex.printStackTrace();
    }
    return frequencyTable;
  }
  
  /**
   * Reads a file and returns a string containing the contents of the file
   * @param fileName the name of the file being read
   * @return String containg the contents of the file
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
   * Writes the contents of the string into the file whose name is given
   * @param fineName the name of the file where the string is to be written
   * @param string the string to be written in the file
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
    HuffmanEcoding huffmanEcoding = new HuffmanEcoding();
     huffmanEcoding.compress("document.txt", "bits.txt");
     huffmanEcoding.decompress("bits.txt", "decodedFile.txt");
  }
}