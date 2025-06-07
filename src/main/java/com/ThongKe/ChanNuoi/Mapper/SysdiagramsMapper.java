package com.ThongKe.ChanNuoi.Mapper;

import java.time.LocalDateTime;
import com.ThongKe.ChanNuoi.DTOS.*;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class SysdiagramsMapper {
	
	public SysdiagramsDTOS mapper(Sysdiagrams entity) {
		return new SysdiagramsDTOS(
				    entity.getName(),
				    entity.getPrincipalId(),
				    entity.getDiagramId(),
				    entity.getVersion(),
				    entity.getDefinition()
			);
	}
	public Sysdiagrams mapperToObject(SysdiagramsDTOS entity) {
		return new Sysdiagrams(
					entity.getName(),
					entity.getPrincipalId(),
					entity.getDiagramId(),
					entity.getVersion(),
					entity.getDefinition()
			);
	}
	
}
