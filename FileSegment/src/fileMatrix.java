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
	
private static int[][] buffer=null;//��Ļ�����	
public static final int M = 5;//������Ϊ����
public static long BLOCK_SIZE = 0;//��Ĵ�С
public static int length=0; 
public static int count=0;


/******
 * ���ļ����л��ֿ�
 * @return
 * @throws IOException 
 */
public static void split() throws IOException
	{
		int blockNo;
		String filePath="./1.jpg";		
		
		length=(int) getFileLength(filePath);
		if((length % M)!=0)
			BLOCK_SIZE = (length / M) + 1;
		//System.out.print(buffer_size);
		
		//��buffer�����ڴ����
		int[][] buffer=new int[M][(int) BLOCK_SIZE];
		
		//�Զ����ƴ��ļ�
		DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		blockNo=0;
		try{
			while(true)
			{
				DataOutputStream fpw=new DataOutputStream(new FileOutputStream("./2-"+blockNo+".jpg"));
				
				for(int i=0;i<BLOCK_SIZE;i++)			
				{
					buffer[blockNo][i]=fpr.readByte();
					fpw.writeByte((buffer[blockNo][i]));
				}
				fpw.close();
				blockNo++;
			}
		}catch(EOFException e)
		{
			fpr.close();
		}		
		//return buffer;  //��Ӧ�ô���buffer,Ӧ���ں��������Լ���ȡ�ļ����
	}

/******
 * ���ļ��������
 * @return
 * @throws IOException 
 */	
    @SuppressWarnings("resource")
public static void merge() throws FileNotFoundException,IOException
    {
    	int blockNo;

    	BLOCK_SIZE=getFileLength("./2-0.jpg");
    	int[][] buffer=new int[M][(int) BLOCK_SIZE];
    	String filePath="./3.jpg";
   
    	DataOutputStream fpw=new DataOutputStream(new FileOutputStream(filePath));
    	blockNo=0;    	
    	try{
	    	for(int k=0;k<M;k++)/////////////////////////////////////////////////////////
	    	{
	    		DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream("./2-"+blockNo+".jpg")));
	    		//System.out.print(getFileLength("./2-"+blockNo+".jpg"));
	    		for(int i=0;i<BLOCK_SIZE;i++)
	    		{
	    			buffer[blockNo][i]=fpr.readByte();
					fpw.writeByte(buffer[blockNo][i]);   			
	    		}
	    		fpr.close();
	    		blockNo++;	
	    	}
    	}catch(EOFException e){
    		fpw.close();
    	}
    }

/******
 * ���ļ��ĳ���
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
 * ���ļ�ת��Ϊ�ֿ�
 * @return
 * @throws IOException 
 * 
 */	
public static int[] FileToBlock(String filePath) throws IOException
{
	//����ĸ���
	if((BLOCK_SIZE% (M-1))!=0)
		count= (int) (BLOCK_SIZE / (M-1) + 1);
	int[] file_buffer= new int[(int) BLOCK_SIZE];
	DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));	
	try{
		while(true)
		{
			for(int i=0;i<BLOCK_SIZE;i++)			
			{
					file_buffer[i]=fpr.readByte();
			}
		}
	}catch(EOFException e)
	{
		fpr.close();
	}		
//	for(int i=0;i<file_buffer.length;i++)
//	 System.out.print(file_buffer[i]);
	
	return file_buffer;
}
/******
 * ���ֿ�ת��Ϊ�ļ�
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
 * ���ֿ�ת��Ϊ (M-1)*M�ľ���
 * @return
 * @throws IOException 
 * 
 */
public static int[][][] BlockToMatrix(int[][] tempMatrix)
{
	int[][][] tempMemory=new int[count][M+2][M-1];//�����ݻ���Ϊ����
    int t=0;
	for(int k=0;k < count;k++)
	{
	    for(int i=0;i<M+2;i++)
	    {
	    	for(int j=0;j<M-1;j++)
	    	{
	    		//System.out.print(buffer[i][t]);
	    		//������һ������������
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
	return tempMemory;
}

/******
 * ������ת��Ϊ ��
 * @return
 * @throws IOException 
 * 
 */
public static int[] MatrixToBlock(int[][] tempMatrix)
{
	int[] temp=new int[(int) BLOCK_SIZE];
	int k=0;
	for(int i=0;i<count;i++)
	{
		for(int j=0;j<M-1;j++)
		{
			if(k>=BLOCK_SIZE)
				break;
			temp[k]=tempMatrix[i][j];
			k++;
		}
	}
	return temp;
}

}





