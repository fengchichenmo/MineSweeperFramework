package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.service.SettingControllerService;
import edu.nju.model.service.GameModelService;

public class SettingControllerImpl implements SettingControllerService{
	GameModelService game = OperationQueue.getGameModel();
	@Override
	public boolean setEasyGameLevel() {
		// TODO Auto-generated method stub
		
		game.setGameLevel("小");
		game.startGame();
		
		return true;
	}

	@Override
	public boolean setHardGameLevel() {
		// TODO Auto-generated method stub
		game.setGameLevel("中");
		game.startGame();
		
		return true;
	}

	@Override
	public boolean setHellGameLevel() {
		// TODO Auto-generated method stub
		game.setGameLevel("大");
		game.startGame();
		return true;
	}

	@Override
	public boolean setCustomizedGameLevel(int height, int width, int nums) {
		// TODO Auto-generated method stub
		game.setGameSize(width, height, nums);
		game.startGame();
		return true;
	}

}
