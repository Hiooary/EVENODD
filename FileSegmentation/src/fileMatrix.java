import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class fileMatrix {
	
	
private static final int BUFFER_SIZE = 512;
private static final int BLOCK_SIZE = 2048;

/******
 * 对文件进行划分块
 * @return
 * @throws IOException 
 */
	public static void split() throws IOException
	{
		int blockNo;
		String filePath="./1.jpg";		
		
		//对buffer进行内存分配
		int[] buffer=new int[BUFFER_SIZE];
        //System.out.print(buffer.length);
		
		//以二进制打开文件
		DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		blockNo=0;
		while(fpr.read()!=-1)
		{
			DataOutputStream fpw=new DataOutputStream(new FileOutputStream("./2-"+blockNo+".jpg"));
			for(int i=0;i<BUFFER_SIZE;i++)			
			{
				buffer[i]=fpr.readInt();
				fpw.writeInt(buffer[i]);
			}
			fpw.close();
			blockNo++;
		}
		fpr.close();
	}
	
		
//		while(true)
//		{
//			limit = length - BLOCK_SIZE * blockNo;//检测文件是否读完
//			if(limit > BLOCK_SIZE)
//				limit = BLOCK_SIZE;
//			if(limit <=0 )
//				break;
//			
//			System.out.print("2-"+blockNo+".jpg");
//			//str=String.format("2-%d.jpg", blockNo);//格式化str
//			//System.out.print(str);
//			
//			//以二进制写方式打开文件
//			//DataOutputStream fpw=new DataOutputStream(new FileOutputStream(filePath));
//			
//			count=0;
//			while(count < limit)
//			{
//				//一次性读取  BLOCK_SIZE * 1 字节的数据
//				for(int i=0;i<buffer.length;i++)
//					buffer[i]=fpr.readInt();
//				for(int i=0;i<buffer.length;i++)
//					fpw.writeInt(buffer[i]);
//				count += BUFFER_SIZE;
//			}
//			fpw.close();
//			blockNo++;
//		}
//		fpr.close();

/******
 * 对文件进行组合
 * @return
 * @throws IOException 
 */	
    @SuppressWarnings("resource")
	public static void merge() throws FileNotFoundException,IOException
    {
    	int blockNo;
    	int[] buffer=new int[BUFFER_SIZE];
    	String filePath="./3.jpg";
   
    	DataOutputStream fpw=new DataOutputStream(new FileOutputStream(filePath));
    	blockNo=0;    	
    	for(int k=0;k<6;k++)
    	{
    		DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream("./2-"+blockNo+".jpg")));
    		//System.out.print(getFileLength("./2-"+blockNo+".jpg"));
    		for(int j=0;j<BUFFER_SIZE;j++)
    		{
    			buffer[j]=fpr.readInt();
				fpw.writeInt(buffer[j]);   			
    		}
//    		while(fpr.read()!=-1)
//    		{
////    			buffer[i]=fpr.readInt();
////				fpw.writeInt(buffer[i]);
//				i++;
//    		}			
    		//System.out.print(i);
    		fpr.close();
    		blockNo++;	
    	}
		fpw.close();
    }

private static long getFileLength(String filePath)
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

public static void main(String[] args) throws IOException
{
	//split();
	//encode();
	//decode();
	//merge();合并后的图片无法查看
}
}
