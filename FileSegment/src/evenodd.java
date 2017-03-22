
/******************************
 *******算法的一些基本函数
 *******
 */  
public class evenodd {
	private static final int M = 5;//块数，为素数
	
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
    public static int getCommonFactor(int[][] dataCache)//求s
	{
		int s=0;
		int m=M;
		for(int i=1;i<m;i++)
		{
			s=s^dataCache[getMod(m-1-i, m)][i];
		}
		return s;
	}
	public static int[] horiExclusive_OR(int[][] dataCache) {//水平校验
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
	public static int[] diagExclusive_OR(int[][] dataCache, int commonFactor) {//对角线校验
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
	public static int[][] addRow(int[][] dataCache,int[][] temp) {//增加一个0行
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
	public static void display(int[][] temp)//显示
	{
		for(int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[i].length;j++)
			{
				System.out.printf("%6d",temp[i][j]);
			}
			System.out.print(" \n");
		}
		System.out.print(" \n");
	}	
/******
 *破坏数据,此处置为0
 */    
	public static int[][][] Destroy(int error1,int error2,int[][][] dataCache)
	{
		int count=dataCache.length;
		for(int k=0;k<count;k++)
		{
			for(int i=0;i<dataCache[0].length;i++)
			{
				dataCache[k][i][error1]=0;
				dataCache[k][i][error2]=0;
			}
		}
		return dataCache;
	}
	
	
	//	public static int[][] encode(int[][] tempMemory)//编码
//	{
//		int dataCache[][] = null;//矩阵形式存储 {{1,0,1,1,0},{0,1,1,0,0},{1,1,0,0,0},{0,1,0,1,1}};
//		int[] tempMatrix1=null,tempMatrix2=null;//行校验和对角线校验
//		int[][] temp=null;//存储校验数据后的盘符
//		int s;//奇偶校验符号
//		
////		System.out.print("输出原数据："+"\n");
////      display(tempMemory);
//		
////		System.out.print("输出转存数据："+"\n");
//		dataCache=getColumnData(tempMemory);
//		//display(dataCache);
//		
//		s=getCommonFactor(dataCache);
//		//System.out.print(s);//求符号s
//		
//		//获取水平校验
//		tempMatrix1=horiExclusive_OR(dataCache);
////		for(int i=0;i<tempMatrix1.length;i++){
////			System.out.print(tempMatrix1[i]);
////		}
//		//获取对角线校验
//		tempMatrix2=diagExclusive_OR(dataCache,s);
////		for(int i=0;i<tempMatrix2.length;i++){
////			System.out.print(tempMatrix2[i]+" ");
////		}
//		
//		//存储得到的校验盘
//		temp=matrixTransposition(tempMatrix1,tempMatrix2,dataCache);
//		System.out.print("存储得到的校验盘\n");
//		display(temp);
//		return temp;
//	}
//	public static void decode(int error1,int error2,int[][] dataCache)//译码
//	{	
//		int m=dataCache[0].length-2;
//		if(error1 != -1 && error2 !=-1)////两个磁盘出错
//		{
//			//错误的是两个校验块
//			if(error1 == dataCache[0].length-2 && error2 == dataCache[0].length-1)
//			{
//				//此情况类同于编码
//				//破坏数据,此处是置0/////////////////////////////////////////////////
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][error1]=0;
//					dataCache[i][error2]=0;
//				}
//				//破坏后的数据
//				System.out.print("破坏后的数据\n");
//				display(dataCache);
//				
//				int[][] tempArray=new int[m-1][m];
//				for(int i=0;i<tempArray.length;i++)
//				{
//					for(int j=0;j<tempArray[i].length;j++)
//					{
//						tempArray[i][j]=dataCache[i][j];
//					}
//				}
//				
//				//进行编码
//				int s=getCommonFactor(tempArray);
//				int[] tempMatrix1=horiExclusive_OR(tempArray);
//				int[] tempMatrix2=diagExclusive_OR(tempArray,s);
//				
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][m]=tempMatrix1[i];
//					dataCache[i][m+1]=tempMatrix2[i];
//				}
//				//输出				
//				System.out.print("修复后的数据\n");
//				display(dataCache);
//			}
//			//错误的一个数据块和水平校验块,此时应该传入带有校验的矩阵
//			else if((error1 >= 0 && error1 < dataCache[0].length-2) && error2 == dataCache[0].length-2)
//			{				
//				//破坏数据,此处是置0/////////////////////////////////////////////////
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][error1]=0;
//					dataCache[i][error2]=0;
//				}
//				//破坏后的数据
//				System.out.print("破坏后的数据\n");
//				display(dataCache);
//				
//				int[][] temp=new int[m][m+2];
//				//增加一个元素全为0的行
//				temp=addRow(dataCache,temp);
////				display(temp);
//				
//				int s=temp[getMod((error1-1),m)][m+1];
//				for(int j=0;j<m;j++)
//				{
//					s=s^temp[getMod((error1-j-1),m)][j];
//				}
//				//System.out.print(s);
//				//恢复error1,公式有误，无法恢复,修改公式后，可以恢复/////////////////////////////////////////////////////////////////////////////
//				for(int k=0;k<temp.length-1;k++)
//				{
//					temp[k][error1]=s^temp[getMod(error1+k,m)][m+1];
//					for(int l=0;l<temp.length;l++)
//					{	
//						if(l!=error1)
//						{		
//							temp[k][error1]=temp[k][error1]^temp[getMod(k+error1-l,m)][l];	
//							dataCache[k][error1]=temp[k][error1];
//					    }	
//					}
//				}			
//				//恢复error2,用水平校验公式
//				int[][] tempArray=new int[m-1][m];
//				for(int i=0;i<tempArray.length;i++)
//				{
//					for(int j=0;j<tempArray[i].length;j++)
//					{
//						tempArray[i][j]=temp[i][j];
//					}
//				}
//			    int[] tempMatrix1=horiExclusive_OR(tempArray);
//			    for(int i=0;i<temp.length-1;i++)
//			    {
//			    	//temp[i][error2]=tempMatrix1[i];
//			    	dataCache[i][error2]=tempMatrix1[i];
//			    }
//			    //输出
//			    System.out.print("修复后的数据\n");
//			    display(dataCache);
//				
//			}
//			//错误的一个数据块和对角线校验块
//			else if((error1 >= 0 && error1 < dataCache[0].length-2) && error2 == dataCache[0].length-1)
//			{
//				//破坏数据,此处是置0/////////////////////////////////////////////////
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][error1]=0;
//					dataCache[i][error2]=0;
//				}
//				//破坏后的数据
//				System.out.print("破坏后的数据\n");
//				display(dataCache);
//				//根据水平校验公式恢复error1
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][error1]=0;
//					for(int j=0;j<dataCache[i].length-1;j++)
//					{
//						if(j != error1)
//						  dataCache[i][error1]=dataCache[i][error1]^dataCache[i][j];
//					}
//				}
//				//根据对角线公式恢复error2
//				int[][] tempArray=new int[m-1][m];
//				for(int i=0;i<tempArray.length;i++)
//				{
//					for(int j=0;j<tempArray[i].length;j++)
//					{
//						tempArray[i][j]=dataCache[i][j];
//					}
//				}
//				int s=getCommonFactor(tempArray);
//				int[] tempMatrix2=diagExclusive_OR(tempArray,s);
//				 for(int i=0;i<tempMatrix2.length;i++)
//				    {
//				    	//temp[i][error2]=tempMatrix1[i];
//				    	dataCache[i][error2]=tempMatrix2[i];
//				    }
//				//输出
//				 System.out.print("修复后的数据\n");
//				 display(dataCache);
//				
//			}
//			//错误的两个数据块
//			else if((error1 >= 0 && error1 < dataCache[0].length-2) && (error2 >= 0 && error2 < dataCache[0].length-2))
//			{
//				//破坏数据,此处是置0/////////////////////////////////////////////////
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][error1]=0;
//					dataCache[i][error2]=0;
//				}
//				//破坏后的数据
//				System.out.print("破坏后的数据\n");
//				display(dataCache);
//				
//				//增加0行
//				int[][] temp=new int[m][m+2];
//				//增加一个元素全为0的行
//				temp=addRow(dataCache,temp);
//				int s=0;//奇偶符号
//				for(int l=0;l<m-1;l++)
//				{
//					s=s^dataCache[l][m]^dataCache[l][m+1];
//				}
//				//System.out.print(s);
//				//寻找水平综合征
//				int[] S0 = new int[m];
//				for(int u=0;u<m;u++)
//				{
//					S0[u]=0;
//					for(int l=0;l<=m;l++)
//					{
//						if(l != error1 && l!= error2)
//							S0[u]=S0[u]^temp[u][l];
//					}
//				}
////				for(int i=0;i<S0.length;i++)
////				{
////					System.out.print(S0[i]);
////				}
//				int[] S1=new int[m];//对角线综合征
//				for(int u=0;u<m;u++)
//				{
//					S1[u]=s^temp[u][m+1];
//					for(int l=0;l<m;l++)
//					{
//						if(l != error1 && l!= error2){	
//							S1[u]=S1[u]^temp[getMod(u-l,m)][l];
//						}
//					}
//				}
////				for(int i=0;i<S1.length;i++)
////				{
////					System.out.print(S1[i]);
////				}
//				//通过步骤计算
//				s=getMod((-(error2-error1)-1),m);
//				while(s!=m-1)
//				{
//					temp[s][error2]=S1[getMod(error2+s,m)]^temp[getMod(s+(error2-error1),m)][error1];
//					dataCache[s][error2]=temp[s][error2];
//					temp[s][error1]=S0[s]^temp[s][error2];
//					dataCache[s][error1]=temp[s][error1];
//					s=getMod(s-(error2-error1),m);
//				}
//				
//				System.out.print("修复后的数据\n");
//				display(dataCache);
//			}
//		}
//		//只有一个数据块出错
//		else if(error2 == -1 && error1 != -1)
//		{
//			
//		}
//		else
//		{//错误数据块不能找到
//			System.out.print("error:fail to find the error disk!!");
//			System.exit(0);
//		
//	    }
//	}
		
//	public static void main(String[] args)
//	{
//		int[][] tempMemory={{0,1,0,1},{0,1,1,1},{0,0,0,0},{1,0,0,1},{0,0,0,1}};//按照块存储的原数据
//		
////		encode(tempMemory);//编码
//		
//		int error1 = -1,error2 = -1;//错误列数位置
//		//传入error1和error2的值
//		int[][] dataCache=null;
//		dataCache=encode(tempMemory);//编码后的磁盘
//		decode(error1,error2,dataCache);
//
//	}

}
