package edu.nju.model.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import edu.nju.model.po.StatisticPO;

/**
 * 负责进行统计数据获取和记录操作
 * @author Wangy
 *
 */
public class StatisticData {
	private final String dataFilePath = "save.dat";
	private BufferedReader br;
	private BufferedWriter bw;
	public StatisticData()
	{
		File file = new File(dataFilePath);
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	public List<StatisticPO> getStatistic(){
		List<StatisticPO> list = new ArrayList<StatisticPO>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(dataFilePath)));
			String s;
			while((s=br.readLine()) != null)
			{
				System.out.println(s);
				String[] strs = s.split(" ");
				if(strs.length == StatisticPO.dataNumber)
				{
					list.add(new StatisticPO(Double.parseDouble(strs[0]),
							Integer.parseInt(strs[1]),
							Integer.parseInt(strs[2]),
							Integer.parseInt(strs[3]),
							Integer.parseInt(strs[4]),
							Integer.parseInt(strs[5])));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(list.size() == 0)
			return null;
		else
			return list;
	}
	
	public boolean saveStatistic(StatisticPO statistic){
		try {
			bw = new  BufferedWriter(new FileWriter(dataFilePath,true));
			bw.newLine();
			bw.write(statistic.toString());
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

}