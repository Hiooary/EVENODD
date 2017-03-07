
public class A {
	public int[][] getColumnData(pictureInComp[] tempMemory,int i){//从对象数组中获取某列的方法
		int[][] a;
		a=tempMemory[i].dataCache;
		return a;
	}
//	public int[][] getColumnData(pictureInComp[] corectObj,int i){
//		return null;
//	}
	public static int getMod(int a,int b)//有限域上的模运算,不规范。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
	{
		if(a>=0)
		{
			return a%b;
		}
		else
			return b+a;
	}
	public int getCommonFactor(int[][] dataCache) {//获取对角线符号s
		// TODO Auto-generated method stub
		int s=0;
		for(int i=1;i<dataCache.length;i++)
		{
			s=s^dataCache[dataCache.length-1-i][i];
		}
		return s;
	}
	public int[] horiExclusive_OR(int[][] dataCache) {
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
	public int[] diagExclusive_OR(int[][] dataCache, int commonFactor) {
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

	public int[][] matrixTransposition(int[][] tempMatrix, int[][] temp) {
		// TODO Auto-generated method stub
		for(int i=0;i<temp[i].length;i++)
		{
			for(int j = 0;j<temp.length;j++)
			{
				temp[i][j]=tempMatrix[j][i];
			}
		}
		return temp;
		
	}
	public int[][] addRow(int[][] tempCache) {
		// TODO Auto-generated method stub
		return null;
	}


}
