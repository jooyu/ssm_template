package com.dsky.baas.gameinvite.dao;

import com.dsky.baas.gameinvite.model.InvitedPeople;

public interface InvitedPeopleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table invited_people_0
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(InvitedPeople record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table invited_people_0
     *
     * @mbggenerated
     */
    int insert(InvitedPeople record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table invited_people_0
     *
     * @mbggenerated
     */
    int insertSelective(InvitedPeople record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table invited_people_0
     *
     * @mbggenerated
     */
    InvitedPeople selectByPrimaryKey(InvitedPeople record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table invited_people_0
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(InvitedPeople record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table invited_people_0
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(InvitedPeople record);
    
    InvitedPeople selectByInvitedPlayerIdAndGameId(InvitedPeople record);
}