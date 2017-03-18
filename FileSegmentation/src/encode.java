import java.io.IOException;


public class encode extends fileMatrix{
	
public static final int K=2;
public static final int R=2;
public static final int N=0;/////////////////(1,K+1);

/******
 * 编码
 * 
 *
 */	
@SuppressWarnings("unused")
public encode(String filePath,int bufSize){
		
		//分块
		long length=getFileLength(filePath);
		long block_num = 0;
		int stripe_index,offset;
		int i;
		
		char[] readBufs=new char[K];
		//String readBufs=new String(a);
		
		char[] writeBufs=new char[R];
		
		if(length % BLOCK_SIZE != 0)
		{
			block_num=length / BLOCK_SIZE + 1;
		}

		long stripe_num = 0;
		if(block_num % K != 0)
		{
			stripe_num=block_num / K + 1;
		}
		
		//分配缓冲区
		char[] buffer=new char[(K+R)* bufSize];
		
//		for(i=0;i<K;i++)
//			readBufs[i] = buffer + i * bufSize;
//		for(i=0;i<R;i++)
//			writeBufs[i] = buffer + (K+i) * bufSize;
		
		//对每一个Stripe，生成校验块
		for(stripe_index = 0;stripe_index<stripe_num;stripe_index++)
		{
			//建立系统和校验数据块对应的流
			for(i=0;i<(K+R);i++)
			{
				if(i<K)
				{
					offset=(int) ((stripe_index * K + i ) * BLOCK_SIZE);
					if(offset < length)
					{
						
					}
				}
			}
		}
		
		
		
	}
	
public static void main(String[] args) throws IOException
	{
		//split();
//	     String filePath="./1.jpg";
//	     int bufSize;
		//encode();
		//decode();
		merge();
	}

}
