/*
 *
 * TODO To manage menu action
 */
package edu.nju.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import edu.nju.controller.impl.MenuControllerImpl;
import edu.nju.controller.impl.SettingControllerImpl;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.controller.service.SettingControllerService;
import edu.nju.model.data.StatisticData;
import edu.nju.model.po.StatisticPO;
import edu.nju.view.CustomDialog;
import edu.nju.view.MainFrame;
import edu.nju.view.RecordDialog;
 

public class MenuListener implements ActionListener{

	private MainFrame ui;
	MenuControllerService menuController = new MenuControllerImpl();
	SettingControllerService settingController = new SettingControllerImpl();
	
	public MenuListener(MainFrame ui){
		this.ui = ui;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ui.getMenuItem("start")) {//生成游戏，默认生成简单游戏
			menuController.startGame();
		} else if (e.getSource() == ui.getMenuItem("easy")) {//生成简单游戏
			
			settingController.setEasyGameLevel();
			
		} else if (e.getSource() == ui.getMenuItem("hard")) {//生成中等游戏
			
			settingController.setHardGameLevel();
			
		} else if (e.getSource() == ui.getMenuItem("hell")) {//生成大型游戏
			
			settingController.setHellGameLevel();
			
		} else if (e.getSource() == ui.getMenuItem("custom")) {//生成定制游戏，需要向controller传递棋盘的高、宽和雷数
			
			CustomDialog cd = new CustomDialog(ui.getMainFrame(),
					ui.getMineBoard().getRows(),
					ui.getMineBoard().getColumns(),
					Integer.parseInt(ui.getMineNumberLabel().getText()));
			cd.show();
			if(cd.getOk())
			{
				settingController.setCustomizedGameLevel(cd.getHeight(), cd.getWidth(), cd.getMineNumber());
			}
			
		} else if (e.getSource() == ui.getMenuItem("exit")) {
			System.exit(0);
		} else if (e.getSource() == ui.getMenuItem("record")) {//统计胜率信息
			RecordDialog rd = new RecordDialog(ui.getMainFrame());
			StatisticData sd = new StatisticData();
			List<StatisticPO> list = sd.getStatistic();
			if(list == null)
			{
				rd.show();
			}
			else
			{
				double[] score =  new double[list.size()];
				int index = 0;
				for(StatisticPO spo: list)
				{
					score[index] = spo.getWinrate();
					index++;
				}
				rd.show(score);
			}
			
		}else if(e.getSource() == ui.getMenuItem("host")){//注册成为主机
			
		}else if(e.getSource() == ui.getMenuItem("client")){//注册成为客户端
			
		}
	}


}