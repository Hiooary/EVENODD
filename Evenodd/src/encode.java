

public class encode extends A{

	
	protected encode()
	{
		int[][] dataCache=null,tempMatrix1=null,tempMatrix2=null;
		pictureInComp[] tempMemory = null;
		A encoding = null;
		tempMatrix1=new int[tempMemory[0].getColumn()][tempMemory[0].getRow()];//�ݴ����������ˮƽУ��λ������ֵ��ת������
		tempMatrix2=new int[tempMemory[0].getColumn()][tempMemory[0].getRow()];//�ݴ���������жԽ���У��λ������ֵ��ת������
        int[][] temp= new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];//�䵱����ת��ʱ����ʱ����
	    dataCache=new int[tempMemory[0].getRow()][tempMemory.length];
	    for(int i = 0; i < tempMemory.length; i++)
	    {
	    	dataCache = super.getColumnData(tempMemory,i);//��ö���Ԫ���е������е�ÿһ�У����һ������
	    	tempMatrix1[i]=super.horiExclusive_OR(dataCache);//���ˮƽУ�飨��ʽ��2����
			tempMatrix2[i]=super.diagExclusive_OR(dataCache,encoding.getCommonFactor(dataCache));//��öԽ���У�����ݣ���ʽ��3����
	    }
	    //���޸������ݴ������
	    super.matrixTransposition(tempMatrix1,temp);//�ָ�ԭ���ݸ�ʽ������ת��
	    pictureInComp horiRedun = new pictureInComp(temp);//ˮƽ����У�����ʵ����
	    super.matrixTransposition(tempMatrix2,temp);
	    pictureInComp diagRedun = new pictureInComp(temp);
	
	}
	

	protected void decode(){
		pictureInComp[] tempMemory = null;
		int error1 = 0,error2 = 0;
		A encoding = null;//222
		if(error1 != -1 && error2 != -1){
			//���������ݿ����
			int [][] dataCache,tempMatrix1,tempMatrix2;
		    pictureInComp[] corectObj;//�ݴ���ȷ���ݿ������
			tempMatrix1=new int[tempMemory[0].getColumn()][tempMemory[0].getRow()];//�ݴ����������ˮƽУ��λ������ֵ��ת������
			tempMatrix2=new int[tempMemory[0].getColumn()][tempMemory[0].getRow()];//�ݴ���������жԽ���У��λ������ֵ��ת������
			int[][] temp = new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];//�䵱����ת��ʱ����ʱ����
			if(error1 == tempMemory.length - 2 && error2 == tempMemory.length - 1){//����У�����ݿ����
				corectObj=new pictureInComp[tempMemory.length - 2 ];
				System.arraycopy(tempMemory, 0, corectObj,0,tempMemory.length-2);//����ȷ�Ķ������鿽����corectObj��
				dataCache = new int[tempMemory[0].getRow()][corectObj.length];
				for(int i=0;i<corectObj.length;i++)
				{
					dataCache = super.getColumnData(corectObj,i);//��ö���Ԫ�ص�ÿһ�У����һ������
					tempMatrix1[i]=super.horiExclusive_OR(dataCache);//�޸����ݿ�error
					
					tempMatrix2[i]=super.diagExclusive_OR(dataCache,encoding.getCommonFactor(dataCache));//�޸����ݿ�error2
				}
				//���޸������ݴ������
				super.matrixTransposition(tempMatrix1,temp);
				tempMemory[error1].read(temp);
				super.matrixTransposition(tempMatrix2,temp);
				tempMemory[error2].read(temp);
			}
			else if((error1 >= 0 && error1 < tempMemory.length - 2) &&  error2 == tempMemory.length - 2)
			{//ԭ���ݿ��ˮƽУ�����ݿ�����ݳ���
				int s=0;
				int m=tempMemory.length - 2;
				tempMatrix1 = new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];
				int[][] tempCache = new int[tempMemory[0].getRow()][tempMemory.length];
				dataCache = new int[tempMemory[0].getRow()+1][tempMemory.length];//�洢����һ�е�����
				for(int i=0;i < tempMemory[0].getColumn();i++){
					tempCache = super.getColumnData(tempMemory,i);//��ȡ����������ÿһ��Ԫ�ض�Ӧ�е�����
					dataCache=super.addRow(tempCache);//����һ��
					//���㹫������
					s=dataCache[(error1-1+m)%m][m+1];//��ȡ����������ÿһ��Ԫ�ض�Ӧ�е�����
					for(int l=0;l<m;l++)
					{
						s=s^dataCache[(error1-l-1+m)%m][l];
					}
					//�ָ���error1�����ݿ�����
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
					tempMemory[error1].read(tempMatrix1);//�޸�����
					//�ָ���error2�����ݿ������
					corectObj=new pictureInComp[m];
					System.arraycopy(tempMemory, 0, corectObj, 0, m);
					dataCache=new int[tempMemory[0].getRow()][m];
					for(int i=0;i<m;i++)
					{
						dataCache = super.getColumnData(corectObj, i);
						tempMatrix2[i]=super.horiExclusive_OR(dataCache);//ˮƽ���
					}
					super.matrixTransposition(tempMatrix2, temp);
					tempMemory[error2].read(temp);
				}else if((error1 >= 0 && error1 < tempMemory.length - 2 ) && error2==tempMemory.length-1)
				{//ԭ���ݿ�ͶԽ�У�����ݿ����
					int m=tempMemory.length-2;
					//�޸����ݿ�error1
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
					//�޸����ݿ�error2
					System.arraycopy(tempMemory, 0, corectObj, 0, m);
					for(int i=0;i<m;i++)
					{
						dataCache=super.getColumnData(corectObj, i);
						tempMatrix2[i]=super.diagExclusive_OR(dataCache, encoding.getCommonFactor(dataCache));					
					}
					super.matrixTransposition(tempMatrix2, temp);
					tempMemory[error2].read(temp);
				}else if((error1 >=0 && error1 < tempMemory.length - 2)&&(error2 >= 0 && error2 < tempMemory.length-2))
				{//����ԭ���ݿ����
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
							s=s^tempCache[j][m]^tempCache[j][m+1];//������s:������У��λ���
							
						dataCache=super.addRow(tempCache);
							//��sh,sd��ʽ3-7,3-8
							for(int u=0;u<m;u++)
							{
								for(int l=0;l<=m;l++)//��sh
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
							//�޸�����
							k=(m-(error2-error1)-1)%m;
							while(k != m-1)
							{
								dataCache[k][error2]=sd[(error2+k+m)%m]^dataCache[(k+error2-error1+m)%m][error1];
								dataCache[k][error1]=sh[k]^dataCache[k][error2];
								k=((k-error2-error1)+m)%m;
							}
							for(int j=0;j<tempMatrix1.length;j++){
								//����ת��
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
				//ֻ��һ�����ݿ����
				int m=tempMemory.length-2;
				int[][] dataCache = new int[tempMemory[0].getRow()][m];
				int[][] tempMatrix=new int[tempMemory[0].getColumn()][tempMemory[0].getRow()];
				pictureInComp[] corectObj=new pictureInComp[m];
				int[][] temp = new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];//�䵱����ת��ʱ����ʱ����
				if(error1 < tempMemory.length -1 && error1 >= 0 ){
					//�������ݿ���ˮƽУ��λ����ԭ����
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
					//�������ݿ��ǶԽ�У��λ���ݿ�
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
				{//�������ݿ鲻���ҵ�
					System.out.print("error:fail to find the error disk!!");
					System.exit(0);
				
			    }
	}
    protected static void main(String[] args)
    {
    	
    }
}
	

