
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
					dataCache = super.getColumnData(corectObj,i);//获得对象元素的每一列，组成一个数组
					tempMatrix1[i]=super.horiExclusive_OR(dataCache);//修复数据块error
					tempMatrix2[i]=super.diagExclusive_OR(dataCache,encoding.getCommonFactor(dataCache));//修复数据块error2
				}
				//将修复的数据存入对象
				super.matrixTransposition(tempMatrix1,temp);
				tempMemory[error1].read(temp);
				super.matrixTransposition(tempMatrix2.temp);
				tempMemory[error2].read(temp);
			}
			else if(error1 >= 0 && error1 < tempMemory.length - 2) &&  error2 == tempMemory.length - 2){//原数据块和水平校验数据块的数据出错
				int s=0;
				int m=tempMemory.length - 2;
				tempMatrix1 = new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];
				int[][] tempCache = new int[tempMemory[0].getRow()][tempMemory.length];
				dataCache = new int[tempMemory[0].getRow()+1][tempMemory.length];//存储增加一行的数据
				for(int i=0;i<tempMemory[0].getColumnData;i++){
					tempCache = super.getColumnData(tempMemory,i));//获取对象数组中每一个元素对应列的数据
					dataCache=super.addRow(tempCache);//增加一行
					//计算公共因子
					
				}	
			}
		}
		
		
	}

}
