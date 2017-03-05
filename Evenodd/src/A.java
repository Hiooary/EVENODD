
public class A {
	public int[][] getColumnData(getInfo[] tempMemory,int i){//从对象数组中获取某列的方法
		return null;
	}
	public int[][] getColumnData(pictureInComp[] corectObj,int i){
		return null;
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
		int horiExculsive[] = null;
		for(int i=0;i<dataCache.length;i++)
		{
			int temp=0;
			for(int j=0;j<dataCache[i].length-1;j++)
			{
				temp=temp^dataCache[i][j];
			}
			horiExculsive[i]=temp;
		}
		return horiExculsive;
	}
	public int[] diagExclusive_OR(int[][] dataCache, int commonFactor) {
		// TODO Auto-generated method stub
		int diagExclusive[] = null;
		for(int i=0;i<dataCache.length;i++)
		{
			int temp=0;
			temp=temp^commonFactor;
			for(int j=0; j< dataCache[i].length-1;j++)
			{
				temp=temp^dataCache[(i-j)%dataCache[i].length-1][j];
			}
			diagExclusive[i]=temp;
		}
		return diagExclusive;
	}

	public void matrixTransposition(int[][] tempMatrix, int[][] temp) {
		// TODO Auto-generated method stub
		
	}
	public int[][] addRow(int[][] tempCache) {
		// TODO Auto-generated method stub
		return null;
	}


}
