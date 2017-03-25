import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class fileMatrix extends evenodd{
	
public static int[][] buffer=null;//块的缓冲区	
public static final int M = 5;//块数，为素数
public static long BLOCK_SIZE;//块的大小
public static int length=0; 
public static int count=0;


/******
 * 对文件进行划分块
 * @return
 * @throws IOException 
 */
public static void split() throws IOException
	{
		int blockNo;
		String filePath="./1.jpg";		
	
		byte[] ReadBuffer=new byte[(int) BLOCK_SIZE];//一次性读取 BLOCK_SIZE 大小字节
		
		//以二进制打开文件
		DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		blockNo=0;
		try{
			while(true)
			{
				DataOutputStream fpw=new DataOutputStream(new FileOutputStream("./2-"+blockNo+".jpg"));
				fpr.read(ReadBuffer);
				fpw.write(ReadBuffer);;
				fpw.close();
				blockNo++;
				if(blockNo>=M)
				{
					break;
				}
			}
		}catch(EOFException e)
		{
			fpr.close();
		}				
	}

/******
 * 对文件进行组合
 * @return
 * @throws IOException 
 */	
public static void merge() throws FileNotFoundException,IOException
    {
    	
    	String filePath="./3.jpg";
    	int blockNo;
    	byte[] WriteBuffer=new byte[(int) BLOCK_SIZE];

    	DataOutputStream fpw=new DataOutputStream(new FileOutputStream(filePath));
    	blockNo=0;    	
    	try{
	    	for(int k=0;k<M;k++)
	    	{
	    		DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream("./2-"+blockNo+".jpg")));
	    		fpr.read(WriteBuffer);
				fpw.write(WriteBuffer);;   			
	    		fpr.close();
	    		blockNo++;	
	    		if(blockNo>=M)
	    		{
	    			break;
	    		}
	    	}
    	}catch(EOFException e){
    		fpw.close();
    	}
    }

/******
 * 求文件的长度
 * @return
 * 
 */	
public static long getFileLength(String filePath)
{
	// TODO Auto-generated method stub
	long ret = 0;
	FileChannel fc=null;
	try{
		File f=new File(filePath);
		if(f.exists() && f.isFile())
		{
			FileInputStream fis=new FileInputStream(f);
			fc=fis.getChannel();
			ret=fc.size();
		}
		else
		{
			System.out.print("file dones't exit or is not a file");
		}
	}catch(FileNotFoundException e)
	{
		
	}
	catch(IOException e){
		
	}
	finally{
		if(null!=fc)
		{
			try{
				fc.close();
			}catch(IOException e)
			{
				
			}
		}
	}
	return ret;
}

/******
 * 将文件转换为分块
 * @return
 * @throws IOException 
 * 
 */	
public static int[] FileToBlock(String filePath) throws IOException
{
	//矩阵的个数
	if((BLOCK_SIZE % (M-1))!=0)
		count= (int) (BLOCK_SIZE / (M-1) + 1);
	int[] file_buffer= new int[(int) BLOCK_SIZE];

	DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));	
	try{
		while(true)
		{
			for(int i=0;i<BLOCK_SIZE;i++)			
			{
				file_buffer[i]=fpr.readByte();
				//System.out.print(file_buffer[i]);
			}
			if(file_buffer.length==BLOCK_SIZE)
				break;  //不手动退出的话，java相关网络协议一直不关闭，一直在循环
		}
	}catch(EOFException e)
	{
		fpr.close();
	}
	//System.out.print("\n");
	return file_buffer;
}
/******
 * 将分块转换为文件
 * @return
 * @throws IOException 
 * 
 */
public static void BlockToFile(int[] tempMatrix,int blockNo ) throws IOException
{
	DataOutputStream fpw=new DataOutputStream(new FileOutputStream("./2-"+blockNo+".jpg"));
	for(int i=0;i<BLOCK_SIZE;i++)			
	{
		fpw.writeByte(tempMatrix[i]);
	}
	fpw.close();	
}

/******
 * 将分块转换为 (M-1)*M的矩阵
 * @return
 * @throws IOException 
 * 
 */
public static int[][][] BlockToMatrix(int[][] tempMatrix)
{
	int m=tempMatrix.length;
	int[][][] tempMemory=new int[count][m][M-1];//将数据划分为矩阵
	//display(tempMatrix);
    int t=0;
	for(int k=0;k<count;k++)
	{
	    for(int i=0;i<m;i++)
	    {
	    	t=(M-1)*k;
	    	for(int j=0;j<M-1;j++)
	    	{
	    		//对最后的一个矩阵补齐行数
	    		if(t>=BLOCK_SIZE)
	    		{
	    			tempMemory[k][i][j]=0;
	    		}
	    		else
	    			tempMemory[k][i][j]=tempMatrix[i][t];
	    		t++;
	    	}	
    		
		}
	 }
	
//	 for(int i=0;i<count;i++)
//      display(tempMemory[i]);
	 
	return tempMemory;
}

/******
 * 将矩阵转换为 块
 * @param m 
 * @return
 * @throws IOException 
 * 
 */
public static int[] MatrixToBlock(int[][][] dataCache, int m)
{
	int[] temp=new int[(int) BLOCK_SIZE];
	int k=0;
	int j=m;
	for(int c=0;c<count;c++)
	{
		for(int i=0;i<M-1;i++)
		{
				if(k>=BLOCK_SIZE)
					break;
				else
					temp[k]=dataCache[c][i][j];
				k++;
			//}
		}
	}

	return temp;
}

}





