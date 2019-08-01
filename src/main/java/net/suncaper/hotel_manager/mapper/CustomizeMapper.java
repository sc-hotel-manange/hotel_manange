package net.suncaper.hotel_manager.mapper;

import net.suncaper.hotel_manager.domain.H_Hotel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomizeMapper {

    @Select("SELECT * FROM h_hotel WHERE star_rating='5' LIMIT 4")
    List<H_Hotel> selectFiveStarHotels();

    @Select("SELECT * FROM h_hotel ORDER BY rating_average DESC LIMIT 6")
    List<H_Hotel> selectTopRatedHotels();
}
