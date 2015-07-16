package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.nju.model.po.BlockPO;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.ParameterModelService;
import edu.nju.model.state.BlockState;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.BlockVO;

public class ChessBoardModelImpl extends BaseModel implements ChessBoardModelService{
	
	private GameModelService gameModel;
	private ParameterModelService parameterModel;
	
	private BlockPO[][] blockMatrix;

//	日志系统
	private static   Logger logger = LogManager.getLogger(ChessBoardModelImpl.class);
	
	public ChessBoardModelImpl(ParameterModelService parameterModel){
		this.parameterModel = parameterModel;
	}

	@Override
	public boolean initialize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		/********************简单示例初始化方法，待完善********************/
		blockMatrix = new BlockPO[width][height];
		setBlock(mineNum);
		
		this.parameterModel.setMineNum(mineNum);
		/***********请在删除上述内容的情况下，完成自己的内容****************/
		
		this.printBlockMatrix();
		
		return false;
	}

	@Override
	public boolean excavate(int x, int y) {
		// TODO Auto-generated method stub
		/********************简单示例挖开方法，待完善********************/
		
		if(blockMatrix == null)
			return false;
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		
		block.setState(BlockState.CLICK);
		blocks.add(block);
		
		GameState gameState = GameState.RUN;
		if(block.isMine()){
			gameState = GameState.OVER;
			this.gameModel.gameOver(GameResultState.FAIL);
		}
		
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));			
		/***********请在删除上述内容的情况下，完成自己的内容****************/
		return true;
	}
	
	@Override
	public boolean mark(int x, int y) {
		// TODO Auto-generated method stub
		/********************简单示例标记方法，待完善********************/
		if(blockMatrix == null)
			return false;
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		 
		BlockState state = block.getState();
		if(state == BlockState.UNCLICK){
			block.setState(BlockState.FLAG);
			this.parameterModel.minusMineNum();
		}
		else if(state == BlockState.FLAG){
			block.setState(BlockState.UNCLICK);
			this.parameterModel.addMineNum();
		}
		
		blocks.add(block);	
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, GameState.RUN)));
		/***********请在删除上述内容的情况下，完成自己的内容****************/
		
		return true;
	}

	@Override
	public boolean quickExcavate(int x, int y) {
		// TODO Auto-generated method stub
		logger.info("快速挖开地雷");
		/***********请在此处完成快速挖开方法实现****************/
		if(blockMatrix == null)
			return false;
		//记录需要挖开的方块
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		
		//只有点击已经被挖开的方块才能触发快速挖开功能
		if(block.getState() != BlockState.CLICK)
			return false;
		
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		int tempI = x-1;		
		int flagNum = 0;//改方块周围被被标记的雷数
		for(;tempI<=x+1;tempI++){
			int tempJ = y-1;
			for(;tempJ<=y+1;tempJ++){
				if((tempI>-1&&tempI<width)&&(tempJ>-1&&tempJ<height)){
//					System.out.println(i+";"+j+":"+tempI+";"+tempJ+":");
					if(blockMatrix[tempI][tempJ].getState() == BlockState.FLAG);
					{
						flagNum++;
					}
					if(blockMatrix[tempI][tempJ].getState() == BlockState.UNCLICK)
					{
						blocks.add(blockMatrix[tempI][tempJ]);
					}
				}
			}
		}
		if(flagNum == block.getMineNum())
		{
			logger.info("符合快速挖雷的条件");
			super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, GameState.RUN)));
			return true;
		}
		else
		{
			blocks.clear();
			return false;
		}
		
	}

	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 初始化BlockMatrix中的Block，并随机设置mineNum颗雷
	 * 同时可以为每个Block设定附近的雷数
	 * @param mineNum
	 * @return
	 */
	private boolean setBlock(int mineNum){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		int index = 0;
		
		//初始化
		for(int i = 0 ; i<width; i++){
			for (int j = 0 ; j< height; j++){
				blockMatrix[i][j] = new BlockPO(i,j);
			}
		}
		//放置雷，并设定block附近雷数，随机实现放置雷
		while(mineNum > 0)
		{
			Random rd = new Random();
			int i = rd.nextInt(width);
			int j = rd.nextInt(height);
			if(!blockMatrix[i][j].isMine())
			{
				blockMatrix[i][j].setMine(true);
				addMineNum(i, j);
				mineNum--;
			}
		}
		
		
		return false;
	}
	
	
	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 将(i,j)位置附近的Block雷数加1
	 * @param i
	 * @param j
	 */
	private void addMineNum(int i, int j){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		int tempI = i-1;		
		
		for(;tempI<=i+1;tempI++){
			int tempJ = j-1;
			for(;tempJ<=j+1;tempJ++){
				if((tempI>-1&&tempI<width)&&(tempJ>-1&&tempJ<height)){
//					System.out.println(i+";"+j+":"+tempI+";"+tempJ+":");
					blockMatrix[tempI][tempJ].addMine();
				}
			}
		}
		
	}
	
	/**
	 * 将逻辑对象转化为显示对象
	 * @param blocks
	 * @param gameState
	 * @return
	 */
	private List<BlockVO> getDisplayList(List<BlockPO> blocks, GameState gameState){
		List<BlockVO> result = new ArrayList<BlockVO>();
		for(BlockPO block : blocks){
			if(block != null){
				BlockVO displayBlock = block.getDisplayBlock(gameState);
				if(displayBlock.getState() != null)
				result.add(displayBlock);
			}
		}
		return result;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		// TODO Auto-generated method stub
		this.gameModel = gameModel;
	}
	
	private void printBlockMatrix(){
		for(BlockPO[] blocks : this.blockMatrix){
			for(BlockPO b :blocks){
				String p = b.getMineNum()+"";
				if(b.isMine())
					p="*";
				System.out.print(p);
			}
			System.out.println();
		}
	}
}
