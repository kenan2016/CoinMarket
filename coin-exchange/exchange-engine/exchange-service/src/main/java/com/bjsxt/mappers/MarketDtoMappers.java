package com.bjsxt.mappers;

import com.bjsxt.domain.Market;
import com.bjsxt.dto.MarketDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MarketDtoMappers {

    MarketDtoMappers INSTANCE = Mappers.getMapper(MarketDtoMappers.class) ;

    Market toConvertEntity(MarketDto source) ;


    MarketDto toConvertDto(Market source) ;
}
