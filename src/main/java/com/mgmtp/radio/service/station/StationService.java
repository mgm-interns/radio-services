package com.mgmtp.radio.service.station;

import com.mgmtp.radio.domain.station.ActiveStation;
import com.mgmtp.radio.domain.station.Station;
import com.mgmtp.radio.dto.station.StationConfigurationDTO;
import reactor.core.publisher.Mono;
import com.mgmtp.radio.dto.station.StationDTO;
import com.mgmtp.radio.dto.user.UserDTO;
import reactor.core.publisher.Flux;

public interface StationService {
    Flux<StationDTO> getAll();
    Mono<StationDTO> findById(String id);
    Mono<StationDTO> create(String userId, StationDTO stationDTO);
    Mono<StationDTO> update(String id, StationDTO stationDTO);

    int getOnlineUsersNumber(StationDTO stationDTO);

    Mono<Station> findStationByIdAndDeletedFalse(String stationId);
    Mono<ActiveStation> findByStationId(UserDTO userDTO, String id);

    Mono<StationConfigurationDTO> updateConfiguration(String id, StationConfigurationDTO stationConfigurationDTO);
}
