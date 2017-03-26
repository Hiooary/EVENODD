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
	
//public static int[][] buffer=null;//块的缓冲区	
public static final int M = 5;//块数，为素数
public static long DISK_SIZE;//一个文件碎块的大小
public static int length=0; 
public static int count=0;//块的大小


/******
 * 对文件进行划分块
 * @return
 * @throws IOException 
 */
public static void split() throws IOException
	{
		int blockNo;
		String filePath="./1.jpg";		
	
		byte[] ReadBuffer=new byte[(int) DISK_SIZE];//一次性读取 BLOCK_SIZE 大小字节
		
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
    	byte[] WriteBuffer=new byte[(int) DISK_SIZE];

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
public static byte[] FileToBlock(String filePath) throws IOException
{
	//矩阵的个数
	if((DISK_SIZE % (M-1))!=0)
		count= (int) (DISK_SIZE / (M-1) + 1);//块的大小
	else
		count=(int)(DISK_SIZE)/(M-1);

	byte[] file_buffer= new byte[(int) DISK_SIZE];
	//int[] file_block=new int[(int) DISK_SIZE];
	DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));	
	try{
		while(true)
		{
			fpr.read(file_buffer);
			if(file_buffer.length==DISK_SIZE)
				break;  //不手动退出的话，java相关网络协议一直不关闭，一直在循环
		}
	}catch(EOFException e)
	{
		fpr.close();
	}
	
//	for(int i=0;i<DISK_SIZE;i++)
//	{
//		//byte ->int
//		file_block[i]=byteToInt(file_buffer,i*4);
//	}
	
	return file_buffer;
}
/******
 * 将分块转换为文件
 * @return
 * @throws IOException 
 * 
 */
public static void BlockToFile(byte[] tempMatrix,int blockNo ) throws IOException
{
	//int->byte
//	byte[] file_buffer=new byte[(int) DISK_SIZE];
//	for(int i=0;i<DISK_SIZE;i++)
//	{
//		file_buffer=intToByte(tempMatrix[i]);
//	}
	DataOutputStream fpw=new DataOutputStream(new FileOutputStream("./2-"+blockNo+".jpg"));
	fpw.write(tempMatrix);;
	fpw.close();	
	
}

/******
 * 将分块转换为 (M-1)*M的矩阵
 * @return
 * @throws IOException 
 * 
 */
public static byte[][][] BlockToMatrix(byte[][] tempMatrix)
{
	int m=tempMatrix.length;
	byte[][][] tempMemory=new byte[count][m][M-1];//将数据划分为矩阵
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
	    		if(t>=DISK_SIZE)
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
public static byte[] MatrixToBlock(byte[][][] dataCache, int m)
{
	byte[] temp=new byte[(int) DISK_SIZE];
	int k=0;
	int j=m;
	for(int c=0;c<count;c++)
	{
		for(int i=0;i<M-1;i++)
		{
				if(k>=DISK_SIZE)
					break;
				else
					temp[k]=dataCache[c][i][j];
				k++;
		}
	}

	return temp;
}

public static byte[] intToByte(int integer)
{
	int byteNum=(40 -Integer.numberOfLeadingZeros (integer < 0 ? ~integer : integer))/ 8;
	byte[] byteArray=new byte[4];
	for(int i=0;i<byteNum;i++)
	{
		byteArray[3-i]=(byte)(integer>>>(i*8));
	}
	return byteArray;
}
public static int byteToInt(byte[] b,int offset)
{
	int value=0;
	for(int i=0;i<4;i++)
	{
		if(offset>=b.length)
			break;
		else
		{
			int shift=(4-1-i)*8;
			value+=(b[i+offset]& 0x000000FF)<<shift;
		}
	
	}
	return value;
	}


}





