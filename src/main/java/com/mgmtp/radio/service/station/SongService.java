package com.mgmtp.radio.service.station;

import com.mgmtp.radio.domain.station.PlayList;
import com.mgmtp.radio.domain.station.Song;
import com.mgmtp.radio.dto.station.SongDTO;
import org.aspectj.lang.annotation.AfterReturning;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SongService {
    Flux<SongDTO> getListSongByStationId(String stationId);

    Flux<SongDTO> getAllSongById(List<String> idList);

    Mono<PlayList> getPlayListByStationId(String stationId);

    Mono<SongDTO> upVoteSongInStationPlaylist(String stationId, String songId, String userId);

    Mono<SongDTO> downVoteSongInStationPlaylist(String stationId, String songId, String userId);

    Mono<SongDTO> addSongToStationPlaylist(String stationId, String videoId, String message, String creatorId) ;

    Mono<Boolean> existsById(String id);

    Mono<SongDTO> getById(String id);

    Flux<SongDTO> getListSongByListSongId(List<String> listSongId);

	@AfterReturning(value = "execution(* com.mgmtp.radio.service.station.StationServiceImpl.checkAndSkipSongIfNeeded(..)"
		, returning = "songDTO")
	Mono<Song> updateSongSkippedStatusToDb(String songId);
}
