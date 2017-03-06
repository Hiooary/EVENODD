

public class encode extends A{

	
	protected encode()
	{
		int[][] dataCache=null,tempMatrix1=null,tempMatrix2=null;
		pictureInComp[] tempMemory = null;
		A encoding = null;
		tempMatrix1=new int[tempMemory[0].getColumn()][tempMemory[0].getRow()];//暂存运算过程中水平校验位的数据值的转置数据
		tempMatrix2=new int[tempMemory[0].getColumn()][tempMemory[0].getRow()];//暂存运算过程中对角线校验位的数据值的转置数据
        int[][] temp= new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];//充当数组转置时的临时变量
	    dataCache=new int[tempMemory[0].getRow()][tempMemory.length];
	    for(int i = 0; i < tempMemory.length; i++)
	    {
	    	dataCache = super.getColumnData(tempMemory,i);//获得对象元素中的数据中的每一列，组成一个数组
	    	tempMatrix1[i]=super.horiExclusive_OR(dataCache);//获得水平校验（公式（2））
			tempMatrix2[i]=super.diagExclusive_OR(dataCache,encoding.getCommonFactor(dataCache));//获得对角线校验数据（公式（3））
	    }
	    //将修复的数据存入对象
	    super.matrixTransposition(tempMatrix1,temp);//恢复原数据格式将数组转置
	    pictureInComp horiRedun = new pictureInComp(temp);//水平冗余校验对象实例化
	    super.matrixTransposition(tempMatrix2,temp);
	    pictureInComp diagRedun = new pictureInComp(temp);
	
	}
	

	protected void decode(){
		pictureInComp[] tempMemory = null;
		int error1 = 0,error2 = 0;
		A encoding = null;//222
		if(error1 != -1 && error2 != -1){
			//有两个数据块出错
			int [][] dataCache,tempMatrix1,tempMatrix2;
		    pictureInComp[] corectObj;//暂存正确数据块的数据
			tempMatrix1=new int[tempMemory[0].getColumn()][tempMemory[0].getRow()];//暂存运算过程中水平校验位的数据值的转置数据
			tempMatrix2=new int[tempMemory[0].getColumn()][tempMemory[0].getRow()];//暂存运算过程中对角线校验位的数据值的转置数据
			int[][] temp = new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];//充当数组转置时的临时变量
			if(error1 == tempMemory.length - 2 && error2 == tempMemory.length - 1){//两个校验数据块出错
				corectObj=new pictureInComp[tempMemory.length - 2 ];
				System.arraycopy(tempMemory, 0, corectObj,0,tempMemory.length-2);//将正确的对象数组拷贝到corectObj中
				dataCache = new int[tempMemory[0].getRow()][corectObj.length];
				for(int i=0;i<corectObj.length;i++)
				{
					dataCache = super.getColumnData(corectObj,i);//获得对象元素的每一列，组成一个数组
					tempMatrix1[i]=super.horiExclusive_OR(dataCache);//修复数据块error
					
					tempMatrix2[i]=super.diagExclusive_OR(dataCache,encoding.getCommonFactor(dataCache));//修复数据块error2
				}
				//将修复的数据存入对象
				super.matrixTransposition(tempMatrix1,temp);
				tempMemory[error1].read(temp);
				super.matrixTransposition(tempMatrix2,temp);
				tempMemory[error2].read(temp);
			}
			else if((error1 >= 0 && error1 < tempMemory.length - 2) &&  error2 == tempMemory.length - 2)
			{//原数据块和水平校验数据块的数据出错
				int s=0;
				int m=tempMemory.length - 2;
				tempMatrix1 = new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];
				int[][] tempCache = new int[tempMemory[0].getRow()][tempMemory.length];
				dataCache = new int[tempMemory[0].getRow()+1][tempMemory.length];//存储增加一行的数据
				for(int i=0;i < tempMemory[0].getColumn();i++){
					tempCache = super.getColumnData(tempMemory,i);//获取对象数组中每一个元素对应列的数据
					dataCache=super.addRow(tempCache);//增加一行
					//计算公共因子
					s=dataCache[(error1-1+m)%m][m+1];//获取对象数组中每一个元素对应列的数据
					for(int l=0;l<m;l++)
					{
						s=s^dataCache[(error1-l-1+m)%m][l];
					}
					//恢复第error1个数据块数据
					for(int k=0;k<m-1;k++)
					{
						tempMatrix1[k][i]=s^dataCache[(k+error1+m)%m][m+1];
						for(int l=0;l<m;l++)
						{
							if(l != error1)
							{
								tempMatrix1[k][i]=tempMatrix1[k][i]^dataCache[(k+error1-l+m)%m][l];
							}
						}
					}
				}
					tempMemory[error1].read(tempMatrix1);//修复数据
					//恢复第error2个数据块的数据
					corectObj=new pictureInComp[m];
					System.arraycopy(tempMemory, 0, corectObj, 0, m);
					dataCache=new int[tempMemory[0].getRow()][m];
					for(int i=0;i<m;i++)
					{
						dataCache = super.getColumnData(corectObj, i);
						tempMatrix2[i]=super.horiExclusive_OR(dataCache);//水平异或
					}
					super.matrixTransposition(tempMatrix2, temp);
					tempMemory[error2].read(temp);
				}else if((error1 >= 0 && error1 < tempMemory.length - 2 ) && error2==tempMemory.length-1)
				{//原数据块和对角校验数据块出错
					int m=tempMemory.length-2;
					//修复数据块error1
					corectObj=new pictureInComp[m];
					dataCache=new int[tempMemory[0].getRow()][m];
					for(int i=0,j=0;i<tempMemory.length-1;i++)
					{
						if(i != error1)
						{
							corectObj[j++]=(pictureInComp)tempMemory[i].clone();
						}
					}
					for(int i=0;i<m;i++)
					{
						dataCache=super.getColumnData(corectObj, i);
						tempMatrix1[i]=super.horiExclusive_OR(dataCache);
					}
					super.matrixTransposition(tempMatrix1, temp);
					tempMemory[error1].read(temp);
					//修复数据块error2
					System.arraycopy(tempMemory, 0, corectObj, 0, m);
					for(int i=0;i<m;i++)
					{
						dataCache=super.getColumnData(corectObj, i);
						tempMatrix2[i]=super.diagExclusive_OR(dataCache, encoding.getCommonFactor(dataCache));					
					}
					super.matrixTransposition(tempMatrix2, temp);
					tempMemory[error2].read(temp);
				}else if((error1 >=0 && error1 < tempMemory.length - 2)&&(error2 >= 0 && error2 < tempMemory.length-2))
				{//两个原数据块出错
					int m=tempMemory.length-2;
					dataCache = new int[tempMemory[0].getRow()+1][m+2];
					int[][] tempCache=new int[tempMemory[0].getRow()][m+2];
					tempMatrix1=new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];
					tempMatrix2=new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];
					for(int i=0;i<tempMemory[0].getColumn();i++)
					{
						int s=0;
						int[] sh=new int[m];
						int[] sd=new int[m];
						int k=0;
						tempCache=super.getColumnData(tempMemory, i);
						for(int j=0;j<tempCache.length;j++)
							s=s^tempCache[j][m]^tempCache[j][m+1];//求公因子s:将所有校验位异或
							
						dataCache=super.addRow(tempCache);
							//求sh,sd公式3-7,3-8
							for(int u=0;u<m;u++)
							{
								for(int l=0;l<=m;l++)//求sh
									if(l != error1 && l != error2)
									{
										sh[u]=sh[u]^dataCache[u][l];
									}
									sd[u]=s^dataCache[u][m+1];
									for(int l=0;l<m;l++)
									{
										if(l != error1 && l != error2)
										{
											sd[u]=sd[u]^dataCache[(u-l+m)%m][l];
										}
									}
							}
							//修复数据
							k=(m-(error2-error1)-1)%m;
							while(k != m-1)
							{
								dataCache[k][error2]=sd[(error2+k+m)%m]^dataCache[(k+error2-error1+m)%m][error1];
								dataCache[k][error1]=sh[k]^dataCache[k][error2];
								k=((k-error2-error1)+m)%m;
							}
							for(int j=0;j<tempMatrix1.length;j++){
								//数据转移
								tempMatrix1[j][i]=dataCache[j][error1];
								tempMatrix2[j][i]=dataCache[j][error2];
							}
						}
				        tempMemory[error1].read(tempMatrix1);
				        tempMemory[error2].read(tempMatrix2);
					}
				else{
					System.out.print("error:fail to find the error disk!!");
					System.exit(0);
				}
			}else if(error2 == -1 && error1 != -1)
			{
				//只有一个数据块出错
				int m=tempMemory.length-2;
				int[][] dataCache = new int[tempMemory[0].getRow()][m];
				int[][] tempMatrix=new int[tempMemory[0].getColumn()][tempMemory[0].getRow()];
				pictureInComp[] corectObj=new pictureInComp[m];
				int[][] temp = new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];//充当数组转置时的临时变量
				if(error1 < tempMemory.length -1 && error1 >= 0 ){
					//出错数据块是水平校验位或者原数据
					for(int i=0,j=0;i<m+1;i++)
					{
						if(i != error1)
						{
							corectObj[j++]=(pictureInComp)tempMemory[i].clone();
						}
					}
					for(int i=0;i<corectObj.length;i++)
					{
						dataCache=super.getColumnData(corectObj, i);
						tempMatrix[i]=super.horiExclusive_OR(dataCache);
					}
					super.matrixTransposition(tempMatrix, temp);
					tempMemory[error1].read(temp);
				}else if(error1 == tempMemory.length-1){
					//出错数据块是对角校验位数据块
					System.arraycopy(tempMemory, 0, corectObj, 0, m);
					for(int i=0;i<corectObj.length;i++)
					{
						dataCache=super.getColumnData(corectObj, i);
						tempMatrix[i]=super.diagExclusive_OR(dataCache, encoding.getCommonFactor(dataCache));
					}
					super.matrixTransposition(tempMatrix, temp);
					tempMemory[error1].read(temp);
				}
			else
			{
				System.out.print("error:fail to find the error disk!!");
				System.exit(0);
			
		    }
			}else
				{//错误数据块不能找到
					System.out.print("error:fail to find the error disk!!");
					System.exit(0);
				
			    }
	}
    protected static void main(String[] args)
    {
    	
    }
}
	

