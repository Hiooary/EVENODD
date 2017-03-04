
public class decode {
	
	
	protected void decode(){
		if(error1 != -1 && errer2 != -1){//有两个数据块出错
			int [][] dataCache,tempMatrix1,tempMatrix2;
			pictureInComp[] corectObj;//暂存正确数据块的数据
			/****/ Object[] tempMemory;/****/
			tempMatrix1=new int[tempMemory[0].getColumn()][tempMemory.getRow()];//暂存运算过程中水平校验位的数据值的转置数据
			tempMatrix2=new int[tempMemory[0].getColumn()][tempMemory.getRow()];//暂存运算过程中对角线校验位的数据值的转置数据
			int[][] temp = new int[tempMemory[0].getRow()][tempMemory.getColumn()];//充当数组转置时的临时变量
			if(error1 == tempMemory.length - 2 && error2 == tempMemory.length - 1){//两个校验数据块出错
				corectObj=new pictureInComp[tempMemory.length - 2 ];
				System.arraycopy(tempMemory0, 0, corectObj,0,tempMemory.length-2);//将正确的对象数组拷贝到corectObj中
				dataCache = new int[tempMemory[0].getRow()][corectOnj.length];
				for(int i=0;i<corectObj.length;i++)
				{
					dataCache = super.getColumnData(corectObj,i);.
				}
			}
		}
		
		
	}

}
