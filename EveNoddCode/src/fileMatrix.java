import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class fileMatrix {
	
	
private static final int BUFFER_SIZE = 512;
private static final int BLOCK_SIZE = 2048;

/******
 * 对文件进行划分块
 * @return
 * @throws FileNotFoundException 
 */
	public int split() throws FileNotFoundException
	{
		int blockNo,c1,c2,length,limit,count;
		String filePath="./2.jpg";		
		char[] a = new char[20];
		String str = new String(a); ;
		
		//对buffer进行内存分配
		int[] buffer=new int[BUFFER_SIZE];
//		if(buffer == null)//!!!!!!
//		{
//			System.out.print("Fail allocating buffer\n");
//			return -1;
//		}
		
		//以二进制打开文件
		DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		
		length=getFileLength(filePath);
		
		blockNo=0;
		
		while(true)
		{
			limit = length - BLOCK_SIZE * blockNo;//检测文件是否读完
			if(limit > BLOCK_SIZE)
				limit = BLOCK_SIZE;
			if(limit <=0 )
				break;
			
			str=String.format("2-%d.jpg", blockNo);//格式化str
			System.out.print(str);
			
			//以二进制写方式打开文件
			DataOutputStream fpw=new DataOutputStream(new FileOutputStream(filePath));
			count=0;
			while(count < limit)
			{
				//读取文件
				for(int i=0;i<buffer.length;i++)
					buffer[i]=fpr.readInt();
			    
				c1=fread(buffer,1,BUFFER_SIZE,fpr);
				if(c1<=0)
					break;
				c2=fwrite(buffer,1,c1,fpw);
				if(c2!=0)
					break;
				count +=c2;
			}
			
			fpr.close();
			fpw.close();
			blockNo++;
		}
		
		delete []buffer;
		return 0;
		
	}

private int getFileLength(String filePath) {
	// TODO Auto-generated method stub
	return 0;
}

}
