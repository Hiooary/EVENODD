
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
			return (a+b)%b;
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
		for(int i=0;i<dataCache.length;i++)
		{
			s=s^dataCache[dataCache.length-1-i][i];
		}
		return s;
	}
	public static int[] horiExclusive_OR(int[][] dataCache) {
		// TODO Auto-generated method stub
		int l=dataCache.length;
		
		int horiExculsive[]=new int[l];
		for(int i=0;i<l;i++)
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
			int temp=commonFactor;
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
		int[][] temp=new int[m-1][m+2];
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
	private static int[][] addRow(int[][] dataCache,int[][] temp) {//增加一个0行
		// TODO Auto-generated method stub
		int i,j;
		for(i=0;i<dataCache.length;i++)
		{
			for(j=0;j<dataCache[i].length;j++)
			{
				temp[i][j]=dataCache[i][j];
			}
		}
		for(j=0;j<dataCache[0].length;j++)
		{
			temp[i][j]=0;
		}
		return temp;
	}
	
	
	
	
	public static int[][] encode(int[][] tempMemory)//编码
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
//			System.out.print(tempMatrix2[i]+" ");
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
		return temp;
	}
	public static void decode(int error1,int error2,int[][] tempMemory)//译码
	{
		
		if(error1 != -1 && error2 !=-1)////两个磁盘出错
		{
			//int[][] tempMemory = null;//按行存储的原始数据
			int[][] dataCache=null;
			int[] tempMatrix1=null,tempMatrix2=null;//行校验和对角线校验
			int[][] temp=null;
			//错误的是两个校验块
			if(error1 == tempMemory.length-2 && error2 == tempMemory.length-1)
			{
				//此情况类同于编码
				encode(tempMemory);
			}
			//错误的一个数据块和水平校验块,此时应该传入带有校验的矩阵
			else if((error1 >= 0 && error1 < tempMemory.length-2) && error2 == tempMemory.length-2)
			{
				dataCache=getColumnData(tempMemory);//按列存储
				//增加一个元素全为0的行
				temp=addRow(dataCache,temp);
				//计算s
				int s=0;
				int m=temp.length-2;
				s=temp[getMod((error1-1),m)][m+1];
				
				
				
				
				
				
				
			}
			//错误的一个数据块和对角线校验块
			else if((error1 >= 0 && error1 < tempMemory.length-2) && error2 == tempMemory.length-1)
			{}
			//错误的两个数据块
			else if((error1 >= 0 && error1 < tempMemory.length-2) && (error2 >= 0 && error2 < tempMemory.length-2))
			{}
			
			
			
		}
	}
	
	
	
	

	public static void main(String[] args)
	{
		int[][] tempMemory={{0,1,0,1},{0,1,1,1},{0,0,0,0},{1,0,0,1},{0,0,0,1}};//按照块存储的原数据
//		encode(tempMemory);//编码
		int error1 = 1,error2 = 5;//错误列数位置
		int[][] dataCache=null;
		
		//传入error1和error2的值
		//decode(error1,error2,tempMemory);
		
		dataCache=encode(tempMemory);//编码后的磁盘
		
		//破坏数据,此处是置0/////////////////////////////////////////////////
		for(int i=0;i<dataCache.length;i++)
		{
			dataCache[i][error1]=0;
			dataCache[i][error2]=0;
			//System.out.print(dataCache[i][error1]);
		}

		//破坏后的数据
//		for(int i=0;i<dataCache.length;i++)
//		{
//			for(int j=0;j<dataCache[i].length;j++)
//			{
//				System.out.print(dataCache[i][j]+" ");
//			} 
//			System.out.print("\n");
//		}
		
		int m=dataCache[0].length-2;
		int[][] temp=new int[m][m+2];
		//增加一个元素全为0的行
		temp=addRow(dataCache,temp);
//		for(int i=0;i<temp.length;i++)
//		{
//			for(int j=0;j<temp[i].length;j++)
//			{
//				System.out.print(temp[i][j]+" ");
//			} 
//			System.out.print("\n");
//		}
		
		int s=temp[getMod((error1-1),m)][m+1];
		for(int j=0;j<m;j++)
		{
			s=s^temp[getMod((error1-j-1),m)][j];
		}
		//System.out.print(s);
		//恢复error1
		
		for(int i=0;i<=3;i++)
		{
			temp[i][error1]=s^temp[getMod(error1-1,m)][m+1];;
			System.out.print(temp[i][error1]);
			for(int l=0;l<temp.length;l++)
			{	
				if(l!=error1)
				{		
					System.out.print(temp[getMod(i+error1-l,m)][l]);
					temp[i][error1]=temp[i][error1]^temp[getMod(i+error1-l,m)][l];				
			    }	
			}
			System.out.print("\n");
		}
		for(int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[i].length;j++)
			{
				System.out.print(temp[i][j]+" ");
			} 
			System.out.print("\n");
		}
//		
		
		
	}

	

}
