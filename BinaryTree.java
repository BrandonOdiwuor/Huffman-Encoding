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