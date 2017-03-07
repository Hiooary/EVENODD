
public class evenodd {

	//	public static int getColumn(int[][] dataCache)//获取列数
//	{
//		int t=dataCache[0].length;
//		return t;
//		
//	}
//	public static int getRow(int[][] dataCache)//获取行数
//	{
//		int l=dataCache.length;
//		return l;	
//	}
	public static int getMod(int a,int b)//有限域上的模运算
	{
		if(a>=0)
		{
			return a%b;
		}
		else
			return b+a;
	}
	
	public static int[][] getColumnData(int[][] tempMemory){//从对象数组中获取某列的方法
		int m=tempMemory.length;
		int l=tempMemory[0].length;
		int[][] temp=new int[l][m];
		for(int i=0;i<l;i++)
		{
			for(int j=0;j<m;j++)
			{
				temp[i][j]=tempMemory[j][i];
			}
		}
		return temp;	
	}
	
    public static int getCommonFactor(int[][] dataCache)
	{
		int s=0;
		for(int i=1;i<dataCache.length;i++)
		{
			s=s^dataCache[dataCache.length-1-i][i];
		}
		return s;
	}
	public static int[] horiExclusive_OR(int[][] dataCache) {
		// TODO Auto-generated method stub
		int l=dataCache.length;
		int horiExculsive[]=new int[l];
		for(int i=0;i < l;i++)
		{
			int temp=0;
			for(int j=0;j<dataCache[i].length;j++)
			{
				temp=temp^dataCache[i][j];
			}
			horiExculsive[i]=temp;
		}
		return horiExculsive;
	}
	public static int[] diagExclusive_OR(int[][] dataCache, int commonFactor) {
		// TODO Auto-generated method stub
		int l=dataCache.length;
		int diagExclusive[] = new int[l];
		for(int i=0;i<l;i++)
		{
			int temp=0;
			temp=temp^commonFactor;
			for(int j=0; j<dataCache[i].length;j++)
			{
				int t=getMod((i-j),dataCache[i].length);
				if(t>=l)
				{
					continue;
				}
				temp=temp^dataCache[t][j];
				
			}
			diagExclusive[i]=temp;
		}
		return diagExclusive;
	}
	public static int[][] matrixTransposition(int[] tempMatrix1,int[] tempMatrix2, int[][] dataCache) {//将获得的校验矩阵存储
		// TODO Auto-generated method stub
		int m=dataCache[0].length;
		int[][] temp=new int[m][m+2];
		for(int i=0;i<dataCache.length;i++)
		{
			for(int j=0;j<m;j++)
			{
				temp[i][j]=dataCache[i][j];
			}
		}
		for(int i=0;i<tempMatrix1.length;i++)
		{
			temp[i][m]=tempMatrix1[i];
			temp[i][m+1]=tempMatrix2[i];
		}
		return temp;
	}
	public static void encode(int[][] tempMemory)//编码
	{
		int dataCache[][] = null;//矩阵形式存储 {{1,0,1,1,0},{0,1,1,0,0},{1,1,0,0,0},{0,1,0,1,1}};
		int[] tempMatrix1=null,tempMatrix2=null;//行校验和对角线校验
		int[][] temp=null;//存储校验数据后的盘符
		int s;//奇偶校验符号
		
//		System.out.print("输出原数据："+"\n");
//		for(int i=0;i<tempMemory.length;i++)
//		{
//			for(int j=0;j<tempMemory[i].length;j++)
//			{
//				System.out.print(tempMemory[i][j]+" ");
//			}
//			System.out.print(" \n");
//		}
//		System.out.print("输出转存数据："+"\n");
		dataCache=getColumnData(tempMemory);
//		for(int i=0;i<dataCache.length;i++)
//		{
//			for(int j=0;j<dataCache[i].length;j++)
//			{
//				System.out.print(dataCache[i][j]+" ");
//			}
//			System.out.print(" \n");
//		}
		
		s=getCommonFactor(dataCache);
		//System.out.print(s);//求符号s
		
		//获取水平校验
		tempMatrix1=horiExclusive_OR(dataCache);
//		for(int i=0;i<tempMatrix1.length;i++){
//			System.out.print(tempMatrix1[i]);
//		}
		//获取对角线校验
		tempMatrix2=diagExclusive_OR(dataCache,s);
//		for(int i=0;i<tempMatrix2.length;i++){
//			//System.out.print(tempMatrix2[i]+" ");
//		}
		
		//存储得到的校验盘
		temp=matrixTransposition(tempMatrix1,tempMatrix2,dataCache);
//		for(int i=0;i<temp.length;i++)
//		{
//			for(int j=0;j<temp[i].length;j++)
//			{
//				System.out.print(temp[i][j]+" ");
//			}
//			System.out.print(" \n");
//		}
		
	}
	public static void decode()//译码
	{
		int error1,error2;
		if(error1 != -1 && error2 !=-1)////两个磁盘出错
		{
			
		}
		
	}
	
	
	
	public static void main(String[] args)
	{
		int[][] tempMemory={{1,0,1,0},{0,1,1,1},{1,1,0,0},{1,0,0,1},{0,0,0,1}};//按照块存储的原数据
		encode(tempMemory);//编码
		decode();
	}

	

}
