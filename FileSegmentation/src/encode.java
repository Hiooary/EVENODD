import java.io.IOException;


public class encode extends fileMatrix{

/******
 * 编码
 */	
@SuppressWarnings("unused")
public encode(String filePath,int bufSize){
		
		//分块
//		long length=getFileLength(filePath);
//		long block_num = 0;
//		int stripe_index,offset;
//		int i;
//		
//		char[] readBufs=new char[K];
//		//String readBufs=new String(a);
//		
//		char[] writeBufs=new char[R];
//		
//		if(length % BLOCK_SIZE != 0)
//		{
//			block_num=length / BLOCK_SIZE + 1;
//		}
//
//		long stripe_num = 0;
//		if(block_num % K != 0)
//		{
//			stripe_num=block_num / K + 1;
//		}
//		
//		//分配缓冲区
//		char[] buffer=new char[(K+R)* bufSize];
//		
////		for(i=0;i<K;i++)
////			readBufs[i] = buffer + i * bufSize;
////		for(i=0;i<R;i++)
////			writeBufs[i] = buffer + (K+i) * bufSize;
//		
//		//对每一个Stripe，生成校验块
//		for(stripe_index = 0;stripe_index<stripe_num;stripe_index++)
//		{
//			//建立系统和校验数据块对应的流
//			for(i=0;i<(K+R);i++)
//			{
//				if(i<K)
//				{
//					offset=(int) ((stripe_index * K + i ) * BLOCK_SIZE);
//					if(offset < length)
//					{
//						
//					}
//				}
//			}
//		}
//		
		
		
	}
	
@SuppressWarnings("null")
public static void main(String[] args) throws IOException
	{
	    int[][] buffer=split();//分好的块
	    
	    //System.out.print(buffer[0].length);
	    
	    int count=0;
	    int len=buffer[0].length;
	    if((len)%(M-1)!=0)
	    	count=(len)/(M-1)+1;
	    //System.out.print(count);
	    
	    int[][][] tempMemory=null;//将数据划分为矩阵
	    int t=0;
	    for(int k=0;k<count;k++)
	    {
		    for(int i=0;i<M-1;i++)
			    {
			    	for(int j=0;j<M;j++)
			    	{
			    		tempMemory[k][i][j]=buffer[i][t];
			    	}
			    	t++;
			    }
	    }
	    
//	    for(int k=0;k<2;k++)
//	    {
//		    for(int i=0;i<M-1;i++)
//			    {
//			    	for(int j=0;j<M;j++)
//			    	{
//			    		System.out.print(tempMemory[][i][j]+" ");
//			    	}
//			    	System.out.print("\n");
//			    }
//		    System.out.print("\n");
//	    }
	    
	    
//	     String filePath="./1.jpg";
//	     int bufSize;
		//encode();
		//decode();
		//merge();
	}

}
