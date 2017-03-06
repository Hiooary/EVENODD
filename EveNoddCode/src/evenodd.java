
public class evenodd {
	static int m=5;
	public static void main(String[] args)
	{
		int[][] tempArray=new int[m][m+2];
		int[] tempMatrix1=null,tempMatrix2=null;
//		dataCache=null;
		int dataCache[][] = {{1,0,1,1,0},{0,1,1,0,0},{1,1,0,0,0},{0,1,0,1,1}};
		tempArray=dataCache;
		int s;
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
		
	}
	public static int getMod(int a,int b)//有限域上的模运算
	{
		if(a>=0)
		{
			return a%b;
		}
		else
			return b+a;
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
	public void matrixTransposition(int[][] tempMatrix, int[][] temp) {
		// TODO Auto-generated method stub
		for(int i=0;i<tempMatrix.length;i++)
		{
			for(int j=0;j<tempMatrix[i].length;j++)
			{
				temp[i][j]=tempMatrix[j][i];
			}
		}
		
	}
	public void encode(int[][] dataCache)
	{
		
	}
	

}
