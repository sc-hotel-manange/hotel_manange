package net.suncaper.hotel_manager.mapper;

import java.util.List;
import net.suncaper.hotel_manager.domain.H_Roomtype;
import net.suncaper.hotel_manager.domain.H_RoomtypeExample;
import org.apache.ibatis.annotations.Param;

public interface H_RoomtypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Fri Aug 02 11:50:11 CST 2019
     */
    long countByExample(H_RoomtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Fri Aug 02 11:50:11 CST 2019
     */
    int deleteByExample(H_RoomtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Fri Aug 02 11:50:11 CST 2019
     */
    int deleteByPrimaryKey(Integer rtId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Fri Aug 02 11:50:11 CST 2019
     */
    int insert(H_Roomtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Fri Aug 02 11:50:11 CST 2019
     */
    int insertSelective(H_Roomtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Fri Aug 02 11:50:11 CST 2019
     */
    List<H_Roomtype> selectByExample(H_RoomtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Fri Aug 02 11:50:11 CST 2019
     */
    H_Roomtype selectByPrimaryKey(Integer rtId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Fri Aug 02 11:50:11 CST 2019
     */
    int updateByExampleSelective(@Param("record") H_Roomtype record, @Param("example") H_RoomtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Fri Aug 02 11:50:11 CST 2019
     */
    int updateByExample(@Param("record") H_Roomtype record, @Param("example") H_RoomtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Fri Aug 02 11:50:11 CST 2019
     */
    int updateByPrimaryKeySelective(H_Roomtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_roomtype
     *
     * @mbg.generated Fri Aug 02 11:50:11 CST 2019
     */
    int updateByPrimaryKey(H_Roomtype record);
}