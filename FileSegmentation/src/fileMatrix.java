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


public class fileMatrix {
	
public static final int M = 5;//������Ϊ����
private static long BLOCK_SIZE = 0;//��Ĵ�С
protected static int[][] buffer=null;//��Ļ�����


/******
 * ���ļ����л��ֿ�
 * @return
 * @throws IOException 
 */
	public static int[][] split() throws IOException
	{
		int blockNo;
		String filePath="./1.jpg";		
		
		long length=getFileLength(filePath);
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
		return buffer;
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

}
