/**
 * 
 * @author Brandon Wayne Odiwuor
 */

import java.util.Map;
import java.util.HashMap;

public class BinaryTree{  
  public Node root;
  public int totalFrequency = 0;
  
  public BinaryTree(Character c, Integer freq){
    root = new Node(c, freq);
    totalFrequency += freq;
  }
  
  public BinaryTree(BinaryTree left, BinaryTree right){
    root = new Node(left.totalFrequency + right.totalFrequency);
    root.left = left.root;
    root.right = right.root;
    totalFrequency += left.totalFrequency + right.totalFrequency;
  }
  
  public void drawTree(){
    drawTree(root, 0);
  }
  
  public void drawTree(Node currNode, int indentLevel){
    for (int i=0; i<indentLevel; i++)
      System.out.print("\t");
    System.out.println(" * " + currNode.character + " " + currNode.frequency);
    if(currNode.left != null)
      drawTree(currNode.left, indentLevel+1);
    if(currNode.right != null)
      drawTree(currNode.right, indentLevel+1);
  }
  
  /**
   * With the help of getEncodingTable() function, it returns a map representation of the whole tree
   * @return a map contating the content of huffman tree
   */
  public Map<Character,String> getEncodingTable(){
    Map<Character,String> t = new HashMap<Character,String>();
    getEncodingTable(root, t, "");
    return t;
  }
  
  /**
   * Creates a Map representation of the subtree starting from node
   * @param node
   * @param table
   * @param string
   */
  private void getEncodingTable(Node node, Map<Character,String> table, String string) { 
    if (node == null) 
      return; 
    if(node.left == null && node.right == null) 
      table.put(node.character, string.toString()); 
    else { 
      getEncodingTable(node.left, table, string + '0'); 
      getEncodingTable(node.right, table, string + '1'); 
    } 
  }
  
  /**
   * with the aid of traverse() function returns an encoded string to its original form before encoding
   * @param str the string to be decoded
   * @returns the decoded version of str
   */
  public String decodeString(String str){ 
    StringBuilder string = new StringBuilder();
    traverse(root, 0, str, string);
    return string.toString();
  }
  
  /**
   * Traverses a tree and re
   * @param node
   * @param index
   * @param str
   * param decodedString
   */
  public void traverse(Node node, int index, String str, StringBuilder decodedString){
    if(index == str.length()){
      decodedString.append(node.character);
      return;
    }
    if(node.left == null && node.right == null){
      decodedString.append(node.character);
      traverse(root,  index, str, decodedString);
    }
    else if(str.charAt(index) == '0')
      traverse(node.left, ++index, str, decodedString);
    else
      traverse(node.right, ++index, str, decodedString);
  }
  
  /**
   * An Inner class storing information about the nodes in a tree
   */
  public static class Node{
    public Character character = new Character(' ');
    public int frequency;
    public boolean isCharacter = true;
    public Node left;
    public Node right;
    
    public Node(Character c, Integer freq){
      character = c;
      frequency = (int) freq;
    }
    
    public Node(Integer freq){
      isCharacter = false;
      frequency = (int) freq;
    }
  }
}