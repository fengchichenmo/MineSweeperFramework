package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.ChessBoardModelService;

/**
 * @author zhangpeng
 * 右击小方块
 */
public class RightClickOperation extends MineOperation{

	private int x;
	private int y;
	public RightClickOperation(int x ,int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ChessBoardModelService chess = OperationQueue.getChessBoardModel();
		chess.mark(x, y);
	}

}
