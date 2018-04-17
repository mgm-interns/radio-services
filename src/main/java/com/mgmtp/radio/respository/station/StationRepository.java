package com.mgmtp.radio.respository.station;

import com.mgmtp.radio.domain.station.Station;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StationRepository extends ReactiveMongoRepository<Station, String> {
    @Query("{ $or: [ { '_id': ?#{[0]} }, { 'friendlyId': ?#{[0]} } ] }")
    Mono<Station> retriveByIdOrFriendlyId(String friendlyId);
    Flux<Station> findByOwnerId(String Id);
	Mono<Station> findFirstByName(String stationId);
}
