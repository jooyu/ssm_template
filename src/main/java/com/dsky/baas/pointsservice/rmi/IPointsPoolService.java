package com.dsky.baas.pointsservice.rmi;


public interface IPointsPoolService {

	/**
	 * @ 增加积分
	 * @author eaves.zhu
	 * @param 推广员A的id，A应该加多少分,A实际加多少分，推广员B的id，B应该加多少分，B实际加多少分，游戏id，活动id，code
	 * @return
	 */
	public void addPoints(int playAid,int shouldAddAPointsNumber, int promoterAPoints, int playBid,
			int shouldAddBPointsNumber, int promoterBPoints, int gid, int actid,String code);
	
	/**
	 * 减少积分
	 * @author eaves.zhu
	 * @param 
	 * @return
	 */
	public int minusPoints();

}
