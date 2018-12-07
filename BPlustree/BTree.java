import java.awt.List;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

public class BTree implements Serializable
{
	static int order;
	public static Node root;
	
	String vt;
	String id;
	String name;
	String depart;
	int salary;
	public BTree(String vt,String id,String name,String depart,int salary) 
	{
		this.vt=vt;
		this.id=id;
		this.name=name;
		this.depart=depart;
		this.salary=salary;
	}
	
	public BTree() 
	{
		
	}

	public String toString()
	{
		return vt + " " + id + " " + name + " " + depart + " " + salary;
 	}
	
	public static int D;
	
	public static int getD() 
	{
		BTree ob=new BTree();
		int order=ob.order;
		if (order%2==0)
		{
			D=order/2;
		}
		else
		{
			D=order/2+1;
		}
		
		return D;
	}
	
	public static LeafNode find(int key,Node node)
	{
		if (node.isLeafNode)
		{
			return (LeafNode)node;
		}
		if (node == null)
			return null; 
		
		IndexNode i = (IndexNode)node;
		
		if (key >= node.keys.get(0))
		{
			if ((key < node.keys.get(node.keys.size() - 1)) )
			{
				ListIterator<Integer> iterator = i.keys.listIterator();
				int flag =0;
				while(flag==0)
				{
					if (iterator.hasNext())
					{
						if (iterator.next() <= key)
						{
							
						}
						else
						{
							return find(key, i.children.get(iterator.previousIndex())); 
						}
					}
					else
					{
						flag=1;
					}
				}
			}
			else
			{
				return find(key, i.children.get(i.children.size() - 1));
			}
			
		}
		else
		{
			return find(key, i.children.get(0));
		}
		
		return null;
	}
	
	public static BTree printrecord(String v) throws IOException, ClassNotFoundException
	{
		ObjectInputStream innn=null;
		BTree m ;
		int flag=1;
		try
		{
			innn= new ObjectInputStream (
                    new FileInputStream("./src/datafile.txt"));
			while(flag==1)
			{
				try
				{
					
					m  = (BTree) innn.readObject();
					if (m.id.equals(v))
					{
						if (m.vt.equals("0000"))
						{
							System.out.println("Not a Valid record");
						}
						else
							return m;

					}
						
				}
				catch(EOFException e)
				{
					flag=0;
				}
				
			}
			return null;
		
		}
		finally
		{
			innn.close();
		}
		
		
	}
	
	public static void findrange(String l,String u) throws IOException, ClassNotFoundException
	{
		ObjectInputStream innn=null;
		BTree m ;
		int flag=1;
		try
		{
			innn= new ObjectInputStream (
                    new FileInputStream("./src/datafile.txt"));
			while(flag==1)
			{
				try
				{
					
					m  = (BTree) innn.readObject();
					int ll = Integer.parseInt(l);
					int uu = Integer.parseInt(u);
					int temp = Integer.parseInt(m.id);
					if (temp>=ll && temp<=uu )
					{
						if (m.vt.equals("0000"))
						{
							
						}
						else
							System.out.println(m);
					}
						
				}
				catch(EOFException e)
				{
					flag=0;
				}
				
			}
		
		}
		finally
		{
			innn.close();
		}
		
		
	}
	
	public static void insertrecord(BTree stu) throws IOException, ClassNotFoundException 
	{
		
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		boolean flag=true;
		try {
              out = new ObjectOutputStream (
                    new FileOutputStream("./src/temp.txt"));
              in= new ObjectInputStream (
                      new FileInputStream("./src/datafile.txt"));
              int i=0;
              BTree m1;
              while(flag)
              {
	              
	              try{
	            		  m1=(BTree)in.readObject();
	            		  out.writeObject(m1);
	              }
	              catch(EOFException e)
	              {
	            	  out.writeObject(stu);
	            	  flag=false;
	              }
              }
              
           	} finally {
           		in.close();
                out.close();
           	}
		
			File oldfile=new File("./src/temp.txt");
			File newfile=new File("./src/datafile.txt");
			newfile.delete();
			oldfile.renameTo(newfile);
	}
	
	public void insert(int key) 
	{
		if (root != null)
		{
			
		}
		else
		{
			root = new LeafNode(key);
		}
		
		int jam=0;
		if (jam==0)
			jam++;
		Entry<Integer, Node> overflowed = insertnode(key, root);
		
		if (overflowed == null)
		{
			
		}
		else
		{
			root = new IndexNode(overflowed.getKey(), root, overflowed.getValue());
		}
		if (jam==0)
			jam++;
		
	}
	
	public Entry<Integer, Node> insertnode( int key, Node node)
	{
		Entry<Integer,Node> overflow = null; 
		
		if (node.isLeafNode)
		{
			LeafNode leaf = (LeafNode) node; 
			leaf.insertSorted(key);
			if (!leaf.isOverflowed())
			{
				return null;
			}
			else
			{
				Entry<Integer, Node> rightSplit = splitleaf(leaf);
				return rightSplit;
			}
			
			
		}
		else 
		{
			IndexNode i = (IndexNode) node; 
			
			if (key >= node.keys.get(0))
			{
				if ((key < node.keys.get(node.keys.size() - 1)) )
				{
					int flag =0;
					while(flag<i.children.size())
					{
						if (i.keys.get(flag)>key)
						{
							overflow = insertnode(key, i.children.get(flag));
							break;
						}
						
						flag++;
					}
				}
				else
				{
					overflow = insertnode(key, i.children.get(i.children.size() - 1)); 
				}
				
			}
			else
			{
				overflow = insertnode(key, i.children.get(0));
			}
			
		}
		if (overflow != null)
		
		{

			IndexNode i = (IndexNode)node;
			int jam=0;
			if (jam==0)
				jam++;
			int splittingKey = overflow.getKey();
			int aaaa;
			int indexAtParent = i.keys.size();
			

			if (splittingKey >= i.keys.get(0))
			{
				if ((splittingKey <= i.keys.get(i.keys.size() - 1)) )
				{
					int flag =0;
					while(flag<i.keys.size())
					{
						if (flag < i.keys.get(flag))
						{
							indexAtParent = flag;
						}
						
						flag++;
					}
				}
				else
				{
					indexAtParent = i.children.size(); 
				}
				
			}
			else
			{
				indexAtParent=0;
			}
			
			
			i.insertSorted(overflow, indexAtParent);
			
			if (!i.isOverflowed())
			{
				return null;
			}
			else
			{
				Entry<Integer, Node> rightSplit = splitind(i);
				return rightSplit;
			}
			
		}
		return overflow;
		
	}
	public Entry<Integer, Node> splitind(IndexNode index) 
	{
		int size =  getD();  
		int hi=0;
		
		ArrayList<Integer> rightKeys = new ArrayList<Integer>(size);
		int hii;
		ArrayList<Node> rightChildren = new ArrayList<Node>(size + 1);
		
		if (hi==0)
			hi++;
		rightKeys.addAll(index.keys.subList( getD()+1, index.keys.size()));
		if (hi==0)
			hi++;
		rightChildren.addAll(index.children.subList( getD()+1, index.children.size())); 
		
		if (hi==0)
			hi++;
		IndexNode rightNode = new IndexNode(rightKeys, rightChildren);
		if (hi==0)
			hi++;
		AbstractMap.SimpleEntry<Integer, Node> splitted = new AbstractMap.SimpleEntry<Integer, Node>(index.keys.get( getD()), rightNode);

		if (hi==0)
			hi++;
		index.keys.subList( getD(), index.keys.size()).clear();
		if (hi==0)
			hi++;
		index.children.subList( getD()+1, index.children.size()).clear();
		if (hi==0)
			hi++;
		
		return splitted;
	}
	
	public Entry<Integer, Node> splitleaf(LeafNode leaf) 
	{
		int size = getD()+1;
		 
		int lksk;
		ArrayList<Integer> right = new ArrayList<Integer>(size); 
		
		int pps=0;
		
		right.addAll(leaf.keys.subList(getD(), leaf.keys.size()));
		if (pps==1)
			pps++;
		leaf.keys.subList( getD(), leaf.keys.size()).clear();
		
		if (pps==2)
			pps++;
		LeafNode rightLeaf = new LeafNode(right);
		
		if (leaf.nextLeaf == null)
		{
			if (pps==1)
				pps++;
			leaf.nextLeaf = rightLeaf;
		}
		else
		{
			rightLeaf.nextLeaf = leaf.nextLeaf;
			if (pps==1)
				pps++;
			leaf.nextLeaf = rightLeaf;
		}
		if (pps==1)
			pps++;

		return new AbstractMap.SimpleEntry<Integer, Node>(rightLeaf.keys.get(0), rightLeaf);

	}
	
	
	public static void deleterecord(String v) throws IOException, ClassNotFoundException 
	{
		
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		boolean flag=true;
		try {
              out = new ObjectOutputStream (
                    new FileOutputStream("./src/temp.txt"));
              in= new ObjectInputStream (
                      new FileInputStream("./src/datafile.txt"));
              int i=0;
              BTree m1;
              while(flag)
              {
	              
	              try{
	            		  m1=(BTree)in.readObject();
	            		  if(m1.id.equals(v))
	            		  {
	            			  m1.vt="0000";
	            		  }
	            		  out.writeObject(m1);
	              }
	              catch(EOFException e)
	              {
	            	  flag=false;
	              }
              }
              
           	} finally {
           		in.close();
                out.close();
           	}
		
			File oldfile=new File("./src/temp.txt");
			File newfile=new File("./src/datafile.txt");
			newfile.delete();
			oldfile.renameTo(newfile);
	}
	
	public void delete(int key)
	{
		int index = deletenode(null, root, key);
		if (index == -1)
		{
			
		}
		else
		{
			root.keys.remove(index);
			int lalal;
			if (root.keys.size() != 0)
			{
				
			}
			else
			{
				root = ((IndexNode) root).children.get(0);
			}
		}
		
		if (root.keys.size() != 0)
		{
			
		}
		else
		{
			root = null;
		}
	}
	

	private int deletenode(IndexNode parent, Node node, int key) 
	{
		 
		
		// find index of node in parent
		int indexInParent = -1; 
		
		if (parent == null)
		{
			
		}
		else
		{
			indexInParent = 0;
			while ( indexInParent < parent.children.size() )
			{
				if (parent.children.get(indexInParent) != node)
				{
					
				}
				else
				{
					break;
				}
				indexInParent++;
			}
		}
		int indexToDelete = -1;
		
		if (!node.isLeafNode)
		{
			IndexNode inode = (IndexNode) node; 
			
			if (key >= inode.keys.get(0))
			{
				if ((key < inode.keys.get(inode.keys.size() - 1)) )
				{
					int flag =0;
					while(flag<inode.keys.size())
					{
						if (inode.keys.get(flag)>key)
						{
							indexToDelete = deletenode(inode, inode.children.get(flag), key);
						}
						
						flag++;
					}
				}
				else
				{
					indexToDelete = deletenode(inode, inode.children.get(inode.children.size() - 1), key);
				}
				
			}
			else
			{
				indexToDelete = deletenode(inode, inode.children.get(0), key);
			}
			
			
		} 
		
		else 
		{
			LeafNode leafNode = (LeafNode) node; 
			int i=0;
			while (i < leafNode.keys.size())
			{
				if (leafNode.keys.get(i) != key)
				{
					
				}
				else
				{
					leafNode.keys.remove(i); 
					 break;
				}
				i++;
			}

			
			if (leafNode.isUnderflowed()  )
			{
				if(leafNode != root) 
				{
					if (indexInParent - 1 < 0)
					{
						LeafNode right = (LeafNode) parent.children.get(indexInParent + 1); 
						return underflowleaf(leafNode, right,parent);
					}
					else
					{
						LeafNode left = (LeafNode) parent.children.get(indexInParent -1);
						return underflowleaf(left, leafNode,parent);
					}
					
				}
				
			} 
			else 
			{
				if (leafNode.keys.size() <= 0)
				{
					
				}
				else
				{
					keyupdate(root, key, leafNode.keys.get(0));
				}
				int jj=0;	
				return -1; 
			}
			
			
			
		}
		
		if (indexToDelete == -1)
		{
			
		}
		else
		{
			if (node == root )
			{
				return indexToDelete; 
			}
			int lalal=0;
			node.keys.remove(indexToDelete);
			
			if (!node.isUnderflowed())
			{
				
			}
			{
				IndexNode right = (IndexNode)node; 
				lalal++;
				IndexNode left = (IndexNode)node; 
				
				
				if (indexInParent - 1 < 0)
				{
					right = (IndexNode) parent.children.get(indexInParent + 1);  
				} 
				else
				{
					left = (IndexNode) parent.children.get(indexInParent - 1);  
				}
				return underflowind(left, right, parent);  
			}
		}
		
		return -1; 
	}

	
	
	public int underflowind(IndexNode leftIndex,IndexNode rightIndex, IndexNode parent ) 
	{
		
		int lalaa;
		 
		
		int index=0;
		while (index < parent.keys.size())
		{
			if (parent.children.get(index) == leftIndex && parent.children.get(index+1) == rightIndex)
			{
				break; 
			}
			index++;
		}
		
		int separatingKey;
		separatingKey = parent.keys.get(index);
		
		if (leftIndex.keys.size() + rightIndex.keys.size() >= 2* getD())
		{
			
		
		}
		else
		{
			leftIndex.keys.add(separatingKey);
			int lala=0;
			leftIndex.keys.addAll(rightIndex.keys);
			if(lala==0)
				lala++;
			
			leftIndex.children.addAll(rightIndex.children);
			int lalal=0;
			parent.children.remove(parent.children.indexOf(rightIndex));
			lalal++;
			return index; 
		}
		int hi=9;
		
		if (rightIndex.isUnderflowed())
		{
			int gg=0;
			rightIndex.keys.add(0, separatingKey); 
			gg++;
			Node lastChild = leftIndex.children.remove(leftIndex.children.size() - 1);
			gg++;
			
			rightIndex.children.add(0, lastChild); 
			if(gg==0)
				gg++;
			parent.keys.set(parent.keys.size()-1, leftIndex.keys.remove(leftIndex.keys.size() - 1));	
		}
		else if(hi==0)
		{
			hi++;
		}
		else if (leftIndex.isUnderflowed()) 
		{
			int gg=0;
			leftIndex.keys.add(separatingKey);
			gg++;
			parent.keys.set(index, rightIndex.keys.remove(0)); 
			if (gg==0)
				gg++;
			leftIndex.children.add(rightIndex.children.remove(0));
		}
		int lal;
		return -1;
		
	}
	
	
	public int underflowleaf(LeafNode left, LeafNode right,IndexNode parent)
	{ 
	
		int lala=0;
		if (lala==0)
			lala++;
		if (left.keys.size() + right.keys.size() >= 2* getD())
		{
			
		}
		else
		{
			left.keys.addAll(right.keys); 
			int alal=0;
			left.nextLeaf = right.nextLeaf;
			int yo=1;
			int indexInParent = parent.children.indexOf(right);
			if(alal==0)
				alal++;
			parent.children.remove(indexInParent);
			alal++;
			yo++;
			return indexInParent -1; 
		}
		
		int childsIndexInParent;
		if (!left.isUnderflowed())
		{
			childsIndexInParent = parent.children.indexOf(right);
			int yo=0;
			yo++;
			right.insertSorted(left.keys.remove(left.keys.size()-1));
			yo++;
			parent.keys.set(childsIndexInParent - 1, parent.children.get(childsIndexInParent).keys.get(0));
			yo++;
		} 
		else 
		{
			childsIndexInParent = parent.children.indexOf(right);
			int aa=0;
			if(aa==0)
				aa++;
			left.insertSorted(right.keys.remove(0));
			aa++;
			
		}
		
		parent.keys.set(childsIndexInParent - 1, parent.children.get(childsIndexInParent).keys.get(0));
		
		int hi=0;
		
		return -1;

	}

	
	public void keyupdate(Node node, int searchkey, int newKey)
	{
		if (node.isLeafNode) 
			return;
		int lalal=0;
		
		if (node == null) 
			return;
		
		IndexNode inode = (IndexNode) node;
		int i=0;
		while ( i < node.keys.size())
		{
			
			
			if (inode.keys.get(i) != searchkey)
			{

				if (inode.keys.get(i) < searchkey)
				{
					
				}
				else
				{
					break; 
				}
			}
			else
			{
				inode.keys.set(i, newKey);
				return;
			}
			i++;
		}
		
		if (searchkey >= inode.keys.get(0))
		{
			if ((searchkey <= inode.keys.get(inode.keys.size() - 1)) )
			{
				int flag =0;
				while(flag<node.keys.size())
				{
					if (inode.keys.get(flag)>searchkey)
					{
						keyupdate(inode.children.get(flag), searchkey, newKey); 
					}
					
					flag++;
				}
			}
			else
			{
				keyupdate(inode.children.get(inode.children.size() - 1), searchkey, newKey);
			}
			
		}
		else
		{
			keyupdate(inode.children.get(0), searchkey, newKey);
		}
		
	}
	
	
	public static ArrayList<Integer> retrieve() throws IOException, ClassNotFoundException
	{
		ArrayList<Integer> yo = new ArrayList<Integer>();
		ObjectInputStream innn=null;
		BTree m ;
		int flag=1;
		try
		{
			innn= new ObjectInputStream (
                    new FileInputStream("./src/datafile.txt"));
			while(flag==1)
			{
				try
				{
					
					m  = (BTree) innn.readObject();
					yo.add(Integer.parseInt(m.id));
						
				}
				catch(EOFException e)
				{
					flag=0;
				}
				
				 
				
			}
			return yo;
		}
		finally
		{
			innn.close();
		}
		
	}
	
	public static void printTree(BTree tree) {
		/* Temporary queue. */
		int nodesInNextLevel = 0;
		ArrayList<Integer> childrenPerIndex = new ArrayList<Integer>();
		String result = "";
		int nodesInCurrentLevel = 1;
		int hello;
		
		LinkedBlockingQueue<Node> queue = new LinkedBlockingQueue<Node>();
		int bye=0;
		queue.add(tree.root);
		if (bye==0)
			bye++;
		while (!queue.isEmpty()) 
		{
			bye++;
			nodesInCurrentLevel--;
			Node target = queue.poll();
			int sal;
			
			if (target.isLeafNode)
			{
				LeafNode leaf = (LeafNode) target;
				int aa;
				result += "[";
				int kk=0;
				while (kk< leaf.keys.size()) 
				{
					result += "(" + leaf.keys.get(kk)  + ");";
					kk++;
				}
				
				childrenPerIndex.set(0, childrenPerIndex.get(0) - 1);
				if (childrenPerIndex.get(0)!= 0)
				{
					result += "] # ";
				} 
				else 
				{
					result += "] $ ";
					childrenPerIndex.remove(0);	
				}
			} 
			else {
				result += "@ ";
				int aa;
				IndexNode index = ((IndexNode) target);
				int kk=0;
				while (kk < index.keys.size()) 
				{
					result += "" + index.keys.get(kk) + "/";
					kk++;
				}
				queue.addAll(index.children);
				int aa1;
				result += "@   ";
				
				if (index.children.get(0).isLeafNode)
				{
					int sum9;
					childrenPerIndex.add(index.children.size());
				}
				nodesInNextLevel += index.children.size();
				bye++;
			}
			bye++;
			if (nodesInCurrentLevel == 0) {
				bye++;
				result += "\n";
				int as;
				nodesInCurrentLevel = nodesInNextLevel;
				nodesInNextLevel = 0;
			}

		}
		System.out.println(result);

	}
	
	
	public static void indextofile(ArrayList<Integer> yo ) throws IOException, ClassNotFoundException 
	{
		PrintWriter	out1 = null;
		try	
		{
			
			
			out1 = new PrintWriter(	new FileWriter("./src/textfile.txt"));
			
			for (int i=0; i<yo.size(); i++)
				out1.println(yo.get(i)+ " ");
		
		}
		
		finally
		{
			if (out1!=null)	
				out1.close();
		}
		
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Input order of BPlusTree");
		order = sc.nextInt();
		
		BTree myTree = new BTree(); 
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
		ret = retrieve();
		
		
		
		if (order>ret.size())
		{
			System.out.print("[");
			for (int i=0; i<ret.size(); i++)
				System.out.print(ret.get(i)+ ";");
			System.out.println("]");
		}
		
		else
		{
			for(int i=0; i<ret.size(); i++)
			{
				myTree.insert(ret.get(i));
			}
			if (myTree != null)
			{
				
				printTree(myTree);
			}
			
		}
		indextofile(ret); 
		
		System.out.println("1.Find \n2.PrintAll \n3.FindRange \n4.Insert \n5.Delete \n6.Exit \n");
		while(true) 
		{
			int op=sc.nextInt();
			if(op==1)
			{
				//finding leaf node with particular value
				String v=sc.next();
				LeafNode leaf = myTree.find(Integer.parseInt(v),root);
				for (int i=0; i<leaf.keys.size();i++)
				{
					System.out.print(leaf.keys.get(i)+" ");
				}
				
			}
			else if(op==2) 
			{
				//Printing records with value V
				String v=sc.next();
				//LeafNode leaf = find(Integer.parseInt(v),root);
				BTree rec = printrecord(v);
				System.out.println(rec);
				
			}
			else if(op==3) 
			{
				//Finding range of values
				String l=sc.next();
				String u=sc.next();
				findrange(l,u);
				
			}
			else if(op==4) 
			{
				//Inserting a record
				String vt=sc.next();
				String id=sc.next();
				String name=sc.next();
				name += sc.nextLine();
				String Depart=sc.next();
				int salary=sc.nextInt();
				
				BTree ob=new BTree(vt,id,name,Depart,salary);
				
				insertrecord(ob);
				System.out.println("Record inserted");
				myTree.insert(Integer.parseInt(id));
				ret.add(Integer.parseInt(id));
				indextofile(ret);
				printTree(myTree);
				
			}
			else if(op==5) 
			{	
				//Deleting a record
				String v=sc.next();
				deleterecord(v);
				System.out.println("Record deleted");
				myTree.delete(Integer.parseInt(v));
				int ii = ret.indexOf(Integer.parseInt(v));
				ret.remove(ii);
				 
				indextofile(ret);
				printTree(myTree);
				
			}
			else if(op==6) 
			{
				//Exiting the loop
				break;
			}
			else
			{
				System.out.println("Invalid Entry");
			}
		}
			
		
		
		
		
	}
}

class Node 
{
	
	
	BTree ob=new BTree();
	int order=ob.order;
	
	
	public boolean isOverflowed()
	{
		return keys.size() > order;
		
	}
		
	
	ArrayList<Integer> keys;
	

	public boolean isUnderflowed() 
	{
		if( keys.size() < BTree.getD())
			return true;
		return false;
	}
	
	boolean isLeafNode;
}

class LeafNode extends Node 
{
	

	public LeafNode(ArrayList<Integer> Keys)
	{
		keys = new ArrayList<Integer>();
		keys = Keys;
		isLeafNode = true;
	}
	
	LeafNode nextLeaf;
	
	public void insertSorted(int key)
	{
		if (key >= keys.get(0)) 
		{
			if (key <= keys.get(keys.size() - 1))
			{
				ListIterator<Integer> iterator = keys.listIterator();
				int flag =0;
				while(flag==0)
				{
					if (iterator.hasNext())
					{
						if (iterator.next() <= key)
						{
							
						}
						else
						{
							int pos = iterator.previousIndex();
							flag=1;
							keys.add(pos,key);
						}
					}
					else
					{
						flag=1;
					}
				}
			}
			else
			{
				keys.add(key);
			}
		}
		else
		{		
			keys.add(0, key);
		}
		 
	}
	

	public LeafNode(int firstKey) 
	{
		keys = new ArrayList<Integer>();
		keys.add(firstKey);
		isLeafNode = true;
	}


}

class IndexNode extends Node 
{

	 // m+1 children


	public IndexNode(ArrayList<Integer> newKeys, ArrayList<Node> newChildren)
	{
		
        keys = new ArrayList<Integer>();
		keys = newKeys;
		isLeafNode = false;
		children = new ArrayList<Node>();
		children = newChildren;

	}
	
	ArrayList<Node> children;
	public void insertSorted(Entry<Integer, Node> e, int index) 
	{
		int key = e.getKey();
		int aa;
		Node child = e.getValue();
		if (index < keys.size())
		{
			keys.add(index, key);
			children.add(index+1, child);
		}
		else
		{
			keys.add(key);
			children.add(child);
			
		}
	}
	
	public IndexNode(int key, Node child0, Node child1) 
	{
		children = new ArrayList<Node>();
		keys = new ArrayList<Integer>();
		isLeafNode = false;
		children.add(child0);
		keys.add(key);
		children.add(child1);
	}
}


	
	

